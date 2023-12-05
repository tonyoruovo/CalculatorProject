/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.parselet.scientific;

import java.util.Iterator;

import mathaid.calculator.base.evaluator.parser.CommonSyntax;
import mathaid.calculator.base.evaluator.parser.PrattParser;
import mathaid.calculator.base.evaluator.parser.Token;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.evaluator.parser.expression.scientific.Name.Params;
import mathaid.calculator.base.evaluator.parser.expression.scientific.Postfix;
import mathaid.calculator.base.evaluator.parser.parselet.Parselet;
import mathaid.calculator.base.typeset.SegmentBuilder;

/*
 * Date: 14 Sep 2022----------------------------------------------------------- 
 * Time created: 12:18:23---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.parselet.scientific------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: PostfixParselet.java------------------------------------------------------ 
 * Class name: PostfixParselet------------------------------------------------ 
 */
/**
 * A prefix {@code Parselet} for creating {@code Postfix} objects.
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class PostfixParselet implements
		Parselet<String, SegmentBuilder, EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params>, Params> {

	/*
	 * Most Recent Date: 14 Sep 2022-----------------------------------------------
	 * Most recent time created: 12:18:29--------------------------------------
	 */
	/**
	 * Creates a new {@code Postfix} object.
	 * 
	 * @param alreadyParsedLeft  the value that is the operand.
	 * @param yetToBeParsedToken the symbol of the postfix operator
	 * @param parser {@inheritDoc}
	 * @param lexerReference {@inheritDoc}
	 * @param syntax {@inheritDoc}
	 * @param params {@inheritDoc}
	 * @return the new {@code Postfix} object.
	 */
	@Override
	public EvaluatableExpression<Params> parse(EvaluatableExpression<Params> alreadyParsedLeft,
			Token<String> yetToBeParsedToken, PrattParser<EvaluatableExpression<Params>, Params> parser,
			Iterator<Token<String>> lexerReference,
			CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params> syntax,
			Params params) {
		return new Postfix(alreadyParsedLeft, yetToBeParsedToken.getName(), params);
	}

}
