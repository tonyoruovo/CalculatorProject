/**
 * 
 */
package mathaid.calculator.base.parser.expression;

/*
 * Date: 20 Jun 2021----------------------------------------------------------- 
 * Time created: 20:00:47---------------------------------------------------  
 * Package: mathaid.calculator.base------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: EvaluatablePrefix.java------------------------------------------------------ 
 * Class name: EvaluatablePrefix------------------------------------------------ 
 */
/**
 * An {@code OperatorExpression} to evaluate expressions such as +x, -b, ++a
 * <b>Note</b>: EvaluatableExpression.toString(StringBuilder) must be overridden
 * or the result may be undefined. Calling getLeftOperand() on this class
 * will always return null.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public abstract class EvaluatablePrefix extends EvaluatableOperator {

	/*
	 * Date: 20 Jun 2021----------------------------------------------------------- 
	 * Time created: 20:00:47--------------------------------------------------- 
	 */
	/**
	 * Instantiates an {@code EvaluatablePrefix} object from one operand and an
	 * operator symbol.
	 * 
	 * @param rightOperand the operand of this operator
	 * @param name        the symbol or name of this operator
	 */
	public EvaluatablePrefix(String name, EvaluatableExpression rightOperand) {
		super(null, name, rightOperand);
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
	public final EvaluatableExpression getLeftOperand() {
		return super.getLeftOperand();
	}

}
