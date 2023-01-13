/**
 * 
 */
package mathaid.calculator.base.parser.expression;

import mathaid.NullException;

/*
 * Date: 20 Jun 2021----------------------------------------------------------- 
 * Time created: 20:11:12---------------------------------------------------  
 * Package: mathaid.calculator.base.parser.expression------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: EvaluatableGroup.java------------------------------------------------------ 
 * Class name: EvaluatableGroup------------------------------------------------ 
 */
/**
 * A group is a sub-type of <code>EvaluatableExpression</code> that does not
 * have a designated name. For example: <code>(x+(2*(y/sin(x))*-7))</code> does
 * not have a specified name, as such calling {@link #name()} on an
 * {@code EvaluatableExpression} object with such an expression will return
 * {@code null}
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public abstract class EvaluatableGroup implements EvaluatableExpression {

	/*
	 * Date: 20 Jun 2021-----------------------------------------------------------
	 * Time created: 20:11:12---------------------------------------------------
	 */
	/**
	 * Creates a {@code EvaluatableGroup}.
	 * @param content the value inside the parenthesis.
	 */
	public EvaluatableGroup(EvaluatableExpression content) {
		super();// Groups do not have specified names
		if (content == null)
			new NullException(EvaluatableExpression.class);
		this.content = content;
	}

	/*
	 * Date: 29 Jul 2021----------------------------------------------------------- 
	 * Time created: 20:10:22--------------------------------------------
	 */
	/**Gets the value inside the parenthesis which is a child of this.
	 * @return the value inside the parenthesis.
	 */
	public EvaluatableExpression getContent() {
		return content;
	}

	private final EvaluatableExpression content;// the content of the parenthesis

}
