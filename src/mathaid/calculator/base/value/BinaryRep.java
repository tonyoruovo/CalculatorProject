/**
 * 
 */
package mathaid.calculator.base.value;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import mathaid.ExceptionMessage;
import mathaid.calculator.base.MathematicalException;
import mathaid.calculator.base.converter.Convertible;
import mathaid.calculator.base.util.Arith;
import mathaid.calculator.base.util.Utility;

/*
 * Date: 1 Feb 2020<br> Time created: 18:34:16<br> Package:
 * mathaid.calculator.base.value<br> Project: LatestPoject2<br> File:
 * IntValue.java<br> Class name: BinaryRep
 */
/**
 * <p>
 * Enum to specify the different common representations of integers in a
 * computer.
 * </p>
 * <p>
 * It also contains basic arithmetic operations such as add, multiply, sqrt, and
 * bitwise operations. All of the operations that fall under this categories
 * simulates the behaviour of the {@code BinaryRep} in question to return exact
 * result. The results returned never throw exception to show overflow (even
 * when they overflow their domain) but rather they behave like real computer
 * integers such as java's {@code int}. For example: <blockquote>
 * 
 * <pre>
 * BinaryRep br = BinaryRep.TWO_C;
 * int bits = 8;
 * BigInteger x = new BigInteger("100");
 * BigInteger y = new BigInteger("50");
 * &#x002f;&#x002a;
 *  * overflow! 150 is bigger than the maximum 8 bit 2'complement value but
 *  * instead of an exception a circular value is returned.
 * &#x002a;&#x002f;
 * BigInteger result = br.add(x, y, bits);
 * result = br.convert(result, BinaryRep.MATH, bits);// must convert since it is not in traditional decimal notation
 * System.out.println(result);// prints -106 (in decimal) instead of 150
 * </pre>
 * 
 * </blockquote>
 * </p>
 * <p>
 * All operations in this class that accepts an {@code int} as a bit length
 * parameter will only recognise the following as legitimate arguments: 0, 4, 8,
 * 16, 32, 64, 128, 256. Any other value will default calculations to 32 bits.
 * Although 0 is a legitimate value, any attempt to use it apart from the
 * {@code BinaryRep} {@link #MATH} will cause an
 * {@code IllegalArgumentException} to be thrown by most methods in this class.
 * </p>
 * <p>
 * <b>Note:</b> This is only for visual representation and not for internal
 * calculations.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 *
 */
