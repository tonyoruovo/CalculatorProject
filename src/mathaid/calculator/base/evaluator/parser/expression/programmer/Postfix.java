/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.programmer;

import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;

/*
 * Date: 18 Oct 2022----------------------------------------------------------- 
 * Time created: 08:15:59---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression.programmer------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Postfix.java------------------------------------------------------ 
 * Class name: Postfix------------------------------------------------ 
 */
/**
 * An expression that represents unary arithmetic operator that are on the right side of an operand.
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Postfix extends Name {
	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 09:15:35 ---------------------------------------------------
	 */
	/**
	 * Constructor for creating a {@code Postfix} expression.
	 * @param left the sole operand.
	 * @param name the symbol of this operator.
	 * @param params the {@code ExpressionParams} representing options for the evaluation and format within this expression.
	 */
	public Postfix(PExpression left, String name, Params params) {
		super(name, params, String.class);
		this.left = left;
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 09:16:34 ---------------------------------------------------
	 */
	/**
	 * Gets the sole operand of this postfix operator.
	 * @return the sole operand.
	 */
	public PExpression getLeft() {
		return left;
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 09:17:18 ---------------------------------------------------
	 */
	/**
	 * Appends the {@code LinkedSegment} representation of this operator and it's operand into the format builder.
	 * <p>
	 * The format is ordered so: operand, symbol.
	 * <p>
	 * This has no side-effects.
	 * 
	 * @param f {@inheritDoc}
	 */
	@Override
	public void format(SegmentBuilder f) {
		left.format(f);
		f.append(Segments.operator(getName(), getName()));
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 09:17:50 ---------------------------------------------------
	 */
	/**
	 * Evaluates this unary operator and returns the result.
	 * <p>
	 * The operand will have it's {@link EvaluatableExpression#evaluate() evaluate()} method called before the final evaluation
	 * is done.
	 * <p>
	 * This has no side-effects.
	 * 
	 * @return a new {@code EvaluatableExpression} that is the result of evaluating this operator. May return the same object
	 *         especially if called more than once.
	 */
	@Override
	public Name evaluate() {
		return new Postfix(left.evaluate(), getName(), getParams());
	}

	/**
	 * Holds the sole operand.
	 */
	private final PExpression left;
}
