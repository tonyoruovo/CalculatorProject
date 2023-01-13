/**
 * 
 */
package mathaid.calculator.base.gui.mathjax;

import mathaid.js.JSObject;

/*
 * Date: 23 Aug 2022----------------------------------------------------------- 
 * Time created: 23:34:57---------------------------------------------------  
 * Package: js.mathjax.options------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: InputProcessorBuilder.java------------------------------------------------------ 
 * Class name: InputProcessorBuilder------------------------------------------------ 
 */
/**
 * A builder that configures and builds an object that can be used in the input
 * processor options. It contains a method to configure shared configuration for
 * all input processors. This creates a shared interface between all input
 * processor builders such as {@link TexInputProcessorBuilder},
 * {@link MmlInputProcessorBuilder} and AsciiMathInputProcessorBuilder.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public abstract class InputProcessorBuilder extends AbstractOptionsBuilder {

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 08:23:47--------------------------------------------
	 */
	/**
	 * A shared method to be used by builders of input processors. This is a
	 * developer option.
	 * 
	 * @param inputObject an instance of the subclass of the main input processor.
	 * @return {@code this} for further method chaining
	 * @see TexInputProcessorBuilder#setFind(JSObject)
	 * @see MmlInputProcessorBuilder#setFind(JSObject)
	 * @see AsciiMathInputProcessorBuilder#setFind(JSObject)
	 */
	public abstract InputProcessorBuilder setFind(JSObject inputObject);

}
