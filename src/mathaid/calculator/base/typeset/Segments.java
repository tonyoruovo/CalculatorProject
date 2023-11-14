/**
 * 
 */
package mathaid.calculator.base.typeset;

/*
 * Date: 28 Aug 2022----------------------------------------------------------- 
 * Time created: 19:05:39---------------------------------------------------  
 * Package: calculator.typeset------------------------------------------------ 
 * Project: InputTestCode------------------------------------------------ 
 * File: Segments.java------------------------------------------------------ 
 * Class name: Segments------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Segments {
	private Segments() {
	}

	public static LinkedSegment empty(String placeHolder) {
		return new Empty(placeHolder);
	}

	public static LinkedSegment boundVariable(String format, String var) {
		return new BasicSegment(format, var, Segment.Type.VAR_BOUND);
	}

	public static LinkedSegment freeVariable(String format, String var) {
		return new BasicSegment(format, var, Segment.Type.VAR_FREE);
	}

	public static LinkedSegment constant(String format, String var) {
		return new BasicSegment(format, var, Segment.Type.CONSTANT);
	}

	public static LinkedSegment operator(String format, String op) {
		return new BasicSegment(format, op, Segment.Type.OPERATOR);
	}

	public static LinkedSegment fraction(LinkedSegment num, LinkedSegment denom) {
		return new CompositeSegment(new String[] { " \\frac{", " }{", "}" }, new String[] { " Rational[", " ,", " ]" },
				Segment.Type.FRACTION, new LinkedSegment[] { num, denom }, 0, 1);
	}

	public static LinkedSegment paren(boolean left) {
		return left ? new BasicSegment(" \\left(", " (", Segment.Type.L_PARENTHESIS)
				: new BasicSegment(" \\right)", " )", Segment.Type.R_PARENTHESIS);
	}

	public static LinkedSegment genericFunction(String texName, String name, LinkedSegment[] args, boolean degree,
			boolean defer) {
		return new Function(texName, name, degree, defer, false, false, null, args, 0, -1);
	}

	public static LinkedSegment exponential(LinkedSegment exponent) {
		return new CompositeSegment(new String[] { " e^{", " }" }, new String[] { " Exp[", " ]" },
				Segment.Type.FUNCTION, new LinkedSegment[] { exponent }, 0, -1);
	}

	public static LinkedSegment pow(LinkedSegment base, LinkedSegment exponent) {
		return new CompositeSegment(new String[] { "", " ^{", " }" }, new String[] { "", " ^(", " )" },
				Segment.Type.FUNCTION, new LinkedSegment[] { base, exponent }, 1, 0);
	}

	public static LinkedSegment root(LinkedSegment index, LinkedSegment radicand) {
		return new CompositeSegment(new String[] { " \\sqrt[", " ]{", " }" }, new String[] { " Power(", " , 1/", " )" },
				Segment.Type.FUNCTION, new LinkedSegment[] { index, radicand }, 0, 1);
	}

	public static LinkedSegment abs(LinkedSegment arg) {
		return new CompositeSegment(new String[] { " \\left|", " \\right|" }, new String[] { " Abs[", " ]" },
				Segment.Type.FUNCTION, new LinkedSegment[] { arg });
	}

	// TODO: test SYMJA whether valueSign is possible
	public static LinkedSegment limit(String index, LinkedSegment value,
			/* String valueSign, */LinkedSegment function) {
		return new CompositeSegment(new String[] { String.format(" \\lim\\limits_{%s \\to ", index), " }\\,", "" },
				new String[] { " Limit[", String.format(" , %s -> ", index), " ]" }, new int[] { 0, 1 },
				new int[] { 1, 0 }, Segment.Type.OBJECT, new LinkedSegment[] { value, function }, 1, 0);
	}
	
	public static LinkedSegment limit(LinkedSegment index, LinkedSegment value,
			/* String valueSign, */LinkedSegment function) {
		return new CompositeSegment(new String[] { " \\lim\\limits_{", " \\to ", " }\\,", "" },
				new String[] { " Limit[", " ,", " ->", " ]" }, new int[] { 0, 1, 2 },
				new int[] { 2, 0, 1 }, Segment.Type.OBJECT, new LinkedSegment[] { index, value, function }, 2, 1);
	}

	public static LinkedSegment logxy(LinkedSegment subscript, LinkedSegment argument) {
		return new CompositeSegment(new String[] { " \\log_{", " } \\left(", " \\right)" },
				new String[] { " Log[", " ,", " ]" }, new int[] { 0, 1 }, new int[] { 1, 0 }, Segment.Type.OBJECT,
				new LinkedSegment[] { subscript, argument }, 1, 0);
	}

	public static LinkedSegment sqrt(LinkedSegment argument) {
		return new CompositeSegment(new String[] { " \\sqrt{", " }" }, new String[] { " Sqrt[", " ]" },
				Segment.Type.FUNCTION, new LinkedSegment[] { argument });
	}

	public static LinkedSegment product(String index, LinkedSegment lower, LinkedSegment upper,
			LinkedSegment function) {
		return new CompositeSegment(new String[] { String.format(" \\prod\\limits_{%s = ", index), " }^{", " }\\,", "" },
				new String[] { " Product[", String.format(" ,{%s,", index), " ,", " }]" }, new int[] { 1, 0, 2 },
				new int[] { 2, 1, 0 }, Segment.Type.OBJECT, new LinkedSegment[] { upper, lower, function }, 0, 1);
	}
	
	public static LinkedSegment product(LinkedSegment index, LinkedSegment lower, LinkedSegment upper,
			LinkedSegment function) {
		return new CompositeSegment(new String[] { " \\prod\\limits_{", " =", " }^{", " }\\,", "" },
				new String[] { " Product[", " ,{", " ,", " ,", " }]" }, new int[] { 1, 2, 0, 3 },
				new int[] { 3, 1, 2, 0 }, Segment.Type.OBJECT, new LinkedSegment[] { upper, index, lower, function }, 0, 1);
	}

	public static LinkedSegment sum(String index, LinkedSegment lower, LinkedSegment upper, LinkedSegment function) {
		return new CompositeSegment(new String[] { String.format(" \\sum\\limits_{%s = ", index), " }^{", " }\\,", "" },
				new String[] { " Sum[", String.format(" ,{%s,", index), " ,", " }]" }, new int[] { 1, 0, 2 },
				new int[] { 2, 1, 0 }, Segment.Type.OBJECT, new LinkedSegment[] { upper, lower, function }, 0, 1);
	}
	
	public static LinkedSegment sum(LinkedSegment index, LinkedSegment lower, LinkedSegment upper, LinkedSegment function) {
		return new CompositeSegment(new String[] { " \\sum\\limits_{", " =", " }^{", " }\\,", "" },
				new String[] { " Sum[", " ,{", " ,", " ,", " }]" }, new int[] { 1, 2, 0, 3 },
				new int[] { 3, 1, 2, 0 }, Segment.Type.OBJECT, new LinkedSegment[] { upper, index, lower, function }, 0, 1);
	}

	public static LinkedSegment diff(String var, LinkedSegment function) {
		return new CompositeSegment(new String[] { String.format(" \\frac{\\mathrm{d}}{\\mathrm{d}\\, %s}\\,", var), "" },
				new String[] { String.format(" D[", " , %s]", var) }, new int[] { 0 }, new int[] { 0 },
				Segment.Type.OBJECT, new LinkedSegment[] { function }, 0, 0);
	}
	
	public static LinkedSegment diff(LinkedSegment var, LinkedSegment function) {
		return new CompositeSegment(new String[] { " \\frac{\\mathrm{d}}{\\mathrm{d}\\,", " }\\,", "" },
				new String[] { " D[", " ,", " ]" }, new int[] { 0, 1 }, new int[] { 1, 0 },
				Segment.Type.OBJECT, new LinkedSegment[] { var, function }, 0, 0);
	}

	public static LinkedSegment integral(String index, LinkedSegment lower, LinkedSegment upper,
			LinkedSegment function) {
		return new CompositeSegment(
				new String[] { String.format(" \\int\\limits_{%s = ", index), " }^{", " }\\,", "\\mathrm{d}" },
				new String[] { " Integrate[", String.format(" , {%s,", index), " ,", " }]" }, new int[] { 1, 0, 2 },
				new int[] { 2, 1, 0 }, Segment.Type.OBJECT, new LinkedSegment[] { upper, lower, function }, 0, 1);
	}

	public static LinkedSegment integral(LinkedSegment index, LinkedSegment lower, LinkedSegment upper,
			LinkedSegment function) {
		return new CompositeSegment(new String[] { " \\int\\limits_{", " =", " }^{", " }\\,", "\\mathrm{d}", "" },
				new String[] { " Integrate[", " , {", " ,", " ,", " }]" }, new int[] { 1, 2, 0, 3, 1 },
				new int[] { 3, 1, 2, 0 }, Segment.Type.OBJECT, new LinkedSegment[] { upper, index, lower, function }, 0,
				1);
	}

	public static LinkedSegment floor(LinkedSegment function) {
		return new CompositeSegment(new String[] { " \\left\\lfloor", " \\right\\rfloor" },
				new String[] { " Floor[", " ]" }, Segment.Type.FUNCTION, new LinkedSegment[] { function });
	}

	public static LinkedSegment ceil(LinkedSegment function) {
		return new CompositeSegment(new String[] { " \\left\\lceil", " \\right\\rceil" },
				new String[] { " Ceiling[", " ]" }, Segment.Type.FUNCTION, new LinkedSegment[] { function });
	}

	public static LinkedSegment array(LinkedSegment[] elements) {
		return new Array(elements);
	}
}
