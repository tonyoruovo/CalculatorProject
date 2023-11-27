/**
 * 
 */
package mathaid.calculator.base.typeset;

/**
 * <p>
 * Represents a colour in the the RGB colour space with it's alpha component.
 * <p>
 * This uses the sRGB with an alpha (opacity) component. This API does not
 * contain colours out of the sRGB gamut because of the way colors are applied
 * in the (local) MathJax adapter API. Colours are applied inside an inline tag
 * using the {@code opacity} and {@code *-color} (where * is any attribute that
 * a colour can be applied to, such as {@code font}) attributes. Such attributes
 * uses sRGB implementation of the RGB model as such it is not possible to use
 * traditional RGB since these attribute does not support values outside of the
 * sRGB space.
 * <p>
 * However the author wishes to diverge from MathJax in the future hence
 * non-sRGB values will be supported then when CSS styling is favoured over HTML
 * tags, attributes and values.
 */
public class RGBA extends IntegerColour {

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:26:17 ---------------------------------------------------
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
	protected static void iae(float min, float max, String[] names, float... a) {
		if (names.length != a.length)
			throw new ArrayIndexOutOfBoundsException();
		else if (min > max)
			throw new IllegalArgumentException();
		for (int i = 0; i < a.length; i++) {
			if (a[i] < min)
				throw new IllegalArgumentException(
						String.format("The float argument %2$s of value %1$s was less than the minimum of %3$s", a[i],
								names[i], min));
			else if (a[i] > max)
				throw new IllegalArgumentException(
						String.format("The float argument %2$s of value %1$s was less than the maximum of %3$s", a[i],
								names[i], max));
		}
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:27:24 ---------------------------------------------------
	 */
	/**
	 * Converts the red ({@code r}), green ({@code g}), blue ({@code b}) and alpha
	 * components of a colour in the rgb colour space into a corresponding hex
	 * colour code supported by the {@code IntegerColour} class.
	 * <p>
	 * All arguments must be in the range [0, 256).
	 * 
	 * @param r the red component.
	 * @param g the green component.
	 * @param b the blue component.
	 * @param a the alpha component.
	 * @return the hex colour code compiled from the argument.
	 */
	private static int convert(int r, int g, int b, int a) {
		iae(0, 0xFF, new String[] { "r", "g", "b", "a" }, r, g, b, a);
		return (r << 24) | (g << 16) | (b << 8) | a;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:32:07 ---------------------------------------------------
	 */
	/**
	 * Converts the red ({@code r}), green ({@code g}), blue ({@code b}) and alpha
	 * components of a colour in the rgb colour space into a corresponding hex
	 * colour code supported by the {@code IntegerColour} class.
	 * <p>
	 * All arguments must be in the range [0, 1].
	 * 
	 * @param r the red component.
	 * @param g the green component.
	 * @param b the blue component.
	 * @param a the alpha component.
	 * @return the hex colour code compiled from the argument.
	 */
	private static int convert(float r, float g, float b, float a) {
		iae(0.0f, 1.0f, new String[] { "r", "g", "b", "a" }, r, g, b, a);
		return convert((int) (r * 255.0f), (int) (g * 255.0f), (int) (b * 255.0f), (int) (a * 255.0f));
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:33:24 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code RGBA} representing a colour in the RGB space from the given
	 * components.
	 * <p>
	 * All arguments must be in the range [0, 256).
	 * 
	 * @param r the red component.
	 * @param g the green component.
	 * @param b the blue component.
	 * @param a the alpha component.
	 */
	public RGBA(int r, int g, int b, int a) {
		super(convert(r, g, b, a));
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:33:24 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code RGBA} representing a colour in the RGB space from the given
	 * components.
	 * <p>
	 * All arguments must be in the range [0, 1].
	 * 
	 * @param r the red component.
	 * @param g the green component.
	 * @param b the blue component.
	 * @param a the alpha component.
	 */
	public RGBA(float r, float g, float b, float a) {
		super(convert(r, g, b, a));
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:33:24 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code RGBA} representing a colour in the RGB space from the given
	 * components.
	 * <p>
	 * All elements must be in the range [0, 256).
	 * 
	 * @param rgba a 4 length array representing the rgb components and their alpha
	 *             component.
	 * @see #getComponents()
	 */
	protected RGBA(int[] rgba) {
		this(rgba[0], rgba[1], rgba[2], rgba[3]);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:36:48 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code RGBA} representing a colour in the RGB space from the given
	 * components.
	 * <p>
	 * All elements must be in the range [0, 1].
	 * 
	 * @param rgba a 4 length array representing the rgb components and their alpha
	 *             component.
	 * @see #components()
	 */
	protected RGBA(float[] rgba) {
		this(rgba[0], rgba[1], rgba[2], rgba[3]);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:37:38 ---------------------------------------------------
	 */
	/**
	 * Getter for the RGB components along with the alpha value.
	 * 
	 * @return a 4-length array with the red, green, blue and alpha components
	 *         respectively.
	 */
	protected float[] components() {
		return new float[] { red(), green(), blue(), opacity() };
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:37:38 ---------------------------------------------------
	 */
	/**
	 * Getter for the RGB components along with the alpha value.
	 * 
	 * @return a 4-length array with the red, green, blue and alpha components
	 *         respectively.
	 */
	protected int[] getComponents() {
		return new int[] { getRed(), getGreen(), getBlue(), getOpacity() };
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:43:59 ---------------------------------------------------
	 */
	/**
	 * Sets the rgb components of this colour. The array format must be the same as
	 * {@link #components()} and {@link #RGBA(float[])}.
	 * 
	 * @param rgba the components to be set.
	 */
	protected void setComponents(float[] rgba) {
		setRed(rgba[0]);
		setGreen(rgba[1]);
		setBlue(rgba[2]);
		setOpacity(rgba[3]);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:43:59 ---------------------------------------------------
	 */
	/**
	 * Sets the rgb components of this colour. The array format must be the same as
	 * {@link #getComponents()} and {@link #RGBA(int[])}.
	 * 
	 * @param rgba the components to be set.
	 */
	protected void setComponents(int[] rgba) {
		setRed(rgba[0]);
		setGreen(rgba[1]);
		setBlue(rgba[2]);
		setOpacity(rgba[3]);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:45:39 ---------------------------------------------------
	 */
	/**
	 * Gets the alpha component of this colour in the range [0, 1].
	 * 
	 * @return the opacity of this colour.
	 */
	public float opacity() {
		return getOpacity() / 255.0f;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:46:57 ---------------------------------------------------
	 */
	/**
	 * Sets the alpha component of this colour.
	 * @param a the alpha component to be set. The range is [0, 1].
	 */
	public void setOpacity(float a) {
		iae(0.0f, 1.0f, new String[] { "a" }, a);
		setOpacity((int) (a * 255.0f));
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:45:39 ---------------------------------------------------
	 */
	/**
	 * Gets the red component of this colour in the range [0, 256).
	 * 
	 * @return the red value of this colour.
	 */
	public int getRed() {
		return getOpaqueColour() >>> 16;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:46:57 ---------------------------------------------------
	 */
	/**
	 * Sets the red component of this colour.
	 * @param r the red component to be set. The range is [0, 255).
	 */
	public void setRed(int r) {
		iae(0, 0xFF, new String[] { "r" }, r);
		setOpaqueColour((r << 16) | (getGreen() << 8) | getBlue());
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:45:39 ---------------------------------------------------
	 */
	/**
	 * Gets the red component of this colour in the range [0, 1].
	 * 
	 * @return the red value of this colour.
	 */
	public float red() {
		return getRed() / 255.0f;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:46:57 ---------------------------------------------------
	 */
	/**
	 * Sets the red component of this colour.
	 * @param r the red component to be set. The range is [0, 1].
	 */
	public void setRed(float r) {
		iae(0.0f, 1.0f, new String[] { "r" }, r);
		setRed((int) (r * 255.0f));
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:45:39 ---------------------------------------------------
	 */
	/**
	 * Gets the green component of this colour in the range [0, 256).
	 * 
	 * @return the green value of this colour.
	 */
	public int getGreen() {
		return (getOpaqueColour() & 0xFF_00) >>> 8;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:46:57 ---------------------------------------------------
	 */
	/**
	 * Sets the green component of this colour.
	 * @param g the green component to be set. The range is [0, 255).
	 */
	public void setGreen(int g) {
		iae(0, 0xFF, new String[] { "g" }, g);
		setOpaqueColour((getRed() << 16) | (g << 8) | getBlue());
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:45:39 ---------------------------------------------------
	 */
	/**
	 * Gets the green component of this colour in the range [0, 1].
	 * 
	 * @return the green value of this colour.
	 */
	public float green() {
		return getGreen() / 255.0f;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:46:57 ---------------------------------------------------
	 */
	/**
	 * Sets the green component of this colour.
	 * @param g the green component to be set. The range is [0, 1].
	 */
	public void setGreen(float g) {
		iae(0.0f, 1.0f, new String[] { "g" }, g);
		setGreen((int) (g * 255.0f));
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:45:39 ---------------------------------------------------
	 */
	/**
	 * Gets the blue component of this colour in the range [0, 256).
	 * 
	 * @return the blue value of this colour.
	 */
	public int getBlue() {
		return getOpaqueColour() & 0xFF;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:46:57 ---------------------------------------------------
	 */
	/**
	 * Sets the blue component of this colour.
	 * @param b the blue component to be set. The range is [0, 255).
	 */
	public void setBlue(int b) {
		iae(0, 0xFF, new String[] { "b" }, b);
		setOpaqueColour((getRed() << 16) | (getGreen() << 8) | b);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:45:39 ---------------------------------------------------
	 */
	/**
	 * Gets the blue component of this colour in the range [0, 1].
	 * 
	 * @return the blue value of this colour.
	 */
	public float blue() {
		return getBlue() / 255.0f;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:46:57 ---------------------------------------------------
	 */
	/**
	 * Sets the blue component of this colour.
	 * @param b the blue component to be set. The range is [0, 1].
	 */
	public void setBlue(float b) {
		iae(0.0f, 1.0f, new String[] { "b" }, b);
		setBlue((int) (b * 255.0f));
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:55:50 ---------------------------------------------------
	 */
	/**
	 * Returns the hash-code which is a culmination of all the components.
	 * @return {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Float.hashCode((getRed() + getGreen() + getBlue()) / 3.0f);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:56:35 ---------------------------------------------------
	 */
	/**
	 * <code>true</code> if and only if the argument is an instance of
	 * {@code RGBA} and if and only if {@link #isSameColour(WebColour)} is
	 * true for the argument.
	 * 
	 * @param o {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof RGBA) {
			RGBA rgba = (RGBA) o;
			return isSameColour(rgba);
		}
		return false;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:22:52 ---------------------------------------------------
	 */
	/**
	 * Gets this colour in the the format
	 * {@code rgba(red(), green(), blue(), opacity())}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format("rgba(%1$s, %2$s, %3$s, %4$s)", red(), green(), blue(), opacity());
	}
}