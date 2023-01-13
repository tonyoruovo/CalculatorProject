/**
 * <a href={@docRoot mathaid/calculator/base/value/Precision.html#Exceptions}> <i>Exceptions</i></a>
 * 
 * To correctly round an hexadecimal, extract the correct number of needed digits plus one more
 * digit for the rounding, then convert to a binary after finding a suitable mask to prevent the
 * the loss of the leading zeros, share the binary string into groups of nibbles then add the first
 * character of the last group to the right to the binary string not containing the last group. Convert
 * back to a hex and do away with the mask positions. -----+++# DONE
 */
package mathaid.calculator.base.value;

import static mathaid.ExceptionMessage.BITS_GREATER_THAN_FOUND;
import static mathaid.ExceptionMessage.OVERFLOW_;
import static mathaid.ExceptionMessage.UNLIMITED_PRECISION_NOT_ALLOWED;
import static mathaid.calculator.base.util.Utility.fromDecimal;
import static mathaid.calculator.base.util.Utility.stripTrailingZeros;
import static mathaid.calculator.base.util.Utility.zeros;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.util.Date;

import mathaid.BaseException;
import mathaid.Device;
import mathaid.ExceptionMessage;
import mathaid.calculator.base.util.Utility;
import mathaid.calculator.error.ValueException;

//TODO: Use apfloat for radix conversion, it is faster
/*
 * 
 * Date: 6 Apr 2020---------------------------------------------------- Time
 * created: 14:47:11-------------------------------------------- Package: mathaid.calculator.base.value
 * ----------------------------------------- Project:
 * CalculatorProject----------------------------------------- File:
 * Precision.java----------------------------------------------- Class name:
 * Precision-----------------------------------------
 */
/**
 * An {@code enum} which specifies the precision of a floating point number that
 * complies with IEEE 754 standards. It also details the limits of a
 * floating-point including dealing with from floating-point to decimal and
 * vice-versa. Below is a summary of a generalised floating point logic.
 * <hr>
 * <h1>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
 * <font size="3">Floating point</font></h1>
 * <p>
 * A floating point number is a scientific notational number in binary. e.g
 * <code>0.006125</code> is <code>6.125 &times 10<sup>-3</sup></code> in decimal
 * scientific notation and
 * <code>1.10010001011010000111001 &times 2<sup>-8</sup></code> in binary
 * scientific notation. It is stored in computers in 3 segments namely: a sign
 * <i>s</i> for sign of the number, an exponent <i>e</i> (the exponent is
 * usually in the power of 2, but some variations uses other bases such as
 * decimal, hexadecimal etc) and a significand <i>m</i>(a.k.a mantissa,
 * coefficient). For example the number from the above example, <i>m</i> =
 * {@code 1.10010001011010000111001}, <i>s</i> = +, <i>e</i> = -8. The actual
 * value of a radix scientific notational number is:
 * <blockquote><code>m &divide b<sup>p-1</sup> &times b</code><sup>e</sup></blockquote>where
 * <i>b</i> is the base or radix of the representation, <i>p</i> is the
 * precision (the number of digits allowed in the fractional part of the
 * number). When written in common fractional notation without any form of
 * scientific notation.
 * </p>
 * <p>
 * But because computers only accept binary internally, the decimal fraction is
 * first converted to a binary fraction (called a bicimal), then the radix point
 * is shifted to the front of the first significant bit (in binary this bit is
 * always 1). The number of indices the radix point is shifted becomes the
 * binary exponent. If the radix point was shifted left then the binary exponent
 * becomes positive else it becomes negative.
 * </p>
 * <h2>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
 * IEEE 754 conventions</h2>
 * <p>
 * IEEE (<b>I</b>nternational <b>E</b>lectrical <b>E</b>lectronics
 * <b>E</b>ngineers) 754 is the common standard for implementation of floating
 * point arithmetic. It states that every fraction should be encoded as a
 * floating point and every floating point encoding must have a bit layout with
 * the sign, exponent and mantissa represented respectively as a series of bit
 * which form a bit pattern. Below is a standard arrangement for a typical IEEE
 * 754 bit layout:
 * </p>
 * 
 * <h3>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
 * <font size="2">The bit layout </font>
 * 
 * </h3>In any IEEE 754 bit layout, the sign comes first in the string, followed
 * by the a bit pattern that constitutes the exponent and finally the
 * significand bit fills the rest of the remaining length.
 * <p>
 * <img src="sign_exponent_mantissa.jpg" alt="sign exponent and significand"
 * style="width:600px;height:50px;">
 * <p>
 * The most notable representations are the double and single precision.
 * <p>
 * The bit layout for an IEEE 754 single precision floating point number carries
 * an optional sign bit, an 8 bit exponent and a 24 bit significand. A bit in
 * the significand is implicit and is not stored nor written down. Below is an
 * example of the fraction string {@code -2.3099328e-23} showing the sign,
 * exponent and significand bits:
 * </p>
 * <p>
 * <img src="single_precision_bit_layout.jpg" alt="sign exponent and significand
 * 2" style="width:1080px;height:70px;"><br>
 * The value of this bit pattern can be retrieved as a two's complement of an
 * integer. In fact IEEE 754 floating point numbers are stored as integers in
 * twos complement or any other integer format the underlying system uses. Note
 * that the significand portion is 23 bits and not 24 bit. That is because there
 * is always a 1. that was discarded. See the section <i>The significand</i> for
 * more details, although there are exceptions to be considered where this may
 * not be the case. Please see the section <i>Exceptions</i>.
 * </p>
 * <h4>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
 * The sign
 * 
 * </h4>
 * <p>
 * The sign is the leftmost bit in an IEEE 754 standard integer. If the number
 * is negative, then the sign will be 1 else the sign bit 0. Please know that if
 * you convert an IEEE 754 integer into a binary string you may not see the sign
 * bit except you enable leading zeros or the length of the string is equal to
 * the total bit length of the precision used (such as single precision [32
 * bits] or double precision [64 bits]).
 * </p>
 * <h4>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
 * The exponent
 * 
 * </h4>
 * <p>
 * After the sign bit comes the next set of bits which are all interpreted as
 * the exponent. The length of the exponent depends on the precision used. E.g
 * Double precision uses 11 bits and single precision uses 8 bits.
 * </p>
 * <p>
 * Now the actual value of the exponent that is represented by the exponent bit
 * pattern is a biased value (excess-n notation). That is the actual expoenent
 * added to <code> 2<sup>exponent bits -1</sup> - 1</code> to produce the figure
 * that is represented in the bit pattern. This value which all exponent are
 * added to before they are represented is called a <i>bias</i>. For Double
 * precision, the bias is 1023 and 127 for Single precision. The bias is stored
 * as an unsigned integer. E.g for the above represented decimal fraction
 * {@code 2.3099328e-23} the exponent is -76 (which is {@code 1011 0100} in 8
 * bit unsigned binary notation). But in order to store -76 it is added to 127
 * (which is the exponent bias of Single precision) and the answer is 51.
 * Therfore the actual exponent bit pattern stored is 51 (which is
 * {@code 0011 0011} in unsigned notation).
 * </p>
 * <h4>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
 * The significand
 * 
 * </h4>
 * <p>
 * After the exponent bit pattern comes the significand which like the exponent
 * has a fixed bit length (24 bit for single and 53 for double precision). The
 * actual bit pattern stored omits the left most bit when it is stored or
 * written down. This is called <i>the leading bit convention</i> or <i>the
 * implicit bit</i>.
 * </p>
 * <p>
 * The significand of a floating point number is all the digits of the bicimal
 * (binary fraction) (apart from the radix point). If the significand bits in a
 * bicimal is less than the required number of bits in a precision, then bit
 * patern is padded with trailing zeros. Because of the implicit bit, the number
 * of the significand bit actually placed in the layout is one less than the
 * total significand bits required. If the significand bit length is more than
 * the required bit length, then it is truncated from the right most bits. E.g
 * the bicimal of {@code 101110001.0111101110110110110110110110} to be stored as
 * a Single precision floating point number must get rid of the radix point and
 * extract only 24 bits for the significand, since Single precision only
 * accommodate 24 bit for the significand, therefore
 * {@code 10111000101110111011011} is the significand bit pattern for the above
 * bicimal. But the leftmost bit must be discarded (this is the implicit bit) in
 * order to be stored correctly. This implicit bit is always 1 (except in some
 * other special cases). Therefore the correct significand of the above bicimal
 * is {@code 01110001011110111011011}. the leading zeros are not to be discarded
 * (unlike binary integer).
 * </p>
 * <h3>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
 * <font size="2">Exceptions</font>
 * 
 * </h3>
 * <p>
 * There various exception as to how the bit layout is formed. These exceptions
 * are special cases such as:
 * <ul>
 * <li><b>Subnormal</b> - Also known as denormal numbers, are numbers that are
 * smaller than the least (positive non-zero value) normal value, hence cannot
 * be represented in normalised form (binary scientific notation). The minimum
 * normal number to be represented is
 * &#0177<code> 2<sup>min exponent</sup></code>. Any non-zero number whose
 * absolute value is below this cannot be properly represented in normalised
 * form. The implicit bit of a subnormal number is always 0.<br>
 * When the indices that the binary point was shifted is less than min exponent,
 * but did not reach the implicit 1, then all the bits - from the radix point
 * right-ward - will be the significand. E.g <code>2<sup>-130</sup></code> in
 * single precision is <code> 0.0001</code> &#215; <code>2<sup>-4</sup></code>
 * in floating point (IEEE). Notice that the exponent has been biased to IEEE
 * 754 Standard for single precision, and the significand has the implicit 0
 * instead of the implicit 1. This is called an <i>unnormalised form</i> of a
 * floating point.<br>
 * The least subnormal number that can be represented is
 * <code> 2<sup>min exponent - (significand bit length - 1)</sup></code> any
 * number whose absolute value is less than a subnormal number is represented as
 * 0 or {@code NaN}.</li>
 * <li><b>Zero</b> - If a number is 0, the exponent bit pattern contains all
 * zeros, the significand also contains only zeros. The sign however, can be
 * positive or negative. This is one of the disadvantages of using floating
 * point numbers.</li>
 * <li><b>Infinities</b> - An infinity value is a value whose absolute value is
 * exceeds the max value that can be represented in a particular precision. The
 * largest value a precision can represent is
 * <code> 2<sup>bias + 1</sup></code>. When Infinity is reached, the exponent is
 * all ones and the significand is all zeros. Infinities may be signed (Thus
 * negative and positive infinities differ in bit pattern).</li>
 * <li><b>NaN</b> - An {@code NaN} (<b>N</b>ot-<b>a</b>-<b>N</b>umber) value is
 * a value whose absolute value is less than the smallest subnormal number
 * possible. The smallest subnormal value an IEEE 754 precision can represent is
 * <code> 2<sup>min exponent - (total significand bit length - 1)</code>.</li>
 * </ul>
 * </p>
 * <hr>
 * <p>
 * A {@code Precision} object implements IEEE 754 conventions and this
 * implementation uses the <i>leading bit convention</i> in <i>big-endian</i>
 * byte order. This {@code enum} can identify 4 special cases for the following
 * numbers according to IEEE 754 conventions:
 * <ul>
 * <li>Positive infinity i.e <code>isFinite(BigDecimal)</code> returns
 * {@code false} and the sign of the number is positive</li>
 * <li>Negative infinity i.e <code>isFinite(BigDecimal)</code> returns
 * {@code false} and the sign of the number is negative</li>
 * <li>Subnormal. This only returns special exponents</li>
 * <li><code>NaN</code>(Not-a-Number). This will only return an <code>NaN</code>
 * pattern if {@code isNaN(BigDecimal)} returns {@code true}</li>
 * </ul>
 * All methods in this {@code enum} are final and cannot be changed, This is so
 * because java's <code>enum</code> rules prevents a '{@code final}' modifier to
 * be used in the declaration of an {@code enum}. The method that return an
 * integer actually returns a bit pattern which is similar to IEEE 754 standard;
 * in big-endian byte oder, whereby the bit at the most significant bit index
 * carries the sign; the bit following up to the index
 * log<sub>2</sub>{@code (getMaxExponent() + 1)} - 1 is the exponent and
 * remaining bits up to the lowest set bit is the significand(mantissa) of the
 * number. A {@code MathContext} object is provided for users who may wish to
 * round a {@code BigDecimal} with the same precision specified by the IEEE 754
 * as the {@code Precision} object. All methods(with one exception) that return
 * a {@code BigDecimal} are rounded with this {@code MathContext}. The method
 * that returns a {@code BigInteger} returns a bit pattern in <i>big-endian</i>
 * format.<br>
 * </p>
 * <p>
 * Please see the wikipedia article at
 * <a href = "http://www.wikipedia.org/IEEE%20754%20-%20Wikipedia.html">IEEE
 * 754</a> under the section '<b>Basic and interchange formats</b>' for a
 * detailed description of the {@code enum} constants in this {@code enum}.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 *         <p>
 * 
 * @apiNote The documentation of this {@code enum} consistently refers to
 *          <i>normalised form</i> as a floating point representation in the
 *          form:
 *          <code>[implicit bit].[radix specific mantissa]p[binary exponent]</code>.
 *          Any representation in this form using this notation is referred to
 *          by the documentation of this {@code enum} as being in <i>normalised
 *          form</i> and as such the method {@code isNormalised(String)} will
 *          return {@code true}.
 *          <p>
 *          The {@code enum} for 8 bit IEEE 754 floating-point numbers is not
 *          included in this version. Upcoming versions will contain it in the
 *          nearest future.
 * @implNote The bit pattern of NaNs and zeros are identical in this
 *           implementation
 */
