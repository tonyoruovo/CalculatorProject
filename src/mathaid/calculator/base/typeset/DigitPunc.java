
/**
 * 
 */
package mathaid.calculator.base.typeset;

/**
 * An immutable punctuator for the {@link Digit} class. It represents formatting
 * options for digits in a number tree.
 */
public class DigitPunc {

	/*
	 * Date: 25 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:34:34 ---------------------------------------------------
	 */
	/**
	 * Constructs a {@code DigitPunc} with default punctuations.
	 */
	public DigitPunc() {
		this(",", ".", "\\,", 3, 3, 3);
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:21:17 ---------------------------------------------------
	 */
	/**
	 * Creates a {@code DigitPunc} by specifying all it's properties.
	 * 
	 * @param intSep        the value to be used as digit separator within the
	 *                      digits with the {@link Segment.Type#INTEGER} type in the
	 *                      number sub-tree.
	 * @param point         the value to be used as radix point.
	 * @param mantSep       the value to be used as digit separator within the
	 *                      digits with the {@link Segment.Type#MANTISSA} type in
	 *                      the number sub-tree.
	 * @param intGroupSize  the number of digits in-between separators in the
	 *                      integer portion of the number sub-tree.
	 * @param mantGroupSize the number of digits in-between separators in the
	 *                      mantissa portion of the number sub-tree.
	 * @param numOfRepeats  the number of repeats be fore the ellipsis is appended
	 *                      to the result. Useful only for {@code Digit} nodes with
	 *                      the type {@link Segment.Type#ELLIPSIS}.
	 */
	public DigitPunc(String intSep, String point, String mantSep, int intGroupSize, int mantGroupSize,
			int numOfRepeats) {
		is = intSep;
		p = point;
		ms = mantSep;
		igs = intGroupSize;
		mgs = mantGroupSize;
		nor = numOfRepeats;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:28:35 ---------------------------------------------------
	 */
	/**
	 * Gets the separator used when formatting integer {@code Digit} nodes within a
	 * number tree.
	 * 
	 * @return the value to be used as digit separator within the digits with the
	 *         {@link Segment.Type#INTEGER} type in the number sub-tree.
	 */
	public String getIntSeparator() {
		return is;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:28:35 ---------------------------------------------------
	 */
	/**
	 * Gets the separator used when formatting mantissa {@code Digit} nodes within a
	 * number tree.
	 * 
	 * @return the value to be used as digit separator within the digits with the
	 *         {@link Segment.Type#MANTISSA} type in the number sub-tree.
	 */
	public String getMantSeparator() {
		return ms;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:28:35 ---------------------------------------------------
	 */
	/**
	 * Gets the value that separates the integer from the mantissa {@code Digit}
	 * nodes within a number tree.
	 * 
	 * @return the radix point.
	 */
	public String getPoint() {
		return p;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:28:35 ---------------------------------------------------
	 */
	/**
	 * Gets the number of {@code Digit} nodes between every
	 * {@link #getIntSeparator()} within a number tree.
	 * 
	 * @return the number of digits in-between separators in the integer portion of
	 *         the number sub-tree.
	 */
	public int getIntGroupSize() {
		return igs;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:28:35 ---------------------------------------------------
	 */
	/**
	 * Gets the number of {@code Digit} nodes between every
	 * {@link #getMantSeparator()} within a number tree.
	 * 
	 * @return the number of digits in-between separators in the mantissa portion of
	 *         the number sub-tree.
	 */
	public int getMantGroupSize() {
		return mgs;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:28:35 ---------------------------------------------------
	 */
	/**
	 * Gets the number of recurring digits to repeat before finally appending the
	 * ellipsis. This is used exclusively by recurring digits with the
	 * {@link Segment.Type#ELLIPSIS} type.
	 * 
	 * @return  the number of repeats be fore the ellipsis is appended
	 *                      to the result. Useful only for {@code Digit} nodes with
	 *                      the type {@link Segment.Type#ELLIPSIS}.
	 */
	public int getNumOfRecurringDigits() {
		return nor;
	}

	/**
	 * The value used as the integer separator
	 */
	private final String is;
	/**
	 * The value used as the mantissa separator
	 */
	private final String ms;
	/**
	 * The value used as the separator that delimits between the integer portion and the mantissa portion.
	 */
	private final String p;
	/**
	 * The value for the number of integer digits to be formatted before applying the {@link #getIntSeparator()}.
	 */
	private final int igs;
	/**
	 * The value for the number of mantissa digits to be formatted before applying the {@link #getMantSeparator()}.
	 */
	private final int mgs;
	/**
	 * The number of recurring digit(s) to be appended when formatting {@code Segment.Type.ELLIPSIS} types.
	 */
	private final int nor;
}