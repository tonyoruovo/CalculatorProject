
/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public interface Formatter {

	int STYLE = 0x0;
	int CSSID = 0x1;
	int CLASS_NAME = 0x2;
	//other markers can be here
	int CARET = 0x7F_FF_FF_FE;
	int ERROR = 0x7F_FF_FF_FF;
	
	static Formatter empty() {
		Map<Integer, Marker> m = new HashMap<>();
		m.put(0, new EmptyMarker());
		return new BasicFormatter(Collections.unmodifiableMap(m));
	}

	String format(Segment segment, String format, int type, List<Integer> position);
	ForwardRunningMarker getCaretMarker();
	ErrorMarker getErrorMarker();
	CssIdMarker getCSSIDMarker();
	StyleMarker getStyleMarker();
	ClassNameMarker getClassNameMarker();
	Marker get(int code) throws NoSuchElementException;
	boolean addMarker(int code, Marker element);
	boolean removeMarker(int code);
	boolean disableMarker(int code);
	boolean enableMarker(int code);
}