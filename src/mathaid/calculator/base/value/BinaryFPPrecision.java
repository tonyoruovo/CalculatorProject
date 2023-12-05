/**
 * 
 */
package mathaid.calculator.base.value;

import static mathaid.calculator.base.converter.AngleUnit.RAD;
import static mathaid.calculator.base.util.Utility.d;
import static mathaid.calculator.base.util.Utility.i;
import static mathaid.calculator.base.util.Utility.isInteger;
import static mathaid.calculator.base.util.Utility.isNumber;
import static mathaid.calculator.base.util.Utility.mc;
import static mathaid.calculator.base.value.FloatAid.calculateSignificandDigits;
import static mathaid.calculator.base.value.FloatAid.clearMSB;
import static mathaid.calculator.base.value.FloatAid.fromDecimal;
import static mathaid.calculator.base.value.FloatAid.getAllOnes;
import static mathaid.calculator.base.value.FloatAid.getTrailingZeros;
import static mathaid.calculator.base.value.TC.fromDecimal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

import mathaid.calculator.base.util.Arith;
import mathaid.calculator.base.util.Constants;

/*
 * Date: 2 Nov 2022----------------------------------------------------------- 
 * Time created: 09:05:36---------------------------------------------------  
 * Package: mathaid.calculator.base.value------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: BinaryFPPrecision.java------------------------------------------------------ 
 * Class name: BinaryFPPrecision------------------------------------------------ 
 */
/**
 * A class representing floating point parameters such as the
 * {@link #getSignificandBitLength() significand width} and
 * {@link #getExponentBitLength() exponent width}. It also goes further to
 * calculate the {@link #getBias() bias}, the {@link #getMinExponent() minimum
 * exponent}, {@link #getMaxExponent() maximum exponent}, {@link #getMaxValue()
 * maximum value}, {@link #getMinNormal() minimum normal value} and the
 * {@link #getMinValue() minimum value}, infinities and NaNs. It can also be
 * used to create a {@link BinaryFP} object so as to get access to manipulation
 * of floating point values at bit level.
 * <p>
 * Future support for rounding modes and setting arithmetic flag will be coming
 * soon
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 * @apiNote It should be noted that significand and mantissa are not referring
 *          to the same thing. For example in 1.002345, 1002345 is the
 *          significand and 002345 is the mantissa
 * @see {@link Precision} for s quick refresher course on floating point and
 *      IEEE standards.
 */
public class BinaryFPPrecision implements Comparable<BinaryFPPrecision>, Serializable {

	/*
	 * Date: 7 Nov 2022-----------------------------------------------------------
	 * Time created: 21:03:06---------------------------------------------------
	 */
	/**
	 * Field for serial
	 */
	private static final long serialVersionUID = 4859954370253194116L;
	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 08:12:11---------------------------------------------------
	 */
	/**
	 * The minimum exponent width this class is allowed to have
	 */
	public static final int MIN_EXPONENT_BITS = 3;
	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 08:14:55---------------------------------------------------
	 */
	/**
	 * The minimum significand width this class is allowed to have
	 */
	public static final int MIN_SIGNIFICAND_BITS = 4;
	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 08:17:28---------------------------------------------------
	 */
	/**
	 * The minimum bit length this class is allowed to have
	 */
	public static final int MIN_BITLENGTH = 7;
	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 08:18:03---------------------------------------------------
	 */
	/**
	 * The maximum exponent width this class is allowed to have. This prevents
	 * overflowing an {@code int} when shifts are performed on the floating point
	 * number given that java's {@code BigInteger}'s shift methods only take an
	 * {@code int} as an argument.
	 */
	public static final int MAX_EXPONENT_BITS = 30;
	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 08:19:14---------------------------------------------------
	 */
	/**
	 * The maximum significand width this class is allowed to have
	 */
	public static final int MAX_SIGNIFICAND_BITS = 512;

	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 08:19:43---------------------------------------------------
	 */
	/**
	 * A constant {@code String} value representing the NaN value when converted to
	 * a {@code String}
	 */
	static final String NaN_STRING = "NaN";
	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 08:20:42---------------------------------------------------
	 */
	/**
	 * A constant {@code String} value used in representing the different infinites
	 */
	static final String INFINITY_STRING = "Infinity";

	/*
	 * Date: 25 Nov 2022-----------------------------------------------------------
	 * Time created: 02:56:25---------------------------------------------------
	 */
	/**
	 * The exception flag for invalid operation
	 */
	public static final int FP_EXCEPTION_INVALID_OP = 0xa;
	/*
	 * Date: 25 Nov 2022-----------------------------------------------------------
	 * Time created: 02:57:00---------------------------------------------------
	 */
	/**
	 * The exception flag for division by zero
	 */
	public static final int FP_EXCEPTION_DIVSION_BY_ZERO = 0xb;
	/*
	 * Date: 25 Nov 2022-----------------------------------------------------------
	 * Time created: 02:57:18---------------------------------------------------
	 */
	/**
	 * The exception flag for overflow
	 */
	public static final int FP_EXCEPTION_OVERFLOW = 0xc;
	/*
	 * Date: 25 Nov 2022-----------------------------------------------------------
	 * Time created: 02:57:31---------------------------------------------------
	 */
	/**
	 * The exception flag for underflow
	 */
	public static final int FP_EXCEPTION_UNDERFLOW = 0xd;
	/*
	 * Date: 25 Nov 2022-----------------------------------------------------------
	 * Time created: 02:57:49---------------------------------------------------
	 */
	/**
	 * The exception flag for inexact result
	 */
	public static final int FP_EXCEPTION_INEXACT_CALC = 0xe;

	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 08:21:19---------------------------------------------------
	 */
	/**
	 * Creates a {@code BinaryFPPrecision} by specifying the total number bits that
	 * can be used to represent the exponent of a floating point and by specifying
	 * the total number of bits that can be used to represent the floating point
	 * number.
	 * <p>
	 * An example of how to create a floating point in java's {@code float} is:
	 * 
	 * <pre>
	 * <code>
	 * BinaryFPPrecision p = new BinaryFPPrecision(8, 32);// allocates 8 bits for storing exponent and a total of 32 bit for storing the floating point number
	 * BinaryFP f = p.createFP("1.234");// successfully created a float
	 * System.out.println(f.toNormalisedString());// prints 1.3be76cp0
	 * System.out.println(f);// prints 1.23399997
	 * System.out.println(f.toBitString(2));// prints 111111100111011111001110110110
	 * </code>
	 * </pre>
	 * 
	 * @param totalExponentBits the number of bits that can be used to represent a
	 *                          floating point number. A high value mean that
	 *                          {@link #getMaxValue()} can support big values and
	 *                          {@link #getMinValue()} can support small values.
	 *                          Because of the structure of this class, this value
	 *                          is limited so that it always stays an {@code int}.
	 *                          This limitation is due to the fact that
	 *                          {@link BigInteger}'s shift methods only take in an
	 *                          {@code int} as an argument.
	 * @param totalBitLength    the total number of bits that can be used to
	 *                          represent the full floating point value. This may
	 *                          increase or decrease the precision of the
	 *                          significand of {@code BinaryFP} produced from this
	 *                          object
	 * @param rm                the object whose context is used for rounding
	 *                          calculations
	 * @throws IllegalArgumentException if any of the following is {@code true}:
	 *                                  <ul>
	 *                                  <li>If
	 *                                  {@code totalExponentBits > totalBitLength}.
	 *                                  </li>
	 *                                  <li>If
	 *                                  <code> totalBitLength &lt; {@value #MIN_BITLENGTH}</code></li>
	 *                                  <li>If
	 *                                  <code>totalExponentBits &lt; {@value #MIN_EXPONENT_BITS}</code></li>
	 *                                  <li>If
	 *                                  <code>totalBitLength - totalExponentBits &lt; {@value #MIN_SIGNIFICAND_BITS}</code></li>
	 *                                  <li>If
	 *                                  <code>totalExponentBits &gt; {@value #MAX_EXPONENT_BITS}</code></li>
	 *                                  <li>If
	 *                                  <code>totalBitLength - totalExponentBits &gt; {@value #MAX_SIGNIFICAND_BITS}</code></li>
	 *                                  </ul>
	 */
	public BinaryFPPrecision(int totalExponentBits, int totalBitLength, RoundingMode rm)
			throws IllegalArgumentException {
		if (totalExponentBits > totalBitLength)
			throw new IllegalArgumentException("total exponent bits was greater than total bit length");
		else if (totalBitLength < MIN_BITLENGTH)
			throw new IllegalArgumentException(
					"minimum length breached. Total bit length must be gretaer than or equal to 6");
		else if (totalExponentBits < MIN_EXPONENT_BITS)
			throw new IllegalArgumentException("exponent bitlength is less than minimum");
		else if (totalBitLength - totalExponentBits < MIN_SIGNIFICAND_BITS)
			throw new IllegalArgumentException("significand bitlength is less than minimum");
		else if (totalExponentBits > MAX_EXPONENT_BITS)
			throw new IllegalArgumentException("exponent bitlength is greater than maximum");
		else if (totalBitLength - totalExponentBits > MAX_SIGNIFICAND_BITS)
			throw new IllegalArgumentException("max significand breached");
		this.exponentLength = totalExponentBits;
		this.significandLength = totalBitLength - totalExponentBits;
		this.rm = rm;
	}

	/*
	 * Date: 23 Nov 2022-----------------------------------------------------------
	 * Time created: 22:46:02--------------------------------------------
	 */
	/**
	 * Gets the current {@code RoundingMode} object used for rounding operations.
	 * 
	 * @return a {@code RoundingMode}
	 */
	public RoundingMode getRoundingMode() {
		return rm;
	}

	/*
	 * Date: 23 Nov 2022-----------------------------------------------------------
	 * Time created: 22:46:55--------------------------------------------
	 */
	/**
	 * Sets the rounding context to the previded argument. Note that is is not a
	 * mutable operation as a new {@code BinaryFPPrecision} object will be returned
	 * whose rounding mode is the specified value
	 * 
	 * @param rm a {@code RoundingMode} object
	 * @return {@code this} after setting the rounding mode to the argument
	 */
	public BinaryFPPrecision setRoundingMode(RoundingMode rm) {
		return new BinaryFPPrecision(exponentLength, getBitLength(), rm);
	}

	/*
	 * Date: 12 Nov 2022-----------------------------------------------------------
	 * Time created: 01:47:35--------------------------------------------
	 */
	/**
	 * Returns a {@code MathContext} that can round a number to the appropriate
	 * digits in the specified radix. For example, rounding to a hexadecimal number
	 * may cause the user to be confused on how many digits should be used in the
	 * final string, this method will help return the appropriate precision. An
	 * exception is made if this conversion is specified to be for a subnormal
	 * value, when it is so, a valid number of digit(s) to be retrieved must be
	 * specified. For example:
	 * 
	 * <pre>
	 * <code>
	 * BinaryFPPrecision bf = FloatAid.IEEE754Single();//a float's precision
	 * BinaryFP f = bf.getMaxValue();
	 * System.out.println(f);// prints 3.40282347E+38
	 * System.out.println(f.toNormalisedString(16));// prints 1.fffffep127
	 * f = bf.getMinValue();//subnormal
	 * &sol;&ast;
	 *  &ast; without checking for subnormal in the {@link #getMathContext(int, boolean, int, RoundingMode)} method,
	 *  &ast; this will print out the full digits in their respective radix(es)
	 *  &ast;&sol;
	 * System.out.println(f);// prints 1.4E-45
	 * System.out.println(f.toNormalisedString(16));// prints 0.000002p-127
	 * </code>
	 * </pre>
	 * 
	 * @param radix       the base of the number to which the significant digits
	 *                    will be converted to. Note that this is converted from
	 *                    binary, meaning that the
	 *                    {@link #getSignificandBitLength()} (is the
	 *                    {@code isSubnormal} parameter is {@code false}) value is
	 *                    converted to the given radix
	 * @param isSubnormal a check that prevents the
	 *                    {@link #getSignificandBitLength()} value to be used and a
	 *                    different value to be used instead (denoting that the
	 *                    value to be rounded is subnormal and not all the
	 *                    significant digits are needed).
	 * @param sigLength   an {@code int} value only used if {@code isSubnormal} is
	 *                    {@code true}. This is used to denote that the value to be
	 *                    rounded is in the subnormal range and a different
	 *                    significant digit is needed.
	 * @return a {@code MathContext} that can round a number to the specified digits
	 *         (or a given number of digits if the {@code isSubnormal} flag is set
	 *         to {@code true}) in the specified radix
	 */
	public MathContext getMathContext(int radix, boolean isSubnormal, int sigLength) {
		int precision = calculateSignificandDigits(2, !isSubnormal ? significandLength : sigLength, radix) + 1;
		return mc(Math.max(2, precision), rm);
	}

	/*
	 * Date: 12 Nov 2022-----------------------------------------------------------
	 * Time created: 02:24:50--------------------------------------------
	 */
	/**
	 * Calculates and returns the biggest floating point value representable in this
	 * precision. Depending on the {@code BinaryFPPrecision}, the IEEE754 bit layout
	 * of this value may be different from it's counterpart in other precisions. For
	 * example:
	 * 
	 * <pre>
	 * <code>
	 * BinaryFPPrecision bf = FloatAid.IEEE754Single();
	 * System.out.println(bf.getMaxValue().toBitString(2));//prints 1111111011111111111111111111111
	 * bf = FloatAid.IEEE754Double();
	 * System.out.println(bf.getMaxValue().toBitString(2));//prints 111111111101111111111111111111111111111111111111111111111111111
	 * </code>
	 * </pre>
	 * 
	 * @return the largest possible {@code BinaryFP} value that can be represented
	 *         using this {@code BinaryFPPrecision}
	 */
	public BinaryFP getMaxValue() {
//		return new BinaryFP(d(i(1).shiftLeft(getMaxExponent() + 1),
//				getMathContext(10, false, significandLength, rm("HALF_EVEN"))));
		return new BinaryFP(
				iEEE754(i(0), getAllOnes(exponentLength - 1).shiftLeft(1), getAllOnes(significandLength - 1)), 0, 0);
	}

	/*
	 * Date: 12 Nov 2022-----------------------------------------------------------
	 * Time created: 02:28:41--------------------------------------------
	 */
	/**
	 * Calculates and returns the smallest floating point value representable in
	 * this precision.
	 * 
	 * @return the smallest possible {@code BinaryFP} value that can be represented
	 *         using this {@code BinaryFPPrecision}
	 */
	public BinaryFP getMinValue() {
		return new BinaryFP(i(1), 0, 0);
	}

	/*
	 * Date: 12 Nov 2022-----------------------------------------------------------
	 * Time created: 02:34:47--------------------------------------------
	 */
	/**
	 * Calculates and returns the smallest normal floating point value representable
	 * in this precision. Depending on the {@code BinaryFPPrecision}, the IEEE754
	 * bit layout of this value may be different from it's counterpart in other
	 * precisions. For example:
	 * 
	 * <pre>
	 * <code>
	 * BinaryFPPrecision bf = FloatAid.IEEE754Single();
	 * System.out.println(bf.getMinNormal().toBitString(2));//prints 100000000000000000000000
	 * bf = FloatAid.IEEE754Double();
	 * System.out.println(bf.getMinNormal().toBitString(2));//prints 10000000000000000000000000000000000000000000000000000
	 * </code>
	 * </pre>
	 * 
	 * @return the smallest normal {@code BinaryFP} value that can be represented
	 *         using this {@code BinaryFPPrecision}
	 */
	public BinaryFP getMinNormal() {
		return new BinaryFP(iEEE754(i(0), i(1), i(0)), 0, 0);
	}

	/*
	 * Date: 12 Nov 2022-----------------------------------------------------------
	 * Time created: 02:39:42--------------------------------------------
	 */
	/**
	 * Gets the specified NaN (<b>N</b>ot <b>a</b> <b>N</b>umber) for this
	 * {@code BinaryFPPrecision}. This is only guaranteed to return QNaNs and will
	 * not distinguish between the different types of NaN. Depending on the
	 * {@code BinaryFPPrecision}, the IEEE754 bit layout of this value may be
	 * different from it's counterpart in other precisions. For example:
	 * 
	 * <pre>
	 * <code>
	 * BinaryFPPrecision bf = FloatAid.IEEE754Single();
	 * System.out.println(bf.getNaN().toBitString(2));//prints 1111111110000000000000000000000
	 * bf = FloatAid.IEEE754Double();
	 * System.out.println(bf.getNaN().toBitString(2));//prints 111111111111000000000000000000000000000000000000000000000000000
	 * </code>
	 * </pre>
	 * 
	 * @return a {@code BinaryFP} value that is equal to the representable NaN in
	 *         this {@code BinaryFPPrecision}
	 */
	public BinaryFP getNaN() {
		return new BinaryFP(getExponentMask(true).or(getTrailingZeros(significandLength - 2)), 0, 0);
	}

	/*
	 * Date: 12 Nov 2022-----------------------------------------------------------
	 * Time created: 02:44:04--------------------------------------------
	 */
	/**
	 * Gets the positive infinity value as a {@code BinaryFP}. Depending on the
	 * {@code BinaryFPPrecision}, the IEEE754 bit layout of this value may be
	 * different from it's counterpart in other precisions. For example:
	 * 
	 * <pre>
	 * <code>
	 * BinaryFPPrecision bf = FloatAid.IEEE754Single();
	 * System.out.println(bf.getPositiveInfinity().toBitString(2));//prints 1111111100000000000000000000000
	 * bf = FloatAid.IEEE754Double();
	 * System.out.println(bf.getPositiveInfinity().toBitString(2));//prints 111111111110000000000000000000000000000000000000000000000000000
	 * </code>
	 * </pre>
	 * 
	 * @return a {@code BinaryFP} value that is equal to positive infinity in this
	 *         {@code BinaryFPPrecision}
	 */
	public BinaryFP getPositiveInfinity() {
		return new BinaryFP(getExponentMask(true), 0, 0);
	}

	/*
	 * Date: 12 Nov 2022-----------------------------------------------------------
	 * Time created: 03:01:19--------------------------------------------
	 */
	/**
	 * Gets the negative infinity value as a {@code BinaryFP}. Depending on the
	 * {@code BinaryFPPrecision}, the IEEE754 bit layout of this value may be
	 * different from it's counterpart in other precisions. For example:
	 * 
	 * <pre>
	 * <code>
	 * BinaryFPPrecision bf = FloatAid.IEEE754Single();
	 * System.out.println(bf.getNegativeInfinity().toBitString(2));//prints 11111111100000000000000000000000
	 * bf = FloatAid.IEEE754Double();
	 * System.out.println(bf.getNegativeInfinity().toBitString(2));//prints 1111111111110000000000000000000000000000000000000000000000000000
	 * </code>
	 * </pre>
	 * 
	 * @return a {@code BinaryFP} value that is equal to negative infinity in this
	 *         {@code BinaryFPPrecision}
	 */
	public BinaryFP getNegativeInfinity() {
		return new BinaryFP(getExponentMask(true).or(getSignMask(true)), 0, 0);
	}

	/*
	 * Date: 13 Nov 2022-----------------------------------------------------------
	 * Time created: 21:29:33--------------------------------------------
	 */
	/**
	 * Gets the negative zero value as a {@code BinaryFP}. Depending on the
	 * {@code BinaryFPPrecision}, the IEEE754 bit layout of this value may be
	 * different from it's counterpart in other precisions. For example:
	 * 
	 * <pre>
	 * <code>
	 * BinaryFPPrecision bf = FloatAid.IEEE754Single();
	 * System.out.println(bf.getNegativeZero().toBitString(2));//prints 10000000000000000000000000000000
	 * bf = FloatAid.IEEE754Double();
	 * System.out.println(bf.getNegativeZero().toBitString(2));//prints 1000000000000000000000000000000000000000000000000000000000000000
	 * </code>
	 * </pre>
	 * 
	 * @return a {@code BinaryFP} value that is equal to negative zero in this
	 *         {@code BinaryFPPrecision}
	 */
	public BinaryFP getNegativeZero() {
		return new BinaryFP(getSignMask(true), 0, 0);
	}

	/*
	 * Date: 13 Nov 2022-----------------------------------------------------------
	 * Time created: 21:31:29--------------------------------------------
	 */
	/**
	 * Gets a bit mask value where the sign bit position is either 1 (all other bits
	 * being zeros) or 0 (all other bits being ones) depending on the whether the
	 * argument is set to {@code true} or {@code false}.
	 * 
	 * @param allOnes a {@code boolean} value to set the sign bit
	 * @return a {@code BigInteger} where the sign bit is either 1 or 0 and all
	 *         other bits are the differentiated from it (either 0 or 1
	 *         respectively).
	 */
	public BigInteger getSignMask(boolean allOnes) {
		return allOnes ? getTrailingZeros(getBitLength() - 1) : getAllOnes(getBitLength() - 1);
	}

