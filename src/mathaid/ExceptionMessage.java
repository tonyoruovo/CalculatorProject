/**
 * 
 */
package mathaid;

import java.text.Collator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import mathaid.calculator.base.value.BinaryRep;
import mathaid.calculator.base.value.Precision;

/*
 * Date: 18 Jul 2021----------------------------------------------------------- 
 * Time created: 15:24:00---------------------------------------------------  
 * Package: mathaid------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: ExceptionMessage.java------------------------------------------------------ 
 * Class name: ExceptionMessage------------------------------------------------ 
 * Wrong 'WXPT' for 'XZBT'
 * #The index 1 is out of bounds for the array XZBT
 */
/**
 * <p>
 * This enum provides the concrete implementation for {@code LocalizableMessage}
 * object that are used as parameters for {@code BaseException} classes.
 * </p>
 * <p>
 * An {@code ExceptionMessage} contains all the message constants used for
 * formatting exception message to the user. All the constants in this enum are
 * also used as keys within a map-like collection that stores its values as the
 * translation between supported languages. The messages that are translated are
 * themselves used as arguments for the constants. This ensures that when
 * certain translation are unavailable, that default is returned via
 * {@link #getSourceMessage()}. It also serves as a reminder for translators on
 * the format supported for translation. Although the source text are English,
 * users can create their own constants that are not english, the language of
 * the source does not matter. This enum is backed by a {@code ResourceBundle}
 * object which is the map-like object spoken of above, this resource bundle
 * object is also backed by a ".properties" file.
 * </p>
 * <p>
 * Messages may range from simple messages (which display a text) to complex
 * messages (which contains arguments for strings, dates and times, integer,
 * currency, decimals and percents). While simple messages do not contain
 * arguments, complex massages may contain an arbitrary number of arguments.
 * This ensures optimal display of localised texts.
 * </p>
 * 
 * @see {@link java.text.MessageFormat}
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public enum ExceptionMessage implements LocalizableMessage {
	/**
	 * Message for {@code MathematicalException} when the {@code BigFraction}
	 * accuracy is <code>&lt;= 0</code> or is <code>&gt;= 1</code>
	 */
	ACCURACY_MUST_BE_ONE("accuracy cannot be less than 0 or greater than 1"),
	/**
	 * Message for 0 input bit length in binary representation objects
	 */
	ARBITRARY_BITS_NOT_ALLOWED("arbitrary bits not allowed"),
	/**
	 * Message constant for array sizes exceptions. It has 1 argument which is
	 * expected to be a number.
	 */
	ARRAY_SIZE_OUTSIDE_LIMIT("array size must be {0}"),
	/**
	 * When a negative value is the argument to a square root function
	 */
	ATTEMPTED_ROOT_OF_NEGATIVE_NUMBER("attempted root of a negative number"),
	/**
	 * Message constant for when the bits within a floating point or integer is
	 * greater than the specified bit length for that value
	 */
	BITS_GREATER_THAN_FOUND("bits length found is greater than bit length specified"),
	/**
	 * Message constant for the builder within the CommonSyntax interface to inform
	 * users that character being registered failed for the purpose of not being a
	 * digit. It has 1 argument which is expected to be a single character.
	 */
	CHAR_NOT_DIGIT("{0} is not a digit"),
	/**
	 * Message constant for the builder within the CommonSyntax interface to inform
	 * users that character being registered failed for the purpose of not being a
	 * letter. It has 1 argument which is expected to be a single character.
	 */
	CHAR_NOT_LETTER("{0} is not a letter"),
	/**
	 * Message constant for the builder within the CommonSyntax interface to inform
	 * users that character being registered failed for the purpose of not being a
	 * punctuator or a delimiter. It has 1 argument which is expected to be a single
	 * character.
	 */
	CHAR_NOT_PUNC_OR_DELIM("{0} is not a punctuator or delimiter"),
	/**
	 * Message constant foe parsing exception whereby the character to be parsed is
	 * not registered. It has 1 argument which is expected to be a single character.
	 */
	CHAR_NOT_VALID("{0} is not a valid token"),
	/**
	 * Message constant used by an exception thrown by the
	 * {@code CommonSyntax.Builder} class when a syntax build is attempted and the
	 * whitespace character is not whitespace. It has 1 argument which is expected
	 * to be a single character.
	 */
	CHAR_NOT_WHITESPACE("{0} is not a white space"),
	/**
	 * Message constant for server timeout.
	 */
	DEADLINE_EXPIRED("took too long to receive a response from the server. Please revise the timeout value"),
	/**
	 * Message for {@code MathematicalException} when a {@code BigFraction}
	 * constructor that specifies a max denominator is used and the max denominator
	 * is exceeded.
	 */
	DENOMINATOR_ABOVE_MAX("Denominator is above the maximum"),
	/**
	 * Message constant for exception thrown when division by 0 is detected
	 */
	DIVISION_BY_ZERO("division by zero"),
	/**
	 * Message constant for generic error messages.
	 */
	ERROR("something went wrong"),
	/**
	 * Message constant for when a code such as "Error 404" was received from a
	 * website instead of something like 200. It has 1 argument which is expected to
	 * be a number.
	 */
	ERROR_REQUEST("error, request {0} found"),
	/**
	 * Place holder constant for JDK exception messages. these cannot be localised
	 */
	EMPTY(""),
	/**
	 * Message constant for when a field that has already being written is
	 * unintentionally overridden.
	 */
	FIELD_WRITE_EXCEPTION("this field is already written. Cannot rewrite"),
	/**
	 * Message constant for {@link java.lang.IndexOutOfBoundsException}. It has 2
	 * arguments which are expected to both be numbers.
	 */
	INDEX_OUT_BOUNDS("{0} is outside the limit. Limit is {1}"),
	/**
	 * Message for infinite continued fraction.
	 */
	INFINITE_CONTINUED_FRACTION("continued fraction is infinite"),
	/**
	 * Message for when user tries to use a method that requires internet
	 * connection.
	 */
	INTERNET_NOT_AVAILABLE("no internet connection found"),
	/**
	 * General Message for value exceptions. It has 1 argument which is expected to
	 * be a single character.
	 */
	ILLEGAL_DIGIT("{0} is not a digit"),
	/**
	 * Message constant for {@code MathematicalException} when an a root function
	 * takes in an {@code int} argument less than or equal to 1
	 */
	ILLEGAL_INDEX_FOR_ROOT("illegal index for root function"),
	/**
	 * Message constant used by the {@code History} class to prohibit 0 limits.
	 */
	LIMIT_ZERO("limit cannot be 0"),
	/**
	 * Message constant for when a method is not meant to be called by the class it
	 * was declared in. used by {@code Lexer}, {@code History} and other classes
	 * within this API.
	 */
	METHOD_NOT_IMPLEMENTED("method not implemented"),
	/**
	 * Message constant for negative value exception messages. It has 1 argument
	 * which is expected to be positive value.
	 */
	NEGATIVE_INPUT_DETECTED("negative value -{0} not allowed"),
	/**
	 * Message constant for when a method receives an argument string that was
	 * expected to be a number but turn out to be not numeric.
	 */
	NOT_A_NUMBER("{0} is not a number"),
	/**
	 * Message constant for malformed urls when the protocol (such as http or https)
	 * is missing or the url itself is invalid.
	 */
	NO_PROTOCOL("no protocol found in url. Use either one of the http or https"),
	/**
	 * Number too big message
	 */
	NUMBER_TOO_BIG("number too big"),
	/**
	 * Message constant for exceptions thrown because of unnormalised floating point
	 * representation
	 */
	NUMBER_NOT_NORMALISED("number is not normalised"),
	/**
	 * Message constant for null arguments. It has 1 argument which is expected to
	 * be text (a name of a class, enum, interface)
	 */
	OBJECT_IS_NULL("{0} is null"),
	/**
	 * Simple overflow exception message.
	 */
	OVERFLOW_("overflow"),
	/**
	 * Message constant for overflows in floating-point values or value containers.
	 * It has 2 arguments which are expected to both be strings.
	 */
	OVERFLOW("Overflow. {0} is too big or too small for {1}"),
	/**
	 * Message for {@code MathematicalException} constructors to indicate a value
	 * that is not supposed to be signed is. It has 1 argument which is expected to
	 * be a number.
	 */
	SIGNED_NUMBER_NOT_ACCEPTED("{0} cannot be signed"),
	/**
	 * General message for classes cannot be instantiated via a traditional
	 * constructor
	 */
	SINGLETON_CONSTRUCTOR_ERROR("class cannot be instantiated"),
	/**
	 * Message for exception thrown when an instance of a class already exists in
	 * the JVM and a user tries to re-instantiate it. Such an object can be a
	 * {@code Settings} object.
	 */
	STATIC_SINGLETON_ERROR("Object already exists illegal instance not allowed"),
	/**
	 * Message constant for when a parser failed to parse a token. It has 1 argument
	 * which is expected to be a string.
	 */
	UNABLE_TO_PARSE("unable to parse {0}"),
	/**
	 * General message for uncaught exceptions. Uncaught exceptions are those that
	 * are throw from the JDK and are unavoidable. these exception messages cannot
	 * be localised.
	 */
	UNCAUGHT_EXCEPTION("uncaught exception"),
	/**
	 * Message constant for when the expected is missing and in it's stead an
	 * illegal token was found. It has 2 arguments which are expected to be strings.
	 * 
	 */
	UNEXPECTED_TOKEN_FOUND("expected token: {0}, but found: {1}"),
	/**
	 * General Message for value exceptions.
	 */
	UNKNOWN_DIGIT("Unknown digit"),
	/**
	 * Message constant for exception raised in the {@code InternalEvaluator} class
	 * for when an invalid input is given to a method that ios supposed to return an
	 * {@code AngleUnit} object.
	 */
	UNKNOWN_GEOMETRY("unknown trigonometry"),
	/**
	 * Message constant for when an unknown {@code Type} for a builder method is
	 * given as an argument.
	 */
	UNKNOWN_TYPE_DETECTED("Unknown type detected"),
	/**
	 * Message constant for when a user tries to use an illegal value for the
	 * convert methods of the {@code Convertible} object.
	 */
	UNKNOWN_UNIT("unknown unit"),
	/**
	 * Message used for valueFormatexception if the user specifies the illegal
	 * {@link Precision#UNLTD} <b>Note</b>: the argument is for 'Precision.UNLTD'
	 * because the argument should always be in English. It has 1 argument which is
	 * expected to be a string.
	 */
	UNLIMITED_PRECISION_NOT_ALLOWED("{0} not allowed"),
	/***
	 * General message constant for the builder class when an unregistered delimiter
	 * is being used.
	 */
	UNREGISTERED_DELIMITERS("Unregistered delimiters"),
	/**
	 * Message constant for when the user tries to do a conversion between currency
	 * units and the currency involved is not a supported one.
	 */
	UNSUPPORTED_CURRENCY("unsupported currency"),
	/**
	 * Message for inputting unknown radixes
	 */
	UNSUPPORTED_RADIX("Unsupported radix"),
	/**
	 * Message constant for zero input exceptions
	 */
	VALUE_IS_ZERO("cannot be 0"),
	/**
	 * Message constant for writing warnings in to the mathaid print stream
	 */
	WARNING_NAN("warning NaN value detected"),
	/**
	 * Message constant for writing infinity warnings in to the mathaid print stream
	 */
	WARNING_INFINITY("warning infinity values detected"),
	/**
	 * Message constant for wrong bitlength for reps other than
	 * {@link BinaryRep#MATH}. It has 2 arguments which are expected to both be
	 * strings.
	 */
	WRONG_BITLENGTH_FOR_BINARY_REP("Wrong {0} for {1}");

	/*
	 * Date: 23 Jul 2021-----------------------------------------------------------
	 * Time created: 13:36:03---------------------------------------------------
	 */
	/**
	 * Private constructor that constructs {@code ExceptionMessage} objects
	 * 
	 * @param source the source text
	 */
	private ExceptionMessage(String source) {
		this.source = source;
	}

	/*
	 * Most Recent Date: 19 Jul 2021-----------------------------------------------
	 * Most recent time created: 14:12:27--------------------------------------
	 */
	/**
	 * Returns the argument {@code String} along with any internal message left for
	 * a formatter.
	 * 
	 * @return the argument string
	 */
	@Override
	public String getSourceMessage() {
		return source;
	}

	/*
	 * Most Recent Date: 19 Jul 2021-----------------------------------------------
	 * Most recent time created: 14:12:27--------------------------------------
	 */
	/**
	 * Gets the localised message along with arguments within the message left
	 * specifically for formatters. When the given {@code LocaleSelection} is not
	 * supported, this method returns the same value as {@link #getSourceMessage()}.
	 * 
	 * @param locale the {@code LocaleSelection} object that is used as an
	 *               identifier for returning a locale-specific message
	 * @return a {@code String} object using the characters in the specified
	 *         {@code LocaleSelection}
	 */
	@Override
	public String getLocalizedMessage(LocaleSelection locale) {
		try {
			final ResourceBundle rs = ResourceBundle.getBundle("mathaid.ExceptionLang", locale.locale);
			if (rs.getLocale().getLanguage().equals(locale.locale.getLanguage())) {
				Collator c = Collator.getInstance(Locale.US);
				String s = rs.getString(toString());
				if (c.compare(s, "null") != 0)// This is for the Empty String message constant
					return s;
			}
		} catch (MissingResourceException e) {
		}

		return source;
	}

	/**
	 * The original {@code String}
	 */
	private final String source;
}