public enum Precision {

	////////////////////////////////////////////////////////////
	/////////////////////// Constants /////////////////////////
	//////////////////////////////////////////////////////////

	/**
	 * A {@code Precision} that specifies floating-point in a non-standard 8 bits. It uses 4 bits to
	 * represent the significand and 4 for the exponent.
	 */
	BIT_8(BitLength.BIT_8),

	/**
	 * A {@code Precision} that specifies floating-point properties of Binary16(Half
	 * precision) in accordance with IEEE 754 standards. It uses 16 bits to
	 * represent values.
	 * <p>
	 * This implementation overrides the method {@code toHex(String)} by scaling the
	 * decimal argument and rounding it correctly. Hence the method
	 * {@code toHex(String)} will not function as other {@code enum} constant's as
	 * they are not implemented the same. The hex rounding uses a binary based
	 * rounding.
	 * </p>
	 */
	HALF(BitLength.BIT_16) {
		/*
		 * Most Recent Date: 14 Apr 2020---------------------------------------- Most
		 * recent time created: 11:15:27--------------------------------
		 */
		@Override
		public final String toHex(String decimal) {
			if (checkNormalised(decimal))
				return decimal;
			/* store the single precision's hexadecimal representation */
			String singleHex = Precision.SINGLE.toHex(decimal);
			/* If it is zero, use the single precision method */
			if (new BigDecimal(decimal).compareTo(BigDecimal.ZERO) == 0)
				return singleHex;
			/* store the sign, implicit bit, radix point and exponent */
			String hex = super.toHex(decimal);
			/*
			 * If the decimal to be converted is a subnormal, create a subnormal single
			 * precision number, then replace the exponent with a half precision exponent,
			 * round properly and return.
			 */
			if (isSubnormal(new BigDecimal(decimal))) {
				singleHex = Precision.SINGLE.toHex(scalb(decimal, SINGLE.getMinExponent() - getMinExponent()));
				singleHex = singleHex.replaceFirst("p-126$", "p" + getMinExponent());
				return hexRound(singleHex);
			} else if (isInfinite(new BigDecimal(decimal)))
				return "1.0p16";
			/* concatenate the following... and properly round them */
			return hexRound(hex.substring(0, hex.indexOf(".") + 1)// the sign, implicit bit, radix point,
					/* the hexadecimal string, */
					.concat(singleHex.substring(singleHex.indexOf(".") + 1, singleHex.indexOf("p")))
					.concat(hex.substring(hex.indexOf("p"))));// and the exponent
		}

		/*
		 * Date: 15 Apr 2020---------------------------------------------------- Time
		 * created: 13:57:27--------------------------------------------
		 */
		/**
		 * Returns {@code num} &times <code>2<sup>scale</sup></code>
		 * 
		 * @param num   string decimal to b scaled by 2.
		 * @param scale the exponent of two
		 * @return {@code num} &times <code>2<sup>scale</sup></code>
		 */
		private String scalb(String num, int scale) {
			return new BigDecimal(num).multiply(new BigDecimal("2").pow(scale, getMathContext()), getMathContext())
					.toString();
		}

		/*
		 * 
		 * Date: 15 Apr 2020---------------------------------------------------- Time
		 * created: 13:38:19--------------------------------------------
		 */
		/**
		 * Rounds the hex string argument to a proper {@code HALF} hex fraction. The
		 * argument is in the floating point form: [implicit bit].[hex fraction]p[binary
		 * exponent]. The implicit bit is the same as described in this class docs.
		 * 
		 * @param raw the normalised IEEE 754 hex floating point number as a string
		 * @return the argument rounded to {@code HALF} precision
		 */
		private String hexRound(String raw) {
			/* Extract the hex fraction without the implcit bit and exponent */
			String hex = raw.substring(raw.indexOf(".") + 1, raw.indexOf("p"));
			/* If the hex fraction does not need rounding, return it */
			if (hex.length() <= 3)
				return raw;
			/* mask of 28 zeros to cover the hex fractional digits during rounding */
			String mask = new BigDecimal("1E28").toBigInteger().toString();
			/* inclusive OR to preserve the high bits even though they are leading zeros */
			String ans = new BigInteger(mask, 2).or(new BigInteger(hex, 16)).toString(2);
			/* approximate if neccessary... */
			ans = new BigInteger(ans.substring(0, 17)// use only the bits in the positions needed (three nibbles plus an
														// extra 10000) and discard the rest
			, 2).add(new BigInteger(Character.toString(ans.charAt(17))// add the bit next to the neccessary bits
			, 2)).toString(16);// parse as a hex
			/* concatenate the following... */
			ans = raw.substring(0, raw.indexOf(".") + 1)// the sign, implicit bit, radix point,
			/* the hexadecimal mantissa string, */
//					+ stripTrailingZerosAndReturn(
					+ stripTrailingZeros(// remove any trailing zeros and return
							ans.substring(2, ans.length() < 8 ? ans.length() : 5), 16, true)
			/* and the exponent */
					+ raw.substring(raw.indexOf("p"));
			/* remove any trailing zeros and return */
			return ans;
		}
	},
	/**
	 * A {@code Precision} that specifies floating-point properties of
	 * Binary32(Single precision) in accordance with IEEE 754 standards. It uses 32
	 * bits to represent values.
	 * <p>
	 * This implementation overrides the method {@link Precision#toHex(String)} by
	 * scaling the decimal argument and rounding it correctly. Hence the method
	 * {@link Precision#toHex(String)} will not function as other {@code enum}
	 * constant's as they are not implemented the same. The hex rounding uses a
	 * binary based rounding.
	 * </p>
	 */
	SINGLE(BitLength.BIT_32) {
		/*
		 * Most Recent Date: 14 Apr 2020---------------------------------------- Most
		 * recent time created: 11:15:27--------------------------------
		 */
		@Override
		public final String toHex(String decimal) {
			if (checkNormalised(decimal))
				return decimal;
			/* store the double precision's hexadecimal representation */
			String doubleHex = Precision.DOUBLE.toHex(decimal);
			/* If it is zero, use the double precision method */
			if (new BigDecimal(decimal).compareTo(BigDecimal.ZERO) == 0)
				return doubleHex;
			/* store the sign, implicit bit, radix point and exponent */
			String hex = super.toHex(decimal);
			/*
			 * If the decimal to be converted is a subnormal, create a subnormal double
			 * precision number, then replace the exponent with a single precision exponent,
			 * round properly and return.
			 */
			if (isSubnormal(new BigDecimal(decimal))) {
				doubleHex = Precision.DOUBLE.toHex(scalb(decimal, DOUBLE.getMinExponent() - getMinExponent()));
				doubleHex = doubleHex.replaceFirst("p-1022$", "p-126");
				return hexRound(doubleHex);
			} else if (isInfinite(new BigDecimal(decimal)))
				return "1.0p+128";

			/* concatenate the following... and properly round them */
			return hexRound(hex.substring(0, hex.indexOf(".") + 1)// the sign, implicit bit, radix point,
					/* the hexadecimal string, */
					.concat(doubleHex.substring(doubleHex.indexOf(".") + 1, doubleHex.indexOf("p")))
					.concat(hex.substring(hex.indexOf("p"))));// and the exponent
		}

		/*
		 * Date: 15 Apr 2020---------------------------------------------------- Time
		 * created: 14:09:23--------------------------------------------
		 */
		/**
		 * Returns {@code num} &times <code>2<sup>scale</sup></code>
		 * 
		 * @param num   string decimal to b scaled by 2.
		 * @param scale the exponent of two
		 * @return {@code num} &times <code>2<sup>scale</sup></code>
		 */
		private String scalb(String num, int scale) {
			return new BigDecimal(num).multiply(new BigDecimal("2").pow(scale, getMathContext()), getMathContext())
					.toString();
		}

		/*
		 * Date: 15 Apr 2020---------------------------------------------------- Time
		 * created: 14:10:16--------------------------------------------
		 */
		/**
		 * Rounds the hex string argument to a proper {@code SINGLE} hex fraction. The
		 * argument is in the floating point form: [implicit bit].[hex fraction]p[binary
		 * exponent]. The implicit bit is the same as described in this class docs.
		 * 
		 * @param raw the normalised IEEE 754 hex floating point number as a string
		 * @return the argument rounded to {@code SINGLE} precision
		 */
		private String hexRound(String raw) {
			/* store the hex fraction without the implicit bit and exponent */
			String hex = raw.substring(raw.indexOf(".") + 1, raw.indexOf("p"));
			/* If the hex fraction does not need rounding, return it */
			if (hex.length() <= 6)
				return raw;
			/* mask of 56 zeros to cover the hex fractional digits during rounding */
			String mask = new BigDecimal("1E56").toBigInteger().toString();
			/* inclusive OR to preserve the high bits even though they are leading zeros */
			String ans = new BigInteger(mask, 2).or(new BigInteger(hex, 16)).toString(2);
			/* approximate if neccessary... */
			ans = new BigInteger(ans.substring(0, 29)// use only the bits in the positions needed (six nibbles plus an
														// extra 10000) and discard the rest
			, 2).add(new BigInteger(Character.toString(ans.charAt(29))// add the bit next to the neccessary bits
			, 2)).toString(16);// parse as a hex
			/* concatenate the following... */
			ans = raw.substring(0, raw.indexOf(".") + 1)// the sign, implicit bit, radix point,
			/* the hexadecimal string, */
//					+ stripTrailingZerosAndReturn(
					+ stripTrailingZeros(// remove any trailing zeros and return
							ans.substring(2, ans.length() < 15 ? ans.length() : 8), 16, true)
			/* and the exponent */
					+ raw.substring(raw.indexOf("p"));
			return ans;
		}
	},
	/**
	 * A {@code Precision} that specifies floating-point properties of
	 * Binary64(Double precision) in accordance with IEEE 754 standards. It uses 64
	 * bits to represent values.
	 */
	DOUBLE(BitLength.BIT_64),
	/**
	 * A {@code Precision} that specifies floating-point properties of Binary80
	 * (Double extended precision) in accordance with IEEE 754 standards. It uses 80 bits to
	 * represent values. This implementation mimics x86 double extended formats
	 */
	EXTENDED(BitLength.BIT_80),
	/**
	 * A {@code Precision} that specifies floating-point properties of
	 * Binary128 in accordance with IEEE 754 standards. It uses
	 * 128 bits to represent values.
	 */
	QUADRUPLE(BitLength.BIT_128),
	/**
	 * A {@code Precision} that specifies floating-point properties of
	 * Binary256 in accordance with IEEE 754 standards. It uses
	 * 256 bits to represent values.
	 */
	OCTUPLE(BitLength.BIT_256),
	/**
	 * A {@code Precision} constant to assert that the user does not want to use
	 * floating-point. Note that usage of this constant with almost all the methods
	 * in this {@code enum} will lead to undefined results including throwing
	 * various types of exceptions.
	 */
	UNLTD;

