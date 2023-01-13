
/**
 * 
 */
package mathaid.calculator.base.typeset;

public class CIEXYZ extends RGBA {

	private static float adjust(float component) {
		if(Math.abs(component) < 0.0031308f)
			return 12.92f * component;
		return 1.055f * (float) Math.pow(component, 0.41666f) - 0.055f;
	}

	private static float[] convert(float a, float x, float y, float z) {
		iae(0.0f, 1.0f, new String[] { "a", "x", "y", "z" }, a, x, y, z);
		float[] rgba = { a, 0.0f, 0.0f, 0.0f };
		rgba[1] = (3.2404542f * x) + (-1.5371385f * y) + (-0.4985314f * z);
		rgba[2] = (-0.9692660f * x) + (1.8760108f * y) + (0.0415560f * z);
		rgba[3] = (0.0556434f * x) + (-0.2040259f * y) + (1.0572252f * z);

		return rgba;
	}

	private static float[] convert_sRGB(float a, float x, float y, float z) {
		float[] rgba = convert(a, x, y, z);

		for(int i = 1; i < rgba.length; i++)
			rgba[i] = adjust(rgba[i]);

		return rgba;
	}

	public CIEXYZ(float a, float x, float y, float z) {
		super(convert_sRGB(a, x, y, z));
	}

	public float getX() {
		return (red() * 0.4124564f) + (green() * 0.357561f) + (blue() * 0.1804375f); 
	}

	public void setX(float x) {
		iae(0.0f, 1.0f, new String[] { "x" }, x);
		float[] xyz = convert_sRGB(opacity(), x, getY(), getZ());
		setOpaqueColour(new RGBA(xyz).getOpaqueColour());
	}

	public float getY() {
		return (red() * 0.2126729f) + (green() * 0.7151522f) + (blue() * 0.0721750f); 
	}

	public void setY(float y) {
		iae(0.0f, 1.0f, new String[] { "y" }, y);
		float[] xyz = convert_sRGB(opacity(), getX(), y, getZ());
		setOpaqueColour(new RGBA(xyz).getOpaqueColour());
	}

	public float getZ() {
		return (red() * 0.0193339f) + (green() * 0.1191920f) + (blue() * 0.9503041f);
	}

	public void setZ(float z) {
		iae(0.0f, 1.0f, new String[] { "z" }, z);
		float[] xyz = convert_sRGB(opacity(), getX(), getY(), z);
		setOpaqueColour(new RGBA(xyz).getOpaqueColour());
	}

	@Override
	public int hashCode() {
		return Float.hashCode((getX() + getY() + getZ()) / 3.0f);
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof CIEXYZ) {
			CIEXYZ c = (CIEXYZ) o;
			return isSameColour(c);
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("[ x: %1$s, y: %2$s, z: %3$s, a: %4$s ]", getX(), getY(), getZ(), opacity());
	}
}