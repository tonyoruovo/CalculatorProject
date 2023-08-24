/*
 * Date: Mar 29, 2023 -----------------------------------------------------------
 * Time created: 8:54:50 PM ---------------------------------------------------
 */
/**
 * 
 */
package mathaid.designpattern.concurrency;

import java.util.concurrent.Flow;

/*
 * Date: Mar 29, 2023 -----------------------------------------------------------
 * Time created: 8:54:50 PM ---------------------------------------------------
 * Package: mathaid.designpattern.concurrency ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: Subscriber.java ------------------------------------------------------
 * Class name: Subscriber ------------------------------------------------
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Subscriber implements Flow.Subscriber<String> {
	
	int req = 10;
	int ids = 0;
	Flow.Subscription sub;
	String item = "";

	/*
	 * Date: Mar 29, 2023 -----------------------------------------------------------
	 * Time created: 8:54:51 PM ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @param subscription
	 */
	@Override
	public void onSubscribe(Flow.Subscription subscription) {
		(this.sub = subscription).request(req);
	}

	/*
	 * Date: Mar 29, 2023 -----------------------------------------------------------
	 * Time created: 8:54:51 PM ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @param item
	 */
	@Override
	public void onNext(String item) {
		//do stuff with the incoming data
		req--;
		this.item = item;
		System.out.println(item);
//		if(req <= 0) completeCount--;
	}

	/*
	 * Date: Mar 29, 2023 -----------------------------------------------------------
	 * Time created: 8:54:51 PM ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @param throwable
	 */
	@Override
	public void onError(Throwable throwable) {
		System.out.println("Incoming...");
		System.err.println(throwable);
		sub.cancel();
	}

	/*
	 * Date: Mar 29, 2023 -----------------------------------------------------------
	 * Time created: 8:54:51 PM ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onComplete() {
		System.out.println("Completed!");
//		if(completeCount > 0) {
//			sub.request(req);
//		} else 
			sub.cancel();
	}
//	int completeCount = 10;

}