	/**
	 * The number of twos in the divisor that is used in the decomposing of "decimal
	 * expansion" to floating point
	 */
	/* 0x50000 Anything above this, performance improvement becomes marginal... */
	private static final int DYADIC_BITS = 40960;
	/** The divisor used for decomposing a "real number" to floating point */
	private static final BigDecimal DYADIC_BASE = new BigDecimal("2").pow(DYADIC_BITS);

	////////////////////////////////////////////////////////////
	///////////////////// Constructors ////////////////////////
	//////////////////////////////////////////////////////////

	/*
	 * 
	 * Date: 8 Apr 2020---------------------------------------------------- Time
	 * created: 00:16:18--------------------------------------------
	 */
	/**
	 * Constructor for the {@code enum} <code>UNLTD</code> whereby all fields in
	 * this {@code enum} are 0.
	 */
	private Precision() {
		this(BitLength.BIT_UNLTD);
	}

	/*
	 * 
	 * Date: 8 Apr 2020---------------------------------------------------- Time
	 * created: 00:18:35--------------------------------------------
	 */
	/**
	 * Constructs a {@code Precision} object for all active constants in this
	 * {@code enum} using the specified a length. 'Active constants' includes all
	 * the constants in this {@code enum} excluding {@code UNLTD}.
	 * 
	 * @param bits the bit length of the {@code Precision}
	 */
	private Precision(BitLength bits) {
		this.length = bits.length();
		switch (this.length) {
		case 8:
			significandBits = 4;
			break;
		case 16:
			significandBits = 11;
			break;
		case 64:
			significandBits = 53;
			break;
		case 80:
			significandBits = 65;
			break;
		case 128:
			significandBits = 113;
			break;
		case 256:
			significandBits = 237;
			break;
		default:
			significandBits = 24;
		}
		exponent = (length - significandBits);
	}

	////////////////////////////////////////////////////////////
	/////////////////////// Accessors /////////////////////////
	//////////////////////////////////////////////////////////

	/*
	 * 
	 * Date: 8 Apr 2020---------------------------------------------------- Time
	 * created: 00:24:50--------------------------------------------
	 */
	/**
	 * Gets the total number of bits used to represent this {@code Precision} which
	 * includes the sign, exponent and significand bits
	 * 
	 * @return the maximum number of bits this {@code Precision} can be stored in
	 */
	public final int getBitLength() {
		return length;
	}

	/*
	 * Date: 8 Apr 2020---------------------------------------------------- Time
	 * created: 00:46:47--------------------------------------------
	 */
	/**
	 * Returns the total number of bits within the word length used to represent the
	 * mantissa (significand) part of the floating-point value including the
	 * implicit bit in the <i>leading bit convention</i>
	 * 
	 * @return the max bit length of this {@code Precision} significand
	 */
	public final int getSignificandBits() {
		return significandBits;
	}

	/*
	 * Date: 10 Apr 2020---------------------------------------------------- Time
	 * created: 09:40:06--------------------------------------------
	 */
	/**
	 * Returns the number of bits used to represent the exponent
	 * 
	 * @return the bits for exponent of this {@code Precision}
	 */
	public final int getExponentBits() {
		return exponent;
	}

	////////////////////////////////////////////////////////////
	//////////////////////// Checks ///////////////////////////
	//////////////////////////////////////////////////////////

	/*
	 * 
	 * Date: 8 Apr 2020---------------------------------------------------- Time
	 * created: 00:54:48--------------------------------------------
	 */
	/**
	 * Returns {@code true} if the argument's magnitude is less than or equal to
	 * the<br>
	 * max possible value, and {@code false} otherwise. This is only for testing for
	 * infinity. NaNs may return {@code false}
	 * 
	 * @param num the number to be tested
	 * @return {@code true} if the argument's magnitude is less than or equal to the
	 *         max possible value, and {@code false} otherwise.
	 */
	public final boolean isFinite(BigDecimal num) {
		/* it is not NaN and it's absolute value is lesser than max value */
		return (!isNaN(num)) && (num.abs(getMathContext()).compareTo(getMaxValue()) <= 0);
	}

	/*
	 * Date: 9 Apr 2020---------------------------------------------------- Time
	 * created: 21:09:42--------------------------------------------
	 */
	/**
	 * Checks whether the argument is compatible with infinity value in relation to
	 * this {@code Precision} and also in compliance with IEEE 754 stipulations.
	 * This method will return {@code false} for {@code NaN} and {@code true} for
	 * {@code Infinity}. This method is not just for convenience, please use this
	 * method to check for the inifiteness of a value.
	 * 
	 * @param num the number to be tested
	 * @return {@code true} if the argument is infinite as defined by IEEE 754
	 *         conventions for this {@code Precision} otherwise retuns {@code false}
	 */
	public final boolean isInfinite(BigDecimal num) {
		return (num.abs(getMathContext()).compareTo(getMaxValue()) > 0);
	}

	/*
	 * Date: 8 Apr 2020---------------------------------------------------- Time
	 * created: 01:00:29--------------------------------------------
	 */
	/**
	 * Tests whether the argument lies within the subnormal range. A subnormal<br>
	 * number is a number which is lesser than smallest non-zero, non-negative<br>
	 * normal value
	 * 
	 * @param num the number to be tested
	 * @return {@code true} if the argument's magnitude is less than the
	 *         smallest<br>
	 *         possible normal value, and {@code false} otherwise.
	 */
	public final boolean isSubnormal(BigDecimal num) {
		/* num is less than 1 BUT greater than 0 and less than the min normal */
		return (num.signum() != 0)// must not be 0
				/* It's absolute value must be lesser than 1 */
				&& (num.abs(getMathContext()).compareTo(BigDecimal.ONE) < 0)
				/* It's absolute value must be lesser than minimum normal */
				&& (num.abs(getMathContext()).compareTo(getMinNormal()) < 0);
	}

	/*
	 * 
	 * Date: 8 Apr 2020---------------------------------------------------- Time
	 * created: 01:06:42--------------------------------------------
	 */
	/**
	 * Tests whether the argument is less than the smallest non-zero, positive value
	 * in this {@code Precision}. This method will return {@code true} if the
	 * argument is lesser than the minimum possible value or {@code false} if
	 * otherwise.
	 * 
	 * @param num the number to be tested
	 * @return {@code true} if the argument's magnitude is less than the
	 *         smallest<br>
	 *         possible value, and {@code false} otherwise.
	 */
	public final boolean isNaN(BigDecimal num) {
		/* It is not a 0 and its absolute value is less than minimum value */
		return (num.signum() != 0) && (num.abs(getMathContext()).compareTo(getMinValue()) < 0);
	}

