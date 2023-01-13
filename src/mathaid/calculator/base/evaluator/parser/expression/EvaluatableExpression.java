/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression;

import java.util.Map;

import mathaid.calculator.base.converter.AngleUnit;
import mathaid.calculator.base.evaluator.Evaluatable;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.util.Tuple.Couple;
import mathaid.functional.Supplier.Function;

/*
 * Date: 4 Sep 2022----------------------------------------------------------- 
 * Time created: 08:14:07---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: EvaluatableExpression.java------------------------------------------------------ 
 * Class name: EvaluatableExpression------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public abstract class EvaluatableExpression<P extends EvaluatableExpression.ExpressionParams<P>>
		implements Expression<SegmentBuilder>, Evaluatable<EvaluatableExpression<P>> {

	/*
	 * Date: 5 Sep 2022-----------------------------------------------------------
	 * Time created: 02:49:32---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base.evaluator.parser.expression.scientific---------------
	 * --------------------------------- Project:
	 * CalculatorProject------------------------------------------------ File:
	 * ExpressionParams.java------------------------------------------------------
	 * Class name: ExpressionParams------------------------------------------------
	 */
	/**
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 */
	public static interface ExpressionParams<T extends ExpressionParams<T>> {

		interface ResultType {
			int DECIMAL = 0;
		}

		int getResultType();

		String getDecimalPoint();

		String getIntSeparator();

		String getMantSeparator();

		int getIntGroupSize();

		int getMantGroupSize();

		int getRadix();
		
		/*
		 * Date: 10 Sep 2022-----------------------------------------------------------
		 * Time created: 16:30:10--------------------------------------------
		 */
		/**
		 * These include infinity --&gt; \\infty, pi --&gt; \\pi, e,
		 * 
		 * @return
		 */
		Map<String, Couple<String, Function<T, SegmentBuilder>>> getConstants();

		Map<String, Couple<String, Function<T, SegmentBuilder>>> getBoundVariables();

		String[] getDivisionString();

		String[] getMultiplicationString();
		
		/*
		 * Date: 12 Sep 2022-----------------------------------------------------------
		 * Time created: 09:31:50--------------------------------------------
		 */
		/**
		 * @return
		 */
		AngleUnit getTrig();

	}

	/*
	 * Date: 4 Sep 2022-----------------------------------------------------------
	 * Time created: 17:34:21---------------------------------------------------
	 */
	/**
	 */
	public EvaluatableExpression(P params) {
		this.params = params;
	}
	
	public abstract String getName();

	@Override
	public abstract boolean equals(Object o);

	@Override
	public abstract int hashCode();

	public P getParams() {
		return params;
	}

	private final P params;
}
