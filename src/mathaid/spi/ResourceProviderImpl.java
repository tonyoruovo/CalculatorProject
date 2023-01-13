/**
 * 
 */
package mathaid.spi;

import java.util.spi.AbstractResourceBundleProvider;

/*
 * Date: 2 Jul 2020----------------------------------------------------------- 
 * Time created: 12:47:17---------------------------------------------------  
 * Package: mathaid.calculator.base.spi------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: ResourceProviderImpl.java------------------------------------------------------ 
 * Class name: ResourceProviderImpl------------------------------------------------ 
 */
/**
 * <p>
 * Implementation of basic support for the {@code LangResourceProvider}
 * interface.
 * </p>
 * <p>
 * This object implements the {@code AbstractResourceBundleProvider} constructor
 * as using a properties file as opposed to using a java class file.
 * </p>
 * <p>
 * This interface is required by the static method
 * {@link java.util.ResourceBundle#getBundle(String)} so as not to throw a
 * {@code MissingResourceException}. Because the resource is packaged in named
 * module project (a project that contains at least one named module), a class
 * such as this needs to be present in a package name "spi" and must extend the
 * {@code AbstractResourceBundleProvider} and must implement an interface that
 * resides in the same "spi" package that extends the
 * {@code ResourceBundleProvider} interface. Any module that provide the above
 * specification is a <i>provider module</i>, and any module that creates a
 * {@code ResourceBundle} object is a <i>consumer module</i>. As this module is
 * both a provider and a consumer module, it will support {@code ResourceBundle}
 * loading by adding the following to the module file:
 * 
 * <pre>
 * // no need for this code as this is both a consumer and a provider module
 * // requires mathaid&semi; // if this is the provider module and is not in the same module as this
 * uses LangResourceProvider&semi; // if this is the consumer module
 * 
 * // if this is the consumer module
 * provides LangResourceProvider with mathaid.spi.ResourceProviderImpl;
 * 
 * </pre>
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class ResourceProviderImpl extends AbstractResourceBundleProvider implements LangResourceProvider {

	/*
	 * Date: 2 Jul 2020-----------------------------------------------------------
	 * Time created: 12:47:17---------------------------------------------------
	 */
	/**
	 * Constructs a {@code ResourceProviderImpl} initialising the format parameter
	 * of {@code AbstractResourceBundleProvider} to the @{@code String} object
	 * {@code "java.properties"}
	 */
	public ResourceProviderImpl() {
		super("java.properties");
	}

	/*
	 * Date: 2 Jul 2020-----------------------------------------------------------
	 * Time created: 12:47:17---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param baseName{@inheritDoc}
	 * @param locale{@inheritDoc}
	 * @return the {@code String} object
	 *         <code>"p." + locale.getLanguage() + "." + baseName</code>
	 */
	@Override
	protected String toBundleName(String baseName, java.util.Locale locale) {
		return "p." + locale.getLanguage() + "." + baseName;
	}
}
