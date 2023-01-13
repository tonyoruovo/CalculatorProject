/**
 * 
 */
package mathaid.calculator.base.evaluator.parser;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import mathaid.ExceptionMessage;
import mathaid.IllegalStatusException;
import mathaid.IndexBeyondLimitException;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression.ExpressionParams;
import mathaid.calculator.base.evaluator.parser.parselet.Parselet;
import mathaid.calculator.base.parser.IllformedSyntaxException;
import mathaid.calculator.base.parser.expression.FunctionExpression;
import mathaid.calculator.base.parser.expression.GroupExpression;
import mathaid.calculator.base.parser.parselet.InfixParselet;
import mathaid.calculator.base.parser.parselet.PrefixParselet;
import mathaid.calculator.base.typeset.SegmentBuilder;

/*
 * Date: 4 Sep 2022----------------------------------------------------------- 
 * Time created: 08:21:27---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: CommonSyntax.java------------------------------------------------------ 
 * Class name: CommonSyntax------------------------------------------------ 
 */
/*
 * Date: 5 May 2021----------------------------------------------------------- 
 * Time created: 21:51:53---------------------------------------------------  
 * Package: com.etineakpopha.parser------------------------------------------------ 
 * Project: RemoteWork------------------------------------------------ 
 * File: CommonSyntax.java------------------------------------------------------ 
 * Class name: CommonSyntax------------------------------------------------ 
 */
/**
 * A {@code Syntax} that represents the type of syntax expected from
 * programming/computer languages. It contains final fields which help with
 * setting up {@code Type} objects that wraps strings. Users can use the
 * {@code PrattParser} class along with this one to enforce non-ambiguity in
 * parsing. This interface can also be used with the {@code Lexer} class where
 * non-ambiguity is also required. Note that all the {@code Type} object
 * returned by the methods of this interface are wrappers of strings.
 * <p>
 * This interface specifically deals with parsing strings and the syntax
 * surrounding such parsing. If users wish to parse other objects, a direct
 * extension/implementation of the {@code Syntax} interface may is advised.
 * Also, non- supported characters like escape characters, block opening/
 * closing character etc can be supported by sub-classes or sub-interfaces to
 * provide such functionality.
 * </p>
 * <p>
 * A {@link CommonSyntax.Builder builder} is provided with this interface to
 * help with building a {@code CommonSyntax}.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 *
 * @param <E> a type of {@code NameExpression}.
 * @param <P> A concrete {@code Parser} type which parses strings.
 */
