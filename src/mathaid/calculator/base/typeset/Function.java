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
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Function extends AbstractSegment {

	/*
	 * Date: Jan 12, 2023
	 * ----------------------------------------------------------- Time created:
	 * 7:45:06 PM ---------------------------------------------------
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
	 * @param a
	 * @param f
	 * @param position
	 */
	@Override
	public void format(Appendable a, Formatter f, List<Integer> position) {
		/* Update the current position of the cursor */
		position.set(position.size() - 1, position.get(position.size() - 1) + 1);

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
	 * @param a
	 * @param l
	 * @param position
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
			getChildren()[i].toString(a, l, position);
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
	 * {@inheritDoc}
	 * 
	 * @param index
	 * @param focus
	 * @return
	 * @throws IndexOutOfBoundsException
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
	 * {@inheritDoc}
	 * 
	 * @param index
	 * @param error
	 * @return
	 * @throws IndexOutOfBoundsException
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
	 * @param index
	 * @param sibling
	 * @return
	 * @throws IndexOutOfBoundsException
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
	 * {@inheritDoc}
	 * 
	 * @param s
	 * @return
	 * @throws IndexOutOfBoundsException
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
	 * {@inheritDoc}
	 * 
	 * @param o
	 * @return
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
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return Integer.toHexString(getType());
	}

	private final String fname;
	private final String sname;
	private final boolean defer;
	private final boolean degree;
}
