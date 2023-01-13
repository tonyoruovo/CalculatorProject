/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.programmer;

import java.math.BigInteger;
import java.math.RoundingMode;

import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.value.BinaryFPPrecision;
import mathaid.calculator.base.value.BinaryFPPrecision.BinaryFP;
import mathaid.calculator.base.value.BinaryRep;
import mathaid.calculator.base.value.Ex;
import mathaid.calculator.base.value.FloatAid;
import mathaid.calculator.base.value.MT;
import mathaid.calculator.base.value.NB;
import mathaid.calculator.base.value.OC;
import mathaid.calculator.base.value.SMR;
import mathaid.calculator.base.value.TC;
import mathaid.calculator.base.value.US;

/*
 * Date: 29 Nov 2022----------------------------------------------------------- 
 * Time created: 20:42:56---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression.programmer------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: PExpression.java------------------------------------------------------ 
 * Class name: PExpression------------------------------------------------ 
 */
/**
 * A default access class created with the sole purpose of extends behaviour of
 * the {@link Name} class to all expressions within the programmer api.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public abstract class PExpression extends EvaluatableExpression<PExpression.Params> {

	public static interface Params extends EvaluatableExpression.ExpressionParams<Params> {

		public static interface ResultType extends EvaluatableExpression.ExpressionParams.ResultType {
			int NORMALISED = 1;
//			int BIT = 2;
			int REP_FLOATING_POINT = 3;
			int REP_SMR = 4;
			int REP_ONE_C = 5;
			int REP_TWO_C = 6;
			int REP_EXCESS_K = 7;
			int REP_NEGABINARY = 8;
			int REP_MATH = 9;
			int REP_UNSIGNED = 10;
			int ENDIAN_BIG = 11;
			int ENDIAN_SMALL = 12;
			int ENDIAN_PDP_11 = 13;
			int SHIFT_LOGICAL = 14;
			int SHIFT_ARITHMETIC = 15;
			int SHIFT_ROTATE = 16;
			/*
			 * rotate through carry one
			 */
			int SHIFT_ROTATE_2RU_C1 = 17;
		}

		int getBitRepresentation();

		int getEndianess();

		int getBitLength();

//		Charset getCharset();

		RoundingMode getRoundingMode();

		int getShift();

	}

	/*
	 * Date: 29 Nov 2022-----------------------------------------------------------
	 * Time created: 20:46:37---------------------------------------------------
	 */
	/**
	 * @param params
	 */
	public PExpression(Params params) {
		super(params);
	}

	Name toDecimal() {
		BigInteger[] r = { getInteger(), getCarry() };
		if(r[0] == null)
			throw new ArithmeticException("This is not an integer");
		switch (getRep()) {
		case EXCESS_K: {
			MT.fromEx(getParams().getBitLength(), r);
			return new Name(r[0], r[1], getParams());
		}
		case MATH: {
			BigInteger n = r[0];
			Params p = getParams();
			if (n.abs().bitLength() > p.getBitLength()) {
				r[1] = n.shiftRight(p.getBitLength()).testBit(0) ? BigInteger.ONE : BigInteger.ZERO;
				int sign = n.signum();
				n = FloatAid.clearMSB(n.abs(), n.abs().bitLength() - p.getBitLength())
						.multiply(BigInteger.valueOf(sign));
			}
			return new Name(n, r[1], getParams());
//			return new Name(r[0], r[1], getParams());
		}
		case NEGABINARY: {
			MT.fromNB(getParams().getBitLength(), r);
			return new Name(r[0], r[1], getParams());
		}
		case ONE_C:{
			MT.fromOC(getParams().getBitLength(), r);
			return new Name(r[0],r[1], getParams());
		}
		case SMR:{
			MT.fromSMR(getParams().getBitLength(), r);
			return new Name(r[0],r[1], getParams());
		}
		case UNSIGNED:{
			MT.fromUS(getParams().getBitLength(), r);
			return new Name(r[0],r[1], getParams());
		}
		case TWO_C:
		default:{
			MT.fromTC(getParams().getBitLength(), r);
			return new Name(r[0],r[1], getParams());
		}
		}
	}

	Name fromDecimal() {
		BigInteger[] r = { getInteger(), getCarry() };
		switch (getRep()) {
		case EXCESS_K:
			Ex.fromDecimal(getParams().getBitLength(), r);
			return new Name(r[0], r[1], getParams());
		case MATH:
			BigInteger n = r[0];
			Params p = getParams();
			if (n.abs().bitLength() > p.getBitLength()) {
				r[1] = n.shiftRight(p.getBitLength()).testBit(0) ? BigInteger.ONE : BigInteger.ZERO;
				int sign = n.signum();
				n = FloatAid.clearMSB(n.abs(), n.abs().bitLength() - p.getBitLength())
						.multiply(BigInteger.valueOf(sign));
			}
			return new Name(n, r[1], getParams());
		case NEGABINARY:
			NB.fromDecimal(getParams().getBitLength(), r);
			return new Name(r[0], r[1], getParams());
		case ONE_C:
			OC.fromDecimal(getParams().getBitLength(), r);
			return new Name(r[0], r[1], getParams());
		case SMR:
			SMR.fromDecimal(getParams().getBitLength(), r);
			return new Name(r[0], r[1], getParams());
		case UNSIGNED:
			US.fromDecimal(getParams().getBitLength(), r);
			return new Name(r[0], r[1], getParams());
		case TWO_C:
		default:
			TC.fromDecimal(getParams().getBitLength(), r);
			return new Name(r[0], r[1], getParams());
		}
	}

	public BinaryFPPrecision getPrecision() {
		switch (getParams().getBitLength()) {
		case 8:
		default:
			return FloatAid.IEEE754Bit8(getParams().getRoundingMode());
		case 16:
			return FloatAid.IEEE754Half(getParams().getRoundingMode());
		case 32:
			return FloatAid.IEEE754Single(getParams().getRoundingMode());
		case 64:
			return FloatAid.IEEE754Double(getParams().getRoundingMode());
		case 80:
			return FloatAid.IEEE754x86Extended(getParams().getRoundingMode());
		case 128:
			return FloatAid.IEEE754Quadruple(getParams().getRoundingMode());
		case 256:
			return FloatAid.IEEE754Octuple(getParams().getRoundingMode());

		}
	}

	public BinaryRep getRep() {
		switch (getParams().getBitRepresentation()) {
		case 4:
			return BinaryRep.SMR;
		case 5:
			return BinaryRep.ONE_C;
		case 7:
			return BinaryRep.EXCESS_K;
		case 8:
			return BinaryRep.NEGABINARY;
		case 9:
			return BinaryRep.MATH;
		case 10:
			return BinaryRep.UNSIGNED;
		case 6:
		default:
			return BinaryRep.TWO_C;
		}
	}

	public abstract BigInteger getCarry();

	@Override
	public abstract PExpression evaluate();

	public boolean isFloatingPoint() {
		return getFloatingPoint() != null;
	}

	public boolean isInteger() {
		return getInteger() != null;
	}

	abstract BinaryFP getFloatingPoint();

	abstract BigInteger getInteger();
}
