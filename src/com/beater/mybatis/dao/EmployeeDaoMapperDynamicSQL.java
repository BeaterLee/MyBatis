package com.beater.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.beater.mybatis.bean.Employee;

public interface EmployeeDaoMapperDynamicSQL {
	List<Employee> getEmployeeByDynammicSQL(Employee employee);
	List<Employee> getEmployeeByDynammicSQLTrim(Employee employee);
	List<Employee> getEmployeeByDynammicSQLChoose(Employee employee);
	List<Employee> getEmployeeByDynammicSQLForEach(@Param("ids")List<Integer> ids);
	List<Employee> getEmployeeByDynammicSQLInnerParameter(Employee employee);
	void updateEmployeeByDynamicSQL(Employee employee);
	void batchSave(@Param("employees")List<Employee> employee);
}
