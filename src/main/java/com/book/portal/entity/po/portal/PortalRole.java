package com.book.portal.entity.po.portal;

import java.util.*;
import com.huisa.common.reflection.annotations.*;

/*  */
@huisadb_alias("role")
public class PortalRole {
	@huisadb_ignore
	private java.lang.Integer id;//remark:id，自增;length:10; not null,default:null
	@huisadb_alias("role_name")
	private java.lang.String roleName;//remark:角色名称;length:16; not null,default:null
	@huisadb_alias("role_code")
	private java.lang.String roleCode;//remark:角色code;length:16; not null,default:null
	@huisadb_alias("can_borrow_number")
	private java.lang.Integer canBorrowNumber;//remark:角色名称;length:16; not null,default:null
	@huisadb_alias("overdue_fine")
	private java.lang.Float overdueFine;//remark:角色code;length:16; not null,default:null
	@huisadb_alias("lost_fine")
	private java.lang.Float lostFine;//remark:角色code;length:16; not null,default:null
	@huisadb_alias("create_time")
	private java.util.Date createTime;//remark:创建时间;length:19
	@huisadb_ignore
	@huisadb_alias("update_time")
	private java.util.Date updateTime;//remark:更新时间;length:19; not null,default:CURRENT_TIMESTAMP

	public PortalRole() {
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getCanBorrowNumber() {
		return canBorrowNumber;
	}

	public void setCanBorrowNumber(java.lang.Integer canBorrowNumber) {
		this.canBorrowNumber = canBorrowNumber;
	}

	public java.lang.Float getOverdueFine() {
		return overdueFine;
	}

	public void setOverdueFine(java.lang.Float overdueFine) {
		this.overdueFine = overdueFine;
	}

	public java.lang.Float getLostFine() {
		return lostFine;
	}

	public void setLostFine(java.lang.Float lostFine) {
		this.lostFine = lostFine;
	}

	public java.lang.Integer getId() {
		return id;
	}

	public void setRoleName(java.lang.String roleName) {
		this.roleName = roleName;
	}

	public java.lang.String getRoleName() {
		return roleName;
	}

	public void setRoleCode(java.lang.String roleCode) {
		this.roleCode = roleCode;
	}

	public java.lang.String getRoleCode() {
		return roleCode;
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