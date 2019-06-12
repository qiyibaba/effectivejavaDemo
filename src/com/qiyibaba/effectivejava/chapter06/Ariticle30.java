package com.qiyibaba.effectivejava.chapter06;

public class Ariticle30 {

	public enum Day {

		// ��סҪ�÷ֺŽ���
		MONDAY("����һ"), TUESDAY("���ڶ�"), WEDNESDAY("������"), THURSDAY("������"), FRIDAY("������"), SATURDAY("������"), SUNDAY("������");

		private String desc;// ��������

		/**
		 * ˽�й���,��ֹ���ⲿ����
		 * 
		 * @param desc
		 */
		private Day(String desc) {
			this.desc = desc;
		}
	}

	public enum Operation {
		PLUS("+") {
			double apply(double x, double y) {
				return x + y;
			}
		},
		MINUS("-") {
			double apply(double x, double y) {
				return x - y;
			}
		},
		TIMES("*") {
			double apply(double x, double y) {
				return x * y;
			}
		},
		DEVIDE("/") {
			double apply(double x, double y) {
				return x / y;
			}
		};

		private final String symbol;

		private Operation(String symbol) {
			this.symbol = symbol;
		}

		abstract double apply(double x, double y);

		@Override
		public String toString() {
			return this.symbol;
		}
	}

	// ö��Ƕ��
	public enum PayrollDay {
		MONDAY(PayType.WEEKDAY), THESDAY(PayType.WEEKDAY), WEDNESDAY(PayType.WEEKDAY), THURSDAY(
				PayType.WEEKDAY), FRIDAY(PayType.WEEKDAY), SATURDAY(PayType.WEEKEND), SUNDAY(PayType.WEEKEND);

		private final PayType p;

		PayrollDay(PayType p) {
			this.p = p;
		}

		double pay(double hoursWorked, double payRate) {
			return p.pay(hoursWorked, payRate);
		}

		enum PayType {
			WEEKDAY {
				double overtimePay(double hours, double payRate) {
					return hours <= HOURS_PER_SHIFT ? 0 : (hours - HOURS_PER_SHIFT) * payRate * 2;
				}
			},
			WEEKEND {
				double overtimePay(double hours, double payRate) {
					return hours * payRate / 2;
				}
			};
			private final static int HOURS_PER_SHIFT = 8;

			abstract double overtimePay(double hours, double Rate);

			double pay(double hoursWorked, double payRate) {
				double basePay = hoursWorked * payRate;
				return basePay + overtimePay(hoursWorked, payRate);
			}
		}
	}

	public static void main(String[] args) {
		double x = 1.2;
		double y = 3.6;

		for (Operation o : Operation.values()) {
			System.out.printf("%f %s %f = %f%n", x, o, y, o.apply(x, y));
		}
	}
}
