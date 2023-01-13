/**
 * 
 */
package mathaid.functional;

/*
 * Date: 3 Sep 2022----------------------------------------------------------- 
 * Time created: 11:12:38---------------------------------------------------  
 * Package: mathaid.functional------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Implementable.java------------------------------------------------------ 
 * Class name: Implementable------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface Implementable extends Runnable {
	
	default Implementable before(Runnable post) {
		return new Implementable() {
			@Override
			public void run() {
				Implementable.this.run();
				post.run();
			}
		};
	}
	
	default Implementable after(Runnable pre) {
		return new Implementable() {
			@Override
			public void run() {
				pre.run();
				Implementable.this.run();
			}
		};
	}
}
