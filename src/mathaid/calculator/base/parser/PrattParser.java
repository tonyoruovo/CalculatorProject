/**
 * 
 */
package mathaid.calculator.base.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mathaid.ExceptionMessage;
import mathaid.calculator.base.parser.expression.NameExpression;
import mathaid.calculator.base.parser.parselet.Parselet;

/*
 * Date: 5 May 2021----------------------------------------------------------- 
 * Time created: 16:19:07---------------------------------------------------  
 * Package: com.etineakpopha.parser------------------------------------------------ 
 * Project: RemoteWork------------------------------------------------ 
 * File: PrattParser.java------------------------------------------------------ 
 * Class name: PrattParser------------------------------------------------ 
 */
/**
 * A top-down recursive descent parser implementation of the {@code Parser}
 * interface loosely based on Vaughn Pratt's parser algorithm.
 * <p>
 * All tokens are provided by an {@code Iterator} that is specified at the
 * constructor and cannot be changed after that. This iterator is regarded as an
 * infinite {@code Iterator} i.e will never throw a
 * {@code NoSuchElementException} when being {@link Lexer#next() queried} for
 * token.
 * </p>
 * <h1>How parsing is done</h1>
 * <p>
 * After this object has been created, and {@link #parse()} is called,
 * {@link #parse(int)} is also called with an argument of 0. This means that the
 * {@code Iterator} is queried and whatever token returned will be immediately
 * sent to the waiting stack. This is done because only tokens that can be
 * {@link mathaid.calculator.base.parser.parselet.PrefixParselet parsed as
 * prefix} tokens can start the method {@link #parse(int)}. If the first token
 * to be parsed is not found to be a prefix token an
 * {@code java.lang.IllegalArgumentException} is thrown (note that names are
 * also regarded as prefix expression). Any subsequent prefix token found with
 * the same precedence will be pushed into the waiting stack. If a token has a
 * higher precedence that the immediate previous token, it is written to the
 * output queue, {@code Token} objects representing names of variables and
 * numbers fall under the umbrella of this category. If the subsequent token
 * found is has a lower precedence than the immediate previous token, then that
 * immediate previous token is written to the output queue and this present one
 * is pushed into the waiting stack. This process is repeated for all tokens
 * queried from the {@code Iterator} until it returns an end-of-file token
 * (which would have been parsed but at this point, all non-infix tokens have
 * been parsed and only infix tokens will be parsed of which an end-of-file
 * token is not) is returned, at which point this {@code PrattParser} exits the
 * {@code parse()} method and returns the output queue as a
 * {@code NameExpression}.
 * </p>
 * <h2>The waiting stack</h2>
 * <p>
 * A {@code List} object is used for storing tokens to be parsed. This list is
 * treated like the waiting stack (not ideal but this enables the use of an
 * {@code ArrayList} object which is a fast random access list) for tokens with
 * a lower precedence than the one currently being parsed. After the first token
 * has been inserted into the waiting stack, It is analysed to see if it is a
 * prefix token and sent to the appropriate {@code PrefixParselet} via the
 * {@link CommonSyntax#getPrefixParselet(Type)} method depending on the
 * {@link Token#getType() token's type}. The token being parsed is considered to
 * be the left hand side of the raw expression source.
 * </p>
 * <h2>The output queue</h2>
 * <p>
 * When the appropriate {@code Parselet} object has finished
 * {@link Parselet#parse(mathaid.calculator.base.parser.expression.Expression, Token, Parser)
 * parsing} the token sent to it, it returns a {@code NameExpression} object (of
 * the same generic type specified by this class). This {@code NameExpression}
 * object is the <b>output queue</b>.This is because a {@code NameExpression}
 * object is implemented by the corresponding parselets as recursive objects
 * with a potentially unlimited number of direct children, this is called a
 * <i>Abstract Syntax Tree</i>. This {@code Expression} is considered to be the
 * left hand side expression. This {@code PrattParser} then goes on to parse the
 * remaining tokens and treats the next token as a infix one. However the
 * parselet that will deal with the token being parsed is the actual object that
 * knows the identity of the token. When the {@code InfixParselet} retrieves the
 * left hand side of the expression, it checks to see if there is any waiting
 * expression inside the stack by calling {@link #parse(int)} with the current
 * precedence. This action begins the process we've been describing from the
 * start to ensure any token of higher precedence (even if it's not an infix
 * token) is parsed, this is repeated until a token of lower or equal precedence
 * is found or an end-of-file token is found. When this happens, then the token
 * for which the infix parselet was retrieved will be parsed and written to the
 * output queue. This process prevents an end-of-file token to be parsed since
 * such a token will have an unreasonably low precedence such that it will not
 * be parsed. The unlimited token {@code Iterator} also prevents
 * {@link Iterator#hasNext() checks for emptiness} to be made which naturally
 * ensures faster execution of code by the JVM.
 * </p>
 * <p>
 * No method or constructor of this class directly or indirectly accepts a
 * {@code null} argument. {@code java.lang.IllegalArgumentException} will be
 * thrown when a syntax is violated, for example, expression such as
 * {@code 3*2+6)}. This exception is constructed by
 * {@code IllegalTokenException} class.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @param E a type of {@code NameExpression} to be returned by {@link #parse()}.
 *          An example would be an {@code EvaluatableExpression} which can
 *          evaluate its elements and return a result different from the
 *          expression it was built to be.
 * 
 */
