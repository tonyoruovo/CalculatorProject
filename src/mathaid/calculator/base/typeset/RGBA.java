/**
 * 
 */
package mathaid.calculator.base.typeset;

/**
 * <p>
 * This uses the sRGB with an alpha (opacity) component.
 * This API does not contain colours out of the sRGB gamut
 * because of the way colors are applied in the (local)
 * MathJax adapter API. Colours are applied inside an
 * inline tag using the {@code opacity} and {@code *-color}
 * (where * is any attribute that a colour can be applied
 * to, such as {@code font}) attributes. Such attributes
 * uses sRGB implementation of the RGB model as such it is
 * not possible to use traditional RGB since these attribute
 * does not support values outside of the sRGB space.
 * <p>
 * However the author wishes to diverge from MathJax in the
 * future hence non-sRGB values will be supported then when
 * CSS styling is favoured over HTML tags, attributes and
 * values.
 */
public class RGBA extends IntegerColour {

	static void iae(float min, float max, String[] names, float... a) {
		if(names.length != a.length)
			throw new ArrayIndexOutOfBoundsException();
		else if (min > max)
			throw new IllegalArgumentException();	
		for(int i = 0; i < a.length; i++) {
			if(a[i] < min)
				throw new IllegalArgumentException(
					String.format("The float argument %2$s of value %1$s was less than the minimum of %3$s", a[i], names[i], min));
			else if(a[i] > max)
				throw new IllegalArgumentException(
					String.format("The float argument %2$s of value %1$s was less than the maximum of %3$s", a[i], names[i], max));
		}
	}

	private static int convert(int a, int r, int g, int b) {
		iae(0, 0xFF, new String[] { "a", "r", "g", "b" }, a, r, g, b);
		return (a << 24) | (r << 16) | (g << 8) | b;
	}

	private static int convert(float a, float r, float g, float b) {
		iae(0.0f, 1.0f, new String[] { "a", "r", "g", "b" }, a, r, g, b);
		return convert((int) (a * 255.0f), (int) (r * 255.0f), (int) (g * 255.0f), (int) (b * 255.0f));
	}

	public RGBA(int a, int r, int g, int b) {
		super(convert(a, r, g, b));
	}

	public RGBA(float a, float r, float g, float b) {
		super(convert(a, r, g, b));
	}

	protected RGBA(int[] rgba) {
		this(rgba[0], rgba[1], rgba[2], rgba[3]);
	}

	protected RGBA(float[] rgba) {
		this(rgba[0], rgba[1], rgba[2], rgba[3]);
	}

	public float opacity() {
		return getOpacity() / 255.0f;
	}

	public void setOpacity(float a) {
		iae(0.0f, 1.0f, new String[] { "a" }, a);
		setOpacity((int) (a * 255.0f));
	}

	public int getRed() {
		return getOpaqueColour() >> 16;
	}

	public void setRed(int r) {
		iae(0, 0xFF, new String[] { "r" }, r);
		setOpaqueColour((r << 16) | (getGreen() << 8) | getBlue());
	}

	public float red() {
		return getRed() / 255.0f;
	}

	public void setRed(float r) {
		iae(0.0f, 1.0f, new String[] { "r" }, r);
		setRed((int) (r * 255.0f));
	}

	public int getGreen() {
		return (getOpaqueColour() & 0xFF_00) >> 8;
	}

	public void setGreen(int g) {
		iae(0, 0xFF, new String[] { "g" }, g);
		setOpaqueColour((getRed() << 16) | (g << 8) | getBlue());
	}

	public float green() {
		return getGreen() / 255.0f;
	}

	public void setGreen(float g) {
		iae(0.0f, 1.0f, new String[] { "g" }, g);
		setGreen((int) (g * 255.0f));
	}

	public int getBlue() {
		return getOpaqueColour() & 0xFF;
	}

	public void setBlue(int b) {
		iae(0, 0xFF, new String[] { "b" }, b);
		setOpaqueColour((getRed() << 16) | (getGreen() << 8) | b);
	}

	public float blue() {
		return getBlue() / 255.0f;
	}

	public void setBlue(float b) {
		iae(0.0f, 1.0f, new String[] { "b" }, b);
		setBlue((int) (b * 255.0f));
	}

	@Override
	public int hashCode() {
		return Float.hashCode((getRed() + getGreen() + getBlue()) / 3.0f);
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof RGBA) {
			RGBA rgba = (RGBA) o;
			return isSameColour(rgba);
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("[ r: %1$s, g: %2$s, b: %3$s, a: %4$s ]", getRed(), getGreen(), getBlue(), getOpacity());
	}
}