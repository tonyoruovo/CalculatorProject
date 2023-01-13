/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.List;

public interface Segment {

	static final class Type {

		public static final int EDITABLE = 1;
		public static final int NON_EDITABLE = -1;

		public static final int ALPHANUMERIC = 2;
		public static final int SYMBOL = 5;
		public static final int PUNCTUATION = 3;
		public static final int WHITESPACE = 4;
		public static final int OBJECT = 7;
		public static final int EMPTY = 0;

		public static final int NUMERIC = ALPHANUMERIC * 2;
		public static final int TEXT = ALPHANUMERIC * 3;

		public static final int DECIMAL = NUMERIC * 2;
		public static final int NON_DECIMAL = NUMERIC * 3;

		public static final int INTEGER = NUMERIC * 4;
		public static final int MANTISSA = NUMERIC * 5;

		public static final int ELLIPSIS = MANTISSA * 2;
		public static final int VINCULUM = MANTISSA * 3;
		public static final int DOT = MANTISSA * 4;
		public static final int DOT_ALL = MANTISSA * 5;
		public static final int DOT_BAR = MANTISSA * 6;
		public static final int ARC = MANTISSA * 7;
		public static final int PARENTHESISED = MANTISSA * 8;

		public static final int CASED = TEXT * 2;
		public static final int NON_CASED = TEXT * 3;

		public static final int UPPER = CASED * 2;
		public static final int LOWER = CASED * 3;

		public static final int VAR_BOUND = UPPER * 2;

		public static final int CONSTANT = LOWER * 2;
		public static final int VAR_FREE = LOWER * 3;

		public static final int UNIT = NON_CASED * 2;

		public static final int OPERATOR = PUNCTUATION * 2;
		public static final int SEPARATOR = PUNCTUATION * 3;
		public static final int DELIMITER = PUNCTUATION * 4;
		public static final int PAIR = PUNCTUATION * 5;

		public static final int PREFIX = OPERATOR * 2;
		public static final int INFIX = OPERATOR * 3;
		public static final int SUFFIX = OPERATOR * 4;

		public static final int PREFIX_PLUS = PREFIX * 2;
		public static final int PREFIX_MINUS = PREFIX * 2;

		public static final int POINT = SEPARATOR * 2;

		public static final int LEFT = PAIR * 2;
		public static final int RIGHT = PAIR * 3;

		public static final int L_PARENTHESIS = LEFT * 2;
		public static final int R_PARENTHESIS = RIGHT * 2;

		public static final int H_WHITESPACE = WHITESPACE * 2;
		public static final int V_WHITESPACE = WHITESPACE * 3;

		public static final int FUNCTION = OBJECT * 2;
		public static final int FRACTION = OBJECT * 3;
		public static final int M_FRACTION = OBJECT * 4;
		public static final int AUTO_COMPLETE = OBJECT * 5;
		public static final int EXPONENT = OBJECT * 6;

	}

	int getType();

	boolean isFocused();

	boolean hasError();

	void format(Appendable a, Formatter f, List<Integer> indexes) throws IndexOutOfBoundsException;

	void toString(Appendable a, Log l, List<Integer> indexes) throws IndexOutOfBoundsException;

}