/**
 * 
 */
package mathaid.calculator.base.typeset;

import static mathaid.calculator.base.util.Utility.string;

/*
 * Date: 24 Nov 2023 -----------------------------------------------------------
 * Time created: 10:57:03 ---------------------------------------------------
 * Package: mathaid.calculator.base.typeset ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: IntegerColour.java ------------------------------------------------------
 * Class name: IntegerColour ------------------------------------------------
 */
/**
 * The HTML/CSS blueprint class for hex colour codes.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class IntegerColour implements WebColour {

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:57:47 ---------------------------------------------------
	 */
	/**
	 * Throws {@code IllegalArgumentException} if any of the varargs is less than
	 * {@code min} or greater than {@code max} using the corresponding {@code name}
	 * to construct a message specifying the actual argument that failed the check.
	 * 
	 * @param min   the minimum that any of the vararg can be.
	 * @param max   the maximum that any of the vararg can be.
	 * @param names the names assigned to each of the vararg. Must have the same
	 *              length as {@code a}.
	 * @param a     varargs specifying the values to be checked.
	 * @throws ArrayIndexOutOfBoundsException if {@code names.length != a.length}.
	 * @throws IllegalArgumentException       if {@code min > max}.
	 */
	protected static void iae(int min, int max, String[] names, int... a) {
		if (names.length != a.length)
			throw new ArrayIndexOutOfBoundsException();
		else if (min > max)
			throw new IllegalArgumentException();
		for (int i = 0; i < a.length; i++) {
			if (a[i] < min)
				throw new IllegalArgumentException(String.format(
						"The int argument %2$s of value %1$s was less than the minimum of %3$s", a[i], names[i], min));
			else if (a[i] > max)
				throw new IllegalArgumentException(String.format(
						"The int argument %2$s of value %1$s was less than the maximum of %3$s", a[i], names[i], max));
		}
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:05:25 ---------------------------------------------------
	 */
	/**
	 * Constructs an {@code IntegerColour} which uses the HTML/CSS format for hex
	 * colour codes i.e {@code RRGGBBAA} where:
	 * <ul>
	 * <li>{@code RR} is the 8 bit part representing the red component in RGB
	 * space.</li>
	 * <li>{@code GG} is the 8 bit part representing the green component in RGB
	 * space.</li>
	 * <li>{@code BB} is the 8 bit part representing blue green component in RGB
	 * space.</li>
	 * <li>{@code AA} is the 8 bit part representing alpha component, used for
	 * controlling the opacity of the other components.</li>
	 * </ul>
	 * 
	 * @param c an {@code int} for the hex colour code
	 */
	public IntegerColour(int c) {
		this.c = c;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:10:33 ---------------------------------------------------
	 */
	/**
	 * Gets the value representing the hex colour code for this colour object.
	 * 
	 * @return the hex colour code for this object as an {@code int}.
	 */
	public int getColour() {
		return c;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:11:26 ---------------------------------------------------
	 */
	/**
	 * Sets the hex colour code. See the {@linkplain #IntegerColour(int)
	 * constructor} for details on the argument.
	 * 
	 * @param c an {@code int} for the hex colour code to be set.
	 */
	public void setColour(int c) {
		this.c = c;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:12:38 ---------------------------------------------------
	 */
	/**
	 * Gets the alpha component of this colour as it's hex colour code.
	 * 
	 * @return the opacity part of the hex colour code of this colour.
	 */
	public int getOpacity() {
		return c & 0xFF;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:15:17 ---------------------------------------------------
	 */
	/**
	 * Setter for the opacity of this colour. The value must be in the range [0,
	 * 256) which is an unsinged {@code byte}.
	 * 
	 * @param a the alpha component of this colour to be set.
	 */
	public void setOpacity(int a) {
		iae(0, 0xFF, new String[] { "a" }, a);
		this.c = ((0xFF_FF_FF_00 & c) | a);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:17:34 ---------------------------------------------------
	 */
	/**
	 * Gets the opaque part of this colour using the HTML format for hex colour
	 * codes RRGGBB.
	 * 
	 * @return the colour without the alpha component.
	 */
	public int getOpaqueColour() {
		return c >>> 8;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:18:59 ---------------------------------------------------
	 */
	/**
	 * Sets the the part of this colour that is not identified with the alpha
	 * component.
	 * 
	 * @param c the opaque colour to be set. This value must be in the range [0,
	 *          0xFF_FF_FF) which is an unsinged {@code byte}.
	 */
	public void setOpaqueColour(int c) {
		iae(0, 0xFF_FF_FF, new String[] { "c" }, c);
		this.c = ((0x00_00_00_FF & this.c) | (c << 8));
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:20:52 ---------------------------------------------------
	 */
	/**
	 * The colour code as the hash code.
	 * 
	 * @return the hash code for this colour.
	 */
	@Override
	public int hashCode() {
		return Integer.hashCode(c);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:21:34 ---------------------------------------------------
	 */
	/**
	 * <code>true</code> if and only if the argument is an instance of
	 * {@code IntegerColour} and if and only if {@link #isSameColour(WebColour)} is
	 * true for the argument.
	 * 
	 * @param o {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof IntegerColour) {
			IntegerColour i = (IntegerColour) o;
			return isSameColour(i);
		}
		return false;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:22:52 ---------------------------------------------------
	 */
	/**
	 * Gets this colour in the the format {@code #RRGGBBAA}
	 * 
	 * @return the CSS format of this colour.
	 */
	@Override
	public String toString() {
		String h = Integer.toHexString(c);
		return String.format("\u0023%s", string('0', 8 - h.length()) + h); // u0023 is the unicode for '#'
	}

	/**
	 * The hex colour code.
	 */
	private int c;
}
