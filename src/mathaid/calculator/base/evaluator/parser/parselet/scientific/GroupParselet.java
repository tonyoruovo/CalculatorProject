/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.parselet.scientific;

import java.util.Iterator;

import mathaid.calculator.base.evaluator.parser.CommonSyntax;
import mathaid.calculator.base.evaluator.parser.PrattParser;
import mathaid.calculator.base.evaluator.parser.Token;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.evaluator.parser.expression.scientific.Group;
import mathaid.calculator.base.evaluator.parser.expression.scientific.Name.Params;
import mathaid.calculator.base.evaluator.parser.parselet.Parselet;
import mathaid.calculator.base.typeset.SegmentBuilder;

/*
 * Date: 14 Sep 2022----------------------------------------------------------- 
 * Time created: 12:22:53---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.parselet.scientific------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: GroupParselet.java------------------------------------------------------ 
 * Class name: GroupParselet------------------------------------------------ 
 */
/**
 * A prefix {@code Parselet} for creating {@code Group} objects.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class GroupParselet implements
		Parselet<String, SegmentBuilder, EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params>, Params> {

	/*
	 * Most Recent Date: 14 Sep 2022-----------------------------------------------
	 * Most recent time created: 12:22:57--------------------------------------
	 */
	/**
	 * Creates a new {@code Group} object.
	 * <p>
	 * This calls the given {@code PrattParser} object's parse no-arg method then consumes the closing symbol specified by
	 * {@link CommonSyntax#precedenceDirector2()} and returns a {@code Group} object.
	 *
	 * @param alreadyParsedLeft  {@inheritDoc}
	 * @param yetToBeParsedToken {@inheritDoc}
	 * @param parser             {@inheritDoc}
	 * @param lexerReference     {@inheritDoc}
	 * @param syntax             {@inheritDoc}
	 * @param params             {@inheritDoc}
	 * @return the new {@code Group} object.
	 */
	@Override
	public EvaluatableExpression<Params> parse(EvaluatableExpression<Params> alreadyParsedLeft,
			Token<String> yetToBeParsedToken, PrattParser<EvaluatableExpression<Params>, Params> parser,
			Iterator<Token<String>> lexerReference,
			CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params> syntax,
			Params params) {
		EvaluatableExpression<Params> content = parser.parse(lexerReference, syntax, params);
		parser.consume(syntax.getType(syntax.precedenceDirector2()), lexerReference);
		return new Group(content, params);
	}

}
