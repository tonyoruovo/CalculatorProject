
/**
 * 
 */
package mathaid.calculator.base.typeset;

import static mathaid.calculator.base.typeset.Segment.Type.*;

import java.io.IOException;
import java.util.List;

import mathaid.calculator.FatalReadException;

public class Digit extends AbstractSegment {

	protected Digit(String d, int t, boolean f, boolean e, DigitPunc dp, LinkedSegment s) {
		super(t, f, e, s, new LinkedSegment[] {}, -1, -1);
		this.digit = d;
		this.dp = dp;
		this.repeatend = new StringBuilder();
		this.isFirstIndex = true;
	}

	public Digit(String n, int t, DigitPunc dp) {
		this(n, t, false, false, dp, null);
	}

	public Digit(char d, int t, DigitPunc dp) {
		this(String.valueOf(d), t, false, false, dp, null);
	}

	public Digit(String n, DigitPunc dp) {
		this(n, INTEGER, dp);
	}

	public Digit(char d, DigitPunc dp) {
		this(d, INTEGER, dp);
	}

	public Digit(String n) {
		this(n, new DigitPunc());
	}

	public Digit(char d) {
		this(d, new DigitPunc());
	}

	public Digit() {
		this('0');
	}

	private int getRemainingDigits(int start) {
		if (hasSibling())
			if (getSibling() instanceof Digit)
				return ((Digit) getSibling()).getRemainingDigits(start + 1);
		return start;
	}

	protected int getRemainingDigits() {
		return getRemainingDigits(0);
	}

	public String getDigit() {
		return digit;
	}

	private void clearMutables() {
		repeatend.delete(0, repeatend.length());
		isFirstIndex = true;
		recurCount = 0;
		unitCount = 0;
	}

	protected Digit toDigit() {
		clearMutables();
		return new Digit(digit, INTEGER, isFocused(), hasError(), dp,
				isDigit(getSibling()) ? ((Digit) getSibling()).toDigit() : getSibling());
	}

	protected Digit toMantissaDigit() {
		clearMutables();
		return new Digit(digit, MANTISSA, isFocused(), hasError(), dp,
				isDigit(getSibling()) ? ((Digit) getSibling()).toMantissaDigit() : getSibling());
	}

	protected Digit toRecurringDigit(int type) {
		clearMutables();
		return new Digit(digit, type, isFocused(), hasError(), dp,
				isDigit(getSibling()) ? ((Digit) getSibling()).toRecurringDigit(type) : getSibling());
	}

	@Override
	public LinkedSegment concat(LinkedSegment s) {
		return new Digit(digit, getType(), isFocused(), hasError(), dp, hasSibling() ? getSibling().concat(s) : s);
	}

	@Override
	public LinkedSegment setFocus(int i, boolean f) {
		if (i == 0)
			return new Digit(digit, getType(), f, hasError(), dp, getSibling());
		else if (i > 0 && hasSibling())
			return new Digit(digit, getType(), isFocused(), hasError(), dp, getSibling().setFocus(i - 1, f));
		throw new IndexOutOfBoundsException(i);
	}

	@Override
	public LinkedSegment setError(int i, boolean e) {
		if (i == 0)
			return new Digit(digit, getType(), isFocused(), e, dp, getSibling());
		else if (i > 0 && hasSibling())
			return new Digit(digit, getType(), isFocused(), hasError(), dp, getSibling().setError(i - 1, e));
		throw new IndexOutOfBoundsException(i);
	}

