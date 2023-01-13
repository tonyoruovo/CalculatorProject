/**
 * 
 */
package mathaid;

/*
 * Date: 18 Jul 2021----------------------------------------------------------- 
 * Time created: 14:58:32---------------------------------------------------  
 * Package: mathaid------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: LocalizableMessage.java------------------------------------------------------ 
 * Class name: LocalizableMessage------------------------------------------------ 
 */
/**
 * An interface for locale-specific strings.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public interface LocalizableMessage {

	/*
	 * Date: 18 Jul 2021-----------------------------------------------------------
	 * Time created: 14:54:10--------------------------------------------
	 */
	/**
	 * Returns the raw source message as a {@code String}. This message should be
	 * returned in the original characters it was written in.
	 * 
	 * @return the raw source {@code String}
	 */
	public String getSourceMessage();

	/*
	 * Date: 18 Jul 2021-----------------------------------------------------------
	 * Time created: 14:56:37--------------------------------------------
	 */
	/**
	 * <p>
	 * Returns the message in the default {@code LocaleSelection} as a
	 * {@code String}.
	 * </p>
	 * <p>
	 * This method functions as though
	 * 
	 * <pre>
	 * LocalizableMessage.getLocalizedMessage(LocaleSelection.defaultSelection())
	 * </pre>
	 * 
	 * was called.
	 * </p>
	 * 
	 * @return the message in the {@code LocaleSelection} specified by
	 *         {@link LocaleSelection#defaultSelection()}
	 */
	public default String getLocalizedMessage() {
		return getLocalizedMessage(LocaleSelection.defaultSelection());
	}

	/*
	 * Date: 18 Jul 2021-----------------------------------------------------------
	 * Time created: 15:08:31--------------------------------------------
	 */
	/**
	 * Returns the message as a {@code String} in the specified
	 * {@code LocalSelection}
	 * 
	 * @param locale a valid {@code LocaleSelection}
	 * @return a {@code String} value of the message in the specified
	 *         {@code LocaleSelection}
	 */
	public String getLocalizedMessage(LocaleSelection locale);

}
