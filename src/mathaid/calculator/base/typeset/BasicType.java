/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.Iterator;

public final class BasicType implements Type {

	public BasicType(int code, Type child, String name) throws IllegalArgumentException {
		this.code = code;
		this.sibling = child;
		this.name = name;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public Type getChild() {
		return sibling;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		return code;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Type) {
			Type t = (Type) obj;
			return code == t.getCode() &&
				(sibling != null && t.getChild() != null
					? getChild().equals(t.getChild()) : true);
		}
		return false;
	}

	public Type concat(Type t) {
		if(getChild() == null)
			return new BasicType(code, t, name);
		return ((BasicType) sibling).concat(t);
	}

	private final Type sibling;
	private final int code;
	private final String name;
	/*
	 * Date: Jan 11, 2023 -----------------------------------------------------------
	 * Time created: 2:34:58 AM ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @return
	 */
	@Override
	public Iterator<Type> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
}