	@Override
	public LinkedSegment setSibling(int i, LinkedSegment s) {
		if (i == 0) {
			switch (getType()) {
			case INTEGER:// this is an integer digit
				switch (s.getType()) {// the type of next segment
				case MANTISSA:
				case ARC:
				case DOT:
				case DOT_ALL:
				case DOT_BAR:
				case ELLIPSIS:
				case PARENTHESISED:
				case VINCULUM:
					return new Digit(digit, getType(), isFocused(), hasError(), dp, ((Digit) s).toDigit());
				case INTEGER:
				default:
					break;
				}
			case MANTISSA:// this is a mantissa digit
				switch (s.getType()) {// the type of next segment
				case INTEGER:
				case ARC:
				case DOT:
				case DOT_ALL:
				case DOT_BAR:
				case ELLIPSIS:
				case PARENTHESISED:
				case VINCULUM:
					return new Digit(digit, getType(), isFocused(), hasError(), dp, ((Digit) s).toDigit());
				case MANTISSA:// the next is also a mantissa
				default:
					break;
				}
			case ARC:// this is an arc recurring digit
			case DOT:// this is a dot first and last only recurring digit
			case DOT_ALL:// this is a dot all recurring digit
			case DOT_BAR:// this a dot one or vinculum recurring digit
			case ELLIPSIS:// this is an ellipsis recurring digit
			case PARENTHESISED:// this is an enclose recurring digit (parenthesis)
			case VINCULUM:// this is a vinculum recurring digit
				if (s instanceof Digit && !isRecurring(s))
					return new Digit(digit, getType(), isFocused(), hasError(), dp,
							((Digit) s).toRecurringDigit(getType()));
			default: // assume it is non-digit
			}
			return new Digit(digit, getType(), isFocused(), hasError(), dp, s);
		} else if (i > 0 && hasSibling()) {
			return new Digit(digit, getType(), isFocused(), hasError(), dp, getSibling().setSibling(i - 1, s));
		}
		throw new IndexOutOfBoundsException(i);
	}

	private void formatDotAll(Appendable a, Formatter f, List<Integer> p) {
		try {
			a.append(f.format(this, String.format("\\dot{%s}", digit), getType(), p));
		} catch (@SuppressWarnings("unused") IOException e) {
		}
		unitCount += 1;
		recurCount += 1;
		if (hasSibling()) {
			if (hasValidSibling(this, getType())) {
				Digit n = (Digit) getSibling();
				n.unitCount = unitCount;
				n.recurCount = recurCount;
				if (unitCount % dp.getMantGroupSize() == 0)
					try {
						a.append(f.format(null, dp.getMantSeparator(), SEPARATOR, null));
					} catch (@SuppressWarnings("unused") IOException e) {
					}
			}
			getSibling().format(a, f, p);
		}
	}

	private void formatVinc(Appendable a, Formatter f, List<Integer> p) {
		try {
			if (recurCount == 0)
				a.append("\\overline{");
			a.append(f.format(this, digit, getType(), p));
			unitCount += 1;
			recurCount += 1;
			if (!hasValidSibling(this, getType()))
				a.append('}');
		} catch (IOException e) {
		}

		if (hasSibling()) {
			if (hasValidSibling(this, getType())) {
				Digit n = (Digit) getSibling();
				n.unitCount = unitCount;
				n.recurCount = recurCount;
				if (unitCount % dp.getMantGroupSize() == 0)
					try {
						a.append(f.format(null, dp.getMantSeparator(), SEPARATOR, p));
					} catch (@SuppressWarnings("unused") IOException e) {
					}
			}
			getSibling().format(a, f, p);
		}
	}

	private void formatParen(Appendable a, Formatter f, List<Integer> p) {
		try {
			if (recurCount == 0)
				a.append(f.format(null, "(", DELIMITER, p));
			a.append(f.format(this, digit, getType(), p));
			unitCount += 1;
			recurCount += 1;
			if (!hasValidSibling(this, getType()))
				a.append(f.format(null, ")", DELIMITER, p));
		} catch (IOException e) {
		}

		if (hasSibling()) {
			if (hasValidSibling(this, getType())) {
				Digit n = (Digit) getSibling();
				n.unitCount = unitCount;
				n.recurCount = recurCount;
				if (unitCount % dp.getMantGroupSize() == 0)
					try {
						a.append(f.format(null, dp.getMantSeparator(), SEPARATOR, p));
					} catch (@SuppressWarnings("unused") IOException e) {
					}
			}
			getSibling().format(a, f, p);
		}
	}

	private void formatArc(Appendable a, Formatter f, List<Integer> p) {
		try {
			if (recurCount == 0)
				a.append("\\overparen{");
			a.append(f.format(this, digit, getType(), p));
			unitCount += 1;
			recurCount += 1;
			if (!hasValidSibling(this, getType()))
				a.append('}');
		} catch (IOException e) {
		}

		if (hasSibling()) {
			if (hasValidSibling(this, getType())) {
				Digit n = (Digit) getSibling();
				n.unitCount = unitCount;
				n.recurCount = recurCount;
				if (unitCount % dp.getMantGroupSize() == 0)
					try {
						a.append(f.format(null, dp.getMantSeparator(), SEPARATOR, p));
					} catch (@SuppressWarnings("unused") IOException e) {
					}
			}
			getSibling().format(a, f, p);
		}
	}

