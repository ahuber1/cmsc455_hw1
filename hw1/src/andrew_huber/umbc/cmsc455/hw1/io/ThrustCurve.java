package andrew_huber.umbc.cmsc455.hw1.io;

import java.util.ArrayList;

public class ThrustCurve {

	private double lowestSecond = 0;
	private double highestSecond = 0;
	private double step = 0;
	private double previous = 0;
	
	private int precision = 0;
	
	private ArrayList<Double> curve;
	
	public ThrustCurve() {
		curve = new ArrayList<Double>();
	}
	
	public void addValue(double timeInSeconds, double thrustInNewtons) {		
		if(timeInSeconds < lowestSecond)
			lowestSecond = timeInSeconds;
		if(timeInSeconds > highestSecond)
			highestSecond = timeInSeconds;
		
		if(timeInSeconds > 0) {
			if(step == 0) {
				step = timeInSeconds - previous;
				
				String stepString = Double.toString(step);
				String[] tokens = stepString.split("\\.");
				
				precision = tokens[1].length();
			}
			else {
				double x = round(timeInSeconds - previous, precision); // gets rid of some arithmetic errors
				double y = step - x;
				
				if(y != 0)
					throw new IllegalStateException("The thrust curve's time increments do not increment at a constant rate");
			}				
		}
		
		previous = timeInSeconds;
		curve.add(thrustInNewtons);
	}
	
	public double thrustAt(double t) {
		if(t < lowestSecond)
			return curve.get(0);
		else if(t > highestSecond)
			return curve.get(curve.size() - 1);
		else {
			double tRounded = round(t, precision);
			double iDouble = (tRounded - lowestSecond) / step;
			int i = (int) iDouble;
			
			return curve.get(i);
		}
	}
	
	private double round(double val, int precision) {
		String formatString = "%." + precision + "g%n";
		String tString = String.format(formatString, val);
		
		return Double.parseDouble(tString);
	}
}
