package com.xiaoshu.dao;

import java.util.List;

import com.xiaoshu.entity.Company;

public interface CompanyMapper {

	List<Company> findCompany();

	Company findByName(String cname);

}
