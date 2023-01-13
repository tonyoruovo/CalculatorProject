/**
 * 
 */
package mathaid.calculator.base.evaluator;

import java.text.CharacterIterator;
import java.text.Collator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.EvalUtilities;

import mathaid.MomentString;
import mathaid.calculator.base.converter.AngleUnit;
import mathaid.calculator.base.evaluator.parser.PrattParser;
import mathaid.calculator.base.evaluator.parser.ScientificLexer;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.evaluator.parser.expression.scientific.Name;
import mathaid.calculator.base.evaluator.parser.expression.scientific.Name.Params;
import mathaid.calculator.base.gui.GUIComponent;
import mathaid.calculator.base.gui.KeyAction;
import mathaid.calculator.base.gui.KeyBoard;
import mathaid.calculator.base.typeset.BasicSegment;
import mathaid.calculator.base.typeset.Digits;
import mathaid.calculator.base.typeset.LinkedSegment;
import mathaid.calculator.base.typeset.NumberAdapter;
import mathaid.calculator.base.typeset.Segment;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;
import mathaid.calculator.base.util.Tuple.Couple;
import mathaid.calculator.base.util.Utility;
import mathaid.calculator.base.value.BigFraction;
import mathaid.functional.Supplier.Function;

/*
 * Date: 4 Sep 2022----------------------------------------------------------- 
 * Time created: 06:02:35---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: SymjaEvaluator.java------------------------------------------------------ 
 * Class name: SymjaEvaluator------------------------------------------------ 
 */
/**
 * A concrete Adapter of the SYMJA API into an {@code Evaluator}
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class ScientificCalculator<T extends GUIComponent<T>, F> implements Calculator<T, F>, Name.Params {

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
//	static final int REWRITE_DIFF = "diff".hashCode();
	/**
	 * A constant meant to be used as an argument for
	 * {@link #rewrite(EvalUtilities, int...)} that allows a symbolic expression to
	 * be rewritten using distributive laws.
	 * 
	 * @see DetailsList.SciDetailsList
	 */
	static final int REWRITE_DISTRIBUTE = "distribute".hashCode();
	/**
	 * A constant meant to be used as an argument for
	 * {@link #rewrite(EvalUtilities, int...)} that allows a symbolic expression to
	 * be rewritten by expanding grouped expressions.
	 * 
	 * @see DetailsList.SciDetailsList
	 */
	static final int REWRITE_EXPAND = "expand".hashCode();
	/**
	 * A constant meant to be used as an argument for
	 * {@link #rewrite(EvalUtilities, int...)} that allows a symbolic expression to
	 * be rewritten by expanding grouped expressions including powers and roots.
	 * 
	 * @see DetailsList.SciDetailsList
	 */
	static final int REWRITE_EXPAND_ALL = "expand".hashCode();
	/**
	 * A constant meant to be used as an argument for
	 * {@link #rewrite(EvalUtilities, int...)} that allows a symbolic expression to
	 * be rewritten by expanding grouped expressions containg complex expressions.
	 * 
	 * @see DetailsList.SciDetailsList
	 */
	static final int REWRITE_EXPAND_COMPLEX = "complex".hashCode();
