/**
 * 
 */
package mathaid;

/*
 * Date: 21 Jul 2021----------------------------------------------------------- 
 * Time created: 10:35:47---------------------------------------------------  
 * Package: mathaid------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: ObjectInstantiationException.java------------------------------------------------------ 
 * Class name: ObjectInstantiationException------------------------------------------------ 
 */
/**
 * <p>
 * Exception wrapper that throws {@code java.lang.InstantiationException} to
 * indicate that the given constructor cannot be used to create the object.
 * </p>
 * <p>
 * This is a mathaid fabricated checked exception
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class ObjectInstantiationException extends BaseException {

	/*
	 * Date: 21 Jul 2021-----------------------------------------------------------
	 * Time created: 10:35:47---------------------------------------------------
	 */
	/**
	 * Initialises a no-argument {@link InstantiationException} constructor, write
	 * the error to the mathaid error file and throws same error
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
	 * @throws Exception            specifically an {@code InstantiationException}
	 *                              after writing the relevant information to a file
	 * @throws NullPointerException if the {@code LocalizableMessage} is
	 *                              {@code null}. This exception may not written
	 *                              to the mathaid error file nor is it
	 *                              locale-sensitive
	 */
	public ObjectInstantiationException(LocalizableMessage localizableMsg, Object... args) throws Exception {
		super(localizableMsg, new InstantiationException(), args);
	}

}
