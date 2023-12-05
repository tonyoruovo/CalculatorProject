/*
 * Date: 10 Nov 2023 -----------------------------------------------------------
 * Time created: 15:23:15 ---------------------------------------------------
 */
/**
 * 
 */
package mathaid.calculator.base.evaluator;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import mathaid.calculator.base.converter.AngleUnit;
import mathaid.calculator.base.evaluator.parser.PrattParser;
import mathaid.calculator.base.evaluator.parser.ProgrammerLexer;
import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression;
import mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression;
import mathaid.calculator.base.evaluator.parser.expression.programmer.PExpression.Params;
import mathaid.calculator.base.typeset.LinkedSegment;
import mathaid.calculator.base.typeset.SegmentBuilder;
import mathaid.calculator.base.util.Tuple.Couple;
import mathaid.functional.Supplier.Function;

public class Programmer implements Evaluator<LinkedSegment>, PExpression.Params {
	
	/*
	 * Date: 11 Nov 2023 -----------------------------------------------------------
	 * Time created: 09:01:42 ---------------------------------------------------
	 */
	/**
	 */
	public Programmer() {
		parser = new PrattParser<>();
		this.lexer = new ProgrammerLexer();
		this.constants = new HashMap<>();
		this.boundVariables = new HashMap<>();
	}
	
	/*
	 * Most Recent Date: 8 Oct 2022-----------------------------------------------
	 * Most recent time created: 09:03:56--------------------------------------
	 */
	/**
	 * @return
	 */
	public int getModifier() {
		return modifier;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public int getResultType() {
		return rt;
	}

	public void setResultType(int resultType) {
		rt = resultType;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public String getDecimalPoint() {
		return dp;
	}

	public void setDecimalPoint(char c) {
		dp = String.valueOf(c);
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public String getIntSeparator() {
		return is;
	}

	public void setIntSeparator(String s) {
		is = s;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public String getMantSeparator() {
		return ms;
	}

	public void setMantSeparator(String s) {
		ms = s;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public int getIntGroupSize() {
		return igs;
	}

	public void setIntGroupSize(int size) {
		igs = size;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public int getMantGroupSize() {
		return mgs;
	}

	public void setMantGroupSize(int size) {
		mgs = size;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public int getRadix() {
		return r;
	}

	public void setRadix(int r) {
		this.r = r;
		lexer.setRadix(r);
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public Map<String, Couple<String, Function<Params, SegmentBuilder>>> getConstants() {
		return constants;
	}

	public void addConstant(String name, Couple<String, Function<Params, SegmentBuilder>> constant) {
		constants.put(name, constant);
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public Map<String, Couple<String, Function<Params, SegmentBuilder>>> getBoundVariables() {
		return boundVariables;
	}

	public void addBoundVariable(String name, Couple<String, Function<Params, SegmentBuilder>> boundVariable) {
		boundVariables.put(name, boundVariable);
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public String[] getDivisionString() {
		return div;
	}

	public void setDivisionString(String[] div) {
		this.div = div;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public String[] getMultiplicationString() {
		return mul;
	}

	public void setMultiplicationString(String[] mul) {
		this.mul = mul;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public AngleUnit getTrig() {
		return AngleUnit.RAD;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public int getBitRepresentation() {
		return br;
	}

	public void setBitRepresentation(int bitRep) {
		br = bitRep;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public int getEndianess() {
		return end;
	}

	public void setEndianess(int e) {
		this.end = e;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public int getBitLength() {
		return bl;
	}

	public void setBitLength(int bitLength) {
		bl = bitLength;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public RoundingMode getRoundingMode() {
		return rm;
	}

	public void setRoundingMode(RoundingMode rm) {
		this.rm = rm;
	}

	/*
	 * Most Recent Date: 11 Dec 2022-----------------------------------------------
	 * Most recent time created: 13:49:22--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public int getShift() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * Date: 11 Nov 2023 -----------------------------------------------------------
	 * Time created: 09:01:10 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @param expression
	 * @return
	 * @throws RuntimeException
	 */
	@Override
	public LinkedSegment evaluate(String expression) throws RuntimeException {
		lexer.setSource(expression);
		EvaluatableExpression<Params> f = parser.parse(lexer, lexer.getSyntax(), this);
		SegmentBuilder sb = new SegmentBuilder();
		f.format(sb);
		return sb.toSegment();
	}
	
	private int modifier;

	private final ProgrammerLexer lexer;
	private final PrattParser<EvaluatableExpression<Params>, Params> parser;

	private final Map<String, Couple<String, Function<Params, SegmentBuilder>>> constants;
	private final Map<String, Couple<String, Function<Params, SegmentBuilder>>> boundVariables;

	// values
	private RoundingMode rm;
	private int bl;// bitlength
	private int end;// endianess
	private int br;// bit representation
	private int r;// radix
	private int igs;// integer group size
	private int mgs;// mantissa group size
	private int rt;// result type
	private String[] mul;// multiplication strings
	private String[] div;// division strings
	private String is;// integer separator
	private String ms;// mantissa separator
	private String dp;// decimal point
}
//	private static class Converter implements Evaluator<String> {}
//	private static class Graphing implements Evaluator<String> {}