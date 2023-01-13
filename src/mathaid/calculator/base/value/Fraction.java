package mathaid.calculator.base.value;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

/**
 * Immutable common fraction used to represent decimal floating point in common
 * fractions format. This is primarily a representation of a rational numbers.
 * Care should be taken when converting such numbers as instances of this class
 * can only work with primitive values such as double and long. Note that for
 * floating point values longer than the specified {@code tolerance} value,
 * conversion may be inaccurate. <b>e.g</b>
 *
 * <pre>
 * Fraction myFraction = new Fraction(Math.PI); // Uses the default tolerance.
 * myFraction.toIntegerFraction();
 * Sytem.out.println(myFraction);
 * </pre>
 *
 * The output of this fraction is {@literal 3/1/7} which is an inaccurate value
 * of pi. Therefore the tolerance should be taken into account when creating
 * instances of this class. None of the methods of this class mutates this
 * object, it is therefore important that sub-classers beware of methods which
 * mutates this object, methods such as those that have void as return type.
 * This class is for double values. Many of the methods(e.g aritmetic operations
 * such as {@link #add(Fraction)}, {@link #subtract(Fraction)},
 * {@link #divide(Fraction)} and {@link #multiply(Fraction)}), will return a
 * {@code Fraction} object for which {@code isMixed()} is {@code false}. The
 * maximum value of the denominator in this class is limited to the maximum
 * value of java's <code>long</code><br>
 * <br>
 * <b>PLEASE NOTE:</b> There is a misinterpretation of the word
 * <i><b>quotient</b></i> in this class. When you see the word <i>quotient</i>
 * in this class, it means a whole number attached to a fraction, i.e the whole
 * number part of the fraction. Also note the documentation of this class uses
 * pseudocode to explain certain methods.
 *
 * @author Oruovo Anthony Etineakpopha
 */
public class Fraction extends Number implements Comparator<Fraction>, Comparable<Fraction> {
	/* An auto generated serial. */
	private static final long serialVersionUID = 1L;

	/**
	 * The default tolerance used by this {@code Fraction}
	 * <p>
	 * <b>Value</b><br>
	 * <code>1.0e-5</code>
	 */
	private static final double DEFAULT = 1.0E-5;

	/*
	 * Constants declarations
	 */
	/**
	 * A {@code Fraction} constant for {@code 0}.
	 */
	public static final Fraction ZERO = new Fraction(0L, 1L);
	/**
	 * A {@code Fraction} constant for <code>1</code>.
	 */
	public static final Fraction ONE = new Fraction();
	/**
	 * A {@code Fraction} constant for <code>1 / 2</code>.
	 */
	public static final Fraction HALF = new Fraction(1L, 2L);
	/**
	 * A {@code Fraction} constant for <code>1 / 3</code>.
	 */
	public static final Fraction ONE_THIRD = new Fraction(1L, 3L);
	/**
	 * A {@code Fraction} constant for <code>1 / 4</code>.
	 */
	public static final Fraction ONE_FOURTH = new Fraction(1L, 4L);
	/**
	 * A {@code Fraction} constant for <code>1 / 5</code>.
	 */
	public static final Fraction ONE_FIFTH = new Fraction(1L, 5L);
	/**
	 * A {@code Fraction} constant for <code>1 / 6</code>.
	 */
	public static final Fraction ONE_SIXTH = new Fraction(1L, 6L);
	/**
	 * A {@code Fraction} constant for <code>1 / 7</code>.
	 */
	public static final Fraction ONE_SEVENTH = new Fraction(1L, 7L);
	/**
	 * A {@code Fraction} constant for <code>1 / 8</code>.
	 */
	public static final Fraction ONE_EIGTH = new Fraction(1L, 8L);
	/**
	 * A {@code Fraction} constant for <code>1 / 9</code>.
	 */
	public static final Fraction ONE_NINTH = new Fraction(1L, 9L);
	/**
	 * A {@code Fraction} constant for <code>1 / 10</code>.
	 */
	public static final Fraction TITHE = new Fraction(1L, 10L);
	/**
	 * A {@code Fraction} constant for <code>2 / 5</code>.
	 */
	public static final Fraction TWO_FIFTH = new Fraction(2L, 5L);
	/**
	 * A {@code Fraction} constant for <code>2 / 7</code>.
	 */
	public static final Fraction TWO_SEVENTH = new Fraction(2L, 7L);
	/**
	 * A {@code Fraction} constant for <code>2 / 9</code>.
	 */
	public static final Fraction TWO_NINTH = new Fraction(2L, 9L);
	/**
	 * A {@code Fraction} constant for <code>2</code>.
	 */
	public static final Fraction TWO = new Fraction(2L, 0L, 1L);
	/**
	 * A {@code Fraction} constant for <code>10</code>.
	 */
	public static final Fraction TEN = new Fraction(10L, 0L, 1L);

	////////////////////////////////////////////////////////////
	///////////////////// Constructors ////////////////////////
	//////////////////////////////////////////////////////////

	/**
	 * Initialises the {@code Fraction} object with a quotient that is equal to 1, a
	 * numerator that is 1, a denominator that is 1 and uses the default tolerance
	 * of {@code 1.0e-2}.
	 */
	private Fraction() {
		this(1L, 1L);
	}

	/*
	 * 
	 * Date: 19 Mar 2020---------------------------------------------------- Time
	 * created: 08:40:57--------------------------------------------
	 */
	/**
	 * Lazily constructs a fraction from the argument. Could be used to clone
	 * fractions, subclasses implement java.lang.Cloneable.
	 * 
	 * @param fraction the fraction from which this {@code Fraction} are
	 *                 Initialised.
	 */
	private Fraction(Fraction fraction) {
		quotient = fraction.quotient;
		numerator = fraction.numerator;
		denominator = fraction.denominator;
		this.fraction = fraction.fraction;
		tolerance = fraction.tolerance;
		signum = fraction.signum;
	}

