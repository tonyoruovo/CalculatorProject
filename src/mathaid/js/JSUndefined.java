/**
 * 
 */
package mathaid.js;

import java.io.IOException;

/*
 * Date: 20 Aug 2022----------------------------------------------------------- 
 * Time created: 00:40:49---------------------------------------------------  
 * Package: js------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: JSUndefined.java------------------------------------------------------ 
 * Class name: JSUndefined------------------------------------------------ 
 */
/**
 * A wrapper for Javascript's undefined value type. This a simple (non-compound) type.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class JSUndefined extends JSValue<JSUndefined> {

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 00:41:29--------------------------------------
	 */
	/**
	 * Always returns the undefined type.
	 * 
	 * @return the undefined type
	 * @see Type#UNDEFINED
	 */
	@Override
	public Type getType() {
		return Type.UNDEFINED;
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 00:41:29--------------------------------------
	 */
	/**
	 * Writes the string {@code undefined} to the given parser.
	 * 
	 * @param parser    an {@code Appendable} such as a {@code StringBuilder}.
	 * @param numOfTabs not relevent in this case because this type is a simple
	 *                  (or atomic) type.
	 */
	@Override
	public void parseToScript(Appendable parser, int tabs) {
		try {
			parser.append(getType().getName());
		} catch (@SuppressWarnings("unused") IOException e) {
		}
	}

	/*
	 * Most Recent Date: 26 Aug 2022-----------------------------------------------
	 * Most recent time created: 10:50:41--------------------------------------
	 */
	/**
	 * Gets the hash code of {@link #getType()}
	 * 
	 * @return the hash code of {@link #getType()}
	 */
	@Override
	public int hashCode() {
		return getType().hashCode();
	}

	/*
	 * Most Recent Date: 26 Aug 2022-----------------------------------------------
	 * Most recent time created: 10:52:25--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param o the reference object with which to compare
	 * @return {@code true} if the argument is an {@link JSUndefined} type and
	 *         {@code getType() == o.getType()}
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof JSUndefined) {
			JSUndefined u = (JSUndefined) o;
			return getType().equals(u.getType());
		}
		return false;
	}

	/*
	 * Most Recent Date: 22 Aug 2022-----------------------------------------------
	 * Most recent time created: 00:48:54--------------------------------------
	 */
	/**
	 * Returns a {@code JSUndefined} object whose
	 * {@link #parseToScript(Appendable, int)} method is delegated to the
	 * argument's.
	 * 
	 * @param function a {@code JSFunction} which is assumed to have a return value
	 *                 of the same type as {@code this}
	 * @return a {@code JSUndefined} which delegates it's
	 *         {@link #parseToScript(Appendable, int)} to the argument.
	 * @see JSValue#byFunction(JSFunction)
	 */
	@Override
	public JSUndefined byFunction(JSFunction function) {
		return new JSUndefined() {
			@Override
			public void parseToScript(Appendable p, int i) {
				function.parseToScript(p, i);
			}
		};
	}
}
