/**
 * 
 */
package mathaid.js;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import mathaid.js.JSSnippet.Type;

/*
 * Date: 20 Aug 2022----------------------------------------------------------- 
 * Time created: 08:02:24---------------------------------------------------  
 * Package: js------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: JSArray.java------------------------------------------------------ 
 * Class name: JSArray------------------------------------------------ 
 */
/**
 * A wrapper for Javascript's array value type. This a simple (non-compound) type.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class JSArray<T extends JSValue<?>> extends JSValue<JSArray<T>> {

	/*
	 * Date: 27 Aug 2022----------------------------------------------------------- 
	 * Time created: 07:05:44--------------------------------------------------- 
	 */
	/**
	 * Creates a {@code JSArray} object from the argument.
	 * 
	 * @param elements the value to be wrapped
	 */
	public JSArray(List<T> elements) {
		this.elements = elements;
	}
	
	/*
	 * Date: 27 Aug 2022----------------------------------------------------------- 
	 * Time created: 07:06:13--------------------------------------------------- 
	 */
	/**
	 * Constructor that wraps a zero length array 
	 */
	private JSArray() {
		this.elements = Collections.emptyList();
	}

	/*
	 * Date: 27 Aug 2022----------------------------------------------------------- 
	 * Time created: 07:07:36--------------------------------------------------- 
	 */
	/**
	 * Field for the wrapped value
	 */
	private final List<T> elements;

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 08:05:21--------------------------------------
	 */
	/**
	 * Always returns the array type.
	 * 
	 * @return the array type
	 * @see Type#ARRAY
	 */
	@Override
	public Type getType() {
		return Type.ARRAY;
	}
	
	/*
	 * Date: 27 Aug 2022----------------------------------------------------------- 
	 * Time created: 07:08:31--------------------------------------------
	 */
	/**
	 * Gets the wrapped value
	 * @return the wrapped {@code List}
	 */
	public List<T> asList(){
		return Collections.unmodifiableList(elements);
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 08:05:21--------------------------------------
	 */
	/**
	 * Writes the array wrapped within as a string into the given parser.
	 * 
	 * @param parser    an {@code Appendable} such as a {@code StringBuilder}.
	 * @param numOfTabs not relevent in this case because this type is a simple (or
	 *                  atomic) type.
	 */
	@Override
	public void parseToScript(Appendable parser, int tabs) {
		try {
			parser.append('[');
//			parser.append(LS);

			for (int i = 0; i < elements.size(); i++) {
				elements.get(i).parseToScript(parser, tabs);
				if (i < elements.size() - 1)
					parser.append(", ");
//				parser.append(LS);
			}

			parser.append(']');
		} catch (@SuppressWarnings("unused") IOException e) {
		}
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 08:05:21--------------------------------------
	 */
	/**
	 * Gets the hash code of {@link #getType()}
	 * 
	 * @return the hash code of {@link #getType()}
	 */
	@Override
	public int hashCode() {
		return elements.hashCode();
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 08:05:21--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param o the reference object with which to compare
	 * @return {@code true} if the argument is an {@link JSArray} type and
	 *         {@code getType() == o.getType()}
	 */
	@Override
	public boolean equals(Object o) {
		if(o instanceof JSArray) {
			@SuppressWarnings("unchecked")
			JSArray<T> r = (JSArray<T>) o;
			return elements.equals(r.elements);
		}
		return false;
	}

	/*
	 * Most Recent Date: 22 Aug 2022-----------------------------------------------
	 * Most recent time created: 00:26:46--------------------------------------
	 */
	/**
	 * Returns a {@code JSArray} object whose
	 * {@link #parseToScript(Appendable, int)} method is delegated to the
	 * argument's.
	 * 
	 * @param function a {@code JSFunction} which is assumed to have a return value
	 *                 of the same type as {@code this}
	 * @return a {@code JSArray} which delegates it's
	 *         {@link #parseToScript(Appendable, int)} to the argument.
	 * @see JSValue#byFunction(JSFunction)
	 */
	@Override
	public JSArray<T> byFunction(JSFunction function) {
		return new JSArray<>() {
			@Override
			public void parseToScript(Appendable p, int i) {
				function.parseToScript(p, i);
			}
		};
	}

}
