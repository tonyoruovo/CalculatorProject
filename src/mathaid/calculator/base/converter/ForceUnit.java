/**
 * 
 */
package mathaid.calculator.base.converter;

import java.math.BigDecimal;
import java.math.MathContext;

import mathaid.ExceptionMessage;

/*
 * Date: 8 Jul 2021----------------------------------------------------------- 
 * Time created: 17:52:15---------------------------------------------------  
 * Package: mathaid.calculator.base.converter------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: ForceUnit.java------------------------------------------------------ 
 * Class name: ForceUnit------------------------------------------------ 
 */
/**
 * An object containing a set of constants that represents energy units of
 * measurements e.g Newtons, pound-forces which are used for measuring force.
 * 
 * @author Oruovo Anthony Etineakpopha
 */
public enum ForceUnit implements Convertible<ForceUnit, BigDecimal, MathContext> {
	/**
	 * A constant for micro-Newtons.
	 */
	MICRONEWTON {
		@Override
		public BigDecimal convert(BigDecimal x, ForceUnit to, MathContext mc) {
			switch (to) {
			case NEWTON:
				return new BigDecimal("1E-6").multiply(x, mc);
			case MICRONEWTON:
				return x.round(mc);
			default:
				return NEWTON.convert(convert(x, NEWTON, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for milli-Newtons.
	 */
	MILLINEWTON {
		@Override
		public BigDecimal convert(BigDecimal x, ForceUnit to, MathContext mc) {
			switch (to) {
			case NEWTON:
				return new BigDecimal("1E-3").multiply(x, mc);
			case MILLINEWTON:
				return x.round(mc);
			default:
				return NEWTON.convert(convert(x, NEWTON, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for Newtons.
	 */
	NEWTON {
		@Override
		public BigDecimal convert(BigDecimal x, ForceUnit to, MathContext mc) {
			switch (to) {
			case DYNE:
				return new BigDecimal("1E5").multiply(x, mc);
			case GRAM_FORCE:
				return new BigDecimal("101.9716212977928242570093").multiply(x, mc);
			case KILOGRAM_FORCE:
				return new BigDecimal("0.1019716212977928242570093").multiply(x, mc);
			case KILONEWTON:
				return new BigDecimal("1E-3").multiply(x, mc);
			case KILOPOND:
				return new BigDecimal("0.1019716212977928242570093").multiply(x, mc);
			case LONG_TON_FORCE:
				return new BigDecimal("0.000100361135312370751299").multiply(x, mc);
			case MEGANEWTON:
				return new BigDecimal("1E-6").multiply(x, mc);
			case MEGAPOND:
				return new BigDecimal("0.0001019716212977928242570093").multiply(x, mc);
			case MICRONEWTON:
				return new BigDecimal("1E6").multiply(x, mc);
			case MILLINEWTON:
				return new BigDecimal("1E3").multiply(x, mc);
			case NEWTON:
				return x.round(mc);
			case POND:
				return new BigDecimal("101.9716212977928242570093").multiply(x, mc);
			case POUNDAL:
				return new BigDecimal("7.233013851209894380674993").multiply(x, mc);
			case POUND_FORCE:
				return new BigDecimal("0.224808943099710482910039").multiply(x, mc);
			case SHORT_TON_FORCE:
				return new BigDecimal("0.000112404471549855241455").multiply(x, mc);
			case TONNE_FORCE:
				return new BigDecimal("0.0001019716212977928242570093").multiply(x, mc);
			default:
				break;
			}
			new mathaid.IllegalArgumentException(ExceptionMessage.UNKNOWN_UNIT);
			throw new IllegalArgumentException("Unknown unit");
		}
	},
	/**
	 * A constant for kilo-Newtons.
	 */
	KILONEWTON {
		@Override
		public BigDecimal convert(BigDecimal x, ForceUnit to, MathContext mc) {
			switch (to) {
			case NEWTON:
				return new BigDecimal("1E3").multiply(x, mc);
			case KILONEWTON:
				return x.round(mc);
			default:
				return NEWTON.convert(convert(x, NEWTON, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for mega-Newtons.
	 */
	MEGANEWTON {
		@Override
		public BigDecimal convert(BigDecimal x, ForceUnit to, MathContext mc) {
			switch (to) {
			case NEWTON:
				return new BigDecimal("1E6").multiply(x, mc);
			case MEGANEWTON:
				return x.round(mc);
			default:
				return NEWTON.convert(convert(x, NEWTON, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for ponds.
	 */
	POND {
		@Override
		public BigDecimal convert(BigDecimal x, ForceUnit to, MathContext mc) {
			switch (to) {
			case NEWTON:
				return new BigDecimal("0.00980665").multiply(x, mc);
			case POND:
				return x.round(mc);
			default:
				return NEWTON.convert(convert(x, NEWTON, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for kilo-ponds.
	 */
	KILOPOND {
		@Override
		public BigDecimal convert(BigDecimal x, ForceUnit to, MathContext mc) {
			switch (to) {
			case NEWTON:
				return new BigDecimal("9.80665").multiply(x, mc);
			case KILOPOND:
				return x.round(mc);
			default:
				return NEWTON.convert(convert(x, NEWTON, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for mega-ponds.
	 */
	MEGAPOND {
		@Override
		public BigDecimal convert(BigDecimal x, ForceUnit to, MathContext mc) {
			switch (to) {
			case NEWTON:
				return new BigDecimal("9806.65").multiply(x, mc);
			case MEGAPOND:
				return x.round(mc);
			default:
				return NEWTON.convert(convert(x, NEWTON, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for gram-force.
	 */
	GRAM_FORCE {
		@Override
		public BigDecimal convert(BigDecimal x, ForceUnit to, MathContext mc) {
			switch (to) {
			case NEWTON:
				return new BigDecimal("0.00980665").multiply(x, mc);
			case GRAM_FORCE:
				return x.round(mc);
			default:
				return NEWTON.convert(convert(x, NEWTON, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for kilo-gram-force.
	 */
	KILOGRAM_FORCE {
		@Override
		public BigDecimal convert(BigDecimal x, ForceUnit to, MathContext mc) {
			switch (to) {
			case NEWTON:
				return new BigDecimal("9.80665").multiply(x, mc);
			case KILOGRAM_FORCE:
				return x.round(mc);
			default:
				return NEWTON.convert(convert(x, NEWTON, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for tonne-force.
	 */
	TONNE_FORCE {
		@Override
		public BigDecimal convert(BigDecimal x, ForceUnit to, MathContext mc) {
			switch (to) {
			case NEWTON:
				return new BigDecimal("9806.65").multiply(x, mc);
			case TONNE_FORCE:
				return x.round(mc);
			default:
				return NEWTON.convert(convert(x, NEWTON, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for pound-force.
	 */
	POUND_FORCE {
		@Override
		public BigDecimal convert(BigDecimal x, ForceUnit to, MathContext mc) {
			switch (to) {
			case NEWTON:
				return new BigDecimal("4.4482216152605").multiply(x, mc);
			case POUND_FORCE:
				return x.round(mc);
			default:
				return NEWTON.convert(convert(x, NEWTON, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for long-ton force aka displacement ton or imperial ton used
	 * particularly in commonwealth countries.
	 */
	LONG_TON_FORCE {
		@Override
		public BigDecimal convert(BigDecimal x, ForceUnit to, MathContext mc) {
			switch (to) {
			case NEWTON:
				return new BigDecimal("9964.01641818352").multiply(x, mc);
			case LONG_TON_FORCE:
				return x.round(mc);
			default:
				return NEWTON.convert(convert(x, NEWTON, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for short-ton force aka common ton used particularly in the united
	 * states.
	 */
	SHORT_TON_FORCE {
		@Override
		public BigDecimal convert(BigDecimal x, ForceUnit to, MathContext mc) {
			switch (to) {
			case NEWTON:
				return new BigDecimal("8896.443230521").multiply(x, mc);
			case SHORT_TON_FORCE:
				return x.round(mc);
			default:
				return NEWTON.convert(convert(x, NEWTON, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for dynes.
	 */
	DYNE {
		@Override
		public BigDecimal convert(BigDecimal x, ForceUnit to, MathContext mc) {
			switch (to) {
			case NEWTON:
				return new BigDecimal("1E-5").multiply(x, mc);
			case DYNE:
				return x.round(mc);
			default:
				return NEWTON.convert(convert(x, NEWTON, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for poundal.
	 */
	POUNDAL {
		@Override
		public BigDecimal convert(BigDecimal x, ForceUnit to, MathContext mc) {
			switch (to) {
			case NEWTON:
				return new BigDecimal("0.138254954376").multiply(x, mc);
			case POUNDAL:
				return x.round(mc);
			default:
				return NEWTON.convert(convert(x, NEWTON, mc), to, mc);
			}
		}
	};

	/*
	 * Most Recent Date: 12 Aug 2021-----------------------------------------------
	 * Most recent time created: 17:08:13--------------------------------------
	 */
	/**
	 * Returns the value of the given {@code BigDecimal} converted from {@code this}
	 * to the {@code ForceUnit} argument using the given {@code MathContext} for
	 * numeric approximations.
	 * 
	 * @param x  the value to be converted.
	 * @param to the destination of the conversion.
	 * @param mc the object for approximation.
	 * @return the given value converted from {@code this} to the type argument.
	 */
	@Override
	public abstract BigDecimal convert(BigDecimal x, ForceUnit to, MathContext mc);
}
