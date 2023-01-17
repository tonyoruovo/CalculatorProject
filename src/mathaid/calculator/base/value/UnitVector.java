/*
 * Date: Jan 16, 2023 -----------------------------------------------------------
 * Time created: 8:01:03 AM ---------------------------------------------------
 */
/**
 * 
 */
package mathaid.calculator.base.value;

import java.util.HashMap;
import java.util.Map;

import mathaid.calculator.base.converter.AngleUnit;
import mathaid.calculator.base.util.Arith;

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
public class UnitVector {//implements Comparable<UnitVector> {

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

	public UnitVector(Map<String, BigFraction> m) {// (MomentFraction f, String unit) {
		vector = new HashMap<>(m);
	}
	
	public UnitVector(String s, BigFraction f) {// (MomentFraction f, String unit) {
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

	public BigFraction magnitude() {
		BigFraction accumulator = BigFraction.ZERO;
		for (BigFraction s : vector.values())
			accumulator = s.exponentiate(2).add(accumulator);
		return accumulator;
	}

	public BigFraction dotProduct(UnitVector v) {
		if (!sameSpace(v))
			throw new ArithmeticException("Can't be processed. Different spaces");
		BigFraction accumulator = BigFraction.ZERO;
		for (String s : vector.keySet()) {
			accumulator = vector.get(s).multiply(v.vector.get(s)).add(accumulator);
		}
		return accumulator;
	}

	public UnitVector unitVector() {
		return divide(magnitude());
	}

	public UnitVector crossProduct(UnitVector v) {
		if (!sameSpace(v))
			throw new ArithmeticException("Can't be processed. Different spaces");
		else if (vector.size() != 3 && vector.size() != 7)
			throw new ArithmeticException("Can't be processed");
		BigFraction f = magnitude().multiply(v.magnitude());
		f = f.multiply(new BigFraction(Arith.sin(angle(v).getFraction(), AngleUnit.RAD, f.getMathContext()),
				f.getMathContext(), null, f.getAccuracy()));
		return unitVector().multiply(f);
	}

	public BigFraction angle(UnitVector v) {
		if (!sameSpace(v))
			throw new ArithmeticException("Can't be processed. Different spaces");
		BigFraction m = magnitude().multiply(v.magnitude());
		return new BigFraction(Arith.acos(dotProduct(v).divide(m).getFraction(), AngleUnit.RAD, m.getMathContext()),
				m.getMathContext(), null, m.getAccuracy());
	}

	public UnitVector add(UnitVector addend) {
		if (!sameSpace(addend))
			throw new ArithmeticException("Can't be added. Different spaces");
		for (String s : vector.keySet()) {
			BigFraction f = vector.get(s);
			BigFraction f2 = addend.vector.get(s);
//			System.err.printf("%1$s, %2$s + %3$s = %4$s\r\n", s, f, f2, f.add(f2));
			vector.put(s, f.add(f2));
		}
		return new UnitVector(vector);
	}

	public UnitVector add(String s, BigFraction f) {
		vector.put(s, f);
		return new UnitVector(vector);
	}

	public UnitVector subtract(UnitVector subtrahend) {
		if (!sameSpace(subtrahend))
			throw new ArithmeticException("Can't be processed. Different spaces");
		for (String s : vector.keySet()) {
			BigFraction f = vector.get(s);
			BigFraction f2 = subtrahend.vector.get(s);
			vector.put(s, f.subtract(f2));
		}
		return new UnitVector(vector);
	}

	public UnitVector multiply(BigFraction scalar) {
		for (String s : vector.keySet())
			vector.put(s, vector.get(s).multiply(scalar));

		return new UnitVector(vector);
	}

	public UnitVector divide(BigFraction scalar) {
		for (String s : vector.keySet())
			vector.put(s, vector.get(s).divide(scalar));

		return new UnitVector(vector);
	}
	
	public String toString() {
		if(vector.size() == 1)
			return vector.values().iterator().next().toString();
		return vector.toString();
	}

	private final Map<String, BigFraction> vector;

}
