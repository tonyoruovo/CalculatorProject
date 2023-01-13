/**
 * 
 */
package mathaid.calculator.base.evaluator;

import java.util.NavigableMap;
import java.util.TreeMap;

import mathaid.MomentString;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression.ExpressionParams;
import mathaid.calculator.base.typeset.Segment;

/*
 * Date: 17 Sep 2022----------------------------------------------------------- 
 * Time created: 18:56:02---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: DetailsList.java------------------------------------------------------ 
 * Class name: DetailsList------------------------------------------------ 
 */
/**
 * An abstract class that provides further info about a {@code Calculator}
 * within the {@code CalculatorManager} class. It contains 3 classes that each
 * represent 1 of the 3 modes of the {@code CalculatorManager} object. The
 * single method {@link #getLastDetails()} returns a {@code Map} that contains
 * the appropriate information as values and the heading to be displayed in the
 * details part of the calculator as the key.
 * </p>
 * <p>
 * A {@code DetailsList} should not throw any exception because it is not
 * directly controlled by the user, but when an exception is encountered, it may
 * abort the computation and instead display an error message in the index the
 * information was supposed to be.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public abstract class DetailsList<P extends ExpressionParams> implements Runnable {

	static class Converter extends DetailsList {

		/*
		 * Most Recent Date: 18 Sep 2022-----------------------------------------------
		 * Most recent time created: 14:25:00--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub

			super.run();
		}
	}

	@Override
	public void run() {
		src = null;
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setSource(Segment segment) {
//		this.src = Tuple.of(params, segment);
		this.src = segment;
		details.clear();
	}

	protected NavigableMap<MomentString, Segment> getLastDetails() {
		return details;
	}

	final NavigableMap<MomentString, Segment> details = new TreeMap<>();
//	Couple<P, Segment> src;
	Segment src;
}