	private void formatDotBar(Appendable a, Formatter f, List<Integer> p) {
		try {
			if (recurCount == 0)
				if (!hasValidSibling(this, getType()))
					a.append("\\dot{");
				else
					a.append("\\overline{");
			a.append(f.format(this, digit, getType(), p));
			unitCount += 1;
			recurCount += 1;
			if (!hasValidSibling(this, getType()))
				a.append('}');
		} catch (IOException e) {
		}

		if (hasSibling()) {
			if (hasValidSibling(this, getType())) {
				Digit n = (Digit) getSibling();
				n.unitCount = unitCount;
				n.recurCount = recurCount;
				if (unitCount % dp.getMantGroupSize() == 0)
					try {
						a.append(f.format(null, dp.getMantSeparator(), SEPARATOR, p));
					} catch (@SuppressWarnings("unused") IOException e) {
					}
			}
			getSibling().format(a, f, p);
		}
	}

	private void formatDot(Appendable a, Formatter f, List<Integer> p) {
		String sym = digit;
		if (recurCount == 0 || !hasValidSibling(this, getType()))
			sym = String.format("\\dot{%s}", sym);
		try {
			a.append(f.format(this, sym, getType(), p));
		} catch (IOException e) {
		}

		unitCount += 1;
		recurCount += 1;

		if (hasSibling()) {
			if (hasValidSibling(this, getType())) {
				Digit n = (Digit) getSibling();
				n.unitCount = unitCount;
				n.recurCount = recurCount;
				if (unitCount % dp.getMantGroupSize() == 0)
					try {
						a.append(f.format(null, dp.getMantSeparator(), SEPARATOR, p));
					} catch (@SuppressWarnings("unused") IOException e) {
					}
			}
			getSibling().format(a, f, p);
		}
	}

	private void appendRepeatend(Appendable a, Formatter f, List<Integer> p) {
		try {
			String r = repeatend.toString();
			for (int i = 1; i < dp.getNumOfRecurringDigits(); i++)
				repeatend.append(r);
			for (int i = 0; i < repeatend.length(); i++) {
				if ((unitCount + i) % dp.getMantGroupSize() == 0) {
					a.append(f.format(null, dp.getMantSeparator(), SEPARATOR, null));
				}
				String d = repeatend.substring(i, i + 1);
				a.append(f.format(null, d, AUTO_COMPLETE, null));
			}
			a.append(f.format(null, "\\ldots", AUTO_COMPLETE, null));
		} catch (IOException e) {
		}
	}

	private void formatEllip(Appendable a, Formatter f, List<Integer> p) {
		try {
			a.append(f.format(this, digit, getType(), p));
		} catch (@SuppressWarnings("unused") IOException e) {
		}

		repeatend.append(digit);
		unitCount += 1;

		if (hasSibling()) {
			if (sibIsSame()) {
				Digit n = (Digit) getSibling();
				n.unitCount = unitCount;
				n.repeatend.append(repeatend);
				if (unitCount % dp.getMantGroupSize() == 0) {
					try {
						a.append(f.format(null, dp.getMantSeparator(), SEPARATOR, null));
					} catch (IOException e) {
					}
				}
			} else
				appendRepeatend(a, f, p);
			getSibling().format(a, f, p);
		} else
			appendRepeatend(a, f, p);
	}

	private void formatMant(Appendable a, Formatter f, List<Integer> p) {
		try {
			a.append(f.format(this, digit, getType(), p));
		} catch (@SuppressWarnings("unused") IOException e) {
		}
		unitCount += 1;

		if (hasSibling()) {
			if (sibIsSame() || isRecurring(getSibling())) {
				((Digit) getSibling()).unitCount = unitCount;
				if (unitCount % dp.getMantGroupSize() == 0) {
					try {
						a.append(f.format(null, dp.getIntSeparator(), SEPARATOR, null));
					} catch (@SuppressWarnings("unused") IOException e) {
					}
				}
			}
			getSibling().format(a, f, p);
		}
	}

