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
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Group extends EvaluatableExpression<Name.Params> {

	/*
	 * Date: 11 Sep 2022----------------------------------------------------------- 
	 * Time created: 17:53:02--------------------------------------------------- 
	 */
	/**
	 * @param params
	 */
	public Group(EvaluatableExpression<Params> content, Params params) {
		super(params);
		this.content = content;
	}
	
	public EvaluatableExpression<Params> getContent(){
		return content;
	}

	/*
	 * Most Recent Date: 11 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:53:10--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @param formatBuilder
	 */
	@Override
	public void format(SegmentBuilder formatBuilder) {
		if(content instanceof Name) {
			content.format(formatBuilder);
			return;
		} else if (content instanceof Operator) {
			Operator div = (Operator) content;
			// (3/a)
			if(content.getName().equals("/")) {
				EvaluatableExpression<Params> l = div.getLeft();
				EvaluatableExpression<Params> r = div.getRight();
				if(l instanceof Name && r instanceof Name)
				{
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
	 * {@inheritDoc}
	 * @return
	 */
	@Override
	public EvaluatableExpression<Params> evaluate() {
		EvaluatableExpression<Params> x = content.evaluate();
		return x instanceof Name ? x : new Group(x, getParams());
	}

	/*
	 * Most Recent Date: 11 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:53:10--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @return
	 */
	@Override
	public String getName() {
		return "";
	}
	
	private final EvaluatableExpression<Params> content;

	/*
	 * Most Recent Date: 11 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:53:10--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @param o
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
		if(o instanceof Group) {
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
	 * @return
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this, content);
	}

}
