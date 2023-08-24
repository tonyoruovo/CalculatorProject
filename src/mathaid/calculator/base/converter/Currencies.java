/**
 * 
 */
package mathaid.calculator.base.converter;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import mathaid.BaseException;
import mathaid.ExceptionMessage;
import mathaid.NullException;
import mathaid.calculator.base.Calculator;
import mathaid.calculator.base.util.Utility;
import mathaid.calculator.base.value.ValueFormatException;

/*
 * Date: 15 Jul 2021----------------------------------------------------------- 
 * Time created: 07:37:04---------------------------------------------------  
 * Package: mathaid.calculator.base.converter------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: Currencies.java------------------------------------------------------ 
 * Class name: Currencies------------------------------------------------ 
 */
/**
 * A class that contains a set of static members that defines currencies and how
 * they are used with a {@code Convertible} object. The end-product of this
 * class is used in the {@code CurrencyUnit} class.
 * 
 * @author Oruovo Anthony Etineakpopha
 */
public final class Currencies {

	/**
	 * A {@code List} of {@code MediumOfExchange}. This is the only legitimate way
	 * to retrieve a {@code MediumOfExchange} object. The objects are ordered
	 * alphabetically by their {@link MediumOfExchange#getCountry()
	 * country/organisation name}.
	 * 
	 * <p>
	 * For currencies whose numeric code could not be ascertained, the value -1 is used.
	 * All date specified by {@link MediumOfExchange#getDateCreated()} are given in the
	 * modern era. Any dates preceding the current issuer (central bank or federal reserve)
	 * may not be regarded. Most dates in the format with 31-12-YYYY or 1-1-YYYY could
	 * not have their months and days be ascertained. Alot of currencies have the same country name
	 * (specified by {@link MediumOfExchange#getCountry()}), the same numeric code,
	 * but each has a unique ISO 4217 code. The following ar some minor changes to take
	 * note of:
	 * <ul>
	 * <li><strong>AFA - </strong>The numeric is the same as <i>AFN</i>. Infact many of the revalued/abandoned
	 * currencies have the same numeric code as its predecessor (or successor).</li>
	 * <li><strong>ADF - </strong>Apart from it's iso and country name, the rest non-null values is the same as <i>FRF</i> values.</li>
	 * <li><strong>ADP - </strong>Apart from it's iso and country name, the rest non-null values is the same as <i>ESP</i> values.</li>
	 * <li><strong>AWF - </strong>Apart from it's iso, the rest non-null values is the same as <i>AWG</i> values.</li>
	 * <li><strong>BHD - </strong>Pegged day is unknown. Although it was pegged with IMF's special drawing rights (<i>SDR</i>),
	 * this implementation returns the <i>USD</i>.</li>
	 * <li><strong>BZD - </strong>The date created is not exact, but when the first notes under the country's name were issued.</li>
	 * <li><strong>XOF, XAF, KMF - </strong>In reality, it was pegged to the <i>FRF</i>.</li>
	 * <li><strong>BAM, BAD, BGN - </strong>In reality it was pegged to the <i>DEM</i>.</li>
	 * <li><strong>CVE - </strong>In reality, it was pegged to the <i>PTE</i>.</li>
	 * <li><strong>CUP - </strong>In reality, it was pegged to the old Soviet Ruble which is not on this list.</li>
	 * <li><strong>ANG - </strong>Will cease to be come a currency in 2023 at least and 2024 at most.</li>
	 * <li><strong>EUR, XEU - </strong>Actually introduced in 1-1-1999?</li>
	 * <li><strong>GGP, JEP - </strong>Has no iso name. Could be the same with <i>GBP</i>?</li>
	 * <li><strong>KID - </strong>Has no iso name. Could be the same as the <i>AUD</i>?</li>
	 * <li><strong>LVL - </strong>Questionable numeric code. Could not confirm at the time of compiling this list.</li>
	 * <li><strong>LBP - </strong>Pegged rate is almost meaningless as black market rate wildly diverges from the official one.</li>
	 * <li><strong>NPR - </strong>Pegged selling rate is NPR<code>1.6015</code>.</li>
	 * </ul>
	 * </p>
	 * <p>
	 * Values are according to <a href="https://www.wikipedia.org">Wikipedia</a> and
	 * <a href="https://www.six-group.com/dam/download/financial-information/data-center/iso-currrency/lists/list_three.xml">the official iso
	 * 4217 website's list of deprecated currencies</a>. All metal rates are expected to be in troy ounces.
	 * </p>
	 */
	public static final List<MediumOfExchange> CURRENCIES;

