/**
 * 
 */
package mathaid.designpattern;

/*
 * Date: 28 Apr 2022----------------------------------------------------------- 
 * Time created: 23:19:25---------------------------------------------------  
 * Package: mathaid.designpattern------------------------------------------------ 
 * Project: LatestProject------------------------------------------------ 
 * File: Builder.java------------------------------------------------------ 
 * Class name: Builder------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface Builder<T> {
	T build();
	static interface Rebuilder<T> extends Builder<T> {
		Rebuilder<T> rebuild(T t);
	}
}
