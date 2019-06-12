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
 * @author 10210074 ���ⴴ�������õĶ����Զ�װ�䴴������
 */
public class Article05 {
	public static void main(String[] args) {
		// ���ڶ����sum������װ�����ͣ����һ�´���������
		Long sum = 0l;

		String s1 = getObjInfo();
		for (long i = 0; i < 10000; i++) {
			sum += i;
		}
		System.out.println(sum);
		// ���󴴽���10240-256=9984��
		// Long�Ỻ��-128~127=128*2=256��long���󣬼��տ�ʼ�����256������
		// ����Ҫ�����Ķ������Ϊ10000-128=9972����������������������
		getInfo("java.lang.Long", s1, getObjInfo());
	}

	public static String getObjInfo() {
		RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
		String name = bean.getName();
		int index = name.indexOf('@');
		String pid = name.substring(0, index);
		// ����Ҫ���ֲ���ϵͳ
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
