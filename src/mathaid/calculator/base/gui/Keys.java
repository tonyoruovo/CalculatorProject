/**
 * 
 */
package mathaid.calculator.base.gui;

import java.util.Objects;

import mathaid.calculator.base.util.Utility;

/*
 * Date: 16 Jul 2022----------------------------------------------------------- 
 * Time created: 15:05:03---------------------------------------------------  
 * Package: mathaid.calculator.base.model------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: Keys.java------------------------------------------------------ 
 * Class name: Keys------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class Keys {
	public static Keys build(int numOfKeys, int modifiers, boolean useGlobalModifier) {
		Keys keys = new Keys(modifiers, useGlobalModifier);
		for (int j = 1; j < numOfKeys; j++) {
			keys.append();
		}
		return keys;
	}

	public Keys(Key head) {
		this.head = head;
	}

	public Keys(int modifiersDefault, boolean useGlobalModifier) {// TODO: intToString hard-coded

		StringBuilder modAsBinary = new StringBuilder(Integer.toBinaryString(modifiersDefault));
		modAsBinary.insert(0, Utility.string('0', Integer.SIZE - modAsBinary.length()));
//		if (modAsBinary.length() != Integer.SIZE)
//			throw new IllegalArgumentException("modifier length must be 32");
		int priMod0 = Integer.parseInt(modAsBinary.substring(0, Key.MAX_BITS_PER_MODIFIER), 2);
		modAsBinary.delete(0, Key.MAX_BITS_PER_MODIFIER);
		int priMod1 = Integer.parseInt(modAsBinary.substring(0, Key.MAX_BITS_PER_MODIFIER), 2);
		modAsBinary.delete(0, Key.MAX_BITS_PER_MODIFIER);
		int priMod2 = Integer.parseInt(modAsBinary.substring(0, Key.MAX_BITS_PER_MODIFIER), 2);
		modAsBinary.delete(0, Key.MAX_BITS_PER_MODIFIER);
		int secMod = Integer.parseInt(modAsBinary.substring(0, Key.MAX_SECONDARY_MODIFIER_BITS), 2);
		modAsBinary.delete(0, Key.MAX_SECONDARY_MODIFIER_BITS);
		int tetMod0 = Integer.parseInt(modAsBinary.substring(0, Key.MAX_BITS_PER_MODIFIER), 2);
		modAsBinary.delete(0, Key.MAX_BITS_PER_MODIFIER);
		int tetMod1 = Integer.parseInt(modAsBinary.substring(0, Key.MAX_BITS_PER_MODIFIER), 2);
		modAsBinary.delete(0, Key.MAX_BITS_PER_MODIFIER);
		int tetMod2 = Integer.parseInt(modAsBinary.substring(0, Key.MAX_BITS_PER_MODIFIER), 2);
		modAsBinary.delete(0, Key.MAX_BITS_PER_MODIFIER);

		head = new Key(new int[] { priMod0, priMod1, priMod2 }, secMod, new int[] { tetMod0, tetMod1, tetMod2, },
				useGlobalModifier);
	}

	public int length() {
		if (head != null)
			return head.length();
		return 0;
	}

	public Keys append(Key k) {
		head = head != null ? head.appendSibling() : Objects.requireNonNull(k, "Cannot insert null key");
		return this;
	}

	public Keys append() {
		return this.append(null);
	}

	public Key getKey(int keyCode) {
		Objects.requireNonNull(head, "No element in the list");
		return head.get(keyCode);
	}
	
	public Key headKey() {
		return head;
	}
	
	public Key tailKey() {
		return head.get(length() - 1);
	}

	private Key head;
}
