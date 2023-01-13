/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinkedSegmentAdapter implements LinkedSegment {

	public LinkedSegmentAdapter(Segment s, Segment[] children, boolean f, boolean e, Segment next, int supIndex,
			int subIndex) {
		this.s = s;
		this.i1 = supIndex;
		this.i2 = subIndex;
		this.sb = next == null ? null : new LinkedSegmentAdapter(next);
		this.c = Arrays.stream(children).filter(sg -> sg == null).reduce(new ArrayList<>(), (list, sg) -> {
			list.add(new LinkedSegmentAdapter(sg));
			return list;
		}, (l1, l2) -> l2).toArray(new LinkedSegment[] {});
//		this.c = Arrays.copyOfRa
		this.f = f;
		this.e = e;
	}

	public LinkedSegmentAdapter(Segment s, Segment sibling) {
		this(s, new Segment[] {}, false, false, sibling, -1, -1);
	}

	public LinkedSegmentAdapter(Segment s) {
		this.s = s;
		if (s instanceof LinkedSegment) {
			LinkedSegment ls = (LinkedSegment) s;
			sb = ls.getSibling();
			c = ls.getChildren();
			i1 = ls.getSuperIndex();
			i2 = ls.getSubIndex();
			f = ls.isFocused();
			e = ls.hasError();
		} else {
			this.sb = null;
			c = new LinkedSegment[] {};
			this.i1 = -1;
			this.i2 = -1;
			this.f = false;
			e = false;
		}
	}

	public int getType() {
		return s.getType();
	}

	public boolean isFocused() {
		return s.isFocused();
	}

	public boolean hasError() {
		return s.hasError();
	}

	@Override
	public int getSuperIndex() {
		return i1;
	}

	@Override
	public int getSubIndex() {
		return i2;
	}

	@Override
	public LinkedSegment getSibling() {
		return sb;
	}

	@Override
	public LinkedSegment[] getChildren() {
		return c;
	}

	@Override
	public LinkedSegment setFocus(int i, boolean f) {
		if (i == 0)
			return new LinkedSegmentAdapter(s, c, f, e, sb, i1, i2);
		else if (i > 0 && hasSibling())
			return new LinkedSegmentAdapter(s, c, this.f, e, sb.setFocus(i - 1, f), i1, i2);

		throw new IndexOutOfBoundsException(i + " is out of bounds or negative");
	}

	@Override
	public LinkedSegment setError(int i, boolean e) {
		if (i == 0)
			return new LinkedSegmentAdapter(s, c, f, e, sb, i1, i2);
		else if (i > 0 && hasSibling())
			return new LinkedSegmentAdapter(s, c, this.f, this.e, sb.setError(i - 1, f), i1, i2);

		throw new IndexOutOfBoundsException(i + " is out of bounds or negative");
	}

	@Override
	public LinkedSegment setSibling(int i, LinkedSegment sibling) {
		if (i == 0)
			return new LinkedSegmentAdapter(s, c, f, e, sibling, i1, i2);
		else if (i > 0 && hasSibling())
			return new LinkedSegmentAdapter(s, c, this.f, e, sb.setSibling(i - 1, sibling), i1, i2);

		throw new IndexOutOfBoundsException(i + " is out of bounds or negative");
	}

	@Override
	public LinkedSegment setChild(int index, LinkedSegment childToSet) throws IndexOutOfBoundsException {
		c[index] = childToSet;
		return this;
	}

	@Override
	public LinkedSegment concat(LinkedSegment s) {
		if (s == null)
			throw new NullPointerException();
		else if (sb == null)
			return new LinkedSegmentAdapter(s, c, this.f, this.e, s, i1, i2);
		return new LinkedSegmentAdapter(s, c, this.f, this.e, sb.concat(s), i1, i2);
	}

	private final Segment s;
	private final LinkedSegment[] c;
	private final LinkedSegment sb;
	private final int i1;
	private final int i2;
	private final boolean f;
	private final boolean e;

	@Override
	public void format(Appendable a, Formatter f, List<Integer> indexes) throws IndexOutOfBoundsException {
		s.format(a, f, indexes);
		if (hasSibling())
			getSibling().format(a, f, indexes);
	}

	@Override
	public void toString(Appendable a, Log l, List<Integer> indexes) throws IndexOutOfBoundsException {
		s.toString(a, l, indexes);
		if (hasSibling())
			getSibling().toString(a, l, indexes);
	}
}