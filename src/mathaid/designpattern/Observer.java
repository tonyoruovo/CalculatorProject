/**
 * 
 */
package mathaid.designpattern;

/*
 * Date: 6 Apr 2021----------------------------------------------------------- 
 * Time created: 16:39:19---------------------------------------------------  
 * Package: mathaid.designpattern------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: Observer.java------------------------------------------------------ 
 * Class name: Observer------------------------------------------------ 
 */
/**
 * <p>
 * A receiver of updates of type <code>&lt;T&gt;</code> from a proposed
 * {@code Subject}.
 * </p>
 * <p>
 * An {@code Observer} object get informed by a {@code Subject} handler that
 * pushes out objects of the same type as &lt;T&gt;. The info can then be used
 * when {@code doAction()} is called. No handler will ever call
 * {@code doAction()}.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @param <T> the type of objects to be updated on
 * 
 */
@FunctionalInterface
public interface Observer<T> {

	/*
	 * Date: 23 Jul 2021-----------------------------------------------------------
	 * Time created: 16:21:44--------------------------------------------
	 */
	/**
	 * Called by the handler object, it informs this on the current state of the
	 * argument.
	 * 
	 * @param t the object to be updated
	 */
	void inform(T t);

	/*
	 * Date: 23 Jul 2021-----------------------------------------------------------
	 * Time created: 16:23:25--------------------------------------------
	 */
	/**
	 * <p>
	 * Consumes the info that was updated. This method is typically called after
	 * {@link #inform(Object)} has been called.
	 * </p>
	 * <p>
	 * Because of the JRE requirements of the annotation
	 * <code>{@literal @FunctionalInterface}</code>, this method is marked with the
	 * default modifier. However to fully apply the observer design patter logic,
	 * all codes that consume the value set by {@code inform(Object)} should be
	 * placed here.
	 * </p>
	 */
	default void doAction() {
	}
}
