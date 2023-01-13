/**
 * 
 */
package mathaid.calculator.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.Arrays;

import mathaid.calculator.base.util.Utility;
import mathaid.calculator.base.value.Precision;

/*
 * Date: 22 Jun 2020----------------------------------------------------------- 
 * Time created: 19:07:05---------------------------------------------------  
 * Package: calculator------------------------------------------------ 
 * Project: MyTempTest------------------------------------------------ 
 * File: Settings.java------------------------------------------------------ 
 * Class name: Settings------------------------------------------------ 
 */
/**
 * <p>
 * A {@code Settings} object is a singleton object that stores all the user's
 * preference from the {@link #Calculator} in one object. It also partially
 * manages these same preferences such that they are readable and writable (e.g
 * {@link #getScale()} and {@link #setScale(short)}).
 * </p>
 * <p>
 * A {@code Settings} object does not check for irregularities inside any of its
 * methods, it just does simple checks hence it only partially manages the
 * settings of the calculator
 * </p>
 * <p>
 * This object is expected to be instantiated just once per jvm, as only a
 * unique instance exists. Attempts to remotely instantiate this object (e.g via
 * the {@link java.lang.Class#newInstance() newInstance} method of the
 * {@link Class} class) will throw a {@code InstantiationException} if this
 * unique instance already exists.
 * </p>
 * 
 * <p>
 * <strong>Adding a field to the Settings class:</strong> To add a field, simply
 * add the field declaration, it's setter and getter, it's static read and write
 * command (since this is not a {@code java.io.Externalizable} class), then add
 * an initialiser at the constructor using an array parameter for the
 * constructor - to add a {@code java.util.List} object simply add an array of
 * list of the given type; to add an int value (or any value whose array type is
 * already declared as a parameter of the private constructor of this class)
 * simply do nothing - if the parameter's array type does not already exist in
 * the parameter list, add a size check via the method
 * {@link Settings#checkAndThrowException(Object[], int)} (or any of the
 * primitive variant. Note that you can add a check method for a specific type
 * of your choice if it is not already available) before your assignment code
 * (note that any additional check code such as the state of the object, should
 * be put before the assignment statement i.e the assignment statement of the
 * field you are adding to this class), if the parameter array type already
 * exists as a parameter in the constructor of this class, simply manually
 * increment the int argument of the array length check method by one, then add
 * the assignment to array index, then update the
 * {@link Settings#defaultSetting()} method by adding an index (and initialising
 * that index for non-boolean types only since all boolean types have their
 * array filled with the default {@code false}) for the newly added field,
 * remember to increment the array size for already available array types or to
 * create a new array for new types.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public final class Settings {

	/**
	 * Field for this object's serializer
	 */
