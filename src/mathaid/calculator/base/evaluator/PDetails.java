/*
 * Date: 11 Nov 2023 -----------------------------------------------------------
 * Time created: 09:25:27 ---------------------------------------------------
 */
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import mathaid.MomentString;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression.ExpressionParams;
import mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression.Params;
import mathaid.calculator.base.typeset.DigitPunc;
import mathaid.calculator.base.typeset.Digits;
import mathaid.calculator.base.typeset.LinkedSegment;
import mathaid.calculator.base.typeset.Segment;
import mathaid.calculator.base.util.Tuple;
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

/*
 * Date: 11 Nov 2023 -----------------------------------------------------------
 * Time created: 09:25:27 ---------------------------------------------------
 * Package: mathaid.calculator.base.evaluator ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: PDetails.java ------------------------------------------------------
 * Class name: PDetails ------------------------------------------------
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class PDetails implements Result.Details {

	private static void fixFPDetails(Map<MomentString, LinkedSegment> details, Tuple.Couple<BinaryFP, BigInteger> temp,
			Params p) {
		if (temp.get() == null) {
			BinaryFP fp = getPrecision(p).fromBitLayout(temp.get2nd());
			LinkedSegment seg = Digits.toSegment(fp, p.getRadix(), 10, false, p.getDecimalPoint().charAt(0),
					Calculator.fromParams(p, Segment.Type.VINCULUM));
			details.put(new MomentString("As decimal floating-point"), seg);
		} else if (temp.get2nd() == null) {
			BinaryFP fp = temp.get();
			LinkedSegment seg = Digits.toSegment(fp, 10, 10, true, p.getDecimalPoint().charAt(0), fromParams(p));
			details.put(new MomentString("Expression"), seg);

			seg = Digits.toRadixedSegment(BigInteger.valueOf(fp.signum() < 0 ? 1 : 0), p.getRadix(), fromParams(p));
			details.put(new MomentString("Sign (unsigned)"), seg);

			seg = Digits.toRadixedSegment(BigInteger.valueOf(fp.getExponent()), 2, fromParams(p));
			details.put(new MomentString("Exponent"), seg);

			seg = Digits.toRadixedSegment(fp.getSignificand(), 2, fromParams(p));
			details.put(new MomentString("Significand"), seg);

			seg = Digits.toRadixedSegment(fp.toBigInteger(), 2, fromParams(p));
			details.put(new MomentString("Bit layout (2's complement)"), seg);

			seg = Digits.toSegment(fp, 2, 10, true, p.getDecimalPoint().charAt(0), fromParams(p));
			details.put(new MomentString("normalised (Binary Significand)"), seg);
			seg = Digits.toSegment(fp, 8, 10, true, p.getDecimalPoint().charAt(0), fromParams(p));
			details.put(new MomentString("normalised (Octal Significand)"), seg);
			seg = Digits.toSegment(fp, 10, 10, true, p.getDecimalPoint().charAt(0), fromParams(p));
			details.put(new MomentString("normalised"), seg);
			seg = Digits.toSegment(fp, 16, 10, true, p.getDecimalPoint().charAt(0), fromParams(p));
			details.put(new MomentString("normalised (Hexadecimal Significand)"), seg);

			seg = Digits.toSegment(fp, 2, 10, false, p.getDecimalPoint().charAt(0), fromParams(p));
			details.put(new MomentString("Binary Significand"), seg);
			seg = Digits.toSegment(fp, 8, 10, false, p.getDecimalPoint().charAt(0), fromParams(p));
			details.put(new MomentString("Octal Significand"), seg);
			seg = Digits.toSegment(fp, 16, 10, false, p.getDecimalPoint().charAt(0), fromParams(p));
			details.put(new MomentString("Hexadecimal Significand"), seg);
		}
	}

	private static void fixUSDetails(Map<MomentString, LinkedSegment> details, BigInteger[] temp, Params p) {
		BigInteger[] r = { temp[0], temp[1] };
		// SMR
		SMR.fromDecimal(p.getBitLength(), r);
		LinkedSegment seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("Signed magnitude (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("Signed magnitude (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("Signed magnitude (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("Signed magnitude (Hexadecimal)"), seg);
		// 1's
		r[0] = temp[0];
		r[1] = temp[1];
		OC.fromDecimal(p.getBitLength(), r);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("1's complement (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("1's complement (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("1's complement (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("1's complement (Hexadecimal)"), seg);
		// 2's
		r[0] = temp[0];
		r[1] = temp[1];
		TC.fromDecimal(p.getBitLength(), r);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("2's complement (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("2's complement (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("2's complement (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("2's complement (Hexadecimal)"), seg);
		// nega
		r[0] = temp[0];
		r[1] = temp[1];
		NB.fromDecimal(p.getBitLength(), r);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("Negabinary (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("Negabinary (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("Negabinary (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("Negabinary (Hexadecimal)"), seg);
		// math
		seg = Digits.toRadixedSegment(temp[0], 2, fromParams(p));
		details.put(new MomentString("Mathematical (Binary)"), seg);
		seg = Digits.toRadixedSegment(temp[0], 8, fromParams(p));
		details.put(new MomentString("Mathematical (Octal)"), seg);
		seg = Digits.toRadixedSegment(temp[0], 10, fromParams(p));
		details.put(new MomentString("Mathematical (Decimal)"), seg);
		seg = Digits.toRadixedSegment(temp[0], 16, fromParams(p));
		details.put(new MomentString("Mathematical (Hexadecimal)"), seg);
		// excess-n
		r[0] = temp[0];
		r[1] = temp[1];
		Ex.fromDecimal(p.getBitLength(), r);
		String n = FloatAid.getTrailingZeros(p.getBitLength() - 1).toString(10);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString(String.format("Excess-%s (Binary)", n)), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString(String.format("Excess-%s (Octal)", n)), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString(String.format("Excess-%s (Decimal)", n)), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString(String.format("Excess-%s (Hexadecimal)", n)), seg);
		// floating point
		if (p.getBitLength() >= 8) {
			BinaryFP fp = getPrecision(p).fromBitLayout(temp[2]);
			seg = Digits.toSegment(fp, 10, 10, false, p.getDecimalPoint().charAt(0), fromParams(p));
			details.put(new MomentString("As decimal floating-point (Unsigned)"), seg);
		}
	}

	private static void fixSMRDetails(Map<MomentString, LinkedSegment> details, BigInteger[] temp, Params p) {
		BigInteger[] r = { temp[0], temp[1] };
		// nega
		r[0] = temp[0];
		r[1] = temp[1];
		NB.fromDecimal(p.getBitLength(), r);
		LinkedSegment seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("Negabinary (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("Negabinary (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("Negabinary (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("Negabinary (Hexadecimal)"), seg);
		// 1's
		r[0] = temp[0];
		r[1] = temp[1];
		OC.fromDecimal(p.getBitLength(), r);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("1's complement (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("1's complement (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("1's complement (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("1's complement (Hexadecimal)"), seg);
		// 2's
		r[0] = temp[0];
		r[1] = temp[1];
		TC.fromDecimal(p.getBitLength(), r);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("2's complement (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("2's complement (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("2's complement (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("2's complement (Hexadecimal)"), seg);
		// excess-n
		r[0] = temp[0];
		r[1] = temp[1];
		Ex.fromDecimal(p.getBitLength(), r);
		String n = FloatAid.getTrailingZeros(p.getBitLength() - 1).toString(10);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString(String.format("Excess-%s (Binary)", n)), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString(String.format("Excess-%s (Octal)", n)), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString(String.format("Excess-%s (Decimal)", n)), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString(String.format("Excess-%s (Hexadecimal)", n)), seg);
		// math
		seg = Digits.toRadixedSegment(temp[0], 2, fromParams(p));
		details.put(new MomentString("Mathematical (Binary)"), seg);
		seg = Digits.toRadixedSegment(temp[0], 8, fromParams(p));
		details.put(new MomentString("Mathematical (Octal)"), seg);
		seg = Digits.toRadixedSegment(temp[0], 10, fromParams(p));
		details.put(new MomentString("Mathematical (Decimal)"), seg);
		seg = Digits.toRadixedSegment(temp[0], 16, fromParams(p));
		details.put(new MomentString("Mathematical (Hexadecimal)"), seg);
		// unsigned
		r[0] = temp[0];
		r[1] = temp[1];
		US.fromDecimal(p.getBitLength(), r);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("Unsigned (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("Unsigned (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("Unsigned (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("Unsigned (Hexadecimal)"), seg);
		// floating point
		if (p.getBitLength() >= 8) {
			BinaryFP fp = getPrecision(p).fromBitLayout(temp[2]);
			seg = Digits.toSegment(fp, 10, 10, false, p.getDecimalPoint().charAt(0), fromParams(p));
			details.put(new MomentString("As decimal floating-point (Signed magnitude)"), seg);
		}
	}

	private static void fixTCDetails(Map<MomentString, LinkedSegment> details, BigInteger[] temp, Params p) {
		BigInteger[] r = { temp[0], temp[1] };
		// SMR
		SMR.fromDecimal(p.getBitLength(), r);
		LinkedSegment seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("Signed magnitude (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("Signed magnitude (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("Signed magnitude (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("Signed magnitude (Hexadecimal)"), seg);
		// 1's
		r[0] = temp[0];
		r[1] = temp[1];
		OC.fromDecimal(p.getBitLength(), r);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("1's complement (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("1's complement (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("1's complement (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("1's complement (Hexadecimal)"), seg);
		// excess-n
		r[0] = temp[0];
		r[1] = temp[1];
		Ex.fromDecimal(p.getBitLength(), r);
		String n = FloatAid.getTrailingZeros(p.getBitLength() - 1).toString(10);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString(String.format("Excess-%s (Binary)", n)), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString(String.format("Excess-%s (Octal)", n)), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString(String.format("Excess-%s (Decimal)", n)), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString(String.format("Excess-%s (Hexadecimal)", n)), seg);
		// nega
		r[0] = temp[0];
		r[1] = temp[1];
		NB.fromDecimal(p.getBitLength(), r);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("Negabinary (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("Negabinary (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("Negabinary (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("Negabinary (Hexadecimal)"), seg);
		// math
		seg = Digits.toRadixedSegment(temp[0], 2, fromParams(p));
		details.put(new MomentString("Mathematical (Binary)"), seg);
		seg = Digits.toRadixedSegment(temp[0], 8, fromParams(p));
		details.put(new MomentString("Mathematical (Octal)"), seg);
		seg = Digits.toRadixedSegment(temp[0], 10, fromParams(p));
		details.put(new MomentString("Mathematical (Decimal)"), seg);
		seg = Digits.toRadixedSegment(temp[0], 16, fromParams(p));
		details.put(new MomentString("Mathematical (Hexadecimal)"), seg);
		// unsigned
		r[0] = temp[0];
		r[1] = temp[1];
		US.fromDecimal(p.getBitLength(), r);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("Unsigned (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("Unsigned (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("Unsigned (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("Unsigned (Hexadecimal)"), seg);
		// floating point
		if (p.getBitLength() >= 8) {
			BinaryFP fp = getPrecision(p).fromBitLayout(temp[2]);
			seg = Digits.toSegment(fp, 10, 10, false, p.getDecimalPoint().charAt(0), fromParams(p));
			details.put(new MomentString("As decimal floating-point (2's complement)"), seg);
		}
	}

	private static void fixOCDetails(Map<MomentString, LinkedSegment> details, BigInteger[] temp, Params p) {
		BigInteger[] r = { temp[0], temp[1] };
		// SMR
		SMR.fromDecimal(p.getBitLength(), r);
		LinkedSegment seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("Signed magnitude (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("Signed magnitude (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("Signed magnitude (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("Signed magnitude (Hexadecimal)"), seg);
		// nega
		r[0] = temp[0];
		r[1] = temp[1];
		NB.fromDecimal(p.getBitLength(), r);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("Negabinary (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("Negabinary (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("Negabinary (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("Negabinary (Hexadecimal)"), seg);
		// 2's
		r[0] = temp[0];
		r[1] = temp[1];
		TC.fromDecimal(p.getBitLength(), r);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("2's complement (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("2's complement (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("2's complement (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("2's complement (Hexadecimal)"), seg);
		// excess-n
		r[0] = temp[0];
		r[1] = temp[1];
		Ex.fromDecimal(p.getBitLength(), r);
		String n = FloatAid.getTrailingZeros(p.getBitLength() - 1).toString(10);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString(String.format("Excess-%s (Binary)", n)), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString(String.format("Excess-%s (Octal)", n)), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString(String.format("Excess-%s (Decimal)", n)), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString(String.format("Excess-%s (Hexadecimal)", n)), seg);
		// math
		seg = Digits.toRadixedSegment(temp[0], 2, fromParams(p));
		details.put(new MomentString("Mathematical (Binary)"), seg);
		seg = Digits.toRadixedSegment(temp[0], 8, fromParams(p));
		details.put(new MomentString("Mathematical (Octal)"), seg);
		seg = Digits.toRadixedSegment(temp[0], 10, fromParams(p));
		details.put(new MomentString("Mathematical (Decimal)"), seg);
		seg = Digits.toRadixedSegment(temp[0], 16, fromParams(p));
		details.put(new MomentString("Mathematical (Hexadecimal)"), seg);
		// unsigned
		r[0] = temp[0];
		r[1] = temp[1];
		US.fromDecimal(p.getBitLength(), r);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("Unsigned (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("Unsigned (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("Unsigned (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("Unsigned (Hexadecimal)"), seg);
		// floating point
		if (p.getBitLength() >= 8) {
			BinaryFP fp = getPrecision(p).fromBitLayout(temp[2]);
			seg = Digits.toSegment(fp, 10, 10, false, p.getDecimalPoint().charAt(0), fromParams(p));
			details.put(new MomentString("As decimal floating-point (1's complement)"), seg);
		}
	}

	private static void fixNBDetails(Map<MomentString, LinkedSegment> details, BigInteger[] temp, Params p) {
		BigInteger[] r = { temp[0], temp[1] };
		// SMR
		SMR.fromDecimal(p.getBitLength(), r);
		LinkedSegment seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("Signed magnitude (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("Signed magnitude (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("Signed magnitude (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("Signed magnitude (Hexadecimal)"), seg);
		// 1's
		r[0] = temp[0];
		r[1] = temp[1];
		OC.fromDecimal(p.getBitLength(), r);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("1's complement (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("1's complement (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("1's complement (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("1's complement (Hexadecimal)"), seg);
		// 2's
		r[0] = temp[0];
		r[1] = temp[1];
		TC.fromDecimal(p.getBitLength(), r);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("2's complement (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("2's complement (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("2's complement (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("2's complement (Hexadecimal)"), seg);
		// excess-n
		r[0] = temp[0];
		r[1] = temp[1];
		Ex.fromDecimal(p.getBitLength(), r);
		String n = FloatAid.getTrailingZeros(p.getBitLength() - 1).toString(10);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString(String.format("Excess-%s (Binary)", n)), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString(String.format("Excess-%s (Octal)", n)), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString(String.format("Excess-%s (Decimal)", n)), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString(String.format("Excess-%s (Hexadecimal)", n)), seg);
		// math
		seg = Digits.toRadixedSegment(temp[0], 2, fromParams(p));
		details.put(new MomentString("Mathematical (Binary)"), seg);
		seg = Digits.toRadixedSegment(temp[0], 8, fromParams(p));
		details.put(new MomentString("Mathematical (Octal)"), seg);
		seg = Digits.toRadixedSegment(temp[0], 10, fromParams(p));
		details.put(new MomentString("Mathematical (Decimal)"), seg);
		seg = Digits.toRadixedSegment(temp[0], 16, fromParams(p));
		details.put(new MomentString("Mathematical (Hexadecimal)"), seg);
		// unsigned
		r[0] = temp[0];
		r[1] = temp[1];
		US.fromDecimal(p.getBitLength(), r);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("Unsigned (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("Unsigned (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("Unsigned (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("Unsigned (Hexadecimal)"), seg);
		// floating point
		if (p.getBitLength() >= 8) {
			BinaryFP fp = getPrecision(p).fromBitLayout(temp[2]);
			seg = Digits.toSegment(fp, 10, 10, false, p.getDecimalPoint().charAt(0), fromParams(p));
			details.put(new MomentString("As decimal floating-point (Negabinary)"), seg);
		}
	}

	private static void fixMTDetails(Map<MomentString, LinkedSegment> details, BigInteger[] temp, Params p) {
		BigInteger[] r = { temp[0], temp[1] };
		// SMR
		SMR.fromDecimal(p.getBitLength(), r);
		LinkedSegment seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("Signed magnitude (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("Signed magnitude (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("Signed magnitude (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("Signed magnitude (Hexadecimal)"), seg);
		// 1's
		r[0] = temp[0];
		r[1] = temp[1];
		OC.fromDecimal(p.getBitLength(), r);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("1's complement (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("1's complement (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("1's complement (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("1's complement (Hexadecimal)"), seg);
		// 2's
		r[0] = temp[0];
		r[1] = temp[1];
		TC.fromDecimal(p.getBitLength(), r);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("2's complement (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("2's complement (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("2's complement (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("2's complement (Hexadecimal)"), seg);
		// nega
		r[0] = temp[0];
		r[1] = temp[1];
		NB.fromDecimal(p.getBitLength(), r);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("Negabinary (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("Negabinary (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("Negabinary (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("Negabinary (Hexadecimal)"), seg);
		// excess-n
		r[0] = temp[0];
		r[1] = temp[1];
		Ex.fromDecimal(p.getBitLength(), r);
		String n = FloatAid.getTrailingZeros(p.getBitLength() - 1).toString(10);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString(String.format("Excess-%s (Binary)", n)), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString(String.format("Excess-%s (Octal)", n)), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString(String.format("Excess-%s (Decimal)", n)), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString(String.format("Excess-%s (Hexadecimal)", n)), seg);
		// unsigned
		r[0] = temp[0];
		r[1] = temp[1];
		US.fromDecimal(p.getBitLength(), r);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("Unsigned (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("Unsigned (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("Unsigned (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("Unsigned (Hexadecimal)"), seg);
		// floating point
		if (p.getBitLength() >= 8) {
			BinaryFP fp = getPrecision(p).fromBitLayout(temp[2]);
			seg = Digits.toSegment(fp, 10, 10, false, p.getDecimalPoint().charAt(0), fromParams(p));
			details.put(new MomentString("As decimal floating-point (Mathematical)"), seg);
		}
	}

	private static void fixExDetails(Map<MomentString, LinkedSegment> details, BigInteger[] temp, Params p) {
		BigInteger[] r = { temp[0], temp[1] };
		// SMR
		SMR.fromDecimal(p.getBitLength(), r);
		LinkedSegment seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("Signed magnitude (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("Signed magnitude (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("Signed magnitude (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("Signed magnitude (Hexadecimal)"), seg);
		// 1's
		r[0] = temp[0];
		r[1] = temp[1];
		OC.fromDecimal(p.getBitLength(), r);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("1's complement (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("1's complement (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("1's complement (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("1's complement (Hexadecimal)"), seg);
		// 2's
		r[0] = temp[0];
		r[1] = temp[1];
		TC.fromDecimal(p.getBitLength(), r);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("2's complement (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("2's complement (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("2's complement (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("2's complement (Hexadecimal)"), seg);
		// nega
		r[0] = temp[0];
		r[1] = temp[1];
		NB.fromDecimal(p.getBitLength(), r);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("Negabinary (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("Negabinary (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("Negabinary (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("Negabinary (Hexadecimal)"), seg);
		// math
		seg = Digits.toRadixedSegment(temp[0], 2, fromParams(p));
		details.put(new MomentString("Mathematical (Binary)"), seg);
		seg = Digits.toRadixedSegment(temp[0], 8, fromParams(p));
		details.put(new MomentString("Mathematical (Octal)"), seg);
		seg = Digits.toRadixedSegment(temp[0], 10, fromParams(p));
		details.put(new MomentString("Mathematical (Decimal)"), seg);
		seg = Digits.toRadixedSegment(temp[0], 16, fromParams(p));
		details.put(new MomentString("Mathematical (Hexadecimal)"), seg);
		// unsigned
		r[0] = temp[0];
		r[1] = temp[1];
		US.fromDecimal(p.getBitLength(), r);
		seg = Digits.toRadixedSegment(r[0], 2, fromParams(p));
		details.put(new MomentString("Unsigned (Binary)"), seg);
		seg = Digits.toRadixedSegment(r[0], 8, fromParams(p));
		details.put(new MomentString("Unsigned (Octal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 10, fromParams(p));
		details.put(new MomentString("Unsigned (Decimal)"), seg);
		seg = Digits.toRadixedSegment(r[0], 16, fromParams(p));
		details.put(new MomentString("Unsigned (Hexadecimal)"), seg);
		// floating point
		if (p.getBitLength() >= 8) {
			BinaryFP fp = getPrecision(p).fromBitLayout(temp[2]);
			String n = FloatAid.getTrailingZeros(p.getBitLength() - 1).toString(10);
			seg = Digits.toSegment(fp, 10, 10, false, p.getDecimalPoint().charAt(0), fromParams(p));
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
			LinkedSegment seg = Digits.toRadixedSegment(val, p.getRadix(), fromParams(p));
			details.put(new MomentString("Expression (Big endian)"), seg);
			seg = Digits.toRadixedSegment(BitLength.toSmallEndian(val), p.getRadix(), fromParams(p));
			details.put(new MomentString("Expression (Little endian)"), seg);
			seg = Digits.toRadixedSegment(BitLength.toPDP11Endian(val), p.getRadix(), fromParams(p));
			details.put(new MomentString("Expression (PDP-11 endian)"), seg);

		} else if (p.getEndianess() == ENDIAN_SMALL) {
			LinkedSegment seg = Digits.toRadixedSegment(BitLength.toSmallEndian(val), p.getRadix(), fromParams(p));
			details.put(new MomentString("Expression (Big endian)"), seg);
			seg = Digits.toRadixedSegment(val, p.getRadix(), fromParams(p));
			details.put(new MomentString("Expression (Little endian)"), seg);
			seg = Digits.toRadixedSegment(BitLength.toPDP11Endian(BitLength.toSmallEndian(val)), p.getRadix(),
					fromParams(p));
			details.put(new MomentString("Expression (PDP-11 endian)"), seg);
		} else if (p.getEndianess() == ENDIAN_PDP_11) {
			LinkedSegment seg = Digits.toRadixedSegment(BitLength.toPDP11Endian(val), p.getRadix(), fromParams(p));
			details.put(new MomentString("Expression (Big endian)"), seg);
			seg = Digits.toRadixedSegment(BitLength.toSmallEndian(BitLength.toPDP11Endian(val)), p.getRadix(),
					fromParams(p));
			details.put(new MomentString("Expression (Little endian)"), seg);
			seg = Digits.toRadixedSegment(val, p.getRadix(), fromParams(p));
			details.put(new MomentString("Expression (PDP-11 endian)"), seg);
		}

	}

	private static DigitPunc fromParams(Params p) {
		return Calculator.fromParams(p, Segment.Type.VINCULUM);
	}

	public NavigableMap<MomentString, LinkedSegment> getDetails(ExpressionParams<?> p, LinkedSegment src) {
		details.clear();
		computeDetails((Params) p, src);
		return java.util.Collections.unmodifiableNavigableMap(details);
	}
	
	private void computeDetails(final Params params, LinkedSegment src) {
		StringBuilder sb = new StringBuilder();
		src.toString(sb, null, new ArrayList<>(Arrays.asList(-1)));
		String s = sb.toString();
		BigInteger val = null;
		BinaryFP fval = null;
		try {
			val = new BigInteger(s, params.getRadix());
		} catch (NumberFormatException e) {
			fval = getPrecision(params).createFP(s, params.getRadix());
		}
		if (params.getBitRepresentation() == REP_MATH || params.getBitRepresentation() == REP_FLOATING_POINT)
			details.put(new MomentString("Expression"), src);
		else if (val != null)
			fixEndianDetails(details, val, params);
		switch (params.getBitRepresentation()) {
		case REP_EXCESS_K: {
			BigInteger carry = BigInteger.ZERO;
			// An extra val to prevent the array mutators from changing the original answer
			// value so as to retrieve it for floating-point details
			BigInteger[] temp = { val, carry, val };
			MT.fromEx(params.getBitLength(), temp);
			fixExDetails(details, temp, params);
			break;
		}
		case REP_MATH: {
			BigInteger carry = BigInteger.ZERO;
			// An extra val to prevent the array mutators from changing the original answer
			// value so as to retrieve it for floating-point details
			BigInteger[] temp = { val, carry, val };
			fixMTDetails(details, temp, params);
			break;
		}
		case REP_NEGABINARY: {
			BigInteger carry = BigInteger.ZERO;
			// An extra val to prevent the array mutators from changing the original answer
			// value so as to retrieve it for floating-point details
			BigInteger[] temp = { val, carry, val };
			MT.fromNB(params.getBitLength(), temp);
			fixNBDetails(details, temp, params);
			break;
		}
		case REP_ONE_C: {
			BigInteger carry = BigInteger.ZERO;
			// An extra val to prevent the array mutators from changing the original answer
			// value so as to retrieve it for floating-point details
			BigInteger[] temp = { val, carry, val };
			MT.fromOC(params.getBitLength(), temp);
			fixOCDetails(details, temp, params);
			break;
		}
		case REP_TWO_C: {
			BigInteger carry = BigInteger.ZERO;
			// An extra val to prevent the array mutators from changing the original answer
			// value so as to retrieve it for floating-point details
			BigInteger[] temp = { val, carry, val };
			MT.fromTC(params.getBitLength(), temp);
			fixTCDetails(details, temp, params);
			break;
		}
		case REP_SMR: {
			BigInteger carry = BigInteger.ZERO;
			// An extra val to prevent the array mutators from changing the original answer
			// value so as to retrieve it for floating-point details
			BigInteger[] temp = { val, carry, val };
			MT.fromSMR(params.getBitLength(), temp);
			fixSMRDetails(details, temp, params);
			break;
		}
		case REP_UNSIGNED: {
			BigInteger carry = BigInteger.ZERO;
			// An extra val to prevent the array mutators from changing the original answer
			// value so as to retrieve it for floating-point details
			BigInteger[] temp = { val, carry, val };
			MT.fromUS(params.getBitLength(), temp);
			fixUSDetails(details, temp, params);
			break;
		}
		case REP_FLOATING_POINT:
		default: {
			// An extra val to prevent the array mutators from changing the original answer
			// value so as to retrieve it for floating-point details
			Tuple.Couple<BinaryFP, BigInteger> temp = Tuple.of(fval, val);
			fixFPDetails(details, temp, params);
		}
		}
//		super.run();
	}

	final NavigableMap<MomentString, LinkedSegment> details = new TreeMap<>((x, y) -> x.compareTo(y));
//	protected LinkedSegment src;
}
