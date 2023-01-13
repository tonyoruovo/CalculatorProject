/**
 * 
 */
package mathaid.calculator.base.parser;

/*
 * Date: 5 May 2021----------------------------------------------------------- 
 * Time created: 15:28:06---------------------------------------------------  
 * Package: mathaid.calculator.base.parser------------------------------------------------ 
 * Project: RemoteWork------------------------------------------------ 
 * File: Type.java------------------------------------------------------ 
 * Class name: Type------------------------------------------------ 
 */
/**
 * A {@code Type} object wraps an object of the users choice within a
 * {@code Token}.
 * <p>
 * When {@code Token} objects are created by a {@code Lexer}, a {@code Type} is
 * specified along with it. This {@code Type} will wrap an object of choice and
 * contain a {@code Precedence} object for the parser and syntax.
 * </p>
 * 
 * @param <T> the object to be wrapped
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public interface Type<T> {
	/*
	 * Date: 29 Jul 2021-----------------------------------------------------------
	 * Time created: 10:52:26--------------------------------------------
	 */
	/**
	 * Returns the wrapped object
	 * 
	 * @return the wrapped object
	 */
	T name();

	/*
	 * Date: 29 Jul 2021-----------------------------------------------------------
	 * Time created: 10:53:08--------------------------------------------
	 */
	/**
	 * Returns an associated object that parsers and parselets use for precedence
	 * parsing.
	 * 
	 * @return a {@code Precedence} object used by parsers and parselets.
	 */
	Precedence precedence();
}
