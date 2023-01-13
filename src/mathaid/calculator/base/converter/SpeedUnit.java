/**
 * 
 */
package mathaid.calculator.base.converter;

import java.math.BigDecimal;
import java.math.MathContext;

import mathaid.ExceptionMessage;

/*
 * Date: 8 Jul 2021----------------------------------------------------------- 
 * Time created: 16:54:26---------------------------------------------------  
 * Package: mathaid.calculator.base.function------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: SpeedUnit.java------------------------------------------------------ 
 * Class name: SpeedUnit------------------------------------------------ 
 */
/**
 * An object containing a set of constants that can convert values between speed
 * units such as km/h.
 * @author Oruovo Anthony Etineakpopha
 */
public enum SpeedUnit implements Convertible<SpeedUnit, BigDecimal, MathContext> {
	/**
	 * A constant representing metres per second.
	 */
	METERS_PER_SECOND {
		@Override
		public BigDecimal convert(BigDecimal x, SpeedUnit to, MathContext mc) {
			switch (to) {
			case KILOMETERS_PER_HOUR:
				return new BigDecimal("3.6").multiply(x, mc);
			case METERS_PER_SECOND:
				return x.round(mc);
			default:
				return KILOMETERS_PER_HOUR.convert(convert(x, KILOMETERS_PER_HOUR, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing metres per hour.
	 */
	METERS_PER_HOUR {
		@Override
		public BigDecimal convert(BigDecimal x, SpeedUnit to, MathContext mc) {
			switch (to) {
			case KILOMETERS_PER_HOUR:
				return BigDecimal.ONE.divide(new BigDecimal("3.6E6")).multiply(x, mc);
			case METERS_PER_HOUR:
				return x.round(mc);
			default:
				return KILOMETERS_PER_HOUR.convert(convert(x, KILOMETERS_PER_HOUR, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing kilometres per second.
	 */
	KILOMETERS_PER_SECOND {
		@Override
		public BigDecimal convert(BigDecimal x, SpeedUnit to, MathContext mc) {
			switch (to) {
			case KILOMETERS_PER_HOUR:
				return new BigDecimal("3600").multiply(x, mc);
			case KILOMETERS_PER_SECOND:
				return x.round(mc);
			default:
				return KILOMETERS_PER_HOUR.convert(convert(x, KILOMETERS_PER_HOUR, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing kilometres per second.
	 */
	KILOMETERS_PER_HOUR {
		@Override
		public BigDecimal convert(BigDecimal x, SpeedUnit to, MathContext mc) {
			switch (to) {
			case FEET_PER_SECOND:
				return new BigDecimal("0.911344415281423155438903").multiply(x, mc);
			case KILOMETERS_PER_HOUR:
				return x.round(mc);
			case KILOMETERS_PER_SECOND:
				return BigDecimal.ONE.divide(new BigDecimal("3600"), mc).multiply(x, mc);
			case KNOTS:
				return new BigDecimal("0.539956803455723542116631").multiply(x, mc);
			case METERS_PER_HOUR:
				return new BigDecimal("1000").multiply(x, mc);
			case METERS_PER_SECOND:
				return new BigDecimal("5").divide(new BigDecimal("18"), mc).multiply(x, mc);
			case MILES_PER_HOUR:
				return new BigDecimal("0.621371192237333969617434").multiply(x, mc);
			case MILES_PER_SECOND:
				return new BigDecimal("0.000017260310895481499156").multiply(x, mc);
			default:
				break;
			}
			new mathaid.IllegalArgumentException(ExceptionMessage.UNKNOWN_UNIT);
			throw new IllegalArgumentException("Unknown unit");
		}
	},
	/**
	 * A constant representing feet per second.
	 */
	FEET_PER_SECOND {
		@Override
		public BigDecimal convert(BigDecimal x, SpeedUnit to, MathContext mc) {
			switch (to) {
			case KILOMETERS_PER_HOUR:
				return new BigDecimal("1.09728").multiply(x, mc);
			case FEET_PER_SECOND:
				return x.round(mc);
			default:
				return KILOMETERS_PER_HOUR.convert(convert(x, KILOMETERS_PER_HOUR, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing miles per second.
	 */
	MILES_PER_SECOND {
		@Override
		public BigDecimal convert(BigDecimal x, SpeedUnit to, MathContext mc) {
			switch (to) {
			case KILOMETERS_PER_HOUR:
				return new BigDecimal("5793.6384").multiply(x, mc);
			case MILES_PER_SECOND:
				return x.round(mc);
			default:
				return KILOMETERS_PER_HOUR.convert(convert(x, KILOMETERS_PER_HOUR, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing miles per hour.
	 */
	MILES_PER_HOUR {
		@Override
		public BigDecimal convert(BigDecimal x, SpeedUnit to, MathContext mc) {
			switch (to) {
			case KILOMETERS_PER_HOUR:
				return new BigDecimal("1.609344").multiply(x, mc);
			case MILES_PER_HOUR:
				return x.round(mc);
			default:
				return KILOMETERS_PER_HOUR.convert(convert(x, KILOMETERS_PER_HOUR, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing knots.
	 */
	KNOTS {

		@Override
		public BigDecimal convert(BigDecimal x, SpeedUnit to, MathContext mc) {
			switch (to) {
			case KILOMETERS_PER_HOUR:
				return new BigDecimal("1.852").multiply(x, mc);
			case KNOTS:
				return x.round(mc);
			default:
				return KILOMETERS_PER_HOUR.convert(convert(x, KILOMETERS_PER_HOUR, mc), to, mc);
			}
		}

	};

	/*
	 * Most Recent Date: 12 Aug 2021-----------------------------------------------
	 * Most recent time created: 21:16:44--------------------------------------
	 */
	/**
	 * Returns the value of the given {@code BigDecimal} converted from {@code this}
	 * to the {@code SpeedUnit} argument using the given {@code MathContext} for
	 * numeric approximations.
	 * 
	 * @param x  the value to be converted.
	 * @param to the destination of the conversion.
	 * @param mc the object for approximation.
	 * @return the given value converted from {@code this} to the type argument.
	 */
	@Override
	public abstract BigDecimal convert(BigDecimal x, SpeedUnit to, MathContext mc);
}
