/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.parselet.programmer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mathaid.calculator.base.evaluator.parser.CommonSyntax;
import mathaid.calculator.base.evaluator.parser.PrattParser;
import mathaid.calculator.base.evaluator.parser.Token;
import mathaid.calculator.base.evaluator.parser.Type;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.evaluator.parser.expression.programmer.Function;
import mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression;
import mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression.Params;
import mathaid.calculator.base.evaluator.parser.parselet.Parselet;
import mathaid.calculator.base.typeset.SegmentBuilder;

/*
 * Date: 1 Dec 2022----------------------------------------------------------- 
 * Time created: 15:46:48---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.parselet.programmer------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: FunctionParselet.java------------------------------------------------------ 
 * Class name: FunctionParselet------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class FunctionParselet implements
		Parselet<String, SegmentBuilder, EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params>, Params> {

	/*
	 * Most Recent Date: 1 Dec 2022-----------------------------------------------
	 * Most recent time created: 16:14:14--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param alreadyParsedLeft
	 * @param yetToBeParsedToken
	 * @param parser
	 * @param lexerReference
	 * @param syntax
	 * @param params
	 * @return
	 */
	@Override
	public PExpression parse(EvaluatableExpression<Params> alreadyParsedLeft,
			Token<String> yetToBeParsedToken, PrattParser<EvaluatableExpression<Params>, Params> parser,
			Iterator<Token<String>> lexerReference,
			CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params> syntax,
			Params params) {
		List<PExpression> args = new ArrayList<>();

		Type<String> functionEndType = syntax.getType(syntax.functionClose());
		if (!parser.match(functionEndType, lexerReference)) {
			do {
				args.add((PExpression) parser.parse(lexerReference, syntax, params));
			} while (parser.match(syntax.getType(syntax.functionArgDelimiter()), lexerReference));
			parser.consume(functionEndType, lexerReference);
		}
		return new Function(alreadyParsedLeft.getName(), args, params);
	}

}
