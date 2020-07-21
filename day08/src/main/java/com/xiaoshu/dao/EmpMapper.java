package com.xiaoshu.dao;

import java.util.List;

import com.xiaoshu.entity.Emp;
import com.xiaoshu.entity.EmpVo;

public interface EmpMapper {

	List<EmpVo> findEmpPage(EmpVo empVo);

	Emp existUserWithUserName(Emp emp);

	void updateEmp(Emp emp);

	void addEmp(Emp emp);

	void deleteEmp(int parseInt);

	List<EmpVo> echartsEmp();

	

}
