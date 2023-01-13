/**
 * 
 */
package mathaid.calculator;

/*
 * Date: 28 Aug 2022----------------------------------------------------------- 
 * Time created: 11:54:29---------------------------------------------------  
 * Package: calculator------------------------------------------------ 
 * Project: InputTestCode------------------------------------------------ 
 * File: FatalReadException.java------------------------------------------------------ 
 * Class name: FatalReadException------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class FatalReadException extends CalculatorException {

	/*
	 * Date: 28 Aug 2022----------------------------------------------------------- 
	 * Time created: 11:56:25--------------------------------------------------- 
	 */
	/**
	 * Field for
	 */
	private static final long serialVersionUID = 7522424420427861569L;

	/*
	 * Date: 28 Aug 2022----------------------------------------------------------- 
	 * Time created: 11:56:50--------------------------------------------------- 
	 */
	/**
	 */
	public FatalReadException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * Date: 28 Aug 2022----------------------------------------------------------- 
	 * Time created: 11:56:50--------------------------------------------------- 
	 */
	/**
	 * @param message
	 * @param cause
	 */
	public FatalReadException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Date: 28 Aug 2022----------------------------------------------------------- 
	 * Time created: 11:56:50--------------------------------------------------- 
	 */
	/**
	 * @param message
	 */
	public FatalReadException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Date: 28 Aug 2022----------------------------------------------------------- 
	 * Time created: 11:56:50--------------------------------------------------- 
	 */
	/**
	 * @param cause
	 */
	public FatalReadException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
