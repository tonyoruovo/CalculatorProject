/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.scientific;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.evaluator.parser.expression.scientific.Name.Params;
import mathaid.calculator.base.typeset.LinkedSegment;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;

/*
 * Date: 16 Sep 2022----------------------------------------------------------- 
 * Time created: 00:09:49---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression.scientific------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Array.java------------------------------------------------------ 
 * Class name: Array------------------------------------------------ 
 */
/**
 * A special expression representing lists in SYMJA.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Array extends EvaluatableExpression<Params> {

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 00:12:13---------------------------------------------------
	 */
	/**
	 * Constructs an {@code Array} by specifying the elements, evaluation and format options.
	 * 
	 * @param array  the elements.
	 * @param params the {@code ExpressionParams} representing options for the evaluation and format within this expression.
	 */
	public Array(List<EvaluatableExpression<Params>> array, Params params) {
		super(params);
		content = array;
	}

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:45:56 ---------------------------------------------------
	 */
	/**
	 * Gets the elements of {@code this} as an unmodifiable {@code List}.
	 * 
	 * @return the elements of this {@code Array}.
	 */
	public List<EvaluatableExpression<Params>> getArray() {
		return content;
	}

	/*
	 * Most Recent Date: 16 Sep 2022-----------------------------------------------
	 * Most recent time created: 00:12:18--------------------------------------
	 */
	/**
	 * Appends the {@code LinkedSegment} representation of this array expression and it's elements into the format builder.
	 * <p>
	 * This has no side-effects.
	 * 
	 * @param formatBuilder {@inheritDoc}
	 */
	@Override
	public void format(SegmentBuilder formatBuilder) {
		formatBuilder.append(Segments.array(listToSegments()));
	}

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:48:13 ---------------------------------------------------
	 */
	/**
	 * Formats all the elements into array.
	 * 
	 * @return an array of the {@code LinkedSegment} representation of all elements.
	 */
	private LinkedSegment[] listToSegments() {
		LinkedSegment[] s = new LinkedSegment[content.size()];
		SegmentBuilder sb = new SegmentBuilder();
		for (int i = 0; i < content.size(); i++) {
			EvaluatableExpression<Params> x = content.get(i);
			x.format(sb.deleteAll());
			s[i] = sb.toSegment();
		}
		return s;
	}

	/*
	 * Most Recent Date: 16 Sep 2022-----------------------------------------------
	 * Most recent time created: 00:12:18--------------------------------------
	 */
	/**
	 * Evaluates this {@code Array} by calling {@link EvaluatableExpression#evaluate() evaluate} on it's elements and returning
	 * {@code this} with all elements evaluated.
	 * <p>
	 * All calculations are done with a numerical precision of <code>{@linkplain Params#getScale() scale} + 5</code> and may throw
	 * exceptions that indicate that the value were out of range.
	 * <p>
	 * This has no side-effects.
	 * 
	 * @return an {@code Array} expression where all elements have been evaluated.
	 */
	@Override
	public EvaluatableExpression<Params> evaluate() {
		List<EvaluatableExpression<Params>> arg = new ArrayList<>();
		for (int i = 0; i < content.size(); i++)
			arg.add(arg.get(i).evaluate());
		return arg.size() > 1 ? new Array(arg, getParams()) : arg.get(0);
	}

	/*
	 * Most Recent Date: 16 Sep 2022-----------------------------------------------
	 * Most recent time created: 00:12:18--------------------------------------
	 */
	/**
	 * Gets the empty string {@code ""} as an array does not have a name
	 * 
	 * @return an empty string.
	 */
	@Override
	public String getName() {
		return "";
	}

	/*
	 * Most Recent Date: 16 Sep 2022-----------------------------------------------
	 * Most recent time created: 00:12:18--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param o {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Array) {
			return content.equals(((Array) o).content);
		}
		return false;
	}

	/*
	 * Most Recent Date: 16 Sep 2022-----------------------------------------------
	 * Most recent time created: 00:12:18--------------------------------------
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

	/**
	 * The elements of this array.
	 */
	private final List<EvaluatableExpression<Params>> content;

}
