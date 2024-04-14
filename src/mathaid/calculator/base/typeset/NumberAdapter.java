/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.math.BigDecimal;

import mathaid.calculator.base.util.Utility;
import mathaid.calculator.base.value.BigFraction;

/*
 * Date: 27 Sep 2022----------------------------------------------------------- 
 * Time created: 12:47:45---------------------------------------------------  
 * Package: mathaid.calculator.base.typeset------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: NumberAdapter.java------------------------------------------------------ 
 * Class name: NumberAdapter------------------------------------------------ 
 */
/**
 * Provides support for a proper handling of the mathaid number syntax which is
 * given by:
 * 
 * <pre>
 * <code>[+|-][integer][.[mantissa][(r|R)[recurring digits]][(e|E)[+|-][exponent digits]]]</code>
 * </pre>
 * 
 * which when translated to BNF (Backus-Naur form) is:
 * 
 * <pre>
 *	<code>
 *		&lt;digit&gt;              ::= "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" | "0"
 *		&lt;negative-sign&gt;      ::= "-"
 *		&lt;positive-sign&gt;      ::= "+"
 *		&lt;sign&gt;               ::= &lt;positive-sign&gt; | &lt;negative-sign&gt;
 *		&lt;digits&gt;             ::= &lt;digit&gt; | &lt;digit&gt; &lt;digits&gt;
 *		&lt;integer&gt;            ::= &lt;digits&gt; | &lt;sign&gt; &lt;digits&gt;
 *		&lt;significand&gt;        ::= &lt;integer&gt; | "." &lt;digits&gt; | &lt;integer&gt; "." &lt;digits&gt;
 *		&lt;exponent-prefix&gt;    ::= "e" | "E"
 *		&lt;exponent&gt;           ::= &lt;exponent-prefix&gt; &lt;integer&gt;
 *		&lt;repeatend-prefix&gt;   ::= "r" | "R"
 *		&lt;repeatend&gt;          ::= &lt;repeatend-part&gt; &lt;digits&gt;
 *		&lt;number&gt;             ::= &lt;significand&gt; | &lt;significand&gt; &lt;repeatend&gt; | &lt;significand&gt; &lt;exponent&gt; | &lt;significand&gt; &lt;repeatend&gt; &lt;exponent&gt;
 *	</code>
 * </pre>
 * 
 * The above syntax is replaced by the Symja expression string:
 * 
 * <pre>
 * <code>Rational[numerator, denominator]</code>
 * </pre>
 * 
 * after being converted to a fraction using the {@code BigFraction} class.
 * These operations are done whenever {@link #toString} is called.
 * <p>
 * This is needed because the above format is not supported by the
 * {@code BigDecimal} class or any other numerical string class. Symja,
 * Mathematical also don't support it. Hence an intermediary object is needed to
 * temporarily handle it so as to avoid preventable exceptions.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 * 
 * @apiNote In future releases, the following syntax <code>1.23R10E-4</code>
 *          will become <code>{1}_10.{2}{3}_10({3}_10)@-{4}_10</code>. Where the
 *          braces will be used for a digit that consists of multiple characters
 *          and the {@code '_'} will be used as radix specifier, the parenthesis
 *          will be used as repeatend specifier and the {@code @} will be used
 *          as exponent specifier (to prevent collision with alphanumeric
 *          characters in radixes greater than 10).
 */
