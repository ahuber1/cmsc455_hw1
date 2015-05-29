package andrew_huber.umbc.cmsc455.hw1.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Reader {
	
	public static VariableManager<String, Double> readVariables(String filePath) throws FileNotFoundException {
		File file = new File(filePath);
		Scanner scan = new Scanner(file);
		VariableManager<String, Double> manager = new VariableManager<String, Double>();
		
		while(scan.hasNext()) {
			String line = scan.nextLine();
			String[] tokens = line.split("=");
			
			if(tokens.length >= 2) {
				tokens[0] = tokens[0].trim();
				String[] tokens2 = tokens[1].split("#");
				
				try {
					String k = tokens[0].trim();
					double v = Double.parseDouble(tokens2[0].trim());
					manager.addValue(k, v);
				}
				catch (NumberFormatException e) {
					scan.close();
					
					StringBuilder builder = new StringBuilder();
					builder.append("The text following the equals sign must be a number\n");
					builder.append(String.format("Error with string \"%s\"", line));
					throw new NumberFormatException(builder.toString());
				}
			}
		}
		
		scan.close();
		return manager;
	}
	
	public static ThrustCurve readThrustCurve(String filePath) throws FileNotFoundException {
		File file = new File(filePath);
		Scanner scan = new Scanner(file);
		ThrustCurve curve = new ThrustCurve();
		
		while(scan.hasNext()) {
			String line = scan.nextLine();
			String[] tokens = line.split(",");
			
			if(tokens.length >= 2) {
				tokens[0] = tokens[0].trim();
				
				try {
					double k = Double.parseDouble(tokens[0].trim());
					double v = Double.parseDouble(tokens[1].trim());
					curve.addValue(k, v);
				} 
				catch (NumberFormatException e) {
					scan.close();
					throw new NumberFormatException("There must be numbers on either side of the comma in the thrust curve file");
				}
			}
		}
		
		scan.close();
		return curve;
	}
}
