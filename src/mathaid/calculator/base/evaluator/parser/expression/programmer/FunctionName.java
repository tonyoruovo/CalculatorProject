/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.programmer;

/*
 * Date: 30 Nov 2022----------------------------------------------------------- 
 * Time created: 09:59:48---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression.programmer------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: FunctionName.java------------------------------------------------------ 
 * Class name: FunctionName------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface FunctionName {

	// Operator names
	/*
	 * Date: 11 Dec 2022-----------------------------------------------------------
	 * Time created: 11:16:55---------------------------------------------------
	 */
	/**
	 * Constant for the <b>NOT</b> operator.
	 */
	String NOT = "not";
	/*
	 * Date: 11 Dec 2022-----------------------------------------------------------
	 * Time created: 11:16:07---------------------------------------------------
	 */
	/**
	 * Constant for the <b>NOT</b> operator used in C for bit level arithmetics
	 */
	String C_NOT = "~";
	/*
	 * Date: 11 Dec 2022-----------------------------------------------------------
	 * Time created: 11:14:54---------------------------------------------------
	 */
	/**
	 * Constant for the <b>NOT</b> operator used in mathematics for logical boolean
	 * algebra
	 */
	String MATH_NOT = "¬";
	/*
	 * Date: 11 Dec 2022-----------------------------------------------------------
	 * Time created: 11:19:14---------------------------------------------------
	 */
	/**
	 * A <b>NOT</b> operator commonly used for inverting a boolean expression
	 */
	String BOOLEAN_NOT = "!";
	String PLUS = "+";
	String MINUS = "-";
	String ASTERISK = "*";
	String F_SLASH = "/";
	String OR = "or";
	String MATH_OR = "\u2228";
	String C_OR = "|";
	String AND = "and";
	String MATH_AND = "\u2227";
	String C_AND = "&";
	String XOR = "xor";
	String C_XOR = "^";
	String C_REM = "%";
	String C_LEFT_SHIFT = "<<";
	String C_RIGHT_SHIFT = ">>";

	// Function names

	String INT_CAST = "#";
	String FLOAT_CAST = "$";
	String ABS = "abs";
	String BIT_COUNT = "bitCount";
	String BIT_LENGTH = "bitLength";
	String CEIL = "ceil";
	String EXPONENT = "exponent";
	String FMA = "fma";
	String FLOOR = "floor";
	String FROM_INTEGER = "fromInteger";
	String GCD = "gcd";
	String IEEE_REM = "ieeeRem";
	String HIGH = "high";
	String LCM = "lcm";
	String LOW = "low";
	String MAX = "max";
	String MIN = "min";
	String MOD = "mod";
	String NAND = "nand";
	String NEXT_AFTER = "nextAfter";
	String NEXT_DOWN = "nextDown";
	String NEXT_UP = "nextUp";
	String NOR = "nor";
//	String REM = "rem";//No rem use % instead
	String RINT = "rint";
	String ROUND = "round";
	String SIGNIFICAND = "significand";
	String SIGNUM = "signum";
	String TO_INTEGER = "toInteger";
	String TRUNC = "trunc";
	String ULP = "ulp";
	String XNOR = "xnor";
}
