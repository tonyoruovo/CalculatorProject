/**
 * 
 */
package mathaid;

import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.Date;

import mathaid.calculator.base.util.Utility;

/*
 * Date: 19 Jul 2021----------------------------------------------------------- 
 * Time created: 19:27:01---------------------------------------------------  
 * Package: mathaid.calculator.base------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: BaseException.java------------------------------------------------------ 
 * Class name: BaseException------------------------------------------------ 
 */
/**
 * A class that throws exception from it's constructor after writing it to the
 * mathaid error file.
 * <p>
 * The base class for all well formed exception in the mathaid api, it is
 * essentially a wrapper for java's throw statement. A {@code BaseException} is
 * designed so that messages are localised according to the specified
 * {@code LocalizableMessage}. It is an error for a code within the mathaid api to
 * throw a java exception directly, this is because all exceptions that pass
 * through here are written to an error file in a specific format and can be
 * used for debugging.
 * </p>
 * <p>
 * All {@code BaseException} cannot be directly thrown as per the java language
 * {@code throw} command and {@code throws} declaration. However internal
 * objects within this class maintain exceptions in the mathaid api to write
 * such exception as text to a file. When such object have finished their write
 * operation, the {@code BaseException} object will throw the exception
 * specified in it's constructor. Hence this class will never be instantiated
 * (even when it's constructors are called) but vital informations about the
 * exception is written to a file in a semi-Human-readable text by internal
 * objects.
 * </p>
 * <p>
 * 3 different types of JDK exceptions are supported by this class. They are
 * <ol>
 * <li>{@link java.lang.Error} for errors to be thrown by mathaid code
 * (specified by {@link #BaseException(LocalizableMessage, Error, Object...)}).
 * An example of this could be code that deals with a {@link StackOverflowError}
 * or an {@link OutOfMemoryError}, such a code will have to have a way of
 * writing to the mathaid error file for logging for debugging purposes.</li>
 * <li>{@link java.lang.Exception} (a.k.a checked exceptions) for exceptions
 * that need to add a throws declaration to the method or constructor where they
 * are thrown (specified by
 * {@link #BaseException(LocalizableMessage, Exception, Object...)}). An example
 * of this could be an {@link java.io.IOException} that is thrown by a jdk code
 * or otherwise (perhaps an external library), such an exception will not be
 * possible to write to the mathaid error file if it is not wrapped by an this
 * constructor. This in turn alerts users of any method they might wish to use
 * that contains an IO exception even without documentation or explicitly throw
 * an IO exception.</li>
 * <li>{@link java.lang.Runtime} (a.k.a unchecked exceptions) for exceptions
 * that don't want to have add a throws declaration to their methods or
 * constructor (specified by
 * {@link #BaseException(LocalizableMessage, RuntimeException, Object...)}).
 * Maybe a user just wants to throw a classical {@code ArithmeticException} or
 * {@code IllegalArgumentException}, such a constructor is appropriate to
 * use.</li>
 * </ol>
 * </p>
 * <p>
 * <i> This class is meant be used in such a way that a subclass should only
 * extend just one of it's constructor that would designate it to be either an
 * error, a checked exception or an unchecked exception. So by rule, a subclass
 * of</i> {@code BaseException}<i> cannot be all 3 of these things at the same
 * time.</i>
 * </p>
 * <p>
 * To throw an exception that consistent with the mathaid rule, get a subclass
 * to implement just one of the constructors:
 * 
 * <pre>
 * class MyIOException extends BaseException {
 * 
 * &numsp;&numsp;&numsp;&numsp;public MyIOException(LocalizableMessage msg, IOException e, Object... args) throws java.lang.Exception { // constructor
 * &numsp;&numsp;&numsp;&numsp;&numsp;&numsp;&numsp;&numsp;super(msg, e, args)&semi;
 * &numsp;&numsp;&numsp;&numsp;}
 * 
 * &numsp;&numsp;&numsp;&numsp;public MyIOException(LocalizableMessage msg, Object... args) throws java.lang.Exception { // constructor
 * &numsp;&numsp;&numsp;&numsp;&numsp;&numsp;&numsp;&numsp;// no argument exception constructor is preferred because of the msg argument
 * &numsp;&numsp;&numsp;&numsp;&numsp;&numsp;&numsp;&numsp;super(msg, new java.io.IOException(), args)&semi;
 * &numsp;&numsp;&numsp;&numsp;}
 * 
 * }
 * </pre>
 * 
 * Then use it in place of a throw statement like so:
 * 
 * <pre>
 * final boolean conditionNotMet = ...
 * if(conditionNotMet) {
 * &numsp;&numsp;LocalizableMessage msg = ...
 * &numsp;&numsp;Object[] args = ...
 * &numsp;&numsp;new MyIOException(msg, args)&semi; // mathaid exception is now thrown here
 * }
 * </pre>
 * 
 * Or to intercept perceived exceptions
 * 
 * <pre>
 * try{
 * &numsp;&numsp;FileWriter p = ...
 * &numsp;&numsp;String text = ...
 * &numsp;&numsp;p.append(text)&semi;
 * }catch(IOException e){
 * &numsp;&numsp;LocalizableMessage msg = ...
 * &numsp;&numsp;Object[] args = ...
 * &numsp;&numsp;// intercepts and documents the exception
 * &numsp;&numsp;// then throws the same exception
 * &numsp;&numsp;new MyIOException(msg, e, args)&semi;
 * }
 * </pre>
 * 
 * To catch an exception thrown by this class but want it written to the mathaid
 * error file do:
 * 
 * <pre>
 * try {
 * &numsp;&numsp;LocalizableMessage msg = ...
 * &numsp;&numsp;java.io.IOException t = ...
 * &numsp;&numsp;Object[] args = ...
 * &numsp;&numsp;new MyIOException(msg, t, args)&semi;
 * }catch(Throwable t){// catch code goes here...
 * }
 * </pre>
 * 
 * <b>Note</b>: The 3 methods of this class are used by the internal error
 * handling object to produce locale-specific messages for display to users
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class BaseException implements MathaidException {

	/*
	 * Date: 21 Jul 2021-----------------------------------------------------------
	 * Time created: 16:12:10---------------------------------------------------
	 */
	/**
	 * <p>
	 * Writes the {@code LocalizableMessage}, {@code Error} and {@code Object}
	 * varargs to the resident mathaid error file, then throws the {@code Error}
	 * argument. Thus this will never instantiate a {@code BaseException}.
	 * </p>
	 * <p>
	 * <b><i> Subclasses that are errors by abstraction (logically) are advised to
	 * implement this constructor only.</i></b>
	 * </p>
	 * 
	 * @param localizableMsg a message that is locale specified and may be retrieved
	 *                       by the programmer for the purpose of displaying it to
	 *                       the user of their software
	 * @param cause          an Error, the purpose of which this constructor is
	 *                       called. A no-argument constructor is advised in this
	 *                       situation, because the {@code LocalizableMessage}
	 *                       argument depends on the input error having no message.
	 * @param args           varargs for the {@code LocalizableMessage} to consume.
	 *                       {@code LocalizableMessage} may contain arguments as
	 *                       specified in {@link java.text.MessageFormat}, these
	 *                       arguments are parsed along with the
	 *                       {@code LocalizableMessage} to form a human-readable
	 *                       string.
	 * @throws Error                the input {@code Error} argument after writing
	 *                              the relevant information to a file
	 * @throws NullPointerException if the {@code LocalizableMessage} and
	 *                              {@code Error} arguments are {@code null}. This
	 *                              exception may not be written to the mathaid
	 *                              error file nor is it locale-sensitive
	 */
	public BaseException(LocalizableMessage localizableMsg, Error cause, Object... args) throws Error {
		if (localizableMsg == null || cause == null) {
			NullPointerException e = new NullPointerException("localizable or cause is null");
			try {
				Utility.writeTextToFile("", "uncaught.txt", new Date() + Device.lineSeparator() + e.toString(),
						StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			throw e;
		}
		this.cause2 = null;
		this.cause1 = null;
		this.cause0 = cause;
		this.lm = localizableMsg;
		this.args = args;
		UncaughtExceptionManager ucem = UncaughtExceptionManager.getManager();
		ucem.register(getException(), getLocalizable(), getArguments());
		ucem.endSession();
		throw cause0;
	}

	/*
	 * Date: 21 Jul 2021-----------------------------------------------------------
	 * Time created: 16:12:10---------------------------------------------------
	 */
	/**
	 * <p>
	 * Writes the {@code LocalizableMessage}, {@code Exception} and {@code Object}
	 * varargs to the resident mathaid error file, then throws the {@code Exception}
	 * argument. Thus this will never instantiate a {@code BaseException}.
	 * </p>
	 * <p>
	 * <b><i> Subclasses that are checked exceptions by abstraction (logically) are
	 * advised to implement this constructor only.</i></b>
	 * </p>
	 * 
	 * @param localizableMsg a message that is locale specified and may be retrieved
	 *                       by the programmer for the purpose of displaying it to
	 *                       the user of their software.
	 * @param cause          an Exception, the purpose of which this constructor is
	 *                       called. A no-argument constructor is advised in this
	 *                       situation, because the {@code LocalizableMessage}
	 *                       argument depends on the input error having no message.
	 * @param args           varargs for the {@code LocalizableMessage} to consume
	 *                       {@code LocalizableMessage} may contain arguments as
	 *                       specified in {@link java.text.MessageFormat}, these
	 *                       arguments are parsed along with the
	 *                       {@code LocalizableMessage} to form a human-readable
	 *                       string.
	 * @throws Exception            the input {@code Exception} argument after
	 *                              writing the relevant information to a file.
	 * @throws NullPointerException if the {@code LocalizableMessage} and
	 *                              {@code Exception} arguments are {@code null}.
	 *                              This exception may not be written to the mathaid
	 *                              error file nor is it locale-sensitive.
	 */
	public BaseException(LocalizableMessage localizableMsg, Exception cause, Object... args) throws Exception {
		if (localizableMsg == null || cause == null) {
			NullPointerException e = new NullPointerException("localizable or cause is null");
			try {
				Utility.writeTextToFile("", "uncaught.txt", new Date() + Device.lineSeparator() + e.toString(),
						StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			throw e;
		}
		this.cause2 = null;
		this.cause1 = cause;
		this.cause0 = null;
		this.lm = localizableMsg;
		this.args = args;
		UncaughtExceptionManager ucem = UncaughtExceptionManager.getManager();
		ucem.register(getException(), getLocalizable(), getArguments());
		ucem.endSession();
		throw cause1;
	}

	/*
	 * Date: 19 Jul 2021-----------------------------------------------------------
	 * Time created: 19:27:01---------------------------------------------------
	 */
	/**
	 * <p>
	 * Writes the {@code LocalizableMessage}, {@code RunException} and
	 * {@code Object} varargs to the resident mathaid error file, then throws the
	 * {@code RunException} argument. Thus this will never instantiate a
	 * {@code BaseException}.
	 * </p>
	 * <p>
	 * <b><i> Subclasses that are unchecked exceptions by abstraction (logically)
	 * are advised to implement this constructor only.</i></b>
	 * </p>
	 * 
	 * @param localizableMsg a message that is locale specified and may be retrieved
	 *                       by the programmer for the purpose of displaying it to
	 *                       the user of their software
	 * @param cause          a RuntimeException, the purpose of which this
	 *                       constructor is called. A no-argument constructor is
	 *                       advised in this situation, because the
	 *                       {@code LocalizableMessage} argument depends on the
	 *                       input error having no message.
	 * @param args           varargs for the {@code LocalizableMessage} to consume.
	 *                       {@code LocalizableMessage} may contain arguments as
	 *                       specified in {@link java.text.MessageFormat}, these
	 *                       arguments are parsed along with the
	 *                       {@code LocalizableMessage} to form a human-readable
	 *                       string.
	 * @throws RuntimeException     the input {@code RuntimeException} argument
	 *                              after writing the relevant information to a file
	 * @throws NullPointerException if the {@code LocalizableMessage} and
	 *                              {@code RuntimeException} arguments are
	 *                              {@code null}. This exception may not be written
	 *                              to the mathaid error file nor is it
	 *                              locale-sensitive
	 */
	public BaseException(LocalizableMessage localizableMsg, RuntimeException cause, Object... args)
			throws RuntimeException {
		if (localizableMsg == null || cause == null) {
			NullPointerException e = new NullPointerException("localizable or cause is null");
			try {
				Utility.writeTextToFile("", "uncaught.txt", new Date() + Device.lineSeparator() + e.toString(),
						StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			throw e;
		}
		this.cause2 = cause;
		this.cause1 = null;
		this.cause0 = null;
		this.lm = localizableMsg;
		this.args = args;
		UncaughtExceptionManager ucem = UncaughtExceptionManager.getManager();
		ucem.register(getException(), getLocalizable(), getArguments());
		ucem.endSession();
		throw cause2;
	}

	/*
	 * Most Recent Date: 19 Jul 2021-----------------------------------------------
	 * Most recent time created: 19:59:36--------------------------------------
	 */
	/**
	 * This method will never be called
	 * 
	 * @return the internal Throwable object or at least that is what it is supposed
	 *         to do. In practice this method will never be called because this
	 *         object will never be instantiated
	 */
	@Override
	public Throwable getException() {
		return cause0 == null ? cause1 == null ? cause2 : cause1 : cause0;
	}

	/*
	 * Most Recent Date: 19 Jul 2021-----------------------------------------------
	 * Most recent time created: 19:59:36--------------------------------------
	 */
	/**
	 * This method will never be called
	 * 
	 * @return the internal varargs used for parsing the internal
	 *         {@code LocalizableMessage} to a locale-specific string or at least
	 *         that is what it is supposed to do. In practice this method will never
	 *         be called because this object will never be instantiated
	 */
	@Override
	public Object[] getArguments() {
		return args;
	}

	/*
	 * Most Recent Date: 19 Jul 2021-----------------------------------------------
	 * Most recent time created: 19:59:36--------------------------------------
	 */
	/**
	 * This method will never be called
	 * 
	 * @return the internal {@code LocalizableMessage} object used for parsing to a
	 *         locale-specific string or at least that is what it is supposed to do.
	 *         In practice this method will never be called because this object will
	 *         never be instantiated
	 */
	@Override
	public LocalizableMessage getLocalizable() {
		return lm;
	}

	/**
	 * For localisation when strings are written to the mathaid error file
	 */
	private final LocalizableMessage lm;
	/**
	 * Varargs that are stored as an object array. This used as arguments for the
	 * parameters specified in {@link LocalizableMessage#getLocalizedMessage()}
	 */
	private final Object[] args;
	/**
	 * A {@code RuntimeException} object that is thrown inside the constructors of
	 * this class. When this is non-null, then this is an unchecked exception
	 */
	private final RuntimeException cause2;
	/**
	 * An {@code Exception} object that is thrown inside the constructors of this
	 * class. When this is non-null, then this is checked exception
	 */
	private final Exception cause1;
	/**
	 * An {@code Error} object that is thrown inside the constructors of this class.
	 * When this is non-null, then this is an error
	 */
	private final Error cause0;

}
