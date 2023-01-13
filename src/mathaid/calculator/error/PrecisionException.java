/**
 * 
 */
package mathaid.calculator.error;

import mathaid.calculator.base.value.Precision;

/*
 * Date: 6 May 2020----------------------------------------------------------- 
 * Time created: 21:20:14---------------------------------------------------  
 * Package: mathaid.calculator.error------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: PrecisionException.java------------------------------------------------------ 
 * Class name: PrecisionException------------------------------------------------ 
 */
/**
 * An exception to be thrown for overflows and underflows within a
 * {@code Precision}.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class PrecisionException extends ValueException {
	private static final long serialVersionUID = 1L;
	private Precision p;

	/*
	 * Date: 6 May 2020-----------------------------------------------------------
	 * Time created: 21:23:10---------------------------------------------------
	 */
	/**
	 * @param p
	 */
	public PrecisionException(Precision p) {
		this.p = p;
	}

	/*
	 * Date: 7 May 2020-----------------------------------------------------------
	 * Time created: 18:25:48---------------------------------------------------
	 */
	/**
	 * @param msg
	 */
	public PrecisionException(String msg) {
		super(msg);
	}

	@Override
	public String getMessage() {
		if (super.getMessage() != null)
			return super.getMessage();
		return "Precision." + p + " is not allowed for this values";
	}
}
