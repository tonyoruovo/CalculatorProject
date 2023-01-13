/**
 * 
 */
package mathaid.js;

import java.io.IOException;

import mathaid.js.JSSnippet.Type;

/*
 * Date: 20 Aug 2022----------------------------------------------------------- 
 * Time created: 00:53:12---------------------------------------------------  
 * Package: js------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: JSNumber.java------------------------------------------------------ 
 * Class name: JSNumber------------------------------------------------ 
 */
/**
 * A wrapper for Javascript's boolean value type. This a simple (non-compound)
 * type.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class JSNumber extends JSValue<JSNumber> implements Comparable<JSNumber> {

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 11:24:29---------------------------------------------------
	 */
	/**
	 * Constant for the max integer value a Javascript number is allowed to have
	 */
	private static final long MAX = 9007199254740991L;// (2^53)-1
	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 11:27:22---------------------------------------------------
	 */
	/**
	 * Constant for the minimum integer value a Javascript number is allowed to have
	 */
	private static final long MIN = -MAX;

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 14:02:35--------------------------------------------
	 */
	/**
	 * checks whether {@code val} can be used as an integer within Javascript's
	 * integer range.
	 * 
	 * @param val the value to be checked as a double
	 * @return {@code true} if
	 *         <code>val &lt;= {@value #MAX} || val &gt;= {@value #MIN}</code> and
	 *         <code>Math.floor(val) == Math.ceil(val)</code> otherwise returns
	 *         {@code false}
	 */
	static boolean isNotOverflow(double val) {
		return (Math.floor(val) == Math.ceil(val)) && ((!(val < 0 && val < MIN)) || (!(val > 0 && val > MAX)));
	}

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 14:02:35--------------------------------------------
	 */
	/**
	 * checks whether {@code val} can be used as an integer within Javascript's
	 * integer range.
	 * 
	 * @param val the value to be checked as a long
	 * @return {@code true} if
	 *         <code>val &lt;= {@value #MAX} || val &gt;= {@value #MIN}</code> and
	 *         <code>Math.floor(val) == Math.ceil(val)</code> otherwise returns
	 *         {@code false}
	 */
	static boolean isNotOverflow(long val) {
		return ((!(val < 0 && val < MIN)) || (!(val > 0 && val > MAX)));
	}

	/*
	 * Date: 20 Aug 2022-----------------------------------------------------------
	 * Time created: 00:53:12---------------------------------------------------
	 */
	/**
	 * Creates a {@code JSNumber} object from the argument.
	 * 
	 * @param val the value that this type wraps.
	 */
	public JSNumber(long val) {
		dVal = lVal = val;
	}

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 11:28:27---------------------------------------------------
	 */
	/**
	 * Creates a {@code JSNumber} object from the argument.
	 * 
	 * @param val the value that this type wraps.
	 */
	public JSNumber(double val) {
		dVal = val;
		lVal = (long) dVal;
	}

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 11:35:06---------------------------------------------------
	 */
	/**
	 * Creates a {@code JSNumber} that wraps the value {@code NaN}
	 */
	private JSNumber() {
		dVal = Double.NaN;
		lVal = Long.MIN_VALUE;
	}

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 11:35:42--------------------------------------------
	 */
	/**
	 * Gets the wrapped value.
	 * 
	 * @return the wrapped double
	 */
	public double getAsDouble() {
		return dVal;
	}

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 11:36:23--------------------------------------------
	 */
	/**
	 * Gets the wrapped value.
	 * 
	 * @return the wrapped long
	 */
	public long getAsLong() {
		return lVal;
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 01:04:16--------------------------------------
	 */
	/**
	 * Always returns the number type.
	 * 
	 * @return the number type
	 * @see Type#NUMBER
	 */
	@Override
	public Type getType() {
		return Type.NUMBER;
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 01:04:16--------------------------------------
	 */
	/**
	 * Writes the number wrapped within as a string into the given parser.
	 * 
	 * @param parser    an {@code Appendable} such as a {@code StringBuilder}.
	 * @param numOfTabs not relevent in this case because this type is a simple (or
	 *                  atomic) type.
	 */
	@Override
	public void parseToScript(Appendable parser, int tabs) {
		try {
			parser.append(Math.floor(dVal) == Math.ceil(dVal) && isNotOverflow(dVal) ? String.valueOf(lVal)
					: String.valueOf(dVal));
		} catch (@SuppressWarnings("unused") IOException e) {
		}
	}

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 11:39:33---------------------------------------------------
	 */
	/**
	 * Field for the wrapped value as floating point
	 */
	private final double dVal;
	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 11:41:03---------------------------------------------------
	 */
	/**
	 * Field for the wrapped value as integer
	 */
	private final long lVal;

	/*
	 * Most Recent Date: 26 Aug 2022-----------------------------------------------
	 * Most recent time created: 11:41:57--------------------------------------
	 */
	/**
	 * Gets the hash code of {@link #getType()}
	 * 
	 * @return the hash code of {@link #getType()}
	 */
	@Override
	public int hashCode() {
		return Math.floor(dVal) == Math.ceil(dVal) ? Long.hashCode(lVal) : Double.hashCode(dVal);
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 01:15:57--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param o the reference object with which to compare
	 * @return {@code true} if the argument is an {@link JSNumber} type and
	 *         {@code getType() == o.getType()}
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof JSNumber) {
			JSNumber n = (JSNumber) o;

			return Math.floor(dVal) == Math.ceil(dVal) ? lVal == n.lVal : dVal == n.dVal;
		}
		return false;
	}

	/*
	 * Most Recent Date: 22 Aug 2022-----------------------------------------------
	 * Most recent time created: 00:40:27--------------------------------------
	 */
	/**
	 * Returns a {@code JSNumber} object whose
	 * {@link #parseToScript(Appendable, int)} method is delegated to the
	 * argument's.
	 * 
	 * @param function a {@code JSFunction} which is assumed to have a return value
	 *                 of the same type as {@code this}
	 * @return a {@code JSNumber} which delegates it's
	 *         {@link #parseToScript(Appendable, int)} to the argument.
	 * @see JSValue#byFunction(JSFunction)
	 */
	@Override
	public JSNumber byFunction(JSFunction function) {
		return new JSNumber() {
			@Override
			public void parseToScript(Appendable p, int i) {
				function.parseToScript(p, i);
			}
		};
	}

	/*
	 * Most Recent Date: 22 Aug 2022-----------------------------------------------
	 * Most recent time created: 03:18:21--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param o the reference of the value to be compared
	 * @return {@inheritDoc}
	 */
	@Override
	public int compareTo(JSNumber o) {
		return Math.floor(dVal) == Math.ceil(dVal) ? Long.compare(lVal, o.lVal) : Double.compare(dVal, o.dVal);
	}
}
