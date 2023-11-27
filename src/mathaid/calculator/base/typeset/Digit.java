
/**
 * 
 */
package mathaid.calculator.base.typeset;

import static mathaid.calculator.base.typeset.Segment.Type.ARC;
import static mathaid.calculator.base.typeset.Segment.Type.AUTO_COMPLETE;
import static mathaid.calculator.base.typeset.Segment.Type.CONSTANT;
import static mathaid.calculator.base.typeset.Segment.Type.DELIMITER;
import static mathaid.calculator.base.typeset.Segment.Type.DOT;
import static mathaid.calculator.base.typeset.Segment.Type.DOT_ALL;
import static mathaid.calculator.base.typeset.Segment.Type.DOT_BAR;
import static mathaid.calculator.base.typeset.Segment.Type.ELLIPSIS;
import static mathaid.calculator.base.typeset.Segment.Type.FUNCTION;
import static mathaid.calculator.base.typeset.Segment.Type.INTEGER;
import static mathaid.calculator.base.typeset.Segment.Type.L_PARENTHESIS;
import static mathaid.calculator.base.typeset.Segment.Type.MANTISSA;
import static mathaid.calculator.base.typeset.Segment.Type.PARENTHESISED;
import static mathaid.calculator.base.typeset.Segment.Type.SEPARATOR;
import static mathaid.calculator.base.typeset.Segment.Type.VAR_BOUND;
import static mathaid.calculator.base.typeset.Segment.Type.VAR_FREE;
import static mathaid.calculator.base.typeset.Segment.Type.VINCULUM;

import java.io.IOException;
import java.util.List;

import mathaid.calculator.FatalReadException;

/*
 * Date: 17 Nov 2023 -----------------------------------------------------------
 * Time created: 12:22:52 ---------------------------------------------------
 * Package: mathaid.calculator.base.typeset ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: Digit.java ------------------------------------------------------
 * Class name: Digit ------------------------------------------------
 */
