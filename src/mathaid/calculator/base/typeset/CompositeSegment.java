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
 * A {@code LinkedSegment} node for creating and handling complex segments. It
 * allows the user to specify the order in which render components and
 * expressions are built at {@link #format(Appendable, Formatter, List)} and
 * {@link #toString(Appendable, Log, List)} respectively. These are specified in
 * components called wrappers The format (render) wrappers are given by
 * {@link #fwrappers} and the expression wrappers are given by
 * {@link #swrappers}. The order in which they are used (for building) are
 * specified by {@link #forder} and {@link #sorder} for render formats and
 * expressions respectively.
 * 
 * @implNote A @TODO action is needed for this class to properly display
 *           styling.
 *           <p>
 *           An extra component array is required to direct the format method on
 *           which type of formatting will be done on component. Currently
 *           component are not being formatted, resulting in components such as
 *           the one returned by {@link Digits#decimalExponent(LinkedSegment)}
 *           to be incapable of receiving styling.
 *           <p>
 *           My fix is that the different components (wrappers and orders) be
 *           streamlined into a hash map along with type specifiers for the
 *           {@link Formatter#format(Segment, String, int, List)} method.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class CompositeSegment extends AbstractSegment {

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:51:11 ---------------------------------------------------
	 */
	/**
	 * Gets the indexes of the elements of the argument as an array.
	 * <p>
	 * This method serves as a getter for the natural order of a component specified
	 * in
	 * {@link #CompositeSegment(String[], String[], int[], int[], int, boolean, boolean, LinkedSegment, LinkedSegment[], int, int)}.
	 * It ensures that an expression/format is built by using all the elements
	 * (components) in their declared order omitting the last index (because the
	 * last index will always be called to 'close-up' the format/expression). For
	 * example:
	 * 
	 * <pre>
	 * <code>
	 * String formatComponents = Utility.as("", " ^{", " }");//Components for LaTeX and MATHJAX
	 * String expressionComponents = Utility.as("", " ^(", " )");//Components for SYMJA
		CompositeSegment segment = new CompositeSegment(
									formatComponents, expressionComponents,
									Segment.Type.FUNCTION,
									new LinkedSegment[] { child1, child2 },
									CompositeSegment.getOrder(formatComponents),
									CompositeSegment.getOrder(epressionComponents), 1, 0);
		</code>
	 * </pre>
	 * 
	 * Using the above code, one has specified that the first component followed by
	 * the next subsequent one be called in the order that they were provided for
	 * when the array was declared.
	 * 
	 * <p>
	 * This is used by:
	 * <ul>
	 * <li>{@link #CompositeSegment(String[], String[], int, LinkedSegment[], int, int)}</li>
	 * <li>{@link #CompositeSegment(String[], String[], int, LinkedSegment[])}</li>
	 * </ul>
	 * 
	 * @param a the array to search
	 * @return an array of all indexes from the first index to the second to the
	 *         last.
	 */
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
	 * Constructor for maintaining the mutability of this class.
	 * 
	 * @param fwrappers  component(s) for constructing render formats, which will be
	 *                   used when {@link #format(Appendable, Formatter, List)} is
	 *                   called.
	 * @param swrappers  component(s) for constructing expression strings, which are
	 *                   consumed at {@link #toString(Appendable, Log, List)}
	 *                   invocations.
	 * @param forder     the specified order in which {@code fwrappers} will be
	 *                   built component-by-component. The length of this array may
	 *                   or may not be equivalent to the length of {@code fwrappers}
	 *                   as some components may be repeated during formatting. So
	 *                   the range of the length is [{@code fwrappers.length - 1},
	 *                   +&infin;).
	 * @param sorder     the specific order in which {@code swrappers} will be used
	 *                   component-by-component. The length of this array may or may
	 *                   not be equivalent to the length of {@code swrappers} as
	 *                   some components may be repeated during expression building.
	 *                   So the range of the length is
	 *                   [{@code swrappers.length - 1}, +&infin;).
	 * @param type       the specified type.
	 * @param focus      the focus of this node.
	 * @param error      the error of this node.
	 * @param sibling    the sibling of this node.
	 * @param children   the child nodes of this segment.
	 * @param superIndex the superscript index.
	 * @param subIndex   the subscript of this index.
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

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 13:54:36 ---------------------------------------------------
	 */
	/**
	 * Creates a {@code CompositeSegment} without siblings where the focus and error
	 * properties are {@code false}.
	 * 
	 * @param fwrappers  component(s) for constructing render formats, which will be
	 *                   used when {@link #format(Appendable, Formatter, List)} is
	 *                   called.
	 * @param swrappers  component(s) for constructing expression strings, which are
	 *                   consumed at {@link #toString(Appendable, Log, List)}
	 *                   invocations.
	 * @param forder     the specified order in which {@code fwrappers} will be
	 *                   built component-by-component. The length of this array may
	 *                   or may not be equivalent to the length of {@code fwrappers}
	 *                   as some components may be repeated during formatting. So
	 *                   the range of the length is [{@code fwrappers.length - 1},
	 *                   +&infin;).
	 * @param sorder     the specific order in which {@code swrappers} will be used
	 *                   component-by-component. The length of this array may or may
	 *                   not be equivalent to the length of {@code swrappers} as
	 *                   some components may be repeated during expression building.
	 *                   So the range of the length is
	 *                   [{@code swrappers.length - 1}, +&infin;).
	 * @param type       the specified type.
	 * @param children   the child nodes of this segment.
	 * @param superIndex the superscript index.
	 * @param subIndex   the subscript of this index.
	 * @see #CompositeSegment(String[], String[], int[], int[], int, boolean,
	 *      boolean, LinkedSegment, LinkedSegment[], int, int)
	 */
	public CompositeSegment(String[] fwrappers, String[] swrappers, int[] forder, int[] sorder, int type,
			LinkedSegment[] children, int superIndex, int subIndex) {
		this(fwrappers, swrappers, forder, sorder, type, false, false, null, children, superIndex, subIndex);
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 13:58:55 ---------------------------------------------------
	 */
	/**
	 * Calls
	 * {@link #CompositeSegment(String[], String[], int[], int[], int, boolean, boolean, LinkedSegment, LinkedSegment[], int, int)}
	 * and sets the orders to be the natural order in which the components are
	 * declared (index-wise).
	 * 
	 * @param fwrappers  component(s) for constructing render formats, which will be
	 *                   used when {@link #format(Appendable, Formatter, List)} is
	 *                   called.
	 * @param swrappers  component(s) for constructing expression strings, which are
	 *                   consumed at {@link #toString(Appendable, Log, List)}
	 *                   invocations.
	 * @param type       the specified type.
	 * @param children   the child nodes of this segment.
	 * @param superIndex the superscript index.
	 * @param subIndex   the subscript of this index.
	 * @see #CompositeSegment(String[], String[], int[], int[], int, boolean,
	 *      boolean, LinkedSegment, LinkedSegment[], int, int)
	 */
	public CompositeSegment(String[] fwrappers, String[] swrappers, int type, LinkedSegment[] children, int superIndex,
			int subIndex) {
		this(fwrappers, swrappers, getOrder(fwrappers), getOrder(swrappers), type, false, false, null, children,
				superIndex, subIndex);
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:04:07 ---------------------------------------------------
	 */
	/**
	 * Calls
	 * {@link #CompositeSegment(String[], String[], int, LinkedSegment[], int, int)}
	 * setting the subscript and superscript indexes to {@code 0}, thus asserting
	 * that this has a superscript and a subscript that are both pointing to the
	 * same child, in effect, a child is both a superscript and a subscript.
	 * 
	 * @param fwrappers component(s) for constructing render formats, which will be
	 *                  used when {@link #format(Appendable, Formatter, List)} is
	 *                  called.
	 * @param swrappers component(s) for constructing expression strings, which are
	 *                  consumed at {@link #toString(Appendable, Log, List)}
	 *                  invocations.
	 * @param type      the specified type.
	 * @param children  the child nodes of this segment.
	 */
	public CompositeSegment(String[] fwrappers, String[] swrappers, int type, LinkedSegment[] children) {
		this(fwrappers, swrappers, type, children, 0, 0);
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 13:13:09 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param i {@inheritDoc}
	 * @param f {@inheritDoc}
	 * @return {@inheritDoc}
	 * @throws IndexOutOfBoundsException if {@code i} will reference a {@code null}
	 *                                   node.
	 */
	public LinkedSegment setFocus(int i, boolean f) {
		if (i == 0)
			return new CompositeSegment(fwrappers, swrappers, forder, sorder, getType(), f, hasError(), getSibling(),
					getChildren(), getSuperIndex(), getSubIndex());
		else if (i > 0 && hasSibling())
			return new CompositeSegment(fwrappers, swrappers, forder, sorder, getType(), isFocused(), hasError(),
					getSibling().setFocus(i - 1, f), getChildren(), getSuperIndex(), getSubIndex());
		throw new IndexOutOfBoundsException(i + " is out of bounds or negative");
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 13:17:20 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param i {@inheritDoc}
	 * @param e {@inheritDoc}
	 * @return {@inheritDoc}
	 * @throws IndexOutOfBoundsException if {@code i} will reference a {@code null}
	 *                                   node.
	 */
	public LinkedSegment setError(int i, boolean e) {
		if (i == 0)
			return new CompositeSegment(fwrappers, swrappers, forder, sorder, getType(), isFocused(), e, getSibling(),
					getChildren(), getSuperIndex(), getSubIndex());
		else if (i > 0 && hasSibling())
			return new CompositeSegment(fwrappers, swrappers, forder, sorder, getType(), isFocused(), hasError(),
					getSibling().setError(i - 1, e), getChildren(), getSuperIndex(), getSubIndex());
		throw new IndexOutOfBoundsException(i + " is out of bounds or negative");
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 13:17:58 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param i {@inheritDoc}
	 * @param s {@inheritDoc}
	 * @return {@inheritDoc}
	 * @throws IndexOutOfBoundsException if {@code i} will reference a {@code null}
	 *                                   node.
	 */
	public LinkedSegment setSibling(int i, LinkedSegment s) {
		if (i == 0)
			return new CompositeSegment(fwrappers, swrappers, forder, sorder, getType(), isFocused(), hasError(), s,
					getChildren(), getSuperIndex(), getSubIndex());
		else if (i > 0 && hasSibling())
			return new CompositeSegment(fwrappers, swrappers, forder, sorder, getType(), isFocused(), hasError(),
					getSibling().setSibling(i - 1, s), getChildren(), getSuperIndex(), getSubIndex());
		throw new IndexOutOfBoundsException(i + " is out of bounds or negative");
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 13:19:24 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param ls {@code this} as the head of the tree whereby the argument is now
	 *           the tail (last index). In practice, this will be called recursively
	 *           and unless {@code this} is the head of the tree that initiated the
	 *           call, it may not be directly returned.
	 * @throws NullPointerException if the argument is {@code null}
	 */
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

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 13:23:23 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param a {@inheritDoc}
	 * @param f {@inheritDoc}
	 * @param p {@inheritDoc}
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	public void format(Appendable a, Formatter f, List<Integer> p) {
		/* Update the current position of the cursor */
		p.set(p.size() - 1, p.get(p.size() - 1) + 1);
		StringBuilder sb = new StringBuilder();

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
			a.append(f.format(this, sb.toString(), getType(), p));
		} catch (IOException e) {
		}

		if (hasSibling())
			getSibling().format(a, f, p);
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 13:23:44 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param a {@inheritDoc}
	 * @param l {@inheritDoc}
	 * @param p {@inheritDoc}
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	public void toString(Appendable a, Log l, List<Integer> p) {
		/* Update the current position of the cursor */
		p.set(p.size() - 1, p.get(p.size() - 1) + 1);

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

	/**
	 * The array of render format(s) where each element represents a component that
	 * may be built into a specific format that can be rendered on a display that
	 * supports that format.
	 * 
	 * @see #Segments for how this is used.
	 * @see #Digits for how this is used.
	 */
	protected final String[] fwrappers;// toformat()
	/**
	 * The array of expression string(s) were each element represents a component
	 * that may used in building a CAS-specific syntax (possibly for evaluation).
	 */
	protected final String[] swrappers;
	/**
	 * The order in which the components from {@link #fwrappers} are used when
	 * building the render format.
	 */
	protected final int[] forder;
	/**
	 * The order in which the components from {@link #swrappers} are used when
	 * building the expression string.
	 */
	protected final int[] sorder;

	/*
	 * Date: Jan 11, 2023
	 * ----------------------------------------------------------- Time created:
	 * 11:13:03 PM ---------------------------------------------------
	 */
	/**
	 * Implemented to enable use in {@code java.util} collections. {@inheritDoc}
	 * 
	 * @param obj {@inheritDoc}
	 * @return {@code true} if the argument is an instance of
	 *         {@code CompositeSegment} and has the same type as {@code this}.
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
	 * Gets the string representation of this object. This implementation includes
	 * the {@link #getType()} property as a hex string.
	 * 
	 * @return the hex string of this type.
	 */
	@Override
	public String toString() {
		return Integer.toHexString(getType());
	}
}
