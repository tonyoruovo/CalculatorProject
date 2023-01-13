
/**
 * 
 */
package mathaid.calculator.base.typeset;

public class HSL extends RGBA {

	private static float[] convert(float a, float h, float s, float l) {
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

		return new float[] { a, r, g, b };
	}

	public HSL(float a, float h, float s, float l) {
		super(convert(a, h, s, l));
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
		float[] ahsl = convert(opacity(), h, getSaturation(), getLightness());
		setOpaqueColour(new RGBA(ahsl).getOpaqueColour());
	}

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

	public void setSaturation(float s) {
		iae(0.0f, 1.0f, new String[] { "s" }, s);
		float[] ahsl = convert(opacity(), getHue(), s, getLightness());
		setOpaqueColour(new RGBA(ahsl).getOpaqueColour());
	}

	public float getLightness() {
		float r = red();
		float g = green();
		float b = blue();
		float mx = Math.max(r, Math.max(g, b));
		float mn = Math.min(r, Math.min(g, b));
		return (mx + mn) / 2.0f;
	}

	public void setLightness(float l) {
		iae(0.0f, 1.0f, new String[] { "l" }, l);
		float[] ahsl = convert(opacity(), getHue(), getSaturation(), l);
		setOpaqueColour(new RGBA(ahsl).getOpaqueColour());
	}

	@Override
	public int hashCode() {
		return Float.hashCode((getHue() + getSaturation() + getLightness()) / 3.0f);
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof HSL) {
			HSL hsl = (HSL) o;
			return isSameColour(hsl);
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("[ h: %1$s, s: %2$s, l: %3$s, a: %4$s ]", getHue(), getSaturation(), getLightness(), opacity());
	}
}