	/**
	 * Constructs a {@code Fraction}, object using a {@code String} in decimal
	 * format and uses the default tolerance of {@code 1.0e-2}. The converted
	 * fraction is converted to a common fraction at this constructor. Only decimal
	 * integers or floating-point numbers are allowed.
	 *
	 * @see {@code toIntegerFraction()}<br>
	 *      {@code setTolerance(double)}
	 * @throws NullPointerException  if {@code fraction} is null.
	 * @throws NumberFormatException if {@code fraction} contains
	 *                               (!,%,$,{@literal #}, etc).
	 * @param fraction The fraction to be converted.
	 */
	private Fraction(String fraction) throws NumberFormatException {
		this(Double.parseDouble(fraction));
	}

	/**
	 * Constructs an common fraction using the specified fraction and the default
	 * tolerance of {@code 1.0e-2}. Calling this constructor converts the double
	 * value to a common fraction, i.e a fraction with a whole number, a numerator,
	 * and a denominator. This constructor's argument can be floating-point or
	 * integer.
	 *
	 * @see {@link #toIntegerFraction(double)}, {@link #setTolerance(double)}
	 *
	 * @param fraction The fraction which determines the whole number, numerator,
	 *                 denominator and sign of this {@code Fraction}.
	 */
	public Fraction(double fraction) {
		this(fraction, DEFAULT);
	}

	/**
	 * Initializing the a fraction object with the provided tolerance level. The
	 * tolerance level must be greater than 0 and less than 1, or else an
	 * {@code IllegalArgumentException} will be thrown. This constructor should be
	 * preferred over others when accuracy of the conversion is of utmost
	 * importance, because the tolerance level can increase or decrease accuracy of
	 * the conversion. The lesser the tolerance, the more accurate and expensive the
	 * conversion is. The greater the tolerance the lesser the accuracy and cost of
	 * the conversion. "Cost" here refer to the performance penalties incurred.
	 *
	 * @see {@code toIntegerFraction()}
	 * @throws IllegalArgumentException if tolerance <=0 or >=1.
	 * @param fraction  The fraction to be converted.
	 * @param tolerance The tolerance which constrains the accuracy of the
	 *                  conversion.
	 */
	public Fraction(double fraction, double tolerance) throws IllegalArgumentException {
		if (tolerance >= 1 || tolerance <= 0)
			throw new IllegalArgumentException("illegal tolerance");
		this.fraction = fraction;
		this.tolerance = tolerance;
		signum = (byte) Math.signum(fraction);
		long[] fs;// Fractions
		fs = toIntegerFraction(fraction);
		quotient = fs[0];
		numerator = fs[1];
		denominator = fs[2];
	}

	/*
	 * Date: 21 Mar 2020---------------------------------------------------- Time
	 * created: 12:50:34--------------------------------------------
	 */
	/**
	 * Constructs a fraction which is either improper or irreducible.
	 * 
	 * 
	 * @param numerator   the numerator of the fraction.
	 * @param denominator the denominator of the fraction.
	 * @see #Fraction(long, long, long)
	 */
	public Fraction(long numerator, long denominator) {
		this(0L, numerator, denominator);
	}

	/**
	 * <p>
	 * Constructs a {@code Fraction} object with the provided quotient, numerator,
	 * denominator and uses the default tolerance of {@code 1.0e-2}. Only the
	 * quotient is allowed to be negative, the numerator cannot be negative as long
	 * as quotient is less than or greater than 1 otherwise an
	 * {@code IllegalArgumentException} is thrown, and the denominator cannot be
	 * less than 1 otherwise an {@code IllegalArgumentException} is thrown.<br>
	 * A {@code Fraction} object constructed with this constructor does not need to
	 * be coverted, it is already an integer fraction! It can however be converted
	 * to mixed and improper via {@link #toMixedFraction()} and
	 * {@link #toImproperFraction()} respectively.
	 * </p>
	 * To prevent the nasty cases of overflows, this constructor and all
	 * constructors that make a call to it will throw an {@code ArithmeticException}
	 * if
	 * <code>abs(numerator) &gt;= {@link Long#MAX_VALUE} - 2 || denominator &gt;= {@link Long#MAX_VALUE}</code>
	 * 
	 * @throws IllegalArgumentException if numerator < 0 and quotient != 0,<br>
	 *                                  if numerator = 0 and quotient = 0 and
	 *                                  denominator is > 1,<br>
	 *                                  if quotient != 0 and the absolute value of
	 *                                  the numerator is > or = 1 and denominator <
	 *                                  2,<br>
	 *                                  if quotient != 0 and the absolute value of
	 *                                  the numerator is > denominator,<br>
	 *                                  if denominator < 0 or = 0.
	 * @param quotient    the quotient of the fraction.
	 * @param numerator   the numerator of the fraction
	 * @param denominator the denominator which is greater than 1; <br>
	 *                    </dd> <br>
	 *                    <b>See Also:</b><br>
	 *                    <dd>{@link #toIntegerFraction(double)}<br>
	 *                    {@link #setTolerance(double)}</dd>
	 */
	public Fraction(long quotient, long numerator, long denominator) throws IllegalArgumentException {
		if ((numerator < 0 && quotient != 0) /* || (numerator == 0 && quotient == 0 && denominator > 1) */)
			throw new IllegalArgumentException(
					"numerator cannot be negative or equal to zero while there is a quotient.");
		if (quotient != 0 && Math.abs(numerator) >= 1 && denominator < 2)
			throw new IllegalArgumentException("denominator cannot be less than 2 while there numerator > 0.");
		if (quotient != 0 && Math.abs(numerator) > denominator)
			throw new IllegalArgumentException("Cannot be mixed and improper at once");
		if (denominator == Long.MAX_VALUE || denominator == Long.MIN_VALUE)
			throw new ArithmeticException("overflow in denominator");
		if (numerator >= Long.MAX_VALUE - 2 || numerator <= Long.MIN_VALUE + 2)
			throw new ArithmeticException("overflow in numerator");

		if ((denominator < /* = */ 0))
			denominator = Math.abs(denominator);
		this.quotient = quotient;
		this.numerator = numerator;
		this.denominator = denominator;

		signum = (byte) Math.signum(quotient != 0 ? quotient : numerator);
		fraction = (quotient == 0 ? (double) ((double) Math.abs(numerator) / (double) denominator)
				: ((double) Math.abs(numerator) / (double) denominator) + Math.abs(quotient)) * signum;
		tolerance = DEFAULT;
	}

