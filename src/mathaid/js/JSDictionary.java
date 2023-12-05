/**
 * 
 */
package mathaid.js;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import mathaid.js.JSSnippet.Type;

/*
 * Date: 20 Aug 2022----------------------------------------------------------- 
 * Time created: 08:14:29---------------------------------------------------  
 * Package: js------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: JSDictionary.java------------------------------------------------------ 
 * Class name: JSDictionary------------------------------------------------ 
 */
/**
 * A wrapper for Javascript's array value type. This a compound type.
 * <p>
 * This is not an actual javascript type but an informal one.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class JSDictionary<K extends JSValue<?>, V extends JSValue<?>> extends JSValue<JSDictionary<K, V>> {

	/*
	 * Date: 20 Aug 2022-----------------------------------------------------------
	 * Time created: 08:14:30---------------------------------------------------
	 */
	/**
	 * Creates a {@code JSDictionary} object from the argument.
	 * 
	 * @param pairs the value to be wrapped
	 */
	public JSDictionary(Map<K, V> pairs) {
		this.pairs = pairs;
	}

	/*
	 * Date: 27 Aug 2022-----------------------------------------------------------
	 * Time created: 07:13:56---------------------------------------------------
	 */
	/**
	 * Constructor that wraps a zero length dictionary
	 */
	private JSDictionary() {
		this.pairs = Collections.emptyMap();
	}

	/*
	 * Date: 27 Aug 2022-----------------------------------------------------------
	 * Time created: 07:14:10---------------------------------------------------
	 */
	/**
	 * Field for the wrapped value
	 */
	private final Map<K, V> pairs;

	/*
	 * Date: 27 Aug 2022-----------------------------------------------------------
	 * Time created: 07:14:28--------------------------------------------
	 */
	/**
	 * Gets the wrapped value
	 * 
	 * @return the wrapped {@code Map}
	 */
	public Map<K, V> asMap() {
		return Collections.unmodifiableMap(pairs);
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 08:16:12--------------------------------------
	 */
	/**
	 * Always returns the map type.
	 * 
	 * @return the map type
	 * @see Type#MAP
	 */
	@Override
	public Type getType() {
		return Type.MAP;
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 08:16:12--------------------------------------
	 */
	/**
	 * Writes the map wrapped within as a string into the given parser in Javascript
	 * notation of:
	 * 
	 * <pre>
	 * <code>
	 * [
	 * 	key: value,
	 * 	// ... other mappings
	 * ]
	 * </code>
	 * </pre>
	 * 
	 * @param parser    an {@code Appendable} such as a {@code StringBuilder}.
	 * @param numOfTabs tabulates each line of mapping as specified. For example: if
	 *                  this value is 2, then the tab character is appended twice
	 *                  consecutively to the appendable.
	 */
	@Override
	public void parseToScript(Appendable parser, int tabs) {
		try {
			parser.append('{');
			parser.append(LS);

			int iteration = -1;
			for (Map.Entry<K, V> entry : pairs.entrySet()) {
				++iteration;
				JSSnippet.appendTabs(parser, tabs);
				entry.getKey().parseToScript(parser, tabs + 1);
				parser.append(": ");
				entry.getValue().parseToScript(parser, tabs + 1);
				if (iteration < pairs.size() - 1)
					parser.append(',');
				parser.append(LS);
			}

			JSSnippet.appendTabs(parser, tabs - 1);
			parser.append('}');
		} catch (IOException e) {
		}
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 08:16:12--------------------------------------
	 */
	/**
	 * Gets the hash code of {@link #getType()}
	 * 
	 * @return the hash code of {@link #getType()}
	 */
	@Override
	public int hashCode() {
		return pairs.hashCode();
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 08:16:12--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param o the reference object with which to compare
	 * @return {@code true} if the argument is an {@link JSDictionary} type and
	 *         {@code getType() == o.getType()}
	 */
	@Override
	public boolean equals(Object o) {
		if(o instanceof JSDictionary) {
			@SuppressWarnings("unchecked")
			JSDictionary<K, V> r = (JSDictionary<K, V>) o;
			return pairs.equals(r.pairs);
		}
		return false;
	}

	/*
	 * Most Recent Date: 22 Aug 2022-----------------------------------------------
	 * Most recent time created: 00:35:26--------------------------------------
	 */
	/**
	 * Returns a {@code JSDictionary} object whose
	 * {@link #parseToScript(Appendable, int)} method is delegated to the
	 * argument's.
	 * 
	 * @param function a {@code JSFunction} which is assumed to have a return value
	 *                 of the same type as {@code this}
	 * @return a {@code JSDictionary} which delegates it's
	 *         {@link #parseToScript(Appendable, int)} to the argument.
	 * @see JSValue#byFunction(JSFunction)
	 */
	@Override
	public JSDictionary<K, V> byFunction(JSFunction function) {
		return new JSDictionary<>() {
			@Override
			public void parseToScript(Appendable p, int i) {
				function.parseToScript(p, i);
			}
		};
	}

}
