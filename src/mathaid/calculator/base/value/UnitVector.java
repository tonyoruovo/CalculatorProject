/*
 * Date: Jan 16, 2023 -----------------------------------------------------------
 * Time created: 8:01:03 AM ---------------------------------------------------
 */
/**
 * 
 */
package mathaid.calculator.base.value;

import static mathaid.calculator.base.util.Utility.f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

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
 * Represents a number that consists of one or more numbers where each number is a scalar that represents a given unit found in
 * the {@link mathaid.calculator.base.converter} package. <p>Each number in the vector is implemented as a node that has a
 * {@code byte} and a numerical value. The {@code byte} describes the type of unit that the given node represents, while the
 * numeric value is it's literal value.<p>This makes the following possible:
 * 
 * <pre>
 *	<code>
 *		(0.5rad, 740$, 144km) + (455&#x00a5, 60deg, 300cm)
 *	</code>
 * </pre>
 * 
 * Notice both operand have the same vector dimension and units types, i.e angle, currency and distance.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 * @email tonyoruovo@gmail.com
 * 
 * @apiNote This class and the converter api in general needs to be refactored to use {@link BigFraction} as it's value rather
 *          than java's {@code BigFraction}.
 */
public class UnitVector {// implements Comparable<UnitVector> {

	// There are 17 of these
	/**
	 * The <span style="font-style:italic">byte</span> used for specifying that the numeric value of a node is representative of a
	 * {@link mathaid.calculator.base.converter.AngleUnit AngleUnit} value.
	 */
	public static final byte ANGLE = 0b0000_0001;
	/**
	 * The <span style="font-style:italic">byte</span> used for specifying that the numeric value of a node is representative of a
	 * {@link mathaid.calculator.base.converter.AreaUnit AreaUnit} value.
	 */
	public static final byte AREA = 0b0000_0010;
	/**
	 * The <span style="font-style:italic">byte</span> used for specifying that the numeric value of a node is representative of a
	 * {@link mathaid.calculator.base.converter.CurrencyUnit CurrencyUnit} value.
	 */
	public static final byte CURRENCY = 0b0000_0100;
	/**
	 * The <span style="font-style:italic">byte</span> used for specifying that the numeric value of a node is representative of a
	 * {@link mathaid.calculator.base.converter.DataUnit DataUnit} value.
	 */
	public static final byte DATA = 0b0000_1000;
	/**
	 * The <span style="font-style:italic">byte</span> used for specifying that the numeric value of a node is representative of a
	 * {@link mathaid.calculator.base.converter.DistanceUnit DistanceUnit} value.
	 */
	public static final byte DISTANCE = 0b0001_000;
	/**
	 * The <span style="font-style:italic">byte</span> used for specifying that the numeric value of a node is representative of a
	 * {@link mathaid.calculator.base.converter.EnergyUnit EnergyUnit} value.
	 */
	public static final byte ENERGY = 0b0010_0000;
	/**
	 * The <span style="font-style:italic">byte</span> used for specifying that the numeric value of a node is representative of a
	 * {@link mathaid.calculator.base.converter.ForceUnit ForceUnit} value.
	 */
	public static final byte FORCE = 0b0100_0000;// Vector
	/**
	 * The <span style="font-style:italic">byte</span> used for specifying that the numeric value of a node is representative of a
	 * {@link mathaid.calculator.base.converter.FrequencyUnit FrequencyUnit} value.
	 */
	public static final byte FREQUENCY = (byte) 0b1000_0000;
	/**
	 * The <span style="font-style:italic">byte</span> used for specifying that the numeric value of a node is representative of a
	 * {@link mathaid.calculator.base.converter.FuelConsumptionUnit FuelConsumptionUnit} value.
	 */
	public static final byte FUEL = (byte) 0b1000_0001;
	/**
	 * The <span style="font-style:italic">byte</span> used for specifying that the numeric value of a node is representative of a
	 * {@link mathaid.calculator.base.converter.MassUnit MassUnit} value.
	 */
	public static final byte MASS = (byte) 0b1000_0010;
	/**
	 * The <span style="font-style:italic">byte</span> used for specifying that the numeric value of a node is representative of a
	 * {@link mathaid.calculator.base.converter.PowerUnit PowerUnit} value.
	 */
	public static final byte POWER = (byte) 0b1000_0100;
	/**
	 * The <span style="font-style:italic">byte</span> used for specifying that the numeric value of a node is representative of a
	 * {@link mathaid.calculator.base.converter.PressureUnit PressureUnit} value.
	 */
	public static final byte PRESSURE = (byte) 0b1000_1000;
	/**
	 * The <span style="font-style:italic">byte</span> used for specifying that the numeric value of a node is representative of a
	 * {@link mathaid.calculator.base.converter.SpeedUnit SpeedUnit} value.
	 */
	public static final byte SPEED = (byte) 0b1001_0000;
	/**
	 * The <span style="font-style:italic">byte</span> used for specifying that the numeric value of a node is representative of a
	 * {@link mathaid.calculator.base.converter.TemperatureUnit TemperatureUnit} value.
	 */
	public static final byte TEMPERATURE = (byte) 0b1010_0000;
	/**
	 * The <span style="font-style:italic">byte</span> used for specifying that the numeric value of a node is representative of a
	 * {@link mathaid.calculator.base.converter.TemporalUnit TemopralUnit} value.
	 */
	public static final byte TEMPORAL = (byte) 0b1100_0000;
	/**
	 * The <span style="font-style:italic">byte</span> used for specifying that the numeric value of a node is representative of a
	 * {@link mathaid.calculator.base.converter.TorqueUnit TorqueUnit} value.
	 */
	public static final byte TORQUE = (byte) 0b1100_0001;
	/**
	 * The <span style="font-style:italic">byte</span> used for specifying that the numeric value of a node is representative of a
	 * {@link mathaid.calculator.base.converter.VolumeUnit VolumeUnit} value.
	 */
	public static final byte VOLUME = (byte) 0b1100_0010;

	/*
	 * Date: 28 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:58:31 ---------------------------------------------------
	 */
	/**
	 * Constructor for creating a {@code UnitVector} instantiated from nodes represented by the entries in the give {@code Map}.
	 * 
	 * @param m the entries that becomes the nodes/scalars of this vector.
	 */
	public UnitVector(Map<Byte, BigFraction> m) {// (MomentFraction f, String unit) {
		vector = new HashMap<>(m);
	}

	/*
	 * Date: 28 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:01:10 ---------------------------------------------------
	 */
	/**
	 * Constructor for creating a {@code UnitVector} with a single scalar. This is a vector with a single value. <p> This is
	 * equivalent to calling {@code new UnitVector(Map.of(s, f))}.
	 * 
	 * @param s the key representing the node unit type.
	 * @param f the value of this node.
	 */
	public UnitVector(byte s, BigFraction f) {// (MomentFraction f, String unit) {
		vector = new HashMap<>();
		vector.put(s, f);
	}

	/*
	 * Date: 28 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:04:21 ---------------------------------------------------
	 */
	/**
	 * Checks if the argument is in the same vector space as {@code this}. 2 values are in the vector space if, and only if, both
	 * have the dimension (size) and have the same exactly the same unit types. <p>If 2 vectors of different dimensions are to be
	 * concatenated then deficient units in both must be represented by a zero unit. For example, to add {@code (70m, 3.5J)} to
	 * {@code (1.2L, 8kg, 122.349ms)} one has to include the missing units:
	 * 
	 * <pre>
	 * <code>
	 *	(0L, 0g, 0s 70m, 3.5J) + (1.2L, 8kg, 122.349ms, 0km, 0J)
	 * </code>
	 * </pre>
	 * 
	 * Notice that litre, gramme and second is now added to the left operand and kilometre, joules are also added to the right
	 * operand. to allow both to be in the same space. It is not mandatory for the magnitudes to be {@code 0} they can be any valid
	 * non-null numeric value.
	 * 
	 * @param v the value to be compared to {@code this}.
	 * 
	 * @return <code>true</code> if the argument is the same space as {@code this}.
	 */
	public boolean sameSpace(UnitVector v) {
		if (vector.size() != v.vector.size())
			return false;
		for (Byte s : vector.keySet())
			if (!v.vector.containsKey(s))
				return false;
		return true;
	}

	/*
	 * Date: 28 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:07:33 ---------------------------------------------------
	 */
	/**
	 * Compare the argument to this asserting that both are in the same space and have the same magnitudes.
	 * 
	 * @param v the value to be compared to {@code this}.
	 * 
	 * @return {@code this == v}.
	 */
	public boolean equals(UnitVector v) {
		if (!sameSpace(v))
			return false;
		for (Byte s : vector.keySet())
			if (vector.get(s).compareTo(v.vector.get(s)) != 0)
				return false;
		return true;
	}

	/*
	 * Date: 28 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:39:07 ---------------------------------------------------
	 * @param mc provides rounding for the operation and result.
	 */
	/**
	 * Calculates the magnitude/euclidean-norm of this vector.
	 * 
	 * 
	 * @return <code>&par;this&par;</code>.
	 */
	public BigFraction magnitude() {
		BigFraction accumulator = BigFraction.ZERO;
		for (BigFraction s : vector.values())
			accumulator = s.exponentiate(f(2)).add(accumulator);//s.exponentiate(2).add(accumulator);
		//		return accumulator.exponentiate(BigFraction.HALF);
		return accumulator.exponentiate(f("0.5"));
	}

	/*
	 * Date: 28 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:41:03 ---------------------------------------------------
	 * @param mc provides rounding for the operation and result.
	 */
	/**
	 * Computes the dot product of {@code this} and {@code v}.
	 * 
	 * @param v  the right operand of the dot operation.
	 * 
	 * @return <code>this &sdot; v</code>.
	 */
	public BigFraction dotProduct(UnitVector v) {
		if (!sameSpace(v))
			throw new ArithmeticException("Can't be processed. Different spaces");
		BigFraction accumulator = BigFraction.ZERO;
		for (Byte s : vector.keySet()) {
			accumulator = vector.get(s).multiply(v.vector.get(s)).add(accumulator);
		}
		return accumulator;
	}

	/*
	 * Date: 28 Nov 2023 -----------------------------------------------------------
	 * Time created: 12:48:58 ---------------------------------------------------
	 * 
	 * @param mc provides rounding for the operation and result.
	 */
	/**
	 * Computes the unit vector of {@code this}.
	 * 
	 * @return &ucirc; or <sup>u</sup>/<sub>&par;u&par;</sub> where u = {@code this}.
	 */
	public UnitVector unitVector() {
		return divide(magnitude());
	}

	/*
	 * Date: Jan 17, 2023
	 * ----------------------------------------------------------- Time created:
	 * 2:32:08 PM ---------------------------------------------------
	 * @param mc provides rounding for the operation and result.
	 */
	/**
	 * Calculates the cross product of this vector and the specified vector. The cross product is only defined for 3-dimensional
	 * vectors.
	 *
	 * @param v  The vector to compute the cross product with.
	 * 
	 * @return A new vector representing the cross product of the two vectors.
	 * 
	 * @throws IllegalArgumentException If either vector is not 3-dimensional.
	 */
	// method for calculating the cross product
	public UnitVector crossProduct(UnitVector v) {
		// Check if the vectors are 3-dimensional
		if (this.vector.size() != 3 || v.vector.size() != 3) {
			throw new IllegalArgumentException("Cross product is only defined for 3-dimensional vectors.");
		}

		List<Map.Entry<Byte, BigFraction>> v1 = new ArrayList<>(new TreeSet<>(vector.entrySet()));
		List<Map.Entry<Byte, BigFraction>> v2 = new ArrayList<>(new TreeSet<>(v.vector.entrySet()));

		BigFraction x = v1.get(1).getValue().multiply(v2.get(2).getValue())
				.subtract(v1.get(2).getValue().multiply(v2.get(1).getValue()));

		BigFraction y = v1.get(2).getValue().multiply(v2.get(0).getValue())
				.subtract(v1.get(0).getValue().multiply(v2.get(2).getValue()));

		BigFraction z = v1.get(0).getValue().multiply(v2.get(1).getValue())
				.subtract(v1.get(1).getValue().multiply(v2.get(0).getValue()));

		return new UnitVector(Map.of(v1.get(0).getKey(), z, v1.get(1).getKey(), x, v1.get(2).getKey(), y));
	}

	/*
	 * Date: Jan 17, 2023 -----------------------------------------------------------
	 * Time created: 2:59:51 PM ---------------------------------------------------
	 * @param mc provides rounding for the operation and result.
	 */
	/**
	 * Calculates the angle in radians between this vector and the specified vector. Both vectors must be non-zero and have the same
	 * dimension.
	 *
	 * @param v  The vector to compute the angle with.
	 * 
	 * @return The angle in radians between the two vectors.
	 * 
	 * @throws IllegalArgumentException If either vector is zero or vectors have different dimensions.
	 * @throws ArithmeticException      If division by zero occurs during angle calculation.
	 */
	// method for calculating the angle between two vectors in radians
	public BigFraction angle(UnitVector v) {
		// Check if the vectors are non-zero and have the same dimension
		if (!sameSpace(v)) {
			throw new IllegalArgumentException(
					"Cannot calculate angle for zero vectors or vectors with different dimensions.");
		}

		BigFraction dotProduct = dotProduct(v);
		BigFraction magnitudeProduct = magnitude().multiply(v.magnitude());

		// Avoid division by zero
		if (magnitudeProduct.signum() == 0) {
			throw new ArithmeticException("Cannot divide by zero when calculating angle.");
		}

		// Calculate the cosine of the angle
		BigFraction cosTheta = dotProduct.divide(magnitudeProduct);

		// Use arccosine to get the angle in radians
		return f(Arith.acos(cosTheta.getFraction(), AngleUnit.RAD, cosTheta.getMathContext()));
	}

	/*
	 * Date: 28 Nov 2023 -----------------------------------------------------------
	 * Time created: 13:05:51 ---------------------------------------------------
	 * @param mc     provides rounding for the operation and result.
	 */
	/**
	 * Calculates the sum of {@code this} and the vector argument.
	 * 
	 * @param addend the right operand of the addition.
	 * 
	 * @return {@code this + addend}.
	 */
	public UnitVector add(UnitVector addend) {
		if (!sameSpace(addend))
			throw new ArithmeticException("Can't be added. Different spaces");
		for (Byte s : vector.keySet()) {
			BigFraction f = vector.get(s);
			BigFraction f2 = addend.vector.get(s);
			vector.put(s, f.add(f2));
		}
		return new UnitVector(vector);
	}

	/*
	 * Date: 28 Nov 2023 -----------------------------------------------------------
	 * Time created: 13:05:51 ---------------------------------------------------
	 */
	/**
	 * Creates a new node inside {@code this} using the arguments.
	 * 
	 * @param s the key representing the new node's unit type.
	 * @param f the value of the new node.
	 * 
	 * @return a new vector with the given values as an element.
	 * 
	 * @see #UnitVector(byte, BigFraction)
	 */
	public UnitVector add(byte s, BigFraction f) {
		vector.put(s, f);
		return new UnitVector(vector);
	}

	/*
	 * Date: 28 Nov 2023 -----------------------------------------------------------
	 * Time created: 13:05:51 ---------------------------------------------------
	 * @param mc         provides rounding for the operation and result.
	 */
	/**
	 * Subtracts {@code this} from the vector argument.
	 * 
	 * @param subtrahend the right operand of the subtraction.
	 * 
	 * @return {@code this - subtrahend}.
	 */
	public UnitVector subtract(UnitVector subtrahend) {
		if (!sameSpace(subtrahend))
			throw new ArithmeticException("Can't be processed. Different spaces");
		for (Byte s : vector.keySet()) {
			BigFraction f = vector.get(s);
			BigFraction f2 = subtrahend.vector.get(s);
			vector.put(s, f.subtract(f2));
		}
		return new UnitVector(vector);
	}

	/*
	 * Date: 28 Nov 2023 -----------------------------------------------------------
	 * Time created: 13:05:51 ---------------------------------------------------
	 * @param mc     provides rounding for the operation and result.
	 */
	/**
	 * Calculates the product of {@code this} and the {@code scalar} argument.<p>This is scalar multiplication.
	 * 
	 * @param scalar the right operand of the multiplication.
	 * 
	 * @return {@code this * scalar}.
	 */
	public UnitVector multiply(BigFraction scalar) {
		for (Byte s : vector.keySet())
			vector.put(s, vector.get(s).multiply(scalar));

		return new UnitVector(vector);
	}

	/*
	 * Date: 28 Nov 2023 -----------------------------------------------------------
	 * Time created: 13:05:51 ---------------------------------------------------
	 * @param mc     provides rounding for the operation and result.
	 */
	/**
	 * Divides {@code this} by the {@code scalar} argument.<p>This is scalar division.
	 * 
	 * @param scalar the right operand of the division.
	 * 
	 * @return {@code this / scalar}.
	 */
	public UnitVector divide(BigFraction scalar) {
		for (Byte s : vector.keySet())
			vector.put(s, vector.get(s).divide(scalar));

		return new UnitVector(vector);
	}

	/*
	 * Date: 28 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:48:39 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	public String toString() {
		if (vector.size() == 1)
			return vector.values().iterator().next().toString();
		return vector.toString();
	}

	/**
	 * Holds the elements along with their unit corresponding types
	 */
	private final Map<Byte, BigFraction> vector;

}
