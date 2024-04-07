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
 * A lexer that validates values based on a given syntax for the programmer calculator.
 * <p>
 * A lexer is an infinite {@code Iterator} that is a token factory that reads {@code String} symbols, determines (with the help
 * of a {@link Syntax}) their validity and generates corresponding {@link Token} objects meant to be consumed by the
 * {@link Parser}.
 * <p>
 * This lexer is meant for the scientific mathaid calculator.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class ProgrammerLexer implements Iterator<Token<String>> {

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 17:38:53 ---------------------------------------------------
	 */
	/**
	 * Builds the default syntax.
	 * <p>
	 * All upper-case letters are digits. All exponent characters are lower-case except the exponent is signed. All lower-case
	 * letters are for constants and variables.
	 * 
	 * @return a syntax
	 */
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
		b.registerPrefixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.INCREMENT), new PrefixParselet());
		b.registerPrefixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.MINUS), new PrefixParselet());
		b.registerPrefixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.DECREMENT), new PrefixParselet());
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

		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.INCREMENT), new PostfixParselet());
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.DECREMENT), new PostfixParselet());

		return b.build();
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 16:18:20 ---------------------------------------------------
	 */
	/**
	 * Constructor for creating a {@code ProgrammerLexer} by specifying the initial source code to be parsed, the positions from
	 * which to start the parsing, a default radix and syntax to used for symbol identification.
	 * 
	 * @param src    the source code to be parsed.
	 * @param start  the index from which to begin the reading of symbols
	 * @param end    the index at which to end the reading of symbols.
	 * @param syntax the syntax that provides symbol specifications.
	 * @param radix  the radix to used for numbers whose radix is unspecified.
	 */
	public ProgrammerLexer(String src, int start, int end,
			CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params> syntax,
			int radix) {
		this.src = src.substring(start, end);
		this.syntax = syntax;
		this.radix = radix;
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 16:20:35 ---------------------------------------------------
	 */
	/**
	 * Constructor for creating a {@code ProgrammerLexer} by specifying the initial source code to be parsed, a default radix and
	 * syntax to used for symbol identification.
	 * 
	 * @param src    the source code to be parsed.
	 * @param syntax the syntax that provides symbol specifications.
	 * @param radix  the radix to used for numbers whose radix is unspecified.
	 */
	public ProgrammerLexer(String src,
			CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params> syntax,
			int radix) {
		this(src, 0, src.length(), syntax, radix);
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 16:21:01 ---------------------------------------------------
	 */
	/**
	 * Constructor for creating a {@code ProgrammerLexer} by specifying the initial source code to be parsed and a default radix.
	 * <p>
	 * A default syntax is used.
	 * 
	 * @param src   the source code to be parsed.
	 * @param radix the radix to used for numbers whose radix is unspecified.
	 */
	public ProgrammerLexer(String src, int radix) {
		this(src, buildSyntax(), radix);
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 16:31:44 ---------------------------------------------------
	 */
	/**
	 * Constructor for creating a {@code ProgrammerLexer} object.
	 * 
	 * @param src the source code to be read.
	 */
	public ProgrammerLexer(String src) {
		this(src, 10);
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 16:37:46 ---------------------------------------------------
	 */
	/**
	 * Constructor for creating a {@code ProgrammerLexer} from an empty string.
	 */
	public ProgrammerLexer() {
		this("");
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 16:38:48 ---------------------------------------------------
	 */
	/**
	 * Gets the default radix used for validating numbers where the radix is not specified.
	 * 
	 * @return the default radix
	 */
	public int getRadix() {
		return radix;
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 17:38:09 ---------------------------------------------------
	 */
	/**
	 * Gets the source code.
	 * 
	 * @return the source code.
	 */
	public String getSource() {
		return src;
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 17:37:18 ---------------------------------------------------
	 */
	/**
	 * Sets the source code to the given value. A <code>null</code> value has no effect.
	 * <p>
	 * This resets the iterator's cursor.
	 * 
	 * @param src the non-null value to be used as the new source code.
	 */
	public void setSource(String src) {
		this.src = src;
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 17:36:54 ---------------------------------------------------
	 */
	/**
	 * Sets the default radix used for validating numbers where the radix is not specified.
	 * 
	 * @param radix the default radix to be set
	 */
	public void setRadix(int radix) {
		this.radix = radix;
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 17:36:27 ---------------------------------------------------
	 */
	/**
	 * Gets the syntax used for validating symbols
	 * 
	 * @return the syntax.
	 */
	public CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params> getSyntax() {
		return syntax;
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 17:36:16 ---------------------------------------------------
	 */
	/**
	 * Returns <code>true</code>.
	 * 
	 * @return <code>true</code>.
	 */
	@Override
	public boolean hasNext() {
		return true;
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 17:35:54 ---------------------------------------------------
	 */
	/**
	 * Advances the cursor, reads all the symbols from the previous index to the new index of the cursor, validates the value read
	 * and returns it if it passes the validation. Will throw an exception if validation fails.
	 * <p>
	 * Spaces are ignored.
	 * 
	 * @return the next {@code Token} if the cursor has not reached the end of the source else returns an end-of-file {@code Token}.
	 */
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
	 * Gets the next symbol that represents a punctuation such as an operator using the given {@code char} as a hint.
	 * <p>
	 * Called by {@link #next} when the {@code char} hint is encountered.
	 * 
	 * @param c the hint for the type of punctuated token to be returned.
	 * @return the next punctuator token.
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
		} else if(c == '+' && index < src.length()) {// for ++
			char ch = src.charAt(index);
			if (ch == '+') {
				index += 1;
				return new Token<>(CommonSyntax.COMMON_TYPES.get(CommonSyntax.INCREMENT),
						String.format("%1$s%2$s", c, ch));
			}
		} else if(c == '-' && index < src.length()) {// for --
			char ch = src.charAt(index);
			if (ch == '-') {
				index += 1;
				return new Token<>(CommonSyntax.COMMON_TYPES.get(CommonSyntax.DECREMENT),
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
	 * Checks if the given {@code char} value is a punctuation or delimiter. Basically, this checks if the value is a number,
	 * variable or constant.
	 * 
	 * @param c the value to be checked.
	 * @return <code>true</code> if the argument is considered as a punctuation or a delimiter by the internal syntax.
	 */
	private boolean isSymbol(char c) {
		return syntax.getPunctuatorsAndDelimiters().contains(c);
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 17:09:27 ---------------------------------------------------
	 */
	/**
	 * Generates a {@code Token} comprising of numeric symbols.
	 * <pre>
	 * <code>&lt;integer-digits&gt;.?&lt;integer-digits&gt;_&lt;decimal-digits&gt;e|p|E(-|+)|P(-|+)</code>&lt;integer-digits&gt;_&lt;decimal-digits&gt;
	 * </pre>
	 * <p>
	 * This is called when the lexer encounters a digit specified by {@link #isNumber(char)}.
	 * 
	 * @param sb                  a string builder to be used for appending digit in a recursive context.
	 * @param hasPoint            {@code false} when first called, will be set by this method when the lexer's cursor is at a radix
	 *                            point.
	 * @param radixFound          {@code false} when first called, will be set by this method when the radix specifier is found.
	 * @param exponentCharIsFound {@code false} when first called, will be set by this method when the exponent specifier is found.
	 * @return {@code String} of symbols that makeup a valid number as a token.
	 */
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
	 * Appends the exponent part of a number into the given {@code StringBuilder} asserting that a sign character (for the exponent)
	 * should be append as well.
	 * <p>
	 * This is called recursively by {@link #getNumberToken(StringBuilder, boolean, boolean, boolean)}.
	 * 
	 * @param exp         a {@code StringBuilder}.
	 * @param signIsFound an Assertion that the sign is found. This will be {@code false} on the initial call and will be
	 *                    automatically set on subsequent calls when the sign characters have been read. This causes this method to
	 *                    behave differently depending on it's value.
	 */
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

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 17:04:37 ---------------------------------------------------
	 */
	/**
	 * Called by {@link #getNumberToken} to append the radix part of a number into the given {@code StringBuilder}.
	 * <p>
	 * This is called recursively by {@link #getNumberToken}.
	 * 
	 * @param sb a {@code StringBuilder}.
	 * @implNote Only decimal digits are supported.
	 */
	private void getRadixValue(StringBuilder sb) {
		if (index < src.length()) {
			char c = src.charAt(index);
			if (isDecimalDigit(c)) {
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
	 * Checks if the argument is a decimal digit.
	 * 
	 * @param cRadix the value to be checked.
	 * @return <code>true</code> if {@code cRadix} is a decimal character or else {@code false}.
	 */
	private boolean isDecimalDigit(char cRadix) {
		return FloatAid.isNumber(cRadix, 10);
	}

	/*
	 * Date: 5 Dec 2022-----------------------------------------------------------
	 * Time created: 15:34:07--------------------------------------------
	 */
	/**
	 * Checks if the character is an exponent based on the given assertion.
	 * 
	 * @param c                   the value to be checked.
	 * @param exponentCharIsFound an assertion that the argument is an exponent character.
	 * @return <code>((c == 'p' || c == 'e') || isSignedExponent(c)) && !exponentCharIsFound</code>.
	 */
	private boolean isExponent(char c, boolean exponentCharIsFound) {
		return ((c == 'p' || c == 'e') || isSignedExponent(c)) && !exponentCharIsFound;
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 16:46:16 ---------------------------------------------------
	 */
	/**
	 * Checks if the argument is a signed exponent by employing lookahead and checking if the next character is a sign character.
	 * <p>
	 * The value to be checked must be an upper-case character.
	 * 
	 * @param c the value to be checked.
	 * @return <code>true</code> if the argument is an upper-case exponent character and it has a sign suffix or else returns
	 *         {@code false}.
	 */
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
	 * Checks if the given {@code char} argument is a number. This is determined by the internal syntax.
	 * <p>
	 * All alphanumeric characters that are valid digits must be in upper-case.
	 * 
	 * @param c the value to be checked.
	 * @return <code>true</code> if the argument is a number or else returns {@code false}.
	 */
	private boolean isNumber(char c) {
//		return FloatAid.isNumber(c, Character.MAX_RADIX) && !isLowerCaseLetter(c);
		return syntax.getDigits().contains(c) && !isLowerCaseLetter(c);
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 16:43:47 ---------------------------------------------------
	 */
	/**
	 * Generates the next letter token (used for declaring variables and constants) from the reading the next set of symbols.
	 * <p>
	 * Called by {@link #next()} when an alphabetic character is encountered.
	 * 
	 * @return the next letter token.
	 */
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
	 * Gets the next character to be validated by this lexer.
	 * 
	 * @return the next character to be read and validated.
	 */
	private char getNextCharacter() {
		return src.charAt(index);
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 16:40:18 ---------------------------------------------------
	 */
	/**
	 * Checks if the given character is lower case Latin alphabet.
	 * <p>
	 * All symbols used for variables and constants must be in lower-case.
	 * <p>
	 * Letters {@code 'p'} and {@code 'e'} will return false as they are used for exponent values within a floating point number.
	 * 
	 * @param letter the value to be checked.
	 * @return <code>true</code> if the argument is a latin lower case letter or else returns <code>false</code>.
	 */
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
