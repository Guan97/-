package com.xiaoshu.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class EmpVo extends Emp{

	private String dname;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startbirthday;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endbirthday;
	
	private Integer num;
	
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Date getStartbirthday() {
		return startbirthday;
	}

	public void setStartbirthday(Date startbirthday) {
		this.startbirthday = startbirthday;
	}

	public Date getEndbirthday() {
		return endbirthday;
	}

	public void setEndbirthday(Date endbirthday) {
		this.endbirthday = endbirthday;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}
	
}
