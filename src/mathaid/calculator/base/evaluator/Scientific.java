/*
 * Date: 10 Nov 2023 -----------------------------------------------------------
 * Time created: 15:21:09 ---------------------------------------------------
 */
/**
 * 
 */
package mathaid.calculator.base.evaluator;

import static mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression.fromParams;
import static mathaid.calculator.base.typeset.Digits.toSegment;
import static mathaid.calculator.base.util.Arith.sqrt;
import static mathaid.calculator.base.util.Constants.apery;
import static mathaid.calculator.base.util.Constants.catalan;
import static mathaid.calculator.base.util.Constants.conf;
import static mathaid.calculator.base.util.Constants.conway;
import static mathaid.calculator.base.util.Constants.e;
import static mathaid.calculator.base.util.Constants.eb;
import static mathaid.calculator.base.util.Constants.em;
import static mathaid.calculator.base.util.Constants.khinchin;
import static mathaid.calculator.base.util.Constants.levy;
import static mathaid.calculator.base.util.Constants.lieb;
import static mathaid.calculator.base.util.Constants.mills;
import static mathaid.calculator.base.util.Constants.omega;
import static mathaid.calculator.base.util.Constants.pi;
import static mathaid.calculator.base.util.Constants.plastic;
import static mathaid.calculator.base.util.Constants.recFib;
import static mathaid.calculator.base.util.Constants.sierpinski;
import static mathaid.calculator.base.util.Constants.superGR;
import static mathaid.calculator.base.util.Constants.uniParabola;
import static mathaid.calculator.base.util.Utility.d;
import static mathaid.calculator.base.util.Utility.f;
import static mathaid.calculator.base.util.Utility.mc;

import java.util.HashMap;
import java.util.Map;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.EvalUtilities;

import mathaid.calculator.base.converter.AngleUnit;
import mathaid.calculator.base.evaluator.parser.PrattParser;
import mathaid.calculator.base.evaluator.parser.ScientificLexer;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.evaluator.parser.expression.scientific.Name;
import mathaid.calculator.base.evaluator.parser.expression.scientific.Name.Params;
import mathaid.calculator.base.typeset.BasicSegment;
import mathaid.calculator.base.typeset.DigitPunc;
import mathaid.calculator.base.typeset.LinkedSegment;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.util.Tuple;
import mathaid.calculator.base.util.Tuple.Couple;
import mathaid.functional.Supplier;
import mathaid.functional.Supplier.Function;

public class Scientific implements Evaluator<LinkedSegment>, Name.Params {

	public static final int HYP_MASK = 0x20_00_00_00;
	public static final int RCPRCL_MASK = 0x40_00_00_00;

	public static final int CONST_MASK = 0x1_00_00_00;
	public static final int CAPS_LOCK_MASK = 0x2_00_00_00;

	public static final int EXPRESSION_MASK = 0x10_00;
	public static final int COMPLEX_MASK = 0x20_00;
	public static final int NUM_LOCK_MASK = 0x40_00;

	public static final int ALL_INPUT_MASK = 0;
	public static final int NUM_INPUT_TYPE = 1;
	public static final int INT_INPUT_MASK = 3;
	public static final int SYMBOL_INPUT_MASK = 2;

	private class Symja implements Evaluator<String> {

		Symja() {
			EvalEngine ev = new EvalEngine(false);
			ev.setNumericPrecision(getScale());
			ev.setNumericMode(getResultType() == Name.Params.ResultType.EXPRESSION, getScale());
			evaluator = new EvalUtilities(ev, false, false);
		}

		/*
		 * Most Recent Date: 18 Sep 2022-----------------------------------------------
		 * Most recent time created: 20:52:40--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @param expression
		 * @return
		 */
		@Override
		public String evaluate(String expression) {
			evaluator.getEvalEngine().setNumericMode(getResultType() == Name.Params.ResultType.EXPRESSION, getScale());
			String s = evaluator.evaluate(expression).toString();
			return s;
		}

		private EvalUtilities evaluator;

	}
	
	static DigitPunc fp(Params p) {
		return fromParams(p, p.getNumOfRepeats());
	}
	
