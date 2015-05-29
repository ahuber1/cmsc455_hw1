package andrew_huber.umbc.cmsc455.hw1;

import java.io.FileNotFoundException;

import andrew_huber.umbc.cmsc455.hw1.io.*;

public class Main {
	
	public static void main(String[] args) {
		if(args.length > 0) {
			VariableManager<String, Double> manager = null;
			ThrustCurve curve = null;
			
			try {
				manager = Reader.readVariables(args[0]);
				curve = Reader.readThrustCurve(args[1]);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			
			runSimulation(manager, curve);
		}
		else {
			System.err.println("This program requires two command line argument.");
			System.err.println("                                *********                                ");
			System.err.println("The first argument must contain the filepath to a text file that contains");
			System.err.println("the values of variables that will be used by this program.");
			System.err.println();
			System.err.println("The second argument must contain the file path to a text file that contains");
			System.err.println("the thrust curve as comma-separated values");
			System.exit(-2);
		}
	}
	
	public static void runSimulation(VariableManager<String, Double> manager, ThrustCurve curve) {
		double a 		= 0; 							// TODO what is this?
		double A_body	= manager.getValue("A_body");	// total surface area in square meters - body
		double A_fins 	= manager.getValue("A_fins");	// total surface area in square meters - fins
		double Cd_body 	= manager.getValue("Cd_body");	// coefficient of drag - body
		double Cd_fins 	= manager.getValue("Cd_fins"); 	// coefficient of drag - fins
		double ds	 	= 0; 							// TODO what is this?
		double dv	 	= 0; 							// TODO what is this?
		double dt	 	= manager.getValue("dt"); 		// delta time in seconds
		double F 		= 0; 							// TODO what is this?
		double Fg 		= 0; 							// TODO what is this?
		double Ft		= 0; 							// TODO what is this?
		double Fd_body 	= 0;						 	// force in newtons in opposite direction of velocity - body
		double Fd_fins 	= 0;							// force in newtons in opposite direction of velocity - fins
		double g		= manager.getValue("g"); 		// acceleration due to gravity
		double m 		= 0.0340 + 0.0242;				// TODO what is this?
		double Rho 		= manager.getValue("Rho"); 		// density of air
		double s		= 0; 							// TODO what is this?
		double t 		= 0;							// TODO what is this?
		double v 		= 0;							// TODO what is this?
		
		int iterationCount = 0;
		
		while(s <= (350 - (350 * 0.3))) {
			Fd_body	= Cd_body * Rho * A_body * Math.pow(v, 2) / 2;
			Fd_fins = Cd_fins * Rho * A_fins * Math.pow(v, 2) / 2;
			Fg 		= m * g;
			Ft 		= curve.thrustAt(t);
			F 		= Ft - (Fd_body + Fd_fins + Fg);
			a 		= F / m;
			dv 		= a * dt;
			v 		= v + dv;
			ds 		= v * dt;
			s 		= s + ds;
			m 		= m - 0.0001644 * Ft;
			t 		= t + dt;
			
			System.out.printf("%5d: %.3f m (height of rocket)\n", iterationCount, s);
		}
	}
}
