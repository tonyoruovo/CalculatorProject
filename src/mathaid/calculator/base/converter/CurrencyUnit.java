/**
 * 
 */
package mathaid.calculator.base.converter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import mathaid.ExceptionMessage;
import mathaid.calculator.base.converter.Currencies.MediumOfExchange;

/*
 * Date: 12 Jul 2021----------------------------------------------------------- 
 * Time created: 12:25:26---------------------------------------------------  
 * Package: mathaid.calculator.base.converter------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: CurrencyUnit.java------------------------------------------------------ 
 * Class name: CurrencyUnit------------------------------------------------ 
 */
/**
 * A class that represents currency exchange units such as US dollar or the
 * Nigerian naira which can be converted to other
 * {@linkplain #supportedCurrencies() supported currencies} using real time
 * data.
 * <p>
 * A {@code CurrencyUnit} object may not be created using a constructor, but
 * with any of the valueOf overloads. This is to prevent arbitrary and
 * unsupported currencies like the Nigerian kobo. Methods such as
 * {@link #getCountryName()} and {@link #getNumericCode()} are for retrieving
 * info about the currency itself.
 * </p>
 * <p>
 * <b>Note:</b> The conversions made in this class requires an internet
 * connection to be present in the resident machine. Also hoping to add an
 * offline mode to prevent instant recalculations (which may trigger some
 * recaptcha HTML on the site) or add some cool down on the
 * {@link #convert(BigDecimal, CurrencyUnit, MathContext)} method.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 */
public class CurrencyUnit implements Convertible<CurrencyUnit, BigDecimal, MathContext> {

	/*
	 * Date: 16 Aug 2021-----------------------------------------------------------
	 * Time created: 13:29:40--------------------------------------------
	 */
	/**
	 * Returns a {@code Set} of all the alpha code (iso4217) of supported
	 * currencies. For example, the US dollar will be represented in this set as
	 * "USD".
	 * <p>
	 * All alpha codes are inserted in alphabetical order. Please note that
	 * historical currencies are intentionally absent, only currencies in active use
	 * are present.
	 * </p>
	 * 
	 * @return a {@code Set} of {@code String} object each representing the alpha
	 *         code of a particular currency.
	 */
	public static Set<String> supportedCurrencies() {
		TreeSet<String> set = new TreeSet<>();
		List<MediumOfExchange> l = Currencies.CURRENCIES;
		for (MediumOfExchange moe : l)
			set.add(moe.getIsoCode());
		return set;
	}

