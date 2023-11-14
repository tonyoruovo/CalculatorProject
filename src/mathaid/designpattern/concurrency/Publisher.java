/*
 * Date: Mar 29, 2023 -----------------------------------------------------------
 * Time created: 9:23:21 PM ---------------------------------------------------
 */
/**
 * 
 */
package mathaid.designpattern.concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Flow;

import mathaid.calculator.base.util.Tuple;
import mathaid.calculator.base.util.Utility;

/*
 * Date: Mar 29, 2023 -----------------------------------------------------------
 * Time created: 9:23:21 PM ---------------------------------------------------
 * Package: mathaid.designpattern.concurrency ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: Publisher.java ------------------------------------------------------
 * Class name: Publisher ------------------------------------------------
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Publisher implements Flow.Publisher<String> {

	Map<Integer, Tuple.Couple<java.math.BigInteger, Flow.Subscriber<? super String>>> subs;
	int id = 0;
	Random r = new Random();

	/*
	 * Date: Mar 29, 2023
	 * ----------------------------------------------------------- Time created:
	 * 9:23:21 PM ---------------------------------------------------
	 */
	/**
	 */
	public Publisher() {
		subs = new HashMap<>();
	}

	/*
	 * Date: Mar 29, 2023
	 * ----------------------------------------------------------- Time created:
	 * 9:23:21 PM ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param subscriber
	 */
	@Override
	public void subscribe(Flow.Subscriber<? super String> subscriber) {
		int id = this.id++;
		subs.put(id, Tuple.of(Utility.i(0), subscriber));
		Subscription s = new Subscription(id);
		subscriber.onSubscribe(s);
	}

	public void process() {
		subs.values().parallelStream().forEach(x -> {
			int ii = 0;
			var bn = x.get();
			while (bn.signum() > 0)
				try {
					x.get2nd()
							.onNext(new StringBuilder("items: ").append(bn).append("\nvalue: ")
									.append(Utility.nextBigDecimal(r, bn.intValue())).append("\nindex: ").append(ii++)
									.append("\n").toString());
					bn = bn.subtract(java.math.BigInteger.ONE);
				} catch (Throwable t) {
					x.get2nd().onError(t);
				}
			x.get2nd().onComplete();
		});
	}

	private class Subscription implements Flow.Subscription {

		int id;

		/*
		 * Date: Mar 29, 2023
		 * ----------------------------------------------------------- Time created:
		 * 9:29:15 PM ---------------------------------------------------
		 */
		/**
		 */
		Subscription(int id) {
			this.id = id;
		}

		/*
		 * Date: Mar 29, 2023 -----------------------------------------------------------
		 * Time created: 9:28:57 PM ---------------------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @param n
		 */
		@Override
		public void request(long n) {
			if (Publisher.this.subs.containsKey((id))) {
				subs.put(id, Tuple.of(subs.get(id).get().add(Utility.i(n)), subs.get(id).get2nd()));
			}
		}

		/*
		 * Date: Mar 29, 2023
		 * ----------------------------------------------------------- Time created:
		 * 9:28:57 PM ---------------------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void cancel() {
			System.out.println(new StringBuilder("Cancelling: ").append(id).append(" ..."));
			subs.remove(id);
		}

	}

}
