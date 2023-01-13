/**
 * 
 */
package mathaid.calculator.base;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.EvalUtilities;
import org.matheclipse.core.interfaces.IExpr;

import mathaid.calculator.base.format.tex.input.InputSegment;
import mathaid.calculator.base.gui.KeyForm;
import mathaid.calculator.base.gui.Keys;
import mathaid.calculator.base.gui.ScientificKeyForms;
import mathaid.calculator.base.parser.CEvaluator;
import mathaid.designpattern.Observer;

/*
 * Date: 8 Apr 2021----------------------------------------------------------- 
 * Time created: 18:44:17---------------------------------------------------  
 * Package: mathaid.calculator------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: Scientific.java------------------------------------------------------ 
 * Class name: Scientific------------------------------------------------ 
 */
/**
 * One of the 3 main evaluators (which represents one of the 3 modes) of the
 * {@code Calculator} class that can evaluate symbolic values and make
 * calculations like a scientific calculator. It is equipped with a
 * {@link DetailsList} objects (like all {@code CEvaluator} objects) for
 * advanced details of a calculation such as graphing, re-writing expressions,
 * displaying alternate forms, giving general info about a value (such the
 * factors of a rational number) etc.
 * <p>
 * This class implements the {@code CEvaluator} interface and as a result is
 * both a {@code Subject} and an {@code Observer}. Within the {@code Calculator}
 * class, it is registered to the {@code Input} class (as is all the evaluators
 * of the {@code Calculator} class) for receiving text inputs passed by the
 * user. It also registers the {@code Output} (as does all the evaluators of the
 * {@code Calculator} class) and the {@code DetailsList.SciDetailsList} classes,
 * for dispersing results of computations. When an input is received from an
 * {@code Input} object, it processes the input and if the expression is well
 * formed proceeds to evaluate it utilising the symja evaluator engine after
 * which it is sent to both the {@code Output} and
 * {@code DetailsList.SciDetailsList} classes for pretty-printing. Recording of
 * calculations (retrieved via {@link #getHistory()}) is only done upon calling
 * {@link #evaluate()}, but calling {@code evaluate()} may produce an error
 * (more formally an exception) if the data which should be evaluated is never
 * updated as the field for the input text is declared as {@code volatile} which
 * means that the system will never cache it's value hence it needs to be
 * constantly updated or else it may be found to be {@code null}.
 * </p>
 * <p>
 * Regarding the syntax supported by this class, the symja syntax is the one
 * used and supported. Any syntactic exception that would have resulted in the
 * symja evaluation engine will also be unsupported by this class. The following
 * are some of the syntax used by this class:
 * <ul>
 * <li>All function names should begin with an upper case letter and then lower
 * case letters for the rest of the name. This is merely a convention and not a
 * necessity.</li>
 * <li>All function arguments must be enclosed in square brackets. The sine
 * function for x for example, should be written as <code>Sin[x]</code> or an
 * exception will be thrown. This for term differentiation because grouping
 * operators already use the parenthesis for their syntax.</li>
 * <li>Identifiers for known constants such as Infinity, Pi, and E must be begin
 * with an upper case letter and then lower case letters for the rest of the
 * name. This is merely a convention and not a necessity.</li>
 * <li>White spaces are not significant.</li>
 * <li>Identifiers for free variables such as x and y may be lower case to
 * prevent being parsed as a bound variable or constant.</li>
 * <li>Assignment of variables is done using {@code =}. However reactive
 * assignment can be done using {@code :=} but this is not yet supported by this
 * class and may have unintended results.</li>
 * <li>Lists use curly braces for elements which are comma separated. the index
 * within a list can be retrieved using the <code>myList[[4]]</code> syntax</li>
 * <li>Every expression using the binary operator {@code /} is regarded as a
 * fraction. {@code Sin[x]/y} is regarded as a rational polynomial.</li>
 * <li>Precedence is controlled with parenthesis.</li>
 * <li>Complex numbers are supported but are enabled via
 * {@link Settings#isComplex()}</li>
 * <li>Bound variables must start with a {@code $} or may otherwise be
 * interpreted as a free variable.</li>
 * <li>Parts of an expression may be retrieved using the {@code expr[[index]]}
 * syntax.</li>
 * </ul>
 * The symja evaluator defines certain functions that may not always give it's
 * results in numerical form. These may have to be handled at the
 * {@code Calculator} end of things. They include the gamma, polygamma and
 * factorial functions. There may be more functions that act this way but these
 * are the tested ones that can be confirmed at this moment.
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
 * <p>
 * <b>TODO</b>: <i>Exceptions that are thrown in the symja evaluating engine are
 * not yet caught (and formatted for user-friendly display) by this class. This
 * will be done at a latter time. </i>
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @see CEvaluator
 * @see DataText
 * @see Calculator
 * @see DetailsList.SciDetailsList
 * @see History
 */
