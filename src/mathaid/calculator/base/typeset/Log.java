/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.ArrayDeque;
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
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Log {
	
	private static final byte ERROR = 0;
	private static final byte WARNING = 1;
	private static final byte INFO = 2;

	/*
	 * Date: 27 Aug 2022----------------------------------------------------------- 
	 * Time created: 22:13:27--------------------------------------------------- 
	 */
	/**
	 */
	public Log() {
		messages = new HashMap<>();
	}
	
	public String removeLatestError() {
		return messages.get(ERROR).pop();
	}
	
	public String removeLatestWarning() {
		return messages.get(WARNING).pop();
	}
	
	public String removeLatestInfo() {
		return messages.get(INFO).pop();
	}
	
	public String getLatestError() {
		return messages.get(ERROR).peek();
	}
	
	public String getLatestWarning() {
		return messages.get(WARNING).peek();
	}
	
	public String getLatestInfo() {
		return messages.get(INFO).peek();
	}
	
	public void logErrorMsg(String msg) {
		messages.get(ERROR).push(msg);
	}
	
	public void logWarningMsg(String msg) {
		messages.get(WARNING).push(msg);
	}
	
	public void logInfoMsg(String msg) {
		messages.get(INFO).push(msg);
	}
	
	public int numOfErrorMessages() {
		return messages.get(ERROR).size();
	}
	
	public int numOfWarningMessages() {
		return messages.get(WARNING).size();
	}
	
	public int numOfInfoMessages() {
		return messages.get(INFO).size();
	}
	
	private final Map<Byte, ArrayDeque<String>> messages;

}