public class PrattParser<E extends NameExpression> implements Parser<String, E, PrattParser<E>> {

	/*
	 * Date: 3 Aug 2021-----------------------------------------------------------
	 * Time created: 13:42:15---------------------------------------------------
	 */
	/**
	 * Sole constructor to initialise this {@code PrattParser} by providing a token
	 * source and a syntax.
	 * 
	 * @param tokenSupplier the source of tokens that will gradually be queried by
	 *                      this {@code class}.
	 * @param syntax        a {@code CommonSyntax} object to prevent ambiguities.
	 */
	public PrattParser(Iterator<Token<String>> tokenSupplier, CommonSyntax<E, PrattParser<E>> syntax) {
		iterator = tokenSupplier;
		this.syntax = syntax;
		this.stack = new ArrayList<>();
	}

	/*
	 * Date: 3 Aug 2021-----------------------------------------------------------
	 * Time created: 13:54:31--------------------------------------------
	 */
	/**
	 * Queries the internal {@code Iterator} object and calls the appropriate
	 * {@code Parselet} to parse the returned token into a {@code NameExpression}.
	 * <p>
	 * This method should not be called in isolation as it may yield undefined
	 * results. This is because the argument is generated by the tokens the queried
	 * from the {@code Iterator}.
	 * </p>
	 * 
	 * @param beginingPrecedence the precedence from which this object should begin
	 *                           parsing. This value is normally expected to only
	 *                           increase.
	 * @return a valid {@code NameExpression}.
	 * @throws RuntimeException specifically a
	 *                          {@code java.lang.IllegalArgumentException} if
	 *                          <ul>
	 *                          <li>The token queried does not lexically match the
	 *                          provided syntax.</li>
	 *                          <li>The token found is not recognised by the
	 *                          syntax.</li>
	 *                          </ul>
	 */
	public E parse(int beginingPrecedence) {
		Token<String> t = readAndPop();
		Parselet<String, E, PrattParser<E>> prefix = syntax.getPrefixParselet(t.getType());

		if (prefix == null)
			new IllegalTokenException(ExceptionMessage.UNABLE_TO_PARSE, t.getName());

		E left = prefix.parse(null, t, this);

		while (beginingPrecedence < getPrecedence()) {
			t = readAndPop();

			Parselet<String, E, PrattParser<E>> infix = syntax.getInfixParselet(t.getType());
			left = infix.parse(left, t, this);
		}
		return left;
	}

	/*
	 * Most Recent Date: 3 Aug 2021-----------------------------------------------
	 * Most recent time created: 14:04:41--------------------------------------
	 */
	/**
	 * Initiates the parse sequence by calling {@link #parse(int)} with an
	 * appropriate argument and returns a valid {@code NameExpression} when all the
	 * tokens have been queried from the provided iterator and there is no more
	 * token to be consumed.
	 * 
	 * @return a valid {@code NameExpression} object.
	 * @throws RuntimeException specifically a
	 *                          {@code java.lang.IllegalArgumentException} if
	 *                          <ul>
	 *                          <li>The token queried does not lexically match the
	 *                          provided syntax.</li>
	 *                          <li>The token found is not recognised by the
	 *                          syntax.</li>
	 *                          </ul>
	 */
	@Override
	public E parse() {
		return parse(0);
	}

