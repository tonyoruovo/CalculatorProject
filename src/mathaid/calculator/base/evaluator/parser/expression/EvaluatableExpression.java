/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression;

import java.util.Map;

import mathaid.calculator.base.converter.AngleUnit;
import mathaid.calculator.base.evaluator.Evaluatable;
import mathaid.calculator.base.typeset.DigitPunc;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.util.Tuple.Couple;
import mathaid.functional.Supplier.Function;

/*
 * Date: 4 Sep 2022----------------------------------------------------------- 
 * Time created: 08:14:07---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: EvaluatableExpression.java------------------------------------------------------ 
 * Class name: EvaluatableExpression------------------------------------------------ 
 */
/**
 * A base class for all <code>EvaluatableExpression</code> that have name(s).
 * <p>
 * An {@code Expression} that implements {@code Evaluatable} and format expressions using a
 * {@link mathaid.calculator.base.typeset.SegmentBuilder}.
 * <p>
 * This object has a symbolic value (specified by {@link #getName()}) and an options (specified by {@link #getParams()}).
 * 
 * @param <P> the type of {@code ExpressionParams} for this expression
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public abstract class EvaluatableExpression<P extends EvaluatableExpression.ExpressionParams<P>>
		implements Expression<SegmentBuilder>, Evaluatable<EvaluatableExpression<P>> {

	/*
	 * Date: 5 Sep 2022-----------------------------------------------------------
	 * Time created: 02:49:32---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base.evaluator.parser.expression.scientific---------------
	 * --------------------------------- Project:
	 * CalculatorProject------------------------------------------------ File:
	 * ExpressionParams.java------------------------------------------------------
	 * Class name: ExpressionParams------------------------------------------------
	 */
	/**
	 * An interface for getters of a number of options for this expression. This is used for proper parsing and formats.
	 * <p>
	 * It represents parameters used for constructing, maintaining and formatting expressions.
	 * <p>
	 * Certain options are needed for expressions such as formatting options like the result type and radix, expression
	 * reconstruction options such as the precision of it's significant digits and the trigonometry to be used etc.
	 * 
	 * @param <T> A sub-type (such as a concrete implementation) used as the argument for keys to the map gotten through
	 *            {@link #getBoundVariables()} and {@link #getConstants()}
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 */
	public static interface ExpressionParams<T extends ExpressionParams<T>> {

		/*
		 * Date: 28 Nov 2023 -----------------------------------------------------------
		 * Time created: 21:10:42 ---------------------------------------------------
		 * Package: mathaid.calculator.base.evaluator.parser.expression ------------------------------------------------
		 * Project: CalculatorProject ------------------------------------------------
		 * File: EvaluatableExpression.java ------------------------------------------------------
		 * Class name: ResultType ------------------------------------------------
		 */
		/**
		 * Interface that provides constants for results type to help with references.
		 * <p>
		 * This very interface has one for decimal results.
		 * 
		 * @author Oruovo Anthony Etineakpopha
		 * @email tonyoruovo@gmail.com
		 */
		interface ResultType {
			/**
			 * Constant representing decimal results.
			 */
			int DECIMAL = 0;
		}

		/*
		 * Date: 28 Nov 2023 -----------------------------------------------------------
		 * Time created: 21:12:32 ---------------------------------------------------
		 */
		/**
		 * Getter for the result type configured for {@code this}.
		 * <p>
		 * All values returned are one of the constants of the {@link ResultType} interface (or one of it's sub-interfaces).
		 * 
		 * @return the result type.
		 */
		int getResultType();

		/*
		 * Date: 28 Nov 2023 -----------------------------------------------------------
		 * Time created: 21:14:37 ---------------------------------------------------
		 */
		/**
		 * Gets the string used for constructing {@code mathaid.calculator.base.typeset.LinkedSegment} number nodes.
		 * 
		 * @return the decimal/radix point description, used for formatting numbers.
		 * @see mathaid.calculator.base.typeset.DigitPunc#getPoint()
		 */
		String getDecimalPoint();

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 04:52:26 ---------------------------------------------------
		 */
		/**
		 * Gets the string used as the group separator for digits in the integer portion when formatting expressions.
		 * 
		 * @return the string used between groups of digits in the integer portion.
		 * @see mathaid.calculator.base.typeset.DigitPunc#getIntSeparator()
		 */
		String getIntSeparator();

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 05:04:56 ---------------------------------------------------
		 */
		/**
		 * Gets the string used as the group separator for digits in the mantissa portion when formatting expressions.
		 * 
		 * @return the string used between groups of digits in the mantissa portion.
		 * @see mathaid.calculator.base.typeset.DigitPunc#getMantSeparator()
		 */
		String getMantSeparator();

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 05:09:02 ---------------------------------------------------
		 */
		/**
		 * Gets the number of digits before the integer separator is inserted during formatting.
		 * 
		 * @return the number of digits surrounding the integer separator.
		 * @see mathaid.calculator.base.typeset.DigitPunc#getIntGroupSize()
		 */
		int getIntGroupSize();

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 05:12:22 ---------------------------------------------------
		 */
		/**
		 * Gets the number of digits before the mantissa separator is inserted during formatting.
		 * 
		 * @return the number of digits surrounding the mantissa separator.
		 * @see mathaid.calculator.base.typeset.DigitPunc#getMantGroupSize()
		 */
		int getMantGroupSize();

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 05:12:57 ---------------------------------------------------
		 */
		/**
		 * Gets the default radix used for parsing numbers whose radix is unspecified. This is also the radix used during formatting.
		 * 
		 * @return the radix.
		 */
		int getRadix();

		/*
		 * Date: 10 Sep 2022-----------------------------------------------------------
		 * Time created: 16:30:10--------------------------------------------
		 * These include infinity --&gt; \\infty, pi --&gt; \\pi, e,
		 */
		/**
		 * Gets the constant as a Map. A constant is a value whose actual representation within an expression is numeric and that number
		 * does not change. e.g {@code 3e^pi} has a constant numeric result because both {@code e} and {@code pi} have a value that
		 * never changes.
		 * <p>
		 * The keys of the map entry represent the CAS symbol (such as {@code "Pi"} in SYMJA for the pi constant), the first index of
		 * the {@code Tuple.Couple} is the format for display engines (such as {@code "\\pi"} for MathJax for the pi constant. Note the
		 * double slash escapes the one in front) and the second index of the {@code Tuple.Couple} contains the {@code Function} that
		 * returns the {@code SegmentBuilder} containing the numeric value. For example: the constant for pi can be inserted using:
		 * 
		 * <pre>
		 *	<code>
		 *	 Scientific  s = new Scientific();
		 *	 
		 *	 s.setResultType(Params.ResultType.DECIMAL);
		 *	 Supplier.Function<Params, SegmentBuilder> fx = p -> new SegmentBuilder(Digits.toSegment(Constants.pi(p.getScale()), 0, new DigitPunc()));
		 *	 
		 *	 s.addConstant("Pi", Tuple.of("\\pi", fx));
		 *	 
		 *	 fx = p -> new SegmentBuilder(Digits.toSegment(Constants.e(p.getScale()), 0, new DigitPunc()));
		 *	 s.addConstant("E", Tuple.of("e", fx));
		 *	</code>
		 * </pre>
		 * <p>
		 * Gets the map of constants where each entry maps the numerical value to a function that retrieves the
		 * {@code mathaid.calculator.base.typeset.SegmentBuilder} representing the {@code mathaid.calculator.base.typeset.LinkedSegment}
		 * format of this variable. The argument to the function is a sub-interface of {@code ExpressionParams} which allows the
		 * building of the representational format to access options such as can be found in
		 * {@code mathaid.calculator.base.typeset.DigitPunc}.
		 * 
		 * @return gets the constants set in this object. Note that the map returned may be unmodifiable.
		 */
		Map<String, Couple<String, Function<T, SegmentBuilder>>> getConstants();

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 05:45:50 ---------------------------------------------------
		 */
		/**
		 * Gets the bound variables as a Map. A bound variable is a variable whose actual representation within an expression is
		 * numeric. e.g {@code A^2 + 35} when {@code A = 0.5}, {@code A} is a variable that is 'bound' to {@code 35}.
		 * <p>
		 * The keys of the map entry represent the CAS symbol (such as {@code "Pi"} in SYMJA for the pi constant), the first index of
		 * the {@code Tuple.Couple} is the format for display engines (such as {@code "\\pi"} for MathJax for the pi constant. Note the
		 * double slash escapes the one in front) and the second index of the {@code Tuple.Couple} contains the {@code Function} that
		 * returns the {@code SegmentBuilder} containing the numeric value. For example: the constant for pi can be inserted using:
		 * 
		 * <pre>
		 *	<code>
		 *	 Scientific  s = new Scientific();
		 *	 
		 *	 s.setResultType(Params.ResultType.DECIMAL);
		 *	 Supplier.Function<Params, SegmentBuilder> fx = p -> new SegmentBuilder(Digits.toSegment(Constants.pi(p.getScale()).multiply(Utility.d(2)), 0, new DigitPunc()));
		 *	 
		 *	 s.addBoundVariables("C", Tuple.of("\\mu", fx));
		 *	 
		 *	 fx = p -> new SegmentBuilder(Digits.toSegment(Constants.e(p.getScale()), 0, new DigitPunc()));
		 *	 s.addConstant("E", Tuple.of("e", fx));
		 *	</code>
		 * </pre>
		 * <p>
		 * Gets the map of variables where each entry maps the numerical value to a function that retrieves the
		 * {@code mathaid.calculator.base.typeset.SegmentBuilder} representing the {@code mathaid.calculator.base.typeset.LinkedSegment}
		 * format of this variable. The argument to the function is a sub-interface of {@code ExpressionParams} which allows the
		 * building of the representational format to access options such as can be found in
		 * {@code mathaid.calculator.base.typeset.DigitPunc}.
		 * <p>
		 * All bound variables are represented as letters in upper-case.
		 * 
		 * @return gets the bound variables set in this object. Note that the map returned may be unmodifiable.
		 */
		Map<String, Couple<String, Function<T, SegmentBuilder>>> getBoundVariables();

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 05:42:27 ---------------------------------------------------
		 */
		/**
		 * Gets a 2-length array. The first index contains the value used as the division operator during parsing. The second index
		 * contains the value used for producing formatted outputs.
		 * 
		 * @return a 2-length array containing the division operator for input and output representation.
		 */
		String[] getDivisionString();

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 05:24:52 ---------------------------------------------------
		 */
		/**
		 * Gets a 2-length array. The first index contains the value used as the multiplication operator during parsing. The second
		 * index contains the value used for producing formatted outputs.
		 * 
		 * @return a 2-length array containing the multiplication operator for input and output representation.
		 */
		String[] getMultiplicationString();

		/*
		 * Date: 12 Sep 2022-----------------------------------------------------------
		 * Time created: 09:31:50--------------------------------------------
		 */
		/**
		 * Gets the trigonometry used during computations and also to represent the result thereof.
		 * 
		 * @return the {@code AngleUnit} used for trigonometry calculations.
		 */
		AngleUnit getTrig();

	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 21:39:00 ---------------------------------------------------
	 */
	/**
	 * Creates a {@code DigitPunc} from number formatting options
	 * @param p the params
	 * @param nor number of repeatend
	 * @return a {@code DigitPunc}.
	 */
	public static DigitPunc fromParams(ExpressionParams<?> p, int nor) {
		return new DigitPunc(p.getIntSeparator(), p.getDecimalPoint(), p.getMantSeparator(), p.getIntGroupSize(), p.getMantGroupSize(), nor);
	}
	
	/*
	 * Date: 4 Sep 2022-----------------------------------------------------------
	 * Time created: 17:34:21---------------------------------------------------
	 */
	/**
	 * Constructor for creating an {@code EvaluatableExpression}.
	 * 
	 * @param params the {@code ExpressionParams} representing options for the evaluation and format within this expression.
	 */
	public EvaluatableExpression(P params) {
		this.params = params;
	}

	/*
	 * Date: 29 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:10:24 ---------------------------------------------------
	 */
	/**
	 * Returns the internal representation of this {@code Expression} which may be different from the string that is used in the
	 * {@link #format} method. For example: the value for planck's constant is {@code 6.626176e-34}, and it may be parsed from one
	 * form into an expression. Supposing the expression is meant to display in TeX form (which is
	 * <code>6.626~176\cdot10^{34}</code>), the parser may also want to retain the above form which may be stored and returned by
	 * this method.
	 * <p>
	 * This is meant to be overridden as the method {@link #format(SegmentBuilder)} only provides a way to build segments, this
	 * method will provide a way to return this some form of raw state.
	 * 
	 * @return a string representation of {@code this}.
	 */
	public abstract String getName();

	/*
	 * Date: 29 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:13:05 ---------------------------------------------------
	 */
	/**
	 * A check implemented to to impose comparative differences between expressions.
	 * <p>
	 * Compares {@code this} to the argument and returns {@code true} if it is equal to it or {@code false} if otherwise. All
	 * expressions are meant to be unique, and only equal {@code Expressions} should return {@code true}.
	 * 
	 * @param o {@inheritDoc}
	 * @return {@code true} if {@code this} is equal to the argument or {@code false} if otherwise.
	 */
	@Override
	public abstract boolean equals(Object o);

	/*
	 * Date: 29 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:14:44 ---------------------------------------------------
	 */
	/**
	 * Returns the hash code associated with this {@code Expression}, following that all expressions are meant to be unique, and
	 * only equal {@code Expressions} should have the same hash code. This way, implementation of the {@code Comparable} interface
	 * can be easily done.
	 * <p>
	 * This gets the hash-code of this expression.
	 * <p>
	 * This is implemented to get an expression that has consistent natural ordering.
	 * 
	 * @return {@inheritDoc}
	 * @see Object#hashCode()
	 */
	@Override
	public abstract int hashCode();

	/*
	 * Date: 29 Nov 2023 -----------------------------------------------------------
	 * Time created: 08:18:02 ---------------------------------------------------
	 */
	/**
	 * Evaluates {@code this} using values from this object and returns an {@code EvaluatableExpression} that may or may not be
	 * modified.
	 * <p>
	 * Expressions such as {@code 6.33} when parsed and evaluated will return the same expression relating to {@code 6.33}, but
	 * expressions such as <code>5+a*a</code> when parsed and evaluated may return an expression like {@code 5+a^2}, which in this
	 * case, is different from the original.
	 * <p>
	 * Evaluation is done using inputs provided from the expression itself. These inputs are values that were parsed from a string
	 * expression by a {@code Parser} or that were gotten from the {@link #getParams() params} object.
	 * <p>
	 * An example of this would be the expression <code>sin(5x^2)</code>.The parser may first parse sin then call {@code evaluate()}
	 * on sin which will return sin (this can be called an infinite number of times) then parse all the contents of the parenthesis
	 * and once again call {@code evaluate()} on the content of the parenthesis. Then evaluate is called again on sin and 5x^2 (the
	 * argument), this will return the final result.
	 * </p>
	 * 
	 * @return a valid {@code EvaluatableExpression} (possibly a new object) that was the result of the computation done.
	 */
	@Override
	public abstract EvaluatableExpression<P> evaluate();

	/*
	 * Date: 29 Nov 2023 -----------------------------------------------------------
	 * Time created: 08:43:24 ---------------------------------------------------
	 */
	/**
	 * Appends the {@code LinkedSegment} representation of this {@code EvaluatableExpression} into the given {@code SegmentBuilder},
	 * which may contain {@code Segments} from preceding expressions from the AST.
	 * <p>
	 * This method is meant to be called just once (via a recursion), it is not meant to be called manually and calling it more than
	 * once will contaminate the builder argument which may throw an exception.
	 * 
	 * @param formatBuilder the builder which stores the segment nodes of any preceding expression.
	 */
	@Override
	public abstract void format(SegmentBuilder formatBuilder);

	/*
	 * Date: 29 Nov 2023 ----------------------------------------------------------- Time created: 06:29:36
	 * ---------------------------------------------------
	 */
	/**
	 * Gets the {@code ExpressionParams} for this expression.
	 * 
	 * @return the params object for this expression.
	 */
	public P getParams() {
		return params;
	}

	/**
	 * the params object.
	 */
	private final P params;
}
