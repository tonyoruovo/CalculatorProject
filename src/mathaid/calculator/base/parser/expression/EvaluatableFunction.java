/**
 * 
 */
package mathaid.calculator.base.parser.expression;

import java.util.List;

import mathaid.NullException;

/*
 * Date: 20 Jun 2021----------------------------------------------------------- 
 * Time created: 15:43:20---------------------------------------------------  
 * Package: mathaid.calculator.base.parser.expression------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: EvaluatableFunction.java------------------------------------------------------ 
 * Class name: EvaluatableFunction------------------------------------------------ 
 */
/**
 * An {@link EvaluatableName} evaluates expression such as {@code sin(x)}.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public abstract class EvaluatableFunction extends EvaluatableName {

	/*
	 * Date: 20 Jun 2021-----------------------------------------------------------
	 * Time created: 15:43:20---------------------------------------------------
	 */
	/**
	 * Constructs a {@code EvaluatableFunction} from a name and a list of arguments.
	 * This list may be an empty list to specify a no-arg function but may never be
	 * null or an exception will be thrown.
	 * 
	 * @param functionName the head of this function.
	 * @param args         the list of argument which may be an empty list for
	 *                     no-arg functions.
	 * @throws RuntimeException specifically a {@code NullPointerException} if args
	 *                          or functionName is null.
	 */
	public EvaluatableFunction(EvaluatableExpression functionName, List<EvaluatableExpression> args) {
		super(functionName.name());
		this.name = functionName;
		if (args == null)
			new NullException(List.class);
		this.args = args;
	}

	/*
	 * Date: 29 Jul 2021-----------------------------------------------------------
	 * Time created: 20:04:18--------------------------------------------
	 */
	/**
	 * The head of this function which may represent it's name.
	 * 
	 * @return an {@code EvaluatableExpression} that is the head of this object.
	 */
	public EvaluatableExpression functionName() {
		return name;
	}

	/*
	 * Date: 29 Jul 2021-----------------------------------------------------------
	 * Time created: 20:05:57--------------------------------------------
	 */
	/**
	 * A {@code List} of arguments of this function. May return a 0 length list for
	 * no-argument functions.
	 * 
	 * @return a list of the arguments of this function.
	 */
	public List<EvaluatableExpression> args() {
		return args;
	}

	/**
	 * The head of this function
	 */
	private final EvaluatableExpression name;
	/**
	 * The list of argument
	 */
	private final List<EvaluatableExpression> args;
}
