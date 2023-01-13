/**
 * 
 */
package mathaid.calculator.base.gui.mathjax;

import mathaid.js.JSBoolean;
import mathaid.js.JSFunction;
import mathaid.js.JSMemberName;
import mathaid.js.JSObject;
import mathaid.js.JSString;

/*
 * Date: 22 Aug 2022----------------------------------------------------------- 
 * Time created: 07:01:13---------------------------------------------------  
 * Package: js.mathjax.options------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: MmlInputProcessorBuilder.java------------------------------------------------------ 
 * Class name: MmlInputProcessorBuilder------------------------------------------------ 
 */
/**
 * MathML Input Processor Options The options below control the operation of the
 * MathML input processor that is run when you include 'input/mml' in the load
 * array of the loader block of your MathJax configuration, or if you load a
 * combined component that includes the MathML input jax. They are listed with
 * their default values. To set any of these options, include an mml section in
 * your MathJax global object.
 * 
 * The Configuration Block
 * 
 * <pre>
 * <code>
MathJax = {
  mml: {
    parseAs: 'html',                     // or 'xml'
    forceReparse: false,                 // true to serialize and re-parse all MathML
    parseError: function (node) {        // function to process parsing errors
      this.error(this.adaptor.textContent(node).replace(/\n.&ast;/g, ''));
    },
    verify: {                            // parameters controling verification of MathML
      checkArity: true,                  //   check if number of children is correct
      checkAttributes: false,            //   check if attribute names are valid
      fullErrors: false,                 //   display full error messages or just error node
      fixMmultiscripts: true,            //   fix unbalanced mmultiscripts
      fixMtables: true                   //   fix incorrect nesting in mtables
    }
  }
};</code>
 * </pre>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class MmlInputProcessorBuilder extends InputProcessorBuilder {

	/**
	 * Field name for {@link #setParseAs(JSString)}
	 */
	public static final String PARSEAS = "parseAs";
	/**
	 * Field name for {@link #setForceReParse(JSBoolean)}
	 */
	public static final String FORCEREPARSE = "forceReparse";
	/**
	 * Field name for {@link #setParseError(JSFunction)}
	 */
	public static final String PARSEERROR = "parseError";
	/**
	 * Field name for {@link #setVerify(VerifyOptions)}
	 */
	public static final String VERIFY = "verify";
	/**
	 * Field name for {@link #setFind(JSObject)}
	 */
	public static final String FINDMATHML = "FindMathML";
	/**
	 * Field name for {@link #setMathMlCompile(JSObject)}
	 */
	public static final String MATHMLCOMPILE = "MathMLCompile";

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 17:33:45---------------------------------------------------
	 * Package: js.mathjax.mml------------------------------------------------
	 * Project: TestCode------------------------------------------------ File:
	 * MmlInputProcessorBuilder.java------------------------------------------------
	 * ------ Class name:
	 * VerifyOptions------------------------------------------------
	 */
	/**
	 * A builder that configures the verify section of the input mml configurations.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 */
	public static class VerifyOptions extends AbstractOptionsBuilder {
		/**
		 * Field name for {@link #setCheckArity(JSBoolean)}
		 */
		public static final String CHECKARITY = "checkArity";
		/**
		 * Field name for {@link #setCheckAttributes(JSBoolean)}
		 */
		public static final String CHECKATTRIBUTES = "checkAttributes";
		/**
		 * Field name for {@link #setFullErrors(JSBoolean)}
		 */
		public static final String FULLERRORS = "fullErrors";
		/**
		 * Field name for {@link #setFixMMultiScripts(JSBoolean)}
		 */
		public static final String FIXMMULTISCRIPTS = "fixMmultiscripts";
		/**
		 * Field name for {@link #setFixMTables(JSBoolean)}
		 */
		public static final String FIXMTABLES = "fixMtables";

		/*
		 * Date: 25 Aug 2022-----------------------------------------------------------
		 * Time created: 17:38:03--------------------------------------------
		 */
		/**
		 * Sets the {@value #CHECKARITY} field.
		 * <p>
		 * <b>Copied from the MathJax documentation</b>
		 * <p>
		 * This specifies whether the number of children is verified or not. The default
		 * is to check for the correct number of children. If the number is wrong, the
		 * node is replaced by an &lt;merror&gt; node containing either a message
		 * indicating the wrong number of children, or the name of the node itself,
		 * depending on the setting of fullErrors below.<br>
		 * <br>
		 * <i>The default is:</i>
		 * 
		 * <pre>
		 * <code>checkArity: true</code>
		 * </pre>
		 * 
		 * @param b the value.
		 * @return {@code this} for further method chaining
		 */
		public VerifyOptions setCheckArity(JSBoolean b) {
			fields.put(new JSMemberName(CHECKARITY, false), b);
			return this;
		}

		/*
		 * Date: 25 Aug 2022-----------------------------------------------------------
		 * Time created: 17:38:03--------------------------------------------
		 */
		/**
		 * Sets the {@value #CHECKATTRIBUTES} field.
		 * <p>
		 * <b>Copied from the MathJax documentation</b>
		 * <p>
		 * This specifies whether the names of all attributes are checked to see if they
		 * are valid on the given node (i.e., they have a default value, or are one of
		 * the standard attributes such as style, class, id, href, or a data- attribute.
		 * If an attribute is in error, the node is either placed inside an
		 * &lt;merror&gt; node (so that it is marked in the output as containing an
		 * error), or is replaced by an &lt;merror&gt; containing a full message
		 * indicating the bad attribute, depending on the setting of fullErrors below.
		 * <p>
		 * Currently only names are checked, not values. Value verification may be added
		 * in a future release.<br>
		 * <br>
		 * <i>The default is:</i>
		 * 
		 * <pre>
		 * <code>checkAttributes: false</code>
		 * </pre>
		 * 
		 * @param b the value.
		 * @return {@code this} for further method chaining
		 */
		public VerifyOptions setCheckAttributes(JSBoolean b) {
			fields.put(new JSMemberName(CHECKATTRIBUTES, false), b);
			return this;
		}

		/*
		 * Date: 25 Aug 2022-----------------------------------------------------------
		 * Time created: 17:38:03--------------------------------------------
		 */
		/**
		 * Sets the {@value #FULLERRORS} field.
		 * <p>
		 * <b>Copied from the MathJax documentation</b>
		 * <p>
		 * This specifies whether a full error message is displayed when a node produces
		 * an error, or whether just the node name is displayed (or the node itself in
		 * the case of attribute errors).<br>
		 * <br>
		 * <i>The default is:</i>
		 * 
		 * <pre>
		 * <code>fullErrors: false</code>
		 * </pre>
		 * 
		 * @param b the value.
		 * @return {@code this} for further method chaining
		 */
		public VerifyOptions setFullErrors(JSBoolean b) {
			fields.put(new JSMemberName(FULLERRORS, false), b);
			return this;
		}

		/*
		 * Date: 25 Aug 2022-----------------------------------------------------------
		 * Time created: 17:38:03--------------------------------------------
		 */
		/**
		 * Sets the {@value #FIXMMULTISCRIPTS} field.
		 * <p>
		 * <b>Copied from the MathJax documentation</b>
		 * <p>
		 * This specifies whether extra <none/> entries are added to
		 * &lt;mmultiscripts&gt; elements to balance the super- ans subscripts, as
		 * required by the specification, or whether to generate an error instead.<br>
		 * <br>
		 * <i>The default is:</i>
		 * 
		 * <pre>
		 * <code>fixMtables: true</code>
		 * </pre>
		 * 
		 * @param b the value.
		 * @return {@code this} for further method chaining
		 */
		public VerifyOptions setFixMMultiScripts(JSBoolean b) {
			fields.put(new JSMemberName(FIXMMULTISCRIPTS, false), b);
			return this;
		}

		/*
		 * Date: 25 Aug 2022-----------------------------------------------------------
		 * Time created: 17:38:03--------------------------------------------
		 */
		/**
		 * Sets the {@value #FIXMTABLES} field.
		 * <p>
		 * <b>Copied from the MathJax documentation</b>
		 * <p>
		 * This specifies whether missing &lt;mtable&gt;, &lt;mtr&gt; and &lt;mtd&gt;
		 * elements are placed around cells or not. When true, MathJax will attempt to
		 * correct the table structure if these elements are missing from the tree. For
		 * example, an &lt;mtr&gt; element that is not within an &lt;mtable&gt; will
		 * have an &lt;mtable&gt; placed around it automatically, and an &lt;mtable&gt;
		 * containing an &lt;mi&gt; as a direct child node will have an &lt;mtr&gt; and
		 * &lt;mtd&gt; inserted around the &lt;mi&gt;.<br>
		 * <br>
		 * <i>The default is:</i>
		 * 
		 * <pre>
		 * <code>fixMtables: true</code>
		 * </pre>
		 * 
		 * @param b the value.
		 * @return {@code this} for further method chaining
		 */
		public VerifyOptions setFixMTables(JSBoolean b) {
			fields.put(new JSMemberName(FIXMTABLES, false), b);
			return this;
		}
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 17:38:03--------------------------------------------
	 */
	/**
	 * Sets the {@value #PARSEAS} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * Specifies how MathML strings should be parsed: as XML or as HTML. When set to
	 * 'xml', the browser’s XML parser is used, which is more strict about format
	 * (e.g., matching end tags) than the HTML parser, which is the default. In node
	 * application (where the liteDOM is used), these both use the same parser,
	 * which is not very strict.<br>
	 * <br>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>parseAs: 'html'</code>
	 * </pre>
	 * 
	 * @param s a string for the file type (either 'html' or 'xml')
	 * @return {@code this} for further method chaining
	 */
	public MmlInputProcessorBuilder setParseAs(JSString s) {
		fields.put(new JSMemberName(PARSEAS, false), s);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 17:38:03--------------------------------------------
	 */
	/**
	 * Sets the {@value #FORCEREPARSE} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * Specifies whether MathJax will serialize and re-parse MathML found in the
	 * document. This can be useful if you want to do XML parsing of the MathML from
	 * an HTML document.<br>
	 * <br>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>forceReparse: false</code>
	 * </pre>
	 * 
	 * @param b true to reparse false otherwise
	 * @return {@code this} for further method chaining
	 */
	public MmlInputProcessorBuilder setForceReParse(JSBoolean b) {
		fields.put(new JSMemberName(FORCEREPARSE, false), b);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 17:38:03--------------------------------------------
	 */
	/**
	 * Sets the {@value #PARSEERROR} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * Specifies a function to be called when there is a parsing error in the MathML
	 * (usually only happens with XML parsing). The node is a DOM node containing
	 * the error text. Your function can process that in any way it sees fit. The
	 * default is to call the MathML input processor’s error function with the text
	 * of the error (which will create an merror node with the error message). Note
	 * that this function runs with this being the MathML input processor
	 * object.<br>
	 * <br>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>parseError: (node) => {...}</code>
	 * </pre>
	 * 
	 * @param f a function to be called when there is a parsing error
	 * @return {@code this} for further method chaining
	 */
	public MmlInputProcessorBuilder setParseError(JSFunction f) {
		fields.put(new JSMemberName(PARSEERROR, false), f);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 17:38:03--------------------------------------------
	 */
	/**
	 * Sets the {@value #VERIFY} field.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * This object controls what verification/modifications are to be performed on
	 * the MathML that is being processed by MathJax.<br>
	 * <br>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>verify: {...}</code>
	 * </pre>
	 * 
	 * @param v a pre-configured builder
	 * @return {@code this} for further method chaining
	 */
	public MmlInputProcessorBuilder setVerify(VerifyOptions v) {
		fields.put(new JSMemberName(VERIFY, false), v.build());
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 17:38:03--------------------------------------------
	 */
	/**
	 * Sets the {@value #FINDMATHML} field. This is a developer option.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * The FindMathML object instance that will override the default one. This
	 * allows you to create a subclass of FindMathML and pass that to the MathML
	 * input jax. A null value means use the default FindMathML class and make a new
	 * instance of that.<br>
	 * <br>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>FindMathML: null</code>
	 * </pre>
	 * 
	 * @param o a subclass of {@code FindMathML}
	 * @return {@code this} for further method chaining
	 */
	@Override
	public MmlInputProcessorBuilder setFind(JSObject o) {
		fields.put(new JSMemberName(FINDMATHML, false), o);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 17:38:03--------------------------------------------
	 */
	/**
	 * Sets the {@value #MATHMLCOMPILE} field. This is a developer option.
	 * <p>
	 * <b>Copied from the MathJax documentation</b>
	 * <p>
	 * The MathMLCompile object instance that will override the default one. This
	 * allows you to create a subclass of MathMLCompile and pass that to the MathML
	 * input jax. A null value means use the default MathMLCompile class and make a
	 * new instance of that.<br>
	 * <br>
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>MathMLCompile: null</code>
	 * </pre>
	 * 
	 * @param o a subclass of {@code MathMLCompile}
	 * @return {@code this} for further method chaining
	 */
	public MmlInputProcessorBuilder setMathMlCompile(JSObject o) {
		fields.put(new JSMemberName(MATHMLCOMPILE, false), o);
		return this;
	}
}
