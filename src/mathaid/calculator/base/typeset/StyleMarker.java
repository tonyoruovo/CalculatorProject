/**
 * 
 */
package mathaid.calculator.base.typeset;

import static mathaid.calculator.base.typeset.StyleMarker.FontStyle.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The alpha (opacity) value can take as much as 8 bits. No more, no less.
 * The rgb (opaque) value can take as much as 24 bits. No more, no less.
 * The font style value as much as 32 bits or less.
 * 
 * Also, the hsv coversions (to and from rgb) are implemented from David H's answer
 * to this StackOverflow question:
 * https://stackoverflow.com/questions/3018313/algorithm-to-convert-rgb-to-hsv-and-hsv-to-rgb-in-range-0-255-for-both
 */
public class StyleMarker implements Marker {

	public enum FontStyle {
		NORMAL, BOLD("\\mathbf"), ITALIC("\\mathit"), 
		UNDERLINE("\\underline"), 
		/*
		 * TODO: requires the "cancel" package in MathJax
		 */
		STRIKE_THROUGH("\\cancelto"), ROMAN("\\mathrm"),
		MONOSPACE("\\mathtt"), CALIGRAPHIC("\\mathcal");

		FontStyle() {
			this("");
		}

		FontStyle(String cmd) {
			this.cmd = cmd;
		}

		public String getCommand() {
			return cmd;
		}

		private final String cmd;
	}

