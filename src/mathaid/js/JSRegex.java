/**
 * 
 */
package mathaid.js;

import java.io.IOException;
import java.util.regex.Pattern;

import mathaid.js.JSSnippet.Type;

/*
 * Date: 20 Aug 2022----------------------------------------------------------- 
 * Time created: 08:38:54---------------------------------------------------  
 * Package: js------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: JSRegex.java------------------------------------------------------ 
 * Class name: JSRegex------------------------------------------------ 
 */
/**
 * A wrapper for Javascript's regex value type. This a simple (non-compound) type.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class JSRegex extends JSValue<JSRegex> {

	/*
	 * Date: 20 Aug 2022----------------------------------------------------------- 
	 * Time created: 08:38:54--------------------------------------------------- 
	 */
	/**
	 * Creates a {@code JSRegex} object from the argument.
	 * 
	 * @param pattern the value that this type wraps.
	 * @param isGlobal a flag for global regex
	 */
	public JSRegex(Pattern pattern, boolean isGlobal) {
		this.p = pattern;
		this.global = isGlobal;
	}
	
	/*
	 * Date: 26 Aug 2022----------------------------------------------------------- 
	 * Time created: 23:56:28--------------------------------------------------- 
	 */
	/**
	 * Creates a {@code JSRegex} that wraps a null regex.
	 */
	private JSRegex() {
		this(null, false);
	}
	
	/*
	 * Date: 26 Aug 2022----------------------------------------------------------- 
	 * Time created: 23:57:49--------------------------------------------------- 
	 */
	/**
	 * Field for the wrapped value
	 */
	private final Pattern p;
	/*
	 * Date: 26 Aug 2022----------------------------------------------------------- 
	 * Time created: 23:57:52--------------------------------------------------- 
	 */
	/**
	 * Field for the global flag
	 */
	private final boolean global;
	
	/*
	 * Date: 26 Aug 2022----------------------------------------------------------- 
	 * Time created: 23:58:25--------------------------------------------
	 */
	/**
	 * Gets the wrapped value
	 * @return the wrapped {@code Pattern}
	 */
	public Pattern asPattern() {
		return p;
	}
	
	/*
	 * Date: 26 Aug 2022----------------------------------------------------------- 
	 * Time created: 23:59:31--------------------------------------------
	 */
	/**
	 * Gets the global flag
	 * @return the value of the global Object.
	 */
	public boolean isGlobal() {
		return global;
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 08:39:46--------------------------------------
	 */
	/**
	 * Always returns the regex type.
	 * 
	 * @return the regex type
	 * @see Type#REGEX
	 */
	@Override
	public Type getType() {
		return Type.REGEX;
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 08:39:46--------------------------------------
	 */
	/**
	 * Writes the regex wrapped within as a string into the given parser.
	 * 
	 * @param parser    an {@code Appendable} such as a {@code StringBuilder}.
	 * @param numOfTabs not relevent in this case because this type is a simple (or
	 *                  atomic) type.
	 */
	@Override
	public void parseToScript(Appendable parser, int tabs) {
		try {
			parser.append('/');
			parser.append(p.pattern());
			parser.append('/');
			if(global)
				parser.append('g');
		}catch(IOException e) {}
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 08:39:46--------------------------------------
	 */
	/**
	 * Gets the hash code of {@link #getType()}
	 * 
	 * @return the hash code of {@link #getType()}
	 */
	@Override
	public int hashCode() {
		return p.hashCode();
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 08:39:46--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param o the reference object with which to compare
	 * @return {@code true} if the argument is an {@link JSRegex} type and
	 *         {@code getType() == o.getType()}
	 */
	@Override
	public boolean equals(Object o) {
		if(o instanceof JSRegex) {
			JSRegex r = (JSRegex) o;
			return p.equals(r.p) && global == r.global;
		}
		return false;
	}

	/*
	 * Most Recent Date: 22 Aug 2022-----------------------------------------------
	 * Most recent time created: 00:46:50--------------------------------------
	 */
	/**
	 * Returns a {@code JSRegex} object whose
	 * {@link #parseToScript(Appendable, int)} method is delegated to the
	 * argument's.
	 * 
	 * @param function a {@code JSFunction} which is assumed to have a return value
	 *                 of the same type as {@code this}
	 * @return a {@code JSRegex} which delegates it's
	 *         {@link #parseToScript(Appendable, int)} to the argument.
	 * @see JSValue#byFunction(JSFunction)
	 */
	@Override
	public JSRegex byFunction(JSFunction function) {
		return new JSRegex() {
			@Override
			public void parseToScript(Appendable p, int i) {
				function.parseToScript(p, i);
			}
		};
	}

}
