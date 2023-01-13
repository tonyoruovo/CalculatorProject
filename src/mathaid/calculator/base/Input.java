/**
 * 
 */
package mathaid.calculator.base;

import java.util.ArrayList;
import java.util.List;

import mathaid.calculator.base.format.tex.input.ForwardMarker;
import mathaid.calculator.base.format.tex.input.InputBuilder;
import mathaid.calculator.base.format.tex.input.InputSegment;
import mathaid.calculator.base.parser.CEvaluator;
import mathaid.designpattern.Observer;
import mathaid.designpattern.Subject;

/*
 * Date: 23 Apr 2021----------------------------------------------------------- 
 * Time created: 14:05:43---------------------------------------------------  
 * Package: mathaid.calculator.base------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: Input.java------------------------------------------------------ 
 * Class name: Input------------------------------------------------ 
 */
/**
 * The input of the calculator class.
 * <p>
 * It's functions in the calculator are
 * <ul>
 * <li>Register {@code CEvaluator} objects.</li>
 * <li>Parse user key events on the calculator interface into valid expression
 * and mutating such expression for displays and evaluation.</li>
 * <li>Update the {@code CEvaluator} objects about the parsed expressions.</li>
 * <li>Catch and throw exceptions on a restricted level on expressions entered
 * by the user.</li>
 * </ul>
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class Input /* extends SubmissionPublisher<String> */ implements Subject<DataText> {
	/*
	 * Date: 23 Apr 2021-----------------------------------------------------------
	 * Time created: 14:12:38---------------------------------------------------
	 */
	/**
	 * Sole constructor for creating an {@code Input} object.
	 */
	public Input() {
		list = new ArrayList<>();
		input = new DataText("0", "0");
	}

	/*
	 * Most Recent Date: 23 Apr 2021-----------------------------------------------
	 * Most recent time created: 14:05:43--------------------------------------
	 */
	/**
	 * Registers the given {@code Observer}. In practice, this registers the current
	 * {@code CEvaluator}.
	 * 
	 * @param o the {@code Observer} to be registered. This will throw a {@code ClassCastException} if this is not
	 * a {@code CEvaluator} object.
	 */
	@Override
	public void register(Observer<DataText> o) {
		CEvaluator<DataText> c = (CEvaluator<DataText>) o;
		list.add(c);
	}
	
	public void executeModifier(int keyCode) {
		for(CEvaluator<DataText> c : list)
			c.modifierKeyFor(keyCode).execute(c, this);
	}
	
	public void execute(int keyCode) {
		for(CEvaluator<DataText> c : list)
			c.keyFor(keyCode).execute(c, this);
	}
	
	public List<Integer> getPosition() {
		return pos;
	}

	/*
	 * Most Recent Date: 23 Apr 2021-----------------------------------------------
	 * Most recent time created: 14:05:43--------------------------------------
	 */
	/**
	 * Registers the given {@code Observer}. In practice, this unregisters the
	 * current {@code CEvaluator}.
	 * 
	 * @param o the {@code Observer} to be registered.
	 */
	@Override
	public void unRegister(Observer<DataText> o) {
		list.remove(o);
	}

	/*
	 * Most Recent Date: 23 Apr 2021-----------------------------------------------
	 * Most recent time created: 14:05:43--------------------------------------
	 */
	/**
	 * Updates all the {@code Observer} objects registered to this object. In
	 * practice, only the current {@code CEvaluator} will be updated.
	 */
	@Override
	public void update() {
		for (Observer<DataText> o : list) {
			o.inform(input);
		}
	}

	/*
	 * Date: 12 Aug 2021-----------------------------------------------------------
	 * Time created: 15:03:41--------------------------------------------
	 */
	/**
	 * Accepts and parses the string input for display and evaluation.
	 * 
	 * @param input the string to be parsed.
	 */
	public synchronized void input(String input) {
		this.input = new DataText(input, input);
	}
	
	public void clearInput() {
		inputBuilder.deleteAll();
	}
	
	public List<String> getLog() {
		return list.get(0).getLogMessages(logType);
	}
	
	public void setLog(int logType) {
		this.logType = logType;
	}
	
	public void backspace() {
		if(!inputBuilder.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<Integer> pos = (List<Integer>) inputBuilder.getCaret().getOption(ForwardMarker.LAST_MARK_POSITION);
			inputBuilder.delete(pos);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void delete() {
		if(!inputBuilder.isEmpty()) {
			List<Integer> pos = (List<Integer>) inputBuilder.getCaret().getOption(ForwardMarker.LAST_MARK_POSITION);
			List<Integer> cPos = (List<Integer>) inputBuilder.getCaret().getOption(ForwardMarker.CURRENT_MAX_POSITION);
			inputBuilder.delete(pos);
		}
	}
	
	public InputBuilder getInputBuilder() {
		return inputBuilder;
	}

	/**
	 * The current raw input of the calculator
	 */
	volatile DataText input;
	/**
	 * The {@code List} of {@code Observer} objects registered to this object via
	 * {@link #register(Observer)}.
	 */
	private final List<CEvaluator<DataText>> list;
//	private CEvaluator currentCalculator;
	
	private final InputBuilder inputBuilder = new InputBuilder();
	private final List<Integer> pos = new ArrayList<>(150);

	/**
	 * The type of log currently active. Values include all
	 * constant whose in {@code CEvaluator} class whose name
	 * begin with {@code LOG_}
	 */
	private int logType;

}
