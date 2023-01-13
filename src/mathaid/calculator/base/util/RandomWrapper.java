/**
 * 
 */
package mathaid.calculator.base.util;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/*
 * Date: 18 Jun 2020----------------------------------------------------------- 
 * Time created: 10:46:14---------------------------------------------------  
 * Package: mathaid.calculator.base.expression.concurrent------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: Randoms.java------------------------------------------------------ 
 * Class name: Randoms------------------------------------------------ 
 */
/**
 * A mutable fast random generator wrapper. Every method from the hashcode to
 * the toString() method is randomised. The only non-randomised methods are
 * equals() and clone()
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class RandomWrapper implements Cloneable, Serializable, AutoCloseable {

	/*
	 * Date: 18 Jun 2020-----------------------------------------------------------
	 * Time created: 14:18:14---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base.expression.concurrent--------------------------------
	 * ---------------- Project:
	 * LatestPoject2------------------------------------------------ File:
	 * RandomWrapper.java------------------------------------------------------
	 * Class name: RandomFactory------------------------------------------------
	 */
	/**
	 * @author Oruovo Anthony Etineakpopha
	 *
	 */
	public static class RandomFactory {
		private static ArrayDeque<RandomWrapper> randomsList;
		private static final int THRESHOLD = 0x2625A0;

		private RandomFactory() {
		}

		public static RandomWrapper create() {
			return create(5);
		}

		public static Collection<RandomWrapper> getAssemblyLineList() {
			if (randomsList == null)
				return new ArrayDeque<RandomWrapper>();
			return randomsList;
		}

		/*
		 * Date: 18 Jun 2020-----------------------------------------------------------
		 * Time created: 12:05:51--------------------------------------------
		 */
		/**
		 * @param i
		 * @return
		 */
		public static RandomWrapper create(int i) {
			Random r = new Random(i);
//			System.out.println(i);
			if (i > THRESHOLD)
				i = THRESHOLD;
			if (randomsList == null) {
				randomsList = new ArrayDeque<>();
				randomsList.push(new RandomWrapper(i ^ new RandomFactory().hashCode()));

				randomsList.push(new RandomWrapper(i));
				return randomsList.pop();
			}
			if (randomsList.isEmpty())
				for (int j = i | i ^ i; j < (i & i); j++, --i)// TODO: delete line
					randomsList.push(new RandomWrapper(i ^ new RandomFactory().hashCode()));
			RandomWrapper[] a = randomsList.toArray(new RandomWrapper[] { null });
			List<RandomWrapper> l = Arrays.asList(a);
			Collections.shuffle(l);
			randomsList = new ArrayDeque<RandomWrapper>(l);

			RandomWrapper rs;
			if (randomsList.peek().length == i)
				rs = randomsList.pop();
			else if (randomsList.peekFirst().length == i)
				rs = randomsList.pollLast();
			else {
				randomsList.push(new RandomWrapper(i));
				rs = randomsList.pollFirst();
				randomsList.push(new RandomWrapper(i ^ new RandomFactory().hashCode()));
			}
			r = new Random();
			rs.current = r.nextInt(rs.length + 1);
			rs.open();
			rs.close();
			rs.firstTime = false;
			randomsList.push(new RandomWrapper(i & new RandomFactory().hashCode() ^ i));
			return rs;
		}
	}

	/*
	 * Date: 18 Jun 2020-----------------------------------------------------------
	 * Time created: 10:47:20---------------------------------------------------
	 */
	/**
	 * Field for
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The current index of the random stream. This is where the cursor is
	 */
	private int current;
	/* For memory constraints choose a lesser number */
	private final int length;
	/* Arrays for switching between random objects */
	private Random[] randoms;
	private boolean firstTime;

	private static final String ERR_MSG = "stream is in a closed state";

	private RandomWrapper(int length) {
		if (length > RandomFactory.THRESHOLD)
			this.length = RandomFactory.THRESHOLD;
		else
			this.length = Math.abs(length);
	}

	public RandomWrapper() {
		this(5);
	}

	private RandomWrapper(RandomWrapper r) {
		current = r.current;
		length = r.length;
		randoms = r.randoms;
//		for (int i = 0; i < length; i++)
//			randoms[i] = r.randoms[i];
	}
	
	public int size() {
		return length;
	}
	
	public int getCurrent() {
		return current;
	}

	public RandomWrapper open() {
		throwException(!isClosed(), "stream is already open!");
		randoms = new Random[this.length];
		for (int i = 0; i < randoms.length; i++)
			randoms[i] = new Random(new Random().nextInt());

		return this;
		// return shuffle();
	}

	private final void updateIndex() {
		Random r = new Random(System.nanoTime());
		int temp = 0;// default value!
		while ((temp = r.nextInt(length)) == current)
			;
		if (!firstTime)
			current = temp;
	}

	@Override
	public void close() {// closes the random stream
		if (isClosed())
			return;
		for (int i = 0; i < randoms.length; i++)
			randoms[i] = null;
		randoms = null;
	}

	public RandomWrapper shuffle() {
		Collections.shuffle(Arrays.asList(randoms), new Random(length));
		return this;
	}

	private void throwException(boolean condition, String message) {
		if (condition)
			throw new IllegalStateException(message);
	}

	/*
	 * Date: 18 Jun 2020-----------------------------------------------------------
	 * Time created: 11:42:58--------------------------------------------
	 */
	/**
	 * @return
	 */
	public boolean isClosed() {
		return randoms == null;
	}

	public synchronized void setSeed(long seed) {
//		for(Random r : randoms)
//			r.setSeed(seed);
		throwException(isClosed(), "Cannot set seed, ".concat(ERR_MSG));
		updateIndex();
		randoms[current].setSeed(seed);
	}

	/**
	 * @param bits  
	 */
	protected int next(int bits) {
		throwException(isClosed(), ERR_MSG);
		updateIndex();
		return randoms[current].nextInt();
	}

	public void nextBytes(byte[] bytes) {
		throwException(isClosed(), "cannot get bytes, ".concat(ERR_MSG));
		updateIndex();
		randoms[current].nextBytes(bytes);
	}

	public int nextInt() {
		throwException(isClosed(), ERR_MSG);
		updateIndex();
		return randoms[current].nextInt();
	}

	public int nextInt(int bound) {
		throwException(isClosed(), ERR_MSG);
		updateIndex();
		return randoms[current].nextInt(bound);
	}

	public long nextLong() {
		throwException(isClosed(), ERR_MSG);
		updateIndex();
		return randoms[current].nextLong();
	}

	public boolean nextBoolean() {
		throwException(isClosed(), ERR_MSG);
		updateIndex();
		return randoms[current].nextBoolean();
	}

	public float nextFloat() {
		throwException(isClosed(), ERR_MSG);
		updateIndex();
		return randoms[current].nextFloat();
	}

	public double nextDouble() {
		throwException(isClosed(), ERR_MSG);
		updateIndex();
		return randoms[current].nextDouble();
	}

	public synchronized double nextGaussian() {
		throwException(isClosed(), ERR_MSG);
		updateIndex();
		return randoms[current].nextGaussian();
	}

	public IntStream ints(long streamSize) {
		throwException(isClosed(), ERR_MSG);
		updateIndex();
		return randoms[current].ints(streamSize);
	}

	public IntStream ints() {
		throwException(isClosed(), ERR_MSG);
		updateIndex();
		return randoms[current].ints();
	}

	public IntStream ints(long streamSize, int randomNumberOrigin, int randomNumberBound) {
		throwException(isClosed(), ERR_MSG);
		updateIndex();
		return randoms[current].ints(streamSize, randomNumberOrigin, randomNumberBound);
	}

	public IntStream ints(int randomNumberOrigin, int randomNumberBound) {
		throwException(isClosed(), ERR_MSG);
		updateIndex();
		return randoms[current].ints(randomNumberOrigin, randomNumberBound);
	}

	public LongStream longs(long streamSize) {
		throwException(isClosed(), ERR_MSG);
		updateIndex();
		return randoms[current].longs(streamSize);
	}

	public LongStream longs() {
		throwException(isClosed(), ERR_MSG);
		updateIndex();
		return randoms[current].longs();
	}

	public LongStream longs(long streamSize, long randomNumberOrigin, long randomNumberBound) {
		throwException(isClosed(), ERR_MSG);
		updateIndex();
		return randoms[current].longs(streamSize, randomNumberOrigin, randomNumberBound);
	}

	public LongStream longs(long randomNumberOrigin, long randomNumberBound) {
		throwException(isClosed(), ERR_MSG);
		updateIndex();
		return randoms[current].longs(randomNumberOrigin, randomNumberBound);
	}

	public DoubleStream doubles(long streamSize) {
		throwException(isClosed(), ERR_MSG);
		updateIndex();
		return randoms[current].doubles(streamSize);
	}

	public DoubleStream doubles() {
		throwException(isClosed(), ERR_MSG);
		updateIndex();
		return randoms[current].doubles();
	}

	public DoubleStream doubles(long streamSize, double randomNumberOrigin, double randomNumberBound) {
		throwException(isClosed(), ERR_MSG);
		updateIndex();
		return randoms[current].doubles(streamSize, randomNumberOrigin, randomNumberBound);
	}

	public DoubleStream doubles(double randomNumberOrigin, double randomNumberBound) {
		throwException(isClosed(), ERR_MSG);
		updateIndex();
		return randoms[current].doubles(randomNumberOrigin, randomNumberBound);
	}

	@Override
	public int hashCode() {
		throwException(isClosed(), ERR_MSG);
		updateIndex();
		return randoms[current].hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RandomWrapper))
			return false;
		RandomWrapper r = (RandomWrapper) obj;
		boolean equals = true;
		if (isClosed())
			equals = randoms == r.randoms;
		else
			for (int i = 0; i < length; i++) {
				equals = randoms[i].equals(obj);
				if (equals == false)
					break;
			}
		return current == r.current && length == r.length && randoms == r.randoms && equals && firstTime;
	}

	@Override
	public RandomWrapper clone() throws CloneNotSupportedException {
		return new RandomWrapper(this);
	}

	@Override
	public String toString() {
		if (!isClosed()) {
			updateIndex();
			randoms[current].toString();
		}
		return "current: " + current + ", length: " + length + ", firstTime: " + firstTime;
	}
}