	/*
	 * Most Recent Date: 3 Aug 2021-----------------------------------------------
	 * Most recent time created: 14:08:34--------------------------------------
	 */
	/**
	 * Returns the {@code CommonSyntax} object that was provided at
	 * {@link #PrattParser(Iterator, CommonSyntax)}. This is the syntax that is used
	 * to prevent ambiguities during parsing.
	 * 
	 * @return the provided syntax.
	 */
	@Override
	public CommonSyntax<E, PrattParser<E>> syntax() {
		return syntax;
	}

	/*
	 * Date: 3 Aug 2021-----------------------------------------------------------
	 * Time created: 14:10:33--------------------------------------------
	 */
	/**
	 * Checks to see if the argument matches with the token type queried from the
	 * {@code Iterator} and throws an exception if it does'nt or queries a new token
	 * and returns it if it does.
	 * 
	 * @param expected the type of token expected from the next
	 *                 {@link Iterator#next() query}.
	 * @return the next token after consuming it to show that the syntax has been
	 *         adhered to.
	 * @throws RuntimeException specifically a
	 *                          {@code java.lang.IllegalArgumentException} if the
	 *                          token queried does not lexically match the provided
	 *                          syntax.
	 */
	public Token<String> consume(Type<String> expected) {
		Token<String> t = readAndPeek(0);
		if (t.getType() != expected)
			new IllegalTokenException(ExceptionMessage.UNEXPECTED_TOKEN_FOUND, expected.toString(), t.getName());
		return readAndPop();
	}

	/*
	 * Date: 3 Aug 2021-----------------------------------------------------------
	 * Time created: 14:28:22--------------------------------------------
	 */
	/**
	 * Returns {@code true} if the argument type is currently being parsed or
	 * {@code false} if it is not the same type as the one being parsed. This method
	 * also consumes a new token. This method is called by parselets of block
	 * statements, function arguments and grouped statements to find the closing
	 * part.
	 * 
	 * @param expected the token whose type is to be checked.
	 * @return {@code true} if the current token being parsed has a type equal to
	 *         the argument or {@code false} if not.
	 * @see CommonSyntax#functionClose()
	 * @see CommonSyntax#functionOpen()
	 * @see CommonSyntax#precedenceDirector1()
	 * @see CommonSyntax#precedenceDirector2()
	 */
	public boolean match(Type<String> expected) {
		Token<String> t = readAndPeek(0);
//		if (t.getType() != expected)
		if (!t.getType().equals(expected))
			return false;
		readAndPop();
		return true;
	}

	/*
	 * Date: 3 Aug 2021-----------------------------------------------------------
	 * Time created: 14:16:19--------------------------------------------
	 */
	/**
	 * Pushes one element into the {@link #stack} if it is empty and then retrieves
	 * and removes the head of the {@link #stack}.
	 * 
	 * @return the head of the {@link #stack} by permanently removing it.
	 */
	public Token<String> readAndPop() {
		readAndPeek(0);
		return stack.remove(0);
	}

	/*
	 * Date: 3 Aug 2021-----------------------------------------------------------
	 * Time created: 12:58:02--------------------------------------------
	 */
	/**
	 * Writes <i>n</i> (specified by the argument) number of elements into the
	 * {@link #stack} then retrieves but does not remove the element whose index is
	 * specified the argument.
	 * 
	 * @param distance the distance to which the cursor of the stack is to be moved.
	 * @return the element at index {@code distance} in the {@link #stack} but does
	 *         not remove it from the {@link #stack}.
	 */
	private Token<String> readAndPeek(int distance) {
		while (distance >= stack.size())
			stack.add(iterator.next());
		return stack.get(distance);
	}

	/*
	 * Date: 3 Aug 2021-----------------------------------------------------------
	 * Time created: 14:39:00--------------------------------------------
	 */
	/**
	 * Searches for the precedence of the token in the waiting stack and returns it
	 * if found or return 0.
	 * 
	 * @return the precedence of the token a the head of the waiting stack.
	 */
	private int getPrecedence() {
		Parselet<?, ?, ?> parser = syntax.getInfixParselet(readAndPeek(0).getType());
		if (parser != null)
			return readAndPeek(0).getType().precedence().getPrecedence();

		return 0;
	}

	/**
	 * A final field of type {@code Iterator}. This represents the querying object.
	 */
	protected final Iterator<Token<String>> iterator;
	/**
	 * A final field of type {@code CommonSyntax}. This represents syntax that this
	 * object uses to parse tokens to expressions.
	 */
	protected final CommonSyntax<E, PrattParser<E>> syntax;
	/**
	 * A final field of type {@code List}. This represents the waiting stack.
	 */
	protected final List<Token<String>> stack;
}
