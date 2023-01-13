/**
 * 
 */
package mathaid.calculator.base.value;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;
import static java.math.BigInteger.ZERO;
import static java.math.BigInteger.valueOf;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import mathaid.calculator.base.util.Arith;

/*
 * Date: 25 Oct 2020----------------------------------------------------------- 
 * Time created: 19:20:02---------------------------------------------------  
 * Package: mathaid.calculator.base.value------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: Factors.java------------------------------------------------------ 
 * Class name: Factors------------------------------------------------ 
 */
/**
 * Set of static methods for factorising integers.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
final class Factors {

	/**
	 * The constant BigInteger value 3 for quick access
	 */
	private static final BigInteger THREE = valueOf(3);
	/**
	 * The constant BigInteger value 5 for quick access
	 */
	private static final BigInteger FIVE = valueOf(5);
	/**
	 * The constant BigInteger value -1 for quick access
	 */
	private static final BigInteger M_1 = valueOf(-1);

	/**
	 * int constant for calculating the correctness of
	 * {@code BigInteger.isProbablePrime(int)}
	 */
	private static final int CERTAINTY = 1_000_000;

	/*
	 * Date: 24 Jul 2021-----------------------------------------------------------
	 * Time created: 10:02:35---------------------------------------------------
	 */
	/**
	 * Private constructor. Cannot create this object.
	 * 
	 * @throws InstantiationException
	 */
	private Factors() throws InstantiationException {
		throw new InstantiationException();
	}

	/*
	 * Date: 24 Jul 2021-----------------------------------------------------------
	 * Time created: 10:03:30--------------------------------------------
	 */
	/**
	 * Attempts to factorise the input number using 1 of 6 algorithms in this class.
	 * <ul>
	 * <li><b>0</b> basic trailDivision - only for small composites</li>
	 * <li><b>1</b> trailDivision with a small base prime sieve</li>
	 * <li><b>2</b> pollard's rho - only splits certain semi-primes and will lag on
	 * a sufficiently large composite number</li>
	 * <li><b>3</b> brent's rho - not working as of this moment</li>
	 * <li><b>4</b> fermat - not working at the moment</li>
	 * <li><b>5</b> a very basic number field sieve - most optimal at the
	 * moment</li>
	 * </ul>
	 * 
	 * @param n     a positive number to be factorised
	 * @param algol the factorisation algorithm to use
	 * @return a {@code List} of {@code BigInteger} which are the prime factors of
	 *         the input number
	 */
	static List<BigInteger> factors(BigInteger n, int algol) {
		if (n.compareTo(ZERO) == 0 || n.compareTo(ONE) == 0)
			return Collections.unmodifiableList(Arrays.asList(n));
		switch (algol) {
		case 0:
			BigInteger[] objects = new BigInteger[7];
			objects[5] = M_1;
			objects[6] = valueOf(4);
			boolean[] b = { true };
			return trialDivision(n, new ArrayList<>(), objects, b);
		case 1:
			return trialDivision(n);
		case 2:
			return pFactors(n, new ArrayList<>());
		case 3:
			return bFactors(n, new ArrayList<>());
		case 4:
			return fermat(n, new ArrayList<>());
		case 5:
			return Collections
					.unmodifiableList(org.matheclipse.core.numbertheory.Primality.factorize(n, new ArrayList<>()));
		default:
			throw new IllegalArgumentException();
		}

	}

	/*
	 * Date: 25 Oct 2020-----------------------------------------------------------
	 * Time created: 19:45:21--------------------------------------------
	 * Trial-Division is slow @: 1234598765431345087,
	 */
	/**
	 * A simple trial division algorithm
	 * 
	 * @param n the positive value to be factorised
	 * @return a {@code List} of {@code BigInteger} which are the prime factors of
	 *         the input number
	 */
	static List<BigInteger> trialDivision(BigInteger n) {
		if (n.isProbablePrime(CERTAINTY))
			return Arrays.asList(n);

		final List<BigInteger> dump = new ArrayList<>();
		BigInteger factor = TWO;
		while (n.remainder(TWO).signum() == 0) {
			dump.add(TWO);
			n = n.divide(TWO);
		}
		factor = THREE;
		while (factor.pow(2).compareTo(n) <= 0) {
			if (n.remainder(factor).signum() == 0) {
				dump.add(factor);
				n = n.divide(factor);
			} else
				factor = factor.add(ONE);
		}
		if (n.abs().compareTo(ONE) != 0)
			dump.add(n);
		return dump;
	}

	/*
	 * Date: 24 Jul 2021-----------------------------------------------------------
	 * Time created: 10:42:49--------------------------------------------
	 */
	/**
	 * A faster trial division than the other one and it uses caches.
	 * 
	 * @param n       the positive value to be factorised
	 * @param dump    a {@code List} of {@code BigInteger} which are the factors of
	 *                the input number that is gradually filled as the method
	 *                calculates the factor(s)
	 * @param objects a cache
	 * @param b       a cache
	 * @return a {@code List} of {@code BigInteger} which are the prime factors of
	 *         the input number
	 */
	static List<BigInteger> trialDivision(final BigInteger n, final List<BigInteger> dump, BigInteger[] objects,
			boolean[] b) {
		if (n.isProbablePrime(CERTAINTY)) {
			dump.add(n);
			return dump;
		}

		objects[0] = n;
		objects[4] = objects[0];

		while (objects[4].gcd(n).compareTo(ONE) != 0) {// while index 4 is a composite number
			objects[0] = trialDiv(objects[4], objects, b);
			trialDivision(objects[0], dump, objects, b);
			objects[4] = objects[4].divide(objects[0]);
//			if( objects[4].multiply(objects[4]).compareTo(n) > 0) {
//				trialDivision(objects[4], dump, objects, b);
//				break;
//			}
		}
		return Collections.unmodifiableList(dump);
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 07:35:39--------------------------------------------
	 */
	/**
	 * Searches for the next prime by incrementing a given index (2) to a prime
	 * number.
	 * 
	 * @param n       the value to be incremented
	 * @param objects a cache to track wrong and right values
	 * @return a successful increment
	 */
	private static BigInteger contextIncrement(BigInteger n, BigInteger[] objects) {
		objects[2] = n.add(objects[5].signum() < 0 ? TWO : objects[6]);

		objects[5] = objects[5].subtract(TWO.multiply(objects[5]));

		return objects[2];
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 08:02:56--------------------------------------------
	 */
	/**
	 * Continuously divide n to the smallest possible prime and returns that prime
	 * 
	 * @param n       the value to be divided
	 * @param objects a cache to track values
	 * @param b       a cache to track already tested primes
	 * @return a prime factor of n
	 */
	private static BigInteger trialDiv(BigInteger n, BigInteger[] objects, boolean[] b) {

		if (n.isProbablePrime(CERTAINTY))
			return n;

		/*
		 * if we have not checked for the first 3 primes before. check if n is a
		 * multiple either 2, 3 or five. If it is true, then the first index of the
		 * boolean array stays true else set it to false
		 */
		if (b[0]) {
			if (n.remainder(TWO).signum() == 0)// if this is a multiple of 2
				return TWO;
			else if (n.remainder(THREE).signum() == 0)// if this is a multiple of 3
				return THREE;
			else if (n.remainder(FIVE).signum() == 0)// if this is a multiple of 5
				return FIVE;
			b[0] = false;// not a multiple of the first 3 primes
		}

		objects[3] = FIVE;// start the search for a multiple of small prime here
		/*
		 * Continue division by a prime if a factor of n has not been found i.e divide n
		 * by 7, 11, 13, 17 19, 23 ... each time searching for the next prime and then
		 * see if n is a multiple of that.
		 */
		while (n.remainder(objects[3] = contextIncrement(objects[3], objects)).signum() != 0)
			;

		// return the value at index 5 which a factor of n
		return objects[3];
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 08:06:25--------------------------------------------
	 */
	/**
	 * Calculates a prime within n using the pollard's rho algorithm. this best used
	 * to find semi-primes although it will enter an infinite loop with certain
	 * values
	 * 
	 * @param n    the value to be factorised
	 * @param rand a {@code Random} object for choosing the first point within the
	 *             number to start the cycle from.
	 * @return a prime factor of n
	 */
	private static BigInteger pollardRho(BigInteger n, Random rand) {
		if (n.remainder(TWO).signum() == 0)
			return TWO;
		else if (n.isProbablePrime(CERTAINTY))
			return n;
		BigInteger x, y, c, g;
		x = new BigInteger(n.bitLength(), rand).add(ONE);
		y = x;
		c = new BigInteger(n.bitLength(), rand).add(ONE);
		g = ONE;
		while (g.compareTo(ONE) == 0) {
			x = x.pow(2).remainder(n).add(c).remainder(n);
			y = y.pow(2).remainder(n).add(c).remainder(n);
			y = y.pow(2).remainder(n).add(c).remainder(n);
			g = x.subtract(y).gcd(n);
		}
		return g;
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 08:09:39--------------------------------------------
	 */
	/**
	 * A simple pollard's rho factorisation algorithm. Repeatedly searches n until a
	 * prime is found then adds it to the list argument
	 * 
	 * @param n    the value to be factorised
	 * @param dump after this method returns, it will contain a list of the prime
	 *             factors of n
	 * @return a list of prime factors of n
	 */
	private static List<BigInteger> pFactors(BigInteger n, List<BigInteger> dump) {
		if (n.isProbablePrime(CERTAINTY)) {
			dump.add(n);
			return dump;
		}
		BigInteger x = n, y = n;
		/*
		 * FIXME: The condition inside this loop is wrong and a source of bug. The
		 * following are test values: 13455980784643, 2719L * 17865961L,
		 * 2509876533435655, 247654323456782297634672973589365,
		 * 247654323456782297634672973589360, 1234598765431345087
		 * 
		 */
		while (y.gcd(n).compareTo(ONE) != 0) {
			x = pollardRho(y, new Random());
			pFactors(x, dump);
			y = y.divide(x);
		}
		return Collections.unmodifiableList(dump);
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 08:06:25--------------------------------------------
	 */
	/**
	 * Calculates a prime within n using the brent version of pollard's rho
	 * algorithm. this method does not work for all values as of this time.
	 * 
	 * @param n    the value to be factorised
	 * @param rand a {@code Random} object for choosing the first point within the
	 *             number to start the cycle from.
	 * @return a prime factor of n
	 */
	static BigInteger brentRho(BigInteger n, Random rand) {
		if (n.remainder(TWO).signum() == 0)
			return TWO;
		else if (n.isProbablePrime(CERTAINTY))
			return n;
		BigInteger y = new BigInteger(n.bitLength(), rand).add(ONE), c = new BigInteger(n.bitLength(), rand).add(ONE),
				m = new BigInteger(n.bitLength(), rand).add(ONE), g = ONE, r = g, q = g, ys = g, x = g;

		while (g.compareTo(ONE) == 0) {
			x = y;
			for (BigInteger i = ZERO; i.compareTo(r) < 0; i = i.add(ONE))
				y = y.pow(2).remainder(n).add(c).remainder(n);

			BigInteger k = ZERO;
			while (k.compareTo(r) < 0 && g.compareTo(ONE) == 0) {
				ys = y;
				for (BigInteger i = ZERO; i.compareTo(m.min(r.subtract(k))) < 0; i = i.add(ONE)) {
					y = y.pow(2).remainder(n).add(c).remainder(n);
					q = q.multiply(x.subtract(y).abs().remainder(n));
				}
				g = q.gcd(n);
				k = k.add(m);
			}
			r = r.multiply(TWO);
		}

		if (g.compareTo(n) == 0) {
			while (true) {
				ys = ys.pow(2).remainder(n).add(c).remainder(n);
				g = n.gcd(x.subtract(ys));

				if (g.compareTo(ONE) > 0)
					break;
			}
		}

		return g;
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 08:09:39--------------------------------------------
	 */
	/**
	 * A simple brent version of the pollard's rho factorisation algorithm.
	 * Repeatedly searches n until a prime is found then adds it to the list
	 * argument. This method does not work properly.
	 * 
	 * @param n    the value to be factorised
	 * @param dump after this method returns, it will contain a list of the prime
	 *             factors of n
	 * @return a list of prime factors of n
	 */
	static List<BigInteger> bFactors(BigInteger n, List<BigInteger> dump) {

		if (n.isProbablePrime(CERTAINTY)) {
			dump.add(n);
			return dump;
		}
		BigInteger x = n, y = n;
		while (y.gcd(n).compareTo(ONE) != 0) {
			x = brentRho(y, new Random());
			bFactors(x, dump);
			y = y.divide(x);
		}

		return Collections.unmodifiableList(dump);
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 08:06:25--------------------------------------------
	 */
	/**
	 * Calculates a prime within n using the fermat's
	 * algorithm. this method does not work
	 * 
	 * @param n    the value to be factorised
	 * @return a prime factor of n
	 */
	static BigInteger fermat(BigInteger n) {
		if (n.isProbablePrime(CERTAINTY))
			return n;
//		BigInteger a, bsq;
//		long i = 1;

//		a = Arith.sqrt(n);

//		if (n.remainder(a).signum() == 0)
//			return a;

//		while (i < 10_000_000) {
//			bsq = a.pow(2).subtract(n).abs();
//			if (fastSquare(bsq))
//				return a.subtract(Arith.sqrt(bsq));
//			a = a.add(BigInteger.ONE);
//			i += 1;
//		}

		BigInteger a = Arith.sqrt(n);
		BigInteger b = a.pow(2).subtract(n).abs();

		while (!isSquare(b)) {
			a = a.add(ONE);
			b = a.pow(2).subtract(n).abs();
		}

		BigInteger root1 = a.subtract(Arith.sqrt(b)).abs();
		BigInteger root2 = n.divide(root1);

		return random(root1, root2);
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 08:09:39--------------------------------------------
	 */
	/**
	 * A simple fermat's factorisation algorithm.
	 * Repeatedly searches n until a prime is found then adds it to the list
	 * argument. This method does not work properly.
	 * 
	 * @param n    the value to be factorised
	 * @param dump after this method returns, it will contain a list of the prime
	 *             factors of n
	 * @return a list of prime factors of n
	 */
	static List<BigInteger> fermat(BigInteger n, List<BigInteger> dump) {

		if (n.isProbablePrime(CERTAINTY)) {
			dump.add(n);
			return dump;
		}
		BigInteger x = n, y = n;
		while (y.gcd(n).compareTo(ONE) != 0) {
			x = fermat(y);
			fermat(x, dump);
			y = y.divide(x);
		}

		return Collections.unmodifiableList(dump);
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 08:37:03--------------------------------------------
	 */
	/**
	 * Randomly chooses one of the varargs and returns it. This varargs must have at
	 * least 1 argument
	 * 
	 * @param <T> the type of varargs
	 * @param t   varargs to be chosen from
	 * @return a value o chosen randomly from oone of the argument
	 */
	private static <T> T random(@SuppressWarnings("unchecked") T... t) {

		Random r = new Random();
		int i = r.nextInt(t.length);

		return t[i];
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 08:33:10--------------------------------------------
	 */
	/**
	 * Returns true if n is a square of n or an adjacent square i.e a very close
	 * close square of n.
	 * 
	 * @param n the value to be tested
	 * @return true if n is a square or close to a square and false if otherwise
	 */
	private static boolean isSquare(BigInteger n) {
		BigInteger s = Arith.sqrt(n);
		return s.pow(2).compareTo(n) == 0 || s.add(ONE).multiply(s.add(ONE)).compareTo(n) == 0;
	}

	static BigInteger ellipticFactor(BigInteger n, BigInteger m) {
		for (int i = 0; i < 5; i++) {
			BigInteger[][] points = randomCurve(n, new Random());
			BigInteger[] pt = points[0], pt2 = points[1];
			BigInteger[][] mul = mulPoint(pt, pt2, m);
//			BigInteger[] q = mul[0];
			BigInteger d = mul[1][0];
			if (d.compareTo(ONE) != 0)
				return d;
		}
		return n;
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 08:30:20--------------------------------------------
	 */
	/**
	 * Multiplies a number of points with the point p.
	 * 
	 * @param points the left operand of the multiplication
	 * @param p      the right operand
	 * @param m
	 * @return a 2 dimensional array that represents <code>points &times; p</code>
	 */
	private static BigInteger[][] mulPoint(BigInteger[] points, BigInteger[] p, BigInteger m) {
		BigInteger[] ret = null;
		BigInteger d = ONE;
		BigInteger[][] addPoint = addPoint(points, ret, p);
		while (m.signum() != 0) {
			if (m.remainder(TWO).signum() != 0) {
				ret = addPoint[0];
				d = addPoint[1][0];
			}
			if (d.compareTo(ONE) != 0)
				return new BigInteger[][] { ret, { d } };
			BigInteger[][] addP = addPoint(points, p, p);
			p = addP[0];
			d = addP[1][0];
			if (!d.equals(ONE))
				return new BigInteger[][] { ret, { d } };
			m = m.shiftRight(1);
		}
		return new BigInteger[][] { ret, { d } };
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 08:23:18--------------------------------------------
	 */
	/**
	 * A method for fermat factorisation algorithm. Increments the point on a given
	 * quadratic.
	 * 
	 * @param points an array of points
	 * @param p1     the first point to be added
	 * @param p2     the second point to be added
	 * @return a 2 dimensional array representing the corresponding quadratic with
	 *         points p1 and p2 added
	 */
	private static BigInteger[][] addPoint(BigInteger[] points, BigInteger[] p1, BigInteger[] p2) {
		if (p1 == null)
			return new BigInteger[][] { p2, new BigInteger[] { ONE } };
		else if (p2 == null)
			return new BigInteger[][] { p1, { ONE } };

		BigInteger a = points[0], n = points[2]; // b = points[1],
		BigInteger x1 = p1[0], y1 = p1[1], x2 = p2[0], y2 = p2[1], x3, y3, d, s, /* v, */ u;
		BigInteger[] extendedGcd;

		x1 = x1.remainder(n);
		y1 = y1.remainder(n);
		x2 = x2.remainder(n);
		y2 = y2.remainder(n);

		if (x1.compareTo(x2) == 0) {
			extendedGcd = extendedGcd(x1.subtract(x2), n);
			d = extendedGcd[0];
			u = extendedGcd[1];
//			v = extendedGcd[2];
			s = y1.subtract(y2).multiply(u).remainder(n);
			x3 = s.multiply(s).subtract(x1).subtract(x2).remainder(n);
			y3 = y1.negate().subtract(s).multiply(x3.subtract(x1)).remainder(n);
		} else {
			if (y1.add(y2).remainder(n).signum() == 0)
				return new BigInteger[][] { null, { ONE } };

			extendedGcd = extendedGcd(TWO.multiply(y1), n);
			d = extendedGcd[0];
			u = extendedGcd[1];
//			v = extendedGcd[2];
			s = u.multiply(THREE.multiply(x1.multiply(x1)).add(a)).remainder(n);
			x3 = s.multiply(s).subtract(TWO.multiply(x1)).remainder(n);
			y3 = y1.negate().subtract(s).multiply(x3.subtract(x1)).remainder(n);
		}

		return new BigInteger[][] { { x3, y3 }, { d } };
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 08:23:18--------------------------------------------
	 */
	/**
	 * A method for fermat factorisation algorithm.
	 * 
	 * @param n      a value on which calculation is done
	 * @param random a {@code Random} object to find where on the curve to begin
	 *               calculations
	 * @return a 2 dimensional array representing the corresponding quadratic
	 */
	private static BigInteger[][] randomCurve(BigInteger n, Random random) {
		BigInteger a = new BigInteger(n.bitLength(), random), u = new BigInteger(n.bitLength(), random),
				v = new BigInteger(n.bitLength(), random);
		BigInteger b = v.multiply(v).subtract(u.pow(3)).subtract(a.multiply(u)).mod(n);
		return new BigInteger[][] { { a, b, n }, { u, v } };
	}

	/*
	 * Date: 25 Jul 2021-----------------------------------------------------------
	 * Time created: 08:20:03--------------------------------------------
	 */
	/**
	 * A method for fermat factorisation algorithm. finds the gcd of both a and b
	 * 
	 * @param a the first argument
	 * @param b the second argument
	 * @return a 3 element array
	 */
	private static BigInteger[] extendedGcd(BigInteger a, BigInteger b) {
		BigInteger x = ZERO, y = ONE, lastX = ONE, lastY = ZERO;
		while (b.signum() != 0) {
			BigInteger q = a.divide(b);
			a = b;
			b = a.remainder(b);
			x = lastX.subtract(q.multiply(x));
			lastX = x;
			y = lastY.subtract(q.multiply(y));
			lastY = y;
		}
		if (a.signum() < 0)
			return new BigInteger[] { a.negate(), lastX.negate(), lastY.negate() };
		return new BigInteger[] { a, lastX, lastY };
	}
}
