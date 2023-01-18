/**
 * 
 */
package mathaid.calculator.base.util;

import static mathaid.ExceptionMessage.ARRAY_SIZE_OUTSIDE_LIMIT;
import static mathaid.ExceptionMessage.EMPTY;
import static mathaid.ExceptionMessage.ILLEGAL_DIGIT;
import static mathaid.ExceptionMessage.OVERFLOW;
import static mathaid.ExceptionMessage.SIGNED_NUMBER_NOT_ACCEPTED;
import static mathaid.ExceptionMessage.SINGLETON_CONSTRUCTOR_ERROR;
import static mathaid.ExceptionMessage.STATIC_SINGLETON_ERROR;
import static mathaid.ExceptionMessage.UNLIMITED_PRECISION_NOT_ALLOWED;
import static mathaid.ExceptionMessage.UNSUPPORTED_RADIX;
import static mathaid.ExceptionMessage.WRONG_BITLENGTH_FOR_BINARY_REP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;
import java.util.TreeMap;

import org.apfloat.Apfloat;

import mathaid.ArraySizeException;
import mathaid.ExceptionMessage;
import mathaid.IndexBeyondLimitException;
import mathaid.NullException;
import mathaid.ObjectInstantiationError;
import mathaid.ObjectInstantiationException;
import mathaid.calculator.base.typeset.Digits;
import mathaid.calculator.base.value.BigFraction;
import mathaid.calculator.base.value.BinaryRep;
import mathaid.calculator.base.value.BitLength;
import mathaid.calculator.base.value.Complex;
import mathaid.calculator.base.value.Fraction;
import mathaid.calculator.base.value.Precision;
import mathaid.calculator.base.value.PrecisionException;
import mathaid.calculator.base.value.PrecisionOverflowException;
import mathaid.calculator.base.value.Radix;
import mathaid.calculator.base.value.ValueFormatException;

/*
 * Date: 27 Apr 2020----------------------------------------------------
 * Time created: 16:53:07--------------------------------------------
 * Package: mathaid.calculator.base-----------------------------------------
 * Project: LatestPoject2-----------------------------------------
 * File: Utility.java-----------------------------------------------
 * Class name: Utility-----------------------------------------
 */
