/**
 * 
 */
package mathaid;

/*
 * Date: 21 Jul 2021----------------------------------------------------------- 
 * Time created: 12:15:53---------------------------------------------------  
 * Package: mathaid------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: IllegalArgumentException.java------------------------------------------------------ 
 * Class name: IllegalArgumentException------------------------------------------------ 
 */
/**
 * <p>
 * Exception wrapper that throws {@code java.lang.IllegalArgumentException} to
 * indicate that some method has received an unsupported or illegal value as an
 * argument.
 * </p>
 * <p>
 * This is a mathaid fabricated unchecked exception
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class IllegalArgumentException extends BaseException {

	/*
	 * Date: 21 Jul 2021-----------------------------------------------------------
	 * Time created: 12:15:54---------------------------------------------------
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
	public IllegalArgumentException(LocalizableMessage msg, Object... args) throws RuntimeException {
		this(msg, new java.lang.IllegalArgumentException(), args);
	}

	/*
	 * Date: 21 Jul 2021-----------------------------------------------------------
	 * Time created: 12:15:54---------------------------------------------------
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
	public IllegalArgumentException(LocalizableMessage localizableMsg, java.lang.IllegalArgumentException cause,
			Object... args) throws RuntimeException {
		super(localizableMsg, cause, args);
	}

}
