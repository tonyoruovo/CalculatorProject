/**
 * 
 */
package mathaid.calculator.base.evaluator.parser;

import java.util.Objects;

/*
 * Date: 4 Sep 2022----------------------------------------------------------- 
 * Time created: 06:59:10---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Type.java------------------------------------------------------ 
 * Class name: Type------------------------------------------------ 
 */
/**
 * A {@code Type} object wraps an object of the users choice within a
 * {@code Token}.
 * <p>
 * When {@code Token} objects are created by a {@code Lexer}, a {@code Type} is
 * specified along with it. This {@code Type} will wrap an object of choice and
 * contain a {@code Precedence} object for the parser and syntax.
 * 
 * @param <T> the object to be wrapped
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public final class Type<T> {

	/*
	 * Date: 4 Sep 2022----------------------------------------------------------- 
	 * Time created: 06:59:10--------------------------------------------------- 
	 */
	/**
	 * Creates a {@code Type} with a given name and precedence.
	 * @param name the value to be wrapped.
	 * @param precedence the object used for ordered evaluation.
	 */
	public Type(T name, Precedence precedence) {
		this.name  = name;
		this.precedence = precedence;
	}

	/*
	 * Most Recent Date: 4 Sep 2022-----------------------------------------------
	 * Most recent time created: 09:19:25--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(name) ^ precedence.hashCode();
	}

	/*
	 * Most Recent Date: 4 Sep 2022-----------------------------------------------
	 * Most recent time created: 09:19:25--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @param obj {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Type) {
			Type<?> t = (Type<?>) obj;
			return getName().equals(t.getName()) && getPrecedence().equals(t.getPrecedence());
		}
		return false;
	}
	/*
	 * Date: 29 Jul 2021-----------------------------------------------------------
	 * Time created: 10:52:26--------------------------------------------
	 */
	/**
	 * Returns the wrapped object
	 * 
	 * @return the wrapped object
	 */
	public T getName() {
		return name;
	}

	/*
	 * Date: 29 Jul 2021-----------------------------------------------------------
	 * Time created: 10:53:08--------------------------------------------
	 */
	/**
	 * Returns an associated object that parsers and parselets use for precedence
	 * parsing.
	 * 
	 * @return a {@code Precedence} object used by parsers and parselets.
	 */
	public Precedence getPrecedence() {
		return precedence;
	}
	
	private final T name;
	private final Precedence precedence;

}
