/**
 * 
 */
/*
 * Date: 27 Aug 2022----------------------------------------------------------- 
 * Time created: 09:51:53---------------------------------------------------  
 * Package: calculator.typeset------------------------------------------------ 
 * Project: InputTestCode------------------------------------------------ 
 * File: package-info.java------------------------------------------------------ 
 * Class name: package-info------------------------------------------------ 
 */
/**
 * Defines various graphical text formatting objects for formatters and cursors.
 * <p>
 * The formatter can be used to format non-editable formulae to Mathematical
 * markup languages such as: TeX, LaTeX, MathML, ASCIIMath, eqn and Unicode. In
 * this documentation, code in these languages are referred to as math code.
 * <p>
 * The {@link mathaid.calculator.base.typeset.Segment Segment} interface can be
 * used to format editable formulae where a
 * {@link mathaid.calculator.base.typeset.Marker Marker} is provided for each
 * 'Segment' to render the segment as editable.
 * 
 * <p>
 * Some examples include:
 * 
 * <pre>
 * <code>
 *	LinkedSegment f = Segments.integral(
 *			Segments.freeVariable("x", "x"),
 *			Digits.toSegment(Utility.i(0), 10, new DigitPunc()),
 *			Segments.constant("\\infty", "infinity"),
 *			Segments.exponential(Segments.freeVariable("x", "x")));
 *	BasicFormatter fm = new BasicFormatter();
 *	RGBA rgba = new RGBA(0xff, 0, 0, 0xff);
 *	fm.getStyleMarker().register(Segment.Type.CONSTANT, rgba, Set.of());
 *	rgba.setBlue(0.5f);
 *	rgba.setRed(0.1f);
 *	rgba.setGreen(0.06f);
 *	fm.getStyleMarker().register(Segment.Type.VAR_FREE, rgba, Set.of());
 *	rgba.setBlue(0f);
 *	rgba.setRed(0f);
 *	rgba.setGreen(0f);
 *	rgba.setOpacity(0.1f);
 *	fm.getStyleMarker().register(Segment.Type.INTEGER, rgba, Set.of());
 *	List&lt;Integer&gt; l = new ArrayList&lt;&gt;(Arrays.asList(-1));
 *	StringBuilder appendable = new StringBuilder();
 *	f.format(appendable, fm, l);
 *	org.jsoup.nodes.Document html = org.jsoup.Jsoup.parse(new File(
 *			new StringBuilder(Device.getProjectPath())
 *				.append("\\res\\graph.html")
 *				.toString()
 *			), "UTF-8");
 *	html.select("div.math").get(0).text(appendable.insert(0, "\\[\r\n").append("\r\n\\]").toString());
 *	Files.write(Paths.get(
 *			new StringBuilder(Device.getProjectPath())
 *				.append("\\res\\graph.html")
 *				.toString()),
 *			html.toString().getBytes());
 * </code>
 * </pre>
 * 
 * <pre>
 * <code>
 *	LinkedSegment f = Digits.toSegment(Utility.fromDecimal(Utility.d(2.3), "201"), Segment.Type.VINCULUM, 1, new DigitPunc());
 *	BasicFormatter fm = new BasicFormatter();
 *  fm.removeMarker(BasicFormatter.CLASS_NAME);
 *  fm.removeMarker(BasicFormatter.STYLE);
 *  fm.removeMarker(BasicFormatter.CSSID);
 *  fm.removeMarker(BasicFormatter.ERROR);
 *  List<Integer> l = new ArrayList<>(Arrays.asList(-1));
 *  StringBuilder appendable = new StringBuilder();
 *  f.format(appendable, fm, l);
 *  if(!fm.getCaretMarker().isMarked()) appendable.append(fm.getCaretMarker().getInputMode().mark("", ForwardRunningMarker.CARET_ID));
 *  org.jsoup.nodes.Document html = org.jsoup.Jsoup.parse(
 *  		new File(new StringBuilder(Device.getProjectPath()).append("\\res\\graph.html").toString()), "UTF-8");
 *  html.select("div.math").get(0).text(appendable.insert(0, "\\[\r\n").append("\r\n\\]").toString());
 *  Files.write(Paths.get(new StringBuilder(Device.getProjectPath()).append("\\res\\graph.html").toString()),
 *  		html.toString().getBytes());
 * </code>
 * </pre>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
package mathaid.calculator.base.typeset;
