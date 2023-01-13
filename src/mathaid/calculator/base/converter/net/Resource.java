/**
 * 
 */
package mathaid.calculator.base.converter.net;

/*
 * Date: 2 May 2022----------------------------------------------------------- 
 * Time created: 01:38:59---------------------------------------------------  
 * Package: mathaid.calculator.base.converter.net------------------------------------------------ 
 * Project: LatestProject------------------------------------------------ 
 * File: Resource.java------------------------------------------------------ 
 * Class name: Resource------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface Resource<T extends java.io.Serializable & Comparable<T>> extends Data<T>, Comparable<Resource<?>> {
	String getPath();
	String getFileExtension();
	java.io.File toFile() throws java.io.FileNotFoundException;
	@Override
	int hashCode();
	@Override
	boolean equals(Object o);
}