	private void formatInt(Appendable a, Formatter f, List<Integer> p) {
		try {
			a.append(f.format(this, digit, getType(), p));
		} catch (@SuppressWarnings("unused") IOException e) {
		}

		if (hasSibling()) {
			if (sibIsSame()) {
				int numLength = getRemainingDigits();
				if (numLength % dp.getIntGroupSize() == 0 && numLength > 0) {
					try {
						a.append(f.format(null, dp.getIntSeparator(), SEPARATOR, null));
					} catch (@SuppressWarnings("unused") IOException e) {
					}
				}
			}
			getSibling().format(a, f, p);
		}
	}

	private static boolean isDigit(Segment s) {
		// return s != null && (s.getType() >= INTEGER && s.getType() <= PARENTHESIS);
		return s != null && (s instanceof Digit);
	}

	/*
	 * sibling is same type as parent segment
	 */
	private boolean sibIsSame(LinkedSegment s) {
		return s.getType() == s.getSibling().getType();
	}

	private boolean sibIsSame() {
		return sibIsSame(this);
	}

	private boolean isRecurring(Segment s) {
		return isDigit(s) && s.getType() != INTEGER && s.getType() != MANTISSA;
	}

	private boolean hasValidSibling(LinkedSegment s, int type) {
		return s.getSibling().getType() == type;
	}

	private boolean isVar(Segment s) {
		return s.getType() == CONSTANT || s.getType() == VAR_FREE || s.getType() == VAR_BOUND
				|| s.getType() == FUNCTION;
	}

	@Override
	public void format(Appendable a, Formatter f, List<Integer> p) {

		/* Update the current position of the cursor */
		p.set(p.size() - 1, p.get(p.size() - 1) + 1);

		switch (getType()) {
		case ARC:
			formatArc(a, f, p);
			break;
		case DOT:
			formatDot(a, f, p);
			break;
		case DOT_ALL:
			formatDotAll(a, f, p);
			break;
		case DOT_BAR:
			formatDotBar(a, f, p);
			break;
		case ELLIPSIS:
			formatEllip(a, f, p);
			break;
		case PARENTHESISED:
			formatParen(a, f, p);
			break;
		case VINCULUM:
			formatVinc(a, f, p);
			break;
		case MANTISSA:
			formatMant(a, f, p);
			break;
		case INTEGER:
		default:
			formatInt(a, f, p);
			break;
		}// End switch
	}

	@Override
	public void toString(Appendable a, Log l, List<Integer> p) {
		p.set(p.size() - 1, p.get(p.size() - 1) + 1);
		try {
			/*
			 * So this method will throw a parse exception here but it will be caught and
			 * will save the position in which it threw said exception, and then throw it.
			 */
			switch (getType()) {
			case ARC:
			case DOT:
			case DOT_ALL:
			case DOT_BAR:
			case ELLIPSIS:
			case PARENTHESISED:
			case VINCULUM:
				if (isFirstIndex)
					a.append('r');
				if (hasSibling() && isRecurring(getSibling()))
					((Digit) getSibling()).isFirstIndex = false;
			case INTEGER:
			case MANTISSA:
			default:
				a.append(digit);
			}
		} catch (@SuppressWarnings("unused") IOException e) {
		} catch (FatalReadException e) {
			throw new FatalParseException(e.getMessage(), e, p);
		}

		if (hasSibling()) {
			if (isVar(getSibling()) || getSibling().getType() == L_PARENTHESIS)
				try {
					a.append('*');
				} catch (@SuppressWarnings("unused") IOException e) {
				}
			getSibling().toString(a, l, p);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Digit) {
			Digit d = (Digit) obj;
			return getType() == d.getType() && digit.equals(d.digit);
		}
		return false;
	}

	@Override
	public String toString() {
		switch (getType()) {
		case INTEGER:
		default:
			return "INTEGER";
		case MANTISSA:
			return "MANTISSA";
		case ARC:
			return "ARC";
		case DOT:
			return "DOT";
		case DOT_ALL:
			return "DOT_ALL";
		case DOT_BAR:
			return "DOT_BAR";
		case ELLIPSIS:
			return "ELLIPSIS";
		case PARENTHESISED:
			return "PARENTHESIS";
		case VINCULUM:
			return "VINCULUM";
		}
	}

	private final String digit;
	private final DigitPunc dp;
	/*
	 * A StringBuilder for storing the repeatend of the ellipsis recurring type
	 */
	private final StringBuilder repeatend;// for the ellipsis recurring type
	private boolean isFirstIndex;// for all recurring type
	private int recurCount;// for some recurring types
	private int unitCount;// for mantissas
}