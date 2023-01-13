/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression;

/*
 * Date: 4 Sep 2022----------------------------------------------------------- 
 * Time created: 06:51:03---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Expression.java------------------------------------------------------ 
 * Class name: Expression------------------------------------------------ 
 */
/**
 * @param <B> An object from which a formatted expression is built.
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface Expression<B> {
	void format(B formatBuilder);
}
