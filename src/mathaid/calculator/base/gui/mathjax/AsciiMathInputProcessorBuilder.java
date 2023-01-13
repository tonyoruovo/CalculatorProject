/**
 * 
 */
package mathaid.calculator.base.gui.mathjax;

import mathaid.js.JSBoolean;
import mathaid.js.JSMemberName;
import mathaid.js.JSObject;
import mathaid.js.JSString;

/*
 * Date: 22 Aug 2022----------------------------------------------------------- 
 * Time created: 07:19:54---------------------------------------------------  
 * Package: js.mathjax.options------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: AsciiMathInputProcessorBuilder.java------------------------------------------------------ 
 * Class name: AsciiMathInputProcessorBuilder------------------------------------------------ 
 */
/**
 * A builder that builds pre-configured objects to be used in the configuration
 * of ASCII input of MathJax
 * <p>
 * <b>Copied from the MathJax documentation:</b>
 * <h3>AsciiMath Input Processor Options</h3>
 * <p>
 * The options below control the operation of the AsciiMath input processor that
 * is run when you include 'input/asciimath' in the in the load array of the
 * loader block of your MathJax configuration, or if you load a combined
 * component that includes the AsciiMath input jax (none currently do, since the
 * AsciiMath input has not been fully ported to version 3). They are listed with
 * their default values. To set any of these options, include an asciimath
 * section in your MathJax global object.
 * 
 * <pre>
 * <code>
 * MathJax = {
  asciimath: {
    fixphi: true,              // true for TeX mapping, false for unicode mapping
    displaystyle: true,        // true for displaystyle typesetting, false for in-line
    decimalsign: '.'           // character to use for decimal separator
  }
};
 * </code>
 * </pre>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class AsciiMathInputProcessorBuilder extends InputProcessorBuilder {
	/**
	 * The field name of {@link #setFixphi(JSBoolean)}
	 */
	public static final String FIXPHI = "fixphi";
	/**
	 * The field of {@link #setDisplayStyle(JSBoolean)}
	 */
	public static final String DISPLAYSTYLE = "displayStyle";
	/**
	 * The field name for {@link #setDecimalSign(JSString)}
	 */
	public static final String DECIMALSIGN = "decimalsign";
	/**
	 * The field name for {@link #setFind(JSObject)}
	 */
	public static final String FINDASCIIMATH = "FindAsciiMath";

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 09:28:34--------------------------------------------
	 */
	/**
	 * Sets the {@value #FIXPHI} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * Determines whether MathJax will switch the Unicode values for phi and varphi.
	 * If set to true MathJax will use the TeX mapping, otherwise the Unicode
	 * mapping.
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>fixphi: true</code>
	 * </pre>
	 * 
	 * @param b fix the glyph?
	 * @return {@code this} for further method chaining
	 */
	public AsciiMathInputProcessorBuilder setFixphi(JSBoolean b) {
		fields.put(new JSMemberName(FIXPHI, false), b);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 09:28:34--------------------------------------------
	 */
	/**
	 * Sets the {@value #DISPLAYSTYLE} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * Determines whether operators like summation symbols will have their limits
	 * above and below the operators (true) or to their right (false). The former is
	 * how they would appear in displayed equations that are shown on their own
	 * lines, while the latter is better suited to in-line equations so that they
	 * don’t interfere with the line spacing so much.
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>displaystyle: true</code>
	 * </pre>
	 * 
	 * @param b use displaystyle?
	 * @return {@code this} for further method chaining
	 */
	public AsciiMathInputProcessorBuilder setDisplayStyle(JSBoolean b) {
		fields.put(new JSMemberName(DISPLAYSTYLE, false), b);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 09:28:34--------------------------------------------
	 */
	/**
	 * Sets the {@value #DECIMALSIGN} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This is the character to be used for decimal points in numbers. If you change
	 * this to ',', then you need to be careful about entering points or intervals.
	 * E.g., use (1, 2) rather than (1,2) in that case.
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>decimalsign: "."</code>
	 * </pre>
	 * 
	 * @param s the character to be used as decimal sign
	 * @return {@code this} for further method chaining
	 */
	public AsciiMathInputProcessorBuilder setDecimalSign(JSString s) {
		fields.put(new JSMemberName(DECIMALSIGN, false), s);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 09:28:34--------------------------------------------
	 */
	/**
	 * Sets the {@value #FINDASCIIMATH} field. This is a developer option.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * The FindAsciiMath object instance that will override the default one. This
	 * allows you to create a subclass of FindAsciiMath and pass that to the
	 * AsciiMath input jax. A null value means use the default FindAsciiMath class
	 * and make a new instance of that.
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>FindAsciiMath: null</code>
	 * </pre>
	 * 
	 * @param o a subclass of FindAsciiMath
	 * @return {@code this} for further method chaining
	 */
	@Override
	public AsciiMathInputProcessorBuilder setFind(JSObject o) {
		fields.put(new JSMemberName(FINDASCIIMATH, false), o);
		return this;
	}
}
