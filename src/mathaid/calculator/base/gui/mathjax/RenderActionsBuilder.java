/**
 * 
 */
package mathaid.calculator.base.gui.mathjax;

import mathaid.js.JSArray;
import mathaid.js.JSMemberName;
import mathaid.js.JSValue;

/*
 * Date: 21 Aug 2022----------------------------------------------------------- 
 * Time created: 06:24:04---------------------------------------------------  
 * Package: js.mathjax.options------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: RenderActionsBuilder.java------------------------------------------------------ 
 * Class name: RenderActionsBuilder------------------------------------------------ 
 */
/**
 * A class created used as an argument for
 * {@link DocumentOptionsBuilder#setRenderActionsObject(RenderActionsBuilder)}
 * so as to ensure safe creation for it's argument.
 * <p>
 * <b>Copied from the MathJax documentation:</b>
 * <p>
 * This is an object that specifies the actions to take during the
 * {@code MathJax.typeset()} (and its underlying
 * {@code MathJax.startup.document.render()} call), and the various conversion
 * functions, such as {@code MathJax.tex2svg()} (and their underlying
 * {@code MathJax.startup.document.convert()} call). The structure of the object
 * is name: value pairs separated by commas, where the name gives an identifier
 * for each action, and the value is an array consisting of a number and zero,
 * one, or two functions, followed optionally by a boolean value.
 * 
 * The number gives the priority of the action (lower numbers are executed first
 * when the actions are performed). The first function gives the action to
 * perform when a document is rendered as a whole, and the second a function to
 * perform when an individual expression is converted or re-rendered. These can
 * be given either as an explicit function, or as a string giving the name of a
 * method to call (the first should be a method of a {@code MathDocument}, and
 * the second of a {@code MathItem}). If either is an empty string, that action
 * is not performed. If the function is missing, the method name is taken from
 * the name of the action. The boolean value tells whether the second function
 * should be performed during a {@code convert()} call (when true) or only
 * during a {@code rerender()} call (when false).
 * 
 * For example,
 * 
 * <pre>
 * <code>
MathJax = {
  options: {
    renderActions: {
      compile: [MathItem.STATE.COMPILED],
      metrics: [MathItem.STATE.METRICS, 'getMetrics', '', false]
    }
  }
};</code>
 * </pre>
 * 
 * specifies two actions, the first called compile that uses the
 * {@code compile()} method of the {@code MathDocument} and {@code MathItem},
 * and the second called metrics that uses the {@code getMetric()} call for the
 * {@code MathDocument} when the document is rendered, but does nothing during a
 * {@code rerender()} or {@code convert()} call or an individual
 * {@code MathItem}.
 * 
 * If the first function is given explicitly, it should take one argument, the
 * {@code MathDocument} on which it is running. If the second function is given
 * explicitly, it should take two arguments, the {@code MathItem} that is being
 * processed, and the {@code MathDocument} in which it exists.
 * 
 * The default value includes actions for the main calls needed to perform
 * rendering of math: find, compile, metrics, typeset, update, and reset. These
 * find the math in the document, call the input jax on the math that was
 * located, obtain the metric information for the location of the math, call the
 * output jax to convert the internal format to the output format, insert the
 * output into the document, and finally reset the internal flags so that a
 * subsequent typesetting action will process properly.
 * 
 * You can add your own actions by adding new named actions to the renderActions
 * object, or override existing ones by re-using an existing name from above.
 * See the MathML Support section for an example of doing this. The priority
 * number tells where in the list your actions will be performed.
 * 
 * Loading extensions may cause additional actions to be inserted into the list.
 * For example, the ui/menu component inserts an action to add the menu event
 * handlers to the math after it is inserted into the page.
 * 
 * @see DocumentOptionsBuilder#setRenderActionsObject(RenderActionsBuilder)
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class RenderActionsBuilder extends AbstractOptionsBuilder {

	/**
	 * The field name for {@link #setActionForFind(JSArray)}
	 */
	public static final String FIND = "find";
	/**
	 * The field name for {@link #setActionForCompile(JSArray)}
	 */
	public static final String COMPILE = "compile";
	/**
	 * The field name for {@link #setActionForMetrics(JSArray)}
	 */
	public static final String METRICS = "metrics";
	/**
	 * The field name for {@link #setActionForTypeset(JSArray)}
	 */
	public static final String TYPESET = "typeset";
	/**
	 * The field name for {@link #setActionForUpdate(JSArray)}
	 */
	public static final String UPDATE = "update";
	/**
	 * The field name for {@link #setActionForReset(JSArray)}
	 */
	public static final String RESET = "reset";

	/*
	 * Date: 25 Aug 2022----------------------------------------------------------- 
	 * Time created: 23:17:43--------------------------------------------
	 */
	/**
	 * Sets the array of actions (or values) to be taken during the process when MathJax is finding Math within the page.
	 * @param a list of actions to be taken (or values)
	 * @return {@code this} for further method chaining
	 */
	public RenderActionsBuilder setActionForFind(JSArray<JSValue<?>> a) {
		fields.put(new JSMemberName(FIND, false), a);
		return this;
	}

	/*
	 * Date: 25 Aug 2022----------------------------------------------------------- 
	 * Time created: 23:20:38--------------------------------------------
	 */
	/**
	 * Sets the array of actions (or values) to be taken during the process when MathJax is compiling Math within the page.
	 * @param a list of actions to be taken (or values)
	 * @return {@code this} for further method chaining
	 */
	public RenderActionsBuilder setActionForCompile(JSArray<JSValue<?>> a) {
		fields.put(new JSMemberName(COMPILE, false), a);
		return this;
	}

	/*
	 * Date: 25 Aug 2022----------------------------------------------------------- 
	 * Time created: 23:17:43--------------------------------------------
	 */
	/**
	 * Sets the array of actions (or values) to be taken during the process when MathJax is calculating metrics and values for Math within the page.
	 * @param a list of actions to be taken (or values)
	 * @return {@code this} for further method chaining
	 */
	public RenderActionsBuilder setActionForMetrics(JSArray<JSValue<?>> a) {
		fields.put(new JSMemberName(METRICS, false), a);
		return this;
	}

	/*
	 * Date: 25 Aug 2022----------------------------------------------------------- 
	 * Time created: 23:17:43--------------------------------------------
	 */
	/**
	 * Sets the array of actions (or values) to be taken during the process when MathJax is typesetting the Math within the page.
	 * @param a list of actions to be taken (or values)
	 * @return {@code this} for further method chaining
	 */
	public RenderActionsBuilder setActionForTypeset(JSArray<JSValue<?>> a) {
		fields.put(new JSMemberName(TYPESET, false), a);
		return this;
	}

	/*
	 * Date: 25 Aug 2022----------------------------------------------------------- 
	 * Time created: 23:17:43--------------------------------------------
	 */
	/**
	 * Sets the array of actions (or values) to be taken during the process when MathJax is updating the document with the already typeset Math.
	 * @param a list of actions to be taken (or values)
	 * @return {@code this} for further method chaining
	 */
	public RenderActionsBuilder setActionForUpdate(JSArray<JSValue<?>> a) {
		fields.put(new JSMemberName(UPDATE, false), a);
		return this;
	}

	/*
	 * Date: 25 Aug 2022----------------------------------------------------------- 
	 * Time created: 23:17:43--------------------------------------------
	 */
	/**
	 * Sets the array of actions (or values) to be taken during reset.
	 * @param a list of actions to be taken (or values)
	 * @return {@code this} for further method chaining
	 */
	public RenderActionsBuilder setActionForReset(JSArray<JSValue<?>> a) {
		fields.put(new JSMemberName(RESET, false), a);
		return this;
	}
}
