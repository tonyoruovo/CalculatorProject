/**
 * 
 */
package mathaid.calculator.base.parser.expression;

import java.util.List;

import mathaid.NullException;

/*
 * Date: 5 May 2021----------------------------------------------------------- 
 * Time created: 19:55:27---------------------------------------------------  
 * Package: com.etineakpopha.parser.expression------------------------------------------------ 
 * Project: RemoteWork------------------------------------------------ 
 * File: FunctionExpression.java------------------------------------------------------ 
 * Class name: FunctionExpression------------------------------------------------ 
 */
/**
 * A {@code Name} expression is parsed from expression such as {@code sin(x)}.
 * It consists of a {@code Name} for the function name and a list of
 * {@code Name} for the function arguments.
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public class FunctionExpression extends Name {

	/**
	 * The head of the function
	 */
	NameExpression name;
	/**
	 * The argument list. This is an empty list for no-arg functions but never null.
	 */
	List<NameExpression> args;

	/*
	 * Date: 29 Jul 2021-----------------------------------------------------------
	 * Time created: 14:22:39---------------------------------------------------
	 */
	/**
	 * Initialises a {@code FunctionExpression} using a given name and list of
	 * arguments
	 * 
	 * @param name the head of this function.
	 * @param args the list of the function's argument. No-argument functions may
	 *             pass in an empty list but this value may not be null.
	 * @throws RuntimeException specifically a {@code NullPointerException} if args
	 *                          or aame is null.
	 */
	public FunctionExpression(NameExpression name, List<NameExpression> args) {
		super(name.name());
		this.name = name;
		if (args == null)
			new NullException(List.class);
		this.args = args;
	}

	/*
	 * Most Recent Date: 29 Jul 2021-----------------------------------------------
	 * Most recent time created: 20:17:31--------------------------------------
	 */
	/**
	 * Appends this {@code FunctionExpression} object string representation to the
	 * string builder.
	 * 
	 * @param sb {@inheritDoc}
	 */
	@Override
	public void toString(StringBuilder sb) {
		name.toString(sb);
		sb.append('(');
		for (int i = 0; i < args.size(); i++) {
			args.get(i).toString(sb);
			if (i < args.size() - 1)
				sb.append(", ");
		}
		sb.append(')');
	}

	/*
	 * Most Recent Date: 29 Jul 2021-----------------------------------------------
	 * Most recent time created: 20:19:25--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @param o {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof FunctionExpression))
			return false;
		FunctionExpression obj = (FunctionExpression) o;
		return name.equals(obj.name) && args.equals(obj.args);
	}

	/*
	 * Most Recent Date: 29 Jul 2021-----------------------------------------------
	 * Most recent time created: 20:19:25--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return name.hashCode() ^ args.hashCode();
	}

}
