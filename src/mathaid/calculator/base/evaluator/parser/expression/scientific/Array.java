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
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Array extends EvaluatableExpression<Params> {

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 00:12:13---------------------------------------------------
	 */
	/**
	 * @param params
	 */
	public Array(List<EvaluatableExpression<Params>> array, Params params) {
		super(params);
		content = array;
	}

	public List<EvaluatableExpression<Params>> getArray() {
		return content;
	}

	/*
	 * Most Recent Date: 16 Sep 2022-----------------------------------------------
	 * Most recent time created: 00:12:18--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param formatBuilder
	 */
	@Override
	public void format(SegmentBuilder formatBuilder) {
		formatBuilder.append(Segments.array(listToSegments()));
	}
	
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
	 * {@inheritDoc}
	 * 
	 * @return
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
	 * {@inheritDoc}
	 * 
	 * @return
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
	 * @param o
	 * @return
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
	 * @return
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this, content);
	}

	private final List<EvaluatableExpression<Params>> content;

}
