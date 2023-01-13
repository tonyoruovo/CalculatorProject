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
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class ForwardRunningMarker implements Marker {
	
	public enum InputMode {
		PREPEND,
		INSERT,
		OVERWRITE,
		APPEND
	}

	public static String CARET_ID = "caret";

	/*
	 * Date: 31 Aug 2022-----------------------------------------------------------
	 * Time created: 16:22:28---------------------------------------------------
	 */
	/**
	 */
	public ForwardRunningMarker() {
		this(InputMode.INSERT);
	}

	/*
	 * Date: 31 Aug 2022----------------------------------------------------------- 
	 * Time created: 18:48:27--------------------------------------------------- 
	 */
	/**
	 * @param insert
	 */
	public ForwardRunningMarker(InputMode mode) {
		this.mode = mode;
	}
	
	public InputMode getInputMode() {
		return mode;
	}
	
	public void setInputMode(InputMode i) {
		mode = i;
	}

	public boolean isMarked() {
		return current != null;
	}

	/*
	 * Date: 31 Aug 2022-----------------------------------------------------------
	 * Time created: 17:07:36--------------------------------------------
	 */
	/**
	 * Note that the {@code List} returned is not modifiable and may not even be an
	 * {@code ArrayList}.
	 * 
	 * @return an unmodifiable {@code List} representing the current caret position
	 *         within a {@code Segment}.
	 */
	public List<Integer> getCurrentPosition() {
		return current;
	}
	
	private String getFormat(String math) {
		switch(mode) {
		case INSERT:
		default:
			return String.format("\\cssId{%1$s}{\\left|%2$s\\right.}", CARET_ID, math);
		case APPEND:
			return String.format("\\cssId{%1$s}{\\left\\lfloor %2$s \\right.}", CARET_ID, math);
		case OVERWRITE:
			return String.format("\\cssId{%1$s}{\\bbox[black]{ %2$s }}", CARET_ID, math);
		case PREPEND:
			return String.format("\\cssId{%1$s}{\\left. %2$s \\right\\rfloor}", CARET_ID, math);
		}
	}

	/*
	 * Most Recent Date: 31 Aug 2022-----------------------------------------------
	 * Most recent time created: 16:22:28--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param s
	 * @param math
	 * @param type
	 * @param position
	 * @return
	 */
	@Override
	public String mark(Segment s, String math, int type, List<Integer> position) {
		if ((!isMarked()) || (!s.isFocused()))
			return math;
		current = Collections.unmodifiableList(position);
		return getFormat(math);
	}

	public boolean equals(Object o) {
		return (o instanceof ForwardRunningMarker);
	}

	private List<Integer> current;
	private InputMode mode;
}
