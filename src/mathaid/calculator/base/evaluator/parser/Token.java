/**
 * 
 */
package mathaid.calculator.base.evaluator.parser;

/*
 * Date: 4 Sep 2022----------------------------------------------------------- 
 * Time created: 07:10:58---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Token.java------------------------------------------------------ 
 * Class name: Token------------------------------------------------ 
 */
/**
 * An object created by a {@code Lexer} and parsed by a {@code PrattParser} to
 * create an {@code Expression}. Every token may be created from a string or
 * some object and during creation a {@code Type} and a name is assigned to it.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @param <T> the object wrapped by the {@code Type} of this {@code Token}.
 */
public class Token<T> {

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 12:10:15 ---------------------------------------------------
	 */
	/**
	 * Constructor for creating a {@code Token} object.
	 * @param type the type information for the token
	 * @param name the value of the token. This is the literal value.
	 */
	public Token(Type<T> type, String name) {
		this.type = type;
		this.name = name;
	}

	/*
	 * Date: 29 Jul 2021-----------------------------------------------------------
	 * Time created: 10:35:17--------------------------------------------
	 */
	/**
	 * Returns the associated type. A {@code Type} provides a way to differentiate
	 * {@code Tokens} and wraps the original object that was parsed.
	 * 
	 * @return the associated type.
	 */
	public Type<T> getType() {
		return type;
	}

	/*
	 * Most Recent Date: 4 Sep 2022-----------------------------------------------
	 * Most recent time created: 09:15:09--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return type.hashCode() ^ name.hashCode();
	}

	/*
	 * Most Recent Date: 4 Sep 2022-----------------------------------------------
	 * Most recent time created: 09:15:09--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param obj {@inheritDoc}
	 * @return {@inheritDoc}h
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Token) {
			Token<?> t = (Token<?>) obj;
			return getName().equals(t.getName()) && getType().equals(t.getType());
		}
		return false;
	}

	/*
	 * Most Recent Date: 4 Sep 2022-----------------------------------------------
	 * Most recent time created: 09:15:09--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public String toString() {
		return name  + ":" + getType().getName() + ":" + getType().getPrecedence();
	}

	/*
	 * Date: 29 Jul 2021-----------------------------------------------------------
	 * Time created: 10:39:56--------------------------------------------
	 */
	/**
	 * Returns the name assigned to this {@code Token} during creation.
	 * 
	 * @return a {@code String} which is also the name of this token.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Holds the given type information.
	 */
	private final Type<T> type;
	/**
	 * Holds the value
	 */
	private final String name;

}