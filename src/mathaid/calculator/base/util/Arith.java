/**
 * 
 */
package mathaid.calculator.base.util;

import static java.lang.System.out;
import static mathaid.calculator.base.util.Constants.HALF;
import static mathaid.calculator.base.util.Constants.ONE;
import static mathaid.calculator.base.util.Constants.TWO;
import static mathaid.calculator.base.util.Constants.ZERO;
import static mathaid.calculator.base.util.Constants.pi;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

import mathaid.calculator.base.converter.AngleUnit;
import mathaid.calculator.base.value.BigFraction;
import mathaid.calculator.base.value.BitLength;

/*
 * Date: 8 May 2020----------------------------------------------------------- 
 * Time created: 14:45:46---------------------------------------------------  
 * Package: mathaid.calculator.base.function------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: Arith.java------------------------------------------------------ 
 * Class name: Arith------------------------------------------------ 
 */
/**
 * Containing methods for performing numeric operation with java's Big numbers.
 * <p>
 * All methods that take in a {@code MathContext} object will throw a
 * {@code NullPointerException} if the {@code MathContext} parameter is
 * {@code null}.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public final class Arith {
	/*
	 * Date: 8 May 2020-----------------------------------------------------------
	 * Time created: 14:45:46---------------------------------------------------
	 */
	/**
	 */
	private Arith() {
	}

	private static class PExp {
		BigInteger prime;
		long exp;

		PExp(BigInteger prime, long exp) {
			this.prime = prime;
			this.exp = exp;
		}
	}

	static final BigInteger BIG_THREE = BigInteger.valueOf(3);

	public static void moTest(BigInteger a, BigInteger n) {
		if (!n.isProbablePrime(20)) {
			out.println("Not computed. Modulus must be prime for this algorithm");
			return;
		}

		if (a.bitLength() < 100)
			out.printf("ord(%s)", a);
		else
			out.print("ord([big])");

		if (n.bitLength() < 100)
			out.printf(" mode %s ", n);
		else
			out.print(" mod [big] ");
		BigInteger mob = moBachShallit58(a, n, factor(n.subtract(n.subtract(BigInteger.ONE))));
		out.println("= " + mob);

	}

	private static BigInteger moBachShallit58(BigInteger a, BigInteger n, List<PExp> pf) {
		BigInteger n1 = n.subtract(BigInteger.ONE);
		BigInteger mo = BigInteger.ONE;
		for (PExp pe : pf) {
			BigInteger y = n1.divide(pe.prime.pow((int) pe.exp));
			long o = 0;
			BigInteger x = a.modPow(y, n.abs());
			while (x.compareTo(BigInteger.ONE) > 0) {
				x = x.modPow(pe.prime, n.abs());
				o++;
			}
			BigInteger o1 = BigInteger.valueOf(o);
			o1 = pe.prime.pow(o1.intValue());
			o1 = o1.divide(mo.gcd(o1));
			mo = mo.multiply(o1);
		}
		return mo;
	}

	private static List<PExp> factor(BigInteger n) {
		List<PExp> pf = new ArrayList<>();
		BigInteger nn = n;
		Long e = 0L;
		while (!nn.testBit(e.intValue()))
			e++;
		if (e > 0L) {
			nn = nn.shiftRight(e.intValue());
			pf.add(new PExp(BigInteger.TWO, e));
		}
		BigInteger s = sqrt(nn);
		BigInteger d = BIG_THREE;
		while (nn.compareTo(BigInteger.ONE) > 0) {
			if (d.compareTo(s) > 0)
				d = nn;
			e = 0L;
			while (true) {
				BigInteger[] qr = nn.divideAndRemainder(d);
				if (qr[1].bitLength() > 0)
					break;
				nn = qr[0];
				e++;
			}
			if (e > 0L) {
				pf.add(new PExp(d, e));
				s = sqrt(nn);
			}
			d = d.add(BigInteger.TWO);
		}
		return pf;
	}

	public static BigInteger sqrt(BigInteger n) {
//		BigInteger b = n;
//		while (true) {
//			BigInteger a = b;
//			b = n.divide(a).add(a).shiftRight(1);
//			if (b.compareTo(a) >= 0)
//				return a;
//		}
		return sqrt(new BigDecimal(n), Constants.DEFAULT_ROUND).toBigInteger();
	}

	public static BigInteger rotateLeft(BigInteger n, int position, BitLength bits) {
		BigInteger x = n.shiftLeft(position);// x=n<<position
		BigInteger y;
		y = (bits == BitLength.BIT_UNLTD ? n.shiftRight(n.bitLength() - position)
				: n.shiftRight(bits.length() - position));// y=n>>32
		x = x.or(y);// x=x|y
		return x;
	}

	public static BigInteger rotateRight(BigInteger n, int position, BitLength bits) {
		BigInteger x = n.shiftRight(position);
		BigInteger y;
		y = (bits == BitLength.BIT_UNLTD ? n.shiftLeft(n.bitLength() - position)
				: n.shiftLeft(bits.length() - position));
		x = x.or(y);
		return x;
	}

	public static BigInteger lcm(BigInteger a, BigInteger b) {
		return new BigFraction(BigInteger.ONE, a).lcm(new BigFraction(BigInteger.ONE, b));
	}

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 13:13:49--------------------------------------------
	 */
	/**
	 * Calculates and returns the factorial of a {@code BigInteger}.
	 * 
	 * @param n a {@code BigInteger}.
	 * @return the result of the factorial (<i>n</i>!) of the argument
	 * @throws StackOverflowError If the argument is too big which in turn would
	 *                            cause the method to recurse too deeply.
	 */
	public static BigInteger factorial(BigInteger n) throws StackOverflowError {
		if (n.signum() == 0)
			return BigInteger.ONE;
		BigInteger recurse = factorial(n.abs().subtract(BigInteger.ONE));

		return n.multiply(recurse);
	}

	/////////////////////////////////////////////////////////////////////
	////////////////// Trigonometrical function methods ////////////////
	///////////////////////////////////////////////////////////////////

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 13:20:31--------------------------------------------
	 */
	/**
	 * Calculates the trigonometric sine of the {@code BigDecimal} argument then
	 * returns a {@code BigDecimal} result using the given {@code AngleUnit}
	 * conversion and rounded using the specified context settings.
	 * 
	 * @param n    a {@code BigDecimal} in the same angular unit as the specified
	 *             {@code AngleUnit}.
	 * @param trig the current unit of angle measurement the {@code BigDecimal}
	 *             argument uses.
	 * @param c    the rounding settings used to round the final result.
	 * @return sin(n) rounded using the specified {@code MathContext} settings.
	 */
	public static BigDecimal sin(BigDecimal n, AngleUnit trig, MathContext c) {
		// TODO: provide optimisation
		n = trig.convert(n, AngleUnit.RAD, c);// convert to radians
		n = sinRad(new Apfloat(n, c.getPrecision()));
		return n;
	}

	private static Apfloat asApfloat(BigDecimal n, int precision) {
		return ApfloatMath.round(new Apfloat(n), precision, RoundingMode.HALF_EVEN);
	}

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 15:14:55--------------------------------------------
	 */
	/**
	 * <p>
	 * Attempts calculation of the sine of an angle in radians by first using
	 * argument reduction techniques, and finally a taylor series polynomial.
	 * </p>
	 * <p>
	 * This algorithm can return results up to 80 decimal places of precision. In
	 * the near future, It may use look-up tables and various aggressive argument
	 * reduction techniques to get results with a precision of 4000 decimal places.
	 * </p>
	 * 
	 * @param x       the angle to be calculated in radians.
	 * @param c       the rounding object.
	 * @param epsilon the length of the oscillation of the power series used.
	 * @return the sin of an angle correct to the number of settings the
	 *         {@code MathContext} object allows.
	 */
	private static BigDecimal sinRad(Apfloat n) {
//		if (n.compareTo(asApfloat(q2((int) n.precision()).divide(SIX,
//				new MathContext((int) n.precision(), RoundingMode.HALF_EVEN)), (int) n.precision())) == 0) {
//			return HALF;
//		} else if (n.compareTo(asApfloat(q2((int) n.precision()).divide(FOUR,
//				new MathContext((int) n.precision(), RoundingMode.HALF_EVEN)), (int) n.precision())) == 0) {
//			return sqrt2Div2((int) n.precision());
//		}
		return new BigDecimal(ApfloatMath.sin(n).toString());
	}

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 13:39:50--------------------------------------------
	 */
	/**
	 * Calculates the trigonometric cosine of the {@code BigDecimal} argument then
	 * returns a {@code BigDecimal} result using the given {@code AngleUnit}
	 * conversion and rounded using the specified context settings.
	 * 
	 * @param n    a {@code BigDecimal} in the same angle unit as the specified
	 *             {@code AngleUnit}.
	 * @param trig the current unit of angle measurement the {@code BigDecimal}
	 *             argument uses.
	 * @param c    the rounding settings used to round the final result.
	 * @return cos(n) rounded using the specified {@code MathContext} settings.
	 */
	public static BigDecimal cos(BigDecimal n, AngleUnit trig, MathContext c) {
		// TODO: provide optimisation
		n = trig.convert(n, AngleUnit.RAD, c);// convert to radians
		n = cosRad(new Apfloat(n, c.getPrecision()));
		return n;
	}

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 15:14:55--------------------------------------------
	 */
	/**
	 * <p>
	 * Attempts calculation of the cosine of an angle in radians by first using
	 * argument reduction techniques, and finally a taylor series polynomial.
	 * </p>
	 * <p>
	 * This algorithm can return results up to 80 decimal places of precision. In
	 * the near future, It may use look-up tables and various aggressive argument
	 * reduction techniques to get results with a precision of 4000 decimal places.
	 * </p>
	 * 
	 * @param x       the angle to be calculated in radians.
	 * @param c       the rounding object.
	 * @param epsilon the length of the oscillation of the power series used.
	 * @return the cosine of an angle correct to the number of settings the
	 *         {@code MathContext} object allows.
	 */
	private static BigDecimal cosRad(Apfloat x) {

//		if (x.compareTo(asApfloat(q2((int) x.precision()).divide(SIX,
//				new MathContext((int) x.precision(), RoundingMode.HALF_EVEN)), (int) x.precision())) == 0)
//			return sqrt3Div2((int) x.precision());
//		else if (x.compareTo(asApfloat(q2((int) x.precision()).divide(FOUR,
//				new MathContext((int) x.precision(), RoundingMode.HALF_EVEN)), (int) x.precision())) == 0)
//			return sqrt2Div2((int) x.precision());

		return new BigDecimal(ApfloatMath.cos(x).toString());
	}

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 15:03:11-------------------------------------------- What I
	 * need is to add a digit detector system whereby
	 */
	/**
	 * Calculates the trigonometric tangent of an angle and returns a result in
	 * {@code BigDecimal} rounded using the provided {@code MathContext} object.The
	 * angle {@code n} is assumed to be in the same angular measurement as is
	 * represented by the specified {@code AngleUnit} object.
	 * 
	 * @param n    the angle as a {@code BigDecimal} in the same angular unit as the
	 *             {@code AngleUnit} object.
	 * @param trig the current unit of angle measurement the {@code BigDecimal}
	 *             argument uses.
	 * @param c    the rounding settings used to round the final result.
	 * @return tan(n) rounded using the specified {@code MathContext} settings.
	 */
	public static BigDecimal tan(BigDecimal n, AngleUnit trig, MathContext c) {
		// TODO: provide optimisation
		n = trig.convert(n, AngleUnit.RAD, c);// convert to radians
		n = tanRad(asApfloat(n, c.getPrecision()));
		return n;
	}

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 15:14:55--------------------------------------------
	 */
	/**
	 * <p>
	 * Attempts calculation of the tangent of an angle in radians by first using
	 * argument reduction techniques, and finally a returns sin(x)/cos(x).
	 * </p>
	 * <p>
	 * This algorithm can return results up to 80 decimal places of precision. In
	 * the near future, It may use look-up tables and various aggressive argument
	 * reduction techniques to get results with a precision of 4000 decimal places.
	 * This algorithm does not use a power series.
	 * </p>
	 * 
	 * @param x the angle to be calculated in radians.
	 * @param c the rounding object.
	 * @return the tangent of an angle correct to the number of settings the
	 *         {@code MathContext} object allows.
	 */
	private static BigDecimal tanRad(Apfloat x) {
//		int xp = (int) x.precision();
//		System.out.println(x.precision(x.precision()));
//		System.err.println(asApfloat(q2((int) x.precision()).divide(FOUR,
//				new MathContext((int) x.precision(), RoundingMode.HALF_EVEN)), (int) x.precision()));
//		if (x.precision(x.precision()-2).compareTo(asApfloat(q2((int) x.precision()).divide(FOUR,
//				new MathContext((int) x.precision(), RoundingMode.HALF_EVEN)), (int) x.precision() - 2)) == 0)
//			return ONE;
		return new BigDecimal(ApfloatMath.tan(x).toString());
	}

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 15:33:45--------------------------------------------
	 */
	/**
	 * Calculates the arc tangent of the given value x, and returns the result in an
	 * angular unit specified by the {@code AngleUnit} object rounded to the
	 * provided context settings.
	 * 
	 * @param x    the value whose arc tangent is to be calculated.
	 * @param trig the angular measurement in which the resulting angle is to be
	 *             returned.
	 * @param c    the rounding settings used to round the final result.
	 * @return the atan(x).
	 */
	public static BigDecimal atan(BigDecimal x, AngleUnit trig, MathContext c) {
		if (x.abs().compareTo(ONE) == 0)
			return AngleUnit.DEG.convert(new BigDecimal(45 * x.signum()), trig, c);
		x = aTanRad(asApfloat(x, c.getPrecision()));
		return AngleUnit.RAD.convert(x, trig, c);
	}

	/*
	 * Date: 30 Aug 2020-----------------------------------------------------------
	 * Time created: 13:51:21--------------------------------------------
	 */
	/**
	 * Returns the angle <i>theta</i> from the conversion of rectangular coordinates
	 * ({@code x},&nbsp;{@code y}) to polar coordinates (r,&nbsp;<i>theta</i>). This
	 * method computes the phase <i>theta</i> by computing an arc tangent
	 * 
	 * @param y    the ordinate
	 * @param x    the abscissa
	 * @param trig the angle unit in which the result is returned
	 * @param c    the rounding settings used to round the final result.
	 * @return <code>atan2(y, x)</code>
	 * @throws ArithmeticException <code>if x == 0 && y == 0</code>
	 */
	public static BigDecimal atan(BigDecimal y, BigDecimal x, AngleUnit trig, MathContext c)
			throws ArithmeticException {
		if (x.signum() > 0)
			return atan(y.divide(x, c), trig, c);
		else if (x.signum() < 0 && y.signum() >= 0) {
			BigDecimal z = aTanRad(asApfloat(y.divide(x, c), c.getPrecision())).add(pi(c.getPrecision()));
			return AngleUnit.RAD.convert(z, trig, c);
		} else if (x.signum() < 0 && y.signum() < 0) {
			BigDecimal z = aTanRad(asApfloat(y.divide(x, c), c.getPrecision())).subtract(pi(c.getPrecision()));
			return AngleUnit.RAD.convert(z, trig, c);
		} else if (x.signum() == 0 && y.signum() > 0)
			return AngleUnit.RAD.convert(pi(c.getPrecision()).divide(TWO), trig, c);
		else if (x.signum() == 0 && y.signum() < 0)
			return AngleUnit.RAD.convert(pi(c.getPrecision()).negate().divide(TWO), trig, c);
		else if (x.signum() == 0 && y.signum() == 0)
			throw new ArithmeticException("both values cannot be 0 simultaneously");
		return null;
	}

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 15:43:21--------------------------------------------
	 */
	/**
	 * <p>
	 * Calculates the arc tangent of the given value and returns a result in radians
	 * by first using argument reduction techniques and finally a taylor polynomial.
	 * </p>
	 * <p>
	 * This algorithm does not make use of look-up tables and can only return
	 * results no more than 80 decimal places. In the near future, This will be
	 * different and may return results up to 4000, digits of precision
	 * </p>
	 * 
	 * @param x       the value whose arc tangent is to be calculated.
	 * @param c       the rounding object
	 * @param epsilon the oscillator of the power series
	 * @return the arc tangent of x in radians.
	 */
	private static BigDecimal aTanRad(Apfloat x) {
		return new BigDecimal(ApfloatMath.atan(x).toString());
	}

	/*
	 * 
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 16:02:48--------------------------------------------
	 */
	/**
	 * Calculates the arc sine of the given value x, and returns the result in an
	 * angular unit specified by the {@code AngleUnit} object rounded to the
	 * provided context settings for all |x| < 1. This method will throw a
	 * {@code ArithmeticException} if |x| is outside of the domain specified by [0,
	 * 1].
	 * 
	 * @param x    the value whose arc sine is to be calculated.
	 * @param trig the angular measurement in which the resulting angle is to be
	 *             returned.
	 * @param c    the rounding settings used to round the final result.
	 * @return asin(x).
	 * @throws ArithmeticException if |x| > 1.
	 */
	public static BigDecimal asin(BigDecimal x, final AngleUnit trig, final MathContext c) throws ArithmeticException {
		if (x.abs().compareTo(ZERO) == 0)
			return Constants.ZERO;
		else if (x.abs().compareTo(HALF) == 0)
			return AngleUnit.DEG.convert(new BigDecimal(30 * x.signum()), trig, c);
		else if (x.abs().compareTo(ONE) == 0)
			return AngleUnit.DEG.convert(new BigDecimal(90 * x.signum()), trig, c);
		else if (x.abs().compareTo(ONE) > 0)
			throw new ArithmeticException("asin undefined");

		return AngleUnit.RAD.convert(aSin(asApfloat(x, c.getPrecision())), trig, c);
	}

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 16:12:49--------------------------------------------
	 */
	/**
	 * Calculates the arc sine of the given value x, and returns the result in the
	 * specified {@code AngleUnit} and rounded to about 80 decimal places.
	 * 
	 * 
	 * @param x       the value whose arc tangent is to be calculated.
	 * @param t       the angular measurement in which the resulting angle is to be
	 *                returned.
	 * @param epsilon the oscillator of the power series
	 * @return the arc tangent of x in the specified angular unit
	 */
	private static BigDecimal aSin(Apfloat x) {
		return new BigDecimal(ApfloatMath.asin(x).toString());
	}

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 16:18:17--------------------------------------------
	 */
	/**
	 * Calculates the arc cosine of the given value x, and returns the result in an
	 * angular unit specified by the {@code AngleUnit} object rounded to the
	 * provided context settings for all |x| < 1. This method will throw a
	 * {@code ArithmeticException} if |x| is outside of the domain specified by [0,
	 * 1].
	 * 
	 * @param x    the value whose arc cosine is to be calculated.
	 * @param trig the angular measurement in which the resulting angle is to be
	 *             returned.
	 * @param c    the rounding settings used to round the final result.
	 * @return acos(x).
	 * @throws ArithmeticException if |x| > 1.
	 */
	public static BigDecimal acos(BigDecimal x, AngleUnit trig, MathContext c) throws ArithmeticException {
		if (x.abs().compareTo(Constants.ZERO) == 0)
			return AngleUnit.DEG.convert(new BigDecimal(90), trig, c);
		else if (x.compareTo(Constants.HALF) == 0)
			return AngleUnit.DEG.convert(new BigDecimal(60), trig, c);
		else if (x.compareTo(Constants.ONE) == 0)
			return Constants.ZERO;
		else if (x.compareTo(Constants.HALF.negate()) == 0)
			return AngleUnit.DEG.convert(new BigDecimal(120), trig, c);
		else if (x.compareTo(Constants.ONE.negate()) == 0)
			return AngleUnit.DEG.convert(new BigDecimal(180), trig, c);
		else if (x.abs().compareTo(Constants.ONE) > 0)
			throw new ArithmeticException("acos undefined");

		x = AngleUnit.RAD.convert(new BigDecimal(ApfloatMath.acos(asApfloat(x, c.getPrecision())).toString()), trig, c);
		return x;
	}

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 16:21:26--------------------------------------------
	 */
	/**
	 * Calculates the hyperbolic sine of x and returns a result rounded according to
	 * the provided {@code MathContext} object. This calculation is accurate up to
	 * 80 decimal places.
	 * 
	 * @param x the value whose hyperbolic sine is to be calculated.
	 * @param c the rounding settings used to round the final result.
	 * @return (e<sup>x</sup> - e<sup>-x</sup>) / 2, where e is the constant
	 *         2.7182...
	 * @throws ArithmeticException if |x| > 99,999
	 */
	public static BigDecimal sinh(final BigDecimal x, MathContext c) throws ArithmeticException {
		return new BigDecimal(ApfloatMath.sinh(asApfloat(x, c.getPrecision())).toString());
	}

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 16:29:54--------------------------------------------
	 */
	/**
	 * This method was made as a helper method for calculating the hyperbolic sine
	 * of x using the appropriate taylor polynomial. However, it returned inaccurate
	 * results when the arguments became huge and would only return accurate results
	 * if the {@code epsilon} value was expanded. This expansion would in turn cause
	 * the function to oscillate for a considerable amount of time incurring
	 * performance penalties. For example, sinh(2,302), would only return accurate
	 * results if oscillation was set to 1500. sinh(5000) would in turn return
	 * inaccurate results unless oscillation was set to a much higher values, for
	 * which the system would incur massive penalties. Therefore, because of the
	 * reasons given this method will not be used in production but will be retained
	 * for further study, perhaps with the aid of look-up tables, and argument
	 * reduction (which it presently lacks) it will perform better.
	 * 
	 * @param x       the value whose hyperbolic sine is to be calculated.
	 * @param c       the rounding object
	 * @param epsilon the oscillator of the power series
	 * @return sinh(x).
	 */
	static BigDecimal sinH(BigDecimal x, MathContext c, int epsilon) {
		/*
		 * Not optimised should be able to provide accurate ans within a second @ 2,302
		 * | windows calculator limit is 23026
		 */
		BigDecimal y = Constants.ZERO;
		for (int i = 0; i < epsilon; i++) {
			int n = (2 * i) + 1;
			BigDecimal numer = x.pow(n);
			try {
				y = y.add(numer.divide(factorial(new BigDecimal(n)), c));
			} catch (StackOverflowError e) {
				e.printStackTrace();
				break;
			}
		}
		return y;
	}

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 16:21:26--------------------------------------------
	 */
	/**
	 * Calculates the hyperbolic cosine of x and returns a result rounded according
	 * to the provided {@code MathContext} object. This calculation is accurate up
	 * to 80 decimal places.
	 * 
	 * @param x the value whose hyperbolic cosine is to be calculated.
	 * @param c the rounding settings used to round the final result.
	 * @return (e<sup>x</sup> + e<sup>-x</sup>) / 2, where e is the constant
	 *         2.7182...
	 * @throws ArithmeticException if |x| > 99,999
	 */
	public static BigDecimal cosh(BigDecimal x, MathContext c) throws ArithmeticException {
		return new BigDecimal(ApfloatMath.cosh(asApfloat(x, c.getPrecision())).toString());
	}

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 16:29:54--------------------------------------------
	 */
	/**
	 * This method was made as a helper method for calculating the hyperbolic cosine
	 * of x using the appropriate taylor polynomial. However, it returned inaccurate
	 * results when the arguments became huge and would only return accurate results
	 * if the {@code epsilon} value was expanded. This expansion would in turn cause
	 * the function to oscillate for a considerable amount of time incurring
	 * performance penalties. For example, cosh(2,302), would only return accurate
	 * results if oscillation was set to 1500. cosh(5000) would in turn return
	 * inaccurate results unless oscillation was set to a much higher values, for
	 * which the system would incur massive penalties. Therefore, because of the
	 * reasons given this method will not be used in production but will be retained
	 * for further study, perhaps with the aid of look-up tables, and argument
	 * reduction (which it presently lacks) it will perform better.
	 * 
	 * @param x       the value whose hyperbolic cosine is to be calculated.
	 * @param c       the rounding object
	 * @param epsilon the oscillator of the power series
	 * @return cosh(x).
	 */
	static BigDecimal cosH(BigDecimal x, MathContext c, int epsilon) {
		/*
		 * Not optimised should be able to provide accurate ans within a second @ 2,302
		 * | windows calculator limit is 23026
		 */
		BigDecimal y = Constants.ZERO;
		for (int i = 0; i < epsilon; i++) {
			int n = (2 * i);
			BigDecimal numer = x.pow(n);
			try {
				y = y.add(numer.divide(factorial(new BigDecimal(n)), c));
			} catch (StackOverflowError e) {
				e.printStackTrace();
				break;
			}
		}
		return y;
	}

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 16:51:18--------------------------------------------
	 */
	/**
	 * Calculates the hyperbolic tangent of x and returns a result rounded according
	 * to the provided {@code MathContext} object. This calculation is accurate up
	 * to 80 decimal places. In addition, all values above 40 return 1.
	 * 
	 * @param x the value whose hyperbolic cosine is to be calculated.
	 * @param c the rounding settings used to round the final result.
	 * @return sinh(x)/cosh(x).
	 */
	public static BigDecimal tanh(BigDecimal x, MathContext c) {
		return new BigDecimal(ApfloatMath.tanh(asApfloat(x, c.getPrecision())).toString());
	}

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 16:55:54--------------------------------------------
	 */
	/**
	 * Calculates the inverse hyperbolic sine of x rounded to the provided context
	 * settings.
	 * 
	 * @param x the value whose inverse hyperbolic sine is to be calculated.
	 * @param c the rounding settings used to round the final result.
	 * @return the inverse hyperbolic sine of x.
	 */
	public static BigDecimal asinh(BigDecimal x, MathContext c) {
		return new BigDecimal(ApfloatMath.asinh(asApfloat(x, c.getPrecision())).toString());
	}

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 17:08:49--------------------------------------------
	 */
	/**
	 * Calculates the inverse hyperbolic cosine of x rounded to the provided context
	 * settings.
	 * 
	 * @param x the value whose inverse hyperbolic cosine is to be calculated.
	 * @param c the rounding settings used to round the final result.
	 * @return the inverse hyperbolic cosine of x.
	 */
	public static BigDecimal acosh(BigDecimal x, MathContext c) {
		return new BigDecimal(ApfloatMath.acosh(asApfloat(x, c.getPrecision())).toString());
	}

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 17:10:39--------------------------------------------
	 */
	/**
	 * Calculates the inverse hyperbolic tangent of x rounded to the provided
	 * context settings.
	 * 
	 * @param x the value whose inverse hyperbolic tangent is to be calculated.
	 * @param c the rounding settings used to round the final result.
	 * @return the inverse hyperbolic tangent of x.
	 * @throws ArithmeticException if |x| &#x2265 1
	 */
	public static BigDecimal atanh(BigDecimal x, MathContext c) throws ArithmeticException {
		return new BigDecimal(ApfloatMath.atanh(asApfloat(x, c.getPrecision())).toString());
	}

	/////////////////////////////////////////////////////////////////////
	/////////////////// Logarithmic function methods ///////////////////
	///////////////////////////////////////////////////////////////////

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 17:19:38--------------------------------------------
	 */
	/**
	 * Calculates the natural logarithm of x and returns a result rounded using the
	 * specified context settings. If the argument is less than or equal to zero,
	 * then an {@code ArithmeticException} will be thrown
	 * 
	 * @param x the value whose natural logarithm is to be calculated.
	 * @param c the rounding settings used to round the final result.
	 * @return <code>log<sub><i>e</i></sub>(x)</code> i.e <code>ln(x)</code> rounded
	 *         using the given {@code MathContext}..
	 * @throws ArithmeticException if x <= 0
	 */
	public static BigDecimal log(BigDecimal x, MathContext c) throws ArithmeticException {
		return new BigDecimal(ApfloatMath.log(asApfloat(x, c.getPrecision())).toString());
	}

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 17:19:38--------------------------------------------
	 */
	/**
	 * Calculates <code>e<sup>x</sup></code>, (natural anti-logarithm) where
	 * <code>e = 2.7182...</code>, and returns a result rounded using the specified
	 * context settings.
	 * 
	 * @param x the value whose natural logarithm is to be calculated.
	 * @param c the rounding settings used to round the final result.
	 * @return the natural logarithm of the {@code BigDecimal} argument rounded
	 *         using the given {@code MathContext}.
	 * @throws ArithmeticException if x <= 0
	 */
	public static BigDecimal exp(BigDecimal x, MathContext c) {
		return new BigDecimal(ApfloatMath.exp(asApfloat(x, c.getPrecision())).toString());
	}

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 17:56:27--------------------------------------------
	 */
	/**
	 * Computes the base 10 logarithm of x and returns a result rounded using the
	 * provided context settings.
	 * 
	 * @param x the value whose base 10 logarithm is to be computed.
	 * @param c the rounding settings used to round the final result.
	 * @return the base 10 logarithm of x rounded using the given
	 *         {@code MathContext}..
	 * @throws ArithmeticException if x <= 0
	 */
	public static BigDecimal log10(BigDecimal x, MathContext c) throws ArithmeticException {
		return new BigDecimal(ApfloatMath
				.log(asApfloat(x, c.getPrecision()), asApfloat(BigDecimal.TEN, c.getPrecision())).toString());
	}

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 17:56:27--------------------------------------------
	 */
	/**
	 * Computes the base 2 logarithm of x and returns a result rounded using the
	 * provided context settings.
	 * 
	 * @param x the value whose base 2 logarithm is to be computed.
	 * @param c the rounding settings used to round the final result.
	 * @return the base 2 logarithm of x rounded using the given
	 *         {@code MathContext}..
	 * @throws ArithmeticException if x <= 0
	 */
	public static BigDecimal log2(BigDecimal x, MathContext c) {
		return new BigDecimal(
				ApfloatMath.log(asApfloat(x, c.getPrecision()), asApfloat(TWO, c.getPrecision())).toString());
	}

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 17:56:27--------------------------------------------
	 */
	/**
	 * Computes the logarithm of x in base <i>n</i> and returns a result rounded
	 * using the provided context settings.
	 * 
	 * @param x    the value whose base <i>n</i> logarithm is to be computed.
	 * @param base the base in which to compute x
	 * @param c    the rounding settings used to round the final result.
	 * @return the base <i>n</i> logarithm of x rounded using the given
	 *         {@code MathContext}..
	 * @throws ArithmeticException if x <= 0 or if base <= 0
	 */
	public static BigDecimal log(BigDecimal x, BigDecimal base, MathContext c) {
		return new BigDecimal(
				ApfloatMath.log(asApfloat(x, c.getPrecision()), asApfloat(base, c.getPrecision())).toString());
	}

	/////////////////////////////////////////////////////////////////////
	////////////////////// Power function methods //////////////////////
	///////////////////////////////////////////////////////////////////

	public static BigDecimal random(long digits, boolean gaussian) {
		return new BigDecimal(
				gaussian ? ApfloatMath.randomGaussian(digits).toString() : ApfloatMath.random(digits).toString());
	}

	public static BigDecimal cbrt(BigDecimal x, int precision) {
		return new BigDecimal(ApfloatMath.cbrt(asApfloat(x, precision)).toString());
	}

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 18:09:11--------------------------------------------
	 */
	/**
	 * Returns the square root of x rounding to the given context setting.
	 * 
	 * @param x the number whose square root is to be computed.
	 * @param c the rounding settings used to round the final result.
	 * @return the square root of x rounded to the given {@code MathContext}
	 *         setting.
	 */
	public static BigDecimal sqrt(BigDecimal x, MathContext c) {

		int signum = x.signum();
		if (signum == 1) {
			/*
			 * The following code draws on the algorithm presented in
			 * "Properly Rounded Variable Precision Square Root," Hull and Abrham, ACM
			 * Transactions on Mathematical Software, Vol 11, No. 3, September 1985, Pages
			 * 229-237.
			 *
			 * The BigDecimal computational model differs from the one presented in the
			 * paper in several ways: first BigDecimal numbers aren't necessarily
			 * normalised, second many more rounding modes are supported, including
			 * UNNECESSARY, and exact results can be requested.
			 *
			 * The main steps of the algorithm below are as follows, first argument reduce
			 * the value to the numerical range [1, 10) using the following relations:
			 *
			 * x = y * 10 ^ exp sqrt(x) = sqrt(y) * 10^(exp / 2) if exp is even sqrt(x) =
			 * sqrt(y/10) * 10 ^((exp+1)/2) is exp is odd
			 *
			 * Then use Newton's iteration on the reduced value to compute the numerical
			 * digits of the desired result.
			 *
			 * Finally, scale back to the desired exponent range and perform any adjustment
			 * to get the preferred scale in the representation.
			 */

			// The code below favors relative simplicity over checking
			// for special cases that could run faster.

			int preferredScale = x.scale() / 2;
			BigDecimal zeroWithFinalPreferredScale = BigDecimal.valueOf(0L, preferredScale);

			// First phase of numerical normalization, strip trailing
			// zeros and check for even powers of 10.
			BigDecimal stripped = x.stripTrailingZeros();
			int strippedScale = stripped.scale();

			// Numerically sqrt(10^2N) = 10^N
			if (isPowerOfTen(stripped) && strippedScale % 2 == 0) {
				BigDecimal result = BigDecimal.valueOf(1L, strippedScale / 2);
				if (result.scale() != preferredScale) {
					// Adjust to requested precision and preferred
					// scale as appropriate.
					result = result.add(zeroWithFinalPreferredScale, c);
				}
				return result;
			}

			// After stripTrailingZeros, the representation is normalized as
			//
			// unscaledValue * 10^(-scale)
			//
			// where unscaledValue is an integer with the mimimum
			// precision for the cohort of the numerical value. To
			// allow binary floating-point hardware to be used to get
			// approximately a 15 digit approximation to the square
			// root, it is helpful to instead normalize this so that
			// the significand portion is to right of the decimal
			// point by roughly (scale() - precision() +1).

			// Now the precision / scale adjustment
			int scaleAdjust = 0;
			int scale = stripped.scale() - stripped.precision() + 1;
			if (scale % 2 == 0) {
				scaleAdjust = scale;
			} else {
				scaleAdjust = scale - 1;
			}

			BigDecimal working = stripped.scaleByPowerOfTen(scaleAdjust);

			assert // Verify 0.1 <= working < 10
			Constants.ONE_TENTH.compareTo(working) <= 0 && working.compareTo(BigDecimal.TEN) < 0;

			// Use good ole' Math.sqrt to get the initial guess for
			// the Newton iteration, good to at least 15 decimal
			// digits. This approach does incur the cost of a
			//
			// BigDecimal -> double -> BigDecimal
			//
			// conversion cycle, but it avoids the need for several
			// Newton iterations in BigDecimal arithmetic to get the
			// working answer to 15 digits of precision. If many fewer
			// than 15 digits were needed, it might be faster to do
			// the loop entirely in BigDecimal arithmetic.
			//
			// (A double value might have as much many as 17 decimal
			// digits of precision; it depends on the relative density
			// of binary and decimal numbers at different regions of
			// the number line.)
			//
			// (It would be possible to check for certain special
			// cases to avoid doing any Newton iterations. For
			// example, if the BigDecimal -> double conversion was
			// known to be exact and the rounding mode had a
			// low-enough precision, the post-Newton rounding logic
			// could be applied directly.)

			BigDecimal guess = new BigDecimal(Math.sqrt(working.doubleValue()));
			int guessPrecision = 15;
			int originalPrecision = c.getPrecision();
			int targetPrecision;

			// If an exact value is requested, it must only need about
			// half of the input digits to represent since multiplying
			// an N digit number by itself yield a 2N-1 digit or 2N
			// digit result.
			if (originalPrecision == 0) {
				targetPrecision = stripped.precision() / 2 + 1;
			} else {
				targetPrecision = originalPrecision;
			}

			// When setting the precision to use inside the Newton
			// iteration loop, take care to avoid the case where the
			// precision of the input exceeds the requested precision
			// and rounding the input value too soon.
			BigDecimal approx = guess;
			int workingPrecision = working.precision();
			do {
				int tmpPrecision = Math.max(Math.max(guessPrecision, targetPrecision + 2), workingPrecision);
				MathContext mcTmp = new MathContext(tmpPrecision, RoundingMode.HALF_EVEN);
				// approx = 0.5 * (approx + fraction / approx)
				approx = Constants.ONE_HALF.multiply(approx.add(working.divide(approx, mcTmp), mcTmp));
				guessPrecision *= 2;
			} while (guessPrecision < targetPrecision + 2);

			BigDecimal result;
			RoundingMode targetRm = c.getRoundingMode();
			if (targetRm == RoundingMode.UNNECESSARY || originalPrecision == 0) {
				RoundingMode tmpRm = (targetRm == RoundingMode.UNNECESSARY) ? RoundingMode.DOWN : targetRm;
				MathContext mcTmp = new MathContext(targetPrecision, tmpRm);
				result = approx.scaleByPowerOfTen(-scaleAdjust / 2).round(mcTmp);

				// If result*result != this numerically, the square
				// root isn't exact
				if (x.subtract(result.multiply(result)).compareTo(BigDecimal.ZERO) != 0) {
					throw new ArithmeticException("Computed square root not exact.");
				}
			} else {
				result = approx.scaleByPowerOfTen(-scaleAdjust / 2).round(c);
			}

			if (result.scale() != preferredScale) {
				// The preferred scale of an add is
				// max(addend.scale(), augend.scale()). Therefore, if
				// the scale of the result is first minimized using
				// stripTrailingZeros(), adding a zero of the
				// preferred scale rounding the correct precision will
				// perform the proper scale vs precision tradeoffs.
				result = result.stripTrailingZeros().add(zeroWithFinalPreferredScale,
						new MathContext(originalPrecision, RoundingMode.UNNECESSARY));
			}
			assert squareRootResultAssertions(x, result, c);
			return result;
		}
		switch (signum) {
		case -1:
			throw new ArithmeticException("Attempted square root " + "of negative BigDecimal");
		case 0:
			return BigDecimal.valueOf(0L, x.scale() / 2);

		default:
			throw new AssertionError("Bad value from signum");
		}
	}

	private static boolean isPowerOfTen(BigDecimal x) {
		return BigInteger.ONE.equals(x.unscaledValue());
	}

	/**
	 * For nonzero values, check numerical correctness properties of the computed
	 * result for the chosen rounding mode.
	 *
	 * For the directed roundings, for DOWN and FLOOR, result^2 must be {@code <=}
	 * the input and (result+ulp)^2 must be {@code >} the input. Conversely, for UP
	 * and CEIL, result^2 must be {@code >=} the input and (result-ulp)^2 must be
	 * {@code <} the input.
	 */
	private static boolean squareRootResultAssertions(BigDecimal x, BigDecimal result, MathContext mc) {
		if (result.signum() == 0) {
			return squareRootZeroResultAssertions(x);
		}
		RoundingMode rm = mc.getRoundingMode();
		BigDecimal ulp = result.ulp();
		BigDecimal neighborUp = result.add(ulp);
		// Make neighbor down accurate even for powers of ten
		if (isPowerOfTen(x)) {
			ulp = ulp.divide(BigDecimal.TEN);
		}
		BigDecimal neighborDown = result.subtract(ulp);

		// Both the starting value and result should be nonzero and positive.
		if (result.signum() != 1 || x.signum() != 1) {
			return false;
		}

		switch (rm) {
		case DOWN:
		case FLOOR:
			return result.multiply(result).compareTo(x) <= 0 && neighborUp.multiply(neighborUp).compareTo(x) > 0;

		case UP:
		case CEILING:
			return result.multiply(result).compareTo(x) >= 0 && neighborDown.multiply(neighborDown).compareTo(x) < 0;

		case HALF_DOWN:
		case HALF_EVEN:
		case HALF_UP:
			BigDecimal err = result.multiply(result).subtract(x).abs();
			BigDecimal errUp = neighborUp.multiply(neighborUp).subtract(x);
			BigDecimal errDown = x.subtract(neighborDown.multiply(neighborDown));
			// All error values should be positive so don't need to
			// compare absolute values.

			int err_comp_errUp = err.compareTo(errUp);
			int err_comp_errDown = err.compareTo(errDown);

			return errUp.signum() == 1 && errDown.signum() == 1 &&

					err_comp_errUp <= 0 && err_comp_errDown <= 0 &&

					((err_comp_errUp == 0) ? err_comp_errDown < 0 : true)
					&& ((err_comp_errDown == 0) ? err_comp_errUp < 0 : true);
		// && could check for digit conditions for ties too

		default: // Definition of UNNECESSARY already verified.
			return true;
		}
	}

	private static boolean squareRootZeroResultAssertions(BigDecimal x) {
		return x.compareTo(BigDecimal.ZERO) == 0;
	}

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 18:12:19--------------------------------------------
	 */
	/**
	 * Returns the first value raised to the power of the second value rounded to
	 * the context setting.
	 * 
	 * @param x the base.
	 * @param p the exponent or degree.
	 * @param c the rounding settings used to round the final result.
	 * @return <code>x<sup>p</sup></code> rounded to the provided context settings.
	 *         i.e <code>exp<sup>plog(x)</sup></code>
	 */
	public static BigDecimal pow(BigDecimal x, BigDecimal p, MathContext c) {
		if (Utility.isInteger(p)) {
			try {
				return new BigDecimal(ApfloatMath.pow(asApfloat(x, c.getPrecision()), p.longValueExact()).toString());
			} catch (ArithmeticException e) {
				e.printStackTrace();
			}
		}
		return new BigDecimal(
				ApfloatMath.pow(asApfloat(x, c.getPrecision()), asApfloat(p, c.getPrecision())).toString());
	}

	public static BigDecimal root(BigDecimal x, long index, int scale) {
		return new BigDecimal(ApfloatMath.root(asApfloat(x, scale), index).toString());
	}

	/////////////////////////////////////////////////////////////////////
	////////////////// Miscellaneous function methods //////////////////
	///////////////////////////////////////////////////////////////////

	public static BigDecimal factorial(BigDecimal n) {
		if (Utility.isInteger(n)) {
			if (n.signum() == 0)
				return BigDecimal.ONE;
			BigDecimal recurse = factorial(n.abs().subtract(BigDecimal.ONE));

			return n.multiply(recurse);
		}
		String x = ApfloatMath.gamma(new Apfloat(n.add(BigDecimal.ONE))).toString();
		return new BigDecimal(x);
	}

	public static BigDecimal factorial(BigDecimal n, MathContext c) {
//		c = Utility.requireNonNullElse(c, Constants.DEFAULT_ROUND);
		if (Utility.isInteger(n)) {
			if (n.signum() == 0)
				return BigDecimal.ONE;
			BigDecimal recurse = factorial(n.abs().subtract(BigDecimal.ONE, c));

			return n.multiply(recurse).round(c);
		}
		String x = ApfloatMath.gamma(new Apfloat(n.add(BigDecimal.ONE), c.getPrecision())).toString();
		return new BigDecimal(x);
	}

	public static BigDecimal gamma(BigDecimal n, int precision) {
		return new BigDecimal(ApfloatMath.gamma(asApfloat(n, precision)).toString());
	}

	/*
	 * Date: 31 Jul 2020-----------------------------------------------------------
	 * Time created: 16:34:05--------------------------------------------
	 */
	/**
	 * <p>
	 * Calculates <b><i>B</i></b><sub>n</sub> and returns a {@code BigDecimal}.
	 * </p>
	 * <p>
	 * This is an unoptimised algorithm that calculates a bernoulli number and
	 * returns the answer as a rational number. This algorithm calculates
	 * <b><i>B</i></b><sub>1</sub>= 1;
	 * </p>
	 * 
	 * @param n the index of the bernoulli number to be calculated.
	 * @return a {@code BigDecimal} representing the bernouli number.
	 * @throws ArithmeticException if n is less than 0
	 */
	public static BigDecimal computeBernoulliNumber(int n, MathContext c) {
		if (n < 0)
			throw new ArithmeticException("Parameter out of range");
		else if (n == 0)
			return BigDecimal.ONE;
		else if (n == 1)
			return new BigDecimal("0.5");
		else if (n > 1 && n % 2 != 0)
			return BigDecimal.ZERO;
		final BigDecimal[] b = new BigDecimal[n + 1];
		for (int m = 0; m <= n; m++) {
			b[m] = BigDecimal.ONE.divide(new BigDecimal(m + 1), c);
			for (int j = m; j >= 1; j--) {
				b[j - 1] = b[j - 1].subtract(b[j], c).multiply(new BigDecimal(j), c);
			}
		}
		return b[0];
	}

	static BigDecimal getBern(int n) {
		if (n == 1)
			return Arith.sqrt(new BigDecimal(0.25), Constants.DEFAULT_ROUND);
		try {
			return n % 2 == 0 ? Constants.BERNOULLI[n / 2] : Constants.ZERO;
		} catch (@SuppressWarnings("unused") IndexOutOfBoundsException e) {
		}
		return computeBernoulliNumber(n, Constants.DEFAULT_ROUND);
	}

	public static boolean isWithinJava(BigDecimal x) {
		Double minD = Double.MIN_VALUE;
		String minString = minD.toString();
		BigDecimal javaMinDouble = new BigDecimal(minString);
		Double maxD = Double.MAX_VALUE;
		String maxString = maxD.toString();
		BigDecimal javaMaxDouble = new BigDecimal(maxString);
		boolean isWithinJava = x.abs().compareTo(javaMinDouble) >= 0;
		isWithinJava = isWithinJava && x.abs().compareTo(javaMaxDouble) <= 0;
		return isWithinJava;
	}

	public static BigDecimal mod(BigDecimal n, BigDecimal mod) {
		BigInteger a;
		BigInteger b;
		try {
			a = n.toBigIntegerExact();
		} catch (@SuppressWarnings("unused") ArithmeticException e) {
			a = null;
		}
		try {
			b = mod.toBigIntegerExact();
		} catch (@SuppressWarnings("unused") ArithmeticException e) {
			b = null;
		}
		if (a == null) {
			BigFraction br = new BigFraction(n);
			BigInteger numerator = br.getNumerator();
			BigInteger denominator = br.getDenominator();
			if (b != null) {
				if (numerator.compareTo(b) < 0)
					numerator = numerator.add(b);
				BigInteger num = numerator.divideAndRemainder(denominator)[1];
				if (num.compareTo(BigInteger.ZERO) == 0) {
					numerator = numerator.divide(denominator);
					numerator = numerator.mod(b);
					return new BigDecimal(numerator, Constants.DEFAULT_ROUND);
				} /* else */
				return n;
			}
		}
		if (b != null)
			return new BigDecimal(a.mod(b));
		BigDecimal num = n.divideAndRemainder(mod, Constants.DEFAULT_ROUND)[1];
		return num;
//		}
	}

	/*
	 * Date: 31 Aug 2020-----------------------------------------------------------
	 * Time created: 18:20:07--------------------------------------------
	 */

	/**
	 * Returns the largest (closest to positive infinity) {@code BigDecimal} value
	 * that is less than or equal to the argument and is equal to a mathematical
	 * integer.
	 * 
	 * @param x a {@code BigDecimal} fractional value.
	 * @return the largest (closest to positive infinity) floating-point value that
	 *         less than or equal to the argument and is equal to a mathematical
	 *         integer.
	 */
	public static BigDecimal floor(BigDecimal x) {
		try {
			return new BigDecimal(x.toBigIntegerExact());
		} catch (@SuppressWarnings("unused") ArithmeticException e) {
			return new BigDecimal(x.setScale(0, RoundingMode.FLOOR).toBigIntegerExact());
		}
	}

	/*
	 * Date: 31 Aug 2020-----------------------------------------------------------
	 * Time created: 18:18:08--------------------------------------------
	 */
	/**
	 * Returns the smallest (closest to negative infinity) {@code BigDecimal} value
	 * that is greater than or equal to the argument and is equal to a mathematical
	 * integer.
	 * 
	 * @param x a {@code BigDecimal} fractional value.
	 * @return the smallest (closest to negative infinity) fractional value that is
	 *         greater than or equal to the argument and is equal to a mathematical
	 *         integer.
	 */
	public static BigDecimal ceil(BigDecimal x) {
		try {
			return new BigDecimal(x.toBigIntegerExact());
		} catch (@SuppressWarnings("unused") ArithmeticException e) {
			return new BigDecimal(x.setScale(0, RoundingMode.CEILING).toBigIntegerExact());
		}
	}

	public static BigDecimal copySign(BigDecimal mag, BigDecimal sign) {
		return mag.multiply(new BigDecimal(sign.signum()));
	}

	/*
	 * Date: 17 Sep 2020-----------------------------------------------------------
	 * Time created: 14:43:55--------------------------------------------
	 */

	/**
	 * Returns an array of two BigIntegers containing the integer root {@code rt} of
	 * {@code this} and its remainder {@code x - rt.pow(radicand)}, respectively.
	 *
	 * @param x        the value whose root is to be found
	 * @param radicand the radicand i.e 2 for square root, 3 for cube root, 4 for
	 *                 quad root and so on...
	 * @param c        the context setting
	 * @return an array of two BigIntegers with the integer root at offset 0 and the
	 *         remainder at offset 1
	 * @throws ArithmeticException if {@code x} is negative. (The root of a negative
	 *                             integer)
	 */
	public static BigInteger[] rootAndRemainder(BigInteger x, int radicand) {
		return rootAndIdentity(x, radicand, true);
	}

	public static BigInteger[] rootAndFactor(BigInteger x, int radicand) {
		return rootAndIdentity(x, radicand, false);
	}

	private static BigInteger[] rootAndIdentity(final BigInteger x, int radicand, boolean isAdditive) {
		if (radicand <= 0)
			throw new ArithmeticException("Zero radicand not computable");
		else if (radicand == 1)
			return new BigInteger[] { BigInteger.ONE, x };
		else if (radicand == 2) {
			if (isAdditive)
				return x.sqrtAndRemainder();
			try {
				BigInteger big = trySqrt(x);
				return new BigInteger[] { big, BigInteger.ONE };
			} catch (@SuppressWarnings("unused") ArithmeticException e) {
			}
			for (int i = 2; i <= x.intValueExact(); i++) {
				try {
					BigInteger y = tryDivide(x, BigInteger.valueOf(i));
					BigInteger x1 = trySqrt(y);
					y = x1.pow(radicand);
					y = x.divide(y);
					return new BigInteger[] { x1, y };
				} catch (@SuppressWarnings("unused") ArithmeticException e) {
				}
			}
			return new BigInteger[] { BigInteger.ONE, x };
		}
		if (isAdditive) {
			MathContext c = MathContext.DECIMAL32;
			BigDecimal radica = BigDecimal.ONE.divide(new BigDecimal(radicand), Constants.DEFAULT_ROUND);
			BigInteger rt, remainder;
			rt = pow(new BigDecimal(x), radica, c).round(c).toBigInteger();

			remainder = x.subtract(rt.pow(radicand));

			return new BigInteger[] { rt, remainder };
		}

		MathContext c = MathContext.DECIMAL32;
		try {
			BigInteger big = tryRoot(x, radicand, c);
			return new BigInteger[] { big, BigInteger.ONE };
		} catch (@SuppressWarnings("unused") ArithmeticException e) {
		}

		for (int i = 2; i <= x.intValueExact(); i++) {
			try {
				BigInteger y = tryDivide(x, BigInteger.valueOf(i));
				BigInteger x1 = tryRoot(y, radicand, c);
				y = x1.pow(radicand);
				y = x.divide(y);
				return new BigInteger[] { x1, y };
			} catch (@SuppressWarnings("unused") ArithmeticException e) {
			}
		}

		return new BigInteger[] { BigInteger.ONE, x };
	}

	private static BigInteger tryDivide(BigInteger dividend, BigInteger divisor) {
		BigInteger[] b = dividend.divideAndRemainder(divisor);
		if (b[1].signum() != 0)
			throw new ArithmeticException("Unable to divide");
		return b[0];
	}

	private static BigInteger trySqrt(BigInteger x) {
		BigInteger[] b = x.sqrtAndRemainder();
		if (b[1].signum() != 0)
			throw new ArithmeticException("Unable to root");
		return b[0];
	}

	private static BigInteger tryRoot(BigInteger x, int r, MathContext c) {
		return pow(new BigDecimal(x), BigDecimal.ONE.divide(new BigDecimal(r), c), c).toBigIntegerExact();
	}

}