	static void initConstants(Map<String, Tuple.Couple<String, Supplier.Function<Params, SegmentBuilder>>> s) {
		//PI, Archimedes' constant
		Supplier.Function<Params, SegmentBuilder> fx = p -> new SegmentBuilder(toSegment(pi(p.getScale()), 0,
					fp(p)));
		s.put("Pi", Tuple.of("\\pi", fx));

		//Euler's number i.e exponential growth constant
		fx = p -> new SegmentBuilder(toSegment(e(p.getScale()), 0,
				fp(p)));
		s.put("E", Tuple.of("e", fx));
		
		//Tau - constant for 2 * Pi
		fx = p -> new SegmentBuilder(toSegment(pi(p.getScale()).multiply(d(2)), 0,
				fp(p)));
		s.put("\u03A4", Tuple.of("\\tau", fx));
		
		//Pythagoras constant Sqrt[2]
		fx = p -> new SegmentBuilder(toSegment(sqrt(d(2), mc(p.getScale())), 0,
				fp(p)));
		s.put("Py", Tuple.of("\\sqrt{2}", fx));
		
		//Theodorus constant Sqrt[3]
		fx = p -> new SegmentBuilder(toSegment(sqrt(d(3), mc(p.getScale())), 0,
				fp(p)));
		s.put("Th", Tuple.of("\\sqrt{3}", fx));
		
		//Apery number to the 50th index in the apery series
		fx = p -> new SegmentBuilder(toSegment(apery(50), p.getResultType() == Params.ResultType.MFRAC, fp(p)));
		s.put("Ap", Tuple.of("\\zeta(3)", fx));
		
		//golden ratio
		fx = p -> new SegmentBuilder(toSegment(sqrt(d(5), mc(p.getScale())).add(d(1)).divide(d(2), mc(p.getScale())), 0, fp(p)));
		s.put("Gr", Tuple.of("\\phi", fx));
		
		//Super golden ratio
		fx = p -> new SegmentBuilder(toSegment(superGR(p.getScale()), 0, fp(p)));
		s.put("SgR", Tuple.of("\\psi_S", fx));
		
		//Silver ratio
		fx = p -> new SegmentBuilder(toSegment(sqrt(d(2), mc(p.getScale())).add(d(1)), 0, fp(p)));
		s.put("\u0394", Tuple.of("\\delta_S", fx));
		
		//Euler-Mascheroni constant
		fx = p -> new SegmentBuilder(toSegment(em(p.getScale()), 0, fp(p)));
		s.put("\\u03b3", Tuple.of("\\gamma", fx));
		
		//Meissel–Mertens constant
		fx = p -> new SegmentBuilder(toSegment(d("0.2614972128476427837554268386086958590516", mc(p.getScale())), 0, fp(p)));
		s.put("Mm", Tuple.of("M", fx));
		
		//Gauss–Kuzmin–Wirsing constant
		fx = p -> new SegmentBuilder(toSegment(d("0.30366300289873265859744812190155623", mc(p.getScale())), 0, fp(p)));
		s.put("GkW", Tuple.of("\\lamda_2", fx));
		
		//Hafner–Sarnak–McCurley constant
		fx = p -> new SegmentBuilder(toSegment(d("0.35323637185499598454351655043268201", mc(p.getScale())), 0, fp(p)));
		s.put("HsM", Tuple.of("\\sigma", fx));
		
		//Conway constant
		fx = p -> new SegmentBuilder(toSegment(conway(p.getScale()), 0, fp(p)));
		s.put("\u03bb", Tuple.of("\\lambda", fx));
		
		//Mills constant
		fx = p -> new SegmentBuilder(toSegment(mills(p.getScale()), 0, fp(p)));
		s.put("\u03b8", Tuple.of("\\theta", fx));
		
		//Plastic constant
		fx = p -> new SegmentBuilder(toSegment(plastic(p.getScale()), 0, fp(p)));
		s.put("\u03c1", Tuple.of("\\rho", fx));
		
		//Ramanujan–Soldner constant
		fx = p -> new SegmentBuilder(toSegment(d("1.45136923488338105028396848589202744", mc(p.getScale())), 0, fp(p)));
		s.put("\u03bc", Tuple.of("\\mu", fx));
		
		//Levy's constant
		fx = p -> new SegmentBuilder(toSegment(levy(p.getScale()), 0, fp(p)));
		s.put("LeV", Tuple.of("L_y", fx));
		
		//Conjugate of fibonacci constant
		fx = p -> new SegmentBuilder(toSegment(conf(p.getScale()), 0, fp(p)));
		s.put("CfC", Tuple.of("\\psi_{CF}", fx));
		
		//Reciprocal fibonacci constant
		fx = p -> new SegmentBuilder(toSegment(recFib(250, p.getScale()), 0, fp(p)));
		s.put("RfC", Tuple.of("\\psi_{RF}", fx));
		
		//Feigenbaum 2nd constant. It is a rational value but the sci calculator cannot yet process constants with rational values
//		fx = p -> new SegmentBuilder(toSegment(f(8177, 3267), p.getResultType() == Params.ResultType.MFRAC, fp(p)));
//		s.put("FbS", Tuple.of("F_2", fx));
		fx = p -> new SegmentBuilder(toSegment(f(8177, 3267).getDecimalExpansion(p.getScale()), 0, fp(p)));
		s.put("FbS", Tuple.of("F_2", fx));
		
		//Sierpinski's constant
		fx = p -> new SegmentBuilder(toSegment(sierpinski(p.getScale()), 0, fp(p)));
		s.put("Ks", Tuple.of("K", fx));
		
		//Khinchin's constant
		fx = p -> new SegmentBuilder(toSegment(khinchin(p.getScale()), 0, fp(p)));
		s.put("KhC", Tuple.of("K_0", fx));
		
		//universal parabolic constant
		fx = p -> new SegmentBuilder(toSegment(uniParabola(p.getScale()), 0, fp(p)));
		s.put("Up", Tuple.of("P_2", fx));
		
		//Erdos–Borwein's constant
		fx = p -> new SegmentBuilder(toSegment(eb(250, p.getScale()), 0, fp(p)));
		s.put("ErBo", Tuple.of("E", fx));
		
		//Lieb's square ice constant
		fx = p -> new SegmentBuilder(toSegment(lieb(p.getScale()), 0, fp(p)));
		s.put("LsI", Tuple.of("L_c", fx));
		
		//Catalan's constant
		fx = p -> new SegmentBuilder(toSegment(catalan(p.getScale()), 0, fp(p)));
		s.put("Ct", Tuple.of("L_c", fx));
		
		//Omega constant
		fx = p -> new SegmentBuilder(toSegment(omega(p.getScale()), 0, fp(p)));
		s.put("\u03A9", Tuple.of("\\Omega", fx));
	}

