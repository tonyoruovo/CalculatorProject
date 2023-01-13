/**
 * 
 */
package mathaid.calculator.base.parser.expression;

/*
 * Date: 20 Jun 2021----------------------------------------------------------- 
 * Time created: 20:08:29---------------------------------------------------  
 * Package: mathaid.calculator.base.parser.expression------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: EvaluatablePostfix.java------------------------------------------------------ 
 * Class name: EvaluatablePostfix------------------------------------------------ 
 */
/**
 * An {@code OperatorExpression} to evaluate expressions such as x!, a++
 * <b>Note</b>: EvaluatableExpression.toString(StringBuilder) must be overridden
 * or the result may be undefined. Calling getRightOperand() on this class
 * will always return null.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public abstract class EvaluatablePostfix extends EvaluatableOperator {

	/*
	 * Date: 20 Jun 2021-----------------------------------------------------------
	 * Time created: 20:08:30---------------------------------------------------
	 */
	/**
	 * Instantiates an {@code EvaluatablePostfix} object from one operand and an
	 * operator symbol.
	 * 
	 * @param leftOperand the operand of this operator
	 * @param name        the symbol or name of this operator
	 */
	public EvaluatablePostfix(EvaluatableExpression leftOperand, String name) {
		super(leftOperand, name, null);
	}

	/*
	 * Most Recent Date: 29 Jul 2021-----------------------------------------------
	 * Most recent time created: 19:50:53--------------------------------------
	 */
	/**
	 * Final method that will always return null.
	 * 
	 * @return {@code null}
	 */
	@Override
	public final EvaluatableExpression getRightOperand() {
		return super.getRightOperand();
	}

}
