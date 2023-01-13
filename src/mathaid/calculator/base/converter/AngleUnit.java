/**
 * 
 */
package mathaid.calculator.base.converter;

import java.math.BigDecimal;
import java.math.MathContext;

import mathaid.calculator.base.util.Constants;

/*
 * Date: 22 Jun 2020-----------------------------------------------------------
 * Time created: 19:10:10---------------------------------------------------
 * Package: calculator------------------------------------------------ Project:
 * MyTempTest------------------------------------------------ File:
 * Settings.java------------------------------------------------------ Class
 * name: AngleUnit------------------------------------------------
 */
/**
 * <p>
 * An object that represents the angular units of measurements e.g degree,
 * radians which are used for measuring angles in mostly trigonometry but also
 * other areas of geometry as well. This class represents the units of
 * trigonometry as it relates to angular measurements.
 * </p>
 * <p>
 * It has a method
 * {@link #convert(BigDecimal, AngleUnit, MathContext)} for converting between
 * angular units, and a {@code MathContext} object to control the values
 * returned by the method.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 */
public enum AngleUnit implements Convertible<AngleUnit, BigDecimal, MathContext> {

	/**
	 * Represents a degree - the measurement of a plane angle that is defined as the
	 * full rotation being equal to 360 - the angular unit which is
	 * <sup>1</sup>/<sub>360</sub> of a full turn.
	 */
	DEG {
		@Override
		public BigDecimal convert(BigDecimal x, AngleUnit to, MathContext c) {
			switch (to) {
			case DEG:
			default:
				return x.round(c);
			case GRAD:
				x = ulpConvert(x, c.getPrecision());
				BigDecimal tenNinth = BigDecimal.TEN.divide(new BigDecimal("9.0"), c);
				x = x.multiply(tenNinth, c);
				return x;
			case RAD:
				x = ulpConvert(x, c.getPrecision());
				x = x.multiply(Constants.degreesToRadians(c.getPrecision()), c);
				return x;
			case TURN:
				x = ulpConvert(x, c.getPrecision());
				BigDecimal theta = Constants.ONE.divide(new BigDecimal(360), c);
				x = x.multiply(theta, c);
				return x;
			case BINARY_DEG:
				x = ulpConvert(x, c.getPrecision());
				BigDecimal bdeg = new BigDecimal(256).divide(new BigDecimal(360), c);
				return x.multiply(bdeg, c);
			}
		}
	},
	/**
	 * Represents an angular unit of a radian (the SI unit for measuring angles)
	 * which is about <sup>&pi;</sup>/<sub>180</sub> of an angle in degrees.
	 */
	RAD {
		@Override
		public BigDecimal convert(BigDecimal x, AngleUnit to, MathContext c) {
			switch (to) {
			case RAD:
			default:
				return x.round(c);
			case DEG:
				x = ulpConvert(x, c.getPrecision());
				return x.multiply(Constants.radiansToDegrees(c.getPrecision()), c);
			case GRAD:
				x = ulpConvert(x, c.getPrecision());
				BigDecimal grad = new BigDecimal("200.0").divide(Constants.pi(c.getPrecision()), c);
				x = x.multiply(grad, c);
				return x;
			case TURN:
				x = ulpConvert(x, c.getPrecision());
				BigDecimal turn = Constants.pi(c.getPrecision()).multiply(Constants.TWO);
				turn = Constants.ONE.divide(turn, c);
				x = x.multiply(turn, c);
				return x;
			case BINARY_DEG:
				x = ulpConvert(x, c.getPrecision());
				BigDecimal bdeg = Constants.pi(c.getPrecision()).multiply(Constants.TWO);
				bdeg = new BigDecimal(256).divide(bdeg, c);
				x = x.multiply(bdeg, c);
				return x;
			}
		}
	},
	/**
	 * Represents an angle in gradians - the unit that is defined as
	 * <sup>1</sup>/<sub>100</sub> of a right angle.
	 */
	GRAD {
		@Override
		public BigDecimal convert(BigDecimal x, AngleUnit to, MathContext c) {
			switch (to) {
			case GRAD:
			default:
				return x.round(c);
			case DEG:
				x = ulpConvert(x, c.getPrecision());
				BigDecimal deg = new BigDecimal("9.0").divide(BigDecimal.TEN, c);
				x = x.multiply(deg, c);
				return x;
			case RAD:
				x = ulpConvert(x, c.getPrecision());
				BigDecimal rad = Constants.pi(c.getPrecision()).divide(new BigDecimal("200.0"), c);
				x = x.multiply(rad, c);
				return x;
			case TURN:
				x = ulpConvert(x, c.getPrecision());
				BigDecimal turn = Constants.ONE.divide(new BigDecimal(400), c);
				x = x.multiply(turn, c);
				return x;
			case BINARY_DEG:
				x = ulpConvert(x, c.getPrecision());
				BigDecimal bdeg = new BigDecimal(256).divide(new BigDecimal(400), c);
				return x.multiply(bdeg, c);
			}
		}
	},
	/**
	 * Represents the unit of plane angle measurement that is equal to 360 degrees.
	 */
	TURN {

		@Override
		public BigDecimal convert(BigDecimal x, AngleUnit to, MathContext c) {
			switch (to) {
			case TURN:
			default:
				return x.round(c);
			case DEG:
				x = ulpConvert(x, c.getPrecision());
				return x.multiply(new BigDecimal(360), c);
			case GRAD:
				x = ulpConvert(x, c.getPrecision());
				return x.multiply(new BigDecimal(400), c);
			case RAD:
				x = ulpConvert(x, c.getPrecision());
				BigDecimal theta = Constants.pi(c.getPrecision()).multiply(Constants.TWO);
				return x.multiply(theta, c);
			case BINARY_DEG:
				x = ulpConvert(x, c.getPrecision());
				return x.multiply(new BigDecimal(256), c);
			}
		}

	},
	/**
	 * A representation of the angular unit that is called binary degree which is
	 * equivalent to <sup>1</sup>/<sub>256</sub> of a turn.
	 */
	BINARY_DEG {
		@Override
		public BigDecimal convert(BigDecimal x, AngleUnit to, MathContext c) {
			switch (to) {
			case BINARY_DEG:
			default:
				return x.round(c);
			case DEG:
				x = ulpConvert(x, c.getPrecision());
				BigDecimal deg = new BigDecimal(360).divide(new BigDecimal(256), c);
				return x.multiply(deg, c);
			case GRAD:
				x = ulpConvert(x, c.getPrecision());
				BigDecimal grad = new BigDecimal(400).divide(new BigDecimal(256), c);
				return x.multiply(grad, c);
			case RAD:
				x = ulpConvert(x, c.getPrecision());
				BigDecimal rad = Constants.TWO.multiply(Constants.pi(c.getPrecision()));
				rad = rad.divide(new BigDecimal(256), c);
				return x.multiply(rad, c);
			case TURN:
				x = ulpConvert(x, c.getPrecision());
				BigDecimal turn = Constants.ONE.divide(new BigDecimal(256), c);
				return x.multiply(turn, c);
			}
		}
	};

