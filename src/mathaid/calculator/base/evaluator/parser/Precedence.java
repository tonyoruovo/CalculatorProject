/**
 * 
 */
package mathaid.calculator.base.evaluator.parser;

/*
 * Date: 4 Sep 2022----------------------------------------------------------- 
 * Time created: 07:09:06---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Precedence.java------------------------------------------------------ 
 * Class name: Precedence------------------------------------------------ 
 */
/**
 * An enum that states the order of operation of a {@code Type} of {@code Token}
 * during parsing. This is just an identifier used by parsers and parselets and
 * does not actually enforce any order when expression are created. Expressions
 * such as <code>a / 5 * -c</code> can have different interpretations as a
 * result of this.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public enum Precedence {
	/**
	 * Highest constant used by the {@code PrattParser} for basic caret '^'.
	 * 
	 */
	BASIC_CARET_HIGHEST(11),
	/**
	 * Precedence constant for the highest precedence a generic token can have when
	 * enforcing order-of-operation in an expression.
	 */
	HIGHEST(10),
	/**
	 * Highest precedence constant used by postfix operators.
	 */
	POSTFIX_1(8),
	/**
	 * Highest precedence constant used by postfix operators.
	 */
	POSTFIX_2(7),
	/**
	 * Precedence constant used by prefix operators.
	 */
	PREFIX_1(6),
	/**
	 * Lowest precedence constant used by prefix operators.
	 */
	PREFIX_2(5),
	/**
	 * Highest precedence constant used by infix operators.
	 */
	INFIX_1(4),
	/**
	 * Precedence constant used by infix operators.
	 */
	INFIX_2(3),
	/**
	 * Precedence constant used by infix operators.
	 */
	INFIX_3(2),
	/**
	 * Lowest precedence constant used by infix operators.
	 */
	INFIX_4(1),
	/**
	 * Precedence constant for the lowest precedence a generic token can have when
	 * enforcing order-of-operation in an expression.
	 */
	LOWEST(0),
	/**
	 * Lowest constant used by the {@code PrattParser} for basic caret '^' which
	 * does not consider associativity or precedence.
	 * 
	 */
	BASIC_CARET_LOWEST(1);

	/*
	 * Date: 29 Jul 2021-----------------------------------------------------------
	 * Time created: 11:07:44---------------------------------------------------
	 */
	/**
	 * Creates a precedence.
	 * 
	 * @param precedence the precedence represented as an {@code int}. Higher value
	 *                   indicate higher priorities and vice-versa.
	 */
	private Precedence(int precedence) {
		this.precedence = precedence;
	}

	/*
	 * Date: 29 Jul 2021-----------------------------------------------------------
	 * Time created: 11:09:34--------------------------------------------
	 */
	/**
	 * Returns the numerical value of the precedence. Higher values indicate higher
	 * precedence.
	 * 
	 * @return {@code this} as an {@code int}.
	 */
	public int getPrecedence() {
		return precedence;
	}

	/**
	 * The precedence
	 */
	private final int precedence;
}
