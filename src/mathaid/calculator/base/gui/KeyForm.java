/**
 * 
 */
package mathaid.calculator.base.gui;

import mathaid.calculator.base.DataText;
import mathaid.calculator.base.Input;
import mathaid.calculator.base.parser.CEvaluator;

/*
 * Date: 13 Jul 2022----------------------------------------------------------- 
 * Time created: 15:51:49---------------------------------------------------  
 * Package: mathaid.calculator.base.model------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: KeyForm.java------------------------------------------------------ 
 * Class name: KeyForm------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public abstract class KeyForm {
	
	public abstract String getFormat();
	
	public abstract String getString();
	
	public abstract boolean execute(CEvaluator<DataText> c, Input i);

	@Override
	public boolean equals(Object o) {
		if(o instanceof KeyForm) {
			KeyForm f = (KeyForm) o;
			return getString().equals(f.getString())
					&& getFormat().equals(f.getFormat());
		}
		return false;
	}
	
	public static final KeyForm EMPTY_KEYFORM = new KeyForm() {
		@Override
		public String getString() {
			return "";
		}
		@Override
		public String getFormat() {
			return "";
		}
		@Override
		public boolean execute(CEvaluator<DataText> c, Input i) {
			return false;
		}
	};

}
