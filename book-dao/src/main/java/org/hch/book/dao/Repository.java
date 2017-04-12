package org.hch.book.dao;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

public interface Repository<T> extends CrudRepository<T, Serializable>{

	
}
