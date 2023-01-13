/**
 * 
 */
package mathaid.designpattern;

import java.lang.reflect.Field;

/*
 * Date: 30 Apr 2022----------------------------------------------------------- 
 * Time created: 16:30:46---------------------------------------------------  
 * Package: mathaid.designpattern------------------------------------------------ 
 * Project: LatestProject------------------------------------------------ 
 * File: MutableBuilder.java------------------------------------------------------ 
 * Class name: MutableBuilder------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public abstract class MutableBuilder<T> extends AbstractBuilder<T> {

	public MutableBuilder(T object, Rebuilder<T> rebuilder) {
		this(object, false, rebuilder);
	}
	/*
	 * Date: 30 Apr 2022-----------------------------------------------------------
	 * Time created: 16:30:46---------------------------------------------------
	 */
	/**
	 */
	public MutableBuilder(T object, boolean accessNonPublicFields, Rebuilder<T> rebuilder) {
		super(object);
		access = accessNonPublicFields;
		this.rebuilder = rebuilder;
	}

	public MutableBuilder<T> setByteField(String fieldName, byte value)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Class<?> c = object.getClass();
		Field f = c.getDeclaredField(fieldName);
		if (access)
			f.setAccessible(true);
		f.setByte(object, value);
		return this;
	}

	public MutableBuilder<T> setShortField(String fieldName, short value)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Class<?> c = object.getClass();
		Field f = c.getDeclaredField(fieldName);
		if (access)
			f.setAccessible(true);
		f.setShort(object, value);
		return this;
	}

	public MutableBuilder<T> setIntField(String fieldName, int value)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Class<?> c = object.getClass();
		Field f = c.getDeclaredField(fieldName);
		if (access)
			f.setAccessible(true);
		f.setInt(object, value);
		return this;
	}

	public MutableBuilder<T> setLongField(String fieldName, long value)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Class<?> c = object.getClass();
		Field f = c.getDeclaredField(fieldName);
		if (access)
			f.setAccessible(true);
		f.setLong(object, value);
		return this;
	}

	public MutableBuilder<T> setBooleanField(String fieldName, boolean value)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Class<?> c = object.getClass();
		Field f = c.getDeclaredField(fieldName);
		if (access)
			f.setAccessible(true);
		f.setBoolean(object, value);
		return this;
	}

	public MutableBuilder<T> setFloatField(String fieldName, float value)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Class<?> c = object.getClass();
		Field f = c.getDeclaredField(fieldName);
		if (access)
			f.setAccessible(true);
		f.setFloat(object, value);
		return this;
	}

	public MutableBuilder<T> setDoubleField(String fieldName, double value)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Class<?> c = object.getClass();
		Field f = c.getDeclaredField(fieldName);
		if (access)
			f.setAccessible(true);
		f.setDouble(object, value);
		return this;
	}

	public MutableBuilder<T> setCharField(String fieldName, char value)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Class<?> c = object.getClass();
		Field f = c.getDeclaredField(fieldName);
		if (access)
			f.setAccessible(true);
		f.setChar(object, value);
		return this;
	}

	@Override
	public <U> MutableBuilder<T> setField(String fieldName, U value) {
		Class<?> c = object.getClass();
		try {
			Field f = c.getDeclaredField(fieldName);
			if (access)
				f.setAccessible(true);
			f.set(object, value);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return this;
	}

	public Rebuilder<T> getRebuilder() {
		return rebuilder;
	}

	private boolean access;
	private Rebuilder<T> rebuilder;

}
