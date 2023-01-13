/**
 * 
 */
package mathaid.calculator.base.converter.net;

import java.util.function.Supplier;

/*
 * Date: 2 May 2022----------------------------------------------------------- 
 * Time created: 01:36:14---------------------------------------------------  
 * Package: mathaid.calculator.base.converter.net------------------------------------------------ 
 * Project: LatestProject------------------------------------------------ 
 * File: Data.java------------------------------------------------------ 
 * Class name: Data------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface Data<T extends Comparable<T>> extends Supplier<Data<T>> {
	T getContent();
	java.math.BigInteger bytes();
}
