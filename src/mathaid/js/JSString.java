/**
 * 
 */
package mathaid.js;

import java.io.IOException;

import mathaid.js.JSSnippet.Type;

/*
 * Date: 20 Aug 2022----------------------------------------------------------- 
 * Time created: 01:10:07---------------------------------------------------  
 * Package: js------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: JSString.java------------------------------------------------------ 
 * Class name: JSString------------------------------------------------ 
 */
/**
 * A wrapper for Javascript's string value type. This a simple (non-compound) type.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class JSString extends JSValue<JSString> {

	/*
	 * Date: 20 Aug 2022----------------------------------------------------------- 
	 * Time created: 01:10:07--------------------------------------------------- 
	 */
	/**
	 * Creates a {@code JSString} object from the argument.
	 * @param val the string that this type wraps. A null value wraps the string {@code "null"}
	 */
	public JSString(String val) {
		this.val = val == null ? "null" : val;
	}
	
	/*
	 * Date: 26 Aug 2022----------------------------------------------------------- 
	 * Time created: 11:11:50--------------------------------------------------- 
	 */
	/**
	 * Creates a null string
	 */
	private JSString() {
		this(null);
	}
	
	/*
	 * Date: 26 Aug 2022----------------------------------------------------------- 
	 * Time created: 11:12:08--------------------------------------------
	 */
	/**
	 * Gets the wrapped value.
	 * @return the wrapped string
	 */
	public String get() {
		return val;
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 01:10:12--------------------------------------
	 */
	/**
	 * Always returns the string type.
	 * 
	 * @return the string type
	 * @see Type#STRING
	 */
	@Override
	public Type getType() {
		return Type.STRING;
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 01:10:12--------------------------------------
	 */
	/**
	 * Writes the string wrapped within into the given parser.
	 * 
	 * @param parser    an {@code Appendable} such as a {@code StringBuilder}.
	 * @param numOfTabs not relevent in this case because this type is a simple
	 *                  (or atomic) type.
	 */
	@Override
	public void parseToScript(Appendable parser, int tabs) {
		try {
			parser.append("\"" + val + "\"");
		}catch(@SuppressWarnings("unused") IOException e) {}
	}
	
	/*
	 * Date: 26 Aug 2022----------------------------------------------------------- 
	 * Time created: 11:14:45--------------------------------------------------- 
	 */
	/**
	 * Field for the wrapped string
	 */
	private final String val;

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 01:17:56--------------------------------------
	 */
	/**
	 * Gets the hash code of {@link #getType()}
	 * 
	 * @return the hash code of {@link #getType()}
	 */
	@Override
	public int hashCode() {
		return val.hashCode();
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 01:17:56--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param o the reference object with which to compare
	 * @return {@code true} if the argument is an {@link JSString} type and
	 *         {@code getType() == o.getType()}
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof JSString) {
			JSString u = (JSString) o;
			return val.equals(u.val);
		}
		return false;
	}

	/*
	 * Most Recent Date: 22 Aug 2022-----------------------------------------------
	 * Most recent time created: 00:47:47--------------------------------------
	 */
	/**
	 * Returns a {@code JSString} object whose
	 * {@link #parseToScript(Appendable, int)} method is delegated to the
	 * argument's.
	 * 
	 * @param function a {@code JSFunction} which is assumed to have a return value
	 *                 of the same type as {@code this}
	 * @return a {@code JSString} which delegates it's
	 *         {@link #parseToScript(Appendable, int)} to the argument.
	 * @see JSValue#byFunction(JSFunction)
	 */
	@Override
	public JSString byFunction(JSFunction function) {
		return new JSString() {
			@Override
			public void parseToScript(Appendable p, int i) {
				function.parseToScript(p, i);
			}
		};
	}

}
