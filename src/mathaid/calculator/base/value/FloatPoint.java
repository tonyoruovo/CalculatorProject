package mathaid.calculator.base.value;

import static mathaid.ExceptionMessage.UNLIMITED_PRECISION_NOT_ALLOWED;
import static mathaid.ExceptionMessage.WARNING_INFINITY;
import static mathaid.ExceptionMessage.WARNING_NAN;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.util.Date;

import org.apfloat.Apfloat;

import mathaid.Device;
import mathaid.ExceptionMessage;
import mathaid.calculator.base.util.Utility;

/*
 * Date: 11 May 2020----------------------------------------------------------- 
 * Time created: 16:58:55---------------------------------------------------  
 * Package: mathaid.calculator.base.value------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: FloatPoint.java------------------------------------------------------ 
 * Class name: FloatPoint------------------------------------------------ 
 * TODO: 1.E223p-5 this value looses bits during conversions
 */
/**
 * A small class that wraps a the precision of a floating point number.
 * <p>
 * Using this class for floating point calculations seems to be faster than
 * directly using the {@code Precision} class for calculations (More
 * investigative tests are required however). For example, None of the methods
 * of this class which takes in {@code BitLength} will accept
 * {@code BitLength.UNLTD} as its argument.
 * </p>
 * <p>
 * This {@code class} supports the following IEEE 754 conventions:
 * <ul>
 * <li>Half precision: 16 bit precision numbers as specified by the
 * documentation of {@code Precision enum} file.</li>
 * <li>Single Precision: 32 bit precision number as specified by IEEE 754
 * convention</li>
 * <li>Double Precision: 64 bit precision number as specified by IEEE 754
 * convention</li>
 * <li>Extended Precision: 80 bit precision number as specified by IEEE 754
 * convention</li>
 * <li>Quadruple Precision: 128 bit precision number as specified by IEEE 754
 * convention</li>
 * <li>Octuple Precision: 256 bit precision number as specified by IEEE 754
 * convention</li>
 * </ul>
 * Most methods that carry out operations that involves numerical values will
 * throw overflow exceptions if there is an overflow or underflow. There is no
 * special representation for {@code NaN} and {@code Infinity} in this class.
 * </p>
 * 
 * @see Precision
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class FloatPoint implements java.io.Serializable {

	/** For this version */
	private static final long serialVersionUID = 1L;

	/*
	 * Date: 11 May 2020-----------------------------------------------------------
	 * Time created: 16:58:55---------------------------------------------------
	 */
	/**
	 * Constructs a {@code FloatPoint} with a specified {@code Precision} and
	 * initialised to a value. This constructor will throw an exception if
	 * {@link Precision#UNLTD} is used.
	 * 
	 * @param p     a {@code Precision} to constrain the value
	 * @param value a {@code BigDecimal} within the constraints imposed by IEEE 754
	 *              conventions for the {@code Precision} argument. NaN and Infinity
	 *              values are permitted.
	 */
	private FloatPoint(Precision p, BigDecimal value) {
		if (p == Precision.UNLTD)
			new PrecisionException(UNLIMITED_PRECISION_NOT_ALLOWED, "Precision.", p.toString());
		if (p.isNaN(value))
			try {
				Utility.writeTextToFile("", "out.txt",
						MessageFormat.format(new Date() + Device.lineSeparator() + WARNING_NAN.getLocalizedMessage(),
								new Object[] {}),
						StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			} catch (IOException e) {
				e.printStackTrace();
			}
		else if (p.isInfinite(value))
			try {
				Utility.writeTextToFile("", "out.txt",
						MessageFormat.format(
								new Date() + Device.lineSeparator() + WARNING_INFINITY.getLocalizedMessage(), ""),
						StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			} catch (IOException e) {
				e.printStackTrace();
			}
		this.p = p;
		this.value = value;
	}

	/*
	 * Date: 11 May 2020-----------------------------------------------------------
	 * Time created: 17:52:14---------------------------------------------------
	 */
	/**
	 * Constructs a {@code FloatPoint} initialised to the argument. This constructor
	 * creates an object that wraps java's {@code float}. This means that this
	 * object uses the same IEEE 754 guidelines as java's {@code float} such as the
	 * exponent bits, significand bits, and the bit length.
	 * 
	 * @param f a {@code float} value. NaN and Infinity values are permitted.
	 */
	private FloatPoint(float f) {
		/* Throws number format exception when it receives NaN or Infinity values */
//		this(Precision.valueOf(Float.SIZE), new BigDecimal(Float.toString(f)));
		/*
		 * This is better but it seems the conversion from bits is not working as it
		 * should for NaN and Infinity values.
		 */
//		this(Precision.valueOf(Float.SIZE),
//				Precision.valueOf(Float.SIZE).toDecimal(BigInteger.valueOf(Float.floatToIntBits(f))));
		p = Precision.valueOf(Float.SIZE);
		if (Float.isNaN(f)) {
			try {
				Utility.writeTextToFile(
						"", "out.txt", MessageFormat
								.format(new Date() + Device.lineSeparator() + WARNING_NAN.getLocalizedMessage(), ""),
						StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			} catch (IOException e) {
				e.printStackTrace();
			}
			BigInteger bg = BigInteger.valueOf(Float.floatToIntBits(f));
			BigDecimal tmp = p.toDecimal(bg);
			if (!p.isNaN(tmp)) {// Let's artificially compile an NaN
				StringBuilder sb = new StringBuilder(13);// size of a float
				String s = tmp.toString();// 5.1042356E+38 (wrong value)
				sb.append(s.substring(0, s.indexOf("E")));// BigDecimal uses upper case 'E'
				sb.append("e-46");
				value = new BigDecimal(sb.toString());
			} else// unlikely
				value = tmp;
		} else if (Float.isInfinite(f)) {
			try {
				Utility.writeTextToFile("", "out.txt",
						MessageFormat.format(
								new Date() + Device.lineSeparator() + WARNING_INFINITY.getLocalizedMessage(), ""),
						StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			} catch (IOException e) {
				e.printStackTrace();
			}
			BigInteger bg = BigInteger.valueOf(Float.floatToIntBits(f));
			BigDecimal tmp = p.toDecimal(bg);
			if (!p.isInfinite(tmp)) {// Let's artificially construct an infinity value
				StringBuilder sb = new StringBuilder(13);// size of a float
				String s = tmp.toString();// 3.4028235E+38(negative for negative infinity) (wrong value)
				sb.append(s.substring(0, s.indexOf("E")));// BigDecimal uses upper case 'E'
				sb.append("e39");
				value = new BigDecimal(sb.toString());
			} else// unlikely
				value = tmp;
		} else
			value = new BigDecimal(String.valueOf(f));
	}

	/*
	 * Date: 11 May 2020-----------------------------------------------------------
	 * Time created: 17:52:19---------------------------------------------------
	 */
	/**
	 * Constructs a {@code FloatPoint} initialised to the argument. This constructor
	 * creates an object that wraps java's {@code double}. This means that this
	 * object uses the same IEEE 754 guidelines as java's {@code double} such as the
	 * exponent bits, significand bits, and the bit length.
	 * 
	 * @param d a {@code double} value. NaN and Infinity values are permitted.
	 */
	private FloatPoint(double d) {
		p = Precision.valueOf(Double.SIZE);
		if (Double.isNaN(d)) {
			try {
				Utility.writeTextToFile(
						"", "out.txt", MessageFormat
								.format(new Date() + Device.lineSeparator() + WARNING_NAN.getLocalizedMessage(), ""),
						StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			} catch (IOException e) {
				e.printStackTrace();
			}
			BigInteger bg = BigInteger.valueOf(Double.doubleToLongBits(d));
			BigDecimal tmp = p.toDecimal(bg);
			if (!p.isNaN(tmp)) {// Let's artificially compile an NaN
				StringBuilder sb = new StringBuilder(23);// size of a double
				String s = tmp.toString();// 2.6965397022934738E+308 (wrong value)
				sb.append(s.substring(0, s.indexOf("E")));// BigDecimal uses upper case 'E'
				sb.append("e-325");
				value = new BigDecimal(sb.toString());
			} else// unlikely
				value = tmp;
		} else if (Double.isInfinite(d)) {
			try {
				Utility.writeTextToFile("", "out.txt",
						MessageFormat.format(
								new Date() + Device.lineSeparator() + WARNING_INFINITY.getLocalizedMessage(), ""),
						StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			} catch (IOException e) {
				e.printStackTrace();
			}
			BigInteger bg = BigInteger.valueOf(Double.doubleToLongBits(d));
			BigDecimal tmp = p.toDecimal(bg);
			if (!p.isInfinite(tmp)) {// Let's artificially construct an infinity value
				StringBuilder sb = new StringBuilder(13);// size of a float
				String s = tmp.toString();// 1.7976931348623157E+308(negative for negative infinity) (wrong value)
				sb.append(s.substring(0, s.indexOf("E")));// BigDecimal uses upper case 'E'
				sb.append("e309");
				value = new BigDecimal(sb.toString());
			} else// unlikely
				value = tmp;
		} else
			value = new BigDecimal(String.valueOf(d));
	}

	/*
	 * Date: 11 May 2020-----------------------------------------------------------
	 * Time created: 17:52:25--------------------------------------------
	 */
	/**
	 * Retrieves a {@code FloatPoint} from an {@code int} whose pattern is
	 * considered to be the representation of an IEEE 754 {@code float} value.
	 * 
	 * @param bits an {@code int} value whose bit pattern is the same as the number
	 *             returned by {@code value()}.
	 * @return the {@code float} (represented by this class) floating-point value
	 *         with the same bit pattern.
	 * @see #value()
	 */
	public static final FloatPoint fromBits(int bits) {
		return new FloatPoint(Float.intBitsToFloat(bits));
	}

	/*
	 * Date: 11 May 2020-----------------------------------------------------------
	 * Time created: 17:54:35--------------------------------------------
	 */
	/**
	 * Retrieves a {@code FloatPoint} from a {@code long} whose pattern is
	 * considered to be the representation of an IEEE 754 {@code float} value.
	 * 
	 * @param bits a {@code long} value whose bit pattern is the same as the number
	 *             returned by {@code value()}.
	 * @return the {@code double} (represented by this class) floating-point value
	 *         with the same bit pattern.
	 * @see #value()
	 */
	public static final FloatPoint fromBits(long bits) {
		return new FloatPoint(Double.longBitsToDouble(bits));
	}

	/*
	 * Date: 11 May 2020-----------------------------------------------------------
	 * Time created: 17:54:40--------------------------------------------
	 */
	/**
	 * Retrieves a {@code FloatPoint} from a {@code short} whose pattern is
	 * considered to be the representation of an IEEE 754 {@code float} value.
	 * 
	 * @param bits a {@code short} value whose bit pattern is the same as the number
	 *             returned by {@code value()}.
	 * @return a {@code FloatPoint} object (represented as IEEE 754's half
	 *         precision) with a floating-point value with the same bit pattern.
	 * @see #value()
	 */
	public static final FloatPoint fromBits(short bits) {
		final Precision p = Precision.valueOf(Short.SIZE);
		return new FloatPoint(p, p.toDecimal(BigInteger.valueOf(bits)));
	}

	/*
	 * Date: 11 May 2020-----------------------------------------------------------
	 * Time created: 17:54:45--------------------------------------------
	 */
	/**
	 * Convenience method for retrieving a {@code FloatPoint} from a bit pattern.
	 * The value returned has the same precision as the {@code BitLength} argument.
	 * <p>
	 * Note that {@link BitLength#BIT_UNLTD} is not permitted, and any
	 * {@code BitLength} argument less than 16 will use a single precision - as
	 * specified by IEEE 754 conventions - as the default.
	 * </p>
	 * 
	 * @param bits      the value whose bit pattern is the same as the number
	 *                  returned by {@link #value()}
	 * @param precision the number of bits to use - subject to IEEE 754 standards
	 * @return a {@code FloatPoint} object (represented as IEEE 754's standards for
	 *         the bit length's provided precision) with a floating-point value with
	 *         the same bit pattern.
	 * @throws ArithmeticException if the bit length of the integer argument is
	 *                             greater than the precision argument
	 * @see #value()
	 */
	public static final FloatPoint fromBits(BigInteger bits, BitLength precision) {
		final Precision p = Precision.valueOf(precision);
//		if (bits.compareTo(FloatAid.nan(p.getBitLength())) == 0
//				|| bits.compareTo(new BigInteger('1' + FloatAid.nan(p.getBitLength()).toString(2))) == 0)
//			return new FloatPoint(p, p.getMinValue().divide(BigDecimal.TEN));
		return new FloatPoint(p, p.toDecimal(bits));
	}

	/*
	 * Date: 11 May 2020-----------------------------------------------------------
	 * Time created: 18:06:09--------------------------------------------
	 */
	/**
	 * Returns a {@code FloatValue} whose value is initialised to the argument. The
	 * object returned has the same IEEE 754 floating-point precision as java's
	 * {@code double}.
	 * 
	 * @param d a {@code double} value. NaN and Infinity values are permitted.
	 * 
	 * @return a {@code FloatPoint} object using java's {@code double} precision.
	 */
	public static final FloatPoint toFloat(double d) {
		return new FloatPoint(d);
	}

	/*
	 * Date: 11 May 2020-----------------------------------------------------------
	 * Time created: 18:06:58--------------------------------------------
	 */
	/**
	 * Returns a {@code FloatValue} whose value is initialised to the argument. The
	 * object returned has the same IEEE 754 floating-point precision as java's
	 * {@code float}.
	 * 
	 * @param f a {@code float} value. NaN and Infinity values are permitted.
	 * 
	 * @return a {@code FloatPoint} object using java's {@code float} precision.
	 */
	public static final FloatPoint toFloat(float f) {
		return new FloatPoint(f);
	}

	/*
	 * Date: 11 May 2020-----------------------------------------------------------
	 * Time created: 18:09:01--------------------------------------------
	 */
	/**
	 * Returns a {@code FloatValue} whose value is initialised to the
	 * {@code BigDecimal} parameter and uses the {@code BitLength} parameter as its
	 * precision.
	 * <p>
	 * Note that {@code BitLength.BIT_UNLTD} is not permitted.
	 * </p>
	 * 
	 *
	 * @param n         a {@code BigDecimal} within the constraints imposed by IEEE
	 *                  754 conventions for the {@code Precision} argument. NaN and
	 *                  Infinity values are permitted.
	 * @param precision a {@code BitLength} to constrain the value
	 * @return a {@code FloatPoint} object that uses the same precision as the
	 *         argument
	 */
	public static final FloatPoint toFloat(BigDecimal n, BitLength precision) {
//		return new FloatPoint(Precision.valueOf(precision), n);
		Precision p = Precision.valueOf(precision);
//		if(p.isNaN(n))
//			return fromBits(FloatAid.nan(p.getBitLength()), precision);
		BigInteger bits = p.toInteger(n.toPlainString());
		return fromBits(bits, precision);
	}

	/*
	 * Date: 26 Sep 2020-----------------------------------------------------------
	 * Time created: 11:21:58--------------------------------------------
	 */
	/**
	 * Parses a {@code FloatPoint} object from a {@code String}. The {@code String}
	 * may be in normalised form, if it is, then it is interpreted as a hexadecimal
	 * number (i.e the value returned by the {@link #toHexString()} method) or else
	 * it is interpreted as java's {@code BigDecimal} class' constructor interprets
	 * strings. For the definition of normalised form see {@link #toHexString()}
	 * and/or {@link Precision this} documentation. This method will accept java's
	 * hex floating point literals.
	 * 
	 * @param num          a {@code String} within the constraints imposed by IEEE
	 *                     754 conventions for the {@code Precision} argument.
	 *                     Normalised and NaN and Infinity values are permitted.
	 * @param bits         a {@code BitLength} to constrain the value
	 * @param radix        the radix in which the {@code String} argument is encoded
	 *                     in
	 * @param isNormalised sets the parser for parsing hexadecimal floating point
	 *                     forms.
	 * @return a {@code FloatPoint} object parsed from the string argument in the
	 *         specified bit length.
	 * @throws NumberFormatException if
	 *                               <ul>
	 *                               <li>{@code num} is not normalised and
	 *                               {@code isNormalised} is {@code true}</li>
	 *                               <li>{@code num} is not a number in any of the
	 *                               radix as specified by
	 *                               {@link IntValue.Radix}</li>
	 *                               <li>{@code num} is contains invalid digits such
	 *                               as {@literal "!@#$%^%&amp;*()"}</li>
	 *                               </ul>
	 */
	public static final FloatPoint toFloat(String num, BitLength bits, Radix radix, boolean isNormalised) {
		final BigDecimal n;
		final Precision p = Precision.valueOf(bits);
		/*
		 * If the input is 'NaN' decrease n by dividing the precision's minimum value it
		 * by 100. If the input is 'Infinity', increase n by multiplying the precision's
		 * max value by 100. By so doing, NaNs and Infinities will be supported
		 */
		if (num.contains("NaN") || num.contains("Infinity")) {
			n = num.contains("NaN")
					? (num.charAt(0) == '-'
							? p.getMinValue().divide(BigDecimal.valueOf(100L), p.getMathContext()).negate()
							: p.getMinValue().divide(BigDecimal.valueOf(100L), p.getMathContext()))
					: (num.charAt(0) == '-' ? p.getMaxValue().multiply(BigDecimal.valueOf(-100L), p.getMathContext())
							: p.getMaxValue().multiply(BigDecimal.valueOf(100L), p.getMathContext()));
		} else if (!isNormalised)
//			n = new BigDecimal(num, p.getMathContext());
			n = Utility.toDecimal(num, radix, null);
		else {
			if (!p.checkNormalised(num))
				new ValueFormatException(ExceptionMessage.NUMBER_NOT_NORMALISED);
			num = radix == Radix.HEX ? num.replaceFirst("0x", "") : num.replaceFirst("0b", "");

			try {
				n = Utility.toDecimal(num, radix, p);
			} catch (ArithmeticException e) {
//				e.printStackTrace();
				new ValueFormatException(ExceptionMessage.EMPTY);
				throw e;
			}
		}
		return new FloatPoint(p, n);
	}

	/*
	 * Date: 11 May 2020-----------------------------------------------------------
	 * Time created: 17:54:49--------------------------------------------
	 */
	/**
	 * Returns the {@code Precision} type being used by this {@code FloatPoint}
	 * object
	 * 
	 * @return the current precision
	 */
	public Precision type() {
		return p;
	}

	/*
	 * Date: 20 Oct 2022----------------------------------------------------------- 
	 * Time created: 16:58:43--------------------------------------------
	 */
	/**
	 * Casts this {@code FloatPoint} to another precision.
	 * @param b the {@code BitLength} of the returned value
	 * @return {@code this} cast to the given length
	 */
	public FloatPoint cast(BitLength b) {
		if(p == Precision.valueOf(b))
			return this;
		return toFloat(value, b);
	}
	
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
	 * A Special BigDecimal that can print out infinity and NaN values, without
	 * throwing an exception.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 *
	 */
	private class SBigD extends BigDecimal {
		private static final long serialVersionUID = 3266823728375447853L;

		private SBigD() {
			super(getSpecialString(value, p));
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
			return value.signum();
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
			return -1;
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
			return new BigInteger("0");
		}

		@Override
		public BigDecimal ulp() {
			return this;
		}

		@Override
		public String toString() {
			if (p.isInfinite(value))
				return value.signum() < 0 ? "-Infinity" : "Infinity";
			else if (p.isNaN(value))
				return "NaN";
			return super.toString();
		}

		@Override
		public String toPlainString() {
			if (p.isNaN(value) || p.isInfinite(value))
				return toString();
			return super.toPlainString();
		}

		@Override
		public String toEngineeringString() {
			if (p.isNaN(value) || p.isInfinite(value))
				return toString();
			return super.toEngineeringString();
		}

	}

	/*
	 * Date: 12 Sep 2020-----------------------------------------------------------
	 * Time created: 12:40:49--------------------------------------------
	 */
	/**
	 * Returns a special string to help {@code SBigD} object instantiation.
	 * 
	 * @param value a {@code BigDecimal}
	 * @param p     a {@code Precision}
	 * @return a special {@code String}.
	 */
	private static String getSpecialString(BigDecimal value, Precision p) {
		if (p.isInfinite(value)) {

			BigDecimal val = p.getMaxValue();
			String s = val.toString();
			StringBuilder sb;
			try {
				sb = new StringBuilder("1.0").append(s.substring(s.indexOf("E"))).append("9");
			} catch (IndexOutOfBoundsException e) {
				sb = new StringBuilder("1.0").append("E").append(p.getMaxExponent()).append("9");
			}
			sb.insert(0, value.signum() < 0 ? "-" : "+");

			return sb.toString();
		} else if (p.isNaN(value)) {

			BigDecimal val = p.getMinValue();
			String s = val.toString();
			StringBuilder sb;
			try {
				sb = new StringBuilder("0.0").append(s.substring(s.indexOf("E"))).append("9");
			} catch (IndexOutOfBoundsException e) {
				sb = new StringBuilder("0.0").append("E").append(p.getMinExponent()).append("9");
			}
			return sb.toString();

		}
		return "0";
	}

	/*
	 * Date: 11 May 2020-----------------------------------------------------------
	 * Time created: 17:59:54--------------------------------------------
	 */
	/**
	 * Returns the value of this object
	 * 
	 * @return a {@code BigDecimal} number being used as a value for this object
	 */
	public BigDecimal value() {
		if (isNaN() || isInfinite())
			return new SBigD();
		return value;
	}

	/*
	 * Date: 11 May 2020-----------------------------------------------------------
	 * Time created: 18:00:00--------------------------------------------
	 */
	/**
	 * Returns a normalised hexadecimal representation of this {@code FloatPoint}'s
	 * value. Please refer to the documentation of {@code Precsion} to see details
	 * of an IEEE 754 normalised form.
	 * 
	 * @return a hex {@code String} represntation of this {@code FloatPoint}'s
	 *         value.
	 */
	public String toHexString() {
		if (p.getBitLength() == 32)
			return Float.toHexString(value.floatValue()).replaceFirst("0x", "");
		if (p.getBitLength() == 64)
			return Double.toHexString(value.doubleValue()).replaceFirst("0x", "");
		else if (isNaN() || isInfinite())
			return new SBigD().toString();
		return normalise(Radix.HEX);
	}

	/*
	 * Date: 11 May 2020-----------------------------------------------------------
	 * Time created: 18:25:05--------------------------------------------
	 */
	/**
	 * Normalises this {@code FloatPoint}'s value and returns it as a string in the
	 * specified radix. If the radix is null, then a normalised verson of the
	 * decimal is returned. Please refer to the documentation of {@code Precision}
	 * to see details of an IEEE 754 normalised form.
	 * 
	 * @param r the radix to use for the normalisation
	 * @return a {@code String} in IEEE 754 normalised form
	 */
	public String normalise(Radix r) {
		if (isNaN() || isInfinite())
			return new SBigD().toString();
		return p.normalise(value.toString(), r == null ? Radix.DEC : r);
	}

	/*
	 * Date: 11 May 2020-----------------------------------------------------------
	 * Time created: 18:00:05--------------------------------------------
	 */
	/**
	 * Returns {@code true} if the argument's magnitude is less than or equal to the
	 * max possible value, and {@code false} otherwise. This is only for testing for
	 * infinity. NaNs may return {@code false}
	 * 
	 * @return {@code true} if the argument's magnitude is less than or equal to the
	 *         max possible value, and {@code false} otherwise.
	 */
	public boolean isFinite() {
		return p.isFinite(value);
	}

	/*
	 * Date: 11 May 2020-----------------------------------------------------------
	 * Time created: 18:00:11--------------------------------------------
	 */
	/**
	 * Checks whether {@code this} is compatible with infinity value in relation to
	 * the {@code Precision} and also in compliance with IEEE 754 stipulations. This
	 * method will return {@code false} for {@code NaN} and {@code true} for
	 * {@code Infinity}. This method is not just for convenience, please use this
	 * method to check for the inifiniteness of a value.
	 * 
	 * @return {@code true} if {@code this} is infinite as defined by IEEE 754
	 *         conventions for this {@code Precision} otherwise returns {@code false}
	 */
	public boolean isInfinite() {
		return p.isInfinite(value);
	}

	/*
	 * Date: 11 May 2020-----------------------------------------------------------
	 * Time created: 18:00:15--------------------------------------------
	 */
	/**
	 * Tests whether the argument lies within the subnormal range. A subnormal
	 * number is a number which is lesser than smallest non-zero, non-negative
	 * normal value
	 * 
	 * @return {@code true} if the argument's magnitude is less than the
	 *         smallest<br>
	 *         possible normal value, and {@code false} otherwise.
	 */
	public boolean isSubnormal() {
		return p.isSubnormal(value);
	}

	/*
	 * Date: 11 May 2020-----------------------------------------------------------
	 * Time created: 18:00:20--------------------------------------------
	 */
	/**
	 * Tests whether the argument is less than the smallest non-zero, positive value
	 * in the {@code Precision}. This method will return {@code true} if the
	 * argument is lesser than the minimum possible value or {@code false} if
	 * otherwise.
	 * 
	 * @return {@code true} if the argument's magnitude is less than the
	 *         smallest<br>
	 *         possible value, and {@code false} otherwise.
	 */
	public boolean isNaN() {
		return p.isNaN(value);
	}

	/*
	 * 
	 * Date: 16 Jun 2020-----------------------------------------------------------
	 * Time created: 14:31:57--------------------------------------------
	 */
	/**
	 * Returns the unbiased floating-point exponent of the BigDecimal.
	 * 
	 * @return the exponent of this value
	 */
	public int getExponent() {
		if (p == Precision.SINGLE)
			return Math.getExponent(value.floatValue());
		if (p == Precision.DOUBLE)
			return Math.getExponent(value.doubleValue());
		return p.getExponent(value);
	}

	/*
	 * Date: 11 May 2020-----------------------------------------------------------
	 * Time created: 18:00:24--------------------------------------------
	 */
	/**
	 * Return the bit layout of the {@code String} argument in accordance with IEEE
	 * 754 floating point rules. The actual number returned has a bit pattern(in
	 * <i>big-endian</i> format) identical to the IEEE 754 pattern of the argument
	 * in relation to the {@code Precision}. Both {@code NaN} and {@code Infinity}
	 * are represented by this method, although {@code sNaN} and {@code qNaN} will
	 * have the same bit layout while positive and negative {@code Infinity} will
	 * each have their own layout.
	 * 
	 * @return the bit pattern of the {@code String} floating-point decimal as a
	 *         {@code BigInteger}
	 */
	public BigInteger toBits() {
//		if (isNaN())
//			return FloatAid.nan(p.getBitLength());
//		System.err.println(value.floatValue());
		if(p.getBitLength() == 32)
			return new BigInteger(Integer.toBinaryString(Float.floatToRawIntBits(Float.parseFloat(value.toString()))), 2);
		if(p.getBitLength() == 64)
			return new BigInteger(Long.toBinaryString(Double.doubleToRawLongBits(Double.parseDouble(value.toString()))), 2);
//		System.err.println("err: "+value);
		return p.toInteger(value.toString());
	}

	/*
	 * Date: 25 Jun 2021-----------------------------------------------------------
	 * Time created: 15:33:30--------------------------------------------
	 */
	/**
	 * Converts this {@code FloatPoint} to the specified {@code Radix} and returns
	 * the {@code String} representation if {@code isInfinite()} and {@code isNaN()}
	 * return {@code false}, otherwise, returns the string for the identified
	 * special case (either {@code "NaN"} or {@code "Infinity"}). Please note that
	 * this method does for return an exponent of any kind
	 * 
	 * @param radix the radix to which this {@code FloatPoint} is to be represented
	 * @return a {@code String} representation of the internal value in the given
	 *         {@code Radix}
	 */
	public String toString(Radix radix) {
		if (isInfinite() || isNaN())
			return value().toString();
		Apfloat f = new Apfloat(value).toRadix(radix.getRadix());
		return f.toString(true);
	}

	/*
	 * Most Recent Date: 11 May 2020-----------------------------------------------
	 * Most recent time created: 18:00:33----------------------------------------
	 */
	/**
	 * Returns a string representation of the wrapped {@code Number}.
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public String toString() {
//		if (isNaN() || isInfinite())
//			return new SBigD().toString();
//		return value.toString();
		return value().toString();
	}

	/**
	 * The {@code Precision} of this object as specified by iEEE 754 recommendations
	 */
	private final Precision p;
	/**
	 * The value field of this class
	 */
	private final BigDecimal value;

}
