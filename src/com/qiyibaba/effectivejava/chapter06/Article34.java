package com.qiyibaba.effectivejava.chapter06;

import java.util.Collection;

public class Article34 {

	interface Operation {
		double apply(double x, double y);
	}

	public enum BasicOperation implements Operation {
		PLUS("+") {
			public double apply(double x, double y) {
				return x + y;
			}
		},
		MINUS("-") {
			public double apply(double x, double y) {
				return x - y;
			}
		},
		TIMES("*") {
			public double apply(double x, double y) {
				return x * y;
			}
		},
		DEVIDE("/") {
			public double apply(double x, double y) {
				return x / y;
			}
		};

		private final String symbol;

		private BasicOperation(String symbol) {
			this.symbol = symbol;
		}

		@Override
		public String toString() {
			return this.symbol;
		}
	}

	public enum ExtendedOperation implements Operation {
		EXP("^") {
			public double apply(double x, double y) {
				return Math.pow(x, y);
			}
		},
		REMAINDER("%") {
			public double apply(double x, double y) {
				return x % y;
			}
		};

		private final String symbol;

		private ExtendedOperation(String symbol) {
			this.symbol = symbol;
		}

		@Override
		public String toString() {
			return this.symbol;
		}
	}

	// 泛型方法
	public static <T extends Enum<T> & Operation> void test1(Class<T> opSet, double x, double y) {
		// 此方法返回一个包含包括由它们声明顺序在Class对象中的值表示的数组，或null如果此Class对象不表示枚举类型的枚举类
		for (Operation o : opSet.getEnumConstants()) {
			System.out.printf("%f %s %f = %f%n", x, o, y, o.apply(x, y));
		}
	}

	public static void test2(Collection<? extends Operation> opSet, double x, double y) {
		for (Operation o : opSet) {
			System.out.printf("%f %s %f = %f%n", x, o, y, o.apply(x, y));
		}
	}

	public static void main(String[] args) {
		double x = 3.6;
		double y = 1.2;

		test1(ExtendedOperation.class, x, y);
	}
}
