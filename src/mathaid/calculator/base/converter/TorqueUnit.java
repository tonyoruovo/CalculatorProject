/**
 * 
 */
package mathaid.calculator.base.converter;

import java.math.BigDecimal;
import java.math.MathContext;

import mathaid.ExceptionMessage;

/*
 * Date: 10 Jul 2021----------------------------------------------------------- 
 * Time created: 07:48:03---------------------------------------------------  
 * Package: mathaid.calculator.base.converter------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: TorqueUnit.java------------------------------------------------------ 
 * Class name: TorqueUnit------------------------------------------------ 
 */
/**
 * An object containing a set of constants that can convert values between
 * torque units such as Newton-metres.
 * 
 * @author Oruovo Anthony Etineakpopha
 */
public enum TorqueUnit implements Convertible<TorqueUnit, BigDecimal, MathContext> {
	/**
	 * A constant representing micronewton-metres.
	 */
	MICRONEWTON_METER {
		@Override
		public BigDecimal convert(BigDecimal x, TorqueUnit to, MathContext mc) {
			switch (to) {
			case NEWTON_METER:
				return new BigDecimal("1E-6").multiply(x, mc);
			case MICRONEWTON_METER:
				return x.round(mc);
			default:
				return NEWTON_METER.convert(convert(x, NEWTON_METER, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing millinewton-metres.
	 */
	MILLINEWTON_METER {
		@Override
		public BigDecimal convert(BigDecimal x, TorqueUnit to, MathContext mc) {
			switch (to) {
			case NEWTON_METER:
				return new BigDecimal("1E-3").multiply(x, mc);
			case MILLINEWTON_METER:
				return x.round(mc);
			default:
				return NEWTON_METER.convert(convert(x, NEWTON_METER, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing Newton-metres.
	 */
	NEWTON_METER {
		@Override
		public BigDecimal convert(BigDecimal x, TorqueUnit to, MathContext mc) {
			switch (to) {
			case DYNE_CENTIMETER:
				return new BigDecimal("1E7").multiply(x, mc);
			case DYNE_METER:
				return new BigDecimal("1E5").multiply(x, mc);
			case DYNE_MILLIMETER:
				return new BigDecimal("1E8").multiply(x, mc);
			case GRAM_FORCE_CENTIMETER:
				return new BigDecimal("10197.16212977928242570093").multiply(x, mc);
			case GRAM_FORCE_METER:
				return new BigDecimal("101.9716212977928242570093").multiply(x, mc);
			case GRAM_FORCE_MILLIMETER:
				return new BigDecimal("101971.6212977928242570093").multiply(x, mc);
			case KILOGRAM_FORCE_CENTIMETER:
				return new BigDecimal("10.19716212977928242570093").multiply(x, mc);
			case KILOGRAM_FORCE_METER:
				return new BigDecimal("0.1019716212977928242570093").multiply(x, mc);
			case KILOGRAM_FORCE_MILLIMETER:
				return new BigDecimal("101.9716212977928242570093").multiply(x, mc);
			case KILONEWTON_METER:
				return new BigDecimal("1E-3").multiply(x, mc);
			case MEGANEWTON_METER:
				return new BigDecimal("1E-6").multiply(x, mc);
			case MICRONEWTON_METER:
				return new BigDecimal("1E6").multiply(x, mc);
			case MILLINEWTON_METER:
				return new BigDecimal("1E3").multiply(x, mc);
			case NEWTON_CENTIMETER:
				return new BigDecimal("1E2").multiply(x, mc);
			case NEWTON_METER:
				return x.round(mc);
			case NEWTON_MILLIMETER:
				return new BigDecimal("1E3").multiply(x, mc);
			case OUNCE_FORCE_FOOT:
				return new BigDecimal("11.80099438843624582204931").multiply(x, mc);
			case OUNCE_FORCE_INCH:
				return new BigDecimal("0.050745791327184366536985").multiply(x, mc);
			case POUND_FORCE_FOOT:
				return new BigDecimal("0.737567149277265363878082").multiply(x, mc);
			case POUND_FORCE_INCH:
				return new BigDecimal("141.611932661234949864").multiply(x, mc);
			default:
			}
			new mathaid.IllegalArgumentException(ExceptionMessage.UNKNOWN_UNIT);
			throw new IllegalArgumentException("Unknown unit");
		}
	},
	/**
	 * A constant representing kilonewton-metres.
	 */
	KILONEWTON_METER {
		@Override
		public BigDecimal convert(BigDecimal x, TorqueUnit to, MathContext mc) {
			switch (to) {
			case NEWTON_METER:
				return new BigDecimal("1E3").multiply(x, mc);
			case KILONEWTON_METER:
				return x.round(mc);
			default:
				return NEWTON_METER.convert(convert(x, NEWTON_METER, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing meganewton-metres.
	 */
	MEGANEWTON_METER {
		@Override
		public BigDecimal convert(BigDecimal x, TorqueUnit to, MathContext mc) {
			switch (to) {
			case NEWTON_METER:
				return new BigDecimal("1E6").multiply(x, mc);
			case MEGANEWTON_METER:
				return x.round(mc);
			default:
				return NEWTON_METER.convert(convert(x, NEWTON_METER, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing Newton-centimetres.
	 */
	NEWTON_CENTIMETER {
		@Override
		public BigDecimal convert(BigDecimal x, TorqueUnit to, MathContext mc) {
			switch (to) {
			case NEWTON_METER:
				return new BigDecimal("1E-2").multiply(x, mc);
			case NEWTON_CENTIMETER:
				return x.round(mc);
			default:
				return NEWTON_METER.convert(convert(x, NEWTON_METER, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing micronewton-millimetres.
	 */
	NEWTON_MILLIMETER {
		@Override
		public BigDecimal convert(BigDecimal x, TorqueUnit to, MathContext mc) {
			switch (to) {
			case NEWTON_METER:
				return new BigDecimal("1E-3").multiply(x, mc);
			case NEWTON_MILLIMETER:
				return x.round(mc);
			default:
				return NEWTON_METER.convert(convert(x, NEWTON_METER, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing gram-force-metres.
	 */
	GRAM_FORCE_METER {
		@Override
		public BigDecimal convert(BigDecimal x, TorqueUnit to, MathContext mc) {
			switch (to) {
			case NEWTON_METER:
				return new BigDecimal("0.00980665").multiply(x, mc);
			case GRAM_FORCE_METER:
				return x.round(mc);
			default:
				return NEWTON_METER.convert(convert(x, NEWTON_METER, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing gram-force-centimetres.
	 */
	GRAM_FORCE_CENTIMETER {
		@Override
		public BigDecimal convert(BigDecimal x, TorqueUnit to, MathContext mc) {
			switch (to) {
			case NEWTON_METER:
				return new BigDecimal("0.0000980665").multiply(x, mc);
			case GRAM_FORCE_CENTIMETER:
				return x.round(mc);
			default:
				return NEWTON_METER.convert(convert(x, NEWTON_METER, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing gram-force-millimetres.
	 */
	GRAM_FORCE_MILLIMETER {
		@Override
		public BigDecimal convert(BigDecimal x, TorqueUnit to, MathContext mc) {
			switch (to) {
			case NEWTON_METER:
				return new BigDecimal("0.00000980665").multiply(x, mc);
			case GRAM_FORCE_MILLIMETER:
				return x.round(mc);
			default:
				return NEWTON_METER.convert(convert(x, NEWTON_METER, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing kilogramme-force-centimetres.
	 */
	KILOGRAM_FORCE_METER {
		@Override
		public BigDecimal convert(BigDecimal x, TorqueUnit to, MathContext mc) {
			switch (to) {
			case NEWTON_METER:
				return new BigDecimal("9.80665").multiply(x, mc);
			case KILOGRAM_FORCE_METER:
				return x.round(mc);
			default:
				return NEWTON_METER.convert(convert(x, NEWTON_METER, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing kilogramme-force-centimetres.
	 */
	KILOGRAM_FORCE_CENTIMETER {
		@Override
		public BigDecimal convert(BigDecimal x, TorqueUnit to, MathContext mc) {
			switch (to) {
			case NEWTON_METER:
				return new BigDecimal("0.0980665").multiply(x, mc);
			case KILOGRAM_FORCE_CENTIMETER:
				return x.round(mc);
			default:
				return NEWTON_METER.convert(convert(x, NEWTON_METER, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing kilogramme-force-millimetres.
	 */
	KILOGRAM_FORCE_MILLIMETER {
		@Override
		public BigDecimal convert(BigDecimal x, TorqueUnit to, MathContext mc) {
			switch (to) {
			case NEWTON_METER:
				return new BigDecimal("0.00980665").multiply(x, mc);
			case KILOGRAM_FORCE_MILLIMETER:
				return x.round(mc);
			default:
				return NEWTON_METER.convert(convert(x, NEWTON_METER, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing dyne-metres.
	 */
	DYNE_METER {
		@Override
		public BigDecimal convert(BigDecimal x, TorqueUnit to, MathContext mc) {
			switch (to) {
			case NEWTON_METER:
				return new BigDecimal("1E-5").multiply(x, mc);
			case DYNE_METER:
				return x.round(mc);
			default:
				return NEWTON_METER.convert(convert(x, NEWTON_METER, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing dyne-centimetres.
	 */
	DYNE_CENTIMETER {
		@Override
		public BigDecimal convert(BigDecimal x, TorqueUnit to, MathContext mc) {
			switch (to) {
			case NEWTON_METER:
				return new BigDecimal("1E-7").multiply(x, mc);
			case DYNE_CENTIMETER:
				return x.round(mc);
			default:
				return NEWTON_METER.convert(convert(x, NEWTON_METER, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing dyne-millimetres.
	 */
	DYNE_MILLIMETER {
		@Override
		public BigDecimal convert(BigDecimal x, TorqueUnit to, MathContext mc) {
			switch (to) {
			case NEWTON_METER:
				return new BigDecimal("1E-8").multiply(x, mc);
			case DYNE_MILLIMETER:
				return x.round(mc);
			default:
				return NEWTON_METER.convert(convert(x, NEWTON_METER, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing pound-force feet.
	 */
	POUND_FORCE_FOOT {
		@Override
		public BigDecimal convert(BigDecimal x, TorqueUnit to, MathContext mc) {
			switch (to) {
			case NEWTON_METER:
				return new BigDecimal("1.3558179483314004").multiply(x, mc);
			case POUND_FORCE_FOOT:
				return x.round(mc);
			default:
				return NEWTON_METER.convert(convert(x, NEWTON_METER, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing pound-force inches.
	 */
	POUND_FORCE_INCH {
		@Override
		public BigDecimal convert(BigDecimal x, TorqueUnit to, MathContext mc) {
			switch (to) {
			case NEWTON_METER:
				return new BigDecimal("0.1129848290276167").multiply(x, mc);
			case POUND_FORCE_INCH:
				return x.round(mc);
			default:
				return NEWTON_METER.convert(convert(x, NEWTON_METER, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing ounce-force feet.
	 */
	OUNCE_FORCE_FOOT {
		@Override
		public BigDecimal convert(BigDecimal x, TorqueUnit to, MathContext mc) {
			switch (to) {
			case NEWTON_METER:
				return new BigDecimal("0.08473862177071525").multiply(x, mc);
			case OUNCE_FORCE_FOOT:
				return x.round(mc);
			default:
				return NEWTON_METER.convert(convert(x, NEWTON_METER, mc), to, mc);
			}
		}
	},
	/**
	 * A constant representing ounce-force inches.
	 */
	OUNCE_FORCE_INCH {
		@Override
		public BigDecimal convert(BigDecimal x, TorqueUnit to, MathContext mc) {
			switch (to) {
			case NEWTON_METER:
				return new BigDecimal("0.00706155181422604375").multiply(x, mc);
			case OUNCE_FORCE_INCH:
				return x.round(mc);
			default:
				return NEWTON_METER.convert(convert(x, NEWTON_METER, mc), to, mc);
			}
		}
	};

	/*
	 * Most Recent Date: 12 Aug 2021-----------------------------------------------
	 * Most recent time created: 21:32:54--------------------------------------
	 */
	/**
	 * Returns the value of the given {@code BigDecimal} converted from {@code this}
	 * to the {@code TorqueUnit} argument using the given {@code MathContext} for
	 * numeric approximations.
	 * 
	 * @param x  the value to be converted.
	 * @param to the destination of the conversion.
	 * @param mc the object for approximation.
	 * @return the given value converted from {@code this} to the type argument.
	 */
	@Override
	public abstract BigDecimal convert(BigDecimal x, TorqueUnit to, MathContext mc);
}
