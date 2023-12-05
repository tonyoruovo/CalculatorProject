/**
 * 
 */
package mathaid.calculator.base.converter;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import mathaid.BaseException;
import mathaid.ExceptionMessage;
import mathaid.calculator.base.converter.Currencies.MediumOfExchange;
import mathaid.calculator.base.converter.Currencies.Website;

/*
 * Date: 12 Apr 2022----------------------------------------------------------- 
 * Time created: 21:00:07---------------------------------------------------  
 * Package: mathaid.calculator.base.converter------------------------------------------------ 
 * Project: LatestProject------------------------------------------------ 
 * File: FawazAhmedCDNRepo.java------------------------------------------------------ 
 * Class name: FawazAhmedCDNRepo------------------------------------------------ 
 */
/**
 * A class that provides concrete implementation for the abstract class
 * {@code Website}. The aim of this class is request a json file from a
 * <a href="https://cdn.jsdelivr.net/gh/fawazahmed0">CDN repo</a> using
 * GET method. The URI has a few parameters it requires, and it's
 * structure includes:
 * <pre><code>
 * https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@{apiVersion}/{date}/{endpoint}.json
 * </code>
 * </pre>
 * <h2>apiVersion</h2>
 * <p>
 * Not much to be done here just use the value {@code 1} and you will be
 * fine. Also check his <a href="https://github.com/fawazahmed0/currency-api">
 * Github page</a> for the latest version.
 * </p>
 * <h2>date</h2>
 * <p>
 * This parameter specifies the date from which the conversion data is to be taken
 * either the latest (using the value {@code latest}), or a date in the format
 * {@code YYYY-MM-DD}. The latter will provide the closing rate for that day,
 * however, any value beyond {@code 2020-11-22} will not be regarded as valid,
 * hence a valid resource may not be sent.
 * </p>
 * <h2>endpoint</h2>
 * <p>
 * This depends on whether the user decides to use one of 3 formats for the
 * request:
 * <ul>
 * <li>{@code /currencies}<br> will return a list of all supported currencies.
 * The format is:
 * <pre><code>
 * {
 * 	"currency code": "currency name"
 * }
 * </code></pre>
 * </li>
 * <li><code>/currencies/&#123;currencyCode&#125;</code><br> will return a list
 * of all supported currencies and their converted values (using a unit of 1)
 * using {@code currencyCode} ({@code currencyCode} represents the iso code
 * that currency) as base. For example:
 * <code>https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/usd.json</code>
 * may have a response json such as:
 * <pre>
 * {
 * 	"date": "2022-04-12",
 * 	"usd": {
 *      		"1inch": 0.681293,
 *      		"ada": 1.07323,
 *      		"aed": 3.673099,
 *      		// rest of the code removed for brevity ...
 * 		}
 * }
 * </pre>
 * The above values uses {@code usd} as a base currency to return a list of all
 * the supported currency and their values.<br><br>
 * </li>
 * <li><code>/currencies/{currencyCode1}/{currencyCode2}</code><br> will return
 * a json object containing the date and the converted currency specified by the
 * argument {@code currencyCode2}, that was converted from the specified
 * {@code currencyCode1}. For example, to convert from us dollar to euros use
 * the URL: <code>https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/usd/eur.json</code>
 * The response body is:
 * <pre>
 * {
 *  "date": "2022-04-12",
 *  "eur": 0.91906
 * }
 * </pre>
 * </li>
 * </ul>
 * </p>
 * <p>The response from a valid request is a resource in json format.
 * it follows and all values use the scale of 1 unit. That is, if one is
 * converting from let's say {@code usd} to let's say {@code jpy}, the
 * value returned under {@code jpy} is equal to 1USD.<br>
 * There is also a minified (as per javascript traditions on CDN) version
 * of each request which takes the format of:
 * <code>
 * https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@{apiVersion}/{date}/{endpoint}.min.json
 * </code>. The difference between a response of the usual request and this
 * one is that the json response does not contain line breaks and all the
 * code is in a single line.
 * </p>
 * <p>
 * <b>NOTE:</b><br>
 * Special thanks to <a href="https://github.com/fawazahmed0/currency-api">
 * Fawaz Ahmed</a> for the CDN repo. He/She is the authour of the repo.
 * </p>
 * @implSpec An internet connection is required for this class to function.
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
final class FawazAhmedCDNRepo extends Website {

	/*
	 * Date: 13 Apr 2022----------------------------------------------------------- 
	 * Time created: 17:16:17--------------------------------------------
	 */
	/**
	 * Use <a href="https://jsoup.org/apidocs/">jsoup</a> to connect to the given URL using
	 * <a href="https://learning.postman.com/docs/getting-started/introduction/">Postman API</a>
	 * headers for request.
	 * @param url a URI or URL as a string to connect to
	 * @return a {@link Connection} that can request for non-html type resources
	 */
	private static Connection connectViaPostManApi(String url) {
		Connection connection = Jsoup.connect(url)
//				.userAgent(USER_AGENT)
	            .header("User-Agent", "PostmanRuntime/7.29.0")
	            .header("Accept", "*/*")
	            .header("Cache-Control", "no-cache")
	            .header("Postman-Token", UUID.randomUUID().toString())
	            .header("Accept-Encoding", "gzip, deflate")
	            .header("Connection", "keep-alive")
				.timeout(TIME_OUT)
				.ignoreContentType(true);
		return connection;
	}
	
	/*
	 * Date: 13 Apr 2022----------------------------------------------------------- 
	 * Time created: 17:16:17--------------------------------------------
	 */
	/**
	 * Recursively traverse the given {@link Element html} and returns the content
	 * of the "&lt;body&gt;" element within as a string.
	 * @param html the {@code Element} to traverse.
	 * @return the content of the of the "&lt;body&gt;" as a string
	 */
	private static String getBodyContent(Element html) {
		if(html.tagName().equals("body")) return html.text();
		
		for(Element e : html.children()) {
			if(e.text().isEmpty()) continue;
			
			return getBodyContent(e);
		}
		return null;
	}
	
	/*
	 * Date: 13 Apr 2022----------------------------------------------------------- 
	 * Time created: 19:50:32--------------------------------------------
	 */
	/**
	 * Returns a list of all the currencies that are not supported
	 * by this site. This is used to prevent errors.
	 * 
	 * @return a list of {@code MediumOfExchange} representing all the
	 * currencies that this site does not support.
	 */
	static final List<MediumOfExchange> getIncompatibleCurrencies2() {
		String url = BASE_URL
				.replace("{apiVersion}", DEFAULT_API_VERSION)
				.replace("{date}", MOST_RECENT)
				.replace("{endpoint}", ALL_AVAILABLE_CURRENCIES);
		Connection connection = connectViaPostManApi(url);
		String json = null;
		try {
			Document response = connection.get();
			json = /* response.child(0).child(1).text(); */getBodyContent(response);
		} catch (IOException ioe) {
			try {
				if (ioe instanceof MalformedURLException)
					new UnknownURLException(ExceptionMessage.NO_PROTOCOL, (MalformedURLException) ioe);
				else if (ioe instanceof HttpStatusException) {
					int statusCode = ((HttpStatusException) ioe).getStatusCode();//connection.response().statusCode();
					new BadHttpResponseException(ExceptionMessage.ERROR_REQUEST, (HttpStatusException) ioe,
							statusCode);
				} else if (ioe instanceof UnsupportedMimeTypeException)
					new MimeTypeException(ExceptionMessage.ERROR, (UnsupportedMimeTypeException) ioe);
				else if (ioe instanceof SocketTimeoutException)
					new ServerTimedOutException(ExceptionMessage.DEADLINE_EXPIRED, (SocketTimeoutException) ioe);
				else if (ioe instanceof UnknownHostException)
					new HostException(ExceptionMessage.INTERNET_NOT_AVAILABLE, (UnknownHostException) ioe);
				new BaseException(ExceptionMessage.EMPTY, ioe);
			} catch (Exception e) {
				return null;
			}
		}
		Map<String, String> dict = parseJsonString(json);
		List<MediumOfExchange> l = new ArrayList<>();
		if(dict != null)
			for(MediumOfExchange currency : Currencies.CURRENCIES)
				if(!dict.containsKey(currency.getIsoCode())) l.add(currency);
		l.remove(FxtopDotCom.valueOf('X', 'B', 'T'));
		return l;
	}

	/*
	 * Date: 13 Apr 2022----------------------------------------------------------- 
	 * Time created: 20:19:21--------------------------------------------
	 */
	/**
	 * Parses the given string (which is expected to be in JSON format)
	 * and returns a corresponding map of keys and values.
	 * 
	 * @param json the string to be parsed. This is not expected to contain complex objects
	 * because this is not a formal JSON parser.
	 * @return a {@code Map} of string keys and string values.
	 */
	private static Map<String, String> parseJsonString(String json) {
		if(json == null) return null;
		int i = 0;
		char c;
		StringBuilder sb = new StringBuilder();
		Map<String, String> map = new HashMap<>();
		while(i < json.length() && (c = json.charAt(i++)) != '}') {
			if(c == '{') continue;
			if(c == ',') {
				String[] keyAndValue = sb.toString().replaceAll("\"", "").split(":");
				map.put(keyAndValue[0].trim().toUpperCase(), keyAndValue[1].trim());
				sb.replace(0, sb.length(), "");
				continue;
			}
			sb.append(c);
		}
		String[] keyAndValue = sb.toString().replaceAll("\"", "").split(":");
		map.put(keyAndValue[0].trim().toUpperCase(), keyAndValue[1].trim());
		return map;
	}

	/**
	 * The url constant string of the page the converter resides in.
	 */
	private static final String BASE_URL = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@{apiVersion}/{date}/{endpoint}.json";
	/**
	 * A fallback url constant for conversion pairs that may fail in
	 * the basic url. The json returned by this url is minified
	 * (see this class' docs).
	 */
	private static final String FALLBACK_URL = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@{apiVersion}/{date}/{endpoint}.min.json";
	/**
	 * The default api version as a string constant.
	 */
	private static final String DEFAULT_API_VERSION = 1 + "";
	/**
	 * A constant representing the most recent update to currency values.
	 */
	private static final String MOST_RECENT = "latest";
	/**
	 * A constant representing all currencies used for returning
	 * lists of currency. Please see this class' docs.
	 */
	private static final String ALL_AVAILABLE_CURRENCIES = "currencies";
	
	/*
	 * Date: 13 Apr 2022----------------------------------------------------------- 
	 * Time created: 20:34:41---------------------------------------------------  
	 * Package: mathaid.calculator.base.converter------------------------------------------------ 
	 * Project: LatestProject------------------------------------------------ 
	 * File: FawazAhmedCDNRepo.java------------------------------------------------------ 
	 * Class name: ResponseObject------------------------------------------------ 
	 */
	/**
	 * A helper class that maps onto the response json within {@link FawazAhmedCDNRepo#convert(MediumOfExchange, BigDecimal, MediumOfExchange)}.
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 */
	private static class ResponseObject {
//		Calendar date;
		MediumOfExchange currency;
		BigDecimal value;
	}

	/*
	 * Date: 13 Apr 2022----------------------------------------------------------- 
	 * Time created: 00:18:48--------------------------------------------------- 
	 */
	/**
	 * Constructor that specifies a date and an api version
	 * @param date A {@link Calendar} object that represents
	 * the date from which the collect currency info. This is
	 * useful for code that may historical currency value data.
	 * <p>Any value that predates 2020-11-22 will cause an exception to be thrown</p>
	 * @param apiVersion the version of the api to use. If
	 * unsure use one of the default constructor.
	 * @throws IndexOutOfBoundsException if the date argument
	 * precedes 2020-11-22
	 */
	public FawazAhmedCDNRepo(Calendar date, String apiVersion) throws IndexOutOfBoundsException {
		super(BASE_URL, /*getIncompatibleCurrencies2()*/
				java.util.Arrays.asList(FxtopDotCom.valueOf(965), FxtopDotCom.valueOf(970), FxtopDotCom.valueOf(979), FxtopDotCom.valueOf(994)));
		if(date != null && date.compareTo(new GregorianCalendar(2020, 11, 22)) < 0)
			new mathaid.IndexBeyondLimitException(
					ExceptionMessage.INDEX_OUT_BOUNDS,
					new IndexOutOfBoundsException(), date, 
					new GregorianCalendar(2020, 11, 22));
		this.date = date;
		this.apiVersion = apiVersion;
	}
	/*
	 * Date: 13 Apr 2022----------------------------------------------------------- 
	 * Time created: 00:16:53--------------------------------------------------- 
	 */
	/**
	 * Constructs this object by specifying a particular date from which data
	 * is to be pulled from and using a default api version of 1.
	 * 
	 * @param date A {@link Calendar} object that represents
	 * the date from which the collect currency info. This is
	 * useful for code that may historical currency value data.
	 * @throws IndexOutOfBoundsException if the date argument
	 * precedes 2020-11-22
	 */
	public FawazAhmedCDNRepo(Calendar date) throws IndexOutOfBoundsException {
		this(date, DEFAULT_API_VERSION);
	}
	
	/*
	 * Date: 13 Apr 2022----------------------------------------------------------- 
	 * Time created: 00:19:15--------------------------------------------------- 
	 */
	/**
	 * Constructs this object by using the latest date from which data
	 * is to be pulled from and using a default api version of 1.
	 */
	public FawazAhmedCDNRepo() {
		this(null);
	}

	/*
	 * Most Recent Date: 12 Apr 2022-----------------------------------------------
	 * Most recent time created: 21:00:07--------------------------------------
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
		String src = from.getIsoCode();
		String dst = to.getIsoCode();
		if(src.compareTo("XBT") == 0)
			src = "BTC";
		if(dst.compareTo("XBT") == 0)
			dst = "BTC";
		String url = getUrl()
				.replace("{apiVersion}", apiVersion)
				.replace("{date}", date == null ? MOST_RECENT : String.format("%1$tY-%1$tm-%1$td", date))
				.replace("{endpoint}", ALL_AVAILABLE_CURRENCIES + "/" + src.toLowerCase() + "/" + dst.toLowerCase());
		Connection connection = connectViaPostManApi(url);
		String json = null;
		Map<String, String> dict = null;
		try {
			Document response = connection.get();
			json = getBodyContent(response);
			dict = parseJsonString(json);
		}catch(IOException ioe) {
			ioe.printStackTrace();
			url = FALLBACK_URL
				.replace("{apiVersion}", apiVersion)
				.replace("{date}", date == null ? MOST_RECENT : String.format("%1$tY-%1$tm-%1$td", date))
				.replace("{endpoint}", ALL_AVAILABLE_CURRENCIES + "/" + src.toLowerCase() + "/" + dst.toLowerCase());
			connection = connectViaPostManApi(url);
			json = null;
			try {
				Document response = connection.get();
				json = getBodyContent(response);
				dict = parseJsonString(json);
			}catch(IOException ex) {
				ex.printStackTrace();
				try {
					new BaseException(ExceptionMessage.EMPTY, ex);
				} catch (Exception e) {
					return null;
				}
			}
		}
		
		if(dict != null) {
			ResponseObject ro = new ResponseObject();
//			List<Integer> date = Arrays.stream(dict.get("date").split("-"))
//					.map(a -> Integer.parseInt(a)).collect(Collectors.toList());
//			ro.date = new GregorianCalendar(date.get(0), date.get(1), date.get(2));
			for(MediumOfExchange c : Currencies.CURRENCIES)
				if(dict.containsKey(c.getIsoCode()))
					ro.currency = c;
			ro.value = new BigDecimal(dict.get(ro.currency.getIsoCode()));

			BigDecimal bd = x.multiply(ro.value, DEF);
			BigDecimal bd2 = bd.setScale(ro.currency.getFractionalDigits(),DEF.getRoundingMode());
			if(bd2.compareTo(BigDecimal.ZERO) == 0)
				return bd;
			return bd2;
		}
		return null;
	}
	
	/**
	 * The date from which currency data is to be gotten from
	 */
	private final Calendar date;
	/**
	 * The api version to use during requests
	 */
	private final String apiVersion;

}
