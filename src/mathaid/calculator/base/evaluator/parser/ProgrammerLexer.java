/**
 * 
 */
package mathaid.calculator.base.evaluator.parser;

import java.util.Iterator;

import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression.Params;
import mathaid.calculator.base.evaluator.parser.parselet.programmer.FunctionParselet;
import mathaid.calculator.base.evaluator.parser.parselet.programmer.GroupParselet;
import mathaid.calculator.base.evaluator.parser.parselet.programmer.NameParselet;
import mathaid.calculator.base.evaluator.parser.parselet.programmer.OperatorParselet;
import mathaid.calculator.base.evaluator.parser.parselet.programmer.PostfixParselet;
import mathaid.calculator.base.evaluator.parser.parselet.programmer.PrefixParselet;
import mathaid.calculator.base.value.FloatAid;

/*
 * Date: 26 Nov 2022----------------------------------------------------------- 
 * Time created: 11:03:01---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: ProgrammerLexer.java------------------------------------------------------ 
 * Class name: ProgrammerLexer------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class ProgrammerLexer implements Iterator<Token<String>> {

	@SuppressWarnings("unused")
	private static CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params> buildSyntax() {

		CommonSyntax.Builder<EvaluatableExpression<Params>, Params> b = new CommonSyntax.Builder<>();

		b.registerPunctuatorAndDelimeter('(').registerPunctuatorAndDelimeter(')').registerPunctuatorAndDelimeter('[')
				.registerPunctuatorAndDelimeter(']').registerPunctuatorAndDelimeter('.')
				.registerPunctuatorAndDelimeter(',').registerPunctuatorAndDelimeter('-')
				.registerPunctuatorAndDelimeter('+').registerPunctuatorAndDelimeter('*')
				.registerPunctuatorAndDelimeter('/').registerPunctuatorAndDelimeter('^')
				.registerPunctuatorAndDelimeter('{').registerPunctuatorAndDelimeter('}')
				.registerPunctuatorAndDelimeter('~').registerPunctuatorAndDelimeter('¬')
				.registerPunctuatorAndDelimeter('\u2227').registerPunctuatorAndDelimeter('\u2228')
				.registerPunctuatorAndDelimeter('|').registerPunctuatorAndDelimeter('&')
				.registerPunctuatorAndDelimeter('%').registerPunctuatorAndDelimeter('!')
				.registerPunctuatorAndDelimeter('>')// for Lambdas and right shift
				.registerPunctuatorAndDelimeter('\'').registerPunctuatorAndDelimeter('<')// for left shift
				.registerPrecedenceDirectors(new Character[] { '(', ')' })
				.registerArrayDelimiters(new Character[] { '{', '}' })
				.registerFunctionDelimiters(new Character[] { '[', ']' });

		String chars = "abcdefghijklmnopqrstuvwxyz";
		for (int i = 0; i < chars.length(); i++)
			b.registerLetter(chars.charAt(i));
		chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 0; i < chars.length(); i++)
			b.registerDigit(chars.charAt(i));

		b.registerTypes(CommonSyntax.COMMON_TYPES.values());
		b.registerNameType(CommonSyntax.COMMON_TYPES.get(CommonSyntax.NAME));

		b.registerPrefixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.NAME), new NameParselet());
		// expressions like b * (a + c)
		b.registerPrefixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.LEFT_PARENTHESIS), new GroupParselet());
		// expressions like Sin[x]
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.LEFT_BRACKET), new FunctionParselet());
		b.registerPrefixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.PLUS), new PrefixParselet());
		b.registerPrefixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.MINUS), new PrefixParselet());
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.BANG), new PrefixParselet());
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.TILDE), new PrefixParselet());
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.NEGATION), new PrefixParselet());

		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.LOGICAL_OR), new OperatorParselet(false));
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.LOGICAL_AND), new OperatorParselet(true));
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.PLUS), new OperatorParselet(false));
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.MINUS), new OperatorParselet(false));
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.ASTERISK), new OperatorParselet(false));
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.FORWARD_SLASH), new OperatorParselet(false));
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.CARET), new OperatorParselet(true));
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.PERCENT), new OperatorParselet(false));
		// for numbers starting with a point such as .0983912
		b.registerPrefixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.PERIOD), new PrefixParselet());
		// for numbers that have a decimal point in between such as 123.456789
//		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.PERIOD), new OperatorParselet(false));
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.QUOTE), new PostfixParselet());
		// for lambdas
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.RIGHT_LAMBDA), new OperatorParselet(true));
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.RIGHT_SHIFT), new OperatorParselet(true));
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.LEFT_SHIFT), new OperatorParselet(true));

		return b.build();
	}

	public ProgrammerLexer(String src, int start, int end,
			CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params> syntax,
			int radix) {
		this.src = src.substring(start, end);
		this.syntax = syntax;
		this.radix = radix;
	}

	public int getRadix() {
		return radix;
	}

	public String getSource() {
		return src;
	}

	public void setSource(String src) {
		this.src = src;
	}

	public void setRadix(int radix) {
		this.radix = radix;
	}

	public CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params> getSyntax() {
		return syntax;
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public Token<String> next() {
		if (index < src.length()) {
			char c = src.charAt(index++);
			if (isSymbol(c)) {
				return getSymbolToken(c);
			} else if (isLowerCaseLetter(c)) {
				return getLetterToken();
			} else if (isNumber(c)) {
				return getNumberToken(new StringBuilder(), false, false, false);
			}
			throw new RuntimeException(String.format("the token \"%s\" is unknown", c));
		}
		return new Token<>(CommonSyntax.COMMON_TYPES.get(CommonSyntax.EOF), "");
	}

	/*
	 * Date: 11 Dec 2022-----------------------------------------------------------
	 * Time created: 04:42:34--------------------------------------------
	 */
	/**
	 * @param c
	 * @return
	 */
	private Token<String> getSymbolToken(char c) {
		if (c == '-' && index < src.length()) {// for lamda
			char ch = src.charAt(index);
			if (ch == '>') {
				index += 1;
				return new Token<>(CommonSyntax.COMMON_TYPES.get(CommonSyntax.RIGHT_LAMBDA),
						String.format("%1$s%2$s", c, ch));
			}
		} else if (c == '>' && index < src.length()) {// for right shift
			char ch = src.charAt(index);
			if (ch == '>') {
				index += 1;
				return new Token<>(CommonSyntax.COMMON_TYPES.get(CommonSyntax.RIGHT_SHIFT),
						String.format("%1$s%2$s", c, ch));
			}
		} else if (c == '<' && index < src.length()) {// for left shift
			char ch = src.charAt(index);
			if (ch == '<') {
				index += 1;
				return new Token<>(CommonSyntax.COMMON_TYPES.get(CommonSyntax.LEFT_SHIFT),
						String.format("%1$s%2$s", c, ch));
			}
		}
		return new Token<>(syntax.getType(c), String.valueOf(c));
	}

	/*
	 * Date: 11 Dec 2022-----------------------------------------------------------
	 * Time created: 04:41:18--------------------------------------------
	 */
	/**
	 * @param c
	 * @return
	 */
	private boolean isSymbol(char c) {
		return syntax.getPunctuatorsAndDelimiters().contains(c);
	}

	@SuppressWarnings("unused")
	private Token<String> getNumberToken(StringBuilder sb, boolean hasPoint, boolean radixFound,
			boolean exponentCharIsFound) {
		if (index < src.length()) {
			char c = src.charAt(index);

			if (isNumber(c) || (c == '.' && !hasPoint)) {
				sb.append(c);
				index++;
				if (c == '.')
					hasPoint = true;
				return getNumberToken(sb, hasPoint, radixFound, exponentCharIsFound);
			} else if (c == '_' && !radixFound) {
				index++;
				StringBuilder rad = new StringBuilder();
				getRadixValue(rad);
				int radix;
				try {
					radix = Integer.parseInt(rad.toString());
				} catch (NumberFormatException e) {
					throw new NumberFormatException("radix is not decimal");
				}
				String significand;
				try {
					significand = FloatAid.toString(sb.toString(), radix, this.radix, null);
				} catch (NumberFormatException e) {
					throw new NumberFormatException("number is not in the same radix as given");
				}
				sb.replace(0, sb.length(), significand);
				return getNumberToken(sb, true, true, exponentCharIsFound);
			} else if (isExponent(c, exponentCharIsFound)) {
				exponentCharIsFound = true;
				StringBuilder exp = new StringBuilder();
				index++;
				getExp(exp, false);
				sb.append(Character.toLowerCase(c));
				sb.append(exp);
			}
//			else if (c == 'E' || c == 'e') {
//				StringBuilder exp = new StringBuilder();
//				index++;
//				getExp(exp, false);
//				sb.append('e');
//				sb.append(exp);
//			}

			return new Token<>(CommonSyntax.COMMON_TYPES.get(CommonSyntax.NAME), sb.toString());
		}
		return new Token<>(CommonSyntax.BASIC_TYPES.get(CommonSyntax.EOF), "");
	}

	/*
	 * Date: 10 Dec 2022-----------------------------------------------------------
	 * Time created: 18:04:50--------------------------------------------
	 */
	/**
	 * @param exp
	 */
	@SuppressWarnings("unused")
	private void getExp(StringBuilder exp, boolean signIsFound) {
		if (index < src.length()) {
			char c = src.charAt(index);
			if (isNumber(c) || ((c == '-' || c == '+') && exp.length() == 0)) {
				exp.append(c);
				index++;
				if ((c == '-' || c == '+') && exp.length() < 1)
					signIsFound = true;
				getExp(exp, signIsFound);
			} else if (c == '_') {
				index++;
				StringBuilder sb = new StringBuilder();
				getRadixValue(sb);
				int radix;
				try {
					radix = Integer.parseInt(sb.toString());
				} catch (NumberFormatException e) {
					throw new NumberFormatException("radix is not decimal");
				}
				int exponent;
				try {
					exponent = Integer.parseInt(exp.toString(), radix);
				} catch (NumberFormatException e) {
					throw new NumberFormatException("exponent is not in the same radix as given");
				}
				exp.delete(0, exp.length()).append(Integer.toString(exponent, this.radix));
				if (exp.charAt(0) != '-' && exp.charAt(0) != '+')
					exp.insert(0, '+');
				return;

			}

			if (exp.length() < 1)
				throw new NumberFormatException("no exponent vaue was found");
		}
	}

	private void getRadixValue(StringBuilder sb) {
		if (index < src.length()) {
			char c = src.charAt(index);
			if (isDigit(c)) {
				sb.append(c);
				index++;
				getRadixValue(sb);
			}
			if (sb.length() < 1)
				throw new NumberFormatException("no radix vaue was found");
		}
	}

	/*
	 * Date: 5 Dec 2022-----------------------------------------------------------
	 * Time created: 16:25:54--------------------------------------------
	 */
	/**
	 * @param cRadix
	 * @return
	 */
	private boolean isDigit(char cRadix) {
		return FloatAid.isNumber(cRadix, 10);
	}

	/*
	 * Date: 5 Dec 2022-----------------------------------------------------------
	 * Time created: 15:34:07--------------------------------------------
	 */
	/**
	 * @param c
	 * @param exponentCharIsFound
	 * @return
	 */
	private boolean isExponent(char c, boolean exponentCharIsFound) {
		return ((c == 'p' || c == 'e') || isSignedExponent(c)) && !exponentCharIsFound;
	}

	private boolean isSignedExponent(char c) {
		if (src.length() > index + 1) {
			char sign = src.charAt(index + 1);
			return (c == 'P' || c == 'E') && (sign == '-' || sign == '+');
		}
		return false;
	}

	/*
	 * Date: 5 Dec 2022-----------------------------------------------------------
	 * Time created: 15:24:00--------------------------------------------
	 */
	/**
	 * @param c
	 * @param pointIsFound
	 * @return
	 */
