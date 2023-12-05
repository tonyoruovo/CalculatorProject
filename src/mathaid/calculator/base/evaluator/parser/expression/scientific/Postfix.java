/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.scientific;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.evaluator.parser.expression.scientific.Name.Params;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;
import mathaid.calculator.base.util.Arith;

/*
 * Date: 11 Sep 2022----------------------------------------------------------- 
 * Time created: 17:36:45---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression.scientific------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Postfix.java------------------------------------------------------ 
 * Class name: Postfix------------------------------------------------ 
 */
/**
 * An expression that represents unary arithmetic operator that are on the right side of an operand such as {@code !}.
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Postfix extends Name {

	/*
	 * Date: 11 Sep 2022-----------------------------------------------------------
	 * Time created: 17:37:12---------------------------------------------------
	 */
	/**
	 * Constructs a {@code Postfix} operator by specifying the operand, symbol, evaluation and format options.
	 * 
	 * @param left the sole operand of this operator.
	 * @param params the {@code ExpressionParams} representing options for the evaluation and format within this expression.
	 */
	public Postfix(EvaluatableExpression<Params> left, String name, Params params) {
		super(name, params);
		this.left = left;
	}

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:13:06 ---------------------------------------------------
	 */
	/**
	 * Gets the sole operand of this postfix operator.
	 * @return the left operand.
	 */
	public EvaluatableExpression<Params> getLeft() {
		return left;
	}

	/*
	 * Most Recent Date: 11 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:37:08--------------------------------------
	 */
	/**
	 * Appends the {@code LinkedSegment} representation of this operator and it's operand into the format builder.
	 * <p>
	 * The format is ordered so: operand, symbol.
	 * <p>
	 * This has no side-effects.
	 * 
	 * @param formatBuilder {@inheritDoc}
	 */
	@Override
	public void format(SegmentBuilder formatBuilder) {
		left.format(formatBuilder);
		formatBuilder.append(Segments.operator(getName(), getName()));
	}

	/*
	 * Most Recent Date: 11 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:37:08--------------------------------------
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
		EvaluatableExpression<Params> exp = left.evaluate();
		if (getName().equals("!"))
			if (isNumber(exp))
				return new Name(
						Arith.factorial(new BigDecimal(exp.getName()),
								new MathContext(getParams().getScale(), RoundingMode.HALF_EVEN)).toPlainString(),
						getParams());
		return new Postfix(exp, getName(), getParams());
	}

	/**
	 * The operand.
	 */
	private final EvaluatableExpression<Params> left;

}
