/**
 * 
 */
package mathaid.calculator.base.gui.mathjax;

import java.io.IOException;

import mathaid.js.JSMemberName;

/*
 * Date: 24 Aug 2022----------------------------------------------------------- 
 * Time created: 08:17:49---------------------------------------------------  
 * Package: js.mathjax------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: MathJaxSetup.java------------------------------------------------------ 
 * Class name: MathJaxSetup------------------------------------------------ 
 */
/**
 * Represents an instance of the MathJax global object.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class MathJaxSetup {
	/**
	 * The field name for the MathJax global object
	 */
	public static final String MATHJAX = "MathJax";

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 08:36:10---------------------------------------------------
	 * Package: js.mathjax------------------------------------------------ Project:
	 * TestCode------------------------------------------------ File:
	 * MathJaxSetup.java------------------------------------------------------ Class
	 * name: ConfigBuilder------------------------------------------------
	 */
	/**
	 * The top-most configuration object that is the literal value of the MathJax
	 * global object.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 */
	public static class ConfigBuilder extends AbstractOptionsBuilder {
		/**
		 * The field name for
		 * {@link #setAsciiMathProcessor(AsciiMathInputProcessorBuilder)}
		 */
		public static final String ASCIIMATH = "asciimath";
		/**
		 * The field name for {@link #setCHTMLProcessor(CHTMLOutputProcessorBuilder)}
		 */
		public static final String CHTML = "chtml";
		/**
		 * The field name for {@link #setLoader(LoaderBuilder)}
		 */
		public static final String LOADER = "loader";
		/**
		 * The field name for {@link #setMMLProcessor(MmlInputProcessorBuilder)}
		 */
		public static final String MML = "mml";
		/**
		 * The field name for {@link #setOptions(OptionsBuilder)}
		 */
		public static final String OPTIONS = "options";
		/**
		 * The field name for {@link #setStartup(StartupBuilder)}
		 */
		public static final String STARTUP = "startup";
		/**
		 * The field name for {@link #setSVGProcessor(SVGOutputProcessorBuilder)}
		 */
		public static final String SVG = "svg";
		/**
		 * The field name for {@link #setTexProcessor(TexInputProcessorBuilder)}
		 */
		public static final String TEX = "tex";

		/*
		 * Date: 26 Aug 2022-----------------------------------------------------------
		 * Time created: 08:39:59--------------------------------------------
		 */
		/**
		 * Builds the argument and assigns the result to {@value #ASCIIMATH}.
		 * <p>
		 * This sets all the {@value #ASCIIMATH} options for the MathJax global object,
		 * as specified by the {@code AsciiMathInputProcessorBuilder} argument.
		 * 
		 * @param a the builder with relevant pre-configured options
		 * @return {@code this} for further method chaining
		 */
		public ConfigBuilder setAsciiMathProcessor(AsciiMathInputProcessorBuilder a) {
			fields.put(new JSMemberName(ASCIIMATH, false), a.build());
			return this;
		}

		/*
		 * Date: 26 Aug 2022-----------------------------------------------------------
		 * Time created: 08:40:59--------------------------------------------
		 */
		/**
		 * Builds the argument and assigns the result to {@value #CHTML}.
		 * <p>
		 * This sets all the {@value #CHTML} options for the MathJax global object, as
		 * specified by the {@code CHTMLOutputProcessorBuilder} argument.
		 * 
		 * @param chtml the builder with relevant pre-configured options
		 * @return {@code this} for further method chaining
		 */
		public ConfigBuilder setCHTMLProcessor(CHTMLOutputProcessorBuilder chtml) {
			fields.put(new JSMemberName(CHTML, false), chtml.build());
			return this;
		}

		/*
		 * Date: 26 Aug 2022-----------------------------------------------------------
		 * Time created: 08:40:56--------------------------------------------
		 */
		/**
		 * Builds the argument and assigns the result to {@value #LOADER}.
		 * <p>
		 * This sets all the {@value #LOADER} options for the MathJax global object, as
		 * specified by the {@code LoaderBuilder} argument.
		 * 
		 * @param loader the builder with relevant pre-configured options
		 * @return {@code this} for further method chaining
		 */
		public ConfigBuilder setLoader(LoaderBuilder loader) {
			fields.put(new JSMemberName(LOADER, false), loader.build());
			return this;
		}

		/*
		 * Date: 26 Aug 2022-----------------------------------------------------------
		 * Time created: 08:40:53--------------------------------------------
		 */
		/**
		 * Builds the argument and assigns the result to {@value #MML}.
		 * <p>
		 * This sets all the {@value #MML} options for the MathJax global object, as
		 * specified by the {@code MmlInputProcessorBuilder} argument.
		 * 
		 * @param mml the builder with relevant pre-configured options
		 * @return {@code this} for further method chaining
		 */
		public ConfigBuilder setMMLProcessor(MmlInputProcessorBuilder mml) {
			fields.put(new JSMemberName(MML, false), mml.build());
			return this;
		}

		/*
		 * Date: 26 Aug 2022-----------------------------------------------------------
		 * Time created: 08:40:47--------------------------------------------
		 */
		/**
		 * Builds the argument and assigns the result to {@value #OPTIONS}.
		 * <p>
		 * This sets all the {@value #OPTIONS} options for the MathJax global object, as
		 * specified by the {@code OptionsBuilder} argument.
		 * 
		 * @param options the builder with relevant pre-configured options
		 * @return {@code this} for further method chaining
		 */
		public ConfigBuilder setOptions(OptionsBuilder options) {
			fields.put(new JSMemberName(OPTIONS, false), options.build());
			return this;
		}

		/*
		 * Date: 26 Aug 2022-----------------------------------------------------------
		 * Time created: 08:40:44--------------------------------------------
		 */
		/**
		 * Builds the argument and assigns the result to {@value #STARTUP}.
		 * <p>
		 * This sets all the {@value #STARTUP} options for the MathJax global object, as
		 * specified by the {@code StartupBuilder} argument.
		 * 
		 * @param startup the builder with relevant pre-configured options
		 * @return {@code this} for further method chaining
		 */
		public ConfigBuilder setStartup(StartupBuilder startup) {
			fields.put(new JSMemberName(STARTUP, false), startup.build());
			return this;
		}

		/*
		 * Date: 26 Aug 2022-----------------------------------------------------------
		 * Time created: 08:40:09--------------------------------------------
		 */
		/**
		 * Builds the argument and assigns the result to {@value #SVG}.
		 * <p>
		 * This sets all the {@value #SVG} options for the MathJax global object, as
		 * specified by the {@code SVGOutputProcessorBuilder} argument.
		 * 
		 * @param svg the builder with relevant pre-configured options
		 * @return {@code this} for further method chaining
		 */
		public ConfigBuilder setSVGProcessor(SVGOutputProcessorBuilder svg) {
			fields.put(new JSMemberName(SVG, false), svg.build());
			return this;
		}

		/*
		 * Date: 26 Aug 2022-----------------------------------------------------------
		 * Time created: 08:40:07--------------------------------------------
		 */
		/**
		 * Builds the argument and assigns the result to {@value #TEX}.
		 * <p>
		 * This sets all the {@value #TEX} options for the MathJax global object, as
		 * specified by the {@code TexInputProcessorBuilder} argument.
		 * 
		 * @param tex the builder with relevant pre-configured options
		 * @return {@code this} for further method chaining
		 */
		public ConfigBuilder setTexProcessor(TexInputProcessorBuilder tex) {
			fields.put(new JSMemberName(TEX, false), tex.build());
			return this;
		}
	}

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 08:40:05--------------------------------------------
	 */
	/**
	 * Serializes the {@code config} parameter into a string and appends the result
	 * to the given {@code Appendable}. The serialised string is in the form:
	 * 
	 * <pre>
	 * <code>MathJax = { ... }</code>
	 * </pre>
	 * 
	 * where the ellipsis is the result of the argument.
	 * <p>
	 * The results can be inserted directly in to a <code>&lt;script&gt;</code>
	 * element in a DOM such as an HTML document, just before the script element
	 * that declares MathJax location de.
	 * 
	 * @param config the builder with relevant pre-configured options
	 * @param a      an {@code Appendable} such as a {@code StringBuilder}
	 * @return the {@code Appendabe} argument after the appendage has taken place.
	 */
	public Appendable configure(ConfigBuilder config, Appendable a) {
		try {
			a.append(String.format("%s = ", MATHJAX));
			config.build().parseToScript(a, 0);
		} catch (IOException e) {
		}
		return a;
	}

}
