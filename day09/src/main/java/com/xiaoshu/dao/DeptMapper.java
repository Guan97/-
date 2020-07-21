package com.xiaoshu.dao;

import java.util.List;

import com.xiaoshu.entity.Dept;

public interface DeptMapper {

	List<Dept> findDept();

	Dept findByname(String dname);

	void addDept(Dept dept2);

}