	public Scientific() {
		lexer = new ScientificLexer();
		parser = new PrattParser<>();
		resultType = Name.Params.ResultType.DECIMAL;
		intGroupSize = 3;
		mantGroupSize = intGroupSize;
		recurringType = LinkedSegment.Type.VINCULUM;
		numOfRepeats = 2;
		scale = 35;
		decimalPoint = ".";
		intSeparator = ",";
		mantSeparator = "~";
		divisionString = new String[] { "/", "\\div" };
		multiplicationString = new String[] { "*", "\\times" };
		trig = AngleUnit.DEG;
		constants = new HashMap<>();
		boundVariables = new HashMap<>();
		symja = new Symja();
		initConstants(constants);
	}

	public Evaluator<String> getSymja() {
		return symja;
	}

	public int getModifier() {
		return modifier;
	}

	public void setModifier(int modifier) {
		this.modifier = modifier;
		if (EXPRESSION_MASK == (this.modifier & EXPRESSION_MASK) && getResultType() != ResultType.EXPRESSION)
			setResultType(ResultType.EXPRESSION);
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the resultType
	 */
	@Override
	public int getResultType() {
		return resultType;
	}

	public void setResultType(int resultType) {
		this.resultType = resultType;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the decimalPoint
	 */
	@Override
	public String getDecimalPoint() {
		return decimalPoint;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param decimalPoint the decimalPoint to set
	 */
	public void setDecimalPoint(String decimalPoint) {
		this.decimalPoint = decimalPoint;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the intSeparator
	 */
	@Override
	public String getIntSeparator() {
		return intSeparator;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param intSeparator the intSeparator to set
	 */
	public void setIntSeparator(String intSeparator) {
		this.intSeparator = intSeparator;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the mantSeparator
	 */
	@Override
	public String getMantSeparator() {
		return mantSeparator;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param mantSeparator the mantSeparator to set
	 */
	public void setMantSeparator(String mantSeparator) {
		this.mantSeparator = mantSeparator;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the intGroupSize
	 */
	@Override
	public int getIntGroupSize() {
		return intGroupSize;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param intGroupSize the intGroupSize to set
	 */
	public void setIntGroupSize(int intGroupSize) {
		this.intGroupSize = intGroupSize;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the mantGroupSize
	 */
	@Override
	public int getMantGroupSize() {
		return mantGroupSize;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param mantGroupSize the mantGroupSize to set
	 */
	public void setMantGroupSize(int mantGroupSize) {
		this.mantGroupSize = mantGroupSize;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the constants
	 */
	@Override
	public Map<String, Couple<String, Function<Name.Params, SegmentBuilder>>> getConstants() {
		return constants;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param name     the name within the expression that will be used to fetch the segment format from the constant map
	 * @param constant the first element is the MathJax format the next is the segment builder of the actual numeric value. This
	 *                 need to be implemented in the scientific expression package where {@code Name} exists. In it format method,
	 *                 it uses the same string for the toString and the format of the segment being returned. This is wrong. It
	 *                 should use a valid format.
	 */
	public void addConstant(String name, Couple<String, Function<Name.Params, SegmentBuilder>> constant) {
		this.constants.put(name, constant);
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the boundVariables
	 */
	@Override
	public Map<String, Couple<String, Function<Name.Params, SegmentBuilder>>> getBoundVariables() {
		return boundVariables;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param boundVariables the boundVariables to set
	 */
	public void addBoundVariables(String name, Couple<String, Function<Name.Params, SegmentBuilder>> boundVariable) {
		this.boundVariables.put(name, boundVariable);
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the divisionString
	 */
	@Override
	public String[] getDivisionString() {
		return divisionString;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param divisionString the divisionString to set
	 */
	public void setDivisionString(String[] divisionString) {
		this.divisionString = divisionString;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the multiplicationString
	 */
	@Override
	public String[] getMultiplicationString() {
		return multiplicationString;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param multiplicationString the multiplicationString to set
	 */
	public void setMultiplicationString(String[] multiplicationString) {
		this.multiplicationString = multiplicationString;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the complex
	 */
	@Override
	public boolean isComplex() {
		return COMPLEX_MASK == (modifier & COMPLEX_MASK);
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param complex the complex to set
	 */
	public void setComplex(boolean complex) {
		this.modifier ^= complex ? 1 : 0;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the intgeral
	 */
	@Override
	public boolean hasIntegral() {
		return integral;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param intgeral the intgeral to set
	 */
	public void setIntgeral(boolean intgeral) {
		this.integral = intgeral;
	}

	/*
	 * Most Recent Date: 8 Oct 2022-----------------------------------------------
	 * Most recent time created: 09:31:44--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public boolean hasDifferential() {
		return diff;
	}

	/*
	 * Most Recent Date: 8 Oct 2022-----------------------------------------------
	 * Most recent time created: 09:31:44--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	public void setDifferential(boolean diff) {
		this.diff = diff;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the recurringType
	 */
	@Override
	public int getRecurringType() {
		return recurringType;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param recurringType the recurringType to set
	 */
	public void setRecurringType(int recurringType) {
		this.recurringType = recurringType;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the numOfRepeats
	 */
	@Override
	public int getNumOfRepeats() {
		return numOfRepeats;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param numOfRepeats the numOfRepeats to set
	 */
	public void setNumOfRepeats(int numOfRepeats) {
		this.numOfRepeats = numOfRepeats;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the scale
	 */
	@Override
	public int getScale() {
		return scale;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param scale the scale to set
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @return the trig
	 */
	@Override
	public AngleUnit getTrig() {
		return trig;
	}

	/*
	 * Date: 16 Sep 2022-----------------------------------------------------------
	 * Time created: 20:34:16---------------------------------------------------
	 */
	/**
	 * @param trig the trig to set
	 */
	public void setTrig(AngleUnit trig) {
		this.trig = trig;
	}

	/*
	 * Date: 10 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:02:23 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param expression
	 * @return
	 * @throws RuntimeException
	 */
	@Override
	public LinkedSegment evaluate(String expression) throws RuntimeException {
		final String src = symja.evaluate(expression);
		lexer.setSource(src);
		EvaluatableExpression<Name.Params> f = parser.parse(lexer, lexer.getSyntax(), Scientific.this);
		parser.reset();

		if (getResultType() != Name.Params.ResultType.EXPRESSION)// (EXPRESSION_MASK == (modifier & EXPRESSION_MASK))
			f = f.evaluate();
		SegmentBuilder sb = new SegmentBuilder();
		f.format(sb);
		if (hasIntegral())
			sb.append(new BasicSegment("", " + C", LinkedSegment.Type.AUTO_COMPLETE));
		return sb.toSegment();
	}

	private final Scientific.Symja symja;

	private final ScientificLexer lexer;
	private final PrattParser<EvaluatableExpression<Name.Params>, Name.Params> parser;

	private boolean integral;
	private boolean diff;
	private int modifier;
	private int resultType;
	private int intGroupSize;
	private int mantGroupSize;
	private int recurringType;
	private int numOfRepeats;
	private int scale;
	private String decimalPoint;
	private String intSeparator;
	private String mantSeparator;
	private String[] divisionString;
	private String[] multiplicationString;
	private AngleUnit trig;
	private final Map<String, Couple<String, Function<Name.Params, SegmentBuilder>>> constants;
	private final Map<String, Couple<String, Function<Name.Params, SegmentBuilder>>> boundVariables;
}