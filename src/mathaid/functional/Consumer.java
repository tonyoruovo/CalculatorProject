/**
 * 
 */
package mathaid.functional;

import mathaid.calculator.base.util.Tuple;

/*
 * Date: 3 Sep 2022----------------------------------------------------------- 
 * Time created: 11:21:39---------------------------------------------------  
 * Package: mathaid.functional------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Consumer.java------------------------------------------------------ 
 * Class name: Consumer------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface Consumer<T> {
	
	interface MultiConsumer<T, C extends Tuple<T>> {
		void consume(C args);
		
		default MultiConsumer<T, C> before(MultiConsumer<? super T, ? super C> post) {
			return new MultiConsumer<>() {
				@Override
				public void consume(C args) {
					Consumer.MultiConsumer.this.consume(args);
					post.consume(args);
				}
				
			};
		}
		
		default MultiConsumer<T, C> after(MultiConsumer<? super T, ? super C> pre) {
			return new MultiConsumer<>() {
				@Override
				public void consume(C args) {
					pre.consume(args);
					Consumer.MultiConsumer.this.consume(args);
				}
				
			};
		}
	}

	void consume(T t);
	
	default Consumer<T> before(Consumer<? super T> post) {
		return new Consumer<>() {
			@Override
			public void consume(T t) {
				Consumer.this.consume(t);
				post.consume(t);
			}
			
		};
	}
	
	default Consumer<T> after(Consumer<? super T> pre) {
		return new Consumer<>() {
			@Override
			public void consume(T t) {
				pre.consume(t);
				Consumer.this.consume(t);
			}
			
		};
	}
}
