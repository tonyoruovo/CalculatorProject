/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.Arrays;
import java.util.List;

import mathaid.calculator.FatalReadException;

/*
 * Date: 28 Aug 2022----------------------------------------------------------- 
 * Time created: 11:59:47---------------------------------------------------  
 * Package: calculator.typeset------------------------------------------------ 
 * Project: InputTestCode------------------------------------------------ 
 * File: FatalParseException.java------------------------------------------------------ 
 * Class name: FatalParseException------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class FatalParseException extends FatalReadException {

	/*
	 * Date: 28 Aug 2022----------------------------------------------------------- 
	 * Time created: 11:59:56--------------------------------------------------- 
	 */
	/**
	 * Field for
	 */
	private static final long serialVersionUID = 4734506950355097431L;

	/*
	 * Date: 28 Aug 2022----------------------------------------------------------- 
	 * Time created: 11:59:47--------------------------------------------------- 
	 */
	/**
	 */
	public FatalParseException() {
		this("", null, Arrays.asList());
	}

	/*
	 * Date: 28 Aug 2022----------------------------------------------------------- 
	 * Time created: 11:59:47--------------------------------------------------- 
	 */
	/**
	 * @param message
	 * @param cause
	 */
	public FatalParseException(String message, Throwable cause, List<Integer> position) {
		super(message, cause);
		this.pos = position;
	}
	
	public List<Integer> getPosition(){
		return pos;
	}
	
	private final List<Integer> pos;

}
