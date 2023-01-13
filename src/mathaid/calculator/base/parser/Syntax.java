/**
 * 
 */
package mathaid.calculator.base.parser;

import java.util.Set;

import mathaid.calculator.base.parser.expression.Expression;
import mathaid.calculator.base.parser.parselet.Parselet;

/*
 * Date: 5 May 2021----------------------------------------------------------- 
 * Time created: 15:24:04---------------------------------------------------  
 * Package: com.etineakpopha.parser------------------------------------------------ 
 * Project: RemoteWork------------------------------------------------ 
 * File: Syntax.java------------------------------------------------------ 
 * Class name: Syntax------------------------------------------------ 
 */
/**
 * A syntax to enforce non-ambiguity on a {@code Parser}. Separating the logic
 * of such from a {@code Parser} implementation contributes to decoupling, which
 * is what this developer is all about. How this is implemented is entirely up
 * to the implementers, but at least all the supported characters within a
 * parser may be registered in this object.
 * <p>
 * A {@code Syntax} contains supported characters specified by
 * {@link #getPunctuatorsAndDelimiters()}, {@link #getLetters()} and
 * {@link #getDigits()}. White spaces may be supported as specified by
 * {@link #getWhitespaces()}. The {@code CommonSyntax} interface provides
 * extended functionality for programming-language-like syntax and also includes
 * a builder for constructing {@code Syntax} objects.
 * </p>
 * <p>
 * All methods that return a {@code Collection} may return an empty
 * {@code Collection} if this {@code Syntax} does not support that subset of
 * characters.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 *
 * @param <T> The type wrapped by the {@code Type} of the token.
 * @param <E> The {@code Expression} type that will be used with this
 *            {@code Syntax}.
 * @param <P> An associated {@code Parser}
 */
public interface Syntax<T, E extends Expression, P extends Parser<T, E, P>> {

	/*
	 * Date: 31 Jul 2021-----------------------------------------------------------
	 * Time created: 10:39:29--------------------------------------------
	 */
	/**
	 * Returns all the supported types in this {@code Syntax} as a {@code Set}.
	 * <p>
	 * A type may represent a wrapped object with a precedence.
	 * </p>
	 * 
	 * @return a {@code Set} of all the types in this {@code Syntax}.
	 */
	Set<Type<T>> getTypes();

	/*
	 * Date: 31 Jul 2021-----------------------------------------------------------
	 * Time created: 10:51:30--------------------------------------------
	 */
	/**
	 * Returns a {@code Set} of keywords that will not be parsed as a name
	 * expression. For example, if the word "abc" is a keyword within a given
	 * syntax, it may not be used as a (a.k.a identifier).
	 * 
	 * @return a {@code Set} of strings of all the keywords in this {@code Syntax}.
	 */
	Set<String> getKeywords();

	/*
	 * Date: 31 Jul 2021-----------------------------------------------------------
	 * Time created: 11:05:42--------------------------------------------
	 */
	/**
	 * Returns a {@code Set} of all the supported digits in this {@code Syntax} used
	 * for numerical evaluations.
	 * 
	 * @return a {@code Set} of all the digits that can be used to form valid
	 *         numbers in this {@code Syntax}.
	 */
	Set<Character> getDigits();

	/*
	 * Date: 31 Jul 2021-----------------------------------------------------------
	 * Time created: 11:08:16--------------------------------------------
	 */
	/**
	 * Returns a {@code Set} of all the valid characters that can form names and
	 * identifiers excluding digits. An example for the java programming would be
	 * the whole set of latin script, the underscore and dollar sign.
	 * 
	 * @return a {@code Set} of all the characters in this {@code Syntax} used for
	 *         naming.
	 */
	Set<Character> getLetters();

	/*
	 * Date: 31 Jul 2021-----------------------------------------------------------
	 * Time created: 11:08:16--------------------------------------------
	 */
	/**
	 * Returns a {@code Set} of all the valid characters that are used for
	 * delimiting and punctuation. An example may be the comma character "," which
	 * is popularly used as a punctuator for separating arguments within a function.
	 * An example of a delimiter may be the curly brace "{"/"}" which is used for
	 * block statements.
	 * 
	 * @return a {@code Set} of all the symbolic characters in this {@code Syntax}
	 *         used for punctuation and delimiting.
	 */
	Set<Character> getPunctuatorsAndDelimiters();

	/*
	 * Date: 31 Jul 2021-----------------------------------------------------------
	 * Time created: 11:22:27--------------------------------------------
	 */
	/**
	 * Returns all the valid white spaces within this {@code Syntax}.
	 * 
	 * @return the {@code Set} of all white spaces within this {@code Syntax}.
	 */
	Set<Character> getWhitespaces();

	/*
	 * Date: 31 Jul 2021-----------------------------------------------------------
	 * Time created: 11:24:59--------------------------------------------
	 */
	/**
	 * Returns the {@code Parselet} used for parsing infix tokens with the given
	 * {@code Type}. Note that postfix tokens are also parsed by these parselets.
	 * 
	 * @param type the type of parselet to be returned.
	 * @return a {@code Parselet} object which represents an infix parselet that
	 *         parses tokens of the same types as the argument.
	 */
	Parselet<T, E, P> getInfixParselet(Type<T> type);

	/*
	 * Date: 31 Jul 2021-----------------------------------------------------------
	 * Time created: 11:28:03--------------------------------------------
	 */
	/**
	 * Returns the {@code Parselet} used for parsing prefix tokens with the given
	 * {@code Type}.
	 * 
	 * @param type the type of parselet to be returned.
	 * @return a {@code Parselet} object which represents a prefix parselet that
	 *         parses tokens of the same types as the argument.
	 */
	Parselet<T, E, P> getPrefixParselet(Type<T> type);

}
