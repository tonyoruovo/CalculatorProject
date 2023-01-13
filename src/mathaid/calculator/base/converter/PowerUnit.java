/**
 * 
 */
package mathaid.calculator.base.converter;

import java.math.BigDecimal;
import java.math.MathContext;

import mathaid.ExceptionMessage;

/*
 * Date: 11 Jul 2021----------------------------------------------------------- 
 * Time created: 19:02:04---------------------------------------------------  
 * Package: mathaid.calculator.base.converter------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: PowerUnit.java------------------------------------------------------ 
 * Class name: PowerUnit------------------------------------------------ 
 */
/**
 * An object containing a set of constants that can convert values between power
 * units such as watts and horsepower.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public enum PowerUnit implements Convertible<PowerUnit, BigDecimal, MathContext> {
	/**
	 * A constant representing watts.
	 */
	WATT {
		@Override
		public BigDecimal convert(BigDecimal value, PowerUnit type, MathContext context) {
			switch (type) {
			case BRITISH_THERMAL_UNIT_PER_HOUR:
				return new BigDecimal("3.412141633127941920046026").multiply(value, context);
			case CALORIE_PER_HOUR:
				return new BigDecimal("859.8452278589583826311263972485").multiply(value, context);
			case CALORIE_PER_SECOND:
				return new BigDecimal("0.238845896627495939619757").multiply(value, context);
			case FOOT_POUND_FORCE_PER_SECOND:
				return new BigDecimal("0.737562149277265363878087").multiply(value, context);
			case GIGAWATT:
				return new BigDecimal("1E-9").multiply(value, context);
			case HORSEPOWER_MECHANICAL:
				return new BigDecimal("0.001341022088807659927").multiply(value, context);
			case HORSEPOWER_METRIC:
				return new BigDecimal("0.001359621303904323427").multiply(value, context);
			case JOULE_PER_HOUR:
				return new BigDecimal("3600").multiply(value, context);
			case KILOCALORIE_PER_HOUR:
				return new BigDecimal("0.859845227858958382631126").multiply(value, context);
			case KILOCALORIE_PER_SECOND:
				return new BigDecimal("0.0002388458966274959396197573326").multiply(value, context);
			case KILOJOULE_PER_HOUR:
				return new BigDecimal("3.6").multiply(value, context);
			case KILOWATT:
				return new BigDecimal("1E-3").multiply(value, context);
			case MEGAWATT:
				return new BigDecimal("1E-6").multiply(value, context);
			case WATT:
				return value.round(context);
			default:
				break;
			}
			new mathaid.IllegalArgumentException(ExceptionMessage.UNKNOWN_UNIT);
			throw new IllegalArgumentException("Unknown unit");
		}
	},
	/**
	 * A constant representing kilowatts.
	 */
	KILOWATT {
		@Override
		public BigDecimal convert(BigDecimal value, PowerUnit type, MathContext context) {
			switch (type) {
			case WATT:
				return new BigDecimal("1000").multiply(value, context);
			case KILOWATT:
				return value.round(context);
			default:
				return WATT.convert(convert(value, WATT, context), type, context);
			}
		}
	},
	/**
	 * A constant representing megawatts.
	 */
	MEGAWATT {
		@Override
		public BigDecimal convert(BigDecimal value, PowerUnit type, MathContext context) {
			switch (type) {
			case WATT:
				return new BigDecimal("1E6").multiply(value, context);
			case MEGAWATT:
				return value.round(context);
			default:
				return WATT.convert(convert(value, WATT, context), type, context);
			}
		}
	},
	/**
	 * A constant representing gigawatts.
	 */
	GIGAWATT {
		@Override
		public BigDecimal convert(BigDecimal value, PowerUnit type, MathContext context) {
			switch (type) {
			case WATT:
				return new BigDecimal("1E9").multiply(value, context);
			case GIGAWATT:
				return value.round(context);
			default:
				return WATT.convert(convert(value, WATT, context), type, context);
			}
		}
	},
	/**
	 * A constant representing joules-per-hour.
	 */
	JOULE_PER_HOUR {
		@Override
		public BigDecimal convert(BigDecimal value, PowerUnit type, MathContext context) {
			switch (type) {
			case WATT:
				return BigDecimal.ONE.divide(new BigDecimal("3600"), context).multiply(value, context);
			case JOULE_PER_HOUR:
				return value.round(context);
			default:
				return WATT.convert(convert(value, WATT, context), type, context);
			}
		}
	},
	/**
	 * A constant representing kilojoules-per-hour.
	 */
	KILOJOULE_PER_HOUR {
		@Override
		public BigDecimal convert(BigDecimal value, PowerUnit type, MathContext context) {
			switch (type) {
			case WATT:
				return new BigDecimal(5).divide(new BigDecimal("18"), context).multiply(value, context);
			case KILOJOULE_PER_HOUR:
				return value.round(context);
			default:
				return WATT.convert(convert(value, WATT, context), type, context);
			}
		}
	},
	/**
	 * A constant representing calories-per-second.
	 */
	CALORIE_PER_SECOND {
		@Override
		public BigDecimal convert(BigDecimal value, PowerUnit type, MathContext context) {
			switch (type) {
			case WATT:
				return new BigDecimal("4.1868").multiply(value, context);
			case CALORIE_PER_SECOND:
				return value.round(context);
			default:
				return WATT.convert(convert(value, WATT, context), type, context);
			}
		}
	},
	/**
	 * A constant representing calories-per-hour.
	 */
	CALORIE_PER_HOUR {
		@Override
		public BigDecimal convert(BigDecimal value, PowerUnit type, MathContext context) {
			switch (type) {
			case WATT:
				return new BigDecimal("1.163E-3").multiply(value, context);
			case CALORIE_PER_HOUR:
				return value.round(context);
			default:
				return WATT.convert(convert(value, WATT, context), type, context);
			}
		}
	},
	/**
	 * A constant representing kilocalories-per-second.
	 */
	KILOCALORIE_PER_SECOND {
		@Override
		public BigDecimal convert(BigDecimal value, PowerUnit type, MathContext context) {
			switch (type) {
			case WATT:
				return new BigDecimal("4186.8").multiply(value, context);
			case KILOCALORIE_PER_SECOND:
				return value.round(context);
			default:
				return WATT.convert(convert(value, WATT, context), type, context);
			}
		}
	},
	/**
	 * A constant representing kilocalories-per-hour.
	 */
	KILOCALORIE_PER_HOUR {
		@Override
		public BigDecimal convert(BigDecimal value, PowerUnit type, MathContext context) {
			switch (type) {
			case WATT:
				return new BigDecimal("1.163").multiply(value, context);
			case KILOCALORIE_PER_HOUR:
				return value.round(context);
			default:
				return WATT.convert(convert(value, WATT, context), type, context);
			}
		}
	},
	/**
	 * A constant representing mechanical horsepower.
	 */
	HORSEPOWER_MECHANICAL {
		@Override
		public BigDecimal convert(BigDecimal value, PowerUnit type, MathContext context) {
			switch (type) {
			case WATT:
				return new BigDecimal("745.699872").multiply(value, context);
			case HORSEPOWER_MECHANICAL:
				return value.round(context);
			default:
				return WATT.convert(convert(value, WATT, context), type, context);
			}
		}
	},
	/**
	 * A constant representing metric horsepower.
	 */
	HORSEPOWER_METRIC {
		@Override
		public BigDecimal convert(BigDecimal value, PowerUnit type, MathContext context) {
			switch (type) {
			case WATT:
				return new BigDecimal("735.49875").multiply(value, context);
			case HORSEPOWER_METRIC:
				return value.round(context);
			default:
				return WATT.convert(convert(value, WATT, context), type, context);
			}
		}
	},
	/**
	 * A constant representing British thermal units per hour.
	 */
	BRITISH_THERMAL_UNIT_PER_HOUR {
		@Override
		public BigDecimal convert(BigDecimal value, PowerUnit type, MathContext context) {
			switch (type) {
			case WATT:
				return new BigDecimal(
						"0.2930710701722222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222"
								+ "2222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222"
								+ "22222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222"
								+ "222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222"
								+ "22").multiply(value, context);
			case BRITISH_THERMAL_UNIT_PER_HOUR:
				return value.round(context);
			default:
				return WATT.convert(convert(value, WATT, context), type, context);
			}
		}
	},
	/**
	 * A constant representing feet-pound-force-per-second.
	 */
	FOOT_POUND_FORCE_PER_SECOND {
		@Override
		public BigDecimal convert(BigDecimal value, PowerUnit type, MathContext context) {
			switch (type) {
			case WATT:
				return new BigDecimal("1.3558179483314004").multiply(value, context);
			case FOOT_POUND_FORCE_PER_SECOND:
				return value.round(context);
			default:
				return WATT.convert(convert(value, WATT, context), type, context);
			}
		}
	};

	/*
	 * Most Recent Date: 11 Jul 2021-----------------------------------------------
	 * Most recent time created: 19:02:49--------------------------------------
	 */
	/**
	 * Returns the value of the given {@code BigDecimal} converted from {@code this}
	 * to the {@code PowerUnit} argument using the given {@code MathContext} for
	 * numeric approximations.
	 * 
	 * @param value   the value to be converted.
	 * @param type    the destination of the conversion.
	 * @param context the object for approximation.
	 * @return the given value converted from {@code this} to the type argument.
	 */
	@Override
	public abstract BigDecimal convert(BigDecimal value, PowerUnit type, MathContext context);

}
