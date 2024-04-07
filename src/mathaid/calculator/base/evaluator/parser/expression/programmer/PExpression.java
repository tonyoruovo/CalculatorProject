/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.programmer;

import java.math.BigInteger;
import java.math.RoundingMode;

import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression.Params.ResultType;
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
 * A default access class created with the sole purpose of extending the behaviour of the {@link EvaluatableExpression} class to
 * all expressions within the programmer api.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public abstract class PExpression extends EvaluatableExpression<PExpression.Params> {

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:33:27 ---------------------------------------------------
	 * Package: mathaid.calculator.base.evaluator.parser.expression.programmer ------------------------------------------------
	 * Project: CalculatorProject ------------------------------------------------
	 * File: PExpression.java ------------------------------------------------------
	 * Class name: Params ------------------------------------------------
	 */
	/**
	 * The {@code ExpressionParams} for programmer expressions, containing definitions for decimals, options for mathematical
	 * evaluations, for formatting to {@code SegmentBuilder} and general options to be passed to programmer expressions.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 */
	public static interface Params extends EvaluatableExpression.ExpressionParams<Params> {

		/*
		 * Date: 30 Nov 2023 -----------------------------------------------------------
		 * Time created: 12:34:46 ---------------------------------------------------
		 * Package: mathaid.calculator.base.evaluator.parser.expression.programmer ------------------------------------------------
		 * Project: CalculatorProject ------------------------------------------------
		 * File: PExpression.java ------------------------------------------------------
		 * Class name: ResultType ------------------------------------------------
		 */
		/**
		 * The interface that contains the constants used for result types within the programmer calculator.
		 * 
		 * @author Oruovo Anthony Etineakpopha
		 * @email tonyoruovo@gmail.com
		 */
		interface ResultType extends EvaluatableExpression.ExpressionParams.ResultType {
			/**
			 * The {@code int} constant that specifies that all floating point result output formats must be in normalised form.
			 * 
			 * @see mathaid.calculator.base.value.Precision
			 * @see mathaid.calculator.base.value.Precision#checkNormalised(String)
			 * @see mathaid.calculator.base.value.Precision#normalise(String, mathaid.calculator.base.value.Radix)
			 * @see mathaid.calculator.base.value.BinaryFPPrecision.BinaryFP
			 * @see mathaid.calculator.base.value.BinaryFPPrecision.BinaryFP#toNormalisedString(int)
			 */
			int NORMALISED = 1;
//			int BIT = 2;
			/**
			 * The {@code int} constant that specifies that all result output formats must be in floating point form i.e all integer must be
			 * converted to the floating point interpretation of the bits within; all fractions must be converted to their floating point
			 * equivalent.
			 * 
			 * @see mathaid.calculator.base.value.BinaryFPPrecision#fromBitLayout(BigInteger)
			 * @see mathaid.calculator.base.value.BinaryFPPrecision.BinaryFP
			 * @see mathaid.calculator.base.value.FloatAid#fromBits(long, long)
			 */
			int REP_FLOATING_POINT = 3;
			/**
			 * The {@code int} constant that specifies that all result output formats must be in signed and magnitude form. All fractions
			 * must be converted to their floating point equivalent and then to signed and magnitude integer form.
			 */
			int REP_SMR = 4;
			/**
			 * The {@code int} constant that specifies that all result output formats must be in 1's complement form. All fractions must be
			 * converted to their floating point equivalent and then to 1's complement integer form.
			 */
			int REP_ONE_C = 5;
			/**
			 * The {@code int} constant that specifies that all result output formats must be in 2's complement form. All fractions must be
			 * converted to their floating point equivalent and then to 2's complement integer form.
			 */
			int REP_TWO_C = 6;
			/**
			 * The {@code int} constant that specifies that all result output formats must be in
			 * Excess-<span style="font-style:italic">k</span> form. All fractions must be converted to their floating point equivalent and
			 * then to Excess-<span style="font-style:italic">k</span> integer form.
			 */
			int REP_EXCESS_K = 7;
			/**
			 * The {@code int} constant that specifies that all result output formats must be in negabinary form. All fractions must be
			 * converted to their floating point equivalent and then to negabinary integer form.
			 */
			int REP_NEGABINARY = 8;
			/**
			 * The {@code int} constant that specifies that all result output formats must be in arithmetic integer form. All fractions must
			 * be converted to their floating point equivalent and then to arithmetic integer integer form.
			 */
			int REP_MATH = 9;
			/**
			 * The {@code int} constant that specifies that all result output formats must be in unsigned form. All fractions must be
			 * converted to their floating point equivalent and then to unsigned integer form.
			 */
			int REP_UNSIGNED = 10;
			/**
			 * The {@code int} constant that specifies that all operations and result output formats must have their bits in big-endian
			 * form.
			 */
			int ENDIAN_BIG = 11;
			/**
			 * The {@code int} constant that specifies that all operations and result output formats must have their bits in little-endian
			 * form.
			 */
			int ENDIAN_SMALL = 12;
			/**
			 * The {@code int} constant that specifies that all operations and result output formats must have their bits in PDP 11 form.
			 * 
			 * @see mathaid.calculator.base.value.BitLength#toPDP11Endian(BigInteger).
			 */
			int ENDIAN_PDP_11 = 13;
			/**
			 * The {@code int} constant that specifies that all right shift operations must be logical (unsigned, like java's {@code >>>}).
			 */
			int SHIFT_LOGICAL = 14;
			/**
			 * The {@code int} constant that specifies that all right shift operations must be arithmetic (unsigned, like java's
			 * {@code >>}).
			 */
			int SHIFT_ARITHMETIC = 15;
			/**
			 * The {@code int} constant that specifies that all shift operations must be circular.
			 */
			int SHIFT_ROTATE = 16;
			/**
			 * The {@code int} constant that specifies that all circular shift operations must be the "rotate through carry one" type.
			 */
			/*
			 * rotate through carry one
			 */
			int SHIFT_ROTATE_2RU_C1 = 17;
		}

		/*
		 * Date: 30 Nov 2023 -----------------------------------------------------------
		 * Time created: 13:11:24 ---------------------------------------------------
		 */
		/**
		 * Gets the bit representation used in this calculator i.e how bits are supposed to interpreted thin a result.
		 * 
		 * @return one of the following values:
		 *         <ul>
		 *         <li>{@link ResultType#REP_UNSIGNED}</li>
		 *         <li>{@link ResultType#REP_EXCESS_K}</li>
		 *         <li>{@link ResultType#REP_FLOATING_POINT}</li>
		 *         <li>{@link ResultType#REP_TWO_C}</li>
		 *         <li>{@link ResultType#REP_SMR}</li>
		 *         <li>{@link ResultType#REP_ONE_C}</li>
		 *         <li>{@link ResultType#REP_MATH}</li>
		 *         <li>{@link ResultType#REP_NEGABINARY}</li>
		 *         </ul>
		 */
		int getBitRepresentation();

		/*
		 * Date: 30 Nov 2023 -----------------------------------------------------------
		 * Time created: 13:16:33 ---------------------------------------------------
		 */
		/**
		 * Gets the current endianess of used in this evaluator for representing bit layouts.
		 * 
		 * @return one of the following values:
		 *         <ul>
		 *         <li>{@link ResultType#ENDIAN_BIG}</li>
		 *         <li>{@link ResultType#ENDIAN_SMALL}</li>
		 *         <li>{@link ResultType#ENDIAN_PDP_11}</li>
		 *         </ul>
		 */
		int getEndianess();

		/*
		 * Date: 30 Nov 2023 -----------------------------------------------------------
		 * Time created: 13:18:44 ---------------------------------------------------
		 */
		/**
		 * Gets the number of bits this evaluator is using to represent values.
		 * 
		 * @return the bit-length of this calculator.
		 */
		int getBitLength();

//		Charset getCharset();

		/*
		 * Date: 30 Nov 2023 -----------------------------------------------------------
		 * Time created: 13:19:36 ---------------------------------------------------
		 */
		/**
		 * Gets the current {@code RoundingMode} (for floating operations) used by this evaluator.
		 * 
		 * @return the current {@code RoundingMode}.
		 * @see mathaid.calculator.base.value.BinaryFPPrecision.BinaryFP#round()
		 * @see mathaid.calculator.base.value.BinaryFPPrecision#getRoundingMode()
		 * @see mathaid.calculator.base.value.BinaryFPPrecision#setRoundingMode(RoundingMode)
		 */
		RoundingMode getRoundingMode();

		/*
		 * Date: 30 Nov 2023 -----------------------------------------------------------
		 * Time created: 13:23:11 ---------------------------------------------------
		 */
		/**
		 * Gets the current state of the 'shift' (2nd function) value within this evaluator.
		 * 
		 * @return the shift
		 */
		int getShift();

	}

	/*
	 * Date: 7 Apr 2024 -----------------------------------------------------------
	 * Time created: 05:49:21 ---------------------------------------------------
	 */
	/**
	 * Converts a {@code BigInteger} (see {@link TC#fromDecimal(int, BigInteger[])} for details on the array form) between the
	 * various supported bit representations;
	 * @param fromRep the bit representation from which the conversion is to be made. It may be any one of the following values:
	 * <ul>
	 * 	<li>{@link ResultType#REP_EXCESS_K}</li>
	 * 	<li>{@link ResultType#REP_FLOATING_POINT}</li>
	 * 	<li>{@link ResultType#REP_MATH}</li>
	 * 	<li>{@link ResultType#REP_NEGABINARY}</li>
	 * 	<li>{@link ResultType#REP_ONE_C}</li>
	 * 	<li>{@link ResultType#REP_SMR}</li>
	 * 	<li>{@link ResultType#REP_TWO_C}</li>
	 * 	<li>{@link ResultType#REP_UNSIGNED}</li>
	 * </ul>
	 * @param val the value to be converted. See {@link TC#fromDecimal(int, BigInteger[])} for details on the format.
	 * @param bound the bitlength of the given value. It is a dyadic value to which calculation will limit the bit-length.
	 * See {@link TC#fromDecimal(int, BigInteger[])} for details on the format.
	 * @param toRep any of the valid values stated in {@code fromRep}
	 * @return {@code val} converted to the same bit representation specified by toRep
	 */
	protected static BigInteger[] convert(int fromRep, BigInteger[] val, int bound, int toRep) {
		switch(fromRep) {
		case ResultType.REP_TWO_C:{
			MT.fromTC(bound, val);
			break;
		}
		case ResultType.REP_SMR:{
			MT.fromSMR(bound, val);
			break;
		}
		case ResultType.REP_UNSIGNED:{
			MT.fromUS(bound, val);
			break;
		}
		case ResultType.REP_ONE_C:{
			MT.fromOC(bound, val);
			break;
		}
		case ResultType.REP_EXCESS_K:{
			MT.fromEx(bound, val);
			break;
		}
		case ResultType.REP_NEGABINARY:{
			MT.fromNB(bound, val);
			break;
		}
		default:
		case ResultType.REP_MATH:{
			break;
		}
		}
		switch(toRep) {
		case ResultType.REP_TWO_C:{
			TC.fromDecimal(bound, val);
			break;
		}
		case ResultType.REP_SMR:{
			SMR.fromDecimal(bound, val);
			break;
		}
		case ResultType.REP_UNSIGNED:{
			US.fromDecimal(bound, val);
			break;
		}
		case ResultType.REP_ONE_C:{
			OC.fromDecimal(bound, val);
			break;
		}
		case ResultType.REP_EXCESS_K:{
			Ex.fromDecimal(bound, val);
			break;
		}
		case ResultType.REP_NEGABINARY:{
			NB.fromDecimal(bound, val);
			break;
		}
		default:
		case ResultType.REP_MATH:{
			break;
		}
		}
		return val;
	}
	
	/*
	 * Date: 29 Nov 2022-----------------------------------------------------------
	 * Time created: 20:46:37---------------------------------------------------
	 */
	/**
	 * Public constructor for sub-classes of {@code PExpression}
	 * 
	 * @param params the {@code Params} representing options for the evaluation and format within this expression.
	 */
	public PExpression(Params params) {
		super(params);
	}

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 13:27:19 ---------------------------------------------------
	 */
	/**
	 * Converts {@code this} to a {@code Name} expression in arithmetic numbers.
	 * 
	 * @return the arithmetic non-bit format of integers.
	 */
	Name toDecimal() {
		BigInteger[] r = { getInteger(), getCarry() };
		if (r[0] == null)
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
		case ONE_C: {
			MT.fromOC(getParams().getBitLength(), r);
			return new Name(r[0], r[1], getParams());
		}
		case SMR: {
			MT.fromSMR(getParams().getBitLength(), r);
			return new Name(r[0], r[1], getParams());
		}
		case UNSIGNED: {
			MT.fromUS(getParams().getBitLength(), r);
			return new Name(r[0], r[1], getParams());
		}
		case TWO_C:
		default: {
			MT.fromTC(getParams().getBitLength(), r);
			return new Name(r[0], r[1], getParams());
		}
		}
	}

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 13:33:30 ---------------------------------------------------
	 */
	/**
	 * Converts {@code this} from an arithmetic number to a given {@linkplain Params#getBitRepresentation() bit representation} as a {@code Name} expression.
	 * 
	 * @return the non-arithmetic bit format of integers.
	 */
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

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 18:04:51 ---------------------------------------------------
	 */
	/**
	 * Gets the current precision used for working with floating point.
	 * @return the precision used for working with floating point values.
	 */
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

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 18:10:55 ---------------------------------------------------
	 */
	/**
	 * Gets the current {@code BinaryRep} used for working with binary expressions.
	 * @return the current bit representation as a {@code BinaryRep}.
	 */
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

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 18:12:30 ---------------------------------------------------
	 */
	/**
	 * Gets current overflow value.
	 * @return the overflow/carry bit.
	 * @see mathaid.calculator.base.evaluator.parser.expression.programmer
	 */
	public abstract BigInteger getCarry();

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 18:14:43 ---------------------------------------------------
	 */
	/**
	 * Evaluates this expression and returns a expression as the result.
	 * <p>The value returned is always numeric.
	 * <p>
	 * Evaluation is done using inputs provided from the expression itself. These inputs are values that were parsed from a string
	 * expression by a {@code Parser} or that were gotten from the {@link #getParams() params} object.
	 * <p>Note that this does (neither should it) mutate this object. Additionally this method may return the same object when called.
	 * 
	 * @return a valid {@code PExpression} (possibly a new object) that was the result of the computation done.
	 */
	@Override
	public abstract PExpression evaluate();

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 18:19:22 ---------------------------------------------------
	 */
	/**
	 * Checks if this expression is in floating point format i.e has a radix point in {@link #getName()}.
	 * @return <code>true</code> if it is and <code>false</code> if otherwise.
	 */
	public boolean isFloatingPoint() {
		return getFloatingPoint() != null;
	}

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 18:20:47 ---------------------------------------------------
	 */
	/**
	 * Checks if this expression is in non-floating point format i.e has a no radix point in {@link #getName()}.
	 * @return <code>true</code> if it is not a floating point and <code>false</code> if otherwise.
	 */
	public boolean isInteger() {
		return getInteger() != null;
	}

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 18:21:50 ---------------------------------------------------
	 */
	/**
	 * Gets {@code this} as a floating point value, if it was created as one.
	 * @return {@code this} as a floating point.
	 */
	abstract BinaryFP getFloatingPoint();

	/*
	 * Date: 4 Apr 2024 -----------------------------------------------------------
	 * Time created: 23:00:44 ---------------------------------------------------
	 */	
	/**
	 * Converts this integer to a floating-point (if {@code isInteger == true}) whose bits are represented by this integer.
	 * If this expression is already a floating-point, then returns {@code this}.
	 * @return {@code this} as a floating-point whereby {@code isInteger == false} and {@code isFloatingPoint == true}
	 * after this method returns.
	 */
	abstract Name toFloatingPoint();

	/*
	 * Date: 4 Apr 2024 -----------------------------------------------------------
	 * Time created: 23:00:44 ---------------------------------------------------
	 */	
	/**
	 * Converts this floating-point to an integer (if {@code isFloatingPoint == true}) whose bits are represented by this floating-point.
	 * If this expression is already an integer, then returns {@code this}.
	 * @return {@code this} as an integer whereby {@code isInteger == true} and {@code isFloatingPoint == false}
	 * after this method returns.
	 */
	abstract Name toInteger();

	/*
	 * Date: 30 Nov 2023 -----------------------------------------------------------
	 * Time created: 18:21:59 ---------------------------------------------------
	 */
	/**
	 * Gets {@code this} as an integer value, if it was created as one.
	 * @return {@code this} as an integer.
	 */
	abstract BigInteger getInteger();
}
