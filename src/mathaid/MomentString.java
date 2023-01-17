/**
 * 
 */
package mathaid;

import java.util.Objects;

/*
 * Date: 17 Sep 2022----------------------------------------------------------- 
 * Time created: 19:52:17---------------------------------------------------  
 * Package: mathaid------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: MomentString.java------------------------------------------------------ 
 * Class name: MomentString------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public class MomentString implements Moment {
	
	{
		moment = System.nanoTime();
//		moment = System.currentTimeMillis();
	}
	
	public MomentString(String s) {
		string = s;
	}
	
	/*
	 * Most Recent Date: 17 Sep 2022-----------------------------------------------
	 * Most recent time created: 19:52:17--------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * @return
	 */
	@Override
	public long getMoment() {
		return moment;
	}
	
	@Override
	public int hashCode() {
		return (string.hashCode() + Objects.hashCode(moment)) / 2;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof MomentString) {
			MomentString ms = (MomentString) o;
			return /* compareTo(ms) == 0 && */string.equals(ms.string);
		}
		return false;
	}
	
	private final long moment;
	public final String string;
}
