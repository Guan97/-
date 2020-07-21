package com.xiaoshu.entity;

import java.util.Date;

import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name="express_person")
public class Person {

	private Integer id;
	private String name;
	private String sex;
	private String trait;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date entrytime;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createtime;
	private Integer cid;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTrait() {
		return trait;
	}
	public void setTrait(String trait) {
		this.trait = trait;
	}
	public Date getEntrytime() {
		return entrytime;
	}
	public void setEntrytime(Date entrytime) {
		this.entrytime = entrytime;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	
	
}
