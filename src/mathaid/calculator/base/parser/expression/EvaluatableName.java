/**
 * 
 */
package mathaid.calculator.base.parser.expression;

import mathaid.NullException;

/*
 * Date: 20 Jun 2021----------------------------------------------------------- 
 * Time created: 10:08:45---------------------------------------------------  
 * Package: mathaid.calculator.base.parser.expression------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: EvaluatableName.java------------------------------------------------------ 
 * Class name: EvaluatableName------------------------------------------------ 
 */
/**
 * A base class for all <code>EvaluatableExpression</code> that have name(s).
 * <p>
 * It directly refers to an expression such as <code>abc</code>. Expressions
 * such as <code>sin(x)</code> are still regarded as an
 * {@code EvaluatableExpression}, however, they specifically consist of a name
 * and a functional parenthesis, as such they are grouped as
 * {@link EvaluatableFunction functions}. Another example is <code>x + y</code>;
 * in this case, the name of such an expression is "{@code +}", but that is just
 * the {@code String} that will be returned via the ubiquitous method
 * {@link #name()} and not the object itself. This type of expression is an
 * {@link EvaluatableOperator} - i.e a sub-type of {@code EvaluatableName} that
 * has 2 fields (x &amp; y) that are both an {@code EvaluatableName}.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public abstract class EvaluatableName implements EvaluatableExpression {

	/*
	 * Date: 20 Jun 2021-----------------------------------------------------------
	 * Time created: 10:08:45---------------------------------------------------
	 */
	/*
	 * Date: 20 Jun 2021-----------------------------------------------------------
	 * Time created: 10:37:53---------------------------------------------------
	 */
	/**
	 * Constructs an {@code EvaluatableExpression} using the {@code String} argument
	 * to represent it's name.
	 * 
	 * @param name the name of this {@code EvaluatableExpression}
	 * @throws RuntimeException specifically a {@code NullPointerException} if name
	 *                          is null
	 */
	public EvaluatableName(String name) {
		if (name == null)
			new NullException(String.class);
		this.name = name;
	}

	/*
	 * Most Recent Date: 20 Jun 2021-----------------------------------------------
	 * Most recent time created: 10:08:45--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public String name() {
		return name;
	}

	/*
	 * Most Recent Date: 20 Jun 2021-----------------------------------------------
	 * Most recent time created: 10:08:45--------------------------------------
	 */
	/**
	 * Inserts the string representation of this {@code EvaluatableName} into the
	 * given {@code StringBuilder}, which may contain a string from preceding
	 * expressions.
	 * 
	 * @param sb {@inheritDoc}
	 */
	@Override
	public abstract void toString(StringBuilder sb);

	/*
	 * Most Recent Date: 20 Jun 2021-----------------------------------------------
	 * Most recent time created: 10:27:36--------------------------------------
	 */
	/**
	 * Compare the argument to {@code this} and returns {@code true} if both are
	 * equal or {@code false} if otherwise.
	 * <p>
	 * Inheritors if this class may want to override this method as this is base
	 * implementation and does properly implement deep comparison. This method just
	 * compares the {@code String} field retrieved from {@link #name()} to the input
	 * argument's.
	 * </p>
	 * 
	 * @param o {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Name))
			return false;
		return name.equals(((EvaluatableName) o).name);
	}

	/*
	 * Most Recent Date: 29 Jul 2021-----------------------------------------------
	 * Most recent time created: 18:59:13--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	/*
	 * Most Recent Date: 29 Jul 2021-----------------------------------------------
	 * Most recent time created: 18:59:13--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public String toString() {
		return name();
	}

	/*
	 * Most Recent Date: 20 Jun 2021-----------------------------------------------
	 * Most recent time created: 10:08:45--------------------------------------
	 */
	/**
	 * Returns an {@code EvaluatableExpression} after evaluating {@code this} using
	 * inputs provided from the expression itself.
	 * <p>
	 * An example of this would be the expression <code>sin(5x^2)</code>.The parser
	 * may first parse sin then call {@code evaluate()} on sin which will return sin
	 * (this can be called an infinite number of times) then parse all the contents
	 * of the parenthesis and once again call {@code evaluate()} on the content of
	 * the parenthesis. Then evaluate is called again on sin and 5x^2 (the
	 * argument), this will return the final result.
	 * </p>
	 * 
	 * @return an {@code EvaluatableExpression} that may or may not be equal to
	 *         {@code this}.
	 */
	@Override
	public abstract EvaluatableExpression evaluate();

	/**
	 * The name of this expression.
	 */
	private final String name;

}
