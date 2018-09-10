package com.beater.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.beater.mybatis.bean.Department;
import com.beater.mybatis.bean.Employee;
import com.beater.mybatis.dao.DepartmentDaoMapper;
import com.beater.mybatis.dao.EmployeeDaoMapperDynamicSQL;
import com.beater.mybatis.dao.EmployeeDaoPlusMapper;

public class MyBatisTestPlus {

	private SqlSessionFactory getSqlSessionFactory() {
		String resource = "mybatis-config.xml";
		InputStream inputStream = null;
		SqlSessionFactory sqlSessionFactory = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			return sqlSessionFactory;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Test
	public void testResultMap() {
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		try {
			EmployeeDaoPlusMapper employeeDaoPlusMapper = sqlSession.getMapper(EmployeeDaoPlusMapper.class);
			//Employee employee = employeeDaoPlusMapper.getEmployeeByID(1);
			Employee employee = employeeDaoPlusMapper.getEmployeeAndDeptByID(6);
			//Employee employee = employeeDaoPlusMapper.getEmployeeByIDWithStep(1);
			System.out.println(employee);
			//DepartmentDaoMapper departmentDaoMapper = sqlSession.getMapper(DepartmentDaoMapper.class);
			//Department department = departmentDaoMapper.getDepartmentAndEmployeeListByID(1);
			//Department department = departmentDaoMapper.getDepartmentAndEmployeeListByIDByStep(1);
			//System.out.println(department);
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testDynamicSQL() {
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		try {
			EmployeeDaoMapperDynamicSQL employeeDaoMapperDynamicSQL = sqlSession.getMapper(EmployeeDaoMapperDynamicSQL.class);
			//test if/where标签
			//List<Employee> list = employeeDaoMapperDynamicSQL.getEmployeeByDynammicSQL(new Employee(null,"%e%",null,null));
			//test trim标签
			//List<Employee> list = employeeDaoMapperDynamicSQL.getEmployeeByDynammicSQLTrim(new Employee(null,"%e%",null,null));
			//test choose标签
			//List<Employee> list = employeeDaoMapperDynamicSQL.getEmployeeByDynammicSQLChoose(new Employee(null,null,null,null));
			List<Employee> list = employeeDaoMapperDynamicSQL.getEmployeeByDynammicSQLForEach(Arrays.asList(1,2));
			System.out.println(list);
			//test set标签
			//employeeDaoMapperDynamicSQL.updateEmployeeByDynamicSQL(new Employee(1,"tom",null,null));
			//手动提交事务
			//sqlSession.commit();
		}finally {
			sqlSession.close();
		}
	}
	
	//测试批量插入
	@Test
	public void testBatchSave() {
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		try {
			EmployeeDaoMapperDynamicSQL employeeDaoMapperDynamicSQL = sqlSession.getMapper(EmployeeDaoMapperDynamicSQL.class);
			List<Employee> employees = new ArrayList<>();
			employees.add(new Employee(null, "smith", "1", "smith@qq.com", new Department(1)));
			employees.add(new Employee(null, "marry", "1", "marry@qq.com", new Department(1)));
			employeeDaoMapperDynamicSQL.batchSave(employees);
			sqlSession.commit();
		}finally {
			sqlSession.close();
		}
	}
	
	//测试mybatis内置参数
	@Test
	public void testInnerParameter() {
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		try {
			EmployeeDaoMapperDynamicSQL employeeDaoMapperDynamicSQL = sqlSession.getMapper(EmployeeDaoMapperDynamicSQL.class);
			List<Employee> list = employeeDaoMapperDynamicSQL.getEmployeeByDynammicSQLInnerParameter(null);
			System.out.println(list);
		}finally {
			sqlSession.close();
		}
	}
	
}
