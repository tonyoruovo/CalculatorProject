/**
 * 
 */
package mathaid.calculator.base.evaluator.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression.ExpressionParams;
import mathaid.calculator.base.evaluator.parser.parselet.Parselet;
import mathaid.calculator.base.typeset.SegmentBuilder;

/*
 * Date: 4 Sep 2022----------------------------------------------------------- 
 * Time created: 08:08:01---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: PrattParser.java------------------------------------------------------ 
 * Class name: PrattParser------------------------------------------------ 
 */
/**
 * A top-down recursive descent parser implementation of the {@code Parser} interface based on Vaughn Pratt's parser algorithm.
 * <p>
 * All tokens are provided by an {@code Iterator} that is specified at {@code parse} call. This iterator is regarded as an
 * infinite {@code Iterator} i.e will never throw a {@code NoSuchElementException} when being {@link Iterator#next() queried}
 * for token.
 * </p>
 * <h1>How parsing is done</h1>
 * <p>
 * After this object has been created, and {@link #parse(Iterator, CommonSyntax, ExpressionParams)} is called,
 * {@link #parse(int, Iterator, CommonSyntax, ExpressionParams)} is also called with an argument of 0. This means that the
 * {@code Iterator} is queried and whatever token returned will be immediately sent to the waiting stack. This is done because
 * only tokens that can be {@linkplain mathaid.calculator.base.evaluator.parser.parselet.Parselet parsed as prefix} tokens can
 * start the method {@link #parse(int, Iterator, CommonSyntax, ExpressionParams)}. If the first token to be parsed is not found
 * to be a prefix token an {@code java.lang.IllegalArgumentException} is thrown (note that names are also regarded as prefix
 * expression). Any subsequent prefix token found with the same precedence will be pushed into the waiting stack. If a token has
 * a higher precedence that the immediate previous token, it is written to the output queue, {@code Token} objects representing
 * names of variables and numbers fall under the umbrella of this category. If the subsequent token found is has a lower
 * precedence than the immediate previous token, then that immediate previous token is written to the output queue and this
 * present one is pushed into the waiting stack. This process is repeated for all tokens queried from the {@code Iterator} until
 * it returns an end-of-file token (which would have been parsed but at this point, all non-infix tokens have been parsed and
 * only infix tokens will be parsed of which an end-of-file token is not) is returned, at which point this {@code PrattParser}
 * exits the {@code parse()} method and returns the output queue as an {@code EvaluatableExpression}.
 * </p>
 * <h2>The waiting stack</h2>
 * <p>
 * A {@code List} object is used for storing tokens to be parsed. This list is treated like the waiting stack (not ideal but
 * this enables the use of an {@code ArrayList} object which is a fast random access list) for tokens with a lower precedence
 * than the one currently being parsed. After the first token has been inserted into the waiting stack, It is analysed to see if
 * it is a prefix token and sent to the appropriate {@code PrefixParselet} via the
 * {@linkplain CommonSyntax#getPrefixParselet(Type)} method depending on the {@linkplain Token#getType() token's type}. The
 * token being parsed is considered to be the left hand side of the raw expression source.
 * </p>
 * <h2>The output queue</h2>
 * <p>
 * When the appropriate {@code Parselet} object has finished {@linkplain Parselet#parse parsing} the token sent to it, it
 * returns a {@code EvaluatableExpression} object (of the same generic type specified by this class). This
 * {@code EvaluatableExpression} object is the <b>output queue</b>.This is because a {@code EvaluatableExpression} object is
 * implemented by the corresponding parselets as recursive objects with a potentially unlimited number of direct children, this
 * is called a <i>Abstract Syntax Tree</i>. This {@code Expression} is considered to be the left hand side expression. This
 * {@code PrattParser} then goes on to parse the remaining tokens and treats the next token as a infix one. However the parselet
 * that will deal with the token being parsed is the actual object that knows the identity of the token. When the
 * {@code InfixParselet} retrieves the left hand side of the expression, it checks to see if there is any waiting expression
 * inside the stack by calling {@link #parse(int, Iterator, CommonSyntax, ExpressionParams)} with the current precedence. This
 * action begins the process we've been describing from the start to ensure any token of higher precedence (even if it's not an
 * infix token) is parsed, this is repeated until a token of lower or equal precedence is found or an end-of-file token is
 * found. When this happens, then the token for which the infix parselet was retrieved will be parsed and written to the output
 * queue. This process prevents an end-of-file token to be parsed since such a token will have an unreasonably low precedence
 * such that it will not be parsed. The unlimited token {@code Iterator} also prevents {@link Iterator#hasNext() checks for
 * emptiness} to be made which naturally ensures faster execution of code by the JVM.
 * </p>
 * <p>
 * No method or constructor of this class directly or indirectly accepts a {@code null} argument.
 * {@code java.lang.IllegalArgumentException} will be thrown when a syntax is violated, for example, expression such as
 * {@code 3*2+6)}. This exception is constructed by {@code IllegalTokenException} class.
 * </p>
 * 
 * @param <E>
 * @param <P> the type of params object
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class PrattParser<E extends EvaluatableExpression<P>, P extends ExpressionParams<P>>
		implements Parser<String, SegmentBuilder, E, PrattParser<E, P>, CommonSyntax<E, PrattParser<E, P>, P>, P> {

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 13:03:55 ---------------------------------------------------
	 */
	/**
	 * Constructor for creating a {@code PrattParser} object.
	 */
	public PrattParser() {
	}

	/*
	 * Most Recent Date: 4 Sep 2022-----------------------------------------------
	 * Most recent time created: 08:36:51--------------------------------------
	 */
	/**
	 * Initiates the parse sequence by calling {@link #parse(int, Iterator, CommonSyntax, ExpressionParams)} with an appropriate
	 * argument and returns a valid {@code EvaluatableExpression} when all the tokens have been queried from the provided iterator
	 * and there is no more token to be consumed.
	 * 
	 * @param lexer  the token factory. This represents the querying object. This is the source of tokens that will gradually be
	 *               queried by this {@code class}.
	 * @param syntax the parselet factory. This is a {@code CommonSyntax} object to prevent ambiguities during parsing.
	 * @param params parameter object that contains data for the parse parselets
	 * @return a valid object of the same type as {@code EvaluatableExpression}.
	 * @throws RuntimeException specifically a {@code java.lang.IllegalArgumentException} if
	 *                          <ul>
	 *                          <li>The token queried does not lexically match the provided syntax.</li>
	 *                          <li>The token found is not recognised by the syntax.</li>
	 *                          </ul>
	 */
	@Override
	public E parse(Iterator<Token<String>> lexer, CommonSyntax<E, PrattParser<E, P>, P> syntax, P params) {
		return parse(0, lexer, syntax, params);
	}

	/*
	 * Date: 4 Sep 2022-----------------------------------------------------------
	 * Time created: 08:48:35--------------------------------------------
	 */
	/**
	 * Prompts the provided Lexer for tokens and parses the results into an expression.
	 * <p>
	 * It parses tokens generated by the provided {@code lexer} into an expression where the parsing will go as long as the next
	 * token generated by the token factory is a type that has higher precedence (as specified by {@link Type#getPrecedence()}) then
	 * the provided <code>beginingPrecedence</code> argument.
	 * <p>
	 * This queries the {@code lexer} object and calls the appropriate {@code Parselet} to parse the returned token into a
	 * {@code EvaluatableExpression}.
	 * <p>
	 * This method should not be called in isolation as it may yield undefined results. This is because the argument is generated by
	 * the tokens the queried from the {@code lexer}.
	 * 
	 * @param beginingPrecedence the precedence from which this object should begin parsing. This is the smallest precedence this
	 *                           parser may regard. When the parser encounters values with precedence equal to or less than this
	 *                           value, the parsing stops and the generated AST is returned.
	 *                           <p>
	 *                           This value is normally expected to only increase.
	 * @param lexer              the token factory. This represents the querying object. This is the source of tokens that will
	 *                           gradually be queried by this {@code class}.
	 * @param syntax             the parselet factory. This is a {@code CommonSyntax} object to prevent ambiguities during parsing.
	 * @param params             parameter object that contains data for the parse parselets
	 * @return a valid object of the same type as {@code EvaluatableExpression}.
	 * @throws RuntimeException specifically a {@code java.lang.IllegalArgumentException} if
	 *                          <ul>
	 *                          <li>The token queried does not lexically match the provided syntax.</li>
	 *                          <li>The token found is not recognised by the syntax.</li>
	 *                          </ul>
	 * @implNote
	 *           <ol>
	 *           <li>The first token parsed by this method is expected to be a prefix token. Prefix tokens are tokens that will
	 *           always start an expression such numbers variable names, prefix (such as pre-increment/decrement) operators and
	 *           prefix keywords. If this token fails to retrieve the corresponding prefix parselet, a syntactic error is
	 *           thrown.</li>
	 *           <li>The parselet for this type is retrieved from the syntax and the parsed into an expression. This expression
	 *           becomes the left-side expression.</li>
	 *           <li>The parser continues to search for the next token whose precedence is greater than zero. When a token's type's
	 *           precedence is less than zero, then a valid AST is returned.</li>
	 *           </ol>
	 *           <p>
	 *           Note that for the above to work properly, the following should generally be observed:
	 *           <ul>
	 *           <li>all prefix parselets (a prefix parselet is a parselet that is retrieved from the syntax using
	 *           {@code syntax.getPrefixParselet(token.getType())}) should not call this method and should not query the lexer (to
	 *           query the lexer is to call it's next method).</li>
	 *           <li>all infix parselets (an infix parselet is a parselet that is retrieved from the syntax using
	 *           {@code syntax.getInfixParselet(token.getType())}) should call {@link parseWithPrecedence this method} instead if
	 *           another token is ahead of them otherwise they are considered postfix and should act like prefix parselet. These
	 *           should not also query the lexer. When calling the {@link #parse(int, Iterator, CommonSyntax, ExpressionParams)
	 *           recommended parse method}, the precedence of the token about to be parsed should be the argument for the first
	 *           parameter of that method.</li>
	 *           <li>all postfix parselets (a postfix parselet is an infix parselet that acts like a prefix and does not call
	 *           {@code parse} within) can not call this method but should act like prefix parselets.</li>
	 *           </ul>
	 */
	public E parse(int beginingPrecedence, Iterator<Token<String>> lexer, CommonSyntax<E, PrattParser<E, P>, P> syntax,
			P params) {
		Token<String> t = readAndPop(lexer);
		Parselet<String, SegmentBuilder, E, PrattParser<E, P>, CommonSyntax<E, PrattParser<E, P>, P>, P> prefix = syntax
				.getPrefixParselet(t.getType());

		if (prefix == null)
			throw new IllegalArgumentException(String.format("unable to parse \"%s\"", t.getName()));

		E left = prefix.parse(null, t, this, lexer, syntax, params);

		while (beginingPrecedence < getPrecedence(lexer, syntax)) {
			t = readAndPop(lexer);

			Parselet<String, SegmentBuilder, E, PrattParser<E, P>, CommonSyntax<E, PrattParser<E, P>, P>, P> infix = syntax
					.getInfixParselet(t.getType());
			left = infix.parse(left, t, this, lexer, syntax, params);
		}

		return left;

	}
	
	/*
	 * Date: 2 Dec 2023 -----------------------------------------------------------
	 * Time created: 00:39:51 ---------------------------------------------------
	 */
	/**
	 * Clears the internal stack. Meant to enhance reuseability
	 */
	public void reset() {
		stack.clear();
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 13:27:57 ---------------------------------------------------
	 */
	/**
	 * Checks to see if the argument matches with the token type queried from the {@code lexer} and throws an exception if it
	 * does'nt or queries a new token and returns it if it does.
	 * <p>
	 * This requests the given {@code lexer} object to generate a token, pushes it into the waiting stack awaiting processing,
	 * inspects the waiting token whether it is the same as the expected token, if it is, then removes the waiting token from the
	 * stack into the processing queue to be parse, if it isn't, then a exception is thrown.
	 * 
	 * @param lexer    the lexer reference.
	 * @param expected the type of token expected from the next {@link Iterator#next() query}.
	 * @return the next token after consuming it to show that the syntax has been adhered to i.e a valid token ready to be
	 *         processed.
	 * @throws RuntimeException specifically a {@code java.lang.IllegalArgumentException} if the token queried does not lexically
	 *                          match the provided syntax.
	 */
	public Token<String> consume(Type<String> expected, Iterator<Token<String>> lexer) {
		Token<String> t = readAndPeek(0, lexer);
		if (!t.getType().equals(expected))
			throw new IllegalArgumentException();
		return readAndPop(lexer);
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 13:31:49 ---------------------------------------------------
	 */
	/**
	 * Returns {@code true} if the argument type is currently being parsed or {@code false} if it is not the same type as the one
	 * being parsed. This method also consumes a new token. This method is called by parselets of block statements, function
	 * arguments and grouped statements to find the closing part.
	 * 
	 * @param expected the token whose type is to be checked.
	 * @param lexer    the lexer reference.
	 * @return {@code true} if the current token being parsed has a type equal to the argument or {@code false} if not.
	 * @see CommonSyntax#functionClose()
	 * @see CommonSyntax#functionOpen()
	 * @see CommonSyntax#precedenceDirector1()
	 * @see CommonSyntax#precedenceDirector2()
	 */
	public boolean match(Type<String> expected, Iterator<Token<String>> lexer) {
		Token<String> t = readAndPeek(0, lexer);
		if (!t.getType().equals(expected))
			return false;
		readAndPop(lexer);
		return true;
	}

	/*
	 * Date: 4 Sep 2022----------------------------------------------------------- 
	 * Time created: 09:06:06--------------------------------------------
	 */
	/**
	 * Searches for the precedence of the token in the waiting stack and returns it if found or return 0.
	 * <p>
	 * This gets the precedence of the next token to be consumed. That is basically the token on the processing queue.
	 * 
	 * @param lexer  the token generator
	 * @param syntax a syntax for the token generator
	 * @return the precedence of the next token to be consumes i.e the precedence of the token a the top of the waiting stack.
	 */
	private int getPrecedence(Iterator<Token<String>> lexer, CommonSyntax<E, PrattParser<E, P>, P> syntax) {
		Parselet<?, ?, ?, ?, ?, ?> parselet = syntax.getInfixParselet(readAndPeek(0, lexer).getType());
		if (parselet != null)
			return readAndPeek(0, lexer).getType().getPrecedence().getPrecedence();
		return 0;
	}

	/*
	 * Date: 4 Sep 2022-----------------------------------------------------------
	 * Time created: 08:52:49--------------------------------------------
	 */
	/**
	 * Removes a token from the processing queue, discards it and returns the next token on the waiting stack
	 * 
	 * @param lexer the token generator
	 * @returns the topmost token on the waiting stack
	 * @implNote Pushes one element into the {@link #stack} if it is empty and then retrieves and removes the head of the
	 *           {@link #stack}.
	 */
	protected Token<String> readAndPop(Iterator<Token<String>> lexer) {
		readAndPeek(0, lexer);
		return stack.remove(0);
	}

	/*
	 * Date: 4 Sep 2022-----------------------------------------------------------
	 * Time created: 08:55:15--------------------------------------------
	 */
	/**
	 * Writes <i>n</i> (specified by the argument) number of elements into the {@link #stack} then retrieves but does not remove the
	 * element whose index is specified the argument.
	 * <p>
	 * Causes the token generator to generate a token and then pushes it into the stack and returns that same token.
	 * 
	 * @param lexer    the token generator
	 * @param distance the number of tokens to generate. This is also the distance to which the cursor of the stack is to be moved.
	 * @return the element at index {@code distance} in the {@link #stack} but does not remove it from the {@link #stack} i.e
	 *         returns the last token generated that is also the topmost token on the stack but does not remove it from the stack
	 */
	protected Token<String> readAndPeek(int distance, Iterator<Token<String>> lexer) {
		while (distance >= stack.size())
			stack.add(lexer.next());
		return stack.get(distance);
	}

	/**
	 * A final field of type {@code List}. This represents the waiting stack.
	 */
	protected final List<Token<String>> stack = new ArrayList<>();

}