	////////////////////////////////////////////////////////////
	/////////////////////// Accessors /////////////////////////
	//////////////////////////////////////////////////////////

	/**
	 * Returns the floating-point value of this {@code Fraction} as double value.
	 *
	 * @return the decimal floating point value of the {@code Fraction} object
	 */
	public double getFraction() {
		return fraction;
	}

	/**
	 * Returns the numerator of this {@code Fraction} object.
	 *
	 * @return the numerator of the fraction.
	 */

	public long getNumerator() {
		return numerator;
	}

	/**
	 * Returns the denominator if this {@code Fraction} object.
	 *
	 * @return the denominator.
	 */

	public long getDenominator() {
		return denominator;
	}

	/**
	 * Returns the whole number part of the quotient of this {@code Fraction} object
	 * or 0 if there is no integer part in the quotient.
	 *
	 * @return the whole number or 0 if none.
	 */
	public long getQuotient() {
		return quotient;
	}

	/**
	 * Returns the tolerance of this {@code Fraction} object, which is used for the
	 * precision of the conversion of this {@code Fraction}
	 *
	 * @return the tolerance.
	 * @see #setTolerance(double)
	 */
	public double getTolerance() {
		return tolerance;
	}

	/**
	 * Return the mathematical sign of the fraction in this {@code Fraction} object.
	 *
	 * @return the signum.
	 */
	public byte getSignum() {
		return isZero() ? (byte) 0 : signum;
	}

	////////////////////////////////////////////////////////////
	//////////////////////// Checks ///////////////////////////
	//////////////////////////////////////////////////////////

	/**
	 * Return whether this {@code Fraction} is a mixed fraction. A mixed fraction is
	 * a fraction with a whole number (whose absolute value is greater than 0). Even
	 * if calling {@link #toMixedFraction()} returns a different {@code Fraction}
	 * object, this method may return false if the whole number is {@code 0}.
	 *
	 * @return true if this {@code Fraction} is a mixed fraction,<br>
	 *         false otherwise.
	 */
	public boolean isMixed() {
		// this.toIntegerFraction();
		return this.quotient != 0;
	}

	/**
	 * Return whether this {@code Fraction} is a proper fraction. A proper fraction
	 * is a fraction whose numerator is lesser in magnitude than its denominator's.
	 * If {@code isMixed()} returns {@code true} this method will return
	 * <code>false</code>.
	 *
	 * @return true if this {@code Fraction} is a proper fraction,<br>
	 *         false otherwise.
	 */
	public boolean isProper() {
		if (isMixed())
			return false;
		return Math.abs(quotient) == 0 && (Math.abs(numerator) < denominator);
	}

	/**
	 * Return whether this {@code Fraction} is a whole number, and lacks the
	 * fractional parts.
	 *
	 * @return true if this {@code Fraction} is a whole number,<br>
	 *         false otherwise.
	 */
	public boolean isInteger() {
		return isInteger(fraction);
	}

	/////////////////////////////////////////////////////////////
	/////////////////// Arithmetic methods /////////////////////
	///////////////////////////////////////////////////////////

	/**
	 * Returns the sum of this {@code Fraction} and the addend as an improper
	 * fraction.
	 * <p>
	 * If the tolerance of both operands are unequal, the {@code Fraction} object
	 * returned will have
	 * {@code tolerance == Math.min(this.tolerance, addend.tolerance)} equal true.
	 * using an object for {@code addend} whose {@code tolerance == this.tolerance}
	 * is less costly (in other words will compute faster).
	 * </p>
	 *
	 * @param addend the fraction to added.
	 * @return the sum of the augend and the addend.
	 */
	public Fraction add(Fraction addend) {
		if (tolerance == addend.tolerance) {
//			long divisor = this.getLcm(addend);

			/* the lcm of both the augend and the addend's denominator */
			long lcm = getLcm(addend);
			/* normalizes a.k.a factorizes this.numerator */
			long numerator1 = (lcm / denominator) * toImproper().numerator;
			/* normalizes a.k.a factorizes addend.numerator */
			long numerator2 = (lcm / addend.denominator) * addend.toImproper().getNumerator();

			/* Do not return a mixed fraction for the sake of precision */
			return new Fraction(numerator1 + numerator2, lcm);
		}
		/* return this object only if both of their tolerance are unequal */
		return new Fraction(fraction + addend.fraction, Math.min(tolerance, addend.tolerance));
	}

	/**
	 * Returns {@code this - subtrahend} as an improper fraction.
	 * <p>
	 * If the tolerance of the operands are unequal, the {@code Fraction} object
	 * returned will have
	 * {@code tolerance == Math.min(this.tolerance, subtrahend.tolerance} equal
	 * true. using an object for {@code subtrahend} whose
	 * {@code tolerance == this.tolerance} is less costly.
	 * </p>
	 * 
	 * @param subtrahend the fraction to subtracted.
	 * @return the difference between {@code this} and the {@code subtrahend}.
	 */
	public Fraction subtract(Fraction subtrahend) {
		if (tolerance == subtrahend.tolerance) {
			long lcm = getLcm(subtrahend);
			long numerator1 = (lcm / denominator) * toImproper().numerator;
			long numerator2 = (lcm / subtrahend.denominator) * subtrahend.toImproper().getNumerator();

			return new Fraction(numerator1 - numerator2, lcm);

		}
		return new Fraction(fraction - subtrahend.fraction, Math.min(tolerance, subtrahend.tolerance));
	}

