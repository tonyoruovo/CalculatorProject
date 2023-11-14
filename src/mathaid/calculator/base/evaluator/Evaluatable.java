/**
 * 
 */
package mathaid.calculator.base.evaluator;

/*
 * Date: 3 Sep 2022----------------------------------------------------------- 
 * Time created: 09:00:05---------------------------------------------------  
 * Package: calculator------------------------------------------------ 
 * Project: InputTestCode------------------------------------------------ 
 * File: Evaluatable.java------------------------------------------------------ 
 * Class name: Evaluatable------------------------------------------------ 
 */
/**
 * An {@code Evaluatable} evaluates expressions.
 * <p>
 * An {@code Evaluatable} calls evaluate on an object that represents input and
 * returns a result of type <code>&lt;T&gt;</code>.
 * 
 * An Object that can be evaluated for a resultant value.
 * <p>
 * All data necessary for a correct evaluation should be available in the fields
 * of this object. It is recommended that no exceptions be thrown by the
 * {@link #evaluate}, but rather a special value (of the same type as the given
 * generic) be returned instead. This will help maintain consistent behaviour as
 * the users of this interface will not expect an exception. However, Errors
 * such as {@link StackOverflowError}, {@link OutOfMemoryError} etc are not
 * expected to be caught.
 * 
 * <p>
 * The difference between {@link Evaluatable} and {@link Evaluator} is the same
 * as the difference between {@link Comparable} and {@link java.util.Comparator
 * Comparator}. While the former <em>IS A</em>, the latter <em>HAS A</em>.
 * 
 * @param <T> The type of result returned by {@link #evaluate} when called.
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface Evaluatable<T> {
	/**
	 * Evaluates this object and returns the given result. This method may or may
	 * not throw an exception, However, it is recommended that any exception that
	 * the evaluation encounters be caught, returning a special value signifying
	 * that an exception was encountered.
	 * 
	 * @return an evaluated result of type {@code T} i.e a value that is related to the evaluation and this object.
	 */
	T evaluate();
}
