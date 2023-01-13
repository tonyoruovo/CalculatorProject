/**
 * 
 */
package mathaid.calculator.base.converter;

import java.math.BigDecimal;
import java.math.MathContext;

import mathaid.ExceptionMessage;
import mathaid.calculator.base.value.BigFraction;

/*
 * Date: 7 Jul 2021----------------------------------------------------------- 
 * Time created: 17:54:49---------------------------------------------------  
 * Package: mathaid.calculator.base.function------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: TemporalUnit.java------------------------------------------------------ 
 * Class name: TemporalUnit------------------------------------------------ 
 */
/**
 * An object containing a set of constants that can convert values between time
 * units such as seconds and minutes.
 * @author Oruovo Anthony Etineakpopha
 */
public enum TemporalUnit implements Convertible<TemporalUnit, BigDecimal, MathContext> {
	/**
	 * A constant representing nanoseconds.
	 */
	NANOSECOND {
		@Override
		public BigDecimal convert(BigDecimal x, TemporalUnit to, MathContext mc) {
			switch (to) {
			case SECOND:
				return new BigDecimal("1E-9").multiply(x, mc);
			case NANOSECOND:
				return x.round(mc);
			default:
				return SECOND.convert(convert(x, SECOND, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing microseconds.
	 */
	MICROSECOND {
		@Override
		public BigDecimal convert(BigDecimal x, TemporalUnit to, MathContext mc) {
			switch (to) {
			case SECOND:
				return new BigDecimal("1E-6").multiply(x, mc);
			case MICROSECOND:
				return x.round(mc);
			default:
				return SECOND.convert(convert(x, SECOND, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing milliseconds.
	 */
	MILLISECOND {
		@Override
		public BigDecimal convert(BigDecimal x, TemporalUnit to, MathContext mc) {
			switch (to) {
			case SECOND:
				return new BigDecimal("1E-3").multiply(x, mc);
			case MILLISECOND:
				return x.round(mc);
			default:
				return SECOND.convert(convert(x, SECOND, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing seconds.
	 */
	SECOND {
		@Override
		public BigDecimal convert(BigDecimal x, TemporalUnit to, MathContext mc) {
			switch (to) {
			case CENTURY:
				return new BigFraction("1/100").getDecimalExpansion(mc.getPrecision()).multiply(convert(x, YEAR, mc),
						mc);
			case DAY:
				return new BigFraction("1/86400").getDecimalExpansion(mc.getPrecision()).multiply(x, mc);
			case DECADE:
				return new BigFraction("1/10").getDecimalExpansion(mc.getPrecision()).multiply(convert(x, YEAR, mc),
						mc);
			case HOUR:
				return new BigFraction("1/3600").getDecimalExpansion(mc.getPrecision()).multiply(x, mc);
			case MICROSECOND:
				return new BigDecimal("1E6").multiply(x, mc);
			case MILLENNIUM:
				return new BigFraction("1/1000").getDecimalExpansion(mc.getPrecision()).multiply(convert(x, YEAR, mc),
						mc);
			case MILLISECOND:
				return new BigDecimal("1E3").multiply(x, mc);
			case MINUTE:
				return new BigFraction("1/60").getDecimalExpansion(mc.getPrecision()).multiply(x, mc);
			case MONTH:
				return new BigFraction("1/30").getDecimalExpansion(mc.getPrecision()).multiply(convert(x, DAY, mc), mc);
			case NANOSECOND:
				return new BigDecimal("1E9").multiply(x, mc);
			case SECOND:
				return x.round(mc);
			case WEEK:
				return new BigFraction("1/604800").getDecimalExpansion(mc.getPrecision()).multiply(x, mc);
			case YEAR:
				return new BigFraction("1/31557600").getDecimalExpansion(mc.getPrecision()).multiply(x, mc);
			default:
			}
			new mathaid.IllegalArgumentException(ExceptionMessage.UNKNOWN_UNIT);
			throw new IllegalArgumentException("Unknown unit");
		}
	},
	/**
	 * A constant representing minutes.
	 */
	MINUTE {
		@Override
		public BigDecimal convert(BigDecimal x, TemporalUnit to, MathContext mc) {
			switch (to) {
			case SECOND:
				return new BigDecimal("60").multiply(x, mc);
			case MINUTE:
				return x.round(mc);
			default:
				return SECOND.convert(convert(x, SECOND, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing hours.
	 */
	HOUR {
		@Override
		public BigDecimal convert(BigDecimal x, TemporalUnit to, MathContext mc) {
			switch (to) {
			case SECOND:
				return new BigDecimal("3600").multiply(x, mc);
			case HOUR:
				return x.round(mc);
			default:
				return SECOND.convert(convert(x, SECOND, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing days.
	 */
	DAY {
		@Override
		public BigDecimal convert(BigDecimal x, TemporalUnit to, MathContext mc) {
			switch (to) {
			case SECOND:
				return new BigDecimal("86400").multiply(x, mc);
			case DAY:
				return x.round(mc);
			default:
				return SECOND.convert(convert(x, SECOND, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing weeks.
	 */
	WEEK {
		@Override
		public BigDecimal convert(BigDecimal x, TemporalUnit to, MathContext mc) {
			switch (to) {
			case SECOND:
				return new BigDecimal("604800").multiply(x, mc);
			case WEEK:
				return x.round(mc);
			default:
				return SECOND.convert(convert(x, SECOND, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing months.
	 */
	MONTH {
		@Override
		public BigDecimal convert(BigDecimal x, TemporalUnit to, MathContext mc) {
			switch (to) {
			case DAY:
				return new BigDecimal("30").multiply(convert(x, DAY, mc), mc);
			case MONTH:
				return x.round(mc);
			default:
				return SECOND.convert(convert(x, SECOND, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing years.
	 */
	YEAR {
		@Override
		public BigDecimal convert(BigDecimal x, TemporalUnit to, MathContext mc) {
			switch (to) {
			case SECOND:
				return new BigDecimal("31557600").multiply(x, mc);
			case YEAR:
				return x.round(mc);
			default:
				return SECOND.convert(convert(x, SECOND, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing decades.
	 */
	DECADE {
		@Override
		public BigDecimal convert(BigDecimal x, TemporalUnit to, MathContext mc) {
			switch (to) {
			case YEAR:
				return new BigDecimal("10").multiply(convert(x, YEAR, mc), mc);
			case DECADE:
				return x.round(mc);
			default:
				return SECOND.convert(convert(x, SECOND, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing centuries.
	 */
	CENTURY {
		@Override
		public BigDecimal convert(BigDecimal x, TemporalUnit to, MathContext mc) {
			switch (to) {
			case YEAR:
				return new BigDecimal("100").multiply(convert(x, YEAR, mc), mc);
			case CENTURY:
				return x.round(mc);
			default:
				return SECOND.convert(convert(x, SECOND, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing millenia.
	 */
	MILLENNIUM {
		@Override
		public BigDecimal convert(BigDecimal x, TemporalUnit to, MathContext mc) {
			switch (to) {
			case YEAR:
				return new BigDecimal("1000").multiply(convert(x, YEAR, mc), mc);
			case MILLENNIUM:
				return x.round(mc);
			default:
				return SECOND.convert(convert(x, SECOND, mc), to, mc);
			}
		}
	};

	/*
	 * Most Recent Date: 12 Aug 2021-----------------------------------------------
	 * Most recent time created: 21:31:38--------------------------------------
	 */
	/**
	 * Returns the value of the given {@code BigDecimal} converted from {@code this}
	 * to the {@code TemporalUnit} argument using the given {@code MathContext} for
	 * numeric approximations.
	 * 
	 * @param x  the value to be converted.
	 * @param to the destination of the conversion.
	 * @param mc the object for approximation.
	 * @return the given value converted from {@code this} to the type argument.
	 */
	@Override
	public abstract BigDecimal convert(BigDecimal x, TemporalUnit to, MathContext mc);
}
