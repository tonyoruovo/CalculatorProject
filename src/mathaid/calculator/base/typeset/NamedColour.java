/*
 * Date: 24 Nov 2023 -----------------------------------------------------------
 * Time created: 16:11:09 ---------------------------------------------------
 */
/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static mathaid.calculator.base.util.Utility.toFlatCase;

/*
 * Date: 24 Nov 2023 -----------------------------------------------------------
 * Time created: 16:11:09 ---------------------------------------------------
 * Package: mathaid.calculator.base.typeset ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: NamedColour.java ------------------------------------------------------
 * Class name: NamedColour ------------------------------------------------
 */
/**
 * Represents a colour with a name as specified by
 * <a href= "https://en.wikipedia.org/wiki/List_of_colors_(alphabetical)">this
 * article</a>. The following is the table for the supported names:
 * <table border="1">
 * <thead>
 * <tr>
 * <th>Name</th>
 * <th>Hex code</th>
 * <th>Sample</th>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td><code>absolute zero</code></td>
 * <td>#0048BA</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#0048BA'></div></td>
 * </tr>
 * <tr>
 * <td><code>acid green</code></td>
 * <td>#B0BF1A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B0BF1A'></div></td>
 * </tr>
 * <tr>
 * <td><code>aero</code></td>
 * <td>#00B9E8</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00B9E8'></div></td>
 * </tr>
 * <tr>
 * <td><code>african violet</code></td>
 * <td>#B284BE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B284BE'></div></td>
 * </tr>
 * <tr>
 * <td><code>air superiority blue</code></td>
 * <td>#72A0C1</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#72A0C1'></div></td>
 * </tr>
 * <tr>
 * <td><code>alabaster</code></td>
 * <td>#F2F0E6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F2F0E6'></div></td>
 * </tr>
 * <tr>
 * <td><code>alice blue</code></td>
 * <td>#F0F8FF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F0F8FF'></div></td>
 * </tr>
 * <tr>
 * <td><code>alizarin</code></td>
 * <td>#DB2D43</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DB2D43'></div></td>
 * </tr>
 * <tr>
 * <td><code>alloy orange</code></td>
 * <td>#C46210</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C46210'></div></td>
 * </tr>
 * <tr>
 * <td><code>almond</code></td>
 * <td>#EFDECD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#EFDECD'></div></td>
 * </tr>
 * <tr>
 * <td><code>amaranth</code></td>
 * <td>#E52B50</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E52B50'></div></td>
 * </tr>
 * <tr>
 * <td><code>amaranth deep purple</code></td>
 * <td>#9F2B68</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9F2B68'></div></td>
 * </tr>
 * <tr>
 * <td><code>amaranth pink</code></td>
 * <td>#F19CBB</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F19CBB'></div></td>
 * </tr>
 * <tr>
 * <td><code>amaranth purple</code></td>
 * <td>#AB274F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#AB274F'></div></td>
 * </tr>
 * <tr>
 * <td><code>amazon</code></td>
 * <td>#3B7A57</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#3B7A57'></div></td>
 * </tr>
 * <tr>
 * <td><code>amber</code></td>
 * <td>#FFBF00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFBF00'></div></td>
 * </tr>
 * <tr>
 * <td><code>amber (sae/ece)</code></td>
 * <td>#FF7E00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF7E00'></div></td>
 * </tr>
 * <tr>
 * <td><code>amethyst</code></td>
 * <td>#9966CC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9966CC'></div></td>
 * </tr>
 * <tr>
 * <td><code>amethyst (crayola)</code></td>
 * <td>#64609A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#64609A'></div></td>
 * </tr>
 * <tr>
 * <td><code>android green</code></td>
 * <td>#3DDC84</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#3DDC84'></div></td>
 * </tr>
 * <tr>
 * <td><code>antique brass</code></td>
 * <td>#CD9575</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CD9575'></div></td>
 * </tr>
 * <tr>
 * <td><code>antique bronze</code></td>
 * <td>#665D1E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#665D1E'></div></td>
 * </tr>
 * <tr>
 * <td><code>antique fuchsia</code></td>
 * <td>#915C83</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#915C83'></div></td>
 * </tr>
 * <tr>
 * <td><code>antique ruby</code></td>
 * <td>#841B2D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#841B2D'></div></td>
 * </tr>
 * <tr>
 * <td><code>antique white</code></td>
 * <td>#FAEBD7</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FAEBD7'></div></td>
 * </tr>
 * <tr>
 * <td><code>apricot</code></td>
 * <td>#FBCEB1</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FBCEB1'></div></td>
 * </tr>
 * <tr>
 * <td><code>aqua</code></td>
 * <td>#00FFFF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00FFFF'></div></td>
 * </tr>
 * <tr>
 * <td><code>aquamarine</code></td>
 * <td>#7FFFD4</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#7FFFD4'></div></td>
 * </tr>
 * <tr>
 * <td><code>aquamarine (crayola)</code></td>
 * <td>#95E0E8</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#95E0E8'></div></td>
 * </tr>
 * <tr>
 * <td><code>arctic lime</code></td>
 * <td>#D0FF14</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D0FF14'></div></td>
 * </tr>
 * <tr>
 * <td><code>artichoke green</code></td>
 * <td>#4B6F44</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#4B6F44'></div></td>
 * </tr>
 * <tr>
 * <td><code>arylide yellow</code></td>
 * <td>#E9D66B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E9D66B'></div></td>
 * </tr>
 * <tr>
 * <td><code>ash gray</code></td>
 * <td>#B2BEB5</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B2BEB5'></div></td>
 * </tr>
 * <tr>
 * <td><code>asparagus</code></td>
 * <td>#7BA05B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#7BA05B'></div></td>
 * </tr>
 * <tr>
 * <td><code>atomic tangerine</code></td>
 * <td>#FF9966</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF9966'></div></td>
 * </tr>
 * <tr>
 * <td><code>aureolin</code></td>
 * <td>#FDEE00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FDEE00'></div></td>
 * </tr>
 * <tr>
 * <td><code>aztec gold</code></td>
 * <td>#C39953</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C39953'></div></td>
 * </tr>
 * <tr>
 * <td><code>azure</code></td>
 * <td>#007FFF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#007FFF'></div></td>
 * </tr>
 * <tr>
 * <td><code>azure (x11/web color)</code></td>
 * <td>#F0FFFF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F0FFFF'></div></td>
 * </tr>
 * <tr>
 * <td><code>baby blue</code></td>
 * <td>#89CFF0</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#89CFF0'></div></td>
 * </tr>
 * <tr>
 * <td><code>baby blue eyes</code></td>
 * <td>#A1CAF1</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A1CAF1'></div></td>
 * </tr>
 * <tr>
 * <td><code>baby pink</code></td>
 * <td>#F4C2C2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F4C2C2'></div></td>
 * </tr>
 * <tr>
 * <td><code>baby powder</code></td>
 * <td>#FEFEFA</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FEFEFA'></div></td>
 * </tr>
 * <tr>
 * <td><code>baker-miller pink</code></td>
 * <td>#FF91AF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF91AF'></div></td>
 * </tr>
 * <tr>
 * <td><code>banana mania</code></td>
 * <td>#FAE7B5</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FAE7B5'></div></td>
 * </tr>
 * <tr>
 * <td><code>barbie pink</code></td>
 * <td>#E0218A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E0218A'></div></td>
 * </tr>
 * <tr>
 * <td><code>barn red</code></td>
 * <td>#7C0A02</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#7C0A02'></div></td>
 * </tr>
 * <tr>
 * <td><code>battleship grey</code></td>
 * <td>#848482</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#848482'></div></td>
 * </tr>
 * <tr>
 * <td><code>beau blue</code></td>
 * <td>#BCD4E6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#BCD4E6'></div></td>
 * </tr>
 * <tr>
 * <td><code>beaver</code></td>
 * <td>#9F8170</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9F8170'></div></td>
 * </tr>
 * <tr>
 * <td><code>beige</code></td>
 * <td>#F5F5DC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F5F5DC'></div></td>
 * </tr>
 * <tr>
 * <td><code>berry parfait</code></td>
 * <td>#A43482</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A43482'></div></td>
 * </tr>
 * <tr>
 * <td><code>b'dazzled blue</code></td>
 * <td>#2E5894</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#2E5894'></div></td>
 * </tr>
 * <tr>
 * <td><code>big dip o�ruby</code></td>
 * <td>#9C2542</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9C2542'></div></td>
 * </tr>
 * <tr>
 * <td><code>big foot feet</code></td>
 * <td>#E88E5A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E88E5A'></div></td>
 * </tr>
 * <tr>
 * <td><code>bisque</code></td>
 * <td>#FFE4C4</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFE4C4'></div></td>
 * </tr>
 * <tr>
 * <td><code>bistre</code></td>
 * <td>#3D2B1F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#3D2B1F'></div></td>
 * </tr>
 * <tr>
 * <td><code>bistre brown</code></td>
 * <td>#967117</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#967117'></div></td>
 * </tr>
 * <tr>
 * <td><code>bitter lemon</code></td>
 * <td>#CAE00D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CAE00D'></div></td>
 * </tr>
 * <tr>
 * <td><code>bittersweet</code></td>
 * <td>#FE6F5E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FE6F5E'></div></td>
 * </tr>
 * <tr>
 * <td><code>bittersweet shimmer</code></td>
 * <td>#BF4F51</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#BF4F51'></div></td>
 * </tr>
 * <tr>
 * <td><code>black</code></td>
 * <td>#000000</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#000000'></div></td>
 * </tr>
 * <tr>
 * <td><code>black bean</code></td>
 * <td>#3D0C02</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#3D0C02'></div></td>
 * </tr>
 * <tr>
 * <td><code>black coral</code></td>
 * <td>#54626F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#54626F'></div></td>
 * </tr>
 * <tr>
 * <td><code>black olive</code></td>
 * <td>#3B3C36</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#3B3C36'></div></td>
 * </tr>
 * <tr>
 * <td><code>black shadows</code></td>
 * <td>#BFAFB2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#BFAFB2'></div></td>
 * </tr>
 * <tr>
 * <td><code>blanched almond</code></td>
 * <td>#FFEBCD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFEBCD'></div></td>
 * </tr>
 * <tr>
 * <td><code>blast-off bronze</code></td>
 * <td>#A57164</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A57164'></div></td>
 * </tr>
 * <tr>
 * <td><code>bleu de france</code></td>
 * <td>#318CE7</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#318CE7'></div></td>
 * </tr>
 * <tr>
 * <td><code>blizzard blue</code></td>
 * <td>#50BFE6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#50BFE6'></div></td>
 * </tr>
 * <tr>
 * <td><code>blood red</code></td>
 * <td>#660000</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#660000'></div></td>
 * </tr>
 * <tr>
 * <td><code>blue</code></td>
 * <td>#0000FF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#0000FF'></div></td>
 * </tr>
 * <tr>
 * <td><code>blue (crayola)</code></td>
 * <td>#1F75FE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#1F75FE'></div></td>
 * </tr>
 * <tr>
 * <td><code>blue (munsell)</code></td>
 * <td>#0093AF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#0093AF'></div></td>
 * </tr>
 * <tr>
 * <td><code>blue (ncs)</code></td>
 * <td>#0087BD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#0087BD'></div></td>
 * </tr>
 * <tr>
 * <td><code>blue (pantone)</code></td>
 * <td>#0018A8</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#0018A8'></div></td>
 * </tr>
 * <tr>
 * <td><code>blue (pigment)</code></td>
 * <td>#333399</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#333399'></div></td>
 * </tr>
 * <tr>
 * <td><code>blue bell</code></td>
 * <td>#A2A2D0</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A2A2D0'></div></td>
 * </tr>
 * <tr>
 * <td><code>blue-gray</code></td>
 * <td>#6699CC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#6699CC'></div></td>
 * </tr>
 * <tr>
 * <td><code>blue-gray (crayola)</code></td>
 * <td>#C8C8CD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C8C8CD'></div></td>
 * </tr>
 * <tr>
 * <td><code>blue-green</code></td>
 * <td>#0D98BA</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#0D98BA'></div></td>
 * </tr>
 * <tr>
 * <td><code>blue jeans</code></td>
 * <td>#5DADEC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#5DADEC'></div></td>
 * </tr>
 * <tr>
 * <td><code>blue ribbon</code></td>
 * <td>#0B10A2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#0B10A2'></div></td>
 * </tr>
 * <tr>
 * <td><code>blue sapphire</code></td>
 * <td>#126180</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#126180'></div></td>
 * </tr>
 * <tr>
 * <td><code>blue-violet</code></td>
 * <td>#8A2BE2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8A2BE2'></div></td>
 * </tr>
 * <tr>
 * <td><code>blue yonder</code></td>
 * <td>#5072A7</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#5072A7'></div></td>
 * </tr>
 * <tr>
 * <td><code>blueberry</code></td>
 * <td>#4F86F7</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#4F86F7'></div></td>
 * </tr>
 * <tr>
 * <td><code>bluetiful</code></td>
 * <td>#3C69E7</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#3C69E7'></div></td>
 * </tr>
 * <tr>
 * <td><code>blush</code></td>
 * <td>#DE5D83</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DE5D83'></div></td>
 * </tr>
 * <tr>
 * <td><code>bole</code></td>
 * <td>#79443B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#79443B'></div></td>
 * </tr>
 * <tr>
 * <td><code>bone</code></td>
 * <td>#E3DAC9</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E3DAC9'></div></td>
 * </tr>
 * <tr>
 * <td><code>booger buster</code></td>
 * <td>#DDE26A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DDE26A'></div></td>
 * </tr>
 * <tr>
 * <td><code>brick red</code></td>
 * <td>#CB4154</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CB4154'></div></td>
 * </tr>
 * <tr>
 * <td><code>bright green</code></td>
 * <td>#66FF00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#66FF00'></div></td>
 * </tr>
 * <tr>
 * <td><code>bright lilac</code></td>
 * <td>#D891EF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D891EF'></div></td>
 * </tr>
 * <tr>
 * <td><code>bright maroon</code></td>
 * <td>#C32148</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C32148'></div></td>
 * </tr>
 * <tr>
 * <td><code>bright navy blue</code></td>
 * <td>#1974D2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#1974D2'></div></td>
 * </tr>
 * <tr>
 * <td><code>bright pink</code></td>
 * <td>#FF007F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF007F'></div></td>
 * </tr>
 * <tr>
 * <td><code>bright turquoise</code></td>
 * <td>#08E8DE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#08E8DE'></div></td>
 * </tr>
 * <tr>
 * <td><code>bright yellow (crayola)</code></td>
 * <td>#FFAA1D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFAA1D'></div></td>
 * </tr>
 * <tr>
 * <td><code>brilliant rose</code></td>
 * <td>#E667CE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E667CE'></div></td>
 * </tr>
 * <tr>
 * <td><code>brink pink</code></td>
 * <td>#FB607F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FB607F'></div></td>
 * </tr>
 * <tr>
 * <td><code>british racing green</code></td>
 * <td>#004225</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#004225'></div></td>
 * </tr>
 * <tr>
 * <td><code>bronze</code></td>
 * <td>#CD7F32</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CD7F32'></div></td>
 * </tr>
 * <tr>
 * <td><code>brown</code></td>
 * <td>#964B00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#964B00'></div></td>
 * </tr>
 * <tr>
 * <td><code>brown (crayola)</code></td>
 * <td>#AF593E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#AF593E'></div></td>
 * </tr>
 * <tr>
 * <td><code>brown (web)</code></td>
 * <td>#A52A2A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A52A2A'></div></td>
 * </tr>
 * <tr>
 * <td><code>brown sugar</code></td>
 * <td>#AF6E4D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#AF6E4D'></div></td>
 * </tr>
 * <tr>
 * <td><code>bud green</code></td>
 * <td>#7BB661</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#7BB661'></div></td>
 * </tr>
 * <tr>
 * <td><code>buff</code></td>
 * <td>#F0DC82</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F0DC82'></div></td>
 * </tr>
 * <tr>
 * <td><code>burgundy</code></td>
 * <td>#800020</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#800020'></div></td>
 * </tr>
 * <tr>
 * <td><code>burlywood</code></td>
 * <td>#DEB887</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DEB887'></div></td>
 * </tr>
 * <tr>
 * <td><code>burnished brown</code></td>
 * <td>#A17A74</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A17A74'></div></td>
 * </tr>
 * <tr>
 * <td><code>burnt orange</code></td>
 * <td>#CC5500</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CC5500'></div></td>
 * </tr>
 * <tr>
 * <td><code>burnt sienna</code></td>
 * <td>#E97451</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E97451'></div></td>
 * </tr>
 * <tr>
 * <td><code>burnt umber</code></td>
 * <td>#8A3324</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8A3324'></div></td>
 * </tr>
 * <tr>
 * <td><code>byzantine</code></td>
 * <td>#BD33A4</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#BD33A4'></div></td>
 * </tr>
 * <tr>
 * <td><code>byzantium</code></td>
 * <td>#702963</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#702963'></div></td>
 * </tr>
 * <tr>
 * <td><code>cadet</code></td>
 * <td>#536872</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#536872'></div></td>
 * </tr>
 * <tr>
 * <td><code>cadet blue</code></td>
 * <td>#5F9EA0</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#5F9EA0'></div></td>
 * </tr>
 * <tr>
 * <td><code>cadet grey</code></td>
 * <td>#91A3B0</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#91A3B0'></div></td>
 * </tr>
 * <tr>
 * <td><code>cadmium green</code></td>
 * <td>#006B3C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#006B3C'></div></td>
 * </tr>
 * <tr>
 * <td><code>cadmium orange</code></td>
 * <td>#ED872D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#ED872D'></div></td>
 * </tr>
 * <tr>
 * <td><code>cadmium red</code></td>
 * <td>#E30022</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E30022'></div></td>
 * </tr>
 * <tr>
 * <td><code>cadmium yellow</code></td>
 * <td>#FFF600</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFF600'></div></td>
 * </tr>
 * <tr>
 * <td><code>caf� au lait</code></td>
 * <td>#A67B5B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A67B5B'></div></td>
 * </tr>
 * <tr>
 * <td><code>caf� noir</code></td>
 * <td>#4B3621</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#4B3621'></div></td>
 * </tr>
 * <tr>
 * <td><code>cambridge blue</code></td>
 * <td>#A3C1AD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A3C1AD'></div></td>
 * </tr>
 * <tr>
 * <td><code>camel</code></td>
 * <td>#C19A6B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C19A6B'></div></td>
 * </tr>
 * <tr>
 * <td><code>cameo pink</code></td>
 * <td>#EFBBCC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#EFBBCC'></div></td>
 * </tr>
 * <tr>
 * <td><code>canary</code></td>
 * <td>#FFFF99</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFFF99'></div></td>
 * </tr>
 * <tr>
 * <td><code>canary yellow</code></td>
 * <td>#FFEF00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFEF00'></div></td>
 * </tr>
 * <tr>
 * <td><code>candy apple red</code></td>
 * <td>#FF0800</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF0800'></div></td>
 * </tr>
 * <tr>
 * <td><code>candy pink</code></td>
 * <td>#E4717A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E4717A'></div></td>
 * </tr>
 * <tr>
 * <td><code>cardinal</code></td>
 * <td>#C41E3A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C41E3A'></div></td>
 * </tr>
 * <tr>
 * <td><code>caribbean green</code></td>
 * <td>#00CC99</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00CC99'></div></td>
 * </tr>
 * <tr>
 * <td><code>carmine</code></td>
 * <td>#960018</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#960018'></div></td>
 * </tr>
 * <tr>
 * <td><code>carnation pink</code></td>
 * <td>#FFA6C9</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFA6C9'></div></td>
 * </tr>
 * <tr>
 * <td><code>carolina blue</code></td>
 * <td>#56A0D3</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#56A0D3'></div></td>
 * </tr>
 * <tr>
 * <td><code>carrot orange</code></td>
 * <td>#ED9121</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#ED9121'></div></td>
 * </tr>
 * <tr>
 * <td><code>catawba</code></td>
 * <td>#703642</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#703642'></div></td>
 * </tr>
 * <tr>
 * <td><code>cedar chest</code></td>
 * <td>#CA3435</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CA3435'></div></td>
 * </tr>
 * <tr>
 * <td><code>celadon</code></td>
 * <td>#ACE1AF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#ACE1AF'></div></td>
 * </tr>
 * <tr>
 * <td><code>celeste</code></td>
 * <td>#B2FFFF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B2FFFF'></div></td>
 * </tr>
 * <tr>
 * <td><code>cerise</code></td>
 * <td>#DE3163</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DE3163'></div></td>
 * </tr>
 * <tr>
 * <td><code>cerulean</code></td>
 * <td>#007BA7</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#007BA7'></div></td>
 * </tr>
 * <tr>
 * <td><code>cerulean blue</code></td>
 * <td>#2A52BE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#2A52BE'></div></td>
 * </tr>
 * <tr>
 * <td><code>cerulean frost</code></td>
 * <td>#6D9BC3</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#6D9BC3'></div></td>
 * </tr>
 * <tr>
 * <td><code>cerulean (crayola)</code></td>
 * <td>#1DACD6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#1DACD6'></div></td>
 * </tr>
 * <tr>
 * <td><code>champagne</code></td>
 * <td>#F7E7CE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F7E7CE'></div></td>
 * </tr>
 * <tr>
 * <td><code>champagne pink</code></td>
 * <td>#F1DDCF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F1DDCF'></div></td>
 * </tr>
 * <tr>
 * <td><code>charcoal</code></td>
 * <td>#36454F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#36454F'></div></td>
 * </tr>
 * <tr>
 * <td><code>charleston green</code></td>
 * <td>#232B2B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#232B2B'></div></td>
 * </tr>
 * <tr>
 * <td><code>charm pink</code></td>
 * <td>#E68FAC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E68FAC'></div></td>
 * </tr>
 * <tr>
 * <td><code>chartreuse</code></td>
 * <td>#7FFF00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#7FFF00'></div></td>
 * </tr>
 * <tr>
 * <td><code>cherry blossom pink</code></td>
 * <td>#FFB7C5</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFB7C5'></div></td>
 * </tr>
 * <tr>
 * <td><code>chestnut</code></td>
 * <td>#954535</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#954535'></div></td>
 * </tr>
 * <tr>
 * <td><code>china pink</code></td>
 * <td>#DE6FA1</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DE6FA1'></div></td>
 * </tr>
 * <tr>
 * <td><code>chinese red</code></td>
 * <td>#AA381E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#AA381E'></div></td>
 * </tr>
 * <tr>
 * <td><code>chinese violet</code></td>
 * <td>#856088</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#856088'></div></td>
 * </tr>
 * <tr>
 * <td><code>chocolate (traditional)</code></td>
 * <td>#7B3F00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#7B3F00'></div></td>
 * </tr>
 * <tr>
 * <td><code>chocolate (web)</code></td>
 * <td>#D2691E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D2691E'></div></td>
 * </tr>
 * <tr>
 * <td><code>cinereous</code></td>
 * <td>#98817B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#98817B'></div></td>
 * </tr>
 * <tr>
 * <td><code>cinnamon satin</code></td>
 * <td>#CD607E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CD607E'></div></td>
 * </tr>
 * <tr>
 * <td><code>citrine</code></td>
 * <td>#E4D00A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E4D00A'></div></td>
 * </tr>
 * <tr>
 * <td><code>citron</code></td>
 * <td>#9FA91F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9FA91F'></div></td>
 * </tr>
 * <tr>
 * <td><code>claret</code></td>
 * <td>#7F1734</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#7F1734'></div></td>
 * </tr>
 * <tr>
 * <td><code>cobalt blue</code></td>
 * <td>#0047AB</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#0047AB'></div></td>
 * </tr>
 * <tr>
 * <td><code>cocoa brown</code></td>
 * <td>#D2691E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D2691E'></div></td>
 * </tr>
 * <tr>
 * <td><code>coconut</code></td>
 * <td>#965A3E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#965A3E'></div></td>
 * </tr>
 * <tr>
 * <td><code>coffee</code></td>
 * <td>#6F4E37</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#6F4E37'></div></td>
 * </tr>
 * <tr>
 * <td><code>columbia blue</code></td>
 * <td>#C4D8E2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C4D8E2'></div></td>
 * </tr>
 * <tr>
 * <td><code>congo pink</code></td>
 * <td>#F88379</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F88379'></div></td>
 * </tr>
 * <tr>
 * <td><code>cool grey</code></td>
 * <td>#8C92AC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8C92AC'></div></td>
 * </tr>
 * <tr>
 * <td><code>copper</code></td>
 * <td>#B87333</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B87333'></div></td>
 * </tr>
 * <tr>
 * <td><code>copper penny</code></td>
 * <td>#AD6F69</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#AD6F69'></div></td>
 * </tr>
 * <tr>
 * <td><code>copper red</code></td>
 * <td>#CB6D51</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CB6D51'></div></td>
 * </tr>
 * <tr>
 * <td><code>copper rose</code></td>
 * <td>#996666</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#996666'></div></td>
 * </tr>
 * <tr>
 * <td><code>coquelicot</code></td>
 * <td>#FF3800</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF3800'></div></td>
 * </tr>
 * <tr>
 * <td><code>coral</code></td>
 * <td>#FF7F50</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF7F50'></div></td>
 * </tr>
 * <tr>
 * <td><code>coral pink</code></td>
 * <td>#F88379</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F88379'></div></td>
 * </tr>
 * <tr>
 * <td><code>cordovan</code></td>
 * <td>#893F45</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#893F45'></div></td>
 * </tr>
 * <tr>
 * <td><code>cornflower blue (web)</code></td>
 * <td>#6495ED</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#6495ED'></div></td>
 * </tr>
 * <tr>
 * <td><code>cornflower blue (crayola)</code></td>
 * <td>#93CCEA</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#93CCEA'></div></td>
 * </tr>
 * <tr>
 * <td><code>cornsilk</code></td>
 * <td>#FFF8DC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFF8DC'></div></td>
 * </tr>
 * <tr>
 * <td><code>cosmic cobalt</code></td>
 * <td>#2E2D88</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#2E2D88'></div></td>
 * </tr>
 * <tr>
 * <td><code>cosmic latte</code></td>
 * <td>#FFF8E7</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFF8E7'></div></td>
 * </tr>
 * <tr>
 * <td><code>coyote brown</code></td>
 * <td>#81613C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#81613C'></div></td>
 * </tr>
 * <tr>
 * <td><code>cotton candy</code></td>
 * <td>#FFBCD9</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFBCD9'></div></td>
 * </tr>
 * <tr>
 * <td><code>cream</code></td>
 * <td>#FFFDD0</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFFDD0'></div></td>
 * </tr>
 * <tr>
 * <td><code>crimson</code></td>
 * <td>#DC143C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DC143C'></div></td>
 * </tr>
 * <tr>
 * <td><code>crimson (ua)</code></td>
 * <td>#AF002A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#AF002A'></div></td>
 * </tr>
 * <tr>
 * <td><code>cultured</code></td>
 * <td>#F5F5F5</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F5F5F5'></div></td>
 * </tr>
 * <tr>
 * <td><code>cyan</code></td>
 * <td>#00FFFF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00FFFF'></div></td>
 * </tr>
 * <tr>
 * <td><code>cyber grape</code></td>
 * <td>#58427C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#58427C'></div></td>
 * </tr>
 * <tr>
 * <td><code>cyber yellow</code></td>
 * <td>#FFD300</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFD300'></div></td>
 * </tr>
 * <tr>
 * <td><code>cyclamen</code></td>
 * <td>#F56FA1</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F56FA1'></div></td>
 * </tr>
 * <tr>
 * <td><code>dandelion</code></td>
 * <td>#DDF719</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DDF719'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark blue</code></td>
 * <td>#00008B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00008B'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark blue-gray</code></td>
 * <td>#666699</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#666699'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark brown</code></td>
 * <td>#654321</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#654321'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark byzantium</code></td>
 * <td>#5D3954</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#5D3954'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark cyan</code></td>
 * <td>#008B8B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#008B8B'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark electric blue</code></td>
 * <td>#536878</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#536878'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark fuchsia</code></td>
 * <td>#A00955</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A00955'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark goldenrod</code></td>
 * <td>#B8860B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B8860B'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark gray (x11)</code></td>
 * <td>#A9A9A9</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A9A9A9'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark green</code></td>
 * <td>#013220</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#013220'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark green (x11)</code></td>
 * <td>#006400</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#006400'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark jungle green</code></td>
 * <td>#1A2421</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#1A2421'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark khaki</code></td>
 * <td>#BDB76B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#BDB76B'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark lava</code></td>
 * <td>#483C32</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#483C32'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark liver</code></td>
 * <td>#534B4F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#534B4F'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark magenta</code></td>
 * <td>#8B008B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8B008B'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark midnight blue</code></td>
 * <td>#003366</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#003366'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark moss green</code></td>
 * <td>#4A5D23</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#4A5D23'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark olive green</code></td>
 * <td>#556B2F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#556B2F'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark orange</code></td>
 * <td>#FF8C00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF8C00'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark orchid</code></td>
 * <td>#9932CC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9932CC'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark pastel green</code></td>
 * <td>#03C03C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#03C03C'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark purple</code></td>
 * <td>#301934</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#301934'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark raspberry</code></td>
 * <td>#872657</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#872657'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark red</code></td>
 * <td>#8B0000</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8B0000'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark salmon</code></td>
 * <td>#E9967A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E9967A'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark sea green</code></td>
 * <td>#8FBC8F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8FBC8F'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark sienna</code></td>
 * <td>#3C1414</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#3C1414'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark sky blue</code></td>
 * <td>#8CBED6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8CBED6'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark slate blue</code></td>
 * <td>#483D8B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#483D8B'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark slate gray</code></td>
 * <td>#2F4F4F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#2F4F4F'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark spring green</code></td>
 * <td>#177245</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#177245'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark tan</code></td>
 * <td>#918151</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#918151'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark turquoise</code></td>
 * <td>#00CED1</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00CED1'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark vanilla</code></td>
 * <td>#D1BEA8</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D1BEA8'></div></td>
 * </tr>
 * <tr>
 * <td><code>dark violet</code></td>
 * <td>#9400D3</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9400D3'></div></td>
 * </tr>
 * <tr>
 * <td><code>dartmouth green</code></td>
 * <td>#00703C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00703C'></div></td>
 * </tr>
 * <tr>
 * <td><code>davy's grey</code></td>
 * <td>#555555</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#555555'></div></td>
 * </tr>
 * <tr>
 * <td><code>deep cerise</code></td>
 * <td>#DA3287</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DA3287'></div></td>
 * </tr>
 * <tr>
 * <td><code>deep champagne</code></td>
 * <td>#FAD6A5</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FAD6A5'></div></td>
 * </tr>
 * <tr>
 * <td><code>deep chestnut</code></td>
 * <td>#B94E48</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B94E48'></div></td>
 * </tr>
 * <tr>
 * <td><code>deep fuchsia</code></td>
 * <td>#C154C1</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C154C1'></div></td>
 * </tr>
 * <tr>
 * <td><code>deep jungle green</code></td>
 * <td>#004B49</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#004B49'></div></td>
 * </tr>
 * <tr>
 * <td><code>deep lemon</code></td>
 * <td>#F5C71A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F5C71A'></div></td>
 * </tr>
 * <tr>
 * <td><code>deep mauve</code></td>
 * <td>#D473D4</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D473D4'></div></td>
 * </tr>
 * <tr>
 * <td><code>deep pink</code></td>
 * <td>#FF1493</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF1493'></div></td>
 * </tr>
 * <tr>
 * <td><code>deep sky blue</code></td>
 * <td>#00BFFF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00BFFF'></div></td>
 * </tr>
 * <tr>
 * <td><code>deep space sparkle</code></td>
 * <td>#4A646C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#4A646C'></div></td>
 * </tr>
 * <tr>
 * <td><code>deep taupe</code></td>
 * <td>#7E5E60</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#7E5E60'></div></td>
 * </tr>
 * <tr>
 * <td><code>denim</code></td>
 * <td>#1560BD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#1560BD'></div></td>
 * </tr>
 * <tr>
 * <td><code>denim blue</code></td>
 * <td>#2243B6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#2243B6'></div></td>
 * </tr>
 * <tr>
 * <td><code>desert</code></td>
 * <td>#C19A6B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C19A6B'></div></td>
 * </tr>
 * <tr>
 * <td><code>desert sand</code></td>
 * <td>#EDC9AF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#EDC9AF'></div></td>
 * </tr>
 * <tr>
 * <td><code>dim gray</code></td>
 * <td>#696969</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#696969'></div></td>
 * </tr>
 * <tr>
 * <td><code>dingy dungeon</code></td>
 * <td>#C53151</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C53151'></div></td>
 * </tr>
 * <tr>
 * <td><code>dirt</code></td>
 * <td>#9B7653</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9B7653'></div></td>
 * </tr>
 * <tr>
 * <td><code>dodger blue</code></td>
 * <td>#1E90FF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#1E90FF'></div></td>
 * </tr>
 * <tr>
 * <td><code>dogwood rose</code></td>
 * <td>#D71868</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D71868'></div></td>
 * </tr>
 * <tr>
 * <td><code>duke blue</code></td>
 * <td>#00009C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00009C'></div></td>
 * </tr>
 * <tr>
 * <td><code>dutch white</code></td>
 * <td>#EFDFBB</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#EFDFBB'></div></td>
 * </tr>
 * <tr>
 * <td><code>earth yellow</code></td>
 * <td>#E1A95F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E1A95F'></div></td>
 * </tr>
 * <tr>
 * <td><code>ebony</code></td>
 * <td>#555D50</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#555D50'></div></td>
 * </tr>
 * <tr>
 * <td><code>ecru</code></td>
 * <td>#C2B280</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C2B280'></div></td>
 * </tr>
 * <tr>
 * <td><code>eerie black</code></td>
 * <td>#1B1B1B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#1B1B1B'></div></td>
 * </tr>
 * <tr>
 * <td><code>eggplant</code></td>
 * <td>#614051</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#614051'></div></td>
 * </tr>
 * <tr>
 * <td><code>eggshell</code></td>
 * <td>#F0EAD6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F0EAD6'></div></td>
 * </tr>
 * <tr>
 * <td><code>egyptian blue</code></td>
 * <td>#1034A6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#1034A6'></div></td>
 * </tr>
 * <tr>
 * <td><code>electric blue</code></td>
 * <td>#7DF9FF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#7DF9FF'></div></td>
 * </tr>
 * <tr>
 * <td><code>electric indigo</code></td>
 * <td>#6F00FF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#6F00FF'></div></td>
 * </tr>
 * <tr>
 * <td><code>electric lime</code></td>
 * <td>#CCFF00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CCFF00'></div></td>
 * </tr>
 * <tr>
 * <td><code>electric purple</code></td>
 * <td>#BF00FF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#BF00FF'></div></td>
 * </tr>
 * <tr>
 * <td><code>electric violet</code></td>
 * <td>#8F00FF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8F00FF'></div></td>
 * </tr>
 * <tr>
 * <td><code>emerald</code></td>
 * <td>#50C878</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#50C878'></div></td>
 * </tr>
 * <tr>
 * <td><code>eminence</code></td>
 * <td>#6C3082</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#6C3082'></div></td>
 * </tr>
 * <tr>
 * <td><code>english lavender</code></td>
 * <td>#B48395</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B48395'></div></td>
 * </tr>
 * <tr>
 * <td><code>english red</code></td>
 * <td>#AB4B52</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#AB4B52'></div></td>
 * </tr>
 * <tr>
 * <td><code>english vermillion</code></td>
 * <td>#CC474B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CC474B'></div></td>
 * </tr>
 * <tr>
 * <td><code>english violet</code></td>
 * <td>#563C5C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#563C5C'></div></td>
 * </tr>
 * <tr>
 * <td><code>eton blue</code></td>
 * <td>#96C8A2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#96C8A2'></div></td>
 * </tr>
 * <tr>
 * <td><code>eucalyptus</code></td>
 * <td>#44D7A8</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#44D7A8'></div></td>
 * </tr>
 * <tr>
 * <td><code>fallow</code></td>
 * <td>#C19A6B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C19A6B'></div></td>
 * </tr>
 * <tr>
 * <td><code>falu red</code></td>
 * <td>#801818</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#801818'></div></td>
 * </tr>
 * <tr>
 * <td><code>fandango</code></td>
 * <td>#B53389</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B53389'></div></td>
 * </tr>
 * <tr>
 * <td><code>fandango pink</code></td>
 * <td>#DE5285</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DE5285'></div></td>
 * </tr>
 * <tr>
 * <td><code>fashion fuchsia</code></td>
 * <td>#F400A1</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F400A1'></div></td>
 * </tr>
 * <tr>
 * <td><code>fawn</code></td>
 * <td>#E5AA70</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E5AA70'></div></td>
 * </tr>
 * <tr>
 * <td><code>feldgrau</code></td>
 * <td>#4D5D53</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#4D5D53'></div></td>
 * </tr>
 * <tr>
 * <td><code>fern green</code></td>
 * <td>#4F7942</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#4F7942'></div></td>
 * </tr>
 * <tr>
 * <td><code>field drab</code></td>
 * <td>#6C541E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#6C541E'></div></td>
 * </tr>
 * <tr>
 * <td><code>fiery rose</code></td>
 * <td>#FF5470</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF5470'></div></td>
 * </tr>
 * <tr>
 * <td><code>firebrick</code></td>
 * <td>#B22222</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B22222'></div></td>
 * </tr>
 * <tr>
 * <td><code>fire engine red</code></td>
 * <td>#CE2029</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CE2029'></div></td>
 * </tr>
 * <tr>
 * <td><code>fire opal</code></td>
 * <td>#E95C4B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E95C4B'></div></td>
 * </tr>
 * <tr>
 * <td><code>flame</code></td>
 * <td>#E25822</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E25822'></div></td>
 * </tr>
 * <tr>
 * <td><code>flax</code></td>
 * <td>#EEDC82</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#EEDC82'></div></td>
 * </tr>
 * <tr>
 * <td><code>flirt</code></td>
 * <td>#A2006D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A2006D'></div></td>
 * </tr>
 * <tr>
 * <td><code>floral white</code></td>
 * <td>#FFFAF0</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFFAF0'></div></td>
 * </tr>
 * <tr>
 * <td><code>fluorescent blue</code></td>
 * <td>#15F4EE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#15F4EE'></div></td>
 * </tr>
 * <tr>
 * <td><code>forest green (crayola)</code></td>
 * <td>#5FA777</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#5FA777'></div></td>
 * </tr>
 * <tr>
 * <td><code>forest green (traditional)</code></td>
 * <td>#014421</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#014421'></div></td>
 * </tr>
 * <tr>
 * <td><code>forest green (web)</code></td>
 * <td>#228B22</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#228B22'></div></td>
 * </tr>
 * <tr>
 * <td><code>french beige</code></td>
 * <td>#A67B5B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A67B5B'></div></td>
 * </tr>
 * <tr>
 * <td><code>french bistre</code></td>
 * <td>#856D4D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#856D4D'></div></td>
 * </tr>
 * <tr>
 * <td><code>french blue</code></td>
 * <td>#0072BB</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#0072BB'></div></td>
 * </tr>
 * <tr>
 * <td><code>french fuchsia</code></td>
 * <td>#FD3F92</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FD3F92'></div></td>
 * </tr>
 * <tr>
 * <td><code>french lilac</code></td>
 * <td>#86608E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#86608E'></div></td>
 * </tr>
 * <tr>
 * <td><code>french lime</code></td>
 * <td>#9EFD38</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9EFD38'></div></td>
 * </tr>
 * <tr>
 * <td><code>french mauve</code></td>
 * <td>#D473D4</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D473D4'></div></td>
 * </tr>
 * <tr>
 * <td><code>french pink</code></td>
 * <td>#FD6C9E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FD6C9E'></div></td>
 * </tr>
 * <tr>
 * <td><code>french raspberry</code></td>
 * <td>#C72C48</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C72C48'></div></td>
 * </tr>
 * <tr>
 * <td><code>french rose</code></td>
 * <td>#F64A8A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F64A8A'></div></td>
 * </tr>
 * <tr>
 * <td><code>french sky blue</code></td>
 * <td>#77B5FE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#77B5FE'></div></td>
 * </tr>
 * <tr>
 * <td><code>french violet</code></td>
 * <td>#8806CE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8806CE'></div></td>
 * </tr>
 * <tr>
 * <td><code>frostbite</code></td>
 * <td>#E936A7</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E936A7'></div></td>
 * </tr>
 * <tr>
 * <td><code>fuchsia</code></td>
 * <td>#FF00FF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF00FF'></div></td>
 * </tr>
 * <tr>
 * <td><code>fuchsia (crayola)</code></td>
 * <td>#C154C1</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C154C1'></div></td>
 * </tr>
 * <tr>
 * <td><code>fuchsia purple</code></td>
 * <td>#CC397B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CC397B'></div></td>
 * </tr>
 * <tr>
 * <td><code>fuchsia rose</code></td>
 * <td>#C74375</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C74375'></div></td>
 * </tr>
 * <tr>
 * <td><code>fulvous</code></td>
 * <td>#E48400</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E48400'></div></td>
 * </tr>
 * <tr>
 * <td><code>fuzzy wuzzy</code></td>
 * <td>#87421F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#87421F'></div></td>
 * </tr>
 * <tr>
 * <td><code>gainsboro</code></td>
 * <td>#DCDCDC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DCDCDC'></div></td>
 * </tr>
 * <tr>
 * <td><code>gamboge</code></td>
 * <td>#E49B0F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E49B0F'></div></td>
 * </tr>
 * <tr>
 * <td><code>garnet</code></td>
 * <td>#733635</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#733635'></div></td>
 * </tr>
 * <tr>
 * <td><code>generic viridian</code></td>
 * <td>#007F66</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#007F66'></div></td>
 * </tr>
 * <tr>
 * <td><code>ghost white</code></td>
 * <td>#F8F8FF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F8F8FF'></div></td>
 * </tr>
 * <tr>
 * <td><code>glaucous</code></td>
 * <td>#6082B6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#6082B6'></div></td>
 * </tr>
 * <tr>
 * <td><code>glossy grape</code></td>
 * <td>#AB92B3</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#AB92B3'></div></td>
 * </tr>
 * <tr>
 * <td><code>go green</code></td>
 * <td>#00AB66</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00AB66'></div></td>
 * </tr>
 * <tr>
 * <td><code>gold</code></td>
 * <td>#A57C00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A57C00'></div></td>
 * </tr>
 * <tr>
 * <td><code>gold (metallic)</code></td>
 * <td>#D4AF37</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D4AF37'></div></td>
 * </tr>
 * <tr>
 * <td><code>gold (web) (golden)</code></td>
 * <td>#FFD700</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFD700'></div></td>
 * </tr>
 * <tr>
 * <td><code>gold (crayola)</code></td>
 * <td>#E6BE8A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E6BE8A'></div></td>
 * </tr>
 * <tr>
 * <td><code>gold fusion</code></td>
 * <td>#85754E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#85754E'></div></td>
 * </tr>
 * <tr>
 * <td><code>golden brown</code></td>
 * <td>#996515</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#996515'></div></td>
 * </tr>
 * <tr>
 * <td><code>golden poppy</code></td>
 * <td>#FCC200</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FCC200'></div></td>
 * </tr>
 * <tr>
 * <td><code>golden yellow</code></td>
 * <td>#FFDF00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFDF00'></div></td>
 * </tr>
 * <tr>
 * <td><code>goldenrod</code></td>
 * <td>#DAA520</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DAA520'></div></td>
 * </tr>
 * <tr>
 * <td><code>granite gray</code></td>
 * <td>#676767</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#676767'></div></td>
 * </tr>
 * <tr>
 * <td><code>granny smith apple</code></td>
 * <td>#A8E4A0</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A8E4A0'></div></td>
 * </tr>
 * <tr>
 * <td><code>gray (web)</code></td>
 * <td>#808080</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#808080'></div></td>
 * </tr>
 * <tr>
 * <td><code>gray (x11)</code></td>
 * <td>#BEBEBE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#BEBEBE'></div></td>
 * </tr>
 * <tr>
 * <td><code>green</code></td>
 * <td>#00FF00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00FF00'></div></td>
 * </tr>
 * <tr>
 * <td><code>green (crayola)</code></td>
 * <td>#1CAC78</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#1CAC78'></div></td>
 * </tr>
 * <tr>
 * <td><code>green (html/css color)</code></td>
 * <td>#008000</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#008000'></div></td>
 * </tr>
 * <tr>
 * <td><code>green (munsell)</code></td>
 * <td>#00A877</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00A877'></div></td>
 * </tr>
 * <tr>
 * <td><code>green (ncs)</code></td>
 * <td>#009F6B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#009F6B'></div></td>
 * </tr>
 * <tr>
 * <td><code>green (pantone)</code></td>
 * <td>#00AD43</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00AD43'></div></td>
 * </tr>
 * <tr>
 * <td><code>green (pigment)</code></td>
 * <td>#00A550</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00A550'></div></td>
 * </tr>
 * <tr>
 * <td><code>green (ryb)</code></td>
 * <td>#66B032</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#66B032'></div></td>
 * </tr>
 * <tr>
 * <td><code>green-blue</code></td>
 * <td>#1164B4</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#1164B4'></div></td>
 * </tr>
 * <tr>
 * <td><code>green-blue (crayola)</code></td>
 * <td>#2887C8</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#2887C8'></div></td>
 * </tr>
 * <tr>
 * <td><code>green-cyan</code></td>
 * <td>#009966</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#009966'></div></td>
 * </tr>
 * <tr>
 * <td><code>green lizard</code></td>
 * <td>#A7F432</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A7F432'></div></td>
 * </tr>
 * <tr>
 * <td><code>green sheen</code></td>
 * <td>#6EAEA1</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#6EAEA1'></div></td>
 * </tr>
 * <tr>
 * <td><code>green-yellow</code></td>
 * <td>#ADFF2F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#ADFF2F'></div></td>
 * </tr>
 * <tr>
 * <td><code>green-yellow (crayola)</code></td>
 * <td>#F0E891</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F0E891'></div></td>
 * </tr>
 * <tr>
 * <td><code>grullo</code></td>
 * <td>#A99A86</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A99A86'></div></td>
 * </tr>
 * <tr>
 * <td><code>gunmetal</code></td>
 * <td>#2a3439</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#2a3439'></div></td>
 * </tr>
 * <tr>
 * <td><code>han blue</code></td>
 * <td>#446CCF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#446CCF'></div></td>
 * </tr>
 * <tr>
 * <td><code>han purple</code></td>
 * <td>#5218FA</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#5218FA'></div></td>
 * </tr>
 * <tr>
 * <td><code>hansa yellow</code></td>
 * <td>#E9D66B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E9D66B'></div></td>
 * </tr>
 * <tr>
 * <td><code>harlequin</code></td>
 * <td>#3FFF00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#3FFF00'></div></td>
 * </tr>
 * <tr>
 * <td><code>harvest gold</code></td>
 * <td>#DA9100</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DA9100'></div></td>
 * </tr>
 * <tr>
 * <td><code>heat wave</code></td>
 * <td>#FF7A00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF7A00'></div></td>
 * </tr>
 * <tr>
 * <td><code>heliotrope</code></td>
 * <td>#DF73FF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DF73FF'></div></td>
 * </tr>
 * <tr>
 * <td><code>heliotrope gray</code></td>
 * <td>#AA98A9</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#AA98A9'></div></td>
 * </tr>
 * <tr>
 * <td><code>hollywood cerise</code></td>
 * <td>#F400A1</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F400A1'></div></td>
 * </tr>
 * <tr>
 * <td><code>honeydew</code></td>
 * <td>#F0FFF0</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F0FFF0'></div></td>
 * </tr>
 * <tr>
 * <td><code>honolulu blue</code></td>
 * <td>#006DB0</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#006DB0'></div></td>
 * </tr>
 * <tr>
 * <td><code>hooker's green</code></td>
 * <td>#49796B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#49796B'></div></td>
 * </tr>
 * <tr>
 * <td><code>hot fuchsia</code></td>
 * <td>#FF00C6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF00C6'></div></td>
 * </tr>
 * <tr>
 * <td><code>hot magenta</code></td>
 * <td>#FF1DCE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF1DCE'></div></td>
 * </tr>
 * <tr>
 * <td><code>hot pink</code></td>
 * <td>#FF69B4</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF69B4'></div></td>
 * </tr>
 * <tr>
 * <td><code>hunter green</code></td>
 * <td>#355E3B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#355E3B'></div></td>
 * </tr>
 * <tr>
 * <td><code>iceberg</code></td>
 * <td>#71A6D2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#71A6D2'></div></td>
 * </tr>
 * <tr>
 * <td><code>icterine</code></td>
 * <td>#FCF75E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FCF75E'></div></td>
 * </tr>
 * <tr>
 * <td><code>illuminating emerald</code></td>
 * <td>#319177</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#319177'></div></td>
 * </tr>
 * <tr>
 * <td><code>imperial red</code></td>
 * <td>#ED2939</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#ED2939'></div></td>
 * </tr>
 * <tr>
 * <td><code>inchworm</code></td>
 * <td>#B2EC5D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B2EC5D'></div></td>
 * </tr>
 * <tr>
 * <td><code>independence</code></td>
 * <td>#4C516D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#4C516D'></div></td>
 * </tr>
 * <tr>
 * <td><code>india green</code></td>
 * <td>#138808</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#138808'></div></td>
 * </tr>
 * <tr>
 * <td><code>indian red</code></td>
 * <td>#CD5C5C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CD5C5C'></div></td>
 * </tr>
 * <tr>
 * <td><code>indian yellow</code></td>
 * <td>#E3A857</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E3A857'></div></td>
 * </tr>
 * <tr>
 * <td><code>indigo</code></td>
 * <td>#4B0082</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#4B0082'></div></td>
 * </tr>
 * <tr>
 * <td><code>indigo dye</code></td>
 * <td>#00416A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00416A'></div></td>
 * </tr>
 * <tr>
 * <td><code>international orange (aerospace)</code></td>
 * <td>#FF4F00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF4F00'></div></td>
 * </tr>
 * <tr>
 * <td><code>international orange (engineering)</code></td>
 * <td>#BA160C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#BA160C'></div></td>
 * </tr>
 * <tr>
 * <td><code>international orange (golden gate bridge)</code></td>
 * <td>#C0362C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C0362C'></div></td>
 * </tr>
 * <tr>
 * <td><code>iris</code></td>
 * <td>#5A4FCF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#5A4FCF'></div></td>
 * </tr>
 * <tr>
 * <td><code>irresistible</code></td>
 * <td>#B3446C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B3446C'></div></td>
 * </tr>
 * <tr>
 * <td><code>isabelline</code></td>
 * <td>#F4F0EC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F4F0EC'></div></td>
 * </tr>
 * <tr>
 * <td><code>italian sky blue</code></td>
 * <td>#B2FFFF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B2FFFF'></div></td>
 * </tr>
 * <tr>
 * <td><code>ivory</code></td>
 * <td>#FFFFF0</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFFFF0'></div></td>
 * </tr>
 * <tr>
 * <td><code>jade</code></td>
 * <td>#00A86B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00A86B'></div></td>
 * </tr>
 * <tr>
 * <td><code>japanese carmine</code></td>
 * <td>#9D2933</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9D2933'></div></td>
 * </tr>
 * <tr>
 * <td><code>japanese violet</code></td>
 * <td>#5B3256</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#5B3256'></div></td>
 * </tr>
 * <tr>
 * <td><code>jasmine</code></td>
 * <td>#F8DE7E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F8DE7E'></div></td>
 * </tr>
 * <tr>
 * <td><code>jazzberry jam</code></td>
 * <td>#A50B5E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A50B5E'></div></td>
 * </tr>
 * <tr>
 * <td><code>jet</code></td>
 * <td>#343434</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#343434'></div></td>
 * </tr>
 * <tr>
 * <td><code>jonquil</code></td>
 * <td>#F4CA16</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F4CA16'></div></td>
 * </tr>
 * <tr>
 * <td><code>june bud</code></td>
 * <td>#BDDA57</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#BDDA57'></div></td>
 * </tr>
 * <tr>
 * <td><code>jungle green</code></td>
 * <td>#29AB87</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#29AB87'></div></td>
 * </tr>
 * <tr>
 * <td><code>kelly green</code></td>
 * <td>#4CBB17</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#4CBB17'></div></td>
 * </tr>
 * <tr>
 * <td><code>keppel</code></td>
 * <td>#3AB09E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#3AB09E'></div></td>
 * </tr>
 * <tr>
 * <td><code>key lime</code></td>
 * <td>#E8F48C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E8F48C'></div></td>
 * </tr>
 * <tr>
 * <td><code>khaki (web) (khaki)</code></td>
 * <td>#C3B091</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C3B091'></div></td>
 * </tr>
 * <tr>
 * <td><code>khaki (x11) (light khaki)</code></td>
 * <td>#F0E68C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F0E68C'></div></td>
 * </tr>
 * <tr>
 * <td><code>kobe</code></td>
 * <td>#882D17</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#882D17'></div></td>
 * </tr>
 * <tr>
 * <td><code>kobi</code></td>
 * <td>#E79FC4</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E79FC4'></div></td>
 * </tr>
 * <tr>
 * <td><code>kobicha</code></td>
 * <td>#6B4423</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#6B4423'></div></td>
 * </tr>
 * <tr>
 * <td><code>kombu green</code></td>
 * <td>#354230</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#354230'></div></td>
 * </tr>
 * <tr>
 * <td><code>ksu purple</code></td>
 * <td>#512888</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#512888'></div></td>
 * </tr>
 * <tr>
 * <td><code>la salle green</code></td>
 * <td>#087830</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#087830'></div></td>
 * </tr>
 * <tr>
 * <td><code>languid lavender</code></td>
 * <td>#D6CADD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D6CADD'></div></td>
 * </tr>
 * <tr>
 * <td><code>lanzones</code></td>
 * <td>#E0BC5B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E0BC5B'></div></td>
 * </tr>
 * <tr>
 * <td><code>lapis lazuli</code></td>
 * <td>#26619C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#26619C'></div></td>
 * </tr>
 * <tr>
 * <td><code>laser lemon</code></td>
 * <td>#FFFF66</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFFF66'></div></td>
 * </tr>
 * <tr>
 * <td><code>laurel green</code></td>
 * <td>#A9BA9D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A9BA9D'></div></td>
 * </tr>
 * <tr>
 * <td><code>lava</code></td>
 * <td>#CF1020</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CF1020'></div></td>
 * </tr>
 * <tr>
 * <td><code>lavender (floral)</code></td>
 * <td>#B57EDC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B57EDC'></div></td>
 * </tr>
 * <tr>
 * <td><code>lavender (web)</code></td>
 * <td>#E6E6FA</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E6E6FA'></div></td>
 * </tr>
 * <tr>
 * <td><code>lavender blue</code></td>
 * <td>#CCCCFF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CCCCFF'></div></td>
 * </tr>
 * <tr>
 * <td><code>lavender gray</code></td>
 * <td>#C4C3D0</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C4C3D0'></div></td>
 * </tr>
 * <tr>
 * <td><code>lavender indigo</code></td>
 * <td>#9457EB</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9457EB'></div></td>
 * </tr>
 * <tr>
 * <td><code>lavender magenta</code></td>
 * <td>#EE82EE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#EE82EE'></div></td>
 * </tr>
 * <tr>
 * <td><code>lavender mist</code></td>
 * <td>#E6E6FA</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E6E6FA'></div></td>
 * </tr>
 * <tr>
 * <td><code>lavender purple</code></td>
 * <td>#967BB6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#967BB6'></div></td>
 * </tr>
 * <tr>
 * <td><code>lavender rose</code></td>
 * <td>#FBA0E3</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FBA0E3'></div></td>
 * </tr>
 * <tr>
 * <td><code>lemon</code></td>
 * <td>#FFF700</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFF700'></div></td>
 * </tr>
 * <tr>
 * <td><code>lemon chiffon</code></td>
 * <td>#FFFACD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFFACD'></div></td>
 * </tr>
 * <tr>
 * <td><code>lemon curry</code></td>
 * <td>#CCA01D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CCA01D'></div></td>
 * </tr>
 * <tr>
 * <td><code>lemon glacier</code></td>
 * <td>#FDFF00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FDFF00'></div></td>
 * </tr>
 * <tr>
 * <td><code>lemon iced tea</code></td>
 * <td>#BD3000</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#BD3000'></div></td>
 * </tr>
 * <tr>
 * <td><code>lemon lime</code></td>
 * <td>#E3FF00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E3FF00'></div></td>
 * </tr>
 * <tr>
 * <td><code>lemon lime</code></td>
 * <td>#5CFF67</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#5CFF67'></div></td>
 * </tr>
 * <tr>
 * <td><code>lemon meringue</code></td>
 * <td>#F6EABE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F6EABE'></div></td>
 * </tr>
 * <tr>
 * <td><code>lemon yellow</code></td>
 * <td>#FFF44F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFF44F'></div></td>
 * </tr>
 * <tr>
 * <td><code>lemon yellow (crayola)</code></td>
 * <td>#FFFF9F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFFF9F'></div></td>
 * </tr>
 * <tr>
 * <td><code>licorice</code></td>
 * <td>#1A1110</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#1A1110'></div></td>
 * </tr>
 * <tr>
 * <td><code>light brown</code></td>
 * <td>#B5651D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B5651D'></div></td>
 * </tr>
 * <tr>
 * <td><code>light carmine pink</code></td>
 * <td>#E66771</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E66771'></div></td>
 * </tr>
 * <tr>
 * <td><code>light chocolate cosmos</code></td>
 * <td>#551F2F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#551F2F'></div></td>
 * </tr>
 * <tr>
 * <td><code>light cobalt blue</code></td>
 * <td>#88ACE0</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#88ACE0'></div></td>
 * </tr>
 * <tr>
 * <td><code>light cornflower blue</code></td>
 * <td>#93CCEA</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#93CCEA'></div></td>
 * </tr>
 * <tr>
 * <td><code>light crimson</code></td>
 * <td>#F56991</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F56991'></div></td>
 * </tr>
 * <tr>
 * <td><code>light cyan</code></td>
 * <td>#E0FFFF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E0FFFF'></div></td>
 * </tr>
 * <tr>
 * <td><code>light french beige</code></td>
 * <td>#C8AD7F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C8AD7F'></div></td>
 * </tr>
 * <tr>
 * <td><code>light fuchsia pink</code></td>
 * <td>#F984EF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F984EF'></div></td>
 * </tr>
 * <tr>
 * <td><code>light gold</code></td>
 * <td>#B29700</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B29700'></div></td>
 * </tr>
 * <tr>
 * <td><code>light grayish magenta</code></td>
 * <td>#CC99CC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CC99CC'></div></td>
 * </tr>
 * <tr>
 * <td><code>light medium orchid</code></td>
 * <td>#D39BCB</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D39BCB'></div></td>
 * </tr>
 * <tr>
 * <td><code>light moss green</code></td>
 * <td>#ADDFAD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#ADDFAD'></div></td>
 * </tr>
 * <tr>
 * <td><code>light orchid</code></td>
 * <td>#E6A8D7</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E6A8D7'></div></td>
 * </tr>
 * <tr>
 * <td><code>light pastel purple</code></td>
 * <td>#B19CD9</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B19CD9'></div></td>
 * </tr>
 * <tr>
 * <td><code>light periwinkle</code></td>
 * <td>#C5CBE1</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C5CBE1'></div></td>
 * </tr>
 * <tr>
 * <td><code>light red</code></td>
 * <td>#FFCCCB</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFCCCB'></div></td>
 * </tr>
 * <tr>
 * <td><code>light red ochre</code></td>
 * <td>#E97451</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E97451'></div></td>
 * </tr>
 * <tr>
 * <td><code>light salmon pink</code></td>
 * <td>#FF9999</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF9999'></div></td>
 * </tr>
 * <tr>
 * <td><code>light sea green</code></td>
 * <td>#20B2AA</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#20B2AA'></div></td>
 * </tr>
 * <tr>
 * <td><code>light silver</code></td>
 * <td>#D8D8D8</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D8D8D8'></div></td>
 * </tr>
 * <tr>
 * <td><code>light thulian pink</code></td>
 * <td>#E68FAC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E68FAC'></div></td>
 * </tr>
 * <tr>
 * <td><code>lilac</code></td>
 * <td>#C8A2C8</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C8A2C8'></div></td>
 * </tr>
 * <tr>
 * <td><code>lilac luster</code></td>
 * <td>#AE98AA</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#AE98AA'></div></td>
 * </tr>
 * <tr>
 * <td><code>lime (color wheel)</code></td>
 * <td>#BFFF00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#BFFF00'></div></td>
 * </tr>
 * <tr>
 * <td><code>lime (web) (x11 green)</code></td>
 * <td>#00FF00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00FF00'></div></td>
 * </tr>
 * <tr>
 * <td><code>lime green</code></td>
 * <td>#32CD32</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#32CD32'></div></td>
 * </tr>
 * <tr>
 * <td><code>limerick</code></td>
 * <td>#9DC209</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9DC209'></div></td>
 * </tr>
 * <tr>
 * <td><code>lincoln green</code></td>
 * <td>#195905</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#195905'></div></td>
 * </tr>
 * <tr>
 * <td><code>linen</code></td>
 * <td>#FAF0E6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FAF0E6'></div></td>
 * </tr>
 * <tr>
 * <td><code>lion</code></td>
 * <td>#C19A6B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C19A6B'></div></td>
 * </tr>
 * <tr>
 * <td><code>liseran purple</code></td>
 * <td>#DE6FA1</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DE6FA1'></div></td>
 * </tr>
 * <tr>
 * <td><code>little boy blue</code></td>
 * <td>#6CA0DC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#6CA0DC'></div></td>
 * </tr>
 * <tr>
 * <td><code>little girl pink</code></td>
 * <td>#F8B9D4</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F8B9D4'></div></td>
 * </tr>
 * <tr>
 * <td><code>liver</code></td>
 * <td>#674C47</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#674C47'></div></td>
 * </tr>
 * <tr>
 * <td><code>liver (dogs)</code></td>
 * <td>#B86D29</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B86D29'></div></td>
 * </tr>
 * <tr>
 * <td><code>liver (organ)</code></td>
 * <td>#6C2E1F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#6C2E1F'></div></td>
 * </tr>
 * <tr>
 * <td><code>liver chestnut</code></td>
 * <td>#987456</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#987456'></div></td>
 * </tr>
 * <tr>
 * <td><code>livid</code></td>
 * <td>#6699CC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#6699CC'></div></td>
 * </tr>
 * <tr>
 * <td><code>lotion</code></td>
 * <td>#FEFDFA</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FEFDFA'></div></td>
 * </tr>
 * <tr>
 * <td><code>lotion blue</code></td>
 * <td>#15F2FD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#15F2FD'></div></td>
 * </tr>
 * <tr>
 * <td><code>lotion pink</code></td>
 * <td>#ECCFCF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#ECCFCF'></div></td>
 * </tr>
 * <tr>
 * <td><code>lumber</code></td>
 * <td>#FFE4CD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFE4CD'></div></td>
 * </tr>
 * <tr>
 * <td><code>lust</code></td>
 * <td>#E62020</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E62020'></div></td>
 * </tr>
 * <tr>
 * <td><code>maastricht blue</code></td>
 * <td>#001C3D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#001C3D'></div></td>
 * </tr>
 * <tr>
 * <td><code>macaroni and cheese</code></td>
 * <td>#FFBD88</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFBD88'></div></td>
 * </tr>
 * <tr>
 * <td><code>madder lake</code></td>
 * <td>#CC3336</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CC3336'></div></td>
 * </tr>
 * <tr>
 * <td><code>magenta</code></td>
 * <td>#FF00FF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF00FF'></div></td>
 * </tr>
 * <tr>
 * <td><code>magenta (pantone)</code></td>
 * <td>#D0417E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D0417E'></div></td>
 * </tr>
 * <tr>
 * <td><code>magic mint</code></td>
 * <td>#AAF0D1</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#AAF0D1'></div></td>
 * </tr>
 * <tr>
 * <td><code>mahogany</code></td>
 * <td>#C04000</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C04000'></div></td>
 * </tr>
 * <tr>
 * <td><code>maize</code></td>
 * <td>#FBEC5D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FBEC5D'></div></td>
 * </tr>
 * <tr>
 * <td><code>maize (crayola)</code></td>
 * <td>#F2C649</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F2C649'></div></td>
 * </tr>
 * <tr>
 * <td><code>majorelle blue</code></td>
 * <td>#6050DC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#6050DC'></div></td>
 * </tr>
 * <tr>
 * <td><code>malachite</code></td>
 * <td>#0BDA51</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#0BDA51'></div></td>
 * </tr>
 * <tr>
 * <td><code>manatee</code></td>
 * <td>#979AAA</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#979AAA'></div></td>
 * </tr>
 * <tr>
 * <td><code>mandarin</code></td>
 * <td>#F37A48</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F37A48'></div></td>
 * </tr>
 * <tr>
 * <td><code>mango</code></td>
 * <td>#FDBE02</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FDBE02'></div></td>
 * </tr>
 * <tr>
 * <td><code>mango green</code></td>
 * <td>#96FF00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#96FF00'></div></td>
 * </tr>
 * <tr>
 * <td><code>mango tango</code></td>
 * <td>#FF8243</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF8243'></div></td>
 * </tr>
 * <tr>
 * <td><code>mantis</code></td>
 * <td>#74C365</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#74C365'></div></td>
 * </tr>
 * <tr>
 * <td><code>mardi gras</code></td>
 * <td>#880085</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#880085'></div></td>
 * </tr>
 * <tr>
 * <td><code>marigold</code></td>
 * <td>#EAA221</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#EAA221'></div></td>
 * </tr>
 * <tr>
 * <td><code>maroon (crayola)</code></td>
 * <td>#C32148</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C32148'></div></td>
 * </tr>
 * <tr>
 * <td><code>maroon (html/css)</code></td>
 * <td>#800000</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#800000'></div></td>
 * </tr>
 * <tr>
 * <td><code>maroon (x11)</code></td>
 * <td>#B03060</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B03060'></div></td>
 * </tr>
 * <tr>
 * <td><code>mauve</code></td>
 * <td>#E0B0FF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E0B0FF'></div></td>
 * </tr>
 * <tr>
 * <td><code>mauve taupe</code></td>
 * <td>#915F6D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#915F6D'></div></td>
 * </tr>
 * <tr>
 * <td><code>mauvelous</code></td>
 * <td>#EF98AA</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#EF98AA'></div></td>
 * </tr>
 * <tr>
 * <td><code>maximum blue</code></td>
 * <td>#47ABCC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#47ABCC'></div></td>
 * </tr>
 * <tr>
 * <td><code>maximum blue green</code></td>
 * <td>#30BFBF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#30BFBF'></div></td>
 * </tr>
 * <tr>
 * <td><code>maximum blue purple</code></td>
 * <td>#ACACE6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#ACACE6'></div></td>
 * </tr>
 * <tr>
 * <td><code>maximum green</code></td>
 * <td>#5E8C31</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#5E8C31'></div></td>
 * </tr>
 * <tr>
 * <td><code>maximum green yellow</code></td>
 * <td>#D9E650</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D9E650'></div></td>
 * </tr>
 * <tr>
 * <td><code>maximum orange</code></td>
 * <td>#FF5B00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF5B00'></div></td>
 * </tr>
 * <tr>
 * <td><code>maximum purple</code></td>
 * <td>#733380</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#733380'></div></td>
 * </tr>
 * <tr>
 * <td><code>maximum pink</code></td>
 * <td>#F6A5F2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F6A5F2'></div></td>
 * </tr>
 * <tr>
 * <td><code>maximum red</code></td>
 * <td>#D92121</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D92121'></div></td>
 * </tr>
 * <tr>
 * <td><code>maximum red purple</code></td>
 * <td>#A63A79</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A63A79'></div></td>
 * </tr>
 * <tr>
 * <td><code>maximum violet</code></td>
 * <td>#892F77</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#892F77'></div></td>
 * </tr>
 * <tr>
 * <td><code>maximum yellow</code></td>
 * <td>#FAFA37</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FAFA37'></div></td>
 * </tr>
 * <tr>
 * <td><code>maximum yellow red</code></td>
 * <td>#F2BA49</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F2BA49'></div></td>
 * </tr>
 * <tr>
 * <td><code>may green</code></td>
 * <td>#4C9141</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#4C9141'></div></td>
 * </tr>
 * <tr>
 * <td><code>maya blue</code></td>
 * <td>#73C2FB</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#73C2FB'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium aquamarine</code></td>
 * <td>#66DDAA</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#66DDAA'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium blue</code></td>
 * <td>#0000CD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#0000CD'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium candy apple red</code></td>
 * <td>#E2062C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E2062C'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium carmine</code></td>
 * <td>#AF4035</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#AF4035'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium champagne</code></td>
 * <td>#F3E5AB</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F3E5AB'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium electric blue</code></td>
 * <td>#035096</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#035096'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium green</code></td>
 * <td>#037949</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#037949'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium jungle green</code></td>
 * <td>#1C352D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#1C352D'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium lavender magenta</code></td>
 * <td>#DDA0DD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DDA0DD'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium orange</code></td>
 * <td>#FF7802</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF7802'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium orchid</code></td>
 * <td>#BA55D3</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#BA55D3'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium persian blue</code></td>
 * <td>#0067A5</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#0067A5'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium pink</code></td>
 * <td>#FE6E9F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FE6E9F'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium purple</code></td>
 * <td>#9370DB</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9370DB'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium red</code></td>
 * <td>#B10304</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B10304'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium red-violet</code></td>
 * <td>#BB3385</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#BB3385'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium ruby</code></td>
 * <td>#AA4069</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#AA4069'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium sea green</code></td>
 * <td>#3CB371</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#3CB371'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium sky blue</code></td>
 * <td>#80DAEB</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#80DAEB'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium slate blue</code></td>
 * <td>#7B68EE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#7B68EE'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium spring bud</code></td>
 * <td>#C9DC87</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C9DC87'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium spring green</code></td>
 * <td>#00FA9A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00FA9A'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium taupe</code></td>
 * <td>#674C47</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#674C47'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium turquoise</code></td>
 * <td>#48D1CC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#48D1CC'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium tuscan red</code></td>
 * <td>#79443B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#79443B'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium vermilion</code></td>
 * <td>#D9603B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D9603B'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium violet</code></td>
 * <td>#65315F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#65315F'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium violet-red</code></td>
 * <td>#C71585</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C71585'></div></td>
 * </tr>
 * <tr>
 * <td><code>medium yellow</code></td>
 * <td>#FFE302</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFE302'></div></td>
 * </tr>
 * <tr>
 * <td><code>mellow apricot</code></td>
 * <td>#F8B878</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F8B878'></div></td>
 * </tr>
 * <tr>
 * <td><code>mellow yellow</code></td>
 * <td>#F8DE7E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F8DE7E'></div></td>
 * </tr>
 * <tr>
 * <td><code>melon</code></td>
 * <td>#FDBCB4</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FDBCB4'></div></td>
 * </tr>
 * <tr>
 * <td><code>melon (crayola)</code></td>
 * <td>#FEBAAD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FEBAAD'></div></td>
 * </tr>
 * <tr>
 * <td><code>menthol</code></td>
 * <td>#C1F9A2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C1F9A2'></div></td>
 * </tr>
 * <tr>
 * <td><code>metallic blue</code></td>
 * <td>#32527B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#32527B'></div></td>
 * </tr>
 * <tr>
 * <td><code>metallic bronze</code></td>
 * <td>#A97142</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A97142'></div></td>
 * </tr>
 * <tr>
 * <td><code>metallic brown</code></td>
 * <td>#AC4313</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#AC4313'></div></td>
 * </tr>
 * <tr>
 * <td><code>metallic gold</code></td>
 * <td>#D3AF37</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D3AF37'></div></td>
 * </tr>
 * <tr>
 * <td><code>metallic green</code></td>
 * <td>#296E01</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#296E01'></div></td>
 * </tr>
 * <tr>
 * <td><code>metallic orange</code></td>
 * <td>#DA680F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DA680F'></div></td>
 * </tr>
 * <tr>
 * <td><code>metallic pink</code></td>
 * <td>#EDA6C4</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#EDA6C4'></div></td>
 * </tr>
 * <tr>
 * <td><code>metallic red</code></td>
 * <td>#A62C2B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A62C2B'></div></td>
 * </tr>
 * <tr>
 * <td><code>metallic seaweed</code></td>
 * <td>#0A7E8C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#0A7E8C'></div></td>
 * </tr>
 * <tr>
 * <td><code>metallic silver</code></td>
 * <td>#A8A9AD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A8A9AD'></div></td>
 * </tr>
 * <tr>
 * <td><code>metallic sunburst</code></td>
 * <td>#9C7C38</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9C7C38'></div></td>
 * </tr>
 * <tr>
 * <td><code>metallic violet</code></td>
 * <td>#5B0A91</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#5B0A91'></div></td>
 * </tr>
 * <tr>
 * <td><code>metallic yellow</code></td>
 * <td>#FDCC0D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FDCC0D'></div></td>
 * </tr>
 * <tr>
 * <td><code>mexican pink</code></td>
 * <td>#E4007C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E4007C'></div></td>
 * </tr>
 * <tr>
 * <td><code>microsoft blue</code></td>
 * <td>#00A2ED</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00A2ED'></div></td>
 * </tr>
 * <tr>
 * <td><code>microsoft edge blue</code></td>
 * <td>#0078D7</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#0078D7'></div></td>
 * </tr>
 * <tr>
 * <td><code>microsoft green</code></td>
 * <td>#7DB700</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#7DB700'></div></td>
 * </tr>
 * <tr>
 * <td><code>microsoft red</code></td>
 * <td>#F04E1F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F04E1F'></div></td>
 * </tr>
 * <tr>
 * <td><code>microsoft yellow</code></td>
 * <td>#FDB900</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FDB900'></div></td>
 * </tr>
 * <tr>
 * <td><code>middle blue</code></td>
 * <td>#7ED4E6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#7ED4E6'></div></td>
 * </tr>
 * <tr>
 * <td><code>middle blue green</code></td>
 * <td>#8DD9CC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8DD9CC'></div></td>
 * </tr>
 * <tr>
 * <td><code>middle blue purple</code></td>
 * <td>#8B72BE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8B72BE'></div></td>
 * </tr>
 * <tr>
 * <td><code>middle grey</code></td>
 * <td>#8B8680</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8B8680'></div></td>
 * </tr>
 * <tr>
 * <td><code>middle green</code></td>
 * <td>#4D8C57</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#4D8C57'></div></td>
 * </tr>
 * <tr>
 * <td><code>middle green yellow</code></td>
 * <td>#ACBF60</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#ACBF60'></div></td>
 * </tr>
 * <tr>
 * <td><code>middle purple</code></td>
 * <td>#D982B5</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D982B5'></div></td>
 * </tr>
 * <tr>
 * <td><code>middle red</code></td>
 * <td>#E58E73</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E58E73'></div></td>
 * </tr>
 * <tr>
 * <td><code>middle red purple</code></td>
 * <td>#A55353</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A55353'></div></td>
 * </tr>
 * <tr>
 * <td><code>middle yellow</code></td>
 * <td>#FFEB00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFEB00'></div></td>
 * </tr>
 * <tr>
 * <td><code>middle yellow red</code></td>
 * <td>#ECB176</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#ECB176'></div></td>
 * </tr>
 * <tr>
 * <td><code>midnight</code></td>
 * <td>#702670</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#702670'></div></td>
 * </tr>
 * <tr>
 * <td><code>midnight blue</code></td>
 * <td>#191970</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#191970'></div></td>
 * </tr>
 * <tr>
 * <td><code>midnight blue</code></td>
 * <td>#00468C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00468C'></div></td>
 * </tr>
 * <tr>
 * <td><code>midnight green (eagle green)</code></td>
 * <td>#004953</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#004953'></div></td>
 * </tr>
 * <tr>
 * <td><code>mikado yellow</code></td>
 * <td>#FFC40C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFC40C'></div></td>
 * </tr>
 * <tr>
 * <td><code>milk</code></td>
 * <td>#FDFFF5</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FDFFF5'></div></td>
 * </tr>
 * <tr>
 * <td><code>milk chocolate</code></td>
 * <td>#84563C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#84563C'></div></td>
 * </tr>
 * <tr>
 * <td><code>mimi pink</code></td>
 * <td>#FFDAE9</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFDAE9'></div></td>
 * </tr>
 * <tr>
 * <td><code>mindaro</code></td>
 * <td>#E3F988</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E3F988'></div></td>
 * </tr>
 * <tr>
 * <td><code>ming</code></td>
 * <td>#36747D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#36747D'></div></td>
 * </tr>
 * <tr>
 * <td><code>minion yellow</code></td>
 * <td>#F5E050</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F5E050'></div></td>
 * </tr>
 * <tr>
 * <td><code>mint</code></td>
 * <td>#3EB489</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#3EB489'></div></td>
 * </tr>
 * <tr>
 * <td><code>mint cream</code></td>
 * <td>#F5FFFA</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F5FFFA'></div></td>
 * </tr>
 * <tr>
 * <td><code>mint green</code></td>
 * <td>#98FF98</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#98FF98'></div></td>
 * </tr>
 * <tr>
 * <td><code>misty moss</code></td>
 * <td>#BBB477</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#BBB477'></div></td>
 * </tr>
 * <tr>
 * <td><code>misty rose</code></td>
 * <td>#FFE4E1</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFE4E1'></div></td>
 * </tr>
 * <tr>
 * <td><code>moccasin</code></td>
 * <td>#FAEBD7</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FAEBD7'></div></td>
 * </tr>
 * <tr>
 * <td><code>mocha</code></td>
 * <td>#BEA493</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#BEA493'></div></td>
 * </tr>
 * <tr>
 * <td><code>mode beige</code></td>
 * <td>#967117</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#967117'></div></td>
 * </tr>
 * <tr>
 * <td><code>moonstone</code></td>
 * <td>#3AA8C1</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#3AA8C1'></div></td>
 * </tr>
 * <tr>
 * <td><code>moonstone blue</code></td>
 * <td>#73A9C2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#73A9C2'></div></td>
 * </tr>
 * <tr>
 * <td><code>mordant red 19</code></td>
 * <td>#AE0C00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#AE0C00'></div></td>
 * </tr>
 * <tr>
 * <td><code>morning blue</code></td>
 * <td>#8DA399</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8DA399'></div></td>
 * </tr>
 * <tr>
 * <td><code>moss green</code></td>
 * <td>#8A9A5B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8A9A5B'></div></td>
 * </tr>
 * <tr>
 * <td><code>mountain meadow</code></td>
 * <td>#30BA8F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#30BA8F'></div></td>
 * </tr>
 * <tr>
 * <td><code>mountbatten pink</code></td>
 * <td>#997A8D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#997A8D'></div></td>
 * </tr>
 * <tr>
 * <td><code>msu green</code></td>
 * <td>#18453B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#18453B'></div></td>
 * </tr>
 * <tr>
 * <td><code>mud</code></td>
 * <td>#70543E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#70543E'></div></td>
 * </tr>
 * <tr>
 * <td><code>mughal green</code></td>
 * <td>#306030</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#306030'></div></td>
 * </tr>
 * <tr>
 * <td><code>mulberry</code></td>
 * <td>#C54B8C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C54B8C'></div></td>
 * </tr>
 * <tr>
 * <td><code>mulberry (crayola)</code></td>
 * <td>#C8509B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C8509B'></div></td>
 * </tr>
 * <tr>
 * <td><code>mummy's tomb</code></td>
 * <td>#828E84</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#828E84'></div></td>
 * </tr>
 * <tr>
 * <td><code>mustard</code></td>
 * <td>#FFDB58</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFDB58'></div></td>
 * </tr>
 * <tr>
 * <td><code>mustard brown</code></td>
 * <td>#CD7A00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CD7A00'></div></td>
 * </tr>
 * <tr>
 * <td><code>mustard green</code></td>
 * <td>#6E6E30</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#6E6E30'></div></td>
 * </tr>
 * <tr>
 * <td><code>mustard yellow</code></td>
 * <td>#E1AD01</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E1AD01'></div></td>
 * </tr>
 * <tr>
 * <td><code>myrtle green</code></td>
 * <td>#317873</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#317873'></div></td>
 * </tr>
 * <tr>
 * <td><code>mystic</code></td>
 * <td>#D65282</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D65282'></div></td>
 * </tr>
 * <tr>
 * <td><code>mystic maroon</code></td>
 * <td>#AD4379</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#AD4379'></div></td>
 * </tr>
 * <tr>
 * <td><code>mystic red</code></td>
 * <td>#FF5500</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF5500'></div></td>
 * </tr>
 * <tr>
 * <td><code>nadeshiko pink</code></td>
 * <td>#F6ADC6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F6ADC6'></div></td>
 * </tr>
 * <tr>
 * <td><code>napier green</code></td>
 * <td>#2A8000</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#2A8000'></div></td>
 * </tr>
 * <tr>
 * <td><code>naples yellow</code></td>
 * <td>#FADA5E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FADA5E'></div></td>
 * </tr>
 * <tr>
 * <td><code>navajo white</code></td>
 * <td>#FFDEAD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFDEAD'></div></td>
 * </tr>
 * <tr>
 * <td><code>navy blue</code></td>
 * <td>#000080</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#000080'></div></td>
 * </tr>
 * <tr>
 * <td><code>navy blue (crayola)</code></td>
 * <td>#1974D2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#1974D2'></div></td>
 * </tr>
 * <tr>
 * <td><code>navy purple</code></td>
 * <td>#9457EB</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9457EB'></div></td>
 * </tr>
 * <tr>
 * <td><code>neon blue</code></td>
 * <td>#1B03A3</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#1B03A3'></div></td>
 * </tr>
 * <tr>
 * <td><code>neon brown</code></td>
 * <td>#C3732A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C3732A'></div></td>
 * </tr>
 * <tr>
 * <td><code>neon carrot</code></td>
 * <td>#AAF0D1</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#AAF0D1'></div></td>
 * </tr>
 * <tr>
 * <td><code>neon cyan</code></td>
 * <td>#00FEFC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00FEFC'></div></td>
 * </tr>
 * <tr>
 * <td><code>neon fuchsia</code></td>
 * <td>#FE4164</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FE4164'></div></td>
 * </tr>
 * <tr>
 * <td><code>neon gold</code></td>
 * <td>#CFAA01</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CFAA01'></div></td>
 * </tr>
 * <tr>
 * <td><code>neon gray</code></td>
 * <td>#808080</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#808080'></div></td>
 * </tr>
 * <tr>
 * <td><code>neon dark green</code></td>
 * <td>#008443</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#008443'></div></td>
 * </tr>
 * <tr>
 * <td><code>neon green</code></td>
 * <td>#139B42</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#139B42'></div></td>
 * </tr>
 * <tr>
 * <td><code>neon green</code></td>
 * <td>#39FF14</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#39FF14'></div></td>
 * </tr>
 * <tr>
 * <td><code>neon pink</code></td>
 * <td>#FE347E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FE347E'></div></td>
 * </tr>
 * <tr>
 * <td><code>neon purple</code></td>
 * <td>#9457EB</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9457EB'></div></td>
 * </tr>
 * <tr>
 * <td><code>neon red</code></td>
 * <td>#FF1818</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF1818'></div></td>
 * </tr>
 * <tr>
 * <td><code>neon scarlet</code></td>
 * <td>#FF2603</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF2603'></div></td>
 * </tr>
 * <tr>
 * <td><code>neon silver</code></td>
 * <td>#CCCCCC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CCCCCC'></div></td>
 * </tr>
 * <tr>
 * <td><code>neon tangerine</code></td>
 * <td>#F6890A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F6890A'></div></td>
 * </tr>
 * <tr>
 * <td><code>neon yellow</code></td>
 * <td>#FFF700</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFF700'></div></td>
 * </tr>
 * <tr>
 * <td><code>new car</code></td>
 * <td>#214FC6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#214FC6'></div></td>
 * </tr>
 * <tr>
 * <td><code>new york pink</code></td>
 * <td>#D7837F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D7837F'></div></td>
 * </tr>
 * <tr>
 * <td><code>nickel</code></td>
 * <td>#727472</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#727472'></div></td>
 * </tr>
 * <tr>
 * <td><code>nintendo red</code></td>
 * <td>#E4000F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E4000F'></div></td>
 * </tr>
 * <tr>
 * <td><code>non-photo blue</code></td>
 * <td>#A4DDED</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A4DDED'></div></td>
 * </tr>
 * <tr>
 * <td><code>nyanza</code></td>
 * <td>#E9FFDB</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E9FFDB'></div></td>
 * </tr>
 * <tr>
 * <td><code>ocean boat blue</code></td>
 * <td>#0077BE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#0077BE'></div></td>
 * </tr>
 * <tr>
 * <td><code>ochre</code></td>
 * <td>#CC7722</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CC7722'></div></td>
 * </tr>
 * <tr>
 * <td><code>office green</code></td>
 * <td>#008000</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#008000'></div></td>
 * </tr>
 * <tr>
 * <td><code>old burgundy</code></td>
 * <td>#43302E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#43302E'></div></td>
 * </tr>
 * <tr>
 * <td><code>old gold</code></td>
 * <td>#CFB53B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CFB53B'></div></td>
 * </tr>
 * <tr>
 * <td><code>old heliotrope</code></td>
 * <td>#563C5C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#563C5C'></div></td>
 * </tr>
 * <tr>
 * <td><code>old lace</code></td>
 * <td>#FDF5E6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FDF5E6'></div></td>
 * </tr>
 * <tr>
 * <td><code>old lavender</code></td>
 * <td>#796878</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#796878'></div></td>
 * </tr>
 * <tr>
 * <td><code>old mauve</code></td>
 * <td>#673147</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#673147'></div></td>
 * </tr>
 * <tr>
 * <td><code>old moss green</code></td>
 * <td>#867E36</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#867E36'></div></td>
 * </tr>
 * <tr>
 * <td><code>old rose</code></td>
 * <td>#C08081</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C08081'></div></td>
 * </tr>
 * <tr>
 * <td><code>old silver</code></td>
 * <td>#848482</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#848482'></div></td>
 * </tr>
 * <tr>
 * <td><code>olive</code></td>
 * <td>#808000</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#808000'></div></td>
 * </tr>
 * <tr>
 * <td><code>olive drab (#3)</code></td>
 * <td>#6B8E23</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#6B8E23'></div></td>
 * </tr>
 * <tr>
 * <td><code>olive drab #7</code></td>
 * <td>#3C341F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#3C341F'></div></td>
 * </tr>
 * <tr>
 * <td><code>olive green</code></td>
 * <td>#B5B35C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B5B35C'></div></td>
 * </tr>
 * <tr>
 * <td><code>olivine</code></td>
 * <td>#9AB973</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9AB973'></div></td>
 * </tr>
 * <tr>
 * <td><code>onyx</code></td>
 * <td>#353839</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#353839'></div></td>
 * </tr>
 * <tr>
 * <td><code>opal</code></td>
 * <td>#A8C3BC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A8C3BC'></div></td>
 * </tr>
 * <tr>
 * <td><code>opera mauve</code></td>
 * <td>#B784A7</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B784A7'></div></td>
 * </tr>
 * <tr>
 * <td><code>orange</code></td>
 * <td>#FF6600</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF6600'></div></td>
 * </tr>
 * <tr>
 * <td><code>orange (color wheel)</code></td>
 * <td>#FF7F00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF7F00'></div></td>
 * </tr>
 * <tr>
 * <td><code>orange (crayola)</code></td>
 * <td>#FF7538</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF7538'></div></td>
 * </tr>
 * <tr>
 * <td><code>orange (pantone)</code></td>
 * <td>#FF5800</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF5800'></div></td>
 * </tr>
 * <tr>
 * <td><code>orange (ryb)</code></td>
 * <td>#FB9902</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FB9902'></div></td>
 * </tr>
 * <tr>
 * <td><code>orange (web)</code></td>
 * <td>#FFA500</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFA500'></div></td>
 * </tr>
 * <tr>
 * <td><code>orange iced tea</code></td>
 * <td>#FF6700</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF6700'></div></td>
 * </tr>
 * <tr>
 * <td><code>orange-red (crayola)</code></td>
 * <td>#FF5349</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF5349'></div></td>
 * </tr>
 * <tr>
 * <td><code>orange soda</code></td>
 * <td>#E74E14</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E74E14'></div></td>
 * </tr>
 * <tr>
 * <td><code>orange soda</code></td>
 * <td>#FA5B3D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FA5B3D'></div></td>
 * </tr>
 * <tr>
 * <td><code>orange-yellow</code></td>
 * <td>#F5BD1F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F5BD1F'></div></td>
 * </tr>
 * <tr>
 * <td><code>orange-yellow (crayola)</code></td>
 * <td>#F8D568</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F8D568'></div></td>
 * </tr>
 * <tr>
 * <td><code>orchid</code></td>
 * <td>#DA70D6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DA70D6'></div></td>
 * </tr>
 * <tr>
 * <td><code>orchid pink</code></td>
 * <td>#F2BDCD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F2BDCD'></div></td>
 * </tr>
 * <tr>
 * <td><code>orchid (crayola)</code></td>
 * <td>#E29CD2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E29CD2'></div></td>
 * </tr>
 * <tr>
 * <td><code>orioles orange</code></td>
 * <td>#FB4F14</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FB4F14'></div></td>
 * </tr>
 * <tr>
 * <td><code>otter brown</code></td>
 * <td>#654321</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#654321'></div></td>
 * </tr>
 * <tr>
 * <td><code>outer space</code></td>
 * <td>#414A4C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#414A4C'></div></td>
 * </tr>
 * <tr>
 * <td><code>outer space (crayola)</code></td>
 * <td>#2D383A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#2D383A'></div></td>
 * </tr>
 * <tr>
 * <td><code>outrageous orange</code></td>
 * <td>#FF6037</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF6037'></div></td>
 * </tr>
 * <tr>
 * <td><code>oxblood</code></td>
 * <td>#800020</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#800020'></div></td>
 * </tr>
 * <tr>
 * <td><code>oxford blue</code></td>
 * <td>#002147</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#002147'></div></td>
 * </tr>
 * <tr>
 * <td><code>oxley</code></td>
 * <td>#6D9A79</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#6D9A79'></div></td>
 * </tr>
 * <tr>
 * <td><code>ou crimson red</code></td>
 * <td>#990000</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#990000'></div></td>
 * </tr>
 * <tr>
 * <td><code>pacific blue</code></td>
 * <td>#1CA9C9</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#1CA9C9'></div></td>
 * </tr>
 * <tr>
 * <td><code>pakistan green</code></td>
 * <td>#006600</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#006600'></div></td>
 * </tr>
 * <tr>
 * <td><code>palatinate blue</code></td>
 * <td>#273BE2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#273BE2'></div></td>
 * </tr>
 * <tr>
 * <td><code>palatinate purple</code></td>
 * <td>#682860</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#682860'></div></td>
 * </tr>
 * <tr>
 * <td><code>pale aqua</code></td>
 * <td>#BCD4E6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#BCD4E6'></div></td>
 * </tr>
 * <tr>
 * <td><code>pale blue</code></td>
 * <td>#AFEEEE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#AFEEEE'></div></td>
 * </tr>
 * <tr>
 * <td><code>pale brown</code></td>
 * <td>#987654</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#987654'></div></td>
 * </tr>
 * <tr>
 * <td><code>pale carmine</code></td>
 * <td>#AF4035</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#AF4035'></div></td>
 * </tr>
 * <tr>
 * <td><code>pale cerulean</code></td>
 * <td>#9BC4E2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9BC4E2'></div></td>
 * </tr>
 * <tr>
 * <td><code>pale chestnut</code></td>
 * <td>#DDADAF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DDADAF'></div></td>
 * </tr>
 * <tr>
 * <td><code>pale copper</code></td>
 * <td>#DA8A67</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DA8A67'></div></td>
 * </tr>
 * <tr>
 * <td><code>pale cornflower blue</code></td>
 * <td>#ABCDEF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#ABCDEF'></div></td>
 * </tr>
 * <tr>
 * <td><code>pale cyan</code></td>
 * <td>#87D3F8</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#87D3F8'></div></td>
 * </tr>
 * <tr>
 * <td><code>pale gold</code></td>
 * <td>#E6BE8A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E6BE8A'></div></td>
 * </tr>
 * <tr>
 * <td><code>pale goldenrod</code></td>
 * <td>#EEE8AA</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#EEE8AA'></div></td>
 * </tr>
 * <tr>
 * <td><code>pale green</code></td>
 * <td>#98FB98</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#98FB98'></div></td>
 * </tr>
 * <tr>
 * <td><code>pale lavender</code></td>
 * <td>#DCD0FF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DCD0FF'></div></td>
 * </tr>
 * <tr>
 * <td><code>pale magenta</code></td>
 * <td>#F984E5</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F984E5'></div></td>
 * </tr>
 * <tr>
 * <td><code>pale pink</code></td>
 * <td>#FADADD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FADADD'></div></td>
 * </tr>
 * <tr>
 * <td><code>pale plum</code></td>
 * <td>#DDA0DD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DDA0DD'></div></td>
 * </tr>
 * <tr>
 * <td><code>pale robin egg blue</code></td>
 * <td>#96DED1</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#96DED1'></div></td>
 * </tr>
 * <tr>
 * <td><code>pale silver</code></td>
 * <td>#C9C0BB</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C9C0BB'></div></td>
 * </tr>
 * <tr>
 * <td><code>pale spring bud</code></td>
 * <td>#ECEBBD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#ECEBBD'></div></td>
 * </tr>
 * <tr>
 * <td><code>pale taupe</code></td>
 * <td>#BC987E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#BC987E'></div></td>
 * </tr>
 * <tr>
 * <td><code>pale turquoise</code></td>
 * <td>#AFEEEE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#AFEEEE'></div></td>
 * </tr>
 * <tr>
 * <td><code>pale violet</code></td>
 * <td>#CC99FF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CC99FF'></div></td>
 * </tr>
 * <tr>
 * <td><code>pale violet-red</code></td>
 * <td>#DB7093</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DB7093'></div></td>
 * </tr>
 * <tr>
 * <td><code>pansy purple</code></td>
 * <td>#78184A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#78184A'></div></td>
 * </tr>
 * <tr>
 * <td><code>paolo veronese green</code></td>
 * <td>#009B7D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#009B7D'></div></td>
 * </tr>
 * <tr>
 * <td><code>papaya whip</code></td>
 * <td>#FFEFD5</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFEFD5'></div></td>
 * </tr>
 * <tr>
 * <td><code>paradise pink</code></td>
 * <td>#E63E62</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E63E62'></div></td>
 * </tr>
 * <tr>
 * <td><code>parchment</code></td>
 * <td>#F1E9D2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F1E9D2'></div></td>
 * </tr>
 * <tr>
 * <td><code>paris green</code></td>
 * <td>#50C878</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#50C878'></div></td>
 * </tr>
 * <tr>
 * <td><code>pastel blue</code></td>
 * <td>#AEC6CF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#AEC6CF'></div></td>
 * </tr>
 * <tr>
 * <td><code>pastel brown</code></td>
 * <td>#836953</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#836953'></div></td>
 * </tr>
 * <tr>
 * <td><code>pastel gray</code></td>
 * <td>#CFCFC4</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CFCFC4'></div></td>
 * </tr>
 * <tr>
 * <td><code>pastel green</code></td>
 * <td>#77DD77</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#77DD77'></div></td>
 * </tr>
 * <tr>
 * <td><code>pastel magenta</code></td>
 * <td>#F49AC2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F49AC2'></div></td>
 * </tr>
 * <tr>
 * <td><code>pastel orange</code></td>
 * <td>#FFB347</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFB347'></div></td>
 * </tr>
 * <tr>
 * <td><code>pastel pink</code></td>
 * <td>#DEA5A4</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DEA5A4'></div></td>
 * </tr>
 * <tr>
 * <td><code>pastel purple</code></td>
 * <td>#B39EB5</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B39EB5'></div></td>
 * </tr>
 * <tr>
 * <td><code>pastel red</code></td>
 * <td>#FF6961</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF6961'></div></td>
 * </tr>
 * <tr>
 * <td><code>pastel violet</code></td>
 * <td>#CB99C9</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CB99C9'></div></td>
 * </tr>
 * <tr>
 * <td><code>pastel yellow</code></td>
 * <td>#FDFD96</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FDFD96'></div></td>
 * </tr>
 * <tr>
 * <td><code>patriarch</code></td>
 * <td>#800080</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#800080'></div></td>
 * </tr>
 * <tr>
 * <td><code>payne's grey</code></td>
 * <td>#536878</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#536878'></div></td>
 * </tr>
 * <tr>
 * <td><code>peach</code></td>
 * <td>#FFE5B4</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFE5B4'></div></td>
 * </tr>
 * <tr>
 * <td><code>peach (crayola)</code></td>
 * <td>#FFCBA4</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFCBA4'></div></td>
 * </tr>
 * <tr>
 * <td><code>peach puff</code></td>
 * <td>#FFDAB9</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFDAB9'></div></td>
 * </tr>
 * <tr>
 * <td><code>pear</code></td>
 * <td>#D1E231</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D1E231'></div></td>
 * </tr>
 * <tr>
 * <td><code>pearl</code></td>
 * <td>#EAE0C8</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#EAE0C8'></div></td>
 * </tr>
 * <tr>
 * <td><code>pearl aqua</code></td>
 * <td>#88D8C0</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#88D8C0'></div></td>
 * </tr>
 * <tr>
 * <td><code>pearly purple</code></td>
 * <td>#B768A2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B768A2'></div></td>
 * </tr>
 * <tr>
 * <td><code>peridot</code></td>
 * <td>#E6E200</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E6E200'></div></td>
 * </tr>
 * <tr>
 * <td><code>periwinkle</code></td>
 * <td>#CCCCFF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CCCCFF'></div></td>
 * </tr>
 * <tr>
 * <td><code>periwinkle (crayola)</code></td>
 * <td>#C3CDE6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C3CDE6'></div></td>
 * </tr>
 * <tr>
 * <td><code>persian blue</code></td>
 * <td>#1C39BB</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#1C39BB'></div></td>
 * </tr>
 * <tr>
 * <td><code>persian green</code></td>
 * <td>#00A693</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00A693'></div></td>
 * </tr>
 * <tr>
 * <td><code>persian indigo</code></td>
 * <td>#32127A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#32127A'></div></td>
 * </tr>
 * <tr>
 * <td><code>persian orange</code></td>
 * <td>#D99058</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D99058'></div></td>
 * </tr>
 * <tr>
 * <td><code>persian pink</code></td>
 * <td>#F77FBE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F77FBE'></div></td>
 * </tr>
 * <tr>
 * <td><code>persian plum</code></td>
 * <td>#701C1C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#701C1C'></div></td>
 * </tr>
 * <tr>
 * <td><code>persian red</code></td>
 * <td>#CC3333</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CC3333'></div></td>
 * </tr>
 * <tr>
 * <td><code>persian rose</code></td>
 * <td>#FE28A2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FE28A2'></div></td>
 * </tr>
 * <tr>
 * <td><code>persimmon</code></td>
 * <td>#EC5800</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#EC5800'></div></td>
 * </tr>
 * <tr>
 * <td><code>peru</code></td>
 * <td>#CD853F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CD853F'></div></td>
 * </tr>
 * <tr>
 * <td><code>petal</code></td>
 * <td>#F5E2E2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F5E2E2'></div></td>
 * </tr>
 * <tr>
 * <td><code>pewter blue</code></td>
 * <td>#8BA8B7</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8BA8B7'></div></td>
 * </tr>
 * <tr>
 * <td><code>philippine blue</code></td>
 * <td>#0038A7</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#0038A7'></div></td>
 * </tr>
 * <tr>
 * <td><code>philippine bronze</code></td>
 * <td>#6E3A07</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#6E3A07'></div></td>
 * </tr>
 * <tr>
 * <td><code>philippine brown</code></td>
 * <td>#5D1916</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#5D1916'></div></td>
 * </tr>
 * <tr>
 * <td><code>philippine gold</code></td>
 * <td>#B17304</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B17304'></div></td>
 * </tr>
 * <tr>
 * <td><code>philippine golden yellow</code></td>
 * <td>#FFDF00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFDF00'></div></td>
 * </tr>
 * <tr>
 * <td><code>philippine gray</code></td>
 * <td>#8C8C8C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8C8C8C'></div></td>
 * </tr>
 * <tr>
 * <td><code>philippine green</code></td>
 * <td>#008543</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#008543'></div></td>
 * </tr>
 * <tr>
 * <td><code>philippine indigo</code></td>
 * <td>#00416A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00416A'></div></td>
 * </tr>
 * <tr>
 * <td><code>philippine orange</code></td>
 * <td>#FF7300</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF7300'></div></td>
 * </tr>
 * <tr>
 * <td><code>philippine pink</code></td>
 * <td>#FA1A8E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FA1A8E'></div></td>
 * </tr>
 * <tr>
 * <td><code>philippine red</code></td>
 * <td>#CE1127</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CE1127'></div></td>
 * </tr>
 * <tr>
 * <td><code>philippine silver</code></td>
 * <td>#B3B3B3</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B3B3B3'></div></td>
 * </tr>
 * <tr>
 * <td><code>philippine sky blue</code></td>
 * <td>#0066FF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#0066FF'></div></td>
 * </tr>
 * <tr>
 * <td><code>philippine violet</code></td>
 * <td>#81007F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#81007F'></div></td>
 * </tr>
 * <tr>
 * <td><code>philippine yellow</code></td>
 * <td>#FECB00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FECB00'></div></td>
 * </tr>
 * <tr>
 * <td><code>phlox</code></td>
 * <td>#DF00FF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DF00FF'></div></td>
 * </tr>
 * <tr>
 * <td><code>phthalo blue</code></td>
 * <td>#000F89</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#000F89'></div></td>
 * </tr>
 * <tr>
 * <td><code>phthalo green</code></td>
 * <td>#123524</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#123524'></div></td>
 * </tr>
 * <tr>
 * <td><code>picton blue</code></td>
 * <td>#45B1E8</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#45B1E8'></div></td>
 * </tr>
 * <tr>
 * <td><code>pictorial carmine</code></td>
 * <td>#C30B4E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C30B4E'></div></td>
 * </tr>
 * <tr>
 * <td><code>piggy pink</code></td>
 * <td>#FDDDE6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FDDDE6'></div></td>
 * </tr>
 * <tr>
 * <td><code>pine green</code></td>
 * <td>#01796F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#01796F'></div></td>
 * </tr>
 * <tr>
 * <td><code>pine tree</code></td>
 * <td>#2A2F23</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#2A2F23'></div></td>
 * </tr>
 * <tr>
 * <td><code>pineapple</code></td>
 * <td>#563C0D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#563C0D'></div></td>
 * </tr>
 * <tr>
 * <td><code>pink</code></td>
 * <td>#FFC0CB</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFC0CB'></div></td>
 * </tr>
 * <tr>
 * <td><code>pink (pantone)</code></td>
 * <td>#D74894</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D74894'></div></td>
 * </tr>
 * <tr>
 * <td><code>pink diamond (ace hardware color)</code></td>
 * <td>#F6D6DE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F6D6DE'></div></td>
 * </tr>
 * <tr>
 * <td><code>pink diamond (independent retailers colors)</code></td>
 * <td>#F0D3DC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F0D3DC'></div></td>
 * </tr>
 * <tr>
 * <td><code>pink flamingo</code></td>
 * <td>#FC74FD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FC74FD'></div></td>
 * </tr>
 * <tr>
 * <td><code>pink lace</code></td>
 * <td>#FFDDF4</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFDDF4'></div></td>
 * </tr>
 * <tr>
 * <td><code>pink pearl</code></td>
 * <td>#E7ACCF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E7ACCF'></div></td>
 * </tr>
 * <tr>
 * <td><code>pink sherbet</code></td>
 * <td>#F78FA7</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F78FA7'></div></td>
 * </tr>
 * <tr>
 * <td><code>pistachio</code></td>
 * <td>#93C572</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#93C572'></div></td>
 * </tr>
 * <tr>
 * <td><code>platinum</code></td>
 * <td>#E5E4E2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E5E4E2'></div></td>
 * </tr>
 * <tr>
 * <td><code>plum</code></td>
 * <td>#8E4585</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8E4585'></div></td>
 * </tr>
 * <tr>
 * <td><code>plum (web)</code></td>
 * <td>#DDA0DD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DDA0DD'></div></td>
 * </tr>
 * <tr>
 * <td><code>plump purple</code></td>
 * <td>#5946B2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#5946B2'></div></td>
 * </tr>
 * <tr>
 * <td><code>poison purple</code></td>
 * <td>#7F01FE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#7F01FE'></div></td>
 * </tr>
 * <tr>
 * <td><code>police blue</code></td>
 * <td>#374F6B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#374F6B'></div></td>
 * </tr>
 * <tr>
 * <td><code>polished pine</code></td>
 * <td>#5DA493</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#5DA493'></div></td>
 * </tr>
 * <tr>
 * <td><code>pomp and power</code></td>
 * <td>#86608E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#86608E'></div></td>
 * </tr>
 * <tr>
 * <td><code>portland orange</code></td>
 * <td>#FF5A36</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF5A36'></div></td>
 * </tr>
 * <tr>
 * <td><code>powder blue</code></td>
 * <td>#B0E0E6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B0E0E6'></div></td>
 * </tr>
 * <tr>
 * <td><code>princeton orange</code></td>
 * <td>#F58025</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F58025'></div></td>
 * </tr>
 * <tr>
 * <td><code>prune</code></td>
 * <td>#701C1C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#701C1C'></div></td>
 * </tr>
 * <tr>
 * <td><code>prussian blue</code></td>
 * <td>#003153</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#003153'></div></td>
 * </tr>
 * <tr>
 * <td><code>psychedelic purple</code></td>
 * <td>#DF00FF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DF00FF'></div></td>
 * </tr>
 * <tr>
 * <td><code>puce</code></td>
 * <td>#CC8899</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CC8899'></div></td>
 * </tr>
 * <tr>
 * <td><code>puce red</code></td>
 * <td>#722F37</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#722F37'></div></td>
 * </tr>
 * <tr>
 * <td><code>pullman brown (ups brown)</code></td>
 * <td>#644117</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#644117'></div></td>
 * </tr>
 * <tr>
 * <td><code>pumpkin</code></td>
 * <td>#FF7518</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF7518'></div></td>
 * </tr>
 * <tr>
 * <td><code>purple (html)</code></td>
 * <td>#800080</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#800080'></div></td>
 * </tr>
 * <tr>
 * <td><code>purple (munsell)</code></td>
 * <td>#9F00C5</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9F00C5'></div></td>
 * </tr>
 * <tr>
 * <td><code>purple (x11)</code></td>
 * <td>#A020F0</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A020F0'></div></td>
 * </tr>
 * <tr>
 * <td><code>purple heart</code></td>
 * <td>#69359C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#69359C'></div></td>
 * </tr>
 * <tr>
 * <td><code>purple mountain majesty</code></td>
 * <td>#9678B6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9678B6'></div></td>
 * </tr>
 * <tr>
 * <td><code>purple navy</code></td>
 * <td>#4E5180</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#4E5180'></div></td>
 * </tr>
 * <tr>
 * <td><code>purple pizzazz</code></td>
 * <td>#FE4EDA</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FE4EDA'></div></td>
 * </tr>
 * <tr>
 * <td><code>purple plum</code></td>
 * <td>#9C51B6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9C51B6'></div></td>
 * </tr>
 * <tr>
 * <td><code>purple taupe</code></td>
 * <td>#50404D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#50404D'></div></td>
 * </tr>
 * <tr>
 * <td><code>purpureus</code></td>
 * <td>#9A4EAE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9A4EAE'></div></td>
 * </tr>
 * <tr>
 * <td><code>quartz</code></td>
 * <td>#51484F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#51484F'></div></td>
 * </tr>
 * <tr>
 * <td><code>queen blue</code></td>
 * <td>#436B95</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#436B95'></div></td>
 * </tr>
 * <tr>
 * <td><code>queen pink</code></td>
 * <td>#E8CCD7</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E8CCD7'></div></td>
 * </tr>
 * <tr>
 * <td><code>quick silver</code></td>
 * <td>#A6A6A6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A6A6A6'></div></td>
 * </tr>
 * <tr>
 * <td><code>quinacridone magenta</code></td>
 * <td>#8E3A59</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8E3A59'></div></td>
 * </tr>
 * <tr>
 * <td><code>quincy</code></td>
 * <td>#6A5445</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#6A5445'></div></td>
 * </tr>
 * <tr>
 * <td><code>rackley</code></td>
 * <td>#5D8AA8</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#5D8AA8'></div></td>
 * </tr>
 * <tr>
 * <td><code>radical red</code></td>
 * <td>#FF355E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF355E'></div></td>
 * </tr>
 * <tr>
 * <td><code>raisin black</code></td>
 * <td>#242124</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#242124'></div></td>
 * </tr>
 * <tr>
 * <td><code>rajah</code></td>
 * <td>#FBAB60</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FBAB60'></div></td>
 * </tr>
 * <tr>
 * <td><code>raspberry</code></td>
 * <td>#E30B5D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E30B5D'></div></td>
 * </tr>
 * <tr>
 * <td><code>raw sienna</code></td>
 * <td>#D68A59</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D68A59'></div></td>
 * </tr>
 * <tr>
 * <td><code>raw umber</code></td>
 * <td>#826644</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#826644'></div></td>
 * </tr>
 * <tr>
 * <td><code>razzmatazz</code></td>
 * <td>#E30B5C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E30B5C'></div></td>
 * </tr>
 * <tr>
 * <td><code>razzle dazzle rose</code></td>
 * <td>#FF33CC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF33CC'></div></td>
 * </tr>
 * <tr>
 * <td><code>razzmic berry</code></td>
 * <td>#8D4E85</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8D4E85'></div></td>
 * </tr>
 * <tr>
 * <td><code>rebecca purple</code></td>
 * <td>#663399</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#663399'></div></td>
 * </tr>
 * <tr>
 * <td><code>red</code></td>
 * <td>#FF0000</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF0000'></div></td>
 * </tr>
 * <tr>
 * <td><code>red (crayola)</code></td>
 * <td>#EE204D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#EE204D'></div></td>
 * </tr>
 * <tr>
 * <td><code>red (munsell)</code></td>
 * <td>#F2003C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F2003C'></div></td>
 * </tr>
 * <tr>
 * <td><code>red (ncs)</code></td>
 * <td>#C40233</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C40233'></div></td>
 * </tr>
 * <tr>
 * <td><code>red (pantone)</code></td>
 * <td>#ED2939</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#ED2939'></div></td>
 * </tr>
 * <tr>
 * <td><code>red (pigment)</code></td>
 * <td>#ED1C24</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#ED1C24'></div></td>
 * </tr>
 * <tr>
 * <td><code>red (ryb)</code></td>
 * <td>#FE2712</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FE2712'></div></td>
 * </tr>
 * <tr>
 * <td><code>red-brown</code></td>
 * <td>#A52A2A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A52A2A'></div></td>
 * </tr>
 * <tr>
 * <td><code>red cola</code></td>
 * <td>#DF0118</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DF0118'></div></td>
 * </tr>
 * <tr>
 * <td><code>red-orange (crayola)</code></td>
 * <td>#FF681F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF681F'></div></td>
 * </tr>
 * <tr>
 * <td><code>red-orange (color wheel)</code></td>
 * <td>#FF4500</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF4500'></div></td>
 * </tr>
 * <tr>
 * <td><code>red rum</code></td>
 * <td>#973A4A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#973A4A'></div></td>
 * </tr>
 * <tr>
 * <td><code>red salsa</code></td>
 * <td>#FD3A4A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FD3A4A'></div></td>
 * </tr>
 * <tr>
 * <td><code>red strawberry</code></td>
 * <td>#EC0304</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#EC0304'></div></td>
 * </tr>
 * <tr>
 * <td><code>red-violet</code></td>
 * <td>#C71585</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C71585'></div></td>
 * </tr>
 * <tr>
 * <td><code>red-violet (crayola)</code></td>
 * <td>#C0448F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C0448F'></div></td>
 * </tr>
 * <tr>
 * <td><code>red-violet (color wheel)</code></td>
 * <td>#922B3E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#922B3E'></div></td>
 * </tr>
 * <tr>
 * <td><code>redwood</code></td>
 * <td>#A45A52</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A45A52'></div></td>
 * </tr>
 * <tr>
 * <td><code>registration black</code></td>
 * <td>#000000</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#000000'></div></td>
 * </tr>
 * <tr>
 * <td><code>resolution blue</code></td>
 * <td>#002387</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#002387'></div></td>
 * </tr>
 * <tr>
 * <td><code>rhythm</code></td>
 * <td>#777696</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#777696'></div></td>
 * </tr>
 * <tr>
 * <td><code>rich brilliant lavender</code></td>
 * <td>#F1A7FE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F1A7FE'></div></td>
 * </tr>
 * <tr>
 * <td><code>rich carmine</code></td>
 * <td>#D70040</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D70040'></div></td>
 * </tr>
 * <tr>
 * <td><code>rich electric blue</code></td>
 * <td>#0892D0</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#0892D0'></div></td>
 * </tr>
 * <tr>
 * <td><code>rich lavender</code></td>
 * <td>#A76BCF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A76BCF'></div></td>
 * </tr>
 * <tr>
 * <td><code>rich lilac</code></td>
 * <td>#B666D2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B666D2'></div></td>
 * </tr>
 * <tr>
 * <td><code>rich maroon</code></td>
 * <td>#B03060</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B03060'></div></td>
 * </tr>
 * <tr>
 * <td><code>rifle green</code></td>
 * <td>#444C38</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#444C38'></div></td>
 * </tr>
 * <tr>
 * <td><code>ripe mango</code></td>
 * <td>#FFC324</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFC324'></div></td>
 * </tr>
 * <tr>
 * <td><code>roast coffee</code></td>
 * <td>#704241</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#704241'></div></td>
 * </tr>
 * <tr>
 * <td><code>robin egg blue</code></td>
 * <td>#00CCCC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00CCCC'></div></td>
 * </tr>
 * <tr>
 * <td><code>rocket metallic</code></td>
 * <td>#8A7F80</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8A7F80'></div></td>
 * </tr>
 * <tr>
 * <td><code>roman silver</code></td>
 * <td>#838996</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#838996'></div></td>
 * </tr>
 * <tr>
 * <td><code>root beer</code></td>
 * <td>#290E05</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#290E05'></div></td>
 * </tr>
 * <tr>
 * <td><code>rose</code></td>
 * <td>#FF007F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF007F'></div></td>
 * </tr>
 * <tr>
 * <td><code>rose dust</code></td>
 * <td>#9E5E6F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9E5E6F'></div></td>
 * </tr>
 * <tr>
 * <td><code>royal azure</code></td>
 * <td>#0038A8</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#0038A8'></div></td>
 * </tr>
 * <tr>
 * <td><code>royal blue</code></td>
 * <td>#002366</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#002366'></div></td>
 * </tr>
 * <tr>
 * <td><code>royal blue</code></td>
 * <td>#4169E1</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#4169E1'></div></td>
 * </tr>
 * <tr>
 * <td><code>royal brown</code></td>
 * <td>#523B35</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#523B35'></div></td>
 * </tr>
 * <tr>
 * <td><code>royal fuchsia</code></td>
 * <td>#CA2C92</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CA2C92'></div></td>
 * </tr>
 * <tr>
 * <td><code>royal green</code></td>
 * <td>#136207</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#136207'></div></td>
 * </tr>
 * <tr>
 * <td><code>royal orange</code></td>
 * <td>#F99245</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F99245'></div></td>
 * </tr>
 * <tr>
 * <td><code>royal pink</code></td>
 * <td>#E73895</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E73895'></div></td>
 * </tr>
 * <tr>
 * <td><code>royal red</code></td>
 * <td>#9B1C31</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9B1C31'></div></td>
 * </tr>
 * <tr>
 * <td><code>royal red</code></td>
 * <td>#D00060</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D00060'></div></td>
 * </tr>
 * <tr>
 * <td><code>royal purple</code></td>
 * <td>#7851A9</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#7851A9'></div></td>
 * </tr>
 * <tr>
 * <td><code>royal yellow</code></td>
 * <td>#FADA5E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FADA5E'></div></td>
 * </tr>
 * <tr>
 * <td><code>ruby</code></td>
 * <td>#E0115F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E0115F'></div></td>
 * </tr>
 * <tr>
 * <td><code>rufous</code></td>
 * <td>#A81C07</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A81C07'></div></td>
 * </tr>
 * <tr>
 * <td><code>rum</code></td>
 * <td>#9A4E40</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9A4E40'></div></td>
 * </tr>
 * <tr>
 * <td><code>russet</code></td>
 * <td>#80461B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#80461B'></div></td>
 * </tr>
 * <tr>
 * <td><code>russian green</code></td>
 * <td>#679267</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#679267'></div></td>
 * </tr>
 * <tr>
 * <td><code>russian violet</code></td>
 * <td>#32174D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#32174D'></div></td>
 * </tr>
 * <tr>
 * <td><code>rust</code></td>
 * <td>#B7410E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B7410E'></div></td>
 * </tr>
 * <tr>
 * <td><code>rusty red</code></td>
 * <td>#DA2C43</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DA2C43'></div></td>
 * </tr>
 * <tr>
 * <td><code>sacramento state green</code></td>
 * <td>#043927</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#043927'></div></td>
 * </tr>
 * <tr>
 * <td><code>saddle brown</code></td>
 * <td>#8B4513</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8B4513'></div></td>
 * </tr>
 * <tr>
 * <td><code>safety orange</code></td>
 * <td>#FF7800</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF7800'></div></td>
 * </tr>
 * <tr>
 * <td><code>safety orange (blaze orange)</code></td>
 * <td>#FF6700</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF6700'></div></td>
 * </tr>
 * <tr>
 * <td><code>safety yellow</code></td>
 * <td>#EED202</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#EED202'></div></td>
 * </tr>
 * <tr>
 * <td><code>saffron</code></td>
 * <td>#F4C430</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F4C430'></div></td>
 * </tr>
 * <tr>
 * <td><code>sage</code></td>
 * <td>#BCB88A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#BCB88A'></div></td>
 * </tr>
 * <tr>
 * <td><code>st. patrick's blue</code></td>
 * <td>#23297A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#23297A'></div></td>
 * </tr>
 * <tr>
 * <td><code>salem</code></td>
 * <td>#177B4D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#177B4D'></div></td>
 * </tr>
 * <tr>
 * <td><code>salmon</code></td>
 * <td>#FA8072</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FA8072'></div></td>
 * </tr>
 * <tr>
 * <td><code>salmon rose</code></td>
 * <td>#E7968B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E7968B'></div></td>
 * </tr>
 * <tr>
 * <td><code>salmon pink</code></td>
 * <td>#FF91A4</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF91A4'></div></td>
 * </tr>
 * <tr>
 * <td><code>samsung blue</code></td>
 * <td>#12279E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#12279E'></div></td>
 * </tr>
 * <tr>
 * <td><code>sand</code></td>
 * <td>#C2B280</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C2B280'></div></td>
 * </tr>
 * <tr>
 * <td><code>sand dune</code></td>
 * <td>#967117</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#967117'></div></td>
 * </tr>
 * <tr>
 * <td><code>sandy brown</code></td>
 * <td>#F4A460</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F4A460'></div></td>
 * </tr>
 * <tr>
 * <td><code>sandy taupe</code></td>
 * <td>#967117</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#967117'></div></td>
 * </tr>
 * <tr>
 * <td><code>sap green</code></td>
 * <td>#507D2A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#507D2A'></div></td>
 * </tr>
 * <tr>
 * <td><code>sapphire</code></td>
 * <td>#0F52BA</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#0F52BA'></div></td>
 * </tr>
 * <tr>
 * <td><code>sapphire blue</code></td>
 * <td>#0067A5</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#0067A5'></div></td>
 * </tr>
 * <tr>
 * <td><code>satin sheen gold</code></td>
 * <td>#CBA135</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CBA135'></div></td>
 * </tr>
 * <tr>
 * <td><code>scarlet</code></td>
 * <td>#FF2400</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF2400'></div></td>
 * </tr>
 * <tr>
 * <td><code>scarlet (crayola)</code></td>
 * <td>#FD0E35</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FD0E35'></div></td>
 * </tr>
 * <tr>
 * <td><code>schauss pink</code></td>
 * <td>#FF91AF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF91AF'></div></td>
 * </tr>
 * <tr>
 * <td><code>school bus yellow</code></td>
 * <td>#FFD800</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFD800'></div></td>
 * </tr>
 * <tr>
 * <td><code>screamin' green</code></td>
 * <td>#66FF66</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#66FF66'></div></td>
 * </tr>
 * <tr>
 * <td><code>sea blue</code></td>
 * <td>#006994</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#006994'></div></td>
 * </tr>
 * <tr>
 * <td><code>sea green</code></td>
 * <td>#2E8B57</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#2E8B57'></div></td>
 * </tr>
 * <tr>
 * <td><code>sea green (crayola)</code></td>
 * <td>#00FFCD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00FFCD'></div></td>
 * </tr>
 * <tr>
 * <td><code>seal brown</code></td>
 * <td>#59260B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#59260B'></div></td>
 * </tr>
 * <tr>
 * <td><code>seashell</code></td>
 * <td>#FFF5EE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFF5EE'></div></td>
 * </tr>
 * <tr>
 * <td><code>selective yellow</code></td>
 * <td>#FFBA00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFBA00'></div></td>
 * </tr>
 * <tr>
 * <td><code>sepia</code></td>
 * <td>#704214</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#704214'></div></td>
 * </tr>
 * <tr>
 * <td><code>shadow</code></td>
 * <td>#8A795D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8A795D'></div></td>
 * </tr>
 * <tr>
 * <td><code>shadow blue</code></td>
 * <td>#778BA5</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#778BA5'></div></td>
 * </tr>
 * <tr>
 * <td><code>shampoo</code></td>
 * <td>#FFCFF1</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFCFF1'></div></td>
 * </tr>
 * <tr>
 * <td><code>shamrock green</code></td>
 * <td>#009E60</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#009E60'></div></td>
 * </tr>
 * <tr>
 * <td><code>shandy</code></td>
 * <td>#FFE670</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFE670'></div></td>
 * </tr>
 * <tr>
 * <td><code>sheen green</code></td>
 * <td>#8FD400</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8FD400'></div></td>
 * </tr>
 * <tr>
 * <td><code>shimmering blush</code></td>
 * <td>#D98695</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D98695'></div></td>
 * </tr>
 * <tr>
 * <td><code>shiny shamrock</code></td>
 * <td>#5FA778</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#5FA778'></div></td>
 * </tr>
 * <tr>
 * <td><code>shocking pink</code></td>
 * <td>#FC0FC0</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FC0FC0'></div></td>
 * </tr>
 * <tr>
 * <td><code>shocking pink (crayola)</code></td>
 * <td>#FF6FFF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF6FFF'></div></td>
 * </tr>
 * <tr>
 * <td><code>sienna</code></td>
 * <td>#882D17</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#882D17'></div></td>
 * </tr>
 * <tr>
 * <td><code>silver</code></td>
 * <td>#C0C0C0</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C0C0C0'></div></td>
 * </tr>
 * <tr>
 * <td><code>silver (crayola)</code></td>
 * <td>#C9C0BB</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C9C0BB'></div></td>
 * </tr>
 * <tr>
 * <td><code>silver (metallic)</code></td>
 * <td>#AAA9AD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#AAA9AD'></div></td>
 * </tr>
 * <tr>
 * <td><code>silver chalice</code></td>
 * <td>#ACACAC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#ACACAC'></div></td>
 * </tr>
 * <tr>
 * <td><code>silver foil</code></td>
 * <td>#AFB1AE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#AFB1AE'></div></td>
 * </tr>
 * <tr>
 * <td><code>silver lake blue</code></td>
 * <td>#5D89BA</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#5D89BA'></div></td>
 * </tr>
 * <tr>
 * <td><code>silver pink</code></td>
 * <td>#C4AEAD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C4AEAD'></div></td>
 * </tr>
 * <tr>
 * <td><code>silver sand</code></td>
 * <td>#BFC1C2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#BFC1C2'></div></td>
 * </tr>
 * <tr>
 * <td><code>sinopia</code></td>
 * <td>#CB410B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CB410B'></div></td>
 * </tr>
 * <tr>
 * <td><code>sizzling red</code></td>
 * <td>#FF3855</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF3855'></div></td>
 * </tr>
 * <tr>
 * <td><code>sizzling sunrise</code></td>
 * <td>#FFDB00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFDB00'></div></td>
 * </tr>
 * <tr>
 * <td><code>skobeloff</code></td>
 * <td>#007474</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#007474'></div></td>
 * </tr>
 * <tr>
 * <td><code>sky blue</code></td>
 * <td>#87CEEB</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#87CEEB'></div></td>
 * </tr>
 * <tr>
 * <td><code>sky blue (crayola)</code></td>
 * <td>#76D7EA</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#76D7EA'></div></td>
 * </tr>
 * <tr>
 * <td><code>sky magenta</code></td>
 * <td>#CF71AF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CF71AF'></div></td>
 * </tr>
 * <tr>
 * <td><code>slate blue</code></td>
 * <td>#6A5ACD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#6A5ACD'></div></td>
 * </tr>
 * <tr>
 * <td><code>slate gray</code></td>
 * <td>#708090</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#708090'></div></td>
 * </tr>
 * <tr>
 * <td><code>slimy green</code></td>
 * <td>#299617</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#299617'></div></td>
 * </tr>
 * <tr>
 * <td><code>smalt (dark powder blue)</code></td>
 * <td>#003399</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#003399'></div></td>
 * </tr>
 * <tr>
 * <td><code>smoke</code></td>
 * <td>#738276</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#738276'></div></td>
 * </tr>
 * <tr>
 * <td><code>smokey topaz</code></td>
 * <td>#832A0D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#832A0D'></div></td>
 * </tr>
 * <tr>
 * <td><code>smoky black</code></td>
 * <td>#100C08</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#100C08'></div></td>
 * </tr>
 * <tr>
 * <td><code>soap</code></td>
 * <td>#CEC8EF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CEC8EF'></div></td>
 * </tr>
 * <tr>
 * <td><code>solid pink</code></td>
 * <td>#893843</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#893843'></div></td>
 * </tr>
 * <tr>
 * <td><code>sonic silver</code></td>
 * <td>#757575</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#757575'></div></td>
 * </tr>
 * <tr>
 * <td><code>spartan crimson</code></td>
 * <td>#9E1316</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9E1316'></div></td>
 * </tr>
 * <tr>
 * <td><code>space cadet</code></td>
 * <td>#1D2951</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#1D2951'></div></td>
 * </tr>
 * <tr>
 * <td><code>spanish bistre</code></td>
 * <td>#807532</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#807532'></div></td>
 * </tr>
 * <tr>
 * <td><code>spanish blue</code></td>
 * <td>#0070B8</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#0070B8'></div></td>
 * </tr>
 * <tr>
 * <td><code>spanish carmine</code></td>
 * <td>#D10047</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D10047'></div></td>
 * </tr>
 * <tr>
 * <td><code>spanish crimson</code></td>
 * <td>#E51A4C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E51A4C'></div></td>
 * </tr>
 * <tr>
 * <td><code>spanish gray</code></td>
 * <td>#989898</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#989898'></div></td>
 * </tr>
 * <tr>
 * <td><code>spanish green</code></td>
 * <td>#009150</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#009150'></div></td>
 * </tr>
 * <tr>
 * <td><code>spanish orange</code></td>
 * <td>#E86100</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E86100'></div></td>
 * </tr>
 * <tr>
 * <td><code>spanish pink</code></td>
 * <td>#F7BFBE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F7BFBE'></div></td>
 * </tr>
 * <tr>
 * <td><code>spanish purple</code></td>
 * <td>#66033C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#66033C'></div></td>
 * </tr>
 * <tr>
 * <td><code>spanish red</code></td>
 * <td>#E60026</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E60026'></div></td>
 * </tr>
 * <tr>
 * <td><code>spanish sky blue</code></td>
 * <td>#00FFFF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00FFFF'></div></td>
 * </tr>
 * <tr>
 * <td><code>spanish violet</code></td>
 * <td>#4C2882</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#4C2882'></div></td>
 * </tr>
 * <tr>
 * <td><code>spanish viridian</code></td>
 * <td>#007F5C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#007F5C'></div></td>
 * </tr>
 * <tr>
 * <td><code>spanish yellow</code></td>
 * <td>#F6B511</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F6B511'></div></td>
 * </tr>
 * <tr>
 * <td><code>spicy mix</code></td>
 * <td>#8B5f4D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8B5f4D'></div></td>
 * </tr>
 * <tr>
 * <td><code>spring bud</code></td>
 * <td>#A7FC00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A7FC00'></div></td>
 * </tr>
 * <tr>
 * <td><code>spring frost</code></td>
 * <td>#87FF2A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#87FF2A'></div></td>
 * </tr>
 * <tr>
 * <td><code>spring green</code></td>
 * <td>#00FF7F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00FF7F'></div></td>
 * </tr>
 * <tr>
 * <td><code>spring green (crayola)</code></td>
 * <td>#ECEBBD</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#ECEBBD'></div></td>
 * </tr>
 * <tr>
 * <td><code>star command blue</code></td>
 * <td>#007BB8</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#007BB8'></div></td>
 * </tr>
 * <tr>
 * <td><code>steel blue</code></td>
 * <td>#4682B4</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#4682B4'></div></td>
 * </tr>
 * <tr>
 * <td><code>steel pink</code></td>
 * <td>#CC33CC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CC33CC'></div></td>
 * </tr>
 * <tr>
 * <td><code>steel teal</code></td>
 * <td>#5F8A8F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#5F8A8F'></div></td>
 * </tr>
 * <tr>
 * <td><code>stil de grain yellow</code></td>
 * <td>#FADA5E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FADA5E'></div></td>
 * </tr>
 * <tr>
 * <td><code>straw</code></td>
 * <td>#E4D96F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E4D96F'></div></td>
 * </tr>
 * <tr>
 * <td><code>strawberry</code></td>
 * <td>#FC5A8D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FC5A8D'></div></td>
 * </tr>
 * <tr>
 * <td><code>stop red</code></td>
 * <td>#CF142B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CF142B'></div></td>
 * </tr>
 * <tr>
 * <td><code>strawberry iced tea</code></td>
 * <td>#FC5A8D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FC5A8D'></div></td>
 * </tr>
 * <tr>
 * <td><code>strawberry red</code></td>
 * <td>#C83F49</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C83F49'></div></td>
 * </tr>
 * <tr>
 * <td><code>sugar plum</code></td>
 * <td>#914E75</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#914E75'></div></td>
 * </tr>
 * <tr>
 * <td><code>sunglow</code></td>
 * <td>#FFCC33</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFCC33'></div></td>
 * </tr>
 * <tr>
 * <td><code>sunray</code></td>
 * <td>#E3AB57</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E3AB57'></div></td>
 * </tr>
 * <tr>
 * <td><code>sunset</code></td>
 * <td>#FAD6A5</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FAD6A5'></div></td>
 * </tr>
 * <tr>
 * <td><code>sunset orange</code></td>
 * <td>#FD5E53</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FD5E53'></div></td>
 * </tr>
 * <tr>
 * <td><code>super pink</code></td>
 * <td>#CF6BA9</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CF6BA9'></div></td>
 * </tr>
 * <tr>
 * <td><code>sweet brown</code></td>
 * <td>#A83731</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A83731'></div></td>
 * </tr>
 * <tr>
 * <td><code>tan</code></td>
 * <td>#D2B48C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D2B48C'></div></td>
 * </tr>
 * <tr>
 * <td><code>tan (crayola)</code></td>
 * <td>#D99A6C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D99A6C'></div></td>
 * </tr>
 * <tr>
 * <td><code>tangelo</code></td>
 * <td>#F94D00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F94D00'></div></td>
 * </tr>
 * <tr>
 * <td><code>tangerine</code></td>
 * <td>#F28500</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F28500'></div></td>
 * </tr>
 * <tr>
 * <td><code>tangerine yellow</code></td>
 * <td>#FFCC00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFCC00'></div></td>
 * </tr>
 * <tr>
 * <td><code>tango pink</code></td>
 * <td>#E4717A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E4717A'></div></td>
 * </tr>
 * <tr>
 * <td><code>tart orange</code></td>
 * <td>#FB4D46</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FB4D46'></div></td>
 * </tr>
 * <tr>
 * <td><code>taupe</code></td>
 * <td>#483C32</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#483C32'></div></td>
 * </tr>
 * <tr>
 * <td><code>taupe gray</code></td>
 * <td>#8B8589</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8B8589'></div></td>
 * </tr>
 * <tr>
 * <td><code>tea green</code></td>
 * <td>#D0F0C0</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D0F0C0'></div></td>
 * </tr>
 * <tr>
 * <td><code>tea rose</code></td>
 * <td>#F88379</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F88379'></div></td>
 * </tr>
 * <tr>
 * <td><code>tea rose</code></td>
 * <td>#F4C2C2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F4C2C2'></div></td>
 * </tr>
 * <tr>
 * <td><code>teal</code></td>
 * <td>#008080</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#008080'></div></td>
 * </tr>
 * <tr>
 * <td><code>teal blue</code></td>
 * <td>#367588</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#367588'></div></td>
 * </tr>
 * <tr>
 * <td><code>teal deer</code></td>
 * <td>#99E6B3</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#99E6B3'></div></td>
 * </tr>
 * <tr>
 * <td><code>teal green</code></td>
 * <td>#00827F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00827F'></div></td>
 * </tr>
 * <tr>
 * <td><code>telemagenta</code></td>
 * <td>#CF3476</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CF3476'></div></td>
 * </tr>
 * <tr>
 * <td><code>temptress</code></td>
 * <td>#3C2126</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#3C2126'></div></td>
 * </tr>
 * <tr>
 * <td><code>tenn� (tawny)</code></td>
 * <td>#CD5700</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CD5700'></div></td>
 * </tr>
 * <tr>
 * <td><code>terra cotta</code></td>
 * <td>#E2725B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E2725B'></div></td>
 * </tr>
 * <tr>
 * <td><code>thistle</code></td>
 * <td>#D8BFD8</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D8BFD8'></div></td>
 * </tr>
 * <tr>
 * <td><code>thistle (crayola)</code></td>
 * <td>#EBB0D7</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#EBB0D7'></div></td>
 * </tr>
 * <tr>
 * <td><code>thulian pink</code></td>
 * <td>#DE6FA1</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DE6FA1'></div></td>
 * </tr>
 * <tr>
 * <td><code>tickle me pink</code></td>
 * <td>#FC89AC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FC89AC'></div></td>
 * </tr>
 * <tr>
 * <td><code>tiffany blue</code></td>
 * <td>#81D8D0</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#81D8D0'></div></td>
 * </tr>
 * <tr>
 * <td><code>tiger's eye</code></td>
 * <td>#E08D3C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E08D3C'></div></td>
 * </tr>
 * <tr>
 * <td><code>timberwolf</code></td>
 * <td>#DBD7D2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DBD7D2'></div></td>
 * </tr>
 * <tr>
 * <td><code>titanium</code></td>
 * <td>#878681</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#878681'></div></td>
 * </tr>
 * <tr>
 * <td><code>titanium yellow</code></td>
 * <td>#EEE600</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#EEE600'></div></td>
 * </tr>
 * <tr>
 * <td><code>tomato</code></td>
 * <td>#FF6347</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF6347'></div></td>
 * </tr>
 * <tr>
 * <td><code>tomato sauce</code></td>
 * <td>#B21807</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B21807'></div></td>
 * </tr>
 * <tr>
 * <td><code>tooth</code></td>
 * <td>#FFFAFA</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFFAFA'></div></td>
 * </tr>
 * <tr>
 * <td><code>topaz</code></td>
 * <td>#FFC87C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFC87C'></div></td>
 * </tr>
 * <tr>
 * <td><code>tractor red</code></td>
 * <td>#FD0E35</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FD0E35'></div></td>
 * </tr>
 * <tr>
 * <td><code>trolley grey</code></td>
 * <td>#808080</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#808080'></div></td>
 * </tr>
 * <tr>
 * <td><code>tropical rain forest</code></td>
 * <td>#00755E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00755E'></div></td>
 * </tr>
 * <tr>
 * <td><code>true blue</code></td>
 * <td>#0073CF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#0073CF'></div></td>
 * </tr>
 * <tr>
 * <td><code>tufts blue</code></td>
 * <td>#3E8EDE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#3E8EDE'></div></td>
 * </tr>
 * <tr>
 * <td><code>tulip</code></td>
 * <td>#FF878D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF878D'></div></td>
 * </tr>
 * <tr>
 * <td><code>tumbleweed</code></td>
 * <td>#DEAA88</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DEAA88'></div></td>
 * </tr>
 * <tr>
 * <td><code>turkish rose</code></td>
 * <td>#B57281</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B57281'></div></td>
 * </tr>
 * <tr>
 * <td><code>turquoise</code></td>
 * <td>#40E0D0</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#40E0D0'></div></td>
 * </tr>
 * <tr>
 * <td><code>turquoise green</code></td>
 * <td>#A0D6B4</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A0D6B4'></div></td>
 * </tr>
 * <tr>
 * <td><code>tuscan brown</code></td>
 * <td>#6F4E37</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#6F4E37'></div></td>
 * </tr>
 * <tr>
 * <td><code>tuscan red</code></td>
 * <td>#7C4848</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#7C4848'></div></td>
 * </tr>
 * <tr>
 * <td><code>tuscan tan</code></td>
 * <td>#A67B5B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A67B5B'></div></td>
 * </tr>
 * <tr>
 * <td><code>tuscany</code></td>
 * <td>#C09999</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C09999'></div></td>
 * </tr>
 * <tr>
 * <td><code>twitter blue</code></td>
 * <td>#26A7DE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#26A7DE'></div></td>
 * </tr>
 * <tr>
 * <td><code>twilight lavender</code></td>
 * <td>#8A496B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8A496B'></div></td>
 * </tr>
 * <tr>
 * <td><code>tyrian purple</code></td>
 * <td>#66023C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#66023C'></div></td>
 * </tr>
 * <tr>
 * <td><code>ultramarine</code></td>
 * <td>#3F00FF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#3F00FF'></div></td>
 * </tr>
 * <tr>
 * <td><code>ultramarine blue</code></td>
 * <td>#4166F5</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#4166F5'></div></td>
 * </tr>
 * <tr>
 * <td><code>ultramarine blue (caran d'ache)</code></td>
 * <td>#2111EF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#2111EF'></div></td>
 * </tr>
 * <tr>
 * <td><code>ultra pink</code></td>
 * <td>#FF6FFF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF6FFF'></div></td>
 * </tr>
 * <tr>
 * <td><code>ultra red</code></td>
 * <td>#FC6C85</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FC6C85'></div></td>
 * </tr>
 * <tr>
 * <td><code>umber</code></td>
 * <td>#635147</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#635147'></div></td>
 * </tr>
 * <tr>
 * <td><code>unbleached silk</code></td>
 * <td>#FFDDCA</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFDDCA'></div></td>
 * </tr>
 * <tr>
 * <td><code>united nations blue</code></td>
 * <td>#5B92E5</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#5B92E5'></div></td>
 * </tr>
 * <tr>
 * <td><code>unmellow yellow</code></td>
 * <td>#FFFF66</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFFF66'></div></td>
 * </tr>
 * <tr>
 * <td><code>up maroon</code></td>
 * <td>#7B1113</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#7B1113'></div></td>
 * </tr>
 * <tr>
 * <td><code>upsdell red</code></td>
 * <td>#AE2029</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#AE2029'></div></td>
 * </tr>
 * <tr>
 * <td><code>urobilin</code></td>
 * <td>#E1AD21</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E1AD21'></div></td>
 * </tr>
 * <tr>
 * <td><code>vampire black</code></td>
 * <td>#080808</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#080808'></div></td>
 * </tr>
 * <tr>
 * <td><code>van dyke brown</code></td>
 * <td>#664228</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#664228'></div></td>
 * </tr>
 * <tr>
 * <td><code>vanilla</code></td>
 * <td>#F3E5AB</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F3E5AB'></div></td>
 * </tr>
 * <tr>
 * <td><code>vanilla ice</code></td>
 * <td>#F38FA9</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F38FA9'></div></td>
 * </tr>
 * <tr>
 * <td><code>vegas gold</code></td>
 * <td>#C5B358</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C5B358'></div></td>
 * </tr>
 * <tr>
 * <td><code>venetian red</code></td>
 * <td>#C80815</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C80815'></div></td>
 * </tr>
 * <tr>
 * <td><code>verdigris</code></td>
 * <td>#43B3AE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#43B3AE'></div></td>
 * </tr>
 * <tr>
 * <td><code>vermilion</code></td>
 * <td>#E34234</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E34234'></div></td>
 * </tr>
 * <tr>
 * <td><code>vermilion</code></td>
 * <td>#D9381E</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D9381E'></div></td>
 * </tr>
 * <tr>
 * <td><code>veronica</code></td>
 * <td>#A020F0</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A020F0'></div></td>
 * </tr>
 * <tr>
 * <td><code>very light azure</code></td>
 * <td>#74BBFB</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#74BBFB'></div></td>
 * </tr>
 * <tr>
 * <td><code>very light blue</code></td>
 * <td>#6666FF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#6666FF'></div></td>
 * </tr>
 * <tr>
 * <td><code>very light malachite green</code></td>
 * <td>#64E986</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#64E986'></div></td>
 * </tr>
 * <tr>
 * <td><code>very light tangelo</code></td>
 * <td>#FFB077</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFB077'></div></td>
 * </tr>
 * <tr>
 * <td><code>very pale orange</code></td>
 * <td>#FFDFBF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFDFBF'></div></td>
 * </tr>
 * <tr>
 * <td><code>very pale yellow</code></td>
 * <td>#FFFFBF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFFFBF'></div></td>
 * </tr>
 * <tr>
 * <td><code>vine green</code></td>
 * <td>#164010</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#164010'></div></td>
 * </tr>
 * <tr>
 * <td><code>violet</code></td>
 * <td>#8F00FF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8F00FF'></div></td>
 * </tr>
 * <tr>
 * <td><code>violet (caran d'ache)</code></td>
 * <td>#6E00C0</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#6E00C0'></div></td>
 * </tr>
 * <tr>
 * <td><code>violet (color wheel)</code></td>
 * <td>#7F00FF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#7F00FF'></div></td>
 * </tr>
 * <tr>
 * <td><code>violet (crayola)</code></td>
 * <td>#963D7F</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#963D7F'></div></td>
 * </tr>
 * <tr>
 * <td><code>violet (ryb)</code></td>
 * <td>#8601AF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#8601AF'></div></td>
 * </tr>
 * <tr>
 * <td><code>violet (web)</code></td>
 * <td>#EE82EE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#EE82EE'></div></td>
 * </tr>
 * <tr>
 * <td><code>violet-blue</code></td>
 * <td>#324AB2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#324AB2'></div></td>
 * </tr>
 * <tr>
 * <td><code>violet-blue (crayola)</code></td>
 * <td>#766EC8</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#766EC8'></div></td>
 * </tr>
 * <tr>
 * <td><code>violin brown</code></td>
 * <td>#674403</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#674403'></div></td>
 * </tr>
 * <tr>
 * <td><code>viridian</code></td>
 * <td>#40826D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#40826D'></div></td>
 * </tr>
 * <tr>
 * <td><code>viridian green</code></td>
 * <td>#009698</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#009698'></div></td>
 * </tr>
 * <tr>
 * <td><code>vista blue</code></td>
 * <td>#7C9ED9</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#7C9ED9'></div></td>
 * </tr>
 * <tr>
 * <td><code>vivid amber</code></td>
 * <td>#cc9900</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#cc9900'></div></td>
 * </tr>
 * <tr>
 * <td><code>vivid auburn</code></td>
 * <td>#922724</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#922724'></div></td>
 * </tr>
 * <tr>
 * <td><code>vivid burgundy</code></td>
 * <td>#9F1D35</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9F1D35'></div></td>
 * </tr>
 * <tr>
 * <td><code>vivid cerise</code></td>
 * <td>#DA1D81</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DA1D81'></div></td>
 * </tr>
 * <tr>
 * <td><code>vivid cerulean</code></td>
 * <td>#00AAEE</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#00AAEE'></div></td>
 * </tr>
 * <tr>
 * <td><code>vivid crimson</code></td>
 * <td>#CC0033</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CC0033'></div></td>
 * </tr>
 * <tr>
 * <td><code>vivid gamboge</code></td>
 * <td>#FF9900</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF9900'></div></td>
 * </tr>
 * <tr>
 * <td><code>vivid lime green</code></td>
 * <td>#a6d608</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#a6d608'></div></td>
 * </tr>
 * <tr>
 * <td><code>vivid mulberry</code></td>
 * <td>#B80CE3</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B80CE3'></div></td>
 * </tr>
 * <tr>
 * <td><code>vivid orange</code></td>
 * <td>#FF5F00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF5F00'></div></td>
 * </tr>
 * <tr>
 * <td><code>vivid orange peel</code></td>
 * <td>#FFA000</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFA000'></div></td>
 * </tr>
 * <tr>
 * <td><code>vivid orchid</code></td>
 * <td>#CC00FF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#CC00FF'></div></td>
 * </tr>
 * <tr>
 * <td><code>vivid raspberry</code></td>
 * <td>#FF006C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF006C'></div></td>
 * </tr>
 * <tr>
 * <td><code>vivid red</code></td>
 * <td>#F70D1A</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F70D1A'></div></td>
 * </tr>
 * <tr>
 * <td><code>vivid red-tangelo</code></td>
 * <td>#DF6124</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#DF6124'></div></td>
 * </tr>
 * <tr>
 * <td><code>vivid tangelo</code></td>
 * <td>#F07427</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F07427'></div></td>
 * </tr>
 * <tr>
 * <td><code>vivid vermilion</code></td>
 * <td>#e56024</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#e56024'></div></td>
 * </tr>
 * <tr>
 * <td><code>vivid violet</code></td>
 * <td>#9F00FF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9F00FF'></div></td>
 * </tr>
 * <tr>
 * <td><code>vivid yellow</code></td>
 * <td>#FFE302</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFE302'></div></td>
 * </tr>
 * <tr>
 * <td><code>water</code></td>
 * <td>#D4F1F9</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D4F1F9'></div></td>
 * </tr>
 * <tr>
 * <td><code>watermelon</code></td>
 * <td>#F05C85</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F05C85'></div></td>
 * </tr>
 * <tr>
 * <td><code>watermelon red</code></td>
 * <td>#BF4147</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#BF4147'></div></td>
 * </tr>
 * <tr>
 * <td><code>watermelon yellow</code></td>
 * <td>#EEFF1B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#EEFF1B'></div></td>
 * </tr>
 * <tr>
 * <td><code>waterspout</code></td>
 * <td>#A4F4F9</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A4F4F9'></div></td>
 * </tr>
 * <tr>
 * <td><code>wenge</code></td>
 * <td>#645452</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#645452'></div></td>
 * </tr>
 * <tr>
 * <td><code>wheat</code></td>
 * <td>#F5DEB3</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F5DEB3'></div></td>
 * </tr>
 * <tr>
 * <td><code>white</code></td>
 * <td>#FFFFFF</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFFFFF'></div></td>
 * </tr>
 * <tr>
 * <td><code>white chocolate</code></td>
 * <td>#EDE6D6</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#EDE6D6'></div></td>
 * </tr>
 * <tr>
 * <td><code>white coffee</code></td>
 * <td>#E6E0D4</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#E6E0D4'></div></td>
 * </tr>
 * <tr>
 * <td><code>white smoke</code></td>
 * <td>#F5F5F5</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F5F5F5'></div></td>
 * </tr>
 * <tr>
 * <td><code>wild orchid</code></td>
 * <td>#D470A2</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#D470A2'></div></td>
 * </tr>
 * <tr>
 * <td><code>wild strawberry</code></td>
 * <td>#FF43A4</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF43A4'></div></td>
 * </tr>
 * <tr>
 * <td><code>wild watermelon</code></td>
 * <td>#FC6C85</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FC6C85'></div></td>
 * </tr>
 * <tr>
 * <td><code>willpower orange</code></td>
 * <td>#FD5800</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FD5800'></div></td>
 * </tr>
 * <tr>
 * <td><code>windsor tan</code></td>
 * <td>#A75502</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#A75502'></div></td>
 * </tr>
 * <tr>
 * <td><code>wine</code></td>
 * <td>#722F37</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#722F37'></div></td>
 * </tr>
 * <tr>
 * <td><code>wine red</code></td>
 * <td>#B11226</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#B11226'></div></td>
 * </tr>
 * <tr>
 * <td><code>winter sky</code></td>
 * <td>#FF007C</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FF007C'></div></td>
 * </tr>
 * <tr>
 * <td><code>wintergreen dream</code></td>
 * <td>#56887D</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#56887D'></div></td>
 * </tr>
 * <tr>
 * <td><code>wisteria</code></td>
 * <td>#C9A0DC</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C9A0DC'></div></td>
 * </tr>
 * <tr>
 * <td><code>wood brown</code></td>
 * <td>#C19A6B</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C19A6B'></div></td>
 * </tr>
 * <tr>
 * <td><code>xanadu</code></td>
 * <td>#738678</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#738678'></div></td>
 * </tr>
 * <tr>
 * <td><code>yellow</code></td>
 * <td>#FFFF00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFFF00'></div></td>
 * </tr>
 * <tr>
 * <td><code>yellow (crayola)</code></td>
 * <td>#FCE883</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FCE883'></div></td>
 * </tr>
 * <tr>
 * <td><code>yellow (munsell)</code></td>
 * <td>#EFCC00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#EFCC00'></div></td>
 * </tr>
 * <tr>
 * <td><code>yellow (ncs)</code></td>
 * <td>#FFD300</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFD300'></div></td>
 * </tr>
 * <tr>
 * <td><code>yellow (pantone)</code></td>
 * <td>#FEDF00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FEDF00'></div></td>
 * </tr>
 * <tr>
 * <td><code>yellow (process)</code></td>
 * <td>#FFEF00</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFEF00'></div></td>
 * </tr>
 * <tr>
 * <td><code>yellow (ryb)</code></td>
 * <td>#FEFE33</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FEFE33'></div></td>
 * </tr>
 * <tr>
 * <td><code>yellow-green</code></td>
 * <td>#9ACD32</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#9ACD32'></div></td>
 * </tr>
 * <tr>
 * <td><code>yellow-green (crayola)</code></td>
 * <td>#C5E384</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#C5E384'></div></td>
 * </tr>
 * <tr>
 * <td><code>yellow orange</code></td>
 * <td>#FFAE42</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFAE42'></div></td>
 * </tr>
 * <tr>
 * <td><code>yellow rose</code></td>
 * <td>#FFF000</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFF000'></div></td>
 * </tr>
 * <tr>
 * <td><code>yellow sunshine</code></td>
 * <td>#FFF700</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#FFF700'></div></td>
 * </tr>
 * <tr>
 * <td><code>yinmn blue</code></td>
 * <td>#2E5090</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#2E5090'></div></td>
 * </tr>
 * <tr>
 * <td><code>zaffre</code></td>
 * <td>#0014A8</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#0014A8'></div></td>
 * </tr>
 * <tr>
 * <td><code>zebra white</code></td>
 * <td>#F5F5F5</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#F5F5F5'></div></td>
 * </tr>
 * <tr>
 * <td><code>zinnwaldite</code></td>
 * <td>#2C1608</td>
 * <td><div style='width:40px;height:20px;border:.5px solid
 * black;background-color:#2C1608'></div></td>
 * </tr>
 * </tbody>
 * </table>
 * <p>
 * All method that accept a colour name or that return one always do so with
 * case-insensitivity.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public final class NamedColour extends IntegerColour {
	
	/**
	 * Mappings for common colours found on the world wide web.
	 * <p>
	 * Please see the article <a href=
	 * "https://en.wikipedia.org/wiki/List_of_colors_(alphabetical)">https://en.wikipedia.org/wiki/List_of_colors_(alphabetical)</a>
	 * for details.
	 */
	private static Map<String, String> named = new HashMap<>();
	/**
	 * Stores the last searched name to prevent traversing the {@link #named}
	 * multiple times, as the parent requires an integer, which when satisfied, this
	 * value becomes assigned.
	 */
	private static String $n = null;

	static {
		named.put("absolute zero", "0048BA");
		named.put("acid green", "B0BF1A");
		named.put("aero", "00B9E8");
		named.put("african violet", "B284BE");
		named.put("air superiority blue", "72A0C1");
		named.put("alabaster", "F2F0E6");
		named.put("alice blue", "F0F8FF");
		named.put("alizarin", "DB2D43");
		named.put("alloy orange", "C46210");
		named.put("almond", "EFDECD");
		named.put("amaranth", "E52B50");
		named.put("amaranth deep purple", "9F2B68");
		named.put("amaranth pink", "F19CBB");
		named.put("amaranth purple", "AB274F");
		named.put("amazon", "3B7A57");
		named.put("amber", "FFBF00");
		named.put("amber (sae/ece)", "FF7E00");
		named.put("amethyst", "9966CC");
		named.put("amethyst (crayola)", "64609A");
		named.put("android green", "3DDC84");
		named.put("antique brass", "CD9575");
		named.put("antique bronze", "665D1E");
		named.put("antique fuchsia", "915C83");
		named.put("antique ruby", "841B2D");
		named.put("antique white", "FAEBD7");
		named.put("apricot", "FBCEB1");
		named.put("aqua", "00FFFF");
		named.put("aquamarine", "7FFFD4");
		named.put("aquamarine (crayola)", "95E0E8");
		named.put("arctic lime", "D0FF14");
		named.put("artichoke green", "4B6F44");
		named.put("arylide yellow", "E9D66B");
		named.put("ash gray", "B2BEB5");
		named.put("asparagus", "7BA05B");
		named.put("atomic tangerine", "FF9966");
		named.put("aureolin", "FDEE00");
		named.put("aztec gold", "C39953");
		named.put("azure", "007FFF");
		named.put("azure (x11/web color)", "F0FFFF");
		named.put("baby blue", "89CFF0");
		named.put("baby blue eyes", "A1CAF1");
		named.put("baby pink", "F4C2C2");
		named.put("baby powder", "FEFEFA");
		named.put("baker-miller pink", "FF91AF");
		named.put("banana mania", "FAE7B5");
		named.put("barbie pink", "E0218A");
		named.put("barn red", "7C0A02");
		named.put("battleship grey", "848482");
		named.put("beau blue", "BCD4E6");
		named.put("beaver", "9F8170");
		named.put("beige", "F5F5DC");
		named.put("berry parfait", "A43482");
		named.put("b'dazzled blue", "2E5894");
		named.put("big dip o�ruby", "9C2542");
		named.put("big foot feet", "E88E5A");
		named.put("bisque", "FFE4C4");
		named.put("bistre", "3D2B1F");
		named.put("bistre brown", "967117");
		named.put("bitter lemon", "CAE00D");
		named.put("bittersweet", "FE6F5E");
		named.put("bittersweet shimmer", "BF4F51");
		named.put("black", "000000");
		named.put("black bean", "3D0C02");
		named.put("black coral", "54626F");
		named.put("black olive", "3B3C36");
		named.put("black shadows", "BFAFB2");
		named.put("blanched almond", "FFEBCD");
		named.put("blast-off bronze", "A57164");
		named.put("bleu de france", "318CE7");
		named.put("blizzard blue", "50BFE6");
		named.put("blood red", "660000");
		named.put("blue", "0000FF");
		named.put("blue (crayola)", "1F75FE");
		named.put("blue (munsell)", "0093AF");
		named.put("blue (ncs)", "0087BD");
		named.put("blue (pantone)", "0018A8");
		named.put("blue (pigment)", "333399");
		named.put("blue bell", "A2A2D0");
		named.put("blue-gray", "6699CC");
		named.put("blue-gray (crayola)", "C8C8CD");
		named.put("blue-green", "0D98BA");
		named.put("blue jeans", "5DADEC");
		named.put("blue ribbon", "0B10A2");
		named.put("blue sapphire", "126180");
		named.put("blue-violet", "8A2BE2");
		named.put("blue yonder", "5072A7");
		named.put("blueberry", "4F86F7");
		named.put("bluetiful", "3C69E7");
		named.put("blush", "DE5D83");
		named.put("bole", "79443B");
		named.put("bone", "E3DAC9");
		named.put("booger buster", "DDE26A");
		named.put("brick red", "CB4154");
		named.put("bright green", "66FF00");
		named.put("bright lilac", "D891EF");
		named.put("bright maroon", "C32148");
		named.put("bright navy blue", "1974D2");
		named.put("bright pink", "FF007F");
		named.put("bright turquoise", "08E8DE");
		named.put("bright yellow (crayola)", "FFAA1D");
		named.put("brilliant rose", "E667CE");
		named.put("brink pink", "FB607F");
		named.put("british racing green", "004225");
		named.put("bronze", "CD7F32");
		named.put("brown", "964B00");
		named.put("brown (crayola)", "AF593E");
		named.put("brown (web)", "A52A2A");
		named.put("brown sugar", "AF6E4D");
		named.put("bud green", "7BB661");
		named.put("buff", "F0DC82");
		named.put("burgundy", "800020");
		named.put("burlywood", "DEB887");
		named.put("burnished brown", "A17A74");
		named.put("burnt orange", "CC5500");
		named.put("burnt sienna", "E97451");
		named.put("burnt umber", "8A3324");
		named.put("byzantine", "BD33A4");
		named.put("byzantium", "702963");
		named.put("cadet", "536872");
		named.put("cadet blue", "5F9EA0");
		named.put("cadet grey", "91A3B0");
		named.put("cadmium green", "006B3C");
		named.put("cadmium orange", "ED872D");
		named.put("cadmium red", "E30022");
		named.put("cadmium yellow", "FFF600");
		named.put("caf� au lait", "A67B5B");
		named.put("caf� noir", "4B3621");
		named.put("cambridge blue", "A3C1AD");
		named.put("camel", "C19A6B");
		named.put("cameo pink", "EFBBCC");
		named.put("canary", "FFFF99");
		named.put("canary yellow", "FFEF00");
		named.put("candy apple red", "FF0800");
		named.put("candy pink", "E4717A");
		named.put("cardinal", "C41E3A");
		named.put("caribbean green", "00CC99");
		named.put("carmine", "960018");
		named.put("carnation pink", "FFA6C9");
		named.put("carolina blue", "56A0D3");
		named.put("carrot orange", "ED9121");
		named.put("catawba", "703642");
		named.put("cedar chest", "CA3435");
		named.put("celadon", "ACE1AF");
		named.put("celeste", "B2FFFF");
		named.put("cerise", "DE3163");
		named.put("cerulean", "007BA7");
		named.put("cerulean blue", "2A52BE");
		named.put("cerulean frost", "6D9BC3");
		named.put("cerulean (crayola)", "1DACD6");
		named.put("champagne", "F7E7CE");
		named.put("champagne pink", "F1DDCF");
		named.put("charcoal", "36454F");
		named.put("charleston green", "232B2B");
		named.put("charm pink", "E68FAC");
		named.put("chartreuse", "7FFF00");
		named.put("cherry blossom pink", "FFB7C5");
		named.put("chestnut", "954535");
		named.put("china pink", "DE6FA1");
		named.put("chinese red", "AA381E");
		named.put("chinese violet", "856088");
		named.put("chocolate (traditional)", "7B3F00");
		named.put("chocolate (web)", "D2691E");
		named.put("cinereous", "98817B");
		named.put("cinnamon satin", "CD607E");
		named.put("citrine", "E4D00A");
		named.put("citron", "9FA91F");
		named.put("claret", "7F1734");
		named.put("cobalt blue", "0047AB");
		named.put("cocoa brown", "D2691E");
		named.put("coconut", "965A3E");
		named.put("coffee", "6F4E37");
		named.put("columbia blue", "C4D8E2");
		named.put("congo pink", "F88379");
		named.put("cool grey", "8C92AC");
		named.put("copper", "B87333");
		named.put("copper penny", "AD6F69");
		named.put("copper red", "CB6D51");
		named.put("copper rose", "996666");
		named.put("coquelicot", "FF3800");
		named.put("coral", "FF7F50");
		named.put("coral pink", "F88379");
		named.put("cordovan", "893F45");
		named.put("cornflower blue (web)", "6495ED");
		named.put("cornflower blue (crayola)", "93CCEA");
		named.put("cornsilk", "FFF8DC");
		named.put("cosmic cobalt", "2E2D88");
		named.put("cosmic latte", "FFF8E7");
		named.put("coyote brown", "81613C");
		named.put("cotton candy", "FFBCD9");
		named.put("cream", "FFFDD0");
		named.put("crimson", "DC143C");
		named.put("crimson (ua)", "AF002A");
		named.put("cultured", "F5F5F5");
		named.put("cyan", "00FFFF");
		named.put("cyber grape", "58427C");
		named.put("cyber yellow", "FFD300");
		named.put("cyclamen", "F56FA1");
		named.put("dandelion", "DDF719");
		named.put("dark blue", "00008B");
		named.put("dark blue-gray", "666699");
		named.put("dark brown", "654321");
		named.put("dark byzantium", "5D3954");
		named.put("dark cyan", "008B8B");
		named.put("dark electric blue", "536878");
		named.put("dark fuchsia", "A00955");
		named.put("dark goldenrod", "B8860B");
		named.put("dark gray (x11)", "A9A9A9");
		named.put("dark green", "013220");
		named.put("dark green (x11)", "006400");
		named.put("dark jungle green", "1A2421");
		named.put("dark khaki", "BDB76B");
		named.put("dark lava", "483C32");
		named.put("dark liver", "534B4F");
		named.put("dark magenta", "8B008B");
		named.put("dark midnight blue", "003366");
		named.put("dark moss green", "4A5D23");
		named.put("dark olive green", "556B2F");
		named.put("dark orange", "FF8C00");
		named.put("dark orchid", "9932CC");
		named.put("dark pastel green", "03C03C");
		named.put("dark purple", "301934");
		named.put("dark raspberry", "872657");
		named.put("dark red", "8B0000");
		named.put("dark salmon", "E9967A");
		named.put("dark sea green", "8FBC8F");
		named.put("dark sienna", "3C1414");
		named.put("dark sky blue", "8CBED6");
		named.put("dark slate blue", "483D8B");
		named.put("dark slate gray", "2F4F4F");
		named.put("dark spring green", "177245");
		named.put("dark tan", "918151");
		named.put("dark turquoise", "00CED1");
		named.put("dark vanilla", "D1BEA8");
		named.put("dark violet", "9400D3");
		named.put("dartmouth green", "00703C");
		named.put("davy's grey", "555555");
		named.put("deep cerise", "DA3287");
		named.put("deep champagne", "FAD6A5");
		named.put("deep chestnut", "B94E48");
		named.put("deep fuchsia", "C154C1");
		named.put("deep jungle green", "004B49");
		named.put("deep lemon", "F5C71A");
		named.put("deep mauve", "D473D4");
		named.put("deep pink", "FF1493");
		named.put("deep sky blue", "00BFFF");
		named.put("deep space sparkle", "4A646C");
		named.put("deep taupe", "7E5E60");
		named.put("denim", "1560BD");
		named.put("denim blue", "2243B6");
		named.put("desert", "C19A6B");
		named.put("desert sand", "EDC9AF");
		named.put("dim gray", "696969");
		named.put("dingy dungeon", "C53151");
		named.put("dirt", "9B7653");
		named.put("dodger blue", "1E90FF");
		named.put("dogwood rose", "D71868");
		named.put("duke blue", "00009C");
		named.put("dutch white", "EFDFBB");
		named.put("earth yellow", "E1A95F");
		named.put("ebony", "555D50");
		named.put("ecru", "C2B280");
		named.put("eerie black", "1B1B1B");
		named.put("eggplant", "614051");
		named.put("eggshell", "F0EAD6");
		named.put("egyptian blue", "1034A6");
		named.put("electric blue", "7DF9FF");
		named.put("electric indigo", "6F00FF");
		named.put("electric lime", "CCFF00");
		named.put("electric purple", "BF00FF");
		named.put("electric violet", "8F00FF");
		named.put("emerald", "50C878");
		named.put("eminence", "6C3082");
		named.put("english lavender", "B48395");
		named.put("english red", "AB4B52");
		named.put("english vermillion", "CC474B");
		named.put("english violet", "563C5C");
		named.put("eton blue", "96C8A2");
		named.put("eucalyptus", "44D7A8");
		named.put("fallow", "C19A6B");
		named.put("falu red", "801818");
		named.put("fandango", "B53389");
		named.put("fandango pink", "DE5285");
		named.put("fashion fuchsia", "F400A1");
		named.put("fawn", "E5AA70");
		named.put("feldgrau", "4D5D53");
		named.put("fern green", "4F7942");
		named.put("field drab", "6C541E");
		named.put("fiery rose", "FF5470");
		named.put("firebrick", "B22222");
		named.put("fire engine red", "CE2029");
		named.put("fire opal", "E95C4B");
		named.put("flame", "E25822");
		named.put("flax", "EEDC82");
		named.put("flirt", "A2006D");
		named.put("floral white", "FFFAF0");
		named.put("fluorescent blue", "15F4EE");
		named.put("forest green (crayola)", "5FA777");
		named.put("forest green (traditional)", "014421");
		named.put("forest green (web)", "228B22");
		named.put("french beige", "A67B5B");
		named.put("french bistre", "856D4D");
		named.put("french blue", "0072BB");
		named.put("french fuchsia", "FD3F92");
		named.put("french lilac", "86608E");
		named.put("french lime", "9EFD38");
		named.put("french mauve", "D473D4");
		named.put("french pink", "FD6C9E");
		named.put("french raspberry", "C72C48");
		named.put("french rose", "F64A8A");
		named.put("french sky blue", "77B5FE");
		named.put("french violet", "8806CE");
		named.put("frostbite", "E936A7");
		named.put("fuchsia", "FF00FF");
		named.put("fuchsia (crayola)", "C154C1");
		named.put("fuchsia purple", "CC397B");
		named.put("fuchsia rose", "C74375");
		named.put("fulvous", "E48400");
		named.put("fuzzy wuzzy", "87421F");
		named.put("gainsboro", "DCDCDC");
		named.put("gamboge", "E49B0F");
		named.put("garnet", "733635");
		named.put("generic viridian", "007F66");
		named.put("ghost white", "F8F8FF");
		named.put("glaucous", "6082B6");
		named.put("glossy grape", "AB92B3");
		named.put("go green", "00AB66");
		named.put("gold", "A57C00");
		named.put("gold (metallic)", "D4AF37");
		named.put("gold (web) (golden)", "FFD700");
		named.put("gold (crayola)", "E6BE8A");
		named.put("gold fusion", "85754E");
		named.put("golden brown", "996515");
		named.put("golden poppy", "FCC200");
		named.put("golden yellow", "FFDF00");
		named.put("goldenrod", "DAA520");
		named.put("granite gray", "676767");
		named.put("granny smith apple", "A8E4A0");
		named.put("gray (web)", "808080");
		named.put("gray (x11)", "BEBEBE");
		named.put("green", "00FF00");
		named.put("green (crayola)", "1CAC78");
		named.put("green (html/css color)", "008000");
		named.put("green (munsell)", "00A877");
		named.put("green (ncs)", "009F6B");
		named.put("green (pantone)", "00AD43");
		named.put("green (pigment)", "00A550");
		named.put("green (ryb)", "66B032");
		named.put("green-blue", "1164B4");
		named.put("green-blue (crayola)", "2887C8");
		named.put("green-cyan", "009966");
		named.put("green lizard", "A7F432");
		named.put("green sheen", "6EAEA1");
		named.put("green-yellow", "ADFF2F");
		named.put("green-yellow (crayola)", "F0E891");
		named.put("grullo", "A99A86");
		named.put("gunmetal", "2a3439");
		named.put("han blue", "446CCF");
		named.put("han purple", "5218FA");
		named.put("hansa yellow", "E9D66B");
		named.put("harlequin", "3FFF00");
		named.put("harvest gold", "DA9100");
		named.put("heat wave", "FF7A00");
		named.put("heliotrope", "DF73FF");
		named.put("heliotrope gray", "AA98A9");
		named.put("hollywood cerise", "F400A1");
		named.put("honeydew", "F0FFF0");
		named.put("honolulu blue", "006DB0");
		named.put("hooker's green", "49796B");
		named.put("hot fuchsia", "FF00C6");
		named.put("hot magenta", "FF1DCE");
		named.put("hot pink", "FF69B4");
		named.put("hunter green", "355E3B");
		named.put("iceberg", "71A6D2");
		named.put("icterine", "FCF75E");
		named.put("illuminating emerald", "319177");
		named.put("imperial red", "ED2939");
		named.put("inchworm", "B2EC5D");
		named.put("independence", "4C516D");
		named.put("india green", "138808");
		named.put("indian red", "CD5C5C");
		named.put("indian yellow", "E3A857");
		named.put("indigo", "4B0082");
		named.put("indigo dye", "00416A");
		named.put("international orange (aerospace)", "FF4F00");
		named.put("international orange (engineering)", "BA160C");
		named.put("international orange (golden gate bridge)", "C0362C");
		named.put("iris", "5A4FCF");
		named.put("irresistible", "B3446C");
		named.put("isabelline", "F4F0EC");
		named.put("italian sky blue", "B2FFFF");
		named.put("ivory", "FFFFF0");
		named.put("jade", "00A86B");
		named.put("japanese carmine", "9D2933");
		named.put("japanese violet", "5B3256");
		named.put("jasmine", "F8DE7E");
		named.put("jazzberry jam", "A50B5E");
		named.put("jet", "343434");
		named.put("jonquil", "F4CA16");
		named.put("june bud", "BDDA57");
		named.put("jungle green", "29AB87");
		named.put("kelly green", "4CBB17");
		named.put("keppel", "3AB09E");
		named.put("key lime", "E8F48C");
		named.put("khaki (web) (khaki)", "C3B091");
		named.put("khaki (x11) (light khaki)", "F0E68C");
		named.put("kobe", "882D17");
		named.put("kobi", "E79FC4");
		named.put("kobicha", "6B4423");
		named.put("kombu green", "354230");
		named.put("ksu purple", "512888");
		named.put("la salle green", "087830");
		named.put("languid lavender", "D6CADD");
		named.put("lanzones", "E0BC5B");
		named.put("lapis lazuli", "26619C");
		named.put("laser lemon", "FFFF66");
		named.put("laurel green", "A9BA9D");
		named.put("lava", "CF1020");
		named.put("lavender (floral)", "B57EDC");
		named.put("lavender (web)", "E6E6FA");
		named.put("lavender blue", "CCCCFF");
		named.put("lavender gray", "C4C3D0");
		named.put("lavender indigo", "9457EB");
		named.put("lavender magenta", "EE82EE");
		named.put("lavender mist", "E6E6FA");
		named.put("lavender purple", "967BB6");
		named.put("lavender rose", "FBA0E3");
		named.put("lemon", "FFF700");
		named.put("lemon chiffon", "FFFACD");
		named.put("lemon curry", "CCA01D");
		named.put("lemon glacier", "FDFF00");
		named.put("lemon iced tea", "BD3000");
		named.put("lemon lime", "E3FF00");
		named.put("lemon lime", "5CFF67");
		named.put("lemon meringue", "F6EABE");
		named.put("lemon yellow", "FFF44F");
		named.put("lemon yellow (crayola)", "FFFF9F");
		named.put("licorice", "1A1110");
		named.put("light brown", "B5651D");
		named.put("light carmine pink", "E66771");
		named.put("light chocolate cosmos", "551F2F");
		named.put("light cobalt blue", "88ACE0");
		named.put("light cornflower blue", "93CCEA");
		named.put("light crimson", "F56991");
		named.put("light cyan", "E0FFFF");
		named.put("light french beige", "C8AD7F");
		named.put("light fuchsia pink", "F984EF");
		named.put("light gold", "B29700");
		named.put("light grayish magenta", "CC99CC");
		named.put("light medium orchid", "D39BCB");
		named.put("light moss green", "ADDFAD");
		named.put("light orchid", "E6A8D7");
		named.put("light pastel purple", "B19CD9");
		named.put("light periwinkle", "C5CBE1");
		named.put("light red", "FFCCCB");
		named.put("light red ochre", "E97451");
		named.put("light salmon pink", "FF9999");
		named.put("light sea green", "20B2AA");
		named.put("light silver", "D8D8D8");
		named.put("light thulian pink", "E68FAC");
		named.put("lilac", "C8A2C8");
		named.put("lilac luster", "AE98AA");
		named.put("lime (color wheel)", "BFFF00");
		named.put("lime (web) (x11 green)", "00FF00");
		named.put("lime green", "32CD32");
		named.put("limerick", "9DC209");
		named.put("lincoln green", "195905");
		named.put("linen", "FAF0E6");
		named.put("lion", "C19A6B");
		named.put("liseran purple", "DE6FA1");
		named.put("little boy blue", "6CA0DC");
		named.put("little girl pink", "F8B9D4");
		named.put("liver", "674C47");
		named.put("liver (dogs)", "B86D29");
		named.put("liver (organ)", "6C2E1F");
		named.put("liver chestnut", "987456");
		named.put("livid", "6699CC");
		named.put("lotion", "FEFDFA");
		named.put("lotion blue", "15F2FD");
		named.put("lotion pink", "ECCFCF");
		named.put("lumber", "FFE4CD");
		named.put("lust", "E62020");
		named.put("maastricht blue", "001C3D");
		named.put("macaroni and cheese", "FFBD88");
		named.put("madder lake", "CC3336");
		named.put("magenta", "FF00FF");
		named.put("magenta (pantone)", "D0417E");
		named.put("magic mint", "AAF0D1");
		named.put("mahogany", "C04000");
		named.put("maize", "FBEC5D");
		named.put("maize (crayola)", "F2C649");
		named.put("majorelle blue", "6050DC");
		named.put("malachite", "0BDA51");
		named.put("manatee", "979AAA");
		named.put("mandarin", "F37A48");
		named.put("mango", "FDBE02");
		named.put("mango green", "96FF00");
		named.put("mango tango", "FF8243");
		named.put("mantis", "74C365");
		named.put("mardi gras", "880085");
		named.put("marigold", "EAA221");
		named.put("maroon (crayola)", "C32148");
		named.put("maroon (html/css)", "800000");
		named.put("maroon (x11)", "B03060");
		named.put("mauve", "E0B0FF");
		named.put("mauve taupe", "915F6D");
		named.put("mauvelous", "EF98AA");
		named.put("maximum blue", "47ABCC");
		named.put("maximum blue green", "30BFBF");
		named.put("maximum blue purple", "ACACE6");
		named.put("maximum green", "5E8C31");
		named.put("maximum green yellow", "D9E650");
		named.put("maximum orange", "FF5B00");
		named.put("maximum purple", "733380");
		named.put("maximum pink", "F6A5F2");
		named.put("maximum red", "D92121");
		named.put("maximum red purple", "A63A79");
		named.put("maximum violet", "892F77");
		named.put("maximum yellow", "FAFA37");
		named.put("maximum yellow red", "F2BA49");
		named.put("may green", "4C9141");
		named.put("maya blue", "73C2FB");
		named.put("medium aquamarine", "66DDAA");
		named.put("medium blue", "0000CD");
		named.put("medium candy apple red", "E2062C");
		named.put("medium carmine", "AF4035");
		named.put("medium champagne", "F3E5AB");
		named.put("medium electric blue", "035096");
		named.put("medium green", "037949");
		named.put("medium jungle green", "1C352D");
		named.put("medium lavender magenta", "DDA0DD");
		named.put("medium orange", "FF7802");
		named.put("medium orchid", "BA55D3");
		named.put("medium persian blue", "0067A5");
		named.put("medium pink", "FE6E9F");
		named.put("medium purple", "9370DB");
		named.put("medium red", "B10304");
		named.put("medium red-violet", "BB3385");
		named.put("medium ruby", "AA4069");
		named.put("medium sea green", "3CB371");
		named.put("medium sky blue", "80DAEB");
		named.put("medium slate blue", "7B68EE");
		named.put("medium spring bud", "C9DC87");
		named.put("medium spring green", "00FA9A");
		named.put("medium taupe", "674C47");
		named.put("medium turquoise", "48D1CC");
		named.put("medium tuscan red", "79443B");
		named.put("medium vermilion", "D9603B");
		named.put("medium violet", "65315F");
		named.put("medium violet-red", "C71585");
		named.put("medium yellow", "FFE302");
		named.put("mellow apricot", "F8B878");
		named.put("mellow yellow", "F8DE7E");
		named.put("melon", "FDBCB4");
		named.put("melon (crayola)", "FEBAAD");
		named.put("menthol", "C1F9A2");
		named.put("metallic blue", "32527B");
		named.put("metallic bronze", "A97142");
		named.put("metallic brown", "AC4313");
		named.put("metallic gold", "D3AF37");
		named.put("metallic green", "296E01");
		named.put("metallic orange", "DA680F");
		named.put("metallic pink", "EDA6C4");
		named.put("metallic red", "A62C2B");
		named.put("metallic seaweed", "0A7E8C");
		named.put("metallic silver", "A8A9AD");
		named.put("metallic sunburst", "9C7C38");
		named.put("metallic violet", "5B0A91");
		named.put("metallic yellow", "FDCC0D");
		named.put("mexican pink", "E4007C");
		named.put("microsoft blue", "00A2ED");
		named.put("microsoft edge blue", "0078D7");
		named.put("microsoft green", "7DB700");
		named.put("microsoft red", "F04E1F");
		named.put("microsoft yellow", "FDB900");
		named.put("middle blue", "7ED4E6");
		named.put("middle blue green", "8DD9CC");
		named.put("middle blue purple", "8B72BE");
		named.put("middle grey", "8B8680");
		named.put("middle green", "4D8C57");
		named.put("middle green yellow", "ACBF60");
		named.put("middle purple", "D982B5");
		named.put("middle red", "E58E73");
		named.put("middle red purple", "A55353");
		named.put("middle yellow", "FFEB00");
		named.put("middle yellow red", "ECB176");
		named.put("midnight", "702670");
		named.put("midnight blue", "191970");
		named.put("midnight blue", "00468C");
		named.put("midnight green (eagle green)", "004953");
		named.put("mikado yellow", "FFC40C");
		named.put("milk", "FDFFF5");
		named.put("milk chocolate", "84563C");
		named.put("mimi pink", "FFDAE9");
		named.put("mindaro", "E3F988");
		named.put("ming", "36747D");
		named.put("minion yellow", "F5E050");
		named.put("mint", "3EB489");
		named.put("mint cream", "F5FFFA");
		named.put("mint green", "98FF98");
		named.put("misty moss", "BBB477");
		named.put("misty rose", "FFE4E1");
		named.put("moccasin", "FAEBD7");
		named.put("mocha", "BEA493");
		named.put("mode beige", "967117");
		named.put("moonstone", "3AA8C1");
		named.put("moonstone blue", "73A9C2");
		named.put("mordant red 19", "AE0C00");
		named.put("morning blue", "8DA399");
		named.put("moss green", "8A9A5B");
		named.put("mountain meadow", "30BA8F");
		named.put("mountbatten pink", "997A8D");
		named.put("msu green", "18453B");
		named.put("mud", "70543E");
		named.put("mughal green", "306030");
		named.put("mulberry", "C54B8C");
		named.put("mulberry (crayola)", "C8509B");
		named.put("mummy's tomb", "828E84");
		named.put("mustard", "FFDB58");
		named.put("mustard brown", "CD7A00");
		named.put("mustard green", "6E6E30");
		named.put("mustard yellow", "E1AD01");
		named.put("myrtle green", "317873");
		named.put("mystic", "D65282");
		named.put("mystic maroon", "AD4379");
		named.put("mystic red", "FF5500");
		named.put("nadeshiko pink", "F6ADC6");
		named.put("napier green", "2A8000");
		named.put("naples yellow", "FADA5E");
		named.put("navajo white", "FFDEAD");
		named.put("navy blue", "000080");
		named.put("navy blue (crayola)", "1974D2");
		named.put("navy purple", "9457EB");
		named.put("neon blue", "1B03A3");
		named.put("neon brown", "C3732A");
		named.put("neon carrot", "AAF0D1");
		named.put("neon cyan", "00FEFC");
		named.put("neon fuchsia", "FE4164");
		named.put("neon gold", "CFAA01");
		named.put("neon gray", "808080");
		named.put("neon dark green", "008443");
		named.put("neon green", "139B42");
		named.put("neon green", "39FF14");
		named.put("neon pink", "FE347E");
		named.put("neon purple", "9457EB");
		named.put("neon red", "FF1818");
		named.put("neon scarlet", "FF2603");
		named.put("neon silver", "CCCCCC");
		named.put("neon tangerine", "F6890A");
		named.put("neon yellow", "FFF700");
		named.put("new car", "214FC6");
		named.put("new york pink", "D7837F");
		named.put("nickel", "727472");
		named.put("nintendo red", "E4000F");
		named.put("non-photo blue", "A4DDED");
		named.put("nyanza", "E9FFDB");
		named.put("ocean boat blue", "0077BE");
		named.put("ochre", "CC7722");
		named.put("office green", "008000");
		named.put("old burgundy", "43302E");
		named.put("old gold", "CFB53B");
		named.put("old heliotrope", "563C5C");
		named.put("old lace", "FDF5E6");
		named.put("old lavender", "796878");
		named.put("old mauve", "673147");
		named.put("old moss green", "867E36");
		named.put("old rose", "C08081");
		named.put("old silver", "848482");
		named.put("olive", "808000");
		named.put("olive drab (#3)", "6B8E23");
		named.put("olive drab #7", "3C341F");
		named.put("olive green", "B5B35C");
		named.put("olivine", "9AB973");
		named.put("onyx", "353839");
		named.put("opal", "A8C3BC");
		named.put("opera mauve", "B784A7");
		named.put("orange", "FF6600");
		named.put("orange (color wheel)", "FF7F00");
		named.put("orange (crayola)", "FF7538");
		named.put("orange (pantone)", "FF5800");
		named.put("orange (ryb)", "FB9902");
		named.put("orange (web)", "FFA500");
		named.put("orange iced tea", "FF6700");
		named.put("orange-red (crayola)", "FF5349");
		named.put("orange soda", "E74E14");
		named.put("orange soda", "FA5B3D");
		named.put("orange-yellow", "F5BD1F");
		named.put("orange-yellow (crayola)", "F8D568");
		named.put("orchid", "DA70D6");
		named.put("orchid pink", "F2BDCD");
		named.put("orchid (crayola)", "E29CD2");
		named.put("orioles orange", "FB4F14");
		named.put("otter brown", "654321");
		named.put("outer space", "414A4C");
		named.put("outer space (crayola)", "2D383A");
		named.put("outrageous orange", "FF6037");
		named.put("oxblood", "800020");
		named.put("oxford blue", "002147");
		named.put("oxley", "6D9A79");
		named.put("ou crimson red", "990000");
		named.put("pacific blue", "1CA9C9");
		named.put("pakistan green", "006600");
		named.put("palatinate blue", "273BE2");
		named.put("palatinate purple", "682860");
		named.put("pale aqua", "BCD4E6");
		named.put("pale blue", "AFEEEE");
		named.put("pale brown", "987654");
		named.put("pale carmine", "AF4035");
		named.put("pale cerulean", "9BC4E2");
		named.put("pale chestnut", "DDADAF");
		named.put("pale copper", "DA8A67");
		named.put("pale cornflower blue", "ABCDEF");
		named.put("pale cyan", "87D3F8");
		named.put("pale gold", "E6BE8A");
		named.put("pale goldenrod", "EEE8AA");
		named.put("pale green", "98FB98");
		named.put("pale lavender", "DCD0FF");
		named.put("pale magenta", "F984E5");
		named.put("pale pink", "FADADD");
		named.put("pale plum", "DDA0DD");
		named.put("pale robin egg blue", "96DED1");
		named.put("pale silver", "C9C0BB");
		named.put("pale spring bud", "ECEBBD");
		named.put("pale taupe", "BC987E");
		named.put("pale turquoise", "AFEEEE");
		named.put("pale violet", "CC99FF");
		named.put("pale violet-red", "DB7093");
		named.put("pansy purple", "78184A");
		named.put("paolo veronese green", "009B7D");
		named.put("papaya whip", "FFEFD5");
		named.put("paradise pink", "E63E62");
		named.put("parchment", "F1E9D2");
		named.put("paris green", "50C878");
		named.put("pastel blue", "AEC6CF");
		named.put("pastel brown", "836953");
		named.put("pastel gray", "CFCFC4");
		named.put("pastel green", "77DD77");
		named.put("pastel magenta", "F49AC2");
		named.put("pastel orange", "FFB347");
		named.put("pastel pink", "DEA5A4");
		named.put("pastel purple", "B39EB5");
		named.put("pastel red", "FF6961");
		named.put("pastel violet", "CB99C9");
		named.put("pastel yellow", "FDFD96");
		named.put("patriarch", "800080");
		named.put("payne's grey", "536878");
		named.put("peach", "FFE5B4");
		named.put("peach (crayola)", "FFCBA4");
		named.put("peach puff", "FFDAB9");
		named.put("pear", "D1E231");
		named.put("pearl", "EAE0C8");
		named.put("pearl aqua", "88D8C0");
		named.put("pearly purple", "B768A2");
		named.put("peridot", "E6E200");
		named.put("periwinkle", "CCCCFF");
		named.put("periwinkle (crayola)", "C3CDE6");
		named.put("persian blue", "1C39BB");
		named.put("persian green", "00A693");
		named.put("persian indigo", "32127A");
		named.put("persian orange", "D99058");
		named.put("persian pink", "F77FBE");
		named.put("persian plum", "701C1C");
		named.put("persian red", "CC3333");
		named.put("persian rose", "FE28A2");
		named.put("persimmon", "EC5800");
		named.put("peru", "CD853F");
		named.put("petal", "F5E2E2");
		named.put("pewter blue", "8BA8B7");
		named.put("philippine blue", "0038A7");
		named.put("philippine bronze", "6E3A07");
		named.put("philippine brown", "5D1916");
		named.put("philippine gold", "B17304");
		named.put("philippine golden yellow", "FFDF00");
		named.put("philippine gray", "8C8C8C");
		named.put("philippine green", "008543");
		named.put("philippine indigo", "00416A");
		named.put("philippine orange", "FF7300");
		named.put("philippine pink", "FA1A8E");
		named.put("philippine red", "CE1127");
		named.put("philippine silver", "B3B3B3");
		named.put("philippine sky blue", "0066FF");
		named.put("philippine violet", "81007F");
		named.put("philippine yellow", "FECB00");
		named.put("phlox", "DF00FF");
		named.put("phthalo blue", "000F89");
		named.put("phthalo green", "123524");
		named.put("picton blue", "45B1E8");
		named.put("pictorial carmine", "C30B4E");
		named.put("piggy pink", "FDDDE6");
		named.put("pine green", "01796F");
		named.put("pine tree", "2A2F23");
		named.put("pineapple", "563C0D");
		named.put("pink", "FFC0CB");
		named.put("pink (pantone)", "D74894");
		named.put("pink diamond (ace hardware color)", "F6D6DE");
		named.put("pink diamond (independent retailers colors)", "F0D3DC");
		named.put("pink flamingo", "FC74FD");
		named.put("pink lace", "FFDDF4");
		named.put("pink pearl", "E7ACCF");
		named.put("pink sherbet", "F78FA7");
		named.put("pistachio", "93C572");
		named.put("platinum", "E5E4E2");
		named.put("plum", "8E4585");
		named.put("plum (web)", "DDA0DD");
		named.put("plump purple", "5946B2");
		named.put("poison purple", "7F01FE");
		named.put("police blue", "374F6B");
		named.put("polished pine", "5DA493");
		named.put("pomp and power", "86608E");
		named.put("portland orange", "FF5A36");
		named.put("powder blue", "B0E0E6");
		named.put("princeton orange", "F58025");
		named.put("prune", "701C1C");
		named.put("prussian blue", "003153");
		named.put("psychedelic purple", "DF00FF");
		named.put("puce", "CC8899");
		named.put("puce red", "722F37");
		named.put("pullman brown (ups brown)", "644117");
		named.put("pumpkin", "FF7518");
		named.put("purple (html)", "800080");
		named.put("purple (munsell)", "9F00C5");
		named.put("purple (x11)", "A020F0");
		named.put("purple heart", "69359C");
		named.put("purple mountain majesty", "9678B6");
		named.put("purple navy", "4E5180");
		named.put("purple pizzazz", "FE4EDA");
		named.put("purple plum", "9C51B6");
		named.put("purple taupe", "50404D");
		named.put("purpureus", "9A4EAE");
		named.put("quartz", "51484F");
		named.put("queen blue", "436B95");
		named.put("queen pink", "E8CCD7");
		named.put("quick silver", "A6A6A6");
		named.put("quinacridone magenta", "8E3A59");
		named.put("quincy", "6A5445");
		named.put("rackley", "5D8AA8");
		named.put("radical red", "FF355E");
		named.put("raisin black", "242124");
		named.put("rajah", "FBAB60");
		named.put("raspberry", "E30B5D");
		named.put("raw sienna", "D68A59");
		named.put("raw umber", "826644");
		named.put("razzmatazz", "E30B5C");
		named.put("razzle dazzle rose", "FF33CC");
		named.put("razzmic berry", "8D4E85");
		named.put("rebecca purple", "663399");
		named.put("red", "FF0000");
		named.put("red (crayola)", "EE204D");
		named.put("red (munsell)", "F2003C");
		named.put("red (ncs)", "C40233");
		named.put("red (pantone)", "ED2939");
		named.put("red (pigment)", "ED1C24");
		named.put("red (ryb)", "FE2712");
		named.put("red-brown", "A52A2A");
		named.put("red cola", "DF0118");
		named.put("red-orange (crayola)", "FF681F");
		named.put("red-orange (color wheel)", "FF4500");
		named.put("red rum", "973A4A");
		named.put("red salsa", "FD3A4A");
		named.put("red strawberry", "EC0304");
		named.put("red-violet", "C71585");
		named.put("red-violet (crayola)", "C0448F");
		named.put("red-violet (color wheel)", "922B3E");
		named.put("redwood", "A45A52");
		named.put("registration black", "000000");
		named.put("resolution blue", "002387");
		named.put("rhythm", "777696");
		named.put("rich brilliant lavender", "F1A7FE");
		named.put("rich carmine", "D70040");
		named.put("rich electric blue", "0892D0");
		named.put("rich lavender", "A76BCF");
		named.put("rich lilac", "B666D2");
		named.put("rich maroon", "B03060");
		named.put("rifle green", "444C38");
		named.put("ripe mango", "FFC324");
		named.put("roast coffee", "704241");
		named.put("robin egg blue", "00CCCC");
		named.put("rocket metallic", "8A7F80");
		named.put("roman silver", "838996");
		named.put("root beer", "290E05");
		named.put("rose", "FF007F");
		named.put("rose dust", "9E5E6F");
		named.put("royal azure", "0038A8");
		named.put("royal blue", "002366");
		named.put("royal blue", "4169E1");
		named.put("royal brown", "523B35");
		named.put("royal fuchsia", "CA2C92");
		named.put("royal green", "136207");
		named.put("royal orange", "F99245");
		named.put("royal pink", "E73895");
		named.put("royal red", "9B1C31");
		named.put("royal red", "D00060");
		named.put("royal purple", "7851A9");
		named.put("royal yellow", "FADA5E");
		named.put("ruby", "E0115F");
		named.put("rufous", "A81C07");
		named.put("rum", "9A4E40");
		named.put("russet", "80461B");
		named.put("russian green", "679267");
		named.put("russian violet", "32174D");
		named.put("rust", "B7410E");
		named.put("rusty red", "DA2C43");
		named.put("sacramento state green", "043927");
		named.put("saddle brown", "8B4513");
		named.put("safety orange", "FF7800");
		named.put("safety orange (blaze orange)", "FF6700");
		named.put("safety yellow", "EED202");
		named.put("saffron", "F4C430");
		named.put("sage", "BCB88A");
		named.put("st. patrick's blue", "23297A");
		named.put("salem", "177B4D");
		named.put("salmon", "FA8072");
		named.put("salmon rose", "E7968B");
		named.put("salmon pink", "FF91A4");
		named.put("samsung blue", "12279E");
		named.put("sand", "C2B280");
		named.put("sand dune", "967117");
		named.put("sandy brown", "F4A460");
		named.put("sandy taupe", "967117");
		named.put("sap green", "507D2A");
		named.put("sapphire", "0F52BA");
		named.put("sapphire blue", "0067A5");
		named.put("satin sheen gold", "CBA135");
		named.put("scarlet", "FF2400");
		named.put("scarlet (crayola)", "FD0E35");
		named.put("schauss pink", "FF91AF");
		named.put("school bus yellow", "FFD800");
		named.put("screamin' green", "66FF66");
		named.put("sea blue", "006994");
		named.put("sea green", "2E8B57");
		named.put("sea green (crayola)", "00FFCD");
		named.put("seal brown", "59260B");
		named.put("seashell", "FFF5EE");
		named.put("selective yellow", "FFBA00");
		named.put("sepia", "704214");
		named.put("shadow", "8A795D");
		named.put("shadow blue", "778BA5");
		named.put("shampoo", "FFCFF1");
		named.put("shamrock green", "009E60");
		named.put("shandy", "FFE670");
		named.put("sheen green", "8FD400");
		named.put("shimmering blush", "D98695");
		named.put("shiny shamrock", "5FA778");
		named.put("shocking pink", "FC0FC0");
		named.put("shocking pink (crayola)", "FF6FFF");
		named.put("sienna", "882D17");
		named.put("silver", "C0C0C0");
		named.put("silver (crayola)", "C9C0BB");
		named.put("silver (metallic)", "AAA9AD");
		named.put("silver chalice", "ACACAC");
		named.put("silver foil", "AFB1AE");
		named.put("silver lake blue", "5D89BA");
		named.put("silver pink", "C4AEAD");
		named.put("silver sand", "BFC1C2");
		named.put("sinopia", "CB410B");
		named.put("sizzling red", "FF3855");
		named.put("sizzling sunrise", "FFDB00");
		named.put("skobeloff", "007474");
		named.put("sky blue", "87CEEB");
		named.put("sky blue (crayola)", "76D7EA");
		named.put("sky magenta", "CF71AF");
		named.put("slate blue", "6A5ACD");
		named.put("slate gray", "708090");
		named.put("slimy green", "299617");
		named.put("smalt (dark powder blue)", "003399");
		named.put("smoke", "738276");
		named.put("smokey topaz", "832A0D");
		named.put("smoky black", "100C08");
		named.put("soap", "CEC8EF");
		named.put("solid pink", "893843");
		named.put("sonic silver", "757575");
		named.put("spartan crimson", "9E1316");
		named.put("space cadet", "1D2951");
		named.put("spanish bistre", "807532");
		named.put("spanish blue", "0070B8");
		named.put("spanish carmine", "D10047");
		named.put("spanish crimson", "E51A4C");
		named.put("spanish gray", "989898");
		named.put("spanish green", "009150");
		named.put("spanish orange", "E86100");
		named.put("spanish pink", "F7BFBE");
		named.put("spanish purple", "66033C");
		named.put("spanish red", "E60026");
		named.put("spanish sky blue", "00FFFF");
		named.put("spanish violet", "4C2882");
		named.put("spanish viridian", "007F5C");
		named.put("spanish yellow", "F6B511");
		named.put("spicy mix", "8B5f4D");
		named.put("spring bud", "A7FC00");
		named.put("spring frost", "87FF2A");
		named.put("spring green", "00FF7F");
		named.put("spring green (crayola)", "ECEBBD");
		named.put("star command blue", "007BB8");
		named.put("steel blue", "4682B4");
		named.put("steel pink", "CC33CC");
		named.put("steel teal", "5F8A8F");
		named.put("stil de grain yellow", "FADA5E");
		named.put("straw", "E4D96F");
		named.put("strawberry", "FC5A8D");
		named.put("stop red", "CF142B");
		named.put("strawberry iced tea", "FC5A8D");
		named.put("strawberry red", "C83F49");
		named.put("sugar plum", "914E75");
		named.put("sunglow", "FFCC33");
		named.put("sunray", "E3AB57");
		named.put("sunset", "FAD6A5");
		named.put("sunset orange", "FD5E53");
		named.put("super pink", "CF6BA9");
		named.put("sweet brown", "A83731");
		named.put("tan", "D2B48C");
		named.put("tan (crayola)", "D99A6C");
		named.put("tangelo", "F94D00");
		named.put("tangerine", "F28500");
		named.put("tangerine yellow", "FFCC00");
		named.put("tango pink", "E4717A");
		named.put("tart orange", "FB4D46");
		named.put("taupe", "483C32");
		named.put("taupe gray", "8B8589");
		named.put("tea green", "D0F0C0");
		named.put("tea rose", "F88379");
		named.put("tea rose", "F4C2C2");
		named.put("teal", "008080");
		named.put("teal blue", "367588");
		named.put("teal deer", "99E6B3");
		named.put("teal green", "00827F");
		named.put("telemagenta", "CF3476");
		named.put("temptress", "3C2126");
		named.put("tenn� (tawny)", "CD5700");
		named.put("terra cotta", "E2725B");
		named.put("thistle", "D8BFD8");
		named.put("thistle (crayola)", "EBB0D7");
		named.put("thulian pink", "DE6FA1");
		named.put("tickle me pink", "FC89AC");
		named.put("tiffany blue", "81D8D0");
		named.put("tiger's eye", "E08D3C");
		named.put("timberwolf", "DBD7D2");
		named.put("titanium", "878681");
		named.put("titanium yellow", "EEE600");
		named.put("tomato", "FF6347");
		named.put("tomato sauce", "B21807");
		named.put("tooth", "FFFAFA");
		named.put("topaz", "FFC87C");
		named.put("tractor red", "FD0E35");
		named.put("trolley grey", "808080");
		named.put("tropical rain forest", "00755E");
		named.put("true blue", "0073CF");
		named.put("tufts blue", "3E8EDE");
		named.put("tulip", "FF878D");
		named.put("tumbleweed", "DEAA88");
		named.put("turkish rose", "B57281");
		named.put("turquoise", "40E0D0");
		named.put("turquoise green", "A0D6B4");
		named.put("tuscan brown", "6F4E37");
		named.put("tuscan red", "7C4848");
		named.put("tuscan tan", "A67B5B");
		named.put("tuscany", "C09999");
		named.put("twitter blue", "26A7DE");
		named.put("twilight lavender", "8A496B");
		named.put("tyrian purple", "66023C");
		named.put("ultramarine", "3F00FF");
		named.put("ultramarine blue", "4166F5");
		named.put("ultramarine blue (caran d'ache)", "2111EF");
		named.put("ultra pink", "FF6FFF");
		named.put("ultra red", "FC6C85");
		named.put("umber", "635147");
		named.put("unbleached silk", "FFDDCA");
		named.put("united nations blue", "5B92E5");
		named.put("unmellow yellow", "FFFF66");
		named.put("up maroon", "7B1113");
		named.put("upsdell red", "AE2029");
		named.put("urobilin", "E1AD21");
		named.put("vampire black", "080808");
		named.put("van dyke brown", "664228");
		named.put("vanilla", "F3E5AB");
		named.put("vanilla ice", "F38FA9");
		named.put("vegas gold", "C5B358");
		named.put("venetian red", "C80815");
		named.put("verdigris", "43B3AE");
		named.put("vermilion", "E34234");
		named.put("vermilion", "D9381E");
		named.put("veronica", "A020F0");
		named.put("very light azure", "74BBFB");
		named.put("very light blue", "6666FF");
		named.put("very light malachite green", "64E986");
		named.put("very light tangelo", "FFB077");
		named.put("very pale orange", "FFDFBF");
		named.put("very pale yellow", "FFFFBF");
		named.put("vine green", "164010");
		named.put("violet", "8F00FF");
		named.put("violet (caran d'ache)", "6E00C0");
		named.put("violet (color wheel)", "7F00FF");
		named.put("violet (crayola)", "963D7F");
		named.put("violet (ryb)", "8601AF");
		named.put("violet (web)", "EE82EE");
		named.put("violet-blue", "324AB2");
		named.put("violet-blue (crayola)", "766EC8");
		named.put("violin brown", "674403");
		named.put("viridian", "40826D");
		named.put("viridian green", "009698");
		named.put("vista blue", "7C9ED9");
		named.put("vivid amber", "cc9900");
		named.put("vivid auburn", "922724");
		named.put("vivid burgundy", "9F1D35");
		named.put("vivid cerise", "DA1D81");
		named.put("vivid cerulean", "00AAEE");
		named.put("vivid crimson", "CC0033");
		named.put("vivid gamboge", "FF9900");
		named.put("vivid lime green", "a6d608");
		named.put("vivid mulberry", "B80CE3");
		named.put("vivid orange", "FF5F00");
		named.put("vivid orange peel", "FFA000");
		named.put("vivid orchid", "CC00FF");
		named.put("vivid raspberry", "FF006C");
		named.put("vivid red", "F70D1A");
		named.put("vivid red-tangelo", "DF6124");
		named.put("vivid tangelo", "F07427");
		named.put("vivid vermilion", "e56024");
		named.put("vivid violet", "9F00FF");
		named.put("vivid yellow", "FFE302");
		named.put("water", "D4F1F9");
		named.put("watermelon", "F05C85");
		named.put("watermelon red", "BF4147");
		named.put("watermelon yellow", "EEFF1B");
		named.put("waterspout", "A4F4F9");
		named.put("wenge", "645452");
		named.put("wheat", "F5DEB3");
		named.put("white", "FFFFFF");
		named.put("white chocolate", "EDE6D6");
		named.put("white coffee", "E6E0D4");
		named.put("white smoke", "F5F5F5");
		named.put("wild orchid", "D470A2");
		named.put("wild strawberry", "FF43A4");
		named.put("wild watermelon", "FC6C85");
		named.put("willpower orange", "FD5800");
		named.put("windsor tan", "A75502");
		named.put("wine", "722F37");
		named.put("wine red", "B11226");
		named.put("winter sky", "FF007C");
		named.put("wintergreen dream", "56887D");
		named.put("wisteria", "C9A0DC");
		named.put("wood brown", "C19A6B");
		named.put("xanadu", "738678");
		named.put("yellow", "FFFF00");
		named.put("yellow (crayola)", "FCE883");
		named.put("yellow (munsell)", "EFCC00");
		named.put("yellow (ncs)", "FFD300");
		named.put("yellow (pantone)", "FEDF00");
		named.put("yellow (process)", "FFEF00");
		named.put("yellow (ryb)", "FEFE33");
		named.put("yellow-green", "9ACD32");
		named.put("yellow-green (crayola)", "C5E384");
		named.put("yellow orange", "FFAE42");
		named.put("yellow rose", "FFF000");
		named.put("yellow sunshine", "FFF700");
		named.put("yinmn blue", "2E5090");
		named.put("zaffre", "0014A8");
		named.put("zebra white", "F5F5F5");
		named.put("zinnwaldite", "2C1608");
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:52:52 ---------------------------------------------------
	 */
	/**
	 * Searches {@link #named} and gets the hex colour code mapped to the argument.
	 * Will return {@code 255} as the default.
	 * 
	 * @param lowerCaseName the lower
	 * @return the mapping for the argument or else returns {@code 255} if no
	 *         mapping exists.
	 */
	static int of(String lowerCaseName) {
		try {
			int i = Integer.parseInt(named.get(lowerCaseName), 16);
			$n = lowerCaseName;
			return i;
		} catch (NullPointerException e) {
			$n = "black";
			return 0xFF;
		}
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:50:23 ---------------------------------------------------
	 */
	/**
	 * Gets the internal supported name for the hex colour code.
	 * 
	 * @param opaque the opaque color i.e it does not have an alpha value in it,
	 *               only the red, green and blue component are present.
	 * @return a valid name for the argument else returns an empty string if no
	 *         mapping exists.
	 */
	private static String getIfCompatible(int opaque) {
		Set<Map.Entry<String, String>> data = named.entrySet();
		for (Map.Entry<String, String> entry : data) {
			int current = Integer.valueOf(entry.getValue(), 16);
			if (current == opaque) {
				$n = entry.getKey();
				return entry.getKey();
			}
		}
		return "";
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 16:11:09 ---------------------------------------------------
	 */
	/**
	 * Constructs a {@code NameColour} from the given supported name.
	 * 
	 * @param c and supported name as a string.
	 */
	public NamedColour(String name) {
		super((of(name.toLowerCase()) << 8) | 0xFF);
		if (name.compareToIgnoreCase($n) != 0)
			throw new IllegalArgumentException(name + " is not a valid name");
		this.name = $n;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 17:29:29 ---------------------------------------------------
	 */
	/**
	 * Sets only the opaque part of the value, hence calling
	 * <code>setColour(0xFF_E5_B4_88)</code> is the same as calling
	 * <code>setColour(0xFF_E5_B4)</code>.
	 * <p>
	 * A condition for which the argument will be is set is that it must have a
	 * named mapping for it or else this method does nothing when called.
	 * 
	 * @param c the colour whose opaque part will be set
	 */
	@Override
	public void setColour(int c) {
		setOpaqueColour(c >>> 8);// remove the opacity
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 17:32:00 ---------------------------------------------------
	 */
	/**
	 * Does nothing. This method, when called, has no effect.
	 * 
	 * @param a any value.
	 */
	@Override
	public void setOpacity(int a) {
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 17:32:14 ---------------------------------------------------
	 */
	/**
	 * Sets the opaque part of the colour if, and only if, there is a named
	 * equivalent.
	 * 
	 * @param c the opaque colour to be set.
	 */
	@Override
	public void setOpaqueColour(int c) {
		if (getIfCompatible(c).length() > 0) {
			super.setOpaqueColour(c);
			this.name = $n;
		}
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:25:53 ---------------------------------------------------
	 */
	/**
	 * Sets the name if, and only if, the argument is valid name. See class
	 * documentation above for details.
	 * <p>
	 * If the argument is not a valid name, this method has no effect.
	 * 
	 * @param name the colour name to be set.
	 */
	public void setName(String name) {
		super.setColour((of(name.toLowerCase()) << 8) | 0xFF);
		if (name.compareToIgnoreCase($n) != 0)
			throw new IllegalArgumentException(name + " is not a valid name");
		this.name = $n;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 19:25:21 ---------------------------------------------------
	 */
	/**
	 * Gets the name of this colour.
	 * 
	 * @return the name of the current colour.
	 */
	public String getName() {
		return name;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:21:34 ---------------------------------------------------
	 */
	/**
	 * <code>true</code> if and only if the argument is an instance of
	 * {@code NamedColour} and if and only if {@link #isSameColour(WebColour)} is
	 * true for the argument.
	 * 
	 * @param o {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	public boolean equals(Object o) {
		if (o instanceof NamedColour) {
			NamedColour n = (NamedColour) o;
			return isSameColour(n);
		}
		return false;
	}

	/*
	 * Date: 24 Nov 2023 -----------------------------------------------------------
	 * Time created: 11:22:52 ---------------------------------------------------
	 */
	/**
	 * Gets the name of this colour as a {@code String}.
	 * <p>
	 * The value returned may be incompatible with CSS
	 * 
	 * @return the CSS format of this colour.
	 */
	public String toString() {
		return toFlatCase(getName());
	}

	/**
	 * The name of this colour.
	 */
	private String name;

}