public enum BinaryRep implements Convertible<BinaryRep, BigInteger, Integer> {
	/**
	 * Signed magnitude representation of binary numbers
	 */
	SMR {
		@Override
		public BigInteger getMaxValue(int numBits) {
			return new BigInteger("2").pow(numBits - 1).subtract(BigInteger.ONE);
		}

		@Override
		public BigInteger getMinValue(int numBits) {
			return new BigInteger("2").pow(numBits - 1).subtract(BigInteger.ONE).negate();
		}

		@Override
		public BigInteger convert(BigInteger x, BinaryRep br, Integer bits) {
			throwArithmeticException(x, this, bits);
			final BitLength b = BitLength.valueOf(bits);
			String signed;
			switch (br) {
			case SMR:
			default:
				return x.bitLength() <= b.length() ? x : FloatAid.clearMSB(x, x.bitLength() - b.length());
			case EXCESS_K:
				signed = Utility.smrToDecimal(x.toString(2), b);
				signed = Utility.toExcessBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case MATH:
				signed = Utility.smrToDecimal(x.toString(2), b);
				return new BigInteger(signed);
			case NEGABINARY:
				signed = Utility.smrToDecimal(x.toString(2), b);
				signed = Utility.toNegaBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case ONE_C:
				signed = Utility.smrToDecimal(x.toString(2), b);
				signed = Utility.toOnesBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case TWO_C:
				signed = Utility.smrToDecimal(x.toString(2), b);
				signed = Utility.toTwosBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case UNSIGNED:
				signed = Utility.smrToDecimal(x.toString(2), b);
				signed = Utility.toUnsignedBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			}
		}
	},
	/**
	 * One's complement representation of binary numbers
	 */
	ONE_C {
		@Override
		public BigInteger getMaxValue(int numBits) {
			return new BigInteger("2").pow(numBits - 1).subtract(BigInteger.ONE);
		}

		@Override
		public BigInteger getMinValue(int numBits) {
			return new BigInteger("2").pow(numBits - 1).subtract(BigInteger.ONE).negate();

		}

		@Override
		public BigInteger convert(BigInteger x, BinaryRep br, Integer bits) {
			throwArithmeticException(x, this, bits);
			final BitLength b = BitLength.valueOf(bits);
			String signed;
			switch (br) {
			case ONE_C:
			default:
				return x.bitLength() <= b.length() ? x : FloatAid.clearMSB(x, x.bitLength() - b.length());
			case EXCESS_K:
				signed = Utility.onesToDecimal(x.toString(2), b);
				signed = Utility.toExcessBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case MATH:
				signed = Utility.onesToDecimal(x.toString(2), b);
				return new BigInteger(signed);
			case NEGABINARY:
				signed = Utility.onesToDecimal(x.toString(2), b);
				signed = Utility.toNegaBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case SMR:
				signed = Utility.onesToDecimal(x.toString(2), b);
				signed = Utility.toSmrBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case TWO_C:
				signed = Utility.onesToDecimal(x.toString(2), b);
				signed = Utility.toTwosBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case UNSIGNED:
				signed = Utility.onesToDecimal(x.toString(2), b);
				signed = Utility.toUnsignedBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			}
		}
	},
	/**
	 * Two's complement representation of binary numbers
	 */
	TWO_C {
		@Override
		public BigInteger getMaxValue(int bits) {
			return new BigInteger("2").pow(bits - 1).subtract(BigInteger.ONE);
		}

		@Override
		public BigInteger getMinValue(int bits) {
			return new BigInteger("2").pow(bits - 1).negate();
		}

		@Override
		public BigInteger convert(BigInteger x, BinaryRep br, Integer bits) {
			throwArithmeticException(x, this, bits);
			final BitLength b = BitLength.valueOf(bits);
			String signed;
			switch (br) {
			case TWO_C:
			default:
				return x.bitLength() <= b.length() ? x : FloatAid.clearMSB(x, x.bitLength() - b.length());
			case EXCESS_K:
				signed = Utility.twosToDecimal(x.toString(2), b);
				signed = Utility.toExcessBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case MATH:
				signed = Utility.twosToDecimal(x.toString(2), b);
				return new BigInteger(signed);
			case NEGABINARY:
				signed = Utility.twosToDecimal(x.toString(2), b);
				signed = Utility.toNegaBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case ONE_C:
				signed = Utility.twosToDecimal(x.toString(2), b);
				signed = Utility.toOnesBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case SMR:
				signed = Utility.twosToDecimal(x.toString(2), b);
				signed = Utility.toSmrBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case UNSIGNED:
				signed = Utility.twosToDecimal(x.toString(2), b);
				signed = Utility.toUnsignedBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			}
		}
	},
	/**
	 * Excess-n representation of binary numbers
	 */
	EXCESS_K {
		@Override
		public BigInteger getMaxValue(int bits) {
			return new BigInteger("2").pow(bits - 1).subtract(BigInteger.ONE);
		}

		@Override
		public BigInteger getMinValue(int bits) {
			return new BigInteger("2").pow(bits - 1).negate();
		}

		@Override
		public BigInteger convert(BigInteger x, BinaryRep br, Integer bits) {
			throwArithmeticException(x, this, bits);
			final BitLength b = BitLength.valueOf(bits);
			String signed;
			switch (br) {
			case EXCESS_K:
			default:
				return x.bitLength() <= b.length() ? x : FloatAid.clearMSB(x, x.bitLength() - b.length());
			case TWO_C:
				signed = Utility.excessToDecimal(x.toString(2), b);
				signed = Utility.toTwosBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case MATH:
				signed = Utility.excessToDecimal(x.toString(2), b);
				return new BigInteger(signed);
			case NEGABINARY:
				signed = Utility.excessToDecimal(x.toString(2), b);
				signed = Utility.toNegaBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case ONE_C:
				signed = Utility.excessToDecimal(x.toString(2), b);
				signed = Utility.toOnesBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case SMR:
				signed = Utility.excessToDecimal(x.toString(2), b);
				signed = Utility.toSmrBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case UNSIGNED:
				signed = Utility.excessToDecimal(x.toString(2), b);
				signed = Utility.toUnsignedBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			}
		}
	},
	/**
	 * Negative base representation of binary numbers
	 */
	NEGABINARY {
		@Override
		public BigInteger getMaxValue(int bits) {
			return new BigInteger(negaBinaryPositiveLimit(BitLength.valueOf(bits)));
		}

		@Override
		public BigInteger getMinValue(int bits) {
			BigInteger decimal = getMaxValue(bits);
			return decimal.multiply(BigInteger.TWO).negate();
		}

		@Override
		public BigInteger convert(BigInteger x, BinaryRep br, Integer bits) {
			throwArithmeticException(x, this, bits);
			final BitLength b = BitLength.valueOf(bits);
			String signed;
			switch (br) {
			case NEGABINARY:
			default:
				return x.bitLength() <= b.length() ? x : FloatAid.clearMSB(x, x.bitLength() - b.length());
			case TWO_C:
				signed = Utility.negaToDecimal(x.toString(2), b);
				signed = Utility.toTwosBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case MATH:
				signed = Utility.negaToDecimal(x.toString(2), b);
				return new BigInteger(signed);
			case EXCESS_K:
				signed = Utility.negaToDecimal(x.toString(2), b);
				signed = Utility.toExcessBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case ONE_C:
				signed = Utility.negaToDecimal(x.toString(2), b);
				signed = Utility.toOnesBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case SMR:
				signed = Utility.negaToDecimal(x.toString(2), b);
				signed = Utility.toSmrBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case UNSIGNED:
				signed = Utility.negaToDecimal(x.toString(2), b);
				signed = Utility.toUnsignedBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			}
		}
	},
	/**
	 * Normal everyday mathematical binary representation. This representation is
	 * one of two {@code BinaryRep} that can use {@code BitLength.UNLTD} to represent
	 * binary numbers and can represent signed numbers with mathematical sign.
	 */
	MATH {
		@Override
		public BigInteger getMaxValue(int bits) {
			return new BigInteger("2").pow(bits - 1).subtract(BigInteger.ONE).multiply(BigInteger.TWO)
					.add(BigInteger.ONE);
		}

		@Override
		public BigInteger getMinValue(int bits) {
			return getMaxValue(bits).negate();
		}

		@Override
		public BigInteger convert(BigInteger x, BinaryRep br, Integer bits) {
			throwArithmeticException(x, this, bits);
			final BitLength b = BitLength.valueOf(bits);
			String signed;
			switch (br) {
			case MATH:
			default:
				return x;
			case TWO_C:
				signed = x.toString();
				signed = Utility.toTwosBinary(new BigInteger(signed), b);
				/*
				 * The next 2 lines are kind for iffy for me. I added them on 1st-June-2021 to
				 * prevent unnecessary overflow that happens after the previous line is called.
				 * 
				 * For example, when converting a number from two's complement to math with 8
				 * bits this happens :- BinaryRep bin = BinaryRep.TWO_C;---------------------
				 * String n = BinaryRep.MATH.convert(new BigInteger("5054"), bin, 8).toString();
				 * out.println(n);------------------------------------------------------------
				 * Without the next 2 lines, the above will print '1001110111110' instead of
				 * '10111110'
				 */
				if (signed.length() > bits)
					signed = signed.substring(signed.length() - bits, signed.length());
				return new BigInteger(signed, 2);
			case NEGABINARY:
				signed = x.toString();
				signed = Utility.toNegaBinary(new BigInteger(signed), b);
				/*
				 * The next 2 lines are kind for iffy for me. I added them on 1st-June-2021 to
				 * prevent unnecessary overflow that happens after the previous line is called.
				 * 
				 * For example, when converting a number from two's complement to math with 8
				 * bits this happens :- BinaryRep bin = BinaryRep.TWO_C;---------------------
				 * String n = BinaryRep.MATH.convert(new BigInteger("5054"), bin, 8).toString();
				 * out.println(n);------------------------------------------------------------
				 * Without the next 2 lines, the above will print '1001110111110' instead of
				 * '10111110'
				 */
				if (signed.length() > bits)
					signed = signed.substring(signed.length() - bits, signed.length());
				return new BigInteger(signed, 2);
			case EXCESS_K:
				signed = x.toString();
				signed = Utility.toExcessBinary(new BigInteger(signed), b);
				/*
				 * The next 2 lines are kind for iffy for me. I added them on 1st-June-2021 to
				 * prevent unnecessary overflow that happens after the previous line is called.
				 * 
				 * For example, when converting a number from two's complement to math with 8
				 * bits this happens :- BinaryRep bin = BinaryRep.TWO_C;---------------------
				 * String n = BinaryRep.MATH.convert(new BigInteger("5054"), bin, 8).toString();
				 * out.println(n);------------------------------------------------------------
				 * Without the next 2 lines, the above will print '1001110111110' instead of
				 * '10111110'
				 */
				if (signed.length() > bits)
					signed = signed.substring(signed.length() - bits, signed.length());
				return new BigInteger(signed, 2);
			case ONE_C:
				signed = x.toString();
				signed = Utility.toOnesBinary(new BigInteger(signed), b);
				/*
				 * The next 2 lines are kind for iffy for me. I added them on 1st-June-2021 to
				 * prevent unnecessary overflow that happens after the previous line is called.
				 * 
				 * For example, when converting a number from two's complement to math with 8
				 * bits this happens :- BinaryRep bin = BinaryRep.TWO_C;---------------------
				 * String n = BinaryRep.MATH.convert(new BigInteger("5054"), bin, 8).toString();
				 * out.println(n);------------------------------------------------------------
				 * Without the next 2 lines, the above will print '1001110111110' instead of
				 * '10111110'
				 */
				if (signed.length() > bits)
					signed = signed.substring(signed.length() - bits, signed.length());
				return new BigInteger(signed, 2);
			case SMR:
				signed = x.toString();
				signed = Utility.toSmrBinary(new BigInteger(signed), b);
				/*
				 * The next 2 lines are kind for iffy for me. I added them on 1st-June-2021 to
				 * prevent unnecessary overflow that happens after the previous line is called.
				 * 
				 * For example, when converting a number from two's complement to math with 8
				 * bits this happens :- BinaryRep bin = BinaryRep.TWO_C;---------------------
				 * String n = BinaryRep.MATH.convert(new BigInteger("5054"), bin, 8).toString();
				 * out.println(n);------------------------------------------------------------
				 * Without the next 2 lines, the above will print '1001110111110' instead of
				 * '10111110'
				 */
				if (signed.length() > bits)
					signed = signed.substring(signed.length() - bits, signed.length());
				return new BigInteger(signed, 2);
			case UNSIGNED:
				signed = x.toString();
				signed = Utility.toUnsignedBinary(new BigInteger(signed), b);
				/*
				 * The next 2 lines are kind for iffy for me. I added them on 1st-June-2021 to
				 * prevent unnecessary overflow that happens after the previous line is called.
				 * 
				 * For example, when converting a number from two's complement to math with 8
				 * bits this happens :- BinaryRep bin = BinaryRep.TWO_C;---------------------
				 * String n = BinaryRep.MATH.convert(new BigInteger("5054"), bin, 8).toString();
				 * out.println(n);------------------------------------------------------------
				 * Without the next 2 lines, the above will print '1001110111110' instead of
				 * '10111110'
				 */
				if (signed.length() > bits)
					signed = signed.substring(signed.length() - bits, signed.length());
				return new BigInteger(signed, 2);
			}
		}
	},
	/**
	 * Default binary implementation using unsigned representation. This
	 * representation is one of two {@code BinaryRep} that can use
	 * {@code BitLength.UNLTD} to represent binary numbers.
	 */
	UNSIGNED {
		@Override
		public BigInteger getMaxValue(int bits) {
			return new BigInteger("2").pow(bits - 1).subtract(BigInteger.ONE).multiply(BigInteger.TWO)
					.add(BigInteger.ONE);
		}

		@Override
		public BigInteger getMinValue(int bits) {
			return BigInteger.ZERO;
		}

		@Override
		public BigInteger convert(BigInteger x, BinaryRep br, Integer bits) {
			throwArithmeticException(x, this, bits);
			final BitLength b = BitLength.valueOf(bits);
			String signed;
			switch (br) {
			case UNSIGNED:
			default:
				return x.bitLength() <= b.length() ? x : FloatAid.clearMSB(x, x.bitLength() - b.length());
			case TWO_C:
				signed = Utility.unsignedToDecimal(x.toString(2), b);
				signed = Utility.toTwosBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case NEGABINARY:
				signed = Utility.unsignedToDecimal(x.toString(2), b);
				signed = Utility.toNegaBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case EXCESS_K:
				signed = Utility.unsignedToDecimal(x.toString(2), b);
				signed = Utility.toExcessBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case ONE_C:
				signed = Utility.unsignedToDecimal(x.toString(2), b);
				signed = Utility.toOnesBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case SMR:
				signed = Utility.unsignedToDecimal(x.toString(2), b);
				signed = Utility.toSmrBinary(new BigInteger(signed), b);
				return new BigInteger(signed, 2);
			case MATH:
				signed = Utility.unsignedToDecimal(x.toString(2), b);
				return new BigInteger(signed);
			}
		}
	};

