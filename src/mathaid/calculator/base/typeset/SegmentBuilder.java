/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import mathaid.calculator.base.util.Utility;

/*
 * Date: 13 Aug 2022-----------------------------------------------------------
 * Time created: 16:16:18--------------------------------------------
 * Package: mathaid.calculator.base.typeset ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: SegmentBuilder.java ------------------------------------------------------
 * Class name: SegmentBuilder ------------------------------------------------
 * 
 * Some methods are implemented 3 or more times in this class namely:
 * 1. segmentAt - 4 overloads
 * 2. setError - 3 overloads
 * 3. setFocus - 3 overloads
 * 4. delete - 4 overloads
 * 5. insert - 8 overloads
 * 6. replace - 8 overloads
 * They usually follow this pattern: a static implementation, a public one with an int varargs and another public one with a List parameter.
 * These are primarily implemented in the static implementation which properly traverses the segment tree.
 */
/**
 * Acts as a mutable semi-wrapper for {@code LinkedSegment} objects and
 * retrofits them with additional capabilities while enhancing ease-of-use. For
 * example, this class supports mutative deletion, mutative insertion, mutative
 * appendage, mutative replacement and other mutative operations that take the
 * form of {@code setXxx} (such as {@link #setError(boolean, List)}), proper
 * traversal of top-level {@code LinkedSegment}s and their children whereby the
 * children can be the target of a set operation (this is not possible using the
 * vanilla {@code LinkedSegment} interface). This class is to
 * {@link LinkedSegment} as {@link StringBuilder} is to {@link String}.
 * <p>
 * An example of this class in action is when the quadratic formula is built
 * using this class:
 *<pre>
 *	<code>
 *		DigitPunc dp = new DigitPunc();
 *		SegmentBuilder formula = new SegmentBuilder()
 *				.append(Segments.freeVariable("x", "x"))
 *				.append(Segments.operator("=", "="));
 *		SegmentBuilder numerator = new SegmentBuilder(Digits.prefixMinus())
 *				.append(Segments.freeVariable("b", "b"))
 *				.append(Segments.operator("\\pm", "-"));// To prevent errors during toString
 *		SegmentBuilder radicand = new SegmentBuilder(
 *				Segments.pow(Segments.freeVariable("b", "b"), Digits.integer('2', dp)))
 *				.append(Segments.operator("-", "-")).append(Digits.integer('4', dp))
 *				.append(Segments.freeVariable("a", "a")).append(Segments.freeVariable("c", "c"));
 *		numerator.append(Segments.sqrt(radicand.toSegment()));
 *		formula.append(Segments.fraction(numerator.toSegment(), Digits.integer('2', dp)
 *				.concat(Segments.freeVariable("a", "a"))));
 *		
 *		List<Integer> l = new ArrayList<>(Arrays.asList(-1));
 *		formula.toSegment().toString(out, null, l);//prints: x= Rational[-b- Sqrt[b ^(2 )-4*ac ] ,2*a ]
 *		out.println();
 *		l.clear();
 *		l.add(-1);
 *		formula.toSegment().format(out, Formatter.empty(), l);//prints: x= \frac{\textrm{-}b\pm \sqrt{b ^{2 }-4ac } }{2a}
 *	</code>
 *</pre>
 * Which when displayed in a TeX capable program will render
 * as:
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class SegmentBuilder implements Iterable<LinkedSegment> {

	public SegmentBuilder(Segment segment) {
		this(new LinkedSegmentAdapter(segment));
	}

	public SegmentBuilder(LinkedSegment s) {
		this.head = s;
	}

	public SegmentBuilder() {
		this(new Empty());
	}

	public LinkedSegment toSegment() {
		return head;
	}

	public boolean isEmpty() {
		try {
			return head.length() == 0;
		} catch (NullPointerException e) {
			return false;
		}
	}

	public int length() {
		return head.length();
	}

	private static LinkedSegment segmentAt(SegmentBuilder headToPermute, List<Integer> indexes)
			throws IndexOutOfBoundsException {
		int sibling = indexes.remove(0);
		int descendant = -1;
		try {
			descendant = indexes.remove(0);
		} catch (IndexOutOfBoundsException e) {
		}

		if (sibling < 0)
			throw new IndexOutOfBoundsException("Negative sibling index found");

		if (indexes.isEmpty()) {
			try {
				return headToPermute.segmentAt(sibling);
			} catch (IndexOutOfBoundsException e) {
				throw new IndexOutOfBoundsException("insertion index not found");
			}
		}

		if (descendant < 0)
			throw new IndexOutOfBoundsException("Negative descedant index found");

		return segmentAt(new SegmentBuilder(headToPermute.segmentAt(sibling).getChildren()[descendant]), indexes);
	}

	public LinkedSegment segmentAt(int... index) throws IndexOutOfBoundsException {
		ArrayList<Integer> l = new ArrayList<>();
		for (int i : index)
			l.add(i);
		return segmentAt(this, l);
	}

	public LinkedSegment segmentAt(List<Integer> pos) throws IndexOutOfBoundsException {
		return segmentAt(this, pos);
	}

	public LinkedSegment segmentAt(int index) throws IndexOutOfBoundsException {
		return head.segmentAt(index);
	}

	private static LinkedSegment setError(LinkedSegment headToPermute, boolean err, List<Integer> indexes)
			throws IndexOutOfBoundsException {
		int sibling = indexes.remove(0);
		int descendant = -1;
		try {
			descendant = indexes.remove(0);
		} catch (IndexOutOfBoundsException e) {
		}

		if (sibling < 0)
			throw new IndexOutOfBoundsException("Negative sibling index found");

		if (indexes.isEmpty()) {
			try {
				return headToPermute.setError(sibling, err);
			} catch (IndexOutOfBoundsException e) {
				throw new IndexOutOfBoundsException("insertion index not found");
			}
		}

		if (descendant < 0)
			throw new IndexOutOfBoundsException("Negative descedant index found");

		return new SegmentBuilder(headToPermute)
				.replace(sibling,
						headToPermute.segmentAt(sibling).setChild(descendant,
								setError(headToPermute.subsegment(sibling).getChildren()[descendant], err, indexes)))
				.toSegment();
	}

	public SegmentBuilder setError(boolean err, int... index) throws IndexOutOfBoundsException {
		ArrayList<Integer> l = new ArrayList<>();
		for (int i : index)
			l.add(i);
		head = setError(head, err, l);
		return this;
	}

	public SegmentBuilder setError(boolean err, List<Integer> pos) throws IndexOutOfBoundsException {
		head = setError(head, err, pos);
		return this;
	}

	private static LinkedSegment setFocus(LinkedSegment headToPermute, boolean f, List<Integer> indexes)
			throws IndexOutOfBoundsException {
		int sibling = indexes.remove(0);
		int descendant = -1;
		try {
			descendant = indexes.remove(0);
		} catch (IndexOutOfBoundsException e) {
		}

		if (sibling < 0)
			throw new IndexOutOfBoundsException("Negative sibling index found");

		if (indexes.isEmpty()) {
			try {
				return headToPermute.setFocus(sibling, f);
			} catch (IndexOutOfBoundsException e) {
				throw new IndexOutOfBoundsException("insertion index not found");
			}
		}

		if (descendant < 0)
			throw new IndexOutOfBoundsException("Negative descedant index found");

		return new SegmentBuilder(headToPermute)
				.replace(sibling,
						headToPermute.segmentAt(sibling).setChild(descendant,
								setFocus(headToPermute.subsegment(sibling).getChildren()[descendant], f, indexes)))
				.toSegment();
	}

	public SegmentBuilder setFocus(boolean focus, int... index) throws IndexOutOfBoundsException {
		ArrayList<Integer> l = new ArrayList<>();
		for (int i : index)
			l.add(i);
		head = setFocus(head, focus, l);
		return this;
	}

	public SegmentBuilder setFocus(boolean focus, List<Integer> pos) throws IndexOutOfBoundsException {
		head = setFocus(head, focus, pos);
		return this;
	}

	private static LinkedSegment delete(LinkedSegment headToPermute, List<Integer> indexes)
			throws IndexOutOfBoundsException {
		int sibling = indexes.remove(0);
		int descendant = -1;
		try {
			descendant = indexes.remove(0);
		} catch (IndexOutOfBoundsException e) {
		}

		if (sibling < 0)
			throw new IndexOutOfBoundsException("Negative sibling index found");

		if (indexes.isEmpty()) {
			try {
				return new SegmentBuilder(headToPermute).delete(sibling).toSegment();
			} catch (IndexOutOfBoundsException e) {
				throw new IndexOutOfBoundsException("insertion index not found");
			}
		}

		if (descendant < 0)
			throw new IndexOutOfBoundsException("Negative descedant index found");

		return new SegmentBuilder(headToPermute)
				.replace(sibling,
						headToPermute.segmentAt(sibling).setChild(descendant,
								delete(headToPermute.subsegment(sibling).getChildren()[descendant], indexes)))
				.toSegment();
	}

	private SegmentBuilder delete(int index) throws IndexOutOfBoundsException {
		if (index == 0)
			if (length() == 1)
				head = new Empty();
			else
				head = head.getSibling();
		else if (isEmpty())
			throw new IndexOutOfBoundsException(index);
		else {
			LinkedSegment segmentAtIndex = head.subsegment(index);
			head = head.setSibling(index - 1, segmentAtIndex.getSibling());
		}
		return this;
	}

	public SegmentBuilder delete(int... index) throws IndexOutOfBoundsException {
		ArrayList<Integer> l = new ArrayList<>();
		for (int i : index)
			l.add(i);
		head = delete(head, l);
		return this;
	}

	/*
	 * Date: 13 Aug 2022-----------------------------------------------------------
	 * Time created: 16:16:18--------------------------------------------
	 */
	/**
	 * @param pos
	 */
	public SegmentBuilder delete(List<Integer> pos) throws IndexOutOfBoundsException {
		head = delete(head, pos);
		return this;
	}

	public SegmentBuilder deleteAll() {
		head = new Empty();
		return this;
	}

	private static LinkedSegment insert(LinkedSegment headToPermute, List<Integer> indexes, LinkedSegment newSegment)
			throws IndexOutOfBoundsException {
		int sibling = indexes.remove(0);
		int descendant = -1;
		try {
			descendant = indexes.remove(0);
		} catch (IndexOutOfBoundsException e) {
		}

		if (sibling < 0)
			throw new IndexOutOfBoundsException("Negative sibling index found");

		if (indexes.isEmpty()) {
			try {
				return new SegmentBuilder(headToPermute).insert(sibling, newSegment).toSegment();
			} catch (IndexOutOfBoundsException e) {
				throw new IndexOutOfBoundsException("insertion index not found");
			}
		}

		if (descendant < 0)
			throw new IndexOutOfBoundsException("Negative descedant index found");

		return new SegmentBuilder(headToPermute)
				.replace(sibling,
						headToPermute.segmentAt(sibling).setChild(descendant, insert(
								headToPermute.subsegment(sibling).getChildren()[descendant], indexes, newSegment)))
				.toSegment();
	}

	private SegmentBuilder insert(int index, LinkedSegment s) throws IndexOutOfBoundsException {
		if (index == 0) {
			head = Objects.requireNonNull(s, "Cannot insert a null value").concat(head);
		} else if ((!isEmpty()) && index < head.length()) {
			LinkedSegment segmentAtIndex = head.subsegment(index);
			head = head.setSibling(index - 1,
					Objects.requireNonNull(s, "Cannot insert a null value").concat(segmentAtIndex));
		} else if (index == length()) {
			head = head.concat(Objects.requireNonNull(s, "Cannot insert a null value"));
		}
		return this;
	}

	public SegmentBuilder insert(LinkedSegment seg, int... index) throws IndexOutOfBoundsException {
		ArrayList<Integer> l = new ArrayList<>();
		for (int i : index)
			l.add(i);
		head = insert(head, l, Objects.requireNonNull(seg, "Cannot insert a null value"));
//		System.out.println(Utility.toCSV(l));
//		Integer[] il = new Integer[l.size()];
//		il = l.toArray(il);
//		try {
//			head = insert(head, l, Objects.requireNonNull(seg, "Cannot insert a null value"));
//		} catch (NullPointerException e) {
//			IndexOutOfBoundsException iob = new IndexOutOfBoundsException(
//					new StringBuilder(e.getMessage())
//					.append(Utility.toCSV(Arrays.asList(il)))
//					.toString()
//			);
//			iob.initCause(e);
//			throw iob;
//		}
		return this;
	}

	public SegmentBuilder insert(List<Integer> pos, LinkedSegment newSegment) throws IndexOutOfBoundsException {
		head = insert(head, pos, newSegment);
		return this;
	}

	public SegmentBuilder insert(List<Integer> pos, SegmentBuilder s) throws IndexOutOfBoundsException {
		return insert(pos, s.toSegment());
	}

	public SegmentBuilder insert(SegmentBuilder s, int... index) throws IndexOutOfBoundsException {
		return insert(s.toSegment(), index);
	}

	public SegmentBuilder insert(List<Integer> pos, Segment s) throws IndexOutOfBoundsException {
		return insert(pos, new LinkedSegmentAdapter(s));
	}

	public SegmentBuilder insert(Segment s, int... index) throws IndexOutOfBoundsException {
		return insert(new LinkedSegmentAdapter(s), index);
	}

	private static LinkedSegment replace(LinkedSegment headToPermute, List<Integer> indexes, LinkedSegment newSegment)
			throws IndexOutOfBoundsException {
		int sibling = indexes.remove(0);
		int descendant = -1;
		try {
			descendant = indexes.remove(0);
		} catch (IndexOutOfBoundsException e) {
		}

		if (sibling < 0)
			throw new IndexOutOfBoundsException("Negative sibling index found");

		if (indexes.isEmpty()) {
			try {
				return new SegmentBuilder(headToPermute).replace(sibling, newSegment).toSegment();
			} catch (IndexOutOfBoundsException e) {
				throw new IndexOutOfBoundsException("insertion index not found");
			}
		}

		if (descendant < 0)
			throw new IndexOutOfBoundsException("Negative descedant index found");

		return new SegmentBuilder(headToPermute)
				.replace(sibling,
						headToPermute.segmentAt(sibling).setChild(descendant, replace(
								headToPermute.subsegment(sibling).getChildren()[descendant], indexes, newSegment)))
				.toSegment();
	}

	private SegmentBuilder replace(int index, LinkedSegment replacement) throws IndexOutOfBoundsException {
		return delete(index).insert(index, Objects.requireNonNull(replacement, "Cannot replace with a null value"));
	}

	public SegmentBuilder replace(LinkedSegment seg, int... index) throws IndexOutOfBoundsException {
		ArrayList<Integer> l = new ArrayList<>();
		for (int i : index)
			l.add(i);
		head = replace(head, l, Objects.requireNonNull(seg, "Cannot replace with a null value"));
		return this;
	}

	public SegmentBuilder replace(List<Integer> pos, LinkedSegment newSegment) throws IndexOutOfBoundsException {
		head = replace(head, pos, newSegment);
		return this;
	}

	public SegmentBuilder replace(List<Integer> pos, SegmentBuilder s) throws IndexOutOfBoundsException {
		return replace(pos, s.toSegment());
	}

	public SegmentBuilder replace(SegmentBuilder s, int... index) throws IndexOutOfBoundsException {
		return replace(s.toSegment(), index);
	}

	public SegmentBuilder replace(List<Integer> pos, Segment s) throws IndexOutOfBoundsException {
		return replace(pos, new LinkedSegmentAdapter(s));
	}

	public SegmentBuilder replace(Segment s, int... index) throws IndexOutOfBoundsException {
		return replace(new LinkedSegmentAdapter(s), index);
	}

	public SegmentBuilder append(LinkedSegment s) {
		head = head.concat(Objects.requireNonNull(s, "Cannot append to null"));
		return this;
	}

	public SegmentBuilder append(SegmentBuilder s) {
		return append(s.toSegment());
	}

	public SegmentBuilder append(Segment s) {
		return append(new LinkedSegmentAdapter(s));
	}

	public SegmentBuilder prepend(LinkedSegment s) {
		head = Objects.requireNonNull(s, "Cannot prepend to null").concat(head);
		return this;
	}

	public SegmentBuilder prepend(SegmentBuilder s) {
		return prepend(s.toSegment());
	}

	public SegmentBuilder prepend(Segment s) {
		return prepend(new LinkedSegmentAdapter(s));
	}

	public int indexOf(int type) {
		int i = 0;
		for (LinkedSegment s : this) {
			if (s.getType() == type)
				return i;
			i++;
		}
		return -1;
	}

	public int lastIndexOf(int type) {
		int i = length() - 1;
		for (LinkedSegment s : this.reverse()) {
			if (s.getType() == type)
				return i;
			i--;
		}
		return -1;
	}

	public SegmentBuilder reverse() {// TODO: not tested
		if (!isEmpty()) {
			LinkedSegment next = null;
			for (LinkedSegment s : this)
				next = s.setChild(0, next);
//			if (head.hasSibling()) {
//				this.head = next.concat(this.head.setChild(0, null));
//				this.head = next.concat(this.head);
//			}
		}
		return this;
	}

	private LinkedSegment head;

	@Override
	public Iterator<LinkedSegment> iterator() {
		return new Iterator<>() {
			private LinkedSegment current;

			@Override
			public boolean hasNext() {
				return current != null ? current.hasSibling() : head != null || !(head instanceof Empty);
			}

			@Override
			public LinkedSegment next() {
				return (current = current != null ? current.getSibling() : head);
			}
		};
	}
}