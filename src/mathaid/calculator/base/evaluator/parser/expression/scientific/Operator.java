/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.scientific;

import static java.util.Arrays.asList;
import static mathaid.calculator.base.typeset.Segments.fraction;
import static mathaid.calculator.base.typeset.Segments.logxy;
import static mathaid.calculator.base.typeset.Segments.operator;
import static mathaid.calculator.base.typeset.Segments.pow;
import static mathaid.calculator.base.typeset.Segments.root;
import static mathaid.calculator.base.typeset.Segments.sqrt;
import static mathaid.calculator.base.util.Arith.pow;
import static mathaid.calculator.base.util.Utility.d;
import static mathaid.calculator.base.util.Utility.mc;
import static mathaid.calculator.base.util.Utility.rm;

import java.math.BigDecimal;

import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.typeset.LinkedSegment;
import mathaid.calculator.base.typeset.Segment;
import mathaid.calculator.base.typeset.SegmentBuilder;
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
 * An expression that represents binary arithmetic operators such as {@code +}, {@code -}, {@code /}, {@code *}.
 * <p>
 * It consists of an identifier specified by {@link #getName()} and it's ordered operands.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Operator extends Name {

	/*
	 * Date: 11 Sep 2022-----------------------------------------------------------
	 * Time created: 01:59:48---------------------------------------------------
	 */
	/**
	 * Constructs an {@code Operator} from the arguments.
	 * 
	 * @param left   the left operand expression
	 * @param op     the symbol/identifier of this {@code Operator}.
	 * @param right  the right operand expression.
	 * @param params the {@code ExpressionParams} representing options for the evaluation and format within this expression.
	 */
	public Operator(EvaluatableExpression<Params> left, String op, EvaluatableExpression<Params> right, Params params) {
		super(op, params);
		this.left = left;
		this.right = right;
	}

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:21:05 ---------------------------------------------------
	 */
	/**
	 * Gets the left operand.
	 * 
	 * @return the left operand.
	 */
	public EvaluatableExpression<Params> getLeft() {
		return left;
	}

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:22:58 ---------------------------------------------------
	 */
	/**
	 * Gets the right operand.
	 * 
	 * @return the right operand.
	 */
	public EvaluatableExpression<Params> getRight() {
		return right;
	}

	/*
	 * Most Recent Date: 11 Sep 2022-----------------------------------------------
	 * Most recent time created: 02:08:54--------------------------------------
	 */
	/**
	 * Appends the {@code LinkedSegment} representation of this operator and it's operands into the format builder.
	 * <p>
	 * The format is ordered so: left-operand, symbol, right-operand.
	 * <p>
	 * This has no side-effects.
	 * 
	 * @param formatBuilder {@inheritDoc}
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
						formatBuilder.append(logxy(arg1, arg2));
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
				formatBuilder.append(fraction(arg1, arg2));
				return;
			}
			left.format(formatBuilder);
			String[] div = p.getDivisionString();
			formatBuilder.append(operator(div[1], div[0]));
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
			formatBuilder.append(operator(mul[1], mul[0]));
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
						BigDecimal num = d(div.right.getName());
						BigDecimal num2;
						try {
							num2 = d(div.left.getName());
						} catch (ClassCastException | NumberFormatException e) {
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
								radicand = pow(base, exponent);
							if (num.compareTo(BigDecimal.valueOf(2L)) == 0) {
								formatBuilder.append(sqrt(radicand));
								return;
							}
							div.right.format(sb.deleteAll());
							LinkedSegment index = sb.toSegment();
							formatBuilder.append(root(index, radicand));
//							formatBuilder.append(sq)
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
			formatBuilder.append(pow(base, exponent));
			return;
		}
		default:
		}
		left.format(formatBuilder);
		formatBuilder.append(operator(getName(), getName()));
		right.format(formatBuilder);

	}
	
	public String toString() {
		return new StringBuilder(left.toString()).append(getName()).append(right.toString()).toString();
	}

	/*
	 * Most Recent Date: 11 Sep 2022-----------------------------------------------
	 * Most recent time created: 02:08:54--------------------------------------
	 */
	/**
	 * Computes the result of applying both operand to this operator.
	 * <p>
	 * Both operands will have their {@link EvaluatableExpression#evaluate() evaluate()} methods called before the final evaluation
	 * is done and all computation will use the values from {@link #getParams()}.
	 * <p>
	 * All calculations are done with a numerical precision of <code>{@linkplain Params#getScale() scale} + 5</code> and may throw
	 * exceptions that indicate that value(s) were out of range.
	 * <p>
	 * This has no side-effects.
	 * 
	 * @return a new {@code EvaluatableExpression} that is the result of evaluating this operator. May return the same object
	 *         especially if called more than once.
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
					return new Name(d(left.getName())
							.add(d(right.getName()), mc(p.getScale(), rm("HALF_EVEN")))
							.toPlainString(), p);
			break;
		case "-":
			if (isNumber(left))
				if (isNumber(right))
					return new Name(d(left.getName()).subtract(d(right.getName()),
							mc(p.getScale(), rm("HALF_EVEN"))).toPlainString(), p);
			break;
		case "/":
			if (isNumber(left))
				if (isNumber(right))
					return new Name(d(left.getName()).divide(d(right.getName()),
							mc(p.getScale(), rm("HALF_EVEN"))).toPlainString(), p);
			// Log[5]/Log[2]
			if (left instanceof Function && right instanceof Function) {
				if (left.getName().equals(p.getLog()) && right.getName().equals(p.getLog())) {
					Function fx = (Function) left;
					Function fy = (Function) right;
					return new Function(new Name(p.getLog(), p), asList(fx, fy), p).evaluate();
				}
			}
			break;
		case "*":
			if (isNumber(left))
				if (isNumber(right))
					return new Name(d(left.getName()).multiply(d(right.getName()),
							mc(p.getScale(), rm("HALF_EVEN"))).toPlainString(), p);
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
//					return new Name(d(n1.toString(true)).toPlainString());
					return new Name(pow(d(left.getName()), d(right.getName()),
							mc(p.getScale(), rm("HALF_EVEN"))).toPlainString(), p);
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

	/**
	 * The left operand.
	 */
	private final EvaluatableExpression<Params> left;
	/**
	 * The right operand.
	 */
	private final EvaluatableExpression<Params> right;

}
