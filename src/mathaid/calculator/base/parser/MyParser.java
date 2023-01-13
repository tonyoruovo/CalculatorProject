/**
 * 
 */
package mathaid.calculator.base.parser;

import mathaid.calculator.base.parser.expression.NameExpression;
import mathaid.calculator.base.parser.parselet.FunctionParselet;
import mathaid.calculator.base.parser.parselet.GroupParselet;
import mathaid.calculator.base.parser.parselet.InfixParselet;
import mathaid.calculator.base.parser.parselet.NameParselet;
import mathaid.calculator.base.parser.parselet.PostfixParselet;
import mathaid.calculator.base.parser.parselet.PrefixParselet;

/*
 * Date: 7 May 2021----------------------------------------------------------- 
 * Time created: 04:15:30---------------------------------------------------  
 * Package: mathaid.calculator.base.parser------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: MyParser.java------------------------------------------------------ 
 * Class name: MyParser------------------------------------------------ 
 */
/**
 * An object that extends the {@code PrattParser} class to provide functionality
 * for {@link Lexer#COMMON_SYNTAX}.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
class MyParser extends PrattParser<NameExpression> {

	/*
	 * Date: 3 Aug 2021-----------------------------------------------------------
	 * Time created: 19:16:01---------------------------------------------------
	 */
	/**
	 * Constructs a {@code MyParser} that will parse tokens as they are presented
	 * and no context is applied to them concerning precedence.
	 * 
	 * @param text the source text to be parsed.
	 * @apiNote This parser does nothing with the tokens.
	 */
	MyParser(String text) {
		super(new Lexer(text, buildBasicSyntax()), buildBasicSyntax());
	}

	/*
	 * Date: 3 Aug 2021-----------------------------------------------------------
	 * Time created: 19:20:34---------------------------------------------------
	 */
	/**
	 * Constructs a {@code MyParser} that will parse tokens depending on a
	 * pre-assigned precedence. This constructor is made to be different from the
	 * other one by implementing a char array as the source.
	 * 
	 * @param text the source text to be parsed.
	 * @apiNote This parser does nothing with the tokens.
	 */
	MyParser(char[] text) {
		super(text == null ? null : new Lexer(String.valueOf(text), buildCommonSyntax()), buildCommonSyntax());
	}

	/*
	 * Date: 3 Aug 2021-----------------------------------------------------------
	 * Time created: 19:26:54--------------------------------------------
	 */
	/**
	 * A {@code CommonSyntax} that in theory does not take precedence into account.
	 * However this is just a dummy syntax and does nothing.
	 * 
	 * @return a {@code CommonSyntax} that will not regard precedence.
	 */
	// Remember to create support for point '.' and exponent 'e'
	static CommonSyntax<NameExpression, PrattParser<NameExpression>> buildBasicSyntax() {
		CommonSyntax.Builder<NameExpression> builder = new CommonSyntax.Builder<>();

		builder.registerPunctuatorAndDelimeter('(');
		builder.registerPunctuatorAndDelimeter(')');
		builder.registerPunctuatorAndDelimeter('[');
		builder.registerPunctuatorAndDelimeter(']');
		builder.registerPunctuatorAndDelimeter('.');
		builder.registerPunctuatorAndDelimeter(',');
		builder.registerPunctuatorAndDelimeter('-');
		builder.registerPunctuatorAndDelimeter('+');
		builder.registerPunctuatorAndDelimeter('*');
		builder.registerPunctuatorAndDelimeter('/');
		builder.registerPunctuatorAndDelimeter('^');
		builder.registerPunctuatorAndDelimeter('!');
		builder.registerPunctuatorAndDelimeter('\'');
		builder.registerArgDelimiter(',');
		builder.registerPrecedenceDirectors(new Character[] { '(', ')', });
		builder.registerFunctionDelimiters(new Character[] { '[', ']', });

		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		for (int i = 0; i < chars.length(); i++)
			builder.registerLetter(chars.charAt(i));
		chars = "1234567890";
		for (int i = 0; i < chars.length(); i++)
			builder.registerDigit(chars.charAt(i));

		builder.registerTypes(CommonSyntax.BASIC_TYPES.values());
		builder.registerNameType(CommonSyntax.BASIC_TYPES.get(CommonSyntax.NAME));

		builder.registerPrefixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.NAME), new NameParselet());
		// expressions like b * (a + c)
		builder.registerPrefixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.LEFT_PARENTHESIS),
				new GroupParselet());
		// expressions like Sin(x)
		builder.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.LEFT_BRACKET), new FunctionParselet());
		builder.registerPrefixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.PLUS), new PrefixParselet());
		builder.registerPrefixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.MINUS), new PrefixParselet());

		builder.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.PLUS), new InfixParselet(false));
		builder.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.MINUS), new InfixParselet(false));
		builder.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.ASTERISK), new InfixParselet(false));
		builder.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.FORWARD_SLASH),
				new InfixParselet(false));
		builder.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.CARET), new InfixParselet(true));
		builder.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.BANG), new PostfixParselet());
		// for numbers starting with a point such as .0983912
		builder.registerPrefixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.PERIOD), new PrefixParselet());
		// for numbers that have a decimal point in between such as 123.456789
		builder.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.PERIOD), new InfixParselet(false));
		builder.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.QUOTE), new PostfixParselet());

		return builder.build();
	}

	/*
	 * Date: 3 Aug 2021-----------------------------------------------------------
	 * Time created: 19:26:54--------------------------------------------
	 */
	/**
	 * A {@code CommonSyntax} that in theory takes precedence into account. However
	 * this is just a dummy syntax and does nothing.
	 * 
	 * @return a {@code CommonSyntax} that will regard precedence when parsing.
	 */
	static CommonSyntax<NameExpression, PrattParser<NameExpression>> buildCommonSyntax() {
		CommonSyntax.Builder<NameExpression> builder = new CommonSyntax.Builder<>();

		builder.registerPunctuatorAndDelimeter('(');
		builder.registerPunctuatorAndDelimeter(')');
		builder.registerPunctuatorAndDelimeter('[');
		builder.registerPunctuatorAndDelimeter(']');
		builder.registerPunctuatorAndDelimeter('.');
		builder.registerPunctuatorAndDelimeter(',');
		builder.registerPunctuatorAndDelimeter('-');
		builder.registerPunctuatorAndDelimeter('+');
		builder.registerPunctuatorAndDelimeter('*');
		builder.registerPunctuatorAndDelimeter('/');
		builder.registerPunctuatorAndDelimeter('^');
		builder.registerPunctuatorAndDelimeter('!');
		builder.registerPunctuatorAndDelimeter('\'');
		builder.registerArgDelimiter(',');
		builder.registerPrecedenceDirectors(new Character[] { '(', ')', });
		builder.registerFunctionDelimiters(new Character[] { '[', ']', });

		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		for (int i = 0; i < chars.length(); i++)
			builder.registerLetter(chars.charAt(i));
		chars = "1234567890";
		for (int i = 0; i < chars.length(); i++)
			builder.registerDigit(chars.charAt(i));

		builder.registerTypes(CommonSyntax.COMMON_TYPES.values());
		builder.registerNameType(CommonSyntax.COMMON_TYPES.get(CommonSyntax.NAME));

		builder.registerPrefixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.NAME), new NameParselet());
		// expressions like b * (a + c)
		builder.registerPrefixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.LEFT_PARENTHESIS),
				new GroupParselet());
		// expressions like Sin(x)
		builder.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.LEFT_BRACKET), new FunctionParselet());
		builder.registerPrefixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.PLUS), new PrefixParselet());
		builder.registerPrefixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.MINUS), new PrefixParselet());

		builder.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.PLUS), new InfixParselet(false));
		builder.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.MINUS), new InfixParselet(false));
		builder.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.ASTERISK), new InfixParselet(false));
		builder.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.FORWARD_SLASH),
				new InfixParselet(false));
		builder.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.CARET), new InfixParselet(true));
		builder.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.BANG), new PostfixParselet());
		// for numbers starting with a point such as .0983912
		builder.registerPrefixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.PERIOD), new PrefixParselet());
		// for numbers that have a decimal point in between such as 123.456789
		builder.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.PERIOD), new InfixParselet(false));
		builder.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.QUOTE), new PostfixParselet());

		return builder.build();
	}

}