	/*
	 * Date: 13 Nov 2022-----------------------------------------------------------
	 * Time created: 21:50:36--------------------------------------------
	 */
	/**
	 * Gets a bit mask value where the exponent bits are either all ones (all other
	 * bits being zeros) or all zeros (all other bits being ones) depending on the
	 * whether the argument is set to {@code true} or {@code false}.
	 * 
	 * @param allOnes a {@code boolean} value to set the exponent bits
	 * @return a {@code BigInteger} where the exponent bits are either 1s or 0s and
	 *         all other bits are the differentiated from them (either 0s or 1s
	 *         respectively).
	 */
	public BigInteger getExponentMask(boolean allOnes) {
		return allOnes ? getAllOnes(exponentLength).shiftLeft(significandLength - 1)
				: getTrailingZeros(getBitLength() - 1).or(getAllOnes(significandLength - 1));
	}

	/*
	 * Date: 13 Nov 2022-----------------------------------------------------------
	 * Time created: 21:52:41--------------------------------------------
	 */
	/**
	 * Gets a bit mask value where the significand bits are either all ones (all
	 * other bits being zeros) or all zeros (all other bits being ones) depending on
	 * the whether the argument is set to {@code true} or {@code false}.
	 * 
	 * @param allOnes a {@code boolean} value to set the exponent bits
	 * @return a {@code BigInteger} where the significand bits are either all 1s or
	 *         all 0s and all other bits are the differentiated from them (either 0s
	 *         or 1s respectively).
	 */
	public BigInteger getSignificandMask(boolean allOnes) {
		return allOnes ? getAllOnes(significandLength - 1)
				: getAllOnes(exponentLength + 1).shiftLeft(significandLength - 1);
	}

	/*
	 * Date: 13 Nov 2022-----------------------------------------------------------
	 * Time created: 21:56:04--------------------------------------------
	 */
	/**
	 * Gets the number of bits allocated to {@code this} for storing the significand
	 * portion of the floating point number. This includes the implicit bit in the
	 * leading bit convention.
	 * 
	 * @return {@link #getBitLength()} - {@link #getExponentBitLength()} i.e the
	 *         number of bits used for storing the significand
	 */
	public int getSignificandBitLength() {
		return significandLength;
	}

	/*
	 * Date: 13 Nov 2022-----------------------------------------------------------
	 * Time created: 21:59:22--------------------------------------------
	 */
	/**
	 * Gets the number of bits allocated to {@code this} for storing the exponent
	 * portion of the floating point number.
	 * 
	 * @return {@link #getBitLength()} - {@link #getSignificandBitLength()} i.e the
	 *         number of bits used for storing the significand
	 */
	public int getExponentBitLength() {
		return exponentLength;
	}

	/*
	 * Date: 13 Nov 2022-----------------------------------------------------------
	 * Time created: 22:00:29--------------------------------------------
	 */
	/**
	 * Gets the total number of bits allocated to {@code this} for storing the
	 * floating point number.
	 * 
	 * @return the max number of bits this {@code BinaryFPPrecision} can use for
	 *         storing a floating point number
	 */
	public int getBitLength() {
		return exponentLength + significandLength;
	}

	/*
	 * Date: 13 Nov 2022-----------------------------------------------------------
	 * Time created: 22:02:01--------------------------------------------
	 */
	/**
	 * Gets the minimum binary exponent a normal value is allowed to have. This
	 * value is always negative.
	 * 
	 * @return the minimum binary exponent a normal value is allowed to have.
	 */
	public int getMinExponent() {
		return (getMaxExponent() - 1) * -1;
	}

	/*
	 * Date: 13 Nov 2022-----------------------------------------------------------
	 * Time created: 22:06:04--------------------------------------------
	 */
	/**
	 * Gets the maximum binary exponent a normal value is allowed to have. This
	 * value is always positive.
	 * 
	 * @return the maximum binary exponent a normal value is allowed to have.
	 */
	public int getMaxExponent() {
		return i(1).shiftLeft(exponentLength - 1).subtract(i(1)).intValue();
	}

	/*
	 * Date: 13 Nov 2022-----------------------------------------------------------
	 * Time created: 22:07:12--------------------------------------------
	 */
	/**
	 * Returns the bias used for the exponent part of this floating-point in
	 * relation to this {@code BinaryFPPrecision} in compliance to IEEE 754
	 * conventions. The bias is the value that is added to the binary exponent after
	 * the binary fraction has been normalised.
	 * 
	 * @return the exponent bias for this {@code BinaryFPPrecision}
	 */
	public int getBias() {
		return getMaxExponent();
	}

	/*
	 * Most Recent Date: 13 Nov 2022-----------------------------------------------
	 * Most recent time created: 22:10:24--------------------------------------
	 */
	/**
	 * Compares {@code this} with the argument and returns 1, 0 or -1 depending or
	 * wether {@code this} is greater than, equal to or less than the argument.
	 * {@code BinaryFPPrecision} objects are compared based on the largest value and
	 * the smallest normal value they can support. When they are equal in that
	 * regard, then they are compared by the number of bits their significand can
	 * support.
	 * 
	 * @param p the {@code BinaryFPPrecision} to be compared
	 * @return 1, 0 or -1 as {@code this} is greater than, equal to or less than the
	 *         argument.
	 */
	@Override
	public int compareTo(BinaryFPPrecision p) {
		if (exponentLength != p.exponentLength)
			return Integer.compare(exponentLength, p.exponentLength);
		return Integer.compare(significandLength, p.significandLength);
	}

	/*
	 * Most Recent Date: 13 Nov 2022-----------------------------------------------
	 * Most recent time created: 22:21:46--------------------------------------
	 */
	/**
	 * Compare {@code this} to the argument and returns {@code true} if {@code this}
	 * is equal to the argument.
	 * 
	 * @param o the object to be compared
	 * @return {@code true} if the argument is of type {@code BinaryFPPrecision} and
	 *         {@link #compareTo(BinaryFPPrecision)} returns {@code 0} for it or
	 *         else returns {@code false}
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof BinaryFPPrecision) {
			BinaryFPPrecision p = (BinaryFPPrecision) o;
			return compareTo(p) == 0;
		}
		return false;
	}

	/*
	 * Date: 13 Nov 2022-----------------------------------------------------------
	 * Time created: 22:24:47--------------------------------------------
	 */
	/**
	 * Creates a {@code BinaryFP} after parsing given decimal string. Note that due
	 * to the nuances of floating point decimal parsing, values may not translate
	 * verbatim. For example:
	 * 
	 * <pre>
	 * <code>
	 * BinaryFPPrecision p = new BinaryFPPrecision(8, 32);
	 * String n = "0.628";
	 * BinaryFP f = p.createFP(n);
	 * System.out.println(f.toBigDecimal());// prints 0.628000020
	 * </code>
	 * </pre>
	 * 
	 * However, if the mantissa is fully representable as an exponent of 5 or as an
	 * exponent of 10 then the exact value can be represented accurately.
	 * <p>
	 * -0.0, NaN and Infinities are all supported here as well as binary and decimal
	 * exponents.
	 * 
	 * @param n a decimal {@code String} that may use the 'e' or 'p' exponent
	 * @return a {@code BinaryFP} equivalent to decimal string. May return a special
	 *         value such as NaN or an infinity value depending on wether the
	 *         argument underflows or overflows this {@code BinaryFPPrecision}
	 */
	public BinaryFP createFP(String n) {
		return createFP(n, 10);
	}

	/*
	 * Date: 13 Nov 2022-----------------------------------------------------------
	 * Time created: 22:29:04--------------------------------------------
	 */
	/**
	 * Creates a {@code BinaryFP} from the {@code BigDecimal} argument. Note that
	 * due to the nuances of floating point decimal parsing, values may not
	 * translate verbatim. For example:
	 * 
	 * <pre>
	 * <code>
	 * BinaryFPPrecision p = new BinaryFPPrecision(8, 32);
	 * BigDecimal n = d("0.628");
	 * BinaryFP f = p.createFP(n);
	 * System.out.println(f.toBigDecimal());// prints 0.628000020
	 * </code>
	 * </pre>
	 * 
	 * However, if the mantissa is fully representable as an exponent of 5 or as an
	 * exponent of 10 then the exact value can be represented accurately.
	 * 
	 * @param n a {@code BigDecimal} value to be parsed.
	 * @return a {@code BinaryFP} equivalent to the argument. May return a special
	 *         value such as NaN or an infinity value depending on wether the
	 *         argument underflows or overflows this {@code BinaryFPPrecision}
	 */
	public BinaryFP createFP(BigDecimal n) {
		return new BinaryFP(n, 0, 0);
	}

	/*
	 * Date: 13 Nov 2022-----------------------------------------------------------
	 * Time created: 22:50:39--------------------------------------------
	 */
	/**
	 * Creates a {@code BinaryFP} using the layout specified by IEEE754 convention,
	 * with the sign (corresponding to the signum function for this value), biased
	 * exponent and significand (without the implicit bit). Note that if any if the
	 * exponent or significand has a bit length greater than specified, then
	 * truncation will occur, for exponents this happens from the left, for
	 * significand this happens from the right.
	 * 
	 * @param signum      the signum of the number
	 * @param exponent    the biased exponent
	 * @param significand the significand without the implicit bit
	 * @return a {@code BinaryFP} equivalent to the argument. May return a special
	 *         value such as NaN or an infinity value depending on wether the
	 *         argument underflows or overflows this {@code BinaryFPPrecision}
	 */
	public BinaryFP iEEE754(int signum, int exponent, BigInteger significand) {
		BigInteger exp = i(exponent).abs();
		BigInteger sig = significand.abs();
		return new BinaryFP(iEEE754(signum < 0 ? i(1) : i(0),
				exp.bitLength() > exponentLength ? clearMSB(exp, exp.bitLength() - exponentLength) : exp,
				sig.bitLength() > significandLength ? significand.shiftRight(sig.bitLength() - significandLength)
						: sig),
				0, 0);
	}

	/*
	 * Date: 13 Nov 2022-----------------------------------------------------------
	 * Time created: 23:00:13--------------------------------------------
	 */
	/**
	 * Creates a {@code BinaryFP} using the number string given in the specified
	 * radix. Note that due to the nuances of floating point decimal parsing, values
	 * may not translate verbatim. For example:
	 * 
	 * <pre>
	 * <code>
	 * BinaryFPPrecision p = new BinaryFPPrecision(8, 32);
	 * String n = "0.628";
	 * BinaryFP f = p.createFP(n, 10);
	 * System.out.println(f.toBigDecimal());// prints 0.628000020
	 * </code>
	 * </pre>
	 * 
	 * However, if the mantissa is fully representable as an exponent of 5 or as an
	 * exponent of 10 then the exact value can be represented accurately. Please
	 * note that any exponents ('p' or 'e') used must have it's value in the same
	 * radix as the number. For example:
	 * 
	 * <pre>
	 * <code>String s = "1a.00d5p-14";
	 * BinaryFPPrecision p = ...
	 * BinaryFP f = p.createFP(s, 16);//the exponent "-14" is assumed to be in base-16
	 * </code>
	 * </pre>
	 * <p>
	 * -0.0, NaN and Infinities are all supported here as well as binary and decimal
	 * exponents.
	 * 
	 * @param n     a number in the specified radix. Binary exponents (p) and
	 *              decimal exponents (e) (case insensitive) are the only exponents
	 *              allowed.
	 * @param radix the radix of the {@code String} argument.
	 * @return a {@code BinaryFP} equivalent to the argument. May return a special
	 *         value such as NaN or an infinity value depending on wether the
	 *         argument underflows or overflows this {@code BinaryFPPrecision}
	 * @see FloatAid#toString(String, int, int, int)
	 */
	public BinaryFP createFP(String n, int radix) {
		if (n.compareToIgnoreCase(NaN_STRING) == 0)
			return getNaN();
		else if (n.compareToIgnoreCase(INFINITY_STRING) == 0 || n.compareToIgnoreCase("+" + INFINITY_STRING) == 0)
			return getPositiveInfinity();
		else if (n.compareToIgnoreCase("-" + INFINITY_STRING) == 0)
			return getNegativeInfinity();
		else if (n.charAt(0) == '-' && isNumber(n) && d(n).signum() == 0)
			return getNegativeZero();
		else if (isNumber(n) && d(n).signum() == 0)
			return createFP(d(0));
//		BigDecimal d = toDecimal(n, radix);
		String s = FloatAid.toString(n, radix, 10, getMathContext(10, false, significandLength));
		return new BinaryFP(d(s), 0, 0);
	}

	/*
	 * Date: 14 Nov 2022-----------------------------------------------------------
	 * Time created: 02:52:54--------------------------------------------
	 */
	/**
	 * Returns the bit layout of the {@code String} argument in accordance with IEEE
	 * 754 floating point rules. The actual number returned has a bit pattern (in
	 * <i>big-endian</i> format) identical to the IEEE 754 pattern of the argument
	 * in relation to the {@code BinaryFPPrecision}. If the argument is signed, then
	 * the sign becomes the IEEE754 sign of the {@code BinaryFP} returned. Both
	 * {@code NaN} and {@code Infinity} are represented by this method, although
	 * {@code sNaN} and {@code qNaN} will have the same bit layout while positive
	 * and negative {@code Infinity} will each have their own layout.
	 * 
	 * @param layout an integer according to the IEEE754 specs
	 * @return a {@code BinaryFP} equivalent to the argument. May return a special
	 *         value such as NaN or an infinity value depending on wether the
	 *         argument underflows or overflows this {@code BinaryFPPrecision}
	 */
	public BinaryFP fromBitLayout(BigInteger layout) {
		return new BinaryFP(layout, 0, 0);
	}

	/*
	 * Date: 14 Nov 2022-----------------------------------------------------------
	 * Time created: 02:57:55--------------------------------------------
	 */
	/**
	 * Returns the {@code BinaryFP} equivalent of pi.
	 * 
	 * @return the {@code BinaryFP} equivalent of pi.
	 */
	public BinaryFP pi() {
		return createFP(Constants.pi(getMathContext(10, false, significandLength).getPrecision()));
	}

	/*
	 * Date: 14 Nov 2022-----------------------------------------------------------
	 * Time created: 02:59:31--------------------------------------------
	 */
	/**
	 * Returns the {@code BinaryFP} equivalent of e.
	 * 
	 * @return the {@code BinaryFP} equivalent of e.
	 */
	public BinaryFP e() {
		return createFP(Constants.e(getMathContext(10, false, significandLength).getPrecision()));
	}

	/*
	 * Date: 16 Nov 2022-----------------------------------------------------------
	 * Time created: 11:39:54--------------------------------------------
	 */
	/**
	 * Constant by which to multiply an angular value in radians to obtain anangular
	 * value in degrees.
	 * 
	 * @return 1 converted to degrees from radians i.e
	 *         <code>180 / {@linkplain #pi() &#x03c0;}</code>
	 */
	public BinaryFP degrees() {
		return createFP("180").divide(pi());
	}

	/*
	 * Date: 16 Nov 2022-----------------------------------------------------------
	 * Time created: 11:44:19--------------------------------------------
	 */
	/**
	 * Constant by which to multiply an angular value in radians to obtain anangular
	 * value in degrees.
	 * 
	 * @return 1 converted to radians from degrees i.e
	 *         <code>{@link #pi() &#x03c0;} / 180</code>
	 */
	public BinaryFP radians() {
		return pi().divide(createFP("180.0"));
	}

	/*
	 * Date: 23 Nov 2022-----------------------------------------------------------
	 * Time created: 05:57:13--------------------------------------------
	 */
	/**
	 * Gets the bit index of this particular exception in the significand portion
	 * (big endian). When an exception occurs, this index is set to 1 in the NaN
	 * returned.
	 * 
	 * @return
	 */
	int getExceptionIndex(int exception) {
		switch (exception) {
		case FP_EXCEPTION_INVALID_OP:
		default:
			return getExceptionIndex(FP_EXCEPTION_DIVSION_BY_ZERO) - 1;
		case FP_EXCEPTION_DIVSION_BY_ZERO:
			return significandLength / 2;
		case FP_EXCEPTION_OVERFLOW:
			return significandLength - 2;
		case FP_EXCEPTION_UNDERFLOW:
			return 0;
		case FP_EXCEPTION_INEXACT_CALC:
			return getExceptionIndex(FP_EXCEPTION_DIVSION_BY_ZERO) - 2;
		}
	}

	/*
	 * Date: 14 Nov 2022-----------------------------------------------------------
	 * Time created: 02:59:54--------------------------------------------
	 */
	/**
	 * Returns the IEEE754 bit layout of the argument or any of the special values
	 * as a representation of the argument.
	 * 
	 * @param n a {@code BigDecimal} value
	 * @return a {@code BigInteger} which is the IEEE754 bit layout of the argument
	 *         or a special value if an exception occurs (such as an underflow or
	 *         overflow)
	 * @implNote This Algorithm does not round properly
	 */
	BigInteger toIEEE754Layout(BigDecimal n) {
		if (n.signum() == 0)
			return i(0);
		/*
		 * Math.pow(2, sigBits + maxExp + roundBit (guard bit) + All the bits after the
		 * rounding bit to the right)
		 */
		int mantissLen = getSignificandBitLength() + getMaxExponent() + 1 + 4;// p.getBias() * 2;
		BigInteger[] b = fromDecimal(n, 2, mantissLen);
		BigInteger exp = i(-getBias());
		BigInteger sigMask = getTrailingZeros(significandLength - 1);// i(string('1', p.getSignificandBits()));
		BigInteger sig = sigMask;
		if (b[1].signum() != 0) {// has an integer part
			int fml = b[3].abs().intValue();// Full mantissa length
			int lz = fml - b[2].bitLength();// number of leading zeros
			BigInteger mantissa = b[2].shiftRight(fml - (getSignificandBitLength() - b[1].bitLength()));
			/*
			 * Does not have a mantissa part or cannot include the mantissa part for some
			 * reason
			 */
			if (mantissa.bitCount() == 0 || b[1].bitLength() >= getSignificandBitLength()) {
				sig = b[1].shiftRight(b[1].bitLength() - getSignificandBitLength());
				/*
				 * If the integer bit length is greater than the required significand bits
				 */
				if (b[1].bitLength() > getSignificandBitLength()) {
					BigInteger roundBit = // rounding bit
							b[2].testBit(b[1].bitLength() - (getSignificandBitLength() + 1)) ? i(1) : i(0);
					/*
					 * All the bits after the rounding bit to the right. If there is none let it
					 * default to 0
					 */
					BigInteger arb = i(1);
					if (b[1].bitLength() > getSignificandBitLength() + 1) {
						arb = i(1).shiftLeft(b[1].bitLength() - getSignificandBitLength() - 1).subtract(i(1)).and(b[2]);
					}
					int sigLen = sig.bitLength();// Significand bit length b4 rounding
					sig = arb.bitCount() > 0 ? sig.add(roundBit) : sig.testBit(0) ? sig.add(roundBit) : sig;
					if (sig.bitLength() > sigLen) {
						int dist = sig.bitLength() - sigLen;
						sig = sig.shiftRight(dist);
						b[1] = b[1].add(i(dist));
					}
				}
			} else {// has mantissa part
				sig = b[1].shiftLeft(lz + mantissa.bitLength()).or(mantissa);
				/*
				 * If there is a guard or round bit remaining
				 */
				if (fml >= (getSignificandBitLength() - b[1].bitLength())
						&& Math.signum(getSignificandBitLength() - b[1].bitLength()) >= 0) {
					BigInteger roundBit = // rounding bit
							b[2].testBit(fml - (getSignificandBitLength() - b[1].bitLength()) - 1) ? i(1) : i(0);
					/*
					 * All the bits after the rounding bit to the right. If there is none let it
					 * default to 0
					 */
					BigInteger arb = i(1);
					if (b[2].bitLength() > (mantissa.bitLength() + 1)) {
						arb = i(1).shiftLeft(b[2].bitLength() - mantissa.bitLength() - 1).subtract(i(1)).and(b[2]);
					}
					int sigLen = sig.bitLength();// Significand bit length b4 rounding
					sig = arb.bitCount() > 0 ? sig.add(roundBit) : sig.testBit(0) ? sig.add(roundBit) : sig;
					if (sig.bitLength() > sigLen) {
						int dist = sig.bitLength() - sigLen;
						sig = sig.shiftRight(dist);
						b[1] = b[1].add(i(dist));
					}
				}
			}
			exp = i(b[1].bitLength() - 1);
			if (exp.compareTo(i(getMaxExponent())) > 0) {// Infinity value
				exp = i(2).pow(getExponentBitLength()).subtract(i(getBias())).subtract(i(1));
				sig = i(0);
			}
		} else if (b[2].signum() != 0) {// does not have integer part
			int fml = b[3].abs().intValue();// Full mantissa length
			int lz = fml - b[2].bitLength();// number of leading zeros
			if (lz > Math.abs(getMinExponent())) {// subnormal, possibly NaN
				/*
				 * number of leading zeros to retain as part of the significand
				 */
				int lzr = lz - Math.abs(getMinExponent());
				BigInteger mantissa = b[2].shiftRight(b[2].bitLength() - ((getSignificandBitLength() - 1) - lzr));
				sig = sigMask.or(mantissa);
				// We are supposed to assign the exponent here but we are not going to do so
				// because it is already as we want it. Also, we are rounding sub-normals
				if (b[2].bitLength() >= (getSignificandBitLength() - lzr)// ROUNDING TIME!!!
						&& Math.signum(getSignificandBitLength() - lzr) >= 0) {
					BigInteger roundBit = // guard or rounding bit
							b[2].testBit(b[2].bitLength() - (getSignificandBitLength() - lzr)) ? i(1) : i(0);
					/*
					 * All the bits after the rounding bit to the right. If there is none let it
					 * default to 1
					 */
					BigInteger arb = i(1);
					if (b[2].bitLength() > mantissa.bitLength() + 1 + lzr) {
						arb = i(2).pow(b[2].bitLength() - (mantissa.bitLength() + lzr)).subtract(i(1)).and(b[2]);
					}
					int sigLen = sig.bitLength();// Significand bit length b4 rounding
					sig = arb.bitCount() > 0 ? sig.add(roundBit) : sig.testBit(0) ? sig.add(roundBit) : sig;
					/*
					 * If the following block is executed, it prevents this value from appearing as
					 * subnormal
					 */
					if (sig.bitLength() > sigLen) {// TODO: Brittle code!
						int dist = sig.bitLength() - sigLen;
						sig = sig.shiftRight(dist);
						lz -= (dist);
						if (lz <= Math.abs(getMinExponent()))
							exp = i(-lz);
					}

				} else if (sig.signum() == 0) {// NaN!
					exp = i(2).pow(getExponentBitLength()).subtract(i(getBias())).subtract(i(1));
					/*
					 * 2 1 bits and all zeroes to the left
					 */
					sig = i(3).shiftLeft(getSignificandBitLength() - 2);
				}
			} else {// normal range
				BigInteger mantissa = b[2].shiftRight(b[2].bitLength() - getSignificandBitLength());
				sig = sigMask.or(mantissa);
				/*
				 * If there is a guard or round bit remaining
				 */
				if (b[2].bitLength() > getSignificandBitLength()) {
					BigInteger roundBit = // rounding bit
							b[2].testBit(b[2].bitLength() - (getSignificandBitLength() + 1)) ? i(1) : i(0);
					/*
					 * All the bits after the rounding bit to the right. If there is none let it
					 * default to 1
					 */
					BigInteger arb = i(1);
					if (b[2].bitLength() > (mantissa.bitLength() + 1)) {
						arb = i(1).shiftLeft(b[2].bitLength() - (mantissa.bitLength() + 1)).subtract(i(1)).and(b[2]);
					}
					int sigLen = sig.bitLength();// Significand bit length b4 rounding
					sig = arb.bitCount() > 0 ? sig.add(roundBit) : sig.testBit(0) ? sig.add(roundBit) : sig;
					if (sig.bitLength() > sigLen) {
						int dist = sig.bitLength() - sigLen;
						sig = sig.shiftRight(dist);
						lz -= (dist);
					}
				}
				exp = i(-(lz + 1));
			}
		}
		/*
		 * could not compute a valid non-zero mantissa because the value is underflowed,
		 * therefore it is considered NaN
		 */
		else if (b[1].compareTo(i(getMinExponent())) < 0) {
			exp = i(2).pow(getExponentBitLength()).subtract(i(getBias())).subtract(i(1));
			sig = i(3).shiftLeft(getSignificandBitLength() - 2);
		}
		exp = exp.add(i(getBias())).abs();
		sig = sigMask.xor(sig);

		return iEEE754(b[0], exp, sig);
	}

