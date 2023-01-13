/**
 * 
 */
package mathaid.calculator.base.parser.expression;

import mathaid.calculator.base.parser.Evaluator;

/*
 * Date: 19 May 2021----------------------------------------------------------- 
 * Time created: 10:57:25---------------------------------------------------  
 * Package: mathaid.calculator.base.parser.expression------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: EvaluatableExpression.java------------------------------------------------------ 
 * Class name: EvaluatableExpression------------------------------------------------ 
 */
/**
 * An <code>Expression</code> that can be evaluated.
 * <p>
 * Such an expression is expected to have a name (hence a sub-interface of
 * <code>ExpressionName</code>) and will be function because it will always
 * return as an <code>EvaluatableExpression</code>
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public interface EvaluatableExpression extends NameExpression, Evaluator<EvaluatableExpression> {

	/*
	 * Most Recent Date: 31 Jul 2021-----------------------------------------------
	 * Most recent time created: 10:13:39--------------------------------------
	 */
	/**
	 * Evaluates {@code this} using information from this object and returns an
	 * {@code EvaluatableExpression} that may or may not be modified.
	 * <p>
	 * Expressions such as {@code 6.33} when parsed and evaluated will return the
	 * same expression relating to {@code 6.33}, but expressions such as
	 * <code>5+a*a</code> when parsed and evaluated may return an expression like
	 * {@code 5+a^2}, which in this case, is different from the original.
	 * </p>
	 * 
	 * @return a valid {@code EvaluatableExpression} object after evaluation.
	 */
	@Override
	public EvaluatableExpression evaluate();
}
