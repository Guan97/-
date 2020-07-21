package com.xiaoshu.dao;

import java.util.List;

import com.xiaoshu.entity.Person;
import com.xiaoshu.entity.PersonVo;

public interface PersonMapper {

	List<PersonVo> findUserPage(PersonVo personVo);

	void updatePerson(Person person);

	void addPerson(Person person);

	void deleteUser(int parseInt);

	List<PersonVo> echartsPerson();

}
