/**
 * 
 */
package mathaid.calculator.base.converter;

import mathaid.BaseException;
import mathaid.LocalizableMessage;

/*
 * Date: 15 Aug 2021----------------------------------------------------------- 
 * Time created: 19:37:11---------------------------------------------------  
 * Package: mathaid.calculator.base.converter------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: MimeTypeException.java------------------------------------------------------ 
 * Class name: MimeTypeException------------------------------------------------ 
 */
/**
 * <p>
 * Exception wrapper that throws {@code org.jsoup.UnsupportedMimeTypeException}
 * to indicate that the Mime type received from a website server is not
 * supported.
 * </p>
 * <p>
 * This is a mathaid fabricated checked exception.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class MimeTypeException extends BaseException {

	/*
	 * Date: 15 Aug 2021-----------------------------------------------------------
	 * Time created: 19:37:11---------------------------------------------------
	 */
	/**
	 * Throws the given {@code org.jsoup.UnsupportedMimeTypeException} object, after
	 * writing it to the mathaid error file.
	 * 
	 * @param localizableMsg a message that is locale specified and may be retrieved
	 *                       by the programmer for the purpose of displaying it to
	 *                       the user of their software
	 * @param cause          a {@code org.jsoup.UnsupportedMimeTypeException}, the
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
	 * @throws Exception            specifically a
	 *                              {@code org.jsoup.UnsupportedMimeTypeException}
	 *                              after writing the relevant information to a file
	 * @throws NullPointerException if the {@code LocalizableMessage} is
	 *                              {@code null}. This exception is may not written
	 *                              to the mathaid error file nor is it
	 *                              locale-sensitive
	 */
	public MimeTypeException(LocalizableMessage localizableMsg, org.jsoup.UnsupportedMimeTypeException cause,
			Object... args) throws Exception {
		super(localizableMsg, cause, args);
	}

}
