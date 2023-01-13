/**
 * 
 */
package mathaid.calculator.base.typeset;

public interface WebColour {
	int getOpacity();
	int getOpaqueColour();

	default boolean isSameColour(WebColour c) {
		return getOpacity() == c.getOpacity() && getOpaqueColour() == c.getOpaqueColour();
	}

	@Override
	int hashCode() ;

	@Override
	boolean equals(Object o);

	@Override
	String toString();
}