/**
 * 
 */
package mathaid.calculator.base.gui;

import static mathaid.calculator.base.Scientific.CAPS_LOCK_MASK;
import static mathaid.calculator.base.Scientific.CONST_MASK;
import static mathaid.calculator.base.Scientific.HYP_MASK;
import static mathaid.calculator.base.Scientific.IS_COMPLEX_MASK;
import static mathaid.calculator.base.Scientific.IS_EXPR_MASK;
import static mathaid.calculator.base.Scientific.NUM_LOCK_MASK;
import static mathaid.calculator.base.Scientific.OPTIONS_KEY_CURRENT_RESULT;
import static mathaid.calculator.base.Scientific.OPTIONS_KEY_RESULT_TEXT;
import static mathaid.calculator.base.Scientific.OPTIONS_KEY_TRIG;
import static mathaid.calculator.base.Scientific.RCPRCL_MASK;
import static mathaid.calculator.base.Scientific.SHIFT_MASK;
import static mathaid.calculator.base.format.tex.input.ForwardMarker.*;

import java.util.ArrayList;
import java.util.List;

import mathaid.calculator.base.DataText;
import mathaid.calculator.base.Input;
import mathaid.calculator.base.format.tex.input.ForwardMarker;
import mathaid.calculator.base.format.tex.input.InputBuilder;
import mathaid.calculator.base.parser.CEvaluator;

