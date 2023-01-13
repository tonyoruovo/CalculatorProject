/**
 * 
 */
package mathaid.calculator.base.parser.parselet;

import mathaid.calculator.base.parser.Parser;
import mathaid.calculator.base.parser.Token;
import mathaid.calculator.base.parser.expression.Expression;

/*
 * Date: 5 May 2021----------------------------------------------------------- 
 * Time created: 16:06:37---------------------------------------------------  
 * Package: mathaid.calculator.base.parser.parselet------------------------------------------------ 
 * Project: RemoteWork------------------------------------------------ 
 * File: Parselet.java------------------------------------------------------ 
 * Class name: Parselet------------------------------------------------ 
 */
/**
 * A {@code Parselet} is an object used by the parser for specialised parsing.
 * It is directly responsible for creating {@code Expression} objects to be
 * returned by {@link mathaid.calculator.base.parser.Parser#parse()}. The amount
 * of parsing to be done by a parselet is limited to the kind of kind of
 * {@code Token} to be parsed.
 * <p>
 * In top-down parsing, this is a base interface for all the parselet that
 * parses names, prefix and postfix operators, functions etc.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 *
 * @param <T> The type of object specified by the token type
 * @param <E> the type of {@code Expression} to return
 * @param <P> the type of concrete parser to use
 */
public interface Parselet<T, E extends Expression, P extends Parser<T, E, P>> {

	/*
	 * Date: 29 Jul 2021-----------------------------------------------------------
	 * Time created: 11:13:58--------------------------------------------
	 */
	/**
	 * Parses the token argument and returns an {@code Expression}.
	 * 
	 * @param alreadyParsedLeft  a valid already parsed {@code Expression} whose
	 *                           syntax matches the token argument.
	 * @param yetToBeParsedToken the token to be parsed.
	 * @param parser             a concrete parser to be used.
	 * @return a valid {@code Expression} depending on the {@code Syntax} of the
	 *         provided parser.
	 */
	E parse(E alreadyParsedLeft, Token<T> yetToBeParsedToken, P parser);
}
