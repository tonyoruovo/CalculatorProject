/**
 * The foundational interfaces and classes for the calculator.
 * <p>
 * <b>TODO:</b> I need to write a formatting API to format expressions, decimals
 * fractions etc. and interface this API with java.text.Format class. So that we
 * can now have <code>ExpressionTeXFormat</code> class for expression details
 * list, {@code DecimalTeXFormat} class for decimal details list,
 * {@code FractionTeXFormat} class for fraction details list etc. This will
 * further decrease the tight coupling that exists between the {@code Settings}
 * class and the formatting methods of the respective details list and cause
 * other classes to be able to independently use these formatting classes and at
 * the same time make them compatible with the JDK's format API (sort of).
 * <p>
 * <b>TODO:</b> I also need a facade interface for all access to the SYMJA API
 * such as the EvalUtilities class, EvalEngine class and so on. This will enable
 * interchangeability with other libraries because as it is now, they are too
 * tightly coupled with the mathaid API.
 */
package mathaid.calculator.base;