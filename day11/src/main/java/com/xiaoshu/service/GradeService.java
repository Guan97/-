package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.GradeMapper;
import com.xiaoshu.dao.RoleMapper;
import com.xiaoshu.entity.Grade;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.RoleExample;
import com.xiaoshu.entity.RoleExample.Criteria;

@Service
public class GradeService {
	
	@Autowired
	private GradeMapper gm;

	public List<Grade> findgrade() {
		// TODO Auto-generated method stub
		return gm.selectAll();
	}
	
	


}