	/*
	 * Populates the above list with valid currencies.
	 */
	static {
		CURRENCIES = new ArrayList<>();
		CURRENCIES.add(new MediumOfExchange("Afghanistan", "AFN", 971, 2, "Afghan Afghani", "\u060B", get(8, 10, 2002)));
		CURRENCIES.add(new MediumOfExchange("Afghanistan", "AFA", 971, 2, "Afghan Afghani", "\u060B", get(1, 1, 1925), get(4, 9, 2002), "AFN"));//numeric code is the same as AFN
		CURRENCIES.add(new MediumOfExchange("African Development Bank", "XUA", 965, 5, "ADB Unit of Account", null, get(10, 9, 1964)));
		CURRENCIES.add(new MediumOfExchange("Albania", "ALL", 8, 2, "Albanian Lek", "\u004C\u0065\u006B", get(16, 2, 1926)));
		CURRENCIES.add(new MediumOfExchange("Algeria", "DZD", 12, 2, "Algerian Dinar", "DA", get(1, 4, 1964)));
		/*The actual date created might be 13-march-1979?*/
		//CURRENCIES.add(new MediumOfExchange("Andorra", "ADF", -1, 2, "Andorran Franc", "F", get(1, 1, 1960), null, "FRF", null, get(17, 2, 2002), "EUR"));//Values other than iso, country name and display name uses french franc values
		// CURRENCIES.add(new MediumOfExchange("Andorra", "ADP", -1, 2, "Andorran Peseta", "Pts", get(1, 1, 1868), null, "ESP", null, get(17, 2, 2002), "EUR"));//Values other than iso, country name and display name uses spanish peseta values
		CURRENCIES.add(new MediumOfExchange("Angola", "AOA", 973, 2, "Angolan Kwanza", "Kz", get(1, 12, 1999)));
		CURRENCIES.add(new MediumOfExchange("Angola", "AOK", 24, 2, "Angolan Kwanza", "Kz", get(8, 1, 1977), get(24, 9, 1990), "AON"));//values is according to https://www.six-group.com/dam/download/financial-information/data-center/iso-currrency/lists/list_three.xml
		CURRENCIES.add(new MediumOfExchange("Angola", "AON", 24, 2, "Angolan Novo kwanza", "Kz", get(25, 9, 1990), get(30, 6, 1995), "AOR"));//values is according to https://www.six-group.com/dam/download/financial-information/data-center/iso-currrency/lists/list_three.xml
		CURRENCIES.add(new MediumOfExchange("Angola", "AOR", 982, 2, "Angolan Kwanza reajustado", "Kz", get(1, 7, 1995), get(30, 11, 1999), "AOK"));//values is according to https://www.six-group.com/dam/download/financial-information/data-center/iso-currrency/lists/list_three.xml
		CURRENCIES.add(new MediumOfExchange("Argentina", "ARS", 32, 2, "Argentine Peso", "$", get(31, 12, 1992)));// day and month within date created is unknown
		CURRENCIES.add(new MediumOfExchange("Argentina", "ARA", 32, 2, "Argentine Austral", "\u20B3", get(15, 6, 1985), get(31, 12, 1991), "ARY"));
		CURRENCIES.add(new MediumOfExchange("Argentina", "ARP", 32, 2, "Peso Argentino", "$a", get(1, 6, 1983), get(14, 6, 1985), "ARA"));
		CURRENCIES.add(new MediumOfExchange("Argentina", "ARY", 32, 2, "Argentine Peso", "$", get(31, 12, 1989), get(31, 12, 1990), "ARS"));//only year is accurate
		CURRENCIES.add(new MediumOfExchange("Armenia", "AMD", 51, 2, "Armenian Dram", "\u058F", get(22, 11, 1993)));
		CURRENCIES.add(new MediumOfExchange("Aruba", "AWG", 533, 2, "Aruban Guilder", "\u0192", get(31, 12, 1986), "USD", get("1.79")));//exact month and day is unknown
		CURRENCIES.add(new MediumOfExchange("Aruba", "AWF", 533, 2, "Aruban Florin", "\u0192", get(31, 12, 1986), "USD", get("1.79")));//same with aruba guilder
		CURRENCIES.add(new MediumOfExchange("Austria", "ATS", 40, 2, "Austrian Shilling", "\u00F6S", get(21, 11, 1947), null, "EUR", get("13.7603"), get(1, 1, 1999), "EUR"));
		CURRENCIES.add(new MediumOfExchange("Australia", "AUD", 36, 2, "Australian Dollar", "AU$", get(14, 2, 1966), "TVD", get("1")));
		CURRENCIES.add(new MediumOfExchange("Azerbaijan", "AZN", 944, 2, "Azerbaijanian Manat", "\u20BC", get(1, 1, 2006), get(31, 5, 2017), "USD", get("1.7"), null, null));
		CURRENCIES.add(new MediumOfExchange("Azerbaijan", "AYM", 945, 2, "Azerbaijanian Manat", "\u20BC", get(30, 8, 1994), get(31, 10, 2005), "AZM"));
		CURRENCIES.add(new MediumOfExchange("Azerbaijan", "AZM", 31, 2, "Azerbaijanian Manat", "\u20BC", get(1, 11, 2005), get(31, 12, 2005), "AZN"));
		CURRENCIES.add(new MediumOfExchange("Bahamas", "BSD", 44, 2, "Bahamian Dollar", "B$", get(31, 12, 1966), null, "USD", get("1"), null, null));
		CURRENCIES.add(new MediumOfExchange("Bahrain", "BHD", 48, 3, "Bahraini Dinar", "BD", get(1, 1, 1965), get(31, 12, 1980), "USD", get("0.376"), null, null));//pegged day unknown
		CURRENCIES.add(new MediumOfExchange("Bangladesh", "BDT", 50, 2, "Taka",
				String.valueOf(Character.valueOf((char) 2547)), get(31, 12, 1972)));
		CURRENCIES.add(new MediumOfExchange("Barbados", "BBD", 52, 2, "Barbados Dollar", "BDs$", get(1, 1, 1973), get(5, 7, 1975), "USD", get("1.98"), null, null));
		CURRENCIES.add(new MediumOfExchange("Belarus", "BYN", 933, 0, "Belarrusian Ruble", "Br", get(1, 7, 2016)));
		CURRENCIES.add(new MediumOfExchange("Belarus", "BYB", 112, 0, "Belarrusian Ruble", "Br", get(1, 5, 1992), get(1, 1, 2001), "BYR"));
		CURRENCIES.add(new MediumOfExchange("Belarus", "BYR", 974, 0, "Belarrusian Ruble", "Br", get(1, 1, 2001), get(30, 6, 2016), "BYN"));
		CURRENCIES.add(new MediumOfExchange("Belgium", "BEF", 56, 2, "Belgian Franc", "Fb", get(31, 12, 1832), null, null, null, get(1, 3, 2002), "EUR"));
		CURRENCIES.add(new MediumOfExchange("Belize", "BZD", 84, 2, "Belize Dollar", "BZ$", get(1, 7, 1983), get(31, 12, 1978), "USD", get("0.5"), null, null));
		CURRENCIES.add(new MediumOfExchange("Bermuda", "BMD", 60, 2, "Bermudian Dollar", "$", get(31, 7, 1972), null, "USD", get("1"), null, null));
		CURRENCIES.add(new MediumOfExchange("Bhutan", "BTN", 64, 2, "Bhutanese Ngultrum", "Nu", get(31, 12, 1974), null, "INR", get("1"), null, null));
		CURRENCIES.add(new MediumOfExchange("Bolivia,  (Plurinational state of)", "BOB", 68, 2, "Boliviano", "Bs", get(31, 12, 1987)));
		CURRENCIES.add(new MediumOfExchange("Bolivia,  (Plurinational state of)", "BOP", 68, 2, "Peso Boliviano", "b$", get(31, 12, 1963), null, null, null, get(27, 2, 1987), "BOB"));
		CURRENCIES.add(new MediumOfExchange("Bosnia and Herzgovina", "BAM", 977, 2, "Convertible Mark", "KM", get(31, 12, 1998), null, "EUR", get("1.95583"), null, null));// actually pegged with the german deustch mark
		CURRENCIES.add(new MediumOfExchange("Bosnia and Herzgovina", "BAD", 70, 2, "Bosnia and Herzgovina Dinar", "BAD", get(31, 7, 1992), null, null, null, get(31, 12, 1998), "BAM"));// actually pegged with the german deustch mark
		CURRENCIES.add(new MediumOfExchange("Botswana", "BWP", 72, 2, "Pula", "P", get(23, 8, 1976)));
		CURRENCIES.add(new MediumOfExchange("Brazil", "BRL", 986, 2, "Brazilian Real", "R$", get(1, 7, 1994)));
		CURRENCIES.add(new MediumOfExchange("Brazil", "BRB", 76, 2, "Brazilian Cruzeiro", "\u20A2$", get(31, 12, 1942), get(1, 3, 1986), "BRC"));
		CURRENCIES.add(new MediumOfExchange("Brazil", "BRC", 76, 2, "Brazilian Cruzado", "Cz$", get(31, 3, 1986), get(1, 2, 1989), "BRN"));
		CURRENCIES.add(new MediumOfExchange("Brazil", "BRN", 76, 2, "Brazilian New Cruzado", "NCz$", get(31, 2, 1989), get(15, 3, 1990), "BRE"));
		CURRENCIES.add(new MediumOfExchange("Brazil", "BRE", 76, 2, "Brazilian Cruzeiro", "\u20A2$", get(31, 3, 1990), get(1, 3, 1993), "BRR"));
		CURRENCIES.add(new MediumOfExchange("Brazil", "BRR", 987, 2, "Brazilian Cruzeiro Real", "CR$", get(31, 3, 1993), get(30, 6, 1994), "BRL"));
		CURRENCIES.add(new MediumOfExchange("Brazil", "URV", -1, 2, "Unidade real de valor"));//cannot ascertain it's numeric code
		CURRENCIES.add(new MediumOfExchange("Brunei Darussalam", "BND", 96, 2, "Brunei Dollar", "B$", get(31, 12, 1967), null, "SGD", get("1"), null, null));
		CURRENCIES.add(new MediumOfExchange("Bulgaria", "BGN", 975, 2, "Bulgarian Lev", "\u043B\u0432", get(5, 7, 1999), null, "EUR", get("1.95583"), get(31, 12, 2023), "EUR"));//actually pegged to the german deustch mark
		CURRENCIES.add(new MediumOfExchange("Bulgaria", "BGJ", 100, 2, "Bulgarian Lev A/52", "\u043B\u0432", get(31, 12, 1881), get(1, 1, 1952), "BGK"));
		CURRENCIES.add(new MediumOfExchange("Bulgaria", "BGK", 100, 2, "Bulgarian Lev A/62", "\u043B\u0432", get(31, 12, 1952), get(1, 1, 1962), "BGL"));
		CURRENCIES.add(new MediumOfExchange("Bulgaria", "BGK", 100, 2, "Bulgarian Lev", "\u043B\u0432", get(31, 12, 1962), get(1, 11, 2003), "BGN"));
		CURRENCIES.add(new MediumOfExchange("Burundi", "BIF", 108, 0, "Burundian Franc", "FBu", get(31, 12, 1964)));
		CURRENCIES.add(new MediumOfExchange("Cambodia", "KHR", 116, 2, "Cambodian Riel", "\u17DB", get(20, 3, 1980), null, "USD", get("4050"), null, null));
		CURRENCIES.add(new MediumOfExchange("Canada", "CAD", 124, 2, "Canadian Dollar", "$", get(31, 12, 1958)));
		CURRENCIES.add(new MediumOfExchange("Cape Verde", "CVE", 132, 2, "Cape Verdean Escudo", "$", get(31, 12, 1914), get(1, 7, 1998), "EUR", get("110.265"), null, null));// actually pegged with the portugese escudo
		CURRENCIES.add(new MediumOfExchange("Cayman Islands", "KYD", 136, 2, "Cayman Islands Dollar", "$", get(31, 12, 1972), get(1, 4, 1974), "USD", get("1").divide(get("1.2"), Website.DEF), null, null));
		CURRENCIES.add(new MediumOfExchange("Chile", "CLP", 152, 0, "Chilean Peso", "$", get(29, 9, 1975)));
		CURRENCIES.add(new MediumOfExchange("Chile", "CLF", 990, 0, "Unidades de fomento", "UF", get(10, 1, 1967)));
		CURRENCIES.add(new MediumOfExchange("China", "CNY", 156, 2, "Yuan Renminbi", "\u00A5", get(31,12,1948)));
		CURRENCIES.add(new MediumOfExchange("Columbia", "COP", 170, 2, "Columbian Peso", "$", get(31, 12, 1931)));
		CURRENCIES.add(new MediumOfExchange("Columbia", "COU", 970, 2, "Unidad de Valor Real"));
		CURRENCIES.add(new MediumOfExchange("Comoros", "KMF", 174, 0, "Comorian Franc", "CF", get(1, 1, 1960), null, "EUR", get("491.96775"), null, null));//Actually pegged with the french franc
		CURRENCIES
				.add(new MediumOfExchange("Congo, the Democratic Rebulic of", "CDF", 976, 2, "Congolese Franc", "FC", get(31, 12, 1997)));
		CURRENCIES.add(new MediumOfExchange("Costa Rica", "CRC", 188, 2, "Costa Rican Colon", "\u20A1", get(31, 12, 1951)));
		CURRENCIES.add(new MediumOfExchange("Crotia", "HRK", 191, 2, "Croatian Kuna", "kn", get(30, 5, 1994), get(10, 7, 2020), "EUR", get("7.5345"), get(1, 1, 2023), "EUR"));
		CURRENCIES.add(new MediumOfExchange("Cuba", "CUP", 192, 2, "Cuban Peso", "\u20B1", get(31, 12, 1851), null, "USD", get("24"), null, null));//actually pegged to the old soviet ruble
		CURRENCIES.add(new MediumOfExchange("Cuba", "CUC", 931, 2, "Peso Convertible", "CUC$", get(31, 12, 1994), null, "USD", get("1"), get(1, 12, 2021), "CUP"));
		CURRENCIES.add(new MediumOfExchange("Old Netherlands Antilles", "ANG", 532, 2, "Netherlands Antilean Guilder", "NA\u0192", get(31, 12, 1952), null, "USD", get("1.79"), null, null));
		CURRENCIES.add(new MediumOfExchange("Czechoslovakia", "CSK", 200, 2, "Czechoslovak Koruna", "K\u010Ds", get(1, 6, 1953), get(1, 1, 2009), "EUR"));
		CURRENCIES.add(new MediumOfExchange("Czech Republic", "CZK", 203, 2, "Czech Koruna", "K\u010D", get(31, 12, 1993)));
		CURRENCIES.add(new MediumOfExchange("Cyprus", "CYP", 196, 2, "Cypriot Pound", "£", get(31, 12, 1879), get(7, 12, 2007), "EUR", get("0.585274"), get(1, 1, 2008), "EUR"));
		CURRENCIES.add(new MediumOfExchange("Denmark", "DKK", 208, 2, "Danish Krone", "Kr", get(31, 12, 1914), get(13, 3, 1979), "EUR", get("7.46038"), null, null));
		CURRENCIES.add(new MediumOfExchange("Djibouti", "DJF", 262, 0, "Djiboutian Franc", "Fdj", get(31, 12, 1919), null, "USD", get("177.721"), null, null));
		CURRENCIES.add(new MediumOfExchange("Dominican Republic", "DOP", 214, 2, "Dominican Peso", "RD$", get(31, 12, 1844)));
		CURRENCIES.add(new MediumOfExchange("Economic and Monetary Community of Central Africa", "XAF", 950, 0, "BEAC", "FCFA", get(31, 12, 1945), null, "EUR", get("655.957"), null, null));
		CURRENCIES.add(new MediumOfExchange("Ecuador", "ECS", 218, 2, "Ecuadorian Sucre", "S/.", get(31, 12, 1884), null, null, null, get(9, 1, 2000), "USD"));
		CURRENCIES.add(new MediumOfExchange("Egypt", "EGP", 818, 2, "Egyptian Pound", "£", get(31, 12, 1834)));
		CURRENCIES.add(new MediumOfExchange("El Salvador", "SVC", 222, 2, "El Salvador Colon", "\u20A1", get(1, 10, 1892), null, "USD", get("8.75"), get(1, 1, 2001), "USD"));
		CURRENCIES.add(new MediumOfExchange("Eritrea", "ERN", 232, 2, "Eritrean Nakfa", "Nkf", get(15, 11, 1998), null, "USD", get("15"), null, null));
		CURRENCIES.add(new MediumOfExchange("Ethiopia", "ETB", 230, 2, "Ethiopian Birr", "Br", get(31, 12, 1945)));
		CURRENCIES.add(new MediumOfExchange("Estonia", "EEK", 233, 2, "Estonian Kroon", "kr", get(20, 6, 1992), get(31, 12, 1998), "EUR", get("15.6466"), get(1, 1, 2011), "EUR"));
		CURRENCIES.add(new MediumOfExchange("European Union", "EUR", 978, 2, "European Euro", "\u20aC", get(1, 1, 2002)));//Actually introduced 1-1-1999
		CURRENCIES.add(new MediumOfExchange("European Union", "XEU", 954, 2, "European Currency Unit (E.C.U)", "\u20A0", get(13, 3, 1979)));//Actually introduced 1-1-1999
		CURRENCIES
				.add(new MediumOfExchange("Falkland Islands (Malvinas)", "FKP", 238, 2, "Falkland Islands Pound", "£", get(31, 12, 1974), null, "GBP", get("1"), null, null));
		CURRENCIES.add(new MediumOfExchange("Fiji", "FJD", 242, 2, "Fiji Dollar", "$", get(15, 1, 1969)));
		CURRENCIES.add(new MediumOfExchange("Finland", "FIM", 246, 2, "Finnish Markka", "Mk", get(31, 12, 1860), get(31, 12, 1998), "EUR", get("5.94573"), get(28, 2, 2002), "EUR"));
		CURRENCIES.add(new MediumOfExchange("France", "FRF", 250, 2, "French Franc", "F", get(31, 1, 1960), get(31, 12, 1998), "EUR", get("6.55957"), get(17, 2, 2002), "EUR"));
		CURRENCIES.add(new MediumOfExchange("French Polynesia", "XPF", 953, 0, "CFP Franc", "F", get(31, 12, 1945), null, "EUR", get("0.00838"), null, null));
		CURRENCIES.add(new MediumOfExchange("Gambia", "GMD", 270, 2, "Gambian Dalasi", "D", get(31, 12, 1971)));
		CURRENCIES.add(new MediumOfExchange("Georgia", "GEL", 981, 2, "Georgian Lari", "\u20BE", get(2, 10, 1995)));
		CURRENCIES.add(new MediumOfExchange("Georgia", "GEK", 268, 2, "Georgian Kuponi", "\u20BE", get(5, 4, 1993), get(2, 10, 1995), "GEL"));
		CURRENCIES.add(new MediumOfExchange("Germany", "DEM", 276, 2, "German Deutsche Mark", "DM", get(31, 12, 1948), get(31, 12, 1998), "EUR", get("1.95583"), get(1, 1, 1999), "EUR"));
		CURRENCIES.add(new MediumOfExchange("Ghana", "GHS", 936, 2, "Ghanaian Cedi", "\u00A2", get(2, 7, 2007)));
		CURRENCIES.add(new MediumOfExchange("Ghana", "GHC", 288, 2, "New Cedi", "\u00A2", get(23, 2, 1967), get(2, 7, 2007), "GHS"));
		CURRENCIES.add(new MediumOfExchange("Gibraltar", "GIP", 292, 2, "Gibraltar Pound", "£", get(31, 12, 1927), null, "GBP", get("1"), null, null));
		CURRENCIES.add(new MediumOfExchange("Greece", "GRD", 300, 2, "Greek Drachma", "\u20AF", get(31, 5, 1832), get(19, 6, 2000), "EUR", get("340.75"), get(1, 1, 2001), "EUR"));
		CURRENCIES.add(new MediumOfExchange("Guatemala", "GTQ", 320, 2, "Guatemalan Quetzal", "Q", get(31, 12, 1925)));
		CURRENCIES.add(new MediumOfExchange("Guernsey", "GGP", -1, 2, "Guernsey pound", "£", get(31, 1, 1921), null, "GBP", get("1"), null, null));//This is an unofficial iso 4217 name
		CURRENCIES.add(new MediumOfExchange("Guinea", "GNF", 324, 0, "Guinean Franc", "FG", get(31, 12, 1985)));
		CURRENCIES.add(new MediumOfExchange("Guinea", "GNS", 324, 2, "Guinean Syli", null, get(31, 12, 1971), null, null, null, get(1, 1, 1985), "GNF"));
		CURRENCIES.add(new MediumOfExchange("Guyana", "GYD", 328, 2, "Guyanese Dollar", "$", get(29, 1, 1839)));
		CURRENCIES.add(new MediumOfExchange("Haiti", "HTG", 332, 2, "Haitian Gourde", "G", get(31, 12, 1813)));
		CURRENCIES.add(new MediumOfExchange("Honduras", "HNL", 340, 2, "Houduran Lempira", "L", get(31, 12, 1931)));
		CURRENCIES.add(new MediumOfExchange("Hong Kong", "HKD", 344, 2, "Hong Kong Dollar", "$", get(31, 12, 1914), get(12, 5, 2005), "USD", get("7.8"), null, null));
		CURRENCIES.add(new MediumOfExchange("Hungary", "HUF", 348, 2, "Hungarian Forint", "Ft", get(1, 8, 1946)));
		CURRENCIES.add(new MediumOfExchange("Iceland", "ISK", 352, 0, "Icelandic Krona", "Kr", get(31, 12, 1918)));
		CURRENCIES.add(new MediumOfExchange("India", "INR", 358, 2, "Indian Rupee", "\u20B9", get(31, 12, 1837)));
		CURRENCIES.add(new MediumOfExchange("Indonesia", "IDR", 360, 2, "Indonesian Rupiah", "Rp", get(3, 10, 1946)));
		CURRENCIES.add(new MediumOfExchange("International Monetary Fund (IMF)", "XDR", 960, 5,
				"Special Draw Rights (SDR)", "SDR", get(31, 12, 1969)));
		CURRENCIES.add(new MediumOfExchange("Iran", "IRR", 364, 2, "Iranian Rial", "\uFDFC", get(31, 12, 1932)));
		CURRENCIES.add(new MediumOfExchange("Iraq", "IQD", 368, 3, "Iraqi Dinar", "IQD", get(1, 4, 1932)));
		CURRENCIES.add(new MediumOfExchange("Isle of Man", "IMP", -1, 2, "Manx Pound", "\u00A3", get(24, 6, 1710)));
		CURRENCIES.add(new MediumOfExchange("Isreal", "ILS", 376, 2, "Isreali New Shekel", "\u20AA", get(1, 1, 1986)));
		CURRENCIES.add(new MediumOfExchange("Israel", "ILP", 376, 2, "Israeli Pound", "I\u00A3", get(9, 6, 1952), get(23, 2, 1980), "ILR"));
		CURRENCIES.add(new MediumOfExchange("Israel", "ILR", 376, 2, "Israeli old Shekel", "IS", get(24, 2, 1980), get(31, 12, 1985), "ILS"));
		CURRENCIES.add(new MediumOfExchange("Italy", "ITL", 380, 2, "Italian Lira", "L", get(31, 12, 1861), get(31, 12, 1998), "EUR", get("1936.27"), get(17, 9, 1992), "EUR"));
		CURRENCIES.add(new MediumOfExchange("Ireland", "IEP", 372, 2, "Irish Pound", "\u00A3", get(31, 12, 1928), get(31, 12, 1998), "EUR", get("0.787564"), get(1, 1, 1999), "EUR"));
		CURRENCIES.add(new MediumOfExchange("Jamaica", "JMD", 388, 2, "Jamaican Dollar", "J$", get(30, 1, 1968)));
		CURRENCIES.add(new MediumOfExchange("Japan", "JPY", 392, 0, "Japanese Yen", "\u00A5", get(31, 12, 1871)));
		CURRENCIES.add(new MediumOfExchange("Jersey", "JEP", -1, 2, "Jersey Pound", "\u00A3", get(31,12,1971), null, "GBP", get("1"), null, null));//Not a separate currency from the GBP as a result has no iso name
		CURRENCIES.add(new MediumOfExchange("Jordan", "JOD", 400, 3, "Jordanian Dinar", "JOD", get(1, 7, 1950), get(23, 10, 1995), "USD", get("0.708"), null, null));
		CURRENCIES.add(new MediumOfExchange("Khazakstan", "KZT", 398, 2, "Khazakstani Tenge", "\u20B8", get(15, 11, 1993)));
		CURRENCIES.add(new MediumOfExchange("Kenya", "KES", 404, 2, "Kenyan Shilling", "KSh", get(31, 12, 1966)));
		CURRENCIES.add(new MediumOfExchange("Kiribati", "KID", 36, 2, "Kiribati Dollar", "$", get(14,2,1966), null, "AUD", get("1"), null, null));//Not a separate currency from the AUD as a result has no iso name
		CURRENCIES.add(new MediumOfExchange("Korea, Democratic Peoples Republic of", "KPW", 408, 2, "North Korean Won",
				"\u20A9", get(27, 2, 1959)));
		CURRENCIES.add(new MediumOfExchange("Korea, Republic of", "KRW", 410, 0, "South Korean Won", "\u20A9", get(31, 12, 1962)));
		CURRENCIES.add(new MediumOfExchange("Kuwait", "KWD", 414, 3, "Kuwaiti Dinar", "KD", get(31,12, 1961), null, "USD", get("0.29963"), null, null));
		CURRENCIES.add(new MediumOfExchange("Kyrgystan", "KGS", 417, 2, "Kygystani Som", "\u20C0", get(10, 5, 1993)));
		CURRENCIES.add(new MediumOfExchange("Lao, People's Democratic Republic (The)", "LAK", 418, 2, "Lao Kip", "\u20AD", get(16, 12, 1979)));
		CURRENCIES.add(new MediumOfExchange("Laos", "LAJ", 418, 2, "Pathet Lao Kip", "\u20AD", get(31, 12, 1976), get(16, 12, 1979), "LAK"));
		CURRENCIES.add(new MediumOfExchange("Latvia", "LVL", 428, 2, "Latvian Lats", "Ls", get(5, 3, 1993), get(1, 1, 2005), "EUR", get("0.702804"), get(1, 1, 2014), "EUR"));
		CURRENCIES.add(new MediumOfExchange("Latvia", "LVR", 428, 2, "Latvian Rublis", "Lvr", get(7, 5, 1992), get(5, 3, 1993), "LVL"));
		CURRENCIES.add(new MediumOfExchange("Lebanon", "LBP", 422, 2, "Lebanese Pound", "L£", get(31, 12, 1924), null, "USD", get("1507"), null, null));//Pegged rate is almost meaningless as black market rate wildly diverges
		CURRENCIES.add(new MediumOfExchange("Lesotho", "LSL", 426, 2, "Lesotho Loti", "L", get(31, 12, 1966), null, "ZAR", get("1"), null, null));
		CURRENCIES.add(new MediumOfExchange("Liberia", "LRD", 430, 2, "Liberian Dollar", "L$", get(31, 12, 1943)));
		CURRENCIES.add(new MediumOfExchange("Libya", "LYD", 434, 3, "Libyan Dinar", "LD", get(30, 9, 1971)));
		CURRENCIES.add(new MediumOfExchange("Lithuania", "LTL", 440, 2, "Lithuanian Litas", "Lt", get(25, 6, 1993), get(2,2, 2002), "EUR", get("3.4528"), get(1, 1, 2015), "EUR"));
		CURRENCIES.add(new MediumOfExchange("Lithuania", "LTT", 440, 2, "Lithuanian Talonas", null, get(5, 8, 1991), get(25, 6, 1993), "LTL"));
		CURRENCIES.add(new MediumOfExchange("Luxembourg", "LUC", 989, 2, "Luxembourg Convertible Franc", "F", get(31, 12, 1935), get(1, 3, 1990), "LUF"));
		CURRENCIES.add(new MediumOfExchange("Luxembourg", "LUL", 988, 2, "Luxembourg Financial Franc", "F", get(31, 12, 1935), get(1, 3, 1990), "LUF"));
		CURRENCIES.add(new MediumOfExchange("Luxembourg", "LUF", 442, 2, "Luxembourg Franc", "F", get(31, 12, 1935), get(31,12, 1998), "EUR", get("40.3399"), get(28, 2, 2002), "EUR"));
		CURRENCIES.add(new MediumOfExchange("Macau", "MOP", 446, 2, "Macanesse Pataca", "MOP$", get(31, 12, 1952), null, "HKD", get("1.03"), null, null));
		CURRENCIES.add(new MediumOfExchange("Macedonia", "MKD", 807, 2, "Macedonian Denar", "ден", get(30, 4, 1993)));
		CURRENCIES.add(new MediumOfExchange("Madagascar", "MGA", 969, 2, "Malagasy Ariary", "Ar", get(1, 1, 2005)));
		CURRENCIES.add(new MediumOfExchange("Madagascar", "MGF", 450, 0, "Malagasy Franc", "MF", get(31, 12, 1961), get(1,1,2005), "MGA"));
		CURRENCIES.add(new MediumOfExchange("Malawi", "MWK", 454, 2, "Malawian Kwacha", "MK", get(31,12,1971)));
		CURRENCIES.add(new MediumOfExchange("Malaysia", "MYR", 458, 2, "Malaysian Ringgit", "RM", get(31,12,1993)));
		CURRENCIES.add(new MediumOfExchange("Maldives", "MVR", 462, 2, "Maldivian Rifiyaa", "Rf", get(22,1,1983)));
		CURRENCIES.add(new MediumOfExchange("Mali", "MLF", 466, 2, "Malian Franc", "MAF", get(31,12,1962), get(1,1,1984), "XOF"));
		CURRENCIES.add(new MediumOfExchange("Malta", "MTL", 470, 2, "Maltese Lira", "Lm", get(31,12,1972), get(1, 5, 2005), "EUR", get("0.4293"), get(31,12,2007), "EUR"));
		CURRENCIES.add(new MediumOfExchange("Mauritania", "MRO", 478, 2, "Mauritanian Ouguiya", "UM", get(31,12,1973), get(5,12,2017), "MRU"));
		CURRENCIES.add(new MediumOfExchange("Mauritania", "MRU", 929, 2, "Mauritanian Ouguiya", "UM", get(5,12,2017)));
		CURRENCIES.add(new MediumOfExchange("Mauritius", "MUR", 480, 2, "Mauritian Rupee", "MUR", get(31,12,1877)));
		CURRENCIES.add(new MediumOfExchange("Mexico", "MXN", 484, 2, "Mexican new Peso", "N$", get(31,12,1863)));
		CURRENCIES.add(new MediumOfExchange("Mexico", "MXP", 484, 2, "Mexican Peso", "$", get(1,1,1993)));
		CURRENCIES.add(new MediumOfExchange("Mexico", "MXV", 979, 2, "Mexican Unidad de Inversion (UDI)"));
		CURRENCIES.add(new MediumOfExchange("Moldolva, Republic of", "MDL", 498, 2, "Moldovan Leu", "L", get(29, 11, 1993)));
		CURRENCIES.add(new MediumOfExchange("Monaco, Principality of", "MCF", 250, 2, "Monégasque franc", "fr", get(31,12,1838), get(31,12,1996), "FRF"));
		CURRENCIES.add(new MediumOfExchange("Mongolia", "MNT", 496, 2, "Mongolian Tugrik", "\u20AE", get(9, 12, 1925)));
		CURRENCIES.add(new MediumOfExchange("Morocco", "MAD", 504, 2, "Moroccan Dirham", "DH", get(16, 10, 1960)));
		CURRENCIES.add(new MediumOfExchange("Mozambique", "MZN", 943, 2, "Mozambican Metical", "MT", get(1, 7, 2006)));
		CURRENCIES.add(new MediumOfExchange("Mozambique", "MZE", 508, 2, "Mozambican Escudo", null, get(31,12,1914), get(16, 6, 1980), "MZM"));
		CURRENCIES.add(new MediumOfExchange("Mozambique", "MZM", 508, 2, "Mozambican Metical", "MT", get(16,6,1980), get(1, 7, 2006), "MZN"));
		CURRENCIES.add(new MediumOfExchange("Myanmar", "MMK", 104, 2, "Kyat", "K", get(1, 7, 1952)));
		CURRENCIES.add(new MediumOfExchange("Namibia", "NAD", 516, 2, "Namibian Dollar", "N$", get(31,12, 1993), null, "ZAR", get("1"), null, null));
		CURRENCIES.add(new MediumOfExchange("Nepal", "NPR", 624, 2, "Nepalesse Rupee", "रु", get(31, 12, 1932), null, "INR", get("1.6"), null, null));//Pegged sell rate is NPR1.6015
		CURRENCIES.add(new MediumOfExchange("Netherlands", "NLG", 528, 2, "Dutch Guilder", "\u0192", get(31, 12, 1434), get(31,12, 1998), "EUR", get("2.20371"), get(1, 1, 1999), "EUR"));
		CURRENCIES.add(new MediumOfExchange("New Zealand", "NZD", 554, 2, "New Zealand Dollar", "$", get(10, 7, 1967)));
		CURRENCIES.add(new MediumOfExchange("Nicaragua", "NIO", 558, 2, "Nicaraguan Cordoba Oro", "C$", get(30, 4, 1991)));
		CURRENCIES.add(new MediumOfExchange("Nigeria", "NGN", 566, 2, "Nigerian Naira", "\u20A6", get(1, 1, 1973)));
		CURRENCIES.add(new MediumOfExchange("Norway", "NOK", 578, 2, "Norwegian Krone", "Kr", get(31,12,1875)));
		CURRENCIES.add(new MediumOfExchange("Oman", "OMR", 512, 3, "Omani Rial", "R.O", get(11, 11, 1972), null, "USD", get("0.384497"), null, null));
		CURRENCIES.add(new MediumOfExchange("Organisation of Eastern Caribbean States", "XCD", 951, 2, "East Carribean Dollar", "EC$", get(31, 12, 1965), "USD", get("2.7")));
		CURRENCIES.add(new MediumOfExchange("Pakistan", "PKR", 586, 2, "Pakistani Rupee", "Rs.", get(31, 12, 1947)));
		CURRENCIES.add(new MediumOfExchange("Panama", "PAB", 590, 2, "Panamanian Balboa", "B/.", get(31, 12, 1904), null, "USD", get("1"), null, null));
		CURRENCIES.add(new MediumOfExchange("Papua New Guinea", "PGK", 598, 2, "Papua new Guinea Kina", "K", get(19, 5, 1975)));
		CURRENCIES.add(new MediumOfExchange("Paraguay", "PYG", 600, 0, "Paraguayan Guarani", "\u20B2", get(5, 10, 1943)));
		CURRENCIES.add(new MediumOfExchange("Peru", "PEN", 604, 2, "Peruvian Nuevo Sol", "S/", get(1, 7, 1991)));
		CURRENCIES.add(new MediumOfExchange("Phillipines", "PHP", 608, 2, "Phillipine Peso", "\u20B1", get(14, 6, 1993)));
		CURRENCIES.add(new MediumOfExchange("Poland", "PLN", 985, 2, "Polish Zloty", "zł", get(1, 1, 1995)));
		CURRENCIES.add(new MediumOfExchange("Poland", "PLZ", 616, 2, "Polish Zloty", "zł", get(28, 2, 1919), get(1, 1, 1995), "PLN"));
		CURRENCIES.add(new MediumOfExchange("Portugal", "PTE", 620, 2, "Portuguese Escudo", "$", get(31, 12, 1911), get(31,12, 1998), "EUR", get("200.482"), get(1, 1, 1999), "EUR"));
		CURRENCIES.add(new MediumOfExchange("Qatar", "QAR", 624, 2, "Qatari Riyal", "QR", get(19, 5, 1973), null, "USD", get("3.64"), null, null));
		CURRENCIES.add(new MediumOfExchange("Rhodesia", "RHD", 716, 2, "Rhodesian Dollar", "$", get(17, 2, 1970), null, null, null, get(1,1, 1980), "ZWD"));
		CURRENCIES.add(new MediumOfExchange("Romania", "RON", 946, 2, "Romainian new Leu", "L", get(1, 7, 2005)));
		CURRENCIES.add(new MediumOfExchange("Romania", "ROL", 642, 2, "Romainian Leu", "L", get(28, 1, 1952), get(1, 7, 2005), "RON"));
		CURRENCIES.add(new MediumOfExchange("Russia", "RUB", 643, 2, "Russian Ruble", "\u20BD", get(31, 12, 1998)));
		CURRENCIES.add(new MediumOfExchange("Russia", "SUR", 810, 2, "Soviet Ruble", "py6", get(31, 12, 1922), get(1, 1, 1992), "RUR"));
		CURRENCIES.add(new MediumOfExchange("Russia", "RUR", 810, 2, "Russian Ruble", "\u20BD", get(31, 12, 1992), get(1, 1, 1998), "RUB"));//	руб
		CURRENCIES.add(new MediumOfExchange("Rwanda", "RWF", 646, 0, "Rwandan Franc", "RF", get(31, 12, 1960)));
		CURRENCIES.add(new MediumOfExchange("Saint Helena, Ascension and Tristan de Cuna", "SHP", 654, 2,
				"Saint Helena Pound", "£", get(31, 12, 1976), null, "GBP", get("1"), null, null));
		CURRENCIES.add(new MediumOfExchange("Samoa", "WST", 882, 2, "Samoan tālā", "$", get(31, 12, 1967)));
		CURRENCIES.add(new MediumOfExchange("Sao Tome and Principe", "STD", 676, 2, "São Tomé and Príncipe dobra", "Db", get(31, 12, 1977), get(1, 1, 2010), "EUR", get("24500"), get(1,1, 2018), "STN"));
		CURRENCIES.add(new MediumOfExchange("Sao Tome and Principe", "STN", 930, 2, "São Tomé and Príncipe dobra", "Db", get(1, 1, 2018), null, "EUR", get("24.5"), null, null));
		CURRENCIES.add(new MediumOfExchange("Saudi Arabia", "SAR", 682, 2, "Saudi Riyal", "SAR"));//This currency is too old
		CURRENCIES.add(new MediumOfExchange("Serbia", "RSD", 941, 2, "Serbian Dinar", "Дин.", get(31,12,2006)));
		CURRENCIES.add(new MediumOfExchange("Serbia and Montenegro", "CSD", 891, 2, "Serbian Dinar", "Дин.", get(31, 12, 2003), get(31,12,2006), "RSD"));
		CURRENCIES.add(new MediumOfExchange("Seychelles", "SCR", 690, 2, "Seychellois Rupee", "SCR", get(10,8,1914)));
		CURRENCIES.add(new MediumOfExchange("Sierra Leone", "SLL", 694, 2, "Sierra Leonean Leone", "Le", get(4, 8, 1964)));
		CURRENCIES.add(new MediumOfExchange("Singapore", "SGD", 702, 2, "Singapore Dollar", "S$", get(7, 5, 1967), null, "BND", get("1"), null, null));
		CURRENCIES.add(new MediumOfExchange("Sistema Unitario de Compensacion Regional de Pagos (SUCRE)", "XSU", 994, 5,
				"Sucre", "Sucre", get(31, 12, 2010)));
		CURRENCIES.add(new MediumOfExchange("Slovakia", "SKK", 703, 2, "Slovak Koruna", "Sk", get(8, 2, 1993), get(28,11, 2005), "EUR", get("30.126"), get(1,1, 2009), "EUR"));
		CURRENCIES.add(new MediumOfExchange("Slovenia", "SIT", 704, 2, "Slovenian Tolar", null, get(8, 10, 1991), get(11,7, 2006), "EUR", get("239.64"), get(1,1, 2005), "EUR"));
		CURRENCIES.add(new MediumOfExchange("Solomon Islands", "SBD", 90, 2, "Solomon Islands Dollar", "SI$", get(31, 12, 1977)));
		CURRENCIES.add(new MediumOfExchange("Somalia", "SOS", 706, 2, "Somali Shilling", "So.Sh.", get(31, 12, 1962)));
		CURRENCIES.add(new MediumOfExchange("South Africa", "ZAR", 710, 2, "South African Rand", "R", get(27, 2, 1961)));
		CURRENCIES.add(new MediumOfExchange("Spain", "ESP", 724, 2, "Spanish Peseta", "\u20A7", get(31, 12, 1868), get(31,12, 1998), "EUR", get("166.386"), get(1,1, 1999), "EUR"));
		CURRENCIES.add(new MediumOfExchange("Sri Lanka", "LKR", 144, 2, "Sri Lankan Rupee", "LKR", get(1, 1, 1872)));
		CURRENCIES.add(new MediumOfExchange("Sudan", "SDG", 938, 2, "Sudanese Pound", "£Sd", get(1, 7, 2007)));
		CURRENCIES.add(new MediumOfExchange("Sudan", "SDP", 736, 2, "Sudanese Pound", "LSd", get(1, 1, 1956), null, null, null, get(8,6, 1992), "SDD"));
		CURRENCIES.add(new MediumOfExchange("Sudan", "SDD", 736, 2, "Sudanese Dinar", "LSd", get(8, 6, 1992), null, null, null, get(1,7, 2007), "SDG"));
		CURRENCIES.add(new MediumOfExchange("Suriname", "SRD", 968, 2, "Surinamese Dollar", "$", get(1, 1, 2004)));
		CURRENCIES.add(new MediumOfExchange("Suriname", "SRG", 740, 2, "Surinamese Guilder", "\u0192", get(1, 1, 1940), null, null, null, get(1,1, 2004), "SRD"));
		CURRENCIES.add(new MediumOfExchange("Swaziland", "SZL", 748, 2, "Swazi lilangeni", "L", get(31, 12, 1974), null, "ZAR", get("1"), null, null));
		CURRENCIES.add(new MediumOfExchange("Sweeden", "SEK", 752, 2, "Sweedish Krona", "Kr", get(31, 12, 1876)));
		CURRENCIES.add(new MediumOfExchange("Switzerland", "CHF", 756, 2, "Swiss Franc", "Fr", get(7, 5, 1850)));
		CURRENCIES.add(new MediumOfExchange("Syria", "SYP", 760, 2, "Syrian Pound", "S£", get(31, 12, 1919)));
		CURRENCIES.add(new MediumOfExchange("Taiwan", "TWD", 901, 2, "New Taiwan Dollar", "NT$", get(15, 6, 1949)));
		CURRENCIES.add(new MediumOfExchange("Tajikistan", "TJS", 972, 2, "Tajikistani Somoni", "SM", get(30, 10, 2000)));
		CURRENCIES.add(new MediumOfExchange("Tajikistan", "TJR", 762, 2, "Tajik Ruble", null, get(10, 5, 1995), null, null, null, get(29,10, 2000), "TJS"));
		CURRENCIES.add(new MediumOfExchange("Tanzania", "TZS", 834, 2, "Tanzanian Shilling", "TSh", get(14, 6, 1966)));
		CURRENCIES.add(new MediumOfExchange("Thailand", "THB", 764, 2, "Thai Baht", "\u0E3F", get(28, 10 , 1904)));
		CURRENCIES.add(new MediumOfExchange("Trinidad and Tobago", "TTD", 780, 2, "Trinidad and Tobago Dollar", "TT$", get(31, 12, 1964)));
		CURRENCIES.add(new MediumOfExchange("Tunisia", "TND", 788, 3, "Tunisian Dinar", "DT", get(31, 12, 1960)));
		CURRENCIES.add(new MediumOfExchange("Turkey", "TRY", 949, 2, "Turkish Lira", "TL", get(1, 1, 2005)));
		CURRENCIES.add(new MediumOfExchange("Turkmenistan", "TMT", 934, 2, "Turkmenistani New Manat", "m", get(31, 12, 2009)));
		CURRENCIES.add(new MediumOfExchange("Uganda", "UGX", 800, 2, "Ugandan Shilling", "USh", get(31, 12, 1987)));
		CURRENCIES.add(new MediumOfExchange("Uganda", "UGS", 800, 2, "Ugandan Shilling", "USh", get(31, 12, 1966), get(1, 1, 1987), "UGX"));
		CURRENCIES.add(new MediumOfExchange("Ukraine", "UAH", 980, 2, "Ukrainian Hryvnia", "\u20B4", get(16, 9, 1996)));
		CURRENCIES.add(new MediumOfExchange("Ukraine", "UAK", 804, 2, "Ukrainian Karbovanets", "\u20B4", get(10, 1, 1992), get(16, 9, 1996), "UAH"));
		CURRENCIES.add(new MediumOfExchange("United Arab Emirates", "AED", 784, 2, "UAE Dirham", null, get(31, 12, 1973)));
		CURRENCIES.add(new MediumOfExchange("United Kingdom", "GBP", 826, 2, "Pound Sterling", "£"));//Too old
		CURRENCIES.add(new MediumOfExchange("United States Of America", "USD", 840, 2, "United States Dollar", "$", get(31,12, 1732)));
		CURRENCIES.add(new MediumOfExchange("Uruguay", "UYU", 858, 2, "Peso Uruguayo", "$U", get(1, 3, 1993)));
		CURRENCIES.add(new MediumOfExchange("Uruguay", "UYP", 858, 2, "Uruguayan Peso", "$", get(1, 1, 1900), get(1,7,1975), "UYN"));
		CURRENCIES.add(new MediumOfExchange("Uruguay", "UYN", 858, 2, "Nuevo Peso Uruguayo", "$", get(1, 7, 1975), get(1,3,1993), "UYU"));
		CURRENCIES.add(
				new MediumOfExchange("Uruguay", "UYI", 940, 0, "Uruguay Peso en Unidades Indexadas (URUIURUI)"));
		CURRENCIES.add(new MediumOfExchange("Uzbekistan", "UZS", 860, 2, "Uzbekistani So'm", "лв", get(15, 11, 1993)));
		CURRENCIES.add(new MediumOfExchange("Vanatu", "VUV", 548, 0, "Vanatu Vatu", "VT", get(31, 12, 1981)));
		CURRENCIES.add(new MediumOfExchange("Venezuela", "VEB", 862, 2, "Venezuelan Bolivar", "Bs", get(31, 3, 1879), get(31, 12, 2007), "VEF"));
		CURRENCIES.add(new MediumOfExchange("Venezuela", "VEF", 937, 2, "Bolivar Fuerte", "Bs", get(1, 1, 2008), get(20, 8, 2018), "VES"));
		CURRENCIES.add(new MediumOfExchange("Venezuela", "VES", 928, 2, "Sovereign Bolivar", "Bs", get(20, 8, 2018)));
		CURRENCIES.add(new MediumOfExchange("Venezuela", "VED", 926, 2, "Sovereign Bolivar", "Bs", get(1, 10, 2021)));
		CURRENCIES.add(new MediumOfExchange("Vietnam", "VND", 704, 2, "Vietnamese đồng", "\u20AB", get(3, 5, 1978)));
		CURRENCIES.add(new MediumOfExchange("West African Economic and Monetary Union", "XOF", 952, 0, "BCEAO", "CFA", get(31, 12, 1945), get(1, 1, 1999), "EUR", get("655.957"), null, null));
		CURRENCIES.add(new MediumOfExchange("Yemen", "YER", 886, 2, "Yemeni Rial", "YER", get(31, 12, 1990)));
		CURRENCIES.add(new MediumOfExchange("Zaire", "ZRZ", 180, 2, "zaïre", "Ƶ", get(31, 12, 1967), get(30, 6, 1993), "ZRN"));
		CURRENCIES.add(new MediumOfExchange("Zaire", "ZRN", 180, 2, "Zairean new zaïre", "NZ", get(31, 12, 1993), get(1, 7, 1998), "CDF"));
		CURRENCIES.add(new MediumOfExchange("Zambia", "ZMW", 967, 2, "Zambian Kwacha", "ZK", get(1, 7, 1966)));
		CURRENCIES.add(new MediumOfExchange("Zambia", "ZMK", 894, 2, "Zambian Kwacha", "ZK", get(1, 7, 1966), get(31, 12, 2012), "ZMW"));
		CURRENCIES.add(new MediumOfExchange("Zimbabwe", "ZWD", 716, 2, "Zimbabwean Dollar", "$", get(31, 12, 1980), get(1, 8, 2006), "ZWN"));
		CURRENCIES.add(new MediumOfExchange("Zimbabwe", "ZWN", 942, 2, "Zimbabwean Dollar", "$", get(1, 8, 2006), get(1, 8, 2008), "ZWR"));
		CURRENCIES.add(new MediumOfExchange("Zimbabwe", "ZWR", 935, 2, "Zimbabwean Dollar", "$", get(1, 8, 2008), get(2, 2, 2009), "ZWL"));
		CURRENCIES.add(new MediumOfExchange("Zimbabwe", "ZWL", 932, 2, "Zimbabwean Dollar", "Z$", get(2, 2, 2009)));
		//The rates of metals are in ounces
		CURRENCIES.add(new MediumOfExchange("Copper", "XCP", -1, 5, "Copper"));
		CURRENCIES.add(new MediumOfExchange("ZZ08_Gold", "XAU", 959, 5, "Gold"));
		CURRENCIES.add(new MediumOfExchange("ZZ09_Palladium", "XPD", 964, 5, "Palladium"));
		CURRENCIES.add(new MediumOfExchange("ZZ10_Platinium", "XPT", 962, 5, "Platinium"));
		CURRENCIES.add(new MediumOfExchange("ZZ11_Silver", "XAG", 961, 5, "Silver"));
		CURRENCIES.add(new MediumOfExchange("Peer-to-peer digital currency", "XBT", -1, BigDecimal.TEN.pow(-8, MathContext.DECIMAL128).scale(), "Bitcoin", "\u20BF"));
//		CURRENCIES.add(new MediumOfExchange("Peer-to-peer digital currency", "BTC", -1, BigDecimal.TEN.pow(-8, MathContext.DECIMAL128).scale(), "Bitcoin", "\u20BF"));
	}

