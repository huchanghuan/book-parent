package org.hch.book.util.proxy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

public class HttpRequestUtils {

	/**
	 * GET请求
	 * @Description (TODO
	 * @param url
	 * @return
	 */
	public static String doGetRequest(String url) {
		return doGetRequest(url, null);
	}
	
	/**
	 * GET请求
	 * @Description (TODO
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doGetRequest(String url, Map<String, Object> params) {
		CloseableHttpClient closeableHttpClient = null;
		String result = null;
		 try {
			closeableHttpClient = createCloseableHttpClient(url);
			 URIBuilder uriBuilder = new URIBuilder(url);
			 if (null != params && !params.isEmpty()) {
				 for (Entry<String, Object> entry : params.entrySet()) {
					 uriBuilder.addParameter(entry.getKey(), null != entry.getValue() ? (String)entry.getValue() : "");
				}
			 }
			 
			 HttpGet httpGet = new HttpGet(uriBuilder.build());
			 CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
			 result = EntityUtils.toString(closeableHttpResponse.getEntity());
		 } catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeableHttpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		 
		 return result;
	}
	
	/**
	 * POST请求
	 * @Description (TODO
	 * @param url
	 * @return
	 */
	public static String doPostRequest(String url) {
		return doPostRequest(url, null);
	}
	
	/**
	 * POST请求
	 * @Description (TODO
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doPostRequest(String url, Map<String, Object> params) {
		String result = null;
		CloseableHttpClient closeableHttpClient = null;
		try {
			closeableHttpClient = createCloseableHttpClient(url);
			HttpPost httpPost = new HttpPost(url);
			if (null != params && !params.isEmpty()) {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				for (Entry<String, Object> entry : params.entrySet()) {
					nameValuePairs.add(new BasicNameValuePair(entry.getKey(), null != entry.getValue() ? (String)entry.getValue() : ""));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, Consts.UTF_8));
			}
			
			CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
			result = EntityUtils.toString(closeableHttpResponse.getEntity(), Consts.UTF_8);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeableHttpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 下载资源
	 * @Description (TODO
	 * @param url
	 * @return
	 */
	public static void download(String url, String localFilePath) {
		File file = new File(localFilePath);
		File parentFile = file.getParentFile();
		if (null != parentFile && !parentFile.exists()) {
			parentFile.mkdirs();
		}
		
		CloseableHttpClient closeableHttpClient = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			closeableHttpClient = createCloseableHttpClient(url);
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
			InputStream is = closeableHttpResponse.getEntity().getContent();
			byte[] bytes = new byte[4096];
			int len = 0;
			while(-1 != (len = is.read(bytes))) {
				fos.write(bytes, 0, len);
			}
			is.close();
			fos.flush();
			fos.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeableHttpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 上传
	 * @Description (TODO
	 */
	public static void upload(String url, Map<String, Object> params, String fileName, File file) {
		CloseableHttpClient closeableHttpClient = null;
		
		try {
			closeableHttpClient = createCloseableHttpClient(url);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			
			if (null != params && !params.isEmpty()) {
				for (Entry<String, Object> entry : params.entrySet()) {
					builder.addPart(entry.getKey(), new StringBody(null != entry.getValue() ? (String)entry.getValue() : "", ContentType.MULTIPART_FORM_DATA));
				}
			}
			
			builder.addPart(fileName, new FileBody(file));
			
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(builder.build());
			
			CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
			String result = EntityUtils.toString(closeableHttpResponse.getEntity());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static CloseableHttpClient createCloseableHttpClient(String url) throws Exception {
		if (url.toLowerCase().startsWith("http://")) {
			return HttpClientBuilder.create().build();
		} else if (url.toLowerCase().startsWith("https://")) {
			return createSSLContextDefault();
		} else {
			throw new Exception("url is invalid");
		}
	}
	
	public static CloseableHttpClient createSSLContextDefault() {
		CloseableHttpClient closeableHttpClient = null;
		try {
			SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(null, new TrustStrategy() {
				
				public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					
					return true;
				}
			}).build();
			
			closeableHttpClient = HttpClients.custom().setSSLSocketFactory(new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE)).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return closeableHttpClient;
	}
	
	public static void main(String[] args) {
		download("https://www.baidu.com", "e://HCH/baidu.html");
		System.out.println("over!");
		
	}
}
