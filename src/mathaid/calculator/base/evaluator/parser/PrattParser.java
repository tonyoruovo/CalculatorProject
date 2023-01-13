/**
 * 
 */
package mathaid.calculator.base.evaluator.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression.ExpressionParams;
import mathaid.calculator.base.evaluator.parser.parselet.Parselet;
import mathaid.calculator.base.typeset.SegmentBuilder;

/*
 * Date: 4 Sep 2022----------------------------------------------------------- 
 * Time created: 08:08:01---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: PrattParser.java------------------------------------------------------ 
 * Class name: PrattParser------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class PrattParser<E extends EvaluatableExpression<P>, P extends ExpressionParams<P>>
		implements Parser<String, SegmentBuilder, E, PrattParser<E, P>, CommonSyntax<E, PrattParser<E, P>, P>, P> {

	/*
	 * Most Recent Date: 4 Sep 2022-----------------------------------------------
	 * Most recent time created: 08:36:51--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param src
	 * @param lexer
	 * @param syntax
	 * @return
	 */
	@Override
	public E parse(Iterator<Token<String>> lexer, CommonSyntax<E, PrattParser<E, P>, P> syntax, P params) {
		return parse(0, lexer, syntax, params);
	}

	/*
	 * Date: 4 Sep 2022-----------------------------------------------------------
	 * Time created: 08:48:35--------------------------------------------
	 */
	/**
	 * @param i
	 * @param src
	 * @param lexer
	 * @param syntax
	 * @return
	 */
	public E parse(int beginingPrecedence, Iterator<Token<String>> lexer,
			CommonSyntax<E, PrattParser<E, P>, P> syntax, P params) {
		Token<String> t = readAndPop(lexer);
		Parselet<String, SegmentBuilder, E, PrattParser<E, P>, CommonSyntax<E, PrattParser<E, P>, P>, P> prefix = syntax
				.getPrefixParselet(t.getType());
		
		if(prefix == null)
			throw new IllegalArgumentException(String.format("unable to parse \"%s\"", t.getName()));
		
		E left = prefix.parse(null, t, this, lexer, syntax, params);
		
		while(beginingPrecedence < getPrecedence(lexer, syntax)) {
			t = readAndPop(lexer);
			
			Parselet<String, SegmentBuilder, E, PrattParser<E, P>, CommonSyntax<E, PrattParser<E, P>, P>, P> infix  =
					syntax.getInfixParselet(t.getType());
			left = infix.parse(left, t, this, lexer, syntax, params);
		}
		
		return left;

	}
	
	public Token<String> consume(Type<String> expected, Iterator<Token<String>> lexer){
		Token<String> t = readAndPeek(0, lexer);
		if(!t.getType().equals(expected))
			throw new IllegalArgumentException();
		return readAndPop(lexer);
	}
	
	public boolean match(Type<String> expected, Iterator<Token<String>> lexer) {
		Token<String> t = readAndPeek(0, lexer);
		if(!t.getType().equals(expected))
			return false;
		readAndPop(lexer);
		return true;
	}

	/*
	 * Date: 4 Sep 2022----------------------------------------------------------- 
	 * Time created: 09:06:06--------------------------------------------
	 */
	/**
	 * @return
	 */
	private int getPrecedence(Iterator<Token<String>> lexer,
			CommonSyntax<E, PrattParser<E, P>, P> syntax) {
		Parselet<?, ?, ?, ?, ?, ?> parselet = syntax.getInfixParselet(readAndPeek(0, lexer).getType());
		if(parselet != null)
			return readAndPeek(0, lexer).getType().getPrecedence().getPrecedence();
		return 0;
	}

	/*
	 * Date: 4 Sep 2022-----------------------------------------------------------
	 * Time created: 08:52:49--------------------------------------------
	 */
	/**
	 * @return
	 */
	protected Token<String> readAndPop(Iterator<Token<String>> lexer) {
		readAndPeek(0, lexer);
		return stack.remove(0);
	}

	/*
	 * Date: 4 Sep 2022-----------------------------------------------------------
	 * Time created: 08:55:15--------------------------------------------
	 */
	/**
	 */
	protected Token<String> readAndPeek(int distance, Iterator<Token<String>> lexer) {
		while (distance >= stack.size())
			stack.add(lexer.next());
		return stack.get(distance);
	}

	protected final List<Token<String>> stack = new ArrayList<>();

}
