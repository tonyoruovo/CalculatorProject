/**
 * 
 */
package mathaid.calculator.base.evaluator.parser;

import java.util.Objects;

/*
 * Date: 4 Sep 2022----------------------------------------------------------- 
 * Time created: 06:59:10---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Type.java------------------------------------------------------ 
 * Class name: Type------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public final class Type<T> {

	/*
	 * Date: 4 Sep 2022----------------------------------------------------------- 
	 * Time created: 06:59:10--------------------------------------------------- 
	 */
	/**
	 */
	public Type(T name, Precedence precedence) {
		this.name  = name;
		this.precedence = precedence;
	}

	/*
	 * Most Recent Date: 4 Sep 2022-----------------------------------------------
	 * Most recent time created: 09:19:25--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @return
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(name) ^ precedence.hashCode();
	}

	/*
	 * Most Recent Date: 4 Sep 2022-----------------------------------------------
	 * Most recent time created: 09:19:25--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Type) {
			Type<?> t = (Type<?>) obj;
			return getName().equals(t.getName()) && getPrecedence().equals(t.getPrecedence());
		}
		return false;
	}

	/*
	 * Most Recent Date: 4 Sep 2022-----------------------------------------------
	 * Most recent time created: 09:19:25--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @return
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	public T getName() {
		return name;
	}
	
	public Precedence getPrecedence() {
		return precedence;
	}
	
	private final T name;
	private final Precedence precedence;

}
