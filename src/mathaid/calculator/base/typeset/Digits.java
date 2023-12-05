/**
 * 
 */
package mathaid.calculator.base.typeset;

import static mathaid.calculator.base.typeset.Segment.Type.ARC;
import static mathaid.calculator.base.typeset.Segment.Type.CONSTANT;
import static mathaid.calculator.base.typeset.Segment.Type.DOT;
import static mathaid.calculator.base.typeset.Segment.Type.DOT_ALL;
import static mathaid.calculator.base.typeset.Segment.Type.DOT_BAR;
import static mathaid.calculator.base.typeset.Segment.Type.ELLIPSIS;
import static mathaid.calculator.base.typeset.Segment.Type.EXPONENT;
import static mathaid.calculator.base.typeset.Segment.Type.FRACTION;
import static mathaid.calculator.base.typeset.Segment.Type.INTEGER;
import static mathaid.calculator.base.typeset.Segment.Type.MANTISSA;
import static mathaid.calculator.base.typeset.Segment.Type.M_FRACTION;
import static mathaid.calculator.base.typeset.Segment.Type.OBJECT;
import static mathaid.calculator.base.typeset.Segment.Type.OPERATOR;
import static mathaid.calculator.base.typeset.Segment.Type.PARENTHESISED;
import static mathaid.calculator.base.typeset.Segment.Type.POINT;
import static mathaid.calculator.base.typeset.Segment.Type.PREFIX_MINUS;
import static mathaid.calculator.base.typeset.Segment.Type.PREFIX_PLUS;
import static mathaid.calculator.base.typeset.Segment.Type.UNIT;
import static mathaid.calculator.base.typeset.Segment.Type.VINCULUM;
import static mathaid.calculator.base.util.Utility.d;
import static mathaid.calculator.base.util.Utility.formatAsStandardForm;
import static mathaid.calculator.base.util.Utility.fromDecimal;
import static mathaid.calculator.base.util.Utility.mc;
import static mathaid.calculator.base.util.Utility.toEngineeringString;
import static mathaid.calculator.base.value.FloatAid.isNumber;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mathaid.calculator.base.value.BigFraction;
import mathaid.calculator.base.value.BinaryFPPrecision.BinaryFP;

/*
 * Date: 13 Nov 2023 -----------------------------------------------------------
 * Time created: 10:39:35 ---------------------------------------------------
 * Package: mathaid.calculator.base.typeset ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: Digits.java ------------------------------------------------------
 * Class name: Digits ------------------------------------------------
 */
