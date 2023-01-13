/**
 * 
 */
package mathaid.calculator.base.converter;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

import mathaid.BaseException;
import mathaid.ExceptionMessage;
import mathaid.calculator.base.converter.Currencies.MediumOfExchange;
import mathaid.calculator.base.converter.Currencies.Website;

/*
 * Date: 14 Apr 2022----------------------------------------------------------- 
 * Time created: 15:03:43---------------------------------------------------  
 * Package: mathaid.calculator.base.converter------------------------------------------------ 
 * Project: LatestProject------------------------------------------------ 
 * File: FxtopDotCom.java------------------------------------------------------ 
 * Class name: FxtopDotCom------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
final class FxtopDotCom extends Website {

	/**
	 * Use <a href="https://jsoup.org/apidocs/">jsoup</a> to connect to the given URL using
	 * <a href="https://learning.postman.com/docs/getting-started/introduction/">Postman API</a>
	 * headers for request, but require the response to be in html.
	 * @param url a URI or URL as a string to connect to
	 * @return a {@link Connection} that can request for non-html type resources
	 */
	protected static org.jsoup.Connection connectViaPostManApi(String url, int timeout, boolean acceptOnlyOtherMimeTypes, String postmanVersion) {
		org.jsoup.Connection connection = org.jsoup.Jsoup.connect(url)
	            .header("User-Agent", postmanVersion == null || postmanVersion.isEmpty() ? "PostmanRuntime/7.29.0" : postmanVersion)
	            .header("Accept", "*/*")
	            .header("Cache-Control", "no-cache")
	            .header("Postman-Token", UUID.randomUUID().toString())
	            .header("Accept-Encoding", "gzip, deflate")
	            .header("Connection", "keep-alive")
				.timeout(timeout)
				.ignoreContentType(acceptOnlyOtherMimeTypes);
		return connection;
	}

	/**
	 * Returns a valid {@code MediumOfExchange} whose 3 digit numeric code corresponds
	 * with the argument or else throws a {@code java.lang.IllegalArgumentException}
	 * if the argument is not supported.
	 * 
	 * @param numericCode the 3 digit numeric code as specified by ISO4217.
	 * @return a {@code MediumOfExchange} object created from the given argument.
	 * @throws RuntimeException specifically an {@code IllegalArgumentException} if
	 *                          the argument is not for a supported currency.
	 */
	protected static MediumOfExchange valueOf(int numericCode) {
		List<MediumOfExchange> l = Currencies.CURRENCIES;
		for (MediumOfExchange moe : l) {
			if (moe.getNumericCode() == numericCode)
				return moe;
		}
		new mathaid.IllegalArgumentException(ExceptionMessage.UNSUPPORTED_CURRENCY);
		/*
		 * For java formality
		 */
		throw new IllegalArgumentException("Unknown currency");
	}

	/*
	 * Date: 16 Aug 2021-----------------------------------------------------------
	 * Time created: 13:56:31--------------------------------------------
	 */
	/**
	 * Returns a valid {@code MediumOfExchange} created with the 3 letter alpha code
	 * that corresponds to the argument or else throws a
	 * {@code java.lang.IllegalArgumentException} if the arguments are not
	 * supported.
	 * 
	 * @param first the first letter in the currency's alpha code.
	 * @param mid   the second letter in the currency's alpha code.
	 * @param last  the last letter in the currency's alpha code.
	 * @return a {@code MediumOfExchange} object created from the given arguments.
	 * @throws RuntimeException specifically an {@code IllegalArgumentException} if
	 *                          the arguments are not for a supported currency.
	 */
	public static MediumOfExchange valueOf(char first, char mid, char last) {
		List<MediumOfExchange> l = Currencies.CURRENCIES;
		String iso4217Code = String.valueOf(new char[] { first, mid, last }).toUpperCase();
		for (MediumOfExchange moe : l) {
			if (moe.getIsoCode().compareTo(iso4217Code) == 0)
				return moe;
		}
		new mathaid.IllegalArgumentException(ExceptionMessage.UNSUPPORTED_CURRENCY);
		/*
		 * For java formality
		 */
		throw new IllegalArgumentException("Unknown currency");
	}

	/*
	 * Date: 16 Aug 2021-----------------------------------------------------------
	 * Time created: 14:00:40--------------------------------------------
	 */
	/**
	 * Returns a valid {@code MediumOfExchange} created with the name of the country or
	 * establishment using such a currency or else throws a
	 * {@code java.lang.IllegalArgumentException} if the argument is not a supported
	 * country/organisation.
	 * 
	 * @param countryName a name of a valid country/organisation.
	 * @return a {@code MediumOfExchange} object created from the corresponding
	 *         country/organisation name.
	 * @see {@linkplain java.util.Currency#getAvailableCurrencies()} for details on
	 *      the syntax of the names.
	 */
	protected static MediumOfExchange valueOf(String countryName) {
		List<MediumOfExchange> l = Currencies.CURRENCIES;
		for (MediumOfExchange moe : l) {
			if (moe.getCountry() != null && moe.getCountry().equals(countryName))
				return moe;
		}
		new mathaid.IllegalArgumentException(ExceptionMessage.UNSUPPORTED_CURRENCY);
		/*
		 * For java formality
		 */
		throw new IllegalArgumentException("Unknown currency");
	}
	/**
	 * The url constant string of the page the converter resides in.
	 */
	private static final String BASE_URL = "https://fxtop.com/en/currency-pair.php?C1={USD}&A=1&C2={EUR}";
	/*
	 * Date: 14 Apr 2022----------------------------------------------------------- 
	 * Time created: 15:03:44--------------------------------------------------- 
	 */
	/**
	 * @param baseUrl
	 * @param incompatibleCurrencies
	 */
	public FxtopDotCom() {
		super(BASE_URL, java.util.Arrays.asList(
				valueOf(970), valueOf(979), valueOf(994), valueOf(940)));
	}
	
	protected void navigateResource(
			org.jsoup.select.Elements elementToTraverse,
			String tag, String attribute, String value,
			java.util.regex.Pattern regex, List<String> out) {
		for(org.jsoup.nodes.Element e: elementToTraverse) {
			if(e.tagName().equals(tag) && e.hasAttr(attribute) && e.attr(attribute).compareTo(value) == 0)
				if (!e.text().isEmpty())
					if(regex.matcher(e.text()).find()) out.add(e.text());
			
			if (e.childrenSize() >= 1)
				navigateResource(e.children(), tag, attribute, value, regex, out);
		}
	}
	
	protected org.jsoup.Connection connect(String url, String fromIsoCode, String toIsoCode) {
		return connectViaPostManApi(url
				.replace("{USD}", fromIsoCode)
				.replace("{EUR}", toIsoCode),
				TIME_OUT, false, null);
	}
	
	protected org.jsoup.nodes.Document updateResource(String url, String fromIsoCode, String toIsoCode) {
		try {
			org.jsoup.nodes.Document resource = connect(url, fromIsoCode, toIsoCode).get();
			return resource;
		} catch (IOException ioe) {
			try {
				if (ioe instanceof MalformedURLException)
					new UnknownURLException(ExceptionMessage.NO_PROTOCOL, (MalformedURLException) ioe);
				else if (ioe instanceof org.jsoup.HttpStatusException) {
					int statusCode = ((org.jsoup.HttpStatusException) ioe).getStatusCode();//connection.response().statusCode();
					new BadHttpResponseException(ExceptionMessage.ERROR_REQUEST, (org.jsoup.HttpStatusException) ioe,
							statusCode);
				} else if (ioe instanceof org.jsoup.UnsupportedMimeTypeException)
					new MimeTypeException(ExceptionMessage.ERROR, (org.jsoup.UnsupportedMimeTypeException) ioe);
				else if (ioe instanceof SocketTimeoutException)
					new ServerTimedOutException(ExceptionMessage.DEADLINE_EXPIRED, (SocketTimeoutException) ioe);
				else if (ioe instanceof UnknownHostException)
					new HostException(ExceptionMessage.INTERNET_NOT_AVAILABLE, (UnknownHostException) ioe);
				new BaseException(ExceptionMessage.EMPTY, ioe);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			ioe.printStackTrace();
		}
		return null;
	}

	/*
	 * Most Recent Date: 14 Apr 2022-----------------------------------------------
	 * Most recent time created: 15:03:44--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @param from
	 * @param x
	 * @param to
	 * @return
	 */
	@Override
	public BigDecimal convert(MediumOfExchange from, BigDecimal x, MediumOfExchange to) {
		// Examples:
//		"1 NGN-Nigeria [Nigerian naira]=0.04 USD-United States [US dollar / $]"
//		"1 JPY-Japan [Japanese yen / ¥]=0.0079 USD-United States [US dollar / $]";
//		"1 XUA-African Development Bank [ADB Unit of Account]=20.2 USD-United States [US dollar / $]";
//		"1 XBT-Bitcoin [Peer-to-peer digital currency / ₿]=40 138.6024 USD-United States [US dollar / $]";
		StringBuilder sb = new StringBuilder();
		sb.append("^1\\s+");//starts with a 1 (which represents the unit) then one or more spaces
		sb.append(from.getIsoCode());//followed by the source (from) iso code
		sb.append("-");//followed by the hyphen
		/*
		 * followed by one or more text (describing the country of origin), one or more spaces
		 * (as many times as possible) or nothing at all. This will guarantee sthat strings such as 'Peer-to-peer'
		 * will scale through
		 */
		sb.append("([A-Za-z]+\\s+)*");
		/*
		 * followed by a '[' character which must be followed the following: one or more text (describing the country of origin), one or more spaces
		 * (as many times as possible) or nothing at all, one or more forward slash(es) '/', zero or more spaces, zero or more symbol(s) and ']'
		 */
		sb.append("\\[(?=(([A-Za-z]+\\s+)*/?\\s*.*\\]))");
		/*
		 * one or more text (describing the country of origin), one or more spaces (as many times as possible) or nothing at all, one or more forward
		 * slash(es) '/', zero or more spaces and zero or more symbol(s) (describing the currency symbol, even though some currencies such the special
		 * drawing rights do not have a symbol)
		 */
		sb.append("([A-Za-z]+\\s+)*/?\\s*.*");
		/*
		 * followed by a ']' (the look behind alternative did not work for unknown reasons hence commented out)
		 */
//			sb.append("(?<=\\[([A-Za-z]+\\s+)*/?\\s*.*)\\]");
		sb.append("\\]");
		sb.append("=");//followed by a '='
		sb.append("\\d+|\\d+(?=.\\d+)");//match decimals i.e the actual rate
		sb.append("\\s+");// yet another one or more spaces
		sb.append(to.getIsoCode());//followed by the to iso code
		sb.append("-");//followed by the hyphen
		sb.append("([A-Za-z]+\\s+)*");//followed by one or more text describing the country of origin
		java.util.regex.Pattern regex =  java.util.regex.Pattern.compile(sb.toString());
		List<String> out = new java.util.ArrayList<>();
		navigateResource(updateResource(getUrl(), from.getIsoCode(), to.getIsoCode()).select(
				"body>table>tbody>tr>td>table>tbody>tr>td>table>tbody>tr>td[align=left] a[name=result]+table>tbody"),
				"td", "align", "center", regex, out);
		if(out.isEmpty())
			return null;
		for(String text : out) {
			if(text.contains("=")) {
				String ans = text.substring(text.indexOf('=') + 1);
				ans = ans.substring(0, ans.indexOf(to.getIsoCode()));
				BigDecimal n = sanitize(ans.replaceAll("\\s|-|,|", ""));
				n = x.multiply(n, DEF);
				
				BigDecimal n2 = n.setScale(to.getFractionalDigits(), DEF.getRoundingMode());
				if(n2.compareTo(BigDecimal.ZERO) == 0)
					return n;
				return n2;
			}
		}
		
		return null;
	}

	
	public BigDecimal historicalConvert(MediumOfExchange from, BigDecimal x, MediumOfExchange to, java.util.Calendar date) {
		
		int dateVal = date.get(java.util.Calendar.DAY_OF_MONTH);
		String rearPart = "&DD=" + (dateVal < 10 ? "0" + dateVal : String.valueOf(dateVal));
		dateVal = date.get(java.util.Calendar.MONTH);
		rearPart = rearPart.concat("&MM=" + (dateVal < 10 ? "0" + dateVal : String.valueOf(dateVal)));
		dateVal = date.get(java.util.Calendar.YEAR);
		rearPart = rearPart.concat("&YYYY=" + (dateVal < 10 ? "0" + dateVal : String.valueOf(dateVal)));
		
		//1 USD=1.015113 EUR
		StringBuilder sb = new StringBuilder("^1\\s+");
		sb.append(from.getIsoCode());
		sb.append("=");
		sb.append("\\d+|\\d+(?=.\\d+)");
		sb.append("\\s+");
		sb.append(to.getIsoCode());
		
		java.util.regex.Pattern regex =  java.util.regex.Pattern.compile(sb.toString());
		List<String> out = new java.util.ArrayList<>();
		navigateResource(updateResource(getUrl()
				.replace("currency-pair", "historical-currency-converter")
				.concat(rearPart), from.getIsoCode(), to.getIsoCode()).select(
				"body>table>tbody>tr>td>table>tbody>tr>td>table>tbody>tr>td[align=left] a[name=result]+table>tbody>tr"),
				"td", "align", "center", regex, out);
		if(out.isEmpty())
			return null;
		for(String text : out) {
			if(text.contains("=")) {
				String ans = text.substring(text.indexOf('=') + 1);
				ans = ans.substring(0, ans.indexOf(to.getIsoCode()));
				BigDecimal n = sanitize(ans.replaceAll("\\s|-|,|", ""));
				n = x.multiply(n, DEF);
				
				BigDecimal n2 = n.setScale(to.getFractionalDigits(), DEF.getRoundingMode());
				if(n2.compareTo(BigDecimal.ZERO) == 0)
					return n;
				return n2;
			}
		}
		
		return null;
	}
//	private org.jsoup.nodes.Document resource;
//	private MediumOfExchange from;
//	private MediumOfExchange to;
//	private java.util.regex.Pattern regex;
//	private StringBuilder sb = new StringBuilder();
}
