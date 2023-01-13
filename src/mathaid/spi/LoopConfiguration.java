/**
 * 
 */
package mathaid.spi;

import java.util.function.Function;

import mathaid.ExceptionMessage;
import mathaid.NullException;

/*
 * Date: 3 Sep 2020-----------------------------------------------------------
 * Time created: 20:33:16--------------------------------------------
 * Package: mathaid.calculator.base.spi------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: LoopConfiguration.java------------------------------------------------------ 
 * Class name: LoopConfiguration------------------------------------------------ 
 */
/**
 * Utility class for the implementation of Floyd's Cycle Detection Algorithm.
 * This represents the configuration of the loop within Floyd's cycle detection
 * algorithm. It consists of 2 fields, an int field that represents the length
 * of the non-recurring part and another int field that represents the length of
 * the loop.
 * 
 * @author Oruovo Anthony Etineakpopha
 *
 */
public class LoopConfiguration {

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 20:33:16--------------------------------------------
	 */
	/**
	 * Constructs a {@code LoopConfiguration} object using a prefix length and a
	 * loop length
	 * 
	 * @param prefixLength the prefix length
	 * @param loopLength   the number of loops
	 */
	private LoopConfiguration(int prefixLength, int loopLength) {
		this.prefixLength = prefixLength;
		this.loopLength = loopLength;
	}

	/**
	 * Length of the non-repetitive part
	 */
	private final int prefixLength;
	/**
	 * length of the loop
	 */
	private final int loopLength;

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 20:33:16--------------------------------------------
	 */
	/**
	 * The prefix start position in floyd's cycle.
	 * 
	 * @return the prefix length
	 */
	public int getPrefixLength() {
		return prefixLength;
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 20:33:16--------------------------------------------
	 */
	/**
	 * The number of loops of a floyd's cycle
	 * 
	 * @return the number of loops
	 */
	public int getLoopLength() {
		return loopLength;
	}

	/*
	 * Date: 3 Sep 2020-----------------------------------------------------------
	 * Time created: 20:33:16--------------------------------------------
	 */
	/**
	 * Uses the cycle detection algorithm to detect the recurring values of the
	 * {@code Function} values.
	 * 
	 * @param <T>   A type of comparable value returned by the input
	 *              {@code Function}.
	 * @param f     a {@code Function} object.
	 * @param start represents the first value recorded by the detection algorithm.
	 * @return a {@code LoopConfiguration} object.
	 */
	public static <T extends Comparable<T>> LoopConfiguration detectPrefixLength(Function<T, T> f, T start) {
		if (f == null)
			new NullException(Function.class);
		if (start == null)
			new NullException(ExceptionMessage.OBJECT_IS_NULL, new NullPointerException(), "start");
		T slow = f.apply(start);
		T fast = f.apply(f.apply(start));

//		while (!slow.equals(fast)) {
		while (slow.compareTo(fast) != 0) {
			slow = f.apply(slow);
			fast = f.apply(f.apply(fast));
		}

		slow = start;
		int prefixLength = 0;

//		while (!slow.equals(fast)) {
		while (slow.compareTo(fast) != 0) {
			slow = f.apply(slow);
			fast = f.apply(fast);
			prefixLength++;
		}

		int loopLength = 1;
		fast = f.apply(fast);

//		while (!slow.equals(fast)) {
		while (slow.compareTo(fast) != 0) {
			fast = f.apply(fast);
			loopLength++;
		}

		return new LoopConfiguration(prefixLength, loopLength);
	}

}