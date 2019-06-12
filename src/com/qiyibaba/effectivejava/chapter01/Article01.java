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
 * @author 10210074 �����þ�̬����������������� �ŵ㣺 1.��̬�������������� 2.����ÿ�ε��õ�ʱ�򴴽��µĶ���
 *         3.���Է���ԭʼ���͵��κ������Ͷ��� 4.��������������ʵ����ʱ��ʹ�����ø����� ȱ�㣺
 *         1.����������й��еĻ����ܱ����Ĺ��������Ͳ��ܱ����໯ 2.�����뾲̬����ʵ��û��ʲô����
 */
public class Article01 {

	public enum num {
		one, two, three, four, five, six, seven
	};

	@Test
	public void func1() {
		// ����������ת�������ö��󣬲���ÿ�δ����µĶ���,���Ա��ⴴ������Ҫ�Ķ���
		Boolean valueOf = Boolean.valueOf(false);
		// �����ƣ�֪������
		BigInteger probablePrime = BigInteger.probablePrime(10, new Random());
		System.out.println(valueOf);
		System.out.println(probablePrime);
	}

	@Test
	public void func2() {
		EnumSet<num> of = EnumSet.of(num.one, num.two, num.three);
		System.out.println(of.getClass());
		// ���ؽ��ΪRegularEnumSet�࣬�����ʵ����������65��ʹ��RegularEnumSet������65��ʹ��JumboEnumSet
	}

	// ��δһ������ע���������DriverManagerһ������ȡ���ִ���ʵ��
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
