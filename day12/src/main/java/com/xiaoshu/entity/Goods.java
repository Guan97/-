package com.xiaoshu.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Goods {
	private Integer gid;
	
	private String gname;
	
	private String gsex;
	
	private String ghobby;
	
	private String start;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date gbirthday;
	
	private Integer tid;
	
	private Type type;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startbirthday;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endbirthday;

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public String getGsex() {
		return gsex;
	}

	public void setGsex(String gsex) {
		this.gsex = gsex;
	}

	public String getGhobby() {
		return ghobby;
	}

	public void setGhobby(String ghobby) {
		this.ghobby = ghobby;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public Date getGbirthday() {
		return gbirthday;
	}

	public void setGbirthday(Date gbirthday) {
		this.gbirthday = gbirthday;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
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

	@Override
	public String toString() {
		return "Goods [gid=" + gid + ", gname=" + gname + ", gsex=" + gsex + ", ghobby=" + ghobby + ", start=" + start
				+ ", gbirthday=" + gbirthday + ", tid=" + tid + ", type=" + type + ", startbirthday=" + startbirthday
				+ ", endbirthday=" + endbirthday + "]";
	}
	
}
