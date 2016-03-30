package com.book.portal.entity.vo;

import com.huisa.common.reflection.annotations.huisadb_alias;
import com.huisa.common.reflection.annotations.huisadb_ignore;

public class QueryBookInfo {
	@huisadb_ignore 
	private java.lang.Integer id; /*注释:书本id;长度：null;默认值：null;是否为空：NO;是否自增：1*/ 
	@huisadb_alias("ISBN")
	private java.lang.String ISBN; /*注释:书号;长度：30;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("book_name")
	private java.lang.String bookName; /*注释:书名;长度：70;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("type")
	private java.lang.String type; /*注释:类型;长度：70;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("author")
	private java.lang.String author; /*注释:作者;长度：30;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("publisher")
	private java.lang.String publisher; /*注释:出版社;长度：30;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("translator")
	private java.lang.String translator; /*注释:出版社;长度：30;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("publish_time")
	private java.lang.String publishTime; /*注释:出版日期;长度：30;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("price")
	private java.lang.Float price; /*注释:价格;长度：null;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("in_time")
	private java.util.Date inTime; /*注释:加入时间;长度：null;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("user_name")
	private java.lang.String operatorName; /*注释:操作人id;长度：null;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("has_number")
	private java.lang.Integer hasNumber; /*注释:目前剩余量【未被借走】;长度：null;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("total_number")
	private java.lang.Integer totalNumber; /*注释:书总量;长度：null;默认值：0;是否为空：YES;是否自增：0*/
    private java.lang.Integer is_delete ;
    
	public java.lang.Integer getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(java.lang.Integer is_delete) {
		this.is_delete = is_delete;
	}
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public java.lang.String getISBN() {
		return ISBN;
	}
	public void setISBN(java.lang.String iSBN) {
		ISBN = iSBN;
	}
	public java.lang.String getBookName() {
		return bookName;
	}
	public void setBookName(java.lang.String bookName) {
		this.bookName = bookName;
	}
	public java.lang.String getType() {
		return type;
	}
	public void setType(java.lang.String type) {
		this.type = type;
	}
	public java.lang.String getAuthor() {
		return author;
	}
	public void setAuthor(java.lang.String author) {
		this.author = author;
	}
	public java.lang.String getPublisher() {
		return publisher;
	}
	public void setPublisher(java.lang.String publisher) {
		this.publisher = publisher;
	}
	public java.lang.String getTranslator() {
		return translator;
	}
	public void setTranslator(java.lang.String translator) {
		this.translator = translator;
	}
	public java.lang.String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(java.lang.String publishTime) {
		this.publishTime = publishTime;
	}
	public java.lang.Float getPrice() {
		return price;
	}
	public void setPrice(java.lang.Float price) {
		this.price = price;
	}
	public java.util.Date getInTime() {
		return inTime;
	}
	public void setInTime(java.util.Date inTime) {
		this.inTime = inTime;
	}
	public java.lang.String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(java.lang.String operatorName) {
		this.operatorName = operatorName;
	}
	public java.lang.Integer getHasNumber() {
		return hasNumber;
	}
	public void setHasNumber(java.lang.Integer hasNumber) {
		this.hasNumber = hasNumber;
	}
	public java.lang.Integer getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(java.lang.Integer totalNumber) {
		this.totalNumber = totalNumber;
	} 
	
}
