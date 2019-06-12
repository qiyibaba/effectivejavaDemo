package com.qiyibaba.effectivejava.chapter01;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * 
 * @author 10210074 �������ڵĶ������ã� 1.���Լ������ڴ� 2.���� 3.�������ͻص�
 */
public class Article06 {

	public static class Stack1 {

		private Object[] elements;
		private int size = 0;
		private static final int DEFAULT_INITIAL_CAPACITY = 16;

		public Stack1() {
			elements = new Object[DEFAULT_INITIAL_CAPACITY];
		}

		private void ensureCapacity() {
			if (elements.length == size) {
				elements = Arrays.copyOf(elements, 2 * size + 1);
			}
		}

		public void push(Object e) {
			ensureCapacity();
			elements[size++] = e;
		}

		public Object pop() {
			if (size == 0) {
				throw new EmptyStackException();
			}
			return elements[--size];
		}
	}

	public static class Stack2 {

		private Object[] elements;
		private int size = 0;
		private static final int DEFAULT_INITIAL_CAPACITY = 16;

		public Stack2() {
			elements = new Object[DEFAULT_INITIAL_CAPACITY];
		}

		private void ensureCapacity() {
			if (elements.length == size) {
				elements = Arrays.copyOf(elements, 2 * size + 1);
			}
		}

		public void push(Object e) {
			ensureCapacity();
			elements[size++] = e;
		}

		public Object pop() {
			if (size == 0) {
				throw new EmptyStackException();
			}
			Object object = elements[--size];
			elements[size] = null; // ������������
			return object;
		}
	}
	
	public static class abcdefg01{
		
	}
	
	public static class abcdefg02{
		
	}
	
	public static void main(String[] args) {
		// ���ڶ�����2��stack���������ԣ����Ƿ����չ������ö���
		Stack1 s1 = new Stack1();
		s1.push(new abcdefg01());
		s1.pop();
		System.out.println("check first ...");
		Article05.getInfo("abcdefg0", Article05.getObjInfo());
		System.gc();
		System.out.println("check second ...");
		Article05.getInfo("abcdefg0", Article05.getObjInfo());
		
		Stack2 s2 = new Stack2();
		s2.push(new abcdefg02());
		s2.pop();
		System.out.println("check third ...");
		Article05.getInfo("abcdefg0", Article05.getObjInfo());
		System.gc();
		System.out.println("check fourth ...");
		// abcdefg02�����Ѿ������գ�����abcdefg01�������ɴ���
		Article05.getInfo("abcdefg0", Article05.getObjInfo());
	}
}
