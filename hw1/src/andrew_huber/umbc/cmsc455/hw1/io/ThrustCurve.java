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
				String[] tokens = stepString.split(".");
				
				precision = tokens[1].length();
			}
			else if(step != (timeInSeconds - previous))
				throw new IllegalStateException("The thrust curve's time increments do not increment at a constant rate");
		}
		
		previous = timeInSeconds;
		curve.add(thrustInNewtons);
	}
	
	public double thrustAt(double t) {
		String formatString = "%." + precision + "g%n";
		String tString = String.format(formatString, t);
		
		double tRounded = Double.parseDouble(tString);
		double iDouble = (tRounded - lowestSecond) / step;
		int i = (int) iDouble;
		
		return curve.get(i);
	}
}
