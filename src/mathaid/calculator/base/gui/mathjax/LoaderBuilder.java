/**
 * 
 */
package mathaid.calculator.base.gui.mathjax;

import mathaid.js.JSArray;
import mathaid.js.JSDictionary;
import mathaid.js.JSFunction;
import mathaid.js.JSMemberName;
import mathaid.js.JSString;

/*
 * Date: 24 Aug 2022----------------------------------------------------------- 
 * Time created: 03:02:37---------------------------------------------------  
 * Package: js------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: LoaderBuilder.java------------------------------------------------------ 
 * Class name: LoaderBuilder------------------------------------------------ 
 */
/**
 * A Builder class that configures and builds a valid object for the
 * {@code loader} field within the global MathJax Object.
 * <p>
 * There is no check ready for this builder class.
 * <p>
 * <b>Copied from the MathJax documentation:</b>
 * <p>
 * The loader component is the one responsible for loading the requested MathJax
 * components. It is configured using the loader block in your MathJax
 * configuration object.
 * <p>
 * In the example below, Loader represents the MathJax.loader object, for
 * brevity.
 * 
 * <pre>
 * <code>
MathJax = {
  loader: {
    load: [],                                    // array of components to load
    ready: Loader.defaultReady.bind(Loader),     // function to call when everything is loaded
    failed: function (error) {                   // function to call if a component fails to load
      console.log(`MathJax(${error.package || '?'}): ${error.message}`);
    },
    paths: {mathjax: Loader.getRoot()},          // the path prefixes for use in specifying components
    source: {},                                  // the URLs for components, when defaults aren't right
    dependencies: {},                            // arrays of dependencies for each component
    provides: {},                                // components provided by each component
    require: null,                               // function to use for loading components
    pathFlters: []                               // functions to use to process package names
  }
};</code>
 * </pre>
 * <p>
 * In addition to the option methods defined in this class, individual
 * components can be configured in the loader block by using a sub-block with
 * the component’s name, and any of the options listed below. For example,
 * 
 * <pre>
 * <code>
MathJax = {
  loader: {
    load: ['input/tex'],
    'input/tex': {
      ready: (name) => console.log(name + ' ready'),
      failed: (error) => console.log(error.package + ' failed')
    }
  }
};</code>
 * </pre>
 * 
 * which sets up {@code ready()} and {@code failed()} functions to process when
 * the input/tex component is either loaded successfully or fails to load.
 * <ul>
 * <li><b><code>ready: undefined</code></b> - This is a function that has an
 * argument that is the name of the component being loaded, and is called when
 * the component and all its dependencies are fully loaded.</li>
 * <li><b><code>failed: undefined</code></b> - This is a function that has an
 * argument that is a PackageError object (which is a subclass of Error with an
 * extra field, that being pacakge, the name of the component being loaded). It
 * is called when the component fails to load (and that can be because one of
 * its dependencies fails to load).</li>
 * <li><b><code>checkReady: undefined</code></b> - This is a function that takes
 * no argument and is called when the component is loaded, but before the
 * ready() function is called. It can be used o do post-processing after the
 * component is loaded, but before other components are signaled that it is
 * ready. For example, it could be used to load other components; e.g., the
 * output/chtml component can use its configuration to determine which font to
 * load, and then load that. If this function returns a promise object, the
 * ready() function will not be called until the promise is resolved.</li>
 * </ul>
 * 
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class LoaderBuilder extends AbstractOptionsBuilder {
	/**
	 * The field name for {@link #setLoad(JSArray)}
	 */
	public static final String LOAD = "load";
	/**
	 * The field name for {@link #setReady(JSFunction)}
	 */
	public static final String READY = "ready";
	/**
	 * The field name for {@link #setFailed(JSFunction)}
	 */
	public static final String FAILED = "failed";
	/**
	 * The field name for {@link #setPaths(JSDictionary)}
	 */
	public static final String PATHS = "paths";
	/**
	 * The field name for {@link #setSource(JSDictionary)}
	 */
	public static final String SOURCE = "source";
	/**
	 * The field name for {@link #setDependencies(JSDictionary)}
	 */
	public static final String DEPENDENCIES = "dependencies";
	/**
	 * The field name for {@link #setProviders(JSDictionary)}
	 */
	public static final String PROVIDES = "provides";
	/**
	 * The field name for {@link #setRequiredPackages(JSFunction)}
	 */
	public static final String REQUIRES = "require";
	/**
	 * The field name for {@link #setPathFilters(JSFunction)}
	 */
	public static final String PATHFILTERS = "pathFilters";

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 23:35:12--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #LOAD}.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * This array lists the components that you want to load. If you are using a
	 * combined component file, you may not need to request any additional
	 * components. If you are using using the startup component explicitly, then you
	 * will need to list all the components you want to load.<br>
	 * <br>
	 * <i>The typical value is:</i>
	 * 
	 * <pre>
	 * <code>load: []</code>
	 * </pre>
	 * 
	 * @param componentList a list of packages and components name
	 * @return {@code this} for further method chaining
	 */
	public LoaderBuilder setLoad(JSArray<JSString> componentList) {
		fields.put(new JSMemberName(LOAD, false), componentList);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 23:35:17--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #READY}.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * This is a function that is called when all the components have been loaded
	 * successfully. By default, it simply calls the startup component’s ready()
	 * function, if there is one. You can override this with your own function, can
	 * can call MathJax.loader.defaultReady() after doing whatever startup you need
	 * to do.<br>
	 * <br>
	 * <i>The typical value is:</i>
	 * 
	 * <pre>
	 * <code>ready: MathJax.loader.defaultReady.bind(MathJax.loader)</code>
	 * </pre>
	 * 
	 * @param readyFunction the function to call when MathJax is ready to typeset
	 *                      math
	 * @return {@code this} for further method chaining
	 */
	public LoaderBuilder setReady(JSFunction readyFunction) {
		fields.put(new JSMemberName(READY, false), readyFunction);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 23:35:22--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #FAILED}.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * This is a function that is called if one or more of the components fails to
	 * load properly. The default is to print a message to the console log, but you
	 * can override it to trap loading errors in MathJax components.<br>
	 * <br>
	 * <i>The typical value is:</i>
	 * 
	 * <pre>
	 * <code>failed: (error) => console.log(`MathJax(${error.package || '?'}): ${error.message}`)}</code>
	 * </pre>
	 * 
	 * @param failedFunction the function to call when a typesetting has failed
	 * @return {@code this} for further method chaining
	 */
	public LoaderBuilder setFailed(JSFunction failedFunction) {
		fields.put(new JSMemberName(FAILED, false), failedFunction);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 23:35:29--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #PATHS}.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * This object links path prefixes to their actual locations. By default, the
	 * mathjax prefix is predefined to be the location from which the MathJax file
	 * is being loaded. You can use {@code [mathjax]/...} to identify a component,
	 * and this prefix is prepended automatically for any that doesn’t already have
	 * a prefix. For example, {@code input/tex} will become
	 * {@code [mathjax]/input/jax} automatically.
	 * 
	 * When the TeX require extension is loaded, an additional tex path is created
	 * in order to be able to load the various TeX extensions.
	 * 
	 * You can define your own prefixes, for example,
	 * 
	 * <pre>
	 * <code>
	MathJax = {
	  loader: {
	    paths: {custom: 'https://my.site.com/mathjax'},
	    load: ['[custom]/myComponent']
	  }
	};</code>
	 * </pre>
	 * 
	 * which defines a custom prefix that you can used to access custom extensions.
	 * The URL can even be to a different server than where you loaded the main
	 * MathJax code, so you can host your own custom extensions and still use a CDN
	 * for the main MathJax code.
	 * 
	 * You can define as many different paths as you need. Note that paths can refer
	 * to other paths, so you could do
	 * 
	 * <pre>
	 * <code>
	MathJax = {
	  loader: {
	    paths: {
	      custom: 'https://my.site.com/mathjax',
	      extensions: '[custom]/extensions'
	    },
	    load: ['[extensions]/myExtension']
	  }
	};</code>
	 * </pre>
	 * 
	 * to define the extensions prefix in terms of the custom prefix.<br>
	 * <br>
	 * <i>The typical value is:</i>
	 * 
	 * <pre>
	 * <code>paths: {mathjax: Loader.getRoot()}</code>
	 * </pre>
	 * 
	 * @param pairs <i>key/value</i> pairs that specify an identifier for locations
	 *              of files to be used by MathJax
	 * @return {@code this} for further method chaining
	 */
	public LoaderBuilder setPaths(JSDictionary<JSString, JSString> pairs) {
		fields.put(new JSMemberName(PATHS, false), MathJaxOptionsBuilder.convertDictionaryToObject(pairs));
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 23:35:33--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #SOURCE}.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * This object allows you to override the default locations of components and
	 * provide a specific location on a component-by-component basis. For example:
	 * 
	 * <pre>
	 * <code>
	MathJax = {
	  loader: {
	    source: {
	      'special/extension': 'https://my.site.com/mathjax/special/extension.js'
	    },
	    load: ['special/extension']
	  }
	};</code>
	 * </pre>
	 * 
	 * gives an explicit location to obtain the {@code special/extension}
	 * component.<br>
	 * <br>
	 * <i>The typical value is:</i>
	 * 
	 * <pre>
	 * <code>source: {}</code>
	 * </pre>
	 * 
	 * @param pairs <i>key/value</i> pairs that specify an identifier for locations
	 *              of source files to be used by MathJax
	 * @return {@code this} for further method chaining
	 */
	public LoaderBuilder setSource(JSDictionary<JSString, JSString> pairs) {
		fields.put(new JSMemberName(SOURCE, false), MathJaxOptionsBuilder.convertDictionaryToObject(pairs));
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 23:35:36--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #DEPENDENCIES}.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * This object maps component names to arrays of names of components that must
	 * be loaded before the given one. The startup component pre-populates this
	 * object with the dependencies among the MathJax components, but you can add
	 * your own dependencies if you make custom components that rely on others. For
	 * example, if you make a custom TeX extension that relies on another TeX
	 * component, you would want to indicate that dependency so that if your
	 * extension is loaded via \require, for example, the loader will automatically
	 * load the dependencies first.
	 * 
	 * <pre>
	 * <code>
	MathJax = {
	  loader: {
	    source: {
	      '[tex]/myExtension: 'https://my.site.com/mathjax/tex/myExtension.js'},
	    },
	    dependencies: {
	      '[tex]/myExtension': ['input/tex-base', '[tex]/newcommand', '[tex]/enclose']
	    }
	  }
	};</code>
	 * </pre>
	 * 
	 * This would cause the {@code newcommand} and enclose components to be loaded
	 * prior to loading your extension, and would load your extension from the given
	 * URL even though you may be getting MathJax from a CDN.<br>
	 * <br>
	 * <i>The typical value is:</i>
	 * 
	 * <pre>
	 * <code>dependencies: {}</code>
	 * </pre>
	 * 
	 * @param pairs <i>key/value</i> pairs that specify an identifier for a list of
	 *              source files used by the source block
	 * @return {@code this} for further method chaining
	 */
	public LoaderBuilder setDependencies(JSDictionary<JSString, JSArray<JSString>> pairs) {
		fields.put(new JSMemberName(DEPENDENCIES, false), MathJaxOptionsBuilder.convertDictionaryToObject(pairs));
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 23:35:39--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #PROVIDES}.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * This object indicates the components that are provided by a component that
	 * may include several sub-components. For example, the input/tex component
	 * loads the newcommand component (and several others), so the provides object
	 * indicates that via
	 * 
	 * <pre>
	 * <code>
	loader: {
	  provides: {
	    'input/tex': [
	      'input/tex-base',
	      '[tex]/ams',
	      '[tex]/newcommand',
	      '[tex]/noundefined',
	      '[tex]/require',
	      '[tex]/autoload',
	      '[tex]/configmacros'
	    ]
	  }
	}</code>
	 * </pre>
	 * 
	 * The startup component pre-populates this object with the dependencies among
	 * the MathJax components, but if you define your own custom components that
	 * include other components, you may need to declare the components that it
	 * provides, so that if another component has one of them as a dependency, that
	 * dependency will not be loaded again (since your code already includes it).
	 * <p>
	 * For example, if your custom component [tex]/myExtension depends on the
	 * newcommand and enclose components, then
	 * 
	 * <pre>
	 * <code>
	MathJax = {
	  loader: {
	    source: {
	      '[tex]/myExtension: 'https://my.site.com/mathjax/tex/myExtension.js'},
	    },
	    dependencies: {
	      '[tex]/myExtension': ['input/tex-base', '[tex]/newcommand', '[tex]/enclose']
	    },
	    load: ['input/tex', '[tex]/myExtension']
	  }
	};</code>
	 * </pre>
	 * 
	 * will load the {@code input/tex} component, which provides both
	 * {@code input/tex-base} and {@code [tex]/newcommand}, and then load
	 * {@code [tex]/enclose} before loading your {@code [tex]/myExtension}.<br>
	 * <br>
	 * <i>The typical value is:</i>
	 * 
	 * <pre>
	 * <code>provides: {}</code>
	 * </pre>
	 * 
	 * @param pairs <i>key/value</i> pairs that specify an identifier for a list of
	 *              sources used by internal loaders
	 * @return {@code this} for further method chaining
	 */
	public LoaderBuilder setProviders(JSDictionary<JSString, JSArray<JSString>> pairs) {
		fields.put(new JSMemberName(PROVIDES, false), MathJaxOptionsBuilder.convertDictionaryToObject(pairs));
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 23:35:42--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #REQUIRES}.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * This is a function to use for loading components. It should accept a string
	 * that is the location of the component to load, and should do whatever is
	 * needed to load that component. If the loading is asynchronous, it should
	 * return a promise that is resolved when the component is loaded, or therwise it
	 * should return nothing. If there is an error loading the component, it should
	 * throw an error. <br>
	 * If set null, the default is to insert a &lt;script&gt; tag into the document that
	 * loads the component.
	 * <p>
	 * For use in node applications, set this value to require, which will use
	 * node’s require command to load components. E.g.
	 * 
	 * <pre>
	 * <code>
	MathJax = {
	  loader: {
	    require: require
	  }
	};</code>
	 * </pre>
	 * 
	 * <br>
	 * <br>
	 * <i>The typical value is:</i>
	 * 
	 * <pre>
	 * <code>require: null</code>
	 * </pre>
	 * 
	 * @param f a function
	 * @return {@code this} for further method chaining
	 */
	public LoaderBuilder setRequiredPackages(JSFunction f) {
		fields.put(new JSMemberName(REQUIRES, false), f);
		return this;
	}

	/*
	 * Date: 25 Aug 2022-----------------------------------------------------------
	 * Time created: 23:35:47--------------------------------------------
	 */
	/**
	 * Sets the field for {@value #PATHFILTERS}.
	 * <p>
	 * <b>Copied from the mathjax documentation:</b>
	 * <p>
	 * This is an array of functions that are used to process the names of
	 * components to produce the actual URL used to locate the component. There are
	 * built-in filters that perform actions like converting the prefix [tex] to the
	 * path for the TeX extensions, and adding .js to the end of the name, and so
	 * on. You can provide your own filters if you need to manage the URLs in a
	 * different way. The array consists of entries that are either functions that
	 * take a data object as an argument, or an array consisting of such a function
	 * and a number representing its priority in the list of filters (lower numbers
	 * are earlier in the list). The data object that is passed to these functions
	 * is
	 * 
	 * <pre>
	 * <code>
	{
	  name: string,            // the current name for the package (this becomes the url in the end)
	  original: string,        // the original package name (should not be modified)
	  addExtension: boolean,   // true if .js should be added to this name at some stage in the filter list
	}</code>
	 * </pre>
	 * 
	 * The filter can change the name value to move it closer to the final URL used
	 * for loading the given package. The original property should be the original
	 * name of the package, and should not be modified.
	 * <p>
	 * The function should return true if the name should be futher processed by
	 * other filters in the list, and false to end processing with the name now
	 * representing the final URL for the component.
	 * <p>
	 * There are three default filters: one that replaces name with its value in the
	 * source list, if any; one that normalizes package names by adding [mathjax]/
	 * if there is no prefix or protocol already, and adding .js if there is no
	 * extension; and one that replaced prefixes with their values in the paths
	 * list. These have priorities 0, 10, and 20, respectively, and you can use
	 * priorities (including negative ones) with your own functions to insert them
	 * into this list in any location. <br>
	 * <br>
	 * <i>The typical value is:</i>
	 * 
	 * <pre>
	 * <code>pathFilters: []</code>
	 * </pre>
	 * 
	 * @param f an array of functions
	 * @return {@code this} for further method chaining
	 */
	public LoaderBuilder setPathFilters(JSArray<JSFunction> f) {
		fields.put(new JSMemberName(PATHFILTERS, false), f);
		return this;
	}
}
