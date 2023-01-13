/**
 * 
 */
package mathaid.calculator.base;

import static mathaid.calculator.base.Calculator.ABS;
import static mathaid.calculator.base.Calculator.ACOS;
import static mathaid.calculator.base.Calculator.ACOSH;
import static mathaid.calculator.base.Calculator.ACOT;
import static mathaid.calculator.base.Calculator.ACOTH;
import static mathaid.calculator.base.Calculator.ACSC;
import static mathaid.calculator.base.Calculator.ACSCH;
import static mathaid.calculator.base.Calculator.ASEC;
import static mathaid.calculator.base.Calculator.ASECH;
import static mathaid.calculator.base.Calculator.ASIN;
import static mathaid.calculator.base.Calculator.ASINH;
import static mathaid.calculator.base.Calculator.ATAN;
import static mathaid.calculator.base.Calculator.ATANH;
import static mathaid.calculator.base.Calculator.CBRT;
import static mathaid.calculator.base.Calculator.CONJ;
import static mathaid.calculator.base.Calculator.COS;
import static mathaid.calculator.base.Calculator.COSH;
import static mathaid.calculator.base.Calculator.COT;
import static mathaid.calculator.base.Calculator.COTH;
import static mathaid.calculator.base.Calculator.CSC;
import static mathaid.calculator.base.Calculator.CSCH;
import static mathaid.calculator.base.Calculator.DEFAULT_PRECISION;
import static mathaid.calculator.base.Calculator.DIFF;
import static mathaid.calculator.base.Calculator.E;
import static mathaid.calculator.base.Calculator.ERF;
import static mathaid.calculator.base.Calculator.EXP;
import static mathaid.calculator.base.Calculator.GAMMA;
import static mathaid.calculator.base.Calculator.GCD;
import static mathaid.calculator.base.Calculator.INFINITY;
import static mathaid.calculator.base.Calculator.INT;
import static mathaid.calculator.base.Calculator.LCM;
import static mathaid.calculator.base.Calculator.LN;
import static mathaid.calculator.base.Calculator.LOG;
import static mathaid.calculator.base.Calculator.LOG10;
import static mathaid.calculator.base.Calculator.LOG2;
import static mathaid.calculator.base.Calculator.MAX;
import static mathaid.calculator.base.Calculator.MIN;
import static mathaid.calculator.base.Calculator.MOD;
import static mathaid.calculator.base.Calculator.NCR;
import static mathaid.calculator.base.Calculator.NINT;
import static mathaid.calculator.base.Calculator.NPR;
import static mathaid.calculator.base.Calculator.PI;
import static mathaid.calculator.base.Calculator.POLYGAMMA;
import static mathaid.calculator.base.Calculator.SEC;
import static mathaid.calculator.base.Calculator.SECH;
import static mathaid.calculator.base.Calculator.SIGN;
import static mathaid.calculator.base.Calculator.SIN;
import static mathaid.calculator.base.Calculator.SINH;
import static mathaid.calculator.base.Calculator.SQRT;
import static mathaid.calculator.base.Calculator.SUM;
import static mathaid.calculator.base.Calculator.TAN;
import static mathaid.calculator.base.Calculator.TANH;
import static mathaid.calculator.base.Settings.DEC_ENG;
import static mathaid.calculator.base.Settings.DEC_FIX;
import static mathaid.calculator.base.Settings.DEC_OFF;
import static mathaid.calculator.base.Settings.DEC_SCI;
import static mathaid.calculator.base.Settings.FRAC_FRAC;
import static mathaid.calculator.base.Settings.FRAC_MIXED;
import static mathaid.calculator.base.Settings.FRAC_OFF;
import static mathaid.calculator.base.Settings.TRIG_DEG;
import static mathaid.calculator.base.Settings.TRIG_RAD;
import static mathaid.calculator.base.Settings.defaultSetting;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mathaid.calculator.base.converter.AngleUnit;
import mathaid.calculator.base.parser.CommonSyntax;
import mathaid.calculator.base.parser.Lexer;
import mathaid.calculator.base.parser.PrattParser;
import mathaid.calculator.base.parser.Token;
import mathaid.calculator.base.parser.Type;
import mathaid.calculator.base.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.parser.expression.Expression;
import mathaid.calculator.base.parser.expression.NameExpression;
import mathaid.calculator.base.parser.parselet.Parselet;
import mathaid.calculator.base.util.Arith;
import mathaid.calculator.base.util.Constants;
import mathaid.calculator.base.util.Utility;
import mathaid.calculator.base.value.BigFraction;
import mathaid.designpattern.Observer;

/*
 * Date: 23 Apr 2021----------------------------------------------------------- 
 * Time created: 14:21:18---------------------------------------------------  
 * Package: mathaid.calculator.base------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: Output.java------------------------------------------------------ 
 * Class name: Output------------------------------------------------ 
 */
/**
 * An object that manages formatting of strings that are meant for display on
 * this {@code Calculator} object's screen. The string formatted appear - in the
 * source string - in TeX notation.
 * <p>
 * Internally, the calculator uses a private evaluator to evaluate expressions,
 * but this private evaluator only does a half-finished job on some expressions
 * (most notably 1.7! - 5.6 * 3.2) so that when the result is sent to the
 * output, it may be undefined. This is where this class is needed. It takes the
 * half-finished expression and further evaluates it. So from the example, the
 * result returned from the internal evaluator of the scientific class may be
 * {@code 1.7! - 17.92}. This object may finalise this expression as
 * {@code -16.375314154149406235039406296808}, which by the way is the correct
 * answer.
 * </p>
 * <p>
 * The behaviour of this object largely depends on the state of the
 * {@code Settings} class. Unlike the {@code InternalEvaluator} class, the
 * evaluator here can evaluate symbolic values such as x+2*y.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha <b>NOTE:</b> Only lowercase 'e' is
 *         allowed for exponents
 */
public class Output implements Observer<DataText> {

	/*
	 * Date: 8 Aug 2021-----------------------------------------------------------
	 * Time created: 05:51:18---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * Output.java------------------------------------------------------ Class name:
	 * Name------------------------------------------------
	 */
	/**
	 * Represents constants and numbers. Constant names and bound variables are
	 * expected to be in lower case. Free variables are expected to be in upper
	 * case.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 *
	 */
	private static class Name implements EvaluatableExpression {
		/*
		 * Date: 8 Aug 2021-----------------------------------------------------------
		 * Time created: 05:57:56---------------------------------------------------
		 */
		/**
		 * Constructs a unique object for this class with the argument representing that
		 * in an expression. Only numbers and latin alphabets are allowed.
		 * 
		 * @param name a number, a named constant or a variable.
		 */
		public Name(final String name) {
			this.name = name;
		}

