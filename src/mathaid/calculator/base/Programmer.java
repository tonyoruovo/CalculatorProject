/**
 * 
 */
package mathaid.calculator.base;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mathaid.calculator.base.gui.Keys;
import mathaid.calculator.base.parser.CEvaluator;
import mathaid.designpattern.Observer;

/*
 * Date: 19 Jun 2021----------------------------------------------------------- 
 * Time created: 17:58:36---------------------------------------------------  
 * Package: mathaid.calculator.base------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: Programmer.java------------------------------------------------------ 
 * Class name: Programmer------------------------------------------------ 
 */
/**
 * One of the 3 main evaluators (which represents one of the 3 modes) of the
 * {@code Calculator} class that can evaluate symbolic values and make
 * calculations like a calculator built for computer programmers. It is equipped
 * with a {@link DetailsList} objects (like all {@code CEvaluator} objects) for
 * advanced details of a calculation such as alternate radix forms, more info
 * about the floating-point form, precision, number form (to and from
 * floating-point) etc.
 * <p>
 * This class implements the {@code CEvaluator} interface and as a result is
 * both a {@code Subject} and an {@code Observer}. Within the {@code Calculator}
 * class, it is registered to the {@code Input} class (as is all the evaluators
 * of the {@code Calculator} class) for receiving text inputs passed by the
 * user. It also registers the {@code Output} (as does all the evaluators of the
 * {@code Calculator} class) and the {@code DetailsList.ProgDetailsList}
 * classes, for dispersing results of computations. When an input is received
 * from an {@code Input} object, it processes the input and if the expression is
 * well formed proceeds to evaluate it utilising an {@link InternalEvaluator
 * internal evaluator engine} after which it is sent to both the {@code Output}
 * and {@code DetailsList.ProgDetailsList} classes for pretty-printing.
 * Recording of calculations (retrieved via {@link #getHistory()}) is only done
 * upon calling {@link #evaluate()}, but calling {@code evaluate()} may produce
 * an error (more formally an exception) if the data which should be evaluated
 * is never updated as the field for the input text is declared as
 * {@code volatile} which means that the system will never cache it's value
 * hence it needs to be constantly updated or else it may be found to be
 * {@code null}.
 * </p>
 * <p>
 * The {@code Calculator} class has implemented this object to work
 * concurrently. That is, every time a user enters a value into the input, this
 * class' {@link #doAction()} method runs and before {@link #update()} is called
 * to update the output. However, these methods of implementation will ignore
 * the internal {@code History} object which may not record vital calculations
 * being made. The {@code Calculator} class fixes this by calling
 * {@link #evaluate()} - which actually records the calculations - when the
 * equals button is pressed.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @see InternalEvaluator
 * @see CEvaluator
 * @see DataText
 * @see Calculator
 * @see DetailsList.ProgDetailsList
 * @see History
 * 
 */
public class Programmer implements CEvaluator<DataText> {
	
	public static final String OPTIONS_KEY_SHIFT = "isShift";
	public static final String OPTIONS_KEY_ENDIANESS = "endianess";
	public static final String OPTIONS_KEY_RADIX = "radix";
	public static final String OPTIONS_KEY_TRIG = "trig";
	public static final String OPTIONS_KEY_FP_RESULT_TEXT = "fpResultDisplay";
	public static final String OPTIONS_KEY_FP_CURRENT_RESULT = "fpCurrentResult";
	public static final String OPTIONS_KEY_BITLENGTH = "bitLength";
	public static final String OPTIONS_KEY_BINARY_REP = "bitRep";
	public static final String OPTIONS_KEY_CHARSETS = "charsets";
	public static final String OPTIONS_KEY_CURRENT_CHARSET = "currentCharsets";
	
	private static final List<Charset> CHARSETS = new ArrayList<>();
	
	private static boolean charsetIsSupported(Charset c) {
		try {
			"dis is a str!".getBytes(c);
			return true;
		}catch(@SuppressWarnings("unused") Exception e) {
			return false;
		}
	}
	
	static {
		Collection<Charset> c = Charset.availableCharsets().values();
		for(Charset charset : c) {
			if(charsetIsSupported(charset))
				CHARSETS.add(charset);
		}
	}

