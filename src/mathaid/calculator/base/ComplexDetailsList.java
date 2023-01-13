/**
 * 
 */
package mathaid.calculator.base;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import mathaid.calculator.base.value.Complex;

/*
 * Date: 10 Apr 2021----------------------------------------------------------- 
 * Time created: 13:56:43---------------------------------------------------  
 * Package: mathaid.calculator.base------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: ComplexDetailsList.java------------------------------------------------------ 
 * Class name: ComplexDetailsList------------------------------------------------ 
 */
/**
 * A {@code DetailsList} helper class. It can also be used to retrieve complex
 * numbers in TeX notation, but users should refrain from doing that in isolation because
 * values returned by methods are dependent upon the current state of the
 * {@code Settings} object.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
class ComplexDetailsList {

	/*
	 * Date: 10 Apr 2021-----------------------------------------------------------
	 * Time created: 13:56:43---------------------------------------------------
	 */
	/**
	 * Constructs this object with a {@code String} representation of a complex
	 * number.
	 * 
	 * @param symjaExpression a string representing a complex number.
	 */
	public ComplexDetailsList(String symjaExpression) {
		this.number = symjaExpression;
		String n = number.replaceAll("Complex\\(", "").replaceAll("Complex\\[", "").replaceAll("\\]", "").replaceAll("\\)", "");
		String r = n.substring(0, n.indexOf(','));
		String i = n.substring(n.indexOf(",")).replace(",", "");
		z = new Complex(new BigDecimal(r.trim()), new BigDecimal(i.trim()),
				new MathContext(Settings.defaultSetting().getScale(), RoundingMode.HALF_EVEN));
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 13:26:01--------------------------------------------
	 */
	/**
	 * Returns the TeX notation for the decimal (specified at the constructor). This
	 * may also display recurring digits.
	 * 
	 * @return a {@code DataText} with the TeX notation and the symja notation of a
	 *         complex number as its components.
	 */
	public DataText getExpression() {
		// the complex value
		return new DataText(asTeXRecurring(z), "Complex[" + z.real() + "," + z.imaginary() + "]");
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 13:28:04--------------------------------------------
	 */
	/**
	 * Returns the TeX notation for complex number but with both the real and
	 * imaginary fields represented as common fraction.
	 * 
	 * @return {@code DataText} with the TeX notation and the symja notation of a
	 *         complex number as its components.
	 */
	public DataText getExpressionAsFraction() {
		FractionalDetailsList re = new FractionalDetailsList(new BigDecimal(getReal().getSymjaString()));
		FractionalDetailsList im = new FractionalDetailsList(new BigDecimal(getImaginary().getSymjaString()));
		return new DataText(re.getExpression().getTeXString() + (z.imaginary().signum() == 1 ? "+" : "")
				+ im.getExpression().getTeXString() + "\\,i", getExpression().getSymjaString());
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 13:29:32--------------------------------------------
	 */
	/**
	 * Returns the real part.
	 * 
	 * @return the real part.
	 */
	public DataText getReal() {
		// the real value
		DecimalDetailsList d = new DecimalDetailsList(z.real().toPlainString());
		return new DataText(d.getExpression().getTeXString(), z.real().toString());
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 13:30:08--------------------------------------------
	 */
	/**
	 * Returns the imaginary part.
	 * 
	 * @return the inaginary part.
	 */
	public DataText getImaginary() {
		// The imaginary value
		DecimalDetailsList d = new DecimalDetailsList(z.imaginary().toPlainString());
		return new DataText(d.getExpression().getTeXString(), z.real().toString());
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 13:30:38--------------------------------------------
	 */
	/**
	 * Returns the argument of the complex number.
	 * 
	 * @return the argument of the complex number.
	 */
	public DataText getArg() {
		// The argument (angle subtended by the number in the complex plain)
		DecimalDetailsList d = new DecimalDetailsList(z.argument().toPlainString());
		return new DataText(d.getExpression().getTeXString(), z.real().toString());
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 13:31:16--------------------------------------------
	 */
	/**
	 * Returns the magnitude of the complex number.
	 * 
	 * @return the magnitude of the complex number.
	 */
	public DataText getMagnitude() {
		// the magnitude (radius of the number in the complex plain)
		DecimalDetailsList d = new DecimalDetailsList(z.magnitude().toPlainString());
		return new DataText(d.getExpression().getTeXString(), z.real().toString());
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 13:31:39--------------------------------------------
	 */
	/**
	 * Returns the X coordinate of the rectangular coordinate of a complex value.
	 * 
	 * @return the X coordinate part of the rectangular coordinate of a complex
	 *         value.
	 */
	public DataText getHorizontal() {
		// the rectangular horizontal
		DecimalDetailsList d = new DecimalDetailsList(z.toRectangularCoordinates()[0].toPlainString());
		return new DataText(d.getExpression().getTeXString(), z.real().toString());
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 13:32:37--------------------------------------------
	 */
	/**
	 * Returns the Y coordinate of the rectangular coordinate of a complex value.
	 * 
	 * @return the Y coordinate part of the rectangular coordinate of a complex
	 *         value.
	 */
	public DataText getVertical() {
		// the rectangular vertical
		DecimalDetailsList d = new DecimalDetailsList(z.toRectangularCoordinates()[1].toPlainString());
		return new DataText(d.getExpression().getTeXString(), z.real().toString());
	}

	/*
	 * Date: 11 Aug 2021-----------------------------------------------------------
	 * Time created: 13:32:59--------------------------------------------
	 */
	/**
	 * Returns the argument in TeX notational string.
	 * 
	 * @param z the value to be returned.
	 * @return a TeX representation of the argument.
	 */
	private static String asTeXRecurring(Complex z) {
		if (z.isIndeterminate())
			return "i";
		else if (z.isImaginary()) {
			String teXIm = new DecimalDetailsList(z.imaginary().toPlainString()).getExpression().getTeXString() + "~i";
			return teXIm;
		} else if (z.isReal()) {
			String teXReal = new DecimalDetailsList(z.real().toPlainString()).getExpression().getTeXString();
			return teXReal;
		}
		String teXReal = new DecimalDetailsList(z.real().toPlainString()).getExpression().getTeXString();
		String teXIm = new DecimalDetailsList(z.imaginary().toPlainString()).getExpression().getTeXString();
		return teXReal + "~~" + (z.imaginary().signum() >= 0 ? "+" : "") + "~~" + teXIm + "~i";
	}

	/**
	 * The complex number in symja notation.
	 */
	private final String number;
	/**
	 * The complex number.
	 */
	private final Complex z;

}
