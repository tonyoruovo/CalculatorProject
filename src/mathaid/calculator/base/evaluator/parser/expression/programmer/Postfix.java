/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.programmer;

import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.C_DECREMENT;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.C_INCREMENT;

import java.math.BigInteger;

import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;
import mathaid.calculator.base.util.Utility;
import mathaid.calculator.base.value.Ex;
import mathaid.calculator.base.value.FloatAid;
import mathaid.calculator.base.value.MT;
import mathaid.calculator.base.value.NB;
import mathaid.calculator.base.value.OC;
import mathaid.calculator.base.value.SMR;
import mathaid.calculator.base.value.TC;
import mathaid.calculator.base.value.US;

/*
 * Date: 18 Oct 2022----------------------------------------------------------- 
 * Time created: 08:15:59---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression.programmer------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Postfix.java------------------------------------------------------ 
 * Class name: Postfix------------------------------------------------ 
 */
/**
 * An expression that represents unary arithmetic operator that are on the right side of an operand.
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Postfix extends Name {
	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 09:15:35 ---------------------------------------------------
	 */
	/**
	 * Constructor for creating a {@code Postfix} expression.
	 * @param left the sole operand.
	 * @param name the symbol of this operator.
	 * @param params the {@code ExpressionParams} representing options for the evaluation and format within this expression.
	 */
	public Postfix(PExpression left, String name, Params params) {
		super(name, params, String.class);
		this.left = left;
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 09:16:34 ---------------------------------------------------
	 */
	/**
	 * Gets the sole operand of this postfix operator.
	 * @return the sole operand.
	 */
	public PExpression getLeft() {
		return left;
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 09:17:18 ---------------------------------------------------
	 */
	/**
	 * Appends the {@code LinkedSegment} representation of this operator and it's operand into the format builder.
	 * <p>
	 * The format is ordered so: operand, symbol.
	 * <p>
	 * This has no side-effects.
	 * 
	 * @param f {@inheritDoc}
	 */
	@Override
	public void format(SegmentBuilder f) {
		left.format(f);
		f.append(Segments.operator(getName(), getName()));
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 09:17:50 ---------------------------------------------------
	 */
	/**
	 * Evaluates this unary operator and returns the result.
	 * <p>
	 * The operand will have it's {@link EvaluatableExpression#evaluate() evaluate()} method called before the final evaluation
	 * is done.
	 * <p>
	 * This has no side-effects.
	 * 
	 * @return a new {@code EvaluatableExpression} that is the result of evaluating this operator. May return the same object
	 *         especially if called more than once.
	 */
	@Override
	public Name evaluate() {
		Params p = getParams();
		switch (getName()) {
		case C_INCREMENT: {
			PExpression x = left.evaluate();
			if(x.isFloatingPoint()) return new Name(x.getFloatingPoint().add(getPrecision().createFP("1")), p);
			else if(x.isInteger()) {
				switch (getRep()) {
				case EXCESS_K: {
					BigInteger[] r = { x.getInteger(), x.getCarry() };
					MT.fromEx(p.getBitLength(), r);
					r[0] = r[0].add(Utility.i(1));
					Ex.fromDecimal(p.getBitLength(), r);
					return new Name(r[0], r[1], p);
				}
				case MATH: {
					BigInteger n = x.getInteger().add(Utility.i(1));
					BigInteger c = x.getCarry();
					if (n.abs().bitLength() > p.getBitLength()) {
						c = n.shiftRight(p.getBitLength()).testBit(0) ? BigInteger.ONE : BigInteger.ZERO;
						int sign = n.signum();
						n = FloatAid.clearMSB(n.abs(), n.abs().bitLength() - p.getBitLength()).multiply(BigInteger.valueOf(sign));
					}
					return new Name(n, c, p);
				}
				case NEGABINARY: {
					BigInteger[] r = { x.getInteger(), x.getCarry() };
					MT.fromNB(p.getBitLength(), r);
					r[0] = r[0].add(Utility.i(1));
					NB.fromDecimal(p.getBitLength(), r);
					return new Name(r[0], r[1], p);
				}
				case ONE_C: {
					BigInteger[] r = { x.getInteger(), x.getCarry() };
					MT.fromOC(p.getBitLength(), r);
					r[0] = r[0].add(Utility.i(1));
					OC.fromDecimal(p.getBitLength(), r);
					return new Name(r[0], r[1], p);
				}
				case SMR: {
					BigInteger[] r = { x.getInteger(), x.getCarry() };
					MT.fromSMR(p.getBitLength(), r);
					r[0] = r[0].add(Utility.i(1));
					SMR.fromDecimal(p.getBitLength(), r);
					return new Name(r[0], r[1], p);
				}
				default:
				case TWO_C: {
					BigInteger[] r = { x.getInteger(), x.getCarry() };
					MT.fromTC(p.getBitLength(), r);
					r[0] = r[0].add(Utility.i(1));
					TC.fromDecimal(p.getBitLength(), r);
					return new Name(r[0], r[1], p);
				}
				case UNSIGNED: {
					BigInteger[] r = { x.getInteger(), x.getCarry() };
					MT.fromUS(p.getBitLength(), r);
					r[0] = r[0].add(Utility.i(1));
					US.fromDecimal(p.getBitLength(), r);
					return new Name(r[0], r[1], p);
				}
				}
			}
			break;
		}
		case C_DECREMENT: {
			PExpression x = left.evaluate();
			if(x.isFloatingPoint()) return new Name(x.getFloatingPoint().subtract(getPrecision().createFP("1")), p);
			else if(x.isInteger()) {
				switch (getRep()) {
				case EXCESS_K: {
					BigInteger[] r = { x.getInteger(), x.getCarry() };
					MT.fromEx(p.getBitLength(), r);
					r[0] = r[0].subtract(Utility.i(1));
					Ex.fromDecimal(p.getBitLength(), r);
					return new Name(r[0], r[1], p);
				}
				case MATH: {
					BigInteger n = x.getInteger().subtract(Utility.i(1));
					BigInteger c = x.getCarry();
					if (n.abs().bitLength() > p.getBitLength()) {
						c = n.shiftRight(p.getBitLength()).testBit(0) ? BigInteger.ONE : BigInteger.ZERO;
						int sign = n.signum();
						n = FloatAid.clearMSB(n.abs(), n.abs().bitLength() - p.getBitLength()).multiply(BigInteger.valueOf(sign));
					}
					return new Name(n, c, p);
				}
				case NEGABINARY: {
					BigInteger[] r = { x.getInteger(), x.getCarry() };
					MT.fromNB(p.getBitLength(), r);
					r[0] = r[0].subtract(Utility.i(1));
					NB.fromDecimal(p.getBitLength(), r);
					return new Name(r[0], r[1], p);
				}
				case ONE_C: {
					BigInteger[] r = { x.getInteger(), x.getCarry() };
					MT.fromOC(p.getBitLength(), r);
					r[0] = r[0].subtract(Utility.i(1));
					OC.fromDecimal(p.getBitLength(), r);
					return new Name(r[0], r[1], p);
				}
				case SMR: {
					BigInteger[] r = { x.getInteger(), x.getCarry() };
					MT.fromSMR(p.getBitLength(), r);
					r[0] = r[0].subtract(Utility.i(1));
					SMR.fromDecimal(p.getBitLength(), r);
					return new Name(r[0], r[1], p);
				}
				default:
				case TWO_C: {
					BigInteger[] r = { x.getInteger(), x.getCarry() };
					MT.fromTC(p.getBitLength(), r);
					r[0] = r[0].subtract(Utility.i(1));
					TC.fromDecimal(p.getBitLength(), r);
					return new Name(r[0], r[1], p);
				}
				case UNSIGNED: {
					BigInteger[] r = { x.getInteger(), x.getCarry() };
					MT.fromUS(p.getBitLength(), r);
					r[0] = r[0].subtract(Utility.i(1));
					US.fromDecimal(p.getBitLength(), r);
					return new Name(r[0], r[1], p);
				}
				}
			}
			break;
		}
		}
//		return new Postfix(left.evaluate(), getName(), getParams());
		return this;
	}

	/**
	 * Holds the sole operand.
	 */
	private final PExpression left;
}
