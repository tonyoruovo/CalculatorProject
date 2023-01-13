/**
 * 
 */
package mathaid.calculator.base.parser.expression;

import mathaid.calculator.base.parser.Token;

/*
 * Date: 5 May 2021----------------------------------------------------------- 
 * Time created: 19:51:46---------------------------------------------------  
 * Package: com.etineakpopha.parser.expression------------------------------------------------ 
 * Project: RemoteWork------------------------------------------------ 
 * File: PostfixExpression.java------------------------------------------------------ 
 * Class name: PostfixExpression------------------------------------------------ 
 */
/**
 * A {@code Name} expression that is also a postfix operator used within the
 * {@code PrattParser}. Postfix operators are unary operators that represent
 * tokens that are placed on the right side of their operands. In the context of
 * the {@code PrattParser} class, a {@code PostfixExpression} is an expression
 * such as {@code x!}.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class PostfixExpression extends Name {
	/**
	 * The operand of this.
	 */
	private NameExpression left;

	/*
	 * Date: 29 Jul 2021-----------------------------------------------------------
	 * Time created: 12:49:48---------------------------------------------------
	 */
	/**
	 * Constructs a {@code PostfixExpression} with a given operand and the token
	 * that represents the operator sign.
	 * 
	 * @param left the operand.
	 * @param t    the operator sign token
	 */
	public PostfixExpression(NameExpression left, Token<String> t) {
		super(t.getName());
		this.left = left;
	}

	/*
	 * Most Recent Date: 29 Jul 2021-----------------------------------------------
	 * Most recent time created: 13:00:19--------------------------------------
	 */
	/**
	 * Calls the operand's version of this method before appending the token
	 * representation of this object.
	 * 
	 * @param sb a {@code StringBuilder} object which stores the pretty version of
	 *           expressions.
	 */
	@Override
	public void toString(StringBuilder sb) {
		left.toString(sb);
		sb.append(name());
	}

	/*
	 * Most Recent Date: 29 Jul 2021-----------------------------------------------
	 * Most recent time created: 12:55:10--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param o {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PostfixExpression))
			return false;
		PostfixExpression obj = (PostfixExpression) o;
		return left.equals(obj.left) && name().equals(obj.name());
	}

	/*
	 * Most Recent Date: 29 Jul 2021-----------------------------------------------
	 * Most recent time created: 12:55:10--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return left.hashCode() ^ name().hashCode();
	}

}
