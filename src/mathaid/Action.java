/**
 * 
 */
package mathaid;

/*
 * Date: 27 Jul 2022----------------------------------------------------------- 
 * Time created: 20:41:33---------------------------------------------------  
 * Package: mathaid.calculator.base.format.tex.input------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Action.java------------------------------------------------------ 
 * Class name: Action------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface Action<T, R> {
	R call(T argument);
}
