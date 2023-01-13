/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.parselet;

import java.util.Iterator;

import mathaid.calculator.base.evaluator.parser.Parser;
import mathaid.calculator.base.evaluator.parser.Syntax;
import mathaid.calculator.base.evaluator.parser.Token;
import mathaid.calculator.base.evaluator.parser.expression.Expression;

/*
 * Date: 4 Sep 2022----------------------------------------------------------- 
 * Time created: 07:23:20---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.parselet------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
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
 * @param <B> The type of builder object used for converting the expression to a
 *            suitable display
 * @param <E> the type of {@code Expression} to return
 * @param <P> the type of concrete parser to use
 */
public interface Parselet<T, B, E extends Expression<B>, P extends Parser<T, B, E, P, S, A>, S extends Syntax<T, B, E, P, S, A>, A> {
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
	E parse(E alreadyParsedLeft, Token<T> yetToBeParsedToken, P parser, Iterator<Token<String>> lexerReference, S syntax, A params);
}