	/*
	 * Date: 17 Apr 2020---------------------------------------------------- Time
	 * created: 15:50:12--------------------------------------------
	 */
	/**
	 * Checks the whether or not the argument is in IEEE 754 floating point
	 * normalised form.
	 * <p>
	 * To be in a valid normalised form, the following must be upheld:
	 * <ul>
	 * <li>The mantissa {@code String} must be in either binary, octal, decimal or
	 * hexadecimal format, any other format will not be recognised</li>
	 * <li>The binary exponent {@code String} (i.e all digits after the 'p') must be
	 * in decimal format, any other format will not be recognised</li>
	 * <li>The radix point cannot start the number, neither can the the binary
	 * exponent</li>
	 * <li>The only valid digit (apart from a sign of course!) at the start of a
	 * number is 1</li>
	 * </ul>
	 * </p>
	 * <p>
	 * This method potentially throws {@code PrecisionException} if the exponent
	 * exceeds the max exponent or if the exponent is less than the minimum
	 * exponent. This method also does not check if the value is a valid number,
	 * it's job is to check whether the rules of writing a floating point number are
	 * broken or not.
	 * </p>
	 * 
	 * @param value the number {@code String} (in any radix) to be tested.
	 * @return {@code true} if this is in normalised form else {@code false}.
	 * @throws PrecisionException if the exponent overflows or underflows
	 */
	public boolean checkNormalised(String value) {
		if (this == UNLTD)
			new PrecisionException(UNLIMITED_PRECISION_NOT_ALLOWED, "Precision." + toString());
		boolean hasPoint = value.contains(".");
		if (!hasPoint)
			return false;
		int exponent = 0;
		/* magnitude of the integral part */
		BigInteger integer = null;

		boolean hasExpo = value.contains("p");

		if (!hasExpo)
			return false;
		try {
			exponent = Integer.parseInt(value.substring(value.indexOf("p") + 1));
			integer = new BigInteger(value.substring(0, value.indexOf(".")));

			try {
				new BigInteger(value.substring(value.indexOf('.') + 1, value.indexOf('p')), 16);
			} catch (@SuppressWarnings("unused") NumberFormatException e) {
				return false;
			}
		} /* something happened in the exponent area, probably an empty string? */
		catch (@SuppressWarnings("unused") NumberFormatException | IndexOutOfBoundsException e) {
			return false;
		}

		if (exponent > getMaxExponent() || exponent < getMinExponent())
//			throw new PrecisionException("Exponent overflow!");
			try {
				Utility.writeTextToFile("", "out.txt",
						MessageFormat.format( new Date() + Device.lineSeparator() + OVERFLOW_.getLocalizedMessage(), new Object[] {}),
						StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			} catch (IOException e) {
				e.printStackTrace();
			}
		if (integer.abs().compareTo(BigInteger.ONE) > 0)
			return false;
		return true;
	}

	////////////////////////////////////////////////////////////
	//////////////// Exponents and mantissa ///////////////////
	//////////////////////////////////////////////////////////

	/*
	 * 
	 * Date: 8 Apr 2020---------------------------------------------------- Time
	 * created: 00:28:10--------------------------------------------
	 */
	/**
	 * Returns the maximum exponent of this {@code Precision} which is used to
	 * represent the largest value possible.
	 * <p>
	 * The exponent here is actually the binary exponent(after the decimal fraction
	 * has been normalised) and not the ubiquitous decimal exponent. This is the
	 * same exponent used by the value {@code getMaxValue()}. For the max value of
	 * {@code this}, a programmer should note that one is added to to this value
	 * (that is, <code>2<sup>getMaxExponent() + 1</sup></code>) in its unnormalised
	 * decimal form, such as a scientific notation.
	 * 
	 * @return the max binary exponent.
	 */
	public final int getMaxExponent() {
		return (int) (Math.pow(2, exponent - 1) - 1);
	}

	/*
	 * 
	 * Date: 8 Apr 2020---------------------------------------------------- Time
	 * created: 00:33:42--------------------------------------------
	 */
	/**
	 * Returns the minimum exponent of this {@code Precision} which is used to
	 * represent the smallest non negative non-zero normal value.
	 * <p>
	 * The exponent here is actually the binary exponent(after the decimal fraction
	 * has been normalised) and not the ubiquitous decimal exponent. This is the
	 * same exponent used by the value {@code getMinNormal()}. The actual exponent
	 * used by {@link Precision#getMinValue()} is lesser than this by
	 * {@link #getSignificandBits()} - 1. That is
	 * <code>2<sup>getMinExponent() - (getSignificandBits() - 1)</sup></code>
	 * 
	 * @return the min binary exponent for a normal value
	 */
	public final int getMinExponent() {
		/* Subtract 1 then negate */
		return (getMaxExponent() - 1) * -1;
	}

	/*
	 * Date: 10 Apr 2020---------------------------------------------------- Time
	 * created: 07:04:06--------------------------------------------
	 */
	/**
	 * Returns the bias used for the exponent part of this floating-point in
	 * relation to this {@code Precision} in compliance to IEEE 754 conventions
	 * 
	 * @return the exponent bias for this {@code Precision}
	 */
	public final int getBias() {
		return getMaxExponent();
	}

	/*
	 * Date: 16 Jun 2020-----------------------------------------------------------
	 * Time created: 14:24:57--------------------------------------------
	 */
	/**
	 * Returns the unbiased binary exponent of the BigDecimal.
	 * 
	 * @param num a {@code BigDecimal} value
	 * @return the binary exponent of the {@code BigDecimal} argument
	 */
	public final int getExponent(BigDecimal num) {
//		StringBuilder sb = new StringBuilder(toHex(num.toString()));
//		return Integer.valueOf(sb.substring(sb.indexOf("p") + 1));
		return toInteger(num.toString()).and(getExponentBitMask()).shiftRight(significandBits - 1)
				.subtract(BigInteger.valueOf(getBias())).intValue();
	}

	/*
	 * 
	 * Date: 8 Apr 2020---------------------------------------------------- Time
	 * created: 00:49:57--------------------------------------------
	 */
	/**
	 * Returns the maximum value possible in this {@code Precision} rounded with the
	 * appropriate rounding in compliance with IEEE 754 pertaining this
	 * {@code Precision}. Any value greater than this is an infinity value
	 * 
	 * @return the max value of this {@code Precision}
	 */
	public final BigDecimal getMaxValue() {
		return maxValue();
	}

	/*
	 * 
	 * Date: 8 Apr 2020---------------------------------------------------- Time
	 * created: 00:40:00--------------------------------------------
	 */
	/**
	 * Returns the smallest non-negative, non-zero normal number rounded with the
	 * appropriate rounding in compliance with IEEE 754 pertaining this
	 * {@code Precision}. Any number smaller than this is a subnormal number.
	 * 
	 * @return the smallest non-negative, non-zero normal value as
	 *         a{@code BigDecimal}
	 */
	public final BigDecimal getMinNormal() {
		return minNormal();
	}

	/*
	 * 
	 * Date: 8 Apr 2020---------------------------------------------------- Time
	 * created: 00:52:29--------------------------------------------
	 */
	/**
	 * Returns the smallest non-zero, non-negative value rounded to just 2
	 * significant figure. Any value whose magnitude is lesser than this is NaN.
	 * 
	 * @return the smallest non-zero, non-negative value
	 */
	public final BigDecimal getMinValue() {
		return minValue();
	}

	/*
	 * 
	 * Date: 8 Apr 2020---------------------------------------------------- Time
	 * created: 10:45:55--------------------------------------------
	 */
	/**
	 * Return the bit layout of the {@code String} argument in accordance with IEEE
	 * 754 floating point rules. The actual number returned has a bit pattern(in
	 * <i>big-endian</i> format) identical to the IEEE 754 pattern of the argument
	 * in relation to the {@code Precision}. Both {@code NaN} and {@code Infinity}
	 * are represented by this method, although {@code sNaN} and {@code qNaN} will
	 * have the same bit layout while positive and negative {@code Infinity} will
	 * each have their own layout.
	 * <p>
	 * For {@code UNLTD} precision, this method will might become undefined.
	 * 
	 * @param decimalFraction the fraction to be converted to IEEE 754 bit
	 *                        representation.
	 * @implNote There is a special case whereby the number kind of very small (i.e
	 *           with a negative exponent) and the mantissa part of it's bicimal is
	 *           filled with ones but it does fail to approximate because the
	 *           character at the index of approximation is the digit 0. For example
	 *           this can be found when the min normal for <code>SINGLE</code>
	 *           <code>Precision</code> is converted to {@code DOUBLE}
	 *           <code>Precision</code>. This does not only affect the bridge
	 *           between {@code DOUBLE} and {@code SINGLE}, but also other similar
	 *           bridges as well. I plan to resolve this issue by writing a method
	 *           that round the least significant bits of the bicimal if there is a
	 *           zero in them, else return the whole thing.
	 * @return the bit pattern of the {@code String} floating-point decimal as a
	 *         {@code BigInteger}
	 */
	public final BigInteger toInteger(String decimalFraction) {
		/* Parse to java's BigDecimal */
		BigDecimal num = new BigDecimal(decimalFraction);
		int sign = decimalFraction.charAt(0) == '-' ? -1 : num.signum();// store the sign
		/* If num is zero, just return num */
		if (num.signum() == 0)
			return new BigInteger(iEEESign(sign).concat(zeros(getBitLength() - 1)), 2);

		/* Remove the sign for the string parsing */
		return returnBigInteger(num.abs(), sign);
	}

	/*
	 * Date: 17 Apr 2020---------------------------------------------------- Time
	 * created: 19:47:14--------------------------------------------
	 */
	/**
	 * Returns the IEEE 754 decimal value corresponding to the integer argument. If
	 * the argument is signed as negative, then it is treated as a two's complement
	 * integer.
	 * <p>
	 * If the integer argument is to return a number whose magnitude is greater than
	 * the max value of this {@code Precision}, then max value is returned. If the
	 * integer argument is to return a value whose magnitude is lesser than min
	 * value, then min value is returned. This method will not return negative zeros
	 * and might also need a check for values whose magnitude is greater than
	 * {@link #getMaxValue()} or less than {@link #getMinValue()}. The bit must be
	 * in big-endian byte order else the behaviour of this method will be the same
	 * as if the bit is in big-endian byte order.<br>
	 * </p>
	 * 
	 * @param bits a {@code BigInteger} representing the bit pattern to be
	 *             converted.
	 * @return a {@code BigDecimal} whose value is equal (in terms of IEEE 754
	 *         conventions) to the bit pattern provided.
	 * @throws ValueException     if there is an anomaly in the bit pattern, i.e the
	 *                            bit pattern is unnatural.
	 * @throws PrecisionException if the natural bit length of the argument is
	 *                            greater than the length of this {@code Precision}.
	 */
	public final BigDecimal toDecimal(BigInteger bits) {

		if (bits.signum() < 0)
			bits = fromDecimal(bits, BinaryRep.TWO_C, BitLength.valueOf(length));
		if (bits.bitLength() > length)
			new PrecisionException(BITS_GREATER_THAN_FOUND);
		if (bits.compareTo(BigInteger.ZERO) == 0)
			return BigDecimal.ZERO;
		String intBits = new StringBuilder(bits.toString(2)).reverse().toString();
		if (intBits.length() == length && new BigInteger(intBits, 2).compareTo(BigInteger.ONE) == 0)
			return new BigDecimal("-0.0");
		if (intBits.length() < length)
			intBits = new StringBuilder(intBits).append(zeros(length - intBits.length())).toString();
		else if (intBits.length() > length)
			intBits = intBits.substring(0, length);

		String exponent = new StringBuilder(intBits.substring(significandBits - 1, intBits.length() - 1)).reverse()
				.toString();
		String sign = intBits.substring(intBits.length() - 1);
		if (sign.length() > 1)// redundant check. Does nothing
			new BaseException(ExceptionMessage.EMPTY, new RuntimeException());
		int exp = Integer.parseInt(exponent, 2) - getBias();
		final boolean isSubnormal = (Integer.parseInt(exponent, 2) == 0);
		if (isSubnormal)
			exp = getMinExponent();

		String mantissa = new StringBuilder(intBits.substring(0, significandBits - 1)).reverse().toString();
		final boolean isNaN = (isSubnormal && (mantissa.indexOf("1") < 0 || mantissa.indexOf("0") < 0));
		final boolean isInfinite = (exponent.indexOf("0") < 0) && (mantissa.indexOf("1") < 0);
		BigDecimal binIndex = BigDecimal.ZERO;
		for (int i = 0; i < mantissa.length() - 1; i++) {
			String binChar = Character.toString(mantissa.charAt(i));
			BigInteger binDigit = new BigInteger(binChar, 2);
			BigDecimal decDigit = new BigDecimal(binDigit);
			int n = Integer.parseInt("-" + (i + 1));
			BigDecimal dyadic = new BigDecimal("2", getMathContext()).pow(n,
					length <= 128 ? MathContext.DECIMAL128 : getMathContext());
			binIndex = binIndex.add(decDigit.multiply(dyadic), getMathContext());
		}
		mantissa = binIndex.toPlainString();
		mantissa = mantissa.substring(mantissa.indexOf(".") + 1);
		mantissa = isSubnormal ? "0." + mantissa : "1." + mantissa;

		binIndex = new BigDecimal(mantissa);
		binIndex = binIndex.multiply(new BigDecimal("2").pow(exp, getMathContext()), getMathContext());

		if (sign.charAt(0) == '1')
			binIndex = binIndex.negate(getMathContext());

		if (isNaN)
			return (sign.charAt(0) == '1' ? getMinValue().negate(getMathContext()) : getMinValue());
		if (isInfinite) {
			if (sign.charAt(0) == '1')
				return getMaxValue().negate(getMathContext());
			return getMaxValue();
		}
		if (binIndex.abs().compareTo(getMinValue()) < 0 && binIndex.compareTo(BigDecimal.ZERO) != 0)
			return getMinValue();

		return binIndex;
	}

	/*
	 * Date: 14 Apr 2020---------------------------------------------------- Time
	 * created: 11:10:57--------------------------------------------
	 */
	/**
	 * Returns the IEEE 754 normalised hexadecimal representation of the argument as
	 * a {@code String}. The {@code String} returned is in the form: [implicit
	 * bit].[hex fraction]p[binary exponent]. A radix point always comes after the
	 * implicit bit and there is always a 'p' (2 to the power of), although the
	 * power might be 0 at 1 and 0.
	 * <p>
	 * When the subnormal is reached, leading zeros will begin to appear in the
	 * fraction part, the exponent will be fixed at {@link #getMinExponent()} and
	 * the implicit bit will be 0. When the argument's value is &#0177infinity,
	 * &#0177;<code>1.0p[max exponent]</code> is returned. When the argument's
	 * absolute value is less than the minimum subnormal value, then
	 * &#0177<code>0.0p0</code> is returned, this is also returned if the argument
	 * is zero
	 * 
	 * @param decimal a {@code String} decimal fraction.
	 * @return a normalised hex floating point as a {@code String}
	 */
	public String toHex(String decimal) {
		if (checkNormalised(decimal))
			return decimal;
		return toHex(toInteger(decimal), isSubnormal(new BigDecimal(decimal)));
	}

	/*
	 * Date: 16 Apr 2020---------------------------------------------------- Time
	 * created: 16:46:54--------------------------------------------
	 */
	/**
	 * Returns a normalised IEEE 754 notational number in the specified radix. The
	 * number returned is in the form: the implicit bit followed by the radix point
	 * followed by the IEEE 754 interpretation of the mantissa in the specified
	 * radix followed by the binary exponent.
	 * <p>
	 * If the decimal is already in normalised form, the argument {@code num} is
	 * returned exactly regardless of the radix
	 * </p>
	 * 
	 * @param num   the number as an unnormalised decimal string
	 * @param radix the {@code Radix} to which the number is to be converted.
	 * @return the IEEE 754 normalised notation for {@code num} in the specified
	 *         {@code Radix}.
	 */
	public String normalise(String num, Radix radix) {
		if (checkNormalised(num))
			return num;
		String hex = toHex(num);
		switch (radix) {
		case DEC:
			return toDec(hex);
		case HEX:
			return hex;
		case OCT:
			return octMantissa(normalise(num, Radix.DEC));
		default:
			BigInteger bg = toInteger(num);
//			System.out.println("BigInteger: "+bg.bitLength());
			if (bg.signum() == 0)
				return "0.0p0";
			String signif = new StringBuilder(bg.toString(2)).reverse().toString();
			if (bg.bitLength() == length && BigInteger.ONE.compareTo(new BigInteger(signif, 2)) == 0)
				return "-0.0p0";
			final boolean isNegative = (signif.length() == length && signif.charAt(length - 1) == '1');
			signif = signif.substring(0, signif.length() < significandBits ? signif.length() : significandBits - 1);
			return (isNegative ? "-" : "") + (isSubnormal(new BigDecimal(num)) ? "0." : "1.")
			/* + stripTrailingZerosAndReturn(new StringBuilder(signif).reverse() + "") */
					+ stripTrailingZeros(new StringBuilder(signif).reverse() + "", radix.getRadix(), true)
					+ hex.substring(hex.indexOf("p"));
		}
	}

	/*
	 * 
	 * Date: 7 Apr 2020---------------------------------------------------- Time
	 * created: 16:15:10--------------------------------------------
	 */
	/**
	 * Returns the <code>MathContext</code> object that is used for correct rounding
	 * of this {@code Precision}
	 * 
	 * @return the {@code MathContext} object appropriate for handling rounding in
	 *         this {@code Precision}
	 */
	public final MathContext getMathContext() {
		/*
		 * round log(2 ^ significandBits) to get the max number of significant digits in
		 * the decimal fraction.
		 */
		return new MathContext((int) round(Math.log10(Math.scalb(1.0d, significandBits))) + 1, RoundingMode.HALF_EVEN);
	}

	/*
	 * Date: 27 Aug 2020-----------------------------------------------------------
	 * Time created: 00:28:54--------------------------------------------
	 */
	/**
	 * Returns the size of an ulp of the argument. An ulp, unit in the last place,
	 * of this {@code Precision} value is the positive distance between this
	 * floating-point value and the {@code
	 * Precision} value next larger in magnitude. Note that for any non-NaN <i>x</i>,
	 * <code>ulp(-<i>x</i>) == ulp(<i>x</i>)</code>.
	 *
	 * @param num the floating-point value whose ulp is to be returned
	 * @return the size of an ulp of the argument
	 */
	public BigDecimal ulp(BigDecimal num) {
		int exp = getExponent(num);

		if (exp == (getMaxExponent() + 1))
			return num.abs();
		else if (exp == (getMinExponent() - 1))
			return getMinValue();

		exp = exp - (significandBits - 1);
		if (exp >= getMinExponent())
			return powerOf2(exp);

		return toDecimal(BigInteger.ONE.shiftLeft(exp - (getMinExponent() - (significandBits - 1))));
	}

	////////////////////////////////////////////////////////////
	//////////////////// Private methods //////////////////////
	//////////////////////////////////////////////////////////

	private BigDecimal powerOf2(int exp) {
		return toDecimal(BigInteger.valueOf(exp).add(BigInteger.valueOf(getBias())).shiftLeft(significandBits - 1)
				.and(getExponentBitMask()));
	}

	/*
	 * Date: 8 Apr 2020---------------------------------------------------- Time
	 * created: 09:03:29--------------------------------------------
	 */
	/**
	 * Delegate method for {@code getMaxValue()}. Returns the value of
	 * 2<sup>getMaxExponent()</sup> rounded with the appropriate rounding in
	 * compliance with IEEE 754 pertaining this {@code Precision}
	 * 
	 * @return the max value of this {@code Precision}
	 */
	@SuppressWarnings("cast")
	private BigDecimal maxValue() {
		if (this instanceof Serializable) {
			switch (length) {
			case 8:
				break;
			case 16:
				return new BigDecimal("6.552E+4", getMathContext());
			case 64:
				return new BigDecimal("1.7976931348623157E+308", getMathContext());
			case 80:
				return new BigDecimal("1.18973149535723176505E+4932", getMathContext());
			case 128:
				return new BigDecimal("1.1897314953572317650857593266280071E+4932", getMathContext());
			case 256:
				return new BigDecimal(
						"1.6113257174857604736195721184520050" + "1064402387454966951747637125049607183E+78913",
						getMathContext());
			default:
				return new BigDecimal("3.4028235E+38", getMathContext());
			}
		}
		return new BigDecimal("2").pow(getMaxExponent() + 1, getMathContext());
	}

	/*
	 * 
	 * Date: 8 Apr 2020---------------------------------------------------- Time
	 * created: 08:49:08--------------------------------------------
	 */
	/**
	 * Delegate for {@code getMinNormal()}. Returns
	 * 2<sup>getMinExponent()</sup>.<br>
	 * It does this by continually dividing 1 by two until the loop counter
	 * reaches<br>
	 * the magnitude of {@code getMinExponent()}
	 * 
	 * @return the min normal value
	 */
	@SuppressWarnings("cast")
	private BigDecimal minNormal() {
		if (this instanceof Serializable) {
			switch (length) {
			case 8:
				break;
			case 16:
				return new BigDecimal("6.1E-5", getMathContext());
			case 64:
				return new BigDecimal("2.2250738585072014E-308", getMathContext());
			case 80:
				return new BigDecimal("3.3621031431120935061E-4932", getMathContext());
			case 128:
				return new BigDecimal("3.3621031431120935062626778173217435E-4932", getMathContext());
			case 256:
				return new BigDecimal(
						"2.482427951464349788299328222913871723" + "67768770607964686927095329791378862E-78913",
						getMathContext());
			default:
				return new BigDecimal("1.17549435E-38");
			}
		}
		BigDecimal one = BigDecimal.ONE;
		for (int i = 0; i < Math.abs(getMinExponent()); i++)
			/* Continue division by two until min exponent is reached */
			one = one.divide(new BigDecimal("2"), getMathContext());
		return one;// then return the answer
	}

	/*
	 * Date: 8 Apr 2020---------------------------------------------------- Time
	 * created: 08:56:47--------------------------------------------
	 */
	/**
	 * Delegate for {@code minValue()}. Return 2<sup>getMinExponent() +
	 * (getSignificand() - 1)</sup>. It does this by continually dividing 1 by
	 * the above power of 2.
	 * 
	 * @return the min value
	 */
	@SuppressWarnings("cast")
	private BigDecimal minValue() {
		if (this instanceof Serializable) {
			switch (length) {
			case 8:
				break;
			case 16:
				return new BigDecimal("6.0E-8", getMathContext());
			case 64:
				return new BigDecimal("4.9E-324", getMathContext());
			case 80:
				return new BigDecimal("3.6E-4951", getMathContext());
			case 128:
				return new BigDecimal("6.5E-4966", getMathContext());
			case 256:
				return new BigDecimal("2.2E-78984", getMathContext());
			default:
				return new BigDecimal("1.4E-45", getMathContext());
			}
		}
		BigDecimal one = BigDecimal.ONE;
		for (int i = 0; i < Math.abs(getMinExponent()) + (getSignificandBits() - 1); i++)
			/* Continue division by two until min exponent is reached */
			one = one.divide(new BigDecimal("2"), getMathContext());
		/* round answer to 1 mantissa digit */
		return one.round(new MathContext(2, RoundingMode.HALF_EVEN));
//		return one;
	}

	/*
	 * Round to an integer using half-even rounding Date: 6 Apr
	 * 2020---------------------------------------------------- Time created:
	 * 10:00:34--------------------------------------------
	 */
	/**
	 * Rounds a double to an integer and return it as a double using the half-even
	 * algorithm.
	 * 
	 * @param a the number to be rounded
	 * @return the argument rounded to an integer using half-even rounding
	 */
	static double round(double a) {
		/* If the argument is already an integer */
		if (Fraction.isInteger(a))
			return a;
		/* Avoid any exponent notation */
		String num = new BigDecimal(Double.toString(a)).toPlainString();
		/* Let cc be a char array of the fractional digits */
		char[] cc = num.substring(num.indexOf(".") + 1).toCharArray();
		/* let a be the integer part of the fraction */
		a = Double.parseDouble(num.substring(0, num.indexOf(".")));
		byte carry = 0;// for carry-over arithmetic
		/* Start iteration and traversal of the array backwards */
		for (int i = (cc.length - 1); i >= 0; i--) {
			/* Add the carry-over to current index */
			byte b = (byte) (Byte.parseByte(Character.toString(cc[i])) + carry);
			if (b > 4)// Let the carry-over be 1 if current index is greater than 4
				carry = 1;
			else// else reset it to 0
				carry = 0;
		}
//		if (carry > 0)
		/* If there is still any carry-over, add it to a and restore the sign */
		a = (Math.abs(a) + carry) * (Math.copySign(1.0, a) < 0 ? -1 : 1);
		return a;
	}

	/*
	 * Date: 16 Jun 2020-----------------------------------------------------------
	 * Time created: 21:52:06--------------------------------------------
	 */
	/**
	 * Gets the bit mask that isolates the sign bit of this {@code Precision} during
	 * the bitwise operation "AND" with the IEEE 754 integer. This bit mask has the
	 * same length as the length of this {@code Precision} with all bits off except
	 * the most significant (left-most) bit.
	 * 
	 * @return the significand bit mask
	 */
	final BigInteger getSignBitMask() {
		/* All the bits are off except the sign bit */
		return new BigInteger(1 + zeros(length - 1), 2);
	}

	/*
	 * Date: 16 Jun 2020-----------------------------------------------------------
	 * Time created: 21:52:12--------------------------------------------
	 */
	/**
	 * Gets the bit mask that isolates the exponent bits of this {@code Precision}
	 * during the bitwise operation "AND" with the IEEE 754 integer. This mask has
	 * all it's bits off except the bits thats that align with the exponent of the
	 * integer representation of this {@code Precision} number.
	 * 
	 * @return the exponent part bit mask
	 */
	final BigInteger getExponentBitMask() {
		/* Place holder for ones in the exponent bit mask */
//		StringBuilder sb = new StringBuilder(length);
//		for (int i = 0; i < exponent; i++)
//			sb.append("1");
		/*
		 * In the form of all the bits are off except the bits that align with the
		 * exponent
		 */
//		return new BigInteger(sb.toString() + zeros(significandBits - 1), 2);
		return new BigInteger(String.format("%1$s%2$s", Utility.string('1', exponent), zeros(significandBits - 1)), 2);
	}

	/*
	 * Date: 16 Jun 2020-----------------------------------------------------------
	 * Time created: 21:52:20--------------------------------------------
	 */
	/**
	 * Gets the bit mask that isolates the significand bits of this
	 * {@code Precision} during the bitwise operation "AND" with the IEEE 754
	 * integer. This is an all ones bitmask whose length is the same as
	 * <code>getSignificandBits() - 1</code>
	 * 
	 * @return the significand bit mask
	 */
	final BigInteger getSignificandBitMask() {
		/* Place holder for ones in the significand bit mask */
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < significandBits - 1; i++)
			sb.append("1");

		/* All the bits are off except the bits that align with significand */
		return new BigInteger(sb.toString(), 2);
	}

