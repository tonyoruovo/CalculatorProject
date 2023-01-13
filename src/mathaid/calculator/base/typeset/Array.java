/*
 * Date: Jan 12, 2023 -----------------------------------------------------------
 * Time created: 8:38:16 PM ---------------------------------------------------
 */
/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.io.IOException;
import java.util.List;

import mathaid.calculator.FatalReadException;

/*
 * Date: Jan 12, 2023 -----------------------------------------------------------
 * Time created: 8:38:16 PM ---------------------------------------------------
 * Package: mathaid.calculator.base.typeset ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: Array.java ------------------------------------------------------
 * Class name: Array ------------------------------------------------
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
class Array extends AbstractSegment {

	/*
	 * Date: Jan 12, 2023
	 * ----------------------------------------------------------- Time created:
	 * 8:38:16 PM ---------------------------------------------------
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
	private Array(boolean focus, boolean error, LinkedSegment sibling, LinkedSegment[] elements) {
		super(Segment.Type.OBJECT, focus, error, sibling, elements, -1, -1);
	}

	public Array(LinkedSegment[] elements) {
		this(false, false, null, elements);
	}

	/*
	 * Date: Jan 12, 2023
	 * ----------------------------------------------------------- Time created:
	 * 8:38:16 PM ---------------------------------------------------
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

		StringBuilder sb = new StringBuilder(" \\left[");
		for (int i = 0; i < getChildren().length; i++) {
			position.add(i);
			position.add(-1);
			getChildren()[i].format(sb, f, position);
			position.remove(position.size() - 1);
			position.remove(position.size() - 1);

			if (i < getChildren().length - 1)
				sb.append(f.format(null, ",\\,", Segment.Type.SEPARATOR, null));
		}
		sb.append(" \\right]");
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
	 * 8:38:16 PM ---------------------------------------------------
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

		StringBuilder sb = new StringBuilder(" {");
		for (int i = 0; i < getChildren().length; i++) {
			position.add(i);
			position.add(-1);
			getChildren()[i].toString(a, l, position);
			position.remove(position.size() - 1);
			position.remove(position.size() - 1);
			if (i < getChildren().length - 1)
				sb.append(", ");
		}
		sb.append('}');
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
	 * 8:38:16 PM ---------------------------------------------------
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
			return new Array(focus, hasError(), getSibling(), getChildren());
		else if (index > 0 && hasSibling())
			return new Array(isFocused(), hasError(), getSibling().setFocus(index - 1, focus), getChildren());
		throw new IndexOutOfBoundsException(index + " is out of bounds or negative");
	}

	/*
	 * Date: Jan 12, 2023
	 * ----------------------------------------------------------- Time created:
	 * 8:38:16 PM ---------------------------------------------------
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
			return new Array(isFocused(), error, getSibling(), getChildren());
		else if (index > 0 && hasSibling())
			return new Array(isFocused(), hasError(), getSibling().setError(index - 1, error), getChildren());
		throw new IndexOutOfBoundsException(index + " is out of bounds or negative");
	}

	/*
	 * Date: Jan 12, 2023
	 * ----------------------------------------------------------- Time created:
	 * 8:38:16 PM ---------------------------------------------------
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
			return new Array(isFocused(), hasError(), sibling, getChildren());
		else if (index > 0 && hasSibling())
			return new Array(isFocused(), hasError(), getSibling().setSibling(index - 1, sibling), getChildren());
		throw new IndexOutOfBoundsException(index + " is out of bounds or negative");
	}

	/*
	 * Date: Jan 12, 2023
	 * ----------------------------------------------------------- Time created:
	 * 8:38:16 PM ---------------------------------------------------
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
				return new Array(isFocused(), hasError(), d.toDigit(), getChildren());
			}
			return new Array(isFocused(), hasError(), ls, getChildren());
		}
		return new Array(isFocused(), hasError(), getSibling().concat(ls), getChildren());
	}

	/*
	 * Date: Jan 12, 2023
	 * ----------------------------------------------------------- Time created:
	 * 8:38:16 PM ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * Date: Jan 12, 2023
	 * ----------------------------------------------------------- Time created:
	 * 8:38:16 PM ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
