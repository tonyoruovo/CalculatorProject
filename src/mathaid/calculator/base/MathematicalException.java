/**
 * 
 */
package mathaid.calculator.base;

import mathaid.BaseException;
import mathaid.LocalizableMessage;

/*
 * Date: 19 Jul 2021----------------------------------------------------------- 
 * Time created: 21:25:14---------------------------------------------------  
 * Package: mathaid.calculator.base------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: MathematicalException.java------------------------------------------------------ 
 * Class name: MathematicalException------------------------------------------------ 
 */
/**
 * <p>
 * Exception wrapper that throws {@code ArithmeticException} to indicate that a
 * calculation inside a constructor or method has failed. For example, using the
 * value zero as an argument for the denominator in a {@code BigFraction}
 * object.
 * </p>
 * <p>
 * This is a mathaid fabricated unchecked exception
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class MathematicalException extends BaseException {

	/*
	 * Date: 19 Jul 2021-----------------------------------------------------------
	 * Time created: 21:25:14---------------------------------------------------
	 */
	/**
	 * Throws the given {@link ArithmeticException} object, after
	 * writing it to the mathaid error file.
	 * 
	 * @param msg a message that is locale specific and may be retrieved
	 *                       by the programmer for the purpose of displaying it to
	 *                       the user of their software
	 * @param cause          an {@code ArithmeticException}, the
	 *                       purpose of which this constructor is called. A
	 *                       no-argument constructor is advised in this situation,
	 *                       because the {@code LocalizableMessage} argument depends
	 *                       on the input error having no message.
	 * @param args           varargs for the {@code LocalizableMessage} to consume.
	 *                       {@code LocalizableMessage} may contain arguments as
	 *                       specified in {@link java.text.MessageFormat}, these
	 *                       arguments are parsed along with the
	 *                       {@code LocalizableMessage} to form a human-readable
	 *                       string.
	 * @throws RuntimeException     specifically an
	 *                              {@code ArithmeticException} after
	 *                              writing the relevant information to a file
	 * @throws NullPointerException if the {@code LocalizableMessage} is
	 *                              {@code null}. This exception is may not written
	 *                              to the mathaid error file nor is it
	 *                              locale-sensitive
	 */
	public MathematicalException(LocalizableMessage msg, ArithmeticException cause, Object... args) {
		super(msg, cause, args);
	}

	/*
	 * Date: 19 Jul 2021-----------------------------------------------------------
	 * Time created: 21:25:14---------------------------------------------------
	 */
	/**
	 * Initialises a no-argument {@link ArithmeticException} constructor, writes
	 * the error to the mathaid error file and throws same error
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
	 *                              {@code ArithmeticException} after
	 *                              writing the relevant information to a file
	 * @throws NullPointerException if the {@code LocalizableMessage} is
	 *                              {@code null}. This exception may not written
	 *                              to the mathaid error file nor is it
	 *                              locale-sensitive
	 */
	public MathematicalException(LocalizableMessage msg, Object... args) {
		super(msg, new ArithmeticException(), args);
	}

}
