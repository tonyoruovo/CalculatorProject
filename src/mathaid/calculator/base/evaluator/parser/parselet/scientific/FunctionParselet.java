/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.parselet.scientific;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mathaid.calculator.base.evaluator.parser.CommonSyntax;
import mathaid.calculator.base.evaluator.parser.PrattParser;
import mathaid.calculator.base.evaluator.parser.Token;
import mathaid.calculator.base.evaluator.parser.Type;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.evaluator.parser.expression.scientific.Function;
import mathaid.calculator.base.evaluator.parser.expression.scientific.Name.Params;
import mathaid.calculator.base.evaluator.parser.parselet.Parselet;
import mathaid.calculator.base.typeset.SegmentBuilder;

/*
 * Date: 14 Sep 2022----------------------------------------------------------- 
 * Time created: 12:05:41---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.parselet.scientific------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: FunctionParselet.java------------------------------------------------------ 
 * Class name: FunctionParselet------------------------------------------------ 
 */
/**
 * A prefix {@code Parselet} for creating {@code Function} objects.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class FunctionParselet implements
		Parselet<String, SegmentBuilder, EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params>, Params> {

	/*
	 * Most Recent Date: 14 Sep 2022-----------------------------------------------
	 * Most recent time created: 12:06:39--------------------------------------
	 */
	/**
	 * Parses the token argument and searches for a closing symbol specified by {@link CommonSyntax#functionClose()}. If it is not
	 * found (then that means that this function contains arguments), all the arguments found are added to a list. The arguments
	 * must be separated by a symbol specified by {@link CommonSyntax#functionArgDelimiter()}. After the last argument is added to
	 * the list, the closing symbol is consumed and a {@link Function} object is returned.
	 * 
	 * @param alreadyParsedLeft  represents the name of the returned {@code Function} object.
	 * @param yetToBeParsedToken unused. Can be left as <code>null</code>.
	 * @param parser         used for parsing each of the arguments.
	 * @param lexerReference {@inheritDoc}
	 * @param syntax         used for getting the the symbol used for the guarding the function's arguments.
	 * @param params         {@inheritDoc}
	 * @return a {@code Function} object {@inheritDoc}
	 */
	@Override
	public EvaluatableExpression<Params> parse(EvaluatableExpression<Params> alreadyParsedLeft,
			Token<String> yetToBeParsedToken, PrattParser<EvaluatableExpression<Params>, Params> parser,
			Iterator<Token<String>> lexerReference,
			CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params> syntax,
			Params params) {
		List<EvaluatableExpression<Params>> args = new ArrayList<>();

		Type<String> functionEndType = syntax.getType(syntax.functionClose());
		if (!parser.match(functionEndType, lexerReference)) {
			do {
				args.add(parser.parse(lexerReference, syntax, params));
			} while (parser.match(syntax.getType(syntax.functionArgDelimiter()), lexerReference));
			parser.consume(functionEndType, lexerReference);
		}
		return new Function(alreadyParsedLeft, args, params);
	}

}
