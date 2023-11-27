/**
 * 
 */
package mathaid.calculator.base.gui.mathjax;

import mathaid.js.JSBoolean;
import mathaid.js.JSMemberName;
import mathaid.js.JSNumber;
import mathaid.js.JSString;

/*
 * Date: 24 Aug 2022----------------------------------------------------------- 
 * Time created: 01:50:22---------------------------------------------------  
 * Package: js.mathjax.options------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: SVGOutputProcessorBuilder.java------------------------------------------------------ 
 * Class name: SVGOutputProcessorBuilder------------------------------------------------ 
 */
/**
 * A builder that builds pre-configured objects to be used in the configuration
 * of CHTML output of MathJax.
 * <p>
 * <b>Copied from the MathJax documentation:</b>
 * <h3>SVG Output Processor Options</h3>
 * <p>
 * The options below control the operation of the SVG output processor that is
 * run when you include 'output/svg' in the load array of the loader block of
 * your MathJax configuration, or if you load a combined component that includes
 * the CommonHTML output jax. They are listed with their default values. To set
 * any of these options, include an svg section in your MathJax global object.
 * <h4>The Configuration block</h4>
 * 
 * <pre>
 * <code>
 * MathJax = {
  svg: {
    scale: 1,                      // global scaling factor for all expressions
    minScale: .5,                  // smallest scaling factor to use
    mtextInheritFont: false,       // true to make mtext elements use surrounding font
    merrorInheritFont: true,       // true to make merror text use surrounding font
    mathmlSpacing: false,          // true for MathML spacing rules, false for TeX rules
    skipAttributes: {},            // RFDa and other attributes NOT to copy to the output
    exFactor: .5,                  // default size of ex in em units
    displayAlign: 'center',        // default for indentalign when set to 'auto'
    displayIndent: '0',            // default for indentshift when set to 'auto'
    fontCache: 'local',            // or 'global' or 'none'
    localID: null,                 // ID to use for local font cache (for single equation processing)
    internalSpeechTitles: true,    // insert &lt;title&gt; tags with speech content
    titleID: 0                     // initial id number to use for aria-labeledby titles
  }
};</code>
 * </pre>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class SVGOutputProcessorBuilder extends OutputProcessorBuilder {

	/**
	 * The Field name for {@link #setFontCache(JSString)}
	 */
	public static final String FONTCACHE = "fontCache";
	/**
	 * The field name for {@link #setInternalSpeechTitles(JSBoolean)}
	 */
	public static final String INTERNALSPEECHTITLES = "internalSpeechTitles";
	/**
	 * The field name for {@link #setLocalID(JSString)}
	 */
	public static final String LOCALID = "localID";
	/**
	 * The field name for {@link #setTitleID(JSNumber)}
	 */
	public static final String TITLEID = "titleID";

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 22:40:07--------------------------------------------
	 */
	/**
	 * Sets the {@value #FONTCACHE} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This setting determines how the SVG output jax manages characters that appear
	 * multiple times in an equation or on a page. The SVG processor uses SVG paths
	 * to display the characters in your math expressions, and when a character is
	 * used more than once, it is possible to reuse the same path description; this
	 * can save space in the SVG image, as the paths can be quite complex. When set
	 * to 'local', MathJax will cache font paths on an express-by-expression (each
	 * expression has its own cache within the SVG image itself), which makes the
	 * SVG self-contained, but still allows for some savings if characters are
	 * repeated. When set to 'global', a single cache is used for all paths on the
	 * page; this gives the most savings, but makes the images dependent on other
	 * elements of the page. When set to 'none', no caching is done and explicit
	 * paths are used for every character in the expression.
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>fontCache: 'local'</code>
	 * </pre>
	 * 
	 * @param s specifies how MathJax should look for fonts
	 * @return {@code this} for further method chaining
	 */
	public SVGOutputProcessorBuilder setFontCache(JSString s) {
		fields.put(new JSMemberName(FONTCACHE, false), s);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 22:40:10--------------------------------------------
	 */
	/**
	 * Sets the {@value #INTERNALSPEECHTITLES} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This tells the SVG output jax whether to put speech text into <title>
	 * elements within the SVG (when set to 'true'), or to use an aria-label
	 * attribute instead. Neither of these control whether speech strings are
	 * generated (that is handled by the Semantic-Enrich Extension Options
	 * settings); this setting only tells what to do with a speech string when it
	 * has been generated or included as an attribute on the root MathML element.
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>internalSpeechTitles: true</code>
	 * </pre>
	 * 
	 * @param b true for speeches
	 * @return {@code this} for further method chaining
	 */
	public SVGOutputProcessorBuilder setInternalSpeechTitles(JSBoolean b) {
		fields.put(new JSMemberName(INTERNALSPEECHTITLES, false), b);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 22:40:13--------------------------------------------
	 */
	/**
	 * Sets the {@value #LOCALID} field. This is a developer option.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This gives the ID prefix to use for the paths stored in a local font cache
	 * when fontCache is set to 'local'. This is useful if you need to process
	 * multiple equations by hand and want to generate unique ids for each equation,
	 * even if MathJax is restarted between equations. If set to null, no prefix is
	 * used.
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>localID: null</code>
	 * </pre>
	 * 
	 * @param s see {@link #setFontCache(JSString)}
	 * @return {@code this} for further method chaining
	 */
	public SVGOutputProcessorBuilder setLocalID(JSString s) {
		fields.put(new JSMemberName(LOCALID, false), s);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 22:40:16--------------------------------------------
	 */
	/**
	 * Sets the {@value #TITLEID} field. This is a developer option.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This gives the initial number used to make unique &lt;title&gt; ids when
	 * internalSpeechTitles is true. This is useful if you need to process multiple
	 * equations by hand and want to generate unique ids for each equation, even if
	 * MathJax is restarted between equations.
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>titleID: 0</code> </p
	 * 
	 * @param id the id to be used
	 * @return {@code this} for further method chaining
	 */
	public SVGOutputProcessorBuilder setTitleID(JSNumber id) {
		fields.put(new JSMemberName(TITLEID, false), id);
		return this;
	}

}
