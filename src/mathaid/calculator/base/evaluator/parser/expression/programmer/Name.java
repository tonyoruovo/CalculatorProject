/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.programmer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import mathaid.calculator.base.evaluator.Calculator;
import mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression.Params.ResultType;
import mathaid.calculator.base.typeset.Digits;
import mathaid.calculator.base.typeset.Segment;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;
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
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Name extends PExpression {

	public Name(String name, Params params, Class<?> k) {
		super(params);
		if (k == BigInteger.class) {
			this.integer = new BigInteger(name, params.getRadix());
			this.fp = null;
			this.name = null;
			this.carry = null;
		} else if (k == BinaryFP.class) {
			this.fp = getPrecision().createFP(name, params.getRadix());
			this.integer = null;
			this.name = null;
			this.carry = null;
		} else if (k == String.class) {
			this.fp = null;
			this.integer = null;
			this.name = name;
			this.carry = null;
		} else
			throw new IllegalArgumentException();
	}

	public Name(BigInteger n, BigInteger carry, Params params) {
		super(params);
		this.integer = n;
		this.fp = null;
		this.name = null;
		this.carry = carry;
	}

	public Name(BinaryFP n, Params params) {
		super(params);
		this.fp = n;
		this.integer = null;
		this.name = null;
		this.carry = n.getCarry();
	}

	@Override
	public BigInteger getCarry() {
		return carry;
	}

	/*
	 * Most Recent Date: 8 Oct 2022-----------------------------------------------
	 * Most recent time created: 10:18:18--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param formatBuilder
	 */
	@Override
	public void format(SegmentBuilder formatBuilder) {
		String[] c = FloatAid.getComponents(name, getParams().getRadix());
		if (FloatAid.isNumber(c))
			switch (getParams().getBitRepresentation()) {
			case Params.ResultType.REP_FLOATING_POINT: {
				formatBuilder.append(Digits.toSegment(fp, getParams().getRadix(), 10,
						getParams().getResultType() == ResultType.NORMALISED, getParams().getDecimalPoint().charAt(0),
						Calculator.fromParams(getParams(), Segment.Type.VINCULUM)));
			}
			default: {
				formatBuilder.append(Digits.toRadixedSegment(integer, getParams().getRadix(),
						Calculator.fromParams(getParams(), Segment.Type.VINCULUM)));
			}
			}
		else
			formatBuilder.append(Segments.boundVariable(name, name));// NaN, +-Infinity, -0.0
	}

	/*
	 * Most Recent Date: 8 Oct 2022-----------------------------------------------
	 * Most recent time created: 10:18:18--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * Calling evaluate twice will mutate the internal value, albeit, this class is
	 * immutable
	 * 
	 * @return
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
	 * {@inheritDoc}
	 * 
	 * @return
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
	 * {@inheritDoc}
	 * 
	 * @return
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
	 * {@inheritDoc}
	 * 
	 * @return
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
	 * {@inheritDoc}
	 * 
	 * @param o
	 * @return
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
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public int hashCode() {
		return getName().hashCode() ^ Objects.hash(this);
	}

	/*
	 * Most Recent Date: 10 Oct 2022-----------------------------------------------
	 * Most recent time created: 21:25:08--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return isInteger() ? integer.toString() : fp.toString();
	}

	private final BigInteger integer;
	private final BigInteger carry;
	private final BinaryFP fp;
	private final String name;
}
