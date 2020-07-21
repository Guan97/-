package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoshu.dao.DeptMapper;
import com.xiaoshu.entity.Dept;

@Service
public class DeptService {

	
	@Autowired
	private DeptMapper deptMapper;

	public List<Dept> findDept() {
		// TODO Auto-generated method stub
		return deptMapper.findDept();
	}

	public Dept findByname(String dname) {
		// TODO Auto-generated method stub
		Dept dept=deptMapper.findByname(dname);
		if(dept==null){
			Dept dept2=new Dept();
			dept2.setDname(dname);
			deptMapper.addDept(dept2);
			return dept2;
		}
		
		return dept;
	}

	public void addDept(Dept dept) {
		// TODO Auto-generated method stub
		deptMapper.addDept(dept);
	}
}
