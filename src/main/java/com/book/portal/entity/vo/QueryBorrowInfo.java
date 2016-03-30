package com.book.portal.entity.vo;

import com.huisa.common.reflection.annotations.huisadb_alias;
import com.huisa.common.reflection.annotations.huisadb_ignore;

public class QueryBorrowInfo {
	@huisadb_ignore 
	private java.lang.Integer id; /*注释:借书条目id;长度：null;默认值：null;是否为空：NO;是否自增：1*/ 
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
	private java.lang.String bookName; /*注释:书本id;长度：null;默认值：null;是否为空：YES;是否自增：0*/ 	
	@huisadb_alias("borrow_time")
	private java.util.Date borrowTime; /*注释:借书时间;长度：null;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("deadline")
	private java.util.Date deadline; /*注释:应还书时间;长度：null;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("back_time")
	private java.util.Date backTime; /*注释:还书时间;长度：null;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("author")
	private java.lang.String author; /*注释:作者;长度：30;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("publisher")
	private java.lang.String publisher; /*注释:出版社;长度：30;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("library_card")
	private String libraryCard;
	@huisadb_alias("is_back")
	private java.lang.Integer isBack; /*注释:是否返回【0否 1是】;长度：null;默认值：0;是否为空：YES;是否自增：0*/

	public java.lang.String getAuthor() {
		return author;
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


	public java.lang.Integer getBookId() {
		return bookId;
	}

	public void setBookId(java.lang.Integer bookId) {
		this.bookId = bookId;
	}


	public java.lang.String getReaderName() {
		return readerName;
	}

	public void setReaderName(java.lang.String readerName) {
		this.readerName = readerName;
	}

	public java.lang.String getBookName() {
		return bookName;
	}

	public void setBookName(java.lang.String bookName) {
		this.bookName = bookName;
	}

	public java.util.Date getBorrowTime() {
		return borrowTime;
	}

	public void setBorrowTime(java.util.Date borrowTime) {
		this.borrowTime = borrowTime;
	}

	public java.util.Date getDeadline() {
		return deadline;
	}

	public void setDeadline(java.util.Date deadline) {
		this.deadline = deadline;
	}

	public java.util.Date getBackTime() {
		return backTime;
	}

	public void setBackTime(java.util.Date backTime) {
		this.backTime = backTime;
	}

	public java.lang.Integer getIsBack() {
		return isBack;
	}

	public void setIsBack(java.lang.Integer isBack) {
		this.isBack = isBack;
	} 
	
}
