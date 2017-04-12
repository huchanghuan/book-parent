package org.hch.book.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "book")
@DynamicInsert(true)
@DynamicUpdate(true)
public class BookBean implements Serializable {

	/**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int NOT_COMPLETE = 0; 
	public static final int COMPLETE = 1;
	
	public static final int SHOW = 0;
	public static final int HIDE = 1;
	
	@Id
	private String id;
	
	@Column(name = "typeId")
	private String typeId;
	
	@Column(name = "subTypeId")
	private String subTypeId;
	
	@Column(name = "cover")
	private String cover;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "author")
	private String author;
	
	@Column(name = "tag")
	private String tag;
	
	@Column(name = "writeStatus")
	private int writeStatus;
	
	@Column(name = "intro")
	private String intro;
	
	@Column(name = "charNum")
	private String charNum;
	
	@Column(name = "status")
	private int status;

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getSubTypeId() {
		return subTypeId;
	}

	public void setSubTypeId(String subTypeId) {
		this.subTypeId = subTypeId;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getWriteStatus() {
		return writeStatus;
	}

	public void setWriteStatus(int writeStatus) {
		this.writeStatus = writeStatus;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getCharNum() {
		return charNum;
	}

	public void setCharNum(String charNum) {
		this.charNum = charNum;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