	/*
	 * Date: 14 Apr 2020---------------------------------------------------- Time
	 * created: 11:23:01--------------------------------------------
	 */
	/**
	 * Removes a trailing zeros of a fractional part of a number. This method is a
	 * delegate to several other methods
	 * <p>
	 * <b>NOTE:</b> This method was made static not by choice but because it is
	 * called inside an anonymous class and java requires methods called inside
	 * anonymous classes to be made <code>static</code>.
	 * 
	 * @param num a fractional mantissa (not the total normalised fraction!)
	 * @return a number that has been stripped of its trailing zeros
	 */
	@SuppressWarnings("unused")
	private static String stripTrailingZerosAndReturn(String num) {
		/* Initialise the buffer with the string argument */
		StringBuilder sb = new StringBuilder(num);
		/* loop through the buffer from behind */
		for (int i = num.length() - 1; i >= 0; i--) {
			/* If a zero is found from behind, */
			if (num.charAt(i) == '0')
				/* Delete the digit at the last index in the buffer */
				sb.deleteCharAt(sb.length() - 1);
			else {
				/*
				 * if the buffer is empty (as a result of accidentally deleting all the digits)
				 * or the iteration has hit a decimal point
				 */
				if (num.isEmpty() || num.charAt(i) == '.')
					sb.append("0");// append the lost zero
				break;
			}
		}
		return sb.toString();
	}

