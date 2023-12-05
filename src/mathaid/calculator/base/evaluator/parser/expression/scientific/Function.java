/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.scientific;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.List.of;
import static mathaid.calculator.base.converter.AngleUnit.DEG;
import static mathaid.calculator.base.converter.AngleUnit.GRAD;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ABS;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ACOS;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ACOSH;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ACOT;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ACOTH;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ACSC;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ACSCH;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ASEC;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ASECH;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ASIN;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ASINH;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ATAN;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ATANH;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.CBRT;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.COS;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.COSH;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.COT;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.COTH;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.CSC;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.CSCH;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.DIFF;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ERF;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.EXP;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.GAMMA;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.GCD;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.INT;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.LCM;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.LIMIT;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.LOG;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.LOG10;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.LOG2;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.MAX;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.MIN;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.MOD;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.NCR;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.NPR;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.POLYGAMMA;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.PRODUCT;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.RATIONAL;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.SEC;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.SECH;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.SIGN;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.SIN;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.SINH;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.SQRT;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.SUM;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.TAN;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.TANH;
import static mathaid.calculator.base.typeset.Digits.toSegment;
import static mathaid.calculator.base.typeset.Segments.abs;
import static mathaid.calculator.base.typeset.Segments.ceil;
import static mathaid.calculator.base.typeset.Segments.diff;
import static mathaid.calculator.base.typeset.Segments.floor;
import static mathaid.calculator.base.typeset.Segments.fraction;
import static mathaid.calculator.base.typeset.Segments.genericFunction;
import static mathaid.calculator.base.typeset.Segments.integral;
import static mathaid.calculator.base.typeset.Segments.limit;
import static mathaid.calculator.base.typeset.Segments.logxy;
import static mathaid.calculator.base.typeset.Segments.pow;
import static mathaid.calculator.base.typeset.Segments.product;
import static mathaid.calculator.base.typeset.Segments.root;
import static mathaid.calculator.base.typeset.Segments.sqrt;
import static mathaid.calculator.base.typeset.Segments.sum;
import static mathaid.calculator.base.util.Arith.acos;
import static mathaid.calculator.base.util.Arith.acosh;
import static mathaid.calculator.base.util.Arith.asin;
import static mathaid.calculator.base.util.Arith.asinh;
import static mathaid.calculator.base.util.Arith.atan;
import static mathaid.calculator.base.util.Arith.atanh;
import static mathaid.calculator.base.util.Arith.cbrt;
import static mathaid.calculator.base.util.Arith.cos;
import static mathaid.calculator.base.util.Arith.cosh;
import static mathaid.calculator.base.util.Arith.exp;
import static mathaid.calculator.base.util.Arith.factorial;
import static mathaid.calculator.base.util.Arith.gamma;
import static mathaid.calculator.base.util.Arith.log;
import static mathaid.calculator.base.util.Arith.log10;
import static mathaid.calculator.base.util.Arith.log2;
import static mathaid.calculator.base.util.Arith.mod;
import static mathaid.calculator.base.util.Arith.sin;
import static mathaid.calculator.base.util.Arith.sinh;
import static mathaid.calculator.base.util.Arith.sqrt;
import static mathaid.calculator.base.util.Arith.tan;
import static mathaid.calculator.base.util.Arith.tanh;
import static mathaid.calculator.base.util.Utility.d;
import static mathaid.calculator.base.util.Utility.f;
import static mathaid.calculator.base.util.Utility.i;
import static mathaid.calculator.base.util.Utility.mc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.typeset.LinkedSegment;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.value.BigFraction;

/*
 * Date: 11 Sep 2022----------------------------------------------------------- 
 * Time created: 18:09:33---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression.scientific------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Function.java------------------------------------------------------ 
 * Class name: Function------------------------------------------------ 
 */
