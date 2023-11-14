package mathaid.calculator.base.evaluator;

/*
 * Date: 10 Nov 2023 -----------------------------------------------------------
 * Time created: 08:05:20 ---------------------------------------------------
 * Package: mathaid.calculator.base.evaluator ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: Input.java ------------------------------------------------------
 * Class name: Input ------------------------------------------------
 */
/**
 * Represents values sent from one part of a calculator (<code>Publisher</code>)
 * to another part of the calculator (<code>Subscriber</code>).
 * <p>
 * It consists of a text (which is actually a
 * {@link mathaid.calculator.base.typeset.Segment Segment}) representing the
 * current user input on the calculator screen, and a command representing any
 * addition processing to be carried out.
 * <p>
 * {@code Input} objects can be either be pure commands (whereby
 * {@link #getText} returns {@code null}) or pure text (whereby
 * {@link #getCommand} returns {@code null}). They can also be neither but not
 * both.
 * <p>
 * Pure commands are <code>Input</code> objects that tell parts of a calculator
 * to do something but not display it's result to the user.
 * <p>
 * Pure texts are <code>Input</code> objects that send user input (usually
 * formulae or expressions) to pats of the calculator for further processing or
 * outputting.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface Input {

	/*
	 * Date: 10 Nov 2023 -----------------------------------------------------------
	 * Time created: 08:48:35 ---------------------------------------------------
	 */
	/**
	 * Returns a unique id for this input. This id is only unique from a single
	 * ({@code Publisher}) instance. If the program was shutdown, then this value
	 * will be reset.
	 * 
	 * @return the id of this {@codeInput}
	 */
	long getId();

	/*
	 * Date: 10 Nov 2023 -----------------------------------------------------------
	 * Time created: 08:08:18 ---------------------------------------------------
	 */
	/**
	 * Gets the text of this input as a <code>Segment</code>. This text is an object
	 * with a render string and an evaluation string.
	 * 
	 * @return the text input part of this object. Will return {@code null} if, and
	 *         only if {@code this} is a pure command.
	 */
	mathaid.calculator.base.typeset.LinkedSegment getText();

	/*
	 * Date: 10 Nov 2023 -----------------------------------------------------------
	 * Time created: 08:03:32 ---------------------------------------------------
	 */
	/**
	 * Returns a string that is an action to be performed and the significance of
	 * which depends on the receiving subscriber. In the context of the calculator
	 * app, this value will be a numerical string specifying the index of the
	 * evaluation engine to be used for processing the {@link #getText text}.
	 * 
	 * @return a string representing a command, directive or action. Will return
	 *         {@code null} if, and only if {@code this} is a pure text.
	 */
	String getCommand();
}