/**
 * 
 */
package mathaid.calculator.base.evaluator.parser.expression.scientific;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import mathaid.calculator.base.evaluator.Calculator;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.typeset.Digits;
import mathaid.calculator.base.typeset.Segment;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.typeset.Segments;
import mathaid.calculator.base.util.Utility;
import mathaid.calculator.base.value.BigFraction;
import mathaid.functional.Supplier.Function;

/*
 * Date: 4 Sep 2022----------------------------------------------------------- 
 * Time created: 17:41:03---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression.scientific------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Name.java------------------------------------------------------ 
 * Class name: Name------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Name extends EvaluatableExpression<Name.Params> {

	public static interface Params extends EvaluatableExpression.ExpressionParams<Params> {

		public static interface ResultType extends EvaluatableExpression.ExpressionParams.ResultType {
			int FIX = 1;
			int SCI = 2;
			int ENG = 3;
			int FRAC = 4;
			int MFRAC = 5;
			int EXPRESSION = 6;
		}

		boolean isComplex();

		boolean hasIntegral();

		boolean hasDifferential();

		int getRecurringType();// use one of Segment.getType()

		int getNumOfRepeats();

		default char[] getIndependentVariables() {
			return "xy".toCharArray();
		}

		int getScale();

		@Override
		default int getRadix() {
			return 10;
		}

		default String getLog() {
			return "Log";
		}

		default Map<String, String> getInverseTrigFunctions() {
			Map<String, String> f = new HashMap<>();
			f.put("ArcSin", "Sin");
			f.put("ArcCos", "Cos");
			f.put("ArcTan", "Tan");
			f.put("ArcCot", "Cot");
			f.put("ArcCsc", "Csc");
			f.put("ArcSec", "Sec");
			f.put("ArcSinh", "Sinh");
			f.put("ArcCosh", "Cosh");
			f.put("ArcTanh", "Tanh");
			f.put("ArcCoth", "Coth");
			f.put("ArcCsch", "Csch");
			f.put("ArcSech", "Sech");
			return Collections.unmodifiableMap(f);
		}

		default String getAbs() {
			return "Abs";
		}

		default String getSqrt() {
			return "Sqrt";
		}

		default String getGamma() {
			return "Gamma";
		}

		default String getPow() {
			return "Power";
		}

		default String getSignum() {
			return "Sign";
		}

		default String getNaturalLog() {
			return "Log";
		}

		/*
		 * Date: 11 Sep 2022-----------------------------------------------------------
		 * Time created: 22:38:06--------------------------------------------
		 */
		/**
		 * Gets the String used in the evaluator to represent common fractions as
		 * Functions. For example most CAS (Computer Algebra System) use the string
		 * 'Ration' or 'Rational' as the function name for common fractions, in this
		 * context, if the user is using one of those that uses the examples, then this
		 * method will return one of the above string.
		 * <p>
		 * This used for special cases during evaluation and formatting.
		 * 
		 * @return a string used by the underlying CAS to represent common fractions.
		 */
		default String getFractionFunction() {
			return "Rational";
		}

		/*
		 * Date: 12 Sep 2022-----------------------------------------------------------
		 * Time created: 09:23:15--------------------------------------------
		 */
		/**
		 * Gets a list of all the strings used in the evaluator to represent cos, tan,
		 * sin, cot, csc, sec.
		 * <p>
		 * This used for special cases during evaluation and formatting.
		 * 
		 * @return a list of strings used by the underlying CAS to represent elementary
		 *         trig functions.
		 */
		default List<String> getTrigStrings() {
			return Arrays.asList("Sin", "Cos", "Tan", "Sec", "Csc", "Cot");
		}

		default String getLimit() {
			return "Lmit";
		}

		/*
		 * Date: 16 Sep 2022-----------------------------------------------------------
		 * Time created: 19:58:23--------------------------------------------
		 */
		/**
		 * @return
		 */
		default String getDifferential() {
			return "D";
		}

		/*
		 * Date: 16 Sep 2022-----------------------------------------------------------
		 * Time created: 19:58:58--------------------------------------------
		 */
		/**
		 * @return
		 */
		default String getIntegral() {
			return "Integrate";
		}

		/*
		 * Date: 16 Sep 2022-----------------------------------------------------------
		 * Time created: 20:00:09--------------------------------------------
		 */
		/**
		 * @return
		 */
		default String getSummation() {
			return "Sum";
		}

		/*
		 * Date: 16 Sep 2022-----------------------------------------------------------
		 * Time created: 20:01:17--------------------------------------------
		 */
		/**
		 * @return
		 */
		default String getProduct() {
			return "Product";
		}

		/*
		 * Date: 16 Sep 2022-----------------------------------------------------------
		 * Time created: 20:03:02--------------------------------------------
		 */
		/**
		 * @return
		 */
		default String getFloor() {
			return "Floor";
		}

		default String getCeil() {
			return "Ceil";
		}

	}

	protected static boolean isInteger(EvaluatableExpression<?> x) {
		if (isNumber(x))
			return Utility.isInteger(new BigDecimal(x.getName()));
		return false;
	}

	protected static boolean isIntegerFraction(EvaluatableExpression<?> x) {
		if (x instanceof Function) {
			mathaid.calculator.base.evaluator.parser.expression.scientific.Function fraction = (mathaid.calculator.base.evaluator.parser.expression.scientific.Function) x;
			return fraction.getName().equals("Rational") && isInteger(fraction.getArguments().get(0))
					&& isInteger(fraction.getArguments().get(1));
		} else if (x instanceof Operator) {
			Operator div = (Operator) x;
			return div.getName().equals("/") && isInteger(div.getLeft()) && isInteger(div.getRight());
		}
		return false;
	}

	protected static boolean isNumber(EvaluatableExpression<?> x) {
		return x instanceof Name && isNumber(x.getName());
	}

	/*
	 * Date: 4 Sep 2022-----------------------------------------------------------
	 * Time created: 17:41:08---------------------------------------------------
	 */
	/**
	 * @param name
	 */
	public Name(String name, Params params) {
		super(params);
		this.name = name;
	}

	static int getTotalScale(BigDecimal n) {
		return Utility.numOfIntegerDigits(n) + Utility.numOfFractionalDigits(n);
	}

	static String fixedPoint(BigDecimal n, int signum, int scale) {
		n = n.abs();
		int totalScale = getTotalScale(n);
		if (totalScale > scale) {
			n = n.round(new MathContext(scale, RoundingMode.HALF_EVEN));
			n = n.stripTrailingZeros();
			return n.multiply(new BigDecimal(signum)).toPlainString();
		}
		totalScale = scale - totalScale;
		return n.setScale(totalScale, RoundingMode.HALF_EVEN).multiply(new BigDecimal(signum)).toPlainString();
	}

	static boolean isNumber(String s) {
		try {
			Digits.fromSegmentString(s);
		} catch (Throwable t) {
			return false;
		}
		return true;
	}

	/*
	 * Most Recent Date: 4 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:41:03--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param formatBuilder
	 */
	@Override
	public void format(SegmentBuilder formatBuilder) {
//		boolean isNumber = Utility.isNumber(getName());
		Params p = getParams();

		if (isNumber(getName())) {
			switch (p.getResultType()) {
			case Params.ResultType.DECIMAL:
			case Params.ResultType.EXPRESSION:
			default: {
//				String s = Digits.toSegmentString(
//						new BigFraction(new BigDecimal(getName()),
//								new MathContext(p.getScale(), RoundingMode.HALF_EVEN), null, new BigDecimal("1E-10")),
//						0);
				BigFraction s = Digits.fromSegmentString(getName());
				formatBuilder.append(
						Digits.toSegment(s, p.getRecurringType(), 0, Calculator.fromParams(p, p.getNumOfRepeats())));
				break;

			}
			case Params.ResultType.FIX: {
				BigDecimal n = Digits.fromSegmentString(getName()).getFraction();
				formatBuilder.append(Digits.toSegment(new BigDecimal(fixedPoint(n, n.signum(), p.getScale())), 0,
						Calculator.fromParams(p, p.getNumOfRepeats())));
				break;
			}
			case Params.ResultType.SCI: {
				BigFraction s = Digits.fromSegmentString(getName());
				formatBuilder.append(
						Digits.toSegment(s, p.getRecurringType(), 1, Calculator.fromParams(p, p.getNumOfRepeats())));
				break;
			}
			case Params.ResultType.ENG: {
				BigFraction s = Digits.fromSegmentString(getName());
				formatBuilder.append(Digits.toSegment(s, false, p.getRecurringType(),
						Calculator.fromParams(p, p.getNumOfRepeats())));
				break;
			}
			case Params.ResultType.FRAC: {
				BigFraction s = Digits.fromSegmentString(getName());
				formatBuilder.append(Digits.toSegment(s, false, Calculator.fromParams(p, p.getNumOfRepeats())));
				break;
			}
			case Params.ResultType.MFRAC: {
				BigFraction s = Digits.fromSegmentString(getName());
				formatBuilder.append(Digits.toSegment(s, true, Calculator.fromParams(p, p.getNumOfRepeats())));
				break;
			}
			}
		}
//		else if (p.getConstants().containsKey(getName())) 
//			formatBuilder.append(Segments.constant(p.getConstants().get(getName()).get(), getName()));
//		 else if (p.getBoundVariables().containsKey(getName())) 
//			formatBuilder.append(Segments.boundVar(p.getBoundVariables().get(getName()).get(), getName()));
		else if (getName().length() == 1 && String.valueOf(p.getIndependentVariables()).contains(getName())) {
			formatBuilder.append(Segments.freeVariable(getName(), getName()));
		} else
			formatBuilder.append(Segments.constant(getName(), getName()));
		// end if
	}

	/*
	 * Most Recent Date: 4 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:41:03--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public EvaluatableExpression<Params> evaluate() {
		/*
		 * There is no need for the code below because before evaluation, all constants
		 * and dependent variables will be unboxed (converted to their literal value) if
		 * params.result-type == expression.
		 */
		if (getParams().getResultType() != Params.ResultType.EXPRESSION)
			if (getParams().getConstants().containsKey(getName())) {
				Segment s = getParams().getConstants().get(getName()).get2nd().call(getParams()).toSegment();
				StringBuilder sb = new StringBuilder();
				s.toString(sb, null, new ArrayList<>());
				// TODO: This may cause an infinite recursion!!!
//				getParams().getInternalEvaluator().evaluate(sb.toString()).toSegment()
//						.toString(sb.delete(0, sb.length()), null, new ArrayList<>());
				return new Name(sb.toString(), getParams());
			} else if (getParams().getBoundVariables().containsKey(getName())) {
				Segment s = getParams().getBoundVariables().get(getName()).get2nd().call(getParams()).toSegment();
				StringBuilder sb = new StringBuilder();
				s.toString(sb, null, new ArrayList<>());
				// TODO: This may cause an infinite recursion!!!
//				getParams().getInternalEvaluator().evaluate(sb.toString()).toSegment()
//						.toString(sb.delete(0, sb.length()), null, new ArrayList<>());
				return new Name(sb.toString(), getParams());
			}
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

	/*
	 * Most Recent Date: 4 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:48:30--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param o
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Name) {
			return getName().equals(((Name) o).getName());
		}
		return false;
	}

	/*
	 * Most Recent Date: 4 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:48:30--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public int hashCode() {
		return getName().hashCode() ^ Objects.hash(this);
	}

	/*
	 * Most Recent Date: 4 Sep 2022-----------------------------------------------
	 * Most recent time created: 17:48:30--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return name;
	}

	private final String name;
}
