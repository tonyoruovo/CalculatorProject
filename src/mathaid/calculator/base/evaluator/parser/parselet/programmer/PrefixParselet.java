/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.parselet.programmer;

import java.util.Iterator;

import mathaid.calculator.base.evaluator.parser.CommonSyntax;
import mathaid.calculator.base.evaluator.parser.PrattParser;
import mathaid.calculator.base.evaluator.parser.Token;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression;
import mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression.Params;
import mathaid.calculator.base.evaluator.parser.expression.programmer.Prefix;
import mathaid.calculator.base.evaluator.parser.parselet.Parselet;
import mathaid.calculator.base.typeset.SegmentBuilder;

/*
 * Date: 1 Dec 2022----------------------------------------------------------- 
 * Time created: 14:31:14---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.parselet.programmer------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: PrefixParselet.java------------------------------------------------------ 
 * Class name: PrefixParselet------------------------------------------------ 
 */
/**
 * A prefix {@code Parselet} for creating {@code Prefix} objects.
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class PrefixParselet implements
		Parselet<String, SegmentBuilder, EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params>, Params> {

	/*
	 * Most Recent Date: 1 Dec 2022-----------------------------------------------
	 * Most recent time created: 14:31:58--------------------------------------
	 */
	/**
	 * Creates a new {@code Prefix} object after parsing the operand.
	 * <p>
	 * This calls the {@link PrattParser#parse(int) parse} method with the given token's precedence as it's argument and returns a
	 * {@link Prefix} object.
	 * 
	 * @param alreadyParsedLeft  unused. Can be left as <code>null</code>.
	 * @param yetToBeParsedToken the value that is (or part of) the operand.
	 * @param parser             {@inheritDoc}
	 * @param lexerReference     {@inheritDoc}
	 * @param syntax             {@inheritDoc}
	 * @param params             {@inheritDoc}
	 * @return a new {@code Prefix} object.
	 */
	@Override
	public PExpression parse(EvaluatableExpression<Params> alreadyParsedLeft, Token<String> yetToBeParsedToken,
			PrattParser<EvaluatableExpression<Params>, Params> parser, Iterator<Token<String>> lexerReference,
			CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params> syntax,
			Params params) {
		PExpression right = (PExpression) parser.parse(yetToBeParsedToken.getType().getPrecedence().getPrecedence(),
				lexerReference, syntax, params);
		return new Prefix(yetToBeParsedToken.getName(), right, params);
	}

}
