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
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class ErrorMarker implements Marker {

	/*
	 * Date: 1 Sep 2022----------------------------------------------------------- 
	 * Time created: 10:46:17--------------------------------------------------- 
	 */
	/**
	 */
	public ErrorMarker() {
	}
	
	public boolean isMarked() {
		return current != null;
	}
	
	public List<Integer> getCurrentPosition() {
		return current;
	}

	/*
	 * Most Recent Date: 1 Sep 2022-----------------------------------------------
	 * Most recent time created: 10:46:34--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @param s
	 * @param math
	 * @param type
	 * @param position
	 * @return
	 */
	@Override
	public String mark(Segment s, String math, int type, List<Integer> position) {
		if ((!isMarked()) || (!s.hasError()))
			return math;
		current = Collections.unmodifiableList(position);
		return String.format("\\cssId{%1$s}{\\bbox[red]{ %2$s }}", Formatter.ERROR, math);
	}

	public boolean equals(Object o) {
		return (o instanceof ErrorMarker);
	}
	
	private List<Integer> current;

}
