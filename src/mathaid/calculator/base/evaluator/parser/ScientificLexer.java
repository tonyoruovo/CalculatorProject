/**
 * 
 */
package mathaid.calculator.base.evaluator.parser;

import java.util.Iterator;

import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.evaluator.parser.expression.scientific.Name.Params;
import mathaid.calculator.base.evaluator.parser.parselet.scientific.ArrayParselet;
import mathaid.calculator.base.evaluator.parser.parselet.scientific.FunctionParselet;
import mathaid.calculator.base.evaluator.parser.parselet.scientific.GroupParselet;
import mathaid.calculator.base.evaluator.parser.parselet.scientific.NameParselet;
import mathaid.calculator.base.evaluator.parser.parselet.scientific.OperatorParselet;
import mathaid.calculator.base.evaluator.parser.parselet.scientific.PostfixParselet;
import mathaid.calculator.base.evaluator.parser.parselet.scientific.PrefixParselet;

/*
 * Date: 15 Sep 2022----------------------------------------------------------- 
 * Time created: 09:04:00---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: ScientificLexer.java------------------------------------------------------ 
 * Class name: ScientificLexer------------------------------------------------ 
 */
/**
 * A lexer that validates values based on a given syntax for the scientific calculator.
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
public class ScientificLexer implements Iterator<Token<String>> {

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 15:29:11 ---------------------------------------------------
	 */
	/**
	 * Builds the default syntax.
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
				.registerPunctuatorAndDelimeter('!').registerPunctuatorAndDelimeter('>')// for Lambdas
				.registerPunctuatorAndDelimeter('\'').registerArgDelimiter(',')
				.registerPrecedenceDirectors(new Character[] { '(', ')' })
				.registerArrayDelimiters(new Character[] { '{', '}' })
				.registerFunctionDelimiters(new Character[] { '[', ']' });

		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		for (int i = 0; i < chars.length(); i++)
			b.registerLetter(chars.charAt(i));
		chars = "1234567890";
		for (int i = 0; i < chars.length(); i++)
			b.registerDigit(chars.charAt(i));

		b.registerTypes(CommonSyntax.COMMON_TYPES.values());
		b.registerNameType(CommonSyntax.COMMON_TYPES.get(CommonSyntax.NAME));

		b.registerPrefixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.NAME), new NameParselet());
		// expressions like b * (a + c)
		b.registerPrefixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.LEFT_PARENTHESIS), new GroupParselet());
		b.registerPrefixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.LEFT_BRACE), new ArrayParselet());
		// expressions like Sin(x)
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.LEFT_BRACKET), new FunctionParselet());
		b.registerPrefixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.PLUS), new PrefixParselet());
		b.registerPrefixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.MINUS), new PrefixParselet());

		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.PLUS), new OperatorParselet(false));
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.MINUS), new OperatorParselet(false));
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.ASTERISK), new OperatorParselet(false));
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.FORWARD_SLASH), new OperatorParselet(false));
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.CARET), new OperatorParselet(true));
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.BANG), new PostfixParselet());
		// for numbers starting with a point such as .0983912
		b.registerPrefixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.PERIOD), new PrefixParselet());
		// for numbers that have a decimal point in between such as 123.456789
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.PERIOD), new OperatorParselet(false));
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.QUOTE), new PostfixParselet());
		// for lambdas
		b.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.RIGHT_LAMBDA), new OperatorParselet(true));

		return b.build();
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 15:21:49 ---------------------------------------------------
	 */
	/**
	 * Constructor for creating a {@code ScientificLexer} by specifying the source code and syntax.
	 * 
	 * @param src    the source code to be parsed
	 * @param syntax the syntax that provides symbol specifications.
	 */
	public ScientificLexer(String src,
			CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params> syntax) {
		this(src, 0, src.length(), syntax);
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 15:24:59 ---------------------------------------------------
	 */
	/**
	 * Constructor for creating a {@code ScientificLexer} by specifying the initial source code to be parsed, the positions from
	 * which to start the parsing and syntax to used for symbol identification.
	 * 
	 * @param src        the source code to be parsed.
	 * @param beginIndex the index from which to begin the reading of symbols
	 * @param endIndex   the index at which to end the reading of symbols.
	 * @param syntax     the syntax that provides symbol specifications.
	 */
	public ScientificLexer(String src, int beginIndex, int endIndex,
			CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params> syntax) {
//		this(src.substring(beginIndex, endIndex), syntax);
		this.src = src.substring(beginIndex, endIndex);
		this.index = 0;
		this.syntax = syntax;
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 15:28:42 ---------------------------------------------------
	 */
	/**
	 * Constructor for creating a {@code ScientificLexer} using an internal default syntax.
	 * 
	 * @param src   the source code to be parsed.
	 * @param begin the index from which to begin the reading of symbols
	 * @param end   the index at which to end the reading of symbols
	 */
	public ScientificLexer(String src, int begin, int end) {
		this(src, begin, end, buildSyntax());
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 15:30:16 ---------------------------------------------------
	 */
	/**
	 * Constructor for creating a {@code ScientificLexer} by specifying the initial source code to be parsed.
	 * 
	 * @param src the source code to be parsed.
	 */
	public ScientificLexer(String src) {
		this(src, 0, src.length(), buildSyntax());
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 15:31:37 ---------------------------------------------------
	 */
	/**
	 * Constructor for creating a {@code ScientificLexer} from an empty string.
	 */
	public ScientificLexer() {// for debug purposes
		this("");
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 15:33:34 ---------------------------------------------------
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
	 * Time created: 15:34:13 ---------------------------------------------------
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
	 * Time created: 15:34:46 ---------------------------------------------------
	 */
	/**
	 * Sets the source code to the given value. A <code>null</code> value has no effect.
	 * <p>
	 * This resets the iterator's cursor.
	 * 
	 * @param src the non-null value to be used as the new source code.
	 */
	public void setSource(String src) {
		if (src != null && src.length() > 0) {
			this.src = src;
			index = 0;
		}
	}

	/*
	 * Most Recent Date: 15 Sep 2022-----------------------------------------------
	 * Most recent time created: 09:04:01--------------------------------------
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
	 * Most Recent Date: 15 Sep 2022-----------------------------------------------
	 * Most recent time created: 09:04:01--------------------------------------
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
			if (isPuncAndOrDelim(c))
				return getSymbolToken(c);
			else if (isLetter(c)) {
				return getLetterToken();
			} else if (isNumber(c)) {
				return getNumberToken();
			}
			throw new RuntimeException(String.format("the token \"%s\" is unknown", c));
		}
		return new Token<>(CommonSyntax.BASIC_TYPES.get(CommonSyntax.EOF), "");
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 15:45:49 ---------------------------------------------------
	 */
	/**
	 * Generates a {@code Token} comprising of numeric symbols, which are in the same format as the mathaid number specified in the
	 * {@link mathaid.calculator.base.typeset.NumberAdapter} documentation.
	 * <p>
	 * This is called when the lexer encounters a digit specified by {@link #isNumber(char)}.
	 * 
	 * @return {@code String} of symbols that makeup a valid mathaid number as a token.
	 */
	private Token<String> getNumberToken() {
		int start = index - 1;
		while (index < src.length()) {
			char ch = src.charAt(index);
			/*
			 * if this lexer encounters a non-digit and that non-digit is not an exponent
			 * character (e because symja uses only java's double exponent operator) stop
			 * parsing the string and return the word as number
			 */
			if (ch != 'e' && ch != '.' && (!syntax.getDigits().contains(ch)))
				if (src.charAt(index - 1) == 'e' && (ch == '-' || ch == '+'))
					;
				else
					break;
			index++;
		}
		String num = src.substring(start, index);
		return new Token<>(syntax.getNameType(), num);
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 15:54:36 ---------------------------------------------------
	 */
	/**
	 * Gets the character being validated by this lexer.
	 * 
	 * @return the current character being read and validated.
	 */
	private char getCurrentCharacter() {
		return src.charAt(index);
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 15:55:27 ---------------------------------------------------
	 */
	/**
	 * Generates the next letter token (used for declaring variables and constants) from the reading the next set of symbols.
	 * <p>
	 * Called by {@link #next()} when an alphabetic character is encountered.
	 * 
	 * @return the next letter token.
	 */
	private Token<String> getLetterToken() {
		int start = index - 1;
		while (index < src.length()) {
			if (!syntax.getLetters().contains(getCurrentCharacter()))
				break;
			index++;
		}
		String name = src.substring(start, index);
		return new Token<>(syntax.getNameType(), name);
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 16:03:16 ---------------------------------------------------
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
			char ch = lookAhead();
			if (ch == '>') {
				index += 1;
				return new Token<>(CommonSyntax.BASIC_TYPES.get(CommonSyntax.RIGHT_LAMBDA),
						String.format("%1$s%2$s", c, ch));
			}
		}
		return new Token<>(syntax.getType(c), String.valueOf(c));
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 16:06:56 ---------------------------------------------------
	 */
	/**
	 * Peeks ahead of the cursor position at the nect character to be read and returns it.
	 * 
	 * @return the character at one index after the current position of the {@code Iterator}'s cursor.
	 */
	private char lookAhead() {
		return src.charAt(index + 1);
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 16:08:14 ---------------------------------------------------
	 */
	/**
	 * Checks if the given {@code char} value is a punctuation or delimiter. Basically, this checks if the value is a number,
	 * variable or constant.
	 * 
	 * @param c the value to be checked.
	 * @return <code>true</code> if the argument is considered as a punctuation or a delimiter by the internal syntax.
	 */
	private boolean isPuncAndOrDelim(char c) {
		return syntax.getPunctuatorsAndDelimiters().contains(c);
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 16:10:36 ---------------------------------------------------
	 */
	/**
	 * Checks if the given {@code char} value is a letter i.e it can be used as a constant or variable.
	 * 
	 * @param c the value to be checked.
	 * @return <code>true</code> if the argument is a letter or else returns <code>false</code>.
	 */
	private boolean isLetter(char c) {
		return syntax.getLetters().contains(c);
	}

	/*
	 * Date: 1 Dec 2023 -----------------------------------------------------------
	 * Time created: 16:12:14 ---------------------------------------------------
	 */
	/**
	 * Checks if the given {@code char} argument is a number. This is determined by the internal syntax.
	 * 
	 * @param c the value to be checked.
	 * @return <code>true</code> if the argument is a number or else returns {@code false}.
	 */
	private boolean isNumber(char c) {
		return syntax.getDigits().contains(c);
	}

	/**
	 * Holds the source code
	 */
	private String src;
	/**
	 * Holds the {@code Iterator}'s current cursor position.
	 */
	private int index;
	/**
	 * Holds the internal syntax object.
	 */
	private final CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params> syntax;
}
