/*
 * Date: 10 Nov 2023 -----------------------------------------------------------
 * Time created: 10:08:24 ---------------------------------------------------
 */
/**
 * 
 */
package mathaid.calculator.base.evaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.function.Function;

import mathaid.calculator.base.evaluator.parser.expression.EvaluatableExpression.ExpressionParams;
import mathaid.calculator.base.typeset.LinkedSegment;

/*
 * Date: 10 Nov 2023 -----------------------------------------------------------
 * Time created: 10:08:24 ---------------------------------------------------
 * Package: mathaid.calculator.base.evaluator ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: BasicCalculator.java ------------------------------------------------------
 * Class name: BasicCalculator ------------------------------------------------
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class BasicCalculator implements InputProcessor, AutoCloseable {
	
	private static class Processed implements Result.Processed {
		
		private static int n = 0;
		private final int id;
		private final LinkedSegment t;
		private final String cmd;
		private final ExpressionParams<?> p;
		
		/*
		 * Date: 12 Nov 2023 -----------------------------------------------------------
		 * Time created: 18:23:59 ---------------------------------------------------
		 */
		/**
		 */
		public Processed(LinkedSegment text, String command, ExpressionParams<?> params) {
			id = n++;
			t = text;
			cmd = command;
			p = params;
		}
	
		/*
		 * Date: 12 Nov 2023 -----------------------------------------------------------
		 * Time created: 18:22:45 ---------------------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * @return
		 */
		@Override
		public long getId() {
			return id;
		}
	
		/*
		 * Date: 12 Nov 2023 -----------------------------------------------------------
		 * Time created: 18:22:45 ---------------------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * @return
		 */
		@Override
		public LinkedSegment getText() {
			return t;
		}
	
		/*
		 * Date: 12 Nov 2023 -----------------------------------------------------------
		 * Time created: 18:22:45 ---------------------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * @return
		 */
		@Override
		public String getCommand() {
			return cmd;
		}
	
		/*
		 * Date: 12 Nov 2023 -----------------------------------------------------------
		 * Time created: 18:22:45 ---------------------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * @return
		 */
		@Override
		public ExpressionParams<?> getParams() {
			return p;
		}
	}

	/*
	 * Date: 10 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:08:24 ---------------------------------------------------
	 */
	/**
	 */
	public BasicCalculator(Function<Throwable, Void> errorHandler, Runnable completeHandler) {
		this.errHandler = errorHandler;
		this.comHandler = completeHandler;
		this.engines = new ArrayList<>();
		engines.add(new Scientific());
		engines.add(new Programmer());
		this.id = 0;
//		this.index = 0;
		this.subs = new ArrayList<>();
	}
	
	public BasicCalculator() {
		this(t -> { t.printStackTrace(); return null; }, () -> {});
	}

	/*
	 * Date: 10 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:37:56 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @throws Exception
	 */
	@Override
	public void close() throws Exception {
		shutdown();
	}

	protected void shutdown() {
		subs.clear();
		engines.clear();
		subscription.cancel();
//		index = 0;
	}

	protected void restart() {
		subscription.cancel();
//		index = 0;
		subscription.request(Long.MAX_VALUE);
	}

	/*
	 * Date: 10 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:08:24 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param subscriber
	 */
	@Override
	public void subscribe(Flow.Subscriber<? super Result.Processed> subscriber) {
		Subscription s = new Subscription(id++, subscriber);
		s.onSubscribe();
	}

	private class Subscription implements Flow.Subscription, Comparable<Subscription> {

		/*
		 * Date: 10 Nov 2023 -----------------------------------------------------------
		 * Time created: 11:13:22 ---------------------------------------------------
		 */
		/**
		 */
		Subscription(int id, Flow.Subscriber<? super Result.Processed> subscriber) {
			this.id = id;
			this.subscriber = subscriber;
		}

		/*
		 * Date: 10 Nov 2023 -----------------------------------------------------------
		 * Time created: 10:46:41 ---------------------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @param n
		 */
		@Override
		public void request(long n) {
			if (BasicCalculator.this.subs.contains(this)) {
				requests += n;
			}
		}

		/*
		 * Date: 10 Nov 2023 -----------------------------------------------------------
		 * Time created: 10:46:41 ---------------------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void cancel() {
			subs.remove(this);
		}
		
		@Override
		public int compareTo(Subscription s) {
			return Integer.compare(id, s.id);
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof Subscription) {
				return compareTo((Subscription) o) == 0;
			}
			return false;
		}

		void onSubscribe() {
			subscriber.onSubscribe(this);
		}

		void onNext(Result.Processed data) {
			if (requests > 0L)
				try {
					subscriber.onNext(data);
				} catch (Throwable e) {
					subscriber.onError(e);
				}
		}

		private final int id;
		long requests;
		private final Flow.Subscriber<? super Result.Processed> subscriber;
	}

	public void onNext(Input input)  {
		final boolean isCommand = input.getText() == null;
		if (!isCommand) {
			StringBuilder sb = new StringBuilder();
			List<Integer> l = new java.util.ArrayList<>();
			LinkedSegment text = input.getText();
			text.toString(sb, null, l);
			final int index = Integer.parseInt(input.getCommand());
			LinkedSegment val = engines.get(index).evaluate(sb.toString());
//			getSubscribers().parallelStream().forEach(x -> {
//				try {
//					x.onNext(val);
//				} catch (Throwable t) {
//					x.onError(t);
//					t.printStackTrace();
//				}
//			});
			onNext(new Processed(val, input.getCommand(), (ExpressionParams<?>) engines.get(index)));
		} else
			processCommand(input.getCommand());
}
	
	/*
	 * Date: 10 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:52:30 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param val
	 */
	public void onNext(Result.Processed val) {
		subs.parallelStream().forEach(x -> x.onNext(val));
	}

	/*
	 * Date: 10 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:08:24 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param subscription
	 */
	@Override
	public void onSubscribe(Flow.Subscription subscription) {
		(this.subscription = subscription).request(Long.MAX_VALUE);
	}

	/*
	 * Date: 10 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:08:24 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onComplete() {
		comHandler.run();
		subs.stream().forEachOrdered(x -> x.subscriber.onComplete());
		shutdown();
	}

	/*
	 * Date: 10 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:08:24 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param throwable {@inheritDoc}
	 */
	@Override
	public void onError(Throwable throwable) {
		errHandler.apply(throwable);
		subs.parallelStream().forEach(x -> x.subscriber.onError(throwable));
		restart();
	}

	/*
	 * Date: 10 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:08:24 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param command {@inheritDoc}
	 * @implNote All commands must be prefixed with the {@code '$'} character. All
	 *           commands are case insensitive.
	 */
	@Override
	public void processCommand(String command) {
		switch (command.toLowerCase().substring(1)) {
//		case "next": {
//			setCurrent(Math.min(index + 1, engines.size() - 1));
//			break;
//		}
//		case "prev": {
//			setCurrent(Math.max(index - 1, 0));
//			break;
//		}
		default:
			return;
		}
	}

	/*
	 * Date: 10 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:19:55 ---------------------------------------------------
	 */
	/**
	 * Sets the value for {@link #getCurrent}. This will set the evaluation engine
	 * that will be used each time {@link #evaluate} is called.
	 * 
	 * @param index the index of the evaluation engine to be used.
	 * @throws IndexOutOfBoundsException if the argument is less than 0 or more than
	 *                                   the number of engines available.
	 */
//	protected void setCurrent(int index) throws IndexOutOfBoundsException {
//		if (index >= 0 && index < engines.size())
//			this.index = index;
//		throw new IndexOutOfBoundsException(index);
//	}

	/*
	 * Date: 10 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:08:24 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public int getEngineLength() {
		return engines.size();
	}

	/**
	 * A collection of all the engines registered by this processor.
	 */
	private final List<Evaluator<LinkedSegment>> engines;
	/**
	 * The current index of the engine collection representing the active engine.
	 */
//	private int index;
	/**
	 * A collection of all the active subscribers for this processor.
	 */
	private final List<Subscription> subs;
	/**
	 * The subscription that enables this object to receive updates from the input
	 * publisher.
	 */
	private Flow.Subscription subscription;
	/**
	 * The pool from which id are assigned before being incremented.
	 */
	protected int id;
	/**
	 * Called by {@link #onComplete} to enable user defined on complete action
	 */
	private final Runnable comHandler;
	/**
	 * Called by {@link #onError} to enable user defined on error action
	 */
	private final Function<Throwable, Void> errHandler;

}
