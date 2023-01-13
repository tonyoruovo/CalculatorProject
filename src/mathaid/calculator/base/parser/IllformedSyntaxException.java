/**
 * 
 */
package mathaid.calculator.base.parser;

import mathaid.IllegalArgumentException;
import mathaid.LocalizableMessage;

/*
 * Date: 1 Aug 2021----------------------------------------------------------- 
 * Time created: 15:51:18---------------------------------------------------  
 * Package: mathaid.calculator.base.parser------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: IllformedSyntaxException.java------------------------------------------------------ 
 * Class name: IllformedSyntaxException------------------------------------------------ 
 */
/**
 * <p>
 * Exception wrapper that throws {@link java.lang.IllegalArgumentException} to
 * indicate that some a {@link CommonSyntax.Builder} object has encountered an
 * illegal element that does not fit the {@code Syntax} currently being built.
 * </p>
 * <p>
 * This is a mathaid fabricated unchecked exception
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 *
 */
public class IllformedSyntaxException extends IllegalArgumentException {

	/*
	 * Date: 1 Aug 2021-----------------------------------------------------------
	 * Time created: 15:51:18---------------------------------------------------
	 */
	/**
	 * Initialises a no-argument {@link java.lang.IllegalArgumentException}
	 * constructor, write the error to the mathaid error file and throws same error.
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
	 *                              {@code null}. This exception may not written to
	 *                              the mathaid error file nor is it
	 *                              locale-sensitive
	 */
	public IllformedSyntaxException(LocalizableMessage msg, Object... args) {
		super(msg, args);
	}

}
