
/**
 * 
 */
package mathaid.calculator.base.typeset;
/*
 * 
 * Date: 24 Nov 2023 -----------------------------------------------------------
 * Time created: 12:32:55 ---------------------------------------------------
 * Package: mathaid.calculator.base.typeset ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: HSL.java ------------------------------------------------------
 * Class name: HSL ------------------------------------------------
 */
/**
 * A class that represents a colour defined in HSL but using the RGB model.
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class HSL extends RGBA {

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:28:36 ---------------------------------------------------
	 */
	/**
	 * Converts the arguments of a
	 * hsl colour into a corresponding rgb colour space supported by the
	 * {@code RGBA} class.
	 * @param h the hue. Must be in the range [0, 360].
	 * @param s the saturation. Must be in the range [0, 1].
	 * @param l the lightness value. Must be in the range [0, 1].
	 * @param a the alpha value.
	 * @return an array of the converted rgb value that is compatible with
	 *         {@link RGBA#RGBA(float[])}.
	 */
	private static float[] convert(float h, float s, float l, float a) {
		iae(0.0f, 1.0f, new String[] { "a", "s", "l" }, a, s, l);
		iae(0.0f, 360.0f, new String[] { "h" }, h);

		float c = (1.0f - Math.abs((2.0f * l) - 1.0f)) * s;
		h /= 60.0f;
		float x = c * (1.0f - (float) Math.abs(Math.floorMod((int)h, 2) - 1));

		float r = 0.0f, g = 0.0f, b = 0.0f;

		if(h >= 0.0f && h < 1.0f) {
			r = c;
			g = x;
		} else if(h >= 1.0f && h < 2.0f) {
			r = x;
			g = c;
		} else if(h >= 2.0f && h < 3.0f) {
			g = c;
			b = x;
		} else if(h >= 3.0f && h < 4.0f) {
			g = x;
			b = c;
		} else if(h >= 4.0f && h < 5.0f) {
			r = x;
			b = c;
		} else if(h >= 5.0f && h < 6.0f) {
			r = c;
			b = x;
		}

		float m = l - (c / 2);
		r += m;
		g += m;
		b += m;

		return new float[] { r, g, b, a };
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:30:56 ---------------------------------------------------
	 */
	/**
	 * Constructs a {@code HSL} representing a in hsi colour
	 * 
	 * @param h the hue. Must be in the range [0, 360].
	 * @param s the saturation. Must be in the range [0, 1].
	 * @param l the lightness value. Must be in the range [0, 1].
	 * @param a the alpha value.
	 */
	public HSL(float h, float s, float l, float a) {
		super(convert(h, s, l, a));
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:45:39 ---------------------------------------------------
	 */
	/**
	 * Gets the hue of this colour in the range [0, 360].
	 * 
	 * @return the saturation of this colour.
	 */
	public float getHue() {
		float r = red();
		float g = green();
		float b = blue();
		float mx = Math.max(r, Math.max(g, b));
		float mn = Math.min(r, Math.min(g, b));
		float c = mx - mn;
		float h = 0.0f;
		if(c == 0.0f)
			h = -0.0f;//undefined
		else if(mx == r)
			h = (g - b) / c;
		else if(mx == g)
			h = (((b - r) / c) + 2.0f);
		else if(mx == b)
			h = (((r - g) / c) + 4.0f);
		return h * 60.0f;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:46:57 ---------------------------------------------------
	 */
	/**
	 * Sets the hue of this colour.
	 * @param h the hue to be set. The range is [0, 360].
	 */
	public void setHue(float h) {
		iae(0.0f, 360.0f, new String[] { "h" }, h);
		float[] rgba = convert(h, getSaturation(), getLightness(), opacity());
//		setOpaqueColour(new RGBA(ahsl).getOpaqueColour());
		setComponents(rgba);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:45:39 ---------------------------------------------------
	 */
	/**
	 * Gets the saturation of this colour in the range [0, 1].
	 * 
	 * @return the saturation of this colour.
	 */
	public float getSaturation() {
		float r = red();
		float g = green();
		float b = blue();
		float l = getLightness();
		float v = Math.max(r, Math.max(g, b));
		if(l == 0.0f || l == 1.0f)
			return 0.0f;
		return (v - l) / Math.min(l, 1.0f - l);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:46:57 ---------------------------------------------------
	 */
	/**
	 * Sets the saturation of this colour.
	 * @param s the saturation to be set. The range is [0, 1].
	 */
	public void setSaturation(float s) {
		iae(0.0f, 1.0f, new String[] { "s" }, s);
		float[] rgba = convert(getHue(), s, getLightness(), opacity());
//		setOpaqueColour(new RGBA(ahsl).getOpaqueColour());
		setComponents(rgba);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:45:39 ---------------------------------------------------
	 */
	/**
	 * Gets the lightness of this colour in the range [0, 1].
	 * 
	 * @return the lightness of this colour.
	 */
	public float getLightness() {
		float r = red();
		float g = green();
		float b = blue();
		float mx = Math.max(r, Math.max(g, b));
		float mn = Math.min(r, Math.min(g, b));
		return (mx + mn) / 2.0f;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:46:57 ---------------------------------------------------
	 */
	/**
	 * Sets the lightness of this colour.
	 * @param l the lightness to be set. The range is [0, 1].
	 */
	public void setLightness(float l) {
		iae(0.0f, 1.0f, new String[] { "l" }, l);
		float[] rgba = convert(getHue(), getSaturation(), l, opacity());
//		setOpaqueColour(new RGBA(ahsl).getOpaqueColour());
		setComponents(rgba);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:17:34 ---------------------------------------------------
	 */
	/**
	 * Returns the hash-code which is a culmination of all the values.
	 * @return {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Float.hashCode((getHue() + getSaturation() + getLightness()) / 3.0f);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:18:24 ---------------------------------------------------
	 */
	/**
	 * <code>true</code> if and only if the argument is an instance of
	 * {@code HSL} and if and only if {@link #isSameColour(WebColour)} is
	 * true for the argument.
	 * 
	 * @param o {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if(o instanceof HSL) {
			HSL hsl = (HSL) o;
			return isSameColour(hsl);
		}
		return false;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:18:45 ---------------------------------------------------
	 */
	/**
	 * Gets this colour in the the format
	 * {@code hsla(getHue(), getSaturation(), getLightness(), opacity())}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format("hsla(%1$s, %2$s, %3$s, %4$s)", getHue(), getSaturation(), getLightness(), opacity());
	}
}