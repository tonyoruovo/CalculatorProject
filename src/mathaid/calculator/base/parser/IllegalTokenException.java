/**
 * 
 */
package mathaid.calculator.base.parser;

import mathaid.IllegalArgumentException;
import mathaid.LocalizableMessage;

/*
 * Date: 3 Aug 2021----------------------------------------------------------- 
 * Time created: 10:45:45---------------------------------------------------  
 * Package: mathaid.calculator.base.parser------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: IllegalTokenException.java------------------------------------------------------ 
 * Class name: IllegalTokenException------------------------------------------------ 
 */
/**
/**
 * <p>
 * Exception wrapper that throws {@code java.lang.IllegalArgumentException} to
 * indicate that a token provider for a {@code Parser} has encountered unregistered tokens.
 * </p>
 * <p>
 * This is a mathaid fabricated unchecked exception
 * </p>
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com 
 *
 */
public class IllegalTokenException extends IllegalArgumentException {

	/*
	 * Date: 3 Aug 2021----------------------------------------------------------- 
	 * Time created: 10:45:45--------------------------------------------------- 
	 */
	/**
	 * Initialises a no-argument {@link java.lang.IllegalArgumentException} constructor, write
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
	 *                              {@code java.lang.IllegalArgumentException} after
	 *                              writing the relevant information to a file
	 * @throws NullPointerException if the {@code LocalizableMessage} is
	 *                              {@code null}. This exception may not written
	 *                              to the mathaid error file nor is it
	 *                              locale-sensitive
	 */
	public IllegalTokenException(LocalizableMessage msg, Object... args) throws RuntimeException{
		super(msg, args);
	}

	/*
	 * Date: 3 Aug 2021----------------------------------------------------------- 
	 * Time created: 10:45:45--------------------------------------------------- 
	 */
	/**
	 * Throws the given {@link java.lang.IllegalArgumentException} object, after
	 * writing it to the mathaid error file.
	 * 
	 * @param localizableMsg a message that is locale specified and may be retrieved
	 *                       by the programmer for the purpose of displaying it to
	 *                       the user of their software
	 * @param cause          an {@code java.lang.IllegalArgumentException}, the
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
	 *                              {@code java.lang.IllegalArgumentException} after
	 *                              writing the relevant information to a file
	 * @throws NullPointerException if the {@code LocalizableMessage} is
	 *                              {@code null}. This exception is may not written
	 *                              to the mathaid error file nor is it
	 *                              locale-sensitive
	 */
	public IllegalTokenException(LocalizableMessage localizableMsg, java.lang.IllegalArgumentException cause,
			Object... args) throws RuntimeException {
		super(localizableMsg, cause, args);
	}

}
