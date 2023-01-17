/**
 * 
 */
package mathaid.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mathaid.calculator.base.evaluator.Calculator;
import mathaid.calculator.base.gui.GUIComponent;
import mathaid.calculator.base.gui.Typesetable;
import mathaid.calculator.base.typeset.BasicFormatter;
import mathaid.calculator.base.typeset.Formatter;
import mathaid.calculator.base.typeset.LinkedSegment;
import mathaid.calculator.base.typeset.Log;
import mathaid.calculator.base.typeset.NumberAdapter;
import mathaid.calculator.base.typeset.SegmentBuilder;

/*
 * Date: 20 Sep 2022----------------------------------------------------------- 
 * Time created: 16:55:52---------------------------------------------------  
 * Package: mathaid.calculator------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: CalculatorManager.java------------------------------------------------------ 
 * Class name: CalculatorManager------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class CalculatorManager<T extends GUIComponent<T>, F> {

	private class InputDisplay implements Runnable {
		@Override
		public void run() {
			input.toSegment().format(inputTypesetter, middleware, position);
			inputTypesetter.typeset();
			calculatorThread.start();
		}
	}

	private class CThread implements Runnable {
		@Override
		public void run() {
			input.toSegment().toString(parser, logger, errorPosition);
			ans.deleteAll().append(current().evaluate(parser.toString()));
			outputDisplayThread.start();
		}
	}

	private class Details implements Runnable {
		@Override
		public void run() {
//			StringBuilder s = new StringBuilder();
//			ans.toSegment().toString(s, null, new ArrayList<>(Arrays.asList(-1)));
//			current().getDetails().setSource(new String[] { "Expression", s.toString() });
			new Thread(current().getDetails()).start();
		}
	}

	private class OutputDisplay implements Runnable {
		@Override
		public void run() {
			ans.toSegment().format(outputTypesetter, Formatter.empty(), new ArrayList<>(Arrays.asList(-1)));
			outputTypesetter.typeset();
			new Thread(new Details()).start();
		}
	}

	public CalculatorManager(List<Calculator<T, F>> calculators, T mainComponent, Typesetable<T> input,
			Typesetable<T> output) {
		this.c = Collections.unmodifiableList(calculators);
		mainComponent.configure(null);
		this.input = new SegmentBuilder();
		this.parser = new NumberAdapter();
		this.logger = new Log();
		this.ans = new SegmentBuilder();
		this.position = new ArrayList<>(Arrays.asList(-1));
		this.errorPosition = new ArrayList<>(Arrays.asList(-1));

//		this.caret = new ForwardRunningMarker();
//		this.middleware = new HashMap<>();
		this.middleware = new BasicFormatter();
//		middleware.put(LinkedSegment.CARET, caret);
//		middleware.put(LinkedSegment.CSSID, LinkedSegment.cssId());

//		middleware.put(LinkedSegment.STYLE, LinkedSegment.style(LinkedSegment.defaultStyles()));
//		middleware.put(LinkedSegment.ERROR, new ErrorMarker());

		this.inputTypesetter = input;
		this.outputTypesetter = output;
	}

	public void start() {
		inputDisplayThread = new Thread(new InputDisplay());
		calculatorThread = new Thread(new CThread());
		outputDisplayThread = new Thread(new OutputDisplay());

		inputDisplayThread.start();// starts all threads
	}

	public void escapeEvaluation() {
		if (calculatorThread.isAlive())
			calculatorThread.interrupt();
	}

	public Calculator<T, F> current() {
		return c.get(index);
	}

	public Calculator<T, F> next() {
		if (++index >= c.size())
			index = 0;
		return c.get(index);
	}

	public void input(LinkedSegment s) {
		switch (middleware.getCaretMarker().getInputMode()) {
		case APPEND:
			input.append(s);
			break;
		case OVERWRITE: {
//			List<Integer> ref = new ArrayList<>(position);
			try {
//				input.segmentAt(ref);
				input.replace(position, s);
			} catch (@SuppressWarnings("unused") IndexOutOfBoundsException e) {
				input.append(s);
			}

		}
			break;
		case PREPEND:
			input.prepend(s);
			break;
		case INSERT:
		default:
			input.insert(position, s);
		}
	}

	public SegmentBuilder getInput() {
		return input;
	}

	public List<Integer> getPosition() {
		return position;
	}

	public SegmentBuilder getAnswer() {
		return ans;
	}

//	public ForwardRunningMarker getCaretFormatter() {
//		return caret;
//	}

	private final List<Calculator<T, F>> c;
	private int index;
	private Thread calculatorThread;
	private Thread inputDisplayThread;
	private Thread outputDisplayThread;

	private final SegmentBuilder input;
	private final SegmentBuilder ans;
	/*
	 * TODO: make sure to change this so as to prevent 1.8r5e-2 to be parsed into
	 * the symja parser. The best way to go about it is to create an appendable that
	 * can convert numbers such as the one above in to rational function i.e
	 * 1.8r5e-2 => Rational[167, 9000]
	 */
	private final Appendable parser;
	private final Log logger;
	private final List<Integer> position;
	private final List<Integer> errorPosition;
//	private final Map<Integer, FormatAction> middleware;
	private final Formatter middleware;
//	private final ForwardRunningMarker caret;

	private final Typesetable<T> inputTypesetter;
	private final Typesetable<T> outputTypesetter;
}