	/*
	 * Date: 20 Feb 2020<br> Time created: 13:24:49<br> <br> This is a delegate
	 * method for the inner interfaces
	 */
	/**
	 * Returns the positive range limit of a negabinary within the given bits.
	 * 
	 * @param bits the number of bits to be used in the conversion as specified by
	 *             {@link mathaid.calculator.base.value.IntValue.BitLength}. If this
	 *             argument is null, the default {@code BitLength}, which is the
	 *             current {@code BitLength} is used
	 * 
	 * @return the positive limit of a negabinary in the given {@code BitLength}
	 */
	private static String negaBinaryPositiveLimit(BitLength bits) {
		final int numBits = bits.length();
		if (numBits < 1)
			new ValueFormatException(ExceptionMessage.ARBITRARY_BITS_NOT_ALLOWED);
		if (numBits <= 64) {
			return negaBinaryPositiveLimit((byte) numBits) + "";
		}
		return negaBinaryPositiveLimit((short) numBits).toString();
	}

	/*
	 * Date: 18 Feb 2020<br> Time created: 10:08:50<br> <br>
	 */
	/**
	 * Delegate method for
	 * {@link #negaBinaryPositiveLimit(mathaid.calculator.base.value.IntValue.BitLength)}.
	 * This method returns positive limits within java's long
	 * 
	 * @param bits the number of bits whose limits to return
	 * @return the positve limit of the bit length of a negabinary within java's
	 *         long
	 */
	private static long negaBinaryPositiveLimit(byte bits) {
		if (bits < 0)
			return 0L;
		int n = bits - 2;
		long result = (long) (Math.pow(-2, n) + negaBinaryPositiveLimit((byte) n));
		return result;
	}

