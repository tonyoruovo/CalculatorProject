/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.scientific;

import static java.util.Collections.unmodifiableMap;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ABS;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ACOS;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ACOSH;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ACOT;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ACOTH;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ACSC;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ACSCH;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ASEC;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ASECH;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ASIN;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ASINH;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ATAN;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.ATANH;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.COS;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.COSH;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.COT;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.COTH;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.CSC;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.CSCH;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.DIFF;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.GAMMA;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.INT;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.LIMIT;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.LOG;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.LOG10;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.POW;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.PRODUCT;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.RATIONAL;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.SEC;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.SECH;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.SIGN;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.SIN;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.SINH;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.SQRT;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.SUM;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.TAN;
import static mathaid.calculator.base.evaluator.parser.expression.scientific.FunctionName.TANH;
import static mathaid.calculator.base.typeset.Digits.fromSegmentString;
import static mathaid.calculator.base.typeset.Digits.toSegment;
import static mathaid.calculator.base.typeset.Segments.boundVariable;
import static mathaid.calculator.base.typeset.Segments.constant;
import static mathaid.calculator.base.typeset.Segments.freeVariable;
import static mathaid.calculator.base.util.Utility.d;
import static mathaid.calculator.base.util.Utility.mc;
import static mathaid.calculator.base.util.Utility.numOfFractionalDigits;
import static mathaid.calculator.base.util.Utility.numOfIntegerDigits;
import static mathaid.calculator.base.util.Utility.rm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.typeset.LinkedSegment;
import mathaid.calculator.base.typeset.NumberAdapter;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.value.BigFraction;
import mathaid.functional.Supplier.Function;

/*
 * Date: 4 Sep 2022----------------------------------------------------------- 
 * Time created: 17:41:03---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression.scientific------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Name.java------------------------------------------------------ 
 * Class name: Name------------------------------------------------ 
 */
