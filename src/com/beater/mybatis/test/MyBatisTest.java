package com.beater.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.beater.mybatis.bean.Employee;
import com.beater.mybatis.dao.EmployeeDaoMapper;
import com.beater.mybatis.dao.EmployeeDaoMapperAnnotation;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class MyBatisTest {

	/**
	 * 1、接口式编程
	 * 	原生：		Dao		====>  DaoImpl
	 * 	mybatis：	Mapper	====>  xxMapper.xml
	 * 
	 * 2、SqlSession代表和数据库的一次会话；用完必须关闭；
	 * 3、SqlSession和connection一样她都是非线程安全。每次使用都应该去获取新的对象。
	 * 4、mapper接口没有实现类，但是mybatis会为这个接口生成一个代理对象。
	 * 		（将接口和xml进行绑定）
	 * 		EmployeeMapper empMapper =	sqlSession.getMapper(EmployeeMapper.class);
	 * 5、两个重要的配置文件：
	 * 		mybatis的全局配置文件：包含数据库连接池信息，事务管理器信息等...系统运行环境信息
	 * 		sql映射文件：保存了每一个sql语句的映射信息：
	 * 					将sql抽取出来。	
	 */
	
	/**
	 * 1、根据xml配置文件（全局配置文件）创建一个SqlSessionFactory对象 有数据源一些运行环境信息
	 * 2、sql映射文件；配置了每一个sql，以及sql的封装规则等。 3、将sql映射文件注册在全局配置文件中 4、写代码：
	 * 1）、根据全局配置文件得到SqlSessionFactory； 2）、使用sqlSession工厂，获取到sqlSession对象使用他来执行增删改查
	 * 一个sqlSession就是代表和数据库的一次会话，用完关闭
	 * 3）、使用sql的唯一标志来告诉MyBatis执行哪个sql。sql都是保存在sql映射文件中的。
	 * 
	 * @throws IOException
	 */
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
	
	// 2、获取sqlSession实例，能直接执行已经映射的sql语句
	// sql的唯一标识：statement Unique identifier matching the statement to use.
	// 执行sql要用的参数：parameter A parameter object to pass to the statement.
	@Test
	public void testSelectOne() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSessionFactory().openSession();
			Employee employee = sqlSession.selectOne("com.beater.mybatis.dao.EmployeeDaoMapper.getEmployeeByID", 1);
			System.out.println(employee);
		} finally {
			sqlSession.close();
		}
	}
	
	
	//测试SqlSession.getMapper()
	@Test
	public void testMapper() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSessionFactory().openSession();
			// 获取接口的实现类对象，mybatis会为接口自动创建一个代理对象，代理对象调用增删改查方法
			EmployeeDaoMapper employeeDao = sqlSession.getMapper(EmployeeDaoMapper.class);
			System.out.println(employeeDao);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			sqlSession.close();
		}
	}
	
	//测试利用注解注册的接口映射(无需xml映射文件)
	@Test
	public void testAnnotationMapper() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSessionFactory().openSession();
			// 获取接口的实现类对象，mybatis会为接口自动创建一个代理对象，代理对象调用增删改查方法
			EmployeeDaoMapperAnnotation employeeDao = sqlSession.getMapper(EmployeeDaoMapperAnnotation.class);
			System.out.println(employeeDao.getEmployeeByID(1));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			sqlSession.close();
		}
	}
	
	/**
	 * 测试增删改
	 * 1、mybatis允许增删改直接定义以下类型返回值
	 * 		Integer、Long、Boolean、void
	 * 2、我们需要手动提交数据
	 * 		sqlSessionFactory.openSession();===》手动提交
	 * 		sqlSessionFactory.openSession(true);===》自动提交
	 * @throws IOException 
	 */
	@Test
	public void testCUD() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSessionFactory().openSession();
			// 获取接口的实现类对象，mybatis会为接口自动创建一个代理对象，代理对象调用增删改查方法
			EmployeeDaoMapper employeeDao = sqlSession.getMapper(EmployeeDaoMapper.class);
			//	新增
			Employee employee = new Employee(null, "jerry", "1", "aa@qq.com",null);
			boolean flag = employeeDao.insertEmployee(employee);
			System.out.println(flag);
			System.out.println(employee.getId());
			//	更新
			// 	long flag = employeeDao.updateEmployee(new Employee(2,"mike", "0", "bb@qq.com"));
			//	System.out.println(flag);
			// 	删除
			//	Integer flag = employeeDao.deleteEmployeeByID(3);
			//	System.out.println(flag);
			//	手动提交数据
			sqlSession.commit();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			sqlSession.close();
		}
	}
	
	//测试@Param注解对多参数的作用
	@Test
	public void testParamAnnotation() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSessionFactory().openSession();
			EmployeeDaoMapper employeeDaoMapper = sqlSession.getMapper(EmployeeDaoMapper.class);
			Employee employee = employeeDaoMapper.getEmployeeByIDAndLastName(1, "Tom");
			System.out.println(employee);
		} finally {
			sqlSession.close();
		}
	}
	
	//测试主动提交一个参数map
	@Test
	public void testParamMap() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSessionFactory().openSession();
			EmployeeDaoMapper employeeDaoMapper = sqlSession.getMapper(EmployeeDaoMapper.class);
			Map<String,Object> paramMap = new HashMap<>();
			paramMap.put("id", "1");
			paramMap.put("lastName", "Tom");
			Employee employee = employeeDaoMapper.getEmployeeByIDAndLastName(paramMap);
			System.out.println(employee);
		} finally {
			sqlSession.close();
		}
	}
	
	//测试批量查询
	@Test
	public void testGetEmployees() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSessionFactory().openSession();
			EmployeeDaoMapper employeeDaoMapper = sqlSession.getMapper(EmployeeDaoMapper.class);
			List<Employee> list = employeeDaoMapper.getEmployeesByLastName("%m%");
			for(Employee employee : list) {
				System.out.println(employee);
			}
		} finally {
			sqlSession.close();
		}
	}
	
	//测试返回Map
	@Test
	public void testGetEmployeesMap() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSessionFactory().openSession();
			EmployeeDaoMapper employeeDaoMapper = sqlSession.getMapper(EmployeeDaoMapper.class);
			Map<String,Object> map = employeeDaoMapper.getMapByID(1);
			System.out.println(map);
			Map<String,Employee> empMap = employeeDaoMapper.getEmployeesMap();
			System.out.println(empMap);
		} finally {
			sqlSession.close();
		}
	}
}
