/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.parselet.programmer;

import java.math.BigInteger;
import java.util.Iterator;

import mathaid.calculator.base.evaluator.parser.CommonSyntax;
import mathaid.calculator.base.evaluator.parser.PrattParser;
import mathaid.calculator.base.evaluator.parser.Token;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.evaluator.parser.expression.programmer.Name;
import mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression;
import mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression.Params;
import mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression.Params.ResultType;
import mathaid.calculator.base.evaluator.parser.parselet.Parselet;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.util.Utility;
import mathaid.calculator.base.value.BinaryFPPrecision;
import mathaid.calculator.base.value.BinaryFPPrecision.BinaryFP;
import mathaid.calculator.base.value.BitLength;
import mathaid.calculator.base.value.FloatAid;

/*
 * Date: 1 Dec 2022----------------------------------------------------------- 
 * Time created: 07:57:09---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.parselet.programmer------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: NameParselet.java------------------------------------------------------ 
 * Class name: NameParselet------------------------------------------------ 
 */
/**
 * A prefix {@code Parselet} for creating expression that are {@link Name} expressions.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class NameParselet implements
		Parselet<String, SegmentBuilder, EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params>, Params> {

	// Does not affect Rep.MATH
	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 10:50:05 ---------------------------------------------------
	 */
	/**
	 * Converts the value given by {@code n} to the bit representation set in {@code p} and returns the result.
	 * <p>
	 * The does not have any effect when bit representation is {@link ResultType#REP_MATH}.
	 * 
	 * @param n the value to be converted.
	 * @param p the value for accessing the endianess and bit representation.
	 * @return the converted {@code BigInteger} value
	 */
	static BigInteger toCurrentEndianess(BigInteger n, Params p) {
		if (p.getBitRepresentation() != ResultType.REP_MATH)
			switch (p.getEndianess()) {
			case ResultType.ENDIAN_SMALL:
				return BitLength.toSmallEndian(n);
			case ResultType.ENDIAN_PDP_11:
				return BitLength.toPDP11Endian(n);
			case ResultType.ENDIAN_BIG:
			default:
			}
		return n;
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 10:55:24 ---------------------------------------------------
	 */
	/**
	 * Gets the corresponding {@code BinaryFPPrecision} for the set bit length with the {@code Params} object.
	 * 
	 * @param p the value for accessing the bit length
	 * @return a {@code BinaryFPPrecision} object matching the bit length.
	 */
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

	/*
	 * Most Recent Date: 1 Dec 2022-----------------------------------------------
	 * Most recent time created: 08:02:22--------------------------------------
	 */
	/**
	 * Creates a new {@code Name} object.
	 * <p>
	 * Endianess, bit-length, bit representation all play an important role in the creation.
	 * 
	 * @param alreadyParsedLeft  not accessed. Can be <code>null</code>.
	 * @param yetToBeParsedToken the value being parsed.
	 * @param parser             {@inheritDoc}
	 * @param lexerReference     {@inheritDoc}
	 * @param syntax             {@inheritDoc}
	 * @param params             {@inheritDoc}
	 * @return a new {@code Name} object.
	 */
	// Endianess is applied here
	@Override
	public PExpression parse(EvaluatableExpression<Params> alreadyParsedLeft, Token<String> yetToBeParsedToken,
			PrattParser<EvaluatableExpression<Params>, Params> parser, Iterator<Token<String>> lexerReference,
			CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params> syntax,
			Params params) {
		if (yetToBeParsedToken.getName().contains(".") || yetToBeParsedToken.getName().contains("e+")
				|| yetToBeParsedToken.getName().contains("e-") || yetToBeParsedToken.getName().contains("E+")
				|| yetToBeParsedToken.getName().contains("E-") || yetToBeParsedToken.getName().contains("p+")
				|| yetToBeParsedToken.getName().contains("p-") || yetToBeParsedToken.getName().contains("P+")
				|| yetToBeParsedToken.getName().contains("P-") || params.getBitRepresentation() == ResultType.REP_FLOATING_POINT)
			return new Name(getPrecision(params).createFP(yetToBeParsedToken.getName(), params.getRadix()), params);
		else if (Utility.isNumber(yetToBeParsedToken.getName(), params.getRadix())) {
			BigInteger i = new BigInteger(yetToBeParsedToken.getName(), params.getRadix());
			return new Name(toCurrentEndianess(i, params), BigInteger.ZERO, params);
		} else if (yetToBeParsedToken.getType().getName().equals("name")) {
			try {
				BigInteger i = new BigInteger(yetToBeParsedToken.getName(), params.getRadix());
				return new Name(toCurrentEndianess(i, params), BigInteger.ZERO, params);
			} catch (NumberFormatException e) {
				try {
					BinaryFP i = getPrecision(params).createFP(yetToBeParsedToken.getName(), params.getRadix());
					return new Name(i, params);
				} catch (NumberFormatException ex) {
				}
			}
		}
		return new Name(yetToBeParsedToken.getName(), params, String.class);
	}

}
