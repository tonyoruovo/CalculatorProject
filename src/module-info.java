import mathaid.spi.LangResourceProvider;

/*
 * 
 * Date: 11 Mar 2020<br>
 * Time created: 20:49:48<br>
 * Project name: LatestPoject2<br>
 * File: module-info.java<br>
 * <br>
 * 
 * How to write javadoc comments (in no particular order):*****************************
 * 1. Write the purpose of the comment's subject (target)******************************
 * 2. expand on said purpose with examples to make it clear****************************
 * 3. if need be, explain the purpose of the comment itself****************************
 * 4. explain the concept or abstraction on which the algorithm of said comment is based
 **** on ******************************************************************************
 * 5. Give reasonable references somewhere else  within and/or outside the api*********
 * 6. Look into the code itself, pick out special cases and comment on those***********
 * 7. Give detailed explanation on possible exceptions within the subject*************
 * 
 */
/**
 * Defines foundational packages, classes, interfaces, enums, and annotations
 * for the calculator API.
 *
 * @uses org.jsoup for the converter API.
 * @uses matheclipse.core for the scientific calculator.
 * @uses apfloat for general big number functions.
 * @uses {@link mathaid.spi.LangResourceProvider} for the translation messages.
 * @author Oruovo Anthony Etineakpopha
 */
module CalculatorProject {
	requires java.base;
	requires java.desktop;
	requires matheclipse.core;
	requires apfloat;
	requires hipparchus.core;
	requires org.jsoup;
//	requires jas;
	uses LangResourceProvider;

    provides LangResourceProvider with mathaid.spi.ResourceProviderImpl;
    
}