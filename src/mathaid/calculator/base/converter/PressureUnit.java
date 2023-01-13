/**
 * 
 */
package mathaid.calculator.base.converter;

import java.math.BigDecimal;
import java.math.MathContext;

import mathaid.ExceptionMessage;

/*
 * Date: 10 Jul 2021----------------------------------------------------------- 
 * Time created: 08:26:28---------------------------------------------------  
 * Package: mathaid.calculator.base.converter------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: PressureUnit.java------------------------------------------------------ 
 * Class name: PressureUnit------------------------------------------------ 
 */
/**
 * An object containing a set of constants that can convert values between pressure
 * units such as pascal and bars.
 * @author Oruovo Anthony Etineakpopha
 */
public enum PressureUnit implements Convertible<PressureUnit, BigDecimal, MathContext> {
	/**
	 * A constant representing micropascal.
	 */
	MICROPASCAL {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("1E-6").multiply(x, mc);
			case MICROPASCAL:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing millipascal.
	 */
	MILLIPASCAL {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("1E-3").multiply(x, mc);
			case MILLIPASCAL:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing pascal.
	 */
	PASCAL {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case ATMOSPHERE:
				return new BigDecimal("9.869232667160128300024673E-6").multiply(x, mc);
			case BAR:
				return new BigDecimal("1E-5").multiply(x, mc);
			case GIGAPASCAL:
				return new BigDecimal("1E-9").multiply(x, mc);
			case HECTOPASCAL:
				return new BigDecimal("1E-2").multiply(x, mc);
			case INCH_OF_MERCURY_ZERO_DEGEREE:
				return new BigDecimal("0.000295299833010100918878").multiply(x, mc);
			case INCH_OF_WATER_COLUMN:
				return new BigDecimal("0.004014630759755623002244").multiply(x, mc);
			case KILOGRAM_FORCE_PER_SQUARE_CENTIMETER:
				return new BigDecimal("1.019716212977928242570093E-5").multiply(x, mc);
			case KILOGRAM_FORCE_PER_SQUARE_METER:
				return new BigDecimal("0.1019716212977928242570093").multiply(x, mc);
			case KILOPASCAL:
				return new BigDecimal("1E-3").multiply(x, mc);
			case KILOPOND_PER_SQUARE_CENTIMETER:
				return new BigDecimal("1.019716212977928242570093E-5").multiply(x, mc);
			case KILOPOND_PER_SQUARE_METER:
				return new BigDecimal("0.1019716212977928242570093").multiply(x, mc);
			case KILOPOUND_PER_SQUARE_FOOT:
				return new BigDecimal("2.088543423315012698221071E-5").multiply(x, mc);
			case KILOPOUND_PER_SQUARE_INCH:
				return new BigDecimal("1.45037737730209215154241E-7").multiply(x, mc);
			case MEGAPASCAL:
				return new BigDecimal("1E-6").multiply(x, mc);
			case MEGAPOUND_PER_SQUARE_FOOT:
				return new BigDecimal("2.088543423315012698221071E-8").multiply(x, mc);
			case MEGAPOUND_PER_SQUARE_INCH:
				return new BigDecimal("1.45037737730209215154241E-10").multiply(x, mc);
			case MICROBAR:
				return new BigDecimal("10").multiply(x, mc);
			case MICROPASCAL:
				return new BigDecimal("1E6").multiply(x, mc);
			case MILLIBAR:
				return new BigDecimal("1E-2").multiply(x, mc);
			case MILLIMETER_OF_MERCURY_ZERO_DEGEREE:
				return new BigDecimal("0.007500615758456563339513").multiply(x, mc);
			case MILLIMETER_OF_WATER_COLUMN:
				return new BigDecimal("0.101971621297792824257009").multiply(x, mc);
			case MILLIPASCAL:
				return new BigDecimal("1E3").multiply(x, mc);
			case MILLITORR:
				return new BigDecimal("7.500616827041697508018752").multiply(x, mc);
			case PASCAL:
				return x.round(mc);
			case POUND_PER_SQUARE_FOOT:
				return new BigDecimal("0.020885434233150126982211").multiply(x, mc);
			case POUND_PER_SQUARE_INCH:
				return new BigDecimal("0.000145037737730209215154241").multiply(x, mc);
			case TECHNICAL_ATMOSPHERE:
				return new BigDecimal("1.019716212977928242570093E-5").multiply(x, mc);
			case TORR:
				return new BigDecimal("0.007500616827041697508018752").multiply(x, mc);
			default:
			}
			new mathaid.IllegalArgumentException(ExceptionMessage.UNKNOWN_UNIT);
			throw new IllegalArgumentException("Unknown unit");
		}
	},
	/**
	 * A constant representing hectopascal.
	 */
	HECTOPASCAL {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("1E2").multiply(x, mc);
			case HECTOPASCAL:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing kilopascal.
	 */
	KILOPASCAL {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("1E3").multiply(x, mc);
			case KILOPASCAL:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing megapascal.
	 */
	MEGAPASCAL {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("1E6").multiply(x, mc);
			case MEGAPASCAL:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing gigapascal.
	 */
	GIGAPASCAL {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("1E9").multiply(x, mc);
			case GIGAPASCAL:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing atmospheres.
	 */
	ATMOSPHERE {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("101325").multiply(x, mc);
			case ATMOSPHERE:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing microbars.
	 */
	MICROBAR {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("1E-1").multiply(x, mc);
			case MICROBAR:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing millibars.
	 */
	MILLIBAR {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("1E2").multiply(x, mc);
			case MILLIBAR:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing bars.
	 */
	BAR {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("1E5").multiply(x, mc);
			case BAR:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing millitorrs.
	 */
	MILLITORR {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("4053").divide(new BigDecimal("30400"), mc).multiply(x, mc);
			case MILLITORR:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing torrs.
	 */
	TORR {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("20265").divide(new BigDecimal(152), mc).multiply(x, mc);
			case TORR:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing technical atmospheres.
	 */
	TECHNICAL_ATMOSPHERE {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("98066.5").multiply(x, mc);
			case TECHNICAL_ATMOSPHERE:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing kilogram-force per square centimeter.
	 */
	KILOGRAM_FORCE_PER_SQUARE_CENTIMETER {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("98066.5").multiply(x, mc);
			case KILOGRAM_FORCE_PER_SQUARE_CENTIMETER:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing kilogramme-force per square metre.
	 */
	KILOGRAM_FORCE_PER_SQUARE_METER {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("9.80665").multiply(x, mc);
			case KILOGRAM_FORCE_PER_SQUARE_METER:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing kilopound per square centimetre.
	 */
	KILOPOND_PER_SQUARE_CENTIMETER {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("98066.5").multiply(x, mc);
			case KILOPOND_PER_SQUARE_CENTIMETER:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing kilopound per square metre.
	 */
	KILOPOND_PER_SQUARE_METER {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("9.80665").multiply(x, mc);
			case KILOPOND_PER_SQUARE_METER:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing pound per square inch.
	 */
	POUND_PER_SQUARE_INCH {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("6894.757293168361336722673").multiply(x, mc);
			case POUND_PER_SQUARE_INCH:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing kilopound per square inch.
	 */
	KILOPOUND_PER_SQUARE_INCH {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("6894757.293168361336722673").multiply(x, mc);
			case KILOPOUND_PER_SQUARE_INCH:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing megapound per square inch.
	 */
	MEGAPOUND_PER_SQUARE_INCH {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("6894757293.168361336722673").multiply(x, mc);
			case MEGAPOUND_PER_SQUARE_INCH:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing pound per square foot.
	 */
	POUND_PER_SQUARE_FOOT {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("47.88025898033584261612968").multiply(x, mc);
			case POUND_PER_SQUARE_FOOT:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing kilopound per square foot.
	 */
	KILOPOUND_PER_SQUARE_FOOT {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("47880.25898033584261612968").multiply(x, mc);
			case KILOPOUND_PER_SQUARE_FOOT:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing megapound per square foot.
	 */
	MEGAPOUND_PER_SQUARE_FOOT {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("47880258.98033584261612968").multiply(x, mc);
			case MEGAPOUND_PER_SQUARE_FOOT:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing milliletres of mercury at 0&deg;.
	 */
	MILLIMETER_OF_MERCURY_ZERO_DEGEREE {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("133.322387415").multiply(x, mc);
			case MILLIMETER_OF_MERCURY_ZERO_DEGEREE:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing inches of mercury at 0&deg;.
	 */
	INCH_OF_MERCURY_ZERO_DEGEREE {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("3386388640341").multiply(x, mc);
			case INCH_OF_MERCURY_ZERO_DEGEREE:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing millimetres of water column.
	 */
	MILLIMETER_OF_WATER_COLUMN {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("9.80665").multiply(x, mc);
			case MILLIMETER_OF_WATER_COLUMN:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing inches of water columns.
	 */
	INCH_OF_WATER_COLUMN {
		@Override
		public BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc) {
			switch (to) {
			case PASCAL:
				return new BigDecimal("249.08891").multiply(x, mc);
			case INCH_OF_WATER_COLUMN:
				return x.round(mc);
			default:
				return PASCAL.convert(convert(x, PASCAL, mc), to, mc);
			}
		}
	};

	/*
	 * Most Recent Date: 12 Aug 2021-----------------------------------------------
	 * Most recent time created: 21:13:00--------------------------------------
	 */
	/**
	 * Returns the value of the given {@code BigDecimal} converted from {@code this}
	 * to the {@code PressureUnit} argument using the given {@code MathContext} for
	 * numeric approximations.
	 * 
	 * @param x  the value to be converted.
	 * @param to the destination of the conversion.
	 * @param mc the object for approximation.
	 * @return the given value converted from {@code this} to the type argument.
	 */
	@Override
	public abstract BigDecimal convert(BigDecimal x, PressureUnit to, MathContext mc);
}