	/*
	 * Most Recent Date: 29 Aug 2020-----------------------------------------------
	 * Most recent time created: 13:05:17--------------------------------------
	 */
	/**
	 * Calculates the value of x as it relates to the specified angular unit, then
	 * returns it rounded to provided {@code MathContext}. If the specified
	 * {@code AngleUnit} is the same unit as {@code this}, the x is rounded and
	 * returned as is.
	 * 
	 * @param x  the value to be converted.
	 * @param to the unit to which the conversion is made.
	 * @param c  the context settings used to round the result.
	 * @return x converted from {@code this} to the specified {@code AngleUnit}.
	 */
	@Override
	public abstract BigDecimal convert(BigDecimal x, AngleUnit to, MathContext c);

	/*
	 * Date: 20 Jan 2021-----------------------------------------------------------
	 * Time created: 13:46:20--------------------------------------------
	 */
	/**
	 * Calculates and approximates the {@code BigDecimal} using the given scale.
	 * @param x the number to be approximated.
	 * @param scale the number of digits within x to be considered significant.
	 * @return a {@code BigDecimap} approximated using the given significant digits.
	 */
	private static BigDecimal ulpConvert(final BigDecimal x, final int scale) {
		BigDecimal ulp = x.ulp();
		if (ulp.compareTo(BigDecimal.ONE) == 0 && scale > 3) {
			ulp = ulp.movePointLeft(scale - 2);
			ulp = x.add(ulp);
			return ulp;
		}
		return x;
	}

	/*
	 * Date: 12 Aug 2021----------------------------------------------------------- 
	 * Time created: 15:10:26--------------------------------------------
	 */
	/**
	 * Returns an {@code AngleUnit} from a value given by of {@link #ordinal()}.
	 * @param ordinal a value given by {@link #ordinal()}.
	 * @return an {@code AngleUnit}.
	 */
	public static AngleUnit fromOrdinal(int ordinal) {
		switch (ordinal) {
		case 1:
			return RAD;
		case 2:
			return GRAD;
		case 3:
			return TURN;
		case 4:
			return BINARY_DEG;
		case 0:
		default:
			return DEG;
		}
	}
}