public class Scientific implements CEvaluator<DataText> {

	public static final String OPTIONS_KEY_RESULT_TEXT = "resultDisplay";
	public static final String OPTIONS_KEY_CURRENT_RESULT = "currentResult";
	public static final String OPTIONS_KEY_TRIG = "trig";
	
	//Primary modifiers
	//Group - 0
	/**
	 * The global bit modifier for the scientific calculator.
	 * Can be used with hyp and reciprocal simultaneously.
	 * This is also the second function button.
	 */
	public static final int SHIFT_MASK 				= 0x10_00_00_00;
	/**
	 * The hyperbole modifier bit. Used for the trigonometrical hyperbole functions
	 */
	public static final int HYP_MASK 				= 0x20_00_00_00;
	/**
	 * The reciprocal modifier bit. Used for activating the reciprocal input of
	 * trigonometrical functions.
	 */
	public static final int RCPRCL_MASK 			= 0x40_00_00_00;
	//Group - 1
	/**
	 * The constant access button. This turns the numeric buttons to mathematical constants
	 * such as pi, e etc. When used simultaneously with {@link #CAPS_LOCK_MASK}, the constants
	 * become their uppercase variant
	 */
	public static final int CONST_MASK 				= 0x01_00_00_00;
	/**
	 * The quick variable access button, turning all number buttons to variable buttons. When
	 * used simultaneously with {@link #CONST_MASK}, all number buttons become the upper case
	 * variant of the normal constant.
	 */
	public static final int CAPS_LOCK_MASK 			= 0x02_00_00_00;
	//Secondary modifiers
	/**
	 * Turns all number buttons to recurring digits for as long as this modifier is active.
	 */
	public static final int NUM_LOCK_MASK 			= 0x00_00_10_00;
	/**
	 * Allows all allows complex functions, numbers for inputs and results.
	 */
	public static final int IS_COMPLEX_MASK 		= 0x00_00_20_00;
	/**
	 * Allows access to expression (such as free variables x, y, z)
	 * inputs and results.
	 */
	public static final int IS_EXPR_MASK 			= 0x00_00_40_00;
	//Tertiary modifiers
	/**
	 * Any and all input types allowed
	 */
	public static final int INPUT_TYPE_ALL_MASK 	= 0x00_00_00_00;
	/**
	 * Only integers allowed
	 */
	public static final int INPUT_TYPE_INT_MASK 	= 0x00_00_01_00;
	/**
	 * Only numeric types allowed
	 */
	public static final int INPUT_TYPE_NUM_MASK 	= 0x40_00_02_00;
	/**
	 * Only symbolic types such as (x, y, z). Note this is free variables
	 * not bound ones.
	 */
	public static final int INPUT_TYPE_SYM_MASK 	= 0x40_00_03_00;
	
