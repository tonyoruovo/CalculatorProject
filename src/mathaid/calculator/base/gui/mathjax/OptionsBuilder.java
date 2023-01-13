/**
 * 
 */
package mathaid.calculator.base.gui.mathjax;

import mathaid.js.JSBoolean;
import mathaid.js.JSMemberName;
import mathaid.js.JSObject;

/*
 * Date: 24 Aug 2022----------------------------------------------------------- 
 * Time created: 06:37:21---------------------------------------------------  
 * Package: js.mathjax.options------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: OptionsBuilder.java------------------------------------------------------ 
 * Class name: OptionsBuilder------------------------------------------------ 
 */
/**
 * A builder that configures fields under {@code options} and builds a valid
 * object representing it.
 * <p>
 * During the call to {@link #build()}, all menu-related extensions are disabled
 * in configuration and the returned object will not enable any of those.
 * <p>
 * Note that 'Safe Extensions Options' is not currently supported.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class OptionsBuilder extends AbstractOptionsBuilder {

	/*
	 * Date: 24 Aug 2022-----------------------------------------------------------
	 * Time created: 07:25:12--------------------------------------------
	 */
	/**
	 * Sets all the options of the specified in the 'Document Options' section of
	 * the MathJax documentation using a give {@code DocumentOptionsBuilder}.
	 * 
	 * @param dob a fully configured {@code DocumentOptionsBuilder} object.
	 * @return {@code this} for further method chaining
	 */
	public OptionsBuilder setDocumentOptions(DocumentOptionsBuilder dob) {
		fields.putAll(dob.build().getMembers());
		return this;
	}

	/*
	 * Most Recent Date: 24 Aug 2022-----------------------------------------------
	 * Most recent time created: 07:04:03--------------------------------------
	 */
	/**
	 * Disables all the accessibility extension and the Contextual menu options,
	 * then builds a suitable options object from all the parameters specified by
	 * users of this {@code OptionsBuilder} object before {@code build()} was
	 * called.
	 * 
	 * @return a {@code JSObject} which will contain configuration(s) compatible
	 *         with the {@code options} field of the {@code MathJax} global object.
	 */
	@Override
	public JSObject build() {
		/* Disable semantic enrichment */
		fields.put(new JSMemberName("enableEnrichment", false), new JSBoolean(false));
		/* Disable complexity option */
		fields.put(new JSMemberName("enableComplexity", false), new JSBoolean(false));
		/* Disable complexity option */
		fields.put(new JSMemberName("makeCollapsible", false), new JSBoolean(false));
		/* Disable complexity option */
		fields.put(new JSMemberName("identifyCollapsible", false), new JSBoolean(false));
		/* Disable explorer option */
		fields.put(new JSMemberName("enableExplorer", false), new JSBoolean(false));
		/* Disable assistive mml option */
		fields.put(new JSMemberName("enableAssistiveMml", false), new JSBoolean(false));
		/* Disable menu option */
		fields.put(new JSMemberName("enableMenu", false), new JSBoolean(false));

		return super.build();
	}

}
