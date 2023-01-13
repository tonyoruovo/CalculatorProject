/**
 * 
 */
package mathaid;

/*
 * Date: 21 Jul 2021----------------------------------------------------------- 
 * Time created: 13:33:24---------------------------------------------------  
 * Package: mathaid------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: IndexBeyondLimitException.java------------------------------------------------------ 
 * Class name: IndexBeyondLimitException------------------------------------------------ 
 */
/**
 * <p>
 * Exception wrapper that throws {@code java.lang.IndexOutOfBoundsException} to
 * indicate that some index (array, string and/or collection etc index) has
 * exceeded the defined limit.
 * </p>
 * <p>
 * This is a mathaid fabricated unchecked exception
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class IndexBeyondLimitException extends BaseException {

	/*
	 * Date: 21 Jul 2021-----------------------------------------------------------
	 * Time created: 13:33:24---------------------------------------------------
	 */
	/**
	 * Throws the given {@link InstantiationException} object, after writing it to
	 * the mathaid error file.
	 * 
	 * @param localizableMsg a message that is locale specified and may be retrieved
	 *                       by the programmer for the purpose of displaying it to
	 *                       the user of their software
	 * @param e              an {@code IndexOutOfBoundsException}, the purpose of
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
	 * @throws RuntimeException     specifically an
	 *                              {@code IndexOutOfBoundsException} after writing
	 *                              the relevant information to a file
	 * @throws NullPointerException if the {@code LocalizableMessage} is
	 *                              {@code null}. This exception may not written
	 *                              to the mathaid error file nor is it
	 *                              locale-sensitive
	 */
	public IndexBeyondLimitException(LocalizableMessage localizableMsg, IndexOutOfBoundsException e, Object... args)
			throws RuntimeException {
		super(localizableMsg, e, args);
	}

	/*
	 * Date: 21 Jul 2021-----------------------------------------------------------
	 * Time created: 13:52:08---------------------------------------------------
	 */
	/**
	 * Initialises a no-argument {@link IndexOutOfBoundsException} constructor,
	 * write the error to the mathaid error file using the given wrongIndex and
	 * bound to formulate a message and throws same error.
	 * 
	 * @param wrongIndex the index that was encountered that triggered this
	 *                   exception
	 * @param bound      the limit which will be displayed to the user to inform
	 *                   them that this is the limit of the structure they are
	 *                   trying to access.
	 * 
	 * @throws RuntimeException     specifically an
	 *                              {@code IndexOutOfBoundsException} after writing
	 *                              the relevant information to a file
	 * @throws NullPointerException if the {@code LocalizableMessage} is
	 *                              {@code null}. This exception is may not written
	 *                              to the mathaid error file nor is it
	 *                              locale-sensitive
	 */
	public IndexBeyondLimitException(int wrongIndex, int bound) throws RuntimeException {
		this(ExceptionMessage.INDEX_OUT_BOUNDS, new IndexOutOfBoundsException(), wrongIndex, bound);
	}

}
