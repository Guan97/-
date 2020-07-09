package cn.jiyun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jiyun.mapper.StudentMapper;
import cn.jiyun.pojo.Student;

@Service
@Transactional
public class StudentService {

	@Autowired
	private StudentMapper studentMapper;

	public List<Student> findAll() {
		// TODO Auto-generated method stub
		return studentMapper.findAll();
	}
}
