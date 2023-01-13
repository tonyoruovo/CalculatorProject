/**
 * 
 */
package mathaid.calculator.base.parser.expression;

/*
 * Date: 5 May 2021----------------------------------------------------------- 
 * Time created: 16:33:33---------------------------------------------------  
 * Package: com.etineakpopha.parser------------------------------------------------ 
 * Project: RemoteWork------------------------------------------------ 
 * File: NameExpression.java------------------------------------------------------ 
 * Class name: NameExpression------------------------------------------------ 
 */
/**
 * An {@code Expression} with an internal string representation. This represents
 * the family of expressions that carry a given name.
 * <p>
 * In the context of a {@code PrattParser}, a {@code NameExpression} should
 * never be created directly as it is implemented by certain internal objects
 * within the {@code PrattParser} class as an <i>Abstract Syntax Tree</i>, as
 * such attempts at direct implementation yield in undefined results.
 * </p>
 * <p>
 * In an event where a user may want to implement an expression for TeX
 * applications, this interface may be used to differentiate between the
 * internal name of the expression and the string the user wishes to present to
 * clients.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public interface NameExpression extends Expression {

	/*
	 * Date: 29 Jul 2021-----------------------------------------------------------
	 * Time created: 11:31:34--------------------------------------------
	 */
	/**
	 * Returns the internal representation of this {@code Expression} which may be
	 * different from the string that is used in the
	 * {@link Expression#toString(StringBuilder)} method. For example: the value for
	 * planck's constant is {@code 6.626176e-34}, and it may be parsed from one form
	 * into an expression. Supposing the expression is meant to display in TeX form
	 * (which is <code>6.626~176\cdot10^{34}</code>), the parser may also want to
	 * retain the above form which may be stored and returned by this method.
	 * 
	 * @return an internal string representation of {@code this}.
	 */
	String name();
}
