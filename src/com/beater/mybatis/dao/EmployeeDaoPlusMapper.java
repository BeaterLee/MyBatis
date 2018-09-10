package com.beater.mybatis.dao;

import java.util.List;

import com.beater.mybatis.bean.Employee;

public interface EmployeeDaoPlusMapper {

	Employee getEmployeeByID(Integer id);
	List<Employee> getEmployeesByDeptID(Integer deptID);
	Employee getEmployeeAndDeptByID(Integer id);
}
