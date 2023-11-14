/*
 * Date: 10 Nov 2023 -----------------------------------------------------------
 * Time created: 07:11:18 ---------------------------------------------------
 */
/**
 * 
 */
package mathaid.calculator.base.evaluator;

import java.util.concurrent.Flow;

/*
 * Date: 10 Nov 2023 -----------------------------------------------------------
 * Time created: 07:11:18 ---------------------------------------------------
 * Package: mathaid.calculator.base.evaluator ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: Computer.java ------------------------------------------------------
 * Class name: Computer ------------------------------------------------
 */
/**
 * The part of the calculator that evaluates user input.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface InputProcessor extends Flow.Processor<Input, Result.Processed> {

	/*
	 * Date: 10 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:27:56 ---------------------------------------------------
	 */
	/**
	 * Does one of 2 things depending on the state of the given input:
	 * <ol>
	 * <li>Parses the given {@code Input}, evaluates the text contained therein using the engine specified by the command field and
	 * disseminates the results to all registered subscribers. If there is a syntactic exception (malformed expression text) then this method throws it.</li>
	 * <li>Executes a command on the calculator by calling {@link #processCommand}
	 * such as switch evaluator</li>
	 * </ol>
	 * (1) will be done if the argument is a <strong>not</strong> a <em>pure
	 * command</em>.<br>
	 * (2) will only be done if the argument is a <em>pure command</em>.
	 * <p>
	 * {@inheritDoc}
	 * 
	 * @param input the value given by an {@code InputPublisher}.
	 */
	@Override
	void onNext(Input input); //{
//		final boolean isCommand = input.getText() == null;
//		if (!isCommand) {
//			StringBuilder sb = new StringBuilder();
//			List<Integer> l = new java.util.ArrayList<>();
//			LinkedSegment text = input.getText();
//			text.toString(sb, null, l);
//			LinkedSegment val = getCurrent().evaluate(sb.toString());
//			getSubscribers().parallelStream().forEach(x -> {
//				try {
//					x.onNext(val);
//				} catch (Throwable t) {
//					x.onError(t);
//					t.printStackTrace();
//				}
//			});
//			onNext(new Processed(val, input.getCommand(), null));
//		} else
//			processCommand(input.getCommand());
//	}

	/*
	 * Date: 10 Nov 2023 -----------------------------------------------------------
	 * Time created: 09:14:58 ---------------------------------------------------
	 */
	/**
	 * Sets the internal subscription and sends a request to the publisher using
	 * {@link Long#MAX_VALUE} as the argument.
	 * <p>
	 * {@inheritDoc}
	 * 
	 * @param subscription {@inheritDoc}
	 */
	@Override
	void onSubscribe(Flow.Subscription subscription);

	/*
	 * Date: 10 Nov 2023 -----------------------------------------------------------
	 * Time created: 09:29:38 ---------------------------------------------------
	 */
	/**
	 * Releases all resources being used by this processor and initiates a shutdown.
	 * <p>
	 * This method will only be called if the user initiates a shutdown of the
	 * calculator from the publisher.
	 * <p>
	 * {@inheritDoc}
	 */
	@Override
	void onComplete();

	/*
	 * Date: 10 Nov 2023 -----------------------------------------------------------
	 * Time created: 09:32:43 ---------------------------------------------------
	 */
	/**
	 * Logs the given {@link Throwable} and sends an error signal to the subscribers
	 * so that the output (for example) may display an error message. If the
	 * argument is an instance of {@link Error}, then this processor will be
	 * initiated it's shutdown (even though the calculator does not shutdown). This
	 * will doubtless cause undefined behaviour, so it is recommended that the
	 * entire program be shutdown as well.
	 * <p>
	 * {@inheritDoc}
	 * 
	 * @param throwable {@inheritDoc}
	 */
	@Override
	void onError(Throwable throwable);

	/*
	 * Date: 10 Nov 2023 -----------------------------------------------------------
	 * Time created: 08:56:08 ---------------------------------------------------
	 */
	/**
	 * Processes the argument string -- which is expected to be a valid command --
	 * into a command and executes the corresponding action for the given command.
	 * 
	 * @param command the string to be processed.
	 */
	void processCommand(String command);

	/*
	 * Date: 10 Nov 2023 -----------------------------------------------------------
	 * Time created: 08:36:46 ---------------------------------------------------
	 */
	/**
	 * Returns all the subscribers for this processor.
	 * 
	 * @return an unmodifiable list view of all subs.
	 */
//	List<Flow.Subscriber<String>> getSubscribers();

	/*
	 * Date: 10 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:23:26 ---------------------------------------------------
	 */
	/**
	 * Gets the current evaluation engine. This is the engine that is being used
	 * when {@link #evaluate(String)} is called.
	 * 
	 * @return the current evaluation engine.
	 */
//	Evaluator<LinkedSegment> getCurrent();

	/*
	 * Date: 10 Nov 2023 -----------------------------------------------------------
	 * Time created: 07:25:17 ---------------------------------------------------
	 */
	/**
	 * Returns the total number of engines available to this object.
	 * 
	 * @return the total number of engines available.
	 */
	int getEngineLength();

} 
