/**
 * 
 */
package mathaid.js;

import java.io.IOException;

/*
 * Date: 20 Aug 2022----------------------------------------------------------- 
 * Time created: 00:18:48---------------------------------------------------  
 * Package: js------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: JSSnippet.java------------------------------------------------------ 
 * Class name: JSSnippet------------------------------------------------ 
 */
/**
 * This a snippet of Javascript code that can be converted to a corresponding
 * serialised string via {@link #parseToScript(Appendable, int)}
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface JSSnippet {

	/**
	 * A constant for the line separator string of this platform as a reference.
	 */
	String LS = System.lineSeparator();

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 09:21:22--------------------------------------------
	 */
	/**
	 * Appends "\t" the specified ({@code t}) number of times. This is used for
	 * prettifying the results of {@code JSSnippet} object when
	 * {@link JSSnippet#parseToScript(Appendable, int)} is called.
	 * 
	 * @param parser an {@code Appendable} such as {@code StringBuilder}.
	 * @param t      the specified number of times that the tab character is to be
	 *               appended.
	 * @throws IOException if any I/O exception occurs from the {@code Appendable}
	 *                     end.
	 */
	static void appendTabs(Appendable parser, int t) throws IOException {
		for (int i = 0; i < t; i++)
			parser.append('\t');
	}

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 09:27:32--------------------------------------------
	 */
	/**
	 * Checks whether the given string can be used as a non-serialised variable name
	 * in a Javascript object i.e whether the argument can be used as a non-string
	 * identifier. For example:
	 * 
	 * <pre>
	 * <code>
	 * {
	 * 	fieldName: "this is a field",
	 *	&sol;&sol; ... other relevant variable declarations
	 * }
	 * </code>
	 * </pre>
	 * 
	 * {@code fieldName} from the above code is a non-serialised variable name.
	 * While
	 * 
	 * <pre>
	 * <code>
	 * {
	 * 	"field-Name-As-a-String": "this is a field",
	 *	&sol;&sol; ... other relevant variable declarations
	 * }
	 * </code>
	 * </pre>
	 * 
	 * {@code field-Name-As-a-String} is a serialised string used as a variable
	 * name. The former is a Javascript identifier and the latter is not.
	 * 
	 * @param s the string to be checked. This also includes empty strings but not
	 *          null strings.
	 * @return {@code true} if the argument can be used as a variable name in
	 *         Javascript without enclosing it in string quotation and {@code false}
	 *         if otherwise.
	 */
	static boolean isJavascriptIdentifier(String s) {
		try {
			int startPos = 0;
			int endPos = s.length() - 1;
			char start = s.charAt(0);
			boolean isStart = Character.isJavaIdentifierStart(start);
			if (startPos == endPos)
				return isStart;
			boolean isIdentifier = isStart;
			int i = 1;
			do {
				char c = s.charAt(i);
				isIdentifier = Character.isJavaIdentifierPart(c);
				i += 1;
			} while (isIdentifier == true && i < s.length());
			return isIdentifier;
		} catch (@SuppressWarnings("unused") IndexOutOfBoundsException e) {
			return false;
		}
	}

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 09:43:21---------------------------------------------------
	 * Package: js------------------------------------------------ Project:
	 * TestCode------------------------------------------------ File:
	 * JSSnippet.java------------------------------------------------------ Class
	 * name: Type------------------------------------------------
	 */
	/**
	 * A type is a part of the state of a {@code JSValue} which represents the type
	 * of Javascript value.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 */
	enum Type {
		/**
		 * A constant for a Javascript array type
		 */
		ARRAY("array"),
		/**
		 * A constant for a Javascript boolean type
		 */
		BOOLEAN("boolean"),
		/**
		 * A constant for a Javascript bigInt type
		 */
		BIGINT("bigint"),
		/**
		 * A constant for a Javascript function type
		 */
		FUNCTION("function"),
		/**
		 * A constant for a dictionary type> Although this is an unconventional
		 * Javascript type, it is used extensively by this API.
		 */
		MAP("map"),
		/**
		 * A constant for a Javascript number type
		 */
		NUMBER("number"),
		/**
		 * A constant for a Javascript null type
		 */
		NULL("null"),
		/**
		 * A constant for a Javascript object type
		 */
		OBJECT("object"), REGEX("regex"),
		/**
		 * A constant for a Javascript string type
		 */
		STRING("string"),
		/**
		 * A constant for a Javascript symbol type
		 */
		SYMBOL("symbol"),
		/**
		 * A constant for a undefined bigInt type
		 */
		UNDEFINED("undefined");

		/*
		 * Date: 26 Aug 2022-----------------------------------------------------------
		 * Time created: 10:13:39---------------------------------------------------
		 */
		/**
		 * Creates a {@code Type} and assigns a given name to it which is accessed by
		 * {@link #getName()}
		 * 
		 * @param name
		 */
		Type(String name) {
			n = name;
		}

		/*
		 * Date: 26 Aug 2022-----------------------------------------------------------
		 * Time created: 10:14:35--------------------------------------------
		 */
		/**
		 * Gets the name assigned to this type as a string
		 * 
		 * @return the name of {@code this}
		 */
		public String getName() {
			return n;
		}

		/*
		 * Date: 26 Aug 2022-----------------------------------------------------------
		 * Time created: 10:15:34---------------------------------------------------
		 */
		/**
		 * Field for the name of {@code this}
		 */
		private String n;
	}
	
	// tabs argument is for multi-layered snippet such as an object literal or a
	// function
	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 10:16:06--------------------------------------------
	 */
	/**
	 * Serialises {@code this} to a string and appends the result to the given
	 * {@code Appendable}, using the {@code int} argument to tabulate the lines of
	 * code as needed.
	 * 
	 * @param parser    an {@code Appendable} such as a {@code StringBuilder}.
	 * @param numOfTabs the number of tabs by which the internal formatter uses for
	 *                  prettifying of the serialised string. If this
	 *                  {@code JSSnippet} is implemented as a nested node, then the
	 *                  top-most object can just pass {@code 0} to this parameter.
	 * @param comments any relevant comment to the code.
	 */
//	void parseToScript(Appendable parser, int numOfTabs, String comments);

	// tabs argument is for multi-layered snippet such as an object literal or a
	// function
	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 10:16:06--------------------------------------------
	 */
	/**
	 * Serialises {@code this} to a string and appends the result to the given
	 * {@code Appendable}, using the {@code int} argument to tabulate the lines of
	 * code as needed.
	 * 
	 * @param parser    an {@code Appendable} such as a {@code StringBuilder}.
	 * @param numOfTabs the number of tabs by which the internal formatter uses for
	 *                  prettifying of the serialised string. If this
	 *                  {@code JSSnippet} is implemented as a nested node, then the
	 *                  top-most object can just pass {@code 0} to this parameter.
	 */
	void parseToScript(Appendable parser, int numOfTabs);
}
