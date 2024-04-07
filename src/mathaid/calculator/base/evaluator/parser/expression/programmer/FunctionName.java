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
 * Interface consisting entirely of static fields that are identifiers for pre-defined functions and operators within the
 * underlying programmer CAS (Computer Algebra System).
 * 
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
	 * The {@code String} constant which serves as an identifier for the bitwise <b>NOT</b> operator (or function name).
	 */
	String NOT = "not";
	/*
	 * Date: 11 Dec 2022-----------------------------------------------------------
	 * Time created: 11:16:07---------------------------------------------------
	 */
	/**
	 * The {@code String} constant which serves as an identifier for the bitwise <b>NOT</b> operator used in C.
	 */
	String C_NOT = "~";
	/*
	 * Date: 11 Dec 2022-----------------------------------------------------------
	 * Time created: 11:14:54---------------------------------------------------
	 */
	/**
	 * The {@code String} constant which serves as an identifier for the logical <b>NOT</b> operator used in mathematics for logical
	 * boolean algebra
	 */
	String MATH_NOT = "¬";
	/*
	 * Date: 11 Dec 2022-----------------------------------------------------------
	 * Time created: 11:19:14---------------------------------------------------
	 */
	/**
	 * The {@code String} constant representing a <b>NOT</b> operator commonly used for inverting a boolean expression
	 */
	String BOOLEAN_NOT = "!";
	/**
	 * The {@code String} constant which serves as an identifier for the arithmetic add operation {@code +}.
	 */
	String PLUS = "+";
	/**
	 * The {@code String} constant which serves as an identifier for the arithmetic subtract operation {@code -}.
	 */
	String MINUS = "-";
	/**
	 * The {@code String} constant which serves as an identifier for the arithmetic multiply operation {@code *}.
	 */
	String ASTERISK = "*";
	/**
	 * The {@code String} constant which serves as an identifier for the arithmetic divide operation {@code /}.
	 */
	String F_SLASH = "/";
	/**
	 * The {@code String} constant which serves as an identifier for the bitwise <span style="font-weight:bold">OR</span> operator
	 * (or function name)
	 */
	String OR = "or";
	/**
	 * The {@code String} constant which serves as an identifier for the boolean logic <span style="font-weight:bold">OR</span>
	 * operator.
	 */
	String MATH_OR = "\u2228";
	/**
	 * The {@code String} constant which serves as an identifier for the C-style bitwise <span style="font-weight:bold">OR</span>
	 * operator.
	 */
	String C_OR = "|";
	/**
	 * The {@code String} constant which serves as an identifier for the bitwise <span style="font-weight:bold">AND</span> operator
	 * (or function name)
	 */
	String AND = "and";
	/**
	 * The {@code String} constant which serves as an identifier for the boolean logic <span style="font-weight:bold">AND</span>
	 * operator.
	 */
	String MATH_AND = "\u2227";
	/**
	 * The {@code String} constant which serves as an identifier for the C-style bitwise <span style="font-weight:bold">AND</span>
	 * operator.
	 */
	String C_AND = "&";
	/**
	 * The {@code String} constant which serves as an identifier for the bitwise <span style="font-weight:bold">XOR</span> operator
	 * (or function name)
	 */
	String XOR = "xor";
	/**
	 * The {@code String} constant which serves as an identifier for the C-style bitwise <span style="font-weight:bold">XOR</span>
	 * operator.
	 */
	String C_XOR = "^";
	/**
	 * The {@code String} constant which serves as an identifier for the C-style bitwise <span style="font-weight:bold">%</span>
	 * operator for integer remainder operations.
	 */
	String C_REM = "%";
	/**
	 * The {@code String} constant which serves as an identifier for the C-style bitwise left-shift operator.
	 */
	String C_LEFT_SHIFT = "<<";
	/**
	 * The {@code String} constant which serves as an identifier for the C-style bitwise right-shift operator.
	 */
	String C_RIGHT_SHIFT = ">>";
	/**
	 * The {@code String} constant which serves as an identifier for the C-style increment operator.
	 */
	String C_INCREMENT = "++";
	/**
	 * The {@code String} constant which serves as an identifier for the C-style decrement operator.
	 */
	String C_DECREMENT = "--";

	// Function names

	/**
	 * The {@code String} constant which serves as an identifier for unary operator for casting a value to an integer.
	 */
	String INT_CAST = "#";
	/**
	 * The {@code String} constant which serves as an identifier for unary operator for casting a value to a floating-point.
	 */
	String FLOAT_CAST = "$";
	/**
	 * The {@code String} constant which serves as an identifier for the absolute function.
	 */
	String ABS = "abs";
	/**
	 * The {@code String} constant which serves as an identifier for the function that counts the number of bits that are"on" in an
	 * integer.
	 */
	String BIT_COUNT = "bitCount";
	/**
	 * The {@code String} constant which serves as an identifier for the function that counts the number of bits in an integer.
	 */
	String BIT_LENGTH = "bitLength";
	/**
	 * The {@code String} constant which serves as an identifier for the ceiling function for a floating-point.
	 */
	String CEIL = "ceil";
	/**
	 * The {@code String} constant which serves as an identifier for the exponent function that retrieves the exponent of a IEEE754
	 * floating-point.
	 */
	String EXPONENT = "exponent";
	/**
	 * The {@code String} constant which serves as an identifier for the exponent function that performs fused-multiply-add on a
	 * floating-point.
	 */
	String FMA = "fma";
	/**
	 * The {@code String} constant which serves as an identifier for the floor function for a floating-point.
	 */
	String FLOOR = "floor";
	/**
	 * The {@code String} constant which serves as an identifier for the function that converts bits into an IEEE754 floating-point.
	 */
	String FROM_INTEGER = "fromInteger";
	/**
	 * The {@code String} constant which serves as an identifier for the
	 * <span style="font-weight:bold">GCD</span>/<span style="font-weight:bold">HCF</span> function.
	 */
	String GCD = "gcd";
	/**
	 * The {@code String} constant which serves as an identifier for the function that calculates the remainder between 2 IEEE754
	 * floating-point.
	 */
	String IEEE_REM = "ieeeRem";
	/**
	 * The {@code String} constant which serves as an identifier for the function that retrieves the most significant (high) bits of
	 * an integer.
	 */
	String HIGH = "high";
	/**
	 * The {@code String} constant which serves as an identifier for the <span style="font-weight:bold">LCM</span> function.
	 */
	String LCM = "lcm";
	/**
	 * The {@code String} constant which serves as an identifier for the function that retrieves the least significant (low) bits of
	 * an integer.
	 */
	String LOW = "low";
	/**
	 * The {@code String} constant which serves as an identifier for the max function.
	 */
	String MAX = "max";
	/**
	 * The {@code String} constant which serves as an identifier for the min function.
	 */
	String MIN = "min";
	/**
	 * The {@code String} constant which serves as an identifier for the mod function.
	 */
	String MOD = "mod";
	/**
	 * The {@code String} constant which serves as an identifier for the bitwise <span style="font-weight:bold">NAND</span> operator
	 * (or function name)
	 */
	String NAND = "nand";
	/**
	 * The {@code String} constant which serves as an identifier for the {@linkplain Math#nextAfter(double, double) next-after}
	 * function for a floating-point.
	 */
	String NEXT_AFTER = "nextAfter";
	/**
	 * The {@code String} constant which serves as an identifier for the {@linkplain Math#nextDown(double) next-down} function for a
	 * floating-point.
	 */
	String NEXT_DOWN = "nextDown";
	/**
	 * The {@code String} constant which serves as an identifier for the {@linkplain Math#nextUp(double) next-up} function for a
	 * floating-point.
	 */
	String NEXT_UP = "nextUp";
	/**
	 * The {@code String} constant which serves as an identifier for the bitwise <span style="font-weight:bold">NOR</span> operator
	 * (or function name)
	 */
	String NOR = "nor";
	/**
	 * The {@code String} constant which serves as an identifier for the function name of a bitwise function that calculates the bit
	 * a given index in a bit pattern. It uses the format <code>nth(BigInteger i, int n)</code>
	 */
	String NTH = "nth";
	//String REM = "rem";//No rem use % instead
	/**
	 * The {@code String} constant which serves as an identifier for the function name of a bitwise function that calculates a bit
	 * pattern where all bits are 'on' and has the same count as the specified value. It's format is <code>on(int n)</code>
	 */
	String ON = "on";
	/**
	 * The {@code String} constant which serves as an identifier for the {@linkplain Math#rint(double) rint} function for a
	 * floating-point.
	 */
	String RINT = "rint";
	/**
	 * The {@code String} constant which serves as an identifier for the {@linkplain Math#round(double) round} function for a
	 * floating-point.
	 */
	String ROUND = "round";
	/**
	 * The {@code String} constant which serves as an identifier for the significand function that retrieves the significand of a
	 * IEEE754 floating-point.
	 */
	String SIGNIFICAND = "significand";
	/**
	 * The {@code String} constant which serves as an identifier for the sign/signum function.
	 */
	String SIGNUM = "signum";
	/**
	 * The {@code String} constant which serves as an identifier for the function that converts an IEEE754 floating-point into
	 * integer bits.
	 */
	String TO_INTEGER = "toInteger";
	/**
	 * The {@code String} constant which serves as an identifier for the truncate function for a floating-point.
	 */
	String TRUNC = "trunc";
	/**
	 * The {@code String} constant which serves as an identifier for the <span style="font-weight:bold">ULP</span>
	 * (Unit-in-last-place) function for a floating-point.
	 */
	String ULP = "ulp";
	/**
	 * The {@code String} constant which serves as an identifier for the bitwise <span style="font-weight:bold">XNOR</span> operator
	 * (or function name)
	 */
	String XNOR = "xnor";
}
