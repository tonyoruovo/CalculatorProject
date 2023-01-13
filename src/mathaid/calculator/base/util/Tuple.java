/**
 * TODO: Type policy is partially enforced during set operations,
 * so that differences in generic interfaces do not matter as long
 * as the type of the new value is the same as the old value.
 * e.g Tuple.of(List<String>, List<Integer>) will be completely
 * overlooked by the code and not throw a type exception.
 * TODO: Serialization is not implemented. If a Tuple object is
 * saved to the serialization stream, it's element will be lost
 * as only the identity is saved.
 */
package mathaid.calculator.base.util;

import mathaid.IndexBeyondLimitException;

/*
 * Date: 28 Apr 2022----------------------------------------------------------- 
 * Time created: 20:20:18---------------------------------------------------  
 * Package: mathaid.calculator.base.converter------------------------------------------------ 
 * Project: LatestProject------------------------------------------------ 
 * File: Tuple.java------------------------------------------------------ 
 * Class name: Tuple------------------------------------------------ 
 */
/**
 * Represents a mathematical tuple of a single item (monuple). In the context of
 * the mathaid API, this represents an immutable, fixed size data structure of
 * Strongly-typed elements. The tuple is not merely a collection, it is a data
 * structure designed with the idea that a user may need to represent data of
 * different types in a single container (the collection interface and maps of
 * the JDK being out of the question because they only support a generic of a
 * singular type). The purpose is to wrap data of different types while
 * preserving the data type of the data contained therein. Therefore, methods
 * such as {@link Tuple#set(int, Object)} and {@link Tuple#get(int)} are not
 * fully supported (generic-wise) because some form of casting is needed (either
 * from the API end or user end). If a user desires random access tuples, mutable
 * sizes and index changes, they should consider constituting a list-tuple hybrid,
 * although all child interfaces below support random access, i.e get the value
 * at an index without traversing the whole container. An example of how a
 * {@code Tuple} might be used to store different data types and also maintain
 * their compile-time type is given below:
 * <pre><code>
 *	Random r = new Random();
 *	&sol;&ast;
 *	 &ast; This Tuple stores up to 12 unrelated (with no shared interface) elements
 *	 &ast; by nesting a four-element Tuple (Quadruple) inside a nine-element Tuple
 *	 &ast; (Nonuple). Thus the 9th element of the nine-element Tuple is another
 *	 &ast; four-element Tuple to bring the number to 12 elements. This nesting
 *	 &ast; method should be used when a Tuple of more than 9 elements is needed.
 *	 &ast;&sol;
 *	Nonuple&lt;Integer, Byte, Boolean, Long, Short,
 *		String, BigDecimal, Character, Quadruple&lt;BigInteger,
 *		Double, BigFraction, Float&gt;&gt; tuple
 *			= Tuple.of(r.nextInt(), (byte)r.nextInt(Byte.MAX_VALUE + 1), r.nextBoolean(),
 *					r.nextLong(), (short)r.nextInt(Short.MAX_VALUE + 1), nextString(r, r.nextInt(50)),
 *					nextBigDecimal(r, r.nextInt(200)), nextChar(r, "ASCII", false), Tuple.of(nextBigInteger(r, r.nextInt(200)),
 *							r.nextDouble(), nextBigFraction(r, r.nextInt(25), r.nextInt(30)), r.nextFloat()));
 *
 *	System.out.println("Ist Element of Type " + tuple.get().getClass().getSimpleName() + ": " + tuple.get());
 *	System.out.println("2nd Element of Type " + tuple.get2nd().getClass().getSimpleName() + ": " + tuple.get2nd());
 *	System.out.println("3rd Element of Type " + tuple.get3rd().getClass().getSimpleName() + ": " + tuple.get3rd());
 *	System.out.println("4th Element of Type " + tuple.get4th().getClass().getSimpleName() + ": " + tuple.get4th());
 *	System.out.println("5th Element of Type " + tuple.get5th().getClass().getSimpleName() + ": " + tuple.get5th());
 *	System.out.println("6th Element of Type " + tuple.get6th().getClass().getSimpleName() + ": " + tuple.get6th());
 *	System.out.println("7th Element of Type " + tuple.get7th().getClass().getSimpleName() + ": " + tuple.get7th());
 *	System.out.println("8th Element of Type " + tuple.get8th().getClass().getSimpleName() + ": " + tuple.get8th());
 *	
 *	// The rest of the Tuple containing the remaining 4 elements
 *	Quadruple&lt;BigInteger, Double, BigFraction, Float&gt; quad = tuple.get9th();
 *
 *	System.out.println("9th Element of Type " + quad.get().getClass().getSimpleName() + ": " + quad.get());
 *	System.out.println("10th Element of Type " + quad.get2nd().getClass().getSimpleName() + ": " + quad.get2nd());
 *	System.out.println("11th Element of Type " + quad.get3rd().getClass().getSimpleName() + ": " + quad.get3rd());
 *	System.out.println("12th Element of Type " + quad.get4th().getClass().getSimpleName() + ": " + quad.get4th());
 * </code></pre>
 * The Random methods are defined thus:
 * <pre><code>
 *	
 *	static char nextChar(Random r, String encoding, boolean controlChar) {
 *		int codePoint;
 *		switch(encoding.toUpperCase()) {
 *		case "ASCII":
 *			codePoint = r.nextInt(Byte.MAX_VALUE);
 *			break;
 *		case "UTF-8":
 *			codePoint = r.nextInt(Short.MAX_VALUE);
 *			break;
 *		case "UTF-16":
 *		case "UTF-32":
 *			codePoint = r.nextInt(Integer.MAX_VALUE);
 *			break;
 *			default: throw new java.lang.IllegalArgumentException("unknown encoding");
 *		}
 *		return Character.isISOControl(codePoint) && !controlChar ? nextChar(r, encoding, controlChar) : (char) codePoint;
 *	}
 *	
 *	static BigInteger nextBigInteger(Random r, int digits) {
 *		int length = r.nextInt(Math.abs(digits)) + 1;
 *		StringBuilder sb = new StringBuilder(Math.abs(length));
 *		sb.append(r.nextBoolean() ? '-' : '+');
 *		sb.append(r.nextInt(9) + 1);//prevent the first digit being a zero (1-9)
 *		int i = 1;//since one slot is already used
 *		do {
 *			sb.append(r.nextInt(10));//0-9
 *			i++;
 *		}while(i < length);
 *		return new BigInteger(sb.toString());
 *	}
 *	
 *	static BigDecimal nextBigDecimal(Random r, int mantissaDigits) {
 *		int length = r.nextInt(Math.abs(mantissaDigits));
 *		StringBuilder sb = new StringBuilder(Math.abs(length) + 2);//the zero and the decimal point
 *		sb.append(r.nextBoolean() ? '-' : '+');
 *		sb.append("0.");
 *		int i = 0;
 *		do {
 *			sb.append(r.nextInt(10));//0-9
 *			i++;
 *		}while(i < length);
 *		return new BigDecimal(sb.toString());
 *	}
 *	
 *	static BigFraction nextBigFraction(Random r, int numeratorLength, int denominatorLength) {
 *		return new BigFraction(nextBigInteger(r, numeratorLength), nextBigInteger(r, denominatorLength));
 *	}
 *	
 *	static String nextString(Random r, int stringLength){
 *		StringBuilder sb = new StringBuilder(Math.abs(stringLength));
 *		for(int i = 0; i < stringLength; i++)
 *			sb.append((char)(r.nextInt(26) + 'a'));
 *		return sb.toString();
 *	}
 * </code></pre>
 * The Output looks something like:
 * <pre>
 *	Ist Element of Type Integer: -2095507570
 *	2nd Element of Type Byte: 119
 *	3rd Element of Type Boolean: true
 *	4th Element of Type Long: -2393397260771119583
 *	5th Element of Type Short: 21918
 *	6th Element of Type String: kytqgayijomhjjhrigufrvoiodrffzsxpgspos
 *	7th Element of Type BigDecimal: 0.8241432412860313200062747473325
 *	8th Element of Type Character: M
 *	9th Element of Type BigInteger: -423731375460121733175192796298500715901927595514080297664455292304124910493910205381164910149393
 *	10th Element of Type Double: 0.5994954929337044
 *	11th Element of Type BigFraction: [ -39236, 24, 38 ]
 *	12th Element of Type Float: 0.36669785
 * </pre>
 * Once again, you can see that elements were retrieved at their index without
 * sequential traversal of the tuple, hence random access is achieved, although
 * using this method to create a 12 length tuple (or any other length of tuple
 * longer than the initial container) will not properly reveal the number of
 * elements, instead when {@link #size()} is called on the above tuple, 9 will
 * be returned. To properly utilise the length of your tuple use the {@code NTuple}
 * interface. That way, when a tuple is created using nested tuples, the correct
 * number of elements may be returned.
 * <p>
 * Once a tuple is created, it's state should not be changed. No matter what
 * behaviour it exhibits (or method is called on it), {@code equals(Object)} and
 * {@code hashCode()} must consistently return the same value each time they are
 * called on the same object, once created, {@code size()} must never change and
 * {@code get(int)} must return the same object each time (unless the object at
 * that index is mutable and the user has changed the object through it's
 * mutability). In short, the immutability of tuples must be maintained when
 * implementing it and a mutable class should never implement period. As a result,
 * it is recommended to create a tuple using one the static methods of this class,
 * as they do not possess internal fields (state) which may compromise the above
 * stipulations.
 * </p>
 * <p>
 * Tuples are fully serializable if all their elements are serializable, otherwise
 * a {@code NotSerializableException} will be thrown.
 * </p>
 * <p>
 * <b>Null Elements - </b>Important to note that the tuple returned by the static factory
 * methods of this interface will allow initialisation and insertion of null elements.
 * However, excluding {@link Tuple#of()} and {@link Tuple#of(Object, Tuple)},
 * {@link #equals(Object)} and {@link #hashCode()} will throw a {@code NullPointerException}
 * if called with a null element existing in the tuple from which it was called.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface Tuple<T> extends java.io.Serializable {

	/*
	 * Date: 28 Apr 2022-----------------------------------------------------------
	 * Time created: 21:10:00---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base.value------------------------------------------------
	 * Project: LatestProject------------------------------------------------ File:
	 * Tuple.java------------------------------------------------------ Class name:
	 * Couple------------------------------------------------
	 */
	/**
	 * Represents the mathematical Couple (2-length tuple)
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 * @param <A> the type of the first element whose value is specified by
	 *            {@link Tuple#get()}
	 * @param <B> the type of the second element whose value is specified by
	 *            {@link Couple#get2nd()}
	 */
	static interface Couple<A, B> extends Tuple<A> {
		/*
		 * Date: 28 Apr 2022-----------------------------------------------------------
		 * Time created: 21:14:09--------------------------------------------
		 */
		/**
		 * Gets the second element in this {@code Couple}.
		 * 
		 * @return the second element
		 */
		B get2nd();

		/*
		 * Most Recent Date: 29 Apr 2022-----------------------------------------------
		 * Most recent time created: 01:16:28--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @param index {@inheritDoc}
		 * @return {@inheritDoc}
		 * @throws IndexOutOfBoundsException if
		 *                                   <code>index &lt; 0 || index &gt; size() - 1</code>.
		 */
		@Override
		default Object get(int index) throws IndexOutOfBoundsException {
			if (index < 0 || index > size() - 1)
				new IndexBeyondLimitException(index, size());
			return index == 0 ? get() : get2nd();
		}

		/*
		 * Most Recent Date: 29 Apr 2022-----------------------------------------------
		 * Most recent time created: 08:56:36--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * @param <U> {@inheritDoc}
		 * @param index {@inheritDoc}
		 * @param u {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 * @throws 
		 */
		@Override
		<U> Couple<A, B> set(int index, U u) throws IndexOutOfBoundsException;

		/*
		 * Most Recent Date: 29 Apr 2022-----------------------------------------------
		 * Most recent time created: 02:05:11--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 */
		@Override
		default int size() {
			return 2;
		}
	}

	/*
	 * Date: 28 Apr 2022-----------------------------------------------------------
	 * Time created: 21:15:01---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base.value------------------------------------------------
	 * Project: LatestProject------------------------------------------------ File:
	 * Tuple.java------------------------------------------------------ Class name:
	 * Triple------------------------------------------------
	 */
	/**
	 * Represents a mathematical Triple (3-length tuple).
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 * @param <A> the type of the first element whose value is specified by
	 *            {@link Tuple#get()}
	 * @param <B> the type of the second element whose value is specified by
	 *            {@link Couple#get2nd()}
	 * @param <C> the type of the third element whose value is specified by
	 *            {@link Triple#get3rd()}
	 */
	static interface Triple<A, B, C> extends Couple<A, B> {
		/*
		 * Date: 28 Apr 2022-----------------------------------------------------------
		 * Time created: 21:16:23--------------------------------------------
		 */
		/**
		 * Gets the third element in this {@code Triple}.
		 * 
		 * @return the third element
		 */
		C get3rd();

		/*
		 * Most Recent Date: 29 Apr 2022-----------------------------------------------
		 * Most recent time created: 08:56:36--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * @param <U> {@inheritDoc}
		 * @param index {@inheritDoc}
		 * @param u {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 * @throws
		 */
		@Override
		<U> Triple<A, B, C> set(int index, U u) throws IndexOutOfBoundsException;

		/*
		 * Most Recent Date: 29 Apr 2022-----------------------------------------------
		 * Most recent time created: 02:05:11--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 */
		@Override
		default int size() {
			return 3;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @param index {@inheritDoc}
		 * @return {@inheritDoc}
		 * @throws IndexOutOfBoundsException if
		 *                                   <code>index &lt; 0 || index &gt; size() - 1</code>.
		 */
		@Override
		default Object get(int index) throws IndexOutOfBoundsException {
			if (index < 0 || index > size() - 1)
				new IndexBeyondLimitException(index, size());
			return index == 0 ? get() : index == 1 ? get2nd() : get3rd();
		}
	}

	/*
	 * Date: 28 Apr 2022-----------------------------------------------------------
	 * Time created: 21:25:27---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base.value------------------------------------------------
	 * Project: LatestProject------------------------------------------------ File:
	 * Tuple.java------------------------------------------------------ Class name:
	 * Quadruple------------------------------------------------
	 */
	/**
	 * Represents a mathematical Quadruple (4-length tuple).
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 * @param <A> the type of the first element whose value is specified by
	 *            {@link Tuple#get()}
	 * @param <B> the type of the second element whose value is specified by
	 *            {@link Couple#get2nd()}
	 * @param <C> the type of the third element whose value is specified by
	 *            {@link Triple#get3rd()}
	 * @param <D> the type of the fourth element whose value is specified by
	 *            {@link Quadruple#get4th()}
	 */
	static interface Quadruple<A, B, C, D> extends Triple<A, B, C> {
		/*
		 * Date: 28 Apr 2022-----------------------------------------------------------
		 * Time created: 21:27:29--------------------------------------------
		 */
		/**
		 * Gets the 4th element in this {@code Quadruple}.
		 * 
		 * @return the 4th element.
		 */
		D get4th();

		/*
		 * Most Recent Date: 29 Apr 2022-----------------------------------------------
		 * Most recent time created: 08:56:36--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * @param <U> {@inheritDoc}
		 * @param index {@inheritDoc}
		 * @param u {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 * @throws
		 */
		@Override
		<U> Quadruple<A, B, C, D> set(int index, U u) throws IndexOutOfBoundsException;

		/*
		 * Most Recent Date: 29 Apr 2022-----------------------------------------------
		 * Most recent time created: 02:05:11--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 */
		@Override
		default int size() {
			return 4;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @param index {@inheritDoc}
		 * @return {@inheritDoc}
		 * @throws IndexOutOfBoundsException if
		 *                                   <code>index &lt; 0 || index &gt; size() - 1</code>.
		 */
		@Override
		default Object get(int index) throws IndexOutOfBoundsException {
			if (index < 0 || index > size() - 1)
				new IndexBeyondLimitException(index, size());
			return index == 0 ? get() : index == 1 ? get2nd() : index == 2 ? get3rd() : get4th();
		}
	}

	/*
	 * Date: 28 Apr 2022-----------------------------------------------------------
	 * Time created: 21:28:16---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base.value------------------------------------------------
	 * Project: LatestProject------------------------------------------------ File:
	 * Tuple.java------------------------------------------------------ Class name:
	 * Quintuple------------------------------------------------
	 */
	/**
	 * Represents a mathematical Quintuple (5-length tuple).
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 * @param <A> the type of the first element whose value is specified by
	 *            {@link Tuple#get()}
	 * @param <B> the type of the second element whose value is specified by
	 *            {@link Couple#get2nd()}
	 * @param <C> the type of the third element whose value is specified by
	 *            {@link Triple#get3rd()}
	 * @param <D> the type of the fourth element whose value is specified by
	 *            {@link Quadruple#get4th()}
	 * @param <E> the type of the fifth element whose value is specified by
	 *            {@link Quintuple#get5th()}
	 */
	static interface Quintuple<A, B, C, D, E> extends Quadruple<A, B, C, D> {
		/*
		 * Date: 28 Apr 2022-----------------------------------------------------------
		 * Time created: 21:29:16--------------------------------------------
		 */
		/**
		 * Gets the 5th element in this {@code Quintuple}.
		 * 
		 * @return the fifth element.
		 */
		E get5th();

		/*
		 * Most Recent Date: 29 Apr 2022-----------------------------------------------
		 * Most recent time created: 08:56:36--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * @param <U> {@inheritDoc}
		 * @param index {@inheritDoc}
		 * @param u {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 * @throws
		 */
		@Override
		<U> Quintuple<A, B, C, D, E> set(int index, U u) throws IndexOutOfBoundsException;

		/*
		 * Most Recent Date: 29 Apr 2022-----------------------------------------------
		 * Most recent time created: 02:05:11--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 */
		@Override
		default int size() {
			return 5;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @param index {@inheritDoc}
		 * @return {@inheritDoc}
		 * @throws IndexOutOfBoundsException if
		 *                                   <code>index &lt; 0 || index &gt; size() - 1</code>.
		 */
		@Override
		default Object get(int index) throws IndexOutOfBoundsException {
			if (index < 0 || index > size() - 1)
				new IndexBeyondLimitException(index, size());
			return index == 0 ? get()
					: index == 1 ? get2nd() : index == 2 ? get3rd() : index == 3 ? get4th() : get5th();
		}
	}

	/*
	 * Date: 28 Apr 2022-----------------------------------------------------------
	 * Time created: 21:29:57---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base.value------------------------------------------------
	 * Project: LatestProject------------------------------------------------ File:
	 * Tuple.java------------------------------------------------------ Class name:
	 * Sextuple------------------------------------------------
	 */
	/**
	 * Represents a mathematical Sextuple (6-length tuple).
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 * @param <A> the type of the first element whose value is specified by
	 *            {@link Tuple#get()}
	 * @param <B> the type of the second element whose value is specified by
	 *            {@link Couple#get2nd()}
	 * @param <C> the type of the third element whose value is specified by
	 *            {@link Triple#get3rd()}
	 * @param <D> the type of the fourth element whose value is specified by
	 *            {@link Quadruple#get4th()}
	 * @param <E> the type of the fifth element whose value is specified by
	 *            {@link Quintuple#get5th()}
	 * @param <F> the type of the sixth element whose value is specified by
	 *            {@link Sextuple#get6th()}
	 */
	static interface Sextuple<A, B, C, D, E, F> extends Quintuple<A, B, C, D, E> {
		/*
		 * Date: 28 Apr 2022-----------------------------------------------------------
		 * Time created: 21:32:02--------------------------------------------
		 */
		/**
		 * Gets the 6th element in this {@code Sextuple}.
		 * 
		 * @return the sixth element.
		 */
		F get6th();

		/*
		 * Most Recent Date: 29 Apr 2022-----------------------------------------------
		 * Most recent time created: 08:56:36--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * @param <U> {@inheritDoc}
		 * @param index {@inheritDoc}
		 * @param u {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 * @throws
		 */
		@Override
		<U> Sextuple<A, B, C, D, E, F> set(int index, U u) throws IndexOutOfBoundsException;

		/*
		 * Most Recent Date: 29 Apr 2022-----------------------------------------------
		 * Most recent time created: 02:05:11--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 */
		@Override
		default int size() {
			return 6;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @param index {@inheritDoc}
		 * @return {@inheritDoc}
		 * @throws IndexOutOfBoundsException if
		 *                                   <code>index &lt; 0 || index &gt; size() - 1</code>.
		 */
		@Override
		default Object get(int index) throws IndexOutOfBoundsException {
			if (index < 0 || index > size() - 1)
				new IndexBeyondLimitException(index, size());
			return index == 0 ? get()
					: index == 1 ? get2nd()
							: index == 2 ? get3rd() : index == 3 ? get4th() : index == 4 ? get5th() : get6th();
		}
	}

	/*
	 * Date: 28 Apr 2022-----------------------------------------------------------
	 * Time created: 21:32:42---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base.value------------------------------------------------
	 * Project: LatestProject------------------------------------------------ File:
	 * Tuple.java------------------------------------------------------ Class name:
	 * Septuple------------------------------------------------
	 */
	/**
	 * Represents a mathematical Septuple (7-length tuple).
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 * @param <A> the type of the first element whose value is specified by
	 *            {@link Tuple#get()}
	 * @param <B> the type of the second element whose value is specified by
	 *            {@link Couple#get2nd()}
	 * @param <C> the type of the third element whose value is specified by
	 *            {@link Triple#get3rd()}
	 * @param <D> the type of the fourth element whose value is specified by
	 *            {@link Quadruple#get4th()}
	 * @param <E> the type of the fifth element whose value is specified by
	 *            {@link Quintuple#get5th()}
	 * @param <F> the type of the sixth element whose value is specified by
	 *            {@link Sextuple#get6th()}
	 * @param <G> the type of the seventh element whose value is specified by
	 *            {@link Septuple#get7th()}
	 */
	static interface Septuple<A, B, C, D, E, F, G> extends Sextuple<A, B, C, D, E, F> {
		/*
		 * Date: 28 Apr 2022-----------------------------------------------------------
		 * Time created: 21:33:27--------------------------------------------
		 */
		/**
		 * Gets the 7th element in this {@code Septuple}.
		 * 
		 * @return the seventh element.
		 */
		G get7th();

		/*
		 * Most Recent Date: 29 Apr 2022-----------------------------------------------
		 * Most recent time created: 08:56:36--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * @param <U> {@inheritDoc}
		 * @param index {@inheritDoc}
		 * @param u {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 * @throws
		 */
		@Override
		<U> Septuple<A, B, C, D, E, F, G> set(int index, U u) throws IndexOutOfBoundsException;

		/*
		 * Most Recent Date: 29 Apr 2022-----------------------------------------------
		 * Most recent time created: 02:05:11--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 */
		@Override
		default int size() {
			return 7;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @param index {@inheritDoc}
		 * @return {@inheritDoc}
		 * @throws IndexOutOfBoundsException if
		 *                                   <code>index &lt; 0 || index &gt; size() - 1</code>.
		 */
		@Override
		default Object get(int index) throws IndexOutOfBoundsException {
			if (index < 0 || index > size() - 1)
				new IndexBeyondLimitException(index, size());
			return index == 0 ? get()
					: index == 1 ? get2nd()
							: index == 2 ? get3rd()
									: index == 3 ? get4th() : index == 4 ? get5th() : index == 5 ? get6th() : get7th();
		}
	}

	/*
	 * Date: 28 Apr 2022-----------------------------------------------------------
	 * Time created: 21:34:39---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base.value------------------------------------------------
	 * Project: LatestProject------------------------------------------------ File:
	 * Tuple.java------------------------------------------------------ Class name:
	 * Octuple------------------------------------------------
	 */
	/**
	 * Represents a mathematical Octuple (8-length tuple).
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 * @param <A> the type of the first element whose value is specified by
	 *            {@link Tuple#get()}
	 * @param <B> the type of the second element whose value is specified by
	 *            {@link Couple#get2nd()}
	 * @param <C> the type of the third element whose value is specified by
	 *            {@link Triple#get3rd()}
	 * @param <D> the type of the fourth element whose value is specified by
	 *            {@link Quadruple#get4th()}
	 * @param <E> the type of the fifth element whose value is specified by
	 *            {@link Quintuple#get5th()}
	 * @param <F> the type of the sixth element whose value is specified by
	 *            {@link Sextuple#get6th()}
	 * @param <G> the type of the seventh element whose value is specified by
	 *            {@link Septuple#get7th()}
	 * @param <H> the type of the eighth element whose value is specified by
	 *            {@link Octuple#get8th()}
	 */
	static interface Octuple<A, B, C, D, E, F, G, H> extends Septuple<A, B, C, D, E, F, G> {
		/*
		 * Date: 28 Apr 2022-----------------------------------------------------------
		 * Time created: 21:35:31--------------------------------------------
		 */
		/**
		 * Gets the 8th element in this {@code Octuple}.
		 * 
		 * @return the eighth element.
		 */
		H get8th();

		/*
		 * Most Recent Date: 29 Apr 2022-----------------------------------------------
		 * Most recent time created: 08:56:36--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * @param <U> {@inheritDoc} 
		 * @param index {@inheritDoc}
		 * @param u {@inheritDoc}
		 * @return {@inheritDoc}
		 * @throws
		 */
		@Override
		<U> Octuple<A, B, C, D, E, F, G, H> set(int index, U u) throws IndexOutOfBoundsException;

		/*
		 * Most Recent Date: 29 Apr 2022-----------------------------------------------
		 * Most recent time created: 02:05:11--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 */
		@Override
		default int size() {
			return 8;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @param index {@inheritDoc}
		 * @return {@inheritDoc}
		 * @throws IndexOutOfBoundsException if
		 *                                   <code>index &lt; 0 || index &gt; size() - 1</code>.
		 */
		@Override
		default Object get(int index) throws IndexOutOfBoundsException {
			if (index < 0 || index > size() - 1)
				new IndexBeyondLimitException(index, size());
			return index == 0 ? get()
					: index == 1 ? get2nd()
							: index == 2 ? get3rd()
									: index == 3 ? get4th()
											: index == 4 ? get5th()
													: index == 5 ? get6th() : index == 6 ? get7th() : get8th();
		}
	}

	/*
	 * Date: 28 Apr 2022-----------------------------------------------------------
	 * Time created: 21:37:15---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base.value------------------------------------------------
	 * Project: LatestProject------------------------------------------------ File:
	 * Tuple.java------------------------------------------------------ Class name:
	 * Nonuple------------------------------------------------
	 */
	/**
	 * Represents a mathematical Nonuple (9-length tuple).
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 * @param <A> the type of the first element whose value is specified by
	 *            {@link Tuple#get()}
	 * @param <B> the type of the second element whose value is specified by
	 *            {@link Couple#get2nd()}
	 * @param <C> the type of the third element whose value is specified by
	 *            {@link Triple#get3rd()}
	 * @param <D> the type of the fourth element whose value is specified by
	 *            {@link Quadruple#get4th()}
	 * @param <E> the type of the fifth element whose value is specified by
	 *            {@link Quintuple#get5th()}
	 * @param <F> the type of the sixth element whose value is specified by
	 *            {@link Sextuple#get6th()}
	 * @param <G> the type of the seventh element whose value is specified by
	 *            {@link Septuple#get7th()}
	 * @param <H> the type of the eighth element whose value is specified by
	 *            {@link Octuple#get8th()}
	 * @param <I> the type of the ninth element whose value is specified by
	 *            {@link Nonuple#get9th()}
	 */
	static interface Nonuple<A, B, C, D, E, F, G, H, I> extends Octuple<A, B, C, D, E, F, G, H> {
		/*
		 * Date: 28 Apr 2022-----------------------------------------------------------
		 * Time created: 21:38:00--------------------------------------------
		 */
		/**
		 * Gets the 9th element in this {@code Nonuple}.
		 * 
		 * @return the ninth element.
		 */
		I get9th();

		/*
		 * Most Recent Date: 29 Apr 2022-----------------------------------------------
		 * Most recent time created: 08:56:36--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * @param <U> {@inheritDoc}
		 * @param index {@inheritDoc}
		 * @param u {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 * @throws
		 */
		@Override
		<U> Nonuple<A, B, C, D, E, F, G, H, I> set(int index, U u) throws IndexOutOfBoundsException;

		/*
		 * Most Recent Date: 29 Apr 2022-----------------------------------------------
		 * Most recent time created: 02:05:11--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 */
		@Override
		default int size() {
			return 9;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @param index {@inheritDoc}
		 * @return {@inheritDoc}
		 * @throws IndexOutOfBoundsException if
		 *                                   <code>index &lt; 0 || index &gt; size() - 1</code>.
		 */
		@Override
		default Object get(int index) throws IndexOutOfBoundsException {
			if (index < 0 || index > size() - 1)
				new IndexBeyondLimitException(index, size());
			return index == 0 ? get()
					: index == 1 ? get2nd()
							: index == 2 ? get3rd()
									: index == 3 ? get4th()
											: index == 4 ? get5th()
													: index == 5 ? get6th()
															: index == 6 ? get7th() : index == 7 ? get8th() : get9th();
		}
	}

	/*
	 * Date: 28 Apr 2022-----------------------------------------------------------
	 * Time created: 21:39:11---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base.value------------------------------------------------
	 * Project: LatestProject------------------------------------------------ File:
	 * Tuple.java------------------------------------------------------ Class name:
	 * NTuple------------------------------------------------
	 */
	/**
	 * Represents a mathematical tuple of <i>n</i>-tuples. Please note that this
	 * interface is meant to be used only if the user needs more than 9 elements. If
	 * the users requires less than 9 elements, they should prefer one of the
	 * subclasses of {@code Tuple}.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 * @param <H> the type of the first element whose value is specified by
	 *            {@link Tuple#get()}
	 * @param <S> the type of the second element (i.e the first element of
	 *            {@link NTuple#getTuple()})
	 * @param <N> The type of {@link Tuple} that holds the rest of the elements
	 */
	static interface NTuple<H, S, N extends Tuple<S>> extends Tuple<H> {
		/*
		 * Date: 28 Apr 2022-----------------------------------------------------------
		 * Time created: 21:42:26--------------------------------------------
		 */
		/**
		 * The rest of the elements as contained in a {@code Tuple}.
		 * 
		 * @return a {@code Tuple} that contains the rest of the elements.
		 */
		N getTuple();

		/*
		 * Most Recent Date: 29 Apr 2022-----------------------------------------------
		 * Most recent time created: 08:56:36--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * @param <U> {@inheritDoc}
		 * @param index {@inheritDoc}
		 * @param u {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 * @throws
		 */
		@Override
		<U> NTuple<H, S, N> set(int index, U u) throws IndexOutOfBoundsException;

		/**
		 * {@inheritDoc}
		 * 
		 * @param index {@inheritDoc}
		 * @return {@inheritDoc}
		 * @throws IndexOutOfBoundsException if
		 *                                   <code>index &lt; 0 || index &gt; size() - 1</code>.
		 */
		@Override
		default Object get(int index) throws IndexOutOfBoundsException {
			if (index < 0 || index > size() - 1)
				new IndexBeyondLimitException(index, size());
			return index == 0 ? get() : getTuple().get(index - 1);
		}

		/*
		 * Most Recent Date: 28 Apr 2022-----------------------------------------------
		 * Most recent time created: 21:46:29--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return {@inheritDoc}
		 */
		@Override
		default int size() {
			return 1 + getTuple().size();
		}
	}

	/*
	 * Date: 28 Apr 2022-----------------------------------------------------------
	 * Time created: 22:18:55--------------------------------------------
	 */
	/**
	 * Compiles and returns an empty tuple of length 0. Note that the
	 * {@link set(int, Object)} method will always throw a
	 * {@code IndexOutOfBoundsException} since the size is constant and there is no
	 * element in this tuple therefore, index will always be out of bounds.
	 * 
	 * @param <T> the type of elements, if there was any, in the tuple.
	 * @return a {@code Tuple} of the given type whose {@link Tuple#get() element}
	 *         is {@code null} and {@link Tuple#size() length} is zero.
	 */
	static <T> Tuple<T> of() {
		return new Tuple<T>() {
			
			private static final long serialVersionUID = 0x0000_0000_0000_0000L;;
			
			@Override
			public T get() {
				return null;
			}

			@Override
			public <U> Tuple<T> set(int index, U u) {
				new IndexBeyondLimitException(index, size());
				return this; // for java formality
			}

			@Override
			public int size() {
				return 0;
			}

			@Override
			public int hashCode() {
				return 0b1000_0000_0000_0000_0000_0000_0000_0000;
			}

			@Override
			public boolean equals(Object o) {
				if (o instanceof Tuple) {
					Tuple<?> t = (Tuple<?>) o;
					return size() == t.size() && t.get().equals(get());
				}
				return false;
			}
			
			@Override
			public String toString() {
				return "()";
			}
		};
	}

	/*
	 * Date: 28 Apr 2022-----------------------------------------------------------
	 * Time created: 22:24:13--------------------------------------------
	 */
	/**
	 * Compiles and returns a tuple of single length (or monuple).
	 * 
	 * @param <T> the type of element in the tuple.
	 * @param t   the element of the tuple.
	 * @return a {@code Tuple} of the given type whose {@link Tuple#get() element}
	 *         is the argument and {@link Tuple#size() length} is 1.
	 */
	static <T> Tuple<T> of(T t) {
		class Singleton implements Tuple<T> {
			private static final long serialVersionUID = 0x0000_0000_0000_0001L;
			private T _1 = t;
			@Override
			public T get() {
				return _1;
			}

			@Override
			public <U> Tuple<T> set(int index, U u) {
				if (index != 0)
					new IndexBeyondLimitException(index, size());
				return new Singleton() {
					private static final long serialVersionUID = 0x0000_0000_0000_0001L;
					@SuppressWarnings("unchecked")
					@Override
					public T get() {
						if(!Utility.checkType(super.get(), u, super.get().getClass()))
							new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
									new ClassCastException("incompatible object for set operation"), (Object[]) null);
						return (T) u;
					}
				};
			}

			@Override
			public int hashCode() {
				return 0b1000_0000_0000_0000_0000_0000_0000_0000 | get().hashCode();
			}

			@Override
			public boolean equals(Object o) {
				if (o instanceof Tuple) {
					Tuple<?> t = (Tuple<?>) o;
					return size() == t.size() && t.get().equals(get());
				}
				return false;
			}

			@Override
			public String toString() {
				return "(" + get().toString() + ")";
			}
		}

		return new Singleton();
	}

	/*
	 * Date: 28 Apr 2022-----------------------------------------------------------
	 * Time created: 22:28:51--------------------------------------------
	 */
	/**
	 * Compiles and returns a tuple of 2-length.
	 * 
	 * @param <A> the type of first element in the tuple.
	 * @param <B> the type of second element in the tuple.
	 * @param a   the first element of the tuple.
	 * @param b   the second element of the tuple.
	 * @return a {@code Couple} of the given type whose {@link Tuple#get() elements}
	 *         are the arguments and {@link Tuple#size() length} is 2.
	 */
	static <A, B> Couple<A, B> of(A a, B b) {
		class Double implements Couple<A, B> {
			private static final long serialVersionUID = 0x0000_0000_0000_0002L;
			private A _1 = a;
			private B _2 = b;
			@Override
			public A get() {
				return _1;
			}

			@Override
			public B get2nd() {
				return _2;
			}

			@Override
			public <U> Couple<A, B> set(int index, U u) {
				if (index < 0 || index > size() - 1)
					new IndexBeyondLimitException(index, size());
				if (index == 0)
					return new Double() {
					private static final long serialVersionUID = 0x0000_0000_0000_0002L;
						@SuppressWarnings("unchecked")
						@Override
						public A get() {
							if(!Utility.checkType(super.get(), u, super.get().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
						
							return (A) u;
						}
					};
				return new Double() {
					private static final long serialVersionUID = 0x0000_0000_0000_0002L;
					@SuppressWarnings("unchecked")
					@Override
					public B get2nd() {
						if(!Utility.checkType(super.get2nd(), u, super.get2nd().getClass()))
							new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
									new ClassCastException("incompatible object for set operation"), (Object[]) null);
						return (B) u;
					}
				};
			}
			
			@Override
			public int hashCode() {
				return 0b1000_0000_0000_0000_0000_0000_0000_0000 | get().hashCode() | get2nd().hashCode();
			}

			@Override
			public boolean equals(Object o) {
				if (o instanceof Couple) {
					Couple<?, ?> t = (Couple<?, ?>) o;
					return size() == t.size() && t.get().equals(get()) && t.get2nd().equals(get2nd());
				}
				return false;
			}

			@Override
			public String toString() {
				return "(" + get().toString() + ", " + get2nd() + ")";
			}
		}

		return new Double();
	}

	/*
	 * Date: 28 Apr 2022-----------------------------------------------------------
	 * Time created: 22:32:31--------------------------------------------
	 */
	/**
	 * Compiles and returns a tuple of 3-length.
	 * 
	 * @param <A> the type of first element in the tuple.
	 * @param <B> the type of second element in the tuple.
	 * @param <C> the type of third element in the tuple.
	 * @param a   the first element of the tuple.
	 * @param b   the second element of the tuple.
	 * @param c   the third element of the tuple.
	 * @return a {@code Triple} of the given type whose {@link Tuple#get() elements}
	 *         are the arguments and {@link Tuple#size() length} is 3.
	 */
	static <A, B, C> Triple<A, B, C> of(A a, B b, C c) {
		class Treble implements Triple<A, B, C> {
			private static final long serialVersionUID = 0x0000_0000_0000_0003L;
			private A _1 = a;
			private B _2 = b;
			private C _3 = c;
			@Override
			public A get() {
				return _1;
			}

			@Override
			public B get2nd() {
				return _2;
			}

			@Override
			public C get3rd() {
				return _3;
			}

			@Override
			public <U> Triple<A, B, C> set(int index, U u) {
				if (index < 0 || index > size() - 1)
					new IndexBeyondLimitException(index, size());
				if (index == 0)
					return new Treble() {
					private static final long serialVersionUID = 0x0000_0000_0000_0003L;
						@SuppressWarnings("unchecked")
						@Override
						public A get() {
							if(!Utility.checkType(super.get(), u, super.get().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (A) u;
						}
					};
				else if (index == 1)
					return new Treble() {
					private static final long serialVersionUID = 0x0000_0000_0000_0003L;
						@SuppressWarnings("unchecked")
						@Override
						public B get2nd() {
							if(!Utility.checkType(super.get2nd(), u, super.get2nd().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (B) u;
						}
					};
				return new Treble() {
					private static final long serialVersionUID = 0x0000_0000_0000_0003L;
					@SuppressWarnings("unchecked")
					@Override
					public C get3rd() {
						if(!Utility.checkType(super.get3rd(), u, super.get3rd().getClass()))
							new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
									new ClassCastException("incompatible object for set operation"), (Object[]) null);
						return (C) u;
					}
				};
			}
			
			@Override
			public int hashCode() {
				return 0b1000_0000_0000_0000_0000_0000_0000_0000 | get().hashCode() | get2nd().hashCode() | get3rd().hashCode();
			}

			@Override
			public boolean equals(Object o) {
				if (o instanceof Triple) {
					Triple<?, ?, ?> t = (Triple<?, ?, ?>) o;
					return size() == t.size() && t.get().equals(get()) && t.get2nd().equals(get2nd())
							&& t.get3rd().equals(get3rd());
				}
				return false;
			}

			@Override
			public String toString() {
				return "(" + get().toString() + ", " + get2nd() + ", " + get3rd() + ")";
			}
		}

		return new Treble();
	}

	/*
	 * Date: 28 Apr 2022-----------------------------------------------------------
	 * Time created: 22:33:58--------------------------------------------
	 */
	/**
	 * Compiles and returns a tuple of 4-length.
	 * 
	 * @param <A> the type of first element in the tuple.
	 * @param <B> the type of second element in the tuple.
	 * @param <C> the type of third element in the tuple.
	 * @param <D> the type of fourth element in the tuple.
	 * @param a   the first element of the tuple.
	 * @param b   the second element of the tuple.
	 * @param c   the third element of the tuple.
	 * @param d   the fourth element of the tuple.
	 * @return a {@code Quadruple} of the given type whose {@link Tuple#get()
	 *         elements} are the arguments and {@link Tuple#size() length} is 4.
	 */
	static <A, B, C, D> Quadruple<A, B, C, D> of(A a, B b, C c, D d) {
		class Tetrad implements Quadruple<A, B, C, D> {
			private static final long serialVersionUID = 0x0000_0000_0000_0004L;
			private A _1 = a;
			private B _2 = b;
			private C _3 = c;
			private D _4 = d;
			@Override
			public A get() {
				return _1;
			}

			@Override
			public B get2nd() {
				return _2;
			}

			@Override
			public C get3rd() {
				return _3;
			}

			@Override
			public D get4th() {
				return _4;
			}

			@Override
			public <U> Quadruple<A, B, C, D> set(int index, U u) {
				if (index < 0 || index > size() - 1)
					new IndexBeyondLimitException(index, size());
				if (index == 0)
					return new Tetrad() {
					private static final long serialVersionUID = 0x0000_0000_0000_0004L;
						@SuppressWarnings("unchecked")
						@Override
						public A get() {
							if(!Utility.checkType(super.get(), u, super.get().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (A) u;
						}
					};
				else if (index == 1)
					return new Tetrad() {
					private static final long serialVersionUID = 0x0000_0000_0000_0004L;
						@SuppressWarnings("unchecked")
						@Override
						public B get2nd() {
							if(!Utility.checkType(super.get2nd(), u, super.get2nd().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (B) u;
						}
					};
				else if (index == 2)
					return new Tetrad() {
					private static final long serialVersionUID = 0x0000_0000_0000_0004L;
						@SuppressWarnings("unchecked")
						@Override
						public C get3rd() {
							if(!Utility.checkType(super.get3rd(), u, super.get3rd().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (C) u;
						}
					};
				return new Tetrad() {
					private static final long serialVersionUID = 0x0000_0000_0000_0004L;
					@SuppressWarnings("unchecked")
					@Override
					public D get4th() {
						if(!Utility.checkType(super.get4th(), u, super.get4th().getClass()))
							new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
									new ClassCastException("incompatible object for set operation"), (Object[]) null);
						return (D) u;
					}
				};
			}
			
			@Override
			public int hashCode() {
				return 0b1000_0000_0000_0000_0000_0000_0000_0000 | get().hashCode() | get2nd().hashCode() | get3rd().hashCode()
						| get4th().hashCode();
			}

			@Override
			public boolean equals(Object o) {
				if (o instanceof Quadruple) {
					Quadruple<?, ?, ?, ?> t = (Quadruple<?, ?, ?, ?>) o;
					return size() == t.size() && t.get().equals(get()) && t.get2nd().equals(get2nd())
							&& t.get3rd().equals(get3rd()) && t.get4th().equals(get4th());
				}
				return false;
			}

			@Override
			public String toString() {
				return "(" + get().toString() + ", " + get2nd() + ", " + get3rd() + ", " + get4th() + ")";
			}
		}

		return new Tetrad();
	}

	/*
	 * Date: 28 Apr 2022-----------------------------------------------------------
	 * Time created: 22:35:52--------------------------------------------
	 */
	/**
	 * Compiles and returns a tuple of 5-length.
	 * 
	 * @param <A> the type of first element in the tuple.
	 * @param <B> the type of second element in the tuple.
	 * @param <C> the type of third element in the tuple.
	 * @param <D> the type of fourth element in the tuple.
	 * @param <E> the type of 5th element in the tuple.
	 * @param a   the first element of the tuple.
	 * @param b   the second element of the tuple.
	 * @param c   the third element of the tuple.
	 * @param d   the fourth element of the tuple.
	 * @param e   the 5th element of the tuple.
	 * @return a {@code Quintuple} of the given type whose {@link Tuple#get()
	 *         elements} are the arguments and {@link Tuple#size() length} is 5.
	 */
	static <A, B, C, D, E> Quintuple<A, B, C, D, E> of(A a, B b, C c, D d, E e) {
		class Pentuple implements Quintuple<A, B, C, D, E> {
			private static final long serialVersionUID = 0x0000_0000_0000_0005L;
			private A _1 = a;
			private B _2 = b;
			private C _3 = c;
			private D _4 = d;
			private E _5 = e;
			@Override
			public A get() {
				return _1;
			}

			@Override
			public B get2nd() {
				return _2;
			}

			@Override
			public C get3rd() {
				return _3;
			}

			@Override
			public D get4th() {
				return _4;
			}

			@Override
			public E get5th() {
				return _5;
			}

			@Override
			public <U> Quintuple<A, B, C, D, E> set(int index, U u) {
				if (index < 0 || index > size() - 1)
					new IndexBeyondLimitException(index, size());
				if (index == 0)
					return new Pentuple() {
					private static final long serialVersionUID = 0x0000_0000_0000_0005L;
						@SuppressWarnings("unchecked")
						@Override
						public A get() {
							if(!Utility.checkType(super.get(), u, super.get().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (A) u;
						}
					};
				else if (index == 1)
					return new Pentuple() {
					private static final long serialVersionUID = 0x0000_0000_0000_0005L;
						@SuppressWarnings("unchecked")
						@Override
						public B get2nd() {
							if(!Utility.checkType(super.get2nd(), u, super.get2nd().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (B) u;
						}
					};
				else if (index == 2)
					return new Pentuple() {
					private static final long serialVersionUID = 0x0000_0000_0000_0005L;
						@SuppressWarnings("unchecked")
						@Override
						public C get3rd() {
							if(!Utility.checkType(super.get3rd(), u, super.get3rd().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (C) u;
						}
					};
				else if (index == 3)
					return new Pentuple() {
					private static final long serialVersionUID = 0x0000_0000_0000_0005L;
						@SuppressWarnings("unchecked")
						@Override
						public D get4th() {
							if(!Utility.checkType(super.get4th(), u, super.get4th().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (D) u;
						}
					};
				return new Pentuple() {
					private static final long serialVersionUID = 0x0000_0000_0000_0005L;
					@SuppressWarnings("unchecked")
					@Override
					public E get5th() {
						if(!Utility.checkType(super.get5th(), u, super.get5th().getClass()))
							new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
									new ClassCastException("incompatible object for set operation"), (Object[]) null);
						return (E) u;
					}
				};

			}
			
			@Override
			public int hashCode() {
				return 0b1000_0000_0000_0000_0000_0000_0000_0000 | get().hashCode() | get2nd().hashCode() | get3rd().hashCode()
						| get4th().hashCode() | get5th().hashCode();
			}

			@Override
			public boolean equals(Object o) {
				if (o instanceof Quintuple) {
					Quintuple<?, ?, ?, ?, ?> t = (Quintuple<?, ?, ?, ?, ?>) o;
					return size() == t.size() && t.get().equals(get()) && t.get2nd().equals(get2nd())
							&& t.get3rd().equals(get3rd()) && t.get4th().equals(get4th())
							&& t.get5th().equals(get5th());
				}
				return false;
			}

			@Override
			public String toString() {
				return "(" + get().toString() + ", " + get2nd() + ", " + get3rd() + ", " + get4th() + ", " + get5th()
						+ ")";
			}
		}
		return new Pentuple();
	}

	/*
	 * Date: 28 Apr 2022-----------------------------------------------------------
	 * Time created: 22:37:17--------------------------------------------
	 */
	/**
	 * Compiles and returns a tuple of 6-length.
	 * 
	 * @param <A> the type of first element in the tuple.
	 * @param <B> the type of second element in the tuple.
	 * @param <C> the type of third element in the tuple.
	 * @param <D> the type of fourth element in the tuple.
	 * @param <E> the type of 5th element in the tuple.
	 * @param <F> the type of 6th element in the tuple.
	 * @param a   the first element of the tuple.
	 * @param b   the second element of the tuple.
	 * @param c   the third element of the tuple.
	 * @param d   the fourth element of the tuple.
	 * @param e   the 5th element of the tuple.
	 * @param f   the 6th element in the tuple.
	 * @return a {@code Sextuple} of the given type whose {@link Tuple#get()
	 *         elements} are the arguments and {@link Tuple#size() length} is 6.
	 */
	static <A, B, C, D, E, F> Sextuple<A, B, C, D, E, F> of(A a, B b, C c, D d, E e, F f) {
		class Hextuple implements Sextuple<A, B, C, D, E, F> {
			private static final long serialVersionUID = 0x0000_0000_0000_0006L;
			private A _1 = a;
			private B _2 = b;
			private C _3 = c;
			private D _4 = d;
			private E _5 = e;
			private F _6 = f;
			@Override
			public A get() {
				return _1;
			}

			@Override
			public B get2nd() {
				return _2;
			}

			@Override
			public C get3rd() {
				return _3;
			}

			@Override
			public D get4th() {
				return _4;
			}

			@Override
			public E get5th() {
				return _5;
			}

			@Override
			public F get6th() {
				return _6;
			}

			@Override
			public <U> Sextuple<A, B, C, D, E, F> set(int index, U u) {
				if (index < 0 || index > size() - 1)
					new IndexBeyondLimitException(index, size());
				if (index == 0)
					return new Hextuple() {
					private static final long serialVersionUID = 0x0000_0000_0000_0006L;
						@SuppressWarnings("unchecked")
						@Override
						public A get() {
							if(!Utility.checkType(super.get(), u, super.get().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (A) u;
						}
					};
				else if (index == 1)
					return new Hextuple() {
					private static final long serialVersionUID = 0x0000_0000_0000_0006L;
						@SuppressWarnings("unchecked")
						@Override
						public B get2nd() {
							if(!Utility.checkType(super.get2nd(), u, super.get2nd().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (B) u;
						}
					};
				else if (index == 2)
					return new Hextuple() {
					private static final long serialVersionUID = 0x0000_0000_0000_0006L;
						@SuppressWarnings("unchecked")
						@Override
						public C get3rd() {
							if(!Utility.checkType(super.get3rd(), u, super.get3rd().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (C) u;
						}
					};
				else if (index == 3)
					return new Hextuple() {
					private static final long serialVersionUID = 0x0000_0000_0000_0006L;
						@SuppressWarnings("unchecked")
						@Override
						public D get4th() {
							if(!Utility.checkType(super.get4th(), u, super.get4th().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (D) u;
						}
					};
				else if (index == 4)
					return new Hextuple() {
					private static final long serialVersionUID = 0x0000_0000_0000_0006L;
						@SuppressWarnings("unchecked")
						@Override
						public E get5th() {
							if(!Utility.checkType(super.get5th(), u, super.get5th().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (E) u;
						}
					};
				return new Hextuple() {
					private static final long serialVersionUID = 0x0000_0000_0000_0006L;
					@SuppressWarnings("unchecked")
					@Override
					public F get6th() {
						if(!Utility.checkType(super.get6th(), u, super.get6th().getClass()))
							new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
									new ClassCastException("incompatible object for set operation"), (Object[]) null);
						return (F) u;
					}
				};
			}
			
			@Override
			public int hashCode() {
				return 0b1000_0000_0000_0000_0000_0000_0000_0000 | get().hashCode() | get2nd().hashCode() | get3rd().hashCode()
						| get4th().hashCode() | get5th().hashCode() | get6th().hashCode();
			}

			@Override
			public boolean equals(Object o) {
				if (o instanceof Sextuple) {
					Sextuple<?, ?, ?, ?, ?, ?> t = (Sextuple<?, ?, ?, ?, ?, ?>) o;
					return size() == t.size() && t.get().equals(get()) && t.get2nd().equals(get2nd())
							&& t.get3rd().equals(get3rd()) && t.get4th().equals(get4th()) && t.get5th().equals(get5th())
							&& t.get6th().equals(get6th());
				}
				return false;
			}

			@Override
			public String toString() {
				return "(" + get().toString() + ", " + get2nd() + ", " + get3rd() + ", " + get4th() + ", " + get5th()
						+ ", " + get6th() + ")";
			}
		}
		return new Hextuple();
	}

	/*
	 * Date: 28 Apr 2022-----------------------------------------------------------
	 * Time created: 22:38:25--------------------------------------------
	 */
	/**
	 * Compiles and returns a tuple of 7-length.
	 * 
	 * @param <A> the type of first element in the tuple.
	 * @param <B> the type of second element in the tuple.
	 * @param <C> the type of third element in the tuple.
	 * @param <D> the type of fourth element in the tuple.
	 * @param <E> the type of 5th element in the tuple.
	 * @param <F> the type of 6th element in the tuple.
	 * @param <G> the type of 7th element in the tuple.
	 * @param a   the first element of the tuple.
	 * @param b   the second element of the tuple.
	 * @param c   the third element of the tuple.
	 * @param d   the fourth element of the tuple.
	 * @param e   the 5th element of the tuple.
	 * @param f   the 6th element in the tuple.
	 * @param g   the 7th element in the tuple.
	 * @return a {@code Septuple} of the given type whose {@link Tuple#get()
	 *         elements} are the arguments and {@link Tuple#size() length} is 7.
	 */
	static <A, B, C, D, E, F, G> Septuple<A, B, C, D, E, F, G> of(A a, B b, C c, D d, E e, F f, G g) {
		class Heptuple implements Septuple<A, B, C, D, E, F, G> {
			private static final long serialVersionUID = 0x0000_0000_0000_0007L;
			private A _1 = a;
			private B _2 = b;
			private C _3 = c;
			private D _4 = d;
			private E _5 = e;
			private F _6 = f;
			private G _7 = g;
			@Override
			public A get() {
				return _1;
			}

			@Override
			public B get2nd() {
				return _2;
			}

			@Override
			public C get3rd() {
				return _3;
			}

			@Override
			public D get4th() {
				return _4;
			}

			@Override
			public E get5th() {
				return _5;
			}

			@Override
			public F get6th() {
				return _6;
			}

			@Override
			public G get7th() {
				return _7;
			}

			@Override
			public <U> Septuple<A, B, C, D, E, F, G> set(int index, U u) {
				if (index < 0 || index > size() - 1)
					new IndexBeyondLimitException(index, size());
				if (index == 0)
					return new Heptuple() {
					private static final long serialVersionUID = 0x0000_0000_0000_0007L;
						@SuppressWarnings("unchecked")
						@Override
						public A get() {
							if(!Utility.checkType(super.get(), u, super.get().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (A) u;
						}
					};
				else if (index == 1)
					return new Heptuple() {
					private static final long serialVersionUID = 0x0000_0000_0000_0007L;
						@SuppressWarnings("unchecked")
						@Override
						public B get2nd() {
							if(!Utility.checkType(super.get2nd(), u, super.get2nd().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (B) u;
						}
					};
				else if (index == 2)
					return new Heptuple() {
					private static final long serialVersionUID = 0x0000_0000_0000_0007L;
						@SuppressWarnings("unchecked")
						@Override
						public C get3rd() {
							if(!Utility.checkType(super.get3rd(), u, super.get3rd().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (C) u;
						}
					};
				else if (index == 3)
					return new Heptuple() {
					private static final long serialVersionUID = 0x0000_0000_0000_0007L;
						@SuppressWarnings("unchecked")
						@Override
						public D get4th() {
							if(!Utility.checkType(super.get4th(), u, super.get4th().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (D) u;
						}
					};
				else if (index == 4)
					return new Heptuple() {
					private static final long serialVersionUID = 0x0000_0000_0000_0007L;
						@SuppressWarnings("unchecked")
						@Override
						public E get5th() {
							if(!Utility.checkType(super.get5th(), u, super.get5th().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (E) u;
						}
					};
				else if (index == 5)
					return new Heptuple() {
					private static final long serialVersionUID = 0x0000_0000_0000_0007L;
						@SuppressWarnings("unchecked")
						@Override
						public F get6th() {
							if(!Utility.checkType(super.get6th(), u, super.get6th().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (F) u;
						}
					};
				return new Heptuple() {
					private static final long serialVersionUID = 0x0000_0000_0000_0007L;
					@SuppressWarnings("unchecked")
					@Override
					public G get7th() {
						if(!Utility.checkType(super.get7th(), u, super.get7th().getClass()))
							new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
									new ClassCastException("incompatible object for set operation"), (Object[]) null);
						return (G) u;
					}
				};

			}
			
			@Override
			public int hashCode() {
				return 0b1000_0000_0000_0000_0000_0000_0000_0000 | get().hashCode() | get2nd().hashCode() | get3rd().hashCode()
						| get4th().hashCode() | get5th().hashCode() | get6th().hashCode() | get7th().hashCode();
			}

			@Override
			public boolean equals(Object o) {
				if (o instanceof Septuple) {
					Septuple<?, ?, ?, ?, ?, ?, ?> t = (Septuple<?, ?, ?, ?, ?, ?, ?>) o;
					return size() == t.size() && t.get().equals(get()) && t.get2nd().equals(get2nd())
							&& t.get3rd().equals(get3rd()) && t.get4th().equals(get4th()) && t.get5th().equals(get5th())
							&& t.get6th().equals(get6th()) && t.get7th().equals(get7th());
				}
				return false;
			}

			@Override
			public String toString() {
				return "(" + get().toString() + ", " + get2nd() + ", " + get3rd() + ", " + get4th() + ", " + get5th()
						+ ", " + get6th() + ", " + get7th() + ")";
			}
		}

		return new Heptuple();
	}

	/*
	 * Date: 28 Apr 2022-----------------------------------------------------------
	 * Time created: 22:39:29--------------------------------------------
	 */
	/**
	 * Compiles and returns a tuple of 8-length.
	 * 
	 * @param <A> the type of first element in the tuple.
	 * @param <B> the type of second element in the tuple.
	 * @param <C> the type of third element in the tuple.
	 * @param <D> the type of fourth element in the tuple.
	 * @param <E> the type of 5th element in the tuple.
	 * @param <F> the type of 6th element in the tuple.
	 * @param <G> the type of 7th element in the tuple.
	 * @param <H> the type of 8th element in the tuple.
	 * @param a   the first element of the tuple.
	 * @param b   the second element of the tuple.
	 * @param c   the third element of the tuple.
	 * @param d   the fourth element of the tuple.
	 * @param e   the 5th element of the tuple.
	 * @param f   the 6th element in the tuple.
	 * @param g   the 7th element in the tuple.
	 * @param h   the 8th element in the tuple.
	 * @return a {@code Octuple} of the given type whose {@link Tuple#get()
	 *         elements} are the arguments and {@link Tuple#size() length} is 8.
	 */
	static <A, B, C, D, E, F, G, H> Octuple<A, B, C, D, E, F, G, H> of(A a, B b, C c, D d, E e, F f, G g, H h) {
		class Octa implements Octuple<A, B, C, D, E, F, G, H> {
			private static final long serialVersionUID = 0x0000_0000_0000_0008L;
			private A _1 = a;
			private B _2 = b;
			private C _3 = c;
			private D _4 = d;
			private E _5 = e;
			private F _6 = f;
			private G _7 = g;
			private H _8 = h;
			@Override
			public A get() {
				return _1;
			}

			@Override
			public B get2nd() {
				return _2;
			}

			@Override
			public C get3rd() {
				return _3;
			}

			@Override
			public D get4th() {
				return _4;
			}

			@Override
			public E get5th() {
				return _5;
			}

			@Override
			public F get6th() {
				return _6;
			}

			@Override
			public G get7th() {
				return _7;
			}

			@Override
			public H get8th() {
				return _8;
			}

			@Override
			public <U> Octuple<A, B, C, D, E, F, G, H> set(int index, U u) {
				if (index < 0 || index > size() - 1)
					new IndexBeyondLimitException(index, size());
				if (index == 0)
					return new Octa() {
					private static final long serialVersionUID = 0x0000_0000_0000_0008L;
						@SuppressWarnings("unchecked")
						@Override
						public A get() {
							if(!Utility.checkType(super.get(), u, super.get().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (A) u;
						}
					};
				else if (index == 1)
					return new Octa() {
					private static final long serialVersionUID = 0x0000_0000_0000_0008L;
						@SuppressWarnings("unchecked")
						@Override
						public B get2nd() {
							if(!Utility.checkType(super.get2nd(), u, super.get2nd().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (B) u;
						}
					};
				else if (index == 2)
					return new Octa() {
					private static final long serialVersionUID = 0x0000_0000_0000_0008L;
						@SuppressWarnings("unchecked")
						@Override
						public C get3rd() {
							if(!Utility.checkType(super.get3rd(), u, super.get3rd().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (C) u;
						}
					};
				else if (index == 3)
					return new Octa() {
					private static final long serialVersionUID = 0x0000_0000_0000_0008L;
						@SuppressWarnings("unchecked")
						@Override
						public D get4th() {
							if(!Utility.checkType(super.get4th(), u, super.get4th().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (D) u;
						}
					};
				else if (index == 4)
					return new Octa() {
					private static final long serialVersionUID = 0x0000_0000_0000_0008L;
						@SuppressWarnings("unchecked")
						@Override
						public E get5th() {
							if(!Utility.checkType(super.get5th(), u, super.get5th().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (E) u;
						}
					};
				else if (index == 5)
					return new Octa() {
					private static final long serialVersionUID = 0x0000_0000_0000_0008L;
						@SuppressWarnings("unchecked")
						@Override
						public F get6th() {
							if(!Utility.checkType(super.get6th(), u, super.get6th().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (F) u;
						}
					};
				else if (index == 6)
					return new Octa() {
					private static final long serialVersionUID = 0x0000_0000_0000_0008L;
						@SuppressWarnings("unchecked")
						@Override
						public G get7th() {
							if(!Utility.checkType(super.get7th(), u, super.get7th().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (G) u;
						}
					};
				return new Octa() {
					private static final long serialVersionUID = 0x0000_0000_0000_0008L;
					@SuppressWarnings("unchecked")
					@Override
					public H get8th() {
						if(!Utility.checkType(super.get8th(), u, super.get8th().getClass()))
							new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
									new ClassCastException("incompatible object for set operation"), (Object[]) null);
						return (H) u;
					}
				};

			}
			
			@Override
			public int hashCode() {
				return 0b1000_0000_0000_0000_0000_0000_0000_0000 | get().hashCode() | get2nd().hashCode() | get3rd().hashCode()
						| get4th().hashCode() | get5th().hashCode() | get6th().hashCode() | get7th().hashCode()
						| get8th().hashCode();
			}

			@Override
			public boolean equals(Object o) {
				if (o instanceof Octuple) {
					Octuple<?, ?, ?, ?, ?, ?, ?, ?> t = (Octuple<?, ?, ?, ?, ?, ?, ?, ?>) o;
					return size() == t.size() && t.get().equals(get()) && t.get2nd().equals(get2nd())
							&& t.get3rd().equals(get3rd()) && t.get4th().equals(get4th()) && t.get5th().equals(get5th())
							&& t.get6th().equals(get6th()) && t.get7th().equals(get7th())
							&& t.get8th().equals(get8th());
				}
				return false;
			}

			@Override
			public String toString() {
				return "(" + get().toString() + ", " + get2nd() + ", " + get3rd() + ", " + get4th() + ", " + get5th()
						+ ", " + get6th() + ", " + get7th() + ", " + get8th() + ")";
			}
		}

		return new Octa();
	}

	/*
	 * Date: 28 Apr 2022-----------------------------------------------------------
	 * Time created: 22:40:33--------------------------------------------
	 */
	/**
	 * Compiles and returns a tuple of 9-length.
	 * 
	 * @param <A> the type of first element in the tuple.
	 * @param <B> the type of second element in the tuple.
	 * @param <C> the type of third element in the tuple.
	 * @param <D> the type of fourth element in the tuple.
	 * @param <E> the type of 5th element in the tuple.
	 * @param <F> the type of 6th element in the tuple.
	 * @param <G> the type of 7th element in the tuple.
	 * @param <H> the type of 8th element in the tuple.
	 * @param <I> the type of 9th element in the tuple.
	 * @param a   the first element of the tuple.
	 * @param b   the second element of the tuple.
	 * @param c   the third element of the tuple.
	 * @param d   the fourth element of the tuple.
	 * @param e   the 5th element of the tuple.
	 * @param f   the 6th element in the tuple.
	 * @param g   the 7th element in the tuple.
	 * @param h   the 8th element in the tuple.
	 * @param i   9th element in the tuple.
	 * @return a {@code Nonuple} of the given type whose {@link Tuple#get()
	 *         elements} are the arguments and {@link Tuple#size() length} is 9.
	 */
	static <A, B, C, D, E, F, G, H, I> Nonuple<A, B, C, D, E, F, G, H, I> of(A a, B b, C c, D d, E e, F f, G g, H h,
			I i) {
		class Nonad implements Nonuple<A, B, C, D, E, F, G, H, I> {
			private static final long serialVersionUID = 0x0000_0000_0000_0009L;
			private A _1 = a;
			private B _2 = b;
			private C _3 = c;
			private D _4 = d;
			private E _5 = e;
			private F _6 = f;
			private G _7 = g;
			private H _8 = h;
			private I _9 = i;
			@Override
			public A get() {
				return _1;
			}

			@Override
			public B get2nd() {
				return _2;
			}

			@Override
			public C get3rd() {
				return _3;
			}

			@Override
			public D get4th() {
				return _4;
			}

			@Override
			public E get5th() {
				return _5;
			}

			@Override
			public F get6th() {
				return _6;
			}

			@Override
			public G get7th() {
				return _7;
			}

			@Override
			public H get8th() {
				return _8;
			}

			@Override
			public I get9th() {
				return _9;
			}

			@Override
			public <U> Nonuple<A, B, C, D, E, F, G, H, I> set(int index, U u) {
				if (index < 0 || index > size() - 1)
					new IndexBeyondLimitException(index, size());
				if (index == 0)
					return new Nonad() {
					private static final long serialVersionUID = 0x0000_0000_0000_0009L;
						@SuppressWarnings("unchecked")
						@Override
						public A get() {
							if(!Utility.checkType(super.get(), u, super.get().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (A) u;
						}
					};
				else if (index == 1)
					return new Nonad() {
					private static final long serialVersionUID = 0x0000_0000_0000_0009L;
						@SuppressWarnings("unchecked")
						@Override
						public B get2nd() {
							if(!Utility.checkType(super.get2nd(), u, super.get2nd().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (B) u;
						}
					};
				else if (index == 2)
					return new Nonad() {
					private static final long serialVersionUID = 0x0000_0000_0000_0009L;
						@SuppressWarnings("unchecked")
						@Override
						public C get3rd() {
							if(!Utility.checkType(super.get3rd(), u, super.get3rd().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (C) u;
						}
					};
				else if (index == 3)
					return new Nonad() {
					private static final long serialVersionUID = 0x0000_0000_0000_0009L;
						@SuppressWarnings("unchecked")
						@Override
						public D get4th() {
							if(!Utility.checkType(super.get4th(), u, super.get4th().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (D) u;
						}
					};
				else if (index == 4)
					return new Nonad() {
					private static final long serialVersionUID = 0x0000_0000_0000_0009L;
						@SuppressWarnings("unchecked")
						@Override
						public E get5th() {
							if(!Utility.checkType(super.get5th(), u, super.get5th().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (E) u;
						}
					};
				else if (index == 5)
					return new Nonad() {
					private static final long serialVersionUID = 0x0000_0000_0000_0009L;
						@SuppressWarnings("unchecked")
						@Override
						public F get6th() {
							if(!Utility.checkType(super.get6th(), u, super.get6th().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (F) u;
						}
					};
				else if (index == 6)
					return new Nonad() {
					private static final long serialVersionUID = 0x0000_0000_0000_0009L;
						@SuppressWarnings("unchecked")
						@Override
						public G get7th() {
							if(!Utility.checkType(super.get7th(), u, super.get7th().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (G) u;
						}
					};
				else if (index == 7)
					return new Nonad() {
					private static final long serialVersionUID = 0x0000_0000_0000_0009L;
						@SuppressWarnings("unchecked")
						@Override
						public H get8th() {
							if(!Utility.checkType(super.get8th(), u, super.get8th().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (H) u;
						}
					};
				return new Nonad() {
					private static final long serialVersionUID = 0x0000_0000_0000_0009L;
					@SuppressWarnings("unchecked")
					@Override
					public I get9th() {
						if(!Utility.checkType(super.get9th(), u, super.get9th().getClass()))
							new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
									new ClassCastException("incompatible object for set operation"), (Object[]) null);
						return (I) u;
					}
				};

			}
			
			@Override
			public int hashCode() {
				return 0b1000_0000_0000_0000_0000_0000_0000_0000 | get().hashCode() | get2nd().hashCode() | get3rd().hashCode()
						| get4th().hashCode() | get5th().hashCode() | get6th().hashCode() | get7th().hashCode()
						| get8th().hashCode() | get9th().hashCode();
			}

			@Override
			public boolean equals(Object o) {
				if (o instanceof Nonuple) {
					Nonuple<?, ?, ?, ?, ?, ?, ?, ?, ?> t = (Nonuple<?, ?, ?, ?, ?, ?, ?, ?, ?>) o;
					return size() == t.size() && t.get().equals(get()) && t.get2nd().equals(get2nd())
							&& t.get3rd().equals(get3rd()) && t.get4th().equals(get4th()) && t.get5th().equals(get5th())
							&& t.get6th().equals(get6th()) && t.get7th().equals(get7th()) && t.get8th().equals(get8th())
							&& t.get9th().equals(get9th());
				}
				return false;
			}

			@Override
			public String toString() {
				return "(" + get().toString() + ", " + get2nd() + ", " + get3rd() + ", " + get4th() + ", " + get5th()
						+ ", " + get6th() + ", " + get7th() + ", " + get8th() + ", " + get9th() + ")";
			}
		}

		return new Nonad();
	}

	/*
	 * Date: 28 Apr 2022-----------------------------------------------------------
	 * Time created: 22:41:35--------------------------------------------
	 */
	/**
	 * Compiles and returns a tuple of <i>n</i>-length.
	 * 
	 * @param <T>               the type of the first element in the tuple.
	 * @param <U>               the type of the first element in the tuple that
	 *                          holds the rest of the elements.
	 * @param <V>               the type of tuple that holds the rest of the
	 *                          elements.
	 * @param t                 the first element.
	 * @param remainingElements a tuple containing the rest of the elements
	 *                          excluding the the first element.
	 * @return a {@code NTuple} of the given types that has the first argument as
	 *         it's first element and the second argument as it's
	 *         {@link NTuple#getTuple() remaining element}
	 */
	static <T, U, V extends Tuple<U>> NTuple<T, U, V> of(T t, V remainingElements) {
		class Nth implements NTuple<T, U, V> {
			private static final long serialVersionUID = 0x0000_0000_0000_000AL;
			private T element = t;
			private V container = remainingElements;
			@Override
			public T get() {
				return element;
			}

			@Override
			public V getTuple() {
				return container;
			}

			@Override
			public <W> NTuple<T, U, V> set(int index, W u) {
				if (index < 0 || index > size() - 1)
					new IndexBeyondLimitException(index, size());
				if (index == 0)
					return new Nth() {
					private static final long serialVersionUID = 0x0000_0000_0000_000AL;
						@SuppressWarnings("unchecked")
						@Override
						public T get() {
							if(!Utility.checkType(super.get(), u, super.get().getClass()))
								new mathaid.BaseException(mathaid.ExceptionMessage.ERROR,
										new ClassCastException("incompatible object for set operation"), (Object[]) null);
							return (T) u;
						}
					};
				return new Nth() {
					private static final long serialVersionUID = 0x0000_0000_0000_000AL;
					@SuppressWarnings("unchecked")
					@Override
					public V getTuple() {
						return (V) Nth.this.getTuple().set(index - 1, u);
					}
				};

			}
			
			@Override
			public int hashCode() {
				return 0b1000_0000_0000_0000_0000_0000_0000_0000 ^ get().hashCode() ^ ~getTuple().hashCode();
			}

			@Override
			public boolean equals(Object o) {
				if (o instanceof NTuple) {
					NTuple<?, ?, ?> t = (NTuple<?, ?, ?>) o;
					return size() == t.size() &&
							(t.get() == null ? get() == null : t.get().equals(get())) &&
							(t.getTuple() == null ? getTuple() == null
							: t.getTuple().equals(getTuple()));
				}
				return false;
			}

			@Override
			public String toString() {
				String s = getTuple().toString();//.replaceAll("\\(", ", ").replaceAll("\\)", "");
				return "(" + get().toString() + ", " + s + ")";
			}
		}

		return new Nth();
	}

	/*
	 * Date: 28 Apr 2022-----------------------------------------------------------
	 * Time created: 21:46:48--------------------------------------------
	 */
	/**
	 * Supplies the first element of this tuple.
	 * 
	 * @return the first element of this {@code Tuple}.
	 */
	T get();

	/*
	 * Date: 29 Apr 2022-----------------------------------------------------------
	 * Time created: 01:14:40--------------------------------------------
	 */
	/**
	 * Provides random access to tuple elements. It is recommended not to use
	 * this method as due to java's compiler enforcements, a general object
	 * is returned which will compel users to always cast their result.
	 * 
	 * @param index the zero-based index to access
	 * @return the element as a {@code java.lang.Object}
	 * @throws IndexOutOfBoundsException if
	 *                                   <code>index &lt; 0 || index &gt; size() - 1</code>.
	 */
	default Object get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index > size() - 1)
			new IndexBeyondLimitException(index, size());
		return get();
	}

	/*
	 * Date: 29 Apr 2022-----------------------------------------------------------
	 * Time created: 08:45:23--------------------------------------------
	 */
	/**
	 * Destroys the current tuple object, creates a new one with all the old element
	 * - except the element at the given index which is replaced by the argument
	 * object - and returns the newly created object using the old value for
	 * type-checking the argument and the value at the index to be replaced. Note:
	 * this is not a mutable operation since the last tuple was never changed.
	 * <p>
	 * Although this method enforces some sort of type safety, it does so partially.
	 * Generic types will never throw a cast exception as they are the same
	 * to this tuple. Code such as:
	 * <pre>
	 * Tuple&lt;List&lt;String&gt;&gt; tuple = Tuple.of(List.of("item"))&semi;
	 * tuple = tuple.set(0, Collections.unmodifiableCollection(List.of("replacement")), Collection.class)&semi;
	 * </pre>
	 * <pre>
	 * Tuple&lt;List&lt;String&gt;&gt; tuple = Tuple.of(List.of("item"))&semi;
	 * tuple = tuple.set(0, Set.of("replacement"), Collection.class)&semi;
	 * </pre>
	 * will throw a {@code ClassCastException} (as they should). But code such as:
	 * <pre>
	 * Tuple&lt;List&lt;String&gt;&gt; tuple = Tuple.of(List.of("item"))&semi;
	 * tuple = tuple.set(0, 25, Integer.class)&semi;
	 * </pre>
	 * will not throw an exception.
	 * </p>
	 * <p>
	 * This method should generally be avoided as this method is bound to be
	 * performance intensive, especially in an environment where tuples are use
	 * for performance intensive calculations.
	 * </p>
	 * 
	 * @param <U>   the type of object to replace
	 * @param index the index in which the object to be replaced is located.
	 * @param u     the replacement object.
	 * @param cast the class (or type) of the value expected at this index.
	 * @return a newly created tuple of the same length as the one on which this
	 *         method was called, with the given object argument in the specified
	 *         index.
	 * @throws IndexOutOfBoundsException if
	 *                                   <code>index &lt; 0 || index &gt; size() - 1</code>.
	 * @throws ClassCastException if the given cast is not the same class as the
	 * value to be overwritten.
	 */
	<U> Tuple<T> set(int index, U u) throws IndexOutOfBoundsException;

	/*
	 * Date: 28 Apr 2022-----------------------------------------------------------
	 * Time created: 21:47:44--------------------------------------------
	 */
	/**
	 * Gets the size of the tuple.
	 * 
	 * @return the length of this {@code Tuple}
	 * @implNote This method should always be overridden in all sub-interfaces and
	 *           implementing classes as it is not dynamic
	 */
	default int size() {
		return 1;
	}
}
