package mathaid.calculator.base.converter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mathaid.calculator.base.converter.Currencies.MediumOfExchange;
import mathaid.calculator.base.converter.Currencies.Website;

/*
 * Date: 16 Aug 2021----------------------------------------------------------- 
 * Time created: 03:59:28---------------------------------------------------  
 * Package: mathaid.calculator.base.converter------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: WiseDotCom.java------------------------------------------------------ 
 * Class name: WiseDotCom------------------------------------------------ 
 */
/**
 * A class that provides concrete implementation for the abstract class
 * {@code Website}. The aim of this class is to retrieve the HTML file of
 * <a href="www.wise.com">www.wise.com</a> and append the uri that triggers
 * conversion on the site to the one that leads to the homepage. Then when the
 * results are given, search through the file and parse the result as a number.
 * 
 * @implSpec An internet connection is required for this class to function.
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
final class WiseDotCom extends Website {
	/**
	 * The url constant string of the page the converter resides in.
	 */
	private static final String BASE_URL = "https://wise.com/gb/currency-converter/";
	/**
	 * A constant inserted in the mid of the url that is between the {@link #BASE_URL} and {@link #END}.
	 */
	private static final String MID = "-to-";
	/**
	 * A constant appended to the end of the url that is after {@link #MID}.
	 */
	private static final String END = "-rate?amount=1";
	/**
	 * The class attribute name of the tag where the element that contains the result is located.
	 */
	private static final String RESIDENT_CLASS_NAME = "text-success";

	/*
	 * Date: 16 Jul 2021-----------------------------------------------------------
	 * Time created: 22:12:51---------------------------------------------------
	 */
	/**
	 * Package private constructor (package private because of the "final" modifier in the class declaration) that initialises this object.
	 */
	protected WiseDotCom() {
		super(BASE_URL, Arrays.asList(
				FxtopDotCom.valueOf(971),
				FxtopDotCom.valueOf(965),
				FxtopDotCom.valueOf(974),
				FxtopDotCom.valueOf(108),
				FxtopDotCom.valueOf(990),
				FxtopDotCom.valueOf(970),
				FxtopDotCom.valueOf(976),
				FxtopDotCom.valueOf(192),
				FxtopDotCom.valueOf(931),
				FxtopDotCom.valueOf(214),
				FxtopDotCom.valueOf(232),
				FxtopDotCom.valueOf(960),
				FxtopDotCom.valueOf(364),
				FxtopDotCom.valueOf(368),
				FxtopDotCom.valueOf(410),
				FxtopDotCom.valueOf(426),
				FxtopDotCom.valueOf(434),
				FxtopDotCom.valueOf(440),
				FxtopDotCom.valueOf(478),
				FxtopDotCom.valueOf(979),
				FxtopDotCom.valueOf(676),
				FxtopDotCom.valueOf(994),
				FxtopDotCom.valueOf(706),
				FxtopDotCom.valueOf(938),
				FxtopDotCom.valueOf(760),
				FxtopDotCom.valueOf(940),
				FxtopDotCom.valueOf(937),
				FxtopDotCom.valueOf(886),
				FxtopDotCom.valueOf(932),
				FxtopDotCom.valueOf(959),
				FxtopDotCom.valueOf(964),
				FxtopDotCom.valueOf(962),
				FxtopDotCom.valueOf(961),
				FxtopDotCom.valueOf('X','B','T')));
	}

	/*
	 * Most Recent Date: 16 Jul 2021-----------------------------------------------
	 * Most recent time created: 22:13:01--------------------------------------
	 */
	/**
	 * {@inheritDoc}<p>An internet connection is needed for this method to function.</p>
	 * 
	 * @param from {@inheritDoc}
	 * @param x {@inheritDoc}
	 * @param to {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public BigDecimal convert(MediumOfExchange from, BigDecimal x, MediumOfExchange to) {
		if (from.compareTo(to) == 0)
			return x;
		BigDecimal n;
		String url = getUrl() + from.getIsoCode().toLowerCase() + MID + to.getIsoCode().toLowerCase() + END;
		List<String> l = new ArrayList<>();
		try {			
			search(url, RESIDENT_CLASS_NAME, l);
		}catch(Throwable t) {
			throw t;
		}
		n = sanitize(l.get(0));
		n = x.multiply(n, DEF);
		
		BigDecimal n2 = n.setScale(to.getFractionalDigits(), DEF.getRoundingMode());
		if(n2.compareTo(BigDecimal.ZERO) == 0)
			return n;
		return n2;
//		return n.setScale(to.getFractionalDigits(), RoundingMode.HALF_EVEN);
//		String  = n.toPlainString();
//		int numOfIntegerDigits = s.substring(0, s.indexOf(".")).length();
		/*
		 * TODO: use BigDecimal.scale() to get the num of fractional digits
		 */
//		return n.round(new MathContext(numOfIntegerDigits + to.getFractionalDigits(), RoundingMode.HALF_EVEN));
	}

}