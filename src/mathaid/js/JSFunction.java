/**
 * 
 */
package mathaid.js;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import mathaid.js.JSSnippet.Type;

/*
 * Date: 20 Aug 2022----------------------------------------------------------- 
 * Time created: 01:20:59---------------------------------------------------  
 * Package: js------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: JSFunction.java------------------------------------------------------ 
 * Class name: JSFunction------------------------------------------------ 
 */
/**
 * A wrapper for Javascript's function literal value type. This a compound type.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class JSFunction extends JSValue<JSFunction> {

	/*
	 * Date: 20 Aug 2022-----------------------------------------------------------
	 * Time created: 01:20:59---------------------------------------------------
	 */
	/**
	 * Creates a {@code JSFunction} object from the arguments.
	 * 
	 * @param argNames the identifiers of the arguments
	 * @param body     each line of code from the body of the function. The <i>key</i> is
	 *                 the line number while the <i>value</i> is the line itself
	 */
	public JSFunction(List<String> argNames, Map<Integer, String> body) {
		this.argNames = argNames;
		this.steps = body;
	}

	/*
	 * Date: 27 Aug 2022----------------------------------------------------------- 
	 * Time created: 07:27:39--------------------------------------------------- 
	 */
	/**
	 * Constructor that wraps a no-argument function with no code in it's body part
	 */
	private JSFunction() {
		this.argNames = Collections.emptyList();
		this.steps = Collections.emptyMap();
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 01:21:04--------------------------------------
	 */
	/**
	 * Always returns the function type.
	 * 
	 * @return the function type
	 * @see Type#FUNCTION
	 */
	@Override
	public Type getType() {
		return Type.FUNCTION;
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 01:21:04--------------------------------------
	 */
	/**
	 * Writes the function wrapped within as a string into the given parser in Javascript
	 * notation of:
	 * 
	 * <pre>
	 * <code>
	 * () => function.call()
	 * </code>
	 * </pre>
	 * for single line, and:
	 * <pre>
	 * <code>
	 * () => {
	 * 	&sol;&sol; ... body of function here
	 * }
	 * </code>
	 * </pre>
	 * .
	 * 
	 * @param parser    an {@code Appendable} such as a {@code StringBuilder}.
	 * @param numOfTabs tabulates each line of mapping as specified. For example: if
	 *                  this value is 2, then the tab character is appended twice
	 *                  simultaneously to the appendable.
	 */
	@Override
	public void parseToScript(Appendable parser, int tabs) {
		try {
			parser.append('(');
			for (int i = 0; i < argNames.size(); i++) {
				parser.append(argNames.get(i));
				if (i < argNames.size() - 1)
					parser.append(", ");
			}
			parser.append(") => ");
			if (steps.size() == 1) {
				String line = steps.get(steps.size() - 1);
				char end = line.charAt(line.length() - 1);
				if (end == ';')
					parser.append(line.subSequence(0, line.length() - 1));
				else
					parser.append(line);

			} else {
				parser.append('{');
				parser.append(LS);
//				for(int lineNumber : steps.keySet()) {
//					parser.append(steps.get(lineNumber));
//					parser.append(LS);
//				}
				int numOfTabs = tabs;
				for (int i = 0; i < steps.size(); i++) {
					String line = steps.get(i);
					char endOfLineChar;
					try {
						endOfLineChar = line.charAt(line.length() - 1);
					} catch (@SuppressWarnings("unused") IndexOutOfBoundsException e) {
						endOfLineChar = '\0';
					}
					if (endOfLineChar == '}')
						numOfTabs = numOfTabs > 0 ? numOfTabs - 1 : 0;
					JSSnippet.appendTabs(parser, numOfTabs);
					parser.append(line);
					parser.append(LS);
					if (endOfLineChar == '{')
						numOfTabs += 1;
				}
				JSSnippet.appendTabs(parser, tabs > 0 ? tabs - 1 : 0);
				parser.append('}');
//				parser.append(LS);
			}
		} catch (@SuppressWarnings("unused") IOException e) {
		}
	}

	/*
	 * Date: 27 Aug 2022----------------------------------------------------------- 
	 * Time created: 07:34:25--------------------------------------------------- 
	 */
	/**
	 * Field for the list of argument names
	 */
	private List<String> argNames;
	/*
	 * Date: 27 Aug 2022----------------------------------------------------------- 
	 * Time created: 07:35:10--------------------------------------------------- 
	 */
	/**
	 * Field for the body of the function
	 */
	private Map<Integer, String> steps;

	/*
	 * Date: 27 Aug 2022----------------------------------------------------------- 
	 * Time created: 07:35:30--------------------------------------------
	 */
	/**
	 * Gets the identifiers of the argument(s)
	 * @return a list of string for the given argument's identifier(s)
	 */
	public List<String> getArgNames() {
		return Collections.unmodifiableList(argNames);
	}

	/*
	 * Date: 27 Aug 2022----------------------------------------------------------- 
	 * Time created: 07:36:44--------------------------------------------
	 */
	/**
	 * Gets the body of this function in a map that pairs the line number with the code in that line.
	 * @return a map given the code in the body part of this {@code JSFunction}
	 */
	public Map<Integer, String> getLines() {
		return Collections.unmodifiableMap(steps);
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 01:21:04--------------------------------------
	 */
	/**
	 * Gets the hash code of {@link #getType()}
	 * 
	 * @return the hash code of {@link #getType()}
	 */
	@Override
	public int hashCode() {
		return argNames.hashCode() ^ steps.hashCode();
	}

	/*
	 * Most Recent Date: 20 Aug 2022-----------------------------------------------
	 * Most recent time created: 01:21:04--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param o the reference object with which to compare
	 * @return {@code true} if the argument is an {@link JSFunction} type and
	 *         {@code getType() == o.getType()}
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof JSFunction) {
			JSFunction f = (JSFunction) o;
			return steps.equals(f.steps) && argNames.equals(f.argNames);
		}
		return false;
	}

	/*
	 * Most Recent Date: 22 Aug 2022-----------------------------------------------
	 * Most recent time created: 00:37:48--------------------------------------
	 */
	/**
	 * Returns a {@code JSFunction} object whose
	 * {@link #parseToScript(Appendable, int)} method is delegated to the
	 * argument's.
	 * 
	 * @param function a {@code JSFunction} which is assumed to have a return value
	 *                 of the same type as {@code this}
	 * @return a {@code JSFunction} which delegates it's
	 *         {@link #parseToScript(Appendable, int)} to the argument.
	 */
	@Override
	public JSFunction byFunction(JSFunction function) {
		return new JSFunction() {
			@Override
			public void parseToScript(Appendable p, int i) {
				function.parseToScript(p, i);
			}
		};
	}

}
