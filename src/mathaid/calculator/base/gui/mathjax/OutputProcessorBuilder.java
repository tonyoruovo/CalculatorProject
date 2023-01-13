/**
 * 
 */
package mathaid.calculator.base.gui.mathjax;

import mathaid.js.JSBoolean;
import mathaid.js.JSDictionary;
import mathaid.js.JSMemberName;
import mathaid.js.JSNumber;
import mathaid.js.JSObject;
import mathaid.js.JSString;

/*
 * Date: 23 Aug 2022----------------------------------------------------------- 
 * Time created: 23:47:28---------------------------------------------------  
 * Package: js.mathjax.options------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: OutputProcessorBuilder.java------------------------------------------------------ 
 * Class name: OutputProcessorBuilder------------------------------------------------ 
 */
/**
 * Base class that contains shared configurations between all known output
 * processors. An output processor is an engine that processes and transforms
 * math from text input code such as (TeX, MathML and AsciiMath) to pretty
 * visuals usually to text output (<b>C</b>ommon <b>HTML</b>) or a graphics
 * format output (such as <b>S</b>calable <b>V</b>ector <b>G</b>raphics).
 * <p>
 * <b>Copied from the MathJax documentation:</b>
 * <p>
 * <h2>Options Common to All Output Processors</h2> The following options are
 * common to all the output processors listed above. They are given here with
 * their default values, using the chtml block as an example.
 * 
 * <pre>
 * <code>
MathJax = {
  chtml: {
    scale: 1,                      // global scaling factor for all expressions
    minScale: .5,                  // smallest scaling factor to use
    mtextInheritFont: false,       // true to make mtext elements use surrounding font
    merrorInheritFont: false,      // true to make merror text use surrounding font
    mtextFont: '',                 // font to use for mtext, if not inheriting (empty means use MathJax fonts)
    merrorFont: 'serif',           // font to use for merror, if not inheriting (empty means use MathJax fonts)
    unknownFamily: 'serif',        // font to use for character that aren't in MathJax's fonts
    mathmlSpacing: false,          // true for MathML spacing rules, false for TeX rules
    skipAttributes: {},            // RFDa and other attributes NOT to copy to the output
    exFactor: .5,                  // default size of ex in em units
    displayAlign: 'center',        // default for indentalign when set to 'auto'
    displayIndent: '0'             // default for indentshift when set to 'auto'
  }
};</code>
 * </pre>
 * 
 * Note that the above is an example of a configuration for chtml (specified by
 * the {@code CHTMLOutputProcessorBuilder} class). Another output configuration
 * is svg (specified by the {@code SVGOutputProcessorBuilder} class).
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class OutputProcessorBuilder extends AbstractOptionsBuilder {
	/**
	 * This is a string that represents the field name for the resident output
	 * processor object, whose value is used for the global scaling factor for all
	 * expressions.
	 */
	public static final String SCALE = "scale";
	/**
	 * This is a string that represents the field name for the resident output
	 * processor object, whose value is used for the smallest scaling factor to use.
	 */
	public static final String MINSCALE = "minScale";
	/**
	 * This is a string that represents the field name for the resident output
	 * processor object, whose value is used for making mtext elements use
	 * surrounding font.
	 */
	public static final String MTEXTINHERITFONT = "mtextinheritFont";
	/**
	 * This is a string that represents the field name for the resident output
	 * processor object, whose value is used for making merror text use surrounding
	 * font.
	 */
	public static final String MERRORINHERITFONT = "merrorInheritFont";
	/**
	 * This is a string that represents the field name for the resident output
	 * processor object, whose value is the font to use for mtext, if not inheriting
	 * (empty means use MathJax fonts).
	 */
	public static final String MTEXTFONT = "mtextFont";
	/**
	 * This is a string that represents the field name for the resident output
	 * processor object, whose value is the font to use for merror, if not
	 * inheriting (empty means use MathJax fonts).
	 */
	public static final String MERRORFONT = "merrorFont";
	/**
	 * This is a string that represents the field name for the resident output
	 * processor object, whose value is the font to use for character that aren't in
	 * MathJax's fonts.
	 */
	public static final String UNKNOWNFAMILY = "unknownFamily";
	/**
	 * This is a string that represents the field name for the resident output
	 * processor object, whose value is true for MathML spacing rules, false for TeX
	 * rules.
	 */
	public static final String MATHMLSPACING = "mathmlSpacing";
	/**
	 * This is a string that represents the field name for the resident output
	 * processor object, whose value is the RFDa and other attributes NOT to copy to
	 * the output.
	 */
	public static final String SKIPATTRIBUTES = "skipAttributes";
	/**
	 * This is a string that represents the field name for the resident output
	 * processor object, whose value is the default size of ex in em units.
	 */
	public static final String EXFACTOR = "exFactor";
	/**
	 * This is a string that represents the field name for the resident output
	 * processor object, whose value is the default size of ex in em units.
	 */
	public static final String DISPLAYALIGN = "displayAlign";
	/**
	 * This is a string that represents the field name for the resident output
	 * processor object, whose value is the default for <code>indentalign</code>
	 * when set to 'auto'.
	 */
	public static final String DISPLAYINDENT = "displayIndent";
	/**
	 * The field name for the developer option {@link #setWrapperFactory(JSObject)}
	 */
	public static final String WRAPPERFACTORY = "wrapperFactory";
	/**
	 * The field name for the developer option {@link #setFont(JSObject)}
	 */
	public static final String FONT = "font";
	/**
	 * The field name for the developer option {@link #setCssStyles(JSObject)}
	 */
	public static final String CSSSTYLES = "cssStyles";

	/*
	 * Date: 24 Aug 2022-----------------------------------------------------------
	 * Time created: 21:43:52--------------------------------------------
	 */
	/**
	 * Sets the {@value #SCALE} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * The scaling factor for math compared to the surrounding text. The CommonHTML
	 * output processor tries to match the ex-size of the mathematics with that of
	 * the text where it is placed, but you may want to adjust the results using
	 * this scaling factor. The user can also adjust this value using the contextual
	 * menu item associated with the typeset mathematics. <br>
	 * <br>
	 * <i>The default is:</i> <code>scale: 1</code>
	 * 
	 * @param scale a floating point or integer which represents how large the math
	 *              on the web page will be.
	 * @return {@code this} for further method chaining
	 */
	public OutputProcessorBuilder setScale(JSNumber scale) {
		fields.put(new JSMemberName(SCALE, false), scale);
		return this;
	}

	/*
	 * Date: 24 Aug 2022-----------------------------------------------------------
	 * Time created: 21:51:47--------------------------------------------
	 */
	/**
	 * Sets the {@value #MINSCALE} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This gives a minimum scale factor for the scaling used by MathJax to match
	 * the equation to the surrounding text. This will prevent MathJax from making
	 * the mathematics too small. <br>
	 * <br>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>minScale: .5</code>
	 * </pre>
	 * 
	 * @param scale a floating point or integer which represents how small the math
	 *              on the web page is allowed to be.
	 * @return {@code this} for further method chaining
	 */
	public OutputProcessorBuilder setMinScale(JSNumber scale) {
		fields.put(new JSMemberName(MINSCALE, false), scale);
		return this;
	}

	/*
	 * Date: 24 Aug 2022-----------------------------------------------------------
	 * Time created: 21:54:41--------------------------------------------
	 */
	/**
	 * Sets the {@value #MTEXTINHERITFONT} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This setting controls whether &lt;mtext&gt; elements will be typeset using
	 * the math fonts or the font of the surrounding text. When false, the mtextFont
	 * will be used, unless it is blank, in which case math fonts will be used, as
	 * they are for other token elements; when true, the font will be inherited from
	 * the surrounding text, when possible, depending on the math variant for the
	 * element (some math variants, such as fraktur can’t be inherited from the
	 * surroundings). <br>
	 * <br>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>mtextInheritFont: false</code>
	 * </pre>
	 * 
	 * @param scale true for \mtext{} values to inherit the font of surrounding html
	 *              text. false otherwise.
	 * @return {@code this} for further method chaining
	 */
	public OutputProcessorBuilder setMtextInheritFont(JSBoolean b) {
		fields.put(new JSMemberName(MTEXTINHERITFONT, false), b);
		return this;
	}

	/*
	 * Date: 24 Aug 2022-----------------------------------------------------------
	 * Time created: 21:58:12--------------------------------------------
	 */
	/**
	 * Sets the {@value #MERRORINHERITFONT} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This setting controls whether the text for &lt;merror&gt; elements will be
	 * typeset using the math fonts or the font of the surrounding text. When false,
	 * the merrorFont will be used; when true, the font will be inherited from the
	 * surrounding text, when possible, depending on the math variant for the
	 * element (some math variants, such as fraktur can’t be inherited from the
	 * surroundings). <br>
	 * <br>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>merrorInheritFont: false</code>
	 * </pre>
	 * 
	 * @param scale true for input error messages to inherit the font of surrounding
	 *              html text. false otherwise.
	 * @return {@code this} for further method chaining
	 */
	public OutputProcessorBuilder setMerrorInheritFont(JSBoolean b) {
		fields.put(new JSMemberName(MERRORINHERITFONT, false), b);
		return this;
	}

	/*
	 * Date: 24 Aug 2022-----------------------------------------------------------
	 * Time created: 22:02:09--------------------------------------------
	 */
	/**
	 * Sets the {@value #MTEXTFONT} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This specifies the font family to use for &lt;mtext&gt; elements when
	 * mtextInheritFont is false (and is ignored if it is true). It can be a
	 * comma-separated list of font-family names. If it is empty, then the math
	 * fonts are used, as they are with other token elements.. <br>
	 * <br>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>mtextFont: ''</code>
	 * </pre>
	 * 
	 * @param fontList a comma-separated list of font family names
	 * @return {@code this} for further method chaining
	 */
	public OutputProcessorBuilder setMtextFont(JSString fontList) {
		fields.put(new JSMemberName(MTEXTFONT, false), fontList);
		return this;
	}

	/*
	 * Date: 24 Aug 2022-----------------------------------------------------------
	 * Time created: 22:02:09--------------------------------------------
	 */
	/**
	 * Sets the {@value #MERRORFONT} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This specifies the font family to use for &lt;merror&gt; elements when
	 * merrorInheritFont is false (and is ignored if it is true). It can be a
	 * comma-separated list of font-family names. If it is empty, then the math
	 * fonts are used, as they are with other token elements. <br>
	 * <br>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>mtextFont: ''</code>
	 * </pre>
	 * 
	 * @param fontList a comma-separated list of font family names
	 * @return {@code this} for further method chaining
	 */
	public OutputProcessorBuilder setMerrorFont(JSString fontList) {
		fields.put(new JSMemberName(MERRORFONT, false), fontList);
		return this;
	}

	/*
	 * Date: 24 Aug 2022-----------------------------------------------------------
	 * Time created: 22:02:09--------------------------------------------
	 */
	/**
	 * Sets the {@value #UNKNOWNFAMILY} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This specifies the font family to use for characters that are not found in
	 * the MathJax math fonts. For exmaple, if you enter unicode characters
	 * directly, these may not be in MathJax’s font, and so they will be taken from
	 * the font specified here. <br>
	 * <br>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>unknownFamily: 'serif'</code>
	 * </pre>
	 * 
	 * @param fontFamilyName a font family name
	 * @return {@code this} for further method chaining
	 */
	public OutputProcessorBuilder setUnknownFamily(JSString fontFamilyName) {
		fields.put(new JSMemberName(UNKNOWNFAMILY, false), fontFamilyName);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 00:54:12--------------------------------------------
	 */
	/**
	 * Sets the {@value #MATHMLSPACING} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This specifies whether to use TeX spacing or MathML spacing when typesetting
	 * the math. When true, MathML spacing rules are used; when false, the TeX rules
	 * are used. <br>
	 * <br>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>mathmlSpacing: false</code>
	 * </pre>
	 * 
	 * @param b true to set and false otherwise
	 * @return {@code this} for further method chaining
	 */
	public OutputProcessorBuilder setMathMlSpacing(JSBoolean b) {
		fields.put(new JSMemberName(MATHMLSPACING, false), b);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 00:56:30--------------------------------------------
	 */
	/**
	 * Sets the {@value #SKIPATTRIBUTES} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This object gives a list of non-standard attributes (e.g., RFDa attributes)
	 * that will not be transferred from MathML element to their corresponding DOM
	 * elements in the typeset output. For example, with
	 * 
	 * <pre>
	 * <code>
	 * skipAttributes: { data-my-attr: true } </code>
	 * </pre>
	 * 
	 * a MathML element like &lt;mi data-my-attr="some data"&gt;x&lt;/mi&gt; will
	 * not have the data-my-attr attribute on the &lt;mjx-mi&gt; element created by
	 * the CommonHTML output processor to represent the &lt;mi&gt; element
	 * (normally, any non-standard attributes are retained in the output). <br>
	 * <br>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>skipAttributes: {}</code>
	 * </pre>
	 * 
	 * @param attr a collection of key-value pairs where the key (a string) is the
	 *             name of the html attribute and the value is a boolean specifying
	 *             whether a tag with the given attribute should be skipped by the
	 *             MathJax engine when searching for math to typeset.
	 * @return {@code this} for further method chaining
	 */
	public OutputProcessorBuilder setSkipAttributes(JSDictionary<JSString, JSBoolean> attr) {
		fields.put(new JSMemberName(SKIPATTRIBUTES, false), MathJaxOptionsBuilder.convertDictionaryToObject(attr));
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 01:06:50--------------------------------------------
	 */
	/**
	 * Sets the {@value #EXFACTOR} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This is the size of an ex in comparison to 1 em that is to be used when the
	 * ex-size can’t be determined (e.g., when running in a Node application, where
	 * the size of DOM elements can’t be determined). <br>
	 * <br>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>exFactor: .5</code>
	 * </pre>
	 * 
	 * @param factor a number specifying the ex factor
	 * @return {@code this} for further method chaining
	 */
	public OutputProcessorBuilder setExFactor(JSNumber factor) {
		fields.put(new JSMemberName(EXFACTOR, false), factor);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 01:10:40--------------------------------------------
	 */
	/**
	 * Sets the {@value #DISPLAYALIGN} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This determines how displayed equations will be aligned (left, center, or
	 * right). The default is 'center'. <br>
	 * <br>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>displayAlign: 'center'</code>
	 * </pre>
	 * 
	 * @param align either one of the following string: 'left', 'right' or 'center'
	 * @return {@code this} for further method chaining
	 */
	public OutputProcessorBuilder setDisplayAlign(JSString align) {
		fields.put(new JSMemberName(DISPLAYALIGN, false), align);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 01:14:16--------------------------------------------
	 */
	/**
	 * Sets the {@value #DISPLAYINDENT} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This gives the amount of indentation that should be used for displayed
	 * equations. The default is 0. A value of '1em', for example, would introduce
	 * an extra 1 em of space from whichever margin the equation is aligned to, or
	 * an offset from the center position if the expression is centered. Note that
	 * negative values are allowed. <br>
	 * <br>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>displayIndent: 0</code>
	 * </pre>
	 * 
	 * @param indent
	 * @return {@code this} for further method chaining
	 */
	public OutputProcessorBuilder setDisplayIndent(JSString indent) {
		fields.put(new JSMemberName(DISPLAYINDENT, false), indent);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 01:14:16--------------------------------------------
	 */
	/**
	 * Sets the {@value #WRAPPERFACTORY} field. This is a
	 * {@link AbstractOptionsBuilder developer option}.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * The WrapperFactory object instance to use for creating wrappers for the
	 * internal MathML objects. This allows you to create a subclass of
	 * WrapperFactory and pass that to the output jax. A null value means use the
	 * default WrapperFactory class and make a new instance of that. <br>
	 * <br>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>wrapperFactory: null</code>
	 * </pre>
	 * 
	 * @param factory an object which is a subclass of the {@code WrapperFactory}
	 *                class within the MathJax API.
	 * @return {@code this} for further method chaining
	 */
	public OutputProcessorBuilder setWrapperFactory(JSObject factory) {
		fields.put(new JSMemberName(WRAPPERFACTORY, false), factory);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 01:34:31--------------------------------------------
	 */
	/**
	 * Sets the {@value #FONT} field. This is a {@link AbstractOptionsBuilder
	 * developer option}.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * The FontData object instance to use for creating wrappers for the internal
	 * MathML objects. This allows you to create a subclass of FontData and pass
	 * that to the output jax. A null value means use the default FontData class and
	 * make a new instance of that. <br>
	 * <br>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>font: null</code>
	 * </pre>
	 * 
	 * @param fontObject an object which is a subclass of the {@code FontData} class
	 *                   within the MathJax API.
	 * @return {@code this} for further method chaining
	 */
	public OutputProcessorBuilder setFont(JSObject fontObject) {
		fields.put(new JSMemberName(FONT, false), fontObject);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 03:45:23--------------------------------------------
	 */
	/**
	 * Sets the {@value #CSSSTYLES} field. This is a {@link AbstractOptionsBuilder
	 * developer option}.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * The CssStyles object instance to use for creating wrappers for the internal
	 * MathML objects. This allows you to create a subclass of CssStyles and pass
	 * that to the output jax. A null value means use the default CssStyles class
	 * and make a new instance of that. <br>
	 * <br>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>cssStyles: null</code>
	 * </pre>
	 * 
	 * @param cssObject an object which is a subclass of the {@code CssObject} class
	 *                  within the MathJax API.
	 * @return {@code this} for further method chaining
	 */
	public OutputProcessorBuilder setCssStyles(JSObject cssObject) {
		fields.put(new JSMemberName(CSSSTYLES, false), cssObject);
		return this;
	}

}
