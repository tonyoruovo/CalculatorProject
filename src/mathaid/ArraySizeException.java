/**
 * 
 */
package mathaid;

/*
 * Date: 21 Jul 2021----------------------------------------------------------- 
 * Time created: 14:33:04---------------------------------------------------  
 * Package: mathaid.calculator.base------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: ArraySizeException.java------------------------------------------------------ 
 * Class name: ArraySizeException------------------------------------------------ 
 */
/**
 * <p>
 * Exception wrapper that throws {@code java.lang.ArrayIndexOutOfBoundsException} to
 * indicate that an array has been accessed at an illegal index.
 * </p>
 * <p>
 * This is a mathaid fabricated unchecked exception
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class ArraySizeException extends IndexBeyondLimitException {

	/*
	 * Date: 21 Jul 2021-----------------------------------------------------------
	 * Time created: 14:34:54---------------------------------------------------
	 */
	/**
	 * Initialises a no-argument {@link ArrayIndexOutOfBoundsException} constructor, write
	 * the error to the mathaid error file and throws same error
	 * 
	 * 
	 * @param localizableMsg a message that is locale specified and may be retrieved
	 *                       by the programmer for the purpose of displaying it to
	 *                       the user of their software.
	 * @param args           varargs for the {@code LocalizableMessage} to consume
	 *                       {@code LocalizableMessage} may contain arguments as
	 *                       specified in {@link java.text.MessageFormat}, these
	 *                       arguments are parsed along with the
	 *                       {@code LocalizableMessage} to form a human-readable
	 *                       string.
	 * @throws RuntimeException            specifically an {@code ArrayIndexOutOfBoundsException}
	 *                              after writing the relevant information to a file
	 * @throws NullPointerException if the {@code LocalizableMessage} is
	 *                              {@code null}. This exception may not written
	 *                              to the mathaid error file nor is it
	 *                              locale-sensitive
	 */
	public ArraySizeException(LocalizableMessage localizableMsg, Object... args) throws RuntimeException {
		super(localizableMsg, new ArrayIndexOutOfBoundsException(), args);
	}

}
