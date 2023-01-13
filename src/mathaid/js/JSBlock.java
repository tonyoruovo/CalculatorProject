/**
 * 
 */
package mathaid.js;

import java.util.List;

/*
 * Date: 20 Aug 2022----------------------------------------------------------- 
 * Time created: 07:13:36---------------------------------------------------  
 * Package: js------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: JSBlock.java------------------------------------------------------ 
 * Class name: JSBlock------------------------------------------------ 
 */
/**
 * Represents an assignable block of Javascript code i.e a code within a
 * context.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface JSBlock extends JSSnippet {

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 10:43:57--------------------------------------------
	 */
	/**
	 * Returns all no-name functions within this context
	 * 
	 * @return a list of all the anonymous functions within this block of code.
	 */
	List<JSFunction> getAnonymousFunctions();
}
