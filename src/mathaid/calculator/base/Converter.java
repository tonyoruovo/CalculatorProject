/**
 * 
 */
package mathaid.calculator.base;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mathaid.calculator.base.converter.AngleUnit;
import mathaid.calculator.base.converter.AreaUnit;
import mathaid.calculator.base.converter.Currencies;
import mathaid.calculator.base.converter.CurrencyUnit;
import mathaid.calculator.base.converter.DataUnit;
import mathaid.calculator.base.converter.DistanceUnit;
import mathaid.calculator.base.converter.EnergyUnit;
import mathaid.calculator.base.converter.ForceUnit;
import mathaid.calculator.base.converter.FrequencyUnit;
import mathaid.calculator.base.converter.FuelConsumptionUnit;
import mathaid.calculator.base.converter.MassUnit;
import mathaid.calculator.base.converter.PowerUnit;
import mathaid.calculator.base.converter.PressureUnit;
import mathaid.calculator.base.converter.SpeedUnit;
import mathaid.calculator.base.converter.TemperatureUnit;
import mathaid.calculator.base.converter.TemporalUnit;
import mathaid.calculator.base.converter.TorqueUnit;
import mathaid.calculator.base.converter.VolumeUnit;
import mathaid.calculator.base.parser.CEvaluator;
import mathaid.designpattern.Observer;

/*
 * Date: 1 Jul 2021----------------------------------------------------------- 
 * Time created: 16:45:22---------------------------------------------------  
 * Package: mathaid.calculator.base------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: Converter.java------------------------------------------------------ 
 * Class name: Converter------------------------------------------------ 
 */
/**
 * One of the 3 main evaluators (which represents one of the 3 modes) of the
 * {@code Calculator} class that can convert numeric values using various types
 * of converters set by the {@code Settings} object. It is equipped with a
 * {@link DetailsList} objects (like all {@code CEvaluator} objects) for further
 * calculation of units within the same logical unit set.
 * <p>
 * All converters used are defined in the
 * {@link mathaid.calculator.base.converter convert API}
 * </p>
 * <p>
 * This class implements the {@code CEvaluator} interface and as a result is
 * both a {@code Subject} and an {@code Observer}. Within the {@code Calculator}
 * class, it is registered to the {@code Input} class (as is all the evaluators
 * of the {@code Calculator} class) for receiving text inputs passed by the
 * user. It also registers the {@code Output} (as does all the evaluators of the
 * {@code Calculator} class) and the {@code DetailsList.ConverterDetailsList}
 * classes, for dispersing results of computations. When an input is received
 * from an {@code Input} object, it processes the input and if the expression is
 * well formed proceeds to evaluate it using a simple evaluator after which it
 * is sent to both the {@code Output} and
 * {@code DetailsList.ConverterDetailsList} classes for pretty-printing.
 * Recording of calculations (retrieved via {@link #getHistory()}) is only done
 * upon calling {@link #evaluate()}, but calling {@code evaluate()} may produce
 * an error (more formally an exception) if the data which should be evaluated
 * is never updated as the field for the input text is declared as
 * {@code volatile} which means that the system will never cache it's value
 * hence it needs to be constantly updated or else it may be found to be
 * {@code null}.
 * </p>
 * <p>
 * The {@code Calculator} class has implemented this object to work
 * concurrently. That is, every time a user enters a value into the input, this
 * class' {@link #doAction()} method runs and before {@link #update()} is called
 * to update the output. However, these methods of implementation will ignore
 * the internal {@code History} object which may not record vital calculations
 * being made. The {@code Calculator} class fixes this by calling
 * {@link #evaluate()} - which actually records the calculations - when the
 * equals button is pressed.
 * </p>
 * 
 * @author Oruovo Anthony Etineakpopha
 * @see CEvaluator
 * @see DataText
 * @see Calculator
 * @see DetailsList.ConverterDetailsList
 * @see History
 * 
 */
public class Converter implements CEvaluator<DataText> {

	public static final String OPTIONS_KEY_SHIFT = "isShift";
	public static final String OPTIONS_KEY_CURRENT_KEYS_INDEX = "keyIndex";
	
