/**
 * 
 */
package mathaid.calculator.base;

import mathaid.calculator.base.util.Utility;
import mathaid.calculator.base.value.FloatAid;
import mathaid.calculator.base.value.FloatPoint;
import mathaid.calculator.base.value.IntValue.Radix;

/*
 * Date: 28 Jun 2021----------------------------------------------------------- 
 * Time created: 12:16:24---------------------------------------------------  
 * Package: mathaid.calculator.base------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: FloatingPointDetailsList.java------------------------------------------------------ 
 * Class name: FloatingPointDetailsList------------------------------------------------ 
 * 10.132865584e30
 */
/**
 * A {@code DetailsList} helper class. It can also be used to retrieve
 * floating-point numbers in TeX notation, but users should refrain from doing
 * that in isolation because values returned by methods are dependent upon the
 * current state of the {@code Settings} object.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
class FloatingPointDetailsList {
	/**
	 * The value to be converted.
	 */
	private final FloatPoint fp;

	/*
	 * Date: 12 Aug 2021-----------------------------------------------------------
	 * Time created: 14:02:04---------------------------------------------------
	 */
	/**
	 * Constructs this object with a given {@code FloatPoint}.
	 * 
	 * @param n the floating point number.
	 */
	public FloatingPointDetailsList(FloatPoint n) {
		this.fp = n;
	}

	/*
	 * Date: 12 Aug 2021-----------------------------------------------------------
	 * Time created: 14:02:59--------------------------------------------
	 */
	/**
	 * Gets this object's value as a {@code DataText} which contains the TeX
	 * notation corresponding to the value and the plain string value.
	 * 
	 * @return a {@code DataText} with the TeX notation and the plain string for
	 *         floating-point as its components.
	 */
	public DataText getExpression() {
		if (fp.isNaN() || fp.isInfinite())
			return new DataText(fp.toString(), fp.toString());
		return new DecimalDetailsList(fp.toString()).getExpression();
	}

	/*
	 * Date: 12 Aug 2021-----------------------------------------------------------
	 * Time created: 14:05:51--------------------------------------------
	 */
	/**
	 * Gets this object's value as a {@code DataText} which contains the TeX
	 * notation corresponding to the value in the given radix and the plain string
	 * value.
	 * 
	 * @param radix the radix in which the value is to be returned.
	 * @return a {@code DataText} with the TeX notation and the plain string for
	 *         floating-point as its components.
	 */
	public DataText getExpression(int radix) {
		if (fp.isNaN() || fp.isInfinite())
			return new DataText(fp.toString(), fp.toString());
		Settings s = Settings.defaultSetting();
		String num = fp.toString(getRadix(radix)), string = num;
		switch (radix) {
		case 2:
			num = Utility.toTexString(num, s.getDecimalPoint(), '~', s.getFracDivider(), 4);
			break;
		case 8:
			num = Utility.toTexString(num, s.getDecimalPoint(), '~', s.getFracDivider(), 2);
			break;
		case 16:
			num = Utility.toTexString(num, s.getDecimalPoint(), '~', s.getFracDivider(), 2);
			break;
		default:
			num = Utility.toTexString(num, s.getDecimalPoint(), s.getIntDivider(), s.getFracDivider(), 3);
		}

		return new DataText(num, string);
	}

	/*
	 * Date: 12 Aug 2021-----------------------------------------------------------
	 * Time created: 14:11:36--------------------------------------------
	 */
	/**
	 * Gets this object's normalised value as a {@code DataText} which contains the
	 * TeX notation corresponding to the value in the given radix and the plain
	 * string value.
	 * 
	 * @param radix the radix in which the value is to be returned.
	 * @return a {@code DataText} with the TeX notation and the plain string for
	 *         normalised floating-point as its components.
	 */
	public DataText getNormalised(int radix) {
		if (fp.isNaN() || fp.isInfinite())
			return new DataText(fp.toString(), fp.toString());
		Settings s = Settings.defaultSetting();
		String num = fp.normalise(getRadix(radix));
		final String string = num;

		switch (radix) {
		case 2:
			num = Utility.toTexString(num.substring(0, num.indexOf('p')), s.getDecimalPoint(), '~', s.getFracDivider(),
					4);
			break;
		case 8:
			num = Utility.toTexString(num.substring(0, num.indexOf('p')), s.getDecimalPoint(), '~', s.getFracDivider(),
					2);
			break;
		case 16:
			num = Utility.toTexString(num.substring(0, num.indexOf('p')), s.getDecimalPoint(), '~', s.getFracDivider(),
					2);
			break;
		default:
			num = Utility.toTexString(num.substring(0, num.indexOf('p')), s.getDecimalPoint(), s.getIntDivider(),
					s.getFracDivider(), 3);
		}

		num = num.concat(s.getMultiplicationSign() + "2").concat("^{");// .concat(string.substring(string.indexOf('p') +
																		// 1));
		IntegerDetailsList idl = new IntegerDetailsList(string.substring(string.indexOf('p') + 1));
		return new DataText(num.concat(idl.getExpression(Radix.DEC, false).getTeXString().concat("}")), string);
	}

	/*
	 * Date: 12 Aug 2021-----------------------------------------------------------
	 * Time created: 14:12:40--------------------------------------------
	 */
	/**
	 * Returns the IEEE754 name for the calculator's current internal precision.
	 * 
	 * @return one of the constant names of the {@link Precision} class enclosed in
	 *         a {@code DataText} object.
	 */
	public DataText getPrecision() {
		String s = String.valueOf(fp.type().getBitLength());
		return new DataText(s, s);
	}

	/*
	 * Date: 12 Aug 2021-----------------------------------------------------------
	 * Time created: 14:15:44--------------------------------------------
	 */
	/**
	 * Returns the total number of bits in the significand of the value.
	 * 
	 * @return bit length of the significand.
	 */
	public DataText getSignificandBitsForCurrentPrecision() {
		String s = String.valueOf(fp.type().getSignificandBits());
		IntegerDetailsList idl = new IntegerDetailsList(s);
		return idl.getExpression(Radix.DEC, false);
	}

	/*
	 * Date: 12 Aug 2021-----------------------------------------------------------
	 * Time created: 14:17:38--------------------------------------------
	 */
	/**
	 * Returns the bit pattern of the significand as a decimal integer in two's
	 * complement.
	 * 
	 * @return the 2's complement interpretation of the significand in decimal form.
	 */
	public DataText getSignificand() {// 2's complement
		String s = String.valueOf(FloatAid.getSignificanBits(fp));
		IntegerDetailsList idl = new IntegerDetailsList(s);
		return idl.getExpression(Radix.DEC, false);
	}

	/*
	 * Date: 12 Aug 2021-----------------------------------------------------------
	 * Time created: 14:39:02--------------------------------------------
	 */
	/**
	 * Returns the total number of bits in the exponent of the value.
	 * 
	 * @return bit length of the exponent field.
	 */
	public DataText getExponentBitsForCurrentPrecision() {
		String s = String.valueOf(fp.type().getExponentBits());
		IntegerDetailsList idl = new IntegerDetailsList(s);
		return idl.getExpression(Radix.DEC, false);
	}

	/*
	 * Date: 12 Aug 2021-----------------------------------------------------------
	 * Time created: 14:39:53--------------------------------------------
	 */
	/**
	 * Returns the bit pattern of the biased exponent as a decimal integer.
	 * 
	 * @return the interpretation of the biased exponent in decimal form.
	 */
	public DataText getBiasedExponent() {
		String s = String.valueOf(FloatAid.getExponentBits(fp));
		IntegerDetailsList idl = new IntegerDetailsList(s);
		return idl.getExpression(Radix.DEC, false);
	}

	/*
	 * Date: 12 Aug 2021-----------------------------------------------------------
	 * Time created: 14:41:36--------------------------------------------
	 */
	/**
	 * Returns the bit pattern of the exponent as a decimal integer in excess-n
	 * notation.
	 * 
	 * @return the excess-n interpretation of the exponent in decimal form.
	 */
	public DataText getExponent() {
		String s = String.valueOf(fp.getExponent());
		IntegerDetailsList idl = new IntegerDetailsList(s);
		return idl.getExpression(Radix.DEC, false);
	}

	/*
	 * Date: 12 Aug 2021-----------------------------------------------------------
	 * Time created: 14:42:36--------------------------------------------
	 */
	/**
	 * Returns the bit pattern (as per IEE754) of the value as a decimal integer in
	 * two's complement.
	 * 
	 * @return the 2's complement interpretation of the value in decimal form.
	 */
	public DataText getFullBits() {// 2's complement
		String s = String.valueOf(fp.toBits());
		IntegerDetailsList idl = new IntegerDetailsList(s);
		return idl.getExpression(Radix.DEC, false);
	}

	/*
	 * Date: 12 Aug 2021-----------------------------------------------------------
	 * Time created: 14:44:09--------------------------------------------
	 *//**
		 * Returns a {@code Radix} object appropriate for the given argument which is
		 * specified by the int value for that base.
		 * 
		 * @param radix one of the values given by the constants 2, 8, 10, 16.
		 * @return a {@code Radix} object.
		 */
	private Radix getRadix(int radix) {
		return radix == 2 ? Radix.BIN : radix == 8 ? Radix.OCT : radix == 16 ? Radix.HEX : Radix.DEC;
	}

}
