/**
 * 
 */
package mathaid.calculator.base;

import java.util.HashSet;
import java.util.Set;

import org.matheclipse.core.eval.EvalUtilities;
import org.matheclipse.core.interfaces.IExpr;

/*
 * Date: 20 Apr 2021----------------------------------------------------------- 
 * Time created: 09:07:21---------------------------------------------------  
 * Package: mathaid.calculator.base------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: ExpressionDetailsList.java------------------------------------------------------ 
 * Class name: ExpressionDetailsList------------------------------------------------ 
 * TODO: Add graphing commands
 */
/**
 * A {@code DetailsList} helper class. It can also be used to retrieve the TeX
 * notation of an expression, but users should refrain from doing that in isolation because
 * values returned by methods are dependent upon the current state of the
 * {@code Settings} object.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
class ExpressionDetailsList {

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
	 * Date: 20 Apr 2021-----------------------------------------------------------
	 * Time created: 09:08:03---------------------------------------------------
	 */
	/**
	 * Initialises this object with the symbolic string expression.
	 * 
	 * @param symja the expression parsable by the symja evaluation engine to be
	 *              used.
	 */
	public ExpressionDetailsList(String symja) {
		this.symja = symja;
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 11:42:49--------------------------------------------
	 */
	/**
	 * Returns a {@code DataText} object representing the TeX notation of the string
	 * argument in the constructor.
	 * 
	 * @param e the symja engine to use for converting the expression to TeX.
	 * @return a {@code DataTex} object containing TeX and symja text.
	 */
	public DataText getExpression(EvalUtilities e) {
		String teX = e.evaluate("TeXForm[" + symja + "]").toString();
		return new DataText(teX, symja);
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 11:45:21--------------------------------------------
	 */
	/**
	 * Rewrites the expression given by this object using the constants of this
	 * class as valid arguments and returns a {@code DataText} object representing
	 * the TeX notation.
	 * 
	 * @param e    the symja engine to use for converting the expression to TeX.
	 * @param args varargs whereby any of the constants given by this class is a
	 *             valid argument.
	 * @return a {@code DataTex} object which corresponds to the expression being
	 *         rewritten with the given varargs.
	 */
	public DataText rewrite(EvalUtilities e, int... args) {
		Set<Integer> listOfArgs = new HashSet<>(args.length);
		for (Integer i : args)
			listOfArgs.add(i);
		IExpr result = e.evaluate(symja);

		if (listOfArgs.contains(REWRITE_DISTRIBUTE))
			result = e.evaluate("Distribute[" + symja + "]");
		if (listOfArgs.contains(REWRITE_EXPAND))
			result = e.evaluate("Expand[" + symja + "]");
		if (listOfArgs.contains(REWRITE_EXPAND_ALL))
			result = e.evaluate("ExpandAll[" + symja + "]");
		if (listOfArgs.contains(REWRITE_EXPAND_COMPLEX))
			result = e.evaluate("ComplexExpand[" + symja + "]");
		if (listOfArgs.contains(REWRITE_FRACTIONS_APART))
			result = e.evaluate("Apart[" + symja + "]");
		if (listOfArgs.contains(REWRITE_FRACTIONS_APARTX))
			result = e.evaluate("Apart[" + symja + ",x]");
		if (listOfArgs.contains(REWRITE_FRACTIONS_APARTY))
			result = e.evaluate("Apart[" + symja + ",y]");
		if (listOfArgs.contains(REWRITE_FRACTIONS_TOGETHER))
			result = e.evaluate("Together[" + symja + "]");
		if (listOfArgs.contains(REWRITE_HOLD))
			result = e.evaluate("Hold[" + symja + "]");
//		if (listOfArgs.contains(REWRITE_HOLD_ALL))
//			result = e.evaluate("Hold[" + symja + "]");
		if (listOfArgs.contains(REWRITE_HOLDFORM))
			result = e.evaluate("HoldForm[" + symja + "]");
//		if (listOfArgs.contains(REWRITE_HOLD_FIRST))
//			result = e.evaluate("HoldFirst[" + symja + "]");
//		if (listOfArgs.contains(REWRITE_HOLD_REST))
//			result = e.evaluate("HoldRest[" + symja + "]");
		if (listOfArgs.contains(REWRITE_EXPAND_POWER))
			result = e.evaluate("PowerExpand[" + symja + "]");
		if (listOfArgs.contains(REWRITE_SIMPLIFY))
			result = e.evaluate("Simplify[" + symja + "]");
		if (listOfArgs.contains(REWRITE_SIMPLIFYX))
			result = e.evaluate("Simplify[" + symja + ", Assumptions -> x>0]");
		if (listOfArgs.contains(REWRITE_SIMPLIFYY))
			result = e.evaluate("Simplify[" + symja + ", Assumptions -> y>0]");
		if (listOfArgs.contains(REWRITE_SIMPLIFYX1))
			result = e.evaluate("Simplify[" + symja + ", Assumptions -> x<0]");
		if (listOfArgs.contains(REWRITE_SIMPLIFYY1))
			result = e.evaluate("Simplify[" + symja + ", Assumptions -> y<0]");
		if (listOfArgs.contains(REWRITE_TRACE))
			result = e.evaluate("Trace[" + symja + "]");
		if (listOfArgs.contains(REWRITE_THROUGH_FUNCTIONS))
			result = e.evaluate("Through[" + symja + "]");
		if (listOfArgs.contains(REWRITE_TRIG_EXPAND))
			result = e.evaluate("TrigExpand[" + symja + "]");
		if (listOfArgs.contains(REWRITE_TRIG_REDUCE))
			result = e.evaluate("TrigReduce[" + symja + "]");
		if (listOfArgs.contains(REWRITE_TRIG_TO_EXPONENT))
			result = e.evaluate("TrigToExp[" + symja + "]");

		DataText dt = new DataText(e.evaluate("TeXForm[" + result.toString() + "]").toString(), result.toString());
		return dt;
//		return new ExpressionDetailsList(result.toString()).getExpression(e);
	}

	/**
	 * The expression as a string in symja notation.
	 */
	final private String symja;
}
