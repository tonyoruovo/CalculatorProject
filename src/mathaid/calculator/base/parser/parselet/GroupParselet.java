/**
 * 
 */
package mathaid.calculator.base.parser.parselet;

import mathaid.calculator.base.parser.PrattParser;
import mathaid.calculator.base.parser.Token;
import mathaid.calculator.base.parser.expression.GroupExpression;
import mathaid.calculator.base.parser.expression.NameExpression;

/*
 * Date: 5 May 2021----------------------------------------------------------- 
 * Time created: 20:48:54---------------------------------------------------  
 * Package: com.etineakpopha.parser.parselet------------------------------------------------ 
 * Project: RemoteWork------------------------------------------------ 
 * File: GroupParselet.java------------------------------------------------------ 
 * Class name: GroupParselet------------------------------------------------ 
 */
/**
 * A {@code Parselet} used by the {@code PrattParser} class for specialised
 * parsing of grouped expressions such as "(x*5)" i.e expressions where the
 * values/expression are enclosed in parenthesis.
 * <p>
 * Tokens representing groups do not have a specified precedence within this api
 * as they are not considered concrete, however, due to the way they are parsed
 * by the {@code PrattParser} class, they have a high precedence just between a
 * value and postfix expression.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class GroupParselet implements Parselet<String, NameExpression, PrattParser<NameExpression>> {

	/*
	 * Most Recent Date: 5 May 2021-----------------------------------------------
	 * Most recent time created: 20:49:33--------------------------------------
	 */
	/**
	 * Parses grouped expressions such as "(x+(y^2))" and returns a
	 * {@code GroupExpression}. The {@code PrattParser} that generates this object
	 * only calls this method when it hits a character specified by
	 * {@code CommonSyntax.precedenceDirector2()} (which may be a left parenthesis
	 * if users wish to make it so).
	 * 
	 * @param alreadyParsedLeft  can be left as <code>null</code> as it has no
	 *                           effect whatsoever on this method.
	 * @param yetToBeParsedToken can be left as <code>null</code> as it has no
	 *                           effect whatsoever on this method.
	 * @param parser             the {@code PrattParser} where the syntax object
	 *                           resides.
	 * @return a valid {@code NameExpression} object which is specifically a
	 *         {@code GroupExpression} object.
	 */
	@Override
	public NameExpression parse(NameExpression alreadyParsedLeft, Token<String> yetToBeParsedToken,
			PrattParser<NameExpression> parser) {
		NameExpression content = parser.parse();
		/*
		 * A precedence director is any token that a syntax may use for delimiting an
		 * expression to force a certain order of precedence. For example: a * (5 + b),
		 * in this expression, '(' and ')' are precedence directors because they change
		 * the order of operations which should have been multiplication first before
		 * addition to addition before multiplication
		 */
		parser.consume(parser.syntax().getType(parser.syntax().precedenceDirector2()));
//		return exp;
		return new GroupExpression(content);
	}
}
