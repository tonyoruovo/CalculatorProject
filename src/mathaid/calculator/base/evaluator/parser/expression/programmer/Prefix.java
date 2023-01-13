/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.programmer;

import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.BOOLEAN_NOT;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.C_NOT;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.MATH_NOT;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.MINUS;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.NOT;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.PLUS;

import java.math.BigDecimal;
import java.math.BigInteger;

import mathaid.calculator.base.typeset.Digits;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;
import mathaid.calculator.base.util.Utility;
import mathaid.calculator.base.value.BinaryFPPrecision.BinaryFP;
import mathaid.calculator.base.value.Ex;
import mathaid.calculator.base.value.FloatAid;
import mathaid.calculator.base.value.MT;
import mathaid.calculator.base.value.NB;
import mathaid.calculator.base.value.OC;
import mathaid.calculator.base.value.SMR;
import mathaid.calculator.base.value.TC;
import mathaid.calculator.base.value.US;

/*
 * Date: 15 Oct 2022----------------------------------------------------------- 
 * Time created: 21:32:30---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression.programmer------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Prefix.java------------------------------------------------------ 
 * Class name: Prefix------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Prefix extends Name {

	public Prefix(String name, PExpression right, Params params) {
		super(name, params, String.class);
		this.right = right;
	}

	public PExpression getRight() {
		return right;
	}

	private boolean isNegation() {
		return getName().equals(MINUS);
	}

	private boolean isPlus() {
		return getName().equals(PLUS);
	}

	@Override
	public void format(SegmentBuilder f) {
		switch (getName()) {
		case NOT:
			f.append(Segments.operator("~NOT~", getName()));
			break;
		case C_NOT:
		case MATH_NOT:
			f.append(Segments.operator("\\sim", getName()));
			break;
		default:
			f.append(isNegation() ? Digits.prefixMinus()
					: isPlus() ? Digits.prefixPlus()
							: Segments.operator(getName(), getName()));
		}
		right.format(f);
	}

	@Override
	public PExpression evaluate() {
		Params p = getParams();
		switch (getName()) {
		case ".":{
			PExpression x = this.right.evaluate();
			if(x.isInteger()) {
				BigDecimal dividend = new BigDecimal(x.getInteger());
				BigDecimal divisor = BigDecimal.ONE.scaleByPowerOfTen(dividend.precision());
				BinaryFP ans = getPrecision().createFP(dividend.divide(divisor, getPrecision().getMathContext(10, false, 2)));
				return new Name(ans, p);
			} else if(x.isFloatingPoint()) {
				BigDecimal dividend = x.getFloatingPoint().toBigDecimal();
				BigDecimal divisor = BigDecimal.ONE.scaleByPowerOfTen(Utility.numOfFractionalDigits(dividend) + Utility.numOfIntegerDigits(dividend));
				BinaryFP ans = getPrecision().createFP(dividend.divide(divisor, getPrecision().getMathContext(10, false, 2)));
				return new Name(ans, p);
			}
			throw new ArithmeticException(x.getName() + " is not a number");
		}
		case PLUS:
			return this.right.evaluate();
		case MINUS: {
			PExpression x = this.right.evaluate();
			if (x.isFloatingPoint())
				return new Name(x.getFloatingPoint().negate(), p);
			else if (x.isInteger()) {
				switch (getRep()) {
				case EXCESS_K: {
					BigInteger[] r = { x.getInteger(), x.getCarry() };
					MT.fromEx(p.getBitLength(), r);
					r[0] = r[0].negate();
					Ex.fromDecimal(p.getBitLength(), r);
					return new Name(r[0], r[1], p);
				}
				case MATH: {
					BigInteger n = x.getInteger().negate();
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
					r[0] = r[0].negate();
					NB.fromDecimal(p.getBitLength(), r);
					return new Name(r[0], r[1], p);
				}
				case ONE_C: {
					BigInteger[] r = { x.getInteger(), x.getCarry() };
					MT.fromOC(p.getBitLength(), r);
					r[0] = r[0].negate();
					OC.fromDecimal(p.getBitLength(), r);
					return new Name(r[0], r[1], p);
				}
				case SMR: {
					BigInteger[] r = { x.getInteger(), x.getCarry() };
					MT.fromSMR(p.getBitLength(), r);
					r[0] = r[0].negate();
					SMR.fromDecimal(p.getBitLength(), r);
					return new Name(r[0], r[1], p);
				}
				default:
				case TWO_C: {
					BigInteger[] r = { x.getInteger(), x.getCarry() };
					MT.fromTC(p.getBitLength(), r);
					r[0] = r[0].negate();
					TC.fromDecimal(p.getBitLength(), r);
					return new Name(r[0], r[1], p);
				}
				case UNSIGNED: {
					BigInteger[] r = { x.getInteger(), x.getCarry() };
					MT.fromUS(p.getBitLength(), r);
					r[0] = r[0].negate();
					US.fromDecimal(p.getBitLength(), r);
					return new Name(r[0], r[1], p);
				}
				}
			}
		}
		case NOT:
		case BOOLEAN_NOT:
		case MATH_NOT:
		case C_NOT: {
			PExpression x = this.right.evaluate();
			if (x.isInteger()) {
				switch (getRep()) {
				case EXCESS_K: {
					BigInteger[] r = { x.getInteger(), x.getCarry() };
					MT.fromEx(p.getBitLength(), r);
					r[0] = r[0].not();
					Ex.fromDecimal(p.getBitLength(), r);
					return new Name(r[0], r[1], p);
				}
				case MATH: {
					BigInteger n = x.getInteger().not();
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
					r[0] = r[0].not();
					NB.fromDecimal(p.getBitLength(), r);
					return new Name(r[0], r[1], p);
				}
				case ONE_C: {
					BigInteger[] r = { x.getInteger(), x.getCarry() };
					MT.fromOC(p.getBitLength(), r);
					r[0] = r[0].not();
					OC.fromDecimal(p.getBitLength(), r);
					return new Name(r[0], r[1], p);
				}
				case SMR: {
					BigInteger[] r = { x.getInteger(), x.getCarry() };
					MT.fromSMR(p.getBitLength(), r);
					r[0] = r[0].not();
					SMR.fromDecimal(p.getBitLength(), r);
					return new Name(r[0], r[1], p);
				}
				default:
				case TWO_C: {
					BigInteger[] r = { x.getInteger(), x.getCarry() };
					TC.not(p.getBitLength(), r);
					return new Name(r[0], r[1], p);
				}
				case UNSIGNED: {
					BigInteger[] r = { x.getInteger(), x.getCarry() };
					MT.fromUS(p.getBitLength(), r);
					r[0] = r[0].not();
					US.fromDecimal(p.getBitLength(), r);
					return new Name(r[0], r[1], p);
				}
				}
			}
			throw new ArithmeticException(
					"Cannot apply \"not\" to a floating point. Please convert to bits or cast it to integer");
		}
		default:
		}
		return this;
	}

	private final PExpression right;
}
