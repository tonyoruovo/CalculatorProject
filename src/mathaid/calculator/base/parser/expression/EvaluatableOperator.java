/**
 * 
 */
package mathaid.calculator.base.parser.expression;

import mathaid.NullException;

/*
 * Date: 20 Jun 2021----------------------------------------------------------- 
 * Time created: 15:57:26---------------------------------------------------  
 * Package: mathaid.calculator.base.parser.expression------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: EvaluatableOperator.java------------------------------------------------------ 
 * Class name: EvaluatableOperator------------------------------------------------ 
 */
/**
 * An {@link EvaluatableName} evaluates expression such as {@code 2 + 3}.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public abstract class EvaluatableOperator extends EvaluatableName {

	/*
	 * Date: 20 Jun 2021-----------------------------------------------------------
	 * Time created: 15:57:26---------------------------------------------------
	 */
	/**
	 * Constructs an {@code EvaluatableExpression} using a left operand and right
	 * operand with the name or symbol specified by the string argument. The left
	 * operand may be null in which case {@code this} is considered a postfix
	 * operator else if the right operand is null then this is considered a prefix
	 * operand, if both is null then a {@code NullPointerException} will be thrown.
	 * 
	 * @param leftOperand  the left operand of this operator.
	 * @param name         the name (sign) of this operator.
	 * @param rightOperand the right operand of this operator.
	 * @throws RuntimeException specifically a {@code NullPointerException} if the
	 *                          string argument is null or if the left and right
	 *                          operand is null.
	 */
	public EvaluatableOperator(EvaluatableExpression leftOperand, String name, EvaluatableExpression rightOperand) {
		super(name);
		if (leftOperand == null && rightOperand == null)
			new NullException(EvaluatableExpression.class);
		this.left = leftOperand;
		this.right = rightOperand;
	}

	/*
	 * Date: 29 Jul 2021----------------------------------------------------------- 
	 * Time created: 19:40:30--------------------------------------------
	 */
	/**
	 * Returns the left operand of this operator.
	 * @return the left operand
	 */
	public EvaluatableExpression getLeftOperand() {
		return left;
	}

	/*
	 * Date: 29 Jul 2021----------------------------------------------------------- 
	 * Time created: 19:40:30--------------------------------------------
	 */
	/**
	 * Returns the right operand of this operator.
	 * @return the right operand
	 */
	public EvaluatableExpression getRightOperand() {
		return right;
	}

	/**
	 * The left operand
	 */
	private final EvaluatableExpression left;
	/**
	 * The right operand
	 */
	private final EvaluatableExpression right;

}
