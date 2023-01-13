/**
 * 
 */
package mathaid.calculator.base.converter;

/*
 * Date: 28 Aug 2020----------------------------------------------------------- 
 * Time created: 22:29:43---------------------------------------------------  
 * Package: mathaid.calculator.base.function------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: Convertible.java------------------------------------------------------ 
 * Class name: Convertible------------------------------------------------ 
 */
/**
 * An object that converts values from one type to the other. All converter
 * programs within the calculator must extend this interface to be considered
 * valid.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @param <T> the type from which the conversion is made and also to which the
 *            conversion is made
 * @param <V> the value type which is also the return type
 * @param <C> An applied context. 
 * @see {@link AngleUnit}
 * @see {@link mathaid.calculator.base.value.BinaryRep BinaryRep}
 * 
 */
public interface Convertible<T extends Convertible<T, V, C>, V, C> {

	/*
	 * Date: 28 Aug 2020-----------------------------------------------------------
	 * Time created: 22:31:03--------------------------------------------
	 */
	/**
	 * Converts the value which is of type {@code this}, to the given argument
	 * {@code to} with certain context specified. the value to be converted is
	 * assumed to be in the same unit as {@code this}
	 * 
	 * @param value   the number to be converted
	 * @param type    the object to which this conversion is made
	 * @param context a context that may be applied to the value e.g a
	 *                {@link MathContext} applied to a {@link BigDecimal} for
	 *                rounding, or a {@link BitLength} applied to a
	 *                {@link BigInteger} etc.
	 * @return the value argument converted to the provided generic type &lt;V&gt;
	 */
	V convert(final V value, final T type, final C context);
}
