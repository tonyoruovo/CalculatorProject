/**
 * 
 */
package mathaid;

/*
 * Date: 27 Jul 2022----------------------------------------------------------- 
 * Time created: 20:46:37---------------------------------------------------  
 * Package: mathaid------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: ConsumerAction.java------------------------------------------------------ 
 * Class name: ConsumerAction------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface ConsumerAction<T> {
	void consume(T argument);
}
