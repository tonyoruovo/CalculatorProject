/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.scientific;

import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.typeset.Digits;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;

/*
 * Date: 11 Sep 2022----------------------------------------------------------- 
 * Time created: 17:06:32---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression.scientific------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Prefix.java------------------------------------------------------ 
 * Class name: Prefix------------------------------------------------ 
 */
/**
 * An expression that represents unary arithmetic operator that are on the left side of an operand such as {@code +}, {@code -}.
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Prefix extends Name {

	/*
	 * Date: 11 Sep 2022-----------------------------------------------------------
	 * Time created: 17:06:36---------------------------------------------------
	 */
	/**
	 * Constructs a {@code Prefix} operator by specifying the operand, symbol, evaluation and format options.
	 * 
	 * @param name the symbol of the operator
	 * @param right the sole operand of this operator.
	 * @param params the {@code ExpressionParams} representing options for the evaluation and format within this expression.
	 */
	public Prefix(String name, EvaluatableExpression<Params> right, Params params) {
		super(name, params);
		this.right = right;
	}

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:13:06 ---------------------------------------------------
	 */
	/**
	 * Gets the sole operand of this prefix operator.
	 * @return the right operand.
	 */
	public EvaluatableExpression<Params> getRight() {
		return right;
	}
	
	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:13:55 ---------------------------------------------------
	 */
	/**
	 * Checks if {@code this} is the negation operand {@code -}
	 * @return {@code this == '-'}.
	 */
	private boolean isNegation() {
		return getName().equals("-");
	}
	
	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:16:09 ---------------------------------------------------
	 */
	/**
	 * Checks if {@code this} is the plus operand {@code +}
	 * @return {@code this == '+'}.
	 */
	private boolean isPlus() {
		return getName().equals("+");
	}

	/*
	 * Most Recent Date: 11 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:13:23--------------------------------------
	 */
	/**
	 * Appends the {@code LinkedSegment} representation of this operator and it's operand into the format builder.
	 * <p>
	 * The format is ordered so: symbol, operand.
	 * <p>
	 * This has no side-effects.
	 * 
	 * @param formatBuilder {@inheritDoc}
	 */
	@Override
	public void format(SegmentBuilder formatBuilder) {
		// exponential function
		if (getName().length() == 1 && getName().equals("E")) {
			SegmentBuilder sb = new SegmentBuilder();
			right.format(sb);
			Segments.pow(Segments.constant("e", "e"), sb.toSegment());
			return;
		} else if (getName().equals(".")) {
			formatBuilder.append(Digits.integer('0', EvaluatableExpression.fromParams(getParams(), getParams().getNumOfRepeats())));
			formatBuilder.append(Digits.point(getParams().getDecimalPoint().charAt(0)));
			right.format(formatBuilder);
//			formatBuilder.append(Segments.toSegment(right.getName(), getParams().getDecimalPoint(),
//					getParams().getRecurringType(), 10, getParams().getIntSeparator(), getParams().getMantSeparator(),
//					getParams().getIntGroupSize(), getParams().getMantGroupSize(), getParams().getNumOfRepeats()));
			return;
		}
		formatBuilder.append(isNegation() ? Digits.prefixMinus() : isPlus() ? Digits.prefixPlus() : Segments.operator(getName(), getName()));
		right.format(formatBuilder);
	}

	/*
	 * Most Recent Date: 11 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:13:23--------------------------------------
	 */
	/**
	 * Evaluates this unary operator and returns the result.
	 * <p>
	 * The operand will have it's {@link EvaluatableExpression#evaluate() evaluate()} method called before the final evaluation
	 * is done and all computation will use the values from {@link #getParams()}.
	 * <p>
	 * All calculations are done with a numerical precision of <code>{@linkplain Params#getScale() scale} + 5</code> and may throw
	 * exceptions that indicate that the value were out of range.
	 * <p>
	 * This has no side-effects.
	 * 
	 * @return a new {@code EvaluatableExpression} that is the result of evaluating this operator. May return the same object
	 *         especially if called more than once.
	 */
	@Override
	public EvaluatableExpression<Params> evaluate() {
		EvaluatableExpression<Params> right = this.right.evaluate();
//		switch (getName()) {
//		case "-":
//		case ".":
//		case "+":// +2.5, +a, +(x-y)
//		case "E":// E^2
//		default:
//			return new Name(name() + right.name()).evaluate();
//		}
		return new Prefix(getName(), right, getParams());
	}

	/**
	 * The operand.
	 */
	private final EvaluatableExpression<Params> right;
}