	/**
	 * Returns {@code this * multiplicand}. Note that this method will return an
	 * improper fraction.
	 * <p>
	 * If the tolerance of the operands are unequal, the {@code Fraction} object
	 * returned will have
	 * {@code tolerance == Math.min(this.tolerance, multiplicand.tolerance} equal
	 * true. Using an object for {@code multiplicand} whose
	 * {@code tolerance == this.tolerance} is less costly (in other words will
	 * compute faster).
	 * </p>
	 * 
	 * @param multiplicand the fraction to multiplied.
	 * @return the product of {@code this} and the {@code multiplicand}.
	 */
	public Fraction multiply(Fraction multiplicand) {
		if (compareTo(multiplicand) == 0)
			return exponentiate(2);
		if (tolerance == multiplicand.tolerance) {

			return (isZero() || multiplicand.isZero() ? ZERO
					: new Fraction(toImproper().numerator * multiplicand.toImproper().numerator,
							toImproper().denominator * multiplicand.toImproper().denominator));
		}
		// return new Fraction((double) fraction - multiplicand.fraction,
//					(double) Math.min((double) tolerance, (double) multiplicand.tolerance));
		return new Fraction(fraction * multiplicand.fraction, Math.min(tolerance, multiplicand.tolerance));
	}

	/**
	 * Returns {@code this * divisor}. Note that this method will return an improper
	 * fraction.
	 * <p>
	 * If the tolerance of the operands are unequal, the {@code Fraction} object
	 * returned will have
	 * {@code tolerance == Math.max(this.tolerance, divisor.tolerance} equal true.
	 * using an object for {@code divisor} whose {@code tolerance == this.tolerance}
	 * is less costly (in other words will compute faster).
	 * </p>
	 *
	 * @param divisor the fraction which divides this.
	 * @return the quotient of {@code this} and the {@code divisor}.
	 */
	public Fraction divide(Fraction divisor) {
		if (divisor.isZero())
			throw new ArithmeticException("Undefined! Cannot divide by zero");
		if (tolerance == divisor.tolerance) {
			return multiply(divisor.toReciprocal());
		}
		return new Fraction(fraction - divisor.fraction, Math.min(tolerance, divisor.tolerance));
	}

	/*
	 * 
	 * Date: 21 Mar 2020---------------------------------------------------- Time
	 * created: 13:27:24--------------------------------------------
	 */
	/**
	 * Returns a {@code Fraction} which is <code> this<sup>exponent</sup></code> as
	 * a non-mixed fraction if the {@code exponent} is positive else a possibly
	 * mixed fraction or a non-divible fraction.
	 * 
	 * @param exponent the power this {@code Fraction} is raised to.
	 * @return <code>this<sup>exponent</sup></code>
	 */
	public Fraction exponentiate(int exponent) {
		return pow(this, exponent);
	}

	/*
	 * 
	 * Date: 21 Mar 2020---------------------------------------------------- Time
	 * created: 16:11:37--------------------------------------------
	 */
	/**
	 * Returns a {@code Fraction} which is <code> this<sup>exponent</sup></code> as
	 * a possibly mixed fraction or a non-divible fraction.
	 * 
	 * @param exponent a non-mixed fraction object
	 * @return <code>this<sup>exponent</sup></code>
	 */
	public Fraction exponentiate(Fraction exponent) {
//		exponent = exponent.toImproper();
		return pow(this.toImproper(), exponent.fraction);
	}

	/**
	 * Returns the absolute value of this {@code Fraction}, i.e the positive value
	 * of this {@code Fraction} as a mixed fraction or a non-divible fraction.
	 *
	 * @return the absolute value of <code>this</code>
	 */
	public Fraction abs() {
		return new Fraction(Math.abs(getQuotient()), Math.abs(getNumerator()), Math.abs(getDenominator()));
	}

	/**
	 * Negates this {@code Fraction} and returns an improper fraction or a
	 * non-divisible fraction if this {@code Fraction} is improper else returns a
	 * mixed fraction. This will return a positive value if {@code this} is negative
	 * else it will return a negative value.
	 *
	 * @return a negative {@code Fraction} object if positive<br>
	 *         or a positive {@code Fraction} if otherwise.
	 */
	public Fraction negate() {
		if (this.getQuotient() != 0)
			return new Fraction(getQuotient() * (-1), Math.abs(getNumerator()), getDenominator());
		return new Fraction(getNumerator() * (-1L), getDenominator());
	}

	////////////////////////////////////////////////////////////
	//////////////// Other utility methods ////////////////////
	//////////////////////////////////////////////////////////

	/*
	 * 
	 * Date: 21 Mar 2020---------------------------------------------------- Time
	 * created: 13:32:57--------------------------------------------
	 */
	/**
	 * Performs a reduction on this {@code Fraction} by decomposing the whole
	 * number, numerator and denominator to their respective lowest terms and
	 * returns a mixed fraction or a non-divisible fraction.
	 * 
	 * @return a mixed fraction or a non-divisible fraction.
	 */
	public Fraction toLowestTerm() {
		return setTolerance(tolerance);
	}

	/**
	 * Returns the reciprocal of {@code this} and returns an improper fraction or a
	 * non-divible fraction. A reciprocal is when a fraction's numerator becomes the
	 * denominator and vice-versa.
	 *
	 * @return a {@code Fraction} which is an reciprocal of this.
	 */
	public Fraction toReciprocal() {
		Fraction f = toImproper();
		return new Fraction(f.denominator * signum, Math.abs(f.numerator));
	}