	/**
	 * The internal calculations history holder.
	 */
	private final History<DataText> history;
	/**
	 * Inputs received from the user.
	 */
	private volatile DataText input;
	/**
	 * Holds the results of computations.
	 */
	private DataText output;
	/**
	 * The {@code List} of {@code Observer} objects registered to this object via
	 * {@link #register(Observer)}.
	 */
	private List<Observer<DataText>> observers;
	/**
	 * The {@code DetailsList} that holds details about calculations.
	 */
	private final DetailsList details;
	
	private final Map<String, Object> options = new HashMap<>();

	/**
	 * The memory of this calculator specified by the "M"
	 * symbol at the bottom of the input screen.
	 * 
	 */
	// XXX: version 100100
	private DataText mem;

	/*
	 * Date: 10 Aug 2021-----------------------------------------------------------
	 * Time created: 13:00:55---------------------------------------------------
	 */
	/**
	 * A sole constructor for this class that initialises the observers list, the
	 * history and details list. It also registers the details list to itself. This
	 * is done so as to prevent the calculator class from directly calling this
	 * object.
	 */
	public Converter() {
		
		options.put(OPTIONS_KEY_SHIFT, false);//for shift modifier
		/*
		 * Because the converter units (such as feet, inches and meters) can have
		 * operations (such as add, subtract and multiplication) performed
		 * on them, there will be keys present to directly input a unit symbol directly
		 * into the calculator input. For example 38ft /18m.
		 * 
		 * However, an issue arises especially with currencies. The number of currency units
		 * surpass the number of physical buttons on the face of the calculator. So the
		 * buttons will display on the faces in batches. That way, all units will be
		 * covered.
		 */
		options.put(OPTIONS_KEY_CURRENT_KEYS_INDEX, 0);//for shift modifier
		
		history = new History<>();
		observers = new ArrayList<>();
		details = new DetailsList.ConverterDetailsList();
		register(details);
		// XXX: version 100100
		mem = new DataText("0", "0");
	}

