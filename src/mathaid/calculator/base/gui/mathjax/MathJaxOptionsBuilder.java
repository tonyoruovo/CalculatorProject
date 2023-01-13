/**
 * 
 */
package mathaid.calculator.base.gui.mathjax;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import mathaid.js.JSDictionary;
import mathaid.js.JSMemberName;
import mathaid.js.JSObject;
import mathaid.js.JSString;
import mathaid.js.JSValue;

/*
 * Date: 21 Aug 2022----------------------------------------------------------- 
 * Time created: 02:28:15---------------------------------------------------  
 * Package: js.mathjax.options------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: MathJaxOptionsBuilder.java------------------------------------------------------ 
 * Class name: MathJaxOptionsBuilder------------------------------------------------ 
 */
/**
 * The interface that represents builders of mathjax's options object during
 * configuration of the global mathjax object. Please see the "Configuring
 * MathJax" section of the mathjax documentation.
 * <p>
 * Please note that the {@code autoload} and {@code setoptions} options the of
 * the tex object are not supported in this API.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface MathJaxOptionsBuilder<T extends JSObject> {

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 08:28:07--------------------------------------------
	 */
	/**
	 * Converts the argument to a javascript object literal notation and returns the
	 * result.
	 * 
	 * @param <V>  the type to be returned
	 * @param dict a dictionary whose string keys will become field names (variable
	 *             names) in the created object and whose values will become the
	 *             values of the respected keys.
	 * @return a {@link JSObject}
	 */
	static <V extends JSValue<V>> JSObject convertDictionaryToObject(JSDictionary<JSString, V> dict) {
		Map<JSString, V> m = dict.asMap();
		NavigableMap<JSMemberName, JSValue<?>> fields = new TreeMap<>();
		for (Map.Entry<JSString, V> entry : m.entrySet())
			fields.put(new JSMemberName(entry.getKey().get()), entry.getValue());
		return new JSObject(fields);
	}

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 08:31:38--------------------------------------------
	 */
	/**
	 * Creates an Object that has been configured by this builder.
	 * 
	 * @return a fully configured object.
	 */
	T build();

	/*
	 * Date: 26 Aug 2022-----------------------------------------------------------
	 * Time created: 08:32:32--------------------------------------------
	 */
	/**
	 * A check for fields that have already been set.
	 * 
	 * @param optionName the name of the variable or field.
	 * @param isString   is the field name stored as a string or an identifier (true
	 *                   for string and false for indentifier).
	 * @return true if {@code optionName} (along with {@code isString}) is already
	 *         set and {@code false} if otherwise.
	 */
	boolean isOptionSet(String optionName, boolean isString);
}