	/**
	 * Creates a {@code Fraction} object by coverting this {@code Fraction} to an
	 * improper fraction.<br>
	 * An improper fraction is a fraction whose numerator's absolute is greater than
	 * it's denominator. If this is an indivisible fraction, i.e it can not be
	 * decomposed further, then it {@code this.equals(toImproper())} returns
	 * {@code true}
	 *
	 * @return a {@code Fraction} that is an improper representatin of this
	 *         {@code Fraction}.
	 */
	public Fraction toImproper() {
		if (isProper())
			return new Fraction(this);
		if ((!this.isProper()) && (!isMixed()))
			return new Fraction(this);
		return new Fraction(0,
				(((quotient != 0 ? Math.abs(quotient) : 1) * denominator) + Math.abs(numerator)) * signum, denominator);
	}

	/**
	 * Converts an improper fraction to mixed fraction. If the fraction is a proper
	 * fraction or it is already a mixed fraction, then {@code this} is returned.
	 *
	 * @return a {@code Fraction} object in mixed form.
	 */
	public Fraction toMixedFraction() {
		Fraction f = new Fraction(this);
		if (f.isMixed())
			return f;
		if (!f.isProper()) {
			long quotient = ((Math.abs(f.getQuotient())) + (Math.abs(f.getNumerator()) / f.getDenominator()))
					* f.signum;
			return new Fraction(quotient,
					Math.abs(f.getNumerator()) % f.getDenominator() * (quotient == 0 ? f.signum : 1),
					f.getDenominator());
		}
		return f;
	}

	/**
	 * Return a fraction with the provided tolerance level tolerance level for
	 * evaluating fractions. The tolerance for a decimal floating-point value is the
	 * scale or number of mantissa digits that will be used for calculating the
	 * whole number, numerator and denominator. The longer the tolerance the more
	 * accurate calculations are and the more memory calculations use too.<br>
	 * The {@code Fraction} object that is returned uses this {@code Fraction}'s
	 * decimal fraction.
	 *
	 * @throws IllegalArgumentException if {@code tolerance >= 1} or
	 *                                  {@code tolerance <= 0}.
	 *
	 * @param tolerance Sets the tolerance of the {@code Fraction} Object.
	 * @return a <code>Fraction</code> object with a tolerance that is euqal to the
	 *         argument
	 */
	public Fraction setTolerance(double tolerance) throws IllegalArgumentException {
		return new Fraction(this.fraction, tolerance);
	}

	/**
	 * Converts a proper fraction into an array of egyptian fractions using
	 * Sylvester's sequence.<br>
	 * An egyptian fraction is a fraction whose numerator is always 1.<br>
	 * Note: This method might return itself that contains null element(s) for the
	 * following reasons:<br>
	 * <ul>
	 * <li>If this {@code Fraction} is an integer.</li>
	 * <li>If {@code this.isMixed()} returns true.</li>
	 * <li>If {@code this.isProper()} returns false.</li>
	 * </ul>
	 *
	 * If {@code this.getNumerator() == 1}, and {@code isMixed()} returns false then
	 * this method returns an array containing just 1 element that is {@code this}.
	 * <br>
	 * If this fraction is mixed or is improper or is an integer then it will return
	 * an array of just 1 element which is itself.
	 *
	 * @return an array of fractions whose numerator = 1, and the sum of its
	 *         elements == this.
	 */
	public Fraction[] toEgyptianFractions() {
		/* Converts the fraction, in case it is an improper fraction. */
//		toIntegerFraction();

		ArrayList<Fraction> fractions = new ArrayList<>();
		int iteration = 0;
		Fraction fraction = abs();

		if (!isProper()) {
			fraction = fraction.toMixedFraction();
			fractions.add(Fraction.valueOf(fraction.getQuotient() + ""));
			fraction = fraction.subtract(fractions.get(0));
		} else if (isMixed()) {
			fractions.add(Fraction.valueOf(fraction.getQuotient() + ""));
			fraction = fraction.subtract(fractions.get(0));
		} else if (isInteger()) {
			return new Fraction[] { new Fraction(getQuotient(), getNumerator(), getDenominator()) };
		}
//			if (this.isInteger() || this.isMixed() || !this.isProperFraction())
//				return new Fraction[] { null };
		if (Math.abs(this.numerator) == 1 && (!isMixed()))
			/* If the fraction is already egyptian. */
			return new Fraction[] { new Fraction(getQuotient(), getNumerator(), getDenominator()) };
		do {
			/*
			 * Gets the reciprocal of the fraction rounded-down to an integer.
			 */
			double d = Math.floor((double) fraction.getDenominator() / (double) fraction.getNumerator()) + 1.0d;
			/*
			 * create a new fraction which is 1/d then add it to the list.
			 */
			Fraction f = new Fraction().divide(new Fraction((long) d, 0, 1));
			fractions.add(iteration, f);
			/*
			 * subtract the converted fraction from the original to get the difference then
			 * make the difference the current fraction to e evaluted.
			 */
			fraction = fraction.subtract(f);
			/*
			 * In case this is the last iteration and the reamaining fraction is egyptian.
			 */
			if (fraction.getNumerator() == 1)
				fractions.add(iteration + 1, fraction);
			iteration++;
			/*
			 * Continue the loop while the remaining fraction is not egyptian or 0;
			 */
		} while (fraction.numerator != 1 || fraction.isZero());
		Fraction[] fractionArray = new Fraction[iteration + 1];
		fractions.removeIf(isNull());
		fractions.trimToSize();
		/*
		 * dumps the list into an array
		 */
		fractionArray = fractions.toArray(fractionArray);
		return fractionArray;
	}

	/**
	 * Returns the highest common factor of both the numerator and the denominator.
	 *
	 * @return the highest common factor of this {@code Fraction}'s numerator and
	 *         denominator.
	 */
	public long getHcf() {
		return hcf(((quotient != 0 ? Math.abs(quotient) : 1) * denominator) + Math.abs(numerator), denominator);
	}

