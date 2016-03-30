package com.book.portal.entity.po.portal;

import java.io.Serializable;

import com.huisa.common.reflection.annotations.huisadb_alias;
import com.huisa.common.reflection.annotations.huisadb_ignore;

/*  */
@huisadb_alias("user_info")
public class PortalUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 111044141565L;
	@huisadb_ignore
	private java.lang.Integer id;//remark:id，自增;length:10; not null,default:null
	private java.lang.String account;//remark:账号;length:16; not null,default:null
	private java.lang.String passwd;//remark:密码，MD5加密;length:32; not null,default:null
	@huisadb_alias("library_card")
	private String libraryCard;
    private String idcard;
    private Integer sex;
	@huisadb_alias("is_delete")
    private Integer isDelete ;
	@huisadb_alias("user_name")
	private java.lang.String userName;//remark:姓名;length:16; not null,default:null
	@huisadb_alias("create_time")
	private java.util.Date createTime;//remark:创建时间;length:19
	@huisadb_ignore
	@huisadb_alias("update_time")
	private java.util.Date updateTime;//remark:更新时间;length:19; not null,default:CURRENT_TIMESTAMP

	public PortalUser() {
	}


	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}


	public String getLibraryCard() {
		return libraryCard;
	}


	public void setLibraryCard(String libraryCard) {
		this.libraryCard = libraryCard;
	}


	public Integer getIsDelete() {
		return isDelete;
	}


	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}


	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getId() {
		return id;
	}

	public void setAccount(java.lang.String account) {
		this.account = account;
	}

	public java.lang.String getAccount() {
		return account;
	}

	public void setPasswd(java.lang.String passwd) {
		this.passwd = passwd;
	}

	public java.lang.String getPasswd() {
		return passwd;
	}

	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

	public java.lang.String getUserName() {
		return userName;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}
}