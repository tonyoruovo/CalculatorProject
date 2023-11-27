/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Date: 18 Nov 2023 -----------------------------------------------------------
 * Time created: 14:47:59 ---------------------------------------------------
 * Package: mathaid.calculator.base.typeset ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: LinkedSegmentAdapter.java ------------------------------------------------------
 * Class name: LinkedSegmentAdapter ------------------------------------------------
 */
/**
 * A class that is used to retrofit a {@code Segment} (which is not an instance
 * of {@code LinkedSegment}) to extend the capabilities of the
 * {@code LinkedSegment} interface to it.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public final class LinkedSegmentAdapter implements LinkedSegment {

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:57:43 ---------------------------------------------------
	 */
	/**
	 * Creates a {@code LinkedSegment} by wrapping the first argument.
	 * 
	 * @param s        the value to be wrapped
	 * @param children the non-null array
	 * @param f        the focus property of this node.
	 * @param e        the error property of this node.
	 * @param next     the sibling to be set.
	 * @param supIndex the superscript index
	 * @param subIndex the subscript index.
	 */
	protected LinkedSegmentAdapter(Segment s, Segment[] children, boolean f, boolean e, Segment next, int supIndex,
			int subIndex) {
		this.s = s;
		this.i1 = supIndex;
		this.i2 = subIndex;
		this.sb = next == null ? null : new LinkedSegmentAdapter(next);
		this.c = Arrays.stream(children).filter(sg -> sg == null).reduce(new ArrayList<LinkedSegment>(), (list, sg) -> {
			list.add(new LinkedSegmentAdapter(sg));
			return list;
		}, (l1, l2) -> l2).toArray(new LinkedSegment[] {});
		this.f = f;
		this.e = e;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:02:18 ---------------------------------------------------
	 */
	/**
	 * Creates a {@code LinkedSegment} by calling
	 * {@link #LinkedSegmentAdapter(Segment, Segment[], boolean, boolean, Segment, int, int)}
	 * whereby the error and focus properties are set to {@code false}, children are
	 * an empty array and the superscript and subscript indexes are equal to
	 * <code>-1</code>.
	 * 
	 * @param s       the value to be wrapped.
	 * @param sibling the sibling of this node to be set.
	 */
	public LinkedSegmentAdapter(Segment s, Segment sibling) {
		this(s, new Segment[] {}, false, false, sibling, -1, -1);
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:06:35 ---------------------------------------------------
	 */
	/**
	 * Constructs this object by wrapping the argument and adapting it to the
	 * {@code LinkedSegment} interface.
	 * 
	 * @param s the segment to be wrapped.
	 */
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

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:07:36 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	public int getType() {
		return s.getType();
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:07:50 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	public boolean isFocused() {
		return s.isFocused();
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:08:06 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	public boolean hasError() {
		return s.hasError();
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:08:15 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public int getSuperIndex() {
		return i1;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:08:23 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public int getSubIndex() {
		return i2;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:08:31 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public LinkedSegment getSibling() {
		return sb;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:08:39 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public LinkedSegment[] getChildren() {
		return c;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:08:52 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param i {@inheritDoc}
	 * @param f {@inheritDoc}
	 * @return {@inheritDoc}
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	@Override
	public LinkedSegment setFocus(int i, boolean f) {
		if (i == 0)
			return new LinkedSegmentAdapter(s, c, f, e, sb, i1, i2);
		else if (i > 0 && hasSibling())
			return new LinkedSegmentAdapter(s, c, this.f, e, sb.setFocus(i - 1, f), i1, i2);

		throw new IndexOutOfBoundsException(i + " is out of bounds or negative");
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:09:14 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param i {@inheritDoc}
	 * @param e {@inheritDoc}
	 * @return {@inheritDoc}
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	@Override
	public LinkedSegment setError(int i, boolean e) {
		if (i == 0)
			return new LinkedSegmentAdapter(s, c, f, e, sb, i1, i2);
		else if (i > 0 && hasSibling())
			return new LinkedSegmentAdapter(s, c, this.f, this.e, sb.setError(i - 1, f), i1, i2);

		throw new IndexOutOfBoundsException(i + " is out of bounds or negative");
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:09:35 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param i       {@inheritDoc}
	 * @param sibling {@inheritDoc}
	 * @return {@inheritDoc}
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	@Override
	public LinkedSegment setSibling(int i, LinkedSegment sibling) {
		if (i == 0)
			return new LinkedSegmentAdapter(s, c, f, e, sibling, i1, i2);
		else if (i > 0 && hasSibling())
			return new LinkedSegmentAdapter(s, c, this.f, e, sb.setSibling(i - 1, sibling), i1, i2);

		throw new IndexOutOfBoundsException(i + " is out of bounds or negative");
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:10:00 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param index      {@inheritDoc}
	 * @param childToSet {@inheritDoc}
	 * @return {@inheritDoc}
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	@Override
	public LinkedSegment setChild(int index, LinkedSegment childToSet) throws IndexOutOfBoundsException {
		c[index] = childToSet;
		return this;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:10:14 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param s {@inheritDoc}
	 * @return {@inheritDoc}
	 * @throws NullPointerException {@inheritDoc}
	 */
	@Override
	public LinkedSegment concat(LinkedSegment s) {
		if (s == null)
			throw new NullPointerException();
		else if (sb == null)
			return new LinkedSegmentAdapter(s, c, this.f, this.e, s, i1, i2);
		return new LinkedSegmentAdapter(s, c, this.f, this.e, sb.concat(s), i1, i2);
	}

	/**
	 * The wrapped {@code Segment} value.
	 */
	private final Segment s;
	/**
	 * The children of this node.
	 */
	private final LinkedSegment[] c;
	/**
	 * The sibling of this node.
	 */
	private final LinkedSegment sb;
	/**
	 * The superscript index of this {@code LinkedSegment}
	 */
	private final int i1;
	/**
	 * The subscript index of this {@code LinkedSegment}.
	 */
	private final int i2;
	/**
	 * The focus property of this node.
	 */
	private final boolean f;
	/**
	 * The error property of this node.
	 */
	private final boolean e;

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:13:59 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param a       {@inheritDoc}
	 * @param f       {@inheritDoc}
	 * @param indexes {@inheritDoc}
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	@Override
	public void format(Appendable a, Formatter f, List<Integer> indexes) throws IndexOutOfBoundsException {
		s.format(a, f, indexes);
		if (hasSibling())
			getSibling().format(a, f, indexes);
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:14:30 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param a       {@inheritDoc}
	 * @param l       {@inheritDoc}
	 * @param indexes {@inheritDoc}
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	@Override
	public void toString(Appendable a, Log l, List<Integer> indexes) throws IndexOutOfBoundsException {
		s.toString(a, l, indexes);
		if (hasSibling())
			getSibling().toString(a, l, indexes);
	}

}