		/*
		 * Most Recent Date: 8 Aug 2021-----------------------------------------------
		 * Most recent time created: 05:59:03--------------------------------------
		 */
		/**
		 * Returns the source unformatted string of this expression.
		 * 
		 * @return the source string of this expression.
		 */
		@Override
		public String name() {
			return name;
		}

		/*
		 * Most Recent Date: 8 Aug 2021-----------------------------------------------
		 * Most recent time created: 06:03:23--------------------------------------
		 */
		/**
		 * Appends the source string in a formatted TeX form to the specified
		 * {@code StringBuilder} object. The value of the string that will be appended
		 * to the {@code StringBuilder} may change if {@link #evaluate()} is
		 * non-idempotent and is called before this method.
		 * 
		 * @param sb a {@code String Builder} object that may have a string of valid
		 *           characters in TeX form.
		 */
		@Override
		public void toString(StringBuilder sb) {
			boolean isNumber = Utility.isNumber(name());
			String name_number = null;
			if (isNumber) {
				FractionalDetailsList fdl = new FractionalDetailsList(new BigDecimal(name()));
				Settings s = defaultSetting();

				switch (s.getFraction()) {
				case FRAC_FRAC:
					name_number = fdl.getExpression().getTeXString();
					break;
				case FRAC_MIXED:
					name_number = fdl.getMixedFraction().getTeXString();
					break;
				case FRAC_OFF:
				default:
					DecimalDetailsList ddl = new DecimalDetailsList(name());
					switch (s.getDecimalMode()) {
					case DEC_ENG:
						name_number = ddl.getSISuffixExpression().getTeXString();
						break;
					case DEC_FIX:
						name_number = ddl.getFixExpression().getTeXString();
						break;
					case DEC_SCI:
						name_number = ddl.getSciExpression().getTeXString();
						break;
					case DEC_OFF:
					default:
						name_number = ddl.getExpression().getTeXString();
					}
				}
			} else if (name().equals(INFINITY))
				name_number = "\\infty";
			else if (name().equals(PI))
				name_number = "\\pi";
			else if (name().equals(LOG))
				name_number = "ln";
			/*
			 * For place holder bound variables like 'X' or 'Y' that reference actual
			 * numerical, complex or expression values
			 */
			else if (name().length() == 1 && Character.isUpperCase(name().charAt(0)))
				name_number = name();
			else
				name_number = name().toLowerCase();
			switch (name()) {
			case SIN:
			case COS:
			case TAN:
			case SEC:
			case CSC:
			case COT:
			case SINH:
			case COSH:
			case TANH:
			case SECH:
			case CSCH:
			case COTH:
//				sb.append("\\text{" + name_number + "}");
//				name_number = "\\text{" + name_number + "}";
				name_number = "\\" + name_number;
				break;
			default:
				if (!isNumber) {
					/*
					 * All Bound variable that make it to this point have been turned to numbers by
					 * now.
					 */
					name_number = "\\text{" + name().toLowerCase() + "}";
					break;
				}
			}

			sb.append(name_number);
		}

