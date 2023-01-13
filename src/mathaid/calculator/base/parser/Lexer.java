/**
 * 
 */
package mathaid.calculator.base.parser;

import java.util.Iterator;

import mathaid.BaseException;
import mathaid.ExceptionMessage;
import mathaid.calculator.base.parser.expression.NameExpression;

/*
 * Date: 5 May 2021----------------------------------------------------------- 
 * Time created: 22:07:45---------------------------------------------------  
 * Package: com.etineakpopha.parser.parselet------------------------------------------------ 
 * Project: RemoteWork------------------------------------------------ 
 * File: Lexer.java------------------------------------------------------ 
 * Class name: Lexer------------------------------------------------ 
 */
/**
 * A {@code Lexer} is an {@code Iterator} that scans strings
 * character-by-character and creates {@code Token} objects from the context.
 * This action is determined by a {@code Syntax}. The {@code Parser} that will
 * parse {@code Token} objects provided by this class will not regard
 * precedence. Hence {@code 4+(5*2)} is the same as {@code 4+5*2}.
 * <p>
 * There are different types of token that this can create, here are some of the
 * following:
 * <ul>
 * <li>Variable names. Only characters found at {@link Syntax#getLetters()} are
 * regarded as valid.</li>
 * <li>Numbers, which must start with either an optional sign (+/-) or a digit
 * in the range 0-9 (inclusive) for the integer portion followed by an optional
 * decimal point and then followed by digits in the range (0-9) followed by an
 * optional lower case "e" for the exponent followed by an optional sign (+/-)
 * followed by decimal numerals. No extraneous space is supported. <i><b>Hex
 * digits are not supported</b></i></li>
 * <li>Function and grouping delimiters. Please see
 * {@link CommonSyntax#functionArgDelimiter()},
 * {@link CommonSyntax#functionClose()}, {@link CommonSyntax#functionOpen()},
 * {@link CommonSyntax#precedenceDirector1()},
 * {@link CommonSyntax#precedenceDirector2()} and
 * {@link CommonSyntax#getPunctuatorsAndDelimiters()}.</li>
 * <li>Operators including +-/*^</li>
 * </ul>
 * Note that any type of token not specified via a {@code CommonSyntax} will
 * cause an exception to be thrown.
 * </p>
 * <p>
 * The nature of this lexer makes it so that it's {@link #next()} method will
 * never return null or throw an exception just because the cursor has passed
 * the last character in the provided text, instead it will continue to return
 * {@link CommonSyntax#EOF end-of-file} tokens.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class Lexer implements Iterator<Token<String>> {
	/**
	 * The text to be scanned by {@code this}.
	 */
	private String text;
	/**
	 * The current position of the cursor.
	 */
	private int index;
	/**
	 * The {@code Syntax} object used for enforcing non-ambiguities.
	 */
	private CommonSyntax<NameExpression, PrattParser<NameExpression>> cs;
	/**
	 * Constant for a {@code CommonSyntax} object that will regard precedence
	 * similar to common calculators.
	 * <p>
	 * In this syntax, the square brackets (\u005B) and (\u005D) are both used for
	 * function "brackets". So if one wanted to declare the sine of 50 one would
	 * have to write sin[50] and not sin(50).
	 * </p>
	 * <p>
	 * Please see the parselet api documentation for more details.
	 * </p>
	 * 
	 * @apiNote This {@code CommonSyntax} does nothing when used as an argument for
	 *          {@code PrattParser} object.
	 * @see CommonSyntax#COMMON_TYPES
	 */
	protected static final CommonSyntax<NameExpression, PrattParser<NameExpression>> COMMON_SYNTAX = MyParser
			.buildCommonSyntax();

	/*
	 * Date: 3 Aug 2021-----------------------------------------------------------
	 * Time created: 11:08:40---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base.parser-----------------------------------------------
	 * - Project: LatestPoject2------------------------------------------------
	 * File: Lexer.java------------------------------------------------------ Class
	 * name: Token------------------------------------------------
	 */
	/**
	 * Basic concrete implementation provider for the
	 * {@code mathaid.calculator.base.parser.Token} interface.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 *
	 */
	private static class Token implements mathaid.calculator.base.parser.Token<String> {

		/**
		 * The {@code Type} of {@code this}
		 */
		private Type<String> t;
		/**
		 * The String object that this {@code mathaid.calculator.base.parser.Token}
		 * wraps.
		 */
		private String name;

		/*
		 * Date: 3 Aug 2021-----------------------------------------------------------
		 * Time created: 11:16:56---------------------------------------------------
		 */
		/**
		 * Initialises a {@code Token} object with a name and a given {@code Type}.
		 * 
		 * @param name the name of this token.
		 * @param type the type of this token.
		 */
		public Token(String name, Type<String> type) {
			this.name = name;
			this.t = type;
		}

		/*
		 * Most Recent Date: 3 Aug 2021-----------------------------------------------
		 * Most recent time created: 11:18:14--------------------------------------
		 */
		/**
		 * Returns a {@code Type} of type {@code String}.
		 * 
		 * @return a {@code Type} of type {@code String}.
		 */
		@Override
		public Type<String> getType() {
			return t;
		}

		/*
		 * Most Recent Date: 3 Aug 2021-----------------------------------------------
		 * Most recent time created: 11:19:06--------------------------------------
		 */
		/**
		 * Returns the actual string name associated with this token.
		 * 
		 * @return a {@code String} object which was wrapped as a generic.
		 */
		@Override
		public String getName() {
			return name;
		}

		/*
		 * Most Recent Date: 3 Aug 2021-----------------------------------------------
		 * Most recent time created: 11:20:00--------------------------------------
		 */
		/**
		 * Return {@link #getName()}, a colon, a space and the string of
		 * {@link #getType()}.
		 * 
		 * @return the string representation of {@code this}.
		 */
		@Override
		public String toString() {
			return name + ": " + t;
		}

	}

	/*
	 * Date: 3 Aug 2021-----------------------------------------------------------
	 * Time created: 11:09:23---------------------------------------------------
	 */
	/**
	 * Instantiates a {@code Lexer} object with a given text to be parsed and a
	 * syntax to guide parsing, especially precedence setting within the tokens.
	 * 
	 * @param text the text to be tokenised.
	 * @param cs   a {@code CommonSyntax} object to direct which tokens has what
	 *             precedence.
	 */
	Lexer(String text, CommonSyntax<NameExpression, PrattParser<NameExpression>> cs) {
		this.text = text;
		index = 0;
		this.cs = cs;
	}

	/*
	 * Date: 3 Aug 2021-----------------------------------------------------------
	 * Time created: 11:13:31---------------------------------------------------
	 */
	/**
	 * Instantiates a {@code Lexer} object with a given text to be parsed into
	 * tokens.
	 * 
	 * @param text the source text to be tokenised.
	 */
	public Lexer(String text) {
		this(text, MyParser.buildBasicSyntax());
	}

	/*
	 * Most Recent Date: 3 Aug 2021-----------------------------------------------
	 * Most recent time created: 11:14:04--------------------------------------
	 */
	/**
	 * Throws a {@code UnsupportedOperationException} after doing nothing. This
	 * exception is not written to the mathaid error file.
	 * 
	 * @throws UnsupportedOperationException if called.
	 */
	@Override
	public final void remove() {
		new BaseException(ExceptionMessage.METHOD_NOT_IMPLEMENTED, new UnsupportedOperationException());
		throw new UnsupportedOperationException();// For java formality
	}

	/*
	 * Most Recent Date: 3 Aug 2021-----------------------------------------------
	 * Most recent time created: 11:15:31--------------------------------------
	 */
	/**
	 * Returns {@code true}.
	 * 
	 * @return {@code true}.
	 */
	@Override
	public boolean hasNext() {
		return true;
	}

	/*
	 * Most Recent Date: 3 Aug 2021-----------------------------------------------
	 * Most recent time created: 11:23:27--------------------------------------
	 */
	/**
	 * Queries this {@code Lexer} for the next
	 * {@link mathaid.calculator.base.parser.Token} to be consumed by the
	 * {@code PrattParser}.
	 * <p>
	 * This method will always return a token, albeit, if the string has been
	 * exhausted, then a special end-of-file token is returned. Thus this method
	 * will never throw a {@code NoSuchElementException}.
	 * </p>
	 * 
	 * @return the next {@code Token} represented by the source or a special
	 *         end-of-file token if the source has been full scanned.
	 */
	@Override
	public mathaid.calculator.base.parser.Token<String> next() {
		while (index < text.length()) {
			char c = text.charAt(index++);
			//we can check for the lambda '->' here
			if (cs.getPunctuatorsAndDelimiters().contains(c))
				return new Token(String.valueOf(c), cs.getType(c));
			else if (cs.getLetters().contains(c)) {
				int start = index - 1;
				while (index < text.length()) {
//					if (!cs.getLetters().contains(text.charAt(index)))
					if (!cs.getLetters().contains(c))
						break;
					index++;
				}

				String name = text.substring(start, index);
				return new Token(name, cs.getNameType());
			} else if (cs.getDigits().contains(c)) {
				int start = index - 1;
				while (index < text.length()) {
					char ch = text.charAt(index);
					/*
					 * if this lexer encounters a non-digit and that non-digit is not an exponent
					 * character (e because symja uses only java's double exponent operator) stop
					 * parsing the string and return the word as number
					 */
					if (ch != 'e' && ch != '.' && (!cs.getDigits().contains(ch)))
						// if the previous character is an exponent operator
						if (text.charAt(index - 1) == 'e' && (ch == '-' || ch == '+'))
							;
						else
							break;
					index++;
				}

				String name = text.substring(start, index);
				return new Token(name, cs.getNameType());
			}
			new IllegalTokenException(ExceptionMessage.CHAR_NOT_VALID, c);

		}
		return new Token("", CommonSyntax.BASIC_TYPES.get(CommonSyntax.EOF));
	}

}
