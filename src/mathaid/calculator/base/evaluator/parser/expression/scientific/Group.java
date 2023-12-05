/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.scientific;

import java.util.Objects;

import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.evaluator.parser.expression.scientific.Name.Params;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;

/*
 * Date: 11 Sep 2022----------------------------------------------------------- 
 * Time created: 17:52:25---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression.scientific------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Group.java------------------------------------------------------ 
 * Class name: Group------------------------------------------------ 
 */
/**
 * Represents structural symbols that have a start symbol, an end symbol and an expression in between as it's content i.e
 * represents a Grouped expression. An example of this is the parenthesis {@code (x + y)}, where {@code x + y} is the content.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Group extends EvaluatableExpression<Name.Params> {

	/*
	 * Date: 11 Sep 2022----------------------------------------------------------- 
	 * Time created: 17:53:02--------------------------------------------------- 
	 */
	/**
	 * Constructs a {@code Group} by specifying the content, evaluation and format options.
	 * 
	 * @param content the root of the expression tree in this group.
	 * @param params  the {@code ExpressionParams} representing options for the evaluation and format within this expression.
	 */
	public Group(EvaluatableExpression<Params> content, Params params) {
		super(params);
		this.content = content;
	}

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:31:42 ---------------------------------------------------
	 */
	/**
	 * Gets the content of this group.
	 * 
	 * @return the content expression.
	 */
	public EvaluatableExpression<Params> getContent() {
		return content;
	}

	/*
	 * Most Recent Date: 11 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:53:10--------------------------------------
	 */
	/**
	 * Appends the {@code LinkedSegment} representation of this grouped expression and it's content into the format builder.
	 * <p>
	 * This has no side-effects.
	 * 
	 * @param formatBuilder {@inheritDoc}
	 */
	@Override
	public void format(SegmentBuilder formatBuilder) {
		if (content instanceof Name) {
			content.format(formatBuilder);
			return;
		} else if (content instanceof Operator) {
			Operator div = (Operator) content;
			// (3/a)
			if (content.getName().equals("/")) {
				EvaluatableExpression<Params> l = div.getLeft();
				EvaluatableExpression<Params> r = div.getRight();
				if (l instanceof Name && r instanceof Name) {
					content.format(formatBuilder);
					return;
				}
			}
		}
		formatBuilder.append(Segments.paren(true));
		content.format(formatBuilder);
		formatBuilder.append(Segments.paren(false));
	}

	/*
	 * Most Recent Date: 11 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:53:10--------------------------------------
	 */
	/**
	 * Evaluates this {@code Group} by calling {@link EvaluatableExpression#evaluate() evaluate} on it's content.
	 * <p>
	 * All calculations are done with a numerical precision of <code>{@linkplain Params#getScale() scale} + 5</code> and may throw
	 * exceptions that indicate that the value were out of range.
	 * <p>
	 * This has no side-effects.
	 * 
	 * @return the result of evaluating the content. May return the same object especially if called more than once.
	 */
	@Override
	public EvaluatableExpression<Params> evaluate() {
		EvaluatableExpression<Params> x = content.evaluate();
		return (x instanceof Name && !(x instanceof Operator || x instanceof Prefix || x instanceof Postfix)) ? x
				: new Group(x, getParams());
	}

	/*
	 * Most Recent Date: 11 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:53:10--------------------------------------
	 */
	/**
	 * Gets the empty string {@code ""} as a group does not have a name
	 * 
	 * @return an empty string.
	 */
	@Override
	public String getName() {
		return "";
	}

	/**
	 * The expression content of this group.
	 */
	private final EvaluatableExpression<Params> content;

	/*
	 * Most Recent Date: 11 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:53:10--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param o {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Group) {
			return content.equals(((Group) o).content);
		}
		return false;
	}

	/*
	 * Most Recent Date: 11 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:53:10--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this, content);
	}

}
