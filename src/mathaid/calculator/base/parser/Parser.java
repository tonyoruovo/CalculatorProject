/**
 * 
 */
package mathaid.calculator.base.parser;

import mathaid.calculator.base.parser.expression.Expression;

/*
 * Date: 5 May 2021----------------------------------------------------------- 
 * Time created: 16:04:58---------------------------------------------------  
 * Package: com.etineakpopha.parser------------------------------------------------ 
 * Project: RemoteWork------------------------------------------------ 
 * File: Parser.java------------------------------------------------------ 
 * Class name: Parser------------------------------------------------ 
 */
/**
 * An interface that parses tokens (in forms of strings, primitive types,
 * objects) and returns an {@code Expression} using an internal {@code Syntax}
 * object. It is responsible for creating {@code Expression} objects.
 * The user of this interface has control over the tokens within the parser.
 * Users may use a {@code Syntax} object to differentiate between token types.
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
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 *
 * @param <T> The type of object that {@code Type} wraps around. This may be a
 *            string, java primitive value or any suitable object.
 * @param <E> The type of {@code Expression} return by {@link #parse()}
 * @param <P> A type of concrete implementation of this interface
 */
public interface Parser<T, E extends Expression, P extends Parser<T, E, P>> {

	/*
	 * Date: 28 Jul 2021-----------------------------------------------------------
	 * Time created: 18:23:26--------------------------------------------
	 */
	/**
	 * Evaluates available tokens and returns an {@code Expression}. This is may
	 * happen gradually over time, in which case a cursor may be used to track the
	 * progress of this parser, preventing it from re-parsing already parsed tokens
	 * because of an interruption.
	 * 
	 * @return a valid {@code Expression}.
	 */
	E parse();

	/*
	 * Date: 28 Jul 2021-----------------------------------------------------------
	 * Time created: 18:35:12--------------------------------------------
	 */
	/**
	 * Returns the internal {@code Syntax} used for parsing token to ensure that
	 * they are in the right context. This object is used to enforce language
	 * constraints on the parser.
	 * 
	 * @return a {@code Syntax} object that enforce syntactic restrictions on this
	 *         when parsing is done.
	 */
	Syntax<T, E, P> syntax();
}
