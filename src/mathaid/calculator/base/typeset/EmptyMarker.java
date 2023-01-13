
/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.List;

class EmptyMarker implements Marker {
	EmptyMarker() {
	}

	public String mark(Segment s, String f, int t, List<Integer> p) {
		return f;
	}
}