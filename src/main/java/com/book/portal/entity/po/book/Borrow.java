package com.book.portal.entity.po.book;
import java.util.Date;
import com.huisa.common.reflection.annotations.*;

   /**
    * borrow 实体类
    * Fri Jan 08 20:25:45 CST 2016 cxx
    */ 


@huisadb_alias("borrow")public class Borrow{
	@huisadb_ignore 
	private java.lang.Integer id; /*注释:借书条目id;长度：null;默认值：null;是否为空：NO;是否自增：1*/ 
	@huisadb_alias("reader_id")
	private java.lang.Integer readerId; /*注释:借书的读者id;长度：null;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("book_id")
	private java.lang.Integer bookId; /*注释:书本id;长度：null;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("borrow_time")
	private java.util.Date borrowTime; /*注释:借书时间;长度：null;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("deadline")
	private java.util.Date deadline; /*注释:应还书时间;长度：null;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("back_time")
	private java.util.Date backTime; /*注释:还书时间;长度：null;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("operator_id")
	private java.lang.Integer operatorId; /*注释:操作人id;长度：null;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("is_back")
	private java.lang.Integer isBack; /*注释:是否返回【0否 1是】;长度：null;默认值：0;是否为空：YES;是否自增：0*/ 
	public void setId(java.lang.Integer id){
	this.id=id;
	}
	public java.lang.Integer getId(){
		return id;
	}
	public void setReaderId(java.lang.Integer readerId){
	this.readerId=readerId;
	}
	public java.lang.Integer getReaderId(){
		return readerId;
	}
	public void setBookId(java.lang.Integer bookId){
	this.bookId=bookId;
	}
	public java.lang.Integer getBookId(){
		return bookId;
	}
	public void setBorrowTime(java.util.Date borrowTime){
	this.borrowTime=borrowTime;
	}
	public java.util.Date getBorrowTime(){
		return borrowTime;
	}
	public void setDeadline(java.util.Date deadline){
	this.deadline=deadline;
	}
	public java.util.Date getDeadline(){
		return deadline;
	}
	public void setBackTime(java.util.Date backTime){
	this.backTime=backTime;
	}
	public java.util.Date getBackTime(){
		return backTime;
	}
	public void setOperatorId(java.lang.Integer operatorId){
	this.operatorId=operatorId;
	}
	public java.lang.Integer getOperatorId(){
		return operatorId;
	}
	public void setIsBack(java.lang.Integer isBack){
	this.isBack=isBack;
	}
	public java.lang.Integer getIsBack(){
		return isBack;
	}
}

