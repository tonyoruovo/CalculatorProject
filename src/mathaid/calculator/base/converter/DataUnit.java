/**
 * 
 */
package mathaid.calculator.base.converter;

import java.math.BigDecimal;
import java.math.MathContext;

import mathaid.ExceptionMessage;

/*
 * Date: 12 Jul 2021----------------------------------------------------------- 
 * Time created: 11:26:34---------------------------------------------------  
 * Package: mathaid.calculator.base.converter------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: DataUnit.java------------------------------------------------------ 
 * Class name: DataUnit------------------------------------------------ 
 */
/**
 * An object containing a set of constants that represents data units of measurements e.g bytes, bits
 * which are used for measuring digital information.
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public enum DataUnit implements Convertible<DataUnit, BigDecimal, MathContext> {
	/**
	 * A constant for bytes.
	 */
	BYTE {
		@Override
		public BigDecimal convert(BigDecimal value, DataUnit type, MathContext context) {
			switch(type) {
			case BIT:
				return new BigDecimal(8).multiply(value, context);
			case BYTE:
				return value.round(context);
			case GIBIBIT:
				return new BigDecimal("7.450580596923828125E-9").multiply(value, context);
			case GIBIBYTE:
				return new BigDecimal("9.31322574615478515625E-10").multiply(value, context);
			case GIGABYTE:
				return new BigDecimal("1E-9").multiply(value, context);
			case KIBIBIT:
				return new BigDecimal("0.0078125").multiply(value, context);
			case KIBIBYTE:
				return new BigDecimal("0.0009765625").multiply(value, context);
			case KILOBYTE:
				return new BigDecimal("0.001").multiply(value, context);
			case MEBIBIT:
				return new BigDecimal("7.62939453125E-6").multiply(value, context);
			case MEBIBYTE:
				return new BigDecimal("9.5367431640625E-7").multiply(value, context);
			case MEGABYTE:
				return new BigDecimal("1E-6").multiply(value, context);
			case PEBIBYTE:
				return new BigDecimal("8.881784197001252323389053E-16").multiply(value, context);
			case PETABYTE:
				return new BigDecimal("1E-15").multiply(value, context);
			case TEBIBYTE:
				return new BigDecimal("9.094947017729282379150391E-13").multiply(value, context);
			case TERABYTE:
				return new BigDecimal("1E-12").multiply(value, context);
			default:
			}
			new mathaid.IllegalArgumentException(ExceptionMessage.UNKNOWN_UNIT);
			throw new IllegalArgumentException("Unknown unit");
		}
	},
	/**
	 * A constant for kibibytes.
	 */
	KIBIBYTE {
		@Override
		public BigDecimal convert(BigDecimal value, DataUnit type, MathContext context) {
			switch (type) {
			case BYTE:
				return new BigDecimal("1024").multiply(value, context);
			case KIBIBYTE:
				return value.round(context);
			default:
				return BYTE.convert(convert(value, BYTE, context), type, context);
			}

		}
	},
	/**
	 * A constant for mebibytes.
	 */
	MEBIBYTE {
		@Override
		public BigDecimal convert(BigDecimal value, DataUnit type, MathContext context) {
			switch (type) {
			case BYTE:
				return new BigDecimal("1048576").multiply(value, context);
			case MEBIBYTE:
				return value.round(context);
			default:
				return BYTE.convert(convert(value, BYTE, context), type, context);
			}
		}
	},
	/**
	 * A constant for gibibytes.
	 */
	GIBIBYTE {
		@Override
		public BigDecimal convert(BigDecimal value, DataUnit type, MathContext context) {
			switch (type) {
			case BYTE:
				return new BigDecimal("1073741824").multiply(value, context);
			case GIBIBYTE:
				return value.round(context);
			default:
				return BYTE.convert(convert(value, BYTE, context), type, context);
			}
		}
	},
	/**
	 * A constant for tebibytes.
	 */
	TEBIBYTE {
		@Override
		public BigDecimal convert(BigDecimal value, DataUnit type, MathContext context) {
			switch (type) {
			case BYTE:
				return new BigDecimal("1099511627776").multiply(value, context);
			case TEBIBYTE:
				return value.round(context);
			default:
				return BYTE.convert(convert(value, BYTE, context), type, context);
			}
		}
	},
	/**
	 * A constant for pebibytes.
	 */
	PEBIBYTE {
		@Override
		public BigDecimal convert(BigDecimal value, DataUnit type, MathContext context) {
			switch (type) {
			case BYTE:
				return new BigDecimal("1125899906842624").multiply(value, context);
			case PEBIBYTE:
				return value.round(context);
			default:
				return BYTE.convert(convert(value, BYTE, context), type, context);
			}
		}
	},
	/**
	 * A constant for kilobytes.
	 */
	KILOBYTE {
		@Override
		public BigDecimal convert(BigDecimal value, DataUnit type, MathContext context) {
			switch (type) {
			case BYTE:
				return new BigDecimal("1000").multiply(value, context);
			case KILOBYTE:
				return value.round(context);
			default:
				return BYTE.convert(convert(value, BYTE, context), type, context);
			}
		}
	},
	/**
	 * A constant for megabytes.
	 */
	MEGABYTE {
		@Override
		public BigDecimal convert(BigDecimal value, DataUnit type, MathContext context) {
			switch (type) {
			case BYTE:
				return new BigDecimal("1E6").multiply(value, context);
			case MEGABYTE:
				return value.round(context);
			default:
				return BYTE.convert(convert(value, BYTE, context), type, context);
			}
		}
	},
	/**
	 * A constant for gigabytes.
	 */
	GIGABYTE {
		@Override
		public BigDecimal convert(BigDecimal value, DataUnit type, MathContext context) {
			switch (type) {
			case BYTE:
				return new BigDecimal("1E9").multiply(value, context);
			case GIGABYTE:
				return value.round(context);
			default:
				return BYTE.convert(convert(value, BYTE, context), type, context);
			}
		}
	},
	/**
	 * A constant for terabytes.
	 */
	TERABYTE {
		@Override
		public BigDecimal convert(BigDecimal value, DataUnit type, MathContext context) {
			switch (type) {
			case BYTE:
				return new BigDecimal("1E12").multiply(value, context);
			case TERABYTE:
				return value.round(context);
			default:
				return BYTE.convert(convert(value, BYTE, context), type, context);
			}
		}
	},
	/**
	 * A constant for petabytes.
	 */
	PETABYTE {
		@Override
		public BigDecimal convert(BigDecimal value, DataUnit type, MathContext context) {
			switch (type) {
			case BYTE:
				return new BigDecimal("1E15").multiply(value, context);
			case PETABYTE:
				return value.round(context);
			default:
				return BYTE.convert(convert(value, BYTE, context), type, context);
			}
		}
	},
	/**
	 * A constant for bits.
	 */
	BIT {
		@Override
		public BigDecimal convert(BigDecimal value, DataUnit type, MathContext context) {
			switch (type) {
			case BYTE:
				return new BigDecimal("0.125").multiply(value, context);
			case BIT:
				return value.round(context);
			default:
				return BYTE.convert(convert(value, BYTE, context), type, context);
			}
		}
	},
	/**
	 * A constant for kibibits.
	 */
	KIBIBIT {
		@Override
		public BigDecimal convert(BigDecimal value, DataUnit type, MathContext context) {
			switch (type) {
			case BYTE:
				return new BigDecimal("128").multiply(value, context);
			case KIBIBIT:
				return value.round(context);
			default:
				return BYTE.convert(convert(value, BYTE, context), type, context);
			}
		}
	},
	/**
	 * A constant for mebibits.
	 */
	MEBIBIT {
		@Override
		public BigDecimal convert(BigDecimal value, DataUnit type, MathContext context) {
			switch (type) {
			case BYTE:
				return new BigDecimal("131072").multiply(value, context);
			case MEBIBIT:
				return value.round(context);
			default:
				return BYTE.convert(convert(value, BYTE, context), type, context);
			}
		}
	},
	/**
	 * A constant for gibibits.
	 */
	GIBIBIT {
		@Override
		public BigDecimal convert(BigDecimal x, DataUnit to, MathContext mc) {
			switch (to) {
			case BYTE:
				return new BigDecimal("134217728").multiply(x, mc);
			case GIBIBIT:
				return x.round(mc);
			default:
				return BYTE.convert(convert(x, BYTE, mc), to, mc);
			}
		}
	};

	/*
	 * Most Recent Date: 12 Aug 2021-----------------------------------------------
	 * Most recent time created: 15:40:44--------------------------------------
	 */
	/**
	 * Returns the value of the given {@code BigDecimal} converted from {@code this}
	 * to the {@code DataUnit} argument using the given {@code MathContext} for
	 * numeric approximations.
	 * 
	 * @param x   the value to be converted.
	 * @param to    the destination of the conversion.
	 * @param mc the object for approximation.
	 * @return the given value converted from {@code this} to the type argument.
	 */
	@Override
	public abstract BigDecimal convert(BigDecimal x, DataUnit to, MathContext mc);
}