public interface CommonSyntax<E extends EvaluatableExpression<A>, P extends Parser<String, SegmentBuilder, E, P, CommonSyntax<E, P, A>, A>, A extends ExpressionParams<A>>
		extends Syntax<String, SegmentBuilder, E, P, CommonSyntax<E, P, A>, A> {

	/**
	 * The {@code String} constant for the end-of-file token which can be used for
	 * retrieving the corresponding type from {@link #COMMON_TYPES} or
	 * {@link #BASIC_TYPES}.
	 */
	String EOF = "eof";
	/**
	 * The {@code String} constant for the plus operator (+) token which can be used
	 * for retrieving the corresponding type from {@link #COMMON_TYPES} or
	 * {@link #BASIC_TYPES}.
	 */
	String PLUS = "plus";
	/**
	 * The {@code String} constant for the minus operator (-) token which can be
	 * used for retrieving the corresponding type from {@link #COMMON_TYPES} or
	 * {@link #BASIC_TYPES}.
	 */
	String MINUS = "minus";
	/**
	 * The {@code String} constant for the asterisk operator (*) token which can be
	 * used for retrieving the corresponding type from {@link #COMMON_TYPES} or
	 * {@link #BASIC_TYPES}.
	 */
	String ASTERISK = "asterisk";
	/**
	 * The {@code String} constant for the forward slash operator (/) token which
	 * can be used for retrieving the corresponding type from {@link #COMMON_TYPES}
	 * or {@link #BASIC_TYPES}.
	 */
	String FORWARD_SLASH = "forwardSlash";
	/**
	 * The {@code String} constant for the caret symbol operator (^) token which can
	 * be used for retrieving the corresponding type from {@link #COMMON_TYPES} or
	 * {@link #BASIC_TYPES}.
	 */
	String CARET = "caret";
	/**
	 * The {@code String} constant for the comma token which can be used for
	 * retrieving the corresponding type from {@link #COMMON_TYPES} or
	 * {@link #BASIC_TYPES}.
	 */
	String COMMA = "comma";
	/**
	 * The {@code String} constant for the period token which can be used for
	 * retrieving the corresponding type from {@link #COMMON_TYPES} or
	 * {@link #BASIC_TYPES}.
	 */
	String PERIOD = "period";
	/**
	 * The {@code String} constant for the left parenthesis token which can be used
	 * for retrieving the corresponding type from {@link #COMMON_TYPES} or
	 * {@link #BASIC_TYPES}.
	 */
	String LEFT_PARENTHESIS = "leftParenthesis";
	/**
	 * The {@code String} constant for the right parenthesis token which can be used
	 * for retrieving the corresponding type from {@link #COMMON_TYPES} or
	 * {@link #BASIC_TYPES}.
	 */
	String RIGHT_PARENTHESIS = "rightParenthesis";
	/**
	 * The {@code String} constant for the left square bracket token which can be
	 * used for retrieving the corresponding type from {@link #COMMON_TYPES} or
	 * {@link #BASIC_TYPES}.
	 */
	String LEFT_BRACKET = "leftBracket";
	/**
	 * The {@code String} constant for the right square bracket token which can be
	 * used for retrieving the corresponding type from {@link #COMMON_TYPES} or
	 * {@link #BASIC_TYPES}.
	 */
	String RIGHT_BRACKET = "rightBracket";
	/**
	 * The {@code String} constant for the name token which can be used for
	 * retrieving the corresponding type from {@link #COMMON_TYPES} or
	 * {@link #BASIC_TYPES}.
	 */
	String NAME = "name";
	/**
	 * The {@code String} constant for the exclamation mark token which can be used
	 * for retrieving the corresponding type from {@link #COMMON_TYPES} or
	 * {@link #BASIC_TYPES}.
	 */
	String BANG = "bang";
	/**
	 * The {@code String} constant for the single quote token which can be used for
	 * retrieving the corresponding type from {@link #COMMON_TYPES} or
	 * {@link #BASIC_TYPES}.
	 */
	String QUOTE = "quote";

	/**
	 * The {@code String} constant for the right lambda "<code>-&gt;</code>" token
	 * which can be used for retrieving the corresponding type from
	 * {@link #COMMON_TYPES} or {@link #BASIC_TYPES}.
	 */
	String RIGHT_LAMBDA = "rightLambda";

	/**
	 * The {@code String} constant for the left brace "<code>{</code>" token which
	 * can be used for retrieving the corresponding type from {@link #COMMON_TYPES}
	 * or {@link #BASIC_TYPES}.
	 */
	String LEFT_BRACE = "leftBrace";
	/**
	 * The {@code String} constant for the right brace "<code>}</code>" token which
	 * can be used for retrieving the corresponding type from {@link #COMMON_TYPES}
	 * or {@link #BASIC_TYPES}.
	 */
	String RIGHT_BRACE = "rightBrace";
	/**
	 * The {@code String} constant for the left shift (java) "<code>&lt;&lt;</code>"
	 * token which can be used for retrieving the corresponding type from
	 * {@link #COMMON_TYPES} or {@link #BASIC_TYPES}.
	 */
	String LEFT_SHIFT = "leftShift";
	/**
	 * The {@code String} constant for the right shift (java)
	 * "<code>&gt;&gt;</code>" token which can be used for retrieving the
	 * corresponding type from {@link #COMMON_TYPES} or {@link #BASIC_TYPES}.
	 */
	String RIGHT_SHIFT = "rightShift";
	/*
	 * Date: 11 Dec 2022-----------------------------------------------------------
	 * Time created: 12:11:11---------------------------------------------------
	 */
	/**
	 * The {@code String} constant for the tilde "<code>~</code>" token which can be
	 * used for retrieving the corresponding type from {@link #COMMON_TYPES} or
	 * {@link #BASIC_TYPES}.
	 */
	String TILDE = "tilde";
	/**
	 * The {@code String} constant for the not "<code>¬</code>" token which can be
	 * used for retrieving the corresponding type from {@link #COMMON_TYPES} or
	 * {@link #BASIC_TYPES}.
	 */
	String NEGATION = "negation";
	/**
	 * The {@code String} constant for the logical or "<code>&#x2228;</code>" token
	 * which can be used for retrieving the corresponding type from
	 * {@link #COMMON_TYPES} or {@link #BASIC_TYPES}.
	 */
	String LOGICAL_OR = "logicOr";
	/**
	 * The {@code String} constant for the logical and "<code>&#x2227;</code>" token
	 * which can be used for retrieving the corresponding type from
	 * {@link #COMMON_TYPES} or {@link #BASIC_TYPES}.
	 */
	String LOGICAL_AND = "logicAnd";
	/**
	 * The {@code String} constant for the and "<code>&</code>" token which can be
	 * used for retrieving the corresponding type from {@link #COMMON_TYPES} or
	 * {@link #BASIC_TYPES}.
	 */
	String AND = "and";
	/**
	 * The {@code String} constant for the or "<code>|</code>" token which can be
	 * used for retrieving the corresponding type from {@link #COMMON_TYPES} or
	 * {@link #BASIC_TYPES}.
	 */
	String OR = "or";
	/**
	 * The {@code String} constant for the percent "<code>%</code>" token which can
	 * be used for retrieving the corresponding type from {@link #COMMON_TYPES} or
	 * {@link #BASIC_TYPES}.
	 */
	String PERCENT = "percent";
	/**
	 * A constant for a {@code Map} that defines all the {@code Type} needed for
	 * parsing strings in the {@code Calculator}. The following contains info about
	 * the precedence of the types:
	 * <table border="1">
	 * <tr>
	 * <th colspan="4" align="center">Map contents</th>
	 * </tr>
	 * <tr>
	 * <th>Type name</th>
	 * <th>Map key</th>
	 * <th>Integer precedence (Ascending order of importance)</th>
	 * <th>Notes</th>
	 * </tr>
	 * <tr>
	 * <td>N/A</td>
	 * <td>{@link #EOF eof}</td>
	 * <td>0 ({@link Precedence#LOWEST})</td>
	 * <td>The precedence and name are irrelevant because it's tokens will never be
	 * parsed, it is just for identification purposes.</td>
	 * </tr>
	 * <tr>
	 * <td>+</td>
	 * <td>{@link #PLUS plus}</td>
	 * <td>2 ({@link Precedence#INFIX_3})</td>
	 * </tr>
	 * <tr>
	 * <td>-</td>
	 * <td>{@link #MINUS minus}</td>
	 * <td>2 ({@link Precedence#INFIX_3})</td>
	 * </tr>
	 * <tr>
	 * <td>*</td>
	 * <td>{@link #ASTERISK asterisk}</td>
	 * <td>3 ({@link Precedence#INFIX_2})</td>
	 * </tr>
	 * <tr>
	 * <td>&sol;</td>
	 * <td>{@link #FORWARD_SLASH forwardSlash}</td>
	 * <td>3 ({@link Precedence#INFIX_3})</td>
	 * </tr>
	 * <tr>
	 * <td>^</td>
	 * <td>{@link #CARET caret}</td>
	 * <td>4 ({@link Precedence#INFIX_1})</td>
	 * <td>Has a slightly higher precedence than * and &sol; because it is will be
	 * parsed as a right associative infix operator, however in theory, it is the
	 * same precedence as both * and &sol; operators.</td>
	 * </tr>
	 * <tr>
	 * <td>,</td>
	 * <td>{@link #COMMA comma}</td>
	 * <td>10 ({@link Precedence#HIGHEST})</td>
	 * <td>The precedence is irrelevant because they will never be considered by the
	 * {@code PrattParser} for the {@code InternalEvaluator}. However other
	 * parselets may use it in other ways.</td>
	 * </tr>
	 * <tr>
	 * <td>(</td>
	 * <td>{@link #LEFT_PARENTHESIS leftParenthesis}</td>
	 * <td>10 ({@link Precedence#HIGHEST})</td>
	 * <td>Ditto</td>
	 * </tr>
	 * <tr>
	 * <td>)</td>
	 * <td>{@link #RIGHT_PARENTHESIS rightParenthesis}</td>
	 * <td>0 ({@link Precedence#LOWEST})</td>
	 * <td>Ditto</td>
	 * </tr>
	 * <tr>
	 * <td>[</td>
	 * <td>{@link #LEFT_BRACKET leftBracket}</td>
	 * <td>10 ({@link Precedence#HIGHEST})</td>
	 * <td>Ditto</td>
	 * </tr>
	 * <tr>
	 * <td>]</td>
	 * <td>{@link #RIGHT_BRACKET rightBracket}</td>
	 * <td>10 ({@link Precedence#HIGHEST})</td>
	 * <td>Ditto</td>
	 * </tr>
	 * <tr>
	 * <td>N/A</td>
	 * <td>{@link #NAME name}</td>
	 * <td>10 ({@link Precedence#HIGHEST})</td>
	 * <td>Ditto</td>
	 * </tr>
	 * <tr>
	 * <td>.</td>
	 * <td>{@link #PERIOD period}</td>
	 * <td>6 ({@link Precedence#PREFIX_1})</td>
	 * </tr>
	 * <tr>
	 * <td>!</td>
	 * <td>{@link #BANG bang}</td>
	 * <td>8 ({@link Precedence#POSTFIX_1})</td>
	 * </tr>
	 * <tr>
	 * <td>'</td>
	 * <td>{@link #QUOTE quote}</td>
	 * <td>7 ({@link Precedence#POSTFIX_2})</td>
	 * </tr>
	 * </table>
	 * <p>
	 * Every {@code Type} is a value of one of the {@code String} constants in this
	 * interface within this map.
	 * </p>
	 * <p>
	 * This provides support for precedence parsing.
	 * </p>
	 * <p>
	 * This {@code Map} is an unmodifiable {@code Map}.
	 * </p>
	 */
	static Map<String, Type<String>> COMMON_TYPES = getMyCommonTypes();
	/**
	 * A constant for an unmodifiable {@code Map} that defines all the {@code Type}
	 * needed for parsing strings in the {@code Calculator}.
	 * <p>
	 * Every {@code Type} in this {@code Map} is a value of one of the
	 * {@code String} constants in this interface.
	 * </p>
	 * <p>
	 * This provides support for non-precedence parsing, this means that all the
	 * tokens are regarded as having the same precedence and written to the output
	 * stack as soon as they go into the {@code PrattParser}. This is useful for
	 * users who may not want to change the order of computation, but want to
	 * display the token in a different notation other than source notation (i.e the
	 * notation in which it was originally sent into the {@code PrattParser}).
	 * </p>
	 */
	static Map<String, Type<String>> BASIC_TYPES = getMyBasicTypes();

	/*
	 * Date: 1 Aug 2021-----------------------------------------------------------
	 * Time created: 11:42:37--------------------------------------------
	 */
	/**
	 * Creates and populates a {@code Map} with {@code Type} objects whose keys are
	 * the {@code String} constants in this interface, then returns the {@code Map}
	 * as an unmodifiable {@code Map}.
	 * 
	 * @return an unmodifiable {@code Map} with keys that are the {@code String}
	 *         constants in this class and values that are corresponding
	 *         {@code Type} objects.
	 */
	private static Map<String, Type<String>> getMyCommonTypes() {
		Map<String, Type<String>> m = new HashMap<>();
		m.put(EOF, new Type<>("", Precedence.LOWEST));
		m.put(PLUS, new Type<>("+", Precedence.INFIX_3));
		m.put(MINUS, new Type<>("-", Precedence.INFIX_3));
		m.put(ASTERISK, new Type<>("*", Precedence.INFIX_2));
		m.put(FORWARD_SLASH, new Type<>("/", Precedence.INFIX_2));
		m.put(CARET, new Type<>("^", Precedence.INFIX_1));
		m.put(COMMA, new Type<>(",", Precedence.HIGHEST));
		m.put(LEFT_PARENTHESIS, new Type<>("(", Precedence.HIGHEST));
		m.put(RIGHT_PARENTHESIS, new Type<>(")", Precedence.LOWEST));
		m.put(LEFT_BRACKET, new Type<>("[", Precedence.HIGHEST));
		m.put(RIGHT_BRACKET, new Type<>("]", Precedence.LOWEST));
		m.put(NAME, new Type<>("", Precedence.HIGHEST));
		m.put(PERIOD, new Type<>(".", Precedence.PREFIX_1));
		m.put(QUOTE, new Type<>("'", Precedence.POSTFIX_2));
		// for lambdas
		m.put(RIGHT_LAMBDA, new Type<>("->", Precedence.HIGHEST));
		m.put(LEFT_BRACE, new Type<>("{", Precedence.HIGHEST));
		m.put(RIGHT_BRACE, new Type<>("}", Precedence.HIGHEST));
		// for shifts
		m.put(LEFT_SHIFT, new Type<>("<<", Precedence.BASIC_CARET_LOWEST));
		m.put(RIGHT_SHIFT, new Type<>(">>", Precedence.BASIC_CARET_LOWEST));
		// bit operators
		m.put(BANG, new Type<>("!", Precedence.PREFIX_1));
		m.put(TILDE, new Type<>("~", Precedence.PREFIX_1));
		m.put(NEGATION, new Type<>("¬", Precedence.PREFIX_1));
		// binary
		m.put(LOGICAL_OR, new Type<>("\u2228", Precedence.INFIX_1));
		m.put(OR, new Type<>("|", Precedence.INFIX_1));
		m.put(LOGICAL_AND, new Type<>("\u2227", Precedence.INFIX_2));
		m.put(AND, new Type<>("&", Precedence.INFIX_2));
		m.put(PERCENT, new Type<>("%", Precedence.INFIX_2));
		return Collections.unmodifiableMap(m);
	}

	/*
	 * Date: 1 Aug 2021-----------------------------------------------------------
	 * Time created: 12:39:37--------------------------------------------
	 */
	/**
	 * Creates and populates a {@code Map} with {@code Type} objects whose keys are
	 * the {@code String} constants in this interface, then returns the {@code Map}
	 * as an unmodifiable {@code Map}.
	 * <p>
	 * <b>Note:</b><i> The type object returned is a non-precedence affected type.
	 * What this means is that when the types gotten from here are used to create
	 * tokens, the parser parsing these tokens will not regard precedence and will
	 * treat all the tokens as having the same precedence</i>.
	 * </p>
	 * 
	 * @return an unmodifiable {@code Map} with keys that are the {@code String}
	 *         constants in this class and values that are corresponding
	 *         {@code Type} objects.
	 */
	private static Map<String, Type<String>> getMyBasicTypes() {
		Map<String, Type<String>> m = new HashMap<>();
		m.put(EOF, new Type<>("", Precedence.LOWEST));
		m.put(PLUS, new Type<>("+", Precedence.HIGHEST));
		m.put(MINUS, new Type<>("-", Precedence.HIGHEST));
		m.put(ASTERISK, new Type<>("*", Precedence.HIGHEST));
		m.put(FORWARD_SLASH, new Type<>("/", Precedence.HIGHEST));
		m.put(CARET, new Type<>("^", Precedence.BASIC_CARET_HIGHEST));
		m.put(COMMA, new Type<>(",", Precedence.HIGHEST));
		m.put(LEFT_PARENTHESIS, new Type<>("(", Precedence.HIGHEST));
		m.put(RIGHT_PARENTHESIS, new Type<>(")", Precedence.HIGHEST));
		m.put(LEFT_BRACKET, new Type<>("[", Precedence.HIGHEST));
		m.put(RIGHT_BRACKET, new Type<>("]", Precedence.LOWEST));
		m.put(NAME, new Type<>("", Precedence.HIGHEST));
		m.put(NAME, new Type<>("", Precedence.HIGHEST));
		m.put(PERIOD, new Type<>(".", Precedence.HIGHEST));
		m.put(BANG, new Type<>("!", Precedence.HIGHEST));
		m.put(QUOTE, new Type<>("'", Precedence.HIGHEST));
		// for lambdas
		m.put(RIGHT_LAMBDA, new Type<>("->", Precedence.HIGHEST));
		m.put(LEFT_BRACE, new Type<>("{", Precedence.HIGHEST));
		m.put(RIGHT_BRACE, new Type<>("}", Precedence.HIGHEST));
		// for shifts
		m.put(LEFT_SHIFT, new Type<>("<<", Precedence.HIGHEST));
		m.put(RIGHT_SHIFT, new Type<>(">>", Precedence.HIGHEST));
		// bit operators
		m.put(TILDE, new Type<>("~", Precedence.HIGHEST));
		m.put(NEGATION, new Type<>("¬", Precedence.HIGHEST));
		// binary
		m.put(LOGICAL_OR, new Type<>("\u2228", Precedence.HIGHEST));
		m.put(OR, new Type<>("|", Precedence.HIGHEST));
		m.put(LOGICAL_AND, new Type<>("\u2227", Precedence.HIGHEST));
		m.put(AND, new Type<>("&", Precedence.HIGHEST));
		m.put(PERCENT, new Type<>("%", Precedence.HIGHEST));
		return Collections.unmodifiableMap(m);
	}

	/*
	 * Date: 1 Aug 2021-----------------------------------------------------------
	 * Time created: 12:42:29---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base.parser-----------------------------------------------
	 * - Project: LatestPoject2------------------------------------------------
	 * File: CommonSyntax.java------------------------------------------------------
	 * Class name: Builder------------------------------------------------
	 */
	/**
	 * A class implementing the ubiquitous builder design pattern for building
	 * instances of {@code CommonSyntax} from values configured by the setter
	 * methods of this class. The builder design pattern is an object oriented
	 * design pattern that contributes to decoupling and enables the creation of
	 * object (whose fields and properties may be too numerous to lay out as
	 * parameters for a constructor, or whose creation requires an initialisation of
	 * certain fields among all supported fields which may confuse users of said
	 * class) from mutator methods.
	 * <p>
	 * It comes with 2 constructors, a no-argument one for building basic syntax
	 * that uses just the hindi-arabic numerals 0-9 to represent numbers and another
	 * boolean-argument constructor for making use of hex digits and other user
	 * specified digits.
	 * </p>
	 * <p>
	 * Every builder can only be used to build 1 type of {@code CommonSyntax}, so
	 * that one cannot have a builder that can build a syntax that supports only
	 * hindi-arabic numeral and at the same reuse the same builder object to build a
	 * syntax that supports user specified numerals.
	 * </p>
	 * <p>
	 * When certain methods in this class is called, an exception may be thrown to
	 * indicate the method was called too early. To avoid this please read the
	 * documentation for each method carefully
	 * </p>
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 *
	 * @param <E> The type of {@code NameExpression} to be used with the
	 *            {@code Syntax} object that will be built.
	 */
	static class Builder<E extends EvaluatableExpression<P>, P extends ExpressionParams<P>> {
		/**
		 * The internal syntax object that is empty and unusable at the initialisation
		 * of this {@code Builder}. It's field are gradually filled as methods in this
		 * class are called, and when {@link #build()} is called it is the object that
		 * will be returned.
		 */
		private S<E, P> syntax;
		/**
		 * Check for using regular hindi-arabic digits (0-9) or whether user defined
		 * digits should be allowed for the {@code CommonSyntax}.
		 */
		private final boolean useNonNumberDigits;

		/*
		 * Date: 1 Aug 2021-----------------------------------------------------------
		 * Time created: 12:54:38---------------------------------------------------
		 * Package:
		 * mathaid.calculator.base.parser-----------------------------------------------
		 * - Project: LatestPoject2------------------------------------------------
		 * File: CommonSyntax.java------------------------------------------------------
		 * Class name: S------------------------------------------------
		 */
		/**
		 * A class providing base implementation of a {@code CommonSyntax} object.
		 * 
		 * @author Oruovo Anthony Etineakpopha
		 * @email tonyoruovo@gmail.com
		 *
		 * @param <E> The type of {@code NameExpression} to be used with the
		 *            {@code CommonSyntax} object that will be built.
		 */
		private static class S<E extends EvaluatableExpression<P>, P extends ExpressionParams<P>>
				implements CommonSyntax<E, PrattParser<E, P>, P> {
			/**
			 * A map to hold all the defined {@link InfixParselet}. These are parselets that
			 * will be used for parsing infix operators and tokens.
			 */
			Map<Type<String>, Parselet<String, SegmentBuilder, E, PrattParser<E, P>, CommonSyntax<E, PrattParser<E, P>, P>, P>> infixParselet = new HashMap<>();
			/**
			 * A map to hold all the defined {@link PrefixParselet}. These are parselets
			 * that will be used for parsing prefix operators and tokens
			 */
			Map<Type<String>, Parselet<String, SegmentBuilder, E, PrattParser<E, P>, CommonSyntax<E, PrattParser<E, P>, P>, P>> prefixParselet = new HashMap<>();
			/**
			 * Holds all the valid white spaces supported by this object
			 */
			Set<Character> whitespaces;
			/**
			 * A {@code Set} to hold all the valid punctuators/symbols supported by this
			 * object. An example of a puntuator may be operators, separators, delimiters or
			 * any of `~¬!"£$%^&*(){}:@<>?[];',/-=~#|\.
			 */
			Set<Character> punctuators;
			/**
			 * A {@code Set} to hold all the valid characters that can be used to form
			 * identifiers or {@link Name name expressions}.
			 */
			Set<Character> letters;
			/**
			 * The {@code Set} to hold all the characters that can used to form numbers.
			 */
			Set<Character> digits;
			/**
			 * A {@code Set} to hold all the {@code String} that are exclusively
			 * {@link Syntax#getKeywords() keywords}.
			 */
			Set<String> keywords;
			/**
			 * A {@code Set} to hold all the valid token types. These types are string
			 * wrappers.
			 */
			Set<Type<String>> types = new HashSet<>();
			/**
			 * An array of length 2 to hold {@link Character#isMirrored(char) mirrored}
			 * characters that creates {@link GroupExpression} objects. For example, the
			 * result given by the expression {@code 2+5*3^2} will be different from the one
			 * given by <code>(2+5)*(3^2)</code> because of the "precedence director"
			 * represented by the parenthesis i.e a precedence director is a mirrored
			 * character that changes the order of operation of an expression.
			 * <p>
			 * Some syntax may use {@code x(y+2)} to mean {@code x} as a function of
			 * <code>y+2</code> this is meant to clarify those ambiguities. So any pair of
			 * character that is found in this array will regarded as the open (index 0) and
			 * close (index 1) of parenthesised expression. So that if "[]" is the
			 * precedence director (which mean to specify x as a factor of y+2 one has to
			 * write x[y+2]), the index 0 contains "[" and index 1 contains "]".
			 * </p>
			 */
			Character[] precedenceDirectors;
			/**
			 * An array of length 2 to hold {@link Character#isMirrored(char) mirrored}
			 * characters that creates {@link FunctionExpression} objects.
			 * <p>
			 * Some syntax may use {@code sin(x)} to mean {@code sin*x} this is meant to
			 * clarify those ambiguities. So any pair of character that is found in this
			 * array will regarded as the open (index 0) and close (index 1) of a function.
			 * So that if "[]" is the function delimiter (which mean to specify sin as a
			 * function one has to write sin[x]), the index 0 contains "[" and index 1
			 * contains "]".
			 * </p>
			 */
			Character[] functionDelimiters;
			/**
			 * The argument separator within a function. This character is used to separate
			 * arguments for example: in the function {@code tan(x, y)}, the comma character
			 * "," is used to separate the first argument from the second.
			 */
			Character argDelimiter;
			/**
			 * The default type for all name tokens. Note that for this type to be valid it
			 * must be a member {@link #types}.
			 */
			Type<String> nameType;
			/**
			 * An array of length 2 to hold {@link Character#isMirrored(char) mirrored}
			 * characters that creates {@link Array} objects.
			 */
			Character[] arrayDelimiters;

			/*
			 * Most Recent Date: 1 Aug 2021-----------------------------------------------
			 * Most recent time created: 13:40:45--------------------------------------
			 */
			/**
			 * Returns the specified type for the given character in this syntax or returns
			 * null if there is no defined type for the specified character.
			 * 
			 * @param c the character whose type within this syntax is to be returned.
			 * @return the corresponding {@code Type} for the provided {@code Character}
			 */
			@Override
			public Type<String> getType(Character c) {
				Type<String> t;
				for (Iterator<Type<String>> i = types.iterator(); i.hasNext();) {
					t = i.next();
					if ((!t.getName().isEmpty()) && t.getName().toString().charAt(0) == c)
						return t;
				}
				return null;
			}

			/*
			 * Most Recent Date: 1 Aug 2021-----------------------------------------------
			 * Most recent time created: 13:43:35--------------------------------------
			 */
			/**
			 * Gets the {@code Set} that holds all the valid token types. These types are
			 * string wrappers.
			 * 
			 * @return {@inheritDoc}
			 */
			@Override
			public Set<Type<String>> getTypes() {
				return types;
			}

			/*
			 * Most Recent Date: 1 Aug 2021-----------------------------------------------
			 * Most recent time created: 13:43:35--------------------------------------
			 */
			/**
			 * Gets the {@code Set} that holds all the {@code String} that are exclusively
			 * {@link Syntax#getKeywords() keywords}.
			 * 
			 * @return {@inheritDoc}
			 */
			@Override
			public Set<String> getKeywords() {
				return keywords;
			}

			/*
			 * Most Recent Date: 1 Aug 2021-----------------------------------------------
			 * Most recent time created: 13:47:01--------------------------------------
			 */
			/**
			 * Gets the {@code Set} that holds all the characters that can used to form
			 * numbers.
			 * 
			 * @return {@inheritDoc}
			 */
			@Override
			public Set<Character> getDigits() {
				return digits;
			}

			/*
			 * Most Recent Date: 1 Aug 2021-----------------------------------------------
			 * Most recent time created: 13:48:03--------------------------------------
			 */
			/**
			 * Gets the {@code Set} that holds all the valid characters that can be used to
			 * form identifiers or {@link Name name expressions}.
			 * 
			 * @return {@inheritDoc}
			 */
			@Override
			public Set<Character> getLetters() {
				return letters;
			}

			/*
			 * Most Recent Date: 1 Aug 2021-----------------------------------------------
			 * Most recent time created: 13:50:25--------------------------------------
			 */
			/**
			 * Returns the {@code Set} that hold all the valid punctuators/symbols supported
			 * by this object. An example of a puntuator may be operators, separators,
			 * delimiters or any of `~¬!"£$%^&*(){}:@<>?[];',/-=~#|\.
			 * 
			 * @return {@inheritDoc}
			 */
			@Override
			public Set<Character> getPunctuatorsAndDelimiters() {
				return punctuators;
			}

			/*
			 * Most Recent Date: 1 Aug 2021-----------------------------------------------
			 * Most recent time created: 13:52:58--------------------------------------
			 */
			/**
			 * {@inheritDoc}
			 * 
			 * @return {@inheritDoc}
			 */
			@Override
			public Set<Character> getWhitespaces() {
				return whitespaces;
			}

			/*
			 * Most Recent Date: 1 Aug 2021-----------------------------------------------
			 * Most recent time created: 13:53:29--------------------------------------
			 */
			/**
			 * Returns a {@code Parselet} of string, {@code NameExpression} and
			 * {@code PrattParser} can parse the specified {@code Type}. This is a parselets
			 * that can be used for parsing infix operators and tokens.
			 * 
			 * @param type the type of parselet to return. This must correspond with an
			 *             infix type. Please see the parselet api for further information.
			 * @return a{@code Parselet} capable of correctly parsing the given
			 *         {@code Type}.
			 */
			@Override
			public Parselet<String, SegmentBuilder, E, PrattParser<E, P>, CommonSyntax<E, PrattParser<E, P>, P>, P> getInfixParselet(
					Type<String> type) {
				return infixParselet.get(type);
			}

			/*
			 * Most Recent Date: 1 Aug 2021-----------------------------------------------
			 * Most recent time created: 13:53:29--------------------------------------
			 */
			/**
			 * Returns a {@code Parselet} of string, {@code NameExpression} and
			 * {@code PrattParser} can parse the specified {@code Type}. This is a parselets
			 * that can be used for parsing prefix operators and tokens.
			 * 
			 * @param type the type of parselet to return. This must correspond with a
			 *             prefix type. Please see the parselet api for further information.
			 * @return a{@code Parselet} capable of correctly parsing the given
			 *         {@code Type}.
			 */
			@Override
			public Parselet<String, SegmentBuilder, E, PrattParser<E, P>, CommonSyntax<E, PrattParser<E, P>, P>, P> getPrefixParselet(
					Type<String> type) {
				return prefixParselet.get(type);
			}

			/*
			 * Most Recent Date: 1 Aug 2021-----------------------------------------------
			 * Most recent time created: 14:03:38--------------------------------------
			 */
			/**
			 * Returns the character used by this {@code CommonSyntax} to represent the
			 * beginning of a function's argument.
			 * <p>
			 * Some syntax may use {@code sin(x)} to mean {@code sin*x} this is meant to
			 * clarify those ambiguities. So if "[]" is the function delimiter (which mean
			 * to specify sin as a function one has to write sin[x]), the function open will
			 * be "[".
			 * </p>
			 * 
			 * @return a {@code Character} that is used for starting argument(s) to a
			 *         function.
			 */
			@Override
			public Character functionOpen() {
				return functionDelimiters[0];
			}

			/*
			 * Most Recent Date: 1 Aug 2021-----------------------------------------------
			 * Most recent time created: 14:12:24--------------------------------------
			 */
			/**
			 * Returns the first pair of characters used for specifying the order of
			 * precedence in an expression. For example, the result given by the expression
			 * {@code 2+5*3^2} will be different from the one given by
			 * <code>(2+5)*(3^2)</code> because of the "precedence director" represented by
			 * the parenthesis i.e a precedence director is a
			 * {@link Character#isMirrored(char) mirrored} character that changes the order
			 * of operation of an expression.
			 * <p>
			 * Some syntax may use {@code x(y+2)} to mean {@code x} as a function of
			 * <code>y+2</code> this is meant to clarify those ambiguities. So any character
			 * returned by this method will be regarded as the opening of a grouped
			 * expression. So that if "[]" is the precedence director (which mean to specify
			 * x as a factor of y+2 one has to write x[y+2]), opening is "[".
			 * </p>
			 * 
			 * @return the {@code Character} that is used for starting grouped expressions.
			 */
			@Override
			public Character precedenceDirector1() {
				return precedenceDirectors[0];
			}

			/*
			 * Most Recent Date: 1 Aug 2021-----------------------------------------------
			 * Most recent time created: 14:07:30--------------------------------------
			 */
			/**
			 * Returns the character used by this {@code CommonSyntax} to represent the end
			 * of a function's argument.
			 * <p>
			 * Some syntax may use {@code sin(x)} to mean {@code sin*x} this is meant to
			 * clarify those ambiguities. So if "[]" is the function delimiter (which mean
			 * to specify sin as a function one has to write sin[x]), the function close
			 * will be "]".
			 * </p>
			 * 
			 * @return a {@code Character} that is used for ending argument(s) to a
			 *         function.
			 */
			@Override
			public Character functionClose() {
				return functionDelimiters[1];
			}

			/*
			 * Most Recent Date: 1 Aug 2021-----------------------------------------------
			 * Most recent time created: 14:12:24--------------------------------------
			 */
			/**
			 * Returns the second pair of characters used for specifying the order of
			 * precedence in an expression. For example, the result given by the expression
			 * {@code 2+5*3^2} will be different from the one given by
			 * <code>(2+5)*(3^2)</code> because of the "precedence director" represented by
			 * the parenthesis i.e a precedence director is a
			 * {@link Character#isMirrored(char) mirrored} character that changes the order
			 * of operation of an expression.
			 * <p>
			 * Some syntax may use {@code x(y+2)} to mean {@code x} as a function of
			 * <code>y+2</code> this is meant to clarify those ambiguities. So any character
			 * returned by this method will be regarded as the end of a grouped expression.
			 * So that if "[]" is the precedence director (which mean to specify x as a
			 * factor of y+2 one has to write x[y+2]), end is "[".
			 * </p>
			 * 
			 * @return the {@code Character} that is used for ending grouped expressions.
			 */
			@Override
			public Character precedenceDirector2() {
				return precedenceDirectors[1];
			}

			/*
			 * Most Recent Date: 1 Aug 2021-----------------------------------------------
			 * Most recent time created: 14:19:43--------------------------------------
			 */
			/**
			 * Returns the argument separator within a function. This character is used to
			 * separate arguments for example: in the function {@code tan(x, y)}, the comma
			 * character "," is used to separate the first argument from the second.
			 * 
			 * @return a {@code Character} used for delimiting arguments within s function.
			 */
			@Override
			public Character functionArgDelimiter() {
				return argDelimiter;
			}

			/*
			 * Most Recent Date: 1 Aug 2021-----------------------------------------------
			 * Most recent time created: 14:21:24--------------------------------------
			 */
			/**
			 * Gets the default {@code Type} for all name tokens. Note that for this
			 * {@code Type} to be valid it must be a member {@link #getTypes()}.
			 * 
			 * @return the {@code Type} object specifically used for tokens representing
			 *         identifiers.
			 */
			@Override
			public Type<String> getNameType() {
				return nameType;
			}

			/*
			 * Date: 16 Sep 2022-----------------------------------------------------------
			 * Time created: 01:03:23--------------------------------------------
			 */
			/**
			 * Gets the character used in the syntax for the beginning of an array
			 * expression. For example, the array <code>{1,2,3,4,5}</code> has a left brace
			 * as it's opening character, this method will be expected to return a left
			 * brace for this syntax.
			 * 
			 * @return a character used as a the marker for the start of array.
			 */
			@Override
			public Character getArrayOpen() {
				return arrayDelimiters[0];
			}

			/*
			 * Date: 16 Sep 2022-----------------------------------------------------------
			 * Time created: 01:03:23--------------------------------------------
			 */
			/**
			 * Gets the character used in the syntax for the end of an array expression. For
			 * example, the array <code>{1,2,3,4,5}</code> has a right brace as it's end
			 * character, this method will be expected to return a right brace for this
			 * syntax.
			 * 
			 * @return a character used as a the marker for the end of array.
			 */
			@Override
			public Character getArrayClose() {
				return arrayDelimiters[1];
			}

			/*
			 * Date: 8 Aug 2021-----------------------------------------------------------
			 * Time created: 09:37:29---------------------------------------------------
			 */
			/**
			 * Sole constructor.
			 */
			public S() {
			}
		}

		/*
		 * Date: 1 Aug 2021-----------------------------------------------------------
		 * Time created: 14:39:13---------------------------------------------------
		 */
		/**
		 * Constructs a builder that can build a {@code CommonSyntax}. Using this
		 * constructor will prohibit users from specifying digits outside the range of
		 * 0-9.
		 */
		public Builder() {
			this(false);
		}

		/*
		 * Date: 1 Aug 2021-----------------------------------------------------------
		 * Time created: 14:39:18---------------------------------------------------
		 */
		/**
		 * Initialises the builder with a check to specify whether to use user specified
		 * numerals or the ubiquitous hindi-arabic numerals.
		 * 
		 * @param useNonNumberDigits {@code true} for user specified digits, and
		 *                           {@code false} for otherwise. <b>Note:</b> This only
		 *                           concerns integers and digit recognition and does
		 *                           not cover radix points, signs and exponent
		 *                           notations.
		 */
		public Builder(final boolean useNonNumberDigits) {
			syntax = new S<>();
			this.useNonNumberDigits = useNonNumberDigits;
		}

		/*
		 * Date: 1 Aug 2021-----------------------------------------------------------
		 * Time created: 15:10:11--------------------------------------------
		 */
		/**
		 * Builds and returns a {@code CommonSyntax} object that correspond with the
		 * elements being used as arguments in the methods of this {@code Builder}. Any
		 * subsequent changes to {@code this} does not affect the object returned. If
		 * none of the methods of this class all been called prior to this method, then
		 * the {@code Syntax} object returned may be unusable.
		 * 
		 * @return a {@code CommonSyntax}.
		 */
		public CommonSyntax<E, PrattParser<E, P>, P> build() {
			return syntax;
		}

		/*
		 * Date: 1 Aug 2021-----------------------------------------------------------
		 * Time created: 15:20:09--------------------------------------------
		 */
		/**
		 * Registers the specified input with the given infix parselet.
		 * 
		 * @param type          the type of parselet to be registered.
		 * @param infixParselet a parselet which can be used for parsing infix tokens.
		 * @return {@code this} with the given parselet registered to the corresponding
		 *         internal syntax.
		 * @throws RuntimeException specifically
		 *                          {@link java.lang.IllegalAgumentException} if this
		 *                          method is called before
		 *                          {@link #registerTypes(Collection)} or
		 *                          {@link #registerType(Type)} or the specified type is
		 *                          not compatible with the syntax the user is trying to
		 *                          build.
		 */
		public Builder<E, P> registerInfixParselet(Type<String> type,
				Parselet<String, SegmentBuilder, E, PrattParser<E, P>, CommonSyntax<E, PrattParser<E, P>, P>, P> infixParselet) {
			if (!syntax.types.contains(type))
//				throw new IllegalArgumentException("Unknown type detected");
				new IllformedSyntaxException(ExceptionMessage.UNKNOWN_TYPE_DETECTED);
			this.syntax.infixParselet.put(type, infixParselet);
			return this;
		}

		/*
		 * Date: 1 Aug 2021-----------------------------------------------------------
		 * Time created: 15:39:44--------------------------------------------
		 */
		/**
		 * Registers the specified input with the given prefix parselet.
		 * 
		 * @param type           the type of parselet to be registered.
		 * @param prefixParselet a parselet which can be used for parsing prefix tokens.
		 * @return {@code this} with the given parselet registered to the corresponding
		 *         internal syntax.
		 * @throws RuntimeException specifically
		 *                          {@link java.lang.IllegalAgumentException} if this
		 *                          method is called before
		 *                          {@link #registerTypes(Collection)} or
		 *                          {@link #registerType(Type)} or the specified type is
		 *                          not compatible with the syntax the user is trying to
		 *                          build.
		 */
		public Builder<E, P> registerPrefixParselet(Type<String> type,
				Parselet<String, SegmentBuilder, E, PrattParser<E, P>, CommonSyntax<E, PrattParser<E, P>, P>, P> prefixParselet) {
			if (!syntax.types.contains(type))
				new IllformedSyntaxException(ExceptionMessage.UNKNOWN_TYPE_DETECTED);
			this.syntax.prefixParselet.put(type, prefixParselet);
			return this;
		}

		/*
		 * Date: 1 Aug 2021-----------------------------------------------------------
		 * Time created: 15:42:20--------------------------------------------
		 */
		/**
		 * Registers the given white space character. White spaces are used by syntaxes
		 * to separate identifiers and tokens from one another. A white space also
		 * denotes distinctiveness in tokens, for example in java's {@code Scanner}
		 * class, white space is used for separating tokens. If the argument is not a
		 * {@link Character#isWhitespace(char) whitespace}, an exception will be thrown.
		 * 
		 * @param whitespace a character which is regarded as a white space.
		 * @return {@code this} with the given character registered into the syntax
		 *         being built.
		 * @throws RuntimeException specifically
		 *                          {@link java.lang.IllegalAgumentException} if
		 *                          {@link Character#isWhitespace(char)} returns
		 *                          {@code true} for the argument.
		 */
		public Builder<E, P> registerWhiteSpace(Character whitespace) {
			if (!Character.isWhitespace(whitespace))
				new IllformedSyntaxException(ExceptionMessage.CHAR_NOT_WHITESPACE, whitespace);
			if (syntax.whitespaces == null)
				syntax.whitespaces = new HashSet<>();
			syntax.whitespaces.add(whitespace);
			return this;
		}

		/*
		 * Date: 3 Aug 2021-----------------------------------------------------------
		 * Time created: 02:40:13--------------------------------------------
		 */
		/**
		 * Registers the given character as a punctuator or delimiter (returned by
		 * {@link CommonSyntax#getPunctuatorsAndDelimiters()}). A punctuator and/or
		 * delimiter are tokens represented by operators and symbols. Operators can be
		 * one of those characters used by for example the JRE (+-/*|&^) for
		 * mathematical calculations. Delimiters can be one of those characters used by
		 * for example the JRE (.,:;'"<>{}[]()) for as general separators. If the
		 * argument is a {@link Character#isWhitespace(char) whitespace},
		 * {@link Character#isAlphabetic(int) alphabetic}, or
		 * {@link Character#isDigit(char) digit} an exception will be thrown.
		 * 
		 * @param c a character which is not a white space, alphabetic or digit
		 *          (decimal).
		 * @return {@code this} with the given character registered into the syntax
		 *         being built.
		 * @throws RuntimeException specifically
		 *                          {@link java.lang.IllegalAgumentException} if the
		 *                          argument is a digit (decimal), white space or
		 *                          alphabetic (A-Z, a-z).
		 */
		public Builder<E, P> registerPunctuatorAndDelimeter(Character c) {
			if (Character.isWhitespace(c) || Character.isAlphabetic(c) || Character.isDigit(c))
				new IllformedSyntaxException(ExceptionMessage.CHAR_NOT_PUNC_OR_DELIM, c);
			if (syntax.punctuators == null)
				syntax.punctuators = new HashSet<>();
			syntax.punctuators.add(c);
			return this;
		}

		/*
		 * Date: 3 Aug 2021-----------------------------------------------------------
		 * Time created: 02:57:13--------------------------------------------
		 */
		/**
		 * Registers the given character as a letter that can be used as an
		 * identifier/variable name (returned by {@link CommonSyntax#getLetters()}). If
		 * the argument is not a member of the latin alphabet, an exception will be
		 * thrown.
		 * 
		 * @param letter a character which is a letter according to the latin alphabet.
		 * @return {@code this} with the given character registered into the syntax
		 *         being built.
		 * @throws RuntimeException specifically
		 *                          {@link java.lang.IllegalAgumentException} if
		 *                          {@code Character.isLetter(char)} returns
		 *                          {@code false}.
		 */
		public Builder<E, P> registerLetter(Character letter) {
			if (!Character.isLetter(letter))
				new IllformedSyntaxException(ExceptionMessage.CHAR_NOT_LETTER, letter);
			if (syntax.letters == null)
				syntax.letters = new HashSet<>();
			syntax.letters.add(letter);
			return this;
		}

		/*
		 * Date: 3 Aug 2021-----------------------------------------------------------
		 * Time created: 03:15:22--------------------------------------------
		 */
		/**
		 * Registers the given character as a digit that can be used to form numbers
		 * (returned by {@link CommonSyntax#getDigits()}). If the boolean argument
		 * constructor was used to create this {@code Builder} then any character may be
		 * used as argument (however users must take care not use a character specified
		 * as having a different function elsewhere), otherwise only digits that fall
		 * into the range 0-9 (inclusive) are allowed as a valid argument or an
		 * exception will be thrown.
		 * 
		 * @param digit a character.
		 * @return {@code this} with the given character registered into the syntax
		 *         being built.
		 * @throws RuntimeException specifically
		 *                          {@link java.lang.IllegalAgumentException} if
		 *                          {@code Character.isDigit(char)} returns
		 *                          {@code false} and the no-arg constructor was used to
		 *                          instantiate this object.
		 */
		public Builder<E, P> registerDigit(Character digit) {
			if (!Character.isDigit(digit))
				if (!useNonNumberDigits)
					new IllformedSyntaxException(ExceptionMessage.CHAR_NOT_DIGIT, digit);
			if (syntax.digits == null)
				syntax.digits = new HashSet<>();
			syntax.digits.add(digit);
			return this;
		}

		/*
		 * Date: 3 Aug 2021-----------------------------------------------------------
		 * Time created: 03:27:20--------------------------------------------
		 */
		/**
		 * Registers the given {@code String} as a keyword within the
		 * {@code CommonSyntax} to be created (returned by
		 * {@link CommonSyntax#getKeywords()}).
		 * 
		 * @param keyword a string which whose sequence cannot be used for naming
		 *                identifier/variables.
		 * @return {@code this} with the given character registered into the syntax
		 *         being built.
		 */
		public Builder<E, P> registerKeyword(String keyword) {
			if (syntax.keywords == null)
				syntax.keywords = new HashSet<>();
			syntax.keywords.add(keyword);
			return this;
		}

		/*
		 * Date: 3 Aug 2021-----------------------------------------------------------
		 * Time created: 03:30:53--------------------------------------------
		 */
		/**
		 * Registers a type and returns {@code this}.
		 * 
		 * @param type the {@code Type} - which wraps a string - to be registered.
		 * @return {@code this} with the given {@code Type} registered into the syntax
		 *         being built.
		 */
		public Builder<E, P> registerType(Type<String> type) {
			syntax.types.add(type);
			return this;
		}

		/*
		 * Date: 3 Aug 2021-----------------------------------------------------------
		 * Time created: 03:33:55--------------------------------------------
		 */
		/**
		 * Registers all the elements of the given {@code Collection} into the
		 * {@code Syntax} being built.
		 * 
		 * @param types a {@code Collection} of {@code Type} objects.
		 * @return {@code this} with all the given {@code Type}s registered into the
		 *         syntax being built.
		 */
		public Builder<E, P> registerTypes(Collection<Type<String>> types) {
			syntax.types.addAll(types);
			return this;
		}

		/*
		 * Date: 3 Aug 2021-----------------------------------------------------------
		 * Time created: 03:36:34--------------------------------------------
		 */
		/**
		 * Registers an array of 2 elements with the first index containing a character
		 * that will represent the opening parenthesis of a functions (returned by
		 * {@link CommonSyntax#functionOpen()}) and the second index representing the
		 * closing parenthesis (returned by {@link CommonSyntax#functionClose()}) of a
		 * function and returns {@code this}. If this method was hitherto called with a
		 * valid non-null argument or the input array length is not 2 then an exception
		 * will be thrown. This method may only be called once.
		 * <p>
		 * Some syntax may use {@code sin(x)} to mean {@code sin*x} this is meant to
		 * clarify those ambiguities. So any pair of characters that is found in this
		 * array will be regarded as the open (index 0) and close (index 1) of a
		 * function. So that if "[]" is the function delimiter (which mean to specify
		 * sin as a function one has to write sin[x]), the index 0 contains "[" and
		 * index 1 contains "]".
		 * </p>
		 * 
		 * @param openAndClose An array of length 2 to hold
		 *                     {@link Character#isMirrored(char) mirrored} characters
		 *                     that creates {@link FunctionExpression} objects.
		 * @return {@code this} with the given characters registered into the syntax
		 *         being built.
		 * @throws RuntimeException specifically a
		 *                          {@link java.lang.IllegalStateException} if this
		 *                          method was hitherto called with a valid non-null
		 *                          argument.
		 * @throws RuntimeException specifically an {@link IndexOutOfBoundsException} if
		 *                          the array length is greater than 2.
		 * @throws RuntimeException specifically an
		 *                          {@link java.lang.IllegalArgumentException} if the
		 *                          elements of the array have not been registered using
		 *                          {@link #registerPunctuatorAndDelimeter(Character)}.
		 */
		public Builder<E, P> registerFunctionDelimiters(Character[] openAndClose) {
			if (syntax.functionDelimiters != null)
				new IllegalStatusException(ExceptionMessage.FIELD_WRITE_EXCEPTION);
			else if (openAndClose.length != 2)
				new IndexBeyondLimitException(openAndClose.length, 2);
			else if (syntax.punctuators.contains(openAndClose[0]) && syntax.punctuators.contains(openAndClose[1])) {
				syntax.functionDelimiters = new Character[2];
				syntax.functionDelimiters[0] = openAndClose[0];
				syntax.functionDelimiters[1] = openAndClose[1];
				return this;
			}
			new IllformedSyntaxException(ExceptionMessage.UNREGISTERED_DELIMITERS);
			throw new java.lang.IllegalArgumentException(ExceptionMessage.UNREGISTERED_DELIMITERS.getSourceMessage());
		}

		/*
		 * Date: 3 Aug 2021-----------------------------------------------------------
		 * Time created: 04:18:22--------------------------------------------
		 */
		/**
		 * Registers an array of 2 elements with the first index containing a character
		 * that will represent the opening parenthesis of a grouped expression (returned
		 * by {@link CommonSyntax#precedenceDirector1()}) and the second index
		 * representing the closing parenthesis of a grouped expression (returned by
		 * {@link CommonSyntax#precedenceDirector2()}) of a function and returns
		 * {@code this}. If this method was hitherto called with a valid non-null
		 * argument or the input array length is not 2 then an exception will be thrown.
		 * This method may only be called once.
		 * <p>
		 * Some syntax may use {@code x(y+2)} to mean {@code x} as a function of
		 * <code>y+2</code> this is meant to clarify those ambiguities. So any pair of
		 * character that is found in this array will regarded as the open (index 0) and
		 * close (index 1) of parenthesised expression. So that if "[]" is the
		 * precedence director (which mean to specify x as a factor of y+2 one has to
		 * write x[y+2]), the index 0 contains "[" and index 1 contains "]".
		 * </p>
		 * 
		 * @param directors An array of length 2 to hold
		 *                  {@link Character#isMirrored(char) mirrored} characters that
		 *                  creates {@link GroupExpression} objects.
		 * @return {@code this} with the given characters registered into the syntax
		 *         being built.
		 * @throws RuntimeException specifically a
		 *                          {@link java.lang.IllegalStateException} if this
		 *                          method was hitherto called with a valid non-null
		 *                          argument.
		 * @throws RuntimeException specifically an {@link IndexOutOfBoundsException} if
		 *                          the array length is greater than 2.
		 * @throws RuntimeException specifically an
		 *                          {@link java.lang.IllegalArgumentException} if the
		 *                          elements of the array have not been registered using
		 *                          {@link #registerPunctuatorAndDelimeter(Character)}.
		 */
		public Builder<E, P> registerPrecedenceDirectors(Character[] directors) {
			if (syntax.precedenceDirectors != null)
				new IllegalStatusException(ExceptionMessage.FIELD_WRITE_EXCEPTION);
			else if (directors.length != 2)
				new IndexBeyondLimitException(directors.length, 2);
			else if (syntax.punctuators.contains(directors[0]) && syntax.punctuators.contains(directors[1])) {
				syntax.precedenceDirectors = new Character[2];
				syntax.precedenceDirectors[0] = directors[0];
				syntax.precedenceDirectors[1] = directors[1];
				return this;
			}
			new IllformedSyntaxException(ExceptionMessage.UNREGISTERED_DELIMITERS);
			throw new java.lang.IllegalArgumentException(ExceptionMessage.UNREGISTERED_DELIMITERS.getSourceMessage());
		}

		/*
		 * Date: 16 Sep 2022-----------------------------------------------------------
		 * Time created: 01:13:33--------------------------------------------
		 */
		/**
		 * Registers an array of 2 elements with the first index containing a character
		 * that will represent the opening character of an array expression (returned by
		 * {@link CommonSyntax#getArrayOpen()}) and the second index representing the
		 * closing character of an array expression (returned by
		 * {@link CommonSyntax#getArrayClose()}) of an array and returns {@code this}.
		 * If this method was hitherto called with a valid non-null argument or the
		 * input array length is not 2 then an exception will be thrown. This method may
		 * only be called once.
		 * 
		 * @param delimiters An array of length 2 to hold
		 *                   {@link Character#isMirrored(char) mirrored} characters that
		 *                   creates {@link Array} objects.
		 * @return {@code this} with the given characters registered into the syntax
		 *         being built.
		 * @throws RuntimeException specifically a
		 *                          {@link java.lang.IllegalStateException} if this
		 *                          method was hitherto called with a valid non-null
		 *                          argument.
		 * @throws RuntimeException specifically an {@link IndexOutOfBoundsException} if
		 *                          the array length is greater than 2.
		 * @throws RuntimeException specifically an
		 *                          {@link java.lang.IllegalArgumentException} if the
		 *                          elements of the array have not been registered using
		 *                          {@link #registerPunctuatorAndDelimeter(Character)}.
		 */
		public Builder<E, P> registerArrayDelimiters(Character[] delimiters) {
			if (syntax.arrayDelimiters != null)
				new IllegalStatusException(ExceptionMessage.FIELD_WRITE_EXCEPTION);
			else if (delimiters.length != 2)
				new IndexBeyondLimitException(delimiters.length, 2);
			else if (syntax.punctuators.contains(delimiters[0]) && syntax.punctuators.contains(delimiters[1])) {
				syntax.arrayDelimiters = new Character[2];
				syntax.arrayDelimiters[0] = delimiters[0];
				syntax.arrayDelimiters[1] = delimiters[1];
				return this;
			}
			new IllformedSyntaxException(ExceptionMessage.UNREGISTERED_DELIMITERS);
			throw new java.lang.IllegalArgumentException(ExceptionMessage.UNREGISTERED_DELIMITERS.getSourceMessage());
		}

		/*
		 * Date: 3 Aug 2021-----------------------------------------------------------
		 * Time created: 04:23:14--------------------------------------------
		 */
		/**
		 * Registers the given character as an argument separator within a function
		 * expression (returned by {@link CommonSyntax#functionArgDelimiter()}). If this
		 * method was hitherto called with a valid non-null argument or the input is not
		 * a valid character in this syntax then an exception will be thrown. This
		 * method may only be called once.
		 * 
		 * @param argDelimiter a character which is a member of
		 *                     {@link Syntax#getPunctuatorsAndDelimiters()}.
		 * @return {@code this} with the given character registered into the syntax
		 *         being built.
		 * @throws RuntimeException specifically an {@link IllegalStateException} if
		 *                          this method was hitherto called with a valid
		 *                          non-null argument.
		 * @throws RuntimeException specifically an
		 *                          {@link java.lang.IllegalArgumentException} if the
		 *                          elements of the array have not been registered using
		 *                          {@link #registerPunctuatorAndDelimeter(Character)}.
		 * @see CommonSyntax#functionArgDelimiter()
		 */
		public Builder<E, P> registerArgDelimiter(Character argDelimiter) {
			if (syntax.argDelimiter != null)
				new IllegalStatusException(ExceptionMessage.FIELD_WRITE_EXCEPTION);
			else if (!syntax.punctuators.contains(argDelimiter))
				new IllformedSyntaxException(ExceptionMessage.UNREGISTERED_DELIMITERS);
			syntax.argDelimiter = argDelimiter;
			return this;
		}

		/*
		 * Date: 3 Aug 2021-----------------------------------------------------------
		 * Time created: 04:30:40--------------------------------------------
		 */
		/**
		 * Registers the given type to as the default name {@code Type} (returned by
		 * {@link CommonSyntax#getNameType()}). This means that this
		 * {@code CommonSyntax} only recognises names to be token(s) that have the same
		 * type as the arguments. If the arguments is missing from
		 * {@link CommonSyntax#getTypes()} then an exception will be thrown. This method
		 * may only be called once.
		 * 
		 * @param nameType the name type to be registered which must have been
		 *                 pre-registered using {@link #registerType(Type)} or
		 *                 {@link #registerTypes(Collection)}.
		 * @return {@code this} with the given name type registered into the syntax
		 *         being built.
		 * @throws RuntimeException specifically an {@link IllegalStateException} if
		 *                          this method was hitherto called with a valid
		 *                          non-null argument.
		 * @throws RuntimeException specifically an
		 *                          {@link java.lang.IllegalArgumentException} if the
		 *                          elements of the array have not been registered using
		 *                          {@link #registerType(Type)} or
		 *                          {@link #registerTypes(Collection)}.
		 */
		public Builder<E, P> registerNameType(Type<String> nameType) {
			if (syntax.nameType != null)
				new IllegalStatusException(ExceptionMessage.FIELD_WRITE_EXCEPTION);
			else if (!syntax.types.contains(nameType))
				new IllformedSyntaxException(ExceptionMessage.UNREGISTERED_DELIMITERS);
			syntax.nameType = nameType;
			return this;
		}
	}

	/*
	 * Most Recent Date: 1 Aug 2021-----------------------------------------------
	 * Most recent time created: 14:21:24--------------------------------------
	 */
	/**
	 * Gets the default {@code Type} for all name tokens. Note that for this
	 * {@code Type} to be valid it must be a member {@link #getTypes()}.
	 * 
	 * @return the {@code Type} object specifically used for tokens representing
	 *         identifiers.
	 */
	Type<String> getNameType();

	/*
	 * Most Recent Date: 1 Aug 2021-----------------------------------------------
	 * Most recent time created: 13:40:45--------------------------------------
	 */
	/**
	 * Returns the specified type for the given character in this syntax or returns
	 * null if there is no defined type for the specified character.
	 * 
	 * @param c the character whose type within this syntax is to be returned.
	 * @return the corresponding {@code Type} for the provided {@code Character}
	 */
	Type<String> getType(Character c);

	/*
	 * Most Recent Date: 1 Aug 2021-----------------------------------------------
	 * Most recent time created: 14:03:38--------------------------------------
	 */
	/**
	 * Returns the character used by this {@code CommonSyntax} to represent the
	 * beginning of a function's argument.
	 * <p>
	 * Some syntax may use {@code sin(x)} to mean {@code sin*x} this is meant to
	 * clarify those ambiguities. So if "[]" is the function delimiter (which means
	 * to specify sin as a function one has to write sin[x]), the function open will
	 * be "[".
	 * </p>
	 * 
	 * @return a {@code Character} that is used for starting argument(s) to a
	 *         function.
	 */
	Character functionOpen();

	/*
	 * Most Recent Date: 1 Aug 2021-----------------------------------------------
	 * Most recent time created: 14:07:30--------------------------------------
	 */
	/**
	 * Returns the character used by this {@code CommonSyntax} to represent the end
	 * of a function's argument.
	 * <p>
	 * Some syntax may use {@code sin(x)} to mean {@code sin*x} this is meant to
	 * clarify those ambiguities. So if "[]" is the function delimiter (which means
	 * to specify sin as a function one has to write sin[x]), the function close
	 * will be "]".
	 * </p>
	 * 
	 * @return a {@code Character} that is used for ending argument(s) to a
	 *         function.
	 */
	Character functionClose();

	/*
	 * Most Recent Date: 1 Aug 2021-----------------------------------------------
	 * Most recent time created: 14:19:43--------------------------------------
	 */
	/**
	 * Returns the argument separator within a function. This character is used to
	 * separate arguments for example: in the function {@code tan(x, y)}, the comma
	 * character "," is used to separate the first argument from the second.
	 * 
	 * @return a {@code Character} used for delimiting arguments within s function.
	 */
	Character functionArgDelimiter();

	/*
	 * Most Recent Date: 1 Aug 2021-----------------------------------------------
	 * Most recent time created: 14:12:24--------------------------------------
	 */
	/**
	 * Returns the first pair of characters used for specifying the order of
	 * operation in an expression. For example, the result given by the expression
	 * {@code 2+5*3^2} will be different from the one given by
	 * <code>(2+5)*(3^2)</code> because of the "precedence director" represented by
	 * the parenthesis i.e a precedence director is a
	 * {@link Character#isMirrored(char) mirrored} character that changes the order
	 * of operation of an expression.
	 * <p>
	 * Some syntax may use {@code x(y+2)} to mean {@code x} as a function of
	 * <code>y+2</code> this is meant to clarify those ambiguities. So any character
	 * returned by this method will be regarded as the opening of a grouped
	 * expression. So that if "[]" is the precedence director (which means to
	 * specify x as a factor of y+2 one has to write x[y+2]), opening is "[".
	 * </p>
	 * 
	 * @return the {@code Character} that is used for starting grouped expressions.
	 */
	Character precedenceDirector1();

	/*
	 * Most Recent Date: 1 Aug 2021-----------------------------------------------
	 * Most recent time created: 14:12:24--------------------------------------
	 */
	/**
	 * Returns the second pair of characters used for specifying the order of
	 * operation in an expression. For example, the result given by the expression
	 * {@code 2+5*3^2} will be different from the one given by
	 * <code>(2+5)*(3^2)</code> because of the "precedence director" represented by
	 * the parenthesis i.e a precedence director is a
	 * {@link Character#isMirrored(char) mirrored} character that changes the order
	 * of operation of an expression.
	 * <p>
	 * Some syntax may use {@code x(y+2)} to mean {@code x} as a function of
	 * <code>y+2</code> this is meant to clarify those ambiguities. So any character
	 * returned by this method will be regarded as the end of a grouped expression.
	 * So that if "[]" is the precedence director (which means to specify x as a
	 * factor of y+2 one has to write x[y+2]), end is "[".
	 * </p>
	 * 
	 * @return the {@code Character} that is used for ending grouped expressions.
	 */
	Character precedenceDirector2();

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 01:03:23--------------------------------------------
	 */
	/**
	 * Gets the character used in the syntax for the beginning of an array
	 * expression. For example, the array <code>{1,2,3,4,5}</code> has a left brace
	 * as it's opening character, this method will be expected to return a left
	 * brace for this syntax.
	 * 
	 * @return a character used as a the marker for the start of array.
	 */
	Character getArrayOpen();

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 01:03:23--------------------------------------------
	 */
	/**
	 * Gets the character used in the syntax for the end of an array expression. For
	 * example, the array <code>{1,2,3,4,5}</code> has a right brace as it's end
	 * character, this method will be expected to return a right brace for this
	 * syntax.
	 * 
	 * @return a character used as a the marker for the end of array.
	 */
	Character getArrayClose();

//	Character blockOpen();

//	Character blockClose();
}