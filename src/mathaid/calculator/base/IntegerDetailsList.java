/**
 * 
 */
package mathaid.calculator.base;

import java.math.BigDecimal;
import java.math.BigInteger;

import mathaid.calculator.base.util.Utility;
import mathaid.calculator.base.value.FloatPoint;
import mathaid.calculator.base.value.IntValue.BitLength;
import mathaid.calculator.base.value.IntValue.Radix;

/*
 * Date: 13 Apr 2021----------------------------------------------------------- 
 * Time created: 11:19:21---------------------------------------------------  
 * Package: mathaid.calculator.base------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: IntegerDetailsList.java------------------------------------------------------ 
 * Class name: IntegerDetailsList------------------------------------------------ 
 */
/**
 * A {@code DetailsList} helper class. It can also be used to retrieve integers
 * in TeX notation, but users should refrain from doing that in isolation
 * because values returned by methods are dependent upon the current state of
 * the {@code Settings} object.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
class IntegerDetailsList {

	/*
	 * Date: 13 Apr 2021-----------------------------------------------------------
	 * Time created: 11:19:21---------------------------------------------------
	 */
	/**
	 * Constructs this object with a {@code String} representation of an integer.
	 * 
	 * @param integer the string representation of an integer.
	 */
	public IntegerDetailsList(String integer) {
		this(integer, 10);
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 22:06:47---------------------------------------------------
	 */
	/**
	 * Constructs this object with a {@code String} representation of an integer in
	 * a given radix.
	 * 
	 * @param integer the string representation of an integer.
	 * @param radix   the radix (base) of the string value.
	 */
	public IntegerDetailsList(String integer, int radix) {
		this.integer = new BigInteger(integer, radix);
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 22:09:01--------------------------------------------
	 */
	/**
	 * Gets this object's integer value as a {@code DataText} which contains the TeX
	 * notation corresponding to the value and the plain string value.
	 * <p>
	 * The value returned may be in scientific notation if the significant digits
	 * are more than {@link Settings#getScale}. For a non approximated integer
	 * value, use {@link #getExpression(Radix, boolean)}.
	 * </p>
	 * 
	 * @return a {@code DataText} with the TeX notation and the plain string for
	 *         integers as its components.
	 */
	public DataText getExpression() {
		return new DecimalDetailsList(integer.toString()).getExpression();
	}

	/*
	 * 
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 22:09:07--------------------------------------------
	 */
	/**
	 * Gets this object's integer value in the given radix as a {@code DataText}
	 * which contains the TeX notation corresponding to the value and the plain
	 * string value.
	 * 
	 * @param radix       the radix to which the returned object should encode the
	 *                    number.
	 * @param approximate asks whether to approximate the number when
	 *                    {@link Setting#getScale} is less than the number of
	 *                    significant digits. If {@code true} then a decimal
	 *                    expression is returned.
	 * @return a {@code DataText} with the TeX notation and the plain string for
	 *         integers as its components.
	 */
	public DataText getExpression(Radix radix, boolean approximate) {
		if (approximate)
			return getExpression();// Do not attempt to format the result
		Settings s = Settings.defaultSetting();
		String tex = Utility.toTexString(integer.toString(radix.getRadix()), Character.MAX_VALUE,
				radix == Radix.DEC ? s.getIntDivider() : '~', Character.MAX_VALUE, s.getDigitsPerUnit());
		return new DataText(tex, integer.toString());
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 22:09:46--------------------------------------------
	 */
	/**Calls {@link FractionalDetailsList#getFactors()} and returns the result.
	 * @return {@link FractionalDetailsList#getFactors()}.
	 */
	public DataText getFactors() {
		return new FractionalDetailsList(new BigDecimal(integer)).getFactors();
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 22:09:54--------------------------------------------
	 */
	/**
	 * Assumes the integer value is a bit layout in IEEE754 notation and returns the floating point representation for it.
	 * @return the IEEE754 value that has the same bit layout as the internal integer.
	 */
	public DataText getAsFloatingPoint() {
		Settings s = Settings.defaultSetting();
		FloatPoint fp = FloatPoint.fromBits(integer, BitLength.valueOf(s.getBitLength()));
		return new FloatingPointDetailsList(fp).getExpression();
	}

	/**
	 * The internal integer value
	 */
	private final BigInteger integer;

}
