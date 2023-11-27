/**
 * 
 */
package mathaid.calculator.base;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.CharacterIterator;
import java.text.Collator;
import java.text.StringCharacterIterator;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.matheclipse.core.eval.EvalUtilities;

import mathaid.calculator.base.converter.AngleUnit;
import mathaid.calculator.base.converter.AreaUnit;
import mathaid.calculator.base.converter.Currencies;
import mathaid.calculator.base.converter.Currencies.MediumOfExchange;
import mathaid.calculator.base.converter.CurrencyUnit;
import mathaid.calculator.base.converter.DataUnit;
import mathaid.calculator.base.converter.DistanceUnit;
import mathaid.calculator.base.converter.EnergyUnit;
import mathaid.calculator.base.converter.ForceUnit;
import mathaid.calculator.base.converter.FrequencyUnit;
import mathaid.calculator.base.converter.FuelConsumptionUnit;
import mathaid.calculator.base.converter.MassUnit;
import mathaid.calculator.base.converter.PowerUnit;
import mathaid.calculator.base.converter.PressureUnit;
import mathaid.calculator.base.converter.SpeedUnit;
import mathaid.calculator.base.converter.TemperatureUnit;
import mathaid.calculator.base.converter.TemporalUnit;
import mathaid.calculator.base.converter.TorqueUnit;
import mathaid.calculator.base.converter.VolumeUnit;
import mathaid.calculator.base.util.Utility;
import mathaid.calculator.base.value.BitLength;
import mathaid.calculator.base.value.FloatPoint;
import mathaid.calculator.base.value.IntValue;
import mathaid.calculator.base.value.Precision;
import mathaid.calculator.base.value.Radix;
import mathaid.designpattern.Observer;

/*
 * Date: 9 Apr 2021----------------------------------------------------------- 
 * Time created: 17:11:59---------------------------------------------------  
 * Package: mathaid.calculator.base------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: DetailsList.java------------------------------------------------------ 
 * Class name: DetailsList------------------------------------------------ 
 */
