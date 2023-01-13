
/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.List;

class ClassNameMarker implements Marker {
	public ClassNameMarker() {
	}

	public String mark(Segment s, String f, int t, List<Integer> p) {
		if(s != null)
			return String.format("\\class{%1$s}{%2$s}", s, f);
		return f;
	}

	public boolean equals(Object o) {
		return (o instanceof StyleMarker);
	}
}