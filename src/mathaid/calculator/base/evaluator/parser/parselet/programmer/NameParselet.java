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
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class NameParselet implements
		Parselet<String, SegmentBuilder, EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params>, Params> {

	//Does not affect Rep.MATH
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
	 * {@inheritDoc}
	 * 
	 * @param alreadyParsedLeft
	 * @param yetToBeParsedToken
	 * @param parser
	 * @param lexerReference
	 * @param syntax
	 * @param params
	 * @return
	 */
	//Endianess is applied here
	@SuppressWarnings("unused")
	@Override
	public PExpression parse(EvaluatableExpression<Params> alreadyParsedLeft, Token<String> yetToBeParsedToken,
			PrattParser<EvaluatableExpression<Params>, Params> parser, Iterator<Token<String>> lexerReference,
			CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params> syntax,
			Params params) {
		if (yetToBeParsedToken.getName().contains(".") || yetToBeParsedToken.getName().contains("e+")
				|| yetToBeParsedToken.getName().contains("e-") || yetToBeParsedToken.getName().contains("E+")
				|| yetToBeParsedToken.getName().contains("E-") || yetToBeParsedToken.getName().contains("p+")
				|| yetToBeParsedToken.getName().contains("p-") || yetToBeParsedToken.getName().contains("P+")
				|| yetToBeParsedToken.getName().contains("P-"))
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
