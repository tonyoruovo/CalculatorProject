/**
 * 
 */
package mathaid.calculator.base.parser.parselet;

import mathaid.calculator.base.parser.PrattParser;
import mathaid.calculator.base.parser.Token;
import mathaid.calculator.base.parser.expression.NameExpression;
import mathaid.calculator.base.parser.expression.OperatorExpression;

/*
 * Date: 5 May 2021----------------------------------------------------------- 
 * Time created: 20:22:19---------------------------------------------------  
 * Package: com.etineakpopha.parser.parselet------------------------------------------------ 
 * Project: RemoteWork------------------------------------------------ 
 * File: InfixParselet.java------------------------------------------------------ 
 * Class name: InfixParselet------------------------------------------------ 
 */
/**
 * A {@code Parselet} used by the {@code PrattParser} class for specialised
 * parsing of infix expressions such as "a + 0.5" or "x^2" i.e expression where
 * the token appears in the middle of the expression. This may be initialised as
 * a right associative operator or left associative operator. When
 * {@link #parse(NameExpression, Token, PrattParser)} is called on a right
 * associative operator, it's precedence specified by the
 * {@code Token.getType().precedence()} is lowered by one level, i.e all right
 * associative operators are parsed as if they have the same precedence with an
 * operator with a whose has a lower precedence.
 * <p>
 * This object {@code parse(NameExpression, Token, PrattParser)} method first
 * parses calls {@code PrattParser.parse(int)} on the provided
 * {@code NameExpression}, then goes on to parse the given {@code Token} object
 * and everything after that. For example, the expression: <code>2^x+y</code> is
 * parsed as {@code 2^(x+y)}.
 * </p>
 * <p>
 * Tokens representing infix operators are considered as having a mid-low
 * precedence when being parsed, as a result, they may or may not be pushed to
 * the waiting stack depending on what sort of token is before it.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class InfixParselet implements Parselet<String, NameExpression, PrattParser<NameExpression>> {
	/**
	 * A check for right associativity. {@code true} for right associative and
	 * {@code false} for left associative.
	 */
	private boolean right;

	/*
	 * Date: 31 Jul 2021-----------------------------------------------------------
	 * Time created: 03:37:30---------------------------------------------------
	 */
	/**
	 * Initialises a {@code InfixParselet} object with a check to specify
	 * associativity.
	 * 
	 * @param right {@code true} for right associative and {@code false} for left
	 *              associative.
	 */
	public InfixParselet(boolean right) {
		this.right = right;
	}

	/*
	 * Most Recent Date: 5 May 2021-----------------------------------------------
	 * Most recent time created: 20:22:35--------------------------------------
	 */
	/**
	 * Parses the given {@code Token} using the specified {@code NameExpression} and
	 * {@code PrattParser} to determine if given {@code Token} object is indeed an
	 * infix operator and finally returns an {@code OperatorExpression} object.
	 * 
	 * @param alreadyParsedLeft  a previously parsed expression.
	 * @param yetToBeParsedToken the infix token that is currently being parsed.
	 * @param parser             the {@code PrattParser} that is being used for
	 *                           parsing. Should be the same as the one that parsed
	 *                           the first argument.
	 * @return a valid {@code NameExpression} object which is specifically an
	 *         {@code OperatorExpression} object.
	 */
	@Override
	public NameExpression parse(NameExpression alreadyParsedLeft, Token<String> yetToBeParsedToken,
			PrattParser<NameExpression> parser) {
		int p = yetToBeParsedToken.getType().precedence().getPrecedence() - (right ? 1 : 0);
		/*
		 * Call parse(int) to ensure that any more awaiting token in the priority list
		 * which has a higher precedence is first parsed before the given Token
		 */
		NameExpression right = parser.parse(p);
		return new OperatorExpression(alreadyParsedLeft, yetToBeParsedToken, right);
	}
}
