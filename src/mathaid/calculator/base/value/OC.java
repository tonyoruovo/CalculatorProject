/**
 * 
 */
package mathaid.calculator.base.value;

import static mathaid.calculator.base.util.Utility.i;
import static mathaid.calculator.base.value.FloatAid.clearMSB;
import static mathaid.calculator.base.value.FloatAid.getAllOnes;
import static mathaid.calculator.base.value.TC.multiplicationHasOverflown;

import java.math.BigInteger;
import java.util.Comparator;

/*
 * Date: 20 Nov 2022----------------------------------------------------------- 
 * Time created: 15:41:52---------------------------------------------------  
 * Package: mathaid.calculator.base.util------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: OC.java------------------------------------------------------ 
 * Class name: OC------------------------------------------------ 
 */
/**
 * Static methods for 1's complement arithmetic on a bounded {@code BigInteger}. Bounded integers are {@code BigInteger} values
 * that behave exactly like their native counterparts therefore can be used to simulate values such as {@code byte} (bound to
 * 8-bits), {@code short} (bound to 16-bits), {@code int} (bound to 32-bits), {@code long} (bound to 64-bits) and also create
 * dyadic bounds such as 128, 256 and so on. <p>Unless otherwise stated, all values to be operated on are in decimal format and
 * must be non-null. The results are all in 1's complement format.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 * @email tonyoruovo@gmail.com
 */
public class OC {