	/*
	 * Date: 25 Nov 2022-----------------------------------------------------------
	 * Time created: 01:27:48--------------------------------------------
	 */
	/**
	 * Extracts the integer stored at index 1, rounds it (if the bit length >
	 * {@link BinaryFPPrecision#significandLength}) or pad with trailing zero(es)
	 * (if the bit length < {@link BinaryFPPrecision#significandLength}) and
	 * normalizes the exponent at index 3. After this method returns, the value at
	 * index 1 will be the significand including the implicit bit and index 3 will
	 * contain the normalized exponent.
	 * 
	 * @param p a context precision for the extraction of the bits
	 * @param b a 5-length array of {@code BigInteger} where index 1 and 3 must not
	 *          be {@code null}.
	 */
	static void convertIntToSig(BigInteger[] b, BinaryFPPrecision p) {
		b[3] = i(b[1].bitLength() - 1);
		/* Needs rounding */
		if (b[1].bitLength() > p.getSignificandBitLength()) {
			roundToSig(b, p);
		} /* no need for rounding, just pad with zero(es) */
		else if (b[1].bitLength() < p.getSignificandBitLength())
			b[1] = b[1].shiftLeft(p.getSignificandBitLength() - b[1].bitLength());

	}

	/*
	 * Date: 25 Nov 2022-----------------------------------------------------------
	 * Time created: 01:31:12--------------------------------------------
	 */
	/**
	 * Extracts the integer stored at index 1 and the mantissa stored at index 2,
	 * merges (to from the IEEE-754 bit layout) and normalizes them to form the
	 * significand and normalizes the exponent at index 3. After this method
	 * returns, the value at index 1 will be the significand including the implicit
	 * bit and index 3 will contain the normalized exponent.
	 * 
	 * @param p a context precision for the extraction of the bits
	 * @param b a 5-length array of {@code BigInteger} where index 1, 2 and 3 must
	 *          not be {@code null}.
	 */
	static void convertToIntAndMantToSig(BigInteger[] b, BinaryFPPrecision p) {
		/*
		 * The number was too small so an underflow occurred hence even though the
		 * mantissa was non-zero a non-zero binary value was ot found
		 */
		if (b[2].signum() == 0) {
			/*
			 * Setting the exponent safely under a conditional to prevent bugs from wrong
			 * leading 0s value
			 */
			b[3] = i(b[1].bitLength() - 1);
			if (b[1].bitLength() < p.getSignificandBitLength())
				b[1] = b[1].shiftLeft(p.getSignificandBitLength() - b[1].bitLength());
		} else {// a non-zero mantissa
			// Leading zero(es)
			int lz = b[3].intValue() - b[2].bitLength();
			b[3] = i(b[1].bitLength() - 1);
			b[1] = b[1].shiftLeft(lz + b[2].bitLength()).or(b[2]);

			/* No rounding needed */
			if (b[1].bitLength() < p.getSignificandBitLength())
				b[1] = b[1].shiftLeft(p.getSignificandBitLength() - b[1].bitLength());
			// rounding needed
			else if (b[1].bitLength() > p.getSignificandBitLength())
				roundToSig(b, p);
		}
	}

	/*
	 * Date: 25 Nov 2022-----------------------------------------------------------
	 * Time created: 01:45:24--------------------------------------------
	 */
	/**
	 * Extracts the value at index 2 (wich represents the mantissa), normalizes it
	 * to form the significand and normalizes the exponent accordingly and store the
	 * result at index 1.
	 * 
	 * @param b a 5-length array of {@code BigInteger} where index 2, 3 and 4 must
	 *          not be {@code null}.
	 * @param p a context precision for the extraction of the bits
	 */
	static void convertMantToSig(BigInteger[] b, BinaryFPPrecision p) {
		/* leading zero(es) */
		int lz = b[3].intValue() - b[2].bitLength();
		b[3] = i(-(lz + 1));
		if (b[3].compareTo(i(p.getMinExponent())) >= 0) {// Normal range
			convertMantToNormalSig(b, p);
		} else if (b[3].compareTo(i(p.getMinExponent())) < 0) {// Subnormal range or NaN
			convertMantToSubSig(b, lz, p);
		}
	}

	/*
	 * Date: 25 Nov 2022-----------------------------------------------------------
	 * Time created: 01:48:51--------------------------------------------
	 */
	/**
	 * Extracts the value at index 2 (wich represents the mantissa), normalizes it
	 * to form the significand (and only normalizes the exponent accordingly if
	 * rounding causes a shift leftwards) and store the result at index 1. This
	 * methods treats the value at index 2 as a non-subnormal value and that the
	 * exponent is already normalised.
	 * 
	 * @param b a 5-length array of {@code BigInteger} where index 2 and 3 must not
	 *          be {@code null}.
	 * @param p a context precision for the extraction of the bits
	 */
	static void convertMantToNormalSig(BigInteger[] b, BinaryFPPrecision p) {
		/* No rounding needed */
		if (b[2].bitLength() < p.getSignificandBitLength()) {
			b[1] = b[2].shiftLeft(p.getSignificandBitLength() - b[2].bitLength());
		} else if (b[2].bitLength() == p.getSignificandBitLength())
			b[1] = b[2];
		else {// rounding required
			int i = b[2].bitLength() - p.getSignificandBitLength();
			b[1] = b[2].shiftRight(b[2].bitLength() - p.getSignificandBitLength());
			/*
			 * Guard bits. The code below aims to keep leading zeros hence an additional bit
			 * is used to prevent the 0s from "falling" to the left
			 */
			BigInteger gb = FloatAid.getTrailingZeros(i).or(b[2].and(FloatAid.getAllOnes(i)));
			/* round bit */
			BigInteger rb = gb.bitLength() <= 2 ? i(2).xor(gb) : gb.shiftRight(gb.bitLength() - 2).xor(i(2));

			doRounding(b, rb, gb, p);
		}
	}

	/*
	 * Date: 25 Nov 2022-----------------------------------------------------------
	 * Time created: 02:34:49--------------------------------------------
	 */
	/**
	 * Extracts the value at index 2 (wich represents the mantissa), normalizes it
	 * to form the significand (and only normalizes the exponent accordingly if
	 * rounding causes a shift leftwards) and store the result at index 1. This
	 * methods treats the value at index 2 as a subnormal value and the exponent
	 * will be {@link BinaryFPPrecision#getMinExponent()} (if rounding does not
	 * cause a leftward shift).
	 * 
	 * @param b  a 5-length array of {@code BigInteger} where index 2 must not be
	 *           {@code null}.
	 * @param lz the number of leading zero(es). This is necessary because the value
	 *           at index 3 (which was used to calculate this parameter) has been
	 *           mutated at
	 *           {@link #convertMantToSig(BigInteger[], BinaryFPPrecision)}
	 * @param p  a context precision for the extraction of the bits
	 */
	static void convertMantToSubSig(BigInteger[] b, int lz, BinaryFPPrecision p) {
		/*
		 * number of leading zeroes to retain as part of the significand
		 */
		int lzr = lz - Math.abs(p.getMinExponent());
		b[3] = i(p.getMinExponent() - 1);
		b[1] = FloatAid.getTrailingZeros(lzr + b[2].bitLength()).or(b[2]);

		if (b[1].signum() == 0) {// NaN
			b[4] = i(1);// value to suggest overflow or carry bit
			b[3] = i(p.getMaxExponent() + 1);
			b[1] = i(3).shiftLeft(p.getSignificandBitLength() - 2);
			/* Set the exception index within the NaN */
			b[1] = b[1].setBit(p.getExceptionIndex(BinaryFPPrecision.FP_EXCEPTION_UNDERFLOW));
		}
		/* No rounding needed */
		else if (b[1].bitLength() < p.getSignificandBitLength())
			b[1] = b[1].shiftLeft(p.getSignificandBitLength() - b[1].bitLength());
		/* Rounding required */
		else if (b[1].bitLength() > p.getSignificandBitLength()) {
			int i = b[1].bitLength() - p.getSignificandBitLength();
			b[1] = b[1].shiftRight(i);
			/*
			 * Guard bits. The code below aims to keep leading zeros hence an additional bit
			 * is used to prevent the 0s from "falling" to the left
			 */
			BigInteger gb = FloatAid.getTrailingZeros(i).or(b[2].and(FloatAid.getAllOnes(i)));
			/* round bit */
			BigInteger rb = gb.bitLength() <= 2 ? i(2).xor(gb) : gb.shiftRight(gb.bitLength() - 2).xor(i(2));

			doRounding(b, rb, gb, p);
		}
	}

	/*
	 * Date: 25 Nov 2022-----------------------------------------------------------
	 * Time created: 02:39:17--------------------------------------------
	 */
	/**
	 * Extracts all the remaining bits after the significand (stored at index 1) and
	 * uses them to calculate a proper rounded significand and store the result at
	 * index 1 using the rounding mode provided by the precision object
	 * 
	 * @param b a 5-length array of {@code BigInteger} where index 1 and 3 must not
	 *          be {@code null}.
	 * @param p a context precision for the extraction of the bits
	 */
	static void roundToSig(BigInteger[] b, BinaryFPPrecision p) {
		int i = b[1].bitLength() - p.getSignificandBitLength();
		/*
		 * Guard bits. The code below aims to keep leading zeros hence an additional bit
		 * is used to prevent the 0s from "falling" to the left
		 */
		BigInteger gb = FloatAid.getTrailingZeros(i).or(b[1].and(FloatAid.getAllOnes(i)));
		/* round bit */
		BigInteger rb = gb.bitLength() <= 2 ? i(2).xor(gb) : gb.shiftRight(gb.bitLength() - 2).xor(i(2));
		b[1] = b[1].shiftRight(i);

		doRounding(b, rb, gb, p);
	}

	/*
	 * Date: 25 Nov 2022-----------------------------------------------------------
	 * Time created: 02:43:52--------------------------------------------
	 */
	/**
	 * Rounds the value at index 1 using the given round bit and guard bits. If the
	 * rounding causes the value's bit length to increase, then the exponent is
	 * normalised as well. E.g 1111 with a round bit of 1 when rounded up will
	 * result in 10000 hence a normalization of the exponent is needed.
	 * 
	 * @param b  a 5-length array of {@code BigInteger} where index 1 and 3 must not
	 *           be {@code null}.
	 * @param rb the rounding bit
	 * @param gb all the guard (remaining) bits after the significand. This includes
	 *           a leading bit (to ensure that leading zeros are visible), the round
	 *           bit and every other bit after the round bit.
	 * @param p  a context precision for the extraction of the bits
	 */
	static void doRounding(BigInteger[] b, BigInteger rb, BigInteger gb, BinaryFPPrecision p) {
		/* save the bit length for later comparison */
		int valBitLength = b[1].bitLength();
		switch (p.getRoundingMode()) {
		case CEILING:// towards positive infinity
			// this value is negative
			if (b[0].bitCount() == 0) {
				/* Rounding bit is 0 but all the guard bits are one then round */
				if (rb.bitCount() == 0 && gb.bitCount() == gb.bitLength() && gb.bitLength() > 2)
					b[1] = b[1].add(i(1));
				else
					b[1] = b[1].add(rb);
			}
			break;
		case FLOOR:// towards negative infinity
			// this value is negative
			if (b[0].bitCount() != 0) {
				/* Rounding bit is 0 but all the guard bits are one then round */
				if (rb.bitCount() == 0 && gb.bitCount() == gb.bitLength() && gb.bitLength() > 2)
					b[1] = b[1].add(i(1));
				else
					b[1] = b[1].add(rb);
			}
			break;
		case UP:// away from 0 i.e always round
			/* Rounding bit is 0 but all the guard bits are one then round */
			if (rb.bitCount() == 0 && gb.bitCount() == gb.bitLength() && gb.bitLength() > 2)
				b[1] = b[1].add(i(1));
			else
				b[1] = b[1].add(rb);
			break;
		case HALF_DOWN:// only round if fraction > 0.5
			/* Rounding bit is 1 but the guard bits are not all 0 */
			if (rb.bitCount() == 1 && gb.bitLength() > 2 && clearMSB(gb, 2).bitCount() > 1)
				b[1] = b[1].add(rb);
			break;
		case HALF_EVEN:// ties to even up
			// This value is even
			if (b[1].testBit(0)) {
				b[1] = b[1].add(rb);
			}
			break;
		case HALF_UP:// only round if fraction >= 0.5
			b[1] = b[1].add(rb);
			break;
		case DOWN:// towards 0 i.e never round (truncate)
			break;
		case UNNECESSARY:// do not round
		default:
			break;
		}

		/*
		 * if the length of the value increased as a result of the rounding operations,
		 * then shift and normalize accordingly
		 */
		if (b[1].bitLength() > valBitLength) {
			int i = b[1].bitLength() - valBitLength;
			b[1] = b[1].shiftRight(i);
			b[3] = b[3].add(i(i));
		}
	}

	/*
	 * Date: 25 Nov 2022-----------------------------------------------------------
	 * Time created: 00:41:47--------------------------------------------
	 */
	/**
	 * Extracts the bits from the floating point decimal representation to an
	 * IEEE-754 bit layout and stores the result in index 0 of the given array
	 * 
	 * @param x the number to be converted
	 * @param p a context precision for the extraction of the bits
	 * @param b a 5-length array of {@code BigInteger}
	 */
	static void toBits(BigDecimal x, BinaryFPPrecision p, BigInteger[] b) {
		b[0] = x.signum() < 0 ? i(1) : i(0);
		x = x.abs();
		b[1] = x.toBigInteger();
		x = x.subtract(d(b[1]));
		if (b[1].signum() != 0) {// Integer part is non-zero
			/*
			 * no mantissa part or the integer part is big enough to fill the significand by
			 * itself
			 */
			if (x.signum() == 0 || b[1].bitLength() >= p.getSignificandBitLength()) {
				/* void method that mutates the array */
				convertIntToSig(b, p);
			} else {// mantissa and integer needed to fill the significand
				BigInteger[] temp = FloatAid.fromDecimal(x, 2, (p.getSignificandBitLength() - b[1].bitLength()) + 8);
				b[2] = temp[2];
				b[3] = temp[3].abs();

				convertToIntAndMantToSig(b, p);
			}

			if (b[3].compareTo(i(p.getMaxExponent())) > 0) {// Infinity value
				b[4] = i(1);// value to suggest overflow or carry bit
				b[3] = i(p.getMaxExponent() + 1);
				b[1] = i(0);
			}
		} else if (x.signum() != 0) {// Integer part is zero
			BigInteger[] temp = FloatAid.fromDecimal(x, 2, p.getSignificandBitLength() + p.getMaxExponent() + 8);

			b[2] = temp[2];
			b[3] = temp[3].abs();

			convertMantToSig(b, p);
		} else {// Zero
			b[3] = i(-p.getBias());
			b[1] = b[2] = FloatAid.getTrailingZeros(p.getSignificandBitLength() - 1);
		}
		b[3] = b[3].add(i(p.getBias()));
		b[1] = b[1].xor(FloatAid.getTrailingZeros(p.getSignificandBitLength() - 1));

		converToInt(b, p);
	}

	/*
	 * Date: 25 Nov 2022-----------------------------------------------------------
	 * Time created: 00:36:36--------------------------------------------
	 */
	/**
	 * Welds the sign (index 0), significand (index 1) and exponent (index 3) from
	 * the given array into a single {@code BigInteger} which is the IEEE754
	 * equivalent of the 3, and stores the result in index 0 of the input array.
	 * 
	 * @param b an array where indexes 0, 1, and 3 contains the sign, significand
	 *          and exponent respectively
	 * @param p a given precision
	 */
	static void converToInt(BigInteger[] b, BinaryFPPrecision p) {
		if (b[0].bitLength() > 1)
			throw new ArithmeticException("there is more than one bit in the sign");
		if (b[1].bitLength() > p.getSignificandBitLength())
			throw new ArithmeticException("there are more bits in the significand than the precision specified");
		if (b[3].bitLength() > p.getExponentBitLength())
			throw new ArithmeticException("there are more bits in the exponent than the precision specified");
		b[0] = b[0].shiftLeft(p.getBitLength() - 1).or(b[3].shiftLeft(p.getSignificandBitLength() - 1)).or(b[1]);
	}

	/*
	 * Date: 24 Nov 2022-----------------------------------------------------------
	 * Time created: 23:56:50--------------------------------------------
	 */
	/**
	 * Extracts the bits lay of the floating point representation and returns an
	 * array of 2-length containing the result and the exception bit (if any
	 * exception occurred). This algorithm uses extra 8 bits for rounding purposes
	 * 
	 * @param n a {@code BigDecimal}
	 * @return a 2-length array.
	 */
	private BigInteger[] toBits(BigDecimal n) {
		BigInteger[] b = new BigInteger[5];
		toBits(n, this, b);
		return b;
	}

