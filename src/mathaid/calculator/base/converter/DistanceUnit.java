/**
 * 
 */
package mathaid.calculator.base.converter;

import java.math.BigDecimal;
import java.math.MathContext;

import mathaid.ExceptionMessage;

/*
 * Date: 1 Jul 2021----------------------------------------------------------- 
 * Time created: 18:18:14---------------------------------------------------  
 * Package: mathaid.calculator.base.function------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: DistanceUnit.java------------------------------------------------------ 
 * Class name: DistanceUnit------------------------------------------------ 
 */
/**
 * An object containing a set of constants that represents distance units of measurements e.g meters, miles
 * which are used for measuring distances.
 * @author Oruovo Anthony Etineakpopha
 */
public enum DistanceUnit implements Convertible<DistanceUnit, BigDecimal, MathContext> {
	/**
	 * A constant for picometers.
	 */
	PICOMETER {

		@Override
		public BigDecimal convert(BigDecimal x, DistanceUnit to, MathContext c) {
			switch (to) {
			case METER:
				return new BigDecimal("1E-12").multiply(x, c);
			case PICOMETER:
				return x.round(c);
			default:
				return METER.convert(convert(x, METER, c), to, c);
			}
		}
		
	},
	/**
	 * A constant for nanometers.
	 */
	NANOMETER {
		@Override
		public BigDecimal convert(BigDecimal x, DistanceUnit to, MathContext c) {
			switch (to) {
			case METER:
				return new BigDecimal("1E-9").multiply(x, c);
			case NANOMETER:
				return x.round(c);
			default:
				return METER.convert(convert(x, METER, c), to, c);
			}
		}

	},
	/**
	 * A constant for micrometers.
	 */
	MICROMETER {
		@Override
		public BigDecimal convert(BigDecimal x, DistanceUnit to, MathContext c) {
			switch (to) {
			case METER:
				return new BigDecimal("1E-6").multiply(x, c);
			case MICROMETER:
				return x.round(c);
			default:
				return METER.convert(convert(x, METER, c), to, c);
			}
		}
	},
	/**
	 * A constant for millimeters.
	 */
	MILLIMETER {

		@Override
		public BigDecimal convert(BigDecimal x, DistanceUnit to, MathContext c) {
			switch (to) {
			case METER:
				return new BigDecimal("1E-3").multiply(x, c);
			case MILLIMETER:
				return x.round(c);
			default:
				return METER.convert(convert(x, METER, c), to, c);
			}
		}
		
	},
	/**
	 * A constant for centimeters.
	 */
	CENTIMETER{

		@Override
		public BigDecimal convert(BigDecimal x, DistanceUnit to, MathContext c) {
			switch (to) {
			case METER:
				return new BigDecimal("1E-2").multiply(x, c);
			case CENTIMETER:
				return x.round(c);
			default:
				return METER.convert(convert(x, METER, c), to, c);
			}
		}
		
	},
	/**
	 * A constant for decimeters.
	 */
	DECIMETER {

		@Override
		public BigDecimal convert(BigDecimal x, DistanceUnit to, MathContext c) {
			switch (to) {
			case METER:
				return new BigDecimal("1E-1").multiply(x, c);
			case DECIMETER:
				return x.round(c);
			default:
				return METER.convert(convert(x, METER, c), to, c);
			}
		}
		
	},
	/**
	 * A constant for meters.
	 */
	METER {
		@Override
		public BigDecimal convert(BigDecimal x, DistanceUnit to, MathContext c) {
			switch (to) {
			case ANGSTROM:
				return new BigDecimal("1E10").multiply(x, c);
			case ASTRONOMICAL_UNIT:
				return new BigDecimal("6.68458712226845E-12").multiply(x, c);
			case CABLE:
				return new BigDecimal("0.00539956803456").multiply(x, c);
			case CENTIMETER:
				return new BigDecimal("1E2").multiply(x, c);
			case CHAIN:
				return new BigDecimal("0.04970969537899").multiply(x, c);
			case DECIMETER:
				return new BigDecimal("10").multiply(x, c);
			case FATHOM:
				return new BigDecimal("0.54680664916885").multiply(x, c);
			case FOOT:
				return new BigDecimal("3.28083989501312").multiply(x, c);
			case FURLONG:
				return new BigDecimal("0.0049709695379").multiply(x, c);
			case INCH:
				return new BigDecimal("39.3700787401575").multiply(x, c);
			case KILOMETER:
				return new BigDecimal("1E-3").multiply(x, c);
			case LIGHT_YEAR:
				return new BigDecimal("1.05700083402462E-16").multiply(x, c);
			case METER:
				return x.round(c);
			case MICROMETER:
				return new BigDecimal("1E6").multiply(x, c);
			case MILE:
				return new BigDecimal("0.00062137119224").multiply(x, c);
			case MILLIMETER:
				return new BigDecimal("1E3").multiply(x, c);
			case NANOMETER:
				return new BigDecimal("1E9").multiply(x, c);
			case NAUTICAL_MILE:
				return new BigDecimal("0.00053995680346").multiply(x, c);
			case PARSEC:
				return new BigDecimal("3.24077928944437E-17").multiply(x, c);
			case PICOMETER:
				return new BigDecimal("1E12").multiply(x, c);
			case THOU:
				return FOOT.convert(convert(x, FOOT, c), to, c);
			case YARD:
				return new BigDecimal("1.09361329833771").multiply(x, c);
			default:
				new mathaid.IllegalArgumentException(ExceptionMessage.UNKNOWN_UNIT);
				throw new IllegalArgumentException("Unknown unit");
			}
		}

	},
	/**
	 * A constant for kilometers.
	 */
	KILOMETER{

		@Override
		public BigDecimal convert(BigDecimal x, DistanceUnit to, MathContext c) {
			switch (to) {
			case METER:
				return new BigDecimal("1000").multiply(x, c);
			case KILOMETER:
				return x.round(c);
			default:
				return METER.convert(convert(x, METER, c), to, c);
			}
		}
		
	},
	/**
	 * A constant for angstroms.
	 */
	ANGSTROM{

		@Override
		public BigDecimal convert(BigDecimal x, DistanceUnit to, MathContext c) {
			switch (to) {
			case METER:
				return new BigDecimal("1E-10").multiply(x, c);
			case ANGSTROM:
				return x.round(c);
			default:
				return METER.convert(convert(x, METER, c), to, c);
			}
		}
		
	},
	/**
	 * A constant for thous.
	 */
	THOU{

		@Override
		public BigDecimal convert(BigDecimal x, DistanceUnit to, MathContext c) {
			switch (to) {
			case FOOT:
				return BigDecimal.ONE.divide(new BigDecimal(12000), c).multiply(x, c);
			case THOU:
				return x.round(c);
			default:
				return FOOT.convert(convert(x, FOOT, c), to, c);
			}
		}
		
	},
	/**
	 * A constant for inches.
	 */
	INCH{

		@Override
		public BigDecimal convert(BigDecimal x, DistanceUnit to, MathContext c) {
			switch (to) {
			case METER:
				return new BigDecimal("2.54E-2").multiply(x, c);
			case INCH:
				return x.round(c);
			default:
				return METER.convert(convert(x, METER, c), to, c);
			}
		}
		
	},
	/**
	 * A constant for feet.
	 */
	FOOT{

		@Override
		public BigDecimal convert(BigDecimal x, DistanceUnit to, MathContext c) {
			switch (to) {
			case THOU:
				return new BigDecimal(12000).multiply(x, c);
			case METER:
				return new BigDecimal("0.3048").multiply(x, c);
			case FOOT:
				return x.round(c);
			default:
				return METER.convert(convert(x, METER, c), to, c);
			}
		}
		
	},
	/**
	 * A constant for yard.
	 */
	YARD{

		@Override
		public BigDecimal convert(BigDecimal x, DistanceUnit to, MathContext c) {
			switch (to) {
			case METER:
				return new BigDecimal("9.144E-1").multiply(x, c);
			case YARD:
				return x.round(c);
			default:
				return METER.convert(convert(x, METER, c), to, c);
			}
		}
		
	},
	/**
	 * A constant for chains.
	 */
	CHAIN{

		@Override
		public BigDecimal convert(BigDecimal x, DistanceUnit to, MathContext c) {
			switch (to) {
			case METER:
				return new BigDecimal("20.1168").multiply(x, c);
			case CHAIN:
				return x.round(c);
			default:
				return METER.convert(convert(x, METER, c), to, c);
			}
		}
		
	},
	/**
	 * A constant for furlongs.
	 */
	FURLONG{

		@Override
		public BigDecimal convert(BigDecimal x, DistanceUnit to, MathContext c) {
			switch (to) {
			case METER:
				return new BigDecimal("201.168").multiply(x, c);
			case FURLONG:
				return x.round(c);
			default:
				return METER.convert(convert(x, METER, c), to, c);
			}
		}
		
	},
	/**
	 * A constant for miles.
	 */
	MILE{

		@Override
		public BigDecimal convert(BigDecimal x, DistanceUnit to, MathContext c) {
			switch (to) {
			case METER:
				return new BigDecimal("1609.344").multiply(x, c);
			case MILE:
				return x.round(c);
			default:
				return METER.convert(convert(x, METER, c), to, c);
			}
		}
		
	},
	/**
	 * A constant for fathoms.
	 */
	FATHOM{

		@Override
		public BigDecimal convert(BigDecimal x, DistanceUnit to, MathContext c) {
			switch (to) {
			case METER:
				return new BigDecimal("1.8288").multiply(x, c);
			case FATHOM:
				return x.round(c);
			default:
				return METER.convert(convert(x, METER, c), to, c);
			}
		}
		
	},
	/**
	 * A constant for cables.
	 */
	CABLE{

		@Override
		public BigDecimal convert(BigDecimal x, DistanceUnit to, MathContext c) {
			switch (to) {
			case METER:
				return new BigDecimal("185.2").multiply(x, c);
			case CABLE:
				return x.round(c);
			default:
				return METER.convert(convert(x, METER, c), to, c);
			}
		}
		
	},
	/**
	 * A constant for nautical miles.
	 */
	NAUTICAL_MILE{

		@Override
		public BigDecimal convert(BigDecimal x, DistanceUnit to, MathContext c) {
			switch (to) {
			case METER:
				return new BigDecimal("1852").multiply(x, c);
			case MILE:
				return x.round(c);
			default:
				return METER.convert(convert(x, METER, c), to, c);
			}
		}
		
	},
	/**
	 * A constant for astronomical units.
	 */
	ASTRONOMICAL_UNIT{

		@Override
		public BigDecimal convert(BigDecimal x, DistanceUnit to, MathContext c) {
			switch (to) {
			case METER:
				return new BigDecimal("149597870700").multiply(x, c);
			case ASTRONOMICAL_UNIT:
				return x.round(c);
			default:
				return METER.convert(convert(x, METER, c), to, c);
			}
		}
		
	},
	/**
	 * A constant for light years.
	 */
	LIGHT_YEAR{

		@Override
		public BigDecimal convert(BigDecimal x, DistanceUnit to, MathContext c) {
			switch (to) {
			case METER:
				return new BigDecimal("9.4607304725808E15").multiply(x, c);
			case LIGHT_YEAR:
				return x.round(c);
			default:
				return METER.convert(convert(x, METER, c), to, c);
			}
		}
		
	},
	/**
	 * A constant for parsecs.
	 */
	PARSEC{

		@Override
		public BigDecimal convert(BigDecimal x, DistanceUnit to, MathContext c) {
			switch (to) {
			case METER:
				return new BigDecimal("3.08567758149137").multiply(x, c);
			case PARSEC:
				return x.round(c);
			default:
				return METER.convert(convert(x, METER, c), to, c);
			}
		}
		
	}
	;

	/*
	 * Most Recent Date: 12 Aug 2021-----------------------------------------------
	 * Most recent time created: 15:41:53--------------------------------------
	 */
	/**
	 * Returns the value of the given {@code BigDecimal} converted from {@code this}
	 * to the {@code DistanceUnit} argument using the given {@code MathContext} for
	 * numeric approximations.
	 * 
	 * @param x   the value to be converted.
	 * @param to    the destination of the conversion.
	 * @param c the object for approximation.
	 * @return the given value converted from {@code this} to the type argument.
	 */
	@Override
	public abstract BigDecimal convert(BigDecimal x, DistanceUnit to, MathContext c);
}
