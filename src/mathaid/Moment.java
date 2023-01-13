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
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
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
	 * {@inheritDoc}
	 * 
	 * @param m
	 * @return
	 */
	@Override
	default int compareTo(Moment m) {
		return Long.valueOf(getMoment() - m.getMoment()).intValue();
	}

	@Override
	int hashCode();

	@Override
	boolean equals(Object o);
}