	/*
	 * Date: 14 Nov 2022-----------------------------------------------------------
	 * Time created: 03:24:31--------------------------------------------
	 */
	/**
	 * Fuses the arguments into a IEEE754 bit layout and returns the result. Some of
	 * the arguments may be truncated
	 * 
	 * @param sign        the IEEE754 sign i.e a 0 for non-negative values and a 1
	 *                    for negative values
	 * @param exponent    the already biased exponent
	 * @param significand the significand whose bit length is not greater than
	 *                    {@link #significandLength} - 1 i.e the significand without
	 *                    the implicit bit.
	 * @return a {@code BigInteger} whose bit layout is the same as the argument
	 */
	private BigInteger iEEE754(BigInteger sign, BigInteger exponent, BigInteger significand) {
		BigInteger i = sign.shiftLeft(getBitLength() - 1);
		i = i.or(exponent.shiftLeft(getSignificandBitLength() - 1));
		return i.or(significand);
	}

	/*
	 * Date: 14 Nov 2022-----------------------------------------------------------
	 * Time created: 03:24:37--------------------------------------------
	 */
	/**
	 * Clips and truncates the argument so that it is within the bounds of
	 * {@link #getBitLength()} and returns the result. If the argument is signed,
	 * then the sign becomes the IEEE754 sign of the {@code BigInteger} returned.
	 * Because a {@code BigInteger} has arbitrary length, it may have to be
	 * truncated to fit the current bit length of the {@code BinaryFPPrecision}.
	 * 
	 * @param i the {@code BigInteger} to be examined
	 * @return the argument truncated and clipped to fit {@link #getBitLength()}.
	 */
	private BigInteger sanitise(BigInteger i) {
		BigInteger[] r = { i, i(0) };
		fromDecimal(getBitLength(), r);
		i = r[0];
		/*
		 * Check for signed NaNs, because we dont't want signed NaNs so we remove the
		 * sign bit and compare to the nan bit layout
		 */
		BigInteger nan = getExponentMask(true).or(getTrailingZeros(significandLength - 2));
		if (i.bitLength() == getBitLength() && clearMSB(i, 1).compareTo(nan) == 0)
			i = nan;

		return i;
	}

	/*
	 * Date: 14 Nov 2022-----------------------------------------------------------
	 * Time created: 03:24:44---------------------------------------------------
	 */
	/**
	 * Field for the number of bits for storing the exponent field of a floating
	 * point number
	 */
	private final int exponentLength;
	/*
	 * Date: 14 Nov 2022-----------------------------------------------------------
	 * Time created: 03:24:49---------------------------------------------------
	 */
	/**
	 * Field for the number of bits for storing the significand field of a floating
	 * point number
	 */
	private final int significandLength;

	/*
	 * Date: 23 Nov 2022-----------------------------------------------------------
	 * Time created: 22:52:29---------------------------------------------------
	 */
	/**
	 * Field for the rounding mode that is currently being used
	 */
	private final RoundingMode rm;

	/*
	 * Date: 14 Nov 2022-----------------------------------------------------------
	 * Time created: 03:53:41---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base.value------------------------------------------------
	 * Project: CalculatorProject------------------------------------------------
	 * File:
	 * BinaryFPPrecision.java------------------------------------------------------
	 * Class name: BinaryFP------------------------------------------------
	 */
	/**
	 * A floating point implementation, primarily representable as an integer, that
	 * can be created only by a {@code BinaryFPPrecision}.
	 * <p>
	 * It supports the following special values:
	 * <ul>
	 * <li>-0.0</li>
	 * <li>NaN - Only one type of NaN is supported. In the future sNaN and qNaN will
	 * be supported.</li>
	 * <li>Infinities - Both negative and positive infinity are supported</li>
	 * </ul>
	 * All these are reflected on arithmetic operations as well as when converted to
	 * string. Apart from these, it can do basic arithmetic operations as well as
	 * have elementary functions performed on it.
	 * <p>
	 * This class is meant as a permanent replacement for the {@code Precision} and
	 * {@code FloatPoint} classes (both of which are semi deprecated). As an
	 * alternative, it supercedes those classes because it is faster, more accurate,
	 * portable, flexible precision handling, seamlessly represents special values
	 * etc. In other words, it is better than those classes in every way.
	 * <p>
	 * Although it was the design goal of this class to return results of operations
	 * by computing them bit-by-bit, most of the operations are not so and instead
	 * rely on the {@link Arith} api. The strict math (bit-by-bit) operations
	 * include:
	 * <ul>
	 * <li>{@link #ceil()}</li>
	 * <li>{@link #floor()}</li>
	 * <li>{@link #rint()}</li>
	 * <li>{@link #copySign(BinaryFP)}</li>
	 * <li>{@link #nextAfter(BinaryFP)}</li>
	 * <li>{@link #nextDown()}</li>
	 * <li>{@link #nextUp()}</li>
	 * <li>{@link #abs()}</li>
	 * <li>{@link #negate()}</li>
	 * <li>{@link #ulp()}</li>
	 * </ul>
	 * In future releases, this class will have all of it's operations and
	 * elementary function be strict.
	 * <p>
	 * All methods that take in another {@code BinaryFP} argument make use of
	 * {@link #cast(BinaryFPPrecision)} to the bigger {@code BinaryFPPrecision} if
	 * {@code this} and the argument are of different precision. This is analogous
	 * to doing:
	 * 
	 * <pre>
	 * <code>double d = 5.0;
	 * float f = 8.0f;
	 * System.out.println(Math.pow(d, f));//calls the double method overload
	 * </code>
	 * </pre>
	 * <p>
	 * Certain values may throw an exception when trying to convert them using
	 * {@link #toBigDecimal()} or any of the methods that return a {@code String}.
	 * This is because these conversions rely on
	 * {@code BigDecimal#pow(int, MathContext)} which has a limitation of
	 * {@code 999999999} for it's {@code int} argument.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 */
	public class BinaryFP implements Comparable<BinaryFP>, Serializable {

		////////////////////////////////////////////////////////////
		/////////////////////// Constants /////////////////////////
		//////////////////////////////////////////////////////////
		/*
		 * Date: 2 Nov 2022-----------------------------------------------------------
		 * Time created: 10:51:24---------------------------------------------------
		 */
		/**
		 * Field for serial
		 */
		private static final long serialVersionUID = -5957182892012591385L;

		////////////////////////////////////////////////////////////
		///////////////////// Constructors ////////////////////////
		//////////////////////////////////////////////////////////

		/*
		 * Date: 14 Nov 2022-----------------------------------------------------------
		 * Time created: 16:36:11---------------------------------------------------
		 */
		/**
		 * Constructs a {@code BinaryFP} from a {@code BigDecimal}. The value retrieved
		 * from {@link #toBigDecimal()} may not be equal to the argument considering the
		 * nuances of floating point.
		 * 
		 * @param n         a {@code BigDecimal}
		 * @param exception the current exception
		 * @param carry     the current carry
		 */
		BinaryFP(BigDecimal n, int exception, int carry) {
//			this(toIEEE754Layout(n), exception, carry);
			BigInteger[] b = toBits(n);
			val = b[0];
			this.carry = b[4];
			this.exception = 0;
		}

		/*
		 * Date: 14 Nov 2022-----------------------------------------------------------
		 * Time created: 16:38:29---------------------------------------------------
		 */
		/**
		 * Constructs a {@code BinaryFP} from a {@code BigInteger} which is expected to
		 * be in IEEE754 bit layout. This value may be retrieved from
		 * {@link #toBigInteger()}. If the argument is signed, then the sign becomes the
		 * IEEE754 sign of the {@code this}. Because a {@code BigInteger} has arbitrary
		 * length, it may have to be truncated to fit the current bit length of the
		 * {@code BinaryFPPrecision}.
		 * 
		 * @param layout    a {@code BigInteger} which is in IEEE754 bit layout. If this
		 *                  argument has a bit length greater than
		 *                  {@link BinaryFPPrecision#getBitLength()}, then truncation
		 *                  from the left is made.
		 * @param exception the current exception
		 * @param carry     the current carry
		 */
		BinaryFP(BigInteger layout, int exception, int carry) {
			this.val = sanitise(layout);
			this.exception = exception;
			this.carry = i(carry);
		}

		////////////////////////////////////////////////////////////
		/////////////////////// Accessors /////////////////////////
		//////////////////////////////////////////////////////////

		/*
		 * Date: 14 Nov 2022-----------------------------------------------------------
		 * Time created: 17:03:54--------------------------------------------
		 */
		/**
		 * Returns the {@code BinaryFPPrecision} object that was used to create
		 * {@code this}.
		 * 
		 * @return the associated {@code BinaryFPPrecision}
		 */
		public BinaryFPPrecision getPrecision() {
			return BinaryFPPrecision.this;
		}

		/*
		 * Date: 14 Nov 2022-----------------------------------------------------------
		 * Time created: 16:43:48--------------------------------------------
		 */
		/**
		 * Returns the significand bits (without the implicit bit) from {@code this}
		 * 
		 * @return the significand bits
		 */
		public BigInteger getSignificand() {
			return BinaryFPPrecision.this.getSignificandMask(true).and(val);
		}

		/*
		 * Date: 14 Nov 2022-----------------------------------------------------------
		 * Time created: 16:45:14--------------------------------------------
		 */
		/**
		 * Returns the exponent bits from {@code this}
		 * 
		 * @return the exponent bits
		 */
		public int getExponent() {
			return BinaryFPPrecision.this.getExponentMask(true).and(val).shiftRight(getSignificandBitLength() - 1)
					.subtract(i(getBias())).intValue();
		}

		/*
		 * Date: 14 Nov 2022-----------------------------------------------------------
		 * Time created: 16:45:45--------------------------------------------
		 */
		/**
		 * Converts {@code this} to it's decimal equivalent.
		 * <p>
		 * -0.0, NaNs and infinities have limited supported via a small subclass because
		 * they have no way to be represented in a {@code BigDecimal}. In this value,
		 * arithmetic and mathematical operations are broken and don't work properly.
		 * The following is how special values are represented in the returned value:
		 * <ul>
		 * <li>NaNs will have this format 0.0e[minExponent -
		 * -(BinaryFPPrecision.this.getSignificandBitLength())]</li>
		 * <li>Infinity will have this format 0.0e+[maxExponent]</li>
		 * <li>-Infinity will have this format 0.0e-[maxExponent]</li>
		 * <li>-0.0 will have this format -0.0</li>
		 * </ul>
		 * These format are the strings seen when any of the toString method are called.
		 * Also note that -0.0, -Infinity, Infinity and NaN have weird results for their
		 * arithmetic operations
		 * 
		 * @return a {@code BigDecimal} which is the decimal equivalent of {@code this}
		 */
		public BigDecimal toBigDecimal() {
			if (isNegativeZero() || !isFinite())
				return new SBigD();
			// decompose
			if (val.signum() < 0)
				throw new ArithmeticException("Layout was signed");
			// sign, exponent, implicit, significand
			BigInteger[] b = new BigInteger[4];
			b[0] = signum() < 0 ? i(1) : i(0);
			b[1] = getExponentMask(true).and(val).shiftRight(significandLength - 1);
			b[2] = i(1);
			b[3] = getSignificandMask(true).and(val);
			if (b[1].bitCount() == getExponentBitLength()) {// Infinity or NaN
				if (b[3].signum() == 0) {// Infinity
					b[1] = i(getMaxExponent());
					b[3] = null;
				} else {// NaN
					b[0] = i(0);
					b[1] = i(getMaxExponent());
					b[2] = i(0);
					b[3] = null;
				}
			} else if (b[1].bitCount() == 0) {// Subnormal or zero
				if (b[3].signum() != 0) {// Subnormal
					b[1] = i(getMinExponent());
					b[2] = i(0);
				}
			} else {// Normal range
				b[1] = b[1].subtract(i(getBias()));
			}

			// toBigDecimal
			if (b[3] == null) {// NaN or Infinity
				return d("0.0e" + calculateSignificandDigits(2, getMaxExponent(), 10));
			}
			StringBuilder sb = new StringBuilder(getBitLength());
			sb.append(b[2].toString(2)).append('.')
					.append(getTrailingZeros(significandLength - 1).or(b[3]).toString(2).substring(1)).append('p')
					.append(b[1].toString(2));
			return d(String.format("%1$s%2$s", b[0].signum() == 0 ? "" : "-",
					FloatAid.toString(sb.toString(), 2, 10, getMC(10))));
		}

		/*
		 * Date: 14 Nov 2022-----------------------------------------------------------
		 * Time created: 16:46:44--------------------------------------------
		 */
		/**
		 * Converts {@code this} to it's IEEE754 bit layout equivalent.
		 * 
		 * @return a {@code BigInteger} which is the IEEE754 bit layout of {@code this}.
		 */
		public BigInteger toBigInteger() {
			return val;
		}

