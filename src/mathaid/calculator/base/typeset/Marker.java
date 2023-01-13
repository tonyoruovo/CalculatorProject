/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.List;

/**
 * An interface that represents a middleware during formatting of a
 * {@code Segment}. It is typically stored in a {@code Map} and used as an
 * argument in {@link Segment#format(Appendable, java.util.Map, List)} using one
 * of the constants (marked as middleware in the documentation) as its keys.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface Marker {
	/**
	 * Wraps the argument {@code math} in a TeX string that makes the result
	 * visually different than before.
	 * 
	 * @param segment  the {@code Segment} which called this method
	 * @param format   the TeX source code to be wrapped
	 * @param type     the type of {@code format} argument. It can be
	 *                 {@link Segment#getType()} or any one of the constants in the
	 *                 {@code Segment} class.
	 * @param position a {@code List} that represents the position (or cursor)
	 *                 object that count the nodes and siblings of a
	 *                 {@code Segment}.
	 * @return a formatted TeX code. The actual details is implementation dependent.
	 */
	String mark(Segment segment, String format, int type, List<Integer> position);

	@Override
	boolean equals(Object o);
}