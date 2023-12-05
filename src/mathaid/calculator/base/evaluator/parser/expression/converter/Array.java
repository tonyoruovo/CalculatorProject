/*
 * Date: Jan 19, 2023 -----------------------------------------------------------
 * Time created: 5:32:13 AM ---------------------------------------------------
 */
/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.converter;

import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.value.UnitVector;

/*
 * Date: Jan 19, 2023 -----------------------------------------------------------
 * Time created: 5:32:13 AM ---------------------------------------------------
 * Package: mathaid.calculator.base.evaluator.parser.expression.converter ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: Array.java ------------------------------------------------------
 * Class name: Array ------------------------------------------------
 */
/**
 * This the main valueType
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Array extends CExpression {

	/*
	 * Date: Jan 19, 2023 -----------------------------------------------------------
	 * Time created: 5:32:13 AM ---------------------------------------------------
	 */
	/**
	 */
	public Array(UnitVector v, Params p) {
		super(p);
		this.v = v;
	}

	/*
	 * Date: Jan 19, 2023 -----------------------------------------------------------
	 * Time created: 5:35:13 AM ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @param formatBuilder
	 */
	@Override
	public void format(SegmentBuilder formatBuilder) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * Date: Jan 19, 2023 -----------------------------------------------------------
	 * Time created: 5:35:13 AM ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @return
	 */
	@Override
	public Array evaluate() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Date: Jan 19, 2023 -----------------------------------------------------------
	 * Time created: 5:35:13 AM ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @return
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Date: Jan 19, 2023 -----------------------------------------------------------
	 * Time created: 5:35:13 AM ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @param o
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * Date: Jan 19, 2023 -----------------------------------------------------------
	 * Time created: 5:35:13 AM ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @return
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@SuppressWarnings("unused")
	private final UnitVector v;

}