	/**
	 * Returns the lowest common multiple of this {@code Fraction}'s numerator and
	 * denominator.
	 *
	 * @return the lowest common multiple of the numerator and denominator.
	 */
	public long getLcm() {
		return lcm(((quotient != 0 ? Math.abs(quotient) : 1) * denominator) + Math.abs(numerator), denominator);
	}

	/**
	 * Return the lowest common multiple of {@code this.getDenominator()} and
	 * {@code fraction.getDenominator()}
	 *
	 * @param fraction - the value which is evaluated to get the lcm.
	 * @return the lcm of {@code this.getDenominator()} and
	 *         {@code fraction.getDenominator()}.
	 */
	public long getLcm(Fraction fraction) {
		return lcm(Math.abs(denominator), Math.abs(fraction.denominator));
	}

	/*
	 * Date: 22 May 2020-----------------------------------------------------------
	 * Time created: 10:41:12--------------------------------------------
	 */
	/**
	 * Returns this &times 100 i.e the percentage value of this as a double.
	 * 
	 * @return a double value which is the percentage value of this {@code Fraction}
	 */
	public double percent() {
		return multiply(new Fraction(100L, 1L)).fraction;
	}

	////////////////////////////////////////////////////////////
	/////////////////// Private methods ///////////////////////
	//////////////////////////////////////////////////////////

	/**
	 * Flag for a zero fraction.
	 * 
	 * @return true if this == 0 and false otherwise
	 */
	private boolean isZero() {
		return getQuotient() == 0 && getNumerator() == 0;
	}

	/**
	 * Method for detecting null in fraction list. I used this predicate in the
	 * {@link #toEgyptianFractions()} method.
	 * 
	 * @return a {@link Predicate} object.
	 */
	private Predicate<Fraction> isNull() {
		return (Fraction f) -> f == null;
	}

	////////////////////////////////////////////////////////////
	////////////////// Protected methods //////////////////////
	//////////////////////////////////////////////////////////

	/**
	 * Fully converts the {@code fraction} argument to a long array containing the
	 * whole number part at index 0, numerator part at index 1 and the denominator
	 * part at index 2 using the current tolerance. This method is called at
	 * constructors which take floating point numbers as arguments.<br>
	 * <br>
	 * The default tolerance is used unless it is set using the
	 * {@code setTolerance(double)}, or at the appropriate constructor.<br>
	 * Without calling this method, the fraction remains unconverted and calling its
	 * {@link #toString()}, {@link #hashCode()}, {@link #getNumerator()},
	 * {@link #getNumerator()}, {@link #getQuotient()}, {@link #getHcf()},
	 * {@link #getLcm()}, and {@link #getLcm(Fraction)} methods will yeild undefined
	 * results. This method cannot be overridden by subclasses.
	 * 
	 * @param fraction the fraction to be converted
	 * @return the whole number(if possible), the numerator and denominator in this
	 *         order in a long array.
	 */
	protected final long[] toIntegerFraction(double fraction) {
		if (Double.isNaN(fraction))
			return new long[] { 0, 0, 1 };
		String tmp = String.valueOf(fraction);
		if (tmp.contains("e") || tmp.contains("E")) {
			tmp = (long) (Math.floor(Math.abs(fraction))) + "";
		} else
			tmp = tmp.substring(0, tmp.indexOf('.'));
		/*
		 * (double) ((double) Math.abs(fraction) - (double)
		 * Math.abs(Double.parseDouble(tmp)));
		 */
		Double decimal = BigDecimal.valueOf(fraction).abs()
				.subtract(BigDecimal.valueOf(Math.abs(Double.parseDouble(tmp)))).doubleValue();
		Long[] fractionalElements = new Long[2];
		// double tolerance = 1.0E-2;
		double numerator1 = 1;
		double numerator2 = 0;
		double denominator1 = 0;
		double denominator2 = 1;
		double b = decimal;
//		byte signum = (byte) Math.signum(fraction);
		do {
			double a = Math.floor(b);
			double aux = numerator1;
			numerator1 = a * numerator1 + numerator2;
			numerator2 = aux;
			aux = denominator1;
			denominator1 = a * denominator1 + denominator2;
			denominator2 = aux;
			b = 1 / (b - a);
		} while (Math.abs(decimal - numerator1 / denominator1) > decimal * tolerance);
		fractionalElements[0] = (long) numerator1;
		fractionalElements[1] = (long) denominator1;
		long quotient, numerator, denominator;
		quotient = Math.abs(Long.parseLong(tmp));// (fraction[0] / fraction[1]);
		quotient *= quotient != 0 ? signum : 0;
		numerator = fractionalElements[0];// (fraction[0] - (Math.abs(quotient) * fraction[1]));
		numerator *= (quotient == 0) ? signum : 1;
		denominator = fractionalElements[1];
		return new long[] { quotient, numerator, denominator };
	}

	////////////////////////////////////////////////////////////
	/////////////////// Static methods ////////////////////////
	//////////////////////////////////////////////////////////

	/*
	 * 
	 * Date: 21 Mar 2020---------------------------------------------------- Time
	 * created: 13:27:29--------------------------------------------
	 */
	/**
	 * Returns the {@code base} raised to the {@code exponent}.<br>
	 * Please see the jdk documentation on the method
	 * {@link Math#pow(double, double)} for the limitations of this method.
	 * 
	 * @param base     the fraction to be raised.
	 * @param exponent the power to which the {@code base} is to be raised to.
	 * @return <code>this<sup>exponent</sup></code>
	 */
	private static Fraction pow(Fraction base, double exponent) {
		/* Let x be base, and n be exponent. */
		base = base.toImproper();
		/* If 0 ^ n */
		if (base.isZero())
			return ZERO;
		/* If x ^ 0 */
		if (exponent == 0)
			return ONE;
		/* If x ^ 1, return x */
		if (exponent == 1)
			return base;
		/* If 1 ^ n, and n is a positive integer */
		if (ONE.compareTo(base) == 0)// && (exponent >= 0 || isInteger(exponent)))
			return ONE;
		/* If x ^ n such that n is lesser than 0 or n is a fraction */
		if (exponent < 0 || !isInteger(exponent))
			return new Fraction(Math.pow(base.fraction, exponent), base.tolerance);
		return new Fraction((long) Math.pow(base.numerator, Math.abs(exponent)),
				(long) Math.pow(base.denominator, Math.abs(exponent)));
	}

