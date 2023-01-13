/**
 * 
 */
package mathaid.calculator.base.parser;

/*
 * Date: 5 May 2021----------------------------------------------------------- 
 * Time created: 16:01:50---------------------------------------------------  
 * Package: mathaid.calculator.base.parser------------------------------------------------ 
 * Project: RemoteWork------------------------------------------------ 
 * File: Token.java------------------------------------------------------ 
 * Class name: Token------------------------------------------------ 
 */
/**
 * An object created by a {@code Lexer} and parsed by a {@code PrattParser} to
 * create an {@code Expression}. Every token may be created from a string or
 * some object and during creation a {@code Type} and a name is assigned to it.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @param <T> the object wrapped by the {@code Type} of this {@code Token}.
 */
public interface Token<T> {

	/*
	 * Date: 29 Jul 2021-----------------------------------------------------------
	 * Time created: 10:35:17--------------------------------------------
	 */
	/**
	 * Returns the associated type. A {@code Type} provides a way to differentiate
	 * {@code Tokens} and wraps the original object that was parsed.
	 * 
	 * @return the associated type.
	 */
	Type<T> getType();

	/*
	 * Date: 29 Jul 2021-----------------------------------------------------------
	 * Time created: 10:39:56--------------------------------------------
	 */
	/**
	 * Returns the name assigned to this {@code Token} during creation.
	 * 
	 * @return a {@code String} which is also the name of this token.
	 */
	String getName();

}
