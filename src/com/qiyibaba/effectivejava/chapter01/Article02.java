package com.qiyibaba.effectivejava.chapter01;

/**
 * 
 * @author 10210074 
 * 如果类的构造器或者静态工厂中具备多个参数，设计这种类时，Builder模式就是一种不错的选择
 * 先通过某种方式取得构造对象需要的所有参数，再通过这些参数一次性构建这个对象。
 * 比如MyBatis中SqlSessionFactoryBuilder就是通过读取MyBatis的xml配置文件来获取构造SqlSessionFactory所需要的参数的
 */
public class Article02 {

	private final int id;
	private final String name;
	private final int type;
	private final float price;

	private Article02(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.type = builder.type;
		this.price = builder.price;
	}

	public static class Builder {
		private int id;
		private String name;
		private int type;
		private float price;

		public Builder id(int id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder type(int type) {
			this.type = type;
			return this;
		}

		public Builder price(float price) {
			this.price = price;
			return this;
		}

		public Article02 build() {
			return new Article02(this);
		}
	}

	public static void main(String[] args) {
		Article02 a2 = new Article02.Builder().id(10).name("phone").price(100).type(1).build();
		System.out.println(a2);
	}
}
