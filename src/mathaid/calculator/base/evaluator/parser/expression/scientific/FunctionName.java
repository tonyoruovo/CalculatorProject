/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.scientific;

/*
 * Date: 12 Sep 2022----------------------------------------------------------- 
 * Time created: 09:43:37---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression.scientific------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: FunctionName.java------------------------------------------------------ 
 * Class identifier: FunctionName------------------------------------------------ 
 */
/**
 * Interface consisting entirely of static fields that are identifiers for pre-defined Mathematical functions within the
 * underlying CAS (Computer Algebra System).
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
interface FunctionName {
	/**
	 * The identifier for the trigonometric sine function.
	 */
	String SIN = "Sin";
	/**
	 * The identifier for the inverse of the trigonometric sine function.
	 */
	String ASIN = "ArcSin";
	/**
	 * The identifier for the hyperbolic of the trigonometric sine function.
	 */
	String SINH = "Sinh";
	/**
	 * The identifier for the inverse hyperbolic of the trigonometric sine function.
	 */
	String ASINH = "ArcSinh";
	/**
	 * The identifier for the trigonometric cosine function.
	 */
	String COS = "Cos";
	/**
	 * The identifier for the inverse of the trigonometric cosine function.
	 */
	String ACOS = "ArcCos";
	/**
	 * The identifier for the hyperbolic of the trigonometric cosine function.
	 */
	String COSH = "Cosh";
	/**
	 * The identifier for the inverse hyperbolic of the trigonometric cosine function.
	 */
	String ACOSH = "ArcCosh";
	/**
	 * The identifier for the trigonometric tangent function.
	 */
	String TAN = "Tan";
	/**
	 * The identifier for the inverse of the trigonometric tangent function.
	 */
	String ATAN = "ArcTan";
	/**
	 * The identifier for the hyperbolic of the trigonometric tangent function.
	 */
	String TANH = "Tanh";
	/**
	 * The identifier for the inverse hyperbolic of the trigonometric tangent function.
	 */
	String ATANH = "ArcTanh";
	/**
	 * The identifier for the trigonometric cosecant function.
	 */
	String CSC = "Csc";
	/**
	 * The identifier for the inverse of the trigonometric cosecant function.
	 */
	String ACSC = "ArcCsc";
	/**
	 * The identifier for the hyperbolic of the trigonometric cosecant function.
	 */
	String CSCH = "Csch";
	/**
	 * The identifier for the inverse hyperbolic of the trigonometric cosecant function.
	 */
	String ACSCH = "ArcCsch";
	/**
	 * The identifier for the trigonometric secant function.
	 */
	String SEC = "Sec";
	/**
	 * The identifier for the inverse of the trigonometric secant function.
	 */
	String ASEC = "ArcSec";
	/**
	 * The identifier for the hyperbolic of the trigonometric secant function.
	 */
	String SECH = "Sech";
	/**
	 * The identifier for the inverse hyperbolic of the trigonometric secant function.
	 */
	String ASECH = "ArcSech";
	/**
	 * The identifier for the trigonometric cotangent function.
	 */
	String COT = "Cot";
	/**
	 * The identifier for the inverse of the trigonometric cotangent function.
	 */
	String ACOT = "ArcCot";
	/**
	 * The identifier for the hyperbolic of the trigonometric cotangent function.
	 */
	String COTH = "Coth";
	/**
	 * The identifier for the inverse hyperbolic of the trigonometric cotangent function.
	 */
	String ACOTH = "ArcCoth";

	/**
	 * The identifier for the common fraction function.
	 */
	String RATIONAL = "Rational";

	/**
	 * The identifier for the exponential function.
	 */
	String EXP = "Exp";
	/**
	 * The identifier for the natural logarithm function.
	 */
	String LOG = "Log";
	/**
	 * The identifier for the decimal logarithm function.
	 */
	String LOG10 = "Log10";
	/**
	 * The identifier for the binary logarithm function.
	 */
	String LOG2 = "Log2";

	/**
	 * The identifier for the square root function.
	 */
	String SQRT = "Sqrt";
	/**
	 * The identifier for the power function.
	 */
	String POW = "Power";
	/**
	 * The identifier for the cube root function.
	 */
	String CBRT = "Cqrt";

	/**
	 * The identifier for the absolute function.
	 */
	String ABS = "Abs";
	/**
	 * The identifier for the signum/sign function.
	 */
	String SIGN = "Sign";
	/**
	 * The identifier for the greatest common divisor (or highest common factor) function.
	 */
	String GCD = "Gcd";
	/**
	 * The identifier for the lowest common multiple function.
	 */
	String LCM = "Lcm";
	/**
	 * The identifier for the modular function.
	 */
	String MOD = "Mod";
	/**
	 * The identifier for the minimum function.
	 */
	String MIN = "Min";
	/**
	 * The identifier for the maximum function.
	 */
	String MAX = "Max";

	/**
	 * The identifier for the {@code nPr} (combinatronics) function.
	 */
	String NPR = "Npr";
	/**
	 * The identifier for the {@code nCr} (combinatronics) function.
	 */
	String NCR = "Ncr";

	/**
	 * The identifier for the gamma function.
	 */
	String GAMMA = "Gamma";
	/**
	 * The identifier for the polygamma function.
	 */
	String POLYGAMMA = "PolyGamma";

	/**
	 * The identifier for the sum function.
	 */
	String SUM = "Sum";
	/**
	 * The identifier for the product function.
	 */
	String PRODUCT = "Product";
	/**
	 * The identifier for the differential function.
	 */
	String DIFF = "D";
	/**
	 * The identifier for the integral function.
	 */
	String INT = "Integral";
	/**
	 * The identifier for the limit function.
	 */
	String LIMIT = "Limit";
	/**
	 * The identifier for the error function.
	 */
	String ERF = "Erf";
}
