/**
 * 
 */
package mathaid.js;

import java.util.List;
import java.util.Map;

import mathaid.calculator.base.gui.mathjax.LoaderBuilder;
import mathaid.calculator.base.gui.mathjax.MathJaxSetup;
import mathaid.calculator.base.gui.mathjax.MathJaxSetup.ConfigBuilder;
import mathaid.calculator.base.gui.mathjax.StartupBuilder;
import mathaid.calculator.base.gui.mathjax.TexInputProcessorBuilder;

/*
 * Date: 20 Aug 2022----------------------------------------------------------- 
 * Time created: 08:42:47---------------------------------------------------  
 * Package: js------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: JSMain.java------------------------------------------------------ 
 * Class name: JSMain------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class JSMain {

	public static void main(String... args) {

		StartupBuilder stb = new StartupBuilder();
		stb.setInput(new JSArray<>(List.of(new JSString("tex"))));
		stb.setOutput(new JSString("svg"));
		stb.setTypesetter(new JSBoolean(true));
		stb.setElements(new JSArray<>(List.of(new JSString(".math"))));

		LoaderBuilder loader = new LoaderBuilder();
		loader.setLoad(new JSArray<>(
				List.of(new JSString("[tex]/ams"), new JSString("[tex]/cancel"), new JSString("[tex]/unicode"),
						new JSString("[tex]/html"), new JSString("[tex]/bbox"), new JSString("[tex]/colorv2"))));

		TexInputProcessorBuilder tex = new TexInputProcessorBuilder();
		tex.setPackages(new JSArray<>(List.of(new JSString("ams"), new JSString("cancel"), new JSString("unicode"),
				new JSString("html"), new JSString("bbox"), new JSString("color"))));
		tex.setDelimitersForInlineMath(
				new JSArray<>(List.of(new JSDictionary<>(Map.of(new JSString("$$"), new JSString("$$"))))));
		tex.setDelimitersForDisplayMath(
				new JSArray<>(List.of(new JSDictionary<>(Map.of(new JSString("\\\\["), new JSString("\\\\]"))))));

		ConfigBuilder config = new ConfigBuilder();
		config.setTexProcessor(tex);
		config.setLoader(loader);
		config.setStartup(stb);
		MathJaxSetup mjs = new MathJaxSetup();
		mjs.configure(config, System.out);
	}

}
