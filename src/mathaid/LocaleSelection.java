/**
 * 
 */
package mathaid;

import java.text.Collator;
import java.util.Arrays;
import java.util.Locale;

/*
 * Date: 18 Jul 2021----------------------------------------------------------- 
 * Time created: 15:15:00---------------------------------------------------  
 * Package: mathaid------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: LocaleSelection.java------------------------------------------------------ 
 * Class name: LocaleSelection------------------------------------------------ 
 */
/**
 * A {@code java.util.Locale} wrapper.
 * <p>
 * A wrapper is needed to prevent illegal locales in the mathaid api.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public final class LocaleSelection {

	private static final Locale DEFAULT = new Locale("en", "GB");
	private static Locale internalDefault;

	/*
	 * Date: 18 Jul 2021-----------------------------------------------------------
	 * Time created: 15:15:00---------------------------------------------------
	 */
	/**
	 * Initialises this object with the input argument. The following corresponds to
	 * the argument:
	 * <ul>
	 * <li><b>0</b> - &lsquo;en&rsquo; for language &lsquo;GB&rsquo; for country
	 * <li><b>1</b> - &lsquo;zh&rsquo; for language &lsquo;CN&rsquo; for country
	 * <li><b>2</b> - &lsquo;es&rsquo; for language &lsquo;ES&rsquo; for country
	 * <li><b>3</b> - &lsquo;ar&rsquo; for language &lsquo;SA&rsquo; for country
	 * <li><b>4</b> - &lsquo;hi&rsquo; for language &lsquo;IN&rsquo; for country
	 * <li><b>5</b> - &lsquo;fr&rsquo; for language &lsquo;FR&rsquo; for country
	 * </ul>
	 * 
	 * @param locale one of the above listed {@code int} values
	 */
	private LocaleSelection(final int locale) {

		Locale l;
		switch (locale) {
		case 1:
//			l = Locale.SIMPLIFIED_CHINESE;
			l = new Locale("zh", "CN");
			if (Arrays.asList(Locale.getAvailableLocales()).contains(l)) {
				this.locale = l;
				return;
			}
			break;
		case 2:
//			l = Locale.forLanguageTag("es-ES Spanish Spain");
			l = new Locale("es", "ES");
			if (Arrays.asList(Locale.getAvailableLocales()).contains(l)) {
				this.locale = l;
				return;
			}
			break;
		case 3:
//			l = Locale.forLanguageTag("ar-SA Arabic Saudi Arabia");
			l = new Locale("ar", "SA");
			if (Arrays.asList(Locale.getAvailableLocales()).contains(l)) {
				this.locale = l;
				return;
			}
			break;
		case 4:
//			l = Locale.forLanguageTag("hi-IN Hindi India");
			l = new Locale("hi", "IN");
			if (Arrays.asList(Locale.getAvailableLocales()).contains(l)) {
				this.locale = l;
				return;
			}
			break;
		case 5:
//			l = Locale.forLanguageTag("fr-FR French France");
			l = new Locale("fr", "FR");
			if (Arrays.asList(Locale.getAvailableLocales()).contains(l)) {
				this.locale = l;
				return;
			}
			break;
		default:
		}
		this.locale = DEFAULT;
	}

	/*
	 * Date: 22 Jul 2021-----------------------------------------------------------
	 * Time created: 12:43:44--------------------------------------------
	 */
	/**
	 * Returns the internal locale identifier for objects such as those found in the
	 * <code>java.text</code> package. This {@code Locale} object returned
	 * corresponds with this object.
	 * 
	 * @return a {@code Locale} object used internally.
	 */
	public Locale asJavaLocale() {
		return locale;
	}

	/*
	 * Date: 20 Jul 2021-----------------------------------------------------------
	 * Time created: 14:43:22--------------------------------------------
	 */
	/**
	 * Sets the {@code LocaleSelection} to one of the supported values or a default
	 * value if the argument is invalid. The value set by this method will directly
	 * affect values returned by {@link #defaultSelection()}
	 * 
	 * @param locale one of the following {@code int} values:
	 *               <ul>
	 *               <li><b>0</b> - &lsquo;en&rsquo; for language &lsquo;GB&rsquo;
	 *               for country
	 *               <li><b>1</b> - &lsquo;zh&rsquo; for language &lsquo;CN&rsquo;
	 *               for country
	 *               <li><b>2</b> - &lsquo;es&rsquo; for language &lsquo;ES&rsquo;
	 *               for country
	 *               <li><b>3</b> - &lsquo;ar&rsquo; for language &lsquo;SA&rsquo;
	 *               for country
	 *               <li><b>4</b> - &lsquo;hi&rsquo; for language &lsquo;IN&rsquo;
	 *               for country
	 *               <li><b>5</b> - &lsquo;fr&rsquo; for language &lsquo;FR&rsquo;
	 *               for country
	 *               </ul>
	 * @return a {@code LocaleSelection} for chaining purposes. This is the same
	 *         object returned by {@code defaultSelection()}
	 */
	public static LocaleSelection setDefaultSelection(final int locale) {
		switch (locale) {
		case 1:
			internalDefault = new Locale("zh", "CN");
			break;
		case 2:
			internalDefault = new Locale("es", "ES");
			break;
		case 3:
			internalDefault = new Locale("ar", "SA");
			break;
		case 4:
			internalDefault = new Locale("hi", "IN");
			break;
		case 5:
			internalDefault = new Locale("fr", "FR");
			break;
		default:
		}
		return defaultSelection();
	}

	/*
	 * Date: 19 Jul 2021-----------------------------------------------------------
	 * Time created: 14:17:59--------------------------------------------
	 */
	/**
	 * Returns the current value used as a default {@code LocaleSelection}. This is
	 * used by {@code LocalizableMessage} objects to format messages to the user.
	 * This default value can be set via {@link #setDefaultSelection(int)}, and if
	 * {@link #setDefaultSelection(int)} was ever called before this method, then
	 * the value returned by {@code setDefaultSelection()} is returned.
	 * 
	 * @return the default {@code LocaleSelection}
	 * @see {@link #setDefaultSelection}
	 */
	public static LocaleSelection defaultSelection() {

		if (internalDefault == null)
			internalDefault = Locale.getDefault();

		String lang = internalDefault.getLanguage();
		Collator c = Collator.getInstance(Locale.US);
		if (c.compare(lang, new Locale("zh", "CN").getLanguage()) == 0)
			return new LocaleSelection(1);
		if (c.compare(lang, new Locale("es", "ES").getLanguage()) == 0)
			return new LocaleSelection(2);
		if (c.compare(lang, new Locale("ar", "SA").getLanguage()) == 0)
			return new LocaleSelection(3);
		if (c.compare(lang, new Locale("hi", "IN").getLanguage()) == 0)
			return new LocaleSelection(4);
		if (c.compare(lang, new Locale("fr", "FR").getLanguage()) == 0)
			return new LocaleSelection(5);
		return new LocaleSelection(0);
	}

	/**
	 * The internal locale to legitimise languages and countries
	 */
	final Locale locale;
}
