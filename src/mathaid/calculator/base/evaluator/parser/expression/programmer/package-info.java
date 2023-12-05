/**
 * 
 */
/*
 * Date: 8 Oct 2022----------------------------------------------------------- 
 * Time created: 09:58:54---------------------------------------------------  
 * Package: mathaid.calculator.base.evaluator.parser.expression.programmer------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: package-info.java------------------------------------------------------ 
 * Class name: package-info------------------------------------------------ 
 */
/**
 * Holds all the class and interface definitions for expressions that are created by the
 * {@link mathaid.calculator.base.evaluator.parser.Parser} class and the
 * {@link mathaid.calculator.base.evaluator.parser.parselet.programmer} package. This particular expression is created by
 * reading binary data, evaluating the binary data and outputting results that are consistent with conventional operations on
 * binary data. The binary data being processed can be interpreted as floating point or integer bits.
 * <p>
 * All expressions maintain the following states:
 * <ul>
 * <li><span style="font-weight:bold">Bit Representation</span> - This is the interpretation of the layout of the binary data to
 * be processed. The layout may have a sign and a defined location within the data, how operations are to be carried out such as
 * addition, bitwise not etc. The following representations are supported:
 * <ul>
 * <li><span style="font-style:italic">Signed and Magnitude</span>: this is where the most significant bit (msb) define the sign
 * and the rest bits define the magnitude.</li>
 * <li><span style="font-style:italic">Unsigned</span>: this is where all bits within the binary data define the magnitude. This
 * allows for more range of data to be worked on</li>
 * <li><span style="font-style:italic">One's Complement</span>: this is where the msb stores the sign but the magnitude has the
 * bitwise 'not' performed on it.</li>
 * <li><span style="font-style:italic">Two's Complement</span>: the same as the above <span style="font-style:italic">One's
 * complement</span> except {@code 1} is added to the conversion result.</li>
 * <li><span style="font-style:italic">Excess-<span style="font-style:italic">k</span></span>: this is where a
 * <span style="font-style:italic">k</span> number of bits within the binary data is secluded to create a
 * <span style="font-weight:bold">bias</span> so as to reliably store both the sign and the magnitude of the integer.</li>
 * <li><span style="font-style:italic">Negabinary</span>: this is where the binary data is represented in base -2. Help to
 * reliably store more positive value than is possible with the previous one.</li>
 * <li><span style="font-style:italic">Math (Arithmetic integers)</span>: this is where the binary data is interpreted as a
 * conventional integer.</li>
 * <li><span style="font-style:italic">Floating Point</span>: this is where the binary data is interpreted as an IEEE754 value
 * depending on the bit-length.</li>
 * </ul>
 * </li>
 * <li><span style="font-weight:bold">Bit Length</span> - Provides bit precision. This is the number of bits that the current operation can precisely
 * represent, beyond which operations and expressions will be in accurate as an <em><strong>overflow</strong></em> occurs.</li>
 * <li><span style="font-weight:bold">Carry Bit</span> - This is the bit that holds the least significant bit of the bits that
 * overflowed from the last operation.</li>
 * <li><span style="font-weight:bold">Endianess</span> - This is the interpretation of the bit index start. This supports
 * <em>Big-Endian</em> (bit index starts from the right-most bit), <em>Little-Endian</em> (bit index starts from the left-most
 * bit) and a form of <em>Mid-Endian</em></li>
 * <li><span style="font-weight:bold">Radix</span> - This is the format of the input an output of the binary data.</li>
 * </ul>
 * <p>
 * An important thing to note is that floating point inputs can use radix-base exponent but outputs decimal exponent. This means
 * that the exponent value is always in decimal even when the value is normalised.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
package mathaid.calculator.base.evaluator.parser.expression.programmer;
