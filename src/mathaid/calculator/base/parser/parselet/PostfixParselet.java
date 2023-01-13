/**
 * 
 */
package mathaid.calculator.base.parser.parselet;

import mathaid.calculator.base.parser.PrattParser;
import mathaid.calculator.base.parser.Token;
import mathaid.calculator.base.parser.expression.NameExpression;
import mathaid.calculator.base.parser.expression.PostfixExpression;

/*
 * Date: 5 May 2021----------------------------------------------------------- 
 * Time created: 20:18:44---------------------------------------------------  
 * Package: com.etineakpopha.parser.parselet------------------------------------------------ 
 * Project: RemoteWork------------------------------------------------ 
 * File: PostfixParselet.java------------------------------------------------------ 
 * Class name: PostfixParselet------------------------------------------------ 
 */
/**
 * A {@code Parselet} used by the {@code PrattParser} class for specialised parsing of
 * unary postfix expressions such as "x!" i.e expression where the token appears
 * after the expression.
 * <p>
 * Tokens representing infix operators are considered as having a high
 * precedence when being parsed, as a result, they may be pushed to the output
 * queue immediately if a group or name expression is not waiting on the stack.
 * </p>
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class PostfixParselet implements
		Parselet<String, NameExpression, PrattParser<NameExpression>> {

	/*
	 * Most Recent Date: 5 May 2021-----------------------------------------------
	 * Most recent time created: 20:19:18--------------------------------------
	 */
	/**
	 * Parses and pushes the given {@code Token} to the output queue.
	 * @param alreadyParsedLeft  can be left as <code>null</code> as it has no
	 *                           effect whatsoever on this method.
	 * @param yetToBeParsedToken  the postfix token currently being parsed.
	 * @param parser  the {@code PrattParser} that is being used for parsing. Should
	 *                           be the same as the one that parsed the first
	 *                           argument.
	 * @return a valid {@code NameExpression} object which is specifically a
	 *         {@code PostfixExpression} object.
	 */
	@Override
	public NameExpression parse(NameExpression alreadyParsedLeft, Token<String> yetToBeParsedToken,
			PrattParser<NameExpression> parser) {
		return new PostfixExpression(alreadyParsedLeft, yetToBeParsedToken);
	}
}
