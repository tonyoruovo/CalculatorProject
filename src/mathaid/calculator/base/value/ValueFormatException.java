/**
 * 
 */
package mathaid.calculator.base.value;

import java.math.BigDecimal;
import java.math.BigInteger;

import mathaid.BaseException;
import mathaid.LocalizableMessage;
import mathaid.calculator.base.util.Utility;

/*
 * Date: 20 Jul 2021----------------------------------------------------------- 
 * Time created: 16:20:59---------------------------------------------------  
 * Package: mathaid.calculator.base.value------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: ValueFormatException.java------------------------------------------------------ 
 * Class name: ValueFormatException------------------------------------------------ 
 */
/**
 * <p>
 * Exception wrapper that throws {@code java.lang.NumberFormatException} to
 * indicate that some numerical value is not in the correct form.
 * </p>
 * <p>
 * This is a mathaid fabricated unchecked exception
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class ValueFormatException extends BaseException {

	/*
	 * Date: 20 Jul 2021-----------------------------------------------------------
	 * Time created: 16:20:59---------------------------------------------------
	 */
	/**
	 * <p>
	 * Writes the {@code LocalizableMessage} to the resident mathaid error file, then throws the
	 * {@code NumberFormatException} initialised with the no-argument. Thus this will never instantiate a
	 * {@code ValueFormatException}.
	 * </p>
	 * @param msg a message that is locale specified and may be retrieved by
	 *                 the programmer for the purpose of displaying it to the user
	 *                 of their software.
	 */
	public ValueFormatException(LocalizableMessage msg) {
		this(msg, new NumberFormatException());
	}

	/*
	 * Date: 20 Jul 2021-----------------------------------------------------------
	 * Time created: 16:20:59---------------------------------------------------
	 */
	/**
	 * <p>
	 * Writes the {@code LocalizableMessage}, {@code NumberFormatException} and
	 * {@code String} varargs to the resident mathaid error file, then throws the
	 * {@code NumberFormatException} argument. Thus this will never instantiate a
	 * {@code ValueFormatException}.
	 * </p>
	 * 
	 * @param msg      a message that is locale specified and may be retrieved by
	 *                 the programmer for the purpose of displaying it to the user
	 *                 of their software.
	 * @param e        a NumberFormatException, the purpose of which this
	 *                 constructor is called. A no-argument constructor is advised
	 *                 in this situation, because the {@code LocalizableMessage}
	 *                 argument depends on the input error having no message.
	 * @param extraMsg varargs for the {@code LocalizableMessage} to consume.
	 *                 {@code LocalizableMessage} may contain arguments as specified
	 *                 in {@link java.text.MessageFormat}, these arguments are
	 *                 parsed along with the {@code LocalizableMessage} to form a
	 *                 human-readable string.
	 * @throws RuntimeException     specifically a {@code NumberFormatException}
	 *                              after writing the relevant information to a
	 *                              file.
	 * @throws NullPointerException if the {@code LocalizableMessage} is
	 *                              {@code null}. This exception may not written to
	 *                              the mathaid error file nor is it
	 *                              locale-sensitive.
	 */
	public ValueFormatException(LocalizableMessage msg, NumberFormatException e, String... extraMsg) {
		super(msg, e, (Object[]) extraMsg);
	}

	/*
	 * Date: 21 Jul 2021-----------------------------------------------------------
	 * Time created: 12:07:28---------------------------------------------------
	 */
	/**
	 * <p>
	 * Exception wrapper that throws {@code java.lang.NumberFormatException} to
	 * indicate that an array has been accessed at an illegal index.
	 * </p>
	 * 
	 * @param msg    a message that is locale specified and may be retrieved by the
	 *               programmer for the purpose of displaying it to the user of
	 *               their software.
	 * @param number a number {@code String} consumed by the
	 *               {@code LocalizableMessage}.
	 * @param args   varargs for the {@code LocalizableMessage} to consume.
	 *               {@code LocalizableMessage} may contain arguments as specified
	 *               in {@link java.text.MessageFormat}, these arguments are parsed
	 *               along with the {@code LocalizableMessage} to form a
	 *               human-readable string.
	 * @throws RuntimeException     specifically a {@code NumberFormatException}
	 *                              after writing the relevant information to a
	 *                              file.
	 * @throws NullPointerException if the {@code LocalizableMessage} is
	 *                              {@code null}. This exception may not written to
	 *                              the mathaid error file nor is it
	 *                              locale-sensitive.
	 */
	public ValueFormatException(LocalizableMessage msg, String number, String... args) {
		this(msg, new NumberFormatException(), Utility.add(args, new int[] { 0 }, number));
	}

	/*
	 * Date: 21 Jul 2021-----------------------------------------------------------
	 * Time created: 12:07:28---------------------------------------------------
	 */
	/**
	 * <p>
	 * Exception wrapper that throws {@code java.lang.NumberFormatException} to
	 * indicate that an array has been accessed at an illegal index.
	 * </p>
	 * 
	 * @param msg    a message that is locale specified and may be retrieved by the
	 *               programmer for the purpose of displaying it to the user of
	 *               their software.
	 * @param number a number consumed by the {@code LocalizableMessage}.
	 * @param args   varargs for the {@code LocalizableMessage} to consume
	 *               {@code LocalizableMessage} may contain arguments as specified
	 *               in {@link java.text.MessageFormat}, these arguments are parsed
	 *               along with the {@code LocalizableMessage} to form a
	 *               human-readable string.
	 * @throws RuntimeException     specifically a {@code NumberFormatException}
	 *                              after writing the relevant information to a
	 *                              file.
	 * @throws NullPointerException if the {@code LocalizableMessage} is
	 *                              {@code null}. This exception may not written to
	 *                              the mathaid error file nor is it
	 *                              locale-sensitive.
	 */
	public ValueFormatException(LocalizableMessage msg, Number number, String... args) {
		this(msg, new NumberFormatException(), Utility.add(args, new int[] { 0 }, number.toString()));
	}

	/*
	 * Date: 21 Jul 2021-----------------------------------------------------------
	 * Time created: 12:07:28---------------------------------------------------
	 */
	/**
	 * <p>
	 * Exception wrapper that throws {@code java.lang.NumberFormatException} to
	 * indicate that an array has been accessed at an illegal index.
	 * </p>
	 * 
	 * @param msg            a message that is locale specified and may be retrieved
	 *                       by the programmer for the purpose of displaying it to
	 *                       the user of their software.
	 * @param number         a number {@code String} consumed by the
	 *                       {@code LocalizableMessage}.
	 * @param usePlainString asks whether to use the whole number string or the
	 *                       abridged scientific notational string.
	 * @param args           varargs for the {@code LocalizableMessage} to consume
	 *                       {@code LocalizableMessage} may contain arguments as
	 *                       specified in {@link java.text.MessageFormat}, these
	 *                       arguments are parsed along with the
	 *                       {@code LocalizableMessage} to form a human-readable
	 *                       string.
	 * @throws RuntimeException     specifically a {@code NumberFormatException}
	 *                              after writing the relevant information to a
	 *                              file.
	 * @throws NullPointerException if the {@code LocalizableMessage} is
	 *                              {@code null}. This exception may not written to
	 *                              the mathaid error file nor is it
	 *                              locale-sensitive.
	 */
	public ValueFormatException(LocalizableMessage msg, BigDecimal number, boolean usePlainString, String... args) {
		this(msg, new NumberFormatException(),
				Utility.add(args, new int[] { 0 }, usePlainString ? number.toPlainString() : number.toString()));
	}

	/*
	 * Date: 21 Jul 2021-----------------------------------------------------------
	 * Time created: 12:07:28---------------------------------------------------
	 */
	/**
	 * <p>
	 * Exception wrapper that throws {@code java.lang.NumberFormatException} to
	 * indicate that an array has been accessed at an illegal index.
	 * </p>
	 * 
	 * @param msg            a message that is locale specified and may be retrieved
	 *                       by the programmer for the purpose of displaying it to
	 *                       the user of their software.
	 * @param number         a number {@code String} consumed by the
	 *                       {@code LocalizableMessage}.
	 * @param radix the base in which to display the integer argument.
	 * @param args           varargs for the {@code LocalizableMessage} to consume
	 *                       {@code LocalizableMessage} may contain arguments as
	 *                       specified in {@link java.text.MessageFormat}, these
	 *                       arguments are parsed along with the
	 *                       {@code LocalizableMessage} to form a human-readable
	 *                       string.
	 * @throws RuntimeException     specifically a {@code NumberFormatException}
	 *                              after writing the relevant information to a
	 *                              file.
	 * @throws NullPointerException if the {@code LocalizableMessage} is
	 *                              {@code null}. This exception may not written to
	 *                              the mathaid error file nor is it
	 *                              locale-sensitive.
	 */
	public ValueFormatException(LocalizableMessage msg, BigInteger number, int radix, String... args) {
		this(msg, new NumberFormatException(), Utility.add(args, new int[] { 0 }, number.toString(radix)));
	}

}
