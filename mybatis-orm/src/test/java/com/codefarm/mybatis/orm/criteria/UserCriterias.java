package com.codefarm.mybatis.orm.criteria;

import java.util.Date;

import com.codefarm.mybatis.orm.annotations.Criteria;
import com.codefarm.mybatis.orm.annotations.Criterias;
import com.codefarm.mybatis.orm.annotations.OrderBy;
import com.codefarm.mybatis.orm.enums.Operator;
import com.codefarm.mybatis.pagination.Pagable;

@Criterias
public class UserCriterias extends Pagable {
	@OrderBy(orderby = "id desc,name asc")
	private String orderby;
	@Criteria(column = "id", operator = Operator.GREATER)
	private Long userid;

	@Criteria(column = "username")
	private String username = null;

	@Criteria(column = "id")
	private Long[] userIds;

	@Criteria(column = "username")
	private String[] userNames=null;

	public String[] getUserNames() {
		return userNames;
	}

	public void setUserNames(String[] userNames) {
		this.userNames = userNames;
	}

	@Criteria(column = "birthday", operator = Operator.LESS)
	private Date birthDay;

	@Criteria(column = "created", operator = Operator.LESS)
	private Date created;

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long[] getUserIds() {
		return userIds;
	}

	public void setUserIds(Long[] userIds) {
		this.userIds = userIds;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

}
