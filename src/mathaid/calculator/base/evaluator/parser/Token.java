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
	 * @return
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
	 * @param obj
	 * @return
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
	 * @return
	 */
	@Override
	public String toString() {
		return name;
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

	private final Type<T> type;
	private final String name;

}