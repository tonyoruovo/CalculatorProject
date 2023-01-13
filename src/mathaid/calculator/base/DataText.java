/**
 * 
 */
package mathaid.calculator.base;

import java.io.StringWriter;
import java.text.Collator;
import java.util.Locale;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.TeXUtilities;
import org.matheclipse.core.interfaces.IExpr;

import mathaid.ExceptionMessage;
import mathaid.NullException;

/*
 * Date: 4 Apr 2021----------------------------------------------------------- 
 * Time created: 09:51:00---------------------------------------------------  
 * Package: mathaid.calculator.base------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: DataText.java------------------------------------------------------ 
 * Class name: DataText------------------------------------------------ 
 */
/**
 * An object used for passing text (and TeX) related info between components in
 * the calculator. It is used by classes that which to process formatted text
 * (in TeX form) and still retain the original string. When this object is used
 * for such a purpose, both the source and the formatted TeX string must be
 * defined either at the constructors or using an evaluator to convert the
 * string.
 * <p>
 * It has 3 forms, the source form, the symja form (used by the internal symja
 * evaluator in the {@code Scientific} class) and the formatted TeX form. The
 * source form is the original unformatted string, while the formatted string is
 * a string in TeX notation. For example, when an expression such as
 * <code>(2^5*y)/sqrt(x)</code> is sent to the evaluator, it is sent in this
 * object in which it is clearly defined as a source string and as a TeX string.
 * The source string (defined by {@link #getSymjaString()}) after evaluation may
 * be the expression <code>(32*x*y)/Sqrt</code> and the TeX (defined by
 * {@link #getTeXString()}) string may be translated to the expression
 * {@code \frac{32\,x\,y}{sqrt}}. There is also {@link #getRawString()} which is
 * really the same as the symja string unless this object was created using the
 * static {@link #fromPlainString(String, EvalEngine)} method.
 * </p>
 * <p>
 * This class also includes a {@code Collator} object (specified by
 * {@link #getCollator()}) for use within this API for comparing US English
 * related strings.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class DataText {

	/*
	 * Date: 9 Aug 2021-----------------------------------------------------------
	 * Time created: 09:47:27--------------------------------------------
	 */
	/**
	 * An object for comparing US English-related strings.
	 * <p>
	 * Throughout this API, there locale sensitive data, however one may want to
	 * just see if one string equals another within the Latin alphabet, this object
	 * helps with such scenarios.
	 * </p>
	 * 
	 * @return a {@code Collator} object specified with {@link Locale#US}.
	 * @see Collator
	 */
	public static Collator getCollator() {
		return collator;
	}

	/*
	 * Date: 4 Apr 2021-----------------------------------------------------------
	 * Time created: 09:51:00---------------------------------------------------
	 */
	/**
	 * Protected constructor for constructing a {@code DataText} object with the the
	 * specified TeX form, string source and symja string.
	 * 
	 * @param texString   a {@code String} in TeX notation.
	 * @param symjaString the first argument {@code String} in symja or java syntax.
	 * @param rawString   the first argument {@code String} in java syntax.
	 */
	protected DataText(String texString, String symjaString, String rawString) {
		if (texString == null && symjaString == null)
			new NullException(ExceptionMessage.OBJECT_IS_NULL, new NullPointerException(), texString, symjaString);
		this.texString = texString;
		this.symjaString = symjaString;
		this.rawString = rawString;
	}

	/*
	 * Date: 9 Aug 2021-----------------------------------------------------------
	 * Time created: 09:55:46---------------------------------------------------
	 */
	/**
	 * Package private constructor for quick initialisation of a {@code DataText}
	 * which only specifies a TeX and symja string.
	 * 
	 * @param teX   a {@code String} in TeX notation.
	 * @param symja the first argument {@code String} in symja or java syntax.
	 */
	DataText(String teX, String symja) {
		this(teX, symja, symja);
	}

	/*
	 * Date: 9 Apr 2021-----------------------------------------------------------
	 * Time created: 17:07:59--------------------------------------------
	 */
	/**
	 * Used by the scientific calculator and it's {@code DetailsList} to convert a
	 * symja expression to TeX form.
	 * 
	 * <p>
	 * Symja's evaluation engine seems to use an external evaluation engine because
	 * really long numbers (such as 400!) do not return their raw values in
	 * numerical form but in a TeX notation whereby the number is broken down into
	 * several lines with the new-line character '\' (for TeX) appended at the end
	 * of each line. This so regardless of whether the user tries to get th raw
	 * non-TeX) expression. Hence this problem needs to be dealt with as a special
	 * case.
	 * </p>
	 * 
	 * @param symja  the first argument {@code String} in symja or java syntax.
	 * @param engine the symja engine which does the actual conversion.
	 * @return a {@code DataText} object whose {@link #getRawString()} and
	 *         {@link #getSymjaString()} is one and the same.
	 */
	public static DataText fromSymja(String symja, EvalEngine engine) {
		TeXUtilities tex = new TeXUtilities(engine, false);
		StringWriter out = new StringWriter();
		tex.toTeX(symja, out);
		StringBuilder val = new StringBuilder();
		return new DataText(out.toString(), isLongTeXNumber(symja, val) ? val.toString() : symja);
	}

	/*
	 * Date: 8 May 2022-----------------------------------------------------------
	 * Time created: 17:57:49--------------------------------------------
	 */
	/**
	 * Searches the entire string of the first argument whether it is a multi-line
	 * TeX number such as (300!):
	 * 
	 * <pre>
	 * 3060575122164406360353704612972686293885888041735769994167767412594765331767168\
	 * 6746551529142247757334993914788870172636886426390775900315422684292790697455984\
	 * 1225476930271954604008012215776252176854255965356903506788725264321896264299365\
	 * 2045764488303889097539434896254360532259807765212708224376394491201286786753683\
	 * 0571229368194364995646049816645022771650018517654646934011222603472972406633325\
	 * 8583506870150169794168850353752137554910289126407157154830282284937952636580145\
	 * 2352331569364822334367992545940952768206080622328123873838808170496000000000000\
	 * 00000000000000000000000000000000000000000000000000000000000000
	 * </pre>
	 * 
	 * If it is: formats it to a java.math.BigDecimal string representation, stores the
	 * format in the given StringBuilder using <code>append()</code> and returns {@code true}.
	 * If it's not: return {@code false} without any operation performed.
	 * @param tex the value in TeX notation to be formatted
	 * @param results the result of the formatted value
	 * @return {@code true} if this is a multi-line numerical value in TeX notation
	 * or {@code false} if otherwise.
	 */
	private static boolean isLongTeXNumber(String tex, StringBuilder results) {
		if (!tex.contains("\n"))
			return false;
		String intern = tex.replaceAll("\\\\(\\p{Cntrl}+)", "");
		try {
			new java.math.BigDecimal(intern);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;// this is not a number probably a really long expression such as a TeX graph
		}
		results.append(intern);
		return true;
	}

	/*
	 * Date: 9 Aug 2021-----------------------------------------------------------
	 * Time created: 10:01:15--------------------------------------------
	 */
	/**
	 * Used by the scientific calculator and it's {@code DetailsList} to convert a
	 * symja expression to TeX form. This method appropriately converts a generic
	 * expression to a symja, TeX and then java interpretation.
	 * 
	 * @param plainString a non-TeX notational string. This may also be in java or
	 *                    symja notation, or even a generic notation supported by
	 *                    the symja evaluation engine.
	 * @param engine      the symja engine which does the actual conversion.
	 * @return a {@code DataText} object whose {@link #getRawString()} will return
	 *         the {@code String} argument and {@link #getSymjaString()} may be the
	 *         argument in java and/or symja notation.
	 */
	public static DataText fromPlainString(String plainString, EvalEngine engine) {
		TeXUtilities t = new TeXUtilities(engine, true);
		StringWriter out = new StringWriter();
		IExpr ex = engine.parse(plainString);
		t.toTeX(ex, out);
		return new DataText(out.toString(), engine.evaluate("JavaForm[" + plainString + "]").toString(), plainString);
	}

	/*
	 * Date: 9 Aug 2021-----------------------------------------------------------
	 * Time created: 10:08:24--------------------------------------------
	 */
	/**
	 * Gets this {@code DataText} in TeX notational string.
	 * 
	 * @return the TeX interpretation of this {@code DataText}.
	 */
	public String getTeXString() {
		return texString;
	}

	/*
	 * 
	 * Date: 9 Aug 2021-----------------------------------------------------------
	 * Time created: 10:09:14--------------------------------------------
	 */
	/**
	 * Returns the java (or symja as the case may be) notation for this
	 * {@code DataText}.
	 * 
	 * @return the java/symja interpretation of {@code this}.
	 */
	public String getSymjaString() {
		return symjaString;
	}

	/*
	 * Date: 9 Aug 2021-----------------------------------------------------------
	 * Time created: 10:11:03--------------------------------------------
	 */
	/**
	 * Gets the raw un formatted string. The string returned by this method may be
	 * the same as {@link #getSymjaString()} depending on how this object was
	 * created.
	 * 
	 * @return the original text.
	 */

	public String getRawString() {
		return rawString;
	}

	/*
	 * Most Recent Date: 9 Aug 2021-----------------------------------------------
	 * Most recent time created: 10:12:17--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param o {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof DataText))
			return false;
		DataText oo = (DataText) o;
		return texString.equals(oo.texString) && rawString.equals(oo.rawString) && symjaString.equals(oo.symjaString);
	}

	/*
	 * Most Recent Date: 9 Aug 2021-----------------------------------------------
	 * Most recent time created: 10:12:37--------------------------------------
	 */
	/**
	 * Returns the TeX interpretation and the symja notation as a string separated
	 * by a comma.
	 * 
	 * @return <code>{@link #getTeXString()} + ", " + {@link #getSymjaString()}</code>
	 */
	@Override
	public String toString() {
		return texString + ", " + symjaString;
	}

	/**
	 * Stores the TeX string value.
	 */
	private String texString;
	/**
	 * Field for the symja/java string.
	 */
	private String symjaString;
	/**
	 * The original unformatted string.
	 */
	private String rawString;
	/**
	 * Object for comparing non-locale specifc information within locale-sensitive
	 * data.
	 */
	private static Collator collator = Collator.getInstance(Locale.US);

}
