/**
 * 
 */
package mathaid.calculator.base.converter;

import java.math.BigDecimal;
import java.math.MathContext;

import mathaid.ExceptionMessage;

/*
 * Date: 1 Jul 2021----------------------------------------------------------- 
 * Time created: 20:05:18---------------------------------------------------  
 * Package: mathaid.calculator.base.function------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: AreaUnit.java------------------------------------------------------ 
 * Class name: AreaUnit------------------------------------------------ 
 */
/**
 * An object that represents the area units of measurements e.g acres, hectares
 * which are used for measuring areas in a 2D plain.
 * 
 * @author Oruovo Anthony Etineakpopha
 */
public enum AreaUnit implements Convertible<AreaUnit, BigDecimal, MathContext> {
	/**
	 * A constant for square kilometers.
	 */
	SQ_MILLIMETER {

		@Override
		public BigDecimal convert(BigDecimal value, AreaUnit type, MathContext context) {
			switch (type) {
			case SQ_METER:
				return new BigDecimal("1E-6").multiply(value, context);
			case SQ_MILLIMETER:
				return value.round(context);
			default:
				return SQ_METER.convert(convert(value, SQ_METER, context), type, context);
			}
		}

	},
	/**
	 * A constant for square centimeters.
	 */
	SQ_CENTIMETER {

		@Override
		public BigDecimal convert(BigDecimal value, AreaUnit type, MathContext context) {
			switch (type) {
			case SQ_METER:
				return new BigDecimal("1E-4").multiply(value, context);
			case SQ_CENTIMETER:
				return value.round(context);
			default:
				return SQ_METER.convert(convert(value, SQ_METER, context), type, context);
			}
		}

	},
	/**
	 * A constant for square meters.
	 */
	SQ_METER {

		@Override
		public BigDecimal convert(BigDecimal value, AreaUnit to, MathContext context) {
			switch (to) {
			case ACRE:
				return new BigDecimal("0.00024710538147").multiply(value, context);
			case ARE:
				return new BigDecimal("1E-2").multiply(value, context);
			case HECTARE:
				return new BigDecimal("1E-4").multiply(value, context);
			case SQ_CENTIMETER:
				return new BigDecimal("1E4").multiply(value, context);
			case SQ_FOOT:
				return new BigDecimal("10.7639104167097").multiply(value, context);
			case SQ_INCH:
				return new BigDecimal("1550.0031000062").multiply(value, context);
			case SQ_KILOMETER:
				return new BigDecimal("1E-6").multiply(value, context);
			case SQ_METER:
				return value.round(context);
			case SQ_MILE:
				return new BigDecimal("3.86102158542446E-7").multiply(value, context);
			case SQ_MILLIMETER:
				return new BigDecimal("1E6").multiply(value, context);
			case SQ_YARD:
				return new BigDecimal("1.19599004630108").multiply(value, context);
			default:
				new mathaid.IllegalArgumentException(ExceptionMessage.UNKNOWN_UNIT);
				throw new IllegalArgumentException("Unknown unit");
			}
		}

	},
	/**
	 * A constant for ares.
	 */
	ARE {
		@Override
		public BigDecimal convert(BigDecimal value, AreaUnit type, MathContext context) {
			switch (type) {
			case SQ_METER:
				return new BigDecimal("100").multiply(value, context);
			case ARE:
				return value.round(context);
			default:
				return SQ_METER.convert(convert(value, SQ_METER, context), type, context);
			}
		}
	},
	/**
	 * A constant for hectares.
	 */
	HECTARE {
		@Override
		public BigDecimal convert(BigDecimal value, AreaUnit type, MathContext context) {
			switch (type) {
			case SQ_METER:
				return new BigDecimal("1E4").multiply(value, context);
			case HECTARE:
				return value.round(context);
			default:
				return SQ_METER.convert(convert(value, SQ_METER, context), type, context);
			}
		}
	},
	/**
	 * A constant for square kilometers.
	 */
	SQ_KILOMETER {
		@Override
		public BigDecimal convert(BigDecimal value, AreaUnit type, MathContext context) {
			switch (type) {
			case SQ_METER:
				return new BigDecimal("1E6").multiply(value, context);
			case SQ_KILOMETER:
				return value.round(context);
			default:
				return SQ_METER.convert(convert(value, SQ_METER, context), type, context);
			}
		}
	},
	/**
	 * A constant for square inches.
	 */
	SQ_INCH {
		@Override
		public BigDecimal convert(BigDecimal value, AreaUnit type, MathContext context) {
			switch (type) {
			case SQ_METER:
				return new BigDecimal("6.4516E-4").multiply(value, context);
			case SQ_INCH:
				return value.round(context);
			default:
				return SQ_METER.convert(convert(value, SQ_METER, context), type, context);
			}
		}
	},
	/**
	 * A constant for square feet.
	 */
	SQ_FOOT {
		@Override
		public BigDecimal convert(BigDecimal value, AreaUnit type, MathContext context) {
			switch (type) {
			case SQ_METER:
				return new BigDecimal("9.290304E2").multiply(value, context);
			case SQ_FOOT:
				return value.round(context);
			default:
				return SQ_METER.convert(convert(value, SQ_METER, context), type, context);
			}
		}
	},
	/**
	 * A constant for square yards.
	 */
	SQ_YARD {
		@Override
		public BigDecimal convert(BigDecimal value, AreaUnit type, MathContext context) {
			switch (type) {
			case SQ_METER:
				return new BigDecimal("0.83612736").multiply(value, context);
			case SQ_YARD:
				return value.round(context);
			default:
				return SQ_METER.convert(convert(value, SQ_METER, context), type, context);
			}
		}
	},
	/**
	 * A constant for acres.
	 */
	ACRE {
		@Override
		public BigDecimal convert(BigDecimal value, AreaUnit type, MathContext context) {
			switch (type) {
			case SQ_METER:
				return new BigDecimal("4046.8564224").multiply(value, context);
			case ACRE:
				return value.round(context);
			default:
				return SQ_METER.convert(convert(value, SQ_METER, context), type, context);
			}
		}
	},
	/**
	 * A constant for square miles.
	 */
	SQ_MILE {
		@Override
		public BigDecimal convert(BigDecimal value, AreaUnit type, MathContext context) {
			switch (type) {
			case SQ_METER:
				return new BigDecimal("2.589988110336E6").multiply(value, context);
			case SQ_MILE:
				return value.round(context);
			default:
				return SQ_METER.convert(convert(value, SQ_METER, context), type, context);
			}
		}
	};

	/*
	 * Most Recent Date: 12 Aug 2021-----------------------------------------------
	 * Most recent time created: 15:24:58--------------------------------------
	 */
	/**
	 * Returns the value of the given {@code BigDecimal} converted from {@code this}
	 * to the {@code AreaUnit} argument using the given {@code MathContext} for
	 * numeric approximations.
	 * 
	 * @param value   the value to be converted.
	 * @param type    the destination of the conversion.
	 * @param context the object for approximation.
	 * @return the given value converted from {@code this} to the type argument.
	 */
	@Override
	public abstract BigDecimal convert(BigDecimal value, AreaUnit type, MathContext context);
}
