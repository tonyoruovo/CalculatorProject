
/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.List;

/*
 * Date: 25 Nov 2023 -----------------------------------------------------------
 * Time created: 20:24:51 ---------------------------------------------------
 * Package: mathaid.calculator.base.typeset ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: ClassNameMarker.java ------------------------------------------------------
 * Class name: ClassNameMarker ------------------------------------------------
 */
/**
 * The {@code Marker} that inserts the type of a segment as a HTML class
 * attribute in to the segment tree to allow for reference in the DOM code.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
class ClassNameMarker implements Marker {
	/*
	 * Date: 25 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:25:49 ---------------------------------------------------
	 */
	/**
	 * Creates a {@code ClassNameMarker}.
	 */
	public ClassNameMarker() {
	}

	/*
	 * Date: 25 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:26:08 ---------------------------------------------------
	 */
	/**
	 * Inserts the {@code Segment#toString()} value into the format as it's HTML
	 * class name.
	 * 
	 * @param s a valid {@code Segment} which is a reference to the owner of the
	 *          {@code math} to be formatted.
	 * @param f the value to be formatted. This is the object of this formatting
	 *          operation.
	 * @param t any value. Not used by this method.
	 * @param p can be left as <code>null</code>.
	 * @return the format after formatting.
	 */
	public String mark(Segment s, String f, int t, List<Integer> p) {
		if (s != null)
			return String.format("\\class{%1$s}{%2$s}", s, f);
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
		return (o instanceof StyleMarker);
	}
}