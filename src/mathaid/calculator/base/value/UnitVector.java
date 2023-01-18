/*
 * Date: Jan 16, 2023 -----------------------------------------------------------
 * Time created: 8:01:03 AM ---------------------------------------------------
 */
/**
 * 
 */
package mathaid.calculator.base.value;

import static mathaid.calculator.base.util.Arith.pow;
import static mathaid.calculator.base.util.Utility.d;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;

/*
 * Date: Jan 16, 2023 -----------------------------------------------------------
 * Time created: 8:01:03 AM ---------------------------------------------------
 * Package: mathaid.calculator.base.value ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: UnitVector.java ------------------------------------------------------
 * Class name: UnitVector ------------------------------------------------
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class UnitVector {// implements Comparable<UnitVector> {

	// There are 17 of these
	public static final String ANGLE = "Angle";
	public static final String AREA = "Area";
	public static final String CURRENCY = "Currency";
	public static final String DATA = "Data";
	public static final String DISTANCE = "Distance";
	public static final String ENERGY = "Energy";
	public static final String FORCE = "Force";// Vector
	public static final String FREQUENCY = "Frequency";
	public static final String FUEL = "FuelConsumption";
	public static final String MASS = "Mass";
	public static final String POWER = "Power";
	public static final String PRESSURE = "Pressure";
	public static final String SPEED = "Speed";
	public static final String TEMPERATURE = "Temperature";
	public static final String TEMPORAL = "Temporal";
	public static final String TORQUE = "Torque";
	public static final String VOLUME = "Volume";

	public UnitVector(Map<String, BigDecimal> m) {// (MomentFraction f, String unit) {
		vector = new HashMap<>(m);
	}

	public UnitVector(String s, BigDecimal f) {// (MomentFraction f, String unit) {
		vector = new HashMap<>();
		vector.put(s, f);
	}

	public boolean sameSpace(UnitVector v) {
		if (vector.size() != v.vector.size())
			return false;
		for (String s : vector.keySet())
			if (!v.vector.containsKey(s))
				return false;
		return true;
	}

	public boolean equals(UnitVector v) {
		if (!sameSpace(v))
			return false;
		for (String s : vector.keySet())
			if (vector.get(s).compareTo(v.vector.get(s)) != 0)
				return false;
		return true;
	}

	public BigDecimal magnitude(MathContext mc) {
		BigDecimal accumulator = BigDecimal.ZERO;
		for (BigDecimal s : vector.values())
			accumulator = pow(s, d(2), mc).add(accumulator);//s.exponentiate(2).add(accumulator);
//		return accumulator.exponentiate(BigDecimal.HALF);
		return pow(accumulator, d("0.5"), mc);
	}

	public BigDecimal dotProduct(UnitVector v, MathContext mc) {
		if (!sameSpace(v))
			throw new ArithmeticException("Can't be processed. Different spaces");
		BigDecimal accumulator = BigDecimal.ZERO;
		for (String s : vector.keySet()) {
			accumulator = vector.get(s).multiply(v.vector.get(s), mc).add(accumulator, mc);
		}
		return accumulator;
	}

	public UnitVector unitVector(MathContext mc) {
		return divide(magnitude(mc), mc);
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 2:32:08 PM ---------------------------------------------------
	 */
	/*
	 * @param v
	 * @return
	 * @deprecated Cannot take the cross-product of a vector unit because
	 *             <ol>
	 *             <li>Results may often lead to negative values</li>
	 *             <li>Is logical only for Triple and Septuple vectors</li>
	 *             <li>Causes cross multiplication which will cause incompatible
	 *             units to operate on ecah other</li>
	 *             </ol>
	 */
//	public UnitVector crossProduct(UnitVector v) {
//		if (!sameSpace(v))
//			throw new ArithmeticException("Can't be processed. Different spaces");
//		else if (vector.size() != 3 && vector.size() != 7)
//			throw new ArithmeticException("Can't be processed");
//		BigDecimal f = magnitude().multiply(v.magnitude());
//		f = f.multiply(new BigDecimal(Arith.sin(angle(v).getFraction(), AngleUnit.RAD, f.getMathContext()),
//				f.getMathContext(), null, f.getAccuracy()));
//		return unitVector().multiply(f);
//	}

	/*
	 * Date: Jan 17, 2023 -----------------------------------------------------------
	 * Time created: 2:59:51 PM ---------------------------------------------------
	 */
	/*
	 * @param v
	 * @return
	 * @deprecated Does not function properly
	 */
//	public BigDecimal angle(UnitVector v) {
//		if (!sameSpace(v))
//			throw new ArithmeticException("Can't be processed. Different spaces");
//		BigDecimal m = magnitude().multiply(v.magnitude());
//		return new BigDecimal(Arith.acos(dotProduct(v).divide(m).getFraction(), AngleUnit.RAD, m.getMathContext()),
//				m.getMathContext(), null, m.getAccuracy());
//	}

	public UnitVector add(UnitVector addend, MathContext mc) {
		if (!sameSpace(addend))
			throw new ArithmeticException("Can't be added. Different spaces");
		for (String s : vector.keySet()) {
			BigDecimal f = vector.get(s);
			BigDecimal f2 = addend.vector.get(s);
			vector.put(s, f.add(f2, mc));
		}
		return new UnitVector(vector);
	}

	public UnitVector add(String s, BigDecimal f) {
		vector.put(s, f);
		return new UnitVector(vector);
	}

	public UnitVector subtract(UnitVector subtrahend, MathContext mc) {
		if (!sameSpace(subtrahend))
			throw new ArithmeticException("Can't be processed. Different spaces");
		for (String s : vector.keySet()) {
			BigDecimal f = vector.get(s);
			BigDecimal f2 = subtrahend.vector.get(s);
			vector.put(s, f.subtract(f2, mc));
		}
		return new UnitVector(vector);
	}

	public UnitVector multiply(BigDecimal scalar, MathContext mc) {
		for (String s : vector.keySet())
			vector.put(s, vector.get(s).multiply(scalar, mc));

		return new UnitVector(vector);
	}

	public UnitVector divide(BigDecimal scalar, MathContext mc) {
		for (String s : vector.keySet())
			vector.put(s, vector.get(s).divide(scalar, mc));

		return new UnitVector(vector);
	}

	public String toString() {
		if (vector.size() == 1)
			return vector.values().iterator().next().toString();
		return vector.toString();
	}

	private final Map<String, BigDecimal> vector;

}
