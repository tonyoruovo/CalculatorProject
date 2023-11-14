/*
 * Date: 11 Nov 2023 -----------------------------------------------------------
 * Time created: 12:45:39 ---------------------------------------------------
 */
/**
 * 
 */
package mathaid.calculator.base.evaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;
import java.util.function.Function;

import mathaid.MomentString;
import mathaid.calculator.base.typeset.LinkedSegment;

/*
 * Date: 11 Nov 2023 -----------------------------------------------------------
 * Time created: 12:45:39 ---------------------------------------------------
 * Package: mathaid.calculator.base.evaluator ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: Details.java ------------------------------------------------------
 * Class name: Details ------------------------------------------------
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Details implements Flow.Subscriber<Result.Processed> {

	private final Runnable comHandler;
	private final Function<Throwable, Void> errHandler;
	private final List<Result.Details> resultDetails;
	public volatile transient NavigableMap<MomentString, LinkedSegment> details;
//	private int index;

	/*
	 * Date: 11 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:45:39 ---------------------------------------------------
	 */
	/**
	 */
	public Details(Function<Throwable, Void> errorHandler, Runnable completeHandler) {
		this.errHandler = errorHandler;
		this.comHandler = completeHandler;
		resultDetails = new ArrayList<>();
		resultDetails.add(new SDetails());
		resultDetails.add(new PDetails());
//		index = 0;
	}
	
	public Details() {
		this(t -> { t.printStackTrace(); return null; }, () -> {});
	}

	/*
	 * Date: 11 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:50:09 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @param subscription
	 */
	@Override
	public void onSubscribe(Subscription subscription) {
		(s = subscription).request((Long.MAX_VALUE));
	}

	/*
	 * Date: 11 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:50:09 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @param item
	 */
	@Override
	public void onNext(Result.Processed item) {
		int index = Integer.parseInt(item.getCommand());
		details = resultDetails.get(index).getDetails(item.getParams(), item.getText());
	}

	/*
	 * Date: 11 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:50:09 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @param throwable
	 */
	@Override
	public void onError(Throwable throwable) {
		s.cancel();
		errHandler.apply(throwable);
	}

	/*
	 * Date: 11 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:50:09 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onComplete() {
		if (s != null)
			s.cancel();
		s = null;
//		resultDetails.clear();
		comHandler.run();
	}
	
	private Flow.Subscription s;

}
