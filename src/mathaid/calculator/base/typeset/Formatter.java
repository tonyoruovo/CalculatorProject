
/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/*
 * Date: 13 Nov 2023 -----------------------------------------------------------
 * Time created: 08:08:31 ---------------------------------------------------
 * Package: mathaid.calculator.base.typeset ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: Formatter.java ------------------------------------------------------
 * Class name: Formatter ------------------------------------------------
 */
/**
 * Provides additional styling for math code inside {@link Segment}s using
 * {@link Segment#format}.
 * <p>
 * An object used by {@code Segment.format} to add further beautification to a
 * given math code meant for display. They are used to modify parts of the format
 * given to them, they do this to enable certain features in the the formatted code.
 * For example, the formatters are responsible for adding blinking caret so
 * that user can see where to edit on the formula.
 * <p>
 * Each layer of styling is created by a {@link Marker}. There are 5 default
 * markers inside a {@code Formatter}. User defined markers can also be created
 * by implementing the {@code Marker} interface, then adding them using
 * {@link Formatter#addMarker}.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface Formatter {

	/**
	 * The key code for the {@code StyleMarker}.
	 */
	int STYLE = 0x0;
	/**
	 * The key code for the {@code CssIdMarker}.
	 */
	int CSSID = 0x1;
	/**
	 * The key code for the {@code ClassNameMarker}.
	 */
	int CLASS_NAME = 0x2;
	// other markers can be here
	/**
	 * The key code for the {@code ForwardRunningMarker}.
	 */
	int CARET = 0x7F_FF_FF_FE;
	/**
	 * The key code for the {@code ErrorMarker}.
	 */
	int ERROR = 0x7F_FF_FF_FF;

	/*
	 * Date: 13 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:33:27 ---------------------------------------------------
	 */
	/**
	 * A {@code Formatter} that does not style math code given to it, opting instead to return it as is.
	 * @return the empty formatter.
	 */
	static Formatter empty() {
		Map<Integer, Marker> m = new HashMap<>();
		m.put(0, new EmptyMarker());
		return new BasicFormatter(Collections.unmodifiableMap(m));
	}

	/*
	 * Date: 13 Nov 2023 -----------------------------------------------------------
	 * Time created: 08:32:49 ---------------------------------------------------
	 */
	/**
	 * Applies all {@code Marker}s to the input {@code format} so as to visually
	 * transform it, and then returns the transformed code.
	 * <p>
	 * Not all code may be formatted, in fact, it is possible for a code to be
	 * unformattable. This may be the case if this method is called multiple times
	 * with the arguments.
	 * 
	 * @param segment  a reference to the {@code Segment} that called this method.
	 * @param format   the math code to be transformed
	 * @param type     the type of the {@code segment} argument.
	 * @param position the same position given to {@link Segment#format}
	 * @return the transformed string argument such that it is visually different.
	 */
	String format(Segment segment, String format, int type, List<Integer> position);

	/*
	 * Date: 13 Nov 2023 -----------------------------------------------------------
	 * Time created: 09:48:04 ---------------------------------------------------
	 */
	/**
	 * Gets 1 of the 5 default markers of this {@code Formatter}. This is the
	 * {@code Marker} that is responsible for drawing the caret at positions where
	 * {@link Segment#isFocused} returns {@code true}.
	 * 
	 * @return the {@code Marker} for displaying the visual cursor (caret).
	 */
	ForwardRunningMarker getCaretMarker();

	/*
	 * Date: 13 Nov 2023 -----------------------------------------------------------
	 * Time created: 09:52:41 ---------------------------------------------------
	 */
	/**
	 * Gets 1 of the 5 default markers of this {@code Formatter}. This is the
	 * {@code Marker} that is responsible for drawing the error boxes around
	 * {@code Segment}s where {@link Segment#hasError} returns {@code true}.
	 * 
	 * @return the {@code Marker} for displaying the visual error boxes around
	 *         {@code Segment}s with syntactic errors.
	 */
	ErrorMarker getErrorMarker();

	/*
	 * Date: 13 Nov 2023 -----------------------------------------------------------
	 * Time created: 09:52:41 ---------------------------------------------------
	 */
	/**
	 * Gets 1 of the 5 default markers of this {@code Formatter}. This
	 * {@code Marker} augments the caret marker by applying HTML id (accessible from
	 * CSS using {@code #id}) to math code.
	 * 
	 * @return the {@code Marker} for assigning HTML id to math code.
	 */
	CssIdMarker getCSSIDMarker();

	/*
	 * Date: 13 Nov 2023 -----------------------------------------------------------
	 * Time created: 09:52:41 ---------------------------------------------------
	 */
	/**
	 * Gets 1 of the 5 default markers of this {@code Formatter}. This
	 * {@code Marker} applies visual effects to the text within the math code.
	 * 
	 * @return the {@code Marker} for textual effects such as bold, underline etc.
	 */
	StyleMarker getStyleMarker();

	/*
	 * Date: 13 Nov 2023 -----------------------------------------------------------
	 * Time created: 09:52:41 ---------------------------------------------------
	 */
	/**
	 * Gets 1 of the 5 default markers of this {@code Formatter}. This
	 * {@code Marker} augments the caret marker by applying HTML class names
	 * (accessible from CSS using {@code .class}) to math code.
	 * 
	 * @return the {@code Marker} for assigning HTML class name to the math code.
	 */
	ClassNameMarker getClassNameMarker();

	/*
	 * Date: 13 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:08:27 ---------------------------------------------------
	 */
	/**
	 * Gets a {@code Marker} which was registered to this {@code Formatter} using
	 * the argument.
	 * 
	 * @param code the key code for retrieving the {@code Marker}.
	 * @return the {@code Marker} stored with the specified key code
	 * @throws NoSuchElementException if the no {@code Marker} was registered with
	 *                                the argument.
	 */
	Marker get(int code) throws NoSuchElementException;

	/*
	 * Date: 13 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:11:09 ---------------------------------------------------
	 */
	/**
	 * Registers the given {@code Marker} using the provided key code, and returns
	 * {@code true} for a successful registration.
	 * 
	 * @param code    the key code with which the argument {@code Marker} will be
	 *                registered.
	 * @param element the {@code Marker} to be registered.
	 * @return {@code true} if the operation was successful. Returns {@code false}
	 *         otherwise.
	 * @implNote Most implementation should prevent an overwrite on scenarios where
	 *           the key code was already used to register a {@code Marker}. They
	 *           may opt to throw an exception but ideally, they should return
	 *           simply {@code false}.
	 */
	boolean addMarker(int code, Marker element);

	/*
	 * Date: 13 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:16:13 ---------------------------------------------------
	 */
	/**
	 * Unregisters the given {@code Marker} using the provided key code, and returns
	 * {@code true} for a successful removal. If this method returns {@code true}, then
	 * calling {@link #get} with this code will throw a {@code NoSuchElementException} and
	 * this {@code Formatter} will no longer support any {@code Marker} using the given code.
	 * 
	 * @param code    the key code with which the argument {@code Marker} was
	 *                registered.
	 * @param element the {@code Marker} to be registered.
	 * @return {@code true} if the operation was successful. Returns {@code false}
	 *         otherwise.
	 */
	boolean removeMarker(int code);

	/*
	 * Date: 13 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:20:42 ---------------------------------------------------
	 */
	/**
	 * Disables (but does not remove) the {@code Marker} whose key is the argument.
	 * @param code the key of the {@code Marker} to be disabled.
	 * @return {@code true} if an active {@code Marker} was deactivated with this method else returns {@code false}.
	 */
	boolean disableMarker(int code);

	/*
	 * Date: 13 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:29:23 ---------------------------------------------------
	 */
	/**
	 * Enables a previously disabled {@code Marker} whose key is the argument.
	 * @param code the key of the {@code Marker} to be enabled.
	 * @return {@code true} if an inactive {@code Marker} was activated with this method else returns {@code false}.
	 */
	boolean enableMarker(int code);
}