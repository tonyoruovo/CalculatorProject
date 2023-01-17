/**
 * 
 */
package mathaid.calculator.base;

import static java.lang.System.err;
import static java.lang.System.in;
import static java.lang.System.out;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import mathaid.calculator.base.util.Utility;
import mathaid.calculator.base.value.BigFraction;
import mathaid.calculator.base.value.UnitVector;

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

	static BigInteger i(int i) {
		return BigInteger.valueOf(i);
	}

	static BigDecimal d(BigInteger i) {
		return new BigDecimal(i);
	}

	public static String bin(String s, int div, int len) {
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
		out.println(err + "\t" + in);
		
		UnitVector v = new UnitVector(UnitVector.CURRENCY, BigFraction.valueOf(1));
		v = v.add(UnitVector.ANGLE, BigFraction.valueOf(2));
		v = v.add(UnitVector.AREA, BigFraction.valueOf(3));
		
		UnitVector v2 = new UnitVector(UnitVector.CURRENCY, BigFraction.valueOf(-2));
		v2 = v2.add(UnitVector.AREA, BigFraction.valueOf(4));
		v2 = v2.add(UnitVector.ANGLE, BigFraction.valueOf(0));
//		out.println(v2);
		
		out.println(v.add(v2).unitVector());
		
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

	static class Solution {
		public static int calPoints(String[] ops) {
			int result = Integer.MIN_VALUE;
			List<Integer> list = new ArrayList<>();

			for (int i = 0; i < ops.length; i++) {
				String op = ops[i];
				try {
					list.add(Integer.parseInt(op));
				} catch (NumberFormatException e) {
					switch (op) {
					case "+":
						list.add(list.stream().reduce((i1, i2) -> i1 + i2).get());
						break;
					case "D":
						list.add(list.get(list.size() - 1) * 2);
						break;
					case "C":
					default:
						list.remove(list.size() - 1);
					}
				}
			}
			result = list.stream().reduce((x, y) -> x + y).get();
			return result;
		}

		public static boolean isValid(String s) {
			boolean result = false;

			Stack<Character> str = new Stack<>();
			final Map<Character, Character> mirrors = Map.of(')', '(', '}', '{', ']', '[');
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				if (mirrors.containsKey(c)) {
					if (mirrors.get(c).equals(str.peek()))
						str.pop();
				} else
					str.push(c);
			}
			result = str.isEmpty();
			return result;
		}
	}

}