	private static Map<Long, FontStyle> initBasicFontStyles() {
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

	private static void constrainByException(long limit, long... val) {
		for (int i = 0; i < val.length; i++) {
			if (val[i] < 0L)
				throw new IllegalArgumentException("Cannot be below 0");
			if (val[i] > limit)
				throw new IllegalArgumentException(String.format("Cannot be above %s", limit));
		}
	}

	protected StyleMarker(Map<Integer, Long> styles, Map<Long, FontStyle> validFontStyles) {
		this.styles = styles;
		this.fs = Collections.unmodifiableMap(validFontStyles);
	}

	public StyleMarker(Map<Integer, Long> styles) {
		this(styles, initBasicFontStyles());
	}

	public StyleMarker() {
		this(new HashMap<>());
		HashSet<FontStyle> st = new HashSet<>();
		st.add(ROMAN);
		register(Segment.Type.NON_DECIMAL, new RGBA(1.0f, 0.0f, 0.0f, 0.0f), st);
	}

	private int numOfFontStyles() {
		return fs.size();
	}

	private int fontStylesBitMask() {
		return (int) (Math.pow(2, numOfFontStyles() - 1) - 1);
	}

	private long colourBitMask() {
		return 0xFF_FF_FF_FFL << (long) numOfFontStyles();
	}

	private long opaqueColourBitMask() {
		return 0xFF_FF_FFL << (long) numOfFontStyles();
	}

	private long alphaBitMask() {
		return 0xFFL << (long) (numOfFontStyles() + Long.bitCount(opaqueColourBitMask()));
	}

	private long defaultStyles() {
		return alphaBitMask();
	}

	private String style(String math, long styles) {
		math = useFontStyles(math, styles & fontStylesBitMask());
		return useARGB(math, (styles & colourBitMask()) >> numOfFontStyles());
	}

	private String useFontStyles(String math, long fontStyles) {
		for (int i = 1; i < fontStylesBitMask(); i <<= 1) {
			FontStyle fs = this.fs.getOrDefault(fontStyles & i, NORMAL);
			if (!fs.getCommand().isEmpty())
				if (fs == STRIKE_THROUGH)
					math = String.format("%1$s{%2$s}{}", fs.getCommand(), math);
				else
					math = String.format("%1$s{%2$s}", fs.getCommand(), math);
		}
		return math;
	}

	private String useARGB(String math, long argb) {
		/* Use BigDecimal division to prevent weird floating-point strings */
		return String.format("\\style{opacity:%1$s;color:\u0023%2$s;}{%3$s}",
			BigDecimal.valueOf(argb >> Long.bitCount(opaqueColourBitMask()))
				.divide(BigDecimal.valueOf(255L), MathContext.DECIMAL32)
				.stripTrailingZeros().toPlainString(),
			Long.toHexString(argb & (opaqueColourBitMask() >> numOfFontStyles())), math);
	}

	private long compile(short a, int rgb, int fontStyle) {
		return (((long) a << (long) (Long.bitCount(opaqueColourBitMask()) + numOfFontStyles()))
				| (long) rgb) | (long) fontStyle;
	}

	public long getStyles(int t) {
		return styles.getOrDefault(t, defaultStyles());
	}

	public void register(int t, long styles) {
		constrainByException((long) (colourBitMask() << (long) numOfFontStyles()) | fontStylesBitMask(),
			styles);
		this.styles.put(t, styles);
	}

	public WebColour getColour(int t) {
		if(styles.containsKey(t))
			return new IntegerColour((int) (styles.get(t) << (long) numOfFontStyles()));
		return null;
	}

	/*
	 * Only the font styles available are validated by isValidFontStyle(fs) are processed.
	 * The rest are simply ignored. Note that using this method erases any
	 * extra styling that was previously associated with the given type
	 * because this method is treated as if a new type is being registered
	 */
	public void register(int t, WebColour wc, Set<FontStyle> fs) {
		int fontStyles = 0;
		for (int i = 1; i < fontStylesBitMask(); i <<= 1) {
			FontStyle fst = this.fs.getOrDefault(fontStyles & i, NORMAL);
			if((!fst.getCommand().isEmpty()) && fs.contains(fst))
				fontStyles |= i;
		}
		register(t, compile((short) wc.getOpacity(), wc.getOpaqueColour(), fontStyles));
	}

	/*
	 * Only the font styles available that are validated by isValidFontStyle(fs) are processed.
	 * The rest are simply ignored. Note that using this method erases any
	 * extra styling that was previously associated with the given type
	 * because this method is treated as if a new type is being registered
	 */
	public void register(int t, WebColour wc, FontStyle fs) {
		int fontStyles = 0;
		for (int i = 1; i < fontStylesBitMask() && i > 0; i <<= 1) {
			FontStyle fst = this.fs.getOrDefault(fontStyles & i, NORMAL);
			if((!fst.getCommand().isEmpty()) && this.fs.containsValue(fst))
				fontStyles |= i;
		}
		register(t, compile((short) wc.getOpacity(), wc.getOpaqueColour(), fontStyles));
	}

	public int getFontStylesAsInt(int t) {
		return (int) (getStyles(t) & fontStylesBitMask());
	}

	public Set<FontStyle> getFontStyles(int t) {
		long fs = getStyles(t) & fontStylesBitMask();
		HashSet<FontStyle> styles = new HashSet<>();
		for(int i = 1; i <= numOfFontStyles(); i <<= 1) {
			styles.add(this.fs.getOrDefault(fs & i, NORMAL));
		}
		return styles;
	}

	public void register(int t, Set<FontStyle> fs) {
		if(fs == null)
			throw new NullPointerException();
		register(t, getColour(t), fs);
	}

	public boolean isValidFontStyle(FontStyle fs) {
		return this.fs.values().contains(fs);
	}

	public boolean hasFontStyle(int t, FontStyle fs) {
		//return getFontStyles(t).contains(fs);
		long f = getStyles(t) & fontStylesBitMask();
		for(int i = 1; i <= numOfFontStyles(); i <<= 1) {
			if(fs == this.fs.get(f & i)) return true;
		}
		return false;
	}

	public void unregister(int t) {
		styles.remove(t);
	}

	@Override
	public String mark(Segment s, String format, int t, List<Integer> position) {
		//long st = style.getOrDefault(t, defaultStyles());
		//if((st == 0 || st == defaultStyles()) && t.getChild() != null)
			//return mark(s, format, t.getChild(), position);
		return style(format, styles.getOrDefault(t, defaultStyles()));
	}

	public boolean equals(Object o) {
		if(o instanceof StyleMarker) {
			StyleMarker sm = (StyleMarker) o;
			return styles.equals(sm.styles) && fs.equals(sm.fs);
		}
		return false;
	}

	private final Map<Integer, Long> styles;
	private final Map<Long, FontStyle> fs;
}