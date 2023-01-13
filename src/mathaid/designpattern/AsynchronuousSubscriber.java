/**
 * 
 */
package mathaid.designpattern;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Date: 5 Apr 2021----------------------------------------------------------- 
 * Time created: 19:30:20---------------------------------------------------  
 * Package: mathaid.calculator.base------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: AsynchronuousSubscriber.java------------------------------------------------------ 
 * Class name: AsynchronuousSubscriber------------------------------------------------ 
 */
/**
 * 
 * @implNote The current implementation has no way of shutting down it's
 *           activities in a thread. If a feature that does exactly that is
 *           desired, users can use the {@link AsynchronuousThreadHolder} for
 *           that purpose;
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class AsynchronuousSubscriber<T> implements Runnable {

	private final Observer<T> subscriber;
	private volatile long interval;
	private ReentrantLock lock;
	private final long initial;
	private AtomicBoolean initialTime = new AtomicBoolean(true);

	/*
	 * Date: 5 Apr 2021-----------------------------------------------------------
	 * Time created: 19:30:21---------------------------------------------------
	 */
	/**
	 */
	public AsynchronuousSubscriber(Observer<T> subscriber, byte initial, byte interval, ReentrantLock lock) {
//		this.subscriber = subscriber;
//		this.waitTime = waitTime * 1000L;
		this(subscriber, initial * 1000L, interval * 1000L, lock);
	}

	protected AsynchronuousSubscriber(Observer<T> subscriber, long initial, long interval, ReentrantLock lock) {
		this.subscriber = subscriber;
		this.interval = interval;
		this.lock = lock;
		this.initial = initial;
	}

	protected AsynchronuousSubscriber(Observer<T> subscriber) {
		this(subscriber, (byte) 2, (byte) 1, new ReentrantLock());
	}

	/*
	 * Most Recent Date: 5 Apr 2021-----------------------------------------------
	 * Most recent time created: 19:30:21--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {

		long elapsed = System.currentTimeMillis();
		while (true) {
			try {
//				if(lock.tryLock(500, MILLISECONDS)) {
				if (initialTime.getAndSet(false))
					Thread.sleep(initial);
				else
					Thread.sleep(interval + (System.currentTimeMillis() - elapsed - 1000L));
				try {// prevent thread death
					lock.lock(); 
					subscriber.doAction();
					elapsed = System.currentTimeMillis();
				} catch (@SuppressWarnings("unused") Exception e) {
					elapsed = System.currentTimeMillis();
					Thread t = Thread.currentThread();
					System.err.println("Class: " + subscriber.getClass().getName() + ", " + t.getName() + ", ID: "
							+ t.getId() + ", Hash: " + hashCode() + "\n");
//				e.printStackTrace();
					Throwable[] errs = e.getSuppressed();
					for(Throwable err : errs)
						System.err.println(err);
				}finally {
					lock.unlock();
				}
//				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public final synchronized void setSeconds(byte seconds) {
		this.interval = seconds * 1000L;
	}

	public byte getSeconds() {
		long l = interval / 1000L;
		return (byte) l;
	}

	public long getMillis() {
		return interval;
	}

	public Observer<T> getSubscriber() {
		return subscriber;
	}

}
