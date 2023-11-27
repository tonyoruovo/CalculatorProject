/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/*
 * Date: 27 Aug 2022----------------------------------------------------------- 
 * Time created: 22:13:27---------------------------------------------------  
 * Package: calculator.typeset------------------------------------------------ 
 * Project: InputTestCode------------------------------------------------ 
 * File: Log.java------------------------------------------------------ 
 * Class name: Log------------------------------------------------ 
 */
/**
 * Logs messages in the
 * {@link Segment#toString(Appendable, Log, java.util.List)} method.
 * <p>
 * Errors, warnings and general information are stored in a stacked-based
 * collection. Each of these can be created, retrieved and deleted using one of
 * logXxx, getXxx and removeXxx respectively, the number of messages can also be
 * gotten via one of numOfXxx.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Log {

	/**
	 * Used by this class as a key for accessing error messages from the error
	 * stack.
	 */
	private static final byte ERROR = 0;
	/**
	 * Used by this class as a key for accessing warning messages from the warn
	 * stack.
	 */
	private static final byte WARNING = 1;
	/**
	 * Used by this class as a key for accessing information messages from the info
	 * stack.
	 */
	private static final byte INFO = 2;

	/*
	 * 
	 * Date: 26 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:18:56 ---------------------------------------------------
	 */
	/**
	 * Constructor for creating a {@code mathaid.calculator.base.typeset.Log}
	 * object.
	 */
	public Log() {
		messages = new HashMap<>();
		messages.put(ERROR, new ArrayDeque<>());
		messages.put(WARNING, new ArrayDeque<>());
		messages.put(INFO, new ArrayDeque<>());
	}

	/*
	 * Date: 26 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:06:41 ---------------------------------------------------
	 */
	/**
	 * Removes the last error message from the error stack and returns the deleted
	 * value. Will return <code>null</code> if no error message was ever pushed into
	 * the error stack.
	 * 
	 * @return the deleted message.
	 */
	public String removeLatestError() {
		return messages.get(ERROR).pop();
	}

	/*
	 * Date: 26 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:20:38 ---------------------------------------------------
	 */
	/**
	 * Removes the last warning message from the warn stack and returns the deleted
	 * value. Will return <code>null</code> if no warning message was ever pushed
	 * into the error stack.
	 * 
	 * @return the deleted message.
	 */
	public String removeLatestWarning() {
		return messages.get(WARNING).pop();
	}

	/*
	 * Date: 26 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:20:38 ---------------------------------------------------
	 */
	/**
	 * Removes the last information message from the info stack and returns the
	 * deleted value. Will return <code>null</code> if no information message was
	 * ever pushed into the error stack.
	 * 
	 * @return the deleted message.
	 */
	public String removeLatestInfo() {
		return messages.get(INFO).pop();
	}

	/*
	 * Date: 26 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:20:38 ---------------------------------------------------
	 */
	/**
	 * Retrieves the last error message from the error stack. Will return
	 * <code>null</code> if no error message was ever pushed into the error stack.
	 * 
	 * @return the retrieved message.
	 */
	public String getLatestError() {
		return messages.get(ERROR).peek();
	}

	/*
	 * Date: 26 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:20:38 ---------------------------------------------------
	 */
	/**
	 * Retrieves the last warning message from the warn stack. Will return
	 * <code>null</code> if no warning message was ever pushed into the error stack.
	 * 
	 * @return the retrieved message.
	 */
	public String getLatestWarning() {
		return messages.get(WARNING).peek();
	}

	/*
	 * Date: 26 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:20:38 ---------------------------------------------------
	 */
	/**
	 * Retrieves the last information message from the info stack. Will return
	 * <code>null</code> if no information message was ever pushed into the error
	 * stack.
	 * 
	 * @return the retrieved message.
	 */
	public String getLatestInfo() {
		return messages.get(INFO).peek();
	}

	/*
	 * Date: 26 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:24:00 ---------------------------------------------------
	 */
	/**
	 * Logs the given error message by pushing it into the error stack.
	 * 
	 * @param msg the error message to be logged.
	 */
	public void logErrorMsg(String msg) {
		messages.get(ERROR).push(msg);
	}

	/*
	 * Date: 26 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:24:00 ---------------------------------------------------
	 */
	/**
	 * Logs the given warning message by pushing it into the warn stack.
	 * 
	 * @param msg the warning message to be logged.
	 */
	public void logWarningMsg(String msg) {
		messages.get(WARNING).push(msg);
	}

	/*
	 * Date: 26 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:24:00 ---------------------------------------------------
	 */
	/**
	 * Logs the given information message by pushing it into the info stack.
	 * 
	 * @param msg the information message to be logged.
	 */
	public void logInfoMsg(String msg) {
		messages.get(INFO).push(msg);
	}

	/*
	 * Date: 26 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:26:30 ---------------------------------------------------
	 */
	/**
	 * Gets the total number of messages in the error stack.
	 * 
	 * @return the total number of error messages logged.
	 */
	public int numOfErrorMessages() {
		return messages.get(ERROR).size();
	}

	/*
	 * Date: 26 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:26:30 ---------------------------------------------------
	 */
	/**
	 * Gets the total number of messages in the warn stack.
	 * 
	 * @return the total number of warning messages logged.
	 */
	public int numOfWarningMessages() {
		return messages.get(WARNING).size();
	}

	/*
	 * Date: 26 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:26:30 ---------------------------------------------------
	 */
	/**
	 * Gets the total number of messages in the info stack.
	 * 
	 * @return the total number of information messages logged.
	 */
	public int numOfInfoMessages() {
		return messages.get(INFO).size();
	}

	/**
	 * A map that holds mappings for error, warning and information messages.
	 */
	private final Map<Byte, Deque<String>> messages;

}
