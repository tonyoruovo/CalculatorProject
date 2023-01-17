/**
 * 
 */
package mathaid.calculator.base.evaluator.parser;

import java.util.Iterator;

import mathaid.calculator.base.evaluator.parser.expression.Expression;

/*
 * Date: 4 Sep 2022----------------------------------------------------------- 
 * Time created: 06:25:21---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Parser.java------------------------------------------------------ 
 * Class name: Parser------------------------------------------------ 
 */
/**
 * An interface that parses tokens (in forms of strings, primitive types,
 * objects) and returns an {@code Expression} using an internal {@code Syntax}
 * object. It is responsible for creating {@code Expression} objects. The user
 * of this interface has control over the tokens within the parser. Users may
 * use a {@code Syntax} object to differentiate between token types.
 * <p>
 * The tokens that are parsed by this interface are already in a state such that
 * only valid characters are present inside the parser. Users are advised to
 * implements some sort of lexer that enforces this. This however, does not mean
 * that the parser will not find errors within the token but when those errors
 * are found, they will be syntactic errors and not otherwise.
 * </p>
 * <p>
 * Although they are many possible parser implementations, the mathaid api only
 * implements one - a {@link PrattParser pratt parser}. However, users can
 * easily extend this interface for their needs.
 * </p>
 * 
 * @param <T> The type of object that {@code Type} wraps around. This may be a
 *            string, java primitive value or any suitable object.
 * @param <B> The type of builder object used for converting the expression to a
 *            suitable display.
 * @param <E> The type of {@code Expression} return by {@link #parse()}
 * @param <P> a type of concrete implementation of this interface
 * @param <A> a type that may be used as parameters for forming an expression
 *            e.g {@code ExpressionParams}
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface Parser<T, B, E extends Expression<B>, P extends Parser<T, B, E, P, S, A>, S extends Syntax<T, B, E, P, S, A>, A> {

	/*
	 * Date: 4 Sep 2022-----------------------------------------------------------
	 * Time created: 07:31:15--------------------------------------------
	 */
	/**
	 * @param lexer
	 * @param syntax
	 * @param params
	 * @return
	 */
	E parse(Iterator<Token<String>> lexer, S syntax, A params);
}