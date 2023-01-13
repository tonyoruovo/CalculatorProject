/**
 * 
 */
package mathaid.calculator.base.converter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import mathaid.ExceptionMessage;
import mathaid.calculator.base.converter.Currencies.MediumOfExchange;
import mathaid.calculator.base.converter.Currencies.Website;

/*
 * Date: 13 Apr 2022----------------------------------------------------------- 
 * Time created: 20:59:25---------------------------------------------------  
 * Package: mathaid.calculator.base.converter------------------------------------------------ 
 * Project: LatestProject------------------------------------------------ 
 * File: EuropeanCentralBankFeed.java------------------------------------------------------ 
 * Class name: EuropeanCentralBankFeed------------------------------------------------ 
 */
/**
 * The <a href="https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html">European central bank feed</a>
 * that updates a limited list of currencies on a daily basis (work days excluding weekends). This list is situated within an XML file, which will be the
 * resource in the GET response.
 * 
 * @implNote The response resource gotten from this site comprises of an XML resource which contains a list of all compatible currencies and their rates
 * in euros.
 * @implSpec An internet connection is required for this class to function.
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
final class EuropeanCentralBankFeed extends Website {
	
	/**
	 * Use <a href="https://jsoup.org/apidocs/">jsoup</a> to connect to the given URL using
	 * <a href="https://learning.postman.com/docs/getting-started/introduction/">Postman API</a>
	 * headers for request.
	 * @param url a URI or URL as a string to connect to
	 * @return a {@link Connection} that can request for non-html type resources
	 */
	private static org.jsoup.Connection connectViaPostManApi(String url) {
		org.jsoup.Connection connection = org.jsoup.Jsoup.connect(url)
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
	 * Date: 14 Apr 2022----------------------------------------------------------- 
	 * Time created: 00:18:24--------------------------------------------
	 */
	/**
	 * Creates a set of all the currencies represented in the XML data.
	 * @param xml the XML in string form.
	 * @return a set of all the currencies represented in the XML data.
	 */
	private static HashSet<Currencies.MediumOfExchange> in(String xml) {
		Map<String, String> map = new HashMap<>();
		javax.xml.parsers.DocumentBuilderFactory builderFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
		try {
			javax.xml.parsers.DocumentBuilder builder = builderFactory.newDocumentBuilder();
			java.io.ByteArrayInputStream stream = new java.io.ByteArrayInputStream(xml.getBytes("UTF-8"));
			try {
				org.w3c.dom.Document xmlDocument = builder.parse(stream);
				org.w3c.dom.NodeList list = xmlDocument.getElementsByTagName("Cube");
				for(int i = 0; i < list.getLength(); i++) {
					org.w3c.dom.Node node = list.item(i);
					if(node instanceof org.w3c.dom.Element) {
						org.w3c.dom.Element element = (org.w3c.dom.Element) node;
						
						if(element.hasAttribute("currency") && element.hasAttribute("rate"))
							map.put(element.getAttribute("currency"), element.getAttribute("rate"));
					}
				}
			} catch (org.xml.sax.SAXException e) {
				e.printStackTrace();
			}
		} catch (javax.xml.parsers.ParserConfigurationException e) {
			e.printStackTrace();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		HashSet<Currencies.MediumOfExchange> set = new HashSet<>();
		for(String iso : map.keySet()) {
			for(Currencies.MediumOfExchange currency : Currencies.CURRENCIES) {
				if(currency.getIsoCode().equals(iso))
					set.add(currency);
			}
		}
		return set;
	}

	/**
	 * Creates a set of {@code MediumOfExchange} consisting of all the currencies
	 * supported in the mathaid api that is absent in the argument set.
	 * 
	 * @param compatibleCurrencies the set of {@code MediumOfExchange} objects
	 * that this class can support.
	 * @return a set containing every object not in the argument set.
	 */
	private static Set<Currencies.MediumOfExchange> incompatibleCurrencies(Set<Currencies.MediumOfExchange> compatibleCurrencies){
		Set<Currencies.MediumOfExchange> set = new HashSet<>(Currencies.CURRENCIES);
		set.removeAll(compatibleCurrencies);
		return set;
	}
	
	/**
	 * Returns a list of all the currencies that are not supported
	 * by this site. This is used to prevent errors.
	 * 
	 * @return a list of {@code MediumOfExchange} representing all the
	 * currencies that this site does not support.
	 */
	private static List<MediumOfExchange> incompatibleCurrencies(){
		Set<Currencies.MediumOfExchange> set = in(XML);
		set.add(EURO);
		set = incompatibleCurrencies(set);
		return new ArrayList<>(set);
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
	private static MediumOfExchange valueOf(int numericCode) {
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
	
	/**
	 * The url constant string of the page the converter resides in.
	 */
	private static final String BASE_URL = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
	/**
	 * A snapshot of the prototype response from {@link BASE_URL} that contains all the compatible currencies
	 * in XML format.
	 */
	private static final String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
			"<gesmes:Envelope xmlns:gesmes=\"http://www.gesmes.org/xml/2002-08-01\" xmlns=\"http://www.ecb.int/vocabulary/2002-08-01/eurofxref\">\r\n" + 
			"    <gesmes:subject>Reference rates</gesmes:subject>\r\n" + 
			"    <gesmes:Sender>\r\n" + 
			"        <gesmes:name>European Central Bank</gesmes:name>\r\n" + 
			"    </gesmes:Sender>\r\n" + 
			"    <Cube>\r\n" + 
			"        <Cube time='2022-04-13'>\r\n" + 
			"            <Cube currency='USD' rate='1.0826'/>\r\n" + 
			"            <Cube currency='JPY' rate='136.26'/>\r\n" + 
			"            <Cube currency='BGN' rate='1.9558'/>\r\n" + 
			"            <Cube currency='CZK' rate='24.450'/>\r\n" + 
			"            <Cube currency='DKK' rate='7.4377'/>\r\n" + 
			"            <Cube currency='GBP' rate='0.83280'/>\r\n" + 
			"            <Cube currency='HUF' rate='378.45'/>\r\n" + 
			"            <Cube currency='PLN' rate='4.6453'/>\r\n" + 
			"            <Cube currency='RON' rate='4.9415'/>\r\n" + 
			"            <Cube currency='SEK' rate='10.3323'/>\r\n" + 
			"            <Cube currency='CHF' rate='1.0116'/>\r\n" + 
			"            <Cube currency='ISK' rate='140.20'/>\r\n" + 
			"            <Cube currency='NOK' rate='9.5693'/>\r\n" + 
			"            <Cube currency='HRK' rate='7.5538'/>\r\n" + 
			"            <Cube currency='TRY' rate='15.7992'/>\r\n" + 
			"            <Cube currency='AUD' rate='1.4603'/>\r\n" + 
			"            <Cube currency='BRL' rate='5.0449'/>\r\n" + 
			"            <Cube currency='CAD' rate='1.3700'/>\r\n" + 
			"            <Cube currency='CNY' rate='6.8939'/>\r\n" + 
			"            <Cube currency='HKD' rate='8.4867'/>\r\n" + 
			"            <Cube currency='IDR' rate='15549.10'/>\r\n" + 
			"            <Cube currency='ILS' rate='3.4782'/>\r\n" + 
			"            <Cube currency='INR' rate='82.4780'/>\r\n" + 
			"            <Cube currency='KRW' rate='1328.47'/>\r\n" + 
			"            <Cube currency='MXN' rate='21.4170'/>\r\n" + 
			"            <Cube currency='MYR' rate='4.5799'/>\r\n" + 
			"            <Cube currency='NZD' rate='1.5991'/>\r\n" + 
			"            <Cube currency='PHP' rate='56.446'/>\r\n" + 
			"            <Cube currency='SGD' rate='1.4769'/>\r\n" + 
			"            <Cube currency='THB' rate='36.305'/>\r\n" + 
			"            <Cube currency='ZAR' rate='15.6820'/></Cube>\r\n" + 
			"    </Cube>\r\n" + 
			"</gesmes:Envelope>";

	/**
	 * The Euro currency
	 */
	private static final MediumOfExchange EURO = valueOf(978);
	/*
	 * Date: 13 Apr 2022----------------------------------------------------------- 
	 * Time created: 20:59:25--------------------------------------------------- 
	 */
	/**
	 * Constructs a {@code EuropeanCentralBankFeed} object, setting all the relevant
	 * available currencies.
	 */
	public EuropeanCentralBankFeed() {
		super(BASE_URL, incompatibleCurrencies());
	}
	
	/*
	 * Date: 14 Apr 2022----------------------------------------------------------- 
	 * Time created: 01:26:05--------------------------------------------
	 */
	/**
	 * Connects the mathaid api to the given url specified by {@link #getUrl()},
	 * which by consequence is the European central bank feed.
	 * @return a {@code HashMap} using the available currencies' iso code as keys
	 * and their corresponding rate as the value. The rate is the euro rate for
	 * the given key.
	 */
	private Map<String, String> connectToFeed(){
		org.jsoup.Connection connection = connectViaPostManApi(getUrl());
		Map<String, String> map = new HashMap<>();
		try {
			String xml = connection.get().toString();
			javax.xml.parsers.DocumentBuilderFactory builderFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
			try {
				javax.xml.parsers.DocumentBuilder builder = builderFactory.newDocumentBuilder();
				java.io.ByteArrayInputStream stream = new java.io.ByteArrayInputStream(xml.getBytes("UTF-8"));
				try {
					org.w3c.dom.Document xmlDocument = builder.parse(stream);
					org.w3c.dom.NodeList list = xmlDocument.getElementsByTagName("Cube");
					for(int i = 0; i < list.getLength(); i++) {
						org.w3c.dom.Node node = list.item(i);
						if(node instanceof org.w3c.dom.Element) {
							org.w3c.dom.Element element = (org.w3c.dom.Element) node;
							
							if(element.hasAttribute("currency") && element.hasAttribute("rate"))
								map.put(element.getAttribute("currency"), element.getAttribute("rate"));
						}
					}
				} catch (org.xml.sax.SAXException e) {
					e.printStackTrace();
				}
			} catch (javax.xml.parsers.ParserConfigurationException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	/*
	 * Date: 14 Apr 2022----------------------------------------------------------- 
	 * Time created: 02:18:14--------------------------------------------
	 */
	/**
	 * Return a {@code BigDecimal} value which has the effect of convert from X
	 * currency to X currency.
	 * 
	 * @param type the type of currency. This is used for final truncation of the result.
	 * @param units the num of units of currency.
	 * @return return 1 * units
	 */
	private BigDecimal same(MediumOfExchange type, BigDecimal units) {
		BigDecimal n = units;
		
		BigDecimal n2 = n.setScale(type.getFractionalDigits(), DEF.getRoundingMode());
		if(n2.compareTo(BigDecimal.ZERO) == 0)
			return n;
		return n2;
	}
	
	/*
	 * Date: 14 Apr 2022----------------------------------------------------------- 
	 * Time created: 02:22:58--------------------------------------------
	 */
	/**
	 * Converts the given a single unit of Euro to {@code MediumOfExchange}
	 * @param currency the currency to which the euro is to be converted to
	 * @return the argument converted from euro
	 * @throws NumberFormatException if the specified currency is not
	 * available
	 */
	private BigDecimal getEuroUnitFor(MediumOfExchange currency) throws NumberFormatException {
		if(currency.compareTo(EURO) == 0) return BigDecimal.ONE;
		Map<String, String> feed = connectToFeed();
		if(feed.isEmpty())
			return null;
		return new BigDecimal(feed.get(currency.getIsoCode()));
	}
	
	/*
	 * Date: 14 Apr 2022----------------------------------------------------------- 
	 * Time created: 02:25:22--------------------------------------------
	 */
	/**
	 * Converts the given units of Euros to the specified {@code MediumOfExchange}
	 * 
	 * @param units the num of units of currency by which the conversion is scaled.
	 * @param to the currency to which the euro is to be converted to
	 * @return the argument converted from euros scaled by the given units
	 * @throws NumberFormatException if the specified currency is not
	 * available for this conversion
	 */
	private BigDecimal eur2another(BigDecimal units, MediumOfExchange to) {
		BigDecimal n = getEuroUnitFor(to);
		n = units.multiply(n, DEF);
		
		BigDecimal n2 = n.setScale(to.getFractionalDigits(), DEF.getRoundingMode());
		if(n2.compareTo(BigDecimal.ZERO) == 0)
			return n;
		return n2;
	}
	
	/*
	 * Date: 14 Apr 2022----------------------------------------------------------- 
	 * Time created: 02:34:08--------------------------------------------
	 */
	/**
	 * Converts the first argument to the third argument using the given units as scale.
	 * @param from the currency from which the conversion is made
	 * @param units the num of unit(s) by which the conversion is scaled
	 * @param to the currency to which the first argument is to be converted to
	 * @return the a {@code BigDecimal} converted from the first argument to the third argument using the given units as a scale
	 * @throws NumberFormatException if either the first or third argument is not
	 * available for this conversion
	 */
	private BigDecimal another2another(MediumOfExchange from, BigDecimal units, MediumOfExchange to) {
		BigDecimal n = BigDecimal.ONE.divide(getEuroUnitFor(from), DEF);
		BigDecimal n1 = BigDecimal.ONE.divide(getEuroUnitFor(to), DEF);
		n = n.divide(n1, DEF);
		n = units.multiply(n, DEF);

		BigDecimal n2 = n.setScale(to.getFractionalDigits(), DEF.getRoundingMode());
		if(n2.compareTo(BigDecimal.ZERO) == 0)
			return n;
		return n2;
	}
	
	/*
	 * Most Recent Date: 13 Apr 2022-----------------------------------------------
	 * Most recent time created: 20:59:25--------------------------------------
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
		if(from.compareTo(to) == 0)
			return same(to, x);
		if(from.compareTo(EURO) == 0)
			return eur2another(x, to);

		return another2another(from, x, to);
	}

}
