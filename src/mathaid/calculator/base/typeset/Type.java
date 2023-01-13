/**
 * 
 */
package mathaid.calculator.base.typeset;
import java.util.NoSuchElementException;

public interface Type extends Iterable<Type> {

	Type getChild();

	default Type get(int code) throws NoSuchElementException {
		if(getCode() == code)
			return this;
		else if(getChild() != null)
			return getChild().get(code);
		throw new NoSuchElementException();
	}
	
	int getCode();

	@Override
	String toString();
	@Override
	boolean equals(Object obj);
	@Override
	int hashCode();
}