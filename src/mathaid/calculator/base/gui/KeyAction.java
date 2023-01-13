/**
 * 
 */
package mathaid.calculator.base.gui;

import mathaid.calculator.CalculatorManager;

/*
 * Date: 18 Sep 2022----------------------------------------------------------- 
 * Time created: 21:00:40---------------------------------------------------  
 * Package: mathaid.calculator.base.gui------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: KeyAction.java------------------------------------------------------ 
 * Class name: KeyAction------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface KeyAction<T extends GUIComponent<T>, F> {

	F getFormat();
	
	boolean apply(CalculatorManager<T, F> c);
}
