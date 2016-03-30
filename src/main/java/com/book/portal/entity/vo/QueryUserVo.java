package com.book.portal.entity.vo;

public class QueryUserVo
{
    private int id;
    private String account;
    private String user_name;
    private String role;
    private String library_card;
    private String idcard;
    private Integer sex;
    private Integer is_delete ;
    
    public String getLibrary_card() {
		return library_card;
	}
	public void setLibrary_card(String library_card) {
		this.library_card = library_card;
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
	public Integer getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(Integer is_delete) {
		this.is_delete = is_delete;
	}
	public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    
}