	/*
	 * Date: 8 Apr 2021-----------------------------------------------------------
	 * Time created: 18:44:17---------------------------------------------------
	 */
	/**
	 * A sole constructor for this class that initialises the observers list, the
	 * input object, the symja evaluation engine, the history and details list. It
	 * also registers the details list to itself. This is done so as to prevent the
	 * calculator class from directly calling this object.
	 */
	public Scientific() {
		this.keys = null;
		modifiers = 0;

		options = new HashMap<>();
		
		/*
		 * For numerical result format
		 * 0 - FIX
		 * 1 - SCI
		 * 2 - YOTTA (ENG)
		 * 3 - ZETTA (ENG)
		 * 4 - EXA (ENG)
		 * 5 - PETA (ENG)
		 * 6 - TERRA (ENG)
		 * 7 - GIGA (ENG)
		 * 8 - MEGA (ENG)
		 * 9 - KILO (ENG)
		 * 10 - MILLI (ENG)
		 * 11 - MICRO (ENG)
		 * 12 - NANO (ENG)
		 * 13 - PICO (ENG)
		 * 14 - FEMTO (ENG)
		 * 15 - ATTO (ENG)
		 * 16 - ZEPTO (ENG)
		 * 17 - YOCTO (ENG)
		 * 18 - FRAC
		 * 19 - MFRAC
		 */
		options.put(OPTIONS_KEY_RESULT_TEXT, List.of(
				"FIX", "SCI",
				"YOTTA", "ZETTA", "EXA", "PETA", "TERRA", "GIGA", "MEGA", "KILO",
				"MILLI", "MICRO", "NANO", "PICO", "FEMTO", "ATTO", "ZEPTO", "YOCTO",
				"FRAC", "MFRAC"
				));
		options.put(OPTIONS_KEY_CURRENT_RESULT, 0);//The current index of the result format list
		options.put(OPTIONS_KEY_TRIG, 0);// Such as DEG, RAD and GRAD
		
		modKeys = ScientificKeyForms.getModKeys();
		evaluationThread = new Thread(new Runnable() {
			@Override
			public void run() { doAction(); }
		}, Scientific.class.getName());

		observers = new ArrayList<>();
		input = new DataText("", "");
		output = input;
		Settings settings = Settings.defaultSetting();
		EvalEngine ev = new EvalEngine(false);
		ev.setNumericPrecision(settings.getScale());
		ev.setNumericMode(!settings.isExpression(), settings.getScale());
		evaluator = new EvalUtilities(ev, false, false);
//		tex = new TeXUtilities(ev, false);
		history = new History<>();
		details = new DetailsList.SciDetailsList(evaluator);
		register(details);
		// XXX: version 100100
		mem = new DataText("0", "0");
	}

	/*
	 * Most Recent Date: 8 Apr 2021-----------------------------------------------
	 * Most recent time created: 18:44:17--------------------------------------
	 */
	/**
	 * Updates the input source of this object with the latest value. The updated
	 * source is used as the data upon which evaluation is made. This method is
	 * meant only to be called by the {@code Subject} that registered this object.
	 * <b><i>This method must first be called before doAction() or else users may
	 * have undefined results.</i></b>
	 * 
	 * @param input the latest value to be evaluated.
	 */
	@Override
	public void inform(DataText input) {
		this.input = input;
	}

	/*
	 * Most Recent Date: 9 Aug 2021-----------------------------------------------
	 * Most recent time created: 13:46:59--------------------------------------
	 */
	/**
	 * Evaluates current expression given by {@link #inform(DataText)}. This action
	 * does not record any data into the {@code History} of this object.
	 */
	@Override
	public void doAction() {
		synchronized (this) {
			IExpr symja = evaluator.evaluate(input.getSymjaString());
//			StringWriter out = new StringWriter();
//			tex.toTeX(symja, out);
			output = DataText.fromSymja(symja.toString(), evaluator.getEvalEngine());

//			output = new DataText(out.toString(), symja.toString());
		}
	}

	/*
	 * Most Recent Date: 8 Apr 2021-----------------------------------------------
	 * Most recent time created: 18:44:17--------------------------------------
	 */
	/**
	 * Calls {@link #doAction()} and then records the input in the internal
	 * {@code History} object then returns the result of the calculation as a
	 * {@code DataText}.
	 * 
	 * @return a {@code DataText} object containing the TeX notation and the plain
	 *         symja string of the result of the evaluation.
	 */
	@Override
	public DataText evaluate() {
		doAction();
		history.record(input);
		return output;
	}

	/*
	 * Most Recent Date: 8 Apr 2021-----------------------------------------------
	 * Most recent time created: 18:44:17--------------------------------------
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
	 * Most Recent Date: 9 Aug 2021-----------------------------------------------
	 * Most recent time created: 13:54:10--------------------------------------
	 */
	/**
	 * Returns the {@code DetailsList} of this object. this details list may contain
	 * infos such as graphs, factors of a rational number, alternate forms of an
	 * expression etc.
	 * 
	 * @return a {@code DetailsList.SciDetailsList}.
	 */
	@Override
	public DetailsList getDetails() {
		return details;
	}

