package com.book.portal.entity.po.book;
import com.huisa.common.reflection.annotations.*;

   /**
    * giveback 实体类
    * Fri Jan 08 20:26:13 CST 2016 cxx
    */ 


@huisadb_alias("giveback")public class Giveback{
	@huisadb_ignore 
	private java.lang.Integer id; /*注释:还书条目id;长度：null;默认值：null;是否为空：NO;是否自增：1*/ 
	@huisadb_alias("borrow_id")
	private java.lang.Integer borrowId; /*注释:读者id;长度：null;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("is_overdue")
	private java.lang.Integer isOverdue; /*注释:是否逾期【0否 1是】;长度：null;默认值：0;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("is_lost")
	private java.lang.Integer isLost; /*注释:是否丢失【0否 1是】;长度：null;默认值：null;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("need_feed_money")
	private java.lang.Float needFeedMoney; /*注释:逾期或丢失  应还金额;长度：null;默认值：0000000000;是否为空：YES;是否自增：0*/ 
	@huisadb_alias("operator_id")
	private java.lang.Integer operatorId; /*注释:操作人员id;长度：null;默认值：null;是否为空：YES;是否自增：0*/ 
	public void setId(java.lang.Integer id){
	this.id=id;
	}
	public java.lang.Integer getId(){
		return id;
	}
	public void setBorrowId(java.lang.Integer borrowId){
	this.borrowId=borrowId;
	}
	public java.lang.Integer getBorrowId(){
		return borrowId;
	}
	public void setIsOverdue(java.lang.Integer isOverdue){
	this.isOverdue=isOverdue;
	}
	public java.lang.Integer getIsOverdue(){
		return isOverdue;
	}
	public void setIsLost(java.lang.Integer isLost){
	this.isLost=isLost;
	}
	public java.lang.Integer getIsLost(){
		return isLost;
	}
	public void setNeedFeedMoney(java.lang.Float needFeedMoney){
	this.needFeedMoney=needFeedMoney;
	}
	public java.lang.Float getNeedFeedMoney(){
		return needFeedMoney;
	}
	public void setOperatorId(java.lang.Integer operatorId){
	this.operatorId=operatorId;
	}
	public java.lang.Integer getOperatorId(){
		return operatorId;
	}
}

