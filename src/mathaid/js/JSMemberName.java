/**
 * 
 */
package mathaid.js;

import java.io.IOException;

/*
 * Date: 20 Aug 2022----------------------------------------------------------- 
 * Time created: 09:30:27---------------------------------------------------  
 * Package: js------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: JSMemberName.java------------------------------------------------------ 
 * Class name: JSMemberName------------------------------------------------ 
 */
/**
 * A special type of {@code JSSnippet} whose main purpose is to be used as a key
 * in a {@link java.util.SortedMap} (such as a {@link java.util.TreeMap}) to
 * preserve insertion order of a string used as a field name for a
 * {@code JSObject}.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 * @see JSSnippet#isJavascriptIdentifier(String)
 */
public final class JSMemberName implements JSSnippet, CharSequence, Comparable<JSMemberName> {

	/*
	 * Date: 27 Aug 2022-----------------------------------------------------------
	 * Time created: 08:21:57---------------------------------------------------
	 */
	/**
	 * Field for the identifier of this member
	 */
	private final String value;
	/*
	 * Date: 27 Aug 2022-----------------------------------------------------------
	 * Time created: 08:22:00---------------------------------------------------
	 */
	/**
	 * Field for the unique id for the time of insertion used in the comparable to
	 * allow for preservation of insertion order.
	 */
	private final long declareTime;

	/*
	 * Date: 20 Aug 2022-----------------------------------------------------------
	 * Time created: 09:30:27---------------------------------------------------
	 */
	/**
	 * Creates a {@code JSMember} with an identifier as {@code value} and a check
	 * whether it is in string form or identifier form. Please see
	 * {@link JSSnippet#isJavascriptIdentifier(String)}
	 * 
	 * @param value    the identifier part of the member name
	 * @param asString a flag for whether to use it as a string
	 */
	public JSMemberName(String value, boolean asString) {
		this.value = asString ? String.format("\"%s\"", value) : value;
		declareTime = System.nanoTime();
	}

	/*
	 * Date: 27 Aug 2022-----------------------------------------------------------
	 * Time created: 08:33:20---------------------------------------------------
	 */
	/**
	 * Creates a {@code JSMemberName} where the argument is used as either a string
	 * id or a direct identifier depending on whether it is a javascript identifier.
	 * 
	 * @param value the identifier part of the member name
	 */
	public JSMemberName(String value) {
		this(value, !JSSnippet.isJavascriptIdentifier(value));
	}

	/*
	 * Date: 27 Aug 2022-----------------------------------------------------------
	 * Time created: 08:35:21--------------------------------------------
	 */
	/**
	 * Gets the time that this member was declared. This is the same as when this
	 * constructor was called.
	 * 
	 * @return a long value in the same fashion as {@link System#nanoTime()}
	 */
	public long getDeclaredTime() {
		return declareTime;
	}

	/*
	 * Date: 27 Aug 2022----------------------------------------------------------- 
	 * Time created: 08:41:00--------------------------------------------
	 */
	/**
	 * Gets the id name
	 * @return the id name
	 */
	public String getName() {
		return value;
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 09:33:28--------------------------------------
	 */
	/**
	 * Gets the length of the identifier. This includes any quotation marks included if this identifier is a string.
	 * @return the length of this member name
	 */
	@Override
	public int length() {
		return value.length();
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 09:33:28--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param index {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public char charAt(int index) {
		return value.charAt(index);
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 09:33:28--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param start {@inheritDoc}
	 * @param end {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public CharSequence subSequence(int start, int end) {
		return value.subSequence(start, end);
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 09:39:47--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param o {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public int compareTo(JSMemberName o) {
//		return Long.valueOf(declareTime - o.declareTime).intValue();
		return Long.compareUnsigned(declareTime, o.declareTime);
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 09:39:47--------------------------------------
	 */
	/**
	 * Writes the id name wrapped within as a string into the given parser.
	 * 
	 * @param parser    an {@code Appendable} such as a {@code StringBuilder}.
	 * @param numOfTabs not relevent in this case because this is a simple (or
	 *                  atomic).
	 */
	@Override
	public void parseToScript(Appendable parser, int tabs) {
		try {
			parser.append(value);
		} catch (@SuppressWarnings("unused") IOException e) {
		}
	}

	@Override
	public int hashCode() {
		return value.hashCode() ^ Long.hashCode(declareTime);
	}

	@Override
	public boolean equals(Object o) {

		if (o instanceof JSMemberName) {
			JSMemberName m = (JSMemberName) o;
			return value.equals(m.value) && compareTo(m) == 0;
		}

		return false;
	}

}
