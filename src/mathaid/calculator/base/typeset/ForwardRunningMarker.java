/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.Collections;
import java.util.List;

/*
 * Date: 31 Aug 2022----------------------------------------------------------- 
 * Time created: 16:22:28---------------------------------------------------  
 * Package: calculator.typeset------------------------------------------------ 
 * Project: InputTestCode------------------------------------------------ 
 * File: ForwardRunningMarker.java------------------------------------------------------ 
 * Class name: ForwardRunningMarker------------------------------------------------ 
 */
/**
 * The {@code Marker} that acts as a caret by marking the last segment with the
 * tree focus.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class ForwardRunningMarker implements Marker {

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:40:55 ---------------------------------------------------
	 * Package: mathaid.calculator.base.typeset
	 * ------------------------------------------------ Project: CalculatorProject
	 * ------------------------------------------------ File:
	 * ForwardRunningMarker.java
	 * ------------------------------------------------------ Class name: InputMode
	 * ------------------------------------------------
	 */
	/**
	 * An {@code enum} that represents the kind of caret that the
	 * {@code ForwardRunningMarker} should mark on a segment.
	 * <p>
	 * This can also be combined with the {@code SegmentBuilder} class to implement
	 * a full input method which may restrict the builder object to append only,
	 * prepend only, insert only or overwrite only.
	 * <p>
	 * Note that this object cannot implement a proper input method itself, but can
	 * only help with changing the shape of the caret.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 */
	public enum InputMode {
		/**
		 * Formats the math by setting the caret only at the beginning of the segment.
		 * <p>
		 * Assuming the input math is "x" and {@code caretId} is "cid", the literal
		 * example of the result is:
		 * 
		 * <pre>
		 * <code>\cssId{cid}{\left\lfloorx\right.}</code>
		 * </pre>
		 */
		PREPEND {
			@Override
			public String mark(String math, String caretId) {
				return String.format("\\cssId{%1$s}{\\left\\lfloor %2$s \\right.}", caretId, math);
			}
		},
		/**
		 * Formats the math by setting the caret at any position within the segment. The
		 * position itself will be dictated by the position of the segment with the
		 * focus.
		 * <p>
		 * Assuming the input math is "x" and {@code caretId} is "cid", the literal
		 * example of the result is:
		 * 
		 * <pre>
		 * <code>\cssId{cid}{\left|x\right.}</code>
		 * </pre>
		 */
		INSERT {
			@Override
			public String mark(String math, String caretId) {
				return String.format("\\cssId{%1$s}{\\left|%2$s\\right.}", caretId, math);
			}
		},
		/**
		 * Formats the math by setting a thick caret that covers the current segment
		 * with the focus.
		 * <p>
		 * Assuming the input math is "x" and {@code caretId} is "cid", the literal
		 * example of the result is:
		 * 
		 * <pre>
		 * <code>\cssId{cid}{\bbox[black]{ x }}</code>
		 * </pre>
		 */
		OVERWRITE {
			@Override
			public String mark(String math, String caretId) {
				return String.format("\\cssId{%1$s}{\\bbox[black]{ %2$s }}", caretId, math);
			}
		},
		/**
		 * Formats the math by setting the caret only at the end of the segment.
		 * <p>
		 * Assuming the input math is "x" and {@code caretId} is "cid", the literal
		 * example of the result is:
		 * 
		 * <pre>
		 * <code>\cssId{cid}{\left. x \right\rfloor}</code>
		 * </pre>
		 */
		APPEND {
			@Override
			public String mark(String math, String caretId) {
				return String.format("\\cssId{%1$s}{\\left. %2$s \\right\\rfloor}", caretId, math);
			}
		};

		/*
		 * Date: 24 Nov 2023 -----------------------------------------------------------
		 * Time created: 21:05:54 ---------------------------------------------------
		 */
		/**
		 * Directs the caret at a specific position -- defined by the
		 * {@code ForwardRunningMarker} -- by formatting the {@code math} argument,
		 * rendering a different caret based on the type of {@code this} and returning
		 * the result.
		 * <p>
		 * It is expected and recommended that this be called by
		 * {@code ForwardRunningMarker}.
		 * 
		 * @param math    the value on which to insert the math.
		 * @param caretId the CSS id attribute used for uniquely identifying the caret
		 *                with the HTML DOM.
		 * @return {@code math} after inserting the caret.
		 */
		public abstract String mark(String math, String caretId);
	}

	public static final String CARET_ID = "caret";

	/*
	 * Date: 31 Aug 2022-----------------------------------------------------------
	 * Time created: 16:22:28---------------------------------------------------
	 */
	/**
	 * Creates a {@code ForwardRunningMarker} where
	 * {@code getInputMode() == InputMode.INSERT} and {@link #getCurrentPosition()}
	 * is <code>null</code>.
	 */
	public ForwardRunningMarker() {
		this(InputMode.INSERT);
	}

	/*
	 * Date: 31 Aug 2022-----------------------------------------------------------
	 * Time created: 18:48:27---------------------------------------------------
	 */
	/**
	 * Creates a {@code ForwardRunningMarker} by setting the {@code InputMode}. When
	 * this constructor returns, {@link #getCurrentPosition()} will be
	 * <code>null</code>.
	 * 
	 * @param mode
	 */
	public ForwardRunningMarker(InputMode mode) {
		this.mode = mode;
	}

	/*
	 * Date: 25 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:49:56 ---------------------------------------------------
	 */
	/**
	 * Gets the input mode used for determining the type of caret to use.
	 * 
	 * @return the input mode.
	 */
	public InputMode getInputMode() {
		return mode;
	}

	/*
	 * Date: 25 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:50:50 ---------------------------------------------------
	 */
	/**
	 * Sets the input mode. The input mode is used for determining the type of caret
	 * to use.
	 * 
	 * @param i the input mode to be set.
	 */
	public void setInputMode(InputMode i) {
		mode = i;
	}

	/*
	 * Date: 25 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:51:41 ---------------------------------------------------
	 */
	/**
	 * Checks whether this caret has already inserted the caret into the segment
	 * tree during {@link Segment#format}.
	 * 
	 * @return {@code true} if the caret has been inserted into the segment tree.
	 *         Returns <code>false</code> if it hasn't.
	 */
	public boolean isMarked() {
		return current != null;
	}

	/*
	 * Date: 31 Aug 2022-----------------------------------------------------------
	 * Time created: 17:07:36--------------------------------------------
	 */
	/**
	 * Gets the last position where the caret was inserted into the segment tree.
	 * <p>
	 * Note that the {@code List} returned is not modifiable and may not even be an
	 * {@code ArrayList}.
	 * 
	 * @return an unmodifiable {@code List} representing the current caret position
	 *         within a {@code Segment}, i.e the positional index of the last
	 *         segment in the tree at which {@link Segment#isFocused()} was
	 *         <code>true</code>. Please see {@link SegmentBuilder} for details on
	 *         positional indexes.
	 */
	public List<Integer> getCurrentPosition() {
		return current;
	}

	/*
	 * Date: 25 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:54:53 ---------------------------------------------------
	 */
	/**
	 * Calls the input mode to format the given string.
	 * 
	 * @param math the string to be formatted.
	 * @return the argument after formatting is done.
	 */
	private String getFormat(String math) {
//		switch (mode) {
//		case INSERT:
//		default:
//			return String.format("\\cssId{%1$s}{\\left|%2$s\\right.}", CARET_ID, math);
//		case APPEND:
//			return String.format("\\cssId{%1$s}{\\left\\lfloor %2$s \\right.}", CARET_ID, math);
//		case OVERWRITE:
//			return String.format("\\cssId{%1$s}{\\bbox[black]{ %2$s }}", CARET_ID, math);
//		case PREPEND:
//			return String.format("\\cssId{%1$s}{\\left. %2$s \\right\\rfloor}", CARET_ID, math);
//		}
		return mode.mark(math, CARET_ID);
	}

	/*
	 * Most Recent Date: 31 Aug 2022-----------------------------------------------
	 * Most recent time created: 16:22:28--------------------------------------
	 */
	/**
	 * Inserts the caret into the given {@code math} if the segment is focused and
	 * an insertion has not been made.
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
		if (isMarked() || s == null || (!s.isFocused()) || position == null)
			return math;
//		if (s != null && !s.isFocused())
//			return math;
		current = Collections.unmodifiableList(position);
		return getFormat(math);
	}

	/*
	 * Date: 25 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:01:28 ---------------------------------------------------
	 */
	/**
	 * Call to enable reformatting.
	 */
	public void reset() {
		current = null;
	}

	/*
	 * Date: 25 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:02:04 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param o {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	public boolean equals(Object o) {
		return (o instanceof ForwardRunningMarker);
	}

	/**
	 * The last position in the segment tree where the cursor was inserted.
	 */
	private List<Integer> current;
	/**
	 * Store the input mode.
	 */
	private InputMode mode;
}
