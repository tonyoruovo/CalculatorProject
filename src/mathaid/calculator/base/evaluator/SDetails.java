/*
 * Date: 11 Nov 2023 -----------------------------------------------------------
 * Time created: 09:54:26 ---------------------------------------------------
 */
/**
 * 
 */
package mathaid.calculator.base.evaluator;

import java.text.CharacterIterator;
import java.text.Collator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import org.matheclipse.core.eval.EvalUtilities;

import mathaid.MomentString;
import mathaid.calculator.base.evaluator.parser.PrattParser;
import mathaid.calculator.base.evaluator.parser.ScientificLexer;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression.ExpressionParams;
import mathaid.calculator.base.evaluator.parser.expression.scientific.Name;
import mathaid.calculator.base.evaluator.parser.expression.scientific.Name.Params;
import mathaid.calculator.base.typeset.BasicSegment;
import mathaid.calculator.base.typeset.Digits;
import mathaid.calculator.base.typeset.LinkedSegment;
import mathaid.calculator.base.typeset.NumberAdapter;
import mathaid.calculator.base.typeset.Segment;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;
import mathaid.calculator.base.util.Utility;
import mathaid.calculator.base.value.BigFraction;

/*
 * Date: 11 Nov 2023 -----------------------------------------------------------
 * Time created: 09:54:26 ---------------------------------------------------
 * Package: mathaid.calculator.base.evaluator ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: SDetails.java ------------------------------------------------------
 * Class name: SDetails ------------------------------------------------
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class SDetails implements Result.Details {
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
	 * Returns {@code true} if {@code s} is an element of the given {@code Iterable}
	 * otherwise returns {@code false}.
	 * 
	 * @param s    the value to searched
	 * @param list the search destination
	 * @return {@code true} if list contains s otherwise returns {@code false}.
	 */
	private static boolean contains(String s, Iterable<String> list) {
		for (String st : list) {
			if (s.contains(st))
				return true;
		}
		return false;
	}
	
	public NavigableMap<MomentString, LinkedSegment> getDetails(ExpressionParams<?> p, LinkedSegment src) {
		details.clear();
		computeDetails((Scientific) p, src);
		return java.util.Collections.unmodifiableNavigableMap(details);
	}
	
	@SuppressWarnings("null")
	private void computeDetails(final Scientific evaluator, LinkedSegment src) {
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
		boolean isAtomic = c.compare(evaluator.getSymja().evaluate(String.format("AtomicQ[%s]", s)), "True") == 0;
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
			boolean isNumber = c.compare(evaluator.getSymja().evaluate(String.format("NumberQ[%s]", s)), "True") == 0;
			boolean isSymbol = c.compare(evaluator.getSymja().evaluate(String.format("SymbolQ[%s]", s)), "True") == 0;
			if (isNumber) {
				boolean isComplex = c.compare(evaluator.getSymja().evaluate(String.format("RealNumberQ[%s]", s)).toString(),
						"False") == 0;
				boolean isFrac = s.indexOf("Rational[") >= 0 || Utility.isFraction(s);
				boolean isInteger = c.compare(evaluator.getSymja().evaluate(String.format("IntegerQ[%s]", s)), "True") == 0;
				if (isComplex) {
					details.put(new MomentString("Expression"), CompDetails.getExpression(s, evaluator));
					details.put(new MomentString("Engineering notation"), CompDetails.inEngineering(s, evaluator));
					details.put(new MomentString("Engineering suffix notation"),
							CompDetails.inEngineeringSuffix(s, evaluator));
					details.put(new MomentString("Scientific notation"), CompDetails.inScientific(s, evaluator));
					details.put(new MomentString("Fixed notation"), CompDetails.inFixed(s, evaluator));
					details.put(new MomentString("Real"), CompDetails.getReal(s, evaluator));
					details.put(new MomentString("Imaginary"), CompDetails.getImaginary(s, evaluator));
					details.put(new MomentString("Argument"), CompDetails.getArg(s, evaluator));
					details.put(new MomentString("Magnitude"), CompDetails.getMagnitude(s, evaluator));
					details.put(new MomentString("X coordinate"), CompDetails.getXCoordinate(s, evaluator));
					details.put(new MomentString("Y coordinate"), CompDetails.getYCoordinate(s, evaluator));
					details.put(new MomentString("As common fraction"), CompDetails.inCommonFraction(s, evaluator));
				} else if (isFrac) {
					if (Utility.isFraction(s)) {
						BigFraction f = Digits.fromSegmentString(s);
						LinkedSegment sg = Digits.toSegment(f, false, EvaluatableExpression.fromParams(evaluator, evaluator.getNumOfRepeats()));
						details.put(new MomentString("Expression"), sg);
					} else {
						details.put(new MomentString("Expression"), src);
					}
					details.put(new MomentString("Lowest term"), FractionalDetails.toLowestTerm(s, evaluator));
					details.put(new MomentString("Mixed fraction"),
							FractionalDetails.toMixedFraction(s, evaluator));
					details.put(new MomentString("Continued fraction"),
							FractionalDetails.getContinuedFraction(s, evaluator));
					details.put(new MomentString("Egyptian fraction"),
							FractionalDetails.getEgyptianFraction(s, evaluator));
					details.put(new MomentString("Prime factor(s)"), FractionalDetails.getFactors(s, evaluator));
					details.put(new MomentString("Period"), FractionalDetails.getPeriod(s, evaluator));
					details.put(new MomentString("Percentage"), FractionalDetails.getPercentage(s, evaluator));
					details.put(new MomentString("Quotient"), FractionalDetails.getQuotient(s, evaluator));
					details.put(new MomentString("Remainder"), FractionalDetails.getRemainder(s, evaluator));
					details.put(new MomentString("Decimal"), FractionalDetails.getFractionAsDecimal(s, evaluator));
					details.put(new MomentString("Engineering notation"),
							FractionalDetails.inEngineering(s, evaluator));
					details.put(new MomentString("Engineering suffix notation"),
							FractionalDetails.inEngineeringSuffix(s, evaluator));
					details.put(new MomentString("Scientific notation"),
							FractionalDetails.inScientific(s, evaluator));
					details.put(new MomentString("Fixed notation"), FractionalDetails.inFixed(s, evaluator));
//					TODO: details.put(new MomentString("Number line"), FractionalDetails.getPeriod(s, evaluator));
				} else if (isInteger) {
					details.put(new MomentString("Expression"), src);
					details.put(new MomentString("Prime factors"), FractionalDetails.getFactors(s, evaluator));
					details.put(new MomentString("Roman figure"),
							Segments.constant(s, Utility.romanNumeral(Integer.valueOf(s))));
				} else {
					details.put(new MomentString("Expression"), src);
					details.put(new MomentString("Scientific notation"), DecDetails.inScientific(s, evaluator));
					details.put(new MomentString("Engineering notation"), DecDetails.inEngineering(s, evaluator));
					details.put(new MomentString("Engineering SI"), DecDetails.inEngineeringSI(s, evaluator));
				}
			} else if (isSymbol) {
				details.put(new MomentString("Expression"), src);
				SegmentBuilder value = null;
				try {
					value = evaluator.getBoundVariables().get(s).get2nd().call(evaluator);
				} catch (NullPointerException e) {
					try {
						value = evaluator.getConstants().get(s).get2nd().call(evaluator);
					} catch (NullPointerException ex) {
					}
				}
				boolean hasValue = value != null;
				if (hasValue)
					details.put(new MomentString("Value"), value.toSegment());
			}
//			super.run();
			return;
		}

		if (occurence(s, '*') > 1)