	/*
	 * Date: 19 Feb 2020<br> Time created: 10:08:59<br> <br>
	 */
	/**
	 * Delegate method for
	 * {@link #negaBinaryPositiveLimit(mathaid.calculator.base.value.IntValue.BitLength)}.
	 * This method operates like {@link #negaBinaryPositiveLimit(byte)} but returns
	 * values as a {@code BigInteger}. Suitable for values greater thhan java's long
	 * 
	 * @param bits the number of bits whose limits to return
	 * @return the positve limit of the bit length of a negabinary as a
	 *         {@code BigInteger}
	 */
	private static BigInteger negaBinaryPositiveLimit(short bits) {
		if (bits < 2)
			return BigInteger.ZERO;
		int n = (bits - 2);
		BigInteger result = new BigInteger("-2").pow(Math.abs(n)).add(negaBinaryPositiveLimit((short) n));
		return result;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 10:18:05--------------------------------------------
	 */
	/**
	 * Only {@link #MATH} is allowed to have mathematically signed
	 * {@code BigInteger}s and also to use 0 (which really means arbitrary length)
	 * as arguments for most of this class's methods. This methods checks to see if
	 * these laws have been violated and then throws an {@code ArithmeticException}
	 * if they have.
	 * 
	 * @param x    the value to be checked.
	 * @param rep  the {@code BinaryRep} to be checked.
	 * @param bits the bit length to be checked.
	 * @throws ArithmeticException if the given {@code BinaryRep} != {@code MATH}
	 *                             and any of the 2 predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	private static void throwArithmeticException(BigInteger x, BinaryRep rep, int bits) throws ArithmeticException {
		if (x.signum() < 0 && rep != MATH)
			new MathematicalException(ExceptionMessage.SIGNED_NUMBER_NOT_ACCEPTED);
		if (bits == 0 && rep != MATH)
			new MathematicalException(ExceptionMessage.WRONG_BITLENGTH_FOR_BINARY_REP,
					BitLength.valueOf(bits).toString(), rep.toString());
	}

	/*
	 * Date: 29 Aug 2020-----------------------------------------------------------
	 * Time created: 21:59:40--------------------------------------------
	 */
	/**
	 * Returns the maximum {@code BigInteger} value representable by {@code this}.
	 * 
	 * @param bits the bit length of the {@code BinaryRep}. The legitimate values
	 *             include 4, 8, 16, 32, 64, 128, 256. In this context, 0 is not a
	 *             legitimate value because 0 stands for arbitrary length integers
	 *             that do not have known maximum limits.
	 * @return the max value representable in this {@code BinaryRep}.
	 */
	public abstract BigInteger getMaxValue(int bits);

	/*
	 * Date: 29 Aug 2020-----------------------------------------------------------
	 * Time created: 22:04:10--------------------------------------------
	 */
	/**
	 * Returns the minimum {@code BigInteger} value representable by {@code this}.
	 * 
	 * @param bits the bit length of the {@code BinaryRep}. The legitimate values
	 *             include 4, 8, 16, 32, 64, 128, 256.. In this context, 0 is not a
	 *             legitimate value because 0 stands for arbitrary length integers
	 *             that do not have known minimum limits.
	 * @return the min value representable in this {@code BinaryRep}.
	 */
	public abstract BigInteger getMinValue(int bits);

	/*
	 * Most Recent Date: 29 Aug 2020-----------------------------------------------
	 * Most recent time created: 22:04:51--------------------------------------
	 */
	/**
	 * Returns the first argument, converted to a notation that is the same as the
	 * second argument and using the bit constraints of the third argument.
	 * 
	 * @param value a {@code BigInteger} to converted.
	 * @param type  the {@code BinaryRep} notation to which this conversion is made.
	 * @param bits  the bit length that will be used to constrain the results.
	 * @return the value converted to the given {@code BinaryRep} notation.
	 * @throws ArithmeticException if the given {@code BinaryRep} argument !=
	 *                             {@code MATH} and any of the 2 predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	@Override
	public abstract BigInteger convert(final BigInteger value, final BinaryRep type, final Integer bits)
			throws ArithmeticException;

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 09:52:35--------------------------------------------
	 */
	/**
	 * Checks whether the {@code BigInteger} value overflows the upper or lower
	 * bounds of its domain, then corrects this by add the remainder of the max to
	 * the min for positive values and vice-versa for negative values. If the
	 * argument does not overflow any of the bounds, it is then returned unchanged.
	 * 
	 * @param z    a {@code BigInteger} value to be checked for overflow.
	 * @param bits the bit length of the value. This provide contextual information
	 *             on the domain of this {@code BinaryRep}.
	 * @return a {@code BigInteger} value that has been truncated if it is outside
	 *         of the domain specified by the given bit length, or returned as it is
	 *         if it is within the domain.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	private BigInteger checkValue(BigInteger z, final int bits) {
		throwArithmeticException(z, this, bits);
		final BigInteger max = getMaxValue(bits);
		final BigInteger min = getMinValue(bits);
		if (z.signum() > 0) {
			if (z.compareTo(max) > 0) {
				z = z.remainder(max).subtract(BigInteger.ONE);
				return min.add(z);
			}
		} else if (z.signum() < 0) {
			if (z.compareTo(min) < 0) {
				z = z.abs().remainder(min.abs()).subtract(BigInteger.ONE);
				return max.add(z);
			}
		}
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 10:02:01--------------------------------------------
	 */
	/**
	 * Returns {@code x + y} with any overflow truncated to fit the provide bit
	 * length context.
	 * 
	 * @param x    the augend (left operand).
	 * @param y    the addend (right operand).
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return {@code x + y}.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger add(final BigInteger x, final BigInteger y, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger cy = convert(y, MATH, bits);
		BigInteger z = cx.add(cy);
		/*
		 * TODO: There is a danger using checkValue() inside of covert. It should be the
		 * other way round. I suspect that an overflow may happen the commented line
		 * below is more safe, however it throw an exception if the value is negative
		 * and this != MATH
		 */
//		return bits != 0 ? checkValue(MATH.convert(z, this, bits), bits) : z;
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 11:40:58--------------------------------------------
	 */
	/**
	 * Returns {@code x - y} with any overflow truncated to fit the provide bit
	 * length context.
	 * 
	 * @param x    the minuend (left operand).
	 * @param y    the subtrahend (right operand).
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return {@code x - y}.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger subtract(final BigInteger x, final BigInteger y, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger cy = convert(y, MATH, bits);
		BigInteger z = cx.subtract(cy);
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
//		System.out.println(z);
//		return checkValue(z, bits);
	}

	/**
	 * Returns <code>x &times; y</code> with any overflow truncated to fit the
	 * provide bit length context.
	 * 
	 * @param x    the addend (left operand).
	 * @param y    the augend (right operand).
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>x &times; y</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger multiply(final BigInteger x, final BigInteger y, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger cy = convert(y, MATH, bits);
		BigInteger z = cx.multiply(cy);
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 11:51:17--------------------------------------------
	 */
	/**
	 * Returns <code>x &#x00f7; y</code> with any overflow truncated to fit the
	 * provide bit length context.
	 * 
	 * @param x    the dividend (left operand).
	 * @param y    the divisor (right operand).
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>x &#x00f7; y</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger divide(final BigInteger x, final BigInteger y, final int bits) throws ArithmeticException {
		if(y.signum() == 0)
			new MathematicalException(ExceptionMessage.DIVISION_BY_ZERO);
		BigInteger cx = convert(x, MATH, bits);
		BigInteger cy = convert(y, MATH, bits);
		BigInteger z = cx.divide(cy);
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 11:51:17--------------------------------------------
	 */
	/**
	 * Returns <code>x | y</code> with any overflow truncated to fit the provide bit
	 * length context.
	 * 
	 * @param x    the left operand.
	 * @param y    the right operand.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>x | y</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger or(final BigInteger x, final BigInteger y, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger cy = convert(y, MATH, bits);
		BigInteger z = cx.or(cy);
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 11:51:17--------------------------------------------
	 */
	/**
	 * Returns <code>x & y</code> with any overflow truncated to fit the provide bit
	 * length context.
	 * 
	 * @param x    the left operand.
	 * @param y    the right operand.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>x & y</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger and(final BigInteger x, final BigInteger y, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger cy = convert(y, MATH, bits);
		BigInteger z = cx.and(cy);
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 11:51:17--------------------------------------------
	 */
	/**
	 * Returns <code>~x</code> with any overflow truncated to fit the provide bit
	 * length context.
	 * 
	 * @param x    the single operand.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>~x</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger not(final BigInteger x, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger y = cx.not();
		y = bits != 0 ? MATH.convert(MATH.checkValue(y, bits), this, bits) : y;
		if (this != MATH)
			y = checkValue(y, bits);
		return y;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 11:51:17--------------------------------------------
	 */
	/**
	 * Returns <code>-x</code> with any overflow truncated to fit the provide bit
	 * length context.
	 * 
	 * @param x    the single operand.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>-x</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger neg(final BigInteger x, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger y = cx.negate();
		y = bits != 0 ? MATH.convert(MATH.checkValue(y, bits), this, bits) : y;
		if (this != MATH)
			y = checkValue(y, bits);
		return y;
	}

	/*
	 * 
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 11:57:55--------------------------------------------
	 */
	/**
	 * Returns <code>x << 1</code> with any overflow truncated to fit the provide
	 * bit length context.
	 * 
	 * @param x    the single operand.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>x << 1</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger shiftLeft1(final BigInteger x, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger y = cx.shiftLeft(1);
		y = bits != 0 ? MATH.convert(MATH.checkValue(y, bits), this, bits) : y;
		if (this != MATH)
			y = checkValue(y, bits);
		return y;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 11:58:54--------------------------------------------
	 */
	/**
	 * Returns <code>x</code> rotated 1 bit left with any overflow truncated to fit
	 * the provide bit length context.
	 * 
	 * @param x    the single operand.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return the 1 bit logical left rotation of <code>x</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger rotateLeft1(final BigInteger x, final int bits) throws ArithmeticException {
		return rotateLeft(x, 1, bits);
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:04:22--------------------------------------------
	 */
	/**
	 * Returns <code>x >> 1</code> with any overflow truncated to fit the provide
	 * bit length context.
	 * 
	 * @param x    the single operand.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>x >> 1</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger shiftRight1(final BigInteger x, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger y = cx.shiftRight(1);
//		return bits != 0 ? MATH.convert(MATH.checkValue(y, bits), this, bits) : y;
		y = bits != 0 ? MATH.convert(MATH.checkValue(y, bits), this, bits) : y;
		if (this != MATH)
			y = checkValue(y, bits);
		return y;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 11:58:54--------------------------------------------
	 */
	/**
	 * Returns <code>x</code> rotated 1 bit right with any overflow truncated to fit
	 * the provide bit length context.
	 * 
	 * @param x    the single operand.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return the 1 bit logical right rotation of <code>x</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger rotateRight1(final BigInteger x, final int bits) throws ArithmeticException {
		return rotateRight(x, 1, bits);
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:09:15--------------------------------------------
	 */
	/**
	 * Returns <code>x ^ y</code> with any overflow truncated to fit the provide bit
	 * length context.
	 * 
	 * @param x    the left operand.
	 * @param y    the right operand.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>x ^ y</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger xor(final BigInteger x, final BigInteger y, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger cy = convert(y, MATH, bits);
		BigInteger z = cx.xor(cy);
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:09:15--------------------------------------------
	 */
	/**
	 * Returns <code>x NAND y</code> with any overflow truncated to fit the provide
	 * bit length context.
	 * 
	 * @param x    the left operand.
	 * @param y    the right operand.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>x NAND y</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger nand(final BigInteger x, final BigInteger y, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger cy = convert(y, MATH, bits);
		BigInteger z = cx.and(cy).not();
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:09:15--------------------------------------------
	 */
	/**
	 * Returns <code>x XNOR y</code> with any overflow truncated to fit the provide
	 * bit length context.
	 * 
	 * @param x    the left operand.
	 * @param y    the right operand.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>x XNOR y</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger xnor(final BigInteger x, final BigInteger y, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger cy = convert(y, MATH, bits);
		BigInteger left = cx.and(cy);
		BigInteger right = cx.not().and(cy.not());
		BigInteger z = left.or(right);
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:09:15--------------------------------------------
	 */
	/**
	 * Returns <code>x NOR y</code> with any overflow truncated to fit the provide
	 * bit length context.
	 * 
	 * @param x    the left operand.
	 * @param y    the right operand.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>x NOR y</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger nor(final BigInteger x, final BigInteger y, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger cy = convert(y, MATH, bits);
		BigInteger z = cx.or(cy);
		return bits != 0 ? MATH.convert(MATH.checkValue(z.not(), bits), this, bits) : z.not();
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:11:21--------------------------------------------
	 */
	/**
	 * Returns <code>x << n</code> with any overflow truncated to fit the provide
	 * bit length context.
	 * 
	 * @param x    the single operand.
	 * @param n    the distance in bits the valued is to be shifted
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>x << n</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger shiftLeft(final BigInteger x, final int n, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger z = cx.shiftLeft(n);
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:11:21--------------------------------------------
	 */
	/**
	 * Returns <code>x >> n</code> with any overflow truncated to fit the provide
	 * bit length context.
	 * 
	 * @param x    the single operand.
	 * @param n    the distance in bits the valued is to be shifted
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>x >> n</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger shiftRight(final BigInteger x, final int n, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger z = cx.shiftRight(n);
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 11:58:54--------------------------------------------
	 */
	/**
	 * Returns <code>x</code> with it's bits logically rotated left to the specified
	 * distance and any overflow truncated to fit the provide bit length context.
	 * 
	 * @param x    the single operand.
	 * @param n    the distance in bits the valued is to be rotated
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return the logical left rotation of <code>x</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger rotateLeft(final BigInteger x, final int n, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger z = cx.shiftLeft(n);
		BigInteger y;
		y = (bits == 0 ? cx.shiftRight(z.bitLength() - n) : cx.shiftRight(bits - n));
		z = z.or(y);
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 11:58:54--------------------------------------------
	 */
	/**
	 * Returns <code>x</code> with it's bits logically rotated right to the
	 * specified distance and any overflow truncated to fit the provide bit length
	 * context.
	 * 
	 * @param x    the single operand.
	 * @param n    the distance in bits the valued is to be rotated.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return the logical right rotation of <code>x</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger rotateRight(final BigInteger x, final int n, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger z = cx.shiftRight(n);
		BigInteger y;
		y = (bits == 0 ? cx.shiftLeft(z.bitLength() - n) : cx.shiftLeft(bits - n));
		z = z.or(y);
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:15:28--------------------------------------------
	 */
	/**
	 * Returns <code>x<sup>y</sup></code> with any overflow truncated to fit the
	 * provide bit length context.
	 * 
	 * @param x    the base (left operand).
	 * @param y    the exponent (right operand).
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>x<sup>y</sup></code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger pow(final BigInteger x, final BigInteger y, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger cy = convert(y, MATH, bits);
		BigInteger z;
		try {
			z = cx.pow(cy.intValueExact());
		} catch (ArithmeticException e) {
			e.printStackTrace();
			z = Arith.pow(new BigDecimal(cx), new BigDecimal(cy), Precision.OCTUPLE.getMathContext()).toBigInteger();
		}
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:15:28--------------------------------------------
	 */
	/**
	 * Returns <code><sup>x</sup>&#x221a;y</code> with any overflow truncated to fit
	 * the provide bit length context.
	 * 
	 * @param x    the degree (left operand).
	 * @param y    the radicand (right operand).
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code><sup>x</sup>&#x221a;y</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger root(final BigInteger x, final BigInteger y, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger cy = convert(y, MATH, bits);
		BigInteger z = Arith.pow(new BigDecimal(cy), BigDecimal.ONE.divide(new BigDecimal(cx), MathContext.DECIMAL64),
				MathContext.DECIMAL128).toBigInteger();
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:28:07--------------------------------------------
	 */
	/**
	 * Returns the base 10 logarithms of {@code x} with any overflow truncated to
	 * fit the provide bit length context.
	 * 
	 * @param x    the single operand.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>log<sub>10</sub>(x)</code> i.e
	 *         <code>ln(x) &#x00f7; ln(10)</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger log10(final BigInteger x, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger z = Arith.log10(new BigDecimal(cx), MathContext.DECIMAL128).toBigInteger();
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:20:23--------------------------------------------
	 */
	/**
	 * Returns the binary logarithms of {@code x} with any overflow truncated to fit
	 * the provide bit length context.
	 * 
	 * @param x    the single operand.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>log<sub>2</sub>(x)</code> i.e
	 *         <code>ln(x) &#x00f7; ln(2)</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger log2(final BigInteger x, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger z = Arith.log2(new BigDecimal(cx), MathContext.DECIMAL128).toBigInteger();
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:20:23--------------------------------------------
	 */
	/**
	 * Returns the base 10 anti-logarithms of {@code x} with any overflow truncated
	 * to fit the provide bit length context.
	 * 
	 * @param x    the exponent.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>10<sup>x</sup></code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger antilog(final BigInteger x, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger z = Arith.pow(BigDecimal.TEN, new BigDecimal(cx), MathContext.DECIMAL128).toBigInteger();
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:31:15--------------------------------------------
	 */
	/**
	 * Returns the absolute value of {@code x} with any overflow truncated to fit
	 * the provide bit length context.
	 * 
	 * @param x    the single operand.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>|x|</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger abs(final BigInteger x, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger z = cx.abs();
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:32:15--------------------------------------------
	 */
	/**
	 * Returns the signum of {@code x}.
	 * 
	 * @param x    the single operand.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>sgn(x)</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger signum(final BigInteger x, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger z = new BigInteger(cx.signum() + "");
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:34:15--------------------------------------------
	 */
	/**
	 * Returns {@code x} raised to the power of 2 with any overflow truncated to fit
	 * the provide bit length context.
	 * 
	 * @param x    the base.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>x<sup>2</sup></code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger square(final BigInteger x, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger z = cx.pow(2);
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:34:15--------------------------------------------
	 */
	/**
	 * Returns {@code x} raised to the power of 3 with any overflow truncated to fit
	 * the provide bit length context.
	 * 
	 * @param x    the base.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>x<sup>3</sup></code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger cube(final BigInteger x, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger z = cx.pow(3);
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:40:02--------------------------------------------
	 */
	/**
	 * Returns square root of {@code x} with any overflow truncated to fit the
	 * provide bit length context.
	 * 
	 * @param x    the base.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>&#x221a;x</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger sqrt(final BigInteger x, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger z = cx.sqrt();
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:41:30--------------------------------------------
	 */
	/**
	 * Returns the cube root of {@code x} with any overflow truncated to fit the
	 * provide bit length context.
	 * 
	 * @param x    the base.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code><sup>3</sup>&#x221a;x</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger cbrt(final BigInteger x, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger z = Arith.pow(new BigDecimal(cx), BigDecimal.ONE.divide(new BigDecimal(3), MathContext.DECIMAL128),
				MathContext.DECIMAL128).toBigInteger();
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:43:10--------------------------------------------
	 */
	/**
	 * Returns <code>gcd(x, y)</code> (greatest common divisor a.k.a highest common
	 * factor [h.c.f]) with any overflow truncated to fit the provide bit length
	 * context.
	 * 
	 * @param x    the left operand.
	 * @param y    the right operand.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>gcd(x, y)</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger gcd(final BigInteger x, final BigInteger y, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger cy = convert(y, MATH, bits);
		BigInteger z = cx.gcd(cy);
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:48:17--------------------------------------------
	 */
	/**
	 * Returns <code>lcm(x, y)</code> (lowest common factor) with any overflow
	 * truncated to fit the provide bit length context.
	 * 
	 * @param x    the left operand.
	 * @param y    the right operand.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>lcm(x, y)</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger lcm(final BigInteger x, final BigInteger y, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger cy = convert(y, MATH, bits);
		BigInteger z = cx.multiply(cy).divide(cx.gcd(cy));
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:49:45--------------------------------------------
	 */
	/**
	 * Returns the factorial of {@code x} with any overflow truncated to fit the
	 * provide bit length context.
	 * 
	 * @param x    the single operand.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code><i>n</i>!</code>
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 * @throws StackOverflowError  If the argument is too big which in turn would
	 *                             cause the method to recurse too deeply.
	 */
	public BigInteger factorial(final BigInteger x, final int bits) throws ArithmeticException, StackOverflowError {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger z = Arith.factorial(cx);
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:51:55--------------------------------------------
	 */
	/**
	 * Returns <code><i>x</i>C<i>y</i></code> with any overflow truncated to fit the
	 * provide bit length context.
	 * 
	 * @param x    the left operand.
	 * @param y    the right operand.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code><i>x</i>C<i>y</i></code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 * @throws StackOverflowError  If the argument is too big which in turn would
	 *                             cause the method to recurse too deeply.
	 */
	public BigInteger combination(final BigInteger x, final BigInteger y, final int bits)
			throws ArithmeticException, StackOverflowError {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger cy = convert(y, MATH, bits);
		BigInteger c = Arith.factorial(cx);
		BigInteger d = Arith.factorial(cy);
		BigInteger e = c.subtract(cy);
		BigInteger f = c.divide(d.multiply(e));
		c = BigInteger.valueOf(cx.min(cy).signum());
		BigInteger z = f.multiply(c);
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:54:37--------------------------------------------
	 */
	/**
	 * Returns <code><i>x</i>P<i>y</i></code> with any overflow truncated to fit the
	 * provide bit length context.
	 * 
	 * @param x    the left operand.
	 * @param y    the right operand.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code><i>x</i>P<i>y</i></code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 * @throws StackOverflowError  If the argument is too big which in turn would
	 *                             cause the method to recurse too deeply.
	 */
	public BigInteger permutation(final BigInteger x, final BigInteger y, final int bits)
			throws ArithmeticException, StackOverflowError {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger cy = convert(y, MATH, bits);
		BigInteger n = Arith.factorial(cx.abs()).divide(Arith.factorial(cx.abs().subtract(cy.abs())));
		BigInteger z = n.multiply(new BigInteger(cx.min(cy).signum() + ""));
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}
	
	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:54:37--------------------------------------------
	 */
	/**
	 * Returns <code>x mod <i>y</i></code> with any overflow truncated to fit the
	 * provide bit length context.
	 * 
	 * @param x    the left operand.
	 * @param y    the modulus (right operand).
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>x mod <i>y</i></code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger mod(final BigInteger x, final BigInteger y, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger cy = convert(y, MATH, bits);
		BigInteger z = cx.mod(cy);
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:54:37--------------------------------------------
	 */
	/**
	 * Returns <code>x % <i>y</i></code> with any overflow truncated to fit the
	 * provide bit length context.
	 * 
	 * @param x    the left operand.
	 * @param y    the right operand.
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>x % <i>y</i></code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger rem(final BigInteger x, final BigInteger y, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger cy = convert(y, MATH, bits);
		BigInteger z = cx.remainder(cy);
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:58:50--------------------------------------------
	 */
	/**
	 * Returns minimum argument between {@code x, y} with any overflow truncated to
	 * fit the provide bit length context.
	 * 
	 * @param x    the left operand.
	 * @param y    the modulus (right operand).
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>min(x, y)</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger min(final BigInteger x, final BigInteger y, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger cy = convert(y, MATH, bits);
		BigInteger z = cx.min(cy);
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 12:58:50--------------------------------------------
	 */
	/**
	 * Returns maximum argument between {@code x, y} with any overflow truncated to
	 * fit the provide bit length context.
	 * 
	 * @param x    the left operand.
	 * @param y    the modulus (right operand).
	 * @param bits the length of the bits to work with. If this is 0, then no
	 *             truncation will take place. However, only {@link #MATH} is
	 *             allowed to have that argument. please see the documentation of
	 *             this enum above.
	 * @return <code>max(x, y)</code>.
	 * @throws ArithmeticException if {@code this} != {@code MATH} and any of the 2
	 *                             predicate is true:
	 *                             <ul>
	 *                             <li>The {@code BigInteger} argument is
	 *                             mathematically signed i.e it contains in it's
	 *                             {@code String} the negative sign '-'.
	 *                             <li>The {@code int} argument is 0.
	 *                             </ul>
	 */
	public BigInteger max(final BigInteger x, final BigInteger y, final int bits) throws ArithmeticException {
		BigInteger cx = convert(x, MATH, bits);
		BigInteger cy = convert(y, MATH, bits);
		BigInteger z = cx.max(cy);
		z = bits != 0 ? MATH.convert(MATH.checkValue(z, bits), this, bits) : z;
		if (this != MATH)
			z = checkValue(z, bits);
		return z;
	}

}
