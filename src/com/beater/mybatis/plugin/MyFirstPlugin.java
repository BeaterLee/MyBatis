package com.beater.mybatis.plugin;

import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

@Intercepts(
	value = 
	{ 
		@Signature(type=StatementHandler.class,method="parameterize",args= {Statement.class}) 
	}
)

public class MyFirstPlugin implements Interceptor {
	
	private Properties properties;
	
	//如果对象时被包装的代理对象，那么调用invoke方法时，实际上是来调用自定义intercept方法来处理目标方法
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println(properties.getProperty("intercept"));
		System.out.println("正在执行：" + invocation.getMethod() + " 参数：" + invocation.getArgs().toString());
		//获取当前对象的元数据
		MetaObject mo = SystemMetaObject.forObject(invocation.getTarget());
		//获取当前的参数对象
		System.out.println("当前参数：" + mo.getValue("parameterHandler.parameterObject"));
		//修改当前参数对象
		mo.setValue("parameterHandler.parameterObject", 2);
		//调用目标方法（@Signature内规定的方法)
		Object result = invocation.proceed();
		//返回目标方法执行后的结果
		return result;
	}

	//四大对象创建时会调用拦截器的plugin方法，target入参就代表四大对象之一
	@Override
	public Object plugin(Object target) {
		// TODO Auto-generated method stub
		System.out.println(properties.getProperty("plugin"));
		System.out.println("正在调用对象：" + target.toString());
		//Plugin.Wrap方法会为@Intercepts中符合@Signature的对象创建代理对象，如果不符合，则返回原对象
		Object wrap = Plugin.wrap(target, this);
		return wrap;
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		//properties可在全局配置中的plugin参数中设置，用于传入一些参数
		this.properties = properties;
	}

}