	/*
	 * Date: 19 Jun 2021-----------------------------------------------------------
	 * Time created: 17:58:36---------------------------------------------------
	 */
	/**
	 * A sole constructor for this class that initialises the observers list, the
	 * input object, the {@link InternalEvaluator internal evaluation engine}, the
	 * history and details list. It also registers the details list to itself. This
	 * is done so as to prevent the calculator class from directly calling this
	 * object.
	 */
	public Programmer() {
		
		options.put(OPTIONS_KEY_SHIFT, false);//for shift and second function keys
		options.put(OPTIONS_KEY_ENDIANESS, false);//Big-Endian, Small-Endian, Mid-Endian
		options.put(OPTIONS_KEY_RADIX, 2);// 2-binary, 8-octal, 10-decimal, 16-hexadecimal
		/*
		 * For displaying the current floating-point input and result type such as
		 * canonical (normal), normalised and integer only
		 */
		options.put(OPTIONS_KEY_FP_RESULT_TEXT, List.of("CANON", "NORM", "INTEGER"));
		options.put(OPTIONS_KEY_FP_CURRENT_RESULT, 0);//The current index of the floating-point result format list
		options.put(OPTIONS_KEY_TRIG, 0);// Such as DEG, RAD and GRAD
		/*
		 * 4-nibble, 8-byte, 16-word, 32-double word, 64-quad word,
		 * 128-double quad word, 256-double-double-quad word, unlimited
		 * for MATH
		 * 
		 * Note that for floating point, nibble and unlimited is not
		 * included, so, during transition between floating-point and
		 * integers, nibble should be skipped
		 */
		options.put(OPTIONS_KEY_BITLENGTH, 32);
		/*
		 * 0 - two's complement
		 * 1 - one's complement
		 * 2 - excess-n
		 * 3 - negabinary
		 * 4 - unsigned
		 * 5 - math
		 * 6 - floating-point
		 */
		options.put(OPTIONS_KEY_BINARY_REP, 0);
		/*
		 * all the character sets that can be converted via toString and from string
		 */
		options.put(OPTIONS_KEY_CHARSETS, CHARSETS);
		//The current index of the character set
		options.put(OPTIONS_KEY_CURRENT_CHARSET, 0);

		history = new History<>();
		details = new DetailsList.ProgDetailsList();
		evaluator = new InternalEvaluator();
		observers = new ArrayList<>();
		register(details);
		// XXX: version 100100
		mem = new DataText("0", "0");
	}

	/*
	 * Most Recent Date: 19 Jun 2021-----------------------------------------------
	 * Most recent time created: 17:59:36--------------------------------------
	 */
	/**
	 * Calls {@link #doAction()} and then records the input in the internal
	 * {@code History} object then returns the result of the calculation as a
	 * {@code DataText}.
	 * 
	 * @return a {@code DataText} object containing the TeX notation and the plain
	 *         java string of the result of the evaluation.
	 */
	@Override
	public DataText evaluate() {
		doAction();
		history.record(input);
		return output;
	}

	/*
	 * Most Recent Date: 19 Jun 2021-----------------------------------------------
	 * Most recent time created: 17:59:36--------------------------------------
	 */
	/**
	 * Registers the given {@code Observer}. In practice, this registers the given
	 * calculator output and details list.
	 * 
	 * @param o the {@code Observer} to be registered.
	 */
	@Override
	public void register(Observer<DataText> o) {
		observers.add(o);
	}

	/*
	 * Most Recent Date: 19 Jun 2021-----------------------------------------------
	 * Most recent time created: 17:59:36--------------------------------------
	 */
	/**
	 * Unregisters the given {@code Observer}.
	 * 
	 * @param o the {@code Observer} to be unregistered.
	 */
	@Override
	public void unRegister(Observer<DataText> o) {
		observers.remove(o);
	}

	/*
	 * Most Recent Date: 19 Jun 2021-----------------------------------------------
	 * Most recent time created: 17:59:36--------------------------------------
	 */
	/**
	 * Updates all the {@code Observer} objects registered to this object. In
	 * practice, only the {@code Output} and {@code DetailsList.ProgDetailsList}
	 * objects will be updated.
	 */
	@Override
	public void update() {
		for (Observer<DataText> o : observers) {
			o.inform(output);
		}
	}

