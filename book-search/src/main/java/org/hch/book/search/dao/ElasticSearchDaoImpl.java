package org.hch.book.search.dao;

import java.io.IOException;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;

public class ElasticSearchDaoImpl implements ElasticSearchDao{

	private Client client = ClientFactory.getInstance().getClient();
	
	public String save(String indexName, String typeName, String json) {
		IndexResponse indexResponse = client.prepareIndex()
			.setIndex(indexName)
			.setType(typeName)
			.setSource(json)
			.execute().actionGet();
		
		indexResponse.isCreated();
		
		return indexResponse.getId();
	}

	public void delete(String indexName, String typeName, String id) {
		DeleteResponse deleteResponse = client.prepareDelete()
			.setIndex(indexName)
			.setType(typeName)
			.setId(id)
			.get();
		
		deleteResponse.isFound();
	}

	public void update(String indexName, String typeName, String id, String json) {
		
		UpdateResponse updateResponse = client.prepareUpdate()
			.setIndex(indexName)
			.setType(typeName)
			.setId(id)
			.setDoc(json)
			.get();
		
		updateResponse.isCreated();
	}

	public String getById(String indexName, String typeName, String id) {
		
		GetResponse getResponse = client.prepareGet()
			.setIndex(indexName)
			.setType(typeName)
			.setId(id)
			.get();
		
		return getResponse.getSourceAsString();
	}

	public static XContentBuilder jsonBuilder() {
		XContentBuilder xContentBuilder = null;
		try {
			xContentBuilder = XContentFactory.jsonBuilder();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return xContentBuilder;
	}
	
	
	public static void main(String[] args) throws Exception {
		ElasticSearchDao dao = new ElasticSearchDaoImpl();
		XContentBuilder builder = jsonBuilder().startObject()
					.field("name", "陈思成")
					.field("age", 35)
					.field("location", "上海")
					.endObject();
		
//		dao.save("test", "person", builder.string());
//		String string = dao.getById("test", "person", "AVsOlnypkvxVzyXYdUht");
//		System.out.println(string);
//		dao.update("test", "person", "AVsOlnypkvxVzyXYdUht", builder.string());
//		dao.delete("test", "person", "AVsOlnypkvxVzyXYdUht");
		
		Client transportclient = ClientFactory.getInstance().getClient();
		SearchResponse searchResponse = transportclient.prepareSearch("test")
				.setTypes("person")
				//.setQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("name", "hch")))
				.setQuery(QueryBuilders.wildcardQuery("name", "*h*"))
				.setFrom(1)
				.setSize(2)
				.addSort("age", SortOrder.DESC)
				.get();
		
		SearchHits hits = searchResponse.getHits();
		if (hits.getTotalHits() > 0) {
			for (SearchHit searchHit : hits) {
				System.out.println(searchHit.getSourceAsString());
			}
		}
		
		System.out.println("over");
	}
}
