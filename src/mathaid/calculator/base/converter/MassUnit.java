/**
 * 
 */
package mathaid.calculator.base.converter;

import java.math.BigDecimal;
import java.math.MathContext;

import mathaid.ExceptionMessage;

/*
 * Date: 5 Jul 2021----------------------------------------------------------- 
 * Time created: 14:40:28---------------------------------------------------  
 * Package: mathaid.calculator.base.function------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: MassUnit.java------------------------------------------------------ 
 * Class name: MassUnit------------------------------------------------ 
 */
/**
 * An object containing a set of constants that can convert values between units
 * of mass such as grams and ounces.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public enum MassUnit implements Convertible<MassUnit, BigDecimal, MathContext> {
	/**
	 * A constant representing micro-grams.
	 */
	MICROGRAM {
		@Override
		public BigDecimal convert(BigDecimal x, MassUnit to, MathContext mc) {
			switch (to) {
			case GRAM:
				return new BigDecimal("1E-6").multiply(x, mc);
			case MICROGRAM:
				return x.round(mc);
			default:
				return GRAM.convert(convert(x, GRAM, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing milli-grams.
	 */
	MILLIGRAM {
		@Override
		public BigDecimal convert(BigDecimal x, MassUnit to, MathContext mc) {
			switch (to) {
			case GRAM:
				return new BigDecimal("1E-3").multiply(x, mc);
			case MILLIGRAM:
				return x.round(mc);
			default:
				return GRAM.convert(convert(x, GRAM, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing grams.
	 */
	GRAM {
		@Override
		public BigDecimal convert(BigDecimal x, MassUnit to, MathContext mc) {
			switch (to) {
			case CARAT:
				return new BigDecimal("5").multiply(x, mc);
			case GRAIN:
				return new BigDecimal("20").multiply(x, mc);
			case GRAM:
				return x.round(mc);
			case HUNDREDWEIGHT:
				return new BigDecimal("1.9684130552221E-5").multiply(x, mc);
			case HUNDREDWEIGHT_US:
				return new BigDecimal("2.2046226218488E-5").multiply(x, mc);
			case KILOGRAM:
				return new BigDecimal("1E-3").multiply(x, mc);
			case LONG_TON:
				return new BigDecimal("9.8420652761106E-7").multiply(x, mc);
			case MICROGRAM:
				return new BigDecimal("1000000").multiply(x, mc);
			case MILLIGRAM:
				return new BigDecimal("1000").multiply(x, mc);
			case OUNCE:
				return new BigDecimal("0.0353").multiply(x, mc);
			case POUND:
				return new BigDecimal("453.59237").multiply(x, mc);
			case QUARTER:
				return KILOGRAM.convert(convert(x, KILOGRAM, mc), to, mc);
			case SHORT_TON_US:
				return new BigDecimal("1.1023113109244E-6").multiply(x, mc);
			case STONE:
				return new BigDecimal("0.00015747304441777").multiply(x, mc);
			case TONNE:
				return new BigDecimal("1.0E-6").multiply(x, mc);
			case TROY_OUNCE:
				return new BigDecimal("0.032150746568628").multiply(x, mc);
			default:
			}
			new mathaid.IllegalArgumentException(ExceptionMessage.UNKNOWN_UNIT);
			throw new IllegalArgumentException("Unknown unit");
		}
	},
	/**
	 * A constant representing kilogrammes.
	 */
	KILOGRAM {
		@Override
		public BigDecimal convert(BigDecimal x, MassUnit to, MathContext mc) {
			switch (to) {
			case GRAM:
				return new BigDecimal("1E3").multiply(x, mc);
			case KILOGRAM:
				return x.round(mc);
			default:
				return GRAM.convert(convert(x, GRAM, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing tonnes.
	 */
	TONNE {
		@Override
		public BigDecimal convert(BigDecimal x, MassUnit to, MathContext mc) {
			switch (to) {
			case GRAM:
				return new BigDecimal("1E6").multiply(x, mc);
			case TONNE:
				return x.round(mc);
			default:
				return GRAM.convert(convert(x, GRAM, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing grains.
	 */
	GRAIN {
		@Override
		public BigDecimal convert(BigDecimal x, MassUnit to, MathContext mc) {
			switch (to) {
			case MILLIGRAM:
				return new BigDecimal("64.79891").multiply(x, mc);
			case GRAIN:
				return x.round(mc);
			default:
				return MILLIGRAM.convert(convert(x, MILLIGRAM, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing ounces.
	 */
	OUNCE {
		@Override
		public BigDecimal convert(BigDecimal x, MassUnit to, MathContext mc) {
			switch (to) {
			case GRAM:
				return new BigDecimal("28.349523125").multiply(x, mc);
			case OUNCE:
				return x.round(mc);
			default:
				return GRAM.convert(convert(x, GRAM, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing pounds.
	 */
	POUND {
		@Override
		public BigDecimal convert(BigDecimal x, MassUnit to, MathContext mc) {
			switch (to) {
			case KILOGRAM:
				return new BigDecimal("0.45359237").multiply(x, mc);
			case POUND:
				return x.round(mc);
			default:
				return KILOGRAM.convert(convert(x, KILOGRAM, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing stones.
	 */
	STONE {
		@Override
		public BigDecimal convert(BigDecimal x, MassUnit to, MathContext mc) {
			switch (to) {
			case POUND:
				return new BigDecimal("14").multiply(x, mc);
			case STONE:
				return x.round(mc);
			default:
				return POUND.convert(convert(x, POUND, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing quaters.
	 */
	QUARTER {
		@Override
		public BigDecimal convert(BigDecimal x, MassUnit to, MathContext mc) {
			switch (to) {
			case STONE:
				return new BigDecimal("2").multiply(x, mc);
			case QUARTER:
				return x.round(mc);
			default:
				return STONE.convert(convert(x, STONE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing hundred weight in the imperial measurement system.
	 */
	HUNDREDWEIGHT {
		@Override
		public BigDecimal convert(BigDecimal x, MassUnit to, MathContext mc) {
			switch (to) {
			case POUND:
				return new BigDecimal("112").multiply(x, mc);
			case HUNDREDWEIGHT:
				return x.round(mc);
			default:
				return POUND.convert(convert(x, POUND, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing hundred weight in the US.
	 */
	HUNDREDWEIGHT_US {
		@Override
		public BigDecimal convert(BigDecimal x, MassUnit to, MathContext mc) {
			switch (to) {
			case POUND:
				return new BigDecimal("100").multiply(x, mc);
			case HUNDREDWEIGHT_US:
				return x.round(mc);
			default:
				return POUND.convert(convert(x, POUND, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for long-ton force aka displacement ton or imperial ton used
	 * particularly in commonwealth countries.
	 */
	LONG_TON {
		@Override
		public BigDecimal convert(BigDecimal x, MassUnit to, MathContext mc) {
			switch (to) {
			case KILOGRAM:
				return new BigDecimal("1016.047").multiply(x, mc);
			case LONG_TON:
				return x.round(mc);
			default:
				return KILOGRAM.convert(convert(x, KILOGRAM, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for short-ton force aka common ton used particularly in the united
	 * states.
	 */
	SHORT_TON_US {
		@Override
		public BigDecimal convert(BigDecimal x, MassUnit to, MathContext mc) {
			switch (to) {
			case POUND:
				return new BigDecimal("2000").multiply(x, mc);
			case SHORT_TON_US:
				return x.round(mc);
			default:
				return POUND.convert(convert(x, POUND, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing carats.
	 */
	CARAT {
		@Override
		public BigDecimal convert(BigDecimal x, MassUnit to, MathContext mc) {
			switch (to) {
			case MILLIGRAM:
				return new BigDecimal("200").multiply(x, mc);
			case CARAT:
				return x.round(mc);
			default:
				return MILLIGRAM.convert(convert(x, MILLIGRAM, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing troy ounces.
	 */
	TROY_OUNCE {
		@Override
		public BigDecimal convert(BigDecimal x, MassUnit to, MathContext mc) {
			switch (to) {
			case GRAM:
				return new BigDecimal("31.1034768").multiply(x, mc);
			case TROY_OUNCE:
				return x.round(mc);
			default:
				return GRAM.convert(convert(x, GRAM, mc), to, mc);
			}
		}
	};

	/*
	 * Most Recent Date: 12 Aug 2021-----------------------------------------------
	 * Most recent time created: 17:51:53--------------------------------------
	 */
	/**
	 * Returns the value of the given {@code BigDecimal} converted from {@code this}
	 * to the {@code MassUnit} argument using the given {@code MathContext} for
	 * numeric approximations.
	 * 
	 * @param x  the value to be converted.
	 * @param to the destination of the conversion.
	 * @param mc the object for approximation.
	 * @return the given value converted from {@code this} to the type argument.
	 */
	@Override
	public abstract BigDecimal convert(BigDecimal x, MassUnit to, MathContext mc);
}