	/*
	 * Most Recent Date: 19 Jun 2021-----------------------------------------------
	 * Most recent time created: 17:59:36--------------------------------------
	 */
	/**
	 * Updates the input source of this object with the latest value. The updated
	 * source is used as the data upon which evaluation is made. This method is
	 * meant only to be called by the {@code Subject} that registered this object.
	 * <b><i>This method must first be called before doAction() or else users may
	 * have undefined results.</i></b>
	 * 
	 * @param t the latest value to be evaluated.
	 */
	@Override
	public void inform(DataText t) {
		this.input = t;
	}

	/*
	 * Most Recent Date: 27 Jun 2021-----------------------------------------------
	 * Most recent time created: 13:27:37--------------------------------------
	 */
	/**
	 * Evaluates current expression given by {@link #inform(DataText)}. This action
	 * does not record any data into the {@code History} of this object.
	 */
	@Override
	public void doAction() {
		/*
		 * TODO: We should only catch parser and lexer syntax exception. My thought on
		 * how to do this is create a specific ParseException class which inherits
		 * RuntimeExcetpion catch that specific exception here then immediately after
		 * get the UncaucghtExceptionManager object to retrieve the last error message
		 */
		synchronized (this) {
			output = evaluator.evaluate(input.getSymjaString());
		}
	}

	/*
	 * Most Recent Date: 19 Jun 2021-----------------------------------------------
	 * Most recent time created: 17:59:36--------------------------------------
	 */
	/**
	 * Returns the history of the input received by this object.
	 * 
	 * @return a {@code History} of {@code DataText} objects containing calculations
	 *         received by this object since it's initialisation.
	 */
	@Override
	public History<DataText> getHistory() {
		return history;
	}

	/*
	 * Most Recent Date: 19 Jun 2021-----------------------------------------------
	 * Most recent time created: 17:59:36--------------------------------------
	 */
	/**
	 * Returns the {@code DetailsList} of this object.
	 * 
	 * @return a {@code DetailsList.ProgDetailsList}.
	 */
	@Override
	public DetailsList getDetails() {
		return details;
	}

	/*
	 * Most Recent Date: 23 Aug 2021-----------------------------------------------
	 * Most recent time created: 13:51:42--------------------------------------
	 */
	/**
	 * Adds the current conversion result to the previous one stored in the memory
	 * and returns the last value it.
	 * 
	 * @return the result gotten from adding the previous to the current one..
	 */
	// XXX: version 100100
	@Override
	public DataText mPlus() {
		mem = evaluator.evaluate(mem.getSymjaString() + "+" + output.getSymjaString());
		return mem;
	}

	/*
	 * Most Recent Date: 23 Aug 2021-----------------------------------------------
	 * Most recent time created: 13:57:35--------------------------------------
	 */
	/**
	 * Subtracts the current result from the previous one stored in the memory and
	 * returns the result of the operation.
	 * 
	 * @return the result gotten from subtracting the previous one from the current
	 *         one.
	 */
	// XXX: version 100100
	@Override
	public DataText mMinus() {
		mem = evaluator.evaluate(mem.getSymjaString() + "-" + output.getSymjaString());
		return mem;
	}

	/**
	 * The internal calculations history holder.
	 */
	private final History<DataText> history;
	/**
	 * The internal evaluation engine that evaluates expressions
	 */
	private final InternalEvaluator evaluator; // Internal calculator
	/**
	 * Inputs received from the user.
	 */
	private volatile DataText input;
	/**
	 * Holds the results of computations.
	 */
	private DataText output;
	/**
	 * The {@code List} of {@code Observer} objects registered to this object via
	 * {@link #register(Observer)}.
	 */
	private List<Observer<DataText>> observers;
	/**
	 * The {@code DetailsList} that holds details about calculations.
	 */
	private final DetailsList details;
	/**
	 * The memory of this calculator specified by the "M" symbol at the bottom of
	 * the input screen.
	 */
	// XXX: version 100100
	private DataText mem;
	private Keys modKeys;
	
	private final Map<String, Object> options = new HashMap<>();

}
