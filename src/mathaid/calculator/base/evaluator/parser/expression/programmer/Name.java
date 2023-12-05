/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.programmer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression.Params.ResultType;
import mathaid.calculator.base.typeset.Digits;
import mathaid.calculator.base.typeset.LinkedSegment;
import mathaid.calculator.base.typeset.Segment;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;
import mathaid.calculator.base.value.BinaryFPPrecision;
import mathaid.calculator.base.value.BinaryFPPrecision.BinaryFP;
import mathaid.calculator.base.value.FloatAid;

/*
 * Date: 8 Oct 2022----------------------------------------------------------- 
 * Time created: 09:59:41---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression.programmer------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Name.java------------------------------------------------------ 
 * Class name: Name------------------------------------------------ 
 */
/**
 * Represents constants and numbers. Because hex numbers are supported, upper case A through F is not allowed for naming of
 * constants. Constant names are expected to be in lower case.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Name extends PExpression {

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:07:40 ---------------------------------------------------
	 */
	/**
	 * Constructor for creating a {@code Name} expression.
	 * <p>
	 * This is also likely the constructor be overridden by sub-classes.
	 * 
	 * @param name   the value of the name which can be alphanumeric (for integers and floating point) or can be alphabetic (for
	 *               variables and constants).
	 *               <p>
	 *               For floating point values, the format must conform to {@link BinaryFPPrecision#createFP(String, int)}. For
	 *               integers, values must be in the format specified by {@link BigInteger#BigInteger(String, int)}. For variables
	 *               and constants, only single non-decimal-digit characters are supported.
	 * @param params the {@code Params} representing options for the evaluation and format within this expression.
	 * @param k      the value specifying the type of string input. Only 3 values are valid namely:
	 *               <ul>
	 *               <li><code>BigInteger.class</code>: specifies that {@code name} is an integer.</li>
	 *               <li><code>BinaryFP.class</code>: specifies that {@code name} is a floating-point.</li>
	 *               <li><code>String.class</code>: specifies that {@code name} is a variable/constant.</li>
	 *               </ul>
	 */
	public Name(String name, Params params, Class<?> k) {
		super(params);
		if (k.equals(BigInteger.class)) {
			this.integer = new BigInteger(name, params.getRadix());
			this.fp = null;
			this.name = null;
			this.carry = null;
		} else if (k.equals(BinaryFP.class)) {
			this.integer = null;
			this.fp = getPrecision().createFP(name, params.getRadix());
			this.name = null;
			this.carry = null;
		} else if (k.equals(String.class)) {
			this.fp = null;
			this.integer = null;
			this.name = name;
			this.carry = null;
		} else
			throw new IllegalArgumentException();
	}

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:21:55 ---------------------------------------------------
	 */
	/**
	 * Constructor for creating a {@code Name} as an integer.
	 * 
	 * @param n      the literal value.
	 * @param carry  the carry/overflow bit.
	 * @param params the {@code Params} representing options for the evaluation and format within this expression.
	 */
	public Name(BigInteger n, BigInteger carry, Params params) {
		super(params);
		this.integer = n;
		this.fp = null;
		this.name = null;
		this.carry = carry;
	}

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:22:58 ---------------------------------------------------
	 */
	/**
	 * Constructor for creating a {@code Name} as a floating-point.
	 * 
	 * @param n      the literal value.
	 * @param params the {@code Params} representing options for the evaluation and format within this expression.
	 */
	public Name(BinaryFP n, Params params) {
		super(params);
		this.fp = n;
		this.integer = null;
		this.name = null;
		this.carry = n.getCarry();
	}

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:24:04 ---------------------------------------------------
	 */
	/**
	 * Gets the carry/overflow bit.
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public BigInteger getCarry() {
		return carry;
	}

	/*
	 * Most Recent Date: 8 Oct 2022-----------------------------------------------
	 * Most recent time created: 10:18:18--------------------------------------
	 */
	/**
	 * Appends the {@code LinkedSegment} representation of {@code this}.
	 * <p>
	 * All calculations are done with a numerical precision of <code>{@linkplain Params#getScale() scale} + 5</code> and may throw
	 * exceptions that indicate that value(s) were out of range.
	 * <p>
	 * All sub-classes will override this method to provide their own evaluation unique to them.
	 * <p>
	 * This has no side-effects.
	 * 
	 * @param formatBuilder the builder to which the {@link LinkedSegment} representation of {@code this} will be appended.
	 */
	@Override
	public void format(SegmentBuilder formatBuilder) {
		String[] c = FloatAid.getComponents(name, getParams().getRadix());
		if (FloatAid.isNumber(c))
			switch (getParams().getBitRepresentation()) {
			case Params.ResultType.REP_FLOATING_POINT: {
				formatBuilder.append(Digits.toSegment(fp, getParams().getRadix(), 10,
						getParams().getResultType() == ResultType.NORMALISED,
						EvaluatableExpression.fromParams(getParams(), Segment.Type.VINCULUM)));
			}
			default: {
				formatBuilder.append(Digits.toRadixedSegment(integer, getParams().getRadix(),
						EvaluatableExpression.fromParams(getParams(), Segment.Type.VINCULUM)));
			}
			}
		else if (getParams().getConstants().containsKey(getName())) {
			formatBuilder.append(Segments.constant(getParams().getConstants().get(getName()).get(), name));
		} else if(getParams().getBoundVariables().containsKey(getName()))
			formatBuilder.append(Segments.boundVariable(getParams().getBoundVariables().get(getName()).get(), name));// NaN, +-Infinity, -0.0
	}

	/*
	 * Most Recent Date: 8 Oct 2022-----------------------------------------------
	 * Most recent time created: 10:18:18--------------------------------------
	 * Calling evaluate twice will mutate the internal value, albeit, this class is immutable
	 */
	/**
	 * Computes this {@code Name} if it is a bound variable or constant else just returns the same value albeit as a new object.
	 * <p>
	 * All sub-classes will override this method to provide their own evaluation unique to them.
	 * <p>
	 * This creates no side-effects.
	 * 
	 * @return a new {@code Name} that is a number (if {@code getFloatingPoint() != null && getInteger() != null}) or the same
	 *         object.
	 */
	/*
	 * TODO: We need a way to convert to the correct endianess even though this
	 * method is called more than once. I propose that we do it at the NameParselet.
	 * However how can we tell that the current format that a number is in? I think
	 * the answer to that question is to only convert if the bitRep != floating
	 * point. Methods that will convert from floating point to integer or vice-versa
	 * can have their own self contained convert method.
	 */
	@Override
	public PExpression evaluate() {
		Params p = getParams();
		if (name != null)
			if (p.getConstants().containsKey(name)) {
				SegmentBuilder sb = p.getConstants().get(name).get2nd().call(p);
				StringBuilder prs = new StringBuilder();
				List<Integer> a = new ArrayList<>();
				a.add(-1);
				sb.toSegment().toString(prs, null, a);
				return new Name(prs.toString(), p,
						p.getResultType() == ResultType.NORMALISED ? BinaryFP.class : BigInteger.class);
			} else if (p.getBoundVariables().containsKey(name)) {
				SegmentBuilder sb = p.getBoundVariables().get(name).get2nd().call(p);
				StringBuilder prs = new StringBuilder();
				List<Integer> a = new ArrayList<>();
				a.add(-1);
				sb.toSegment().toString(prs, null, a);
				return new Name(prs.toString(), p,
						p.getResultType() == ResultType.NORMALISED ? BinaryFP.class : BigInteger.class);
			}
		return this;
	}

	/*
	 * Most Recent Date: 29 Nov 2022-----------------------------------------------
	 * Most recent time created: 21:27:09--------------------------------------
	 */
	/**
	 * Gets the floating-point representation. May be <code>null</code> of {@code this} was not instantiated as a floating-point.
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public BinaryFP getFloatingPoint() {
		return fp;
	}

	/*
	 * Most Recent Date: 29 Nov 2022-----------------------------------------------
	 * Most recent time created: 21:27:09--------------------------------------
	 */
	/**
	 * The integer representation. May be <code>null</code> of {@code this} was not instantiated as an integer.
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public BigInteger getInteger() {
		return integer;
	}

	/*
	 * Most Recent Date: 8 Oct 2022-----------------------------------------------
	 * Most recent time created: 10:18:18--------------------------------------
	 */
	/**
	 * Gets the literal value.
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name != null ? name
				: isInteger() ? integer.toString(getParams().getRadix())
						: getParams().getResultType() == ResultType.NORMALISED
								? fp.toNormalisedString(getParams().getRadix())
								: fp.toString(getParams().getRadix());
	}

	/*
	 * Most Recent Date: 8 Oct 2022-----------------------------------------------
	 * Most recent time created: 10:18:18--------------------------------------
	 */
	/**
	 * Checks whether {@code o} is an instance of {@code Name} and both their {@link #getName()} is equal.
	 * 
	 * @param o the value to be compared.
	 * @return <code>getName().equals(((Name) o).getName())</code>.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Name) {
			return getName().equals(((Name) o).getName());
		}
		return false;
	}

	/*
	 * Most Recent Date: 8 Oct 2022-----------------------------------------------
	 * Most recent time created: 10:18:18--------------------------------------
	 */
	/**
	 * Gets the hash-code of the value returned by {@link #getName()}.
	 * 
	 * @return {@code getName().hashCode()}.
	 */
	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	/*
	 * Most Recent Date: 10 Oct 2022-----------------------------------------------
	 * Most recent time created: 21:25:08--------------------------------------
	 */
	/**
	 * Returns {@link #getName()}
	 * 
	 * @return {@code getName()}
	 */
	@Override
	public String toString() {
		return isInteger() ? integer.toString() : fp.toString();
	}

	/**
	 * The integer representation. May be <code>null</code> of {@code this} was not instantiated as an integer.
	 */
	private final BigInteger integer;
	/**
	 * The carry/overflow bit.
	 */
	private final BigInteger carry;
	/**
	 * The floating-point representation. May be <code>null</code> of {@code this} was not instantiated as a floating-point.
	 */
	private final BinaryFP fp;
	/**
	 * The variable/constant representation. May be <code>null</code> of {@code this} was not instantiated as a variable/constant.
	 */
	private final String name;
}
