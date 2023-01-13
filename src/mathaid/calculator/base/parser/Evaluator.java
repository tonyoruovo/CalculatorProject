/**
 * 
 */
package mathaid.calculator.base.parser;

/*
 * Date: 21 Apr 2020----------------------------------------------------
 * Time created: 18:54:56--------------------------------------------
 * Package: mathaid.calculator-----------------------------------------
 * Project: LatestPoject2-----------------------------------------
 * File: Evaluator.java-----------------------------------------------
 * Class name: Evaluator-----------------------------------------
 */
/**
 * An {@code Evaluator} evaluates expressions of type <code>&lt;T&gt;</code>.
 * <p>
 * An {@code Evaluator} calls evaluate on an object that represents input and
 * returns a result.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @param <T> the type of the evaluation result
 */
@FunctionalInterface
public interface Evaluator<T> {

	/*
	 * Date: 19 Jun 2021-----------------------------------------------------------
	 * Time created: 09:23:20--------------------------------------------
	 */
	/**
	 * Calculates some input and returns a result of type <code>T</code>
	 * 
	 * @return an evaluated result of type {@code T}
	 */
	T evaluate();
}
