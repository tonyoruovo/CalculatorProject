/**
 * 
 */
package mathaid.calculator.base.converter;

import java.math.BigDecimal;
import java.math.MathContext;

import mathaid.ExceptionMessage;

/*
 * Date: 12 Jul 2021----------------------------------------------------------- 
 * Time created: 12:01:06---------------------------------------------------  
 * Package: mathaid.calculator.base.converter------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: FuelConsumptionUnit.java------------------------------------------------------ 
 * Class name: FuelConsumptionUnit------------------------------------------------ 
 */
/**
 * An object containing a set of constants that represents fuel consumption units of
 * measurements e.g Hertz for measuring fuel consumption.
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public enum FuelConsumptionUnit implements Convertible<FuelConsumptionUnit, BigDecimal, MathContext> {
	/**
	 * A Constant representing litre per 100 kilometres.
	 */
	LITER_PER_100_KM {
		@Override
		public BigDecimal convert(BigDecimal x, FuelConsumptionUnit to, MathContext mc) {
			switch (to) {
			case KILOMETER_PER_LITER:
				return new BigDecimal(100).multiply(x, mc);
			case LITER_PER_100_KM:
				return x.round(mc);
			default:
				return KILOMETER_PER_LITER.convert(convert(x, KILOMETER_PER_LITER, mc), to, mc);
			}
		}
	},
	/**
	 * A Constant representing litre per 100 miles.
	 */
	LITER_PER_100_MILES {
		@Override
		public BigDecimal convert(BigDecimal x, FuelConsumptionUnit to, MathContext mc) {
			switch (to) {
			case KILOMETER_PER_LITER:
				return new BigDecimal("160.9344").multiply(x, mc);
			case LITER_PER_100_MILES:
				return x.round(mc);
			default:
				return KILOMETER_PER_LITER.convert(convert(x, KILOMETER_PER_LITER, mc), to, mc);
			}
		}
	},
	/**
	 * A Constant representing kilometre per liter.
	 */
	KILOMETER_PER_LITER {
		@Override
		public BigDecimal convert(BigDecimal x, FuelConsumptionUnit to, MathContext mc) {
			switch(to) {
			case KILOMETER_PER_GALLON_US:
				return new BigDecimal("3.785411784").multiply(x, mc);
			case KILOMETER_PER_LITER:
				return x.round(mc);
			case LITER_PER_100_KM:
				return new BigDecimal("100").multiply(x, mc);
			case LITER_PER_100_MILES:
				return new BigDecimal("160.9344").multiply(x, mc);
			case MILE_PER_GALLON_IMP:
				return new BigDecimal("2.824809363318221585938121").multiply(x, mc);
			case MILE_PER_GALLON_US:
				return new BigDecimal("112903").divide(new BigDecimal("48000"), mc).multiply(x, mc);
			case MILE_PER_LITER:
				return new BigDecimal("0.621371192333969617434").multiply(x, mc);
			case NAUTICAL_MILE_PER_LITER:
				return new BigDecimal("0.539956803455723542116631").multiply(x, mc);
			default:
			}
			new mathaid.IllegalArgumentException(ExceptionMessage.UNKNOWN_UNIT);
			throw new IllegalArgumentException("Unknown unit");
		}
	},
	/**
	 * A Constant representing kilometre per gallon in the US.
	 */
	KILOMETER_PER_GALLON_US {
		@Override
		public BigDecimal convert(BigDecimal x, FuelConsumptionUnit to, MathContext mc) {
			switch (to) {
			case KILOMETER_PER_LITER:
				return new BigDecimal("0.2641720523581484153799").multiply(x, mc);
			case KILOMETER_PER_GALLON_US:
				return x.round(mc);
			default:
				return KILOMETER_PER_LITER.convert(convert(x, KILOMETER_PER_LITER, mc), to, mc);
			}
		}
	},
	/**
	 * A Constant representing mile per liter.
	 */
	MILE_PER_LITER {
		@Override
		public BigDecimal convert(BigDecimal x, FuelConsumptionUnit to, MathContext mc) {
			switch (to) {
			case KILOMETER_PER_LITER:
				return new BigDecimal("1.609344").multiply(x, mc);
			case MILE_PER_LITER:
				return x.round(mc);
			default:
				return KILOMETER_PER_LITER.convert(convert(x, KILOMETER_PER_LITER, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing mile per gallon in the imperial system.
	 */
	MILE_PER_GALLON_IMP {
		@Override
		public BigDecimal convert(BigDecimal x, FuelConsumptionUnit to, MathContext mc) {
			switch (to) {
			case KILOMETER_PER_LITER:
				return new BigDecimal("0.354006189934647136330341").multiply(x, mc);
			case MILE_PER_GALLON_IMP:
				return x.round(mc);
			default:
				return KILOMETER_PER_LITER.convert(convert(x, KILOMETER_PER_LITER, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing mile per gallon in the US.
	 */
	MILE_PER_GALLON_US {
		@Override
		public BigDecimal convert(BigDecimal x, FuelConsumptionUnit to, MathContext mc) {
			switch (to) {
			case KILOMETER_PER_LITER:
				return new BigDecimal("0.42514370743027200340115").multiply(x, mc);
			case MILE_PER_GALLON_US:
				return x.round(mc);
			default:
				return KILOMETER_PER_LITER.convert(convert(x, KILOMETER_PER_LITER, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing Nautical-mile.
	 */
	NAUTICAL_MILE_PER_LITER {
		@Override
		public BigDecimal convert(BigDecimal x, FuelConsumptionUnit to, MathContext mc) {
			switch (to) {
			case KILOMETER_PER_LITER:
				return new BigDecimal("1.852").multiply(x, mc);
			case NAUTICAL_MILE_PER_LITER:
				return x.round(mc);
			default:
				return KILOMETER_PER_LITER.convert(convert(x, KILOMETER_PER_LITER, mc), to, mc);
			}
		}
	};

	/*
	 * Most Recent Date: 12 Aug 2021-----------------------------------------------
	 * Most recent time created: 17:51:00--------------------------------------
	 */
	/**
	 * Returns the value of the given {@code BigDecimal} converted from {@code this}
	 * to the {@code FuelConsumptionUnit} argument using the given {@code MathContext} for
	 * numeric approximations.
	 * 
	 * @param x  the value to be converted.
	 * @param to the destination of the conversion.
	 * @param mc the object for approximation.
	 * @return the given value converted from {@code this} to the type argument.
	 */
	@Override
	public abstract BigDecimal convert(BigDecimal x, FuelConsumptionUnit to, MathContext mc);
}
