/**
 * 
 */
package mathaid.designpattern;

/*
 * Date: 4 Aug 2020----------------------------------------------------------- 
 * Time created: 08:37:20---------------------------------------------------  
 * Package: mathaid.calculator.base------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: Subject.java------------------------------------------------------ 
 * Class name: Subject------------------------------------------------ 
 */
/**
 * <p>
 * A disperser of info in the observer design pattern. Its' primary purpose is
 * to register (and unregister if needed) and update clients
 * (<code>Observer</code> objects).
 * </p>
 * <p>
 * All the {@link Observer} objects in the {@code Subject} should implement the
 * {@link Object#hashCode()} to clearly differentiate between the objects inside
 * a {@code List}. An alternative approach will be to extend the
 * {@link Observer} interface and include a method that return a
 * {@link java.util.UUID uuid} object so as to provide differentiation.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @param <T> the type of update the {@code Observer} clients will receive
 * 
 */
public interface Subject<T> {

	/*
	 * Date: 23 Jul 2021-----------------------------------------------------------
	 * Time created: 15:51:19--------------------------------------------
	 */
	/**
	 * Adds this {@code Observer} into it's list of clients
	 * 
	 * @param o the {@code Observer} to be added
	 */
	void register(Observer<T> o);

	/*
	 * Date: 23 Jul 2021-----------------------------------------------------------
	 * Time created: 15:52:16--------------------------------------------
	 */
	/**
	 * Removes this {@code Observer} from the list of clients.
	 * 
	 * @param o the {@code Observer} to be removed
	 */
	void unRegister(Observer<T> o);

	/*
	 * Date: 23 Jul 2021-----------------------------------------------------------
	 * Time created: 15:57:35--------------------------------------------
	 */
	/**
	 * Updates each {@code Observer} in the client list by calling it's
	 * {@code inform()} method
	 */
	void update();
}
