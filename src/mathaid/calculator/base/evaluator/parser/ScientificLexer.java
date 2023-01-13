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
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class ScientificLexer implements Iterator<Token<String>> {

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

		b.registerTypes(CommonSyntax.BASIC_TYPES.values());
		b.registerNameType(CommonSyntax.BASIC_TYPES.get(CommonSyntax.NAME));

		b.registerPrefixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.NAME), new NameParselet());
		// expressions like b * (a + c)
		b.registerPrefixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.LEFT_PARENTHESIS), new GroupParselet());
		b.registerPrefixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.LEFT_BRACE), new ArrayParselet());
		// expressions like Sin(x)
		b.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.LEFT_BRACKET), new FunctionParselet());
		b.registerPrefixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.PLUS), new PrefixParselet());
		b.registerPrefixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.MINUS), new PrefixParselet());

		b.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.PLUS), new OperatorParselet(false));
		b.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.MINUS), new OperatorParselet(false));
		b.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.ASTERISK), new OperatorParselet(false));
		b.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.FORWARD_SLASH), new OperatorParselet(false));
		b.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.CARET), new OperatorParselet(true));
		b.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.BANG), new PostfixParselet());
		// for numbers starting with a point such as .0983912
		b.registerPrefixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.PERIOD), new PrefixParselet());
		// for numbers that have a decimal point in between such as 123.456789
		b.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.PERIOD), new OperatorParselet(false));
		b.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.QUOTE), new PostfixParselet());
		// for lambdas
		b.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.RIGHT_LAMBDA), new OperatorParselet(true));

		return b.build();
	}

	public ScientificLexer(String src,
			CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params> syntax) {
		this(src, 0, src.length(), syntax);
	}

	public ScientificLexer(String src, int beginIndex, int endIndex,
			CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params> syntax) {
//		this(src.substring(beginIndex, endIndex), syntax);
		this.src = src.substring(beginIndex, endIndex);
		this.index = 0;
		this.syntax = syntax;
	}

	public ScientificLexer(String src, int begin, int end) {
		this(src, begin, end, buildSyntax());
	}

	public ScientificLexer(String src) {
		this(src, 0, src.length(), buildSyntax());
	}

	public ScientificLexer() {// for debug purposes
		this("");
	}

	public CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params> getSyntax() {
		return syntax;
	}

	public String getSource() {
		return src;
	}

	public void setSource(String src) {
		this.src = src;
		index = 0;
	}

	/*
	 * Most Recent Date: 15 Sep 2022-----------------------------------------------
	 * Most recent time created: 09:04:01--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
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
	 * {@inheritDoc}
	 * 
	 * @return
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
	
	private char getCurrentCharacter() {
		return src.charAt(index);
	}

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

	private char lookAhead() {
		return src.charAt(index + 1);
	}

	private boolean isPuncAndOrDelim(char c) {
		return syntax.getPunctuatorsAndDelimiters().contains(c);
	}

	private boolean isLetter(char c) {
		return syntax.getLetters().contains(c);
	}

	private boolean isNumber(char c) {
		return syntax.getDigits().contains(c);
	}

	private String src;
	private int index;
	private final CommonSyntax<EvaluatableExpression<Params>, PrattParser<EvaluatableExpression<Params>, Params>, Params> syntax;
}
