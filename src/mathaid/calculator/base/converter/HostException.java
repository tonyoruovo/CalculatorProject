/**
 * 
 */
package mathaid.calculator.base.converter;

import java.net.UnknownHostException;

import mathaid.BaseException;
import mathaid.LocalizableMessage;

/*
 * Date: 15 Aug 2021----------------------------------------------------------- 
 * Time created: 20:53:16---------------------------------------------------  
 * Package: mathaid.calculator.base.converter------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: HostException.java------------------------------------------------------ 
 * Class name: HostException------------------------------------------------ 
 */
/**
 * <p>
 * Exception wrapper that throws {@link java.net.UnknownHostException} to
 * indicate that the Ip address of a given host could not be determined a.k.a no
 * internet connection was found.
 * </p>
 * <p>
 * This is a mathaid fabricated checked exception.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class HostException extends BaseException {

	/*
	 * Date: 15 Aug 2021-----------------------------------------------------------
	 * Time created: 20:53:16---------------------------------------------------
	 */
	/**
	 * Throws the given {@link java.net.UnknownHostException} object, after
	 * writing it to the mathaid error file.
	 * 
	 * @param localizableMsg a message that is locale specified and may be retrieved
	 *                       by the programmer for the purpose of displaying it to
	 *                       the user of their software
	 * @param cause          a {@link java.net.UnknownHostException}, the purpose
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
	 *                              {@link java.net.UnknownHostException} after
	 *                              writing the relevant information to a file
	 * @throws NullPointerException if the {@code LocalizableMessage} is
	 *                              {@code null}. This exception is may not written
	 *                              to the mathaid error file nor is it
	 *                              locale-sensitive
	 */
	public HostException(LocalizableMessage localizableMsg, UnknownHostException cause, Object... args)
			throws Exception {
		super(localizableMsg, cause, args);
	}

}
