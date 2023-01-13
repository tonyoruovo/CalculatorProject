/**
 * 
 */
package mathaid.calculator.base.typeset;

import static mathaid.calculator.base.typeset.Segment.Type.INTEGER;
import static mathaid.calculator.base.typeset.Segment.Type.POINT;

import java.io.IOException;
import java.util.List;

import mathaid.calculator.FatalReadException;

public class BasicSegment extends AbstractSegment {
	protected BasicSegment(String ft, String s, int t, boolean f, boolean e, LinkedSegment ls) {
		super(t, f, e, ls, new LinkedSegment[] {}, -1, -1);
		this.s = s;
		this.f = ft;
	}

	public BasicSegment(String f, String s, int t) {
		this(f, s, t, false, false, null);
	}

	public BasicSegment(String s, int t) {
		this(s, s, t);
	}

	public LinkedSegment setFocus(int i, boolean f) {
		if (i == 0)
			return new BasicSegment(this.f, s, getType(), f, hasError(), getSibling());
		else if (i > 0 && hasSibling())
			return new BasicSegment(this.f, s, getType(), isFocused(), hasError(), getSibling().setFocus(i - 1, f));
		throw new IndexOutOfBoundsException(i + " is out of bounds or negative");
	}

	public LinkedSegment setError(int i, boolean e) {
		if (i == 0)
			return new BasicSegment(f, s, getType(), isFocused(), e, getSibling());
		else if (i > 0 && hasSibling())
			return new BasicSegment(f, s, getType(), isFocused(), hasError(), getSibling().setError(i - 1, e));
		throw new IndexOutOfBoundsException(i + " is out of bounds or negative");
	}

	public LinkedSegment setSibling(int i, LinkedSegment s) {
		if (i == 0)
			return new BasicSegment(f, this.s, getType(), isFocused(), hasError(), s);
		else if (i > 0 && hasSibling())
			return new BasicSegment(f, this.s, getType(), isFocused(), hasError(), getSibling().setSibling(i - 1, s));
		throw new IndexOutOfBoundsException(i + " is out of bounds or negative");
	}

	public LinkedSegment concat(LinkedSegment ls) {
		if (!hasSibling()) {
			if (ls instanceof Digit) {
				Digit d = (Digit) ls;
				if (getType() == POINT) {
					if (d.getType() == INTEGER)
						return new BasicSegment(f, s, getType(), isFocused(), hasError(), d.toMantissaDigit());
				} else
					return new BasicSegment(f, s, getType(), isFocused(), hasError(), d.toDigit());
			}
			return new BasicSegment(f, s, getType(), isFocused(), hasError(), ls);
		}
		return new BasicSegment(f, s, getType(), isFocused(), hasError(), getSibling().concat(ls));
	}

	public void format(Appendable a, Formatter f, List<Integer> p) {
		/* Update the current position of the cursor */
		p.set(p.size() - 1, p.get(p.size() - 1) + 1);

		try {
			a.append(f.format(this, this.f, getType(), p));
		} catch (IOException e) {
		}

		if (hasSibling())
			getSibling().format(a, f, p);

	}

	public void toString(Appendable a, Log l, List<Integer> p) {
		/* Update the current position of the cursor */
		p.set(p.size() - 1, p.get(p.size() - 1) + 1);
		try {
			a.append(s);
		} catch (IOException e) {
		} catch (FatalReadException e) {
			throw new FatalParseException(e.getMessage(), e, p);
		}

		if (hasSibling())
			getSibling().toString(a, l, p);
	}

	public boolean equals(Object o) {
		if (o instanceof BasicSegment) {
			BasicSegment bs = (BasicSegment) o;
			return getType() == bs.getType() && s.equals(bs.s) && f.equals(bs.f);
		}
		return false;
	}

	public String toString() {
		return Integer.toHexString(getType());
	}

	private final String s;// string
	private final String f;// format
}