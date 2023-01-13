/**
 * 
 */
package mathaid.calculator.error;

import java.math.BigInteger;

import mathaid.calculator.base.value.IntValue.Radix;
import mathaid.calculator.base.value.IntegerRepresentation;

/*
 * Date: 20 Feb 2020<br>
 * Time created: 11:08:49<br>
 * Package: <br>
 * Project: LatestPoject2<br>
 * File: CalcSyntaxException.java<br>
 * Class name: IntegerOverFlowException
 */
/**
 *
 * @author Oruovo Anthony Etineakpopha
 *
 */
public class IntegerOverFlowException extends ValueException {
	/*
	 * Date: 20 Feb 2020<br> Time created: 11:33:28
	 */
	/**
	 * Field for auto-generated serial.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private IntegerRepresentation rep;
	/**
	 * 
	 */
	private BigInteger difference;

	/*
	 * Date: 20 Feb 2020<br> Time created: 13:14:19
	 */
	/**
	 *
	 * @param message
	 * @param rep
	 */
	public IntegerOverFlowException(String message, IntegerRepresentation rep) {
		super(message);
		this.rep = rep;
	}

//	public IntegerOverFlowException(String decimal, String limit, BitLength min, BitLength current, BinaryRep currentRep) {
//		super();
//	}

	public IntegerOverFlowException(IntegerRepresentation rep) {
		super();
		this.rep = rep;
		BigInteger decimal = rep.getRadix() == Radix.DEC ? new BigInteger(rep.getValue())
				: new BigInteger(rep.toRadix(Radix.DEC));
		BigInteger limit = new BigInteger(decimal.signum() < 0 ? rep.getRange(null)[0] : rep.getRange(null)[1]);
		difference = limit.subtract(decimal);
	}

	/*
	 * Most Recent Date: 5 May 2020-------------------------------------------------
	 * Most recent time created: 10:50:49-----------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return the detail message string of this Throwable instance(which may be
	 *         null).
	 */
	@Override
	public String getMessage() {
		return "Overflow by " + difference + ". BitLength." + rep.getNumOfBits() + " is too big for BinaryRep."
				+ rep.getRep() + ". " + rep.getMinBits() + " is needed to use this value";
	}

	/*
	 * Date: 20 Feb 2020<br> Time created: 11:29:38
	 */
	/**
	 * Returns the difference between the value and the limit the bit can take
	 * 
	 * @return
	 */
	public BigInteger getDifference() {
		return difference;
	}
}
