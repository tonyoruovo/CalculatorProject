
/**
 * 
 */
package mathaid.calculator.base.typeset;

/*
 * Date: 24 Nov 2023 -----------------------------------------------------------
 * Time created: 12:39:51 ---------------------------------------------------
 * Package: mathaid.calculator.base.typeset ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: HSV.java ------------------------------------------------------
 * Class name: HSV ------------------------------------------------
 */
/**
 * A class that represents a colour defined in HSL but using the RGB model.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class HSV extends RGBA {

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:28:36 ---------------------------------------------------
	 */
	/**
	 * Converts the arguments of a hsv colour into a corresponding rgb colour space
	 * supported by the {@code RGBA} class.
	 * 
	 * @param h the hue. Must be in the range [0, 360].
	 * @param s the saturation. Must be in the range [0, 1].
	 * @param v the value. Must be in the range [0, 1].
	 * @param a the alpha value.
	 * @return an array of the converted rgb value that is compatible with
	 *         {@link RGBA#RGBA(float[])}.
	 */
	private static float[] convert(float h, float s, float v, float a) {
		iae(0.0f, 1.0f, new String[] { "a", "s", "v" }, a, s, v);
		iae(0.0f, 360.0f, new String[] { "h" }, h);

		float c = v * s;
		h /= 60.0f;
		float x = c * (1.0f - (float) Math.abs(Math.floorMod((int) h, 2) - 1));

		float r = 0.0f, g = 0.0f, b = 0.0f;

		if (h >= 0.0f && h < 1.0f) {
			r = c;
			g = x;
		} else if (h >= 1.0f && h < 2.0f) {
			r = x;
			g = c;
		} else if (h >= 2.0f && h < 3.0f) {
			g = c;
			b = x;
		} else if (h >= 3.0f && h < 4.0f) {
			g = x;
			b = c;
		} else if (h >= 4.0f && h < 5.0f) {
			r = x;
			b = c;
		} else if (h >= 5.0f && h < 6.0f) {
			r = c;
			b = x;
		}

		float m = v - c;
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
	 * Constructs a {@code HSV} representing a in hsi colour
	 * 
	 * @param h the hue. Must be in the range [0, 360].
	 * @param s the saturation. Must be in the range [0, 1].
	 * @param v the value. Must be in the range [0, 1].
	 * @param a the alpha value.
	 */
	public HSV(float h, float s, float v, float a) {
		super(convert(h, s, v, a));
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:45:39 ---------------------------------------------------
	 */
	/**
	 * Gets the hue of this colour in the range [0, 360].
	 * 
	 * @return the hue of this colour.
	 */
	public float getHue() {
		/*
		 * float r = red(); float g = green(); float b = blue(); float mx = Math.max(r,
		 * Math.max(g, b)); float mn = Math.min(r, Math.min(g, b)); float c = mx - mn;
		 * float h = 0.0f; if(c == 0.0f) h = -0.0f;//undefined else if(mx == r) h =
		 * (float) Math.floorMod((int) ((g - b) / c), 6); else if(mx == g) h = (float)
		 * (((b - r) / c) + 2); else if(mx == b) h = (float) (((r - g) / c) + 4); return
		 * h * 60.0f;
		 */
		float r = red();
		float g = green();
		float b = blue();
		float mx = Math.max(r, Math.max(g, b));
		float mn = Math.min(r, Math.min(g, b));
		float c = mx - mn;
		float h = 0.0f;
		if (c == 0.0f)
			h = -0.0f;// undefined
		else if (mx == r)
			h = (g - b) / c;
		else if (mx == g)
			h = (((b - r) / c) + 2.0f);
		else if (mx == b)
			h = (((r - g) / c) + 4.0f);
		return h * 60.0f;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:46:57 ---------------------------------------------------
	 */
	/**
	 * Sets the hue of this colour.
	 * 
	 * @param h the hue to be set. The range is [0, 360].
	 */
	public void setHue(float h) {
		iae(0.0f, 360.0f, new String[] { "h" }, h);
		float[] rgba = convert(h, getSaturation(), getValue(), opacity());
//		setOpaqueColour(new RGBA(ahsv).getOpaqueColour());
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
		float mx = Math.max(r, Math.max(g, b));
		if (mx == 0.0f)
			return 0.0f;
		float mn = Math.min(r, Math.min(g, b));
		float c = mx - mn;
		return mx / c;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:46:57 ---------------------------------------------------
	 */
	/**
	 * Sets the saturation of this colour.
	 * 
	 * @param s the saturation to be set. The range is [0, 1].
	 */
	public void setSaturation(float s) {
		iae(0.0f, 1.0f, new String[] { "s" }, s);
		float[] rgba = convert(getHue(), s, getValue(), opacity());
//		setOpaqueColour(new RGBA(ahsv).getOpaqueColour());
		setComponents(rgba);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:45:39 ---------------------------------------------------
	 */
	/**
	 * Gets the value of this colour in the range [0, 1].
	 * 
	 * @return the value of this colour.
	 */
	public float getValue() {
		float r = red();
		float g = green();
		float b = blue();
		float mx = Math.max(r, Math.max(g, b));
		if (mx == 0.0f)
			return 0.0f;
		float mn = Math.min(r, Math.min(g, b));
		float c = mx - mn;
		return mx / c;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:46:57 ---------------------------------------------------
	 */
	/**
	 * Sets the value of this colour.
	 * 
	 * @param v the value to be set. The range is [0, 1].
	 */
	public void setValue(float v) {
		iae(0.0f, 1.0f, new String[] { "v" }, v);
		float[] rgba = convert(getHue(), getSaturation(), v, opacity());
//		setOpaqueColour(new RGBA(ahsv).getOpaqueColour());
		setComponents(rgba);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:17:34 ---------------------------------------------------
	 */
	/**
	 * Returns the hash-code which is a culmination of all the fields.
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Float.hashCode((getHue() + getSaturation() + getValue()) / 3.0f);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:18:24 ---------------------------------------------------
	 */
	/**
	 * <code>true</code> if and only if the argument is an instance of {@code HSV}
	 * and if and only if {@link #isSameColour(WebColour)} is true for the argument.
	 * 
	 * @param o {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof HSV) {
			HSV hsv = (HSV) o;
			return isSameColour(hsv);
		}
		return false;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:18:45 ---------------------------------------------------
	 */
	/**
	 * Gets this colour in the the format
	 * {@code hsva(getHue(), getSaturation(), getValue(), opacity())}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format("hsva(%1$s, %2$s, %3$s, %4$s)", getHue(), getSaturation(), getValue(), opacity());
	}
}