	/*
	 * Most Recent Date: 23 Apr 2021-----------------------------------------------
	 * Most recent time created: 13:58:36--------------------------------------
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
	 * Most Recent Date: 23 Apr 2021-----------------------------------------------
	 * Most recent time created: 13:58:36--------------------------------------
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
	 * Most Recent Date: 23 Apr 2021-----------------------------------------------
	 * Most recent time created: 13:58:36--------------------------------------
	 */
	/**
	 * Updates all the {@code Observer} objects registered to this object. In
	 * practice, only the {@code Output} and {@code DetailsList.SciDetailsList}
	 * objects will be updated.
	 */
	@Override
	public void update() {
		for (Observer<DataText> o : observers) {
			o.inform(output);
		}
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
		IExpr x = evaluator.evaluate(mem.getSymjaString() + "+" + output.getSymjaString());
		mem = DataText.fromSymja(x.toString(), evaluator.getEvalEngine());
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
		IExpr x = evaluator.evaluate(mem.getSymjaString() + "-" + output.getSymjaString());
		mem = DataText.fromSymja(x.toString(), evaluator.getEvalEngine());
		return mem;
	}
	
	@Override
	public Thread getEvaluationThread() {
		return evaluationThread;
	}
	
	public InputSegment getVariable(String var) {
		return this.var.get(var);
	}

	public void setVariable(String var, InputSegment s) {
		this.var.put(var, s);
	}
	
	@Override
	public void logMessage(int logType, String msg) {
		logs.get(logType).add(msg);
	}

	@Override
	public List<String> getLogMessages(int logType) {
		return Collections.unmodifiableList(logs.get(logType));
	}

	@Override
	public KeyForm keyFor(int keyCode) {
		return keys.getKey(keyCode).getKeyForm(modifiers);
	}
	
	@Override
	public KeyForm modifierKeyFor(int modKeyCode) {
		return modKeys.getKey(modKeyCode).getKeyForm(0);
	}
	
	@Override
	public boolean setOption(String optionId, Object newVal) {
		Object rv = options.put(optionId, newVal);// Return value
		return rv != null;
	}

	@Override
	public Object getOption(String optionId) {
		Object rv = options.get(optionId);// Return value
		return rv;
	}
	
	/*
	 * Most Recent Date: 13 Aug 2022-----------------------------------------------
	 * Most recent time created: 12:03:37--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @return
	 */
	@Override
	public int getModifiers() {
		return modifiers;
	}

	/*
	 * Most Recent Date: 13 Aug 2022-----------------------------------------------
	 * Most recent time created: 12:03:37--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @param val
	 */
	@Override
	public void setModifiers(int modifiers) {
		this.modifiers = modifiers;
	}

	/**
	 * The internal calculations history holder.
	 */
	private final History<DataText> history;
	/**
	 * The symja evaluation engine that evaluates expressions
	 */
	private final EvalUtilities evaluator;
	/**
	 * Converts expressions to TeX strings
	 */
//	private TeXUtilities tex;
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

	/**
	 * cardinal keys are SHIFT, HYP, RCPRCL, trig, caps_lock,
	 * num_lock, isComplex, isExpr, resultType
	 */
	private final Keys modKeys;

	private final Keys keys;
	
	private final Map<String, Object> options;

	/**
	 * Message logs for errors, warnings and infos 
	 */
	private final Map<Integer, List<String>> logs =
			Map.of(LOG_ERROR, new ArrayList<String>(), LOG_WARNING, new ArrayList<String>(),
					LOG_INFO, new ArrayList<String>());
	
	/**
	 * Maps a list of constants to a certain field.
	 * Each constant is an InputSegment which enables
	 * users to directly grab it from the list into
	 * the input.
	 * for example, mathematics constants pi, e, gamma
	 * etc can be stored with an integer indicating
	 * math and can be withdrawn with that integer.
	 */
	private final Map<Integer, List<InputSegment>> constants =
			new HashMap<>();
	
	/**
	 * A list of variables names mapped to their values.
	 * The keys may be a single letter variable
	 * or a function. Their values may be an expression. 
	 */
	private final Map<String, InputSegment> var =
			new HashMap<>();
	
	/**
	 * For the undo manager. A queue that stores the
	 * last type action of the user
	 */
	private final Queue<InputSegment> undoList =
			new ArrayDeque<>();

	/**
	 * For the redo manager. A queue that stores the
	 * last undo action of the user
	 */
	private final Queue<InputSegment> redoList =
			new ArrayDeque<>();
	
	/**
	 * The Scientific calculator modifier setting
	 */
	private int modifiers;
	
	private final Thread evaluationThread;
}
