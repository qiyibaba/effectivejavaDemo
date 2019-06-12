package com.qiyibaba.effectivejava.chapter01;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import com.sun.tools.attach.AttachNotSupportedException;

import sun.tools.attach.HotSpotVirtualMachine;

/**
 * 
 * @author 10210074 避免创建不可用的对象：自动装箱创建对象
 */
public class Article05 {
	public static void main(String[] args) {
		// 由于定义的sum类型是装箱类型，检测一下创建的类型
		Long sum = 0l;

		String s1 = getObjInfo();
		for (long i = 0; i < 10000; i++) {
			sum += i;
		}
		System.out.println(sum);
		// 对象创建了10240-256=9984个
		// Long会缓存-128~127=128*2=256个long对象，即刚开始检测有256个对象
		// 则需要创建的对象个数为10000-128=9972个？？？？？？，待分析
		getInfo("java.lang.Long", s1, getObjInfo());
	}

	public static String getObjInfo() {
		RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
		String name = bean.getName();
		int index = name.indexOf('@');
		String pid = name.substring(0, index);
		// 这里要区分操作系统
		HotSpotVirtualMachine machine = null;
		InputStream is = null;
		try {
			machine = (HotSpotVirtualMachine) new sun.tools.attach.WindowsAttachProvider().attachVirtualMachine(pid);
			is = machine.heapHisto("-all");

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int readed;
			byte[] buff = new byte[1024];
			while ((readed = is.read(buff)) > 0)
				baos.write(buff, 0, readed);
			return baos.toString();
		} catch (AttachNotSupportedException | IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				machine.detach();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void getInfo(String sub, String... ss) {
		for (String s : ss) {
			String[] split = s.split("\\n");
			for (String _s : split) {
				if (_s.indexOf(sub) >= 0) {
					System.out.println(_s);
				}
			}
		}
	}
}