	private static java.util.Calendar get(int day, int month, int year) {
		return new java.util.GregorianCalendar(year, month, day);
	}

	private static BigDecimal get(String val) {
		return new BigDecimal(val);
	}
	/*
	 * Date: 13 Aug 2021-----------------------------------------------------------
	 * Time created: 14:21:50---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base.converter--------------------------------------------
	 * ---- Project: LatestPoject2------------------------------------------------
	 * File: Currencies.java------------------------------------------------------
	 * Class name: Website------------------------------------------------
	 */
	/**
	 * A representation of a website that does conversions between currencies.
	 * <p>
	 * The class itself can traverse HTML docs and retrieve values relating to the
	 * present conversion.
	 * 
	 * @implSpec relies on the jsoup api.
	 * @implSpec An internet connection may be required for this class to function.
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 */
	public static abstract class Website {

		/*
		 * Date: 13 Aug 2021-----------------------------------------------------------
		 * Time created: 16:48:00--------------------------------------------
		 */
		/**
		 * Runs a search through the specified url (website) and locates the element
		 * using the attribute "toClassName" and stores the content of the element in
		 * the given list at index 0 as a string. The content found is expected to be a
		 * number.
		 * 
		 * @param url         the uniform resource locator of the website to be searched
		 * @param toClassName an attribute of the tag of element where the value to be
		 *                    retrieved is residing.
		 * @param l           a {@code List} object to store the result.
		 * @throws RuntimeException specifically a {@link UnknownURLException} if the
		 *                          url provided is not well-formed.
		 * @throws RuntimeException specifically a {@link BadHttpResponseException} if
		 *                          the satus code sent by the server is 404 or some
		 *                          invalid code apart from 200.
		 * @throws RuntimeException specifically a {@link MimeTypeException} if the mime
		 *                          type is not valid, e.g is not "text/html".
		 * @throws RuntimeException specifically a {@link ServerTimedOutException} if
		 *                          the {@link #TIME_OUT timeout} expires.
		 * @throws Exception        specifically an {@link IOException} as thrown by the
		 *                          jsoup code within if there is a certain exception on
		 *                          the website server.
		 * 
		 */
		protected static void search(String url, String toClassName, List<String> l) {
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT).referrer(GOOGLE).timeout(TIME_OUT);
			try {
				Document htmlDocument = connection.get();
				/*
				 * Already checked by connection.get()
				 */
//				if (connection.response().statusCode() == 200)
//					if (!connection.response().contentType().contains("text/html"))
//						return;
				Elements body = htmlDocument.body().children();
				traverseDocument(body, toClassName, l);
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
					e.printStackTrace();
					return;
//					System.exit(1);
				}
				ioe.printStackTrace();
			}
		}

		/*
		 * Date: 13 Aug 2021-----------------------------------------------------------
		 * Time created: 17:11:42--------------------------------------------
		 */
		/**
		 * Called within {@link #search(String, String, List)} to locate and retrieve
		 * the value inside the given element. The given element and all it's children
		 * is searched and the result is dumped in the provided list.
		 * 
		 * @param elmt     an HTML element inside a tag as specified in the jsoup API.
		 * @param toString any unique id or class name in the tag portion of the element
		 *                 to be searched.
		 * @param list     a {@code List} that will contain the result of the traversal
		 *                 after this method returns at it's first index except
		 *                 retrieval failed in which case this method is idempotent.
		 */
		protected static void traverseDocument(Elements elmt, String toString, List<String> list) {
			for (Element e : elmt) {
				if (e.className().startsWith(toString)) {
					list.add(0, e.text());
					return;
				}
				if (e.childrenSize() >= 1)
					traverseDocument(e.children(), toString, list);

			}
		}

		/**
		 * A string constant used as a default user agent for connecting to generic
		 * sites. Without the user agent and {@link #GOOGLE referrer}, an invalid
		 * webpage or response may be sent to the user.
		 */
		public static final String USER_AGENT = HttpConnection.DEFAULT_UA;
		/**
		 * A string constant used as a valid user agent (although uses old
		 * specifications such as windows vista) for connecting to sites. Without the
		 * user agent and {@link #GOOGLE referrer}, an invalid webpage or response may
		 * be sent to the user.
		 */
		public static final String USER_AGENT_2 = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
		/**
		 * A string constant used as a default referrer for connecting generic sites.
		 * Without the {@link #USER_AGENT user agent} and referrer, an invalid webpage
		 * or response may be sent to the user.
		 */
		public static final String GOOGLE = "http://www.google.com";