//	static final int REWRITE_FACTOR_TERMS = "terms".hashCode();
//	static final int REWRITE_FACTOR_POLYNOMIAL = "factor".hashCode();
	/**
	 * A constant meant to be used as an argument for
	 * {@link #rewrite(EvalUtilities, int...)} that allows a rational polynomial
	 * expression to be rewritten as a sum of it's individual fractions.
	 * 
	 * @see DetailsList.SciDetailsList
	 */
	static final int REWRITE_FRACTIONS_APART = "apart".hashCode();
	/**
	 * A constant meant to be used as an argument for
	 * {@link #rewrite(EvalUtilities, int...)} that allows a rational polynomial
	 * expression to be rewritten as a sum of it's individual fractions considering
	 * "x" as the sole free variable and all else as constant.
	 * 
	 * @see DetailsList.SciDetailsList
	 */
	static final int REWRITE_FRACTIONS_APARTX = "x".hashCode();
	/**
	 * A constant meant to be used as an argument for
	 * {@link #rewrite(EvalUtilities, int...)} that allows a rational polynomial
	 * expression to be rewritten as a sum of it's individual fractions considering
	 * "y" as the sole free variable and all else as constant.
	 * 
	 * @see DetailsList.SciDetailsList
	 */
	static final int REWRITE_FRACTIONS_APARTY = "y".hashCode();
	/**
	 * A constant meant to be used as an argument for
	 * {@link #rewrite(EvalUtilities, int...)} that allows a rational polynomial
	 * expression to be rewritten as a sum of it's factors.
	 * 
	 * @see DetailsList.SciDetailsList
	 */
	static final int REWRITE_FRACTIONS_TOGETHER = "togther".hashCode();
	/**
	 * A constant meant to be used as an argument for
	 * {@link #rewrite(EvalUtilities, int...)} that allows a symbolic expression to
	 * be prevented from being evaluated.
	 */
	static final int REWRITE_HOLD = "hold".hashCode();
//	static final int REWRITE_HOLD_ALL = "all".hashCode();
	/**
	 * The same as {@link #REWRITE_HOLD} but will not add it self in the
	 * pretty-print string.
	 */
	static final int REWRITE_HOLDFORM = "form".hashCode();
//	static final int REWRITE_HOLD_FIRST = "first".hashCode();
//	static final int REWRITE_HOLD_REST = "rest".hashCode();
//	static final int REWRITE_INT = "int".hashCode();
	/**
	 * Performs the same functions {@link #REWRITE_EXPAND} but only on exponentials.
	 * 
	 * @see DetailsList.SciDetailsList
	 */
	static final int REWRITE_EXPAND_POWER = "power".hashCode();
