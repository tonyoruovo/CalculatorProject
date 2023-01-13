/**
 * 
 */
package mathaid;

/*
 * Date: 18 Jul 2021----------------------------------------------------------- 
 * Time created: 14:48:54---------------------------------------------------  
 * Package: mathaid------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: MathaidException.java------------------------------------------------------ 
 * Class name: MathaidException------------------------------------------------ 
 */
/**
 * A model exception with all the requirements for proper exception
 * documentation.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
interface MathaidException {

	/*
	 * Date: 22 Jul 2021-----------------------------------------------------------
	 * Time created: 11:49:29--------------------------------------------
	 */
	/**
	 * Gets the object that prompted this to be invoked
	 * 
	 * @return a {@code Throwable} object
	 */
	Throwable getException();

	/*
	 * Date: 22 Jul 2021-----------------------------------------------------------
	 * Time created: 11:50:55--------------------------------------------
	 */
	/**
	 * Retrieves the object array for which act as arguments for the corresponding
	 * {@code LocalizableMessage} as specified by {@link java.text.MessageFormat}
	 * 
	 * @return an object array that corresponds with an internal
	 *         {@code mathaid.LocalizableMessage}
	 */
	Object[] getArguments();

	/*
	 * Date: 22 Jul 2021-----------------------------------------------------------
	 * Time created: 11:54:48--------------------------------------------
	 */
	/**
	 * A message to be displayed in a locale-specific manner.
	 * 
	 * @return a {@code LocalizableMessage} object for locale-specific messages.
	 */
	LocalizableMessage getLocalizable();
}
