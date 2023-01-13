/**
 * 
 */
package mathaid.calculator.base.gui.mathjax;

import java.util.HashMap;
import java.util.Map;

import mathaid.js.JSArray;
import mathaid.js.JSDictionary;
import mathaid.js.JSFunction;
import mathaid.js.JSMemberName;
import mathaid.js.JSObject;
import mathaid.js.JSRegex;
import mathaid.js.JSString;

/*
 * Date: 21 Aug 2022----------------------------------------------------------- 
 * Time created: 01:34:08---------------------------------------------------  
 * Package: js.mathjax.options------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: DocumentOptionsBuilder.java------------------------------------------------------ 
 * Class name: DocumentOptionsBuilder------------------------------------------------ 
 */
/**
 * <p>
 * <b>Copied from the MathJax documentation:</b>
 * <p>
 * The options below control the operation of the MathDocument object created by
 * MathJax to process the mathematics in your web page. They are listed with
 * their default values. To set any of these options, include an options section
 * in your MathJax global object.<br>
 * <br>
 * An example of the code produced by from the final built object is:
 * 
 * <pre>
 * <code>options: {
    skipHtmlTags: [            //  HTML tags that won't be searched for math
        'script', 'noscript', 'style', 'textarea', 'pre',
        'code', 'annotation', 'annotation-xml'
    ],
    includeHtmlTags: {         //  HTML tags that can appear within math
        br: '\n', wbr: '', '#comment': ''
    },
    ignoreHtmlClass: 'tex2jax_ignore',    //  class that marks tags not to search
    processHtmlClass: 'tex2jax_process',  //  class that marks tags that should be searched
    compileError: function (doc, math, err) {doc.compileError(math, err)},
    typesetError: function (doc, math, err) {doc.typesetError(math, err)},
    renderActions: {...}
  }
 *  </code>
 * </pre>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class DocumentOptionsBuilder extends AbstractOptionsBuilder {

	/**
	 * The field name for {@link #setTagsToSkip(JSArray)}
	 */
	public static final String SKIPHTMLTAGS = "skipHtmlTags";
	/**
	 * The field name for {@link #setTagsToInclude(JSDictionary)}
	 */
	public static final String INCLUDEHTMLTAGS = "includeHtmlTags";
	/**
	 * The field name for {@link #setClassesToIgnore(JSRegex)}
	 */
	public static final String IGNOREHTMLCLASS = "ignoreHtmlClass";
	/**
	 * The field name for {@link #setClassesToProcess(JSRegex)}
	 */
	public static final String PROCESSHTMLCLASS = "processHtmlClass";
	/**
	 * The field name for {@link #setCallBackForCompileError(JSFunction)}
	 */
	public static final String COMPILEERROR = "compileError";
	/**
	 * The field name for {@link #setCallBackForTypesetError(JSFunction)}
	 */
	public static final String TYPESETERROR = "typesetError";
	/**
	 * The field name for {@link #setRenderActionsObject(RenderActionsBuilder)}
	 */
	public static final String RENDERACTIONS = "renderActions";
	/**
	 * The field name for {@link #setOutputJax(JSObject)}
	 */
	public static final String OUTPUTJAX = "OutputJax";
	/**
	 * The field name for {@link #setInputJax(JSObject)}
	 */
	public static final String INPUTJAX = "InputJax";
	/**
	 * The field name for {@link #setMmlFactory(JSObject)}
	 */
	public static final String MMLFACTORY = "MmlFactory";
	/**
	 * The field name for {@link #setMathList(JSObject)}
	 */
	public static final String MATHLIST = "MathList";
	/**
	 * The field name for {@link #setMathItem(JSObject)}
	 */
	public static final String MATHITEM = "MathItem";

	/*
	 * Date: 21 Aug 2022-----------------------------------------------------------
	 * Time created: 02:22:53--------------------------------------------
	 */
	/**
	 * Sets the {@value #SKIPHTMLTAGS} field.
	 * <p>
	 * <b>Mostly copied from the MathJax Documentation:</b>
	 * <p>
	 * This array lists the names of the tags whose contents should not be processed
	 * by MathJaX (other than to look for ignore/process classes as listed below).
	 * For example:
	 * 
	 * <pre>
	 * <code>skipHtmlTags: [            //  HTML tags that won't be searched for math
	 * 'script', 'noscript', 'style', 'textarea', 'pre',
	 * 'code', 'annotation', 'annotation-xml'
	 * ]</code>
	 * </pre>
	 * 
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>skipHtmlTags: ['script', 'noscript', 'style', 'textarea',
	 *	'pre', 'code', 'annotation', 'annotation-xml']</code>
	 * </pre>
	 * 
	 * @param tagsToSkip a list of HTML tags to be skipped by the MathJax engine when searching for math within HTML elements.
	 * @return {@code this} for further method chaining
	 */
	public DocumentOptionsBuilder setTagsToSkip(JSArray<JSString> tagsToSkip) {
		fields.put(new JSMemberName(SKIPHTMLTAGS, false), tagsToSkip);
		return this;
	}

	/*
	 * Date: 21 Aug 2022-----------------------------------------------------------
	 * Time created: 03:24:07--------------------------------------------
	 */
	/**
	 * Adds and removes tags from the {@value #SKIPHTMLTAGS} field's default values
	 * according to the mathjax specification for adding and removal of options
	 * value (see below). Note that arrays should not be empty or result of the
	 * object built may cause an error in the top-level configuration object will be
	 * undefined.
	 * <p>
	 * <b>Copied from the MathJax Documentation:</b>
	 * <p>
	 * You can add to (or remove from) this list to prevent MathJax from processing
	 * mathematics in specific contexts. E.g.,
	 * 
	 * <pre>
	 * <code>skipHtmlTags: {'[-]': ['code', 'pre'], '[+]': ['li']}</code>
	 * </pre>
	 * 
	 * would remove {@code 'code'} and {@code 'pre'} tags from the list, while
	 * adding {@code 'li'} tags to the list.
	 * 
	 * @param toBeRemoved  a list of HTML tags to be removed from the default tags-to-skip list.
	 * @param toBeAdded a list of HTML tags to be added to the default tags-to-skip list.
	 * @return {@code this} for further method chaining
	 * @throws OptionsBuilderModificationException the {@value #SKIPHTMLTAGS} was
	 *                                             previously set.
	 */
	public DocumentOptionsBuilder addRemoveTagsToSkip(JSArray<JSString> toBeRemoved, JSArray<JSString> toBeAdded)
			throws OptionsBuilderModificationException {
		if (isOptionSet(SKIPHTMLTAGS, false))
			throw new OptionsBuilderModificationException(SKIPHTMLTAGS);
		Map<JSString, JSArray<JSString>> m = new HashMap<>();
		m.put(new JSString("[-]"), toBeRemoved);
		m.put(new JSString("[+]"), toBeAdded);
		JSDictionary<JSString, JSArray<JSString>> d = new JSDictionary<>(m);
		fields.put(new JSMemberName(SKIPHTMLTAGS, false), MathJaxOptionsBuilder.convertDictionaryToObject(d));
		return this;
	}

	/*
	 * Date: 21 Aug 2022-----------------------------------------------------------
	 * Time created: 04:33:52--------------------------------------------
	 */
	/**
	 * Set the {@value #INCLUDEHTMLTAGS} field.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * This object specifies what tags can appear within a math expression, and what
	 * text to replace them by within the math. The default is to allow
	 * <code>&lt;br&gt;</code> , which becomes a newline, and
	 * <code>&lt;wbr&gt;</code> and HTML <code>comments</code>, which are removed
	 * entirely.
	 * 
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>includeHtmlTags: {br: 'n', wbr: '', '#comment': ''}</code>
	 * </pre>
	 * 
	 * 
	 * @param tagsToInclude a dictionary of string keys which correspond to real
	 *                      HTML tags and corresponding string values which are
	 *                      strings that will replace that tag within the source
	 *                      Math expression.
	 * @return {@code this} for further method chaining
	 */
	public DocumentOptionsBuilder setTagsToInclude(JSDictionary<JSString, JSString> tagsToInclude) {
		JSObject o = MathJaxOptionsBuilder.convertDictionaryToObject(tagsToInclude);
		fields.put(new JSMemberName(INCLUDEHTMLTAGS, false), o);
		return this;
	}

	/*
	 * Date: 21 Aug 2022-----------------------------------------------------------
	 * Time created: 05:12:10--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #IGNOREHTMLCLASS}.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * This is the class name used to mark elements whose contents should not be
	 * processed by MathJax (other than to look for the processHtmlClass pattern
	 * below). Note that this is a regular expression, and so you need to be sure to
	 * quote any regexp special characters. The pattern is inserted into one that
	 * requires your pattern to match a complete word, so setting
	 * <code>ignoreHtmlClass:
	 * 'class2'</code> would cause it to match an element with
	 * <code>class='class1 class2 class3'</code> but not
	 * <code>class='myclass2'</code>. Note that you can assign several classes by
	 * separating them by the vertical line character (<code>|</code>). For
	 * instance, with <code>ignoreHtmlClass: 'class1|class2'</code> any element
	 * assigned a class of either <code>class1</code> or <code>class2</code> will be
	 * skipped. This could also be specified by
	 * <code>ignoreHtmlClass: 'class[12]'</code>, which matches {@code class}
	 * followed by either a <code>1</code> or a <code>2</code>.
	 * 
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>ignoreHtmlClass: 'mathjax_ignore'</code>
	 * </pre>
	 * 
	 * @param regexPatternForClassesToIgnore the classes to ignore
	 * @return {@code this} for further method chaining
	 */
	public DocumentOptionsBuilder setClassesToIgnore(JSRegex regexPatternForClassesToIgnore) {
		String pattern = regexPatternForClassesToIgnore.asPattern().pattern();
		fields.put(new JSMemberName(IGNOREHTMLCLASS, false), new JSString(pattern));
		return this;
	}

	/*
	 * Date: 21 Aug 2022-----------------------------------------------------------
	 * Time created: 05:18:16--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #PROCESSHTMLCLASS}.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * 
	 * This is the class name used to mark elements whose contents should be
	 * processed by MathJax. This is used to restart processing within tags that
	 * have been marked as ignored via the ignoreHtmlClass or to cause a tag that
	 * appears in the <code>skipHtmlTags</code> list to be processed rather than
	 * skipped. Note that this is a regular expression, and so you need to be sure
	 * to quote any regexp special characters. The pattern is inserted into one that
	 * requires your pattern to match a complete word, so setting
	 * <code>processHtmlClass: 'class2'</code> would cause it to match an element
	 * with <code>class='class1 class2 class3'</code> but not
	 * <code>class='myclass2'</code>. Note that you can assign several classes by
	 * separating them by the vertical line character ({@code |}). For instance,
	 * with processHtmlClass: {@code 'class1|class2'} any element assigned a class
	 * of either {@code class1} or {@code class2} will have its contents processed.
	 * This could also be specified by {@code processHtmlClass: 'class[12]'}, which
	 * matches {@code class} followed by either a {@code 1} or a {@code 2}.
	 * 
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>processHtmlClass: 'mathjax_process'</code>
	 * </pre>
	 * 
	 * @param regexPatternForClassesToInclude the class to include
	 * @return {@code this} for further method chaining
	 */
	public DocumentOptionsBuilder setClassesToProcess(JSRegex regexPatternForClassesToInclude) {
		String pattern = regexPatternForClassesToInclude.asPattern().pattern();
		fields.put(new JSMemberName(PROCESSHTMLCLASS, false), new JSString(pattern));
		return this;
	}

	/*
	 * Date: 21 Aug 2022-----------------------------------------------------------
	 * Time created: 05:29:08--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #PROCESSHTMLCLASS}, which is a function.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * This is the function called whenever there is an uncaught error while an
	 * input jax is running (i.e., during the document’s {@code compile()} call).
	 * The arguments are the {@code MathDocument} in which the error occurred, the
	 * {@code MathItem} for the expression where it occurred, and the {@code Error}
	 * object for the uncaught error. The default action is to call the document’s
	 * default {@code compileError()} function, which sets {@code math.root} to a
	 * math element containing an error message (i.e.,
	 * <code>&lt;math&gt;&lt;merror&gt;&lt;mtext&gt;Math input error&lt;mtext&gt;&lt;/merror&gt;&lt;/math&gt;</code>).
	 * You can replace this with your own function for trapping run-time errors in
	 * the input processors.
	 * 
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>compileError: function (doc, math, err) {doc.compileError(math, err)}</code>
	 * </pre>
	 * 
	 * @param callBack a function that will be called every time there is a compile error
	 * @return {@code this} for further method chaining
	 */
	public DocumentOptionsBuilder setCallBackForCompileError(JSFunction callBack) {
		fields.put(new JSMemberName(COMPILEERROR, false), callBack);
		return this;
	}

	/*
	 * Date: 21 Aug 2022-----------------------------------------------------------
	 * Time created: 05:42:19--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #TYPESETERROR}, which is a function.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * This is the function called whenever there is an uncaught error while an
	 * output jax is running (i.e., during the document’s typeset() call). The
	 * arguments are the MathDocument in which the error occurred, the MathItem for
	 * the expression where it occurred, and the Error object for the uncaught
	 * error. The default action is to call the document’s default typesetError()
	 * function, which sets math.typesetRoot to a <span> element containing the text
	 * Math output error. You can replace this with your own function for trapping
	 * run-time errors in the output processors.
	 * 
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>typesetError: function (doc, math, err) {doc.typesetError(math, err)}</code>
	 * </pre>
	 * 
	 * @param callBack afunction to be called anytime there is a compile error
	 * @return {@code this} for further method chaining
	 */
	public DocumentOptionsBuilder setCallBackForTypesetError(JSFunction callBack) {
		fields.put(new JSMemberName(TYPESETERROR, false), callBack);
		return this;
	}

	/*
	 * 
	 * Date: 21 Aug 2022-----------------------------------------------------------
	 * Time created: 05:47:57--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #RENDERACTIONS}, which is an object.
	 * 
	 * @param object a builder for the render options object
	 * @see RenderActionsBuilder
	 * @return {@code this} for further method chaining
	 */
	public DocumentOptionsBuilder setRenderActionsObject(RenderActionsBuilder object) {
		fields.put(new JSMemberName(RENDERACTIONS, false), object.build());
		return this;
	}

	/*
	 * The following are developer options for this builder. Developer options are
	 * options which allows a user of MathJax to directly interact/read/modify the
	 * internal MathJax API.
	 */

	/*
	 * Date: 21 Aug 2022-----------------------------------------------------------
	 * Time created: 07:31:43--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #OUTPUTJAX}, which is an object. This is developer
	 * option
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * The OutputJax object instance to use for this MathDocument. If you are using
	 * MathJax components, the startup component will create this automatically. If
	 * you are writing a Node application accessing MathJax code directly, you will
	 * need to create the output jax yourself and pass it to the document through
	 * this option.
	 * 
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>OutputJax: null</code>
	 * </pre>
	 * 
	 * @param <J>       the type of outputJax. Note that this must be a subclass
	 *                  <code>OutputJax</code>
	 * @param outputJax the value that is a subclass of the MathJax class
	 *                  {@code OutputJax}
	 * @return {@code this} for further method chaining
	 */
	public <J extends JSObject> DocumentOptionsBuilder setOutputJax(J outputJax) {
		fields.put(new JSMemberName(OUTPUTJAX, false), outputJax);
		return this;
	}

	/*
	 * Date: 21 Aug 2022-----------------------------------------------------------
	 * Time created: 07:31:43--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #INPUTJAX}, which is an object. This is developer
	 * option.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * The InputJax object instance to use for this MathDocument. If you are using
	 * MathJax components, the startup component will create this automatically. If
	 * you are writing a Node application accessing MathJax code directly, you will
	 * need to create the input jax yourself and pass it to the document through
	 * this option.
	 * 
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>InputJax: null</code>
	 * </pre>
	 * 
	 * @param <J>      the type of inputJax. Note that this must be a subclass
	 *                 <code>InputJax</code>
	 * @param inputJax the value that is a subclass of the MathJax class
	 *                 {@code InputJax}
	 * @return {@code this} for further method chaining
	 */
	public <J extends JSObject> DocumentOptionsBuilder setInputJax(J inputJax) {
		fields.put(new JSMemberName(INPUTJAX, false), inputJax);
		return this;
	}

	/*
	 * Date: 21 Aug 2022-----------------------------------------------------------
	 * Time created: 07:31:43--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #MMLFACTORY}, which is an object. This is
	 * developer option.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * The MmlFactory object instance to use for creating the internal MathML
	 * objects. This allows you to create a subclass of MmlFactory and pass that to
	 * the document. A null value means use the default MmlFactory class and make a
	 * new instance of that.
	 * 
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>MmlFactory: null</code>
	 * </pre>
	 * 
	 * @param <M>        the type of mmlFactory. Note that this must be a subclass
	 *                   <code>MmlFactory</code>
	 * @param mmlFactory the value that is a subclass of the MathJax class
	 *                   {@code MmlFactory}
	 * @return {@code this} for further method chaining
	 */
	public <M extends JSObject> DocumentOptionsBuilder setMmlFactory(M mmlFactory) {
		fields.put(new JSMemberName(MMLFACTORY, false), mmlFactory);
		return this;
	}

	/*
	 * Date: 21 Aug 2022-----------------------------------------------------------
	 * Time created: 07:31:43--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #MathList}, which is an object. This is developer
	 * option.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * The MathList object class to use for managing the list of MathItem objects
	 * associated with the MathDocument. This allows you to create a subclass of
	 * MathList and pass that to the document.
	 * 
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>MathList: DefaultMathList</code>
	 * </pre>
	 * 
	 * @param <M>      the type of mathList. Note that this must be a subclass
	 *                 <code>MathList</code>
	 * @param mathList the value that is a subclass of the MathJax class
	 *                 {@code MathList}
	 * @return {@code this} for further method chaining
	 */
	public <M extends JSObject> DocumentOptionsBuilder setMathList(M mathList) {
		fields.put(new JSMemberName(MATHLIST, false), mathList);
		return this;
	}

	/*
	 * Date: 21 Aug 2022-----------------------------------------------------------
	 * Time created: 07:31:43--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #MathItem}, which is an object. This is developer
	 * option.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * The MathItem object class to use for maintaining the information about a
	 * single expression in a MathDocument. This allows you to create a subclass of
	 * MathItem and pass that to the document. The document Handler object may
	 * define its own subclass of MathItem and use that as the default instead. For
	 * example, the HTML handler uses HTMLMathItem objects for this option.
	 * 
	 * <i>The default is:</i>
	 * 
	 * <pre>
	 * <code>MathItem: DefaultMathItem</code>
	 * </pre>
	 * 
	 * @param <M>      the type of mathItem. Note that this must be a subclass
	 *                 <code>MathItem</code>
	 * @param mathItem the value that is a subclass of the MathJax class
	 *                 {@code MathItem}
	 * @return {@code this} for further method chaining
	 */
	public <M extends JSObject> DocumentOptionsBuilder setMathItem(M mathItem) {
		fields.put(new JSMemberName(MATHITEM, false), mathItem);
		return this;
	}

}
