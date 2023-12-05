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
import mathaid.calculator.base.evaluator.parser.expression.scientific.Array;
import mathaid.calculator.base.evaluator.parser.expression.scientific.Name.Params;
import mathaid.calculator.base.evaluator.parser.parselet.Parselet;
import mathaid.calculator.base.typeset.SegmentBuilder;

/*
 * Date: 16 Sep 2022----------------------------------------------------------- 
 * Time created: 00:54:37---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.parselet.scientific------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: ArrayParselet.java------------------------------------------------------ 
 * Class name: ArrayParselet------------------------------------------------ 
 */
/**
 * A prefix {@code Parselet} for creating {@code Array} objects.
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class ArrayParselet implements
Parselet<String, SegmentBuilder, EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params>, Params>{

	/*
	 * Most Recent Date: 16 Sep 2022-----------------------------------------------
	 * Most recent time created: 00:56:24--------------------------------------
	 */
	/**
	 * Creates a new {@code Array} object.
	 *
	 * @param alreadyParsedLeft {@inheritDoc}
	 * @param yetToBeParsedToken {@inheritDoc}
	 * @param parser {@inheritDoc}
	 * @param lexerReference {@inheritDoc}
	 * @param syntax {@inheritDoc}
	 * @param params {@inheritDoc}
	 * @return the new {@code Array} object.
	 */
	@Override
	public EvaluatableExpression<Params> parse(EvaluatableExpression<Params> alreadyParsedLeft,
			Token<String> yetToBeParsedToken, PrattParser<EvaluatableExpression<Params>, Params> parser,
			Iterator<Token<String>> lexerReference,
			CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params> syntax,
			Params params) {

		List<EvaluatableExpression<Params>> elements = new ArrayList<>();
		
		Type<String> arrayEndType = syntax.getType(syntax.getArrayClose());
		if (!parser.match(arrayEndType, lexerReference)) {
			do {
				elements.add(parser.parse(lexerReference, syntax, params));
			} while (parser.match(syntax.getType(syntax.functionArgDelimiter()), lexerReference));
			parser.consume(arrayEndType, lexerReference);
		}
		return new Array(elements, params);
	}

}