	/*
	 * Date: 15 Apr 2020---------------------------------------------------- Time
	 * created: 17:50:27--------------------------------------------
	 */
	/**
	 * Rounds the binary string argument and returns the rounded binary as a string.
	 * This method is to be used for rounding bicimal whose absolute value is grater
	 * than zero but less than one. If the bit immediately after the bicimal's
	 * significand bits is one, then the binary is rounded else it is returned. This
	 * method is a delegate for {@link #toBicimal(BigDecimal, boolean)}. This method
	 * does not apply standard floating point rounding rules.
	 * 
	 * @param bin the bicimal to be rounded
	 * @return a string binary rounded properly
	 */
	private String roundTrip(String bin) {
		/* Mask to cover trailing zeros */
//		String mask = new BigDecimal("1E" + Math.abs(getMinExponent() - (significandBits + 4))).toBigInteger()
//				.toString();
		String mask = "1" + zeros(Math.abs(getMinExponent() - (significandBits + 4)));
		mask = new BigInteger(mask, 2).or(new BigInteger(bin, 2)).toString(2);
		mask = new BigInteger(mask, 2).add(new BigInteger(Character.toString(mask.charAt(mask.length() - 1))))
				.toString(2);
		return mask.substring(mask.length() - bin.length());
	}

	/*
	 * I do not yet have use for this
	 * method.-------------------------------------------- Date: 15 Apr
	 * 2020---------------------------------------------------- Time created:
	 * 18:00:25--------------------------------------------
	 */
	/**
	 * @param a
	 * @return
	 */
	static int getDecimalExponent(BigDecimal a) {
		if (BigDecimal.ZERO.compareTo(a) == 0)
			return 0;
		if (a.toString().contains("E"))
			return Integer.valueOf(a.toString().substring(a.toString().indexOf("E") + 1));
		StringBuilder sb = new StringBuilder(a.abs().toPlainString());
		if (sb.indexOf(".") < 0)// if there is no decimal point
			sb.append(".00");// add it to the number
		if (sb.charAt(0) == '0' || sb.charAt(0) == '.') {
			/* Return a negative exponent */
			return 1 - sb.indexOf(
					Character.toString(new BigDecimal(sb.substring(sb.indexOf(".") + 1)).toString().charAt(0)));
		} else if (sb.indexOf(".") <= 1)
			return 1;
		else
			return sb.indexOf(".") - 1;
	}

	/*
	 * Date: 8 Apr 2020----------------------------------------------------------
	 * Time created:
	 * 09:51:49-------------------------------------------------------- TODO : This
	 * method takes about 36 seconds (on OCTUPLE.getMinValue()) to execute on my
	 * laptop should be much less............................... Okay, so heres the
	 * thing, I have tested the approximation algorithm extensively (more or less)
	 * and I found out that java's approximation of floating point value depends on
	 * whether the value is percieved (cast) to be a double or float, as these two
	 * have different return values. For example:.............. float value =
	 * Float.MIN_NORMAL;............................................. double dValue
	 * = Double.parseDouble(String.valueOf(value));.................. String s1 =
	 * Double.toHexString(value);// perceived as a float............... String s2 =
	 * Double.toHexString(dValue);// cast to a double.................. From the
	 * above Strings s1 & s2, both will definitely be different values (via
	 * Collator.getInstance().equals(s1, s2) returns false) because the arguments
	 * casted as a double (1.1754943508222875E-38) has more digits (significant
	 * fractional digits) than the value perceived as a float (1.17549435E-38).
	 */
	/**
	 * Converts a fraction to a bicimal string. A bicimal is a binary fraction as
	 * derived from it's decimal counterpart. If the bicimal's mantissa(fractional
	 * digits) is recurring, then the mantissa part is truncated to fit the number
	 * of significant digits. If the mantissa part has leading zeros and the number
	 * of it's leading zeros have surpassed the significand of this
	 * {@code Precision}, then the algorithm searches for the remaining non-zero
	 * mantissa of the bicimal and the returns it according to the number of digits
	 * in this {@code Precision}'s significand with 2 extra digits.
	 * <p>
	 * <b>NOTE: Any signed decimal put in as an argument will loose it's sign </b>
	 * </p>
	 * 
	 * @param n           the positive decimal fraction to be converted.
	 * @param useRounding specifies whether or not to use internal rounding for
	 *                    approximating values during calculation. This helps
	 *                    subnormal numbers accuracy
	 * @return a bicimal converted from a decimal fraction
	 */
	private String toBicimal(BigDecimal n, boolean useRounding) {
		n = useRounding ? n.abs(getMathContext()) : n.abs();// destroy any negative sign.
		/* Buffer for storage of the converted raw binary i.e the bicimal */
		StringBuilder raw = new StringBuilder(length + DYADIC_BITS);
		/* Stores the integer part in the specified base */
		raw.append(n.toBigInteger().toString(2));
		/*
		 * Be certain to subtract the integer part from the fractional part so that the
		 * integer part of the number becomes 0
		 */
		n = useRounding ? n.subtract(new BigDecimal(n.toBigInteger()), getMathContext())
				: n.subtract(new BigDecimal(n.toBigInteger()));
		/* Just in case there is a non-zero fraction remainder */
		boolean notZero = n.abs().compareTo(BigDecimal.ZERO) > 0;
		if (notZero) {
			raw.append(".");// It is a fraction therefore append the decimal point
			while (notZero) {// if n != 0
				/* n = n * dyadic */
				n = useRounding ? n.multiply(DYADIC_BASE, getMathContext()) : n.multiply(DYADIC_BASE);
				/* Stores the integer part in the specified base */
				String binary = n.toBigInteger().toString(2);
				int toAppend = DYADIC_BITS - binary.length();
				String zeros = zeros(toAppend).concat(binary);
				raw.append(zeros);
				/*
				 * Be certain to subtract the integer part from the fractional part so that the
				 * integer part of the number becomes 0
				 */
				n = useRounding ? n.subtract(new BigDecimal(n.toBigInteger()), getMathContext())
						: n.subtract(new BigDecimal(n.toBigInteger()));
				notZero = n.abs().compareTo(BigDecimal.ZERO) > 0;

				try {
					/* If the number of significand bits is satisfied break from the loop */
					if (raw.substring(raw.indexOf("1")).length() >= getSignificandBits())
						break;
				} catch (@SuppressWarnings("unused") IndexOutOfBoundsException e) {
					// Nothing to catch
				}
			} // End of while statement
		} // End of if statement
		/* Approximation time */
		try {
			/*
			 * If the first digit on the bicimal is 0 then we know we are dealing with a
			 * non-negative, non-zero number lesser than 1
			 */
			if (raw.charAt(0) == '0') {
				/* Truncate the bicimal and prep it for rounding */
				String rounded = raw.substring(raw.indexOf(".") + 1, // all bits after the radix point
						raw.indexOf("1") + 1 > Math.abs(getMinExponent())// If it is subnormal...
								/* return the subnormal bits */
								? Math.abs(getMinExponent() - significandBits) + 4
								/*
								 * Else return all the bits from the point to the bit in the position of the
								 * significand's least significant bit plus an extra four bits for rounding
								 * purposes
								 */
								: raw.indexOf("1") + significandBits + 4);

				rounded = roundTrip(rounded);
				/* Delete the un-rounded mantissa and append the rounded mantissa */
				raw.delete(2, raw.length()).append(rounded);
			} else {
				/*
				 * i do not think that numbers whose magnitude (absolute value) is greater than
				 * or equal to 1 needs approximation
				 */
			} // End else
		} catch (@SuppressWarnings("unused") IndexOutOfBoundsException e) {
			// Do nothing
		} // End catch
		String rv = raw.toString();// Return value
		return rv;
	}

