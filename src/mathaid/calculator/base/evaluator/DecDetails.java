/**
 * 
 */
package mathaid.calculator.base.evaluator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import mathaid.calculator.base.evaluator.parser.expression.scientific.Name.Params;
import mathaid.calculator.base.typeset.Digits;
import mathaid.calculator.base.typeset.LinkedSegment;
import mathaid.calculator.base.util.Utility;

/*
 * Date: 30 Sep 2022----------------------------------------------------------- 
 * Time created: 14:31:37---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: DecDetails.java------------------------------------------------------ 
 * Class name: DecDetails------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
class DecDetails {

	public static LinkedSegment inScientific(String str, Params p) {
		String n = Utility
				.toScientificString(new BigDecimal(str, new MathContext(p.getScale(), RoundingMode.HALF_EVEN)));
		return Digits.toSegment(new BigDecimal(n), 1, Calculator.fromParams(p, p.getNumOfRepeats()));
	}

	public static LinkedSegment inEngineering(String str, Params p) {
		String n = Utility
				.toEngineeringString(new BigDecimal(str, new MathContext(p.getScale(), RoundingMode.HALF_EVEN)), false);
		return Digits.toSegment(new BigDecimal(n), 3, Calculator.fromParams(p, p.getNumOfRepeats()));
	}

	public static LinkedSegment inEngineeringSI(String str, Params p) {
		String s = Utility
				.toEngineeringString(new BigDecimal(str, new MathContext(p.getScale(), RoundingMode.HALF_EVEN)), true);
		return Digits.toSegment(new BigDecimal(s), true, Calculator.fromParams(p, p.getNumOfRepeats()));
//		SegmentBuilder sb;

//		int suffixIndex = s.indexOf('E');
//		if (suffixIndex != s.length() - 1) {
//			sb = new SegmentBuilder(Digits.toSegment(new BigDecimal(s.substring(0, suffixIndex)), 0,
//					Calculator.fromParams(p, p.getNumOfRepeats())));
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
//			sb = new SegmentBuilder(Digits.toSegment(new BigDecimal(str.substring(0, str.length())), 0,
//					Calculator.fromParams(p, p.getNumOfRepeats())));
//			sb.append(new BasicSegment(String.format("*10^(%s)", suffixes.get(suffix).substring(1)),
//					suffix.compareTo("µ") == 0 ? "\\mu" : suffix, Segment.Type.EXPONENT));
//		}

//		return sb.toSegment();
	}
}