	/*
	 * Date: 16 Aug 2021-----------------------------------------------------------
	 * Time created: 13:37:07--------------------------------------------
	 */
	/**
	 * Returns a valid {@code CurrencyUnit} whose 3 digit numeric code corresponds
	 * with the argument or else throws a {@code java.lang.IllegalArgumentException}
	 * if the argument is not supported.
	 * 
	 * @param numericCode the 3 digit numeric code as specified by ISO4217.
	 * @return a {@code CurrencyUnit} object created from the given argument.
	 * @throws RuntimeException specifically an {@code IllegalArgumentException} if
	 *                          the argument is not for a supported currency.
	 */
	public static CurrencyUnit valueOf(int numericCode) {
		List<MediumOfExchange> l = Currencies.CURRENCIES;
		for (MediumOfExchange moe : l) {
			if (moe.getNumericCode() == numericCode)
				return new CurrencyUnit(moe);
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
	 * Returns a valid {@code CurrencyUnit} created with the 3 letter alpha code
	 * that corresponds to the argument or else throws a
	 * {@code java.lang.IllegalArgumentException} if the arguments are not
	 * supported.
	 * 
	 * @param first the first letter in the currency's alpha code.
	 * @param mid   the second letter in the currency's alpha code.
	 * @param last  the last letter in the currency's alpha code.
	 * @return a {@code CurrencyUnit} object created from the given arguments.
	 * @throws RuntimeException specifically an {@code IllegalArgumentException} if
	 *                          the arguments are not for a supported currency.
	 */
	public static CurrencyUnit valueOf(char first, char mid, char last) {
		List<MediumOfExchange> l = Currencies.CURRENCIES;
		String iso4217Code = String.valueOf(new char[] { first, mid, last }).toUpperCase();
		for (MediumOfExchange moe : l) {
			if (moe.getIsoCode().compareTo(iso4217Code) == 0)
				return new CurrencyUnit(moe);
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
	 * Returns a valid {@code CurrencyUnit} created with the name of the country or
	 * establishment using such a currency or else throws a
	 * {@code java.lang.IllegalArgumentException} if the argument is not a supported
	 * country/organisation.
	 * 
	 * @param countryName a name of a valid country/organisation.
	 * @return a {@code CurrencyUnit} object created from the corresponding
	 *         country/organisation name.
	 * @see {@linkplain java.util.Currency#getAvailableCurrencies()} for details on
	 *      the syntax of the names.
	 */
	public static CurrencyUnit valueOf(String countryName) {
		List<MediumOfExchange> l = Currencies.CURRENCIES;
		for (MediumOfExchange moe : l) {
			if (moe.getCountry() != null && moe.getCountry().equals(countryName))
				return new CurrencyUnit(moe);
		}
		new mathaid.IllegalArgumentException(ExceptionMessage.UNSUPPORTED_CURRENCY);
		/*
		 * For java formality
		 */
		throw new IllegalArgumentException("Unknown currency");
	}

	/*
	 * Date: 16 Aug 2021-----------------------------------------------------------
	 * Time created: 14:07:28--------------------------------------------
	 */
	/**
	 * Returns the 3 digit ISO4217 alpha code for this {@code CurrencyUnit}.
	 * 
	 * @return the alpha code.
	 */
	public String getIsoCode() {
		return currency.getIsoCode();
	}

	/*
	 * Date: 16 Aug 2021-----------------------------------------------------------
	 * Time created: 14:09:42--------------------------------------------
	 */
	/**
	 * Returns the numeric code for this {@code CurrencyUnit}.
	 * 
	 * @return the numeric code for {@code this}.
	 */
	public int getNumericCode() {
		return currency.getNumericCode();
	}

	/*
	 * Date: 16 Aug 2021-----------------------------------------------------------
	 * Time created: 14:10:19--------------------------------------------
	 */
	/**
	 * Returns the name of the country/organisation that uses this
	 * {@code CurrencyUnit}.
	 * 
	 * @return the name of the country/organisation that uses this
	 *         {@code CurrencyUnit}.
	 */
	public String getCountryName() {
		return currency.getCountry();
	}

	/*
	 * Most Recent Date: 12 Jul 2021-----------------------------------------------
	 * Most recent time created: 12:25:38--------------------------------------
	 */
	/**
	 * Returns the value of the given {@code BigDecimal} converted from {@code this}
	 * to the {@code CurrencyUnit} argument. The given {@code MathContext} is not
	 * necessary for numeric approximations and can be left as {@code null}, this is
	 * because the fractional digits of the currency is what is used for the decimal
	 * place of the value returned. Although some currencies do not have decimal
	 * places specified (such as the ADB Unit of Account), so a decimal place of 4
	 * is used.
	 * 
	 * @param value   the value to be converted.
	 * @param type    the destination of the conversion.
	 * @param context can be left as {@code null}.
	 * @return the given value converted from {@code this} to the type argument.
	 */
	@Override
	public BigDecimal convert(BigDecimal value, CurrencyUnit type, MathContext context) {
		for(Currencies.Website w : websites) {
			if(!w.getIncompatibleCurrencies().contains(type.currency))
				try {
					BigDecimal val = w.convert(currency, value, type.currency);
					if(val != null)
						return val;
				} catch (Throwable t) {
					t.printStackTrace();
				}
		}
		return null;
	}

	/*
	 * public Map<String, BigDecimal> convert(BigDecimal units) { Map<String,
	 * BigDecimal> map = new HashMap<>(); for(Currencies.MediumOfExchange currency :
	 * Currencies.CURRENCIES) { if(currency.compareTo(this.currency) == 0) continue;
	 * for(Currencies.Website w : websites) {
	 * if(w.getIncompatibleCurrencies().contains(this.currency)) continue; try {
	 * BigDecimal value = w.convert(this.currency, units, currency); if(value !=
	 * null) { map.put(currency.getIsoCode(), value);
	 * System.out.println(currency.getCountry() + "-" + currency.getIsoCode() + ": "
	 * + value); } else { System.out.println(currency.getCountry() + "-" +
	 * currency.getIsoCode() + ": FAILED"); continue; } } catch (Throwable t) {
	 * System.out.println(currency.getCountry() + "-" + currency.getIsoCode() +
	 * ": FAILED"); t.printStackTrace(); } } } return map; }
	 */
	/*
	 * Date: 16 Aug 2021-----------------------------------------------------------
	 * Time created: 14:22:14---------------------------------------------------
	 */
	/**
	 * Sole constructor to instantiate a {@code CurrencyUnit} using a specified
	 * {@code MediumOfExchange} object. User instantiation is prevented to eliminate
	 * invalid currencies.
	 * 
	 * @param currency the value containing all the fields that can be used for
	 *                 creating a valid currency unit.
	 */
	private CurrencyUnit(MediumOfExchange currency) {
		this.currency = currency;
//		xe = new XeDotCom();
//		ws = new WiseDotCom();
		websites = new ArrayList<>();
		websites.add(new FxtopDotCom());
		websites.add(new FawazAhmedCDNRepo());
		websites.add(new XeDotCom());
		websites.add(new WiseDotCom());
		websites.add(new EuropeanCentralBankFeed());
	}

	/**
	 * The backing object that powers this class.
	 */
	private final MediumOfExchange currency;

	/**
	 * A value representing xe.com, that will assist with converting single units.
	 */
//	private final XeDotCom xe;
	/**
	 * A value representing wise.com, that will assist with converting single units.
	 */
//	private final WiseDotCom ws;
	/**
	 * A list of all the currency conversion sites.
	 */
	private final List<Currencies.Website> websites; 

}