/*
 * Date: 31 Jul 2022----------------------------------------------------------- 
 * Time created: 03:11:59---------------------------------------------------  
 * Package: mathaid.calculator.base.model------------------------------------------------ 
 * Project: CalculatorProject------------------------------------------------ 
 * File: ScientificKeyForms.java------------------------------------------------------ 
 * Class name: ScientificKeyForms------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public final class ScientificKeyForms {

	// Modifier keys

	static KeyForm shift() {
		return new KeyForm() {
			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}

			@Override
			public String getString() {
				return "shift";
			}

			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				c.setModifiers(c.getModifiers() ^ SHIFT_MASK);
				return true;
			}
		};
	}

	static KeyForm hyp() {
		return new KeyForm() {
			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}

			@Override
			public String getString() {
				return "hyp";
			}

			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				c.setModifiers(c.getModifiers() ^ HYP_MASK);
				return true;
			}
		};
	}

	static KeyForm reciprocal() {
		return new KeyForm() {
			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}

			@Override
			public String getString() {
				return "reciprocal";
			}

			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				c.setModifiers(c.getModifiers() ^ RCPRCL_MASK);
				return true;
			}
		};
	}

	static KeyForm trigonometry() {
		return new KeyForm() {
			String val;

			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}

			@Override
			public String getString() {
				return val;
			}

			private String trig(int trig) {
				switch (trig) {
				case 0:
					return "d";
				case 1:
					return "r";
				default:
					return "g";
				}
			}

			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				int trig = (Integer) c.getOption(OPTIONS_KEY_TRIG);
				++trig;
				if (trig > 2)
					trig = 0;
				c.setOption(OPTIONS_KEY_TRIG, trig);
				val = trig(trig);
				return true;
			}
		};
	}

	static KeyForm expression() {
		return new KeyForm() {
			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}

			@Override
			public String getString() {
				return "e";
			}

			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				c.setModifiers(c.getModifiers() ^ IS_EXPR_MASK);
				return true;
			}
		};
	}

	static KeyForm complex() {
		return new KeyForm() {

			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}

			@Override
			public String getString() {
				return "c";
			}

			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				c.setModifiers(c.getModifiers() ^ IS_COMPLEX_MASK);
				return true;
			}
		};
	}

	static KeyForm result() {
		return new KeyForm() {
			int res;
			String disp;

			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}

			@Override
			public String getString() {
				return disp;
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				int add = ((Integer) c.getOption(OPTIONS_KEY_CURRENT_RESULT)) + 1;
				List<String> displayList = (List<String>) c.getOption(OPTIONS_KEY_RESULT_TEXT);
				res = add > displayList.size() - 1 ? 0 : add;
				disp = displayList.get(res);
				c.setOption(OPTIONS_KEY_CURRENT_RESULT, res);
				return true;
			}
		};
	}

	static KeyForm capsLock() {
		return new KeyForm() {

			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}

			@Override
			public String getString() {
				return "a";
			}

			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				c.setModifiers(c.getModifiers() ^ CAPS_LOCK_MASK);
				return true;
			}
		};
	}

	static KeyForm numLock() {
		return new KeyForm() {

			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}

			@Override
			public String getString() {
				return "9";
			}

			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				c.setModifiers(c.getModifiers() ^ NUM_LOCK_MASK);
				return true;
			}
		};
	}

	private static KeyForm getKeyForm(int index) {
		switch (index) {
		case 0:
			return shift();
		case 1:
			return hyp();
		case 2:
			return reciprocal();
		case 3:
			return trigonometry();
		case 4:
			return capsLock();
		case 5:
			return numLock();
		case 6:
			return expression();
		case 7:
			return complex();
		case 8:
			return result();
		default:
		}
		return null;
	}

	public static Keys getModKeys() {
		Keys keys = Keys.build(9, 0, false);
		for (int j = 0; j < keys.length(); j++) {
			keys.getKey(j).setKeyForm(0, getKeyForm(j));
		}
		return keys;
	}

	// Control keys
	// LEVEL -- 1, 1, 1
	static KeyForm ac() {
		return new KeyForm() {

			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}

			@Override
			public String getString() {
				return "ac";
			}

			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				i.getInputBuilder().deleteAll();
				return true;
			}
		};
	}

	// LEVEL -- 1, 1, 2
	static KeyForm err() {
		return new KeyForm() {

			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}

			@Override
			public String getString() {
				return "err";
			}

			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				i.setLog(CEvaluator.LOG_ERROR);
				return true;
			}
		};
	}

	// LEVEL -- 1, 2
	// TODO: needs a hold (or long press) action on the gui
	static KeyForm constant() {
		return new KeyForm() {

			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}

			@Override
			public String getString() {
				return "const";
			}

			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				c.setModifiers(c.getModifiers() ^ CONST_MASK);
				return true;
			}
		};
	}

	// LEVEL -- 1, 3
	static KeyForm up() {
		return new KeyForm() {

			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}

			@Override
			public String getString() {
				return "const";
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				InputBuilder ib = i.getInputBuilder();
				List<Integer> position = (List<Integer>) ib.getCaret().getOption(LAST_MARK_POSITION);
				try {
					position.add(0);// for the tree node
					position.add(0);// for beginning of the linked-node
					ib.setFocus(true, position);
				} catch (IndexOutOfBoundsException e) {
					position.remove(0);// for the tree node
					position.remove(0);// for beginning of the linked-node
				}
				return true;
			}
		};
	}

	// LEVEL -- 1, 4
	// TODO: Does nothing for now
	static KeyForm var() {
		return new KeyForm() {

			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}

			@Override
			public String getString() {
				return "var";
			}

			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
//				InputSegment is = i.getInputSegment();
//				if(is != null)
//					c.setVariable(var, segment);
				return true;
			}
		};
	}

	// LEVEL -- 1, 5, 1
	static KeyForm backspace() {
		return new KeyForm() {
			
			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}
			
			@Override
			public String getString() {
				return "bkspc";
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				InputBuilder ib = i.getInputBuilder();
				ArrayList<Integer> position = (ArrayList<Integer>) ib.getCaret().getOption(LAST_MARK_POSITION);
				ArrayList<Integer> unmodifiedList = (ArrayList<Integer>) position.clone();
				int lastIndex = position.get(position.size() - 1);
				try {
					while(lastIndex == 0) {
						position.remove(position.size() - 1);
						lastIndex = position.get(position.size() - 1);
					}
					position.set(position.size() - 1, --lastIndex);
					ib.delete(position);
					ib.setFocus(true, position);
				} catch (@SuppressWarnings("unused") IndexOutOfBoundsException e) {
					ib.getCaret().setOption(LAST_MARK_POSITION, unmodifiedList);
				}
				return true;
			}
		};
	}
	
	// LEVEL -- 1, 5, 2
	static KeyForm delete() {
		return new KeyForm() {
			
			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}
			
			@Override
			public String getString() {
				return "delete";
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				InputBuilder ib = i.getInputBuilder();
				ArrayList<Integer> position = (ArrayList<Integer>) ib.getCaret().getOption(LAST_MARK_POSITION);
				try {
					ib.delete(position);
					ib.setFocus(true, position);
				} catch (@SuppressWarnings("unused") IndexOutOfBoundsException e) {
				}
				return true;
			}
		};
	}
	
	// LEVEL -- 2, 1,1
	// TODO: Does nothing for now. Supposed to stop evaluation
	static KeyForm escape() {
		return new KeyForm() {
			
			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}
			
			@Override
			public String getString() {
				return "esc";
			}
			
			@SuppressWarnings({ "deprecation" })
			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				c.getEvaluationThread().stop();
				return true;
			}
		};
	}
	
	// LEVEL -- 2, 1, 2
	// TODO: Does nothing for now. Supposed cause
	// replacement instead of insertion or appendage
	static KeyForm insert() {
		return new KeyForm() {
			
			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}
			
			@Override
			public String getString() {
				return "ins";
			}
			
			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				return true;
			}
		};
	}
	
	// LEVEL -- 2, 2, 1
	/*
	 * Date: 14 Aug 2022----------------------------------------------------------- 
	 * Time created: 09:26:03--------------------------------------------
	 */
	/**
	 * Pushes the caret backwards with each click
	 * @return
	 */
	static KeyForm back() {
		return new KeyForm() {
			
			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}
			
			@Override
			public String getString() {
				return "back";
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				InputBuilder ib = i.getInputBuilder();
				ForwardMarker caret = ib.defaultCaret();
				ArrayList<Integer> pos = (ArrayList<Integer>) caret.getOption(LAST_MARK_POSITION);
				ArrayList<Integer> clone = (ArrayList<Integer>) pos.clone();
				int lastIndex = pos.get(pos.size() - 1);
				try {
					while(lastIndex == 0) {
						pos.remove(pos.size() - 1);
						lastIndex = pos.get(pos.size() - 1);
					}
					pos.set(pos.size() - 1, --lastIndex);
					ib.setFocus(true, pos);
				} catch (@SuppressWarnings("unused") IndexOutOfBoundsException e) {
					ib.getCaret().setOption(LAST_MARK_POSITION, clone);
				}
				
				return true;
			}
		};
	}
	
	//Level -- 2, 2, 2
	/*
	 * Date: 14 Aug 2022----------------------------------------------------------- 
	 * Time created: 09:42:29--------------------------------------------
	 */
	/**
	 * @return
	 */
	static KeyForm home() {
		return new KeyForm() {
			
			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}
			
			@Override
			public String getString() {
				return "home";
			}
			
			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				InputBuilder ib = i.getInputBuilder();
				if(!ib.isEmpty())
					ib.setFocus(true, 0);
				return true;
			}
		};
	}
	
	//Level -- 2, 3, 1
	static KeyForm info() {
		return new KeyForm() {
			
			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}
			
			@Override
			public String getString() {
				return "info";
			}
			
			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				i.setLog(CEvaluator.LOG_INFO);
				return true;
			}
		};
	}
	
	//Level -- 2, 3, 2
	static KeyForm warn() {
		return new KeyForm() {
			
			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}
			
			@Override
			public String getString() {
				return "warn";
			}
			
			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				i.setLog(CEvaluator.LOG_WARNING);
				return true;
			}
		};
	}
	
	//Level -- 2, 4, 1
	static KeyForm forward() {
		return new KeyForm() {
			
			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}
			
			@Override
			public String getString() {
				return "forward";
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				InputBuilder ib = i.getInputBuilder();
				ForwardMarker caret = ib.defaultCaret();
				ArrayList<Integer> pos = (ArrayList<Integer>) caret.getOption(LAST_MARK_POSITION);
				List<Integer> allInputs = (List<Integer>) caret.getOption(CURRENT_MAX_POSITION);
				ArrayList<Integer> clone = (ArrayList<Integer>) pos.clone();
				try {
					if(!pos.equals(allInputs)) {
						pos.set(pos.size() - 1, pos.get(pos.size() - 1) + 1);
						ib.setFocus(true, pos);
					}
				} catch (@SuppressWarnings("unused") IndexOutOfBoundsException e) {
					ib.getCaret().setOption(LAST_MARK_POSITION, clone);
				}
				
				return true;
			}
		};
	}
	
	//Level -- 2, 4, 2
	static KeyForm end() {
		return new KeyForm() {
			
			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}
			
			@Override
			public String getString() {
				return "end";
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				InputBuilder ib = i.getInputBuilder();
				ForwardMarker caret = ib.defaultCaret();
				List<Integer> pos = (List<Integer>) caret.getOption(CURRENT_MAX_POSITION);
				List<Integer> allInputs = (List<Integer>) caret.getOption(CURRENT_MAX_POSITION);
				try {
					ib.setFocus(false, pos);
					ib.setFocus(true, allInputs);
				} catch (@SuppressWarnings("unused") IndexOutOfBoundsException e) {
				}
				
				return true;
			}
		};
	}
	
	//Level -- 3, 1, 1
	// TODO: Does nothing for now
	static KeyForm mPlus() {
		return new KeyForm() {
			
			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}
			
			@Override
			public String getString() {
				return "m+";
			}
			
			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				return true;
			}
		};
	}
	
	//Level -- 3, 1, 2
	// TODO: Does nothing for now
	static KeyForm mMinus() {
		return new KeyForm() {
			
			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}
			
			@Override
			public String getString() {
				return "m-";
			}
			
			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				return true; 
			}
		};
	}
	
	//Level -- 3, 2, 1
	// TODO: Does nothing for now
	static KeyForm mClear() {
		return new KeyForm() {
			
			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}
			
			@Override
			public String getString() {
				return "mc";
			}
			
			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				return true; 
			}
		};
	}
	
	//Level -- 3, 2, 2
	// TODO: Does nothing for now
	static KeyForm mRecall() {
		return new KeyForm() {
			
			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}
			
			@Override
			public String getString() {
				return "mr";
			}
			
			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				return true; 
			}
		};
	}
	
	//Level -- 3, 3
	static KeyForm down() {
		return new KeyForm() {
			
			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}
			
			@Override
			public String getString() {
				return "down";
			}
			
			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				InputBuilder ib = i.getInputBuilder();
				@SuppressWarnings("unchecked")
				List<Integer> position = (List<Integer>) ib.getCaret().getOption(LAST_MARK_POSITION);
				try {
					position.add(1);// for the tree node
					position.add(0);// for beginning of the linked-node
					ib.setFocus(true, position);
				} catch (IndexOutOfBoundsException e) {
					position.remove(0);// for the tree node
					position.remove(0);// for beginning of the linked-node
				}
				return true; 
			}
		};
	}
	
	//Level -- 3, 4, 1
	//TODO: Does nothing for now
	static KeyForm enter() {
		return new KeyForm() {
			
			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}
			
			@Override
			public String getString() {
				return "enter";
			}
			
			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				return true; 
			}
		};
	}
	
	//Level -- 3, 4, 2
	//TODO: Does nothing for now
	static KeyForm history() {
		return new KeyForm() {
			
			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}
			
			@Override
			public String getString() {
				return "history";
			}
			
			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				InputBuilder ib = i.getInputBuilder();
				@SuppressWarnings("unchecked")
				List<Integer> position = (List<Integer>) ib.getCaret().getOption(LAST_MARK_POSITION);
				try {
					position.add(1);// for the tree node
					position.add(0);// for beginning of the linked-node
					ib.setFocus(true, position);
				} catch (IndexOutOfBoundsException e) {
					position.remove(0);// for the tree node
					position.remove(0);// for beginning of the linked-node
				}
				return true; 
			}
		};
	}
	
	//Level -- 3, 5, 1
	//TODO: Does nothing for now
	static KeyForm undo() {
		return new KeyForm() {
			
			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}
			
			@Override
			public String getString() {
				return "undo";
			}
			
			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				return true; 
			}
		};
	}
	
	//Level -- 3, 5, 2
	//TODO: Does nothing for now
	static KeyForm redo() {
		return new KeyForm() {

			@Override
			public String getFormat() {
				return getString().toUpperCase();
			}

			@Override
			public String getString() {
				return "redo";
			}

			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				InputBuilder ib = i.getInputBuilder();
				@SuppressWarnings("unchecked")
				List<Integer> position = (List<Integer>) ib.getCaret().getOption(LAST_MARK_POSITION);
				try {
					position.add(1);// for the tree node
					position.add(0);// for beginning of the linked-node
					ib.setFocus(true, position);
				} catch (IndexOutOfBoundsException e) {
					position.remove(0);// for the tree node
					position.remove(0);// for beginning of the linked-node
				}
				return true; 
			}
		};
	}

	//Functions
	static KeyForm sin() {
		return new KeyForm() {

			@Override
			public String getFormat() {
				return "\\sin";
			}

			@Override
			public String getString() {
				return "sin";
			}

			@Override
			public boolean execute(CEvaluator<DataText> c, Input i) {
				return false;
			}
			
		};
	}
	
	private ScientificKeyForms() {
	}
}
