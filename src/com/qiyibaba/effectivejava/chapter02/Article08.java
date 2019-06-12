package com.qiyibaba.effectivejava.chapter02;

/**
 * ��θ�дequal������hashcode����
 * 
 * @author 10210074
 *
 */
public class Article08 {

	// ����಻�ɱ䣬���Ҽ���ɢ���뿪���ܴ�����ɢ���뻺��
	private volatile int hashCode;

	private final int param1;

	private final int param2;

	public Article08(int param1, int param2) {
		this.param1 = param1;
		this.param2 = param2;
		this.hashCode = hashCode();
	}

	// ��Ҫ���صĹ涨��
	// �Է���(x.equals(x)==true)��Υ���Է��������Ԫ�������ϵ�ʱ���һֱ��ӳɹ����ü��ϲ������ղ��ύ��Ԫ��
	// �Գ���(x.equals(y)==true,��y.equals(x)==true)
	// ������(x.equals(y)==true,y.equals(z)==true,��x.equals(��)==true)
	// һ����(ֻҪ����δ�����仯�����ε���x.equals(y)����һ���Խ��)
	// x.equals(null)����false
	@Override
	public boolean equals(Object o) {
		// ʹ�á�==���������������Ƿ��������������ã����򷵻�true
		if (o == this) {
			return true;
		}
		// ʹ��instanceof��������顰�����Ƿ�����ȷ���͡��������򷵻�false
		if (!(o instanceof Article08)) {
			return false;
		}
		// �Ѳ���ת����ȷ������
		Article08 ao = (Article08) o;
		// �����е�ÿ���ؼ��򣬼����������Ƿ���ö����еĶ�Ӧ����ƥ��
		return this.param1 == ao.param1 && this.param2 == ao.param2;
	}

	// �淶��
	// ͬһ������Ķ�ε��ã�hashcode����ʼ����һ�ķ���ͬһ����������һ��Ӧ�ó���Ķ��ִ�й����У�ÿ��ִ�еķ��ؿ��Բ�һ��
	// ���2�������equals������ȣ��������ص�hashcode�������ͬһ�����
	// ���2�����ֹ涨equals����������ع���hashcode��һ��Ҫ�����ͬһ�����
	@Override
	public int hashCode() {
		int result = hashCode;
		if (result == 0) {
			// ��ĳ����0�ĳ���ֵ������17��������һ��result�ı�����
			result = 17;
			// ����ɢ���룺
			// 1.boolean=(f ? 0 : 1)
			// 2.byte��char��short��int=int(f)
			// 3.long=(int)(f^(f>>>32))
			// 4.float=Float.floatToIntBits(f)
			// 5.double=Double.doubleToLongBits(f)==>3
			// 6.���ã��ݹ����
			// 7.���飬ÿ��Ԫ�ش�������ʹ��Arrays.hashCode()����
			result = 31 * result + this.param1; // 2
			result = 31 * result + this.param2; // 2
			// Ϊʲôѡ��31�������������������ż�������ҳ˷��������Ϣ��ʧ��ͬʱ��31�и��ܺõ����ԣ�ʹ����λ����ͼ�������˷���31*i=(i<<5)-i��
			hashCode = result;
		}
		return result;
	}
}
