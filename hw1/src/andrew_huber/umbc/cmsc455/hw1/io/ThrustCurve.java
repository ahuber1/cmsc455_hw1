package andrew_huber.umbc.cmsc455.hw1.io;

import java.util.ArrayList;

/**
 * This stores a <i>discrete</i> thrust curve (as opposed to a continuous one).
 * For more information on how the thrust curve is loaded, view the documentation
 * for {@link ThrustCurve#addValue(double, double)}
 * 
 * @author andrew_huber
 */
public class ThrustCurve {
	
	/** The lowest second value in the thrust curve */
	private double lowestSecond = 0;
	
	/** The highest second value in the thrust curve */
	private double highestSecond = 0;
	
	/**
	 * The thrust curve loads in a list of key-value pairs. The keys
	 * are quantities measured in seconds, they are added in ascending order
	 * and each key is {@code step} more than the last value.
	 * <br>
	 * <br>
	 * For example, if the keys (quantities measured in seconds) are 
	 * <br>
	 * <br><center>
	 * {@code 0.0, 0.1, 0.2, ...}
	 * </center><br>
	 * <br>
	 * Then {@code step} is {@code 0.1}
	 */
	private double step = 0;
	
	/** 
	 * The previous second value that was added to the {@link ThrustCurve}
	 * @see ThrustCurve#addValue(double, double)
	 */
	private double previous = 0;
	
	/** 
	 * The level of precision of the second values 
	 * @see ThrustCurve#addValue(double, double)
	 */
	private int precision = 0;
	
	/** This will store the list of values in the Thrust Curve */
	private ArrayList<Double> curve;
	
	/** Makes a new {@link ThrustCurve} */
	public ThrustCurve() {
		curve = new ArrayList<Double>();
	}
	
	/**
	 * Adds a value to the {@link ThrustCurve}. <b>Note that quantities measured in seconds
	 * ({@code timeInSeconds}) <i>must</i> be sorted in numerical order starting at 0.0</b>
	 * 
	 * @param timeInSeconds the time measured in seconds
	 * @param thrustInNewtons the amount of thrust at time {@code timeInNewtons} measured in seconds
	 */
	public void addValue(double timeInSeconds, double thrustInNewtons) {		
		if(timeInSeconds < lowestSecond)
			lowestSecond = timeInSeconds;
		if(timeInSeconds > highestSecond)
			highestSecond = timeInSeconds;
		
		// If timeInSeconds > 0, we can calculate the step and the precision
		if(timeInSeconds > 0) {
			if(step == 0) {
				step = timeInSeconds - previous;
				
				String stepString = Double.toString(step);
				String[] tokens = stepString.split("\\.");
				
				precision = tokens[1].length();
			}
			else {
				// round is called because it gets rid of some arithmetic errors as a result of 
				// IEEE 754
				double x = round(timeInSeconds - previous, precision); 
				double y = step - x;
				
				if(y != 0) {
					throw new IllegalStateException("The thrust curve's time increments do not "
							+ "increment at a constant rate");
				}
			}				
		}
		
		previous = timeInSeconds;
		curve.add(thrustInNewtons);
	}
	
	/**
	 * Retrieves the thrust at a particular time
	 * @param t the time elapsed since launch, measured in seconds
	 * @return the thrust at time {@code t}
	 */
	public double thrustAt(double t) {
		if(t < lowestSecond)
			return curve.get(0);
		else if(t > highestSecond)
			return curve.get(curve.size() - 1);
		else {
			// Calculate the index in which t is located. Depending on the step value,
			// the closest value to t that is in the thrust curve will be returned
			double tRounded = round(t, precision);
			double iDouble = (tRounded - lowestSecond) / step;
			int i = (int) iDouble;
			
			return curve.get(i);
		}
	}
	
	/**
	 * Rounds a value to a certain precision
	 * @param val the value to round
	 * @param precision the amount of precision
	 * @return {@code val} rounded to precision {@code precision}
	 */
	private double round(double val, int precision) {
		String formatString = "%." + precision + "g%n";
		String tString = String.format(formatString, val);
		
		return Double.parseDouble(tString);
	}
}
