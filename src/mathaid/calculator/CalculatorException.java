/**
 * 
 */
package mathaid.calculator;

/*
 * Date: 28 Aug 2022----------------------------------------------------------- 
 * Time created: 11:52:43---------------------------------------------------  
 * Package: calculator------------------------------------------------ 
 * Project: InputTestCode------------------------------------------------ 
 * File: CalculatorException.java------------------------------------------------------ 
 * Class name: CalculatorException------------------------------------------------ 
 */
/**
 * An exception thrown before, during or after a calculation has/is been made. The message
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class CalculatorException extends RuntimeException {

	/*
	 * Date: 28 Aug 2022----------------------------------------------------------- 
	 * Time created: 11:52:47--------------------------------------------------- 
	 */
	/**
	 * Field for
	 */
	private static final long serialVersionUID = -4755879727090209849L;

	/*
	 * Date: 28 Aug 2022----------------------------------------------------------- 
	 * Time created: 11:52:43--------------------------------------------------- 
	 */
	/**
	 */
	public CalculatorException() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * Date: 28 Aug 2022----------------------------------------------------------- 
	 * Time created: 11:52:43--------------------------------------------------- 
	 */
	/**
	 * @param message
	 */
	public CalculatorException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Date: 28 Aug 2022----------------------------------------------------------- 
	 * Time created: 11:52:43--------------------------------------------------- 
	 */
	/**
	 * @param cause
	 */
	public CalculatorException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Date: 28 Aug 2022----------------------------------------------------------- 
	 * Time created: 11:52:43--------------------------------------------------- 
	 */
	/**
	 * @param message
	 * @param cause
	 */
	public CalculatorException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
