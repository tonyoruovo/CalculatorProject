/**
 * 
 */
package mathaid.calculator.base.value;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

import org.apfloat.Apcomplex;
import org.apfloat.ApcomplexMath;
import org.apfloat.Apfloat;

import mathaid.ExceptionMessage;
import mathaid.calculator.base.MathematicalException;
import mathaid.calculator.base.converter.AngleUnit;
import mathaid.calculator.base.util.Arith;
import mathaid.calculator.base.util.Utility;

/*
 * Date: 15 Aug 2020----------------------------------------------------------- 
 * Time created: 12:46:07---------------------------------------------------  
 * Package: math.number------------------------------------------------ 
 * Project: MyTempTest------------------------------------------------ 
 * File: Complex.java------------------------------------------------------ 
 * Class name: Complex------------------------------------------------ 
 */
/**
 * <p>
 * Immutable representation of complex numbers, with the ability for basic
 * arithmetic operations (add, subtract divide, multiply, negate), functions
 * (absolute value, log, power, square root and trigonometry) and other
 * important functions (conjugate, reciprocal, gamma etc).
 * </p>
 * <p>
 * The {@code Complex} class extends the {@code java.lang.Number} class to
 * provide functionality for conversions to java's primitives
 * </p>
 * <p>
 * A {@code complex} object has only 3 fields. The real and imaginary values
 * (retrieved via {@link #real()} and {@link #imaginary()} respectively), and
 * then a {@code MathContext} object. When a {@code Complex} object is said to
 * be real, then the imaginary field is equal to 0. When a {@code Complex}
 * object is said to be imaginary, then the real field is 0. When it is equal to
 * the imaginary unit <code><i>i</i></code>, then it is imaginary while the
 * imaginary part is <code>&plusmn;1</code>. When a function that needs to apply
 * a {@code MathContext} object is called - such as {@link #divide(Complex)
 * divide}, {@link #sqrt() sqrt}, {@link #log() log} etc - the
 * {@code MathContext} of the result is determined by evaluating the operand
 * with the higher precision value and using that precision for the new object
 * returned by the method.
 * </p>
 * <p>
 * This class contains methods for transforming complex numbers to
 * {@link #toPolarCoordinates() polar} and {@link #toRectangularCoordinates()
 * rectangular} coordinates, calculating the {@link #argument() argument} of a
 * complex number, it {@link #radius() length} on the complex plain,
 * {@link #isImaginary() check} for the imaginary unit support for all
 * elementary functions, a {@link #round(int) rounding mechanism} and some
 * extras. All methods that take in a complex value as argument have an
 * alternative method that take in a {@code java.lan.Number} value as an
 * argument, this is simply for convenience. Note that all trigonometrical
 * operations return their values in radians.
 * 
 * </p>
 * <p>
 * A {@code Complex} object can also be used like a decimal real number. For
 * example:
 * 
 * <pre>
 * Complex z = new Complex("25")&semi;
 * // perform basic arithmetic operations on it
 * z = z.negate()&semi;
 * z = z.sqrt()&semi; // becomes the complex number -5<i>i</i>
 * z = z.add(Complex.I)&semi;
 * z = z.subtract(new Complex("5i"))&semi;
 * z = z.pow(2)&semi; // now a real value
 * </pre>
 * 
 * So from the above, we can see that a {@code Complex} object may not start as
 * a complex number at first therefore, {@code Complex} object are dynamic
 * numbers that can start as real and then move to complex as the user wishes.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class Complex extends java.lang.Number {

	/////////////////////////////////////////////////////////////////////
	/////////////////////////// Constants //////////////////////////////
	///////////////////////////////////////////////////////////////////

	/**
	 * Version serializer
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The real value 1.
	 */
	public static final Complex ONE = new Complex(BigDecimal.ONE, BigDecimal.ZERO);
	/**
	 * The real value 0.
	 */
	public static final Complex ZERO = new Complex(BigDecimal.ZERO, BigDecimal.ZERO);
	/**
	 * The imaginary value <i>i</i>, whose real value is zero and imaginary value is
	 * -1.
	 */
	public static final Complex I = new Complex(BigDecimal.ZERO, BigDecimal.ONE);
	/**
	 * The unit of angular measurement that all trigonometrical operations use
	 */
	private static final AngleUnit UNIT = AngleUnit.RAD;
	/**
	 * The maximum iteration for the {@code pow()} functions
	 */
	private static final int MAX_ITER = 123456;

	/////////////////////////////////////////////////////////////////////
	//////////////////////// Constructors //////////////////////////////
	///////////////////////////////////////////////////////////////////

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 09:52:12---------------------------------------------------
	 */
	/**
	 * Initialises a {@code Complex} object using a string.
	 * 
	 * @param val a {@code String} representation of this {@code Complex} value
	 * @see #Complex(String, int)
	 */
	public Complex(String val) {
		this(val, MathContext.DECIMAL32.getPrecision());
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 10:08:48---------------------------------------------------
	 */
	/**
	 * Initialises a {@code Complex} object using a string, and a given precision.
	 * <p>
	 * The string must be in one of the following the format:
	 * <ul>
	 * <li><code>&plusmn;[decimal]&plusmn;[decimal]</code> and must not contain
	 * spaces (for full complex description)</li>
	 * <li>&plusmn;[decimal]i (for imaginary description)</li>
	 * <li>a format accepted by {@link java.math.BigDecimal#BigDecimal(String)} (for
	 * real description)</li>
	 * </ul>
	 * The parameter "decimal" in square brackets above must conform to the same
	 * described in the {@code java.math.BigDecimal} documentation for it's string
	 * parameter constructors. If the value (either the imaginary part or the real
	 * part) is meant to be negative, then it must be signed, otherwise it is
	 * regarded as a positive value and the sign unnecessary. Note that if this is
	 * not a real value, then the suffix <i>i</i> must be appended to the imaginary
	 * part.
	 * </p>
	 * 
	 * @param val    a {@code String} representation of this {@code Complex} value
	 * @param digits the precision of the digits within the given decimals
	 */
	public Complex(String val, int digits) {
		StringBuilder sb = new StringBuilder(val);
		boolean pos = sb.lastIndexOf("+") >= 1;
		boolean neg = sb.lastIndexOf("-") >= 1;
		boolean hasIm = sb.indexOf("i") >= 0;
		mc = new MathContext(digits, RoundingMode.HALF_EVEN);
		if (hasIm) {
			String real;
			String imaginary;
			if (pos) {
				real = sb.substring(0, sb.indexOf("+"));
				imaginary = sb.substring(sb.indexOf("+"));
			} else if (neg) {
				real = sb.substring(0, sb.indexOf("-"));
				imaginary = sb.substring(sb.indexOf("-"), sb.indexOf("i"));
			} else {
				real = "0";
				imaginary = sb.indexOf("-") == 0 ? "-1" : "1";
			}
			this.real = new BigDecimal(real, mc);
			this.imaginary = new BigDecimal(imaginary.replace("i", ""), mc);
		} else {
			this.real = new BigDecimal(val, mc);
			this.imaginary = BigDecimal.ZERO;
		}
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 10:11:55---------------------------------------------------
	 */
	/**
	 * Constructs a {@code Complex} number that consists of a real and complex
	 * value.
	 * 
	 * @param re the real value
	 * @param im the complex value
	 */
	public Complex(BigDecimal re, BigDecimal im) {
		this(re, im, new MathContext(Math.max(re.precision(), im.precision()), RoundingMode.HALF_EVEN));
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 10:11:55---------------------------------------------------
	 */
	/**
	 * Constructs a {@code Complex} number that consists of a real and complex value
	 * with the precision specified by a given {@code MathContext}.
	 * 
	 * @param re the real value
	 * @param im the complex value
	 * @param a  {@code MathContext} to specify the precision of <code>this</code>
	 */
	public Complex(BigDecimal re, BigDecimal im, MathContext mc) {
		this.real = re;
		this.imaginary = im;
		this.mc = mc;
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 10:16:35---------------------------------------------------
	 */
	/**
	 * Creates a {@code Complex} value initialised to the given real and imaginary
	 * constants.
	 * <p>
	 * The string arguments must be in the same formats as
	 * {@link java.math.BigDecimal#BigDecimal(String) this} constructor
	 * documentation says
	 * </p>
	 * 
	 * @param real      a decimal {@code String} for the real value
	 * @param imaginary a decimal {@code String} for the imaginary value
	 */
	public Complex(String real, String imaginary) {
		this(new BigDecimal(real), new BigDecimal(imaginary));
	}

	/////////////////////////////////////////////////////////////////////
	////////////////////////// Accessors ///////////////////////////////
	///////////////////////////////////////////////////////////////////

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 10:58:51--------------------------------------------
	 */
	/**
	 * Gets the real value
	 * 
	 * @return a {@code BigDecimal} which is the real value
	 */
	public BigDecimal real() {
		return this.real.round(mc);
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 10:58:51--------------------------------------------
	 */
	/**
	 * Gets the imaginary value
	 * 
	 * @return a {@code BigDecimal} which is the imaginary value
	 */
	public BigDecimal imaginary() {
		return imaginary.round(mc);
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 11:00:06--------------------------------------------
	 */
	/**
	 * Returns the angle the position of this complex value in the complex plane
	 * makes with the real axis
	 * 
	 * @return the argument of this {@code Complex} value
	 */
	public BigDecimal theta() {
		BigDecimal theta = Arith.atan(imaginary, real, UNIT, mc);
		return theta;
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 11:02:15--------------------------------------------
	 */
	/**
	 * Returns the argument function of this {@code Complex} value.
	 * 
	 * @return {@link #theta()}
	 */
	public BigDecimal argument() {
		return theta();
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 11:03:07--------------------------------------------
	 */
	/**
	 * Returns the length of {@code this} relative to the {@link #ZERO zero} point
	 * on the complex plane.
	 * 
	 * @return the absolute value of {@code this}
	 */
	public BigDecimal magnitude() {
		return abs().real;
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 11:03:07--------------------------------------------
	 */
	/**
	 * Returns the radius of the circle of {@code this} on the complex plane. The
	 * circle is the locus of points equidistant from the {@link #ZERO origin} of
	 * the complex plane
	 * 
	 * @return {@link #magnitude()}
	 */
	public BigDecimal radius() {
		return magnitude();
	}

	/////////////////////////////////////////////////////////////////////
	/////////////////////////// Checks /////////////////////////////////
	///////////////////////////////////////////////////////////////////

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 11:03:07--------------------------------------------
	 */
	/**
	 * Returns whether or not this is a real number. The value zero is considered a
	 * real number.
	 * 
	 * @return {@code true} if this is solely a real value and {@code code false} if
	 *         otherwise
	 */
	public boolean isReal() {
		return imaginary.compareTo(BigDecimal.ZERO) == 0;
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 11:03:07--------------------------------------------
	 */
	/**
	 * Returns whether or not this is an imaginary number. The value zero is
	 * considered to be a real number.
	 * 
	 * @return {@code true} if this is solely an imaginary value and
	 *         {@code code false} if otherwise
	 */
	public boolean isImaginary() {
		return real.compareTo(BigDecimal.ZERO) == 0 && imaginary.compareTo(BigDecimal.ZERO) != 0;
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 11:03:07--------------------------------------------
	 */
	/**
	 * Returns whether or not this is the real number constant 0.
	 * 
	 * @return {@code true} if this is solely the real value 0 and
	 *         {@code code false} if otherwise
	 */
	public boolean isZero() {
		return real.compareTo(BigDecimal.ZERO) == 0 && isReal();
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 11:03:07--------------------------------------------
	 */
	/**
	 * Returns whether or not this is the imaginary number constant <i>i</i>.
	 * 
	 * @return {@code true} if this is solely the imaginary value <i>i</i> and
	 *         {@code code false} if otherwise
	 */
	public boolean isIndeterminate() {
		return isImaginary() && imaginary.abs().compareTo(BigDecimal.ONE) == 0;
	}

	/*
	 * Date: 27 Jul 2021-----------------------------------------------------------
	 * Time created: 11:51:01--------------------------------------------
	 */
	/**
	 * Compares the real and imaginary values of {@code this} and the argument for
	 * numerical greatness and returns {@code true} if and only if the real and
	 * imaginary values of {@code this} is numerically equal to that of the
	 * argument.
	 * 
	 * @param c a reference object on which to compare
	 * @return {@code true} if and only if {@code this} is numerically equal to the
	 *         argument or else returns {@code false}
	 */
	public boolean equals(Complex c) {
		return real.compareTo(c.real) == 0 && imaginary.compareTo(c.imaginary) == 0;
	}

	/////////////////////////////////////////////////////////////////////
	///////////////////// Arithmetic methods ///////////////////////////
	///////////////////////////////////////////////////////////////////

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:28:20--------------------------------------------
	 */
	/**
	 * <p>
	 * Calculates and returns the sum of {@code this} and the argument.
	 * </p>
	 * 
	 * @param i the {@code Complex} to be added to <code>this</code>.
	 * @return <code>this + i</code>
	 */
	public Complex add(Complex i) {
		return new Complex(real.add(i.real), imaginary.add(i.imaginary), getContext(mc, i.mc));
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:31:23--------------------------------------------
	 */
	/**
	 * Computes and returns the sum of {@code this} and the real argument. This is
	 * simply a convenience method for java's primitive support.
	 * 
	 * @param x a real number to be added to <code>this</code>.
	 * @return <code>this + x</code>
	 */
	public Complex add(Number x) {
		if (isSupported(x))
			return new Complex(real.add(new BigDecimal(String.valueOf(x))), imaginary, mc);
		return new Complex(real.add(new BigDecimal(String.valueOf(x.doubleValue()))), imaginary, mc);
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:33:29--------------------------------------------
	 */
	/**
	 * <p>
	 * Calculates and returns the difference between {@code this} and the argument.
	 * </p>
	 * 
	 * @param i the {@code Complex} to be subtracted from <code>this</code>.
	 * @return <code>this - i</code>
	 */
	public Complex subtract(Complex i) {
		return new Complex(real.subtract(i.real), imaginary.subtract(i.imaginary), getContext(mc, i.mc));
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:36:18--------------------------------------------
	 */
	/**
	 * Computes and returns the difference between {@code this} and the real
	 * argument. This is simply a convenience method for java's primitive support.
	 * 
	 * @param x a real number to be subtracted from <code>this</code>.
	 * @return <code>this - x</code>
	 */
	public Complex subtract(Number x) {
		if (isSupported(x))
			return new Complex(real.subtract(new BigDecimal(String.valueOf(x))), imaginary, mc);
		return new Complex(real.subtract(new BigDecimal(String.valueOf(x.doubleValue()))), imaginary, mc);
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:38:24--------------------------------------------
	 */
	/**
	 * <p>
	 * Calculates and returns the product of {@code this} and the argument.
	 * </p>
	 * 
	 * @param i the {@code Complex} to be multiplied by <code>this</code>.
	 * @return <code>this * i</code>
	 */
	public Complex multiply(Complex i) {
		if (i.isZero() || isZero())
			return ZERO;
		if (isReal())
			return i.multiply(real);
		if (i.isReal())
			return multiply(i.real);
		return new Complex(real.multiply(i.real).subtract(imaginary.multiply(i.imaginary)),
				real.multiply(i.imaginary).add(imaginary.multiply(i.real)), getContext(mc, i.mc));
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:49:15--------------------------------------------
	 */
	/**
	 * Computes and returns the product of {@code this} and the real argument. This
	 * is simply a convenience method for java's primitive support.
	 * 
	 * @param x a real number to be multiplied by <code>this</code>.
	 * @return <code>this * x</code>
	 */
	public Complex multiply(Number x) {
		if (isSupported(x))
			return new Complex(real.multiply(new BigDecimal(String.valueOf(x))),
					imaginary.multiply(new BigDecimal(String.valueOf(x))), mc);
		return new Complex(real.multiply(new BigDecimal(String.valueOf(x.doubleValue()))),
				imaginary.multiply(new BigDecimal(String.valueOf(x.doubleValue()))), mc);
//		return new Complex(real.multiply(new BigDecimal(x)), imaginary.multiply(new BigDecimal(x)), mc);
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:50:58--------------------------------------------
	 */
	/**
	 * <p>
	 * Calculates and returns the quotient of {@code this} divided by the argument.
	 * </p>
	 * 
	 * @param c the {@code Complex} which divides <code>this</code>.
	 * @return <code>this / c</code>
	 * @throws RuntimeException specifically a {@code ArithmeticException} if
	 *                          <code>c.isZero()</code> returns {@code true}
	 */
	public Complex divide(Complex c) {
		if (c.isZero())
			new MathematicalException(ExceptionMessage.DIVISION_BY_ZERO);
		final BigDecimal n1, n2, n3, n4;
		final MathContext mc = getContext(this.mc, c.mc);
		if (real.abs().compareTo(imaginary.abs()) < 0) {
			n1 = real.divide(imaginary, mc);
			n2 = real.multiply(n1, mc).add(imaginary);
			n3 = real.multiply(n1, mc).add(imaginary).divide(n2, mc);
			n4 = imaginary.multiply(n1).subtract(real).divide(n2, mc);
		} else {
			n1 = imaginary.divide(real, mc);
			n2 = imaginary.multiply(n1).add(real);
			n3 = imaginary.multiply(n1, mc).add(real).divide(n2, mc);
			n4 = imaginary.subtract(real.multiply(n1)).divide(n2, mc);
		}
		return new Complex(n3, n4, mc);
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:49:15--------------------------------------------
	 */
	/**
	 * Computes and returns the quotient of {@code this} and the real argument. This
	 * is simply a convenience method for java's primitive support.
	 * 
	 * @param x a real number which divides <code>this</code>.
	 * @return <code>this / x</code>
	 * @throws RuntimeException specifically a {@code ArithmeticException} if x == 0
	 */
	public Complex divide(Number x) {
		if (x.doubleValue() == 0)
			new MathematicalException(ExceptionMessage.DIVISION_BY_ZERO);
		if (isSupported(x))
			return new Complex(real.divide(new BigDecimal(String.valueOf(x))),
					imaginary.divide(new BigDecimal(String.valueOf(x))), mc);
		return new Complex(real.divide(new BigDecimal(String.valueOf(x.doubleValue()))),
				imaginary.divide(new BigDecimal(String.valueOf(x.doubleValue()))), mc);
//		return new Complex(real.divide(new BigDecimal(x), mc), imaginary.divide(new BigDecimal(x), mc), mc);
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:25:12--------------------------------------------
	 */
	/**
	 * Performs negation on both the real and imaginary value and then returns the
	 * result.
	 *
	 * @return <code>-this</code>
	 */
	public Complex negate() {
		return new Complex(real.negate(), imaginary.negate(), mc);
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 11:03:07--------------------------------------------
	 */
	/**
	 * Returns the reciprocal {@code this}.
	 * 
	 * @return <code><sup>1</sup>/</code><sub>z</sub> given that {@code z = this}
	 * @throws RuntimeException specifically a {@code ArithmeticException} if
	 *                          {@code this == 0}
	 */
	public Complex reciprocal() {
		if (isZero())
			new MathematicalException(ExceptionMessage.DIVISION_BY_ZERO);
		BigDecimal n1, n2, n3;
		if (real.abs().compareTo(imaginary.abs()) < 0) {
			n1 = real.divide(imaginary, mc);
			n2 = BigDecimal.ONE.divide(real.multiply(n1).add(imaginary), mc);
			n3 = n2.multiply(n1);
			n2 = n2.negate();
		} else {
			n1 = imaginary.divide(real, mc);
			n2 = BigDecimal.ONE.divide(imaginary.multiply(n1).add(real), mc);
			n3 = n2;
			n2 = n3.negate().multiply(n1);
		}
		return new Complex(n3, n2, mc);
	}

	/////////////////////////////////////////////////////////////////////
	//////////////////// Elementary functions //////////////////////////
	///////////////////////////////////////////////////////////////////

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 11:03:07--------------------------------------------
	 */
	/**
	 * Returns the Euclidean norm of {@code this}.
	 * 
	 * @return <code>|this|</code>
	 */
	public Complex abs() {
		BigDecimal bd;
		BigDecimal re;
		if (real.abs().compareTo(imaginary.abs()) < 0) {
			if (isReal())
				return new Complex(real.abs(), BigDecimal.ZERO, mc);
			bd = real.divide(imaginary, mc);
			re = imaginary.abs().multiply(Arith.sqrt(BigDecimal.ONE.add(bd.pow(2)), mc));

			return new Complex(re, BigDecimal.ZERO, mc);
		}
		if (isImaginary())
			return new Complex(imaginary.abs(), BigDecimal.ZERO, mc);
		bd = imaginary.divide(real, mc);
		re = real.abs().multiply(Arith.sqrt(BigDecimal.ONE.add(bd.pow(2)), mc));

		return new Complex(re, BigDecimal.ZERO, mc);
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:58:11--------------------------------------------
	 */
	/**
	 * Performs the trigonometrical function {@code sin(this)} and returns the
	 * result in radians.
	 * 
	 * @return {@code sin(this)}
	 */
	public Complex sin() {
		BigDecimal re = Arith.sin(real, UNIT, mc);
		re = re.multiply(Arith.cosh(imaginary, mc));
		BigDecimal im = Arith.cos(real, UNIT, mc);
		im = im.multiply(Arith.sinh(imaginary, mc));
		return new Complex(re, im, mc);
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:58:11--------------------------------------------
	 */
	/**
	 * Performs the inverse sine function <code>sin<sup>-1</sup>(this)</code> and
	 * returns the result.
	 * 
	 * @return <code>sin<sup>-1</sup>(this)</code>
	 */
	public Complex asin() {
		return sqrt1z().add(this.multiply(I)).log().multiply(I.negate());
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:58:11--------------------------------------------
	 */
	/**
	 * Performs the hyperbolic sine function <code>sinh(this)</code> and returns the
	 * result in radians
	 * 
	 * @return <code>sinh(this)</code>
	 */
	public Complex sinh() {
		BigDecimal re = Arith.sinh(real, mc);
		re = re.multiply(Arith.cos(imaginary, UNIT, mc));
		BigDecimal im = Arith.cosh(real, mc);
		im = im.multiply(Arith.sin(imaginary, UNIT, mc));
		return new Complex(re, im, mc);
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:58:11--------------------------------------------
	 */
	/**
	 * Performs the inverse hyperbolic sine function
	 * <code>sinh<sup>-1</sup>(this)</code> and returns the result
	 * 
	 * @return <code>sinh<sup>-1</sup>(this)</code>
	 */
	public Complex asinh() {
		Complex z = square().add(ONE).sqrt();
		return add(z).log();
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:58:11--------------------------------------------
	 */
	/**
	 * Performs the cosine function <code>cos(this)</code> and returns the result in
	 * radians
	 * 
	 * @return <code>cos(this)</code>
	 */
	public Complex cos() {
		BigDecimal re = Arith.cos(real, UNIT, mc);
		re = re.multiply(Arith.cosh(imaginary, mc));
		BigDecimal im = Arith.sin(real, UNIT, mc).negate();
		im = im.multiply(Arith.sinh(imaginary, mc));
		return new Complex(re, im, mc);
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:58:11--------------------------------------------
	 */
	/**
	 * Performs the inverse cosine function <code>cos<sup>-1</sup>(this)</code> and
	 * returns the result.
	 * 
	 * @return <code>cos<sup>-1</sup>(this)</code>
	 */
	public Complex acos() {
		return add(sqrt1z().multiply(I)).log().multiply(I.negate());
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:58:11--------------------------------------------
	 */
	/**
	 * Performs the hyperbolic cosine function <code>cosh(this)</code> and returns
	 * the result in radians
	 * 
	 * @return <code>cosh(this)</code>
	 */
	public Complex cosh() {
		BigDecimal re = Arith.cosh(real, mc);
		re = re.multiply(Arith.cos(imaginary, UNIT, mc));
		BigDecimal im = Arith.sinh(real, mc);
		im = im.multiply(Arith.sin(imaginary, UNIT, mc));
		return new Complex(re, im, mc);
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:58:11--------------------------------------------
	 */
	/**
	 * Performs the inverse hyperbolic cosine function
	 * <code>cosh<sup>-1</sup>(this)</code> and returns the result.
	 * 
	 * @return <code>cosh<sup>-1</sup>(this)</code>
	 */
	public Complex acosh() {
		Complex z = add(ONE).sqrt().multiply(subtract(ONE).sqrt());
		return add(z).log();
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:58:11--------------------------------------------
	 */
	/**
	 * Performs the tangent function <code>tan(this)</code> and returns the result
	 * in radians
	 * 
	 * @return <code>tan(this)</code>
	 */
	public Complex tan() {
		if (imaginary.compareTo(new BigDecimal(20)) > 0)
			return I;
		else if (imaginary.compareTo(new BigDecimal(-20)) < 0)
			return I.negate();

		final BigDecimal two = new BigDecimal(2);

		BigDecimal r2 = two.multiply(real);
		BigDecimal im2 = two.multiply(imaginary);
		BigDecimal bg = Arith.cos(r2, UNIT, mc).add(Arith.cosh(imaginary, mc));

		r2 = Arith.sin(r2, UNIT, mc).divide(bg, mc);
		im2 = Arith.sinh(im2, mc).divide(bg);

		return new Complex(r2, im2, mc);
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:58:11--------------------------------------------
	 */
	/**
	 * Performs the inverse tangent function <code>tan<sup>-1</sup>(this)</code> and
	 * returns the result.
	 * 
	 * @return <code>tan<sup>-1</sup>(this)</code>
	 */
	public Complex atan() {
		return add(I).divide(I.subtract(this)).log()
				.multiply(I.divide(new Complex(new BigDecimal(2), new BigDecimal(0))));
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:58:11--------------------------------------------
	 */
	/**
	 * Performs the hyperbolic tangent function <code>tanh(this)</code> and returns
	 * the result in radians
	 * 
	 * @return <code>tanh(this)</code>
	 */
	public Complex tanh() {
		if (real.compareTo(new BigDecimal(20)) > 0)
			return ONE;
		else if (real.compareTo(new BigDecimal(-20)) < 0)
			return ONE.negate();

		final BigDecimal two = new BigDecimal(2);
		BigDecimal r2 = two.multiply(real);
		BigDecimal im2 = two.multiply(imaginary);
		BigDecimal bd = Arith.cosh(r2, mc).add(Arith.cos(im2, UNIT, mc));

		r2 = Arith.sinh(r2, mc).divide(bd, mc);
		im2 = Arith.sin(im2, UNIT, mc).divide(bd, mc);

		return new Complex(r2, im2, mc);
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:58:11--------------------------------------------
	 */
	/**
	 * Performs the inverse hyperbolic tangent function
	 * <code>tanh<sup>-1</sup>(this)</code> and returns the result.
	 * 
	 * @return <code>tanh<sup>-1</sup>(this)</code>
	 */
	public Complex atanh() {
//		Complex z = add(ONE).divide(ONE.subtract(this));
//		return z.log().multiply(0.5);
		Complex lhs = add(ONE).log().multiply(0.5);
		Complex rhs = ONE.subtract(this).log().multiply(0.5);
		return lhs.subtract(rhs);
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:58:11--------------------------------------------
	 */
	/**
	 * Performs the natural logarithm function on {@code this} and returns the
	 * result.
	 * 
	 * @return <code>ln(this)</code>
	 */
	public Complex log() {
		return new Complex(Arith.log(abs().real, mc), Arith.atan(imaginary, real, UNIT, mc), mc);
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:58:11--------------------------------------------
	 */
	/**
	 * Performs the base 10 logarithm function on <code>this</code> and returns the
	 * result.
	 * 
	 * @return <code>ln(this) / ln(10)</code>
	 */
	public Complex log10() {
		return log(valueOf(10).round(mc));
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:58:11--------------------------------------------
	 */
	/**
	 * Performs the base 2 logarithm function on <code>this</code> and returns the
	 * result.
	 * 
	 * @return <code>ln(this) / ln(2)</code>
	 */
	public Complex log2() {
		return log(valueOf(2).round(mc));
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:58:11--------------------------------------------
	 */
	/**
	 * Performs the logarithm function on <code>this</code> in the given base and
	 * returns the result.
	 * 
	 * @param base the base in which this function is to be computed
	 * @return <code>ln(this) / ln(base)</code>
	 */
	public Complex log(Complex base) {
		return log().divide(base.log());
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:58:11--------------------------------------------
	 */
	/**
	 * Performs the given base logarithm function on <code>this</code> and returns
	 * the result.
	 * 
	 * @param base a real number which is the base of this logarithm
	 * @return <code>ln(this) / ln(base)</code>
	 */
	public Complex log(Number base) {
		return log(valueOf(base));
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:58:11--------------------------------------------
	 */
	/**
	 * Performs the exponential function on <code>this</code> and returns the result
	 * in radians.
	 * 
	 * @return <code>exp(this)</code> or <code>e<sup>this</sup></code> where e is
	 *         the constant for base of all natural logarithms.
	 */
	public Complex exp() {
		BigDecimal exp = Arith.exp(real, mc);
		BigDecimal re = exp.multiply(Arith.cos(imaginary, UNIT, mc));
		BigDecimal im = exp.multiply(Arith.sin(imaginary, UNIT, mc));
		return new Complex(re, im, mc);
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:58:11--------------------------------------------
	 */
	/**
	 * Calculates <code>this</code> raised to the power of the argument and returns
	 * the result.
	 * 
	 * @param exponent the value to the power of which {@code this} is raised.
	 * @return <code>this<sup>exponent</sup></code>
	 */
	public Complex pow(final Complex exponent) {
		if (isZero())
			return ONE;
		else if (exponent.isReal())
			return pow(exponent.real);

		return log().multiply(exponent).exp();
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:58:11--------------------------------------------
	 */
	/**
	 * Calculates <code>this</code> raised to the power of the real number argument
	 * and returns the result.
	 * 
	 * @param exp the real value to the power of which {@code this} is raised.
	 * @return <code>this<sup>exp</sup></code>
	 */
	public Complex pow(final Number exp) {
		if (isInteger(exp)) {
			BigDecimal x;
			Complex last = this;
			try {
				if (!(exp instanceof BigDecimal))
					x = new BigDecimal(exp.doubleValue());
				else
					x = (BigDecimal) exp;
				if (isIndeterminate())
					return powImaginaryUnit(x).multiply(valueOf(signum()));
				if (x.compareTo(new BigDecimal(2)) == 0)
					return square();

				int it = Math.abs(x.intValueExact());
				if (isWithinIteration(it)) {
					for (int i = 0; i < it - 1; i++)
						last = last.multiply(this);
					return x.signum() < 0 ? ONE.divide(last) : last;
				}
			} catch (@SuppressWarnings("unused") ArithmeticException e) {
			}
		}

		return log().multiply(exp).exp();
	}

	/*
	 * Date: 27 Jul 2021-----------------------------------------------------------
	 * Time created: 11:48:27--------------------------------------------
	 */
	/**
	 * Calculates the signum of {@code this} and returns the value as an {@code int}
	 * 
	 * @return {@code sign(this)}.
	 */
	public int signum() {
		if (isZero())
			return 0;
		if (isReal())
			return real.signum();
		return imaginary.signum();
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 22:20:13--------------------------------------------
	 */
	/**
	 * calculates and returns the square root of {@code this}.
	 * 
	 * @return <code>this<sup>1/2</sup></code>
	 */
	public Complex sqrt() {
//		if (isZero())
//			return ZERO;
//		BigDecimal two = new BigDecimal(2);
//		BigDecimal x = real.abs().add(abs().real).divide(two, mc);
//		x = Arith.sqrt(x, mc);
//		if (real.compareTo(BigDecimal.ZERO) >= 0)
//			return new Complex(x, imaginary.divide(two.multiply(x), mc), mc);
//		return new Complex(imaginary.abs().divide(two.multiply(x), mc),
//				Arith.copySign(BigDecimal.ONE, imaginary).multiply(x), mc);
		Apcomplex z = new Apcomplex(new Apfloat(real), new Apfloat(imaginary));
		z = ApcomplexMath.sqrt(z);
		return new Complex(z.real().toString(), z.imag().toString());
	}

	/////////////////////////////////////////////////////////////////////
	/////////////////////// Other functions ////////////////////////////
	///////////////////////////////////////////////////////////////////

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:58:11--------------------------------------------
	 */
	/**
	 * Calculates n<sup>th</sup> root of <code>this</code> and returns the result.
	 * 
	 * @param index an {@code int} which is the specified root index. For example,
	 *              to find the quad root of {@code this} would be {@code 4}.
	 * @return <code>this<sup>1/index</sup></code>
	 * @throws RuntimeException specifically a {@code ArithmeticException} if index
	 *                          <= 1
	 */
	public Complex root(int index) {
		if (index <= 1)
			new MathematicalException(ExceptionMessage.ILLEGAL_INDEX_FOR_ROOT);
		Complex c = I.multiply(theta()).divide(index).exp();
		return c.multiply(Arith.root(magnitude(), index, mc.getPrecision()));
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 11:03:07--------------------------------------------
	 */
	/**
	 * Returns the reflection symmetry with relative to the real axis.
	 * 
	 * @return <code>&amacr;</code> given that a is a complex number
	 */
	public Complex conjugate() {
		return new Complex(real, imaginary.negate(), mc);
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 22:04:25--------------------------------------------
	 */
	/**
	 * Calculates the gamma function of {@code this} and returns the result.
	 * 
	 * @return <code>&Gamma;(this)</code>
	 */
	public Complex gamma() {
		Apcomplex z = new Apcomplex(new Apfloat(real), new Apfloat(imaginary));
		z = ApcomplexMath.gamma(z.precision(mc.getPrecision()));
		Complex w = new Complex(z.real().toString(), z.imag().toString());
		return w;
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 22:20:13--------------------------------------------
	 */
	/**
	 * calculates and returns the square of {@code this}.
	 * 
	 * @return <code>this<sup>2</sup></code>
	 */
	public Complex square() {
		if (isIndeterminate())
			return powImaginaryUnit(new BigDecimal(2)).multiply(valueOf(signum()));
		BigDecimal i1, i2, i3, x = new BigDecimal(2);
		Complex last;
		i1 = Arith.pow(real, x, mc);
		i2 = real.multiply(imaginary).multiply(x);
		i3 = Arith.pow(imaginary, x, mc);
		last = powImaginaryUnit(x).multiply(i3);
		if (last.isImaginary())
			last = last.add(i2);
		else if (last.isReal())
			i1 = i1.add(last.real);
		else
			throw new ArithmeticException("unknown value");
		return new Complex(i1, last.isImaginary() ? last.imaginary : i2, mc);
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 22:20:13--------------------------------------------
	 */
	/**
	 * Compute the square root of <code>1 - this<sup>2</sup></code> for {@code this}
	 * number. Computes the result directly as
	 * {@code sqrt(ONE.subtract(z.multiply(z)))}.
	 * 
	 * @return the square root of <code>1 - this<sup>2</sup></code>
	 */
	public Complex sqrt1z() {
		return ONE.subtract(this.multiply(this)).sqrt();
	}

	/////////////////////////////////////////////////////////////////////
	/////////////////// Extra utility functions ////////////////////////
	///////////////////////////////////////////////////////////////////

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 10:21:27--------------------------------------------
	 */
	/**
	 * Converts this complex value to polar coordinates and returns the result as a
	 * 2 element array. This method functions as though
	 * <code>return new BigDecimal[] { magnitude(), argument() }</code> was called.
	 * The angle returned is in radians.
	 * 
	 * @return a 2 element array with index 0 containing the length of {@code this}
	 *         on the complex plane and index 1 containing the angle it's position
	 *         makes relative to the real axis
	 */
	public BigDecimal[] toPolarCoordinates() {
		BigDecimal[] p = { radius(), theta() };
		return p;
	}

	/**
	 * Converts this complex value to rectangular coordinates and returns the result
	 * as a 2 element array. All calculations are in radians.
	 * 
	 * @return a 2 element array with index 0 being
	 *         {@code radius() * cos(argument())} and index 1 being
	 *         {@code radius() * sin(argument())}.
	 */
	public BigDecimal[] toRectangularCoordinates() {
		BigDecimal[] p = toPolarCoordinates();
		BigDecimal x = p[0].multiply(Arith.cos(p[1], UNIT, mc));
		BigDecimal y = p[0].multiply(Arith.sin(p[1], UNIT, mc));
		return new BigDecimal[] { x, y };
	}

	/*
	 * Date: 27 Jul 2021-----------------------------------------------------------
	 * Time created: 11:21:54--------------------------------------------
	 */
	/**
	 * Calls {@code real.roun(MathContext)} and {@code imaginary.round(mathContext)}
	 * using the internal {@code MathContext} object as argument and afterward
	 * returns {@code this}.
	 * 
	 * @return {@code this} with both the real and imaginary part round according to
	 *         the internal rounder.
	 */
	public Complex round() {
		return round(mc);
	}

	/*
	 * Date: 27 Jul 2021-----------------------------------------------------------
	 * Time created: 11:25:09--------------------------------------------
	 */
	/**
	 * Calls {@code BigDecimal}'s round method on both the real and imaginary part
	 * using the supplied {@code MathContext}, then returns {@code this}.
	 * 
	 * @param mc a {@code MathContext} object for determining the significant
	 *           figures.
	 * @return {@code this} with both the real and imaginary value rounding
	 *         according to the provided context settings.
	 */
	public Complex round(MathContext mc) {
		return new Complex(real.round(mc), imaginary.round(mc), mc);
	}

	/*
	 * Date: 27 Jul 2021-----------------------------------------------------------
	 * Time created: 11:29:55--------------------------------------------
	 */
	/**
	 * Rounds both the real and imaginary values to the number of specified
	 * significant figures
	 * 
	 * @param significantFigures the number of significant figures to round to.
	 * @return {@code this} rounded to the given number of significant figures.
	 */
	public Complex round(int significantFigures) {
		return round(new MathContext(significantFigures, RoundingMode.HALF_EVEN));
	}

	/*
	 * Date: 27 Jul 2021-----------------------------------------------------------
	 * Time created: 13:00:08--------------------------------------------
	 */
	/**
	 * <p>
	 * Returns a TeX format of {@code this} as a complex decimal {@code String}. The
	 * result is a {@code String} formatted with the specified {@code decimalPoint},
	 * {@code integerDelimeter}, {@code mantissaDelimeter} and
	 * {@code digitsPerUnit}. For example:
	 * 
	 * <pre>
	 * Complex z = Complex.valueOf(-1234567890).sqrt().add(-1234567890);
	 * out.println(Utility.toTexString(z, '.', ',', ' ', 3)); // prints -1,234,568,000 + 35,136.42i
	 * </pre>
	 * 
	 * The approximation is due to using {@code Complex} static method, using the
	 * {@link mathaid.calculator.base.value.Complex#Complex(BigDecimal, BigDecimal, MathContext)
	 * complex constructor} will be less ambiguous. Note that this method does not
	 * take {@code Locale} into account.
	 * </p>
	 * 
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
	public String toTeXString(char decimalPoint, char integerDelimiter, char mantissaDelimiter, int digitsPerUnit) {
//		if (isReal()) {
//			return real.round(mc).toString();
//		} else if (isImaginary()) {
//			if (isIndeterminate())
//				return "i";
//			return imaginary.round(mc).toString() + "\\,i";
//		}
//		return real.round(mc).toString() + (imaginary.signum() == 1 ? "+" : "") + imaginary + "\\,i";
		return Utility.toTexString(this, decimalPoint, integerDelimiter, mantissaDelimiter, digitsPerUnit);
	}

	/////////////////////////////////////////////////////////////////////
	//////////////////////// Private methods ///////////////////////////
	///////////////////////////////////////////////////////////////////

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 09:21:11--------------------------------------------
	 */
	/**
	 * Selects the argument with the highest precision (as specified by
	 * {@link MathContext#getPrecision()}) and returns it.
	 * 
	 * @param m1 a {@code MathContext} object
	 * @param m2 a {@code MathContext} object regarded as different from the first
	 *           argument
	 * @return <code>new MathContext(Math.max(m1.getPrecision(), m2.getPrecision))</code>
	 */
	private MathContext getContext(MathContext m1, MathContext m2) {
		return new MathContext(Math.max(m1.getPrecision(), m2.getPrecision()), m1.getRoundingMode());
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 11:42:38--------------------------------------------
	 */
	/**
	 * Returns {@code true} if {@code x} is an instance of one of the following
	 * {@code Double}, {@code Float}, {@code Byte}, {@code Short}, {@code Integer},
	 * {@code Long}, {@code BigDecimal} and {@code BigInteger}.
	 * 
	 * @param x an instance of {@code java.lang.Number}
	 * @return true if it is one of the above
	 */
	private static boolean isSupported(Number x) {
		return x instanceof BigDecimal || x instanceof Double || x instanceof Float || x instanceof Long
				|| x instanceof Integer || x instanceof Short || x instanceof Byte || x instanceof BigInteger;
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 11:46:38--------------------------------------------
	 */
	/**
	 * Returns {@code true} if {@code x} is an integer
	 * 
	 * @param x the value to be evaluated
	 * @return true if the argument x is an integer representation of
	 *         {@link #isSupported(Number) java's Number}
	 */
	private static boolean isInteger(Number x) {
		if (isSupported(x)) {
			if (x instanceof BigDecimal)
				return Utility.isInteger((BigDecimal) x);
			if (x instanceof Double || x instanceof Float) {
				Double a = Math.abs(Math.floor(x.doubleValue()));
				Double b = Math.abs(Math.ceil(x.doubleValue()));
				Double c = Math.abs(x.doubleValue());
				BigDecimal bd = new BigDecimal(c.toString());
				bd = bd.subtract(new BigDecimal(bd.toBigInteger()));
				return a == b && b == c && a == c && bd.compareTo(BigDecimal.ZERO) == 0;
			}
			if (x instanceof Long || x instanceof Integer || x instanceof Short || x instanceof Byte
					|| x instanceof BigInteger)
				return true;
		}
		return Fraction.isInteger(x.doubleValue());
	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 08:55:06--------------------------------------------
	 */
	/**
	 * Calculates the result of the imaginary unit <i>i</i> raised to the power of
	 * the argument. This is an auxillary method used in exponentiation function.
	 * 
	 * @param x the exponent of the imaginary unit
	 * @return <i>i</i> <sup>x</sup>
	 */
	private static Complex powImaginaryUnit(BigDecimal x) {
		BigDecimal rem = x.remainder(new BigDecimal(4));
		switch (rem.intValue()) {
		case 0:
			return ONE;
		case 1:
			return I;
		case 2:
			return ONE.negate();
		case 3:
		default:
		}
		return I.negate();
	}

//	public Complex erf() {
//		IExpr ex = F.N(F.Erf(ApcomplexNum.valueOf(new Apfloat(real), new Apfloat(imaginary))));
//		return valueOf(ex.toString());
//	}

	/*
	 * Date: 26 Jul 2021-----------------------------------------------------------
	 * Time created: 22:16:40--------------------------------------------
	 */
	/**
	 * Returns {@code true} if the argument is less than or equal to
	 * {@link #MAX_ITER}
	 * 
	 * @param it an {@code int} value to be checked
	 * @return {@code true} if <code>it <= {@value #MAX_ITER}</code> and
	 *         {@code false} if otherwise
	 */
	private static boolean isWithinIteration(int it) {
		return it <= MAX_ITER;
	}

	/////////////////////////////////////////////////////////////////////
	///////////////////// Public static methods ////////////////////////
	///////////////////////////////////////////////////////////////////

	/*
	 * Date: 27 Jul 2021-----------------------------------------------------------
	 * Time created: 11:57:53--------------------------------------------
	 */
	/**
	 * Creates a {@code Complex} representation of the input argument as a real
	 * value or an angle representation of a complex value on the complex plane.
	 * <p>
	 * If the argument is a {@code Complex} value then it is simply returned
	 * without further conversion. Otherwise if the argument is not an instance
	 * of any one of java's primitive numbers (as an object) or is not a
	 * {@code BigDecimal} or is not a {@code BigInteger} then it {@code doubleValue()}
	 * method is called.
	 * </p>
	 * 
	 * @param n       the value to be parsed.
	 * @return a {@code Complex} object which is a representation of the argument.
	 */
	public static Complex valueOf(Number n) {
		if (isSupported(n))
			return new Complex(new BigDecimal(n.toString()), BigDecimal.ZERO);
		else if(n instanceof Complex) return (Complex) n;
		return new Complex(new BigDecimal(String.valueOf(n.doubleValue())), BigDecimal.ZERO);
	}

	/*
	 * Date: 27 Jul 2021-----------------------------------------------------------
	 * Time created: 12:07:03--------------------------------------------
	 */
	/**
	 * Parses a {@code String} into a {@code Complex} number.
	 * <p>
	 * The {@code String} can be in the following format:
	 * 
	 * <pre>
	 * [real][imaginary sign][imaginary magnitude]i
	 * </pre>
	 * 
	 * If the argument is meant to exclusively be a real or an imaginary value, then
	 * the format for {@link BigDecimal#BigDecimal(String) BigDecimal strings} may
	 * be uses, although the imaginary value must have the 'i' suffix appended to
	 * it. If this is an imaginary unit then the 'i' character is sufficient,
	 * although a sign may be specified.
	 * </p>
	 * 
	 * @param z the string to be parsed.
	 * @return a {@code Complex} parsed from the input string.
	 */
	public static Complex valueOf(String z) {
		if (z.length() == 2 || z.length() == 1)
			if (z.equals("i"))
				return I;
			else if (z.contains("i") && (z.charAt(0) == '-' || z.charAt(0) == '+'))
				return z.charAt(0) == '+' ? I : I.negate();
		BigDecimal real, imaginary;
		String re, im;
		StringBuilder sb = new StringBuilder();
		re = z.contains("+") && z.indexOf("+") > 0 && z.indexOf("+") != z.length() - 1
				? z.substring(0, z.lastIndexOf("+"))
				: z.contains("-") && z.indexOf("-") > 0 && z.indexOf("-") != z.length() - 1
						? z.substring(0, z.lastIndexOf("-"))
						: z;
		sb.append(re);
		sb = new StringBuilder(sb.toString().replaceAll(" ", ""));
		try {
			real = new BigDecimal(sb.toString());
		} catch (@SuppressWarnings("unused") NumberFormatException e) {
			throw new NumberFormatException("Real part is not recognised");
		}
		sb.delete(0, sb.length());
		try {
			im = z.substring(z.indexOf(re) + re.length());
			sb.append(im);
			sb = new StringBuilder(sb.toString().replaceAll(" ", ""));
			if (sb.charAt(sb.length() - 1) == 'i')
				sb.deleteCharAt(sb.length() - 1);

			imaginary = new BigDecimal(sb.toString());
		} catch (@SuppressWarnings("unused") IndexOutOfBoundsException e) {
			imaginary = BigDecimal.ZERO;
		} catch (@SuppressWarnings("unused") NumberFormatException e) {
			throw new NumberFormatException("Imaginary part is not recognised");
		}

		return new Complex(real, imaginary, new MathContext(Math.max(real.precision(), imaginary.precision())));
	}

	/*
	 * Date: 27 Jul 2021-----------------------------------------------------------
	 * Time created: 12:03:54--------------------------------------------
	 */
	/**
	 * Creates a {@code Complex} representation of the input angle. the value
	 * returned is the approximate value and not the precise value. Does not seem to
	 * work correctly.
	 * 
	 * @param theta the corresponding angle in radians
	 * @return a {@code Complex} number that corresponds with the given angle on the
	 *         complex plane.
	 */
	static Complex parse(Number theta) {// TODO: doesn't seem to work correctly
		BigDecimal angle;
		if (isSupported(theta))
			angle = new BigDecimal(theta.toString());
		else
			angle = new BigDecimal(Double.toString(theta.doubleValue()));

		MathContext mc = MathContext.DECIMAL128;
		angle = Arith.tan(angle, UNIT, mc);
		BigFraction f = new BigFraction(angle, mc, null, BigFraction.HALF.getAccuracy());

		return new Complex(new BigDecimal(f.getDenominator()), new BigDecimal(f.getNumerator()), mc);
	}

	/////////////////////////////////////////////////////////////////////
	/////////////////////// Overridden methods /////////////////////////
	///////////////////////////////////////////////////////////////////

	/*
	 * Date: 27 Jul 2021-----------------------------------------------------------
	 * Time created: 11:32:10--------------------------------------------
	 */
	/**
	 * Provides support for the comparison operator useful for
	 * {@link java.util.TreeMap ordered maps} by comparing the magnitude of
	 * {@code this} and the argument. Unlike real number representations such as
	 * {@code java.math.BigDecimal} and {@code java.math.BigInteger} classes, a
	 * {@code Complex} value cannot be linearly ordered because they exist on a 2d
	 * plane (Cartesian) and not on a number line. However, this method provides a
	 * way to perform ordering in the case where it is need for example in
	 * collection-based structures.
	 * 
	 * @param c the value to be compared to {@code this}.
	 * @return 1, 0 or -1 as {@code this} has a/an greater, equal or lesser
	 *         magnitude than the argument respectively.
	 */
	public int compareTo(Complex c) {
		return radius().compareTo(c.radius());
	}

	/*
	 * Most Recent Date: 27 Jul 2021-----------------------------------------------
	 * Most recent time created: 12:07:33--------------------------------------
	 */
	/**
	 * Returns the integer form of the real part as much as an {@code int} may
	 * allow. This may result in negative values for a sufficiently large real part.
	 * 
	 * @return the real part in integer form as an {@code int}.
	 */
	@Override
	public int intValue() {
		return real.intValue();
	}

	/*
	 * Most Recent Date: 27 Jul 2021-----------------------------------------------
	 * Most recent time created: 12:08:27--------------------------------------
	 */
	/**
	 * Returns the integer form of the real part as much as a {@code long} may
	 * allow. This may result in negative values for a sufficiently large real part.
	 * 
	 * @return the real part in integer form as a {@code long}.
	 */
	@Override
	public long longValue() {
		return real.longValue();
	}

	/*
	 * Most Recent Date: 27 Jul 2021-----------------------------------------------
	 * Most recent time created: 12:11:11--------------------------------------
	 */
	/**
	 * Returns the real part as much as a {@code float} may allow. This may result
	 * in infinity and NaN values for a sufficiently large or sufficiently small
	 * real part.
	 * 
	 * @return the real part as a {@code float}.
	 */
	@Override
	public float floatValue() {
		return real.floatValue();
	}

	/*
	 * Most Recent Date: 27 Jul 2021-----------------------------------------------
	 * Most recent time created: 12:13:10--------------------------------------
	 */
	/**
	 * Returns the real part as much as a {@code double} may allow. This may result
	 * in infinity and NaN values for a sufficiently large or sufficiently small
	 * real part.
	 * 
	 * @return the real part as a {@code double}.
	 */
	@Override
	public double doubleValue() {
		return real.doubleValue();
	}

	/*
	 * Most Recent Date: 27 Jul 2021-----------------------------------------------
	 * Most recent time created: 11:54:56--------------------------------------
	 */
	/**
	 * Returns {@code false} if this is not a {@code Complex} object else returns
	 * {@link #equals(Complex)}
	 * 
	 * @param x {@inheritDoc}
	 * @return {@code true} if and only if the argument is a {@code Complex} object
	 *         and {@code this} is numerically equal to it or else returns
	 *         {@code false}
	 */
	@Override
	public boolean equals(Object x) {
		if (x instanceof Complex) {
			Complex c = (Complex) x;
			return equals(c) && mc.equals(c.mc);
		}
		return false;
	}

	/*
	 * Most Recent Date: 27 Jul 2021-----------------------------------------------
	 * Most recent time created: 12:14:17--------------------------------------
	 */
	/**
	 * Returns the {@code String} representation of a {@code Complex} number.
	 * <p>
	 * If {@link #isZero()} returns {@code true} then just {@code 0} is returned. If
	 * {@code this} is a real value, then {@code BigDecimal} string conversion
	 * pipeline is used. If {@link #isIndeterminate()} returns {@code true} then
	 * only the character {@code i} is returned. If {@code this} is imaginary then
	 * the value concatenated to the "i" character is returned. If this is neither
	 * real nor imaginary, then
	 * <code>[real][imaginary sign][imaginary absolute value]</code> is returned.
	 * </p>
	 * 
	 * @return a {@code String} representation of this {@code Complex} number.
	 */
	@Override
	public String toString() {
		if (isZero())
			return "0";
		if (isReal())
			return real.round(mc).toString();
		if (isIndeterminate())
			return imaginary.signum() < 0 ? "-i" : "i";
		if (isImaginary())
			return imaginary.round(mc).toString() + "i";
		StringBuilder sb = new StringBuilder(real.round(mc).toString()).append(imaginary.signum() < 0 ? "-" : "+")
				.append(imaginary.abs(mc)).append("i");
		return sb.toString();
	}

	/////////////////////////////////////////////////////////////////////
	//////////////////////////// Fields ////////////////////////////////
	///////////////////////////////////////////////////////////////////

	/**
	 * The real value
	 */
	private final BigDecimal real;
	/**
	 * The imaginary value
	 */
	private final BigDecimal imaginary;
	/**
	 * The precision object used for numerical approximations
	 */
	private final MathContext mc;

}
