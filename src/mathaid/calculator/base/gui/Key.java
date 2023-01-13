/**
 * 
 */
package mathaid.calculator.base.gui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/*
 * Date: 16 Jul 2022----------------------------------------------------------- 
 * Time created: 12:54:14---------------------------------------------------  
 * Package: mathaid.calculator.base.model------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Key.java------------------------------------------------------ 
 * Class name: Key------------------------------------------------ 
 */
/**
 * A {@code Key} is a node within a singly linked-list that represents an input
 * interface such as a button on the calculator interface. It is an abstract
 * (general) implementation of a button within a group of buttons whereby the
 * keypress of one button directly affects another button within the group and
 * may provide possibilities for implementing intuitive interactions between the
 * user and the software. For example, pressing one key may change the text on
 * another and certain actions may become available depending on the last key
 * pressed. The data that provides an interface for such a feature to be
 * possible is called (within the context of this API) an input modifier (or
 * modifier for short). Actions mapped internally to modifiers are of type
 * {@code KeyForm}.
 * 
 * <h2 id="modifier-paragraph" style="text-align:center">MODIFIERS</h2>
 * <p>
 * <img src="./Modifier_Blank.png" alt="modifier bit layout" style=
 * "display:block;margin-right:auto;margin-left:auto;width:75%;">
 * <p>
 * A modifier is a key that maps what type of input-action to expect from a
 * button press. This key is stored as a 32-bit integer type (int) where each
 * bit corresponds to a modifier/boolean value (a.k.a modifier bit). This is
 * further divided into 3 section using the ratio 12:8:12. the first section
 * (primary modifier section) comprises of 3 groups (where a group has 4 bits
 * each), the second section is a group/section hybrid and comprises of 8 bits
 * and the third section comprises of 3 groups (again, each group is 4 bits
 * long). Based on the modifier argument of {@link #getKeyForm(int)}, a valid
 * {@code KeyForm} may be returned or a {@link KeyForm#EMPTY_KEYFORM} to signify
 * no modifier is available but no action is mapped to it. When {@code null} is
 * returned, then that modifier simply does not exist. For example, in the
 * calculator we may have a reciprocal (RCPRCL), hyp (HYP) and shift (SHIFT)
 * modifier represented as the following:
 * <table style="display:block;margin-right:auto;margin-left:auto;width:50%;">
 * <tr>
 * <th>Modifier name</th>
 * <th>Modifier combination</th>
 * <th>Sample key function</th>
 * </tr>
 * <tr>
 * <td style="text-align:center;">Unmodified</td>
 * <td style="text-align:center;">{@code 000}</td>
 * <td style="text-align:center;">sin, cos, tan</td>
 * </tr>
 * <tr>
 * <td style="text-align:center;">SHIFT</td>
 * <td style="text-align:center;">{@code 001}</td>
 * <td style="text-align:center;">asin, acos, atan</td>
 * </tr>
 * <tr>
 * <td style="text-align:center;">HYP</td>
 * <td style="text-align:center;">{@code 010}</td>
 * <td style="text-align:center;">sinh, cosh, tanh</td>
 * </tr>
 * <tr>
 * <td style="text-align:center;">SHIFT, HYP</td>
 * <td style="text-align:center;">{@code 011}</td>
 * <td style="text-align:center;">asinh, acosh, atanh</td>
 * </tr>
 * <tr>
 * <td style="text-align:center;">RCPRCL</td>
 * <td style="text-align:center;">{@code 100}</td>
 * <td style="text-align:center;">cosec, sec, cot</td>
 * </tr>
 * <tr>
 * <td style="text-align:center;">SHIFT, RCPRCL</td>
 * <td style="text-align:center;">{@code 101}</td>
 * <td style="text-align:center;">acosec, asec, acot</td>
 * </tr>
 * <tr>
 * <td style="text-align:center;">HYP, RCPRCL</td>
 * <td style="text-align:center;">{@code 110}</td>
 * <td style="text-align:center;">cosech, sech, coth</td>
 * </tr>
 * <tr>
 * <td style="text-align:center;">SHIFT, HYP, RCPRCL</td>
 * <td style="text-align:center;">{@code 111}</td>
 * <td style="text-align:center;">acosech, asech, acoth</td>
 * </tr>
 * </table>
 * <p>
 * The input is not the only thing that may change on the calculator's button,
 * the text may change as well. This happens because there is a specific unique
 * mapping using the modifier as key and the function (together with it's text)
 * as it's value.
 * <h3 id="modifier-section-paragraph" style="text-align:center">Modifier
 * section:</h3>
 * <p>
 * A modifier section is a modifier group of bits with it's own rules. Each
 * section has a rule of combination. Certain sections allow multiple bits
 * within it's group to be "on", while others don't. Some allow for combination
 * with the global bit modifier while others do not. A section may not allow for
 * a situation whereby bits from other section or groups may be on while they
 * are on because this object does not have any combinatorial mapping as such.
 * The exception to this is the global bit modifier, as when it is enabled, some
 * sections may allow for it to be on.
 * <h4 id="primary-modifier-section-paragraph" style="text-align:center">Primary
 * modifier section:</h4>
 * <p>
 * This has 3 modifier groups and each group is made up of 4 bits. The first
 * group (counting from the left-most/most significant bit) is the most
 * significant group and it contains the global modifier bit or global bit. When
 * extracted, each group of bits should be interpreted as it is in binary logic.
 * This section's groups may use combinations on it's bits whereby each bit is
 * combined for a different interpretation.We have already given an example of
 * this in the table above. A group within this section may not be more than
 * 15<sub>10</sub> (1111<sub>2</sub>). Note that only bits within a group are
 * subject to combinations. Inter-group combinations are undefined. The groups
 * within this section do not adhere to global bit enablement i.e whether the
 * global bit is on or off does not make any difference for these groups.
 * <h5 id="primary-modifier-section-paragraph" style="text-align:center">Global
 * bit:</h5>
 * <p>
 * Also known as global bit modifier is the least significant bit in the first
 * group of the primary modifier's section. The is most likely always a global
 * bit in a set of {@code Key} objects if the global bit is enabled. This is
 * enabled via {@link #Key(int[], int, int[], boolean, int) this constructor}.
 * <h4 id="secondary-modifier-section-paragraph" style=
 * "text-align:center">Secondary modifier section:</h4> This is also a group in
 * that it does not function like a traditional section with multiple groups.
 * Instead, each group is an independent boolean (on/off) value. it supports up
 * to 8 values (8 bits). In addition to this, when the global modifier bit is
 * enabled, addition mappings (in which the global bit is always on) are
 * provided. For example: If we have 3 secondary modifiers namely: isExpr (for
 * inputting free variables such as x, y, z), isComp (for inputting complex
 * functions and values) and NUM_LOCK (for inputting recurring digits), we would
 * then have this arrangement:
 * <table style="display:block;margin-right:auto;margin-left:auto;width:50%;">
 * <tr>
 * <th>Modifier name</th>
 * <th>Modifier combination</th>
 * <th>Sample key function</th>
 * </tr>
 * <tr>
 * <td style="text-align:center;">isExpr</td>
 * <td style="text-align:center;">{@code 00000001}</td>
 * <td style="text-align:center;">for inputting free variables such as x, y,
 * z</td>
 * </tr>
 * <tr>
 * <td style="text-align:center;">isComp</td>
 * <td style="text-align:center;">{@code 00000010}</td>
 * <td style="text-align:center;">for inputting complex functions and
 * values</td>
 * </tr>
 * <tr>
 * <td style="text-align:center;">NUM_LOCK</td>
 * <td style="text-align:center;">{@code 00000100}</td>
 * <td style="text-align:center;">for inputting recurring digits</td>
 * </tr>
 * </table>
 * When the global modifier is enabled, extra mappings are added to the above
 * such as:
 * <table style="display:block;margin-right:auto;margin-left:auto;width:50%;">
 * <tr>
 * <th>Modifier name</th>
 * <th>Modifier combination</th>
 * <th>Sample key function</th>
 * </tr>
 * <tr>
 * <td style="text-align:center;">isExpr</td>
 * <td style="text-align:center;">{@code 00010000000000000001}</td>
 * <td style="text-align:center;">for inputting free variables such as x, y,
 * z</td>
 * </tr>
 * <tr>
 * <td style="text-align:center;">isComp</td>
 * <td style="text-align:center;">{@code 00010000000000000010}</td>
 * <td style="text-align:center;">for inputting complex functions and
 * values</td>
 * </tr>
 * <tr>
 * <td style="text-align:center;">NUM_LOCK</td>
 * <td style="text-align:center;">{@code 00010000000000000100}</td>
 * <td style="text-align:center;">for inputting recurring digits</td>
 * </tr>
 * </table>
 * <h4 id="Tertiary-modifier-section-paragraph" style=
 * "text-align:center">Tertiary modifier section:</h4>
 * <p>
 * This is a modifier section where each group represents a range. They comprise
 * of 3 groups and each group is at most E<sub>16</sub> and at least 0 modifier
 * bit. Each group's interpretation depends on that of the other groups. This
 * group is permanently considered (along with the global bit modifier). This
 * modifier section takes the global modifier bit into consideration. For
 * example: In my programmer calculator, I have 3 tertiary modifiers namely:
 * binary rep (has 7 options), radix rep (has 4 options) and input type (has 4
 * options). Each group's current value is a number within the range
 * [0<sub>10</sub> &#x2264; x &#x2264; 14<sub>10</sub>] where x is the group's
 * current value. So if an action is chosen from a {@code Key}, then the
 * following are taken into account: the current binary rep (a number between 0
 * - 6), the current radix (a number between 0 - 3) and the current input type
 * (a number between 0 - 3).
 * <h3 id="modifier-group-paragraph" style="text-align:center">Modifier
 * group:</h3> As stated earlier, a modifier group is a collection of bits
 * within a modifier section that are logically grouped. The left-most group is
 * the most significant group. No group may explicitly include 0000 (except
 * tertiary sections) because it is mapped separately and doing so nullifies the
 * uniqueness of each group therefore such groups are ignored.
 * <h3 id="modifier-bit-paragraph" style="text-align:center">Modifier bit:</h3>
 * <p>
 * A modifier bit is a single bit in the modifier when it is in integer form.
 * The most significant bits are the left-most bits.
 * <p>
 * It is important to remember that this is a Node in a Linked list. And as
 * such, there are 3 constructors which are intended for different purposes.
 * {@link #Key(int[], int, int[], boolean, int) The first} is intended to be the
 * head of the linked list, {@link #Key(Map, int, Key) the next one} is intended
 * for maintaining the immutability of the nodes so that the fields are
 * maintained and {@link #Key(Key, Key) the last one} is responsible for
 * initialising a sibling node.
 * <p>
 * Make sure that when a {@code Key} node is queried for a {@code KeyForm}
 * object, only the relevant bits (for primary modifier section, these are bits
 * within a group, for secondary only a single bit and perhaps the global
 * modifier bit and for tertiary only the group pertaining to the relevent
 * range) should be on. All other bits not relevant to the proximity of the
 * intended bits should be off otherwise the behaviour of the node may be
 * undefined.
 * <p>
 * Key codes are numeric values that increase in value as more nodes
 * ({@code Key} objects) are created via {@link #appendSibling()}. There is one
 * key code for every {@code Key} node, making each key code unique.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Key {

	/**
	 * The maximum number of bits allowed for a primary and secondary group.
	 */
	public static final int MAX_BITS_PER_MODIFIER = 0x4;
	/**
	 * A bit-mask that can be used to reveal if the global modifier is on (using
	 * <code>modifier & GLOBAL_MODIFIER_MASK</code>) or to set the global modifier
	 * to on alongside a secondary and tertiary bit (using
	 * <code>modifier | GLOBAL_MODIFIER_MASK</code>).
	 */
	public static final int GLOBAL_MODIFIER_MASK = 0x10_00_00_00;
	/**
	 * The maximum number of bits that the primary modifier section can contain.
	 */
	public static final int MAX_PRIMARY_MODIFIER_BITS = 0xC;
	/**
	 * The maximum number of bits that the secondary modifier section can contain.
	 */
	public static final int MAX_SECONDARY_MODIFIER_BITS = 0x8;
	/**
	 * The maximum number of bits that the tertiary modifier section can contain.
	 */
	public static final int MAX_TERTIARY_MODIFIER_BITS = MAX_PRIMARY_MODIFIER_BITS;

	/*
	 * Date: 12 Aug 2022-----------------------------------------------------------
	 * Time created: 14:27:58--------------------------------------------
	 */
	/**
	 * Performs a combinatorial function on the argument array and returns a
	 * {@code Set} of integers where each is a combination of the tertiary section
	 * modifier's group as described in the documentation of this class. Note that
	 * the each group does not combine itself but other groups.
	 * 
	 * @param tm an int array containing the tertiary section whose length is
	 *           exactly 3, the group values do not exceed 15<sub>10</sub> and do
	 *           not fall below 0.
	 * @return a set containing all the possible combination of each specified group
	 *         given their max values presented.
	 */
	private static Set<Integer> combineTertiaryModifier(int[] tm) {
		Set<Integer> si = new HashSet<>();
		List<Set<Integer>> s = List.of(new TreeSet<Integer>(), new TreeSet<Integer>(), new TreeSet<Integer>());
		if (tm.length != 3)
			throw new IllegalArgumentException("tertiary modifier length != 3");
		for (int index = 0; index < tm.length; index++) {
			if (tm[index] == 0)
				continue;
			if (Integer.bitCount(tm[index]) > MAX_BITS_PER_MODIFIER)
				throw new IllegalArgumentException(
						"A tertiary modifier cannot be bigger than " + ((int) Math.pow(2, MAX_BITS_PER_MODIFIER) - 1));
			int val;
			for (int i = 0; i < tm[index]; i++) {
				int shift = MAX_TERTIARY_MODIFIER_BITS - (MAX_BITS_PER_MODIFIER * (index + 1));
				val = i << shift;
				s.get(index).add(val);
			}
		}

		// 0.
		si.addAll(s.get(0));
		for (int i : s.get(0)) {
			for (int integer : s.get(1))
				si.add(i | integer);
			for (int integer : s.get(2))
				si.add(i | integer);
			for (int integer0 : s.get(1))
				for (int integer1 : s.get(2))
					si.add(i | integer0 | integer1);
		}

		// 1.
		si.addAll(s.get(1));
		for (int i : s.get(1)) {
			for (int integer : s.get(0))
				si.add(i | integer);
			for (int integer : s.get(2))
				si.add(i | integer);
			for (int integer0 : s.get(0))
				for (int integer1 : s.get(2))
					si.add(i | integer0 | integer1);
		}

		// 2.
		si.addAll(s.get(2));
		for (int i : s.get(2)) {
			for (int integer : s.get(0))
				si.add(i | integer);
			for (int integer : s.get(1))
				si.add(i | integer);
			for (int integer0 : s.get(0))
				for (int integer1 : s.get(1))
					si.add(i | integer0 | integer1);
		}

		return si;
	}

	/*
	 * Date: 12 Aug 2022-----------------------------------------------------------
	 * Time created: 14:36:14---------------------------------------------------
	 */
	/**
	 * Constructs a {@code Key} object that is intended to be the head of the linked
	 * node. A performance intensive operation is done here to compute the keys for
	 * the mapping, so This is best suited for an initialiser than anything else.
	 * 
	 * <p>
	 * Each of the array arguments have a length of 3. the first element of the
	 * array is the most significant group in it's implied section, the next is the
	 * next significant group and the last element of the array is the least
	 * significant group.
	 * 
	 * @param primaryModifiers   an array representing the primary section modifier.
	 *                           The first element is the most significant group and
	 *                           contains the global bit modifier. Each index
	 *                           represents the value of it's group in that position
	 *                           within the section. It must be between 0 and 15.
	 *                           The next element also has the same range limit as
	 *                           the former but does not contain the global bit
	 *                           modifier and occupies the next 4 significant bits
	 *                           the last element is the same as the previous but
	 *                           occupies the 4 least significant bits of the
	 *                           primary section modifier. If a group is not needed
	 *                           just leave it's value at 0.
	 *                           <p>
	 *                           Each element is an int that contains one bits
	 *                           proportional to the number of modifier bits
	 *                           expected and in the position it is expected to be.
	 *                           For example, if the modifiers reciprocal, hyp and
	 *                           shift is expected as a group, then 7<sub>10</sub>
	 *                           would be the value for that group. This is because
	 *                           7<sub>10</sub> translates to 0111<sub>2</sub> which
	 *                           turns on it's last 3 bits to show that they are the
	 *                           actual modifier bits to be used.
	 * @param secondaryModifiers an int value representing the secondary section of
	 *                           a modifier. It should contain an int value whose
	 *                           least significant bits should equal
	 *                           {@code Integer.bitCount(secondaryModifier)} and
	 *                           should return exactly the number of modifier bits
	 *                           intended for this section
	 * @param tertiaryModifiers  an array representing the tertiary section
	 *                           modifier. The first element is the most significant
	 *                           group. It must be between 0 and 15. Each index
	 *                           represents the value of it's group in that position
	 *                           within the section. The next element also has the
	 *                           same range limit as the former and occupies the
	 *                           next 4 significant bits the last element is the
	 *                           same as the previous but occupies the 4 least
	 *                           significant bits of the tertiary section modifier.
	 *                           If a group is not needed just leave it's value at
	 *                           0.
	 *                           <p>
	 *                           Each element is an int that is the total length of
	 *                           the range - 1. For example, to represent the radix
	 *                           of a calculator (2, 8, 10, 16), the binary
	 *                           representation (1's, 2's, excess, nega-binary, smr,
	 *                           unsigned, math, floating-point) and input type
	 *                           (all, integer only, numbers only, symbols only), we
	 *                           would do [3, 7, 3].
	 * @param useGlobalModifier  the flag for whether the secondary and tertiary
	 *                           sections should include separate mappings for the
	 *                           global modifier bit.
	 * @throws IllegalArgumentException if any of the array argument's length != 0
	 *                                  or each element within them is greater than
	 *                                  15 or if the secondarModifiers value >
	 *                                  {@link Byte#MAX_VALUE}.
	 */
	public Key(int[] primaryModifiers, int secondaryModifiers, int[] tertiaryModifiers, boolean useGlobalModifier) {
		this(primaryModifiers, secondaryModifiers, tertiaryModifiers, useGlobalModifier, 0);
	}

	/*
	 * Date: 12 Aug 2022-----------------------------------------------------------
	 * Time created: 14:36:14---------------------------------------------------
	 */
	/**
	 * Constructs a {@code Key} object that is intended to be the head of the linked
	 * node. A performance intensive operation is carried out here to compute the
	 * keys for the mapping, so This is best suited for an initialiser than anything
	 * else.
	 * 
	 * <p>
	 * Each of the array arguments have a length of 3. the first element of the
	 * array is the most significant group in it's implied section, the next is the
	 * next significant group and the last element of the array is the least
	 * significant group.
	 * 
	 * @param primaryModifiers   an array representing the primary section modifier.
	 *                           The first element is the most significant group and
	 *                           contains the global bit modifier. Each index
	 *                           represents the value of it's group in that position
	 *                           within the section. It must be between 0 and 15.
	 *                           The next element also has the same range limit as
	 *                           the former but does not contain the global bit
	 *                           modifier and occupies the next 4 significant bits
	 *                           the last element is the same as the previous but
	 *                           occupies the 4 least significant bits of the
	 *                           primary section modifier. If a group is not needed
	 *                           just leave it's value at 0.
	 *                           <p>
	 *                           Each element is an int that contains one bits
	 *                           proportional to the number of modifier bits
	 *                           expected and in the position it is expected to be.
	 *                           For example, if the modifiers reciprocal, hyp and
	 *                           shift is expected as a group, then 7<sub>10</sub>
	 *                           would be the value for that group. This is because
	 *                           7<sub>10</sub> translates to 0111<sub>2</sub> which
	 *                           turns on it's last 3 bits to show that they are the
	 *                           actual modifier bits to be used.
	 * @param secondaryModifiers an int value representing the secondary section of
	 *                           a modifier. It should contain an int value whose
	 *                           least significant bits should equal
	 *                           {@code Integer.bitCount(secondaryModifier)} and
	 *                           should return exactly the number of modifier bits
	 *                           intended for this section
	 * @param tertiaryModifiers  an array representing the tertiary section
	 *                           modifier. The first element is the most significant
	 *                           group. It must be between 0 and 15. Each index
	 *                           represents the value of it's group in that position
	 *                           within the section. The next element also has the
	 *                           same range limit as the former and occupies the
	 *                           next 4 significant bits the last element is the
	 *                           same as the previous but occupies the 4 least
	 *                           significant bits of the tertiary section modifier.
	 *                           If a group is not needed just leave it's value at
	 *                           0.
	 *                           <p>
	 *                           Each element is an int that is the total length of
	 *                           the range - 1. For example, to represent the radix
	 *                           of a calculator (2, 8, 10, 16), the binary
	 *                           representation (1's, 2's, excess, nega-binary, smr,
	 *                           unsigned, math, floating-point) and input type
	 *                           (all, integer only, numbers only, symbols only), we
	 *                           would do [3, 7, 3].
	 * @param useGlobalModifier  the flag for whether the secondary and tertiary
	 *                           sections should include separate mappings for the
	 *                           global modifier bit.
	 * @param keyCode            this is the first key code from which subsequent
	 *                           key codes will be computed. the val {@code 0} is
	 *                           sufficient.
	 * @throws IllegalArgumentException if any of the array argument's length != 0
	 *                                  or each element within them is greater than
	 *                                  15 or if the secondarModifiers value >
	 *                                  {@link Byte#MAX_VALUE}.
	 */
	private Key(int[] primaryModifiers, int secondaryModifiers, int[] tertiaryModifiers, boolean useGlobalModifier,
			int keyCode) throws IllegalArgumentException {// the head of the linked list
		TreeMap<Integer, KeyForm> keyMap = new TreeMap<>();
		this.keyMap = new HashMap<>();

		for (int index = 0; index < MAX_PRIMARY_MODIFIER_BITS / MAX_BITS_PER_MODIFIER; index++) {
			if (primaryModifiers[index] > 0) {
				if (Integer.bitCount(primaryModifiers[index]) > MAX_BITS_PER_MODIFIER)
					throw new IllegalArgumentException("primaryModifier's index value must not surpass 4 bits");
				for (int i = 1; i <= primaryModifiers[index]; i++) {
					int shiftMask = MAX_SECONDARY_MODIFIER_BITS + MAX_TERTIARY_MODIFIER_BITS
							+ (MAX_BITS_PER_MODIFIER * (primaryModifiers.length - 1 - index));
					int combination = i << shiftMask;
					keyMap.put(combination, KeyForm.EMPTY_KEYFORM);
				}
			}
		}

		if (secondaryModifiers > 0) {// If this modifier value is not zero
			int bitCount = Integer.bitCount(secondaryModifiers);
			if (bitCount > MAX_SECONDARY_MODIFIER_BITS)
				throw new IllegalArgumentException("secondaryModifiers' index value must not surpass 8 bits");
			for (int i = 1; i <= secondaryModifiers; i++) {
				int shiftMask = MAX_TERTIARY_MODIFIER_BITS;
				int combination = i << shiftMask;
				keyMap.put(combination, KeyForm.EMPTY_KEYFORM);
				if (useGlobalModifier) {
					combination |= GLOBAL_MODIFIER_MASK;
					keyMap.put(combination, KeyForm.EMPTY_KEYFORM);
				}
			}
		}

		keyMap.put(0, KeyForm.EMPTY_KEYFORM);
		if (!keyMap.containsKey(GLOBAL_MODIFIER_MASK))
			keyMap.put(GLOBAL_MODIFIER_MASK, KeyForm.EMPTY_KEYFORM);
		this.keyMap.putAll(keyMap);

		Set<Integer> set = combineTertiaryModifier(tertiaryModifiers);

		for (int integer0 : keyMap.keySet()) {
			for (int integer1 : set)
				this.keyMap.put(integer0 | integer1, KeyForm.EMPTY_KEYFORM);
		}

		this.keyCode = keyCode;
		this.next = null;
	}

	/*
	 * Date: 13 Aug 2022-----------------------------------------------------------
	 * Time created: 06:21:31---------------------------------------------------
	 */
	/**
	 * Constructs a {@code Key} object that initialises all the internal field
	 * values with the given arguments. This constructor is well suited for
	 * returning the same object within the method of the {@code Key} class without
	 * the computational lag of {@link #Key(int[], int, int[], boolean, int)}.
	 * 
	 * @param keyMap  the mappings of modifiers and {@code KeyForm} objects
	 *                retrieved via {@link #getMap()}
	 * @param keyCode the unique key code of this node.
	 * @param next    the sibling of this node.
	 */
	protected Key(Map<Integer, KeyForm> keyMap, int keyCode, Key next) {// for preserving the current key.
		this.keyMap = keyMap;
		this.keyCode = keyCode;
		this.next = next;
	}

	/*
	 * Date: 13 Aug 2022-----------------------------------------------------------
	 * Time created: 06:28:16---------------------------------------------------
	 */
	/**
	 * Constructs a {@code Key} object that is intended to be a sibling of the node
	 * that called this constructor. This constructor is automatically called by a
	 * {@code Key} node on which {@link #appendSibling()} sibling is called and uses
	 * this constructor to construct its sibling using information present in the
	 * creator sibling to initialise it's values.
	 * 
	 * @param prev the caller of this constructor which is expected to be a
	 *             {@code Key} node and a sibling of this object about ot be
	 *             created.
	 * @param next a sibling of this object.
	 */
	protected Key(Key prev, Key next) {// for creating a new key
		keyMap = new TreeMap<>(prev.keyMap);
		this.keyCode = prev.keyCode + 1;
		this.next = next;
	}

	/*
	 * Date: 13 Aug 2022-----------------------------------------------------------
	 * Time created: 06:32:44--------------------------------------------
	 */
	/**
	 * Removes all mappings within this node (excluding any sibling(s)) that has
	 * {@code KeyForm.EMPTY_KEYFORM} as their value. That is key/value pairs using
	 * the default mapping.
	 * 
	 * @return {@code this} for more chaining actions.
	 */
	public Key trim() {
		for (int i : keyMap.keySet()) {
			if (keyMap.get(i).equals(KeyForm.EMPTY_KEYFORM))
				keyMap.remove(i, KeyForm.EMPTY_KEYFORM);
		}
		return this;
	}

	/*
	 * Date: 13 Aug 2022-----------------------------------------------------------
	 * Time created: 06:36:11--------------------------------------------
	 */
	/**
	 * returns the internal mapping of modifiers and {@code KeyForm} objects.
	 * 
	 * @return a {@code Map} object.
	 */
	public Map<Integer, KeyForm> getMap() {
		return keyMap;
	}

	/*
	 * Date: 13 Aug 2022-----------------------------------------------------------
	 * Time created: 06:37:05--------------------------------------------
	 */
	/**
	 * Gets the sibling of this {@code Key} node.
	 * 
	 * @return the sibling of this node.
	 */
	public Key next() {
		return next;
	}

	/*
	 * Date: 13 Aug 2022-----------------------------------------------------------
	 * Time created: 06:38:20--------------------------------------------
	 */
	/**
	 * Computes the number of nodes within this node and adds the result to the
	 * argument that returns that value.
	 * 
	 * @param start any int value. Use {@code 0} if the number of nodes is desired.
	 * @return the argument + {@link #length()}
	 */
	private int length(int start) {
		if (hasNext())
			return next.length(start + 1);
		return start + 1;
	}

	/*
	 * 
	 * Date: 13 Aug 2022-----------------------------------------------------------
	 * Time created: 06:40:37--------------------------------------------
	 */
	/**
	 * Gets the number of nodes in this node.
	 * 
	 * @return he number of node in proximity to the current node.
	 */
	public int length() {
		return length(0);
	}

	/*
	 * Date: 13 Aug 2022-----------------------------------------------------------
	 * Time created: 06:41:48--------------------------------------------
	 */
	/**
	 * Permutes this node to search for any resident node with the given keyCode and
	 * returns the first one found or throws an exception if no match is found.
	 * 
	 * @param keyCode the keyCode (specified by {@link #getKeyCode()}) of the node
	 *                to be returned.
	 * @return the {@code Key} node within this node whose keyCode is the same as
	 *         the argument.
	 * @throws IndexOutOfBoundsException  if
	 *                                    <code>keyCode >= {@link #length()}</code>
	 * @throws NegativeArraySizeException if <code>keyCode < 0</code>
	 */
	public Key get(int keyCode) throws IndexOutOfBoundsException, NegativeArraySizeException {
		if (keyCode < 0)
			throw new NegativeArraySizeException(new IndexOutOfBoundsException(keyCode).getMessage());
		if (keyCode == 0) {
			return this;
		} else if (keyCode > 0 && hasNext())
			return next.get(keyCode - 1);
		throw new IndexOutOfBoundsException(keyCode);
	}

	/*
	 * Date: 13 Aug 2022-----------------------------------------------------------
	 * Time created: 06:47:59--------------------------------------------
	 */
	/**
	 * The unique id of the current node.
	 * 
	 * @return the a unique keyCode typically used for identification of this node
	 *         excluding it's sibling.
	 */
	public int getKeyCode() {
		return keyCode;
	}

	/*
	 * Date: 13 Aug 2022-----------------------------------------------------------
	 * Time created: 06:49:19--------------------------------------------
	 */
	/**
	 * The number modifier-to-{@code KeyForm}-objects mappings in the node with the
	 * given keyCode.
	 * 
	 * @param keyCode the keyCode of the node whose mapping length will be returned
	 * @return the number of mappings in this node.
	 * @throws IndexOutOfBoundsException  if
	 *                                    <code>keyCode >= {@link #length()}</code>
	 * @throws NegativeArraySizeException if <code>keyCode < 0</code>
	 */
	public int numOfKeyForms(int keyCode) throws IndexOutOfBoundsException, NegativeArraySizeException {
		if (keyCode < 0)
			throw new NegativeArraySizeException(new IndexOutOfBoundsException(keyCode).getMessage());
		if (keyCode == 0)
			return keyMap.size();
		else if (keyCode > 0 && hasNext())
			return next.numOfKeyForms(keyCode - 1);
		throw new IndexOutOfBoundsException(keyCode);
	}

	/*
	 * Date: 13 Aug 2022-----------------------------------------------------------
	 * Time created: 07:02:39--------------------------------------------
	 */
	/**
	 * returns true if {@link #next()} == null or false if otherwise.
	 * 
	 * @return whether there is a valid sibling for this node.
	 */
	protected boolean hasNext() {
		return next != null;
	}

	/*
	 * Date: 13 Aug 2022-----------------------------------------------------------
	 * Time created: 07:11:36--------------------------------------------
	 */
	/**
	 * Searches this node and it's sibling(s) for a match using the keyCode argument
	 * and returns the {@code KeyForm} object mapped to the modifier argument.
	 * 
	 * @param keyCode  the unique id of the node (within this node) where the
	 *                 mapping resides
	 * @param modifier the modifier mapped to the {@link KeyForm} to be returned.
	 * @return the {@code KeyForm} to which the argument is mapped.
	 * @throws IndexOutOfBoundsException  if
	 *                                    <code>keyCode >= {@link #length()}</code>
	 * @throws NegativeArraySizeException if <code>keyCode < 0</code>
	 */
	protected KeyForm getKeyForm(int keyCode, int modifier)
			throws IndexOutOfBoundsException, NegativeArraySizeException {
		if (keyCode < 0)
			throw new NegativeArraySizeException(new IndexOutOfBoundsException(keyCode).getMessage());
		if (keyCode == 0)
			return keyMap.get(modifier);
		else if (keyCode > 0 && hasNext())
			return next.getKeyForm(keyCode - 1, modifier);
		throw new IndexOutOfBoundsException(keyCode);
	}

	/*
	 * Date: 13 Aug 2022-----------------------------------------------------------
	 * Time created: 07:03:55--------------------------------------------
	 */
	/**
	 * Returns the {@code KeyForm} object mapped to the argument in this node
	 * without taking into account it's siblings.
	 * 
	 * @param modifier the modifier mapped to the {@link KeyForm} to be returned.
	 * @return the {@code KeyForm} to which the argument is mapped within this node
	 *         excluding sibling(s).
	 */
	public KeyForm getKeyForm(int modifier) {
		return getKeyForm(0, modifier);
	}

	/*
	 * Date: 13 Aug 2022-----------------------------------------------------------
	 * Time created: 07:18:56--------------------------------------------
	 */
	/**
	 * Searches this node and it's sibling(s) for a match using the keyCode argument
	 * and sets (mutable operation) the {@code KeyForm} for the given modifier
	 * within this node and it's sibling(s) as the new value for the modifier key..
	 * 
	 * @param keyCode  the unique id of the node (within this node) where the
	 *                 mapping resides
	 * @param modifier the modifier mapped to the {@link KeyForm} to be returned.
	 * @param key      the {@code KeyForm} object that will be the new value of the
	 *                 specified key immediately after this method returns
	 *                 successfully
	 * @throws IndexOutOfBoundsException  if
	 *                                    <code>keyCode >= {@link #length()}</code>
	 * @throws NegativeArraySizeException if <code>keyCode < 0</code>
	 * @throws IllegalArgumentException   if there is no mapping for the given
	 *                                    modifier
	 */
	protected void setKeyForm(int keyCode, int modifier, KeyForm key)
			throws NegativeArraySizeException, IndexOutOfBoundsException {
		if (keyCode < 0)
			throw new NegativeArraySizeException(new IndexOutOfBoundsException(keyCode).getMessage());
		if (keyCode == 0) {
			if (keyMap.containsKey(modifier))
				keyMap.put(modifier, key);
			else
				throw new IllegalArgumentException("Unknown KeyForm");
		} else if (keyCode > 0 && hasNext())
			next.setKeyForm(keyCode - 1, modifier, key);
		throw new IndexOutOfBoundsException(keyCode);
	}

	/*
	 * Date: 13 Aug 2022-----------------------------------------------------------
	 * Time created: 07:19:03--------------------------------------------
	 */
	/**
	 * Sets (Mutable operation) the {@code KeyForm} for the given modifier within
	 * this node excluding sibling(s) as the new value for the modifier key.
	 * 
	 * @param modifier the modifier mapped to the {@link KeyForm} to be returned.
	 * @param key      the {@code KeyForm} object that will be the new value of the
	 *                 specified key immediately after this method returns
	 *                 successfully
	 * @throws IllegalArgumentException if there is no mapping for the given
	 *                                  modifier
	 */
	public void setKeyForm(int modifier, KeyForm key) {
		setKeyForm(0, modifier, key);
	}

	/*
	 * Date: 13 Aug 2022-----------------------------------------------------------
	 * Time created: 07:30:46--------------------------------------------
	 */
	/**
	 * Creates a new sibling at the end of the last node within this node.
	 * 
	 * @return the current node for further method chaining.
	 */
	public Key appendSibling() {
		return new Key(keyMap, keyCode, hasNext() ? next.appendSibling() : new Key(this, null));
	}

	/**
	 * The mapping for key action.
	 */
	private final Map<Integer, KeyForm> keyMap;
	/**
	 * A unique integer value within all nodes. This is implemented as the index of
	 * this button. For example if this index is 1, then this value is 1.
	 */
	private final int keyCode;
	/**
	 * The sibling of this node.
	 */
	private final Key next;

}
