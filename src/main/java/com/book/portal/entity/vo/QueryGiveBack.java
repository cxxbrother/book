package com.book.portal.entity.vo;

import com.huisa.common.reflection.annotations.huisadb_alias;
import com.huisa.common.reflection.annotations.huisadb_ignore;

public class QueryGiveBack {
	@huisadb_ignore 
	private java.lang.Integer id; /*注释:还书条目id;长度：null;默认值：null;是否为空：NO;是否自增：1*/ 
	@huisadb_alias("reader_id")
	private java.lang.Integer readerId; /*注释:借书的读者id;长度：null;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("ISBN")
	private java.lang.String ISBN; /*注释:书号;长度：30;默认值：null;是否为空：YES;是否自增：0*/ 
    private java.lang.String  account;
	@huisadb_alias("user_name")
	private java.lang.String readerName; /*注释:借书的读者姓名;长度：null;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("book_id")
	private java.lang.Integer bookId; /*注释:书本id;长度：null;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("book_name")
	private java.lang.String bookName; /*注释:书本名;长度：null;默认值：null;是否为空：YES;是否自增：0*/ 	
	@huisadb_alias("author")
	private java.lang.String author; /*注释:作者;长度：30;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("publisher")
	private java.lang.String publisher; /*注释:出版社;长度：30;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("library_card")
	private String libraryCard;
	@huisadb_alias("borrow_id")
	private java.lang.Integer borrowId; /*注释:读者id;长度：null;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("is_overdue")
	private java.lang.Integer isOverdue; /*注释:是否逾期【0否 1是】;长度：null;默认值：0;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("is_lost")
	private java.lang.Integer isLost; /*注释:是否丢失【0否 1是】;长度：null;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("need_feed_money")
	private java.lang.Float needFeedMoney; /*注释:逾期或丢失  应还金额;长度：null;默认值：0000000000;是否为空：YES;是否自增：0*/
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public java.lang.Integer getReaderId() {
		return readerId;
	}
	public void setReaderId(java.lang.Integer readerId) {
		this.readerId = readerId;
	}
	public java.lang.String getISBN() {
		return ISBN;
	}
	public void setISBN(java.lang.String iSBN) {
		ISBN = iSBN;
	}
	public java.lang.String getAccount() {
		return account;
	}
	public void setAccount(java.lang.String account) {
		this.account = account;
	}
	public java.lang.String getReaderName() {
		return readerName;
	}
	public void setReaderName(java.lang.String readerName) {
		this.readerName = readerName;
	}
	public java.lang.Integer getBookId() {
		return bookId;
	}
	public void setBookId(java.lang.Integer bookId) {
		this.bookId = bookId;
	}
	public java.lang.String getBookName() {
		return bookName;
	}
	public void setBookName(java.lang.String bookName) {
		this.bookName = bookName;
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
	public String getLibraryCard() {
		return libraryCard;
	}
	public void setLibraryCard(String libraryCard) {
		this.libraryCard = libraryCard;
	}
	public java.lang.Integer getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(java.lang.Integer borrowId) {
		this.borrowId = borrowId;
	}
	public java.lang.Integer getIsOverdue() {
		return isOverdue;
	}
	public void setIsOverdue(java.lang.Integer isOverdue) {
		this.isOverdue = isOverdue;
	}
	public java.lang.Integer getIsLost() {
		return isLost;
	}
	public void setIsLost(java.lang.Integer isLost) {
		this.isLost = isLost;
	}
	public java.lang.Float getNeedFeedMoney() {
		return needFeedMoney;
	}
	public void setNeedFeedMoney(java.lang.Float needFeedMoney) {
		this.needFeedMoney = needFeedMoney;
	}
	
	
	
}
