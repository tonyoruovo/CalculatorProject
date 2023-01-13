/**
 * 
 */
package mathaid.calculator.base.converter;

import java.math.BigDecimal;
import java.math.MathContext;

import mathaid.ExceptionMessage;

/*
 * Date: 8 Jul 2021----------------------------------------------------------- 
 * Time created: 17:31:16---------------------------------------------------  
 * Package: mathaid.calculator.base.converter------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: FrequencyUnit.java------------------------------------------------------ 
 * Class name: FrequencyUnit------------------------------------------------ 
 */
/**
 * An object containing a set of constants that represents frequency units of
 * measurements e.g Hertz for measuring frequency.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public enum FrequencyUnit implements Convertible<FrequencyUnit, BigDecimal, MathContext> {
	/**
	 * A constant for nano-Hertz.
	 */
	NANOHERTZ {
		@Override
		public BigDecimal convert(BigDecimal x, FrequencyUnit to, MathContext mc) {
			switch(to) {
			case HERTZ:
				return new BigDecimal("1E-9").multiply(x, mc);
			case NANOHERTZ:
				return x.round(mc);
				default:
					return HERTZ.convert(convert(x, HERTZ, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for micro-Hertz.
	 */
	MICROHERTZ {
		@Override
		public BigDecimal convert(BigDecimal x, FrequencyUnit to, MathContext mc) {
			switch(to) {
			case HERTZ:
				return new BigDecimal("1E-6").multiply(x, mc);
			case MICROHERTZ:
				return x.round(mc);
				default:
					return HERTZ.convert(convert(x, HERTZ, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for milli-Hertz.
	 */
	MILLIHERTZ {
		@Override
		public BigDecimal convert(BigDecimal x, FrequencyUnit to, MathContext mc) {
			switch(to) {
			case HERTZ:
				return new BigDecimal("1E-3").multiply(x, mc);
			case MILLIHERTZ:
				return x.round(mc);
				default:
					return HERTZ.convert(convert(x, HERTZ, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for Hertz.
	 */
	HERTZ {
		@Override
		public BigDecimal convert(BigDecimal x, FrequencyUnit to, MathContext mc) {
			switch(to) {
			case CYCLES_PER_SECOND:
				return x.round(mc);
			case DEGREES_PER_SECOND:
				return new BigDecimal("360").multiply(x, mc);
			case GIGAHERTZ:
				return new BigDecimal("1E-9").multiply(x, mc);
			case HERTZ:
				return x.round(mc);
			case KILOHERTZ:
				return new BigDecimal("1E-3").multiply(x, mc);
			case MEGAHERTZ:
				return new BigDecimal("1E-6").multiply(x, mc);
			case MICROHERTZ:
				return new BigDecimal("1E6").multiply(x, mc);
			case MILLIHERTZ:
				return new BigDecimal("1E3").multiply(x, mc);
			case NANOHERTZ:
				return new BigDecimal("1E9").multiply(x, mc);
			case RADIANS_PER_SECOND:
				return new BigDecimal("6.283185307179586476925287").multiply(x, mc);
			case REVOLUTIONS_PER_MINUTE:
				return new BigDecimal("60").multiply(x, mc);
			default:
				break;
			}
			new mathaid.IllegalArgumentException(ExceptionMessage.UNKNOWN_UNIT);
			throw new IllegalArgumentException("Unknown unit");
		}
	},
	/**
	 * A constant for kilo-Hertz.
	 */
	KILOHERTZ {
		@Override
		public BigDecimal convert(BigDecimal x, FrequencyUnit to, MathContext mc) {
			switch(to) {
			case HERTZ:
				return new BigDecimal("1E3").multiply(x, mc);
			case KILOHERTZ:
				return x.round(mc);
				default:
					return HERTZ.convert(convert(x, HERTZ, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for mega-Hertz.
	 */
	MEGAHERTZ {
		@Override
		public BigDecimal convert(BigDecimal x, FrequencyUnit to, MathContext mc) {
			switch(to) {
			case HERTZ:
				return new BigDecimal("1E6").multiply(x, mc);
			case MEGAHERTZ:
				return x.round(mc);
				default:
					return HERTZ.convert(convert(x, HERTZ, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for giga-Hertz.
	 */
	GIGAHERTZ {
		@Override
		public BigDecimal convert(BigDecimal x, FrequencyUnit to, MathContext mc) {
			switch(to) {
			case HERTZ:
				return new BigDecimal("1E9").multiply(x, mc);
			case GIGAHERTZ:
				return x.round(mc);
				default:
					return HERTZ.convert(convert(x, HERTZ, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for cycles per second.
	 */
	CYCLES_PER_SECOND {
		@Override
		public BigDecimal convert(BigDecimal x, FrequencyUnit to, MathContext mc) {
			switch(to) {
			case HERTZ:
			case CYCLES_PER_SECOND:
				return x.round(mc);
				default:
					return HERTZ.convert(convert(x, HERTZ, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for revolutions per minute.
	 */
	REVOLUTIONS_PER_MINUTE {
		@Override
		public BigDecimal convert(BigDecimal x, FrequencyUnit to, MathContext mc) {
			switch(to) {
			case HERTZ:
				return BigDecimal.ONE.divide(new BigDecimal("60"), mc).multiply(x, mc);
			case REVOLUTIONS_PER_MINUTE:
				return x.round(mc);
				default:
					return HERTZ.convert(convert(x, HERTZ, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for radians per second.
	 */
	RADIANS_PER_SECOND {
		@Override
		public BigDecimal convert(BigDecimal x, FrequencyUnit to, MathContext mc) {
			switch(to) {
			case HERTZ:
				return new BigDecimal("0.159154943091895335768884").multiply(x, mc);
			case RADIANS_PER_SECOND:
				return x.round(mc);
				default:
					return HERTZ.convert(convert(x, HERTZ, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for degrees per second.
	 */
	DEGREES_PER_SECOND {
		@Override
		public BigDecimal convert(BigDecimal x, FrequencyUnit to, MathContext mc) {
			switch(to) {
			case HERTZ:
				return BigDecimal.ONE.divide(new BigDecimal("360"), mc).multiply(x, mc);
			case DEGREES_PER_SECOND:
				return x.round(mc);
				default:
					return HERTZ.convert(convert(x, HERTZ, mc), to, mc);
			}
		}
	};

	/*
	 * Most Recent Date: 12 Aug 2021-----------------------------------------------
	 * Most recent time created: 17:21:41--------------------------------------
	 */
	/**
	 * Returns the value of the given {@code BigDecimal} converted from {@code this}
	 * to the {@code FrequencyUnit} argument using the given {@code MathContext} for
	 * numeric approximations.
	 * 
	 * @param x  the value to be converted.
	 * @param to the destination of the conversion.
	 * @param mc the object for approximation.
	 * @return the given value converted from {@code this} to the type argument.
	 */
	@Override
	public abstract BigDecimal convert(BigDecimal x, FrequencyUnit to, MathContext mc);
}
