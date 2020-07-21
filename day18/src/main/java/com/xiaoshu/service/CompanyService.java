package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoshu.dao.CompanyMapper;
import com.xiaoshu.entity.Company;

@Service
public class CompanyService {

	@Autowired
	private CompanyMapper companyMapper;

	public List<Company> findCompany() {
		// TODO Auto-generated method stub
		return companyMapper.findCompany();
	}

	public Company findByName(String cname) {
		// TODO Auto-generated method stub
		return companyMapper.findByName(cname);
	}
}
