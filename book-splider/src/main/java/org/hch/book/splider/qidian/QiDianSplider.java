package org.hch.book.splider.qidian;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.hch.book.search.dao.ElasticSearchDao;
import org.hch.book.search.dao.ElasticSearchDaoImpl;
import org.hch.book.splider.data.BookData;
import org.hch.book.splider.data.BookTypeData;
import org.hch.book.splider.data.ChapterData;
import org.hch.book.splider.data.SubType;
import org.hch.book.util.util.CheckUtil;
import org.hch.book.util.util.JsonUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class QiDianSplider {

	private static final String QIDIAN_URL = "http://www.qidian.com";
	//书本详情分页后缀
	private static final String QIDIAN_BOOK_PAGE_PREFFIX = "&size=-1&sign=-1&tag=-1&orderId=&update=-1&month=-1&style=1&action=-1&vip=-1&page=";
	
	private ElasticSearchDao elasticSearchDao = new ElasticSearchDaoImpl();
	
	private static final String INDEX = "book";
	private static final String TYPE = "type";
	
	ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
	
	public void saveBookType() {
		try {
			Document document = Jsoup.connect(QIDIAN_URL).get();
			Elements dds = document.select("#classify-list > dl > dd");
			for (Element element : dds) {
				final BookTypeData bookTypeData = new BookTypeData();
				Element span = element.select(".info").get(0);
				String name = span.select("i").get(0).text();
				int bookNum = Integer.valueOf(span.select("b").get(0).text());
				final String url = element.select("a").first().attr("abs:href");
				
				bookTypeData.setName(name);
				bookTypeData.setBookNum(bookNum);
				//保存书籍类型
				//elasticSearchDao.save(INDEX, TYPE, JsonUtil.obj2JsonString(bookTypeData));
				
				//System.out.println(JsonUtil.obj2JsonString(bookTypeData));
				
				//saveSubType(url, "001");
				
				executorService.submit(new Runnable() {
					
					public void run() {
						String id = elasticSearchDao.save(INDEX, TYPE, JsonUtil.obj2JsonString(bookTypeData));
						
						System.out.println(JsonUtil.obj2JsonString(bookTypeData));
						
						saveSubType(url, id);
						
					}
				});
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void saveSubType(String url, String typeId) {
		if (CheckUtil.stringIsNullOrEmpty(url)) return;
		try {
			SubType subType = null;
			Document document = Jsoup.connect(url).get();
			Elements as = document.select(".sub-type-wrap a[href^='//a']");
			for (Element a : as) {
				subType = new SubType();
				subType.setName(a.text());
				
				String subTypeId = elasticSearchDao.save(INDEX, "subType", JsonUtil.obj2JsonString(subType));
				
				System.out.println(JsonUtil.obj2JsonString(subType));
				
				String href = a.attr("abs:href");
				
				saveBookDetail(href, typeId, subTypeId);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void saveBookDetail(String href, String typeId, String subTypeId) {
		try {
			Document doc = Jsoup.connect(href).get();
			Element pageContainer = doc.select("#page-container").first();
			int totalPage = Integer.valueOf(pageContainer.attr("data-pagemax"));
			
			System.out.println(totalPage);
			
			int page = 1;
			while(page <= totalPage) {
				Document document = Jsoup.connect(href + QIDIAN_BOOK_PAGE_PREFFIX + page).get();
				Elements lis = document.select(".all-img-list > li");
				for (Element li : lis) {
					BookData bookData = new BookData();
					Element a = li.select(".book-img-box > a").first();
					String url = a.attr("abs:href");
					String cover = a.select("img").first().absUrl("src");
					
					Element div = li.select(".book-mid-info").first();
					String name = div.select("h4 > a").first().text();
					String author = div.select(".author > a.name").first().text();
					int writeStatus = div.select("span").first().text().equals("连载中") 
							? BookData.NOT_COMPLETE : BookData.COMPLETE;
					String intro = div.select(".intro").first().text().trim();
					String charNum = div.select(".update > span").text();
					
					StringBuilder tag = new StringBuilder();
					Elements as = div.select(".author > em:eq(0) ~ a");
					for (int i = 0; i < as.size(); i++) {
						if (i != 0) {
							tag.append(",");
						}
						tag.append(as.get(i).text());
					}
					
					bookData.setCover(cover);
					bookData.setAuthor(author);
					bookData.setCharNum(charNum);
					bookData.setIntro(intro);
					bookData.setName(name);
					bookData.setStatus(BookData.SHOW);
					bookData.setSubTypeId(subTypeId);
					bookData.setTag(tag.toString());
					bookData.setTypeId(typeId);
					bookData.setWriteStatus(writeStatus);
					
					String id = elasticSearchDao.save(INDEX, "book", JsonUtil.obj2JsonString(bookData));
					
					System.out.println(JsonUtil.obj2JsonString(bookData));
				}
				
				page++;
				System.out.println("###################################");
				System.out.println("###################################");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void saveBookZJ(String url, String bookId) {
		try {
			Document document = Jsoup.connect(url).get();
			Element div = document.select(".volume-wrap").first().select(".volume").last();
			Elements as = div.select("a");
			int len = as.size();
			for (int i = 1; i <= len; i++) {
				Element a = as.get(i);
				ChapterData chapterData = new ChapterData();
				
				String href = a.absUrl("href");
				String updateDesc = a.attr("title");
				String[] desc = handleUpdtaeDesc(updateDesc);
				String firstTime = desc[0];
				int charNum = Integer.valueOf(desc[1]);
				String name = a.text();
				String content = "";
				
				chapterData.setBookId(bookId);
				chapterData.setCharNum(charNum);
				chapterData.setContent(content);
				chapterData.setFirstTime(firstTime);
				chapterData.setName(name);
				chapterData.setNum(i);
				
				
				//String chapterId = elasticSearchDao.save(INDEX, "chapter", JsonUtil.obj2JsonString(chapterData));
				System.out.println(JsonUtil.obj2JsonString(chapterData));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String[] handleUpdtaeDesc(String updateDesc) {
		String[] result = {"", "0"};
		if (CheckUtil.stringIsNullOrEmpty(updateDesc)){
			return result;
		}
		
		String[] split = updateDesc.split("章");
		if (null != split && split.length > 0) {
			for (int i = 0; i < result.length; i++) {
				result[i] = split[i].split("：")[1];
			}
		}
		return result;
	}

	public static void main(String[] args) {
		QiDianSplider qd = new QiDianSplider();
		qd.saveBookType();
	}
}
