package mathaid.calculator.error;

/**
 * 
 */

/**
 * Date: 29 Jan 2020<br>
 * Time created: 14:08:02<br>
 * Package: <br>
 * Project: LatestPoject2<br>
 * File: CalcSyntaxException.java<br>
 * Class name: CalcSyntaxException<br>
 *
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class CalcSyntaxException extends NullPointerException {

	/**
	 * Date: 17 Feb 2020<br>
	 * Time created: 15:49:28<br>
	 * <br>
	 *
	 * @param string
	 */
	public CalcSyntaxException(String msg) {
		super(msg);
	}

	public CalcSyntaxException() {
		super();
	}

	/**
	 * Date: 29 Jan 2020<br>
	 * Time created: 14:08:11<br>
	 *
	 * Field for the auto-generated serial.
	 */
	private static final long serialVersionUID = 1L;

}
