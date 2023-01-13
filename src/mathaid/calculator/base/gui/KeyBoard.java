/**
 * 
 */
package mathaid.calculator.base.gui;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/*
 * Date: 21 Sep 2022----------------------------------------------------------- 
 * Time created: 13:35:00---------------------------------------------------  
 * Package: mathaid.calculator.base.gui------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: KeyBoard.java------------------------------------------------------ 
 * Class name: KeyBoard------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class KeyBoard<T extends GUIComponent<T>, F> {

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

	public KeyBoard(List<IKey<T, F>> keys, int[] primaryModifiers, int secondaryModifiers, int[] tertiaryModifiers, boolean useGlobalModifier,
			T topMostContainer) {
		TreeSet<Integer> keyset = new TreeSet<>();
		supportedModifiers = new HashSet<>();

		for (int index = 0; index < MAX_PRIMARY_MODIFIER_BITS / MAX_BITS_PER_MODIFIER; index++) {
			if (primaryModifiers[index] > 0) {
				if (Integer.bitCount(primaryModifiers[index]) > MAX_BITS_PER_MODIFIER)
					throw new IllegalArgumentException("primaryModifier's index value must not surpass 4 bits");
				for (int i = 1; i <= primaryModifiers[index]; i++) {
					int shiftMask = MAX_SECONDARY_MODIFIER_BITS + MAX_TERTIARY_MODIFIER_BITS
							+ (MAX_BITS_PER_MODIFIER * (primaryModifiers.length - 1 - index));
					int combination = i << shiftMask;
					keyset.add(combination);//, KeyForm.EMPTY_KEYFORM);
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
				keyset.add(combination);//, KeyForm.EMPTY_KEYFORM);
				if (useGlobalModifier) {
					combination |= GLOBAL_MODIFIER_MASK;
					keyset.add(combination);//, KeyForm.EMPTY_KEYFORM);
				}
			}
		}

		keyset.add(0);//, KeyForm.EMPTY_KEYFORM);
		if (!keyset.contains(GLOBAL_MODIFIER_MASK))
			keyset.add(GLOBAL_MODIFIER_MASK);//, KeyForm.EMPTY_KEYFORM);
//		this.keyMap.putAll(keyMap);
		supportedModifiers.addAll(keyset);

		Set<Integer> set = combineTertiaryModifier(tertiaryModifiers);

		for (int integer0 : keyset) {
			for (int integer1 : set)
				supportedModifiers.add(integer0 | integer1);//, KeyForm.EMPTY_KEYFORM);
		}
		
		for(int i = 0; i < keys.size(); i++) {
			IKey<T, F> key = keys.get(i);
			key.configure(topMostContainer);
		}

		this.keys = Collections.unmodifiableList(keys);
	}
	
	public void setFaceIcon(int keyIndex, int modifier) {
		if(!supportedModifiers.contains(modifier))
			throw new IllegalArgumentException("unsupported modifier");
		keys.get(keyIndex).setIcon(modifier);
	}
	
	public final int getNumOfKeys() {
		return keys.size();
	}
	
	public Set<Integer> getSupportedModifiers() {
		return supportedModifiers;
	}
	
	private final List<IKey<T, F>> keys;

	private Set<Integer> supportedModifiers;
}
