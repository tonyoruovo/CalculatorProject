/**
 * An API for creating and configuring the MATHJAX (Mathematical JavaScript and
 * XML) global object. All objects, options and configurations are based on
 * MathJax 3.2.2 API.
 * <p>
 * A simple configuration can be built using:
 * <pre>
 * <code>
 * StartupBuilder stb = new StartupBuilder();
 * stb.setInput(new JSArray<>(List.of(new JSString("tex"))));
 * stb.setOutput(new JSString("svg"));
 * stb.setTypesetter(new JSBoolean(true));
 * stb.setElements(new JSArray<>(List.of(new JSString(".math"))));
 * 
 * LoaderBuilder loader = new LoaderBuilder();
 * loader.setLoad(new JSArray<>(
 * 		List.of(new JSString("[tex]/ams"), new JSString("[tex]/cancel"), new JSString("[tex]/unicode"),
 * 				new JSString("[tex]/html"), new JSString("[tex]/bbox"), new JSString("[tex]/colorv2"))));
 * 
 * TexInputProcessorBuilder tex = new TexInputProcessorBuilder();
 * tex.setPackages(new JSArray<>(List.of(new JSString("ams"), new JSString("cancel"), new JSString("unicode"),
 * 		new JSString("html"), new JSString("bbox"), new JSString("color"))));
 * tex.setDelimitersForInlineMath(
 * 		new JSArray<>(List.of(new JSDictionary<>(Map.of(new JSString("$$"), new JSString("$$"))))));
 * tex.setDelimitersForDisplayMath(
 * 		new JSArray<>(List.of(new JSDictionary<>(Map.of(new JSString("\\\\["), new JSString("\\\\]"))))));
 * 
 * ConfigBuilder config = new ConfigBuilder();
 * config.setTexProcessor(tex);
 * config.setLoader(loader);
 * config.setStartup(stb);
 * MathJaxSetup mjs = new MathJaxSetup();
 * mjs.configure(config, System.out);
 * </code>
 * </pre>
 *  Will give the output.
 *  <pre><code>
 *  MathJax = {
 *	 	tex : {
 *	 		packages : ["ams", "cancel", "unicode", "html", "bbox", "color"],
 *	 		inlineMath : [{
 *	 			"$$": "$$"
 *	 		}],
 *	 		displayMath : [{
 *	 			"\\[": "\\]"
 *	 		}]
 *	 	},
 *	 	loader : {
 *	 		load : ["[tex]/ams", "[tex]/cancel", "[tex]/unicode", "[tex]/html", "[tex]/bbox", "[tex]/colorv2"]
 *	 	},
 *	 	startup : {
 *	 		input : ["tex"],
 *	 		output : "svg",
 *	 		typeset : true,
 *	 		elements : [".math"]
 *	 	}
 * 	}
 *</code></pre>
 *Which can then be used to create the following:
 *<pre><code>
 *&lt;!doctype html&gt;
 *	&lt;html&gt; 
 *	 &lt;head&gt; 
 *	  &lt;script id="MathJax-script" src="./npm/mathjax/es5/tex-svg-full.js"&gt;&lt;/script&gt; 
 *	  &lt;script&gt;
 *			MathJax = {
 *				tex: {
 *					packages: ["ams", "cancel", "unicode", "html", "bbox", "color"],
 *					inlineMath: [{
 *						"$$": "$$"
 *					}],
 *					displayMath: [{
 *						"\\[": "\\]"
 *					}]
 *				},
 *				loader: {
 *					load: ["[tex]/ams", "[tex]/cancel", "[tex]/unicode", "[tex]/html", "[tex]/bbox", "[tex]/colorv2"]
 *				},
 *				startup: {
 *					input: ["tex"],
 *					output: "svg",
 *					typeset: true,
 *					elements: [".math"]
 *				}
 *			}
 *	
 *		&lt;/script&gt; 
 *	  &lt;style&gt;
 *			#caret use[data-c="7C"] {
 *				/*
 *				If CARET METHOD_INPUT == APPEND THEN SELECTOR = #caret use[data-c="230B"]
 *				If CARET METHOD_INPUT == PREPEND THEN SELECTOR = #caret use[data-c="230A"]
 *				If CARET METHOD_INPUT == OVERWRITE THEN SELECTOR = #caret rect
 *				If CARET METHOD_INPUT == INSERT THEN SELECTOR = #caret use[data-c="7C"]
 *				*&sol;
 *				animation: caret 250ms linear 100ms alternate-reverse infinite;
 *			}
 *	
 *			&commat;keyframes caret {
 *				0% {
 *					opacity: 1;
 *				}
 *	
 *				20% {
 *					opacity: .8;
 *				}
 *	
 *				40% {
 *					opacity: .6;
 *				}
 *	
 *				60% {
 *					opacity: .4;
 *				}
 *	
 *				80% {
 *					opacity: .2;
 *				}
 *	
 *				100% {
 *					opacity: 0;
 *				}
 *			}
 *		&lt;/style&gt; 
 *	 &lt;/head&gt; 
 *	 &lt;body&gt; 
 *	  &lt;div class="math"&gt;
 *	   \[ 123.947\,5\overline{10\,57}\cssId{caret}{\left|\right.} \]
 *	  &lt;/div&gt;  
 *	 &lt;/body&gt;
 *	&lt;/html&gt;
 *</code></pre>
 */
package mathaid.calculator.base.gui.mathjax;
