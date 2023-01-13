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
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class NumberAdapter implements Appendable, CharSequence {

	public NumberAdapter(int capacity) {
		buffer = new StringBuilder(capacity);
//		num = new StringBuilder();
		lastNumericalStart = -1;
	}

	public NumberAdapter() {
		this(25);
	}

	/*
	 * Most Recent Date: 27 Sep 2022-----------------------------------------------
	 * Most recent time created: 12:49:32--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
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
	 * @param index
	 * @return
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
	 * @param start
	 * @param end
	 * @return
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
	 * {@inheritDoc}
	 * 
	 * @param csq
	 * @return
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
	 * 
	 * @param csq
	 * @param start
	 * @param end
	 * @return
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
	 * 
	 * @param c
	 * @return
	 */
	@Override
	public NumberAdapter append(char c) {
		return append(String.valueOf(c));
	}

	private static void ensureIsNum(String n) {
		if (!Utility.isNumber(n))
			throw new NumberFormatException("number cannot contain non-digits");
	}

	private static void ensureIsRecur(String r) {
		if (!Utility.isNumber(r))
			throw new NumberFormatException("recurring part cannot contain non-digits");
		if (r.contains("+") || r.contains("-"))
			throw new NumberFormatException("recurring part cannot be signed");
	}

	private StringBuilder wrap() {
		int lastNumericalStart = this.lastNumericalStart;
		StringBuilder buffer = new StringBuilder(this.buffer.toString());
		if (lastNumericalStart > -1) {
			String s = buffer.substring(lastNumericalStart, buffer.length()).toLowerCase();
			if (!Utility.isNumber(s)) {
				final String non_exponent = s.substring(0, s.indexOf('r'));
				ensureIsNum(non_exponent);
				final String recur;
				final String exp;
				if (s.contains("e")) {
					recur = s.substring(s.indexOf('r') + 1, s.indexOf('e'));
					ensureIsRecur(recur);
					exp = s.substring(s.indexOf('e'));
					ensureIsNum(exp.substring(1));
				} else {
					recur = s.substring(s.indexOf('r'), s.length());
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
		return buffer;
	}
	
	private void destructiveWrap() {
		if (lastNumericalStart >= 0) {
			String s = buffer.substring(lastNumericalStart, buffer.length()).toLowerCase();
			if (!Utility.isNumber(s)) {
				final String non_exponent = s.substring(0, s.indexOf('r'));
				ensureIsNum(non_exponent);
				final String recur;
				final String exp;
				if (s.contains("e")) {
					recur = s.substring(s.indexOf('r') + 1, s.indexOf('e'));
					ensureIsRecur(recur);
					exp = s.substring(s.indexOf('e'));
					ensureIsNum(exp);
				} else {
					recur = s.substring(s.indexOf('r'), s.length());
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

	@Override
	public String toString() {
		return wrap().toString();
	}

	private final StringBuilder buffer;
	private int lastNumericalStart;
}
