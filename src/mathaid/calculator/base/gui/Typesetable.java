/**
 * 
 */
package mathaid.calculator.base.gui;

/*
 * Date: 27 Aug 2022----------------------------------------------------------- 
 * Time created: 09:38:16---------------------------------------------------  
 * Package: typeset------------------------------------------------ 
 * Project: InputTestCode------------------------------------------------ 
 * File: Typesetable.java------------------------------------------------------ 
 * Class name: Typesetable------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface Typesetable<T> extends Appendable, CharSequence, GUIComponent<T> {

	boolean isDone();

	boolean isReady();
	
	void typeset();
}
