/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import mathaid.calculator.base.util.Tuple;

public class BasicFormatter implements Formatter {

	private static Map<Integer, Marker> getDefaults() {
		Map<Integer, Marker> m = new HashMap<>();

		m.put(Formatter.CARET, new ForwardRunningMarker());
		m.put(Formatter.CSSID, new CssIdMarker());
		m.put(Formatter.STYLE, new StyleMarker());
		m.put(Formatter.CLASS_NAME, new ClassNameMarker());
		m.put(Formatter.ERROR, new ErrorMarker());

		return m;
	}

	public BasicFormatter() {
		this(getDefaults());
	}

	public BasicFormatter(Map<Integer, Marker> m) {
		active = new TreeMap<>(m);
		inactive = new HashSet<>();
	}

	public String format(Segment segment, String format, int type, List<Integer> position) {
		for(Marker m : active.values())
			format = m.mark(segment, format, type, position);
		return format;
	}

	public ForwardRunningMarker getCaretMarker() {
		return (ForwardRunningMarker) active.get(CARET);
	}

	public ErrorMarker getErrorMarker() {
		return (ErrorMarker) active.get(ERROR);
	}

	public CssIdMarker getCSSIDMarker() {
		return (CssIdMarker) active.get(CSSID);
	}

	public StyleMarker getStyleMarker() {
		return (StyleMarker) active.get(STYLE);
	}

	public ClassNameMarker getClassNameMarker() {
		return (ClassNameMarker) active.get(CLASS_NAME);
	}

	public Marker get(int code) {
		return active.get(code);
	}

	public boolean addMarker(int c, Marker e) {
		for(Tuple.Couple<Integer, Marker> t : inactive) {
			if(t.get() == c || e.equals(t.get2nd()))
				return false;
		}
		if(active.containsKey(c) || active.containsValue(e))
			return false;
		active.put(c, e);
		return true;
	}

	public boolean removeMarker(int c) {
		Object o = active.remove(c);
		for(Tuple.Couple<Integer, Marker> t : inactive) {
			if(t.get() == c)
				return inactive.remove(t);
		}
		return o != null;
	}

	public boolean disableMarker(int code) {
		Marker m = active.remove(code);
		if(m == null || inactive.contains(Tuple.of(code, m)))
			return false;
		return inactive.add(Tuple.of(code, m));
	}

	public boolean enableMarker(int code) {
		Tuple.Couple<Integer, Marker> tp = null;
		for(Tuple.Couple<Integer, Marker> t : inactive) {
			if(t.get() == code) {
				tp = t;
				break;
			}
		}
		if(tp != null) {
			active.put(code, tp.get2nd());
			return true;
		}
		return false;
	}

	private final NavigableMap<Integer, Marker> active;
	private final Set<Tuple.Couple<Integer, Marker>> inactive;
}