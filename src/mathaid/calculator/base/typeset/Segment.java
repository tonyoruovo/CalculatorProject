/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.List;

/*
 * Date: 12 Nov 2023 -----------------------------------------------------------
 * Time created: 20:12:07 ---------------------------------------------------
 * Package: mathaid.calculator.base.typeset ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: Segment.java ------------------------------------------------------
 * Class name: Segment ------------------------------------------------
 */
/**
 * At the root of the {@link Segment} hierarchy is the {@code Segment}
 * interface.
 * <p>
 * A {@code Segment} can be a unit of mathematical textual data to a composite
 * object representing math with complex data and formulae. They can be thought
 * of as a TeX (or MathML, AsciiMath) code in memory because their purpose is to
 * display mathematical expressions on the computer screen in a way that is both
 * beautiful, editable and easy to recognise (from academic conventions like
 * calculus).
 * <p>
 * A {@code Segment} maintains 2 distinct representation of a mathematical
 * formula.
 * <ol>
 * <li>The representation meant for display such as a TeX (or MathML)
 * representation. This is made possible via {@link #format}</li>
 * <li>The representation meant for evaluation such as a string that can be
 * evaluated by a Mathematica engine. This is made possible via
 * {@link #toString(Appendable, Log, List)}</li>
 * </ol>
 * <p>
 * A {@code Segment} can be formatted and it's contents may be edited.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface Segment {

	/*
	 * Date: 14 Nov 2023 -----------------------------------------------------------
	 * Time created: 09:07:56 ---------------------------------------------------
	 * Package: mathaid.calculator.base.typeset
	 * ------------------------------------------------ Project: CalculatorProject
	 * ------------------------------------------------ File: Segment.java
	 * ------------------------------------------------------ Class name: Type
	 * ------------------------------------------------
	 */
	/**
	 * Class comprising of a set of static fields that are used within
	 * {@code Segment}s to represent their types using {@link Segment#getType()}.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 */
	static final class Type {

		/**
		 * A constant specifying that a {@link Segment} can be edited
		 */
		public static final int EDITABLE = 1;
		/**
		 * A constant specifying that a {@link Segment} cannot be edited
		 */
		public static final int NON_EDITABLE = -1;

		/**
		 * A constant specifying that a {@link Segment} is an number and/or a letter
		 * (used for constant and variable representations) type such as Hexadecimal
		 * digits.
		 */
		public static final int ALPHANUMERIC = 2;
		/**
		 * A constant specifying that a {@link Segment} is a symbol such as an operator
		 * or separator
		 */
		public static final int SYMBOL = 5;
		/**
		 * A constant specifying that a {@link Segment} is a separator
		 */
		public static final int PUNCTUATION = 3;
		/**
		 * A constant specifying that a {@link Segment} is a whitespace
		 */
		public static final int WHITESPACE = 4;
		/**
		 * A constant specifying that a {@link Segment} is composite, comprising of
		 * other segments.
		 */
		public static final int OBJECT = 7;
		/**
		 * A constant specifying that a {@link Segment} is empty. An empty segment is a
		 * segment that may exist in a place where the value {@code null} may be
		 * applicable but unrealistic (maybe because it may compromise the structure).
		 */
		public static final int EMPTY = 0;

		/**
		 * A constant specifying that a {@link Segment} is a number
		 */
		public static final int NUMERIC = ALPHANUMERIC * 2;
		/**
		 * A constant specifying that a {@link Segment} is non-numerical text
		 */
		public static final int TEXT = ALPHANUMERIC * 3;

		/**
		 * A constant specifying that a {@link Segment} is a decimal value (digit).
		 */
		public static final int DECIMAL = NUMERIC * 2;
		/**
		 * A constant specifying that a {@link Segment} not a decimal value.
		 */
		public static final int NON_DECIMAL = NUMERIC * 3;

		/**
		 * A constant specifying that a {@link Segment} is an integer value (digit).
		 */
		public static final int INTEGER = NUMERIC * 4;
		/**
		 * A constant specifying that a {@link Segment} is a mantissa value (digit).
		 * This may enable some implementation to perform special action on it such as
		 * displaying recurring mantissa digits.
		 */
		public static final int MANTISSA = NUMERIC * 5;

		/**
		 * A constant specifying that a {@link Segment} has uses ellipsis over its
		 * recurring mantissa values (digits).
		 */
		public static final int ELLIPSIS = MANTISSA * 2;
		/**
		 * A constant specifying that a {@link Segment} has uses vinculum over its
		 * recurring mantissa values (digits).
		 */
		public static final int VINCULUM = MANTISSA * 3;
		/**
		 * A constant specifying that a {@link Segment} has uses dots over its first and
		 * last recurring mantissa values (digits).
		 */
		public static final int DOT = MANTISSA * 4;
		/**
		 * A constant specifying that a {@link Segment} has uses dots over all its
		 * recurring mantissa values (digits).
		 */
		public static final int DOT_ALL = MANTISSA * 5;
		/**
		 * A constant specifying that a {@link Segment} has uses dot over a single
		 * recurring mantissa value and a vinculum over multiple recurring mantissa
		 * values (digits).
		 */
		public static final int DOT_BAR = MANTISSA * 6;
		/**
		 * A constant specifying that a {@link Segment} has uses an arc over its
		 * recurring mantissa values (digits).
		 */
		public static final int ARC = MANTISSA * 7;
		/**
		 * A constant specifying that a {@link Segment} has uses parenthesis over its
		 * recurring mantissa values (digits).
		 */
		public static final int PARENTHESISED = MANTISSA * 8;

		/**
		 * A constant specifying that a {@link Segment} which is a {@link #TEXT} is
		 * case-sensitive
		 */
		public static final int CASED = TEXT * 2;
		/**
		 * A constant specifying that a {@link Segment} which is a {@link #TEXT} is
		 * case-insensitive
		 */
		public static final int NON_CASED = TEXT * 3;

		/**
		 * A constant specifying that a {@link Segment} that is a {@link #TEXT} which is
		 * case-sensitive is all upper-case.
		 */
		public static final int UPPER = CASED * 2;
		/**
		 * A constant specifying that a {@link Segment} that is a {@link #TEXT} which is
		 * case-sensitive is all lower-case.
		 */
		public static final int LOWER = CASED * 3;

		/**
		 * A constant specifying that a {@link Segment} that is a {@link #TEXT} is a
		 * bound variable type.
		 */
		public static final int VAR_BOUND = UPPER * 2;

		/**
		 * A constant specifying that a {@link Segment} that is a {@link #TEXT} is a
		 * constant.
		 */
		public static final int CONSTANT = LOWER * 2;
		/**
		 * A constant specifying that a {@link Segment} that is a {@link #TEXT} which is
		 * free variable.
		 */
		public static final int VAR_FREE = LOWER * 3;

		/**
		 * A constant specifying that a {@link Segment} that is a {@link #TEXT} which is
		 * case-insensitive is a representation of a unit such as cm mm km etc.
		 */
		public static final int UNIT = NON_CASED * 2;

		/**
		 * A constant specifying that a {@link Segment} that is a {@link #PUNCTUATION} is in fact an operator.
		 */
		public static final int OPERATOR = PUNCTUATION * 2;
		/**
		 * A constant specifying that a {@link Segment} that is a {@link #PUNCTUATION} is in fact a separator.
		 */
		public static final int SEPARATOR = PUNCTUATION * 3;
		/**
		 * A constant specifying that a {@link Segment} that is a {@link #PUNCTUATION} is in fact a delimiter.
		 */
		public static final int DELIMITER = PUNCTUATION * 4;
		/**
		 * A constant specifying that a {@link Segment} that is a {@link #PUNCTUATION} is in fact a pair.
		 * A pair is any character that has a corresponding flipped character such as {@code ()}.
		 */
		public static final int PAIR = PUNCTUATION * 5;

		/**
		 * A constant specifying that a {@link Segment} that is a {@link #OPERATOR} is in fact a prefix.
		 */
		public static final int PREFIX = OPERATOR * 2;
		/**
		 * A constant specifying that a {@link Segment} that is a {@link #OPERATOR} is in fact an infix.
		 */
		public static final int INFIX = OPERATOR * 3;
		/**
		 * A constant specifying that a {@link Segment} that is a {@link #OPERATOR} is in fact a postfix.
		 */
		public static final int SUFFIX = OPERATOR * 4;

		/**
		 * A constant specifying that a {@link Segment} that is a {@link #OPERATOR} is in fact a prefix-plus '+'..
		 */
		public static final int PREFIX_PLUS = PREFIX * 2;
		/**
		 * A constant specifying that a {@link Segment} that is a {@link #OPERATOR} is in fact a prefix-minus.
		 */
		public static final int PREFIX_MINUS = PREFIX * 2;

		/**
		 * A constant specifying that a {@link Segment} that is a {@link #SEPARATOR} is in fact a separator.
		 */
		public static final int POINT = SEPARATOR * 2;


		/**
		 * A constant specifying that a {@link Segment} that is a {@link #PAIR} is in fact a left pair.
		 */
		public static final int LEFT = PAIR * 2;

		/**
		 * A constant specifying that a {@link Segment} that is a {@link #SEPARATOR} is in fact a right pair.
		 */
		public static final int RIGHT = PAIR * 3;

		/**
		 * A constant specifying that a {@link Segment} that is a {@link #LEFT} is in fact a left-parenthesis.
		 */
		public static final int L_PARENTHESIS = LEFT * 2;
		/**
		 * A constant specifying that a {@link Segment} that is a {@link #RIGHT} is in fact a right-parenthesis.
		 */
		public static final int R_PARENTHESIS = RIGHT * 2;

		/**
		 * A constant specifying that a {@link Segment} that is a {@link #WHITESPACE} is in fact a horizontal whitespace.
		 */
		public static final int H_WHITESPACE = WHITESPACE * 2;
		/**
		 * A constant specifying that a {@link Segment} that is a {@link #WHITESPACE} is in fact a vertical whitespace.
		 */
		public static final int V_WHITESPACE = WHITESPACE * 3;

		/**
		 * A constant specifying that a {@link Segment} that is an {@link #OBJECT} is in fact a function.
		 */
		public static final int FUNCTION = OBJECT * 2;
		/**
		 * A constant specifying that a {@link Segment} that is an {@link #OBJECT} is in fact a fraction.
		 */
		public static final int FRACTION = OBJECT * 3;
		/**
		 * A constant specifying that a {@link Segment} that is an {@link #OBJECT} is in fact a mixed fraction.
		 */
		public static final int M_FRACTION = OBJECT * 4;
		/**
		 * A constant specifying that a {@link Segment} that is an {@link #OBJECT} is in fact a an uneditable auto complete segment.
		 */
		public static final int AUTO_COMPLETE = OBJECT * 5;
		/**
		 * A constant specifying that a {@link Segment} that is an {@link #OBJECT} is in fact an exponent.
		 */
		public static final int EXPONENT = OBJECT * 6;

	}

	/*
	 * Date: 13 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:28:14 ---------------------------------------------------
	 */
	/**
	 * Returns the type of the expression which is one of the constants in the
	 * {@link Segment.Type} class.
	 * <p>
	 * This gives an identifier to the {@code Segment}, thus enabling operations to
	 * be performed on the basis of it's type. For example, the quadratic formula
	 * consists of the following types:
	 * <ul>
	 * <li>Fraction: the whole formula has a numerator and denominator part</li>
	 * <li>Operators: these include -, +, &pm;</li>
	 * <li>Numbers: these are plain numbers in the formula</li>
	 * <li>Square root</li>
	 * </ul>
	 * From the above, we can deduce that parts of the formula can be related with
	 * according to the corresponding type of that part. Each part is not the same,
	 * even though the formula will be considered as a single {@code Segment},
	 * therefore different parts may have different methods by which we may relate
	 * to them. Some {@code Segment}s have editable parts, others do not. Some may
	 * consists of multiple parts (composition) others may be scalar.
	 * 
	 * @return an {@code int} that represents the type of this {@code Segment}
	 * @implNote The typing of {@code Segment}s is currently implemented as an
	 *           {@code int} specifying it's type as an identifier, however, we
	 *           intend to use an array of long to represent an inheritance
	 *           hierarchy of the {@code Segment} type structure. An example of this
	 *           method is the tape structure of the SIMDJSON project. All
	 *           {@code Segment} object types are derived from 2 types: EDITABLE and
	 *           NON_EDITABLE.
	 */
	int getType();

	/*
	 * Date: 13 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:10:45 ---------------------------------------------------
	 */
	/**
	 * Returns {@code true} if this {@code Segment} can receive focus (is editable)
	 * and is the current holder of the focus. This is usually shown by displaying a
	 * blinking cursor on this particular {@code Segment}.
	 * 
	 * @return {@code true} if and only if the focus is on this {@code Segment},
	 *         otherwise will return {@code false}.
	 */
	boolean isFocused();

	/*
	 * Date: 13 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:10:45 ---------------------------------------------------
	 */
	/**
	 * Returns {@code true} if this {@code Segment} contains any syntactic or
	 * semantic error. This is usually shown by displaying some sort of graphical
	 * colouring on this particular {@code Segment} to indicate an error on it.
	 * 
	 * @return {@code true} if and only if there is an error on this
	 *         {@code Segment}, otherwise will return {@code false}.
	 */
	boolean hasError();

	/*
	 * Date: 13 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:33:33 ---------------------------------------------------
	 */
	/**
	 * Formats this {@code Segment} possibly with side-effects into a specified
	 * mathematical string such as a TeX string, marking it with the given
	 * {@link mathaid.calculator.base.typeset.Formatter Formatter} only if this is a
	 * focusable position and stores the formatted string in the given
	 * {@code Appendable}. A focusable position is the position in which input can
	 * be inserted within the {@code Segment}.
	 * 
	 * @param a       an {@code Appendable} such as a {@code StringBuilder} to store
	 *                the format of this {@code Segment}
	 * @param f       a {@code Formatter} that create additional strings in selected
	 *                indexes within the format to ease readability and enhance
	 *                beautification of the resultant display. This is responsible
	 *                for displaying the cursor when {@link #isFocused} is
	 *                {@code true} and for showing where a syntactic error occurred
	 *                by colouring the {@code Segment} where it occurred.
	 * @param indexes stores the current state of the index of this {@code Segment}
	 *                which can be accessible by {@code Formatter}s and other
	 *                {@code Segment} objects and can also help in simulating the
	 *                caret position.
	 * @throws IndexOutOfBoundsException particularly if the given {@code indexes}
	 *                                   argument is not in the expected order,
	 *                                   which may cause the internal code to call
	 *                                   non-existing {@code Segment}s.
	 */
	void format(Appendable a, Formatter f, List<Integer> indexes) throws IndexOutOfBoundsException;

	/*
	 * Date: 13 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:46:56 ---------------------------------------------------
	 */
	/**
	 * Inserts the string representation of this {@code Segment} (possibly with
	 * side-effects) into the given {@code Appendable}. The resultant string may be
	 * evaluated by an evaluation library or program such as Symja, Mathmatica etc.
	 * 
	 * @param a       an {@code Apendable} such as a {@link StringBuilder} to store
	 *                the evaluatable string representation of this {@code Segment}.
	 * @param l       the logger or warnings and errors as well as informative
	 *                messages.
	 * @param indexes
	 * @throws IndexOutOfBoundsException particularly if the given {@code indexes}
	 *                                   argument is not in the expected order,
	 *                                   which may cause the internal code to call
	 *                                   non-existing {@code Segment}s.
	 */
	void toString(Appendable a, Log l, List<Integer> indexes) throws IndexOutOfBoundsException;

}