/**
 * The base concrete class for all expressions created from a parser. The represent function names, operators, numbers,
 * constants and variables.
 * <p>
 * An {@code Expression} that represents a scalar value such as a variable, constant, number created from the scientific
 * evaluator. It is an {@code Expression} with an internal string representation. This represents the family of expressions that
 * carry a given name.
 * <p>
 * When numbers are represented by this class, the entire digits in the number comprises it, including the exponent. For
 * example: in the expression {@code sin(4.331e-5) * sqrt(17 /43)}, there are 3 number nodes namely {@code 4.331e-5}, {@code 17}
 * and {@code 43}. Notice that each number node consists of it's entire digits including the exponent and not just individual
 * digits. This can also be extended to complex numbers.
 * <p>
 * When variables and constants are represented, a lookup is performed using the internal {@linkplain #getParams() params}
 * object to determine the actual representation. For example: in the expression {@code e ^ (pi + infinity)}, the fact that
 * {@code e}, {@code pi} and {@code infinity} is an instance of this class is not determined by the length, but by the
 * availability of those symbols within the internal table.
 * <p>
 * It directly refers to an expression such as <code>abc</code>. Expressions such as <code>sin(x)</code> are still regarded as
 * an {@code EvaluatableExpression}, however, they specifically consist of a name and a functional parenthesis, as such they are
 * grouped as functions. Another example is <code>x + y</code>; in this case, the name of such an expression is "{@code +}", but
 * that is just the {@code String} that will be returned via the ubiquitous method {@link #getName()} and not the object itself.
 * Such types of expressions are usually implemented as a binary operator - i.e a sub-type of {@code Name} that has 2 fields (x
 * &amp; y) that are both an evaluatable name.
 * <p>
 * In the context of a {@code PrattParser}, a {@code Name} should never be created directly as it is implemented by certain
 * internal objects within the {@code PrattParser} class as an <i>AST</i>, as such attempts at direct implementation yield in
 * undefined results.
 * <p>
 * In an event where a user may want to implement an expression for Mathematical graphical display (such as TeX) applications,
 * this class may be used to differentiate between the internal name of the expression and the string the user wishes to present
 * to clients.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Name extends EvaluatableExpression<Name.Params> {

	/*
	 * Date: 29 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:05:57 ---------------------------------------------------
	 * Package: mathaid.calculator.base.evaluator.parser.expression.scientific ------------------------------------------------
	 * Project: CalculatorProject ------------------------------------------------
	 * File: Name.java ------------------------------------------------------
	 * Class name: Params ------------------------------------------------
	 */
	/**
	 * The {@code ExpressionParams} for scientific expressions, containing definitions for decimals, options for mathematical
	 * evaluations, for formatting to {@code SegmentBuilder} etc.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 */
	public static interface Params extends EvaluatableExpression.ExpressionParams<Params> {

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 07:13:54 ---------------------------------------------------
		 * Package: mathaid.calculator.base.evaluator.parser.expression.scientific ------------------------------------------------
		 * Project: CalculatorProject ------------------------------------------------
		 * File: Name.java ------------------------------------------------------
		 * Class name: ResultType ------------------------------------------------
		 */
		/**
		 * The interface that contains the constants used for result types within the scientific calculator.
		 * 
		 * @author Oruovo Anthony Etineakpopha
		 * @email tonyoruovo@gmail.com
		 */
		public static interface ResultType extends EvaluatableExpression.ExpressionParams.ResultType {
			/**
			 * The {@code int} constant that specifies: that all result output formats must have finite significand without any exponents
			 * attached.
			 */
			int FIX = 1;
			/**
			 * The {@code int} constant that specifies: that all result output formats must be in conventional scientific notation (standard
			 * form).
			 */
			int SCI = 2;
			/**
			 * The {@code int} constant that specifies that all result output formats must use the engineering notational format. This
			 * format may append an engineering suffix to the result or use the thousandth suffix.
			 * 
			 * @see mathaid.calculator.base.util.Utility#toEngineeringString(BigDecimal, boolean)
			 */
			int ENG = 3;
			/**
			 * The {@code int} constant that specifies that all result output formats must use fractional format.
			 */
			int FRAC = 4;
			/**
			 * The {@code int} constant that specifies that all result output formats must use fractional format and where possible, use
			 * mixed fractions.
			 */
			int MFRAC = 5;
			/**
			 * The {@code int} constant that specifies that all result output formats must be non-numeric. For example the expression
			 * {@code sin(60)} may in {@code sqrt(3)/2} instead of {@code 0.866025403784439}.
			 */
			int EXPRESSION = 6;
		}

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 12:27:53 ---------------------------------------------------
		 */
		/**
		 * Getter for the whether this is set to support evaluations resulting in results that may have the imaginary number, such as
		 * {@code sqrt(-20)}.
		 * 
		 * @return <code>true</code> if {@code this} supports complex calculations or <code>false</code> if otherwise.
		 */
		boolean isComplex();

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 12:31:45 ---------------------------------------------------
		 */
		/**
		 * Checks if {@code this} is set to indicate that the whole expression AST may contain one or more integral node(s).
		 * <p>
		 * This option is used for formatting expression
		 * 
		 * @return <code>true</code> if {@code this} has integrals or <code>false</code> if otherwise.
		 */
		boolean hasIntegral();

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 12:31:45 ---------------------------------------------------
		 */
		/**
		 * Checks if {@code this} is set to indicate that the whole expression AST may contain one or more differential node(s).
		 * <p>
		 * This option is used for formatting expression
		 * 
		 * @return <code>true</code> if {@code this} has differential or <code>false</code> if otherwise.
		 */
		boolean hasDifferential();

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 12:35:07 ---------------------------------------------------
		 */
		/**
		 * Gets the type of value to use when constructing a {@code DigitPunc} for number sub-trees during formatting of expressions.
		 * <p>
		 * Valid values are:
		 * <ul>
		 * <li>{@link mathaid.calculator.base.typeset.Segment.Type#ARC ARC}</li>
		 * <li>{@link mathaid.calculator.base.typeset.Segment.Type#DOT DOT}</li>
		 * <li>{@link mathaid.calculator.base.typeset.Segment.Type#DOT_ALL DOT_ALL}</li>
		 * <li>{@link mathaid.calculator.base.typeset.Segment.Type#DOT_BAR DOT_BAR}</li>
		 * <li>{@link mathaid.calculator.base.typeset.Segment.Type#ELLIPSIS ELLIPSIS}</li>
		 * <li>{@link mathaid.calculator.base.typeset.Segment.Type#PARENTHESISED PARENTHESISED}</li>
		 * <li>{@link mathaid.calculator.base.typeset.Segment.Type#VINCULUM VINCULUM}</li>
		 * </ul>
		 * 
		 * @return the {@link mathaid.calculator.base.typeset.LinkedSegment#getType() segment type} used for constructing recurring
		 *         digit nodes.
		 * @see mathaid.calculator.base.typeset.Digit
		 */
		int getRecurringType();// use one of Segment.getType()

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 12:35:07 ---------------------------------------------------
		 */
		/**
		 * Gets the number of recurring digits in the repeatend portion of the ellipsis type used when constructing a {@code DigitPunc}
		 * using the {@link mathaid.calculator.base.typeset.Segment.Type#ELLIPSIS} as the type for formatting recurring digit.
		 * 
		 * @return the number of recurring digits in the recurring part. Used for constructing recurring digit nodes.
		 * @see mathaid.calculator.base.typeset.DigitPunc#getNumOfRecurringDigits()
		 */
		int getNumOfRepeats();

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 13:50:31 ---------------------------------------------------
		 */
		/**
		 * Gets a 2-length array representing supported independent variables used within an expression in the scientific calculator. An
		 * independent (free) variable is a variable that is not {@linkplain #getBoundVariables() bound} to a value, but is abstracted
		 * as a value i.e it is treated within am expression has having a numeric value but never evaluated as such. e.g {@code 2x - y}
		 * is a linear expression with both {@code x} and {@code y} acting as independent variables. Not all values within an expression
		 * that is a letter is an independent variable.
		 * <p>
		 * All independent variables are lower-cased and are used for plotting mathematic diagrams such as cartesian graphs.
		 * <p>
		 * All letters within an expression that are not names of functions, {@link #getConstants() supported constants} and configured
		 * {@link #getBoundVariables() bound variables} must be an element of this array or else a crash (from throwing an exception)
		 * may occur.
		 * 
		 * @return an array representing valid independent variables.
		 * @implNote This method is meant to return the array of characters used for independent variables in the scientific calculator.
		 *           Currently, only 2 will be returned, namely: {@code 'x'} and {@code 'y'}. In the future, {@code 'z'} will be added.
		 */
		default char[] getIndependentVariables() {
			return "xy".toCharArray();
		}

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 14:00:11 ---------------------------------------------------
		 */
		/**
		 * Gets the number of significant digits in a numerical computation to be retained in the result.
		 * 
		 * @return the precision of calculations
		 * @see java.math.MathContext#getPrecision()
		 */
		int getScale();

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 14:02:19 ---------------------------------------------------
		 */
		/**
		 * Gets the value {@code 10}.
		 * 
		 * @return {@code 10}.
		 */
		@Override
		default int getRadix() {
			return 10;
		}

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 14:03:14 ---------------------------------------------------
		 */
		/**
		 * Gets the identifier used in the evaluator to represent logarithm functions. For example, some CAS (Computer Algebra System)
		 * use the string 'log' as the function name, in this context, if the user is using one of those CAS that uses the examples,
		 * then this method will return one of the above string.
		 * <p>
		 * These are used to give the parsers a hint that the value is a logarithm function and also to helps to properly format these
		 * types of functions.
		 * 
		 * @return the identifier used by the underlying CAS to represent the {@code log} function in expressions.
		 * @implNote The current implementation is to return {@link "Log"} because of the definition of the identifier of the log
		 *           function in SYMJA. In the future this may change as an in-house CAS is developed.
		 */
		default String getLog() {
			return LOG10;
		}

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 14:07:33 ---------------------------------------------------
		 */
		/**
		 * Gets the identifier used in the evaluator to represent trigonometric functions.
		 * <p>
		 * These are used to give the parsers a hint that the value is an trigonometric function and also to helps to properly format
		 * these types of functions. Gets the mappings for trigonometric function identifiers.
		 * <p>
		 * The keys are the inverse function and the values are the corresponding function themselves, see table below.
		 * <table border="1">
		 * <thead>
		 * <tr>
		 * <th>Key</th>
		 * <th>Value</th>
		 * <th>Remark</th>
		 * </tr>
		 * </thead> <tbody>
		 * <tr>
		 * <td>{@code "ArcSin"}</td>
		 * <td>{@code "Sin"}</td>
		 * <td>Represents the identifier for the sine function and it's inverse counterpart</td>
		 * </tr>
		 * <tr>
		 * <td>{@code "ArcCos"}</td>
		 * <td>{@code "Cos"}</td>
		 * <td>Represents the identifier for the cosine function and it's inverse counterpart</td>
		 * </tr>
		 * <tr>
		 * <td>{@code "ArcTan"}</td>
		 * <td>{@code "Tan"}</td>
		 * <td>Represents the identifier for the tangent function and it's inverse counterpart</td>
		 * </tr>
		 * <tr>
		 * <td>{@code "ArcCot"}</td>
		 * <td>{@code "Cot"}</td>
		 * <td>Represents the identifier for the cotangent function and it's inverse counterpart</td>
		 * </tr>
		 * <tr>
		 * <td>{@code "ArcCsc"}</td>
		 * <td>{@code "Csc"}</td>
		 * <td>Represents the identifier for the cosecant function and it's inverse counterpart</td>
		 * </tr>
		 * <tr>
		 * <td>{@code "ArcSec"}</td>
		 * <td>{@code "Sec"}</td>
		 * <td>Represents the identifier for the secant function and it's inverse counterpart</td>
		 * </tr>
		 * <tr>
		 * <td>{@code "ArcSinh"}</td>
		 * <td>{@code "Sinh"}</td>
		 * <td>Represents the identifier for the hyperbolic sine function and it's inverse counterpart</td>
		 * </tr>
		 * <tr>
		 * <td>{@code "ArcCosh"}</td>
		 * <td>{@code "Cosh"}</td>
		 * <td>Represents the identifier for the hyperbolic cosine function and it's inverse counterpart</td>
		 * </tr>
		 * <tr>
		 * <td>{@code "ArcTanh"}</td>
		 * <td>{@code "Tanh"}</td>
		 * <td>Represents the identifier for the hyperbolic tangent function and it's inverse counterpart</td>
		 * </tr>
		 * <tr>
		 * <td>{@code "ArcCoth"}</td>
		 * <td>{@code "Coth"}</td>
		 * <td>Represents the identifier for the hyperbolic cotangent function and it's inverse counterpart</td>
		 * </tr>
		 * <tr>
		 * <td>{@code "ArcCsch"}</td>
		 * <td>{@code "Csch"}</td>
		 * <td>Represents the identifier for the hyperbolic cosecant function and it's inverse counterpart</td>
		 * </tr>
		 * <tr>
		 * <td>{@code "ArcSech"}</td>
		 * <td>{@code "Sech"}</td>
		 * <td>Represents the identifier for the hyperbolic secant function and it's inverse counterpart</td>
		 * </tr>
		 * </tbody>
		 * </table>
		 * 
		 * @return an unmodifiable {@code Map} of all supported trigonometric functions used by the underlying CAS to represent the
		 *         trigonometric function in expressions.
		 * @implNote The current implementation returns values that are compatible with SYMJA. In the future this may change as an
		 *           in-house CAS is developed.
		 */
		default Map<String, String> getInverseTrigFunctions() {
			Map<String, String> f = new HashMap<>();
			f.put(ASIN, SIN);
			f.put(ACOS, COS);
			f.put(ATAN, TAN);
			f.put(ACOT, COT);
			f.put(ACSC, CSC);
			f.put(ASEC, SEC);
			f.put(ASINH, SINH);
			f.put(ACOSH, COSH);
			f.put(ATANH, TANH);
			f.put(ACOTH, COTH);
			f.put(ACSCH, CSCH);
			f.put(ASECH, SECH);
			return unmodifiableMap(f);
		}

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 14:03:14 ---------------------------------------------------
		 */
		/**
		 * Gets the identifier used in the evaluator to represent absolute functions. For example, some CAS (Computer Algebra System)
		 * use the string 'abs' as the function name, in this context, if the user is using one of those CAS that uses the examples,
		 * then this method will return one of the above string.
		 * <p>
		 * These are used to give the parsers a hint that the value is an absolute function and also to helps to properly format these
		 * types of functions.
		 * 
		 * @return the identifier used by the underlying CAS to represent the {@code abs} function in expressions.
		 * @implNote The current implementation is to return {@link "Abs"} because of the definition of the identifier of the absolute
		 *           function in SYMJA. In the future this may change as an in-house CAS is developed.
		 */
		default String getAbs() {
			return ABS;
		}

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 14:03:14 ---------------------------------------------------
		 */
		/**
		 * Gets the identifier used in the evaluator to represent square root functions. For example, some CAS (Computer Algebra System)
		 * use the string 'sqrt' as the function name, in this context, if the user is using one of those CAS that uses the examples,
		 * then this method will return one of the above string.
		 * <p>
		 * These are used to give the parsers a hint that the value is a square root function and also to helps to properly format these
		 * types of functions.
		 * 
		 * @return the identifier used by the underlying CAS to represent the {@code sqrt} function in expressions.
		 * @implNote The current implementation is to return {@link "Sqrt"} because of the definition of the identifier of the square
		 *           root function in SYMJA. In the future this may change as an in-house CAS is developed.
		 */
		default String getSqrt() {
			return SQRT;
		}

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 14:03:14 ---------------------------------------------------
		 */
		/**
		 * Gets the identifier used in the evaluator to represent gamma functions. For example, some CAS (Computer Algebra System) use
		 * the string 'gamma', or 'g' as the function name, in this context, if the user is using one of those CAS that uses the
		 * examples, then this method will return one of the above string.
		 * <p>
		 * These are used to give the parsers a hint that the value is a gamma function and also to helps to properly format these types
		 * of functions.
		 * 
		 * @return the identifier used by the underlying CAS to represent the {@code gamma} function in expressions.
		 * @implNote The current implementation is to return {@link "Gamma"} because of the definition of the identifier of the gamma
		 *           function in SYMJA. In the future this may change as an in-house CAS is developed.
		 */
		default String getGamma() {
			return GAMMA;
		}

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 14:03:14 ---------------------------------------------------
		 */
		/**
		 * Gets the identifier used in the evaluator to represent power functions. For example, some CAS (Computer Algebra System) use
		 * the string 'pow', or 'power' as the function name, in this context, if the user is using one of those CAS that uses the
		 * examples, then this method will return one of the above string.
		 * <p>
		 * These are used to give the parsers a hint that the value is a power function and also to helps to properly format these types
		 * of functions.
		 * 
		 * @return the identifier used by the underlying CAS to represent the {@code pow} function in expressions.
		 * @implNote The current implementation is to return {@link "Power"} because of the definition of the identifier of the power
		 *           function in SYMJA. In the future this may change as an in-house CAS is developed.
		 */
		default String getPow() {
			return POW;
		}

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 14:03:14 ---------------------------------------------------
		 */
		/**
		 * Gets the identifier used in the evaluator to represent signum/sign functions. For example, some CAS (Computer Algebra System)
		 * use the string 'sgn', 'signum' or 'sign' as the function name, in this context, if the user is using one of those CAS that
		 * uses the examples, then this method will return one of the above string.
		 * <p>
		 * These are used to give the parsers a hint that the value is an signum function and also to helps to properly format these
		 * types of functions.
		 * 
		 * @return the identifier used by the underlying CAS to represent the {@code signum} function in expressions.
		 * @implNote The current implementation is to return {@link "Sign"} because of the definition of the identifier of the signum
		 *           function in SYMJA. In the future this may change as an in-house CAS is developed.
		 */
		default String getSignum() {
			return SIGN;
		}

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 14:03:14 ---------------------------------------------------
		 */
		/**
		 * Gets the identifier used in the evaluator to represent natural logarithm functions. For example, some CAS (Computer Algebra
		 * System) use the string 'log' or 'ln' as the function name, in this context, if the user is using one of those CAS that uses
		 * the examples, then this method will return one of the above string.
		 * <p>
		 * These are used to give the parsers a hint that the value is a natural logarithm function and also to helps to properly format
		 * these types of functions.
		 * 
		 * @return the identifier used by the underlying CAS to represent the {@code log} function in expressions.
		 * @implNote The current implementation is to return {@link #getLog()} because of the definition of the identifier of the
		 *           natural log function in SYMJA. In the future this may change as an in-house CAS is developed.
		 */
		default String getNaturalLog() {
			return LOG;
		}

		/*
		 * Date: 11 Sep 2022-----------------------------------------------------------
		 * Time created: 22:38:06--------------------------------------------
		 */
		/**
		 * Gets the identifier used in the evaluator to represent common fractions as Functions. For example most CAS (Computer Algebra
		 * System) use the string 'Fraction', 'Fractional', 'Ration' or 'Rational' as the function name for common fractions, in this
		 * context, if the user is using one of those CAS that uses the examples, then this method will return one of the above string.
		 * <p>
		 * These are used to give the parsers a hint that the value is a common fraction function and also to helps to properly format
		 * these types of functions.
		 * 
		 * @return the identifier used by the underlying CAS to represent the common fraction function in expressions.
		 * @implNote The current implementation is to return {@code "Rational"} because of the definition of the identifier of the
		 *           common fraction function in SYMJA. In the future this may change as an in-house CAS is developed.
		 */
		default String getFractionFunction() {
			return RATIONAL;
		}

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 14:03:14 ---------------------------------------------------
		 */
		/**
		 * Gets the identifier used in the evaluator to represent limit functions. For example, some CAS (Computer Algebra System) use
		 * the string 'limit' as the function name, in this context, if the user is using one of those CAS that uses the examples, then
		 * this method will return one of the above string.
		 * <p>
		 * These are used to give the parsers a hint that the value is a limit function and also to helps to properly format these types
		 * of functions.
		 * 
		 * @return the identifier used by the underlying CAS to represent the {@code limit} function in expressions.
		 * @implNote The current implementation is to return {@link "Limit"} because of the definition of the identifier of the limit
		 *           function in SYMJA. In the future this may change as an in-house CAS is developed.
		 */
		default String getLimit() {
			return LIMIT;
		}

		/*
		 * Date: 16 Sep 2022-----------------------------------------------------------
		 * Time created: 19:58:23--------------------------------------------
		 */
		/**
		 * Gets the identifier used in the evaluator to represent differential functions.
		 * <p>
		 * These are used to give the parsers a hint that the value is a differential function and also to helps to properly format
		 * these types of functions.
		 * 
		 * @return the identifier used by the underlying CAS to represent the {@code diff} function in expressions.
		 * @implNote The current implementation is to return {@link "D"} because of the definition of the identifier of the differential
		 *           function in SYMJA. In the future this may change as an in-house CAS is developed.
		 */
		default String getDifferential() {
			return DIFF;
		}

		/*
		 * Date: 16 Sep 2022-----------------------------------------------------------
		 * Time created: 19:58:58--------------------------------------------
		 */
		/**
		 * Gets the identifier used in the evaluator to represent integration functions.
		 * <p>
		 * These are used to give the parsers a hint that the value is a product integration and also to helps to properly format these
		 * types of functions.
		 * 
		 * @return the identifier used by the underlying CAS to represent the {@code integrate} function in expressions.
		 * @implNote The current implementation is to return {@link "Integrate"} because of the definition of the identifier of the
		 *           integration function in SYMJA. In the future this may change as an in-house CAS is developed.
		 */
		default String getIntegral() {
			return INT;
		}

		/*
		 * Date: 16 Sep 2022-----------------------------------------------------------
		 * Time created: 20:00:09--------------------------------------------
		 */
		/**
		 * Gets the identifier used in the evaluator to represent summation functions.
		 * <p>
		 * These are used to give the parsers a hint that the value is a summation function and also to helps to properly format these
		 * types of functions.
		 * 
		 * @return the identifier used by the underlying CAS to represent the {@code sum} function in expressions.
		 * @implNote The current implementation is to return {@link "Sum"} because of the definition of the identifier of the summation
		 *           function in SYMJA. In the future this may change as an in-house CAS is developed.
		 */
		default String getSummation() {
			return SUM;
		}

		/*
		 * Date: 16 Sep 2022-----------------------------------------------------------
		 * Time created: 20:01:17--------------------------------------------
		 */
		/**
		 * Gets the identifier used in the evaluator to represent product functions.
		 * <p>
		 * These are used to give the parsers a hint that the value is a product function and also to helps to properly format these
		 * types of functions.
		 * 
		 * @return the identifier used by the underlying CAS to represent the {@code product} function in expressions.
		 * @implNote The current implementation is to return {@link "Product"} because of the definition of the identifier of the
		 *           product function in SYMJA. In the future this may change as an in-house CAS is developed.
		 */
		default String getProduct() {
			return PRODUCT;
		}

		/*
		 * Date: 16 Sep 2022-----------------------------------------------------------
		 * Time created: 20:03:02--------------------------------------------
		 */
		/**
		 * Gets the identifier used in the evaluator to represent floor functions. For example, some CAS (Computer Algebra System) use
		 * the string 'floor' as the function name, in this context, if the user is using one of those CAS that uses the examples, then
		 * this method will return one of the above string.
		 * <p>
		 * These are used to give the parsers a hint that the value is a floor function and also to helps to properly format these types
		 * of functions.
		 * 
		 * @return the identifier used by the underlying CAS to represent the {@code floor} function in expressions.
		 * @implNote The current implementation is to return {@link "Floor"} because of the definition of the identifier of the floor
		 *           function in SYMJA. In the future this may change as an in-house CAS is developed.
		 */
		default String getFloor() {
			return "Floor";
		}

		/*
		 * Date: 29 Nov 2023 -----------------------------------------------------------
		 * Time created: 14:03:14 ---------------------------------------------------
		 */
		/**
		 * Gets the identifier used in the evaluator to represent ceil functions. For example, some CAS (Computer Algebra System) use
		 * the string 'ceil' as the function name, in this context, if the user is using one of those CAS that uses the examples, then
		 * this method will return one of the above string.
		 * <p>
		 * These are used to give the parsers a hint that the value is a ceil function and also to helps to properly format these types
		 * of functions.
		 * 
		 * @return the identifier used by the underlying CAS to represent the {@code ceil} function in expressions.
		 * @implNote The current implementation is to return {@link "Ceil"} because of the definition of the identifier of the ceil
		 *           function in SYMJA. In the future this may change as an in-house CAS is developed.
		 */
		default String getCeil() {
			return "Ceil";
		}

	}

	/*
	 * Date: 29 Nov 2023 -----------------------------------------------------------
	 * Time created: 16:21:16 ---------------------------------------------------
	 */
	/**
	 * Checks if the generic expression object is an integer.
	 * 
	 * @param x the value to be checked.
	 * @return {@code true} if {@code x} is an integer. Returns {@code false} if otherwise.
	 */
	protected static boolean isInteger(EvaluatableExpression<?> x) {
		if (isNumber(x))
			return mathaid.calculator.base.util.Utility.isInteger(d(x.getName()));
		return false;
	}

	/*
	 * Date: 29 Nov 2023 -----------------------------------------------------------
	 * Time created: 16:21:59 ---------------------------------------------------
	 */
	/**
	 * Checks if the generic expression object is a rational number.
	 * 
	 * @param x the value to be checked.
	 * @return {@code true} if {@code x} is a fraction consisting entirely of integers. Returns {@code false} if otherwise.
	 */
	protected static boolean isIntegerFraction(EvaluatableExpression<?> x) {
		if (x instanceof Function) {
			mathaid.calculator.base.evaluator.parser.expression.scientific.Function fraction = (mathaid.calculator.base.evaluator.parser.expression.scientific.Function) x;
			return fraction.getName().equals("Rational") && isInteger(fraction.getArguments().get(0))
					&& isInteger(fraction.getArguments().get(1));
		} else if (x instanceof Operator) {
			Operator div = (Operator) x;
			return div.getName().equals("/") && isInteger(div.getLeft()) && isInteger(div.getRight());
		}
		return false;
	}

	/*
	 * Date: 29 Nov 2023 -----------------------------------------------------------
	 * Time created: 16:22:08 ---------------------------------------------------
	 */
	/**
	 * Checks if the generic expression is a number.
	 * 
	 * @param x the value to be checked.
	 * @return {@code x instanceof Name && isNumber(x.getName())}
	 */
	protected static boolean isNumber(EvaluatableExpression<?> x) {
		return x instanceof Name && isNumber(x.getName());
	}

	/*
	 * Date: 29 Nov 2023 -----------------------------------------------------------
	 * Time created: 17:08:21 ---------------------------------------------------
	 */
	/**
	 * Gets the total number of integer and mantissa digits in {@code n}.
	 * 
	 * @param n a {@code BigDecimal}
	 * @return the total number of significand digits in {@code n}.
	 */
	static int getTotalScale(BigDecimal n) {
		return numOfIntegerDigits(n) + numOfFractionalDigits(n);
	}

	/*
	 * Date: 29 Nov 2023 -----------------------------------------------------------
	 * Time created: 16:58:58 ---------------------------------------------------
	 */
	/**
	 * Constructs a string where the decimal point is shifted so that the result is not in scientific notation after evaluating the
	 * {@code BigDecimal} value.
	 * 
	 * @param n      a non-negative value whose sign is {@code signum}.
	 * @param signum the sign of the {@code BigDecimal} value.
	 * @param scale  a positive value used to determine how many digits will be in the significand.
	 * @return a non-scientific notational format of {@code n} in the correct sign using the given {@code signum} and truncating to
	 *         the given {@code scale}
	 */
	static String fixedPoint(BigDecimal n, int signum, int scale) {
		n = n.abs();
		int totalScale = getTotalScale(n);
		if (totalScale > scale) {
			n = n.round(mc(scale, rm("HALF_EVEN")));
			n = n.stripTrailingZeros();
			return n.multiply(d(signum)).toPlainString();
		}
		totalScale = scale - totalScale;
		return n.setScale(totalScale, rm("HALF_EVEN")).multiply(d(signum)).toPlainString();
	}

	/*
	 * Date: 29 Nov 2023 -----------------------------------------------------------
	 * Time created: 16:55:04 ---------------------------------------------------
	 */
	/**
	 * Checks if the argument is in the same format as described by {@link mathaid.calculator.base.typeset.NumberAdapter}.
	 * 
	 * @param s the value to be checked.
	 * @return <code>true</code> if the argument is in the MathAid number format or <code>false</code> if otherwise.
	 */
	static boolean isNumber(String s) {
		try {
			fromSegmentString(s);
		} catch (Throwable t) {
			return false;
		}
		return true;
	}

	/*
	 * Date: 4 Sep 2022-----------------------------------------------------------
	 * Time created: 17:41:08---------------------------------------------------
	 */
	/**
	 * Creates a {@code Name} expression from the given {@code String} and {@code Params} object.
	 * 
	 * @param name   the value that will be returned by {@link #getName()}. This is usually a numeric string,
	 *               {@link Params#getConstants() a constant}, {@link Params#getIndependentVariables() a variable}, an operator or
	 *               the name of a function.
	 * @param params the {@code ExpressionParams} representing options for the evaluation and format within this expression.
	 */
	public Name(String name, Params params) {
		super(params);
		this.name = name;
	}

	/*
	 * Most Recent Date: 4 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:41:03--------------------------------------
	 */
	/**
	 * Constructs a {@code LinkedSegment} representing this {@code Name} and appends it to the argument. This specifically creates a
	 * number (if {@code this} is a number), a variable or a constant.
	 * <p>
	 * If this is a number the actual number tree created depends on the {@linkplain Params#getResultType() result type} and may in
	 * fact create fractions instead of a number tree.
	 * <p>
	 * If this is a variable or a constant, then the value is appended untouched. Note that for
	 * {@linkplain Params#getBoundVariables() bound variables}, they will be formatted the same way unless {@link #evaluate()} is
	 * called first which will replace the letter with it's bound value.
	 * <p>
	 * All calculations are done with a numerical precision of <code>{@linkplain Params#getScale() scale} + 5</code> and may throw
	 * exceptions that indicate that value(s) were out of range.
	 * <p>
	 * All sub-classes will override this method to provide their own evaluation unique to them.
	 * <p>
	 * This has no side-effects.
	 * 
	 * @param formatBuilder the builder to which the {@link LinkedSegment} representation of {@code this} will be appended.
	 */
	@Override
	public void format(SegmentBuilder formatBuilder) {
		// boolean isNumber = Utility.isNumber(getName());
		Params p = getParams();

		if (isNumber(getName())) {
			switch (p.getResultType()) {
			case Params.ResultType.DECIMAL:
			case Params.ResultType.EXPRESSION:
			default: {
				// String s = toSegmentString(
				// new BigFraction(new BigDecimal(getName()),
				// new java.math.MathContext(p.getScale(), rm("HALF_EVEN")), null, new BigDecimal("1E-10")),
				// 0);
				BigFraction s = fromSegmentString(getName()).setMathContext(mc(getParams().getScale()));
				formatBuilder
						.append(toSegment(s, p.getRecurringType(), 0, EvaluatableExpression.fromParams(p, p.getNumOfRepeats())));
				break;

			}
			case Params.ResultType.FIX: {
				BigDecimal n = fromSegmentString(getName()).getFraction();
				formatBuilder.append(toSegment(d(fixedPoint(n, n.signum(), p.getScale())), 0,
						EvaluatableExpression.fromParams(p, p.getNumOfRepeats())));
				break;
			}
			case Params.ResultType.SCI: {
				BigFraction s = fromSegmentString(getName());
				formatBuilder
						.append(toSegment(s, p.getRecurringType(), 1, EvaluatableExpression.fromParams(p, p.getNumOfRepeats())));
				break;
			}
			case Params.ResultType.ENG: {
				BigFraction s = fromSegmentString(getName());
				formatBuilder.append(
						toSegment(s, false, p.getRecurringType(), EvaluatableExpression.fromParams(p, p.getNumOfRepeats())));
				break;
			}
			case Params.ResultType.FRAC: {
				BigFraction s = fromSegmentString(getName());
				formatBuilder.append(toSegment(s, false, EvaluatableExpression.fromParams(p, p.getNumOfRepeats())));
				break;
			}
			case Params.ResultType.MFRAC: {
				BigFraction s = fromSegmentString(getName());
				formatBuilder.append(toSegment(s, true, EvaluatableExpression.fromParams(p, p.getNumOfRepeats())));
				break;
			}
			}
		}
		// else if (p.getConstants().containsKey(getName()))
		// formatBuilder.append(constant(p.getConstants().get(getName()).get(), getName()));
		// else if (p.getBoundVariables().containsKey(getName()))
		// formatBuilder.append(boundVar(p.getBoundVariables().get(getName()).get(), getName()));
		else if (getName().length() == 1 && String.valueOf(p.getIndependentVariables()).contains(getName())) {
			formatBuilder.append(freeVariable(getName(), getName()));
		} else if (p.getBoundVariables().containsKey(getName())) {
			formatBuilder.append(boundVariable(getParams().getBoundVariables().get(getName()).get(), getName()));
		} else if(p.getConstants().containsKey(getName())) {
			formatBuilder.append(constant(p.getConstants().get(getName()).get(), getName()));
		} else {
			formatBuilder.append(freeVariable(getName(), getName()));
		}
		// end if
	}

	/*
	 * Most Recent Date: 4 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:41:03--------------------------------------
	 */
	/**
	 * Further evaluates this {@code Name} to reduce it's components to either it's numeric form (for bound variables) or evaluate
	 * constants as numbers. e.g the expression <code>pi</code> will evaluate to {@code 3.141592653589793} to 15 s.f digits.
	 * <p>
	 * All sub-classes will override this method to provide their own evaluation unique to them.
	 * <p>
	 * This creates no side-effects.
	 * 
	 * @return a new {@code Name} that is a number (if {@code getParams().getType() != ExpressionParams.ResultType.Expression}) or
	 *         the same object.
	 */
	@Override
	public EvaluatableExpression<Params> evaluate() {
		/*
		 * There is no need for the code below because before evaluation, all constants
		 * and dependent variables will be unboxed (converted to their literal value) if
		 * params.result-type == expression.
		 */
		if (getParams().getResultType() != Params.ResultType.EXPRESSION)
			if (getParams().getConstants().containsKey(getName())) {
				LinkedSegment s = getParams().getConstants().get(getName()).get2nd().call(getParams()).toSegment();
				NumberAdapter sb = new NumberAdapter();
				s.toString(sb, null, new ArrayList<>(Arrays.asList(-1)));
				// TODO: This may cause an infinite recursion!!!
				// getParams().getInternalEvaluator().evaluate(sb.toString()).toSegment()
				// .toString(sb.delete(0, sb.length()), null, new ArrayList<>());
				return new Name(sb.toString(), getParams()).evaluate();//evaluate in case a non-decimal is retrieved
			} else if (getParams().getBoundVariables().containsKey(getName())) {
				LinkedSegment s = getParams().getBoundVariables().get(getName()).get2nd().call(getParams()).toSegment();
				NumberAdapter sb = new NumberAdapter();
				s.toString(sb, null, new ArrayList<>(Arrays.asList(-1)));
				// TODO: This may cause an infinite recursion!!!
				// getParams().getInternalEvaluator().evaluate(sb.toString()).toSegment()
				// .toString(sb.delete(0, sb.length()), null, new ArrayList<>());
				return new Name(sb.toString(), getParams()).evaluate();//evaluate in case a non-decimal is retrieved
			}
		return this;
	}

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:13:01 ---------------------------------------------------
	 */
	/**
	 * Gets the symbol(s) from which this expression was created, which is probably an alphanumeric, decimal or letter string.
	 * <p>
	 * All sub-classes will override this method to provide their own evaluation unique to them.
	 * 
	 * @return
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * Most Recent Date: 4 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:48:30--------------------------------------
	 */
	/**
	 * Checks whether {@code o} is an instance of {@code Name} and both their {@link #getName()} is equal.
	 * 
	 * @param o the value to be compared.
	 * @return <code>getName().equals(((Name) o).getName())</code>.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Name) {
			return getName().equals(((Name) o).getName());
		}
		return false;
	}

	/*
	 * Most Recent Date: 4 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:48:30--------------------------------------
	 */
	/**
	 * Gets the hash-code of the value returned by {@link #getName()}.
	 * 
	 * @return {@code getName().hashCode()}.
	 */
	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	/*
	 * Most Recent Date: 4 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:48:30--------------------------------------
	 */
	/**
	 * Returns {@link #getName()}
	 * 
	 * @return {@code getName()}
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * The name of this expression.
	 */
	private final String name;
}
