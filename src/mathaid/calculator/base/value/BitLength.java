/**
 * 
 */
package mathaid.calculator.base.value;

import java.math.BigInteger;

/*
 * Date: 1 Feb 2020<br> Time created: 18:33:03<br> Package:
 * mathaid.calculator.base.value<br> Project: LatestPoject2<br> File:
 * IntValue.java<br> Class name: BitLength
 */
/**
 * An enum used to specify the bit length or word length of an integer. The
 * {@code BitLength} of an integer will determine both it's size and signum.
 * When input value that are of the type IntValue, make sure the {@code String}
 * fits the word length({@code BitLength} or the sign which is the left most bit
 * in the big-endian byte-order will be missing and it will be interpreted as a
 * positive number.
 * <p>
 * This {@code enum} is to be used with the {@code BinaryRep} {@code class}, as
 * such certain constant will only work with their corresponding
 * {@code BinaryRep} constant. For Example: {@code BIT_UNLTD} will only work
 * with {@link BinaryRep#MATH}.
 * </p>
 *
 * @author Oruovo Anthony Etineakpopha
 *
 */
public enum BitLength {
	/**
	 * Constant for 4-bit integers (nibble)
	 */
	BIT_4(4),
	/**
	 * Constant for 8-bit integers (byte)
	 */
	BIT_8(8),
	/**
	 * Constant for 16-bit integers (word)
	 */
	BIT_16(16),
	/*
	 * TODO: Would include 18 and 24 bits soon and also have the precision class
	 * support 4 and 8 bits
	 */
	/**
	 * Constant for 32-bit integers (double word)
	 */
	BIT_32(32),
	/**
	 * Constant for 64-bit integers (quad word)
	 */
	BIT_64(64),
	/**
	 * Constant for 80-bit integers (quad word word)
	 */
	BIT_80(80),
	/**
	 * Constant for 128-bit integers (octuple word)
	 */
	BIT_128(128),
	/**
	 * Constant for 256-bit integers (double octuple word)
	 */
	BIT_256(256),
	/**
	 * Constant for arbitrary length integers. Using this constant in an
	 * {@code IntValue} object will throw a {@code ValueException} unless it is used
	 * with {@link BinaryRep#MATH}.
	 */
	BIT_UNLTD;

	/*
	 * Date: 14 Oct 2022-----------------------------------------------------------
	 * Time created: 20:40:03---------------------------------------------------
	 */
	/**
	 * Enum constructor. Constructs a {@code BitLength} object.
	 */
	BitLength() {
		this(0);
	}

	/*
	 * Date: 14 Oct 2022-----------------------------------------------------------
	 * Time created: 20:39:13---------------------------------------------------
	 */
	/**
	 * Enum constructor. Constructs a {@code BitLength} object.
	 * 
	 * @param length the length of the bits
	 */
	BitLength(int length) {
		this.length = length;
	}

	/*
	 * Date: 1 May 2020---------------------------------------------------- Time
	 * created: 15:43:48--------------------------------------------
	 */
	/**
	 * Gets the length of this {@code BitLength} as an {@code int}
	 * 
	 * @return the length as an {@code int}
	 */
	public int length() {
		return length;
	}

	/*
	 * Date: 3 May 2020-----------------------------------------------------------
	 * Time created: 12:59:01--------------------------------------------
	 */
	/**
	 * Convenience method for retrieving a {@code BitLength}.
	 * 
	 * @param bits the number of bits represented by the {@code BitLength} to be
	 *             returned
	 * @return a {@code BitLength} that represents the argument
	 */
	public static BitLength valueOf(int bits) {
		switch (bits) {
		case 0:
			return BIT_UNLTD;
		case 4:
			return BIT_4;
		case 8:
			return BIT_8;
		case 16:
			return BIT_16;
		case 32:
			return BIT_32;
		case 64:
			return BIT_64;
		case 80:
			return BIT_80;
		case 128:
			return BIT_128;
		case 256:
			return BIT_256;
		default:
			return BIT_32;
		}
	}

	/*
	 * Date: 26 Jul 2020-----------------------------------------------------------
	 * Time created: 18:58:52--------------------------------------------
	 */
	/**
	 * Creates a {@code BitLength} from the argument which may be gotten from
	 * {@link #ordinal()}, which is the index of the enum constant (in the same
	 * order it was declared starting from 0 - (n-1), where <i>n</i> is the number
	 * of constants declared in this enum) returned.
	 * 
	 * @param ordinal the index of the constant as it was declared in this enum to
	 *                be returned. This is the same value that is returned by
	 *                {@link #ordinal()}.
	 * @return the BitLength corresponding to the ordinal argument.
	 */
	public static BitLength fromOrdinal(int ordinal) {
		switch (ordinal) {
		case 0:
			return BIT_4;
		case 1:
			return BIT_8;
		case 2:
			return BIT_16;
		case 3:
			return BIT_32;
		case 4:
			return BIT_64;
		case 5:
			return BIT_80;
		case 6:
			return BIT_128;
		case 7:
			return BIT_256;
		default:
			return BIT_UNLTD;
		}
	}

	/*
	 * Date: 16 Nov 2022-----------------------------------------------------------
	 * Time created: 20:18:06--------------------------------------------
	 */
	/**
	 * Converts the argument to it's <i>small-endian</i> counter part and returns
	 * the result. If the number of bits in the argument is less than 9 then it is
	 * returned unchanged. If the argument is signed (negative) the sign is
	 * preserved.
	 * 
	 * @param n a {@code BigInteger} value.
	 * @return the argument in <i>small-endian</i> form
	 */
	public static BigInteger toSmallEndian(BigInteger n) {
		int signum = n.signum();
		n = n.abs();
		BigInteger val = FloatAid.i(0);
		do {
			BigInteger lowByte = FloatAid.getAllOnes(8).and(n);
			val = val.shiftLeft(8).or(lowByte);
			n = n.shiftRight(8);
		} while (n.signum() != 0);
		return val.multiply(FloatAid.i(signum));
	}

	/*
	 * Date: 16 Nov 2022-----------------------------------------------------------
	 * Time created: 23:08:01--------------------------------------------
	 */
	/**
	 * Converts the argument to it's PDP-11 <i>middle-endian</i> counter part and
	 * returns the result. If the number of bits in the argument is less than 9 then
	 * it is returned unchanged. If the argument is signed (negative) the sign is
	 * preserved.
	 * 
	 * @param n a {@code BigInteger} value.
	 * @return the argument in PDP-11 <i>middle-endian</i> form
	 * @see <a href="https://en.wikipedia.org/wiki/PDP-11">This wiki resource</a>
	 *      for more info about the PDP-11
	 * @see <a href="https://en.wikipedia.org/wiki/Endianness#Byte_addressing">This
	 *      wiki resource</a> for more info about the implementation
	 */
	public static BigInteger toPDP11Endian(BigInteger n) {
		int signum = n.signum();
		n = n.abs();
		BigInteger val = FloatAid.i(0);
		int index = 0;
		do {
			BigInteger lowShort = FloatAid.getAllOnes(16).and(n);
			BigInteger $byte = FloatAid.getAllOnes(8).and(lowShort);
			BigInteger i = $byte.shiftLeft(8).or(lowShort.shiftRight(8));
			val = i.shiftLeft(16 * index).or(val);
			n = n.shiftRight(16);
			index += 1;
		} while (n.signum() != 0);
		return val.multiply(FloatAid.i(signum));
	}

	/**
	 * The actual length represented by this constant
	 */
	private final int length;
}
