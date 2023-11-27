/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.Collections;
import java.util.List;

/*
 * Date: 1 Sep 2022----------------------------------------------------------- 
 * Time created: 10:46:17---------------------------------------------------  
 * Package: calculator.typeset------------------------------------------------ 
 * Project: InputTestCode------------------------------------------------ 
 * File: ErrorMarker.java------------------------------------------------------ 
 * Class name: ErrorMarker------------------------------------------------ 
 */
/**
 * The {@code Marker} that renders the error component on the segment tree.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class ErrorMarker implements Marker {

	/*
	 * Date: 1 Sep 2022-----------------------------------------------------------
	 * Time created: 10:46:17---------------------------------------------------
	 */
	/**
	 * Creates an {@code ErrorMarker}.
	 */
	public ErrorMarker() {
	}

	/*
	 * Date: 25 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:12:02 ---------------------------------------------------
	 */
	/**
	 * Gets the last position that was marked with the error component.
	 * 
	 * @return the positional index of the last segment in the tree at which
	 *         {@link Segment#hasError()} was <code>true</code>. Please see
	 *         {@link SegmentBuilder} for details on positional indexes.
	 */
	public List<Integer> getCurrentPosition() {
		return current;
	}

	/*
	 * Most Recent Date: 1 Sep 2022-----------------------------------------------
	 * Most recent time created: 10:46:34--------------------------------------
	 */
	/**
	 * Renders the error component on the given math if the specified segment has
	 * error.
	 * 
	 * @param s        a valid {@code Segment} which is a reference to the owner of
	 *                 the {@code math} to be formatted.
	 * @param math     the value to be formatted. This is the object of this
	 *                 formatting operation.
	 * @param type     any value. Not used by this method.
	 * @param position the position of the current segment in the segment tree.
	 *                 Please see {@link SegmentBuilder} for details on positional
	 *                 indexes.
	 * @return the math after formatting.
	 */
	@Override
	public String mark(Segment s, String math, int type, List<Integer> position) {
		if (s == null || (!s.hasError()) || position == null)
			return math;
		current = Collections.unmodifiableList(position);
//		return String.format("\\cssId{%1$s}{\\bbox[red]{ %2$s }}", Formatter.ERROR, math);
		return String.format("\\bbox[red]{ %2$s }", Formatter.ERROR, math);
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
		return (o instanceof ErrorMarker);
	}

	/**
	 * The last position in the segment tree where the error property was
	 * <code>true</code>.
	 */
	private List<Integer> current;

}
