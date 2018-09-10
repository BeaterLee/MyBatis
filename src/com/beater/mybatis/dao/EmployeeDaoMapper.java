package com.beater.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.beater.mybatis.bean.Employee;

public interface EmployeeDaoMapper {
	
	List<Employee> getEmployees();
	//使用@Param注解对mybatis封装参数的map指定明确的key，否则默认为(param1(0)...paramN(N-1))
	Employee getEmployeeByIDAndLastName(@Param("id")Integer id,@Param("lastName")String lastName);
	
	//既然mybatis使用map封装多参数，那么也可以直接将map传入，自定义参数名与参数值，此处效果与@Param注解相同
	Employee getEmployeeByIDAndLastName(Map<String,Object> paramMap);
	
	//为返回的单条记录封装为一个Map（默认key：列名	默认value：列值）
	Map<String,Object> getMapByID(Integer id);
	
	//为返回的记录封装成值为Employee对象的Map，Map的key使用@MapKey注解指定
	@MapKey("lastName")
	Map<String,Employee> getEmployeesMap();
	
	List<Employee> getEmployeesByLastName(String lastName);

	Employee getEmployeeByID(Integer id);

	boolean insertEmployee(Employee employee);

	long updateEmployee(Employee employee);

	Integer deleteEmployeeByID(Integer id);
}