		/*
		 * Most Recent Date: 8 Aug 2021-----------------------------------------------
		 * Most recent time created: 06:53:37--------------------------------------
		 */
		/**
		 * Returns the value of a bound variable or a constant if this is a variable or
		 * constant else just returns {@code this}.
		 * 
		 * @return {@code this} or some value that {@code this} represents. This
		 *         operation may be idempotent.
		 */
		@Override
		public EvaluatableExpression evaluate() {
			final int scale = DEFAULT_PRECISION;// A bigger precision than is necessary for accurate calculations
			switch (name()) {// TODO: add more variables such as those created by the user
			case PI:
			case "π":// Just for the fun of it. It won't really go through the lexer
				return new Name(Constants.pi(scale).toString());
			case E:
				return new Name(Constants.e(scale).toString());
			default:
			}
			return this;// leaf node
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Name))
				return false;
			return name.equals(((Name) o).name);
		}

		@Override
		public int hashCode() {
			return name.hashCode();
		}

		@Override
		public String toString() {
			return name();
		}

		/**
		 * The string source of the expression which also represents the name.
		 */
		private final String name;

	}

	/*
	 * Date: 8 Aug 2021-----------------------------------------------------------
	 * Time created: 06:59:31---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * Output.java------------------------------------------------------ Class name:
	 * Operator------------------------------------------------
	 */
	/**
	 * Handles the 5 cardinal binary operators namely: +, -, *, /, ^ and their
	 * respective operations regarding valid operands.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 *
	 */
	private static class Operator extends Name {

		/**
		 * The left operand
		 */
		private final EvaluatableExpression left;
		/**
		 * The right operand
		 */
		private final EvaluatableExpression right;

		/*
		 * Date: 8 Aug 2021-----------------------------------------------------------
		 * Time created: 07:03:13---------------------------------------------------
		 */
		/**
		 * Constructs an {@code EvaluatableExpression} using a left operand and right
		 * operand with the name or symbol specified by the string argument. The left
		 * operand may be null in which case {@code this} is considered a postfix
		 * operator else if the right operand is null then this is considered a prefix
		 * operand, if both is null then a {@code NullPointerException} will be thrown.
		 * 
		 * @param left  the left operand of this operator.
		 * @param name  the name (sign) of this operator.
		 * @param right the right operand of this operator.
		 */
		private Operator(EvaluatableExpression left, String operatorName, EvaluatableExpression right) {
			super(operatorName);
			this.left = left;
			this.right = right;
		}

		/*
		 * Date: 8 Aug 2021-----------------------------------------------------------
		 * Time created: 07:04:12---------------------------------------------------
		 */
		/**
		 * Constructs an {@code EvaluatableExpression} using a left operand and right
		 * operand with the name or symbol specified by the {@code Token} argument. The
		 * left operand may be null in which case {@code this} is considered a postfix
		 * operator else if the right operand is null then this is considered a prefix
		 * operand, if both is null then a {@code NullPointerException} will be thrown.
		 * 
		 * @param left  the left operand of this operator.
		 * @param name  the name (sign) of this operator.
		 * @param right the right operand of this operator.
		 */
		public Operator(EvaluatableExpression left, Token<String> operatorName, EvaluatableExpression right) {
			this(left, operatorName.getName(), right);
		}

		/*
		 * Most Recent Date: 8 Aug 2021-----------------------------------------------
		 * Most recent time created: 07:05:18--------------------------------------
		 */
		/**
		 * Calls the left operand's {@link NameExpression#toString(StringBuilder)
		 * toString} method, appends a formatted version of {@link #name()} and then
		 * calls the right operand's {@link NameExpression#toString(StringBuilder)
		 * toString} method.
		 * <p>
		 * The value of the string that will be appended to the {@code StringBuilder}
		 * may change if {@link #evaluate()} is non-idempotent and is called before this
		 * method.
		 * </p>
		 * 
		 * @param sb {@inheritDoc}
		 */
		@Override
		public void toString(StringBuilder sb) {
			Settings s = defaultSetting();
			// for test cases TODO: slows program
//			st = Utility.serializeAndDeserializeObject(sb);
			switch (name()) {
			case "/":
				StringBuilder st;
				st = new StringBuilder(sb.toString());//might be faster?
				// Log[5]/Log[10]
				if (left.name().equals(LOG) && right.name().equals(LOG)) {
					Function fx = (Function) left;
					Function fy = (Function) right;
					if (fx.args.size() == 1 && fy.args.size() == 1) {
						sb.append(LOG.toLowerCase());
						sb.append("_{");
						fy.args.get(0).toString(sb);
						sb.append('}');
						sb.append("\\,");
						fx.args.get(0).toString(sb);
						return;
					}
				}

				left.toString(st);

				String str = st.toString();
				boolean isFracFormatted = str.contains("\\frac{")
						&& str.replaceFirst("[1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz+-^"
								+ s.getDecimalPoint() + s.getIntDivider() + s.getFracDivider()
								+ s.getMultiplicationSign() + s.getDivisionSign() + "]", "").lastIndexOf("}{}") == 0;

				if (!isFracFormatted) {
					sb.append("\\frac{");
					left.toString(sb);
					sb.append("}{");
					right.toString(sb);
					sb.append("}");
					return;
				}
				left.toString(sb);
				sb.append(s.getDivisionSign());
				right.toString(sb);
				return;
			case "+":
			case "-":
				break;
			case "*":
				// for symja expressions such as 5*x or I*3.5
				if ((left instanceof Name && !Utility.isNumber(left.name()))
						|| (right instanceof Name && !Utility.isNumber(right.name()))) {
					if (Utility.isNumber(right.name())) {
						right.toString(sb);
						left.toString(sb);
						return;
					} else if (left.name().length() == 1 && right.name().length() == 1) {
						if (right.name().compareTo(left.name()) < 0) {
							right.toString(sb);
							left.toString(sb);
							return;
						}
					}
					left.toString(sb);
					right.toString(sb);
					return;
				} else if (left instanceof Group) {// (x+y)*z
					left.toString(sb);
					right.toString(sb);
					return;
				}
				left.toString(sb);
				sb.append(s.getMultiplicationSign());
				sb.append(" ");
				right.toString(sb);
				return;
			case "^":
				/*
				 * x^y, x^0.5, x^(3/4), x^(1/5), x^(x/y), x^-3
				 */
				if (right.name().equals("/")) {
					Operator op = (Operator) right;
					if (op.right instanceof Name && Utility.isNumber(op.right.name())) {
						boolean rightIsInt = Utility.isInteger(new BigDecimal(op.right.name()));
						if (rightIsInt) {
							sb.append("\\sqrt[");
							op.right.toString(sb);
							sb.append(']');
							sb.append("{");
							left.toString(sb);
							sb.append('^');
							sb.append('{');
							op.left.toString(sb);
							sb.append("}}");
							return;
						}
					}
				}
				left.toString(sb);
				sb.append(name());
				sb.append("{");
				right.toString(sb);
				sb.append("}");
				return;
			default:
			}
			left.toString(sb);
			sb.append(name());
			right.toString(sb);
		}

		/*
		 * Most Recent Date: 8 Aug 2021-----------------------------------------------
		 * Most recent time created: 07:15:14--------------------------------------
		 */
		/**
		 * Calculates the result of the operation between the 2 operands and returns it.
		 * 
		 * @return an expression resulting from {@code this} applied to both it's
		 *         operands. This action may be an idempotent operation.
		 */
		@Override
		public EvaluatableExpression evaluate() {
			EvaluatableExpression left = this.left.evaluate();
			EvaluatableExpression right = this.right.evaluate();
			/*
			 * throw exception if is complex returns false and there is a complex digit in
			 * the expression
			 */
			Settings s = defaultSetting();
			switch (name()) {
			case "+":
				if (left instanceof Name && Utility.isNumber(left.name()))
					if (right instanceof Name && Utility.isNumber(right.name()))
						return new Name(new BigDecimal(left.name()).add(new BigDecimal(right.name()),
								new MathContext(s.getScale(), RoundingMode.HALF_EVEN)).toPlainString());
				break;
			case "-":
				if (left instanceof Name && Utility.isNumber(left.name()))
					if (right instanceof Name && Utility.isNumber(right.name()))
						return new Name(new BigDecimal(left.name()).subtract(new BigDecimal(right.name()),
								new MathContext(s.getScale(), RoundingMode.HALF_EVEN)).toPlainString());
				break;
			case "/":
				if (left instanceof Name && Utility.isNumber(left.name()))
					if (right instanceof Name && Utility.isNumber(right.name()))
						return new Name(new BigDecimal(left.name()).divide(new BigDecimal(right.name()),
								new MathContext(s.getScale(), RoundingMode.HALF_EVEN)).toPlainString());
				// Log[5]/Log[2]
				if (left instanceof Function && right instanceof Function) {
					if (left.name().equals(LOG) && right.name().equals(LOG)) {
						Function fx = (Function) left;
						Function fy = (Function) right;
						return new Function(new Name(LOG), Arrays.asList(fx, fy)).evaluate();
					}
				}
				break;
			case "*":
				if (left instanceof Name && Utility.isNumber(left.name())) {
					if (right instanceof Name) {
						if (Utility.isNumber(right.name()))
							return new Name(
									new BigDecimal(left.name())
											.multiply(new BigDecimal(right.name()),
													new MathContext(s.getScale(), RoundingMode.HALF_EVEN))
											.toPlainString());
						break;
					}
				}
				break;
			case "^":
				if (left instanceof Name && Utility.isNumber(left.name()))
					if (right instanceof Name && Utility.isNumber(right.name())) {
						/*
						 * Somehow, the commented code Returns only integers for E^2. The bottom one
						 * however is slower
						 */
//						Apfloat n1 = new Apfloat(left.name()), n2 = new Apfloat(right.name());
//						n1 = ApfloatMath.pow(n1, n2);
//						return new Name(new BigDecimal(n1.toString(true)).toPlainString());
						return new Name(Arith.pow(new BigDecimal(left.name()), new BigDecimal(right.name()),
								new MathContext(DEFAULT_PRECISION, RoundingMode.HALF_EVEN)).toPlainString());
					}
				break;
			/*
			 * the period '.' character will be an infix as well as a prefix, and also, no
			 * need to handle it in the 'toString(StringBuilder)' method because even if
			 * isExpression return true the default implementation handles it perfectly fine
			 */
			case ".":
				return new Name(left.name() + name() + right.name());
			/*
			 * for exponents, although this is already covered by the lexer object and is
			 * not part of the syntax
			 */
			case "e":
			default:
			}
			return new Operator(left, name(), right);
		}

	}

	/*
	 * Date: 8 Aug 2021-----------------------------------------------------------
	 * Time created: 07:16:44---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * Output.java------------------------------------------------------ Class name:
	 * Prefix------------------------------------------------
	 */
	/**
	 * An expression whose operator represents the following characters +,-,!,~,¬
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 *
	 */
	private static class Prefix extends Name {
		/**
		 * The sole operand of this operator.
		 */
		private final EvaluatableExpression right;

		/*
		 * Date: 8 Aug 2021-----------------------------------------------------------
		 * Time created: 08:04:47---------------------------------------------------
		 */
		/**
		 * Initialises this {@code Prefix} object with a specified {@code Token} object
		 * for it's name and a given {@code EvaluatableExpression} for it's operand.
		 * 
		 * @param name  the name of the prefix expression taken from the {@code Token}
		 *              object's getName method.
		 * @param right the sole operand of this operator.
		 */
		public Prefix(Token<String> name, EvaluatableExpression right) {
			this(name.getName(), right);
		}

		/*
		 * Date: 8 Aug 2021-----------------------------------------------------------
		 * Time created: 08:23:33---------------------------------------------------
		 */
		/**
		 * Initialises this {@code Prefix} object.
		 * 
		 * @param name  the name of the prefix expression.
		 * @param right the sole operand of this operator.
		 * @see Prefix#Prefix(Token, EvaluatableExpression)
		 */
		private Prefix(String name, EvaluatableExpression right) {
			super(name);
			this.right = right;
		}

		/*
		 * Most Recent Date: 8 Aug 2021-----------------------------------------------
		 * Most recent time created: 08:24:31--------------------------------------
		 */
		/**
		 * Appends a formatted version of {@link #name()} then calls this operator's
		 * value {@link Expression#toString(StringBuilder) toString} method.
		 * <p>
		 * Note that the E constant is treated as a prefix expression and not a name.
		 * </p>
		 * 
		 * @param sb {@inheritDoc}
		 */
		@Override
		public void toString(StringBuilder sb) {
			// exponential function
			if (name().length() == 1 && name().equals("E")) {
				sb.append(name());
				sb.append('^');
				sb.append('{');
				right.toString(sb);
				sb.append('}');
				return;
			}
			if (name().equals("."))
				sb.append('0');
			sb.append(name());
			right.toString(sb);
		}

		/*
		 * Most Recent Date: 8 Aug 2021-----------------------------------------------
		 * Most recent time created: 08:29:10--------------------------------------
		 */
		/**
		 * Calculates the result of the expression and returns it. The value returned
		 * may be equal to {@code this}.
		 * 
		 * @return the result of the expression.
		 */
		@Override
		public EvaluatableExpression evaluate() {
			EvaluatableExpression right = this.right.evaluate();
			switch (name()) {
			case "-":
			case ".":
			case "+":// +2.5, +a, +(x-y)
			case "E":// E^2
			default:
				return new Prefix(name(), right);
//				return new Name(name() + right.name()).evaluate();
			}
		}

	}

	/*
	 * Date: 8 Aug 2021-----------------------------------------------------------
	 * Time created: 08:31:17---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * Output.java------------------------------------------------------ Class name:
	 * Postfix------------------------------------------------
	 */
	/**
	 * An unary postfix expression whose operator represents the following
	 * characters ++,--,!
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 *
	 */
	private static class Postfix extends Name {
		/**
		 * The sole operand.
		 */
		private final EvaluatableExpression left;

		/*
		 * Date: 8 Aug 2021-----------------------------------------------------------
		 * Time created: 08:41:37---------------------------------------------------
		 */
		/**
		 * Sole constructor. It comprises of an operand and a name.
		 * 
		 * @param left the only operand that {@code this} will be applied to.
		 * @param name the symbol of {@code this}.
		 */
		public Postfix(EvaluatableExpression left, String name) {
			super(name);
			this.left = left;
		}

		/*
		 * Most Recent Date: 8 Aug 2021-----------------------------------------------
		 * Most recent time created: 08:43:29--------------------------------------
		 */
		/**
		 * Calls this operator's value {@link Expression#toString(StringBuilder)
		 * toString} method then appends a formatted value of {@link #name()}.
		 * 
		 * @param sb {@inheritDoc}
		 */
		@Override
		public void toString(StringBuilder sb) {
			left.toString(sb);
			sb.append(name());
		}

		/*
		 * Most Recent Date: 8 Aug 2021-----------------------------------------------
		 * Most recent time created: 08:45:13--------------------------------------
		 */
		/**
		 * Calculates the result of the expression and returns it. The value returned
		 * may be equal to {@code this}.
		 * 
		 * @return the result of the expression.
		 */
		@Override
		public EvaluatableExpression evaluate() {
			EvaluatableExpression exp = left.evaluate();
			if (name().equals("!")) {
				if (exp instanceof Name && Utility.isNumber(exp.name()))
					return new Name(Arith
							.factorial(new BigDecimal(exp.name()),
									new MathContext(defaultSetting().getScale(), RoundingMode.HALF_EVEN))
							.toPlainString());
			}
			return new Postfix(exp, name());
		}

	}

	/*
	 * Date: 8 Aug 2021-----------------------------------------------------------
	 * Time created: 08:46:08---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * Output.java------------------------------------------------------ Class name:
	 * Group------------------------------------------------
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
	private static class Group extends Name {
		/**
		 * The expression inside the parenthesis
		 */
		private final EvaluatableExpression expr;

		/*
		 * Date: 8 Aug 2021-----------------------------------------------------------
		 * Time created: 08:47:30---------------------------------------------------
		 */
		/**
		 * Creates a group that isolates the argument.
		 * 
		 * @param expr the content of the group which is a child of {@code this}.
		 */
		public Group(EvaluatableExpression expr) {
			super("(" + expr.name() + ")");
			this.expr = expr;
		}

		/*
		 * Most Recent Date: 8 Aug 2021-----------------------------------------------
		 * Most recent time created: 08:49:43--------------------------------------
		 */
		/**
		 * Appends the left parenthesis, then calls the content of this group's
		 * {@link Expression#toString(StringBuilder) toString} method.
		 * 
		 * @param sb {@inheritDoc}
		 */
		@Override
		public void toString(StringBuilder sb) {
			if (expr instanceof Name) {
				expr.toString(sb);
				return;
			} else if (expr instanceof Operator) {
				// (3/a)
				if (expr.name().equals("/")) {
					Operator op = (Operator) expr;
					EvaluatableExpression l = op.left;
					EvaluatableExpression r = op.right;
					if (l instanceof Name && r instanceof Name) {
						expr.toString(sb);
						return;
					}
				}
			}
			sb.append("\\left(");
			expr.toString(sb);
			sb.append("\\right)");
		}

		/*
		 * Most Recent Date: 8 Aug 2021-----------------------------------------------
		 * Most recent time created: 08:52:00--------------------------------------
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
			EvaluatableExpression x = expr.evaluate();
			return x instanceof Name ? x : new Group(x);
		}

	}

	/*
	 * Date: 8 Aug 2021-----------------------------------------------------------
	 * Time created: 08:53:54---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * Output.java------------------------------------------------------ Class name:
	 * Function------------------------------------------------
	 */
	/**
	 * An expression that represents mathematical functions (sine, tangent,
	 * logarithm) as well as user specified functions. All functions use square
	 * brackets to input their arguments for example: sin[0.5] would be the correct
	 * syntax for this. Please see
	 * {@link mathaid.calculator.base.parser.CommonSyntax#functionOpen() this}
	 * documentation.
	 * <p>
	 * All names are assumed to be lower case with the exception of the initial
	 * letter else may not be evaluated.
	 * </p>
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 *
	 */
	private static class Function extends Name {
		/**
		 * the head of the function.
		 */
		private final EvaluatableExpression name;
		/**
		 * The argument list.
		 */
		private List<EvaluatableExpression> args;

		/*
		 * Date: 8 Aug 2021-----------------------------------------------------------
		 * Time created: 08:58:06---------------------------------------------------
		 */
		/**
		 * Constructs a function by creating it's head and argument list. The head of
		 * the function is expected to be an {@link Output.Name} for syntactic and
		 * legacy purposes but virtually any valid expression can be used for it's head.
		 * The argument list can be an empty list but must never be null or a
		 * {@code NullPointerException} will be thrown.
		 * 
		 * @param name the head of the function.
		 * @param args A {@code List} containing the arguments to this function in a
		 *             deliberate order.
		 */
		public Function(EvaluatableExpression name, List<EvaluatableExpression> args) {
			super(name.name());
			this.name = name;
			this.args = args;
		}

		/*
		 * Most Recent Date: 8 Aug 2021-----------------------------------------------
		 * Most recent time created: 09:00:22--------------------------------------
		 */
		/**
		 * Appends {@code this} to the specified {@code StringBuilder} in a pretty-print
		 * (TeX) format.
		 * 
		 * @param sb {@inheritDoc}
		 */
		@Override
		public void toString(StringBuilder sb) {
			switch (name()) {
			case ASIN:
			case ASINH:
			case ACOS:
			case ACOSH:
			case ATAN:
			case ATANH:
			case ACSC:
			case ACSCH:
			case ASEC:
			case ASECH:
			case ACOT:
			case ACOTH:
				sb.append(name().substring(1, name().length()));
				sb.append("^{-1}");
				args.get(0).toString(sb);
				return;
			case SQRT:
				sb.append("\\sqrt{");
				args.get(0).toString(sb);
				sb.append("}");
				return;
			case ABS:
				sb.append("\\left|");
				args.get(0).toString(sb);
				sb.append("\\right|");
				return;
			case MOD:
				args.get(0).toString(sb);
				sb.append("\\,");
				name.toString(sb);
				sb.append("\\,");
				args.get(1).toString(sb);
				return;
			case GAMMA:
				sb.append("\\Gamma\\left(");
				args.get(0).toString(sb);
				sb.append("\\right)");
				return;
			// Symbolic functions
			case SUM:
			case DIFF:
			case INT:// TODO: Remember to append '+ C' (in faded italics of course)
			case NINT:
//			case CONJ:
			default:
			}
//			sb.append("\\text{");
			name.toString(sb);
//			sb.append("}");
			sb.append("\\left(");
			for (int i = 0; i < args.size(); i++) {
				args.get(i).toString(sb);
				if (i < args.size() - 1)
					sb.append(',');
			}
			sb.append("\\right)");
		}

		/*
		 * Most Recent Date: 8 Aug 2021-----------------------------------------------
		 * Most recent time created: 09:01:42--------------------------------------
		 */
		/**
		 * Computes the result of {@code this} when applied to the specified
		 * (constructor) arguments and return it as a {@code EvaluatableExpression}.
		 * This operation may be idempotent.
		 * <p>
		 * If the result can be evaluated to a numerical value, then it is returned else
		 * a symbolic value is returned.
		 * </p>
		 * 
		 * @return an expression which is the result of {@code this} applied to the
		 *         arguments specified by the constructor.
		 */
		@Override // Only for numeric evaluation
		public EvaluatableExpression evaluate() {
			Settings s = defaultSetting();
			final int scale = s.getScale();
			final MathContext mc = new MathContext(scale, RoundingMode.HALF_EVEN);
			EvaluatableExpression name = args.get(0).evaluate();

			if (name instanceof Name && Utility.isNumber(name.name()))
				switch (name()) {
				/*
				 * Make sure all numeric arguments are defered here using symja's
				 * 'Defer[Sin[x]]' method. Use this for all trigonometry methods
				 */
				case SIN:
					return new Name(Arith.sin(new BigDecimal(name.name()), s.getTrig() == TRIG_DEG ? AngleUnit.DEG
							: s.getTrig() == TRIG_RAD ? AngleUnit.RAD : AngleUnit.GRAD, mc).toString());
				case ASIN:
					return new Name(Arith.asin(new BigDecimal(name.name()), s.getTrig() == TRIG_DEG ? AngleUnit.DEG
							: s.getTrig() == TRIG_RAD ? AngleUnit.RAD : AngleUnit.GRAD, mc).toString());
				case SINH:
					return new Name(Arith.sinh(new BigDecimal(name.name()), mc).toString());
				case ASINH:
					return new Name(Arith.asinh(new BigDecimal(name.name()), mc).toString());
				case COS:
					return new Name(Arith.cos(new BigDecimal(name.name()), s.getTrig() == TRIG_DEG ? AngleUnit.DEG
							: s.getTrig() == TRIG_RAD ? AngleUnit.RAD : AngleUnit.GRAD, mc).toString());
				case ACOS:
					return new Name(Arith.acos(new BigDecimal(name.name()), s.getTrig() == TRIG_DEG ? AngleUnit.DEG
							: s.getTrig() == TRIG_RAD ? AngleUnit.RAD : AngleUnit.GRAD, mc).toString());
				case COSH:
					return new Name(Arith.cosh(new BigDecimal(name.name()), mc).toString());
				case ACOSH:
					return new Name(Arith.acosh(new BigDecimal(name.name()), mc).toString());
				case TAN:
					return new Name(Arith.tan(new BigDecimal(name.name()), s.getTrig() == TRIG_DEG ? AngleUnit.DEG
							: s.getTrig() == TRIG_RAD ? AngleUnit.RAD : AngleUnit.GRAD, mc).toString());
				case ATAN:
					if (args.size() == 2) {
						EvaluatableExpression ex = args.get(1).evaluate();
						if (ex instanceof Name && Utility.isNumber(ex.name()))
							return new Name(Arith
									.atan(new BigDecimal(name.name()), new BigDecimal(ex.name()),
											s.getTrig() == TRIG_DEG ? AngleUnit.DEG
													: s.getTrig() == TRIG_RAD ? AngleUnit.RAD : AngleUnit.GRAD,
											mc)
									.toString());
					}
					return new Name(Arith.atan(new BigDecimal(name.name()), s.getTrig() == TRIG_DEG ? AngleUnit.DEG
							: s.getTrig() == TRIG_RAD ? AngleUnit.RAD : AngleUnit.GRAD, mc).toString());
				case TANH:
					return new Name(Arith.tanh(new BigDecimal(name.name()), mc).toString());
				case ATANH:
					return new Name(Arith.atanh(new BigDecimal(name.name()), mc).toString());
				case COT:
					return new Name(Arith.tan(BigDecimal.ONE.divide(new BigDecimal(name.name()), mc),
							s.getTrig() == TRIG_DEG ? AngleUnit.DEG
									: s.getTrig() == TRIG_RAD ? AngleUnit.RAD : AngleUnit.GRAD,
							mc).toString());
				case ACOT:
					return new Name(Arith.atan(BigDecimal.ONE.divide(new BigDecimal(name.name()), mc),
							s.getTrig() == TRIG_DEG ? AngleUnit.DEG
									: s.getTrig() == TRIG_RAD ? AngleUnit.RAD : AngleUnit.GRAD,
							mc).toString());
				case COTH:
					return new Name(Arith.tanh(BigDecimal.ONE.divide(new BigDecimal(name.name()), mc), mc).toString());
				case ACOTH:
					return new Name(Arith.atanh(BigDecimal.ONE.divide(new BigDecimal(name.name()), mc), mc).toString());
				case CSC:
					return new Name(Arith.sin(BigDecimal.ONE.divide(new BigDecimal(name.name()), mc),
							s.getTrig() == TRIG_DEG ? AngleUnit.DEG
									: s.getTrig() == TRIG_RAD ? AngleUnit.RAD : AngleUnit.GRAD,
							mc).toString());
				case ACSC:
					return new Name(Arith.asin(BigDecimal.ONE.divide(new BigDecimal(name.name()), mc),
							s.getTrig() == TRIG_DEG ? AngleUnit.DEG
									: s.getTrig() == TRIG_RAD ? AngleUnit.RAD : AngleUnit.GRAD,
							mc).toString());
				case CSCH:
					return new Name(Arith.sinh(BigDecimal.ONE.divide(new BigDecimal(name.name()), mc), mc).toString());
				case ACSCH:
					return new Name(Arith.asinh(BigDecimal.ONE.divide(new BigDecimal(name.name()), mc), mc).toString());
				case SEC:
					return new Name(Arith.cos(BigDecimal.ONE.divide(new BigDecimal(name.name()), mc),
							s.getTrig() == TRIG_DEG ? AngleUnit.DEG
									: s.getTrig() == TRIG_RAD ? AngleUnit.RAD : AngleUnit.GRAD,
							mc).toString());
				case ASEC:
					return new Name(Arith.acos(BigDecimal.ONE.divide(new BigDecimal(name.name()), mc),
							s.getTrig() == TRIG_DEG ? AngleUnit.DEG
									: s.getTrig() == TRIG_RAD ? AngleUnit.RAD : AngleUnit.GRAD,
							mc).toString());
				case SECH:
					return new Name(Arith.cosh(BigDecimal.ONE.divide(new BigDecimal(name.name()), mc), mc).toString());
				case ASECH:
					return new Name(Arith.acosh(BigDecimal.ONE.divide(new BigDecimal(name.name()), mc), mc).toString());
				case LOG:
					if (args.size() == 2) {
						EvaluatableExpression ex = args.get(1).evaluate();
						if (ex instanceof Name && Utility.isNumber(ex.name())) {
							BigDecimal x = new BigDecimal(name.name());
							BigDecimal y = new BigDecimal(ex.name());
							return new Name(Arith.log(x, y, mc).toString());
						}
					}
				case LN:
					return new Name(Arith.log(new BigDecimal(name.name()), mc).toString());
				case LOG10:
					return new Name(Arith.log10(new BigDecimal(name.name()), mc).toString());
				case LOG2:
					return new Name(Arith.log2(new BigDecimal(name.name()), mc).toString());
				case EXP:
					return new Name(Arith.exp(new BigDecimal(name.name()), mc).toString());
				case SQRT:
					return new Name(Arith.sqrt(new BigDecimal(name.name()), mc).toString());
				case CBRT:
					return new Name(Arith.cbrt(new BigDecimal(name.name()), scale).toString());
				case ABS:
					return new Name(new BigDecimal(name.name()).abs().toString());
				case SIGN:
					return new Name(Integer.toString(new BigDecimal(name.name()).signum()));
				case GCD:
					if (Utility.isInteger(new BigDecimal(name.name()))) {
						BigFraction x = new BigFraction(name.name());
						List<EvaluatableExpression> unknowns = new ArrayList<>();
						for (int i = 1; i < args.size(); i++) {
							EvaluatableExpression ex = args.get(i).evaluate();
							if (ex instanceof Name && Utility.isNumber(ex.name())
									&& Utility.isInteger(new BigDecimal(ex.name()))) {
								BigFraction y = new BigFraction(ex.name());
								x = x.gcd(y);
							} else
								unknowns.add(ex);
						}
						if (unknowns.size() > 0) {
							unknowns.add(0, new Name(x.toString()));
							return new Function(this.name, unknowns);
						}
						return new Name(x.toString());
					}
					break;
				case LCM:
					if (Utility.isInteger(new BigDecimal(name.name()))) {
						BigFraction x = new BigFraction(name.name());
						List<EvaluatableExpression> unknowns = new ArrayList<>();
						for (int i = 1; i < args.size(); i++) {
							EvaluatableExpression ex = args.get(i).evaluate();
							if (ex instanceof Name && Utility.isNumber(ex.name())
									&& Utility.isInteger(new BigDecimal(ex.name()))) {
								BigFraction y = new BigFraction(ex.name());
								x = new BigFraction(x.lcm(y), BigInteger.ONE);
							} else
								unknowns.add(ex);
						}
						if (unknowns.size() > 0) {
							unknowns.add(0, new Name(x.getFraction().toString()));
							return new Function(this.name, unknowns);
						}
						return new Name(x.getFraction().toString());
					}
					break;
				case MOD:
					if (Utility.isInteger(new BigDecimal(name.name()))) {
						BigDecimal x = new BigDecimal(name.name());
						EvaluatableExpression ex = args.get(1).evaluate();
						if (ex instanceof Name && Utility.isNumber(ex.name())
								&& Utility.isInteger(new BigDecimal(ex.name()))) {
							BigDecimal y = new BigDecimal(ex.name());
							return new Name(Arith.mod(x, y).toString());
						}
					}
					break;
				case NPR: {
					EvaluatableExpression arg2 = args.get(1).evaluate();
					if (Utility.isInteger(new BigDecimal(name.name())) && arg2 instanceof Name
							&& Utility.isInteger(new BigDecimal(arg2.name()))) {
						BigDecimal operand = new BigDecimal(name.name());
						BigDecimal operator = new BigDecimal(arg2.name());
						BigDecimal n = Arith.factorial(operand.abs())
								.divide(Arith.factorial(operand.abs().subtract(operator.abs())));
						return new Name(n.multiply(new BigDecimal(operand.min(operator).signum())).toString());
					}
				}
					break;
				case NCR: {
					EvaluatableExpression arg2 = args.get(1).evaluate();
					if (Utility.isInteger(new BigDecimal(name.name())) && arg2 instanceof Name
							&& Utility.isInteger(new BigDecimal(arg2.name()))) {
						BigDecimal operand = new BigDecimal(name.name());
						BigDecimal operator = new BigDecimal(arg2.name());
						BigDecimal c = Arith.factorial(operand.abs());
						BigDecimal d = Arith.factorial(operator.abs());
						BigDecimal e = Arith.factorial(operand.abs().subtract(operator.abs()));
						BigDecimal f = c.divide(d.multiply(e));
						return new Name(f.multiply(new BigDecimal(operand.min(operator).signum())).toString());
					}
				}
					break;
				case GAMMA:
					return new Name(Arith.gamma(new BigDecimal(name.name()), scale).toString());
				case POLYGAMMA:
					break;
				case MIN:
				case MAX:
					BigDecimal x1 = new BigDecimal(name.name());
					EvaluatableExpression ex = args.get(1).evaluate();
					if (ex instanceof Name && Utility.isNumber(ex.name())) {
						BigDecimal y = new BigDecimal(ex.name());
						return new Name(name().equals(MIN) ? x1.min(y).toString() : x1.max(y).toString());
					}
					break;
				// Symbolic functions
				case SUM:
				case DIFF:
				case INT:
				case NINT:
					/*
					 * This will not be implemented here it will be implemented at view part of the
					 * input. That is, at the point where input(Conj(z)) is called. At that point,
					 * the complex argument will be evaluated to give the conjugate
					 */
				case CONJ:
				case ERF:
				default:
				}
			List<EvaluatableExpression> ex = new ArrayList<>();
			for (int i = 0; i < args.size(); i++)
				ex.add(args.get(i).evaluate());
			return new Function(this.name, ex);
		}

	}

	/*
	 * Date: 8 Aug 2021-----------------------------------------------------------
	 * Time created: 09:05:21---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * Output.java------------------------------------------------------ Class name:
	 * NameParselet------------------------------------------------
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
		 * Most Recent Date: 8 Aug 2021-----------------------------------------------
		 * Most recent time created: 09:05:47--------------------------------------
		 */
		/**
		 * Returns a {@link Name}.
		 * 
		 * @param alreadyParsedLeft  can be null.
		 * @param yetToBeParsedToken the token being parsed.
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
	 * Date: 8 Aug 2021-----------------------------------------------------------
	 * Time created: 09:09:13---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * Output.java------------------------------------------------------ Class name:
	 * OperatorParselet------------------------------------------------
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
		 * Most Recent Date: 8 Aug 2021-----------------------------------------------
		 * Most recent time created: 09:11:14--------------------------------------
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
			return new Operator(alreadyParsedLeft, yetToBeParsedToken, right);
		}

		/*
		 * Date: 8 Aug 2021-----------------------------------------------------------
		 * Time created: 09:14:56---------------------------------------------------
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
	 * Date: 8 Aug 2021-----------------------------------------------------------
	 * Time created: 09:15:39---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * Output.java------------------------------------------------------ Class name:
	 * FunctionParselet------------------------------------------------
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
		 * Most Recent Date: 8 Aug 2021-----------------------------------------------
		 * Most recent time created: 09:16:24--------------------------------------
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
	 * Date: 8 Aug 2021-----------------------------------------------------------
	 * Time created: 09:17:48---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * Output.java------------------------------------------------------ Class name:
	 * PrefixParselet------------------------------------------------
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
		 * Most Recent Date: 8 Aug 2021-----------------------------------------------
		 * Most recent time created: 09:18:32--------------------------------------
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
			return new Prefix(yetToBeParsedToken, right);
		}

	}

	/*
	 * Date: 8 Aug 2021-----------------------------------------------------------
	 * Time created: 09:18:52---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * Output.java------------------------------------------------------ Class name:
	 * PostfixParselet------------------------------------------------
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
		 * Most Recent Date: 8 Aug 2021-----------------------------------------------
		 * Most recent time created: 09:19:57--------------------------------------
		 */
		/**
		 * Returns a {@code Postfix} object.
		 * 
		 * @param alreadyParsedLeft  {@inheritDoc}
		 * @param yetToBeParsedToken {@inheritDoc}
		 * @param parser             can be left as {@code null}.
		 * @return {@inheritDoc}
		 */
		@Override
		public EvaluatableExpression parse(EvaluatableExpression alreadyParsedLeft, Token<String> yetToBeParsedToken,
				PrattParser<EvaluatableExpression> parser) {
			return new Postfix(alreadyParsedLeft, yetToBeParsedToken.getName());
		}

	}

	/*
	 * Date: 8 Aug 2021-----------------------------------------------------------
	 * Time created: 09:20:42---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base------------------------------------------------
	 * Project: LatestPoject2------------------------------------------------ File:
	 * Output.java------------------------------------------------------ Class name:
	 * GroupParselet------------------------------------------------
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
		 * Most Recent Date: 8 Aug 2021-----------------------------------------------
		 * Most recent time created: 09:22:41--------------------------------------
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
//			return exp;
			return new Group(content);
		}

	}

	/*
	 * Date: 8 Aug 2021-----------------------------------------------------------
	 * Time created: 09:24:12--------------------------------------------
	 */
	/**
	 * Returns a {@code CommonSyntax} object whose syntax is synonymous with
	 * {@link CommonSyntax#BASIC_TYPES}
	 * 
	 * @return a {@link CommonSyntax} object.
	 */
	// TODO: Remember to create support for point '.' and exponent 'e'
	static CommonSyntax<EvaluatableExpression, PrattParser<EvaluatableExpression>> buildSyntax() {
		CommonSyntax.Builder<EvaluatableExpression> builder = new CommonSyntax.Builder<>();

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

		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		for (int i = 0; i < chars.length(); i++)
			builder.registerLetter(chars.charAt(i));
		chars = "1234567890";
		for (int i = 0; i < chars.length(); i++)
			builder.registerDigit(chars.charAt(i));

		builder.registerTypes(CommonSyntax.BASIC_TYPES.values());
		builder.registerNameType(CommonSyntax.BASIC_TYPES.get(CommonSyntax.NAME));

		builder.registerPrefixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.NAME), new NameParselet());
		// expressions like b * (a + c)
		builder.registerPrefixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.LEFT_PARENTHESIS),
				new GroupParselet());
		// expressions like Sin(x)
		builder.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.LEFT_BRACKET), new FunctionParselet());
		builder.registerPrefixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.PLUS), new PrefixParselet());
		builder.registerPrefixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.MINUS), new PrefixParselet());

		builder.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.PLUS), new OperatorParselet(false));
		builder.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.MINUS), new OperatorParselet(false));
		builder.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.ASTERISK), new OperatorParselet(false));
		builder.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.FORWARD_SLASH),
				new OperatorParselet(false));
		builder.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.CARET), new OperatorParselet(true));
		builder.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.BANG), new PostfixParselet());
		// for numbers starting with a point such as .0983912
		builder.registerPrefixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.PERIOD), new PrefixParselet());
		// for numbers that have a decimal point in between such as 123.456789
		builder.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.PERIOD), new OperatorParselet(false));
		builder.registerInfixParselet(CommonSyntax.BASIC_TYPES.get(CommonSyntax.QUOTE), new PostfixParselet());

		return builder.build();
	}

	/*
	 * Most Recent Date: 8 Aug 2021-----------------------------------------------
	 * Most recent time created: 09:26:26--------------------------------------
	 */
	/**
	 * Updates the source string that will be evaluated within {@link #doAction()}.
	 * 
	 * @param output the data source.
	 */
	@Override
	public void inform(DataText output) {
		string = output.getSymjaString();
	}

	/*
	 * Most Recent Date: 8 Aug 2021-----------------------------------------------
	 * Most recent time created: 09:27:48--------------------------------------
	 */
	/**
	 * Parses the internal source text, evaluates the value parsed and updates
	 * {@link #getOutput()} with a valid {@code DataText}.
	 */
	@Override
	public void doAction() {
//		System.out.println("after internal calc: " + string);
		PrattParser<EvaluatableExpression> parser = new PrattParser<>(new Lexer(string), buildSyntax());
		EvaluatableExpression expression = parser.parse();
		StringBuilder sb = new StringBuilder();
		if (!defaultSetting().isExpression())
			expression = expression.evaluate();
		expression.toString(sb);
		synchronized (this) {
			this.output = new DataText(sb.toString(), string);
		}
		/*
		 * TODO: Any print to screen can be done below (to replace the code below) or at
		 * the equivalent location in the calculator class
		 */
		System.err.println(this.output);
	}

	/*
	 * Date: 8 Aug 2021-----------------------------------------------------------
	 * Time created: 09:30:03--------------------------------------------
	 */
	/**
	 * Gets the current result of calculations. If no calculations has been done,
	 * then{@code null} will be returned.
	 * 
	 * @return the current result of the most recent calculation or {@code null} if
	 *         no calculation has been done since creation of this object.
	 */
	public DataText getOutput() {
		return output;
	}
	
	/*
	 * Date: 8 Aug 2021----------------------------------------------------------- 
	 * Time created: 09:33:18--------------------------------------------------- 
	 */
	/**
	 * Sole Constructor.
	 */
	public Output() {
		string = "0";
	}

	/**
	 * The source string that is parsed, evaluated and printed to screen.
	 */
	private volatile String string;
	/**
	 * The value to be printed to screen. It may contain an evaluated TeX format for {@link #string}
	 */
	private volatile DataText output;

}