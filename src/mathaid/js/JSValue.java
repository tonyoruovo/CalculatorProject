/**
 * 
 */
package mathaid.js;

/*
 * Date: 20 Aug 2022----------------------------------------------------------- 
 * Time created: 01:12:57---------------------------------------------------  
 * Package: js------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: JSValue.java------------------------------------------------------ 
 * Class name: JSValue------------------------------------------------ 
 */
/**
 * A wrapper object for a Javascript literal value as a {@code JSSnippet}.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public abstract class JSValue<T extends JSValue<T>> implements JSSnippet {

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 10:21:45--------------------------------------------
	 */
	/**
	 * Serialises the argument function which is assumed to have the same returned
	 * value as {@code this}. Note that when a {@code JSValue} is created using this
	 * method, all states (fields and variables) associated with that object may be
	 * rendered invalid.
	 * 
	 * @param function a {@code JSFunction} which is assumed to have a return value
	 *                 of the same type as {@code this}. For example, if
	 *                 this is a {@code JSString} then the function is assumed to
	 *                 return a Javascript {@code string}.
	 * @return {@code this} of type <code>&lt;T&gt;</code> whose
	 *         {@link #parseToScript(Appendable, int)} uses the argument's method
	 *         instead.
	 */
	public abstract T byFunction(JSFunction function);

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 10:31:37--------------------------------------------
	 */
	/**
	 * Gets the type of this value.
	 * 
	 * @return a {@code Type} value to signify the type of value {@code this} is.
	 */
	public abstract Type getType();

	/*
	 * Most Recent Date: 26 Aug 2022-----------------------------------------------
	 * Most recent time created: 10:32:49--------------------------------------
	 */
	/**
	 * The hash code of this value as specified in <i>Java&trade; Language
	 * Specification</i>.
	 * <p>
	 * If this is an non-compound type such as number, string, boolean null,
	 * undefined etc, then the hash code should be the hash code of the java value
	 * it wraps. Else if it is a compound type such as an array, dictionary or
	 * object, then each implementation (sub-type) should implement their own hash
	 * code.
	 * 
	 * @return the hash code of {@code this}.
	 */
	@Override
	public abstract int hashCode();

	/*
	 * Most Recent Date: 26 Aug 2022-----------------------------------------------
	 * Most recent time created: 10:39:55--------------------------------------
	 */
	/**
	 * Implemented equality for all Javascript value wrappers.
	 * @param o {@inheritDoc}
	 * @return {@inheritDoc}
	 * @see #hashCode()
	 */
	@Override
	public abstract boolean equals(Object o);

}
