/**
 * 
 */
package mathaid.designpattern;

import java.util.Timer;
import java.util.TimerTask;

/*
 * Date: 7 Apr 2021----------------------------------------------------------- 
 * Time created: 09:32:23---------------------------------------------------  
 * Package: mathaid.calculator.base------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: AsynchronuousThreadHolder.java------------------------------------------------------ 
 * Class name: AsynchronuousThreadHolder------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class AsynchronuousThreadHolder {

	/*
	 * Date: 7 Apr 2021-----------------------------------------------------------
	 * Time created: 09:32:23---------------------------------------------------
	 */
	/**
	 */
	public AsynchronuousThreadHolder(int howMany) {
		threads = new Timer[howMany];
		current = 0;
	}

	public void scheduleFixedInterval(Runnable runnable, long initialDelay, long subsequentInterval) {
		threads[current] = new Timer();
		threads[current].schedule(new TimerTask() {
			@Override
			public void run() {
				runnable.run();
			}
		}, initialDelay, subsequentInterval);
		current += 1;
	}

	public void scheduleFixedRate(Runnable runnable, long initialDelay, long subsequentInterval) {
		threads[current] = new Timer();
		threads[current].scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				runnable.run();
			}
		}, initialDelay, subsequentInterval);
	}

	public int getHolderSize() {
		return threads.length;
	}

	public void executeNow(Runnable r) {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				r.run();
			}
		}, 0);
	}

	public void cancelAll() {
		for (Timer t : threads)
			t.cancel();
	}

	private final Timer[] threads;
	private int current;

}
