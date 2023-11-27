/**
 * 
 */
package mathaid.calculator.base.typeset;

/*
 * Date: 24 Nov 2023 -----------------------------------------------------------
 * Time created: 10:48:02 ---------------------------------------------------
 * Package: mathaid.calculator.base.typeset ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: WebColour.java ------------------------------------------------------
 * Class name: WebColour ------------------------------------------------
 */
/**
 * An interface for defining colours that are HTML/CSS compliant.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface WebColour {

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:54:11 ---------------------------------------------------
	 */
	/**
	 * Checks if this colour is the same as the argument.
	 * 
	 * @param c the colour to be checked.
	 * @return <code>true</code> if the argument is the same colour as
	 *         <code>this</code> and <code>false</code> if otherwise.
	 */
	default boolean isSameColour(WebColour c) {
		return getOpacity() == c.getOpacity() && getOpaqueColour() == c.getOpaqueColour();
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:52:54 ---------------------------------------------------
	 */
	/**
	 * Getter for the alpha (opacity) value.
	 * 
	 * @return the opacity of this colour.
	 */
	int getOpacity();

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:52:01 ---------------------------------------------------
	 */
	/**
	 * Getter for the part of this colour with no alpha (opacity) value.
	 * 
	 * @return the non-opaque color.
	 */
	int getOpaqueColour();

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:50:50 ---------------------------------------------------
	 */
	/**
	 * Overridden to provide consistency as per recommended by the JLS (Java
	 * Language Specification).
	 * 
	 * @return the hash-code colour.
	 */
	@Override
	int hashCode();

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:49:50 ---------------------------------------------------
	 */
	/**
	 * Checks for colour equality.
	 * 
	 * @param o {@inheritDoc}
	 * @return <code>true</code> if and only if the argument is the same colour as
	 *         {@code this}.
	 */
	@Override
	boolean equals(Object o);

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:49:01 ---------------------------------------------------
	 */
	/**
	 * Gets the CSS string for this colour.
	 * 
	 * @return the CSS colour string.
	 */
	@Override
	String toString();
}