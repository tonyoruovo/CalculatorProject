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
import static mathaid.calculator.base.typeset.Segment.Type.PARENTHESISED;
import static mathaid.calculator.base.typeset.Segment.Type.POINT;
import static mathaid.calculator.base.typeset.Segment.Type.PREFIX_MINUS;
import static mathaid.calculator.base.typeset.Segment.Type.PREFIX_PLUS;
import static mathaid.calculator.base.typeset.Segment.Type.UNIT;
import static mathaid.calculator.base.typeset.Segment.Type.VINCULUM;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import mathaid.calculator.base.util.Utility;
import mathaid.calculator.base.value.BigFraction;
import mathaid.calculator.base.value.BinaryFPPrecision.BinaryFP;
import mathaid.calculator.base.value.FloatAid;

public final class Digits {

	public static LinkedSegment prefixPlus() {
		return new BasicSegment("\\textrm{+}", "+", PREFIX_PLUS);
	}

	public static LinkedSegment prefixMinus() {
		return new BasicSegment("\\textrm{-}", "-", PREFIX_MINUS);
	}

	public static Digit integer(char d, DigitPunc dp) {
		return new Digit(d, dp);
	}

	public static Digit mantissa(char d, DigitPunc dp) {
		return new Digit(d, MANTISSA, dp);
	}

	public static LinkedSegment point(char p) {
		return new BasicSegment(String.valueOf(p), POINT);
	}

	public static Digit arc(char d, DigitPunc dp) {
		return new Digit(d, ARC, dp);
	}

	public static Digit dot(char d, DigitPunc dp) {
		return new Digit(d, DOT, dp);
	}

	public static Digit dotAll(char d, DigitPunc dp) {
		return new Digit(d, DOT_ALL, dp);
	}

	public static Digit dotBar(char d, DigitPunc dp) {
		return new Digit(d, DOT_BAR, dp);
	}

	public static Digit ellipsis(char d, DigitPunc dp) {
		return new Digit(d, ELLIPSIS, dp);
	}

	public static Digit parenthesis(char d, DigitPunc dp) {
		return new Digit(d, PARENTHESISED, dp);
	}

	public static Digit vinculum(char d, DigitPunc dp) {
		return new Digit(d, VINCULUM, dp);
	}

	public static LinkedSegment decimalExponent(LinkedSegment power) {
		String[] f = { " \\times 10^{", "}" };
		String[] s = { "e", "" };
		return new CompositeSegment(f, s, EXPONENT, new LinkedSegment[] { power }, 0, -1);
	}

	// floating-point exponent
	public static LinkedSegment radixExponent(boolean binary) {
		if(!binary)
			return new BasicSegment("\\mathrm{e}", "e", EXPONENT);
		return new BasicSegment("\\mathrm{p}", "p", EXPONENT);
	}

	public static LinkedSegment toSegment(BigInteger n, int radix, DigitPunc dp) {
		String str = n.toString(radix);
		if (n.signum() < 0)
			return unsignedDigits(new StringBuilder(str.substring(1)), INTEGER, prefixMinus(), dp);
		else if (str.charAt(0) == '+')
			return unsignedDigits(new StringBuilder(str.substring(1)), INTEGER, new Empty(), dp);
		return unsignedDigits(new StringBuilder(str), INTEGER, new Empty(), dp);
	}

