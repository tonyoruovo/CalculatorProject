/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.List;

/*
 * Date: 27 Aug 2022----------------------------------------------------------- 
 * Time created: 19:48:29---------------------------------------------------  
 * Package: calculator.typeset------------------------------------------------ 
 * Project: InputTestCode------------------------------------------------ 
 * File: FormatAction.java------------------------------------------------------ 
 * Class name: FormatAction------------------------------------------------ 
 */
/**
 * An interface that represents a middleware during formatting of a
 * {@code Segment}. It is typically stored in a {@code Map} and used as an
 * argument in {@link Segment#format(Appendable, java.util.Map, List)} using one
 * of the constants (marked as middleware in the documentation) as its keys.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface FormatAction {
	/*
	 * Date: 30 Aug 2022-----------------------------------------------------------
	 * Time created: 21:11:53--------------------------------------------
	 */
	/**
	 * Wraps the argument {@code math} in a TeX string that makes the result
	 * visually different than before.
	 * 
	 * @param s        the {@code Segment} which called this method
	 * @param math     the TeX source code to be wrapped
	 * @param type     the type of {@code math} argument. It can be
	 *                 {@link Segment#getType()} or any one of the constants in the
	 *                 {@code Segment} class.
	 * @param position a {@code List} that represents the position (or cursor)
	 *                 object that count the nodes and siblings of a
	 *                 {@code Segment}.
	 * @return a formatted TeX code. The actual details is implementation dependent.
	 */
	String format(Segment s, String math, int type, List<Integer> position);
}
