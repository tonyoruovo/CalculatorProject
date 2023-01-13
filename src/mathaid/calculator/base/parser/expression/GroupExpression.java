/**
 * 
 */
package mathaid.calculator.base.parser.expression;

/*
 * Date: 9 May 2021----------------------------------------------------------- 
 * Time created: 20:50:05---------------------------------------------------  
 * Package: mathaid.calculator.base.parser.expression------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: GroupExpression.java------------------------------------------------------ 
 * Class name: GroupExpression------------------------------------------------ 
 */
/**
 * A {@code NameExpression} that contains other expressions and represent
 * parenthesised expressions. E.g (-a(x +y)).
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class GroupExpression implements NameExpression {

	/**
	 * The child expression of the parenthesis.
	 */
	private NameExpression content;

	/*
	 * Date: 29 Jul 2021-----------------------------------------------------------
	 * Time created: 20:27:14---------------------------------------------------
	 */
	/**
	 * Creates a {@code GroupExpression} initialised with it's child expression.
	 * 
	 * @param content the child of this expression
	 */
	public GroupExpression(NameExpression content) {
		this.content = content;
	}

	/*
	 * Most Recent Date: 29 Jul 2021-----------------------------------------------
	 * Most recent time created: 20:28:59--------------------------------------
	 */
	/**
	 * Appends this {@code GroupExpression} object string representation to the
	 * string builder.
	 * 
	 * @param sb {@inheritDoc}
	 */
	@Override
	public void toString(StringBuilder sb) {
		sb.append('(');
		content.toString(sb);
		sb.append(')');
	}

	/*
	 * Most Recent Date: 29 Jul 2021-----------------------------------------------
	 * Most recent time created: 20:30:15--------------------------------------
	 */
	/**
	 * Returns the name() method of the child of this object.
	 * 
	 * @return a string representation of the child as specified by
	 *         {@link NameExpression#name()}
	 */
	@Override
	public String name() {
		return content.name();
	}

}
