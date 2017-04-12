package org.hch.book.splider.data;

public class BookData {

	public static final int NOT_COMPLETE = 0; 
	public static final int COMPLETE = 1;
	
	public static final int SHOW = 0;
	public static final int HIDE = 1;
	
	private String typeId;
	
	private String subTypeId;
	
	private String cover;
	
	private String name;
	
	private String author;
	
	private String tag;
	
	private int writeStatus;
	
	private String intro;
	
	private String charNum;
	
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
	
	
}
