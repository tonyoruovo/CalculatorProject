/*
 * Date: 10 Nov 2023 -----------------------------------------------------------
 * Time created: 15:21:09 ---------------------------------------------------
 */
/**
 * 
 */
package mathaid.calculator.base.evaluator;

import java.util.HashMap;
import java.util.Map;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.EvalUtilities;

import mathaid.calculator.base.converter.AngleUnit;
import mathaid.calculator.base.evaluator.parser.PrattParser;
import mathaid.calculator.base.evaluator.parser.ScientificLexer;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.evaluator.parser.expression.scientific.Name;
import mathaid.calculator.base.typeset.BasicSegment;
import mathaid.calculator.base.typeset.LinkedSegment;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.util.Tuple.Couple;
import mathaid.functional.Supplier.Function;

public class Scientific implements Evaluator<LinkedSegment>, Name.Params {

	public static final int HYP_MASK = 0x20_00_00_00;
	public static final int RCPRCL_MASK = 0x40_00_00_00;

	public static final int CONST_MASK = 0x1_00_00_00;
	public static final int CAPS_LOCK_MASK = 0x2_00_00_00;

	public static final int EXPRESSION_MASK = 0x10_00;
	public static final int COMPLEX_MASK = 0x20_00;
	public static final int NUM_LOCK_MASK = 0x40_00;

	public static final int ALL_INPUT_MASK = 0;
	public static final int NUM_INPUT_TYPE = 1;
	public static final int INT_INPUT_MASK = 3;
	public static final int SYMBOL_INPUT_MASK = 2;

	private static class Symja implements Evaluator<String> {

		Symja(Name.Params p) {
			EvalEngine ev = new EvalEngine(false);
			ev.setNumericPrecision(p.getScale());
			ev.setNumericMode(p.getResultType() == Name.Params.ResultType.EXPRESSION, p.getScale());
			evaluator = new EvalUtilities(ev, false, false);
		}

		/*
		 * Most Recent Date: 18 Sep 2022-----------------------------------------------
		 * Most recent time created: 20:52:40--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @param expression
		 * @return
		 */
		@Override
		public String evaluate(String expression) {
			String s = evaluator.evaluate(expression).toString();
			return s;
		}

		private EvalUtilities evaluator;

	}
	
	Scientific(){
		lexer = new ScientificLexer();
		parser = new PrattParser<>();
		resultType = Name.Params.ResultType.DECIMAL;
		intGroupSize = 3;
		mantGroupSize = intGroupSize;
		recurringType = LinkedSegment.Type.VINCULUM;
		numOfRepeats = 2;
		scale = 28;
		decimalPoint = ".";
		intSeparator = ",";
		mantSeparator = " ";
		divisionString = new String[] { "/", "\\div" };
		multiplicationString = new String[] { "*", "\\times" };
		trig = AngleUnit.DEG;
		constants = new HashMap<>();
		boundVariables = new HashMap<>();
		symja = new Symja(Scientific.this);
	}

	public Evaluator<String> getSymja() {
		return symja;
	}

	public int getModifier() {
		return modifier;
	}

