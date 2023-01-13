/**
 * 
 */
package mathaid.calculator.base.value;

import static mathaid.calculator.base.value.FloatAid.clearMSB;
import static mathaid.calculator.base.value.FloatAid.getAllOnes;
import static mathaid.calculator.base.value.FloatAid.i;
import static mathaid.calculator.base.value.TC.multiplicationHasOverflown;

import java.math.BigInteger;
import java.util.Comparator;

/*
 * Date: 22 Nov 2022----------------------------------------------------------- 
 * Time created: 01:29:31---------------------------------------------------  
 * Package: mathaid.calculator.base.util------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: US.java------------------------------------------------------ 
 * Class name: US------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class US {

	static Comparator<BigInteger> getComparator(int bound) {
		return new Comparator<BigInteger>() {
			@Override
			public int compare(BigInteger x, BigInteger y) {
				BigInteger[] r = { x, TC.i(0), };
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

	public static void fromDecimal(int bound, BigInteger[] r) throws ArithmeticException {
		if (r[0].signum() < 0) {
			r[0] = TC.i(0);
			return;
		} else if (bound < 4)
			return;
		TC.fromDecimal(bound, r);
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

//		boolean isNeg = r[0].testBit(b - 1);
		r[0] = r[0].shiftRight(s);
//		if (isNeg && a && r[0].bitLength() < b)
//			r[0] = getTrailingZeros(b - 1).or(r[0]);
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

	public static void shiftLeft(int shift, int bound, BigInteger[] r) throws ArithmeticException {
		if (bound < 4)
			throw new ArithmeticException("bound is less than 4");
		sl(shift, bound, r);
	}

	public static void shiftRight(int shift, int bound, boolean arithmetic, BigInteger[] r) throws ArithmeticException {
		if (bound < 4)
			throw new ArithmeticException("bound is less than 4");
		sr(shift, bound, arithmetic, r);
	}

	public static void circularShiftLeft(int shift, int bound, boolean carry, BigInteger[] r)
			throws ArithmeticException {
		if (bound < 4)
			throw new ArithmeticException("bound is less than 4");
		csl(shift, bound, carry, r);
	}

	public static void circularShiftRight(int shift, int bound, boolean carry, BigInteger[] r)
			throws ArithmeticException {
		if (bound < 4)
			throw new ArithmeticException("bound is less than 4");
		csr(shift, bound, carry, r);
	}

	public static void abs(int bound, BigInteger[] r) {
		if (bound < 4)
			throw new ArithmeticException("bound is less than 4");
		if(r[0].bitLength() > bound) {
			fromDecimal(bound, r);
			abs(bound, r);
			return;
		}
	}
}
