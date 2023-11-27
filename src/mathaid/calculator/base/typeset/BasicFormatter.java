/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/*
 * Date: 23 Nov 2023 -----------------------------------------------------------
 * Time created: 11:06:23 ---------------------------------------------------
 * Package: mathaid.calculator.base.typeset ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: BasicFormatter.java ------------------------------------------------------
 * Class name: BasicFormatter ------------------------------------------------
 */
/**
 * A {@code Formatter} implementation that is based on the MATHJAX (Mathematical
 * JavaScript and XML) display engine.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class BasicFormatter implements Formatter {

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:44:27 ---------------------------------------------------
	 */
	/**
	 * Generates defaults markers used for {@link #BasicFormatter()}.
	 * 
	 * @return the an ordered map of the default markers.
	 */
	private static NavigableMap<Integer, Marker> getDefaults() {
		NavigableMap<Integer, Marker> m = new TreeMap<>();

		m.put(Formatter.CARET, new ForwardRunningMarker());
		m.put(Formatter.CSSID, new CssIdMarker());
		m.put(Formatter.STYLE, new StyleMarker());
		m.put(Formatter.CLASS_NAME, new ClassNameMarker());
		m.put(Formatter.ERROR, new ErrorMarker());

		return m;
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:47:46 ---------------------------------------------------
	 */
	/**
	 * Constructor for {@code BasicFormatter} that has pre-loaded {@code Marker}
	 * objects in the following order:
	 * <ol>
	 * <li>{@link StyleMarker}, for assigning in-line CSS to nodes within the
	 * math.</li>
	 * <li>{@link CssIdMarker}, for assigning HTML id attributes to segment which
	 * creates a unique reference to a single node within the math code.</li>
	 * <li>{@link ClassNameMarker}, for assigning a segment's HTML class attribute
	 * to {@link Segment#getType()}. This allows it to be referenced in the
	 * accompanying CSS so as to style segments.</li>
	 * <li>{@link ForwardRunningMarker}, for painting the segment where
	 * {@link Segment#isFocused()} is <code>true</code>.</li>
	 * <li>{@link ErrorMarker}, for painting segments where
	 * {@link Segment#hasError()} is <code>true</code>.</li>
	 * </ol>
	 */
	public BasicFormatter() {
		this(getDefaults());
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:07:51 ---------------------------------------------------
	 */
	/**
	 * Constructs a {@code BasicFormatter} from an ordered map of markers, whereby
	 * each marker will be invoked based on it position/order in the given
	 * {@code Map}.
	 * 
	 * @param m an ordered map of markers. Note that the keys are also the code of
	 *          the markers.
	 */
	protected BasicFormatter(NavigableMap<Integer, Marker> m) {
		markers = new HashMap<>(m);
		painter = new ArrayList<>(m.keySet());
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:10:58 ---------------------------------------------------
	 */
	/**
	 * Marks the given {@code format} argument by applying every {@code Marker} in
	 * their specified order order, passing the rest of the arguments to them.
	 * 
	 * @param segment  {@inheritDoc}
	 * @param format   {@inheritDoc}
	 * @param type     {@inheritDoc}
	 * @param position {@inheritDoc}
	 * @return the {@code format} after the marking operations are complete.
	 */
	public String format(Segment segment, String format, int type, List<Integer> position) {
		for (int i = 0; i < painter.size(); i++)
			format = markers.get(painter.get(i)).mark(segment, format, type, position);
		return format;
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:15:48 ---------------------------------------------------
	 */
	/**
	 * Get the {@code ForwardRunningMarker} which usually represents the caret.
	 * <p>
	 * Note that the behaviour of this method is undefined if {@link #removeMarker}
	 * was previously called with {@link Formatter#CARET} as it's argument, or if a
	 * {@code ForwardRunningMarker} was never added using {@link Formatter#CARET}.
	 * 
	 * @return the caret of this formatter.
	 */
	public ForwardRunningMarker getCaretMarker() {
		return (ForwardRunningMarker) markers.get(CARET);
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:15:48 ---------------------------------------------------
	 */
	/**
	 * Get the {@code ErrorMarker} which is used to mark error segments.
	 * <p>
	 * Note that the behaviour of this method is undefined if {@link #removeMarker}
	 * was previously called with {@link Formatter#ERROR} as it's argument, or if a
	 * {@code ErrorMarker} was never added using {@link Formatter#ERROR}.
	 * 
	 * @return the error marker of this formatter.
	 */
	public ErrorMarker getErrorMarker() {
		return (ErrorMarker) markers.get(ERROR);
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:15:48 ---------------------------------------------------
	 */
	/**
	 * Get the {@code CssIdMarker} which is used to apply HTML id attribute to the
	 * segments for node-based reference.
	 * <p>
	 * Note that the behaviour of this method is undefined if {@link #removeMarker}
	 * was previously called with {@link Formatter#CSSID} as it's argument, or if a
	 * {@code CssIdMarker} was never added using {@link Formatter#CSSID}.
	 * 
	 * @return the id marker of this formatter.
	 */
	public CssIdMarker getCSSIDMarker() {
		return (CssIdMarker) markers.get(CSSID);
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:15:48 ---------------------------------------------------
	 */
	/**
	 * Get the {@code StyleMarker} which is used to apply in-line CSS styling to
	 * nodes.
	 * <p>
	 * Note that the behaviour of this method is undefined if {@link #removeMarker}
	 * was previously called with {@link Formatter#STYLE} as it's argument, or if a
	 * {@code StyleMarker} was never added using {@link Formatter#STYLE}.
	 * 
	 * @return the styling marker of this formatter.
	 */
	public StyleMarker getStyleMarker() {
		return (StyleMarker) markers.get(STYLE);
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:15:48 ---------------------------------------------------
	 */
	/**
	 * Get the {@code ClassNameMarker} which is used to apply HTML class attribute
	 * to the segments for {@linkplain Segment#getType() type}-based reference.
	 * <p>
	 * Note that the behaviour of this method is undefined if {@link #removeMarker}
	 * was previously called with {@link Formatter#CLASS_NAME} as it's argument, or
	 * if a {@code ClassNameMarker} was never added using
	 * {@link Formatter#CLASS_NAME}.
	 * 
	 * @return the class-name marker of this formatter.
	 */
	public ClassNameMarker getClassNameMarker() {
		return (ClassNameMarker) markers.get(CLASS_NAME);
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:22:33 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * <p>
	 * Will return <code>null</code> if no {@code Marker} exists for the given
	 * {@code code}.
	 * 
	 * @param code {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	public Marker get(int code) {
		return markers.get(code);
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:58:52 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param c     {@inheritDoc}
	 * @param e     {@inheritDoc}
	 * @param order {@inheritDoc}
	 * @return {@inheritDoc}
	 *         <p>
	 *         Returns <code>false</code> if the given order is less than 0 or
	 *         greater than the current number of {@code Markers} available.
	 */
	public boolean addMarker(int c, Marker e, int order) {
		if (order < 0 || order > painter.size())
			return false;
		if (markers.containsKey(c))
			return false;
		markers.put(c, e);
		painter.add(order, c);
		return true;
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:47:28 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param code    {@inheritDoc}
	 * @param element {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean addMarker(int code, Marker element) {
		return addMarker(code, element, painter.size());
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:47:28 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param code  {@inheritDoc}
	 * @param order {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean setMarkOrder(int code, int order) {
		try {
			int code2 = painter.get(order);
			Marker m = markers.get(code);
			if (m == null)
				return false;
			painter.set(painter.indexOf(code), code2);
			painter.set(order, code);
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:47:28 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param code {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public int getMarkOrder(int code) {
		return painter.indexOf(code);
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:26:43 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param c {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	public boolean removeMarker(int c) {
		Object value = markers.remove(c);
		if (value == null)
			return false;
//		Integer index = painter.indexOf(c);
//		return painter.remove(index);
		return painter.remove((Integer) c);
	}

	/**
	 * All the markers along with their corresponding codes.
	 */
	private final Map<Integer, Marker> markers;
	/**
	 * the order in which the markers will be stored whereby each element contains
	 * the code of the marker and it's index within the list is it's order.
	 */
	private final List<Integer> painter;
}