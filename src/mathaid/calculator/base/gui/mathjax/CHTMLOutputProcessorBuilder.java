/**
 * 
 */
package mathaid.calculator.base.gui.mathjax;

import mathaid.js.JSBoolean;
import mathaid.js.JSMemberName;
import mathaid.js.JSString;

/*
 * Date: 24 Aug 2022----------------------------------------------------------- 
 * Time created: 00:54:58---------------------------------------------------  
 * Package: js.mathjax.options------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: CHTMLOutputProcessorBuilder.java------------------------------------------------------ 
 * Class name: CHTMLOutputProcessorBuilder------------------------------------------------ 
 */
/**
 * A builder that builds pre-configured objects to be used in the configuration
 * of CHTML output of MathJax.
 * <p>
 * <b>Copied from the MathJax documentation:</b>
 * <h3>AsciiMath Input Processor Options</h3>CommonHTML Output Processor Options
 * The options below control the operation of the CommonHTML output processor
 * that is run when you include 'output/chtml' in the load array of the loader
 * block of your MathJax configuration, or if you load a combined component that
 * includes the CommonHTML output jax. They are listed with their default
 * values. To set any of these options, include a chtml section in your MathJax
 * global object.
 * 
 * <p>
 * The Configuration Block:
 * 
 * <pre>
 * <code>
MathJax = {
  chtml: {
    scale: 1,                      // global scaling factor for all expressions
    minScale: .5,                  // smallest scaling factor to use
    mtextInheritFont: false,       // true to make mtext elements use surrounding font
    merrorInheritFont: true,       // true to make merror text use surrounding font
    mathmlSpacing: false,          // true for MathML spacing rules, false for TeX rules
    skipAttributes: {},            // RFDa and other attributes NOT to copy to the output
    exFactor: .5,                  // default size of ex in em units
    displayAlign: 'center',        // default for indentalign when set to 'auto'
    displayIndent: '0',            // default for indentshift when set to 'auto'
    matchFontHeight: true,         // true to match ex-height of surrounding font
    fontURL: '[mathjax]/components/output/chtml/fonts/woff-v2',   // The URL where the fonts are found
    adaptiveCSS: true              // true means only produce CSS that is used in the processed equations
  }
};</code>
 * </pre>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class CHTMLOutputProcessorBuilder extends OutputProcessorBuilder {

	/**
	 * The field name for {@link #setMatchFontHeight(JSBoolean)}
	 */
	public static final String MATCHFONTHEIGHT = "matchFontHeight";

	/**
	 * The field name for {@link #setFontURL(JSString)}
	 */
	public static final String FONTURL = "fontURL";
	/**
	 * The field name for {@link #setAdaptiveCSS(JSBoolean)}
	 */
	public static final String ADAPTIVECSS = "adaptiveCSS";

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 22:29:53--------------------------------------------
	 */
	/**
	 * Sets the {@value #MATCHFONTHEIGHT} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This setting controls whether MathJax will scale the mathematics so that the
	 * ex-height of the math fonts matches the ex-height of the surrounding fonts.
	 * This makes the math match the surroundings better, but if the surrounding
	 * font does not have its ex-height set properly (and not all fonts do), it can
	 * cause the math to not match the surrounding text. While this will make the
	 * lower-case letters match the surrounding fonts, the upper case letters may
	 * not match (that would require the font height and ex-height to have the same
	 * ratio in the surrounding text as in the math fonts, which is unlikely).
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>matchFontHeight: true</code>
	 * </pre>
	 * 
	 * @param b true to match font height false otherwise
	 * @return {@code this} for further method chaining
	 */
	public CHTMLOutputProcessorBuilder setMatchFontHeight(JSBoolean b) {
		fields.put(new JSMemberName(MATCHFONTHEIGHT, false), b);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 22:29:58--------------------------------------------
	 */
	/**
	 * Sets the {@value #FONTURL} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This is the URL to the location where the MathJax fonts are stored. In the
	 * default, [mathjax] is replaced by the location from which you have loaded
	 * MathJax. You should include a complete URL to the location of the fonts you
	 * want to use.
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>fontURL: '[mathjax]/components/output/chtml/fonts/woff-v2'</code>
	 * </pre>
	 * 
	 * @param url the URL where a given font is located. This is the font to be
	 *            used.
	 * @return {@code this} for further method chaining
	 */
	public CHTMLOutputProcessorBuilder setFontURL(JSString url) {
		fields.put(new JSMemberName(FONTURL, false), url);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 22:30:03--------------------------------------------
	 */
	/**
	 * Sets the {@value #ADAPTIVECSS} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This setting controls how the CommonHTML output jax handles the CSS styles
	 * that it generates. When true, this means that only the CSS needed for the
	 * math that has been processed on the page so far is generated. When false, the
	 * CSS needed for all elements and all characters in the MathJax font are
	 * generated. This is an extremely large amount of CSS, and that can have an
	 * effect on the performance of your page, so it is best to leave this as true.
	 * You can reset the information about what CSS is needed by using the command
	 * 
	 * <pre>
	 * <code>MathJax.startup.document.output.clearCache();</code>
	 * </pre>
	 * 
	 * to clear the font cache.
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>adaptiveCSS: true</code>
	 * </pre>
	 * 
	 * @param b true to use adaptive css of MathJax
	 * @return {@code this} for further method chaining
	 */
	public CHTMLOutputProcessorBuilder setAdaptiveCSS(JSBoolean b) {
		fields.put(new JSMemberName(ADAPTIVECSS, false), b);
		return this;
	}

}
