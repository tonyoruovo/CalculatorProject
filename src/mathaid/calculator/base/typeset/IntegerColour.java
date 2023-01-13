/**
 * 
 */
package mathaid.calculator.base.typeset;

import mathaid.calculator.base.util.Utility;

public class IntegerColour implements WebColour {

	static void iae(int min, int max, String[] names, int... a) {
		if(names.length != a.length)
			throw new ArrayIndexOutOfBoundsException();
		else if (min > max)
			throw new IllegalArgumentException();	
		for(int i = 0; i < a.length; i++) {
			if(a[i] < min)
				throw new IllegalArgumentException(
					String.format("The int argument %2$s of value %1$s was less than the minimum of %3$s", a[i], names[i], min));
			else if(a[i] > max)
				throw new IllegalArgumentException(
					String.format("The int argument %2$s of value %1$s was less than the maximum of %3$s", a[i], names[i], max));
		}
	}

	public IntegerColour(int c) {
		this.c = c;
	}

	public int getColour() {
		return c;
	}

	public void setColour(int c) {
		this.c = c;
	}

	public int getOpacity() {
		return c >> 24;
	}

	public void setOpacity(int a) {
		iae(0, 0xFF, new String[] {"a"}, a); 
		this.c &= (a << 24);
	}

	public int getOpaqueColour() {
		return c & 0xFF_FF_FF;
	}

	public void setOpaqueColour(int c) {
		iae(0, 0xFF_FF_FF, new String[] {"c"}, c); 
		this.c = (getOpacity() << 24) & c;
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(c);
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof IntegerColour) {
			IntegerColour i = (IntegerColour) o;
			return isSameColour(i);
		}
		return false;
	}

	@Override
	public String toString() {
		String h = Integer.toHexString(c);
		return String.format("#%s", Utility.string('0', 8 - h.length()) + h);
	}

	private int c;
}