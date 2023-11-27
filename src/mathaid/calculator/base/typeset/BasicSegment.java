/**
 * 
 */
package mathaid.calculator.base.typeset;

import static mathaid.calculator.base.typeset.Segment.Type.INTEGER;
import static mathaid.calculator.base.typeset.Segment.Type.POINT;

import java.io.IOException;
import java.util.List;

import mathaid.calculator.FatalReadException;

/*
 * Date: 17 Nov 2023 -----------------------------------------------------------
 * Time created: 04:56:21 ---------------------------------------------------
 * Package: mathaid.calculator.base.typeset ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: BasicSegment.java ------------------------------------------------------
 * Class name: BasicSegment ------------------------------------------------
 */
/**
 * A concrete basic implementation for simple segments.
 * <p>
 * This class mostly does separate format from value. Both are not publicly
 * exposed (via public getters/fields), but their effects can be seen from
 * {@link #format(Appendable, Formatter, List)} and
 * {@link #toString(Appendable, Log, List)} respectively.
 * <p>
 * This class is created for use by segments without children such as numbers,
 * variables, constants etc.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class BasicSegment extends AbstractSegment {
	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:59:45 ---------------------------------------------------
	 */
	/**
	 * Creates a {@code BasicSegment} by specifying all properties. This is mostly
	 * used for aiding immutability of this class.
	 * 
	 * @param ft the format the image i.e the value to be used
	 *           {@link #format(Appendable, Formatter, List)}. This is a TeX based
	 *           value.
	 * @param s  the value to be for {@link #toString(Appendable, Log, List)}.
	 * @param t  the type of this segment specified by {@link #getType()}.
	 * @param f  the focus of this segment {@link #isFocused()}.
	 * @param e  the error of this segment {@link #hasError()}.
	 * @param ls the sibling of this segment, specified by {@link #getSibling()}.
	 */
	protected BasicSegment(String ft, String s, int t, boolean f, boolean e, LinkedSegment ls) {
		super(t, f, e, ls, new LinkedSegment[] {}, -1, -1);
		this.s = s;
		this.f = ft;
	}

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:38:22 ---------------------------------------------------
	 */
	/**
	 * Creates a {@code BasicSegment} without specifying a focus, error or a
	 * sibling.
	 * 
	 * @param f the format the image i.e the value to be used
	 *          {@link #format(Appendable, Formatter, List)}. This is a TeX based
	 *          value.
	 * @param s the value to be for {@link #toString(Appendable, Log, List)}.
	 * @param t the type of this segment specified by {@link #getType()}.
	 */
	public BasicSegment(String f, String s, int t) {
		this(f, s, t, false, false, null);
	}

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:42:50 ---------------------------------------------------
	 */
	/**
	 * Creates a {@code BasicSegment} where the format is the same as the value.
	 * 
	 * @param s the format and the value
	 * @param t the type of this segment specified by {@link #getType()}.
	 */
	public BasicSegment(String s, int t) {
		this(s, s, t);
	}

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:46:29 ---------------------------------------------------
	 */
	/**
	 * Sets the focus of the segment, located at the given index, to the
	 * {@link boolean} argument.
	 * 
	 * @param i the index specifying the location of the segment whose focus is to
	 *          be set.
	 * @param f the focus to be set.
	 * @return {@code this} as the head.
	 */
	public LinkedSegment setFocus(int i, boolean f) {
		if (i == 0)
			return new BasicSegment(this.f, s, getType(), f, hasError(), getSibling());
		else if (i > 0 && hasSibling())
			return new BasicSegment(this.f, s, getType(), isFocused(), hasError(), getSibling().setFocus(i - 1, f));
		throw new IndexOutOfBoundsException(i + " is out of bounds or negative");
	}

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:46:29 ---------------------------------------------------
	 */
	/**
	 * Sets the error of the segment, located at the given index, to the
	 * {@link boolean} argument.
	 * 
	 * @param i the index specifying the location of the segment whose error is to
	 *          be set.
	 * @param e the error to be set.
	 * @return {@code this} as the head.
	 * @throws IndexOutOfBoundsException if the index would point to a {@code null}
	 *                                   node.
	 */
	public LinkedSegment setError(int i, boolean e) throws IndexOutOfBoundsException {
		if (i == 0)
			return new BasicSegment(f, s, getType(), isFocused(), e, getSibling());
		else if (i > 0 && hasSibling())
			return new BasicSegment(f, s, getType(), isFocused(), hasError(), getSibling().setError(i - 1, e));
		throw new IndexOutOfBoundsException(i + " is out of bounds or negative");
	}

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:16:43 ---------------------------------------------------
	 */
	/**
	 * Sets the sibling of the segment at the given index to the second argument. If
	 * the {@code i == 0} then the current segment's sibling is set to the second
	 * argument, else the method will recursively traverse this segment tree in
	 * search of the node at the given index and sets it sibling to the second
	 * argument when found.
	 * 
	 * @param i a non-negative value specifying the position of the segment to whose
	 *          sibling is to be set.
	 * @param s the sibling to be set.
	 * @return {@code this} as the head. This is usually called recursively, hence
	 *         when called from the head, that same head is what's returned.
	 * @throws IndexOutOfBoundsException if the index would point to a {@code null}
	 *                                   node.
	 */
	public LinkedSegment setSibling(int i, LinkedSegment s) throws IndexOutOfBoundsException {
		if (i == 0)
			return new BasicSegment(f, this.s, getType(), isFocused(), hasError(), s);
		else if (i > 0 && hasSibling())
			return new BasicSegment(f, this.s, getType(), isFocused(), hasError(), getSibling().setSibling(i - 1, s));
		throw new IndexOutOfBoundsException(i + " is out of bounds or negative");
	}

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:26:06 ---------------------------------------------------
	 */
	/**
	 * Appends the argument to the last node of this segment tree.
	 * 
	 * @param ls the node to be concatenated. {@code this} as the head. This is
	 *           usually called recursively, hence when called from the head, that
	 *           same head is what's returned.
	 */
	public LinkedSegment concat(LinkedSegment ls) {
		if (!hasSibling()) {
			if (ls instanceof Digit) {
				Digit d = (Digit) ls;
				if (getType() == POINT) {
					if (d.getType() == INTEGER)
						return new BasicSegment(f, s, getType(), isFocused(), hasError(), d.toMantissaDigit());
				} else
					return new BasicSegment(f, s, getType(), isFocused(), hasError(), d.toDigit());
			}
			return new BasicSegment(f, s, getType(), isFocused(), hasError(), ls);
		}
		return new BasicSegment(f, s, getType(), isFocused(), hasError(), getSibling().concat(ls));
	}

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:28:00 ---------------------------------------------------
	 */
	/**
	 * Appends the format (TeX) to the provided {@link Appendable}, using the given
	 * {@code Formatter} apply addtional formatting to the generated code (such as
	 * minifying, prettifying, punctuation --like enforcing parenthesis --,
	 * coloring, error marking, focus, marking etc). And afterwards, recursively
	 * calls this method for it's siblings.
	 * 
	 * @param a {@inheritDoc}
	 * @param l {@inheritDoc}
	 * @param p {@inheritDoc}
	 * @throws IndexOutOfBoundsException if the positional indices (specified by the
	 *                                   {@code List} argument) would point to a
	 *                                   {@code null} node.
	 */
	public void format(Appendable a, Formatter f, List<Integer> p) {
		/* Update the current position of the cursor */
		p.set(p.size() - 1, p.get(p.size() - 1) + 1);

		try {
			a.append(f.format(this, this.f, getType(), p));
		} catch (IOException e) {
		}

		if (hasSibling())
			getSibling().format(a, f, p);

	}

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:28:00 ---------------------------------------------------
	 */
	/**
	 * Appends the value (Symja) to the provided {@link Appendable}, using the given
	 * {@code Log} to record an info, warning or error that occurred. Afterwards,
	 * recursively calls this method for it's siblings.
	 * 
	 * @param a {@inheritDoc}
	 * @param l {@inheritDoc}
	 * @param p {@inheritDoc}
	 * @throws IndexOutOfBoundsException if the positional indices (specified by the
	 *                                   {@code List} argument) would point to a
	 *                                   {@code null} node.
	 */
	public void toString(Appendable a, Log l, List<Integer> p) throws IndexOutOfBoundsException {
		/* Update the current position of the cursor */
		p.set(p.size() - 1, p.get(p.size() - 1) + 1);
		try {
			a.append(s);
		} catch (IOException e) {
		} catch (FatalReadException e) {
			throw new FatalParseException(e.getMessage(), e, p);
		}

		if (hasSibling())
			getSibling().toString(a, l, p);
	}

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:36:19 ---------------------------------------------------
	 */
	/**
	 * Checks if this segment is equal to the argument by casting it to a
	 * {@code BasicSegment} and comparing the type, value and format with that of
	 * this object. Will only return {@link tru} if the value type and format are
	 * equal to the argument's.
	 * 
	 * @param o {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	public boolean equals(Object o) {
		if (o instanceof BasicSegment) {
			BasicSegment bs = (BasicSegment) o;
			return getType() == bs.getType() && s.equals(bs.s) && f.equals(bs.f);
		}
		return false;
	}
/*
 * Date: 17 Nov 2023 -----------------------------------------------------------
 * Time created: 07:40:01 ---------------------------------------------------
 */
/**
 * Gets the string representation of this object. This implementation includes the
 * {@link #getType()} property as a hex string.
 * 
 * @return the string generated.
 */
	public String toString() {
		return Integer.toHexString(getType());
	}

	/**
	 * Represents the expression of this segment
	 */
	private final String s;// string
	/**
	 * Represents the rendered format of this segment
	 */
	private final String f;// format
}