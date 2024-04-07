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
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.NTH;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.FunctionName.ON;
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

import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.typeset.LinkedSegment;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;
import mathaid.calculator.base.util.Utility;
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
 * An expression with a parameter (argument) that performs an action on the parameter which results in a new value being created
 * from the action.
 * <p>
 * Functions have an arity, which gives the number of argument required before it can give a result. Arity types include:
 * nullary (takes 0 number of argument), unary (takes 1 argument), binary (takes 2 arguments) etc. If the function is evaluated
 * without the correct arity, it will result in an exception.
 * <p>
 * The result of an evaluation depends on the {@linkplain Params#getBitRepresentation() bit representation} of the expression,
 * some functions can only evaluate in certain bit representations. E.g {@code bitCount(3.5ap-2)}, {@code ceil(0xeeb12)} (when
 * bit representation is floating-point) will all throw an exception.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Function extends Name {

	/*
	 * Date: 19 Nov 2022-----------------------------------------------------------
	 * Time created: 09:17:50---------------------------------------------------
	 */
	/**
	 * Constructs a {@code Function} with a given name, argument list and params object.
	 * 
	 * @param name   an {@code EvaluatableExpression} that is the identifier of this {@code Function}.
	 * @param args the argument(s)/parameter(s) to this function.
	 * @param params the {@code ExpressionParams} representing options for the evaluation and format within this expression.
	 */
	public Function(String name, List<PExpression> args, Params params) {
		super(name, params, String.class);
		this.args = args;
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 08:15:05 ---------------------------------------------------
	 */
	/**
	 * Gets the arguments given at the constructor.
	 * 
	 * @return an unmodifiable list of all the arguments of this function.
	 */
	public List<PExpression> getArguments() {
		return Collections.unmodifiableList(args);
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 08:15:14 ---------------------------------------------------
	 */
	/**
	 * Converts all the arguments into {@code LinkedSegment} nodes and returns them in the same order .
	 * 
	 * @return an array of the formatted arguments.
	 */
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

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 08:18:14 ---------------------------------------------------
	 */
	/**
	 * Appends the {@code LinkedSegment} representation of this function into the format builder.
	 * <p>
	 * The name is first formatted and appended to the given builder, after which the arguments are formatted and appended.
	 * <p>
	 * This has no side-effects.
	 * 
	 * @param sb {@inheritDoc}
	 */
	@Override
	public void format(SegmentBuilder sb) {
		sb.append(Segments.genericFunction(String.format(" \\mathrm{%s}", getName()), getName(), argsToSegments(),
				false, false));
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 08:15:35 ---------------------------------------------------
	 */
	/**
	 * Performs computations on the arguments and returns the result as an {@code PExpression}.
	 * <p>
	 * All arguments given will have their {@link EvaluatableExpression#evaluate() evaluate()} method called on them, then their
	 * results will be applied to this function. The result itself depends on the type of function, it's arguments, as well as the
	 * {@linkplain Params#getBitRepresentation() bit representation}.
	 * <p>
	 * This has no side-effects.
	 * 
	 * @return a new {@code EvaluatableExpression} that is the result of evaluating this function. May return the same object
	 *         especially if called more than once.
	 */
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
		case FLOAT_CAST: {// float cast
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
			PExpression arg = args.get(0).evaluate().toInteger();
			BigInteger i = BigInteger.valueOf(arg.getInteger().bitCount());
			return new Name(i, arg.getCarry(), getParams());

		}
		case BIT_LENGTH: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate().toInteger();
			BigInteger i = BigInteger.valueOf(arg.getInteger().bitLength());
			return new Name(i, arg.getCarry(), getParams());

		}
		case CEIL: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate().toFloatingPoint();
			return new Name(arg.getFloatingPoint().ceil(), getParams());

		}
		case EXPONENT: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate().toFloatingPoint();
			BigInteger i = BigInteger.valueOf(arg.getFloatingPoint().getExponent());
			return new Name(i, BigInteger.valueOf(0), getParams());

		}
		case FMA: {
			throw new ArithmeticException("Not implemented");

		}
		case FLOOR: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate().toFloatingPoint();
			return new Name(arg.getFloatingPoint().floor(), getParams());

		}
		case FROM_INTEGER: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			BigInteger x = args.get(0).evaluate().toInteger().getInteger();
			return new Name(getPrecision().fromBitLayout(x), getParams());
		}
		case GCD: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate().toInteger();
			PExpression arg2 = args.get(1).evaluate().toInteger();
			BigInteger i = arg1.toDecimal().getInteger().gcd(arg2.toDecimal().getInteger());
			return new Name(i, BigInteger.ZERO, getParams()).fromDecimal();

		}
		case IEEE_REM: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate().toFloatingPoint();
			PExpression arg2 = args.get(1).evaluate().toFloatingPoint();
			return new Name(arg1.getFloatingPoint().ieeeRemainder(arg2.getFloatingPoint()), getParams())
					.fromDecimal();

		}
		case HIGH: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate().toInteger();
			PExpression arg2 = args.get(1).evaluate().toInteger();
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
		case LCM: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate().toInteger();
			PExpression arg2 = args.get(1).evaluate().toInteger();
			mathaid.calculator.base.value.BigFraction x = new mathaid.calculator.base.value.BigFraction(
					arg1.toDecimal().getInteger(), BigInteger.ONE);
			mathaid.calculator.base.value.BigFraction y = new mathaid.calculator.base.value.BigFraction(
					arg2.toDecimal().getInteger(), BigInteger.ONE);
			return new Name(x.lcm(y), arg1.getCarry(), getParams()).fromDecimal();

		}
		case LOW: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate().toInteger();
			PExpression arg2 = args.get(1).evaluate().toInteger();
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
		case MAX: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate().toInteger();
			PExpression arg2 = args.get(1).evaluate().toInteger();
			BigInteger x = arg1.toDecimal().getInteger();
			BigInteger y = arg2.toDecimal().getInteger();
			return new Name(x.max(y), arg1.getCarry(), getParams()).fromDecimal();

		}
		case MIN: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate().toInteger();
			PExpression arg2 = args.get(1).evaluate().toInteger();
			BigInteger x = arg1.toDecimal().getInteger();
			BigInteger y = arg2.toDecimal().getInteger();
			return new Name(x.min(y), arg1.getCarry(), getParams()).fromDecimal();

		}
		case MOD: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate().toInteger();
			PExpression arg2 = args.get(1).evaluate().toInteger();
			BigInteger x = arg1.toDecimal().getInteger();
			BigInteger y = arg2.toDecimal().getInteger();
			return new Name(x.mod(y), arg1.getCarry(), getParams()).fromDecimal();
		}
		case NAND: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate().toInteger();
			PExpression arg2 = args.get(1).evaluate().toInteger();
			BigInteger x = arg1.toDecimal().getInteger();
			BigInteger y = arg2.toDecimal().getInteger();
			return new Name(x.and(y).not(), arg1.getCarry(), getParams()).fromDecimal();
		}
		case NEXT_AFTER: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate().toFloatingPoint();
			PExpression arg2 = args.get(1).evaluate().toFloatingPoint();
			return new Name(arg1.getFloatingPoint().nextAfter(arg2.getFloatingPoint()), getParams()).fromDecimal();

		}
		case NEXT_DOWN: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate().toFloatingPoint();
			return new Name(arg.getFloatingPoint().nextDown(), getParams());

		}
		case NEXT_UP: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate().toFloatingPoint();
			return new Name(arg.getFloatingPoint().nextUp(), getParams());

		}
		case NOR: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate().toInteger();
			PExpression arg2 = args.get(1).evaluate().toInteger();
			BigInteger x = arg1.toDecimal().getInteger();
			BigInteger y = arg2.toDecimal().getInteger();
			return new Name(x.or(y).not(), arg1.getCarry(), getParams()).fromDecimal();
		}
		case NTH: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate().toInteger();
			PExpression arg2 = args.get(1).evaluate();
			BigInteger x = arg1.toDecimal().getInteger();
			int y;
			try {				
				y = arg2.isInteger() ? arg2.getInteger().intValueExact() : arg2.getFloatingPoint().trunc().intValueExact();
			} catch (ArithmeticException e) {
				throw new ArithmeticException("second argument too big");
			}
			return new Name(x.testBit(y) ? Utility.i(1) : Utility.i(0), arg1.getCarry(), getParams());
		}
		case ON: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate();
			int x;
			try {				
				x = arg.isInteger() ? arg.getInteger().intValueExact() : arg.getFloatingPoint().trunc().intValueExact();
			} catch (ArithmeticException e) {
				throw new ArithmeticException("second argument too big");
			}
			return new Name(FloatAid.getAllOnes(x), arg.getCarry(), getParams());
		}
		case RINT: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate().toFloatingPoint();
			return new Name(arg.getFloatingPoint().rint(), getParams());
		}
		case ROUND: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate().toFloatingPoint();
			return new Name(arg.getFloatingPoint().round(), getParams());
		}
		case SIGNIFICAND: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate().toFloatingPoint();
			BigInteger i = arg.getFloatingPoint().getSignificand();
			return new Name(i, BigInteger.valueOf(0), getParams());

		}
		case SIGNUM: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate().toFloatingPoint();
			BigInteger i = BigInteger.valueOf(arg.getFloatingPoint().signum());
			return new Name(i, BigInteger.valueOf(0), getParams());

		}
		case TO_INTEGER: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate().toFloatingPoint();
			BigInteger i = arg.getFloatingPoint().toBigInteger();
			return new Name(i, BigInteger.valueOf(0), getParams());

		}
		case TRUNC: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate().toFloatingPoint();
			BigInteger i = arg.getFloatingPoint().trunc();
			return new Name(i, BigInteger.valueOf(0), getParams());

		}
		case ULP: {
			if (args.size() != 1)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg = args.get(0).evaluate().toFloatingPoint();
			return new Name(arg.getFloatingPoint().ulp(), getParams());
		}
		case XNOR: {
			if (args.size() != 2)
				throw new ArithmeticException("unknown argument(s) found");
			PExpression arg1 = args.get(0).evaluate().toInteger();
			PExpression arg2 = args.get(1).evaluate().toInteger();
			BigInteger x = arg1.toDecimal().getInteger();
			BigInteger y = arg2.toDecimal().getInteger();
			return new Name(x.and(y).or(x.not().and(y.not())), arg1.getCarry(), getParams()).fromDecimal();
		}
		default:
		}
		throw new ArithmeticException("unknown function name");
	}

	/**
	 * Stores the arguments i.e the parameters to this function.
	 */
	final List<PExpression> args;

}
