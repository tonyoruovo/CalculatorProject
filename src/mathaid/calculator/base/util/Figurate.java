/**
 * 
 */
package mathaid.calculator.base.util;

import java.math.BigInteger;
import java.util.Iterator;

import mathaid.calculator.base.value.BigFraction;

/**
 * A utility number class for implementation of figurate values
 * As specified in <a href="https://en.wikipedia.org/wiki/Figurate_number">this Wikipedia article</a>
 */
public class Figurate {

	private static void iae(int min, int max, String[] names, Class<?>[] classes, int... a) {
		if(names.length != a.length)
			throw new ArrayIndexOutOfBoundsException();
		else if (min > max)
			throw new IllegalArgumentException();	
		for(int i = 0; i < a.length; i++) {
			if(a[i] < min)
				throw new IllegalArgumentException(
					String.format("The %4$s argument %2$s of value %1$s was less than the minimum of %3$s", a[i], names[i], min, classes[i].getName()));
			else if(a[i] > max)
				throw new IllegalArgumentException(
					String.format("The %4$s argument %2$s of value %1$s was less than the maximum of %3$s", a[i], names[i], max, classes[i].getName()));
		}
	}

	static BigInteger i(int i) {
		return BigInteger.valueOf(i);
	}

	static BigInteger i(long i) {
		return BigInteger.valueOf(i);
	}

	static BigFraction f(int i) {
		return new BigFraction(i(i), i(1));
	}

	static BigFraction f(long i) {
		return new BigFraction(i(i), i(1L));
	}

	static BigFraction f(BigInteger i) {
		return new BigFraction(i, i(1L));
	}

	static BigFraction f(long n, long d) {
		return new BigFraction(i(n), i(d));
	}

	static class Linear implements Iterator<BigInteger> {
		Linear(BigInteger start) { this.s = start; }
		@Override
		public boolean hasNext() { return true; }
		@Override
		public BigInteger next() {
			BigInteger rv = s;
			s = s.add(i(1));
			return rv;
		}
		private BigInteger s;
	}

	public static BigInteger linear(BigInteger index) {
		return index;
	}

	public static Iterator<BigInteger> getLinear() {
		return getLinear(0);
	}

	public static Iterator<BigInteger> getLinear(int start) {
		return new Linear(i(start));
	}

	public static Iterator<BigInteger> getLinear(long start) {
		return new Linear(i(start));
	}

	public static Iterator<BigInteger> getLinear(BigInteger start) {
		return new Linear(start);
	}

	static class Triangular implements Iterator<BigInteger> {
		Triangular(BigInteger startIndex) { this.i = startIndex; s = triangular(i); }
		@Override
		public boolean hasNext() { return true; }
		@Override
		public BigInteger next() {
			BigInteger rv = s;
			i = i.add(i(1));
			s = triangular(i);
			return rv;
		}
		private BigInteger s;
		private BigInteger i;
	}

	public static BigInteger triangular(BigInteger index) {
		return index.multiply(index.add(i(1))).divide(i(2));
	}

	public static Iterator<BigInteger> getTriangular() {
		return getTriangular(0);
	}

	public static Iterator<BigInteger> getTriangular(int start) {
		return new Triangular(i(start));
	}

	public static Iterator<BigInteger> getTriangular(long start) {
		return new Triangular(i(start));
	}

	public static Iterator<BigInteger> getTriangular(BigInteger start) {
		return new Triangular(start);
	}

	public static int triangularIntIndex(BigInteger value) {
		return i(8).multiply(value).add(i(1)).sqrt().subtract(i(1)).divide(i(2)).intValue();
	}

	public static long triangularLongIndex(BigInteger value) {
		return i(8).multiply(value).add(i(1)).sqrt().subtract(i(1)).divide(i(2)).longValue();
	}

	public static BigInteger triangularIndex(BigInteger value) {
		return i(8).multiply(value).add(i(1)).sqrt().subtract(i(1)).divide(i(2));
	}

	static class Tetrahedral implements Iterator<BigInteger> {
		Tetrahedral(BigInteger startIndex) { this.i = startIndex; s = tetrahedral(i); }
		@Override
		public boolean hasNext() { return true; }
		@Override
		public BigInteger next() {
			BigInteger rv = s;
			i = i.add(i(1));
			s = tetrahedral(i);
			return rv;
		}
		private BigInteger s;
		private BigInteger i;
	}

	public static BigInteger tetrahedral(BigInteger index) {
		return index.add(i(1)).multiply(index.add(i(2))).multiply(index).divide(i(6));
	}

