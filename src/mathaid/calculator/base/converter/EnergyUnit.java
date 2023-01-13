/**
 * 
 */
package mathaid.calculator.base.converter;

import java.math.BigDecimal;
import java.math.MathContext;

import mathaid.ExceptionMessage;

/*
 * Date: 11 Jul 2021----------------------------------------------------------- 
 * Time created: 17:30:44---------------------------------------------------  
 * Package: mathaid.calculator.base.converter------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: EnergyUnit.java------------------------------------------------------ 
 * Class name: EnergyUnit------------------------------------------------ 
 */
/**
 * An object containing a set of constants that represents energy units of
 * measurements e.g joules, calories which are used for measuring energy.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public enum EnergyUnit implements Convertible<EnergyUnit, BigDecimal, MathContext> {
	/**
	 * A constant for nano-joules.
	 */
	NANOJOULE {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return new BigDecimal("1E-9").multiply(x, mc);
			case NANOJOULE:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for micro-joules.
	 */
	MICROJOULE {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return new BigDecimal("1E-6").multiply(x, mc);
			case MICROJOULE:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for milli-joules.
	 */
	MILLIJOULE {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return new BigDecimal("1E-3").multiply(x, mc);
			case MILLIJOULE:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for joules.
	 */
	JOULE {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return x.round(mc);
			case BARREL_OF_OIL_EQUIVALENT:
				return new BigDecimal("1.634167220540409948496279E-10").multiply(x, mc);
			case BRITISH_THERMAL_UNIT_ISO:
				return new BigDecimal("0.000947817120313317200013").multiply(x, mc);
			case CALORIE:
				return new BigDecimal("0.238845896627495939619757").multiply(x, mc);
			case ELECTRONVOLT:
				return new BigDecimal("6241509343260179317.377545").multiply(x, mc);
			case ERG:
				return new BigDecimal("1E7").multiply(x, mc);
			case FOOT_POUND_FORCE:
				return new BigDecimal("0.737562149277265363878082").multiply(x, mc);
			case GIGAELECTRONVOLT:
				return new BigDecimal("6241509343.260179317377545").multiply(x, mc);
			case HORSEPOWER_HOUR:
				return new BigDecimal("3.776726714733067565074418E-7").multiply(x, mc);
			case KILOCALORIE:
				return new BigDecimal("0.000238845896627495939619757").multiply(x, mc);
			case KILOELECTRONVOLT:
				return new BigDecimal("6241509343260179.317377545").multiply(x, mc);
			case KILOJOULE:
				return new BigDecimal("1E-3").multiply(x, mc);
			case KILOTONNE_OF_TNT:
				return new BigDecimal("2.390057361376673040152964E-13").multiply(x, mc);
			case KILOWATT_HOUR:
				return BigDecimal.ONE.divide(new BigDecimal("3.6E6"), mc).multiply(x, mc);
			case MEGAELECTRONVOLT:
				return new BigDecimal("6241509343260.179317377545").multiply(x, mc);
			case MEGAJOULE:
				return new BigDecimal("1E-6").multiply(x, mc);
			case MEGATONNE_OF_TNT:
				return new BigDecimal("2.390057361376673040152964E-16").multiply(x, mc);
			case MEGAWATT_HOUR:
				return BigDecimal.ONE.divide(new BigDecimal("3.6E-9"), mc).multiply(x, mc);
			case MICROJOULE:
				return new BigDecimal("1E6").multiply(x, mc);
			case MILLIJOULE:
				return new BigDecimal("1E3").multiply(x, mc);
			case NANOJOULE:
				return new BigDecimal("1E9").multiply(x, mc);
			case NEWTON_METER:
				return x.round(mc);
			case THERM:
				return new BigDecimal("9.4781712031331720001212785E-10").multiply(x, mc);
			case TONNE_OF_OIL_EQUIVALENT:
				return new BigDecimal("2.388458966274959396197573E-11").multiply(x, mc);
			case TONNE_OF_TNT:
				return new BigDecimal("2.390057361376673040152964E-10").multiply(x, mc);
			case WATT_HOUR:
				return BigDecimal.ONE.divide(new BigDecimal("3600"), mc).multiply(x, mc);
			default:
			}
			new mathaid.IllegalArgumentException(ExceptionMessage.UNKNOWN_UNIT);
			throw new IllegalArgumentException("Unknown unit");
		}
	},
	/**
	 * A constant for kilo-joules.
	 */
	KILOJOULE {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return new BigDecimal("1E3").multiply(x, mc);
			case KILOJOULE:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for mega-joules.
	 */
	MEGAJOULE {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return new BigDecimal("1E6").multiply(x, mc);
			case MEGAJOULE:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for Newton-meters.
	 */
	NEWTON_METER {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
			case NEWTON_METER:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for watt/hours.
	 */
	WATT_HOUR {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return new BigDecimal("3600").multiply(x, mc);
			case WATT_HOUR:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for kilo-watt/hours.
	 */
	KILOWATT_HOUR {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return new BigDecimal("3.6E6").multiply(x, mc);
			case KILOWATT_HOUR:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for mega-watt/hours.
	 */
	MEGAWATT_HOUR {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return new BigDecimal("3.6E9").multiply(x, mc);
			case MEGAWATT_HOUR:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for calories.
	 */
	CALORIE {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return new BigDecimal("4.1868").multiply(x, mc);
			case CALORIE:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for kilo-calories.
	 */
	KILOCALORIE {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return new BigDecimal("4186.8").multiply(x, mc);
			case KILOCALORIE:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for foot-pound-force.
	 */
	FOOT_POUND_FORCE {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return new BigDecimal("1.3558179483314004").multiply(x, mc);
			case FOOT_POUND_FORCE:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for the British thermal unit (an ISO standard) .
	 */
	BRITISH_THERMAL_UNIT_ISO {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return new BigDecimal("1055.05585262").multiply(x, mc);
			case BRITISH_THERMAL_UNIT_ISO:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for therms.
	 */
	THERM {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return new BigDecimal("105505585.262").multiply(x, mc);
			case THERM:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for horse-power/hour.
	 */
	HORSEPOWER_HOUR {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return new BigDecimal("2647795.5").multiply(x, mc);
			case HORSEPOWER_HOUR:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for barrels of oil equivalent.
	 */
	BARREL_OF_OIL_EQUIVALENT {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return new BigDecimal("6119324800").multiply(x, mc);
			case BARREL_OF_OIL_EQUIVALENT:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for tonnes of oil equivalent.
	 */
	TONNE_OF_OIL_EQUIVALENT {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return new BigDecimal("4.1868E10").multiply(x, mc);
			case TONNE_OF_OIL_EQUIVALENT:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for tonnes of tnt (2,4,6-trinitrotoluene).
	 */
	TONNE_OF_TNT {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return new BigDecimal("4.1868E9").multiply(x, mc);
			case TONNE_OF_TNT:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for kilo-tonnes of tnt (2,4,6-trinitrotoluene).
	 */
	KILOTONNE_OF_TNT {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return new BigDecimal("4.1868E12").multiply(x, mc);
			case KILOTONNE_OF_TNT:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for mega-tonnes of tnt (2,4,6-trinitrotoluene).
	 */
	MEGATONNE_OF_TNT {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return new BigDecimal("4.1868E15").multiply(x, mc);
			case MEGATONNE_OF_TNT:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for erg.
	 */
	ERG {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return new BigDecimal("1E-7").multiply(x, mc);
			case ERG:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for electron-volt.
	 */
	ELECTRONVOLT {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return new BigDecimal("1.602176565E-19").multiply(x, mc);
			case ELECTRONVOLT:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for kilo-electron-volt.
	 */
	KILOELECTRONVOLT {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return new BigDecimal("1.602176565E-16").multiply(x, mc);
			case KILOELECTRONVOLT:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for mega-electron-volt.
	 */
	MEGAELECTRONVOLT {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return new BigDecimal("1.602176565E-13").multiply(x, mc);
			case MEGAELECTRONVOLT:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	},
	/**
	 * A constant for giga-electron-volt.
	 */
	GIGAELECTRONVOLT {
		@Override
		public BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc) {
			switch (to) {
			case JOULE:
				return new BigDecimal("1.602176565E-10").multiply(x, mc);
			case GIGAELECTRONVOLT:
				return x.round(mc);
			default:
				return JOULE.convert(convert(x, JOULE, mc), to, mc);
			}
		}
	};

	/*
	 * Most Recent Date: 12 Aug 2021-----------------------------------------------
	 * Most recent time created: 16:53:40--------------------------------------
	 */
	/**
	 * Returns the value of the given {@code BigDecimal} converted from {@code this}
	 * to the {@code EnergyUnit} argument using the given {@code MathContext} for
	 * numeric approximations.
	 * 
	 * @param x  the value to be converted.
	 * @param to the destination of the conversion.
	 * @param mc the object for approximation.
	 * @return the given value converted from {@code this} to the type argument.
	 */
	@Override
	public abstract BigDecimal convert(BigDecimal x, EnergyUnit to, MathContext mc);
}
