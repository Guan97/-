package com.xiaoshu.dao;

import java.util.List;

import com.xiaoshu.entity.Type;

public interface TypeMapper {

	List<Type> selectType();

	void addType(Type type);

}