	public static Iterator<BigInteger> getTetrahedral() {
		return getTetrahedral(0);
	}

	public static Iterator<BigInteger> getTetrahedral(int start) {
		return new Tetrahedral(i(start));
	}

	public static Iterator<BigInteger> getTetrahedral(long start) {
		return new Tetrahedral(i(start));
	}

	public static Iterator<BigInteger> getTetrahedral(BigInteger start) {
		return new Tetrahedral(start);
	}

	public static int tetrahedralIntIndex(BigInteger value) {
		return tetrahedralIndex(value).intValue();
	}

	public static long tetrahedralLongIndex(BigInteger value) {
		return tetrahedralIndex(value).longValue();
	}

	public static BigInteger tetrahedralIndex(BigInteger value) {
		BigFraction l = f(3).multiply(f(value)).add(f(9).multiply(f(value).exponentiate(2)).subtract(f(1, 27)));
		BigFraction r = f(3).multiply(f(value)).subtract(f(9).multiply(f(value).exponentiate(2)).subtract(f(1, 27)));
		r = r.exponentiate(f(1, 3));
		l = l.exponentiate(f(1, 3));
		return l.add(r).subtract(1).getFraction().toBigInteger();
	}

	static class Pentatopic implements Iterator<BigInteger> {
		Pentatopic(BigInteger startIndex) { this.i = startIndex; s = pentatopic(i); }
		@Override
		public boolean hasNext() { return true; }
		@Override
		public BigInteger next() {
			BigInteger rv = s;
			i = i.add(i(1));
			s = pentatopic(i);
			return rv;
		}
		private BigInteger s;
		private BigInteger i;
	}

	public static BigInteger pentatopic(BigInteger index) {
		return index.add(i(1)).multiply(index.add(i(2))).multiply(index.add(i(3))).multiply(index).divide(i(24));
	}

	public static Iterator<BigInteger> getPentatopic() {
		return getPentatopic(0);
	}

	public static Iterator<BigInteger> getPentatopic(int start) {
		return new Pentatopic(i(start));
	}

	public static Iterator<BigInteger> getPentatopic(long start) {
		return new Pentatopic(i(start));
	}

	public static Iterator<BigInteger> getPentatopic(BigInteger start) {
		return new Pentatopic(start);
	}

	public static int pentatopicIntIndex(BigInteger value) {
		return pentatopicIndex(value).intValue();
	}

	public static long pentatopicLongIndex(BigInteger value) {
		return pentatopicIndex(value).longValue();
	}

	public static BigInteger pentatopicIndex(BigInteger value) {
		return i(24).multiply(value).add(i(1)).sqrt().multiply(i(4)).add(i(5)).sqrt().subtract(i(3)).divide(i(2));
	}

	static class Polygonal implements Iterator<BigInteger> {
		Polygonal(int faces, BigInteger startIndex) { 
			this.i = startIndex;
			this.faces = faces;
			this. s = polygonal(faces, i);
		}
		@Override
		public boolean hasNext() { return true; }
		@Override
		public BigInteger next() {
			BigInteger rv = s;
			i = i.add(i(1));
			s = polygonal(faces, i);
			return rv;
		}
		private BigInteger s;
		private BigInteger i;
		private final int faces;
	}

	public static BigInteger polygonal(int faces, BigInteger index) {
		iae(1, Integer.MAX_VALUE, new String[] { "faces" }, new Class<?>[] { int.class }, faces);
		faces -= 1;
		if(faces == 0)
			return i(0);
		BigInteger ans = index;
		for(int i = 1; i < faces; i++) {
			ans = index.add(i(i)).multiply(ans);
		}
		return ans.divide(Arith.factorial(i(faces)));
	}

	public static Iterator<BigInteger> getPolygonal(int faces) {
		return new Polygonal(faces, i(0));
	}

	public static Iterator<BigInteger> getPolygonal(int faces, int start) {
		return new Polygonal(faces, i(start));
	}

	public static Iterator<BigInteger> getPolygonal(int faces, long start) {
		return new Polygonal(faces, i(start));
	}

	public static Iterator<BigInteger> getPolygonal(int faces, BigInteger start) {
		return new Polygonal(faces, start);
	}

	public static BigInteger getEvenPerfectNum(int prime) {
		return i(2).pow(prime - 1).multiply(i(2).pow(prime).subtract(i(1)));
	}

	private Figurate() {}
}