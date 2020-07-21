package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiaoshu.dao.TypeMapper;
import com.xiaoshu.entity.Type;

@Service
@Transactional
public class TypeService {

	@Autowired
	private TypeMapper tm;
	
	public List<Type> selectType() {
		List<Type> typeList = tm.selectType();
		return typeList;
	}

	public void addType(Type type) {
		tm.addType(type);
	}

}
