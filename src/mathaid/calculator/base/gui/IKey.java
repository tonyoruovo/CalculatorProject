/**
 * 
 */
package mathaid.calculator.base.gui;

import java.util.Map;

/*
 * Date: 21 Sep 2022----------------------------------------------------------- 
 * Time created: 12:57:56---------------------------------------------------  
 * Package: mathaid.calculator.base.gui------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: IKey.java------------------------------------------------------ 
 * Class name: IKey------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface IKey<T extends GUIComponent<T>, F> extends GUIComponent<T> {

	void setActions(Map<Integer, KeyAction<T, F>> action);
	void setIcon(int modifier);
//	F getIcon(int modifier);
//	void executeAction(int modifier);
}
