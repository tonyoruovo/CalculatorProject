/**
 * 
 */
package mathaid.calculator.base.converter;

import mathaid.BaseException;
import mathaid.LocalizableMessage;

/*
 * Date: 15 Aug 2021----------------------------------------------------------- 
 * Time created: 19:18:16---------------------------------------------------  
 * Package: mathaid.calculator.base.converter------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: BadHttpRequestException.java------------------------------------------------------ 
 * Class name: BadHttpResponseException------------------------------------------------ 
 */
/**
 * <p>
 * Exception wrapper that throws {@code org.jsoup.HttpStatusException} to
 * indicate that the response received from the server was not a valid one.
 * </p>
 * <p>
 * This is a mathaid fabricated checked exception.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class BadHttpResponseException extends BaseException {

	/*
	 * Date: 15 Aug 2021-----------------------------------------------------------
	 * Time created: 19:18:16---------------------------------------------------
	 */
	/**
	 * Throws the given {@code org.jsoup.HttpStatusException} object, after writing
	 * it to the mathaid error file.
	 * 
	 * @param localizableMsg a message that is locale specified and may be retrieved
	 *                       by the programmer for the purpose of displaying it to
	 *                       the user of their software
	 * @param cause          a {@code org.jsoup.HttpStatusException}, the purpose of
	 *                       which this constructor is called. A no-argument
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
	 *                              {@code org.jsoup.HttpStatusException} after
	 *                              writing the relevant information to a file
	 * @throws NullPointerException if the {@code LocalizableMessage} is
	 *                              {@code null}. This exception is may not written
	 *                              to the mathaid error file nor is it
	 *                              locale-sensitive
	 */
	public BadHttpResponseException(LocalizableMessage localizableMsg, org.jsoup.HttpStatusException cause,
			Object... args) throws Exception {
		super(localizableMsg, cause, args);
	}

}
