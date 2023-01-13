/**
 * 
 */
package mathaid.calculator.base.parser.parselet;

import mathaid.calculator.base.parser.PrattParser;
import mathaid.calculator.base.parser.Token;
import mathaid.calculator.base.parser.expression.NameExpression;
import mathaid.calculator.base.parser.expression.PrefixExpression;

/*
 * Date: 5 May 2021----------------------------------------------------------- 
 * Time created: 20:11:52---------------------------------------------------  
 * Package: com.etineakpopha.parser.parselet------------------------------------------------ 
 * Project: RemoteWork------------------------------------------------ 
 * File: PrefixParselet.java------------------------------------------------------ 
 * Class name: PrefixParselet------------------------------------------------ 
 */
/**
 * A {@code Parselet} used by the {@code PrattParser} class for specialised parsing of
 * unary prefix expressions such as "-a" i.e expression where the token appears
 * before the expression.
 * <p>
 * Tokens representing prefix operators are considered as having a medium
 * precedence when being parsed, as a result, they may be pushed to the output
 * queue if preceding a value or a postfix token (which have a high precedence).
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class PrefixParselet implements Parselet<String, NameExpression, PrattParser<NameExpression>> {

	/*
	 * Most Recent Date: 5 May 2021-----------------------------------------------
	 * Most recent time created: 20:13:25--------------------------------------
	 */
	/**
	 * First looks ahead of the the prefix token to see if any token with a higher
	 * precedence (such as a postfix operator or a value) is on the waiting stack,
	 * then parses that token before going on to return a {@code PrefixExpression}.
	 * It does the latter by calling the given {@code PrattParser}'s
	 * {@code parse(int)} method.
	 * 
	 * @param alreadyParsedLeft  can be left as <code>null</code> as it has no
	 *                           effect whatsoever on this method.
	 * @param yetToBeParsedToken the prefix token currently being parsed.
	 * @param parser the {@code PrattParser} that is being used for parsing. Should
	 *                           be the same as the one that parsed the first
	 *                           argument.
	 * @return a valid {@code NameExpression} object which is specifically a
	 *         {@code PrefixExpression} object.
	 */
	@Override
	public NameExpression parse(NameExpression alreadyParsedLeft, Token<String> yetToBeParsedToken,
			PrattParser<NameExpression> parser) {
		NameExpression right = parser.parse(yetToBeParsedToken.getType().precedence().getPrecedence());
		return new PrefixExpression(yetToBeParsedToken, right);
	}
}
