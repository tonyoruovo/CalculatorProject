/**
 * 
 */
package mathaid.calculator.base.parser;

import java.util.List;

import mathaid.calculator.base.DetailsList;
import mathaid.calculator.base.History;
import mathaid.calculator.base.format.tex.input.InputSegment;
import mathaid.calculator.base.gui.KeyForm;
import mathaid.designpattern.Observer;
import mathaid.designpattern.Subject;

/*
 * Date: 23 Apr 2021----------------------------------------------------------- 
 * Time created: 20:51:37---------------------------------------------------  
 * Package: mathaid.calculator.base.parser------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: CEvaluator.java------------------------------------------------------ 
 * Class name: CEvaluator------------------------------------------------ 
 */
/**
 * A <b>C</b>alculator<b>Evaluator</b> that is also an {@code Observer} and a
 * {@code Subject}, that contains history of all the calculations made down to a
 * specified limit. As this evaluator is an observer, it may receive messages
 * from a source (such as a network client, the web, an input device etc), and
 * because it is also a subject, it may send messages to the same type of
 * channels.
 * <p>
 * This class contains a generic, to specify the type of data (messages) that
 * will be received via it's observer methods.
 * </p>
 * <p>
 * The {@code DetailsList} is for extra details about a calculation that may not
 * be sent with the answer to that calculation
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @param <R> the type of messages to receive and send both as an observer and a
 *            subject respectively
 * @see Observer
 * @see Subject
 */
public interface CEvaluator<R> extends Evaluator<R>, Subject<R>, Observer<R> /* , Processor<T, R> */ {

	public static final int LOG_ERROR = 0;
	public static final int LOG_WARNING = 1;
	public static final int LOG_INFO = 2;
	
	Thread getEvaluationThread();
	
	InputSegment getVariable(String var);
	
	void setVariable(String var, InputSegment segment);
	
	void logMessage(int logType, String msg);

	List<String> getLogMessages(int logType);
	
	KeyForm modifierKeyFor(int modKeyCode);
	
	KeyForm keyFor(int keyCode);
	
	boolean setOption(String optionName, Object newValue);

	Object getOption(String optionName);
	
	int getModifiers();
	
	void setModifiers(int modifiers);

	/*
	 * Date: 28 Jul 2021-----------------------------------------------------------
	 * Time created: 14:52:25--------------------------------------------
	 */
	/**
	 * Returns a {@code History} of calculations (not results) made.
	 * 
	 * @return a {@code History} object which contains prior calculations
	 */
	History<R> getHistory();

	/*
	 * Date: 28 Jul 2021-----------------------------------------------------------
	 * Time created: 14:58:24--------------------------------------------
	 */
	/**
	 * Gets the {@code DetailsList} object which contains extra details about a
	 * calculation that may not be part of it's answer. For example, a currency
	 * converter may calculate the current exchange rate between the US dollar and
	 * the Pound Sterling, when the answer is displayed, other details such as the
	 * history of the rate since a certain time, the exchange rate between the US
	 * dollar and popular currencies etc may be stored inside the
	 * {@code DetailsList}.
	 * 
	 * @return a {@code DetailsList} object.
	 */
	DetailsList getDetails();

	/*
	 * Date: 23 Aug 2021-----------------------------------------------------------
	 * Time created: 13:37:21--------------------------------------------
	 */
	/**
	 * Adds the current calculation result to the value in memory. If there is no
	 * value in memory then the current Calculation result is added to the integer
	 * 0. Note that current calculation result is not the same as getting the last
	 * history result, because getting the last history result requires the user to
	 * use the equals function, however the {@code Calculator} class automatically
	 * evaluates the current value entered even if the user does not press the
	 * "equals" button.
	 * <p>
	 * This is akin to the "M+" button on the calculator interface.
	 * 
	 * @return the current memory value after the current calculation result is
	 *         added to the previous memory value.
	 * @since 100100
	 */
	// XXX: version 100100
	R mPlus();

	/*
	 * Date: 23 Aug 2021-----------------------------------------------------------
	 * Time created: 13:37:13--------------------------------------------
	 */
	/**
	 * Subtracts the current calculation result from the value in memory. If there
	 * is no value in memory then the current calculation result is added to the
	 * integer 0. Note that the current calculation result is not the same as
	 * getting the last history result, because getting the last history result
	 * requires the user to use the equals function, however the {@code Calculator}
	 * class automatically evaluates the current value entered even if the user does
	 * not press the "equals" button.
	 * <p>
	 * This is akin to the "M-" button on the calculator interface.
	 * 
	 * @return the current memory value after the current calculation result is
	 *         subtracted from the previous memory value.
	 * @since 100100
	 */
	// XXX: version 100100
	R mMinus();
}
