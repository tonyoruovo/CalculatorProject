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
 * This class consists exclusively of static utility methods for creating
 * {@code LinkedSegments} which are commonly used in Mathematics and the
 * sciences.
 * <p>
 * The terms segment, tree, sub-tree, subtree, node, nodes, segments all refer
 * to an instance of the {@code LinkedSegment} interface. THe terms number node,
 * number nodes, number tree, number trees, number sub-tree, number sub-trees,
 * all refer to a {@code LinkedSegment} instance where all the nodes are an
 * instance of the {@code Digit} class.
 * <p>
 * All {@code LinkedSegment}s returned by the methods are in TeX
 * ({@linkplain LinkedSegment#format format}), and their
 * {@link LinkedSegment#toString(Appendable, Log, java.util.List)} method can
 * only be evaluated by the Symja engine.
 * <p>
 * Unless otherwise specified, all arguments expect a non-null value. In
 * applications where a default value may be needed, use {@code new Empty()}
 * instead of <code>null</code>.
 * 
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Segments {
	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 21:32:10 ---------------------------------------------------
	 */
	/**
	 * Privately constructed to prevent instantiation by user.
	 */
	private Segments() {
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 21:32:51 ---------------------------------------------------
	 */
	/**
	 * Creates an empty childless segment with no sibling using the the given TeX
	 * code as the placeholder when displayed.
	 * 
	 * @param placeHolder the TeX code to be displayed in the place of this
	 *                    {@code Empty} value.
	 * @return the {@link Empty} segment.
	 */
	public static LinkedSegment empty(String placeHolder) {
		return new Empty(placeHolder);
	}

	/*
	 * Date: 22 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:15:52 ---------------------------------------------------
	 */
	/**
	 * Creates a childless segment with no sibling for mathematical variables
	 * separating it's MATHJAX format from the it's SYMJA expression with the type
	 * {@link Segment.Type#VAR_BOUND}.
	 * 
	 * @param format the MATHJAX format
	 * @param var    the SYMJA expression
	 * @return a segment created from the arguments.
	 */
	public static LinkedSegment boundVariable(String format, String var) {
		return new BasicSegment(format, var, Segment.Type.VAR_BOUND);
	}

	/*
	 * Date: 22 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:15:52 ---------------------------------------------------
	 */
	/**
	 * Creates a childless segment with no sibling for mathematical variables
	 * separating it's MATHJAX format from the it's SYMJA expression with the type
	 * {@link Segment.Type#VAR_FREE}.
	 * 
	 * @param format the MATHJAX format
	 * @param var    the SYMJA expression
	 * @return a segment created from the arguments.
	 */
	public static LinkedSegment freeVariable(String format, String var) {
		return new BasicSegment(format, var, Segment.Type.VAR_FREE);
	}

	/*
	 * Date: 22 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:15:52 ---------------------------------------------------
	 */
	/**
	 * Creates a childless segment with no sibling for mathematical constants
	 * separating it's MATHJAX format from the it's SYMJA expression with the type
	 * {@link Segment.Type#CONSTANT}.
	 * 
	 * @param format the MATHJAX format
	 * @param var    the SYMJA expression
	 * @return a segment created from the arguments.
	 */
	public static LinkedSegment constant(String format, String var) {
		return new BasicSegment(format, var, Segment.Type.CONSTANT);
	}

	/*
	 * Date: 22 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:15:52 ---------------------------------------------------
	 */
	/**
	 * Creates a childless segment with no sibling for operators separating it's
	 * MATHJAX format from the it's SYMJA expression with the type
	 * {@link Segment.Type#OPERATOR}.
	 * 
	 * @param format the MATHJAX format
	 * @param op     the SYMJA expression
	 * @return a segment created from the arguments.
	 */
	public static LinkedSegment operator(String format, String op) {
		return new BasicSegment(format, op, Segment.Type.OPERATOR);
	}

	/*
	 * Date: 22 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:28:07 ---------------------------------------------------
	 */
	/**
	 * Creates a segment with no sibling for the common fraction using the specified
	 * {@code num} and {@code denom} arguments as it's children with the superscript
	 * index {@code 0} and subscript index {@code 1} respectively.
	 * 
	 * @param num   the child of the returned segment representing the numerator.
	 * @param denom the child of the returned segment representing the denominator.
	 * @return a common fraction segment created from the arguments.
	 */
	public static LinkedSegment fraction(LinkedSegment num, LinkedSegment denom) {
		return new CompositeSegment(new String[] { " \\frac{", " }{", "}" }, new String[] { " Rational[", " ,", " ]" },
				Segment.Type.FRACTION, new LinkedSegment[] { num, denom }, 0, 1);
	}

	/*
	 * Date: 22 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:33:55 ---------------------------------------------------
	 */
	/**
	 * Creates a childless segment with no sibling for a parenthesis '(' or ')'.
	 * 
	 * @param left specifies that this method should return a left-parenthesis
	 *             instead.
	 * @return a left/right parenthesis depending on the argument.
	 */
	public static LinkedSegment paren(boolean left) {
		return left ? new BasicSegment(" \\left(", " (", Segment.Type.L_PARENTHESIS)
				: new BasicSegment(" \\right)", " )", Segment.Type.R_PARENTHESIS);
	}

	/*
	 * Date: 22 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:36:23 ---------------------------------------------------
	 */
	/**
	 * Creates a segment with no sibling for a mathematical function which contains
	 * a name and it's argument.
	 * 
	 * @param texName the MATHJAX name of the function.
	 * @param name    the SYMJA name of the function.
	 * @param args    the arguments of the function which will serve as the children
	 *                of the returned segment.
	 * @param degree  specifies if the argument(s) is/are numerical and are in
	 *                degrees (instead of radians).
	 * @param defer   specifies whether to evaluate as a normal function (such as
	 *                sin(60)) or be treated as an unknown variable (such as f(x)).
	 * @return a function segment assembled from the arguments.
	 */
	public static LinkedSegment genericFunction(String texName, String name, LinkedSegment[] args, boolean degree,
			boolean defer) {
		return new Function(texName, name, degree, defer, false, false, null, args, 0, -1);
	}

	/*
	 * Date: 22 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:42:33 ---------------------------------------------------
	 */
	/**
	 * Creates segment with no sibling for the exponential function (like
	 * {@link Math#exp(double)}) using the argument as it's child.
	 * 
	 * @param exponent the argument to the exponential function
	 * @return an exponential function segment using the argument.
	 */
	public static LinkedSegment exponential(LinkedSegment exponent) {
		return new CompositeSegment(new String[] { " e^{", " }" }, new String[] { " Exp[", " ]" },
				Segment.Type.FUNCTION, new LinkedSegment[] { exponent }, 0, -1);
	}

	/*
	 * Date: 22 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:44:24 ---------------------------------------------------
	 */
	/**
	 * Creates a segment with no sibling for the mathematical power function (like
	 * {@link Math#pow(double, double)}) from the given {@code base} and
	 * {@code exponent}.
	 * 
	 * @param base     the base which is the superscript child at index 0.
	 * @param exponent the exponent which is the subscript child at index 1.
	 * @return the power function segment created from the arguments.
	 */
	public static LinkedSegment pow(LinkedSegment base, LinkedSegment exponent) {
		return new CompositeSegment(new String[] { "", " ^{", " }" }, new String[] { "", " ^(", " )" },
				Segment.Type.FUNCTION, new LinkedSegment[] { base, exponent }, 1, 0);
	}

	/*
	 * Date: 22 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:47:47 ---------------------------------------------------
	 */
	/**
	 * Creates a segment with no sibling for the mathematical root function from the
	 * given index and radicand.
	 * 
	 * @param index    the child at the superscript index 0.
	 * @param radicand the child at the subscript index 1.
	 * @return the root function segment created from the arguments.
	 */
	public static LinkedSegment root(LinkedSegment index, LinkedSegment radicand) {
		return new CompositeSegment(new String[] { " \\sqrt[", " ]{", " }" }, new String[] { " Power(", " , 1/", " )" },
				Segment.Type.FUNCTION, new LinkedSegment[] { index, radicand }, 0, 1);
	}

	/*
	 * Date: 22 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:49:29 ---------------------------------------------------
	 */
	/**
	 * Creates a segment with no sibling for the absolute function.
	 * 
	 * @param arg the child of the returned segment i.e the argument of the absolute
	 *            function.
	 * @return the absolute function segment.
	 */
	public static LinkedSegment abs(LinkedSegment arg) {
		return new CompositeSegment(new String[] { " \\left|", " \\right|" }, new String[] { " Abs[", " ]" },
				Segment.Type.FUNCTION, new LinkedSegment[] { arg });
	}

	/*
	 * Date: 22 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:52:01 ---------------------------------------------------
	 */
	/**
	 * Creates the segment with no sibling for the calculus function limit. An
	 * example is:
	 * 
	 * <pre>
	 * <code> 
	 *	LinkedSegment f = Segments.limit("x", Segments.freeVariable("\\infty", "infinity"),
	 *			Segments.exponential(Segments.freeVariable("x", "x")));
	 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
	 *	f.toString(System.out, null, l);// prints: Limit[ Exp[x ] , x -> infinity ]
	 *	out.println();
	 *	l.clear();
	 *	l.add(-1);
	 *	f.format(System.out, Formatter.empty(), l);// prints: \lim\limits_{x \to \infty }\, e^{x }
	 *</code>
	 * </pre>
	 * 
	 * @param index    a string transformed into a static part of the image of the
	 *                 returned segment. This is the value at the left-side of the
	 *                 arrow. This is not a child of the returned segment i.e it
	 *                 cannot be edited as it is part of the segment.
	 * @param value    the child of the returned segment representing the object of
	 *                 the {@code index} i.e right-side of where the arrow points
	 *                 to. This is the subscript whose index is {@code 0}.
	 * @param function the child of the created segment for the object of the limit
	 *                 function itself. This is the superscript whose index is
	 *                 {@code 1}.
	 * @return a segment created from the arguments.
	 */
	// TODO: test SYMJA whether valueSign is possible
	public static LinkedSegment limit(String index, LinkedSegment value,
			/* String valueSign, */LinkedSegment function) {
		return new CompositeSegment(new String[] { String.format(" \\lim\\limits_{%s \\to ", index), " }\\,", "" },
				new String[] { " Limit[", String.format(" , %s -> ", index), " ]" }, new int[] { 0, 1 },
				new int[] { 1, 0 }, Segment.Type.OBJECT, new LinkedSegment[] { value, function }, 1, 0);
	}

	/*
	 * Date: 22 Nov 2023 -----------------------------------------------------------
	 * Time created: 17:52:02 ---------------------------------------------------
	 */
	/**
	 * Creates the segment with no sibling for the calculus function limit. An
	 * example is:
	 * 
	 * <pre>
	 * <code> 
	 *	LinkedSegment f = Segments.limit(Segments.freeVariable("x", "x"), Segments.freeVariable("\\infty", "infinity"),
	 *			Segments.exponential(Segments.freeVariable("x", "x")));
	 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
	 *	f.toString(System.out, null, l);// prints: Limit[ Exp[x ] , x -> infinity ]
	 *	out.println();
	 *	l.clear();
	 *	l.add(-1);
	 *	f.format(System.out, Formatter.empty(), l);// prints: \lim\limits_{x \to \infty }\, e^{x }
	 *</code>
	 * </pre>
	 * 
	 * @param index    the child of the created segment representing the index of
	 *                 the limit.
	 * @param value    the child of the returned segment representing the object of
	 *                 the {@code index} i.e right-side of where the arrow points
	 *                 to. This is the superscript whose index is {@code 1}.
	 * @param function the child of the created segment for the object of the limit
	 *                 function itself. This is the subscript whose index is
	 *                 {@code o}.
	 * @return a segment created from the arguments.
	 */
	public static LinkedSegment limit(LinkedSegment index, LinkedSegment value,
			/* String valueSign, */LinkedSegment function) {
		return new CompositeSegment(new String[] { " \\lim\\limits_{", " \\to ", " }\\,", "" },
				new String[] { " Limit[", " ,", " ->", " ]" }, new int[] { 0, 1, 2 }, new int[] { 2, 0, 1 },
				Segment.Type.OBJECT, new LinkedSegment[] { index, value, function }, 2, 1);
	}

	/*
	 * Date: 22 Nov 2023 -----------------------------------------------------------
	 * Time created: 17:58:43 ---------------------------------------------------
	 */
	/**
	 * Creates a segment with no sibling for the Mathematical log function.
	 * 
	 * @param subscript the value representing the base of the logarithm. This is
	 *                  the subscript child whose index is 0
	 * @param argument  the argument of the function. The child index is 1 and it is
	 *                  the superscript.
	 * @return a segment created from the arguments.
	 */
	public static LinkedSegment logxy(LinkedSegment subscript, LinkedSegment argument) {
		return new CompositeSegment(new String[] { " \\log_{", " } \\left(", " \\right)" },
				new String[] { " Log[", " ,", " ]" }, new int[] { 0, 1 }, new int[] { 1, 0 }, Segment.Type.OBJECT,
				new LinkedSegment[] { subscript, argument }, 1, 0);
	}

	/*
	 * Date: 22 Nov 2023 -----------------------------------------------------------
	 * Time created: 18:01:59 ---------------------------------------------------
	 */
	/**
	 * Creates a segment with no sibling for the square root function.
	 * 
	 * @param argument the argument of the square root function which is the child
	 *                 of the returned segment.
	 * @return a segment created from the arguments.
	 */
	public static LinkedSegment sqrt(LinkedSegment argument) {
		return new CompositeSegment(new String[] { " \\sqrt{", " }" }, new String[] { " Sqrt[", " ]" },
				Segment.Type.FUNCTION, new LinkedSegment[] { argument });
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 03:20:54 ---------------------------------------------------
	 */
	/**
	 * Creates the segment with no sibling for the product function. An example of
	 * this is:
	 * 
	 * <pre>
	 * <code>
	 *	LinkedSegment f = Segments.product("x", Digits.toSegment(Utility.i(0), 10, new DigitPunc()),
	 *			Segments.freeVariable("\\infty", "infinity"), Segments.exponential(Segments.freeVariable("x", "x")));
	 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
	 *	f.toString(System.out, null, l);// prints:  Product[ Exp[x ] ,{x,0 ,infinity }]
	 *	out.println();
	 *	l.clear();
	 *	l.add(-1);
	 *	f.format(System.out, Formatter.empty(), l);// prints: \prod\limits_{x = 0 }^{\infty }\, e^{x }
	 *</code>
	 * </pre>
	 * 
	 * @param index    a string transformed into a static part of the image of the
	 *                 returned segment. This is the value at the left-side of the
	 *                 equals sign. This is not a child of the returned segment i.e
	 *                 it cannot be edited as it is part of the segment.
	 * @param lower    the child at the 1st index of the created segment. This is
	 *                 the subscript.
	 * @param upper    the child at the 0 index if the returned segment. This the
	 *                 superscript.
	 * @param function the segment for the main function of the product. This the
	 *                 child at the second index.
	 * @return a segment created from the arguments.
	 */
	public static LinkedSegment product(String index, LinkedSegment lower, LinkedSegment upper,
			LinkedSegment function) {
		return new CompositeSegment(
				new String[] { String.format(" \\prod\\limits_{%s = ", index), " }^{", " }\\,", "" },
				new String[] { " Product[", String.format(" ,{%s,", index), " ,", " }]" }, new int[] { 1, 0, 2 },
				new int[] { 2, 1, 0 }, Segment.Type.OBJECT, new LinkedSegment[] { upper, lower, function }, 0, 1);
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 03:50:31 ---------------------------------------------------
	 */
	/**
	 * Creates the segment with no sibling for the product function. An example of
	 * this is:
	 * 
	 * <pre>
	 * <code>
	 *	LinkedSegment f = Segments.product(Segments.freeVariable("x", "x"), Digits.toSegment(Utility.i(0), 10, new DigitPunc()),
	 *			Segments.freeVariable("\\infty", "infinity"), Segments.exponential(Segments.freeVariable("x", "x")));
	 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
	 *	f.toString(System.out, null, l);// prints:  Product[ Exp[x ] ,{x,0 ,infinity }]
	 *	out.println();
	 *	l.clear();
	 *	l.add(-1);
	 *	f.format(System.out, Formatter.empty(), l);// prints: \prod\limits_{x = 0 }^{\infty }\, e^{x }
	 *</code>
	 * </pre>
	 * 
	 * @param index    the child of the created segment representing the index of
	 *                 the limit.
	 * @param lower    the child at the 1st index of the created segment. This is
	 *                 the subscript.
	 * @param upper    the child at the 0 index if the returned segment. This the
	 *                 superscript.
	 * @param function the segment for the main function of the product. This the
	 *                 child at the second index.
	 * @return a segment created from the arguments.
	 */
	public static LinkedSegment product(LinkedSegment index, LinkedSegment lower, LinkedSegment upper,
			LinkedSegment function) {
		return new CompositeSegment(new String[] { " \\prod\\limits_{", " =", " }^{", " }\\,", "" },
				new String[] { " Product[", " ,{", " ,", " ,", " }]" }, new int[] { 1, 2, 0, 3 },
				new int[] { 3, 1, 2, 0 }, Segment.Type.OBJECT, new LinkedSegment[] { upper, index, lower, function }, 0,
				1);
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 03:53:42 ---------------------------------------------------
	 */
	/**
	 * Creates the segment with no sibling for the sum function. An example of
	 * this is:
	 * 
	 * <pre>
	 * <code>
	 *	LinkedSegment f = Segments.sum("x", Digits.toSegment(Utility.i(0), 10, new DigitPunc()),
	 *			Segments.freeVariable("\\infty", "infinity"), Segments.exponential(Segments.freeVariable("x", "x")));
	 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
	 *	f.toString(System.out, null, l);// prints:  Sum[ Exp[x ] ,{x,0 ,infinity }]
	 *	out.println();
	 *	l.clear();
	 *	l.add(-1);
	 *	f.format(System.out, Formatter.empty(), l);// prints: \sum\limits_{x = 0 }^{\infty }\, e^{x }
	 *</code>
	 * </pre>
	 * 
	 * @param index    a string transformed into a static part of the image of the
	 *                 returned segment. This is the value at the left-side of the
	 *                 equals sign. This is not a child of the returned segment i.e
	 *                 it cannot be edited as it is part of the segment.
	 * @param lower    the child at the 1st index of the created segment. This is
	 *                 the subscript.
	 * @param upper    the child at the 0 index if the returned segment. This the
	 *                 superscript.
	 * @param function the segment for the main function of the sum. This the child
	 *                 at the second index.
	 * @return a segment created from the arguments.
	 */
	public static LinkedSegment sum(String index, LinkedSegment lower, LinkedSegment upper, LinkedSegment function) {
		return new CompositeSegment(new String[] { String.format(" \\sum\\limits_{%s = ", index), " }^{", " }\\,", "" },
				new String[] { " Sum[", String.format(" ,{%s,", index), " ,", " }]" }, new int[] { 1, 0, 2 },
				new int[] { 2, 1, 0 }, Segment.Type.OBJECT, new LinkedSegment[] { upper, lower, function }, 0, 1);
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:09:14 ---------------------------------------------------
	 */
	/**
	 * Creates the segment with no sibling for the sum function. An example of
	 * this is:
	 * 
	 * <pre>
	 * <code>
	 *	LinkedSegment f = Segments.sum(Segments.freeVariable("x", "x"), Digits.toSegment(Utility.i(0), 10, new DigitPunc()),
	 *			Segments.freeVariable("\\infty", "infinity"), Segments.exponential(Segments.freeVariable("x", "x")));
	 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
	 *	f.toString(System.out, null, l);// prints:  Sum[ Exp[x ] ,{x,0 ,infinity }]
	 *	out.println();
	 *	l.clear();
	 *	l.add(-1);
	 *	f.format(System.out, Formatter.empty(), l);// prints: \sum\limits_{x = 0 }^{\infty }\, e^{x }
	 *</code>
	 * </pre>
	 * 
	 * @param index    the child of the created segment representing the index of
	 *                 the limit. It is the child at index 1. This is
	 *                 the subscript.
	 * @param lower    the child at the 3rd index of the created segment.
	 * @param upper    the child at the 0 index if the returned segment. This the
	 *                 superscript.
	 * @param function the segment for the main function of the sum. This the child
	 *                 at the second index.
	 * @return a segment created from the arguments.
	 */
	public static LinkedSegment sum(LinkedSegment index, LinkedSegment lower, LinkedSegment upper,
			LinkedSegment function) {
		return new CompositeSegment(new String[] { " \\sum\\limits_{", " =", " }^{", " }\\,", "" },
				new String[] { " Sum[", " ,{", " ,", " ,", " }]" }, new int[] { 1, 2, 0, 3 }, new int[] { 3, 1, 2, 0 },
				Segment.Type.OBJECT, new LinkedSegment[] { upper, index, lower, function }, 0, 1);
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:12:19 ---------------------------------------------------
	 */
	/**
	 * Creates the segment with no sibling for the differential function.
	 * 
	 * @param var      a static non-editable part of the image of the segment. This
	 *                 not a child.
	 * @param function the sole child of the returned segment. This is both the
	 *                 sub/superscript.
	 * @return a segment from the arguments.
	 */
	public static LinkedSegment diff(String var, LinkedSegment function) {
		return new CompositeSegment(
				new String[] { String.format(" \\frac{\\mathrm{d}}{\\mathrm{d}\\, %s}\\,", var), "" },
				new String[] { String.format(" D[", " , %s]", var) }, new int[] { 0 }, new int[] { 0 },
				Segment.Type.OBJECT, new LinkedSegment[] { function }, 0, 0);
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:17:42 ---------------------------------------------------
	 */
	/**
	 * Creates the segment with no sibling for the differential function. An example
	 * is:
	 * 
	 * <pre>
	 * <code>
		LinkedSegment f = Segments.diff(Segments.freeVariable("x", "x"), Segments.exponential(Segments.freeVariable("x", "x")));
		List<Integer> l = new ArrayList<>(Arrays.asList(-1));
		f.toString(System.out, null, l);// prints:  D[ Exp[x ] ,x ]
		out.println();
		l.clear();
		l.add(-1);
		f.format(System.out, Formatter.empty(), l);// prints: \frac{\mathrm{d}}{\mathrm{d}\,x }\, e^{x }
		</code>
	 * </pre>
	 * 
	 * @param var      the variable of the differential function. It is the child of
	 *                 the segment located at index 0. It is both the
	 *                 super/subscript.
	 * @param function the function to be differentiated. It is the child at index
	 *                 1.
	 * @return a segment from the arguments.
	 */
	public static LinkedSegment diff(LinkedSegment var, LinkedSegment function) {
		return new CompositeSegment(new String[] { " \\frac{\\mathrm{d}}{\\mathrm{d}\\,", " }\\,", "" },
				new String[] { " D[", " ,", " ]" }, new int[] { 0, 1 }, new int[] { 1, 0 }, Segment.Type.OBJECT,
				new LinkedSegment[] { var, function }, 0, 0);
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:23:08 ---------------------------------------------------
	 */
	/**
	 * Creates the segment with no sibling for the integral function. An example of
	 * this is:
	 * 
	 * <pre>
	 * <code>
	 *	LinkedSegment f = Segments.integral("x", Digits.toSegment(Utility.i(0), 10, new DigitPunc()),
	 *			Segments.freeVariable("\\infty", "infinity"), Segments.exponential(Segments.freeVariable("x", "x")));
	 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
	 *	f.toString(System.out, null, l);// prints:  Integrate[ Exp[x ] , {x ,0 ,infinity }]
	 *	out.println();
	 *	l.clear();
	 *	l.add(-1);
	 *	f.format(System.out, Formatter.empty(), l);// prints: \int\limits_{x =0 }^{\infty }\, e^{x }\mathrm{d}x
	 *</code>
	 * </pre>
	 * @param index    a string transformed into a static part of the image of the
	 *                 returned segment. This is the value at the left-side of the
	 *                 equals sign. This is not a child of the returned segment i.e
	 *                 it cannot be edited as it is part of the segment.
	 * @param lower    the child at the 1st index of the created segment. This is
	 *                 the subscript.
	 * @param upper    the child at the 0 index if the returned segment. This the
	 *                 superscript.
	 * @param function the segment for the main function of the integral. This the child
	 *                 at the 2nd index.
	 * @return a segment created from the arguments.
	 */
	public static LinkedSegment integral(String index, LinkedSegment lower, LinkedSegment upper,
			LinkedSegment function) {
		return new CompositeSegment(
				new String[] { String.format(" \\int\\limits_{%s = ", index), " }^{", " }\\,", "\\mathrm{d}" },
				new String[] { " Integrate[", String.format(" , {%s,", index), " ,", " }]" }, new int[] { 1, 0, 2 },
				new int[] { 2, 1, 0 }, Segment.Type.OBJECT, new LinkedSegment[] { upper, lower, function }, 0, 1);
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:27:14 ---------------------------------------------------
	 */
	/**
	 * Creates the segment with no sibling for the integral function. An example of
	 * this is:
	 * 
	 * <pre>
	 * <code>
	 *	LinkedSegment f = Segments.integral(Segments.freeVariable("x", "x"), Digits.toSegment(Utility.i(0), 10, new DigitPunc()),
	 *			Segments.freeVariable("\\infty", "infinity"), Segments.exponential(Segments.freeVariable("x", "x")));
	 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
	 *	f.toString(System.out, null, l);// prints:  Integrate[ Exp[x ] , {x ,0 ,infinity }]
	 *	out.println();
	 *	l.clear();
	 *	l.add(-1);
	 *	f.format(System.out, Formatter.empty(), l);// prints: \int\limits_{x =0 }^{\infty }\, e^{x }\mathrm{d}x
	 *</code>
	 * </pre>
	 * 
	 * @param index    the child at the 1st index of the created segment representing the index of
	 *                 the limit. This is
	 *                 the subscript.
	 * @param lower    the child at the 2nd index of the created segment.
	 * @param upper    the child at the 0 index if the returned segment. This the
	 *                 superscript.
	 * @param function the segment for the main function of the integral. This the child
	 *                 at the 3rd index.
	 * @return a segment created from the arguments.
	 */
	public static LinkedSegment integral(LinkedSegment index, LinkedSegment lower, LinkedSegment upper,
			LinkedSegment function) {
		return new CompositeSegment(new String[] { " \\int\\limits_{", " =", " }^{", " }\\,", "\\mathrm{d}", "" },
				new String[] { " Integrate[", " , {", " ,", " ,", " }]" }, new int[] { 1, 2, 0, 3, 1 },
				new int[] { 3, 1, 2, 0 }, Segment.Type.OBJECT, new LinkedSegment[] { upper, index, lower, function }, 0,
				1);
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:40:53 ---------------------------------------------------
	 */
	/**
	 * Creates the segment without a sibling for the floor function using the argument as the function's argument.
	 * @param function the argument of the floor function.
	 * @return a segment created from the arguments.
	 */
	public static LinkedSegment floor(LinkedSegment function) {
		return new CompositeSegment(new String[] { " \\left\\lfloor", " \\right\\rfloor" },
				new String[] { " Floor[", " ]" }, Segment.Type.FUNCTION, new LinkedSegment[] { function });
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:43:49 ---------------------------------------------------
	 */
	/**
	 * Creates the segment without a sibling for the ceil function using the argument as the function's argument.
	 * @param function the argument of the ceil function.
	 * @return a segment created from the arguments.
	 */
	public static LinkedSegment ceil(LinkedSegment function) {
		return new CompositeSegment(new String[] { " \\left\\lceil", " \\right\\rceil" },
				new String[] { " Ceiling[", " ]" }, Segment.Type.FUNCTION, new LinkedSegment[] { function });
	}

	/*
	 * Date: 21 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:58:08 ---------------------------------------------------
	 */
	/**
	 * Creates a segment without a sibling for an array.
	 * 
	 * @param elements the elements of the array
	 * @return a segment whose elements are the array.
	 */
	public static LinkedSegment array(LinkedSegment[] elements) {
		return new Array(elements);
	}
	
	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:46:48 ---------------------------------------------------
	 */
	/**
	 * Creates a childless segment with no sibling for the Mathematical 'x' free variable.
	 * @return the created segment representing the 'x' variable
	 */
	public static LinkedSegment x() {
		return freeVariable("x", "x");
	}
	
	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:46:55 ---------------------------------------------------
	 */
	/**
	 * Creates a childless segment with no sibling for the Mathematical 'y' free variable.
	 * @return the created segment representing the 'y' variable
	 */
	public static LinkedSegment y() {
		return freeVariable("y", "y");
	}
	
	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 05:47:00 ---------------------------------------------------
	 */
	/**
	 * Creates a childless segment with no sibling for the Mathematical 'z' free variable.
	 * @return the created segment representing the 'z' variable
	 */
	public static LinkedSegment z() {
		return freeVariable("z", "z");
	}

}
