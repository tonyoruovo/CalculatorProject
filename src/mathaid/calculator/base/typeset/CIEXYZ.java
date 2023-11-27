
/**
 * 
 */
package mathaid.calculator.base.typeset;

/*
 * Date: 24 Nov 2023 -----------------------------------------------------------
 * Time created: 12:00:43 ---------------------------------------------------
 * Package: mathaid.calculator.base.typeset ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: CIEXYZ.java ------------------------------------------------------
 * Class name: CIEXYZ ------------------------------------------------
 */
/**
 * A representation of the CIEXYZ in the RGB color space.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class CIEXYZ extends RGBA {

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:01:30 ---------------------------------------------------
	 */
	/**
	 * Adjust the rgb component from the sRGB colour space.
	 * 
	 * @param component the rgb component to be adjusted
	 * @return the adjusted component
	 */
	private static float adjust(float component) {
		if (Math.abs(component) < 0.0031308f)
			return 12.92f * component;
		return 1.055f * (float) Math.pow(component, 0.41666f) - 0.055f;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:03:09 ---------------------------------------------------
	 */
	/**
	 * Converts the {@code x}, {@code y}, {@code z} and {@code a} arguments of a
	 * ciexyz colour into a corresponding srgb colour space supported by the
	 * {@code RGBA} class.
	 * <p>
	 * The converted value is yet to be normalised, hence cannot be used directly to
	 * represent rgb in a computer screen.
	 * <p>
	 * All arguments must be in the range [0, 1].
	 * 
	 * @param x the x value.
	 * @param y the y value.
	 * @param z the z value.
	 * @param a the alpha value.
	 * @return an array of the converted rgb value that is compatible with
	 *         {@link RGBA#RGBA(float[])}.
	 */
	private static float[] convert(float x, float y, float z, float a) {
		iae(0.0f, 1.0f, new String[] { "a", "x", "y", "z" }, a, x, y, z);
		float[] rgba = { 0.0f, 0.0f, 0.0f, a };
		rgba[0] = (3.2404542f * x) + (-1.5371385f * y) + (-0.4985314f * z);
		rgba[1] = (-0.9692660f * x) + (1.8760108f * y) + (0.0415560f * z);
		rgba[2] = (0.0556434f * x) + (-0.2040259f * y) + (1.0572252f * z);

		return rgba;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:09:17 ---------------------------------------------------
	 */
	/**
	 * Converts the {@code x}, {@code y}, {@code z} and {@code a} arguments of a
	 * ciexyz colour into a corresponding rgb colour space supported by the
	 * {@code RGBA} class.
	 * <p>
	 * All arguments must be in the range [0, 1].
	 * 
	 * @param x the x value.
	 * @param y the y value.
	 * @param z the z value.
	 * @param a the alpha value.
	 * @return an array of the converted rgb value that is compatible with
	 *         {@link RGBA#RGBA(float[])}.
	 */
	private static float[] convert_sRGB(float x, float y, float z, float a) {
		float[] rgba = convert(x, y, z, a);

		for (int i = 0; i < rgba.length - 1; i++)
			rgba[i] = adjust(rgba[i]);

		return rgba;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:11:26 ---------------------------------------------------
	 */
	/**
	 * Constructs a {@code CIEXYZ} representing a colour in ciexyz definition.
	 * <p>
	 * All arguments must be in the range [0, 1].
	 * 
	 * @param x the x value.
	 * @param y the y value.
	 * @param z the z value.
	 * @param a the alpha value.
	 */
	public CIEXYZ(float x, float y, float z, float a) {
		super(convert_sRGB(x, y, z, a));
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:14:44 ---------------------------------------------------
	 */
	/**
	 * Gets the x value.
	 * 
	 * @return the x value.
	 */
	public float getX() {
		return (red() * 0.4124564f) + (green() * 0.357561f) + (blue() * 0.1804375f);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:15:04 ---------------------------------------------------
	 */
	/**
	 * Sets the x value.
	 * 
	 * @param x the x value to be set.
	 */
	public void setX(float x) {
		iae(0.0f, 1.0f, new String[] { "x" }, x);
		float[] rgba = convert_sRGB(x, getY(), getZ(), opacity());
		setComponents(rgba);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:15:27 ---------------------------------------------------
	 */
	/**
	 * Gets the y value.
	 * 
	 * @return the y value.
	 */
	public float getY() {
		return (red() * 0.2126729f) + (green() * 0.7151522f) + (blue() * 0.0721750f);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:16:05 ---------------------------------------------------
	 */
	/**
	 * Sets the y value.
	 * 
	 * @param y the y value to be set.
	 */
	public void setY(float y) {
		iae(0.0f, 1.0f, new String[] { "y" }, y);
		float[] rgba = convert_sRGB(getX(), y, getZ(), opacity());
		setComponents(rgba);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:15:27 ---------------------------------------------------
	 */
	/**
	 * Gets the z value.
	 * 
	 * @return the z value.
	 */
	public float getZ() {
		return (red() * 0.0193339f) + (green() * 0.1191920f) + (blue() * 0.9503041f);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:16:50 ---------------------------------------------------
	 */
	/**
	 * Sets the z value.
	 * 
	 * @param z the z value to be set.
	 */
	public void setZ(float z) {
		iae(0.0f, 1.0f, new String[] { "z" }, z);
		float[] rgba = convert_sRGB(getX(), getY(), z, opacity());
//		setOpaqueColour(new RGBA(xyz).getOpaqueColour());
		setComponents(rgba);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:17:34 ---------------------------------------------------
	 */
	/**
	 * Returns the hash-code which is a culmination of all the values.
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Float.hashCode((getX() + getY() + getZ()) / 3.0f);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:18:24 ---------------------------------------------------
	 */
	/**
	 * <code>true</code> if and only if the argument is an instance of
	 * {@code CIEXYZ} and if and only if {@link #isSameColour(WebColour)} is true
	 * for the argument.
	 * 
	 * @param o {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof CIEXYZ) {
			CIEXYZ c = (CIEXYZ) o;
			return isSameColour(c);
		}
		return false;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:18:45 ---------------------------------------------------
	 */
	/**
	 * Gets this colour in the the format
	 * {@code ciexyza(getX(), getY(), getZ(), opacity())}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format("ciexyza(%1$s, %2$s, %3$s, %4$s)", getX(), getY(), getZ(), opacity());
	}
}