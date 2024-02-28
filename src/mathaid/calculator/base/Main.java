/**
 * 
 */
package mathaid.calculator.base;

import static java.lang.System.err;
import static java.lang.System.in;
import static java.lang.System.out;
import static mathaid.calculator.base.util.Utility.*;
import static mathaid.calculator.base.util.Constants.*;
import static mathaid.calculator.base.util.Arith.*;
import static mathaid.calculator.base.typeset.Digits.*;
import static mathaid.calculator.base.typeset.Segments.*;
import static mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import mathaid.calculator.base.evaluator.Evaluator;
import mathaid.calculator.base.evaluator.Scientific;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.evaluator.parser.expression.scientific.Name.Params;
import mathaid.calculator.base.typeset.DigitPunc;
import mathaid.calculator.base.typeset.Digits;
import mathaid.calculator.base.typeset.Formatter;
import mathaid.calculator.base.typeset.LinkedSegment;
import mathaid.calculator.base.typeset.Segment;
import mathaid.calculator.base.typeset.NumberAdapter;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.util.Arith;
import mathaid.calculator.base.util.Constants;
import mathaid.calculator.base.util.Tuple;
import mathaid.calculator.base.util.Utility;
import mathaid.calculator.base.value.FloatAid;
import mathaid.functional.Supplier;

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
 */
//@SuppressWarnings("deprecation")
public class Main {

	/**
	 * Sets the calculators fields using {@code set <i|b|s> <name-of-the-field-to-be-set> <value>}. i is for int fields b is for
	 * boolean fields s is for String fields
	 */
	static void set(Evaluator<?> e, String cmd) {
		String[] cmds = cmd.split("\\s");
		Class<?> c = e.getClass();
		Field f;
		try {
			f = c.getDeclaredField(cmds[2]);
		} catch (NoSuchFieldException | SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		f.setAccessible(true);
		try {
			switch (cmds[1].toLowerCase()) {
			case "i":
				f.setInt(e, Integer.parseInt(cmds[3]));
				System.out.println("int field set");
				break;
			case "b":
				f.setBoolean(e, Boolean.parseBoolean(cmds[3]));
				System.out.println("boolean field set");
				break;
			case "s":
				f.set(e, String.valueOf(cmds[3]));
				System.out.println("String field set");
				break;
			default:
				System.err.println(cmds[1] + " is an unknown type!");
			}
		} catch (IllegalArgumentException | IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * Print the calculators fields using {@code get <i|b|s> <name-of-the-field-to-be-printed>}. i is for int fields b is for
	 * boolean fields s is for String fields
	 */
	static void get(Evaluator<?> e, String cmd) {
		String[] cmds = cmd.split("\\s");
		Class<?> c = e.getClass();
		Field f;
		try {
			f = c.getDeclaredField(cmds[2]);
		} catch (NoSuchFieldException | SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		f.setAccessible(true);
		try {
			switch (cmds[1].toLowerCase()) {
			case "i":
				System.out.println(cmds[2] + " = " + f.getInt(e));
				break;
			case "b":
				System.out.println(cmds[2] + " = " + f.getBoolean(e));
				break;
			case "s":
				System.out.println(cmds[2] + " = " + f.get(e));
				break;
			default:
				System.err.println(cmds[1] + " is an unknown type!");
			}
		} catch (IllegalArgumentException | IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	static void eval(Evaluator<LinkedSegment> e) {

		Scanner sc = new Scanner(in);

		Appendable sb = new NumberAdapter();
		List<Integer> l = new ArrayList<>(Arrays.asList(-1));
		while (true) {
			String cmd = sc.nextLine();
			if (cmd.toLowerCase().matches("(?:end|exit|stop|cancel)"))
				break;
			else if (cmd.toLowerCase().startsWith("set")) {
				set(e, cmd);
				continue;
			} else if (cmd.toLowerCase().startsWith("get")) {
				get(e, cmd);
				continue;
			}
			try {
				Segment s = e.evaluate(cmd);
				s.toString(sb, null, l);
				err.println(sb);
				l.clear();
				l.add(-1);
				s.format(sb.delete(0, sb.length()), Formatter.empty(), l);
				out.println(sb);
			} catch (Throwable t) {
				t.printStackTrace();
			}
			sb.delete(0, sb.length());
			l.clear();
			l.add(-1);
		}

		sc.close();

	}

	// Please show in the segment-builder documentation the difference between
	// setting the focus on an inner child using vanilla linked-segment and doing
	// the same using segment-builder.

	public static void main(String[] args) throws InterruptedException {
		out.println(Arrays.toString(new Object[] { err, in, out }));
		
		eval(new Scientific());

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
