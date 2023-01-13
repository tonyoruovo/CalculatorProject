/**
 * 
 */
package mathaid.calculator.error;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

/*
 * Date: 26 Apr 2020----------------------------------------------------
 * Time created: 13:11:52--------------------------------------------
 * Package: mathaid.calculator.error-----------------------------------------
 * Project: LatestPoject2-----------------------------------------
 * File: ErrorLogger.java-----------------------------------------------
 * Class name: ErrorLogger-----------------------------------------
 */
/**
 * A non exception {@code class} that tracks all the list of errors in this
 * calculator.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class ErrorLogger {
	
	public ErrorLogger() {
		log = new ArrayDeque<>();
	}
	
	public Collection<NullPointerException> getLog() {
		return log;
	}

	private Deque<NullPointerException> log;
}
