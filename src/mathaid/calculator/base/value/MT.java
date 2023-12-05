/**
 * 
 */
package mathaid.calculator.base.value;

import static mathaid.calculator.base.util.Utility.i;
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
 * Static methods for mathematics binary arithmetic on a bounded {@code BigInteger}. Bounded integers are {@code BigInteger}
 * values that behave exactly like their native counterparts therefore can be used to simulate values such as {@code byte}
 * (bound to 8-bits), {@code short} (bound to 16-bits), {@code int} (bound to 32-bits), {@code long} (bound to 64-bits) and also
 * create dyadic bounds such as 128, 256 and so on.<p>All operations are carried out as side effects (instead of the
 * conventional return syntax) on a 2-length {@code BigInteger} array. The format of the array is ordered as follows: <ol>
 * <li>The first (left) operand of the operation. Will contain the result of the operation as the side effect after the method
 * returns.</li> <li>The second operand (may be {@code null} if the operation is intended as unary. Will contain the
 * carry/overflow bit as the side effect after the method returns.</li> </ol> The carry/overflow bit is the least significant
 * bit of the most significant result bits that could not fit into the the result constrained by {@code bound} <p>Unless
 * otherwise stated, all values to be operated on are in decimal format and must be non-null. The results are all their
 * specified format.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 * @email tonyoruovo@gmail.com
 */
public class MT {

	/*
	 * Date: 28 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:50:30 ---------------------------------------------------
	 */
	/**
	 * Private constructor for creating a {@code mathaid.calculator.base.value.MT} object. It is {@code private} because this class
	 * should never be instantiated.
	 */
	private MT() {
	}

	/*
	 * Date: 28 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:51:21 ---------------------------------------------------
	 */
	/**
	 * Converts the element at the first index of the given {@code r} from it's decimal format into the signed and magnitude
	 * equivalent, then stores the result in the same first index, also storing the overflow/carry bit in the second index.
	 * 
	 * @param bound the number of bits (dyadic) to work with. This simulates computer bit registers such as 8, 16, 32, 64, 128, 256,
	 *              512 and so on. This is the bit-length of the value to be converted.
	 * @param r     the array holding the value to be transformed.
	 * 
	 * @throws ArithmeticException if {@code bound < 4}
	 */
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
			r[0] = i(1).shiftLeft(bound - 1).xor(r[0]);
		}
		r[0] = r[0].multiply(i(s));
	}

	/*
	 * Date: 28 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:51:21 ---------------------------------------------------
	 */
	/**
	 * Converts the element at the first index of the given {@code r} from it's decimal format into the 1's complement equivalent,
	 * then stores the result in the same first index, also storing the overflow/carry bit in the second index.
	 * 
	 * @param bound the number of bits (dyadic) to work with. This simulates computer bit registers such as 8, 16, 32, 64, 128, 256,
	 *              512 and so on. This is the bit-length of the value to be converted.
	 * @param r     the array holding the value to be transformed.
	 * 
	 * @throws ArithmeticException if {@code bound < 4}
	 */
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
		r[0] = r[0].multiply(i(s));
	}

	/*
	 * Date: 28 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:51:21 ---------------------------------------------------
	 */
	/**
	 * Converts the element at the first index of the given {@code r} from it's decimal format into the 2's complement equivalent,
	 * then stores the result in the same first index, also storing the overflow/carry bit in the second index.
	 * 
	 * @param bound the number of bits (dyadic) to work with. This simulates computer bit registers such as 8, 16, 32, 64, 128, 256,
	 *              512 and so on. This is the bit-length of the value to be converted.
	 * @param r     the array holding the value to be transformed.
	 * 
	 * @throws ArithmeticException if {@code bound < 4}
	 */
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
			r[0] = r[0].subtract(i(1));
			TC.not(bound, r);
		}
		r[0] = r[0].multiply(i(s));
	}

	/*
	 * Date: 28 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:51:21 ---------------------------------------------------
	 */
	/**
	 * Converts the element at the first index of the given {@code r} from it's decimal format into the Excess-<span
	 * style="font-style:italic">k</span> equivalent, then stores the result in the same first index, also storing the
	 * overflow/carry bit in the second index.
	 * 
	 * @param bound the number of bits (dyadic) to work with. This simulates computer bit registers such as 8, 16, 32, 64, 128, 256,
	 *              512 and so on. This is the bit-length of the value to be converted.
	 * @param r     the array holding the value to be transformed.
	 * 
	 * @throws ArithmeticException if {@code bound < 4}
	 */
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

	/*
	 * Date: 28 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:51:21 ---------------------------------------------------
	 */
	/**
	 * Converts the element at the first index of the given {@code r} from it's decimal format into the negabinary equivalent, then
	 * stores the result in the same first index, also storing the overflow/carry bit in the second index.
	 * 
	 * @param bound the number of bits (dyadic) to work with. This simulates computer bit registers such as 8, 16, 32, 64, 128, 256,
	 *              512 and so on. This is the bit-length of the value to be converted.
	 * @param r     the array holding the value to be transformed.
	 * 
	 * @throws ArithmeticException if {@code bound < 4}
	 */
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

	/*
	 * Date: 28 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:51:21 ---------------------------------------------------
	 */
	/**
	 * Converts the element at the first index of the given {@code r} from it's decimal format into the unsigned binary equivalent,
	 * then stores the result in the same first index, also storing the overflow/carry bit in the second index.
	 * 
	 * @param bound the number of bits (dyadic) to work with. This simulates computer bit registers such as 8, 16, 32, 64, 128, 256,
	 *              512 and so on. This is the bit-length of the value to be converted.
	 * @param r     the array holding the value to be transformed.
	 */
	public static void fromUS(int bound, BigInteger[] r) throws ArithmeticException {
		if (bound < 4)
			return;
		if (r[0].bitLength() > bound) {
			r[0] = clearMSB(r[0], r[0].bitLength() - bound);
			r[1] = r[0].shiftRight(bound).testBit(0) ? i(1) : i(0);
			return;
		}
	}

}
