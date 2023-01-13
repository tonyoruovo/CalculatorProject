/**
 * 
 */
package mathaid.calculator.base.evaluator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

import mathaid.calculator.base.evaluator.parser.expression.scientific.Name;
import mathaid.calculator.base.evaluator.parser.expression.scientific.Name.Params;
import mathaid.calculator.base.typeset.Digits;
import mathaid.calculator.base.typeset.LinkedSegment;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;
import mathaid.calculator.base.util.Utility;
import mathaid.calculator.base.value.BigFraction;

/*
 * Date: 27 Sep 2022----------------------------------------------------------- 
 * Time created: 10:56:26---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: FractionalDetails.java------------------------------------------------------ 
 * Class name: FractionalDetails------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
class FractionalDetails {

	public static BigFraction fromString(String str) {
		BigFraction f;
		if (str.contains("Rational[")) {
			String n = str.replaceAll("Rational\\(", "").replaceAll("Rational\\[", "").replaceAll("\\]", "")
					.replaceAll("\\)", "");
			String nm = n.substring(0, n.indexOf(','));
			String d = n.substring(n.indexOf(",") + 1, n.length());
			f = new BigFraction(nm.trim(), d.trim());
		} else {
			try {
				f = new BigFraction(str.substring(str.indexOf('[') + 1, str.indexOf(',')).trim(),
						str.substring(str.indexOf(',') + 1, str.indexOf(']')).trim());
			} catch (@SuppressWarnings("unused") StringIndexOutOfBoundsException | NumberFormatException e) {
				f = new BigFraction(str);
			}
		}
		return f;
	}

	public static LinkedSegment toMixedFraction(String str, Name.Params p) {
		BigFraction f = fromString(str);
		return Digits.toSegment(f, true, Calculator.fromParams(p, p.getNumOfRepeats()));
	}

	public static LinkedSegment toLowestTerm(String str, Name.Params p) {
		BigFraction f = fromString(str).toLowestTerms();
		return Digits.toSegment(f, false, Calculator.fromParams(p, p.getNumOfRepeats()));
	}

	static LinkedSegment integer(BigInteger num, Name.Params p) {
		return Digits.toSegment(num, 10, Calculator.fromParams(p, p.getNumOfRepeats()));
	}

	static BigInteger i(String s) {
		return new BigInteger(s);
	}

	public static LinkedSegment getContinuedFraction(String str, Name.Params p) {
		BigFraction f = fromString(str);
		List<BigInteger> l = f.toContinuedFraction();
		SegmentBuilder sb = new SegmentBuilder();
		for (int i = l.size() - 1; i >= 0; i--) {
			BigInteger n = l.get(i);
			if (n.signum() != 0)
				if (!sb.isEmpty()) {
					LinkedSegment s = sb.toSegment();
					SegmentBuilder nw = new SegmentBuilder(integer(n, p))
							.append(Segments.operator(" +", " +")).append(s);
					sb.deleteAll().append(Segments.fraction(integer(i("1"), p), nw.toSegment()));
				} else
					sb.append(Segments.fraction(integer(i("1"), p), integer(n, p)));
		}
		return sb.toSegment();
	}

	public static LinkedSegment getEgyptianFraction(String str, Params p) {
		BigFraction f = fromString(str);
		List<BigFraction> l = f.toEgyptianFractions();
		SegmentBuilder sb = new SegmentBuilder(Digits.toSegment(l.get(0), false, Calculator.fromParams(p, p.getNumOfRepeats())));
		for (int i = 1; i < l.size(); i++) {
			BigFraction fr = l.get(i);
			sb.append(Segments.operator("+", "+"));
			sb.append(Digits.toSegment(fr, false, Calculator.fromParams(p, p.getNumOfRepeats())));
		}
		return sb.toSegment();
	}

	public static LinkedSegment getFactors(String str, Params p) {
		BigFraction f = fromString(str);
		List<BigInteger> l = f.factorize();

		BigInteger current = null;
		BigInteger previous = i("0");

		int exponent = 1;
		int negativeExponent = exponent;

		SegmentBuilder sb = new SegmentBuilder();
		for (int i = 0; i < l.size(); i++) {
			current = l.get(i);
			if (current == null || (current.compareTo(previous) != 0 && previous.signum() != 0)) {
				sb.append(exponent * negativeExponent == 1 ? integer(previous, p)
						: Segments.pow(integer(previous, p),
								integer(i(String.valueOf(exponent * negativeExponent)), p)))
						.append(Segments.operator("\\cdot", "*"));

				exponent = 1;
				if (current == null) {
					negativeExponent *= -1;
					previous = i("0");
					continue;
				}
			} else if (current.compareTo(previous) == 0)
				exponent++;
			previous = current;
		}
		sb.append(exponent * negativeExponent == 1 ? integer(current, p)
				: Segments.pow(integer(current, p), integer(i(String.valueOf(exponent * negativeExponent)), p)));
		return sb.toSegment();
	}

	public static LinkedSegment getFractionAsDecimal(String str, Params p) {
		BigFraction f = fromString(str);
//		String s = Digits.toSegmentString(f, 0);
		return Digits.toSegment(f, p.getRecurringType(), 0, Calculator.fromParams(p, p.getNumOfRepeats()));
	}

	public static LinkedSegment getPeriod(String str, Params p) {
		BigFraction f = fromString(str);
		return integer(BigInteger.valueOf(f.getPeriod()), p);
	}

	public static LinkedSegment getRemainder(String str, Params p) {
		BigFraction f = fromString(str);
		BigInteger rem = f.toMixed()[1];
		return integer(rem, p);
	}

	public static LinkedSegment getPercentage(String str, Params p) {
		BigFraction f = fromString(str);
		BigDecimal percent = f.percent();
		return Digits
				.toSegment(percent, 0, Calculator.fromParams(p, p.getNumOfRepeats()))
				.concat(Segments.operator("%", "*Rational[1, 100]"));
	}

	public static LinkedSegment getQuotient(String str, Params p) {
		BigFraction f = fromString(str);
		BigInteger quotient = f.toMixed()[0];
		return integer(quotient, p);
	}

	public static LinkedSegment inEngineering(String str, Params p) {
		BigFraction f = fromString(str);
		String s = Digits.toSegmentString(f, 3);
		int scale = Digits.scale(s);
		if (scale > p.getScale())
			s = Digits.truncateToScale(s, p.getScale());
		return Digits.toSegment(Digits.fromSegmentString(s), p.getRecurringType(), 3, Calculator.fromParams(p, p.getNumOfRepeats()));
	}

	public static LinkedSegment inEngineeringSuffix(String str, Params p) {
		BigFraction f = fromString(str);
		return Digits.toSegment(f, true, p.getRecurringType(), Calculator.fromParams(p, p.getNumOfRepeats()));
//		String s = Utility.toEngineeringString(f.getDecimalExpansion(p.getScale()), true);
//		SegmentBuilder sb;

//		int suffixIndex = s.indexOf('E');
//		if (suffixIndex != s.length() - 1) {
//			sb = new SegmentBuilder(Digits.toSegment(new BigDecimal(s.substring(0, suffixIndex)), 0, Calculator.fromParams(p, p.getNumOfRepeats())));
//		} else {
//			Map<String, String> suffixes = new HashMap<>();
//			suffixes.put("Y", "E24");
//			suffixes.put("Z", "E21");
//			suffixes.put("E", "E18");
//			suffixes.put("P", "E15");
//			suffixes.put("T", "E12");
//			suffixes.put("G", "E9");
//			suffixes.put("M", "E6");
//			suffixes.put("K", "E3");
//			suffixes.put("m", "E-3");
//			suffixes.put("µ", "E-6");
//			suffixes.put("n", "E-9");
//			suffixes.put("p", "E-12");
//			suffixes.put("f", "E-15");
//			suffixes.put("a", "E-18");
//			suffixes.put("z", "E-21");
//			suffixes.put("y", "E-24");
//			String suffix = s.substring(s.length() - 1);
//			sb = new SegmentBuilder(Digits.toSegment(new BigDecimal(str.substring(0, str.length())), 0, Calculator.fromParams(p, p.getNumOfRepeats())));
//			sb.append(new BasicSegment(String.format("*10^(%s)", suffixes.get(suffix).substring(1)),
//					suffix.compareTo("µ") == 0 ? "\\mu" : suffix, Segment.Type.EXPONENT));
//		}

//		return sb.toSegment();
	}

	static int getTotalScale(BigDecimal n) {
		return Utility.numOfIntegerDigits(n) + Utility.numOfFractionalDigits(n);
	}

	static String fixedPoint(BigDecimal n, int signum, int scale) {
		n = n.abs();
		int totalScale = getTotalScale(n);
		if (totalScale > scale) {
			n = n.round(new MathContext(scale, RoundingMode.HALF_EVEN));
			n = n.stripTrailingZeros();
			return n.multiply(new BigDecimal(signum)).toPlainString();
		}
		totalScale = scale - totalScale;
		return n.setScale(totalScale, RoundingMode.HALF_EVEN).multiply(new BigDecimal(signum)).toPlainString();
	}

	public static LinkedSegment inScientific(String str, Params p) {
		BigFraction f = fromString(str);
		String s = Digits.toSegmentString(f, 1);
		int scale = Digits.scale(s);
//		if (scale <= p.getScale())
//			return Segments
//					.toSegment(s, p.getDecimalPoint(), p.getRecurringType(), 10, p.getIntSeparator(),
//							p.getMantSeparator(), p.getIntGroupSize(), p.getMantGroupSize(), p.getNumOfRepeats())
//					.toSegment();
//		return Segments.toSegment(truncateToScale(s, p.getScale()), p.getDecimalPoint(), p.getRecurringType(), 10,
//				p.getIntSeparator(), p.getMantSeparator(), p.getIntGroupSize(), p.getMantGroupSize(),
//				p.getNumOfRepeats()).toSegment();
		if (scale > p.getScale())
			s = Digits.truncateToScale(s, p.getScale());
		return Digits.toSegment(f, true, p.getRecurringType(), Calculator.fromParams(p, p.getNumOfRepeats()));
	}

	public static LinkedSegment inFixed(String str, Params p) {
		BigFraction f = fromString(str);
		String s = fixedPoint(f.getDecimalExpansion(p.getScale()), f.signum(), p.getScale());
		return Digits.toSegment(new BigDecimal(s), 0, Calculator.fromParams(p, p.getNumOfRepeats()));
	}
}