/**
 * An abstract class that provides further info about a {@code CEvaluator}
 * within the {@code Calculator} class. It contains 3 classes that each
 * represent 1 of the 3 modes of the {@code Calculator} object. The single
 * method {@link #getLastDetails()} returns a {@code Map} that contains the
 * appropriate information as values and the heading to be displayed in the
 * details part of the calculator as the key.
 * <p>
 * All {@code DetailsList} objects are elements of {@code CEvaluator} objects
 * within the calculator hence they implement the {@code Observer} interface so
 * as to be registered into a {@code CEvaluator} object using
 * {@link mathaid.designpattern.Subject#register(Observer)}. When they have been
 * added as stated previously, then they can be notified of results from
 * calculations. Infact, their {@link #inform(DataText)} method is called by the
 * evaluator to update the output value. Once this is done, it triggers an
 * evaluating mechanism waiting to compute a set of results that will be
 * displayed in the "details" part of the calculator. A valid
 * {@code DetailsList} object can be can be retrieved via
 * {@link mathaid.calculator.base.parser.CEvaluator#getDetails}.
 * </p>
 * <p>
 * A {@code DetailsList} should not throw any exception because it is not
 * directly controlled by the user, but when an exception is encountered, it may
 * abort the computation and instead display an error message in the index the
 * information was supposed to be.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public abstract class DetailsList implements Observer<DataText> {

	/*
	 * Date: 10 Aug 2021-----------------------------------------------------------
	 * Time created: 13:52:23---------------------------------------------------
	 * Package: mathaid.calculator.base--------------------------------------------
	 * Project: LatestPoject2------------------------------------------------------
	 * File: DetailsList.java------------------------------------------------------
	 * Class name: SciDetailsList------------------------------------------------
	 * TODO: Add Roman figures for integers----------------------------------------
	 * TODO: Add constants for real values such when the result is exactly Pi.-----
	 * TODO: Graphs and diagrams coming soon.
	 */
	/**
	 * A {@code DetailsList} that can be updated with numerical and symbolic results
	 * from a {@code CEvaluator} object specifically the {@code Scientific} class.
	 * <p>
	 * The actual details is gotten via {@link #getLastDetails} and it contents may
	 * change depending on the type of input received. Here are some of the details:
	 * <table border="1">
	 * <tr>
	 * <th>Data Type</th>
	 * <th>Valid string key</th>
	 * <th>Description of value</th>
	 * </tr>
	 * <tr>
	 * <td rowspan="2" align="center">Integer</td>
	 * <td>Expression</td>
	 * <td>A TeX pretty-print of the integer expression.</td>
	 * </tr>
	 * <tr>
	 * <td>Prime factors</td>
	 * <td>The factors a the integer. If the integer is too large this key-value
	 * pair is omitted.</td>
	 * </tr>
	 * <tr>
	 * <td rowspan="8" align="center">Common fraction</td>
	 * <td>Expression</td>
	 * <td>A TeX pretty-print of the common fraction expression in non-mixed
	 * form.</td>
	 * </tr>
	 * <tr>
	 * <td>Lowest term</td>
	 * <td>The TeX pretty-print of the reduced form of the given common
	 * fraction.</td>
	 * </tr>
	 * <tr>
	 * <td>Mixed fraction</td>
	 * <td>The TeX pretty-print of the common faction in mixed form.</td>
	 * </tr>
	 * <tr>
	 * <td>Continued fraction</td>
	 * <td>The TeX pretty-print of the continued fraction (elements thereof)
	 * representation of the given common fraction.</td>
	 * <tr>
	 * <td>Egyptian fraction</td>
	 * <td>The TeX pretty-print of the Egyptian fraction representation of the
	 * result.</td>
	 * </tr>
	 * <tr>
	 * <td>Decimal</td>
	 * <td>The TeX pretty-print of the decimal representation of the result.</td>
	 * </tr>
	 * <tr>
	 * <td>Period</td>
	 * <td>The TeX pretty-print of the number of digits in the recurring part of the
	 * decimal (if there is one).</td>
	 * </tr>
	 * <tr>
	 * <td>Prime factors</td>
	 * <td>The TeX pretty-print of the prime factors of the common fraction.</td>
	 * </tr>
	 * <tr>
	 * <td rowspan="4" align="center">Real</td>
	 * <td>Expression</td>
	 * <td>The TeX pretty-print of the real value.
	 * </tr>
	 * <tr>
	 * <td>Scientific notation</td>
	 * <td>The TeX pretty-print of the real value in scientific notation.</td>
	 * </tr>
	 * <tr>
	 * <td>Engineering notation</td>
	 * <td>The TeX pretty-print of the real value in engineering notation.</td>
	 * </tr>
	 * <tr>
	 * <td>Engineering SI</td>
	 * <td>The TeX pretty-print of the real value in engineering notation including
	 * any appropriate SI suffix.</td>
	 * </tr>
	 * <tr>
	 * <td rowspan="8" align="center">Complex</td>
	 * <td>Expression</td>
	 * <td>The TeX pretty-print of the complex result.</td>
	 * </tr>
	 * <tr>
	 * <td>Real</td>
	 * <td>The TeX pretty-print of the real part of the complex result.</td>
	 * </tr>
	 * <tr>
	 * <td>Imaginary</td>
	 * <td>The TeX pretty-print of the imaginary part of the complex result.</td>
	 * </tr>
	 * <tr>
	 * <td>Argument</td>
	 * <td>The TeX pretty-print of the
	 * {@link mathaid.calculator.base.value.Complex#theta() argument} of the complex
	 * result.</td>
	 * </tr>
	 * <tr>
	 * <td>Magnitude</td>
	 * <td>The pretty-print of the
	 * {@link mathaid.calculator.base.value.Complex#radius() magnitude} of the
	 * complex result.</td>
	 * </tr>
	 * <tr>
	 * <td>X coordinate</td>
	 * <td>The TeX pretty-print of the x coordinate of the
	 * {@link mathaid.calculator.base.value.Complex#toRectangularCoordinates
	 * rectangular coordinates} of the complex result.</td>
	 * </tr>
	 * <tr>
	 * <td>Y coordinate</td>
	 * <td>The TeX pretty-print of the x coordinate of the
	 * {@link mathaid.calculator.base.value.Complex#toRectangularCoordinates
	 * rectangular coordinates} of the complex result.</td>
	 * </tr>
	 * <tr>
	 * <td>As common fraction</td>
	 * <td>The TeX pretty-print of the real and imaginary part as a common
	 * fraction.</td>
	 * </tr>
	 * <tr>
	 * <td rowspan="2" align="center">Symbolic variable</td>
	 * <td>Expression</td>
	 * <td>The TeX pretty-print of the variable.</td>
	 * </tr>
	 * <tr>
	 * <td>Value</td>
	 * <td>The TeX pretty-print of the value assigned to the bound variable result
	 * (for bound variables only).</td>
	 * </tr>
	 * <tr>
	 * <td rowspan="17" align="center">Non atomic expression</td>
	 * <td>Expression</td>
	 * <td>The TeX pretty-print of the expression result.
	 * </tr>
	 * <tr>
	 * <td>Distribute</td>
	 * <td>The TeX pretty-print of a parenthesised symbolic expression re-written
	 * using the distributive property of a binary operator. For example:
	 * {@code (a+b)*(x+y+z)} can be re-written as
	 * {@code a*x+a*y+a*z+b*x+b*y+b*z}</td>
	 * </tr>
	 * <tr>
	 * <td>Expand</td>
	 * <td>The TeX pretty-print of the expanded form of a symbolic expression. This
	 * does not re-write the powers and roots of the expression or complex
	 * expressions.</td>
	 * </tr>
	 * <tr>
	 * <td>ExpandAll</td>
	 * <td>The TeX pretty-print of the expanded form of a symbolic expression. This
	 * included powers and roots but not complex expressions.</td>
	 * </tr>
	 * <tr>
	 * <td>ExpandComplex</td>
	 * <td>The TeX pretty-print of the expanded complex expression. The free
	 * variable within the expression are assumed to be real values.</td>
	 * </tr>
	 * <tr>
	 * <td>Apart</td>
	 * <td>The TeX pretty-print of the expression result re-written as a sum of it's
	 * individual fractions. This specifically deals with rational polynomials.</td>
	 * </tr>
	 * <tr>
	 * <td>Apartx</td>
	 * <td>Does the same thing as the above only this time "x" is the only variable
	 * considered in an expression with multiple variables.</td>
	 * </tr>
	 * <tr>
	 * <td>Aparty</td>
	 * <td>Does the same thing as the above only this time "y" is the only variable
	 * considered in an expression with multiple variables.</td>
	 * </tr>
	 * <tr>
	 * <td>Together</td>
	 * <td>The TeX pretty-print of the expression result re-written as a sum of the
	 * factors of it's fractions.</td>
	 * </tr>
	 * <tr>
	 * <td>PowerExpand</td>
	 * <td>The TeX pretty-print of the expression result with only the powers
	 * expanded out.</td>
	 * </tr>
	 * <tr>
	 * <td>SimplifyX</td>
	 * <td>The TeX pretty-print of the expression result with all occurrences of "x"
	 * simplified (re-written) assuming "x" to be greater than 0.</td>
	 * </tr>
	 * <tr>
	 * <td>SimplifyX1</td>
	 * <td>The TeX pretty-print of the expression result with all occurrences of "x"
	 * simplified (re-written) assuming "x" to be less than 0.</td>
	 * </tr>
	 * <tr>
	 * <td>SimplifyY</td>
	 * <td>The TeX pretty-print of the expression result with all occurrences of "y"
	 * simplified (re-written) assuming "y" to be greater than 0.</td>
	 * </tr>
	 * <tr>
	 * <td>SimplifyY1</td>
	 * <td>The TeX pretty-print of the expression result with all occurrences of "y"
	 * simplified (re-written) assuming "y" to be less than 0.</td>
	 * </tr>
	 * <tr>
	 * <td>TrigExpand</td>
	 * <td>The TeX pretty-print of the expression result with all trigonometric
	 * functions expanded. For example, {@code Sin(x+y)} will be
	 * {@code Cos(x)*Sin(y)+Cos(y)*Sin(x)}.</td>
	 * </tr>
	 * <tr>
	 * <td>TrigReduce</td>
	 * <td>The TeX pretty-print of the expression result that rewrites products and
	 * powers of trigonometric functions in expression in terms of trigonometric
	 * functions with combined arguments. For example, {@code 2*Sin(x)*Cos(y)} can
	 * be re-written as {@code Sin(-y+x)+Sin(y+x)}</td>
	 * </tr>
	 * <tr>
	 * <td>TrigToExp</td>
	 * <td>The TeX pretty-print of the expression result that re-writes
	 * trigonometric function to exponentials.</td>
	 * </tr>
	 * </table>
	 * Note that some of the items in the in the above table may not
	 * be made an entry in the {@link #getLastDetails() map} if the result returned
	 * does not match that data type.
	 * </p>
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 *
	 */
	static class SciDetailsList extends DetailsList {

		/*
		 * Date: 10 Aug 2021-----------------------------------------------------------
		 * Time created: 17:17:42---------------------------------------------------
		 */
		/**
		 * Initialises a {@code SciDetailsList} using a specified engine from the
		 * matheclipse API. This is the same symja engine used for evaluating
		 * expressions in the {@code Scientific} class.
		 * 
		 * @param evalEngine the evaluating engine of the symja API.
		 */
		SciDetailsList(EvalUtilities evalEngine) {
			this.evalEngine = evalEngine;
		}

		/*
		 * Most Recent Date: 9 Apr 2021-----------------------------------------------
		 * Most recent time created: 19:06:15--------------------------------------
		 */
		/**
		 * Updates this {@code SciDetailsList}.
		 * <p>
		 * This method should be called before {@link #doAction}
		 * </p>
		 * 
		 * @param t the current value hat this object is updated with.
		 */
		@Override
		public void inform(DataText t) {
			synchronized (this) {
				result = t;
				detail.clear();
			}
		}

		/*
		 * Most Recent Date: 10 Apr 2021-----------------------------------------------
		 * Most recent time created: 11:00:04--------------------------------------0000
		 * Other re-written forms include: Together (evaluates all the fractions),
		 * Simplify, Solve (for equations), Refine[expressions, assumptions],
		 * Rationalize (replaces all decimal real numbers with their rational
		 * equivalent), PowerExpand (for powers), Expand, Diff (for differentials),
		 * Integrate (for integrals), Hold, HoldFirst (Attribute), HoldAll (attribute),
		 * HoldRest (Attribute), HoldForm, FactorTerms (Polynomial), Factor
		 * (polynomial), Eliminnate[list-of-equations, list-of-variables] (for
		 * simultaneous equations), DSolve[equation, f(var), var] (for differential
		 * equations), Distribute, Discriminant[polynomial, var], ComplexExpand ( for
		 * expressons involving complex numbers, however, all variables are assumed to
		 * be real), Apart (fractions), Trace (evaluation steps), TrigExpand (trig
		 * expressions), TrigReduce (trig expressions), TrigToExp (trig expressions),
		 * Through (for nested functions).
		 */
		/**
		 * Consumes the current output value. This may result in out dated information
		 * or even a {@code NullPointerException} if {@link #inform(DataText)} is not
		 * first called.
		 */
		@Override
		public void doAction() {
			/*
			 * Since we already know that this is a result it is either one of the
			 * following: an expression, a constant, a fraction or a decimal(which may be
			 * complex)
			 */
			String string = result.getSymjaString();
			Collator c = DataText.getCollator();
			// it has a single value as it's result
			boolean isAtomic = c.compare(evalEngine.evaluate("AtomicQ[" + string + "]").toString(), "True") == 0;
			/*
			 * There 2 evaluation methods. 1. IExpr.evaluate(EvalEnginge); this one is for
			 * numerical evaluations but is not always reliable because sometimes it returns
			 * the exact expression an alternative to this is the string - "N[expr, n]" -
			 * where expr stands for the expression to be evaluated and n stands for the
			 * number of significant figures to be displayed. 2.
			 * EvalUtilities.evaluate(String); this caters to expressions, especially if the
			 * result is expected to be an expression.
			 */
			if (isAtomic) {// meaning this expression contains just a single node
				boolean isNumber = c.compare(evalEngine.evaluate("NumberQ[" + string + "]").toString(), "True") == 0;
				boolean isSymbol = c.compare(evalEngine.evaluate("SymbolQ[" + string + "]"), "True") == 0;
				if (isNumber) {
					boolean isComplex = c.compare(evalEngine.evaluate("RealNumberQ[" + string + "]").toString(),
							"False") == 0;
					boolean isFrac = string.indexOf("Rational(") >= 0 || Utility.isFraction(string);
					boolean isInteger = c.compare(evalEngine.evaluate("IntegerQ[" + string + "]"), "True") == 0;
					if (isComplex) {
						ComplexDetailsList cdl = new ComplexDetailsList(string);
						detail.put("Expression", cdl.getExpression());
						detail.put("Real", cdl.getReal());
						detail.put("Imaginary", cdl.getImaginary());
						detail.put("Argument", cdl.getArg());
						detail.put("Magnitude", cdl.getMagnitude());
						detail.put("X coordinate", cdl.getHorizontal());
						detail.put("Y coordinate", cdl.getVertical());
						detail.put("As common fraction", cdl.getExpressionAsFraction());
					} else if (isFrac) {
						FractionalDetailsList fdl = new FractionalDetailsList(string, false);
						detail.put("Expression", fdl.getExpression());
						detail.put("Lowest term", fdl.getLowestTerm());
						detail.put("Mixed fraction", fdl.getMixedFraction());
						detail.put("Continued fraction", fdl.getContinuedFraction());
						detail.put("Egyptian fraction", fdl.getEgyptianFraction());
						detail.put("Decimal", fdl.getDecimal());
						detail.put("Period", fdl.getPeriod());
						detail.put("Prime factors", fdl.getFactors());
					} else if (isInteger) {
						IntegerDetailsList idl = new IntegerDetailsList(string);
						detail.put("Expression", idl.getExpression());
						detail.put("Prime factors", idl.getFactors());
						detail.put("Roman figure", new DataText(string, Utility.romanNumeral(Integer.parseInt(string))));
					} else {
						DecimalDetailsList ddl = new DecimalDetailsList(string);
						detail.put("Expression", ddl.getExpression());
						detail.put("Scientific notation", ddl.getSciExpression());
						detail.put("Engineering notation", ddl.getEngExpression());
						detail.put("Engineering SI", ddl.getSISuffixExpression());
					}
				} else if (isSymbol) {
					detail.put("Expression", new DataText(string, string));
					boolean hasValue = c.compare(evalEngine.evaluate("ValueQ[" + string + "]"), "True") == 0;
					if (hasValue) {
						String v = evalEngine.evaluate("N[" + string + "," + Settings.defaultSetting().getScale() + "]")
								.toString();
						detail.put("Value", new DataText(v, v));
					}
				}
				System.err.println(detail);
				return;
			}
			/*
			 * It is a non-atomic expression, we just need to rewrite it as many times as
			 * possible.
			 */
			ExpressionDetailsList edl = new ExpressionDetailsList(string);
			detail.put("Expression", edl.getExpression(evalEngine));
//			List<Integer> list = new ArrayList<>();

			if (occurence(string, '*') > 1)
//				list.add(ExpressionDetailsList.REWRITE_DISTRIBUTE);
				detail.put("Distribute", edl.rewrite(evalEngine, ExpressionDetailsList.REWRITE_DISTRIBUTE));
			if ((occurence(string, '(') > 0 && occurence(string, '^') > 0) || (occurence(string, '(') > 1))
				detail.put("Expand", edl.rewrite(evalEngine, ExpressionDetailsList.REWRITE_EXPAND));
			if ((occurence(string, '(') > 0 && occurence(string, '^') > 0) || (occurence(string, '(') > 1))
				detail.put("ExpandAll", edl.rewrite(evalEngine, ExpressionDetailsList.REWRITE_EXPAND_ALL));
			if (occurence(string, 'I') > 0 || string.contains("Complex"))
				detail.put("ExpandComplex", edl.rewrite(evalEngine, ExpressionDetailsList.REWRITE_EXPAND_COMPLEX));
			if (occurence(string, '/') > 0 && occurence(string, '(') > 1) {
				if (occurence(string, 'y') == 0 || occurence(string, 'x') == 0)
					detail.put("Apart", edl.rewrite(evalEngine, ExpressionDetailsList.REWRITE_FRACTIONS_APART));
				else {
					detail.put("Apartx", edl.rewrite(evalEngine, ExpressionDetailsList.REWRITE_FRACTIONS_APARTX));
					detail.put("Aparty", edl.rewrite(evalEngine, ExpressionDetailsList.REWRITE_FRACTIONS_APARTY));
				}
			}
			if (occurence(string, '/') > 1)
				detail.put("Together", edl.rewrite(evalEngine, ExpressionDetailsList.REWRITE_FRACTIONS_TOGETHER));
			if ((occurence(string, '*') > 0 && occurence(string, '^') > 0) || (occurence(string, '^') > 1))
				detail.put("PowerExpand", edl.rewrite(evalEngine, ExpressionDetailsList.REWRITE_EXPAND_POWER));
			if (occurence(string, 'x') > 0) {
				detail.put("SimplifyX", edl.rewrite(evalEngine, ExpressionDetailsList.REWRITE_SIMPLIFYX));
				detail.put("SimplifyX1", edl.rewrite(evalEngine, ExpressionDetailsList.REWRITE_SIMPLIFYX1));
			}
			if (occurence(string, 'y') > 0) {
				detail.put("SimplifyY", edl.rewrite(evalEngine, ExpressionDetailsList.REWRITE_SIMPLIFYY));
				detail.put("SimplifyY1", edl.rewrite(evalEngine, ExpressionDetailsList.REWRITE_SIMPLIFYY1));
			}
			if (contains(string, trigFunctions())) {
				detail.put("TrigExpand", edl.rewrite(evalEngine, ExpressionDetailsList.REWRITE_TRIG_EXPAND));
				detail.put("TrigToExp", edl.rewrite(evalEngine, ExpressionDetailsList.REWRITE_TRIG_TO_EXPONENT));
			}
			if (containsOccurence(string, trigFunctions()) > 1 && string.contains("*"))
				detail.put("TrigReduce", edl.rewrite(evalEngine, ExpressionDetailsList.REWRITE_TRIG_REDUCE));
//			System.err.println(detail);

		}

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
			return Arrays.asList("Sin", "Cos", "Tan", "Csc", "Cot", "Sec", "Sinh", "Cosh", "Tanh", "Csch", "Coth",
					"Sech", "ArcSin", "ArcCos", "ArcTan", "ArcCsc", "ArcCot", "ArcSec", "ArcSinh", "ArcCosh", "ArcTanh",
					"ArcCsch", "ArcCoth", "ArcSech");
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

//		private DataText result;
		/**
		 * symja evaluation engine for internal symbolic evaluation.
		 */
		private EvalUtilities evalEngine;

	}

	/*
	 * Date: 10 Aug 2021-----------------------------------------------------------
	 * Time created: 20:55:47---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * DetailsList.java------------------------------------------------------ Class
	 * name: ProgDetailsList------------------------------------------------
	 */
	/**
	 * A {@code DetailsList} that can be updated with numeric result only, but these
	 * result can be in base 2, 8, 10 or 16. They can also have varied bit lengths
	 * such as 4, 8, 16, 32, 64, 128 and 256. This object receives updates from the
	 * {@code Programmer} within the {@code Calculator} class.
	 * <p>
	 * The following is a table of values returned by {@link #getLastDetails()}:
	 * <table border="1">
	 * <tr>
	 * <th>Data type</th>
	 * <th>Valid string key</th>
	 * <th>Description of value</th>
	 * </tr>
	 * <tr>
	 * <td rowspan="15" align="center">Floating-point</td>
	 * <td>Decimal</td>
	 * <td>A TeX pretty-print of the result in plain decimal notation.</td>
	 * </tr>
	 * <tr>
	 * <td>Hexadecimal</td>
	 * <td>A TeX pretty-print of the result in plain hexadecimal notation.</td>
	 * </tr>
	 * <tr>
	 * <td>Octal</td>
	 * <td>A TeX pretty-print of the result in plain base 8.</td>
	 * </tr>
	 * <tr>
	 * <td>Binary</td>
	 * <td>A TeX pretty-print of the result in plain binary form.</td>
	 * </tr>
	 * <tr>
	 * <td>Normalised decimal</td>
	 * <td>A TeX pretty-print of the result in decimal floating-point notation as
	 * specified {@link Double#toHexString}.</td>
	 * </tr>
	 * <tr>
	 * <td>Normalised hexadecimal</td>
	 * <td>A TeX pretty-print of the result in hex floating-point notation as
	 * specified {@link Double#toHexString}.</td>
	 * </tr>
	 * <tr>
	 * <td>Normalised octal</td>
	 * <td>A TeX pretty-print of the result in base 8 floating-point notation as
	 * specified {@link Double#toHexString}.</td>
	 * </tr>
	 * <tr>
	 * <td>Normalised binary</td>
	 * <td>A TeX pretty-print of the result in binary floating-point notation as
	 * specified {@link Double#toHexString}. This is not the bit layout.</td>
	 * </tr>
	 * <tr>
	 * <td>Precision</td>
	 * <td>A numerical result specifying the precision of the result.</td>
	 * </tr>
	 * <tr>
	 * <td>Number of significand bits in precision</td>
	 * <td>The number of bits in the mantissa portion of the bit layout as per the
	 * current precision.</td>
	 * </tr>
	 * <tr>
	 * <td>Significand (2's complement)</td>
	 * <td>The significand bit layout in two's complement form as an integer.</td>
	 * </tr>
	 * <tr>
	 * <td>Number of exponent bits in precision</td>
	 * <td>The number of bits in the mantissa portion of the bit layout as per the
	 * current precision.</td>
	 * </tr>
	 * <tr>
	 * <td>Exponent (unbiased)</td>
	 * <td>The bit layout of unbiased the exponent.</td>
	 * </tr>
	 * <tr>
	 * <td>Exponent</td>
	 * <td>The bit layout of the exponent.</td>
	 * </tr>
	 * <tr>
	 * <td>Bits</td>
	 * <td>The bit layout of the result's significand in integer form.</td>
	 * </tr>
	 * <tr>
	 * <td rowspan="5" align="center">Integer</td>
	 * <td>Decimal</td>
	 * <td>A TeX representation of the result in base 10.</td>
	 * </tr>
	 * <tr>
	 * <td>Hexadecimal</td>
	 * <td>A TeX representation of the result in base 16.</td>
	 * </tr>
	 * <tr>
	 * <td>Octal</td>
	 * <td>A TeX representation of the result in base 8.</td>
	 * </tr>
	 * <tr>
	 * <td>Binary</td>
	 * <td>A TeX representation of the result in base 2.</td>
	 * </tr>
	 * <tr>
	 * <td>Floating point value</td>
	 * <td>A TeX representation of the integer result as a floating-point
	 * value.</td>
	 * </tr>
	 * </table>
	 * </p>
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 *
	 */
	static class ProgDetailsList extends DetailsList {

		/*
		 * Most Recent Date: 19 Jun 2021-----------------------------------------------
		 * Most recent time created: 17:49:30--------------------------------------
		 */
		/**
		 * Updates this {@code ProgDetailsList}.
		 * <p>
		 * This method should be called before {@link #doAction}
		 * </p>
		 * 
		 * @param t the current value hat this object is updated with.
		 */
		@Override
		public void inform(DataText t) {
			this.result = t;
			detail.clear();
		}

		/*
		 * Most Recent Date: 10 Aug 2021-----------------------------------------------
		 * Most recent time created: 20:56:04--------------------------------------
		 */
		/**
		 * Consumes the current output value. This may result in out dated information
		 * or even a {@code NullPointerException} if {@link #inform(DataText)} is not
		 * first called.
		 */
		@Override
		public void doAction() {
			Settings s = Settings.defaultSetting();
			if (s.getRep() == Settings.REP_FLOAT_POINT) {
				Precision p = Precision.valueOf(s.getBitLength());
				FloatPoint fp = FloatPoint.toFloat(result.getSymjaString(), BitLength.valueOf(s.getBitLength()),
						getRadix(s.getRadix()), p.checkNormalised(result.getSymjaString()));
				FloatingPointDetailsList fpdl = new FloatingPointDetailsList(fp);
				detail.put("Decimal", fpdl.getExpression(10));
				detail.put("Hexadecimal", fpdl.getExpression(16));
				detail.put("Octal", fpdl.getExpression(8));
				detail.put("Binary", fpdl.getExpression(2));
				detail.put("Normalised decimal", fpdl.getNormalised(10));
				detail.put("Normalised hexadecimal", fpdl.getNormalised(16));
				detail.put("Normalised octal", fpdl.getNormalised(8));
				detail.put("Normalised binary", fpdl.getNormalised(2));
				detail.put("Precision", fpdl.getPrecision());
				detail.put("Number of significand bits in precision", fpdl.getSignificandBitsForCurrentPrecision());
				detail.put("Significand (2's complement)", fpdl.getSignificand());
				detail.put("Number of exponent bits in precision", fpdl.getExponentBitsForCurrentPrecision());
				detail.put("Exponent (Unbiased)", fpdl.getExponent());
				detail.put("Exponent ", fpdl.getBiasedExponent());
				detail.put("Bits", fpdl.getFullBits());
				return;
			}
			IntegerDetailsList idl = new IntegerDetailsList(result.getSymjaString());
			detail.put("Decimal", idl.getExpression(Radix.DEC, false));
			detail.put("Hexadecimal", idl.getExpression(Radix.HEX, false));
			detail.put("Octal", idl.getExpression(Radix.OCT, false));
			detail.put("Binary", idl.getExpression(Radix.BIN, false));
			detail.put("Floating point value", idl.getAsFloatingPoint());
		}

		/*
		 * Date: 10 Aug 2021-----------------------------------------------------------
		 * Time created: 20:56:09--------------------------------------------
		 */
		/**
		 * Returns a {@code Radix} object appropriate for the given argument which is
		 * specified by the int value for that base.
		 * 
		 * @param r one of the values given by the constants 2, 8, 10, 16.
		 * @return a {@code Radix} object.
		 */
		private static IntValue.Radix getRadix(int r) {
			switch (r) {
			case 2:
				return IntValue.Radix.BIN;
			case 8:
				return IntValue.Radix.OCT;
			case 16:
				return IntValue.Radix.HEX;
			default:
				return IntValue.Radix.DEC;
			}
		}

	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 09:46:12---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * DetailsList.java------------------------------------------------------ Class
	 * name: ConverterDetailsList------------------------------------------------
	 */
	/**
	 * A {@code DetailsList} that can be updated with numeric values and can return
	 * a {@code Map} object that has strings (representing the name of the unit
	 * converted) and a {@code DataText} object (representing the value converted
	 * given the {@link Settings#setCurrentConverterFrom current converter from the
	 * conversion is being made}) in a key-value pair.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 *
	 */
	static class ConverterDetailsList extends DetailsList {

		/*
		 * Most Recent Date: 17 Jul 2021-----------------------------------------------
		 * Most recent time created: 22:08:50--------------------------------------
		 */
		/**
		 * Updates this {@code ConverterDetailsList}.
		 * <p>
		 * This method should be called before {@link #doAction}
		 * </p>
		 * 
		 * @param t the current value hat this object is updated with.
		 */
		@Override
		public void inform(DataText t) {
			this.result = t;
			detail.clear();
		}

		/*
		 * Most Recent Date: 11 Aug 2021-----------------------------------------------
		 * Most recent time created: 09:48:08--------------------------------------
		 */
		/**
		 * Consumes the current output value. This may result in out dated information
		 * or even a {@code NullPointerException} if {@link #inform(DataText)} is not
		 * first called.
		 */
		@Override
		public void doAction() {
			BigDecimal x = new BigDecimal(result.getSymjaString());
			Settings s = Settings.defaultSetting();
			MathContext mc = new MathContext(s.getScale(), RoundingMode.HALF_EVEN);
			switch (s.getCurrentConverter()) {
			case 0:
				AngleUnit[] aus = AngleUnit.values();
				AngleUnit af = aus[s.getCurrentConverterTo()];
				AngleUnit af2 = aus[s.getCurrentConverterFrom()];
				for (AngleUnit to : aus) {
					if (to.compareTo(af) != 0 && to.compareTo(af2) != 0) {
						DataText value = new DecimalDetailsList(af2.convert(x, to, mc).toString()).getExpression();
						detail.put(to.toString().toLowerCase(), value);
					}
				}
				break;
			case 1:
				AreaUnit[] au = AreaUnit.values();
				AreaUnit a = au[s.getCurrentConverterTo()];
				AreaUnit a2 = au[s.getCurrentConverterFrom()];
				for (AreaUnit to : au) {
					if (to.compareTo(a) != 0 && to.compareTo(a2) != 0) {
						DataText value = new DecimalDetailsList(a2.convert(x, to, mc).toString()).getExpression();
						detail.put(to.toString().toLowerCase(), value);
					}
				}
				break;
			case 2:
				List<MediumOfExchange> l = Currencies.CURRENCIES;
				CurrencyUnit c = CurrencyUnit.valueOf(l.get(s.getCurrentConverterTo()).getNumericCode());
				CurrencyUnit c2 = CurrencyUnit.valueOf(l.get(s.getCurrentConverterFrom()).getNumericCode());
				for (MediumOfExchange to : l) {
					if (to.getNumericCode() != c.getNumericCode() && to.getNumericCode() != c2.getNumericCode()) {
						DataText value = new DecimalDetailsList(
								c2.convert(x, CurrencyUnit.valueOf(to.getNumericCode()), mc).toString())
										.getExpression();
						detail.put(to.toString().toLowerCase(), value);
					}
				}
				break;
			case 3:
				DistanceUnit[] du = DistanceUnit.values();
				DistanceUnit d = du[s.getCurrentConverterTo()];
				DistanceUnit d2 = du[s.getCurrentConverterFrom()];
				for (DistanceUnit to : du) {
					if (to.compareTo(d) != 0 && to.compareTo(d2) != 0) {
						DataText value = new DecimalDetailsList(d2.convert(x, to, mc).toString()).getExpression();
						detail.put(to.toString().toLowerCase(), value);
					}
				}
				break;
			case 4:
				DataUnit[] dau = DataUnit.values();
				DataUnit da = dau[s.getCurrentConverterTo()];
				DataUnit da2 = dau[s.getCurrentConverterFrom()];
				for (DataUnit to : dau) {
					if (to.compareTo(da) != 0 && to.compareTo(da2) != 0) {
						DataText value = new DecimalDetailsList(da2.convert(x, to, mc).toString()).getExpression();
						detail.put(to.toString().toLowerCase(), value);
					}
				}
				break;
			case 5:
				EnergyUnit[] eu = EnergyUnit.values();
				EnergyUnit e = eu[s.getCurrentConverterTo()];
				EnergyUnit e2 = eu[s.getCurrentConverterFrom()];
				for (EnergyUnit to : eu) {
					if (to.compareTo(e) != 0 && to.compareTo(e2) != 0) {
						DataText value = new DecimalDetailsList(e2.convert(x, to, mc).toString()).getExpression();
						detail.put(to.toString().toLowerCase(), value);
					}
				}
				break;
			case 6:
				ForceUnit[] fu = ForceUnit.values();
				ForceUnit f = fu[s.getCurrentConverterTo()];
				ForceUnit f2 = fu[s.getCurrentConverterFrom()];
				for (ForceUnit to : fu) {
					if (to.compareTo(f) != 0 && to.compareTo(f2) != 0) {
						DataText value = new DecimalDetailsList(f2.convert(x, to, mc).toString()).getExpression();
						detail.put(to.toString().toLowerCase(), value);
					}
				}
				break;
			case 7:
				FrequencyUnit[] frq = FrequencyUnit.values();
				FrequencyUnit fr = frq[s.getCurrentConverterTo()];
				FrequencyUnit fr2 = frq[s.getCurrentConverterFrom()];
				for (FrequencyUnit to : frq) {
					if (to.compareTo(fr) != 0 && to.compareTo(fr2) != 0) {
						DataText value = new DecimalDetailsList(fr2.convert(x, to, mc).toString()).getExpression();
						detail.put(to.toString().toLowerCase(), value);
					}
				}
				break;
			case 8:
				FuelConsumptionUnit[] fcu = FuelConsumptionUnit.values();
				FuelConsumptionUnit fc = fcu[s.getCurrentConverterTo()];
				FuelConsumptionUnit fc2 = fcu[s.getCurrentConverterFrom()];
				for (FuelConsumptionUnit to : fcu) {
					if (to.compareTo(fc) != 0 && to.compareTo(fc2) != 0) {
						DataText value = new DecimalDetailsList(fc2.convert(x, to, mc).toString()).getExpression();
						detail.put(to.toString().toLowerCase(), value);
					}
				}
				break;
			case 9:
				MassUnit[] mu = MassUnit.values();
				MassUnit m = mu[s.getCurrentConverterTo()];
				MassUnit m2 = mu[s.getCurrentConverterFrom()];
				for (MassUnit to : mu) {
					if (to.compareTo(m) != 0 && to.compareTo(m2) != 0) {
						DataText value = new DecimalDetailsList(m2.convert(x, to, mc).toString()).getExpression();
						detail.put(to.toString().toLowerCase(), value);
					}
				}
				break;
			case 10:
				PowerUnit[] pu = PowerUnit.values();
				PowerUnit p = pu[s.getCurrentConverterTo()];
				PowerUnit p2 = pu[s.getCurrentConverterFrom()];
				for (PowerUnit to : pu) {
					if (to.compareTo(p) != 0 && to.compareTo(p2) != 0) {
						DataText value = new DecimalDetailsList(p2.convert(x, to, mc).toString()).getExpression();
						detail.put(to.toString().toLowerCase(), value);
					}
				}
				break;
			case 11:
				PressureUnit[] pru = PressureUnit.values();
				PressureUnit pr = pru[s.getCurrentConverterTo()];
				PressureUnit pr2 = pru[s.getCurrentConverterFrom()];
				for (PressureUnit to : pru) {
					if (to.compareTo(pr) != 0 && to.compareTo(pr2) != 0) {
						DataText value = new DecimalDetailsList(pr2.convert(x, to, mc).toString()).getExpression();
						detail.put(to.toString().toLowerCase(), value);
					}
				}
				break;
			case 12:
				SpeedUnit[] su = SpeedUnit.values();
				SpeedUnit ss = su[s.getCurrentConverterTo()];
				SpeedUnit ss2 = su[s.getCurrentConverterFrom()];
				for (SpeedUnit to : su) {
					if (to.compareTo(ss) != 0 && to.compareTo(ss2) != 0) {
						DataText value = new DecimalDetailsList(ss2.convert(x, to, mc).toString()).getExpression();
						detail.put(to.toString().toLowerCase(), value);
					}
				}
				break;
			case 13:
				TemperatureUnit[] tu = TemperatureUnit.values();
				TemperatureUnit t = tu[s.getCurrentConverterTo()];
				TemperatureUnit t2 = tu[s.getCurrentConverterFrom()];
				for (TemperatureUnit to : tu) {
					if (to.compareTo(t) != 0 && to.compareTo(t2) != 0) {
						DataText value = new DecimalDetailsList(t2.convert(x, to, mc).toString()).getExpression();
						detail.put(to.toString().toLowerCase(), value);
					}
				}
				break;
			case 14:
				TemporalUnit[] teu = TemporalUnit.values();
				TemporalUnit tt = teu[s.getCurrentConverterTo()];
				TemporalUnit tt2 = teu[s.getCurrentConverterFrom()];
				for (TemporalUnit to : teu) {
					if (to.compareTo(tt) != 0 && to.compareTo(tt2) != 0) {
						DataText value = new DecimalDetailsList(tt2.convert(x, to, mc).toString()).getExpression();
						detail.put(to.toString().toLowerCase(), value);
					}
				}
				break;
			case 15:
				TorqueUnit[] tou = TorqueUnit.values();
				TorqueUnit tor = tou[s.getCurrentConverterTo()];
				TorqueUnit tor2 = tou[s.getCurrentConverterFrom()];
				for (TorqueUnit to : tou) {
					if (to.compareTo(tor) != 0 && to.compareTo(tor2) != 0) {
						DataText value = new DecimalDetailsList(tor2.convert(x, to, mc).toString()).getExpression();
						detail.put(to.toString().toLowerCase(), value);
					}
				}
				break;
			case 16:
				VolumeUnit[] vu = VolumeUnit.values();
				VolumeUnit v = vu[s.getCurrentConverterTo()];
				VolumeUnit v2 = vu[s.getCurrentConverterFrom()];
				for (VolumeUnit to : vu) {
					if (to.compareTo(v) != 0 && to.compareTo(v2) != 0) {
						DataText value = new DecimalDetailsList(v2.convert(x, to, mc).toString()).getExpression();
						detail.put(to.toString().toLowerCase(), value);
					}
				}
				break;
			default:
			}
		}

	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 09:49:26--------------------------------------------
	 */
	/**
	 * Returns an unmodifiable {@code Map} of the details. This map may be empty if
	 * a users tries to retrieves before {@link #doAction()} is called.
	 * <p>
	 * The contents of the map for {@code DetailsList} objects is dependent on the
	 * internal implementation, but the general concept is that the keys are strings
	 * that subtitles the value and the values are further calculations made on
	 * updated result appropriate to a certain context. For example: If
	 * <code>200</code> is a result updated to this class, then this map may contain
	 * a key-value pair such as "Prime factors" and then TeX notation of the factors
	 * of {@code 200} repectively.
	 * </p>
	 * 
	 * @return an unmodifiable {@code Map} containing further calculations upon the
	 *         given updated result or an empty map if no update was received.
	 */
	public Map<String, DataText> getLastDetails() {
		return detail.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(detail);
	}

	/**
	 * The map object that is populated when {@link #doAction()} is called.
	 */
	final Map<String, DataText> detail = new LinkedHashMap<>();
	/**
	 * The value that all {@code DetailsList} receives update for.
	 */
	DataText result;

}
