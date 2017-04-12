package org.hch.book.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "subType")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SubTypeBean implements Serializable{

	/**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	@Column(name = "typeId")
	private String typeId;
	
	@Column(name = "name")
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