		// note that this is not the same value as getExponent(), This is mainly meant
		// for decimal exponent. if this is subnormal then getExponent() and this value
		// will be wildly different. e.g getMinValue().getExponent() and
		// getMinValue().getExponent(2) will return a difference of the significand bit
		// length
		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 14:06:30--------------------------------------------
		 */
		/**
		 * Returns the non-binary exponent. If the argument is 2 (binary), then a
		 * unnormalised exponent is returned. This method is for getting the actual
		 * binary exponent and not for the unbiased normalised exponent. For the
		 * unbiased normalised exponent use {@link #getExponent()}.
		 * 
		 * @param radix the radix in which the exponent will be converted to.
		 * @return the non-binary exponent.
		 */
		public int getExponent(int radix) {
			return calculateSignificandDigits(2, getExponent(), radix)
					- (!isSubnormal() ? 0 : calculateSignificandDigits(2, significandLength, radix));
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 14:06:34--------------------------------------------
		 */
		/**
		 * Returns the raw unconverted (biased) exponent field as it is stored in
		 * {@link #toBigInteger()}.
		 * 
		 * @return the unbiased exponent according to IEEE754
		 */
		public BigInteger getBiasedExponent() {
			return BinaryFPPrecision.this.getExponentMask(true).and(val).shiftRight(getSignificandBitLength() - 1);
		}

		/*
		 * Date: 24 Nov 2022-----------------------------------------------------------
		 * Time created: 02:43:01--------------------------------------------
		 */
		/**
		 * Returns the given exception
		 * 
		 * @return one of the following values
		 *         <ul>
		 *         <li>{@link BinaryFPPrecision#FP_EXCEPTION_DIVSION_BY_ZERO}</li>
		 *         <li>{@link BinaryFPPrecision#FP_EXCEPTION_INEXACT_CALC}</li>
		 *         <li>{@link BinaryFPPrecision#FP_EXCEPTION_INVALID_OP}</li>
		 *         <li>{@link BinaryFPPrecision#FP_EXCEPTION_OVERFLOW}</li>
		 *         <li>{@link BinaryFPPrecision#FP_EXCEPTION_UNDERFLOW}</li>
		 *         </ul>
		 */
		public int getException() {
			return exception;
		}

		/*
		 * Date: 24 Nov 2022-----------------------------------------------------------
		 * Time created: 02:46:07--------------------------------------------
		 */
		/**
		 * Gets the carry value. Usually indicates a exception floating point occurred.
		 * 
		 * @return the carry value.
		 */
		public BigInteger getCarry() {
			return carry;
		}

		////////////////////////////////////////////////////////////
		//////////////////////// Checks ///////////////////////////
		//////////////////////////////////////////////////////////

		/*
		 * Date: 14 Nov 2022-----------------------------------------------------------
		 * Time created: 16:49:57--------------------------------------------
		 */
		/**
		 * A check for -0.0 i.e returns {@code true} if this == -0.0 or else returns
		 * {@code false}.
		 * 
		 * @return {@code true} if this == -0.0 or else returns {@code false}
		 */
		public boolean isNegativeZero() {
			return signum() < 0 && val.bitCount() == 1;
		}

		/*
		 * Date: 14 Nov 2022-----------------------------------------------------------
		 * Time created: 16:51:28--------------------------------------------
		 */
		/**
		 * A check for whether {@code this} lies outside of the normal range. Note that
		 * 0 will return {@code false} here.
		 * 
		 * @return {@code true} if
		 *         <code>abs(this) &lt; {@link BinaryFPPrecision#getMinNormal()}</code> else
		 *         returns {@code false}
		 */
		public boolean isSubnormal() {
			return getBiasedExponent().signum() == 0 && getSignificand().signum() > 0;
		}

		/*
		 * Date: 14 Nov 2022-----------------------------------------------------------
		 * Time created: 16:55:02--------------------------------------------
		 */
		/**
		 * Tests whether {@code this} is less than the smallest non-zero, positive value
		 * in this {@code BinaryFPPrecision}.
		 * 
		 * @return {@code true} if {@code this} magnitude is less than the smallest<br>
		 *         possible value, and {@code false} if otherwise.
		 */
		public boolean isNaN() {
			return getBiasedExponent().bitCount() == getExponentBitLength() && getSignificand().signum() != 0;
		}

		/*
		 * Date: 14 Nov 2022-----------------------------------------------------------
		 * Time created: 16:58:22--------------------------------------------
		 */
		/**
		 * Checks whether {@code this} is compatible with infinity value in relation to
		 * the {@code BinaryFPPrecision} and also in compliance with IEEE 754
		 * stipulations. This method will return {@code false} for {@code NaN} and
		 * {@code true} for {@code Infinity}. This method is not just for convenience,
		 * please use this method to check for the inifiniteness of a value.
		 * 
		 * @return {@code true} if {@code this} is infinite as defined by IEEE 754
		 *         conventions for this {@code BinaryFPPrecision} otherwise returns
		 *         {@code false}
		 */
		public boolean isInfinite() {
			return getBiasedExponent().bitCount() == getExponentBitLength() && getSignificand().bitCount() == 0;
		}

		/*
		 * Date: 14 Nov 2022-----------------------------------------------------------
		 * Time created: 16:58:26--------------------------------------------
		 */
		/**
		 * Returns {@code true} if {@code this} magnitude is less than or equal to the
		 * max possible value, and {@code false} otherwise. This is only for testing for
		 * infinity. NaNs may return {@code false} and subnormals sill return
		 * {@code true}
		 * 
		 * @return {@code true} if {@code this} magnitude is less than or equal to the
		 *         max possible value, and {@code false} otherwise.
		 */
		public boolean isFinite() {
			return (!isInfinite()) && (!isNaN());
		}

		////////////////////////////////////////////////////////////
		////////////////// Rounding operations ////////////////////
		//////////////////////////////////////////////////////////

		/*
		 * Date: 14 Nov 2022-----------------------------------------------------------
		 * Time created: 17:08:40--------------------------------------------
		 */
		/**
		 * Returns the smallest (closest to negative infinity) {@code BinaryFP} value
		 * that is greater than or equal to the argument and is equal to a mathematical
		 * integer. Special cases:
		 * <ul>
		 * <li>If {@code this} is already equal to a mathematical integer, then the
		 * result is the same as{@code this}.
		 * <li>If {@code this} is NaN or an infinity or positive zero or negative zero,
		 * then the result is the same as {@code this}.
		 * <li>If {@code this} is less than zero but greater than -1.0, then the result
		 * is negative zero.
		 * </ul>
		 * Note that the value of {@code ceil(x)} is exactly the value of
		 * {@code floor(-x)}.
		 *
		 *
		 * @return the smallest (closest to negative infinity) floating-point value that
		 *         is greater than or equal to {@code this} and is equal to a
		 *         mathematical integer.
		 */
		public BinaryFP ceil() {
			BinaryFP ng = createFP("1", 10);
			return floorOrCeil(getNegativeZero(), ng, ng);
		}

		/*
		 * Date: 14 Nov 2022-----------------------------------------------------------
		 * Time created: 17:16:53--------------------------------------------
		 */
		/**
		 * Returns the largest (closest to positive infinity) {@code BinaryFP} value
		 * that is less than or equal to {@code this} and is equal to a mathematical
		 * integer. Special cases:
		 * <ul>
		 * <li>If {@code this} value is already equal to a mathematical integer, then
		 * the result is the same as {@code this}.
		 * <li>If {@code this} is NaN or an infinity or positive zero or negative zero,
		 * then the result is the same as {@code this}.
		 * </ul>
		 *
		 * @return the largest (closest to positive infinity) floating-point value that
		 *         less than or equal to {@code this} and is equal to a mathematical
		 *         integer.
		 */
		public BinaryFP floor() {
			if (signum() < 0)
				return abs().ceil().negate();
			BinaryFP ng = createFP("-2");
			return floorOrCeil(ng, new BinaryFP(i(0), 0, 0), ng);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 13:43:06--------------------------------------------
		 */
		/**
		 * Returns the {@code BinaryFP} value that is closest in value to {@code this}
		 * and is equal to a mathematical integer. If two {@code double} values that are
		 * mathematical integers are equally close, the result is the integer value that
		 * is even. Special cases:
		 * <ul>
		 * <li>If {@code this} value is already equal to a mathematical integer, then
		 * the result is the same as {@code this}.
		 * <li>If {@code this} is NaN or an infinity or positive zero or negative zero,
		 * then the result is the same as {@code this}.
		 * </ul>
		 *
		 * @return the closest floating-point value to {@code this} that is equal to a
		 *         mathematical integer.
		 */
		public BinaryFP rint() {
			BinaryFP _2_pow_sig = new BinaryFP(getTrailingZeros(significandLength - 1), 0, 0);
			BinaryFP sign = createFP("1").copySign(this);
			BinaryFP $this = abs();

			if ($this.compareTo(_2_pow_sig) < 0)
				$this = _2_pow_sig.add($this).subtract($this);
			return sign.multiply($this);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 14:02:20--------------------------------------------
		 */
		/**
		 * Returns the closest integer to the argument, with ties rounding to positive
		 * infinity.
		 * 
		 * @return the value of {@code this} rounded to the nearest integer value.
		 */
		public BinaryFP round() {
			BigInteger bits = toBigInteger();
			BigInteger biasedExp = getBiasedExponent();
			BigInteger shift = i(((significandLength - 2) + getBias()) - biasedExp.longValue());
			if (shift.and(i(-getBitLength())).signum() == 0) {
				BigInteger r = bits.and(getSignificandMask(true)).or(getSignificandMask(true).add(i(1)));
				if (bits.compareTo(i(0)) < 0)
					r = r.negate();
				return new BinaryFP(d(r.shiftRight(shift.intValue() + 1).shiftRight(1)), 0, 0);
			}
			return this;
		}

		/*
		 * Date: 17 Nov 2022-----------------------------------------------------------
		 * Time created: 15:43:44--------------------------------------------
		 */
		/**
		 * Returns {@code this} truncated to an integer value.
		 * 
		 * @return the integer part of {@code this} as a {@code BigInteger}
		 */
		public BigInteger trunc() {
			if (isNaN() || isInfinite() || isNegativeZero() || signum() == 0)
				return i(0);
			return toBigDecimal().toBigInteger();
		}

		////////////////////////////////////////////////////////////
		//////////////////// ULP operations ///////////////////////
		//////////////////////////////////////////////////////////

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 14:18:51--------------------------------------------
		 */
		/**
		 * Returns the floating-point number adjacent to the first argument in the
		 * direction of the second argument. If both arguments compare as equal the
		 * second argument is returned.
		 *
		 * <p>
		 * Special cases:
		 * <ul>
		 * <li>If {@code this} and the argument is a NaN, then NaN is returned.
		 *
		 * <li>If {@code this} and the argument is signed zeros, {@code direction} is
		 * returned unchanged.
		 *
		 * <li>If {@code this} is &plusmn;{@link BinaryFPPrecision#getMinValue()} and
		 * {@code direction} has a value such that the result should have a smaller
		 * magnitude, then a zero with the same sign as {@code this} is returned.
		 *
		 * <li>If {@code this} is infinite and {@code direction} has a value such that
		 * the result should have a smaller magnitude,
		 * {@link BinaryFPPrecision#getMaxValue()} with the same sign as {@code this} is
		 * returned.
		 *
		 * <li>If {@code this} is equal to &plusmn;
		 * {@link BinaryFPPrecision#getMaxValue()} and {@code direction} has a value
		 * such that the result should have a larger magnitude, an infinity with same
		 * sign as {@code this} is returned.
		 * </ul>
		 * <p>
		 * Note that {@code this} is the starting point.
		 *
		 * @param direction value indicating which of {@code this}'s neighbors or
		 *                  {@code this} should be returned
		 * @return The floating-point number adjacent to {@code this} in the direction
		 *         of {@code direction}.
		 */
		public BinaryFP nextAfter(BinaryFP direction) {
			final int c = getPrecision().compareTo(direction.getPrecision());
			if (c == 0) {
				if (compareTo(direction) > 0) {
					if (signum() != 0)
						return new BinaryFP(val.add(val.compareTo(i(0)) > 0 ? i(-1) : i(1)).abs(), 0, 0);
					return getMinValue().negate();
				} else if (compareTo(direction) < 0) {
					BigInteger transducer = add(new BinaryFP(i(0), 0, 0)).val;
					return new BinaryFP(transducer.add(transducer.compareTo(i(0)) >= 0 ? i(1) : i(-1)).abs(), 0, 0);
				} else if (compareTo(direction) == 0)
					return direction;
				return add(direction);
			} else if (c > 0) {
				return nextAfter(direction.cast(getPrecision()));
			}
			return cast(direction.getPrecision()).nextAfter(direction);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 14:23:57--------------------------------------------
		 */
		/**
		 * Returns the floating-point value adjacent to {@code this} in the direction of
		 * positive infinity. This method is semantically equivalent to
		 * {@code nextAfter(getPositiveInfinity())}; however, a {@code nextUp}
		 * implementation may run faster than its equivalent {@code nextAfter} call.
		 *
		 * <p>
		 * Special Cases:
		 * <ul>
		 * <li>If {@code this} is NaN, the result is NaN.
		 *
		 * <li>If {@code this} is positive infinity, the result is positive infinity.
		 *
		 * <li>If {@code this} is zero, the result is
		 * {@link BinaryFPPrecision#getMinValue()}
		 *
		 * </ul>
		 *
		 * @return The adjacent floating-point value closer to positive infinity.
		 */
		public BinaryFP nextUp() {
			if (compareTo(getPositiveInfinity()) < 0) {
				BigInteger transducer = add(new BinaryFP(i(0), 0, 0)).val;
				return new BinaryFP(transducer.add(transducer.compareTo(i(0)) >= 0 ? i(1) : i(-1)).abs(), 0, 0);
			}
			return this;
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 14:26:28--------------------------------------------
		 */
		/**
		 * Returns the floating-point value adjacent to {@code this} in the direction of
		 * negative infinity. This method is semantically equivalent to
		 * {@code nextAfter(getNegativeInfinity())}; however, a {@code nextDown}
		 * implementation may run faster than its equivalent {@code nextAfter} call.
		 *
		 * <p>
		 * Special Cases:
		 * <ul>
		 * <li>If {@code this} is NaN, the result is NaN.
		 *
		 * <li>If {@code this} is negative infinity, the result is negative infinity.
		 *
		 * <li>If {@code this} is zero, the result is {@code getMinValue().negate()}
		 *
		 * </ul>
		 *
		 * @return The adjacent floating-point value closer to negative infinity.
		 */
		public BinaryFP nextDown() {
			if (isNaN() || compareTo(getNegativeInfinity()) == 0)
				return this;
			if (signum() == 0)
				return getMinValue().negate();
			return new BinaryFP(val, 0, 0).add(signum() < 0 ? new BinaryFP(i(-1), 0, 0) : new BinaryFP(i(1), 0, 0))
					.abs();
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 14:28:08--------------------------------------------
		 */
		/**
		 * Returns the size of an ulp of the argument. An ulp, unit in the last place,
		 * of a {@code BinaryFP} value is the positive distance between this
		 * floating-point value and the {@code
		 * BinaryFP} value next larger in magnitude. Note that for non-NaN <i>x</i>,
		 * <code>ulp(-<i>x</i>) == ulp(<i>x</i>)</code>.
		 *
		 * <p>
		 * Special Cases:
		 * <ul>
		 * <li>If {@code this} is NaN, then the result is NaN.
		 * <li>If {@code this} is positive or negative infinity, then the result is
		 * positive infinity.
		 * <li>If {@code this} is positive or negative zero, then the result is
		 * {@code getMinValue()}.
		 * <li>If {@code this} is &plusmn;{@code getMaxValue()}, then the result is
		 * equal to 2<sup>971</sup>.
		 * </ul>
		 *
		 * @return the size of an ulp of {@code this}
		 */
		public BinaryFP ulp() {
			int exp = getExponent();

			if (exp == (getMaxExponent() + 1))
				return abs();
			else if (exp == (getMinExponent() - 1))
				return getMinValue();

			exp = exp - (significandLength - 1);
			if (exp >= getMinExponent())
				return powerOf2(exp);

			return new BinaryFP(i(1).shiftLeft(exp - (getMinExponent() - (significandLength - 1))), 0, 0);
		}

		/*
		 * Date: 17 Nov 2022-----------------------------------------------------------
		 * Time created: 00:38:17--------------------------------------------
		 */
		/**
		 * Computes the remainder operation on {@code this} and arguments as prescribed
		 * by the IEEE 754 standard. The remainder value is mathematically equal to
		 * <code>this&nbsp;-&nbsp;f</code>&nbsp;&times;&nbsp;<i>n</i>, where <i>n</i> is
		 * the mathematical integer closest to the exact mathematical value of the
		 * quotient {@code f1/f2}, and if two mathematical integers are equally close to
		 * {@code this/f}, then <i>n</i> is the integer that is even. If the remainder
		 * is zero, its sign is the same as the sign of {@code this}.
		 * <p>
		 * Special cases:
		 * <ul>
		 * <li>If {@code this} or argument is NaN, or {@code this} is infinite, or the
		 * argument is positive zero or negative zero, then the result is NaN.
		 * <li>If {@code this} is finite and the argument is infinite, then the result
		 * is the same as {@code this}.
		 * </ul>
		 * 
		 * @param f the divisor of {@code this}
		 * @return the remainder when {@code this} is divided by the argument
		 * @see <a href="https://sourceforge.net/p/orp/mailman/message/124925/">This
		 *      sourceforge post</a> for implementation details
		 */
		public BinaryFP ieeeRemainder(BinaryFP f) {
			final int c = getPrecision().compareTo(f.getPrecision());
			if (c == 0) {
//				BigInteger sign = getAllOnes(getBitLength() - 1).or(val.and(getSignMask(true)));

				if (isNaN() || f.isNaN())
					return getNaN();
				else if (f.signum() == 0 || isInfinite())
					return getNaN();
				else if (f.isInfinite())
					return this;

				// method proper
//				BinaryFP q = divide(f);
//				BinaryFP q1 = q.floor();// createFP(d(q.trunc()));
//				BinaryFP q2 = q.ceil();
//				BinaryFP dq1 = q.subtract(q1);
//				BinaryFP dq2 = q2.subtract(q);

//				if (dq1.compareTo(dq2) > 0)
//					q = q2;
//				else if (dq1.compareTo(dq2) < 0)
//					q = q1;
//				else if (q1.trunc().and(i(1)).signum() == 0)
//					q = q1;
//				else
//					q = q2;

//				BinaryFP r = subtract(q).multiply(f);

//				if (r.signum() == 0) {
//					BigInteger rl = r.val;
//					rl = rl.and(sign);
//					r = fromBitLayout(rl);
//				}

//				return r;
				// This value is undefined if the total number of bit in this precision is not
				// even
//				int halfBits = getBitLength() / 2;
				/*
				 * [0] - high bits of this, [1] - low bits of this, [2] - sign of this
				 */
//				BigInteger[] splitThis = new BigInteger[3];
				/*
				 * [0] - high bits of argument, [1] - low bits of argument
				 */
//				BigInteger[] splitArg = new BigInteger[2];
//				BinaryFP p_half;
//				final BinaryFP zero = new BinaryFP(i(0));
//				BinaryFP x = this;
//				BinaryFP p = f;

//				getHighAndLowBits(splitThis, 0, 1);
//				p.getHighAndLowBits(splitArg, 0, 1);
//				splitThis[2] = splitThis[0].and(getTrailingZeros(halfBits - 1));
//				splitArg[0] = splitArg[0].and(getAllOnes(halfBits - 1));
//				splitThis[0] = splitThis[0].and(getAllOnes(halfBits - 1));

				// deal with exception values
//				if (splitArg[0].or(splitArg[1]).signum() == 0)
//					return multiply(p).divide(multiply(p));// p = 0
//				final BigInteger m = shiftRight(getExponentMask(true), halfBits, halfBits, true);
//				if (splitThis[0].compareTo(m) >= 0// this is not finite
//						|| (splitArg[0].compareTo(m) >= 0// p is NaN
//								&& (splitArg[0].subtract(m).or(splitArg[1]).signum() != 0)))
//					return multiply(p).divide(multiply(p));

//				if (splitArg[0].compareTo(getAllOnes(halfBits - 1)) <= 0)
//					x = fmod(p.add(p));
//				if (splitThis[0].subtract(splitArg[0]).or(splitThis[1].subtract(splitArg[1])).signum() == 0)
//					return zero.multiply(x);
//				x = x.abs();
//				p = p.abs();
//				if (splitArg[0].compareTo(getTrailingZeros(halfBits - exponentLength)) < 0) {
//					if (x.add(x).compareTo(p) > 0) {
//						x = x.subtract(p);
//						if (x.add(x).compareTo(p) >= 0)
//							x = x.subtract(p);
//					}
//					p_half = createFP("0.5").multiply(p);
//					if (x.compareTo(p_half) > 0) {
//						x = x.subtract(p);
//						if (x.compareTo(p_half) >= 0)
//							x = x.subtract(p);
//					}
//				}
//				x.getHighBits(splitThis, 0);
//				x.setHighBits(splitThis, splitThis[0].xor(splitThis[2]), 0);
//				return fromBitLayout(splitThis[0]);

				return fmod(f);
			} else if (c > 0) {
				return ieeeRemainder(f.cast(getPrecision()));
			}
			return cast(f.getPrecision()).ieeeRemainder(f);
		}

		/*
		 * Date: 20 Nov 2022-----------------------------------------------------------
		 * Time created: 15:35:58--------------------------------------------
		 */
		/**
		 * Calculates {@code this % f} and returns the result.
		 * 
		 * @param f the divisor
		 * @return {@code this % f}
		 */
		public BinaryFP fmod(BinaryFP f) {
			final int c = getPrecision().compareTo(f.getPrecision());
			if (c == 0) {
				if (isNaN() || f.isNaN())
					return getNaN();
				else if (f.signum() == 0 || isInfinite())
					return getNaN();
				else if (f.isInfinite())
					return this;
//				BinaryFP x = this;
//				BinaryFP y = f;
//				// This value is undefined if the total number of bit in this precision is not
//				// even
//				int halfBits = getBitLength() / 2;
//				/*
//				 * [0] - high bits of x, [1] - low bits of x
//				 */
//				BigInteger[] xHiLo = new BigInteger[2];
//				/*
//				 * [0] - high bits of y, [1] - low bits of y
//				 */
//				BigInteger[] yHiLo = new BigInteger[2];
//				final BigInteger signOfX;
//				BigInteger ix, iy, i, n, hz, lz;
//
//				final BinaryFP one = createFP("1");
//				final BinaryFP[] zero = { fromBitLayout(i(0)), getNegativeZero() };
//
//				x.getHighAndLowBits(xHiLo, 0, 1);
//				y.getHighAndLowBits(yHiLo, 0, 1);
//				signOfX = xHiLo[0].and(getTrailingZeros(halfBits - 1));
//				xHiLo[0] = xHiLo[0].xor(signOfX);// |x|
//				yHiLo[0] = yHiLo[0].and(getAllOnes(halfBits - 1));// |y|
//
//				// deal with exception values
//				// if y=0 or x is not finite
//				if ((yHiLo[0].or(yHiLo[1]).signum() == 0
//						|| getComparator(halfBits).compare(xHiLo[0], getAllOnes(exponentLength)) >= 0) ||
//				// or y is NaN
//						getComparator(halfBits).compare(
//								yHiLo[0].or(yHiLo[1]
//										.or(shiftRight(TC.negate(yHiLo[1], halfBits), halfBits - 1, halfBits, true))),
//								getAllOnes(exponentLength)) > 0)
//					return x.multiply(y).divide(x.multiply(y));
//				if (compare(xHiLo[0], yHiLo[0], halfBits) <= 0) {
//					// |x|<|y| return x
//					if (compare(xHiLo[0], yHiLo[0], halfBits) < 0 || compare(xHiLo[1], yHiLo[1], halfBits) < 0)
//						return x;
//					if (compare(xHiLo[1], yHiLo[1], halfBits) == 0)
//						return zero[shiftRight(signOfX, halfBits - 1, halfBits, true).intValue()];
//				}
//
//				// determine ix = ilogb(x)
//				if (compare(xHiLo[0], getTrailingZeros(halfBits - exponentLength - 1), halfBits) < 0) {// subnormal x
//					if (xHiLo[0].signum() == 0)
//						for (ix = i(-(getBias() + (halfBits - exponentLength - 1))), i = xHiLo[1]; i
//								.signum() > 0; i = shiftLeft(i, 1, halfBits))
//							ix = TC.subtract(ix, i(1), halfBits);
//					else
//						for (ix = i(getMinExponent()), i = shiftLeft(xHiLo[0], exponentLength, halfBits); i
//								.signum() > 0; i = shiftLeft(i, 1, halfBits))
//							ix = TC.subtract(ix, i(1), halfBits);
//				} else
//					ix = TC.subtract(shiftRight(xHiLo[0], halfBits - exponentLength - 1, halfBits, true), i(getBias()),
//							halfBits);
//
//				// determine iy = ilogb(y)
//				if (compare(yHiLo[0], getTrailingZeros(halfBits - exponentLength - 1), halfBits) < 0) {
//					if (yHiLo[0].signum() == 0) {
//						for (iy = i(-(getBias() + (halfBits - exponentLength - 1))), i = yHiLo[1]; i
//								.signum() > 0; i = shiftLeft(i, 1, halfBits))
//							iy = TC.subtract(iy, i(1), halfBits);
//					} else
//						for (iy = i(getMinExponent()), i = shiftLeft(yHiLo[0], exponentLength, halfBits); i
//								.signum() > 0; i = shiftLeft(i, 1, halfBits))
//							iy = iy.subtract(i(1));
//				} else
//					iy = TC.subtract(shiftRight(yHiLo[0], halfBits - exponentLength - 1, halfBits, true), i(getBias()),
//							halfBits);
//
//				/* set up {hx,lx}, {hy,ly} and align y to x */
//				if (compare(ix, fromDecimal(i(getMinExponent()), halfBits), halfBits) >= 0)
//					xHiLo[0] = getTrailingZeros(halfBits - exponentLength - 1)
//							.or(getAllOnes(halfBits - exponentLength - 1).and(xHiLo[0]));
//				else {
//					n = TC.subtract(i(getMinExponent()), iy, halfBits);
//					if (compare(n, i(halfBits - 1), halfBits) >= 0) {
//						yHiLo[0] = shiftLeft(yHiLo[0], n.intValue(), halfBits)
//								.or(shiftRight(yHiLo[1], halfBits - n.intValue(), halfBits, true));
//						yHiLo[1] = shiftLeft(yHiLo[1], n.intValue(), halfBits);
//					} else {
//						yHiLo[0] = shiftLeft(yHiLo[1], n.intValue() - halfBits, halfBits);
//						yHiLo[1] = i(0);
//					}
//				}
//
//				/* fix point fmod */
//				n = TC.subtract(ix, iy, halfBits);
//				while ((n = n.subtract(i(1))).signum() != 0) {
//					hz = TC.subtract(xHiLo[0], yHiLo[0], halfBits);
//					lz = TC.subtract(xHiLo[1], yHiLo[1], halfBits);
//					if (compare(xHiLo[1], yHiLo[1], halfBits) < 0) {
//						hz = TC.subtract(hz, i(1), halfBits);
//					}
//					if (compare(hz, i(0), halfBits) < 0) {
//						xHiLo[0] = TC.add(TC.add(xHiLo[0], xHiLo[0], halfBits),
//								shiftRight(xHiLo[1], halfBits - 1, halfBits, true), halfBits);
//						xHiLo[1] = TC.add(xHiLo[1], xHiLo[1], halfBits);
//					} else {
//						if (hz.or(lz).signum() == 0)
//							return zero[shiftRight(signOfX, halfBits - 1, halfBits, true).intValue()];
//						xHiLo[0] = TC.add(TC.add(hz, hz, halfBits), shiftRight(lz, halfBits - 1, halfBits, true),
//								halfBits);
//						xHiLo[1] = TC.add(lz, lz, halfBits);
//					}
//				}
//				hz = TC.subtract(xHiLo[0], yHiLo[0], halfBits);
//				lz = TC.subtract(xHiLo[1], yHiLo[1], halfBits);
//				if (compare(xHiLo[1], yHiLo[1], halfBits) < 0) {
//					hz = TC.subtract(hz, i(1), halfBits);
//				}
//				if (compare(hz, i(0), halfBits) >= 0) {
//					xHiLo[0] = hz;
//					xHiLo[1] = lz;
//				}
//
//				if (xHiLo[0].or(xHiLo[1]).signum() == 0)
//					return zero[shiftRight(signOfX, halfBits - 1, halfBits, true).intValue()];
//				while (compare(xHiLo[0], getTrailingZeros(halfBits - exponentLength - 1), halfBits) < 0) {
//					xHiLo[0] = TC.add(TC.add(hz, hz, halfBits), shiftRight(lz, halfBits - 1, halfBits, true), halfBits);
//					xHiLo[1] = TC.add(xHiLo[1], xHiLo[1], halfBits);
//					iy = TC.subtract(iy, i(1), halfBits);
//				}
//				if (compare(iy, fromDecimal(i(getMinExponent()), halfBits), halfBits) >= 0) {
//					xHiLo[0] = TC.subtract(xHiLo[0], getTrailingZeros(halfBits - exponentLength - 1), halfBits)
//							.or(shiftLeft(TC.add(iy, i(getBias()), halfBits), halfBits - exponentLength - 1, halfBits));
//					x = fromBitLayout(xHiLo[0].or(signOfX).shiftLeft(halfBits).or(xHiLo[1]));
//				} else {
//					n = TC.subtract(fromDecimal(i(getMinExponent()), halfBits), iy, halfBits);
//					if (n.intValue() <= halfBits - exponentLength - 1) {
//						xHiLo[1] = shiftRight(xHiLo[1], n.intValue(), halfBits, true)
//								.or(shiftLeft(xHiLo[0], halfBits - n.intValue(), halfBits));
//						xHiLo[0] = shiftRight(xHiLo[0], n.intValue(), halfBits, true);
//					} else if (n.intValue() <= halfBits - 1) {
//						xHiLo[1] = shiftLeft(xHiLo[0], halfBits - n.intValue(), halfBits)
//								.or(shiftRight(xHiLo[1], n.intValue(), halfBits, true));
//						xHiLo[0] = signOfX;
//					} else {
//						xHiLo[1] = shiftRight(xHiLo[0], n.intValue() - halfBits, halfBits, true);
//						xHiLo[0] = signOfX;
//					}
//					x = fromBitLayout(xHiLo[0].or(signOfX).shiftLeft(halfBits).or(xHiLo[1]));
//					x = x.multiply(one);
//				}
//
//				return x;
				return createFP(toBigDecimal().remainder(f.toBigDecimal()));
			} else if (c > 0) {
				return fmod(f.cast(getPrecision()));
			}
			return cast(f.getPrecision()).fmod(f);
		}

		////////////////////////////////////////////////////////////
		//////////////////// Sign operations //////////////////////
		//////////////////////////////////////////////////////////

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 13:44:08--------------------------------------------
		 */
		/**
		 * Returns {@code this} with the sign of the {@code BinaryFP} argument.
		 *
		 * @param sign the parameter providing the sign of the result
		 * @return a value with the magnitude of {@code this} and the sign of
		 *         {@code sign}.
		 */
		public BinaryFP copySign(BinaryFP sign) {
			final int c = getPrecision().compareTo(sign.getPrecision());
			if (c == 0) {
				return new BinaryFP((sign.isNaN() ? createFP("1", 10) : sign).val.and(getSignMask(true))
						.or(val.and(getExponentMask(true).or(getSignificandMask(true)))), 0, 0);
			} else if (c > 0) {
				return copySign(sign.cast(getPrecision()));
			}
			return cast(sign.getPrecision()).copySign(sign);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 14:30:49--------------------------------------------
		 */
		/**
		 * Returns 1, 0 or -1 as {@code this} is non-negative, non-signed zero or
		 * negative. If this is -0.0 then -1 will be returned else if this is NaN then 1
		 * will be returned or else the sign of {@code this} will be returned.
		 * 
		 * @return the mathematical sign of {@code this}
		 */
		public int signum() {
			int s = val.shiftRight(BinaryFPPrecision.this.getBitLength() - 1).bitCount();
			return s > 0 ? -1 : val.signum() == 0 ? 0 : 1;
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 14:33:36--------------------------------------------
		 */
		/**
		 * Returns the absolute value of {@code this}.
		 * 
		 * @return the absolute value of {@code this}
		 */
		public BinaryFP abs() {
			if (isNaN())
				return getNaN();
			else if (isInfinite())
				return signum() < 0 ? getNegativeInfinity() : getPositiveInfinity();
			else if (signum() >= 0)
				return this;
			return new BinaryFP(val.xor(getSignMask(true)), 0, 0);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 14:34:13--------------------------------------------
		 */
		/**
		 * Returns -{@code this}.
		 * 
		 * @return the negation of {@code this}.
		 */
		public BinaryFP negate() {
			if (isNaN())
				return getNaN();
			return new BinaryFP(val.xor(getSignMask(true)), 0, 0);
		}

		////////////////////////////////////////////////////////////
		/////////////////// Basic operations //////////////////////
		//////////////////////////////////////////////////////////

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 14:37:26--------------------------------------------
		 */
		/**
		 * Returns the argument added to {@code this}.
		 * 
		 * @param f the value to be added to {@code this}
		 * @return <code>this + f</code>
		 */
		public BinaryFP add(BinaryFP f) {
			int c = getPrecision().compareTo(f.getPrecision());
			if (c == 0) {
				if (isNaN()) {
					return getNaN();
				} else if (f.isNaN()) {
					return getNaN();
				} else if (isInfinite()) {
					if (signum() > 0) {
						if (f.signum() < 0 && f.isInfinite())
							return getNaN();
						return getPositiveInfinity();
					} else if (f.signum() > 0 && f.isInfinite())
						return getNaN();
					return getNegativeInfinity();
				} else if (f.isInfinite()) {
					if (f.signum() > 0)
						return getPositiveInfinity();
					return getNegativeInfinity();
				} else if (isNegativeZero() || signum() == 0) {
					return f;
				} else if (f.isNegativeZero() || f.signum() == 0) {
					return this;
				} else if (abs().compareTo(f.abs()) < 0)
					return f.add(this);
				// normal number
				if (!(this instanceof Serializable))
					return doNormalAddition(f);
				return new BinaryFP(toBigDecimal().add(f.toBigDecimal()), 0, 0);

			} else if (c > 0)
				return add(f.cast(getPrecision()));
			return cast(f.getPrecision()).add(f);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 14:39:18--------------------------------------------
		 */
		/**
		 * Returns {@code this} after subtracting the argument.
		 * 
		 * @param f the value to be subtracted from {@code this}
		 * @return <code>this - f</code>
		 */
		public BinaryFP subtract(BinaryFP f) {
			return add(f.negate());
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 14:39:48--------------------------------------------
		 */
		/**
		 * Returns {@code this} after dividing by the argument.
		 * 
		 * @param f the value that will divide {@code this}
		 * @return <code>this / f</code>
		 */
		public BinaryFP divide(BinaryFP f) {
			int c = getPrecision().compareTo(f.getPrecision());
			if (c == 0) {
				if (isNaN()) {
					return getNaN();
				} else if (f.isNaN()) {
					return getNaN();
				} else if (f.isNegativeZero() || f.signum() == 0) {
					return getNaN();
				} else if (isInfinite()) {
					if (f.isInfinite())
						return getNaN();
					else if (signum() > 0) {
						if (f.signum() < 0)
							return getNegativeInfinity();
						return getPositiveInfinity();
					} else if (f.signum() < 0)
						return getPositiveInfinity();
					return getNegativeInfinity();
				} else if (f.isInfinite()) {
					if (f.signum() > 0) {
						if (signum() < 0)
							return getNegativeInfinity();
						return getPositiveInfinity();
					} else if (signum() < 0)
						return getPositiveInfinity();
					return getNegativeInfinity();
				} else if (isNegativeZero() || signum() == 0) {
					return this;
				} else if (f.compareTo(new BinaryFP(d(1), 0, 0)) == 0)
					return this;
				else if (f.compareTo(new BinaryFP(d(-1), 0, 0)) == 0)
					return negate();
				// normal number
				if (!(this instanceof Serializable)) {
					BigInteger val = doNormalDivision(f);
					return new BinaryFP(val, 0, 0);
				}
				return new BinaryFP(
						toBigDecimal().divide(f.toBigDecimal(), getMathContext(10, false, significandLength)), 0, 0);
			} else if (c > 0)
				return multiply(f.cast(getPrecision()));
			return cast(f.getPrecision()).multiply(f);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 14:42:27--------------------------------------------
		 */
		/**
		 * Returns {@code this} after multiplied by the argument.
		 * 
		 * @param f the value that will multiply {@code this}
		 * @return <code>this * f</code>
		 */
		public BinaryFP multiply(BinaryFP f) {
			int c = getPrecision().compareTo(f.getPrecision());
			if (c == 0) {
				if (isNaN()) {
					return getNaN();
				} else if (f.isNaN()) {
					return getNaN();
				} else if (isInfinite()) {
					if (signum() > 0) {
						if (f.signum() == 0 || f.isNegativeZero())
							return getNaN();
						else if (f.signum() < 0)
							return getNegativeInfinity();
						return getPositiveInfinity();
					} else if (f.signum() == 0 || f.isNegativeZero()) {
						return getNaN();
					} else if (f.signum() < 0)
						return getPositiveInfinity();
					return getNegativeInfinity();
				} else if (f.isInfinite()) {
					if (f.signum() > 0) {
						if (signum() == 0 || isNegativeZero())
							return getNaN();
						else if (signum() < 0)
							return getNegativeInfinity();
						return getPositiveInfinity();
					} else if (signum() == 0 || isNegativeZero()) {
						return getNaN();
					} else if (signum() < 0)
						return getPositiveInfinity();
					return getNegativeInfinity();
				} else if (isNegativeZero()) {
					return f.signum() < 0 ? getNegativeZero().abs() : getNegativeZero();
				} else if (f.isNegativeZero()) {
					return signum() < 0 ? getNegativeZero().abs() : getNegativeZero();
				} else if (signum() == 0 || f.signum() == 0)
					return getNegativeZero().abs();
				else if (compareTo(new BinaryFP(d(1), 0, 0)) == 0)
					return f;
				else if (f.compareTo(new BinaryFP(d(1), 0, 0)) == 0)
					return this;
				// normal number
				if (!(this instanceof Serializable))
					return doNormalMultiplication(f);
				return new BinaryFP(
						toBigDecimal().multiply(f.toBigDecimal(), getMathContext(10, false, significandLength)), 0, 0);
			} else if (c > 0)
				return multiply(f.cast(getPrecision()));
			return cast(f.getPrecision()).multiply(f);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 14:43:12--------------------------------------------
		 */
		/**
		 * Calculates and returns the bigger of {@code this} and the argument.
		 * 
		 * @param f a {@code BinaryFP} value
		 * @return the larger of {@code this} and the argument
		 */
		public BinaryFP max(BinaryFP f) {
			final int c = getPrecision().compareTo(f.getPrecision());
			if (c == 0) {
				return isNaN() ? f : f.isNaN() ? this : compareTo(f) > 0 ? this : f;
			} else if (c > 0) {
				return max(f.cast(getPrecision()));
			}
			return cast(f.getPrecision()).max(f);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 14:47:15--------------------------------------------
		 */
		/**
		 * Calculates and returns the smaller of {@code this} and the argument.
		 * 
		 * @param f a {@code BinaryFP} value
		 * @return the smaller of {@code this} and the argument
		 */
		public BinaryFP min(BinaryFP f) {
			final int c = getPrecision().compareTo(f.getPrecision());
			if (c == 0) {
				return isNaN() ? f : f.isNaN() ? this : compareTo(f) < 0 ? this : f;
			} else if (c > 0) {
				return min(f.cast(getPrecision()));
			}
			return cast(f.getPrecision()).min(f);
		}

		////////////////////////////////////////////////////////////
		//////////// Elementary function operations ///////////////
		//////////////////////////////////////////////////////////

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 23:03:35--------------------------------------------
		 */
		/**
		 * Calculates and returns the square root of {@code this}.
		 * 
		 * @return returns the square root of {@code this}
		 */
		public BinaryFP sqrt() {
			if (isNaN())
				return getNaN();
			else if (isInfinite()) {
				return signum() < 0 ? getNaN() : getPositiveInfinity();
			} else if (isNegativeZero())
				return getNegativeZero();
			else if (signum() < 0)
				return getNaN();
			return new BinaryFP(Arith.sqrt(toBigDecimal(), getMathContext(10, false, significandLength)), 0, 0);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 23:04:35--------------------------------------------
		 */
		/**
		 * Calculates and returns the cube root of {@code this}.
		 * 
		 * @return returns the cube root of {@code this}
		 */
		public BinaryFP cbrt() {
			if (isNaN() || isInfinite() || isNegativeZero() || signum() == 0)
				return this;
			return new BinaryFP(Arith.cbrt(toBigDecimal(), getMathContext(10, false, significandLength).getPrecision()),
					0, 0);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 23:04:54--------------------------------------------
		 */
		/**
		 * Computes {@code this} raised to the power of the argument and returns the
		 * result.
		 * 
		 * @param f the exponent
		 * @return {@code this} raised to the power of the argument
		 */
		public BinaryFP pow(BinaryFP f) {
			final int c = getPrecision().compareTo(f.getPrecision());
			if (c == 0) {
				if (isNaN() || f.isNaN())
					return getNaN();
				else if (isInfinite()) {
					if (signum() < 0) {
						if (f.isNegativeZero() || f.signum() == 0)
							return createFP("1");
						else if (f.isInfinite()) {
							if (f.signum() < 0)
								return new BinaryFP(i(0), 0, 0);
							return getPositiveInfinity();
						} else if (f.signum() > 0)
							return f.isOdd() ? getNegativeInfinity() : getPositiveInfinity();
						return f.isOdd() ? getNegativeZero() : new BinaryFP(i(0), 0, 0);
					} else if (f.isNegativeZero() || f.signum() == 0)
						return createFP("1");
					else if (f.isInfinite()) {
						if (f.signum() > 0)
							return getPositiveInfinity();
						return new BinaryFP(i(0), 0, 0);
					} else if (f.signum() < 0)
						return new BinaryFP(i(0), 0, 0);
					return getPositiveInfinity();
				} else if (f.isInfinite()) {
					if (f.signum() < 0) {
						if (isNegativeZero() || signum() == 0)
							return createFP("1");
						else if (isInfinite()) {
							if (signum() < 0)
								return new BinaryFP(i(0), 0, 0);
							return getPositiveInfinity();
						} else if (signum() > 0)
							return isOdd() ? getNegativeInfinity() : getPositiveInfinity();
						return isOdd() ? getNegativeZero() : new BinaryFP(i(0), 0, 0);
					} else if (isNegativeZero() || signum() == 0)
						return createFP("1");
					else if (isInfinite()) {
						if (signum() > 0)
							return getPositiveInfinity();
						return new BinaryFP(i(0), 0, 0);
					} else if (signum() < 0)
						return new BinaryFP(i(0), 0, 0);
					return getPositiveInfinity();
				} else if (isNegativeZero()) {
					if (f.signum() == 0)
						return createFP("1");
					else if (f.signum() < 0)
						return getPositiveInfinity();
					return new BinaryFP(i(0), 0, 0);
				} else if (f.isNegativeZero()) {
					if (signum() == 0)
						return createFP("1");
					else if (signum() < 0)
						return getPositiveInfinity();
					return new BinaryFP(i(0), 0, 0);
				}
				return new BinaryFP(
						Arith.pow(toBigDecimal(), f.toBigDecimal(), getMathContext(10, false, significandLength)), 0,
						0);
			} else if (c > 0) {
				return pow(f.cast(getPrecision()));
			}
			return cast(f.getPrecision()).pow(f);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 23:06:17--------------------------------------------
		 */
		/**
		 * Computes euler's number raised to the power of {@code this} and returns the
		 * result.
		 * 
		 * @return euler's number raised to the power of {@code this}
		 */
		public BinaryFP exp() {
			if (isNaN())
				return getNaN();
			else if (isInfinite())
				return signum() < 0 ? new BinaryFP(i(0), 0, 0) : getPositiveInfinity();
			return new BinaryFP(Arith.exp(toBigDecimal(), getMathContext(10, false, significandLength)), 0, 0);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 23:08:17--------------------------------------------
		 */
		/**
		 * Computes the natural logarithm of {@code this} and returns the result.
		 * 
		 * @return the natural logarithm of {@code this}
		 */
		public BinaryFP log() {
			if (isNaN())
				return getNaN();
			else if (isInfinite()) {
				return signum() < 0 ? getNaN() : getPositiveInfinity();
			} else if (isNegativeZero())
				return getNegativeInfinity();
			else if (signum() < 0)
				return getNaN();
			return new BinaryFP(Arith.log(toBigDecimal(), getMC(10)), 0, 0);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 23:08:56--------------------------------------------
		 */
		/**
		 * Computes the decimal (base 10) logarithm of {@code this} and returns the
		 * result
		 * 
		 * @return the base 10 logarithm of {@code this}
		 */
		public BinaryFP log10() {
			return createFP("10").log(this);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 23:10:15--------------------------------------------
		 */
		/**
		 * Computes and returns the binary (base 2) logarithm of {@code this}.
		 * 
		 * @return the base 2 logarithm of {@code this}
		 */
		public BinaryFP log2() {
			return createFP("2").log(this);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 23:11:26--------------------------------------------
		 */
		/**
		 * Computes the logarithm of the argument in the base-{@code this}, i.e
		 * calculates the log of the argument using {@code this} as the base of the
		 * calculation.
		 * 
		 * @param f the value for which the logarithm would be calculated
		 * @return the logarithm of the argument using {@code this} as the base
		 */
		public BinaryFP log(BinaryFP f) {
			final int c = getPrecision().compareTo(f.getPrecision());
			if (c == 0) {
				return f.log().divide(log());
			} else if (c > 0) {
				return log(f.cast(getPrecision()));
			}
			return cast(f.getPrecision()).log(f);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 23:14:43--------------------------------------------
		 */
		/**
		 * Computes and returns the tangent of {@code this}.
		 * 
		 * @return the tangent of {@code this}
		 */
		public BinaryFP tan() {
			if (isNaN())
				return getNaN();
			else if (isInfinite())
				return getNaN();
			return new BinaryFP(Arith.tan(toBigDecimal(), RAD, getMC(10)), 0, 0);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 23:15:43--------------------------------------------
		 */
		/**
		 * Computes and returns the arc-tangent of {@code this}.
		 * 
		 * @return the arc-tangent of {@code this}
		 */
		public BinaryFP atan() {
			if (isNaN())
				return getNaN();
			else if (isInfinite()) {
				BinaryFP ans = pi().divide(createFP("2"));
				return signum() < 0 ? ans.negate() : ans;
			}
			return new BinaryFP(Arith.atan(toBigDecimal(), RAD, getMC(10)), 0, 0);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 23:16:21--------------------------------------------
		 */
		/**
		 * Computes and returns the angle theta from the conversion of rectangular
		 * coordinates ({@code this}, {@code f}) to polar coordinates (r, theta). This
		 * method computes the phase theta by computing an arc tangent of
		 * <code>f / this</code> in the range of -pi to pi.
		 * 
		 * @param f the ordinate angle
		 * @return the theta component of the point(r, theta) in polar coordinates that
		 *         corresponds to the point({@code this}, {@code f}) in Cartesian
		 *         coordinates.
		 */
		public BinaryFP atan2(BinaryFP f) {
			final int c = getPrecision().compareTo(f.getPrecision());
			if (c == 0) {
				if (signum() > 0)
					return f.divide(this).tan();
				else if (signum() < 0 && f.signum() >= 0)
					return f.divide(this).tan().add(pi());
				else if (signum() < 0 && f.signum() < 0)
					return f.divide(this).subtract(pi());
				else if (signum() == 0 && f.signum() > 0)
					return pi().divide(createFP("2"));
				else if (signum() == 0 && f.signum() < 0)
					return pi().negate().divide(createFP("2"));
				else if (signum() == 0 && f.signum() == 0)
					return getNaN();
				return getNaN();
			} else if (c > 0) {
				return atan2(f.cast(getPrecision()));
			}
			return cast(f.getPrecision()).atan2(f);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 23:20:00--------------------------------------------
		 */
		/**
		 * Computes and returns the sine of {@code this}.
		 * 
		 * @return the sine of {@code this}
		 */
		public BinaryFP sin() {
			if (isNaN())
				return getNaN();
			else if (isInfinite()) {
				return getNaN();
			} else if (isNegativeZero() || signum() == 0)
				return this;
			return new BinaryFP(Arith.sin(toBigDecimal(), RAD, getMC(10)), 0, 0);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 23:20:34--------------------------------------------
		 */
		/**
		 * Computes and returns the arc-sine of {@code this}.
		 * 
		 * @return the arc-sine of {@code this}
		 */
		public BinaryFP asin() {
			if (isNaN())
				return getNaN();
			else if (isInfinite()) {
				return getNaN();
			} else if (isNegativeZero() || signum() == 0)
				return getNegativeInfinity();
			else if (signum() == 0 || isNegativeZero())
				return this;
			return new BinaryFP(Arith.asin(toBigDecimal(), RAD, getMC(10)), 0, 0);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 23:20:53--------------------------------------------
		 */
		/**
		 * Computes and returns the cosine of {@code this}.
		 * 
		 * @return the cosine of {@code this}
		 */
		public BinaryFP cos() {
			if (isNaN() || isInfinite())
				return getNaN();
			else if (isNegativeZero() || signum() == 0)
				return this;
			return new BinaryFP(Arith.cos(toBigDecimal(), RAD, getMC(10)), 0, 0);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 23:21:08--------------------------------------------
		 */
		/**
		 * Computes and returns the arc-cosine of {@code this}.
		 * 
		 * @return the arc-cosine of {@code this}
		 */
		public BinaryFP acos() {
			if (isNaN() || isInfinite())
				return getNaN();
			else if (isNegativeZero() || signum() == 0)
				return pi().divide(createFP("2"));
			return new BinaryFP(Arith.acos(toBigDecimal(), RAD, getMC(10)), 0, 0);
		}

		////////////////////////////////////////////////////////////
		//////////////// toString() operations ////////////////////
		//////////////////////////////////////////////////////////

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 23:25:32--------------------------------------------
		 */
		/**
		 * Returns {@code this} as a normalised string in the given radix. The string
		 * return uses the format:
		 * 
		 * <pre>
		 * <code>1.[significand]p[unbiasedExponent]</code>
		 * </pre>
		 * 
		 * if {@code this} is in the normal range
		 * <p>
		 * or:
		 * 
		 * <pre>
		 * <code>0.[significand]p[minExponent - 1]</code>
		 * </pre>
		 * 
		 * if {@code this} lies inside the subnormal range.
		 * 
		 * <p>
		 * If {@code this} is among the following special values then:
		 * <ul>
		 * <li>NaN: This is returned literally</li>
		 * <li>Infinity: This is returned literally with the appropriate sign</li>
		 * <li>-0.0: This is returned literally with the appropriate sign</li>
		 * <li>0.0: This is returned as <code>0p0</code></li>
		 * </ul>
		 * Note that the magnitude of the exponent is always decimal regardless of the
		 * radix specified
		 * 
		 * @param radix the base in which the {@code String} will be returned. Note that
		 *              if this value is 2 then the binary fraction will be returned
		 *              (truncated to fit the number of significant bits) with binary
		 *              exponent appended to the resulting string
		 * @return the normalised string representing {@code this} in the specified
		 *         radix
		 */
		public String toNormalisedString(int radix) {
			if (isNaN())
				return NaN_STRING;
			else if (isInfinite())
				return String.format("%1$s%2$s", signum() < 0 ? "-" : "", INFINITY_STRING);
			else if (isNegativeZero())
				return "-0.0p+0";
			else if (signum() == 0)
				return "0.0p+0";
			StringBuilder sb = new StringBuilder(getBitLength());
			sb.append(signum() >= 0 ? "" : "-").append(isSubnormal() ? "0" : "1").append('.')
					.append(getTrailingZeros(significandLength - 1).or(getSignificand()).toString(2).substring(1));

			if (radix == 2)
				return sb.append('p').append(getExponent()).toString();
			String n = FloatAid.toString(sb.toString(), 2, radix, getMathContext(radix, isSubnormal(),
					getMC(radix).getPrecision() + (isSubnormal() && radix % 2 != 0 ? 0 : 1)));
			int e = !isSubnormal() ? getExponent() : getMinExponent();
			return String.format("%1$sp%2$s", n, e >= 0 ? "+" + e : e);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 23:39:28--------------------------------------------
		 */
		/**
		 * Returns {@code this} as a normalised hexadecimal string. All the special
		 * values applicable {@link #toNormalisedString(int) here} also apply here as
		 * well.
		 * 
		 * @return the normalised hex string representing @code this}
		 * @see #toNormalisedString(int)
		 */
		public String toNormalisedString() {
			return toNormalisedString(16);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 23:41:43--------------------------------------------
		 */
		/**
		 * Returns the bit layout of {@code this} as a {@code String} in the given radix
		 * 
		 * @param radix the radix of the resulting {@code String}
		 * @return the bit layout of {@code this} in the specified radix
		 */
		public String toBitString(int radix) {
			return val.toString(radix);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 23:45:00--------------------------------------------
		 */
		/**
		 * Returns the bit layout of {@code this} as a decimal string
		 * 
		 * @return the bit layout of {@code this} as a decimal string
		 */
		public String toBitString() {
			return toBitString(10);
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 23:49:27--------------------------------------------
		 */
		/**
		 * Returns {@code this} as a {@code String} in the given radix. This is not in
		 * normalised form (binary scientific notation), for that use
		 * {@link #toNormalisedString(int)}. None of the values have exponent.
		 * 
		 * @param radix the radix of the resulting {@code String}
		 * @return {@code this} as a number {@code String} in the given radix
		 */
		public String toString(int radix) {
			if (isNaN())
				return NaN_STRING;
			else if (isInfinite())
				return String.format("%1$s%2$s", signum() < 0 ? "-" : "", INFINITY_STRING);
			else if (isNegativeZero())
				return "-0.0";
			else if (signum() == 0)
				return "0";
			// decompose
			if (val.signum() < 0)
				throw new ArithmeticException("Layout was signed");
			// sign, exponent, implicit, significand
			BigInteger[] b = new BigInteger[4];
			b[0] = signum() < 0 ? i(1) : i(0);
			b[1] = getExponentMask(true).and(val).shiftRight(significandLength - 1);
			b[2] = i(1);
			b[3] = getSignificandMask(true).and(val);
			if (b[1].bitCount() == getExponentBitLength()) {// Infinity or NaN
				if (b[3].signum() == 0) {// Infinity
					b[1] = i(getMaxExponent());
					b[3] = null;
				} else {// NaN
					b[0] = i(0);
					b[1] = i(getMaxExponent());
					b[2] = i(0);
					b[3] = null;
				}
			} else if (b[1].bitCount() == 0) {// Subnormal or zero
				if (b[3].signum() != 0) {// Subnormal
					b[1] = i(getMinExponent());
					b[2] = i(0);
				}
			} else {// Normal range
				b[1] = b[1].subtract(i(getBias()));
			}

			// toBigDecimal
			StringBuilder sb = new StringBuilder(getBitLength());
			sb.append(b[2].toString(2)).append('.')
					.append(getTrailingZeros(significandLength - 1).or(b[3]).toString(2).substring(1)).append('p')
					.append(b[1].toString(2));
			return String.format("%1$s%2$s", b[0].signum() == 0 ? "" : "-",
					FloatAid.toString(sb.toString(), 2, radix, getMC(radix)));
		}

		/*
		 * Date: 25 Nov 2022-----------------------------------------------------------
		 * Time created: 07:19:53--------------------------------------------
		 */
		/**
		 * Converts the {@code this} into a forced scientific string in the given radix.
		 * Note that this exponent is 'e'(signed) and is in the same radix as specified
		 * 
		 * @param radix the radix of the resulting {@code String}
		 * @return {@code this} as a scientific number {@code String} in the given radix
		 */
		public String toScientificString(int radix) {
			if (isNaN())
				return NaN_STRING;
			else if (isInfinite())
				return String.format("%1$s%2$s", signum() < 0 ? "-" : "", INFINITY_STRING);
			else if (isNegativeZero())
				return "-0.0e+0";
			else if (signum() == 0)
				return "0.0e+0";
			// decompose
			if (val.signum() < 0)
				throw new ArithmeticException("Layout was signed");
			// sign, exponent, implicit, significand
			BigInteger[] b = new BigInteger[4];
			b[0] = signum() < 0 ? i(1) : i(0);
			b[1] = getExponentMask(true).and(val).shiftRight(significandLength - 1);
			b[2] = i(1);
			b[3] = getSignificandMask(true).and(val);
			if (b[1].bitCount() == getExponentBitLength()) {// Infinity or NaN
				if (b[3].signum() == 0) {// Infinity
					b[1] = i(getMaxExponent());
					b[3] = null;
				} else {// NaN
					b[0] = i(0);
					b[1] = i(getMaxExponent());
					b[2] = i(0);
					b[3] = null;
				}
			} else if (b[1].bitCount() == 0) {// Subnormal or zero
				if (b[3].signum() != 0) {// Subnormal
					b[1] = i(getMinExponent());
					b[2] = i(0);
				}
			} else {// Normal range
				b[1] = b[1].subtract(i(getBias()));
			}

			// toBigDecimal
			StringBuilder sb = new StringBuilder(getBitLength());
			// use normalised format here
			sb.append(b[2].toString(2)).append('.')
					.append(getTrailingZeros(significandLength - 1).or(b[3]).toString(2).substring(1)).append('p')
					.append(b[1].toString(2));
			// convert from normalised format here
			return String.format("%1$s%2$s", b[0].signum() == 0 ? "" : "-",
					FloatAid.toScientificString(sb.toString(), 2, radix, getMC(radix)));
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 23:54:20--------------------------------------------
		 */
		/**
		 * Returns {@code this} as a decimal engineering {@code String}. This is the
		 * same as calling: <code>toBigDecimal().toEngineeringString()</code>
		 * 
		 * @return {@code this} in decimal engineering {@code String} format
		 */
		public String toEngineeringString() {
			if (isNaN())
				return NaN_STRING;
			else if (isInfinite())
				return (signum() < 0 ? "-" : "") + INFINITY_STRING;
			else if (isNegativeZero())
				return "-0.0";
			else if (signum() == 0)
				return "0";
			return toBigDecimal().round(getMC(10)).toEngineeringString();
		}

		/*
		 * Date: 15 Nov 2022-----------------------------------------------------------
		 * Time created: 23:57:18--------------------------------------------
		 */
		/**
		 * Returns {@code this} as a decimal {@code String} without an exponent. This is
		 * the same as calling: <code>toBigDecimal().toPlainString()</code>. Note that
		 * this is equivalent to calling <code>toString(10)</code>
		 * 
		 * @return {@code this} in decimal {@code String} without an exponent
		 */
		public String toPlainString() {
			if (isNaN())
				return NaN_STRING;
			else if (isInfinite())
				return (signum() < 0 ? "-" : "") + INFINITY_STRING;
			else if (isNegativeZero())
				return "-0.0";
			else if (signum() == 0)
				return "0";
			return toBigDecimal().toPlainString();
		}

		////////////////////////////////////////////////////////////
		////////////// Miscellaneous operations ///////////////////
		//////////////////////////////////////////////////////////

		/*
		 * Date: 16 Nov 2022-----------------------------------------------------------
		 * Time created: 00:13:01--------------------------------------------
		 */
		/**
		 * Recalculates {@code this} in another precision and returns the result.
		 * <p>
		 * Special cases:
		 * <ul>
		 * <li>If {@code this == NaN} then NaN is returned no matter the precision.</li>
		 * <li>If {@code this == Infinity} then Infinity is returned no matter the
		 * precision.</li>
		 * <li>If {@code this == -Infinity} then -Infinity is returned no matter the
		 * precision.</li>
		 * <li>If {@code this == -0.0} then -0.0 is returned no matter the
		 * precision.</li>
		 * </ul>
		 * If the precision hold smaller values than the current {@link #getPrecision()}
		 * from which {@code this} was created, then the value returned may be an
		 * infinity value because an overflow occurred. If casting causes an underflow
		 * to occur, then 0 is returned.
		 * 
		 * @param p the precision to which {@code this} is cast
		 * @return a value equal to {@code this} in the given {@code BinaryFPPrecision}.
		 */
		public BinaryFP cast(BinaryFPPrecision p) {
			if (p.compareTo(getPrecision()) == 0)
				return this;
			BigInteger exp;
			BigInteger sig;
			if (isNaN()) {
				exp = getAllOnes(p.exponentLength);
				sig = FloatAid.getTrailingZeros(p.significandLength - 2);
			} else if (isInfinite() || getExponent() > p.getMaxExponent()) {// Infinity
				exp = getAllOnes(p.exponentLength);
				sig = i(0);
			} else if (signum() == 0 || getExponent() < p.getMinExponent()) {// Clip to zero because of underflow
				exp = i(0);
				sig = i(0);
			} else {
				exp = i(getExponent() + p.getBias()).abs();
				sig = getSignificand().shiftRight((getSignificandBitLength() - 1) - (p.getSignificandBitLength() - 1));
			}
			return p.new BinaryFP(p.iEEE754(signum() < 0 ? i(1) : i(0), exp, sig), 0, 0);
		}

		////////////////////////////////////////////////////////////
		///////////////// Private operations //////////////////////
		//////////////////////////////////////////////////////////

		/*
		 * Date: 16 Nov 2022-----------------------------------------------------------
		 * Time created: 00:22:37--------------------------------------------
		 */
		/**
		 * Returns {@code true} if {@code this} is finite, an non-zero integer and it
		 * even parity else returns {@code false}
		 * 
		 * @return {@code true} if {@code this} is even else returns {@code false}
		 */
		private boolean isEven() {
			if (signum() == 0 || isNegativeZero() || !isFinite())
				return false;
			return isInteger(toBigDecimal()) && toBigDecimal().toBigInteger().remainder(i(2)).signum() == 0;
		}

		/*
		 * Date: 16 Nov 2022-----------------------------------------------------------
		 * Time created: 00:26:51--------------------------------------------
		 */
		/**
		 * Returns {@code true} if {@code this} is finite, an non-zero integer and it
		 * odd parity else returns {@code false}
		 * 
		 * @return {@code true} if {@code this} is odd else returns {@code false}
		 * @return
		 */
		private boolean isOdd() {
			if (signum() == 0 || isNegativeZero() || !isFinite())
				return false;
			return !isEven();
		}

		/*
		 * Date: 16 Nov 2022-----------------------------------------------------------
		 * Time created: 00:27:20--------------------------------------------
		 */
		/**
		 * Returns a valid {@code MathContext} object for {@code this}. For all normal
		 * values, this method calculates the number of bits in the significand and
		 * returns the approximate value in the given radix. For subnormal values, this
		 * method returns the number of bits remaining in the significand portion and
		 * converts it to the given radix to be returned.
		 * 
		 * @param radix the radix for which the {@link MathContext} is for
		 * @return a {@code MathContext} for the input radix depending on whether
		 *         {@code this} is normal or not
		 */
		private MathContext getMC(int radix) {
			return getMathContext(radix, isSubnormal(), radix == 10 ? getSignificand().bitLength()
					: getSignificandBitLength() - getSignificand().bitLength());
		}

		/*
		 * Date: 16 Nov 2022-----------------------------------------------------------
		 * Time created: 00:31:25--------------------------------------------
		 */
		/**
		 * Helper method for {@link #ceil()} and {@link #floor()}. Copied from java.Math
		 * 
		 * @param nBound the negative boundary
		 * @param pBound the positive boundary
		 * @param sign
		 * @return
		 */
		private BinaryFP floorOrCeil(BinaryFP nBound, BinaryFP pBound, BinaryFP sign) {
			int exp = getExponent();

			if (exp < 0)
				return signum() == 0 ? this : signum() < 0 ? nBound : pBound;
			else if (exp >= (significandLength - 1))
				return this;

//			BigInteger bits = val;
			BigInteger mask = getSignificandMask(true).shiftRight(exp);
//			BigInteger mask = BinaryRep.TWO_C.shiftRight(getSignificandMask(true), exp, getBitLength());

			if (mask.and(val).signum() == 0)
				return this;

			BinaryFP res = new BinaryFP(val.and(mask.not()), 0, 0);
//			BinaryFP res = new BinaryFP(BinaryRep.TWO_C.and(val, BinaryRep.TWO_C.not(mask, getBitLength()), getBitLength()));

			if (sign.multiply(this).signum() > 0)
				res = res.add(sign);
			return res;
		}

		/*
		 * Date: 16 Nov 2022-----------------------------------------------------------
		 * Time created: 00:32:28--------------------------------------------
		 */
		/**
		 * Helper method for {@link #ulp()}. Copied from java.Math
		 * 
		 * @param exp
		 * @return
		 */
		private BinaryFP powerOf2(int exp) {
			return new BinaryFP(i(exp).add(i(getBias())).shiftLeft(significandLength - 1).and(getExponentMask(true)), 0,
					0);
		}

		/*
		 * Date: 16 Nov 2022-----------------------------------------------------------
		 * Time created: 00:33:16--------------------------------------------
		 */
		/**
		 * Calculates {@code this + f} on a bit-by-bit basis
		 * 
		 * @param f the addend
		 * @return <code>this + f</code>
		 */
		private BinaryFP doNormalAddition(BinaryFP f) {
			BigInteger sigMask = getTrailingZeros(significandLength - 1);
			BigInteger sig = isSubnormal() ? getSignificand() : sigMask.or(getSignificand());
			BigInteger fsig = f.isSubnormal() ? f.getSignificand() : sigMask.or(f.getSignificand());
			BigInteger exp = getBiasedExponent();
			BigInteger fexp = f.getBiasedExponent();
			int s = signum();
			int fs = f.signum();
			if (exp.compareTo(fexp) > 0) {
				int diff = exp.subtract(fexp).intValue();
				fexp = fexp.add(i(diff));
				fsig = fsig.shiftRight(diff);
			} else if (exp.compareTo(fexp) < 0) {
				int diff = fexp.subtract(exp).intValue();
				exp = exp.add(i(diff));
				sig = sig.shiftRight(diff);
			}
			sig = sig.multiply(i(s)).add(fsig.multiply(i(fs)));
			s = sig.signum();
			sig = sig.abs();
			if (sig.bitLength() > getSignificandBitLength()) {
				BigInteger guardBit = sig.testBit(sig.bitLength() - (significandLength + 1)) ? i(1) : i(0);
				sig = sig.add(guardBit);
				int diff = sig.bitLength() - getSignificandBitLength();
				exp = exp.add(i(diff));
				sig = sig.shiftRight(diff);
			} else if (sig.bitLength() < getSignificandBitLength()) {
				int diff = getSignificandBitLength() - sig.bitLength();
				exp = exp.subtract(i(diff));
				sig = sig.shiftLeft(diff);
			}
			if (exp.compareTo(i(getMaxExponent() + getBias())) > 0) {// Infinite, caused by overflow
				exp = getAllOnes(exponentLength);
				sig = i(0);
			} else if (exp.compareTo(i(getMinExponent() + getBias())) < 0) {// 0 caused by underflow
				exp = i(0);
				sig = i(0);
			}

			return new BinaryFP(
					iEEE754(s < 0 ? i(1) : i(0), exp, sig.bitLength() == significandLength ? sig.xor(sigMask) : sig), 0,
					0);
		}

		/*
		 * Date: 14 Nov 2022-----------------------------------------------------------
		 * Time created: 03:43:52--------------------------------------------
		 */
		/**
		 * Calculates {@code this * f} on a bit-by-bit basis
		 * 
		 * @param f the multiplicand
		 * @return <code>this * f</code>
		 */
		private BinaryFP doNormalMultiplication(BinaryFP f) {
			BigInteger sigMask = getTrailingZeros(significandLength - 1);
			BigInteger sig = isSubnormal() ? getSignificand() : sigMask.or(getSignificand());
			BigInteger fsig = f.isSubnormal() ? f.getSignificand() : sigMask.or(f.getSignificand());
			BigInteger exp = getBiasedExponent();
			BigInteger fexp = f.getBiasedExponent();
			int s = signum();
			int fs = f.signum();

			if (isSubnormal()) {
				exp = exp.subtract(i((significandLength - (sig.compareTo(i(1)) == 0 ? 1 : 2)) - sig.bitLength()));
			}
			if (f.isSubnormal()) {
				fexp = fexp.subtract(i((significandLength - (fsig.compareTo(i(1)) == 0 ? 1 : 2)) - fsig.bitLength()));
			}

			sig = sig.multiply(fsig);
			s = s * fs;
			sig = sig.abs();
			exp = exp.add(fexp).subtract(i(getBias()));

			if (sig.bitLength() > getSignificandBitLength()) {
				BigInteger guardBit = sig.testBit(sig.bitLength() - (significandLength + 1)) ? i(1) : i(0);
				sig = sig.add(guardBit);
				int diff = sig.bitLength() - getSignificandBitLength();
				sig = sig.shiftRight(diff);
			} else if (sig.bitLength() < getSignificandBitLength()) {
				int diff = getSignificandBitLength() - sig.bitLength();
				sig = sig.shiftLeft(diff);
			}
			if (exp.compareTo(i(getMaxExponent() + getBias())) > 0) {// Infinite, caused by overflow
				exp = getAllOnes(exponentLength);
				sig = i(0);
			} else if (exp.compareTo(i(getMinExponent() + getBias())) < 0) {// 0 caused by underflow
				exp = i(0);
				sig = i(0);
			}

			return new BinaryFP(iEEE754(s < 0 ? i(1) : i(0), exp.abs(),
					sig.bitLength() == significandLength ? sig.xor(sigMask) : sig), 0, 0);
		}

		/*
		 * Date: 16 Nov 2022-----------------------------------------------------------
		 * Time created: 00:35:21--------------------------------------------
		 */
		/**
		 * Calculates {@code this / f} on a bit-by-bit basis
		 * 
		 * @param f the divisor
		 * @return <code>this / f</code>
		 */
		private BigInteger doNormalDivision(BinaryFP f) {
			int s = signum();
			final int fs = f.signum();
			/*
			 * retrieve their biased exponent bits from the IEEE754 bit layout. From now on
			 * we will refer to this value as the exponent
			 */
			int exp = getBiasedExponent().intValue();
			final int fexp = f.getBiasedExponent().intValue();
			/*
			 * add implicit bit to both significand if they are within normal range else do
			 * nothing
			 */
			/*
			 * add implicit bit to both significand if they are within normal range else do
			 * nothing
			 */
			BigInteger sigMask = getTrailingZeros(significandLength - 1);
			BigInteger sig = isSubnormal() ? getSignificand() : sigMask.or(getSignificand());
			BigInteger fsig = f.isSubnormal() ? f.getSignificand() : sigMask.or(f.getSignificand());
			final int sigBits = isSubnormal() ? significandLength : sig.bitLength();
			/*
			 * The following will prevent the dividend from being less than the divisor
			 * (triggering a zero quotient) and ensure a meaningful integer-to-integer
			 * division can occur (possibly without remainder).
			 */
			int numOfDivShifts = fsig.bitLength() > sig.bitLength() ? fsig.bitLength() - sig.bitLength() : 0;
			while (numOfDivShifts <= getSignificandBitLength()) {
				numOfDivShifts += Byte.SIZE;
			}
			/*
			 * perform the actual division operation by representing each significand in the
			 * sign of their respective values and then divide them.
			 */
			sig = sig.shiftLeft(numOfDivShifts).divide(fsig);
			// Also subtract the exponents
			exp -= numOfDivShifts;
			exp -= fexp;
			exp += getBias();
			exp += sigBits - 1;
			// save the sign of the result as a IEEE754 sign
			s *= fs;
			/*
			 * if the result has an implicit bit of more than 2 length, then shift the
			 * result right by the difference between the the bit length of the result and
			 * the total number of significand bits in the given precision that we are
			 * working with. Add the difference to the exponent (by now, both exponents are
			 * the equal), and if the implicit bit is still available, remove it.
			 */
			if (sig.bitLength() > getSignificandBitLength() - 1) {
				BigInteger lastBit = sig.testBit(sig.bitLength() - (getSignificandBitLength() - 1)) ? i(1) : i(0);
				int difference = sig.bitLength() - getSignificandBitLength();
				sig = sig.shiftRight(difference).add(lastBit);
				if (sig.bitLength() > getSignificandBitLength()) {
					sig = sig.shiftRight(1);
					exp += 1;
				}
				if (sig.testBit(getSignificandBitLength() - 1)) {
					sig = sig.xor(sigMask);
				}
				exp += difference;
			} else if (sig.bitLength() < getSignificandBitLength()) {// subnormal!
				int difference = numOfDivShifts;
				sig = sig.shiftRight(difference);
				// roll back all changes to the exponent
				exp += difference;
				exp -= sigBits - 1;
				exp -= getBias();
				exp += fexp;
			}

			if (exp > (getMaxExponent() + getBias())) {// Infinity
				exp = getAllOnes(exponentLength).intValue();
				sig = i(0);
			} else if (exp < (getMinExponent() + getBias())) {// 0 under flow
				exp = 0;
				sig = i(0);
				s = 0;
			}

			/*
			 * assemble the disjointed bits into a IEEE754 bit layout of a floating point
			 * number. This includes the sign, the exponent and the significand of the
			 * result
			 */
			fsig = iEEE754(i(s < 0 ? 1 : 0), i(exp).abs().and(getAllOnes(exponentLength)), sig);
			return fsig;
		}

		/*
		 * Date: 12 Sep 2020-----------------------------------------------------------
		 * Time created: 12:40:49--------------------------------------------
		 */
		/**
		 * Returns a special string to help {@code SBigD} object instantiation.
		 * 
		 * @param value a {@code BigDecimal}
		 * @return a special {@code String}.
		 */
		private String getSpecialString() {
			if (isInfinite()) {
				return "0.0E" + (signum() < 0 ? "-" : "+") + getMaxExponent();
			} else if (isNaN()) {
				return "0.0E" + (getMinExponent() - -significandLength);

			}
			return "0";
		}

		/*
		 * Date: 17 Nov 2022-----------------------------------------------------------
		 * Time created: 19:18:12--------------------------------------------
		 */
		/**
		 * Splits {@code this} into it's high bits and low bits and puts the result in
		 * the given indexes in the specified array. For example, a 128 bit floating
		 * point value will have it's high 64 bits in the first index and it's low 64
		 * bits in the second index, this will includes leading bits that are 0. This
		 * method is undefined if {@link BinaryFPPrecision#getBitLength()} returns an
		 * {@code int} value that is not even.
		 * 
		 * <p>
		 * Return value is set to {@code void} to prevent unnecessary extra assignment
		 * statement
		 * 
		 * @param reuseableContainer a reference array which has the capacity to contain
		 *                           both {@code int} arguments
		 * @param highIndex          the index within the given array where the high
		 *                           bits are to be stored
		 * @param lowIndex           the index within the given array where the low bits
		 *                           are to be stored
		 */
		void getHighAndLowBits(BigInteger[] reuseableContainer, int highIndex, int lowIndex) {
			// check not needed as this is private
//			if (highLow == null || highLow.length < 2)
//				highLow = new BigInteger[2];
			int halfWay = getBitLength() / 2;
			// Segregate all the high bits
			reuseableContainer[highIndex] = val.shiftRight(halfWay);// and(getAllOnes(halfWay).shiftLeft(halfWay));
			// Segregate all the low bits
			reuseableContainer[lowIndex] = val.and(getAllOnes(halfWay));
		}

		/*
		 * Date: 17 Nov 2022-----------------------------------------------------------
		 * Time created: 22:37:45--------------------------------------------
		 */
		/**
		 * Extracts the high bits (the first {@link BinaryFPPrecision#getBitLength() bit
		 * length of precision} / 2. For a {@code double} value the high bits will be
		 * the first 32 bits. For a {@code float} value the high bits are the first 16
		 * bits) of {@code this} and puts them in the specified index of the given
		 * array.
		 * 
		 * <p>
		 * Return value is set to {@code void} to prevent unnecessary extra assignment
		 * statement
		 * 
		 * @param reuseableContainer a reference array that can contain the specified
		 *                           {@code indexToUse}
		 * @param indexToUse         the index to put the result within the argument
		 *                           array
		 */
		void getHighBits(BigInteger[] reuseableContainer, int indexToUse) {
			reuseableContainer[indexToUse] = val.shiftRight(getBitLength() / 2);
		}

		/*
		 * Date: 17 Nov 2022-----------------------------------------------------------
		 * Time created: 22:43:19--------------------------------------------
		 */
		/**
		 * Extracts the low bits (the last {@link BinaryFPPrecision#getBitLength() bit
		 * length of precision} / 2. For a {@code double} value the low bits will be the
		 * last 32 bits. For a {@code float} value the low bits are the last 16 bits) of
		 * {@code this} and puts them in the specified index of the given array.
		 * 
		 * <p>
		 * Return value is set to {@code void} to prevent unnecessary extra assignment
		 * statement
		 * 
		 * @param reuseableContainer a reference array that can contain the specified
		 *                           {@code indexToUse}
		 * @param indexToUse         the index to put the result within the argument
		 *                           array
		 */
		void getLowBits(BigInteger[] reuseableContainer, int indexToUse) {
			reuseableContainer[indexToUse] = val.and(getAllOnes(getBitLength() / 2));
		}

		/*
		 * Date: 17 Nov 2022-----------------------------------------------------------
		 * Time created: 22:44:41--------------------------------------------
		 */
		/**
		 * Sets the most significant (high) bits (the first
		 * {@link BinaryFPPrecision#getBitLength() bit length of precision} / 2. For a
		 * {@code double} value the high bits will be the first 32 bits. For a
		 * {@code float} value the high bits are the first 16 bits) of {@code this} to
		 * the given {@code BigInteger} argument and puts the full value (together with
		 * the old low bits) in the specified index of the array argument.
		 * 
		 * <p>
		 * Note that this method does not mutate {@code this} object
		 * <p>
		 * Return value is set to {@code void} to prevent unnecessary extra assignment
		 * statement
		 * 
		 * @param reuseableContainer a reference array that can contain the specified
		 *                           {@code indexToUse}
		 * @param val                the {@code BigInteger} value to be set as the new
		 *                           high bits
		 * @param indexToUse         the index to put the result within the argument
		 *                           array
		 * @throws ArithmeticException if the number of bits in {@code val} is greater
		 *                             than {@link BinaryFPPrecision#getBitLength() bit
		 *                             length of precision} / 2
		 */
		void setHighBits(BigInteger[] reuseableContainer, BigInteger val, int indexToUse) throws ArithmeticException {
			int halfWay = getBitLength() / 2;
			if (val.bitLength() > halfWay)
				throw new ArithmeticException("val bit length is greater than bits needed");
			getLowBits(reuseableContainer, 0);
			reuseableContainer[indexToUse] = val.shiftLeft(halfWay).or(reuseableContainer[0]);
		}

		/*
		 * Date: 17 Nov 2022-----------------------------------------------------------
		 * Time created: 23:01:48--------------------------------------------
		 */
		/**
		 * Sets the least significant (low) bits (the last
		 * {@link BinaryFPPrecision#getBitLength() bit length of precision} / 2. For a
		 * {@code double} value the low bits will be the last 32 bits. For a
		 * {@code float} value the low bits are the last 16 bits) of {@code this} to the
		 * given {@code BigInteger} argument and puts the full value (together with the
		 * old high bits) in the specified index of the array argument.
		 * 
		 * <p>
		 * Note that this method does not mutate {@code this} object
		 * <p>
		 * Return value is set to {@code void} to prevent unnecessary extra assignment
		 * statement
		 * 
		 * @param reuseableContainer a reference array that can contain the specified
		 *                           {@code indexToUse}
		 * @param val                the {@code BigInteger} value to be set as the new
		 *                           low bits
		 * @param indexToUse         the index to put the result within the argument
		 *                           array
		 * @throws ArithmeticException if the number of bits in {@code val} is greater
		 *                             than {@link BinaryFPPrecision#getBitLength() bit
		 *                             length of precision} / 2
		 */
		void setLowBits(BigInteger[] reuseableContainer, BigInteger val, int indexToUse) throws ArithmeticException {
			int halfWay = getBitLength() / 2;
			if (val.bitLength() > halfWay)
				throw new ArithmeticException("val bit length is greater than bits needed");
			getHighBits(reuseableContainer, 0);
			reuseableContainer[indexToUse] = reuseableContainer[0].shiftLeft(halfWay).or(val);
		}

		////////////////////////////////////////////////////////////
		//////////// Overridden methods (java.lang) ///////////////
		//////////////////////////////////////////////////////////

		/*
		 * Most Recent Date: 16 Nov 2022-----------------------------------------------
		 * Most recent time created: 00:36:44--------------------------------------
		 */
		/**
		 * Compares {@code this} for numerical ordering. NaNs will never compare as
		 * equal, infinities will never compare as equals, -0.0 and 0 are lexically the
		 * same as far as this method is concerned. Please see
		 * {@link BigDecimal#compareTo(BigDecimal)} for more details as this method is
		 * different from {@link #equals(Object)} and may return 0 even if equals
		 * returns {@code false}.
		 * <p>
		 * Please note that if the argument is not in the same {@link BinaryFPPrecision
		 * precision} as {@code this}, the {@code BinaryFP}'s precision that is bigger
		 * is used.
		 * 
		 * @param x the value to be compared
		 * @return 1, 0 or -1 as {@code this} is greater than, equal to or less than the
		 *         argument.
		 */
		@Override
		public int compareTo(BinaryFP x) {
			int compare = getPrecision().compareTo(x.getPrecision());
			if (compare == 0) {
				if (isNaN() || x.isNaN()) {
					int[] i = new int[] { -1, 1 };
					return i[new java.util.Random().nextInt(i.length)];
				} else if (isInfinite()) {
					if (signum() >= 0)
						return 1;
					return -1;
				} else if (x.isInfinite()) {
					if (x.signum() >= 0)
						return -1;
					return 1;
				} else if (isNegativeZero()) {
					if (x.signum() == 0)
						return 0;
					return -1;
				} else if (x.isNegativeZero()) {
					if (signum() == 0)
						return 0;
					return 1;
				}
				if (getExponent() != x.getExponent())
					return Integer.compare(getExponent(), x.getExponent());
				BigInteger sig = !isSubnormal() ? getTrailingZeros(significandLength - 1).or(getSignificand())
						: getSignificand();
				BigInteger xsig = !x.isSubnormal() ? getTrailingZeros(significandLength - 1).or(x.getSignificand())
						: x.getSignificand();
				return sig.compareTo(xsig);
			} else if (compare < 0)
				return cast(x.getPrecision()).compareTo(x);
			else
				return compareTo(x.cast(getPrecision()));
		}

		/*
		 * Most Recent Date: 16 Nov 2022-----------------------------------------------
		 * Most recent time created: 00:48:47--------------------------------------
		 */
		/**
		 * Returns the decimal string equivalent to {@code this}. If there is a
		 * necessary exponent, then it is returned in decimal scientific notation
		 * 
		 * @return the decimal {@code String} equivalent to this.
		 */
		@Override
		public String toString() {
			return toScientificString(10);
		}

		/*
		 * Most Recent Date: 16 Nov 2022-----------------------------------------------
		 * Most recent time created: 00:51:05--------------------------------------
		 */
		/**
		 * Returns whether {@code this} is equal to the input object. If the input
		 * object is a type of {@code this} then it must have the same
		 * {@link #getPrecision()} and the same bit layout for it to be considered equal
		 * to {@code this}. This method is mainly for object equality, for numerical
		 * comparison use {@link #compareTo(BinaryFP)} for accurate results.
		 * 
		 * @param o the reference of the value to be compared to {@code this}
		 * @return {@code true} if this is the same object as the argument else returns
		 *         {@code false}
		 */
		@Override
		public boolean equals(Object o) {
			if (o instanceof BinaryFP) {
				BinaryFP f = (BinaryFP) o;
				return getPrecision().compareTo(f.getPrecision()) == 0 && val.equals(f.val);
			}
			return false;
		}

		////////////////////////////////////////////////////////////
		///////////////////// Inner classes ///////////////////////
		//////////////////////////////////////////////////////////

		/*
		 * Date: 12 Sep 2020-----------------------------------------------------------
		 * Time created: 12:28:16---------------------------------------------------
		 * Package:
		 * mathaid.calculator.base.value------------------------------------------------
		 * Project: LatestPoject2------------------------------------------------ File:
		 * FloatPoint.java------------------------------------------------------ Class
		 * name: SBigD------------------------------------------------
		 */
		/**
		 * A Special BigDecimal that can print out infinity and NaN values and negative
		 * zero, without throwing an exception.
		 * 
		 * @author Oruovo Anthony Etineakpopha
		 *
		 */
		private class SBigD extends BigDecimal {
			private static final long serialVersionUID = 3266823728375447853L;

			private SBigD() {
				super(getSpecialString());
			}

			@Override
			public BigDecimal add(BigDecimal augend) {
				return this;
			}

			@Override
			public BigDecimal add(BigDecimal augend, MathContext mc) {
				return this;
			}

			@Override
			public BigDecimal subtract(BigDecimal subtrahend) {
				return this;
			}

			@Override
			public BigDecimal subtract(BigDecimal subtrahend, MathContext mc) {
				return this;
			}

			@Override
			public BigDecimal multiply(BigDecimal multiplicand) {
				return this;
			}

			@Override
			public BigDecimal multiply(BigDecimal multiplicand, MathContext mc) {
				return this;
			}

			@Deprecated(since = "9")
			@Override
			public BigDecimal divide(BigDecimal divisor, int scale, int roundingMode) {
				return this;
			}

			@Override
			public BigDecimal divide(BigDecimal divisor, int scale, RoundingMode roundingMode) {
				return this;
			}

			@Override
			public BigDecimal divide(BigDecimal divisor, int roundingMode) {
				return this;
			}

			@Override
			public BigDecimal divide(BigDecimal divisor) {
				return this;
			}

			@Override
			public BigDecimal divide(BigDecimal divisor, MathContext mc) {
				return this;
			}

			@Override
			public BigDecimal divideToIntegralValue(BigDecimal divisor) {
				return this;
			}

			@Override
			public BigDecimal divideToIntegralValue(BigDecimal divisor, MathContext mc) {
				return this;
			}

			@Override
			public BigDecimal remainder(BigDecimal divisor) {
				return this;
			}

			@Override
			public BigDecimal remainder(BigDecimal divisor, MathContext mc) {
				return this;
			}

			@Override
			public BigDecimal[] divideAndRemainder(BigDecimal divisor) {
				return new SBigD[] { this, this };
			}

			@Override
			public BigDecimal[] divideAndRemainder(BigDecimal divisor, MathContext mc) {
				return new SBigD[] { this, this };
			}

			@Override
			public BigDecimal pow(int n) {
				return this;
			}

			@Override
			public BigDecimal pow(int n, MathContext mc) {
				return this;
			}

			@Override
			public BigDecimal plus() {
				return this;
			}

			@Override
			public BigDecimal plus(MathContext mc) {
				return this;
			}

			@Override
			public BigDecimal round(MathContext mc) {
				return this;
			}

			@Override
			public int signum() {
				return BinaryFP.this.signum();
			}

			@Override
			public BigDecimal setScale(int newScale, RoundingMode roundingMode) {
				return this;
			}

			@Override
			@Deprecated(since = "9")
			public BigDecimal setScale(int newScale, int roundingMode) {
				return this;
			}

			@Override
			public BigDecimal setScale(int newScale) {
				return this;
			}

			@Override
			public BigDecimal movePointLeft(int n) {
				return this;
			}

			@Override
			public BigDecimal movePointRight(int n) {
				return this;
			}

			@Override
			public BigDecimal scaleByPowerOfTen(int n) {
				return this;
			}

			@Override
			public BigDecimal stripTrailingZeros() {
				return this;
			}

			@Override
			public int compareTo(BigDecimal val) {
				return isInfinite() ? 1 : -1;
			}

			@Override
			public BigDecimal min(BigDecimal val) {
				return this;
			}

			@Override
			public BigDecimal max(BigDecimal val) {
				return this;
			}

			@Override
			public BigInteger toBigInteger() {
				return toBigIntegerExact();
			}

			@Override
			public BigInteger toBigIntegerExact() {
				return super.toBigIntegerExact();
			}

			@Override
			public BigDecimal ulp() {
				return this;
			}

			@Override
			public String toString() {
				if (isInfinite())
					return signum() < 0 ? "-Infinity" : "Infinity";
				else if (isNaN())
					return "NaN";
				else if (isNegativeZero())
					return "-0.0";
				return super.toString();
			}

			@Override
			public String toPlainString() {
				return toString();
			}

			@Override
			public String toEngineeringString() {
				return toString();
			}

		}

		////////////////////////////////////////////////////////////
		///////////////////////// Field ///////////////////////////
		//////////////////////////////////////////////////////////

		/*
		 * Date: 16 Nov 2022-----------------------------------------------------------
		 * Time created: 00:51:09---------------------------------------------------
		 */
		/**
		 * The representation of the IEEE754 bit layout of this class
		 */
		private final BigInteger val;
		/**
		 * If this is the result of an overflow, then this is the 4 least most
		 * significand bits of the value truncated from the exponent, if this is the
		 * result an underflow, then this is the 4 most significant bits of all the bits
		 * that did not enter the exponent part
		 */
		private final BigInteger carry;
		/*
		 * Date: 24 Nov 2022-----------------------------------------------------------
		 * Time created: 23:51:27---------------------------------------------------
		 */
		/**
		 * The current exception
		 */
		private final int exception;
	}

}