	/**
	 * Utility method for computing the highest common factor(or gcd) of two
	 * integers.
	 *
	 * @param num   first of the two terms of the function
	 * @param denom second of the two terms of the function
	 * @return the greatest common divisor(hcf) of both arguments
	 */
	public static long hcf(long num, long denom) {
		if (denom < 0)
			denom = Math.abs(denom);
		if (num < 0)
			num = Math.abs(num);
		if (denom == 0)
			return num;

		return hcf(denom, num % denom);
	}

	/**
	 * Utility method for identifying integer values from fractional numbers.
	 *
	 * @param d the number to be evaluated.
	 * @return whether the number is integer, true if it is, false otherwise.
	 */
	public static boolean isInteger(double d) {
		double a = Math.ceil(d);
		double b = Math.floor(d);
		return (a == b && a == d && b == d);
	}

	/**
	 * Utility method for computing the lowest common multiple of two integers
	 *
	 * @param num   first of the two terms of the function
	 * @param denom second of the two terms of the function
	 * @return the lowest common multiple of both aguments.
	 */
	public static long lcm(long num, long denom) {
		if (denom < 0)
			denom = Math.abs(denom);
		if (num < 0)
			num = Math.abs(num);
		return (num * denom) / hcf(num, denom);
	}

	/**
	 * Creates a {@code Fraction} object from a {@code String}. The {@code String}
	 * must be in the same form supported by {@link Double#valueOf(String)}.
	 *
	 * @param fraction the fraction to be converted.
	 * @return a {@code Fraction}.
	 */
	public static Fraction valueOf(String fraction) {
		return new Fraction(fraction);
	}

	/*
	 * 
	 * Date: 18 Mar 2020---------------------------------------------------- Time
	 * created: 14:36:40--------------------------------------------
	 */
	/**
	 * Factory method for transforming the fractional part of a {@code BigDecimal}
	 * into a {@code Fraction}. Any sign associated with the argument will be lost
	 * after this method returns. Only the fractional part of the {@code fraction}
	 * is transformed, the integer part is discarded. The tolerance of the
	 * {@code Fraction} returned is 1e-6.
	 * 
	 * @param fraction the {@code BigInteger} whose fractional parts are to be
	 *                 transformed
	 * @return a {@code Fraction} object whose tolerance is {@code 1e-2} and whose
	 *         {@code getFraction()} is equal to the integer part subtracted from
	 *         the fractional part.
	 */
	public static Fraction valueOf(BigDecimal fraction) {
		BigDecimal a = fraction.abs();
		BigDecimal integer = new BigDecimal(a.abs().toBigInteger());
		a = a.subtract(integer);
		if (fraction.signum() < 0)
			a = a.negate();
		return new Fraction(a.doubleValue(), 1.0e-6);
	}

