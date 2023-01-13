/**
 * 
 */
package mathaid.calculator.base.converter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mathaid.calculator.base.converter.Currencies.MediumOfExchange;
import mathaid.calculator.base.converter.Currencies.Website;

/*
 * Date: 16 Jul 2021----------------------------------------------------------- 
 * Time created: 14:47:38---------------------------------------------------  
 * Package: mathaid.calculator.base.converter------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: XeDotCom.java------------------------------------------------------ 
 * Class name: XeDotCom------------------------------------------------ 
 */
/**
 * A class that provides concrete implementation for the abstract class
 * {@code Website}. The aim of this class is to retrieve the HTML file of
 * <a href="www.xe.com">www.xe.com</a> and append the uri that triggers
 * conversion on the site to the one that leads to the homepage. Then when the
 * results are given, search through the file and parse the result as a number.
 * 
 * @implSpec An internet connection is required for this class to function.
 * @author Oruovo Anthony Etineakpopha
 */
final class XeDotCom extends Website {

	/**
	 * The url constant string of the page the converter resides in.
	 */
	private static final String BASE_URL = "https://www.xe.com/currencyconverter/convert/?Amount=1&From=";
	/**
	 * A constant inserted at come mid-point of the url.
	 */
	private static final String MID = "&To=";
	/**
	 * /** The class attribute name of the tag where the element that contains the
	 * result is located.
	 */
	private static final String RESIDENT_CLASS_NAME = "result__BigRate";

	/*
	 * Date: 16 Jul 2021-----------------------------------------------------------
	 * Time created: 14:47:38---------------------------------------------------
	 */
	/**
	 * Package private constructor (package private because of the "final" modifier
	 * in the class declaration) that initialises this object.
	 */
	public XeDotCom() {
		super(BASE_URL, java.util.Arrays.asList(
				FxtopDotCom.valueOf(965),
				FxtopDotCom.valueOf(990),
				FxtopDotCom.valueOf(970),
				FxtopDotCom.valueOf(203),
				FxtopDotCom.valueOf(979),
				FxtopDotCom.valueOf(994),
				FxtopDotCom.valueOf(940),
				FxtopDotCom.valueOf(932)));
	}

	/*
	 * Most Recent Date: 16 Jul 2021-----------------------------------------------
	 * Most recent time created: 14:47:43--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * <p>
	 * An internet connection is needed for this method to function.
	 * </p>
	 * 
	 * @param from {@inheritDoc}
	 * @param x    {@inheritDoc}
	 * @param to   {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public BigDecimal convert(MediumOfExchange from, BigDecimal x, MediumOfExchange to) {
		if (from.compareTo(to) == 0)
			return x;
		BigDecimal n;
		String url = getUrl() + from.getIsoCode() + MID + to.getIsoCode();
		List<String> l = new ArrayList<>();
		search(url, RESIDENT_CLASS_NAME, l);
		n = sanitize(l.get(0));
		n = x.multiply(n, DEF);
		
		BigDecimal n2 = n.setScale(to.getFractionalDigits(), DEF.getRoundingMode());
		if(n2.compareTo(BigDecimal.ZERO) == 0)
			return n;
		return n2;
//		return n.setScale(to.getFractionalDigits(), RoundingMode.HALF_EVEN);
//		String s = n.toPlainString();
//		int numOfIntegerDigits = s.substring(0, s.indexOf(".")).length();
		/*
		 * TODO: use BigDecimal.scale() to get the num of fractional digits
		 */
//		return n.round(new MathContext(numOfIntegerDigits + to.getFractionalDigits(), RoundingMode.HALF_EVEN));
	}

}
