/**
 * 
 */
package mathaid.calculator.base.util;

import static mathaid.calculator.base.util.Utility.f;
import static mathaid.calculator.base.util.Utility.i;

import java.math.BigInteger;
import java.util.Iterator;

import mathaid.calculator.base.value.BigFraction;

/**
 * A utility number class for implementation of figurate values As specified in <a
 * href="https://en.wikipedia.org/wiki/Figurate_number">this Wikipedia article</a> and also general elementary number theory.
 */
public class Figurate {

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:43:26 ---------------------------------------------------
	 */
	/**
	 * Checks if {@code a[i] < min || a[i] > max} (Where {@code i} the the current value of {@code a} being checked) and throws an
	 * {@code IllegalArgumentException} if it is <code>true</code>, using the arguments to construct the message. <p> {@code names},
	 * {@code classes} and {@code a} must have equal length.
	 * 
	 * @param min     the minimum value of any of {@code a}.
	 * @param max     the maximum value of any of {@code a}.
	 * @param names   the names of all the fields being checked. It is assumed that this is a parameter name of a method.
	 * @param classes the classes of {@code names} used for building the exception message
	 * @param a       the values to be checked.
	 * 
	 * @throws ArrayIndexOutOfBoundsException if {@code names.length != a.length}.
	 * @throws IllegalArgumentException       if {@code min > max} or any of {@code s} is either less than <code>min</code> or
	 *                                        greater than <code>max</code>.
	 */
	private static void iae(int min, int max, String[] names, Class<?>[] classes, int... a) {
		if (names.length != a.length)
			throw new ArrayIndexOutOfBoundsException();
		else if (min > max)
			throw new IllegalArgumentException();
		for (int i = 0; i < a.length; i++) {
			if (a[i] < min)
				throw new IllegalArgumentException(
						String.format("The %4$s argument %2$s of value %1$s was less than the minimum of %3$s", a[i], names[i], min,
								classes[i].getName()));
			else if (a[i] > max)
				throw new IllegalArgumentException(
						String.format("The %4$s argument %2$s of value %1$s was less than the maximum of %3$s", a[i], names[i], max,
								classes[i].getName()));
		}
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:59:36 ---------------------------------------------------
	 * Package: mathaid.calculator.base.util
	 * ------------------------------------------------ Project: CalculatorProject
	 * ------------------------------------------------ File: Figurate.java
	 * ------------------------------------------------------ Class name: Linear
	 * ------------------------------------------------
	 */
	/**
	 * An {@code Iterator} that iterates over the set of linear numbers, which are all members of the set &Nopf;
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * 
	 * @email tonyoruovo@gmail.com
	 */
	static class Linear implements Iterator<BigInteger> {
		/*
		 * Date: 27 Nov 2023 -----------------------------------------------------------
		 * Time created: 13:08:43 ---------------------------------------------------
		 */
		/**
		 * Constructor for creating a {@code mathaid.calculator.base.util.Linear} object.
		 * 
		 * @param start the position from which the iteration should begin.
		 */
		Linear(BigInteger start) {
			this.s = start;
		}

		/*
		 * Date: 27 Nov 2023 -----------------------------------------------------------
		 * Time created: 13:10:27 ---------------------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			return true;
		}

		/*
		 * Date: 27 Nov 2023 -----------------------------------------------------------
		 * Time created: 13:10:37 ---------------------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 */
		@Override
		public BigInteger next() {
			BigInteger rv = s;
			s = s.add(i(1));
			return rv;
		}

		/**
		 * Holds the current element, which will be used for computing the next iteration.
		 */
		private BigInteger s;
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 13:18:20 ---------------------------------------------------
	 */
	/**
	 * Creates a mathematically linear Figurate number from the given index. This gets the Figurate value mapped to the given index.
	 * <p> This is equivalent to calling <code>polygonal(1, index)</code>. <p> This is for linear Figurate numbers with the formula:
	 * <blockquote>
	 * 
	 * <pre>
	 * <span style="font-weight:bold"><span style="font-style:italic">P</span></span><span style="font-style:italic"><sub>2</sub></span> (index)
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param index the {@code index} within the linear Figurate set from which the value will be returned.
	 * 
	 * @return the linear Figurate value for the given index.
	 */
	public static BigInteger linear(BigInteger index) {
		return index;
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:39:56 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code Iterator} over the set of mathematically linear numbers by calling {@code getLinear(0)}.
	 * 
	 * @return an {@code Iterator} over the set of linear numbers starting from {@code 0}.
	 */
	public static Iterator<BigInteger> getLinear() {
		return getLinear(0);
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:43:37 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code Iterator} over a set of mathematically linear numbers where the first element of the set is the number at
	 * the index {@code start}.
	 * 
	 * @param start the index within the set where the iteration will start.
	 * 
	 * @return an {@code Iterator} over the set of linear numbers starting from {@code start}.
	 */
	public static Iterator<BigInteger> getLinear(int start) {
		return new Linear(i(start));
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:43:37 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code Iterator} over a set of mathematically linear numbers where the first element of the set is the number at
	 * the index {@code start}.
	 * 
	 * @param start the index within the set where the iteration will start.
	 * 
	 * @return an {@code Iterator} over the set of linear numbers starting from {@code start}.
	 */
	public static Iterator<BigInteger> getLinear(long start) {
		return new Linear(i(start));
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:43:37 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code Iterator} over a set of mathematically linear numbers where the first element of the set is the number at
	 * the index {@code start}.
	 * 
	 * @param start the index within the set where the iteration will start.
	 * 
	 * @return an {@code Iterator} over the set of linear numbers starting from {@code start}.
	 */
	public static Iterator<BigInteger> getLinear(BigInteger start) {
		return new Linear(start);
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 13:54:15 ---------------------------------------------------
	 * Package: mathaid.calculator.base.util ------------------------------------------------
	 * Project: CalculatorProject ------------------------------------------------
	 * File: Figurate.java ------------------------------------------------------
	 * Class name: Triangular ------------------------------------------------
	 */
	/**
	 * An {@code Iterator} that iterates over the set of Triangular numbers, which are all members of the set &Nopf;
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * 
	 * @email tonyoruovo@gmail.com
	 */
	static class Triangular implements Iterator<BigInteger> {
		/*
		 * Date: 27 Nov 2023 -----------------------------------------------------------
		 * Time created: 13:55:05 ---------------------------------------------------
		 */
		/**
		 * Constructor for creating a {@code mathaid.calculator.base.util.Triangular} object.
		 * 
		 * @param startIndex the position from which the iteration should begin.
		 */
		Triangular(BigInteger startIndex) {
			this.i = startIndex;
			s = triangular(i);
		}

		/*
		 * Date: 27 Nov 2023 -----------------------------------------------------------
		 * Time created: 13:56:49 ---------------------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			return true;
		}

		/*
		 * Date: 27 Nov 2023 -----------------------------------------------------------
		 * Time created: 13:57:01 ---------------------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 */
		@Override
		public BigInteger next() {
			BigInteger rv = s;
			i = i.add(i(1));
			s = triangular(i);
			return rv;
		}

		/**
		 * Holds the current element.
		 */
		private BigInteger s;
		/**
		 * Holds the current index, which will be used for computing the next iteration.
		 */
		private BigInteger i;
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 13:18:20 ---------------------------------------------------
	 */
	/**
	 * Creates a mathematically Triangular number from the given index. This gets the Triangular value mapped to the given index.
	 * <p> This is equivalent to calling <code>polygonal(2, index)</code>. <p> This is for triangular numbers with the formula:
	 * <blockquote>
	 * 
	 * <pre>
	 * <span style="font-weight:bold"><span style="font-style:italic">P</span></span><span style="font-style:italic"><sub>2</sub></span> (index)
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param index the {@code index} within the triangular set from which the value will be returned.
	 * 
	 * @return the triangular value for the given index.
	 */
	public static BigInteger triangular(BigInteger index) {
		return index.multiply(index.add(i(1))).divide(i(2));
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:39:56 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code Iterator} over a set of mathematically triangular numbers by calling {@code getTriangular(0)}.
	 * 
	 * @return an {@code Iterator} over the set of triangular numbers starting from {@code 0}.
	 */
	public static Iterator<BigInteger> getTriangular() {
		return getTriangular(0);
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:43:37 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code Iterator} over a set of mathematically triangular numbers where the first element of the set is the number
	 * at the index {@code start}.
	 * 
	 * @param start the index within the set from where the iteration will begin.
	 * 
	 * @return an {@code Iterator} over the set of triangular numbers starting from {@code start}.
	 */
	public static Iterator<BigInteger> getTriangular(int start) {
		return new Triangular(i(start));
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:43:37 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code Iterator} over a set of mathematically triangular numbers where the first element of the set is the number
	 * at the index {@code start}.
	 * 
	 * @param start the index within the set from where the iteration will begin.
	 * 
	 * @return an {@code Iterator} over the set of triangular numbers starting from {@code start}.
	 */
	public static Iterator<BigInteger> getTriangular(long start) {
		return new Triangular(i(start));
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:43:37 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code Iterator} over a set of mathematically triangular numbers where the first element of the set is the number
	 * at the index {@code start}.
	 * 
	 * @param start the index within the set from where the iteration will begin.
	 * 
	 * @return an {@code Iterator} over the set of triangular numbers starting from {@code start}.
	 */
	public static Iterator<BigInteger> getTriangular(BigInteger start) {
		return new Triangular(start);
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:06:04 ---------------------------------------------------
	 */
	/**
	 * Retrieves the index of the given triangular value.
	 * 
	 * @param value the value mapped to the returned index.
	 * 
	 * @return the index mapped to the triangular argument as an {@code int}
	 */
	public static int triangularIntIndex(BigInteger value) {
		//		return i(8).multiply(value).add(i(1)).sqrt().subtract(i(1)).divide(i(2)).intValue();
		return (int) (Math.pow((8 * value.intValueExact()) + 1, 0.5) - 1) / 2;
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:06:04 ---------------------------------------------------
	 */
	/**
	 * Retrieves the index of the given triangular value.
	 * 
	 * @param value the value mapped to the returned index.
	 * 
	 * @return the index mapped to the triangular argument as a {@code long}
	 */
	public static long triangularLongIndex(BigInteger value) {
		//		return i(8).multiply(value).add(i(1)).sqrt().subtract(i(1)).divide(i(2)).longValue();
		return (long) (Math.pow((8L * value.longValueExact()) + 1L, 0.5) - 1L) / 2L;
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:06:04 ---------------------------------------------------
	 */
	/**
	 * Retrieves the index of the given triangular value.
	 * 
	 * @param value the value mapped to the returned index.
	 * 
	 * @return the index mapped to the triangular argument.
	 */
	public static BigInteger triangularIndex(BigInteger value) {
		return i(8).multiply(value).add(i(1)).sqrt().subtract(i(1)).divide(i(2));
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 13:58:44 ---------------------------------------------------
	 * Package: mathaid.calculator.base.util ------------------------------------------------
	 * Project: CalculatorProject ------------------------------------------------
	 * File: Figurate.java ------------------------------------------------------
	 * Class name: Tetrahedral ------------------------------------------------
	 */
	/**
	 * An {@code Iterator} that iterates over the set of tetrahedral numbers, which are all members of the set &Nopf;
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * 
	 * @email tonyoruovo@gmail.com
	 */
	static class Tetrahedral implements Iterator<BigInteger> {
		/*
		 * Date: 27 Nov 2023 -----------------------------------------------------------
		 * Time created: 13:59:43 ---------------------------------------------------
		 */
		/**
		 * Constructor for creating a {@code mathaid.calculator.base.util.Tetrahedral} object.
		 * 
		 * @param startIndex the position from which the iteration should begin.
		 */
		Tetrahedral(BigInteger startIndex) {
			this.i = startIndex;
			s = tetrahedral(i);
		}

		/*
		 * Date: 27 Nov 2023 -----------------------------------------------------------
		 * Time created: 14:00:24 ---------------------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			return true;
		}

		/*
		 * Date: 27 Nov 2023 -----------------------------------------------------------
		 * Time created: 14:00:33 ---------------------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 */
		@Override
		public BigInteger next() {
			BigInteger rv = s;
			i = i.add(i(1));
			s = tetrahedral(i);
			return rv;
		}

		/**
		 * Holds the current element.
		 */
		private BigInteger s;
		/**
		 * Holds the current index, which will be used for computing the next iteration.
		 */
		private BigInteger i;
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 13:18:20 ---------------------------------------------------
	 */
	/**
	 * Creates a mathematically Tetrahedral number from the given index. This gets the tetrahedral value mapped to the given index.
	 * <p> This is equivalent to calling <code>polygonal(3, index)</code>. <p> This is for tetrahedral numbers with the formula:
	 * <blockquote>
	 * 
	 * <pre>
	 * <span style="font-weight:bold"><span style="font-style:italic">P</span></span><span style="font-style:italic"><sub>3</sub></span> (index)
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param index the {@code index} within the tetrahedral set from which the value will be returned.
	 * 
	 * @return the tetrahedral value for the given index.
	 */
	public static BigInteger tetrahedral(BigInteger index) {
		return index.add(i(1)).multiply(index.add(i(2))).multiply(index).divide(i(6));
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:39:56 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code Iterator} over a set of mathematically tetrahedral numbers by calling {@code getTetrahedral(0)}.
	 * 
	 * @return an {@code Iterator} over the set of tetrahedral numbers starting from {@code 0}.
	 */
	public static Iterator<BigInteger> getTetrahedral() {
		return getTetrahedral(0);
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:43:37 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code Iterator} over a set of mathematically tetrahedral numbers where the first element of the set is the number
	 * at the index {@code start}.
	 * 
	 * @param start the index within the set from where the iteration will begin.
	 * 
	 * @return an {@code Iterator} over the set of tetrahedral numbers starting from {@code start}.
	 */
	public static Iterator<BigInteger> getTetrahedral(int start) {
		return new Tetrahedral(i(start));
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:43:37 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code Iterator} over a set of mathematically tetrahedral numbers where the first element of the set is the number
	 * at the index {@code start}.
	 * 
	 * @param start the index within the set from where the iteration will begin.
	 * 
	 * @return an {@code Iterator} over the set of tetrahedral numbers starting from {@code start}.
	 */
	public static Iterator<BigInteger> getTetrahedral(long start) {
		return new Tetrahedral(i(start));
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:43:37 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code Iterator} over a set of mathematically tetrahedral numbers where the first element of the set is the number
	 * at the index {@code start}.
	 * 
	 * @param start the index within the set from where the iteration will begin.
	 * 
	 * @return an {@code Iterator} over the set of tetrahedral numbers starting from {@code start}.
	 */
	public static Iterator<BigInteger> getTetrahedral(BigInteger start) {
		return new Tetrahedral(start);
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:06:04 ---------------------------------------------------
	 */
	/**
	 * Retrieves the index of the given tetrahedral value.
	 * 
	 * @param value the value mapped to the returned index.
	 * 
	 * @return the index mapped to the tetrahedral argument as an {@code int}
	 */
	public static int tetrahedralIntIndex(BigInteger value) {
		return tetrahedralIndex(value).intValue();
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:06:04 ---------------------------------------------------
	 */
	/**
	 * Retrieves the index of the given tetrahedral value.
	 * 
	 * @param value the value mapped to the returned index.
	 * 
	 * @return the index mapped to the tetrahedral argument as a {@code long}
	 */
	public static long tetrahedralLongIndex(BigInteger value) {
		return tetrahedralIndex(value).longValue();
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:06:04 ---------------------------------------------------
	 */
	/**
	 * Retrieves the index of the given tetrahedral value.
	 * 
	 * @param value the value mapped to the returned index.
	 * 
	 * @return the index mapped to the tetrahedral argument.
	 */
	public static BigInteger tetrahedralIndex(BigInteger value) {
		BigFraction l = f(3).multiply(f(value)).add(f(9).multiply(f(value).exponentiate(2)).subtract(f(1, 27)));
		BigFraction r = f(3).multiply(f(value)).subtract(f(9).multiply(f(value).exponentiate(2)).subtract(f(1, 27)));
		r = r.exponentiate(f(1, 3));
		l = l.exponentiate(f(1, 3));
		return l.add(r).subtract(1).getFraction().toBigInteger();
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:04:03 ---------------------------------------------------
	 * Package: mathaid.calculator.base.util ------------------------------------------------
	 * Project: CalculatorProject ------------------------------------------------
	 * File: Figurate.java ------------------------------------------------------
	 * Class name: Pentatopic ------------------------------------------------
	 */
	/**
	 * An {@code Iterator} that iterates over the set of Pentatopic numbers, which are all members of the set &Nopf;
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * 
	 * @email tonyoruovo@gmail.com
	 */
	static class Pentatopic implements Iterator<BigInteger> {
		/*
		 * Date: 27 Nov 2023 -----------------------------------------------------------
		 * Time created: 14:28:00 ---------------------------------------------------
		 */
		/**
		 * Constructor for creating a {@code mathaid.calculator.base.util.Pentatopic} object.
		 * 
		 * @param startIndex the position from which the iteration should begin.
		 */
		Pentatopic(BigInteger startIndex) {
			this.i = startIndex;
			s = pentatopic(i);
		}

		/*
		 * Date: 27 Nov 2023 -----------------------------------------------------------
		 * Time created: 14:29:13 ---------------------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			return true;
		}

		/*
		 * Date: 27 Nov 2023 -----------------------------------------------------------
		 * Time created: 14:31:32 ---------------------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 */
		@Override
		public BigInteger next() {
			BigInteger rv = s;
			i = i.add(i(1));
			s = pentatopic(i);
			return rv;
		}

		/**
		 * Holds the current element.
		 */
		private BigInteger s;
		/**
		 * Holds the current index, which will be used for computing the next iteration.
		 */
		private BigInteger i;
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 13:18:20 ---------------------------------------------------
	 */
	/**
	 * Creates a mathematically Pentatopic/Pentachoric number from the given index. This gets the pentatopic value mapped to the
	 * given index. <p> This is equivalent to calling <code>polygonal(4, index)</code>. <p> This is for pentatopic numbers with the
	 * formula: <blockquote>
	 * 
	 * <pre>
	 * <span style="font-weight:bold"><span style="font-style:italic">P</span></span><span style="font-style:italic"><sub>4</sub></span> (index)
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param index the {@code index} within the pentatopic set from which the value will be returned.
	 * 
	 * @return the pentatopic value for the given index.
	 */
	public static BigInteger pentatopic(BigInteger index) {
		return index.add(i(1)).multiply(index.add(i(2))).multiply(index.add(i(3))).multiply(index).divide(i(24));
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:39:56 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code Iterator} over a set of mathematically pentatopic numbers by calling {@code getPentatopic(0)}.
	 * 
	 * @return an {@code Iterator} over the set of pentatopic numbers starting from {@code 0}.
	 */
	public static Iterator<BigInteger> getPentatopic() {
		return getPentatopic(0);
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:43:37 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code Iterator} over a set of mathematically pentatopic numbers where the first element of the set is the number
	 * at the index {@code start}.
	 * 
	 * @param start the index within the set from where the iteration will begin.
	 * 
	 * @return an {@code Iterator} over the set of pentatopic numbers starting from {@code start}.
	 */
	public static Iterator<BigInteger> getPentatopic(int start) {
		return new Pentatopic(i(start));
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:43:37 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code Iterator} over a set of mathematically pentatopic numbers where the first element of the set is the number
	 * at the index {@code start}.
	 * 
	 * @param start the index within the set from where the iteration will begin.
	 * 
	 * @return an {@code Iterator} over the set of pentatopic numbers starting from {@code start}.
	 */
	public static Iterator<BigInteger> getPentatopic(long start) {
		return new Pentatopic(i(start));
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:43:37 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code Iterator} over a set of mathematically pentatopic numbers where the first element of the set is the number
	 * at the index {@code start}.
	 * 
	 * @param start the index within the set from where the iteration will begin.
	 * 
	 * @return an {@code Iterator} over the set of pentatopic numbers starting from {@code start}.
	 */
	public static Iterator<BigInteger> getPentatopic(BigInteger start) {
		return new Pentatopic(start);
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:06:04 ---------------------------------------------------
	 */
	/**
	 * Retrieves the index of the given pentatopic value.
	 * 
	 * @param value the value mapped to the returned index.
	 * 
	 * @return the index mapped to the pentatopic argument as an {@code int}
	 */
	public static int pentatopicIntIndex(BigInteger value) {
		return pentatopicIndex(value).intValue();
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:06:04 ---------------------------------------------------
	 */
	/**
	 * Retrieves the index of the given pentatopic value.
	 * 
	 * @param value the value mapped to the returned index.
	 * 
	 * @return the index mapped to the pentatopic argument as a {@code long}
	 */
	public static long pentatopicLongIndex(BigInteger value) {
		return pentatopicIndex(value).longValue();
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:06:04 ---------------------------------------------------
	 */
	/**
	 * Retrieves the index of the given pentatopic value.
	 * 
	 * @param value the value mapped to the returned index.
	 * 
	 * @return the index mapped to the pentatopic argument.
	 */
	public static BigInteger pentatopicIndex(BigInteger value) {
		return i(24).multiply(value).add(i(1)).sqrt().multiply(i(4)).add(i(5)).sqrt().subtract(i(3)).divide(i(2));
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:31:50 ---------------------------------------------------
	 * Package: mathaid.calculator.base.util ------------------------------------------------
	 * Project: CalculatorProject ------------------------------------------------
	 * File: Figurate.java ------------------------------------------------------
	 * Class name: Polygonal ------------------------------------------------
	 */
	/**
	 * An {@code Iterator} that iterates over the set of <span style="font-style:italic">r</span>-topic numbers, which are all
	 * members of the set &Nopf;
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * 
	 * @email tonyoruovo@gmail.com
	 */
	static class Polygonal implements Iterator<BigInteger> {
		/*
		 * Date: 27 Nov 2023 -----------------------------------------------------------
		 * Time created: 14:33:00 ---------------------------------------------------
		 */
		/**
		 * Constructor for creating a {@code mathaid.calculator.base.util.Polygonal} object by describing the vertices of this <span
		 * style="font-style:italic">r</span>-simplex number.
		 * 
		 * @param faces      the vertices - 1 of this Polygonal number. Use {@code 1} for linear numbers, {@code 2} for triangular
		 *                   numbers, 3 for tetrahedral numbers and so on.
		 * @param startIndex the position from which the iteration should begin.
		 */
		Polygonal(int faces, BigInteger startIndex) {
			this.i = startIndex;
			this.faces = faces;
			this.s = polygonal(faces, i);
		}

		/*
		 * Date: 27 Nov 2023 -----------------------------------------------------------
		 * Time created: 14:37:06 ---------------------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			return true;
		}

		/*
		 * Date: 27 Nov 2023 -----------------------------------------------------------
		 * Time created: 14:37:13 ---------------------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 */
		@Override
		public BigInteger next() {
			BigInteger rv = s;
			i = i.add(i(1));
			s = polygonal(faces, i);
			return rv;
		}

		/**
		 * Holds the current element.
		 */
		private BigInteger s;
		/**
		 * Holds the current index, which will be used for computing the next iteration.
		 */
		private BigInteger i;
		/**
		 * The number of vertices of the given polygonal number.
		 */
		private final int faces;
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 13:18:20 ---------------------------------------------------
	 */
	/**
	 * Creates a mathematically Polygonal number from the given index. This gets the value in the set (whose vertices are specified
	 * by {@code faces}) mapped to the given {@code index}. <p> This is for polygonal numbers with the formula: <blockquote>
	 * 
	 * <pre>
	 * <span style="font-weight:bold"><span style="font-style:italic">P</span></span><span style="font-style:italic"><sub>faces</sub></span> (index)
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param index the {@code index} within the given polygonal set (whose vertices are specified by {@code faces}) from which the
	 *              value will be returned.
	 * 
	 * @return the polygonal value for the given index.
	 */
	public static BigInteger polygonal(int faces, BigInteger index) {
		iae(1, Integer.MAX_VALUE, new String[] { "faces" }, new Class<?>[] { int.class }, faces);
		faces -= 1;
		if (faces == 0)
			return i(0);
		BigInteger ans = index;
		for (int i = 1; i < faces; i++) {
			ans = index.add(i(i)).multiply(ans);
		}
		return ans.divide(Arith.factorial(i(faces)));
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:39:56 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code Iterator} over a set of mathematically <span style="font-style:italic">r</span>-topic numbers with the
	 * given number of vertices by calling {@code getPolygonal(faces, 0)}.
	 * 
	 * @param faces the number of vertices in the set of polygonal numbers to be returned. Use {@code 1} for linear, {@code 2} for
	 *              triangular, {@code 3} for tetrahedral etc.
	 * 
	 * @return an {@code Iterator} over the set of <span style="font-style:italic">r</span>-topic numbers starting from {@code 0}.
	 */
	public static Iterator<BigInteger> getPolygonal(int faces) {
		return new Polygonal(faces, i(0));
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:43:37 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code Iterator} over a set of mathematically <span style="font-style:italic">r</span>-topic numbers where the
	 * first element of the set is the number at the index {@code start}. <p>This Set has as many vertices as {@code faces}.
	 * 
	 * @param faces the number of vertices in the Set over which the {@code Iterator} to be returned will iterate.
	 * @param start the index within the set from where the iteration will begin.
	 * 
	 * @return an {@code Iterator} over the set of <span style="font-style:italic">r</span>-topic numbers starting from
	 *         {@code start}.
	 */
	public static Iterator<BigInteger> getPolygonal(int faces, int start) {
		return new Polygonal(faces, i(start));
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:43:37 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code Iterator} over a set of mathematically <span style="font-style:italic">r</span>-topic numbers where the
	 * first element of the set is the number at the index {@code start}. <p>This Set has as many vertices as {@code faces}.
	 * 
	 * @param faces the number of vertices in the Set over which the {@code Iterator} to be returned will iterate.
	 * @param start the index within the set from where the iteration will begin.
	 * 
	 * @return an {@code Iterator} over the set of <span style="font-style:italic">r</span>-topic numbers starting from
	 *         {@code start}.
	 */
	public static Iterator<BigInteger> getPolygonal(int faces, long start) {
		return new Polygonal(faces, i(start));
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:43:37 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code Iterator} over a set of mathematically <span style="font-style:italic">r</span>-topic numbers where the
	 * first element of the set is the number at the index {@code start}. <p>This Set has as many vertices as {@code faces}.
	 * 
	 * @param faces the number of vertices in the Set over which the {@code Iterator} to be returned will iterate.
	 * @param start the index within the set from where the iteration will begin.
	 * 
	 * @return an {@code Iterator} over the set of <span style="font-style:italic">r</span>-topic numbers starting from
	 *         {@code start}.
	 */
	public static Iterator<BigInteger> getPolygonal(int faces, BigInteger start) {
		return new Polygonal(faces, start);
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 15:15:12 ---------------------------------------------------
	 */
	/**
	 * Gets the perfect number associated with the given prime. <p> A perfect number is a number that has the sum of its factors
	 * equal to itself. It's factors are the set of numbers that can integrally divide it, however this number itself is excluded
	 * from this set. This result of this sum is called the <span style="font-style:italic">aliquot sum</span> . For example 6 has
	 * divisors 1, 2, 3 which when summed equals 6, hence 6 is a perfect number.<p>They are usually composite numbers, hence cannot
	 * possibly be prime. However, an un-answered question in mathematics is whether there exists any perfect odd numbers, as the
	 * known ones are all even.
	 * 
	 * @param prime a prime number as an {@code int} representing the index of the returned prefect even number in the perfect even
	 *              numbers Set.
	 * 
	 * @return <code>2<sup>prime - 1</sup>(2<sup>p</sup> - 1)</code>.
	 */
	public static BigInteger getEvenPerfectNum(int prime) {
		return i(2).pow(prime - 1).multiply(i(2).pow(prime).subtract(i(1)));
	}

	/*
	 * Date: 27 Nov 2023 -----------------------------------------------------------
	 * Time created: 13:11:01 ---------------------------------------------------
	 */
	/**
	 * Private constructor for creating a {@code mathaid.calculator.base.util.Figurate} object. <span style="font-weight:bold"><span
	 * style="font-style:italic">This is un-instantiable</span></span>.
	 */
	private Figurate() {
	}
}