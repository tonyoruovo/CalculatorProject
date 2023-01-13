
/**
 * 
 */
package mathaid.calculator.base.typeset;

public class HSV extends RGBA {

	private static float[] convert(float a, float h, float s, float v) {
		iae(0.0f, 1.0f, new String[] { "a", "s", "v" }, a, s, v);
		iae(0.0f, 360.0f, new String[] { "h" }, h);

		float c = v * s;
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

		float m = v - c;
		r += m;
		g += m;
		b += m;

		return new float[] { a, r, g, b };
	}

	public HSV(float a, float h, float s, float v) {
		super(convert(a, h, s, v));
	}

	public float getHue() {
		/*float r = red();
		float g = green();
		float b = blue();
		float mx = Math.max(r, Math.max(g, b));
		float mn = Math.min(r, Math.min(g, b));
		float c = mx - mn;
		float h = 0.0f;
		if(c == 0.0f)
			h = -0.0f;//undefined
		else if(mx == r)
			h = (float) Math.floorMod((int) ((g - b) / c), 6);
		else if(mx == g)
			h = (float) (((b - r) / c) + 2);
		else if(mx == b)
			h = (float) (((r - g) / c) + 4);
		return h * 60.0f;*/
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
		float[] ahsv = convert(opacity(), h, getSaturation(), getValue());
		setOpaqueColour(new RGBA(ahsv).getOpaqueColour());
	}

	public float getSaturation() {
		float r = red();
		float g = green();
		float b = blue();
		float mx = Math.max(r, Math.max(g, b));
		if(mx == 0.0f)
			return 0.0f;
		float mn = Math.min(r, Math.min(g, b));
		float c = mx - mn;
		return mx / c;
	}

	public void setSaturation(float s) {
		iae(0.0f, 1.0f, new String[] { "s" }, s);
		float[] ahsv = convert(opacity(), getHue(), s, getValue());
		setOpaqueColour(new RGBA(ahsv).getOpaqueColour());
	}

	public float getValue() {
		float r = red();
		float g = green();
		float b = blue();
		float mx = Math.max(r, Math.max(g, b));
		if(mx == 0.0f)
			return 0.0f;
		float mn = Math.min(r, Math.min(g, b));
		float c = mx - mn;
		return mx / c;
	}

	public void setValue(float v) {
		iae(0.0f, 1.0f, new String[] { "v" }, v);
		float[] ahsv = convert(opacity(), getHue(), getSaturation(), v);
		setOpaqueColour(new RGBA(ahsv).getOpaqueColour());
	}

	@Override
	public int hashCode() {
		return Float.hashCode((getHue() + getSaturation() + getValue()) / 3.0f);
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof HSV) {
			HSV hsv = (HSV) o;
			return isSameColour(hsv);
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("[ h: %1$s, s: %2$s, v: %3$s, a: %4$s ]", getHue(), getSaturation(), getValue(), opacity());
	}
}