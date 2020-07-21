package com.xiaoshu.service;

import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.PersonMapper;
import com.xiaoshu.entity.Person;
import com.xiaoshu.entity.PersonVo;

@Service
public class PersonService {

	@Autowired
	private PersonMapper personMapper;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private Destination destination;

	public PageInfo<PersonVo> findUserPage(PersonVo personVo, Integer pageNum, Integer pageSize, String ordername,
			String order) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<PersonVo> list=personMapper.findUserPage(personVo);
		return new PageInfo<>(list);
	}

	public void updatePerson(Person person) {
		// TODO Auto-generated method stub
		personMapper.updatePerson(person);
	}

	public void addPerson(Person person) {
		// TODO Auto-generated method stub
		personMapper.addPerson(person);
		sendPersonMQ(JSON.toJSONString(person));
		System.out.println(JSON.toJSONString(person));
	}
	
	public void sendPersonMQ(final String person){
		jmsTemplate.send(destination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				return session.createTextMessage(person);
			}
		});
	}

	public void deleteUser(int parseInt) {
		// TODO Auto-generated method stub
		personMapper.deleteUser(parseInt);
	}

	public List<PersonVo> echartsPerson() {
		// TODO Auto-generated method stub
		return personMapper.echartsPerson();
	}

	public List<PersonVo> findAll(PersonVo personVo) {
		// TODO Auto-generated method stub
		return personMapper.findUserPage(personVo);
	}
}
