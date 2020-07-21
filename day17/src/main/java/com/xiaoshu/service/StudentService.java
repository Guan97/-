package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.xiaoshu.dao.StudentMapper;

import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentExample;
import com.xiaoshu.entity.StudentExample.Criteria;


@Service
public class StudentService {

	@Autowired
	private StudentMapper sm;
	
	
	
	public PageInfo<Student> findStudentPage(Student stu, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Student> stuList = sm.selectStudentAndGrade(stu);
		PageInfo<Student> pageInfo = new PageInfo<Student>(stuList);
		return pageInfo;
	}



	public Student existStudentWithStudentName(String sname) {
		StudentExample example = new StudentExample();
		Criteria criteria = example.createCriteria();
		criteria.andSnameEqualTo(sname);
		List<Student> sList = sm.selectByExample(example);
		return sList.isEmpty()?null:sList.get(0);
	}



	public void updateStudent(Student stu) {
		// TODO Auto-generated method stub
		sm.updateByPrimaryKey(stu);
	}



	public void add(Student stu) {
		// TODO Auto-generated method stub
		sm.insert(stu);
	}


	/*// 查询所有
	public List<User> findUser(User t) throws Exception {
		return userMapper.select(t);
	};

	// 数量 
	public int countUser(User t) throws Exception {
		return userMapper.selectCount(t);
	};

	// 通过ID查询
	public User findOneUser(Integer id) throws Exception {
		return userMapper.selectByPrimaryKey(id);
	};

	// 新增
	public void addUser(User t) throws Exception {
		userMapper.insert(t);
	};

	// 修改
	public void updateUser(User t) throws Exception {
		userMapper.updateByPrimaryKeySelective(t);
	};

	// 删除
	public void deleteUser(Integer id) throws Exception {
		userMapper.deleteByPrimaryKey(id);
	};

	// 登录
	public User loginUser(User user) throws Exception {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andPasswordEqualTo(user.getPassword()).andUsernameEqualTo(user.getUsername());
		List<User> userList = userMapper.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	};

	// 通过用户名判断是否存在，（新增时不能重名）
	public User existUserWithUserName(String userName) throws Exception {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(userName);
		List<User> userList = userMapper.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	};

	// 通过角色判断是否存在
	public User existUserWithRoleId(Integer roleId) throws Exception {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andRoleidEqualTo(roleId);
		List<User> userList = userMapper.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	}*/

	

}
