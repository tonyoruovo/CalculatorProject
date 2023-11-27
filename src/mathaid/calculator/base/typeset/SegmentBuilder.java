/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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
 * Class used to build a complex {@link LinkedSegment} which may then be retrieved using {@link SegmentBuilder#toSegment}.
 * It also acts as a mutable semi-wrapper for {@code LinkedSegment} objects and
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
 *		List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
 *		formula.toSegment().toString(out, null, l);//prints: x= Rational[-b- Sqrt[b ^(2 )-4*ac ] ,2*a ]
 *		out.println();
 *		l.clear();
 *		l.add(-1);
 *		formula.toSegment().format(out, Formatter.empty(), l);//prints: x= \frac{\textrm{-}b\pm \sqrt{b ^{2 }-4ac } }{2a}
 *	</code>
 *</pre>
 * Below is more elegant example which showcases the capability of this class, it is functionally the same as the above code,
 * but it uses positional indexes to insert {@code LinkedSegment} which results in using a single {@code SegmentBuilder} instead
 * of the multiple that was used above:
 *<pre>
 *	<code>
 *		DigitPunc dp = new DigitPunc();
 *		// Notice that the order of the insert calls (for child segments) do not matter
 *		SegmentBuilder formula = new SegmentBuilder()
 *				.insert(Segments.freeVariable("x", "x"), 0)
 *				.insert(Segments.operator("=", "="), 1)
 *				.insert(Segments.fraction(null, null), 2)// null not ideal. Should use the Empty segment instead
 *				// numerator
 *				.insert(Digits.prefixMinus(), 2, 0, 0)
 *				.insert(Segments.freeVariable("b", "b"), 2, 0, 1)
 *				.insert(Segments.operator("\\pm", "-"), 2, 0, 2)
 *				// inside the sqrt symbol
 *				.insert(Segments.sqrt(null), 2, 0, 3)
 *				//inside b squared
 *				.insert(Segments.pow(null, null), 2, 0, 3, 0, 0)
 *				.insert(Segments.freeVariable("b", "b"), 2, 0, 3, 0, 0, 0, 0)
 *				.insert(Digits.integer('2', dp), 2, 0, 3, 0, 0, 1, 0)// end b squared
 *				.insert(Segments.operator("-", "-"), 2, 0, 3, 0, 1)
 *				.insert(Digits.integer('4', dp), 2, 0, 3, 0, 2)
 *				.insert(Segments.freeVariable("a", "a"), 2, 0, 3, 0, 3)
 *				.insert(Segments.freeVariable("c", "c"), 2, 0, 3, 0, 4)// end numerator
 *				// denominator
 *				.insert(Digits.integer('2', dp), 2, 1, 0)
 *				.insert(Segments.freeVariable("a", "a"), 2, 1, 1);// end denominator
 *	</code>
 *</pre>
 * Which when displayed in a TeX capable program will be rendered
 * as:
 * 
 *<div>
 *	<svg viewbox="0 0 200 30">
 *		<text x="1" y="25" fill="black" style="font-size:10px;font-family:serif;font-weight:400;font-style:oblique;">x</text>
 *		<text x="8" y="26" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">=</text>
 *		<line x1="18" y1="22.5" x2="84" y2="22.5" style="stroke:black;stroke-width:.5"/>
 *		<text x="20" y="18" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">&#x002D;</text>
 *		<text x="23" y="20" fill="black" style="font-size:10px;font-family:serif;font-weight:400;font-style:oblique;">b</text>
 *		<text x="30" y="20.5" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">&#x00B1;</text>
 *		<text x="38" y="20.5" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">&#x221A;</text>
 *		<text x="45" y="20" fill="black" style="font-size:10px;font-family:serif;font-weight:400;font-style:oblique;">b</text>
 *		<text x="50" y="16" fill="black" style="font-size:5px;font-family:serif;font-weight:400;">2</text>
 *		<text x="55" y="18" fill="black" style="font-size:5px;font-family:serif;font-weight:900;">&#x2014;</text>
 *		<text x="62" y="20" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">4</text>
 *		<text x="68" y="20" fill="black" style="font-size:10px;font-family:serif;font-weight:400;font-style:oblique;">ac</text>
 *		<line x1="43.1" y1="11.5" x2="81" y2="11.5" style="stroke:black;stroke-width:.5"/>
 *		<text x="40" y="31" fill="black" style="font-size:10px;font-family:serif;font-weight:400;">2</text>
 *		<text x="45" y="31" fill="black" style="font-size:10px;font-family:serif;font-weight:400;font-style:oblique;">a</text>
 *	</svg>
 *</div>
 * <h3 id="positional_indices">Positional Indices</h3>
 * <p>Most methods in this class require a {@code List} of {@code Integer} as the last argument. This is the positional index of the
 * segment to be accessed or edited. This enables the method to traverse the segment tree without running into unexpected {@code null}
 * segments. This in-turn allows deeply nested children to be easily reachable from the top-level. The element of positional index list
 * are ordered as follows:
 * <ol>
 * <li>The horizontal position of the top level {@code LinkedSegment}. This is the same node that can be accessed by
 * {@code LinkedSegment.segmentAt(index)}.</li>
 * <li>The index of the child node within the children of the node from (1) that can be accessed by
 * <code>LinkedSegment.getChildren()[index]</code>.</li>
 * <li>The horizontal position of the index within the tree accessed from (2). Note that this is a repetition of
 * (1).
 * </li>
 * </ol>
 * The above steps may be repeated as many times as possible to access the position of the desired node.
 * If only the top-level node is to be accessed, then (1) is enough but if the node to be accessed is nested, then
 * (2) and (3) is required.
 * @see LinkedSegment
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class SegmentBuilder implements Iterable<LinkedSegment> {

	/*
	 * Date: 13 Aug 2022-----------------------------------------------------------
	 * Time created: 16:16:18--------------------------------------------
	 */
	/**
	 * Creates a {@code SegmentBuilder} from the given {@code Segment}, which becomes
	 * the head segment of the tree after this constructor returns.
	 * @param segment the value to become the head of the {@code LinkedSegment} tree.
	 */
	public SegmentBuilder(Segment segment) {
		this(new LinkedSegmentAdapter(segment));
	}

	/*
	 * Date: 13 Aug 2022-----------------------------------------------------------
	 * Time created: 16:16:18--------------------------------------------
	 */
	/**
	 * Creates a {@code SegmentBuilder} from the given {@code LinkedSegment}, which becomes
	 * the head of the tree after this constructor returns.
	 * @param s the value to become the head of the {@code LinkedSegment} tree.
	 */
	public SegmentBuilder(LinkedSegment s) {
		this.head = s;
	}

	/*
	 * Date: 13 Aug 2022-----------------------------------------------------------
	 * Time created: 16:16:18--------------------------------------------
	 */
	/**
	 * Creates an empty {@code SegmentBuilder} i.e without a head.
	 * Technically speaking, makes a {@code Empty} object as the head.
	 */
	public SegmentBuilder() {
		this(new Empty());
	}

	/*
	 * Date: 13 Aug 2022-----------------------------------------------------------
	 * Time created: 16:16:18--------------------------------------------
	 */
	/**
	 * Builds the {@code Segment} tree and returns it as a {@code LinkedSegment}.
	 * @return the {@code LinkedSegment} that was being built.
	 */
	public LinkedSegment toSegment() {
		return head;
	}

	/*
	 * Date: 13 Aug 2022-----------------------------------------------------------
	 * Time created: 16:16:18--------------------------------------------
	 */
	/**
	 * Checks whether or not there is a {@code LinkedSegment} node present in this
	 * builder instance and returns {@code true} if there isn't.
	 * @return {@code true} if there is no {@code LinkedSegment} in this builder else
	 * returns {@code false}.
	 */
	public boolean isEmpty() {
		try {
			return head.length() == 0;
		} catch (NullPointerException e) {
			return false;
		}
	}

	/*
	 * Date: 13 Aug 2022-----------------------------------------------------------
	 * Time created: 16:16:18--------------------------------------------
	 */
	/**
	 * Gets the number of {@code LinkedSegment} nodes in this {@code SegmentBuilder}.
	 * @return the number of {@code LinkedSegment} nodes in this builder.
	 */
	public int length() {
		return head.length();
	}

	/*
	 * Date: 13 Aug 2022-----------------------------------------------------------
	 * Time created: 16:16:18--------------------------------------------
	 */
	/**
	 * Traverses the given builder using the given positions to locate the {@code LinkedSegment} and return it.
	 * @param headToPermute the builder to be traversed
	 * @param indexes the positional indices representing the location (within the given {@code SegmentBuilder})
	 * of the value to be returned.
	 * @return the node ({@code LinkedSegment}) at the given location within the given builder.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices returns
	 * a {@code null} value.
	 */
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

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 18:17:44 ---------------------------------------------------
	 */
	/**
	 * Traverses this builder and returns the segment at the location given by the positional indices argument.
	 * @param index the positional indices. At least one non-negative value is required.
	 * @return the node ({@code LinkedSegment}) at the given location within this builder.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
	public LinkedSegment segmentAt(int... index) throws IndexOutOfBoundsException {
		ArrayList<Integer> l = new ArrayList<>();
		for (int i : index)
			l.add(i);
		return segmentAt(this, l);
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 18:21:53 ---------------------------------------------------
	 */
	/**
	 * Traverses this builder and returns the segment at the location given by the list specifying positional indices.
	 * @param pos a {@code List} containing the positional indices. At least one non-negative value is required to be in the list.
	 * @return the node ({@code LinkedSegment}) at the given location within this builder.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
	public LinkedSegment segmentAt(List<Integer> pos) throws IndexOutOfBoundsException {
		return segmentAt(this, pos);
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:01:29 ---------------------------------------------------
	 */
	/**
	 * Does horizontal-only traversal and returns the {@code LinkedSegment} at the given index. An horizontal-only
	 * traversal entails that the traversal begins at the head of the tree and walks the tree by only calling
	 * {@link LinkedSegment#getSibling()}, incrementing the index (which began at 0) until the specified index
	 * is found (i.e the incremented index is equal to the argument) and then returns the segment at that index.
	 * Note that the segment returned will have no sibling.
	 * @param index the index of the {@code LinkedSegment} to be retrieved.
	 * @return the {@code LinkedSegment} at the index given by the argument.
	 * @throws IndexOutOfBoundsException if no {@code LinkedSegment} exists at the specified index.
	 */
	public LinkedSegment segmentAt(int index) throws IndexOutOfBoundsException {
		return head.segmentAt(index);
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:09:36 ---------------------------------------------------
	 */
	/**
	 * Traverses the given builder using the given positions to locate the {@code LinkedSegment} and set it's
	 * error to the boolean argument before returning that same segment.
	 * @param headToPermute the builder to be traversed
	 * @param err the error to be set.
	 * @param indexes the positional indices representing the location (within the given {@code SegmentBuilder})
	 * of the value to be returned.
	 * @return the node ({@code LinkedSegment}) at the head within the given builder after setting the
	 * error property to the boolean argument.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices returns
	 * a {@code null} value.
	 */
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

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:14:38 ---------------------------------------------------
	 */
	/**
	 * Traverses this builder and sets the error property of the segment at the location given by the positional indices argument.
	 * @param err the error to be set.
	 * @param index the positional indices. At least one non-negative value is required.
	 * @return this same builder object after the operation has been completed.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
	public SegmentBuilder setError(boolean err, int... index) throws IndexOutOfBoundsException {
		ArrayList<Integer> l = new ArrayList<>();
		for (int i : index)
			l.add(i);
		head = setError(head, err, l);
		return this;
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:19:21 ---------------------------------------------------
	 */
	/**
	 * Traverses this builder and sets the error property of the segment at the location given by the list specifying positional indices.
	 * @param err the error to be set.
	 * @param pos a {@code List} containing the positional indices. At least one non-negative value is required to be in the list.
	 * @return this same builder object after the operation has been completed.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
	public SegmentBuilder setError(boolean err, List<Integer> pos) throws IndexOutOfBoundsException {
		head = setError(head, err, pos);
		return this;
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:23:09 ---------------------------------------------------
	 */
	/**
	 * Traverses the given builder using the given positions to locate the {@code LinkedSegment} and set the
	 * focus to the boolean argument before returning that same segment.
	 * @param headToPermute the builder to be traversed
	 * @param f the focus to be set.
	 * @param indexes the positional indices representing the location (within the given {@code SegmentBuilder})
	 * of the value to be returned.
	 * @return the node ({@code LinkedSegment}) at the head within the given builder after setting the
	 * focus property to the boolean argument.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices returns
	 * a {@code null} value.
	 */
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

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:25:01 ---------------------------------------------------
	 */
	/**
	 * Traverses this builder and sets the focus property of the segment at the location given by the positional indices argument.
	 * @param focus the focus to be set.
	 * @param index the positional indices. At least one non-negative value is required.
	 * @return this same builder object after the operation has been completed.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
	public SegmentBuilder setFocus(boolean focus, int... index) throws IndexOutOfBoundsException {
		ArrayList<Integer> l = new ArrayList<>();
		for (int i : index)
			l.add(i);
		head = setFocus(head, focus, l);
		return this;
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:25:37 ---------------------------------------------------
	 */
	/**
	 * Traverses this builder and sets the focus property of the segment at the location given by the list specifying positional indices.
	 * @param focus the focus to be set.
	 * @param pos a {@code List} containing the positional indices. At least one non-negative value is required to be in the list.
	 * @return this same builder object after the operation has been completed.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
	public SegmentBuilder setFocus(boolean focus, List<Integer> pos) throws IndexOutOfBoundsException {
		head = setFocus(head, focus, pos);
		return this;
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:26:30 ---------------------------------------------------
	 */
	/**
	 * Traverses the {@code LinkedSegment} given by the first argument and removes the {@code LinkedSegment} located
	 * at the specified positional indexes, then returns the head of the tree. Note that if the segment to be removed
	 * has any children, they too are removed with the segment, but it's sibling (if any) will not be removed.
	 * @param headToPermute the head of the tree to be traversed
	 * @param indexes the positional indices representing the location (within the given {@code LinkedSegment} tree)
	 * of the value to be returned.
	 * @return the head of the first argument after successfully removing the node at the specified location.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
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

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:34:14 ---------------------------------------------------
	 */
	/**
	 * Horizontally traverses this {@code SegmentBuilder} and locates the node at the given index
	 * and then removes it but retains it's sibling(s), then returns {@code this} builder. An horizontal-only
	 * traversal entails that the traversal begins at the head of the tree and walks the tree by only calling
	 * {@link LinkedSegment#getSibling()}, incrementing the index (which began at 0) until the specified index
	 * is found (i.e the incremented index is equal to the argument) and then returns the segment at that index.
	 * Note that if the segment to be removed has any children, they too are removed with the segment, but
	 * it's sibling (if any) will not be removed.
	 * @param index the index of the {@code LinkedSegment} to be removed.
	 * @return {@code this} after successfully removing the node at the given index.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
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

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:41:00 ---------------------------------------------------
	 */
	/**
	 * Traverses this builder and removes the segment at the location given by the positional indices argument.
	 * @param index the positional indices i.e the location of the segment to be removed. At least one non-negative
	 * value is required.
	 * @return this same builder object after the deletion has been completed.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
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
	 * Traverses this builder and removes the segment at the location given by the list specifying positional indices.
	 * @param pos a {@code List} containing the positional indices of the segment to be removed. At least one
	 * non-negative value is required to be in the list.
	 * @return this same builder object after the operation has been completed.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
	public SegmentBuilder delete(List<Integer> pos) throws IndexOutOfBoundsException {
		head = delete(head, pos);
		return this;
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:44:13 ---------------------------------------------------
	 */
	/**
	 * Removes all segments in this builder, returning an empty builder afterwards.
	 * @return an empty builder
	 */
	public SegmentBuilder deleteAll() {
		head = new Empty();
		return this;
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:46:21 ---------------------------------------------------
	 */
	/**
	 * Traverses the first argument to find the location specified by the second argument then inserts the value
	 * specified by the third argument into the tree that was traversed and returns the head of it.
	 * @param headToPermute the value to be traversed, whose head will be returned.
	 * @param indexes the positional indices representing the location (within the given {@code LinkedSegment} tree)
	 * of the value to be returned. This includes the location at which the insertion is to take place.
	 * @param newSegment the value to be inserted
	 * @return the head of the tree (the first argument) on which the insertion was done.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
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

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:50:58 ---------------------------------------------------
	 */
	/**
	 * Horizontally traverses this {@code SegmentBuilder} and locates the node at the given index
	 * and then moves it and all it's sibling(s) to the right, inserts the {@code LinkedSegment} value at
	 * that position, makes the shifted sibling the immediate sibling of the new inserted node and then
	 * returns {@code this} builder. An horizontal-only
	 * traversal entails that the traversal begins at the head of the tree and walks the tree by only calling
	 * {@link LinkedSegment#getSibling()}, incrementing the index (which began at 0) until the specified index
	 * is found (i.e the incremented index is equal to the argument) and then returns the segment at that index.
	 * Note that if the segment to be inserted has any children, their position within their parent does not
	 * change.
	 * @param index the index of the {@code LinkedSegment} to be inserted.
	 * @return {@code this} after successfully inserting the node at the given index.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
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

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:57:01 ---------------------------------------------------
	 */
	/**
	 * Traverses this builder and inserts the segment argument at the location given by the positional indices argument.
	 * @param seg the value to be inserted
	 * @param index the positional indices i.e the location where the segment is to be inserted. At least one non-negative
	 * value is required.
	 * @return this same builder object after the insertion has been completed.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
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

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:59:30 ---------------------------------------------------
	 */
	/**
	 * Traverses this builder and inserts the segment argument at the location given by the list specifying positional indices.
	 * @param pos a {@code List} containing the positional indices of location where the new segment is to be inserted.
	 * At least one non-negative value is required to be in the list.
	 * @param newSegment the value to be inserted.
	 * @return this same builder object after the operation has been completed.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
	public SegmentBuilder insert(List<Integer> pos, LinkedSegment newSegment) throws IndexOutOfBoundsException {
		head = insert(head, pos, newSegment);
		return this;
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:02:35 ---------------------------------------------------
	 */
	/**
	 * Traverses this builder and inserts contents of the {@code SegmentBuilder} argument at the location (given by the
	 * list specifying positional indices).
	 * @param pos a {@code List} containing the positional indices of location where the new value is to be inserted.
	 * At least one non-negative value is required to be in the list.
	 * @param s the value containg the value(s) to be inserted
	 * @return this same builder object after the operation has been completed.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
	public SegmentBuilder insert(List<Integer> pos, SegmentBuilder s) throws IndexOutOfBoundsException {
		return insert(pos, s.toSegment());
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:03:58 ---------------------------------------------------
	 */
	/**
	 * Traverses this builder and inserts the contents of the {@code SegmentBuilder} argument at the location
	 * given by the positional indices argument.
	 * @param s the value to be inserted
	 * @param index the positional indices i.e the location where the value is to be inserted. At least one non-negative
	 * value is required.
	 * @return this same builder object after the insertion has been completed.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
	public SegmentBuilder insert(SegmentBuilder s, int... index) throws IndexOutOfBoundsException {
		return insert(s.toSegment(), index);
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:02:35 ---------------------------------------------------
	 */
	/**
	 * Traverses this builder and inserts the {@code Segment} argument at the location given by the list specifying positional indices.
	 * @param pos a {@code List} containing the positional indices of location where the new value is to be inserted.
	 * At least one non-negative value is required to be in the list.
	 * @param s the value to be inserted
	 * @return this same builder object after the operation has been completed.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
	public SegmentBuilder insert(List<Integer> pos, Segment s) throws IndexOutOfBoundsException {
		return insert(pos, new LinkedSegmentAdapter(s));
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:03:58 ---------------------------------------------------
	 */
	/**
	 * Traverses this builder and inserts the {@code Segment} argument at the location given by the positional indices argument.
	 * @param s the value to be inserted
	 * @param index the positional indices i.e the location where the value is to be inserted. At least one non-negative
	 * value is required.
	 * @return this same builder object after the insertion has been completed.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
	public SegmentBuilder insert(Segment s, int... index) throws IndexOutOfBoundsException {
		return insert(new LinkedSegmentAdapter(s), index);
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:06:59 ---------------------------------------------------
	 */
	/**
	 * Traverses the first argument to find the location specified by the second argument then replaces at that location with the value
	 * specified by the third argument and returns the head of it.
	 * @param headToPermute the value to be traversed, whose head will be returned.
	 * @param indexes the positional indices representing the location (within the given {@code LinkedSegment} tree)
	 * of the value to be returned. This includes the location at which the insertion is to take place.
	 * @param newSegment the replacement value.
	 * @return the head of the tree (the first argument) on which the replacement was done.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
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

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:17:54 ---------------------------------------------------
	 */
	/**
	 * Horizontally traverses this {@code SegmentBuilder} and locates the node at the given index
	 * and then replaces it with the given value but retains it's sibling, then returns {@code this} builder. An horizontal-only
	 * traversal entails that the traversal begins at the head of the tree and walks the tree by only calling
	 * {@link LinkedSegment#getSibling()}, incrementing the index (which began at 0) until the specified index
	 * is found (i.e the incremented index is equal to the argument) and then returns the segment at that index.
	 * Note that if the segment to be replaced has any children, they too are replaced with the segment, but
	 * it's sibling (if any) will not be replaced.
	 * @param index the index of the {@code LinkedSegment} to be replaced.
	 * @param replacement the replacement value
	 * @return {@code this} after successfully replacing the node at the given index.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
	private SegmentBuilder replace(int index, LinkedSegment replacement) throws IndexOutOfBoundsException {
		return delete(index).insert(index, Objects.requireNonNull(replacement, "Cannot replace with a null value"));
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:20:52 ---------------------------------------------------
	 */
	/**
	 * Traverses this builder and replaces the segment at the location given by the positional indices argument
	 * with the given value.
	 * @param seg the replacement value
	 * @param index the positional indices i.e the location of the segment to be replaced. At least one non-negative
	 * value is required.
	 * @return this same builder object after the replacement has been completed.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
	public SegmentBuilder replace(LinkedSegment seg, int... index) throws IndexOutOfBoundsException {
		ArrayList<Integer> l = new ArrayList<>();
		for (int i : index)
			l.add(i);
		head = replace(head, l, Objects.requireNonNull(seg, "Cannot replace with a null value"));
		return this;
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:23:56 ---------------------------------------------------
	 */
	/**
	 * Traverses this builder and replaces the segment at the location given by the list specifying positional indices with the given value.
	 * @param pos a {@code List} containing the positional indices of the segment to be replaced. At least one
	 * non-negative value is required to be in the list.
	 * @param newSegment the replacement value.
	 * @return this same builder object after the operation has been completed.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
	public SegmentBuilder replace(List<Integer> pos, LinkedSegment newSegment) throws IndexOutOfBoundsException {
		head = replace(head, pos, newSegment);
		return this;
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:25:41 ---------------------------------------------------
	 */
	/**
	 * Traverses this builder and replaces the segment at the location (given by the list specifying positional indices)
	 * with the contents of the given {@code SegmentBuilder}.
	 * @param pos a {@code List} containing the positional indices of the segment to be replaced. At least one
	 * non-negative value is required to be in the list.
	 * @param s the value containing replacement value(s).
	 * @return this same builder object after the operation has been completed.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
	public SegmentBuilder replace(List<Integer> pos, SegmentBuilder s) throws IndexOutOfBoundsException {
		return replace(pos, s.toSegment());
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:25:41 ---------------------------------------------------
	 */
	/**
	 * Traverses this builder and replaces the segment at the location (given by the positional indices)
	 * with the contents of the given {@code SegmentBuilder}.
	 * @param index the positional indices of the segment to be replaced. At least one
	 * non-negative value is required.
	 * @param s the value containing replacement value(s).
	 * @return this same builder object after the operation has been completed.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
	public SegmentBuilder replace(SegmentBuilder s, int... index) throws IndexOutOfBoundsException {
		return replace(s.toSegment(), index);
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:25:41 ---------------------------------------------------
	 */
	/**
	 * Traverses this builder and replaces the segment at the location (given by the list specifying positional indices)
	 * with that of the given {@code Segment}.
	 * @param pos a {@code List} containing the positional indices of the segment to be replaced. At least one
	 * non-negative value is required to be in the list.
	 * @param s the replacement value.
	 * @return this same builder object after the operation has been completed.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
	public SegmentBuilder replace(List<Integer> pos, Segment s) throws IndexOutOfBoundsException {
		return replace(pos, new LinkedSegmentAdapter(s));
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:25:41 ---------------------------------------------------
	 */
	/**
	 * Traverses this builder and replaces the segment at the location (given by the positional indices)
	 * with that of the given {@code Segment}.
	 * @param pos the positional indices of the segment to be replaced. At least one
	 * non-negative value is required.
	 * @param s the replacement value.
	 * @return this same builder object after the operation has been completed.
	 * @throws IndexOutOfBoundsException if the location specified by the positional indices does not exist
	 * in this builder.
	 */
	public SegmentBuilder replace(Segment s, int... index) throws IndexOutOfBoundsException {
		return replace(new LinkedSegmentAdapter(s), index);
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:39:09 ---------------------------------------------------
	 */
	/**
	 * Appends the argument to the tail segment of this builder
	 * @param s the value to be appended.
	 * @return this same builder object after the appendage has been completed.
	 */
	public SegmentBuilder append(LinkedSegment s) {
		head = head.concat(Objects.requireNonNull(s, "Cannot append to null"));
		return this;
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:39:09 ---------------------------------------------------
	 */
	/**
	 * Appends the contents of the argument to the tail segment of this builder
	 * @param s the value whose contents is to be appended. Ideally, this should not be an empty builder.
	 * @return this same builder object after the appendage has been completed.
	 */
	public SegmentBuilder append(SegmentBuilder s) {
		return append(s.toSegment());
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:39:09 ---------------------------------------------------
	 */
	/**
	 * Appends the argument to the tail segment of this builder
	 * @param s the value to be appended.
	 * @return this same builder object after the appendage has been completed.
	 */
	public SegmentBuilder append(Segment s) {
		return append(new LinkedSegmentAdapter(s));
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:39:09 ---------------------------------------------------
	 */
	/**
	 * Prepends the argument to the head segment of this builder
	 * @param s the value to be prepended.
	 * @return this same builder object after the operation has been completed.
	 */
	public SegmentBuilder prepend(LinkedSegment s) {
		head = Objects.requireNonNull(s, "Cannot prepend to null").concat(head);
		return this;
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:39:09 ---------------------------------------------------
	 */
	/**
	 * Prepends the contents of the argument to the head segment of this builder
	 * @param s the value whose contents is to be prepended.
	 * @return this same builder object after the operation has been completed.
	 */
	public SegmentBuilder prepend(SegmentBuilder s) {
		return prepend(s.toSegment());
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:39:09 ---------------------------------------------------
	 */
	/**
	 * Prepends the argument to the head segment of this builder
	 * @param s the value to be prepended.
	 * @return this same builder object after the operation has been completed.
	 */
	public SegmentBuilder prepend(Segment s) {
		return prepend(new LinkedSegmentAdapter(s));
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:43:56 ---------------------------------------------------
	 */
	/**
	 * Returns the horizontal position of the first occurrence of the segment with the type argument.
	 * (specified by {@link Segment#getType()}).
	 * @param type the type of the segment be searched for.
	 * @return the horizontal position of the first segment encountered with the given type.
	 * Returns {@code -1} if no segment with the specified type exists within this tree.
	 */
	public int indexOf(int type) {
		int i = 0;
		for (LinkedSegment s : this) {
			if (s.getType() == type)
				return i;
			i++;
		}
		return -1;
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:43:56 ---------------------------------------------------
	 */
	/**
	 * Returns the horizontal position of the last occurrence of the segment with the type argument.
	 * (specified by {@link Segment#getType()}).
	 * @param type the type of the segment be searched for.
	 * @return the horizontal position of the last segment encountered with the given type.
	 * Returns {@code -1} if no segment with the specified type exists within this tree.
	 */
	public int lastIndexOf(int type) {
		int i = length() - 1;
		for (LinkedSegment s : this.reverse()) {
			if (s.getType() == type)
				return i;
			i--;
		}
		return -1;
	}

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:48:09 ---------------------------------------------------
	 */
	/**
	 * Reverses the order of the segment tree and returns a new {@code SegmentBuilder}.
	 * @return this a new {@code SegmentBuilder} object after the reversal has completed, but if there
	 * was no node in this builder before this method was called, then this same builder will be
	 * returned.
	 */
	public SegmentBuilder reverse() {
		if (!isEmpty()) {
			int l = length();
			int i = l - 1;
			// Brute force algorithm
			LinkedSegment s = segmentAt(i);
			while (--i >= 0) {
				s = s.concat(segmentAt(i));
			}
			return new SegmentBuilder(s);
		}
		return this;
	}

	/**
	 * Field for the head of segment being built by this builder.
	 * This value can be retrieved by {@code SegmentBuilder.segmentAt(0)}.
	 * When this builder is empty, this value will either be {@code null}
	 * or an instance of the {@link Empty} class.
	 */
	private LinkedSegment head;

	/*
	 * Date: 16 Nov 2023 -----------------------------------------------------------
	 * Time created: 20:49:09 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @return
	 */
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