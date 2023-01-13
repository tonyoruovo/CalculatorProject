/**
 * 
 */
package mathaid.calculator.error;


/**
 * 
 * Date: 20 Feb 2020<br>
 * Time created: 11:08:53<br>
 * Package: <br>
 * Project: LatestPoject2<br>
 * File: CalcSyntaxException.java<br>
 * Class name: ValueException<br>
 * <br>
 *
 * @author Oruovo Anthony Etineakpopha
 *
 */
public class ValueException extends CalcSyntaxException {

	/**
	 * Date: 17 Feb 2020<br>
	 * Time created: 15:50:04<br>
	 * <br>
	 *
	 * @param msg
	 */
	public ValueException(String msg) {
		super(msg);
	}

	/**
	 * 
	 * Date: 20 Feb 2020<br>
	 * Time created: 11:10:48<br>
	 * <br>
	 *
	 */
	public ValueException() {
		super();
	}

	/**
	 * Date: 17 Feb 2020<br>
	 * Time created: 15:47:46<br>
	 * <br>
	 *
	 * Field for
	 */
	private static final long serialVersionUID = 1L;

}