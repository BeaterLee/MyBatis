package com.beater.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.beater.mybatis.bean.EmpStatus;
import com.beater.mybatis.bean.Employee;
import com.beater.mybatis.dao.EmployeeDaoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class MyBatisExtendTest {

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
	public void testPageHelper() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSessionFactory().openSession();
			EmployeeDaoMapper employeeDao = sqlSession.getMapper(EmployeeDaoMapper.class);
			PageHelper.startPage(4, 2);
			List<Employee> employees = employeeDao.getEmployees();
			PageInfo<Employee> pageInfo = new PageInfo<>(employees, 3);
			for (Employee employee : employees) {
				System.out.println(employee);
			}
			System.out.println("当前页数" + pageInfo.getPageNum());
			System.out.println("是否为最后一页" + pageInfo.isIsLastPage());
			System.out.println("每页条目数量" + pageInfo.getPageSize());
			System.out.println("下页页码" + pageInfo.getNextPage());
			System.out.println("条目总数" + pageInfo.getTotal());
			int[] nums = pageInfo.getNavigatepageNums();
			for (int i = 0; i < nums.length; i++) {
				System.out.println(nums[i]);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void testBatchUpdate() {
		SqlSession sqlSession = null;
		try {
			// 配置可执行批量操作的sqlSession
			sqlSession = getSqlSessionFactory().openSession(ExecutorType.BATCH);
			EmployeeDaoMapper employeeDao = sqlSession.getMapper(EmployeeDaoMapper.class);
			for (int i = 0; i < 10000; i++) {
				employeeDao.insertEmployee(new Employee(null, "aa", "1", "0@qq.com", null));
			}
			sqlSession.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testEnumUse(){
		EmpStatus login = EmpStatus.LOGIN;
		System.out.println("枚举的索引："+login.ordinal());
		System.out.println("枚举的名字："+login.name());
		
		System.out.println("枚举的状态码："+login.getCode());
		System.out.println("枚举的提示消息："+login.getMsg());
	}
	
	/**
	 * 默认mybatis在处理枚举对象的时候保存的是枚举的名字：EnumTypeHandler
	 * 改变使用：EnumOrdinalTypeHandler：
	 * @throws IOException
	 */
	@Test
	public void testEnum() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try{
			EmployeeDaoMapper mapper = openSession.getMapper(EmployeeDaoMapper.class);
			Employee employee = new Employee(null,"test_enum","1","enum@beater.com");
			//mapper.insertEmployee(employee);
			//System.out.println("保存成功"+employee.getId());
			//openSession.commit();
			Employee empById = mapper.getEmployeeByID(10013);
			System.out.println(empById.getEmpStatus());
		}finally{
			openSession.close();
		}
	}
}
