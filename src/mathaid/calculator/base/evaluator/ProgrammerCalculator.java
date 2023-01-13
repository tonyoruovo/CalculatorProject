/**
 * 
 */
package mathaid.calculator.base.evaluator;

import static mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression.Params.ResultType.ENDIAN_BIG;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression.Params.ResultType.ENDIAN_PDP_11;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression.Params.ResultType.ENDIAN_SMALL;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression.Params.ResultType.REP_EXCESS_K;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression.Params.ResultType.REP_FLOATING_POINT;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression.Params.ResultType.REP_MATH;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression.Params.ResultType.REP_NEGABINARY;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression.Params.ResultType.REP_ONE_C;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression.Params.ResultType.REP_SMR;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression.Params.ResultType.REP_TWO_C;
import static mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression.Params.ResultType.REP_UNSIGNED;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import mathaid.MomentString;
import mathaid.calculator.base.converter.AngleUnit;
import mathaid.calculator.base.evaluator.parser.PrattParser;
import mathaid.calculator.base.evaluator.parser.ProgrammerLexer;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression;
import mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression.Params;
import mathaid.calculator.base.gui.GUIComponent;
import mathaid.calculator.base.gui.KeyAction;
import mathaid.calculator.base.gui.KeyBoard;
import mathaid.calculator.base.typeset.Digits;
import mathaid.calculator.base.typeset.LinkedSegment;
import mathaid.calculator.base.typeset.Segment;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;
import mathaid.calculator.base.util.Tuple;
import mathaid.calculator.base.util.Tuple.Couple;
import mathaid.calculator.base.value.BinaryFPPrecision;
import mathaid.calculator.base.value.BinaryFPPrecision.BinaryFP;
import mathaid.calculator.base.value.BitLength;
import mathaid.calculator.base.value.Ex;
import mathaid.calculator.base.value.FloatAid;
import mathaid.calculator.base.value.MT;
import mathaid.calculator.base.value.NB;
import mathaid.calculator.base.value.OC;
import mathaid.calculator.base.value.SMR;
import mathaid.calculator.base.value.TC;
import mathaid.calculator.base.value.US;
import mathaid.functional.Supplier.Function;

