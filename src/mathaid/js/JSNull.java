/**
 * 
 */
package mathaid.js;

import java.io.IOException;

import mathaid.js.JSSnippet.Type;

/*
 * Date: 20 Aug 2022----------------------------------------------------------- 
 * Time created: 00:35:10---------------------------------------------------  
 * Package: js------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: JSNull.java------------------------------------------------------ 
 * Class name: JSNull------------------------------------------------ 
 */
/**
 * A wrapper for Javascript's null value type. This a simple (non-compound) type.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class JSNull extends JSValue<JSNull> {

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 00:38:53--------------------------------------
	 */
	/**
	 * Always returns the null type.
	 * 
	 * @return the null type
	 * @see Type#NULL
	 */
	@Override
	public Type getType() {
		return Type.NULL;
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 00:38:53--------------------------------------
	 */
	/**
	 * Writes the string {@code null} to the given parser.
	 * 
	 * @param parser    an {@code Appendable} such as a {@code StringBuilder}.
	 * @param numOfTabs not relevent in this case because this type is a simple
	 *                  (or atomic) type.
	 */
	@Override
	public void parseToScript(Appendable parser, int tabs) {
		try {
			parser.append(getType().getName());
		}catch(IOException e) {}
	}
	
	/*
	 * Most Recent Date: 26 Aug 2022-----------------------------------------------
	 * Most recent time created: 11:07:01--------------------------------------
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
	 * Most recent time created: 11:07:11--------------------------------------
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
		return getType().equals(o);
	}

	/*
	 * Most Recent Date: 22 Aug 2022-----------------------------------------------
	 * Most recent time created: 00:39:40--------------------------------------
	 */
	/**
	 * Returns a {@code JSNull} object whose
	 * {@link #parseToScript(Appendable, int)} method is delegated to the
	 * argument's.
	 * 
	 * @param function a {@code JSFunction} which is assumed to have a return value
	 *                 of the same type as {@code this}
	 * @return a {@code JSNull} which delegates it's
	 *         {@link #parseToScript(Appendable, int)} to the argument.
	 * @see JSValue#byFunction(JSFunction)
	 */
	@Override
	public JSNull byFunction(JSFunction function) {
		return new JSNull() {
			@Override
			public void parseToScript(Appendable p, int i) {
				function.parseToScript(p, i);
			}
		};
	}

}
