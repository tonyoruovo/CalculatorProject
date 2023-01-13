/**
 * 
 */
package mathaid.calculator.base.parser.expression;

import mathaid.calculator.base.parser.Token;

/*
 * Date: 5 May 2021----------------------------------------------------------- 
 * Time created: 19:20:05---------------------------------------------------  
 * Package: com.etineakpopha.parser.expression------------------------------------------------ 
 * Project: RemoteWork------------------------------------------------ 
 * File: OperatorExpression.java------------------------------------------------------ 
 * Class name: OperatorExpression------------------------------------------------ 
 */
/**
 * A {@code Name} expression that is also an operator used within the
 * {@code PrattParser}. In the context of the {@code PrattParser} class, a
 * {@code OperatorExpression} is an expression such as {@code x + y}
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class OperatorExpression extends Name {

	/**
	 * The left operand of this operator
	 */
	private NameExpression left;
	/**
	 * The right operand of this operator
	 */
	private NameExpression right;

	/*
	 * Date: 29 Jul 2021-----------------------------------------------------------
	 * Time created: 12:49:48---------------------------------------------------
	 */
	/**
	 * Constructs an {@code OperatorExpression} with a left and right operand and
	 * the token that represents the operator sign.
	 * 
	 * @param left  the left operand.
	 * @param t     the operator sign token
	 * @param right the right operand
	 */
	public OperatorExpression(NameExpression left, Token<String> t, NameExpression right) {
		super(t.getName());
		this.left = left;
		this.right = right;
	}

	/*
	 * Most Recent Date: 29 Jul 2021-----------------------------------------------
	 * Most recent time created: 12:52:09--------------------------------------
	 */
	/**
	 * Calls the left operand's version of this method, appends the token
	 * representation of this object before finally calling the right operand's
	 * version of this method.
	 * 
	 * @param sb a {@code StringBuilder} object which stores the pretty version of
	 *           expressions.
	 */
	@Override
	public void toString(StringBuilder sb) {
		left.toString(sb);
		sb.append(name());
		right.toString(sb);
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
		if (!(o instanceof OperatorExpression))
			return false;
		OperatorExpression obj = (OperatorExpression) o;
		return left.equals(obj.left) && name().equals(obj.name()) && right.equals(obj.right);
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
		return left.hashCode() ^ name().hashCode() ^ right.hashCode();
	}

}
