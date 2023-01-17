/**
 * 
 */
package mathaid;

/*
 * Date: 17 Sep 2022----------------------------------------------------------- 
 * Time created: 19:13:27---------------------------------------------------  
 * Package: mathaid------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Moment.java------------------------------------------------------ 
 * Class name: Moment------------------------------------------------ 
 */
/**
 * An object used to track time-related actions, method calls and objects. It
 * exists precisely for use as keys in {@link java.util.TreeMap TreeMap} objects, when insertion
 * order is warranted/desired. The ideal way to implement this is at the
 * constructor when the object is being created so as to sort of "brand" the
 * object with the "manufacturing date".
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 * 
 * @see MomentString
 */
public interface Moment extends Comparable<Moment> {

	/*
	 * Date: 17 Sep 2022-----------------------------------------------------------
	 * Time created: 19:44:44--------------------------------------------
	 */
	/**
	 * the value returned should be an already saved state such as a {@code long}
	 * field which is expected to be made {@code final} and assigned the value of
	 * {@code System.nanoTime()} during initialisation
	 * 
	 * @return the moment this object was initialised.
	 */
	long getMoment();

	/*
	 * Most Recent Date: 17 Sep 2022-----------------------------------------------
	 * Most recent time created: 19:41:39--------------------------------------
	 */
	/**
	 * A comparative function for 2 moment string in which both are compared down to
	 * the last millisecond (or even nanosecond).
	 * 
	 * @param m a reference {@code Moment} object to be compare with {@code this}
	 * @return a negative integer, zero, or a positive integer as this
	 *         {@code Moment} is less than, equal to, or greater than the specified
	 *         {@code Moment}
	 */
	@Override
	default int compareTo(Moment m) {
		return Long.valueOf(getMoment() - m.getMoment()).intValue();
	}

	/*
	 * Date: Jan 16, 2023
	 * ----------------------------------------------------------- Time created:
	 * 9:14:04 AM ---------------------------------------------------
	 */
	/**
	 * A unique value which may enable use in hash tables and maps
	 * 
	 * @return the hash code of {@code this}
	 */
	@Override
	int hashCode();

	/*
	 * Date: Jan 16, 2023
	 * ----------------------------------------------------------- Time created:
	 * 9:15:08 AM ---------------------------------------------------
	 */
	/**
	 * Compares the given object with {@code this} and returns {@code true} if it is
	 * equal to this {@code Moment} object, otherwise returns {@code false}.
	 * 
	 * @param o a reference to the object to be compared to {@code this}
	 * @return true if this object is the same as the o parameter; false otherwise.
	 */
	@Override
	boolean equals(Object o);
}
