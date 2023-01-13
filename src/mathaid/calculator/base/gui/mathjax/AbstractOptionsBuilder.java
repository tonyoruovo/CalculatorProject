/**
 * 
 */
package mathaid.calculator.base.gui.mathjax;

import java.util.NavigableMap;
import java.util.TreeMap;

import mathaid.js.JSMemberName;
import mathaid.js.JSObject;
import mathaid.js.JSValue;

/*
 * Date: 21 Aug 2022----------------------------------------------------------- 
 * Time created: 06:27:44---------------------------------------------------  
 * Package: js.mathjax.options------------------------------------------------ 
 * Project: TestCode------------------------------------------------ 
 * File: AbstractOptionsBuilder.java------------------------------------------------------ 
 * Class name: AbstractOptionsBuilder------------------------------------------------ 
 */
/**
 * All the field names to be used for creating a MathJax configuration are
 * represented as constants in their respective implementation classes. Methods
 * with the name prefix 'set' creates (or updates) the configuration field,
 * while methods with the prefix 'addRemove' creates the fields using MathJax
 * list syntax for list item appendage and removal.
 * <p>
 * Calling any method with the prefix 'set' more than once may override any
 * previous value set by that method. Calling any method with the prefix
 * 'addRemove' more than once throws a
 * {@code OptionsBuilderModificationException}. Use
 * {@link #isOptionSet(String, boolean)} to be sure that you are not calling the
 * 'addRemove' for that field more than once.
 * <h2>Developer options:</h2>
 * <p>
 * These are developer options for this builder. Developer options are options
 * which allows a user of MathJax to directly interact/read/modify the internal
 * MathJax API.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public abstract class AbstractOptionsBuilder implements MathJaxOptionsBuilder<JSObject> {

	/*
	 * Most Recent Date: 21 Aug 2022-----------------------------------------------
	 * Most recent time created: 02:43:12--------------------------------------
	 */
	/**
	 * Compiles the options given and returns a valid object.
	 * 
	 * The default behaviour is to throw a
	 * {@code OptionsBuilderModificationException} if no field is set. If at least 1
	 * field is set, then create an object and return it.
	 * 
	 * @return a configured object.
	 */
	@Override
	public JSObject build() {
		if (!fields.isEmpty())
			return new JSObject(fields);
		throw new OptionsBuilderModificationException("cannot build an empty options object", false);
	}

	/*
	 * Most Recent Date: 21 Aug 2022-----------------------------------------------
	 * Most recent time created: 03:13:14--------------------------------------
	 */
	/**
	 * Gets the current value of {@code optionName} from this builder and determines
	 * whether it is null or not. If it is non-null, then this method returns true
	 * otherwise it returns false. This is used mostly to check if a field is
	 * already set in the builder.
	 * 
	 * @param optionName the name of the field that is set in the builder.
	 * @param isString   true if this field name is stored as a string and false if
	 *                   otherwise.
	 * @return true if {@code optionName} (along with {@code isString}) is already
	 *         set and {@code false} if otherwise.
	 */
	@Override
	public boolean isOptionSet(String optionName, boolean isString) {
		return fields.containsKey(new JSMemberName(optionName, isString));
	}

	/**
	 * A {@code NavigableMap} that preserves the insertion order of a
	 * {@code JSMemberName} when it is inserted as a key. This will hold values set
	 * by the builder and when {@link #build()} is called, can be used to create a
	 * {@code JSObject}.
	 */
	protected final NavigableMap<JSMemberName, JSValue<?>> fields = new TreeMap<>();
}