/*
 * Date: 8 Oct 2022----------------------------------------------------------- 
 * Time created: 09:03:56---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: ProgrammerCalculator.java------------------------------------------------------ 
 * Class name: ProgrammerCalculator------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class ProgrammerCalculator<T extends GUIComponent<T>, F> implements Calculator<T, F>, PExpression.Params {
	
	private static void fixFPDetails(Map<MomentString, LinkedSegment> details, Tuple.Couple<BinaryFP, BigInteger> temp, Params p) {
		if(temp.get() == null) {
			BinaryFP fp = getPrecision(p).fromBitLayout(temp.get2nd());
			LinkedSegment seg = Digits.toSegment(fp, p.getRadix(), 10, false, p.getDecimalPoint().charAt(0), Calculator.fromParams(p, Segment.Type.VINCULUM));
			details.put(new MomentString("As decimal floating-point"), seg);
		} else if(temp.get2nd() == null) {
			BinaryFP fp = temp.get();
			LinkedSegment seg = Segments.toSegment(Tuple.of(BigInteger.valueOf(fp.signum() < 0 ? 1 : 0), Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize())))
					.toSegment();
			details.put(new MomentString("Signed magnitude (Binary)"), seg);
		}
	}
	
	private static void fixUSDetails(Map<MomentString, LinkedSegment> details, BigInteger[] temp, Params p) {
		BigInteger[] r = { temp[0], temp[1] };
		// SMR
		SMR.fromDecimal(p.getBitLength(), r);
		LinkedSegment seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize())))
				.toSegment();
		details.put(new MomentString("Signed magnitude (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Signed magnitude (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Signed magnitude (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Signed magnitude (Hexadecimal)"), seg);
		// 1's
		r[0] = temp[0];
		r[1] = temp[1];
		OC.fromDecimal(p.getBitLength(), r);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("1's complement (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("1's complement (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("1's complement (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("1's complement (Hexadecimal)"), seg);
		// 2's
		r[0] = temp[0];
		r[1] = temp[1];
		TC.fromDecimal(p.getBitLength(), r);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("2's complement (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("2's complement (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("2's complement (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("2's complement (Hexadecimal)"), seg);
		// nega
		r[0] = temp[0];
		r[1] = temp[1];
		NB.fromDecimal(p.getBitLength(), r);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Negabinary (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Negabinary (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Negabinary (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Negabinary (Hexadecimal)"), seg);
		// math
		seg = Segments.toSegment(Tuple.of(temp[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Mathematical (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(temp[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Mathematical (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(temp[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Mathematical (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(temp[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Mathematical (Hexadecimal)"), seg);
		// excess-n
		r[0] = temp[0];
		r[1] = temp[1];
		Ex.fromDecimal(p.getBitLength(), r);
		String n = FloatAid.getTrailingZeros(p.getBitLength() - 1).toString(10);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString(String.format("Excess-%s (Binary)", n)), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString(String.format("Excess-%s (Octal)", n)), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString(String.format("Excess-%s (Decimal)", n)), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString(String.format("Excess-%s (Hexadecimal)", n)), seg);
		// floating point
		if (p.getBitLength() >= 8) {
			BinaryFP fp = getPrecision(p).fromBitLayout(temp[2]);
			seg = Segments.toSegment(fp, 10, p.getDecimalPoint(), p.getIntSeparator(), p.getMantSeparator(),
					p.getIntGroupSize(), p.getMantGroupSize(), false).toSegment();
			details.put(new MomentString("As decimal floating-point (Unsigned)"), seg);
		}
	}
	
	private static void fixSMRDetails(Map<MomentString, LinkedSegment> details, BigInteger[] temp, Params p) {
		BigInteger[] r = { temp[0], temp[1] };
		// nega
		r[0] = temp[0];
		r[1] = temp[1];
		NB.fromDecimal(p.getBitLength(), r);
		LinkedSegment seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Negabinary (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Negabinary (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Negabinary (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Negabinary (Hexadecimal)"), seg);
		// 1's
		r[0] = temp[0];
		r[1] = temp[1];
		OC.fromDecimal(p.getBitLength(), r);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("1's complement (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("1's complement (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("1's complement (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("1's complement (Hexadecimal)"), seg);
		// 2's
		r[0] = temp[0];
		r[1] = temp[1];
		TC.fromDecimal(p.getBitLength(), r);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("2's complement (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("2's complement (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("2's complement (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("2's complement (Hexadecimal)"), seg);
		// excess-n
		r[0] = temp[0];
		r[1] = temp[1];
		Ex.fromDecimal(p.getBitLength(), r);
		String n = FloatAid.getTrailingZeros(p.getBitLength() - 1).toString(10);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString(String.format("Excess-%s (Binary)", n)), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString(String.format("Excess-%s (Octal)", n)), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString(String.format("Excess-%s (Decimal)", n)), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString(String.format("Excess-%s (Hexadecimal)", n)), seg);
		// math
		seg = Segments.toSegment(Tuple.of(temp[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Mathematical (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(temp[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Mathematical (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(temp[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Mathematical (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(temp[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Mathematical (Hexadecimal)"), seg);
		// unsigned
		r[0] = temp[0];
		r[1] = temp[1];
		US.fromDecimal(p.getBitLength(), r);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Unsigned (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Unsigned (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Unsigned (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Unsigned (Hexadecimal)"), seg);
		// floating point
		if (p.getBitLength() >= 8) {
			BinaryFP fp = getPrecision(p).fromBitLayout(temp[2]);
			seg = Segments.toSegment(fp, 10, p.getDecimalPoint(), p.getIntSeparator(), p.getMantSeparator(),
					p.getIntGroupSize(), p.getMantGroupSize(), false).toSegment();
			details.put(new MomentString("As decimal floating-point (Signed magnitude)"), seg);
		}
	}
	
	private static void fixTCDetails(Map<MomentString, LinkedSegment> details, BigInteger[] temp, Params p) {
		BigInteger[] r = { temp[0], temp[1] };
		// SMR
		SMR.fromDecimal(p.getBitLength(), r);
		LinkedSegment seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize())))
				.toSegment();
		details.put(new MomentString("Signed magnitude (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Signed magnitude (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Signed magnitude (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Signed magnitude (Hexadecimal)"), seg);
		// 1's
		r[0] = temp[0];
		r[1] = temp[1];
		OC.fromDecimal(p.getBitLength(), r);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("1's complement (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("1's complement (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("1's complement (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("1's complement (Hexadecimal)"), seg);
		// excess-n
		r[0] = temp[0];
		r[1] = temp[1];
		Ex.fromDecimal(p.getBitLength(), r);
		String n = FloatAid.getTrailingZeros(p.getBitLength() - 1).toString(10);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString(String.format("Excess-%s (Binary)", n)), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString(String.format("Excess-%s (Octal)", n)), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString(String.format("Excess-%s (Decimal)", n)), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString(String.format("Excess-%s (Hexadecimal)", n)), seg);
		// nega
		r[0] = temp[0];
		r[1] = temp[1];
		NB.fromDecimal(p.getBitLength(), r);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Negabinary (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Negabinary (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Negabinary (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Negabinary (Hexadecimal)"), seg);
		// math
		seg = Segments.toSegment(Tuple.of(temp[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Mathematical (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(temp[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Mathematical (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(temp[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Mathematical (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(temp[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Mathematical (Hexadecimal)"), seg);
		// unsigned
		r[0] = temp[0];
		r[1] = temp[1];
		US.fromDecimal(p.getBitLength(), r);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Unsigned (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Unsigned (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Unsigned (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Unsigned (Hexadecimal)"), seg);
		// floating point
		if (p.getBitLength() >= 8) {
			BinaryFP fp = getPrecision(p).fromBitLayout(temp[2]);
			seg = Segments.toSegment(fp, 10, p.getDecimalPoint(), p.getIntSeparator(), p.getMantSeparator(),
					p.getIntGroupSize(), p.getMantGroupSize(), false).toSegment();
			details.put(new MomentString("As decimal floating-point (2's complement)"), seg);
		}
	}

	private static void fixOCDetails(Map<MomentString, LinkedSegment> details, BigInteger[] temp, Params p) {
		BigInteger[] r = { temp[0], temp[1] };
		// SMR
		SMR.fromDecimal(p.getBitLength(), r);
		LinkedSegment seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize())))
				.toSegment();
		details.put(new MomentString("Signed magnitude (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Signed magnitude (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Signed magnitude (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Signed magnitude (Hexadecimal)"), seg);
		// nega
		r[0] = temp[0];
		r[1] = temp[1];
		NB.fromDecimal(p.getBitLength(), r);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Negabinary (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Negabinary (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Negabinary (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Negabinary (Hexadecimal)"), seg);
		// 2's
		r[0] = temp[0];
		r[1] = temp[1];
		TC.fromDecimal(p.getBitLength(), r);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("2's complement (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("2's complement (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("2's complement (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("2's complement (Hexadecimal)"), seg);
		// excess-n
		r[0] = temp[0];
		r[1] = temp[1];
		Ex.fromDecimal(p.getBitLength(), r);
		String n = FloatAid.getTrailingZeros(p.getBitLength() - 1).toString(10);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString(String.format("Excess-%s (Binary)", n)), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString(String.format("Excess-%s (Octal)", n)), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString(String.format("Excess-%s (Decimal)", n)), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString(String.format("Excess-%s (Hexadecimal)", n)), seg);
		// math
		seg = Segments.toSegment(Tuple.of(temp[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Mathematical (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(temp[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Mathematical (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(temp[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Mathematical (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(temp[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Mathematical (Hexadecimal)"), seg);
		// unsigned
		r[0] = temp[0];
		r[1] = temp[1];
		US.fromDecimal(p.getBitLength(), r);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Unsigned (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Unsigned (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Unsigned (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Unsigned (Hexadecimal)"), seg);
		// floating point
		if (p.getBitLength() >= 8) {
			BinaryFP fp = getPrecision(p).fromBitLayout(temp[2]);
			seg = Segments.toSegment(fp, 10, p.getDecimalPoint(), p.getIntSeparator(), p.getMantSeparator(),
					p.getIntGroupSize(), p.getMantGroupSize(), false).toSegment();
			details.put(new MomentString("As decimal floating-point (1's complement)"), seg);
		}
	}
	
	private static void fixNBDetails(Map<MomentString, LinkedSegment> details, BigInteger[] temp, Params p) {
		BigInteger[] r = { temp[0], temp[1] };
		// SMR
		SMR.fromDecimal(p.getBitLength(), r);
		LinkedSegment seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize())))
				.toSegment();
		details.put(new MomentString("Signed magnitude (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Signed magnitude (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Signed magnitude (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Signed magnitude (Hexadecimal)"), seg);
		// 1's
		r[0] = temp[0];
		r[1] = temp[1];
		OC.fromDecimal(p.getBitLength(), r);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("1's complement (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("1's complement (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("1's complement (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("1's complement (Hexadecimal)"), seg);
		// 2's
		r[0] = temp[0];
		r[1] = temp[1];
		TC.fromDecimal(p.getBitLength(), r);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("2's complement (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("2's complement (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("2's complement (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("2's complement (Hexadecimal)"), seg);
		// excess-n
		r[0] = temp[0];
		r[1] = temp[1];
		Ex.fromDecimal(p.getBitLength(), r);
		String n = FloatAid.getTrailingZeros(p.getBitLength() - 1).toString(10);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString(String.format("Excess-%s (Binary)", n)), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString(String.format("Excess-%s (Octal)", n)), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString(String.format("Excess-%s (Decimal)", n)), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString(String.format("Excess-%s (Hexadecimal)", n)), seg);
		// math
		seg = Segments.toSegment(Tuple.of(temp[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Mathematical (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(temp[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Mathematical (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(temp[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Mathematical (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(temp[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Mathematical (Hexadecimal)"), seg);
		// unsigned
		r[0] = temp[0];
		r[1] = temp[1];
		US.fromDecimal(p.getBitLength(), r);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Unsigned (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Unsigned (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Unsigned (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Unsigned (Hexadecimal)"), seg);
		// floating point
		if (p.getBitLength() >= 8) {
			BinaryFP fp = getPrecision(p).fromBitLayout(temp[2]);
			seg = Segments.toSegment(fp, 10, p.getDecimalPoint(), p.getIntSeparator(), p.getMantSeparator(),
					p.getIntGroupSize(), p.getMantGroupSize(), false).toSegment();
			details.put(new MomentString("As decimal floating-point (Negabinary)"), seg);
		}
	}

	private static void fixMTDetails(Map<MomentString, LinkedSegment> details, BigInteger[] temp, Params p) {
		BigInteger[] r = { temp[0], temp[1] };
		// SMR
		SMR.fromDecimal(p.getBitLength(), r);
		LinkedSegment seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize())))
				.toSegment();
		details.put(new MomentString("Signed magnitude (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Signed magnitude (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Signed magnitude (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Signed magnitude (Hexadecimal)"), seg);
		// 1's
		r[0] = temp[0];
		r[1] = temp[1];
		OC.fromDecimal(p.getBitLength(), r);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("1's complement (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("1's complement (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("1's complement (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("1's complement (Hexadecimal)"), seg);
		// 2's
		r[0] = temp[0];
		r[1] = temp[1];
		TC.fromDecimal(p.getBitLength(), r);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("2's complement (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("2's complement (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("2's complement (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("2's complement (Hexadecimal)"), seg);
		// nega
		r[0] = temp[0];
		r[1] = temp[1];
		NB.fromDecimal(p.getBitLength(), r);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Negabinary (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Negabinary (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Negabinary (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Negabinary (Hexadecimal)"), seg);
		// excess-n
		r[0] = temp[0];
		r[1] = temp[1];
		Ex.fromDecimal(p.getBitLength(), r);
		String n = FloatAid.getTrailingZeros(p.getBitLength() - 1).toString(10);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString(String.format("Excess-%s (Binary)", n)), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString(String.format("Excess-%s (Octal)", n)), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString(String.format("Excess-%s (Decimal)", n)), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString(String.format("Excess-%s (Hexadecimal)", n)), seg);
		// unsigned
		r[0] = temp[0];
		r[1] = temp[1];
		US.fromDecimal(p.getBitLength(), r);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Unsigned (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Unsigned (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Unsigned (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Unsigned (Hexadecimal)"), seg);
		// floating point
		if (p.getBitLength() >= 8) {
			BinaryFP fp = getPrecision(p).fromBitLayout(temp[2]);
			seg = Segments.toSegment(fp, 10, p.getDecimalPoint(), p.getIntSeparator(), p.getMantSeparator(),
					p.getIntGroupSize(), p.getMantGroupSize(), false).toSegment();
			details.put(new MomentString("As decimal floating-point (Mathematical)"), seg);
		}
	}

	private static void fixExDetails(Map<MomentString, LinkedSegment> details, BigInteger[] temp, Params p) {
		BigInteger[] r = { temp[0], temp[1] };
		// SMR
		SMR.fromDecimal(p.getBitLength(), r);
		LinkedSegment seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize())))
				.toSegment();
		details.put(new MomentString("Signed magnitude (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Signed magnitude (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Signed magnitude (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Signed magnitude (Hexadecimal)"), seg);
		// 1's
		r[0] = temp[0];
		r[1] = temp[1];
		OC.fromDecimal(p.getBitLength(), r);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("1's complement (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("1's complement (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("1's complement (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("1's complement (Hexadecimal)"), seg);
		// 2's
		r[0] = temp[0];
		r[1] = temp[1];
		TC.fromDecimal(p.getBitLength(), r);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("2's complement (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("2's complement (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("2's complement (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("2's complement (Hexadecimal)"), seg);
		// nega
		r[0] = temp[0];
		r[1] = temp[1];
		NB.fromDecimal(p.getBitLength(), r);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Negabinary (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Negabinary (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Negabinary (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Negabinary (Hexadecimal)"), seg);
		// math
		seg = Segments.toSegment(Tuple.of(temp[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Mathematical (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(temp[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Mathematical (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(temp[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Mathematical (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(temp[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Mathematical (Hexadecimal)"), seg);
		// unsigned
		r[0] = temp[0];
		r[1] = temp[1];
		US.fromDecimal(p.getBitLength(), r);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(2, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Unsigned (Binary)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(8, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Unsigned (Octal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(10, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Unsigned (Decimal)"), seg);
		seg = Segments.toSegment(Tuple.of(r[0], Tuple.of(16, p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
		details.put(new MomentString("Unsigned (Hexadecimal)"), seg);
		// floating point
		if (p.getBitLength() >= 8) {
			BinaryFP fp = getPrecision(p).fromBitLayout(temp[2]);
			String n = FloatAid.getTrailingZeros(p.getBitLength() - 1).toString(10);
			seg = Segments.toSegment(fp, 10, p.getDecimalPoint(), p.getIntSeparator(), p.getMantSeparator(),
					p.getIntGroupSize(), p.getMantGroupSize(), false).toSegment();
			details.put(new MomentString(String.format("As decimal floating-point (Excess-%s)", n)), seg);
		}
	}

	private static BinaryFPPrecision getPrecision(Params p) {
		switch (p.getBitLength()) {
		case 8:
		default:
			return FloatAid.IEEE754Bit8(p.getRoundingMode());
		case 16:
			return FloatAid.IEEE754Half(p.getRoundingMode());
		case 32:
			return FloatAid.IEEE754Single(p.getRoundingMode());
		case 64:
			return FloatAid.IEEE754Double(p.getRoundingMode());
		case 80:
			return FloatAid.IEEE754x86Extended(p.getRoundingMode());
		case 128:
			return FloatAid.IEEE754Quadruple(p.getRoundingMode());
		case 256:
			return FloatAid.IEEE754Octuple(p.getRoundingMode());

		}
	}

	private static void fixEndianDetails(Map<MomentString, LinkedSegment> details, BigInteger val, Params p) {
		if (p.getEndianess() == ENDIAN_BIG) {
			LinkedSegment seg = Segments
					.toSegment(Tuple.of(val, Tuple.of(p.getRadix(), p.getIntSeparator(), p.getIntGroupSize())))
					.toSegment();
			details.put(new MomentString("Expression (Big endian)"), seg);
			seg = Segments.toSegment(Tuple.of(BitLength.toSmallEndian(val),
					Tuple.of(p.getRadix(), p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
			details.put(new MomentString("Expression (Little endian)"), seg);
			seg = Segments.toSegment(Tuple.of(BitLength.toPDP11Endian(val),
					Tuple.of(p.getRadix(), p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
			details.put(new MomentString("Expression (PDP-11 endian)"), seg);

		} else if (p.getEndianess() == ENDIAN_SMALL) {
			LinkedSegment seg = Segments.toSegment(Tuple.of(BitLength.toSmallEndian(val),
					Tuple.of(p.getRadix(), p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
			details.put(new MomentString("Expression (Big endian)"), seg);
			seg = Segments.toSegment(Tuple.of(val, Tuple.of(p.getRadix(), p.getIntSeparator(), p.getIntGroupSize())))
					.toSegment();
			details.put(new MomentString("Expression (Little endian)"), seg);
			seg = Segments.toSegment(Tuple.of(BitLength.toPDP11Endian(BitLength.toSmallEndian(val)),
					Tuple.of(p.getRadix(), p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
			details.put(new MomentString("Expression (PDP-11 endian)"), seg);
		} else if (p.getEndianess() == ENDIAN_PDP_11) {
			LinkedSegment seg = Segments.toSegment(Tuple.of(BitLength.toPDP11Endian(val),
					Tuple.of(p.getRadix(), p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
			details.put(new MomentString("Expression (Big endian)"), seg);
			seg = Segments.toSegment(Tuple.of(BitLength.toSmallEndian(BitLength.toPDP11Endian(val)),
					Tuple.of(p.getRadix(), p.getIntSeparator(), p.getIntGroupSize()))).toSegment();
			details.put(new MomentString("Expression (Little endian)"), seg);
			seg = Segments.toSegment(Tuple.of(val, Tuple.of(p.getRadix(), p.getIntSeparator(), p.getIntGroupSize())))
					.toSegment();
			details.put(new MomentString("Expression (PDP-11 endian)"), seg);
		}

	}

	/*
	 * Date: 8 Oct 2022-----------------------------------------------------------
	 * Time created: 09:48:29---------------------------------------------------
	 */
	/**
	 */
	public ProgrammerCalculator(KeyBoard<T, F> keyboard, KeyAction<T, String> modKeys) {
		this.keyboard = keyboard;
		this.modKeys = modKeys;
	}

	class Programmer extends DetailsList<PExpression.Params> {
		@Override
		public void run() {
			StringBuilder sb = new StringBuilder();
			src.toString(sb, null, new ArrayList<>(Arrays.asList(-1)));
			String s = sb.toString();
			BigInteger val = null;
			BinaryFP fval = null;
			try{
				val = new BigInteger(s, getRadix());
			} catch(NumberFormatException e) {
				fval = getPrecision(ProgrammerCalculator.this).createFP(s, getRadix());
			}
			if (getBitRepresentation() == REP_MATH || getBitRepresentation() == REP_FLOATING_POINT)
				details.put(new MomentString("Expression"), src);
			else if(val != null)
				fixEndianDetails(details, val, ProgrammerCalculator.this);
			switch (getBitRepresentation()) {
			case REP_EXCESS_K: {
				BigInteger carry = BigInteger.ZERO;
				// An extra val to prevent the array mutators from changing the original answer
				// value so as to retrieve it for floating-point details
				BigInteger[] temp = { val, carry, val };
				MT.fromEx(getBitLength(), temp);
				fixExDetails(details, temp, ProgrammerCalculator.this);
				break;
			}
			case REP_MATH: {
				BigInteger carry = BigInteger.ZERO;
				// An extra val to prevent the array mutators from changing the original answer
				// value so as to retrieve it for floating-point details
				BigInteger[] temp = { val, carry, val };
				fixMTDetails(details, temp, ProgrammerCalculator.this);
				break;
			}
			case REP_NEGABINARY: {
				BigInteger carry = BigInteger.ZERO;
				// An extra val to prevent the array mutators from changing the original answer
				// value so as to retrieve it for floating-point details
				BigInteger[] temp = { val, carry, val };
				MT.fromNB(getBitLength(), temp);
				fixNBDetails(details, temp, ProgrammerCalculator.this);
				break;
			}
			case REP_ONE_C: {
				BigInteger carry = BigInteger.ZERO;
				// An extra val to prevent the array mutators from changing the original answer
				// value so as to retrieve it for floating-point details
				BigInteger[] temp = { val, carry, val };
				MT.fromOC(getBitLength(), temp);
				fixOCDetails(details, temp, ProgrammerCalculator.this);
				break;
			}
			case REP_TWO_C: {
				BigInteger carry = BigInteger.ZERO;
				// An extra val to prevent the array mutators from changing the original answer
				// value so as to retrieve it for floating-point details
				BigInteger[] temp = { val, carry, val };
				MT.fromTC(getBitLength(), temp);
				fixTCDetails(details, temp, ProgrammerCalculator.this);
				break;
			}
			case REP_SMR: {
				BigInteger carry = BigInteger.ZERO;
				// An extra val to prevent the array mutators from changing the original answer
				// value so as to retrieve it for floating-point details
				BigInteger[] temp = { val, carry, val };
				MT.fromSMR(getBitLength(), temp);
				fixSMRDetails(details, temp, ProgrammerCalculator.this);
				break;
			}
			case REP_UNSIGNED: {
				BigInteger carry = BigInteger.ZERO;
				// An extra val to prevent the array mutators from changing the original answer
				// value so as to retrieve it for floating-point details
				BigInteger[] temp = { val, carry, val };
				MT.fromUS(getBitLength(), temp);
				fixUSDetails(details, temp, ProgrammerCalculator.this);
				break;
			}
			case REP_FLOATING_POINT:
			default:{
				// An extra val to prevent the array mutators from changing the original answer
				// value so as to retrieve it for floatingpoint details
				Tuple.Couple<BinaryFP, BigInteger> temp = Tuple.of(fval, val);
				fixFPDetails(details, temp, ProgrammerCalculator.this);
			}
			}
			super.run();
		}
	}

	/*
	 * Most Recent Date: 8 Oct 2022-----------------------------------------------
	 * Most recent time created: 09:03:56--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param expression
	 * @return
	 */
	@Override
	public SegmentBuilder evaluate(String expression) {
		// TODO Auto-generated method stub
		EvaluatableExpression<Params> f;
		synchronized (lexer) {
			lexer.setSource(expression);
			f = parser.parse(lexer, lexer.getSyntax(), this);
		}
		SegmentBuilder sb = new SegmentBuilder();
		f.format(sb);
		synchronized (details) {
			details.setSource(sb.toSegment());
		}
		return sb;
	}

	/*
	 * Most Recent Date: 8 Oct 2022-----------------------------------------------
	 * Most recent time created: 09:03:56--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public int getModifier() {
		return modifier;
	}

	/*
	 * Most Recent Date: 8 Oct 2022-----------------------------------------------
	 * Most recent time created: 09:03:56--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public DetailsList<PExpression.Params> getDetails() {
		return details;
	}

	/*
	 * Most Recent Date: 8 Oct 2022-----------------------------------------------
	 * Most recent time created: 09:03:56--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public KeyBoard<T, F> getRemoteControl() {
		return keyboard;
	}

	/*
	 * Most Recent Date: 8 Oct 2022-----------------------------------------------
	 * Most recent time created: 09:03:56--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public KeyAction<T, String> getModKeys() {
		return modKeys;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public int getResultType() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public String getDecimalPoint() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public String getIntSeparator() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public String getMantSeparator() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public int getIntGroupSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public int getMantGroupSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public int getRadix() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public Map<String, Couple<String, Function<Params, SegmentBuilder>>> getConstants() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public Map<String, Couple<String, Function<Params, SegmentBuilder>>> getBoundVariables() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public String[] getDivisionString() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public String[] getMultiplicationString() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public AngleUnit getTrig() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public int getBitRepresentation() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public int getEndianess() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public int getBitLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public RoundingMode getRoundingMode() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public int getShift() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int modifier;

	private final KeyBoard<T, F> keyboard;
	private final KeyAction<T, String> modKeys;
	private final ProgrammerLexer lexer;
	private final PrattParser<EvaluatableExpression<Params>, Params> parser;
	private final DetailsList<PExpression.Params> details;
	private final Map<String, Couple<String, Function<Params, SegmentBuilder>>> constants;
	private final Map<String, Couple<String, Function<Params, SegmentBuilder>>> boundVariables;

}
