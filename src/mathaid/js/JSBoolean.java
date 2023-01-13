/**
 * 
 */
package mathaid.js;

import java.io.IOException;

import mathaid.js.JSSnippet.Type;

/*
 * Date: 20 Aug 2022----------------------------------------------------------- 
 * Time created: 00:45:45---------------------------------------------------  
 * Package: js------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: JSBoolean.java------------------------------------------------------ 
 * Class name: JSBoolean------------------------------------------------ 
 */
/**
 * A wrapper for Javascript's boolean value type. This a simple (non-compound) type.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class JSBoolean extends JSValue<JSBoolean> {

	/*
	 * Date: 20 Aug 2022----------------------------------------------------------- 
	 * Time created: 00:45:45--------------------------------------------------- 
	 */
	/**
	 * Creates a {@code JSBoolean} object from the argument.
	 * @param val the boolean that this type wraps.
	 */
	public JSBoolean(boolean val) {
		this.val = val;
	}
	
	/*
	 * Date: 26 Aug 2022----------------------------------------------------------- 
	 * Time created: 11:19:03--------------------------------------------------- 
	 */
	/**
	 * Creates a {@code JSBoolean} that wraps the value {@code false}
	 */
	private JSBoolean() {
		this.val = false;
	}
	
	/*
	 * Date: 26 Aug 2022----------------------------------------------------------- 
	 * Time created: 11:19:40--------------------------------------------
	 */
	/**
	 * Gets the wrapped value.
	 * @return the wrapped string
	 */
	public boolean get() {
		return val;
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 00:47:33--------------------------------------
	 */
	/**
	 * Always returns the boolean type.
	 * 
	 * @return the boolean type
	 * @see Type#BOOLEAN
	 */
	@Override
	public Type getType() {
		return Type.BOOLEAN;
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 00:47:33--------------------------------------
	 */
	/**
	 * Writes the boolean wrapped within as a string into the given parser.
	 * 
	 * @param parser    an {@code Appendable} such as a {@code StringBuilder}.
	 * @param numOfTabs not relevent in this case because this type is a simple
	 *                  (or atomic) type.
	 */
	@Override
	public void parseToScript(Appendable parser, int tabs) {
		try {
			parser.append(String.valueOf(val));
		}catch(@SuppressWarnings("unused") IOException e) {}
	}
	
	/*
	 * Most Recent Date: 26 Aug 2022-----------------------------------------------
	 * Most recent time created: 11:22:04--------------------------------------
	 */
	/**
	 * Gets the hash code of {@link #getType()}
	 * 
	 * @return the hash code of {@link #getType()}
	 */
	@Override
	public int hashCode() {
		return Boolean.hashCode(val);
	}
	
	/*
	 * Most Recent Date: 26 Aug 2022-----------------------------------------------
	 * Most recent time created: 11:22:30--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param o the reference object with which to compare
	 * @return {@code true} if the argument is an {@link JSBoolean} type and
	 *         {@code getType() == o.getType()}
	 */
	@Override
	public boolean equals(Object o) {
		if(o instanceof JSBoolean) {
			JSBoolean b = (JSBoolean) o;
			return val == b.val;
		}
		return false;
	}

	/*
	 * Date: 26 Aug 2022----------------------------------------------------------- 
	 * Time created: 11:23:01--------------------------------------------------- 
	 */
	/**
	 * Field for the wrapped value
	 */
	private final boolean val;

	/*
	 * Most Recent Date: 22 Aug 2022-----------------------------------------------
	 * Most recent time created: 00:33:02--------------------------------------
	 */
	/**
	 * Returns a {@code JSBoolean} object whose
	 * {@link #parseToScript(Appendable, int)} method is delegated to the
	 * argument's.
	 * 
	 * @param function a {@code JSFunction} which is assumed to have a return value
	 *                 of the same type as {@code this}
	 * @return a {@code JSBoolean} which delegates it's
	 *         {@link #parseToScript(Appendable, int)} to the argument.
	 * @see JSValue#byFunction(JSFunction)
	 */
	@Override
	public JSBoolean byFunction(JSFunction function) {
		return new JSBoolean() {
			@Override
			public void parseToScript(Appendable p, int i) {
				function.parseToScript(p, i);
			}
		};
	}
}
