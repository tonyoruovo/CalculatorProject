/**
 * 
 */
package mathaid.calculator.base.gui.mathjax;

import mathaid.js.JSArray;
import mathaid.js.JSBoolean;
import mathaid.js.JSFunction;
import mathaid.js.JSMemberName;
import mathaid.js.JSObject;
import mathaid.js.JSString;

/*
 * Date: 24 Aug 2022----------------------------------------------------------- 
 * Time created: 04:58:27---------------------------------------------------  
 * Package: js.mathjax.startup------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: StartupBuilder.java------------------------------------------------------ 
 * Class name: StartupBuilder------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class StartupBuilder extends AbstractOptionsBuilder {
	/**
	 * A field name for {@link #setElements(JSArray)}
	 */
	public static final String ELEMENTS = "elements";
	/**
	 * A field name for {@link #setTypesetter(JSBoolean)}
	 */
	public static final String TYPESET = "typeset";
	/**
	 * A field name for {@link #setReady(JSFunction)}
	 */
	public static final String READY = "ready";
	/**
	 * A field name for {@link #setPageReady(JSFunction)
	 */
	public static final String PAGEREADY = "pageReady";
	/**
	 * A field name for {@link #setDocument(JSObject)}
	 */
	public static final String DOCUMENT = "document";
	/**
	 * A field name for {@link #setInvalidOption(JSString)}
	 */
	public static final String INVALIDOPTION = "invalidOption";
	/**
	 * A field name for {@link #setOptionError(JSFunction)}
	 */
	public static final String OPTIONERROR = "optionError";
	/**
	 * A field name for {@link #setInput(JSArray)}
	 */
	public static final String INPUT = "input";
	/**
	 * A field name for {@link #setOutput(JSString)}
	 */
	public static final String OUTPUT = "output";
	/**
	 * A field name for {@link #setHandler(JSObject)}
	 */
	public static final String HANDLER = "handler";
	/**
	 * A field name for {@link #setAdaptor(JSObject)}
	 */
	public static final String ADAPTOR = "adaptor";

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 01:40:50--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #ELEMENTS}.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * This is either null or an array of DOM elements whose contents should be
	 * typeset. The elements can either be actual DOM elements, or strings that give
	 * CSS selectors for the elements to typeset.<br>
	 * <br>
	 * <i>The typical value is:</i>
	 * 
	 * <pre>
	 * <code>elements: null</code>
	 * </pre>
	 * 
	 * @param elements an array of string where each element is a HTML element
	 * @return {@code this} for further method chaining
	 */
	public StartupBuilder setElements(JSArray<JSString> elements) {
		fields.put(new JSMemberName(ELEMENTS, false), elements);
		return this;
	}

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 01:40:53--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #TYPESET}.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * This determines whether the initial typesetting action should be performed
	 * when the page is ready.<br>
	 * <br>
	 * <i>The typical value is:</i>
	 * 
	 * <pre>
	 * <code>typeset: true</code>
	 * </pre>
	 * 
	 * @param b true if MathJax should begin instant typesetting when the DOM loads
	 * @return {@code this} for further method chaining
	 */
	public StartupBuilder setTypesetter(JSBoolean b) {
		fields.put(new JSMemberName(TYPESET, false), b);
		return this;
	}

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 01:41:09--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #READY}.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * This is a function that is called when MathJax is loaded and ready to go. It
	 * is called by the loader when all the components are loaded. The default
	 * action is to create all the objects needed for MathJax, and set up the call
	 * to the {@code pageReady()} function below. You can override this function if
	 * you want to modify the setup process; see Performing Actions During Startup
	 * for more details. Note that this function may be called before the page is
	 * complete, so unless you are modifying the objects created by the startup
	 * module, replacing {@code pageReady()} may be the better choice.<br>
	 * <br>
	 * <i>The typical value is:</i>
	 * 
	 * <pre>
	 * <code>ready: MathJax.startup.defaultReady.bind(Startup)</code>
	 * </pre>
	 * 
	 * @param readyFunction the function to call when {@code MathJax.ready === true}
	 * @return {@code this} for further method chaining
	 */
	public StartupBuilder setReady(JSFunction readyFunction) {
		fields.put(new JSMemberName(READY, false), readyFunction);
		return this;
	}

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 01:41:07--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #PAGEREADY}.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * This is a function that is called when MathJax is ready to go and the page is
	 * ready to be processed. The default action is to perform the initial
	 * typesetting of the page and return the promise that resolves what that is
	 * complete, but you can override it to do whatever you would like, though you
	 * should return the promise from the {@code MathJax.startup.defaultPageReady()}
	 * function if you call it. See Performing Actions During Startup for more
	 * details and examples of how to do this.
	 * 
	 * <br>
	 * <br>
	 * <i>The typical value is:</i>
	 * 
	 * <pre>
	 * <code>pageReady: MathJax.startup.defaultPageReady.bind(Startup)</code>
	 * </pre>
	 * 
	 * @param readyFunction the function to call after the above ready function
	 * @return {@code this} for further method chaining
	 */
	public StartupBuilder setPageReady(JSFunction readyFunction) {
		fields.put(new JSMemberName(PAGEREADY, false), readyFunction);
		return this;
	}

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 01:41:05--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #DOCUMENT}.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * This is the document (or fragment or string of serialized HTML) that you want
	 * to process. By default (for in-browser use) it is the browser document. When
	 * there is no global document variable, it is an empty HTML document. <br>
	 * <br>
	 * <i>The typical value is:</i>
	 * 
	 * <pre>
	 * <code>document: document
	</code>
	 * </pre>
	 * 
	 * @param o the document object
	 * @return {@code this} for further method chaining
	 */
	public StartupBuilder setDocument(JSObject o) {
		fields.put(new JSMemberName(DOCUMENT, false), o);
		return this;
	}

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 01:41:02--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #INVALIDOPTION}.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * This determines whether an invalid option will cause a fatal error (when set
	 * to 'fatal') that stops MathJax from running, or a warning (when set to
	 * 'warn') that allows MathJax to go on. Prior to version 3.2, invalid options
	 * were fatal, but this option now allows control over that behavior.
	 * 
	 * 
	 * <br>
	 * <br>
	 * <i>The typical value is:</i>
	 * 
	 * <pre>
	 * <code>invalidOption: 'warn' // or 'fatal'
	</code>
	 * </pre>
	 * 
	 * @param s the severity of errors
	 * @return {@code this} for further method chaining
	 */
	public StartupBuilder setInvalidOption(JSString s) {
		fields.put(new JSMemberName(INVALIDOPTION, false), s);
		return this;
	}

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 01:40:57--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #OPTIONERROR}.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * This option gives a function that is called whenever there is an invalid
	 * option provided by the user. It takes two string arguments, the first being
	 * the message, and the second being the name of the invalid option. The default
	 * function looks at the invalidOption value and if it is 'fatal' it throws an
	 * error using the given message, otherwise it logs the message to the browser
	 * console, allowing futher options to be processed. <br>
	 * <br>
	 * <i>The typical value is:</i>
	 * 
	 * <pre>
	 * <code>optionError: OPTIONS.optionError
	</code>
	 * </pre>
	 * 
	 * @param optionErrorFunction
	 * @return {@code this} for further method chaining
	 */
	public StartupBuilder setOptionError(JSFunction optionErrorFunction) {
		fields.put(new JSMemberName(TYPESET, false), optionErrorFunction);
		return this;
	}

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 01:49:03--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #INPUT}.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * This is an array of names of input processors that you want to use, from
	 * among the ones that have been loaded. So if you have loaded the code for
	 * several input jax, but only want to use the tex input jax, for example, set
	 * this to ['tex']. If set to an empty array, then all loaded input jax are
	 * used. <br>
	 * <br>
	 * <i>The typical value is:</i>
	 * 
	 * <pre>
	 * <code>input: []
	</code>
	 * </pre>
	 * 
	 * @param inputNames an array of all the input processor
	 * @return {@code this} for further method chaining
	 */
	public StartupBuilder setInput(JSArray<JSString> inputNames) {
		fields.put(new JSMemberName(INPUT, false), inputNames);
		return this;
	}

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 01:49:01--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #OUTPUT}.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * This is the name of the output processor that you want to use, from among the
	 * ones that have been loaded. So if you have loaded the code for several output
	 * jax, but only want to use the svg output jax, for example, set this to 'svg'.
	 * If set to null or an empty string, then the first output jax that is loaded
	 * will be used. <br>
	 * <br>
	 * <i>The typical value is:</i>
	 * 
	 * <pre>
	 * <code>output: null
	</code>
	 * </pre>
	 * 
	 * @param output a string representing the OutputJax to use as described above
	 * @return {@code this} for further method chaining
	 */
	public StartupBuilder setOutput(JSString output) {
		fields.put(new JSMemberName(OUTPUT, false), output);
		return this;
	}

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 01:48:58--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #HANDLER}.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * This is the name of the document handler that you want to use, from among the
	 * ones that have been loaded. Currently, there is only one handler, the HTML
	 * handler, so unless you are creating your own handlers, leave this as
	 * null.<br>
	 * <br>
	 * <i>The typical value is:</i>
	 * 
	 * <pre>
	 * <code>handler: null
	</code>
	 * </pre>
	 * 
	 * @param o an object that can serve as the handler for your math
	 * @return {@code this} for further method chaining
	 */
	public StartupBuilder setHandler(JSObject o) {
		fields.put(new JSMemberName(HANDLER, false), o);
		return this;
	}

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 01:48:56--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #ADAPTOR}.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * This is the name of the DOM adaptor that you want to use, from among the ones
	 * that have been loaded. By default the components load the browser adaptor,
	 * but you can load the liteDOM adaptor for use in node applications; if you do,
	 * it will set this value so that it will be used automatically.<br>
	 * <br>
	 * <i>The typical value is:</i>
	 * 
	 * <pre>
	 * <code>adaptor: null
	</code>
	 * </pre>
	 * 
	 * @param o the name of the object 
	 * @return {@code this} for further method chaining
	 */
	public StartupBuilder setAdaptor(JSObject o) {
		fields.put(new JSMemberName(ADAPTOR, false), o);
		return this;
	}
}
