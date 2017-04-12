package org.hch.book.search.dao;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class ClientFactory {

	private static ClientFactory clientFactory;
	
	private TransportClient client;
	
	private ClientFactory() {};
	
	public static ClientFactory getInstance() {
		if (null == clientFactory) {
			clientFactory = new ClientFactory();
		}
		return clientFactory;
	}
	
	public Client getClient() {
		if (null == client) {
			try {
				client = TransportClient.builder().build();
				ResourceBundle rb = ResourceBundle.getBundle("elasticSearch");
				String hosts = rb.getString("elasticSearch.host");
				String ports = rb.getString("elasticSearch.port");
				String[] hostArray = hosts.split(",");
				String[] portArray = ports.split(",");
				for (int i = 0; i < portArray.length; i++) {
					client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostArray[i]), Integer.valueOf(portArray[i])));
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			
		}
		return client;
	}
}