//		public static final String JSOUP_CLIENT = "Jsoup client";
		/**
		 * A constant representing the deadline for connecting to a server before a
		 * timeout. This value is 2 minutes in milliseconds.
		 */
		public static final int TIME_OUT = TemporalUnit.MINUTE
				.convert(new BigDecimal("2"), TemporalUnit.MILLISECOND, MathContext.DECIMAL32).intValue();
		/**
		 * Constant for the status code of "OK" sent by server to which connection is
		 * being requested as a response to mean that there is a valid html to be
		 * received by a requester.
		 */
		public static final int OK_CODE = 200;
		/**
		 * The {@code MathContext} object used for immediate or general rounding of rates and figures
		 */
		protected static final MathContext DEF = new MathContext(Calculator.DEFAULT_PRECISION, RoundingMode.HALF_EVEN);

		/**
		 * The <b>U</b>niform <b>R</b>esource <b>L</b>ocator of this {@code Website} as
		 * a string. This is not the same as the <b>U</b>niform <b>R</b>esource
		 * <b>I</b>dentifier (URI) which is displayed on the address bar of browsers.
		 * 
		 * <p>
		 * The actual value is the page address where the conversion will take place.
		 * More text will be concatenated with this value for the complete location.
		 * </p>
		 */
		private final String url;
		/**
		 * A list of incompatible {@code MediumOfExchange} object which this
		 * {@code Website} cannot convert.
		 */
		private final List<MediumOfExchange> incompatibleCurrencies;

		/*
		 * Date: 15 Aug 2021-----------------------------------------------------------
		 * Time created: 21:14:14---------------------------------------------------
		 */
		/**
		 * Initialises this object with the specified values.
		 * 
		 * @param baseUrl                the URL of the page that the conversion takes
		 *                               place. This is not the same as the url where
		 *                               conversions are already done.
		 * @param incompatibleCurrencies the list of {@code MediumOfExchange} objects
		 *                               that cannot be converted by this class. The
		 *                               resulting list specified by {@link #getIncompatibleCurrencies()}
		 *                               is an unmodifiable list.
		 */
		protected Website(String baseUrl, List<MediumOfExchange> incompatibleCurrencies) {
			this.url = baseUrl;
			this.incompatibleCurrencies = Collections.unmodifiableList(incompatibleCurrencies);
		}

		/*
		 * Date: 15 Aug 2021-----------------------------------------------------------
		 * Time created: 21:19:10--------------------------------------------
		 */
		/**
		 * Returns the base <b>U</b>niform <b>R</b>esource <b>L</b>ocator of this
		 * {@code Website} as a string. This is not the same as the <b>U</b>niform
		 * <b>R</b>esource <b>I</b>dentifier (URI) which is displayed on the address bar
		 * of browsers.
		 * <p>
		 * The actual value returned is the page address where the conversion will take
		 * place. More text will be concatenated with this value for the complete
		 * location of the desired conversion because conversion may require unique
		 * addresses.
		 * </p>
		 * 
		 * @return the base url of this {@code Website}.
		 */
		public final String getUrl() {
			return url;
		}

		/*
		 * Most Recent Date: 16 Jul 2021-----------------------------------------------
		 * Most recent time created: 14:47:43--------------------------------------
		 */
		/**
		 * Extracts the numeric value within the argument with any trailing and leading
		 * text removed and returns the value as a {@code BigDecimal}. This meant to be
		 * is called by
		 * {@link #convert(MediumOfExchange, BigDecimal, MediumOfExchange)}.
		 * 
		 * @param rawText a string containing leading and trailing HTML code.
		 * @return a {@code BigDecimal} parsed from the given string.
		 * @throws RuntimeException specifically a {@code NullPointerException} if the
		 *                          argument is {@code null}.
		 * @throws RuntimeException specifically a {@code NumberFormatException} if the
		 *                          value is not a number that can be parsed via the
		 *                          {@code BigDecimal} constructor or the decimal
		 *                          notation as specified by the {@code NumberFormat}
		 *                          class.
		 */
		protected BigDecimal sanitize(String rawText) {
			if (rawText == null)
				new NullException(String.class);
			Scanner sc = new Scanner(rawText.trim());
			if (sc.hasNextBigDecimal()) {
				BigDecimal ans = sc.nextBigDecimal();
				sc.close();
				return ans;
			}
			sc.close();
			new ValueFormatException(ExceptionMessage.NOT_A_NUMBER, rawText);
			/*
			 * For formality
			 */
			throw new NumberFormatException(
					MessageFormat.format(ExceptionMessage.NOT_A_NUMBER.getLocalizedMessage(), rawText));
		}

		/*
		 * Date: 15 Aug 2021-----------------------------------------------------------
		 * Time created: 21:44:38--------------------------------------------
		 */
		/**
		 * Returns a {@code List} of {@code MediumOfExchange} objects that canno be
		 * converted by code this}.
		 * 
		 * @return a {@code List} of {@code MediumOfExchange} that will be ignored when
		 *         conversion is requested.
		 */
		public final List<MediumOfExchange> getIncompatibleCurrencies() {
			return incompatibleCurrencies;
		}

		/*
		 * Date: 15 Aug 2021-----------------------------------------------------------
		 * Time created: 21:47:33--------------------------------------------
		 */
		/**
		 * Converts a given value specified by the {@code BigDecimal} argument from one
		 * currency to another as specified by from and to respectively.
		 * 
		 * @param from the {@code MediumOfExchange} from which the conversion is being
		 *             made.
		 * @param x    the value to be converted.
		 * @param to   the {@code MediumOfExchange} to which the conversion is being
		 *             made.
		 * @return x converted from the first argument to the third argument.
		 */
		public abstract BigDecimal convert(MediumOfExchange from, BigDecimal x, MediumOfExchange to);
	}

	/*
	 * Date: 13 Aug 2021-----------------------------------------------------------
	 * Time created: 13:49:24---------------------------------------------------
	 * Package:
	 * mathaid.calculator.base.converter--------------------------------------------
	 * ---- Project: LatestPoject2------------------------------------------------
	 * File: Currencies.java------------------------------------------------------
	 * Class name: MediumOfExchange------------------------------------------------
	 */
	/**
	 * A full-fledged currency (or form of monetary instrument) defined as a medium
	 * of exchange of any country/organisation that may have a name as specified by
	 * {@link #getDisplayName}, the user (usually a country or organisation
	 * specified by {@link #getCountry}), an ISO4217 code (specified by
	 * {@link #getIsoCode}) and a numeric code (specified by
	 * {@link #getNumericCode}). A symbol (specified by {@link #getGenericSymbol})
	 * and decimal places (specified by {@link #getFractionalDigits}) may also be
	 * provided else {@code null} may be assigned to the symbol and the default 2
	 * may be used for the number of decimal places.
	 * <p>
	 * The {@code Comparable} interface is implemented for numerical comparison for
	 * purposes such as in a map etc.
	 * </p>
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 */
	public static final class MediumOfExchange implements Comparable<MediumOfExchange>, java.io.Serializable {

		/*
		 * Date: 18 Apr 2022----------------------------------------------------------- 
		 * Time created: 14:29:35--------------------------------------------------- 
		 */
		/**
		 * Field for
		 */
		private static final long serialVersionUID = -3658846303924700423L;
		/*
		 * Date: 15 Jul 2021-----------------------------------------------------------
		 * Time created: 07:35:40---------------------------------------------------
		 */
		/**
		 * The name of this {@code MediumOfExchange}. May return {@code null}.
		 * 
		 * @return the display name of this {@code MediumOfExchange}.
		 */
		public final String getDisplayName() {
			return displayName;
		}

		/*
		 * Date: 15 Jul 2021-----------------------------------------------------------
		 * Time created: 07:35:40---------------------------------------------------
		 */
		/**
		 * Returns the 3 digit ISO4217 alpha code for this {@code MediumOfExchange}.
		 * 
		 * @return the alpha code.
		 */
		public final String getIsoCode() {
			return isoCode;
		}

		/*
		 * Date: 15 Jul 2021-----------------------------------------------------------
		 * Time created: 07:35:40---------------------------------------------------
		 */
		/**
		 * The number of decimal places a value in that {@code MediumOfExchange} may be
		 * displayed in. For example, figures in the US dollar are displayed using 2 d.p
		 * (decimal places) and figures in Japanese yen are displayed as integers.
		 * 
		 * @return the number of decimal places a figure may in this
		 *         {@code MediumOfExchange} may be displayed in.
		 */
		public final int getFractionalDigits() {
			return fractionalDigits;
		}

		/*
		 * Date: 15 Jul 2021-----------------------------------------------------------
		 * Time created: 07:35:40---------------------------------------------------
		 */
		/**
		 * Returns the numeric code for this {@code MediumOfExchange}.
		 * 
		 * @return the numeric code for {@code this}.
		 */
		public final int getNumericCode() {
			return numericCode;
		}

		/*
		 * Date: 15 Jul 2021-----------------------------------------------------------
		 * Time created: 07:35:40---------------------------------------------------
		 */
		/**
		 * Gets the symbol of {@code this}. This may return the
		 * {@link #getDisplayName()} or even {@code null} for some currencies.
		 * 
		 * @return the symbol which may be a single character, string or null as the
		 *         case may be.
		 */
		public final String getGenericSymbol() {
			return genericSymbol;
		}

		/*
		 * Date: 15 Jul 2021-----------------------------------------------------------
		 * Time created: 07:35:40---------------------------------------------------
		 */
		/**
		 * Returns the name of the country/organisation that issued this
		 * {@code MediumOfExchange}.
		 * 
		 * @return the name of the country/organisation that uses this
		 *         {@code MediumOfExchange}.
		 */
		public final String getCountry() {
			return country;
		}

		/*
		 * Date: 23 Apr 2022----------------------------------------------------------- 
		 * Time created: 16:48:46--------------------------------------------
		 */
		/**
		 * Gets the date from which this {@code MediumOfExchange} was created.
		 * @return the date of creation of this currency as a
		 * {@code java.util.Calendar} instance. May return a {@code null} value.
		 */
		public final java.util.Calendar getDateCreated() {
			return dateCreated;
		}
		
		/*
		 * Date: 23 Apr 2022----------------------------------------------------------- 
		 * Time created: 16:50:59--------------------------------------------
		 */
		/**
		 * Gets the date at which this {@code MediumOfExchange} was replaced, abandoned or revalued.
		 * @return the date of replacement, abandonment or revaluation as a
		 * {@code java.util.Calendar} instance. May return a {@code null} value for instances that
		 * still in circulation or a future date for those that have their deprecation fixed in the
		 * future.
		 */
		public final java.util.Calendar getDateDeprecated(){
			return dateDeprecated;
		}
		
		/*
		 * Date: 23 Apr 2022----------------------------------------------------------- 
		 * Time created: 16:54:38--------------------------------------------
		 */
		/**
		 * The 3-letter I.S.O 4217 code of the currency that replaced this one.
		 * @return the currency that is currently in use in place of this one as a
		 * {@code String} instance. May return a {@code null} value if
		 * {@link #getDateDeprecated()} is null.
		 */
		public final String getRelocatedToIso() {
			return newCurrency;
		}
		
		/*
		 * Date: 16 Apr 2022----------------------------------------------------------- 
		 * Time created: 13:02:34--------------------------------------------
		 */
		/**
		 * The date the rate was 'pegged' or 'fixed'
		 * @return the date of pegging as a {@code java.util.Calendar} instance. May
		 * return a {@code null} value
		 */
		public final java.util.Calendar getDatePegged(){
			return datePegged;
		}
		
		/*
		 * Date: 23 Apr 2022----------------------------------------------------------- 
		 * Time created: 16:57:55--------------------------------------------
		 */
		/**
		 * Gets the I.S.O 4217 code of the currency with which this currency is 'pegged'.
		 * @return the iso name of the currency that this is pegged to as a String. May
		 * return a null value.
		 */
		public final String peggedWith() {
			return peggedWith;
		}
		
		/*
		 * Date: 23 Apr 2022----------------------------------------------------------- 
		 * Time created: 17:00:27--------------------------------------------
		 */
		/**Returns the fixed rate at which this is 'pegged'. Pegged rates are abandoned all the
		 * time, so this may return a null value.
		 * @return the fixed rate by which this was pegged as a {@code BigDecimal}.
		 * May return a {@code null} value.
		 */
		public final BigDecimal peggedRate() {
			return peggedRate;
		}
		
		/*
		 * Most Recent Date: 13 Aug 2021-----------------------------------------------
		 * Most recent time created: 13:54:34--------------------------------------
		 */
		/**
		 * Compares the numeric code of {@code this} and the argument and returns the
		 * result. If both are the same then their alpha code is compared and the result
		 * is returned.
		 * 
		 * @param moe the {@code MediumOfExchange} reference to be compared.
		 * @return {@inheritDoc}
		 */
		@Override
		public int compareTo(MediumOfExchange moe) {
			int n = Integer.compare(numericCode, moe.getNumericCode());
			if (n == 0)
				return getIsoCode().compareTo(moe.getIsoCode());
			return n;
		}

		/*
		 * Most Recent Date: 13 Aug 2021-----------------------------------------------
		 * Most recent time created: 13:54:39--------------------------------------
		 */
		/**
		 * A {@code MediumOfExchange} is generally equal to another if it is an instance
		 * of this class and {@code this.compareTo(obj) == 0}.
		 * 
		 * @param obj {@inheritDoc}
		 * @return {@code true} if obj is an instance of this class and
		 *         {@code this.compareTo(obj) == 0} otherwise returns {@code false}.
		 */
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof MediumOfExchange) {
				MediumOfExchange moe = (MediumOfExchange) obj;
				return compareTo(moe) == 0;
			}
			return false;
		}

		/*
		 * Most Recent Date: 13 Aug 2021-----------------------------------------------
		 * Most recent time created: 13:54:43--------------------------------------
		 */
		/**
		 * Returns {@code getIsoCode().hasCode() ^ getNumericCode()}.
		 * 
		 * @return the hash code for this object.
		 */
		@Override
		public int hashCode() {
			return isoCode.hashCode() ^ numericCode;
		}
		
		/*
		 * Date: 23 Apr 2022----------------------------------------------------------- 
		 * Time created: 17:30:36--------------------------------------------------- 
		 */
		/**
		 * Private constructor for constructing a {@code MediumOfExchange} object
		 * initialised with the given arguments.
		 * 
		 * @param country          the country or organisation using this
		 *                         {@code MediumOfExchange} as a {@code String}.
		 * @param iso              the 3 character ISO4217 alpha code as a
		 *                         {@code String} in full upper case else the behaviour
		 *                         of this object inside other methods that use it may
		 *                         be undefined.
		 * @param numericCode      the ISO4217 numeric code as an {@code int}.
		 * @param fractionalDigits the canonical decimal place(s) within the figures.
		 * @param displayName      the actual name of the currency.
		 * 
		 * @see #MediumOfExchange(String, String, int, int, String, String, Calendar, Calendar, String, BigDecimal, Calendar, String) for exception details.
		 */
		private MediumOfExchange(String country, String iso, int numericCode, int fractionalDigits, String displayName) {
			this(country, iso, numericCode, fractionalDigits, displayName, null);
		}
		
		/*
		 * Date: 13 Aug 2021-----------------------------------------------------------
		 * Time created: 13:54:47---------------------------------------------------
		 */
		/**
		 * Private constructor for constructing a {@code MediumOfExchange} object
		 * initialised with the given arguments.
		 * 
		 * @param country          the country or organisation using this
		 *                         {@code MediumOfExchange} as a {@code String}.
		 * @param iso              the 3 character ISO4217 alpha code as a
		 *                         {@code String} in full upper case else the behaviour
		 *                         of this object inside other methods that use it may
		 *                         be undefined.
		 * @param numericCode      the ISO4217 numeric code as an {@code int}.
		 * @param fractionalDigits the canonical decimal place(s) within the figures.
		 * @param displayName      the actual name of the currency.
		 * @param symbol           the {@code symbol} associated with this currency. May
		 *                         be left as {@code null}.
		 * 
		 * @see #MediumOfExchange(String, String, int, int, String, String, Calendar, Calendar, String, BigDecimal, Calendar, String) for exception details.
		 */
		private MediumOfExchange(String country, String iso, int numericCode, int fractionalDigits, String displayName,
				String symbol) {
			this(country, iso, numericCode, fractionalDigits, displayName, symbol, null);
		}

		/*
		 * Date: 23 Apr 2022----------------------------------------------------------- 
		 * Time created: 17:25:02--------------------------------------------------- 
		 */
		/**Private constructor that constructs a {@code MediumOfExchange} object with the specified arguments.
		 * @param country the name of the country, group of countries, entity or organisation that uses this currency.
		 * @param iso a 3-digit string comprising only of latin alphabets that represents the I.S.O 4217 code of allocated to this currency.
		 * @param numericCode the numeric code allocated to this currency by reason of it's iso name.
		 * @param fractionalDigits the total units of a 'subunit' that makes up a single unit in this currency.
		 * @param displayName the display name such as "United States Dollar" for 'USD'.
		 * @param symbol the symbol such as "$" for 'USD'. can be left as null.
		 * @param dateCreated the date this currency was created. Can be left as null.
		 * 
		 * @see #MediumOfExchange(String, String, int, int, String, String, Calendar, Calendar, String, BigDecimal, Calendar, String) for exception details.
		 */
		private MediumOfExchange(String country, String iso, int numericCode, int fractionalDigits, String displayName,
				String symbol, java.util.Calendar dateCreated) {
			this(country, iso, numericCode, fractionalDigits, displayName, symbol, dateCreated, null, null, null, null, null);
		}

		/*
		 * Date: 23 Apr 2022----------------------------------------------------------- 
		 * Time created: 17:24:52--------------------------------------------------- 
		 */
		/**Private constructor that constructs a {@code MediumOfExchange} object with the specified arguments.
		 * @param country the name of the country, group of countries, entity or organisation that uses this currency.
		 * @param iso a 3-digit string comprising only of latin alphabets that represents the I.S.O 4217 code of allocated to this currency.
		 * @param numericCode the numeric code allocated to this currency by reason of it's iso name.
		 * @param fractionalDigits the total units of a 'subunit' that makes up a single unit in this currency.
		 * @param displayName the display name such as "United States Dollar" for 'USD'.
		 * @param symbol the symbol such as "$" for 'USD'. can be left as null.
		 * @param dateCreated the date this currency was created. Can be left as null.
		 * @param dateDeprecated the date this currency stopped being a legal tender (officially). can be left as null as long as {@code dateCreated} is null.
		 * @param newCurrency the iso name of the replacement currency. Can be left as null as long as {@code dateDeprecated} is null.
		 * 
		 * @see #MediumOfExchange(String, String, int, int, String, String, Calendar, Calendar, String, BigDecimal, Calendar, String) for exception details.
		 */
		private MediumOfExchange(String country, String iso, int numericCode, int fractionalDigits, String displayName,
				String symbol, java.util.Calendar dateCreated, java.util.Calendar dateDeprecated, String newCurrency) {
			this(country, iso, numericCode, fractionalDigits, displayName, symbol, dateCreated, null, null, null, dateDeprecated, newCurrency);
		}

		/*
		 * Date: 23 Apr 2022----------------------------------------------------------- 
		 * Time created: 17:22:37--------------------------------------------------- 
		 */
		/**Private constructor that constructs a {@code MediumOfExchange} object with the specified arguments.
		 * @param country the name of the country, group of countries, entity or organisation that uses this currency.
		 * @param iso a 3-digit string comprising only of latin alphabets that represents the I.S.O 4217 code of allocated to this currency.
		 * @param numericCode the numeric code allocated to this currency by reason of it's iso name.
		 * @param fractionalDigits the total units of a 'subunit' that makes up a single unit in this currency.
		 * @param displayName the display name such as "United States Dollar" for 'USD'.
		 * @param symbol the symbol such as "$" for 'USD'. can be left as null.
		 * @param dateCreated the date this currency was created. Can be left as null.
		 * @param peggedWith the iso name of the currency it is pegged or fixed to. can be left as null.
		 * @param peggedRate the fixed or pegged rate. can be left as null as long as {@code peggedWith} is null.
		 * 
		 * @see #MediumOfExchange(String, String, int, int, String, String, Calendar, Calendar, String, BigDecimal, Calendar, String) for exception details.
		 */
		private MediumOfExchange(String country, String iso, int numericCode, int fractionalDigits, String displayName,
				String symbol, java.util.Calendar dateCreated, String peggedWith, BigDecimal peggedRate) {
			this(country, iso, numericCode, fractionalDigits, displayName, symbol, dateCreated, null, peggedWith, peggedRate, null, null);
		}

		/*
		 * Date: 23 Apr 2022----------------------------------------------------------- 
		 * Time created: 17:03:37--------------------------------------------------- 
		 */
		/**Private constructor that constructs a {@code MediumOfExchange} object with the specified arguments.
		 * @param country the name of the country, group of countries, entity or organisation that uses this currency.
		 * @param iso a 3-digit string comprising only of latin alphabets that represents the I.S.O 4217 code of allocated to this currency.
		 * @param numericCode the numeric code allocated to this currency by reason of it's iso name.
		 * @param fractionalDigits the total units of a 'subunit' that makes up a single unit in this currency.
		 * @param displayName the display name such as "United States Dollar" for 'USD'.
		 * @param symbol the symbol such as "$" for 'USD'. can be left as null.s
		 * @param dateCreated the date this currency was created. Can be left as null.
		 * @param datePegged the date this currency was pegged or fixed. can be left as null. 
		 * @param peggedWith the iso name of the currency it is pegged or fixed to. can be left as null.
		 * @param peggedRate the fixed or pegged rate. can be left as null as long as {@code peggedWith} is null.
		 * @param dateDeprecated the date this currency stopped being a legal tender (officially). can be left as null as long as {@code dateCreated} is null.
		 * @param newCurrency the iso name of the replacement currency. Can be left as null as long as {@code dateDeprecated} is null.
		 * @throws IllegalArgumentException - if {@code iso} is &lt; {@code 3}, if {@code dateCreated} is more recent than {@code dateDeprecated} or {@code dateCreated} is more recent than today.
		 * @throws NullPointerException - if any of the stipulation (for null tolerable values) is violated. See parameter description section.
		 */
		private MediumOfExchange(String country, String iso, int numericCode, int fractionalDigits, String displayName,
				String symbol, java.util.Calendar dateCreated, java.util.Calendar datePegged, String peggedWith, BigDecimal peggedRate,
				java.util.Calendar dateDeprecated, String newCurrency) throws IllegalArgumentException, NullPointerException {
			Utility.throwException(new String[] { "country", "iso" }, country, iso);
			if(iso.length() != 3 )
				throw new IllegalArgumentException("ISO name is unknown");
			if(dateCreated == null && dateDeprecated != null)
				throw new NullPointerException("date Created cannot be null while date Deprecated is not");
			if(dateDeprecated != null)
				if(dateCreated.compareTo(dateDeprecated) > 0)
					throw new IllegalArgumentException("date Created cannot be further than date deprecated");
			if(dateCreated != null && dateCreated.compareTo(Calendar.getInstance()) > 0)
				throw new IllegalArgumentException("date Created cannot be further than today");
			if(dateDeprecated != null && newCurrency == null)
				throw new NullPointerException("Must include new currency");
			if(newCurrency != null && dateDeprecated == null)
				throw new NullPointerException("Must include date deprecated");
			this.country = country;
			this.isoCode = iso.toUpperCase();
			this.numericCode = numericCode;
			this.fractionalDigits = fractionalDigits;
			this.displayName = displayName;
			this.genericSymbol = symbol;
			this.dateCreated = dateCreated;
			this.dateDeprecated = dateDeprecated;
			if(newCurrency != null && newCurrency.length() != 3)
				throw new IllegalArgumentException("unknown currency");
			this.newCurrency = newCurrency;
			this.datePegged = datePegged;
			this.peggedWith = peggedWith;
			this.peggedRate = peggedRate;
		}
		
		/**
		 * Field for the country or organisation using this {@code MediumOfExchange}.
		 */
		private final String country;
		/**
		 * The actual name of the currency.
		 */
		private final String displayName;
		/**
		 * The 3 character ISO4217 alpha code as a {@code String} in full upper case
		 * else the behaviour of this object inside other methods that use it may be
		 * undefined.
		 */
		private final String isoCode;
		/**
		 * The canonical decimal place(s) within the figures.
		 */
		private final int fractionalDigits;
		/**
		 * The ISO4217 numeric code as an {@code int}.
		 */
		private final int numericCode;
		/**
		 * The {@code symbol} associated with this currency. May be left as
		 * {@code null}.
		 */
		private final String genericSymbol;
		
		/**
		 * The date this currency was created
		 */
		private final java.util.Calendar dateCreated;
		/**
		 * The date this currency was abandoned
		 */
		private final java.util.Calendar dateDeprecated;
		/**
		 * The date this currency was fixed with another currency
		 */
		private final java.util.Calendar datePegged;
		/**
		 * The iso name of the currency that has replaced this one
		 */
		private final String newCurrency;
		/**
		 * The iso name of the currency that this one is pegged to
		 */
		private final String peggedWith;
		/**
		 * The rate at which this is pegged or fixed
		 */
		private final BigDecimal peggedRate;
	}

	/*
	 * Date: 15 Aug 2021-----------------------------------------------------------
	 * Time created: 13:29:55---------------------------------------------------
	 */
	/**
	 * Prevent usage.
	 */
	private Currencies() {
	}

}
