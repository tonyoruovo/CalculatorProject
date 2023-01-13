/**
 * 
 */
package mathaid;

/*
 * Date: 23 Jul 2021----------------------------------------------------------- 
 * Time created: 15:09:14---------------------------------------------------  
 * Package: mathaid------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: NullException.java------------------------------------------------------ 
 * Class name: NullException------------------------------------------------ 
 */
/**
 * <p>
 * Exception wrapper that throws {@code java.lang.NullPointerException} to
 * indicate that some method or constructor has received null as an argument.
 * </p>
 * <p>
 * This is a mathaid fabricated unchecked exception
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 *
 */
public class NullException extends BaseException {

	/*
	 * Date: 23 Jul 2021-----------------------------------------------------------
	 * Time created: 15:09:14---------------------------------------------------
	 */
	/**
	 * Throws the given {@link java.lang.NullPointerException} object, after writing
	 * it to the mathaid error file.
	 * 
	 * @param localizableMsg a message that is locale specified and may be retrieved
	 *                       by the programmer for the purpose of displaying it to
	 *                       the user of their software
	 * @param cause          an {@code java.lang.NullPointerException}, the purpose
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
	 * @throws RuntimeException     specifically a
	 *                              {@code java.lang.NullPointerException} after
	 *                              writing the relevant information to a file
	 * @throws NullPointerException if the {@code LocalizableMessage} is
	 *                              {@code null}. This exception may not written
	 *                              to the mathaid error file nor is it
	 *                              locale-sensitive
	 */
	public NullException(LocalizableMessage localizableMsg, NullPointerException cause, Object... args)
			throws RuntimeException {
		super(localizableMsg, cause, args);
	}

	/*
	 * Date: 23 Jul 2021-----------------------------------------------------------
	 * Time created: 15:09:14---------------------------------------------------
	 */
	/**
	 * Initialises a no-argument {@link java.lang.NullPointerException} constructor,
	 * write the error to the mathaid error file and throws same error
	 * 
	 * @param nullParameterclass the class of the null parameter, this argument is
	 *                           parsed along with an {@code ExceptionMessage} to
	 *                           form a human-readable string. Use the ".class"
	 *                           constant for this parameter.
	 * @throws RuntimeException     specifically a
	 *                              {@code java.lang.NullPointerException} after
	 *                              writing the relevant information to a file
	 * @throws NullPointerException if the {@code LocalizableMessage} is
	 *                              {@code null}. This exception may not written
	 *                              to the mathaid error file nor is it
	 *                              locale-sensitive
	 */
	public NullException(Class<?> nullParameterclass) throws RuntimeException {
		this(ExceptionMessage.OBJECT_IS_NULL, new NullPointerException(), nullParameterclass.getName());
	}

}
