package mathaid.calculator.base.evaluator;

import java.util.NavigableMap;

import mathaid.MomentString;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression.ExpressionParams;
import mathaid.calculator.base.typeset.LinkedSegment;

/*
 * Date: 12 Nov 2023 -----------------------------------------------------------
 * Time created: 17:04:17 ---------------------------------------------------
 * Package: mathaid.calculator.base.evaluator ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: Result.java ------------------------------------------------------
 * Class name: Result ------------------------------------------------
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Result {
	
	public static interface Processed extends Input {
		ExpressionParams<?> getParams();
	}
	/*
	 * Date: 12 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:28:15 ---------------------------------------------------
	 * Package: mathaid.calculator.base.evaluator ------------------------------------------------
	 * Project: CalculatorProject ------------------------------------------------
	 * File: Result.java ------------------------------------------------------
	 * Class name: Details ------------------------------------------------
	 */
	/**
	 * An interface that provides further info about results from an {@link Evaluator}
	 * within the {@link BasicCalculator} class. It contains 3 objects that each
	 * represent 1 of the 3 modes of the Calculator namely (Scientific, Programmer and Converter). The single
	 * method {@link #getDetails()} returns a {@code Map} that contains the
	 * appropriate information as values and the heading to be displayed in the
	 * details part of the calculator as the key.
	 * <p>
	 * All {@code Details} objects are elements of {@link mathaid.calculator.base.evaluator.Details} objects
	 * within the calculator which implement the {@link java.util.concurrent.Flow.Subscriber} interface so
	 * as to be registered into a {@code BasicCalculator} object using
	 * {@link BasicCalculator#subscribe}. When they have been
	 * added as stated previously, then they can be notified of results from
	 * calculations. Infact, their {@link mathaid.calculator.base.evaluator.Details#onNext onNext} method is called by the
	 * evaluator to update the output value. Once this is done, it triggers an
	 * evaluating mechanism waiting to compute a set of results that will be
	 * displayed in the "details" part of the calculator. A valid
	 * {@code Details} object can be can be retrieved via
	 * {@link mathaid.calculator.base.evaluator.Details#details}.
	 * <p>
	 * A {@code Details} should not throw any exception because it is not
	 * directly controlled by the user, but when an exception is encountered, it may
	 * abort the computation and instead display an error message in the index the
	 * information was supposed to be.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 */
	public static interface Details {

		/*
		 * Date: 11 Aug 2021-----------------------------------------------------------
		 * Time created: 09:49:26--------------------------------------------
		 */
		/**
		 * Returns an unmodifiable {@code Map} of the details. This map may be empty if
		 * called manually.
		 * <p>
		 * The contents of the map for {@code Details} objects is dependent on the
		 * internal implementation, but the general concept is that the keys are strings
		 * that subtitles the value and the values are further calculations made on
		 * updated result appropriate to a certain context. For example: If
		 * <code>200</code> is a result updated to this class, then this map may contain
		 * a key-value pair such as "Prime factors" and then TeX notation of the factors
		 * of {@code 200} respectively.
		 * </p>
		 * 
		 * @return an unmodifiable {@code Map} containing further calculations upon the
		 *         given updated result or an empty map if no update was received.
		 */
		NavigableMap<MomentString, LinkedSegment> getDetails(ExpressionParams<?> p, LinkedSegment src);
	}

}
