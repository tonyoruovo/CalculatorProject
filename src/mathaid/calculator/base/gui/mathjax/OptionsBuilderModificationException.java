/**
 * 
 */
package mathaid.calculator.base.gui.mathjax;

/*
 * Date: 21 Aug 2022----------------------------------------------------------- 
 * Time created: 03:16:32---------------------------------------------------  
 * Package: js.mathjax.options------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: OptionsBuilderModificationException.java------------------------------------------------------ 
 * Class name: OptionsBuilderModificationException------------------------------------------------ 
 */
/**
 * An exception thrown inside a addRemoveXXX method to prevent multiple fields
 * which would cause a javascript error or multiple literal of the equal value
 * which will cause MathJax script parsing error.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class OptionsBuilderModificationException extends RuntimeException {

	/*
	 * Date: 21 Aug 2022-----------------------------------------------------------
	 * Time created: 03:16:39---------------------------------------------------
	 */
	/**
	 * Field for
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Date: 21 Aug 2022-----------------------------------------------------------
	 * Time created: 03:16:32---------------------------------------------------
	 */
	/**
	 * A no argument constructor that has a message value of " was already added.
	 * Cannot add or remove now"
	 */
	public OptionsBuilderModificationException() {
		this("");
	}

	/*
	 * Date: 21 Aug 2022-----------------------------------------------------------
	 * Time created: 03:16:32---------------------------------------------------
	 */
	/**
	 * Constructor that favours auto generating an error message in the form:
	 * 
	 * <pre>
	 * <code>optionName + " was already added. Cannot add or remove now"</code>
	 * </pre>
	 * 
	 * @param optionName the name of the option that is illegally modifier by the
	 *                   builder object
	 */
	public OptionsBuilderModificationException(String optionName) {
		this(optionName, true);
	}

	/*
	 * Date: 21 Aug 2022-----------------------------------------------------------
	 * Time created: 03:16:32---------------------------------------------------
	 */
	/**
	 * Creates a {@code OptionsBuilderModificationException} with the name of the
	 * configuration field. This may auto generate a message in the form:
	 * 
	 * <pre>
	 * <code>optionName + " was already added. Cannot add or remove now"</code>
	 * </pre>
	 * 
	 * @param optionName      the name of the option that is illegally modifier by
	 *                        the builder object
	 * @param autoGenerateMsg true if an auto generated message is desired
	 */
	public OptionsBuilderModificationException(String optionName, boolean autoGenerateMsg) {
		super(autoGenerateMsg ? String.format("%s was already added. Cannot add or remove now", optionName)
				: optionName);
	}

}
