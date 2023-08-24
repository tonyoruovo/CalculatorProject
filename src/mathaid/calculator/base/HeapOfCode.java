/*
 * Date: 21 Jun 2023 -----------------------------------------------------------
 * Time created: 06:55:55 ---------------------------------------------------
 */
/**
 * 
 */
package mathaid.calculator.base;

import static java.lang.System.err;
import static java.lang.System.in;
import static java.lang.System.out;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import static mathaid.calculator.base.util.Utility.d;
import static mathaid.calculator.base.util.Utility.requireNonNullElse;
import static mathaid.calculator.base.util.Utility.string;

import mathaid.calculator.base.util.Utility;
import mathaid.js.JSArray;
import mathaid.js.JSMemberName;
import mathaid.js.JSNull;
import mathaid.js.JSNumber;
import mathaid.js.JSObject;
import mathaid.js.JSString;
import mathaid.js.JSValue;

/*
 * Date: 21 Jun 2023 -----------------------------------------------------------
 * Time created: 06:55:55 ---------------------------------------------------
 * Package: mathaid.calculator.base ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: HeapOfCode.java ------------------------------------------------------
 * Class name: HeapOfCode ------------------------------------------------
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class HeapOfCode {

	public static String bin(String s, int div, int len) {
		out.println(err);
		out.println(in);
		if (len < 1)
			len = s.length();
		StringBuilder sb = new StringBuilder(len + (len / div));
		s = String.format("%1$s%2$s", Utility.string('0', Math.max(len - s.length(), 0)), s);
		int j = 0;
		for (int i = s.length() - 1; i >= 0; i--) {
			sb.insert(0, s.charAt(i));
			if (j != 0 && (j + 1) % div == 0 && j != len - 1)
				sb.insert(0, ' ');
			j += 1;
			if (j + 1 > len)
				break;
		}
		return sb.toString();
	}

	static String iso3FromName(String n) {
		var e = getTag("https://en.wikipedia.org/wiki/List_of_ISO_3166_country_codes",
				"#mw-content-text > div.mw-parser-output > table > tbody");
		var children = e.children();
		for (var c : children) {
			try {
				if (c.text().toLowerCase().contains(n.toLowerCase()))
					return c.child(4).text().trim();
			} catch (Exception ex) {
				err.print(n + ": ");
				ex.printStackTrace();
			}
		}
		return null;
		/*
		 * Locale[] locales = Locale.getAvailableLocales(); for (int i = 0; i <
		 * locales.length; i++) { Locale locale = locales[i]; try {
		 * if(locale.getDisplayCountry().toLowerCase().contains(n.toLowerCase())) return
		 * locale.getISO3Country(); } catch (MissingResourceException e) { continue; } }
		 * return null;
		 */
	}

	static String iso2FromName(String n) {
		var e = getTag("https://en.wikipedia.org/wiki/List_of_ISO_3166_country_codes",
				"#mw-content-text > div.mw-parser-output > table > tbody");
		var children = e.children();
		for (var c : children) {
			try {
				if (c.text().toLowerCase().contains(n.toLowerCase()))
					return c.child(3).text().trim();
			} catch (Exception ex) {
				err.print(n + ": ");
				ex.printStackTrace();
			}
		}
		return null;
		/*
		 * Locale[] locales = Locale.getAvailableLocales(); for (int i = 0; i <
		 * locales.length; i++) { Locale locale = locales[i]; try {
		 * if(locale.getDisplayCountry().toLowerCase().contains(n.toLowerCase())) return
		 * locale.getCountry(); } catch (Exception e) { continue; } } return null;
		 */
	}

	static String literalUnicode(String s) {
		StringBuilder sb = new StringBuilder(s.length() * 5);
		for (char c : s.toCharArray()) {
			sb.append("\\u");
			String hex = Integer.toHexString(c);
			sb.append(string('0', 4 - hex.length()));
			sb.append(hex);
		}
		return sb.toString();
	}

	private static String getLocalisedSymbol(String iso3) {
		Locale[] locales = Locale.getAvailableLocales();
		for (int i = 0; i < locales.length; i++) {
			try {
				if (locales[i].getISO3Country().equalsIgnoreCase(iso3)) {
					Currency c = Currency.getInstance(locales[i]);
					String[] sy = null;
					try {
						sy = locales[i].toLanguageTag().split("-");
					} catch (Exception e) {
						return c.getSymbol();
					}
					return c.getSymbol(new Locale(sy[0], sy[1]));
				}
			} catch (Exception e) {
				continue;
			}
		}
		return null;
	}

	protected static org.jsoup.Connection connectViaPostManApi(String url, int timeout,
			boolean acceptOnlyOtherMimeTypes, String postmanVersion) {
		org.jsoup.Connection connection = org.jsoup.Jsoup.connect(url)
				.header("User-Agent",
						postmanVersion == null || postmanVersion.isEmpty() ? "PostmanRuntime/7.29.0" : postmanVersion)
				.header("Accept", "*/*").header("Cache-Control", "no-cache")
				.header("Postman-Token", UUID.randomUUID().toString()).header("Accept-Encoding", "gzip, deflate")
				.header("Connection", "keep-alive").timeout(timeout).ignoreContentType(acceptOnlyOtherMimeTypes);
		return connection;
	}

	static org.jsoup.nodes.Element element = null;

	static org.jsoup.nodes.Element getTag(String url, String selector) {
		if (element != null)
			return element;
		var x = connectViaPostManApi(url, 10000, false, null);
		try {
			return (element = x.get().selectFirst(selector));
		} catch (IOException e) {
			return null;
		}
	}

	public static void main(String[] args) {
//		Locale.getISOCountries(IsoCountryCode.PART1_ALPHA3).stream().sorted().forEach(x -> out.println(x));
		/*
		 * List<JSObject> l = new ArrayList<>(); List<String> parsed = new
		 * ArrayList<>(); Arrays.stream(Locale.getAvailableLocales()) .sorted((x, y) ->
		 * x.getDisplayCountry().compareTo(y.getDisplayCountry())) .forEach(x -> {
		 * Currency c = null; try { c = Currency.getInstance(x);
		 * if(parsed.contains(c.getCurrencyCode())) return; else
		 * parsed.add(c.getCurrencyCode()); NavigableMap<JSMemberName, JSValue<?>>
		 * object = new TreeMap<>(); object.put(new JSMemberName("display-name", true),
		 * new JSString(c.getDisplayName())); object.put(new JSMemberName("iso", true),
		 * new JSString(c.getCurrencyCode())); object.put(new JSMemberName("code",
		 * true), new JSNumber(c.getNumericCode())); object.put(new
		 * JSMemberName("fraction-digits"), new JSNumber(c.getDefaultFractionDigits()));
		 * String symbol = c.getSymbol(); try { String[] loc =
		 * x.toLanguageTag().split("-"); symbol = c.getSymbol(new Locale(loc[0],
		 * loc[1])); } catch(Exception e) {} object.put(new JSMemberName("symbol",
		 * true), new JSString(literalUnicode(symbol))); // NavigableMap<JSMemberName,
		 * JSValue<?>> replacement = new TreeMap<JSMemberName,
		 * JSValue<?>>(JSMemberName::compareTo); // replacement.putAll(object);
		 * l.add(new JSObject(object)); } catch (IllegalArgumentException e) {
		 * err.println("the locale: " + x + " could not be parsed."); } });
		 * 
		 * File f = new File("./test/currencies-" + System.currentTimeMillis() +
		 * ".json"); PrintStream ps = null; try { ps = new PrintStream(f); new
		 * JSArray<>(l).parseToScript(ps, 1); } catch (FileNotFoundException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } finally { if(ps !=
		 * null) ps.close(); } out.println("done!");
		 */

		List<JSObject> a = new ArrayList<>();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		PrintStream ps = null;
		try {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			BufferedInputStream input = null;
			try {
				input = new BufferedInputStream(new FileInputStream("test\\list-1\\list-1.xml"));
				try {
					Document xmlDocument = builder.parse(input);
					xmlDocument.getDocumentElement().normalize();
					NodeList list = xmlDocument.getElementsByTagName("CcyNtry");
					for (int i = 0; i < list.getLength(); i++) {
						Node node = list.item(i);
						NodeList children = node.getChildNodes();
						NavigableMap<JSMemberName, JSValue<?>> obj = new TreeMap<>();
						Node countryName = null;
						Node ccyIsoCode = null;
						Node ccyNumCode = null;
						Node ccyFracUnit = null;
						for (int j = 0; j < children.getLength(); j++) {
							Node current = children.item(j);
							if (current.getNodeType() == Node.ELEMENT_NODE) {
								switch (current.getNodeName()) {
								case "Ccy": {
									ccyIsoCode = current;
									break;
								}
								case "CtryNm": {
									countryName = current;
									break;
								}
								case "CcyNbr": {
									ccyNumCode = current;
									break;
								}
								case "CcyMnrUnts": {
									ccyFracUnit = current;
									break;
								}
								default:
									break;
								}// end switch
							} // end if
						} // end for-loop
						if (ccyIsoCode == null)
							continue;
						String iso2 = requireNonNullElse(iso2FromName(countryName.getTextContent()),
								ccyIsoCode.getTextContent()).toLowerCase();
						String iso3 = requireNonNullElse(iso3FromName(countryName.getTextContent()),
								ccyNumCode.getTextContent()).toLowerCase();
						obj.put(new JSMemberName("country", true), new JSString(iso2));
						obj.put(new JSMemberName("country-code", true), new JSString(iso3));
						obj.put(new JSMemberName("flag", true), new JSString(new StringBuilder(17).append("./flags/")
								.append(iso2).append("/").append(iso2).append(".svg").toString()));
						NavigableMap<JSMemberName, JSValue<?>> info = new TreeMap<>();
						info.put(new JSMemberName("iso", true),
								new JSString(ccyIsoCode.getTextContent().toLowerCase()));
						info.put(new JSMemberName("numeric-code", true),
								new JSNumber(Long.parseLong(ccyNumCode.getTextContent())));
						info.put(new JSMemberName("symbol", true),
								new JSString(new StringBuilder(30).append("./symbols/").append(iso2).append("/")
										.append(iso2).append("-").append(iso3).append("-filled-1.svg").toString()));
						info.put(new JSMemberName("currency-type", true), new JSString("LEGAL_TENDER"));
						var ls = iso2.length() == 2
								? new JSString(literalUnicode(requireNonNullElse(getLocalisedSymbol(iso3), "")))
								: new JSNull();
						if (ls.getType().getName().equals("null") || ((JSString) ls).get().length() == 0) {
							if (ccyIsoCode.getTextContent().equalsIgnoreCase("eur"))
								info.put(new JSMemberName("unicode", true), new JSString("\\u20ac"));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("irr"))
								info.put(new JSMemberName("unicode", true), new JSString("\\ufdfc"));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("gbp"))
								info.put(new JSMemberName("unicode", true), new JSString("\\u00a3"));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("jmd"))
								info.put(new JSMemberName("unicode", true), new JSString(literalUnicode("J$")));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("kzt"))
								info.put(new JSMemberName("unicode", true), new JSString("\\u20b8"));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("kgs"))
								info.put(new JSMemberName("unicode", true), new JSString("\\u20c0"));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("aud"))
								info.put(new JSMemberName("unicode", true), new JSString(literalUnicode("A$")));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("lsl"))
								info.put(new JSMemberName("unicode", true), new JSString(literalUnicode("L/M")));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("zar"))
								info.put(new JSMemberName("unicode", true), new JSString(literalUnicode("R")));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("lrd"))
								info.put(new JSMemberName("unicode", true), new JSString(literalUnicode("L$")));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("chf"))
								info.put(new JSMemberName("unicode", true), new JSString(literalUnicode("SFr")));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("mop"))
								info.put(new JSMemberName("unicode", true), new JSString(literalUnicode("$")));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("mga"))
								info.put(new JSMemberName("unicode", true), new JSString(literalUnicode("Ar")));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("mwk"))
								info.put(new JSMemberName("unicode", true), new JSString(literalUnicode("Mk")));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("mvr"))
								info.put(new JSMemberName("unicode", true), new JSString("\\u0783"));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("xof"))
								info.put(new JSMemberName("unicode", true), new JSString(literalUnicode("fr")));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("xof"))
								info.put(new JSMemberName("unicode", true), new JSString(literalUnicode("fr")));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("usd"))
								info.put(new JSMemberName("unicode", true), new JSString(literalUnicode("$")));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("mru"))
								info.put(new JSMemberName("unicode", true), new JSString(literalUnicode("UM")));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("mur"))
								info.put(new JSMemberName("unicode", true), new JSString(literalUnicode("MRe")));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("mdl"))
								info.put(new JSMemberName("unicode", true), new JSString(literalUnicode("lei")));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("xcd"))
								info.put(new JSMemberName("unicode", true), new JSString(literalUnicode("EC$")));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("mzn"))
								info.put(new JSMemberName("unicode", true), new JSString(literalUnicode("MTn")));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("mmk"))
								info.put(new JSMemberName("unicode", true), new JSString(literalUnicode("K/Ks")));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("nad"))
								info.put(new JSMemberName("unicode", true), new JSString(literalUnicode("")));
							else if (ccyIsoCode.getTextContent().equalsIgnoreCase("mdl"))
								info.put(new JSMemberName("unicode", true), new JSString("\\u20ae"));
							else
								err.println(iso2 + "-" + iso3 + " is null");
						} else
							info.put(new JSMemberName("unicode", true), ls);
						NavigableMap<JSMemberName, JSValue<?>> life = new TreeMap<>();
						life.put(new JSMemberName("created", true), new JSNull());
						life.put(new JSMemberName("deprecated", true), new JSNull());
						life.put(new JSMemberName("to", true), new JSNull());
						obj.put(new JSMemberName("info", true), new JSObject(info));
						obj.put(new JSMemberName("life-span", true), new JSObject(life));
						List<JSObject> sec = new ArrayList<>();
						NavigableMap<JSMemberName, JSValue<?>> el = new TreeMap<>();
						try {
							el.put(new JSMemberName("rate", true), new JSNumber(
									d(1).scaleByPowerOfTen(Integer.parseInt(ccyFracUnit.getTextContent())).intValue()));
						} catch (NumberFormatException e) {
							el.put(new JSMemberName("rate", true), new JSNull());
						}
						sec.add(new JSObject(el));
						obj.put(new JSMemberName("secondary-unit-info", true), new JSArray<>(sec));
						obj.put(new JSMemberName("peg-info", true), new JSArray<>(new ArrayList<>()));
						a.add(new JSObject(obj));
					}
				} catch (SAXException e) {
					e.printStackTrace();
				}
				ps = new PrintStream("test\\list-1\\list-1.json");
				new JSArray<>(a).parseToScript(ps, 1);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (input != null)
					try {
						input.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if (ps != null)
					ps.close();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		out.println("done!");
	}

}
