/*
 * Date: Jan 12, 2023 -----------------------------------------------------------
 * Time created: 7:43:37 PM ---------------------------------------------------
 */
/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import mathaid.calculator.FatalReadException;

/*
 * Date: Jan 12, 2023 -----------------------------------------------------------
 * Time created: 7:43:37 PM ---------------------------------------------------
 * Package: mathaid.calculator.base.typeset ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: Function.java ------------------------------------------------------
 * Class name: Function ------------------------------------------------
 */
/**
 * An implementation of a {@code LinkedSegment} node that represents common
 * functions such as sin, cos, abs etc.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public final class Function extends AbstractSegment {

	/*
	 * Date: Jan 12, 2023
	 * ----------------------------------------------------------- Time created:
	 * 7:45:06 PM ---------------------------------------------------
	 */
	/**
	 * Creates a {@code Function} node within (or as the head of) a
	 * {@code LinkedSegment} tree from the arguments.
	 * 
	 * @param fname      the string that will be rendered as the name of the
	 *                   function
	 * @param sname      the id that will be used as the function name when building
	 *                   the expression.
	 * @param degree     specifies whether the argument to the resultant function
	 *                   expression is a number that is in degrees as opposed to
	 *                   being in radians or gradian. This is for trigonometrical
	 *                   functions.
	 * @param defer      used for building expressions that tell the Symja (Computer
	 *                   Algebra System) engine to not immediate evaluate the
	 *                   argument of the function's expression.
	 * @param focus      the focus of this node to be set.
	 * @param error      the error of this node to be set.
	 * @param sibling    the sibling of this node.
	 * @param args       the argument to this function.
	 * @param superIndex the superscript index.
	 * @param subIndex   the subscript index.
	 */
	protected Function(String fname, String sname, boolean degree, boolean defer, boolean focus, boolean error,
			LinkedSegment sibling, LinkedSegment[] args, int superIndex, int subIndex) {
		super(Segment.Type.FUNCTION, focus, error, sibling, args, superIndex, subIndex);
		this.fname = fname;
		this.sname = sname;
		this.degree = degree;
		this.defer = defer;
	}

	/*
	 * Date: Jan 12, 2023
	 * ----------------------------------------------------------- Time created:
	 * 7:43:37 PM ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param a {@inheritDoc}
	 * @param f {@inheritDoc}
	 * @param position {@inheritDoc}
	 * @throws IndexOutOfBoundsException if the {@link List} argument is presented
	 * in such a way that may cause the traversal to reference a {@code null} node.
	 */
	@Override
	public void format(Appendable a, Formatter f, List<Integer> position) {
		/* Update the current position of the cursor */
		position.set(position.size() - 1, position.get(position.size() - 1) + 1);

		/*
		 * Every single string needs to be run through a formatter. The left and right
		 * parenthesis of this function does not go through the formatter. This is not
		 * the appropriate behaviour and needs to change. We may have a prettifier as a
		 * Formatter that will prettify the TeX, it will need access to all bit of math
		 * code for a proper prettification. This is just an example of why all math
		 * code needs to go through formatters.
		 */
		StringBuilder sb = new StringBuilder(String.format("%s \\left(", fname));
		for (int i = 0; i < getChildren().length; i++) {
			position.add(i);
			position.add(-1);
			getChildren()[i].format(sb, f, position);
			position.remove(position.size() - 1);
			position.remove(position.size() - 1);

			if (i < getChildren().length - 1)
				sb.append(f.format(null, ",\\,", Segment.Type.SEPARATOR, null));
		}
		sb.append(" \\right)");
		try {
			a.append(f.format(this, sb.toString(), getType(), position));
		} catch (IOException e) {
		}
		if (hasSibling())
			getSibling().format(a, f, position);
	}

	/*
	 * Date: Jan 12, 2023
	 * ----------------------------------------------------------- Time created:
	 * 7:43:37 PM ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param a {@inheritDoc}
	 * @param l {@inheritDoc}
	 * @param position {@inheritDoc}
	 * @throws IndexOutOfBoundsException if the {@link List} argument is presented
	 * in such a way that may cause the traversal to reference a {@code null} node.
	 */
	@Override
	public void toString(Appendable a, Log l, List<Integer> position) {
		/* Update the current position of the cursor */
		position.set(position.size() - 1, position.get(position.size() - 1) + 1);

		StringBuilder sb = new StringBuilder(sname);
		if (defer)
			sb.append("Defer[");
		sb.append('[');
		for (int i = 0; i < getChildren().length; i++) {
			position.add(i);
			position.add(-1);
			getChildren()[i].toString(sb, l, position);
			position.remove(position.size() - 1);
			position.remove(position.size() - 1);
			if (degree) {
				if (getChildren()[i] instanceof Digit)
					sb.append(" *Degree");
			}
			if (i < getChildren().length - 1)
				sb.append(", ");
		}
		sb.append(']');
		if (defer)
			sb.append(']');
		try {
			a.append(sb);
		} catch (IOException e) {
		} catch (FatalReadException e) {
			throw new FatalParseException(e.getMessage(), e, position);
		}
		if (hasSibling())
			getSibling().toString(a, l, position);
	}

	/*
	 * Date: Jan 12, 2023
	 * ----------------------------------------------------------- Time created:
	 * 7:43:37 PM ---------------------------------------------------
	 */
	/**
	 * Sets the focus property for the node at the given index.
	 * @param index the index of the {@code LinkedSegment} which will have it's focus property set to the {@code boolean} value.
	 * @param focus the focus property to be set.
	 * @return a new {@code LinkedSegment} tree such that the {@code LinkedSegment} at the given index has the same error (specified by {@link #hasError})
	 * property as the {@code boolean} argument. In this implementation, this will be called recursively and
	 *         unless {@code this} is the head of the tree that initiated the call,
	 *         it may not be directly returned.
	 * @throws IndexOutOfBoundsException if no {@code LinkedSegment} at the specified index exists.
	 */
	@Override
	public LinkedSegment setFocus(int index, boolean focus) throws IndexOutOfBoundsException {
		if (index == 0)
			return new Function(fname, sname, degree, defer, focus, hasError(), getSibling(), getChildren(),
					getSuperIndex(), getSubIndex());
		else if (index > 0 && hasSibling())
			return new Function(fname, sname, degree, defer, isFocused(), hasError(),
					getSibling().setFocus(index - 1, focus), getChildren(), getSuperIndex(), getSubIndex());
		throw new IndexOutOfBoundsException(index + " is out of bounds or negative");
	}

	/*
	 * Date: Jan 12, 2023
	 * ----------------------------------------------------------- Time created:
	 * 7:43:37 PM ---------------------------------------------------
	 */
	/**
	 * Sets the error property for the node at the given index.
	 * @param index the index of the {@code LinkedSegment} which will have it's error property set to the {@code boolean} value.
	 * @param error the error property to be set.
	 * @return a new {@code LinkedSegment} tree such that the {@code LinkedSegment} at the given index has the same error (specified by {@link #hasError})
	 * property as the {@code boolean} argument. In this implementation, this will be called recursively and
	 *         unless {@code this} is the head of the tree that initiated the call,
	 *         it may not be directly returned.
	 * @throws IndexOutOfBoundsException if no {@code LinkedSegment} at the specified index exists.
	 */
	@Override
	public LinkedSegment setError(int index, boolean error) throws IndexOutOfBoundsException {
		if (index == 0)
			return new Function(fname, sname, degree, defer, isFocused(), error, getSibling(), getChildren(),
					getSuperIndex(), getSubIndex());
		else if (index > 0 && hasSibling())
			return new Function(fname, sname, degree, defer, isFocused(), hasError(),
					getSibling().setError(index - 1, error), getChildren(), getSuperIndex(), getSubIndex());
		throw new IndexOutOfBoundsException(index + " is out of bounds or negative");
	}

	/*
	 * Date: Jan 12, 2023
	 * ----------------------------------------------------------- Time created:
	 * 7:43:37 PM ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param index {@inheritDoc}
	 * @param sibling {@inheritDoc}
	 * @return {@inheritDoc}
	 * @throws IndexOutOfBoundsException if no {@code LinkedSegment} at the specified index exists.
	 */
	@Override
	public LinkedSegment setSibling(int index, LinkedSegment sibling) throws IndexOutOfBoundsException {
		if (index == 0)
			return new Function(fname, sname, degree, defer, isFocused(), hasError(), sibling, getChildren(),
					getSuperIndex(), getSubIndex());
		else if (index > 0 && hasSibling())
			return new Function(fname, sname, degree, defer, isFocused(), hasError(),
					getSibling().setSibling(index - 1, sibling), getChildren(), getSuperIndex(), getSubIndex());
		throw new IndexOutOfBoundsException(index + " is out of bounds or negative");
	}

	/*
	 * Date: Jan 12, 2023
	 * ----------------------------------------------------------- Time created:
	 * 7:43:37 PM ---------------------------------------------------
	 */
	/**
	 * Facilitates concatenation of the argument node to the tree of which {@code this} is a node of.
	 * @param s {@inheritDoc}
	 * @return {@code this} as the head of the tree whereby the argument is now the tail (last index).
	 * In reality, this will be called recursively and unless {@code this} is the head of the tree
	 * that initiated the call, it may not be directly returned.
	 * @throws NullPointerException if the argument is {@code null}
	 */
	@Override
	public LinkedSegment concat(LinkedSegment ls) {
		if (!hasSibling()) {
			if (ls instanceof Digit) {
				Digit d = (Digit) ls;
				return new Function(fname, sname, degree, defer, isFocused(), hasError(), d.toDigit(), getChildren(),
						getSuperIndex(), getSubIndex());
			}
			return new Function(fname, sname, degree, defer, isFocused(), hasError(), ls, getChildren(),
					getSuperIndex(), getSubIndex());
		}
		return new Function(fname, sname, degree, defer, isFocused(), hasError(), getSibling().concat(ls),
				getChildren(), getSuperIndex(), getSubIndex());
	}

	/*
	 * Date: Jan 12, 2023
	 * ----------------------------------------------------------- Time created:
	 * 7:43:37 PM ---------------------------------------------------
	 */
	/**
	 * Implemented to enable use in {@code java.util} collections.
	 * {@inheritDoc}
	 * @param obj {@inheritDoc}
	 * @return {@code true} if the argument is an instance of {@code Function}
	 * and has the same type as {@code this}.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Function) {
			Function cs = (Function) o;
			return Arrays.equals(getChildren(), cs.getChildren()) && fname.equals(cs.fname) && sname.equals(cs.sname)
					&& defer == cs.defer && degree == cs.degree;
		}
		return false;
	}

	/*
	 * Date: Jan 12, 2023
	 * ----------------------------------------------------------- Time created:
	 * 7:43:37 PM ---------------------------------------------------
	 */
	/**
	 * Gets the hex string of this type.
	 * @return the hex string of this type.
	 */
	@Override
	public String toString() {
		return Integer.toHexString(getType());
	}
	/**
	 * Field for the function name which when rendered will be displayed.
	 */

	private final String fname;
	/**
	 * Field for the function name which when the expression is built, will be shown.
	 */
	private final String sname;
	/**
	 * Specifies that the evaluation engine (CAS) will not be evaluating the argument
	 */
	private final boolean defer;
	/**
	 * Specifies that the arugument i a number in degrees instead of radians or gradian.
	 */
	private final boolean degree;
}
