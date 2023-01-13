/**
 * 
 */
package mathaid.calculator.base;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

import mathaid.calculator.base.util.Utility;
import mathaid.calculator.base.value.BigFraction;

/*
 * Date: 10 Apr 2021----------------------------------------------------------- 
 * Time created: 15:29:50---------------------------------------------------  
 * Package: mathaid.calculator.base------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: FractionalDetailsList.java------------------------------------------------------ 
 * Class name: FractionalDetailsList------------------------------------------------ 
 */
/**
 * A {@code DetailsList} helper class. It can also be used to retrieve rational
 * numbers in TeX notation, but users should refrain from doing that in isolation because
 * values returned by methods are dependent upon the current state of the
 * {@code Settings} object.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
class FractionalDetailsList {
	/*
	 * Date: 10 Apr 2021-----------------------------------------------------------
	 * Time created: 15:29:50---------------------------------------------------
	 */
	/**
	 * Constructs this object with a {@code String} representation of a common
	 * fraction and a check for whether the string is in TeX notation.
	 * 
	 * @param string      a string representing a rational number.
	 * @param isTeXString a check to specify that the string argument is in TeX
	 *                    form. Use {@code true} is it is in TeX notation or else
	 *                    use {@code false}.
	 */
	public FractionalDetailsList(String string, boolean isTeXString) {
		this.frac = isTeXString ? fromTeX(string) : fromSymja(string);
		this.decimal = frac.getFraction();
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 12:39:57---------------------------------------------------
	 */
	/**
	 * Constructs this object from a {@code BigDecimal}.
	 * 
	 * @param decimal a {@code BigDecimal} value.
	 */
	public FractionalDetailsList(BigDecimal decimal) {
		frac = new BigFraction(decimal, new MathContext(Settings.defaultSetting().getScale(), RoundingMode.HALF_EVEN),
				null, BigFraction.ONE.getAccuracy());
		this.decimal = decimal;

	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 12:40:53--------------------------------------------
	 */
	/**
	 * Returns a {@code DataText} object corresponding to the TeX notation for the
	 * non-mixed fraction (specified at the constructor).
	 * 
	 * @return a {@code DataText} with the TeX notation and the symja notation for
	 *         fractions as its components.
	 */
	public DataText getExpression() {
		Settings s = Settings.defaultSetting();
		return new DataText(frac.toTeXString(s.getDecimalPoint(), s.getIntDivider(), s.getFracDivider(),
				s.getDigitsPerUnit(), false), symjaString(frac));
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 12:43:27--------------------------------------------
	 */
	/**
	 * Returns a {@code DataText} object corresponding to the TeX notation for mixed
	 * fraction (specified at the constructor).
	 * 
	 * @return a {@code DataText} with the TeX notation and the symja notation for
	 *         fractions as its components.
	 */
	public DataText getMixedFraction() {
		final String symja = symjaString(frac);
		Settings s = Settings.defaultSetting();
		if (!frac.isProper()) {
			BigInteger[] mixed = frac.toMixed();
//			return new DataText(new DecimalDetailsList(mixed[0].toString()).getExpression().getTeXString()
//					+ new BigFraction(mixed[1], mixed[2]).toTeXString(s.getIntDivider(), s.getDigitsPerUnit()), symja);
			return new DataText(Utility.toTeXString(mixed, s.getIntDivider(), s.getDigitsPerUnit()), symja);
		}
		return new DataText(frac.toTeXString(s.getDecimalPoint(), s.getIntDivider(), s.getFracDivider(),
				s.getDigitsPerUnit(), true), symja);
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 12:44:00--------------------------------------------
	 */
	/**
	 * Returns a {@code DataText} object corresponding to the decimal TeX notation
	 * for the fraction (specified at the constructor). This may also display
	 * recurring digits.
	 * 
	 * @return a {@code DataText} with the decimal TeX notation and the symja
	 *         notation as its components.
	 */
	public DataText getDecimal() {// TODO: Remove to decimalDetailsList
		int digits = Settings.defaultSetting().getScale();
		String s = asTeXRecurring(digits == 0 ? decimal : frac.getDecimalExpansion(digits));
		return new DataText(s, decimal.toString());

	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 13:00:50--------------------------------------------
	 */
	/**
	 * Returns the {@link BigFraction#getPeriod() period}.
	 * 
	 * @return the number of reccuring digits in the decimal representation of the
	 *         fraction.
	 */
	public DataText getPeriod() {// TODO: Remove to decimalDetailsList
		String s = Integer.toString(frac.getPeriod());
		return new DataText(new DecimalDetailsList(s).getExpression().getTeXString(), s);
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 13:02:04--------------------------------------------
	 */
	/**
	 * Returns a {@code DataText} object corresponding to the fraction in its lowest
	 * terms in TeX notation
	 * 
	 * @return a {@code DataText} TeX notation and the symja notation for a reduced
	 *         fraction as its components.
	 */
	public DataText getLowestTerm() {
		Settings s = Settings.defaultSetting();
		BigFraction f = frac.toLowestTerms();
		return new DataText(
				f.toTeXString(s.getDecimalPoint(), s.getIntDivider(), s.getFracDivider(), s.getDigitsPerUnit(), false),
				symjaString(f));
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 13:05:59--------------------------------------------
	 */
	/**
	 * Returns a {@code DataText} object corresponding to the TeX notation for the
	 * continued fraction of representation.
	 * 
	 * @return a {@code DataText} with the TeX notation and the symja notation for
	 *         fraction lists as its components.
	 */
	public DataText getContinuedFraction() {
		List<BigInteger> fs = frac.toContinuedFraction();
		Settings s = Settings.defaultSetting();
		if (fs.size() > 1) {
			StringBuilder cfs = new StringBuilder();
			StringBuilder sb = new StringBuilder("List[");
			for (int i = 0; i < fs.size() - 1; i++) {
				if (fs.get(i).compareTo(BigInteger.ZERO) == 0) {

				} else {
					cfs.append(asTeXRecurring(new BigDecimal(fs.get(i))));
					cfs.append("+");
				}
				cfs.append("\\frac{1}{");
			}
			cfs.append(asTeXRecurring(new BigDecimal(fs.get(fs.size() - 1))));
			for (int i = 0; i < fs.size() - 1; i++)
				cfs.append("}");
			for (BigInteger i : fs) {
				sb.append(i + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("]");
			return new DataText(cfs.toString(), sb.toString());
		}
		return new DataText(frac.toTeXString(s.getDecimalPoint(), s.getIntDivider(), s.getFracDivider(),
				s.getDigitsPerUnit(), false), symjaString(frac));
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 13:07:54--------------------------------------------
	 */
	/**
	 * Returns a {@code DataText} object corresponding to the TeX notation for the
	 * Egyptian fraction representation.
	 * 
	 * @return a {@code DataText} with the TeX notation and the symja notation for
	 *         the fraction lists as its components.
	 */
	public DataText getEgyptianFraction() {
		List<BigFraction> fs = frac.toEgyptianFractions();
		Settings s = Settings.defaultSetting();
		if (fs.size() > 1) {
			StringBuilder ef = new StringBuilder();
			StringBuilder sb = new StringBuilder("List[");
			for (BigFraction ff : fs) {
				ef.append(ff.toTeXString(s.getDecimalPoint(), s.getIntDivider(), s.getFracDivider(),
						s.getDigitsPerUnit(), false) + "+");
				sb.append(symjaString(ff) + ",");
			}
			ef.deleteCharAt(ef.length() - 1);
			sb.deleteCharAt(sb.length() - 1);
			sb.append("]");
			return new DataText(ef.toString(), sb.toString());
		}
		return new DataText(frac.toTeXString(s.getDecimalPoint(), s.getIntDivider(), s.getFracDivider(),
				s.getDigitsPerUnit(), false), symjaString(frac));
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 13:10:06--------------------------------------------
	 */
	/**
	 * Returns a {@code DataText} object corresponding to the TeX notation for the
	 * prime factors of a rational number.
	 * 
	 * @return a {@code DataText} with the TeX notation and the symja notation for
	 *         prime fractions as its components.
	 */
	public DataText getFactors() {
		return factorize(frac, Settings.defaultSetting().getMultiplicationSign());
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 13:11:02--------------------------------------------
	 */
	/**
	 * Factorises the number in a TeX-friendly way and returns a {@code DataText}
	 * object corresponding to the result.
	 * 
	 * @param f   the value to be factorised.
	 * @param mul a value that represents the multiplication sign.
	 * @return a {@code DataText} with the TeX notation and the symja notation for
	 *         integer lists as its components.
	 */
	private static DataText factorize(BigFraction f, String mul) {
		List<BigInteger> l = f.factorize();
		int exp = 1;
		int expm = 1;
		StringBuilder fb = new StringBuilder();
		StringBuilder sb = new StringBuilder();
		BigInteger ref = BigInteger.ZERO;
		BigInteger n = null;

		for (int i = 0; i < l.size(); i++) {
			n = l.get(i);
			if (n == null || (n.compareTo(ref) != 0 && ref.signum() != 0)) {
				fb.append(asTeXRecurring(new BigDecimal(ref))
						+ ((exp * expm) == 1 ? "" : "^{" + asTeXRecurring(new BigDecimal(exp * expm)) + "}") + mul);
				sb.append(asTeXRecurring(new BigDecimal(ref))
						+ ((exp * expm) == 1 ? "" : "^" + asTeXRecurring(new BigDecimal(exp * expm))) + "*");
				exp = 1;
				if (n == null) {
					expm *= -1;
					ref = BigInteger.ZERO;
					continue;
				}
			} else if (n.compareTo(ref) == 0)
				exp++;

			ref = n;
		}

		fb.append(asTeXRecurring(new BigDecimal(n)) + ((exp * expm) == 1 ? "" : "^{" + (exp * expm) + "}"));
		sb.append(asTeXRecurring(new BigDecimal(n)) + ((exp * expm) == 1 ? "" : "^" + (exp * expm)));

		return new DataText(fb.toString(), sb.toString());
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 13:14:39--------------------------------------------
	 */
	/**
	 * Parses and converts the string from TeX notation to a {@code BigFraction}
	 * object.
	 * 
	 * @param teX the value to be parsed.
	 * @return a {@code BigFraction} representing the argument.
	 */
	private static BigFraction fromTeX(String teX) {
		String s = teX.replaceAll("\\{", "").replaceFirst("\\}", "/").replaceFirst("\\\\", "").replaceFirst(",", "/")
				.replaceFirst("frac", "").replaceAll("\\}", "");
//		String[] ss = Pattern.compile("/").split(s);
		String[] ss = split(s, '/');
		return new BigFraction(new BigInteger(ss[0]), new BigInteger(ss[1]));
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 13:15:44--------------------------------------------
	 */
	/**
	 * Parses and converts the string from symja notation to a {@code BigFraction}
	 * object.
	 * 
	 * @param symja the value to be parsed.
	 * @return a {@code BigFraction} representing the argument.
	 */
	private static BigFraction fromSymja(String symja) {
		String s = symja.replaceAll("Rational\\[", "").replaceAll("\\]", "").replaceAll("Rational\\(", "").replaceAll("\\)", "");
//		String[] ss = Pattern.compile(",").split(s);
		String[] ss = split(s, ',');
		return new BigFraction(new BigInteger(ss[0].trim()), new BigInteger(ss[1].trim()));
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 13:16:29--------------------------------------------
	 */
	/**
	 * Divides the string argument around every occurrence of the given character
	 * into parts that are returns as elements of a {@code String} array.
	 * 
	 * @param value the string to be divided.
	 * @param token the character to be used for splitting th string.
	 * @return an array containing the parts of the string splitted around the given character.
	 */
	private static String[] split(String value, char token) {
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if (c == token) {
				return new String[] { value.substring(0, i), value.substring(i + 1) };
			}
		}
		return new String[] { value };
	}

	/*
	 * Date: 11 Aug 2021----------------------------------------------------------- 
	 * Time created: 13:19:49--------------------------------------------
	 */
	/**
	 * Gets the TeX notation of the argument.
	 * @param n a number.
	 * @return a string represntation of the TeX notation of the argument.
	 */
	private static String asTeXRecurring(BigDecimal n) {
//		BigFraction bf = new BigFraction(n);
//		if (bf.isRecurring()) {
//			BigDecimal[] array = bf.getRecurring();
//			String array1 = array[0].toPlainString();
//			return array[0].toPlainString() + (array1.indexOf('.') == -1 ? "." : "") + "\\overline{"
//					+ array[1].toPlainString() + "}";
//		}

		DecimalDetailsList d = new DecimalDetailsList(n.toPlainString());
		return d.getExpression().getTeXString();

//		return bf.getFraction().toPlainString();
	}

	/*
	 * Date: 11 Aug 2021----------------------------------------------------------- 
	 * Time created: 13:21:09--------------------------------------------
	 */
	/**
	 * Parses the given value to a symja notational fraction.
	 * @param f the value to be converted.
	 * @return a string representing the value in symja notation.
	 */
	private static String symjaString(BigFraction f) {
		return "Rational[" + f.getNumerator() + "," + f.getDenominator() + "]";
	}

//	private EvalUtilities ev;
	/**
	 * The decimal representation of the internal fraction.
	 */
	private final BigDecimal decimal;

	/**
	 * The internal fraction.
	 */
	private final BigFraction frac;
}