//			list.add(ExpressionDetailsList.REWRITE_DISTRIBUTE);
			details.put(new MomentString("Distribute"), rewrite(evaluator, s, REWRITE_DISTRIBUTE));
		if ((occurence(s, '(') > 0 && occurence(s, '^') > 0) || (occurence(s, '(') > 1))
			details.put(new MomentString("Expand"), rewrite(evaluator, s, REWRITE_EXPAND));
		if ((occurence(s, '(') > 0 && occurence(s, '^') > 0) || (occurence(s, '(') > 1))
			details.put(new MomentString("ExpandAll"), rewrite(evaluator, s, REWRITE_EXPAND_ALL));
		if (occurence(s, 'I') > 0 || s.contains("Complex"))
			details.put(new MomentString("ExpandComplex"), rewrite(evaluator, s, REWRITE_EXPAND_COMPLEX));
		if (occurence(s, '/') > 0 && occurence(s, '(') > 1) {
			if (occurence(s, 'y') == 0 || occurence(s, 'x') == 0)
				details.put(new MomentString("Apart"), rewrite(evaluator, s, REWRITE_FRACTIONS_APART));
			else {
				details.put(new MomentString("Apartx"), rewrite(evaluator, s, REWRITE_FRACTIONS_APARTX));
				details.put(new MomentString("Aparty"), rewrite(evaluator, s, REWRITE_FRACTIONS_APARTY));
			}
		}
		if (occurence(s, '/') > 1)
			details.put(new MomentString("Together"), rewrite(evaluator, s, REWRITE_FRACTIONS_TOGETHER));
		if ((occurence(s, '*') > 0 && occurence(s, '^') > 0) || (occurence(s, '^') > 1))
			details.put(new MomentString("PowerExpand"), rewrite(evaluator, s, REWRITE_EXPAND_POWER));
		if (occurence(s, 'x') > 0) {
			details.put(new MomentString("SimplifyX"), rewrite(evaluator, s, REWRITE_SIMPLIFYX));
			details.put(new MomentString("SimplifyX1"), rewrite(evaluator, s, REWRITE_SIMPLIFYX1));
		}
		if (occurence(s, 'y') > 0) {
			details.put(new MomentString("SimplifyY"), rewrite(evaluator, s, REWRITE_SIMPLIFYY));
			details.put(new MomentString("SimplifyY1"), rewrite(evaluator, s, REWRITE_SIMPLIFYY1));
		}
		if (contains(s, trigFunctions())) {
			details.put(new MomentString("TrigExpand"), rewrite(evaluator, s, REWRITE_TRIG_EXPAND));
			details.put(new MomentString("TrigToExp"), rewrite(evaluator, s, REWRITE_TRIG_TO_EXPONENT));
		}
		if (containsOccurence(s, trigFunctions()) > 1 && s.contains("*"))
			details.put(new MomentString("TrigReduce"), rewrite(evaluator, s, REWRITE_TRIG_REDUCE));
		if(!evaluator.hasIntegral()) {
			String i = evaluator.getSymja().evaluate(String.format("Integrate[%1$s, %2$s]", s, evaluator.getIndependentVariables()[0]));
			ScientificLexer lexer = new ScientificLexer(i);
			PrattParser<EvaluatableExpression<Name.Params>, Name.Params> parser = new PrattParser<>();
			EvaluatableExpression<Params> f = parser.parse(lexer, lexer.getSyntax(), evaluator);

			SegmentBuilder integral = new SegmentBuilder();
			f.format(integral);
			
			details.put(new MomentString(String.format("Integral (with respect to %s)", evaluator.getIndependentVariables()[0])), integral.toSegment());
			i = evaluator.getSymja().evaluate(String.format("Integrate[%1$s, %2$s]", s, evaluator.getIndependentVariables()[1]));
			lexer.setSource(i);
			f = parser.parse(lexer, lexer.getSyntax(), evaluator);

			f.format(integral.deleteAll());
			
			details.put(new MomentString(String.format("Integral (with respect to %s)", evaluator.getIndependentVariables()[1])), integral.toSegment());
		}
		if(!evaluator.hasDifferential()) {
			String i = evaluator.getSymja().evaluate(String.format("D[%1$s, %2$s]", s, evaluator.getIndependentVariables()[0]));
			ScientificLexer lexer = new ScientificLexer(i);
			PrattParser<EvaluatableExpression<Name.Params>, Name.Params> parser = new PrattParser<>();
			EvaluatableExpression<Params> f = parser.parse(lexer, lexer.getSyntax(), evaluator);

			SegmentBuilder diff = new SegmentBuilder();
			f.format(diff);
			
			details.put(new MomentString(String.format("Differential (with respect to %s)", evaluator.getIndependentVariables()[0])), diff.toSegment());
			i = evaluator.getSymja().evaluate(String.format("D[%1$s, %2$s]", s, evaluator.getIndependentVariables()[1]));
			lexer.setSource(i);
			f = parser.parse(lexer, lexer.getSyntax(), evaluator);

			f.format(diff.deleteAll());
			
			details.put(new MomentString(String.format("Differential (with respect to %s)", evaluator.getIndependentVariables()[1])), diff.toSegment());
		}
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
	 * @param evaluator the evaluator to be used for expanding expression. This is usually the Symja evaluator.
	 * @param s    the expression;
	 * @param args varargs whereby any of the constants given by this class is a
	 *             valid argument.
	 * @return a {@code LinkedSegment} object which corresponds to the expression being
	 *         rewritten with the given varargs.
	 */
	private LinkedSegment rewrite(Scientific evaluator, String s, int... args) {
		Set<Integer> listOfArgs = new HashSet<>(args.length);
		for (Integer i : args)
			listOfArgs.add(i);
		s = evaluator.getSymja().evaluate(s);

		if (listOfArgs.contains(REWRITE_DISTRIBUTE))
			s = evaluator.getSymja().evaluate(String.format("Distribute[%s]", s));
		if (listOfArgs.contains(REWRITE_EXPAND))
			s = evaluator.getSymja().evaluate(String.format("Expand[%s]", s));
		if (listOfArgs.contains(REWRITE_EXPAND_ALL))
			s = evaluator.getSymja().evaluate(String.format("ExpandAll[%s]", s));
		if (listOfArgs.contains(REWRITE_EXPAND_COMPLEX))
			s = evaluator.getSymja().evaluate(String.format("ComplexExpand[%s]", s));
		if (listOfArgs.contains(REWRITE_FRACTIONS_APART))
			s = evaluator.getSymja().evaluate(String.format("Apart[%s]", s));
		if (listOfArgs.contains(REWRITE_FRACTIONS_APARTX))
			s = evaluator.getSymja().evaluate(String.format("Apart[%s,x]", s));
		if (listOfArgs.contains(REWRITE_FRACTIONS_APARTY))
			s = evaluator.getSymja().evaluate(String.format("Apart[%s,y]", s));
		if (listOfArgs.contains(REWRITE_FRACTIONS_TOGETHER))
			s = evaluator.getSymja().evaluate(String.format("Together[%s]", s));
		if (listOfArgs.contains(REWRITE_HOLD))
			s = evaluator.getSymja().evaluate(String.format("Hold[%s]", s));
//		if (listOfArgs.contains(REWRITE_HOLD_ALL))
//			s = e.evaluate("Hold[" + symja + "]");
		if (listOfArgs.contains(REWRITE_HOLDFORM))
			s = evaluator.getSymja().evaluate(String.format("HoldForm[%s]", s));
//		if (listOfArgs.contains(REWRITE_HOLD_FIRST))
//			s = e.evaluate("HoldFirst[" + symja + "]");
//		if (listOfArgs.contains(REWRITE_HOLD_REST))
//			s = e.evaluate("HoldRest[" + symja + "]");
		if (listOfArgs.contains(REWRITE_EXPAND_POWER))
			s = evaluator.getSymja().evaluate(String.format("PowerExpand[%s]", s));
		if (listOfArgs.contains(REWRITE_SIMPLIFY))
			s = evaluator.getSymja().evaluate(String.format("Simplify[%s]", s));
		if (listOfArgs.contains(REWRITE_SIMPLIFYX))
			s = evaluator.getSymja().evaluate(String.format("Simplify[%s, Assumptions -> x>0]", s));
		if (listOfArgs.contains(REWRITE_SIMPLIFYY))
			s = evaluator.getSymja().evaluate(String.format("Simplify[%s, Assumptions -> y>0]", s));
		if (listOfArgs.contains(REWRITE_SIMPLIFYX1))
			s = evaluator.getSymja().evaluate(String.format("Simplify[%s, Assumptions -> x<0]", s));
		if (listOfArgs.contains(REWRITE_SIMPLIFYY1))
			s = evaluator.getSymja().evaluate(String.format("Simplify[%s, Assumptions -> y<0]", s));
		if (listOfArgs.contains(REWRITE_TRACE))
			s = evaluator.getSymja().evaluate(String.format("Trace[%s]", s));
		if (listOfArgs.contains(REWRITE_THROUGH_FUNCTIONS))
			s = evaluator.getSymja().evaluate(String.format("Through[%s]", s));
		if (listOfArgs.contains(REWRITE_TRIG_EXPAND))
			s = evaluator.getSymja().evaluate(String.format("TrigExpand[%s]", s));
		if (listOfArgs.contains(REWRITE_TRIG_REDUCE))
			s = evaluator.getSymja().evaluate(String.format("TrigReduce[%s]", s));
		if (listOfArgs.contains(REWRITE_TRIG_TO_EXPONENT))
			s = evaluator.getSymja().evaluate(String.format("TrigToExp[%s]", s));

		ScientificLexer lexer = new ScientificLexer(s);
		PrattParser<EvaluatableExpression<Name.Params>, Name.Params> parser = new PrattParser<>();
		EvaluatableExpression<Params> f = parser.parse(lexer, lexer.getSyntax(), evaluator);

		SegmentBuilder sb = new SegmentBuilder();
		f.format(sb);
		if (evaluator.hasIntegral())
			sb.append(new BasicSegment("", " + C", Segment.Type.AUTO_COMPLETE));
		return sb.toSegment();
	}

	final NavigableMap<MomentString, LinkedSegment> details = new TreeMap<>((x, y) -> x.compareTo(y));

}