	/*
	 * Most Recent Date: 1 Jul 2021-----------------------------------------------
	 * Most recent time created: 16:46:07--------------------------------------
	 */
	/**
	 * Evaluates current expression given by {@link #inform(DataText)}. This action
	 * does not record any data into the {@code History} of this object.
	 * <p>
	 * The input is first parsed to a {@code BigDecimal} after which the current
	 * unit is ascertained via {@link Settings#getCurrentConverter()}. With the
	 * current converter known, the unit from which to convert and the unit to which
	 * the conversion will be made is gotten from
	 * {@link Settings#getCurrentConverterFrom()} and
	 * {@link Settings#getCurrentConverterTo()} respectively. When this is done, the
	 * actual conversion is carried out via
	 * {@link mathaid.calculator.base.converter.Convertible#convert(Object, mathaid.calculator.base.converter.Convertible, Object)}
	 * and the result is written to the output.
	 * </p>
	 */
	@Override
	public void doAction() {
		synchronized (this) {
			BigDecimal x = new BigDecimal(input.getSymjaString());
			Settings s = Settings.defaultSetting();
			MathContext context = new MathContext(s.getScale(), RoundingMode.HALF_EVEN);
			switch (s.getCurrentConverter()) {
			case 0:
				AngleUnit auFrom = AngleUnit.values()[s.getCurrentConverterFrom()];
				AngleUnit auTo = AngleUnit.values()[s.getCurrentConverterTo()];
				x = auFrom.convert(x, auTo, context);
				break;
			case 1:
				AreaUnit aFrom = AreaUnit.values()[s.getCurrentConverterFrom()];
				AreaUnit aTo = AreaUnit.values()[s.getCurrentConverterTo()];
				x = aFrom.convert(x, aTo, context);
				break;
			case 2:
				CurrencyUnit cf = CurrencyUnit
						.valueOf(Currencies.CURRENCIES.get(s.getCurrentConverterFrom()).getNumericCode());
				CurrencyUnit ct = CurrencyUnit
						.valueOf(Currencies.CURRENCIES.get(s.getCurrentConverterTo()).getNumericCode());
				x = cf.convert(x, ct, context);
				break;
			case 3:
				DistanceUnit df = DistanceUnit.values()[s.getCurrentConverterFrom()];
				DistanceUnit dt = DistanceUnit.values()[s.getCurrentConverterTo()];
				x = df.convert(x, dt, context);
				break;
			case 4:
				DataUnit daf = DataUnit.values()[s.getCurrentConverterFrom()];
				DataUnit dat = DataUnit.values()[s.getCurrentConverterTo()];
				x = daf.convert(x, dat, context);
				break;
			case 5:
				EnergyUnit ef = EnergyUnit.values()[s.getCurrentConverterFrom()];
				EnergyUnit et = EnergyUnit.values()[s.getCurrentConverterTo()];
				x = ef.convert(x, et, context);
				break;
			case 6:
				ForceUnit ff = ForceUnit.values()[s.getCurrentConverterFrom()];
				ForceUnit ft = ForceUnit.values()[s.getCurrentConverterTo()];
				x = ff.convert(x, ft, context);
				break;
			case 7:
				FrequencyUnit frf = FrequencyUnit.values()[s.getCurrentConverterFrom()];
				FrequencyUnit frt = FrequencyUnit.values()[s.getCurrentConverterTo()];
				x = frf.convert(x, frt, context);
				break;
			case 8:
				FuelConsumptionUnit fcf = FuelConsumptionUnit.values()[s.getCurrentConverterFrom()];
				FuelConsumptionUnit fct = FuelConsumptionUnit.values()[s.getCurrentConverterTo()];
				x = fcf.convert(x, fct, context);
				break;
			case 9:
				MassUnit mf = MassUnit.values()[s.getCurrentConverterFrom()];
				MassUnit mt = MassUnit.values()[s.getCurrentConverterTo()];
				x = mf.convert(x, mt, context);
				break;
			case 10:
				PowerUnit pf = PowerUnit.values()[s.getCurrentConverterFrom()];
				PowerUnit pt = PowerUnit.values()[s.getCurrentConverterTo()];
				x = pf.convert(x, pt, context);
				break;
			case 11:
				PressureUnit prf = PressureUnit.values()[s.getCurrentConverterFrom()];
				PressureUnit prt = PressureUnit.values()[s.getCurrentConverterTo()];
				x = prf.convert(x, prt, context);
				break;
			case 12:
				SpeedUnit sf = SpeedUnit.values()[s.getCurrentConverterFrom()];
				SpeedUnit st = SpeedUnit.values()[s.getCurrentConverterTo()];
				x = sf.convert(x, st, context);
				break;
			case 13:
				TemperatureUnit tf = TemperatureUnit.values()[s.getCurrentConverterFrom()];
				TemperatureUnit tt = TemperatureUnit.values()[s.getCurrentConverterTo()];
				x = tf.convert(x, tt, context);
				break;
			case 14:
				TemporalUnit tpf = TemporalUnit.values()[s.getCurrentConverterFrom()];
				TemporalUnit tpt = TemporalUnit.values()[s.getCurrentConverterTo()];
				x = tpf.convert(x, tpt, context);
				break;
			case 15:
				TorqueUnit tqf = TorqueUnit.values()[s.getCurrentConverterFrom()];
				TorqueUnit tqt = TorqueUnit.values()[s.getCurrentConverterTo()];
				x = tqf.convert(x, tqt, context);
				break;
			case 16:
				VolumeUnit vf = VolumeUnit.values()[s.getCurrentConverterFrom()];
				VolumeUnit vt = VolumeUnit.values()[s.getCurrentConverterTo()];
				x = vf.convert(x, vt, context);
				break;
			default:
				throw new RuntimeException("Unknown unit");
			}
			output = new DecimalDetailsList(x.toString()).getExpression();
		}
	}

	/*
	 * Most Recent Date: 1 Jul 2021-----------------------------------------------
	 * Most recent time created: 16:45:49--------------------------------------
	 */
	/**
	 * Calls {@link #doAction()} and then records the input in the internal
	 * {@code History} object then returns the result of the calculation as a
	 * {@code DataText}.
	 * 
	 * @return a {@code DataText} object containing the TeX notation and the plain
	 *         java string of the result of the evaluation.
	 */
	@Override
	public DataText evaluate() {
		doAction();
		history.record(input);
		return output;
	}