/**
 * <p>
 * A set of static utility methods.
 * </p>
 * <p>
 * Unless otherwise stated, all methods will throw a
 * {@code NullPointerException} if {@code null} is passed as an argument to any
 * one of it's parameters.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public final class Utility {

	/**
	 * A {@code Comparator} for {@code BigDecimal} numbers whose
	 * {@link Comparator#compare(Object, Object)} method is known to be faster than
	 * the {@code BigDecimal} class' {@link BigDecimal#compareTo(BigDecimal)}
	 * method.
	 */
	public static final Comparator<BigDecimal> COMPARATOR = Utility::compareTo;

	/*
	 * Date: 9 Dec 2022-----------------------------------------------------------
	 * Time created: 15:49:55---------------------------------------------------
	 */
	/**
	 * A character token used for encryption in
	 * {@link #encrypt(Calendar, Object, String)}
	 */
	public static final char PARAMS = '\uF71A';

	/*
	 * Date: 28 Apr 2020---------------------------------------------------- Time
	 * created: 21:26:22--------------------------------------------
	 */
	/**
	 * This {@code class} is not instantiable
	 * 
	 * @throws Exception specifically an {@link InstantiationException} if
	 *                   instantiation is attempted.
	 */
	private Utility() throws Exception {
		new ObjectInstantiationException(SINGLETON_CONSTRUCTOR_ERROR);
	}

	/*
	 * Date: 27 Apr 2020---------------------------------------------------- Time
	 * created: 16:58:48--------------------------------------------
	 */
	/**
	 * Returns a string of zeros whose length is equal to the argument. This method
	 * is a fast way to return a set of zeros without the cost of having to iterate
	 * and concatenate the string using a loop. This is especially helpful for bit
	 * vector applications.
	 * 
	 * @param numOfZeros the length of the {@code String} to be returned.
	 * @return a "zero only" {@code String} whose length == {@code numOfZeros}
	 */
	public static String zeros(int numOfZeros) {
		return new BigDecimal("1E" + numOfZeros).toBigInteger().toString().substring(1);
	}

	/**
	 * Utility method for identifying integer values from fractional numbers.
	 *
	 * @param n the number to be evaluated.
	 * @return whether the number is integer, true if it is, false otherwise.
	 */
	public static boolean isInteger(BigDecimal n) {
//		if (n.abs().compareTo(new BigDecimal(Double.MAX_VALUE + "")) <= 0
//				&& n.abs().compareTo(new BigDecimal(Double.MIN_VALUE + "")) >= 0)
//			return Fraction.isInteger(Double.parseDouble(n.toString()));

//		if (n.signum() > 0) {
//			n = n.subtract(new BigDecimal(n.toBigInteger()));
//			return n.signum() == 0;
//		}

		try {
			n.toBigIntegerExact();
		} catch (@SuppressWarnings("unused") ArithmeticException e) {
			return false;
		}
		return true;
	}

	/*
	 * Date: 27 Apr 2020---------------------------------------------------- Time
	 * created: 20:59:14--------------------------------------------
	 */
	/**
	 * Strips all the trailing zeros following the number string argument (which is
	 * the same radix as the argument), and returns a string in the specified radix.
	 * The {@code String} argument can be fractional or otherwise, so the caller
	 * will have to alert the method of it form via the {@code boolean} parameter.
	 * 
	 * @param s     the number whose zeros to be stripped
	 * @param radix the radix of the string argument and also of the returned string
	 * @param isInt confirms the type of string (whether it is an integer or not)
	 * @return the argument string after being stripped of trailing zeros
	 */
	public static String stripTrailingZeros(String s, int radix, boolean isInt) {
		if ((!isInt) && (!s.contains(".")))
			return s;
		if (!isInt) {
			if (new BigInteger(s.substring(s.indexOf(".") + 1), radix).compareTo(BigInteger.ZERO) == 0)
				return s.substring(0, s.indexOf("."));
			return s.substring(0, s.indexOf(".") + 1) + new StringBuilder(
					new BigInteger(new StringBuilder(s.substring(s.indexOf(".") + 1)).reverse().toString(), radix)
							.toString(radix))
					.reverse();
		}
		return new StringBuilder(new BigInteger(new StringBuilder(s).reverse().toString(), radix).toString(radix))
				.reverse().toString();
	}

	/*
	 * Date: 1 May 2020---------------------------------------------------- Time
	 * created: 13:33:29--------------------------------------------
	 */
	/**
	 * Strips the leading zeros in an integer {@code String} and returns it in the
	 * same radix as the argument. If there is no leading zeros, then the argument
	 * {@code String} is returned with no changes.
	 * 
	 * @param s     the integer {@code String} in the same radix as the parameter
	 *              {@code radix}
	 * @param radix the radix of the number {@code String} and the {@code String} to
	 *              be returned
	 * @return an integer {@code String} with any leading zeros leading zeros
	 *         stripped
	 */
	public static String stripLeadingZeros(String s, int radix) {
		return new BigInteger(s, radix).toString(radix);
	}

	/*
	 * Date: 1 May 2020---------------------------------------------------- Time
	 * created: 13:38:10--------------------------------------------
	 */
	/**
	 * Concatenates leading zeros - according to the classical register-like integer
	 * representations - to the integer {@code String}, if the integer's length() is
	 * less than the {@code BitLength} provided. If the number {@code String} is
	 * greater than or equal to the {@code BitLength} provided, then the original
	 * number {@code String} is returned without any changes.
	 * <p>
	 * This method is specifically for binary integer strings.
	 * </p>
	 * 
	 * @param s    the integer {@code String}
	 * @param bits the bit length
	 * @return a {@code String} with leading zeros concatenated to it to make up for
	 *         the bit length in classical register-like integers
	 */
	public static String concatLeadingZeros(String s, BitLength bits) {
		return concatLeadingZeros(s, bits, Radix.BIN);
	}

	/*
	 * Date: 3 May 2020-----------------------------------------------------------
	 * Time created: 12:33:40--------------------------------------------
	 */
	/**
	 * Concatenates leading zeros - according to the classical register-like integer
	 * representations - to the integer {@code String}, if the integer's length() is
	 * less than the {@code BitLength} provided. If the number {@code String} is
	 * greater than or equal to the {@code BitLength} provided, then the original
	 * number {@code String} is returned without any changes.
	 * 
	 * @param s     the integer {@code String}
	 * @param bits  the bit length
	 * @param radix the radix of the {@code String} argument
	 * @return a {@code String} with leading zeros concatenated to it to make up for
	 *         the bit length in classical register-like integers
	 */
	public static String concatLeadingZeros(String s, BitLength bits, Radix radix) {
		switch (radix) {
		case BIN:
			if (s.length() >= bits.length())
//				return s.substring(bits.length() - 1);
				return s;// TODO: should it be this or the above?
			return zeros(bits.length() - s.length()).concat(s);
		case OCT:
			if (s.length() >= (int) Math.ceil(bits.length() / 3))
				// ((bits.length() / 3) + (bits.length() % 3 == 0 ? 0 : 1)))
				return s;
			return zeros(((bits.length() / 3) + (bits.length() % 3)) - s.length()).concat(s);
		case HEX:
			if (s.length() >= (int) Math.ceil(bits.length() / 4))
				// (bits.length() / 4) + (bits.length() % 3 == 0 ? 0 : 1))
				return s;
			return zeros(((bits.length() / 4) + (bits.length() % 4)) - s.length()).concat(s);
		default:
			return s;
		}
	}

	/*
	 * Date: 1 May 2020---------------------------------------------------- Time
	 * created: 13:59:10--------------------------------------------
	 */
	/**
	 * Converts the {@code BigInteger} to a twos complement bit pattern and returns
	 * a {@code String} whose length is equal to the argument {@code bits}. The
	 * {@code String} returned does not carry a mathematical sign. If the binary
	 * length is greater than or equal to the {@code BitLength}, then the sign is
	 * removed and the binary is returned.
	 * <p>
	 * Although the {@code String} binary pattern returned by this method is the
	 * same as the {@code String} binary pattern returned by
	 * {@link #toUnsignedBinary(BigInteger, BitLength)}, it is implemented
	 * differently i.e by adding one to the the complement of {@code num}. This
	 * action is only for negative numbers, positive numbers on the other hand are
	 * just returned unchanged.
	 * </p>
	 * 
	 * @param n    the number to be converted which assumed to be in decimal math
	 *             format.
	 * @param bits the {@code BitLength} of the result
	 * @return a twos complement representational {@code String} of the argument
	 *         {@code num}
	 */
	public static String toTwosBinary(BigInteger num, BitLength bits) {
		final int signum = num.signum();
		if (signum >= 0)
			return concatLeadingZeros(num.toString(2), bits);
		String b = num.abs().toString(2);
		if (b.length() >= bits.length())
			return b;
		BigInteger bin = new BigInteger(b, 2);
		bin = not(bin, bits).add(BigInteger.ONE);
		return bin.toString(2);
	}

	/*
	 * Date: 1 May 2020---------------------------------------------------- Time
	 * created: 14:37:01--------------------------------------------
	 */
	/**
	 * Converts the {@code BigInteger} to a ones complement bit pattern and returns
	 * a {@code String} whose length is equal to the argument {@code bits}. The
	 * {@code String} returned does not carry a mathematical sign. If the binary
	 * length is greater than or equal to the {@code BitLength}, then the sign is
	 * removed and the binary is returned.
	 * 
	 * @param n    the number to be converted which assumed to be in decimal math
	 *             format.
	 * @param bits the {@code BitLength} of the result
	 * @return a ones complement representational {@code String} of the argument
	 *         {@code num}
	 */
	public static String toOnesBinary(BigInteger n, BitLength bits) {
		if (n.signum() >= 0)
			return toTwosBinary(n, bits);
		String b = n.abs().toString(2);
		if (b.length() >= bits.length())
			return b;
		return new BigInteger(toTwosBinary(n, bits), 2).subtract(BigInteger.ONE).toString(2);
	}

	/*
	 * 
	 * Date: 1 May 2020---------------------------------------------------- Time
	 * created: 14:37:22--------------------------------------------
	 */
	/**
	 * Converts the {@code BigInteger} to an excess-n bit pattern and returns a
	 * {@code String} whose length is equal to the argument {@code bits}. The
	 * {@code String} returned does not carry a mathematical sign. If the binary
	 * length is greater than or equal to the {@code BitLength}, then the sign is
	 * removed and the binary is returned.
	 * 
	 * @param n    the number to be converted which assumed to be in decimal math
	 *             format.
	 * @param bits the {@code BitLength} of the result
	 * @return an excess-n representational {@code String} of the argument
	 *         {@code num}
	 */
	public static String toExcessBinary(BigInteger n, BitLength bits) {
		final int signum = n.signum();
		n = n.add(BigInteger.TWO.pow(bits.length() - 1)/* .subtract(BigInteger.ONE) */);
		if (signum >= 0)
			return n.or(new BigInteger("1" + zeros(bits.length() - 1), 2)).toString(2);

		String bin = n.toString(2);
		return concatLeadingZeros(bin, bits);

	}

	/*
	 * Date: 1 May 2020---------------------------------------------------- Time
	 * created: 14:47:53--------------------------------------------
	 */
	/**
	 * Converts the {@code BigInteger} to an unsigned bit pattern and returns a
	 * {@code String} whose length is equal to the argument {@code bits}. If the
	 * binary length is greater than or equal to the {@code BitLength}, then the
	 * sign is removed and the binary is returned.
	 * <p>
	 * The pattern returned by this method is the same as the pattern returned by
	 * {@link #toTwosBinary(BigInteger, BitLength)}. But this method implements its
	 * algorithm differently i.e by <code>n + 2<sup>bits.length()</sup></code>. This
	 * action is only for instances of the {@code BigInteger} argument whose signum
	 * is less than 0, positive {@code BigInteger}s on the other hand are just
	 * returned unchanged.
	 * </p>
	 * 
	 * @param n    the number to be converted which assumed to be in decimal math
	 *             format.
	 * @param bits the {@code BitLength} of the result
	 * @return an unsigned representational {@code String} of the argument
	 *         {@code num}
	 */
	public static String toUnsignedBinary(BigInteger n, BitLength bits) {
		final int signum = n.signum();
		String bin = n.abs().toString(2);

		if (signum >= 0)
			return (bits != BitLength.BIT_UNLTD) ? concatLeadingZeros(bin, bits) : bin;

		bin = n.add(BigInteger.TWO.pow(bits.length())).toString(2);

//		bin = not(new BigInteger(toExcessBinary(n, bits), 2), bits).toString(2);

//		bin = n.abs().add(BigInteger.TWO.pow(bin.length()).subtract(BigInteger.ONE)).toString(2);

		return (bits != BitLength.BIT_UNLTD) ? concatLeadingZeros(bin, bits) : bin;
	}

	/*
	 * Date: 1 May 2020---------------------------------------------------- Time
	 * created: 14:57:37--------------------------------------------
	 */
	/**
	 * Converts the {@code BigInteger} to a sign and magnitude bit pattern and
	 * returns a {@code String} whose length is equal to the argument {@code bits}.
	 * The {@code String} returned does not carry a mathematical sign. If the binary
	 * length is greater than or equal to the {@code BitLength}, then the sign is
	 * removed and the binary is returned.
	 * 
	 * @param n    the number to be converted which assumed to be in decimal math
	 *             format.
	 * @param bits the {@code BitLength} of the result
	 * @return a signed magnitude representational {@code String} of the argument
	 *         {@code num}
	 */
	public static String toSmrBinary(BigInteger n, BitLength bits) {
		final int signum = n.signum();

		if (signum < 0)
			return n.abs().or(new BigInteger("1" + zeros(bits.length() - 1), 2)).toString(2);

		return concatLeadingZeros(n.toString(2), bits);
	}

	/*
	 * Date: 1 May 2020---------------------------------------------------- Time
	 * created: 16:17:51--------------------------------------------
	 */
	/**
	 * Converts the {@code BigInteger} to a negabinary bit pattern and returns a
	 * {@code String} whose length is equal to the argument {@code bits}. The
	 * {@code String} returned does not carry a mathematical sign. If the binary
	 * length is greater than or equal to the {@code BitLength}, then the sign is
	 * removed and the binary is returned.
	 * 
	 * @param n    the number to be converted.
	 * @param bits the {@code BitLength} of the result
	 * @return a negative binary representational {@code String} of the argument
	 *         {@code num}
	 */
	public static String toNegaBinary(BigInteger n, BitLength bits) {
		StringBuffer temp = new StringBuffer("A");
		for (int i = 4; i < bits.length(); i += i)
			temp.append(temp);
		BigInteger mask = new BigInteger(temp.toString(), 16);
		mask = mask.add(n).xor(mask);
		return concatLeadingZeros(mask.abs().toString(2), bits);
	}

	/*
	 * Date: 2 May 2020---------------------------------------------------- Time
	 * created: 22:18:09--------------------------------------------
	 */
	/**
	 * Returns a decimal {@code String} from a twos complement {@code String}
	 * argument.
	 * <p>
	 * Some implementation of might achieve this by performing the inverse of the
	 * operation that converts a decimal to twos complement, and returning the
	 * result.
	 * </p>
	 * 
	 * @param binary a binary {@code String} in twos complement representation
	 * @param bits   the number of bits to which the conversion is made
	 * @return a decimal {@code String}
	 * @throws RuntimeException specifically a {@link NumberFormatException} if the
	 *                          {@code String} argument is signed
	 */
	public static String twosToDecimal(String binary, BitLength bits) throws RuntimeException {
		BigInteger unsigned = new BigInteger(binary, 2);
		if (unsigned.signum() < 0)
			new ValueFormatException(SIGNED_NUMBER_NOT_ACCEPTED, unsigned, 2);
		binary = concatLeadingZeros(unsigned.toString(2), bits);
		final boolean isNegative = binary.charAt(0) == '1';
		if (!isNegative)
			return unsigned.toString();
		unsigned = not(unsigned.subtract(BigInteger.ONE), bits);
		return unsigned.negate().toString();
	}

	/*
	 * Date: 2 May 2020---------------------------------------------------- Time
	 * created: 22:18:09--------------------------------------------
	 */
	/**
	 * Returns a decimal {@code String} from a ones complement {@code String}
	 * argument.
	 * <p>
	 * Some implementation of might achieve this by performing the inverse of the
	 * operation that converts a decimal to ones complement, and returning the
	 * result.
	 * </p>
	 * 
	 * @param binary a binary {@code String} in ones complement representation
	 * @param bits   the number of bits to which the conversion is made
	 * @return a decimal {@code String}
	 * @throws RuntimeException specifically a {@link NumberFormatException} if the
	 *                          {@code String} argument is signed
	 */
	public static String onesToDecimal(String binary, BitLength bits) throws RuntimeException {
		BigInteger unsigned = new BigInteger(binary, 2);
		if (unsigned.signum() < 0)
			new ValueFormatException(SIGNED_NUMBER_NOT_ACCEPTED, unsigned, 2);
		binary = concatLeadingZeros(unsigned.toString(2), bits);
		final boolean isNegative = binary.charAt(0) == '1';
		if (!isNegative)
			return unsigned.toString();
		if (!binary.contains("0"))
			return "-0";
		unsigned = not(unsigned, bits);
		return unsigned.negate().toString();
	}

	/*
	 * Date: 2 May 2020---------------------------------------------------- Time
	 * created: 22:18:09--------------------------------------------
	 */
	/**
	 * Returns a decimal {@code String} from a excess-n {@code String} argument.
	 * <p>
	 * Some implementation of might achieve this by performing the inverse of the
	 * operation that converts a decimal to excess-n, and returning the result.
	 * </p>
	 * 
	 * @param binary a binary {@code String} in excess-n representation
	 * @param bits   the number of bits to which the conversion is made
	 * @return a decimal {@code String}
	 * @throws RuntimeException specifically a {@link NumberFormatException} if the
	 *                          {@code String} argument is signed
	 */
	public static String excessToDecimal(String binary, BitLength bits) throws RuntimeException {
		BigInteger unsigned = new BigInteger(binary, 2);
		if (unsigned.signum() < 0)
			new ValueFormatException(SIGNED_NUMBER_NOT_ACCEPTED, unsigned, 2);
		binary = concatLeadingZeros(unsigned.toString(2), bits);
//		final boolean isNegative = binary.charAt(0) == '0';
//		if (!isNegative)
//			return unsigned.toString();
		unsigned = unsigned.subtract(BigInteger.TWO.pow(bits.length() - 1)/* .subtract(BigInteger.ONE) */);
		return unsigned.toString();
	}

	/*
	 * Date: 2 May 2020---------------------------------------------------- Time
	 * created: 22:18:09--------------------------------------------
	 */
	/**
	 * Returns a decimal {@code String} from an unsigned {@code String} argument.
	 * <p>
	 * Some implementation of might achieve this by performing the inverse of the
	 * operation that converts a decimal to unsigned, and returning the result.
	 * </p>
	 * 
	 * @param binary a binary {@code String} in unsigned representation
	 * @param bits   the number of bits to which the conversion is made
	 * @return a decimal {@code String}
	 * @throws RuntimeException specifically a {@link NumberFormatException} if the
	 *                          {@code String} argument is signed
	 */
	public static String unsignedToDecimal(String binary, BitLength bits) throws RuntimeException {
		BigInteger unsigned = new BigInteger(binary, 2);
		if (unsigned.signum() < 0)
			new ValueFormatException(SIGNED_NUMBER_NOT_ACCEPTED, unsigned, 2);
		binary = concatLeadingZeros(unsigned.toString(2), bits);
		return unsigned.toString();
	}

	/*
	 * Date: 2 May 2020---------------------------------------------------- Time
	 * created: 22:18:09--------------------------------------------
	 */
	/**
	 * Returns a decimal {@code String} from an signed magnitude {@code String}
	 * argument.
	 * <p>
	 * Some implementation of might achieve this by performing the inverse of the
	 * operation that converts a decimal to signed magnitude, and returning the
	 * result.
	 * </p>
	 * 
	 * @param binary a binary {@code String} in the signed magnitude representation
	 * @param bits   the number of bits to which the conversion is made
	 * @return a decimal {@code String}
	 * @throws RuntimeException specifically a {@link NumberFormatException} if the
	 *                          {@code String} argument is signed
	 */
	public static String smrToDecimal(String binary, BitLength bits) throws RuntimeException {
		BigInteger unsigned = new BigInteger(binary, 2);
		if (unsigned.signum() < 0)
			new ValueFormatException(SIGNED_NUMBER_NOT_ACCEPTED, unsigned, 2);
		binary = concatLeadingZeros(unsigned.toString(2), bits);
		if (binary.charAt(0) == '1' && BigInteger.ZERO.compareTo(new BigInteger(binary.substring(1), 2)) == 0)
			return "-0";
		final boolean isNegative = binary.charAt(0) == '1';
		if (!isNegative)
			return unsigned.toString();
		unsigned = new BigInteger(binary.substring(1), 2).negate();
		return unsigned.toString();
	}

	/*
	 * Date: 2 May 2020---------------------------------------------------- Time
	 * created: 22:18:09--------------------------------------------
	 */
	/**
	 * Returns a decimal {@code String} from a negabinary {@code String} argument.
	 * <p>
	 * Some implementation of might achieve this by performing the inverse of the
	 * operation that converts a decimal to negabinary, and returning the result.
	 * </p>
	 * 
	 * @param binary a binary {@code String} in the negabinary representation
	 * @param bits   the number of bits to which the conversion is made
	 * @return a decimal {@code String}
	 * @throws RuntimeException specifically a {@link NumberFormatException} if the
	 *                          {@code String} argument is signed
	 */
	public static String negaToDecimal(String binary, BitLength bits) throws RuntimeException {
		BigInteger unsigned = new BigInteger(binary, 2);
		if (unsigned.signum() < 0)
			new ValueFormatException(SIGNED_NUMBER_NOT_ACCEPTED, unsigned, 2);
		binary = concatLeadingZeros(unsigned.toString(2), bits);

		StringBuffer temp = new StringBuffer("A");
//		n = new BigInteger(toTwosBinary(n, bits), 2);
		for (int i = 4; i < bits.length(); i += i)
			temp.append(temp);
		BigInteger mask = new BigInteger(temp.toString(), 16);
		mask = unsigned.xor(mask).subtract(mask);
		return mask.toString();
	}

	/*
	 * Date: 3 May 2020-----------------------------------------------------------
	 * Time created: 09:52:54-------------------------------------------------
	 */
	/**
	 * Converts the {@code BigInteger} argument (which is a decimal) to a
	 * non-negative {@code BigInteger} that is in the same notation that is the same
	 * as the {@code BinaryRep} argument.
	 * <p>
	 * The {@code BigInteger} argument must be in decimal notation. For example,
	 * inputting {@code -25} will give a result of {@code 10011001} if the
	 * {@code BinaryRep} argument is given {@code BinaryRep.SMR} and the
	 * {@code BitLength} argument is given {@code BitLength.BIT_8} as its
	 * parameters. This means that the {@code BigInteger} returned is in the same
	 * notation as the {@code BinaryRep} argument.
	 * </p>
	 * <p>
	 * <b>WARNING:</b> The value returned should not be interpreted as a
	 * {@code BigInteger} (a.k.a decimal), rather, it should be interpreted as a
	 * binary, a {@code BigInteger} is only returned for historical reasons. An
	 * exception to this is if the {@code BinaryRep} parameter is
	 * {@code BinaryRep.MATH}, otherwise do not interpret the result as a decimal,
	 * it is not.
	 * </p>
	 * <p>
	 * Note that the {@code BitLength} argument does not take
	 * {@code BitLength.BIT_UNLTD} and will throw a {@code IllegalArgumentException}
	 * if that is the parameter of the {@code BitLength} argument, an exception to
	 * this is if the {@code BinaryRep} argument {@code == BinaryRep.MATH}.
	 * </p>
	 * 
	 * @param decimal a {@code BigInteger} whose pattern is a normal decimal
	 * @param rep     the {@code BinaryRep} notation (whose pattern is equivalent to
	 *                the number that is to be returned) is returned
	 * @param bits    the bit length of the {@code BigInteger} to be returned
	 * @return the argument {@code decimal} in the {@code rep} argument's notation
	 *         within the specified {@code BitLength}
	 * @throws RuntimeException specifically a
	 *                          {@link java.lang.IllegalArgumentException} if
	 *                          {@code bits == BitLength.BIT_UNLTD && rep != BinaryRep.MATH}
	 */
	public static BigInteger fromDecimal(BigInteger decimal, BinaryRep rep, BitLength bits) {
		if (bits == BitLength.BIT_UNLTD && rep != BinaryRep.MATH)
			new mathaid.IllegalArgumentException(WRONG_BITLENGTH_FOR_BINARY_REP, rep.getClass().getName(),
					bits.getClass().getName());

		switch (rep) {
		case EXCESS_K:
			return new BigInteger(toExcessBinary(decimal, bits), 2);
		case NEGABINARY:
			return new BigInteger(toNegaBinary(decimal, bits), 2);
		case ONE_C:
			return new BigInteger(toOnesBinary(decimal, bits), 2);
		case SMR:
			return new BigInteger(toSmrBinary(decimal, bits), 2);
		case TWO_C:
			return new BigInteger(toTwosBinary(decimal, bits), 2);
		case UNSIGNED:
			return new BigInteger(toUnsignedBinary(decimal, bits), 2);
		case MATH:
		default:
			return decimal;

		}
	}

	/*
	 * Date: 3 May 2020-----------------------------------------------------------
	 * Time created: 09:53:00--------------------------------------------
	 */
	/**
	 * Converts the binary {@code String} which is in the same notation as {@code
	 * rep} to a decimal notational {@code BigInteger}.
	 * <p>
	 * The binary {@code
	 * String} cannot not contain any mathematical sign - unless {@code rep} is
	 * {@code BinaryRep.MATH} - or a {@code NumberFormatException} will be thrown.
	 * Unless the argument {@code rep} equals {@code BinaryRep.MATH}, the {@code
	 * bits} argument cannot equal {@code BitLength.BIT_UNLTD} or an {@code
	 * IllegalArgumentException} will be thrown
	 * </p>
	 * 
	 * @param binary a binary {@code String} in a representation that corresponds to
	 *               {@code BinaryRep}
	 * @param bits   the bit length of the {@code String} argument
	 * @param rep    the {@code BinaryRep} which the {@code String} is using as its
	 *               notation
	 * @return a {@code BigInteger} in decimal notation
	 * @throws RuntimeException specifically a {@link NumberFormatException} if the
	 *                          {@code String} argument represents a signed number
	 *                          or a {@link java.lang.IllegalArgumentException} if
	 *                          {@code bits == BitLength.BIT_UNLTD && rep != BinaryRep.MATH}
	 */
	public static BigInteger toDecimal(String binary, BitLength bits, BinaryRep rep) {
		if ((binary.contains("-") || binary.contains("+")) && rep != BinaryRep.MATH)
			new ValueFormatException(SIGNED_NUMBER_NOT_ACCEPTED, binary);
		if (bits == BitLength.BIT_UNLTD && rep != BinaryRep.MATH)
			new mathaid.IllegalArgumentException(WRONG_BITLENGTH_FOR_BINARY_REP, rep.getClass().getName(),
					bits.getClass().getName());
		switch (rep) {
		case EXCESS_K:
			return new BigInteger(excessToDecimal(binary, bits));
		case NEGABINARY:
			return new BigInteger(negaToDecimal(binary, bits));
		case ONE_C:
			return new BigInteger(onesToDecimal(binary, bits));
		case SMR:
			return new BigInteger(smrToDecimal(binary, bits));
		case TWO_C:
			return new BigInteger(twosToDecimal(binary, bits));
		case UNSIGNED:
			return new BigInteger(unsignedToDecimal(binary, bits));
		case MATH:
		default:
			return new BigInteger(binary, 2);

		}
	}

	/*
	 * Date: 3 May 2020-----------------------------------------------------------
	 * Time created: 15:36:33--------------------------------------------
	 */
	/**
	 * Returns a decimal representation of fractional number ({@code BigDecimal}) in
	 * the specified radix, using the {@code mantissaBits} argument as the bit size
	 * for the mantissa part. For example using {@code Radix.BIN} and 23 for the
	 * {@code mantissaBits} parameter, the result will be such that
	 * 
	 * <pre>
	 * BigDecimal dec = new BigDecimal("0.1");
	 * Radix radix = Radix.BIN;
	 * int bits = 23;
	 * String fraction = Utility.fromDecimal(dec, radix, bits)
	 * System.out.print(fraction);// This prints 0.00011001100110011001100
	 * </pre>
	 * 
	 * Notice that the mantissa part of the above binary fraction has 23 bits, this
	 * is a direct consequence of the argument {@code bits}.
	 * 
	 * @param n            the number to be converted
	 * @param radix        the radix to which the conversion is to be made
	 * @param mantissaBits the number of bits to use in the mantissa part of the
	 *                     returned fraction. This helps to limit the recurring
	 *                     mantissa digits
	 * @return a fractional number {@code String} represented in the specified radix
	 *         using base 10.
	 */
	public static String fromDecimal(BigDecimal n, Radix radix, int mantissaBits) {
		if (BigDecimal.ZERO.compareTo(n) == 0)
			return "0";
		int s = n.signum();
		n = n.abs();
		String fraction = n.toBigInteger().toString(radix.getRadix());
		if (isInteger(n))
			return (s < 0 ? "-" : "") + fraction;
		if (radix == Radix.DEC)
			return (s < 0 ? "-" : "") + n.toPlainString();
		n = n.subtract(new BigDecimal(n.toBigInteger()));
		if (radix == Radix.OCT)
			mantissaBits = (int) Math.ceil(mantissaBits / 3);
		// ((mantissaBits / 3) + (mantissaBits % 3 == 0 ? 0 : 1));
		else if (radix == Radix.HEX)
			mantissaBits = (int) Math.ceil(mantissaBits / 4);
		// ((mantissaBits / 4) + (mantissaBits % 4 == 0 ? 0 : 1));
		StringBuilder f = new StringBuilder(fraction + ".");
		int i = 0;
		do {
			n = n.multiply(new BigDecimal(radix.getRadix() + ""));
			f.append(n.toBigInteger().toString(radix.getRadix()));
			n = n.subtract(new BigDecimal(n.toBigInteger()));
			i += 1;
		} while (BigDecimal.ZERO.compareTo(n) != 0 && i < mantissaBits);

		return (s < 0 ? "-" : "") + f.toString();
	}

	/*
	 * Date: 8 May 2020-----------------------------------------------------------
	 * Time created: 07:20:06--------------------------------------------
	 */
	/**
	 * Converts an integer, floating-point or fraction to a decimal.
	 * <p>
	 * The number {@code String} to be converted can be in a radix other than
	 * decimal, it could also be in IEEE normalised form. If it is in IEEE
	 * normalised form, then a {@code Precision} must be provided or an exception
	 * will be thrown, otherwise a {@code null} parameter for {@code p} is
	 * permissive (although it is advisable to use {@code Precision.UNLTD} instead).
	 * Below is an example of how to use this method:
	 * 
	 * <pre>
	 * String hex = "1.aaacf33p0"; // normalised form
	 * Radix r = Radix.HEX;
	 * Precision p = Precision.valueOf(BitLength.BIT_32.length());// Precision.SINGLE
	 * System.out.println(Utility.toDecimal(hex, r, p)); // prints "1.6667015067726059"
	 * </pre>
	 * 
	 * If a {@code Precision} is not provided, then the result is rounded by a
	 * default rounding (the {@code Precision} argument should be left as
	 * {@code null} or {@code Precision.UNLTD}).
	 * </p>
	 * 
	 * @param num the number to be converted
	 * @param r   the radix of the number to be converted
	 * @param p   the precision to use - if any - or null
	 * @return a decimal equivalent to the {@code String} argument
	 * @throws RuntimeException specifically an {@link ArithmeticException} if there
	 *                          is overflow or underflow
	 */
	public static BigDecimal toDecimal(String num, Radix r, Precision p) {
		/* Ascertain whether x has a radix point */
		boolean hasPoint/* = num.contains(".") */;
		BigDecimal n;
		char c = num.charAt(0);
		int sign = c == '-' ? -1 : c == '+' ? 1 : 1;
		if (c == '-' || c == '+')
			num = num.substring(1);
		String s;
		/* If a Precision is provided */
		if (p != null)
			if (p != Precision.UNLTD) {
				if (p.checkNormalised(num) || num.contains("p")) {// If it is in normalised form
					/* Store the exponent part */
					int exponent = Integer.parseInt(num.substring(num.indexOf("p") + 1));
					/*
					 * isolate the mantissa and convert the integer and mantissa via the linear
					 * conversion taught in secondary school
					 */
					n = toDecimal(num.substring(num.indexOf(".") + 1, num.indexOf("p")),
							num.substring(0, num.indexOf(".")), r, p.getMathContext());
					/* multiply the result by 2 to the power of the exponent */
					n = n.multiply(new BigDecimal("2").pow(exponent, p.getMathContext()), p.getMathContext());
					if (n.compareTo(p.getMaxValue()) > 0 || n.compareTo(p.getMinValue()) < 0)
						try {
							writeTextToFile("", "out.txt",
									MessageFormat.format(OVERFLOW.getLocalizedMessage(), n.toPlainString(),
											p.toString()),
									StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
						} catch (IOException e) {
							e.printStackTrace();
						}
					return n.multiply(new BigDecimal(sign)).round(p.getMathContext());
				}
				s = new BigDecimal(num).toPlainString();
				hasPoint = s.contains(".");
				if (!hasPoint)// If it doesn't have, then it is an integer
					return new BigDecimal(new BigInteger(s, r.getRadix())).multiply(new BigDecimal(sign))
							.round(p.getMathContext());// treat it like an integer
				n = toDecimal(s.substring(s.indexOf(".") + 1), s.substring(0, s.indexOf(".")), r, p.getMathContext());
				if (n.compareTo(p.getMaxValue()) > 0 || n.compareTo(p.getMinValue()) < 0)
					new PrecisionOverflowException(n, p);// thrown exception (ArithmeticException)

				return n.multiply(new BigDecimal(sign)).round(p.getMathContext());
			}

		if (r == Radix.DEC)
			return new BigDecimal(num).multiply(new BigDecimal(sign));

		s = num;// new BigDecimal(num).toPlainString();
		hasPoint = s.contains(".");
		if (!hasPoint) {// If it doesn't have, then it is an integer
			boolean hasExponent = s.contains("E");
			if (hasExponent) {
				StringBuilder sb = new StringBuilder();
				String ss = new BigInteger(s.substring(0, s.indexOf('E')), r.getRadix()).toString();
				sb.append(ss).append(s.substring(s.indexOf('E')));
				return new BigDecimal(sb.toString()).multiply(new BigDecimal(sign));
			}
			return new BigDecimal(new BigInteger(s, r.getRadix()));// treat it like an integer
		}
		n = toDecimal(s.substring(s.indexOf(".") + 1), s.substring(0, s.indexOf(".")), r, MathContext.DECIMAL128);

		return n.multiply(new BigDecimal(sign));
	}

	/*
	 * Date: 7 May 2020-----------------------------------------------------------
	 * Time created: 21:29:32-------------------------------------------- 1.aaacf33
	 */
	/**
	 * Attempts to convert {@code num} (specified in the {@code Radix} argument) to
	 * decimal.
	 * 
	 * @param fraction the non negative fraction number {@code String} part in the
	 *                 specified {@code Radix} which is without any exponentiation,
	 *                 formatting, leading zeros, or radix point. In other words the
	 *                 mantissa as an integer.
	 * @param integer  the integer part of the real number in the specified
	 *                 {@code Radix}
	 * @param radix    the radix of {@code num}
	 * @param mc       a {@code MathContext} object to specify rounding behaviour of
	 *                 {@link BigDecimal#pow(int, MathContext)}. Without this
	 *                 object, this method will throw an {@code ArithmeticException}
	 *                 with it's message being "non-terminating decimal".
	 * @return a decimal representation of the {@code fraction} and {@code integer}
	 *         arguments.
	 * @throws RuntimeException specifically a {@link NumberFormatException} if
	 *                          fraction or integer is not a valid decimal and the
	 *                          {@code Radix} argument is {@code Radix.DEC}
	 */
	private static BigDecimal toDecimal(String fraction, String integer, Radix radix, MathContext mc) {
		boolean isNegative = (new BigInteger(integer, radix.getRadix()).signum() < 0);
		if (radix == Radix.DEC)
			try {
				return isNegative ? new BigDecimal(new BigInteger(integer)).subtract(new BigDecimal(fraction))
						: new BigDecimal(new BigInteger(integer)).add(new BigDecimal(fraction));
			} catch (NumberFormatException e) {
				new ValueFormatException(EMPTY, e, e.getMessage());// thrown exception (NumberFormatException)
			}
//		String integer = new BigInteger(num.substring(0, num.indexOf(".")), radix.getRadix()).toString();
		BigDecimal radixIndex = BigDecimal.ZERO;
		for (int i = 1; i <= fraction.length(); i++) {
			int n = Integer.parseInt("-" + i);
			radixIndex = radixIndex.add(new BigDecimal(digitForChar(fraction.charAt(i - 1), radix.getRadix()))
					.multiply(new BigDecimal(radix.getRadix() + "").pow(n, mc)));
		}
		return isNegative ? new BigDecimal(new BigInteger(integer, radix.getRadix())).subtract(radixIndex)
				: new BigDecimal(new BigInteger(integer, radix.getRadix())).add(radixIndex);
	}

	/*
	 * Date: 7 May 2020-----------------------------------------------------------
	 * Time created: 20:09:07--------------------------------------------
	 */
	/**
	 * Determines the character representation for a specific digit in the specified
	 * radix.
	 * <p>
	 * The digit returned is a {@code String} whose length is 1 and represents the
	 * {@code digit} in that particular radix. For example:
	 * 
	 * <pre>
	 * int digit = 10;
	 * int radix = 16;
	 * System.out.println(digit, radix);// prints "a"
	 * </pre>
	 * 
	 * The digit {@code String} returned - if it is a letter - is always lower case.
	 * </p>
	 * 
	 * @param digit a digit in the specified radix
	 * @param radix an {@code int} within the following range:<br>
	 *              <code>Character.MIN_RADIX >= radix || radix <= Character.MAX_RADIX</code>
	 * @return a {@code String} representing the {@code digit} argument.
	 * @throws RuntimeException specifically a {@link NumberFormatException} if
	 *                          <ul>
	 *                          <li>{@code radix} is out of range for java's radixes
	 *                          as specified by {@link Character#MIN_RADIX} and
	 *                          {@link Character#MAX_RADIX}</li>
	 *                          <li>{@code digit} is an illegal digit for
	 *                          {@code radix}.</li>
	 *                          </ul>
	 */
	public static String charForDigit(int digit, int radix) {
		final String constant = "0123456789abcdefghijklmnopqrstuvwxyz";
		if (radix > Character.MAX_RADIX || radix < Character.MIN_RADIX)
			new ValueFormatException(UNSUPPORTED_RADIX);

		if (Math.abs(digit) >= radix)
			new ValueFormatException(ILLEGAL_DIGIT, digit);

		try {
			return Character.toString(constant.charAt(Math.abs(digit)));
		} catch (IndexOutOfBoundsException e) {
			/*
			 * throw exception (IndexOutOfBoundsException)
			 */
			new IndexBeyondLimitException(Math.abs(digit), constant.length());
			throw e;
		}
	}

	/*
	 * Date: 7 May 2020-----------------------------------------------------------
	 * Time created: 20:40:59--------------------------------------------
	 */
	/**
	 * Returns the numeric value of the character {@code c} in the specified radix.
	 *
	 * @param c     a digit (or letter) in the specified radix
	 * @param radix an {@code int} within the following range:<br>
	 *              <code>Character.MIN_RADIX >= radix || radix <= Character.MAX_RADIX</code>
	 * @return the decimal value of {@code c}
	 * @throws RuntimeException specifically a {@link NumberFormatException} if
	 *                          <ul>
	 *                          <li>{@code radix} is out of range for java's radixes
	 *                          as specified by {@link Character#MIN_RADIX} and
	 *                          {@link Character#MAX_RADIX}</li>
	 *                          <li>{@code digit} is an illegal digit for
	 *                          {@code radix}.</li>
	 *                          </ul>
	 */
	public static int digitForChar(char c, int radix) {
		final String constant = "0123456789abcdefghijklmnopqrstuvwxyz";
		if (radix > Character.MAX_RADIX || radix < Character.MIN_RADIX)
			new ValueFormatException(UNSUPPORTED_RADIX);
		int index = constant.indexOf(Character.toLowerCase(c));
		if (index >= radix)
			new ValueFormatException(ILLEGAL_DIGIT, String.valueOf(c));
		return index;
	}

	/*
	 * Date: 3 May 2020-----------------------------------------------------------
	 * Time created: 20:36:45--------------------------------------------
	 */
	/**
	 * Returns the integer argument as a floating point decimal {@code String} in
	 * the specified. The {@code String} returned may be fractional or not, it may
	 * be in IEEE 754 normalised form if the {@code true} is passed to the
	 * {@code boolean} parameter. The {@code Precision} argument acts as a bit
	 * length specifier for the integer argument, as a result,
	 * {@code Precision.UNLTD} is prohibited and would cause an exception to be
	 * thrown. Also, if the argument is signed with a mathematical sign, this
	 * method's result becomes undefined, so, it should be prohibited (although an
	 * unsigned equivalent is used for signed integers passed as parameters to this
	 * method, this practice should be discouraged).
	 * 
	 * @param bitPattern   the number bit pattern as a {@code BigInteger}
	 * @param p            the floating-point precision to use as specified by
	 *                     {@code Precision}
	 * @param radix        the radix to which the result is converted to
	 * @param isNormalised enables normalisation of the result i.e returning a
	 *                     {@code String} that is IEEE 754 normal form compliant
	 * @return a decimal {@code String} which is the floating point equivalent of
	 *         the {@code bitPattern} argument
	 * @throws RuntimeException specifically a {@link NumberFormatException} if
	 *                          {@code p == Precision.UNLTD} or the
	 *                          {@code BigInteger} argument is signed
	 */
	public static String integerToFloatingPoint(BigInteger bitPattern, Precision p, Radix radix, boolean isNormalised) {
		if (p == Precision.UNLTD)
			new PrecisionException(UNLIMITED_PRECISION_NOT_ALLOWED, "Precision.", p.toString());
		if (bitPattern.signum() < 0)
			bitPattern = fromDecimal(bitPattern, BinaryRep.UNSIGNED, BitLength.valueOf(p.getBitLength()));
		if (bitPattern.signum() < 0)
			new ValueFormatException(SIGNED_NUMBER_NOT_ACCEPTED, bitPattern, radix.getRadix());
		BigDecimal decimal = p.toDecimal(bitPattern);
		if (isNormalised)
			return p.normalise(decimal.toString(), radix);
		return fromDecimal(decimal, radix, p.getSignificandBits() - 1);
	}

	/*
	 * Date: 1 May 2020-----------------------------------------------------------
	 * Time created: 13:49:21--------------------------------------------
	 */
	/**
	 * Complements the {@code BigInteger} arguments and returns it. The
	 * {@code BitLength} argument is analogous to the number of complemented bits in
	 * front of the most significant bits. For instance without the constraints of a
	 * bit length, the method would interpret 101 as 10. but with the constraint of
	 * a bit length argument, an 8 bit complement of 101 will be 11111010.
	 * 
	 * @param n    the number to complemented
	 * @param bits the number of bits to use for the operation
	 * @return the complement of the {@code BigInteger} argument
	 */
	public static BigInteger not(BigInteger n, BitLength bits) {
		BigInteger mask = new BigInteger("1" + zeros(bits.length()), 2);
		n = n.abs().or(mask);
		String bin = n.toString(2);
		bin = bin.replaceAll("0", "2").replaceAll("1", "0").replaceAll("2", "1");
		return new BigInteger(bin, 2);
	}

	/*
	 * Date: 1 Oct 2020-----------------------------------------------------------
	 * Time created: 05:52:06--------------------------------------------
	 */
	/**
	 * Tests a method's performance by printing the time it takes to execute into
	 * the specified output.
	 * 
	 * @param out    the specified {@code PrintStream}.
	 * @param clock  an object for localised time format
	 * @param caller the object or class from which the method is called, If this
	 *               method is static, then it can be left as {@code null}.
	 * @param m      the method to be tested.
	 * @param args   the method's arguments. may be of 0 length or null, if this
	 *               method does not have parameters.
	 * @return the return value of the specified method or {@code null}, if the
	 *         return type is {@code void}.
	 */
	public static Object getBenchMark(PrintStream out, Clock clock, Object caller, Method m, Object... args) {
		Object returnValue = null;
		Instant instant = clock == null ? Instant.now() : Instant.now(clock);
		out.println("BenchMarking started at " + instant.truncatedTo(ChronoUnit.SECONDS) + " ... ");
		long nano = 0;
		try {
			nano = System.nanoTime();
			returnValue = m.invoke(caller, args);
		} catch (Throwable th) {
			th.printStackTrace();
		}
		double current = System.nanoTime() - nano;
		current /= 1_000_000.0D;// now in milli-seconds
		final double e = current;
		current /= 1e3;// now in seconds (current / 1000.0)
		long[] msms = getMSNS(current / 60.0D);
		assert msms.length == 3;

		out.println("This program took " + msms[0] + " minute(s), " + msms[1] + " second(s), " + e
				+ " millisecond(s) to execute.");

		return returnValue;
	}

	/*
	 * Date: 16 Jun 2020-----------------------------------------------------------
	 * Time created: 12:15:49--------------------------------------------
	 */
	/**
	 * Converts the following <code>double</code> to a string showing degrees
	 * (&#x00B0;), minutes(') and seconds(").
	 * 
	 * @param a a double number in seconds
	 * @return a string in the format: <br />
	 *         degrees&#x00B0;minutes'seconds"
	 */
	private static String toDegrees(double a) {
		if (a > Integer.MAX_VALUE || a < Integer.MIN_VALUE)
			return a + "";
		long degree = (long) a;
		String deg = degree + "";
		double a1 = degree;
		double a2 = a - a1;
		a2 *= 60;
		long minutes = (long) a2;
		String min = minutes + "";
		min = min.length() > 1 ? min : "0" + min;
		double a3 = minutes;
		double a4 = a2 - a3;
		a4 *= 60;
		double a5 = Math.round(a4);
		long seconds = (long) a5;
		String sec = seconds + "";
		sec = sec.length() > 1 ? sec : "0" + sec;
		return deg + "\u00B0" + min + "'" + sec + "\"";

	}

	/*
	 * Date: 15 Jun 2020-----------------------------------------------------------
	 * Time created: 21:39:24--------------------------------------------
	 */
	/**
	 * Returns the minute, seconds and nanoseconds interpretation of it's double
	 * value parameter.
	 * 
	 * @param seconds a double value, usually one that represent a time unit in
	 *                seconds
	 * @return minute, second and the reminder as a <code>long</code>
	 */
	private static long[] getMSNS(double seconds) {
		StringBuilder returnValue = new StringBuilder(toDegrees(seconds));
		long[] rv = { Long.valueOf(returnValue.substring(0, returnValue.indexOf("\u00B0"))),
				Long.valueOf(returnValue.substring(returnValue.indexOf("\u00B0") + 1, returnValue.indexOf("'"))),
				Long.valueOf(returnValue.substring(returnValue.indexOf("'") + 1, returnValue.indexOf("\""))) };
		return rv;
	}

	/*
	 * Date: 13 Jul 2020-----------------------------------------------------------
	 * Time created: 14:21:42--------------------------------------------
	 */
	/**
	 * <p>
	 * Attempts to serialise an object and then returns a deserialized version of
	 * the object that is without reference.
	 * </p>
	 * <p>
	 * This is usually for mutable objects that needs to be dereferenced.
	 * 'Dereferenced' in this case is to make an object that hitherto was a
	 * reference to another, unique i.e unlink it from the original such that it
	 * will henceforth not reference it. The following demonstrates this:
	 * 
	 * <pre>
	 * List&lt;String&gt; original = new ArrayList&lt;&gt;();
	 * // ...populate the list
	 * original.add("FORTRAN");
	 * original.add("COBOL");
	 * original.add("PASCAL");
	 * original.add("BASIC");
	 * original.add("ADA");
	 * List&lt;String&gt; reference = original;// links to the original list
	 * reference.removeAll(reference);// oops! now original is empty
	 * String result = original.stream().collect(Collectors.joining());
	 * System.out.println(result);// prints an empty string!
	 * </pre>
	 * 
	 * The above shows that referencing mutable objects can be problematic because
	 * the copy now has the same mutable rights (write permissions) to the object as
	 * the original. To avoid this, programmers can can reassign the original,
	 * however the alternative - which is this method - is has lesser lines of code:
	 * 
	 * <pre>
	 * List&lt;String&gt; original = new ArrayList&lt;&gt;();
	 * // ...populates the list
	 * original.add("FORTRAN");
	 * original.add("COBOL");
	 * original.add("PASCAL");
	 * original.add("BASIC");
	 * original.add("ADA");
	 * List&lt;String&gt; reference = Utility.serializeAndDeserializeObject(original);// unlinks from the original
	 * // now reference is different from original
	 * reference.removeAll(reference);// very safe!
	 * String result = original.stream().collect(Collectors.joining());
	 * System.out.println(result);// prints all the elements as you would expect
	 * </pre>
	 * 
	 * @param <T> the type of object.
	 * @param obj the object to operated upon.
	 * @return a deserialised object that contains no reference to the assigned
	 *         object.
	 * @throws IOException        if the serialisation or deserialisation went
	 *                            wrong.
	 * @throws ClassCastException if during deserialisation, the argument cannot be
	 *                            cast to the type {@code T} which is rare, but wil
	 *                            most likely happpen if the argument implements the
	 *                            special serialisation methods defined in the JRE
	 *                            and does not handle it's fields properly within
	 *                            the methods.
	 * @implNote This implementation is decreases performance, if there is an
	 *           alternative to this method please use it, otherwise use this method
	 *           sparingly, so as to maintain optimal performance in applications.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T serializeAndDeserializeObject(T obj) {
		T t = null;
		try {
			File f = File.createTempFile("stream", "objects");
			ObjectOutput i = new ObjectOutputStream(new FileOutputStream(f));
			i.writeObject(obj);
			i.close();
			ObjectInput o = new ObjectInputStream(new FileInputStream(f));
			t = (T) o.readObject();
			o.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return t;
	}

	/*
	 * Date: 23 Jul 2020-----------------------------------------------------------
	 * Time created: 13:19:18--------------------------------------------
	 */
	/**
	 * Return a {@code String} of a {@code Fraction} in the format: <blockquote>
	 * 
	 * <pre>
	 *   <i>b</i>
	 * A &#x2015;
	 *   <i>c</i>
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param fr the fraction whose quotient is to be formatted.
	 * @return a formatted {@code String}.
	 */
	public static String formatFraction(Fraction fr) {
		if (fr.isInteger())
			return Long.toString(fr.getQuotient());
		final boolean integerIsZero = fr.getQuotient() == 0;
		final String num = String.valueOf(fr.getNumerator());
		final String denom = String.valueOf(fr.getDenominator());
		final String integer = String.valueOf(fr.getQuotient());
		final String separator = System.lineSeparator();
		final String space = " ";
		StringBuilder sb = new StringBuilder();
		if (!integerIsZero) {
			for (int i = 0; i <= integer.length(); i++)
				sb.append(space);

		}
		if (num.length() < denom.length() / 2)
			for (int i = 0; i < denom.length() / 2; i++)
				sb.append(space);
		else
			sb.append(space);

		sb.append(num);
		sb.append(separator);
		if (!integerIsZero) {
			sb.append(integer);
			sb.append(space);
		}
		for (int i = 0; i < Math.max(num.length(), denom.length()) + 2; i++) {
			sb.append("\u2015");
		}
		sb.append(separator);
		if (!integerIsZero) {
			for (int i = 0; i <= integer.length(); i++) {
				sb.append(space);
			}
		}
		if (denom.length() < num.length() / 2) {
			for (int i = 0; i < num.length() / 2; i++) {
				sb.append(space);
			}
		} else
			sb.append(space);
		sb.append(denom);
		sb.append(separator);
		return sb.toString();
	}

	/*
	 * Date: 22 Nov 2020-----------------------------------------------------------
	 * Time created: 15:21:24--------------------------------------------
	 */
	/**
	 * Return a {@code String} of a {@code BigFraction} in the format: <blockquote>
	 * 
	 * <pre>
	 *   <i>b</i>
	 * A &#x2015;
	 *   <i>c</i>
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param fr the fraction whose quotient is to be formatted.
	 * @return a formatted {@code String}.
	 */
	public static String formatFraction(BigFraction fr) {
		if (fr.isInteger())
			return fr.toMixed()[0].toString();
		final boolean integerIsZero = fr.toMixed()[0].signum() == 0;
		final String num = String.valueOf(fr.getNumerator());
		final String denom = String.valueOf(fr.getDenominator());
		final String integer = fr.toMixed()[0].toString();
		final String separator = System.lineSeparator();
		final String space = " ";
		StringBuilder sb = new StringBuilder();
		if (!integerIsZero) {
			for (int i = 0; i <= integer.length(); i++)
				sb.append(space);

		}
		if (num.length() < denom.length() / 2)
			for (int i = 0; i < denom.length() / 2; i++)
				sb.append(space);
		else
			sb.append(space);

		sb.append(num);
		sb.append(separator);
		if (!integerIsZero) {
			sb.append(integer);
			sb.append(space);
		}
		for (int i = 0; i < Math.max(num.length(), denom.length()) + 2; i++) {
			sb.append("\u2015");
		}
		sb.append(separator);
		if (!integerIsZero) {
			for (int i = 0; i <= integer.length(); i++) {
				sb.append(space);
			}
		}
		if (denom.length() < num.length() / 2) {
			for (int i = 0; i < num.length() / 2; i++) {
				sb.append(space);
			}
		} else
			sb.append(space);
		sb.append(denom);
		sb.append(separator);
		return sb.toString();
	}

	/*
	 * Date: 26 Jul 2020-----------------------------------------------------------
	 * Time created: 19:18:06--------------------------------------------
	 */
	/**
	 * Check whether the argument is null, then throw an {@code InstantiationError}
	 * if it isn't. Used by singletons in their constructors to prevent object
	 * instantiation.
	 * 
	 * @param o the object to checked
	 * @throws InstantiationError if the argument is not {@code null}
	 */
	public static void assertIsNull(Object o) throws InstantiationError {
		if (o != null)
			new ObjectInstantiationError(STATIC_SINGLETON_ERROR);

	}

	/*
	 * Date: 30 Jul 2020-----------------------------------------------------------
	 * Time created: 09:28:42--------------------------------------------
	 */
	/**
	 * Returns the first argument if it is non-{@code null} and otherwise returns
	 * the non-{@code null} second argument.
	 *
	 * @param obj        an object
	 * @param defaultObj a non-{@code null} object to return if the first argument
	 *                   is {@code null}
	 * @param <T>        the type of the reference
	 * @return the first argument if it is non-{@code null} and otherwise the second
	 *         argument if it is non-{@code null}
	 * @throws NullPointerException if both {@code obj} is null and
	 *                              {@code defaultObj} is {@code null}
	 */
	public static <T> T requireNonNullElse(T obj, T defaultObj) {
		if (obj != null)
			return obj;
		return defaultObj;
	}

	/*
	 * Date: 25 Sep 2020-----------------------------------------------------------
	 * Time created: 19:49:44--------------------------------------------
	 */
	/**
	 * Compares the numerical value of 2 {@code BigDecimal}. This method replaces
	 * the {@code BigDecimal} class' {@link BigDecimal#compareTo(BigDecimal)},
	 * because it seems that the {@code BigDecimal} class's method slows down the
	 * trigonometrical methods of {@code Arith} class.
	 * 
	 * @param x the first argument to be compared numerically
	 * @param y the second argument to be compared numerically.
	 * @return -1, 0, or 1 as {@code x} is numerically less than, equal to, or
	 *         greater than {@code y}.
	 */
	private static int compareTo(BigDecimal x, BigDecimal y) {
		if (x.signum() == 0)
			return y.signum();
		else if (y.signum() == 0)
			return x.signum();
		else if (x.signum() != -1) {// if x is positive
			if (y.signum() != -1)// And y is also positive
				return x.subtract(y).signum();// Math.signum(x - y)
			return x.add(y).signum();// Math.signum(x + (-y))
		}
		return y.signum() > 0 ? -1 : y.abs().add(x).signum();
	}

	/*
	 * Date: 20 Apr 2021-----------------------------------------------------------
	 * Time created: 07:52:47--------------------------------------------
	 */
	/**
	 * Checks whether the argument string is a rational number or can be converted
	 * to such. The String expected can be in decimal form or in the form
	 * [numerator]/[denominator]
	 * 
	 * @param s a {@code String} in decimal form or in the form
	 *          [numerator]/[denominator]. Note that in the
	 *          [numerator]/[denominator] form, both numerator and denominator are
	 *          expected to be integers in base ten.
	 * @return {@code true} if this is a rational number or can be converted to a
	 *         common fraction and {@code false} if otherwise.
	 */
	public static boolean isFraction(String s) {
		try {
			return Digits.fromSegmentString(s).isRational();
		} catch (@SuppressWarnings("unused") NumberFormatException e) {
		}
		return false;
	}

	/*
	 * Date: 20 Apr 2021-----------------------------------------------------------
	 * Time created: 08:01:51--------------------------------------------
	 */
	/**
	 * Converts the {@code BigFraction} object to a TeX fraction format
	 * {@code String}. Only the numerator and the denominator is returned, this does
	 * not return a common fraction in mixed form.
	 * 
	 * @param bf                the fraction to be converted
	 * @param decimalPoint      a {@code char} value that is substituted for the
	 *                          decimal point during formatting
	 * @param mantissaDelimiter a {@code char} value that is used to separate the
	 *                          fractional digits into groups
	 * @param integerDelimiter  a {@code char} value that is used to separate the
	 *                          integer digits into groups
	 * @param digitsPerUnit     the number of digits per group within the number
	 * @return a common fraction {@code String} in TeX form
	 * 
	 * @see #toTeXString(BigInteger[], char, int)
	 */
	public static String toTexString(BigFraction bf, char decimalPoint, char mantissaDelimiter, char integerDelimiter,
			int digitsPerUnit) {
		if ((!bf.isRational()) || bf.isInteger())
			return toTexString(bf.getFraction(), decimalPoint, integerDelimiter, mantissaDelimiter, digitsPerUnit);

		return (bf.signum() == 1 ? "" : "-") + "\\frac{"
				+ toTexString(bf.abs().getNumerator(), integerDelimiter, digitsPerUnit) + "}{"
				+ toTexString(bf.abs().getDenominator(), integerDelimiter, digitsPerUnit) + "}";

	}

	/*
	 * Date: 24 Apr 2021-----------------------------------------------------------
	 * Time created: 18:26:46--------------------------------------------
	 */
	/**
	 * <p>
	 * Converts a {@code BigInteger} array - which represents a
	 * {@link mathaid.calculator.base.value.BigFraction#toMixed() mixed fraction} -
	 * to a TeX fraction decimal format {@code String} with each part of the figure
	 * representing a decimal formatted with the input arguments. For example:
	 * 
	 * <pre>
	 * BigInteger[] mixedFraction = new BigFraction("3054577/1128").toMixed();
	 * String TeX = Utility.toTeXString(mixedFraction, ',', 3);
	 * System.out.println(TeX); // prints "2,707\frac{1,081}{1,128}"
	 * </pre>
	 * 
	 * Note that this does not take {@code Locale} into account.
	 * </p>
	 * <p>
	 * The array must have exactly 3 elements or an
	 * {@link ArrayIndexOutOfBoundsException} will be thrown. The array format is
	 * read as thus: first element contains the whole number, the second the
	 * numerator and the third the denominator.
	 * </p>
	 * 
	 * @param mixedFraction    a {@code BigInteger} array that represents a mixed
	 *                         fraction
	 * @param integerDelimiter a {@code char} value that is used to separate the
	 *                         digits into groups
	 * @param digitsPerUnit    the number of digits per group within the numbers
	 * @return a decimal {@code String} in the TeX format:
	 *         <code>mixedFraction[0]</code>\frac{<code>mixedFration[1]</code>}{<code>mixedFraction[2]</code>}<br
	 *         />
	 *         The figures are formatted using the provided arguments
	 * @throws ArrayIndexOutOfBoundsException if the size of the provided array is
	 *                                        not equal to 3
	 */
	public static String toTeXString(BigInteger[] mixedFraction, char integerDelimiter, int digitsPerUnit) {
		if (mixedFraction.length != 3) {
			new ArraySizeException(ARRAY_SIZE_OUTSIDE_LIMIT, 3);
		}

		String integer = toTexString(mixedFraction[0], integerDelimiter, digitsPerUnit);
		String numerator = toTexString(mixedFraction[1], integerDelimiter, digitsPerUnit);
		String denominator = toTexString(mixedFraction[2], integerDelimiter, digitsPerUnit);

		return integer + "\\frac{" + numerator + "}{" + denominator + "}";
	}

	/*
	 * Date: 25 Apr 2021-----------------------------------------------------------
	 * Time created: 09:26:04--------------------------------------------
	 */
	/**
	 * <p>
	 * Returns a TeX format of the provided {@code BigInteger} as a decimal
	 * {@code String}. The result is a {@code String} formatted with the specified
	 * {@code integerDelimeter} and {@code digitsPerUnit}. For example:
	 * 
	 * <pre>
	 * BigInteger n = new BigInteger("1234567890");
	 * String s = Utility.toTeXString(n, ',', 3);
	 * System.out.print(s); // prints 1,234,567,890
	 * </pre>
	 * 
	 * Note that this method does not take {@code Locale} into account.
	 * </p>
	 * 
	 * @param n                a {@code BigInteger} object to be formatted
	 * @param integerDelimiter a {@code char} value used for separating the digits
	 *                         into groups. For the above example, this value is ','
	 *                         (comma)
	 * @param digitsPerUnit    the number of digits per group. For the above
	 *                         example, this value is 3.
	 * @return a decimal {@code String} in TeX format that is not localised (i.e
	 *         does not take {@code Locale} into consideration)
	 */
	public static String toTexString(BigInteger n, char integerDelimiter, int digitsPerUnit) {
		return toTexString(new BigDecimal(n), '\u0000', integerDelimiter, '\u0000', digitsPerUnit);
	}

	/*
	 * Date: 22 Apr 2021-----------------------------------------------------------
	 * Time created: 10:43:42--------------------------------------------
	 */
	/**
	 * Splits the value around the provided token once. The result is a 2-element
	 * array in which the string before the token occupies index 0 and the string
	 * after the token occupies index 1
	 * 
	 * @param value the value to be split
	 * @param token the character around which the value is split. This must be
	 *              present at least one inside the value
	 * @return a 2-element array if the string contains the token argument, else a
	 *         single element array which contains the original
	 */
	private static String[] split(String value, char token) {
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if (c == token) {
				return new String[] { value.substring(0, i), value.substring(i + 1) };
			}
		}
		return new String[] { value };
	}

	/*
	 * Date: 22 Apr 2021-----------------------------------------------------------
	 * Time created: 11:41:16--------------------------------------------
	 */
	/**
	 * <p>
	 * Returns a TeX format of the provided {@code BigDecimal} as a decimal
	 * {@code String}. The result is a {@code String} formatted with the specified
	 * {@code decimalPoint}, {@code integerDelimeter}, {@code mantissaDelimeter} and
	 * {@code digitsPerUnit}. For example:
	 * 
	 * <pre>
	 * BigDecimal n = new BigDecimal("1234.567890");
	 * String s = Utility.toTeXString(n, '.', ',', ' ', 3);
	 * System.out.print(s); // prints 1,234.567 890
	 * </pre>
	 * 
	 * Note that this method does not take {@code Locale} into account.
	 * </p>
	 * 
	 * @param n                 a {@code BigDecimal} object to be formatted
	 * @param decimalPoint      a {@code char} value to be used a substitute for the
	 *                          decimal point
	 * @param integerDelimiter  a {@code char} value used for separating the integer
	 *                          digits into groups. For the above example, this
	 *                          value is ',' (comma)
	 * @param mantissaDelimiter a {@code char} value used for separating the
	 *                          fractional digits into groups. For the above
	 *                          example, this value is ' ' (horizontal white space
	 *                          '\u0020')
	 * @param digitsPerUnit     an {@code int} value that specifies the number of
	 *                          digits per group. For the above example, this value
	 *                          is 3. This means that after every 3 character, a
	 *                          delimiter (integer or mantissa) is inserted.
	 * @return a decimal {@code String} in TeX format that is not localised (i.e
	 *         does not take {@code Locale} into consideration)
	 */
	public static String toTexString(BigDecimal n, char decimalPoint, char integerDelimiter, char mantissaDelimiter,
			int digitsPerUnit) {
		char sign = n.signum() >= 0 ? Character.MAX_VALUE : '-';
		final boolean isInteger = isInteger(n);
		String[] nb = split(n.abs().toPlainString(), '.');
		StringBuilder sb = new StringBuilder();

		for (int i = nb[0].length() - 1; i >= 0; i--) {
			char c = nb[0].charAt(i);
			if ((nb[0].length() - 1 - i) % digitsPerUnit == 0 && i >= 0 && i != nb[0].length() - 1)
				sb.insert(0, integerDelimiter);

			sb.insert(0, c);
		}
		sb.insert(0, sign);

		if (isInteger)
			return sb.toString();

		sb.append(decimalPoint);

		for (int i = 0; i < nb[1].length(); i++) {
			char c = nb[1].charAt(i);
			if (i % digitsPerUnit == 0 && i != 0 && i != nb[1].length())
				sb.append(mantissaDelimiter);
			sb.append(c);
		}

		return sb.toString();

	}

	/*
	 * Date: 22 Apr 2021-----------------------------------------------------------
	 * Time created: 11:42:47--------------------------------------------
	 */
	/**
	 * <p>
	 * Returns a TeX format of the provided {@code Complex} as a complex decimal
	 * {@code String}. The result is a {@code String} formatted with the specified
	 * {@code decimalPoint}, {@code integerDelimeter}, {@code mantissaDelimeter} and
	 * {@code digitsPerUnit}. For example:
	 * 
	 * <pre>
	 * Complex z = Complex.valueOf(-1234567890).sqrt().add(-1234567890);
	 * out.println(Utility.toTexString(z, '.', ',', ' ', 3)); // prints -1,234,568,000 + 35,136.42i
	 * </pre>
	 * 
	 * The approximation is due to using {@code Complex} static method. Note that
	 * this method does not take {@code Locale} into account.
	 * </p>
	 * 
	 * @param z                 a {@code Complex} object to be formatted
	 * @param decimalPoint      a {@code char} value to be used a substitute for the
	 *                          decimal point
	 * @param integerDelimiter  a {@code char} value used for separating the integer
	 *                          digits into groups. For the above example, this
	 *                          value is ',' (comma)
	 * @param mantissaDelimiter a {@code char} value used for separating the
	 *                          fractional digits into groups. For the above
	 *                          example, this value is ' ' (horizontal white space
	 *                          '\u0020')
	 * @param digitsPerUnit     an {@code int} value that specifies the number of
	 *                          digits per group. For the above example, this value
	 *                          is 3. This means that after every 3 character, a
	 *                          delimiter (integer or mantissa) is inserted.
	 * @return a decimal {@code String} in TeX format that is not localised (i.e
	 *         does not take {@code Locale} into consideration)
	 */
	public static String toTexString(Complex z, char decimalPoint, char integerDelimiter, char mantissaDelimiter,
			int digitsPerUnit) {
		if (z.isIndeterminate())
			return "i";
		final StringBuilder sb = new StringBuilder();

		if (z.isReal())
			sb.append(toTexString(z.real(), decimalPoint, integerDelimiter, mantissaDelimiter, digitsPerUnit));
		else if (z.isImaginary()) {
			sb.append(toTexString(z.imaginary(), decimalPoint, integerDelimiter, mantissaDelimiter, digitsPerUnit));
			sb.append("i");
		} else {
			sb.append(toTexString(z.real(), decimalPoint, integerDelimiter, mantissaDelimiter, digitsPerUnit));
			if (z.imaginary().signum() >= 0)
				sb.append(" + ");
			else
				sb.append(" - ");

			if (z.imaginary().abs().compareTo(BigDecimal.ONE) != 0)
				sb.append(toTexString(z.imaginary().abs(), decimalPoint, integerDelimiter, mantissaDelimiter,
						digitsPerUnit));
			sb.append("i");
		}

		return sb.toString();
	}

	/*
	 * Date: 21 Jun 2021-----------------------------------------------------------
	 * Time created: 15:56:55--------------------------------------------
	 */
	/**
	 * <p>
	 * Returns a TeX format of the provided {@code String} as . The result is a
	 * {@code String} formatted with the specified {@code decimalPoint},
	 * {@code integerDelimeter}, {@code mantissaDelimeter} and
	 * {@code digitsPerUnit}. Note that the decimal point must remain as the '.'
	 * character or this method will be undefined.
	 * </p>
	 * <p>
	 * This method just formats without checking the content of the number, hence if
	 * "asdgf" was the argument, it will still proceed to format it. This is useful
	 * for numbers in forms that cannot be parsed by the {@code BigDecimal} and
	 * {@code BigInteger} constructors such as a base 16 fraction. Note that this
	 * method does not take {@code Locale} into account.
	 * </p>
	 * 
	 * @param s                 a {@code String} object to be formatted
	 * @param decimalPoint      a {@code char} value to be used a substitute for the
	 *                          decimal point
	 * @param integerDelimiter  a {@code char} value used for separating the integer
	 *                          digits into groups
	 * @param mantissaDelimiter a {@code char} value used for separating the
	 *                          fractional digits into groups.
	 * @param digitsPerUnit     an {@code int} value that specifies the number of
	 *                          digits per group. This means that after every 3
	 *                          character, a delimiter (integer or mantissa) is
	 *                          inserted, as such the integer and mantissa are
	 *                          decoded by the presence of the '.' character that
	 *                          acts as a radix point.
	 * @return a {@code String} in TeX format that is not localised (i.e does not
	 *         take {@code Locale} into consideration)
	 */
	public static String toTexString(String s, char decimalPoint, char integerDelimiter, char mantissaDelimiter,
			int digitsPerUnit) {
		char sign = s.charAt(0) == '-' ? '-' : s.charAt(0) == '+' ? '+' : Character.MAX_VALUE;
		if (sign != Character.MAX_VALUE)
			s = s.substring(1, s.length());
		final boolean isInteger = !s.contains(".");
		String[] nb = split(s, '.');
		StringBuilder sb = new StringBuilder();

		for (int i = nb[0].length() - 1; i >= 0; i--) {
			char c = nb[0].charAt(i);
			if ((nb[0].length() - 1 - i) % digitsPerUnit == 0 && i >= 0 && i != nb[0].length() - 1)
				sb.insert(0, integerDelimiter);

			sb.insert(0, c);
		}
		sb.insert(0, sign);

		if (isInteger)
			return sb.toString();

		sb.append(decimalPoint);

		for (int i = 0; i < nb[1].length(); i++) {
			char c = nb[1].charAt(i);
			if (i % digitsPerUnit == 0 && i != 0 && i != nb[1].length())
				sb.append(mantissaDelimiter);
			sb.append(c);
		}

		return sb.toString();
	}

	/*
	 * Date: 18 Jul 2021-----------------------------------------------------------
	 * Time created: 16:41:25--------------------------------------------
	 */
	/**
	 * Checks if the argument is a decimal {@code String} and returns {@code true}
	 * if it is or {@code false} if otherwise
	 * 
	 * @param s the {@code String} to be checked
	 * @return {@code true} if the argument is a value that can be passed to the
	 *         {@link BigDecimal#BigDecimal(String) BigDecimal constructor} or
	 *         {@code false} if otherwise
	 */
	public static boolean isNumber(String s) {
		try {
			new BigDecimal(s);
		} catch (@SuppressWarnings("unused") NumberFormatException e) {
			return false;
		}
		return true;
	}

	/*
	 * Date: 18 Jul 2021-----------------------------------------------------------
	 * Time created: 16:41:25--------------------------------------------
	 */
	/**
	 * Checks if the argument is a valid number {@code String} in the provided radix
	 * and returns {@code true} if it is or {@code false} if otherwise
	 * 
	 * @param s     the {@code String} to be checked
	 * @param radix the specified radix that the given number is represented in
	 * @return {@code true} if the argument is a value in the provided radix or
	 *         {@code false} if otherwise
	 */
	public static boolean isNumber(String s, int radix) {
		try {
			new BigInteger(s, radix);
		} catch (@SuppressWarnings("unused") NumberFormatException e) {
			try {
				new Apfloat(s, s.length(), radix);
			} catch (@SuppressWarnings("unused") NumberFormatException ex) {
//				System.err.println(ex);
				return false;
			}
		}
		return true;
	}

	/*
	 * Date: 18 Jul 2021-----------------------------------------------------------
	 * Time created: 16:48:59--------------------------------------------
	 */
	/**
	 * Check if the argument is an imaginary part of a complex number and returns
	 * {@code true} if it is (i.e if it contains the appendage 'i' at its front) or
	 * {@code false} if otherwise
	 * 
	 * @param s the {@code String} to be checked
	 * @return {@code true} if the argument is an imaginary {@code String} value or
	 *         {@code false} if otherwise
	 */
	public static boolean isImaginaryPart(String s) {
		try {
			return s.equals("i") ? true : new Complex("0", s.substring(0, s.length() - 1)).isImaginary();
		} catch (@SuppressWarnings("unused") NumberFormatException e) {
		}
		return false;
	}

	/*
	 * Date: 18 Jul 2021-----------------------------------------------------------
	 * Time created: 16:53:22--------------------------------------------
	 */
	/**
	 * Searches for the given path and writes the provided text to the given
	 * fileName using the provided {@code StandardOpenOption} as a guide.
	 * 
	 * @param pathToFile the path to the file
	 * @param fileName   the file name
	 * @param text       the text to be written to the file
	 * @param options    the type of writing the calling want to do
	 * @throws IOException if file is missing or cannot write to file
	 */
	public static void writeTextToFile(String pathToFile, String fileName, String text, StandardOpenOption... options)
			throws IOException {
		Files.write(FileSystems.getDefault().getPath(pathToFile, fileName), text.getBytes(), options);
	}

	/*
	 * Date: 21 Jul 2021-----------------------------------------------------------
	 * Time created: 13:08:57--------------------------------------------
	 */
	/**
	 * Inserts the given argument(s) {@code args} in to the specified indexes
	 * {@code indexes} within the given array {@code array} and return a new array
	 * that is of the same type as the given array provided that the given indexes's
	 * length is equal to the varargs length or an {@code RuntimeException} will be
	 * thrown.
	 * 
	 * @param <T>     the type of the array which is also the type of var arguments
	 * @param array   an array of type {@code &lt;T&gt;}
	 * @param indexes array of {@code int} that specifies the index of each vararg
	 *                within the returned array. The index of each element is
	 *                assumed to correspond with the position of each vararg. For
	 *                example:
	 * 
	 *                <pre>
	 * String[] items = ...
	 * int indexes = {0, 14, 25}
	 * String[] newItems = Utility.add(items, indexes, "shoes", "bag", "blouse");
	 *                </pre>
	 * 
	 *                From the above example, the resulting array will contain the
	 *                following:
	 *                <ol>
	 *                <li>"shoes" at <code>newItems[0]</code></li>
	 *                <li>"bag" at <code>newItems[14]</code></li>
	 *                <li>"blouse" at <code>newItems[25]</code></li>
	 *                </ol>
	 *                if any of the index is out of bounds (such as specifying a
	 *                negative index or an index &gt; the input array's length),
	 *                then a {@link IndexOutOfBoundsException} will be thrown
	 * 
	 * @param args    the items to be added the the given array of the same type T
	 * @return the first input array argument resized to fit the varargs
	 * @throws RuntimeException specifically {@link ArrayIndexOutOfBoundsException},
	 *                          if the {@code int} array's length {@code !=} the
	 *                          varargs' length
	 * @throws RuntimeException specifically {@link IndexOutOfBoundsException} if
	 *                          any element of the {@code int} array is
	 *                          <code> &lt; 0 ||</code> is<code>
	 *                          &gt; array.length</code>
	 */
	@SafeVarargs
	public static <T> T[] add(T[] array, int[] indexes, T... args) {
		if (indexes.length != args.length)
			new ArraySizeException(ARRAY_SIZE_OUTSIDE_LIMIT, Math.max(indexes.length, args.length));

		List<T> l = new ArrayList<>(Arrays.asList(array));
		int i = 0;
		for (T t : args) {
			int index = indexes[i];
			if (index < 0 || index > l.size())
				new IndexBeyondLimitException(index, l.size());
			l.add(index, t);
			i++;
		}
		return l.toArray(array);
	}

	/*
	 * Date: 13 Aug 2021-----------------------------------------------------------
	 * Time created: 16:09:38--------------------------------------------
	 */
	/**
	 * Throws a {@code NullPointerException} if any of the varargs is null and
	 * writes the exception to the mathaid error file using the name in the string
	 * array whose index corresponds with the position of the vararg.
	 * 
	 * @param argNames the parameter name of each of the vararg.
	 * @param args     the varargs to be tested.
	 * @throws RuntimeException if any of the varargs are {@code null}.
	 */
	public static final void throwException(String[] argNames, Object... args) {
		for (int i = 0; i < args.length; i++) {
			Object o = args[i];
			if (o == null)
				new NullException(ExceptionMessage.OBJECT_IS_NULL, new NullPointerException(), argNames[i]);
		}

	}

	/*
	 * Date: 1 May 2022-----------------------------------------------------------
	 * Time created: 22:03:55--------------------------------------------
	 */
	/**
	 * Traverses the inheritance hierarchy of the first argument to find the
	 * java.lang.Class reference for the interface at the second argument and places
	 * the found reference in the provided array, so that when this method returns
	 * successfully, the array will contain exactly one element that is non-null.
	 * When it fails to find a match, the array will be as it was passed initially.
	 * 
	 * @param klass           the class to search
	 * @param interfaceToFind the interface to search for
	 * @param object          a non-null array of at least 1 length. Does not need
	 *                        to contain anything.
	 */
	private static void findInterface(Class<?> klass, Class<?> interfaceToFind, Object[] object) {
		for (Class<?> _interface : klass.getInterfaces()) {
			if (_interface.getName().equals(interfaceToFind.getName())) {
				object[0] = _interface;
				break;
			} else if (_interface.getInterfaces().length > 0)
				findInterface(_interface, interfaceToFind, object);
		}
		// Search the parent classes
		if (klass.getSuperclass() != null && (!klass.getSuperclass().getName().equals(Object.class.getName()))
				&& (!klass.getSuperclass().getName().equals(Class.class.getName())))
			findInterface(klass.getSuperclass(), interfaceToFind, object);
	}

	/*
	 * Date: 1 May 2022-----------------------------------------------------------
	 * Time created: 22:13:00--------------------------------------------
	 */
	/**
	 * Traverses the inheritance hierarchy of the first argument to find the
	 * java.lang.Class reference for the superclass at the second argument and
	 * places the found reference in the provided array, so that when this method
	 * returns successfully, the array will contain exactly one element that is
	 * non-null. When it fails to find a match, the array will be as it was passed
	 * initially.
	 * 
	 * @param klass            the class to search
	 * @param superclassToFind the class to search for, which is expected to be the
	 *                         a superclass of the first argument.
	 * @param object           a non-null array of at least 1 length. Does not need
	 *                         to contain anything.
	 */
	private static void findSuperclass(Class<?> klass, Class<?> superclassToFind, Object[] object) {
		Class<?> _super = klass.getSuperclass();
		if (klass.getSuperclass() != null && (!klass.getSuperclass().getName().equals(Object.class.getName()))
				&& (!klass.getSuperclass().getName().equals(Class.class.getName())))
			if (_super.getName().equals(superclassToFind.getName()))
				object[0] = _super;
			else
				findSuperclass(_super, superclassToFind, object);// Search the parent classes
	}

	/*
	 * Date: 1 May 2022-----------------------------------------------------------
	 * Time created: 22:17:11--------------------------------------------
	 */
	/**
	 * Checks if the object argument is the same type of the class argument passed
	 * and returns the first argument (casted to the second) if is, or throw a
	 * {@code ClassCastException} if it is not.
	 * 
	 * @param <T>    the type of the object to be checked
	 * @param <U>    the type of class expected when {@link Object#getClass()} is
	 *               called on the first argument
	 * @param object a non-null instance of T
	 * @param cast   the class to which the first argument is cast.
	 * @return the first argument casted to the instance of the class reference
	 *         passed if T and U are the same type or else throws an exception.
	 * @throws ClassCastException if U cannot be cast to T.
	 */
	private static final <T, U> U checkType(T object, Class<U> cast) throws ClassCastException {
		Class<?> typeT = object.getClass();

		if (cast.isInterface()) {
			Object[] o = { null };
			findInterface(typeT, cast, o);
			if (o[0] != null)
				return cast.cast(object);
		} else if (cast.isEnum()) {
			if (!typeT.isEnum())
				throw new ClassCastException("Attempted to cast a non-enum type to an enum type");
		} else if (cast.isArray()) {
			if (!typeT.isArray())
				throw new ClassCastException("Attempted to cast a non-array type to an array type");
		} else if (cast.isAnnotation()) {
			if (!typeT.isAnnotation())
				throw new ClassCastException("Attempted to cast a non-annotation type to an annotation type");
		} else {
			if (cast.isInterface() || cast.isEnum() || cast.isArray() || cast.isAnnotation())
				throw new ClassCastException("Attempted to cast a non-class type to a class type");
			Object[] o = { null };
			findSuperclass(typeT, cast, o);
			if (o[0] != null)
				return cast.cast(object);
		}

		// generics

		if (cast.getName().equals(typeT.getName()))
			return cast.cast(object);
		throw new ClassCastException("Attempted to cast " + cast.getName() + " to " + typeT.getName());
	}

	/*
	 * Date: 1 May 2022-----------------------------------------------------------
	 * Time created: 22:23:25--------------------------------------------
	 */
	/**
	 * Checks if the first argument is the same as the second (or can be cast to the
	 * second) using the third argument for type-checking.
	 * 
	 * @param <T>   the type of the first argument
	 * @param <U>   the type of the second argument
	 * @param <V>   the type of the class argument
	 * @param type1 the first object whose type is to be checked against the second.
	 * @param type2 the second object whose type is to be checked against the first.
	 * @param cast  the type-checker
	 * @return true if the first can be cast to the second argument otherwise
	 *         returns false.
	 */
	public static <T, U, V> boolean checkType(T type1, U type2, Class<V> cast) {
		try {
			checkType(type1, cast);
			checkType(type2, cast);
			return true;
		} catch (@SuppressWarnings("unused") ClassCastException e) {
			return false;
		}
	}

	/*
	 * Date: 1 Jun 2022-----------------------------------------------------------
	 * Time created: 16:38:25--------------------------------------------
	 */
	/**
	 * Returns the given char stringed together the given number of times.
	 * 
	 * @param c the char to be stringed.
	 * @param n the number of times c will be repeated.
	 * @return c repeated <i>n</i> times as a {@code String}. Returns an empty
	 *         string if <i>n</i> is 0.
	 * @throws NegativeArraySizeException if n is negative.
	 */
	public static String string(char c, int n) throws NegativeArraySizeException {
		StringBuilder sb = new StringBuilder(n);

		while (sb.length() < n)
			sb.append(c);

		return sb.toString();
	}

	/*
	 * Date: 2 Jun 2022-----------------------------------------------------------
	 * Time created: 12:18:49--------------------------------------------
	 */
	/**
	 * Creates a {@link BigFraction#isRecurring() recurring}
	 * {@link BigFraction#BigFraction(BigInteger, BigInteger) rational}
	 * {@code BigFraction} from a specified non-recurring decimal and a recurring
	 * sequence of digits.
	 * 
	 * @param nonRecur a {@code BigDecimal} representing the non-repeating decimal.
	 *                 For example the fraction 2.34(5678) will input 2.34 as this
	 *                 argument value.
	 * 
	 * @param recur    a sequence of decimal characters corresponding to indexes
	 *                 (indices) of the recurring region of the mantissa. For
	 *                 example the fraction 2.34(5678) will input 5678 here.
	 * 
	 * @return a rational and recurring {@code BigFraction}.
	 * 
	 * @throws NumberFormatException if the second index has non-decimal characters
	 *                               such as +, -, *, /, . etc
	 */
	public static BigFraction fromDecimal(BigDecimal nonRecur, String recur) throws NumberFormatException {
		MathContext mc = new MathContext(nonRecur.stripTrailingZeros().toPlainString().length(),
				RoundingMode.HALF_EVEN);
		if (recur.length() == 0)
			return new BigFraction(nonRecur, mc, null, new BigDecimal("1E-10"));

		BigFraction r = BigFraction.fromRecurringDecimal(recur.toCharArray(), 0, recur.length());

		if (isInteger(nonRecur))
			return r.add(defaultFractionalConstructor(nonRecur, mc, r.getAccuracy()).abs())
					.multiply(nonRecur.signum() < 1 ? -1 : 1);

		return r.divide(defaultFractionalConstructor((BigDecimal.TEN.pow(numOfFractionalDigits(nonRecur))), mc,
				r.getAccuracy())).add(defaultFractionalConstructor(nonRecur, mc, r.getAccuracy()));
	}

	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 07:31:42--------------------------------------------
	 */
	/**
	 * Handy method for returning a {@code BigFraction} using:
	 * 
	 * <pre>
	 * <code>new BigFraction(n, mc, null, ac == null ? new BigDecimal("1E-10") : ac)</code>
	 * </pre>
	 * 
	 * @param n  a {@code BigDecimal} which is the value
	 * @param mc a {@code MathContext}
	 * @param ac a {@code BigDecimal} representing the accuracy returned by
	 *           {@link BigFraction#getAccuracy()}
	 * @return a {@code BigFraction}
	 */
	private static BigFraction defaultFractionalConstructor(BigDecimal n, MathContext mc, BigDecimal ac) {
		return new BigFraction(n, mc, null, ac == null ? new BigDecimal("1E-10") : ac);
	}

	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 07:34:34--------------------------------------------
	 */
	/**
	 * Returns the number of integer digits in the argument.
	 * 
	 * @param n a {@code BigDecimal}
	 * @return the number of integer digits in the argument.
	 */
	public static int numOfIntegerDigits(BigDecimal n) {
		n = n.abs();
		if (n.compareTo(BigDecimal.ONE) < 0)
			return 1;
		return n.precision() - n.scale();
	}

	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 07:37:21--------------------------------------------
	 */
	/**
	 * Returns the number of digits in the mantissa part after stripping the
	 * argument of all it's zeroes and getting it's {@link BigDecimal#scale()
	 * scale()}
	 * 
	 * @param n a {@code BigDecimal}
	 * @return the number of digits in the mantissa part
	 */
	public static int numOfFractionalDigits(BigDecimal n) {
		n = n.abs().stripTrailingZeros();
		if (isInteger(n))
			return 0;
//		String plain = n.toPlainString();
//		return plain.substring(plain.indexOf('.') + 1).length();
		return n.scale();
	}

	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 07:39:01--------------------------------------------
	 */
	/**
	 * Returns the number of leading zeros in the mantissa part of the given number.
	 * 
	 * @param n a {@code BigDecimal}
	 * @return the number of leading zeros in the mantissa part of the given number.
	 */
	public static int numOfLeadingFractionalZeros(BigDecimal n) {
		if (isInteger(n))
			return 0;
		return numOfFractionalDigits(n) - n.stripTrailingZeros().precision();
	}

	/*
	 * Date: 12 Jul 2022-----------------------------------------------------------
	 * Time created: 16:54:05--------------------------------------------
	 */
	/**
	 * Returns a {@code BigDecimal} at random. The value returned is in the range
	 * (-1, 1).
	 * 
	 * @param r              a pre-configured random object.
	 * @param mantissaDigits the max number of digits in the mantissa of the
	 *                       returned {@code BigDecimal}
	 * @return a {@code BigDecimal} in the open interval (-1, 1) whose digits after
	 *         the decimal point is less than or equal to the int argument
	 */
	public static BigDecimal nextBigDecimal(Random r, int mantissaDigits) {
		int length = r.nextInt(Math.abs(mantissaDigits)) + 1;
		StringBuilder sb = new StringBuilder(Math.abs(length) + 2);// the zero and the decimal point
		sb.append(r.nextBoolean() ? '-' : '+');
		sb.append("0.");
		int i = 0;
		do {
			sb.append(r.nextInt(10));// 0-9
			i++;
		} while (i < length);
		return new BigDecimal(sb.toString());
	}

	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 07:43:04--------------------------------------------
	 */
	/**
	 * Converts the number to it's Roman numeral string equivalent. This method is
	 * undefined if the number is greater than 3999 or if it is zero
	 * 
	 * @param number a non-negative, non-zero {@code int}
	 * @return a {@code String} representing the Roman numeral of the argument
	 */
	private static String toRoman(int number) {

		final TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		map.put(1000, "M");
		map.put(900, "CM");
		map.put(500, "D");
		map.put(400, "CD");
		map.put(100, "C");
		map.put(90, "XC");
		map.put(50, "L");
		map.put(40, "XL");
		map.put(10, "X");
		map.put(9, "IX");
		map.put(5, "V");
		map.put(4, "IV");
		map.put(1, "I");
		int l = map.floorKey(number);
		if (number == l) {
			return map.get(number);
		}
		return map.get(l) + toRoman(number - l);
	}

	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 07:45:48--------------------------------------------
	 */
	/**
	 * Converts the number to it's Roman numeral string equivalent.
	 * 
	 * @param i a non-negative, non-zero {@code int}
	 * @return a {@code String} representing the Roman numeral of the argument
	 * @throws IllegalArgumentException if the number is greater than 3999 or if it
	 *                                  is zero
	 */
	public static String romanNumeral(int i) throws IllegalArgumentException {
		if (i == 0 || i > 3999)
			throw new IllegalArgumentException("minimum must be greater than 0 and max must be less than 3999");
		return toRoman(i);
	}

	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 07:47:49--------------------------------------------
	 */
	/**
	 * Converts the {@code BigDecimal} into a decimal {@code String} represented in
	 * standard form using the exponent factor to float the decimal point
	 * appropriately and normalised (correct) the exponent
	 * 
	 * @param n              a {@code BigDecimal}
	 * @param exponentFactor an {@code int} value specifying the factor to use. For
	 *                       example engineering strings may use 3 here and
	 *                       normalised scientific strings may use 1 here.
	 * @return a decimal string formatted to standard for using the given exponent
	 *         factor as a scale for floating the point and adjusting the exponent
	 *         accordingly.
	 */
	public static String formatAsStandardForm(BigDecimal n, int exponentFactor) {
		final String sign = n.signum() < 0 ? "-" : "";
		n = n.abs();
		final boolean isBetween0And1 = n.compareTo(BigDecimal.ONE) < 0 && !Utility.isInteger(n);
		final int scale;
		int exponent;

		if (!isBetween0And1) {
			scale = Utility.numOfIntegerDigits(n) - 1;
			final int quotient = scale / exponentFactor;
			exponent = quotient * exponentFactor;
		} else {
			scale = Utility.numOfLeadingFractionalZeros(n) + 1;
			final int rem = exponentFactor - (scale % exponentFactor);
			exponent = -(scale + (rem == exponentFactor ? 0 : rem));
		}

		n = n.movePointLeft(exponent);
		StringBuilder num = new StringBuilder(n.stripTrailingZeros().toPlainString());
		num.insert(0, sign).append(String.format("E%s", exponent));
		return num.toString();
	}

	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 07:49:17--------------------------------------------
	 */
	/**
	 * Converts the {@code BigDecimal} into a forced engineering decimal string.
	 * Although the {@code BigDecimal} class already has a method for this, it does
	 * not support engineering suffixes neither will it display a value in
	 * engineering form if the significand has fewer than 3 digits
	 * 
	 * @param n         a {@code BigDecimal}
	 * @param useSuffix a check for whether to display using an engineering suffix
	 *                  if in the range of engineering suffixes
	 * @return an engineering decimal {@code String} with a possible engineering
	 *         suffix
	 */
	public static String toEngineeringString(BigDecimal n, boolean useSuffix) {
		String form = formatAsStandardForm(n, 3);
		if (useSuffix) {
			Map<String, String> suffixes = new HashMap<>();
			suffixes.put("E24", "Y");
			suffixes.put("E21", "Z");
			suffixes.put("E18", "E");
			suffixes.put("E15", "P");
			suffixes.put("E12", "T");
			suffixes.put("E9", "G");
			suffixes.put("E6", "M");
			suffixes.put("E3", "K");
			suffixes.put("E-3", "m");
			suffixes.put("E-6", "µ");
			suffixes.put("E-9", "n");
			suffixes.put("E-12", "p");
			suffixes.put("E-15", "f");
			suffixes.put("E-18", "a");
			suffixes.put("E-21", "z");
			suffixes.put("E-24", "y");

			String exp = form.substring(form.indexOf('E'));
			String suffix = suffixes.getOrDefault(exp, exp);
			return form.replace(exp, suffix);
		}
		return form;
	}

	/*
	 * Date: 10 Nov 2022-----------------------------------------------------------
	 * Time created: 07:56:35--------------------------------------------
	 */
	/**
	 * Converts the {@code BigDecimal} into a forced scientific decimal string.
	 * Although the {@code BigDecimal} class already has a method for this, it does
	 * not display a value in scientific form if the significand has fewer than 3
	 * digits
	 * 
	 * @param n a {@code BigDecimal}
	 * @return a scientific decimal {@code String}.
	 */
	public static String toScientificString(BigDecimal n) {
		return formatAsStandardForm(n, 1);
	}

	/*
	 * Date: 9 Dec 2022-----------------------------------------------------------
	 * Time created: 15:11:43--------------------------------------------
	 */
	/**
	 * Returns an {@code Iterator} of randomly generated number which are
	 * progressively larger in magnitude each time {@link Iterator#next()} is
	 * called. This iterator is infinite.
	 * 
	 * @param seed the starting point of the {@code Iterator} to be returned
	 * @return an {@code Iterator} whose calls to {@link Iterator#next() next}
	 *         infinitely returns {@code Long} values.
	 */
	public static Iterator<Long> next(long seed) {
		class It implements Iterator<Long> {

			long x = seed;
			long t = x += 0x6D2B79F5L;

			@Override
			public boolean hasNext() {
				return true;
			}

			@Override
			public Long next() {
				t = t ^ t >>> 15L * t | 1L;
				t ^= t + (t ^ t >>> 7L * (t | 61L));
				return ((t ^ t >>> 14L) >>> 0L) / 4294967296L;
			}

		}
		return new It();
	}

	private static class LongSupplier implements java.util.function.LongSupplier {

		LongSupplier(long seed) {
			x = seed;
		}

		long x;
		long t = x += 0x6D2B79F5L;

		/*
		 * Most Recent Date: 10 Dec 2022-----------------------------------------------
		 * Most recent time created: 14:23:52--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return
		 */
		@Override
		public long getAsLong() {
			t = t ^ t >>> 15L * t | 1L;
			t ^= t + (t ^ t >>> 7L * (t | 61L));
			return ((t ^ t >>> 14L) >>> 0L) / 4294967296L;
		}

	}

	public static java.util.stream.LongStream streamInfiniteLong(long seed) {
		return java.util.stream.LongStream.generate(new LongSupplier(seed));
	}

	/*
	 * Date: 9 Dec 2022-----------------------------------------------------------
	 * Time created: 15:19:30--------------------------------------------
	 */
	/**
	 * Encrypts the last argument using a {@code Calendar} (for the time of
	 * encryption), and a non-null object which will be tagged to help with the
	 * encryption.
	 * 
	 * @param d             a {@code Calendar} instance. Ideally, this will be the
	 *                      current time.
	 * @param relatedObject a non-null object which has a differentiated hash code.
	 * @param secret        the value to be encrypted
	 * @return the last argument in an encrypted {@code String}.
	 */
	public static String encrypt(Calendar d, Object relatedObject, String secret) {
		if (secret.length() < 6 || secret.length() > 18)
			throw new IllegalArgumentException("Password out of range: 6 \u2265 password Length \u2264 18");
		StringBuilder secretBuilder = new StringBuilder();
		secretBuilder.append(d.get(Calendar.MONTH)).append(d.get(Calendar.DAY_OF_MONTH))
				.append(BigInteger.valueOf(relatedObject.hashCode()).toString(16)).append(d.get(Calendar.YEAR))
				.append(BigInteger.valueOf(d.get(Calendar.MILLISECOND))
						.or(BigInteger.valueOf(System.currentTimeMillis())).toString(16));
		int secretSplitValue = new Random().nextInt(7);
		secretSplitValue = secretSplitValue == 0 || secretSplitValue == 1 ? secretSplitValue + 2 : secretSplitValue;
		List<String> splitSecret = Split(secret, secretSplitValue);
		int secretBuilderSplitValue = new Random().nextInt(4);
		secretBuilderSplitValue = secretBuilderSplitValue <= 1 ? secretBuilderSplitValue + 2 : secretBuilderSplitValue;
		String sbv = Base64.getEncoder().encodeToString(secretBuilder.toString().getBytes());
		List<String> mem = Split(sbv, secretBuilderSplitValue);
		int lastIndexSplitSecretBuilder = mem.get(mem.size() - 1).length();
		Queue<String> splitSecretBuilder = new ArrayDeque<>(mem);
		int isOrderly = new Random().nextInt(2);
		int iteration = 0;
		int sbLength = splitSecretBuilder.size();
		boolean isOverflowing = false;
		List<String> sl = new ArrayList<>();
		while (iteration < splitSecret.size()) {
			if (splitSecretBuilder.size() <= 0)
				isOverflowing = true;
			if (isOrderly == 1) {
				if (!isOverflowing)
					sl.add(splitSecretBuilder.remove());
				sl.add(splitSecret.get(iteration++));
			} else if (isOrderly == 0/* && insertion > 0 */) {
				if (!isOverflowing)
					sl.add(0, splitSecretBuilder.remove());
				sl.add(0, splitSecret.get(iteration++));
			}
		}
		while (splitSecretBuilder.size() > 0)
			if (isOrderly == 1)
				sl.add(splitSecretBuilder.remove());
			else
				sl.add(0, splitSecretBuilder.remove());
		sl.add(Character.toString(PARAMS));
		/*
		 * Add all the parameters here
		 */
		sl.add(sbLength < 10 ? "0" + sbLength : String.valueOf(sbLength));
		sl.add(lastIndexSplitSecretBuilder + "");
		sl.add(Integer.toString(splitSecret.get(splitSecret.size() - 1).length()));
		sl.add(Integer.toString(secretSplitValue));
		sl.add(Integer.toString(secretBuilderSplitValue));
		sl.add(Integer.toString(iteration));
		sl.add(Integer.toString(isOrderly));
		sl.add(isOverflowing ? "1" : "0");
		secretBuilder = new StringBuilder();
		for (String s : sl) {
			secretBuilder.append(s);
		}
		secret = null;
		splitSecretBuilder = null;
		splitSecret = null;
		return secretBuilder.toString();
	}

	/*
	 * Date: 9 Dec 2022-----------------------------------------------------------
	 * Time created: 15:19:27--------------------------------------------
	 */
	/**
	 * Decrypts the given value using {@link #encrypt(Calendar, Object, String) this
	 * algorithm}
	 * 
	 * @param val the value to be decrypted
	 * @return the original string as the decrypted value
	 */
	public static String decrypt(String val) {
		final String parameters = val.substring(val.indexOf(PARAMS) + 1);
		val = val.substring(0, val.indexOf(PARAMS));
		final int sbLength = Integer.parseInt(parameters.substring(0, 2));
		final int lastIndexSplitSecretBuilder = Integer.parseInt("" + parameters.charAt(parameters.length() - 7));
		final int lastIndexSecretSplitValue = Integer
				.parseInt(Character.toString(parameters.charAt(parameters.length() - 6)));
		final int secretSplitValue = Integer.parseInt(Character.toString(parameters.charAt(parameters.length() - 5)));
		final int secretBuilderSplitValue = Integer
				.parseInt(Character.toString(parameters.charAt(parameters.length() - 4)));
		final int iteration = Integer.parseInt(Character.toString(parameters.charAt(parameters.length() - 3)));
		final boolean isOrderly = parameters.charAt(parameters.length() - 2) == '1';
		final boolean isOverflowing = parameters.charAt(parameters.length() - 1) == '1';

		StringBuilder secretBuilder = new StringBuilder();
		int x = 0;
		int y = 0;
		if (isOrderly) {
			for (int i = 0;; i++) {

				if ((!isOverflowing) && i >= iteration)
					break;
				else if (isOverflowing && i >= sbLength - 1) {
					x = ((sbLength - 1) * secretBuilderSplitValue) + (i * secretSplitValue)
							+ lastIndexSplitSecretBuilder;
					secretBuilder.append(val.substring(x, val.length()));
					break;
				}

				x = iteration == sbLength && i == iteration - 1 ? val.length() - lastIndexSecretSplitValue
						: ((i + 1) * secretBuilderSplitValue) + (i * secretSplitValue);
				if (i == iteration - 1) {
					if (!isOverflowing)
						if (iteration == sbLength)
							y = val.length();
						else
							y = x + lastIndexSecretSplitValue;
				} else
					y = x + secretSplitValue;
				try {
					secretBuilder.append(val.substring(x, y));
				} catch (StringIndexOutOfBoundsException e) {
					throw e;
				}
			}
		} else {
			for (int i = 0;; i++) {

				if (i >= iteration && !isOverflowing)
					break;
				else if (isOverflowing && i >= sbLength - 1) {
					y = val.length() - (((sbLength - 1) * secretBuilderSplitValue) + (i * secretSplitValue));
					y = y - lastIndexSplitSecretBuilder;
					String rem = val.substring(0, y);
					int ii = rem.length() / secretSplitValue, y1 = rem.length(), x1 = rem.length(), iii = 0;
					if (ii < 1) {
						// secretBuilder.append((new StringBuilder(rem).reverse()));//I thought I
						// cancelled all reverse calls to the stringBuilder?
						secretBuilder.append(rem);
						break;
					}
					while (true) {
						x1 -= secretSplitValue;
						secretBuilder.append(rem.substring(x1 < 0 ? 0 : x1, y1));
						y1 -= secretSplitValue;
						if (iii >= ii) {
							if ((double) rem.length() / (double) secretSplitValue > (double) ii
									* (double) secretSplitValue)
								secretBuilder.append(rem.substring(0, lastIndexSecretSplitValue));
							break;
						}
						iii++;
					}
					break;
				}

				y = iteration == sbLength && i == iteration - 1 ? lastIndexSecretSplitValue
						: val.length() - (((i + 1) * secretBuilderSplitValue) + (i * secretSplitValue));
				if (i == iteration - 1) {
					if (!isOverflowing)
						if (iteration == sbLength)
							x = 0;
						else
							x = y - lastIndexSecretSplitValue;
				} else
					x = y - secretSplitValue;
				try {
					secretBuilder.append(val.substring(x, y));// When both pass len and padding len are equal err occurs
				} catch (StringIndexOutOfBoundsException e) {
					throw e;
				}
			}
		}

		val = null;
		return secretBuilder.toString();
	}

	/*
	 * Date: 9 Dec 2022-----------------------------------------------------------
	 * Time created: 15:19:22--------------------------------------------
	 */
	/**
	 * Splits the given {@code String} argument into segments after the given number
	 * of characters have been read. Each segment is placed into a list in the order
	 * by which the characters were arranged.
	 * 
	 * @param val        the value to be split
	 * @param numOfChars the number of characters which may constitute a complete
	 *                   segment
	 * @return a {@code List} where each element is a segment
	 */
	private static List<String> Split(String val, int numOfChars) {
		StringBuilder sb = new StringBuilder();
		List<String> returnValue = new ArrayList<>();
		for (int i = 0, j = 0; i < val.length(); i++, j++) {
			if (j >= numOfChars) {
				returnValue.add(sb.toString());
				sb = new StringBuilder();
				j = 0;
			}
			char c = val.charAt(i);
			sb.append(c);
		}
		if (sb.length() > 0)
			returnValue.add(sb.toString());
		return returnValue;
	}

	/*
	 * Date: Jan 16, 2023
	 * ----------------------------------------------------------- Time created:
	 * 9:34:09 AM ---------------------------------------------------
	 */
	/**
	 * Gets a {@linkplain java.util.Comparator Comparator} assembled from a
	 * {@link Comparable}.
	 * 
	 * @param <T> the type of the {@code Comparator} to be returned. This type is
	 *            also a {@code Comparable}
	 * @param t   a value (can be {@code null}) to provide a unique signature to the
	 *            returned Comparator
	 * @return a {@code Comparator} assembled from a {@code Comparable}
	 */
	public static <T extends Comparable<T>> Comparator<T> comparator(T t) {
		return new Comparator<>() {
			public int compare(T x, T y) {
				return x.compareTo(y);
			}

			public int hashCode() {
				return Figurate.getTetrahedral(Objects.hashCode(t)).next().intValue();
			}

			public boolean equals(Object o) {
				return o instanceof Comparator && hashCode() == o.hashCode();
			}
		};
	}

	// short-hands
	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 3:49:40 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for {@link BigInteger#valueOf(long)} for the purpose of
	 * convenience.
	 * 
	 * @param i an {@code int} value
	 * @return the argument as a {@code BigInteger}.
	 */
	public static BigInteger i(int i) {
		return BigInteger.valueOf(i);
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 3:51:35 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for {@link BigInteger#valueOf(long)} for the purpose of
	 * convenience.
	 * 
	 * @param i a {@code long} value
	 * @return the argument as a {@code BigInteger}.
	 */
	public static BigInteger i(long i) {
		return BigInteger.valueOf(i);
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 3:51:53 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for {@code new BigInteger(String)} for the purpose of
	 * convenience.
	 * 
	 * @param i a numerical value as a {@code String} value with decimal digits only
	 * @return the argument as a {@code BigInteger}.
	 */
	public static BigInteger i(String i) {
		return new BigInteger(i);
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 3:53:41 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for {@code new BigInteger(String, int)} for the purpose of
	 * convenience
	 * 
	 * @param i a numerical value as a {@code String} value with digits in the same
	 *          radix as specified by the {@code int} argument.
	 * @param r the radix of the given {@code String} value
	 * @return the argument as a {@code BigInteger}.
	 */
	public static BigInteger i(String i, int r) {
		return new BigInteger(i, r);
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 3:57:57 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for {@link BigDecimal#valueOf(long)} for the purpose of
	 * convenience.
	 * 
	 * @param d an {@code int} value
	 * @return the argument as a {@code BigDecimal}.
	 */
	public static BigDecimal d(int d) {
		return BigDecimal.valueOf(d);
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 3:58:39 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for {@link BigDecimal#valueOf(long)} for the purpose of
	 * convenience.
	 * 
	 * @param d a {@code long} value
	 * @return the argument as a {@code BigDecimal}.
	 */
	public static BigDecimal d(long d) {
		return BigDecimal.valueOf(d);
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 4:00:13 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for {@link BigDecimal#valueOf(double)} for the purpose of
	 * convenience.
	 * 
	 * @param d a {@code double} value
	 * @return the argument as a {@code BigDecimal}.
	 */
	public static BigDecimal d(double d) {
		return BigDecimal.valueOf(d);
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 4:00:36 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for {@code new BigDecimal(String)} for the purpose of
	 * convenience.
	 * 
	 * @param d a {@code String} value
	 * @return the argument as a {@code BigDecimal}.
	 */
	public static BigDecimal d(String d) {
		return new BigDecimal(d);
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 4:01:05 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for {@code new BigDecimal(String, MathContext)} for the purpose
	 * of convenience.
	 * 
	 * @param d  a decimal {@code String} value
	 * @param mc a {@code MathContext} object
	 * @return the argument as a {@code BigDecimal}.
	 */
	public static BigDecimal d(String d, MathContext mc) {
		return new BigDecimal(d, mc);
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 4:02:42 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for {@code new BigDecimal(BigInteger)} for the purpose of
	 * convenience.
	 * 
	 * @param d a {@code BigInteger} value
	 * @return the argument as a {@code BigDecimal}.
	 */
	public static BigDecimal d(BigInteger d) {
		return new BigDecimal(d);
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 4:03:32 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for {@code new BigDecimal(BigInteger, MathContext)} for the
	 * purpose of convenience.
	 * 
	 * @param d  a {@code BigInteger} value
	 * @param mc a {@code MathContext} object
	 * @return the argument as a {@code BigDecimal}.
	 */
	public static BigDecimal d(BigInteger d, MathContext mc) {
		return new BigDecimal(d, mc);
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 4:22:46 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for {@link BigFraction#valueOf(Number)} for the purpose of
	 * convenience.
	 * 
	 * @param f an {@code int} value representing an integer number
	 * @return the argument as a {@code BigFraction}.
	 */
	public static BigFraction f(int f) {
		return BigFraction.valueOf(f);
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 4:22:46 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for
	 * {@code new BigFraction(BigInteger.valueOf(long), BigInteger.valueOf(long))}
	 * for the purpose of convenience.
	 * 
	 * @param n an {@code int} value as the numerator
	 * @param d an {@code int} value as the denominator
	 * @return the argument as a {@code BigFraction}.
	 */
	public static BigFraction f(int n, int d) {
		return new BigFraction(i(n), i(d));
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 4:22:46 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for {@link BigFraction#valueOf(Number)} for the purpose of
	 * convenience.
	 * 
	 * @param f a {@code long} value representing an integer number
	 * @return the argument as a {@code BigFraction}.
	 */
	public static BigFraction f(long f) {
		return BigFraction.valueOf(f);
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 4:22:46 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for
	 * {@code new BigFraction(BigInteger.valueOf(long), BigInteger.valueOf(long))}
	 * for the purpose of convenience.
	 * 
	 * @param n a {@code long} value as the numerator
	 * @param d a {@code long} value as the denominator
	 * @return the arguments as a {@code BigFraction}.
	 */
	public static BigFraction f(long n, long d) {
		return new BigFraction(i(n), i(d));
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 4:28:14 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for {@link BigFraction#valueOf(Number)} for the purpose of
	 * convenience.
	 * 
	 * @param f a {@code double} value.
	 * @return the argument as a {@code BigFraction}.
	 */
	public static BigFraction f(double f) {
		return BigFraction.valueOf(f);
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 4:31:20 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for {@new BigFraction(String)} for the purpose of convenience.
	 * 
	 * @param f a {@code String} value in any format specified by
	 *          {@link BigFraction#BigFraction(String)}
	 * @return the argument as a {@code BigFraction}.
	 */
	public static BigFraction f(String f) {
		return new BigFraction(f);
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 4:32:32 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for {@code new BigFraction(String, String)} for the purpose of
	 * convenience.
	 * 
	 * @param n a {@code String} value (in the same format as described
	 *          {@link BigInteger#BigInteger(String) here}) as the numerator
	 * @param d a {@code String} value (in the same format as described
	 *          {@link BigInteger#BigInteger(String) here}) as the denominator
	 * @return the argument as a {@code BigFraction}.
	 */
	public static BigFraction f(String n, String d) {
		return new BigFraction(n, d);
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 4:33:50 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for {@code new BigFraction(BigInteger, BigInteger.ONE)} for the
	 * purpose of convenience.
	 * 
	 * @param n a {@code BigInteger} value
	 * @return the argument as a {@code BigFraction}.
	 */
	public static BigFraction f(BigInteger n) {
		return new BigFraction(n, i(1));
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 4:35:54 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for {@code new BigFraction(BigInteger, BigInteger)} for the
	 * purpose of convenience.
	 * 
	 * @param n a {@code BigInteger} value as the numerator
	 * @param d a {@code BigInteger} value as the denominator
	 * @return the arguments as a {@code BigFraction}
	 */
	public static BigFraction f(BigInteger n, BigInteger d) {
		return new BigFraction(n, d);
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 4:38:08 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for
	 * {@code new BigFraction(BigDecimal, null, null, new BigDecimal("1E-10"))} for
	 * the purpose of convenience.
	 * 
	 * @param f a {@code BigDecimal} value
	 * @return the argument as a {@code BigFraction}.
	 */
	public static BigFraction f(BigDecimal f) {
		return new BigFraction(f, null, null, d("1E-10"));
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 4:05:42 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for {@code new MathContext(int, RoundingMode.HALF_EVEN)} for the
	 * purpose of convenience.
	 * 
	 * @param s an {@code int} value representing the scale
	 * @return the argument as a {@code MathContext}.
	 */
	public static MathContext mc(int s) {
		return new MathContext(s, RoundingMode.HALF_EVEN);
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 4:10:28 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for {@code new MathContext(int, RoundingMode)} for the purpose
	 * of convenience.
	 * 
	 * @param s  an {@code int} value representing the scale
	 * @param rm the rounding mode to use
	 * @return the argument as a {@code MathContext}.
	 */
	public static MathContext mc(int s, RoundingMode rm) {
		return new MathContext(s, rm);
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 4:11:21 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for {@link RoundingMode#valueOf(int)} for the purpose of
	 * convenience.
	 * 
	 * @param rm an {@code int} value representing the old usage of rounding in the
	 *           {@code BigDecimal} class
	 * @return the argument as a {@code RoundingMode}.
	 */
	public static RoundingMode rm(int rm) {
		return RoundingMode.valueOf(rm);
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 4:14:13 PM ---------------------------------------------------
	 */
	/**
	 * A short hand for {@link RoundingMode#valueOf(String)} for the purpose of
	 * convenience.
	 * 
	 * @param name a {@code String} value representing the exact {@code enum} field
	 *             name of the {@code RoundingMode} object to be returned
	 * @return the argument as a {@code RoundingMode}.
	 */
	public static RoundingMode rm(String name) {
		return RoundingMode.valueOf(name);
	}
}
