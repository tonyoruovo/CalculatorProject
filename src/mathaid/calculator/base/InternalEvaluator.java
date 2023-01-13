/**
 * 
 */
package mathaid.calculator.base;

import static mathaid.calculator.base.Calculator.ACOS;
import static mathaid.calculator.base.Calculator.ACOSH;
import static mathaid.calculator.base.Calculator.AND;
import static mathaid.calculator.base.Calculator.ASIN;
import static mathaid.calculator.base.Calculator.ASINH;
import static mathaid.calculator.base.Calculator.ATAN;
import static mathaid.calculator.base.Calculator.ATANH;
import static mathaid.calculator.base.Calculator.BTF;
import static mathaid.calculator.base.Calculator.CB;
import static mathaid.calculator.base.Calculator.CBRT;
import static mathaid.calculator.base.Calculator.COS;
import static mathaid.calculator.base.Calculator.COSH;
import static mathaid.calculator.base.Calculator.EXP;
import static mathaid.calculator.base.Calculator.FTB;
import static mathaid.calculator.base.Calculator.LN;
import static mathaid.calculator.base.Calculator.LOG;
import static mathaid.calculator.base.Calculator.MAX;
import static mathaid.calculator.base.Calculator.MIN;
import static mathaid.calculator.base.Calculator.MOD;
import static mathaid.calculator.base.Calculator.NAND;
import static mathaid.calculator.base.Calculator.NOR;
import static mathaid.calculator.base.Calculator.NOT;
import static mathaid.calculator.base.Calculator.OR;
import static mathaid.calculator.base.Calculator.RL;
import static mathaid.calculator.base.Calculator.RLN;
import static mathaid.calculator.base.Calculator.RR;
import static mathaid.calculator.base.Calculator.RRN;
import static mathaid.calculator.base.Calculator.SIN;
import static mathaid.calculator.base.Calculator.SINH;
import static mathaid.calculator.base.Calculator.SL;
import static mathaid.calculator.base.Calculator.SLN;
import static mathaid.calculator.base.Calculator.SQ;
import static mathaid.calculator.base.Calculator.SQRT;
import static mathaid.calculator.base.Calculator.SR;
import static mathaid.calculator.base.Calculator.SRN;
import static mathaid.calculator.base.Calculator.TAN;
import static mathaid.calculator.base.Calculator.TANH;
import static mathaid.calculator.base.Calculator.ULP;
import static mathaid.calculator.base.Calculator.XNOR;
import static mathaid.calculator.base.Calculator.XOR;
import static mathaid.calculator.base.Settings.REP_EXCESS_N;
import static mathaid.calculator.base.Settings.REP_FLOAT_POINT;
import static mathaid.calculator.base.Settings.REP_MATH;
import static mathaid.calculator.base.Settings.REP_NEGABINARY;
import static mathaid.calculator.base.Settings.REP_ONEC;
import static mathaid.calculator.base.Settings.REP_SMR;
import static mathaid.calculator.base.Settings.REP_TWOC;
import static mathaid.calculator.base.Settings.REP_UNSIGNED;
import static mathaid.calculator.base.Settings.TRIG_DEG;
import static mathaid.calculator.base.Settings.TRIG_GRAD;
import static mathaid.calculator.base.Settings.TRIG_RAD;
import static mathaid.calculator.base.Settings.defaultSetting;
import static mathaid.calculator.base.util.Utility.isInteger;
import static mathaid.calculator.base.util.Utility.isNumber;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mathaid.ExceptionMessage;
import mathaid.calculator.base.converter.AngleUnit;
import mathaid.calculator.base.parser.CommonSyntax;
import mathaid.calculator.base.parser.Lexer;
import mathaid.calculator.base.parser.PrattParser;
import mathaid.calculator.base.parser.Token;
import mathaid.calculator.base.parser.Type;
import mathaid.calculator.base.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.parser.expression.EvaluatableFunction;
import mathaid.calculator.base.parser.expression.EvaluatableGroup;
import mathaid.calculator.base.parser.expression.EvaluatableName;
import mathaid.calculator.base.parser.expression.EvaluatableOperator;
import mathaid.calculator.base.parser.expression.EvaluatablePostfix;
import mathaid.calculator.base.parser.expression.EvaluatablePrefix;
import mathaid.calculator.base.parser.expression.NameExpression;
import mathaid.calculator.base.parser.parselet.Parselet;
import mathaid.calculator.base.util.Arith;
import mathaid.calculator.base.value.BinaryRep;
import mathaid.calculator.base.value.BitLength;
import mathaid.calculator.base.value.FloatPoint;
import mathaid.calculator.base.value.Precision;
import mathaid.calculator.base.value.Radix;

/*
 * Date: 20 Jun 2021----------------------------------------------------------- 
 * Time created: 10:39:19---------------------------------------------------  
 * Package: mathaid.calculator.base------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: InternalEvaluator.java------------------------------------------------------ 
 * Class name: InternalEvaluator------------------------------------------------ 
 */
