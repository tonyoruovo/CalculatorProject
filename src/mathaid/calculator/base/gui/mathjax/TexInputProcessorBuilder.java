/**
 * Sets the {@value #ENVIRONMENT} field.
 * <p>
 * <b>Copied from the MathJax documentation</b>
 * <p><br>
 * <br>
 * <i>The default is:</i>
 * 
 * <pre>
 * <code></code>
 * </pre>
 * 
 * @param size a length using CSS3 syntax (such em, ex, dp, % etc)
 * @return {@code this} for further method chaining
 */
package mathaid.calculator.base.gui.mathjax;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import mathaid.js.JSArray;
import mathaid.js.JSBoolean;
import mathaid.js.JSDictionary;
import mathaid.js.JSFunction;
import mathaid.js.JSMemberName;
import mathaid.js.JSNumber;
import mathaid.js.JSObject;
import mathaid.js.JSRegex;
import mathaid.js.JSString;
import mathaid.js.JSValue;

/*
 * Date: 21 Aug 2022----------------------------------------------------------- 
 * Time created: 07:51:17---------------------------------------------------  
 * Package: js.mathjax.options------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: TexInputProcessor.java------------------------------------------------------ 
 * Class name: TexInputProcessor------------------------------------------------ 
 */
/**
 * A Builder that builds configuration objects for TeX input for the MathJax
 * object.
 * <p>
 * <b>Copied from the MathJax documentation:</b>
 * <p>
 * The options below control the operation of the TeX input processor that is
 * run when you include 'input/tex', 'input/tex-full', or 'input/tex-base' in
 * the load array of the loader block of your MathJax configuration, or if you
 * load a combined component that includes the TeX input jax. They are listed
 * with their default values. To set any of these options, include a tex section
 * in your MathJax global object.<br>
 * <br>
 * An example of the code produced by from the final built object is:
 * 
 * <pre>
 * <code>
 * tex: {
    packages: ['base'],        // extensions to use
    inlineMath: [              // start/end delimiter pairs for in-line math
      ['\\(', '\\)']
    ],
    displayMath: [             // start/end delimiter pairs for display math
      ['$$', '$$'],
      ['\\[', '\\]']
    ],
    processEscapes: true,      // use \$ to produce a literal dollar sign
    processEnvironments: true, // process \begin{xxx}...\end{xxx} outside math mode
    processRefs: true,         // process \ref{...} outside of math mode
    digits: /^(?:[0-9]+(?:\{,\}[0-9]{3})*(?:\.[0-9]*)?|\.[0-9]+)/,// pattern for recognizing numbers
    tags: 'none',              // or 'ams' or 'all'
    tagSide: 'right',          // side for \tag macros
    tagIndent: '0.8em',        // amount to indent tags
    useLabelIds: true,         // use label name rather than tag for ids
    maxMacros: 1000,           // maximum number of macro substitutions per expression
    maxBuffer: 5 * 1024,       // maximum size for the internal TeX string (5K)
    baseURL:                   // URL for use with links to tags (when there is a <base> tag in effect)
       (document.getElementsByTagName('base').length === 0) ?
        '' : String(document.location).replace(/#.*$/, '')),
    formatError:               // function called when TeX syntax errors occur
        (jax, err) => jax.formatError(err)
  }
 * </code>
 * </pre>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class TexInputProcessorBuilder extends InputProcessorBuilder {

	/**
	 * Field name for the list of packages
	 */
	public static final String PACKAGES = "packages";
	/**
	 * Field name for open and close delimiters for inline math
	 */
	public static final String INLINEMATH = "inlineMath";
	/**
	 * Field name for open and close delimiters for display style math
	 */
	public static final String DISPLAYMATH = "displayMath";
	/**
	 * Field name for how to deal with escape
	 */
	public static final String PROCESSESCAPE = "processEscapes";
	/**
	 * Field name for processing <code>\refs{...}</code> outside of math mode
	 */
	public static final String PROCESSREFS = "precessRefs";
	/**
	 * Field name for processing
	 * <code>\begin{environment}...\end{environment}</code> outside of math mode
	 */
	public static final String PROCESSENVIRONMENTS = "processEnvironments";
	/**
	 * Field name for identifying digits so as to preserve MathJax digit formatting
	 */
	public static final String DIGITS = "digits";
	/**
	 * Field name for how equation tags are numbered
	 */
	public static final String TAGS = "tags";
	/**
	 * Field name for the side equation tags be visible
	 */
	public static final String TAGSIDE = "tagSide";
	/**
	 * Field name for visible equation tag indentation
	 */
	public static final String TAGINDENT = "tagIndent";
	/**
	 * Field name
	 * 
	 * @see TexInputProcessorBuilder#setUseLabelIds(JSBoolean)
	 */
	public static final String USELABELIDS = "useLabelIds";
	/**
	 * Field name for the number of macros definition allowed per instance of
	 * MathJax
	 */
	public static final String MAXMACROS = "maxMacros";
	/**
	 * Field name for the number of nodes within a macros definition allowed per
	 * instance of MathJax
	 */
	public static final String MAXBUFFER = "maxBuffer";
	/**
	 * Field name for the url to use when creating links to tagged equations
	 */
	public static final String BASEURL = "baseUrl";
	/**
	 * Field name for the function called for errors
	 */
	public static final String FORMATERROR = "formatError";
	/**
	 * Field name for the {@code FindTeX} instance
	 */
	public static final String FINDTEX = "FindTeX";

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 04:55:04---------------------------------------------------
	 * Package: js.mathjax.tex------------------------------------------------
	 * Project: TestCode------------------------------------------------ File:
	 * TexInputProcessorBuilder.java------------------------------------------------
	 * ------ Class name:
	 * TexExtensionPackages------------------------------------------------
	 */
	/**
	 * An interface that houses the field names for the tex input processor's
	 * package declaration command 'load'. Please note that 'autoload', 'setoptions'
	 * are not included.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 */
	public static interface TexExtensionPackages {
		/**
		 * Field name for the action extension which gives you access to the MathML
		 * &lt;maction&gt;
		 * <p>
		 * This extension is loaded automatically when the autoload extension is used.
		 * To load the action extension explicitly, add '[tex]/action' to the load array
		 * of the loader block of your MathJax configuration, and add 'action' to the
		 * packages array of the tex block.
		 * 
		 * <pre>
		 * <code>
		window.MathJax = {
		  loader: {load: ['[tex]/action']},
		  tex: {packages: {'[+]': ['action']}}
		};</code>
		 * </pre>
		 * 
		 * Alternatively, use \require{action} in a TeX expression to load it
		 * dynamically from within the math on the page, if the require package is
		 * loaded.
		 */
		String ACTION = "action";
		/**
		 * The ams extension implements AMS math environments and macros, and macros for
		 * accessing the characters in the AMS symbol fonts. This extension is already
		 * loaded in all the components that include the TeX input jax, other than
		 * input/tex-base.
		 */
		String AMS = "ams";
		/**
		 * The amscd extensions implements the CD environment for commutative diagrams.
		 */
		String AMSCD = "amscd";
		/**
		 * A value which when used with
		 * {@link TexInputProcessorBuilder#setPackages(JSArray) the package builder} or
		 * {@link TexInputProcessorBuilder#addRemovePackage(JSArray, JSArray)}
		 * represents all the base packages loaded with the MathJax tex engine. Please
		 * see the MathJax documentation for more details.
		 */
		String BASE = "base";
		/**
		 * The bbox extension defines a new macro for adding background colors, borders,
		 * and padding to your math expressions.
		 */
		String BBOX = "bbox";// This is used by the caret to simulate overwrite input mode
		/**
		 * The bbox extension defines a new macro for adding background colors, borders,
		 * and padding to your math expressions.
		 */
		String BOLDSYMBOL = "boldsymbol";
		/**
		 * The braket extension defines the following macros for producing the bra-ket
		 * notation and set notation used in quantum mechanics
		 */
		String BRAKET = "braket";
		/**
		 * The bussproofs extension implements the bussproofs style package from LaTeX.
		 */
		String BUSSPROOFS = "bussproofs";
		/**
		 * The cancel extension defines the following macros:
		 */
		String CANCEL = "cancel";
		/**
		 * The cases extension implements the cases style package from LaTeX. It
		 * provides environments numcases and subnumcases for formulas with separately
		 * enumerated cases.
		 */
		String CASES = "cases";
		/**
		 * The centernot extension implements the centernot style package from LaTeX. It
		 * provides the \centernot command which can be used as a replacement of the
		 * standard \not command and generally leads to a better alignment of the slash
		 * with the operator it negates.
		 */
		String CENTERNOT = "centernot";
		/**
		 * The color extension defines the \color macro as in the LaTeX color package,
		 * along with \colorbox, \fcolorbox, and \definecolor. It declares the standard
		 * set of colors (Apricot, Aquamarine, Bittersweet, and so on), and provides the
		 * RGB, rgb, and grey-scale color spaces in addition to named colors.
		 */
		String COLOR = "color";
		/**
		 * The colortbl extension partially implements the colortbl style package from
		 * LaTeX. It allows coloring of rows, columns and individual cell of tables. See
		 * the CTAN page for more information and documentation of colortbl. But note
		 * that MathJax currently does not implement any commands for styling or
		 * coloring table boundaries.
		 */
		String COLORTBL = "colortbl";
		/**
		 * The colorv2 extension defines the \color macro to be the non-standard macro
		 * that is the default in MathJax version 2, namely, it takes two arguments, one
		 * the name of the color (or an HTML color of the form #RGB or #RRGGBB), and the
		 * second the math to be colored.
		 */
		String COLORV2 = "colorv2";
		/**
		 * The configmacros extension provides the macros and environments configuration
		 * options for the tex block of your MathJax configuration. This allows you to
		 * predefine custom macros end environments for your page using javascript.
		 */
		String CONFIGMACROS = "configmacros";
		/**
		 * he empheq extension partially implements the empheq style package from LaTeX.
		 * The package provides macros and environments for emphasising equations. See
		 * the list of control sequences for details about what commands are implemented
		 * in this extension. Note, that the current implementation of the empheq
		 * environment supports only the left and right options.
		 */
		String EMPHEQ = "empheq";
		/**
		 * The enclose extension gives you access to the MathML &lt;menclose&gt; element
		 * for adding boxes, ovals, strikethroughs, and other marks over your
		 * mathematics. It defines the following non-standard macro:
		 */
		String ENCLOSE = "enclose";
		/**
		 * he extpfeil extension adds more macros for producing extensible arrows,
		 * including \xtwoheadrightarrow, \xtwoheadleftarrow, \xmapsto, \xlongequal,
		 * \xtofrom, and a non-standard \Newextarrow for creating your own extensible
		 * arrows. The latter has the form
		 */
		String EXTPFEIL = "extpfeil";
		/**
		 * The gensymb extension implements the gensymb style package from LaTeX. It
		 * provides a number of macros for unit notation.
		 */
		String GENSYMB = "gensymb";
		/**
		 * The html extension gives you access to some HTML features like styles,
		 * classes, element ID’s, and clickable links.
		 */
		String HTML = "html";
		/**
		 * The mathtools extension implements the mathtools style package from LaTeX.
		 * The package provides a number of tools for advanced mathematical typesetting.
		 */
		String MATHTOOLS = "mathtools";
		/**
		 * The mhchem extensions implements the \ce and \pu chemical equation macros of
		 * the LaTeX mhchem package.
		 */
		String MCHEM = "mchem";
		/**
		 * The newcommand extension provides the \def, \newcommand, \renewcommand, \let,
		 * \newenvironment, and \renewenvironment macros for creating new macros and
		 * environments in TeX.
		 */
		String NEWCOMMAND = "newcommand";
		/**
		 * The noerrors extension prevents TeX error messages from being displayed and
		 * shows the original TeX code instead.
		 */
		String NOERRORS = "noerrors";
		/**
		 * The noundefined extension causes undefined control sequences to be shown as
		 * their macro names rather than generating error messages. So $X_{\xyz}$ would
		 * display as an “X” with a subscript consisting of the text \xyz in red.
		 */
		String NOUNDEFINED = "noundefined";
		/**
		 * The physics extension implements much of the LaTeX physics package, which
		 * defines simple, yet flexible macros for typesetting equations via:
		 * <ul>
		 * <li>Automatic bracing</li>
		 * <li>Vector notation</li>
		 * <li>Derivatives</li>
		 * <li>Dirac bra-ket notation</li>
		 * <li>Matrix macros</li>
		 * <li>Additional trig functions and other convenient operators</li>
		 * <li>Flat fractions and other useful miscellaneous math macros</li>
		 * </ul>
		 */
		String PHYSICS = "physics";
		/**
		 * The require extension defines the non-standard \require macro that allows you
		 * to load extensions from within a math expression in a web page.
		 */
		String REQUIRE = "require";
		/**
		 * The tagformat extension provides the ability to customize the format of the
		 * equation tags and automatic equation numbers. You do this by providing
		 * functions in the tagformat object of the tex block of your MathJax
		 * configuration.
		 */
		String TAGFORMAT = "tagformat";
		/**
		 * The textcomp extension implements the old textcomp style package from LaTeX.
		 * The macros of the package are now part of LaTeX’s base package. The textcomp
		 * extension provides a number of text macros that can be used in math mode as
		 * well.
		 */
		String TEXTCOMP = "textcomp";
		/**
		 * The textmacros extension adds the ability to process some text-mode macros
		 * within \text{} and other macros that produce text-mode material.
		 */
		String TEXTMACROS = "textmacros";
		/**
		 * The unicode extension implements a (non-standard) \\unicode{} macro that
		 * allows arbitrary unicode code points to be entered in your mathematics. You
		 * can specify the height and depth of the character (the width is determined by
		 * the browser), and the default font from which to take the character.
		 * 
		 * Examples:
		 * 
		 * <pre>
		 * <code>
		\\unicode{65}                        % the character 'A'
		\\unicode{x41}                       % the character 'A'
		\\unicode[.55,0.05]{x22D6}           % less-than with dot, with height .55em and depth 0.05em
		\\unicode[.55,0.05][Geramond]{x22D6} % same taken from Geramond font
		\\unicode[Garamond]{x22D6}           % same, but with default height, depth of .8em,.2em
		</pre></code> Once a size and font are provided for a given unicode point, they
		 * need not be specified again in subsequent \\unicode{} calls for that
		 * character.
		 * 
		 * The result of \\unicode{...} will have TeX class ORD (i.e., it will act like
		 * a variable). Use \mathbin{...}, \mathrel{...}, etc., to specify a different
		 * class.
		 * 
		 * Note that a font list can be given in the \\unicode{} macro. If not is
		 * provided, MathJax will use its own fonts, if possible, and then the default
		 * font list for unknown characters if not.
		 */
		String UNICODE = "unicode";
		/**
		 * The upgreek extension implements the upgreek style package from LaTeX. It
		 * provides upright Greek characters for both lower and upper cases.
		 */
		String UPGREEK = "upgreek";
		/**
		 * The verb extension defines the \verb LaTeX macro that typesets its argument
		 * “verbatim” (without further processing) in a monospaced (typewriter) font.
		 * The first character after the \verb command is used as a delimiter for the
		 * argument, which is everything up to the next copy of the delimiter
		 * character). E.g.
		 * 
		 * <pre>
		 * <code>
		\verb|\sqrt{x}|</code>
		 * </pre>
		 * 
		 * will typeset <code>\sqrt{x}</code> as a literal string.
		 * 
		 * Note that, due to how MathJax locates math strings within the document, the
		 * argument to \verb must have balanced braces, so <code>\verb|{|</code> is not
		 * valid in a web page (use \mathtt{\{} instead). If you are passing TeX strings
		 * to MathJax.tex2svg() or MathJax.tex2chtml(), however, braces don’t have to be
		 * balanced. So
		 * 
		 * <pre>
		 * <code>
		const html = MathJax.tex2chtml('\\verb|{|');</code>
		 * </pre>
		 * 
		 * is valid.
		 */
		String VERB = "verb";
	}

	/*
	 * Date: 22 Aug 2022-----------------------------------------------------------
	 * Time created: 04:50:50---------------------------------------------------
	 * Package: js.mathjax.options------------------------------------------------
	 * Project: TestCode------------------------------------------------ File:
	 * TexInputProcessorBuilder.java------------------------------------------------
	 * ------ Class name:
	 * TexExtensionOptionsBuilder------------------------------------------------
	 */
	/**
	 * These are configurations objects for extensions that require a non-atomic
	 * (object) value during configuration. All the options uses string constants
	 * from {@link TexExtensionPackages}.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <h2>TeX and LaTeX extensions</h2>
	 * <p>
	 * While MathJax includes nearly all of the Plain TeX math macros, and many of
	 * the LaTeX macros and environments, not everything is implemented in the core
	 * TeX input processor. Some less-used commands are defined in extensions to the
	 * TeX processor. MathJax will load some extensions automatically when you first
	 * use the commands they implement (for example, the \color macro is implemented
	 * in the color extension, but MathJax loads this extension itself when you use
	 * that macro). While most extensions are set up to load automatically, there
	 * are a few that you would need to load explicitly yourself. See the autoload
	 * extension below for how to configure which extensions to autoload.
	 * <p>
	 * Please note that 'auto load' is not included in this API.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 */
	public static class TexExtensionOptionsBuilder extends AbstractOptionsBuilder {
		/*
		 * Date: 25 Aug 2022-----------------------------------------------------------
		 * Time created: 05:33:34---------------------------------------------------
		 * Package: js.mathjax.tex------------------------------------------------
		 * Project: TestCode------------------------------------------------ File:
		 * TexInputProcessorBuilder.java------------------------------------------------
		 * ------ Class name:
		 * AMSOptionsBuilder------------------------------------------------
		 */
		/**
		 * Options object builder for the field {@link TexExtensionPackages#AMS} which
		 * stores the configuration object for 'ams' option.
		 * <p>
		 * <b>Copied from the MathJax documentation:</b>
		 * <p>
		 * Adding the ams extension to the packages array defines an ams sub-block of
		 * the tex configuration block with the following values:
		 * 
		 * <pre>
		 * <code>
		MathJax = {
		  tex: {
		    ams: {
		      multlineWidth: '100%',
		      multlineIndent: '1em'
		    }
		  }
		};</code>
		 * </pre>
		 * 
		 * @author Oruovo Anthony Etineakpopha
		 * @email tonyoruovo@gmail.com
		 */
		public static class AMSOptionsBuilder extends AbstractOptionsBuilder {
			/**
			 * Field name for {@link #setMultiLineWidth(JSString)}
			 */
			public static final String MULTILINEWIDTH = "multilineWidth";
			/**
			 * Field name for {@link #setMultiLineIndent(JSString)}
			 */
			public static final String MULTILINEINDENT = "multilineIndent";

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 05:38:31--------------------------------------------
			 */
			/**
			 * Sets the {@value #MULTILINEWIDTH} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * The width to use for multline environments. <br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>multlineWidth: '100%'</code>
			 * </pre>
			 * 
			 * @param width a length using CSS3 syntax (such em, ex, dp, % etc)
			 * @return {@code this} for further method chaining
			 */
			public AMSOptionsBuilder setMultiLineWidth(JSString width) {
				// Note that width is the CSS# syntax for length and distance
				fields.put(new JSMemberName(MULTILINEWIDTH, false), width);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 05:45:20--------------------------------------------
			 */
			/**
			 * Sets the {@value #MULTILINEINDENT} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * The margin to use on both sides of multline environments.<br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>multlineIndent: '1em'</code>
			 * </pre>
			 * 
			 * @param width a length using CSS3 syntax (such em, ex, dp, % etc)
			 * @return {@code this} for further method chaining
			 */
			public AMSOptionsBuilder setMultiLineIndent(JSString width) {
				// Note that width is the CSS# syntax for length and distance
				fields.put(new JSMemberName(MULTILINEINDENT, false), width);
				return this;
			}
		}

		/*
		 * Date: 25 Aug 2022-----------------------------------------------------------
		 * Time created: 05:46:33---------------------------------------------------
		 * Package: js.mathjax.tex------------------------------------------------
		 * Project: TestCode------------------------------------------------ File:
		 * TexInputProcessorBuilder.java------------------------------------------------
		 * ------ Class name:
		 * AMSCDOptionsBuilder------------------------------------------------
		 */
		/**
		 * Options object builder for the field {@link TexExtensionPackages#AMSCD} which
		 * stores the configuration object for 'amscd' option.
		 * <p>
		 * <b>Copied from the MathJax documentation:</b>
		 * <p>
		 * Adding the amscd extension to the packages array defines an amscd sub-block
		 * of the tex configuration block with the following values:
		 * 
		 * 
		 * <pre>
		 * <code>
		MathJax = {
			tex: {
				amscd: {
					colspace: '5pt',
					rowspace: '5pt',
					harrowsize: '2.75em',
					varrowsize: '1.75em',
					hideHorizontalLabels: false
				}
			}
		};</code>
		 * </pre>
		 * 
		 * @author Oruovo Anthony Etineakpopha
		 * @email tonyoruovo@gmail.com
		 */
		public static class AMSCDOptionsBuilder extends AbstractOptionsBuilder {
			/**
			 * Field name for {@link #setColumnSpace(JSString)}
			 */
			public static final String COLSPACE = "colspace";
			/**
			 * Field name for {@link #setRowSpace(JSString)}
			 */
			public static final String ROWSPACE = "rowspace";
			/**
			 * Field name for {@link #setHArrowsSize(JSString)}
			 */
			public static final String HARROWSIZE = "harrowsize";
			/**
			 * Field name for {@link #setVArrowSize(JSString)}
			 */
			public static final String VARROWSIZE = "varrowsize";
			/**
			 * Field name for {@link #setHideHorizontalLabels(JSBoolean)}
			 */
			public static final String HIDEHORIZONTALABELS = "hideHorizontalLabels";

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 05:52:27--------------------------------------------
			 */
			/**
			 * Sets the {@value #COLSPACE} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * This gives the amount of space to use between columns in the commutative
			 * diagram.<br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>colspace: '5pt'</code>
			 * </pre>
			 * 
			 * @param size a length using CSS3 syntax (such em, ex, dp, % etc)
			 * @return {@code this} for further method chaining
			 */
			public AMSCDOptionsBuilder setColumnSpace(JSString size) {
				// Note that size is the CSS# syntax for length and distance
				fields.put(new JSMemberName(COLSPACE, false), size);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 05:56:05--------------------------------------------
			 */
			/**
			 * Sets the {@value #ROWSPACE} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * This gives the amount of space to use between rows in the commutative
			 * diagram. <br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>harrowsize: '2.75em'</code>
			 * </pre>
			 * 
			 * @param size a length using CSS3 syntax (such em, ex, dp, % etc)
			 * @return {@code this} for further method chaining
			 */
			public AMSCDOptionsBuilder setRowSpace(JSString size) {
				// Note that size is the CSS# syntax for length and distance
				fields.put(new JSMemberName(ROWSPACE, false), size);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 05:58:14--------------------------------------------
			 */
			/**
			 * Sets the {@value #HARROWSIZE} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * This gives the minimum size for horizontal arrows in the commutative diagram.
			 * <br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>harrowsize: '2.75em'</code>
			 * </pre>
			 * 
			 * @param size a length using CSS3 syntax (such em, ex, dp, % etc)
			 * @return {@code this} for further method chaining
			 */
			public AMSCDOptionsBuilder setHArrowsSize(JSString size) {
				// Note that size is the CSS# syntax for length and distance
				fields.put(new JSMemberName(HARROWSIZE, false), size);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 06:00:37--------------------------------------------
			 */
			/**
			 * Sets the {@value #VARROWSIZE} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * This gives the minimum size for vertical arrows in the commutative diagram.
			 * <br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>varrowsize: '1.75em'</code>
			 * </pre>
			 * 
			 * @param size a length using CSS3 syntax (such em, ex, dp, % etc)
			 * @return {@code this} for further method chaining
			 */
			public AMSCDOptionsBuilder setVArrowSize(JSString size) {
				// Note that size is the CSS# syntax for length and distance
				fields.put(new JSMemberName(VARROWSIZE, false), size);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 06:04:17--------------------------------------------
			 */
			/**
			 * Sets the {@value #HIDEHORIZONTALABELS} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * This determines whether horizontal arrows with labels above or below will use
			 * \smash in order to hide the height of the labels. (Labels above or below
			 * horizontal arrows can cause excess space between rows, so setting this to
			 * true can improve the look of the diagram.)<br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>hideHorizontalLabels: false</code>
			 * </pre>
			 * 
			 * @param hide whether or not to hide
			 * @return {@code this} for further method chaining
			 */
			public AMSCDOptionsBuilder setHideHorizontalLabels(JSBoolean hide) {
				fields.put(new JSMemberName(HIDEHORIZONTALABELS, false), hide);
				return this;
			}
		}

		/*
		 * Date: 25 Aug 2022-----------------------------------------------------------
		 * Time created: 06:15:51---------------------------------------------------
		 * Package: js.mathjax.tex------------------------------------------------
		 * Project: TestCode------------------------------------------------ File:
		 * TexInputProcessorBuilder.java------------------------------------------------
		 * ------ Class name:
		 * ColorOptionsBuilder------------------------------------------------
		 */
		/**
		 * Options object builder for the field {@link TexExtensionPackages#COLOR} which
		 * stores the configuration object for 'color' option.
		 * <p>
		 * <b>Copied from the MathJax documentation:</b>
		 * <p>
		 * The color extension defines the \color macro as in the LaTeX color package,
		 * along with \colorbox, \fcolorbox, and \definecolor. It declares the standard
		 * set of colors (Apricot, Aquamarine, Bittersweet, and so on), and provides the
		 * RGB, rgb, and grey-scale color spaces in addition to named colors.
		 * 
		 * This extension is loaded automatically when the autoload extension is used.
		 * To load the color extension explicitly, add '[tex]/color' to the load array
		 * of the loader block of your MathJax configuration, and add 'color' to the
		 * packages array of the tex block.
		 * 
		 * <pre>
		 * <code>
		window.MathJax = {
		  loader: {load: ['[tex]/color']},
		  tex: {packages: {'[+]': ['color']}}
		};</code>
		 * </pre>
		 * <p>
		 * Alternatively, use \require{color} in a TeX expression to load it dynamically
		 * from within the math on the page, if the require extension is loaded.
		 * <p>
		 * Adding the color extension to the packages array defines a color sub-block of
		 * the tex configuration block with the following values:
		 * 
		 * <pre>
		 * <code>
		MathJax = {
		  tex: {
		    color: {
		      padding: '5px',
		      borderWidth: '2px'
		    }
		  }
		};</code>
		 * </pre>
		 * 
		 * @author Oruovo Anthony Etineakpopha
		 * @email tonyoruovo@gmail.com
		 */
		public static class ColorOptionsBuilder extends AbstractOptionsBuilder {
			/**
			 * Field name for {@link #setPadding(JSString)}
			 */
			public static final String PADDING = "padding";
			/**
			 * Field name for {@link #setBorderWidth(JSString)}
			 */
			public static final String BORDERWIDTH = "borderWidth";

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 06:19:19--------------------------------------------
			 */
			/**
			 * Sets the {@value #PADDING} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * This gives the padding to use for color boxes with background colors.<br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>padding: '5px'</code>
			 * </pre>
			 * 
			 * @param size a length using CSS3 syntax (such em, ex, dp, % etc)
			 * @return {@code this} for further method chaining
			 */
			public ColorOptionsBuilder setPadding(JSString size) {
				// Note that size is the CSS# syntax for length and distance
				fields.put(new JSMemberName(PADDING, false), size);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 06:24:20--------------------------------------------
			 */
			/**
			 * Sets the {@value #BORDERWIDTH} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * This gives the border width to use with framed color boxes produced by
			 * {@code \fcolorbox}.<br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>borderWidth: '2px'</code>
			 * </pre>
			 * 
			 * @param size a length using CSS3 syntax (such em, ex, dp, % etc)
			 * @return {@code this} for further method chaining
			 */
			public ColorOptionsBuilder setBorderWidth(JSString size) {
				// Note that size is the CSS# syntax for length and distance
				fields.put(new JSMemberName(BORDERWIDTH, false), size);
				return this;
			}
		}

		/*
		 * Date: 25 Aug 2022-----------------------------------------------------------
		 * Time created: 06:26:59---------------------------------------------------
		 * Package: js.mathjax.tex------------------------------------------------
		 * Project: TestCode------------------------------------------------ File:
		 * TexInputProcessorBuilder.java------------------------------------------------
		 * ------ Class name:
		 * ConfigMacrosOptionsBuilder------------------------------------------------
		 */
		/**
		 * <p>
		 * <b>Copied from the MathJax documentation</b>
		 * <p>
		 * The configmacros extension adds a macros option to the tex block that lets
		 * you pre-define macros, and the environments option that lets you pre-define
		 * your own environments.
		 * 
		 * @author Oruovo Anthony Etineakpopha
		 * @email tonyoruovo@gmail.com
		 */
		public static class ConfigMacrosOptionsBuilder extends AbstractOptionsBuilder {
			/**
			 * Field name for {@link #setMacros(JSObject)}
			 */
			public static final String MACROS = "macros";
			/**
			 * Field name for {@link #setEnviroment(JSObject)}
			 */
			public static final String ENVIRONMENT = "environment";

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 06:31:29--------------------------------------------
			 */
			/**
			 * Sets the {@value #MACROS} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * This lists macros to define before the TeX input processor begins. These are
			 * name: value pairs where the name gives the name of the TeX macro to be
			 * defined, and value gives the replacement text for the macro. The value can be
			 * a simple replacement string, or an array of the form [value, n], where value
			 * is the replacement text and n is the number of parameters for the macro. The
			 * array can have a third entry: either a string that is the default value to
			 * give for an optional (bracketed) parameter when the macro is used, or an
			 * array consisting of template strings that are used to separate the various
			 * parameters. The first template must precede the first parameter, the second
			 * must precede the second, and so on until the final which must end the last
			 * parameter to the macro. See the examples below.
			 * 
			 * Note that since the value is a javascript string, backslashes in the
			 * replacement text must be doubled to prevent them from acting as javascript
			 * escape characters.
			 * 
			 * For example,
			 * 
			 * <pre>
			 * <code>
			macros: {
			  RR: '{\\bf R}',                    // a simple string replacement
			  bold: ['\\boldsymbol{#1}',1] ,     // this macro has one parameter
			  ddx: ['\\frac{d#2}{d#1}', 2, 'x'], // this macro has an optional parameter that defaults to 'x'
			  abc: ['(#1)', 1, [null, '\\cba']]  // equivalent to \def\abc#1\cba{(#1)}
			}</code>
			 * </pre>
			 * 
			 * would ask the TeX processor to define four new macros: \RR, which produces a
			 * bold-face “R”, and \bold{...}, which takes one parameter and sets it in the
			 * bold-face font, \ddx, which has an optional (bracketed) parameter that
			 * defaults to x, so that \ddx{y} produces \frac{dy}{dx} while \ddx[t]{y}
			 * produces \frac{dy}{dt}, and \abc that is equivalent to
			 * \def\abc#1\cba{(#1)}.<br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>macros: {}</code>
			 * </pre>
			 * 
			 * @param macros a {@code JSObject}.
			 * @return {@code this} for further method chaining
			 */
			public ConfigMacrosOptionsBuilder setMacros(JSObject macros) {
				fields.put(new JSMemberName(MACROS, false), macros);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 06:35:25--------------------------------------------
			 */
			/**
			 * Sets the {@value #ENVIRONMENT} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * This lists environments to define before the TeX input processor begins.
			 * These are name: value pairs where the name gives the name of the environment
			 * to be defined, and value gives an array that defines the material to go
			 * before and after the content of the environment. The array is of the form
			 * [before, after, n, opt] where before is the material that replaces the
			 * \begin{name}, after is the material that replaces \end{name}, n is the number
			 * of parameters that follow the \begin{name}, and opt is the default value used
			 * for an optional parameter that would follow \begin{name} in brackets. The
			 * parameters can be inserted into the before string using #1, #2, etc., where
			 * #1 is the optional parameter, if there is one.
			 * 
			 * Note that since the before and after values are javascript strings,
			 * backslashes in the replacement text must be doubled to prevent them from
			 * acting as javascript escape characters.
			 * 
			 * For example,
			 * 
			 * <pre>
			 * <code>
			environments: {
			  braced: ['\\left\\{', '\\right\\}'],
			  ABC: ['(#1)(#2)(', ')', 2, 'X']
			}</pre></code> would define two environments, braced and ABC, where
			 * 
			 * <code>\begin{braced} \frac{x}{y} \end{braced}</code> would produce the
			 * fraction x/y in braces that stretch to the height of the fraction, while
			 * 
			 * <code>\begin{ABC}{Z} xyz \end{ABC}</code> would produce
			 * <code>(X)(Z)(xyz)</code> and
			 * 
			 * <code>\begin{ABC}[Y]{Z} xyz \end{ABC}</code> would produce
			 * {@code (Y)(Z)(xyz)}<br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>macros: {}</code>
			 * </pre>
			 * 
			 * @param env a {@code JSObject}.
			 * @return {@code this} for further method chaining
			 */
			public ConfigMacrosOptionsBuilder setEnviroment(JSObject env) {
				fields.put(new JSMemberName(ENVIRONMENT, false), env);
				return this;
			}
		}

		/*
		 * Date: 25 Aug 2022-----------------------------------------------------------
		 * Time created: 06:45:07---------------------------------------------------
		 * Package: js.mathjax.tex------------------------------------------------
		 * Project: TestCode------------------------------------------------ File:
		 * TexInputProcessorBuilder.java------------------------------------------------
		 * ------ Class name:
		 * MathToolsOptionsBuilder------------------------------------------------
		 */
		/**
		 * Options object builder for the field {@link TexExtensionPackages#MATHTOOLS}
		 * which stores the configuration object for 'mathtools' option.
		 * <p>
		 * <b>Copied from the MathJax documentation:</b>
		 * <p>
		 * Adding the mathtools extension to the packages array defines an mathtools
		 * sub-block of the tex configuration block with the following values:
		 * 
		 * <pre>
		 * <code>
		MathJax = {
		  tex: {
		    mathtools: {
		      multlinegap: '1em',
		      multlined-pos: 'c',
		      firstline-afterskip: '',
		      lastline-preskip: '',
		      smallmatrix-align: 'c',
		      shortvdotsadjustabove: '.2em',
		      shortvdotsadjustbelow: '.2em',
		      centercolon: false,
		      centercolon-offset: '.04em',
		      thincolon-dx: '-.04em',
		      thincolon-dw: '-.08em',
		      use-unicode: false,
		      prescript-sub-format: '',
		      prescript-sup-format: '',
		      prescript-arg-format: '',
		      allow-mathtoolsset: true,
		      pairedDelimiters: {},
		      tagforms: {}
		    }
		  }
		};</code>
		 * </pre>
		 * 
		 * @author Oruovo Anthony Etineakpopha
		 * @email tonyoruovo@gmail.com
		 */
		public static class MathToolsOptionsBuilder extends AbstractOptionsBuilder {
			/**
			 * Field name for {@link #setMultiLineGap(JSString)}
			 */
			public static final String MULTILINEGAP = "multlinegap";
			/**
			 * Field name for {@link #setMultiLinedPos(JSString)}
			 */
			public static final String MULTILINEDPOS = "multilined-pos";
			/**
			 * Field name for {@link #setFirstLineAfterSkip(JSString)}
			 */
			public static final String FIRSTLINEAFTERSKIP = "firstline-afterskip";
			/**
			 * Field name for {@link #setLastLinePreSkip(JSString)}
			 */
			public static final String LASTLINEPRESKIP = "lastline-preskip";
			/**
			 * Field name for {@link #setSmallMatrixAlign(JSString)}
			 */
			public static final String SMALLMATRIXALIGN = "smallmatrix-align";
			/**
			 * Field name for {@link #setShortVdotsAdjustAbove(JSString)}
			 */
			public static final String SHORTVDOTSADJUSTABOVE = "shortvdotsadjustabove";
			/**
			 * Field name for {@link #setShortVdotsAdjustBelow(JSString)}
			 */
			public static final String SHORTVDOTSADJUSTBELOW = "shortvdotsadjustbelow";
			/**
			 * Field name for {@link #setCenterColon(JSBoolean)}
			 */
			public static final String CENTERCOLON = "centercolon";
			/**
			 * Field name for {@link #setCenterColorOffset(JSString)}
			 */
			public static final String CENTERCOLONOFFSET = "centercolon-offset";
			/**
			 * Field name for {@link #setThinColonDx(JSString)}
			 */
			public static final String THINCOLONDX = "thincolor-dx";
			/**
			 * Field name for {@link #setThinColonDw(JSString)}
			 */
			public static final String THINCOLONDW = "thincolon-dw";
			/**
			 * Field name for {@link #setUseUnicode(JSBoolean)}
			 */
			public static final String USEUNICODE = "use-unicode";
			/**
			 * Field name for {@link #setPrescriptSubformat(JSString)}
			 */
			public static final String PRESCRIPTSUBFORMAT = "prescript-sub-format";
			/**
			 * Field name for {@link #setPrescriptSupformat(JSString)}
			 */
			public static final String PRESCRIPTSUPFORMAT = "prescript-sup-format";
			/**
			 * Field name for {@link #setPrescriptArgformat(JSString)}
			 */
			public static final String PRESCRIPTARGFORMAT = "prescript-arg-format";
			/**
			 * Field name for {@link #setAllowMathToolSet(JSBoolean)}
			 */
			public static final String ALLOWMATHTOOLSET = "allow-mathtoolset";
			/**
			 * Field name for {@link #setPairedDelimiters(JSObject)}
			 */
			public static final String PAIREDDELIMITERS = "pairedDelimiters";
			/**
			 * Field name for {@link #setTagForms(JSObject)}
			 */
			public static final String TAGFORMS = "tagforms";

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 06:59:11--------------------------------------------
			 */
			/**
			 * Sets the {@value #MULTILINEGAP} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * Horizontal space for multlined environments. <br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>multlinegap: '1em'</code>
			 * </pre>
			 * 
			 * @param size a length using CSS3 syntax (such em, ex, dp, % etc)
			 * @return {@code this} for further method chaining
			 */
			public MathToolsOptionsBuilder setMultiLineGap(JSString size) {
				// Note that size is the CSS# syntax for length and distance
				fields.put(new JSMemberName(MULTILINEGAP, false), size);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 07:09:46--------------------------------------------
			 */
			/**
			 * Sets the {@value #MULTILINEDPOS} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * Default alignment for multlined environments. <br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>multlined-pos: 'c'</code>
			 * </pre>
			 * 
			 * @param position a single character string such as c - for centre, l - for
			 *                 left and r - for right
			 * @return {@code this} for further method chaining
			 */
			public MathToolsOptionsBuilder setMultiLinedPos(JSString position) {
				fields.put(new JSMemberName(MULTILINEDPOS, true), position);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 07:13:09--------------------------------------------
			 */
			/**
			 * Sets the {@value #FIRSTLINEAFTERSKIP} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * Space for first line of multlined (overrides multlinegap). <br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>firstline-afterskip: ''</code>
			 * </pre>
			 * 
			 * @param s a space string
			 * @return {@code this} for further method chaining
			 */
			public MathToolsOptionsBuilder setFirstLineAfterSkip(JSString s) {
				fields.put(new JSMemberName(FIRSTLINEAFTERSKIP, true), s);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 07:14:35--------------------------------------------
			 */
			/**
			 * Sets the {@value #LASTLINEPRESKIP} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * Space for last line of multlined (overrides multlinegap). <br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>lastline-preskip: ''</code>
			 * </pre>
			 * 
			 * @param s a space string
			 * @return {@code this} for further method chaining
			 */
			public MathToolsOptionsBuilder setLastLinePreSkip(JSString s) {
				fields.put(new JSMemberName(LASTLINEPRESKIP, true), s);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 07:16:42--------------------------------------------
			 */
			/**
			 * Sets the {@value #SMALLMATRIXALIGN} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * Default alignment for smallmatrix environments. <br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>smallmatrix-align: 'c'</code>
			 * </pre>
			 * 
			 * @param s a single character string such as c - for centre, l - for left and r
			 *          - for right
			 * @return {@code this} for further method chaining
			 */
			public MathToolsOptionsBuilder setSmallMatrixAlign(JSString s) {
				fields.put(new JSMemberName(SMALLMATRIXALIGN, true), s);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 07:19:02--------------------------------------------
			 */
			/**
			 * Sets the {@value #SHORTVDOTSADJUSTABOVE} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * Space to remove above \shortvdots. <br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>shortvdotsadjustabove: '.2em'</code>
			 * </pre>
			 * 
			 * @param size a single character string such as c - for centre, l - for left
			 *             and r - for right
			 * @return {@code this} for further method chaining
			 */
			public MathToolsOptionsBuilder setShortVdotsAdjustAbove(JSString size) {
				// Note that size is the CSS# syntax for length and distance
				fields.put(new JSMemberName(SHORTVDOTSADJUSTABOVE, false), size);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 07:20:46--------------------------------------------
			 */
			/**
			 * Sets the {@value #SHORTVDOTSADJUSTBELOW} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * Space to remove below \shortvdots. <br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>shortvdotsadjustbelow: '.2em'</code>
			 * </pre>
			 * 
			 * @param size a single character string such as c - for centre, l - for left
			 *             and r - for right
			 * @return {@code this} for further method chaining
			 */
			public MathToolsOptionsBuilder setShortVdotsAdjustBelow(JSString size) {
				// Note that size is the CSS# syntax for length and distance
				fields.put(new JSMemberName(SHORTVDOTSADJUSTBELOW, false), size);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 07:21:40--------------------------------------------
			 */
			/**
			 * Sets the {@value #CENTERCOLON} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * True to have colon automatically centered. <br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>centercolon: false</code>
			 * </pre>
			 * 
			 * @param b a boolean value
			 * @return {@code this} for further method chaining
			 */
			public MathToolsOptionsBuilder setCenterColon(JSBoolean b) {
				fields.put(new JSMemberName(CENTERCOLON, false), b);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 07:24:15--------------------------------------------
			 */
			/**
			 * Sets the {@value #CENTERCOLONOFFSET} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * Vertical adjustment for centered colons. <br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>centercolon-offset: '.04em'</code>
			 * </pre>
			 * 
			 * @param size a single character string such as c - for centre, l - for left
			 *             and r - for right
			 * @return {@code this} for further method chaining
			 */
			public MathToolsOptionsBuilder setCenterColorOffset(JSString size) {
				// Note that size is the CSS# syntax for length and distance
				fields.put(new JSMemberName(CENTERCOLONOFFSET, true), size);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 07:24:15--------------------------------------------
			 */
			/**
			 * Sets the {@value #THINCOLONDX} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * Horizontal adjustment for thin colons (e.g., \coloneqq). <br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>thincolon-dx: '-.04em'</code>
			 * </pre>
			 * 
			 * @param size a single character string such as c - for centre, l - for left
			 *             and r - for right
			 * @return {@code this} for further method chaining
			 */
			public MathToolsOptionsBuilder setThinColonDx(JSString size) {
				// Note that size is the CSS# syntax for length and distance
				fields.put(new JSMemberName(THINCOLONDX, true), size);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 07:24:15--------------------------------------------
			 */
			/**
			 * Sets the {@value #THINCOLONDW} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * Width adjustment for thin colons. <br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>thincolon-dw: '-.08em'</code>
			 * </pre>
			 * 
			 * @param size a single character string such as c - for centre, l - for left
			 *             and r - for right
			 * @return {@code this} for further method chaining
			 */
			public MathToolsOptionsBuilder setThinColonDw(JSString size) {
				// Note that size is the CSS# syntax for length and distance
				fields.put(new JSMemberName(THINCOLONDW, true), size);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 07:24:15--------------------------------------------
			 */
			/**
			 * Sets the {@value #USEUNICODE} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * True to use unicode characters rather than multi-character version for
			 * \coloneqq, etc., when possible. <br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>use-unicode: false</code>
			 * </pre>
			 * 
			 * @param b a boolean value
			 * @return {@code this} for further method chaining
			 */
			public MathToolsOptionsBuilder setUseUnicode(JSBoolean b) {
				fields.put(new JSMemberName(USEUNICODE, true), b);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 07:34:47--------------------------------------------
			 */
			/**
			 * Sets the {@value #PRESCRIPTSUBFORMAT} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * Format for \prescript subscript. <br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>prescript-sub-format: ''</code>
			 * </pre>
			 * 
			 * @param s the format
			 * @return {@code this} for further method chaining
			 */
			public MathToolsOptionsBuilder setPrescriptSubformat(JSString s) {
				fields.put(new JSMemberName(PRESCRIPTSUBFORMAT, true), s);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 07:34:47--------------------------------------------
			 */
			/**
			 * Sets the {@value #PRESCRIPTSUPFORMAT} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * Format for \prescript superscript. <br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>prescript-sup-format: ''</code>
			 * </pre>
			 * 
			 * @param s the format
			 * @return {@code this} for further method chaining
			 */
			public MathToolsOptionsBuilder setPrescriptSupformat(JSString s) {
				fields.put(new JSMemberName(PRESCRIPTSUPFORMAT, true), s);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 07:34:47--------------------------------------------
			 */
			/**
			 * Sets the {@value #PRESCRIPTARGFORMAT} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * Format for \prescript base <br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>prescript-arg-format: ''</code>
			 * </pre>
			 * 
			 * @param s the format
			 * @return {@code this} for further method chaining
			 */
			public MathToolsOptionsBuilder setPrescriptArgformat(JSString s) {
				fields.put(new JSMemberName(PRESCRIPTARGFORMAT, true), s);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 07:34:47--------------------------------------------
			 */
			/**
			 * Sets the {@value #ALLOWMATHTOOLSET} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * True to allow \mathtoolsset to change settings. <br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>allow-mathtoolsset: true</code>
			 * </pre>
			 * 
			 * @param b a flag for {@value #ALLOWMATHTOOLSET}
			 * @return {@code this} for further method chaining
			 */
			public MathToolsOptionsBuilder setAllowMathToolSet(JSBoolean b) {
				fields.put(new JSMemberName(ALLOWMATHTOOLSET, true), b);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 07:34:47--------------------------------------------
			 */
			/**
			 * Sets the {@value #PAIREDDELIMITERS} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * Predefined paired delimiters of the form name: [left, right, body, argcount,
			 * pre, post].<br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>pairedDelimiters: {}</code>
			 * </pre>
			 * 
			 * @param o an object in the format described above.
			 * @return {@code this} for further method chaining
			 */
			public MathToolsOptionsBuilder setPairedDelimiters(JSObject o) {
				fields.put(new JSMemberName(PAIREDDELIMITERS, false), o);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 07:34:47--------------------------------------------
			 */
			/**
			 * Sets the {@value #TAGFORMS} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * Tag form definitions of the form name: [left, right, format].<br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>tagforms: {}</code>
			 * </pre>
			 * 
			 * @param o an object in the format described above.
			 * @return {@code this} for further method chaining
			 */
			public MathToolsOptionsBuilder setTagForms(JSObject o) {
				fields.put(new JSMemberName(TAGFORMS, false), o);
				return this;
			}
		}

		/*
		 * Date: 25 Aug 2022-----------------------------------------------------------
		 * Time created: 08:17:36---------------------------------------------------
		 * Package: js.mathjax.tex------------------------------------------------
		 * Project: TestCode------------------------------------------------ File:
		 * TexInputProcessorBuilder.java------------------------------------------------
		 * ------ Class name:
		 * NoUndefinedOptionsBuilder------------------------------------------------
		 */
		/**
		 * Options object builder for the field {@link TexExtensionPackages#NOUNDEFINED}
		 * which stores the configuration object for 'noundefined' option.
		 * <p>
		 * <b>Copied from the MathJax documentation:</b>
		 * <p>
		 * The noundefined extension causes undefined control sequences to be shown as
		 * their macro names rather than generating error messages. So $X_{\xyz}$ would
		 * display as an “X” with a subscript consisting of the text \xyz in red.
		 * 
		 * @author Oruovo Anthony Etineakpopha
		 * @email tonyoruovo@gmail.com
		 */
		public static class NoUndefinedOptionsBuilder extends AbstractOptionsBuilder {
			/**
			 * Field name for {@link #setColor(JSString)}
			 */
			public static final String COLOR = "color";
			/**
			 * Field name for {@link #setBackground(JSString)}
			 */
			public static final String BACKGROUND = "background";
			/**
			 * Field name for {@link #setSize(JSString)}
			 */
			public static final String SIZE = "size";

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 08:21:10--------------------------------------------
			 */
			/**
			 * Sets the {@value #COLOR} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * This gives the color to use for the text of the undefined macro name, or an
			 * empty string to make the color the same as the surrounding mathematics. <br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>color: 'red'</code>
			 * </pre>
			 * 
			 * @param colorname a CSS3 predfined colour name
			 * @return {@code this} for further method chaining
			 */
			public NoUndefinedOptionsBuilder setColor(JSString colorname) {
				fields.put(new JSMemberName(COLOR, false), colorname);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 08:21:10--------------------------------------------
			 */
			/**
			 * Sets the {@value #BACKGROUND} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * This gives the color to use for the background for the undefined macro name,
			 * or an empty srting to have no brackground color. <br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>background: ''</code>
			 * </pre>
			 * 
			 * @param bg a CSS3 predfined colour name
			 * @return {@code this} for further method chaining
			 */
			public NoUndefinedOptionsBuilder setBackground(JSString bg) {
				fields.put(new JSMemberName(BACKGROUND, false), bg);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 08:21:10--------------------------------------------
			 */
			/**
			 * Sets the {@value #SIZE} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * This gives the size to use for the undefined macro name (e.g., 90% or 12px),
			 * or an emtpy string to keep the size the same as the surrounding mathematics.
			 * <br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>size: ''</code>
			 * </pre>
			 * 
			 * @param size the size in a CSS3 units
			 * @return {@code this} for further method chaining
			 */
			public NoUndefinedOptionsBuilder setSize(JSString size) {
				fields.put(new JSMemberName(SIZE, false), size);
				return this;
			}
		}

		/*
		 * Date: 25 Aug 2022-----------------------------------------------------------
		 * Time created: 08:24:49---------------------------------------------------
		 * Package: js.mathjax.tex------------------------------------------------
		 * Project: TestCode------------------------------------------------ File:
		 * TexInputProcessorBuilder.java------------------------------------------------
		 * ------ Class name:
		 * PhysicsOptionsBuilder------------------------------------------------
		 */
		/**
		 * Options object builder for the field {@link TexExtensionPackages#PHYSICS}
		 * which stores the configuration object for 'physics' option.
		 * <p>
		 * <b>Copied from the MathJax documentation:</b>
		 * <p>
		 * Adding the physics extension to the packages array defines an physics
		 * sub-block of the tex configuration block with the following values:
		 * 
		 * <pre>
		 * <code>
		MathJax = {
		  tex: {
		    physics: {
		      italicdiff: false,
		      arrowdel: false
		    }
		  }
		};</code>
		 * </pre>
		 * 
		 * @author Oruovo Anthony Etineakpopha
		 * @email tonyoruovo@gmail.com
		 */
		public static class PhysicsOptionsBuilder extends AbstractOptionsBuilder {
			/**
			 * Field name for {@link #setItalicDiff(JSString)}
			 */
			public static final String ITALICDIFF = "italicdiff";
			/**
			 * Field name for {@link #setArrowDel(JSString)}
			 */
			public static final String ARROWDEL = "arrowdel";

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 08:21:10--------------------------------------------
			 */
			/**
			 * Sets the {@value #ITALICDIFF} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * This corresponds to the italicdiff option of the physics LaTeX package to use
			 * italic form for the d in the \differential and \derivative` commands. <br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>italicdiff: false</code>
			 * </pre>
			 * 
			 * @param b the boolean value
			 * @return {@code this} for further method chaining
			 */
			public PhysicsOptionsBuilder setItalicDiff(JSBoolean b) {
				fields.put(new JSMemberName(ITALICDIFF, false), b);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 08:21:10--------------------------------------------
			 */
			/**
			 * Sets the {@value #ARROWDEL} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * This corresponds to the arrowdel option of the physics LaTeX package to use
			 * vector notation over the nabla symbol.<br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>arrowdel: false</code>
			 * </pre>
			 * 
			 * @param b the boolean value
			 * @return {@code this} for further method chaining
			 */
			public PhysicsOptionsBuilder setArrowDel(JSBoolean b) {
				fields.put(new JSMemberName(ARROWDEL, false), b);
				return this;
			}
		}

		/*
		 * Date: 25 Aug 2022-----------------------------------------------------------
		 * Time created: 08:33:43---------------------------------------------------
		 * Package: js.mathjax.tex------------------------------------------------
		 * Project: TestCode------------------------------------------------ File:
		 * TexInputProcessorBuilder.java------------------------------------------------
		 * ------ Class name:
		 * RequireOptionsBuilder------------------------------------------------
		 */
		/**
		 * Options object builder for the field {@link TexExtensionPackages#REQUIRE}
		 * which stores the configuration object for 'require' option.
		 * <p>
		 * <b>Copied from the MathJax documentation:</b>
		 * <p>
		 * Adding the require extension to the packages array defines a require
		 * sub-block of the tex configuration block with the following values:
		 * 
		 * <pre>
		 * <code>
		MathJax = {
		  tex: {
		    require: {
		      allow: {
		        base: false,
		        'all-packages': false
		      },
		      defaultAllow: true
		   }
		 };</code>
		 * </pre>
		 * 
		 * @author Oruovo Anthony Etineakpopha
		 * @email tonyoruovo@gmail.com
		 */
		public static class RequireOptionsBuilder extends AbstractOptionsBuilder {
			/**
			 * Field name for a special object constructed during the call to
			 * {@link #build()}. This object is built using
			 * {@link #setLoadPackage(JSMemberName, JSBoolean)}
			 */
			public static final String ALLOW = "allow";
			/**
			 * Field name for {@link #setDefaultAllow(JSString)}
			 */
			public static final String DEFAULTALLOW = "defaultAllow";
			private TreeMap<JSMemberName, JSValue<?>> ext = new TreeMap<>();

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 08:39:32--------------------------------------------
			 */
			/**
			 * Sets up all the structure of a builder an object when repeatedly called with
			 * different arguments. The builder ultimately builds this object whose field is
			 * {@value #ALLOW}.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * This sub-object indicates which extensions can be loaded by \require. The
			 * keys are the package names, and the value is true to allow the extension to
			 * be loaded, and false to disallow it. If an extension is not in the list, the
			 * default value is given by defaultAllow, described below.<br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>allow: {...}</code>
			 * </pre>
			 * 
			 * @param texPackageName a {@code JSMemberName} whose constructor argument is a
			 *                       tex package name specified in
			 *                       {@code TexExtensionPackages}.
			 * @param loadPackage    a flag for loading or not to load the given package.
			 * @return {@code this} for further method chaining
			 */
			public RequireOptionsBuilder setLoadPackage(JSMemberName texPackageName, JSBoolean loadPackage) {
				ext.put(texPackageName, loadPackage);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 08:21:10--------------------------------------------
			 */
			/**
			 * Sets the {@value #DEFAULTALLOW} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * This is the value used for any extensions that are requested, but are not in
			 * the allow object described above. If set to true, any extension not listed in
			 * allow will be allowed; if false, only the ones listed in allow (with value
			 * true) will be allowed.<br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>defaultAllow: true</code>
			 * </pre>
			 * 
			 * @param b the boolean value
			 * @return {@code this} for further method chaining
			 */
			public RequireOptionsBuilder setDefaultAllow(JSBoolean b) {
				fields.put(new JSMemberName(DEFAULTALLOW, false), b);
				return this;
			}

			/*
			 * Most Recent Date: 25 Aug 2022-----------------------------------------------
			 * Most recent time created: 08:48:38--------------------------------------
			 */
			/**
			 * Builds the object which is a value for the require block of the tex input
			 * configuration.
			 * 
			 * @return a {@code JSObject}.
			 */
			@Override
			public JSObject build() {
				fields.put(new JSMemberName(ALLOW, false), new JSObject(ext));
				return super.build();
			}
		}

		/*
		 * Date: 25 Aug 2022-----------------------------------------------------------
		 * Time created: 08:51:13---------------------------------------------------
		 * Package: js.mathjax.tex------------------------------------------------
		 * Project: TestCode------------------------------------------------ File:
		 * TexInputProcessorBuilder.java------------------------------------------------
		 * ------ Class name:
		 * TagFormatOptionsBuilder------------------------------------------------
		 */
		/**
		 * Options object builder for the field {@link TexExtensionPackages#TAGFORMAT}
		 * which stores the configuration object for 'tagformat' option.
		 * 
		 * @author Oruovo Anthony Etineakpopha
		 * @email tonyoruovo@gmail.com
		 */
		public static class TagFormatOptionsBuilder extends AbstractOptionsBuilder {
			/**
			 * The field name of {@link #setNumberFormat(JSFunction)}
			 */
			public static final String NUMBER = "number";
			/**
			 * The field name of {@link #setHTMLTagFormat(JSFunction)}
			 */
			public static final String TAG = "tag";
			/**
			 * The field name for {@link #setHTMLIdFormat(JSFunction)}
			 */
			public static final String ID = "id";
			/**
			 * The field name for {@link #setURLFormat(JSFunction)}
			 */
			public static final String URL = "url";

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 08:54:47--------------------------------------------
			 */
			/**
			 * Sets the {@value #NUMBER} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * A function that tells MathJax what tag to use for equation number n. This
			 * could be used to have the equations labeled by a sequence of symbols rather
			 * than numbers, or to use section and subsection numbers instead.<br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>number: function (n) {return n.toString()}</code>
			 * </pre>
			 * 
			 * @param f a function for this field
			 * @return {@code this} for further method chaining
			 */
			public TagFormatOptionsBuilder setNumberFormat(JSFunction f) {
				fields.put(new JSMemberName(NUMBER, false), f);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 08:54:47--------------------------------------------
			 */
			/**
			 * Sets the {@value #TAG} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * A function that tells MathJax how to format an equation number for displaying
			 * as a tag for an equation. This is what appears in the margin of a tagged or
			 * numbered equation.<br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>tag: function (n) {return '(' + n + ')'}</code>
			 * </pre>
			 * 
			 * @param f a function for this field
			 * @return {@code this} for further method chaining
			 */
			public TagFormatOptionsBuilder setHTMLTagFormat(JSFunction f) {
				fields.put(new JSMemberName(TAG, false), f);
				return this;
			}

			/*
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 08:58:41--------------------------------------------
			 */
			/**
			 * Sets the {@value #ID} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * A function that tells MathJax what ID to use as an anchor for the equation
			 * (so that it can be used in URL references).<br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>id: function (n) {return 'mjx-eqn:' + n.replace(/\s/g, '_')}</code>
			 * </pre>
			 * 
			 * @param f a function for this field
			 * @return {@code this} for further method chaining
			 */
			public TagFormatOptionsBuilder setHTMLIdFormat(JSFunction f) {
				fields.put(new JSMemberName(ID, false), f);
				return this;
			}

			/*
			 * 
			 * Date: 25 Aug 2022-----------------------------------------------------------
			 * Time created: 09:01:42--------------------------------------------
			 */
			/**
			 * Sets the {@value #URL} field.
			 * <p>
			 * <b>Copied from the MathJax documentation</b>
			 * <p>
			 * A function that takes an equation ID and base URL and returns the URL to link
			 * to it. The base value is taken from the baseURL value, so that links can be
			 * make within a page even if it has a <base> element that sets the base URL for
			 * the page to a different location.<br>
			 * <br>
			 * <i>The default is:</i>
			 * 
			 * <pre>
			 * <code>url: function (id, base) {return base + '#' + encodeURIComponent(id)}</code>
			 * </pre>
			 * 
			 * @param f a function for this field
			 * @return {@code this} for further method chaining
			 */
			public TagFormatOptionsBuilder setURLFormat(JSFunction f) {
				fields.put(new JSMemberName(URL, false), f);
				return this;
			}
		}

		/*
		 * Date: 25 Aug 2022-----------------------------------------------------------
		 * Time created: 09:03:39--------------------------------------------
		 */
		/**
		 * Sets the {@link TexExtensionPackages#AMS} field using the provided
		 * pre-configured builder to build a valid object.
		 * 
		 * @param options a preconfigured builder
		 * @return {@code this} for further method chaining
		 */
		public TexExtensionOptionsBuilder setAms(AMSOptionsBuilder options) {
			fields.put(new JSMemberName(TexExtensionPackages.AMS, false), options.build());
			return this;
		}

		/*
		 * Date: 25 Aug 2022-----------------------------------------------------------
		 * Time created: 09:03:39--------------------------------------------
		 */
		/**
		 * Sets the {@link TexExtensionPackages#AMSCD} field using the provided
		 * pre-configured builder to build a valid object.
		 * 
		 * @param options a preconfigured builder
		 * @return {@code this} for further method chaining
		 */
		public TexExtensionOptionsBuilder setAmscd(AMSCDOptionsBuilder options) {
			fields.put(new JSMemberName(TexExtensionPackages.AMSCD, false), options.build());
			return this;
		}

		/*
		 * Date: 25 Aug 2022-----------------------------------------------------------
		 * Time created: 09:03:39--------------------------------------------
		 */
		/**
		 * Sets the {@link TexExtensionPackages#COLOR} field using the provided
		 * pre-configured builder to build a valid object.
		 * 
		 * @param options a preconfigured builder
		 * @return {@code this} for further method chaining
		 */
		public TexExtensionOptionsBuilder setColor(ColorOptionsBuilder options) {
			fields.put(new JSMemberName(TexExtensionPackages.COLOR, false), options.build());
			return this;
		}

		/*
		 * Date: 25 Aug 2022-----------------------------------------------------------
		 * Time created: 09:03:39--------------------------------------------
		 */
		/**
		 * Sets the {@link TexExtensionPackages#CONFIGMACROS} field using the provided
		 * pre-configured builder to build a valid object.
		 * 
		 * @param options a preconfigured builder
		 * @return {@code this} for further method chaining
		 */
		public TexExtensionOptionsBuilder setConfigMacros(ConfigMacrosOptionsBuilder options) {
			fields.put(new JSMemberName(TexExtensionPackages.CONFIGMACROS, false), options.build());
			return this;
		}

		/*
		 * Date: 25 Aug 2022-----------------------------------------------------------
		 * Time created: 09:03:39--------------------------------------------
		 */
		/**
		 * Sets the {@link TexExtensionPackages#MATHTOOLS} field using the provided
		 * pre-configured builder to build a valid object.
		 * 
		 * @param options a preconfigured builder
		 * @return {@code this} for further method chaining
		 */
		public TexExtensionOptionsBuilder setMathTools(MathToolsOptionsBuilder options) {
			fields.put(new JSMemberName(TexExtensionPackages.MATHTOOLS, false), options.build());
			return this;
		}

		/*
		 * Date: 25 Aug 2022-----------------------------------------------------------
		 * Time created: 09:03:39--------------------------------------------
		 */
		/**
		 * Sets the {@link TexExtensionPackages#NOUNDEFINED} field using the provided
		 * pre-configured builder to build a valid object.
		 * 
		 * @param options a preconfigured builder
		 * @return {@code this} for further method chaining
		 */
		public TexExtensionOptionsBuilder setNoUndefined(NoUndefinedOptionsBuilder options) {
			fields.put(new JSMemberName(TexExtensionPackages.NOUNDEFINED, false), options.build());
			return this;
		}

		/*
		 * Date: 25 Aug 2022-----------------------------------------------------------
		 * Time created: 09:03:39--------------------------------------------
		 */
		/**
		 * Sets the {@link TexExtensionPackages#PHYSICS} field using the provided
		 * pre-configured builder to build a valid object.
		 * 
		 * @param options a preconfigured builder
		 * @return {@code this} for further method chaining
		 */
		public TexExtensionOptionsBuilder setPhysics(PhysicsOptionsBuilder options) {
			fields.put(new JSMemberName(TexExtensionPackages.PHYSICS, false), options.build());
			return this;
		}

		/*
		 * Date: 25 Aug 2022-----------------------------------------------------------
		 * Time created: 09:03:39--------------------------------------------
		 */
		/**
		 * Sets the {@link TexExtensionPackages#REQUIRE} field using the provided
		 * pre-configured builder to build a valid object.
		 * 
		 * @param options a preconfigured builder
		 * @return {@code this} for further method chaining
		 */
		public TexExtensionOptionsBuilder setConfigMacros(RequireOptionsBuilder options) {
			fields.put(new JSMemberName(TexExtensionPackages.REQUIRE, false), options.build());
			return this;
		}

		/*
		 * Date: 25 Aug 2022-----------------------------------------------------------
		 * Time created: 09:03:39--------------------------------------------
		 */
		/**
		 * Sets the {@link TexExtensionPackages#TAGFORMAT} field using the provided
		 * pre-configured builder to build a valid object.
		 * 
		 * @param options a preconfigured builder
		 * @return {@code this} for further method chaining
		 */
		public TexExtensionOptionsBuilder setTagFormat(TagFormatOptionsBuilder options) {
			fields.put(new JSMemberName(TexExtensionPackages.TAGFORMAT, false), options.build());
			return this;
		}
	}

	/*
	 * Date: 21 Aug 2022-----------------------------------------------------------
	 * Time created: 08:19:46--------------------------------------------
	 */
	/**
	 * Sets the {@value #SCALE} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This array lists the names of the packages that should be initialized by the
	 * TeX input processor. The input/tex and input/tex-full components
	 * automatically add to this list the packages that they load. If you explicitly
	 * load addition tex extensions, you should add them to this list. For example:
	 * 
	 * <pre>
	 * <code>
	MathJax = {
	  loader: {load: ['[tex]/enclose']},
	  tex: {
	    packages: {'[+]': ['enclose']}
	  }
	};</code>
	 * </pre>
	 * 
	 * This loads the enclose extension and activates it by including it in the
	 * package list. <br>
	 * <br>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>packages: ['base']</code>
	 * </pre>
	 * 
	 * @param listOfPackages a list of all the packages to be available for the
	 *                       loader object. Each package name is available as a
	 *                       string constant in the interface
	 *                       {@link TexExtensionPackages}.
	 * @return {@code this} for further method chaining
	 */
	public TexInputProcessorBuilder setPackages(JSArray<JSString> listOfPackages) {
		fields.put(new JSMemberName(PACKAGES, false), listOfPackages);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 09:16:40--------------------------------------------
	 */
	/**
	 * Adds/removes a package or list of packages from the load section of the tex
	 * configuration.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * You can remove packages from the default list using '[-]' rather than [+], as
	 * in the followiong example:
	 * 
	 * <pre>
	 * <code>
	MathJax = {
	  tex: {
	    packages: {'[-]': ['noundefined']}
	  }
	};</code>
	 * </pre>
	 * 
	 * This would disable the noundefined extension, so that unknown macro names
	 * would cause error messages rather than be displayed in red.
	 * 
	 * If you need to both remove some default packages and add new ones, you can do
	 * so by including both within the braces:
	 * 
	 * <pre>
	 * <code>
	MathJax = {
	  loader: {load: ['[tex]/enclose']},
	  tex: {
	    packages: {'[-]': ['noundefined', 'autoload'], '[+]': ['enclose']}
	  }
	};</code>
	 * </pre>
	 * 
	 * This disables the noundefined and autoload extensions, and adds in the
	 * enclose extension.
	 * 
	 * @param toBeRemoved a list of tex packages to be removed from the default list
	 *                    of loaded packages.
	 * @param toBeAdded   a list of tex packages to be added to the default list of
	 *                    loaded packages.
	 * @return {@code this} for further method chaining
	 * @throws OptionsBuilderModificationException if
	 *                                             {@link #isOptionSet(String, boolean)}
	 *                                             returns {@code false}
	 */
	public TexInputProcessorBuilder addRemovePackage(JSArray<JSString> toBeRemoved, JSArray<JSString> toBeAdded)
			throws OptionsBuilderModificationException {
		if (isOptionSet(PACKAGES, false))
			throw new OptionsBuilderModificationException(PACKAGES);
		Map<JSString, JSArray<JSString>> m = new HashMap<>();
		m.put(new JSString("[-]"), toBeRemoved);
		m.put(new JSString("[+]"), toBeAdded);
		JSDictionary<JSString, JSArray<JSString>> d = new JSDictionary<>(m);
		fields.put(new JSMemberName(PACKAGES, false), MathJaxOptionsBuilder.convertDictionaryToObject(d));
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 09:22:22--------------------------------------------
	 */
	/**
	 * Sets the {@value #INLINEMATH} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This is an array of pairs of strings that are to be used as in-line math
	 * delimiters. The first in each pair is the initial delimiter and the second is
	 * the terminal delimiter. You can have as many pairs as you want. For example,
	 * 
	 * <pre>
	 * <code>inlineMath: [ ['$','$'], ['\\(','\\)'] ]</code>
	 * </pre>
	 * 
	 * would cause MathJax to look for $...$ and \(...\) as delimiters for in-line
	 * mathematics. (Note that the single dollar signs are not enabled by default
	 * because they are used too frequently in normal text, so if you want to use
	 * them for math delimiters, you must specify them explicitly.)
	 * <p>
	 * Note that the delimiters can’t look like HTML tags (i.e., can’t include the
	 * less-than sign), as these would be turned into tags by the browser before
	 * MathJax has the chance to run. You can only include text, not tags, as your
	 * math delimiters.
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>inlineMath: [['\(','\)']]</code>
	 * </pre>
	 * 
	 * @param delim an array of single length dictionaries where the key is the left
	 *              delimiter and the value is the right delimiter.
	 * @return {@code this} for further method chaining
	 */
	public TexInputProcessorBuilder setDelimitersForInlineMath(JSArray<JSDictionary<JSString, JSString>> delim) {
		fields.put(new JSMemberName(INLINEMATH, false), delim);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 09:28:34--------------------------------------------
	 */
	/**
	 * Sets the {@value #DISPLAYMATH} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This is an array of pairs of strings that are to be used as delimiters for
	 * displayed equations. The first in each pair is the initial delimiter and the
	 * second is the terminal delimiter. You can have as many pairs as you want.
	 * <p>
	 * Note that the delimiters can’t look like HTML tags (i.e., can’t include the
	 * less-than sign), as these would be turned into tags by the browser before
	 * MathJax has the chance to run. You can only include text, not tags, as your
	 * math delimiters.
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>displayMath: [ ['$$','$$'], ['\[','\]'] ]</code>
	 * </pre>
	 * 
	 * @param delim an array of single length dictionaries where the key is the left
	 *              delimiter and the value is the right delimiter.
	 * @return {@code this} for further method chaining
	 */
	public TexInputProcessorBuilder setDelimitersForDisplayMath(JSArray<JSDictionary<JSString, JSString>> delim) {
		fields.put(new JSMemberName(DISPLAYMATH, false), delim);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 09:28:34--------------------------------------------
	 */
	/**
	 * Sets the {@value #PROCESSESCAPE} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * When set to true, you may use \$ to represent a literal dollar sign, rather
	 * than using it as a math delimiter, and \\ to represent a literal backslash
	 * (so that you can use \\\$ to get a literal \$ or \\$...$ to get a backslash
	 * jsut before in-line math). When false, \$ will not be altered, and its dollar
	 * sign may be considered part of a math delimiter. Typically this is set to
	 * true if you enable the $ ... $ in-line delimiters, so you can type \$ and
	 * MathJax will convert it to a regular dollar sign in the rendered document.
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>processEscapes: false</code>
	 * </pre>
	 * 
	 * @param processEscape whther or no to process escapes
	 * @return {@code this} for further method chaining
	 */
	public TexInputProcessorBuilder setProcessEscapes(JSBoolean processEscape) {
		fields.put(new JSMemberName(PROCESSESCAPE, false), processEscape);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 09:28:34--------------------------------------------
	 */
	/**
	 * Sets the {@value #PROCESSREFS} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * When set to true, MathJax will process \ref{...} outside of math mode.
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>processRefs: true</code>
	 * </pre>
	 * 
	 * @param processRefs whether or no to process refs outside of Math mode.
	 * @return {@code this} for further method chaining
	 */
	public TexInputProcessorBuilder setProcessRefs(JSBoolean processRefs) {
		fields.put(new JSMemberName(PROCESSREFS, false), processRefs);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 09:28:34--------------------------------------------
	 */
	/**
	 * Sets the {@value #PROCESSENVIRONMENTS} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * When true, tex2jax looks not only for the in-line and display math
	 * delimiters, but also for LaTeX environments
	 * (\begin{something}...\end{something}) and marks them for processing by
	 * MathJax. When false, LaTeX environments will not be processed outside of math
	 * mode.
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>processEnvironments: true</code>
	 * </pre>
	 * 
	 * @param isProcess whether or no to process environment outside of Math mode.
	 * @return {@code this} for further method chaining
	 */
	public TexInputProcessorBuilder setProcessEnvironments(JSBoolean isProcess) {
		fields.put(new JSMemberName(PROCESSENVIRONMENTS, false), isProcess);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 09:28:34--------------------------------------------
	 */
	/**
	 * Sets the {@value #DIGITS} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This gives a regular expression that is used to identify numbers during the
	 * parsing of your TeX expressions. By default, the decimal point is . and you
	 * can use {,} between every three digits before that. If you want to use {,} as
	 * the decimal indicator, use
	 * 
	 * <pre>
	 * <code>
	MathJax = {
	  tex: {
	    digits: /^(?:[0-9]+(?:\{,\}[0-9]*)?|\{,\}[0-9]+)/
	  }
	};</code>
	 * </pre>
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>digits: /^(?:[0-9]+(?:{,}[0-9]{3})*(?:.[0-9]*)?|.[0-9]+)/</code>
	 * </pre>
	 * 
	 * @param digitPattern the regular expression for digits
	 * @return {@code this} for further method chaining
	 */
	public TexInputProcessorBuilder setDigits(JSRegex digitPattern) {
		// Please note that the mathaid api already has a function for dealing with
		// digit formatting in the segment sub-api
		fields.put(new JSMemberName(DIGITS, false), digitPattern);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 09:28:34--------------------------------------------
	 */
	/**
	 * Sets the {@value #TAGS} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This controls whether equations are numbered and how. By default it is set to
	 * 'none' to be compatible with earlier versions of MathJax where auto-numbering
	 * was not performed (so pages will not change their appearance). You can change
	 * this to 'ams' for equations numbered as the AMSmath package would do, or
	 * 'all' to get an equation number for every displayed equation.
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>tags: 'none'</code>
	 * </pre>
	 * 
	 * @param opt the option for tag numbering
	 * @return {@code this} for further method chaining
	 */
	public TexInputProcessorBuilder setTags(JSString opt) {
		fields.put(new JSMemberName(TAGS, false), opt);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 09:28:34--------------------------------------------
	 */
	/**
	 * Sets the {@value #TAGSIDE} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This specifies the side on which \tag{} macros will place the tags, and on
	 * which automatic equation numbers will appear. Set it to 'left' to place the
	 * tags on the left-hand side.
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>tagSide: 'right'</code>
	 * </pre>
	 * 
	 * @param tagSide a string to specify the visual direction of equation tags.
	 * @return {@code this} for further method chaining
	 */
	public TexInputProcessorBuilder setTagSide(JSString tagSide) {
		fields.put(new JSMemberName(TAGSIDE, false), tagSide);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 09:28:34--------------------------------------------
	 */
	/**
	 * Sets the {@value #TAGINDENT} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This is the amount of indentation (from the right or left) for the tags
	 * produced by the \tag{} macro or by automatic equation numbers.
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>tagIndent: "0.8em"</code>
	 * </pre>
	 * 
	 * @param tagIndent a CSS3 string to specify the length
	 * @return {@code this} for further method chaining
	 */
	public TexInputProcessorBuilder setTagIndent(JSString tagIndent) {
		/*
		 * Note that the value is a string in the same format and syntax that distance
		 * is specified in CSS3
		 */
		fields.put(new JSMemberName(TAGINDENT, false), tagIndent);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 09:49:19--------------------------------------------
	 */
	/**
	 * Sets the {@value #USELABELIDS} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This controls whether element IDs for tags use the \label name or the
	 * equation number. When true, use the label, when false, use the equation
	 * number.
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>useLabelIds: true</code>
	 * </pre>
	 * 
	 * @param use use label id?
	 * @return {@code this} for further method chaining
	 */
	public TexInputProcessorBuilder setUseLabelIds(JSBoolean use) {
		fields.put(new JSMemberName(USELABELIDS, false), use);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 09:49:19--------------------------------------------
	 */
	/**
	 * Sets the {@value #MAXMACROS} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * Because a definition of the form \def\x{\x} \x would cause MathJax to loop
	 * infinitely, the maxMacros constant will limit the number of macro
	 * substitutions allowed in any expression processed by MathJax.
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>maxMacros: 10000</code>
	 * </pre>
	 * 
	 * @param max a number to specify the max macros.
	 * @return {@code this} for further method chaining
	 */
	public TexInputProcessorBuilder setMaxMacros(JSNumber max) {
		fields.put(new JSMemberName(MAXMACROS, false), max);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 09:49:19--------------------------------------------
	 */
	/**
	 * Sets the {@value #MAXBUFFER} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * Because a definition of the form \def\x{\x aaa} \x would loop infinitely, and
	 * at the same time stack up lots of a’s in MathJax’s equation buffer, the
	 * maxBuffer constant is used to limit the size of the string being processed by
	 * MathJax. It is set to 5KB, which should be sufficient for any reasonable
	 * equation.
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>maxBuffer: 5 * 1024</code>
	 * </pre>
	 * 
	 * @param max a number to specify the max buffer.
	 * @return {@code this} for further method chaining
	 */
	public TexInputProcessorBuilder setMaxBuffer(JSNumber max) {
		fields.put(new JSMemberName(MAXBUFFER, false), max);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 09:49:19--------------------------------------------
	 */
	/**
	 * Sets the {@value #BASEURL} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This is the base URL to use when creating links to tagged equations (via
	 * \ref{} or \eqref{}) when there is a <base> element in the document that would
	 * affect those links. You can set this value by hand if MathJax doesn’t produce
	 * the correct link.
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>baseURL: (document.getElementsByTagName('base').length === 0) ?
	'' : String(document.location).replace(/#.*$/, ''))</code>
	 * </pre>
	 * 
	 * @param url a string to specify the URL. use
	 *            {@link JSValue#byFunction(JSFunction)} to pass a function that
	 *            returns a string to this method, much like the one that is used in
	 *            the default implementation.
	 * @return {@code this} for further method chaining
	 */
	public TexInputProcessorBuilder setBaseUrl(JSString url) {
		fields.put(new JSMemberName(BASEURL, false), url);
		return this;
	}

	/**
	 * Sets the {@value #FORMATERROR} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This is a function that is called when the TeX input jax reports a syntax or
	 * other error in the TeX that it is processing. The default is to generate an
	 * &lt;merror&gt; MathML element with the message indicating the error that
	 * occurred. You can override the function to perform other tasks, like
	 * recording the message, replacing the message with an alternative message, or
	 * throwing the error so that MathJax will stop at that point (you can catch the
	 * error using promises or a try/catch block).
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>formatError: (jax, err) => jax.formatError(err)</code>
	 * </pre>
	 * 
	 * @param f a function to execute when an error occurs during input processing
	 *          of tex code
	 * @return {@code this} for further method chaining
	 */
	public TexInputProcessorBuilder setFormatError(JSFunction f) {
		fields.put(new JSMemberName(FORMATERROR, false), f);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 10:30:10--------------------------------------------
	 */
	/**
	 * Sets package-specific configurations which is built from the values of the argument builder.
	 * @param extensions a map where each <i>key</i> corresponds to the field name
	 *                   of the options object which is built by the <i>value</i> of
	 *                   the map.
	 * @return {@code this} for further method chaining
	 */
	public TexInputProcessorBuilder setExtensionsAndMacros(TexExtensionOptionsBuilder extensions) {
//		for (JSMemberName s : extensions.keySet())
//			fields.put(s, extensions.get(s).build());
		Map<JSMemberName, JSValue<?>> m = extensions.build().getMembers();
		for(Map.Entry<JSMemberName, JSValue<?>> e : m.entrySet())
			fields.put(e.getKey(), e.getValue());
		return this;
	}

	/**
	 * Sets the {@value #FINDTEX} field. This is a developer option.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * The FindTeX object instance that will override the default one. This allows
	 * you to create a subclass of FindTeX and pass that to the TeX input jax. A
	 * null value means use the default FindTeX class and make a new instance of
	 * that.
	 * <p>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>FindTeX: null</code>
	 * </pre>
	 * 
	 * @param o an object which is a subclass of {@code FindTeX} class.
	 * @return {@code this} for further method chaining
	 */
	@Override
	public TexInputProcessorBuilder setFind(JSObject o) {
		fields.put(new JSMemberName(FINDTEX, false), o);
		return this;
	}
}
