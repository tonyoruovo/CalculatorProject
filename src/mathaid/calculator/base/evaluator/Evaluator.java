/**
 * 
 */
package mathaid.calculator.base.evaluator;

/*
 * Date: 3 Sep 2022----------------------------------------------------------- 
 * Time created: 08:57:39---------------------------------------------------  
 * Package: calculator------------------------------------------------ 
 * Project: InputTestCode------------------------------------------------ 
 * File: Evaluator.java------------------------------------------------------ 
 * Class name: Evaluator------------------------------------------------ 
 */
/**
 * An object that evaluates expressions (strings) and returns a result based on
 * the data received. It is expected that all the options necessary for the
 * evaluation of the argument are present in the argument itself.
 * <p>
 * It is expected that this interface be implemented as immutable (or at least
 * relatively immutable) such that all arguments passed to {@link #evaluate}
 * will not change the way {@code evaluate} works. This will be consistent with
 * the intended behaviour of this interface as it is meant to be "pass-through"
 * and "stateless" as it concerns expressions, such that every evaluation
 * produce consistent results. If a mutative evaluation is required, then one
 * should use {@link Evaluatable} instead.
 * <p>
 * The difference between {@link Evaluatable} and {@link Evaluator} is the same
 * as the difference between {@link Comparable} and {@link java.util.Comparator
 * Comparator}. While the former <em>IS A</em>, the latter <em>HAS A</em>.
 * 
 * @param <T> The type of result returned by {@link #evaluate} when called.
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface Evaluator<T> {
	/**
	 * Computes the argument (which is an expression) and returns a corresponding
	 * result for it.
	 * 
	 * @param expression the value to be evaluated.
	 *                   <p>
	 *                   This is an external data which may not become part of the
	 *                   state of this evaluator. Infact it is expected that this is
	 *                   not a mutative operation and as such, when this method
	 *                   returns, this argument may not be retrieved from this
	 *                   object anymore. However implementors may have a history
	 *                   object to store expression purely for re-evaluation
	 *                   purposes an not for any purpose relating to the direct
	 *                   interference with the behaviour of this method. In other
	 *                   words, this argument will not change the way this method
	 *                   works.
	 * @return the result computed from the argument.
	 * @throws RuntimeException if there is a syntax, overflow etc error
	 *                          encountered.
	 */
	T evaluate(String expression) throws RuntimeException;
	
}