/**
 * An expression that is a mathematical function such as (sine, tangent, logarithm).
 * <p>
 * It consists of an identifier and a parameter list whose elements are other expression objects. e.g {@code sin(30)} is a
 * function expression where {@code sin} is the identifier of the function and {@code 30} is it's parameter. Some functions have
 * multiple parameters such as {@code pow(x, y)}.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Function extends Name {

	/*
	 * Date: 12 Sep 2022-----------------------------------------------------------
	 * Time created: 09:23:15--------------------------------------------
	 */
	/**
	 * Gets a list of all the strings used in the evaluator to represent cos, tan, sin, cot, csc, sec.
	 * <p>
	 * This used for special cases during evaluation and formatting.
	 * 
	 * @return a list of strings used by the underlying CAS to represent elementary trig functions.
	 */
	private static Collection<String> getTrigStrings() {
		return of("Sin", "Cos", "Tan", "Sec", "Csc", "Cot");
	}

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 08:55:17 ---------------------------------------------------
	 */
	/**
	 * Asserts that the argument is a representation of the number {@code 2}.
	 * 
	 * @param x the value to be checked
	 * @return {@code x == 2}.
	 */
	private static boolean isTwo(EvaluatableExpression<Params> x) {
		if (isNumber(x)) {
			BigDecimal n = d(x.getName());
			return n.compareTo(d(2L)) == 0;
		}
		return false;
	}

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 08:55:17 ---------------------------------------------------
	 */
	/**
	 * Asserts that the argument is a representation of the number {@code 1}.
	 * 
	 * @param x the value to be checked
	 * @return {@code x == 1}.
	 */
	private static boolean isOne(EvaluatableExpression<Params> x) {
		if (isNumber(x)) {
			BigDecimal n = d(x.getName());
			return n.compareTo(BigDecimal.ONE) == 0;
		}
		return false;
	}

	/*
	 * Date: 11 Sep 2022-----------------------------------------------------------
	 * Time created: 18:09:50---------------------------------------------------
	 */
	/**
	 * Constructs a {@code Function} with a given name, argument list and params object.
	 * 
	 * @param name   an {@code EvaluatableExpression} that is the identifier of this {@code Function}.
	 * @param args the argument(s)/parameter(s) to this function.
	 * @param params the {@code ExpressionParams} representing options for the evaluation and format within this expression.
	 */
	public Function(EvaluatableExpression<Params> name, List<EvaluatableExpression<Params>> args, Params params) {
		super(name.getName(), params);
		this.args = args;
		this.name = name;
	}

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 08:51:59 ---------------------------------------------------
	 */
	/**
	 * Gets the arguments given at the constructor.
	 * 
	 * @return an unmodifiable list of all the arguments of this function.
	 */
	public List<EvaluatableExpression<Params>> getArguments() {
		return unmodifiableList(args);
	}

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 09:03:00 ---------------------------------------------------
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
			EvaluatableExpression<Params> x = args.get(i);
			x.format(sb);
			s[i] = sb.toSegment();
			sb.deleteAll();
		}
		return s;
	}

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 09:09:26 ---------------------------------------------------
	 */
	/**
	 * Checks if the argument is a representation of a common fraction (with a numerator and denominator).
	 * 
	 * @param x the value to be checked.
	 * @return <code>true</code> if {@code x} is a common fraction. Returns <code>false</code> if otherwise.
	 */
	private boolean isFraction(EvaluatableExpression<Params> x) {
		return (x instanceof Operator && x.getName().equals("/"))
				|| (x instanceof Function && x.getName().equals(getParams().getFractionFunction()));
	}

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 09:13:48 ---------------------------------------------------
	 */
	/**
	 * Converts an {@code EvaluatableExpression} that is obviously a value (such as a decimal or common fraction) into a
	 * {@code BigDecimal} equivalent.
	 * 
	 * @param x the value to be converted. it is expected that this is a number or a common integer fraction.
	 * @return the {@code BigDecimal} equivalent of {@code x}.
	 */
	// Note that the argument must already be checked by isNumber(x) || is
	// IntegerFraction
	private BigDecimal asDecimal(EvaluatableExpression<Params> x) {
		if (x instanceof Function) {
			Function frac = (Function) x;
			BigInteger num = i(frac.getArguments().get(0).getName());
			BigInteger denom = i(frac.getArguments().get(1).getName());
			return f(num, denom).getDecimalExpansion(getParams().getScale() + 5);
		} else if (x instanceof Operator) {
			Operator frac = (Operator) x;
			BigInteger num = i(frac.getLeft().getName());
			BigInteger denom = i(frac.getRight().getName());
			return f(num, denom).getDecimalExpansion(getParams().getScale() + 5);
		} else if (x instanceof Name) {
			return d(x.getName());
		}
		return null;
	}

	/*
	 * Most Recent Date: 11 Sep 2022-----------------------------------------------
	 * Most recent time created: 18:09:47--------------------------------------
	 */
	/**
	 * Appends the {@code LinkedSegment} representation of this function into the format builder.
	 * <p>
	 * The name is first formatted and appended to the given builder, after which the arguments are formatted and appended.
	 * <p>
	 * This has no side-effects.
	 * 
	 * @param formatBuilder {@inheritDoc}
	 */
	@Override
	public void format(SegmentBuilder formatBuilder) {
		// pow/root, sqrt, sign, ln/log, limit,
		// differential, integral, summation, product,
		// floor, ceil, fraction/rational,
		if (getParams().getInverseTrigFunctions().containsKey(getName())) {
			formatBuilder.append(genericFunction(
					String.format("\\mathrm{%s}^{-1}", getParams().getInverseTrigFunctions().get(getName())), getName(),
					argsToSegments(), getParams().getTrig() == DEG, getParams().getTrig() == GRAD));
			return;
		} else if (getParams().getSqrt().compareTo(getName()) == 0) {
			formatBuilder.append(sqrt(argsToSegments()[0]));
			return;
		} else if (getParams().getAbs().compareTo(getName()) == 0) {
			formatBuilder.append(abs(argsToSegments()[0]));
			return;
		} else if (getParams().getGamma().compareTo(getName()) == 0) {
			formatBuilder.append(genericFunction("\\Gamma", getName(), argsToSegments(), false, false));
			return;
		} else if (getParams().getPow().compareTo(getName()) == 0) {
			LinkedSegment[] s = argsToSegments();

			if (isFraction(args.get(1))) {
				EvaluatableExpression<Params> exponentLeft;
				EvaluatableExpression<Params> exponentRight;
				if (args.get(1) instanceof Operator && args.get(1).getName().equals("/")) {// Power[x, x/y]
					exponentLeft = ((Operator) args.get(1)).getLeft();
					exponentRight = ((Operator) args.get(1)).getRight();
				} else if (args.get(1) instanceof Function
						&& args.get(1).getName().equals(getParams().getFractionFunction())) {// Power[x, Rational[x, y]]
					exponentLeft = ((Function) args.get(1)).getArguments().get(0);
					exponentRight = ((Function) args.get(1)).getArguments().get(1);
				} else {
					exponentLeft = null;
					exponentRight = null;
				}

				if (exponentLeft != null && exponentRight != null) {
					if (isTwo(exponentRight)) {
						if (isOne(exponentLeft)) {
							formatBuilder.append(sqrt(s[0]));
							return;
						}
						SegmentBuilder sb = new SegmentBuilder();
						exponentLeft.format(sb);
						formatBuilder.append(sqrt(pow(s[0], sb.toSegment())));
						return;
					}
					SegmentBuilder sb = new SegmentBuilder();
					exponentLeft.format(sb);
					LinkedSegment exponent = sb.toSegment();
					exponentRight.format(sb.deleteAll());
					formatBuilder.append(root(sb.toSegment(), pow(s[0], exponent)));
					return;
				}
			}
			formatBuilder.append(pow(s[0], s[1]));
			return;
		} else if (getParams().getSignum().compareTo(getName()) == 0) {
			formatBuilder.append(genericFunction("\\mathrm{sgn}", getName(), argsToSegments(), false, false));
			return;
		} else if (getParams().getNaturalLog().compareTo(getName()) == 0) {
			if (args.size() < 2) {
				formatBuilder
				.append(genericFunction("\\mathrm{ln}", getName(), argsToSegments(), false, false));
			}
			else {
				LinkedSegment[] args = argsToSegments();
				formatBuilder.append(logxy(args[1], args[0]));
			}
			return;
		}
		/*
		 * There currently is no syntax to deal with Limits (such as Limit[expr,
		 * x->x0]), Differentials (such as D[f, {x,n}]), Integrals (such as Integrate[f,
		 * {x,a,b}]), Summation (such as Sum[expr, {i, imin, imax}]) and product (such
		 * as Product[expr, {i, imin, imax}]). The lambda and list syntax is simply
		 * absent from mathaid right now.
		 * 
		 * I shall add support for list and lambda syntax by doing the following:
		 * 
		 * Firstly, I shall add an expression class (that extends
		 * EvaluatableExpression), It's not going to do much because there isn't much to
		 * do. This class will have a constructor that takes in a list. In case of the
		 * lambda, We will just add support inside the operator class because I plan to
		 * support it as a binary (infix) operator.
		 * 
		 * Next, I shall create a parselet (which will be registered in the syntax as a
		 * infix parselet) with the same code from the function parselet, the difference
		 * will be that they will be no name (because list do not have the prefix names
		 * that functions do) and we be consuming (see code) the right curly brace
		 * instead of the precedence director.
		 * 
		 * Next, I shall create a method in commonSyntax 'getListClose', 'getListOpen'
		 * and their subsequent 'registerListDirectors' in the syntax builder. Do not
		 * forget to register these as 'punctuator and delimiter' when building the
		 * syntax. We can also ad support for lambda here.
		 * 
		 * Next, we shall put 'right braces' and 'left braces' into getCommonTypes and
		 * getBasicTypes which are static methods that return maps in the commonSyntax
		 * class. We can also add support for 'lambda arrow' here.
		 * 
		 * Lastly, we go to the lexer's next method to add support for parsing list
		 * strings into valid tokens. Actually, the current code in the lexer's next
		 * method does not have to change for the list's sake. But it does have to
		 * change for the lambda's sake. Inside the method where we check for
		 * punctuators and delimiters, we can add an extra snippet: if the current
		 * character is '-' then look ahead for a '>' if it is found, then create a
		 * lambda token, increment current index so as not read '>' twice or else
		 * continue as before if '>' is not found.
		 */
		else if (getParams().getLimit().compareTo(getName()) == 0) {
			if (args.get(1) instanceof Operator && args.get(1).getName().equals("->")) {
				SegmentBuilder sb = new SegmentBuilder();
				args.get(0).format(sb);
				Operator x = (Operator) args.get(1);
				LinkedSegment f = sb.toSegment();
				x.getLeft().format(sb.deleteAll());
				LinkedSegment i = sb.toSegment();
				x.getRight().format(sb.deleteAll());
//				formatBuilder.append(limit(i, sb.toSegment(),
//						operator("+", "+", LinkedSegment.PLUS_OPERATOR_SEGMENT), f));
				formatBuilder.append(limit(i, sb.toSegment(), f));
				return;
			}
		} else if (getParams().getDifferential().compareTo(getName()) == 0) {
			SegmentBuilder sb = new SegmentBuilder();
			args.get(0).format(sb);
			LinkedSegment f = sb.toSegment();
			args.get(1).format(sb.deleteAll());
			LinkedSegment i = sb.toSegment();
			formatBuilder.append(diff(i, f));
			return;
		} else if (getParams().getIntegral().compareTo(getName()) == 0) {
			SegmentBuilder sb = new SegmentBuilder();
			args.get(0).format(sb);
			LinkedSegment f = sb.toSegment();
			if (args.get(1) instanceof Array) {
				Array a = (Array) args.get(1);
				if (a.getArray().size() == 3) {
					a.getArray().get(0).format(sb.deleteAll());
					LinkedSegment index = sb.toSegment();
					a.getArray().get(1).format(sb.deleteAll());
					LinkedSegment lower = sb.toSegment();
					a.getArray().get(2).format(sb.deleteAll());
					formatBuilder.append(integral(index, lower, sb.toSegment(), f));
					return;
				}
			}
			args.get(1).format(sb.deleteAll());
			formatBuilder.append(integral(sb.toSegment(), null, null, f));
			return;
		} else if (getParams().getSummation().compareTo(getName()) == 0) {
			if (args.get(1) instanceof Array) {
				Array a = (Array) args.get(1);
				if (a.getArray().size() == 3) {
					SegmentBuilder sb = new SegmentBuilder();
					args.get(0).format(sb);
					LinkedSegment f = sb.toSegment();
					a.getArray().get(0).format(sb.deleteAll());
					LinkedSegment index = sb.toSegment();
					a.getArray().get(1).format(sb.deleteAll());
					LinkedSegment lower = sb.toSegment();
					a.getArray().get(2).format(sb.deleteAll());
					formatBuilder.append(sum(index, lower, sb.toSegment(), f));
					return;
				}
			}
		} else if (getParams().getProduct().compareTo(getName()) == 0) {
			if (args.get(1) instanceof Array) {
				Array a = (Array) args.get(1);
				if (a.getArray().size() == 3) {
					SegmentBuilder sb = new SegmentBuilder();
					args.get(0).format(sb);
					LinkedSegment f = sb.toSegment();
					a.getArray().get(0).format(sb.deleteAll());
					LinkedSegment index = sb.toSegment();
					a.getArray().get(1).format(sb.deleteAll());
					LinkedSegment lower = sb.toSegment();
					a.getArray().get(2).format(sb.deleteAll());
					formatBuilder.append(product(index, lower, sb.toSegment(), f));
					return;
				}
			}
		} else if (getParams().getFloor().compareTo(getName()) == 0) {
			formatBuilder.append(floor(argsToSegments()[0]));
		} else if (getParams().getCeil().compareTo(getName()) == 0) {
			formatBuilder.append(ceil(argsToSegments()[0]));
		} else if (getParams().getFractionFunction().compareTo(getName()) == 0) {
			if (getParams().getResultType() == Params.ResultType.MFRAC && isNumber(args.get(0)) && isNumber(args.get(1))
					&& mathaid.calculator.base.util.Utility.isInteger(d(args.get(0).getName()))
					&& mathaid.calculator.base.util.Utility.isInteger(d(args.get(1).getName()))) {
				BigFraction f = f(i(args.get(0).getName()), i(args.get(1).getName()));
//				BigInteger[] mf = f.toMixed();
//				if (mf[0].signum() != 0) {
//					formatBuilder.append(toSegment(mf, 10, getParams().getIntSeparator(),
//							getParams().getMantSeparator(), getParams().getIntGroupSize(),
//							getParams().getMantGroupSize(), getParams().getNumOfRepeats()));
//					return;
//				}
				formatBuilder.append(
						toSegment(f, true, fromParams(getParams(), getParams().getNumOfRepeats())));
				return;
			}
			LinkedSegment[] s = argsToSegments();
			formatBuilder.append(fraction(s[0], s[1]));
			return;
		}
		boolean isTrig = getTrigStrings().contains(getName());
		formatBuilder.append(genericFunction(
				isTrig ? String.format("\\%s", getName().toLowerCase())
						: String.format("\\mathrm{%s}", getName().toLowerCase()),
				getName(), argsToSegments(), isTrig && getParams().getTrig() == DEG,
				isTrig && getParams().getTrig() == GRAD));
	}

	/*
	 * Most Recent Date: 11 Sep 2022-----------------------------------------------
	 * Most recent time created: 18:09:47--------------------------------------
	 */
	/**
	 * Performs computations on the arguments and returns the result as an {@code EvaluatableExpression}.
	 * <p>
	 * All arguments given will have their {@link EvaluatableExpression#evaluate() evaluate()} method called on them, then their
	 * results will be applied to this function. The result itself depends on the type of function, it's arguments, as well as the
	 * {@linkplain Params#getResultType() result type}.
	 * <p>
	 * All calculations are done with a numerical precision of <code>{@linkplain Params#getScale() scale} + 5</code> and may throw
	 * exceptions that indicate that value(s) were out of range.
	 * <p>
	 * This has no side-effects.
	 * 
	 * @return a new {@code EvaluatableExpression} that is the result of evaluating this function. May return the same object
	 *         especially if called more than once.
	 */
	@Override
	public EvaluatableExpression<Params> evaluate() {
		final MathContext mc = mc(getParams().getScale() + 5);
		EvaluatableExpression<Params> firstArg = args.get(0).evaluate();

		if (isNumber(firstArg) || isIntegerFraction(firstArg))
			switch (getName()) {
			case RATIONAL: {
				EvaluatableExpression<Params> secondArg = args.get(1).evaluate();
				if (getParams().getResultType() == Params.ResultType.FRAC
						|| getParams().getResultType() == Params.ResultType.MFRAC)
					return new Function(name, asList(firstArg, secondArg), getParams());
				if (isNumber(firstArg) && isNumber(secondArg))
					return new Name(f(i(firstArg.getName()), i(secondArg.getName()))
							.getDecimalExpansion(getParams().getScale() + 5).toString(), getParams());
				return new Function(name, asList(firstArg, secondArg), getParams());
			}
			/*
			 * Make sure all numeric arguments are defered here using symja's
			 * 'Defer[Sin[x]]' method. Use this for all trigonometry methods
			 */
			case SIN: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(sin(n, getParams().getTrig(), mc).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case ASIN: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(asin(n, getParams().getTrig(), mc).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case SINH: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(sinh(n, mc).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case ASINH: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(asinh(n, mc).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case COS: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(cos(n, getParams().getTrig(), mc).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case ACOS: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(acos(n, getParams().getTrig(), mc).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case COSH: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(cosh(n, mc).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case ACOSH: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(acosh(n, mc).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case TAN: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(tan(n, getParams().getTrig(), mc).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case ATAN: {
				if (args.size() == 2) {
					EvaluatableExpression<Params> secondArg = args.get(1).evaluate();
					if (isNumber(secondArg) || isIntegerFraction(secondArg)) {
						BigDecimal x = asDecimal(firstArg);
						BigDecimal y = asDecimal(secondArg);
						if (x != null && y != null)
							return new Name(atan(x, y, getParams().getTrig(), mc).toString(), getParams());
						return new Function(name, asList(firstArg, secondArg), getParams());
					}
					return new Function(name, asList(firstArg, secondArg), getParams());
				}
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(atan(n, getParams().getTrig(), mc).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case TANH: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(tanh(n, mc).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case ATANH: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(atanh(n, mc).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case COT: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(tan(BigDecimal.ONE.divide(n, mc), getParams().getTrig(), mc).toString(),
							getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case ACOT: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(atan(BigDecimal.ONE.divide(n, mc), getParams().getTrig(), mc).toString(),
							getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case COTH: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(tanh(BigDecimal.ONE.divide(n, mc), mc).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case ACOTH: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(atanh(BigDecimal.ONE.divide(n, mc), mc).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case CSC: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(sin(BigDecimal.ONE.divide(n, mc), getParams().getTrig(), mc).toString(),
							getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case ACSC: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(asin(BigDecimal.ONE.divide(n, mc), getParams().getTrig(), mc).toString(),
							getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case CSCH: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(sinh(BigDecimal.ONE.divide(n, mc), mc).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case ACSCH: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(asinh(BigDecimal.ONE.divide(n, mc), mc).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case SEC: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(cos(BigDecimal.ONE.divide(n, mc), getParams().getTrig(), mc).toString(),
							getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case ASEC: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(acos(BigDecimal.ONE.divide(n, mc), getParams().getTrig(), mc).toString(),
							getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case SECH: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(cosh(BigDecimal.ONE.divide(n, mc), mc).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case ASECH: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(acosh(BigDecimal.ONE.divide(n, mc), mc).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case LOG: {
				if (args.size() == 2) {
					EvaluatableExpression<Params> secondArg = args.get(1).evaluate();
					if (isNumber(secondArg) || isIntegerFraction(secondArg)) {
						BigDecimal x = asDecimal(firstArg);
						BigDecimal y = asDecimal(secondArg);
						if (x != null && y != null)
							return new Name(log(x, y, mc).toString(), getParams());
						return new Function(name, asList(firstArg, secondArg), getParams());
					}
					return new Function(name, asList(firstArg, secondArg), getParams());
				}
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(log(n, mc).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
//			case LN:
//				return return new Name(log(new BigDecimal(name.name()), mc).toString());
			case LOG10: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(log10(n, mc).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case LOG2: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(log2(n, mc).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case EXP: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(exp(n, mc).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case SQRT: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(sqrt(n, mc).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case CBRT: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(cbrt(n, getParams().getScale()).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case ABS: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(n.abs(mc).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case SIGN: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					return new Name(String.valueOf(n.signum()), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case GCD: {
				EvaluatableExpression<Params> secondArg = args.get(1).evaluate();
				if ((isNumber(secondArg) || isIntegerFraction(secondArg))) {
					BigDecimal x = asDecimal(firstArg);
					BigDecimal y = asDecimal(secondArg);
					if (x != null && y != null && mathaid.calculator.base.util.Utility.isInteger(x)
							&& mathaid.calculator.base.util.Utility.isInteger(y))
						return new Name(x.toBigInteger().gcd(y.toBigInteger()).toString(), getParams());
					return new Function(name, asList(firstArg, secondArg), getParams());
				}
				return new Function(name, asList(firstArg, secondArg), getParams());
			}
			case LCM: {
				EvaluatableExpression<Params> secondArg = args.get(1).evaluate();
				if ((isNumber(secondArg) || isIntegerFraction(secondArg))) {
					BigFraction x = f(asDecimal(firstArg));
					BigFraction y = f(asDecimal(secondArg));
					if (x != null && y != null)
						return new Name(x.lcm(y).toString(), getParams());
					return new Function(name, asList(firstArg, secondArg), getParams());
				}
				return new Function(name, asList(firstArg, secondArg), getParams());
			}
			case MOD: {
				EvaluatableExpression<Params> secondArg = args.get(1).evaluate();
				if ((isNumber(secondArg) || isIntegerFraction(secondArg))) {
					BigDecimal x = asDecimal(firstArg);
					BigDecimal y = asDecimal(secondArg);
					if (x != null && y != null)
						return new Name(mod(x, y).toString(), getParams());
					return new Function(name, asList(firstArg, secondArg), getParams());
				}
				return new Function(name, asList(firstArg, secondArg), getParams());
			}
			case NPR: {
				EvaluatableExpression<Params> secondArg = args.get(1).evaluate();
				if ((isNumber(secondArg) || isIntegerFraction(secondArg))) {
					BigDecimal x = asDecimal(firstArg);
					BigDecimal y = asDecimal(secondArg);
					if (x != null && y != null && mathaid.calculator.base.util.Utility.isInteger(x)
							&& mathaid.calculator.base.util.Utility.isInteger(y)) {
						BigDecimal n = factorial(x.abs()).divide(factorial(x.abs().subtract(y.abs())));
						return new Name(n.multiply(d(x.min(y).signum())).toString(), getParams());
					}
					return new Function(name, asList(firstArg, secondArg), getParams());
				}
				return new Function(name, asList(firstArg, secondArg), getParams());
			}
			case NCR: {
				EvaluatableExpression<Params> secondArg = args.get(1).evaluate();
				if ((isNumber(secondArg) || isIntegerFraction(secondArg))) {
					BigDecimal x = asDecimal(firstArg);
					BigDecimal y = asDecimal(secondArg);
					if (x != null && y != null && mathaid.calculator.base.util.Utility.isInteger(x)
							&& mathaid.calculator.base.util.Utility.isInteger(y)) {
						BigDecimal c = factorial(x.abs());
						BigDecimal d = factorial(y.abs());
						BigDecimal e = factorial(x.abs().subtract(y.abs()));
						BigDecimal f = c.divide(d.multiply(e));
						return new Name(f.multiply(d(x.min(y).signum())).toString(), getParams());
					}
					return new Function(name, asList(firstArg, secondArg), getParams());
				}
				return new Function(name, asList(firstArg, secondArg), getParams());
			}
			case GAMMA: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(gamma(n, getParams().getScale()).toString(), getParams());
				}
				return new Function(name, asList(firstArg), getParams());
			}
			case POLYGAMMA:
				break;
			case MIN: {
				EvaluatableExpression<Params> secondArg = args.get(1).evaluate();
				if ((isNumber(secondArg) || isIntegerFraction(secondArg))) {
					BigDecimal x = asDecimal(firstArg);
					BigDecimal y = asDecimal(secondArg);
					if (x != null && y != null) {
						return new Name(x.min(y).toString(), getParams());
					}
					return new Function(name, asList(firstArg, secondArg), getParams());
				}
				return new Function(name, asList(firstArg, secondArg), getParams());
			}
			case MAX: {
				EvaluatableExpression<Params> secondArg = args.get(1).evaluate();
				if ((isNumber(secondArg) || isIntegerFraction(secondArg))) {
					BigDecimal x = asDecimal(firstArg);
					BigDecimal y = asDecimal(secondArg);
					if (x != null && y != null) {
						return new Name(x.max(y).toString(), getParams());
					}
					return new Function(name, asList(firstArg, secondArg), getParams());
				}
				return new Function(name, asList(firstArg, secondArg), getParams());
			}
			// Symbolic functions
			case SUM:
			case DIFF:
			case LIMIT:
			case PRODUCT:
			case INT:
//			case NINT:
				/*
				 * This will not be implemented here it will be implemented at view part of the
				 * input. That is, at the point where input(Conj(z)) is called. At that point,
				 * the complex argument will be evaluated to give the conjugate
				 */
			case ERF:
			default:
			}

		List<EvaluatableExpression<Params>> arg = new ArrayList<>();
		for (int i = 1; i < args.size(); i++)
			arg.add(arg.get(i).evaluate());
		arg.add(0, firstArg);
		return new Function(this.name, arg, getParams());
	}

	public String toString() {
		return new StringBuilder(getName()).append(args).toString();
	}
	
	/**
	 * *.The arguments of this function
	 */
	private final List<EvaluatableExpression<Params>> args;
	/**
	 * The name of this function stored as an {@code Expression} for formatting purposes.
	 */
	private final EvaluatableExpression<Params> name;

	// A fun implementation of java.util.function.Function interface
//	@Override
//	public String apply(String[] t) {
//		List<EvaluatableExpression<Params>> l = new ArrayList<>();
//		for(String s : t) l.add(new Name(s, getParams()));
//		Function f = new Function(name, l, getParams());
//		StringBuilder sb = new StringBuilder();
//		SegmentBuilder seb = new SegmentBuilder();
//		f.evaluate().format(seb);
//		seb.toSegment().toString(sb, null, new ArrayList<>(java.util.Arrays.asList(-1)));
//		return sb.toString();
//	}

}
