/**
 * 
 */
package mathaid.calculator.base.value;

import mathaid.LocalizableMessage;
import mathaid.calculator.base.MathematicalException;

/*
 * Date: 21 Jul 2021----------------------------------------------------------- 
 * Time created: 14:06:39---------------------------------------------------  
 * Package: mathaid.calculator.base.value------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: PrecisionException.java------------------------------------------------------ 
 * Class name: PrecisionException------------------------------------------------ 
 */
/**
 * A mathaid {@code MathematicalException} that deals with the {@code Precision} class.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class PrecisionException extends MathematicalException {

	/*
	 * Date: 21 Jul 2021----------------------------------------------------------- 
	 * Time created: 14:06:39--------------------------------------------------- 
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
	public PrecisionException(LocalizableMessage msg, Object... args) {
		super(msg, args);
	}

}
