/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.scientific;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;

import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.typeset.LinkedSegment;
import mathaid.calculator.base.typeset.Segment;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;
import mathaid.calculator.base.util.Arith;
import mathaid.calculator.base.util.Utility;

/*
 * Date: 11 Sep 2022----------------------------------------------------------- 
 * Time created: 01:59:44---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression.scientific------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Operator.java------------------------------------------------------ 
 * Class name: Operator------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Operator extends Name {

	/*
	 * Date: 11 Sep 2022-----------------------------------------------------------
	 * Time created: 01:59:48---------------------------------------------------
	 */
	/**
	 * @param name
	 * @param params
	 */
	public Operator(EvaluatableExpression<Params> left, String op, EvaluatableExpression<Params> right, Params params) {
		super(op, params);
		this.left = left;
		this.right = right;
	}

	public EvaluatableExpression<Params> getLeft() {
		return left;
	}

	public EvaluatableExpression<Params> getRight() {
		return right;
	}

	/*
	 * Most Recent Date: 11 Sep 2022-----------------------------------------------
	 * Most recent time created: 02:08:54--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param formatBuilder
	 */
	@Override
	public void format(SegmentBuilder formatBuilder) {
		Params p = getParams();
		switch (getName()) {
		case "/": {
			SegmentBuilder sg = new SegmentBuilder(formatBuilder.toSegment());
			// Log[5]/Log[10]
			if (left instanceof Function && right instanceof Function) {
				Function fx = (Function) left;
				Function fy = (Function) right;
				if (fx.getName().equals(p.getLog()) && fy.getName().equals(p.getLog())) {
					if (fx.getArguments().size() == 1 && fy.getArguments().size() == 1) {
						SegmentBuilder sb = new SegmentBuilder();
						fx.getArguments().get(0).format(sb);
						LinkedSegment arg1 = sb.toSegment();
						fy.getArguments().get(0).format(sb.deleteAll());
						LinkedSegment arg2 = sb.toSegment();
						formatBuilder.append(Segments.logxy(arg1, arg2));
						return;
					}
				}
			}
//			sg = new SegmentBuilder();
			/*
			 * The following 2 lines (and the conditional block) checks whether the left
			 * side is a fraction such as \frac{3}{x} / whatever. In such a case, no need to
			 * use fraction formatting.
			 */
			left.format(sg);
			boolean containsFraction = sg.indexOf(Segment.Type.FRACTION) >= 0;
			if (!containsFraction) {
				SegmentBuilder sb = new SegmentBuilder();
				left.format(sb);
				LinkedSegment arg1 = sb.toSegment();
				right.format(sb.deleteAll());
				LinkedSegment arg2 = sb.toSegment();
				formatBuilder.append(Segments.fraction(arg1, arg2));
				return;
			}
			left.format(formatBuilder);
			String[] div = p.getDivisionString();
			formatBuilder.append(Segments.operator(div[0], div[1]));
			return;
		}
		case "+":
		case "-":
		case "->":
			break;
		case "*": {
			// for symja expressions such as 5*x or I*3.5
			if (((left instanceof Name && !Utility.isNumber(left.getName()))
					|| (right instanceof Name && !Utility.isNumber(right.getName())))
					&& ((!(left instanceof Group)) && (!(right instanceof Group)))) {
				if (Utility.isNumber(right.getName())) {
					right.format(formatBuilder);
					left.format(formatBuilder);
					return;
				} else if (left.getName().length() == 1 && right.getName().length() == 1) {
					if (right.getName().compareTo(left.getName()) < 0) {
						right.format(formatBuilder);
						left.format(formatBuilder);
						return;
					}
				}
			} else if (left instanceof Group || right instanceof Group) {// (x+y)*z
				left.format(formatBuilder);
				right.format(formatBuilder);
				return;
			}
			left.format(formatBuilder);
			String[] mul = p.getMultiplicationString();
			formatBuilder.append(Segments.operator(mul[0], mul[1]));
			right.format(formatBuilder);
			return;
		}
		case "^": {
			/*
			 * x^y, x^0.5, x^(3/4), x^(1/5), x^(x/y), x^-3
			 */
			if (right instanceof Operator) {
				Operator div = (Operator) right;
				if (div.getName().compareTo(p.getDivisionString()[0]) == 0) {
					if (div.right instanceof Name && Utility.isNumber(div.right.getName())) {
						BigDecimal num = new BigDecimal(div.right.getName());
						BigDecimal num2;
						try {
							num2 = new BigDecimal(div.left.getName());
						} catch (@SuppressWarnings("unused") ClassCastException | NumberFormatException e) {
							num2 = null;
						}
						boolean rightIsInteger = Utility.isInteger(num);
						if (rightIsInteger) {
							SegmentBuilder sb = new SegmentBuilder();
							left.format(sb);
							LinkedSegment base = sb.toSegment();
							div.left.format(sb.deleteAll());
							LinkedSegment exponent = sb.toSegment();
							LinkedSegment radicand;
							if (num2 != null && num2.compareTo(BigDecimal.ONE) == 0)
								radicand = base;
							else
								radicand = Segments.pow(base, exponent);
							if (num.compareTo(BigDecimal.valueOf(2L)) == 0) {
								formatBuilder.append(Segments.sqrt(radicand));
								return;
							}
							div.right.format(sb.deleteAll());
							LinkedSegment index = sb.toSegment();
							formatBuilder.append(Segments.root(index, radicand));
//							formatBuilder.append(Segments.sq)
							return;
						}
					}
				}
			}
			SegmentBuilder sb = new SegmentBuilder();
			left.format(sb);
			LinkedSegment base = sb.toSegment();
			right.format(sb.deleteAll());
			LinkedSegment exponent = sb.toSegment();
			formatBuilder.append(Segments.pow(base, exponent));
			return;
		}
		default:
		}
		left.format(formatBuilder);
		formatBuilder.append(Segments.operator(getName(), getName()));
		right.format(formatBuilder);

	}

	/*
	 * Most Recent Date: 11 Sep 2022-----------------------------------------------
	 * Most recent time created: 02:08:54--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public EvaluatableExpression<Params> evaluate() {
		EvaluatableExpression<Params> left = this.left.evaluate();
		EvaluatableExpression<Params> right = this.right.evaluate();
		Params p = getParams();
		/*
		 * throw exception if is complex returns false and there is a complex digit in
		 * the expression
		 */
		switch (getName()) {
		case "+":
			if (isNumber(left))
				if (isNumber(right))
					return new Name(new BigDecimal(left.getName())
							.add(new BigDecimal(right.getName()), new MathContext(p.getScale(), RoundingMode.HALF_EVEN))
							.toPlainString(), p);
			break;
		case "-":
			if (isNumber(left))
				if (isNumber(right))
					return new Name(new BigDecimal(left.getName()).subtract(new BigDecimal(right.getName()),
							new MathContext(p.getScale(), RoundingMode.HALF_EVEN)).toPlainString(), p);
			break;
		case "/":
			if (isNumber(left))
				if (isNumber(right))
					return new Name(new BigDecimal(left.getName()).divide(new BigDecimal(right.getName()),
							new MathContext(p.getScale(), RoundingMode.HALF_EVEN)).toPlainString(), p);
			// Log[5]/Log[2]
			if (left instanceof Function && right instanceof Function) {
				if (left.getName().equals(p.getLog()) && right.getName().equals(p.getLog())) {
					Function fx = (Function) left;
					Function fy = (Function) right;
					return new Function(new Name(p.getLog(), p), Arrays.asList(fx, fy), p).evaluate();
				}
			}
			break;
		case "*":
			if (isNumber(left))
				if (isNumber(right))
					return new Name(new BigDecimal(left.getName()).multiply(new BigDecimal(right.getName()),
							new MathContext(p.getScale(), RoundingMode.HALF_EVEN)).toPlainString(), p);
			break;
		case "^":
			if (isNumber(left))
				if (isNumber(right))
					/*
					 * Somehow, the commented code Returns only integers for E^2. The bottom one
					 * however is slower
					 */
//					Apfloat n1 = new Apfloat(left.name()), n2 = new Apfloat(right.name());
//					n1 = ApfloatMath.pow(n1, n2);
//					return new Name(new BigDecimal(n1.toString(true)).toPlainString());
					return new Name(Arith.pow(new BigDecimal(left.getName()), new BigDecimal(right.getName()),
							new MathContext(p.getScale(), RoundingMode.HALF_EVEN)).toPlainString(), p);
			break;
		case ".":
			/*
			 * the period '.' character will be an infix as well as a prefix, and also, no
			 * need to handle it in the 'toString(StringBuilder)' method because even if
			 * isExpression return true the default implementation handles it perfectly fine
			 */
			return new Name(String.format("%1$s%2$s%3$s", ((Name) left).getName(), getName(), ((Name) right).getName()),
					p);
		/*
		 * for exponents, although this is already covered by the lexer object and is
		 * not part of the syntax
		 */
		case "e":
		default:
		}
		return new Operator(left, getName(), right, p);
	}

	private final EvaluatableExpression<Params> left;
	private final EvaluatableExpression<Params> right;

}
