/**
 * 
 */
package mathaid.calculator.base;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;

import mathaid.IndexBeyondLimitException;
import mathaid.calculator.base.parser.CEvaluator;

/*
 * Date: 13 Jun 2020----------------------------------------------------------- 
 * Time created: 15:12:41---------------------------------------------------  
 * Package: calculator------------------------------------------------ 
 * Project: MyTempTest------------------------------------------------ 
 * File: Calculator.java------------------------------------------------------ 
 * Class name: Calculator------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class Calculator {

	public static final String INFINITY = "Infinity";
	public static final String PI = "pi";
	public static final String E = "e";
	public static final String SIN = "sin";
	public static final String ASIN = "arcsin";
	public static final String SINH = "sinh";
	public static final String ASINH = "arcsinh";
	public static final String COS = "cos";
	public static final String ACOS = "arccos";
	public static final String COSH = "cosh";
	public static final String ACOSH = "arccosh";
	public static final String TAN = "tan";
	public static final String ATAN = "arctan";
	public static final String TANH = "tanh";
	public static final String ATANH = "arctanh";
	public static final String COT = "cot";
	public static final String ACOT = "arccot";
	public static final String COTH = "coth";
	public static final String ACOTH = "arccoth";
	public static final String CSC = "csc";
	public static final String ACSC = "arccsc";
	public static final String CSCH = "csch";
	public static final String ACSCH = "arccsch";
	public static final String SEC = "sec";
	public static final String ASEC = "arcsec";
	public static final String SECH = "sech";
	public static final String ASECH = "arcsech";
	public static final String LOG = "log";
	public static final String LN = "ln";
	public static final String LOG10 = "log10";
	public static final String LOG2 = "log2";
	public static final String EXP = "exp";
	public static final String SQRT = "sqrt";
	public static final String CBRT = "cbrt";
	public static final String ABS = "abs";
	public static final String SIGN = "sign";
	public static final String GCD = "gcd";
	public static final String LCM = "lcm";
	public static final String MOD = "mod";
	public static final String NPR = "npr";
	public static final String NCR = "ncr";
	public static final String GAMMA = "gamma";
	public static final String POLYGAMMA = "polygamma";
	public static final String SUM = "sum";
	public static final String DIFF = "d";
	public static final String INT = "integrate";
	public static final String NINT = "nintegrate";
	public static final String MIN = "min";
	public static final String MAX = "max";
	public static final String CONJ = "conj";
	public static final String ERF = "erf";
	public static final String ULP = "ulp";
	public static final String SQ = "square";
	public static final String CB = "cube";
	public static final String FTB = "floattobits";
	public static final String BTF = "bitstofloat";
	public static final String AND = "and";
	public static final String OR = "or";
	public static final String NOR = "nor";
	public static final String NAND = "nand";
	public static final String NOT = "not";
	public static final String XNOR = "xnor";
	public static final String XOR = "xor";
	public static final String RL = "rl";
	public static final String RR = "rr";
	public static final String RRN = "rrn";
	public static final String RLN = "rln";
	public static final String SL = "sl";
	public static final String SR = "sr";
	public static final String SRN = "srn";
	public static final String SLN = "sln";

	public static final int SCIENTIFIC = 0;
	public static final int PROGRAMMER = 1;
	public static final int CONVERTER = 2;

	public static final int DEFAULT_PRECISION = 0x000000C8;
	private static final int CALC_MAX = 3;
	public static final BigDecimal MAX_FACTORIAL = new BigDecimal("9999999");

	/*
	 * public static final PrintStream OUT; static final InputStream IN; static
	 * final PrintStream ERR;
	 * 
	 * static {
	 * 
	 * // Output stream File file0 = new File("res/output.txt"); if
	 * (!file0.exists()) try { file0.createNewFile(); } catch (IOException e) {
	 * e.printStackTrace(); } FileOutputStream fs = null; try { fs = new
	 * FileOutputStream(file0); } catch (FileNotFoundException e) {
	 * e.printStackTrace(); } OUT = new PrintStream(new BufferedOutputStream(fs));
	 * 
	 * // Error stream
	 * 
	 * file0 = new File("res/error.txt"); if (!file0.exists()) try {
	 * file0.createNewFile(); } catch (IOException e) { e.printStackTrace(); } fs =
	 * null; try { fs = new FileOutputStream(file0, true); } catch
	 * (FileNotFoundException e) { e.printStackTrace(); } ERR = new PrintStream(new
	 * BufferedOutputStream(fs));
	 * 
	 * // Input Stream IN = System.in;
	 * 
	 * }
	 */

	@SuppressWarnings("unchecked")
	public Calculator() {
		input = new Input();
		output = new Output();
//		details = new DetailsList.SciDetailsList(sciCalc.getEvaluator());
		evaluators = new CEvaluator[CALC_MAX];
		evaluators[0] = new Scientific();
//		evaluators[0].register(output);
//		evaluators[0].register(details);
		evaluators[1] = new Programmer();
//		evaluators[1].register(output);
		evaluators[2] = new Converter();
//		evaluators[2].register(output);
		input.register(current());
		for (CEvaluator<DataText> ev : evaluators)
			ev.register(output);

//		scanner = new Scanner(System.in);
	}

	public CEvaluator<DataText> current() {
		return evaluators[currentCalculator];
	}

	public void start(String eq) {
//		String eq = scanner.next();
		input.input(eq);
		new Thread(() -> {
			textChanged();
		}).start();
	}

	public void logCalculation(File file, Object result) {
		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		FileOutputStream fs = null;
		try {
			fs = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		PrintStream out = new PrintStream(new BufferedOutputStream(fs));
		out.println(result);
		out.close();
	}

	public void logError(File file, Object err) {
		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		FileOutputStream fs = null;
		try {
			fs = new FileOutputStream(file, true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		PrintStream error = new PrintStream(new BufferedOutputStream(fs));
		error.println(err);
		error.close();
	}

//	void stop() {
//		scanner.close();
//	}

	public void textChanged() {
		input.update();
		current().doAction();
		current().update();
		new Thread(() -> {
			output.doAction();
			try {
				current().getDetails().doAction();
//				OUT.println(current().evaluate());
				logCalculation(new File("res/output.txt"), current().evaluate());
			} catch (Throwable e) {
				System.err.println(e);
//				ERR.println(e);
				logError(new File("res/error.txt"), e);
			}
//			System.out.println(current().getDetails().getLastDetails());
		}).start();
	}

	public void equals() {
		new Thread(() -> {
			current().evaluate();
			current().update();
//			OUT.println(current().getHistory().getList().get(current().getHistory().getList().size() - 1));
			logCalculation(new File("res/output.txt"),
					current().getHistory().getList().get(current().getHistory().getList().size() - 1));
		});
	}

	public void next() {
		input.unRegister(current());
		currentCalculator++;
		if (currentCalculator >= CALC_MAX)
			// XXX: changed in version 100100
//			currentCalculator = 0;
			currentCalculator = CALC_MAX - 1;
		input.register(current());
	}

	public void previous() {
		input.unRegister(current());
		currentCalculator--;
		if (currentCalculator < 0)
			// XXX: changed in version 100100
//			currentCalculator = CALC_MAX - 1;
			currentCalculator = 0;
		input.register(current());
	}

	// XXX: version 100100
	public void ac() {
		input.input("");
	}

	// XXX: version 100100
	public void mPlus() {
		memory = current().mPlus();
	}

	// XXX: version 100100
	public void mMinus() {
		memory = current().mMinus();
	}

	// XXX: version 100100
	public DataText m() {
		return memory;
	}

	// XXX: version 100100
	public DataText getOutput() {
		return output.getOutput();
	}

	// XXX: version 100100
	public DataText getInput() {
		return input.input;
	}

	// XXX: version 100100
	public void setCalculator(int index) {
		if (index < 0 || index >= CALC_MAX)
			new IndexBeyondLimitException(index, index < 0 ? 0 : CALC_MAX - 1);
		input.unRegister(current());
		currentCalculator = index;
		input.register(current());
	}

//	@Override
//	public void close() {
//		scanner.close();
//	}

//	private ObjectOutputStream savedInstance;

//	private DetailsList details;
	private CEvaluator<DataText>[] evaluators;
	private Input input;
	private Output output;
	private int currentCalculator;

//	private Scanner scanner;
	// XXX: version 100100
	private DataText memory;
}
