package com.qiyibaba.effectivejava.chapter01;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverAction;
import java.sql.SQLException;
import java.util.EnumSet;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Test;

/**
 * 
 * @author 10210074 考虑用静态工厂方法替代构造器 优点： 1.静态工厂方法有名称 2.不必每次调用的时候创建新的对象
 *         3.可以返回原始类型的任何子类型对象 4.创建参数化类型实例的时候，使代码变得更简单那 缺点：
 *         1.类如果不含有共有的或者受保护的构造器，就不能被子类化 2.他们与静态方法实际没有什么区别
 */
public class Article01 {

	public enum num {
		one, two, three, four, five, six, seven
	};

	@Test
	public void func1() {
		// 类似于类型转换，复用对象，不用每次创建新的对象,可以避免创建不必要的对象
		Boolean valueOf = Boolean.valueOf(false);
		// 有名称，知道含义
		BigInteger probablePrime = BigInteger.probablePrime(10, new Random());
		System.out.println(valueOf);
		System.out.println(probablePrime);
	}

	@Test
	public void func2() {
		EnumSet<num> of = EnumSet.of(num.one, num.two, num.three);
		System.out.println(of.getClass());
		// 返回结果为RegularEnumSet类，即如果实例个数少于65个使用RegularEnumSet，超过65个使用JumboEnumSet
	}

	// 如何搭建一个服务注册机，类似DriverManager一样，提取部分代码实现
	public static class DriverManager {
		
		// Driver is the Service privider interface
		private final static CopyOnWriteArrayList<Driver> registeredDrivers = new CopyOnWriteArrayList<>();

		// Provider registration API
		public static synchronized void registerDriver(java.sql.Driver driver, DriverAction da) throws SQLException {
			if (driver != null) {
				registeredDrivers.addIfAbsent(driver);
			} else {
				throw new NullPointerException();
			}
		}

		// Service access API
		public static Connection getConnection(String url, java.util.Properties info, Class<?> caller)
				throws SQLException {

			ClassLoader callerCL = caller != null ? caller.getClassLoader() : null;
			synchronized (DriverManager.class) {
				if (callerCL == null) {
					callerCL = Thread.currentThread().getContextClassLoader();
				}
			}

			if (url == null) {
				throw new SQLException("The url cannot be null", "08001");
			}

			SQLException reason = null;

			for (Driver driver : registeredDrivers) {
				try {
					Connection con = driver.connect(url, info);
					if (con != null) {
						return (con);
					}
				} catch (SQLException ex) {
					if (reason == null) {
						reason = ex;
					}
				}
			}

			if (reason != null) {
				throw reason;
			}

			throw new SQLException("No suitable driver found for " + url, "08001");
		}
	}
}