/**
 * A calculator for the {@code Programmer} class which helps to implement the
 * {@code Evaluator} interface. It contains tools and properties that assist the
 * {@code Programmer} class with parsing and evaluating expressions.
 * <p>
 * Values are entered into this class by calling {@link #evaluate(String)} and
 * then passing a {@code String} argument that represent the expression that
 * users wants to evaluate. For example: by setting the current {@code Settings}
 * radix to decimal, a user may enter <code>6+5*4^2/3</code> as a {@code String}
 * argument into the method. The returned value is a {@code DataText} object
 * containing a string that is in a valid syntax (in a syntax that can used as a
 * direct argument to {@link #evaluate(String)}) retrieved by
 * {@link DataText#getSymjaString()} and another string in TeX form used for
 * display retrieved by {@link DataText#getTeXString()}. Only numeric values are
 * valid in this class, expressions such as {@code x+y} where <code>y</code> is
 * a free variable are not valid and evaluating them will yield undefined
 * results.
 * </p>
 * <p>
 * There are 2 modes supported by this class namely: integer and floating-point
 * mode. Both include specified bit length (4 (excluding floating point), 8, 16,
 * 32, 64, 128 and 25) and 4 different radixes supported by this class, binary,
 * octal, decimal and hexadecimal (which are only valid if they are upper case).
 * The integer mode (which is set by the {@code Settings} class) supports 6
 * forms. These include: {@link mathaid.calculator.base.value.BinaryRep#MATH
 * math} , for everyday mathematical integer calculations&semi;
 * {@link mathaid.calculator.base.value.BinaryRep#SMR signed magnitude
 * representation} &semi; {@link mathaid.calculator.base.value.BinaryRep#ONE_C
 * one's complement} &semi; {@link mathaid.calculator.base.value.BinaryRep#TWO_C
 * two's complement} &semi;
 * {@link mathaid.calculator.base.value.BinaryRep#EXCESS_K excess-n} &semi;
 * {@link mathaid.calculator.base.value.BinaryRep#NEGABINARY negabinary} &semi;
 * {@link mathaid.calculator.base.value.BinaryRep#UNSIGNED unsigned numbers} .
 * The floating-point mode has no specified 4 bits values but in addition to the
 * other bit lengths and radixes supported by the integer mode, it can either be
 * in {@link Precision#checkNormalised(String) normalised or unnormalised}
 * forms. The "p" notation (returned in the JDK by
 * {@link java.lang.Double#toHexString(double)}) and "e" notation are both valid
 * only if they are lower case.
 * </p>
 * <p>
 * <i>This should not be used as a general standalone class because it relies
 * heavily on the </i>{@code Settings}<i> object and it's properties. For
 * example the current radix for numbers is determined by the
 * </i>{@code Settings}<i> class, therefore a user may not be able to directly
 * input an equation containing a number in a certain radix without access to
 * the </i>{@code Settings}<i> object. There are various other instances where
 * this object will not function properly if it used outside of the
 * </i>{@code Programmer}<i> class (which is in itself is dependent on the
 * </i>{@code Calculator}<i> class).</i>
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
class InternalEvaluator {

	/*
	 * Date: 27 Jun 2021-----------------------------------------------------------
	 * Time created: 12:59:25---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * InternalEvaluator.java------------------------------------------------------
	 * Class name: Name------------------------------------------------
	 */
	/**
	 * Represents constants and numbers. Because hex numbers are supported, upper
	 * case A through F is not allowed for naming of constants. Constant names are
	 * expected to be in lower case.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 *
	 */
	private static class Name extends EvaluatableName {

		/*
		 * Date: 7 Aug 2021-----------------------------------------------------------
		 * Time created: 14:57:05---------------------------------------------------
		 */
		/**
		 * Constructs a unique object for this class with the argument representing that
		 * in an expression. Only numbers and latin alphabets are allowed.
		 * 
		 * @param name a number (radix 2, 8, 10 and 1) or a named constant.
		 */
		private Name(String name) {
			super(name);
		}

		/*
		 * Most Recent Date: 7 Aug 2021-----------------------------------------------
		 * Most recent time created: 14:58:53--------------------------------------
		 */
		/**
		 * Appends the TeX representation of this object into the {@code StringBuilder}
		 * argument. If the {@code Settings} object is set to normalised floating point
		 * mode, then numbers will be appended in normalised form, i.e the significand
		 * multiplied by 2 to the power of the binary exponent.
		 * <p>
		 * <b>Note that the value appended to the argument is not the same as the same
		 * one returned by </b>{@link EvaluatableExpression#name()}
		 * </p>
		 * 
		 * @param sb a {@code StringBuilder} object.
		 */
		@Override
		public void toString(StringBuilder sb) {
			/*
			 * There are 2 types of Name expressions in this programmer's
			 * calculator.****************************************** 1. Numbers
			 * ******************************************************2. Function names
			 * Numbers are sub-divided into integers and floating point. floating point are
			 * sub-divided into normalised and not normalised floating point.
			 */
			Settings s = defaultSetting();
			switch (s.getRep()) {
			case REP_FLOAT_POINT:
				/* No need for the commented code, since at this point the value is a decimal */
//				if (isANumber(name(), s.getBitLength(), s.getRadix())) {
//					Precision p = Precision.valueOf(s.getBitLength());
//					if (s.isNormalise()) {
//						FloatPoint fp = FloatPoint.toFloat(name(), BitLength.valueOf(s.getBitLength()),
//								p.checkNormalised(name()));
//						String normalised = fp.normalise(getRadix(s.getRadix()));
//					}
//				}
				if (isANumber(name(), s.getBitLength(), s.getRadix())) {
					FloatPoint fp = FloatPoint.toFloat(name(), BitLength.valueOf(s.getBitLength()),
							getRadix(s.getRadix()), false);
					FloatingPointDetailsList fpdl = new FloatingPointDetailsList(fp);
					String normalised = s.isNormalise() ? fpdl.getNormalised(s.getRadix()).getTeXString()
							: fpdl.getExpression(s.getRadix()).getTeXString();
					sb.append(normalised);
					return;
				}
				break;
			default:
				if (isNumber(name(), s.getRadix())) {
					IntegerDetailsList idl = new IntegerDetailsList(name());
					sb.append(idl.getExpression(getRadix(s.getRadix()), false).getTeXString());
					return;
				}
			}

			sb.append(name());
		}

		/*
		 * TODO: this method should have a switch of all the method names in the
		 * programmer calculator so as to throw an exception when a floating point
		 * number is invalid. TODO: Remember to add values for pi and e
		 */
		/*
		 * Most Recent Date: 7 Aug 2021-----------------------------------------------
		 * Most recent time created: 15:03:26--------------------------------------
		 */
		/**
		 * Returns {@code this}.
		 * 
		 * @return {@code this}.
		 */
		@Override
		public EvaluatableExpression evaluate() {
			Settings s = defaultSetting();
			switch (s.getRep()) {
			case REP_FLOAT_POINT:
				if (isANumber(name(), s.getBitLength(), s.getRadix())) {
					Precision p = Precision.valueOf(s.getBitLength());
					FloatPoint fp = FloatPoint.toFloat(name(), BitLength.valueOf(s.getBitLength()),
							getRadix(s.getRadix()), p.checkNormalised(name()));
					return new Name(fp.toString());
				}
				break;
			case REP_SMR:
			case REP_ONEC:
			case REP_TWOC:
			case REP_EXCESS_N:
			case REP_NEGABINARY:
			case REP_UNSIGNED:
			case REP_MATH:
			default:
			}
			return this;
		}

	}

	/*
	 * Date: 20 Jun 2021-----------------------------------------------------------
	 * Time created: 10:39:19---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * InternalEvaluator.java------------------------------------------------------
	 * Class name: Prefix------------------------------------------------
	 */
	/**
	 * An expression whose operator represents the following characters +,-,!,~,¬
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 *
	 */
	private static class Prefix extends EvaluatablePrefix {

		/*
		 * Date: 7 Aug 2021-----------------------------------------------------------
		 * Time created: 15:09:15---------------------------------------------------
		 */
		/**
		 * Initialises this class with a string argument that represents the operator
		 * symbol and an expression that is the value which the operator should be
		 * evaluating.
		 * 
		 * @param name         a string to represent the operator symbol.
		 * @param rightOperand the value that the operator is evaluating, usually the
		 *                     one that represents the right-hand operand of a postfix
		 *                     operator.
		 */
		public Prefix(String name, EvaluatableExpression rightOperand) {
			super(name, rightOperand);
		}

		/*
		 * Most Recent Date: 7 Aug 2021-----------------------------------------------
		 * Most recent time created: 15:11:48--------------------------------------
		 */
		/**
		 * Appends {@link #name()} then calls this operator's value
		 * {@link NameExpression#toString(StringBuilder) toString} method.
		 * 
		 * @param sb {@inheritDoc}
		 */
		@Override
		public void toString(StringBuilder sb) {
			sb.append(name());
			getRightOperand().toString(sb);
		}

		/*
		 * Most Recent Date: 7 Aug 2021-----------------------------------------------
		 * Most recent time created: 15:18:55--------------------------------------
		 */
		/**
		 * Calculates the result of the expression and returns it. The value returned
		 * may be equal to {@code this}.
		 * 
		 * @return the result of the expression.
		 */
		@Override
		public EvaluatableExpression evaluate() {
			Settings s = defaultSetting();
			EvaluatableExpression right = getRightOperand().evaluate();
			if (isANumber(right.name(), s.getBitLength(), s.getRadix()))
				switch (name()) {
				case "+":
					if (s.getRep() == REP_FLOAT_POINT) {
						FloatPoint fp = FloatPoint.toFloat(right.name(), BitLength.valueOf(s.getBitLength()),
								getRadix(s.getRadix()), Precision.valueOf(s.getBitLength()).checkNormalised(name()));
						if (fp.isNaN() || fp.isInfinite())
							return new Name(fp.value().plus(fp.type().getMathContext()).toString());

					}
					return right;
				case "-":
					if (s.getRep() == REP_FLOAT_POINT) {
						FloatPoint fp = FloatPoint.toFloat(right.name(), BitLength.valueOf(s.getBitLength()),
								getRadix(s.getRadix()), Precision.valueOf(s.getBitLength()).checkNormalised(name()));
						if (fp.isNaN() || fp.isInfinite())
							return new Name(fp.value().negate(fp.type().getMathContext()).toString());
						new Name(
								FloatPoint
										.toFloat(fp.value().negate(fp.type().getMathContext()).toString(),
												BitLength.valueOf(s.getBitLength()), getRadix(s.getRadix()),
												Precision.valueOf(s.getBitLength()).checkNormalised(name()))
										.toString());

					}
					return new Name(
							getRep(s.getRep()).neg(new BigInteger(name(), s.getRadix()), s.getBitLength()).toString());
				case "!":
				case "~":
				case "¬":
					if (s.getRep() == REP_FLOAT_POINT) {
						FloatPoint fp = FloatPoint.toFloat(right.name(), BitLength.valueOf(s.getBitLength()),
								getRadix(s.getRadix()), Precision.valueOf(s.getBitLength()).checkNormalised(name()));
						if (fp.isNaN() || fp.isInfinite())
							return new Name(fp.toString());
						BinaryRep bin = BinaryRep.TWO_C;
						BigInteger integer = bin.not(fp.toBits(), s.getBitLength());
						return new Name(FloatPoint.fromBits(integer, BitLength.valueOf(s.getBitLength())).toString());
					}
					BinaryRep br = getRep(s.getRep());
					return new Name(br.not(new BigInteger(right.name()), s.getBitLength()).toString());
				default:
				}
			return this;
		}
	}

	/*
	 * Date: 7 Aug 2021-----------------------------------------------------------
	 * Time created: 15:21:04---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * InternalEvaluator.java------------------------------------------------------
	 * Class name: Postfix------------------------------------------------
	 */
	/**
	 * An expression whose operator represents the following characters ++,--,!
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 *
	 */
	private static class Postfix extends EvaluatablePostfix {

		/*
		 * Date: 7 Aug 2021-----------------------------------------------------------
		 * Time created: 15:31:38---------------------------------------------------
		 */
		/**
		 * Initialises this class with a string argument that represents the operator
		 * symbol and an expression that is the value which the operator should be
		 * evaluating.
		 * 
		 * @param name        a string to represent the operator symbol.
		 * @param leftOperand the value that the operator is evaluating, usually the one
		 *                    that represents the left operand of a postfix operator.
		 */
		public Postfix(EvaluatableExpression leftOperand, String name) {
			super(leftOperand, name);
		}

		/*
		 * Most Recent Date: 7 Aug 2021-----------------------------------------------
		 * Most recent time created: 15:35:00--------------------------------------
		 */
		/**
		 * Calls this operator's value {@link NameExpression#toString(StringBuilder)
		 * toString} method then appends {@link #name()}.
		 * 
		 * @param sb {@inheritDoc}
		 */
		@Override
		public void toString(StringBuilder sb) {
			getLeftOperand().toString(sb);
			sb.append(name());
//			sb.append(getRightOperand().name());
		}

		/*
		 * Most Recent Date: 7 Aug 2021-----------------------------------------------
		 * Most recent time created: 15:36:27--------------------------------------
		 */
		/**
		 * Calculates the result of the expression and returns it. The value returned
		 * may be equal to {@code this}.
		 * 
		 * @return the result of the expression.
		 */
		@Override
		public EvaluatableExpression evaluate() {
//			EvaluatableExpression right = getRightOperand().evaluate();TODO: Wrong?
			EvaluatableExpression right = getLeftOperand().evaluate();
			switch (name()) {
			case "!":
				Settings s = defaultSetting();
				if (isANumber(right.name(), s.getBitLength(), s.getRadix())) {
					if (s.getRep() == REP_FLOAT_POINT) {
						FloatPoint fp = FloatPoint.toFloat(right.name(), BitLength.valueOf(s.getBitLength()),
								getRadix(s.getRadix()), Precision.valueOf(s.getBitLength()).checkNormalised(name()));
						if (fp.isNaN() || fp.isInfinite())
							return new Name(fp.value().toString());
						return new Name(Arith.factorial(fp.value(), fp.type().getMathContext()).toString());
					}
				}
				break;
			default:
			}
			return new Name(getLeftOperand().name().concat(name()));
		}
	}

	/*
	 * Date: 26 Jun 2021-----------------------------------------------------------
	 * Time created: 18:05:28---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * InternalEvaluator.java------------------------------------------------------
	 * Class name: Operator------------------------------------------------
	 */
	/**
	 * Handles the 5 cardinal binary operators namely: +, -, *, /, ^ and their
	 * respective operations regarding valid operands.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 *
	 */
	private static class Operator extends EvaluatableOperator {

		/*
		 * Date: 7 Aug 2021-----------------------------------------------------------
		 * Time created: 15:44:49---------------------------------------------------
		 */
		/**
		 * Constructs an {@code Operator} with a left operand, symbol and a right
		 * operand.
		 * 
		 * @param leftOperand  the value that the operator is evaluating, usually the
		 *                     one that represents the left operand of an infix
		 *                     operator.
		 * @param name         a string to represent the operator symbol.
		 * @param rightOperand the value that the operator is evaluating, usually the
		 *                     one that represents the right operand of an infix
		 *                     operator.
		 */
		public Operator(EvaluatableExpression leftOperand, String name, EvaluatableExpression rightOperand) {
			super(leftOperand, name, rightOperand);
		}

		/*
		 * Most Recent Date: 7 Aug 2021-----------------------------------------------
		 * Most recent time created: 15:48:18--------------------------------------
		 */
		/**
		 * Calls the left operand's {@link NameExpression#toString(StringBuilder)
		 * toString} method, appends {@link #name()} and then calls the right operand's
		 * {@link NameExpression#toString(StringBuilder) toString} method.
		 * 
		 * @param sb {@inheritDoc}
		 */
		@Override
		public void toString(StringBuilder sb) {
			getLeftOperand().toString(sb);
			sb.append(name());
			getRightOperand().toString(sb);
		}

		/*
		 * Most Recent Date: 7 Aug 2021-----------------------------------------------
		 * Most recent time created: 15:50:40--------------------------------------
		 */
		/**
		 * Calculates the result of the operation between the 2 operands and returns it.
		 * 
		 * @return an expression resulting from {@code this} applied to both it's
		 *         operands. This action may be an idempotent operation.
		 */
		@Override
		public EvaluatableExpression evaluate() {
			EvaluatableExpression left = getLeftOperand().evaluate();
			EvaluatableExpression right = getRightOperand().evaluate();
			Settings s = defaultSetting();

			// By this point both operands should be numerical
			switch (name()) {
			case "+":
				if (s.getRep() == REP_FLOAT_POINT) {
					FloatPoint l = FloatPoint.toFloat(left.name(), BitLength.valueOf(s.getBitLength()),
							getRadix(s.getRadix()), Precision.valueOf(s.getBitLength()).checkNormalised(left.name()));
					if (l.isNaN() || l.isInfinite())
						return new Name(l.toString());
					FloatPoint r = FloatPoint.toFloat(right.name(), BitLength.valueOf(s.getBitLength()),
							getRadix(s.getRadix()), Precision.valueOf(s.getBitLength()).checkNormalised(right.name()));
					if (r.isNaN() || r.isInfinite())
						return new Name(r.toString());
					l = FloatPoint.toFloat(l.value().add(r.value(), l.type().getMathContext()),
							BitLength.valueOf(s.getBitLength()));
					return new Name(l.toString());
				}
				BinaryRep br = getRep(s.getRep());
				BigInteger ans = br.add(new BigInteger(left.name(), s.getRadix()),
						new BigInteger(right.name(), s.getRadix()), s.getBitLength());
				return new Name(ans.toString(s.getRadix()));
			case "-":
				if (s.getRep() == REP_FLOAT_POINT) {
					FloatPoint l = FloatPoint.toFloat(left.name(), BitLength.valueOf(s.getBitLength()),
							getRadix(s.getRadix()), Precision.valueOf(s.getBitLength()).checkNormalised(left.name()));
					if (l.isNaN() || l.isInfinite())
						return new Name(l.toString());
					FloatPoint r = FloatPoint.toFloat(right.name(), BitLength.valueOf(s.getBitLength()),
							getRadix(s.getRadix()), Precision.valueOf(s.getBitLength()).checkNormalised(right.name()));
					if (r.isNaN() || r.isInfinite())
						return new Name(r.toString());
					l = FloatPoint.toFloat(l.value().subtract(r.value(), l.type().getMathContext()),
							BitLength.valueOf(s.getBitLength()));
					return new Name(l.toString());
				}
				br = getRep(s.getRep());
				ans = br.subtract(new BigInteger(left.name(), s.getRadix()), new BigInteger(right.name(), s.getRadix()),
						s.getBitLength());
				return new Name(ans.toString(s.getRadix()));
			case "*":
				if (s.getRep() == REP_FLOAT_POINT) {
					FloatPoint l = FloatPoint.toFloat(left.name(), BitLength.valueOf(s.getBitLength()),
							getRadix(s.getRadix()), Precision.valueOf(s.getBitLength()).checkNormalised(left.name()));
					if (l.isNaN() || l.isInfinite())
						return new Name(l.toString());
					FloatPoint r = FloatPoint.toFloat(right.name(), BitLength.valueOf(s.getBitLength()),
							getRadix(s.getRadix()), Precision.valueOf(s.getBitLength()).checkNormalised(right.name()));
					if (r.isNaN() || r.isInfinite())
						return new Name(r.toString());
					l = FloatPoint.toFloat(l.value().multiply(r.value(), l.type().getMathContext()),
							BitLength.valueOf(s.getBitLength()));
					return new Name(l.toString());
				}
				br = getRep(s.getRep());
				ans = br.multiply(new BigInteger(left.name(), s.getRadix()), new BigInteger(right.name(), s.getRadix()),
						s.getBitLength());
				return new Name(ans.toString(s.getRadix()));
			case "/":
				if (s.getRep() == REP_FLOAT_POINT) {
					FloatPoint l = FloatPoint.toFloat(left.name(), BitLength.valueOf(s.getBitLength()),
							getRadix(s.getRadix()), Precision.valueOf(s.getBitLength()).checkNormalised(left.name()));
					if (l.isNaN() || l.isInfinite())
						return new Name(l.toString());
					FloatPoint r = FloatPoint.toFloat(right.name(), BitLength.valueOf(s.getBitLength()),
							getRadix(s.getRadix()), Precision.valueOf(s.getBitLength()).checkNormalised(right.name()));
					if (r.isNaN() || r.isInfinite())
						return new Name(r.toString());
					l = FloatPoint.toFloat(l.value().divide(r.value(), l.type().getMathContext()),
							BitLength.valueOf(s.getBitLength()));
					return new Name(l.toString());
				}
				br = getRep(s.getRep());
				ans = br.divide(new BigInteger(left.name(), s.getRadix()), new BigInteger(right.name(), s.getRadix()),
						s.getBitLength());
				return new Name(ans.toString(s.getRadix()));
			case "^":
				if (s.getRep() == REP_FLOAT_POINT) {
					FloatPoint l = FloatPoint.toFloat(left.name(), BitLength.valueOf(s.getBitLength()),
							getRadix(s.getRadix()), Precision.valueOf(s.getBitLength()).checkNormalised(left.name()));
					if (l.isNaN() || l.isInfinite())
						return new Name(l.toString());
					FloatPoint r = FloatPoint.toFloat(right.name(), BitLength.valueOf(s.getBitLength()),
							getRadix(s.getRadix()), Precision.valueOf(s.getBitLength()).checkNormalised(right.name()));
					if (r.isNaN() || r.isInfinite())
						return new Name(r.toString());
					l = FloatPoint.toFloat(Arith.pow(l.value(), r.value(), l.type().getMathContext()),
							BitLength.valueOf(s.getBitLength()));
					return new Name(l.toString());
				}
				br = getRep(s.getRep());
				ans = br.pow(new BigInteger(left.name(), s.getRadix()), new BigInteger(right.name(), s.getRadix()),
						s.getBitLength());
				return new Name(ans.toString(s.getRadix()));
			default:
			}

			return this;
		}
	}

	/*
	 * Date: 7 Aug 2021-----------------------------------------------------------
	 * Time created: 17:17:13---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * InternalEvaluator.java------------------------------------------------------
	 * Class name: Function------------------------------------------------
	 */
	/**
	 * An expression that represents mathematical functions (sine, tangent,
	 * logarithm) as well as user specified functions. All functions use square
	 * brackets to input their arguments for example: sin[0.5] would be the correct
	 * syntax for this. Please see
	 * {@link mathaid.calculator.base.parser.CommonSyntax#functionOpen() this}
	 * documentation.
	 * <p>
	 * All names are assumed to be lower case else may not be evaluated.
	 * </p>
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 *
	 */
	private static class Function extends EvaluatableFunction {

		/*
		 * Date: 7 Aug 2021-----------------------------------------------------------
		 * Time created: 17:25:58---------------------------------------------------
		 */
		/**
		 * Constructs a function by creating it's head and argument list. The head of
		 * the function is expected to be an {@link InternalEvaluator.Name} for
		 * syntactic and legacy purposes but virtually any valid expression can be used
		 * for it's head. The argument list can be an empty list but must never be null
		 * or a {@code NullPointerException} will be thrown.
		 * 
		 * @param functionName the head of the function.
		 * @param args         A {@code List} containing the arguments to this function
		 *                     in a deliberate order.
		 */
		public Function(EvaluatableExpression functionName, List<EvaluatableExpression> args) {
			super(functionName, args);
		}

		/*
		 * Most Recent Date: 7 Aug 2021-----------------------------------------------
		 * Most recent time created: 17:34:11--------------------------------------
		 */
		/**
		 * Appends {@code this} to the specified {@code StringBuilder} in a pretty-print
		 * (TeX) format.
		 * 
		 * @param sb {@inheritDoc}
		 */
		@Override
		public void toString(StringBuilder sb) {
			functionName().toString(sb);
			sb.append("\\left(");
			java.util.Iterator<EvaluatableExpression> it = args().iterator();
			while (it.hasNext()) {
				it.next().toString(sb);
				if (it.hasNext())
					sb.append(',');
			}
			sb.append("\\right)");
		}

		/*
		 * Most Recent Date: 7 Aug 2021-----------------------------------------------
		 * Most recent time created: 17:35:55--------------------------------------
		 */
		/**
		 * Computes the result of {@code this} when applied to the specified
		 * (constructor) arguments and return it as a {@code EvaluatableExpression}.
		 * This operation may have no effect.
		 * 
		 * @return an expression which is the result of {@code this} applied to the
		 *         arguments specified by the constructor.
		 */
		@Override
		public EvaluatableExpression evaluate() {
			Settings s = defaultSetting();
			final EvaluatableExpression firstArg = args().get(0).evaluate();
			EvaluatableExpression fName = functionName().evaluate();
			EvaluatableExpression secondArg = null;
			Precision p = null;
			if (s.getRep() == REP_FLOAT_POINT) {
				FloatPoint fp = FloatPoint.toFloat(firstArg.name(), BitLength.valueOf(s.getBitLength()),
						getRadix(s.getRadix()), Precision.valueOf(s.getBitLength()).checkNormalised(firstArg.name()));
				if (fp.isNaN() || fp.isInfinite())
					return new Name(fp.toString());
				p = Precision.valueOf(s.getBitLength());
				BigDecimal n = FloatPoint.toFloat(firstArg.name(), BitLength.valueOf(s.getBitLength()),
						getRadix(s.getRadix()), p.checkNormalised(firstArg.name())).value();
				switch (fName.name()) {
				case SIN:
					return new Name(Arith.sin(n, getTrig(s.getTrig()), p.getMathContext()).toString());
				case COS:
					return new Name(Arith.cos(n, getTrig(s.getTrig()), p.getMathContext()).toString());
				case TAN:
					return new Name(Arith.tan(n, getTrig(s.getTrig()), p.getMathContext()).toString());
				case ASIN:
					return new Name(Arith.asin(n, getTrig(s.getTrig()), p.getMathContext()).toString());
				case ACOS:
					return new Name(Arith.acos(n, getTrig(s.getTrig()), p.getMathContext()).toString());
				case ATAN:
					if (args().size() == 2) {
						secondArg = args().get(1).evaluate();
						BigDecimal n2 = FloatPoint.toFloat(secondArg.name(), BitLength.valueOf(s.getBitLength()),
								getRadix(s.getRadix()), p.checkNormalised(secondArg.name())).value();
						return new Name(Arith.atan(n, n2, getTrig(s.getTrig()), p.getMathContext()).toString());
					}
					return new Name(Arith.atan(n, getTrig(s.getTrig()), p.getMathContext()).toString());
				case SINH:
					return new Name(Arith.sinh(n, p.getMathContext()).toString());
				case COSH:
					return new Name(Arith.cosh(n, p.getMathContext()).toString());
				case TANH:
					return new Name(Arith.tanh(n, p.getMathContext()).toString());
				case ASINH:
					return new Name(Arith.asinh(n, p.getMathContext()).toString());
				case ACOSH:
					return new Name(Arith.acosh(n, p.getMathContext()).toString());
				case ATANH:
					return new Name(Arith.atanh(n, p.getMathContext()).toString());
				case LOG:
					return new Name(Arith.log10(n, p.getMathContext()).toString());
				case LN:
					return new Name(Arith.log(n, p.getMathContext()).toString());
				case EXP:
					return new Name(Arith.exp(n, p.getMathContext()).toString());
				case SQRT:
					return new Name(Arith.sqrt(n, p.getMathContext()).toString());
				case CBRT:
					return new Name(Arith.cbrt(n, p.getMathContext().getPrecision()).toString());
				case SQ:
					return new Name(n.multiply(n, p.getMathContext()).toString());
				case CB:
					return new Name(n.multiply(n.multiply(n, p.getMathContext()), p.getMathContext()).toString());
				case ULP:
					return new Name(p.ulp(n).toString());
				case FTB:
//					BigInteger bi = FloatPoint.toFloat(n, BitLength.valueOf(s.getBitLength())).toBits();
//					System.err.println("float :- " + bi.toString(16) + "to Bits :- " + bi);
					return new Name(FloatPoint.toFloat(n, BitLength.valueOf(s.getBitLength())).toBits().toString());
				case BTF:
					if (!isInteger(n))
						throw new ArithmeticException("Only integers allowed");
					return new Name(
							FloatPoint.fromBits(n.toBigIntegerExact(), BitLength.valueOf(s.getBitLength())).toString());
				default:
				}
			}
			BigInteger x = s.getRep() == REP_FLOAT_POINT
					? FloatPoint.toFloat(firstArg.name(), BitLength.valueOf(s.getBitLength()), getRadix(s.getRadix()),
							Precision.valueOf(s.getBitLength()).checkNormalised(firstArg.name())).toBits()
					: new BigInteger(firstArg.name(), s.getRadix());
			BigInteger y = null;
			BinaryRep br = s.getRep() == REP_FLOAT_POINT ? BinaryRep.TWO_C : getRep(s.getRep());
			if (args().size() == 2) {
				secondArg = args().get(1).evaluate();
				y = (s.getRep() == REP_FLOAT_POINT
						? FloatPoint
								.toFloat(secondArg.name(), BitLength.valueOf(s.getBitLength()), getRadix(s.getRadix()),
										Precision.valueOf(s.getBitLength()).checkNormalised(secondArg.name()))
								.toBits()
						: new BigInteger(secondArg.name(), s.getRadix()));
			}
			switch (fName.name()) {
			case AND:
				if (s.getRep() == REP_FLOAT_POINT) {
					FloatPoint fp = FloatPoint.fromBits(br.and(x, y, s.getBitLength()),
							BitLength.valueOf(s.getBitLength()));
					return new Name(fp.toString());
				}
				return new Name(br.and(x, y, s.getBitLength()).toString(s.getRadix()));
			case OR:
				if (s.getRep() == REP_FLOAT_POINT) {
					FloatPoint fp = FloatPoint.fromBits(br.or(x, y, s.getBitLength()),
							BitLength.valueOf(s.getBitLength()));
					return new Name(fp.toString());
				}
				return new Name(br.or(x, y, s.getBitLength()).toString(s.getRadix()));
			case NOR:
				if (s.getRep() == REP_FLOAT_POINT) {
					FloatPoint fp = FloatPoint.fromBits(br.nor(x, y, s.getBitLength()),
							BitLength.valueOf(s.getBitLength()));
					return new Name(fp.toString());
				}
				return new Name(br.nor(x, y, s.getBitLength()).toString(s.getRadix()));
			case NAND:
				if (s.getRep() == REP_FLOAT_POINT) {
					FloatPoint fp = FloatPoint.fromBits(br.nand(x, y, s.getBitLength()),
							BitLength.valueOf(s.getBitLength()));
					return new Name(fp.toString());
				}
				return new Name(br.nand(x, y, s.getBitLength()).toString(s.getRadix()));
			case NOT:
				if (s.getRep() == REP_FLOAT_POINT) {
					FloatPoint fp = FloatPoint.fromBits(br.not(x, s.getBitLength()),
							BitLength.valueOf(s.getBitLength()));
					return new Name(fp.toString());
				}
				return new Name(br.not(x, s.getBitLength()).toString(s.getRadix()));
			case XOR:
				if (s.getRep() == REP_FLOAT_POINT) {
					FloatPoint fp = FloatPoint.fromBits(br.xor(x, y, s.getBitLength()),
							BitLength.valueOf(s.getBitLength()));
					return new Name(fp.toString());
				}
				return new Name(br.xor(x, y, s.getBitLength()).toString(s.getRadix()));
			case XNOR:
				if (s.getRep() == REP_FLOAT_POINT) {
					FloatPoint fp = FloatPoint.fromBits(br.xnor(x, y, s.getBitLength()),
							BitLength.valueOf(s.getBitLength()));
					return new Name(fp.toString());
				}
				return new Name(br.xnor(x, y, s.getBitLength()).toString(s.getRadix()));
			case RL:
				if (s.getRep() == REP_FLOAT_POINT) {
					FloatPoint fp = FloatPoint.fromBits(br.rotateLeft1(x, s.getBitLength()),
							BitLength.valueOf(s.getBitLength()));
					return new Name(fp.toString());
				}
				return new Name(br.rotateLeft1(x, s.getBitLength()).toString(s.getRadix()));
			case RR:
				if (s.getRep() == REP_FLOAT_POINT) {
					FloatPoint fp = FloatPoint.fromBits(br.rotateRight1(x, s.getBitLength()),
							BitLength.valueOf(s.getBitLength()));
					return new Name(fp.toString());
				}
				return new Name(br.rotateRight1(x, s.getBitLength()).toString(s.getRadix()));
			case RLN:
				if (s.getRep() == REP_FLOAT_POINT) {
					FloatPoint fp = FloatPoint.fromBits(br.rotateLeft(x, y.intValue(), s.getBitLength()),
							BitLength.valueOf(s.getBitLength()));
					return new Name(fp.toString());
				}
				return new Name(br.rotateLeft(x, y.intValue(), s.getBitLength()).toString(s.getRadix()));
			case RRN:
				if (s.getRep() == REP_FLOAT_POINT) {
					FloatPoint fp = FloatPoint.fromBits(br.rotateRight(x, y.intValue(), s.getBitLength()),
							BitLength.valueOf(s.getBitLength()));
					return new Name(fp.toString());
				}
				return new Name(br.rotateRight(x, y.intValue(), s.getBitLength()).toString(s.getRadix()));
			case SL:
				if (s.getRep() == REP_FLOAT_POINT) {
					FloatPoint fp = FloatPoint.fromBits(br.shiftLeft1(x, s.getBitLength()),
							BitLength.valueOf(s.getBitLength()));
					return new Name(fp.toString());
				}
				return new Name(br.shiftLeft1(x, s.getBitLength()).toString(s.getRadix()));
			case SR:
				if (s.getRep() == REP_FLOAT_POINT) {
					FloatPoint fp = FloatPoint.fromBits(br.shiftRight1(x, s.getBitLength()),
							BitLength.valueOf(s.getBitLength()));
					return new Name(fp.toString());
				}
				return new Name(br.shiftRight1(x, s.getBitLength()).toString(s.getRadix()));
			case SLN:
				if (s.getRep() == REP_FLOAT_POINT) {
					FloatPoint fp = FloatPoint.fromBits(br.shiftLeft(x, y.intValue(), s.getBitLength()),
							BitLength.valueOf(s.getBitLength()));
					return new Name(fp.toString());
				}
				return new Name(br.shiftLeft(x, y.intValue(), s.getBitLength()).toString(s.getRadix()));
			case SRN:
				if (s.getRep() == REP_FLOAT_POINT) {
					FloatPoint fp = FloatPoint.fromBits(br.shiftRight(x, y.intValue(), s.getBitLength()),
							BitLength.valueOf(s.getBitLength()));
					return new Name(fp.toString());
				}
				return new Name(br.shiftRight(x, y.intValue(), s.getBitLength()).toString(s.getRadix()));
			case MOD:
				if (s.getRep() == REP_FLOAT_POINT) {
					BigDecimal ans = Arith.mod(
							FloatPoint.toFloat(firstArg.name(), BitLength.valueOf(s.getBitLength()),
									getRadix(s.getRadix()), p.checkNormalised(firstArg.name())).value(),
							FloatPoint.toFloat(secondArg.name(), BitLength.valueOf(s.getBitLength()),
									getRadix(s.getRadix()), p.checkNormalised(secondArg.name())).value());
					return new Name(ans.toString());
				}
				return new Name(br.mod(x, y, s.getBitLength()).toString(s.getRadix()));
			case MIN:
				if (s.getRep() == REP_FLOAT_POINT) {
					return new Name(
							FloatPoint
									.toFloat(firstArg.name(), BitLength.valueOf(s.getBitLength()),
											getRadix(s.getRadix()), p.checkNormalised(firstArg.name()))
									.value()
									.min(FloatPoint
											.toFloat(secondArg.name(), BitLength.valueOf(s.getBitLength()),
													getRadix(s.getRadix()), p.checkNormalised(secondArg.name()))
											.value())
									.toString());
				}
				return new Name(br.min(x, y, s.getBitLength()).toString(s.getRadix()));
			case MAX:
				if (s.getRep() == REP_FLOAT_POINT) {
					return new Name(
							FloatPoint
									.toFloat(firstArg.name(), BitLength.valueOf(s.getBitLength()),
											getRadix(s.getRadix()), p.checkNormalised(firstArg.name()))
									.value()
									.max(FloatPoint
											.toFloat(secondArg.name(), BitLength.valueOf(s.getBitLength()),
													getRadix(s.getRadix()), p.checkNormalised(secondArg.name()))
											.value())
									.toString());
				}
				return new Name(br.max(x, y, s.getBitLength()).toString(s.getRadix()));
			default:
			}
			return this;
		}
	}

	/*
	 * Date: 7 Aug 2021-----------------------------------------------------------
	 * Time created: 17:40:51---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * InternalEvaluator.java------------------------------------------------------
	 * Class name: Group------------------------------------------------
	 */
	/**
	 * Represents a Grouped expression such as 3*(5+2). Parenthesis are exclusively
	 * used to denote the syntax for grouped expressions.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 * @see mathaid.calculator.base.parser.CommonSyntax#precedenceDirector1()
	 * @see mathaid.calculator.base.parser.CommonSyntax#precedenceDirector2()
	 */
	private static class Group extends EvaluatableGroup {

		/*
		 * Date: 7 Aug 2021-----------------------------------------------------------
		 * Time created: 17:46:23---------------------------------------------------
		 */
		/**
		 * Creates a group that isolates the argument.
		 * 
		 * @param content the content of the group which is a child of {@code this}.
		 */
		public Group(EvaluatableExpression content) {
			super(content);
		}

		/*
		 * Most Recent Date: 7 Aug 2021-----------------------------------------------
		 * Most recent time created: 17:47:19--------------------------------------
		 */
		/**
		 * Returns the ( character as a {@code String}.
		 * 
		 * @return "(" as a {@code String}.
		 */
		@Override
		public String name() {
			return "(";
		}

		/*
		 * Most Recent Date: 7 Aug 2021-----------------------------------------------
		 * Most recent time created: 17:48:28--------------------------------------
		 */
		/**
		 * Appends the left parenthesis, then calls the content of this group's
		 * {@link EvaluatableExpression#toString(StringBuilder) toString} method.
		 * 
		 * @param sb {@inheritDoc}
		 */
		@Override
		public void toString(StringBuilder sb) {
			sb.append("\\left(");
			getContent().toString(sb);
			sb.append("\\right)");
		}

		/*
		 * Most Recent Date: 7 Aug 2021-----------------------------------------------
		 * Most recent time created: 17:52:16--------------------------------------
		 */
		/**
		 * Calls the
		 * {@link mathaid.calculator.base.parser.expression.EvaluatableExpression#evaluate()}
		 * method of the content of {@code this}.
		 * 
		 * @return the result of the content of this group.
		 */
		@Override
		public EvaluatableExpression evaluate() {
			return getContent().evaluate();
		}
	}

	/*
	 * Date: 7 Aug 2021-----------------------------------------------------------
	 * Time created: 17:55:36---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * InternalEvaluator.java------------------------------------------------------
	 * Class name: NameParselet------------------------------------------------
	 */
	/**
	 * A {@code Parselet} object for evaluating names.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 *
	 */
	private static class NameParselet
			implements Parselet<String, EvaluatableExpression, PrattParser<EvaluatableExpression>> {
		/*
		 * Most Recent Date: 7 Aug 2021-----------------------------------------------
		 * Most recent time created: 18:02:43--------------------------------------
		 */
		/**
		 * Returns a {@link Name}.
		 * 
		 * @param alreadyParsedLeft  can be null.
		 * @param yetToBeParsedToken the currently parsed token.
		 * @param parser             an be null.
		 * @return a {@link Name}.
		 */
		@Override
		public EvaluatableExpression parse(EvaluatableExpression alreadyParsedLeft, Token<String> yetToBeParsedToken,
				PrattParser<EvaluatableExpression> parser) {
			return new Name(yetToBeParsedToken.getName());
		}

	}

	/*
	 * Date: 7 Aug 2021-----------------------------------------------------------
	 * Time created: 18:04:08---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * InternalEvaluator.java------------------------------------------------------
	 * Class name: OperatorParselet------------------------------------------------
	 */
	/**
	 * A {@code Parselet} object for evaluating infix operators such as binary
	 * operators (+ * - /).
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 *
	 */
	private static class OperatorParselet
			implements Parselet<String, EvaluatableExpression, PrattParser<EvaluatableExpression>> {
		/*
		 * Most Recent Date: 7 Aug 2021-----------------------------------------------
		 * Most recent time created: 18:05:08--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @param alreadyParsedLeft  {@inheritDoc}
		 * @param yetToBeParsedToken {@inheritDoc}
		 * @param parser             {@inheritDoc}
		 * @return {@inheritDoc}
		 */
		@Override
		public EvaluatableExpression parse(EvaluatableExpression alreadyParsedLeft, Token<String> yetToBeParsedToken,
				PrattParser<EvaluatableExpression> parser) {
			int p = yetToBeParsedToken.getType().precedence().getPrecedence() - (right ? 1 : 0);
			EvaluatableExpression right = parser.parse(p);
			return new Operator(alreadyParsedLeft, yetToBeParsedToken.getName(), right);
		}

		/*
		 * Date: 8 Aug 2021----------------------------------------------------------- 
		 * Time created: 09:10:59--------------------------------------------------- 
		 */
		/**
		 * Initialises a {@code OperatorParselet} object with a check to specify
		 * associativity.
		 * 
		 * @param right {@code true} for right associative and {@code false} for left
		 *              associative.
		 */
		public OperatorParselet(boolean right) {
			this.right = right;
		}
		
		/**
		 * A check for right associativity. {@code true} for right associative and
		 * {@code false} for left associative.
		 */
		private final boolean right;

	}

	/*
	 * Date: 7 Aug 2021-----------------------------------------------------------
	 * Time created: 18:06:32---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * InternalEvaluator.java------------------------------------------------------
	 * Class name: FunctionParselet------------------------------------------------
	 */
	/**
	 * A {@code Parselet} object for evaluating functions.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 *
	 */
	private static class FunctionParselet
			implements Parselet<String, EvaluatableExpression, PrattParser<EvaluatableExpression>> {
		/*
		 * Most Recent Date: 7 Aug 2021-----------------------------------------------
		 * Most recent time created: 18:06:55--------------------------------------
		 */
		/**
		 * Parses the token argument and searches for a closing symbol specified by
		 * {@link CommonSyntax#functionClose()}. If it is not found (then that means
		 * that this function contains arguments), all the arguments found are added to
		 * a list. The arguments must be separated by a symbol specified by
		 * {@link CommonSyntax#functionArgDelimiter()}. After the last argument is added
		 * to the list, the closing symbol is consumed and a {@link Function} object is
		 * returned.
		 * 
		 * @param alreadyParsedLeft  {@inheritDoc}
		 * @param yetToBeParsedToken {@inheritDoc}
		 * @param parser             {@inheritDoc}
		 * @return a {@code Function} object
		 */
		@Override
		public EvaluatableExpression parse(EvaluatableExpression alreadyParsedLeft, Token<String> yetToBeParsedToken,
				PrattParser<EvaluatableExpression> parser) {
			List<EvaluatableExpression> args = new ArrayList<>();

			CommonSyntax<?, ?> s = parser.syntax();
			Type<String> functionEndType = s.getType(s.functionClose());
			if (!parser.match(functionEndType)) {
				do {
					args.add(parser.parse());
				} while (parser.match(s.getType(s.functionArgDelimiter())));
				parser.consume(functionEndType);
			}
			return new Function(alreadyParsedLeft, args);
		}

	}

	/*
	 * Date: 7 Aug 2021-----------------------------------------------------------
	 * Time created: 18:16:03---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * InternalEvaluator.java------------------------------------------------------
	 * Class name: PrefixParselet------------------------------------------------
	 */
	/**
	 * A {@code Parselet} object for evaluating unary prefix operators.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 *
	 */
	private static class PrefixParselet
			implements Parselet<String, EvaluatableExpression, PrattParser<EvaluatableExpression>> {
		/*
		 * Most Recent Date: 7 Aug 2021-----------------------------------------------
		 * Most recent time created: 18:16:33--------------------------------------
		 */
		/**
		 * Calls the {@link PrattParser#parse(int) parse} method with the given token's
		 * precedence as it's argument and returns a {@link Prefix} object.
		 * 
		 * @param alreadyParsedLeft  {@inheritDoc}
		 * @param yetToBeParsedToken {@inheritDoc}
		 * @param parser             {@inheritDoc}
		 * @return a {@code Prefix} object.
		 */
		@Override
		public EvaluatableExpression parse(EvaluatableExpression alreadyParsedLeft, Token<String> yetToBeParsedToken,
				PrattParser<EvaluatableExpression> parser) {
			EvaluatableExpression right = parser.parse(yetToBeParsedToken.getType().precedence().getPrecedence());
			return new Prefix(yetToBeParsedToken.getName(), right);
		}

	}

	/*
	 * Date: 7 Aug 2021-----------------------------------------------------------
	 * Time created: 18:23:59---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * InternalEvaluator.java------------------------------------------------------
	 * Class name: PostfixParselet------------------------------------------------
	 */
	/**
	 * A {@code Parselet} object for evaluating unary postfix operators.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 *
	 */
	private static class PostfixParselet
			implements Parselet<String, EvaluatableExpression, PrattParser<EvaluatableExpression>> {
		/*
		 * Most Recent Date: 7 Aug 2021-----------------------------------------------
		 * Most recent time created: 18:24:24--------------------------------------
		 */
		/**
		 * Returns a {@code Postfix} object.
		 * 
		 * @param alreadyParsedLeft  {@inheritDoc}
		 * @param yetToBeParsedToken {@inheritDoc}
		 * @param parser            can be left as {@code null}.
		 * @return {@inheritDoc}
		 */
		@Override
		public EvaluatableExpression parse(EvaluatableExpression alreadyParsedLeft, Token<String> yetToBeParsedToken,
				PrattParser<EvaluatableExpression> parser) {
			return new Postfix(alreadyParsedLeft, yetToBeParsedToken.getName());
		}

	}

	/*
	 * Date: 7 Aug 2021-----------------------------------------------------------
	 * Time created: 18:25:10---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * InternalEvaluator.java------------------------------------------------------
	 * Class name: GroupParselet------------------------------------------------
	 */
	/**
	 * A {@code Parselet} object for evaluating expressions within parenthesis.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 *
	 */
	private static class GroupParselet
			implements Parselet<String, EvaluatableExpression, PrattParser<EvaluatableExpression>> {
		/*
		 * Most Recent Date: 7 Aug 2021-----------------------------------------------
		 * Most recent time created: 18:26:17--------------------------------------
		 */
		/**
		 * Calls the given {@code PrattParser} object's parse no-arg method then
		 * consumes the closing symbol specified by
		 * {@link CommonSyntax#precedenceDirector2()} and returns a {@code Group}
		 * object.
		 * 
		 * @param alreadyParsedLeft  {@inheritDoc}
		 * @param yetToBeParsedToken {@inheritDoc}
		 * @param parser             {@inheritDoc}
		 * @return {@inheritDoc}
		 */
		@Override
		public EvaluatableExpression parse(EvaluatableExpression alreadyParsedLeft, Token<String> yetToBeParsedToken,
				PrattParser<EvaluatableExpression> parser) {
			EvaluatableExpression content = parser.parse();
			/*
			 * A precedence director is any token that a syntax may use for delimiting an
			 * expression to force a certain order of precedence. For example: a * (5 + b),
			 * in this expression, '(' and ')' are precedence directors because they change
			 * the order of operations which should have been multiplication first before
			 * addition to addition before multiplication
			 */
			parser.consume(parser.syntax().getType(parser.syntax().precedenceDirector2()));
			return new Group(content);
		}

	}

	/*
	 * Date: 7 Aug 2021-----------------------------------------------------------
	 * Time created: 18:29:29---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * InternalEvaluator.java------------------------------------------------------
	 * Class name: InternalLexer------------------------------------------------
	 */
	/**
	 * An object akin to {@link mathaid.calculator.base.parser.Lexer}. However
	 * unlike the {@code Lexer} class, this supports hex digits and binary
	 * floating-point exponents.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 * @see Lexer
	 */
	private static class InternalLexer implements Iterator<mathaid.calculator.base.parser.Token<String>> {

		/*
		 * Date: 7 Aug 2021-----------------------------------------------------------
		 * Time created: 18:33:09---------------------------------------------------
		 * Package:
		 * mathaid.calculator.base------------------------------------------------
		 * Project: LatestPoject2------------------------------------------------ File:
		 * InternalEvaluator.java------------------------------------------------------
		 * Class name: Token------------------------------------------------
		 */
		/**
		 * Basic concrete implementation provider for the
		 * {@code mathaid.calculator.base.parser.Token} interface.
		 * 
		 * @author Oruovo Anthony Etineakpopha
		 * @email tonyoruovo@gmail.com
		 *
		 */
		private static class Token implements mathaid.calculator.base.parser.Token<String> {
			/**
			 * The {@code Type} of {@code this}
			 */
			private Type<String> t;
			/**
			 * The String object that this {@code mathaid.calculator.base.parser.Token}
			 * wraps.
			 */
			private String name;

			/*
			 * Date: 7 Aug 2021-----------------------------------------------------------
			 * Time created: 18:35:37---------------------------------------------------
			 */
			/**
			 * Initialises a {@code Token} object with a name and a given {@code Type}.
			 * 
			 * @param name the name of this token.
			 * @param type the type of this token.
			 */
			public Token(String name, Type<String> type) {
				this.name = name;
				this.t = type;
			}

			/*
			 * Most Recent Date: 7 Aug 2021-----------------------------------------------
			 * Most recent time created: 18:36:06--------------------------------------
			 */
			/**
			 * Returns a {@code Type} of type {@code String}.
			 * 
			 * @return a {@code Type} of type {@code String}.
			 */
			@Override
			public Type<String> getType() {
				return t;
			}

			/*
			 * Most Recent Date: 7 Aug 2021-----------------------------------------------
			 * Most recent time created: 18:36:27--------------------------------------
			 */
			/**
			 * Returns the actual string name associated with this token.
			 * 
			 * @return a {@code String} object which was wrapped as a generic.
			 */
			@Override
			public String getName() {
				return name;
			}

			/*
			 * Most Recent Date: 7 Aug 2021-----------------------------------------------
			 * Most recent time created: 18:36:50--------------------------------------
			 */
			/**
			 * Return {@link #getName()}, a colon, a space and the string of
			 * {@link #getType()}.
			 * 
			 * @return the string representation of {@code this}.
			 */
			@Override
			public String toString() {
				return name + ": " + t;
			}

		}

		private int index;
		private String text;
		private final CommonSyntax<EvaluatableExpression, PrattParser<EvaluatableExpression>> cs;

		private InternalLexer(String text) {
			this.text = text;
			index = 0;
			cs = buildSyntax();
		}

		@Override
		public boolean hasNext() {
			return true;
		}

		/*
		 * Most Recent Date: 28 Jun 2021-----------------------------------------------
		 * Most recent time created: 11:06:35--------------------------------------
		 * TODO: I just realised that I can check whether a number or expression is
		 * viable at this point, instead of the multiple checks that I already have at
		 * the expression.s toString and evaluate methods. Also I just realised that I
		 * can have all tokens at each point be added to a list or better still a
		 * sentence exception object that has a list in its field so it can tell the
		 * user where the error is within his formula
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return
		 */
		@Override
		public mathaid.calculator.base.parser.Token<String> next() {
			while (index < text.length()) {
				char c = text.charAt(index++);
				//we can check for the lambda '->' here
				if (cs.getPunctuatorsAndDelimiters().contains(c))
					return new Token(String.valueOf(c), cs.getType(c));
				else if (cs.getLetters().contains(c)) {
					int start = index - 1;
					while (index < text.length()) {
						if (!cs.getLetters().contains(c))
//						if (!cs.getLetters().contains(text.charAt(index)))
							break;
						index++;
					}
					String name = text.substring(start, index);
					return new Token(name, cs.getNameType());
				} else if (cs.getDigits().contains(c)) {
					int start = index - 1;
					while (index < text.length()) {
						char ch = text.charAt(index);
						/*
						 * if this lexer encounters a non-digit and that non-digit is not an exponent
						 * character (e or p) stop parsing the string and return the word as number
						 */
						if ((ch != 'e' && ch != 'p') && ch != '.' && (!cs.getDigits().contains(ch)))
							// if the previous character is an exponent operator
							if ((text.charAt(index - 1) == 'e' || text.charAt(index - 1) == 'p')
									&& (ch == '-' || ch == '+'))
								;
							else
								break;
						index++;
					}

					String num = text.substring(start, index);
					if (!isANumber(num, defaultSetting().getBitLength(), defaultSetting().getRadix()))
						throw new RuntimeException(num + " is not a number in base " + defaultSetting().getRadix());
					return new Token(num, cs.getNameType());
				}

			}
			return new Token("", CommonSyntax.COMMON_TYPES.get(CommonSyntax.EOF));
		}

	}

	/*
	 * Date: 7 Aug 2021-----------------------------------------------------------
	 * Time created: 18:37:35--------------------------------------------
	 */
	/**
	 * Builds a {@code CommonSyntax} object synonymous with
	 * {@link CommonSyntax#COMMON_TYPES}. However this supports hex digits and
	 * binary floating points
	 * 
	 * @return valid {@code CommonSyntax} object.
	 * @see CommonSyntax#COMMON_TYPES
	 */
	static CommonSyntax<EvaluatableExpression, PrattParser<EvaluatableExpression>> buildSyntax() {
		CommonSyntax.Builder<EvaluatableExpression> builder = new CommonSyntax.Builder<>(true);// accept hex digits

		builder.registerPunctuatorAndDelimeter('(');
		builder.registerPunctuatorAndDelimeter(')');
		builder.registerPunctuatorAndDelimeter('[');
		builder.registerPunctuatorAndDelimeter(']');
		builder.registerPunctuatorAndDelimeter('.');
		builder.registerPunctuatorAndDelimeter(',');
		builder.registerPunctuatorAndDelimeter('-');
		builder.registerPunctuatorAndDelimeter('+');
		builder.registerPunctuatorAndDelimeter('*');
		builder.registerPunctuatorAndDelimeter('/');
		builder.registerPunctuatorAndDelimeter('^');
		builder.registerPunctuatorAndDelimeter('!');
		builder.registerPunctuatorAndDelimeter('\'');
		builder.registerArgDelimiter(',');
		builder.registerPrecedenceDirectors(new Character[] { '(', ')', });
		builder.registerFunctionDelimiters(new Character[] { '[', ']', });

		/*
		 * ABCDEF is not included because they are used for hexadecimal digits
		 */
		String chars = "GHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		for (int i = 0; i < chars.length(); i++)
			builder.registerLetter(chars.charAt(i));
		chars = "1234567890ABCDEF";
		for (int i = 0; i < chars.length(); i++)
			builder.registerDigit(chars.charAt(i));

		builder.registerTypes(CommonSyntax.COMMON_TYPES.values());
		builder.registerNameType(CommonSyntax.COMMON_TYPES.get(CommonSyntax.NAME));

		builder.registerPrefixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.NAME), new NameParselet());
		// expressions like b * (a + c)
		builder.registerPrefixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.LEFT_PARENTHESIS),
				new GroupParselet());
		// expressions like Sin(x)
		builder.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.LEFT_BRACKET), new FunctionParselet());
		builder.registerPrefixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.PLUS), new PrefixParselet());
		builder.registerPrefixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.MINUS), new PrefixParselet());

		builder.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.PLUS), new OperatorParselet(false));
		builder.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.MINUS), new OperatorParselet(false));
		builder.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.ASTERISK),
				new OperatorParselet(false));
		builder.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.FORWARD_SLASH),
				new OperatorParselet(false));
		builder.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.CARET), new OperatorParselet(true));
		builder.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.BANG), new PostfixParselet());
		// for numbers starting with a point such as .0983912
		builder.registerPrefixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.PERIOD), new PrefixParselet());
		// for numbers that have a decimal point in between such as 123.456789
		builder.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.PERIOD), new OperatorParselet(false));
		builder.registerInfixParselet(CommonSyntax.COMMON_TYPES.get(CommonSyntax.QUOTE), new PostfixParselet());

		return builder.build();
	}

	/*
	 * Most Recent Date: 20 Jun 2021-----------------------------------------------
	 * Most recent time created: 10:39:19--------------------------------------
	 */
	/**
	 * Evaluates the input argument and returns a {@code DataText} object with the
	 * respective TeX and raw string contained within the object that is returned.
	 * <p>
	 * This method wraps {@link mathaid.calculator.base.parser.Evaluator#evaluate()}
	 * for the {@code Programmer} class.
	 * </p>
	 * 
	 * @param argument the source text to be evaluated
	 * @return a {@code DataText} that represents the answer to the argument.
	 */
	public DataText evaluate(String argument) {
		mathaid.calculator.base.parser.Parser<String, EvaluatableExpression, PrattParser<EvaluatableExpression>> parser = new PrattParser<>(
				new InternalLexer(argument), buildSyntax());
		EvaluatableExpression expression = parser.parse();
		StringBuilder sb = new StringBuilder();
		expression = expression.evaluate();
		expression.toString(sb);
		synchronized (this) {
			return new DataText(sb.toString(), expression.name());
		}
	}

	/*
	 * Date: 7 Aug 2021-----------------------------------------------------------
	 * Time created: 19:07:39--------------------------------------------
	 */
	/**
	 * Returns the {@code AngleUnit} given by the argument (which is one of the
	 * constants in the {@code Settings} class that has the prefix trig attached to
	 * it's name).
	 * 
	 * @param trig one of the values given by the constants in the {@code Settings}
	 *             class whose name begins with the prefix trig.
	 * @return an {@code AngleUnit} object.
	 * @throws RuntimeException specifically a
	 *                          {@code java.lang.IllegalArgumentException} if the
	 *                          argument is not recognised.
	 */
	private static AngleUnit getTrig(int trig) {
		switch (trig) {
		case TRIG_DEG:
			return AngleUnit.DEG;
		case TRIG_RAD:
			return AngleUnit.RAD;
		case TRIG_GRAD:
			return AngleUnit.GRAD;
		default:
		}
		new mathaid.IllegalArgumentException(ExceptionMessage.UNKNOWN_GEOMETRY);
		throw new IllegalArgumentException(ExceptionMessage.UNKNOWN_GEOMETRY.getSourceMessage());
	}

	/*
	 * Date: 7 Aug 2021-----------------------------------------------------------
	 * Time created: 19:17:18--------------------------------------------
	 */
	/**
	 * Returns a {@code BinaryRep} object appropriate for the given argument which
	 * is specified by one of the constants in the {@code Settings} class whose name
	 * has the prefix rep.
	 * 
	 * @param rep one of the values given by the constants in the {@code Settings}
	 *            class whose name begins with the prefix rep.
	 * @return a {@code BinaryRep} object.
	 */
	private static BinaryRep getRep(int rep) {
		switch (rep) {
		case REP_SMR:
			return BinaryRep.SMR;
		case REP_ONEC:
			return BinaryRep.ONE_C;
		case REP_TWOC:
			return BinaryRep.TWO_C;
		case REP_EXCESS_N:
			return BinaryRep.EXCESS_K;
		case REP_NEGABINARY:
			return BinaryRep.NEGABINARY;
		case REP_UNSIGNED:
			return BinaryRep.UNSIGNED;
		case REP_MATH:
		default:
			return BinaryRep.MATH;
		}
	}

	/*
	 * Date: 7 Aug 2021-----------------------------------------------------------
	 * Time created: 19:21:45--------------------------------------------
	 */
	/**
	 * Returns a {@code Radix} object appropriate for the given argument which is
	 * specified by the int value for that base.
	 * 
	 * @param r one of the values given by the constants 2, 8, 10, 16.
	 * @return a {@code Radix} object.
	 */
	private static Radix getRadix(int r) {
		switch (r) {
		case 2:
			return Radix.BIN;
		case 8:
			return Radix.OCT;
		case 16:
			return Radix.HEX;
		default:
			return Radix.DEC;
		}
	}

	/*
	 * Date: 7 Aug 2021-----------------------------------------------------------
	 * Time created: 19:23:09--------------------------------------------
	 */
	/**
	 * Checks if the given {@code String} is an integer or floating point number in
	 * the specified bits and radix and returns {@code true} if it is or
	 * {@code false} if otherwise.
	 * 
	 * @param s     the number to be checked.
	 * @param bits  the number of supposed bits the value s represented in.
	 * @param radix the radix in which the value is represented in.
	 * @return {@code true} if the string argument is a value in the specified radix
	 *         and bit length.
	 */
	private static boolean isANumber(String s, int bits, int radix) {
		if (isNumber(s, radix))
			return true;
		try {
			FloatPoint.toFloat(s, BitLength.valueOf(bits), getRadix(radix),
					Precision.valueOf(bits).checkNormalised(s));
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/*
	 * Date: 8 Aug 2021----------------------------------------------------------- 
	 * Time created: 09:36:43--------------------------------------------------- 
	 */
	/**
	 * Sole Constructor.
	 */
	public InternalEvaluator() {
	}
}
