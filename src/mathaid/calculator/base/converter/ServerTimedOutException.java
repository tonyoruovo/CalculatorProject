/**
 * 
 */
package mathaid.calculator.base.converter;

import mathaid.BaseException;
import mathaid.LocalizableMessage;

/*
 * Date: 15 Aug 2021----------------------------------------------------------- 
 * Time created: 19:58:16---------------------------------------------------  
 * Package: mathaid.calculator.base.converter------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: ServerTimedOutException.java------------------------------------------------------ 
 * Class name: ServerTimedOutException------------------------------------------------ 
 */
/**
 * <p>
 * Exception wrapper that throws {@link java.net.SocketTimeoutException} to
 * indicate that the deadline set to establish contact with a server has expired.
 * </p>
 * <p>
 * This is a mathaid fabricated checked exception.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class ServerTimedOutException extends BaseException {

	/*
	 * Date: 15 Aug 2021-----------------------------------------------------------
	 * Time created: 19:58:16---------------------------------------------------
	 */
	/**
	 * Throws the given {@link java.net.SocketTimeoutException} object, after
	 * writing it to the mathaid error file.
	 * 
	 * @param localizableMsg a message that is locale specified and may be retrieved
	 *                       by the programmer for the purpose of displaying it to
	 *                       the user of their software
	 * @param cause          a {@link java.net.SocketTimeoutException}, the purpose
	 *                       of which this constructor is called. A no-argument
	 *                       constructor is advised in this situation, because the
	 *                       {@code LocalizableMessage} argument depends on the
	 *                       input error having no message.
	 * @param args           varargs for the {@code LocalizableMessage} to consume.
	 *                       {@code LocalizableMessage} may contain arguments as
	 *                       specified in {@link java.text.MessageFormat}, these
	 *                       arguments are parsed along with the
	 *                       {@code LocalizableMessage} to form a human-readable
	 *                       string.
	 * @throws Exception            specifically a
	 *                              {@link java.net.SocketTimeoutException} after
	 *                              writing the relevant information to a file
	 * @throws NullPointerException if the {@code LocalizableMessage} is
	 *                              {@code null}. This exception is may not written
	 *                              to the mathaid error file nor is it
	 *                              locale-sensitive
	 */
	public ServerTimedOutException(LocalizableMessage localizableMsg, java.net.SocketTimeoutException cause,
			Object... args) throws Exception {
		super(localizableMsg, cause, args);
	}

}
