
/**
 * 
 */
package mathaid.calculator.base.typeset;

public class HSI extends RGBA {

	private static float[] convert(float a, float h, float s, float i) {
		iae(0.0f, 1.0f, new String[] { "a", "s", "i" }, a, s, i);
		iae(0.0f, 360.0f, new String[] { "h" }, h);

		h /= 60.0f;
		float z = 1.0f - (float) Math.abs(Math.floorMod((int)h, 2) - 1);
		float c = (3 * i * s) / (1.0f + z);
		float x = c * z;

		float r = 0.0f, g = 0.0f, b = 0.0f;

		if(Float.isNaN(h) || Float.isInfinite(h));
		else if(h >= 0.0f && h < 1.0f) {
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

		float m = i * (1.0f - s);
		r += m;
		g += m;
		b += m;

		return new float[] { a, r, g, b };
	}

	public HSI(float a, float h, float s, float i) {
		super(convert(a, h, s, i));
	}

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

	public void setHue(float h) {
		iae(0.0f, 360.0f, new String[] { "h" }, h);
		float[] ahsi = convert(opacity(), h, getSaturation(), getIntensity());
		setOpaqueColour(new RGBA(ahsi).getOpaqueColour());
	}

	public float getSaturation() {
		float r = red();
		float g = green();
		float b = blue();
		float i = getIntensity();
		if(i == 0.0f)
			return 0.0f;
		return 1.0f - (Math.min(r, Math.min(g, b)) / i);
	}

	public void setSaturation(float s) {
		iae(0.0f, 1.0f, new String[] { "s" }, s);
		float[] ahsi = convert(opacity(), getHue(), s, getIntensity());
		setOpaqueColour(new RGBA(ahsi).getOpaqueColour());
	}

	public float getIntensity() {
		float r = red();
		float g = green();
		float b = blue();
		return (r + g + b) / 3.0f;
	}

	public void setIntensity(float i) {
		iae(0.0f, 1.0f, new String[] { "i" }, i);
		float[] ahsi = convert(opacity(), getHue(), getSaturation(), i);
		setOpaqueColour(new RGBA(ahsi).getOpaqueColour());
	}

	@Override
	public int hashCode() {
		return Float.hashCode((getHue() + getSaturation() + getIntensity()) / 3.0f);
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof HSI) {
			HSI hsi = (HSI) o;
			return isSameColour(hsi);
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("[ h: %1$s, s: %2$s, i: %3$s, a: %4$s ]", getHue(), getSaturation(), getIntensity(), opacity());
	}
}