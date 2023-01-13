/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.scientific;

import mathaid.calculator.base.evaluator.Calculator;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.typeset.Digits;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;

/*
 * Date: 11 Sep 2022----------------------------------------------------------- 
 * Time created: 17:06:32---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression.scientific------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Prefix.java------------------------------------------------------ 
 * Class name: Prefix------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Prefix extends Name {

	/*
	 * Date: 11 Sep 2022-----------------------------------------------------------
	 * Time created: 17:06:36---------------------------------------------------
	 */
	/**
	 * @param name
	 * @param params
	 */
	public Prefix(String name, EvaluatableExpression<Params> right, Params params) {
		super(name, params);
		this.right = right;
	}

	public EvaluatableExpression<Params> getRight() {
		return right;
	}
	
	private boolean isNegation() {
		return getName().equals("-");
	}
	
	private boolean isPlus() {
		return getName().equals("+");
	}

	/*
	 * Most Recent Date: 11 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:13:23--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param formatBuilder
	 */
	@Override
	public void format(SegmentBuilder formatBuilder) {
		// exponential function
		if (getName().length() == 1 && getName().equals("E")) {
			SegmentBuilder sb = new SegmentBuilder();
			right.format(sb);
			Segments.pow(Segments.constant("e", "e"), sb.toSegment());
			return;
		} else if (getName().equals(".")) {
			formatBuilder.append(Digits.integer('0', Calculator.fromParams(getParams(), getParams().getNumOfRepeats())));
			formatBuilder.append(Digits.point(getParams().getDecimalPoint().charAt(0)));
			right.format(formatBuilder);
//			formatBuilder.append(Segments.toSegment(right.getName(), getParams().getDecimalPoint(),
//					getParams().getRecurringType(), 10, getParams().getIntSeparator(), getParams().getMantSeparator(),
//					getParams().getIntGroupSize(), getParams().getMantGroupSize(), getParams().getNumOfRepeats()));
			return;
		}
		formatBuilder.append(isNegation() ? Digits.prefixMinus() : isPlus() ? Digits.prefixPlus() : Segments.operator(getName(), getName()));
		right.format(formatBuilder);
	}

	/*
	 * Most Recent Date: 11 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:13:23--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public EvaluatableExpression<Params> evaluate() {
		EvaluatableExpression<Params> right = this.right.evaluate();
//		switch (getName()) {
//		case "-":
//		case ".":
//		case "+":// +2.5, +a, +(x-y)
//		case "E":// E^2
//		default:
//			return new Name(name() + right.name()).evaluate();
//		}
		return new Prefix(getName(), right, getParams());
	}

	private final EvaluatableExpression<Params> right;
}