	/*
	 * Most Recent Date: 1 Jul 2021-----------------------------------------------
	 * Most recent time created: 16:45:49--------------------------------------
	 */
	/**
	 * Registers the given {@code Observer}. In practice, this registers the given
	 * calculator output and details list.
	 * 
	 * @param o the {@code Observer} to be registered.
	 */
	@Override
	public void register(Observer<DataText> o) {
		observers.add(o);
	}

	/*
	 * Most Recent Date: 1 Jul 2021-----------------------------------------------
	 * Most recent time created: 16:45:49--------------------------------------
	 */
	/**
	 * Unregisters the given {@code Observer}.
	 * 
	 * @param o the {@code Observer} to be unregistered.
	 */
	@Override
	public void unRegister(Observer<DataText> o) {
		observers.remove(o);
	}

	/*
	 * Most Recent Date: 1 Jul 2021-----------------------------------------------
	 * Most recent time created: 16:45:49--------------------------------------
	 */
	/**
	 * Updates all the {@code Observer} objects registered to this object. In
	 * practice, only the {@code Output} and
	 * {@code DetailsList.ConverterDetailsList} objects will be updated.
	 */
	@Override
	public void update() {
		for (Observer<DataText> o : observers) {
			o.inform(output);
		}
	}

	/*
	 * Most Recent Date: 1 Jul 2021-----------------------------------------------
	 * Most recent time created: 16:45:49--------------------------------------
	 */
	/**
	 * Updates the input source of this object with the latest value. The updated
	 * source is used as the data upon which evaluation is made. This method is
	 * meant only to be called by the {@code Subject} that registered this object.
	 * <b><i>This method must first be called before doAction() or else users may
	 * have undefined results.</i></b>
	 * 
	 * @param t the latest value to be evaluated.
	 */
	@Override
	public void inform(DataText t) {
		this.input = t;
	}

	/*
	 * Most Recent Date: 1 Jul 2021-----------------------------------------------
	 * Most recent time created: 16:45:49--------------------------------------
	 */
	/**
	 * Returns the history of the input received by this object.
	 * 
	 * @return a {@code History} of {@code DataText} objects containing calculations
	 *         received by this object since it's initialisation.
	 */
	@Override
	public History<DataText> getHistory() {
		return history;
	}

	/*
	 * Most Recent Date: 1 Jul 2021-----------------------------------------------
	 * Most recent time created: 16:45:49--------------------------------------
	 */
	/**
	 * Returns the {@code DetailsList} of this object.
	 * 
	 * @return a {@code DetailsList.ConverterDetailsList}.
	 */
	@Override
	public DetailsList getDetails() {
		return details;
	}

	/*
	 * Most Recent Date: 23 Aug 2021-----------------------------------------------
	 * Most recent time created: 13:51:42--------------------------------------
	 */
	/**
	 * Adds the current conversion result to the previous one stored in the memory and returns the last
	 * value it.
	 * 
	 * @return the result gotten from adding the previous to the current one..
	 */
	// XXX: version 100100
	@Override
	public DataText mPlus() {
		BigDecimal n = new BigDecimal(mem.getSymjaString())
				.add(new BigDecimal(output.getSymjaString()));
		mem = new DecimalDetailsList(n.toString()).getExpression();
		return mem;
	}

	/*
	 * Most Recent Date: 23 Aug 2021-----------------------------------------------
	 * Most recent time created: 13:57:35--------------------------------------
	 */
	/**
	 * Subtracts the current result from the previous one stored in the memory and returns the result of the operation.
	 * @return the result gotten from subtracting the previous one from the current one.
	 */
	// XXX: version 100100
	@Override
	public DataText mMinus() {
		BigDecimal n = new BigDecimal(mem.getSymjaString())
				.subtract(new BigDecimal(output.getSymjaString()));
		mem = new DecimalDetailsList(n.toString()).getExpression();
		return mem;
	}
}