	/*
	 * Date: 16 Apr 2020---------------------------------------------------- Time
	 * created: 17:14:20--------------------------------------------
	 */
	/**
	 * formats a normalised decimal {@code String} to a normalised octagonal
	 * {@code String}
	 * 
	 * @param normalisedDecimal the normalised {@code String} to be formatted
	 * @return an octagonal number in normalised IEEE 754 form
	 */
	private String octMantissa(String normalisedDecimal) {
		String octMantissa = normalisedDecimal.substring(normalisedDecimal.indexOf(".") + 1,
				normalisedDecimal.indexOf("p"));
		if (octMantissa.isEmpty())
			octMantissa = "0";
		BigDecimal dec = new BigDecimal("0." + octMantissa);
		String recurse = fromDecimal(dec, Radix.OCT, significandBits - 1);
		recurse = recurse.substring(recurse.indexOf(".") + 1)/* .substring(1) */;
		String prefix = normalisedDecimal.substring(0, normalisedDecimal.indexOf(".") + 1);
		String suffix = normalisedDecimal.substring(normalisedDecimal.indexOf("p"));
		return prefix.concat(recurse).concat(suffix);

	}

	/*
	 * Date: 16 Apr 2020---------------------------------------------------- Time
	 * created: 10:01:54--------------------------------------------
	 */
	/**
	 * Recursively retrieves the octal number representation of the decimal
	 * {@code dec}. The decimal's absolute value is less than 1 but greater than 0.
	 * <p>
	 * <b>NOTE:</b>The number {@code String} returned by this method has a single
	 * leading zero in it's mantissa part which should be discarded for the result
	 * to be accurate.
	 * </p>
	 * 
	 * @param dec        the decimal to be converted
	 * @param concat     the octal {@code String} whose digits will be concatenated
	 *                   to form the result.
	 * @param numRecurse the number of recursions this method will carry out if the
	 *                   number is recurring, this value is related to the number of
	 *                   octal digits this {@code Precision}'s mantissa can have.
	 * @return an octal number numerically equal to the argument {@code dec}
	 */
	@SuppressWarnings("unused")
	private String recurserOct(BigDecimal dec, String concat, int numRecurse) {
		BigInteger bg = dec.toBigInteger();
		dec = dec.subtract(new BigDecimal(bg));
		dec = dec.multiply(new BigDecimal("8"));
		StringBuilder sb = new StringBuilder(concat);
		sb.append(bg);
		if (dec.compareTo(BigDecimal.ZERO) == 0 || numRecurse <= 0)
			return "0." + concat;
		return recurserOct(dec, sb.toString(), numRecurse -= 1);
	}

	////////////////////////////////////////////////////////////
	////////////////// IEEE 754 methods ///////////////////////
	//////////////////////////////////////////////////////////

	/*
	 * Date: 10 Apr 2020---------------------------------------------------- Time
	 * created: 23:46:24--------------------------------------------
	 */
	/**
	 * Returns an IEEE 754 floating-point hexadecimal {@code String} that uses 'p'
	 * for it's binary (i.e power of 2) exponent. The string that is returned can be
	 * thought of as an IEEE 754's "<i>Scientific notation</i>" for hexadecimals.
	 * <p>
	 * If argument {@code num}'s bit length is greater than the current length then
	 * it is truncated to the current bit length allowed for this {@code Precision}.
	 * For example, inputing {@code 50000000} as a value for num (whose pattern is
	 * greater than 16 bits) using the constant {@code HALF} will cause the bit
	 * pattern within it to be truncated from the left, as such the value returned
	 * will be equal to that which is returned if the truncated value was the bit
	 * pattern of the argument {@code num}.
	 * 
	 * @param num       the positive number (in IEEE 754 bit layout) to be
	 *                  converted. If this number is negative, it will have no
	 *                  effect on the final result and this method will proceed as
	 *                  if the number was positive.
	 * @param subnormal
	 * @return a hexadecimal IEEE 754 floating-point notational {@code String}
	 */
	private String toHex(BigInteger num, boolean subnormal) {
		num = num.abs();
		/* If it is a positive zero */
		if (num.compareTo(BigInteger.ZERO) == 0)
			return "0.0p0";

		/* convert to binary to 'see' all the bit positions in the bit layout */
		StringBuilder binary = new StringBuilder(num.toString(2));
		if (binary.length() > length)
			binary.delete(0, binary.length() - length);
		else if (binary.length() < length)
//			binary.insert(0, new BigDecimal("1E" + (length - binary.length())).toBigInteger().toString().substring(1));
			binary.insert(0, zeros((length - binary.length())));
		String reversed = new StringBuilder(binary.toString()).reverse().toString();

		/* It is negative if the first binary string index is 1 */
		boolean isNegative = (binary.length() == length && binary.charAt(0) == '1');
		/* buffer for the hex floating point string */
		StringBuilder floatingPoint = new StringBuilder();

		/* A one time mask to enable the retaining of leading zeros */
//		BigInteger outerMask = new BigInteger(new BigDecimal("1E" + (length - 4)).toBigInteger().toString(), 2);
		BigInteger outerMask = new BigInteger("1" + zeros(length - 4), 2);
		/*
		 * The XOR of num and the significand bit mask (see method doc) then the OR of
		 * that and the retaining mask then parse to a hex string. This string is the
		 * actual string with some extra load.
		 */
		String hexBits = num.and(getSignificandBitMask()).or(outerMask).toString(16);
//		int hexLength = (int) round((double) ((significandBits - 1) / 4.0));
		/* Throw away the extra load and reveal the pure hex string */
		hexBits = hexBits.substring(hexBits.length() - (int) round((significandBits - 1) / 4.0));
		/* if the hex string is a zero discard all the zeros leaving just one zero */
		if (BigInteger.ZERO.compareTo(new BigInteger(hexBits, 16)) == 0)
			floatingPoint.append("0");
		else
//			floatingPoint.append(stripTrailingZerosAndReturn(hexBits));
			floatingPoint.append(stripTrailingZeros(hexBits, 16, !hexBits.contains(".")));

		/* Work on the exponent string */
		String expon = reversed.length() < significandBits// If there is no exponent part
				/* then fill the exponent with zeros because it's probably 0 */
				? /* new BigDecimal("1E" + exponent).toBigInteger().toString().substring(1) */
				zeros(exponent)
				: isNegative// if the sign bit is one
						/*
						 * return all the bits starting from the significand index to just befor the
						 * sign index
						 */
						? reversed.substring(significandBits - 1, length - 1)
						/* else just return all the remaining exponent bits */
						: reversed.substring(significandBits - 1);
		/* reverse the exponent string to get the correct bit pattern */
		expon = new StringBuilder(expon).reverse().toString();
		floatingPoint.insert(0, (subnormal)// is the exponent all zeros
				? "0."// If yes, leading bit is 0
				: "1.");// else, leading bit is 1
		/* Assign a decimal string as the exponent with the prefix 'p' */
		expon = (expon.indexOf("1") < 0)// If exponent is all zeros,
				? Integer.toString(getMinExponent())// then assign the subnormal exponent to expon.
				: (expon.indexOf("0") < 0)// If exponent is all ones,
						? Integer.toString(getMaxExponent())// assign the max exponent
						/* else subtract the integer value from the exponent bias */
						: Integer.toString(Integer.valueOf(expon, 2) - getBias());
		floatingPoint.append("p".concat(expon));// 'p' stands for "power of 2"

		floatingPoint.insert(0, isNegative ? "-" : "");

		return floatingPoint.toString();
	}

	/*
	 * Date: 16 Apr 2020---------------------------------------------------- Time
	 * created: 12:04:30--------------------------------------------
	 */
	/**
	 * Converts the normalised hexadecimal string argument to a decimal. The hex
	 * string is in IEEE 754 normalised form, and so also is the string to be
	 * returned. The algorithm used is the same as the secondary school algorithm
	 * for converting number of other radixes to decimal. 'normalised' here means
	 * that the fraction has been decomposed to a fraction such that when multiplied
	 * by 2 to the power of its exponent, yields the same result as the unnormalised
	 * decimal fraction.
	 * 
	 * 
	 * @param hexString a hex string in IEEE 754 normalised form.
	 * @return a normalised (IEEE 754 convention) decimal fraction.
	 */
	private String toDec(String hexString) {
		String hex = hexString.substring(hexString.indexOf(".") + 1, hexString.indexOf("p"));
		if (hex.isEmpty())
			hex = "0";
		BigDecimal hexIndex = BigDecimal.ZERO;
		for (int i = 1; i <= hex.length(); i++) {
			String hexChar = Character.toString(hex.charAt(i - 1));
			BigInteger hexDigit = new BigInteger(hexChar, 16);
			BigDecimal decDigit = new BigDecimal(hexDigit);
			int n = Integer.parseInt("-" + i);
			BigDecimal dyadic = new BigDecimal("16", getMathContext()).pow(n, getMathContext());
			hexIndex = hexIndex.add(decDigit.multiply(dyadic));
		}
		String dec = hexIndex.round(getMathContext()).toString();
		dec = dec.substring(dec.indexOf(".") + 1);
		return hexString.substring(0, hexString.indexOf(".") + 1) + dec + hexString.substring(hexString.indexOf("p"));
	}

