/**
 * 
 */
package mathaid.calculator.base.parser.parselet;

import mathaid.calculator.base.parser.PrattParser;
import mathaid.calculator.base.parser.Token;
import mathaid.calculator.base.parser.expression.Name;
import mathaid.calculator.base.parser.expression.NameExpression;

/*
 * Date: 5 May 2021----------------------------------------------------------- 
 * Time created: 20:07:04---------------------------------------------------  
 * Package: com.etineakpopha.parser.parselet------------------------------------------------ 
 * Project: RemoteWork------------------------------------------------ 
 * File: NameParselet.java------------------------------------------------------ 
 * Class name: NameParselet------------------------------------------------ 
 */
/**
 * A {@code Parselet} used by the {@code PrattParser} for specialised parsing of variable
 * names and numbers such as "abc" or "0.5".
 * <p>
 * Tokens representing names and numbers are regarded as values and therefore
 * considered as having the highest precedence possible and when being parsed,
 * they are instantly pushed to the output queue.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class NameParselet implements Parselet<String, NameExpression, PrattParser<NameExpression>> {

	/*
	 * Most Recent Date: 5 May 2021-----------------------------------------------
	 * Most recent time created: 20:10:24--------------------------------------
	 */
	/**
	 * Parses the specified {@code Token} object and returns a valid expression.
	 * This method may never throw an exception except the yetToBeParsedToken is
	 * null, during parsing however, this argument is the only value needed for this
	 * method.
	 * 
	 * @param alredyParsedLeft   can be left as {@code null}.
	 * @param yetToBeParsedToken the name token to be parsed. Must be a value
	 *                           defined by the {@code PrattParser.syntax()} as a
	 *                           valid letter(s) and/or valid digit(s).
	 * @param parser             can be left as {@code null}.
	 * @return a {@code NameExpression} object.
	 */
	@Override
	public NameExpression parse(NameExpression alredyParsedLeft, Token<String> yetToBeParsedToken,
			PrattParser<NameExpression> parser) {
		return new Name(yetToBeParsedToken.getName());
	}

}
