/**
 * 
 */
package mathaid;

/*
 * Date: 3 Aug 2021----------------------------------------------------------- 
 * Time created: 03:52:50---------------------------------------------------  
 * Package: mathaid------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: IllegalStatusException.java------------------------------------------------------ 
 * Class name: IllegalStatusException------------------------------------------------ 
 */
/**
 * <p>
 * Exception wrapper that throws {@code java.lang.IllegalStateException} to
 * indicate that some method has received an unsupported or illegal value as an
 * argument.
 * </p>
 * <p>
 * This is a mathaid fabricated unchecked exception
 * </p>
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com 
 *
 */
public class IllegalStatusException extends BaseException {

	/*
	 * Date: 3 Aug 2021----------------------------------------------------------- 
	 * Time created: 03:53:38--------------------------------------------------- 
	 */
	/**
	 * Throws the given {@link java.lang.IllegalStateException} object, after
	 * writing it to the mathaid error file.
	 * 
	 * @param localizableMsg a message that is locale specified and may be retrieved
	 *                       by the programmer for the purpose of displaying it to
	 *                       the user of their software.
	 * @param cause          an {@code java.lang.IllegalStateException}, the
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
	 *                              {@code java.lang.IllegalStateException} after
	 *                              writing the relevant information to a file
	 * @throws NullPointerException if the {@code LocalizableMessage} is
	 *                              {@code null}. This exception is may not written
	 *                              to the mathaid error file nor is it
	 *                              locale-sensitive
	 */
	public IllegalStatusException(LocalizableMessage localizableMsg, IllegalStateException cause, Object... args)
			throws RuntimeException {
		super(localizableMsg, cause, args);
	}

	/*
	 * Date: 3 Aug 2021----------------------------------------------------------- 
	 * Time created: 03:53:38--------------------------------------------------- 
	 */
	/**
	 * Initialises a no-argument {@link java.lang.IllegalStateException} constructor, write
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
	 *                              {@code java.lang.IllegalStateException} after
	 *                              writing the relevant information to a file
	 * @throws NullPointerException if the {@code LocalizableMessage} is
	 *                              {@code null}. This exception may not written
	 *                              to the mathaid error file nor is it
	 *                              locale-sensitive
	 */
	public IllegalStatusException(LocalizableMessage msg, Object... args)
			throws RuntimeException {
		super(msg, new IllegalStateException(), args);
	}
}
