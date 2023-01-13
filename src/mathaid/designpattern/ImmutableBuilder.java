/**
 * 
 */
package mathaid.designpattern;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mathaid.IndexBeyondLimitException;
import mathaid.calculator.base.util.Tuple;

/*
 * Date: 28 Apr 2022----------------------------------------------------------- 
 * Time created: 23:23:57---------------------------------------------------  
 * Package: mathaid.designpattern------------------------------------------------ 
 * Project: LatestProject------------------------------------------------ 
 * File: AbstractBuilder.java------------------------------------------------------ 
 * Class name: AbstractBuilder------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public abstract class ImmutableBuilder<T, C extends Tuple<?>> extends AbstractBuilder<C> {

	/*
	 * Date: 28 Apr 2022-----------------------------------------------------------
	 * Time created: 23:23:57---------------------------------------------------
	 */
	/**
	 * @param mutablePublicStates
	 * @param fieldNamesInOrder
	 */
	public ImmutableBuilder(C mutablePublicStates, String... fieldNamesInOrder) {
		super(mutablePublicStates);
		this.fieldNames = Arrays.asList(fieldNamesInOrder);
	}

	/*
	 * @SuppressWarnings("unchecked") public ImmutableBuilder<T, C>
	 * setByteField(String fieldName, byte value) { int index =
	 * fieldNames.indexOf(fieldName); if (index < 0) new
	 * IndexBeyondLimitException(index, object.size()); if (object.get(index) ==
	 * null) {// mutable object. Once a field is assigned, it cannot be reassigned.
	 * // new mathaid.IllegalStatusException(ExceptionMessage.ERROR, (Object[])
	 * null); Object $ = object.get(index); if ($ != null && $ instanceof Byte)
	 * object = (C) object.set(index, value); }
	 * 
	 * return this; }
	 * 
	 * @SuppressWarnings("unchecked") public ImmutableBuilder<T, C>
	 * setShortField(String fieldName, short value) { int index =
	 * fieldNames.indexOf(fieldName); if (index < 0) new
	 * IndexBeyondLimitException(index, object.size()); if (object.get(index) ==
	 * null) {// mutable object. Once a field is assigned, it cannot be reassigned.
	 * // new mathaid.IllegalStatusException(ExceptionMessage.ERROR, (Object[])
	 * null); Object $ = object.get(index); if ($ != null && $ instanceof Short)
	 * object = (C) object.set(index, value); }
	 * 
	 * return this; }
	 * 
	 * @SuppressWarnings("unchecked") public ImmutableBuilder<T, C>
	 * setIntField(String fieldName, int value) { int index =
	 * fieldNames.indexOf(fieldName); if (index < 0) new
	 * IndexBeyondLimitException(index, object.size()); if (object.get(index) ==
	 * null) {// mutable object. Once a field is assigned, it cannot be reassigned.
	 * // new mathaid.IllegalStatusException(ExceptionMessage.ERROR, (Object[])
	 * null); Object $ = object.get(index); if ($ != null && $ instanceof Integer)
	 * object = (C) object.set(index, value); }
	 * 
	 * return this; }
	 * 
	 * @SuppressWarnings("unchecked") public ImmutableBuilder<T, C>
	 * setLongField(String fieldName, long value) { int index =
	 * fieldNames.indexOf(fieldName); if (index < 0) new
	 * IndexBeyondLimitException(index, object.size()); if (object.get(index) ==
	 * null) {// mutable object. Once a field is assigned, it cannot be reassigned.
	 * // new mathaid.IllegalStatusException(ExceptionMessage.ERROR, (Object[])
	 * null); Object $ = object.get(index); if ($ != null && $ instanceof Long)
	 * object = (C) object.set(index, value); }
	 * 
	 * return this; }
	 * 
	 * @SuppressWarnings("unchecked") public ImmutableBuilder<T, C>
	 * setBooleanField(String fieldName, boolean value) { int index =
	 * fieldNames.indexOf(fieldName); if (index < 0) new
	 * IndexBeyondLimitException(index, object.size()); if (object.get(index) ==
	 * null) {// mutable object. Once a field is assigned, it cannot be reassigned.
	 * // new mathaid.IllegalStatusException(ExceptionMessage.ERROR, (Object[])
	 * null); Object $ = object.get(index); if ($ != null && $ instanceof Boolean)
	 * object = (C) object.set(index, value); }
	 * 
	 * return this; }
	 * 
	 * @SuppressWarnings("unchecked") public ImmutableBuilder<T, C>
	 * setFloatField(String fieldName, float value) { int index =
	 * fieldNames.indexOf(fieldName); if (index < 0) new
	 * IndexBeyondLimitException(index, object.size()); if (object.get(index) ==
	 * null) {// mutable object. Once a field is assigned, it cannot be reassigned.
	 * // new mathaid.IllegalStatusException(ExceptionMessage.ERROR, (Object[])
	 * null); Object $ = object.get(index); if ($ != null && $ instanceof Float)
	 * object = (C) object.set(index, value); }
	 * 
	 * return this; }
	 * 
	 * @SuppressWarnings("unchecked") public ImmutableBuilder<T, C>
	 * setDoubleField(String fieldName, double value) { int index =
	 * fieldNames.indexOf(fieldName); if (index < 0) new
	 * IndexBeyondLimitException(index, object.size()); if (object.get(index) ==
	 * null) {// mutable object. Once a field is assigned, it cannot be reassigned.
	 * // new mathaid.IllegalStatusException(ExceptionMessage.ERROR, (Object[])
	 * null); Object $ = object.get(index); if ($ != null && $ instanceof Double)
	 * object = (C) object.set(index, value); }
	 * 
	 * return this; }
	 * 
	 * @SuppressWarnings("unchecked") public ImmutableBuilder<T, C>
	 * setCharField(String fieldName, char value) { int index =
	 * fieldNames.indexOf(fieldName); if (index < 0) new
	 * IndexBeyondLimitException(index, object.size()); if (object.get(index) ==
	 * null) {// mutable object. Once a field is assigned, it cannot be reassigned.
	 * // new mathaid.IllegalStatusException(ExceptionMessage.ERROR, (Object[])
	 * null); Object $ = object.get(index); if ($ != null && $ instanceof Character)
	 * object = (C) object.set(index, value); }
	 * 
	 * return this; }
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <U> ImmutableBuilder<T, C> setField(String fieldName, U newValue) {
		int index = fieldNames.indexOf(fieldName);
		if (index < 0)
			new IndexBeyondLimitException(index, object.size());
		if (object.get(index) == null)// mutable object. Once a field is assigned, it cannot be reassigned.
//			new mathaid.IllegalStatusException(ExceptionMessage.ERROR, (Object[]) null);
			object = (C) object.set(index, newValue);

		return this;
	}

	public C getFields() {
		return object;
	}

	public List<String> getFieldNames() {
		return Collections.unmodifiableList(fieldNames);
	}

	private List<String> fieldNames;

}
