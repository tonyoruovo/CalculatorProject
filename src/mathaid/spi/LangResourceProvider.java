/**
 * 
 */
package mathaid.spi;

import java.util.spi.ResourceBundleProvider;

/*
 * Date: 2 Jul 2020----------------------------------------------------------- 
 * Time created: 12:46:14---------------------------------------------------  
 * Package: mathaid.calculator.base.spi------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: LangResourceProvider.java------------------------------------------------------ 
 * Class name: LangResourceProvider------------------------------------------------ 
 */
/**
 * <p>
 * A service provider interface for {@code ResourceBundle} objects.
 * </p>
 * <p>
 * This interface is required by the static method
 * {@link java.util.ResourceBundle#getBundle(String)} so as not to throw a
 * {@code MissingResourceException}.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @see ResourceProviderImpl
 * 
 */
public interface LangResourceProvider extends ResourceBundleProvider {
}
