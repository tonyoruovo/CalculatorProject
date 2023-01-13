/**
 * 
 */
package mathaid;

/*
 * Date: 21 Jul 2021----------------------------------------------------------- 
 * Time created: 14:28:17---------------------------------------------------  
 * Package: mathaid------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: ObjectInstantiationError.java------------------------------------------------------ 
 * Class name: ObjectInstantiationError------------------------------------------------ 
 */
/**
 * <p>
 * Exception wrapper that throws {@code java.lang.InstantiationError} to
 * indicate that the given constructor cannot be used to create the object.
 * </p>
 * <p>
 * This is a mathaid fabricated error
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class ObjectInstantiationError extends BaseException {

	/*
	 * Date: 21 Jul 2021-----------------------------------------------------------
	 * Time created: 14:28:17---------------------------------------------------
	 */
	/**
	 * Initialises a no-argument {@link InstantiationError} constructor, write the
	 * error to the mathaid error file and throws same error
	 * 
	 * 
	 * @param localizableMsg a message that is locale specified and may be retrieved
	 *                       by the programmer for the purpose of displaying it to
	 *                       the user of their software
	 * @param args           varargs for the {@code LocalizableMessage} to consume.
	 *                       {@code LocalizableMessage} may contain arguments as
	 *                       specified in {@link java.text.MessageFormat}, these
	 *                       arguments are parsed along with the
	 *                       {@code LocalizableMessage} to form a human-readable
	 *                       string.
	 * @throws Error                specifically an {@code InstantiationError} after
	 *                              writing the relevant information to a file
	 * @throws NullPointerException if the {@code LocalizableMessage} is
	 *                              {@code null}. This exception may not written
	 *                              to the mathaid error file nor is it
	 *                              locale-sensitive
	 */
	public ObjectInstantiationError(LocalizableMessage localizableMsg, Object... args) throws Error {
		super(localizableMsg, new InstantiationError(), args);
	}

}
