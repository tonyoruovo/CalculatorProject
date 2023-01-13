/**
 * 
 */
package mathaid.designpattern;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Date: 3 Apr 2021----------------------------------------------------------- 
 * Time created: 11:53:16---------------------------------------------------  
 * Package: mathaid.calculator.base------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: AsynchronuousObserverUpdater.java------------------------------------------------------ 
 * Class name: AsynchronuousObserverUpdater------------------------------------------------ 
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
public class AsynchronuousObserverUpdater implements Runnable {
	private final Subject<?> subject;
	private ReentrantLock lock;
	private volatile long interval;
	private final long initial;
	private AtomicBoolean initialTime = new AtomicBoolean(true);

	protected AsynchronuousObserverUpdater(Subject<?> subject, ReentrantLock lock) {
		this(subject, (byte) 2, (byte) 1, lock);
	}

	public AsynchronuousObserverUpdater(Subject<?> subject, byte initialInSeconds, byte intervalInSeconds,
			ReentrantLock lock) {
		this(subject, initialInSeconds * 1000L, intervalInSeconds * 1000L, lock);
	}

	protected AsynchronuousObserverUpdater(Subject<?> subject, long initial, long interval, ReentrantLock lock) {
		this.subject = subject;
		this.interval = interval;
		this.lock = lock;
		this.initial = initial;
	}

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
//				lock.lock();
				subject.update();
				elapsed = System.currentTimeMillis();
//				lock.unlock();
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

	public Subject<?> getSubject() {
		return subject;
	}

}