	private static LinkedSegment unsignedDigits(StringBuilder n, int t, LinkedSegment s, DigitPunc dp) {
		if (n.length() > 0)
			return unsignedDigits(n.deleteCharAt(0), t, s.concat(new Digit(n.charAt(0), t, dp)), dp);
		return s;
	}

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
		String s = Utility.formatAsStandardForm(n, exp);
		int ioe = s.indexOf('E');// index of exponent
		String e = s.substring((ioe > 0 ? ioe : s.indexOf('e')) + 1, s.length());
		LinkedSegment h, expn;
		if (s.charAt(0) == '+') {
			h = unsignedMantissa(new StringBuilder(s.substring(1, ioe > 0 ? ioe : s.indexOf('e'))), new Empty(), dp);
		} else if (s.charAt(0) == '-') {
			h = unsignedMantissa(new StringBuilder(s.substring(1, ioe > 0 ? ioe : s.indexOf('e'))), prefixMinus(), dp);
		} else
			h = unsignedMantissa(new StringBuilder(s.substring(0, ioe > 0 ? ioe : s.indexOf('e'))), new Empty(), dp);
		expn = e.charAt(0) == '-' ? unsignedDigits(new StringBuilder(e.substring(1)), INTEGER, prefixMinus(), dp)
				: unsignedDigits(new StringBuilder(e.substring(e.charAt(0) == '+' ? 1 : 0)), INTEGER, new Empty(), dp);
		return h.concat(decimalExponent(expn));
	}

	// the p parameter stands for the decimal point string
	public static LinkedSegment toSegment(BigDecimal n, boolean useSuffix, DigitPunc dp) {
		String s = Utility.toEngineeringString(n, useSuffix);
		int ioe = s.indexOf('E');// index of exponent
		String e = (!useSuffix) || Character.isDigit(s.charAt(s.length() - 1))
				? s.substring((ioe > 0 ? ioe : s.indexOf('e')) + 1, s.length())
				: "";
		LinkedSegment h, exp;
		if (s.charAt(0) == '+') {
			h = unsignedMantissa(new StringBuilder(s.substring(1, ioe > 0 ? ioe : s.indexOf('e'))), new Empty(), dp);
		} else if (s.charAt(0) == '-') {
			h = unsignedMantissa(new StringBuilder(s.substring(1, ioe > 0 ? ioe : s.indexOf('e'))), prefixMinus(), dp);
		} else
			h = unsignedMantissa(new StringBuilder(s.substring(0, ioe > 0 ? ioe : s.indexOf('e'))), new Empty(), dp);
		if (!e.isEmpty()) {
			exp = e.charAt(0) == '-' ? unsignedDigits(new StringBuilder(e.substring(1)), INTEGER, prefixMinus(), dp)
					: unsignedDigits(new StringBuilder(e.substring(e.charAt(0) == '+' ? 1 : 0)), INTEGER, new Empty(),
							dp);
			return h.concat(decimalExponent(exp));
		}
		exp = new BasicSegment(s.charAt(s.length() - 1) != 'µ' ? s.charAt(s.length() - 1) + "" : "\\mu",
				getSuffixExpOrDefault(s.substring(s.length() - 1), ""), UNIT);
		return h.concat(exp);
	}

	private static String getSuffixExpOrDefault(String suffix, String def) {
		Map<String, String> suffixes = new HashMap<>();
		suffixes.put("Y", "E24");
		suffixes.put("Z", "E21");
		suffixes.put("E", "E18");
		suffixes.put("P", "E15");
		suffixes.put("T", "E12");
		suffixes.put("G", "E9");
		suffixes.put("M", "E6");
		suffixes.put("K", "E3");
		suffixes.put("m", "E-3");
		suffixes.put("µ", "E-6");
		suffixes.put("n", "E-9");
		suffixes.put("p", "E-12");
		suffixes.put("f", "E-15");
		suffixes.put("a", "E-18");
		suffixes.put("z", "E-21");
		suffixes.put("y", "E-24");
		return suffixes.getOrDefault(suffix, def);
	}

	private static String getSuffix(String exp, String def) {
		Map<String, String> suffixes = new HashMap<>();
		suffixes.put("E24", "Y");
		suffixes.put("E21", "Z");
		suffixes.put("E18", "E");
		suffixes.put("E15", "P");
		suffixes.put("E12", "T");
		suffixes.put("E9", "G");
		suffixes.put("E6", "M");
		suffixes.put("E3", "K");
		suffixes.put("E-3", "m");
		suffixes.put("E-6", "µ");
		suffixes.put("E-9", "n");
		suffixes.put("E-12", "p");
		suffixes.put("E-15", "f");
		suffixes.put("E-18", "a");
		suffixes.put("E-21", "z");
		suffixes.put("E-24", "y");
		return suffixes.getOrDefault(exp, def);
	}

	private static LinkedSegment unsignedMantissa(StringBuilder n, LinkedSegment s, DigitPunc dp) {
		LinkedSegment num = unsignedDigits(new StringBuilder(n.substring(0, n.indexOf("."))), INTEGER, s, dp);
		return unsignedDigits(new StringBuilder(n.substring(n.indexOf(".") + 1)), MANTISSA,
				num.concat(point(dp.getPoint().charAt(0))), dp);
	}

	// returns [integer].[mantissa]r[recurring digits]E[optional exponent
	// sign][exponent magnitude]
	public static String toSegmentString(BigFraction f, int exponentFactor) {
		BigDecimal[] b = f.getRecurring();
		StringBuilder num = new StringBuilder(
				exponentFactor > 0 ? Utility.formatAsStandardForm(b[0], exponentFactor) : b[0].toString());

		if (b[1].signum() != 0) {
			int indexOfExponent = num.indexOf("E");

			if (num.indexOf(".") < 0) {
				if (indexOfExponent >= 0)
					num.insert(num.indexOf("E") - 1, ".");
				else
					num.append('.');
			}

			String s = b[1].toPlainString();
			String recur = String.format("r%s", s.substring(s.indexOf('.') + b[0].scale() + 1));
			if (indexOfExponent >= 0)
				num.insert(indexOfExponent, recur);
			else
				num.append(recur);
		}

		return num.toString();
	}

	public static BigFraction fromSegmentString(String s) {
		StringBuilder sb = new StringBuilder(s.toLowerCase());
		int r = sb.indexOf("r");
		int e = sb.indexOf("e");
		if(r >= 0) {
			String rec = sb.substring(r + 1, e >= 0 ? e : sb.length());
			BigDecimal nonRecur = new BigDecimal(sb.delete(r, e >= 0 ? e : sb.length()).toString());
			return Utility.fromDecimal(nonRecur, rec);
		}
		return new BigFraction(new BigDecimal(s), null, null, new BigDecimal("1E-10"));
	}
	
	/*
	 * Date: 29 Sep 2022-----------------------------------------------------------
	 * Time created: 01:48:13--------------------------------------------
	 */
	/**
	 * @param exp   note that exp must be in the same format as the result of
	 *              {@link Segments#toSegmentString(BigFraction, EngNotation)}.
	 * @param scale
	 * @return
	 */
	public static int scale(String exp) {
		exp = exp.replaceAll("[+-\\.rR]", "");
		int smE = exp.indexOf("e");
		int bigE = exp.indexOf("E");
		if (smE >= 0 || bigE >= 0)
			return exp.substring(0, smE >= 0 ? smE : bigE).length();
		return exp.length();
	}

	// This method expects that 's' is in scientific form i.e there is an exponent
	// field in 's'
	public static String truncateToScale(final String s, int newScale) {
		int smE = s.indexOf("e");
		int bigE = s.indexOf("E");
		int e = smE >= 0 ? smE : bigE;
		StringBuilder sb = new StringBuilder(/* s.substring(0, e) */s);
		int smR = sb.indexOf("r");
		int bigR = sb.indexOf("R");
		int r = smR >= 0 ? smR : bigR;
		if (r >= 0) {// i.e there is recurring digit(s)
			String rec = sb.substring(r + 1, e);
			String newRec = rec.substring(0, newScale - rec.length());
			if (rec.compareTo(newRec) != 0)
				sb.replace(r, e, String.format("r%s", newRec));
		}
		return sb.toString();
	}

	// the scale (or precision) of the BigFraction.getMathContext() is what is used
	// in this method
	public static LinkedSegment toSegment(BigFraction n, int recurType, int exponent, DigitPunc dp) {
		String s = toSegmentString(n, exponent);
		int scale = scale(s);
		if (scale > n.getMathContext().getPrecision())
			s = truncateToScale(s, n.getMathContext().getPrecision()).toLowerCase();
		int pointIndex = s.indexOf(".");
		int recIndex = s.indexOf("r");
		int expIndex = s.indexOf("e");
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
			rec = s.substring(s.indexOf("r") + 1);
			s = s.substring(0, s.indexOf("r"));
		}
		String mant = "";
		if (pointIndex >= 0) {
			mant = s.substring(s.indexOf(".") + 1);
			s = s.substring(0, s.indexOf("."));
		}

		seg = unsignedDigits(new StringBuilder(s), INTEGER, seg, dp);// int
		if (!mant.isEmpty())
			seg = unsignedDigits(new StringBuilder(mant), MANTISSA, seg.concat(point(dp.getPoint().charAt(0))), dp);// mantissa
		seg = unsignedDigits(new StringBuilder(rec), recurType, seg, dp);// recur
		if (!exp.isEmpty()) {// exponent
			if (exp.charAt(0) == '-')
				seg = decimalExponent(
						prefixMinus().concat(unsignedDigits(new StringBuilder(exp.substring(1)), INTEGER, seg, dp)));
			else if (exp.charAt(0) == '+')
				seg = decimalExponent(unsignedDigits(new StringBuilder(exp.substring(1)), INTEGER, seg, dp));
			else
				seg = decimalExponent(unsignedDigits(new StringBuilder(exp), INTEGER, seg, dp));
		}

		return seg;
	}

	public static LinkedSegment toSegment(BigFraction n, boolean engPrefix, int recurType, DigitPunc dp) {
		String s = toSegmentString(n, 3);
		int scale = scale(s);
		if (scale > n.getMathContext().getPrecision())
			s = truncateToScale(s, n.getMathContext().getPrecision()).toLowerCase();
		int pointIndex = s.indexOf(".");
		int recIndex = s.indexOf("r");
		int expIndex = s.indexOf("e");
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
			exp = getSuffix(s.substring(expIndex), s.substring(expIndex + 1));
			s = s.substring(0, expIndex);
		}
		String rec = "";
		if (recIndex >= 0) {
			rec = s.substring(s.indexOf("r") + 1);
			s = s.substring(0, s.indexOf("r"));
		}
		String mant = "";
		if (pointIndex >= 0) {
			mant = s.substring(s.indexOf(".") + 1);
			s = s.substring(0, s.indexOf("."));
		}

		seg = unsignedDigits(new StringBuilder(s), INTEGER, seg, dp);// int
		if (!mant.isEmpty())
			seg = unsignedDigits(new StringBuilder(mant), MANTISSA, seg.concat(point(dp.getPoint().charAt(0))), dp);// mantissa
		seg = unsignedDigits(new StringBuilder(rec), recurType, seg, dp);// recur
		if (!exp.isEmpty()) {// exponent
			if (exp.charAt(0) == '-')
				seg = decimalExponent(
						prefixMinus().concat(unsignedDigits(new StringBuilder(exp.substring(1)), INTEGER, seg, dp)));
			else if (exp.charAt(0) == '+')
				seg = decimalExponent(unsignedDigits(new StringBuilder(exp.substring(1)), INTEGER, seg, dp));
			else if (exp.length() == 1 && FloatAid.isNumber(exp.charAt(exp.length() - 1), 10))
				seg = decimalExponent(unsignedDigits(new StringBuilder(exp), INTEGER, seg, dp));
			else {
				LinkedSegment e = new BasicSegment(
						s.charAt(s.length() - 1) != 'µ' ? s.charAt(s.length() - 1) + "" : "\\mu",
						getSuffixExpOrDefault(s.substring(s.length() - 1), ""), UNIT);
				seg = seg.concat(e);
			}
		}

		return seg;
	}

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

	public static LinkedSegment toRadixedSegment(BigInteger n, int radix, DigitPunc dp) {
		return toSegment(n, radix, dp).concat(sub(radix, dp));
	}

	private static LinkedSegment sub(int radix, DigitPunc dp) {
		return new CompositeSegment(new String[] { " _{", " }" }, new String[] { " _", "" }, OBJECT,
				new LinkedSegment[] { toSegment(BigInteger.valueOf(radix), 10, dp) }, -1, 0);
	}

	public static LinkedSegment toSegment(BinaryFP n, int sigRadix, int expRadix, boolean norm, char point,
			DigitPunc dp) {
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
			exp = s.substring(p - 1);// because of the additional sign
		} else {
			String s = n.toScientificString(sigRadix).toUpperCase();
			int eP = sigRadix == 10 ? s.indexOf("E") : s.indexOf("E+");
			int eM = s.indexOf("E-");
			int e = eP >= 0 ? eP : eM;
			sig = s.substring(0, e >= 0 ? e : s.length());
			exp = e >= 0 ? s.substring((sigRadix == 10) ? e + 1 : e - 1) : "";
		}

		if (sig.charAt(0) == '-') {
			sig = sig.substring(1);
			ls = prefixMinus();
		} else if (sig.charAt(0) == '+') {
			sig = sig.substring(0);
			ls = new Empty();
		} else {
			ls = new Empty();
		}

		if (sig.indexOf('.') >= 0) {
			ls = nonDecimal(new StringBuilder(sig.substring(0, sig.indexOf('.'))), INTEGER, ls, dp);
			ls = nonDecimal(new StringBuilder(sig.substring(sig.indexOf('.') + 1)), INTEGER, ls.concat(point(point)),
					dp).concat(sub(sigRadix, dp));
		} else {
			ls = nonDecimal(new StringBuilder(sig), INTEGER, ls, dp).concat(sub(sigRadix, dp));
		}

		if (!exp.isEmpty())
			if (exp.charAt(0) == '-') {
				exp = exp.substring(1);
				ls = nonDecimal(new StringBuilder(Integer.toString(Integer.parseInt(exp, sigRadix), expRadix)), INTEGER,
						ls.concat(radixExponent(norm)).concat(prefixMinus()), dp).concat(sub(expRadix, dp));
			} else if (exp.charAt(0) == '+') {
				exp = exp.substring(0);
				ls = nonDecimal(new StringBuilder(Integer.toString(Integer.parseInt(exp, sigRadix), expRadix)), INTEGER,
						ls.concat(radixExponent(norm)).concat(prefixPlus()), dp).concat(sub(expRadix, dp));
			} else {
				ls = nonDecimal(new StringBuilder(Integer.toString(Integer.parseInt(exp, sigRadix), expRadix)), INTEGER,
						ls.concat(radixExponent(norm)).concat(prefixPlus()), dp).concat(sub(expRadix, dp));
			}
		return ls;
	}

	private static LinkedSegment nonDecimal(StringBuilder n, int t, LinkedSegment s, DigitPunc dp) {
		if (n.length() > 0)
			return nonDecimal(n.deleteCharAt(0), t,
					s.concat(new Digit(String.format("\\mathrm{%s}", n.charAt(0)), t, dp)), dp);
		return s;
	}

	public static LinkedSegment i() {
		return new BasicSegment(" \\mathrm{i}", " i", Segment.Type.CONSTANT);
	}

	private Digits() {
	}
}