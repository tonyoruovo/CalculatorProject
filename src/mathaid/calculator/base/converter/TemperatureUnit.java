/**
 * 
 */
package mathaid.calculator.base.converter;

import java.math.BigDecimal;
import java.math.MathContext;

import mathaid.ExceptionMessage;

/*
 * Date: 5 Jul 2021----------------------------------------------------------- 
 * Time created: 17:01:33---------------------------------------------------  
 * Package: mathaid.calculator.base.function------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: TemperatureUnit.java------------------------------------------------------ 
 * Class name: TemperatureUnit------------------------------------------------ 
 */
/**
 * An object containing a set of constants that can convert values between temperature
 * units such as celsius and fahrenheit.
 * @author Oruovo Anthony Etineakpopha
 */
public enum TemperatureUnit implements Convertible<TemperatureUnit, BigDecimal, MathContext> {
	/**
	 * A constant representing Celsius.
	 */
	CELSIUS {
		@Override
		public BigDecimal convert(BigDecimal x, TemperatureUnit to, MathContext mc) {
			switch (to) {
			case CELSIUS:
				return x.round(mc);
			case DELISLE:
				return BigDecimal.valueOf(100).subtract(x, mc)
						.multiply(BigDecimal.valueOf(3).divide(BigDecimal.valueOf(2), mc), mc);
			case FAHRENHEIT:
				return x.multiply(BigDecimal.valueOf(9).divide(BigDecimal.valueOf(5), mc), mc).add(new BigDecimal(32),
						mc);
			case KELVIN:
				return x.add(new BigDecimal("273.15"), mc);
			case NEWTON:
				return x.multiply(BigDecimal.valueOf(33).divide(BigDecimal.valueOf(100), mc), mc);
			case RANKINE:
				return x.add(new BigDecimal("273.15"), mc)
						.multiply(BigDecimal.valueOf(9).divide(BigDecimal.valueOf(5), mc), mc);
			case RéAUMUR:
				return x.multiply(BigDecimal.valueOf(4).divide(BigDecimal.valueOf(5), mc), mc);
			case RøMER:
				return x.multiply(BigDecimal.valueOf(21).divide(BigDecimal.valueOf(40), mc), mc)
						.add(new BigDecimal("7.5"), mc);
			default:
			}
			new mathaid.IllegalArgumentException(ExceptionMessage.UNKNOWN_UNIT);
			throw new IllegalArgumentException("Unknown unit");
		}
	},
	/**
	 * A constant representing Fahrenheit.
	 */
	FAHRENHEIT {
		@Override
		public BigDecimal convert(BigDecimal x, TemperatureUnit to, MathContext mc) {
			switch (to) {
			case CELSIUS:
				return x.subtract(BigDecimal.valueOf(32), mc)
						.multiply(BigDecimal.valueOf(5).divide(BigDecimal.valueOf(9), mc), mc);
			case FAHRENHEIT:
				return x.round(mc);
			default:
				return CELSIUS.convert(convert(x, CELSIUS, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing Kelvin.
	 */
	KELVIN {
		@Override
		public BigDecimal convert(BigDecimal x, TemperatureUnit to, MathContext mc) {
			switch (to) {
			case CELSIUS:
				return x.subtract(new BigDecimal("279.15"), mc);
			case KELVIN:
				return x.round(mc);
			default:
				return CELSIUS.convert(convert(x, CELSIUS, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing Rankine.
	 */
	RANKINE {
		@Override
		public BigDecimal convert(BigDecimal x, TemperatureUnit to, MathContext mc) {
			switch (to) {
			case CELSIUS:
				return x.subtract(new BigDecimal("491.67"), mc)
						.multiply(BigDecimal.valueOf(5).divide(BigDecimal.valueOf(9), mc), mc);
			case RANKINE:
				return x.round(mc);
			default:
				return CELSIUS.convert(convert(x, CELSIUS, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing Delisle.
	 */
	DELISLE {
		@Override
		public BigDecimal convert(BigDecimal x, TemperatureUnit to, MathContext mc) {
			switch (to) {
			case CELSIUS:
				return BigDecimal.valueOf(100).subtract(x, mc)
						.multiply(BigDecimal.valueOf(2).divide(BigDecimal.valueOf(3), mc), mc);
			case DELISLE:
				return x.round(mc);
			default:
				return CELSIUS.convert(convert(x, CELSIUS, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing Newton.
	 */
	NEWTON {
		@Override
		public BigDecimal convert(BigDecimal x, TemperatureUnit to, MathContext mc) {
			switch (to) {
			case CELSIUS:
				return x.multiply(BigDecimal.valueOf(100).divide(BigDecimal.valueOf(33), mc), mc);
			case NEWTON:
				return x.round(mc);
			default:
				return CELSIUS.convert(convert(x, CELSIUS, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing Réaumur.
	 */
	RéAUMUR {
		@Override
		public BigDecimal convert(BigDecimal x, TemperatureUnit to, MathContext mc) {
			switch (to) {
			case CELSIUS:
				return x.multiply(BigDecimal.valueOf(5).divide(BigDecimal.valueOf(4), mc), mc);
			case RéAUMUR:
				return x.round(mc);
			default:
				return CELSIUS.convert(convert(x, CELSIUS, mc), to, mc);
			}
		}
	},
	/**
	 * Rømer
	 */
	RøMER {
		@Override
		public BigDecimal convert(BigDecimal x, TemperatureUnit to, MathContext mc) {
			switch (to) {
			case CELSIUS:
				return x.subtract(new BigDecimal("7.5"), mc)
						.multiply(BigDecimal.valueOf(40).divide(BigDecimal.valueOf(21), mc), mc);
			case RøMER:
				return x.round(mc);
			default:
				return CELSIUS.convert(convert(x, CELSIUS, mc), to, mc);
			}
		}
	};

	/*
	 * Most Recent Date: 12 Aug 2021-----------------------------------------------
	 * Most recent time created: 21:21:56--------------------------------------
	 */
	/**
	 * Returns the value of the given {@code BigDecimal} converted from {@code this}
	 * to the {@code TemperatureUnit} argument using the given {@code MathContext} for
	 * numeric approximations.
	 * 
	 * @param x  the value to be converted.
	 * @param to the destination of the conversion.
	 * @param mc the object for approximation.
	 * @return the given value converted from {@code this} to the type argument.
	 */
	@Override
	public abstract BigDecimal convert(BigDecimal x, TemperatureUnit to, MathContext mc);
}
