/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * Date: 23 Nov 2023 -----------------------------------------------------------
 * Time created: 12:32:28 ---------------------------------------------------
 * Package: mathaid.calculator.base.typeset ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: StyleMarker.java ------------------------------------------------------
 * Class name: StyleMarker ------------------------------------------------
 * 
 * Also, the hsv conversions (to and from rgb) are implemented from David H's answer
 * to this StackOverflow question:
 * https://stackoverflow.com/questions/3018313/algorithm-to-convert-rgb-to-hsv-and-hsv-to-rgb-in-range-0-255-for-both
 */
/**
 * The {@code Marker} within a {@code Formatter} that creates in-line CSS within
 * nodes so as style them.
 * <p>
 * Only certain font effects and colouring are supported styling for now.
 * <p>
 * Styling is achieved by using style bits. The style bits is a 64 bit
 * (<code>long</code>) value where the first least 32 bits contains font styling
 * and other effect data and the last least 32 bits contain colour data include
 * rgb and alpha values. Currently, only 7 font styles (specified by
 * {@link FontStyle}) are supported (excluding an extra
 * {@linkplain FontStyle#NORMAL neutral font style}) which are located at the
 * first 7 least significant bits (low-order). Within the colour data, the last
 * 8 most significant bits (high-order) is the alpha/opacity value. The
 * remaining bits are equally split between the red, green and blue values (from
 * high to low order respectively) which means each is 8 bit long.
 * <p>
 * A style map is used for configuring the exact styles for a particular
 * {@linkplain Segment#getType() type} of {@code Segment}. This style map maps the given
 * type to the style bits with which to style that type. A {@code Segment} type is
 * registered to the style map using any of the {@code register} overloads, a
 * colour and font effect may also be specified. When {@link Segment#format} is
 * called, with the type present in the {@code Segment}, the colour and font styles that
 * was registered with this type will be used. A mapping can be invalidated
 * using {@link #unregister(int)}
 * <p>
 * The alpha (opacity) value can take as much as 8 bits. No more, no less. The
 * rgb (opaque) value can take as much as 24 bits. No more, no less. The font
 * style value as much as 32 bits or less, it is the most flexible.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class StyleMarker implements Marker {

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 13:04:06 ---------------------------------------------------
	 * Package: mathaid.calculator.base.typeset
	 * ------------------------------------------------ Project: CalculatorProject
	 * ------------------------------------------------ File: StyleMarker.java
	 * ------------------------------------------------------ Class name: FontStyle
	 * ------------------------------------------------
	 */
	/**
	 * Contains {@code enum} values used for applying mathematical font effects to
	 * parts of segments.
	 * 
	 * @author Oruovo Anthony Etineakpopha
	 * @email tonyoruovo@gmail.com
	 */
	public enum FontStyle {
		/**
		 * Neutral does not apply any math font.
		 */
		NORMAL,
		/**
		 * Applies the TeX font effect {@code \mathbf} to a {@code Segment}, which serves to
		 * make the text <strong>bold</strong>.
		 */
		BOLD("\\mathbf"),
		/**
		 * Applies the TeX font effect {@code \mathit} to a {@code Segment}, which makes the
		 * text <em>italic</em>.
		 */
		ITALIC("\\mathit"),
		/**
		 * Applies the TeX font effect <code>&bsol;underline</code> to a {@code Segment}, which
		 * makes the text <u>underlined</u>.
		 */
		UNDERLINE("\\underline"),
		/**
		 * Applies the TeX font effect <code>&bsol;cancelto</code> to a {@code Segment}, which
		 * makes the text <del>strike-through</del>.
		 * 
		 * @implSpec requires the "cancel" package in MathJax.
		 */
		STRIKE_THROUGH("\\cancelto"),
		/**
		 * Applies the TeX font face <code>&bsol;mathrm</code> to a {@code Segment}, which makes
		 * the text <span style="font-family: 'New Times Roman', serif">roman</span>
		 */
		ROMAN("\\mathrm"),
		/**
		 * Applies the TeX font face <code>&bsol;mathtt</code> to a {@code Segment}, which makes
		 * the text <span style="font-family: 'Lucida Sans Typewriter',
		 * monospace">typewriter</span>
		 */
		MONOSPACE("\\mathtt"),
		/**
		 * Applies the TeX font face <code>&bsol;mathcal</code> to a {@code Segment}, which
		 * makes the text
		 * <span style="font-family: 'Segoe Script', san serif">caligraphic</span>
		 */
		CALIGRAPHIC("\\mathcal");

		/*
		 * Date: 23 Nov 2023 -----------------------------------------------------------
		 * Time created: 14:05:08 ---------------------------------------------------
		 */
		/**
		 * Creates {@link #NORMAL}
		 */
		FontStyle() {
			this("");
		}

		/*
		 * Date: 23 Nov 2023 -----------------------------------------------------------
		 * Time created: 14:05:28 ---------------------------------------------------
		 */
		/**
		 * Constructs a {@code FontStyle} from a given {@code cmd}.
		 * 
		 * @param cmd the command to be used when this effect is applied to the {@code Segment}.
		 */
		FontStyle(String cmd) {
			this.cmd = cmd;
		}

		/*
		 * Date: 23 Nov 2023 -----------------------------------------------------------
		 * Time created: 14:29:39 ---------------------------------------------------
		 */
		/**
		 * Getter for the TeX command used for creating font effects.
		 * 
		 * @return the command used by this {@code FontStyle}.
		 */
		public String getCommand() {
			return cmd;
		}

		/**
		 * The TeX command that actually creates the font effect.
		 */
		private final String cmd;
	}

	/*
	 * Date: 23 Nov 2023 -----------------------------------------------------------
	 * Time created: 14:07:38 ---------------------------------------------------
	 */
	/**
	 * Creates a font style map whose keys are bits where a single bit index is on
	 * and the value is a corresponding {@code FontStyle}.
	 * <p>
	 * The keys are {@code Long} values where all bits are {@code 0} except a bit at
	 * a certain position. The following is a table showing more details:
	 * <table border="1">
	 * <thead>
	 * <tr>
	 * <th>KEY (decimal)</th>
	 * <th><code>FontStyle enum</code> value</th>
	 * <th>64 Bit binary Description (Long)</th>
	 * <th>Bit position</th>
	 * <th>Remarks</th>
	 * </tr>
	 * </thead> <tbody>
	 * <tr>
	 * <td>{@code 0L}</td>
	 * <td>{@link FontStyle#NORMAL}</td>
	 * <td><code>0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000</code></td>
	 * <td>N/A</td>
	 * <td>This is the only mapping with no bit 'on' at any position</td>
	 * </tr>
	 * <tr>
	 * <td>{@code 1L}</td>
	 * <td>{@link FontStyle#BOLD}</td>
	 * <td><code>0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 000<strong>1</strong></code></td>
	 * <td>0</td>
	 * </tr>
	 * <tr>
	 * <td>{@code 2L}</td>
	 * <td>{@link FontStyle#ITALIC}</td>
	 * <td><code>0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 00<strong>1</strong>0</code></td>
	 * <td>1</td>
	 * </tr>
	 * <tr>
	 * <td>{@code 4L}</td>
	 * <td>{@link FontStyle#UNDERLINE}</td>
	 * <td><code>0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0<strong>1</strong>00</code></td>
	 * <td>2</td>
	 * </tr>
	 * <tr>
	 * <td>{@code 8L}</td>
	 * <td>{@link FontStyle#MONOSPACE}</td>
	 * <td><code>0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 <strong>1</strong>000</code></td>
	 * <td>3</td>
	 * </tr>
	 * <tr>
	 * <td>{@code 16L}</td>
	 * <td>{@link FontStyle#ROMAN}</td>
	 * <td><code>0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 000<strong>1</strong> 0000</code></td>
	 * <td>4</td>
	 * </tr>
	 * <tr>
	 * <td>{@code 32L}</td>
	 * <td>{@link FontStyle#CALIGRAPHIC}</td>
	 * <td><code>0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 00<strong>1</strong>0 0000</code></td>
	 * <td>5</td>
	 * </tr>
	 * <tr>
	 * <td>{@code 64L}</td>
	 * <td>{@link FontStyle#STRIKE_THROUGH}</td>
	 * <td><code>0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0<strong>1</strong>00 0000</code></td>
	 * <td>6</td>
	 * </tr>
	 * </tbody>
	 * <table>
	 * <p>
	 * When the {@link StyleMarker#mark} is called, the style to be applied depends
	 * on the position(s) within {@link StyleMarker#getFontStylesAsInt(int)} that
	 * have their bit set as 'on'.
	 * 
	 * @return the font style map.
	 */
	protected static Map<Long, FontStyle> initBasicFontStyles() {
		Map<Long, FontStyle> m = new HashMap<>();
		m.put(1L, FontStyle.BOLD);
		m.put(2L, FontStyle.ITALIC);
		m.put(4L, FontStyle.UNDERLINE);
		m.put(8L, FontStyle.MONOSPACE);
		m.put(16L, FontStyle.ROMAN);
		m.put(32L, FontStyle.CALIGRAPHIC);
		m.put(64L, FontStyle.STRIKE_THROUGH);
		m.put(0L, FontStyle.NORMAL);
		return m;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:30:18 ---------------------------------------------------
	 */
	/**
	 * Invoked by {@link #register(int, long)} to check if the colour bit data is
	 * within the supported range. For each value in the second argument, it checks
	 * whether it is negative or exceeds the first argument then throws an exception
	 * if any if the condition is <code>true</code>.
	 * 
	 * @param limit the max of which values in the second argument will be checked
	 *              against.
	 * @param val   varargs of values to be checked.
	 */
	private static void constrainByException(long limit, long... val) {
		for (int i = 0; i < val.length; i++) {
			if (val[i] < 0L)
				throw new IllegalArgumentException("Cannot be below 0");
			if (val[i] > limit)
				throw new IllegalArgumentException(String.format("Cannot be above %s", limit));
		}
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:36:58 ---------------------------------------------------
	 */
	/**
	 * The main constructor of {@code StyleMarker}. Provides initial style mappings
	 * and defines the supported {@code FontStyles} for the created marker.
	 * 
	 * @param styles          initialises the style map. The keys are the {@code Segment}
	 *                        types. The values are the style bits for that {@code Segment}
	 *                        type.
	 * @param validFontStyles the supported styles. An example of a valid value is
	 *                        {@link #initBasicFontStyles()}.
	 * @see StyleMarker#initBasicFontStyles()
	 */
	protected StyleMarker(Map<Integer, Long> styles, Map<Long, FontStyle> validFontStyles) {
		this.styles = styles;
		this.fs = Collections.unmodifiableMap(validFontStyles);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:46:08 ---------------------------------------------------
	 */
	/**
	 * Creates a {@code StyleMarker} by providing initial style mappings and using
	 * {@link #initBasicFontStyles()} for defining supported font styles.
	 * 
	 * @param styles the initial style mappings to use.
	 */
	public StyleMarker(Map<Integer, Long> styles) {
		this(styles, initBasicFontStyles());
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:50:20 ---------------------------------------------------
	 */
	/**
	 * Creates a {@code StyleMarker} by specifying empty style mappings. This calls
	 * {@link #StyleMarker(Map)} and registers black colour and
	 * {@link FontStyle#ROMAN} for all {@link Segment.Type#NON_DECIMAL} types.
	 */
	public StyleMarker() {
		this(new HashMap<>());
		HashSet<FontStyle> st = new HashSet<>();
		st.add(FontStyle.ROMAN);
		register(Segment.Type.NON_DECIMAL, new RGBA(0.0f, 0.0f, 0.0f, 1.0f), st);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:53:38 ---------------------------------------------------
	 */
	/**
	 * Gets the size of {@link #fs} which represents the number of supported font
	 * style effects.
	 * 
	 * @return the size of {@link #fs}.
	 */
	private int numOfFontStyles() {
		return fs.size();
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:54:40 ---------------------------------------------------
	 */
	/**
	 * Gets an {@code int} where all as many low order bits as are used for font
	 * styling and effect are all set to {@code 1}.
	 * 
	 * @return {@link mathaid.calculator.base.value.FloatAid#getAllOnes(int)} as an
	 *         {@code int} with the number of font styles (specified by
	 *         {@link #numOfFontStyles()}) as argument.
	 */
	private int fontStylesBitMask() {
		return mathaid.calculator.base.value.FloatAid.getAllOnes(numOfFontStyles()).intValue();
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 08:05:29 ---------------------------------------------------
	 */
	/**
	 * Gets the style bits where all the colour data part of the bits are set to
	 * {@code 1}.
	 * 
	 * @return {@code 0xFF_FF_FF_FFL << (long) numOfFontStyles()}
	 */
	private long colourBitMask() {
		return 0xFF_FF_FF_FFL << (long) numOfFontStyles();
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 08:05:29 ---------------------------------------------------
	 */
	/**
	 * Gets the style bits where all the bits of the opaque parts of the colour data
	 * are set to {@code 1}.
	 * 
	 * @return {@code 0xFF_FF_FF_00L << (long) numOfFontStyles()}
	 * @hidden Unused method please remove from code.
	 */
	long opaqueColourBitMask() {
		return 0xFF_FF_FF_00L << (long) numOfFontStyles();
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 08:05:29 ---------------------------------------------------
	 */
	/**
	 * Gets the style bits where all the bits of the alpha parts of the colour data
	 * are set to {@code 1}.
	 * 
	 * @return {@code 0xFFL << (long) numOfFontStyles()}
	 */
	private long alphaBitMask() {
		return 0xFFL << (long) numOfFontStyles();
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 08:14:13 ---------------------------------------------------
	 */
	/**
	 * Gets a default to be used to style {@code Segment} types where no style is set.
	 * 
	 * @return the {@code alphaBitMask()} which basically just sets the opacity to
	 *         max.
	 */
	private long defaultStyles() {
		return alphaBitMask();
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 08:16:39 ---------------------------------------------------
	 */
	/**
	 * Called by {@link #mark(Segment, String, int, List)} to apply all the
	 * {@code styles} to the the {@code math} format.
	 * <p>
	 * It invokes {@link #useFontStyles(String, long)} to initially apply font
	 * effect styling, then calls {@link #useRGBA(String, long)} to apply colour
	 * styling before returning the results. Note that {@link FontStyle#NORMAL}
	 * applies not font font effects.
	 * 
	 * @param math   the math to be styled
	 * @param styles the style bits to apply to the given math
	 * @return {@code math} after applying the styles to it.
	 */
	private String style(String math, long styles) {
		return useRGBA(useFontStyles(math, styles & fontStylesBitMask()),
				(styles & colourBitMask()) >>> numOfFontStyles());
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 08:34:51 ---------------------------------------------------
	 */
	/**
	 * Styles the given {@code math} using the provided font effect bits.
	 * <p>
	 * The specified font style argument is searched to find the effect settings by
	 * circling through the bit indexes. When an index is in view, it is extracted
	 * from the effect bits, then used to query {@link #fs}. If the query returns a
	 * {@code null} value then {@code FontStyle.NORMAL} will be used, else the
	 * retrieved {@code FontStyle} is used.
	 * <p>
	 * All styles will apply their effects with the exception of
	 * {@link FontStyle#NORMAL}, which has no effect. The
	 * {@link FontStyle#STRIKE_THROUGH} effect does not take any argument as it is
	 * not 'canceling towards' any math.
	 * 
	 * @param math       the math to be styled
	 * @param fontStyles the font effect part of the total style bits.
	 * @return {@code math} after styling is done, except when no style is
	 *         specified, then it is returned 'as is'.
	 */
	private String useFontStyles(String math, long fontStyles) {
		for (int i = 1; i < fontStylesBitMask(); i <<= 1) {
			FontStyle fs = this.fs.getOrDefault(fontStyles & i, FontStyle.NORMAL);
			if (!fs.getCommand().isEmpty())
				if (fs == FontStyle.STRIKE_THROUGH)
					math = String.format("%1$s{%2$s}{}", fs.getCommand(), math);
				else
					math = String.format("%1$s{%2$s}", fs.getCommand(), math);
		}
		return math;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 08:50:41 ---------------------------------------------------
	 */
	/**
	 * Applies colour styling to the {@code math} argument.
	 * <p>
	 * The colour applied uses HTML's hex colour codes (such as {@code #ff0000ff}
	 * for opaque red) and the codes themselves are always in the format
	 * <code>#XXXXXXXX</code>, where {@code X} is replaced by a relevant hex digit.
	 * <p>
	 * The format of the {@code math} after styling has been applied is:
	 * 
	 * <pre>
	 *	<code>
	 *		\style{color:#XXXXXXXX;}{math}	
	 *	</code>
	 * </pre>
	 * 
	 * Where {@code #XXXXXXXX} is the hex color code and {@code math} is the math to
	 * be styled.
	 * 
	 * @param math the math to be styled.
	 * @param rgba the colour data part of the total style bits.
	 * @return {@code math} after coloring has been applied to it.
	 */
	private String useRGBA(String math, long rgba) {
		return String.format("\\style{color:%1$s;}{%2$s}", new IntegerColour((int) rgba).toString(), math);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 09:04:38 ---------------------------------------------------
	 */
	/**
	 * Creates the style bits from the arguments.
	 * 
	 * @param a         the alpha value of the colour, representing the colour's
	 *                  opacity.
	 * @param rgb       the opaque hex colour code for the rgb value in the order
	 *                  {@code RRGGBB}. For example the colour for yellow can be
	 *                  gotten through <code>Integer.parseInt("ffff00", 16)</code>.
	 * @param fontStyle the font style data of the style bits.
	 * @return the style bits welded from the arguments.
	 */
	private long compile(short a, int rgb, int fontStyle) {
		/*
		 * A lot of casting because the JVM will auto truncate, treating it as an int if
		 * not casted to long, resulting in signed (negative) or buggy values
		 */
		return ((((long) rgb << 8L) | (long) a) << (long) numOfFontStyles()) | (long) fontStyle;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 09:26:55 ---------------------------------------------------
	 */
	/**
	 * Get the style bits mapping for the given {@code Segment} type.
	 * 
	 * @param t the type of {@code Segment} for which the mapped style bits will be
	 *          returned.
	 * @return the mapped styled bit for the given {@code Segment} type or else returns a
	 *         default style if no mapping is available for this type.
	 */
	public long getStyles(int t) {
		return styles.getOrDefault(t, defaultStyles());
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 09:40:35 ---------------------------------------------------
	 */
	/**
	 * Creates a mapping (in the style map) of {@code t} (the key) to {@code styles}
	 * (the value) which is also the style bits.
	 * 
	 * @param t      the {@code Segment} type which the {@code style} will be mapped to.
	 * @param styles the style bits
	 */
	public void register(int t, long styles) {
		constrainByException((long) (colourBitMask() << (long) numOfFontStyles()) | fontStylesBitMask(), styles);
		this.styles.put(t, styles);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 09:43:38 ---------------------------------------------------
	 */
	/**
	 * Creates a mapping for the given {@code Segment} type {@code t} using it as the key to
	 * the values {@code wc} and {@code fs}. When {@link Segment#format} is invoked
	 * after this method returns, that {@code Segment} (if it is the same type as {@code t})
	 * will be coloured and styled according to the arguments.
	 * <p>
	 * Only supported font styles which are validated by {@link #isValidFontStyle}
	 * are processed. The rest are simply ignored. Note that using this method
	 * erases any extra styling that was previously associated with the given type
	 * because this method is treated as if a new type is being registered
	 * 
	 * @param t  the {@code Segment} type to be registered.
	 * @param wc the colour to be used as the colour for this type.
	 * @param fs a {@code Set} containing the font styles to be used when formatting
	 *           the given type.
	 */
	public void register(int t, WebColour wc, Set<FontStyle> fs) {
		int fontStyles = 0;
		for (int i = 1; i < fontStylesBitMask(); i <<= 1) {
			FontStyle fst = this.fs.getOrDefault(fontStyles & i, FontStyle.NORMAL);
			if ((!fst.getCommand().isEmpty()) && fs.contains(fst))
				fontStyles |= i;
		}
		register(t, compile((short) wc.getOpacity(), wc.getOpaqueColour(), fontStyles));
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 09:55:43 ---------------------------------------------------
	 */
	/**
	 * Creates a mapping for the given {@code Segment} type {@code t} using it as the key to
	 * the values {@code wc} and {@code fs}. When {@link Segment#format} is invoked
	 * after this method returns, that {@code Segment} (if it is the same type as {@code t})
	 * will be coloured and styled according to the arguments.
	 * <p>
	 * Only supported font styles which are validated by {@link #isValidFontStyle}
	 * are processed. The rest are simply ignored. Note that using this method
	 * erases any extra styling that was previously associated with the given type
	 * because this method is treated as if a new type is being registered
	 * 
	 * @param t  the {@code Segment} type to be registered.
	 * @param wc the colour to be used as the colour for this type.
	 * @param fs a single font style to be used when formatting the given type.
	 */
	public void register(int t, WebColour wc, FontStyle fs) {
		int fontStyles = 0;
		for (int i = 1; i < fontStylesBitMask() && i > 0; i <<= 1) {
			FontStyle fst = this.fs.getOrDefault(fontStyles & i, FontStyle.NORMAL);
			if ((!fst.getCommand().isEmpty()) && this.fs.containsValue(fst))
				fontStyles |= i;
		}
		register(t, compile((short) wc.getOpacity(), wc.getOpaqueColour(), fontStyles));
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 09:57:25 ---------------------------------------------------
	 */
	/**
	 * Creates a mapping for the given {@code Segment} type {@code t} using it as the key to
	 * the value {@code wc} with default font effect styling. When
	 * {@link Segment#format} is invoked after this method returns, that {@code Segment} (if
	 * it is the same type as {@code t}) will be coloured according to the argument.
	 * 
	 * @param t  the {@code Segment} type to be registered.
	 * @param wc the colour to be used as the colour for this type.
	 */
	public void register(int t, WebColour wc) {
		register(t, wc, Set.of());
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 09:58:30 ---------------------------------------------------
	 */
	/**
	 * Creates a mapping for the given {@code Segment} type {@code t} using it as the key to
	 * the value {@code fs} with a default opaque black colour. When
	 * {@link Segment#format} is invoked after this method returns, that {@code Segment} (if
	 * it is the same type as {@code t}) will be styled according to the styles in
	 * the argument.
	 * <p>
	 * Only supported font styles which are validated by {@link #isValidFontStyle}
	 * are processed. The rest are simply ignored. Note that using this method
	 * erases any extra styling that was previously associated with the given type
	 * because this method is treated as if a new type is being registered
	 * 
	 * @param t  the {@code Segment} type to be registered.
	 * @param fs a {@code Set} containing the font styles to be used when formatting
	 *           the given type.
	 */
	public void register(int t, Set<FontStyle> fs) {
		register(t, new RGBA(0, 0, 0, 1f), fs);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:03:02 ---------------------------------------------------
	 */
	/**
	 * Creates a mapping for the given {@code Segment} type {@code t} using it as the key to
	 * the value {@code fs} with a black opaque colour. When {@link Segment#format}
	 * is invoked after this method returns, that {@code Segment} (if it is the same type as
	 * {@code t}) will be styled according to the argument.
	 * <p>
	 * Only supported font styles which are validated by {@link #isValidFontStyle}
	 * are processed. The rest are simply ignored. Note that using this method
	 * erases any extra styling that was previously associated with the given type
	 * because this method is treated as if a new type is being registered
	 * 
	 * @param t  the {@code Segment} type to be registered.
	 * @param fs a single font style to be used when formatting the given type.
	 */
	public void register(int t, FontStyle fs) {
		register(t, new IntegerColour(0xFF), fs);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:05:30 ---------------------------------------------------
	 */
	/**
	 * Creates a mapping for the given {@code Segment} type {@code t} using it as a default
	 * font effect style with a black opaque colour.
	 * 
	 * @param t the {@code Segment} type to be registered.
	 */
	public void register(int t) {
		register(t, new RGBA(0, 0, 0, 0xff));
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:08:26 ---------------------------------------------------
	 */
	/**
	 * Gets the colour data registered to given {@code Segment} type. Returns
	 * <code>null</code> if no registration was done or if it was unregistered
	 * before calling this method.
	 * 
	 * @param t the {@code Segment} type which the returned {@code WebColour} was mapped to.
	 * @return the color mapped to {@code t}. Returns <code>null</code> if no
	 *         mapping exists for {@code t}.
	 * @implNote The current value returned is an instance of the
	 *           {@code IntegerColour} class.
	 */
	public WebColour getColour(int t) {
		if (styles.containsKey(t))
			return new IntegerColour((int) (styles.get(t) << (long) numOfFontStyles()));
		return null;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:07:21 ---------------------------------------------------
	 */
	/**
	 * Removes the mapping for the given {@code Segment} type.
	 * 
	 * @param t the {@code Segment} type to be unregistered.
	 */
	public void unregister(int t) {
		styles.remove(t);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:13:08 ---------------------------------------------------
	 */
	/**
	 * Gets the font effect styling part of the total style bits as an {@code int}.
	 * 
	 * @param t the {@code Segment} type which the returned font styling was mapped to.
	 * @return the font style mapped to {@code t}. Returns {@code 0} if no mapping
	 *         exists for {@code t}.
	 */
	protected int getFontStylesAsInt(int t) {
		return (int) (getStyles(t) & fontStylesBitMask());
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:24:30 ---------------------------------------------------
	 */
	/**
	 * Gets the font effect data registered to given {@code Segment} type. Returns a
	 * <code>Set</code> with a single element which is {@link FontStyle#NORMAL} if
	 * no registration was done or if it was unregistered before calling this
	 * method.
	 * 
	 * @param t the {@code Segment} type which the returned {@code FontStyle} was mapped to.
	 * @return the font style mapped to {@code t}.
	 */
	public Set<FontStyle> getFontStyles(int t) {
		long fs = getStyles(t) & fontStylesBitMask();
		HashSet<FontStyle> styles = new HashSet<>();
		for (int i = 1; i <= numOfFontStyles(); i <<= 1) {
			styles.add(this.fs.getOrDefault(fs & i, FontStyle.NORMAL));
		}
		return styles;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:31:21 ---------------------------------------------------
	 */
	/**
	 * Checks if the argument is supported and returns {@code true} if it is
	 * otherwise returns <code>false</code>.
	 * 
	 * @param fs the value to be checked.
	 * @return <code>true</code> if the argument is a valid {@code FontStyle}.
	 */
	public boolean isValidFontStyle(FontStyle fs) {
		return this.fs.values().contains(fs);
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:35:29 ---------------------------------------------------
	 */
	/**
	 * Checks if the given {@code fs} is currently mapped to {@code t} and return
	 * <code>true</code> if and only if it is.
	 * 
	 * @param t  the {@code Segment} type to be checked.
	 * @param fs a {@code FontStyle} to be checked.
	 * @return <code>true</code> if {@code fs} is mapped to {@code t} or else
	 *         returned <code>false</code>.
	 */
	public boolean hasFontStyle(int t, FontStyle fs) {
		// return getFontStyles(t).contains(fs);
		long f = getStyles(t) & fontStylesBitMask();
		for (int i = 1; i <= numOfFontStyles(); i <<= 1) {
			if (fs == this.fs.get(f & i))
				return true;
		}
		return false;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:37:58 ---------------------------------------------------
	 */
	/**
	 * Styles the {@code math} argument using the colour data and font style (or
	 * defaults) that was registered to {@code t} and then returns the styled
	 * result.
	 * 
	 * @param s        can be <code>null</code>.
	 * @param math     the value to be styled
	 * @param t        the {@code Segment} type that specifies the styling to be done.
	 * @param position can be <code>null</code>.
	 * @return the {@code math} argument after styling has been done.
	 */
	@Override
	public String mark(Segment s, String math, int t, List<Integer> position) {
		return style(math, getStyles(t));
//		try {
//			return style(format, styles.get(t));
//		} catch (Exception e) {
//			e.printStackTrace();
//			return format;
//		}
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 10:42:57 ---------------------------------------------------
	 */
	/**
	 * Returns <code>true</code> if and only if the argument is a
	 * {@code StyleMarker} and has the same style map as well as the same supported
	 * font effects.
	 * 
	 * @param o {@inheritDoc}
	 * @return <code>true</code> if and only if the argument is a
	 *         {@code StyleMarker} and has the same style data.
	 */
	public boolean equals(Object o) {
		if (o instanceof StyleMarker) {
			StyleMarker sm = (StyleMarker) o;
			return styles.equals(sm.styles) && fs.equals(sm.fs);
		}
		return false;
	}

	/**
	 * A map that holds the {@code Segment} type and the style bits.
	 */
	private final Map<Integer, Long> styles;

	/**
	 * The set of supported {@code FonstStyle}.
	 */
	private final Map<Long, FontStyle> fs;
}