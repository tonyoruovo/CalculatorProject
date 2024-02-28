import mathaid.spi.LangResourceProvider;

/*
 * 
 * Date: 11 Mar 2020<br> Time created: 20:49:48<br> Project name:
 * LatestPoject2<br> File: module-info.java<br> <br>
 * 
 * How to write javadoc comments (in no particular
 * order):***************************** 1. Write the purpose of the comment's
 * subject (target)****************************** 2. expand on said purpose with
 * examples to make it clear**************************** 3. if need be, explain
 * the purpose of the comment itself**************************** 4. explain the
 * concept or abstraction on which the algorithm of said comment is based on
 * *****************************************************************************
 * * 5. Give reasonable references somewhere else within and/or outside the
 * api********* 6. Look into the code itself, pick out special cases and comment
 * on those*********** 7. Give detailed explanation on possible exceptions
 * within the subject*************
 * 
 */
/**
 * Defines foundational packages, classes, interfaces, enums, and annotations for the calculator API.
 *
 * @uses <code><em><strong>org.jsoup</strong></em></code> for the converter API. An example of jsoup in action:
 * 
 *       <pre>
 * <code>
 *	var connection = Jsoup.connect("https://en.wikipedia.org/wiki/List_of_colors_(alphabetical)");
 *	var document = connection.get();
 *	Files.write(Paths.get(Device.getProjectPath(), "\\res\\List_of_colors_(alphabetical).html"),
 *			document.toString().getBytes(), StandardOpenOption.CREATE);
 *			</code>
 *       </pre>
 * 
 *       Another example:
 * 
 *       <pre>
 * <code>
 *
 *	var document = Jsoup.parse(
 *			Paths.get(Device.getProjectPath(), "\\res\\List_of_colors_(alphabetical).html").toFile(), "UTF-8");
 *	
 *	StringBuilder sb = new StringBuilder("&lt;table border=\"1\"&gt;\n&lt;thead&gt;&lt;tr&gt;&lt;th&gt;Name&lt;/th&gt;&lt;th&gt;Hex code&lt;/th&gt;&lt;th&gt;Sample&lt;/th&gt;&lt;/tr&gt;&lt;/thead&gt;\n&lt;tbody&gt;");
 *	
 *	document.select("div &gt; p[title]")
 *	.forEach(x -&gt; {
 *		var t = x.attr("title");
 *		sb.append("&lt;tr&gt;")
 *		.append("&lt;td&gt;&lt;code&gt;")
 *		.append(x.parent().text().trim().toLowerCase())
 *		.append("&lt;/code&gt;&lt;/td&gt;")
 *		.append("&lt;td&gt;")
 *		.append(t.substring(t.indexOf('#')))
 *		.append("&lt;/td&gt;")
 *		.append("&lt;td&gt;")
 *		.append("&lt;div style='width:40px;height:20px;border:.5px solid black;background-color:")
 *		.append(t.substring(t.indexOf('#')))
 *		.append("'&gt;&lt;/div&gt;")
 *		.append("&lt;/td&gt;")
 *		.append("&lt;/tr&gt;\n")
 *		;
 *	})
 *	;
 *	sb.append("&lt;/tbody&gt;\n&lt;/table&gt;");
 *	out.println(sb);
 * </code>
 *       </pre>
 * 
 * @uses <code><em><strong>matheclipse.core</strong></em></code> as a primary CAS for the scientific calculator.
 * @uses <code><em><strong>apfloat</strong></em></code> for general big number functions.
 * @uses <code><em><strong>{@link mathaid.spi.LangResourceProvider}</strong></em></code> for the translation messages.
 * @author Oruovo Anthony Etineakpopha
 */
module CalculatorProject {
	requires java.base;
	requires java.desktop;
	requires matheclipse.core;
	requires apfloat;
	requires hipparchus.core;
	/**
	 */
	requires org.jsoup;
	requires log4j;
	requires jdk.management;
	requires java.xml;

//	requires jas;
	uses LangResourceProvider;

	provides LangResourceProvider with mathaid.spi.ResourceProviderImpl;

}