	/*
	 * Date: 28 Nov 2023 -----------------------------------------------------------
	 * Time created: 08:08:33 ---------------------------------------------------
	 */
	/**
	 * Constructor for creating a {@code mathaid.calculator.base.value.OC} object. It is {@code private} because this class is not
	 * to be instantiated.
	 */
	private OC() {
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:52:37 ---------------------------------------------------
	 */
	/**
	 * Creates a {@code Comparator} that can do comparisons relative to 1's complement format. <p>All operands of the comparison are
	 * assumed to be in decimal representation i.e ordinary integers. <p>The {@code Comparator} itself will covert both operands
	 * from decimal to 2's complement format where the actual comparison will then be made and the results returned.
	 * 
	 * @param bound the bit length for both of the operands.
	 * 
	 * @return A {@code Comparator} specifically integers in the given bound for 1's complement formats.
	 */
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
	 * Compares both {@code BigInteger} operands assumed using the given {@code bound} as the bit-length constraint. <p>Both
	 * {@code x} and {@code y} will be converted to 2's complement where the actual comparison will be done.
	 * 
	 * @param x     one of the 2 operands representing bits in decimal format.
	 * @param y     one of the 2 operands representing bits in decimal format.
	 * @param bound the bound to constrain the values to be compared. This is the bit-length of both values.
	 * 
	 * @return an {@code int} value indicating the comparative relationship between {@code x} and {@code y}.
	 * 
	 * @see Comparator#compare
	 */
	public static int compare(BigInteger x, BigInteger y, int bound) {
		return getComparator(bound).compare(x, y);
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:56:03 ---------------------------------------------------
	 */
	/**
	 * Converts the first element of {@code r} (which is assumed to be in decimal format) to 1's complement format and stores the
	 * result at the same index.
	 * 
	 * @param bound the number of bits (dyadic) to work with. This simulates computer bit registers such as 8, 16, 32, 64, 128, 256,
	 *              512 and so on. This is the bit-length of the value to be converted.
	 * @param r     the array holding the value to be transformed. When this method returns, the first index contains the
	 *              transformed value and the second is either {@code 1} (specifying that an overflow occurred during the conversion
	 *              which may indicate that a lossless conversion may not have been done hence some bits was lost) or {@code 0}
	 *              (indicating that a lossless conversion was done). <p> The bit at the second index of {@code r} is the overflow
	 *              or carry bit.
	 * 
	 * @throws ArithmeticException if {@code bound < 4}.
	 */
	public static void fromDecimal(int bound, BigInteger[] r) throws ArithmeticException {
		if (bound < 4)
			throw new ArithmeticException("bound is less than 4");
		int s = r[0].signum();
		if (s == 0)
			return;
		else if (s > 0) {
			if (r[0].bitLength() > bound) {
				r[0] = clearMSB(r[0], r[0].bitLength() - bound);
				r[1] = r[0].shiftRight(bound).testBit(0) ? i(1) : i(0);
				return;
			}
			return;
		}

		// remove sign i.e get |n|
		r[0] = r[0].abs();

		// invert all bits
		r[0] = TC.quick2sNot(r[0], bound);

		// assert result is positive or discontinue
		if (r[0].signum() < 0)
			throw new ArithmeticException("operation not successful");
		if (r[0].bitLength() > bound) {
			r[0] = clearMSB(r[0], r[0].bitLength() - bound);
			r[1] = r[0].shiftRight(bound).testBit(0) ? i(1) : i(0);
		}
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:58:29 ---------------------------------------------------
	 */
	/**
	 * Performs a 1's complement left shift on the first element of {@code r}, storing the result in the same index of {@code r} and
	 * the overflow bit (if any) in the second index. <p>This bitwise operation is always a logical left shift.
	 * 
	 * @param s the number of bit (i.e the bit distance) to which the value will be shifted.
	 * @param b the number of bits (dyadic) to work with. This simulates computer bit registers such as 8, 16, 32, 64, 128, 256, 512
	 *          and so on. This is the bit-length of the value to be converted.
	 * @param r the array holding the value to be computed. When this method returns, the first index contains the computed value
	 *          and the second is either {@code 1} (specifying that an overflow occurred during the conversion which may indicate
	 *          that a lossless computation may not have been done hence some bits was lost) or {@code 0} (indicating that a
	 *          lossless computation was done). <p> The bit at the second index of {@code r} is the overflow or carry bit.
	 * 
	 * @throws ArithmeticException if a catastrophic overflow occurred such that the loss of significant bits was too large to
	 *                             ignore and would result in an very wrong result. A 'catastrophic overflow' in this context is
	 *                             when {@code b} is too large so that a multiplication by 2 would overflow it's 32 bits allocated.
	 */
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

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:58:29 ---------------------------------------------------
	 */
	/**
	 * Performs a 1's complement (signed if specified) right shift on the first element of {@code r}, storing the result in the same
	 * index of {@code r} and the overflow bit (if any) in the second index. <p>This bitwise operation is a logical right shift if
	 * {@code a == false} else it is a arithmetic shift.
	 * 
	 * @param s the number of bit (i.e the bit distance) to which the value will be shifted.
	 * @param b the number of bits (dyadic) to work with. This simulates computer bit registers such as 8, 16, 32, 64, 128, 256, 512
	 *          and so on. This is the bit-length of the value to be converted.
	 * @param a specifies whether an unsigned or signed right shift should be carried out. <code>true</code> if signed right shift
	 *          is desired.
	 * @param r the array holding the value to be computed. When this method returns, the first index contains the computed value
	 *          and the second is either {@code 1} (specifying that an overflow occurred during the conversion which may indicate
	 *          that a lossless computation may not have been done hence some bits was lost) or {@code 0} (indicating that a
	 *          lossless computation was done). <p> The bit at the second index of {@code r} is the overflow or carry bit.
	 * 
	 * @throws ArithmeticException if a catastrophic overflow occurred such that the loss of significant bits was too large to
	 *                             ignore and would result in an very wrong result. A 'catastrophic overflow' in this context is
	 *                             when {@code b} is too large so that a multiplication by 2 would overflow it's 32 bits allocated.
	 */
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

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:58:29 ---------------------------------------------------
	 */
	/**
	 * Performs a 1's complement circular/rotating left shift on the first element of {@code r}, storing the result in the same
	 * index of {@code r} and the overflow bit (if any) in the second index. <p>This bitwise operation is always a logical
	 * (unsigned) circular left shift.
	 * 
	 * @param s the number of bit (i.e the bit distance) to which the value will be shifted.
	 * @param b the number of bits (dyadic) to work with. This simulates computer bit registers such as 8, 16, 32, 64, 128, 256, 512
	 *          and so on. This is the bit-length of the value to be converted.
	 * @param c specifies whether a carry bit be preserved. When specified, an extra index is created after the MSB. Any bit found
	 *          at that index by the time this method returns is sent to the second element of {@code r} as the overflow/carry bit.
	 * @param r the array holding the value to be computed. When this method returns, the first index contains the computed value
	 *          and the second is either {@code 1} (specifying that an overflow occurred during the conversion which may indicate
	 *          that a lossless computation may not have been done hence some bits was lost) or {@code 0} (indicating that a
	 *          lossless computation was done). <p> The bit at the second index of {@code r} is the overflow or carry bit and will
	 *          be ignored at this method if {@code c == false}.
	 * 
	 * @throws ArithmeticException if a catastrophic overflow occurred such that the loss of significant bits was too large to
	 *                             ignore and would result in an very wrong result. A 'catastrophic overflow' in this context is
	 *                             when {@code b} is too large so that a multiplication by 2 would overflow it's 32 bits allocated.
	 */
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

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:58:29 ---------------------------------------------------
	 */
	/**
	 * Performs a 1's complement circular/rotating right shift on the first element of {@code r}, storing the result in the same
	 * index of {@code r} and the overflow bit (if any) in the second index. <p>This bitwise operation is always a logical
	 * (unsigned) circular right shift.
	 * 
	 * @param s the number of bit (i.e the bit distance) to which the value will be shifted.
	 * @param b the number of bits (dyadic) to work with. This simulates computer bit registers such as 8, 16, 32, 64, 128, 256, 512
	 *          and so on. This is the bit-length of the value to be converted.
	 * @param c specifies whether a carry bit be preserved. When specified, an extra index is created after the MSB. Any bit found
	 *          at that index by the time this method returns is sent to the second element of {@code r} as the overflow/carry bit.
	 * @param r the array holding the value to be computed. When this method returns, the first index contains the computed value
	 *          and the second is either {@code 1} (specifying that an overflow occurred during the conversion which may indicate
	 *          that a lossless computation may not have been done hence some bits was lost) or {@code 0} (indicating that a
	 *          lossless computation was done). <p> The bit at the second index of {@code r} is the overflow or carry bit and will
	 *          be ignored at this method if {@code c == false}.
	 * 
	 * @throws ArithmeticException if a catastrophic overflow occurred such that the loss of significant bits was too large to
	 *                             ignore and would result in an very wrong result. A 'catastrophic overflow' in this context is
	 *                             when {@code b} is too large so that a multiplication by 2 would overflow it's 32 bits allocated.
	 */
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
	 * Performs <code>n &lt;&lt; shift</code> with respect to the given {@code bound} where <span style="font-style:italic">n</span>
	 * is the first element of {@code r}, while the second index contains the overflow bit. <p> Emulates java's bitwise
	 * <code>&lt;&lt;</code> operator on the first element of the given {@code r} which also emulates a 1's complement integer of
	 * the same bit length as {@code bound}. When the value to be converted is signed (negative), the operation is performed on it's
	 * 1's complement equivalent. This means that if shifting will cause the argument to have a bigger bit length than the given
	 * bound, the excess bits are allowed to "fall off". The last bit among those that "fell off" becomes the carry bit and it is
	 * store at the second element of {@code r}, this index then becomes the carry/overflow bit. <p> This method is created so as
	 * allow for simulation of java's <code>&lt;&lt;</code> operator when porting mathematical libraries from primitive types that
	 * use 1's complement bit operations. <p> Note that as with java's shift operators, this method becomes undefined if the shift
	 * argument is negative.
	 * 
	 * @param shift the number of position(s) to shift value to be converted.<p>This is also the number of bit (i.e the bit
	 *              distance) to which the value will be shifted.
	 * @param bound the respected bit length of the given number. This helps with unsigned conversion before, during and after the
	 *              shift operation.<p>This is also the number of bits (dyadic) to work with. This simulates computer bit registers
	 *              such as 8, 16, 32, 64, 128, 256, 512 and so on. This is the bit-length of the value to be converted.
	 * @param r     a {@code BigInteger} array whereby the first element is the value to be shifted.<p> When this method returns,
	 *              the first index contains the computed value and the second is either {@code 1} (specifying that an overflow
	 *              occurred during the conversion which may indicate that a lossless computation may not have been done hence some
	 *              bits was lost) or {@code 0} (indicating that a lossless computation was done).<p>Note that this value will be
	 *              converted to the 1's complement equivalent with respect to bound. This means that if this method is called with
	 *              a {@code BigInteger} equivalent to 400 and bound equal to 8, then the {@code BigInteger} will be truncated
	 *              before the operation will commence. The second index contains the overflow/carry bit.
	 * 
	 * @throws ArithmeticException if bound is less than 4 or if a catastrophic overflow occurred such that the loss of significant
	 *                             bits was too large to ignore and would result in an very wrong result. A 'catastrophic overflow'
	 *                             in this context is when {@code bound} is too large so that a multiplication by 2 would overflow
	 *                             it's 32 bits allocated.
	 */
	public static void shiftLeft(int shift, int bound, BigInteger[] r) throws ArithmeticException {
		if (bound < 4)
			throw new ArithmeticException("bound is less than 4");
		sl(shift, bound, r);
	}

	/*
	 * Date: 19 Nov 2022-----------------------------------------------------------
	 * Time created: 03:18:00--------------------------------------------
	 */
	/**
	 * Performs <code>n &gt;&gt;&gt; shift</code> or <code>n &gt;&gt; shift</code> with respect to the given bound and
	 * {@code arithmetic} flag where <span style="font-style:italic">n</span> is the first element of {@code r} and the result is in
	 * the same index of {@code r}, while the second index contains the value {@code 0} as no overflow is expected from shifting to
	 * the right, whether logical or otherwise. <p> This emulates java's bitwise <code>&gt;&gt;&gt;</code> (or the
	 * <code>&gt;&gt;</code> if the {@code arithmetic} flag is set to {@code true}) operator on the given element to be converted
	 * which also emulates a 1's complement integer of the same bit length as {@code bound}. When the value to be converted is
	 * signed (negative), the operation is performed on it's 1's complement equivalent. <p> This method is created so as allow for
	 * simulation of java's <code>&gt;&gt;&gt;</code> (or the <code>&gt;&gt;</code> if the {@code arithmetic} flag is set to
	 * {@code true}) bitwise operator when porting mathematical libraries from primitive types that use 1's complement bit
	 * operations. <p> Note that as with java's shift operators, this method becomes undefined if the shift argument is negative.
	 * 
	 * @param shift      the number of position(s) to shift value to be converted.<p>This is also the number of bit (i.e the bit
	 *                   distance) to which the value will be shifted.
	 * @param bound      the respected bit length of the given number. This helps with unsigned conversion before, during and after
	 *                   the shift operation.<p>This is also the number of bits (dyadic) to work with. This simulates computer bit
	 *                   registers such as 8, 16, 32, 64, 128, 256, 512 and so on. This is the bit-length of the value to be
	 *                   converted.
	 * @param arithmetic a flag specifying whether to perform an arithmetic or a logical shift.<p>This specifies whether an unsigned
	 *                   or signed right shift should be carried out.<code>true</code> if signed right shift is desired and
	 *                   <code>false</code> if otherwise.
	 * @param r          a {@code BigInteger} array whereby the first element is the value to be shifted.<p> When this method
	 *                   returns, the first index contains the computed value and the second is always {@code 0} (indicating that a
	 *                   lossless computation was done).<p>Note that this value will be converted to the 1's complement equivalent
	 *                   with respect to bound. This means that if this method is called with a {@code BigInteger} equivalent to 400
	 *                   and bound equal to 8, then the {@code BigInteger} will be truncated before the operation will commence.
	 * 
	 * @throws ArithmeticException if bound is less than 4 or if a catastrophic overflow occurred such that the loss of significant
	 *                             bits was too large to ignore and would result in an very wrong result. A 'catastrophic overflow'
	 *                             in this context is when {@code bound} is too large so that a multiplication by 2 would overflow
	 *                             it's 32 bits allocated.
	 */
	public static void shiftRight(int shift, int bound, boolean arithmetic, BigInteger[] r) throws ArithmeticException {
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
	 * Performs a 1's complement circular/rotating left shift on the first element of {@code r}, storing the result in the same
	 * index of {@code r} and the overflow bit (if any) in the second index. <p>This emulates a circular left shift (left rotation)
	 * on the given array {@code r} (which contains the value to shifted and the carry bit) which also emulates an unsigned integer
	 * of the same bit length as {@code bound} and returns the {@code BigInteger} and the new carry bit. When {@code n} is signed
	 * (negative), the operation is performed on it's unsigned equivalent, and then converted to the signed equivalent, preserving
	 * the sign only if the sign bit was not moved or. <p> This method is created so as allow for simulation of the circular shift
	 * operations when porting mathematical libraries from primitive types that use 1's complement bit operations. <p> Note that as
	 * with java's shift operators, this method becomes undefined if the shift argument is negative
	 * 
	 * @param r     a 2-length array of a {@code BigInteger} values (representing the value to be shifted) and a {@code BigInteger}
	 *              (representing the carry bit. Only the least significant bit is needed. Although this value is ignored if the
	 *              boolean argument is <code>false</code>).<p> When this method returns, the first index contains the computed
	 *              value and the second is always {@code 0} (indicating that a lossless computation was done).<p>Note that this
	 *              value will be converted to the 1's complement equivalent with respect to bound. This means that if this method
	 *              is called with a {@code BigInteger} equivalent to 400 and bound equal to 8, then the {@code BigInteger} will be
	 *              truncated before the operation will commence.
	 * @param shift the number of position(s) to rotate the value.
	 * @param bound the respected bit length of the given number. This helps with unsigned conversion before, during and after the
	 *              shift operation.<p>This is also the number of bits (dyadic) to work with. This simulates computer bit registers
	 *              such as 8, 16, 32, 64, 128, 256, 512 and so on. This is the bit-length of the value to be converted.
	 * @param carry flag to perform a rotate carry or plain rotate.<p>When specified, an extra index is created after the MSB. Any
	 *              bit found at that index by the time this method returns is sent to the second element of {@code r} as the
	 *              overflow/carry bit.
	 * 
	 * @throws ArithmeticException if bound is less than 4 or if a catastrophic overflow occurred such that the loss of significant
	 *                             bits was too large to ignore and would result in an very wrong result. A 'catastrophic overflow'
	 *                             in this context is when {@code bound} is too large so that a multiplication by 2 would overflow
	 *                             it's 32 bits allocated.
	 */
	public static void circularShiftLeft(int shift, int bound, boolean carry, BigInteger[] r) throws ArithmeticException {
		if (bound < 4)
			throw new ArithmeticException("bound is less than 4");
		csl(shift, bound, carry, r);
	}

	// The behaviour of this method when shift is negative is undefined
	/*
	 * Date: 19 Nov 2022-----------------------------------------------------------
	 * Time created: 03:31:17--------------------------------------------
	 */
	/**
	 * Performs a 1's complement circular/rotating right shift on the first element of {@code r}, storing the result in the same
	 * index of {@code r} and the overflow bit (if any) in the second index. <p>This emulates a circular right shift (right
	 * rotation) on the given array {@code r} (which contains the value to shifted and the carry bit) which also emulates an
	 * unsigned integer of the same bit length as {@code bound} and returns the {@code BigInteger} and the new carry bit. When
	 * {@code n} is signed (negative), the operation is performed on it's unsigned equivalent, and then converted to the signed
	 * equivalent, preserving the sign only if the sign bit was not moved or. <p> This method is created so as allow for simulation
	 * of the circular shift operations when porting mathematical libraries from primitive types that use 1's complement bit
	 * operations. <p> Note that as with java's shift operators, this method becomes undefined if the shift argument is negative
	 * 
	 * @param r     a 2-length array of a {@code BigInteger} values (representing the value to be shifted) and a {@code BigInteger}
	 *              (representing the carry bit. Only the least significant bit is needed. Although this value is ignored if the
	 *              boolean argument is <code>false</code>).<p> When this method returns, the first index contains the computed
	 *              value and the second is always {@code 0} (indicating that a lossless computation was done).<p>Note that this
	 *              value will be converted to the 1's complement equivalent with respect to bound. This means that if this method
	 *              is called with a {@code BigInteger} equivalent to 400 and bound equal to 8, then the {@code BigInteger} will be
	 *              truncated before the operation will commence.
	 * @param shift the number of position(s) to rotate the value.
	 * @param bound the respected bit length of the given number. This helps with unsigned conversion before, during and after the
	 *              shift operation.<p>This is also the number of bits (dyadic) to work with. This simulates computer bit registers
	 *              such as 8, 16, 32, 64, 128, 256, 512 and so on. This is the bit-length of the value to be converted.
	 * @param carry flag to perform a rotate carry or plain rotate.<p>When specified, an extra index is created after the MSB. Any
	 *              bit found at that index by the time this method returns is sent to the second element of {@code r} as the
	 *              overflow/carry bit.
	 * 
	 * @throws ArithmeticException if bound is less than 4 or if a catastrophic overflow occurred such that the loss of significant
	 *                             bits was too large to ignore and would result in an very wrong result. A 'catastrophic overflow'
	 *                             in this context is when {@code bound} is too large so that a multiplication by 2 would overflow
	 *                             it's 32 bits allocated.
	 */
	public static void circularShiftRight(int shift, int bound, boolean carry, BigInteger[] r)
			throws ArithmeticException {
		if (bound < 4)
			throw new ArithmeticException("bound is less than 4");
		csr(shift, bound, carry, r);
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:42:37 ---------------------------------------------------
	 */
	/**
	 * Calculates the absolute value of the first index of {@code r} and stores the result in the same index.
	 * 
	 * @param bound the number of bits (dyadic) to work with. This simulates computer bit registers such as 8, 16, 32, 64, 128, 256,
	 *              512 and so on. This is the bit-length of the value to be converted.
	 * @param r     the array holding the value to be computed. This assumes that the first index is in 1's complement format. When
	 *              this method returns, the first index contains the computed value and the second is either {@code 1} or
	 *              {@code 0}, however, it holds no consequence here.
	 * 
	 * @throws ArithmeticException if {@code bound > 4} this exception will also be thrown.
	 */
	public static void abs(int bound, BigInteger[] r) {
		if (bound < 4)
			throw new ArithmeticException("bound is less than 4");
		MT.fromOC(bound, r);
		r[0] = r[0].abs();
		fromDecimal(bound, r);
	}
}
