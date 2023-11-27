/**
 * 
 */
package mathaid.calculator.base.value;

import static java.util.Arrays.compare;
import static java.util.Arrays.fill;
import static mathaid.calculator.base.util.Arith.log;
import static mathaid.calculator.base.util.Utility.d;
import static mathaid.calculator.base.util.Utility.i;
import static mathaid.calculator.base.util.Utility.numOfFractionalDigits;
import static mathaid.calculator.base.util.Utility.mc;
import static mathaid.calculator.base.util.Utility.rm;
import static mathaid.calculator.base.util.Utility.string;
import static org.hipparchus.util.FastMath.log;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

import org.apfloat.Apfloat;

import mathaid.calculator.base.util.Tuple.Quadruple;

/*
 * Date: 26 Jun 2021----------------------------------------------------------- 
 * Time created: 18:29:02---------------------------------------------------  
 * Package: mathaid.calculator.base.value------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: FloatAid.java------------------------------------------------------ 
 * Class name: FloatAid------------------------------------------------ 
 */
/**
 * The {@code BitAid} class is to the {@code Precision}, {@code FloatPoint},
 * {@code BinaryFPPrecision} and {@code BinaryFP} what {@code Math} is to java's
 * value types ({@code int}, {@code long}, {@code float} and {@code double}). It
 * also has utility methods for manipulating bits (using {@code BigInteger}),
 * fast conversion between radixes etc.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public final class FloatAid {

	/*
	 * Date: 18 Nov 2022-----------------------------------------------------------
	 * Time created: 13:58:55---------------------------------------------------
	 */
	/**
	 * Prevent instantiation
	 */
	private FloatAid() {
	}

	/*
	 * Date: 7 Nov 2022-----------------------------------------------------------
	 * Time created: 20:26:43--------------------------------------------
	 */
	/**
	 * Method for constructing a {@link BinaryFPPrecision} object which is
	 * equivalent to creating a {@code BinaryFPPrecision} object using 4 bits for
	 * the exponent field and 4 bits for the significand (including the implicit
	 * bit) and a of {@link RoundingMode#HALF_UP}.
	 * <p>
	 * Note that this is not standard in IEEE754, but the method is given this name
	 * for ease of reference and grouping with other methods that return a
	 * {@code BinaryFPPrecision}.
	 * 
	 * @return a {@code BinaryFPPrecision} object with 4 bits for the exponent and 4
	 *         bits for the significand (including the implicit bit)
	 */
	public static BinaryFPPrecision IEEE754Bit8() {
		return IEEE754Bit8(rm("HALF_UP"));
	}

	/*
	 * Date: 24 Nov 2022-----------------------------------------------------------
	 * Time created: 02:37:49--------------------------------------------
	 */
	/**
	 * Method for constructing a {@link BinaryFPPrecision} object which is
	 * equivalent to creating a {@code BinaryFPPrecision} object using 4 bits for
	 * the exponent field and 4 bits for the significand (including the implicit
	 * bit) using a given {@code RoundingMode} object for rounding.
	 * <p>
	 * Note that this is not standard in IEEE754, but the method is given this name
	 * for ease of reference and grouping with other methods that return a
	 * {@code BinaryFPPrecision}.
	 * 
	 * @return a {@code BinaryFPPrecision} object with 4 bits for the exponent and 4
	 *         bits for the significand (including the implicit bit)
	 * @param rm a given rounding mode of the precision
	 */
	public static BinaryFPPrecision IEEE754Bit8(RoundingMode rm) {
		return new BinaryFPPrecision(4, 8, rm);
	}

	/*
	 * Date: 7 Nov 2022-----------------------------------------------------------
	 * Time created: 20:47:01--------------------------------------------
	 */
	/**
	 * Method for constructing a {@link BinaryFPPrecision} object which is
	 * equivalent to creating a {@code BinaryFPPrecision} object using 5 bits for
	 * the exponent field and 11 bits for the significand (including the implicit
	 * bit) and a of {@link RoundingMode#HALF_UP}. This is the same as the standard
	 * half precision in IEEE754 (hence the name).
	 * 
	 * @return a {@code BinaryFPPrecision} object equivalent to IEE754's half
	 *         precision
	 * @see Precision#HALF
	 */
	public static BinaryFPPrecision IEEE754Half() {
		return IEEE754Half(rm("HALF_UP"));
	}

	/*
	 * Date: 24 Nov 2022-----------------------------------------------------------
	 * Time created: 02:38:42--------------------------------------------
	 */
	/**
	 * Method for constructing a {@link BinaryFPPrecision} object which is
	 * equivalent to creating a {@code BinaryFPPrecision} object using 5 bits for
	 * the exponent field and 11 bits for the significand (including the implicit
	 * bit) using a given {@code RoundingMode} object for rounding. This is the same
	 * as the standard half precision in IEEE754 (hence the name).
	 * 
	 * @return a {@code BinaryFPPrecision} object equivalent to IEE754's half
	 *         precision
	 * @see Precision#HALF
	 * @param rm a given rounding mode of the precision
	 */
	public static BinaryFPPrecision IEEE754Half(RoundingMode rm) {
		return new BinaryFPPrecision(5, 16, rm);
	}

	/*
	 * Date: 7 Nov 2022-----------------------------------------------------------
	 * Time created: 20:49:38--------------------------------------------
	 */
	/**
	 * Method for constructing a {@link BinaryFPPrecision} object which is
	 * equivalent to creating a {@code BinaryFPPrecision} object using 8 bits for
	 * the exponent field and 24 bits for the significand (including the implicit
	 * bit) and a of {@link RoundingMode#HALF_UP}. This is the same as the standard
	 * single (or 32 bit) precision in IEEE754 (hence the name).
	 * 
	 * @return a {@code BinaryFPPrecision} object equivalent to IEE754's single (32
	 *         bit) precision
	 * @see Precision#SINGLE
	 */
	public static BinaryFPPrecision IEEE754Single() {
		return IEEE754Single(rm("HALF_UP"));
	}

	/*
	 * Date: 24 Nov 2022-----------------------------------------------------------
	 * Time created: 02:39:32--------------------------------------------
	 */
	/**
	 * Method for constructing a {@link BinaryFPPrecision} object which is
	 * equivalent to creating a {@code BinaryFPPrecision} object using 8 bits for
	 * the exponent field and 24 bits for the significand (including the implicit
	 * bit) using a given {@code RoundingMode} object for rounding. This is the same
	 * as the standard single (or 32 bit) precision in IEEE754 (hence the name).
	 * 
	 * @return a {@code BinaryFPPrecision} object equivalent to IEE754's single (32
	 *         bit) precision
	 * @see Precision#SINGLE
	 * @param rm a given rounding mode of the precision
	 */
	public static BinaryFPPrecision IEEE754Single(RoundingMode rm) {
		return new BinaryFPPrecision(8, 32, rm);
	}

	/*
	 * Date: 7 Nov 2022-----------------------------------------------------------
	 * Time created: 20:50:55--------------------------------------------
	 */
	/**
	 * Method for constructing a {@link BinaryFPPrecision} object which is
	 * equivalent to creating a {@code BinaryFPPrecision} object using 11 bits for
	 * the exponent field and 53 bits for the significand (including the implicit
	 * bit) and a of {@link RoundingMode#HALF_UP}. This is the same as the standard
	 * double (or 64 bit) precision in IEEE754 (hence the name).
	 * 
	 * @return a {@code BinaryFPPrecision} object equivalent to IEE754's double (64
	 *         bit) precision
	 * @see Precision#DOUBLE
	 */
	public static BinaryFPPrecision IEEE754Double() {
		return IEEE754Double(rm("HALF_UP"));
	}

	/*
	 * Date: 24 Nov 2022-----------------------------------------------------------
	 * Time created: 02:40:11--------------------------------------------
	 */
	/**
	 * Method for constructing a {@link BinaryFPPrecision} object which is
	 * equivalent to creating a {@code BinaryFPPrecision} object using 11 bits for
	 * the exponent field and 53 bits for the significand (including the implicit
	 * bit) using a given {@code RoundingMode} object for rounding. This is the same
	 * as the standard double (or 64 bit) precision in IEEE754 (hence the name).
	 * 
	 * @return a {@code BinaryFPPrecision} object equivalent to IEE754's double (64
	 *         bit) precision
	 * @see Precision#DOUBLE
	 * @param rm a given rounding mode of the precision
	 */
	public static BinaryFPPrecision IEEE754Double(RoundingMode rm) {
		return new BinaryFPPrecision(11, 64, rm);
	}

	/*
	 * Date: 7 Nov 2022-----------------------------------------------------------
	 * Time created: 20:52:15--------------------------------------------
	 */
	/**
	 * Method for constructing a {@link BinaryFPPrecision} object which is
	 * equivalent to creating a {@code BinaryFPPrecision} object using 15 bits for
	 * the exponent field and 65 bits for the significand (including the implicit
	 * bit) and a of {@link RoundingMode#HALF_UP}. This is the same as the standard
	 * double extended (a.k.a long double or 80 bit extended) precision in IEEE754
	 * (hence the name).
	 * 
	 * @return a {@code BinaryFPPrecision} object equivalent to IEE754's double
	 *         extended (80 bit) precision
	 * @see Precision#EXTENDED
	 */
	public static BinaryFPPrecision IEEE754x86Extended() {
		return IEEE754x86Extended(rm("HALF_UP"));
	}

	/*
	 * Date: 24 Nov 2022-----------------------------------------------------------
	 * Time created: 02:40:28--------------------------------------------
	 */
	/**
	 * Method for constructing a {@link BinaryFPPrecision} object which is
	 * equivalent to creating a {@code BinaryFPPrecision} object using 15 bits for
	 * the exponent field and 65 bits for the significand (including the implicit
	 * bit) using a given {@code RoundingMode} object for rounding. This is the same
	 * as the standard double extended (a.k.a long double or 80 bit extended)
	 * precision in IEEE754 (hence the name).
	 * 
	 * @return a {@code BinaryFPPrecision} object equivalent to IEE754's double
	 *         extended (80 bit) precision
	 * @see Precision#EXTENDED
	 * @param rm a given rounding mode of the precision
	 */
	public static BinaryFPPrecision IEEE754x86Extended(RoundingMode rm) {
		return new BinaryFPPrecision(15, 80, rm);
	}

	/*
	 * Date: 7 Nov 2022-----------------------------------------------------------
	 * Time created: 21:08:27--------------------------------------------
	 */
	/**
	 * Method for constructing a {@link BinaryFPPrecision} object which is
	 * equivalent to creating a {@code BinaryFPPrecision} object using 15 bits for
	 * the exponent field and 113 bits for the significand (including the implicit
	 * bit) and a of {@link RoundingMode#HALF_UP}. This is the same as the standard
	 * quadruple (or 128 bit) precision in IEEE754 (hence the name).
	 * 
	 * @return a {@code BinaryFPPrecision} object equivalent to IEE754's quadruple
	 *         (128 bit) precision
	 * @see Precision#QUADRUPLE
	 */
	public static BinaryFPPrecision IEEE754Quadruple() {
		return IEEE754Quadruple(rm("HALF_UP"));
	}

	/*
	 * Date: 24 Nov 2022-----------------------------------------------------------
	 * Time created: 02:40:47--------------------------------------------
	 */
	/**
	 * Method for constructing a {@link BinaryFPPrecision} object which is
	 * equivalent to creating a {@code BinaryFPPrecision} object using 15 bits for
	 * the exponent field and 113 bits for the significand (including the implicit
	 * bit) using a given {@code RoundingMode} object for rounding. This is the same
	 * as the standard quadruple (or 128 bit) precision in IEEE754 (hence the name).
	 * 
	 * @return a {@code BinaryFPPrecision} object equivalent to IEE754's quadruple
	 *         (128 bit) precision
	 * @see Precision#QUADRUPLE
	 * @param rm a given rounding mode of the precision
	 */
	public static BinaryFPPrecision IEEE754Quadruple(RoundingMode rm) {
		return new BinaryFPPrecision(15, 128, rm);
	}

	/*
	 * Date: 7 Nov 2022-----------------------------------------------------------
	 * Time created: 21:10:57--------------------------------------------
	 */
	/**
	 * Method for constructing a {@link BinaryFPPrecision} object which is
	 * equivalent to creating a {@code BinaryFPPrecision} object using 19 bits for
	 * the exponent field and 237 bits for the significand (including the implicit
	 * bit) and a of {@link RoundingMode#HALF_UP}. This is the same as the standard
	 * octuple (or 256 bit) precision in IEEE754 (hence the name).
	 * 
	 * @return a {@code BinaryFPPrecision} object equivalent to IEE754's octuple
	 *         (256 bit) precision
	 * @see Precision#OCTUPLE
	 */
	public static BinaryFPPrecision IEEE754Octuple() {
		return IEEE754Octuple(rm("HALF_UP"));
	}

	/*
	 * Date: 24 Nov 2022-----------------------------------------------------------
	 * Time created: 02:41:13--------------------------------------------
	 */
	/**
	 * Method for constructing a {@link BinaryFPPrecision} object which is
	 * equivalent to creating a {@code BinaryFPPrecision} object using 19 bits for
	 * the exponent field and 237 bits for the significand (including the implicit
	 * bit) using a given {@code RoundingMode} object for rounding. This is the same
	 * as the standard octuple (or 256 bit) precision in IEEE754 (hence the name).
	 * 
	 * @return a {@code BinaryFPPrecision} object equivalent to IEE754's octuple
	 *         (256 bit) precision
	 * @see Precision#OCTUPLE
	 * @param rm a given rounding mode of the precision
	 */
	public static BinaryFPPrecision IEEE754Octuple(RoundingMode rm) {
		return new BinaryFPPrecision(19, 256, rm);
	}

	/*
	 * Date: 7 Nov 2022-----------------------------------------------------------
	 * Time created: 21:12:09--------------------------------------------
	 */
	/**
	 * Converts the argument, which is assumed to be a bit layout, to it's
	 * corresponding {@code BinaryFP} object using a {@code BinaryFPPrecision}
	 * object that contains 8 bits of precision (equivalent to
	 * {@link #IEEE754Bit8()}). This is analogous to using
	 * {@link Double#longBitsToDouble(long)} to create a {@code double} or using
	 * {@link Float#intBitsToFloat(int)} to create a {@code float}, in both cases,
	 * the value returned is the exact translation the input bit layout as specified
	 * by IEEE754, this may be an NaN, an Infinity, a Zero (-0.0, +0.0) or a valid
	 * floating point number.
	 * <p>
	 * Please see the {@link Precision} documentation for details on what a floating
	 * point bit layout is.
	 * 
	 * @param b a {@code byte} value that is the direct integer representation of
	 *          the bit layout of the floating point value to be returned
	 * @return a {@code BinaryFP} object which is the floating point representation
	 *         of the bit layout argument. Note that the value returned does not use
	 *         a standard IEEE754 precision but a precision equal to
	 *         {@link #IEEE754Bit8()}.
	 * @see Float#intBitsToFloat(int)
	 * @see Double#longBitsToDouble(long)
	 */
	public static BinaryFPPrecision.BinaryFP fromBits(byte b) {
		return IEEE754Bit8().fromBitLayout(i(b));
	}

	/*
	 * Date: 7 Nov 2022-----------------------------------------------------------
	 * Time created: 21:31:22--------------------------------------------
	 */
	/**
	 * Converts the argument, which is assumed to be a bit layout, to it's
	 * corresponding {@code BinaryFP} object using a {@code BinaryFPPrecision}
	 * object that contains 16 bits of precision (equivalent to
	 * {@link #IEEE754Half()}). This is analogous to using
	 * {@link Double#longBitsToDouble(long)} to create a {@code double} or using
	 * {@link Float#intBitsToFloat(int)} to create a {@code float}, in both cases,
	 * the value returned is the exact translation the input bit layout as specified
	 * by IEEE754, this may be an NaN, an Infinity, a Zero (-0.0, +0.0) or a valid
	 * floating point number.
	 * <p>
	 * Please see the {@link Precision} documentation for details on what a floating
	 * point bit layout is.
	 * 
	 * @param s a {@code short} value that is the direct integer representation of
	 *          the bit layout of the floating point value to be returned
	 * @return a {@code BinaryFP} object which is the floating point representation
	 *         of the bit layout argument. Note that the value returned uses a
	 *         standard IEEE754 precision equal to {@link #IEEE754Half()}.
	 * @see Float#intBitsToFloat(int)
	 * @see Double#longBitsToDouble(long)
	 */
	public static BinaryFPPrecision.BinaryFP fromBits(short s) {
		return IEEE754Half().fromBitLayout(i(s));
	}

	/*
	 * Date: 7 Nov 2022-----------------------------------------------------------
	 * Time created: 21:36:02--------------------------------------------
	 */
	/**
	 * Converts the argument, which is assumed to be a bit layout, to it's
	 * corresponding {@code BinaryFP} object using a {@code BinaryFPPrecision}
	 * object that contains 32 bits of precision (equivalent to
	 * {@link #IEEE754Single()}). This is the same as using
	 * {@link Float#intBitsToFloat(int)} to create a {@code float}, the value
	 * returned is the exact translation the input bit layout as specified by
	 * IEEE754, this may be an NaN, an Infinity, a Zero (-0.0, +0.0) or a valid
	 * floating point number.
	 * <p>
	 * Please see the {@link Precision} documentation for details on what a floating
	 * point bit layout is.
	 * 
	 * @param i an {@code int} value that is the direct integer representation of
	 *          the bit layout of the floating point value to be returned
	 * @return a {@code BinaryFP} object which is the floating point representation
	 *         of the bit layout argument. Note that the value returned uses a
	 *         standard IEEE754 precision equal to {@link #IEEE754Single()}.
	 * @see Float#intBitsToFloat(int)
	 * @see Double#longBitsToDouble(long)
	 */
	public static BinaryFPPrecision.BinaryFP fromBits(int i) {
		return IEEE754Single().fromBitLayout(i(i));
	}

	/*
	 * Date: 7 Nov 2022-----------------------------------------------------------
	 * Time created: 21:38:32--------------------------------------------
	 */
	/**
	 * Converts the argument, which is assumed to be a bit layout, to it's
	 * corresponding {@code BinaryFP} object using a {@code BinaryFPPrecision}
	 * object that contains 64 bits of precision (equivalent to
	 * {@link #IEEE754Double()}). This is the same as using
	 * {@link Double#longBitsToDouble(long)} to create a {@code double}, the value
	 * returned is the exact translation the input bit layout as specified by
	 * IEEE754, this may be an NaN, an Infinity, a Zero (-0.0, +0.0) or a valid
	 * floating point number.
	 * <p>
	 * Please see the {@link Precision} documentation for details on what a floating
	 * point bit layout is.
	 * 
	 * @param l a {@code long} value that is the direct integer representation of
	 *          the bit layout of the floating point value to be returned
	 * @return a {@code BinaryFP} object which is the floating point representation
	 *         of the bit layout argument. Note that the value returned uses a
	 *         standard IEEE754 precision equal to {@link #IEEE754Double()}.
	 * @see Float#intBitsToFloat(int)
	 * @see Double#longBitsToDouble(long)
	 */
	public static BinaryFPPrecision.BinaryFP fromBits(long l) {
		return IEEE754Double().fromBitLayout(i(l));
	}

	/*
	 * Date: 7 Nov 2022-----------------------------------------------------------
	 * Time created: 21:50:48--------------------------------------------
	 */
	/**
	 * Converts the argument, which is assumed to be a bit layout of 80 bits split
	 * into 64 most significant bits and 16 least significant bits, to it's
	 * corresponding {@code BinaryFP} object using a {@code BinaryFPPrecision}
	 * object that contains 80 bits of precision (equivalent to
	 * {@link #IEEE754x86Extended()}). This is analogous to using
	 * {@link Double#longBitsToDouble(long)} to create a {@code double} or using
	 * {@link Float#intBitsToFloat(int)} to create a {@code float}, in both cases,
	 * the value returned is the exact translation the input bit layout as specified
	 * by IEEE754, this may be an NaN, an Infinity, a Zero (-0.0, +0.0) or a valid
	 * floating point number.
	 * <p>
	 * Please see the {@link Precision} documentation for details on what a floating
	 * point bit layout is.
	 * 
	 * @param high a {@code long} value that is high 64 bits of the direct integer
	 *             representation of the bit layout of the floating point value to
	 *             be returned. The high 64 bits of an integer are the first 64 bits
	 *             from the left (in big-endian format) or the first 64 bits from
	 *             the right (in big-endian format).
	 * @param low  a {@code short} value that is low 16 bits of the direct integer
	 *             representation of the bit layout of the floating point value to
	 *             be returned. The low 16 bits of an integer are the last 16 bits
	 *             from the left (in big-endian format) or the last 16 bits from the
	 *             right (in big-endian format).
	 * @return a {@code BinaryFP} object which is the floating point representation
	 *         of the bit layout argument. Note that the value returned uses a
	 *         standard IEEE754 precision equal to {@link #IEEE754x86Extended()}.
	 * @see Float#intBitsToFloat(int)
	 * @see Double#longBitsToDouble(long)
	 */
	public static BinaryFPPrecision.BinaryFP fromBits(long high, short low) {
		return IEEE754x86Extended().fromBitLayout(i(high).shiftLeft(Short.SIZE).or(i(Short.toString(low), 10)));
	}

	/*
	 * Date: 7 Nov 2022-----------------------------------------------------------
	 * Time created: 21:50:48--------------------------------------------
	 */
	/**
	 * Converts the argument, which is assumed to be a bit layout of 128 bits split
	 * into 64 most significant bits and 64 least significant bits, to it's
	 * corresponding {@code BinaryFP} object using a {@code BinaryFPPrecision}
	 * object that contains 128 bits of precision (equivalent to
	 * {@link #IEEE754Quadruple()}). This is analogous to using
	 * {@link Double#longBitsToDouble(long)} to create a {@code double} or using
	 * {@link Float#intBitsToFloat(int)} to create a {@code float}, in both cases,
	 * the value returned is the exact translation the input bit layout as specified
	 * by IEEE754, this may be an NaN, an Infinity, a Zero (-0.0, +0.0) or a valid
	 * floating point number.
	 * <p>
	 * Please see the {@link Precision} documentation for details on what a floating
	 * point bit layout is.
	 * 
	 * @param high a {@code long} value that is high 64 bits of the direct integer
	 *             representation of the bit layout of the floating point value to
	 *             be returned. The high 64 bits of an integer are the first 64 bits
	 *             from the left (in big-endian format) or the first 64 bits from
	 *             the right (in big-endian format).
	 * @param low  a {@code long} value that is low 64 bits of the direct integer
	 *             representation of the bit layout of the floating point value to
	 *             be returned. The low 64 bits of an integer are the last 64 bits
	 *             from the left (in big-endian format) or the last 64 bits from the
	 *             right (in big-endian format).
	 * @return a {@code BinaryFP} object which is the floating point representation
	 *         of the bit layout argument. Note that the value returned uses a
	 *         standard IEEE754 precision equal to {@link #IEEE754Quadruple()}.
	 * @see Float#intBitsToFloat(int)
	 * @see Double#longBitsToDouble(long)
	 */
	public static BinaryFPPrecision.BinaryFP fromBits(long high, long low) {
		return IEEE754Quadruple().fromBitLayout(i(high).shiftLeft(Long.SIZE).or(i(low)));
	}

	/*
	 * Date: 7 Nov 2022-----------------------------------------------------------
	 * Time created: 22:33:01--------------------------------------------
	 */
	/**
	 * Converts the argument, which is assumed to be a bit layout of 256 bits split
	 * into 64 bits pairs in big-endian order, to it's corresponding
	 * {@code BinaryFP} object using a {@code BinaryFPPrecision} object that
	 * contains 256 bits of precision (equivalent to {@link #IEEE754Octuple()}).
	 * This is analogous to using {@link Double#longBitsToDouble(long)} to create a
	 * {@code double} or using {@link Float#intBitsToFloat(int)} to create a
	 * {@code float}, in both cases, the value returned is the exact translation the
	 * input bit layout as specified by IEEE754, this may be an NaN, an Infinity, a
	 * Zero (-0.0, +0.0) or a valid floating point number.
	 * <p>
	 * Please see the {@link Precision} documentation for details on what a floating
	 * point bit layout is.
	 * 
	 * @param layout a {@link Tuple.Quadruple} that contains 64 bits in each slot
	 *               grouped in big-endian order
	 * @return a {@code BinaryFP} object which is the floating point representation
	 *         of the bit layout argument. Note that the value returned uses a
	 *         standard IEEE754 precision equal to {@link #IEEE754Octuple()}.
	 * @see Float#intBitsToFloat(int)
	 * @see Double#longBitsToDouble(long)
	 */
	public static BinaryFPPrecision.BinaryFP fromBits(Quadruple<Long, Long, Long, Long> layout) {
		return IEEE754Octuple().fromBitLayout(i(layout.get4th()).or(i(layout.get3rd()).shiftLeft(Long.SIZE)
				.or(i(layout.get2nd()).shiftLeft(Long.SIZE * 2).or(i(layout.get()).shiftLeft(Long.SIZE * 3)))));
	}

	/*
	 * Date: 7 Nov 2022-----------------------------------------------------------
	 * Time created: 22:11:45--------------------------------------------
	 */
	/**
	 * Creates a {@code BinaryFP} representing 32 bit floating point number from a
	 * {@code float}
	 * 
	 * @param f a {@code float} value, this includes {@link Float#isNaN()},
	 *          {@link Float#POSITIVE_INFINITY}, {@link Float#NEGATIVE_INFINITY},
	 *          -0.0f
	 * @return a {@code BinaryFP} representation of the {@code float} argument using
	 *         a {@linkplain BinaryFPPrecision precision object} that uses 8 bit
	 *         exponent field and 24 bit significand to interpret values (similar to
	 *         a {@code float}).
	 */
	public static BinaryFPPrecision.BinaryFP createFP(float f) {
		return IEEE754Single().createFP(Float.toString(f));
	}

	/*
	 * Date: 7 Nov 2022-----------------------------------------------------------
	 * Time created: 22:19:38--------------------------------------------
	 */
	/**
	 * Creates a {@code BinaryFP} representing 64 bit floating point number from a
	 * {@code float}
	 * 
	 * @param d a {@code double} value, this includes {@link Double#isNaN()},
	 *          {@link Double#POSITIVE_INFINITY}, {@link Double#NEGATIVE_INFINITY},
	 *          -0.0d
	 * @return a {@code BinaryFP} representation of the {@code double} argument
	 *         using a {@linkplain BinaryFPPrecision precision object} that uses 11
	 *         bit exponent field and 53 bit significand to interpret values
	 *         (similar to a {@code double}).
	 */
	public static BinaryFPPrecision.BinaryFP createFP(double d) {
		return IEEE754Double().createFP(Double.toString(d));
	}

	/*
	 * Date: 9 Dec 2022-----------------------------------------------------------
	 * Time created: 14:45:00--------------------------------------------
	 */
	/**
	 * Performs a radix-based right shift on the given {@code BigInteger} and
	 * returns the shifted value as the result. The difference between a radix-based
	 * shift and {@link BigInteger#shiftRight(int)} is that the latter is a binary
	 * right shift while the former is a shift based on the given radix. For
	 * example:
	 * 
	 * <pre>
	 * <code>
	 * BigInteger n = new BigInteger("d12aef", 16);//hex number
	 * System.out.println(n.shiftRight(3).toString(16));//binary shift, prints 1a255d
	 * System.out.println(FloatAid.shiftRight(n, 3, 16).toString(16));//binary shift, prints d12
	 * </code>
	 * </pre>
	 * 
	 * @param n      the {@code BigInteger} as the value to be shifted
	 * @param places the number of places to the right that this will be shifted
	 * @param radix  the radix in which the sift is done
	 * @return returns the {@code BigInteger} argument right shifted to the given
	 *         first {@code int} argument in the radix of the second {@code int}
	 *         argument. This may cause a zero value to be returned, if the digits
	 *         in the specified radix is less than the given <i>right-shift</i>
	 *         places.
	 */
	public static BigInteger shiftRight(BigInteger n, int places, int radix) {
		if (places < 0)
			return shiftLeft(n, -places, radix);
		String s = n.toString(radix);
		if (places >= s.length())
			return BigInteger.ZERO;
		s = s.substring(0, s.length() - places);
		return s.equals("-") || s.equals("+") ? BigInteger.ZERO : i(s, radix);
	}

	/*
	 * Date: 9 Dec 2022-----------------------------------------------------------
	 * Time created: 14:53:13--------------------------------------------
	 */
	/**
	 * Performs a radix-based left shift on the given {@code BigInteger} and returns
	 * the shifted value as the result. The difference between a radix-based shift
	 * and {@link BigInteger#shiftLeft(int)} is that the latter is a binary left
	 * shift while the former is a shift based on the given radix. For example:
	 * 
	 * <pre>
	 * <code>
	 * BigInteger n = new BigInteger("d12aef", 16);//hex number
	 * System.out.println(n.shiftLeft(3).toString(16));//binary shift, prints 6895778
	 * System.out.println(FloatAid.shiftLeft(n, 3, 16).toString(16));//binary shift, prints d12aef000
	 * </code>
	 * </pre>
	 * 
	 * This will return zero if (and only if) the the {@code BigInteger} argument is
	 * 0.
	 * 
	 * @param n      the {@code BigInteger} as the value to be shifted
	 * @param places the number of places to the left that this will be shifted
	 * @param radix  the radix in which the sift is done
	 * @return returns the {@code BigInteger} argument left shifted to the given
	 *         first {@code int} argument in the radix of the second {@code int}
	 *         argument.
	 */
	public static BigInteger shiftLeft(BigInteger n, int places, int radix) {
		if (places < 0)
			return shiftRight(n, -places, radix);
		return n.multiply(FloatAid.powerOfRadix(radix, places));
	}

	/*
	 * Date: 7 Nov 2022-----------------------------------------------------------
	 * Time created: 22:42:51--------------------------------------------
	 */
	/**
	 * Switches off the first <code><i>n</i></code> bits from the left (most
	 * significant bits a.k.a MSB) in the given {@code BigInteger} by doing:
	 * 
	 * <pre>
	 * <code>
	 * mask = ... // create a BigInteger whose bit length == i.bitLength() with all the bits to be remove set to 1
	 * return (mask | i) ^ mask;
	 * </code>
	 * </pre>
	 * 
	 * @param i the {@code BigInteger} value whose most significant bits is to be
	 *          cleared.
	 * @param n the number of high bits to be cleared.
	 * @return {@code i} with the high <code><i>n</i></code> bits cleared. Returns
	 *         <code>i</code> if {@code n == 0} and returns {@code 0} if
	 *         {@code i == 0}
	 * @throws ArithmeticException if {@code i.bitLength() < n}
	 */
	public static BigInteger clearMSB(BigInteger i, int n) throws ArithmeticException {
		if (i.bitLength() < n)
			throw new ArithmeticException("bit length is lesser than is required");
		BigInteger mask = getAllOnes(n).shiftLeft(i.bitLength() - n);
		return mask.or(i).xor(mask);
	}

	/**
	 * Gets the first <span style="font-style:italic">n</span> bits (or more technically, the
	 * the first <span style="font-style:italic">n</span> high order or most significant bits)
	 * in the given {@code BigInteger} and returns it as a {@code BigInteger}.
	 * 
	 * @param i a {@code BigInteger}
	 * @param n the number of most significant bits to be extracted from the {@code BigInteger}
	 * value
	 * @throws ArithmeticException if {@code i.bitLength() < n}
	 */
	public static BigInteger getHigh(BigInteger i, int n) throws ArithmeticException {
		if (i.bitLength() < n)
			throw new ArithmeticException("bit length is lesser than is required");
		return i.shiftRight(i.bitLength() - n);
		// BigInteger mask = getAllOnes(n).shiftLeft(i.bitLength() - n);
		// return mask.or(i).xor(mask);
	}

	/**
	 * Gets the last <span style="font-style:italic">n</span> bits (or more technically, the
	 * the first <span style="font-style:italic">n</span> low order or least significant bits)
	 * in the given {@code BigInteger} and returns it as a {@code BigInteger}.
	 * 
	 * @param i a {@code BigInteger}
	 * @param n the number of least significant bits to be extracted from the {@code BigInteger}
	 * value
	 * @throws ArithmeticException if {@code i.bitLength() < n}
	 */
	public static BigInteger getLow(BigInteger i, int n) throws ArithmeticException {
		if (i.bitLength() < n)
			throw new ArithmeticException("bit length is lesser than is required");
		return clearMSB(i, i.bitLength() - n);
	}

	/*
	 * Date: 7 Nov 2022-----------------------------------------------------------
	 * Time created: 22:56:36--------------------------------------------
	 */
	/**
	 * Converts {@code numOfDigits} which is assumed to be in {@code fromRadix} to
	 * it's equivalent truncated representation in {@code toRadix}.
	 * <p>
	 * This is useful for telling how many mantissa (or significand) a certain
	 * number converted from one radix to the another will have, hence the name of
	 * the method. For example, a binary fraction
	 * {@code 1.1011 1101 0000 1111 1111 0010} will have exactly 7 digits of
	 * mantissa after converted to decimal by doing:
	 * 
	 * <pre>
	 * <code> int digits = calculateSignificandDigits(2, 24, 10);// 7 </code>
	 * </pre>
	 * 
	 * we can also do:
	 * 
	 * <pre>
	 * <code>
	 * BigInteger b = new BigInteger("101111010000111111110010", 2);
	 * int digits = calculateSignificandDigits(2, b.bitLength(), 10);// 7
	 * </code>
	 * </pre>
	 * 
	 * to know how many digits is in the decimal representation of the
	 * {@code BigInteger}.
	 * 
	 * @param fromRadix   the radix of the {@code numOfDigits}
	 * @param numOfDigits the number of significant digits
	 * @param toRadix     the radix to which to convert {@code numOfDigits} to
	 * @return <code> &#x2308;numOfDigits &times; log<sub>toRadix</sub>(fromRadix)&#x2309;</code>
	 * @throws ArithmeticException if either radixes is less than zero
	 */
	public static int calculateSignificandDigits(int fromRadix, int numOfDigits, int toRadix)
			throws ArithmeticException {
		return (int) Math.ceil(numOfDigits * log(toRadix, fromRadix));
	}

	/*
	 * Date: 11 Nov 2022-----------------------------------------------------------
	 * Time created: 07:07:17--------------------------------------------
	 */
	/**
	 * Converts {@code numOfDigits} which is assumed to be in {@code fromRadix} to
	 * it's equivalent truncated representation in {@code toRadix}.
	 * <p>
	 * This is useful for telling how many mantissa (or significand) a certain
	 * number converted from one radix to the another will have, hence the name of
	 * the method. For example, a binary fraction
	 * {@code 1.1011 1101 0000 1111 1111 0010} will have exactly 7 digits of
	 * mantissa after converted to decimal by doing:
	 * 
	 * <pre>
	 * <code> BigInteger digits = calculateSignificandDigits(2, i(24), 10);// 7 </code>
	 * </pre>
	 * 
	 * we can also do:
	 * 
	 * <pre>
	 * <code>
	 * BigInteger b = new BigInteger("101111010000111111110010", 2);
	 * int digits = calculateSignificandDigits(2, b.bitLength(), 10);// 7
	 * </code>
	 * </pre>
	 * 
	 * to know how many digits is in the decimal representation of the
	 * {@code BigInteger}.
	 * 
	 * @param fromRadix   the radix of the {@code numOfDigits}
	 * @param numOfDigits the number of significant digits
	 * @param toRadix     the radix to which to convert {@code numOfDigits} to
	 * @return <code> trunc(numOfDigits &times; log<sub>toRadix</sub>(fromRadix));</code>
	 * @throws ArithmeticException if either radixes is less than zero
	 */
	static BigInteger calculateSignificandDigits(int fromRadix, BigInteger numOfDigits, int toRadix)
			throws ArithmeticException {
//		return (int) Math.ceil(numOfDigits * FastMath.log(toRadix, fromRadix));
		return d(numOfDigits).multiply(log(d(toRadix), d(fromRadix), mc(Integer.MAX_VALUE)))
				.toBigInteger();
	}

	/*
	 * Date: 7 Nov 2022-----------------------------------------------------------
	 * Time created: 23:15:00--------------------------------------------
	 */
	/**
	 * Checks if the first argument is null then return it if it isn't or else
	 * returns the second argument
	 * 
	 * @param <T>         the type of object to tested and returned
	 * @param s           the current value
	 * @param replacement the default value
	 * @return {@code replacement} if s is null otherwise returns s
	 */
	private static <T> T ensureNotNull(T t, T replacement) {
		return t == null ? replacement : t;
	}

	/*
	 * Date: 7 Nov 2022-----------------------------------------------------------
	 * Time created: 23:19:11--------------------------------------------
	 */
	/**
	 * Calculates and returns an unsigned {@code BigInteger} whose bit length is exactly
	 * {@code numOfBits} with all bits set to 1. Will return -1 if the argument is
	 * negative and 0 if the argument is 0.
	 * 
	 * @param numOfBits the bit length of the returned value
	 * @return an unsigned {@code BigInteger} that has a bit length and bit count is specified
	 *         by the argument
	 */
	public static BigInteger getAllOnes(int numOfBits) {
		return i(1).shiftLeft(numOfBits).subtract(i(1));
	}

	/*
	 * Date: 7 Nov 2022-----------------------------------------------------------
	 * Time created: 23:22:47--------------------------------------------
	 */
	/**
	 * Calculates and returns a {@code BigInteger} whose bit count is 1 and bit
	 * length is the argument + 1 i.e value where {@code 1 << numOfTrailingZeros}.
	 * Will return 0 if the argument is negative and will return 1 if the argument
	 * is 0.
	 * 
	 * @param numOfTrailingZeros the number of off bits (zeros) in the returned
	 *                           value
	 * @return {@code 1 << numOfTrailingZeros}
	 */
	public static BigInteger getTrailingZeros(int numOfTrailingZeros) {
		return i(1).shiftLeft(numOfTrailingZeros);
	}

	/*
	 * Date: 7 Nov 2022-----------------------------------------------------------
	 * Time created: 23:31:16--------------------------------------------
	 */
	/**
	 * Computes <code>radix<sup>exponent</sup></code> and returns the result as a
	 * {@code BigInteger}.
	 * 
	 * @param radix    the base of the exponent
	 * @param exponent the value by which radix is to be raised
	 * @return <code>radix<sup>exponent</sup></code>
	 * @throws ArithmeticException      if {@code exponent} is negative. (This would
	 *                                  cause the operation to yield a non-integer
	 *                                  value.)
	 * @throws IllegalArgumentException if radix < 2
	 */
	public static BigInteger powerOfRadix(int radix, int exponent)
			throws ArithmeticException, IllegalArgumentException {
		if (radix < Character.MIN_RADIX)
			throw new IllegalArgumentException("radix is out of range");
		if (exponent == 0)
			return i(1);
		else if (radix == 2)
			return i(1).shiftLeft(exponent);
		return i(radix).pow(exponent);
	}

	/*
	 * Date: 7 Nov 2022-----------------------------------------------------------
	 * Time created: 23:51:15--------------------------------------------
	 */
	/**
	 * Decomposes the {@code BigDecimal} argument to a 4-length {@code BigInteger}
	 * array using a fast version of the basic secondary school algorithm for
	 * converting decimals to other bases. The returned array contains the
	 * following:
	 * <ol>
	 * <li>The sign of the {@code BigDecimal} argument represented by 0 for
	 * non-negative arguments and 1 for negative arguments.</li>
	 * <li>The integer portion of the argument.</li>
	 * <li>The mantissa, without any leading zero (but may have lots of trailing
	 * ones), in a form that exactly represents the mantissa of the value without
	 * any leading zero. This may not have the same amount of digits as is specified
	 * in {@code mantissaDigits} argument due to leading zeros making up for the
	 * rest. Leading zeros may also cause this value to be zero even when
	 * {@code mantissaDigits} is greater than 0.</li>
	 * <li>The number of mantissa digits in the third index of the array, this
	 * includes leading and trailing zeros. This value, when not a zero, is always
	 * negative. In reality, if the mantissa is not zero this value is
	 * {@code -mantissaDigits} else it is {@code 0}.</li>
	 * </ol>
	 * The following code describes the process:
	 * 
	 * <pre>
	 * <code>
	 *	BigDecimal n = new BigDecimal("-123567890.0123456789");
	 *	int fracDig = Utility.numOfFractionalDigits(n);//store the number of digits to the left of the decimal point
	 *	//the value below will return 0 for small mantissaDigits arguments!
	 *	int binDig = FloatAid.calculateSignificandDigits(10, fracDig, 2);//convert decimal fractional digits to number of binary fractional digits
	 *	BigInteger[] b = FloatAid.fromDecimal(n, 2, binDig);
	 *	String lz = Utility.string('0', b[3].abs().intValue() - b[2].bitLength());//leading zeros
	 *	System.out.println(String.format("%1$s%2$s.%3$s%4$s", b[0].compareTo(BigInteger.valueOf(0L)) > 0 ? "-" : "", b[1].toString(2), lz, b[2].toString(2)));
	 * </code>
	 * </pre>
	 * 
	 * prints:
	 * 
	 * <pre>
	 * <code>-111010111010111111100010010.000000110010100100010110000111110001</code>
	 * </pre>
	 * 
	 * @param n              the value from which the radix conversion is to be made
	 * @param radix          the radix of the final result
	 * @param mantissaDigits the number of digits needed in the mantissa portion.
	 *                       This includes leading and trailing zeros
	 * @return a 4-length {@code BigInteger} array containing the sign, integer,
	 *         mantissa and exponent
	 * @throws ArithmeticException      if {@code mantissaDigit < 0}
	 * @throws IllegalArgumentException if
	 *                                  <code>radix < {@link Character#MIN_RADIX}</code>
	 * 
	 * @implNote While this method supports radixes bigger than
	 *           {@link Character#MAX_RADIX}, {@code BigInteger} cannot represent
	 *           strings of radix higher than {@link Character#MAX_RADIX}. As such,
	 *           the point of using a radix bigger than {@link Character#MAX_RADIX}
	 *           is moot unless the user has an alternative way to represent the
	 *           string in the given radix of their choice, e.g a static method than
	 *           convert a {@code BigInteger} to it's corresponding string in a
	 *           radix above {@link Character#MAX_RADIX}.
	 * 
	 * @apiNote In future releases, I aim to include argument for rounding at a
	 *          specified point in the mantissa
	 */
	public static BigInteger[] fromDecimal(BigDecimal n, int radix, int mantissaDigits/* , boolean binaryRound */)
			throws ArithmeticException, IllegalArgumentException {
		if (radix < Character.MIN_RADIX)
			throw new IllegalArgumentException("radix is out of range");
		final BigInteger[] rv = new BigInteger[4];// sign, integer, mantissa, exponent
		rv[0] = n.signum() < 0 ? i(1) : i(0);
		n = n.abs();
		rv[1] = n.toBigInteger();
		rv[2] = i(0);
		n = n.subtract(d(rv[1]));
		if (n.signum() != 0) {
			rv[2] = n.multiply(d(powerOfRadix(radix, mantissaDigits))).toBigInteger();
			rv[3] = i(-mantissaDigits);
		} else {
			rv[3] = i(0);
		}
		return rv;
	}

	/*
	 * Date: 8 Nov 2022-----------------------------------------------------------
	 * Time created: 19:31:54--------------------------------------------
	 */
	/**
	 * Computes the decimal equivalent of the non-negative fraction represented in a
	 * certain radix and returns the result as a {@code BigDecimal} using the
	 * algorithm:<br>
	 * let {@code nonZeroMantissaSignificand} be x, <br>
	 * let {@code radix} be r, <br>
	 * let {@code numOfLeadingZeros} be z, <br>
	 * let the number of digits in {@code nonZeroMantissaSignificand} be n &#x2234;
	 * 
	 * <pre>
	 * <code>x &div; r<sup>z + n</sup></code>
	 * </pre>
	 * 
	 * using the provided {@code MathContext} to round the result. This algorithm is
	 * faster and more efficient than the brute force iterative division taught in
	 * secondary school (on which the
	 * {@link Utility#toDecimal(String, Radix, Precision)} is based)
	 * <p>
	 * For example to convert <code>0.00abed<sub>16</sub></code> do:
	 * 
	 * <pre>
	 * <code>
	 * String s = "0.00abed";
	 * String mantissa = s.substring(2);// retrieves everything after the radix point
	 * BigInteger nonZeroMantissaSignificand = new BigInteger(mantissa, 16);//leading zeros will not reflect
	 * int numOfLeadingZeros = mantissa.length() - nonZeroMantissaSignificand.toString(16).length();
	 * &sol;&ast;
	 *  &ast; assign a suitable value that can cover the mantissa to prevent truncation or trailing zeros.
	 *  &ast;&sol;
	 * MathContext mc = new MathContext(FloatAid.calculateSignificandDigits(16, mantissa.length(), 10));
	 * BigDecimal val = FloatAid.toDecimal(nonZeroMantissaSignificand, numOfLeadingZeros, 16, mc);
	 * System.out.println(val); // prints 0.0026233792
	 * </code>
	 * </pre>
	 * 
	 * Note that this method is intended for non-negative values in the range (0,
	 * 1), the value returned is always within this range.
	 * 
	 * @param nonZeroMantissaSignificand the non-negative significant digits of the
	 *                                   mantissa portion of a value. The
	 *                                   significant digits of a mantissa are all
	 *                                   the digits after the radix point beginning
	 *                                   with the first non-zero digit. e.g in
	 *                                   0.02345 the significant mantissa is 2345
	 * @param numOfLeadingZeros          the number of leading zero(s) after the
	 *                                   decimal point. This does not include
	 *                                   zero(s) inside the significand (of the
	 *                                   mantissa) or trailing zero(s).
	 * @param radix                      the base that the argument is represented
	 *                                   in
	 * @param mc                         a rounding context
	 * @return a {@code BigDecimal} in which is equivalent to the argument.
	 * @throws ArithmeticException      if
	 *                                  <code>nonZeroMantissaSignificand &lt; 0</code>
	 *                                  or if <code>numOfLeadingZeros &lt; 0</code>
	 * @throws IllegalArgumentException if radix &lt; 2
	 * 
	 * @implNote While this method supports radixes bigger than
	 *           {@link Character#MAX_RADIX}, {@code BigInteger} cannot represent
	 *           strings of radix higher than {@link Character#MAX_RADIX}. As such,
	 *           the point of using a radix bigger than {@link Character#MAX_RADIX}
	 *           is moot unless the user has an alternative way to represent the
	 *           string in the given radix of their choice, e.g a static method than
	 *           convert a {@code BigInteger} to it's corresponding string in a
	 *           radix above {@link Character#MAX_RADIX}.
	 */
	public static BigDecimal toDecimal(BigInteger nonZeroMantissaSignificand, int numOfLeadingZeros, int radix,
			MathContext mc) throws ArithmeticException, IllegalArgumentException {
		if (nonZeroMantissaSignificand.signum() < 0)
			throw new ArithmeticException("mantissa is negative");
		else if (numOfLeadingZeros < 0)
			throw new ArithmeticException("numOfLeadingZeros is negative");
		int length = Math.max(1, calculateSignificandDigits(2, nonZeroMantissaSignificand.bitLength(), radix));
		return d(nonZeroMantissaSignificand).divide(d(powerOfRadix(radix, numOfLeadingZeros + length)), mc);
	}

	/*
	 * Date: 8 Nov 2022-----------------------------------------------------------
	 * Time created: 20:38:02--------------------------------------------
	 */
	/**
	 * Does the same work as {@link #toDecimal(BigInteger, int, int, MathContext)}
	 * but with a provided non-negative integer.
	 * 
	 * @param integer                    the non-negative integer part of the number
	 * @param nonZeroMantissaSignificand the non-negative significant digits of the
	 *                                   mantissa portion of a value. The
	 *                                   significant digits of a mantissa are all
	 *                                   the digits after the radix point beginning
	 *                                   with the first non-zero digit. e.g in
	 *                                   1.02345 the significant mantissa is 2345
	 * @param numOfLeadingZeros          the number of leading zero(s) after the
	 *                                   decimal point. This does not include
	 *                                   zero(s) inside the significand (of the
	 *                                   mantissa) or trailing zero(s).
	 * @param radix                      the base that the argument is represented
	 *                                   in
	 * @param mc                         a rounding context
	 * @return a {@code BigDecimal} in which is equivalent to the argument.
	 * @throws ArithmeticException if {@code integer} is negative
	 * @see #toDecimal(BigInteger, int, int, MathContext)
	 */
	public static BigDecimal toDecimal(BigInteger integer, BigInteger nonZeroMantissaSignificand, int numOfLeadingZeros,
			int radix, MathContext mc) throws ArithmeticException {
		if (integer.signum() < 0)
			throw new ArithmeticException("integer is negative");
		return toDecimal(nonZeroMantissaSignificand, numOfLeadingZeros, radix, mc).add(d(integer), mc);
	}

	/*
	 * Date: 8 Nov 2022-----------------------------------------------------------
	 * Time created: 20:43:26--------------------------------------------
	 */
	/**
	 * Does the same job as
	 * {@link #toDecimal(BigInteger, BigInteger, int, int, MathContext)} but
	 * supports negative value via the {@code signum} argument
	 * 
	 * @param signum                     the signum of the entire number
	 * @param integer                    the non-negative integer part of the number
	 * @param nonZeroMantissaSignificand the non-negative significant digits of the
	 *                                   mantissa portion of a value. The
	 *                                   significant digits of a mantissa are all
	 *                                   the digits after the radix point beginning
	 *                                   with the first non-zero digit. e.g in
	 *                                   1.02345 the significant mantissa is 2345
	 * @param numOfLeadingZeros          the number of leading zero(s) after the
	 *                                   decimal point. This does not include
	 *                                   zero(s) inside the significand (of the
	 *                                   mantissa) or trailing zero(s).
	 * @param radix                      the base that the argument is represented
	 *                                   in
	 * @param mc                         a rounding context
	 * @return a {@code BigDecimal} in which is equivalent to the argument.
	 * @see #toDecimal(BigInteger, BigInteger, int, int, MathContext)
	 * @see #toDecimal(BigInteger, int, int, MathContext)
	 */
	public static BigDecimal toDecimal(int signum, BigInteger integer, BigInteger nonZeroMantissaSignificand,
			int numOfLeadingZeros, int radix, MathContext mc) {
		return toDecimal(integer.abs(), nonZeroMantissaSignificand, numOfLeadingZeros, radix, mc)
				.multiply(d(signum < 0 ? signum : 1));
	}

	/*
	 * Date: 9 Nov 2022-----------------------------------------------------------
	 * Time created: 12:19:36-------------------------------------------- the e
	 * exponent character cannot be discerned when radix is higher than 15 and the p
	 * exponent character (for normalised floating point) cannot be discerned when
	 * radix is higher than 25. Both these limitations can be avoided if the
	 * exponent is signed with either a + or a - Also, if the number is going to be
	 * minuscule (such as 0.00hxw93270udwfnnep-r) it should not have an exponent
	 * because bigDecimal's pow() method will throw an exception if the result will
	 * return a very small value. if there is an 'e' or a 'p' exponent, it is
	 * expected to be in the same radix as the number part
	 */
	/**
	 * Parses the string argument, which is expected to be in the same radix as the
	 * {@code int} argument, into a {@code BigDecimal} using the provided rounding
	 * context to truncate or add trailing zeros to the result.
	 * <h2>Exponents</h2>
	 * <p>
	 * This method accepts fractional arguments (such as
	 * <code>2face.ace<sub>16</sub></code>) and even those with exponents
	 * (<code>111101.001e100<sub>2</sub></code>,
	 * <code>11b.00ffce4p-a<sub>16</sub></code>), values can be signed (negative).
	 * Only 2 exponent characters are allowed i.e 'e' and 'p'. Both characters are
	 * parsed as case insensitive i.e whether the upper case variant or the lower
	 * case variant is used it will be parsed the same. However, exponents should
	 * always be signed as they may be parsed as digits if unsigned (with either '+'
	 * or '-') and the exponent character is a valid digit in that radix - for
	 * example <code>3b8e4<sub>16</sub></code> which may be intended as
	 * <code>3b8e+4<sub>16</sub></code> be cause the 'e' character in the value is a
	 * valid hex digit. Also, exponents must have their respective exponent be in
	 * the same radix as the value or else a {@code NumberFormatException} will be
	 * thrown when parsing the exponent or the value may not be considered as a
	 * valid number.
	 * <h3>The 'p' exponent character</h3>
	 * <p>
	 * If the exponent is the normalised binary floating point (use the 'p'
	 * character), then the significand (integer and mantissa) is normalised to the
	 * form <code>1.xxxxxx</code> and the exponent is incremented or decremented
	 * (depending on the position of the radix point). Therefore one can deduce that
	 * <code>8a.e5p-b != 8a.e5 &times 2<sup>-b</sup></code>, because {@code 8a.e5}
	 * must be converted to binary fraction, then the radix point must be normalised
	 * (binary scientific notation), the exponent must also be adjusted, the
	 * normalised binary fraction (without the normalised exponent) can now be
	 * converted to the specified radix theb multiplied by 2^normalised exponent.
	 * Any exponent above <code>abs({@link Integer#MAX_VALUE})</code> may cause
	 * undefined behaviour.
	 * <h3>The 'e' exponent character</h3>
	 * <p>
	 * This is the parsed as [sign][integer].[mantissa]&times;[radix]<sup>[exponent
	 * sign][exponent magnitude]</sup> <br>
	 * <br>
	 * <br>
	 * <p>
	 * Negative zero is not supported, neither are NaNs and Infinities.
	 * 
	 * @param n     a number in string format that may or may not have an exponent
	 *              (e or p)
	 * @param radix the radix that the string argument is presented in.
	 *              <p>
	 *              Note that in some radixes the exponent characters 'e' and 'p'
	 *              (the latter for radixes greater than 14 and the former for
	 *              radixes greater than 25) may be parsed as actual digits unless a
	 *              sign is appended to them. For example, the value
	 *              <code>0.00hxw93270udwfnnepr<sub>36</sub></code> will be parsed
	 *              as having no exponent value even though the second and third
	 *              characters (from the right) are valid exponent characters. If an
	 *              exponent was intended, it may be re-written as in one of the
	 *              following ways:
	 * 
	 *              <pre>
	 *              <code>
	 *              0.00hxw93270udwfnne+pr
	 *              0.00hxw93270udwfnne-pr
	 *              0.00hxw93270udwfnnep+r//for normalised binary exponent values
	 *              0.00hxw93270udwfnnep-r//for normalised binary exponent values
	 *              </code>
	 *              </pre>
	 * 
	 *              The appended sign distinguishes it from the prior format and
	 *              changes the value to be returned
	 * @param mc    a rounding context if truncation or trailing zero(s) is required
	 *              else can be left as null. It is advised that null should not be
	 *              used for numbers with little significant digits (especially when
	 *              converting from a radix higher than 10) because it can cause a
	 *              precision {@link MathContext} to be used
	 * @return a {@code BigDecimal} in which is equivalent to the argument after
	 *         successfully parsing it.
	 * @throws NumberFormatException if the string argument cannot be parsed into a
	 *                               number using the given radix or if radix is
	 *                               outside of the range
	 *                               ({@link Character#MIN_RADIX},
	 *                               {@link Character#MAX_RADIX})
	 * @implNote This algorithm is faster and more efficient that
	 *           {@link Utility#toDecimal(String, Radix, Precision)}
	 */
	public static BigDecimal toDecimal(String n, int radix, MathContext mc) throws NumberFormatException {
		if (radix == 10 && !n.contains("p"))
			return d(n, mc);
		// decompose the number to it's sign, integer, point, mantissa, exponent char,
		// exponent sign ane exponent
		String[] c = getComponents(n, radix);
		if (isNumber(c)) {// only execute this block if this is a valid number
			boolean isNormalised = isNormalised(c);// check for binary exponents
			// save the integer
			BigInteger integer = i(String.format("%s", c[1]), radix);
			// save the mantissa without it's sign. leading zeroes are lost though
			BigInteger mantissa = i(ensureNotNull(c[3], "0"), radix);
			// check for integer numbers
			boolean isInteger = mantissa.signum() == 0;
			// if this is a 0 return without stress because going any further is a waste of
			// computational power and may cause problems
			if (isInteger && integer.signum() == 0)
				return d(0);
			// save the exponent
			String exponent = String.format("%1$s%2$s", ensureNotNull(c[5], ""), ensureNotNull(c[6], "0"));
			// assign a valid MathContext. if the argument is null then compute the number
			// of digits needed for a direct decimal equivalent
			mc = (mc != null ? mc
					: mc((calculateSignificandDigits(radix, ensureNotNull(c[3], "0").length(), 10)
							+ calculateSignificandDigits(radix, c[1].length(), 10)), rm("HALF_EVEN")));
			if (isInteger) {// Only execute this block if the input is integer
				if (isNormalised) {// Only execute this block if the input has binary exponent
					// calculate the current position of the radix point to the normalised position
					// which is before the first significant bit (i.e the implicit bit)
					final int shift = integer.bitLength() - 1;
					// add the distance the point has moved to the exponent to normalise the
					// exponent
					final int exp = Integer.parseInt(exponent, radix) + (integer.signum() == 0 ? 0 : shift);
					// clear the first significant bit (i.e the implicit bit)
					mantissa = clearMSB(integer, 1).abs();
					// save the number of zeroes in the normalised mantissa eg 1.001111001 after
					// calling the previous line causes the 2 zeros after the point to be eradicated
					// leaving a truncated value
					int lz = (shift) - (mantissa.signum() == 0 ? 0 : mantissa.bitLength());// leading zeros
					// save the implicit bit
					integer = i(1);
					BigDecimal val = mantissa.signum() != 0 ? toDecimal(c[0].equals("-") ? -1 : 1, integer, mantissa,
							lz, 2, mc(mc.getPrecision() + 10)) : d(integer);
					return val.multiply(d(2).pow(exp, mc(mc.getPrecision() + 20)), mc);
				} else if (radix != 10 && c[4] != null && c[4].equalsIgnoreCase("e")) {
					return d(integer.multiply(powerOfRadix(radix, Integer.parseInt(exponent, radix))));
				}
				return d(integer.multiply(i(c[0].equals("-") ? -1 : 1)))
						.scaleByPowerOfTen(Integer.parseInt(exponent, radix));
			} else if (isNormalised) {
				if (radix == 10 && integer.compareTo(i(1)) == 0)
					return d(integer.multiply(i(c[0].equals("-") ? -1 : 1))).multiply(
							d(2).pow(Integer.parseInt(exponent, radix), mc(mc.getPrecision() + 10)), mc);
				// leading zeros
				int lz = Math.max(0, c[3].length() - calculateSignificandDigits(2, mantissa.bitLength(), radix));
				BigDecimal val;
				BigInteger[] b;
				if (radix == 2) {
					b = new BigInteger[] { i(0), integer.abs(), mantissa.abs(), i(-(mantissa.abs().bitLength() + lz)) };
				} else {
					// convert to decimal format
					val = toDecimal(mantissa, lz, radix, mc);
					// then convert to binary fraction
					b = fromDecimal(val, 2, calculateSignificandDigits(10, numOfFractionalDigits(val), 2));
				}
				// grab the number of indexes the point needs to move to normalise to 1.xxxxx
				lz = b[3].abs().intValue() - b[2].bitLength();
				if (integer.signum() == 0) {
					// factor in the first significant bit
					int shift = lz + 1;
					// normalise the exponent
					int exp = Integer.parseInt(exponent, radix) - shift;
					// clear the first significant bit from the mantissa since normalisation has
					// already taken place such that 1.011 = 11
					mantissa = clearMSB(b[2], 1);
					integer = i(1);// assign the most significant bit as the integer
					// calculate any leading zeros that may have been cut during normalisation of
					// the mantissa such as 1.011 = 11 therefore lz = 1
					lz = Math.max(0, (b[2].bitLength() - 1) - mantissa.bitLength());
					// calculate the actual decimal fraction of the binary significand
					val = toDecimal(c[0].equals("-") ? -1 : 1, integer, mantissa, lz, 2,
							mc(mc.getPrecision() + 10));
					// normalise the answer by multiplying the exponent
					return val.multiply(d(2).pow(exp, mc(mc.getPrecision() + 20)), mc);
				}
				final int exp;
				if (integer.compareTo(i(1)) != 0) {// integer and mantissa are not already normalised!
					final int shift = integer.bitLength() - 1;
					exp = Integer.parseInt(exponent, radix) + (integer.signum() == 0 ? 0 : shift);
//					System.out.println("integer: " + integer.bitLength());
					mantissa = clearMSB(integer, 1).shiftLeft(lz + b[2].bitLength()).or(b[2]);
					lz = shift - clearMSB(integer, 1).bitLength();
					integer = i(1);
				} else {// integer and mantissa are already normalised!
					exp = Integer.parseInt(exponent, radix);
					mantissa = b[2];
				}
				// calculate the actual decimal fraction of the binary significand
				val = toDecimal(c[0].equals("-") ? -1 : 1, integer, mantissa, lz, 2, mc);
				// normalise the answer by multiplying the exponent
				return val.multiply(d(2).pow(exp, mc(mc.getPrecision() + 20)), mc);

			} else if (radix != 10 && c[4] != null && c[4].equalsIgnoreCase("e")) {
				int exp = Integer.parseInt(exponent, radix);
				c[4] = null;
				c[5] = null;
				c[6] = null;
				BigDecimal d = toDecimal(String.format("%1$s%2$s%3$s%4$s", c[0], c[1], c[2], c[3]), radix, mc);
				BigInteger e = powerOfRadix(radix, exp);
				return d.multiply(d(e), mc);
			}

			int lz = mantissa.signum() == 0 ? 0
					: Math.max(0,
							c[3].length() - Math.max(1, calculateSignificandDigits(2, mantissa.bitLength(), radix)));

			return toDecimal(c[0].equals("-") ? -1 : 1, integer, mantissa, lz, radix, mc)
					.scaleByPowerOfTen(Integer.parseInt(exponent, radix));
		}
		throw new NumberFormatException("not a number");
	}

	/*
	 * Date: 9 Nov 2022-----------------------------------------------------------
	 * Time created: 18:47:09--------------------------------------------
	 */
	/**
	 * Parses the {@code String} argument - which is assumed to be in the radix
	 * specified the {@code fromRadix} parameter - then coverts the parsed value to
	 * a {@code String} in the radix specified as the parameter {@code toRadix} then
	 * returns it. All trailing zeros are clipped even though the given
	 * {@code digits} parameter covers said digit(s). If
	 * {@code fromRadix == toRadix} and there is no properly signed binary exponent
	 * within range then n is returned unparsed.
	 * <p>
	 * This method supports exponent character ('e' or 'p') but with caveat for
	 * radixes whose digits may include the exponent character such as 15 and 26.
	 * The exponent is also expected to be in the same radix as the number
	 * (significand). Please see {@link #toDecimal(String, int, MathContext)} for
	 * explanation on how to form an exponent.
	 * 
	 * @param n         the number string to be parsed which is expected to be in
	 *                  the radix specified by {@code fromRadix} parameter
	 * @param fromRadix the radix of the given number string.
	 * @param toRadix   the radix to which the conversion is to be made.
	 * @param mc        a rounding context to determine the number of digits in
	 *                  final value. Can be left {@code null}. If
	 *                  {@link MathContext#getPrecision()} is less than 2 or
	 *                  {@code null}, then the final value will have as much digits
	 *                  as the value to be converted
	 * @return a {@code String} which is equivalent to the argument after
	 *         successfully parsing it with a trailing point and a mantissa (0 for
	 *         integers). No exponent field is returned with the number.
	 * @throws NumberFormatException if the {@code String} parameter cannot be
	 *                               parsed or if radix is outside of the range
	 *                               ({@link Character#MIN_RADIX},
	 *                               {@link Character#MAX_RADIX})
	 * @see {@link #toDecimal(String, int, MathContext)}
	 */
	public static String toString(String n, int fromRadix, int toRadix, MathContext mc) throws NumberFormatException {
		// no 'p' character
//		boolean noPChar = (!n.contains("p")) || (!n.contains("P"));
		// radix < 26
//		boolean rLess26 = fromRadix < 26;
		// no signed binary exponent
//		boolean noSignedP = ((!n.contains("p-")) || (!n.contains("P-")))
//				|| ((!n.contains("p+")) || (!n.contains("P+")));
		n = n.toLowerCase();
		if (fromRadix == toRadix
				&& !((n.contains("p") && fromRadix < 26) || (n.contains("p-") || (n.contains("p+"))))) {
			return n;
		}
		int digits = mc == null ? n.length() : mc.getPrecision();
		mc = mc == null ? mc(calculateSignificandDigits(toRadix, digits, 10), rm("HALF_EVEN")) : mc;
		BigDecimal d = toDecimal(n, fromRadix, digits < 2 ? null : mc);
		if (toRadix == 10)
			return d.toPlainString();
		BigInteger[] b = fromDecimal(d, toRadix,
				digits > 0 ? digits : calculateSignificandDigits(10, numOfFractionalDigits(d), toRadix));
//		int numOfTrailingZeroes = Math.max(0, b[2].getLowestSetBit());
//		int mantLen = calculateSignificandDigits(2, b[2].bitLength(), toRadix);
//		BigInteger mantissa = b[2].shiftRight(numOfTrailingZeroes);
//		String lz = string('0', Math.max(0, b[3].abs().intValue() - (b[2].bitCount() == 0 ? 0 : mantLen)));
//		return String.format("%1$s%2$s.%3$s%4$s", b[0].bitCount() > 0 ? "-" : "", b[1].toString(toRadix), lz,
//				mantissa.toString(toRadix));
		String mantissa = b[2].toString(toRadix);
		String lz = string('0', Math.max(0, b[3].abs().intValue() - (b[2].bitCount() == 0 ? 0 : mantissa.length())));
		// Remove trailing zeroes
		StringBuilder sb = new StringBuilder(mantissa).reverse();
		mantissa = new StringBuilder(i(sb.toString(), toRadix).toString(toRadix)).reverse().toString();
		return String.format("%1$s%2$s.%3$s%4$s", b[0].bitCount() > 0 ? "-" : "", b[1].toString(toRadix), lz, mantissa);
	}

	/*
	 * Date: 25 Nov 2022-----------------------------------------------------------
	 * Time created: 06:20:28--------------------------------------------
	 */
	/**
	 * Does the same function as {@link #toString(String, int, int, MathContext)}
	 * except the value may have an exponent field. Note that this exponent is 'e'
	 * (signed) and in the same radix as {@code toRadix}.
	 * 
	 * @param n         the number string to be parsed which is expected to be in
	 *                  the radix specified by {@code fromRadix} parameter
	 * @param fromRadix the radix of the given number string.
	 * @param toRadix   the radix to which the conversion is to be made.
	 * @param mc        a rounding context to determine the number of digits in
	 *                  final value. Can be left {@code null}. If
	 *                  {@link MathContext#getPrecision()} is less than 2 or
	 *                  {@code null}, then the final value will have as much digits
	 *                  as the value to be converted
	 * @return a {@code String} which is equivalent to the argument after
	 *         successfully parsing it with a trailing point and a mantissa (for
	 *         fractional numbers) along with an exponent field.
	 * @throws NumberFormatException if the {@code String} parameter cannot be
	 *                               parsed or if radix is outside of the range
	 *                               ({@link Character#MIN_RADIX},
	 *                               {@link Character#MAX_RADIX})
	 * @see {@link #toString(String, int, int, MathContext)}
	 */
	public static String toScientificString(String n, int fromRadix, int toRadix, MathContext mc)
			throws NumberFormatException {
		n = n.toLowerCase();
		if (fromRadix == toRadix
				&& !((n.contains("p") && fromRadix < 26) || (n.contains("p-") || (n.contains("p+"))))) {
			return n;
		}
		int digits = mc == null ? n.length() : mc.getPrecision();
		mc = mc == null ? mc(calculateSignificandDigits(toRadix, digits, 10), rm("HALF_EVEN")) : mc;
		BigDecimal d = toDecimal(n, fromRadix, digits < 2 ? null : mc);
		if (toRadix == 10)
			return mathaid.calculator.base.util.Utility.toScientificString(d);
		else if (d.signum() == 0)
			return d.toString();
		else if (mathaid.calculator.base.util.Utility.isInteger(d)) {
			StringBuilder val = new StringBuilder(d.toBigInteger().toString(toRadix));
			if(val.length() > mc.getPrecision()) val = val.delete(mc.getPrecision(), val.length());
			int exp = val.length() - 1;
			assert exp >= 0;
			if (val.length() > 1)
				val.insert(1, '.');
			return val.append(String.format("e+%s", Integer.toString(exp, toRadix))).toString();
		}
		BigInteger[] b = fromDecimal(d, toRadix,
				digits > 0 ? digits : calculateSignificandDigits(10, numOfFractionalDigits(d), toRadix));
		String mantissa = b[2].toString(toRadix);
		String lz = string('0', Math.max(0, b[3].abs().intValue() - (b[2].bitCount() == 0 ? 0 : mantissa.length())));
		// Remove trailing zeroes
		StringBuilder sb = new StringBuilder(mantissa).reverse();
		mantissa = new StringBuilder(i(sb.toString(), toRadix).toString(toRadix)).reverse().toString();
		// normalise exponent
		sb.delete(0, sb.length());

		int exp;

		String integer = b[1].toString(toRadix);
		if (b[1].signum() == 0) {
			exp = -(lz.length() + 1);
			assert exp < 0;
			sb.append(String.format("%1$se%2$s", mantissa.length() <= mc.getPrecision() ? mantissa : mantissa.substring(0, mc.getPrecision()), Integer.toString(exp, toRadix)));
			if (mantissa.length() > 1)
				sb.insert(1, '.');
			return sb.toString();
		}
		exp = integer.length() - 1;
		assert exp >= 0;
		String welded = String.format("%1$s%2$s%3$s", integer, lz, mantissa);
		sb.append(String.format("%1$se+%2$s", welded.length() <= mc.getPrecision() ? welded : welded.substring(0, mc.getPrecision()), Integer.toString(exp, toRadix)));
		if (welded.length() > 1)
			sb.insert(1, '.');
		return sb.toString();
	}

	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 05:29:50--------------------------------------------
	 */
	/**
	 * Checks and returns true if the {@code char} argument is a digit in the given
	 * radix else returns false
	 * 
	 * @param digit a valid digit as a {@code char}
	 * @param radix the radix of the digit
	 * @return {@code true} if the first argument is a valid digit in the specified
	 *         radix else returns {@code false}
	 */
	public static boolean isNumber(char digit, int radix) {
		if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX)
			throw new ArithmeticException("radix out of range");
		final String digits = "0123456789abcdefghijklmnopqrstuvwxyz";
		return digits.substring(0, radix).indexOf(String.valueOf(digit).toLowerCase()) >= 0;
	}

	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 05:34:13--------------------------------------------
	 */
	/**
	 * Parses and decomposes the {@code String} parameter into the individual
	 * components of a numerical value and returns a {@code String} array of those
	 * components (with each index containing a component) - or an array of
	 * {@code null} {@code String} objects if the {@code String} parameter is not a
	 * valid number - using the specified radix.
	 * 
	 * <p>
	 * The Array that is returned is always a 7-length array with it's content
	 * indexed in the following order:
	 * <ul>
	 * <li>The sign of the value (either a "-" for negative values or a "+" for
	 * non-negative values).</li>
	 * <li>The integer digits including any leading zeros (without a sign) in the
	 * specified radix.</li>
	 * <li>The radix point or {@code null} if no radix point.</li>
	 * <li>The mantissa digits, including any leading and trailing zeros in the
	 * specified radix or {@code null} if no mantissa part. This value does not
	 * contain a sign or point.</li>
	 * <li>The exponent character or {@code null} if no exponent part. The only
	 * valid ones are 'e' a 'p' (case insensitive). This value does not contain a
	 * sign or point. Note that the exponent character <b>will</b> be not counted as
	 * an exponent character but as a digit if the character is part of the radix
	 * and there is not sign ("+" or "-") after this character.</li>
	 * <li>The exponent's sign (either a "-" for negative values or a "+" for
	 * non-negative values) or {@code null} if no exponent part. Note that the
	 * exponent character <b>will</b> be not counted as an exponent character but as
	 * a digit if the character is part of the radix and there is not sign ("+" or
	 * "-") after this character.</li>
	 * <li>The exponent digits i.e the exponent magnitude, including any leading
	 * zeros without any sign or exponent character or {@code null} if no exponent
	 * part. This value is assumed to be in the same radix as the number and is
	 * considered invalid if a character that is not a digit in the specified radix
	 * is present. Note that the exponent character <b>will</b> be not counted as an
	 * exponent character but as a digit if the character is part of the radix and
	 * there is not sign ("+" or "-") after this character.</li>
	 * </ul>
	 * If the value is found to be ill-formed (a point in the exponent part, sign in
	 * the mantissa or an illegal character), an array filled with {@code null}
	 * {@code String} values will be returned to indicate that the value could not
	 * be parsed. Even if the value is well formed, one or more indexes may still
	 * contain {@code null} if that part was not found. For example: the value
	 * {@code 1} may return {@code null} in the point , mantissa and exponent
	 * indexes.
	 * 
	 * @param n     the value to be parsed
	 * @param radix the radix of the {@code String} value as stipulated by the
	 *              {@link Character} class.
	 * @return a {@code String} array of the parts of the input value
	 */
	public static String[] getComponents(String n, int radix) {
		/*
		 * integer sign, integer, point, mantissa, exponent type (e or p), exponent
		 * sign, exponent
		 */
		String[] cpt = new String[7];
		char prev = '\0';
		int i = 0;
		while (i < n.length()) {
			char c = n.charAt(i++);
			if (c == '-' || c == '+') {
				if (i - 1 == 0)
					cpt[0] = String.valueOf(c);
				else if (prev == 'E' || prev == 'e' || prev == 'p' || prev == 'P')
					cpt[5] = String.valueOf(c);
				else {
//					err.printf("failed due to an illegal sign --> %1$s, previous >> %2$s\r\n", Arrays.toString(cpt), prev);
					fill(cpt, null);
					return cpt;
				}
			} else if (c == '.' && cpt[2] == null) {
				if (cpt[0] == null)
					cpt[0] = "+";
				if (cpt[1] == null)
					cpt[1] = "0";
				if (cpt[2] == null)
					cpt[2] = String.valueOf(c);
				else {
//					err.printf("failed due to an illegal point --> %s\r\n", Arrays.toString(cpt));
					fill(cpt, null);
					return cpt;
				}
			}
			/*
			 * The current character may enter this block as an exponent if any of the
			 * following is true --> It is an e (or E) and radix < 15 i.e the char will not
			 * be mistaken as a digit --> It is an e (or E) and the next char is a sign (+
			 * or -) --> It is a p (or P) i.e a binary exponent
			 */
			else if ((String.valueOf(c).compareToIgnoreCase("e") == 0 && radix < 15)

					|| (String.valueOf(c).compareToIgnoreCase("e") == 0 && i - 1 < n.length() - 1
							&& (n.charAt(i) == '-' || n.charAt(i) == '+'))

					|| (String.valueOf(c).compareToIgnoreCase("p") == 0 && radix < 26)

					|| (String.valueOf(c).compareToIgnoreCase("p") == 0 && i - 1 < n.length() - 1
							&& (n.charAt(i) == '-' || n.charAt(i) == '+'))) {
				if (cpt[2] == null)
					cpt[2] = ".";
				if (cpt[3] == null)
					cpt[3] = "0";
				if (cpt[4] == null)
					cpt[4] = String.valueOf(c);
				else {
//					System.err.printf("failed due to an illegal exponent char --> %s\r\n", Arrays.toString(cpt));
					fill(cpt, null);
					return cpt;
				}
			} else if (isNumber(c, radix)) { // 1.23456
				int start = i - 1;
				while (i < n.length()) {
					char d = n.charAt(i);
					if ((!isNumber(d, radix))

							|| (radix >= 15 && (d == 'e' || d == 'E') && i < n.length() - 1
									&& (n.charAt(i + 1) == '-' || n.charAt(i + 1) == '+'))

							|| (radix >= 26 && (d == 'p' || d == 'P') && i < n.length() - 1
									&& (n.charAt(i + 1) == '-' || n.charAt(i + 1) == '+')))
						break;
					i++;
				}
				if (cpt[1] == null && cpt[2] == null) {
					if (cpt[0] == null)
						cpt[0] = "+";
					cpt[1] = n.substring(start, i);
					prev = cpt[1].charAt(cpt[1].length() - 1);
					continue;
				} else if (cpt[3] == null && cpt[2] != null) {
					if (cpt[0] == null)
						cpt[0] = "+";
					if (cpt[1] == null)
						cpt[1] = "0";
					cpt[3] = n.substring(start, i);
					prev = cpt[3].charAt(cpt[3].length() - 1);
					continue;
				} else if (cpt[6] == null && cpt[4] != null) {
					if (cpt[5] == null)
						cpt[5] = "+";
					cpt[6] = n.substring(start, i);
					prev = cpt[6].charAt(cpt[6].length() - 1);
					continue;
				} else {
//					err.println(Arrays.toString(cpt));
//					System.err.printf("failed due to an illegal digit (%2$s) --> %1$s\r\n", Arrays.toString(cpt),
//							n.charAt(i - 1));
					fill(cpt, null);
					break;
				}
			} else {
//				err.println(Arrays.toString(cpt));
//				err.printf("failed due to an illegal character --> %s\r\n", Arrays.toString(cpt));
				fill(cpt, null);
				break;
			}
			prev = c;
		}
		if (cpt[4] != null) {
			if (cpt[5] == null)
				cpt[5] = "+";
			if (cpt[6] == null)
				cpt[6] = "0";
		}
		return cpt;
	}

	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 06:42:15--------------------------------------------
	 */
	/**
	 * Returns {@code true} if there is an exponent component and this component is
	 * a "p" exponent else {@code false}.
	 * 
	 * @param components a {@code String} array in the same format returned by
	 *                   {@link #getComponents(String, int)}
	 * @return {@code true} if there is an exponent component and this component is
	 *         a "p" exponent else {@code false}.
	 */
	public static boolean isNormalised(String[] components) {
		return components[4] != null && components[4].equalsIgnoreCase("p");
	}

	/*
	 * Date: 7 Aug 2021-----------------------------------------------------------
	 * Time created: 19:21:45--------------------------------------------
	 */
	/**
	 * Returns a {@code Radix} object appropriate for the given argument which is
	 * specified by the int value for that base.
	 * 
	 * @param r one of the values given by the constants 2, 8, 10, 16.
	 * @return a {@code Radix} object.
	 */
	public static Radix getRadix(int r) {
		switch (r) {
		case 2:
			return Radix.BIN;
		case 8:
			return Radix.OCT;
		case 16:
			return Radix.HEX;
		default:
			return Radix.DEC;
		}
	}

	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 07:20:26--------------------------------------------
	 */
	/**
	 * Parses the string in the given radix and returns a 2-length array containing
	 * the significand (may also contain a sign, the radix point and the mantissa if
	 * any) and the exponent (with no exponent character)
	 * 
	 * @param s the value to be parsed
	 * @param r the radix of the argument
	 * @return a 2-length array of the significand and the exponent
	 */
	private static String[] extractFloatingBinary(String s, int r) {
		String[] ans = new String[2];
		Apfloat ap = new Apfloat(s, s.length(), r);
		final String s2 = ap.toRadix(2).toString();
		int el = s2.indexOf('e');
		int eu = s2.indexOf('E');
		int e = el < 0 ? eu < 0 ? -1 : eu + 1 : el + 1;
		ans[0] = e == -1 ? s2 : s2.substring(0, e - 1);
		ans[1] = e == -1 ? "0" : s2.substring(e);
		ans[0] = ans[0].contains(".") ? ans[0] : String.format("%s.0", ans[0]);
		return ans;
	}

	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 07:25:11--------------------------------------------
	 */
	/**
	 * Attempts to normalise a fraction into floating point.
	 * 
	 * @param normalisedComponents a {@code String} array in the same format
	 *                             returned by {@link #getComponents(String, int)}
	 *                             which is assumed to have used the binary exponent
	 *                             'p'. If it dooes not have a binary exponent, then
	 *                             it is assumed that it's binary exponent is equal
	 *                             to 'p0'
	 * @param bits                 the number of bits the final floating point
	 *                             should use
	 * @param radix                the radix of the number
	 * @return a {@code FloatPoint} representing the argument
	 */
	public static FloatPoint sanitizeNormalisation(String[] normalisedComponents, BitLength bits, int radix) {
		if (normalisedComponents[4] == null && normalisedComponents[5] == null && normalisedComponents[6] == null) {
			normalisedComponents[4] = "p";
			normalisedComponents[5] = "+";
			normalisedComponents[6] = "0";
		} else if (!isNormalised(normalisedComponents))
			throw new ArithmeticException("irregular normalised form");
		BigInteger binExp = i(String.format("%1$s%2$s", normalisedComponents[5], normalisedComponents[6]),
				radix);
		if (normalisedComponents[2] == null)
			normalisedComponents[2] = ".";
		if (normalisedComponents[3] == null)
			normalisedComponents[3] = "0";
		String[] bin = extractFloatingBinary(String.format("%1$s%2$s%3$s%4$s", normalisedComponents[0],
				normalisedComponents[1], normalisedComponents[2], normalisedComponents[3]), radix);
		binExp = binExp.add(i(Integer.valueOf(bin[1])));
		return FloatPoint.toFloat(String.format("%1$sp%2$s", bin[0], binExp), bits, Radix.BIN, true);
	}

	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 06:53:03--------------------------------------------
	 */
	/**
	 * Returns {@code true} if the array is not filled with {@code null} values else
	 * {@code false}.
	 * 
	 * @param c a {@code String} array in the same format returned by
	 *          {@link #getComponents(String, int)}
	 * @return {@code true} if the array is not filled with {@code null} values else
	 *         {@code false}.
	 */
	public static boolean isNumber(String[] c) {
//		boolean isNull;
//		int i = 0;
//		do {
//			isNull = c[i] == null;
//		} while (isNull == true);
//		return !isNull;
		String[] s = new String[c.length];
		return compare(c, s) != 0;
	}

}