//	static final int REWRITE_RATIONALISE = "rationalise".hashCode();
//	static final int REWRITE_REFINE = "refine".hashCode();
	/**
	 * A constant meant to be used as an argument for
	 * {@link #rewrite(EvalUtilities, int...)} that allows simplification to be
	 * carried out on a symbolic expression.
	 * 
	 */
	static final int REWRITE_SIMPLIFY = "simplify".hashCode();
	/**
	 * Same as {@link #REWRITE_SIMPLIFY} but assumes that "x" is greater than 0.
	 * 
	 * @see DetailsList.SciDetailsList
	 */
	static final int REWRITE_SIMPLIFYX = "simplifyX".hashCode();
	/**
	 * Same as {@link #REWRITE_SIMPLIFY} but assumes that "y" is greater than 0.
	 * 
	 * @see DetailsList.SciDetailsList
	 */
	static final int REWRITE_SIMPLIFYY = "simplifyY".hashCode();
	/**
	 * Same as {@link #REWRITE_SIMPLIFY} but assumes that "x" is less than 0.
	 * 
	 * @see DetailsList.SciDetailsList
	 */
	static final int REWRITE_SIMPLIFYX1 = "simplifyX1".hashCode();
	/**
	 * Same as {@link #REWRITE_SIMPLIFY} but assumes that "y" is less than 0.
	 * 
	 * @see DetailsList.SciDetailsList
	 */
	static final int REWRITE_SIMPLIFYY1 = "simplifyY1".hashCode();
	/**
	 * A constant meant to be used as an argument for
	 * {@link #rewrite(EvalUtilities, int...)} that allows the evaluation steps of a
	 * symbolic expression to be traced. This useful for applications where part
	 * questions are solved and their workings shown.
	 */
	static final int REWRITE_TRACE = "trace".hashCode();
	/**
	 * A constant meant to be used as an argument for
	 * {@link #rewrite(EvalUtilities, int...)} that allows a symbolic function
	 * expression to be rewritten by applying another function to it.
	 */
	static final int REWRITE_THROUGH_FUNCTIONS = "through".hashCode();
	/**
	 * A constant meant to be used as an argument for
	 * {@link #rewrite(EvalUtilities, int...)} that allows a symbolic expression to
	 * be rewritten by simplifying all trigonometric functions within it.
	 * 
	 * @see DetailsList.SciDetailsList
	 * 
	 */
	static final int REWRITE_TRIG_EXPAND = "trig".hashCode();
	/**
	 * A constant meant to be used as an argument for
	 * {@link #rewrite(EvalUtilities, int...)} that allows a symbolic expression to
	 * be rewritten by simplifying all trigonometric functions within it.
	 * 
	 * @see DetailsList.SciDetailsList
	 * 
	 */
	static final int REWRITE_TRIG_REDUCE = "reduce".hashCode();
	/**
	 * A constant meant to be used as an argument for
	 * {@link #rewrite(EvalUtilities, int...)} that allows a trigonometric symbolic
	 * expressions to be rewritten to exponential symbolic expressions.
	 * 
	 * @see DetailsList.SciDetailsList
	 */
	static final int REWRITE_TRIG_TO_EXPONENT = "exponent".hashCode();

	/*
	 * Date: 10 Aug 2021-----------------------------------------------------------
	 * Time created: 20:38:33--------------------------------------------
	 */
	/**
	 * Searches for the number of occurrences of the given character within the
	 * specified string and returns the result.
	 * 
	 * @param src the string to be searched.
	 * @param ch  the character to be searched for.
	 * @return the number of indexes that ch can be found within src.
	 */
	private static int occurence(String src, char ch) {
		CharacterIterator ci = new StringCharacterIterator(src);
		int i = 0;
		for (char c = ci.first(); c != CharacterIterator.DONE; c = ci.next())
			if (ch == c)
				i += 1;
		return i;
	}

	/*
	 * Date: 10 Aug 2021-----------------------------------------------------------
	 * Time created: 20:45:27--------------------------------------------
	 */
	/**
	 * Returns an {@code Iterable} of string whereby every string within is a valid
	 * trigonometric function name within the symja engine.
	 * 
	 * @return an {@code Iterable} of strings.
	 */
	private static Iterable<String> trigFunctions() {
		return Arrays.asList("Sin", "Cos", "Tan", "Csc", "Cot", "Sec", "Sinh", "Cosh", "Tanh", "Csch", "Coth", "Sech",
				"ArcSin", "ArcCos", "ArcTan", "ArcCsc", "ArcCot", "ArcSec", "ArcSinh", "ArcCosh", "ArcTanh", "ArcCsch",
				"ArcCoth", "ArcSech");
	}

	/*
	 * Date: 10 Aug 2021-----------------------------------------------------------
	 * Time created: 20:47:19--------------------------------------------
	 */
	/**
	 * Searches the given {@code Iterable} for the given string and returns the
	 * number of times s is found within list.
	 * 
	 * @param s    the string to be earched for.
	 * @param list the object where the search takes place.
	 * @return the max number of indexes within the given {@code Iterable} where the
	 *         given string is found.
	 */
	private static int containsOccurence(String s, Iterable<String> list) {
		int o = 0;
		for (String st : list) {
			if (s.contains(st))
				o += 1;
		}
		return o;
	}

	/*
	 * Date: 10 Aug 2021-----------------------------------------------------------
	 * Time created: 20:51:19--------------------------------------------
	 */
	/**
	 * Returns {@code true} if s is an element of the given {@code Iterable}
	 * otherwise returns {@code false}.
	 * 
	 * @param s    the value to searched
	 * @param list the search destination
	 * @return {@code true} if list constains s otherwise returns {@code false}.
	 */
	private static boolean contains(String s, Iterable<String> list) {
		for (String st : list) {
			if (s.contains(st))
				return true;
		}
		return false;
	}

	static class Symja implements Evaluator<String> {

		Symja(Params p) {
			EvalEngine ev = new EvalEngine(false);
			ev.setNumericPrecision(p.getScale());
			ev.setNumericMode(p.getResultType() == Params.ResultType.EXPRESSION, p.getScale());
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

	private static Evaluator<String> getSymjaEvaluator(Params params) {
		return new Symja(params);
	}

	/*
	 * Date: 4 Sep 2022-----------------------------------------------------------
	 * Time created: 06:02:35---------------------------------------------------
	 */
	/**
	 * @param precision
	 * @param precision
	 * @param useSquareBracket a flag for whether square brackets should be used for
	 *                         functions such as {@code Sin[x]} instead of the usual
	 *                         {@code Sin(x)}.
	 */
	public ScientificCalculator(KeyBoard<T, F> keyboard, KeyAction<T, String> modKeys) {
		this.keyboard = keyboard;
		this.modKeys = modKeys;
		lexer = new ScientificLexer();
		parser = new PrattParser<>();
//		complex = false;
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
		evaluator = getSymjaEvaluator(this);
		details = new Scientific();
	}

	@Override
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
	public Map<String, Couple<String, Function<Params, SegmentBuilder>>> getConstants() {
		return constants;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param constants the constants to be added
	 */
	public void addConstant(String name, Couple<String, Function<Params, SegmentBuilder>> constant) {
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
	public Map<String, Couple<String, Function<Params, SegmentBuilder>>> getBoundVariables() {
		return boundVariables;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param boundVariables the boundVariables to set
	 */
	public void addBoundVariables(String name, Couple<String, Function<Params, SegmentBuilder>> boundVariable) {
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
	 * Most Recent Date: 4 Sep 2022-----------------------------------------------
	 * Most recent time created: 06:02:35--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param expression
	 * @return
	 */
	@Override
	public SegmentBuilder evaluate(String expression) {
		final String src;
		synchronized (evaluator) {
			src = evaluator.evaluate(expression);
		}
		EvaluatableExpression<Params> f;
		synchronized (lexer) {
			lexer.setSource(src);
			f = parser.parse(lexer, lexer.getSyntax(), this);
		}

		if (EXPRESSION_MASK == (modifier & EXPRESSION_MASK))// (getResultType() != Params.ResultType.EXPRESSION)
			f = f.evaluate();
		SegmentBuilder sb = new SegmentBuilder();
		f.format(sb);
		if (hasIntegral())
			sb.append(new BasicSegment("", " + C", LinkedSegment.Type.AUTO_COMPLETE));
		synchronized(details) {
//			details.setSource(this, sb.toSegment());
			details.setSource(sb.toSegment());
		}
		return sb;
	}

	class Scientific extends DetailsList<Name.Params> {

		/*
		 * Most Recent Date: 18 Sep 2022-----------------------------------------------
		 * Most recent time created: 14:25:00--------------------------------------
		 * TODO: will add graphing
		 */
		@SuppressWarnings("unused")
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			/*
			 * Since we already know that this is a result it is either one of the
			 * following: an expression, a constant, a fraction or a decimal(which may be
			 * complex)
			 * 
			 * The appendable below should not be a java stringBuilder but an object capable
			 * of supporting numerical syntax such as 1.8r5e-4
			 */
			Appendable sb = new NumberAdapter();
			src.toString(sb, null, new ArrayList<>(Arrays.asList(-1)));
			String s = sb.toString();
			Collator c = Collator.getInstance(Locale.US);
			// it has a single value as it's result
			boolean isAtomic = c.compare(evaluator.evaluate(String.format("AtomicQ[%s]", s)), "True") == 0;
			/*
			 * There 2 evaluation methods. 1. IExpr.evaluate(EvalEngine); this one is for
			 * numerical evaluations but is not always reliable because sometimes it returns
			 * the exact expression an alternative to this is the string - "N[expr, n]" -
			 * where expr stands for the expression to be evaluated and n stands for the
			 * number of significant figures to be displayed. 2.
			 * EvalUtilities.evaluate(String); this caters to expressions, especially if the
			 * result is expected to be an expression.
			 */
			if (isAtomic) {// meaning this expression contains just a single node
				boolean isNumber = c.compare(evaluator.evaluate(String.format("NumberQ[%s]", s)), "True") == 0;
				boolean isSymbol = c.compare(evaluator.evaluate(String.format("SymbolQ[%s]", s)), "True") == 0;
				if (isNumber) {
					boolean isComplex = c.compare(evaluator.evaluate(String.format("RealNumberQ[%s]", s)).toString(),
							"False") == 0;
					boolean isFrac = s.indexOf("Rational[") >= 0 || Utility.isFraction(s);
					boolean isInteger = c.compare(evaluator.evaluate(String.format("IntegerQ[%s]", s)), "True") == 0;
					if (isComplex) {
						details.put(new MomentString("Expression"), CompDetails.getExpression(s, ScientificCalculator.this));
						details.put(new MomentString("Engineering notation"), CompDetails.inEngineering(s, ScientificCalculator.this));
						details.put(new MomentString("Engineering suffix notation"),
								CompDetails.inEngineeringSuffix(s, ScientificCalculator.this));
						details.put(new MomentString("Scientific notation"), CompDetails.inScientific(s, ScientificCalculator.this));
						details.put(new MomentString("Fixed notation"), CompDetails.inFixed(s, ScientificCalculator.this));
						details.put(new MomentString("Real"), CompDetails.getReal(s, ScientificCalculator.this));
						details.put(new MomentString("Imaginary"), CompDetails.getImaginary(s, ScientificCalculator.this));
						details.put(new MomentString("Argument"), CompDetails.getArg(s, ScientificCalculator.this));
						details.put(new MomentString("Magnitude"), CompDetails.getMagnitude(s, ScientificCalculator.this));
						details.put(new MomentString("X coordinate"), CompDetails.getXCoordinate(s, ScientificCalculator.this));
						details.put(new MomentString("Y coordinate"), CompDetails.getYCoordinate(s, ScientificCalculator.this));
						details.put(new MomentString("As common fraction"), CompDetails.inCommonFraction(s, ScientificCalculator.this));
					} else if (isFrac) {
						if (Utility.isFraction(s)) {
							BigFraction f = Digits.fromSegmentString(s);
							LinkedSegment sg = Digits.toSegment(f, false, Calculator.fromParams(ScientificCalculator.this, ScientificCalculator.this.numOfRepeats));
							details.put(new MomentString("Expression"), sg);
						} else {
							details.put(new MomentString("Expression"), src);
						}
						details.put(new MomentString("Lowest term"), FractionalDetails.toLowestTerm(s, ScientificCalculator.this));
						details.put(new MomentString("Mixed fraction"),
								FractionalDetails.toMixedFraction(s, ScientificCalculator.this));
						details.put(new MomentString("Continued fraction"),
								FractionalDetails.getContinuedFraction(s, ScientificCalculator.this));
						details.put(new MomentString("Egyptian fraction"),
								FractionalDetails.getEgyptianFraction(s, ScientificCalculator.this));
						details.put(new MomentString("Prime factor(s)"), FractionalDetails.getFactors(s, ScientificCalculator.this));
						details.put(new MomentString("Period"), FractionalDetails.getPeriod(s, ScientificCalculator.this));
						details.put(new MomentString("Percentage"), FractionalDetails.getPercentage(s, ScientificCalculator.this));
						details.put(new MomentString("Quotient"), FractionalDetails.getQuotient(s, ScientificCalculator.this));
						details.put(new MomentString("Remainder"), FractionalDetails.getRemainder(s, ScientificCalculator.this));
						details.put(new MomentString("Decimal"), FractionalDetails.getFractionAsDecimal(s, ScientificCalculator.this));
						details.put(new MomentString("Engineering notation"),
								FractionalDetails.inEngineering(s, ScientificCalculator.this));
						details.put(new MomentString("Engineering suffix notation"),
								FractionalDetails.inEngineeringSuffix(s, ScientificCalculator.this));
						details.put(new MomentString("Scientific notation"),
								FractionalDetails.inScientific(s, ScientificCalculator.this));
						details.put(new MomentString("Fixed notation"), FractionalDetails.inFixed(s, ScientificCalculator.this));
//						TODO: details.put(new MomentString("Number line"), FractionalDetails.getPeriod(s, ScientificCalculator.this));
					} else if (isInteger) {
						details.put(new MomentString("Expression"), src);
						details.put(new MomentString("Prime factors"), FractionalDetails.getFactors(s, ScientificCalculator.this));
						details.put(new MomentString("Roman figure"),
								Segments.constant(s, Utility.romanNumeral(Integer.valueOf(s))));
					} else {
						details.put(new MomentString("Expression"), src);
						details.put(new MomentString("Scientific notation"), DecDetails.inScientific(s, ScientificCalculator.this));
						details.put(new MomentString("Engineering notation"), DecDetails.inEngineering(s, ScientificCalculator.this));
						details.put(new MomentString("Engineering SI"), DecDetails.inEngineeringSI(s, ScientificCalculator.this));
					}
				} else if (isSymbol) {
					details.put(new MomentString("Expression"), src);
					SegmentBuilder value = null;
					try {
						value = boundVariables.get(s).get2nd().call(ScientificCalculator.this);
					} catch (NullPointerException e) {
						try {
							value = constants.get(s).get2nd().call(ScientificCalculator.this);
						} catch (NullPointerException ex) {
						}
					}
					boolean hasValue = value != null;
					if (hasValue)
						details.put(new MomentString("Value"), value.toSegment());
				}
				super.run();
				return;
			}

			if (occurence(s, '*') > 1)
//				list.add(ExpressionDetailsList.REWRITE_DISTRIBUTE);
				details.put(new MomentString("Distribute"), rewrite(s, REWRITE_DISTRIBUTE));
			if ((occurence(s, '(') > 0 && occurence(s, '^') > 0) || (occurence(s, '(') > 1))
				details.put(new MomentString("Expand"), rewrite(s, REWRITE_EXPAND));
			if ((occurence(s, '(') > 0 && occurence(s, '^') > 0) || (occurence(s, '(') > 1))
				details.put(new MomentString("ExpandAll"), rewrite(s, REWRITE_EXPAND_ALL));
			if (occurence(s, 'I') > 0 || s.contains("Complex"))
				details.put(new MomentString("ExpandComplex"), rewrite(s, REWRITE_EXPAND_COMPLEX));
			if (occurence(s, '/') > 0 && occurence(s, '(') > 1) {
				if (occurence(s, 'y') == 0 || occurence(s, 'x') == 0)
					details.put(new MomentString("Apart"), rewrite(s, REWRITE_FRACTIONS_APART));
				else {
					details.put(new MomentString("Apartx"), rewrite(s, REWRITE_FRACTIONS_APARTX));
					details.put(new MomentString("Aparty"), rewrite(s, REWRITE_FRACTIONS_APARTY));
				}
			}
			if (occurence(s, '/') > 1)
				details.put(new MomentString("Together"), rewrite(s, REWRITE_FRACTIONS_TOGETHER));
			if ((occurence(s, '*') > 0 && occurence(s, '^') > 0) || (occurence(s, '^') > 1))
				details.put(new MomentString("PowerExpand"), rewrite(s, REWRITE_EXPAND_POWER));
			if (occurence(s, 'x') > 0) {
				details.put(new MomentString("SimplifyX"), rewrite(s, REWRITE_SIMPLIFYX));
				details.put(new MomentString("SimplifyX1"), rewrite(s, REWRITE_SIMPLIFYX1));
			}
			if (occurence(s, 'y') > 0) {
				details.put(new MomentString("SimplifyY"), rewrite(s, REWRITE_SIMPLIFYY));
				details.put(new MomentString("SimplifyY1"), rewrite(s, REWRITE_SIMPLIFYY1));
			}
			if (contains(s, trigFunctions())) {
				details.put(new MomentString("TrigExpand"), rewrite(s, REWRITE_TRIG_EXPAND));
				details.put(new MomentString("TrigToExp"), rewrite(s, REWRITE_TRIG_TO_EXPONENT));
			}
			if (containsOccurence(s, trigFunctions()) > 1 && s.contains("*"))
				details.put(new MomentString("TrigReduce"), rewrite(s, REWRITE_TRIG_REDUCE));
			if(!hasIntegral()) {
				String i = evaluator.evaluate(String.format("Integrate[%1$s, %2$s]", s, ScientificCalculator.this.getIndependentVariables()[0]));
				EvaluatableExpression<Params> f;
				synchronized (lexer) {
					lexer.setSource(i);
					f = parser.parse(lexer, lexer.getSyntax(), ScientificCalculator.this);
				}

				SegmentBuilder integral = new SegmentBuilder();
				f.format(integral);
				
				details.put(new MomentString(String.format("Integral (with respect to %s)", ScientificCalculator.this.getIndependentVariables()[0])), integral.toSegment());
				i = evaluator.evaluate(String.format("Integrate[%1$s, %2$s]", s, ScientificCalculator.this.getIndependentVariables()[1]));
				synchronized (lexer) {
					lexer.setSource(i);
					f = parser.parse(lexer, lexer.getSyntax(), ScientificCalculator.this);
				}

				f.format(integral.deleteAll());
				
				details.put(new MomentString(String.format("Integral (with respect to %s)", ScientificCalculator.this.getIndependentVariables()[1])), integral.toSegment());
			}
			if(!hasDifferential()) {
				String i = evaluator.evaluate(String.format("D[%1$s, %2$s]", s, ScientificCalculator.this.getIndependentVariables()[0]));
				EvaluatableExpression<Params> f;
				synchronized (lexer) {
					lexer.setSource(i);
					f = parser.parse(lexer, lexer.getSyntax(), ScientificCalculator.this);
				}

				SegmentBuilder diff = new SegmentBuilder();
				f.format(diff);
				
				details.put(new MomentString(String.format("Differential (with respect to %s)", ScientificCalculator.this.getIndependentVariables()[0])), diff.toSegment());
				i = evaluator.evaluate(String.format("D[%1$s, %2$s]", s, ScientificCalculator.this.getIndependentVariables()[1]));
				synchronized (lexer) {
					lexer.setSource(i);
					f = parser.parse(lexer, lexer.getSyntax(), ScientificCalculator.this);
				}

				f.format(diff.deleteAll());
				
				details.put(new MomentString(String.format("Differential (with respect to %s)", ScientificCalculator.this.getIndependentVariables()[1])), diff.toSegment());
			}

			super.run();
		}

		/*
		 * Date: 11 Aug 2021-----------------------------------------------------------
		 * Time created: 11:45:21--------------------------------------------
		 */
		/**
		 * Rewrites the expression given by this object using the constants of the
		 * enclosing class as valid arguments in the order that they are given and
		 * returns a {@code LinkedSegment} object representing the TeX notation.
		 * 
		 * @param s    the expression;
		 * @param args varargs whereby any of the constants given by this class is a
		 *             valid argument.
		 * @return a {@code DataTex} object which corresponds to the expression being
		 *         rewritten with the given varargs.
		 */
		private LinkedSegment rewrite(String s, int... args) {
			Set<Integer> listOfArgs = new HashSet<>(args.length);
			for (Integer i : args)
				listOfArgs.add(i);
			s = evaluator.evaluate(s);

			if (listOfArgs.contains(REWRITE_DISTRIBUTE))
				s = evaluator.evaluate(String.format("Distribute[%s]", s));
			if (listOfArgs.contains(REWRITE_EXPAND))
				s = evaluator.evaluate(String.format("Expand[%s]", s));
			if (listOfArgs.contains(REWRITE_EXPAND_ALL))
				s = evaluator.evaluate(String.format("ExpandAll[%s]", s));
			if (listOfArgs.contains(REWRITE_EXPAND_COMPLEX))
				s = evaluator.evaluate(String.format("ComplexExpand[%s]", s));
			if (listOfArgs.contains(REWRITE_FRACTIONS_APART))
				s = evaluator.evaluate(String.format("Apart[%s]", s));
			if (listOfArgs.contains(REWRITE_FRACTIONS_APARTX))
				s = evaluator.evaluate(String.format("Apart[%s,x]", s));
			if (listOfArgs.contains(REWRITE_FRACTIONS_APARTY))
				s = evaluator.evaluate(String.format("Apart[%s,y]", s));
			if (listOfArgs.contains(REWRITE_FRACTIONS_TOGETHER))
				s = evaluator.evaluate(String.format("Together[%s]", s));
			if (listOfArgs.contains(REWRITE_HOLD))
				s = evaluator.evaluate(String.format("Hold[%s]", s));
//			if (listOfArgs.contains(REWRITE_HOLD_ALL))
//				s = e.evaluate("Hold[" + symja + "]");
			if (listOfArgs.contains(REWRITE_HOLDFORM))
				s = evaluator.evaluate(String.format("HoldForm[%s]", s));
//			if (listOfArgs.contains(REWRITE_HOLD_FIRST))
//				s = e.evaluate("HoldFirst[" + symja + "]");
//			if (listOfArgs.contains(REWRITE_HOLD_REST))
//				s = e.evaluate("HoldRest[" + symja + "]");
			if (listOfArgs.contains(REWRITE_EXPAND_POWER))
				s = evaluator.evaluate(String.format("PowerExpand[%s]", s));
			if (listOfArgs.contains(REWRITE_SIMPLIFY))
				s = evaluator.evaluate(String.format("Simplify[%s]", s));
			if (listOfArgs.contains(REWRITE_SIMPLIFYX))
				s = evaluator.evaluate(String.format("Simplify[%s, Assumptions -> x>0]", s));
			if (listOfArgs.contains(REWRITE_SIMPLIFYY))
				s = evaluator.evaluate(String.format("Simplify[%s, Assumptions -> y>0]", s));
			if (listOfArgs.contains(REWRITE_SIMPLIFYX1))
				s = evaluator.evaluate(String.format("Simplify[%s, Assumptions -> x<0]", s));
			if (listOfArgs.contains(REWRITE_SIMPLIFYY1))
				s = evaluator.evaluate(String.format("Simplify[%s, Assumptions -> y<0]", s));
			if (listOfArgs.contains(REWRITE_TRACE))
				s = evaluator.evaluate(String.format("Trace[%s]", s));
			if (listOfArgs.contains(REWRITE_THROUGH_FUNCTIONS))
				s = evaluator.evaluate(String.format("Through[%s]", s));
			if (listOfArgs.contains(REWRITE_TRIG_EXPAND))
				s = evaluator.evaluate(String.format("TrigExpand[%s]", s));
			if (listOfArgs.contains(REWRITE_TRIG_REDUCE))
				s = evaluator.evaluate(String.format("TrigReduce[%s]", s));
			if (listOfArgs.contains(REWRITE_TRIG_TO_EXPONENT))
				s = evaluator.evaluate(String.format("TrigToExp[%s]", s));

			EvaluatableExpression<Params> f;
			synchronized (lexer) {
				lexer.setSource(s);
				f = parser.parse(lexer, lexer.getSyntax(), ScientificCalculator.this);
			}

			SegmentBuilder sb = new SegmentBuilder();
			f.format(sb);
			if (hasIntegral())
				sb.append(new BasicSegment("", " + C", Segment.Type.AUTO_COMPLETE));
			return sb.toSegment();
		}
	}

	private final ScientificLexer lexer;
	private final PrattParser<EvaluatableExpression<Params>, Params> parser;
//	private final Params params;
	private Evaluator<String> evaluator;

//	private boolean complex;
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
	private final DetailsList<Name.Params> details;
	private final Map<String, Couple<String, Function<Params, SegmentBuilder>>> constants;
	private final Map<String, Couple<String, Function<Params, SegmentBuilder>>> boundVariables;
	private final KeyBoard<T, F> keyboard;
	private final KeyAction<T, String> modKeys;

	/*
	 * Most Recent Date: 25 Sep 2022-----------------------------------------------
	 * Most recent time created: 15:54:38--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public DetailsList<?> getDetails() {
		return details;
	}

	/*
	 * Most Recent Date: 25 Sep 2022-----------------------------------------------
	 * Most recent time created: 15:54:38--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public KeyBoard<T, F> getRemoteControl() {
		return keyboard;
	}

	/*
	 * Most Recent Date: 25 Sep 2022-----------------------------------------------
	 * Most recent time created: 15:54:38--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public KeyAction<T, String> getModKeys() {
		return modKeys;
	}

}
