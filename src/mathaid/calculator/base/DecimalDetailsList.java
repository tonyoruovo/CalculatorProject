/**
 * 
 */
package mathaid.calculator.base;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import mathaid.calculator.base.util.Utility;
import mathaid.calculator.base.value.BigFraction;
import mathaid.calculator.base.value.EngNotation;

/*
 * Date: 13 Apr 2021----------------------------------------------------------- 
 * Time created: 13:30:56---------------------------------------------------  
 * Package: mathaid.calculator.base------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: DecimalDetailsList.java------------------------------------------------------ 
 * Class name: DecimalDetailsList------------------------------------------------ 
 */
/**
 * A {@code DetailsList} helper class. It can also be used to retrieve decimals
 * in TeX notation, but users should refrain from doing that in isolation because
 * values returned by methods are dependent upon the current state of the
 * {@code Settings} object.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
final class DecimalDetailsList {

	/*
	 * Date: 13 Apr 2021-----------------------------------------------------------
	 * Time created: 13:30:56---------------------------------------------------
	 */
	/**
	 * Initialises this object with a specified value as a decimal string.
	 * 
	 * @param decimal the string representation of a decimal.
	 */
	public DecimalDetailsList(String decimal) {
		this.bf = new BigFraction(new BigDecimal(decimal),
				new MathContext(Settings.defaultSetting().getScale(), RoundingMode.HALF_EVEN), null,
				BigFraction.ONE.getAccuracy());
	}

	/*
	 * Date: 10 May 2021-----------------------------------------------------------
	 * Time created: 13:32:13---------------------------------------------------
	 */
	/**
	 * Initialises this object with a specified rational value. This constructor is
	 * most optimal because of the value returned by {@link #getExpression()} which
	 * appropriately display recurring digits (albeit in TeX notation).
	 * 
	 * @param f A {@code BigFraction} that is already rounded to the settings of the
	 *          calculator.
	 */
	public DecimalDetailsList(BigFraction f) {
		this.bf = f;
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 10:28:35--------------------------------------------
	 */
	/**
	 * Returns the TeX notation for the decimal (specified at the constructor). This
	 * may also display recurring digits.
	 * 
	 * @return a {@code DataText} with the TeX notation and the
	 *         {@link BigDecimal#toPlainString() plain string} as its components.
	 */
	public DataText getExpression() {
		String teX = asTeXRecurring(bf);
		return new DataText(teX, bf.getFraction().toPlainString());
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 10:30:53--------------------------------------------
	 */
	/**
	 * Returns the TeX notation for the decimal (specified at the constructor) in
	 * scientific notation. This may also display recurring digits.
	 * 
	 * @return a {@code DataText} with the scientific TeX notation and the
	 *         {@link BigDecimal#toPlainString() plain string} as its components.
	 */
	public DataText getSciExpression() {

		final class Sci extends EngNotation {
			private static final long serialVersionUID = 1L;

			public Sci(int n) {
				super(n);
			}

			@Override
			public String toString(BigDecimal n) {
				return toString(n, true);
			}
		}

		Settings s = Settings.defaultSetting();

		Sci sci = new Sci(s.getScale());

		String sign = bf.signum() < 0 ? "-" : "";
		StringBuilder numString = new StringBuilder(sci.toString(bf.abs().getFraction()));
		BigDecimal exp = new BigDecimal(numString.substring(numString.indexOf("E") + 1));
		numString = numString.delete(numString.indexOf("E"), numString.length());
		boolean isRecurring = bf.isRecurring();
		BigDecimal[] bd = bf.getRecurring();
		String part1 = bd[0].toPlainString();
		int firstLength = part1.length() + (part1.contains(".") ? 0 : 1);
		if (isRecurring && (s.getScale() > bf.getPeriod() + firstLength + 1)) {
			numString.delete(firstLength, numString.length());
			bd[0] = new BigDecimal(numString.toString());
//				bd[1] = new BigDecimal(bd[1].toPlainString());
			bd[1] = bd[1].movePointRight(bd[0].scale());
			format(bd, (numString = new StringBuilder()), "\\overline{", "}", s.getDecimalPoint(), s.getDigitsPerUnit(),
					s.getIntDivider(), s.getFracDivider(), Utility.isInteger(bd[0]) && bd[1].signum() == 0);
		} else {
			bd = new BigDecimal[] { new BigDecimal(numString.toString()), BigDecimal.ZERO };
			format(bd, (numString = new StringBuilder()), "\\overline{", "}", s.getDecimalPoint(), s.getDigitsPerUnit(),
					s.getIntDivider(), s.getFracDivider(), Utility.isInteger(bd[0]) && bd[1].signum() == 0);
		}
		numString.append(s.getMultiplicationSign() + BigDecimal.TEN + getExponent(exp));
		return new DataText(numString.insert(0, sign).toString(), bf.getFraction().toPlainString());

	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 10:33:38--------------------------------------------
	 */
	/**
	 * Returns the TeX notation for the decimal (specified at the constructor) in
	 * engineering notation. This may also display recurring digits.
	 * 
	 * @return a {@code DataText} with the engineering TeX notation and the
	 *         {@link BigDecimal#toPlainString() plain string} as its components.
	 */
	public DataText getEngExpression() {
		Settings s = Settings.defaultSetting();

		EngNotation eng = new EngNotation(s.getScale(), s.getEngineering());

		String sign = bf.signum() < 0 ? "-" : "";
		StringBuilder numString = new StringBuilder(eng.toString(bf.abs().getFraction()));
		BigDecimal exp = new BigDecimal(numString.substring(numString.indexOf("E") + 1));
		numString = numString.delete(numString.indexOf("E"), numString.length());
		boolean isRecurring = bf.isRecurring();
		BigDecimal[] bd = bf.getRecurring();
		String part1 = bd[0].toPlainString();
		int firstLength = part1.length() + (part1.contains(".") ? 0 : 1);
		if (isRecurring && (s.getScale() > bf.getPeriod() + firstLength + 1)) {
			numString.delete(firstLength, numString.length());
			bd[0] = new BigDecimal(numString.toString());
//				bd[1] = new BigDecimal(bd[1].toPlainString());
			bd[1] = bd[1].movePointRight(bd[0].scale());
			format(bd, (numString = new StringBuilder()), "\\overline{", "}", s.getDecimalPoint(), s.getDigitsPerUnit(),
					s.getIntDivider(), s.getFracDivider(), Utility.isInteger(bd[0]) && bd[1].signum() == 0);
		} else {
			bd = new BigDecimal[] { new BigDecimal(numString.toString()), BigDecimal.ZERO };
			format(bd, (numString = new StringBuilder()), "\\overline{", "}", s.getDecimalPoint(), s.getDigitsPerUnit(),
					s.getIntDivider(), s.getFracDivider(), Utility.isInteger(bd[0]) && bd[1].signum() == 0);
		}
		numString.append(s.getMultiplicationSign() + BigDecimal.TEN + getExponent(exp));
		return new DataText(numString.insert(0, sign).toString(), bf.getFraction().toPlainString());
	}

	/*
	 * Date: 17 Apr 2021-----------------------------------------------------------
	 * Time created: 10:37:00--------------------------------------------
	 */
	/**
	 * Returns the TeX notation for the decimal (specified at the constructor)
	 * appending the appropriate SI suffix if the value falls in to any of the range
	 * (from yotta to yocto) or just returns {@link #getEngExpression()}. This does
	 * not take into account the recurring part.
	 * 
	 * @return a {@code DataText} with the engineering TeX notation and the
	 *         {@link BigDecimal#toPlainString() plain string} as its components.
	 */
	public DataText getSISuffixExpression() {
		Settings s = Settings.defaultSetting();

		EngNotation eng = getCurrentEngNotation(s.getScale(), s.getEngineering());

		String sign = bf.signum() < 0 ? "-" : "";
		StringBuilder numString = new StringBuilder(eng.toString(bf.abs().getFraction()));
		BigDecimal exp = new BigDecimal(numString.substring(numString.indexOf("E") + 1, numString.length()).toString());
		numString.delete(numString.indexOf("E"), numString.length());

		BigDecimal[] bd = new BigDecimal[2];
		bd[0] = new BigDecimal(numString.toString());
		bd[1] = new BigDecimal("0");
		format(bd, (numString = new StringBuilder()), "\\overline{", "}", s.getDecimalPoint(), s.getDigitsPerUnit(),
				s.getIntDivider(), s.getFracDivider(), Utility.isInteger(bd[0]) && bd[1].signum() == 0);

		if (exp.compareTo(new BigDecimal(EngNotation.asYotta(0).getExponent())) == 0)
			numString.append(EngNotation.asYotta(0).getSymbol());
		else if (exp.compareTo(new BigDecimal(EngNotation.asZetta(0).getExponent())) == 0)
			numString.append(EngNotation.asZetta(0).getSymbol());
		else if (exp.compareTo(new BigDecimal(EngNotation.asExa(0).getExponent())) == 0)
			numString.append(EngNotation.asExa(0).getSymbol());
		else if (exp.compareTo(new BigDecimal(EngNotation.asPeta(0).getExponent())) == 0)
			numString.append(EngNotation.asPeta(0).getSymbol());
		else if (exp.compareTo(new BigDecimal(EngNotation.asTerra(0).getExponent())) == 0)
			numString.append(EngNotation.asTerra(0).getSymbol());
		else if (exp.compareTo(new BigDecimal(EngNotation.asGiga(0).getExponent())) == 0)
			numString.append(EngNotation.asGiga(0).getSymbol());
		else if (exp.compareTo(new BigDecimal(EngNotation.asMega(0).getExponent())) == 0)
			numString.append(EngNotation.asMega(0).getSymbol());
		else if (exp.compareTo(new BigDecimal(EngNotation.asKilo(0).getExponent())) == 0)
			numString.append(EngNotation.asKilo(0).getSymbol());
		else if (exp.compareTo(new BigDecimal(EngNotation.asMilli(0).getExponent())) == 0)
			numString.append(EngNotation.asMilli(0).getSymbol());
		else if (exp.compareTo(new BigDecimal(EngNotation.asMicro(0).getExponent())) == 0)
			numString.append(EngNotation.asMicro(0).getSymbol());
		else if (exp.compareTo(new BigDecimal(EngNotation.asNano(0).getExponent())) == 0)
			numString.append(EngNotation.asNano(0).getSymbol());
		else if (exp.compareTo(new BigDecimal(EngNotation.asPico(0).getExponent())) == 0)
			numString.append(EngNotation.asPico(0).getSymbol());
		else if (exp.compareTo(new BigDecimal(EngNotation.asFemto(0).getExponent())) == 0)
			numString.append(EngNotation.asFemto(0).getSymbol());
		else if (exp.compareTo(new BigDecimal(EngNotation.asAtto(0).getExponent())) == 0)
			numString.append(EngNotation.asAtto(0).getSymbol());
		else if (exp.compareTo(new BigDecimal(EngNotation.asZepto(0).getExponent())) == 0)
			numString.append(EngNotation.asZepto(0).getSymbol());
		else if (exp.compareTo(new BigDecimal(EngNotation.asYocto(0).getExponent())) == 0)
			numString.append(EngNotation.asYocto(0).getSymbol());
		else
			return getEngExpression();

		return new DataText(numString.insert(0, sign).toString(), bf.getFraction().toPlainString());
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 10:38:08--------------------------------------------
	 */
	/**
	 * Returns the TeX notation for the decimal (specified at the constructor) in a
	 * fixed mantissa form using {@link Settings#getScale()} for the number of
	 * significant digits to display. This does not display recurring digits.
	 * 
	 * @return a {@code DataText} with the fixed TeX notation and the
	 *         {@link BigDecimal#toPlainString() plain string} as its components.
	 */
	public DataText getFixExpression() {
		Settings s = Settings.defaultSetting();
		BigDecimal n = bf.getDecimalExpansion(s.getScale()).setScale(s.getScale(), RoundingMode.HALF_EVEN);
		String tex = Utility.toTexString(n, s.getDecimalPoint(), s.getIntDivider(), s.getFracDivider(),
				s.getDigitsPerUnit());
		String symja = n.toPlainString();
		return new DataText(tex, symja);
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 10:39:56--------------------------------------------
	 */
	/**
	 * Return a valid {@code EngNotation} for the appropriate notation argument
	 * (from yotta (8) to yocto (-8)). If the notation argument is not recognised,
	 * then an {@code EngNotation} representing the kilo suffix will e returned.
	 * 
	 * @param scale    the number of significant digits desired for the conversion.
	 * @param notation tan {@code int} value where 8 (max) is for yotta and -8 (min)
	 *                 is for yocto and every other value in between corresponds
	 *                 with their engineering exponent value. For example, if peta
	 *                 is desired the argument will be 5 because petta is value
	 *                 multiplied by 1000<sup>5</sup>.
	 * @return a valid {@code EngNotation} object that correspond with the
	 *         arguments.
	 */
	private static EngNotation getCurrentEngNotation(int scale, int notation) {
		switch (notation) {
		case 8:
			return EngNotation.asYotta(scale);
		case 7:
			return EngNotation.asZetta(scale);
		case 6:
			return EngNotation.asExa(scale);
		case 5:
			return EngNotation.asPeta(scale);
		case 4:
			return EngNotation.asTerra(scale);
		case 3:
			return EngNotation.asGiga(scale);
		case 2:
			return EngNotation.asMega(scale);
		case -1:
			return EngNotation.asMilli(scale);
		case -2:
			return EngNotation.asMicro(scale);
		case -3:
			return EngNotation.asNano(scale);
		case -4:
			return EngNotation.asPico(scale);
		case -5:
			return EngNotation.asFemto(scale);
		case -6:
			return EngNotation.asAtto(scale);
		case -7:
			return EngNotation.asZepto(scale);
		case -8:
			return EngNotation.asYocto(scale);
		default:
			return EngNotation.asKilo(scale);
		}
	}

	/*
	 * Date: 15 Apr 2021-----------------------------------------------------------
	 * Time created: 10:51:58--------------------------------------------
	 */
	/**
	 * Prepends and appends the given {@code BigDecimal} argument with TeX
	 * notational prefix and suffix for exponents (more formally superscripts).
	 * 
	 * @return n with TeX exponential prefix and suffix for exponents.
	 */
	private static String getExponent(BigDecimal n) {
		return "^{" + new DecimalDetailsList(n.toPlainString()).getExpression().getTeXString() + "}";
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 10:54:58--------------------------------------------
	 */
	/**
	 * Formats the argument and returns it as in TeX notation.
	 * @param f the value to be formatted.
	 * @return a {@code String} in TeX notation.
	 */
	private static String asTeXRecurring(BigFraction f) {
		Settings s = Settings.defaultSetting();
		String sign = f.signum() < 0 ? "-" : "";
		StringBuilder sb = new StringBuilder();
		BigDecimal[] bg = f.abs().getRecurring();
		String part1 = bg[0].toPlainString();
		if ((part1.length() + bg[1].scale()) - 2 > s.getScale())
			return new DecimalDetailsList(f).getSciExpression().getTeXString();
		int firstLength = part1.length() + (part1.contains(".") ? 0 : 1);
		//TODO: please format this as a non-recurring decimal
		//if the total num of mantissa is greater than
		//settings.scale
		if (s.getScale() < f.getPeriod() + firstLength + 1) {
			//from the new format that getRecurring is designed,
			//the second part can be added to the first part without
			//an exception thrown from bigDecimal
//			bg[0] = new BigDecimal(part1.toString() + (part1.contains(".") ? "" : ".")
//					+ (bg[1].signum() == 0 ? "" : bg[1].toPlainString()));
			bg[0] = bg[0].add(bg[1]);
			bg[1] = BigDecimal.ZERO;
		} else//Let's work on bigDecimal's scale trailing zeros
			/*
			 * The commented code appends extra zeros removed from
			 * the front of the mantissa and placed behind it.
			 */
//			bg[1] = bg[1].multiply(BigDecimal.TEN.pow(bg[0].scale()));
			bg[1] = bg[1].movePointRight(bg[0].scale());

		format(bg, sb, "\\overline{", "}", s.getDecimalPoint(), s.getDigitsPerUnit(), s.getIntDivider(),
				s.getFracDivider(), f.isInteger());
		return sb.insert(0, sign).toString();
	}

	/**
	 * A string constant used for delimiting characters within numbers to
	 * differentiate between significant and recurring values.
	 */
	private static final char recurringBaricade = 'R';
	/**
	 * The TeX character for horizontal whitespace.
	 */
	private static final String PROPER_SPACE = "~";

	/*
	 * the value that will be used separating the recurring part from the
	 * non-recurring part. Can be left as null if the boolean parameter is set to
	 * false Date: 11 Aug
	 * 2021----------------------------------------------------------- Time created:
	 * 11:04:59--------------------------------------------
	 */
	/**
	 * Formats the given {@code BigDecimal} array into a string in TeX notation and
	 * stores the result in the given {@code StringBuilder}.
	 * 
	 * @param decimal               a {@code BigDecimal} array containing at most 2
	 *                              elements whereby index 0 contains the actual
	 *                              value. Index 1 can contain any recurring value
	 *                              if the boolean parameter is set to {@code true},
	 *                              then this only needs one element.
	 * @param sb                    the object in which to store the result.
	 * @param recurringStringPrefix the TeX prefix for drawing an overline on texts.
	 * @param suffix                the TeX suffix for drawing an overline on texts.
	 * @param decimalPoint          a user defined radix point, used for the same
	 *                              purpose as the decimal point.
	 * @param digitsPerUnit         the number of digits in a unit of delimited
	 *                              digits.
	 * @param intSeparator          the value to be appended after the digitsPerUnit
	 *                              value is reached for the integer part.
	 * @param fracSeparator         the value to be appended after the digitsPerUnit
	 *                              value is reached for the fraction part.
	 * @param isInteger             a check telling this method whether the array is
	 *                              an integer or a fraction.
	 */
	private static void format(BigDecimal[] decimal, StringBuilder sb, String recurringStringPrefix, String suffix,
			char decimalPoint, int digitsPerUnit, char intSeparator, char fracSeparator, boolean isInteger) {
		/*
		 * String reference for the non-recurring part (this may include a fractional
		 * delimiter)
		 */
		String firstPart = decimal[0].toPlainString();
		/*
		 * An iterator for quick iteration of strings. This in particular iterate
		 * through the integer part
		 */
		StringCharacterIterator sci = firstPart.indexOf('.') != -1
				? new StringCharacterIterator(firstPart.substring(0, firstPart.indexOf('.')))
				: new StringCharacterIterator(firstPart);

		doIntegerPart(intSeparator, sb, digitsPerUnit, sci);

		if (isInteger)
			return;
		// A character for marking the point of non-terminating digits
		String ch = Character.toString(recurringBaricade);
		doFractionalPart(firstPart, sci, sb, fracSeparator, digitsPerUnit, decimalPoint, recurringStringPrefix, suffix,
				decimal[1], ch);
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 11:05:13--------------------------------------------
	 */
	/**
	 * Called from
	 * {@link #format(BigDecimal[], StringBuilder, String, String, char, int, char, char, boolean)}
	 * to format the integer part of the specified number.
	 * 
	 * @param intSeparator  the value to be appended after the digitsPerUnit value
	 *                      is reached for the integer part.
	 * @param sb            the object in which to store the result.
	 * @param digitsPerUnit the number of digits in a unit of delimited digits.
	 * @param sci           a text iterator that tells the formatter where to start
	 *                      from and where to end.
	 */
	private static void doIntegerPart(char intSeparator, StringBuilder sb, int digitsPerUnit, CharacterIterator sci) {
		// Monitors the digits per units used for separating the integer
		int section = 0;
		// the integer separator. If it is a space, then use LaTeX's default spacer
		String is = Character.isWhitespace(intSeparator) ? PROPER_SPACE : Character.toString(intSeparator);
		// Iterates through the integer part
		for (char c = sci.last(); c != CharacterIterator.DONE; c = sci.previous()) {
			sb.insert(0, c);// Only insert at the beginning since we are using backward iteration
			if (section == digitsPerUnit - 1) {// integer delimiter index is detected
				sb.insert(0, is);
				section = 0;
				continue;
			}

			section += 1;// increment 'cause delimiter index is not yet reached
		} // End for-loop
			// If there is a residual delimiter at the start
		if (sb.substring(0, is.length()).equals(is)) {
			sb.replace(0, is.length(), "");// remove it
		}

	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 11:05:18--------------------------------------------
	 */
	/**
	 * Called from within
	 * {@link #format(BigDecimal[], StringBuilder, String, String, char, int, char, char, boolean)}
	 * to format the fractional part and tidy up the whole by linking the formatted
	 * integer and fraction part.
	 * 
	 * @param firstPart             the non-recurring part.
	 * @param sci                   a text iterator that tells the formatter where
	 *                              to start from and where to end.
	 * @param sb                    the object in which to store the result.
	 * @param fracSeparator         the value to be appended after the digitsPerUnit
	 *                              value is reached for the fraction part.
	 * @param digitsPerUnit         the number of digits in a unit of delimited
	 *                              digits.
	 * @param decimalPoint          a user defined radix point, used for the same
	 *                              purpose as the decimal point.
	 * @param recurringStringPrefix the TeX prefix for drawing an overline on texts.
	 * @param suffix                the TeX suffix for drawing an overline on texts.
	 * @param decimal               the recurring part.
	 * @param ch                    A single string character for marking the point
	 *                              between non-terminating digits and terminating
	 *                              ones within the stored string.
	 */
	private static void doFractionalPart(String firstPart, CharacterIterator sci, StringBuilder sb, char fracSeparator,
			int digitsPerUnit, char decimalPoint, String recurringStringPrefix, String suffix, BigDecimal decimal,
			String ch) {
		// the fractional separator. If it is a space, then use LaTeX's default spacer
		String fs = Character.isWhitespace(fracSeparator) ? PROPER_SPACE : Character.toString(fracSeparator);

		fill(firstPart, decimal, ch, sci, sb, decimalPoint, digitsPerUnit, fs);
		tidy(decimal, sb, ch, decimalPoint, recurringStringPrefix, suffix, fs);
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 11:05:29--------------------------------------------
	 */
	/**
	 * Called from
	 * {@link #doFractionalPart(String, CharacterIterator, StringBuilder, char, int, char, String, String, BigDecimal, String)}
	 * to actually format only thefractional part of the given number.
	 * 
	 * @param firstPart     the non-recurring part.
	 * @param decimal       the recurring part.
	 * @param ch            A single string character for marking the point between
	 *                      non-terminating digits and terminating ones within the
	 *                      stored string.
	 * @param sci           a text iterator that tells the formatter where to start
	 *                      from and where to end.
	 * @param sb            the object in which to store the result.
	 * @param decimalPoint  a user defined radix point, used for the same purpose as
	 *                      the decimal point.
	 * @param digitsPerUnit the number of digits in a unit of delimited digits.
	 * @param fs            a value representing the
	 *                      {@link Settings#getFracDivider()}.
	 */
	private static void fill(String firstPart, BigDecimal decimal, String ch, CharacterIterator sci, StringBuilder sb,
			char decimalPoint, int digitsPerUnit, String fs) {
		// the fractional part - if there is a fractional part
		String pd = decimal.toPlainString();//plainDecimal
		String secondPart = firstPart.indexOf('.') != -1
				// if no recurring part, then no need to add it
				? firstPart.substring(firstPart.indexOf('.') + 1)
						//both commented codes causes the 0. to be appended to the mantissa
//						.concat((decimal.signum() == 0 ? "" : ch + decimal.toPlainString()))
						.concat((decimal.signum() == 0 ? "" : ch + pd.substring(pd.indexOf('.') + 1)))
//				: (decimal.signum() == 0 ? "" : ch + decimal.toPlainString());
				: (decimal.signum() == 0 ? "" : ch + pd.substring(pd.indexOf('.') + 1));
		// Instantiate a new iterator from the fractional part
		sci = new StringCharacterIterator(secondPart);
		// adds the customized decimal point
		sb.append(decimalPoint);
		// reset the delimiter monitor
		int section = 0;

		// iterates through the fractional part
		for (char c = sci.first(); c != CharacterIterator.DONE; c = sci.next()) {
			sb.insert(sb.length(), c);
			if (c == recurringBaricade)// recurring index detected
				continue;
			if (section == digitsPerUnit - 1) {
				sb.insert(sb.length(), fs);
				section = 0;
				continue;
			}

			section += 1;
		} // End for-loop
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 11:05:35--------------------------------------------
	 */
	/**
	 * Called from
	 * {@link #doFractionalPart(String, CharacterIterator, StringBuilder, char, int, char, String, String, BigDecimal, String)}
	 * to put it all together.
	 * 
	 * @param decimal               the recurring part.
	 * @param sb                    the object in which to store the result.
	 * @param ch                    A single string character for marking the point
	 *                              between non-terminating digits and terminating
	 *                              ones within the stored string.
	 * @param decimalPoint          a user defined radix point, used for the same
	 *                              purpose as the decimal point.
	 * @param recurringStringPrefix the TeX prefix for drawing an overline on texts.
	 * @param suffix                the TeX suffix for drawing an overline on texts.
	 * @param fs                    a value representing the
	 *                              {@link Settings#getFracDivider()}.
	 */
	private static void tidy(BigDecimal decimal, StringBuilder sb, String ch, char decimalPoint,
			String recurringStringPrefix, String suffix, String fs) {

		/*
		 * this block typically throws the caught exception when the fraction is not
		 * recurring
		 */
		try {
			// If there is a residual delimiter at the index after the recurring index
			if (decimal.signum() != 0 && sb.substring(sb.indexOf(ch) + 1, sb.indexOf(ch) + 1 + fs.length()).equals(fs))
				sb.replace(sb.indexOf(ch) + 1, sb.indexOf(ch) + 1 + fs.length(), "");
			/*
			 * If there is a residual delimiter at the start of the fractional part just
			 * after the decimal point
			 */
			else if (decimal.signum() == 0
					&& sb.substring(sb.indexOf(decimalPoint + "") + 1, sb.indexOf(decimalPoint + "") + 1 + fs.length())
							.equals(fs))
				sb.replace(sb.indexOf(decimalPoint + "") + 1, sb.indexOf(decimalPoint + "") + 1 + fs.length(), "");
			if (sb.substring(sb.length() - fs.length()).compareTo(fs) == 0)
				sb.delete(sb.length() - fs.length(), sb.length());
		} catch (@SuppressWarnings("unused") IndexOutOfBoundsException e) {
		}

		/*
		 * this block typically throws the caught exception the fraction is not
		 * recurring
		 */
		try {
			if (decimal.signum() != 0)
				// inserts the TeX recurring prefix and suffix
				sb.replace(sb.indexOf(ch), sb.indexOf(ch) + 1, recurringStringPrefix).append(suffix);
		} catch (@SuppressWarnings("unused") IndexOutOfBoundsException e) {
		}
	}

	/**
	 * The number stored in {@code BigFraction} format to preserve any recurring
	 * digit it may internally have.
	 */
	private final BigFraction bf;

}
