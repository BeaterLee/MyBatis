package com.beater.mybatis.dao;

import org.apache.ibatis.annotations.Select;

import com.beater.mybatis.bean.Employee;

public interface EmployeeDaoMapperAnnotation {
	
	@Select("select * from employee where id= #{id}")
	Employee getEmployeeByID(Integer id);
}
