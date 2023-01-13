/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.scientific;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;
import mathaid.calculator.base.util.Arith;

/*
 * Date: 11 Sep 2022----------------------------------------------------------- 
 * Time created: 17:36:45---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression.scientific------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Postfix.java------------------------------------------------------ 
 * Class name: Postfix------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Postfix extends Name {

	/*
	 * Date: 11 Sep 2022-----------------------------------------------------------
	 * Time created: 17:37:12---------------------------------------------------
	 */
	/**
	 * @param name
	 * @param params
	 */
	public Postfix(EvaluatableExpression<Params> left, String name, Params params) {
		super(name, params);
		this.left = left;
	}

	public EvaluatableExpression<Params> getLeft() {
		return left;
	}

	/*
	 * Most Recent Date: 11 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:37:08--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param formatBuilder
	 */
	@Override
	public void format(SegmentBuilder formatBuilder) {
		left.format(formatBuilder);
		formatBuilder.append(Segments.operator(getName(), getName()));
	}

	/*
	 * Most Recent Date: 11 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:37:08--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public EvaluatableExpression<Params> evaluate() {
		EvaluatableExpression<Params> exp = left.evaluate();
		if (getName().equals("!"))
			if (isNumber(exp))
				return new Name(
						Arith.factorial(new BigDecimal(exp.getName()),
								new MathContext(getParams().getScale(), RoundingMode.HALF_EVEN)).toPlainString(),
						getParams());
		return new Postfix(exp, getName(), getParams());
	}

	private final EvaluatableExpression<Params> left;

}