	/*
	 * Date: 31 Jul 2020-----------------------------------------------------------
	 * Time created: 16:34:05--------------------------------------------
	 */
	/**
	 * <p>
	 * Calculates <b><i>B</i></b><sub>n</sub> and returns a {@code Fraction}.
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
	public static Fraction computeBernoulliNumber(byte n) {
		if (n < 0)
			throw new ArithmeticException("Parameter out of range");
		else if (n == 0)
			return Fraction.ONE;
		else if (n == 1)
			return Fraction.HALF;
		else if (n > 1 && n % 2 != 0)
			return Fraction.ZERO;
//		final double tolerance = 1.0e-23d;
		Fraction[] b = new Fraction[n + 1];
		for (int m = 0; m <= n; m++) {
			b[m] = new Fraction(1, m + 1)/* .setTolerance(tolerance) */;
			for (int j = m; j >= 1; j--) {
				b[j - 1] = b[j - 1].subtract(b[j]).multiply(new Fraction(j));
			}
		}
		return b[0];
	}

	////////////////////////////////////////////////////////////
	///////////////////// toString()s /////////////////////////
	//////////////////////////////////////////////////////////

	/*
	 * 
	 * Date: 21 Mar 2020---------------------------------------------------- Time
	 * created: 12:43:30--------------------------------------------
	 */
	/**
	 * Returns this {@code Fraction} in the following notation:
	 * wholenumber/numerator/denominator using the specified radix. If there is no
	 * quotient, then only the numerator and denominator part is returned.
	 * 
	 * @param radix the radix in which to display the numbers
	 * @return this fraction in the above specified notation
	 */
	public String toString(int radix) {
		return (quotient == 0 ? "" : toStringArray(radix)[0] + "/") + toStringArray(radix)[1] + "/"
				+ toStringArray(radix)[2];
	}

	/**
	 * Returns a String array containing the quotient, numerator and the denominator
	 * for this {@code Fraction}.
	 *
	 * @return an array of this {@code Fraction}'s components.
	 */
	public String[] toStringArray() {
		return toStringArray(10);
	}

	/*
	 * 
	 * Date: 21 Mar 2020---------------------------------------------------- Time
	 * created: 12:43:35--------------------------------------------
	 */
	/**
	 * Returns a String array containing the quotient, numerator and the denominator
	 * for this {@code Fraction} in the specified radix.
	 * 
	 * @param radix the radix in which to display the numbers
	 * 
	 * @return an array of this {@code Fraction}'s components in the specified
	 *         radix.
	 */
	public String[] toStringArray(int radix) {
		return new String[] { quotient == 0 ? "" : Long.toString(getQuotient(), radix),
				Long.toString(getNumerator(), radix), Long.toString(getDenominator(), radix) };
	}

	////////////////////////////////////////////////////////////
	////////////////// Field declarations /////////////////////
	//////////////////////////////////////////////////////////

	/**
	 * A field the hold the whole number part of this {@code Fraction}
	 */
	private final long quotient; // The whole number of the fraction.

	/**
	 * A field the hold the remainder part or the numerator of this {@code Fraction}
	 */
	private final long numerator; // The evaluated numerator.

	/**
	 * A field the hold the divisor part or the denominator of this {@code Fraction}
	 */
	private final long denominator; // The final denominator.

	/**
	 * The floating point value of this {@code Fraction}
	 */
	private final double fraction; // the fraction to be converted.

	/* The scale which is used to for the precision of the fraction conversion. */
	/**
	 * The tolerance for this {@code Fraction}. the tolerance, determines how much
	 * precise a {@code Fraction} can be. This field must be above 0 and not exceed
	 * or equate to 1
	 */
	private final double tolerance;

	/**
	 * The mathematical sign of this {@code Fraction}
	 */
	private final byte signum;// The sign of the fraction involved.

	////////////////////////////////////////////////////////////////////////
	////////////////// Overridden methods (java.lang) /////////////////////
	//////////////////////////////////////////////////////////////////////

	/**
	 * Efficiently compares two fraction
	 *
	 * @return a value which is either<br>
	 *         0 for equality,<br>
	 *         or<br>
	 *         greater than 1 for comparative greatness,<br>
	 *         or<br>
	 *         less than 1 for comparative minimality.
	 */
	@Override
	public int compare(Fraction fraction1, Fraction fraction2) {
		return fraction1.compareTo(fraction2);
	}

	/**
	 * Returns the int value of this {@code Fraction}.
	 *
	 * @return the int value of this {@code Fraction}.
	 */
	@Override
	public int intValue() {
		return (int) quotient;
	}

	/**
	 * Returns the long value of this {@code Fraction}.
	 *
	 * @return the long value of this {@code Fraction}.
	 */
	@Override
	public long longValue() {
		return quotient;
	}

	/**
	 * Returns the float value of this {@code Fraction}.
	 *
	 * @return the float value of this {@code Fraction}.
	 */
	@Override
	public float floatValue() {
		return (float) fraction;
	}

	/**
	 * Returns the double value of this {@code Fraction}.
	 *
	 * @return the double value of this {@code Fraction}.
	 */
	@Override
	public double doubleValue() {
		return fraction;
	}

	/**
	 * Efficiently compares two fraction and return 0 for equality, above 0 for
	 * greater than and below 0 for lesser than. This does not guarantee that 2
	 * fraction with the same floating point fields will return 0. this is because
	 * both may be using different tolerance therefore achieving different levels of
	 * precision.
	 *
	 * @return a value which is either<br>
	 *         0 for equality,<br>
	 *         or<br>
	 *         greater than 1 for comparative greatness,<br>
	 *         or<br>
	 *         less than 1 for comparative minimality.
	 */
	@Override
	public int compareTo(Fraction fraction) {
		Fraction thisOne = new Fraction(this);
		if (thisOne.quotient != fraction.getQuotient()) {
			if ((!fraction.isProper()) && !fraction.isMixed()) {
//				fraction = new Fraction(fraction.fraction, fraction.tolerance);
				fraction = fraction.toLowestTerm();
			}
			if ((!isProper()) && !isMixed()) {
//				thisOne = new Fraction(thisOne.fraction, thisOne.tolerance);
				thisOne = thisOne.toLowestTerm();
			}
			return (int) thisOne.quotient - (int) fraction.quotient;
		}
		Long l = thisOne.subtract(fraction).getNumerator();
		return l.intValue();
	}

	/**
	 * Returns the hash code for this {@code Fraction}.
	 *
	 * @return the hashcode for this {@code Fraction}.
	 * @see #equals(Object)
	 */
	@Override
	public int hashCode() {// make sure it returns the correct sign.
		Fraction thisOne = new Fraction(this);
		if ((!isProper()) && !isMixed())
			thisOne = thisOne.toLowestTerm();// make sure the fraction is converted.
//		return (int) (((getQuotient() * getDenominator()) + getNumerator())) | super.hashCode();

		return Long.hashCode(quotient) + Long.hashCode(numerator) + Long.hashCode(denominator);
	}

	/**
	 * Compare two {@code Fraction} object for equality. The
	 * {@code compare(Fraction, Fraction)} and {@code compareTo(Fraction)} methods
	 * should be used in preference to this method when testing for equality.
	 *
	 * @param o the object to be compared
	 * @return true<br>
	 *         if both have the same denominator and have the same length of
	 *         mantissa digits<br>
	 *         false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Fraction))
			return false;
		Fraction f = (Fraction) o;
		if (f.getFraction() + "".length() > getFraction() + "".length()
				|| f.getFraction() + "".length() < getFraction() + "".length())
			return false;
		if (compareTo((Fraction) o) == 0) {
			if (tolerance == f.tolerance)
				return hashCode() == f.hashCode();
			return tolerance == f.tolerance;
		}
		return compareTo(f) == 0;
	}

	/**
	 * Returns debugging info for this {@code Fraction}'s fields.
	 *
	 * @return the String form for this {@code Fraction}'s object and its members.
	 * @see #toString()
	 */
	@Override
	public String toString() {
		return "Quotient: " + quotient + "\nNumerator: " + numerator + "\nDenominator: " + denominator + "\nFraction: "
				+ fraction + "\nTolerance: " + tolerance + "\nSign: " + signum;
	}
}
