/*
 * Date: Jan 18, 2023 -----------------------------------------------------------
 * Time created: 2:13:47 PM ---------------------------------------------------
 */
/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.converter;

import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;

/*
 * Date: Jan 18, 2023 -----------------------------------------------------------
 * Time created: 2:13:47 PM ---------------------------------------------------
 * Package: mathaid.calculator.base.evaluator.parser.expression.converter ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: CExpression.java ------------------------------------------------------
 * Class name: CExpression ------------------------------------------------
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public abstract class CExpression extends EvaluatableExpression<CExpression.Params> {

	public static interface Params extends EvaluatableExpression.ExpressionParams<Params> {
		public static interface ResultType extends EvaluatableExpression.ExpressionParams.ResultType {
			int FRACTION = 1;

			int ANGLE = 3;
			int AREA = 4;
			int CURRENCY = 5;
			int DATA = 6;
			int DISTANCE = 7;
			int ENERGY = 8;
			int FORCE = 9;// Vector
			int FREQUENCY = 10;
			int FUEL = 11;
			int MASS = 12;
			int POWER = 13;
			int PRESSURE = 14;
			int SPEED = 15;
			int TEMPERATURE = 16;
			int TEMPORAL = 17;
			int TORQUE = 18;
			int VOLUME = 19;
		}
		
		int getTo();

		int getRecurringType();// use one of Segment.getType()

		int getNumOfRepeats();

		@Override
		default int getRadix() {
			return 10;
		}
	}
	
	/*
	 * Date: Jan 18, 2023 -----------------------------------------------------------
	 * Time created: 3:25:11 PM ---------------------------------------------------
	 */
	/**
	 * @param params
	 */
	public CExpression(Params params) {
		super(params);
	}
	
	public abstract Array evaluate();

}
