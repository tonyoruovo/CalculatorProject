
/**
 * 
 */
package mathaid.calculator.base.typeset;

/*
 * Date: 23 Nov 2023 -----------------------------------------------------------
 * Time created: 06:24:41 ---------------------------------------------------
 * Package: mathaid.calculator.base.typeset ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: EmptyMarker.java ------------------------------------------------------
 * Class name: EmptyMarker ------------------------------------------------
 */
/**
 * A {@code Marker} that does not apply any formatting to math code given to it.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
class EmptyMarker implements Marker {
	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:26:21 ---------------------------------------------------
	 */
	/**
	 * Constructs an {@code EmptyMarker}.
	 */
	EmptyMarker() {
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:27:05 ---------------------------------------------------
	 */
	/**
	 * Has no effect when invoked.
	 * 
	 * @param s can be <code>null</code>.
	 * @param f the returned value.
	 * @param t any {@code int} value.
	 * @param p can be <code>null</code>.
	 * @return the {@code f} argument.
	 */
	public String mark(Segment s, String f, int t, java.util.List<Integer> p) {
		return f;
	}
}