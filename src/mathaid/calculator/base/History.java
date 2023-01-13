/**
 * 
 */
package mathaid.calculator.base;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mathaid.BaseException;
import mathaid.ExceptionMessage;
import mathaid.IndexBeyondLimitException;

/*
 * Date: 8 Apr 2021----------------------------------------------------------- 
 * Time created: 18:27:10---------------------------------------------------  
 * Package: mathaid.calculator.base------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: History.java------------------------------------------------------ 
 * Class name: History------------------------------------------------ 
 */
/**
 * An object that stores other object at runtime. It is primarily used by the
 * {@link mathaid.calculator.base.parser.CEvaluator CEvaluator} class to archive
 * both previous and current calculations.
 * <p>
 * Every {@code History} object has a given limit (which may be declared once
 * upon initialisation) which is the number of elements it can store, and an
 * entry represented by a {@code List} of {@code Map} objects containing
 * {@code java.util.Date} objects as keys and the given generic as it's
 * value(s). Each entry is registered with a given time stamp.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @param <T> the type of object to be archived.
 */
public class History<T> {

	/*
	 * Date: 9 Aug 2021-----------------------------------------------------------
	 * Time created: 10:48:20---------------------------------------------------
	 */
	/**
	 * Creates a {@code History} object with a limit of 100.
	 */
	public History() {
		this(100);
	}

	/*
	 * Date: 9 Aug 2021-----------------------------------------------------------
	 * Time created: 10:38:49---------------------------------------------------
	 */
	/**
	 * Creates a {@code History} object with a given limit which must be greater
	 * than 0 or an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param limit the given number of items to store in this object.
	 */
	public History(int limit) {
		if (limit <= 0)
			new mathaid.IllegalArgumentException(ExceptionMessage.LIMIT_ZERO);
		this.limit = limit;
		history = new ArrayList<>(this.limit);
	}

	/*
	 * Date: 9 Aug 2021-----------------------------------------------------------
	 * Time created: 10:54:24--------------------------------------------
	 */
	/**
	 * Appends the given data to this {@code History}. If the limit is reached, then
	 * the initial element is polled and the given element is appended.
	 * 
	 * @param data  the object to be recorded.
	 * @param clock t
	 * @return {@code false} if the appendage was not possible else returns
	 *         {@code true}.
	 */
	@SuppressWarnings("unused")
	public boolean record(T data, Clock clock) {
		if (history.size() >= limit)
			try {
				history.remove(0);
			} catch (IndexOutOfBoundsException e) {
				try {
					new IndexBeyondLimitException(0, limit);
				} catch (IndexOutOfBoundsException ex) {
				}
				return false;
			}

		HashMap<Date, T> hm = new HashMap<>(1);
		hm.put(Date.from(Instant.now(clock)), data);
		return history.add(hm);
	}

	/*
	 * Date: 9 Aug 2021-----------------------------------------------------------
	 * Time created: 11:02:00--------------------------------------------
	 */
	/**
	 * Records the given object as specified by {@link #record(Object, Clock)}.
	 * 
	 * @param data the object to be registered.
	 * @return {@code false} if the appendage was not possible else returns
	 *         {@code true}.
	 */
	public boolean record(T data) {
		return record(data, Clock.systemDefaultZone());
	}

	/*
	 * Date: 9 Aug 2021-----------------------------------------------------------
	 * Time created: 11:03:34--------------------------------------------
	 */
	/**
	 * Throws a {@code UnsupportedOperationException} because it is not intended to
	 * be implemented now. Users can extend this class to add functionality to this
	 * method.
	 * 
	 * @return nothing because it throws an exception before the return code is
	 *         executed.
	 * @throws RuntimeException specifically a
	 *                          {@code UnsupportedOperationException}.
	 */
	protected boolean record() {
		new BaseException(ExceptionMessage.METHOD_NOT_IMPLEMENTED, new UnsupportedOperationException());
		throw new UnsupportedOperationException();// For java formality
	}

	/*
	 * Date: 9 Aug 2021-----------------------------------------------------------
	 * Time created: 11:19:19--------------------------------------------
	 */
	/**
	 * Returns a {@code List} of {@code Map} containing a single key-value pair
	 * whereby a {@code Date} object is the key and the generic is the value.
	 * 
	 * @return a {@code List} of {@code Map} objects.
	 */
	public List<Map<Date, T>> getList() {
		return history;
	}

	/*
	 * Date: 9 Aug 2021-----------------------------------------------------------
	 * Time created: 11:22:29--------------------------------------------
	 */
	/**
	 * Gets the max number of objects that can be archived using this object.
	 * 
	 * @return the limit as specified by {@link #History(int)}
	 */
	public int getLimit() {
		return limit;
	}

	/*
	 * Most Recent Date: 9 Aug 2021-----------------------------------------------
	 * Most recent time created: 11:24:53--------------------------------------
	 */
	/**
	 * Returns a string representation of the list.
	 * 
	 * @return the toString method of {@link #getList()}.
	 */
	@Override
	public String toString() {
		return history.toString();
	}

	/**
	 * The list backing this class.
	 */
	private final List<Map<Date, T>> history;
	/**
	 * The limit specified by the user once at construction time. When this limit is
	 * reached, items may begin to be removed at the begining of the list.
	 */
	private final int limit;

}
