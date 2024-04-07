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
	 * Constructs a {@code Group} by specifying the content, evaluation and format options.
	 * 
	 * @param content the root of the expression tree in this group.
	 * @param params  the {@code Params} representing options for the evaluation and format within this expression.
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
	 * Gets the content of this group.
	 * 
	 * @return the content expression.
	 */
	public PExpression getContent() {
		return content;
	}

	/*
	 * Most Recent Date: 1 Dec 2022-----------------------------------------------
	 * Most recent time created: 07:22:13--------------------------------------
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
	 * Evaluates this {@code Group} by calling {@link PExpression#evaluate() evaluate} on it's content.
	 * <p>
	 * This has no side-effects.
	 * 
	 * @return the result of evaluating the content. May return the same object especially if called more than once.
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
	 * Gets the carry/overflow bit.
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public BigInteger getCarry() {
//		throw new IllegalStateException("has no value to produce");
		return content.getCarry();
	}

	/*
	 * Most Recent Date: 1 Dec 2022-----------------------------------------------
	 * Most recent time created: 07:22:13--------------------------------------
	 */
	/**
	 * Returns <code>null</code>.
	 * 
	 * @return <code>null</code>
	 */
	@Override
	BinaryFP getFloatingPoint() {
//		throw new IllegalStateException("has no value to produce");
		return content.getFloatingPoint();
	}

	/*
	 * Most Recent Date: 1 Dec 2022-----------------------------------------------
	 * Most recent time created: 07:22:13--------------------------------------
	 */
	/**
	 * Returns <code>null</code>.
	 * 
	 * @return <code>null</code>
	 */
	@Override
	BigInteger getInteger() {
//		throw new IllegalStateException("has no value to produce");
		return content.getInteger();
	}

	/*
	 * Date: 4 Apr 2024 -----------------------------------------------------------
	 * Time created: 23:02:51 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @return
	 */
	@Override
	Name toFloatingPoint() {
		return content.toFloatingPoint();
	}

	/*
	 * Date: 4 Apr 2024 -----------------------------------------------------------
	 * Time created: 23:02:51 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @return
	 */
	@Override
	Name toInteger() {
		return content.toInteger();
	}

	/*
	 * Most Recent Date: 1 Dec 2022-----------------------------------------------
	 * Most recent time created: 07:22:13--------------------------------------
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

	/*
	 * Most Recent Date: 1 Dec 2022-----------------------------------------------
	 * Most recent time created: 07:22:13--------------------------------------
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
	 * Most Recent Date: 1 Dec 2022-----------------------------------------------
	 * Most recent time created: 07:22:13--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return ~content.hashCode();
	}

	/**
	 * The expression content of this group.
	 */
	private final PExpression content;

}
