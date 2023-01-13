/**
 * 
 */
package mathaid.calculator.base.parser.expression;

/*
 * Date: 5 May 2021----------------------------------------------------------- 
 * Time created: 16:03:02---------------------------------------------------  
 * Package: com.etineakpopha.parser------------------------------------------------ 
 * Project: RemoteWork------------------------------------------------ 
 * File: Expression.java------------------------------------------------------ 
 * Class name: Expression------------------------------------------------ 
 */
/**
 * An {@code Expression} is an object produced by a parser from a token or set
 * of tokens.
 * <p>
 * It has only one method for printing to a {@code StringBuilder} (i.e formats
 * itself into a string).
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public interface Expression {

	/*
	 * Date: 29 Jul 2021-----------------------------------------------------------
	 * Time created: 10:14:26--------------------------------------------
	 */
	/**
	 * Inserts a string representation of this {@code Expression} in to the
	 * {@code StringBuilder}. This can be used recursively to chain-print multiple
	 * expressions. This method does not makes any mutable changes to {@code this}.
	 * 
	 * @param sb a {@code StringBuilder} for pretty printing. It may contain chained string representations of {@code Expression}s.
	 */
	void toString(StringBuilder sb);

	/*
	 * Date: 29 Jul 2021-----------------------------------------------------------
	 * Time created: 10:21:18--------------------------------------------
	 */
	/**
	 * Returns a string representation of this. This is meant to be overriden as the
	 * method {@link #toString(StringBuilder)} only provides a way to use
	 * pretty-print on this interface, this method will provide a way to return this
	 * some form of raw state.
	 * 
	 * @return a string representation of this.
	 */
	@Override
	String toString();

	/*
	 * Date: 29 Jul 2021-----------------------------------------------------------
	 * Time created: 10:24:08--------------------------------------------
	 */
	/**
	 * Returns the hash code associated with this {@code Expression}, following that
	 * all expressions are meant to be unique, and only equal {@code Expressions}
	 * should have the same hash code. This way, implementation of the
	 * {@code Comparable} interface can be easily done.
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	int hashCode();

	/*
	 * Date: 29 Jul 2021-----------------------------------------------------------
	 * Time created: 10:27:23--------------------------------------------
	 */
	/**
	 * Compares {@code this} to the argument and returns {@code true} if it is equal
	 * to it or false if otherwise. All expressions are meant to be unique, and only
	 * equal {@code Expressions} should return true.
	 * 
	 * @param o {@inheritDoc}
	 * @return {@code true} if {@code this} is equal to the argument or
	 *         {@code false} if otherwise.
	 */
	@Override
	boolean equals(Object o);
}
