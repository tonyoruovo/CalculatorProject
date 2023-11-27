/**
 * TODO: Some values causes infinite loop:
 * BigInteger in = new BigInteger(63, 200, new Random());
 * BigInteger id = new BigInteger(63, 3000, new Random());
 * BigFraction f = new BigFraction(in, id);
 * out.println(f.getRecurringDigits());
 * 
 * The above causes an infinite loop because the numerator
 * and denominator is a huge prime number. The quickest
 * solution I can think of is to use a factorisation
 * algorithm to decompose the fraction
 */
package mathaid.calculator.base.value;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import mathaid.ExceptionMessage;
import mathaid.NullException;
import mathaid.calculator.base.Calculator;
import mathaid.calculator.base.MathematicalException;
import mathaid.calculator.base.util.Arith;
import mathaid.calculator.base.util.Constants;
import mathaid.calculator.base.util.Utility;
import mathaid.spi.LoopConfiguration;

/*
 * Date: 31 Aug 2020----------------------------------------------------------- 
 * Time created: 20:52:55---------------------------------------------------  
 * Package: mathaid.calculator.base.value------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: BigFraction.java------------------------------------------------------ 
 * Class name: BigFraction------------------------------------------------ 
 */
/**
 * <p>
 * Immutable arbitrary representation of a rational number. All fractions
 * created by this object are in non-mixed form.
 * </p>
 * <p>
 * {@code BigFractions} are immutable, this means they can be used as constants
 * fields, keys in maps and work well within concurrent and/or distributed
 * programs.
 * </p>
 * <p>
 * BigFractions support 2 types of fractions namely: irrational and
 * rational.<br>
 * Irrational fractions can be constructed via the following constructors:
 * <ul>
 * <li>{@link #BigFraction(String)}</li>
 * <li>{@link #BigFraction(BigDecimal)}</li>
 * <li>{@link #BigFraction(BigDecimal, BigDecimal)}</li>
 * <li>{@link #BigFraction(BigDecimal, BigInteger, BigDecimal)}</li>
 * <li>{@link #BigFraction(BigDecimal, MathContext, BigInteger, BigDecimal)}</li>
 * <li>{@link #valueOf(java.lang.Number)} using floating point values</li>
 * </ul>
 * Rational fractions can be constructed via the following constructors:
 * <ul>
 * <li>{@link #BigFraction(String)} using the
 * <code>new BigFraction("1/2")</code> syntax</li>
 * <li>{@link #BigFraction(String, String)}</li>
 * <li>{@link #BigFraction(BigInteger, BigInteger)}</li>
 * <li>{@link #valueOf(java.lang.Number)} using integer values</li>
 * <li>{@link #fromRecurringDecimal(char[], int, int)}</li>
 * </ul>
 * </p>
 * <p>
 * {@code BigFraction} uses non-mixed fractions internally, but the mixed
 * fractions can be obtained via {@link #toMixed()} which will return an array
 * of the whole part, the numerator and the denominator respectively.
 * Additionally, {@code BigFractions} does not only contains arithmetic methods
 * such as {@link #add(BigFraction)} and {@link #subtract(BigFraction)}, but
 * also {@link #getReciprocal()}, {@link #percent()} and
 * {@link #lcm(BigFraction)}, to help users with quick access to these optimised
 * operations.
 * </p>
 * <p>
 * In line with the purposes of this class, methods such as getting the
 * {@link #toContinuedFraction() continued fraction of a rational number},
 * getting the {@link #getRecurring() recurring digits of a rational number} as
 * well as it's {@link #getPeriod() period} (number of digits in the recurring
 * part) and methods to detect if this representation of a number is accurate
 * using the {@link #isRational()} method help make this class an extremely
 * useful part of this api.
 * </p>
 * <p>
 * Some constructors of this class (which creates a fraction from a decimal
 * expansion) uses a {@code BigDecimal} object to specify the accuracy of
 * conversion from decimals to common fractions. The accuracy is a non-negative,
 * non-zero value less than 1. This property (which can be retrieved via
 * {@link #getAccuracy()}) will determine the precision of the conversion.
 * Because some numbers may be repeating or not have a recurring pattern (as in
 * {@link Constants#PI}), their representations may not be exact, but the
 * precision thereof can be made to be close to the exact representation by
 * decreasing the value specified as accuracy. If a certain representation wants
 * to increase or decrease it's accuracy, it can be done via the
 * {@code setAccuracy(BigDecimal)}, this may change the numerator and
 * denominator because it causes the fraction to re-evaluate the current decimal
 * if the input argument is a new value i.e different from the old value. When
 * calling the methods that take {@code BigFraction} as parameter, it is
 * important to note that if the accuracy is the same as the first operand (the
 * first operand being the object from which the operation is called) then there
 * is a lesser performance penalty than when the input argument is a fraction of
 * a different accuracy. For example:
 * 
 * <pre>
 * <code>
 * BigFraction f1 = BigFraction.valueOf(23.749)&semi; // default accuracy of 1E-10
 * BigFraction f2 = new BigFraction(new BigDecimal("0.5"), MathContext.DECIMAL128, null, new BigDecimal("1.0e-7"))&semi;
 * BigFraction ans = f1.add(f2)&semi; // uses decimal addition which is more costly
 * </code>
 * </pre>
 * 
 * An alternative approach is to use a method which is sure to return a
 * consistent accuracy. <blockquote>
 * 
 * <pre><code>
 * BigFraction f1 = BigFraction.valueOf(23.479)&semi; // default accuracy of 1E-10
 * BigFraction f2 = BigFraction.valueOf(0.5)&semi; // default accuracy of 1E-10
 * &#x002f;&#x002a;
 *  * uses natural arithmetic evaluation of numerators and denominators which is
 *  * less costly because both BigFractions are of same accuracy {@literal }
 * &#x002a;&#x002f;
 * BigFraction ans = f1.add(f2)&semi;
 * </code></pre>
 * 
 * </blockquote>When an operation involving 2 {@code BigFraction} that returns a
 * {@code BigFraction} is called, the resulting object will inherit the minimum
 * value of the 2 evaluated {@code BigFraction} i.e the returned
 * {@code BigFraction} will have an accuracy of
 * {@code operand1.getAccuracy().min(operand2.getAccuracy())}. Constructors that
 * take a {@code BigDecimal} can specify a maxDenominator, and when this max is
 * breached, then an {@code ArithmeticException} will be thrown. Every operation
 * that represents an arithmetic operation that takes a {@code BigFraction} as
 * its argument has a corresponding operation that also takes a {@code Number}
 * as its argument, this is simply for convenience. For the details on how both
 * methods differ, refer to their documentation.
 * </p>
 * <p>
 * Because of the nature of constructors that accept a {@code BigDecimal} as the
 * fraction, the method {@link #isRational()} exists to check if the conversion
 * was precise. This is analogous to truncating a real number in order to use it
 * as a rational number. For example: pi = 3.141... may be truncated to 3.142
 * either by a specified {@code MathContext} object, or the given accuracy.
 * Hence one can check if a {@code BigFraction} is a rational number or if it is
 * just an approximation of a real number.
 * </p>
 * <p>
 * All {@code BigFraction} that represents a whole number have a denominator ==
 * 1. The zero fraction has a numerator which is equal to 0 and a denominator of
 * 1. All fractions created by this object are in non-mixed form.
 * </p>
 * <p>
 * BigFraction implements {@link Comparable}, this means that they can be
 * compared for numerical greatness or equality. The method provided by the
 * {@code Comparable} should be used in preference to the
 * {@link #equals(Object)} method because the former is for numerical comparison
 * but the latter is for object-oriented comparison. The operation of the
 * {@code compareTo(BigFraction)} method does not rely on decimal expansion but
 * on how it is represented in the numerator/denominator ratio. In other words a
 * comparison between 2 {@code BigFraction}'s decimal expansion may not
 * necessarily return the results as a direct comparison between them as common
 * fractions.
 * </p>
 * 
 * @see Comparable
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class BigFraction implements Comparable<BigFraction>, java.io.Serializable {

	/*
	 * Date: 23 Jul 2021-----------------------------------------------------------
	 * Time created: 16:39:27---------------------------------------------------
	 */
	/**
	 * Field for serial
	 */
	private static final long serialVersionUID = 1L;
	/* private constant declarations */
	/**
	 * Default accuracy to ensure accurate convergence of decimals. This will be
	 * adequate for most initialisations.
	 */
	/* 1.0e-10 is the optimal value for both algorithms */
	private static final BigDecimal DEFAULT_ACCURACY = new BigDecimal("1E-10");
	/**
	 * Default context to truncate decimals to about 50 digits (mantissa or
	 * integer). This will be adequate for most constructors.
	 */
	private static final MathContext DEFAULT_CONTEXT = new MathContext(50, RoundingMode.HALF_EVEN);
	/**
	 * A {@code BigInteger} constant of {@code 659,706,976,671}. This is used by
	 * constructors of this class to initialise a default max denominator.
	 */
	private static final BigInteger DEFAULT_MAX_DENOM = new BigInteger("999999999F", 16);

	/* public constant declarations */
	/**
	 * Constant for the zero fraction. It consist of a 0 numerator and a denominator
	 * which is equal to 1.
	 */
	public static final BigFraction ZERO = new BigFraction(BigInteger.ZERO, BigInteger.ONE);
	/**
	 * Constant for the whole number 1. It consist of a numerator which is == 1 and
	 * a denominator which is also == 1.
	 */
	public static final BigFraction ONE = new BigFraction(BigInteger.ONE, BigInteger.ONE);
	/**
	 * Constant for the whole number 2. It consist of a numerator which is equal to
	 * 2 and a denominator which is equal to 1.
	 */
	public static final BigFraction TWO = new BigFraction(BigInteger.TWO, BigInteger.ONE);
	/**
	 * Constant for the whole number 10. It consist of a numerator which is equal to
	 * 2 and a denominator which is equal to 1.
	 */
	public static final BigFraction TEN = new BigFraction(BigInteger.TEN, BigInteger.ONE);
	/**
	 * Constant for the fraction 0.5. It consist of a numerator which is equal to 1
	 * and a denominator which is equal to 2.
	 */
	public static final BigFraction HALF = new BigFraction(BigInteger.ONE, BigInteger.TWO);
	/**
	 * Constant for the fraction 0.3... It consist of a numerator which is equal to
	 * 1 and a denominator which is equal to 3.
	 */
	public static final BigFraction ONE_THIRD = new BigFraction(BigInteger.ONE, BigInteger.valueOf(3));
	/**
	 * Constant for the fraction 0.25. It consist of a numerator which is equal to 1
	 * and a denominator which is equal to 4.
	 */
	public static final BigFraction QUARTER = new BigFraction(BigInteger.ONE, BigInteger.valueOf(4));
	/**
	 * Constant for the fraction 0.2. It consist of a numerator which is equal to 1
	 * and a denominator which is equal to 5.
	 */
	public static final BigFraction ONE_FIFTH = new BigFraction(BigInteger.ONE, BigInteger.valueOf(5));
	/**
	 * Constant for the fraction 0.6... It consist of a numerator which is equal to
	 * 1 and a denominator which is equal to 6.
	 */
	public static final BigFraction ONE_SIXTH = new BigFraction(BigInteger.ONE, BigInteger.valueOf(6));
	/**
	 * Constant for the fraction 0.142857... It consist of a numerator which is
	 * equal to 1 and a denominator which is equal to 7.
	 */
	public static final BigFraction ONE_SEVENTH = new BigFraction(BigInteger.ONE, BigInteger.valueOf(7));
	/**
	 * Constant for the fraction 0.125. It consist of a numerator which is equal to
	 * 1 and a denominator which is equal to 8.
	 */
	public static final BigFraction ONE_EIGTH = new BigFraction(BigInteger.ONE, BigInteger.valueOf(8));
	/**
	 * Constant for the fraction 0.1... It consist of a numerator which is equal to
	 * 1 and a denominator which is equal to 9.
	 */
	public static final BigFraction ONE_NINTH = new BigFraction(BigInteger.ONE, BigInteger.valueOf(9));
	/**
	 * Constant for the fraction 0.1 (i.e one-tenth). It consist of a numerator
	 * which is equal to 1 and a denominator which is equal to 10.
	 */
	public static final BigFraction TITHE = new BigFraction(BigInteger.ONE, BigInteger.valueOf(10));
	/**
	 * Constant for the fraction {@code 25 / 169} whose period is 78 digits and
	 * decimal expansion is:
	 * {@code 0.147928994082840236686390532544378698224852071005917159763313609467455621301775...}.
	 * <p>
	 * This is used for various test cases in this API.
	 * </p>
	 */
	public static final BigFraction N_25_169 = new BigFraction("25", "169");

	////////////////////////////////////////////////////////////
	///////////////////// Inner classes ///////////////////////
	//////////////////////////////////////////////////////////
	/*
	 * Date: 20 May 2021-----------------------------------------------------------
	 * Time created: 09:49:34---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base.value------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * BigFraction.java------------------------------------------------------ Class
	 * name: Helper------------------------------------------------
	 */
	/**
	 * Independent (static) inner class that helps with caching the result of the
	 * resulting computation of {@link BigFraction#isRational()} and
	 * {@link BigFraction#getRecurring()}, so as to prevent computation on
	 * subsequent calls of the former and latter. The initial computation is only
	 * done when said methods are called, and not when the {@code BigFraction} is
	 * initialised. This is important because if the methods in question are not
	 * needed, then the computation is never done.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @see BigFraction#helper
	 * @implNote Any advantages provided by this class to a <code>BigFraction</code>
	 *           has no effect except the above methods are called on the same
	 *           instance of a {@code BigFraction} more than once.
	 */
	private static class Helper implements java.io.Serializable {
		/*
		 * Date: 27 May 2022-----------------------------------------------------------
		 * Time created: 17:05:20---------------------------------------------------
		 */
		/**
		 * Field for
		 */
		private static final long serialVersionUID = 1L;

		/*
		 * Date: 20 May 2021-----------------------------------------------------------
		 * Time created: 10:01:38---------------------------------------------------
		 */
		/**
		 * Instantiates a {@code Helper} object. Parameters are mutually exclusive and
		 * are used to instantiate a {@code BigFraction} based on the given arguments.
		 * For example, based on the arguments,
		 * {@link BigFraction#BigFraction(BigDecimal, MathContext, BigInteger, BigDecimal)}
		 * may be instantiated for irrational values or
		 * {@link BigFraction#BigFraction(BigInteger, BigInteger)} for rational values.
		 * 
		 * @param num      the numerator of the corresponding {@code BigFraction}. If
		 *                 this {@code Helper} is instantiated by this constructor
		 *                 inside the
		 *                 {@link BigFraction#BigFraction(BigDecimal, MathContext, BigInteger, BigDecimal)
		 *                 irrational} constructor (or it's aliases), then this
		 *                 parameter can be left as {@code null}
		 * @param denom    the denominator of the corresponding {@code BigFraction}. If
		 *                 this {@code Helper} is instantiated by this constructor
		 *                 inside the
		 *                 {@link BigFraction#BigFraction(BigDecimal, MathContext, BigInteger, BigDecimal)
		 *                 irrational} constructor (or it's aliases), then this
		 *                 parameter can be left as {@code null}
		 * @param fraction the fraction of the corresponding {@code BigFraction}. Please
		 *                 refer to
		 *                 {@link BigFraction#BigFraction(BigDecimal, MathContext, BigInteger, BigDecimal)
		 *                 this} documentation for more details.
		 *                 <p>
		 *                 If this {@code Helper} is instantiated by this constructor
		 *                 inside the
		 *                 {@link BigFraction#BigFraction(BigInteger, BigInteger)
		 *                 rational} constructor (or it's aliases), then this parameter
		 *                 can be left as {@code null}
		 *                 </p>
		 * @param max      the maximum value for the denominator provided by the decimal
		 *                 fraction constructor. Please refer to
		 *                 {@link BigFraction#BigFraction(BigDecimal, MathContext, BigInteger, BigDecimal)
		 *                 this} documentation for more details.
		 *                 <p>
		 *                 If this {@code Helper} is instantiated by this constructor
		 *                 inside the
		 *                 {@link BigFraction#BigFraction(BigInteger, BigInteger)
		 *                 rational} constructor (or it's aliases), then this parameter
		 *                 can be left as {@code null}
		 *                 </p>
		 * @param acc      the accuracy of the conversion of the decimal fraction.
		 *                 Please refer to
		 *                 {@link BigFraction#BigFraction(BigDecimal, MathContext, BigInteger, BigDecimal)
		 *                 this} documentation for more details.
		 *                 <p>
		 *                 If this {@code Helper} is instantiated by this constructor
		 *                 inside the
		 *                 {@link BigFraction#BigFraction(BigInteger, BigInteger)
		 *                 rational} constructor (or it's aliases), then this parameter
		 *                 can be left as {@code null}
		 *                 </p>
		 * @param mc       the {@code MathContext} of the of the decimal fraction.
		 *                 Please refer to
		 *                 {@link BigFraction#BigFraction(BigDecimal, MathContext, BigInteger, BigDecimal)
		 *                 this} documentation for more details.
		 *                 <p>
		 *                 If this {@code Helper} is instantiated by this constructor
		 *                 inside the
		 *                 {@link BigFraction#BigFraction(BigInteger, BigInteger)
		 *                 rational} constructor (or it's aliases), then this parameter
		 *                 can be left as {@code null}
		 *                 </p>
		 */
		private Helper(BigInteger num, BigInteger denom, BigDecimal fraction, BigInteger max, BigDecimal acc,
				MathContext mc) {
			this.num = num;
			this.denom = denom;
			this.fraction = fraction;
			this.acc = acc;
			this.mc = mc;
			this.max = max;
		}

		/*
		 * Date: 20 May 2021-----------------------------------------------------------
		 * Time created: 10:25:55--------------------------------------------
		 */
		/**
		 * Instantiates a {@code Helper} for a {@code BigFraction} using the
		 * {@code BigFraction} object's rational fields
		 * 
		 * @param num   the expected numerator
		 * @param denom the expected denominator
		 * @return a valid rational {@code Helper} object
		 * @see Helper#Helper(BigInteger, BigInteger, BigDecimal, BigInteger,
		 *      BigDecimal, MathContext) constructor
		 */
		private static Helper getHelper(BigInteger num, BigInteger denom) {
			if (num == null)
				new NullException(ExceptionMessage.OBJECT_IS_NULL, new NullPointerException(), "num");
			if (denom == null)
				new NullException(ExceptionMessage.OBJECT_IS_NULL, new NullPointerException(), "denom");
			return new Helper(num, denom, null, null, null, null);
		}

		/*
		 * Date: 20 May 2021-----------------------------------------------------------
		 * Time created: 10:28:28--------------------------------------------
		 */
		/**
		 * Instantiates a {@code Helper} for a {@code BigFraction} using the
		 * {@code BigFraction} object's irrational fields
		 * 
		 * @param frac the expected decimal fraction
		 * @param acc  the expected accuracy
		 * @param max  the expected maximum denominator
		 * @param mc   the expected rounding object
		 * @return a valid {@code Helper} object which may be irrational
		 * @see Helper#Helper(BigInteger, BigInteger, BigDecimal, BigInteger,
		 *      BigDecimal, MathContext) constructor
		 */
		private static Helper getHelper(BigDecimal frac, BigDecimal acc, BigInteger max, MathContext mc) {
			if (frac == null)
				new NullException(ExceptionMessage.OBJECT_IS_NULL, new NullPointerException(), "frac");
			if (acc == null)
				new NullException(ExceptionMessage.OBJECT_IS_NULL, new NullPointerException(), "acc");
//			if (max == null)
//				new NullException(ExceptionMessage.OBJECT_IS_NULL, new NullPointerException(), "max");
			if (mc == null)
				new NullException(MathContext.class);
			return new Helper(null, null, frac, max, acc, mc);
		}

		/*
		 * Date: 20 May 2021-----------------------------------------------------------
		 * Time created: 10:35:13--------------------------------------------
		 */
		/**
		 * Parses this {@code Helper} into an appropriate {@code BigFraction}
		 * 
		 * @return a valid {@code BigFraction}
		 */
		private BigFraction toBigFraction() {
			return fraction == null ? new BigFraction(num, denom) : new BigFraction(fraction, mc, max, acc);
		}

		/*
		 * Date: 10 Sep 2022-----------------------------------------------------------
		 * Time created: 08:48:48--------------------------------------------
		 */
		/**
		 * Checks if this the argument has a prime (or semi-prime) numerator and/or
		 * denominator. This uses
		 * 
		 * <pre>
		 * <code>f.getNumerator().isProbablePrime(100000) || f.getDenominator().isProbablePrime(100000)</code>
		 * </pre>
		 * 
		 * @param f a {@code BigFraction}
		 * @return {@code true} if the argument's numerator and/or denominator is
		 *         prime/semi-prime, otherwise returns {@code false}.
		 */
		private boolean isProbablyPrime(BigFraction f) {
			if (f.numerator.compareTo(new BigInteger("9087263541")) < 0
					|| f.denominator.compareTo(new BigInteger("9087263541")) < 0)
				return false;
			final int certainty = 100_000;
			return f.numerator.isProbablePrime(certainty) || f.denominator.isProbablePrime(certainty);
		}

		/*
		 * Date: 20 May 2021-----------------------------------------------------------
		 * Time created: 10:36:49--------------------------------------------
		 */
		/**
		 * Returns the recurring values of this {@code Helper} by either calculating and
		 * returning a fresh value or returning a cached one.
		 * 
		 * @return an array of non-negative {@code BigDecimal} in the format specified
		 *         {@link BigFraction#getRecurring() here}.
		 */
		private BigDecimal[] getRecurring() {
			if (recurringElements != null)
				return recurringElements;
			BigFraction bf = toBigFraction();
			if ((!bf.isRational()) || isProbablyPrime(bf))
				return (recurringElements = new BigDecimal[] { bf.decimalFraction, BigDecimal.ZERO });
			BigInteger[] bi = bf.toMixed();
			String s = divide(bi[1].abs(), bi[2], true);
			/*
			 * for example, 0.03(0456789) would cause the commented code to be incorrect. as
			 * when the former recurring algorithm is used: period = 6 (whereby the correct
			 * value is 7) because the second index of the bigDecimal array will return only
			 * an integer hence the initial zero will not be counted, causing an inaccurate
			 * result.
			 */
//			return (recurringElements = new BigDecimal[] { new BigDecimal(s.substring(0, s.indexOf("R")))
//					.add(new BigDecimal(bi[0].abs())).multiply(new BigDecimal(bf.signum())),
//					new BigDecimal(s.substring(s.indexOf("R") + 1)) });
			BigDecimal non_recur = new BigDecimal(s.substring(0, s.indexOf("R")));
			String recur = s.substring(s.indexOf("R") + 1);
			BigDecimal x = new BigDecimal(bi[0].abs());
			int scale = Utility.isInteger(non_recur) ? 0 : Utility.numOfFractionalDigits(non_recur);
//			System.err.println("Scale: " + bi[0]);
			return (recurringElements = new BigDecimal[] { non_recur.add(x).multiply(new BigDecimal(bf.signum())),
					new BigDecimal("0." + Utility.string('0', scale) + recur) });
//			return recurringElements;
		}

		/*
		 * Date: 20 May 2021-----------------------------------------------------------
		 * Time created: 10:44:43--------------------------------------------
		 */
		/**
		 * Returns a {@code boolean} value of this {@code Helper} by either calculating
		 * and returning a fresh value or returning a cached one.
		 * 
		 * @return {@link BigFraction#isRational()}
		 */
		private boolean isRational() {
			if (isRational != null)
				return isRational;
			BigFraction bf = toBigFraction();
//			if (bf.rationalConstructor)
//				return (isRational = true);
			final BigDecimal ac;
			if (bf.accuracy.compareTo(new BigDecimal("1.E-10")) == 0)
				ac = new BigDecimal("1.E-15");
			else
				ac = new BigDecimal("1.E-10");
			BigFraction tmp = new BigFraction(bf.decimalFraction, Helper.this.mc, null, ac);
			return (isRational = bf.toLowestTerms().compareTo(tmp.toLowestTerms()) == 0);
		}

		/**
		 * The numerator, which is <code>null</code> if the enclosing
		 * {@code BigFraction} is instantiated using the
		 * {@code BigFraction#BigFraction(BigDecimal, BigDecimal) irrational}
		 * constructor or any of its aliases
		 */
		private final BigInteger num;
		/**
		 * The denominator, which is <code>null</code> if the enclosing
		 * {@code BigFraction} is instantiated using the
		 * {@code BigFraction#BigFraction(BigDecimal, BigDecimal) irrational}
		 * constructor or any of its aliases
		 */
		private final BigInteger denom;

		/**
		 * The max denominator, which is <code>null</code> if the enclosing
		 * {@code BigFraction} is instantiated using the
		 * {@code BigFraction#BigFraction(BigInteger, BigInteger) rational} constructor
		 * or any of its aliases
		 */
		private final BigInteger max;
		/**
		 * The fraction, which is <code>null</code> if the enclosing {@code BigFraction}
		 * is instantiated using the
		 * {@code BigFraction#BigFraction(BigInteger, BigInteger) rational} constructor
		 * or any of its aliases
		 */
		private final BigDecimal fraction;
		/**
		 * The accuracy, which is <code>null</code> if the enclosing {@code BigFraction}
		 * is instantiated using the
		 * {@code BigFraction#BigFraction(BigInteger, BigInteger) rational} constructor
		 * or any of its aliases
		 */
		private final BigDecimal acc;
		/**
		 * The rounding context, which is <code>null</code> if the enclosing
		 * {@code BigFraction} is instantiated using the
		 * {@code BigFraction#BigFraction(BigInteger, BigInteger) rational} constructor
		 * or any of its aliases
		 */
		private final MathContext mc;
		/**
		 * The cache for {@link Helper#isRational()}
		 */
		private Boolean isRational;// Effectively final, cause instantiated only once
		/**
		 * The cache for {@link Helper#getRecurring()}
		 */
		private BigDecimal[] recurringElements;// Effectively final
	}

	////////////////////////////////////////////////////////////
	///////////////////// Constructors ////////////////////////
	//////////////////////////////////////////////////////////

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 4:42:43 PM ---------------------------------------------------
	 */
	/**
	 * Attempts to create an irrational fraction if {@code isRational} argument is
	 * {@code false} otherwise attempts to create a rational fraction. Both attempts
	 * may end up with unintended results, i.e an attempt to create a rational
	 * fraction may end up creating an irrational fraction and vice-versa
	 * 
	 * @param real       a {@code BigDecimal} value representing the fraction
	 * @param isRational true if irrational is intended false if otherwise
	 * 
	 * @implNote Both attempts may end up with unintended results, i.e an attempt to
	 *           create a rational fraction may end up creating an irrational
	 *           fraction and vice-versa
	 */
	public BigFraction(BigDecimal real, boolean isRational) {// a 'true' value does not guarantee a rational constructor
		this.decimalFraction = real;
		MathContext mc = new MathContext(Utility.numOfFractionalDigits(real) + Utility.numOfIntegerDigits(real),
				RoundingMode.DOWN);
		BigFraction f = new BigFraction(real, mc, null, DEFAULT_ACCURACY);
		numerator = f.numerator;
		denominator = f.denominator;
		rationalConstructor = isRational ? f.rationalConstructor : false;
		context = mc;
		accuracy = f.accuracy;
		helper = rationalConstructor ? Helper.getHelper(numerator, denominator)
				: Helper.getHelper(decimalFraction, accuracy, f.helper.max, mc);
	}

	/*
	 * Date: 1 Oct 2020-----------------------------------------------------------
	 * Time created: 06:20:11---------------------------------------------------
	 */
	/**
	 * Constructs a {@code BigFraction} using a string decimal fraction. The
	 * {@code String} argument must either be in a format that is acceptable to the
	 * {@code BigDecimal} constructor, or [numerator]/[denominator], else a
	 * {@code NumberFormatException} will be thrown. Please see the
	 * {@link BigDecimal#BigDecimal(String) BigDecimal} documentation for details.
	 * 
	 * @param fraction the fraction (in string) to be converted
	 * @see #BigFraction(BigDecimal) when decimal fraction form is used
	 * @see #BigFraction(String, String) when the form a / b is used
	 */
	public BigFraction(final String fraction) {
		if (fraction == null)
			new NullException(ExceptionMessage.OBJECT_IS_NULL, new NullPointerException(), "fraction");
		String ff = fraction.replaceAll(" ", "");
		boolean hasDiv = ff.contains("/");
		final BigFraction f;
		if (hasDiv) {
			rationalConstructor = true;
			f = new BigFraction(ff.substring(0, ff.indexOf("/")), ff.substring(ff.indexOf("/") + 1));
		} else {
			rationalConstructor = false;
			f = new BigFraction(new BigDecimal(ff));
		}
		numerator = f.numerator;
		denominator = f.denominator;
		decimalFraction = f.decimalFraction;
		accuracy = f.accuracy;
		context = f.context;
		helper = Helper.getHelper(decimalFraction, accuracy, DEFAULT_MAX_DENOM, context);
	}

	/*
	 * Date: 1 Sep 2020-----------------------------------------------------------
	 * Time created: 11:20:31---------------------------------------------------
	 */
	/**
	 * Initialises a {@code BigFraction} object by parsing the provided fraction to
	 * a rational number using a precision of {@code 1e-10}, along with other
	 * defaults used by {@linkplain #BigFraction(BigDecimal, BigInteger, BigDecimal)
	 * this constructor}.
	 * 
	 * @param decimalFraction the value (a fraction) to be parsed.
	 * @throws ArithmeticException if the convergence fails to return a denominator
	 *                             less than or equal to {@code 0x999999999f}.
	 */
	public BigFraction(final BigDecimal decimalFraction) throws ArithmeticException {
		this(decimalFraction, DEFAULT_ACCURACY);
	}

	/*
	 * Date: 1 Sep 2020-----------------------------------------------------------
	 * Time created: 11:20:26---------------------------------------------------
	 */
	/**
	 * <p>
	 * Constructs a {@code BigFraction} with a given decimal fraction and an
	 * accuracy to stream line the precision of the conversion.
	 * </p>
	 * <p>
	 * This constructor will throw an {@code ArithmeticException} if the parser
	 * fails to return a fraction whose denominator is less than or equal to
	 * {@code 0x999999999f}. It also uses the same {@code MathContext} object as
	 * {@linkplain #BigFraction(BigDecimal, BigInteger, BigDecimal) this
	 * constructor}.
	 * </p>
	 * 
	 * @param decimalFraction the value (a fraction) to be parsed.
	 * @param accuracy        a value between 0 (exclusive) and 1 (exclusive) which
	 *                        determines the precision of the parsing engine.
	 * @throws ArithmeticException if the accuracy is outside the domain [0, 1] or
	 *                             if the convergence fails to return a denominator
	 *                             less than or equal to {@code 0x999999999f}.
	 * @see {@link #BigFraction(BigDecimal, BigInteger, BigDecimal)}
	 */
	public BigFraction(final BigDecimal decimalFraction, final BigDecimal accuracy) throws ArithmeticException {
		this(decimalFraction, DEFAULT_MAX_DENOM, accuracy);
	}

	/*
	 * Date: 1 Sep 2020-----------------------------------------------------------
	 * Time created: 19:05:48---------------------------------------------------
	 */
	/**
	 * <p>
	 * Constructs a {@code BigFraction} with a given decimal fraction, a max
	 * denominator and an accuracy to stream line the precision of the conversion.
	 * This uses a {@code MathContext} with a precision of about 50 digits and a
	 * {@link RoundingMode#HALF_EVEN} rounding. In other words only about the first
	 * 50 digits of the given fraction is parsed, the rest are approximated to
	 * scientific notation. For example:<code>1234567890 ...</code> to 400 digits
	 * will be parsed as <code>1.234567890 ...</code> to 50 digits the rest will be
	 * approximated using the notation E349. In this scenario, this is the number
	 * that will be parsed.
	 * </p>
	 * <p>
	 * If the parser is unable to reduce the fraction's denominator to a value less
	 * than or equal to max denominator, then an {@code ArithmeticException} will be
	 * thrown to indicate non-convergence.
	 * </p>
	 * 
	 * @param decimalFraction the value (a fraction) to be parsed.
	 * @param maxDenominator  the maximum denominator that the {@code BigFraction}
	 *                        should have, above which this constructor returns a
	 *                        non-rational fraction. A {@code null} value means no
	 *                        maximum.
	 * @param accuracy        a value between 0 (exclusive) and 1 (exclusive) which
	 *                        determines the precision of the parsing engine.
	 * @throws ArithmeticException if the given maximum denominator is exceeded or
	 *                             if the accuracy is outside the domain [0, 1].
	 */
	public BigFraction(final BigDecimal decimalFraction, final BigInteger maxDenominator, final BigDecimal accuracy)
			throws ArithmeticException {
		this(decimalFraction, DEFAULT_CONTEXT, maxDenominator, accuracy);
	}

	/*
	 * Date: 1 Sep 2020-----------------------------------------------------------
	 * Time created: 09:39:36---------------------------------------------------
	 */
	/**
	 * <p>
	 * Constructs a {@code BigFraction} from a given decimal fraction, a specified
	 * accuracy (which attempts to obtain the smallest denominator whose number of
	 * digits is equal to
	 * <code>((decimalFraction - floor(decimalFraction)) + accuracy).scale()</code>),
	 * a context (to limit recurring digits and/or non-terminating decimals such as
	 * pi) and a max denominator (to prevent overly huge values for the
	 * denominator).
	 * </p>
	 * <p>
	 * If the parser is unable to reduce the fraction's denominator to a value less
	 * than or equal to max denominator, then an irrational fraction will be created
	 * to indicate non-convergence.
	 * </p>
	 * 
	 * @param decimalFraction the value (a fraction) to be parsed.
	 * @param context         truncates the decimal fraction to prevent unscalable
	 *                        or non-terminating mantissa.
	 * @param maxDenominator  the maximum denominator that the {@code BigFraction}
	 *                        should have, above which this constructor returns a
	 *                        non-rational fraction. A {@code null} value means no
	 *                        maximum.
	 * @param accuracy        a value between 0 (exclusive) and 1 (exclusive) which
	 *                        determines the precision of the parsing engine. For a
	 *                        default, <code>1 &times; 10<sup>-10</sup></code> is a
	 *                        balanced value.
	 * @throws ArithmeticException if the given maximum denominator is exceeded or
	 *                             if the accuracy is outside the domain [0, 1].
	 * @implNote This implementation uses the
	 *           {@linkplain #sternBrocotAlgorithm2(BigDecimal, BigDecimal)
	 *           Stern-Brocot's} fast algorithm to parse decimals to integer
	 *           fractions.
	 */
	public BigFraction(BigDecimal decimalFraction, MathContext context, BigInteger maxDenominator,
			final BigDecimal accuracy) throws ArithmeticException {
		this(decimalFraction, context, maxDenominator, accuracy, 2);
	}

	/*
	 * Date: 8 Sep 2020-----------------------------------------------------------
	 * Time created: 21:20:18---------------------------------------------------
	 */
	/**
	 * Constructs a {@code BigFraction} using one of the 3 specified algorithms in
	 * this class. Note that conversion is done only when:
	 * <ul>
	 * <li>the {@code decimalFraction} argument is not an integer</li>
	 * <li>rationalising (moving the point the edge so that {@code decimalFraction}
	 * is effectively an integer and dividing that integer with 10<sup>points
	 * moved</sup>) the fraction causes the resultant denominator to be bigger than
	 * max denominator specified</li>
	 * <li>after rationalising the fraction, the resultant numerator divided by the
	 * resultant denominator produces a fraction not equal to the argument fraction
	 * </li>
	 * </ul>
	 * 
	 * @param decimalFraction the value (a fraction) to be parsed.
	 * @param context         truncates the decimal fraction to prevent unscalable
	 *                        or non-terminating mantissa. a {@code null} value
	 *                        means that {@link BigFraction#DEFAULT_CONTEXT this}
	 *                        value will be used instead.
	 * @param maxDenominator  the maximum denominator that the {@code BigFraction}
	 *                        should have, above which this constructor returns a
	 *                        non-rational fraction. A {@code null} value means no
	 *                        maximum.
	 * @param accuracy        a value between 0 (inclusive) and 1 (inclusive) which
	 *                        determines the precision of the parsing engine.
	 * @param algorithm       0 for Richards, 1 for stern-brocot and any value for
	 *                        stern-brocot2.
	 * @throws ArithmeticException if the given maximum denominator is exceeded or
	 *                             if the accuracy is outside the domain [0, 1].
	 */
	private BigFraction(BigDecimal decimalFraction, MathContext context, BigInteger maxDenominator,
			final BigDecimal accuracy, int algorithm) throws ArithmeticException {
		if (decimalFraction == null)
			new NullException(ExceptionMessage.OBJECT_IS_NULL, new NullPointerException(), "decimalFraction");
		if (accuracy == null)
			new NullException(ExceptionMessage.OBJECT_IS_NULL, new NullPointerException(), "accuracy");
		if (maxDenominator != null && maxDenominator.signum() <= 0)
			new MathematicalException(ExceptionMessage.NEGATIVE_INPUT_DETECTED, maxDenominator.abs().toString());
		this.decimalFraction = decimalFraction;
		if (context == null)
			context = getContextFromAccuracy(accuracy);

		if (!Utility.isInteger(decimalFraction)) {

			decimalFraction = decimalFraction.abs().subtract(new BigDecimal(decimalFraction.abs().toBigInteger()));

			int fd = Utility.numOfFractionalDigits(this.decimalFraction);
			BigDecimal fauxNumerator = this.decimalFraction.movePointRight(fd);
			BigDecimal fauxDenominator = BigDecimal.ONE.movePointRight(fd);

//			System.err.printf("Num:\t%1$s%2$sDenom:\t%3$s%2$s", fauxNumerator, System.lineSeparator(), fauxDenominator);

			if (maxDenominator != null && fauxDenominator.toBigInteger().compareTo(maxDenominator) <= 0
					&& fauxNumerator.divide(fauxDenominator, new MathContext(fd, RoundingMode.DOWN))
							.compareTo(this.decimalFraction) == 0) {
//				if (maxDenominator != null && fauxDenominator.toBigInteger().compareTo(maxDenominator.abs()) > 0)
//					new OverflowException(ExceptionMessage.DENOMINATOR_ABOVE_MAX);
				this.numerator = fauxNumerator.toBigIntegerExact();
				this.denominator = fauxDenominator.toBigIntegerExact();
				rationalConstructor = true;
			} else {

				final List<BigInteger> ratios;

				int numOfZerosInMantissa = Utility.numOfLeadingFractionalZeros(decimalFraction);
				decimalFraction = decimalFraction.movePointRight(numOfZerosInMantissa);

				switch (algorithm) {
				case 0:
					ratios = ianRichardsAlgorithm(decimalFraction.abs(context), accuracy);
					break;
				case 1:
					ratios = sternBrocotAlgorithm(decimalFraction.abs(context), accuracy);
					break;
				default:
					ratios = sternBrocotAlgorithm2(decimalFraction.abs(context), accuracy);
				}
				if (maxDenominator != null && ratios.get(1).compareTo(maxDenominator.abs()) > 0)
					new OverflowException(ExceptionMessage.DENOMINATOR_ABOVE_MAX);
				this.numerator = ratios.get(1).multiply(this.decimalFraction.abs().toBigInteger()).add(ratios.get(0))
						.multiply(BigInteger.valueOf(signum()));
				BigInteger scale = BigInteger.TEN.pow(numOfZerosInMantissa);
				this.denominator = ratios.get(1).multiply(scale);

				rationalConstructor = false;
			}

		} else {
			this.numerator = decimalFraction.toBigInteger();
			this.denominator = BigInteger.ONE;
			rationalConstructor = true;
		}
		this.accuracy = accuracy;
		this.context = context;
		helper = Helper.getHelper(this.decimalFraction, this.accuracy, maxDenominator, this.context);
	}

	/*
	 * Date: Jan 11, 2023
	 * ----------------------------------------------------------- Time created:
	 * 1:47:19 AM ---------------------------------------------------
	 */
	/**
	 * A mandatory constructor for this immutable type. This helps to assign fields
	 * and set both the accuracy and the {@code MathContext}.
	 * 
	 * @param bf  a {@code BigFraction} object
	 * @param acc a new accuracy value
	 * @param mc  a new {@code MathContext} value
	 */
	private BigFraction(BigFraction bf, BigDecimal acc, MathContext mc) {
		decimalFraction = bf.decimalFraction;
		accuracy = acc;
		this.rationalConstructor = bf.rationalConstructor;
		context = mc;
		numerator = bf.numerator;
		denominator = bf.denominator;
		this.helper = rationalConstructor ? Helper.getHelper(numerator, denominator)
				: Helper.getHelper(decimalFraction, acc, bf.helper.max, mc);
	}

	/*
	 * Date: 1 Oct 2020-----------------------------------------------------------
	 * Time created: 06:36:20---------------------------------------------------
	 */
	/**
	 * Constructs a {@code BigFraction} by an integer numerator as a string, and an
	 * integer denominator as a string. The argument are all assumed to be decimal
	 * integers i.e base ten, and are in a form that is supported by the
	 * {@code BigInteger} constructor. Please see the
	 * {@link BigInteger#BigInteger(String) BigInteger} class details on the decimal
	 * form of a string.
	 *
	 * @param numerator   a {@code String} that represents the numerator of this
	 *                    {@code BigFraction}
	 * @param denominator a non-zero {@code String} that represents the denominator
	 *                    of this {@code BigFraction}
	 * @throws ArithmeticException   if the specified denominator == 0
	 * @throws NumberFormatException if any of the value is not a valid decimal
	 *                               integer
	 */
	public BigFraction(String numerator, String denominator) {
		this(new BigInteger(numerator.trim()), new BigInteger(denominator.trim()));
	}

	/*
	 * Date: 1 Sep 2020-----------------------------------------------------------
	 * Time created: 09:06:40---------------------------------------------------
	 */
	/**
	 * <p>
	 * Constructs a {@code BigFraction} with a given numerator and denominator. The
	 * {@code BigFraction} constructed will always be in improper form. This
	 * constructor reduces the ratios if:
	 * <ul>
	 * <li>It is in improper form and not in reduced form such as 20 / 8.</li>
	 * <li>It is in proper form but is not in reduced form such as 4 / 8.</li>
	 * <li>Both the numerator and the denominator are equal such as 19 / 19.</li>
	 * </ul>
	 * 
	 * Additionally, if the denominator is negative, then the numerator is
	 * multiplied by it's signum and it (denominator) is regarded in terms of it's
	 * (denominator) absolute value. All integers have a denominator of exactly 1,
	 * and all fractions whereby numerator's absolute value is equal to that of the
	 * denominator have a numerator whose absolute value is 1.
	 * </p>
	 * <p>
	 * The decimal expansion of this rational number is stored as a field in this
	 * object. This expansion is retrieved directly from the decimal representation
	 * of the arguments. This constructor uses a default accuracy of 1E-50 in its
	 * calculation. But this is not guaranteed for all calculations, it may change
	 * due to some unforeseen circumstance. All values are allowed for both
	 * arguments with the exception of 0 for the denominator argument which would
	 * cause an {@code ArithmeticException} to be thrown. Note that for the zero
	 * rational (0 / 1), the {@link #getReciprocal()} method will throw this
	 * exception when invoked. This constructor does not have a max denominator,
	 * hence it supports arbitrary values for all it elements.
	 * </p>
	 * 
	 * @param numerator   a {@code BigInteger} that represents the numerator of this
	 *                    {@code BigFraction}
	 * @param denominator a non-zero {@code BigInteger} that represents the
	 *                    denominator of this {@code BigFraction}
	 * @throws ArithmeticException if the denominator == 0
	 */
	public BigFraction(BigInteger numerator, BigInteger denominator) throws ArithmeticException {
		if (numerator == null)
			new NullException(ExceptionMessage.OBJECT_IS_NULL, new NullPointerException(), "numerator");
		if (denominator == null)
			new NullException(ExceptionMessage.OBJECT_IS_NULL, new NullPointerException(), "denominator");
		if (denominator.signum() == 0)
			new MathematicalException(ExceptionMessage.DIVISION_BY_ZERO);
		if (denominator.signum() < 0) {
			numerator = numerator.negate();
			denominator = denominator.abs();
		}
//		if (numerator.abs().compareTo(denominator) > 0 && denominator.compareTo(BigInteger.ONE) != 0) {
//			BigInteger[] dr = numerator.divideAndRemainder(denominator);
//			numerator = denominator.multiply(dr[0].abs()).add(dr[1].abs());
//			numerator = numerator.multiply(BigInteger.valueOf(dr[0].signum()));
//		} else if (numerator.abs().compareTo(denominator) < 0 && numerator.signum() != 0) {
//			BigInteger[] dr = reduce(numerator, denominator);
//			numerator = dr[0];
//			denominator = dr[1];
//		} else 
		if (numerator.abs().compareTo(denominator) == 0) {
			numerator = numerator.signum() < 1 ? BigInteger.ONE.negate() : BigInteger.ONE;
			denominator = BigInteger.ONE;
		}
		this.numerator = numerator;
		this.denominator = denominator;
		this.accuracy = DEFAULT_ACCURACY;
		this.context = getContextFromAccuracy(accuracy);
		this.decimalFraction = new BigDecimal(this.numerator).divide(new BigDecimal(this.denominator), context);
		rationalConstructor = true;
		helper = Helper.getHelper(this.numerator, this.denominator);
	}

	////////////////////////////////////////////////////////////
	/////////////////////// Accessors /////////////////////////
	//////////////////////////////////////////////////////////

	/*
	 * Most Recent Date: 31 Aug 2020-----------------------------------------------
	 * Most recent time created: 20:53:12--------------------------------------
	 */
	/**
	 * Returns the numerator of the common fraction representative of this
	 * {@code BigFraction}. If this is an integer, then the integer is returned (or
	 * zero if this is 0) or else if this is in improper form then the unreduced
	 * numerator is returned.
	 * <p>
	 * This value is irrelevant if {@link BigFraction#isRational()} returns false
	 * 
	 * @return the unreduced numerator of this common fraction.
	 */
	public BigInteger getNumerator() {
		return numerator;
	}

	/*
	 * Most Recent Date: 31 Aug 2020-----------------------------------------------
	 * Most recent time created: 20:53:12--------------------------------------
	 */
	/**
	 * Returns the denominator of the common fraction representative of this
	 * {@code BigFraction}. If this is an integer, then 1 is returned.
	 * <p>
	 * This value is irrelevant if {@link BigFraction#isRational()} returns false
	 * 
	 * @return the unreduced denominator of this common fraction.
	 */
	public BigInteger getDenominator() {
		return denominator;
	}

	/*
	 * Date: 1 Sep 2020-----------------------------------------------------------
	 * Time created: 20:52:59--------------------------------------------
	 */
	/**
	 * Returns the precision used to parse the decimal fraction. If this object was
	 * instantiated using the {@code BigInteger} argument constructor then a value
	 * of 1e-10 is returned else the appropriate accuracy is returned. This
	 * determines how precise the conversion from decimal fraction to rational
	 * number is when
	 * {@link #BigFraction(BigDecimal, MathContext, BigInteger, BigDecimal)} is
	 * used.
	 * 
	 * @return the accuracy of this {@code BigFraction}
	 */
	public BigDecimal getAccuracy() {
		return accuracy;
	}

	/*
	 * Date: Jan 11, 2023
	 * ----------------------------------------------------------- Time created:
	 * 2:10:25 AM ---------------------------------------------------
	 */
	/**
	 * Returns the internal context settings.
	 * 
	 * @return the internal context of {@code this}
	 */
	public MathContext getMathContext() {
		return context;
	}

	/*
	 * Date: 1 Sep 2020-----------------------------------------------------------
	 * Time created: 21:16:01--------------------------------------------
	 */
	/**
	 * Gets the expanded decimal fraction that this rational number correspond to.
	 * If this {@code BigFraction} was created with any of the constructors that
	 * accepts a {@code BigDecimal}, then the exact argument fraction is returned
	 * and it may be different from the decimal expansion of the numerator and
	 * denominator.
	 * 
	 * @return the decimal fraction representing this {@code BigFraction}.
	 */
	public BigDecimal getFraction() {
		return decimalFraction;
	}

	/*
	 * Date: 1 Oct 2020-----------------------------------------------------------
	 * Time created: 06:26:44--------------------------------------------
	 */
	/**
	 * Gets the expanded decimal fraction that this rational number correspond to,
	 * up to the requested precision. If this {@code BigFraction} was created with
	 * any of the constructors that accepts a {@code BigDecimal}, then the exact
	 * argument fraction (to the specified precision) is returned and it may be
	 * different from the decimal expansion of the numerator and denominator.
	 * 
	 * @param precision the number of significant digits within the returned
	 *                  decimal.
	 * @return the decimal representation of this to the given precision.
	 */
	public BigDecimal getDecimalExpansion(int precision) {
		MathContext c = new MathContext(precision, RoundingMode.HALF_EVEN);
		return isRational() ? new BigDecimal(numerator).divide(new BigDecimal(denominator), c)
				: decimalFraction.stripTrailingZeros().round(c);
	}

	////////////////////////////////////////////////////////////
	//////////////////////// Checks ///////////////////////////
	//////////////////////////////////////////////////////////

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 11:20:46--------------------------------------------
	 */
	/**
	 * Returns true if the absolute value of the numerator is less than the non-
	 * negative denominator and this is not the {@linkplain #ZERO zero} fraction.
	 * 
	 * @return {@code true} if this is a proper fraction else returns {@code false}.
	 */
	public boolean isProper() {
		return numerator.abs().compareTo(denominator) < 0 && numerator.signum() != 0;
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 11:23:54--------------------------------------------
	 */
	/**
	 * Returns true if the expanded decimal does not contain any significant digits
	 * i.e this representation is an integer.
	 * 
	 * @return true if this is an integer and false otherwise.
	 */
	public boolean isInteger() {
		return toMixed()[1].signum() == 0;
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 11:27:26--------------------------------------------
	 */
	/**
	 * Return whether this is a rational fraction or an approximation of a 'real'
	 * number (i.e checks whether or not this fraction converged during conversion
	 * from decimal to common fraction. This is only for the decimal constructors as
	 * this will always return {@code true} if any of the constructor that takes in
	 * a {@code BigInteger} numerator and denominator is used to initialise this
	 * object). This is because certain 'real' values (for example &#x221a;2) do not
	 * have a terminating or recurring mantissa hence cannot be represented exactly.
	 * However, a good knowledge of how to configure the accuracy may lead to very
	 * good approximations.
	 * 
	 * @return {@code true} if this is an exact representation of the resident
	 *         decimal expansion. If this object is created using any of the integer
	 *         based constructors like
	 *         {@linkplain #BigFraction(BigInteger, BigInteger) this} constructor,
	 *         then this method will always return true as long as no unusual
	 *         operation is applied to it such as a fractional exponentiation.
	 */
	public boolean isRational() {
		if (!rationalConstructor) {
			return isInteger() || helper.isRational();
		}
		return true;
//		BigDecimal decConv = new BigDecimal(numerator).divide(new BigDecimal(denominator), DEFAULT_CONTEXT);
//		return decimalFraction.compareTo(decConv) == 0;
	}

	/*
	 * 
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 11:36:41--------------------------------------------
	 */
	/**
	 * Returns {@code true} if the decimal expansion has recurring digits in the
	 * mantissa.
	 * 
	 * @return {@code true} if the decimal expansion has recurring digits.
	 */
	public boolean isRecurring() {
		return getPeriod() != 0;
	}

	/*
	 * Date: 2 Nov 2020-----------------------------------------------------------
	 * Time created: 09:25:36--------------------------------------------
	 */
	/**
	 * Returns whether the argument is a factor of this or vice-versa and return
	 * either true or false depending on the argument. A factor is any value that
	 * evenly divides a {@code BigFraction} i.e when applied as a divisor, the
	 * result is an integer (whole number). For example, 1/2 and 1/4 are common
	 * factors because the latter is a factor of the former. In this method, it does
	 * not matter the order from which the check was made i.e even if this method
	 * was called from 1/4 with 1/2 as it's argument, it will still return true. The
	 * reverse is also true
	 * 
	 * @param f a {@code BigFraction} assumed to be a factor of <code>this</code>,
	 *          or <code>this</code> is a factor of <code>f</code>
	 * @return <code>true</code> if the argument is a factor of <code>this</code> or
	 *         <code>this</code> is a factor of the argument, or else returns
	 *         {@code false}. In other words both are common factors.
	 */
	public boolean isCommonFactor(BigFraction f) {
		if (denominator.compareTo(f.denominator) == 0)
			return divide(f).isInteger() || f.divide(this).isInteger();

		BigInteger lcm = lcm(f);
		BigFraction thisOne = new BigFraction(numerator.multiply(lcm.divide(denominator)), lcm);
		BigFraction fr = new BigFraction(f.numerator.multiply(lcm.divide(f.denominator)), lcm);

		return thisOne.isCommonFactor(fr);
	}

	/*
	 * Date: 2 Nov 2020-----------------------------------------------------------
	 * Time created: 09:47:12--------------------------------------------
	 */
	/**
	 * Returns <code>true</code> if the argument evenly divide <code>this</code>,
	 * i.e the argument divides <code>this</code> and the answer is an integer.
	 * 
	 * @param f a {@code BigFraction} to be tested
	 * @return {@code true} if the argument is a factor of <code>this</code>
	 *         otherwise returns {@code false}.
	 */
	public boolean isAFactorOf(BigFraction f) {
		if (denominator.compareTo(f.denominator) == 0)
			return divide(f).isInteger();

		BigInteger lcm = lcm(f);
		BigFraction thisOne = new BigFraction(numerator.multiply(lcm.divide(denominator)), lcm);
		BigFraction fr = new BigFraction(f.numerator.multiply(lcm.divide(f.denominator)), lcm);

		return thisOne.isCommonFactor(fr);
	}

	/////////////////////////////////////////////////////////////
	/////////////////// Arithmetic methods /////////////////////
	///////////////////////////////////////////////////////////

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 12:09:58--------------------------------------------
	 */
	/**
	 * <p>
	 * Calculates and returns the sum of this (augend) {@code BigFraction} and the
	 * addend.
	 * </p>
	 * <p>
	 * If the accuracy of both operands are unequal, the {@code BigFraction} object
	 * returned will have
	 * {@code accuracy == Math.min(this.accuracy, addend.acuracy)} equal true. Using
	 * an object for {@code addend} whose {@code accuracy == this.accuracy} is less
	 * costly (in other words will compute faster).
	 * </p>
	 * 
	 * @param addend the {@code BigFraction} to be added to <code>this</code>.
	 * @return <code>this + addend</code>
	 */
	public BigFraction add(BigFraction addend) {
		if (this.isZero())
			return addend;
		else if (addend.isZero())
			return this;
		else if (isInteger() && addend.isInteger()) {
			BigInteger[] mxd = toMixed();
			BigInteger[] amxd = addend.toMixed();
			return new BigFraction(mxd[0].add(amxd[0]), BigInteger.ONE);
		}
		if (accuracy.compareTo(addend.accuracy) == 0) {
			BigFraction augend = toLowestTerms();
			addend = addend.toLowestTerms();
			BigInteger lcm = augend.lcm(addend);
			BigInteger n1 = lcm.divide(augend.denominator).multiply(augend.numerator);
			BigInteger n2 = lcm.divide(addend.denominator).multiply(addend.numerator);
			return new BigFraction(n1.add(n2), lcm);
		}
		return new BigFraction(decimalFraction.add(addend.decimalFraction), null, null, accuracy.min(addend.accuracy));
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 12:15:36--------------------------------------------
	 */
	/**
	 * Computes and returns the sum of {@code this} and the addend. The caveat to
	 * using floating point as to initialise a {@code BigFraction} is explained
	 * {@link BigFraction#valueOf(Number) here}.
	 * 
	 * @param addend the {@code Number} to be added to <code>this</code>.
	 * @return <code>this + addend</code>
	 */
	public BigFraction add(Number addend) {
		return add(valueOf(addend));
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 12:17:21--------------------------------------------
	 */
	/**
	 * <p>
	 * Calculates and returns the difference of this (minuend) {@code BigFraction}
	 * and the subtrahend.
	 * </p>
	 * <p>
	 * If the accuracy of both operands are unequal, the {@code BigFraction} object
	 * returned will have
	 * {@code accuracy == Math.min(this.accuracy, addend.acuracy)} equal true. Using
	 * an object for {@code subtrahend} whose {@code accuracy == this.accuracy} is
	 * less costly (in other words will compute faster).
	 * </p>
	 * 
	 * @param subtrahend the {@code BigFraction} to be subtracted to
	 *                   <code>this</code>.
	 * @return <code>this - subtrahend</code>
	 */
	public BigFraction subtract(BigFraction subtrahend) {
		if (isZero())
			return subtrahend.negate();
		else if (subtrahend.isZero())
			return this;
		else if (isInteger() && subtrahend.isInteger()) {
			BigInteger[] mxd = toMixed();
			BigInteger[] amxd = subtrahend.toMixed();
			return new BigFraction(mxd[0].subtract(amxd[0]), BigInteger.ONE);
		}
		if (accuracy.compareTo(subtrahend.accuracy) == 0) {
			BigFraction minuend = toLowestTerms();
			subtrahend = subtrahend.toLowestTerms();
			BigInteger lcm = minuend.lcm(subtrahend);
			BigInteger n1 = lcm.divide(minuend.denominator).multiply(minuend.numerator);
			BigInteger n2 = lcm.divide(subtrahend.denominator).multiply(subtrahend.numerator);
			return new BigFraction(n1.subtract(n2), lcm);
		}
		return new BigFraction(decimalFraction.subtract(subtrahend.decimalFraction), null, null,
				accuracy.min(subtrahend.accuracy));
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 12:19:02--------------------------------------------
	 */
	/**
	 * Computes and returns the difference of {@code this} and the subtrahend. The
	 * caveat to using floating point as to initialise a {@code BigFraction} is
	 * explained {@link BigFraction#valueOf(Number) here}.
	 * 
	 * @param subtrahend the {@code Number} to be subtracted to <code>this</code>.
	 * @return <code>this - subtrahend</code>
	 */
	public BigFraction subtract(Number subtrahend) {
		return subtract(valueOf(subtrahend));
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 12:20:25--------------------------------------------
	 */
	/**
	 * <p>
	 * Calculates and returns the product of this (multiplier) {@code BigFraction}
	 * and the multiplicand.
	 * </p>
	 * <p>
	 * If the accuracy of both operands are unequal, the {@code BigFraction} object
	 * returned will have
	 * {@code accuracy == Math.min(this.accuracy, addend.acuracy)} equal true. Using
	 * an object for {@code multiplicand} whose {@code accuracy == this.accuracy} is
	 * less costly (in other words will compute faster).
	 * </p>
	 * 
	 * @param multiplicand the {@code BigFraction} to be multiplied by
	 *                     <code>this</code>.
	 * @return <code>this * multiplicand</code>
	 */
	public BigFraction multiply(BigFraction multiplicand) {
		if (compareTo(ONE) == 0)
			return multiplicand;
		else if (multiplicand.compareTo(ONE) == 0)
			return this;
		else if (isZero() || multiplicand.isZero())
			return ZERO;
		else if (isInteger()) {
			if (multiplicand.isInteger())
				return new BigFraction(numerator.multiply(multiplicand.numerator), BigInteger.ONE);
			return new BigFraction(numerator.multiply(multiplicand.numerator), multiplicand.denominator);
		} else if (multiplicand.isInteger()) {
			if (isInteger())
				return new BigFraction(numerator.multiply(multiplicand.numerator), BigInteger.ONE);
			return new BigFraction(numerator.multiply(multiplicand.numerator), denominator);
		}
		if (accuracy.compareTo(multiplicand.accuracy) == 0) {
			BigFraction multiplier = toLowestTerms();
			multiplicand = multiplicand.toLowestTerms();
			return new BigFraction(multiplier.numerator.multiply(multiplicand.numerator),
					multiplier.denominator.multiply(multiplicand.denominator));
		}
		return new BigFraction(decimalFraction.multiply(multiplicand.decimalFraction), null, null,
				accuracy.min(multiplicand.accuracy));
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 12:25:33--------------------------------------------
	 */
	/**
	 * Computes and returns the product of {@code this} and the multiplicand. The
	 * caveat to using floating point as to initialise a {@code BigFraction} is
	 * explained {@link BigFraction#valueOf(Number) here}.
	 * 
	 * @param multiplicand the {@code Number} to be multiplied by <code>this</code>.
	 * @return <code>this * multiplicand</code>
	 */
	public BigFraction multiply(Number multiplicand) {
		return multiply(valueOf(multiplicand));
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 12:26:46--------------------------------------------
	 */
	/**
	 * <p>
	 * Calculates and returns the quotient of this (dividend) {@code BigFraction}
	 * and the divisor.
	 * </p>
	 * <p>
	 * If the accuracy of both operands are unequal, the {@code BigFraction} object
	 * returned will have
	 * {@code accuracy == Math.min(this.accuracy, addend.acuracy)} equal true. Using
	 * an object for {@code divisor} whose {@code accuracy == this.accuracy} is less
	 * costly (in other words will compute faster).
	 * </p>
	 * 
	 * @param divisor the {@code BigFraction} to be divided by <code>this</code>.
	 * @return <code>this / multiplicand</code>
	 * @throws ArithmeticException if divisor is 0.
	 */
	public BigFraction divide(BigFraction divisor) throws ArithmeticException {
		if (divisor.isZero())
			new MathematicalException(ExceptionMessage.DIVISION_BY_ZERO);
		else if (isZero())
			return ZERO;
		else if (divisor.compareTo(ONE) == 0)
			return this;
		if (accuracy.compareTo(divisor.accuracy) == 0) {
			divisor = divisor.toLowestTerms().getReciprocal();
			BigFraction dividend = toLowestTerms();
			return new BigFraction(dividend.numerator.multiply(divisor.numerator),
					dividend.denominator.multiply(divisor.denominator));
		}
		return new BigFraction(decimalFraction.divide(divisor.decimalFraction), null, null,
				accuracy.min(divisor.accuracy));
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 12:28:12--------------------------------------------
	 */
	/**
	 * Computes and returns the quotient of {@code this} divided by the divisor. The
	 * caveat to using floating point as to initialise a {@code BigFraction} is
	 * explained {@link BigFraction#valueOf(Number) here}.
	 * 
	 * @param divisor the {@code Number} to be divided by <code>this</code>.
	 * @return <code>this / divisor</code>
	 * @throws ArithmeticException if divisor is 0.
	 */
	public BigFraction divide(Number divisor) throws ArithmeticException {
		return divide(valueOf(divisor));
	}

	/*
	 * Date: 20 May 2021-----------------------------------------------------------
	 * Time created: 11:17:59--------------------------------------------
	 */
	/**
	 * Computes and returns {@code this % divisor}
	 * 
	 * @param divisor the divisor
	 * @return {@code this % divisor}
	 */
	public BigFraction remainder(BigFraction divisor) {
		if (divisor.isZero())
			new MathematicalException(ExceptionMessage.VALUE_IS_ZERO);
		if (isInteger() && divisor.isInteger())
			return valueOf(numerator.remainder(divisor.numerator));
		if (abs().compareTo(divisor.abs()) < 0)
			return abs();
		BigFraction f = valueOf(abs().divide(divisor.abs()).decimalFraction.toBigInteger());
		return subtract(f.multiply(divisor));
	}

	/*
	 * Date: 20 May 2021-----------------------------------------------------------
	 * Time created: 11:19:46--------------------------------------------
	 */
	/**
	 * Computes and returns {@code this % divisor}. The caveat to using floating
	 * point as to initialise a {@code BigFraction} is explained
	 * {@link BigFraction#valueOf(Number) here}.
	 * 
	 * @param divisor the divisor
	 * @return {@code this % divisor}
	 */
	public BigFraction remainder(java.lang.Number n) {
		return remainder(valueOf(n));
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 12:30:06--------------------------------------------
	 */
	/**
	 * Computes and returns {@code this} raised to the power of the exponent. The
	 * caveat to using floating point as to initialise a {@code BigFraction} is
	 * explained {@link BigFraction#valueOf(Number) here}.
	 * 
	 * @param exponent the {@code Number} by which <code>this</code> is raised to
	 *                 the power of.
	 * @return <code>this<sup>exponent</sup></code>
	 */
	public BigFraction exponentiate(Number exponent) {
		if (exponent instanceof BigDecimal)
			return pow(this, (BigDecimal) exponent);
		else if (exponent instanceof BigInteger)
			return pow(this, new BigDecimal((BigInteger) exponent));
		return pow(this, new BigDecimal(exponent.doubleValue()));
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 12:34:59--------------------------------------------
	 */
	/**
	 * <p>
	 * Calculates and returns this (base) {@code BigFraction} raised to the power of
	 * the exponent.
	 * </p>
	 * <p>
	 * If the accuracy of both operands are unequal, the {@code BigFraction} object
	 * returned will have
	 * {@code accuracy == Math.min(this.accuracy, addend.acuracy)} equal true. Using
	 * an object for {@code exponent} whose {@code accuracy == this.accuracy} is
	 * less costly (in other words will compute faster).
	 * </p>
	 * 
	 * @param exponent the {@code BigFraction} by which <code>this</code> is raised
	 *                 to the power of.
	 * @return <code>this<sup>exponent</sup></code>
	 * @throws ArithmeticException if the exponent is not an integer and this is
	 *                             negative
	 */
	public BigFraction exponentiate(final BigFraction exponent) throws ArithmeticException {
		if (!exponent.isInteger() && signum() < 0)
			new MathematicalException(ExceptionMessage.ATTEMPTED_ROOT_OF_NEGATIVE_NUMBER);
		if (isZero())
			return ZERO;
		if (exponent.isZero())
			return ONE;
		if (exponent.compareTo(ONE) == 0)
			return this;
		if (compareTo(ONE) == 0)
			return ONE;
		BigFraction f = toLowestTerms();
		try {
			if (exponent.accuracy.compareTo(f.accuracy) == 0) {
				BigFraction base = f;
				if (exponent.isInteger()) {
					if (exponent.signum() < 0)
						base = base.getReciprocal();
					return new BigFraction(base.numerator.pow(f.decimalFraction.toBigInteger().intValueExact()),
							base.denominator.pow(f.decimalFraction.toBigInteger().intValueExact()));
				}
			}
		} catch (ArithmeticException e) {
		}
		/* Use indices to evaluate */
		if (exponent.signum() > 0 && f.signum() > 0)
			try {
				final BigFraction exp = exponent.toLowestTerms();
				final BigInteger expon = exp.numerator;
				final BigInteger degree = exp.denominator;
				final MathContext c = context;
				final BigDecimal numerRoot = Arith.pow(new BigDecimal(f.numerator),
						BigDecimal.ONE.divide(new BigDecimal(degree), c), c);
				final BigDecimal numer = numerRoot.pow(expon.intValueExact(), c);
				final BigDecimal denomRoot = Arith.pow(new BigDecimal(f.denominator),
						BigDecimal.ONE.divide(new BigDecimal(degree), c), c);
				final BigDecimal denom = denomRoot.pow(expon.intValueExact(), c);
				return new BigFraction(numer.divide(denom, c), c, null, f.accuracy, 1);
			} catch (ArithmeticException e) {
			}
		return new BigFraction(Arith.pow(f.decimalFraction, exponent.decimalFraction, context), null, null,
				f.accuracy.min(exponent.accuracy));
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 12:38:31--------------------------------------------
	 */
	/**
	 * Returns the absolute value of this.
	 * 
	 * @return <code>|this|</code>
	 */
	public BigFraction abs() {
		if (signum() < 0)
			return new BigFraction(numerator.abs(), denominator);

		return this;
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 12:39:25--------------------------------------------
	 */
	/**
	 * Negates and returns this {@code BigFraction}. This will return a positive
	 * value if {@code this} is negative else it will return a negative value.
	 *
	 * @return <code>-this</code>
	 */
	public BigFraction negate() {
		if (signum() < 0)
			return abs();
		return new BigFraction(numerator.negate(), denominator).toLowestTerms();
	}

	/*
	 * Date: 1 Sep 2020-----------------------------------------------------------
	 * Time created: 20:41:45--------------------------------------------
	 */
	/**
	 * Return the signum function of this {@code BigFraction} object.
	 *
	 * @return -1, 0, or 1 as the value of this {@code BigFraction} is negative,
	 *         zero, or positive.
	 */
	public int signum() {
		return decimalFraction.signum();
	}

	/*
	 * Date: 20 May 2021-----------------------------------------------------------
	 * Time created: 11:20:17--------------------------------------------
	 */
	/**
	 * Returns {@code this!}
	 * 
	 * @return {@code this!}
	 */
	public BigDecimal factorial() {
		if (decimalFraction.compareTo(Calculator.MAX_FACTORIAL) > 0)
			new MathematicalException(ExceptionMessage.NUMBER_TOO_BIG);
		return Arith.factorial(decimalFraction, context);
	}

	////////////////////////////////////////////////////////////
	//////////////// Other utility methods ////////////////////
	//////////////////////////////////////////////////////////

	/*
	 * Date: 2 Sep 2020-----------------------------------------------------------
	 * Time created: 16:05:11-----------------------------------------------------
	 * The following are fractions with their period: 25 / 169 ------ 78
	 */
	/**
	 * The number of digits in the recurring portion of the decimal expansion of
	 * this {@code BigFraction}.
	 * 
	 * @return the number of recurring digits or 0 if there is no recurring digit in
	 *         the decimal expansion. If this is an integer or a non rational number
	 *         (as specified by {@link #isRational()}) then 0 is also returned
	 */
	public int getPeriod() {
		if (isInteger() || !isRational())
			return 0;
		BigDecimal[] a = getRecurring();
		/*
		 * for example, 1.03(0456789) would cause the commented code to be incorrect. as
		 * when the former recurring algorithm is used: period = 6 (whereby the correct
		 * value is 7) because the second index of the bigDecimal array will return only
		 * an integer hence the initial zero will not be counted, causing an inaccurate
		 * result.
		 */
//		return a[1].compareTo(BigDecimal.ZERO) == 0 ? 0 : a[1].toPlainString().length();
		if (Utility.isInteger(a[0]))
			return a[1].stripTrailingZeros().toPlainString().substring(2).length();
		else if (a[1].compareTo(BigDecimal.ZERO) == 0)
			return 0;
		return Utility.numOfFractionalDigits(
				a[1].multiply(BigDecimal.TEN.pow(Utility.numOfFractionalDigits(a[0].stripTrailingZeros())))
						.stripTrailingZeros());
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 12:41:51--------------------------------------------
	 */
	/**
	 * Performs a reduction on this {@code BigFraction} by decomposing the numerator
	 * and denominator to their respective lowest terms.
	 * 
	 * @return this as a reduced fraction.
	 */
	public BigFraction toLowestTerms() {
		BigInteger[] reduced = reduce(numerator, denominator);
		return new BigFraction(reduced[0], reduced[1]);
//		return setAccuracy(accuracy);
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 12:54:10--------------------------------------------
	 */
	/**
	 * <p>
	 * Return a fraction with the provided accuracy level tolerance level for
	 * evaluating fractions. The tolerance for a decimal value is the scale or
	 * number of mantissa digits that will be used for calculating the resulting
	 * common fraction. The lesser the value, the more accurate calculations are and
	 * the more memory calculations use too.<br>
	 * The {@code BigFraction} object that is returned uses this
	 * {@code BigFraction}'s decimal fraction.
	 * </p>
	 * <p>
	 * <b>NOTE:</b> Unlike the set methods of some objects, this one does not mutate
	 * this {@code BigFraction}. In order for this operation to be completed, it
	 * must be on the left side of an assignment statement, just calling this method
	 * will do nothing.
	 * </p>
	 *
	 * @param accuracy the accuracy to be set.
	 * @return this with the argument as it's accuracy. Note that this method causes
	 *         a re-evaluation of the internal decimal expansion.
	 * @throws ArithmeticException if <code> accuracy &lt;= 0 ||
	 *                              accuracy &gt;= 1</code>.
	 */
	public BigFraction setAccuracy(BigDecimal accuracy) throws ArithmeticException {
		if (accuracy.compareTo(this.accuracy) == 0)
			return this;
		return new BigFraction(this, accuracy, context);
	}

	/*
	 * Date: Jan 11, 2023
	 * ----------------------------------------------------------- Time created:
	 * 1:38:34 AM ---------------------------------------------------
	 */
	/**
	 * Returns {@code this} with the given {@code MathContext} set on the new
	 * Object. This is an immutable operation.
	 * 
	 * @param mc the {@code MathContext} to be set
	 * @return {@code this} with the rounding object the same as was given in the
	 *         argument.
	 */
	public BigFraction setMathContext(MathContext mc) throws ArithmeticException {
		return new BigFraction(this, getAccuracy(), mc);
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 12:59:51--------------------------------------------
	 */
	/**
	 * Returns the lowest common multiple function of this denominator and the
	 * argument's denominator.
	 * 
	 * @param fraction the value which is evaluated to get the lcm.
	 * @return <code>lcm(getDenominator(), fraction.getDenominator())</code>
	 */
	public BigInteger lcm(BigFraction fraction) {
		return lcm(denominator, fraction.denominator);
	}

	/*
	 * Date: 23 Jul 2021-----------------------------------------------------------
	 * Time created: 16:52:17--------------------------------------------
	 */
	/**
	 * Attempts to calculate the gcd of f.
	 * 
	 * @param f the {@code BigFraction} whose gcd is to be calculated
	 * @return a {@code BigFraction} which is the gcd of the argument
	 */
	public BigFraction gcd(BigFraction f) {
		if (isInteger() && f.isInteger())
			return valueOf(numerator.gcd(f.numerator));
		return hcf(abs(), f.abs());
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 13:06:26--------------------------------------------
	 */
	/**
	 * Returns <code>this &times 100</code> i.e the percentage value of this as a
	 * {@code BigDecimal}, in it's unrounded form. This method never returns a
	 * negative value.
	 * 
	 * @return a {@code BigDecimal} value which is the percentage value of this
	 *         {@code BigFraction}
	 */
	public BigDecimal percent() {
		return abs().multiply(100).decimalFraction;
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 11:39:28--------------------------------------------
	 */
	/**
	 * Gets this fraction's expansion in terms of its non-recurring part and its
	 * recurring part (as a non-negative {@code BigDecimal} whose string form is
	 * 
	 * <pre>
	 * 0.[0 or <i>n</i> times][sequence of the mantissa digits]
	 * </pre>
	 * 
	 * where <code><i>n</i></code> is the {@link BigDecimal#scale() scale} of the
	 * non-recurring part). If this is not a recurring decimal, then the recurring
	 * index - which is the second index in the returned array - is 0. For example,
	 * the fraction 0.(00980) whereby 00980 is infinitely recurring will contain 0
	 * in it's first index and 0.00980 in the second&semi; the fraction 9.81(00980)
	 * whereby 00980 is infinitely recurring will contain 9.81 in it's first index
	 * and 0.0000980 in it's second.
	 * 
	 * @return an array of {@code BigDecimal}. If {@link #isRecurring()} returns
	 *         {@code true} then this value contains the following in the given
	 *         index order:
	 *         <dl>
	 *         <dt>0 -</dt>
	 *         <dd>the non-recurring part (which may include the sign of the
	 *         fraction)</dd>.
	 *         <dt>1 -</dt>
	 *         <dd>the unsigned recurring part in the form:<br>
	 *         <code>0.[sequence of recurring digits] &div; 10<sup>[scale of the non-recurring part]</sup></dd></code>.
	 *         </dl>
	 *         Else if {@link #isRecurring()} returns {@code false}, the first index
	 *         will contain the non- recurring part, while the second will contain
	 *         the value zero.
	 */
	public BigDecimal[] getRecurring() {
		return helper.getRecurring();
	}

	/*
	 * Date: 8 Sep 2022-----------------------------------------------------------
	 * Time created: 10:55:18--------------------------------------------
	 */
	/**
	 * Returns an array of all the digits (excluding any extraneous sign char,
	 * decimal point or exponent sign) in the recurring part of this
	 * {@code BigFraction}.
	 * <p>
	 * The value returned is the same one expected in
	 * {@link #fromRecurringDecimal(char[], int, int)}.
	 * 
	 * @return the digits in the recurring part of {@code this} as a char array, or
	 *         an empty array (of length 0) if {@link #isRecurring()} returns false.
	 */
	public char[] getRecurringDigits() {
		if (!isRecurring())
			return new char[] {};
		BigDecimal[] bf = getRecurring();
		if (Utility.isInteger(bf[0]))
			return bf[1].stripTrailingZeros().toPlainString().substring(2).toCharArray();
		return bf[1].multiply(BigDecimal.TEN.pow(Utility.numOfFractionalDigits(bf[0].stripTrailingZeros())))
				.stripTrailingZeros().toPlainString().substring(2).toCharArray();
	}

	/*
	 * Date: 2 Sep 2020-----------------------------------------------------------
	 * Time created: 14:53:13--------------------------------------------
	 */
	/**
	 * Returns the multiplicative inverse of this {@code BigFraction}.
	 * 
	 * @return a {@code BigFraction} where the numerator is this denominator and the
	 *         denominator is this numerator.
	 * @throws ArithmeticException if this is the {@linkplain #ZERO zero} fraction.
	 */
	public BigFraction getReciprocal() throws ArithmeticException {
		if (isZero())
			new MathematicalException(ExceptionMessage.DIVISION_BY_ZERO);
		return new BigFraction(denominator, numerator);
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 11:50:45--------------------------------------------
	 */
	/**
	 * Calculates the integer (whole number), numerator and denominator of this
	 * {@code BigFraction} and returns the result in an array.
	 * 
	 * @return a 3 elements array of {@code BigInteger} containing the whole number,
	 *         a reduced numerator and denominator respectively.
	 */
	public BigInteger[] toMixed() {
		BigInteger[] quotient = numerator.divideAndRemainder(denominator);
		return new BigInteger[] { quotient[0],
				quotient[0].signum() == 0 && quotient[1].signum() != 0 ? quotient[1] : quotient[1].abs(),
				quotient[1].signum() == 0 ? BigInteger.ONE : denominator };
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 11:57:37--------------------------------------------
	 */
	/**
	 * Calculates the continued fraction representation of this {@code BigFraction}
	 * and returns the result in an immutable {@code List}. If the elements of
	 * continued fraction are greater than 5000, then an exception will be thrown.
	 * 
	 * @return the elements of the continued fraction representation of this
	 *         {@code BigFraction} in an immutable list. If this is negative, then
	 *         only the first element is negative.
	 * @throws RuntimeException specifically a {@code ArithmeticExcpetion} if the
	 *                          elements of the returning {@code List} object is
	 *                          found to be greater than 5000.
	 */
	public List<BigInteger> toContinuedFraction() {
		List<BigInteger> cf = new ArrayList<>();
		cf.add(decimalFraction.toBigInteger());
		BigFraction f = this.abs().subtract(cf.get(0).abs());
		int oscillation = 1;

		while (!f.isZero()) {
			f = f.getReciprocal();
			cf.add(f.decimalFraction.toBigInteger());
			f = f.subtract(f.decimalFraction.toBigInteger());
			if (oscillation > 5000)
				new MathematicalException(ExceptionMessage.INFINITE_CONTINUED_FRACTION);
			oscillation++;
		}

		return Collections.unmodifiableList(cf);
	}

	/*
	 * Date: 26 Oct 2020-----------------------------------------------------------
	 * Time created: 11:41:50--------------------------------------------
	 */
	/**
	 * returns the Egyptian fraction equivalent of {@code this}.
	 * <p>
	 * This method function as if: <code>toEgyptianFraction(abs())</code> is called,
	 * i.e It will never return a negative number in any of its indexes.
	 * <p>
	 * The {@code BigFraction} to be converted must have an absolute value that is
	 * less than 1, or else a list containing only {@code this} is returned. Also,
	 * if the numerator part of this proper fraction is equal to 1, {@code this} is
	 * returned.
	 * </p>
	 * 
	 * @return an immutable {@code List} object containing fractions whose
	 *         numerators == 1, or this {@code BigFraction} object's absolute value
	 *         if it is greater than or equal to 1.
	 */
	public List<BigFraction> toEgyptianFractions() {

		BigFraction f = abs();

		if (f.compareTo(BigFraction.ONE) >= 0 || numerator.compareTo(BigInteger.ONE) == 0)
			return Collections.unmodifiableList(Arrays.asList(f));

		List<BigFraction> fractions = new ArrayList<>();
		int i = 0;

		do {

			BigInteger d = Arith.floor(new BigDecimal(f.denominator).divide(new BigDecimal(f.numerator), context))
					.add(BigDecimal.ONE).toBigIntegerExact();

			BigFraction fr = BigFraction.ONE.divide(d);
			fractions.add(i, fr);

			f = f.subtract(fr);

			if (f.numerator.compareTo(BigInteger.ONE) == 0)
				fractions.add(i + 1, f);
			i++;

		} while (f.numerator.compareTo(BigInteger.ONE) != 0 || f.isZero());

		fractions.removeIf(isNull());

		fractions = Collections.unmodifiableList(fractions);

		return fractions;
	}

	/*
	 * Date: 2 Nov 2020-----------------------------------------------------------
	 * Time created: 10:00:54--------------------------------------------
	 */
	/**
	 * Returns the factor of a sufficiently small semi-prime integer in an
	 * unmodifiable {@code List}. If {@link #isInteger()} returns
	 * <code>false</code>, then a list containing the factors of the whole part,
	 * followed by a null element and finally the factors of the fractional part.
	 * Note that all factors in this {@code List} are probably prime.
	 * 
	 * @return a {@code List} of {@code BigInteger}s which are probably prime
	 *         factors of {@code this}.
	 */
	public List<BigInteger> factorize() {
		BigFraction f = abs();
		if (isInteger())
			return Factors.factors(f.numerator, 5);

		List<BigInteger> num = Factors.factors(f.numerator, 5);
		List<BigInteger> den = Factors.factors(f.denominator, 5);
		List<BigInteger> fact = new ArrayList<>(num);
		fact.add(null);// delimits the numerator factor(s) from the denominator factor(s)
		fact.addAll(den);

		return Collections.unmodifiableList(fact);
	}

	/*
	 * Date: 11 Nov 2020-----------------------------------------------------------
	 * Time created: 07:40:29--------------------------------------------
	 */
	/**
	 * Forces the numerator and denominator to be displayed as a string in the
	 * format: [numerator]/[denominator], regardless of whether the fraction is
	 * rational or not.
	 * 
	 * @return the string representation of this {@link BigFraction}.
	 */
	public String forceToString() {
		return new StringBuilder(numerator.toString()).append("/").append(denominator).toString();
	}

	/*
	 * Date: 20 May 2021-----------------------------------------------------------
	 * Time created: 11:29:22--------------------------------------------
	 */
	/**
	 * Support for TeX pretty print. This method returns this {@code BigFraction} in
	 * TeX or LaTeX syntax as a {@code String}.
	 * 
	 * @param decimalpoint      the decimal point for formatting to output. this is
	 *                          only needed if <code>this</code> is not a rational
	 *                          number.
	 * @param integerDelimiter  the integer delimiter. this is only needed if
	 *                          <code>this</code> is not a rational number.. this
	 * @param mantissaDelimiter a {@code Character} value that sits between units of
	 *                          digits on the mantissa side after formatting has
	 *                          taken place e.g the value {@code 1.234567} after
	 *                          formatting is <code>1.234 567</code>, so in this
	 *                          case, the space character is the mantissa delimiter
	 * @param digitsPerUnit     the number of digits to skip before appending
	 *                          delimiters.
	 * @param mixed             pretty print as a mixed fraction?
	 * @return a {@code String} in TeX fraction format
	 */
	public String toTeXString(char decimalpoint, char integerDelimiter, char mantissaDelimiter, int digitsPerUnit,
			boolean mixed) {
		if (mixed)
			return Utility.toTeXString(toMixed(), integerDelimiter, digitsPerUnit);
		return Utility.toTexString(this, decimalpoint, mantissaDelimiter, integerDelimiter, digitsPerUnit);
	}

	////////////////////////////////////////////////////////////
	////////////// Non-static private methods /////////////////
	//////////////////////////////////////////////////////////

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 17:59:11--------------------------------------------
	 */
	/**
	 * Flag for the {@linkplain #ZERO zero} fraction.
	 * 
	 * @return {@code true} if this == 0 and {@code false} if otherwise.
	 */
	private boolean isZero() {
		return numerator.signum() == 0;
	}

	/*
	 * Date: 26 Oct 2020-----------------------------------------------------------
	 * Time created: 12:18:05--------------------------------------------
	 */
	/**
	 * Method for detecting null in fraction list. I used this predicate in the
	 * {@link #toEgyptianFractions()} method.
	 * 
	 * @return a {@link Predicate} object.
	 */
	private Predicate<BigFraction> isNull() {
		return f -> f == null;
	}

	////////////////////////////////////////////////////////////
	//////////////// Static private methods ///////////////////
	//////////////////////////////////////////////////////////

	/*
	 * Date: 2 Sep 2020-----------------------------------------------------------
	 * Time created: 14:50:16--------------------------------------------
	 */
	/**
	 * Returns the canonical form of p/q. That is, p and q in reduced form.
	 * 
	 * @param p a numerator
	 * @param q a denominator
	 * @return both argument reduced as they are assumed to be numerator and
	 *         denominator of a common fraction.
	 */
	private static BigInteger[] reduce(BigInteger p, BigInteger q) {
		BigInteger k = p.gcd(q);
		while (k.compareTo(BigInteger.ONE) != 0) {
			p = p.divide(k);
			q = q.divide(k);
			k = p.gcd(q);
		}
		return new BigInteger[] { p, q };
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 13:10:34--------------------------------------------
	 */
	/**
	 * Calculates the lowest common multiple of p and q and returns the result. The
	 * calculation takes the form of
	 * <code>lcm(p.abs(), p.abs()) --> (p * q) / (gcd(p, q))</code>
	 * 
	 * @param p the first integer.
	 * @param q the second integer.
	 * @return the lowest common multiple of the arguments.
	 */
	private static BigInteger lcm(BigInteger p, BigInteger q) {
		if (p.signum() < 0)
			p = p.abs();
		if (q.signum() < 0)
			q = q.abs();
		return p.multiply(q).divide(p.gcd(q));
	}

	/*
	 * Date: 23 Jul 2021-----------------------------------------------------------
	 * Time created: 16:49:41--------------------------------------------
	 */
	/**
	 * Returns the highest common factor of p and q
	 * 
	 * @param p the first argument
	 * @param q the second argument
	 * @return the highest common factor of p and q
	 */
	private static BigFraction hcf(BigFraction p, BigFraction q) {
		if (q.signum() == 0)
			return p;
		return hcf(q, p.remainder(q));
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 17:46:02--------------------------------------------
	 */
	/**
	 * Calculates and returns the first argument raised to the power of the second
	 * argument.
	 * 
	 * @param base     the base of the returned power.
	 * @param exponent the exponent of the returned power.
	 * @return the first argument raised to the power of the second argument.
	 */
	private static BigFraction pow(BigFraction base, BigDecimal exponent) {
		if (base.isZero())
			return ZERO;

		if (exponent.compareTo(BigDecimal.ZERO) == 0)
			return ONE;

		if (exponent.compareTo(BigDecimal.ONE) == 0)
			return base;

		if (base.compareTo(ONE) == 0)
			return ONE;
		return new BigFraction(Arith.pow(base.decimalFraction, exponent, base.context), null, null, base.accuracy);
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 17:48:42--------------------------------------------
	 */
	/**
	 * Calculates a {@code MathContext} from the input. The returned
	 * {@code MathContext} object uses a rounding mode of half-even, and a precision
	 * equal to the input's scale.
	 * 
	 * @param accuracy the input which satisfies the condition
	 *                 <code>0 &lt; accuracy &lt; 1</code>
	 * @return a {@code MathContext} object whose rounding mode is
	 *         {@link RoundingMode#HALF_EVEN}.
	 * @throws ArithmeticException if accuracy is outside the domain (0, 1).
	 */
	private static MathContext getContextFromAccuracy(BigDecimal accuracy) throws ArithmeticException {
		if (accuracy.signum() <= 0 || accuracy.compareTo(BigDecimal.ONE) >= 0)
			new MathematicalException(ExceptionMessage.ACCURACY_MUST_BE_ONE);
		return new MathContext(accuracy.scale() < 50 ? 50 : accuracy.scale(), RoundingMode.HALF_EVEN);
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 18:04:15--------------------------------------------
	 */
	/**
	 * An unoptimised version of the common long division algorithm. This method
	 * represents the second step of the long division algorithm. This step is after
	 * the first division, hence the first argument is the remainder of the integer
	 * division, while the second argument is the divisor. The third argument is an
	 * already initialised map that is used to record the remainder (as keys) and
	 * the results of the division as (values). This is a recursive method that
	 * stops only when a repeated result and its corresponding remainder matches a
	 * previous remainder-result pair. This method should never be called when it is
	 * certain that the fraction is not rational 'cause it could cause a
	 * {@code StackOverflowError} to be thrown.
	 * 
	 * @param rem   the first argument. This represents a remainder of a previous
	 *              division.
	 * @param denom the divisor, which will continuously divide into the given
	 *              remainder recursively until the remainder is 0 or until
	 *              recurring remainder(s) is detected.
	 * @param mp    a {@code Map} that stores all the remainders as keys and their
	 *              resultant quotients as the value pair.
	 * @return the first recurring result of the division between the argument if
	 *         the division can no longer continue (either because of a 0 remainder
	 *         or a recurring remainder(s)).
	 */
	private static BigInteger divide(BigInteger rem, BigInteger denom, Map<BigInteger, BigInteger> mp) {

		if (mp.containsKey(rem))
			return rem;

		BigInteger num = rem.multiply(BigInteger.TEN);
		/* result part */
		BigInteger rp = num.divide(denom);
		mp.put(rem, rp);

		return divide(num.divideAndRemainder(denom)[1], denom, mp);
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 18:33:18--------------------------------------------
	 */
	/**
	 * Returns the decimal quotient of the arguments along with any recurring part
	 * truncated or 0 if there is no recurring part. The {@code String} returned
	 * takes the form of:
	 * 
	 * <pre>
	 * <code>[integer].[non-recurring mantissa]R[recurring mantissa]</code>
	 * </pre>
	 * 
	 * This method initialises a {@code LinkedHashMap} uses the
	 * {@link #divide(BigInteger, BigInteger, Map)} for long division and stores the
	 * first repeating result it returns. This value is then used as the third
	 * argument together with the map of remainders
	 * {@linkplain #display(BigInteger, BigInteger, BigInteger, Map) here}. It is
	 * this method that actually returns the string result.
	 * 
	 * @param num   the dividend
	 * @param denom the divisor
	 * @return a string of the decimal quotient of the first 2 arguments, with any
	 *         recurring part truncated
	 */
	private static String divide(BigInteger num, BigInteger denom) {
		Map<BigInteger, BigInteger> map = new LinkedHashMap<>();

		/* First repeating remainder */
		BigInteger frr = divide(num.divideAndRemainder(denom)[1], denom, map);

		return display(num, denom, frr, map);
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 19:01:41--------------------------------------------
	 */
	/**
	 * Returns the decimal quotient of the first 2 arguments along with any
	 * recurring part truncated or 0 if there is no recurring part. The
	 * {@code String} returned takes the form of:
	 * 
	 * <pre>
	 * <code>[integer].[non-recurring mantissa]R[recurring mantissa]</code>
	 * </pre>
	 * 
	 * This method first stores the integer quotient of the first 2 arguments, then
	 * appends all the keys of the {@code Map} argument to the string. When the
	 * recurring digit(s) is detected, then "R" is appended, then the recurring part
	 * and then the string is returned.
	 * 
	 * @param num   the dividend
	 * @param denom the divisor
	 * @param frr   the last remainder. If the division is recurring then this
	 *              is/are the first recurring digit(s) else this is 0.
	 * @param map   a map that order it's keys according their natural ordering.
	 *              This map stores all the remainder of the long division between
	 *              the first 2 arguments.
	 * @return a string of the decimal quotient of the first 2 arguments, with any
	 *         recurring part truncated
	 */
	private static String display(BigInteger num, BigInteger denom, BigInteger frr, Map<BigInteger, BigInteger> map) {
		BigInteger intPart = num.divide(denom);// stores the integer quotient
		StringBuilder sb = new StringBuilder(intPart.toString() + ".");
		for (BigInteger rem : map.keySet()) {

			if (rem.compareTo(frr) == 0)
				sb.append("R");
			/* Result part */
			BigInteger resPart = map.get(rem);
			sb.append(resPart);
		}
		return sb.toString();
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 19:25:16--------------------------------------------
	 */
	/**
	 * Calculates the decimal quotient and/or any recurring part using an
	 * unoptimised algorithm or a cycle detection algorithm.
	 * 
	 * @implNote the internal implementation causes an infinite loop when the
	 *           numerator/denominator is a semi-prime or an actual prime.
	 * 
	 * @param num      the dividend
	 * @param denom    the divisor
	 * @param optimise set if optimal performance is desired else set to false.
	 * @return a string of the decimal quotient of the first 2 arguments, with any
	 *         recurring part truncated.
	 */
	private static String divide(BigInteger num, BigInteger denom, boolean optimise) {
		if (!optimise)
			return divide(num, denom);
		final BigInteger[] divAndRem = num.divideAndRemainder(denom);
		StringBuilder sb = new StringBuilder(divAndRem[0].toString() + ".");

		Function<BigInteger, BigInteger> div = x -> x.multiply(BigInteger.TEN).divideAndRemainder(denom)[1];
		num = divAndRem[1];

		final LoopConfiguration lc = LoopConfiguration.detectPrefixLength(div, num);

		/* Output the non-recurring digits of the ratio */
		for (int i = 0; i < lc.getPrefixLength(); i++) {
			sb.append(num.multiply(BigInteger.TEN).divide(denom));
			num = div.apply(num);
		}

		/* Output the recurring digits */
		sb.append("R");
		for (int i = 0; i < lc.getLoopLength(); i++) {
			sb.append(num.multiply(BigInteger.TEN).divide(denom));
			num = div.apply(num);
		}
		return sb.toString();
	}

	////////////////////////////////////////////////////////////
	/////////////// Static protected methods //////////////////
	//////////////////////////////////////////////////////////

	/*
	 * Date: 31 Aug 2020-----------------------------------------------------------
	 * Time created: 19:58:02--------------------------------------------
	 */
	/**
	 * <p>
	 * Attempts to parse the decimal fraction {@code value} to generate a rational
	 * number (rational fraction) that consist of a whole number, numerator and the
	 * denominator and returns an immutable {@link List} in this order, using Ian
	 * Richard's algorithm for generating continued fractions which is guaranteed to
	 * converge rather quicker than most. If the number has no integer (whole
	 * number) part, then the {@code List} that is returned will have a length of 2
	 * i.e only the numerator and the denominator will be elements of the
	 * {@code List} else the elements in the list will be 3. Note that the numerator
	 * is always 0 and the denominator is always 1 if the input fraction is an
	 * integer.
	 * </p>
	 * <p>
	 * The accuracy argument constrains the parsing of the first argument, which
	 * causes the parser to stay on the course. This controls the precision of the
	 * parser, lesser values increase parsing precision, while greater values
	 * decrease parsing precision. However this method will throw an
	 * {@code ArithmeticException} if accuracy <= 0 || accuracy >= 1.
	 * </p>
	 * 
	 * @param value    the value (a fraction) to be parsed.
	 * @param accuracy the context which determines the precision of the parsing
	 *                 engine.
	 * @return an unmodifiable {@code List} of 3 elements if the fraction input has
	 *         an integer part whose absolute value is numerically greater than zero
	 *         in the following order: whole number, numerator, denominator or else
	 *         returns a {@code List} of 2 elements in the following order
	 *         numerator, denominator.
	 * @throws ArithmeticException if accuracy &lt;=0 || accuracy &gt;= 1.
	 */
	protected static List<BigInteger> ianRichardsAlgorithm(BigDecimal value, BigDecimal accuracy)
			throws ArithmeticException {
		if (accuracy.compareTo(BigDecimal.ZERO) <= 0 || accuracy.compareTo(BigDecimal.ONE) >= 0)
			new MathematicalException(ExceptionMessage.ACCURACY_MUST_BE_ONE);
		int signum = value.signum();
		if (signum < 0)
			value = value.abs();
		BigInteger integer = Arith.floor(value).toBigIntegerExact();
		value = value.subtract(new BigDecimal(integer));
		BigDecimal minimalValue = value.subtract(accuracy);
		if (minimalValue.compareTo(BigDecimal.ZERO) < 0)
			return Collections
					.unmodifiableList(Arrays.asList(integer.multiply(BigInteger.valueOf(signum)), BigInteger.ONE));
		BigDecimal maxValue = value.add(accuracy);
		if (maxValue.compareTo(BigDecimal.ONE) > 0)
			return Collections.unmodifiableList(
					Arrays.asList(BigInteger.valueOf(signum).multiply(integer.add(BigInteger.ONE)), BigInteger.ONE));
		MathContext mc = new MathContext(accuracy.scale(), RoundingMode.HALF_EVEN);
		BigInteger b = BigInteger.ONE;
		BigInteger d = BigDecimal.ONE.divide(maxValue, mc).toBigInteger();
		BigDecimal ln = minimalValue;
		BigDecimal ld = BigDecimal.ONE.subtract(new BigDecimal(d).multiply(minimalValue));
		BigDecimal rn = BigDecimal.ONE.subtract(new BigDecimal(d).multiply(maxValue));
		BigDecimal rd = maxValue;
		while (true) {
			if (ln.compareTo(ld) < 0)
				break;
			BigInteger n = ln.divide(ld, mc).toBigInteger();
			b = b.add(n.multiply(d));
			ln = ln.subtract(new BigDecimal(n).multiply(ld));
			rd = rd.subtract(new BigDecimal(n).multiply(rn));
			if (rn.compareTo(rd) < 0)
				break;
			n = rn.divide(rd, mc).toBigInteger();
			d = d.add(n.multiply(b));
			ld = ld.subtract(new BigDecimal(n).multiply(ln));
			rn = rn.subtract(new BigDecimal(n).multiply(rd));
		}
		BigInteger denom = b.add(d);
		BigInteger numer = value.multiply(new BigDecimal(denom).add(new BigDecimal("0.5"))).toBigInteger();
		return integer.compareTo(BigInteger.ZERO) == 0
				? Collections.unmodifiableList(Arrays.asList(BigInteger.valueOf(signum).multiply(numer), denom))
				: Collections
						.unmodifiableList(Arrays.asList(BigInteger.valueOf(signum).multiply(integer), numer, denom));
	}

	/*
	 * Date: 31 Aug 2020-----------------------------------------------------------
	 * Time created: 20:29:35--------------------------------------------
	 */
	/**
	 * <p>
	 * Attempts to parse the decimal fraction {@code value} to generate a rational
	 * number (rational fraction) that consist of a whole number, numerator and the
	 * denominator and returns an immutable {@link List} in this order, using
	 * Stern-Brocot's algorithm for generating rational numbers which is guaranteed
	 * to return the smallest denominator possible. If the number has no integer
	 * (whole number) part, then the {@code List} that is returned will have a
	 * length of 2 i.e only the numerator and the denominator will be elements of
	 * the {@code List} else the elements in the list will be 3. Note that the
	 * numerator is always 0 and the denominator is always 1 if the input fraction
	 * is an integer.
	 * </p>
	 * <p>
	 * The accuracy argument constrains the parsing of the first argument, which
	 * causes the parser to stay on the course. This controls the precision of the
	 * parser, lesser values increase parsing precision, while greater values
	 * decrease parsing precision. However this method will throw an
	 * {@code ArithmeticException} if accuracy &lt;= 0 || accuracy &gt;= 1.
	 * </p>
	 * 
	 * @param value    the value (a fraction) to be parsed.
	 * @param accuracy the context which determines the precision of the parsing
	 *                 engine.
	 * @return an unmodifiable {@code List} of 3 elements if the fraction input has
	 *         an integer part whose absolute value is numerically greater than zero
	 *         in the following order: whole number, numerator, denominator or else
	 *         returns a {@code List} of 2 elements in the following order
	 *         numerator, denominator.
	 * @throws ArithmeticException if accuracy &lt;=0 || accuracy &gt;= 1.
	 */
	protected static List<BigInteger> sternBrocotAlgorithm(BigDecimal value, BigDecimal accuracy)
			throws ArithmeticException {
		if (accuracy.compareTo(BigDecimal.ZERO) <= 0 || accuracy.compareTo(BigDecimal.ONE) >= 0)
			new MathematicalException(ExceptionMessage.ACCURACY_MUST_BE_ONE);
		int signum = value.signum();
		if (signum < 0)
			value = value.abs();
		BigDecimal maxError = signum == 0 ? accuracy : value.multiply(accuracy);
		BigInteger integer = Arith.floor(value).toBigIntegerExact();
		value = value.subtract(new BigDecimal(integer));
		if (value.compareTo(maxError) < 0)
			return Collections
					.unmodifiableList(Arrays.asList(integer.multiply(BigInteger.valueOf(signum)), BigInteger.ONE));
		else if (BigDecimal.ONE.subtract(maxError).compareTo(value) < 0)
			return Collections.unmodifiableList(
					Arrays.asList(BigInteger.valueOf(signum).multiply(integer.add(BigInteger.ONE)), BigInteger.ONE));

		BigInteger lowN = BigInteger.ZERO, lowD = BigInteger.ONE, upN = lowD, upD = upN;
		while (true) {
			BigInteger midN = lowN.add(upN), midD = lowD.add(upD);
			if (new BigDecimal(midD).multiply(value.add(maxError)).compareTo(new BigDecimal(midN)) < 0) {
				upN = midN;
				upD = midD;
			} else if (new BigDecimal(midN).compareTo(value.subtract(maxError).multiply(new BigDecimal(midD))) < 0) {

				lowN = midN;
				lowD = midD;
			} else

				return integer.compareTo(BigInteger.ZERO) == 0
						? Collections.unmodifiableList(Arrays.asList(midN.multiply(BigInteger.valueOf(signum)), midD))
						: Collections.unmodifiableList(
								Arrays.asList(integer.multiply(BigInteger.valueOf(signum)), midN, midD));
		}
	}

	/*
	 * Date: 31 Aug 2020-----------------------------------------------------------
	 * Time created: 20:29:55--------------------------------------------
	 */
	/**
	 * <p>
	 * A faster implementation of
	 * {@linkplain #sternBrocotAlgorithm(BigDecimal, BigDecimal) this algorithm}.
	 * </p>
	 * 
	 * @param value    the value (a fraction) to be parsed.
	 * @param accuracy the context which determines the precision of the parsing
	 *                 engine.
	 * @return an unmodifiable {@code List} of 3 elements if the fraction input has
	 *         an integer part whose absolute value is numerically greater than zero
	 *         in the following order: whole number, numerator, denominator or else
	 *         returns a {@code List} of 2 elements in the following order
	 *         numerator, denominator.
	 * @throws ArithmeticException if accuracy &lt;=0 || accuracy &gt;= 1.
	 */
	protected static List<BigInteger> sternBrocotAlgorithm2(BigDecimal value, BigDecimal accuracy)
			throws ArithmeticException {
		if (accuracy.compareTo(BigDecimal.ZERO) <= 0 || accuracy.compareTo(BigDecimal.ONE) >= 0)
			new MathematicalException(ExceptionMessage.ACCURACY_MUST_BE_ONE);
		int signum = value.signum();
		if (signum < 0)
			value = value.abs();
		BigDecimal maxError = signum == 0 ? accuracy : value.multiply(accuracy);
		BigInteger integer = Arith.floor(value).toBigIntegerExact();
		value = value.subtract(new BigDecimal(integer));
		if (value.compareTo(maxError) < 0)
			return Collections
					.unmodifiableList(Arrays.asList(integer.multiply(BigInteger.valueOf(signum)), BigInteger.ONE));
		else if (BigDecimal.ONE.subtract(maxError).compareTo(value) < 0)
			return Collections.unmodifiableList(
					Arrays.asList(BigInteger.valueOf(signum).multiply(integer.add(BigInteger.ONE)), BigInteger.ONE));
		final BigDecimal value0 = value;

		BigInteger lowN = BigInteger.ZERO, lowD = BigInteger.ONE, upN = lowD, upD = upN;
		while (true) {
			BigInteger midN = lowN.add(upN), midD = lowD.add(upD);
			final BiPredicate<BigInteger, BigInteger> f;
			if (new BigDecimal(midD).multiply(value.add(maxError)).compareTo(new BigDecimal(midN)) < 0) {
				final BigInteger lowD0 = lowD;
				final BigInteger lowN0 = lowN;
				f = (BigInteger un, BigInteger ud) -> {
					return new BigDecimal(lowD0).add(new BigDecimal(ud)).multiply(value0.add(maxError))
							.compareTo(new BigDecimal(lowN0).add(new BigDecimal(un))) < 0;
				};
				upN = upN.add(lowN);
				upD = upD.add(lowD);
				if (f.test(upN, upD)) {
					BigInteger weight = BigInteger.ONE;

					do {
						weight = weight.multiply(BigInteger.TWO);
						upN = upN.add(lowN.multiply(weight));
						upD = upD.add(lowD.multiply(weight));
					} while (f.test(upN, upD));

					do {
						weight = weight.divide(BigInteger.TWO);

						BigInteger adec = lowN.multiply(weight);
						BigInteger bdec = lowD.multiply(weight);

						if (!f.test(upN.subtract(adec), upD.subtract(bdec))) {
							upN = upN.subtract(adec);
							upD = upD.subtract(bdec);
						}
					} while (weight.compareTo(BigInteger.ONE) > 0);
				}
			} else if (new BigDecimal(midN).compareTo(value.subtract(maxError).multiply(new BigDecimal(midD))) < 0) {
				final BigInteger upN0 = upN;
				final BigInteger upD0 = upD;
				f = (BigInteger ln, BigInteger ld) -> {
					return new BigDecimal(ln).add(new BigDecimal(upN0)).compareTo(
							value0.subtract(maxError).multiply(new BigDecimal(ld).add(new BigDecimal(upD0)))) < 0;
				};
				lowN = lowN.add(upN);
				lowD = lowD.add(upD);
				if (f.test(lowN, lowD)) {
					BigInteger weight = BigInteger.ONE;

					do {
						weight = weight.multiply(BigInteger.TWO);
						lowN = lowN.add(upN.multiply(weight));
						lowD = lowD.add(upD.multiply(weight));
					} while (f.test(lowN, lowD));

					do {
						weight = weight.divide(BigInteger.TWO);

						BigInteger adec = upN.multiply(weight);
						BigInteger bdec = upD.multiply(weight);

						if (!f.test(lowN.subtract(adec), lowD.subtract(bdec))) {
							lowN = lowN.subtract(adec);
							lowD = lowD.subtract(bdec);
						}
					} while (weight.compareTo(BigInteger.ONE) > 0);
				}

			} else

				return integer.compareTo(BigInteger.ZERO) == 0
						? Collections.unmodifiableList(Arrays.asList(midN.multiply(BigInteger.valueOf(signum)), midD))
						: Collections.unmodifiableList(
								Arrays.asList(integer.multiply(BigInteger.valueOf(signum)), midN, midD));
		}
	}

	////////////////////////////////////////////////////////////
	///////////////// Static public methods ///////////////////
	//////////////////////////////////////////////////////////

	/*
	 * Date: 1 Sep 2020-----------------------------------------------------------
	 * Time created: 20:20:37--------------------------------------------
	 */
	/**
	 * <p>
	 * Creates a {@code BigFraction} by parsing the input {@code Number} object.
	 * </p>
	 * <p>
	 * An {@code ArithmeticException} is thrown if one of the following occurs:
	 * <ul>
	 * <li>If {@code n} is a floating-point value and it is NaN.</li>
	 * <li>If {@code n} is a floating-point value and it either positive infinity or
	 * negative infinity.</li>
	 * <li>If {@code n} is a decimal fraction and when parsed returns a
	 * {@code BigFraction} whose denominator is greater than
	 * {@link Long#MAX_VALUE}.</li>
	 * </ul>
	 * </p>
	 * If n is an integer type such as any of the java primitive integer types
	 * ({@code byte, short, int, long}) or is a {@code BigInteger}, then the max
	 * denominator check is quietly ignored. If n is a decimal (floating point)
	 * fraction, then a default context (to truncate decimals to about 50 digits)
	 * and a default accuracy of 1E-10 is used. Please note that some methods (such
	 * as {@linkplain #isRational()}) may not properly function when a
	 * {@code BigFraction} is created this way using a decimal fraction. An example
	 * of this is:
	 * 
	 * <pre>
	 * BigFraction f = BigFraction.valueOf(0.5);
	 * PrintStream out = System.out;
	 * out.println("toString(): " + f);
	 * out.println("getDecimalExpansion(200): " + f.getDecimalExpansion(200));
	 * out.println("forceToString(): " + f.forceToString());
	 * out.println("toTeXString(): " + f.toTeXString('.', ',', ' ', 3, false));
	 * out.println("isRecurring(): " + f.isRecurring());
	 * out.println("isRational(): " + f.isRational());
	 * out.println("getPeriod(): " + f.getPeriod());
	 * out.println("factorial(): " + f.factorial());
	 * out.println("factorize(): " + f.factorize());
	 * out.println("getRecurring()[0]: " + f.getRecurring()[0]);
	 * out.println("getRecurring()[1]: " + f.getRecurring()[1]);
	 * out.println("toContinuedFraction(): " + f.toContinuedFraction());
	 * out.println("toEgyptianFractions(): " + f.toEgyptianFractions());
	 * </pre>
	 * 
	 * The output is:
	 * 
	 * <pre>
	 *	toString(): [ 2500000001, 5000000003 ]
	 *	getDecimalExpansion(200): 0.49999999990000000005999999996400000002159999998704000000777599999533440000279935999832038400100776959939533824036279705578232176653060694008163583595101849842938890094236665943458000433925199739644880
	 *	forceToString(): 2500000001/5000000003
	 *	toTeXString(): \frac{2,500,000,001}{5,000,000,003}
	 *	isRecurring(): false
	 *	isRational(): true
	 *	getPeriod(): 0
	 *	factorial(): 0.88622692545275801364908374167057259139877472806119
	 *	factorize(): [2500000001, null, 149, 33557047]
	 *	getRecurring()[0]: 0.5
	 *	getRecurring()[1]: 0
	 *	toContinuedFraction(): [0, 2, 2500000001]
	 * 	toEgyptianFraction(): //Infinite loop
	 * </pre>
	 * 
	 * But if the fraction is created using:<br>
	 * <br>
	 * 
	 * <code>BigFration f = new BigFraction("0.5")&semi;</code><br>
	 * <br>
	 * 
	 * The output is:
	 * 
	 * <pre>
	 *	toString(): [ 1, 2 ]
	 *	getDecimalExpansion(200): 0.5
	 *	forceToString(): 1/2
	 *	toTeXString(): \frac{1}{2}
	 *	isRecurring(): false
	 *	isRational(): true
	 *	getPeriod(): 0
	 *	factorial(): 0.88622692545275801364908374167057259139877472806119
	 *	factorize(): [1, null, 2]
	 *	getRecurring()[0]: 0.5
	 *	getRecurring()[1]: 0
	 *	toContinuedFraction(): [0, 2]
	 *	toEgyptianFractions(): [[ 1, 2 ]]
	 * </pre>
	 * 
	 * So it is recommended that user should avoid creating non-integer fractions
	 * this way.
	 * 
	 * @param n the value to be parsed.
	 * @return a {@code BigFraction} whose accuracy is 1e-10.
	 * @throws ArithmeticException if any of the above condition mentioned returns
	 *                             true.
	 * @implNote This implementation uses the
	 *           {@linkplain #ianRichardsAlgorithm(BigDecimal, BigDecimal) Ian
	 *           Richard's} fast algorithm to parse decimals to integer fractions.
	 */
	public static BigFraction valueOf(Number n) throws ArithmeticException {
		if (n instanceof Long || n instanceof Integer || n instanceof BigInteger || n instanceof Short
				|| n instanceof Byte)
			return new BigFraction(new BigInteger(n.toString()), BigInteger.ONE);
		else if (n instanceof Float || n instanceof Double || n instanceof BigDecimal) {
			if (n.equals(Float.NaN) || n.equals(Double.NaN) || n.equals(Float.NEGATIVE_INFINITY)
					|| n.equals(Float.POSITIVE_INFINITY) || n.equals(Double.NEGATIVE_INFINITY)
					|| n.equals(Double.POSITIVE_INFINITY))
				throw new ArithmeticException("NaN values not supported");
			return new BigFraction(new BigDecimal(n.toString()), null, BigInteger.valueOf(Long.MAX_VALUE),
					DEFAULT_ACCURACY, 0);
		}
		return new BigFraction(new BigDecimal(String.valueOf(n.doubleValue())), null,
				BigInteger.valueOf(Long.MAX_VALUE), DEFAULT_ACCURACY, 0);
	}

	/*
	 * Date: 31 May 2022-----------------------------------------------------------
	 * Time created: 16:18:20--------------------------------------------
	 */
	/**
	 * Attempts to parse and convert the non-negative arguments into a
	 * {@code BigFraction} whose {@link #isRecurring()} and {@link #isRational()}
	 * returns {@code true}. This method provides an interface for creating a
	 * {@code BigFraction} with decimals and still have recurring digits.
	 * 
	 * <pre>
	 * BigFraction f = BigFraction.fromRecurringDecimal("3".toCharArray(), 0, 1);
	 * PrintStream out = System.out;
	 * out.println("toString(): " + f.toString());
	 * out.println("forceToString(): " + f.forceToString());
	 * out.println("toTeXString(): " + f.toTeXString('.', ',', ' ', 3, f.getNumerator().compareTo(f.getDenominator()) > 0));
	 * out.println("getPeriod(): " + f.getPeriod());
	 * out.println("getDecimalExpansion(200): " + f.getDecimalExpansion(200));
	 * out.println("getFraction(): " + f.getFraction());
	 * out.println("isRational(): " + f.isRational());
	 * out.println("isRecurring(): " + f.isRecurring());
	 * out.println("getRecurring()[0]: " + f.getRecurring()[0]);
	 * out.println("getRecurring()[1]: " + f.getRecurring()[1]);
	 * </pre>
	 * 
	 * Output is:
	 * 
	 * <pre>
	 * toString(): [ 1, 3 ]
	 * forceToString(): 1/3
	 * toTeXString(): \frac{1}{3}
	 * getPeriod(): 1
	 * getDecimalExpansion(200): 0.33333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333
	 * getFraction(): 0.33333333333333333333333333333333333333333333333333
	 * isRational(): true
	 * isRecurring(): true
	 * getRecurring()[0]: 0
	 * getRecurring()[1]: 3
	 * </pre>
	 * <p>
	 * The value to be parsed is represented by it's mantissa field which is
	 * represented by a char array, whereby each index contains a decimal digit
	 * (0-9) and represents it's index in the mantissa without it's integer or
	 * decimal point. For example: the recurring rational {@code 0.142857...} has
	 * the mantissa as a char array <code>{ '1', '4', '2', '8', '5', '7' }</code>.
	 * No sign, integer part or point is added. Note that the char array argument
	 * does not only contain the recurring part of the mantissa but all the digits
	 * in the mantissa: from the most significant digit to the last digit of the
	 * recurring part of the mantissa. For example: the value {@code 0.03(678)...}
	 * in which {@code 678} is the recurring part of the mantissa, users can create
	 * an array starting from the first digit of the mantissa (0 in this case) up to
	 * the last digit of the recurring part (which is 8 in this case). Any
	 * additional digit recurring to be ignored can be specified in the last 2
	 * argument by specifying the start index of the recurring digit(s) within the
	 * given char array (whose index is 2 in this case) and a period (number of
	 * digit(s)) in the recurring part of the mantissa (the periodicity in this case
	 * is 3). All other values after the recurring region are discarded and only the
	 * valid region is parsed and returned.
	 * <p>
	 * If the startIndex value is found to be less than 0, an
	 * {@code java.lang.IllegalArgumentException} is thrown.<br>
	 * <br>
	 * If the periodicity is equal to or less than 0, an
	 * {@code IndexOutOfBoundsException} is thrown.<br>
	 * <br>
	 * If the startIndex + period is found to be greater than the length of what
	 * remains of the truncated array (truncated because all digits after the last
	 * recurring digit is discarded), an {@code ArrayIndexOutOfBoundsException} is
	 * thrown.
	 * <p>
	 * Note that the periodicity specified is not necessarily the value returned by
	 * {@link BigFraction#getPeriod()} parse result may not converge. For example,
	 * an attempt to use ambiguous value and periodicity such as:
	 * 
	 * <pre>
	 * BigFraction f = BigFraction.fromRecurringDecimal("5555".toCharArray(), 0, 4) & semi;
	 * </pre>
	 * 
	 * will return a value equal to <sup>5</sup>/<sub>9</sub> even though such a
	 * value may have a periodicity returned by ({@link BigFraction#getPeriod()}) of
	 * 1 and a recurring digit of 5 (which was not intended).
	 * <p>
	 * For full value parsing (integer and non-recurring parts) use
	 * {@link Utility#fromDecimal(BigDecimal, String)}.
	 * 
	 * @param decimal     a char array of decimal digits (0-9) in the exact order
	 *                    that they appear in the mantissa, with each index of the
	 *                    array to a single digit. For example: the decimal
	 *                    {@code 0.142857...} will have
	 *                    <code>{ '1', '4', '2', '8', '5', '7' }</code> as it's
	 *                    argument. Note that since this is a recurring decimal, the
	 *                    mantissa may be repeated as many times as the user wants,
	 *                    since the pipeline will use only the exact number of digit
	 *                    it needs. No sign (such as - or +), integer part or
	 *                    decimal point should be added.
	 * 
	 * @param startIndex  the index of the first digit of the recurring region
	 *                    within the specified char array. For example: the decimal
	 *                    {@code 0.463465...} has {@code 465} as it's recurring
	 *                    region, therefore, this argument will be {@code 3} because
	 *                    the mantissa is
	 *                    <code>{ '4', '6', '3', '4', '6', '5' }</code> and the
	 *                    first digit of the recurring region is {@code 4} (the
	 *                    second four) which is at index 3.
	 * 
	 * @param periodicity (also known as the exclusive end index) the number of
	 *                    digits in the recurring region.
	 * 
	 * @return a {@link #BigFraction(BigInteger, BigInteger) rational BigFraction}
	 *         whose value is <code>&gt; 0</code> but <code>&lt; 1</code> with the
	 *         given recurring digits and period.
	 * 
	 * @throws NumberFormatException          if decimal cannot be parsed to a valid
	 *                                        number.
	 * 
	 * @throws IndexOutOfBoundsException      if <code>period &lt;= 0</code>.
	 * 
	 * @throws ArrayIndexOutOfBoundsException if
	 *                                        <code>startIndex + period &gt; decimal.length</code>
	 *                                        when the char array has be parsed and
	 *                                        truncated.
	 * 
	 * @throws IllegalArgumentException       if <code>startIndex &lt; 0</code>.
	 */
	public static BigFraction fromRecurringDecimal(char[] decimal, int startIndex, int periodicity)
			throws NumberFormatException, IndexOutOfBoundsException, ArrayIndexOutOfBoundsException,
			IllegalArgumentException {
		if (Pattern.compile("(\\+|-|\\.)").matcher(String.valueOf(decimal)).find())
			new mathaid.calculator.base.value.ValueFormatException(ExceptionMessage.CHAR_NOT_VALID,
					String.valueOf(decimal));
		if (startIndex < 0)
			new mathaid.IllegalArgumentException(ExceptionMessage.NEGATIVE_INPUT_DETECTED, Math.abs(startIndex));
		else if (periodicity <= 0)
			new mathaid.IndexBeyondLimitException(periodicity, 1);
		decimal = Arrays.copyOf(decimal, startIndex + periodicity);
		if (startIndex + periodicity > decimal.length)
			new mathaid.ArraySizeException(ExceptionMessage.INDEX_OUT_BOUNDS, startIndex + periodicity, decimal.length);
		if (startIndex > 0) {
			BigInteger scale1 = BigInteger.TEN.pow(periodicity + startIndex);
			BigInteger scale2 = scale1.divide(BigInteger.TEN.pow(periodicity));
			BigInteger scaledMantissa1 = new BigInteger(new String(decimal));
			BigInteger scaledMantissa2 = new BigInteger(new String(decimal)).multiply(scale2).divide(scale1);
			return new BigFraction(scaledMantissa1.subtract(scaledMantissa2), scale1.subtract(scale2)).toLowestTerms();
		}
		BigInteger scale = BigInteger.TEN.pow(periodicity + startIndex);
		return new BigFraction(new BigInteger(new String(decimal)), scale.subtract(BigInteger.ONE)).toLowestTerms();
	}

	/*
	 * Date: 9 Sep 2020-----------------------------------------------------------
	 * Time created: 15:42:16--------------------------------------------
	 */
	/**
	 * Interfaced method to provide support for {@code Fraction}
	 * 
	 * @param f the fraction to be parsed
	 * @return a {@code BigFraction} that is the same as the argument
	 * @throws ArithmeticException
	 */
	static BigFraction valueOf(Fraction f) throws ArithmeticException {
		int sign = f.getSignum();
		f = f.abs();
		long num = f.getNumerator();
		long denom = f.getDenominator();
		long whole = f.getQuotient();

		BigInteger num2 = BigInteger.valueOf(denom * whole + num);
		BigInteger denom2 = BigInteger.valueOf(denom);

		return new BigFraction(num2, denom2).multiply(valueOf(sign));
	}

	/*
	 * Date: 22 Nov 2020-----------------------------------------------------------
	 * Time created: 15:23:30--------------------------------------------
	 */
	/**
	 * <p>
	 * Calculates <b><i>B</i></b><sub>n</sub> and returns a {@code BigFraction}.
	 * </p>
	 * <p>
	 * This is an unoptimised algorithm that calculates a bernoulli number and
	 * returns the answer as a rational number. This algorithm calculates
	 * <b><i>B</i></b><sub>1</sub>= 1;
	 * </p>
	 * 
	 * @param n the index of the bernoulli number to be calculated.
	 * @return a fraction representing the bernouli number.
	 * @throws ArithmeticException if n is less than 0
	 */
	public static BigFraction computeBernoulliNumber(byte n) {
		if (n < 0)
			new MathematicalException(ExceptionMessage.NUMBER_TOO_BIG);
		else if (n == 0)
			return BigFraction.ONE;
		else if (n == 1)
			return BigFraction.HALF;
		else if (n > 1 && n % 2 != 0)
			return BigFraction.ZERO;
//		final double tolerance = 1.0e-23d;
		BigFraction[] b = new BigFraction[n + 1];
		for (int m = 0; m <= n; m++) {
			b[m] = valueOf(1).divide(m + 1);
			for (int j = m; j >= 1; j--) {
				b[j - 1] = b[j - 1].subtract(b[j]).multiply(j);
			}
		}
		return b[0];
	}

	////////////////////////////////////////////////////////////
	////////////////// Field declarations /////////////////////
	//////////////////////////////////////////////////////////

	/**
	 * The decimal expansion of the common fraction
	 */
	private final BigDecimal decimalFraction;
	/**
	 * The precision scale of the conversion engine
	 */
	private final BigDecimal accuracy;// 1.0e-10 seems to be optimal

	/**
	 * The numerator of the common fraction.
	 *
	 */
	private final BigInteger numerator;
	/**
	 * The denominator of the common fraction
	 */
	private final BigInteger denominator;

	/**
	 * Confirms whether the rational constructor (i.e the non decimal expansion
	 * constructor) was used in constructing this object.
	 */
	private final boolean rationalConstructor;

	/**
	 * An object that internally rounds the {@link BigFraction} object.
	 */
	private final MathContext context;
	/**
	 * <p>
	 * An {@code Helper} that caches values to prevent costly recalculations.
	 * </p>
	 * <p>
	 * A {@code Helper} is a private inner class which allows for calculation of
	 * {@link #isRational()} and {@link #getRecurring()} once, and then caches the
	 * calculation result inside it's fields. When subsequent calls are made to
	 * these methods, the value of the stored fields are used. This is done only if
	 * these particular methods are called repeatedly.<br>
	 * The advantages of this is that when these methods are called more than once
	 * for the instance, a value is returned, as opposed to the case where each time
	 * these methods are called a computation takes place
	 * </p>
	 */
	private final Helper helper;

	////////////////////////////////////////////////////////////////////////
	////////////////// Overridden methods (java.lang) /////////////////////
	//////////////////////////////////////////////////////////////////////

	/*
	 * Most Recent Date: 3 Sep 2020-----------------------------------------------
	 * Most recent time created: 13:14:57--------------------------------------
	 */
	/**
	 * Compare the argument to this and returns -1, 0 or 1 as this is less than,
	 * equal to and greater than the argument. This should be used in preference to
	 * {@code equals(Object)} to check for equality.
	 * 
	 * @param fraction a {@code BigFraction} to be compared with this.
	 * @return -1, 0 or 1 as this is less than, equal to and greater than the
	 *         argument
	 */
	@Override
	public int compareTo(BigFraction fraction) {
		if (isZero())
			return fraction.signum();
		else if (fraction.isZero())
			return signum();
		else if (decimalFraction.toBigInteger().compareTo(fraction.decimalFraction.toBigInteger()) != 0)
			return decimalFraction.toBigInteger().compareTo(fraction.decimalFraction.toBigInteger());

		BigFraction thisOne = this;

		BigInteger lcm = thisOne.lcm(fraction);

		BigInteger n1 = lcm.divide(thisOne.denominator).multiply(thisOne.numerator);
		BigInteger n2 = lcm.divide(fraction.denominator).multiply(fraction.numerator);

		return n1.compareTo(n2);
	}

	/*
	 * Most Recent Date: 3 Sep 2020-----------------------------------------------
	 * Most recent time created: 13:18:26--------------------------------------
	 */
	/**
	 * Compare this to the given object and returns true if this == x or false if
	 * otherwise. The comparison done is a deep comparison which means that an
	 * object is equal to this if and only if it equals all the fields of this.
	 * 
	 * @param x the object to be compared for equality.
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean equals(Object x) {
		if (x instanceof BigFraction) {
			BigFraction f = (BigFraction) x;
			return compareTo(f) == 0 && decimalFraction.equals(f.decimalFraction) && accuracy.equals(f.accuracy)
					&& numerator.equals(f.numerator) && denominator.equals(f.denominator);
		}
		return false;
	}

	/*
	 * Most Recent Date: 3 Sep 2020-----------------------------------------------
	 * Most recent time created: 13:22:09--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return new BigDecimal(numerator).divide(new BigDecimal(denominator)).hashCode();
	}

	/*
	 * Most Recent Date: 3 Sep 2020-----------------------------------------------
	 * Most recent time created: 13:22:33--------------------------------------
	 */
	/**
	 * The string representation of this object. It uses the following
	 * notation:<blockquote> <code>[ integer, numerator, denominator ]</code>
	 * </blockquote> if it is a fraction and it is rational or else return the
	 * decimal expansion of the common fraction. But if it is an integer, then the
	 * integer is returned.
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (isInteger())
			return decimalFraction.toBigInteger().toString();
		else if (!isRational())
			return decimalFraction.toString();
		BigInteger[] b = toMixed();
		StringBuilder sb = new StringBuilder("[ ");
		if (!isProper()) {
			sb.append(b[0]);
			sb.append(", ");
		}
		sb.append(b[1]);
		sb.append(", ");
		sb.append(b[2]);
		sb.append(" ]");
		return sb.toString();
	}
}
