/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.parselet.scientific;

import mathaid.calculator.base.evaluator.parser.parselet.Parselet;

import java.util.Iterator;

import mathaid.calculator.base.evaluator.parser.CommonSyntax;
import mathaid.calculator.base.evaluator.parser.PrattParser;
import mathaid.calculator.base.evaluator.parser.Token;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.evaluator.parser.expression.scientific.*;
import mathaid.calculator.base.evaluator.parser.expression.scientific.Name.Params;
import mathaid.calculator.base.typeset.SegmentBuilder;

/*
 * Date: 13 Sep 2022----------------------------------------------------------- 
 * Time created: 23:11:35---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.parselet.scientific------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: NameParselet.java------------------------------------------------------ 
 * Class name: NameParselet------------------------------------------------ 
 */
/**
 * A prefix {@code Parselet} for creating expression that are {@link Name} expressions.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class NameParselet implements
		Parselet<String, SegmentBuilder, EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params>, Params> {

	/*
	 * Most Recent Date: 13 Sep 2022-----------------------------------------------
	 * Most recent time created: 23:22:45--------------------------------------
	 */
	/**
	 * Returns a new {@code Name} object from the {@code yetToBeParsedToken} and {@code params} arguments.
	 * 
	 * @param alreadyParsedLeft  usually <code>null</code> as ths is a prefix parselet.
	 * @param yetToBeParsedToken {@inheritDoc} This must contain a non-empty string.
	 * @param parser             {@inheritDoc}
	 * @param syntax             {@inheritDoc}
	 * @param params             {@inheritDoc}
	 * @param lexerReference     {@inheritDoc}
	 * @return a new {@code Name} object.
	 * @throws NullPointerException if {@code yetToBeParsedToken == null}.
	 */
	@Override
	public EvaluatableExpression<Params> parse(EvaluatableExpression<Params> alreadyParsedLeft,
			Token<String> yetToBeParsedToken, PrattParser<EvaluatableExpression<Params>, Params> parser,
			Iterator<Token<String>> lexerReference,
			CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params> syntax,
			Name.Params params) {
		return new Name(yetToBeParsedToken.getName(), params);
	}
}
