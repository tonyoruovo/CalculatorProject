/**
 * 
 */
package mathaid.calculator.base;

import static java.lang.System.err;
import static java.lang.System.in;
import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import mathaid.calculator.base.typeset.DigitPunc;
import mathaid.calculator.base.typeset.Digits;
import mathaid.calculator.base.typeset.Empty;
import mathaid.calculator.base.typeset.Formatter;
import mathaid.calculator.base.typeset.LinkedSegment;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;
import mathaid.calculator.base.util.Utility;

/*
 * Date: 18 Mar 2020----------------------------------------------------
 * Time created: 11:38:37--------------------------------------------
 * Package: mathaid.calculator.base-----------------------------------------
 * Project: LatestPoject2-----------------------------------------
 * File: Main.java-----------------------------------------------
 * Class name: Main-----------------------------------------
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * 
 */
//@SuppressWarnings("deprecation")
public class Main {

	public static String bin(String s, int div, int len) {
		out.println(err);
		out.println(in);
		if (len < 1)
			len = s.length();
		StringBuilder sb = new StringBuilder(len + (len / div));
		s = String.format("%1$s%2$s", Utility.string('0', Math.max(len - s.length(), 0)), s);
		int j = 0;
		for (int i = s.length() - 1; i >= 0; i--) {
			sb.insert(0, s.charAt(i));
			if (j != 0 && (j + 1) % div == 0 && j != len - 1)
				sb.insert(0, ' ');
			j += 1;
			if (j + 1 > len)
				break;
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		out.println(new Object[] { err, in });
		DigitPunc dp = new DigitPunc();
		// Notice that the order of the insert calls (for child segments) do not matter
		SegmentBuilder formula = new SegmentBuilder()
				.insert(Segments.freeVariable("x", "x"), 0)
				.insert(Segments.operator("=", "="), 1)
				.insert(Segments.fraction(null, null), 2)// null not ideal. Should use the Empty segment object instead
				// numerator
				.insert(Digits.prefixMinus(), 2, 0, 0)
				.insert(Segments.freeVariable("b", "b"), 2, 0, 1)
				.insert(Segments.operator("\\pm", "-"), 2, 0, 2)
				// inside the sqrt symbol
				.insert(Segments.sqrt(null), 2, 0, 3)
				//inside b squared
				.insert(Segments.pow(null, null), 2, 0, 3, 0, 0)
				.insert(Segments.freeVariable("b", "b"), 2, 0, 3, 0, 0, 0, 0)
				.insert(Digits.integer('2', dp), 2, 0, 3, 0, 0, 1, 0)// end b squared
				.insert(Segments.operator("-", "-"), 2, 0, 3, 0, 1)
				.insert(Digits.integer('4', dp), 2, 0, 3, 0, 2)
				.insert(Segments.freeVariable("a", "a"), 2, 0, 3, 0, 3)
				.insert(Segments.freeVariable("c", "c"), 2, 0, 3, 0, 4)// end numerator
				// denominator
				.insert(Digits.integer('2', dp), 2, 1, 0)
				.insert(Segments.freeVariable("a", "a"), 2, 1, 1);// end denominator

		var l = new ArrayList<Integer>(Arrays.asList(-1));
		formula.toSegment().toString(out, null, l);
		out.println();
		l.clear();
		l.add(-1);
		formula.toSegment().format(out, Formatter.empty(), l);
		out.println();
		
		out.println(Utility.toCSV(IntStream.range(4, 25).boxed().toList()));

//		SegmentBuilder sb = new SegmentBuilder();
//		int type = Segment.INT_DIGIT_SEGMENT, iSize = 3, mSize = 3, numOfRepeats = 3;
//		String iSeparator = ",", mSeparator = "~";
//		Map<Integer, FormatAction> middleware = Segment.emptyMiddleware();// defaultMiddleware(Segment.defaultStyles());
//		List<Integer> position = new ArrayList<>();
//		position.add(-1);
//		Appendable typesetter = new StringBuilder();
//		Appendable parser = new NumberAdapter();
//		BigFraction f = BigFraction.N_25_169;
//		Segment s = getFactors(String.format("Rational[%1$s, %2$s]", f.getNumerator(), f.getDenominator()));
//		new SegmentBuilder(s).reverse().toSegment().format(typesetter, middleware, position);
//		sb.append(Segments.digit("1", type, iSeparator, mSeparator, iSize, mSize, numOfRepeats));
//		sb.append(Segments.point("."));
//		sb.append(Segments.digit("8", type = Segment.MANT_DIGIT_SEGMENT, iSeparator, mSeparator, iSize, mSize,
//				numOfRepeats));
//		sb.append(Segments.digit("5", type = Segment.ELLIPSIS_R_DIGIT_SEGMENT, iSeparator, mSeparator, iSize, mSize,
//				numOfRepeats));
//		sb.append(Segments.mathExponent(new SegmentBuilder(Segments.operator("-", "\\pm", Segment.OPERATOR_SEGMENT))
//				.append(Segments.digit("3", type, iSeparator, mSeparator, iSize, mSize, numOfRepeats)).toSegment()));
//		sb.toSegment().format(typesetter, middleware, position);
//		out.println(typesetter);
//		err.println("Position: " + position);
//		s.toString(parser, null, position = new ArrayList<>(Arrays.asList(-1)));
//		err.println("Position: " + position);
//		out.println(parser);

//		byte l = Byte.MIN_VALUE;
//		String s = Byte.toString(l);
//		out.println("decimal: " + s);
//		out.println("binary: " + (s = Integer.toBinaryString(l)));
//		l -= 1;
//		out.println("decimal (added): " + (s = Byte.toString(l)));
//		out.println("binary (added): " + (s = Integer.toBinaryString(l)));
//		Control c = new Control();
//		Settings s = c.getSettings();
//		s.setState(State.INTEGER);
//		s.setBitLength(BitLength.BIT_8);
//		s.setBinaryRep(BinaryRep.TWO_C);
//		s.setRadix(Radix.BIN);

//		c.add(Tokens.ONE);
//		c.add(Tokens.ZERO);
//		c.add(Tokens.ZERO);
//		c.add(Tokens.ONE);
//		c.add(Tokens.ZERO);
//		c.add(Tokens.ONE);
//		c.add(Tokens.ONE);
//		c.add(Tokens.ZERO);
//		c.add(Tokens.RRn);
//		c.add(Tokens.ONE);
//		c.add(Tokens.ONE);
//		c.add(Tokens.ZERO);
//		c.add(Tokens.ZERO);
//		c.add(Tokens.ONE);
//		c.add(Tokens.ONE);

//		c.inform();
//		out.println(c.getDisplay().displayInput());10294659620395L 12345987654313450879
//		out.println(c.getDisplay().displayOutput());9654321234567

//		{ REWRITE_DISTRIBUTE, REWRITE_EXPAND, REWRITE_EXPAND_ALL, REWRITE_EXPAND_COMPLEX,
//		REWRITE_FRACTIONS_APART, REWRITE_FRACTIONS_APARTX, REWRITE_FRACTIONS_APARTY, REWRITE_FRACTIONS_TOGETHER,
//		REWRITE_HOLD, REWRITE_HOLD_ALL, REWRITE_HOLDFORM, REWRITE_HOLD_FIRST, REWRITE_HOLD_REST,
//		REWRITE_EXPAND_POWER, REWRITE_SIMPLIFY, REWRITE_SIMPLIFYX, REWRITE_SIMPLIFYY, REWRITE_SIMPLIFYX1,
//		REWRITE_SIMPLIFYY1, REWRITE_TRACE, REWRITE_THROUGH_FUNCTIONS, REWRITE_TRIG_EXPAND, REWRITE_TRIG_REDUCE,
//		REWRITE_TRIG_TO_EXPONENT };

//		Calculator c = new Calculator();
//		Scanner keyboard = new Scanner(in);
//		String equation = "0";
//		do {
//			try {
//				equation = keyboard.nextLine();
//				if(equation.equals("exit")) break;
//				c.start(equation);
//			} catch (Exception e) {
//				e.printStackTrace();
//				break;
//			}
//		} while (true);
//		if (keyboard != null)
//			keyboard.close();
//		Settings.defaultSetting().saveOverwrite();

//		for(int i = 0b1000_0000, j = 0; j < 8; i >>= 1, j++) {
//			out.println(Integer.toBinaryString(i));
//		}

//		int i = 1 << Integer.SIZE - 1;
//		int mask = 0x80_00_00_00;
//		out.println(Integer.toBinaryString(i));
//		out.println(Integer.toBinaryString(mask));

		// TODO: for Scientific
//		Keys k = Keys.build(53, 0x73_00_74_00, true);//new Keys(0b0111_0011_0000_0000_0111_0100_0000_0000, true);
//		int i = 0;
//		for(Map.Entry<Integer, KeyForm> e: k.headKey().getMap().entrySet()) {
//			out.println(++i + ".\tKey: " + bFormat(e.getKey()));
//		}
//		out.println("\tLength of keys: " + k.length());

		// TODO: for Programmer
//		k = Keys.build(53, 0x73_00_08_44, true);//new Keys(0b0111_0011_0000_0000_0000_0111_0100_0100, true);
//		i = 0;
//		for(Map.Entry<Integer, KeyForm> e: k.headKey().getMap().entrySet()) {
//			out.println(++i + ".\tKey: " + bFormat(e.getKey()));
//		}
//		out.println("\tLength of keys: " + k.length());

//		int[] tm = new int[] { 7, 4, 4 };
//		Set<Integer> l = i(tm);
//		int in = 0;
//		for (int i : l) {
//			String s = Integer.toBinaryString(i);
//			s = Utility.string('0', Integer.SIZE - s.length()) + s;
//			out.println(++in + "\t" + bFormat(s));
//		}

//		int i = 7 << 1;
//		String si = Integer.toBinaryString(i);
//		si = Utility.string('0', Integer.SIZE - si.length()) + si;
//		out.println(bFormat(si) + "\tLength: " + si.length());

//		out.println(Solution.calPoints(new String[] { "5", "2", "C", "D", "+" }));
//		out.println(Solution.isValid("([(({()}))])"));

		// D[Gamma[x],x] -> PolyGamma
		// Int[Gamma[x] + Cos[x^2], x] -> FresnelC

	}
}