//	private static final long serialVersionUID = 1L;

	/**
	 * One of the three values used for {@link #setTrig(byte)} and
	 * {@link #getTrig()}, this switches trigonometrical calculation mode to degrees
	 */
	public static final byte TRIG_DEG = 0x0;
	/**
	 * One of the three values used for {@link #setTrig(byte)} and
	 * {@link #getTrig()}, this switches trigonometrical calculation mode to radians
	 */
	public static final byte TRIG_RAD = 0x1;
	/**
	 * One of the three values used for {@link #setTrig(byte)} and
	 * {@link #getTrig()}, this switches trigonometrical calculation mode to
	 * gradians
	 */
	public static final byte TRIG_GRAD = 0x2;

	/**
	 * One of the four values used for {@link #setDecimalMode(byte)} and
	 * {@link #getDecimalMode()}, this switches numerical result display to fixed
	 * digits which does not use scientific notation and exponents
	 */
	public static final byte DEC_FIX = 0x3;
	/**
	 * One of the four values used for {@link #setDecimalMode(byte)} and
	 * {@link #getDecimalMode()}, this switches numerical result display to use
	 * engineering notation
	 */
	public static final byte DEC_ENG = 0x4;
	/**
	 * One of the four values used for {@link #setDecimalMode(byte)} and
	 * {@link #getDecimalMode()}, this switches numerical result display to use
	 * scientific notation
	 */
	public static final byte DEC_SCI = 0x5;
	/**
	 * One of the four values used for {@link #setDecimalMode(byte)} and
	 * {@link #getDecimalMode()}, this switches numerical result display to normal
	 * (whatever that is)
	 */
	public static final byte DEC_OFF = 0x6;

	/**
	 * One of sixteen values used for {@link #setEngineering(byte)} and
	 * {@link #getEngineering()}, this attempts to use the SI engineering prefix
	 * Yotta for numerical results
	 */
	public static final byte ENG_YOTTO = 0x8;
	/**
	 * One of sixteen values used for {@link #setEngineering(byte)} and
	 * {@link #getEngineering()}, this attempts to use the SI engineering prefix
	 * Zetta for numerical results
	 */
	public static final byte ENG_ZETTA = 0x7;
	/**
	 * One of sixteen values used for {@link #setEngineering(byte)} and
	 * {@link #getEngineering()}, this attempts to use the SI engineering prefix Exa
	 * for numerical results
	 */
	public static final byte ENG_EXA = 0x6;
	/**
	 * One of sixteen values used for {@link #setEngineering(byte)} and
	 * {@link #getEngineering()}, this attempts to use the SI engineering prefix
	 * Peta for numerical results
	 */
	public static final byte ENG_PETA = 0x5;
	/**
	 * One of sixteen values used for {@link #setEngineering(byte)} and
	 * {@link #getEngineering()}, this attempts to use the SI engineering prefix
	 * Terra for numerical results
	 */
	public static final byte ENG_TERRA = 0x4;
	/**
	 * One of sixteen values used for {@link #setEngineering(byte)} and
	 * {@link #getEngineering()}, this attempts to use the SI engineering prefix
	 * Giga for numerical results
	 */
	public static final byte ENG_GIGA = 0x3;
	/**
	 * One of sixteen values used for {@link #setEngineering(byte)} and
	 * {@link #getEngineering()}, this attempts to use the SI engineering prefix
	 * Mega for numerical results
	 */
	public static final byte ENG_MEGA = 0x2;
	/**
	 * One of sixteen values used for {@link #setEngineering(byte)} and
	 * {@link #getEngineering()}, this attempts to use the SI engineering prefix
	 * Kilo for numerical results
	 */
	public static final byte ENG_KILO = 0x1;
	/**
	 * One of sixteen values used for {@link #setEngineering(byte)} and
	 * {@link #getEngineering()}, this attempts to use the SI engineering prefix
	 * Milli for numerical results
	 */
	public static final byte ENG_MILLI = (byte) 0xFF;
	/**
	 * One of sixteen values used for {@link #setEngineering(byte)} and
	 * {@link #getEngineering()}, this attempts to use the SI engineering prefix
	 * Micro for numerical results
	 */
	public static final byte ENG_MICRO = (byte) 0xFE;
	/**
	 * One of sixteen values used for {@link #setEngineering(byte)} and
	 * {@link #getEngineering()}, this attempts to use the SI engineering prefix
	 * Nano for numerical results
	 */
	public static final byte ENG_NANO = (byte) 0xFD;
	/**
	 * One of sixteen values used for {@link #setEngineering(byte)} and
	 * {@link #getEngineering()}, this attempts to use the SI engineering prefix
	 * Pico for numerical results
	 */
	public static final byte ENG_PICO = (byte) 0xFC;
	/**
	 * One of sixteen values used for {@link #setEngineering(byte)} and
	 * {@link #getEngineering()}, this attempts to use the SI engineering prefix
	 * Femto for numerical results
	 */
	public static final byte ENG_FEMTO = (byte) 0xFB;
	/**
	 * One of sixteen values used for {@link #setEngineering(byte)} and
	 * {@link #getEngineering()}, this attempts to use the SI engineering prefix
	 * Atto for numerical results
	 */
	public static final byte ENG_ATTO = (byte) 0xFA;
	/**
	 * One of sixteen values used for {@link #setEngineering(byte)} and
	 * {@link #getEngineering()}, this attempts to use the SI engineering prefix
	 * Zepto for numerical results
	 */
	public static final byte ENG_ZEPTO = (byte) 0xF9;
	/**
	 * One of sixteen values used for {@link #setEngineering(byte)} and
	 * {@link #getEngineering()}, this attempts to use the SI engineering prefix
	 * Yocto for numerical results
	 */
	public static final byte ENG_YOCTO = (byte) 0xF8;

	/**
	 * One of three values used for {@link #setFraction(byte)} and
	 * {@link #getFraction()}, this switches common fraction result display to mixed
	 * fraction common fraction.
	 */
	public static final byte FRAC_MIXED = (byte) 0x2;
	/**
	 * One of three values used for {@link #setFraction(byte)} and
	 * {@link #getFraction()}, this switches common fraction result display on.
	 */
	public static final byte FRAC_FRAC = (byte) 0x1;
	/**
	 * One of three values used for {@link #setFraction(byte)} and
	 * {@link #getFraction()}, this switches common fraction result display off.
	 */
	public static final byte FRAC_OFF = (byte) 0x0;

	/**
	 * One of four values used for {@link #setRadix(byte)} and {@link #getRadix()},
	 * this switches the user radix and display radix to binary.
	 */
	public static final byte RADIX_BIN = 0x2;
	/**
	 * One of four values used for {@link #setRadix(byte)} and {@link #getRadix()},
	 * this switches the user radix and display radix to octal.
	 */
	public static final byte RADIX_OCT = 0x8;
	/**
	 * One of four values used for {@link #setRadix(byte)} and {@link #getRadix()},
	 * this switches the user radix and display radix to decimal.
	 */
	public static final byte RADIX_DEC = 0xA;
	/**
	 * One of four values used for {@link #setRadix(byte)} and {@link #getRadix()},
	 * this switches the user radix and display radix to hexadecimal.
	 */
	public static final byte RADIX_HEX = 0x10;

	/**
	 * One of eight values used for {@link #setRep(byte)} and {@link #getRep()},
	 * this switches the user and display numeral representation to signed magnitude
	 * representation (S.M.R).
	 */
	public static final byte REP_SMR = 0x0;
	/**
	 * One of eight values used for {@link #setRep(byte)} and {@link #getRep()},
	 * this switches the user and display numeral representation to one's complement
	 * representation.
	 */
	public static final byte REP_ONEC = 0x1;
	/**
	 * One of eight values used for {@link #setRep(byte)} and {@link #getRep()},
	 * this switches the user and display numeral representation to two's complement
	 * representation.
	 */
	public static final byte REP_TWOC = 0x2;
	/**
	 * One of eight values used for {@link #setRep(byte)} and {@link #getRep()},
	 * this switches the user and display numeral representation to excess n
	 * representation.
	 */
	public static final byte REP_EXCESS_N = 0x3;
	/**
	 * One of eight values used for {@link #setRep(byte)} and {@link #getRep()},
	 * this switches the user and display numeral representation to negabinary.
	 */
	public static final byte REP_NEGABINARY = 0x4;
	/**
	 * One of eight values used for {@link #setRep(byte)} and {@link #getRep()},
	 * this switches the user and display numeral representation to common
	 * mathematical representation.
	 */
	public static final byte REP_MATH = 0x5;
	/**
	 * One of eight values used for {@link #setRep(byte)} and {@link #getRep()},
	 * this switches the user and display numeral representation to unsigned
	 * representation.
	 */
	public static final byte REP_UNSIGNED = 0x6;
	/**
	 * One of eight values used for {@link #setRep(byte)} and {@link #getRep()},
	 * this switches the user and display numeral representation to floating point
	 * representation.
	 */
	public static final byte REP_FLOAT_POINT = 0x7;

	/*
	 * Date: 18 Apr 2021-----------------------------------------------------------
	 * Time created: 19:42:09--------------------------------------------
	 */
	/**
	 * Checks whether the specified boolean array argument's length is equal to the
	 * {@code int} argument. If it isn't, then an {@link IllegalArgumentException}
	 * 
	 * @param array          a boolean array
	 * @param expectedLength the array's expected length
	 */
	private static void checkAndThrowException(boolean[] array, int expectedLength) {
		if (array.length != expectedLength)
			throw new IllegalArgumentException("Incompatible values");
	}

	/*
	 * Date: 18 Apr 2021-----------------------------------------------------------
	 * Time created: 19:45:43--------------------------------------------
	 */
	/**
	 * Checks whether the specified byte array argument's length is equal to the
	 * {@code int} argument. If it isn't, then an {@link IllegalArgumentException}
	 * 
	 * @param array          a byte array
	 * @param expectedLength the array's expected length
	 */
	private static void checkAndThrowException(byte[] array, int expectedLength) {
		if (array.length != expectedLength)
			throw new IllegalArgumentException("Incompatible values");
	}

	/*
	 * Date: 18 Apr 2021-----------------------------------------------------------
	 * Time created: 19:46:00--------------------------------------------
	 */
	/**
	 * Checks whether the specified short array argument's length is equal to the
	 * {@code int} argument. If it isn't, then an {@link IllegalArgumentException}
	 * 
	 * @param array          a short array
	 * @param expectedLength the array's expected length
	 */
	private static void checkAndThrowException(short[] array, int expectedLength) {
		if (array.length != expectedLength)
			throw new IllegalArgumentException("Incompatible values");
	}

	/*
	 * Date: 18 Apr 2021-----------------------------------------------------------
	 * Time created: 19:47:18--------------------------------------------
	 */
	/**
	 * Checks whether the specified char array argument's length is equal to the
	 * {@code int} argument. If it isn't, then an {@link IllegalArgumentException}
	 * 
	 * @param array          a char array
	 * @param expectedLength the array's expected length
	 */
	private static void checkAndThrowException(char[] array, int expectedLength) {
		if (array.length != expectedLength)
			throw new IllegalArgumentException("Incompatible values");
	}

	/*
	 * Date: 18 Apr 2021-----------------------------------------------------------
	 * Time created: 19:47:35--------------------------------------------
	 */
	/**
	 * Checks whether the specified array argument's length is equal to the
	 * {@code int} argument. If it isn't, then an {@link IllegalArgumentException}
	 * 
	 * @param <T>            the object array's type
	 * @param array          an array of type T
	 * @param expectedLength the array's expected length
	 */
	private static <T> void checkAndThrowException(T[] array, int expectedLength) {
		if (array.length != expectedLength)
			throw new IllegalArgumentException("Incompatible values");
	}

	/*
	 * Date: 9 May 2022-----------------------------------------------------------
	 * Time created: 14:59:35--------------------------------------------
	 */
	/**
	 * Custom read commands that enables external custom reading. It specifically
	 * sets all the fields of the given {@code Settings} object stored in the given
	 * input stream.
	 * 
	 * @param s  the settings configuration to be loaded. It's state does not matter
	 *           but must be non-null
	 * @param in the stream to read the object from
	 * @throws IOException            Includes any I/O exceptions that may occur.
	 * @throws ClassNotFoundException if the object fields of this class are
	 *                                incompatible with the provided class. Perhaps
	 *                                an incompatible version?
	 */
	private static void readExternal(Settings s, ObjectInput in) throws IOException, ClassNotFoundException {
		// read boolean fields
		s.setComplex(in.readBoolean());
		s.setExpression(in.readBoolean());
		s.setHyp(in.readBoolean());
		s.setInteger(in.readBoolean());
		s.setMemory(in.readBoolean());
		s.setNormalise(in.readBoolean());
		s.setReciprocal(in.readBoolean());
		s.setShowRecurring(in.readBoolean());
		s.setShift(in.readBoolean());

		// read byte fields
		s.setCurrentConverter(in.readByte());
		s.setDecimalMode(in.readByte());
		s.setDigitsPerUnit(in.readByte());
		s.setFraction(in.readByte());
		s.setEngineering(in.readByte());
		s.setRadix(in.readByte());
		s.setRep(in.readByte());
		s.setTrig(in.readByte());

		// read short fields
		s.setBitLength(in.readShort());
		s.setCurrentConverterFrom(in.readShort());
		s.setCurrentConverterTo(in.readShort());
		s.setExponentLength(in.readShort());
		s.setMantissaLength(in.readShort());
		s.setScale(in.readShort());

		// read character fields
		s.setDecimalPoint(in.readChar());
		s.setFracDivider(in.readChar());
		s.setIntDivider(in.readChar());

		// read String fields
		s.setMultiplicationSign((String) in.readObject());
		s.setDivisionSign((String) in.readObject());

		// read BigDecimal fields
		s.setUpperRandomRange((BigDecimal) in.readObject());
		s.setLowerRandomRange((BigDecimal) in.readObject());
	}

	/*
	 * Date: 18 Apr 2021-----------------------------------------------------------
	 * Time created: 20:35:37--------------------------------------------
	 */
	/**
	 * Sets all the {@code Settings} field to default values and returns a valid
	 * object. If this object is already present in the resident jvm, then that
	 * already existent copy is returned
	 * 
	 * @return a {@code Settings} object with default fields or the already existent
	 *         copy in this jvm
	 */
	public static Settings defaultSetting() {
		final boolean[] b = new boolean[9];
		Arrays.fill(b, false);

		final byte[] scale = new byte[8];
		scale[0] = TRIG_DEG;
		scale[1] = DEC_OFF;
		scale[2] = ENG_KILO;
		scale[3] = FRAC_OFF;
		scale[4] = RADIX_DEC;
		scale[5] = REP_MATH;
		scale[6] = 3;
		scale[7] = 0;

		final short[] scales = new short[6];
		scales[0] = 20;
		scales[1] = 32;
		scales[2] = (short) Precision.SINGLE.getSignificandBits();
		scales[3] = (short) Precision.SINGLE.getExponentBits();
		scales[4] = 0;
		scales[5] = 1;

		final char[] div = new char[3];
		div[0] = ',';
		div[1] = ' ';
		div[2] = '.';

		final BigDecimal[] bg = new BigDecimal[2];
		bg[0] = BigDecimal.ZERO;
		bg[1] = BigDecimal.ZERO;

		final String[] st = new String[2];
		st[0] = "\\cdot";
		st[1] = "\\div";

		return getSetting(b, scale, scales, div, bg, st);
	}

	/*
	 * Date: 18 Apr 2021-----------------------------------------------------------
	 * Time created: 20:40:27---------------------------------------------------
	 */
	/**
	 * Constructs a {@code Settings} object with the specified arguments or throws
	 * an {@link InstantiationException} if this object already exists in the
	 * current jvm. This constructor is expected to be called just once per jvm
	 * because this class contains a unique instance of it is as a field.
	 * 
	 * @param defaultChecks a boolean array that maps to all the boolean fields in
	 *                      this class
	 * @param defaultValues a byte array that maps to all the byte fields in this
	 *                      class
	 * @param defaultScales a short array that maps to all the short fields in this
	 *                      class
	 * @param dividers      a char array that maps to all the char fields in this
	 *                      class
	 * @param randomRanges  a {@link BigDecimal} array that maps to all the
	 *                      {@link BigDecimal} fields in this class
	 * @param delimiters    a {@link String} array that maps to all the
	 *                      {@link String} fields in this class
	 * @throws InstantiationException if this object already exists in the current
	 *                                jvm
	 */
	private Settings(final boolean[] defaultChecks, final byte[] defaultValues, final short[] defaultScales,
			final char[] dividers, final BigDecimal[] randomRanges, final String[] delimiters)
			throws InstantiationException {

		Utility.assertIsNull(settings);

		checkAndThrowException(defaultChecks, 9);

		shift = defaultChecks[0];
		hyp = defaultChecks[1];
		reciprocal = defaultChecks[2];
		expression = defaultChecks[3];
		memory = defaultChecks[4];
		complex = defaultChecks[5];
		integer = defaultChecks[6];
		normalise = defaultChecks[7];
		showRecurring = defaultChecks[8];

		checkAndThrowException(defaultValues, 8);

		trig = defaultValues[0];
		decimalMode = defaultValues[1];
		engineering = defaultValues[2];
		fraction = defaultValues[3];
		radix = defaultValues[4];
		rep = defaultValues[5];
		digitsPerUnit = defaultValues[6];
		currentConverter = defaultValues[7];

		checkAndThrowException(defaultScales, 6);

		scale = defaultScales[0];
		bitLength = defaultScales[1];
		exponentLength = defaultScales[2];
		mantissaLength = defaultScales[3];
		currentConverterTo = defaultScales[4];
		currentConverterFrom = defaultScales[5];

		checkAndThrowException(dividers, 3);

		intDivider = dividers[0];
		fracDivider = dividers[1];
		decimalChar = dividers[2];

		checkAndThrowException(randomRanges, 2);

		if (randomRanges[0].compareTo(randomRanges[1]) > 0)
			throw new IllegalStateException("Illegal random range");

		upperRandomRange = randomRanges[0];
		lowerRandomRange = randomRanges[1];

		checkAndThrowException(delimiters, 2);

		multiplicationChar = delimiters[0];
		divisionChar = delimiters[1];
	}

	/*
	 * Date: 9 May 2022----------------------------------------------------------- 
	 * Time created: 17:30:39--------------------------------------------
	 */
	/**
	 * Saves (or otherwise overwrites) the {@code Settings} object.
	 */
	public void saveOverwrite() {
		File file = new File("res/settings.mathaid");
		ObjectOutput in;
		try {
			in = new ObjectOutputStream(new FileOutputStream(file));
			writeExternal(this, in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Check that returns {@code true} if shift is true. This check represents a
	 * possible second function button in the {@code Calculator} class.
	 * 
	 * @return the shift state which may be either {@code true} or {@code false}
	 */
	public boolean isShift() {
		return shift;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Sets the shift field to the specified argument
	 * 
	 * @param shift a boolean value for the shift field
	 */
	public void setShift(boolean shift) {
		this.shift = shift;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Check that returns {@code true} if hyp is true. This check represents a
	 * possible hyperbolic check for trigonometrical functions in the
	 * {@code Calculator} class.
	 * 
	 * @return the hyp state which may be either {@code true} or {@code false}
	 */
	public boolean isHyp() {
		return hyp;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Sets the hyp field to the specified argument
	 * 
	 * @param hyp a boolean value for the hyp field
	 */
	public void setHyp(boolean hyp) {
		this.hyp = hyp;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Check that returns {@code true} if the reciprocal field is true. This check
	 * represents a possible reciprocal check for trigonometrical functions in the
	 * {@code Calculator} class.
	 * 
	 * @return the reciprocal state which may be either {@code true} or
	 *         {@code false}
	 */
	public boolean isReciprocal() {
		return reciprocal;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Sets the reciprocal field to the specified argument
	 * 
	 * @param reciprocal a boolean value for the reciprocal field
	 */
	public void setReciprocal(boolean reciprocal) {
		this.reciprocal = reciprocal;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Check that returns {@code true} if the expression field is true. This check
	 * represents a possible expression check for non-numerical result types in the
	 * {@code Calculator} class.
	 * 
	 * @return the expression state which may be either {@code true} or
	 *         {@code false}
	 */
	public boolean isExpression() {
		return expression;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Sets the expression field to the specified argument
	 * 
	 * @param expression a boolean value for the expression field
	 */
	public void setExpression(boolean expression) {
		this.expression = expression;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Check that returns {@code true} if the memory field is true. This check
	 * represents a possible memory check for a result collator in the
	 * {@code Calculator} class.
	 * 
	 * @return the memory state which may be either {@code true} (non-zero collator)
	 *         or {@code false} (zero value for the internal result collator)
	 */
	public boolean isMemory() {
		return memory;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Sets the memory field to the specified argument
	 * 
	 * @param memory a boolean value for the memory field
	 */
	public void setMemory(boolean memory) {
		this.memory = memory;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Check that returns {@code true} if the complex field is true. This check
	 * represents a possible complex number check for results and user input (which
	 * is {@code true} for complex numbers and {@code false} otherwise)
	 * {@code Calculator} class.
	 * 
	 * @return the complex state which may be either {@code true} or {@code false}
	 */
	public boolean isComplex() {
		return complex;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Sets the complex field to the specified argument
	 * 
	 * @param complex a boolean value for the complex field
	 */
	public void setComplex(boolean complex) {
		this.complex = complex;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 * TODO: This method is redundant as of right now. It is being replaced with a
	 * function inside the programmer calculator called 'integerToFloat()' where
	 * it's argument is an integer in the same radix as the current radix
	 */
	/**
	 * Check that returns {@code true} if the integer field is true. This check
	 * represents a possible integer number check for results and user input (which
	 * is {@code true} for integer specified user input for floating point numbers
	 * and {@code false} otherwise) in the {@code Calculator} class.
	 * 
	 * @return the integer state which may be either {@code true} or {@code false}
	 */
	public boolean isInteger() {
		return integer;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 * TODO: This method is redundant as of right now. It is being replaced with a
	 * function inside the programmer calculator called 'integerToFloat()' where
	 * it's argument is an integer in the same radix as the current radix
	 */
	/**
	 * Sets the integer field to the specified argument
	 * 
	 * @param integer a boolean value for the integer field
	 */
	public void setInteger(boolean integer) {
		this.integer = integer;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Check that returns {@code true} if the normalise field is true. This check
	 * represents a possible normalise check for results (which is {@code true} for
	 * floating point in IEEE normalised form and {@code false} if otherwise)
	 * {@code Calculator} class.
	 * 
	 * @return the normalise state which may be either {@code true} or {@code false}
	 */
	public boolean isNormalise() {
		return normalise;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Sets the normalise field to the specified argument
	 * 
	 * @param normalise a boolean value for the memory field
	 */
	public void setNormalise(boolean normalise) {
		this.normalise = normalise;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Check that returns {@code true} if the showRecurring field is true. This
	 * check represents a possible show recurring digits check for numerical results
	 * (which is {@code true} highlighting recurring digits in numerical result
	 * display and {@code false} if otherwise) {@code Calculator} class.
	 * 
	 * @return the showRecurring state which may be either {@code true} or
	 *         {@code false}
	 */
	public boolean isShowRecurring() {
		return showRecurring;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Sets the showRecurring field to the specified argument
	 * 
	 * @param showRecurring a boolean value for the showRecurring field
	 */
	public void setShowRecurring(boolean showRecurring) {
		this.showRecurring = showRecurring;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Returns one of three values namely:
	 * <ul>
	 * <li>{@link #TRIG_DEG} for degrees</li>
	 * <li>{@link #TRIG_RAD} for radians</li>
	 * <li>{@link #TRIG_GRAD} for gradians</li>
	 * </ul>
	 * 
	 * @return the trigonometrical value for the trigonometrical field
	 */
	public byte getTrig() {
		return trig;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Sets the trig field to the specified argument
	 * 
	 * @param trig a byte value for the trig field
	 */
	public void setTrig(byte trig) {
		this.trig = trig;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Returns one of four values namely:
	 * <ul>
	 * <li>{@link #DEC_FIX} for fixed digits</li>
	 * <li>{@link #DEC_SCI} for scientific notation</li>
	 * <li>{@link #DEC_ENG} for engineering notation</li>
	 * <li>{@link #DEC_OFF} for default</li>
	 * </ul>
	 * 
	 * @return the decimalMode value for the decimalMode field
	 */
	public byte getDecimalMode() {
		return decimalMode;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Sets the decimalMode field to the specified argument.
	 * <p>
	 * The decimal mode of this calculator is the property that decides which
	 * notation to display decimals in.
	 * </p>
	 * 
	 * @param decimalMode a byte value for the decimalMode field
	 * @see #getDecimalMode()
	 */
	public void setDecimalMode(byte decimalMode) {
		this.decimalMode = decimalMode;
		if (decimalMode != DEC_OFF)
			setFraction(FRAC_OFF);
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Returns one of sixteen values that corresponds to a field in this class. All
	 * values returned are one of the static final fields that begin with the prefix
	 * 'ENG_'
	 * 
	 * @return the engineering value for the engineering field
	 */
	public byte getEngineering() {
		return engineering;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Sets the engineering field to the specified argument
	 * 
	 * @param engineering a byte value for the engineering field
	 */
	public void setEngineering(byte engineering) {
		this.engineering = engineering;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Returns one of three values namely:
	 * <ul>
	 * <li>{@link #FRAC_FRAC} for common fraction</li>
	 * <li>{@link #FRAC_MIXED} for mixed common fraction</li>
	 * <li>{@link #FRAC_OFF} for non-common fraction</li>
	 * </ul>
	 * 
	 * @return the fraction value for the fraction field
	 */
	public byte getFraction() {
		return fraction;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Sets the fraction field to the specified argument
	 * 
	 * @param fraction a byte value for the fraction field
	 */
	public void setFraction(byte fraction) {
		this.fraction = fraction;
		if (fraction != FRAC_OFF)
			setDecimalMode(DEC_OFF);
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Returns one of four values namely:
	 * <ul>
	 * <li>{@link #RADIX_BIN} for binary</li>
	 * <li>{@link #RADIX_OCT} for base eight</li>
	 * <li>{@link #RADIX_DEC} for base ten</li>
	 * <li>{@link #RADIX_HEX} for base sixteen</li>
	 * </ul>
	 * 
	 * @return the radix value for the radix field
	 */
	public byte getRadix() {
		return radix;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Sets the radix field to the specified argument
	 * 
	 * @param radix a byte value for the radix field
	 */
	public void setRadix(byte radix) {
		this.radix = radix;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Returns one of eight values that corresponds to a field in this class. All
	 * values returned are one of the static final fields that begin with the prefix
	 * 'REP_'
	 * 
	 * @return the rep value for the rep field
	 */
	public byte getRep() {
		return rep;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Sets the rep field to the specified argument
	 * 
	 * @param rep a byte value for the rep field
	 */
	public void setRep(byte rep) {
		this.rep = rep;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Returns the number of digits-per-unit for number formatting, which specifies
	 * the number of digits required for a separator to be attached during the
	 * formatting process. For example, the number 1234567890 when formatted
	 * produces 1,234,567,890; thus such formatting has a {@code digitsPerUnit} of
	 * 3.
	 * 
	 * @return the digitsPerUnit value for the digitsPerUnit field
	 */
	public byte getDigitsPerUnit() {
		return digitsPerUnit;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Sets the digitsPerUnit field to the specified argument
	 * 
	 * @param digitsPerUnit a byte value for the digitsPerUnit field
	 */
	public void setDigitsPerUnit(byte digitsPerUnit) {
		this.digitsPerUnit = digitsPerUnit;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Returns the number of significant digits the calculator should work with.
	 * 
	 * @return the scale value for the scale field
	 */
	public short getScale() {
		return scale;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Sets the scale field to the specified argument
	 * 
	 * @param scale a short value for the scale field
	 */
	public void setScale(short scale) {
		this.scale = scale;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Returns the bitLength field value which the calculator should work with. This
	 * vaue is the combination of the mantissaLength value and the exponentLength
	 * value
	 * 
	 * @return the bitLength value for the bitLength field
	 */
	public short getBitLength() {
		return bitLength;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Sets the bitLength field to the specified argument
	 * 
	 * @param bitLength a short value for the bitLength field
	 */
	public void setBitLength(short bitLength) {
		this.bitLength = bitLength;
		if (getRep() == REP_FLOAT_POINT && this.bitLength < 8)
			this.bitLength = 8;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Returns the exponentLength field value which the calculator should work with.
	 * 
	 * @return the exponentLength value for the exponentLength field
	 */
	public short getExponentLength() {
		return exponentLength;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Sets the exponentLength field to the specified argument
	 * 
	 * @param exponentLength a short value for the exponentLength field
	 */
	public void setExponentLength(short exponentLength) {
		this.exponentLength = exponentLength;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Returns the mantissaLength field value which the calculator should work with.
	 * 
	 * @return the mantissaLength value for the mantissaLength field
	 */
	public short getMantissaLength() {
		return mantissaLength;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Sets the mantissaLength field to the specified argument
	 * 
	 * @param mantissaLength a short value for the mantissaLength field
	 */
	public void setMantissaLength(short mantissaLength) {
		this.mantissaLength = mantissaLength;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Returns the intDivider field value which the calculator should work with. The
	 * intDivider field is the char used as a delimiter in the integer part of
	 * formatted numerical values
	 * 
	 * @return the intDivider value for the intDivider field
	 */
	public char getIntDivider() {
		return intDivider;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Sets the intDivider field to the specified argument
	 * 
	 * @param intDivider a char value for the intDivider field
	 */
	public void setIntDivider(char intDivider) {
		this.intDivider = intDivider == ' ' ? '~' : intDivider;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Returns the fracDivider field value which the calculator should work with.
	 * The fracDivider field is the char used as a delimiter in the fractional part
	 * of formatted numerical values
	 * 
	 * @return the fracDivider value for the fracDivider field
	 */
	public char getFracDivider() {
		return fracDivider;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Sets the fracDivider field to the specified argument
	 * 
	 * @param fracDivider a char value for the fracDivider field
	 */
	public void setFracDivider(char fracDivider) {
		this.fracDivider = (fracDivider == ' ' ? '~' : fracDivider);
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Returns a {@code BigDecimal} value of the upperRandomRange field of this
	 * class. This is the maximum random value that the random number generator of
	 * this calculator is expected to generate
	 * 
	 * @return the upperRandomRange value of the upperRandomRange field of this
	 *         class
	 */
	public BigDecimal getUpperRandomRange() {
		return upperRandomRange;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Sets the upperRandomRange field to the specified argument
	 * 
	 * @param upperRandomRange a {@code BigDecimal} value for the upperRandomRange
	 *                         field
	 */
	public void setUpperRandomRange(BigDecimal upperRandomRange) {
		this.upperRandomRange = upperRandomRange;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Returns a {@code BigDecimal} value of the upperRandomRange field of this
	 * class. This is the minimum random value that the random number generator of
	 * this calculator is expected to generate
	 * 
	 * @return the lowerRandomRange value of the lowerRandomRange field of this
	 *         class
	 */
	public BigDecimal getLowerRandomRange() {
		return lowerRandomRange;
	}

	/*
	 * Date: 1 Apr 2021-----------------------------------------------------------
	 * Time created: 20:11:31---------------------------------------------------
	 */
	/**
	 * Sets the lowerRandomRange field to the specified argument
	 * 
	 * @param lowerRandomRange a {@code BigDecimal} value for the lowerRandomRange
	 *                         field
	 */
	public void setLowerRandomRange(BigDecimal lowerRandomRange) {
		this.lowerRandomRange = lowerRandomRange;
	}

	/*
	 * Date: 14 Apr 2021-----------------------------------------------------------
	 * Time created: 19:20:15--------------------------------------------
	 */
	/**
	 * Returns the decimalPoint field value which the calculator should work with.
	 * The decimalPoint field is the char used as a delimiter in numerical values
	 * between the integer and the fractional part.
	 * 
	 * @return the decimalPoint value for the decimalPoint field
	 */
	public char getDecimalPoint() {
		return decimalChar;
	}

	/*
	 * Date: 14 Apr 2021-----------------------------------------------------------
	 * Time created: 19:20:31--------------------------------------------
	 */
	/**
	 * Sets the decimalPoint field to the specified argument
	 * 
	 * @param decimalPoint a char value for the decimalPoint field
	 */
	public void setDecimalPoint(char decimalChar) {

		this.decimalChar = decimalChar == ' ' ? '~' : decimalChar;
	}

	/*
	 * Date: 14 Apr 2021-----------------------------------------------------------
	 * Time created: 19:19:26---------------------------------------------------
	 */
	/**
	 * Returns the multiplicationSign field value which the calculator should work
	 * with. The multiplicationSign field is the string used to represent
	 * multiplication within formulae in the calculator.
	 * 
	 * @return the multiplicationSign value for the multiplicationSign field
	 */
	public String getMultiplicationSign() {
		return multiplicationChar;
	}

	/*
	 * Date: 14 Apr 2021-----------------------------------------------------------
	 * Time created: 19:19:26---------------------------------------------------
	 */
	/**
	 * Sets the multiplicationSign field to the specified argument
	 * 
	 * @param multiplicationSign a string value for the multiplicationSign field
	 */
	public void setMultiplicationSign(String multiplicationChar) {
		this.multiplicationChar = multiplicationChar;
	}

	/*
	 * Date: 14 Apr 2021-----------------------------------------------------------
	 * Time created: 19:19:26---------------------------------------------------
	 */
	/**
	 * Returns the divisionSign field value which the calculator should work with.
	 * The divisionSign field is the string used to represent division within
	 * formulae in the calculator.
	 * 
	 * @return the divisionSign value for the divisionSign field
	 */
	public String getDivisionSign() {
		return divisionChar;
	}

	/*
	 * Date: 14 Apr 2021-----------------------------------------------------------
	 * Time created: 19:19:26---------------------------------------------------
	 */
	/**
	 * Sets the divisionSign field to the specified argument
	 * 
	 * @param divisionSign a string value for the divisionSign field
	 */
	public void setDivisionSign(String divisionChar) {
		this.divisionChar = divisionChar;
	}

	/*
	 * Date: 17 Jul 2021-----------------------------------------------------------
	 * Time created: 11:14:42--------------------------------------------
	 */
	/**
	 * Returns the current converter index (which specifies the converter that the
	 * converter calculator should use). For example, when this value is returns
	 * <code>0</code>, the converter calculator will only use {@code AngleUnit} for
	 * conversions.
	 * 
	 * @return a {@code byte} value which specifies the current converter index for
	 *         the converter calculator
	 * @see #setCurrentConverter(byte)
	 */
	public byte getCurrentConverter() {
		return currentConverter;
	}

	/*
	 * Date: 17 Jul 2021-----------------------------------------------------------
	 * Time created: 11:14:48--------------------------------------------
	 */
	/**
	 * Sets the current converter index (which specifies the converter that the
	 * converter calculator should use). For example, when this value is set to
	 * <code>1</code>, the converter calculator will only use {@code AreaUnit} for
	 * conversions. The following is a list of valid arguments and their value
	 * representation in the order that they can be used:
	 * <dl>
	 * <dt>0</dt>
	 * <dd>Angle units</dd>
	 * <dt>1</dt>
	 * <dd>Area units</dd>
	 * <dt>2</dt>
	 * <dd>Currency units</dd>
	 * <dt>3</dt>
	 * <dd>Distance units</dd>
	 * <dt>4</dt>
	 * <dd>Data units</dd>
	 * <dt>5</dt>
	 * <dd>Energy units</dd>
	 * <dt>6</dt>
	 * <dd>Force units</dd>
	 * <dt>7</dt>
	 * <dd>Frequency units</dd>
	 * <dt>8</dt>
	 * <dd>Fuel consumption units</dd>
	 * <dt>9</dt>
	 * <dd>Mass units</dd>
	 * <dt>10</dt>
	 * <dd>Power units</dd>
	 * <dt>11</dt>
	 * <dd>Pressure units</dd>
	 * <dt>12</dt>
	 * <dd>Speed units</dd>
	 * <dt>13</dt>
	 * <dd>Temperature units</dd>
	 * <dt>14</dt>
	 * <dd>Time units</dd>
	 * <dt>15</dt>
	 * <dd>Torque units</dd>
	 * <dt>16</dt>
	 * <dd>Volume units</dd>
	 * </dl>
	 * <p>
	 * Note that this also automatically sets the {@link #getCurrentConverterFrom()}
	 * and the {@link #getCurrentConverterTo()} to prevent a
	 * {@code NullPointerException} because not all converters have the number of
	 * elements to covert. For example {@code AreaUnit} has less elements (units)
	 * than {@code CurrencyUnit} and switching from {@code CurrencyUnit} at index
	 * <code>80</code> to {@code AreaUnit} may likely throw an exception because
	 * there is no element (unit) at index 80 within {@code AreaUnit}.
	 * </p>
	 * 
	 * @param currentConverter an {@code byte} value which specifies the index from
	 *                         which the converter calculator should select the
	 *                         converter to use in an internal list of converters
	 */
	public void setCurrentConverter(byte currentConverter) {
		this.currentConverter = currentConverter;
		this.currentConverterFrom = 0;
		this.currentConverterTo = 1;
	}

	/*
	 * Date: 17 Jul 2021-----------------------------------------------------------
	 * Time created: 11:14:25---------------------------------------------------
	 */
	/**
	 * Returns the current index to which conversions are to be made. When a
	 * converter has been specified using {@link #setCurrentConverter(byte)}, a user
	 * may also specify the element within that converter that conversions should be
	 * made to. For example:
	 * 
	 * <pre>
	 * Settings setting = ...
	 * byte angleUnit = 0;
	 * setting.setCurrentConverter(angleUnit); // sets the converter to angles
	 * short rad = 1;
	 * // sets the unit from which the angle will be converted to radians
	 * setting.setCurentConverterFrom(rad);
	 * short grad = 2;
	 * // sets the unit to which the angle will be converted to gradian
	 * setting.setCurrentConverterTo(grad);
	 * </pre>
	 * 
	 * @return a {@code short} value which specifies index within the converter to
	 *         which conversion are to be made.
	 */
	public short getCurrentConverterTo() {
		return currentConverterTo;
	}

	/*
	 * Date: 17 Jul 2021-----------------------------------------------------------
	 * Time created: 11:14:25---------------------------------------------------
	 */
	/**
	 * Sets the current converter that conversions are been made to to the specified
	 * index. The currentConvertTo index is the index that the converter object
	 * occupies at the converter API.
	 * 
	 * @param currentConverterTo a {@code short} value which specifies the
	 *                           currentConverterTo index to be set
	 * @see #getCurrentConverterTo()
	 */
	public void setCurrentConverterTo(short currentConverterTo) {
		this.currentConverterTo = currentConverterTo;
		if (this.currentConverterTo == this.currentConverterFrom)
			this.currentConverterFrom = (short) (this.currentConverterTo == 0 ? 1 : 0);
	}

	/*
	 * Date: 17 Jul 2021-----------------------------------------------------------
	 * Time created: 11:14:25---------------------------------------------------
	 */
	/**
	 * Returns the current index from which conversions are to be made. When a
	 * converter has been specified using {@link #setCurrentConverter(byte)}, a user
	 * may also specify the element within that converter that conversions should be
	 * made from. For example:
	 * 
	 * <pre>
	 * Settings setting = ...
	 * byte angleUnit = 0;
	 * setting.setCurrentConverter(angleUnit); // sets the converter to angles
	 * short rad = 1;
	 * // sets the unit from which the angle will be converted to radians
	 * setting.setCurentConverterFrom(rad);
	 * short grad = 2;
	 * // sets the unit to which the angle will be converted to gradian
	 * setting.setCurrentConverterTo(grad);
	 * </pre>
	 * 
	 * @return a {@code short} value which specifies index within the converter from
	 *         which conversion are to be made.
	 */
	public short getCurrentConverterFrom() {
		return currentConverterFrom;
	}

	/*
	 * Date: 17 Jul 2021-----------------------------------------------------------
	 * Time created: 11:14:25---------------------------------------------------
	 */
	/**
	 * Sets the current converter that conversions are been made to to the specified
	 * index. The currentConvertFrom index is the index that the converter object
	 * occupies at the converter API.
	 * 
	 * @param currentConverterFrom a {@code short} value which specifies the
	 *                             currentConverterFrom index to be set
	 * @see #getCurrentConverterFrom()
	 */
	public void setCurrentConverterFrom(short currentConverterFrom) {
		this.currentConverterFrom = currentConverterFrom;
		if (this.currentConverterFrom == this.currentConverterTo)
			this.currentConverterTo = (short) (this.currentConverterFrom == 0 ? 1 : 0);
	}

	/*
	 * Date: 18 Apr 2021-----------------------------------------------------------
	 * Time created: 22:19:16--------------------------------------------
	 */
	/**
	 * Sets all the {@code Settings} field to specified values and returns a valid
	 * object. If this object already exists as a saved file in res/settings.fields
	 * and isn't tampered with, then that object is loaded through a specialised
	 * input stream else if this object already exists in the current jvm, then that
	 * already existent copy is returned.
	 * 
	 * @return a {@code Settings} object with default fields or the already existent
	 *         copy in this jvm
	 * 
	 * @param defaultChecks a boolean array that maps to all the boolean fields in
	 *                      this class
	 * @param defaultValues a byte array that maps to all the byte fields in this
	 *                      class
	 * @param defaultScales a short array that maps to all the short fields in this
	 *                      class
	 * @param dividers      a char array that maps to all the char fields in this
	 *                      class
	 * @param randomRanges  a {@link BigDecimal} array that maps to all the
	 *                      {@link BigDecimal} fields in this class
	 * @param delimiters    a {@link String} array that maps to all the
	 *                      {@link String} fields in this class ignoring all
	 *                      parameters
	 */
	static Settings getSetting(final boolean[] defaultChecks, final byte[] defaultValues, final short[] defaultScales,
			final char[] dividers, final BigDecimal[] randomRanges, final String[] delimiters) {
		if(settings == null)
			synchronized (Settings.class) {
				if (settings == null)
					try {
						settings = new Settings(defaultChecks, defaultValues, defaultScales, dividers, randomRanges,
								delimiters);
						File file = new File("res/settings.mathaid");
						if (file.exists()) {
							ObjectInput in;
							in = new ObjectInputStream(new FileInputStream(file));
							readExternal(settings, in);
						}
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
			}
		return settings;
	}

	/*
	 * Date: 9 May 2022-----------------------------------------------------------
	 * Time created: 14:36:31--------------------------------------------
	 */
	/**
	 * Custom write commands that enables external custom writing. It specifically
	 * gets all the fields of the given {@code Settings} object and writes them to
	 * the given output stream.
	 * 
	 * @param s   the settings configuration to be saved
	 * @param out the stream to write the object to
	 * @throws IOException Includes any I/O exceptions that may occur.
	 */
	private static void writeExternal(Settings s, ObjectOutput out) throws IOException {

		out.writeBoolean(s.complex);
		out.writeBoolean(s.expression);
		out.writeBoolean(s.hyp);
		out.writeBoolean(s.integer);
		out.writeBoolean(s.memory);
		out.writeBoolean(s.normalise);
		out.writeBoolean(s.reciprocal);
		out.writeBoolean(s.showRecurring);
		out.writeBoolean(s.shift);

		// write byte fields
		out.writeByte(s.currentConverter);
		out.writeByte(s.decimalMode);
		out.writeByte(s.digitsPerUnit);
		out.writeByte(s.fraction);
		out.writeByte(s.engineering);
		out.writeByte(s.radix);
		out.writeByte(s.rep);
		out.writeByte(s.trig);

		// write short fields
		out.writeShort(s.bitLength);
		out.writeShort(s.currentConverterFrom);
		out.writeShort(s.currentConverterTo);
		out.writeShort(s.exponentLength);
		out.writeShort(s.mantissaLength);
		out.writeShort(s.scale);

		// write character fields
		out.writeChar(s.decimalChar);
		out.writeChar(s.fracDivider);
		out.writeChar(s.intDivider);

		// write String fields
		out.writeObject(s.multiplicationChar);
		out.writeObject(s.divisionChar);

		// write BigDecimal fields
		out.writeObject(s.upperRandomRange);
		out.writeObject(s.lowerRandomRange);
		
		out.flush();
	}

	/*
	 * Most Recent Date: 18 Apr 2021-----------------------------------------------
	 * Most recent time created: 22:44:29--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return 0b11111111111111111111111111111111;
//		return Objects.hash(shift, hyp, reciprocal, expression, memory, complex, integer, normalise, showRecurring,
//				trig, decimalMode, engineering, fraction, radix, digitsPerUnit, currentConverter,
//				scale, bitLength, exponentLength, mantissaLength, currentConverterFrom, currentConverterTo,
//				intDivider, fracDivider, decimalChar,
//				upperRandomRange, lowerRandomRange,
//				multiplicationChar, divisionChar);
	}

	/**
	 * Controls the second function of the calculator
	 */
	private boolean shift;
	/**
	 * check for hyperbolic trigonometry such as tanh, cosh etc.
	 */
	private boolean hyp;
	/**
	 * check for reciprocal trigonometry such as csc, sec etc.
	 */
	private boolean reciprocal;
	/**
	 * check for expression values i.e non-numerical values
	 */
	private boolean expression;
	/**
	 * check for non-zero memory values
	 */
	private boolean memory;
	/**
	 * check for the calculator to allow and work with complex numbers
	 */
	private boolean complex;
	/**
	 * check for the calculator to allow floating point numbers in integer format as
	 * specified by {@link Double#doubleToLongBits(double)} and
	 * {@link Float#floatToIntBits(float)}.
	 */
	/*
	 * TODO: This field is redundant as of right now. It is being replaced with a
	 * function inside the programmer calculator called 'integerToFloat()' where
	 * it's argument is an integer in the same radix as the current radix
	 */
	private boolean integer;
	/**
	 * check for the calculator to allow normalised floating point numbers
	 */
	private boolean normalise;
	/**
	 * check for the calculator to allow display of recurring numbers
	 */
	private boolean showRecurring;

	/**
	 * field for trigonometry and related operations
	 */
	private byte trig;
	/**
	 * field for decimal result format such as engineering, scientific and fixed.
	 */
	private byte decimalMode;
	/**
	 * field for various SI units relating to engineering operations
	 */
	private byte engineering;
	/**
	 * Field for fractional display
	 */
	private byte fraction;
	/**
	 * Field for user specified numerical radix display
	 */
	private byte radix;
	/**
	 * Field for the current binary representation
	 */
	private byte rep;
	/**
	 * Field for the number of digits per separator in a decimal numeral.
	 * 
	 * @see #setDigitsPerUnit(byte)
	 */
	private byte digitsPerUnit;

	/**
	 * Value for the max number of significant digits in a numerical value to be
	 * displayed
	 */
	private short scale;
	/**
	 * the total bit length of binary and other related computing numbers.
	 * 
	 * @apiNote For floating point numbers, this number is the sum of
	 *          {@link #exponentLength} and {@link #mantissaLength} + 1
	 */
	private short bitLength;
	/**
	 * the number of bits in the exponent part of IEEE binary floating point number
	 */
	private short exponentLength;
	/**
	 * the number of bits in the mantissa part of IEEE binary floating point number
	 */
	private short mantissaLength;

	/**
	 * The char value used as a delimiter for the integer part of a formatted
	 * numerical value
	 */
	private char intDivider;
	/**
	 * The char value used as a delimiter for the fractional part of a formatted
	 * numerical value
	 */
	private char fracDivider;
	/**
	 * The char value used as a delimiter for the index that separates the integer
	 * part from the fractional part
	 */
	private char decimalChar;

	/**
	 * The max random value expected to be generated
	 */
	private BigDecimal upperRandomRange;
	/**
	 * The min random value expected to be generated
	 */
	private BigDecimal lowerRandomRange;

	/**
	 * The String value used as the calculator's multiplication sign
	 */
	private String multiplicationChar;
	/**
	 * The String value used as the calculator's division sign
	 */
	private String divisionChar;

	/**
	 * The current converter index within the list of converters. I.e the current
	 * converter being used for conversions in the converter calculator. As there
	 * are many converters, a list of converters is maintained by the converter
	 * calculator and the current index of that list is gotten from this field
	 */
	private byte currentConverter;
	/**
	 * The current index of the convert to which conversions are to be made. For
	 * example, if the current index of the {@code Converter} is {@code AngleUnit}
	 * then this field specifies which {@code AngleUnit} the angle conversion is to
	 * be made to (from {@code AngleUnit.DEG} to {@code AngleUnit.RAD} for instance
	 * in this case, {@code AngleUnit.RAD} is indicated by this field).
	 */
	private short currentConverterTo;
	/**
	 * The current index of the convert from which conversions are to be made. For
	 * example, if the current index of the {@code Converter} is {@code AngleUnit}
	 * then this field specifies which {@code AngleUnit} the angle conversion is to
	 * be made from (from {@code AngleUnit.DEG} to {@code AngleUnit.RAD} for
	 * instance in this case, {@code AngleUnit.DEG} is indicated by this field).
	 */
	private short currentConverterFrom;

	/**
	 * A unique instance of this class. No other instance may coexist with this one
	 * in the same jvm.
	 */
	private static Settings settings;
}
