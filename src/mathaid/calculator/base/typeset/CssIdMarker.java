
/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.List;

class CssIdMarker implements Marker {
	public CssIdMarker() {
	}

	public String mark(Segment s, String f, int t, List<Integer> p) {
		if(s != null) {
			if(p == null || p.size() <= 0)
				throw new IllegalStateException("position is either null or empty");
			return String.format("\\cssId{%1$s}{%2$s}", p, f);
		}

		return f;
	}

	public boolean equals(Object o) {
		return (o instanceof CssIdMarker);
	}
}