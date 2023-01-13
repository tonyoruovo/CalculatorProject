/**
 * 
 */
package mathaid.calculator.base.parser.parselet;

import java.util.ArrayList;
import java.util.List;

import mathaid.calculator.base.parser.CommonSyntax;
import mathaid.calculator.base.parser.PrattParser;
import mathaid.calculator.base.parser.Token;
import mathaid.calculator.base.parser.Type;
import mathaid.calculator.base.parser.expression.FunctionExpression;
import mathaid.calculator.base.parser.expression.NameExpression;

/*
 * Date: 5 May 2021----------------------------------------------------------- 
 * Time created: 20:30:28---------------------------------------------------  
 * Package: com.etineakpopha.parser.parselet------------------------------------------------ 
 * Project: RemoteWork------------------------------------------------ 
 * File: FunctionParselet.java------------------------------------------------------ 
 * Class name: FunctionParselet------------------------------------------------ 
 */
/**
 * A {@code Parselet} used by the {@code PrattParser} class for specialised
 * parsing of functions expressions such as "sin(x)" i.e expressions where the
 * name precede values enclosed in parenthesis.
 * <p>
 * Tokens representing functions are considered as infix expression (this is
 * because the left parenthesis is parsed between the name of the function and
 * the argument of the function) and as such have exactly the same precedence.
 * </p>
 * <p>
 * This parselet may also parse no-argument functions.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class FunctionParselet implements Parselet<String, NameExpression, PrattParser<NameExpression>> {

	/*
	 * Most Recent Date: 5 May 2021-----------------------------------------------
	 * Most recent time created: 20:31:07--------------------------------------
	 */
	/**
	 * Parses the given {@code Token} of the same type specified by
	 * {@code CommonSyntax.getType(Character)} (using the
	 * {@code CommonSyntax.functionOpen()} as an argument) and all other tokens
	 * after, only stopping when it comes across a token specified by
	 * {@code CommonSyntax.functionClose()} and then returns a
	 * {@code FunctionExpression} object. Multiple arguments may be separated by the
	 * character provided by {@code CommonSyntax.functionArgDelimiter()}.
	 * <p>
	 * It parses the token argument and searches for a closing symbol specified by
	 * {@link CommonSyntax#functionClose()}. If it is not found (then that means
	 * that this function contains arguments), all the arguments found are added to
	 * a list. The arguments must be separated by a symbol specified by
	 * {@link CommonSyntax#functionArgDelimiter()}. After the last argument is added
	 * to the list, the closing symbol is consumed and a {@link FunctionExpression}
	 * object is returned.
	 * </p>
	 * 
	 * @param alreadyParsedLeft  must be a name such as {@link Name} or
	 *                           {@code EvaluatableName}.
	 * @param yetToBeParsedToken can be left as <code>null</code> as it has no
	 *                           effect whatsoever on this method.
	 * @param parser             the {@code PrattParser} where the syntax object
	 *                           resides.
	 * @return a valid {@code NameExpression} object which is specifically a
	 *         {@code FunctionExpression} object.
	 */
	@Override
	public NameExpression parse(NameExpression alreadyParsedLeft, Token<String> yetToBeParsedToken,
			PrattParser<NameExpression> parser) {
		List<NameExpression> args = new ArrayList<>();

		CommonSyntax<?, ?> s = parser.syntax();
		Type<String> functionEndType = s.getType(s.functionClose());
		if (!parser.match(functionEndType)) {
			do {
				args.add(parser.parse());
			} while (parser.match(s.getType(s.functionArgDelimiter())));
			parser.consume(functionEndType);
		}
		return new FunctionExpression(alreadyParsedLeft, args);
	}
}
