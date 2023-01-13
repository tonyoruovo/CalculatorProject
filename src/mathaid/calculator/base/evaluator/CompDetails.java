/**
 * 
 */
package mathaid.calculator.base.evaluator;

import static mathaid.calculator.base.evaluator.FractionalDetails.fixedPoint;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import mathaid.calculator.base.evaluator.parser.expression.scientific.Name.Params;
import mathaid.calculator.base.typeset.Digits;
import mathaid.calculator.base.typeset.LinkedSegment;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;
import mathaid.calculator.base.value.BigFraction;
import mathaid.calculator.base.value.Complex;

/*
 * Date: 29 Sep 2022----------------------------------------------------------- 
 * Time created: 09:50:32---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: CompDetails.java------------------------------------------------------ 
 * Class name: CompDetails------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
class CompDetails {

	public static Complex fromString(String str, int scale) {
//		int posRealSign = str.indexOf('+');
//		int negRealSign = str.indexOf('-');
//		int realSign = posRealSign < 0 ? negRealSign : posRealSign;
//		if(realSign < 0)
//			String real;
		String real, im;
		if (str.contains("Complex[")) {
			String n = str.replaceAll("Complex\\(", "").replaceAll("Complex\\[", "").replaceAll("\\]", "")
					.replaceAll("\\)Ii\\*", "");
			real = n.substring(0, n.indexOf(','));
			im = n.substring(n.indexOf(",") + 1, n.length());
		} else {
			// The extra negative 1 is for the imaginary sign such as 2.3+I
			int imIndex = str.indexOf('I');
			real = imIndex - 1 <= 0 ? "0" : str.substring(0, imIndex - 1);
			if (str.indexOf('*') < 0)
				im = "1";
			else
				im = str.substring(imIndex - 1 < 0 ? 0 : imIndex - 1, str.length()).replaceAll("I\\*", "");
		}
		try {
			return new Complex(new BigDecimal(real.trim()), new BigDecimal(im.trim()),
					new MathContext(scale, RoundingMode.HALF_EVEN));
		} catch (NumberFormatException e) {
		}
		BigFraction rFrac = FractionalDetails.fromString(real.trim());
		BigFraction iFrac = FractionalDetails.fromString(im.trim());
		return new Complex(rFrac.getDecimalExpansion(scale), iFrac.getDecimalExpansion(scale));
	}

	static int getExp(Params p) {
		switch (p.getResultType()) {
		case Params.ResultType.ENG:
			return 3;
		case Params.ResultType.SCI:
			return 1;
		default:
			return 0;
		}
	}

	public static LinkedSegment getReal(String str, Params p) {
		Complex z = fromString(str, p.getScale());
		return Digits.toSegment(z.real().round(new MathContext(p.getScale(), RoundingMode.HALF_EVEN)), getExp(p),
				Calculator.fromParams(p, p.getNumOfRepeats()));
	}

	public static LinkedSegment getImaginary(String str, Params p) {
		Complex z = fromString(str, p.getScale());
		return Digits.toSegment(z.imaginary().round(new MathContext(p.getScale(), RoundingMode.HALF_EVEN)), getExp(p),
				Calculator.fromParams(p, p.getNumOfRepeats()));
	}

	public static LinkedSegment getMagnitude(String str, Params p) {
		Complex z = fromString(str, p.getScale());
		return Digits.toSegment(z.magnitude().round(new MathContext(p.getScale(), RoundingMode.HALF_EVEN)), getExp(p),
				Calculator.fromParams(p, p.getNumOfRepeats()));
	}

	public static LinkedSegment getXCoordinate(String str, Params p) {
		Complex z = fromString(str, p.getScale());
		return Digits.toSegment(z.toRectangularCoordinates()[0].round(new MathContext(p.getScale(), RoundingMode.HALF_EVEN)), getExp(p),
				Calculator.fromParams(p, p.getNumOfRepeats()));
	}

	public static LinkedSegment getYCoordinate(String str, Params p) {
		Complex z = fromString(str, p.getScale());
		return Digits.toSegment(z.toRectangularCoordinates()[1].round(new MathContext(p.getScale(), RoundingMode.HALF_EVEN)), getExp(p),
				Calculator.fromParams(p, p.getNumOfRepeats()));
	}

	public static LinkedSegment getArg(String str, Params p) {
		Complex z = fromString(str, p.getScale());
		return Digits.toSegment(z.argument().round(new MathContext(p.getScale(), RoundingMode.HALF_EVEN)), getExp(p),
				Calculator.fromParams(p, p.getNumOfRepeats()));
	}

	public static LinkedSegment getExpression(String str, Params p) {
		Complex z = fromString(str, p.getScale());

		SegmentBuilder s = new SegmentBuilder();
		if (z.real().signum() != 0)
			s.append(Digits.toSegment(z.real().round(new MathContext(p.getScale(), RoundingMode.HALF_EVEN)), getExp(p),
					Calculator.fromParams(p, p.getNumOfRepeats())));
		if (z.imaginary().abs().compareTo(BigDecimal.ONE) != 0)
			s.append(Digits.toSegment(z.imaginary().round(new MathContext(p.getScale(), RoundingMode.HALF_EVEN)), getExp(p),
					Calculator.fromParams(p, p.getNumOfRepeats())));
		else if (z.imaginary().signum() < 0) {
			s.append(Segments.operator(" -", " -"));//.operator("-", "-", Segment.MINUS_OPERATOR_SEGMENT));
			s.append(Digits.i());//Segments.constant("I", "i"));
		} else if (z.imaginary().compareTo(BigDecimal.ONE) == 0)
			s.append(Digits.i());

		return s.toSegment();
	}

	public static LinkedSegment inFixed(String str, Params p) {
		Complex z = fromString(str, p.getScale());
		SegmentBuilder s = new SegmentBuilder();
		if (z.real().signum() != 0) {
			BigFraction rf = new BigFraction(z.real(), new MathContext(p.getScale(), RoundingMode.HALF_EVEN), null,
					new BigDecimal("1E-10"));
			String ss = fixedPoint(rf.getDecimalExpansion(p.getScale()), rf.signum(), p.getScale());
			s.append(Digits.toSegment(new BigDecimal(ss), 0, Calculator.fromParams(p, p.getNumOfRepeats())));
		}
		if (z.imaginary().abs().compareTo(BigDecimal.ONE) != 0) {
			BigFraction rf = new BigFraction(z.imaginary(), new MathContext(p.getScale(), RoundingMode.HALF_EVEN), null,
					new BigDecimal("1E-10"));
			String ss = fixedPoint(rf.getDecimalExpansion(p.getScale()), rf.signum(), p.getScale());
			s.append(Digits.toSegment(new BigDecimal(ss), 0, Calculator.fromParams(p, p.getNumOfRepeats())));

		} else if (z.imaginary().signum() < 0) {
			s.append(Segments.operator(" -", " -"));
			s.append(Digits.i());
		} else if (z.imaginary().compareTo(BigDecimal.ONE) == 0)
			s.append(Digits.i());

		return s.toSegment();
	}

	public static LinkedSegment inEngineering(String str, Params p) {
		Complex z = fromString(str, p.getScale());
		SegmentBuilder s = new SegmentBuilder();
		if (z.real().signum() != 0) {
			BigFraction rf = new BigFraction(z.real(), new MathContext(p.getScale(), RoundingMode.HALF_EVEN), null,
					new BigDecimal("1E-10"));
			String ss = Digits.toSegmentString(rf, 3);
			int scale = Digits.scale(ss);
			if (scale <= p.getScale())
				ss = Digits.truncateToScale(ss, p.getScale());
			s.append(Digits.toSegment(Digits.fromSegmentString(ss), false, p.getRecurringType(), Calculator.fromParams(p, p.getNumOfRepeats())));
		}
		if (z.imaginary().abs().compareTo(BigDecimal.ONE) != 0) {
			BigFraction rf = new BigFraction(z.imaginary(), new MathContext(p.getScale(), RoundingMode.HALF_EVEN), null,
					new BigDecimal("1E-10"));
			String ss = Digits.toSegmentString(rf, 3);
			int scale = Digits.scale(ss);
			if (scale <= p.getScale())
				ss = Digits.truncateToScale(ss, p.getScale());
			s.append(Digits.toSegment(Digits.fromSegmentString(ss), false, p.getRecurringType(), Calculator.fromParams(p, p.getNumOfRepeats())));

		} else if (z.imaginary().signum() < 0) {
			s.append(Segments.operator(" -", " -"));
			s.append(Digits.i());
		} else if (z.imaginary().compareTo(BigDecimal.ONE) == 0)
			s.append(Digits.i());

		return s.toSegment();
	}

	public static LinkedSegment inCommonFraction(String str, Params p) {
		Complex z = fromString(str, p.getScale());
		SegmentBuilder s = new SegmentBuilder();
		if (z.real().signum() != 0) {
			BigFraction rf = new BigFraction(z.real(), new MathContext(p.getScale(), RoundingMode.HALF_EVEN), null,
					new BigDecimal("1E-10"));
			s.append(Digits.toSegment(rf, false, Calculator.fromParams(p, p.getNumOfRepeats())));
		}
		if (z.imaginary().abs().compareTo(BigDecimal.ONE) != 0) {
			BigFraction rf = new BigFraction(z.imaginary(), new MathContext(p.getScale(), RoundingMode.HALF_EVEN), null,
					new BigDecimal("1E-10"));
			s.append(Digits.toSegment(rf, false, Calculator.fromParams(p, p.getNumOfRepeats())));

		} else if (z.imaginary().signum() < 0) {
			s.append(Segments.operator(" -", " -"));
			s.append(Digits.i());
		} else if (z.imaginary().compareTo(BigDecimal.ONE) == 0)
			s.append(Digits.i());

		return s.toSegment();
	}

	public static LinkedSegment inScientific(String str, Params p) {
		Complex z = fromString(str, p.getScale());
		SegmentBuilder s = new SegmentBuilder();
		if (z.real().signum() != 0) {
			BigFraction rf = new BigFraction(z.real(), new MathContext(p.getScale(), RoundingMode.HALF_EVEN), null,
					new BigDecimal("1E-10"));
			String ss = Digits.toSegmentString(rf, 1);
			int scale = Digits.scale(ss);
			if (scale <= p.getScale())
				ss = Digits.truncateToScale(ss, p.getScale());
			s.append(Digits.toSegment(Digits.fromSegmentString(ss), p.getRecurringType(), 1, Calculator.fromParams(p, p.getNumOfRepeats())));
		}
		if (z.imaginary().abs().compareTo(BigDecimal.ONE) > 0) {
			BigFraction rf = new BigFraction(z.imaginary(), new MathContext(p.getScale(), RoundingMode.HALF_EVEN), null,
					new BigDecimal("1E-10"));
			String ss = Digits.toSegmentString(rf, 1);
			int scale = Digits.scale(ss);
			if (scale <= p.getScale())
				ss = Digits.truncateToScale(ss, p.getScale());
			s.append(Digits.toSegment(Digits.fromSegmentString(ss), p.getRecurringType(), 1, Calculator.fromParams(p, p.getNumOfRepeats())));

		} else if (z.imaginary().signum() < 0) {
			s.append(Segments.operator(" -", " -"));
		}
		s.append(Digits.i());

		return s.toSegment();
	}

	public static LinkedSegment inEngineeringSuffix(String str, Params p) {
		Complex z = fromString(str, p.getScale());
		SegmentBuilder s = new SegmentBuilder();
		if (z.real().signum() != 0) {
			BigFraction rf = new BigFraction(z.real(), new MathContext(p.getScale(), RoundingMode.HALF_EVEN), null,
					new BigDecimal("1E-10"));
			s.append(Digits.toSegment(rf, true, p.getRecurringType(), Calculator.fromParams(p, p.getNumOfRepeats())));
//			String ss = Digits.toSegmentString(rf, 3);
//			SegmentBuilder sb;

//			int suffixIndex = ss.indexOf('E');
//			if (suffixIndex < ss.length() - 1) {
//				sb = new SegmentBuilder(Digits.toSegment(new BigDecimal(ss.substring(0, suffixIndex)), 0, Calculator.fromParams(p, p.getNumOfRepeats())));
//			} else {
//				Map<String, String> suffixes = new HashMap<>();
//				suffixes.put("Y", "E24");
//				suffixes.put("Z", "E21");
//				suffixes.put("E", "E18");
//				suffixes.put("P", "E15");
//				suffixes.put("T", "E12");
//				suffixes.put("G", "E9");
//				suffixes.put("M", "E6");
//				suffixes.put("K", "E3");
//				suffixes.put("m", "E-3");
//				suffixes.put("µ", "E-6");
//				suffixes.put("n", "E-9");
//				suffixes.put("p", "E-12");
//				suffixes.put("f", "E-15");
//				suffixes.put("a", "E-18");
//				suffixes.put("z", "E-21");
//				suffixes.put("y", "E-24");
//				String suffix = ss.substring(ss.length() - 1);
//				sb = new SegmentBuilder(Digits.toSegment(new BigDecimal(str.substring(0, str.length())), 0, Calculator.fromParams(p, p.getNumOfRepeats())));
//				sb.append(new BasicSegment(String.format("*10^(%s)", suffixes.get(suffix).substring(1)),
//						suffix.compareTo("µ") == 0 ? "\\mu" : suffix, Segment.Type.EXPONENT));
//			}
//			s.append(sb);
		}
		if (z.imaginary().abs().compareTo(BigDecimal.ONE) != 0) {
			BigFraction rf = new BigFraction(z.imaginary(), new MathContext(p.getScale(), RoundingMode.HALF_EVEN), null,
					new BigDecimal("1E-10"));
			s.append(Digits.toSegment(rf, true, p.getRecurringType(), Calculator.fromParams(p, p.getNumOfRepeats())));
//			String ss = Digits.toSegmentString(rf, 3);
//			SegmentBuilder sb;

//			int suffixIndex = ss.indexOf('E');
//			if (suffixIndex < ss.length() - 1) {
//				sb = new SegmentBuilder(Digits.toSegment(new BigDecimal(ss.substring(0, suffixIndex)), 0, Calculator.fromParams(p, p.getNumOfRepeats())));
//			} else {
//				Map<String, String> suffixes = new HashMap<>();
//				suffixes.put("Y", "E24");
//				suffixes.put("Z", "E21");
//				suffixes.put("E", "E18");
//				suffixes.put("P", "E15");
//				suffixes.put("T", "E12");
//				suffixes.put("G", "E9");
//				suffixes.put("M", "E6");
//				suffixes.put("K", "E3");
//				suffixes.put("m", "E-3");
//				suffixes.put("µ", "E-6");
//				suffixes.put("n", "E-9");
//				suffixes.put("p", "E-12");
//				suffixes.put("f", "E-15");
//				suffixes.put("a", "E-18");
//				suffixes.put("z", "E-21");
//				suffixes.put("y", "E-24");
//				String suffix = ss.substring(ss.length() - 1);
//				sb = new SegmentBuilder(Digits.toSegment(new BigDecimal(str.substring(0, str.length())), 0, Calculator.fromParams(p, p.getNumOfRepeats())));
//				sb.append(new BasicSegment(String.format("*10^(%s)", suffixes.get(suffix).substring(1)),
//						suffix.compareTo("µ") == 0 ? "\\mu" : suffix, Segment.Type.EXPONENT));
//			}
//			s.append(sb);
		} else if (z.imaginary().signum() < 0) {
			s.append(Segments.operator(" -", " -"));
		}
		s.append(Digits.i());

		return s.toSegment();
	}
}
