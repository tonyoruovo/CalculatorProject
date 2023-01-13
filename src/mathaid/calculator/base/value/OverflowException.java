/**
 * 
 */
package mathaid.calculator.base.value;

import mathaid.LocalizableMessage;
import mathaid.calculator.base.MathematicalException;

/*
 * Date: 20 Jul 2021----------------------------------------------------------- 
 * Time created: 15:57:16---------------------------------------------------  
 * Package: mathaid.calculator.base.value------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: OverflowException.java------------------------------------------------------ 
 * Class name: OverflowException------------------------------------------------ 
 */
/**
 * A mathaid {@code MathematicalException} that deals with overflows and
 * underflows within computer integers and floating point
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class OverflowException extends MathematicalException {

	/*
	 * Date: 20 Jul 2021-----------------------------------------------------------
	 * Time created: 15:57:16---------------------------------------------------
	 */
	/**
	 * Initialises a no-argument {@link java.lang.ArithmeticException} constructor,
	 * write the error to the mathaid error file and throws same error.
	 * 
	 * 
	 * @param msg a message that is locale specified and may be retrieved by the
	 *            programmer for the purpose of displaying it to the user of their
	 *            software.
	 * @throws RuntimeException     specifically an
	 *                              {@code java.lang.ArithmeticException} after
	 *                              writing the relevant information to a file
	 * @throws NullPointerException if the {@code LocalizableMessage} is
	 *                              {@code null}. This exception may not written to
	 *                              the mathaid error file nor is it
	 *                              locale-sensitive
	 */
	public OverflowException(LocalizableMessage msg) {
		super(msg);
	}

	/*
	 * Date: 20 Jul 2021-----------------------------------------------------------
	 * Time created: 15:57:16---------------------------------------------------
	 */
	/**
	 * Initialises a no-argument {@link java.lang.ArithmeticException} constructor,
	 * write the error to the mathaid error file and throws same error
	 * 
	 * 
	 * @param msg  a message that is locale specified and may be retrieved by the
	 *             programmer for the purpose of displaying it to the user of their
	 *             software
	 * @param args varargs for the {@code LocalizableMessage} to consume.
	 *             {@code LocalizableMessage} may contain arguments as specified in
	 *             {@link java.text.MessageFormat}, these arguments are parsed along
	 *             with the {@code LocalizableMessage} to form a human-readable
	 *             string.
	 * @throws RuntimeException     specifically an
	 *                              {@code java.lang.ArithmeticException} after
	 *                              writing the relevant information to a file
	 * @throws NullPointerException if the {@code LocalizableMessage} is
	 *                              {@code null}. This exception may not written to
	 *                              the mathaid error file nor is it
	 *                              locale-sensitive
	 */
	public OverflowException(LocalizableMessage msg, Object... args) {
		super(msg, new ArithmeticException(), args);
	}

}