	/*
	 * Date: 8 Apr 2020---------------------------------------------------- Time
	 * created: 10:39:50--------------------------------------------
	 */
	/**
	 * Gets a non zero IEEE 754 representational string that corresponds with the
	 * decimal {@code num} signing the integer with a one if <code>sign</code> is
	 * negative or a zero if {@code sign} is positive. Please see this class's doc
	 * for more details about an iEEE 754 floating point integer.
	 * 
	 * @param num  the decimal fraction to be converted
	 * @param sign the sign of the fraction
	 * @return an IEEE 754 integer with a bit pattern identical to the convention
	 *         for {@code num}.
	 */
	private BigInteger returnBigInteger(BigDecimal num, int sign) {
		if (isInfinite(num)) {
			/*
			 * return an all ones exponent, all zeros significand and the appropriate sign
			 */
			return new BigInteger(iEEESign(sign) + Integer.toBinaryString((getMaxExponent() * 2) + 1)
					+ /*
						 * new BigDecimal("1E" + (significandBits -
						 * 1)).toBigInteger().toString().substring(1), 2);
						 */
					zeros(significandBits - 1), 2);
		} else if (isNaN(num)) {
			/* return an all zeros exponent and signifcand with the appropriate sign */
			return new BigInteger(iEEESign(sign)
					+ /* new BigDecimal("1E" + exponent).toBigInteger().toString().substring(1) */zeros(exponent)
					+ /*
						 * new BigDecimal("1E" + (significandBits -
						 * 1)).toBigInteger().toString().substring(1)
						 */zeros(significandBits - 1), 2);
		}

		/*
		 * Buffer for the storage of unconverted raw bicimal. Initialises the bicimal of
		 * the number using the appropriate significand bits + 1
		 */
		StringBuilder sb = new StringBuilder(toBicimal(num, isSubnormal(num)));
		/*
		 * Buffer for the storage of converted IEEE bit pattern initialised with the
		 * sign
		 */
		StringBuilder iEEE = new StringBuilder(iEEESign(sign));
		/*
		 * If the storage buffer does not contain a decimal point, insert it. It helps
		 * string parsing
		 */
		if (sb.indexOf(".") < 0)
			sb.append(".");

		/*
		 * The current index of decimal point before the number is normalised. This is
		 * used for calculating whether the number is subnormal, overflows or not
		 */

		iEEE.append(iEEEExponent(sb.toString(), num));

		/* Remove the decimal point from it's old position */
		sb.deleteCharAt(sb.indexOf("."))
				/*
				 * Attempts normalisation of the bicimal to IEEE 754 notation, whereby
				 * 1.significandBits
				 */
				.insert(
						/* Is the first index of "1" greater than highest normalised exponent */
						(sb.indexOf("1") + 1) > Math.abs(getMinExponent())
								/*
								 * if yes, only shift the decimal point to the highest normalised exponent index
								 * within the string
								 */
								? Math.abs(getMinExponent()) + 1
								/*
								 * if no, shift the decimal point to after the first index of "1" within the
								 * string
								 */
								: (sb.indexOf("1") + 1),
						".");

		iEEE.append(iEEESignificand(sb.toString(), num));
		return new BigInteger(iEEE.toString(), 2);
	}

	/*
	 * Remember, there is always an implicit bit before the significand bit. This
	 * implicit bit is either 1(for normal numbers) or 0 (for subnormal). This
	 * implicit bit represents the 1.siginificand(which is 0.significand for
	 * subnormal numbers), ie the resting place of the decimal point after the
	 * number is normalised
	 */
	/*
	 * Date: 8 Apr 2020---------------------------------------------------- Time
	 * created: 10:44:07--------------------------------------------
	 */
	/**
	 * Returns a binary String that is the significand part of the {@code num}
	 * argument.
	 * <p>
	 * The input argument is already in normalised iEEE form.
	 * 
	 * @param normalisedIEEE the value of {@code num} in normalised iEEE form
	 * @param num            a {@code BigDecimal} number to use for checking various
	 *                       cases such as the finite qualities of the number
	 *                       involved
	 * @return an unsigned binary that is the significand part of {@code num} in
	 *         compliance with IEEE 754
	 */
	private String iEEESignificand(String normalisedIEEE, BigDecimal num) {
		String signifBits = normalisedIEEE.substring(normalisedIEEE.indexOf(".") + 1);
		/*
		 * if the absolute value overflows the current precision, then return a special
		 * set of bit by filling it with zeros
		 */
		if (isInfinite(num))
			return /*
					 * new BigDecimal("1.E" + (significandBits -
					 * 1)).toBigInteger().toString().substring(1)
					 */zeros(significandBits - 1);
		/*
		 * If the length of the significand string is less than the max significand's
		 * bits, fill with leading zeros
		 */
		if (signifBits.length() < (significandBits - 1))
			return signifBits.concat(zeros((significandBits - 1) - signifBits.length()));
		if (signifBits.length() > (significandBits - 1)) {

			/*
			 * Truncate the string to fit the number of significand bits
			 */
			/* Return an extra bit to be used for approximation */
			signifBits = signifBits.substring(0, (significandBits - 1) + 1);

			if (new BigInteger(signifBits, 2).signum() == 0)// if the significand bit is all zeros
				/*
				 * return a string populated with zeros whose length is equal to the max
				 * significand bits
				 */
				return zeros(significandBits - 1);
			signifBits = (signifBits.indexOf("0") == 0
					/*
					 * The operation on the number below will loose all leading zeros therefore if
					 * the first digit is 0, preserve the leading zeros by concatenating all the
					 * leading zeros with the number below
					 */
					? signifBits.substring(0, signifBits.indexOf("1"))
					: "")// if no, concatenate an empty string with the number below
					/* approximate any bit remaining in the extra index if it is "1" */
					+ (new BigInteger(signifBits.substring(0, (significandBits - 1)), 2)
							.add(new BigInteger(Character.toString(signifBits.charAt((significandBits - 1))), 2))
							.toString(2));
			/* Truncate the string to the max significand bit length */
			if (signifBits.length() > (significandBits - 1))
				signifBits = signifBits.substring(0, (significandBits - 1));
		}
		return signifBits;
	}

	/*
	 * Have exponents for positive and negative infinity, positive and negative NaN,
	 * sub-normal values
	 * 
	 * if the decimal point moves 0 indices and sign is -1 then make sure to return
	 * zeros which is equal in string-length to the number of exponentBits. if the
	 * sign is also -1 and the exponent's string-length is less than the number of
	 * bits in the exponent, add leading zeros else return the exact exponent
	 */
	/*
	 * Date: 8 Apr 2020---------------------------------------------------- Time
	 * created: 10:42:28--------------------------------------------
	 */
	/**
	 * Returns the exponent of the of the argument {@code bicimal} in excess
	 * notation, using the argument {@code num} as a check for special cases such as
	 * subnormal numbers, overflow underflow etc.
	 * 
	 * @param bicimal the binary fraction which has not yet been normalised
	 * @param num     a {@code BigDecimal} number to use for checking various cases
	 *                such as the finite qualities of the number involved
	 * @return the an unsigned binary of the binary exponent biased by
	 *         {@link #getBias()}
	 */
	private String iEEEExponent(String bicimal, BigDecimal num) {

		/*
		 * the number of indices that the decimal point has moved to when the bicimal
		 * has been normalised
		 */
		int pointIndex = (bicimal.indexOf(".")
				- (bicimal.indexOf("1") + (bicimal.indexOf(".") > bicimal.indexOf("1") ? 1 : 0)));

		/*
		 * if the absolute value overflows the current precision, then return a special
		 * set of bit which all an ones bit pattern whose bit length is equal to the
		 * number of exponentBits
		 */
		if (isInfinite(num))
			return Integer.toBinaryString((getMaxExponent() * 2) + 1);

		/*
		 * If the absolute value is less than the subnormal, then return a special set
		 * of bit
		 */
		if (isSubnormal(num))
			/*
			 * return a zero-ridden(full of zeros) string whose length is equal to exponent
			 * bits
			 */
			return zeros(exponent);

		String expBits = Integer.toBinaryString(getBias() + pointIndex);
		/*
		 * If the length of the binary is less than the exponents bits max exponent's
		 * bits
		 */
		if (expBits.length() < exponent)
			/*
			 * let x = exponentBits - expBits; therefore convert 1ex(where 'e' is the
			 * exponent) to a string and cut out the number in the first index the
			 * concatenate this with the former expBits.
			 */
			expBits = zeros(exponent - expBits.length()).concat(expBits);
		return expBits;
	}

	/*
	 * Date: 8 Apr 2020---------------------------------------------------- Time
	 * created: 10:41:09--------------------------------------------
	 */
	/**
	 * Returns the twos complement of a binary's digit for the argument's sign
	 * 
	 * @param sign the sign to be returned regardless of its magnitude
	 * @return 1 if sign is negative otherwise 0
	 */
	private String iEEESign(int sign) {
		return (sign < 0 ? "1" : "0");
	}

	////////////////////////////////////////////////////////////
	/////////////////// Static methods ////////////////////////
	//////////////////////////////////////////////////////////

	/*
	 * 
	 * Date: 8 Apr 2020---------------------------------------------------- Time
	 * created: 09:09:46--------------------------------------------
	 */
	/**
	 * Returns a {@code Precision} from a bit length specified by the argument or a
	 * default if the argument is not in conformity to IEEE 754 standards.
	 * <p>
	 * This method will never return {@code UNLTD}
	 * 
	 * @param length the bit length of the {@code Precision} as defined by IEEE 754
	 *               conventions. This is the same length that will be returned by
	 *               the method {@link #getMaxLength()}.
	 * @return the {@code Precision} pertaining to the argument. If the length is
	 *         not recognised as an IEEE 754 standard length, then the default
	 *         {@code SINGLE} is returned
	 */
	public final static Precision valueOf(int length) {
		switch (length) {
//		case 0:
//			return UNLTD;
		case 8:
			return BIT_8;
		case 16:
			return HALF;
		case 64:
			return DOUBLE;
		case 80:
			return EXTENDED;
		case 128:
			return QUADRUPLE;
		case 256:
			return OCTUPLE;
		default:
			return SINGLE;
		}// End of switch statement
	}

	/*
	 * Date: 8 May 2020-----------------------------------------------------------
	 * Time created: 14:03:33--------------------------------------------
	 */
	/**
	 * Returns a {@code Precision} from a bit length specified by the argument or a
	 * default if the argument is not in conformity to IEEE 754 standards.
	 * <p>
	 * This method will never return {@code UNLTD}
	 * 
	 * @param bits the bit length of the {@code Precision} as defined by IEEE 754
	 *             conventions. This is the same length that will be returned by the
	 *             method {@link #getMaxLength()}.
	 * @return the {@code Precision} pertaining to the argument. If the length is
	 *         not recognised as an IEEE 754 standard length, then the default
	 *         {@code SINGLE} is returned
	 */
	public final static Precision valueOf(BitLength bits) {
		return valueOf(bits.length());
	}

	////////////////////////////////////////////////////////////
	////////////////// Field declarations /////////////////////
	//////////////////////////////////////////////////////////

	/**
	 * The total number of bits this {@code Precision} is using
	 */
	private final int length;
	/**
	 * The total number of bits the significand will use within a given bit length.
	 * This includes the implicit bit in the <i>leading bit convention</i>
	 */
	private final int significandBits;
	/**
	 * The number of bits the exponent part of the total bit length will use
	 */
	private final int exponent;

}
