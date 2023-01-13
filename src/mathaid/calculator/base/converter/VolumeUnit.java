/**
 * 
 */
package mathaid.calculator.base.converter;

import java.math.BigDecimal;
import java.math.MathContext;

import mathaid.ExceptionMessage;

/*
 * Date: 4 Jul 2021----------------------------------------------------------- 
 * Time created: 08:17:46---------------------------------------------------  
 * Package: mathaid.calculator.base.function------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: VolumeUnit.java------------------------------------------------------ 
 * Class name: VolumeUnit------------------------------------------------ 
 */
/**
 * An object containing a set of constants that can convert values between
 * volume units such as litres.
 * @author Oruovo Anthony Etineakpopha
 */
public enum VolumeUnit implements Convertible<VolumeUnit, BigDecimal, MathContext> {
	/**
	 * A constant representing cubic centimetres.
	 */
	CUBIC_CENTIMETER {
		@Override
		public BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context) {
			switch (type) {
			case LITER:
				return new BigDecimal("0.001").multiply(value, context);
			case CUBIC_CENTIMETER:
				return value.round(context);
			default:
				return LITER.convert(convert(value, LITER, context), type, context);
			}
		}
	},
	/**
	 * A constant representing millilitres.
	 */
	MILLILITER {
		@Override
		public BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context) {
			switch (type) {
			case LITER:
				return new BigDecimal("0.001").multiply(value, context);
			case MILLILITER:
				return value.round(context);
			default:
				return LITER.convert(convert(value, LITER, context), type, context);
			}
		}
	},
	/**
	 * A constant representing litres.
	 */
	LITER {
		@Override
		public BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context) {
			switch (type) {
			case BARREL:
				return new BigDecimal("0.0083864143251288").multiply(value, context);
			case CUBIC_CENTIMETER:
				return new BigDecimal("1000").multiply(value, context);
			case CUBIC_DECIMETER:
				return value.round(context);
			case CUBIC_FOOT:
				return new BigDecimal(
						"0.0035314666721488590250438010354002626932054680673957440070027413312336102654124235049286663169418494")
								.multiply(value, context);
			case CUBIC_INCH:
				return new BigDecimal(
						"61.0237440947322839527568818917165393385904882045984564410073702037167853863266781651673539567551576")
								.multiply(value, context);
			case CUBIC_METER:
				return new BigDecimal("0.001").multiply(value, context);
			case CUBIC_YARD:
				return new BigDecimal("0.001307950619314392231498").multiply(value, context);
			case CUP:
				return new BigDecimal("3.519507972785404600436859").multiply(value, context);
			case CUP_US:
				return convert(value, MILLILITER, context).multiply(new BigDecimal("236.5882365"), context);
			case FLUID_OUNCE:
				return new BigDecimal("35.19507972785404600436859").multiply(value, context);
			case FLUID_OUNCE_US:
				return convert(value, TABLE_SPOON_US, context).multiply(BigDecimal.valueOf(2), context);
			case GALLON:
				return new BigDecimal("0.219969248299087787527304").multiply(value, context);
			case GALLON_US:
				return new BigDecimal("3.785411784").multiply(value, context);
			case HECTOLITER:
				return new BigDecimal("0.01").multiply(value, context);
			case LITER:
				return value.round(context);
			case MILLILITER:
				return new BigDecimal("1000").multiply(value, context);
			case PINT:
				return new BigDecimal("1.759753986392702300218429").multiply(value, context);
			case PINT_US:
				return convert(value, CUP_US, context).multiply(BigDecimal.valueOf(2), context);
			case QUART:
				return convert(value, PINT, context).multiply(BigDecimal.valueOf(2), context);
			case QUART_US:
				return convert(value, FLUID_OUNCE_US, context).multiply(BigDecimal.valueOf(32), context);
			case TABLE_SPOON:
				return new BigDecimal("56.31212756456647360698974").multiply(value, context);
			case TABLE_SPOON_US:
				return convert(value, FLUID_OUNCE_US, context).multiply(new BigDecimal("0.5"), context);
			case TEASPOON:
				return new BigDecimal("168.9393826936994208209692").multiply(value, context);
			case TEASPOON_US:
				return convert(value, CUP_US, context).multiply(BigDecimal.ONE.divide(new BigDecimal(48), context));
			default:
			}
			new mathaid.IllegalArgumentException(ExceptionMessage.UNKNOWN_UNIT);
			throw new IllegalArgumentException("Unknown unit");
		}
	},
	/**
	 * A constant representing cubic decimetres.
	 */
	CUBIC_DECIMETER {
		@Override
		public BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context) {
			return LITER.convert(value, type, context);
		}
	},
	/**
	 * A constant representing hectolitres.
	 */
	HECTOLITER {
		@Override
		public BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context) {
			switch (type) {
			case LITER:
				return new BigDecimal("100").multiply(value, context);
			case HECTOLITER:
				return value.round(context);
			default:
				return LITER.convert(convert(value, LITER, context), type, context);
			}
		}
	},
	/**
	 * A constant representing cubic metres.
	 */
	CUBIC_METER {
		@Override
		public BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context) {
			switch (type) {
			case LITER:
				return new BigDecimal("1000").multiply(value, context);
			case CUBIC_METER:
				return value.round(context);
			default:
				return LITER.convert(convert(value, LITER, context), type, context);
			}
		}
	},
	/**
	 * A constant representing cubic inches.
	 */
	CUBIC_INCH {
		@Override
		public BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context) {
			switch (type) {
			case LITER:
				return new BigDecimal("0.016378064").multiply(value, context);
			case CUBIC_INCH:
				return value.round(context);
			default:
				return LITER.convert(convert(value, LITER, context), type, context);
			}
		}
	},
	/**
	 * A constant representing cubic feet.
	 */
	CUBIC_FOOT {
		@Override
		public BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context) {
			switch (type) {
			case LITER:
				return new BigDecimal("28.316846592").multiply(value, context);
			case CUBIC_FOOT:
				return value.round(context);
			default:
				return LITER.convert(convert(value, LITER, context), type, context);
			}
		}
	},
	/**
	 * A constant representing cubic yards.
	 */
	CUBIC_YARD {
		@Override
		public BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context) {
			switch (type) {
			case LITER:
				return new BigDecimal("764554857984").multiply(value, context);
			case CUBIC_YARD:
				return value.round(context);
			default:
				return LITER.convert(convert(value, LITER, context), type, context);
			}
		}
	},
	/**
	 * A constant representing teaspoons in the US.
	 */
	TEASPOON_US {
		@Override
		public BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context) {
			switch (type) {
			case LITER:
				return new BigDecimal("0.00492892159375").multiply(value, context);
			case TEASPOON_US:
				return value.round(context);
			default:
				return LITER.convert(convert(value, LITER, context), type, context);
			}
		}
	},
	/**
	 * A constant representing tablespoons in the US.
	 */
	TABLE_SPOON_US {
		@Override
		public BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context) {
			switch (type) {
			case LITER:
				return new BigDecimal("0.01478676478125").multiply(value, context);
			case TABLE_SPOON_US:
				return value.round(context);
			default:
				return LITER.convert(convert(value, LITER, context), type, context);
			}
		}
	},
	/**
	 * A constant representing fliud ounces in the US.
	 */
	FLUID_OUNCE_US {
		@Override
		public BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context) {
			switch (type) {
			case LITER:
				return new BigDecimal("0.0295735295625").multiply(value, context);
			case FLUID_OUNCE_US:
				return value.round(context);
			default:
				return LITER.convert(convert(value, LITER, context), type, context);
			}
		}
	},
	/**
	 * A constant representing cups in the US.
	 */
	CUP_US {
		@Override
		public BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context) {
			switch (type) {
			case LITER:
				return new BigDecimal("0.2365882365").multiply(value, context);
			case CUP_US:
				return value.round(context);
			default:
				return LITER.convert(convert(value, LITER, context), type, context);
			}
		}
	},
	/**
	 * A constant representing pints in the US.
	 */
	PINT_US {
		@Override
		public BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context) {
			switch (type) {
			case LITER:
				return new BigDecimal("0.473176473").multiply(value, context);
			case PINT_US:
				return value.round(context);
			default:
				return LITER.convert(convert(value, LITER, context), type, context);
			}
		}
	},
	/**
	 * A constant representing quarts in the US.
	 */
	QUART_US {
		@Override
		public BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context) {
			switch (type) {
			case LITER:
				return new BigDecimal("0.946352946").multiply(value, context);
			case QUART_US:
				return value.round(context);
			default:
				return LITER.convert(convert(value, LITER, context), type, context);
			}
		}
	},
	/**
	 * A constant representing gallons in the US.
	 */
	GALLON_US {
		@Override
		public BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context) {
			switch (type) {
			case LITER:
				return new BigDecimal("3.785411784").multiply(value, context);
			case GALLON_US:
				return value.round(context);
			default:
				return LITER.convert(convert(value, LITER, context), type, context);
			}
		}
	},
	/**
	 * A constant representing imperial teaspoons.
	 */
	TEASPOON {
		@Override
		public BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context) {
			switch (type) {
			case LITER:
				return new BigDecimal("0.0059193880208333333333333333333333333333333").multiply(value, context);
			case TEASPOON:
				return value.round(context);
			default:
				return LITER.convert(convert(value, LITER, context), type, context);
			}
		}
	},
	/**
	 * A constant representing imperial tablespoons.
	 */
	TABLE_SPOON {
		@Override
		public BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context) {
			switch (type) {
			case LITER:
				return new BigDecimal("0.0177581640625").multiply(value, context);
			case TABLE_SPOON:
				return value.round(context);
			default:
				return LITER.convert(convert(value, LITER, context), type, context);
			}
		}
	},
	/**
	 * A constant representing imperial fluid ounces.
	 */
	FLUID_OUNCE {
		@Override
		public BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context) {
			switch (type) {
			case LITER:
				return new BigDecimal("0.0284130625").multiply(value, context);
			case FLUID_OUNCE:
				return value.round(context);
			default:
				return LITER.convert(convert(value, LITER, context), type, context);
			}
		}
	},
	/**
	 * A constant representing imperial cups.
	 */
	CUP {
		@Override
		public BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context) {
			switch (type) {
			case LITER:
				return new BigDecimal("0.284130625").multiply(value, context);
			case CUP:
				return value.round(context);
			default:
				return LITER.convert(convert(value, LITER, context), type, context);
			}
		}
	},
	/**
	 * A constant representing imperial pints.
	 */
	PINT {
		@Override
		public BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context) {
			switch (type) {
			case LITER:
				return new BigDecimal("0.568261").multiply(value, context);
			case PINT:
				return value.round(context);
			default:
				return LITER.convert(convert(value, LITER, context), type, context);
			}
		}
	},
	/**
	 * A constant representing imperial quarts.
	 */
	QUART {
		@Override
		public BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context) {
			switch (type) {
			case LITER:
				return new BigDecimal("1.1365225").multiply(value, context);
			case QUART:
				return value.round(context);
			default:
				return LITER.convert(convert(value, LITER, context), type, context);
			}
		}
	},
	/**
	 * A constant representing imperial gallons.
	 */
	GALLON {
		@Override
		public BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context) {
			switch (type) {
			case LITER:
				return new BigDecimal("4.54609").multiply(value, context);
			case GALLON:
				return value.round(context);
			default:
				return LITER.convert(convert(value, LITER, context), type, context);
			}
		}
	},
	/**
	 * A constant representing barrels.
	 */
	BARREL {
		@Override
		public BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context) {
			switch (type) {
			case LITER:
				return new BigDecimal("158.987294928").multiply(value, context);
			case BARREL:
				return value.round(context);
			default:
				return LITER.convert(convert(value, LITER, context), type, context);
			}
		}
	};

	/*
	 * Most Recent Date: 4 Jul 2021-----------------------------------------------
	 * Most recent time created: 08:18:00--------------------------------------
	 */
	/**
	 * Returns the value of the given {@code BigDecimal} converted from {@code this}
	 * to the {@code VolumeUnit} argument using the given {@code MathContext} for
	 * numeric approximations.
	 * 
	 * @param value  the value to be converted.
	 * @param type the destination of the conversion.
	 * @param context the object for approximation.
	 * @return the given value converted from {@code this} to the type argument.
	 */
	@Override
	public abstract BigDecimal convert(BigDecimal value, VolumeUnit type, MathContext context);

}
