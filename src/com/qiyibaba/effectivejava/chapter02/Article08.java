package com.qiyibaba.effectivejava.chapter02;

/**
 * 如何覆写equal方法和hashcode方法
 * 
 * @author 10210074
 *
 */
public class Article08 {

	// 如果类不可变，并且计算散列码开销很大，则考虑散列码缓存
	private volatile int hashCode;

	private final int param1;

	private final int param2;

	public Article08(int param1, int param2) {
		this.param1 = param1;
		this.param2 = param2;
		this.hashCode = hashCode();
	}

	// 需要遵守的规定：
	// 自反性(x.equals(x)==true)，违反自反性则添加元素至集合的时候会一直添加成功，该集合不包含刚才提交的元素
	// 对称性(x.equals(y)==true,则y.equals(x)==true)
	// 传递性(x.equals(y)==true,y.equals(z)==true,则x.equals(中)==true)
	// 一致性(只要对象未发生变化，则多次调用x.equals(y)返回一致性结果)
	// x.equals(null)返回false
	@Override
	public boolean equals(Object o) {
		// 使用“==”操作符检查参数是否是这个对象的引用，是则返回true
		if (o == this) {
			return true;
		}
		// 使用instanceof操作符检查“参数是否是正确类型”，不是则返回false
		if (!(o instanceof Article08)) {
			return false;
		}
		// 把参数转成正确的类型
		Article08 ao = (Article08) o;
		// 对类中的每个关键域，检查参数中域是否与该对象中的对应域相匹配
		return this.param1 == ao.param1 && this.param2 == ao.param2;
	}

	// 规范：
	// 同一个对象的多次调用，hashcode必须始终如一的返回同一个整数，在一个应用程序的多次执行过程中，每次执行的返回可以不一致
	// 如果2个对象的equals方法相等，则他妈呢的hashcode必须产生同一个结果
	// 如果2个兑现规定equals方法不想的呢过，hashcode不一定要求产生同一个结果
	@Override
	public int hashCode() {
		int result = hashCode;
		if (result == 0) {
			// 把某个非0的常数值，比如17，保存在一个result的变量中
			result = 17;
			// 计算散列码：
			// 1.boolean=(f ? 0 : 1)
			// 2.byte，char，short，int=int(f)
			// 3.long=(int)(f^(f>>>32))
			// 4.float=Float.floatToIntBits(f)
			// 5.double=Double.doubleToLongBits(f)==>3
			// 6.引用，递归调用
			// 7.数组，每个元素处理，或者使用Arrays.hashCode()方法
			result = 31 * result + this.param1; // 2
			result = 31 * result + this.param2; // 2
			// 为什么选择31？奇素数，如果乘数是偶数，并且乘法溢出会信息丢失。同时，31有个很好的特性，使用移位运算和减法代替乘法（31*i=(i<<5)-i）
			hashCode = result;
		}
		return result;
	}
}
