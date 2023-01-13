/**
 * 
 */
package mathaid.calculator.base.value;

import static mathaid.calculator.base.value.FloatAid.clearMSB;

import java.math.BigInteger;

/*
 * Date: 20 Nov 2022----------------------------------------------------------- 
 * Time created: 05:15:15---------------------------------------------------  
 * Package: mathaid.calculator.base.util------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: MT.java------------------------------------------------------ 
 * Class name: MT------------------------------------------------ 
 */
/**
 * Static methods for mathematics binary arithmetic on a bounded
 * {@code BigInteger}
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class MT {

	private MT() {
	}

	public static void fromSMR(int bound, BigInteger[] r) throws ArithmeticException {
		if (bound < 4)
			throw new ArithmeticException("bound is less than 4");
		if (r[0].signum() < 0 || r[0].bitLength() > bound) {
			SMR.fromDecimal(bound, r);
			fromSMR(bound, r);
			return;
		}

		boolean isNeg = r[0].bitLength() == bound;
		int s = isNeg ? -1 : 1;
		if (isNeg) {
			r[0] = TC.i(1).shiftLeft(bound - 1).xor(r[0]);
		}
		r[0] = r[0].multiply(TC.i(s));
	}

	public static void fromOC(int bound, BigInteger[] r) throws ArithmeticException {
		if (bound < 4)
			throw new ArithmeticException("bound is less than 4");
		if (r[0].signum() < 0 || r[0].bitLength() > bound) {
			OC.fromDecimal(bound, r);
			fromOC(bound, r);
			return;
		}

		boolean isNeg = r[0].bitLength() == bound;
		int s = isNeg ? -1 : 1;
		if (isNeg)
			TC.not(bound, r);
		r[0] = r[0].multiply(TC.i(s));
	}

	public static void fromTC(int bound, BigInteger[] r) throws ArithmeticException {
		if (bound < 4)
			throw new ArithmeticException("bound is less than 4");
		if (r[0].signum() < 0 || r[0].bitLength() > bound) {
			TC.fromDecimal(bound, r);
			fromEx(bound, r);
			return;
		}

		boolean isNeg = r[0].bitLength() == bound;
		int s = isNeg ? -1 : 1;
		if (isNeg) {
			r[0] = r[0].subtract(TC.i(1));
			TC.not(bound, r);
		}
		r[0] = r[0].multiply(TC.i(s));
	}

	public static void fromEx(int bound, BigInteger[] r) throws ArithmeticException {
		if (bound < 4)
			throw new ArithmeticException("bound is less than 4");
		if (r[0].signum() < 0 || r[0].bitLength() > bound) {
			Ex.fromDecimal(bound, r);
			fromEx(bound, r);
			return;
		}
		BigInteger bias = FloatAid.getTrailingZeros(bound - 1);
		r[0] = r[0].subtract(bias);
	}
	
	public static void fromNB(int bound, BigInteger[] r) throws ArithmeticException {
		if (bound < 4)
			throw new ArithmeticException("bound is less than 4");
		if (r[0].signum() < 0 || r[0].bitLength() > bound) {
			NB.fromDecimal(bound, r);
			fromNB(bound, r);
			return;
		}
		
		final String a = "A";
		final StringBuffer temp = new StringBuffer(a);
		for (int i = 4; i < bound; i += i)
			temp.append(a);
		final BigInteger mask = new BigInteger(temp.toString(), 16);
		r[0] = mask.xor(r[0]).subtract(mask);
	}

	public static void fromUS(int bound, BigInteger[] r) throws ArithmeticException {
		if (bound < 4)
			return;
		if (r[0].bitLength() > bound) {
			r[0] = clearMSB(r[0], r[0].bitLength() - bound);
			r[1] = r[0].shiftRight(bound).testBit(0) ? TC.i(1) : TC.i(0);
			return;
		}
	}

}