/**
 * Set of static methods that help construct proper {@link Digit} objects which are commonly found in TeX and MathJax.
 * <p>
 * Although passing <code>null</code> to any of the method expecting a {@code LinkedSegment} argument will not throw an
 * exception, editing the tree afterwards may cause an exception. it is recommended that the {@link Empty} object be used
 * instead of <code>null</code>. For example, rather than do:
 * 
 * <pre>
 *	<code>
 *		LinkedSegment ls = Digits.decimalExponent(null);
 *		// ... use LinkedSegment here
 *	</code>
 * </pre>
 * 
 * do the following instead:
 * 
 * <pre>
 *	<code>
 *		String placeholder = ...
 *		LinkedSegment ls = Digits.decimalExponent(new Empty(placeholder));
 *		// ... use LinkedSegment here
 *	</code>
 * </pre>
 * 
 * This way, no exception will result from calling methods of the tree. All other argument types must be non-null else a
 * {@code NullPointerException} or {@code IndexOutOfBoundsException} will be thrown.
 * <p>
 * The terms segment, tree, sub-tree, subtree, node, nodes, segments all refer to an instance of the {@code LinkedSegment}
 * interface. THe terms number node, number nodes, number tree, number trees, number sub-tree, number sub-trees, all refer to a
 * {@code LinkedSegment} instance where all the nodes are an instance of the {@code Digit} class.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public final class Digits {

	/*
	 * Date: 13 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:41:03 ---------------------------------------------------
	 */
	/**
	 * Constructs a {@code LinkedSegment} for the unary operator '+'. The value that this method returns, does not have a sibling or
	 * child.
	 * <p>
	 * <strong>The TeX format</strong> (specified by {@link LinkedSegment#format(Appendable, Formatter, java.util.List)}) is:
	 * 
	 * <pre>
	 * <code>\textrm{+}</code>
	 * </pre>
	 * 
	 * This is also the image of {@code LinkedSegment} returned.
	 * <p>
	 * <strong>The expression value</strong> (specified by {@code LinkedSegment.toString(Appendable, Log, java.util.List)}) is:
	 * 
	 * <pre>
	 * <code>+</code>
	 * </pre>
	 * 
	 * This is also the expression of {@code LinkedSegment} returned.
	 * <p>
	 * This uses the {@link #OPERATOR} type.
	 * 
	 * @return a {@code LinkedSegment} for the unary operator {@code +}.
	 */
	public static LinkedSegment prefixPlus() {
		return new BasicSegment("\\textrm{+}", "+", PREFIX_PLUS);
	}

	/*
	 * Date: 13 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:41:03 ---------------------------------------------------
	 */
	/**
	 * Constructs a {@code LinkedSegment} for the unary operator '-', which usually precedes a number. The value that this method
	 * returns, does not have a sibling or child.
	 * <p>
	 * <strong>The TeX format</strong> (specified by {@link LinkedSegment#format(Appendable, Formatter, java.util.List)}) is:
	 * 
	 * <pre>
	 * <code>\textrm{-}</code>
	 * </pre>
	 * 
	 * This is also the image of {@code LinkedSegment} returned.
	 * <p>
	 * <strong>The SYMJA expression value</strong> (specified by {@code LinkedSegment.toString(Appendable, Log, java.util.List)})
	 * is:
	 * 
	 * <pre>
	 * <code>-</code>
	 * </pre>
	 * 
	 * This is also the expression of {@code LinkedSegment} returned.
	 * <p>
	 * This uses the {@link #OPERATOR} type.
	 * 
	 * @return a {@code LinkedSegment} for the unary operator {@code -}.
	 */
	public static LinkedSegment prefixMinus() {
		return new BasicSegment("\\textrm{-}", "-", PREFIX_MINUS);
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:52:17 ---------------------------------------------------
	 */
	/**
	 * Creates the plus and minus {@code Segment} using the argument as the expression value to return whenever
	 * {@link LinkedSegment#toString(Appendable, Log, java.util.List)} is invoked. The value that this method returns, does not have
	 * a sibling or child.
	 * <p>
	 * <strong>The TeX format</strong> (specified by {@link LinkedSegment#format(Appendable, Formatter, java.util.List)}) is:
	 * 
	 * <pre>
	 * <code>\pm</code>
	 * </pre>
	 * 
	 * This is also the image of {@code LinkedSegment} returned.
	 * <p>
	 * <strong>The expression value</strong> (specified by {@code LinkedSegment.toString(Appendable, Log, java.util.List)}) is any
	 * valid {@code String} that can have the same mathematical function as the '<code>&pm;</code>' operator in the SYMJA engine.
	 * <p>
	 * This uses the {@link #OPERATOR} type.
	 * 
	 * @param expression the expression to use as a representative of the '<code>&pm;</code>' operator in the evaluation string that
	 *                   will be built using {@code LinkedSegment.toString(Appendable, Log, java.util.List)}.
	 * @return a {@code LinkedSegment} that represents the unary plus-and-minus operator.
	 */
	public static LinkedSegment plusMinus(String expression) {
		return new BasicSegment("\\pm", expression, OPERATOR);
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:50:21 ---------------------------------------------------
	 */
	/**
	 * Calls {@link #plusMinus(String)} using {@code "-"} as the argument so as to allow the SYMJA evaluation engine to evaluate
	 * it's operand sibling properly. The value that this method returns, does not have a sibling or child.
	 * <p>
	 * This uses the {@link #OPERATOR} type.
	 * 
	 * @return a {@code LinkedSegment} that represents the unary plus-and-minus operator.
	 */
	public static LinkedSegment pm() {
		return plusMinus("-");
	}

	/*
	 * Date: 13 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:44:09 ---------------------------------------------------
	 */
	/**
	 * Creates an integer {@code Digit} from the character {@code d} which is assumed to be the digit of a radix-based number, using
	 * the second argument for adding relevant punctuation such as the separator. Note that this method does not check for the radix
	 * of the character. The value that this method returns, does not have a sibling or child.
	 * <p>
	 * This uses the {@link #INTEGER} type.
	 * 
	 * @param d  the character from which the {@code Digit} will be created.
	 * @param dp the {@code DigitPunc} object which provides adequate punctuation options for the {@code LinkedSegment} created.
	 * @return a {@code Digit} that was created from the arguments.
	 */
	public static Digit integer(char d, DigitPunc dp) {
		return new Digit(d, dp);
	}

	/*
	 * Date: 13 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:44:09 ---------------------------------------------------
	 */
	/**
	 * Creates a mantissa {@code Digit} from the character {@code d} which is assumed to be the digit of a radix-based number, using
	 * the second argument for adding relevant punctuation such as the separator. Note that this method does not check for the radix
	 * of the character. The digit created is one that will reside at the mantissa portion of a number. This digit can have
	 * recurring characters. The value that this method returns, does not have a sibling or child.
	 * <p>
	 * This uses the {@link #MANTISSA} type.
	 * 
	 * @param d  the character from which the {@code Digit} will be created.
	 * @param dp the {@code DigitPunc} object which provides adequate punctuation options for the {@code LinkedSegment} created.
	 * @return a {@code Digit} that was created from the arguments.
	 */
	public static Digit mantissa(char d, DigitPunc dp) {
		return new Digit(d, MANTISSA, dp);
	}

	/**
	 * Creates a {@code LinkedSegment} that represents a radix point that separates the integer from the mantissa. The value that
	 * this method returns, does not have a sibling or child.
	 * <p>
	 * This uses the {@link #POINT} type.
	 * 
	 * @param p the character from which the {@code LinkedSegment} will be created.
	 * @returns the argument as a {@code LinkedSegment}.
	 */
	public static LinkedSegment point(char p) {
		return new BasicSegment(String.valueOf(p), POINT);
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:01:21 ---------------------------------------------------
	 */
	/**
	 * Creates a {@code Digit} which is a recurring variant whose type is {@link #ARC}. The value that this method returns, does not
	 * have a sibling or child.
	 * <p>
	 * <strong>The TeX format</strong> (specified by {@link LinkedSegment#format(Appendable, Formatter, java.util.List)}) is:
	 * 
	 * <pre>
	 * <code>\overparen{<span style="color:gray;">d</span>}</code>
	 * </pre>
	 * 
	 * This is also the image of {@code LinkedSegment} returned.
	 * <p>
	 * <strong>The expression value</strong> (specified by {@code LinkedSegment.toString(Appendable, Log, java.util.List)}) is:
	 * 
	 * <pre>
	 * <code><span style="color:gray;">d</span></code>
	 * </pre>
	 * 
	 * Where {@code d} stands for the digit represented.
	 * 
	 * @param d  the image of the digit i.e the actual digit expression value.
	 * @param dp the value where punctuating options will be retrieved.
	 * @return an {@code ARC} type of recurring {@code Digit}.
	 */
	public static Digit arc(char d, DigitPunc dp) {
		return new Digit(d, ARC, dp);
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:04:37 ---------------------------------------------------
	 */
	/**
	 * /** Creates a {@code Digit} which is a recurring variant whose type is {@link #DOT}. The value that this method returns, does
	 * not have a sibling or child.
	 * <p>
	 * <strong>The TeX format</strong> (specified by {@link LinkedSegment#format(Appendable, Formatter, java.util.List)}) is:
	 * 
	 * <pre>
	 * <code>\dot{<span style="color:gray;">d</span>}</code>
	 * </pre>
	 * 
	 * This is also the image of {@code LinkedSegment} returned.
	 * <p>
	 * <strong>The expression value</strong> (specified by {@code LinkedSegment.toString(Appendable, Log, java.util.List)}) is:
	 * 
	 * <pre>
	 * <code><span style="color:gray;">d</span></code>
	 * </pre>
	 * 
	 * Where {@code d} stands for the digit represented.
	 * 
	 * @param d  the image of the digit i.e the actual digit expression value.
	 * @param dp the value where punctuating options will be retrieved.
	 * @return an {@code DOT} type of recurring {@code Digit}.
	 */
	public static Digit dot(char d, DigitPunc dp) {
		return new Digit(d, DOT, dp);
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:04:37 ---------------------------------------------------
	 */
	/**
	 * /** Creates a {@code Digit} which is a recurring variant whose type is {@link #DOT_ALL}. The value that this method returns,
	 * does not have a sibling or child.
	 * <p>
	 * <strong>The TeX format</strong> (specified by {@link LinkedSegment#format(Appendable, Formatter, java.util.List)}) is:
	 * 
	 * <pre>
	 * <code>\dot{<span style="color:gray;">d</span>}</code>
	 * </pre>
	 * 
	 * This is also the image of {@code LinkedSegment} returned.
	 * <p>
	 * <strong>The expression value</strong> (specified by {@code LinkedSegment.toString(Appendable, Log, java.util.List)}) is:
	 * 
	 * <pre>
	 * <code><span style="color:gray;">d</span></code>
	 * </pre>
	 * 
	 * Where {@code d} stands for the digit represented.
	 * 
	 * @param d  the image of the digit i.e the actual digit expression value.
	 * @param dp the value where punctuating options will be retrieved.
	 * @return an {@code DOT_ALL} type of recurring {@code Digit}.
	 */
	public static Digit dotAll(char d, DigitPunc dp) {
		return new Digit(d, DOT_ALL, dp);
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:04:37 ---------------------------------------------------
	 */
	/**
	 * /** Creates a {@code Digit} which is a recurring variant whose type is {@link #DOT_BAR}. The value that this method returns,
	 * does not have a sibling or child.
	 * 
	 * @param d  the image of the digit i.e the actual digit expression value.
	 * @param dp the value where punctuating options will be retrieved.
	 * @return an {@code DOT_BAR} type of recurring {@code Digit}.
	 */
	public static Digit dotBar(char d, DigitPunc dp) {
		return new Digit(d, DOT_BAR, dp);
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:04:37 ---------------------------------------------------
	 */
	/**
	 * /** Creates a {@code Digit} which is a recurring variant whose type is {@link #ELLIPSIS}. The value that this method returns,
	 * does not have a sibling or child.
	 * <p>
	 * <strong>The TeX format</strong> (specified by {@link LinkedSegment#format(Appendable, Formatter, java.util.List)}) is:
	 * 
	 * <pre>
	 * <code><span style="color:gray;">d</span>\ldots</code>
	 * </pre>
	 * 
	 * <code><span style="color:gray;">d</span></code> will be repeated as many times as specified by
	 * {@link DigitPunc#getNumOfRecurringDigits()}. This is also the image of {@code LinkedSegment} returned.
	 * <p>
	 * <strong>The expression value</strong> (specified by {@code LinkedSegment.toString(Appendable, Log, java.util.List)}) is:
	 * 
	 * <pre>
	 * <code><span style="color:gray;">d</span></code>
	 * </pre>
	 * 
	 * Where {@code d} stands for the digit represented.
	 * 
	 * @param d  the image of the digit i.e the actual digit expression value.
	 * @param dp the value where punctuating options will be retrieved.
	 * @return an {@code ELLIPSIS} type of recurring {@code Digit}.
	 */
	public static Digit ellipsis(char d, DigitPunc dp) {
		return new Digit(d, ELLIPSIS, dp);
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:04:37 ---------------------------------------------------
	 */
	/**
	 * /** Creates a {@code Digit} which is a recurring variant whose type is {@link #PARENTHESISED}. The value that this method
	 * returns, does not have a sibling or child.
	 * 
	 * <pre>
	 * <code>(<span style="color:gray;">d</span>)</code>
	 * </pre>
	 * 
	 * This is also the image of {@code LinkedSegment} returned.
	 * <p>
	 * <strong>The expression value</strong> (specified by {@code LinkedSegment.toString(Appendable, Log, java.util.List)}) is:
	 * 
	 * <pre>
	 * <code><span style="color:gray;">d</span></code>
	 * </pre>
	 * 
	 * Where {@code d} stands for the digit represented.
	 * 
	 * @param d  the image of the digit i.e the actual digit expression value.
	 * @param dp the value where punctuating options will be retrieved.
	 * @return an {@code DOT} type of recurring {@code Digit}.
	 */
	public static Digit parenthesis(char d, DigitPunc dp) {
		return new Digit(d, PARENTHESISED, dp);
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:04:37 ---------------------------------------------------
	 */
	/**
	 * /** Creates a {@code Digit} which is a recurring variant whose type is {@link #VINCULLUM}. The value that this method
	 * returns, does not have a sibling or child.
	 * 
	 * <pre>
	 * <code>\overline{<span style="color:gray;">d</span>}</code>
	 * </pre>
	 * 
	 * This is also the image of {@code LinkedSegment} returned.
	 * <p>
	 * <strong>The expression value</strong> (specified by {@code LinkedSegment.toString(Appendable, Log, java.util.List)}) is:
	 * 
	 * <pre>
	 * <code><span style="color:gray;">d</span></code>
	 * </pre>
	 * 
	 * Where {@code d} stands for the digit represented.
	 * 
	 * @param d  the image of the digit i.e the actual digit expression value.
	 * @param dp the value where punctuating options will be retrieved.
	 * @return an {@code VINCULLUM} type of recurring {@code Digit}.
	 */
	public static Digit vinculum(char d, DigitPunc dp) {
		return new Digit(d, VINCULUM, dp);
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:13:43 ---------------------------------------------------
	 */
	/**
	 * Creates a {@code LinkedSegment} that is {@code 10} to the power of the argument. Proper for decimals in standard form such as
	 * 1.234E567 which will represent 1.234 &times; 10<sup>567</sup>, in this method, {@code 567} is the argument to this function.
	 * The value that this method returns, does not have a sibling.
	 * <p>
	 * <strong>The TeX format</strong> (specified by {@link LinkedSegment#format(Appendable, Formatter, java.util.List)}) is:
	 * 
	 * <pre>
	 * <code>\times 10^{<span style="color:gray">power</span>}</code>
	 * </pre>
	 * 
	 * This is also the image of {@code LinkedSegment} returned.
	 * <p>
	 * <strong>The expression value</strong> (specified by {@code LinkedSegment.toString(Appendable, Log, java.util.List)}) is:
	 * 
	 * <pre>
	 * <code>e<span style="color:gray">power</span></code>
	 * </pre>
	 * 
	 * This is also the expression of {@code LinkedSegment} returned.
	 * <p>
	 * This uses the {@link #EXPONENT} type.
	 * 
	 * @param power the exponent which will be the child of the returned value.
	 * @return a {@code LinkedSegment} that is meant to represent the scientific notation part of a number tree.
	 */
	public static LinkedSegment decimalExponent(LinkedSegment power) {
		String[] f = { " \\times 10^{", "}" };
		String[] s = { "e", "" };
		return new CompositeSegment(f, s, EXPONENT, new LinkedSegment[] { power }, 0, -1);
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:24:17 ---------------------------------------------------
	 */
	/**
	 * Gets the childless {@code LinkedSegment} used for radix-based scientific notations. The value that this method returns, does
	 * not have a sibling or child.
	 * <p>
	 * <strong>The TeX format</strong> (specified by {@link LinkedSegment#format(Appendable, Formatter, java.util.List)}) is:
	 * 
	 * <pre>
	 * <code>\mathrm{p}</code>
	 * </pre>
	 * 
	 * This is also the image of {@code LinkedSegment} returned.
	 * <p>
	 * <strong>The expression value</strong> (specified by {@code LinkedSegment.toString(Appendable, Log, java.util.List)}) is:
	 * 
	 * <pre>
	 * <code>p</code>
	 * </pre>
	 * <p>
	 * Note that when the argument is {@code false}, then the 'p' (for C-style floating point exponents) characters from above are
	 * both are replaced by 'e'. This is also the expression of {@code LinkedSegment} returned.
	 * <p>
	 * This uses the {@link #EXPONENT} type.
	 * 
	 * @param binary if {@code true} then 'p' will be returned else 'e'.
	 * @return a {@code LinkedSegment} that is meant to represent the scientific notation part of a number tree.
	 */
	// floating-point exponent
	public static LinkedSegment radixExponent(boolean binary) {
		if (!binary)
			return new BasicSegment("\\mathrm{e}", "e", EXPONENT);
		return new BasicSegment("\\mathrm{p}", "p", EXPONENT);
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:30:52 ---------------------------------------------------
	 */
	/**
	 * Transforms the {@code BigInteger} and creates the corresponding {@code LinkedSegment} number tree meant to represent an
	 * integer in the given radix, where each digit will have the {@link #INTEGER} type and each will have it's sibling be the next
	 * digit following it. The digits used for the radix are the same used by {@link BigInteger#toString(int)}. An example is:
	 * 
	 * <pre>
	 * <code>
	 *	BigInteger n = new BigInteger("1234567890");
	 *	LinkedSegment num = Digits.toSegment(n, 8, new DigitPunc());
	 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
	 *	num.toString(System.out, null, l);//prints: 11145401322
	 *	out.println();
	 *	l.clear();
	 *	l.add(-1);
	 *	num.format(System.out, Formatter.empty(), l);// prints: 11,145,401,322
	 * </code>
	 * </pre>
	 * 
	 * @param n     the value to be transformed into a {@code LinkedSegment}.
	 * @param radix the specified radix of the returned value.
	 * @param dp    the punctuation options to apply to the generated number tree.
	 * @return a number tree whose nodes are digits in the given radix.
	 */
	public static LinkedSegment toSegment(BigInteger n, int radix, DigitPunc dp) {
		String str = n.toString(radix).toUpperCase();
		if (n.signum() < 0)
			return unsignedDigits(new StringBuilder(str.substring(1)), INTEGER, prefixMinus(), dp);
		else if (str.charAt(0) == '+')
			return unsignedDigits(new StringBuilder(str.substring(1)), INTEGER, new Empty(), dp);
		return unsignedDigits(new StringBuilder(str), INTEGER, new Empty(), dp);
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 16:07:14 ---------------------------------------------------
	 */
	/**
	 * Generates a number tree using the characters in the given {@code StringBuilder} whereby each digit's type is specified by the
	 * {@code int} argument, the head of the returned tree is the {@code LinkedSegment} argument and each node represents it
	 * corresponding character in the {@code StringBuilder] such that adjacent characters are siblings of one another}
	 * 
	 * @param n  the buffer holding the digits to be generated.
	 * @param t  the type which each node will have in the number tree.
	 * @param s  the value to which the generated number will be concatenated.
	 * @param dp the punctuation options to apply to the generated number tree.
	 * @return a number tree generated from the given {@code StringBuilder} where the nodes represent the characters of the
	 *         {@code StringBuilder}.
	 */
	private static LinkedSegment unsignedDigits(StringBuilder n, int t, LinkedSegment s, DigitPunc dp) {
		if (n.length() > 0) {
			char d = n.charAt(0);
			return unsignedDigits(n.deleteCharAt(0), t, s.concat(new Digit(d, t, dp)), dp);
		}
		return s;
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 16:18:23 ---------------------------------------------------
	 */
	/**
	 * Transforms the {@code BigDecimal} and creates the corresponding {@code LinkedSegment} number tree meant to represent a
	 * decimal in scientific notation (or just the significand if the {@code int} argument is less than one).
	 * <p>
	 * If argument is signed then the head will be the the sign. Each digit is a node and adjacent digits are siblings. All the
	 * nodes at integer part will the {@link #INTEGER} type, the decimal point will have the {@link #POINT} type, the nodes at the
	 * mantissa part will have the {@link #MANTISSA} type and the nodes at the exponent part (if specified) will have the
	 * {@link #EXPONENT} type. An example is:
	 * 
	 * <pre>
	 * <code>
	 *	BigDecimal n = d("123456.78901234");
	 *	LinkedSegment num = Digits.toSegment(n, 1, new DigitPunc());
	 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
	 *	num.toString(System.out, null, l);//prints 1.2345678901234e5
	 *	out.println();
	 *	l.clear();
	 *	l.add(-1);
	 *	num.format(System.out, Formatter.empty(), l);// prints: 1.234\,567\,890\,123\,4 \times 10^{5}
	 * </code>
	 * </pre>
	 * 
	 * @param n   the value to be transformed into a {@code LinkedSegment}.
	 * @param exp factor of the scientific exponent to use. E.g engineering notation can use {@code 3} here while the common
	 *            scientific notations can use {@code 1}. Use {@code -1} if no scientific notation is desired.
	 * @param dp  the punctuation options to apply to the generated number tree.
	 * @return a number tree whose nodes are the digits in the given {@code BigDecimal}.
	 * @see mathaid.calculator.base.util.Utility#formatAsStandardForm(BigDecimal, int)
	 */
	public static LinkedSegment toSegment(BigDecimal n, int exp, DigitPunc dp) {
		if (exp < 1) {
			String s = n.toPlainString();
			if (s.charAt(0) == '+') {
				if (s.indexOf('.') >= 0)
					return unsignedMantissa(new StringBuilder(s.substring(1)), new Empty(), dp);
				return unsignedDigits(new StringBuilder(s.substring(1)), INTEGER, new Empty(), dp);
			} else if (s.charAt(0) == '-') {
				if (s.indexOf('.') >= 0)
					return unsignedMantissa(new StringBuilder(s.substring(1)), prefixMinus(), dp);
				return unsignedDigits(new StringBuilder(s.substring(1)), INTEGER, new Empty(), dp);
			} else if (s.indexOf('.') >= 0)
				return unsignedMantissa(new StringBuilder(s.substring(0)), new Empty(), dp);
			return unsignedDigits(new StringBuilder(s.substring(1)), INTEGER, new Empty(), dp);
		}
		final String s = formatAsStandardForm(n, exp).toUpperCase();
		final int ioe = s.indexOf('E');// index of exponent
		final String e = s.substring(ioe + 1, s.length());
		final LinkedSegment h, expn;
		if (s.charAt(0) == '+') {
			h = (s.indexOf('.') >= 0) ? unsignedMantissa(new StringBuilder(s.substring(1, ioe)), prefixPlus(), dp)
					: unsignedDigits(new StringBuilder(s.substring(1, ioe)), INTEGER, prefixPlus(), dp);
		} else if (s.charAt(0) == '-') {
			h = (s.indexOf('.') >= 0) ? unsignedMantissa(new StringBuilder(s.substring(1, ioe)), prefixMinus(), dp)
					: unsignedDigits(new StringBuilder(s.substring(1, ioe)), INTEGER, prefixMinus(), dp);
		} else
			h = (s.indexOf('.') >= 0) ? unsignedMantissa(new StringBuilder(s.substring(0, ioe)), new Empty(), dp)
					: unsignedDigits(new StringBuilder(s.substring(0, ioe)), INTEGER, new Empty(), dp);
		expn = e.charAt(0) == '-' ? unsignedDigits(new StringBuilder(e.substring(1)), INTEGER, prefixMinus(), dp)
				: unsignedDigits(new StringBuilder(e.substring(e.charAt(0) == '+' ? 1 : 0)), INTEGER, new Empty(), dp);
		return h.concat(decimalExponent(expn));
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 16:40:24 ---------------------------------------------------
	 */
	/**
	 * Transforms the {@code BigDecimal} and creates the corresponding {@code LinkedSegment} number tree meant to represent a
	 * decimal in engineering notation. If computed value fits into values that can be represented as by engineering suffixes, then
	 * an engineering suffix is used if the {@code boolean} argument is {@code true}, a numerical exponent is used instead. An
	 * example is:
	 * 
	 * <pre>
	 * <code>
	 *	BigDecimal n = d("123456.78901234");
	 *	LinkedSegment num = Digits.toSegment(n, true, new DigitPunc());
	 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
	 *	num.toString(System.out, null, l);//prints: 123.45678901234E3
	 *	out.println();
	 *	l.clear();
	 *	l.add(-1);
	 *	num.format(System.out, Formatter.empty(), l);// prints: 123.456\,789\,012\,34k
	 * </code>
	 * </pre>
	 * <p>
	 * If argument is signed then the head will be the the sign. Each digit is a node and adjacent digits are siblings. All the
	 * nodes at integer part will the {@link #INTEGER} type, the decimal point will have the {@link #POINT} type, the nodes at the
	 * mantissa part will have the {@link #MANTISSA} type and the node at the exponent will have the {@link #UNIT} type.
	 * 
	 * @param n         the value to be transformed into a {@code LinkedSegment}.
	 * @param useSuffix a {@code boolean} value specifying whether to use engineering suffixes such as &#x03bc;, M, y or use the
	 *                  numerical exponent which are in factors of {@code 3}. Even if this value is {@code true} the computed
	 *                  engineering exponent value must fall into the range of [-24, 24] for the suffixes to be appended.
	 * @param dp        the punctuation options to apply to the generated number tree.
	 * @return a number tree whose nodes are digits in the given {@code BigDecimal} using the computed engineering suffix.
	 * @see mathaid.calculator.base.util.Utility#toEngineeringString(BigDecimal, boolean)
	 */
	public static LinkedSegment toSegment(BigDecimal n, boolean useSuffix, DigitPunc dp) {
		String s = toEngineeringString(n, useSuffix);
		int ioe = s.indexOf('E');// index of exponent
		String e = Character.isDigit(s.charAt(s.length() - 1)) ? s.substring((ioe) + 1, s.length()) : "";
		LinkedSegment h, exp;
		if (s.charAt(0) == '+') {
			h = (s.indexOf('.') >= 0)
					? unsignedMantissa(new StringBuilder(s.substring(1, ioe < 0 ? s.length() - 1 : ioe)), prefixPlus(),
							dp)
					: unsignedDigits(new StringBuilder(s.substring(1, ioe < 0 ? s.length() - 1 : ioe)), INTEGER,
							prefixPlus(), dp);
		} else if (s.charAt(0) == '-') {
			h = (s.indexOf('.') >= 0)
					? unsignedMantissa(new StringBuilder(s.substring(1, ioe < 0 ? s.length() - 1 : ioe)), prefixMinus(),
							dp)
					: unsignedDigits(new StringBuilder(s.substring(1, ioe < 0 ? s.length() - 1 : ioe)), INTEGER,
							prefixMinus(), dp);
		} else
			h = (s.indexOf('.') >= 0)
					? unsignedMantissa(new StringBuilder(s.substring(0, ioe < 0 ? s.length() - 1 : ioe)), new Empty(),
							dp)
					: unsignedDigits(new StringBuilder(s.substring(0, ioe < 0 ? s.length() - 1 : ioe)), INTEGER,
							new Empty(), dp);
		if (!e.isEmpty()) {
			exp = e.charAt(0) == '-' ? unsignedDigits(new StringBuilder(e.substring(1)), INTEGER, prefixMinus(), dp)
					: unsignedDigits(new StringBuilder(e.substring(e.charAt(0) == '+' ? 1 : 0)), INTEGER, new Empty(),
							dp);
			return h.concat(decimalExponent(exp));
		}
//		exp = new BasicSegment(s.charAt(s.length() - 1) != 'µ' ? s.charAt(s.length() - 1) + "" : "\\mu",
		exp = new BasicSegment(s.charAt(s.length() - 1) != '\u03BC' ? s.charAt(s.length() - 1) + "" : "\\mu",
				getSuffixExpOrDefault(s.substring(s.length() - 1), ""), UNIT);
		return h.concat(exp);
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 16:59:45 ---------------------------------------------------
	 */
	/**
	 * Computes the engineering exponent using the first argument and returning the seconding argument only if the first is not a
	 * supported exponent value.
	 * <p>
	 * See {@link mathaid.calculator.base.util.Utility#toEngineeringString(BigDecimal, boolean)} for supported values.
	 * 
	 * @param suffix the suffix whose exponent equivalent will be returned.
	 * @param def    the default to be returned when the given suffix is not supported.
	 * @return the corresponding exponent for the provided suffix or returns the second argument.
	 * @see mathaid.calculator.base.util.Utility#toEngineeringString(BigDecimal, boolean)
	 */
	private static String getSuffixExpOrDefault(String suffix, String def) {
		Map<String, String> suffixes = new HashMap<>();
		suffixes.put("Q", "E30");
		suffixes.put("R", "E27");
		suffixes.put("Y", "E24");
		suffixes.put("Z", "E21");
		suffixes.put("E", "E18");
		suffixes.put("P", "E15");
		suffixes.put("T", "E12");
		suffixes.put("G", "E9");
		suffixes.put("M", "E6");
		suffixes.put("k", "E3");
		suffixes.put("m", "E-3");
		suffixes.put("\u03BC", "E-6");
		suffixes.put("n", "E-9");
		suffixes.put("p", "E-12");
		suffixes.put("f", "E-15");
		suffixes.put("a", "E-18");
		suffixes.put("z", "E-21");
		suffixes.put("y", "E-24");
		suffixes.put("r", "E-27");
		suffixes.put("q", "E-30");
		return suffixes.getOrDefault(suffix, def);
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 17:58:51 ---------------------------------------------------
	 */
	/**
	 * Gets the suffix for the given exponent (first argument) or else return the second argument. Please see
	 * {@link mathaid.calculator.base.util.Utility#toEngineeringString(BigDecimal, boolean)} for supported values.
	 * 
	 * @param exp the exponent whose suffixed is to be computed and returned.
	 * @param def the value to be returned if the first is not a supported exponent.
	 * @return the corresponding suffix for the provided exponent if supported, else returns the second argument.
	 * @see mathaid.calculator.base.util.Utility#toEngineeringString(BigDecimal, boolean)
	 */
	private static String getSuffix(String exp, String def) {
		Map<String, String> suffixes = new HashMap<>();
		suffixes.put("E30", "Q");
		suffixes.put("E27", "R");
		suffixes.put("E24", "Y");
		suffixes.put("E21", "Z");
		suffixes.put("E18", "E");
		suffixes.put("E15", "P");
		suffixes.put("E12", "T");
		suffixes.put("E9", "G");
		suffixes.put("E6", "M");
		suffixes.put("E3", "k");
		suffixes.put("E-3", "m");
		suffixes.put("E-6", "\u03BC");
		suffixes.put("E-9", "n");
		suffixes.put("E-12", "p");
		suffixes.put("E-15", "f");
		suffixes.put("E-18", "a");
		suffixes.put("E-21", "z");
		suffixes.put("E-24", "y");
		suffixes.put("E-27", "r");
		suffixes.put("E-30", "q");
		return suffixes.getOrDefault(exp, def);
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 18:03:50 ---------------------------------------------------
	 */
	/**
	 * Transforms the {@code StringBuilder} into an unsigned decimal number tree, then concatenates that number tree to the
	 * {@code LinkedSegment}.
	 * 
	 * @param n  the value from which the number sub-tree will be generated.
	 * @param s  the head of the returned tree
	 * @param dp the punctuation options to apply to the generated number tree.
	 * @return a tree with a number sub-tree where all integer nodes have the type {@link #INTEGER}, the point node has the type
	 *         {@link #POINT} and the mantissa have the type {@link #MANTISSA}.
	 */
	private static LinkedSegment unsignedMantissa(StringBuilder n, LinkedSegment s, DigitPunc dp) {
		LinkedSegment num = unsignedDigits(new StringBuilder(n.substring(0, n.indexOf("."))), INTEGER, s, dp);
		return unsignedDigits(new StringBuilder(n.substring(n.indexOf(".") + 1)), MANTISSA,
				num.concat(point(dp.getPoint().charAt(0))), dp);
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 18:08:55 ---------------------------------------------------
	 */
	/**
	 * Parses the {@code BigFraction} argument into a mathaid recurring number expression. Please see {@link NumberAdapter} for the
	 * definition.
	 * 
	 * @param f              the value to be transformed
	 * @param exponentFactor the exponent factor to use. When greater than {@code 0}, the resultant string will be in scientific
	 *                       notation.
	 * @return the mathaid recurring number expression for the {@code BigFraction} argument as a string.
	 */
	// returns [integer].[mantissa]r[recurring digits]E[optional exponent
	// sign][exponent magnitude]
	// out.println(Digits.toSegmentString(BigFraction.ONE_THIRD, 0));//0.r3
	// out.println(Digits.toSegmentString(BigFraction.ONE_THIRD, 2));//0.r3E0
	// out.println(Digits.toSegmentString(BigFraction.ONE_THIRD, 3));//0.r3E0
	// out.println(Digits.toSegmentString(BigFraction.ONE_THIRD.add(1234),
	// 3));//1.234r3E3
	// out.println(Digits.toSegmentString(BigFraction.ONE_THIRD.add(1234),
	// 0));//1234.r3
	public static String toSegmentString(BigFraction f, int exponentFactor) {
		BigDecimal[] b = f.getRecurring();
		StringBuilder num = new StringBuilder(
				exponentFactor > 0 ? formatAsStandardForm(b[0], exponentFactor) : b[0].toString());

		if (b[1].signum() != 0) {
			int indexOfExponent = num.indexOf("E");

			if (num.indexOf(".") < 0) {
				if (indexOfExponent >= 0) {
					num.insert(indexOfExponent, ".");
					indexOfExponent++;
				} else
					num.append('.');
			}

			String s = b[1].toPlainString();
			String recur = String.format("R%s", s.substring(s.indexOf('.') + b[0].scale() + 1));
			if (indexOfExponent >= 0)
				num.insert(indexOfExponent, recur);
			else
				num.append(recur);
		}

		return num.toString();
	}

//	out.println(Digits.fromSegmentString("0.r3E0"));// [1, 3]
	// s is expected to be in upper case
	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 18:23:13 ---------------------------------------------------
	 */
	/**
	 * Parses the argument into a {@link BigFraction}.
	 * <p>
	 * Please see {@link NumberAdapter} for definition on the type of format used for the argument.
	 * 
	 * @param s the mathaid recurring expression. This is the same format returned by {@link #toSegmentString(BigFraction, int)}.
	 *          This should be in UPPER case.
	 * @return a {@code BigFraction} from the argument.
	 * @see mathaid.calculator.base.util.Utility#fromDecimal(BigDecimal, String)
	 * @see #toSegmentString(BigFraction, int)
	 */
	public static BigFraction fromSegmentString(String s) {
		StringBuilder sb = new StringBuilder(s);
		int r = sb.indexOf("R");
		int e = sb.indexOf("E");
		if (r >= 0) {
			String rec = sb.substring(r + 1, e >= 0 ? e : sb.length());
			BigDecimal nonRecur = d(sb.delete(r, e >= 0 ? e : sb.length()).toString());
			return fromDecimal(nonRecur, rec);
		}
		return new BigFraction(d(s), null, null, d("1E-10"));
	}

	/*
	 * Date: 29 Sep 2022-----------------------------------------------------------
	 * Time created: 01:48:13--------------------------------------------
	 */
	/**
	 * Returns the number of digits in the numerical string argument. This only counts the digits in the significand including the
	 * recurring ones.
	 * 
	 * @param exp note that exp must be in the same format as the result of {@link #toSegmentString(BigFraction, int)}. It must also
	 *            be in upper case
	 * @return the number of digits in the argument.
	 */
	public static int scale(String exp) {
		exp = exp.replaceAll("(?:\\+|\\-|\\.|r|R)", "");
		int e = exp.indexOf('E');
		if (e >= 0)
			return exp.substring(0, e).length();
		return exp.length();
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 22:20:04 ---------------------------------------------------
	 */
	/**
	 * Truncates the number of digits in the significand of the {@code s} argument to the {@code newScale} value. If the scale of
	 * the significand is less than the specified {@code newScale}, then the string is returned as is.
	 * <p>
	 * Note that this does not truncate the digits in the exponent or repeatend.
	 * 
	 * @param s        must be in scientific notation. May or may not have recurring digits.
	 * @param newScale an {@code int} greater than {@code 0}.
	 * @return the string argument, truncating the significand if it's scale is greater than the {@code int} argument.
	 */
	// This method expects that 's' is in scientific form i.e there is an exponent
	// field in 's'
	// s is expected to be in upper case
	public static String truncateToScale(final String s, int newScale) {
//		System.err.println(s);
		int e = s.indexOf('E');
		StringBuilder sb = new StringBuilder(s);
		int r = s.indexOf('R');
		if (r >= 0) {// i.e there is recurring digit(s)
			String sig = sb.substring(0, r);
			String rec = sb.substring(r + 1, e);
			if (rec.length() < newScale)
				return sb.replace(0, r, d(sig, mc(newScale)).toPlainString()).toString();
			String newRec = rec.substring(0, newScale >= rec.length() ? newScale - rec.length() : newScale);
			if (rec.compareTo(newRec) != 0)
				sb.replace(r, e, String.format("R%s", newRec));
//				sb.replace(r, e, newRec);
		} else if (e > 0) {
			String sig = sb.substring(0, e);
			sb = sb.replace(0, e, d(sig, mc(newScale)).toPlainString());
		} else {
			sb = sb.replace(0, sb.length(), d(sb.toString(), mc(newScale)).toPlainString());
		}
		return sb.toString();
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 22:33:28 ---------------------------------------------------
	 */
	/**
	 * Transforms the {@code BigFraction} argument to a number tree where the head is either the sign (if signed) or the first
	 * integer of the significand. Subsequent nodes may include any following integer digits, any point, mantissa digits, any
	 * recurring digits, any exponent sign and/or digits. An example is:
	 * 
	 * <pre>
	 * <code>
	 *	BigFraction n = BigFraction.N_25_169.add(123456789098765L);
	 *	LinkedSegment num = Digits.toSegment(n, Segment.Type.VINCULUM, 1, new DigitPunc());
	 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
	 *	num.toString(System.out, null, l);//prints: 123.45678901234E3
	 *	out.println();
	 *	l.clear();
	 *	l.add(-1);
	 *	/*
	 *	 * prints:
	 *	 * 1.234\,567\,890\,987\,65\overline{1\,479\,289\,940\,828\,402\,366\,863\,905\,325\,443\,786\,982\,248\,520\,710\,059\,171\,597\,633\,136\,094\,674\,556\,213\,017\,75} \times 10^{14}
	 *	 *&sol;
	 *	num.format(System.out, Formatter.empty(), l);
	 * </code>
	 * </pre>
	 * 
	 * This truncates the mantissa and the integer to fit the {@linkplain java.math.MathContext#getPrecision() scale} given by
	 * {@link BigFraction#getMathContext()} only when there is recurring digits (as truncation can be done on non-terminating
	 * fractions through setting of the {@code MathContext} object of the {@code BigDecimal} returned by
	 * {@link BigFraction#getFraction()}, it then behooves the caller to properly set the fraction's math-context's precision to
	 * allow for or prevent truncation. Note that this does not truncate the digits in the exponent or repeatend.
	 * <p>
	 * This is the recommended way to create a decimal number sub-tree, because the significand, recurring part and exponent are all
	 * accounted for.
	 * 
	 * @param n         the value to be transform.
	 * @param recurType used for recurring digit nodes to specify the type.
	 * @param exponent  used for specifying the exponent in a scientific notation. Use a value less than {@code 1} to prevent
	 *                  formatting in scientific notation.
	 * @param dp        the punctuation options to apply to the generated number tree.
	 * @return a number tree whose nodes are the digits in the given {@code BigFraction}.
	 * @see mathaid.calculator.base.util.Utility#formatAsStandardForm(BigDecimal, int)
	 */
	// the scale (or precision) of the BigFraction.getMathContext() is what is used
	// in this method
	public static LinkedSegment toSegment(BigFraction n, int recurType, int exponent, DigitPunc dp) {
		String s = toSegmentString(n, exponent);
		int scale = scale(s);
		if (scale > n.getMathContext().getPrecision())
			s = truncateToScale(s, n.getMathContext().getPrecision()).toUpperCase();
		int pointIndex = s.indexOf(".");
		int recIndex = s.indexOf("R");
		int expIndex = s.indexOf("E");
		LinkedSegment seg;
		if (n.signum() < 0) {
			s = s.substring(1);
			seg = prefixMinus();
		} else if (s.charAt(0) == '+') {
			s = s.substring(1);
			seg = prefixMinus();
		} else {
			seg = new Empty();
		}

		String exp = "";
		if (expIndex >= 0) {
			exp = s.substring(expIndex + 1);
			s = s.substring(0, expIndex);
		}
		String rec = "";
		if (recIndex >= 0) {
			rec = s.substring(s.indexOf("R") + 1);
			s = s.substring(0, s.indexOf("R"));
		}
		String mant = "";
		if (pointIndex >= 0) {
			mant = s.substring(s.indexOf(".") + 1);
			s = s.substring(0, s.indexOf("."));
		}

		seg = unsignedDigits(new StringBuilder(s), INTEGER, seg, dp);// int
		boolean hasPoint = false;
		if (!mant.isEmpty()) {
			seg = unsignedDigits(new StringBuilder(mant), MANTISSA, seg.concat(point(dp.getPoint().charAt(0))), dp);// mantissa
			hasPoint = true;
		}
		if (!rec.isEmpty()) {
			seg = unsignedDigits(new StringBuilder(rec), recurType,
					hasPoint ? seg : seg.concat(point(dp.getPoint().charAt(0))), dp);// recur
		}
		if (!exp.isEmpty()) {// exponent
			if (exp.charAt(0) == '-')
				seg = seg.concat(decimalExponent(prefixMinus()
						.concat(unsignedDigits(new StringBuilder(exp.substring(1)), INTEGER, new Empty(), dp))));
			else if (exp.charAt(0) == '+')
				seg = seg.concat(
						decimalExponent(unsignedDigits(new StringBuilder(exp.substring(1)), INTEGER, new Empty(), dp)));
			else
				seg = seg.concat(decimalExponent(unsignedDigits(new StringBuilder(exp), INTEGER, new Empty(), dp)));
		}

		return seg;
	}

	/*
	 * Date: 21 Nov 2023 -----------------------------------------------------------
	 * Time created: 16:19:18 ---------------------------------------------------
	 */
	/**
	 * Converts the {@code BigFraction} argument to a {@code LinkedSegment} number tree using the engineering notation. An example
	 * is:
	 * 
	 * <pre>
	 * <code>
	 *	BigFraction n = BigFraction.N_25_169.add(123456789098765L);
	 *	LinkedSegment num = Digits.toSegment(n, true, Segment.Type.VINCULUM, new DigitPunc());
	 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
	 *	num.toString(System.out, null, l);//prints: 123.456789098765r14792899408284023668639053254437869822485207100591E12
	 *	out.println();
	 *	l.clear();
	 *	l.add(-1);
	 *	/*
	 *	 * prints:
	 *	 * 123.456\,789\,098\,765\,\overline{147\,928\,994\,082\,840\,236\,686\,390\,532\,544\,378\,698\,224\,852\,071\,005\,91}T
	 *	 *&sol;
	 *	num.format(System.out, Formatter.empty(), l);
	 * </code>
	 * </pre>
	 * 
	 * This truncates the mantissa and the integer to fit the {@linkplain java.math.MathContext#getPrecision() scale} given by
	 * {@link BigFraction#getMathContext()} only when there is recurring digits (as truncation can be done on non-terminating
	 * fractions through setting of the {@code MathContext} object of the {@code BigDecimal} returned by
	 * {@link BigFraction#getFraction()}, it then behooves the caller to properly set the fraction's math-context's precision to
	 * allow for or prevent truncation. Note that this does not truncate the digits in the exponent or repeatend.
	 * 
	 * @param n         the value to be transformed into a {@code LinkedSegment}.
	 * @param useSuffix a {@code boolean} value specifying whether to use engineering suffixes such as &#x03bc;, M, y or use the
	 *                  numerical exponent which are in factors of {@code 3}. Even if this value is {@code true} the computed
	 *                  engineering exponent value must fall into the range of [-24, 24] for the suffixes to be appended.
	 * @param recurType used for recurring digit nodes to specify the type.
	 * @param dp        the punctuation options to apply to the generated number tree.
	 * @return a number tree whose nodes are digits in the given {@code BigFraction} using the computed engineering suffix.
	 * @see mathaid.calculator.base.util.Utility#toEngineeringString(BigDecimal, boolean)
	 */
	// TEST CASES
//	BigFraction n = BigFraction.ONE_NINTH.add(123456789098765L).setMathContext(Utility.mc(4));
//	BigFraction n = BigFraction.ONE_NINTH.add(123456789098765L);
//	BigFraction n = BigFraction.valueOf(123456789098765L).setMathContext(Utility.mc(4));
//	BigFraction n = BigFraction.valueOf(123456789098765L);
//	BigFraction n = BigFraction.N_25_169;
//	BigFraction n = BigFraction.N_25_169.add(123456789098765L);
//	BigFraction n = BigFraction.N_25_169.multiply(10e-22);
//	BigFraction n = BigFraction.N_25_169.multiply(10e-22).setMathContext(Utility.mc(4));
//	BigFraction n = BigFraction.ONE_NINTH;
//	LinkedSegment num = Digits.toSegment(n, true, Segment.Type.VINCULUM, new DigitPunc());
//	List<Integer> l = new ArrayList<>(Arrays.asList(-1));
//	num.toString(System.out, null, l);//prints 123.45678901234E3
//	out.println();
//	l.clear();
//	l.add(-1);
//	num.format(System.out, Formatter.empty(), l);
//	out.println();
	public static LinkedSegment toSegment(BigFraction n, boolean useSuffix, int recurType, DigitPunc dp) {
		String s = toSegmentString(n, 3);
		int scale = scale(s);
		if (scale > n.getMathContext().getPrecision())
			s = truncateToScale(s, n.getMathContext().getPrecision()).toUpperCase();
		int pointIndex = s.indexOf(".");
		int recIndex = s.indexOf("R");
		int expIndex = s.indexOf("E");
		LinkedSegment seg;
		if (n.signum() < 0) {
			s = s.substring(1);
			seg = prefixMinus();
		} else if (s.charAt(0) == '+') {
			s = s.substring(1);
			seg = prefixMinus();
		} else {
			seg = new Empty();
		}

		String exp = "";
		String numericalExp = s.substring(expIndex + 1);
		if (expIndex >= 0) {
			exp = useSuffix ? getSuffix(s.substring(expIndex), numericalExp) : numericalExp;
			s = s.substring(0, expIndex);
		}
		String rec = "";
		if (recIndex >= 0) {
			rec = s.substring(s.indexOf("R") + 1);
			s = s.substring(0, s.indexOf("R"));
		}
		String mant = "";
		if (pointIndex >= 0) {
			mant = s.substring(s.indexOf(".") + 1);
			s = s.substring(0, s.indexOf("."));
		}

		seg = unsignedDigits(new StringBuilder(s), INTEGER, seg, dp);// int
		boolean hasPoint = false;
		if (!mant.isEmpty()) {
			seg = unsignedDigits(new StringBuilder(mant), MANTISSA, seg.concat(point(dp.getPoint().charAt(0))), dp);// mantissa
			hasPoint = true;
		}
		if (!rec.isEmpty())
			seg = unsignedDigits(new StringBuilder(rec), recurType,
					hasPoint ? seg : seg.concat(point(dp.getPoint().charAt(0))), dp);// recur
		if (!exp.isEmpty()) {// exponent
			if (exp.charAt(0) == '-')
				seg = decimalExponent(
						prefixMinus().concat(unsignedDigits(new StringBuilder(exp.substring(1)), INTEGER, seg, dp)));
			else if (exp.charAt(0) == '+')
				seg = seg.concat(
						decimalExponent(unsignedDigits(new StringBuilder(exp.substring(1)), INTEGER, new Empty(), dp)));
//			else if (exp.length() == 1 && FloatAid.isNumber(exp.charAt(exp.length() - 1), 10))
			else if (isNumber(exp.charAt(exp.length() - 1), 10))
				seg = seg.concat(decimalExponent(unsignedDigits(new StringBuilder(exp), INTEGER, new Empty(), dp)));
			else {
//				System.err.println(Map.of("exp", exp, "rec", rec, "mant", mant, "numericalExp", numericalExp));
				LinkedSegment e = new BasicSegment(
						exp.charAt(exp.length() - 1) != '\u03BC' ? exp.charAt(exp.length() - 1) + "" : "\\mu",
						"E" + numericalExp, UNIT);
				seg = seg.concat(e);
			}
		}

		return seg;
	}

//	Reproduction and Life Cycle: The American holly begins flowering in the springtime, generally between April and June. Only the females produce fruit, which ripen from September through December and remain on the tree through the winter.

	/*
	 * Date: 21 Nov 2023 -----------------------------------------------------------
	 * Time created: 18:09:42 ---------------------------------------------------
	 */
	/**
	 * Transforms a {@code BigFraction} into a {@code LinkedSegment} tree which represents a common fraction with a whole number (as
	 * a child if specified), a numerator (superscript child) and a denominator (subscript child). The whole/numerator/denominator
	 * are number sub-trees with only integer type nodes. An example is:
	 * 
	 * <pre>
	 * <code>
	 *	BigFraction n = BigFraction.N_25_169.add(123456789098765L);
	 *	LinkedSegment num = Digits.toSegment(n, true, new DigitPunc());
	 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
	 *	num.toString(System.out, null, l);//prints: Rational[(169 *123456789098765 )+25 ,169 ]
	 *	out.println();
	 *	l.clear();
	 *	l.add(-1);
	 *	num.format(System.out, Formatter.empty(), l);// prints: 123,456,789,098,765 \frac{25 }{169 }
	 * </code>
	 * </pre>
	 * 
	 * @param n     the value to be transformed
	 * @param mixed {@code boolean} value to specify whether the returned tree is representative of a Mixed fraction ({@code true})
	 *              or not (<code>false</code>).
	 * @param dp    the punctuation options to apply to the generated number sub-trees.
	 * @return a tree whose are the whole number (if specified), numerator and denominator of the {@code BigFraction} argument.
	 */
	public static LinkedSegment toSegment(BigFraction n, boolean mixed, DigitPunc dp) {
		if (n.isInteger())
			return toSegment(n.getFraction().round(n.getMathContext()), 0, dp);
		else if (n.compareTo(BigFraction.valueOf(1)) < 0 || !mixed) {
			LinkedSegment nm = toSegment(n.getNumerator(), 10, dp);
			LinkedSegment d = toSegment(n.getDenominator(), 10, dp);
			String[] f = { " \\frac{", " }{", " }" };
			String[] s = { " Rational[", " ,", " ]" };
			return new CompositeSegment(f, s, FRACTION, new LinkedSegment[] { nm, d }, 0, 1);
		}
		BigInteger[] m = n.toMixed();
		LinkedSegment w = toSegment(m[0], 10, dp);
		LinkedSegment nm = toSegment(m[1], 10, dp);
		LinkedSegment d = toSegment(m[2], 10, dp);
		String[] f = { "", " \\frac{", " }{", " }" };
		String[] s = { " Rational[(", " *", " )+", " ,", " ]" };
		return new CompositeSegment(f, s, CompositeSegment.getOrder(f), new int[] { 2, 0, 1, 2 }, M_FRACTION,
				new LinkedSegment[] { w, nm, d }, 1, 2);
	}

	/*
	 * Date: 21 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:49:40 ---------------------------------------------------
	 */
	/**
	 * Performs the same function as {@link #toSegment(BigInteger, int, DigitPunc)} and then appends a node for the subscript base.
	 * An example is:
	 * 
	 * <pre>
	 * <code>
	 *	BigInteger n = BigInteger.valueOf(123456789098765L);
	 *	LinkedSegment num = Digits.toRadixedSegment(n, 16, new DigitPunc());
	 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
	 *	num.toString(System.out, null, l);//prints: 7048860F310D _16
	 *	out.println();
	 *	l.clear();
	 *	l.add(-1);
	 *	num.format(System.out, Formatter.empty(), l);// prints: 704,886,0F3,10D _{16 }
	 * </code>
	 * </pre>
	 * 
	 * Note that if the radix given is not 10, then the expression returned is currently unsupported by SYMJA, and can only be
	 * evaluated by evaluators from {@link mathaid.calculator.base.evaluator}.
	 * 
	 * @param n     the value to be transformed
	 * @param radix the radix of the final result.
	 * @param dp    the punctuation options to apply to the generated number tree.
	 * @return a number tree whose nodes are digits in the given {@code BigFraction} using the computed engineering suffix.
	 * @return a number tree whose nodes are digits of the given {@code BigInteger} using the specified radix.
	 */
	public static LinkedSegment toRadixedSegment(BigInteger n, int radix, DigitPunc dp) {
		return toSegment(n, radix, dp).concat(sub(radix, dp));
	}

	/*
	 * Date: 21 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:28:02 ---------------------------------------------------
	 */
	/**
	 * Creates a {@code LinkedSegment} that represents a subscript usually used in Mathematics to describe the radix of a number.
	 * 
	 * @param radix the specified radix
	 * @param dp    the punctuation options to apply to the generated number tree.
	 * @return a tree that is meant as a sibling but never as a head, which represents the radix of a number tree.
	 */
	private static LinkedSegment sub(int radix, DigitPunc dp) {
		return new CompositeSegment(new String[] { " _{", " }" }, new String[] { " _", "" }, OBJECT,
				new LinkedSegment[] { toSegment(BigInteger.valueOf(radix), 10, dp) }, -1, 0);
	}

	/*
	 * Date: 21 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:32:13 ---------------------------------------------------
	 */
	/**
	 * Transforms the floating point number to a number tree, creating nodes that specify the radix of the significand and exponent.
	 * An example is:
	 * 
	 * <pre>
	 * <code>
	 *	BinaryFP n = FloatAid.IEEE754Double().createFP(Utility.d("12345.678901e300"));
	 *	LinkedSegment num = Digits.toSegment(n, 16, 16, true, new DigitPunc());
	 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
	 *	num.toString(System.out, null, l);//prints: 1.200B58D302B90F5 _16p+1010 _16
	 *	out.println();
	 *	l.clear();
	 *	l.add(-1);
	 *	num.format(System.out, Formatter.empty(), l);// prints: 1.200\,B58\,D30\,2B9\,0F5 _{16 }\mathrm{p}\textrm{+}1,010 _{16 }
	 * </code>
	 * </pre>
	 * 
	 * Note that the expression returned is currently unsupported by SYMJA, and can only be evaluated by evaluators from
	 * {@link mathaid.calculator.base.evaluator}.
	 * 
	 * @param n        the value to be transformed.
	 * @param sigRadix the radix of the significand.
	 * @param expRadix the radix of the exponent digits.
	 * @param norm     {@code boolean} value that specifies whether to normalise the value before the transformation is done. Please
	 *                 see {@link BinaryFP#toNormalisedString(int)} for an explanation on floating point normalisation.
	 * @param dp       the punctuation options to apply to the generated number tree.
	 * @return a number tree that is representation of the given floating point.
	 */
	// TEST CASES
//	BinaryFP n = FloatAid.IEEE754Double().createFP(Utility.d("12345.678901"));
//	BinaryFP n = FloatAid.IEEE754Double().createFP(Utility.d("12345.678901e300"));
//	BinaryFP n = FloatAid.IEEE754Double().createFP(Utility.d("-1234"));
//	BinaryFP n = FloatAid.IEEE754Double().createFP(Utility.d("123.456"));
//	BinaryFP n = FloatAid.IEEE754Double().createFP(Utility.d("123e7"));
//	BinaryFP n = FloatAid.IEEE754Double().createFP(Utility.d("123e-7"));
//	LinkedSegment num = Digits.toSegment(n, 16, 16, true, new DigitPunc());
//	LinkedSegment num = Digits.toSegment(n, 16, 16, false, new DigitPunc());
	List<Integer> l = new ArrayList<>(Arrays.asList(-1));

//	num.toString(System.out, null, l);// prints: Rational[(169 *123456789098765 )+25 ,169 ]
//	out.println();
//	l.clear();
//	l.add(-1);
//	num.format(System.out, Formatter.empty(), l);
	public static LinkedSegment toSegment(BinaryFP n, int sigRadix, int expRadix, boolean norm, DigitPunc dp) {
		if (n.isNaN()) {
			return new BasicSegment(String.format("\\mathrm{%s}", "NaN"), "NaN", CONSTANT);
		} else if (n.isInfinite()) {
			return new BasicSegment(String.format("\\mathrm{%s}", n), n.toString(), CONSTANT);
		}

		String sig, exp;
		LinkedSegment ls;
		if (norm) {
			String s = n.toNormalisedString(sigRadix).toUpperCase();
			int pP = s.indexOf("P+");
			int pM = s.indexOf("P-");
			int p = pP >= 0 ? pP : pM;
			sig = s.substring(0, p);
			exp = s.substring(p + 1);// because of the additional sign
		} else {
			String s = n.toScientificString(sigRadix).toUpperCase();
			int eP = sigRadix == 10 ? s.indexOf("E") : s.indexOf("E+");
			int eM = s.indexOf("E-");
			int e = eP >= 0 ? eP : eM;
			sig = s.substring(0, e >= 0 ? e : s.length());
			exp = e >= 0 ? s.substring((sigRadix == 10) ? e + 1 : e + 1) : "";
		}

		if (sig.charAt(0) == '-') {
			sig = sig.substring(1);
			ls = prefixMinus();
		} else if (sig.charAt(0) == '+') {
			sig = sig.substring(1);
			ls = prefixPlus();
		} else {
			ls = new Empty();
		}

		if (sig.indexOf('.') >= 0) {
			ls = unsignedDigits(new StringBuilder(sig.substring(0, sig.indexOf('.'))), INTEGER, ls, dp);
			ls = unsignedDigits(new StringBuilder(sig.substring(sig.indexOf('.') + 1)), MANTISSA,
					ls.concat(point(dp.getPoint().charAt(0))), dp).concat(sub(sigRadix, dp));
		} else {
			ls = unsignedDigits(new StringBuilder(sig), INTEGER, ls, dp).concat(sub(sigRadix, dp));
		}

		if (!exp.isEmpty())
			if (exp.charAt(0) == '-') {
				exp = exp.substring(1);
				ls = unsignedDigits(
						new StringBuilder(Integer.toString(Integer.parseInt(exp, sigRadix), expRadix).toUpperCase()),
						INTEGER, ls.concat(radixExponent(norm)).concat(prefixMinus()), dp).concat(sub(expRadix, dp));
			} else if (exp.charAt(0) == '+') {
				exp = exp.substring(0);
				ls = unsignedDigits(
						new StringBuilder(Integer.toString(Integer.parseInt(exp, sigRadix), expRadix).toUpperCase()),
						INTEGER, ls.concat(radixExponent(norm)).concat(prefixPlus()), dp).concat(sub(expRadix, dp));
			} else {
				ls = unsignedDigits(
						new StringBuilder(Integer.toString(Integer.parseInt(exp, sigRadix), expRadix).toUpperCase()),
						INTEGER, ls.concat(radixExponent(norm)).concat(prefixPlus()), dp).concat(sub(expRadix, dp));
			}
		return ls;
	}

	/*
	 * Date: 21 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:44:18 ---------------------------------------------------
	 */
	/**
	 * Generates a number tree from the given {@code StringBuilder} that comprises of only alphanumeric (they are in a base other
	 * than 10) and appends it to the given {@code LinkedSegment} argument.
	 * <p>
	 * This just the radix version of {@link #unsignedDigits(StringBuilder, int, LinkedSegment, DigitPunc)}.
	 * 
	 * @param n  the value from which the tree will be generated.
	 * @param t  the type of digits for each digit.
	 * @param s  the head of the returned tree.
	 * @param dp the punctuation options to apply to the generated number tree.
	 * @return a tree whose head is {@code s} and the rest is a sub-tree representing {@code n}.
	 * @implNote This method does not properly build number expressions because it coerces the digit using {@code \mathrm{digit}} to
	 *           prevent non-decimal digit to be formatted in oblique font by MATHJAX. This poses a problem because the
	 *           {@code Digit} constructor only allows for a single string representing both the MATHJAX display format and the
	 *           expression string. Hence:
	 * 
	 *           <pre>
	 * <code>
	 *	LinkedSegment n = nonDecimal(new StringBuilder(Utility.i(1234567890L).toString(16)), Segment.Type.INTEGER, new Empty(), new DigitPunc());
	 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
	 *	n.toString(System.out, null, l);//prints: 499602\mathrm{d}2
	 * </code>
	 *           </pre>
	 * 
	 *           From the above we can clearly see that the resultant expression is the same as the MATHJAX format which is a bug.
	 *           This can be fixed by using the {@link Formatter} interface and specifying how a digit may be formatted by creating
	 *           a {@link Marker} such as:
	 * 
	 *           <pre>
	 *           <code>
	 *	static class MyMarker implements Marker {
	 *		&commat;Override
	 *		public String mark(Segment segment, String format, int type, List<Integer> position) {
	 *			if (segment instanceof Digit) {
	 *				if(Utility.isNumber(format))//if the value to be formatted is a decimal then return 'as is'
	 *					return format;
	 *				return new StringBuilder("\\mathrm{").append(format.toUpperCase()).append("}").toString();
	 *			}
	 *			return format;
	 *		}
	 *		public static void main(String[] args) {
	 *			Formatter f = Formatter.empty();
	 *			f.addMarker(-1, new MyMarker());//Use -1 as the code to avoid collision with other codes
	 *			LinkedSegment n = unsignedDigits(new StringBuilder(Utility.i(1234567890L).toString(16)), Segment.Type.INTEGER, new Empty(), new DigitPunc());
	 *			List<Integer> l = new ArrayList<>(Arrays.asList(-1));
	 *			n.toString(System.out, null, l);// prints: 499602d2
	 *			out.println();
	 *			l.clear();
	 *			l.add(-1);
	 *			n.format(System.out, f, l);// prints: 49,960,2\mathrm{D}2
	 *		}
	 *	}
	 *	</code>
	 *           </pre>
	 */
	static LinkedSegment nonDecimal(StringBuilder n, int t, LinkedSegment s, DigitPunc dp) {
		if (n.length() > 0) {
			char d = n.charAt(0);
			return nonDecimal(n.deleteCharAt(0), t,
					s.concat(new Digit(Character.isDigit(d) ? d + "" : String.format("\\mathrm{%s}", d), t, dp)), dp);
		}
		return s;
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:49:19 ---------------------------------------------------
	 */
	/**
	 * Gets the {@code LinkedSegment} which is meant to represent the imaginary value <em>i</em>. The value that this method
	 * returns, does not have a sibling or child.
	 * <p>
	 * <strong>The TeX format</strong> (specified by {@link LinkedSegment#format(Appendable, Formatter, java.util.List)}) is:
	 * 
	 * <pre>
	 * <code>\mathrm{i}</code>
	 * </pre>
	 * 
	 * This is also the image of {@code LinkedSegment} returned.
	 * <p>
	 * <strong>The expression value</strong> (specified by {@code LinkedSegment.toString(Appendable, Log, java.util.List)}) is:
	 * 
	 * <pre>
	 * <code>i</code>
	 * </pre>
	 * 
	 * This is also the expression of {@code LinkedSegment} returned.
	 * <p>
	 * This uses the {@link #CONSTANT} type.
	 * 
	 * @return a {@code LinkedSegment} that is meant to represent the imaginary part of a complex number tree.
	 */
	public static LinkedSegment i() {
		return new BasicSegment(" \\mathrm{i}", " i", Segment.Type.CONSTANT);
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:47:05 ---------------------------------------------------
	 */
	/**
	 * Creates a childless segment with no sibling for the Mathematical '&infin;' constant.
	 * 
	 * @return the created segment representing the '&infin;' constant
	 */
	public static LinkedSegment inf() {
		return Segments.constant("\\infty", "infinity");
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:48:46 ---------------------------------------------------
	 */
	/**
	 * Private constructor to prevent instantiation.
	 */
	private Digits() {
	}
}