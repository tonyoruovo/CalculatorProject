
/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.List;

/*
 * Date: 25 Nov 2023 -----------------------------------------------------------
 * Time created: 20:23:06 ---------------------------------------------------
 * Package: mathaid.calculator.base.typeset ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: CssIdMarker.java ------------------------------------------------------
 * Class name: CssIdMarker ------------------------------------------------
 */
/**
 * The {@code Marker} that inserts a unique id in to the segment tree to allow
 * for reference in the DOM code.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
class CssIdMarker implements Marker {
	/*
	 * Date: 25 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:22:46 ---------------------------------------------------
	 */
	/**
	 * Constructs a {@code CssIdMarker}.
	 */
	public CssIdMarker() {
	}

	/*
	 * Date: 25 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:19:29 ---------------------------------------------------
	 */
	/**
	 * Marks the format of the given segment with a an id so as to be reference in
	 * the DOM.
	 * 
	 * @param s a valid {@code Segment} which is a reference to the owner of the
	 *          {@code math} to be formatted.
	 * @param f the value to be formatted. This is the object of this formatting
	 *          operation.
	 * @param t any value. Not used by this method.
	 * @param p the position of the current segment in the segment tree. Please see
	 *          {@link SegmentBuilder} for details on positional indexes.
	 * @return the format after formatting.
	 */
	public String mark(Segment s, String f, int t, List<Integer> p) {
		if (s != null) {
			if (p == null || p.size() <= 0)
				throw new IllegalStateException("position is either null or empty");
			return String.format("\\cssId{%1$s}{%2$s}", p, f);
		}

		return f;
	}

	/*
	 * Date: 25 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:17:29 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param o {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	public boolean equals(Object o) {
		return (o instanceof CssIdMarker);
	}
}