public class NumberAdapter implements Appendable, CharSequence {

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 21:10:59 ---------------------------------------------------
	 */
	/**
	 * Creates a {@code NumberAdapter} by specifying the capacity of the internal
	 * buffer.
	 * 
	 * @param capacity the specified capacity assigned to the internal buffers.
	 * @see StringBuilder#StringBuilder(int)
	 * @see StringBuffer#StringBuffer(int)
	 */
	public NumberAdapter(int capacity) {
		buffer = new StringBuilder(capacity);
		lastNumericalStart = -1;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 21:16:56 ---------------------------------------------------
	 */
	/**
	 * Initializes a {@code NumberAdapter} with a capacity of 25.
	 */
	public NumberAdapter() {
		this(25);
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 08:33:03 ---------------------------------------------------
	 */
	/**
	 * Initialises this object with the argument as the initial contents of the
	 * internal buffer
	 * 
	 * @param c the value which is the first contents to be appended to the internal
	 *          buffer.
	 */
	public NumberAdapter(CharSequence c) {
		this(c.length());
		append(c);
	}
	
	public NumberAdapter clear() {
		buffer.delete(0, buffer.length());
		lastNumericalStart = -1;
		return this;
	}
	
	public NumberAdapter deleteAll() {
		return clear();
	}
	
	public NumberAdapter removeAll() {
		return clear();
	}

	/*
	 * Most Recent Date: 27 Sep 2022-----------------------------------------------
	 * Most recent time created: 12:49:32--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public int length() {
		return wrap().length();
	}

	/*
	 * Most Recent Date: 27 Sep 2022-----------------------------------------------
	 * Most recent time created: 12:49:32--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param index {@inheritDoc}
	 * @return {@inheritDoc}
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	@Override
	public char charAt(int index) {
		return wrap().charAt(index);
	}

	/*
	 * Most Recent Date: 27 Sep 2022-----------------------------------------------
	 * Most recent time created: 12:49:32--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param start {@inheritDoc}
	 * @param end   {@inheritDoc}
	 * @return {@inheritDoc}
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	@Override
	public CharSequence subSequence(int start, int end) {
		return wrap().subSequence(start, end);
	}

	/*
	 * Most Recent Date: 27 Sep 2022-----------------------------------------------
	 * Most recent time created: 12:49:32--------------------------------------
	 */
	/**
	 * Appends the argument to the internal buffer. If the argument is a completion
	 * of the mathaid recurring number syntax, then it will be formatted to a
	 * fraction format supported by the SYMJA library before the appendage takes
	 * place.
	 * 
	 * @param csq {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public NumberAdapter append(CharSequence csq) {
		String s = String.valueOf(csq);
		boolean isNumber = Utility.isNumber(s);
		if (isNumber && lastNumericalStart < 0)
			lastNumericalStart = buffer.length();
		else if (lastNumericalStart >= 0 && !isNumber) {
			switch (s) {
			case ".":
			case "r":
			case "R":
			case "e":
			case "E":
				break;
			case "-":
			case "+": {
				// If the previous character is not the exponent character
				if (buffer.length() > 0 && !String.valueOf(buffer.charAt(buffer.length() - 1)).equalsIgnoreCase("e")) {
					destructiveWrap();
				}
				break;
			}
			default:
				destructiveWrap();
			}
		}
		buffer.append(csq);
		return this;
	}

	/*
	 * Most Recent Date: 27 Sep 2022-----------------------------------------------
	 * Most recent time created: 12:49:32--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * <p>
	 * If the argument is a completion of the mathaid recurring number syntax, then
	 * it will be formatted to a fraction format supported by the SYMJA library
	 * before the appendage takes place.
	 * 
	 * @param csq   {@inheritDoc}
	 * @param start {@inheritDoc}
	 * @param end   {@inheritDoc}
	 * @return {@inheritDoc}
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	@Override
	public NumberAdapter append(CharSequence csq, int start, int end) {
		return append(csq.subSequence(start, end));
	}

	/*
	 * Most Recent Date: 27 Sep 2022-----------------------------------------------
	 * Most recent time created: 12:49:32--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * <p>
	 * If the argument is a completion of the mathaid recurring number syntax, then
	 * it will be formatted to a fraction format supported by the SYMJA library
	 * before the appendage takes place.
	 * 
	 * @param c {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public NumberAdapter append(char c) {
		return append(String.valueOf(c));
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:58:04 ---------------------------------------------------
	 */
	/**
	 * Checks if the argument is a number by invoking
	 * {@link Utility#isNumber(String)} and throws a {@code NumberFormatException}
	 * if it isn't i.e the check is <code>false</code>.
	 * 
	 * @param n the value to be checked.
	 * @throws NumberFormatException if the argument is not a valid number that can
	 *                               be used as the argument to
	 *                               {@link BigDecimal#BigDecimal(String)}.
	 */
	private static void ensureIsNum(String n) {
		if (!Utility.isNumber(n))
			throw new NumberFormatException("number cannot contain non-digits");
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 21:01:40 ---------------------------------------------------
	 */
	/**
	 * Checks if the argument is a number and is unsigned. When any of the
	 * conditions is {@code false} a {@code NumberFormatException} will be thrown.
	 * 
	 * @param r the value to be checked. It is presumed that this value is the
	 *          numerical part of the mathaid recurring format which is:
	 * 
	 *          <pre>
	 * <code>[+|-][integer][.[mantissa][(r|R)[recurring digits]][(e|E)[+|-][exponent digits]]]</code>
	 *          </pre>
	 * 
	 * @throws NumberFormatException if the argument is not a number or if the
	 *                               argument is signed with either a {@code +} or
	 *                               {@code -}.
	 */
	private static void ensureIsRecur(String r) {
		if (!Utility.isNumber(r))
			throw new NumberFormatException("recurring part cannot contain non-digits");
		if (r.contains("+") || r.contains("-"))
			throw new NumberFormatException("recurring part cannot be signed");
	}

	/*
	 * Date: 19 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:40:35 ---------------------------------------------------
	 */
	/**
	 * If the {@link #lastNumericalStart} value greater than {@code -1}, get all the
	 * characters from this index to the end of the buffer; check if it is a number;
	 * if it is not, then retrieve all characters that are not part of the recurring
	 * digit or the exponent, throwing an exception if there is any non-digit in it;
	 * extract from it the exponent and the recurring part, ensuring that both parts
	 * are valid; construct a {@code BigFraction} from all 3 parts the exponent,
	 * recurring and number part; format it to the string
	 * {@code Rational[numerator, denominator]}; replace the non-formatted value was
	 * made with the formatted one and then return the buffer. Note that this buffer
	 * is not the same as the internal buffer (which continues to contain the
	 * original contents).
	 * <p>
	 * This does not assign the {@code lastNumericalStart} field.
	 * 
	 * @return a {@code StringBuilder} whereby all mathaid recurring formats are
	 *         converted to Symja rational format.
	 */
	private StringBuilder wrap() {
		/*
		 * commented, because it seems redundant. See comment at the end of the method.
		 */
//		int lastNumericalStart = this.lastNumericalStart;
		StringBuilder buffer = new StringBuilder(this.buffer.toString());
		if (lastNumericalStart > -1) {
			String s = buffer.substring(lastNumericalStart, buffer.length()).toLowerCase();
			if (!Utility.isNumber(s)) {
				int rIndex = s.indexOf('r');// recurring index
				int eIndex = s.indexOf('e');// exponent index
				final String non_exponent = s.substring(0, rIndex > -1 ? s.indexOf('r') : eIndex);
				ensureIsNum(non_exponent);
				final String recur;
				final String exp;
				if (eIndex > -1) {
					recur = rIndex > -1 ? s.substring(rIndex + 1, eIndex) : s.substring(eIndex + 1);
					ensureIsRecur(recur);
					exp = s.substring(eIndex);
					ensureIsNum(exp.substring(1));
				} else {
					recur = s.substring(rIndex, s.length());
					ensureIsRecur(recur);
					exp = "";
				}
				BigFraction asFrac = Utility.fromDecimal(new BigDecimal(String.format("%1$s%2$s", non_exponent, exp)),
						recur);
				String replacement = String.format("Rational[%1$s, %2$s]", asFrac.getNumerator(),
						asFrac.getDenominator());
				buffer.replace(lastNumericalStart, buffer.length(), replacement);
			}
			/* Since this is not supposed to be a mutative method, this seems redundant */
//			lastNumericalStart = -1;
		}
		return buffer;
	}

	/*
	 * Date: 20 Nov 2023 -----------------------------------------------------------
	 * Time created: 08:53:58 ---------------------------------------------------
	 */
	/**
	 * Performs the {@link #wrap()} operation directly on the internal buffer,
	 * resetting the {@link #lastNumericalStart} value to {@code -1} afterwards.
	 * Useful for procedural and gradual formatting/building/appendage.
	 */
	private void destructiveWrap() {
		if (lastNumericalStart > -1) {
			String s = buffer.substring(lastNumericalStart, buffer.length()).toLowerCase();
			if (!Utility.isNumber(s)) {
				int rIndex = s.indexOf('r');// recurring index
				int eIndex = s.indexOf('e');// exponent index
				final String non_exponent = s.substring(0, rIndex > -1 ? s.indexOf('r') : eIndex);
				ensureIsNum(non_exponent);
				final String recur;
				final String exp;
				if (eIndex > -1) {
					recur = rIndex > -1 ? s.substring(rIndex + 1, eIndex) : s.substring(eIndex + 1);
					ensureIsRecur(recur);
					exp = s.substring(eIndex);
					ensureIsNum(exp);
				} else {
					recur = s.substring(rIndex, s.length());
					ensureIsRecur(recur);
					exp = "";
				}
				BigFraction asFrac = Utility.fromDecimal(new BigDecimal(String.format("%1$s%2$s", non_exponent, exp)),
						recur);
				String replacement = String.format("Rational[%1$s, %2$s]", asFrac.getNumerator(),
						asFrac.getDenominator());
				buffer.replace(lastNumericalStart, buffer.length(), replacement);
			}
			lastNumericalStart = -1;
		}
	}

	/*
	 * Date: 19 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:39:41 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public String toString() {
		return wrap().toString();
	}

	/**
	 * The internal buffer. It is the object that backs the internal implementation
	 */
	private final StringBuilder buffer;
	/**
	 * Stores the last position where a decimal digit was encountered.
	 * <p>
	 * Characters are encountered each time any of the {@code append} methods are
	 * called, when such a character is a decimal, then this value will be set to
	 * the position that this character holds within the internal buffer.
	 */
	private int lastNumericalStart;
}
