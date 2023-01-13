/**
 * 
 */
package mathaid.calculator.base.parser.expression;

import mathaid.calculator.base.parser.Token;

/*
 * Date: 5 May 2021----------------------------------------------------------- 
 * Time created: 19:47:01---------------------------------------------------  
 * Package: com.etineakpopha.parser.expression------------------------------------------------ 
 * Project: RemoteWork------------------------------------------------ 
 * File: PrefixExpression.java------------------------------------------------------ 
 * Class name: PrefixExpression------------------------------------------------ 
 */
/**
 * A {@code Name} expression that is also a prefix operator used within the
 * {@code PrattParser}. Prefix operators are unary operators that represent
 * tokens that precede their operands. In the context of the {@code PrattParser}
 * class, a {@code PrefixExpression} represents something like {@code -x}.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class PrefixExpression extends Name {
	/**
	 * The operand of this.
	 */
	private NameExpression right;

	/*
	 * Date: 29 Jul 2021-----------------------------------------------------------
	 * Time created: 12:49:48---------------------------------------------------
	 */
	/**
	 * Constructs a {@code PrefixExpression} with a given operand and the token that
	 * represents the operator sign.
	 * 
	 * @param t     the operator sign token
	 * @param right the operand.
	 */
	public PrefixExpression(Token<String> t, NameExpression right) {
		super(t.getName());
		this.right = right;
	}

	/*
	 * Most Recent Date: 29 Jul 2021-----------------------------------------------
	 * Most recent time created: 13:00:19--------------------------------------
	 */
	/**
	 * Appends the token representation of this object before calling the operand's
	 * version of this method.
	 * 
	 * @param sb a {@code StringBuilder} object which stores the pretty version of
	 *           expressions.
	 */
	@Override
	public void toString(StringBuilder sb) {
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
		if (!(o instanceof PrefixExpression))
			return false;
		PrefixExpression obj = (PrefixExpression) o;
		return name().equals(obj.name()) && right.equals(obj.right);
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
		return name().hashCode() ^ right.hashCode();
	}

}
