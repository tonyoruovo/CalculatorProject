/*
 * Date: Jan 11, 2023 -----------------------------------------------------------
 * Time created: 10:28:45 PM ---------------------------------------------------
 */
/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/*
 * Date: Jan 11, 2023 -----------------------------------------------------------
 * Time created: 10:28:45 PM ---------------------------------------------------
 * Package: mathaid.calculator.base.typeset ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: CompositeSegment.java ------------------------------------------------------
 * Class name: CompositeSegment ------------------------------------------------
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class CompositeSegment extends AbstractSegment {

	public static int[] getOrder(String[] a) {
		int[] o = new int[a.length - 1];
		for (int i = 0; i < o.length; i++)
			o[i] = i;
		return o;
	}

	/*
	 * Date: Jan 11, 2023
	 * ----------------------------------------------------------- Time created:
	 * 10:37:50 PM ---------------------------------------------------
	 */
	/**
	 * @param type
	 * @param focus
	 * @param error
	 * @param sibling
	 * @param children
	 * @param superIndex
	 * @param subIndex
	 */
	protected CompositeSegment(String[] fwrappers, String[] swrappers, int[] forder, int[] sorder, int type,
			boolean focus, boolean error, LinkedSegment sibling, LinkedSegment[] children, int superIndex,
			int subIndex) {
		super(type, focus, error, sibling, children, superIndex, subIndex);
		this.fwrappers = fwrappers;
		this.swrappers = swrappers;
		this.forder = forder;
		this.sorder = sorder;
	}

	public CompositeSegment(String[] fwrappers, String[] swrappers, int[] forder, int[] sorder, int type,
			LinkedSegment[] children, int superIndex, int subIndex) {
		this(fwrappers, swrappers, forder, sorder, type, false, false, null, children, superIndex, subIndex);
	}

	public CompositeSegment(String[] fwrappers, String[] swrappers, int type, LinkedSegment[] children, int superIndex,
			int subIndex) {
		this(fwrappers, swrappers, getOrder(fwrappers), getOrder(swrappers), type, false, false, null, children,
				superIndex, subIndex);
	}

	public CompositeSegment(String[] fwrappers, String[] swrappers, int type, LinkedSegment[] children) {
		this(fwrappers, swrappers, type, children, 0, 0);
	}

	public LinkedSegment setFocus(int i, boolean f) {
		if (i == 0)
			return new CompositeSegment(fwrappers, swrappers, forder, sorder, getType(), f, hasError(), getSibling(),
					getChildren(), getSuperIndex(), getSubIndex());
		else if (i > 0 && hasSibling())
			return new CompositeSegment(fwrappers, swrappers, forder, sorder, getType(), isFocused(), hasError(),
					getSibling().setFocus(i - 1, f), getChildren(), getSuperIndex(), getSubIndex());
		throw new IndexOutOfBoundsException(i + " is out of bounds or negative");
	}

	public LinkedSegment setError(int i, boolean e) {
		if (i == 0)
			return new CompositeSegment(fwrappers, swrappers, forder, sorder, getType(), isFocused(), e, getSibling(),
					getChildren(), getSuperIndex(), getSubIndex());
		else if (i > 0 && hasSibling())
			return new CompositeSegment(fwrappers, swrappers, forder, sorder, getType(), isFocused(), hasError(),
					getSibling().setError(i - 1, e), getChildren(), getSuperIndex(), getSubIndex());
		throw new IndexOutOfBoundsException(i + " is out of bounds or negative");
	}

	public LinkedSegment setSibling(int i, LinkedSegment s) {
		if (i == 0)
			return new CompositeSegment(fwrappers, swrappers, forder, sorder, getType(), isFocused(), hasError(), s,
					getChildren(), getSuperIndex(), getSubIndex());
		else if (i > 0 && hasSibling())
			return new CompositeSegment(fwrappers, swrappers, forder, sorder, getType(), isFocused(), hasError(),
					getSibling().setSibling(i - 1, s), getChildren(), getSuperIndex(), getSubIndex());
		throw new IndexOutOfBoundsException(i + " is out of bounds or negative");
	}

	public LinkedSegment concat(LinkedSegment ls) {
		if (!hasSibling()) {
			if (ls instanceof Digit) {
				Digit d = (Digit) ls;
				return new CompositeSegment(fwrappers, swrappers, forder, sorder, getType(), isFocused(), hasError(),
						d.toDigit(), getChildren(), getSuperIndex(), getSubIndex());
			}
			return new CompositeSegment(fwrappers, swrappers, forder, sorder, getType(), isFocused(), hasError(), ls,
					getChildren(), getSuperIndex(), getSubIndex());
		}
		return new CompositeSegment(fwrappers, swrappers, forder, sorder, getType(), isFocused(), hasError(),
				getSibling().concat(ls), getChildren(), getSuperIndex(), getSubIndex());
	}

	public void format(Appendable a, Formatter f, List<Integer> p) {
		/* Update the current position of the cursor */
		p.set(p.size() - 1, p.get(p.size() - 1) + 1);
		StringBuilder sb = new StringBuilder();

//		for (int i = 0; i < getChildren().length; i++) {
		for (int i = 0; i < fwrappers.length - 1; i++) {
			sb.append(fwrappers[i]);
			p.add(i);
			p.add(-1);
			getChildren()[forder[i]].format(sb, f, p);
			p.remove(p.size() - 1);
			p.remove(p.size() - 1);
		}
		sb.append(fwrappers[fwrappers.length - 1]);
		try {
			a.append(sb.toString());
		} catch (IOException e) {
		}

		if (hasSibling())
			getSibling().format(a, f, p);
	}

	public void toString(Appendable a, Log l, List<Integer> p) {
		/* Update the current position of the cursor */
		p.set(p.size() - 1, p.get(p.size() - 1) + 1);

//		for (int i = 0; i < getChildren().length; i++) {
		for (int i = 0; i < swrappers.length - 1; i++) {
			try {
				a.append(swrappers[i]);
			} catch (IOException e) {
			}
			p.add(i);
			p.add(-1);
			getChildren()[sorder[i]].toString(a, l, p);
			p.remove(p.size() - 1);
			p.remove(p.size() - 1);
		}
		try {
			a.append(swrappers[swrappers.length - 1]);
		} catch (IOException e) {
		}

		if (hasSibling())
			getSibling().toString(a, l, p);
	}

	private final String[] fwrappers;// toformat()
	private final String[] swrappers;
	private final int[] forder;
	private final int[] sorder;

	/*
	 * Date: Jan 11, 2023
	 * ----------------------------------------------------------- Time created:
	 * 11:13:03 PM ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param o
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof CompositeSegment) {
			CompositeSegment cs = (CompositeSegment) o;
			return getType() == cs.getType() && Arrays.equals(fwrappers, cs.fwrappers)
					&& Arrays.equals(swrappers, cs.swrappers) && Arrays.equals(forder, cs.forder)
					&& Arrays.equals(sorder, cs.sorder);
		}
		return false;
	}

	/*
	 * Date: Jan 11, 2023
	 * ----------------------------------------------------------- Time created:
	 * 11:13:03 PM ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return Integer.toHexString(getType());
	}
}