//	private boolean isPoint(char c, boolean pointIsFound) {
//		return c == '.' && !pointIsFound;
//	}

//	private boolean isNumberSign(char c) {
//		return (c == '-' || c == '+') && isNumber(getNextCharacter());
//	}

	/*
	 * Date: 5 Dec 2022-----------------------------------------------------------
	 * Time created: 14:49:36--------------------------------------------
	 */
	/**
	 * @param c
	 * @return
	 */
	private boolean isNumber(char c) {
//		return FloatAid.isNumber(c, Character.MAX_RADIX) && !isLowerCaseLetter(c);
		return syntax.getDigits().contains(c) && !isLowerCaseLetter(c);
	}

	private Token<String> getLetterToken() {
		/*
		 * Since index was already incremented, we need to decrement it once to get the
		 * current character
		 */
		int start = index - 1;
		while (index < src.length()) {
			if (!syntax.getLetters().contains(getNextCharacter()))
				break;
			index++;
		}
		String name = src.substring(start, index);
		return new Token<>(syntax.getNameType(), name);
	}

	/*
	 * Date: 5 Dec 2022-----------------------------------------------------------
	 * Time created: 14:40:21--------------------------------------------
	 */
	/**
	 * @return
	 */
	private char getNextCharacter() {
		return src.charAt(index);
	}

	private boolean isLowerCaseLetter(char letter) {
		/*
		 * a letter cannot start with a 'p' or 'e' as those are used for exponent
		 * characters
		 */
		String alphabet = "abcdfghijklmnoqrstuvwxyz";
		return alphabet.indexOf(letter) > -1;
	}

	private String src;
	private int index;
	private int radix;
	private final CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params> syntax;
}
