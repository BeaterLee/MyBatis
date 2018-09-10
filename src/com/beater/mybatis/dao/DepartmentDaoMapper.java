package com.beater.mybatis.dao;

import com.beater.mybatis.bean.Department;

public interface DepartmentDaoMapper {

	Department getDepartmentByID(Integer id);
	Department getDepartmentAndEmployeeListByID(Integer id);
	Department getDepartmentAndEmployeeListByIDByStep(Integer deptID);
}
