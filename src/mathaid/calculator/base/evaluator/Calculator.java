/**
 * 
 */
package mathaid.calculator.base.evaluator;

import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression.ExpressionParams;
import mathaid.calculator.base.gui.GUIComponent;
import mathaid.calculator.base.gui.KeyAction;
import mathaid.calculator.base.gui.KeyBoard;
import mathaid.calculator.base.typeset.DigitPunc;
import mathaid.calculator.base.typeset.SegmentBuilder;

/*
 * Date: 17 Sep 2022----------------------------------------------------------- 
 * Time created: 18:37:03---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Calculator.java------------------------------------------------------ 
 * Class name: Calculator------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface Calculator<T extends GUIComponent<T>, F> extends Evaluator<SegmentBuilder> {
	
	int SHIFT_MASK = KeyBoard.GLOBAL_MODIFIER_MASK;
	
	int INPUT_TYPE_MASK = 0x3;
	
	static DigitPunc fromParams(ExpressionParams<?> p, int nor) {
		return new DigitPunc(p.getIntSeparator(), p.getDecimalPoint(), p.getMantSeparator(), p.getIntGroupSize(), p.getMantGroupSize(), nor);
	}
	
	int getModifier();
	
	DetailsList<?> getDetails();
	
	KeyBoard<T, F> getRemoteControl();
	
	KeyAction<T, String> getModKeys();
}
