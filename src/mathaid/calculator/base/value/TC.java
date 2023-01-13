/**
 * 
 */
package mathaid.calculator.base.value;

import static mathaid.calculator.base.value.FloatAid.clearMSB;
import static mathaid.calculator.base.value.FloatAid.getAllOnes;

import java.math.BigInteger;
import java.util.Comparator;

/*
 * Date: 20 Nov 2022----------------------------------------------------------- 
 * Time created: 01:31:43---------------------------------------------------  
 * Package: mathaid.calculator.base.util------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: TC.java------------------------------------------------------ 
 * Class name: TC------------------------------------------------ 
 */
/**
 * Static methods for 2's complement arithmetic on a bounded {@code BigInteger}
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class TC {
	private TC() {
	}

	static Comparator<BigInteger> getComparator(int bound) {
		return new Comparator<BigInteger>() {
			@Override
			public int compare(BigInteger x, BigInteger y) {
				BigInteger[] r = { x, i(0), };
				fromDecimal(bound, r);
				x = r[0];
				r[0] = y;
				fromDecimal(bound, r);
				y = r[0];
				
				r[0] = x;
				MT.fromTC(bound, r);
				x = r[0];
				r[0] = y;
				MT.fromTC(bound, r);
				y = r[0];
				return x.compareTo(y);
			}
		};
	}

	/*
	 * Date: 20 Nov 2022-----------------------------------------------------------
	 * Time created: 15:39:18--------------------------------------------
	 */
	/**
	 * @param x
	 * @param y
	 * @param bound
	 * @return
	 */
	public static int compare(BigInteger x, BigInteger y, int bound) {
		return getComparator(bound).compare(x, y);
	}

	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 06:48:40--------------------------------------------
	 */
	/**
	 * Quick and handy method to initialise a {@code BigInteger} using
	 * {@link BigInteger#valueOf(long)}.
	 * 
	 * @param i an {@code int}
	 * @return a {@code BigInteger} equivalent of the argument
	 */
	static BigInteger i(int i) {
		return BigInteger.valueOf(i);
	}

	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 06:50:00--------------------------------------------
	 */
	/**
	 * Quick and handy method to initialise a {@code BigInteger} using
	 * {@link BigInteger#valueOf(long)}.
	 * 
	 * @param i a {@code long}
	 * @return a {@code BigInteger} equivalent of the argument
	 */
	static BigInteger i(long l) {
		return BigInteger.valueOf(l);
	}

	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 06:50:35--------------------------------------------
	 */
	/**
	 * Quick and handy method to initialise a {@code BigInteger} using
	 * {@link BigInteger#BigInteger(String, int)}.
	 * 
	 * @param i     a {@code String}
	 * @param radix the radix of the argument
	 * @return a {@code BigInteger} equivalent of the argument
	 */
	static BigInteger i(String i, int radix) {
		return new BigInteger(i, radix);
	}

	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 06:52:08--------------------------------------------
	 */
	/**
	 * Quick and handy method to initialise a {@code BigInteger} using a binary
	 * string.
	 * 
	 * @param i a binary {@code String}
	 * @return a {@code BigInteger} equivalent of the argument
	 */
	static BigInteger i(String i) {
		return new BigInteger(i, 2);
	}

	public static void add(int bound, BigInteger[] r) {// this includes the carry bit
		r[0] = r[0].add(r[1]);
		fromDecimal(bound, r);
	}

	public static void subtract(int bound, BigInteger[] r) {// this includes the carry bit
		r[0] = r[0].subtract(r[1]);
		fromDecimal(bound, r);
	}

	public static void multiply(int bound, BigInteger[] r) {// this includes the carry bit
		r[0] = r[0].multiply(r[1]);
		fromDecimal(bound, r);
	}

	public static void divide(int bound, BigInteger[] r) {// this includes the carry bit
		r[0] = r[0].divide(r[1]);
		fromDecimal(bound, r);
	}

	public static void xor(int bound, BigInteger[] r) {// this includes the carry bit
		r[0] = r[0].xor(r[1]);
		fromDecimal(bound, r);
	}

	public static void or(int bound, BigInteger[] r) {// this includes the carry bit
		r[0] = r[0].or(r[1]);
		fromDecimal(bound, r);
	}

	public static void and(int bound, BigInteger[] r) {// this includes the carry bit
		r[0] = r[0].and(r[1]);
		fromDecimal(bound, r);
	}

	public static void nand(int bound, BigInteger[] r) {// this includes the carry bit
		r[0] = r[0].xor(r[1]).not();
//		not(bound, r);
		fromDecimal(bound, r);
	}

	public static void xnor(int bound, BigInteger[] r) {// this includes the carry bit
//		BigInteger x = r[0], y = r[1];
//		BigInteger[] not1 = { x, r[2] };
//		BigInteger[] not2 = { y, r[2] };
//		and(bound, r);
//		not(bound, not1);
//		not(bound, not2);
//		BigInteger[] right = { not1[0], not2[0], r[2] };
//		and(bound, right);
//		r = new BigInteger[] { right[0], right[1], right[2] };
//		or(bound, r);
		r[0] = r[0].and(r[1]).or(r[0].not().and(r[1].not()));
		r[1] = r[2];
		fromDecimal(bound, r);
		
//		Couple<BigInteger, BigInteger> l = and(x, y, c, bound);
//		Couple<BigInteger, BigInteger> not1 = not(of(x, l.get2nd()), bound);
//		Couple<BigInteger, BigInteger> not2 = not(of(y, not1.get2nd()), bound);
//		Couple<BigInteger, BigInteger> r = and(not1.get(), not2.get(), not2.get2nd(), bound);
//		return or(l.get(), r.get(), r.get2nd(), bound);
	}

	public static void rem(int bound, BigInteger[] r) {//this includes the carry bit
		r[0] = r[0].remainder(r[1]);
		fromDecimal(bound, r);
	}

	public static void nor(int bound, BigInteger[] r) {//this includes the carry bit
//		or(bound, r);
		r[0] = r[0].or(r[1]).not();
		r[1] = r[2];
//		not(bound, r);
		fromDecimal(bound, r);
	}

	public static void negate(int bound, BigInteger[] r) {
		r[0] = r[0].negate();
		fromDecimal(bound, r);
	}

	private static void sl(int s, int b, BigInteger[] r) {
		if (Math.abs(s) > b) {
			sl(s % b, b, r);
			return;
		}
		/*
		 * simulate java's irregular behaviour
		 */
		if (s < 0) {
			if (multiplicationHasOverflown(b, 2))
				throw new ArithmeticException("operation caused an oveflow in bound");
			r[0] = r[0].shiftLeft(b);
			sr(-s, b * 2, false, r);
			sl(0, b, r);
			return;
		}
		fromDecimal(b, r);
		if (r[0].signum() == 0 || s % b == 0)
			return;
		r[0] = r[0].shiftLeft(s);
		if (r[0].bitLength() > b) {
			r[0] = clearMSB(r[0], r[0].bitLength() - b);
			r[1] = r[0].shiftRight(b);
		}
	}

	private static void sr(int s, int b, boolean a, BigInteger[] r) {
		if (Math.abs(s) > b) {
			sr(s % b, b, a, r);
			return;
		}
		/*
		 * simulate java's irregular behaviour is shift < 0
		 */
		if (s < 0) {
			if (multiplicationHasOverflown(b, 2))
				throw new ArithmeticException("operation caused an oveflow in bound");
			sl(-s, b * 2, r);
			r[0] = r[0].shiftRight(b);
			sr(0, b, false, r);
			return;
		}
		fromDecimal(b, r);
		if (r[0].signum() == 0 || s % b == 0)
			return;

		boolean isNeg = r[0].testBit(b - 1);
		r[0] = r[0].shiftRight(s);
		if (isNeg && a && r[0].bitLength() < b)
			r[0] = getAllOnes(b - r[0].bitLength()).shiftLeft(r[0].bitLength()).or(r[0]);
	}

	private static void csl(int s, int b, boolean c, BigInteger[] r) {
		if (s < 0) {// simulate an irregular behaviour. this is non standard
			if (multiplicationHasOverflown(b, 2))
				throw new ArithmeticException("operation caused an oveflow in bound");
			r[0] = r[0].shiftLeft(b);
			csr(-s, b * 2, c, r);
			csl(0, b, c, r);
			return;
		}

		// prevent signed values
		fromDecimal(b, r);

		// make sure that shift < bound
		if (s > b) {
			csl(s % b, b, c, r);
			return;
		}
		BigInteger n = r[0];
		/*
		 * carrying does not prevent the shifting as it does in other standard circular
		 * shift implementation. In here, the shift still continues as a normal rotate
		 * and any bit "pushed" into the "carry zone" will be returned as the carry bit
		 */
		if (c) {
			// make room for the carry bit
			n = n.shiftLeft(1).or(r[1].testBit(0) ? i(1) : i(0));
			// make the sign bit the carry bit of the method call, do a usual rotate by
			// increasing the bound by 1 to accurately calculate the resulting carry bit
			// and also to prevent infinite recursion
			r[1] = n.shiftLeft(s).testBit(b + 1) ? i(1) : i(0);
			csl(s, b + 1, false, r);
			// remove the extra bound
			r[0] = r[0].shiftRight(1);
			// do recursion to make sure that val's bigInteger is within bound
			csl(0, b, false, r);
			return;
		}

		// If we are not performing a carry, then we are here

		// do a normal left shift
		n = n.shiftLeft(s);
		// the carry bit(s)
		BigInteger carry = r[1];

		// rotate the bits that "fell off" the left side of the bound. Do this by
		// appending them to the right to create that rotate feel
		if (n.bitLength() > b) {
			BigInteger mask = getAllOnes(n.bitLength() - b).shiftLeft(b).and(n).shiftRight(b);
			n = clearMSB(n, n.bitLength() - b).or(mask);
			carry = n.shiftRight(b);
		}

		r[0] = n;
		r[1] = carry;
	}

	private static void csr(int s, int b, boolean c, BigInteger[] r) {
		if (s < 0) {// simulate an irregular behaviour. this is non standard
			if (multiplicationHasOverflown(b, 2))
				throw new ArithmeticException("bound is too big");
			csl(-s, b * 2, c, r);
			r[0] = r[0].shiftRight(b);
			csr(0, b, c, r);
			return;
		}
		// prevent signed values
		fromDecimal(b, r);
		// make sure that shift < bound
		if (s > b) {
			csr(s % b, b, c, r);
			return;
		}
		BigInteger n = r[0];
		/*
		 * carrying does not prevent the shifting as it does in other standard circular
		 * shift implementation. In here, the shift still continues as a normal rotate
		 * and any bit "pushed" into the "carry zone" will be returned as the carry bit
		 */
		if (c) {
			// append the carry bit to the least significant end
			n = n.shiftLeft(1).or(r[1].testBit(0) ? i(1) : i(0));
			// do a usual rotate by
			// increasing the bound by 1 to accurately calculate the resulting carry bit
			// and also to prevent infinite recursion
			r[0] = n;
			csr(s, b + 1, false, r);
			// make the least significant bit the carry bit
			r[0] = r[0].shiftRight(1);
			r[1] = r[0].testBit(0) ? i(1) : i(0);
			// do recursion to make sure that val's bigInteger is within bound
			csr(0, b, false, r);
			return;
		}

		// If we are not performing a carry, then we are here

		// save n
		BigInteger doppleganger = n;
		// do a normal right shift
		n = n.shiftRight(s);
		// correct the bits that "fell off" to the right side. Do this by prepending
		// them to the left side to create the rotate feel
		if (n.bitLength() < b) {
			BigInteger mask = clearMSB(doppleganger, (doppleganger.signum() != 0 ? doppleganger.bitLength() - s : 0));
			n = mask.shiftLeft(b - s).or(n);
		}
		r[0] = n;
	}

	/*
	 * Date: 19 Nov 2022-----------------------------------------------------------
	 * Time created: 02:52:19--------------------------------------------
	 */
	/**
	 * Emulates java's <code>&lt;&lt;</code> operator on the given
	 * {@code BigInteger} which also emulates a 2's complement integer of the same
	 * bit length as {@code bound}. When {@code n} is signed (negative), the
	 * operation is performed on it's 2's complement equivalent. This means that if
	 * shifting will cause a the argument to have a bigger bit length than the given
	 * bound, the excess bits are allowed to "fall off".
	 * <p>
	 * This method is created so as allow for simulation of java's
	 * <code>&lt;&lt;</code> operator when porting mathematical libraries from
	 * primitive types that use 2's complement bit operations.
	 * <p>
	 * Note that as with java's shift operators, this method becomes undefined if
	 * the shift argument is negative
	 * 
	 * @param n     the value to be shifted. Note that this value will be converted
	 *              to the 2's complement equivalent with respect to bound. This
	 *              means that is this method is called with a {@code BigInteger}
	 *              equivalent to 400 and bound equal to 8, then the
	 *              {@code BigInteger} will be truncated before the operation will
	 *              commence
	 * @param shift the number of position(s) to shift {@code n}
	 * @param bound the respected bit length of the given number. This helps with
	 *              unsigned conversion before, during and after the shift
	 *              operation.
	 * @return {@code n << shift} with respect to the given bound.
	 * @throws ArithmeticException if bound is less than 4
	 */
	public static void shiftLeft(int shift, int bound, BigInteger[] r)
			throws ArithmeticException {
		if (bound < 4)
			throw new ArithmeticException("bound is less than 4");
		sl(shift, bound, r);
	}

	/*
	 * Date: 19 Nov 2022-----------------------------------------------------------
	 * Time created: 03:18:00--------------------------------------------
	 */
	/**
	 * Emulates java's <code>&gt;&gt;&gt;</code> (or the <code>&gt;&gt;</code> if
	 * the {@code arithmetic} flag is set to {@code true}) operator on the given
	 * {@code BigInteger} which also emulates a 2's complement integer of the same
	 * bit length as {@code bound}. When {@code n} is signed (negative), the
	 * operation is performed on it's 2's complement equivalent.
	 * <p>
	 * This method is created so as allow for simulation of java's
	 * <code>&gt;&gt;&gt;</code> (or the <code>&gt;&gt;</code> if the
	 * {@code arithmetic} flag is set to {@code true}) operator when porting
	 * mathematical libraries from primitive types that use 2's complement bit
	 * operations.
	 * <p>
	 * Note that as with java's shift operators, this method becomes undefined if
	 * the shift argument is negative
	 * 
	 * @param n          the value to be shifted. Note that this value will be
	 *                   converted to the unsigned equivalent with respect to bound.
	 *                   This means that is this method is called with a
	 *                   {@code BigInteger} equivalent to 400 and bound equal to 8,
	 *                   then the {@code BigInteger} will be truncated before the
	 *                   operation will commence
	 * @param shift      the number of position(s) to shift {@code n}
	 * @param bound      the respected bit length of the given number. This helps
	 *                   with unsigned conversion before, during and after the shift
	 *                   operation.
	 * @param arithmetic a flag for wether to perform a an arithmetic or a logical
	 *                   shift
	 * @return {@code n >>> shift} or {@code n >> shift} with respect to the given
	 *         bound and {@code boolean} flag.
	 * @throws ArithmeticException if bound is less than 4
	 */
	public static void shiftRight(int shift, int bound,
			boolean arithmetic, BigInteger[] r) throws ArithmeticException {
		if (bound < 4)
			throw new ArithmeticException("bound is less than 4");
		sr(shift, bound, arithmetic, r);
	}

	// The behaviour of this method when shift is negative is undefined
	/*
	 * Date: 19 Nov 2022-----------------------------------------------------------
	 * Time created: 03:31:17--------------------------------------------
	 */
	/**
	 * Emulates a circular left shift on the given {@code Tuple.Couple} (which
	 * contains the value to shifted and the carry bit) which also emulates an
	 * unsigned integer of the same bit length as {@code bound} and returns the
	 * {@code BigInteger} and the new carry bit. When {@code n} is signed
	 * (negative), the operation is performed on it's unsigned equivalent, and then
	 * converted to the signed equivalent, preserving the sign only if the sign bit
	 * was not moved or.
	 * <p>
	 * This method is created so as allow for simulation of the circular shift
	 * operations when porting mathematical libraries from primitive types that use
	 * 2's complement bit operations.
	 * <p>
	 * Note that as with java's shift operators, this method becomes undefined if
	 * the shift argument is negative
	 * 
	 * @param val   a {@code Tuple.Couple} of a {@code BigInteger} (representing the
	 *              value to be shifted) and a {@code BigInteger} (representing the
	 *              carry bit. Only the least significant bit is needed. Although
	 *              this value is ignored if the boolean argument is false)
	 * @param shift the number of position(s) to shift {@code n}
	 * @param bound the respected bit length of the given number. This helps with
	 *              unsigned conversion before, during and after the shift
	 *              operation.
	 * @param carry flag to perform a rotate carry or plain rotate
	 * @return the input Tuple object after performing a rotate (or rotate through
	 *         carry if the {@code boolean} argument is {@code true}).
	 * @throws ArithmeticException if bound is less than 4
	 */
	public static void circularShiftLeft(int shift,
			int bound, boolean carry, BigInteger[] r) throws ArithmeticException {
		if (bound < 4)
			throw new ArithmeticException("bound is less than 4");
		csl(shift, bound, carry, r);
	}

	/*
	 * Date: 19 Nov 2022-----------------------------------------------------------
	 * Time created: 05:09:02--------------------------------------------
	 */
	/**
	 * Emulates a circular right shift on the given {@code Tuple.Couple} (which
	 * contains the value to shifted and the carry bit) which also emulates an
	 * unsigned integer of the same bit length as {@code bound} and returns the
	 * {@code BigInteger} and the new carry bit. When {@code n} is signed
	 * (negative), the operation is performed on it's unsigned equivalent, and then
	 * converted to the signed equivalent, preserving the sign only if the sign bit
	 * was not moved or.
	 * <p>
	 * This method is created so as allow for simulation of the circular shift
	 * operations when porting mathematical libraries from primitive types that use
	 * 2's complement bit operations.
	 * <p>
	 * Note that as with java's shift operators, this method becomes undefined if
	 * the shift argument is negative
	 * 
	 * @param val   a {@code Tuple.Couple} of a {@code BigInteger} (representing the
	 *              value to be shifted) and a {@code BigInteger} (representing the
	 *              carry bit. Only the least significant bit is needed. Although
	 *              this value is ignored if the boolean argument is false)
	 * @param shift the number of position(s) to shift {@code n}
	 * @param bound the respected bit length of the given number. This helps with
	 *              unsigned conversion before, during and after the shift
	 *              operation.
	 * @param carry flag to perform a rotate carry or plain rotate
	 * @return {@code n >>> shift} or {@code n >> shift} with respect to the given
	 *         bound and {@code boolean} flag.
	 * @throws ArithmeticException if bound is less than 4
	 */
	public static void circularShiftRight(int shift,
			int bound, boolean carry, BigInteger[] r) throws ArithmeticException {
		if (bound < 4)
			throw new ArithmeticException("bound is less than 4");
		csr(shift, bound, carry, r);
	}

	public static void not(int bound, BigInteger[] r) throws ArithmeticException {

		// prevent signed values and truncate oversized lengths
		fromDecimal(bound, r);

		// perform actual operation
		r[0] = quick2sNot(r[0], bound);
	}

	static boolean multiplicationHasOverflown(int val, int factor) {
		long result = (long) val * (long) factor;
		return (int) result != result;
	}

	static BigInteger quick2sNot(BigInteger n, int bound) {
//		if (n.bitLength() > bound)
//			n = clearMSB(n, n.bitLength() - bound);
		return getAllOnes(bound).subtract(n);
	}

	static void fd(int b, BigInteger[] r) {
		int s = r[0].signum();
		if (s == 0)
			return;

		else if (s > 0) {
			if (r[0].bitLength() > b) {
				r[0] = clearMSB(r[0], r[0].bitLength() - b);
				r[1] = r[0].shiftRight(b).testBit(0) ? i(1) : i(0);
				return;
			}
			return;
		}

		// remove sign i.e get |n|
		r[0] = r[0].abs();

		if (r[0].bitLength() > b) {
			r[0] = clearMSB(r[0], r[0].bitLength() - b);
			r[1] = r[0].shiftRight(b).testBit(0) ? i(1) : i(0);
		}

		// invert all bits then add 1
		r[0] = quick2sNot(r[0], b).add(i(1));

		// assert result is positive or discontinue
		if (r[0].signum() < 0)
			throw new ArithmeticException("operation not successful");
	}

	public static void fromDecimal(int bound, BigInteger[] r) throws ArithmeticException {
		if (bound < 4)
			throw new ArithmeticException("bound is less than 4");
		fd(bound, r);
	}

	public static void abs(int bound, BigInteger[] r) {
		if (bound < 4)
			throw new ArithmeticException("bound is less than 4");
		MT.fromTC(bound, r);
		r[0] = r[0].abs();
		fromDecimal(bound, r);
	}
}
