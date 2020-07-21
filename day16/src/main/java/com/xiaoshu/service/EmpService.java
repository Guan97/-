package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.EmpMapper;
import com.xiaoshu.entity.Emp;
import com.xiaoshu.entity.EmpVo;

@Service

public class EmpService {

	@Autowired
	private EmpMapper empMapper;

	public PageInfo<EmpVo> findEmpPage(EmpVo empVo, Integer pageNum, Integer pageSize, String ordername, String order) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<EmpVo> elist=empMapper.findEmpPage(empVo);
		return new PageInfo<>(elist);
	}

	public Emp existUserWithUserName(Emp emp) {
		// TODO Auto-generated method stub
		return empMapper.existUserWithUserName(emp);
	}

	public void updateEmp(Emp emp) {
		// TODO Auto-generated method stub
		empMapper.updateEmp(emp);
	}

	public void addEmp(Emp emp) {
		// TODO Auto-generated method stub
		empMapper.addEmp(emp);
	}

	public void deleteEmp(int parseInt) {
		// TODO Auto-generated method stub
		empMapper.deleteEmp(parseInt);
	}

	public List<EmpVo> findEmpAll(EmpVo empVo) {
		// TODO Auto-generated method stub
		return empMapper.findEmpPage(empVo);
	}

	public List<EmpVo> echartsEmp() {
		// TODO Auto-generated method stub
		return empMapper.echartsEmp();
	}
}
