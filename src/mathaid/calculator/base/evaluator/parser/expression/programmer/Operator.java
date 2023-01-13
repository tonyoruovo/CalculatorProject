/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.programmer;

import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.AND;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.ASTERISK;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.C_AND;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.C_LEFT_SHIFT;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.C_OR;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.C_REM;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.C_RIGHT_SHIFT;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.C_XOR;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.F_SLASH;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.MATH_AND;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.MATH_OR;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.MINUS;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.OR;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.PLUS;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.XOR;

import java.math.BigInteger;

import mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression.Params.ResultType;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;
import mathaid.calculator.base.value.BinaryFPPrecision.BinaryFP;
import mathaid.calculator.base.value.Ex;
import mathaid.calculator.base.value.FloatAid;
import mathaid.calculator.base.value.NB;
import mathaid.calculator.base.value.OC;
import mathaid.calculator.base.value.SMR;
import mathaid.calculator.base.value.TC;
import mathaid.calculator.base.value.US;

/*
 * Date: 18 Oct 2022----------------------------------------------------------- 
 * Time created: 11:31:19---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression.programmer------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Operator.java------------------------------------------------------ 
 * Class name: Operator------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Operator extends Name {

	public Operator(PExpression left, String name, PExpression right, Params params) {
		super(name, params, String.class);
		this.left = left;
		this.right = right;
	}

	public PExpression getLeft() {
		return left;
	}

	public PExpression getRight() {
		return right;
	}

	/*
	 * This requires the amssymb package in LaTeX. Whether mathJax has such a
	 * package remains to be seen.
	 */
	@Override
	public void format(SegmentBuilder sb) {
//		int type = Segment.Type.OPERATOR;
		String fmt = getName();
		/*
		 * Before this method is called, it is advisable that evaluate should be first
		 * called. When that happens all these switch cases will be unnecessary
		 */
		switch (getName()) {
		case PLUS:
//			type = Segment.Type.OPERATOR;
			break;
		case MINUS:
//			type = Segment.Type.OPERATOR;
			break;
		case C_AND:
			fmt = "~\\&~";
			break;
		case MATH_AND:
			fmt = " \\land ";
			break;
		case MATH_OR:
			fmt = "~\\lor~";
			break;
		case C_XOR:
			fmt = "\\veebar";
			break;
		case C_REM:
			fmt = String.format("~\\%s~", getName());
			break;
		case F_SLASH:
		case AND:
		case OR:
		case XOR:
		case C_OR:
		case C_LEFT_SHIFT:
		case C_RIGHT_SHIFT:
		case ASTERISK:
		default:
			fmt = String.format("~%s~", getName());
			break;
		}
		left.format(sb);
		sb.append(Segments.operator(fmt, getName()));
		right.format(sb);
	}

	@Override
	public PExpression evaluate() {
		PExpression left = this.left.evaluate();
		PExpression right = this.right.evaluate();
		Params p = getParams();
		switch (getName()) {
		case PLUS: {
			if (left.isFloatingPoint() || right.isFloatingPoint()) {
				if (!left.isFloatingPoint()) {
					BinaryFP fpl = getPrecision().createFP(left.getInteger().toString(p.getRadix()), p.getRadix());
					BinaryFP fpr = right.getFloatingPoint();
					return new Name(fpl.add(fpr), p);
				}
				BinaryFP fpl = left.getFloatingPoint();
				BinaryFP fpr = getPrecision().createFP(right.getInteger().toString(p.getRadix()), p.getRadix());
				return new Name(fpl.add(fpr), p);
			}
			BigInteger[] r = { left.toDecimal().getInteger(), right.toDecimal().getInteger() };
			r[0] = r[0].add(r[1]);
			r[1] = BigInteger.ZERO;
			return new Name(r[0], r[1], p).fromDecimal();
		}
		case MINUS: {
			if (left.isFloatingPoint() || right.isFloatingPoint()) {
				if (!left.isFloatingPoint()) {
					BinaryFP fpl = getPrecision().createFP(left.getInteger().toString(p.getRadix()), p.getRadix());
					BinaryFP fpr = right.getFloatingPoint();
					return new Name(fpl.subtract(fpr), p);
				}
				BinaryFP fpl = left.getFloatingPoint();
				BinaryFP fpr = getPrecision().createFP(right.getInteger().toString(p.getRadix()), p.getRadix());
				return new Name(fpl.subtract(fpr), p);
			}
			BigInteger[] r = { left.toDecimal().getInteger(), right.toDecimal().getInteger() };
			r[0] = r[0].subtract(r[1]);
			r[1] = BigInteger.ZERO;
			return new Name(r[0], r[1], p).fromDecimal();
		}
		case ASTERISK: {
			if (left.isFloatingPoint() || right.isFloatingPoint()) {
				if (!left.isFloatingPoint()) {
					BinaryFP fpl = getPrecision().createFP(left.getInteger().toString(p.getRadix()), p.getRadix());
					BinaryFP fpr = right.getFloatingPoint();
					return new Name(fpl.multiply(fpr), p);
				}
				BinaryFP fpl = left.getFloatingPoint();
				BinaryFP fpr = getPrecision().createFP(right.getInteger().toString(p.getRadix()), p.getRadix());
				return new Name(fpl.multiply(fpr), p);
			}
			BigInteger[] r = { left.toDecimal().getInteger(), right.toDecimal().getInteger() };
			r[0] = r[0].multiply(r[1]);
			r[1] = BigInteger.ZERO;
			return new Name(r[0], r[1], p).fromDecimal();
		}
		case F_SLASH: {
			if (left.isFloatingPoint() || right.isFloatingPoint()) {
				if (!left.isFloatingPoint()) {
					BinaryFP fpl = getPrecision().createFP(left.getInteger().toString(p.getRadix()), p.getRadix());
					BinaryFP fpr = right.getFloatingPoint();
					return new Name(fpl.divide(fpr), p);
				}
				BinaryFP fpl = left.getFloatingPoint();
				BinaryFP fpr = getPrecision().createFP(right.getInteger().toString(p.getRadix()), p.getRadix());
				return new Name(fpl.divide(fpr), p);
			}
			BigInteger[] r = { left.toDecimal().getInteger(), right.toDecimal().getInteger() };
			r[0] = r[0].divide(r[1]);
			r[1] = BigInteger.ZERO;
			return new Name(r[0], r[1], p).fromDecimal();
		}
		case C_REM: {
			if (left.isFloatingPoint() || right.isFloatingPoint()) {
				if (!left.isFloatingPoint()) {
					BinaryFP fpl = getPrecision().createFP(left.getInteger().toString(p.getRadix()), p.getRadix());
					BinaryFP fpr = right.getFloatingPoint();
					return new Name(fpl.fmod(fpr), p);
				}
				BinaryFP fpl = left.getFloatingPoint();
				BinaryFP fpr = getPrecision().createFP(right.getInteger().toString(p.getRadix()), p.getRadix());
				return new Name(fpl.fmod(fpr), p);
			}
			BigInteger[] r = { left.toDecimal().getInteger(), right.toDecimal().getInteger() };
			r[0] = r[0].remainder(r[1]);
			r[1] = BigInteger.ZERO;
			return new Name(r[0], r[1], p).fromDecimal();
		}
		case C_AND:
		case MATH_AND:
		case AND: {
			if (left.isInteger() && right.isInteger()) {
				BigInteger[] r = { left.toDecimal().getInteger(), right.toDecimal().getInteger() };
				r[0] = r[0].and(r[1]);
				r[1] = BigInteger.ZERO;
				return new Name(r[0], r[1], p).fromDecimal();
			}
			throw new ArithmeticException("and can only be performed on integers");
		}
		case C_OR:
		case MATH_OR:
		case OR: {
			if (left.isInteger() && right.isInteger()) {
				BigInteger[] r = { left.toDecimal().getInteger(), right.toDecimal().getInteger() };
				r[0] = r[0].or(r[1]);
				r[1] = BigInteger.ZERO;
				return new Name(r[0], r[1], p).fromDecimal();
			}
			throw new ArithmeticException("or can only be performed on integers");
		}
		case C_XOR:
		case XOR: {
			if (left.isInteger() && right.isInteger()) {
				BigInteger[] r = { left.toDecimal().getInteger(), right.toDecimal().getInteger() };
				r[0] = r[0].xor(r[1]);
				r[1] = BigInteger.ZERO;
				return new Name(r[0], r[1], p).fromDecimal();
			}
			throw new ArithmeticException("xor can only be performed on integers");
		}
		case C_LEFT_SHIFT: {
			if (left.isInteger() && right.isInteger()) {
				BigInteger[] r = { left.getInteger(), left.getCarry() };
				int shift;
				try {
					shift = right.toDecimal().getInteger().intValueExact();
				} catch (@SuppressWarnings("unused") ArithmeticException e) {
					throw new ArithmeticException("right side is too big.");
				}
				switch (p.getShift()) {
				default:
				case ResultType.SHIFT_ARITHMETIC:
				case ResultType.SHIFT_LOGICAL: {
					switch (getRep()) {
					case EXCESS_K: {
						Ex.shiftLeft(shift, p.getBitLength(), r);
						return new Name(r[0], r[1], p);
					}
					case MATH: {
						r[0] = r[0].shiftLeft(shift);
						if (r[0].abs().bitLength() > p.getBitLength()) {
							r[1] = r[0].shiftRight(p.getBitLength()).testBit(0) ? BigInteger.ONE : BigInteger.ZERO;
							int sign = r[0].signum();
							r[0] = FloatAid.clearMSB(r[0].abs(), r[0].abs().bitLength() - p.getBitLength())
									.multiply(BigInteger.valueOf(sign));
						}
						return new Name(r[0], r[1], p);
					}
					case NEGABINARY: {
						NB.shiftLeft(shift, p.getBitLength(), r);
						return new Name(r[0], r[1], p);
					}
					case ONE_C: {
						OC.shiftLeft(shift, p.getBitLength(), r);
						return new Name(r[0], r[1], p);
					}
					case SMR: {
						SMR.shiftLeft(shift, p.getBitLength(), r);
						return new Name(r[0], r[1], p);
					}
					case UNSIGNED: {
						US.shiftLeft(shift, p.getBitLength(), r);
						return new Name(r[0], r[1], p);
					}
					case TWO_C:
					default: {
						TC.shiftLeft(shift, p.getBitLength(), r);
						return new Name(r[0], r[1], p);
					}

					}
				}
				case ResultType.SHIFT_ROTATE: {
					switch (getRep()) {
					case EXCESS_K: {
						Ex.circularShiftLeft(shift, p.getBitLength(), false, r);
						return new Name(r[0], r[1], p);
					}
					case MATH: {
						r[0] = r[0].shiftLeft(shift);
						if (r[0].abs().bitLength() > p.getBitLength()) {
							r[1] = r[0].shiftRight(p.getBitLength()).testBit(0) ? BigInteger.ONE : BigInteger.ZERO;
							int sign = r[0].signum();
							r[0] = FloatAid.clearMSB(r[0].abs(), r[0].abs().bitLength() - p.getBitLength())
									.multiply(BigInteger.valueOf(sign));
						}
						return new Name(r[0], r[1], p);
					}
					case NEGABINARY: {
						NB.circularShiftLeft(shift, p.getBitLength(), false, r);
						return new Name(r[0], r[1], p);
					}
					case ONE_C: {
						OC.circularShiftLeft(shift, p.getBitLength(), false, r);
						return new Name(r[0], r[1], p);
					}
					case SMR: {
						SMR.circularShiftLeft(shift, p.getBitLength(), false, r);
						return new Name(r[0], r[1], p);
					}
					case UNSIGNED: {
						US.circularShiftLeft(shift, p.getBitLength(), false, r);
						return new Name(r[0], r[1], p);
					}
					case TWO_C:
					default: {
						TC.circularShiftLeft(shift, p.getBitLength(), false, r);
						return new Name(r[0], r[1], p);
					}

					}
				}
				case ResultType.SHIFT_ROTATE_2RU_C1: {
					switch (getRep()) {
					case EXCESS_K: {
						Ex.circularShiftLeft(shift, p.getBitLength(), true, r);
						return new Name(r[0], r[1], p);
					}
					case MATH: {
						r[0] = r[0].shiftLeft(shift);
						if (r[0].abs().bitLength() > p.getBitLength()) {
							r[1] = r[0].shiftRight(p.getBitLength()).testBit(0) ? BigInteger.ONE : BigInteger.ZERO;
							int sign = r[0].signum();
							r[0] = FloatAid.clearMSB(r[0].abs(), r[0].abs().bitLength() - p.getBitLength())
									.multiply(BigInteger.valueOf(sign));
						}
						return new Name(r[0], r[1], p);
					}
					case NEGABINARY: {
						NB.circularShiftLeft(shift, p.getBitLength(), true, r);
						return new Name(r[0], r[1], p);
					}
					case ONE_C: {
						OC.circularShiftLeft(shift, p.getBitLength(), true, r);
						return new Name(r[0], r[1], p);
					}
					case SMR: {
						SMR.circularShiftLeft(shift, p.getBitLength(), true, r);
						return new Name(r[0], r[1], p);
					}
					case UNSIGNED: {
						US.circularShiftLeft(shift, p.getBitLength(), true, r);
						return new Name(r[0], r[1], p);
					}
					case TWO_C:
					default: {
						TC.circularShiftLeft(shift, p.getBitLength(), true, r);
						return new Name(r[0], r[1], p);
					}

					}
				}
				}
			}
			throw new ArithmeticException("shift can only be performed on integers");
		}
		case C_RIGHT_SHIFT: {
			if (left.isInteger() && right.isInteger()) {
				BigInteger[] r = { left.getInteger(), left.getCarry() };
				int shift;
				try {
					shift = right.getInteger().intValueExact();
				} catch (@SuppressWarnings("unused") ArithmeticException e) {
					throw new ArithmeticException("right side is too big.");
				}
				switch (p.getShift()) {
				default:
				case ResultType.SHIFT_LOGICAL: {
					switch (getRep()) {
					case EXCESS_K: {
						Ex.shiftRight(shift, p.getBitLength(), false, r);
						return new Name(r[0], r[1], p);
					}
					case MATH: {
						r[0] = r[0].shiftRight(shift);
						if (r[0].abs().bitLength() > p.getBitLength()) {
							r[1] = r[0].shiftRight(p.getBitLength()).testBit(0) ? BigInteger.ONE : BigInteger.ZERO;
							int sign = r[0].signum();
							r[0] = FloatAid.clearMSB(r[0].abs(), r[0].abs().bitLength() - p.getBitLength())
									.multiply(BigInteger.valueOf(sign));
						}
						return new Name(r[0], r[1], p);
					}
					case NEGABINARY: {
						NB.shiftRight(shift, p.getBitLength(), false, r);
						return new Name(r[0], r[1], p);
					}
					case ONE_C: {
						OC.shiftRight(shift, p.getBitLength(), false, r);
						return new Name(r[0], r[1], p);
					}
					case SMR: {
						SMR.shiftRight(shift, p.getBitLength(), false, r);
						return new Name(r[0], r[1], p);
					}
					case UNSIGNED: {
						US.shiftRight(shift, p.getBitLength(), false, r);
						return new Name(r[0], r[1], p);
					}
					case TWO_C:
					default: {
						TC.shiftRight(shift, p.getBitLength(), false, r);
						return new Name(r[0], r[1], p);
					}

					}
				}
				case ResultType.SHIFT_ARITHMETIC: {
					switch (getRep()) {
					case EXCESS_K: {
						Ex.shiftRight(shift, p.getBitLength(), true, r);
						return new Name(r[0], r[1], p);
					}
					case MATH: {
						r[0] = r[0].shiftRight(shift);
						if (r[0].abs().bitLength() > p.getBitLength()) {
							r[1] = r[0].shiftRight(p.getBitLength()).testBit(0) ? BigInteger.ONE : BigInteger.ZERO;
							int sign = r[0].signum();
							r[0] = FloatAid.clearMSB(r[0].abs(), r[0].abs().bitLength() - p.getBitLength())
									.multiply(BigInteger.valueOf(sign));
						}
						return new Name(r[0], r[1], p);
					}
					case NEGABINARY: {
						NB.shiftRight(shift, p.getBitLength(), true, r);
						return new Name(r[0], r[1], p);
					}
					case ONE_C: {
						OC.shiftRight(shift, p.getBitLength(), true, r);
						return new Name(r[0], r[1], p);
					}
					case SMR: {
						SMR.shiftRight(shift, p.getBitLength(), true, r);
						return new Name(r[0], r[1], p);
					}
					case UNSIGNED: {
						US.shiftRight(shift, p.getBitLength(), true, r);
						return new Name(r[0], r[1], p);
					}
					case TWO_C:
					default: {
						TC.shiftRight(shift, p.getBitLength(), true, r);
						return new Name(r[0], r[1], p);
					}

					}
				}
				case ResultType.SHIFT_ROTATE: {
					switch (getRep()) {
					case EXCESS_K: {
						Ex.circularShiftRight(shift, p.getBitLength(), false, r);
						return new Name(r[0], r[1], p);
					}
					case MATH: {
						r[0] = r[0].shiftRight(shift);
						if (r[0].abs().bitLength() > p.getBitLength()) {
							r[1] = r[0].shiftRight(p.getBitLength()).testBit(0) ? BigInteger.ONE : BigInteger.ZERO;
							int sign = r[0].signum();
							r[0] = FloatAid.clearMSB(r[0].abs(), r[0].abs().bitLength() - p.getBitLength())
									.multiply(BigInteger.valueOf(sign));
						}
						return new Name(r[0], r[1], p);
					}
					case NEGABINARY: {
						NB.circularShiftRight(shift, p.getBitLength(), false, r);
						return new Name(r[0], r[1], p);
					}
					case ONE_C: {
						OC.circularShiftRight(shift, p.getBitLength(), false, r);
						return new Name(r[0], r[1], p);
					}
					case SMR: {
						SMR.circularShiftRight(shift, p.getBitLength(), false, r);
						return new Name(r[0], r[1], p);
					}
					case UNSIGNED: {
						US.circularShiftRight(shift, p.getBitLength(), false, r);
						return new Name(r[0], r[1], p);
					}
					case TWO_C:
					default: {
						TC.circularShiftRight(shift, p.getBitLength(), false, r);
						return new Name(r[0], r[1], p);
					}

					}
				}
				case ResultType.SHIFT_ROTATE_2RU_C1: {
					switch (getRep()) {
					case EXCESS_K: {
						Ex.circularShiftRight(shift, p.getBitLength(), true, r);
						return new Name(r[0], r[1], p);
					}
					case MATH: {
						r[0] = r[0].shiftRight(shift);
						if (r[0].abs().bitLength() > p.getBitLength()) {
							r[1] = r[0].shiftRight(p.getBitLength()).testBit(0) ? BigInteger.ONE : BigInteger.ZERO;
							int sign = r[0].signum();
							r[0] = FloatAid.clearMSB(r[0].abs(), r[0].abs().bitLength() - p.getBitLength())
									.multiply(BigInteger.valueOf(sign));
						}
						return new Name(r[0], r[1], p);
					}
					case NEGABINARY: {
						NB.circularShiftRight(shift, p.getBitLength(), true, r);
						return new Name(r[0], r[1], p);
					}
					case ONE_C: {
						OC.circularShiftRight(shift, p.getBitLength(), true, r);
						return new Name(r[0], r[1], p);
					}
					case SMR: {
						SMR.circularShiftRight(shift, p.getBitLength(), true, r);
						return new Name(r[0], r[1], p);
					}
					case UNSIGNED: {
						US.circularShiftRight(shift, p.getBitLength(), true, r);
						return new Name(r[0], r[1], p);
					}
					case TWO_C:
					default: {
						TC.circularShiftRight(shift, p.getBitLength(), true, r);
						return new Name(r[0], r[1], p);
					}

					}
				}
				}
			}
			throw new ArithmeticException("shift can only be performed on integers");
		}
		/*
		 * This radix conversion will be evaluated at the same place that recurring
		 * values are evaluated. The subscript radix can only be specified in decimal.
		 */
//		case "_": {// TODO: for radix conversion
//			if (!Utility.isNumber(right.getName()))
//				throw new NumberFormatException("base can only be in decimal");
//			try {
//				String s = FloatAid.toString(left.getName(), Integer.parseInt(right.getName()), p.getRadix(), 0);
//				return new Name(s, p);
//			} catch (NumberFormatException e) {
//				throw new ArithmeticException(String.format("base must be  greater than %1$s and less than %2$s",
//						Character.MIN_RADIX - 1, Character.MAX_RADIX + 1));
//			}
//		}
		default:
		}
		return new Operator(left, getName(), right, getParams());
	}

	private final PExpression left;
	private final PExpression right;
}
