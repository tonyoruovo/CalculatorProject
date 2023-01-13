/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.programmer;

import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;

/*
 * Date: 18 Oct 2022----------------------------------------------------------- 
 * Time created: 08:15:59---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression.programmer------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Postfix.java------------------------------------------------------ 
 * Class name: Postfix------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Postfix extends Name {
	public Postfix(PExpression left, String name, Params params) {
		super(name, params, String.class);
		this.left = left;
	}

	public PExpression getLeft() {
		return left;
	}

	@Override
	public void format(SegmentBuilder f) {
		left.format(f);
		f.append(Segments.operator(getName(), getName()));
	}

	@Override
	public Name evaluate() {
		return new Postfix(left.evaluate(), getName(), getParams());
	}

	private final PExpression left;
}