/**
 * A {@code Digit} is a node within the segment tree that represents a single
 * number (in any given radix). It does not have any children, can receive
 * focus, and can have error(s).
 * <p>When a (or part of) {@code LinkedSegment} tree consists entirely of
 * {@code Digit} objects, then that tree (or sub-tree) is a number tree.
 * <p>
 * {@code Digit} nodes can be formatted using the formatting option object
 * {@link DigitPunc} allows specification of separator, radix point, recurring
 * style and digit grouping.
 * <p>
 * Internally, this class relates with siblings of a {@code Digit} instance
 * through it's {@linkplain #getType type}. If the sibling of a digit is a
 * {@code Digit} instance, then that sibling is formatted as part of a special
 * joint formatting operation that takes advantage of the facilities supported
 * by this class such as recurring digit formatting and grouping separators. The
 * actual format operation carried out on a {@code Digit} depends on it's type.
 * The following are the types supported by this class:
 * <ul>
 * <li>{@link #INTEGER}: This type is the simplest type of the {@code Digit}
 * instance. It represents a digit located before the radix point (if any). An
 * example of this is:
 * 
 * <pre>
 * <code>
 *	LinkedSegment number = new Digit('5');
 *	for(int i = 0; i < 10; i++) number = number.concat(new Digit(Integer.toString(i).charAt(0)));
 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
 *	number.toString(System.out, null, l);//prints 50123456789
 *	out.println();
 *	l.clear();
 *	l.add(-1);
 *	number.format(System.out, Formatter.empty(), l);// prints 50,123,456,789
 *	out.println();
 * </code>
 * </pre>
 * 
 * </li>
 * <li>{@link #MANTISSA}: This type represents any digit that is placed after
 * the {@link Segment.Type#POINT radix point} and is not part of any recurring
 * node. When the tree traversal encounters a radix point, it assigns this type
 * to the sibling of the point if that sibling is an instance of this class, and
 * also to all consecutive siblings that are also instances of this class. An
 * example is given below:
 * 
 * <pre>
 * <code>
 *	DigitPunc dp = new DigitPunc();
 *	LinkedSegment number = new Digit('1')
 *			.concat(Digits.point('.'))
 *			.concat(new Digit('2', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('3', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('4', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('5', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('6', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('7', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('8', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('9', Segment.Type.MANTISSA, dp))
 *			.concat(Digits.decimalExponent(new Digit('3')));// For number exponents
 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
 *	number.toString(System.out, null, l);//prints 1.23456789e3
 *	out.println();
 *	l.clear();
 *	l.add(-1);
 *	number.format(System.out, Formatter.empty(), l);// prints: 1.234\,567\,89 \times 10^{3}</code>
 * </pre>
 * 
 * </li>
 * <li><strong>Recurring types</strong>: These are special digit types that
 * represent their formatted images in a specific way usually set by the user.
 * They are only found after the radix point, before the exponent character.
 * When their value is appended using {@link #toString(Appendable, Log, List)},
 * an 'r' (case in-sensitive) prefix will precede the first recurring digit. The
 * following are the recurring types currently supported:
 * <ul>
 * <li>{@link #VINCULUM}: this is the common bar drawn over all recurring digits
 * in a number.
 * 
 * <pre>
 * <code>
 *	DigitPunc dp = new DigitPunc();
 *	LinkedSegment number = new Digit('2')
 *			.concat(Digits.point('.'))
 *			.concat(new Digit('4', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('9', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('2', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('8', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('1', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('2', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('7', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('0', Segment.Type.VINCULUM, dp))
 *			.concat(new Digit('4', Segment.Type.VINCULUM, dp))
 *			.concat(new Digit('3', Segment.Type.VINCULUM, dp))
 *			.concat(new Digit('1', Segment.Type.VINCULUM, dp))
 *			.concat(new Digit('0', Segment.Type.VINCULUM, dp))
 *			.concat(new Digit('8', Segment.Type.VINCULUM, dp))
 *			.concat(Digits.decimalExponent(new Digit('3')));// For number exponents
 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
 *	number.toString(System.out, null, l);//prints 2.4928127r043108e3
 *	out.println();
 *	l.clear();
 *	l.add(-1);
 *	number.format(System.out, Formatter.empty(), l);// prints: 2.492\,812\,7\overline{04\,310\,8} \times 10^{3}
 * </code>
 * </pre>
 * 
 * Which renders as:
 *	<div>
 *		<svg viewbox="0 0 150 10">
 *			<line x1="49" y1="2" x2="81" y2="2" style="stroke:black; stroke-width:.5" />
 *			<text x="0" y="10" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">
 *				2.492 812 704 310 8
 *			</text>
 *			<text x="88" y="10" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">
 *				&times; 10
 *			</text>
 *			<text x="106" y="5" fill="black" style="font-size:5px;font-family:serif;font-weight:400;">
 *				3
 *			</text>
 *		</svg>
 *	</div>
 * </li>
 * <li>{@link #ARC}: this is represented as an arc drawn over all recurring
 * digits in a number.
 * 
 * <pre>
 * <code>
 *	DigitPunc dp = new DigitPunc();
 *	LinkedSegment number = new Digit('2')
 *			.concat(Digits.point('.'))
 *			.concat(new Digit('4', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('9', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('2', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('8', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('1', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('2', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('7', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('0', Segment.Type.ARC, dp))
 *			.concat(new Digit('4', Segment.Type.ARC, dp))
 *			.concat(new Digit('3', Segment.Type.ARC, dp))
 *			.concat(new Digit('1', Segment.Type.ARC, dp))
 *			.concat(new Digit('0', Segment.Type.ARC, dp))
 *			.concat(new Digit('8', Segment.Type.ARC, dp))
 *			.concat(Digits.decimalExponent(new Digit('3')));// For number exponents
 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
 *	number.toString(System.out, null, l);//prints 2.4928127r043108e3
 *	out.println();
 *	l.clear();
 *	l.add(-1);
 *	number.format(System.out, Formatter.empty(), l);// prints: 2.492\,812\,7\overparen{04\,310\,8} \times 10^{3}
 * </code>
 * </pre>
 * 
 * Which renders as:
 *	<div>
 *		<svg viewbox="0 0 150 18">
 *			<defs>
 *				<clipPath id="rect-clip">
 *					<rect x="49" y="-7.5" width="33" height="15" />
 *				</clipPath>
 *			</defs>
 *			<ellipse cx="65" cy="12" rx="16" ry="10" clip-path="url(#rect-clip)" style="fill: none; stroke:black; stroke-width:.5" />
 *			<text x="0" y="15" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">
 *				2.492 812 704 310 8
 *			</text>
 *			<text x="88" y="15" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">
 *				&times; 10
 *			</text>
 *			<text x="106" y="10" fill="black" style="font-size:5px;font-family:serif;font-weight:400;">
 *				3
 *			</text>
 *		</svg>
 *	</div>
 * </li>
 * <li>{@link #DOT}: this is represented as a dot on top of the first and last
 * recurring digits in a number.
 * 
 * <pre>
 * <code>
 *	DigitPunc dp = new DigitPunc();
 *	LinkedSegment number = new Digit('2')
 *			.concat(Digits.point('.'))
 *			.concat(new Digit('4', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('9', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('2', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('8', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('1', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('2', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('7', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('0', Segment.Type.DOT, dp))
 *			.concat(new Digit('4', Segment.Type.DOT, dp))
 *			.concat(new Digit('3', Segment.Type.DOT, dp))
 *			.concat(new Digit('1', Segment.Type.DOT, dp))
 *			.concat(new Digit('0', Segment.Type.DOT, dp))
 *			.concat(new Digit('8', Segment.Type.DOT, dp))
 *			.concat(Digits.decimalExponent(new Digit('3')));// For number exponents
 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
 *	number.toString(System.out, null, l);//prints 2.4928127r043108e3
 *	out.println();
 *	l.clear();
 *	l.add(-1);
 *	number.format(System.out, Formatter.empty(), l);// prints: 2.492\,812\,7\dot{0}4\,310\,\dot{8} \times 10^{3}
 * </code>
 * </pre>
 * 
 * Which renders as:
 *	<div>
 *		<svg viewbox="0 0 200 10">
 *			<text x="49" y="2" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">.</text>
 *			<text x="78.5" y="2" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">.</text>
 *			<text x="0" y="10" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">
 *				2.492 812 704 310 8
 *			</text>
 *			<text x="88" y="10" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">
 *				&times; 10
 *			</text>
 *			<text x="106" y="5" fill="black" style="font-size:5px;font-family:serif;font-weight:400;">
 *				3
 *			</text>
 *		</svg>
 *	</div>
 * </li>
 * <li>{@link #DOT_ALL}: this is represented as a dot on top of all recurring
 * digits in a number.
 * 
 * <pre>
 * <code>
 *	DigitPunc dp = new DigitPunc();
 *	LinkedSegment number = new Digit('2')
 *			.concat(Digits.point('.'))
 *			.concat(new Digit('4', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('9', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('2', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('8', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('1', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('2', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('7', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('0', Segment.Type.DOT_ALL, dp))
 *			.concat(new Digit('4', Segment.Type.DOT_ALL, dp))
 *			.concat(new Digit('3', Segment.Type.DOT_ALL, dp))
 *			.concat(new Digit('1', Segment.Type.DOT_ALL, dp))
 *			.concat(new Digit('0', Segment.Type.DOT_ALL, dp))
 *			.concat(new Digit('8', Segment.Type.DOT_ALL, dp))
 *			.concat(Digits.decimalExponent(new Digit('3')));// For number exponents
 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
 *	number.toString(System.out, null, l);//prints 2.4928127r043108e3
 *	out.println();
 *	l.clear();
 *	l.add(-1);
 *	number.format(System.out, Formatter.empty(), l);// prints: 2.492\,812\,7\dot{0}4\,310\,\dot{8} \times 10^{3}
 * </code>
 * </pre>
 * 
 * Which renders as:
 *	<div>
 *		<svg viewbox="0 0 200 10">
 *			<text x="49" y="2" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">.</text>
 *			<text x="54.5" y="2" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">.</text>
 *			<text x="61" y="2" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">.</text>
 *			<text x="66" y="2" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">.</text>
 *			<text x="71" y="2" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">.</text>
 *			<text x="78.5" y="2" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">.</text>
 *			<text x="0" y="10" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">
 *				2.492 812 704 310 8
 *			</text>
 *			<text x="88" y="10" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">
 *				&times; 10
 *			</text>
 *			<text x="106" y="5" fill="black" style="font-size:5px;font-family:serif;font-weight:400;">
 *				3
 *			</text>
 *		</svg>
 *	</div>
 *	</li>
 * <li>{@link #DOT_BAR}: this will function as a {@link #DOT} if there is only a
 * single recurring digit present else it will function as a
 * {@link #VINCULUM}.</li>
 * <li>{@link #ELLIPSIS}: this is represented as 3 leading dots at the end of
 * the mantissa portion of the number after repeating the recurring digits a
 * specified number of times. In this type, the repeatend (recurring digits) are
 * repeated a particular number of times given by {@link DigitPunc} constructor
 * and can be retrieved from the {@code DigitPunc} class by
 * {@link DigitPunc#getNumOfRecurringDigits()}.
 * 
 * <pre>
 * <code>
 *	DigitPunc dp = new DigitPunc();
 *	LinkedSegment number = new Digit('2')
 *			.concat(Digits.point('.'))
 *			.concat(new Digit('4', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('9', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('2', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('8', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('1', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('2', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('7', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('0', Segment.Type.ELLIPSIS, dp))
 *			.concat(new Digit('4', Segment.Type.ELLIPSIS, dp))
 *			.concat(new Digit('3', Segment.Type.ELLIPSIS, dp))
 *			.concat(new Digit('1', Segment.Type.ELLIPSIS, dp))
 *			.concat(new Digit('0', Segment.Type.ELLIPSIS, dp))
 *			.concat(new Digit('8', Segment.Type.ELLIPSIS, dp))
 *			.concat(Digits.decimalExponent(new Digit('3')));// For number exponents
 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
 *	number.toString(System.out, null, l);//prints 2.4928127r043108e3
 *	out.println();
 *	l.clear();
 *	l.add(-1);
 *	number.format(System.out, Formatter.empty(), l);// prints: 2.492\,812\,704\,310\,804\,310\,804\,310\,804\,310\,8\ldots \times 10^{3}
 * </code>
 * </pre>
 * 
 * Which renders as:
 *	<div>
 *		<svg viewbox="0 0 300 10">
 *			<text x="0" y="10" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">
 *				2.492 812 704 310 804 310 804 310 804 310 8 ...
 *			</text>
 *			<text x="202" y="10" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">
 *				&times; 10
 *			</text>
 *			<text x="220" y="5" fill="black" style="font-size:5px;font-family:serif;font-weight:400;">
 *				3
 *			</text>
 *		</svg>
 *	</div>
 * </li>
 * <li>{@link #PARENTHESISED}: this is represented as a parenthesis that encloses the
 * recurring digits in the mantissa portion of the number.
 * 
 * <pre>
 * <code>
 *	DigitPunc dp = new DigitPunc();
 *	LinkedSegment number = new Digit('2')
 *			.concat(Digits.point('.'))
 *			.concat(new Digit('4', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('9', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('2', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('8', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('1', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('2', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('7', Segment.Type.MANTISSA, dp))
 *			.concat(new Digit('0', Segment.Type.PARENTHESISED, dp))
 *			.concat(new Digit('4', Segment.Type.PARENTHESISED, dp))
 *			.concat(new Digit('3', Segment.Type.PARENTHESISED, dp))
 *			.concat(new Digit('1', Segment.Type.PARENTHESISED, dp))
 *			.concat(new Digit('0', Segment.Type.PARENTHESISED, dp))
 *			.concat(new Digit('8', Segment.Type.PARENTHESISED, dp))
 *			.concat(Digits.decimalExponent(new Digit('3')));// For number exponents
 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
 *	number.toString(System.out, null, l);//prints 2.4928127r043108e3
 *	out.println();
 *	l.clear();
 *	l.add(-1);
 *	number.format(System.out, Formatter.empty(), l);// prints: 2.492\,812\,7(04\,310\,8) \times 10^{3}
 * </code>
 * </pre>
 * 
 * Which renders as:
 *	<div>
 *		<svg viewbox="0 0 200 10">
 *			<text x="0" y="10" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">
 *				2.492 812 7(04 310 8)
 *			</text>
 *			<text x="92" y="10" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">
 *				&times; 10
 *			</text>
 *			<text x="110" y="5" fill="black" style="font-size:5px;font-family:serif;font-weight:400;">
 *				3
 *			</text>
 *		</svg>
 *	</div>
 * </li>
 * </ul>
 * </li>
 * </ul>
 * <p>
 * {@code Digit} instances are not checked for their syntactic compliance with
 * real world math i.e this class will treat {@code 'm'} the same way it would
 * treat {@code '5'} as a digit, hence no checking the actual string used for
 * the digit. This is done so as to abstract to the user what they themselves
 * define a digit to be. Because of this behaviour, it is possible to use say
 * {@code ";'je"} as a digit and it will be treated as a single unit (node)
 * despite the fact that it consists of multiple characters (Note that
 * {@link #toString(Appendable, Log, List)} may log an error because it cannot
 * be evaluated).
 * <p>This class is made {@code final} because many thing could break if a subclass
 * was to have access to the fields in this class.
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public final class Digit extends AbstractSegment {
	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 03:05:39 ---------------------------------------------------
	 */
	/**
	 * Creates a {@code Digit} specifying it's sibling, focus and error properties,
	 * the image type and digit punctuation options. This is used internally for
	 * maintaining the immutability of this object.
	 * @param d the image of this digit as well as it's value. This is the value that will
	 * be rendered when {@link #format(Appendable, Formatter, List)} is called. And also
	 * the expression that will be created when {@link #toString(Appendable, Log, List)}
	 * is called. This should be a string with a single character to maintain consistent
	 * rendering and expression.
	 * @param t the type of {@code this}.
	 * @param f the focus property.
	 * @param e the error property.
	 * @param dp the punctuation options.
	 * @param s the sibling of this node.
	 * @see AbstractSegment#AbstractSegment(int, boolean, boolean, LinkedSegment, LinkedSegment[], int, int)
	 */
	protected Digit(String d, int t, boolean f, boolean e, DigitPunc dp, LinkedSegment s) {
		super(t, f, e, s, new LinkedSegment[] {}, -1, -1);
		this.digit = d;
		this.dp = dp;
		this.repeatend = new StringBuilder();
		this.isFirstIndex = true;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 03:51:30 ---------------------------------------------------
	 */
	/**
	 * Creates a {@code Digit} whose {@link #getSibling sibling} is {@code null},
	 * error and focus properties are {@code false}.
	 * @param n the image of this digit as well as it's value. This is the value that will
	 * be rendered when {@link #format(Appendable, Formatter, List)} is called. And also
	 * the expression that will be created when {@link #toString(Appendable, Log, List)}
	 * is called. This should be a string with a single character to maintain consistent
	 * rendering and expression.
	 * @param t the type of this {@code Digit}.
	 * @param dp the punctuation options.
	 * @see #Digit(String, int, boolean, boolean, DigitPunc, LinkedSegment)
	 */
	public Digit(String n, int t, DigitPunc dp) {
		this(n, t, false, false, dp, null);
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 03:57:26 ---------------------------------------------------
	 */
	/**
	 * The {@code char} version of {@linkplain #Digit(String, int, DigitPunc) this constructor}.
	 * @param d the image of this digit as well as it's value. This is the value that will
	 * be rendered when {@link #format(Appendable, Formatter, List)} is called. And also
	 * the expression that will be created when {@link #toString(Appendable, Log, List)}
	 * is called.
	 * @param t the type of this {@code Digit}.
	 * @param dp the punctuation options.
	 * @see #Digit(String, int, boolean, boolean, DigitPunc, LinkedSegment)
	 */
	public Digit(char d, int t, DigitPunc dp) {
		this(String.valueOf(d), t, false, false, dp, null);
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:00:33 ---------------------------------------------------
	 */
	/**
	 * Creates an {@link #INTEGER} type of {@code Digit} from the specified digit string.
	 * @param n the image of this digit as well as it's value. This is the value that will
	 * be rendered when {@link #format(Appendable, Formatter, List)} is called. And also
	 * the expression that will be created when {@link #toString(Appendable, Log, List)}
	 * is called. This should be a string with a single character to maintain consistent
	 * rendering and expression.
	 * @param dp the punctuation options.
	 * @see #Digit(String, int, DigitPunc)
	 */
	public Digit(String n, DigitPunc dp) {
		this(n, INTEGER, dp);
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:02:48 ---------------------------------------------------
	 */
	/**
	 * The {@code char} version of {@linkplain #Digit(String, DigitPunc) this constructor}.
	 * @param d the image of this digit as well as it's value. This is the value that will
	 * be rendered when {@link #format(Appendable, Formatter, List)} is called. And also
	 * the expression that will be created when {@link #toString(Appendable, Log, List)}
	 * is called.
	 * @param dp the punctuation options.
	 * @see #Digit(char, int, DigitPunc)
	 */
	public Digit(char d, DigitPunc dp) {
		this(d, INTEGER, dp);
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:04:14 ---------------------------------------------------
	 */
	/**
	 * Initialises a {@code Digit} from the given string, using a default punctuation
	 * option specified by {@code new DigitPunc()}.
	 * @param n the image of this digit as well as it's value. This is the value that will
	 * be rendered when {@link #format(Appendable, Formatter, List)} is called. And also
	 * the expression that will be created when {@link #toString(Appendable, Log, List)}
	 * is called. This should be a string with a single character to maintain consistent
	 * rendering and expression.
	 * @see #Digit(String, DigitPunc)
	 */
	public Digit(String n) {
		this(n, new DigitPunc());
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:06:28 ---------------------------------------------------
	 */
	/**
	 * The {@code char} version of {@linkplain #Digit(String) this constructor}.
	 * @param d the image of this digit as well as it's value. This is the value that will
	 * be rendered when {@link #format(Appendable, Formatter, List)} is called. And also
	 * the expression that will be created when {@link #toString(Appendable, Log, List)}
	 * is called.
	 * @see #Digit(char, DigitPunc)
	 */
	public Digit(char d) {
		this(d, new DigitPunc());
	}

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:34:26 ---------------------------------------------------
	 */
	/**
	 * Convenience constructor for initialising the digit {@code 0}
	 * @see #Digit(char)
	 */
	public Digit() {
		this('0');
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:10:51 ---------------------------------------------------
	 */
	/**
	 * Traverses the segment tree from this node if the sibling is an {@code instanceof} {@code Digit}
	 * class recursively incrementing the argument until a sibling is found that is not a {@code Digit} or {@code null}.
	 * @param start the start of the count.
	 * @return the number of consecutive digit siblings from this digit.
	 */
	private int getRemainingDigits(int start) {
		if (hasSibling())
			if (getSibling() instanceof Digit)
				return ((Digit) getSibling()).getRemainingDigits(start + 1);
		return start;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:28:17 ---------------------------------------------------
	 */
	/**
	 * Gets the number of remaining {@code Digit} nodes within the {@code Segment} tree by
	 * recursively traversing the tree and counting all consecutive {@code Digit} nodes.
	 * <p>Does not include {@code this} in the count.
	 * @return the count of consecutive {@code Digit} nodes from (but not including)
	 * {@code this} i.e the number of elements in a number tree excluding the first
	 * digit in the tree.
	 */
	protected int getRemainingDigits() {
		return getRemainingDigits(0);
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:32:53 ---------------------------------------------------
	 */
	/**
	 * Returns the image/expression of {@code this}.
	 * <p>The image is what gets appended to the {@code Appendable} argument
	 * during {@link #format(Appendable, Formatter, List)}.
	 * <p>The expression is what gets appended to the {@code Appendable} argument
	 * during {@link #toString(Appendable, Log, List)}.
	 * @return the image/expression of this {@code Digit}.
	 */
	public String getDigit() {
		return digit;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:38:01 ---------------------------------------------------
	 */
	/**
	 * Resets all mutable fields in a bid to maintain the immutability of this class.
	 * This clears the repeatend buffer, the incremented {@code recurCount} and {@code unitCount} and
	 * sets this node as the first index within consecutive digits in the tree.
	 */
	private void clearMutables() {
		repeatend.delete(0, repeatend.length());
		isFirstIndex = true;
		recurCount = 0;
		unitCount = 0;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:44:09 ---------------------------------------------------
	 */
	/**
	 * Carries out a 'forced' casting of {@code this} from the type given by {@link #getType()}
	 * to the type {@link #INTEGER}.
	 * <p>
	 * This should be called automatically by code within, calling manually may yield unexpected
	 * results.
	 * @return {@code this} as a new object (immutability) after casting it's type property to
	 * {@link #INTEGER}.
	 */
	protected Digit toDigit() {
		clearMutables();
		return new Digit(digit, INTEGER, isFocused(), hasError(), dp,
				isDigit(getSibling()) ? ((Digit) getSibling()).toDigit() : getSibling());
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:46:24 ---------------------------------------------------
	 */
	/**
	 * Carries out a 'forced' casting of {@code this} from the type given by {@link #getType()}
	 * to the type {@link #MANTISSA}.
	 * <p>
	 * This should be called automatically by code within, calling manually may yield unexpected
	 * results.
	 * @return {@code this} as a new object (immutability) after casting it's type property to
	 * {@link #MANTISSA}.
	 */
	protected Digit toMantissaDigit() {
		clearMutables();
		return new Digit(digit, MANTISSA, isFocused(), hasError(), dp,
				isDigit(getSibling()) ? ((Digit) getSibling()).toMantissaDigit() : getSibling());
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:49:33 ---------------------------------------------------
	 */
	/**
	 * Carries out a 'forced' casting of {@code this} from the type given by {@link #getType()}
	 * to the type argument.
	 * <p>
	 * This should be called automatically by code within, calling manually may yield unexpected
	 * results.
	 * @param type the type to which {@code this} will be cast.
	 * @return {@code this} as a new object (immutability) after casting it's type property to
	 * the argument.
	 */
	protected Digit toRecurringDigit(int type) {
		clearMutables();
		return new Digit(digit, type, isFocused(), hasError(), dp,
				isDigit(getSibling()) ? ((Digit) getSibling()).toRecurringDigit(type) : getSibling());
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:51:23 ---------------------------------------------------
	 */
	/**
	 * Facilitates concatenation of the argument node to the tree of which {@code this} is a node of.
	 * @param s {@inheritDoc}
	 * @return {@code this} as the head of the tree whereby the argument is now the tail (last index).
	 * In practice, this will be called recursively and unless {@code this} is the head of the tree
	 * that initiated the call, it may not be directly returned.
	 * @throws NullPointerException if the argument is {@code null}
	 */
	@Override
	public LinkedSegment concat(LinkedSegment s) {
		return new Digit(digit, getType(), isFocused(), hasError(), dp, hasSibling() ? getSibling().concat(s) : s);
	}

	/*
	 * 
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:03:53 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @param i {@inheritDoc}
	 * @param f {@inheritDoc}
	 * @return {@inheritDoc}
	 * @throws IndexOutOfBoundsException if i will reference a {@code null} node.
	 */
	@Override
	public LinkedSegment setFocus(int i, boolean f) {
		if (i == 0)
			return new Digit(digit, getType(), f, hasError(), dp, getSibling());
		else if (i > 0 && hasSibling())
			return new Digit(digit, getType(), isFocused(), hasError(), dp, getSibling().setFocus(i - 1, f));
		throw new IndexOutOfBoundsException(i);
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:04:51 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @param i {@inheritDoc}
	 * @param e {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public LinkedSegment setError(int i, boolean e) {
		if (i == 0)
			return new Digit(digit, getType(), isFocused(), e, dp, getSibling());
		else if (i > 0 && hasSibling())
			return new Digit(digit, getType(), isFocused(), hasError(), dp, getSibling().setError(i - 1, e));
		throw new IndexOutOfBoundsException(i);
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:05:54 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @param i {@inheritDoc}
	 * @param s {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public LinkedSegment setSibling(int i, LinkedSegment s) {
		if (i == 0) {
			if (s != null) {
				switch (getType()) {
				case INTEGER:// this is an integer digit
					switch (s.getType()) {// the type of next segment
					case MANTISSA:
					case ARC:
					case DOT:
					case DOT_ALL:
					case DOT_BAR:
					case ELLIPSIS:
					case PARENTHESISED:
					case VINCULUM:
						return new Digit(digit, getType(), isFocused(), hasError(), dp, ((Digit) s).toDigit());
					case INTEGER:
					default:
						break;
					}
				case MANTISSA:// this is a mantissa digit
					switch (s.getType()) {// the type of next segment
					case INTEGER:
					case ARC:
					case DOT:
					case DOT_ALL:
					case DOT_BAR:
					case ELLIPSIS:
					case PARENTHESISED:
					case VINCULUM:
						return new Digit(digit, getType(), isFocused(), hasError(), dp, ((Digit) s).toDigit());
					case MANTISSA:// the next is also a mantissa
					default:
						break;
					}
				case ARC:// this is an arc recurring digit
				case DOT:// this is a dot first and last only recurring digit
				case DOT_ALL:// this is a dot all recurring digit
				case DOT_BAR:// this a dot one or vinculum recurring digit
				case ELLIPSIS:// this is an ellipsis recurring digit
				case PARENTHESISED:// this is an enclose recurring digit (parenthesis)
				case VINCULUM:// this is a vinculum recurring digit
					if (s instanceof Digit && !isRecurring(s))
						return new Digit(digit, getType(), isFocused(), hasError(), dp,
								((Digit) s).toRecurringDigit(getType()));
				default: // assume it is non-digit
				}
			} // end if
			return new Digit(digit, getType(), isFocused(), hasError(), dp, s);
		} else if (i > 0 && hasSibling()) {
			return new Digit(digit, getType(), isFocused(), hasError(), dp, getSibling().setSibling(i - 1, s));
		}
		throw new IndexOutOfBoundsException(i);
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:06:10 ---------------------------------------------------
	 */
	/**
	 * Renders {@code this} to a MathJax LaTeX string and appends to the given {@code Appendable}.
	 * This method specialises in formatting recurring digits marked bas the type {@link #DOT_ALL}.
	 * @param a the {@code Appendable} which will build the LaTeX render string.
	 * @param f give additional styling to the resulting LaTeX code. The value given by {@code #getDigit()}
	 * is passed to this parameter
	 * @param p passed to the {@link Formatter#format(Segment, String, int, List)} and sibling's
	 * {@link LinkedSegment#format(Appendable, Formatter, List)} methods.
	 * @throws IndexOutOfBoundsException if the positional indices given by the {@code List} argument
	 * fails to reach a non-null node.
	 */
	private void formatDotAll(Appendable a, Formatter f, List<Integer> p) {
		try {
			a.append(f.format(this, String.format("\\dot{%s}", digit), getType(), p));
		} catch (IOException e) {
		}
		unitCount += 1;
		recurCount += 1;
		if (hasSibling()) {
			if (hasValidSibling(this, getType())) {
				Digit n = (Digit) getSibling();
				n.unitCount = unitCount;
				n.recurCount = recurCount;
				if (unitCount % dp.getMantGroupSize() == 0)
					try {
						a.append(f.format(null, dp.getMantSeparator(), SEPARATOR, null));
					} catch (IOException e) {
					}
			}
			getSibling().format(a, f, p);
		}
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:06:10 ---------------------------------------------------
	 */
	/**
	 * Renders {@code this} to a MathJax LaTeX string and appends to the given {@code Appendable}.
	 * This method specialises in formatting recurring digits marked bas the type {@link #VINCULUM}.
	 * @param a the {@code Appendable} which will build the LaTeX render string.
	 * @param f give additional styling to the resulting LaTeX code. The value given by {@code #getDigit()}
	 * is passed to this parameter
	 * @param p passed to the {@link Formatter#format(Segment, String, int, List)} and sibling's
	 * {@link LinkedSegment#format(Appendable, Formatter, List)} methods.
	 * @throws IndexOutOfBoundsException if the positional indices given by the {@code List} argument
	 * fails to reach a non-null node.
	 */
	private void formatVinc(Appendable a, Formatter f, List<Integer> p) {
		try {
			if (recurCount == 0)
				a.append("\\overline{");
			a.append(f.format(this, digit, getType(), p));
			unitCount += 1;
			recurCount += 1;
			if (!hasValidSibling(this, getType()))
				a.append('}');
		} catch (IOException e) {
		}

		if (hasSibling()) {
			if (hasValidSibling(this, getType())) {
				Digit n = (Digit) getSibling();
				n.unitCount = unitCount;
				n.recurCount = recurCount;
				if (unitCount % dp.getMantGroupSize() == 0)
					try {
						a.append(f.format(null, dp.getMantSeparator(), SEPARATOR, p));
					} catch (IOException e) {
					}
			}
			getSibling().format(a, f, p);
		}
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:06:10 ---------------------------------------------------
	 */
	/**
	 * Renders {@code this} to a MathJax LaTeX string and appends to the given {@code Appendable}.
	 * This method specialises in formatting recurring digits marked bas the type {@link #PARENTHESISED}.
	 * @param a the {@code Appendable} which will build the LaTeX render string.
	 * @param f give additional styling to the resulting LaTeX code. The value given by {@code #getDigit()}
	 * is passed to this parameter
	 * @param p passed to the {@link Formatter#format(Segment, String, int, List)} and sibling's
	 * {@link LinkedSegment#format(Appendable, Formatter, List)} methods.
	 * @throws IndexOutOfBoundsException if the positional indices given by the {@code List} argument
	 * fails to reach a non-null node.
	 */
	private void formatParen(Appendable a, Formatter f, List<Integer> p) {
		try {
			if (recurCount == 0)
				a.append(f.format(null, "(", DELIMITER, p));
			a.append(f.format(this, digit, getType(), p));
			unitCount += 1;
			recurCount += 1;
			if (!hasValidSibling(this, getType()))
				a.append(f.format(null, ")", DELIMITER, p));
		} catch (IOException e) {
		}

		if (hasSibling()) {
			if (hasValidSibling(this, getType())) {
				Digit n = (Digit) getSibling();
				n.unitCount = unitCount;
				n.recurCount = recurCount;
				if (unitCount % dp.getMantGroupSize() == 0)
					try {
						a.append(f.format(null, dp.getMantSeparator(), SEPARATOR, p));
					} catch (IOException e) {
					}
			}
			getSibling().format(a, f, p);
		}
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:06:10 ---------------------------------------------------
	 */
	/**
	 * Renders {@code this} to a MathJax LaTeX string and appends to the given {@code Appendable}.
	 * This method specialises in formatting recurring digits marked bas the type {@link #ARC}.
	 * @param a the {@code Appendable} which will build the LaTeX render string.
	 * @param f give additional styling to the resulting LaTeX code. The value given by {@code #getDigit()}
	 * is passed to this parameter
	 * @param p passed to the {@link Formatter#format(Segment, String, int, List)} and sibling's
	 * {@link LinkedSegment#format(Appendable, Formatter, List)} methods.
	 * @throws IndexOutOfBoundsException if the positional indices given by the {@code List} argument
	 * fails to reach a non-null node.
	 */
	private void formatArc(Appendable a, Formatter f, List<Integer> p) {
		try {
			if (recurCount == 0)
				a.append("\\overparen{");
			a.append(f.format(this, digit, getType(), p));
			unitCount += 1;
			recurCount += 1;
			if (!hasValidSibling(this, getType()))
				a.append('}');
		} catch (IOException e) {
		}

		if (hasSibling()) {
			if (hasValidSibling(this, getType())) {
				Digit n = (Digit) getSibling();
				n.unitCount = unitCount;
				n.recurCount = recurCount;
				if (unitCount % dp.getMantGroupSize() == 0)
					try {
						a.append(f.format(null, dp.getMantSeparator(), SEPARATOR, p));
					} catch (IOException e) {
					}
			}
			getSibling().format(a, f, p);
		}
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:06:10 ---------------------------------------------------
	 */
	/**
	 * Renders {@code this} to a MathJax LaTeX string and appends to the given {@code Appendable}.
	 * This method specialises in formatting recurring digits marked bas the type {@link #DOT_BAR}.
	 * @param a the {@code Appendable} which will build the LaTeX render string.
	 * @param f give additional styling to the resulting LaTeX code. The value given by {@code #getDigit()}
	 * is passed to this parameter
	 * @param p passed to the {@link Formatter#format(Segment, String, int, List)} and sibling's
	 * {@link LinkedSegment#format(Appendable, Formatter, List)} methods.
	 * @throws IndexOutOfBoundsException if the positional indices given by the {@code List} argument
	 * fails to reach a non-null node.
	 */
	private void formatDotBar(Appendable a, Formatter f, List<Integer> p) {
		try {
			if (recurCount == 0)
				if (!hasValidSibling(this, getType()))
					a.append("\\dot{");
				else
					a.append("\\overline{");
			a.append(f.format(this, digit, getType(), p));
			unitCount += 1;
			recurCount += 1;
			if (!hasValidSibling(this, getType()))
				a.append('}');
		} catch (IOException e) {
		}

		if (hasSibling()) {
			if (hasValidSibling(this, getType())) {
				Digit n = (Digit) getSibling();
				n.unitCount = unitCount;
				n.recurCount = recurCount;
				if (unitCount % dp.getMantGroupSize() == 0)
					try {
						a.append(f.format(null, dp.getMantSeparator(), SEPARATOR, p));
					} catch (IOException e) {
					}
			}
			getSibling().format(a, f, p);
		}
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:06:10 ---------------------------------------------------
	 */
	/**
	 * Renders {@code this} to a MathJax LaTeX string and appends to the given {@code Appendable}.
	 * This method specialises in formatting recurring digits marked bas the type {@link #DOT}.
	 * @param a the {@code Appendable} which will build the LaTeX render string.
	 * @param f give additional styling to the resulting LaTeX code. The value given by {@code #getDigit()}
	 * is passed to this parameter
	 * @param p passed to the {@link Formatter#format(Segment, String, int, List)} and sibling's
	 * {@link LinkedSegment#format(Appendable, Formatter, List)} methods.
	 * @throws IndexOutOfBoundsException if the positional indices given by the {@code List} argument
	 * fails to reach a non-null node.
	 */
	private void formatDot(Appendable a, Formatter f, List<Integer> p) {
		String sym = digit;
		if (recurCount == 0 || !hasValidSibling(this, getType()))
			sym = String.format("\\dot{%s}", sym);
		try {
			a.append(f.format(this, sym, getType(), p));
		} catch (IOException e) {
		}

		unitCount += 1;
		recurCount += 1;

		if (hasSibling()) {
			if (hasValidSibling(this, getType())) {
				Digit n = (Digit) getSibling();
				n.unitCount = unitCount;
				n.recurCount = recurCount;
				if (unitCount % dp.getMantGroupSize() == 0)
					try {
						a.append(f.format(null, dp.getMantSeparator(), SEPARATOR, p));
					} catch (IOException e) {
					}
			}
			getSibling().format(a, f, p);
		}
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:06:10 ---------------------------------------------------
	 */
	/**
	 * Called by {@link #formatEllip(Appendable, Formatter, List)} to render
	 * all recurring characters within the recurring parts of a number.
	 * @param a the {@code Appendable} which will build the LaTeX render string.
	 * @param f give additional styling to the resulting LaTeX code. The value given by {@code #getDigit()}
	 * is passed to this parameter
	 * @param p passed to the {@link Formatter#format(Segment, String, int, List)} and sibling's
	 * {@link LinkedSegment#format(Appendable, Formatter, List)} methods.
	 * @throws IndexOutOfBoundsException if the positional indices given by the {@code List} argument
	 * fails to reach a non-null node.
	 */
	private void appendRepeatend(Appendable a, Formatter f, List<Integer> p) {
		try {
			String r = repeatend.toString();
			for (int i = 1; i < dp.getNumOfRecurringDigits(); i++)
				repeatend.append(r);
			for (int i = 0; i < repeatend.length(); i++) {
				if ((unitCount + i) % dp.getMantGroupSize() == 0) {
					a.append(f.format(null, dp.getMantSeparator(), SEPARATOR, null));
				}
				String d = repeatend.substring(i, i + 1);
				a.append(f.format(null, d, AUTO_COMPLETE, null));
			}
			a.append(f.format(null, "\\ldots", AUTO_COMPLETE, null));
		} catch (IOException e) {
		}
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:06:10 ---------------------------------------------------
	 */
	/**
	 * Renders {@code this} to a MathJax LaTeX string and appends to the given {@code Appendable}.
	 * This method specialises in formatting recurring digits marked bas the type {@link #ELLIPSIS}.
	 * @param a the {@code Appendable} which will build the LaTeX render string.
	 * @param f give additional styling to the resulting LaTeX code. The value given by {@code #getDigit()}
	 * is passed to this parameter
	 * @param p passed to the {@link Formatter#format(Segment, String, int, List)} and sibling's
	 * {@link LinkedSegment#format(Appendable, Formatter, List)} methods.
	 * @throws IndexOutOfBoundsException if the positional indices given by the {@code List} argument
	 * fails to reach a non-null node.
	 */
	private void formatEllip(Appendable a, Formatter f, List<Integer> p) {
		try {
			a.append(f.format(this, digit, getType(), p));
		} catch (IOException e) {
		}

		repeatend.append(digit);
		unitCount += 1;

		if (hasSibling()) {
			if (sibIsSame()) {
				Digit n = (Digit) getSibling();
				n.unitCount = unitCount;
				n.repeatend.append(repeatend);
				if (unitCount % dp.getMantGroupSize() == 0) {
					try {
						a.append(f.format(null, dp.getMantSeparator(), SEPARATOR, null));
					} catch (IOException e) {
					}
				}
			} else
				appendRepeatend(a, f, p);
			getSibling().format(a, f, p);
		} else
			appendRepeatend(a, f, p);
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:06:10 ---------------------------------------------------
	 */
	/**
	 * Renders {@code this} to a MathJax LaTeX string and appends to the given {@code Appendable}.
	 * This method specialises in formatting recurring digits marked bas the type {@link #MANTISSA}.
	 * @param a the {@code Appendable} which will build the LaTeX render string.
	 * @param f give additional styling to the resulting LaTeX code. The value given by {@code #getDigit()}
	 * is passed to this parameter
	 * @param p passed to the {@link Formatter#format(Segment, String, int, List)} and sibling's
	 * {@link LinkedSegment#format(Appendable, Formatter, List)} methods.
	 * @throws IndexOutOfBoundsException if the positional indices given by the {@code List} argument
	 * fails to reach a non-null node.
	 */
	private void formatMant(Appendable a, Formatter f, List<Integer> p) {
		try {
			a.append(f.format(this, digit, getType(), p));
		} catch (IOException e) {
		}
		unitCount += 1;

		if (hasSibling()) {
			if (sibIsSame() || isRecurring(getSibling())) {
				((Digit) getSibling()).unitCount = unitCount;
				if (unitCount % dp.getMantGroupSize() == 0) {
					try {
						a.append(f.format(null, dp.getMantSeparator(), SEPARATOR, null));
					} catch (IOException e) {
					}
				}
			}
			getSibling().format(a, f, p);
		}
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:06:10 ---------------------------------------------------
	 */
	/**
	 * Renders {@code this} to a MathJax LaTeX string and appends to the given {@code Appendable}.
	 * This method specialises in formatting recurring digits marked bas the type {@link #INTEGER}.
	 * @param a the {@code Appendable} which will build the LaTeX render string.
	 * @param f give additional styling to the resulting LaTeX code. The value given by {@code #getDigit()}
	 * is passed to this parameter
	 * @param p passed to the {@link Formatter#format(Segment, String, int, List)} and sibling's
	 * {@link LinkedSegment#format(Appendable, Formatter, List)} methods.
	 * @throws IndexOutOfBoundsException if the positional indices given by the {@code List} argument
	 * fails to reach a non-null node.
	 */
	private void formatInt(Appendable a, Formatter f, List<Integer> p) {
		try {
			a.append(f.format(this, digit, getType(), p));
		} catch (IOException e) {
		}

		if (hasSibling()) {
			if (sibIsSame()) {
				int numLength = getRemainingDigits();
				if (numLength % dp.getIntGroupSize() == 0 && numLength > 0) {
					try {
						a.append(f.format(null, dp.getIntSeparator(), SEPARATOR, null));
					} catch (IOException e) {
					}
				}
			}
			getSibling().format(a, f, p);
		}
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:25:58 ---------------------------------------------------
	 */
	/**
	 * Checks whether the argument is non-null and is an {@code instanceof} {@code Digit} class.
	 * @param s the value to be checked
	 * @return {@code true} if the argument is an {@code instanceof} this class, otherwise, returns {@code false}.
	 */
	private static boolean isDigit(Segment s) {
		// return s != null && (s.getType() >= INTEGER && s.getType() <= PARENTHESIS);
		return s != null && (s instanceof Digit);
	}

	/*
	 * sibling is same type as parent segment
	 */
	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:30:19 ---------------------------------------------------
	 */
	/**
	 * Checks whether or not the argument is the same type as it's sibling.
	 * Does not check whether or not the sibling is {@code null}.
	 * @param s the value to be checked.
	 * @return {@code true} if the argument is the same type as it's sibling.
	 */
	private static boolean sibIsSame(LinkedSegment s) {
		return s.getType() == s.getSibling().getType();
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:06:10 ---------------------------------------------------
	 */
	/**
	 * Calls {@linkplain #sibIsSame()} with {@code this} as the argument.
	 * @return {@code true} if the sibling has the same type as {@code this}.
	 */
	private boolean sibIsSame() {
		return sibIsSame(this);
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:39:48 ---------------------------------------------------
	 */
	/**
	 * Checks whether the argument is a recurring type.
	 * @param s the value to be checked.
	 * @return {@code true} if the argument is a recurring type.
	 */
	private static boolean isRecurring(Segment s) {
		return isDigit(s) && s.getType() != INTEGER && s.getType() != MANTISSA;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:39:48 ---------------------------------------------------
	 */
	/**
	 * Checks whether the argument has a sibling of the same type as as the {@code int} argument.
	 * @param s the value to be checked.
	 * @param type the type
	 * @return {@code true} if the argument's sibling is the same as type.
	 */
	private static boolean hasValidSibling(LinkedSegment s, int type) {
		return s.hasSibling() && s.getSibling().getType() == type;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:39:48 ---------------------------------------------------
	 */
	/**
	 * Checks whether the argument is a varaiable specified by {@link #CONSTANT},  {@link #VAR_FREE},
	 *  {@link #VAR_BOUND} or {@link #FUNCTION}.
	 * @param s the value to be checked.
	 * @return {@code true} if the argument is a variable type.
	 */
	private static boolean isVar(Segment s) {
//		System.err.println(s.getType());
		return s.getType() == CONSTANT || s.getType() == VAR_FREE || s.getType() == VAR_BOUND
				|| s.getType() == FUNCTION;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:44:36 ---------------------------------------------------
	 */
	/**
	 * Renders {@code this} in LaTeX form.
	 * @param a {@inheritDoc}
	 * @param f {@inheritDoc}
	 * @param p {@inheritDoc}
	 */
	@Override
	public void format(Appendable a, Formatter f, List<Integer> p) {

		/* Update the current position of the cursor */
		p.set(p.size() - 1, p.get(p.size() - 1) + 1);

		switch (getType()) {
		case ARC:
			formatArc(a, f, p);
			break;
		case DOT:
			formatDot(a, f, p);
			break;
		case DOT_ALL:
			formatDotAll(a, f, p);
			break;
		case DOT_BAR:
			formatDotBar(a, f, p);
			break;
		case ELLIPSIS:
			formatEllip(a, f, p);
			break;
		case PARENTHESISED:
			formatParen(a, f, p);
			break;
		case VINCULUM:
			formatVinc(a, f, p);
			break;
		case MANTISSA:
			formatMant(a, f, p);
			break;
		case INTEGER:
		default:
			formatInt(a, f, p);
			break;
		}// End switch
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:45:56 ---------------------------------------------------
	 */
	/**
	 * Generates an expression compatible with the Symja evaluator.
	 * @param a {@inheritDoc}
	 * @param l {@inheritDoc}
	 * @param p {@inheritDoc}
	 */
	@Override
	public void toString(Appendable a, Log l, List<Integer> p) {
		p.set(p.size() - 1, p.get(p.size() - 1) + 1);
		try {
			/*
			 * So this method will throw a parse exception here but it will be caught and
			 * will save the position in which it threw said exception, and then throw it.
			 */
			switch (getType()) {
			case ARC:
			case DOT:
			case DOT_ALL:
			case DOT_BAR:
			case ELLIPSIS:
			case PARENTHESISED:
			case VINCULUM:
				if (isFirstIndex)
					a.append('r');
				if (hasSibling() && isRecurring(getSibling()))
					((Digit) getSibling()).isFirstIndex = false;
			case INTEGER:
			case MANTISSA:
			default:
				a.append(digit);
			}
		} catch (IOException e) {
		} catch (FatalReadException e) {
			throw new FatalParseException(e.getMessage(), e, p);
		}

		if (hasSibling()) {
			if ((isVar(getSibling()) || getSibling().getType() == L_PARENTHESIS) && !(getSibling() instanceof Digit))
				try {
					a.append('*');
				} catch (IOException e) {
				}
			getSibling().toString(a, l, p);
		}
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:47:36 ---------------------------------------------------
	 */
	/**
	 * Implemented to enable use in {@code java.util} collections.
	 * {@inheritDoc}
	 * @param obj {@inheritDoc}
	 * @return {@code true} if the argument is an instance of {@code Digit}
	 * and has the same type as {@code this}.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Digit) {
			Digit d = (Digit) obj;
			return getType() == d.getType() && digit.equals(d.digit);
		}
		return false;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:49:11 ---------------------------------------------------
	 */
	/**
	 * Prints the field name that was used to declare the type at {@code Segment.Type}.
	 * @return
	 */
	@Override
	public String toString() {
		switch (getType()) {
		case INTEGER:
		default:
			return "INTEGER";
		case MANTISSA:
			return "MANTISSA";
		case ARC:
			return "ARC";
		case DOT:
			return "DOT";
		case DOT_ALL:
			return "DOT_ALL";
		case DOT_BAR:
			return "DOT_BAR";
		case ELLIPSIS:
			return "ELLIPSIS";
		case PARENTHESISED:
			return "PARENTHESIS";
		case VINCULUM:
			return "VINCULUM";
		}
	}

	/**
	 * The image/expression of this digit.
	 */
	private final String digit;
	/**
	 * The punctuation options
	 */
	private final DigitPunc dp;
	/**
	 * A StringBuilder for storing the repeatend of the ellipsis recurring type
	 */
	private final StringBuilder repeatend;// for the ellipsis recurring type
	/**
	 * Field for specifying that this is the first digit in a number sub-tree.
	 * A number sub-tree is (or part of) a {@code LinkedSegment} tree consisting entirely
	 * of {@code Digit} objects.
	 */
	private boolean isFirstIndex;// for all recurring type
	/**
	 * Used (by some recurring types) for storing the number of digits
	 * in the recurring portion of the number tree.
	 */
	private int recurCount;// for some recurring types
	/**
	 * Used by mantissa types for storing the number of digits
	 * in the mantissa portion of the number tree.
	 */
	private int unitCount;// for mantissas
}