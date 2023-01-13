/**
 * 
 */
package mathaid;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.nio.file.InvalidPathException;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mathaid.calculator.base.util.Utility;

/*
 * Date: 19 Jul 2021----------------------------------------------------------- 
 * Time created: 12:18:27---------------------------------------------------  
 * Package: mathaid------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: UncaughtExceptionManager.java------------------------------------------------------ 
 * Class name: UncaughtExceptionManager------------------------------------------------ 
 */
/**
 * A sentinel-like object that stands ready to write exceptions and
 * errors in the mathaid api to an error file. The job of this class is to
 * intercept any exception thrown at runtime in the mathaid api and write such
 * exception to a file before it's throw calls returns. It is used side-by-side
 * with the {@code BaseException} class.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public final class UncaughtExceptionManager implements AutoCloseable {

	/**
	 * The string representing the path to the internal error file
	 */
	private static final String ERROR_FILE;

	static {
		String fileName;
		try {
			fileName = Device.getFullPathForExcluding(UncaughtExceptionManager.class)
					.concat(Device.fileSeparator().concat("Error.mathaid"));
		} catch (InvalidPathException e) {
			try {
				Utility.writeTextToFile("", "uncaught.txt", e.toString(), StandardOpenOption.CREATE,
						StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			fileName = null;
		}
		ERROR_FILE = fileName;
	}

	/*
	 * Date: 19 Jul 2021-----------------------------------------------------------
	 * Time created: 12:18:27---------------------------------------------------
	 */
	/**
	 * Instantiates this object
	 * 
	 * @throws InstantiationException if the internal manager is non-null;
	 */
	private UncaughtExceptionManager() throws InstantiationException {
		if (manager == null) {
			list = new ArrayList<>();
			return;
		}
		throw new InstantiationException(ExceptionMessage.SINGLETON_CONSTRUCTOR_ERROR.getLocalizedMessage());
	}

	/*
	 * Date: 21 Jul 2021-----------------------------------------------------------
	 * Time created: 17:18:39---------------------------------------------------
	 * Package: mathaid------------------------------------------------ Project:
	 * LatestPoject2------------------------------------------------ File:
	 * UncaughtExceptionManager.java------------------------------------------------
	 * ------ Class name:
	 * MathaidExceptionHolder------------------------------------------------
	 */
	/**
	 * A copy-cat of {@code MathaidException} that holds fields corresponding to
	 * values returned by the {@code MathaidException} interface. As the sole
	 * implementer of the {@code MathaidException} interface ({@code BaseException})
	 * cannot be constructed successfully, this class holds relevant fields of that
	 * class that are needed for debugging.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 *
	 */
	private static class MathaidExceptionHolder {
		/*
		 * Date: 21 Jul 2021-----------------------------------------------------------
		 * Time created: 17:23:24---------------------------------------------------
		 */
		/**
		 * Creates a {@code MathaidExceptionHolder} object
		 * 
		 * @param t    the reason for the exception
		 * @param msg  the field corresponding to
		 *             {@link MathaidException#getLocalizable()}
		 * @param args the field corresponding to
		 *             {@link MathaidException#getArguments()}
		 */
		private MathaidExceptionHolder(Throwable t, LocalizableMessage msg, Object[] args) {
			this.t = t;
			this.msg = msg;
			this.args = args;
		}

		/**
		 * For localisation when strings are written to the mathaid error file
		 */
		private final LocalizableMessage msg;
		/**
		 * Varargs that are stored as an object array. This used as arguments for the
		 * parameters specified in {@link LocalizableMessage#getLocalizedMessage()}
		 */
		private final Object[] args;
		/**
		 * A {@code RuntimeException} object that represents the exception or error
		 */
		private final Throwable t;
	}

	/*
	 * Date: 19 Jul 2021-----------------------------------------------------------
	 * Time created: 12:21:46--------------------------------------------
	 */
	/**
	 * Registers the given {@code Throwable} to the mathaid stack with a specified
	 * localised message.
	 * 
	 * @param t    the {@code java.lang.Throwable} that caused the
	 *             {@code MathaidException} to be called in the first place
	 * @param msg  a message that is locale specified and may be retrieved by the
	 *             programmer for the purpose of displaying it to the user of their
	 *             software
	 * @param args varargs for the {@code LocalizableMessage} to consume.
	 *             {@code LocalizableMessage} may contain arguments as specified in
	 *             {@link java.text.MessageFormat}, these arguments are parsed along
	 *             with the {@code LocalizableMessage} to from a human-readable
	 *             string.
	 * @throws NullPointerException if the {@code LocalizableMessage} and the
	 *                              {@code Throwable} argument are {@code null}.
	 *                              this exception is not written to the mathaid
	 *                              error file nor is it locale-sensitive
	 */
	public void register(Throwable t, LocalizableMessage msg, Object... args) {
		list.add(new MathaidExceptionHolder(t, msg, args));
	}

	/*
	 * Date: 22 Jul 2021-----------------------------------------------------------
	 * Time created: 10:07:17--------------------------------------------
	 */
	/**
	 * Registers the {@code Throwable t} in the mathaid error file the
	 * {@code LocalizableMessage} specified by {@link Message#UNCAUGHT_EXCEPTION}.
	 * 
	 * @param t   the exception to be documented
	 * @param obj any additional info to be documented along with the exception
	 */
	public void register(Throwable t, Object obj) {
		register(t, ExceptionMessage.UNCAUGHT_EXCEPTION, obj);
	}

	/*
	 * Date: 22 Jul 2021-----------------------------------------------------------
	 * Time created: 10:16:40--------------------------------------------
	 */
	/**
	 * Retrieves this session's {@code UncaughtExceptionManager} object. Since this
	 * class is designed as a singleton object, only one instance of this must
	 * exist. However, because of the behaviour of this object at runtime, this
	 * object gets destroy immediately after it is created because of
	 * {@link #endSession()}, so this method should be called more often than
	 * expected when an exception is thrown.
	 * 
	 * @return a valid {@code UncaughtExceptionManager} object.
	 */
	public static UncaughtExceptionManager getManager() {
		if (manager == null)
			try {
				manager = new UncaughtExceptionManager();
			} catch (InstantiationException e) {
				e.printStackTrace();
			}
		return manager;
	}

	/*
	 * Date: 22 Jul 2021-----------------------------------------------------------
	 * Time created: 10:22:00--------------------------------------------
	 */
	/**
	 * Writes the thrown exception to the error file, and nullifies the internal
	 * error manager then returns <code>true</code> if error was successfully
	 * documented or {@code false} if otherwise. The format of the error report is
	 * the same as {@link Throwable#printStackTrace()}. However depending on the
	 * {@code LocaleSelection}, the message may be localized. The format is thus:
	 * <ol>
	 * <li>The current date using <code>Calendar.getInstance(Locale.UK)</code> in
	 * it's own line</li>
	 * <li>The {@code LocalizedMessage} in it's own line</li>
	 * <li>The exception's trace message using
	 * Throwable.printStackTrace(PrintStream) in it own line</li>
	 * <li>The asterisk (&times;500) character in it's own line</li>
	 * <li>A new line</li>
	 * </ol>
	 * If all these fail, then the exception that made it fail is caught, after
	 * which the {@code java.util.Date()} object's toString() method, a
	 * {@link Device#lineSeparator() line separator} and the exception's
	 * getMessage() are all written to a file named &lsquo;uncaught.txt&rsquo;, and
	 * false is returned. If this fails then a forced shutdown via
	 * {@link System#exit(int)} is called with argument {@code 1}.
	 * 
	 * @return {@code true} if the exception was documented without errors or
	 *         {@code false} if otherwise
	 * @throws NullPointerException if the error file path provided is null. It can
	 *                              be null if certain fails. Please see
	 *                              {@link #ERROR_FILE} for more details. This
	 *                              exception is written to the appropriate error
	 *                              file
	 * @throws IOException          if the file could not be created or written to.
	 *                              This exception may or may not be written to an
	 *                              error file.
	 * @apiNote TODO: please remember to include the current version of this api in the error message
	 */
	public boolean endSession() {
		try (PrintStream ps = new PrintStream(
				new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(ERROR_FILE, true))));
				UncaughtExceptionManager m = this) {
			ps.println(Calendar.getInstance(Locale.UK));
			for (MathaidExceptionHolder e : list) {
				String s = MessageFormat.format(e.msg.getLocalizedMessage(), e.args);
				ps.println(s);
				e.t.printStackTrace(ps);
			}
			for (int i = 0; i < 500; i++)
				ps.print("*");
			ps.println();
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
			try {
				Utility.writeTextToFile("", "uncaught.txt", new Date() + Device.lineSeparator() + e.toString(),
						StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			} catch (IOException e1) {
				e1.printStackTrace();
				System.exit(1);
			}
			return false;
		}
		return true;
	}

	/*
	 * Most Recent Date: 19 Jul 2021-----------------------------------------------
	 * Most recent time created: 13:35:52--------------------------------------
	 */
	/*
	 * This just nullifies the collection that keeps tracks of the errors This also
	 * nullifies the internal exception manager.
	 */
	/**
	 * Does nothing for now.
	 */
	@Override
	public void close() {
//		for (int i = 0; i < list.size(); i++)
//			list.remove(i);
//		list = null;
//		manager = null;
	}

	/**
	 * A list to store {@code MathaidExceptionHolder} objects awaiting being written
	 * to the underlying file system
	 */
	private List<MathaidExceptionHolder> list;

	/**
	 * The internal manager that enforces the singleton design pattern on this class
	 */
	private static UncaughtExceptionManager manager;
}
