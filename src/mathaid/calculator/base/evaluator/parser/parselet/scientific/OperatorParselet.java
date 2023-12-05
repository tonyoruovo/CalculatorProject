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
import mathaid.calculator.base.evaluator.parser.expression.scientific.Operator;
import mathaid.calculator.base.evaluator.parser.parselet.Parselet;
import mathaid.calculator.base.typeset.SegmentBuilder;

/*
 * Date: 14 Sep 2022----------------------------------------------------------- 
 * Time created: 11:52:50---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.parselet.scientific------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: OperatorParselet.java------------------------------------------------------ 
 * Class name: OperatorParselet------------------------------------------------ 
 */
/**
 * An infix {@code Parselet} for creating expression that are {@link Operator} expressions.
 * <p>
 * This class supports associativity as one of it's features. Operator associativity defines whether an operator is left or
 * right-associative. A right associative operator will have a slightly lower precedence than it equivalent by subtracting
 * {@code 1} from the precedence during parsing. This means that operators with the same precedence but with different
 * associativity will be ordered such that left-associative operators comes first. e.g in the expression {@code 2 ^ 3 + 5},
 * {@code ^} is defined as a right-associative binary operator hence the oder of operations goes like this: {@code 2 ^ (3 + 5)}
 * instead of the linear {@code (2 ^ 3) + 5}
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class OperatorParselet implements
		Parselet<String, SegmentBuilder, EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params>, Params> {

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 09:53:33 ---------------------------------------------------
	 */
	/**
	 * Constructor for creating an {@code OperatorParselet} by specifying it's
	 * right-associative<span style="font-style:italic">ness</span>.
	 * 
	 * @param right {@code true} if this is right-associative.
	 */
	public OperatorParselet(boolean right) {
		this.right = right;
	}

	/*
	 * Most Recent Date: 14 Sep 2022-----------------------------------------------
	 * Most recent time created: 11:53:10--------------------------------------
	 */
	/**
	 * Parses {@code yetToBeParsedToken} into an {@code EvaluatableExpression} and returns a new {@code Operator} objects as the
	 * result.
	 * <p>
	 * The {@linkplain mathaid.calculator.base.evaluator.parser.Type#getPrecedence precedence} of {@code yetToBeParsedToken} will be lowered by {@code 1} and then used to
	 * parse the remaining tokens to the right. This is done if this is set as right-associative.
	 * 
	 * @param alreadyParsedLeft {@inheritDoc}
	 * @param yetToBeParsedToken {@inheritDoc}
	 * @param parser {@inheritDoc}
	 * @param syntax {@inheritDoc}
	 * @param params {@inheritDoc}
	 * @param lexerReference {@inheritDoc}
	 * @return a new {@code Operator} object.
	 */
	@Override
	public EvaluatableExpression<Params> parse(EvaluatableExpression<Params> alreadyParsedLeft,
			Token<String> yetToBeParsedToken, PrattParser<EvaluatableExpression<Params>, Params> parser,
			Iterator<Token<String>> lexerReference,
			CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params> syntax,
			Params params) {
		int p = yetToBeParsedToken.getType().getPrecedence().getPrecedence() - (right ? 1 : 0);
		EvaluatableExpression<Params> right = parser.parse(p, lexerReference, syntax, params);
		return new Operator(alreadyParsedLeft, yetToBeParsedToken.getName(), right, params);
	}

	/**
	 * A check for right associativity. {@code true} for right associative and {@code false} for left associative.
	 */
	private final boolean right;

}
