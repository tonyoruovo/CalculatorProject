/**
 * 
 */
package mathaid.designpattern;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Date: 23 Apr 2021----------------------------------------------------------- 
 * Time created: 18:39:07---------------------------------------------------  
 * Package: mathaid.designpattern------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: ConcurrentRelation.java------------------------------------------------------ 
 * Class name: ConcurrentRelation------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class ConcurrentRelation<T> {

	@SuppressWarnings("unused")
	private static final class MyThreadFactory implements ThreadFactory {

		private final int threadPriority;
		private String name;

		MyThreadFactory(String name, int priority) {
			this.name = name;
			this.threadPriority = priority;
		}

		MyThreadFactory(int priority) {
			this(null, priority);
		}

		MyThreadFactory() {
			this(Thread.NORM_PRIORITY);
		}

		@Override
		public Thread newThread(Runnable r) {

			Thread t = new Thread(r, name == null ? "Thread-" + Thread.activeCount() + 1 : name);
			t.setPriority(threadPriority);
			t.setDaemon(false);

			return t;
		}

	}

	private static final class RunnableSubject<T> implements Runnable, Subject<T> {
		private final Subject<T> subject;
		private final int priority;
		static final Comparator<RunnableSubject<?>> COMPARATOR = (RunnableSubject<?> r1,
				RunnableSubject<?> r2) -> Integer.compare(r1.priority, r2.priority);

		RunnableSubject(Subject<T> subject, int priority) {
			this.subject = subject;
			this.priority = priority;
		}

		@Override
		public void run() {
			subject.update();
		}

		@Override
		public void register(Observer<T> o) {
			subject.register(o);
		}

		@Override
		public void unRegister(Observer<T> o) {
			subject.unRegister(o);
		}

		@Override
		public void update() {
			subject.update();
		}
	}

//	protected ConcurrentRelation(Subject<T> subject, int numOfThreads, int priority) {
//		this.subject = new RunnableSubject<>(subject);
//		threadPool = new ScheduledThreadPoolExecutor(numOfThreads, new MyThreadFactory(priority));
//		lock = new ReentrantLock();
//	}

//	public ConcurrentRelation(Subject<T> subject, int numOfThreads) {
//		this.subject = new RunnableSubject<>(subject);
//		threadPool = new ScheduledThreadPoolExecutor(numOfThreads, new MyThreadFactory());
//	}

	public ConcurrentRelation(int numOfSubjects, int numOfThreads) {
		subjects = new PriorityQueue<>(numOfSubjects, RunnableSubject.COMPARATOR);
		subjectThreadPool = new ScheduledThreadPoolExecutor(numOfThreads);
		lock = new ReentrantLock();
	}

	public void addSubject(Subject<T> s, int priority) {
		subjects.add(new RunnableSubject<>(s, priority));
	}

	public void removeSubject(Subject<T> s) {
		subjects.remove(s);
	}

	private ScheduledThreadPoolExecutor subjectThreadPool;
	private ReentrantLock lock;
	private PriorityQueue<RunnableSubject<T>> subjects;
//	private List<RunnableSubject<T>> subjects;
}
