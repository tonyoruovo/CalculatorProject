/**
 * 
 */
package mathaid.calculator.base.value;

import java.math.BigDecimal;

import mathaid.ExceptionMessage;

/*
 * Date: 20 Jul 2021----------------------------------------------------------- 
 * Time created: 15:59:43---------------------------------------------------  
 * Package: mathaid.calculator.base.value------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: PrecisionOverflowException.java------------------------------------------------------ 
 * Class name: PrecisionOverflowException------------------------------------------------ 
 */
/**
 * A mathaid {@code MathematicalException} that deals with overflows and
 * underflows within the {@code Precision} class.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class PrecisionOverflowException extends OverflowException {

	/*
	 * Date: 20 Jul 2021----------------------------------------------------------- 
	 * Time created: 15:59:47--------------------------------------------------- 
	 */
	/**
	 * Field for
	 */
//	private static final long serialVersionUID = 1L;

	/*
	 * Date: 20 Jul 2021----------------------------------------------------------- 
	 * Time created: 15:59:43--------------------------------------------------- 
	 */
	/**
	 * Initialises a no-argument {@link java.lang.ArithmeticException} constructor,
	 * write the error to the mathaid error file and throws same error. The message written
	 * is the same as {@link ExceptionMessage#OVERFLOW}.
	 * @param x the number that caused the overflow
	 * @param p the {@code Precision} object being used at the time of the overflow
	 * @throws RuntimeException     specifically an
	 *                              {@code java.lang.ArithmeticException} after
	 *                              writing the relevant information to a file
	 */
	public PrecisionOverflowException(BigDecimal x, Precision p) {
		super(ExceptionMessage.OVERFLOW, x.toPlainString(), "Precision" + p.toString());
	}

}
