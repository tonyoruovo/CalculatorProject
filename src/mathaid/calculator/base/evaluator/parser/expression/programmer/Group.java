/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.programmer;

import java.math.BigInteger;

import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;
import mathaid.calculator.base.value.BinaryFPPrecision.BinaryFP;

/*
 * Date: 1 Dec 2022----------------------------------------------------------- 
 * Time created: 06:58:37---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression.programmer------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Group.java------------------------------------------------------ 
 * Class name: Group------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Group extends PExpression {

	/*
	 * Date: 1 Dec 2022-----------------------------------------------------------
	 * Time created: 07:32:00---------------------------------------------------
	 */
	/**
	 * @param params
	 */
	public Group(PExpression content, Params params) {
		super(params);
		this.content = content;
	}

	/*
	 * Date: 1 Dec 2022-----------------------------------------------------------
	 * Time created: 07:47:58---------------------------------------------------
	 */
	/**
	 * @return the content
	 */
	public PExpression getContent() {
		return content;
	}

	/*
	 * Most Recent Date: 1 Dec 2022-----------------------------------------------
	 * Most recent time created: 07:22:13--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param formatBuilder
	 */
	@Override
	public void format(SegmentBuilder formatBuilder) {
		if (content instanceof Name && (content.isInteger() || content.isFloatingPoint())) {
			content.format(formatBuilder);
			return;
		}
		formatBuilder.append(Segments.paren(true));
		content.format(formatBuilder);
		formatBuilder.append(Segments.paren(false));
	}

	/*
	 * Most Recent Date: 1 Dec 2022-----------------------------------------------
	 * Most recent time created: 07:22:13--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public PExpression evaluate() {
		return content.evaluate();
	}

	/*
	 * Most Recent Date: 1 Dec 2022-----------------------------------------------
	 * Most recent time created: 07:22:13--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public BigInteger getCarry() {
		throw new IllegalStateException("has no value to produce");
	}

	/*
	 * Most Recent Date: 1 Dec 2022-----------------------------------------------
	 * Most recent time created: 07:22:13--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	BinaryFP getFloatingPoint() {
//		throw new IllegalStateException("has no value to produce");
		return null;
	}

	/*
	 * Most Recent Date: 1 Dec 2022-----------------------------------------------
	 * Most recent time created: 07:22:13--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	BigInteger getInteger() {
//		throw new IllegalStateException("has no value to produce");
		return null;
	}

	/*
	 * Most Recent Date: 1 Dec 2022-----------------------------------------------
	 * Most recent time created: 07:22:13--------------------------------------
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
	 * Most Recent Date: 1 Dec 2022-----------------------------------------------
	 * Most recent time created: 07:22:13--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param o
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Group) {
			return content.equals(((Group) o).content);
		}
		return false;
	}

	/*
	 * Most Recent Date: 1 Dec 2022-----------------------------------------------
	 * Most recent time created: 07:22:13--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public int hashCode() {
		return ~content.hashCode();
	}

	private final PExpression content;

}
