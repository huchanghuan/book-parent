package org.hch.book.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.DynamicInsert;

/**
 * 章节
 * @ClassName ChapterData
 * @Description TODO
 * @author huchanghuan
 * @Date 2017年3月28日 上午11:33:08
 * @version 1.0.0
 */
@Entity
@Table(name = "chapter")
@DynamicInsert(true)
@DynamicUpdate(true)
public class ChapterBean implements Serializable {

	/**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	@Column(name = "num")
	private int num;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "firstTime")
	private String firstTime;
	
	@Column(name = "charNum")
	private int charNum;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "bookId")
	private String bookId;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(String firstTime) {
		this.firstTime = firstTime;
	}

	public int getCharNum() {
		return charNum;
	}

	public void setCharNum(int charNum) {
		this.charNum = charNum;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	
	
}
