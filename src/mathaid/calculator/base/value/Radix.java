/**
 * 
 */
package mathaid.calculator.base.value;

/*
 * Date: 1 Feb 2020<br> Time created: 18:32:01<br> Package:
 * mathaid.calculator.base.value<br> Project: LatestPoject2<br> File:
 * IntValue.java<br> Class name: Radix
 */
/**
 * An enumeration for radixes of integer numbers. Only four radixes are
 * supported, namely: binary, octal, decimal and hexadecimal. It is entirely
 * possible that more are added in the future.<br>
 * These enums work with the class {@code Value} to express their integer
 * representation in multiple bases.
 *
 * @author Oruovo Anthony Etineakpopha
 */
public enum Radix {
	/**
	 * Constant for base two (binary)
	 */
	BIN(2),
	/**
	 * Constant for base eight (octal)
	 */
	OCT(8),
	/**
	 * Constant for base ten (decimal)
	 */
	DEC(10),
	/**
	 * Constant for base sixteen (hexadecimal)
	 */
	HEX(16);

	/**
	 * Represents this {@code Radix} as an {@code int}
	 */
	private int radix;

	/*
	 * Date: 2 May 2020---------------------------------------------------- Time
	 * created: 21:49:39--------------------------------------------
	 */
	/**
	 * Instantiates this {@code radix} using the argument as a field for the type of
	 * {@code Radix}
	 * 
	 * @param radix the radix this {@code enum} constant represents.
	 */
	private Radix(int radix) {
		this.radix = radix;
	}

	/*
	 * Date: 2 May 2020---------------------------------------------------- Time
	 * created: 21:24:59--------------------------------------------
	 */
	/**
	 * Returns the {@code enum} constant of this {@code Radix} as an {@code int}
	 * 
	 * @return this {@code Radix} as an {@code int}
	 */
	public final int getRadix() {
		return radix;
	}

}