	public void setModifier(int modifier) {
		this.modifier = modifier;
		if (EXPRESSION_MASK == (this.modifier & EXPRESSION_MASK) && getResultType() != ResultType.EXPRESSION)
			setResultType(ResultType.EXPRESSION);
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the resultType
	 */
	@Override
	public int getResultType() {
		return resultType;
	}

	public void setResultType(int resultType) {
		this.resultType = resultType;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the decimalPoint
	 */
	@Override
	public String getDecimalPoint() {
		return decimalPoint;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param decimalPoint the decimalPoint to set
	 */
	public void setDecimalPoint(String decimalPoint) {
		this.decimalPoint = decimalPoint;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the intSeparator
	 */
	@Override
	public String getIntSeparator() {
		return intSeparator;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param intSeparator the intSeparator to set
	 */
	public void setIntSeparator(String intSeparator) {
		this.intSeparator = intSeparator;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the mantSeparator
	 */
	@Override
	public String getMantSeparator() {
		return mantSeparator;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param mantSeparator the mantSeparator to set
	 */
	public void setMantSeparator(String mantSeparator) {
		this.mantSeparator = mantSeparator;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the intGroupSize
	 */
	@Override
	public int getIntGroupSize() {
		return intGroupSize;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param intGroupSize the intGroupSize to set
	 */
	public void setIntGroupSize(int intGroupSize) {
		this.intGroupSize = intGroupSize;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the mantGroupSize
	 */
	@Override
	public int getMantGroupSize() {
		return mantGroupSize;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param mantGroupSize the mantGroupSize to set
	 */
	public void setMantGroupSize(int mantGroupSize) {
		this.mantGroupSize = mantGroupSize;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the constants
	 */
	@Override
	public Map<String, Couple<String, Function<Name.Params, SegmentBuilder>>> getConstants() {
		return constants;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param constants the constants to be added
	 */
	public void addConstant(String name, Couple<String, Function<Name.Params, SegmentBuilder>> constant) {
		this.constants.put(name, constant);
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the boundVariables
	 */
	@Override
	public Map<String, Couple<String, Function<Name.Params, SegmentBuilder>>> getBoundVariables() {
		return boundVariables;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param boundVariables the boundVariables to set
	 */
	public void addBoundVariables(String name, Couple<String, Function<Name.Params, SegmentBuilder>> boundVariable) {
		this.boundVariables.put(name, boundVariable);
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the divisionString
	 */
	@Override
	public String[] getDivisionString() {
		return divisionString;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param divisionString the divisionString to set
	 */
	public void setDivisionString(String[] divisionString) {
		this.divisionString = divisionString;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the multiplicationString
	 */
	@Override
	public String[] getMultiplicationString() {
		return multiplicationString;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param multiplicationString the multiplicationString to set
	 */
	public void setMultiplicationString(String[] multiplicationString) {
		this.multiplicationString = multiplicationString;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the complex
	 */
	@Override
	public boolean isComplex() {
		return COMPLEX_MASK == (modifier & COMPLEX_MASK);
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param complex the complex to set
	 */
	public void setComplex(boolean complex) {
		this.modifier ^= complex ? 1 : 0;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the intgeral
	 */
	@Override
	public boolean hasIntegral() {
		return integral;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param intgeral the intgeral to set
	 */
	public void setIntgeral(boolean intgeral) {
		this.integral = intgeral;
	}
	
	/*
	 * Most Recent Date: 8 Oct 2022-----------------------------------------------
	 * Most recent time created: 09:31:44--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @return
	 */
	@Override
	public boolean hasDifferential() {
		return diff;
	}

	/*
	 * Most Recent Date: 8 Oct 2022-----------------------------------------------
	 * Most recent time created: 09:31:44--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @return
	 */
	public void setDifferential(boolean diff) {
		this.diff = diff;
	}
	
	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the recurringType
	 */
	@Override
	public int getRecurringType() {
		return recurringType;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param recurringType the recurringType to set
	 */
	public void setRecurringType(int recurringType) {
		this.recurringType = recurringType;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the numOfRepeats
	 */
	@Override
	public int getNumOfRepeats() {
		return numOfRepeats;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param numOfRepeats the numOfRepeats to set
	 */
	public void setNumOfRepeats(int numOfRepeats) {
		this.numOfRepeats = numOfRepeats;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the scale
	 */
	@Override
	public int getScale() {
		return scale;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param scale the scale to set
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the trig
	 */
	@Override
	public AngleUnit getTrig() {
		return trig;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param trig the trig to set
	 */
	public void setTrig(AngleUnit trig) {
		this.trig = trig;
	}

	/*
	 * Date: 10 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:02:23 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @param expression
	 * @return
	 * @throws RuntimeException
	 */
	@Override
	public LinkedSegment evaluate(String expression) throws RuntimeException {
		final String src = symja.evaluate(expression);
		lexer.setSource(src);
		EvaluatableExpression<Name.Params> f = parser.parse(lexer, lexer.getSyntax(), Scientific.this);

		if (EXPRESSION_MASK == (modifier & EXPRESSION_MASK))// (getResultType() != Name.Params.ResultType.EXPRESSION)
			f = f.evaluate();
		SegmentBuilder sb = new SegmentBuilder();
		f.format(sb);
		if (hasIntegral())
			sb.append(new BasicSegment("", " + C", LinkedSegment.Type.AUTO_COMPLETE));
		return sb.toSegment();
	}
	private final Scientific.Symja symja;
	
	private final ScientificLexer lexer;
	private final PrattParser<EvaluatableExpression<Name.Params>, Name.Params> parser;

	private boolean integral;
	private boolean diff;
	private int modifier;
	private int resultType;
	private int intGroupSize;
	private int mantGroupSize;
	private int recurringType;
	private int numOfRepeats;
	private int scale;
	private String decimalPoint;
	private String intSeparator;
	private String mantSeparator;
	private String[] divisionString;
	private String[] multiplicationString;
	private AngleUnit trig;
	private final Map<String, Couple<String, Function<Name.Params, SegmentBuilder>>> constants;
	private final Map<String, Couple<String, Function<Name.Params, SegmentBuilder>>> boundVariables;
}