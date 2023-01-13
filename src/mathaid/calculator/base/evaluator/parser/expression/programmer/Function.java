/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.programmer;

import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.ABS;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.BIT_COUNT;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.BIT_LENGTH;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.CEIL;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.EXPONENT;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.FLOAT_CAST;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.FLOOR;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.FMA;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.FROM_INTEGER;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.GCD;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.HIGH;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.IEEE_REM;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.INT_CAST;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.LCM;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.LOW;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.MAX;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.MIN;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.MOD;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.NAND;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.NEXT_AFTER;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.NEXT_DOWN;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.NEXT_UP;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.NOR;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.RINT;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.ROUND;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.SIGNIFICAND;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.SIGNUM;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.TO_INTEGER;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.TRUNC;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.ULP;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.XNOR;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import mathaid.calculator.base.typeset.LinkedSegment;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;
import mathaid.calculator.base.value.Ex;
import mathaid.calculator.base.value.FloatAid;
import mathaid.calculator.base.value.NB;
import mathaid.calculator.base.value.OC;
import mathaid.calculator.base.value.SMR;
import mathaid.calculator.base.value.TC;
import mathaid.calculator.base.value.US;

/*
 * Date: 19 Nov 2022----------------------------------------------------------- 
 * Time created: 09:16:17---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression.programmer------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Function.java------------------------------------------------------ 
 * Class name: Function------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Function extends Name {

	/*
	 * Date: 19 Nov 2022-----------------------------------------------------------
	 * Time created: 09:17:50---------------------------------------------------
	 */
	/**
	 * @param name
	 * @param params
	 */
	public Function(String name, List<PExpression> args, Params params) {
		super(name, params, String.class);
		this.args = args;
	}

	public List<PExpression> getArguments() {
		return Collections.unmodifiableList(args);
	}

	private LinkedSegment[] argsToSegments() {
		LinkedSegment[] s = new LinkedSegment[args.size()];
		SegmentBuilder sb = new SegmentBuilder();
		for (int i = 0; i < args.size(); i++) {
			PExpression x = args.get(i);
			x.format(sb.deleteAll());
			s[i] = sb.toSegment();
		}
		return s;
	}

	@Override
	public void format(SegmentBuilder sb) {
		sb.append(Segments.genericFunction(String.format(" \\mathrm{%s}", getName()), getName(), argsToSegments(), false, false));
	}

	@Override
	public PExpression evaluate() {
		switch (getName()) {
		case INT_CAST: {// integer cast
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate();
			if (arg.isFloatingPoint()) {
				BigInteger i = arg.getFloatingPoint().trunc();
				return new Name(i, BigInteger.valueOf(0), getParams());
			} else if (arg.isInteger()) {
				return arg;
			}
			throw new ArithmeticException("argument must be floating point");
		}
		case FLOAT_CAST: {// integer cast
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate();
			if (arg.isFloatingPoint()) {
				return arg;
			} else if (arg.isInteger()) {
				return new Name(getPrecision().createFP(new java.math.BigDecimal(arg.getInteger())), getParams());
			}
			throw new ArithmeticException("argument must be floating point");
		}
		case ABS: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate();
			if (arg.isInteger()) {
				BigInteger[] r = { arg.getInteger(), arg.getCarry() };
				switch (getRep()) {
				case EXCESS_K: {
					Ex.abs(getParams().getBitLength(), r);
					return new Name(r[0], r[1], getParams());
				}
				case MATH: {
					return new Name(r[0].abs(), r[1], getParams()).toDecimal();
				}
				case NEGABINARY: {
					NB.abs(getParams().getBitLength(), r);
					return new Name(r[0], r[1], getParams());
				}
				case ONE_C: {
					OC.abs(getParams().getBitLength(), r);
					return new Name(r[0], r[1], getParams());
				}
				case SMR: {
					SMR.abs(getParams().getBitLength(), r);
					return new Name(r[0], r[1], getParams());
				}
				case UNSIGNED: {
					US.abs(getParams().getBitLength(), r);
					return new Name(r[0], r[1], getParams());
				}
				case TWO_C:
				default: {
					TC.abs(getParams().getBitLength(), r);
					return new Name(r[0], r[1], getParams());
				}
				}
			} else if (arg.isFloatingPoint()) {
				return new Name(arg.getFloatingPoint().abs(), getParams());
			}
			throw new ArithmeticException("argument must be integer or floating point");
		}
		case BIT_COUNT: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate();
			if (arg.isInteger()) {
				BigInteger i = BigInteger.valueOf(arg.getInteger().bitCount());
				return new Name(i, arg.getCarry(), getParams());
			}
			throw new ArithmeticException("argument must be integer");

		}
		case BIT_LENGTH: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate();
			if (arg.isInteger()) {
				BigInteger i = BigInteger.valueOf(arg.getInteger().bitLength());
				return new Name(i, arg.getCarry(), getParams());
			}
			throw new ArithmeticException("argument must be integer");

		}
		case CEIL: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate();
			if (arg.isFloatingPoint()) {
				return new Name(arg.getFloatingPoint().ceil(), getParams());
			}
			throw new ArithmeticException("argument must be floating point");

		}
		case EXPONENT: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate();
			if (arg.isFloatingPoint()) {
				BigInteger i = BigInteger.valueOf(arg.getFloatingPoint().getExponent());
				return new Name(i, BigInteger.valueOf(0), getParams());
			}
			throw new ArithmeticException("argument must be floating point");

		}
		case FMA: {
			throw new ArithmeticException("Not implemented");

		}
		case FLOOR: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate();
			if (arg.isInteger()) {
				return new Name(arg.getFloatingPoint().floor(), getParams());
			}
			throw new ArithmeticException("argument must be integer");

		}
		case FROM_INTEGER: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			BigInteger x = args.get(0).evaluate().getInteger();
			if (x != null) {
				return new Name(getPrecision().fromBitLayout(x), getParams());
			}
			throw new ArithmeticException("argument must be integer");
		}
		case GCD: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate();
			PExpression arg2 = args.get(1).evaluate();
			if (arg1.isInteger() && arg2.isInteger()) {
				BigInteger i = arg1.toDecimal().getInteger().gcd(arg2.toDecimal().getInteger());
				return new Name(i, BigInteger.ZERO, getParams()).fromDecimal();
			}
			throw new ArithmeticException("arguments must all be integer");

		}
		case IEEE_REM: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate();
			PExpression arg2 = args.get(1).evaluate();
			if (arg1.isFloatingPoint() && arg2.isFloatingPoint()) {
				return new Name(arg1.getFloatingPoint().ieeeRemainder(arg2.getFloatingPoint()), getParams())
						.fromDecimal();
			}
			throw new ArithmeticException("arguments must all be floating point");

		}
		case HIGH: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate();
			PExpression arg2 = args.get(1).evaluate();
			if (arg1.isInteger() && arg2.isInteger()) {
				BigInteger x = arg1.getInteger();
				int length;
				try {
					length = arg2.getInteger().intValueExact();
				} catch (ArithmeticException e) {
					throw new ArithmeticException(
							"this argument must be less then the bit length of the first argument");
				}
				return new Name(x.shiftRight(getParams().getBitLength() - length), arg1.getCarry(), getParams());
			}
			throw new ArithmeticException("arguments must all be integer");

		}
		case LCM: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate();
			PExpression arg2 = args.get(1).evaluate();
			if (arg1.isInteger() && arg2.isInteger()) {
				mathaid.calculator.base.value.BigFraction x = new mathaid.calculator.base.value.BigFraction(
						arg1.toDecimal().getInteger(), BigInteger.ONE);
				mathaid.calculator.base.value.BigFraction y = new mathaid.calculator.base.value.BigFraction(
						arg2.toDecimal().getInteger(), BigInteger.ONE);
				return new Name(x.lcm(y), arg1.getCarry(), getParams()).fromDecimal();
			}
			throw new ArithmeticException("arguments must all be integer");

		}
		case LOW: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate();
			PExpression arg2 = args.get(1).evaluate();
			if (arg1.isInteger() && arg2.isInteger()) {
				BigInteger x = arg1.getInteger();
				int length;
				try {
					length = arg2.getInteger().intValueExact();
				} catch (ArithmeticException e) {
					throw new ArithmeticException(
							"this argument must be less then the bit length of the first argument");
				}
				return new Name(FloatAid.clearMSB(x, getParams().getBitLength() - length), arg1.getCarry(),
						getParams());
			}
			throw new ArithmeticException("arguments must all be integer");

		}
		case MAX: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate();
			PExpression arg2 = args.get(1).evaluate();
			if (arg1.isInteger() && arg2.isInteger()) {
				BigInteger x = arg1.toDecimal().getInteger();
				BigInteger y = arg2.toDecimal().getInteger();
				return new Name(x.max(y), arg1.getCarry(), getParams()).fromDecimal();
			}
			throw new ArithmeticException("arguments must all be integer");

		}
		case MIN: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate();
			PExpression arg2 = args.get(1).evaluate();
			if (arg1.isInteger() && arg2.isInteger()) {
				BigInteger x = arg1.toDecimal().getInteger();
				BigInteger y = arg2.toDecimal().getInteger();
				return new Name(x.min(y), arg1.getCarry(), getParams()).fromDecimal();
			}
			throw new ArithmeticException("arguments must all be integer");

		}
		case MOD: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate();
			PExpression arg2 = args.get(1).evaluate();
			if (arg1.isInteger() && arg2.isInteger()) {
				BigInteger x = arg1.toDecimal().getInteger();
				BigInteger y = arg2.toDecimal().getInteger();
				return new Name(x.mod(y), arg1.getCarry(), getParams()).fromDecimal();
			}
			throw new ArithmeticException("arguments must all be integer");
		}
		case NAND: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate();
			PExpression arg2 = args.get(1).evaluate();
			if (arg1.isInteger() && arg2.isInteger()) {
				BigInteger x = arg1.toDecimal().getInteger();
				BigInteger y = arg2.toDecimal().getInteger();
				return new Name(x.and(y).not(), arg1.getCarry(), getParams()).fromDecimal();
			}
			throw new ArithmeticException("arguments must all be integer");
		}
		case NEXT_AFTER: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate();
			PExpression arg2 = args.get(1).evaluate();
			if (arg1.isFloatingPoint() && arg2.isFloatingPoint()) {
				return new Name(arg1.getFloatingPoint().nextAfter(arg2.getFloatingPoint()), getParams()).fromDecimal();
			}
			throw new ArithmeticException("arguments must all be floating point");

		}
		case NEXT_DOWN: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate();
			if (arg.isInteger()) {
				return new Name(arg.getFloatingPoint().nextDown(), getParams());
			}
			throw new ArithmeticException("argument must be integer");

		}
		case NEXT_UP: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate();
			if (arg.isInteger()) {
				return new Name(arg.getFloatingPoint().nextUp(), getParams());
			}
			throw new ArithmeticException("argument must be integer");

		}
		case NOR: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate();
			PExpression arg2 = args.get(1).evaluate();
			if (arg1.isInteger() && arg2.isInteger()) {
				BigInteger x = arg1.toDecimal().getInteger();
				BigInteger y = arg2.toDecimal().getInteger();
				return new Name(x.or(y).not(), arg1.getCarry(), getParams()).fromDecimal();
			}
			throw new ArithmeticException("arguments must all be integer");
		}
		case RINT: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate();
			if (arg.isInteger()) {
				return new Name(arg.getFloatingPoint().rint(), getParams());
			}
			throw new ArithmeticException("argument must be integer");

		}
		case ROUND: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate();
			if (arg.isInteger()) {
				return new Name(arg.getFloatingPoint().round(), getParams());
			}
			throw new ArithmeticException("argument must be integer");
		}
		case SIGNIFICAND: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate();
			if (arg.isFloatingPoint()) {
				BigInteger i = arg.getFloatingPoint().getSignificand();
				return new Name(i, BigInteger.valueOf(0), getParams());
			}
			throw new ArithmeticException("argument must be floating point");

		}
		case SIGNUM: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate();
			if (arg.isFloatingPoint()) {
				BigInteger i = BigInteger.valueOf(arg.getFloatingPoint().signum());
				return new Name(i, BigInteger.valueOf(0), getParams());
			}
			throw new ArithmeticException("argument must be floating point");

		}
		case TO_INTEGER: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate();
			if (arg.isFloatingPoint()) {
				BigInteger i = arg.getFloatingPoint().toBigInteger();
				return new Name(i, BigInteger.valueOf(0), getParams());
			}
			throw new ArithmeticException("argument must be floating point");

		}
		case TRUNC: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate();
			if (arg.isFloatingPoint()) {
				BigInteger i = arg.getFloatingPoint().trunc();
				return new Name(i, BigInteger.valueOf(0), getParams());
			}
			throw new ArithmeticException("argument must be floating point");

		}
		case ULP: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate();
			if (arg.isInteger()) {
				return new Name(arg.getFloatingPoint().ulp(), getParams());
			}
			throw new ArithmeticException("argument must be integer");
		}
		case XNOR: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate();
			PExpression arg2 = args.get(1).evaluate();
			if (arg1.isInteger() && arg2.isInteger()) {
				BigInteger x = arg1.toDecimal().getInteger();
				BigInteger y = arg2.toDecimal().getInteger();
				return new Name(x.and(y).or(x.not().and(y.not())), arg1.getCarry(), getParams()).fromDecimal();
			}
			throw new ArithmeticException("arguments must all be integer");
		}
		default:
		}
		throw new ArithmeticException("unknown function name");
	}

	final List<PExpression> args;

}
