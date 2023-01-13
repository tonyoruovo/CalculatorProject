/**
 * 
 */
package mathaid.js;

import java.io.IOException;
import java.util.Collections;
import java.util.NavigableMap;
import java.util.Objects;

import mathaid.js.JSSnippet.Type;

/*
 * Date: 20 Aug 2022----------------------------------------------------------- 
 * Time created: 07:03:16---------------------------------------------------  
 * Package: js------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: JSObject.java------------------------------------------------------ 
 * Class name: JSObject------------------------------------------------ 
 */
/**
 * A wrapper for Javascript's object value type. This a compound type.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class JSObject extends JSValue<JSObject> {
	/*
	 * Date: 27 Aug 2022-----------------------------------------------------------
	 * Time created: 07:43:22---------------------------------------------------
	 */
	/**
	 * Creates a {@code JSObject} object from the argument.
	 * 
	 * @param members the value to be wrapped
	 */
	public JSObject(NavigableMap<JSMemberName, JSValue<?>> members) {
		this.members = members;
	}

	/*
	 * Date: 27 Aug 2022-----------------------------------------------------------
	 * Time created: 07:44:28---------------------------------------------------
	 */
	/**
	 * Constructor that wraps an empty object
	 */
	private JSObject() {
		this(Collections.emptyNavigableMap());
	}

	/*
	 * Date: 27 Aug 2022-----------------------------------------------------------
	 * Time created: 07:44:58--------------------------------------------
	 */
	/**
	 * The members (fields) of this value as a {@code NavigableMap}
	 * 
	 * @return all the values (including functions) declared in this object in
	 *         {@code NavigableMap}, using their names as keys.
	 */
	public NavigableMap<JSMemberName, JSValue<?>> getMembers() {
		return Collections.unmodifiableNavigableMap(members);
	}

	/*
	 * Date: 27 Aug 2022-----------------------------------------------------------
	 * Time created: 08:01:44---------------------------------------------------
	 */
	/**
	 * Field for the wrapped value
	 */
	private final NavigableMap<JSMemberName, JSValue<?>> members;

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 07:20:01--------------------------------------
	 */
	/**
	 * Always returns the object type.
	 * 
	 * @return the object type
	 * @see Type#MAP
	 */
	@Override
	public Type getType() {
		return Type.OBJECT;
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 07:20:01--------------------------------------
	 */
	/**
	 * Writes the map wrapped within as a string into the given parser in Javascript
	 * notation of:
	 * 
	 * <pre>
	 * <code>
	 * {
	 * 	key: value,
	 * 	// ... other mappings
	 * }
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
			for (JSMemberName memberName : members.keySet()) {
				++iteration;
				JSSnippet.appendTabs(parser, tabs);
				memberName.parseToScript(parser, tabs + 1);
				parser.append(" : ");
				members.get(memberName).parseToScript(parser, tabs + 1);
				if (iteration < members.size() - 1)
					parser.append(',');
				parser.append(LS);
			}

			JSSnippet.appendTabs(parser, tabs - 1);
			parser.append('}');
		} catch (@SuppressWarnings("unused") IOException e) {
		}
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 07:20:01--------------------------------------
	 */
	/**
	 * Gets the hash code of {@link #getType()}
	 * 
	 * @return the hash code of {@link #getType()}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this);
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 07:20:01--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param o the reference object with which to compare
	 * @return {@code true} if the argument is an {@link JSObject} type and
	 *         {@code getType() == o.getType()}
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof JSObject) {
			JSObject obj = (JSObject) o;
			return members == obj.members;
		}
		return false;
	}

	/*
	 * Most Recent Date: 22 Aug 2022-----------------------------------------------
	 * Most recent time created: 00:44:41--------------------------------------
	 */
	/**
	 * Returns a {@code JSObject} object whose
	 * {@link #parseToScript(Appendable, int)} method is delegated to the
	 * argument's.
	 * 
	 * @param function a {@code JSFunction} which is assumed to have a return value
	 *                 of the same type as {@code this}
	 * @return a {@code JSObject} which delegates it's
	 *         {@link #parseToScript(Appendable, int)} to the argument.
	 * @see JSValue#byFunction(JSFunction)
	 */
	@Override
	public JSObject byFunction(JSFunction function) {
		return new JSObject() {
			@Override
			public void parseToScript(Appendable p, int i) {
				function.parseToScript(p, i);
			}
		};
	}
}
