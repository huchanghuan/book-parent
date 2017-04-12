package org.hch.book.search.dao;

public interface ElasticSearchDao {

	public String save(String indexName, String typeName, String json);
	
	public void delete(String indexName, String typeName, String id);
	
	public void update(String indexName, String typeName, String id, String json);
	
	public String getById(String indexName, String typeName, String id);
	
}
