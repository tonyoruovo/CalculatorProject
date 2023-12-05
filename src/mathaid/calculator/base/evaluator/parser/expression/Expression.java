/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression;

/*
 * Date: 4 Sep 2022----------------------------------------------------------- 
 * Time created: 06:51:03---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Expression.java------------------------------------------------------ 
 * Class name: Expression------------------------------------------------ 
 */
/**
 * A node in an <span style="font-weight:bold"><span style="font-style:italic">A</span></span>bstract
 * <span style="font-weight:bold"><span style="font-style:italic">S</span></span>yntax
 * <span style="font-style:italic"><span style="font-weight:bold">T</span></span>ree (AST).
 * <p>
 * The result of calling {@link mathaid.calculator.base.evaluator.parser.Parser#parse parse}.
 * <p>
 * An {@code Expression} is an object produced by a parser from a token or set of tokens.
 * <p>
 * It has only one method for formatting itself to a builder.
 * <p>
 * This interface is a node in an AST that holds relevant data relative to its position in the tree hierarchy. The expression
 * <code>2x + y^3</code> will create an AST where '+' is the root and '2x' is the left branch (operand) and 'y^3' is the right
 * operand. Note that each operand are themselves trees.
 * <p>
 * It is recommended that expressions should be implemented as value/immutable objects. No method
 * <span style="font-weight:bold">SHOULD</span> mutate the contents of it's format. For expressions that can be evaluated, it is
 * recommended that the object should implement the {@link mathaid.calculator.base.evaluator.Evaluatable Evaluatable} interface
 * then when {@link mathaid.calculator.base.evaluator.Evaluatable#evaluate evaluate} is called, a new object should be returned
 * without mutating the state(s) of the caller.
 * 
 * @param <B> An object from which a formatted expression is built. It represents a builder that can be called recursively from
 *            an Abstract Syntax Tree by the expression nodes resulting in building a given object representative of this
 *            expression in the specified format.
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface Expression<B> {
	/**
	 * Recursively inserts a given representation of this {@code Expression} in to the builder. This can be used recursively to
	 * chain-format multiple expressions.
	 * <p>
	 * This method should never be called in isolation but from the root of the AST, which will recursively build the format by
	 * calling same method for each of it's child nodes. If this method is manually called, it's behaviour is undefined. If it is
	 * called more than once, it's behaviour is undefined.
	 * <p>
	 * This method does not makes any mutable changes to {@code this}.
	 * 
	 * @param formatBuilder an object for formatting this {@code Expression}. It may contain chained object representations of
	 *                      {@code Expression}s.
	 */
	void format(B formatBuilder);
}
