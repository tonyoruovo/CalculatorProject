/**
 * 
 */
package mathaid.calculator.base.parser.expression;

import mathaid.ExceptionMessage;
import mathaid.NullException;

/*
 * Date: 5 May 2021----------------------------------------------------------- 
 * Time created: 19:13:44---------------------------------------------------  
 * Package: com.etineakpopha.parser------------------------------------------------ 
 * Project: RemoteWork------------------------------------------------ 
 * File: Name.java------------------------------------------------------ 
 * Class name: Name------------------------------------------------ 
 */
/**
 * A concrete implementation of {@code NameExpression}. In the
 * {@code PrattParser} class, this kind of expression represents string such as
 * 'abc'. Note that in context of the {@code PrattParser} class, numbers are
 * also regarded as an instance of this class.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class Name implements NameExpression {

	/**
	 * The name of this expression
	 */
	private final String name;

	/*
	 * Date: 29 Jul 2021-----------------------------------------------------------
	 * Time created: 12:36:07---------------------------------------------------
	 */
	/**
	 * Creates the expression using the specified string.
	 * 
	 * @param name a string that represent this object.
	 */
	public Name(String name) {
		if(name==null)
			new NullException(ExceptionMessage.EMPTY, new NullPointerException("name cannot be null!"));
		this.name = name;
	}

	/*
	 * Most Recent Date: 29 Jul 2021-----------------------------------------------
	 * Most recent time created: 12:33:49--------------------------------------
	 */
	/**
	 * Pretty-prints the a customised version of the name string and appends it to
	 * the {@code StringBuilder}. This method does not alter any field within this
	 * class.
	 * 
	 * @param sb a {@code StringBuilder} object
	 */
	@Override
	public void toString(StringBuilder sb) {
		sb.append(name);
	}

	/*
	 * Most Recent Date: 29 Jul 2021-----------------------------------------------
	 * Most recent time created: 12:37:08--------------------------------------
	 */
	/**
	 * Returns a {@code String} which in this case, is the same as the one used in
	 * the {@link #toString(StringBuilder)} method.
	 * 
	 * @return the string given in {@link #Name(String)}.
	 */
	@Override
	public String name() {
		return name;
	}

	/*
	 * Most Recent Date: 29 Jul 2021-----------------------------------------------
	 * Most recent time created: 12:39:00--------------------------------------
	 */
	/**
	 * Implements the equals method for this object to distinguish one expression
	 * from the other.
	 * 
	 * @param o {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Name))
			return false;
		return name.equals(((Name) o).name);
	}

	/*
	 * Most Recent Date: 29 Jul 2021-----------------------------------------------
	 * Most recent time created: 12:45:46--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	/*
	 * Most Recent Date: 29 Jul 2021-----------------------------------------------
	 * Most recent time created: 12:46:13--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return {@link #name()}
	 */
	@Override
	public String toString() {
		return name();
	}

}
