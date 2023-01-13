
/**
 * 
 */
package mathaid.calculator.base.typeset;
/*
 * A punctuator for the Digit class
 */
public class DigitPunc {

	public DigitPunc() {
		this(",", ".", " ", 3, 3, 3); 
	}

	public DigitPunc(String intSep, String point, String mantSep, int intGroupSize, int mantGroupSize, int numOfRepeats) {
		is = intSep;
		p = point;
		ms = mantSep;
		igs = intGroupSize;
		mgs = mantGroupSize;
		nor = numOfRepeats;
	}

	public String getIntSeparator() {
		return is;
	}

	public String getMantSeparator() {
		return ms;
	}

	public String getPoint() {
		return p;
	}

	public int getIntGroupSize() {
		return igs;
	}

	public int getMantGroupSize() {
		return mgs;
	}

	public int getNumOfRecurringDigits() {
		return nor;
	}

	private final String is;
	private final String ms;
	private final String p;
	private final int igs;
	private final int mgs;
	private final int nor;
}