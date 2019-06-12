package com.qiyibaba.effectivejava.chapter01;

/**
 * 
 * @author 10210074 使用单元素的枚举类型是实现单例的最佳方法
 */
public enum Article03 {

	OBJECT;

	private Object obj = null;

	private Article03() {
		obj = new Object();
	}

	public Object getObj() {
		return obj;
	}
	
	public static void main(String[] args) {
		for (int i=0;i<10;i++){
			System.out.println(Article03.OBJECT.getObj());
		}
	}
}
