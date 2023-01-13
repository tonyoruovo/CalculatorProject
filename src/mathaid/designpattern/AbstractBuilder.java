/**
 * 
 */
package mathaid.designpattern;

/*
 * Date: 1 May 2022----------------------------------------------------------- 
 * Time created: 12:47:41---------------------------------------------------  
 * Package: mathaid.designpattern------------------------------------------------ 
 * Project: LatestProject------------------------------------------------ 
 * File: AbstractBuilder.java------------------------------------------------------ 
 * Class name: AbstractBuilder------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public abstract class AbstractBuilder<T> implements Builder<T> {

	/*
	 * Date: 1 May 2022----------------------------------------------------------- 
	 * Time created: 12:47:41--------------------------------------------------- 
	 */
	/**
	 */
	public AbstractBuilder(T object) {
		this.object = object;
	}
	
	public abstract <U> AbstractBuilder<T> setField(String name, U object);

	protected T object;

}
