/**
 * 
 */
package mathaid.calculator.base.gui;

/*
 * Date: 27 Aug 2022----------------------------------------------------------- 
 * Time created: 09:39:34---------------------------------------------------  
 * Package: typeset------------------------------------------------ 
 * Project: InputTestCode------------------------------------------------ 
 * File: GUIComponent.java------------------------------------------------------ 
 * Class name: GUIComponent------------------------------------------------ 
 */
/**
 * Any object that can be graphically expressed on the monitor screen. This may
 * include swing, AWT, JavaFX objects, android views etc.
 * <p>
 * The purpose is to configure this object in-sync with the calculator.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 * @param <T> a type of gui that is most likely the top-most container where the
 *            main graphical interface of the calculator is located
 */
public interface GUIComponent<T> {

	/*
	 * Date: 27 Aug 2022-----------------------------------------------------------
	 * Time created: 09:54:43--------------------------------------------
	 */
	/**
	 * Configures {@code this} using
	 * 
	 * @param topMostContainer
	 */
	void configure(T topMostContainer);
}
