/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.scientific;

import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mathaid.calculator.base.converter.AngleUnit;
import mathaid.calculator.base.evaluator.Calculator;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.typeset.Digits;
import mathaid.calculator.base.typeset.LinkedSegment;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;
import mathaid.calculator.base.util.Arith;
import mathaid.calculator.base.util.Utility;
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
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Function extends Name {

	/*
	 * Date: 11 Sep 2022-----------------------------------------------------------
	 * Time created: 18:09:50---------------------------------------------------
	 */
	/**
	 * @param name
	 * @param params
	 */
	public Function(EvaluatableExpression<Params> name, List<EvaluatableExpression<Params>> args, Params params) {
		super(name.getName(), params);
		this.args = args;
		this.name = name;
	}

	public List<EvaluatableExpression<Params>> getArguments() {
		return Collections.unmodifiableList(args);
	}

	private LinkedSegment[] argsToSegments() {
		LinkedSegment[] s = new LinkedSegment[args.size()];
		SegmentBuilder sb = new SegmentBuilder();
		for (int i = 0; i < args.size(); i++) {
			EvaluatableExpression<Params> x = args.get(i);
			x.format(sb.deleteAll());
			s[i] = sb.toSegment();
		}
		return s;
	}

	private boolean isFraction(EvaluatableExpression<Params> x) {
		return (x instanceof Operator && x.getName().equals("/"))
				|| (x instanceof Function && x.getName().equals(getParams().getFractionFunction()));
	}

	private boolean isTwo(EvaluatableExpression<Params> x) {
		if (isNumber(x)) {
			BigDecimal n = new BigDecimal(x.getName());
			return n.compareTo(BigDecimal.valueOf(2L)) == 0;
		}
		return false;
	}

	private boolean isOne(EvaluatableExpression<Params> x) {
		if (isNumber(x)) {
			BigDecimal n = new BigDecimal(x.getName());
			return n.compareTo(BigDecimal.ONE) == 0;
		}
		return false;
	}

	// Note that the argument must already be checked by isNumber(x) || is
	// IntegerFraction
	private BigDecimal asDecimal(EvaluatableExpression<Params> x) {
		if (x instanceof Function) {
			Function frac = (Function) x;
			BigInteger num = new BigInteger(frac.getArguments().get(0).getName());
			BigInteger denom = new BigInteger(frac.getArguments().get(1).getName());
			return new BigFraction(num, denom).getDecimalExpansion(getParams().getScale() + 5);
		} else if (x instanceof Operator) {
			Operator frac = (Operator) x;
			BigInteger num = new BigInteger(frac.getLeft().getName());
			BigInteger denom = new BigInteger(frac.getRight().getName());
			return new BigFraction(num, denom).getDecimalExpansion(getParams().getScale() + 5);
		} else if (x instanceof Name) {
			return new BigDecimal(x.getName());
		}
		return null;
	}

	/*
	 * Most Recent Date: 11 Sep 2022-----------------------------------------------
	 * Most recent time created: 18:09:47--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param formatBuilder
	 */
	@Override
	public void format(SegmentBuilder formatBuilder) {
		// pow/root, sqrt, sign, ln/log, limit,
		// differential, integral, summation, product,
		// floor, ceil, fraction/rational,
		if (getParams().getInverseTrigFunctions().containsKey(getName())) {
			formatBuilder.append(Segments.genericFunction(
					String.format("\\mathrm{%s}^{-1}", getParams().getInverseTrigFunctions().get(getName())), getName(),
					argsToSegments(), getParams().getTrig() == AngleUnit.DEG, getParams().getTrig() == AngleUnit.GRAD));
			return;
		} else if (getParams().getSqrt().compareTo(getName()) == 0) {
			formatBuilder.append(Segments.sqrt(argsToSegments()[0]));
			return;
		} else if (getParams().getAbs().compareTo(getName()) == 0) {
			formatBuilder.append(Segments.abs(argsToSegments()[0]));
			return;
		} else if (getParams().getGamma().compareTo(getName()) == 0) {
			formatBuilder.append(Segments.genericFunction("\\Gamma", getName(), argsToSegments(), false, false));
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
							formatBuilder.append(Segments.sqrt(s[0]));
							return;
						}
						SegmentBuilder sb = new SegmentBuilder();
						exponentLeft.format(sb);
						formatBuilder.append(Segments.sqrt(Segments.pow(s[0], sb.toSegment())));
						return;
					}
					SegmentBuilder sb = new SegmentBuilder();
					exponentLeft.format(sb);
					LinkedSegment exponent = sb.toSegment();
					exponentRight.format(sb.deleteAll());
					formatBuilder.append(Segments.root(sb.toSegment(), Segments.pow(s[0], exponent)));
					return;
				}
			}
			formatBuilder.append(Segments.pow(s[0], s[1]));
			return;
		} else if (getParams().getSignum().compareTo(getName()) == 0) {
			formatBuilder.append(Segments.genericFunction("\\mathrm{sgn}", getName(), argsToSegments(), false, false));
			return;
		} else if (getParams().getNaturalLog().compareTo(getName()) == 0) {
			if (args.size() > 1)
				formatBuilder.append(Segments.genericFunction("\\mathrm{ln}", getName(), argsToSegments(), false, false));
			else {
				LinkedSegment[] args = argsToSegments();
				formatBuilder.append(Segments.logxy(args[1], args[0]));
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
//				formatBuilder.append(Segments.limit(i, sb.toSegment(),
//						Segments.operator("+", "+", LinkedSegment.PLUS_OPERATOR_SEGMENT), f));
				formatBuilder.append(Segments.limit(i, sb.toSegment(), f));
				return;
			}
		} else if (getParams().getDifferential().compareTo(getName()) == 0) {
			SegmentBuilder sb = new SegmentBuilder();
			args.get(0).format(sb);
			LinkedSegment f = sb.toSegment();
			args.get(1).format(sb.deleteAll());
			LinkedSegment i = sb.toSegment();
			formatBuilder.append(Segments.diff(i, f));
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
					formatBuilder.append(Segments.integral(index, lower, sb.toSegment(), f));
					return;
				}
			}
			args.get(1).format(sb.deleteAll());
			formatBuilder.append(Segments.integral(sb.toSegment(), null, null, f));
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
					formatBuilder.append(Segments.sum(index, lower, sb.toSegment(), f));
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
					formatBuilder.append(Segments.product(index, lower, sb.toSegment(), f));
					return;
				}
			}
		} else if (getParams().getFloor().compareTo(getName()) == 0) {
			formatBuilder.append(Segments.floor(argsToSegments()[0]));
		} else if (getParams().getCeil().compareTo(getName()) == 0) {
			formatBuilder.append(Segments.ceil(argsToSegments()[0]));
		} else if (getParams().getFractionFunction().compareTo(getName()) == 0) {
			if (getParams().getResultType() == Params.ResultType.MFRAC && isNumber(args.get(0)) && isNumber(args.get(1))
					&& Utility.isInteger(new BigDecimal(args.get(0).getName()))
					&& Utility.isInteger(new BigDecimal(args.get(1).getName()))) {
				BigFraction f = new BigFraction(new BigInteger(args.get(0).getName()),
						new BigInteger(args.get(1).getName()));
//				BigInteger[] mf = f.toMixed();
//				if (mf[0].signum() != 0) {
//					formatBuilder.append(Segments.toSegment(mf, 10, getParams().getIntSeparator(),
//							getParams().getMantSeparator(), getParams().getIntGroupSize(),
//							getParams().getMantGroupSize(), getParams().getNumOfRepeats()));
//					return;
//				}
				formatBuilder.append(Digits.toSegment(f, true, Calculator.fromParams(getParams(), getParams().getNumOfRepeats())));
				return;
			}
			LinkedSegment[] s = argsToSegments();
			formatBuilder.append(Segments.fraction(s[0], s[1]));
			return;
		}
		boolean isTrig = getParams().getTrigStrings().contains(getName());
		formatBuilder.append(Segments.genericFunction(
				isTrig ? String.format("\\%s", getName().toLowerCase())
						: String.format("\\mathrm{%s}", getName().toLowerCase()),
				getName(), argsToSegments(), isTrig && getParams().getTrig() == AngleUnit.DEG,
				isTrig && getParams().getTrig() == AngleUnit.GRAD));
	}

	/*
	 * Most Recent Date: 11 Sep 2022-----------------------------------------------
	 * Most recent time created: 18:09:47--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public EvaluatableExpression<Params> evaluate() {
		final MathContext mc = new MathContext(getParams().getScale() + 5, RoundingMode.HALF_EVEN);
		EvaluatableExpression<Params> firstArg = args.get(0).evaluate();

		if (isNumber(firstArg) || isIntegerFraction(firstArg))
			switch (getName()) {
			case RATIONAL: {
				EvaluatableExpression<Params> secondArg = args.get(1).evaluate();
				if (getParams().getResultType() == Params.ResultType.FRAC
						|| getParams().getResultType() == Params.ResultType.MFRAC)
					return new Function(name, Arrays.asList(firstArg, secondArg), getParams());
				if (isNumber(firstArg) && isNumber(secondArg))
					return new Name(
							new BigFraction(new BigInteger(firstArg.getName()), new BigInteger(secondArg.getName()))
									.getDecimalExpansion(getParams().getScale() + 5).toString(),
							getParams());
				return new Function(name, Arrays.asList(firstArg, secondArg), getParams());
			}
			/*
			 * Make sure all numeric arguments are defered here using symja's
			 * 'Defer[Sin[x]]' method. Use this for all trigonometry methods
			 */
			case SIN: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.sin(n, getParams().getTrig(), mc).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case ASIN: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.asin(n, getParams().getTrig(), mc).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case SINH: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.sinh(n, mc).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case ASINH: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.asinh(n, mc).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case COS: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.cos(n, getParams().getTrig(), mc).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case ACOS: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.acos(n, getParams().getTrig(), mc).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case COSH: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.cosh(n, mc).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case ACOSH: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.acosh(n, mc).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case TAN: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.tan(n, getParams().getTrig(), mc).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case ATAN: {
				if (args.size() == 2) {
					EvaluatableExpression<Params> secondArg = args.get(1).evaluate();
					if (isNumber(secondArg) || isIntegerFraction(secondArg)) {
						BigDecimal x = asDecimal(firstArg);
						BigDecimal y = asDecimal(secondArg);
						if (x != null && y != null)
							return new Name(Arith.atan(x, y, getParams().getTrig(), mc).toString(), getParams());
						return new Function(name, Arrays.asList(firstArg, secondArg), getParams());
					}
					return new Function(name, Arrays.asList(firstArg, secondArg), getParams());
				}
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.atan(n, getParams().getTrig(), mc).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case TANH: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.tanh(n, mc).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case ATANH: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.atanh(n, mc).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case COT: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.tan(BigDecimal.ONE.divide(n, mc), getParams().getTrig(), mc).toString(),
							getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case ACOT: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.atan(BigDecimal.ONE.divide(n, mc), getParams().getTrig(), mc).toString(),
							getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case COTH: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.tanh(BigDecimal.ONE.divide(n, mc), mc).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case ACOTH: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.atanh(BigDecimal.ONE.divide(n, mc), mc).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case CSC: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.sin(BigDecimal.ONE.divide(n, mc), getParams().getTrig(), mc).toString(),
							getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case ACSC: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.asin(BigDecimal.ONE.divide(n, mc), getParams().getTrig(), mc).toString(),
							getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case CSCH: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.sinh(BigDecimal.ONE.divide(n, mc), mc).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case ACSCH: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.asinh(BigDecimal.ONE.divide(n, mc), mc).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case SEC: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.cos(BigDecimal.ONE.divide(n, mc), getParams().getTrig(), mc).toString(),
							getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case ASEC: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.acos(BigDecimal.ONE.divide(n, mc), getParams().getTrig(), mc).toString(),
							getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case SECH: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.cosh(BigDecimal.ONE.divide(n, mc), mc).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case ASECH: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.acosh(BigDecimal.ONE.divide(n, mc), mc).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case LOG: {
				if (args.size() == 2) {
					EvaluatableExpression<Params> secondArg = args.get(1).evaluate();
					if (isNumber(secondArg) || isIntegerFraction(secondArg)) {
						BigDecimal x = asDecimal(firstArg);
						BigDecimal y = asDecimal(secondArg);
						if (x != null && y != null)
							return new Name(Arith.log(x, y, mc).toString(), getParams());
						return new Function(name, Arrays.asList(firstArg, secondArg), getParams());
					}
					return new Function(name, Arrays.asList(firstArg, secondArg), getParams());
				}
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.log(n, mc).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
//			case LN:
//				return new Name(Arith.log(new BigDecimal(name.name()), mc).toString());
			case LOG10: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.log10(n, mc).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case LOG2: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.log2(n, mc).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case EXP: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.exp(n, mc).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case SQRT: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.sqrt(n, mc).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case CBRT: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.cbrt(n, getParams().getScale()).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case ABS: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(n.abs(mc).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case SIGN: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(String.valueOf(n.signum()), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
			}
			case GCD: {
				EvaluatableExpression<Params> secondArg = args.get(1).evaluate();
				if ((isNumber(secondArg) || isIntegerFraction(secondArg))) {
					BigDecimal x = asDecimal(firstArg);
					BigDecimal y = asDecimal(secondArg);
					if (x != null && y != null && Utility.isInteger(x) && Utility.isInteger(y))
						return new Name(x.toBigInteger().gcd(y.toBigInteger()).toString(), getParams());
					return new Function(name, Arrays.asList(firstArg, secondArg), getParams());
				}
				return new Function(name, Arrays.asList(firstArg, secondArg), getParams());
			}
			case LCM: {
				EvaluatableExpression<Params> secondArg = args.get(1).evaluate();
				if ((isNumber(secondArg) || isIntegerFraction(secondArg))) {
					BigFraction x = new BigFraction(asDecimal(firstArg));
					BigFraction y = new BigFraction(asDecimal(secondArg));
					if (x != null && y != null)
						return new Name(x.lcm(y).toString(), getParams());
					return new Function(name, Arrays.asList(firstArg, secondArg), getParams());
				}
				return new Function(name, Arrays.asList(firstArg, secondArg), getParams());
			}
			case MOD: {
				EvaluatableExpression<Params> secondArg = args.get(1).evaluate();
				if ((isNumber(secondArg) || isIntegerFraction(secondArg))) {
					BigDecimal x = asDecimal(firstArg);
					BigDecimal y = asDecimal(secondArg);
					if (x != null && y != null)
						return new Name(Arith.mod(x, y).toString(), getParams());
					return new Function(name, Arrays.asList(firstArg, secondArg), getParams());
				}
				return new Function(name, Arrays.asList(firstArg, secondArg), getParams());
			}
			case NPR: {
				EvaluatableExpression<Params> secondArg = args.get(1).evaluate();
				if ((isNumber(secondArg) || isIntegerFraction(secondArg))) {
					BigDecimal x = asDecimal(firstArg);
					BigDecimal y = asDecimal(secondArg);
					if (x != null && y != null && Utility.isInteger(x) && Utility.isInteger(y)) {
						BigDecimal n = Arith.factorial(x.abs()).divide(Arith.factorial(x.abs().subtract(y.abs())));
						return new Name(n.multiply(new BigDecimal(x.min(y).signum())).toString(), getParams());
					}
					return new Function(name, Arrays.asList(firstArg, secondArg), getParams());
				}
				return new Function(name, Arrays.asList(firstArg, secondArg), getParams());
			}
			case NCR: {
				EvaluatableExpression<Params> secondArg = args.get(1).evaluate();
				if ((isNumber(secondArg) || isIntegerFraction(secondArg))) {
					BigDecimal x = asDecimal(firstArg);
					BigDecimal y = asDecimal(secondArg);
					if (x != null && y != null && Utility.isInteger(x) && Utility.isInteger(y)) {
						BigDecimal c = Arith.factorial(x.abs());
						BigDecimal d = Arith.factorial(y.abs());
						BigDecimal e = Arith.factorial(x.abs().subtract(y.abs()));
						BigDecimal f = c.divide(d.multiply(e));
						return new Name(f.multiply(new BigDecimal(x.min(y).signum())).toString(), getParams());
					}
					return new Function(name, Arrays.asList(firstArg, secondArg), getParams());
				}
				return new Function(name, Arrays.asList(firstArg, secondArg), getParams());
			}
			case GAMMA: {
				BigDecimal n = asDecimal(firstArg);
				if (n != null) {
					new Name(Arith.gamma(n, getParams().getScale()).toString(), getParams());
				}
				return new Function(name, Arrays.asList(firstArg), getParams());
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
					return new Function(name, Arrays.asList(firstArg, secondArg), getParams());
				}
				return new Function(name, Arrays.asList(firstArg, secondArg), getParams());
			}
			case MAX: {
				EvaluatableExpression<Params> secondArg = args.get(1).evaluate();
				if ((isNumber(secondArg) || isIntegerFraction(secondArg))) {
					BigDecimal x = asDecimal(firstArg);
					BigDecimal y = asDecimal(secondArg);
					if (x != null && y != null) {
						return new Name(x.max(y).toString(), getParams());
					}
					return new Function(name, Arrays.asList(firstArg, secondArg), getParams());
				}
				return new Function(name, Arrays.asList(firstArg, secondArg), getParams());
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

	private final List<EvaluatableExpression<Params>> args;
	private final EvaluatableExpression<Params> name;

}
