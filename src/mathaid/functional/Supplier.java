/**
 * 
 */
package mathaid.functional;

/*
 * Date: 3 Sep 2022----------------------------------------------------------- 
 * Time created: 11:39:39---------------------------------------------------  
 * Package: mathaid.functional------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Supplier.java------------------------------------------------------ 
 * Class name: Supplier------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface Supplier<T> {

	interface Function<T, R> {
		R call(T t);

		default <U> Function<U, R> append(Function<? super U, ? extends T> inner) {
			return new Function<>() {
				@Override
				public R call(U u) {
					return Supplier.Function.this.call(inner.call(u));
				}
			};
		}

		default <U> Function<T, U> prepend(Function<? super R, ? extends U> outer) {
			return new Function<>() {
				@Override
				public U call(T t) {
					return outer.call(Supplier.Function.this.call(t));
				}
			};
		}
	}

	T supply();
}
