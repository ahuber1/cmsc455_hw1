package andrew_huber.umbc.cmsc455.hw1.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import andrew_huber.umbc.cmsc455.hw1.Main;

/**
 * Reads the text files whose filepaths are passed into the 
 * {@linkplain Main#main(String[]) main method} and returns
 * an object containing the file's data in a usable format
 * @author andrew_huber
 */
public class Reader {
	
	/**
	 * Reads a file containing key-value pairs separated by an equal sign. The key, which
	 * is on the left side of the equal sign, is the variable name; the value, which
	 * is on the right side of the equal sign, is the value associated with that variable.
	 * 
	 * @param filePath the filepath to the file containing the key-value pairs
	 * @return a {@link VariableManager} object containing the contents of the file located
	 * at {@code filePath}
	 * @throws FileNotFoundException if a file is not found at {@code filePath}
	 */
	public static VariableManager readVariables(String filePath) throws FileNotFoundException {
		File file = new File(filePath);
		Scanner scan = new Scanner(file);
		VariableManager manager = new VariableManager();
		
		while(scan.hasNext()) {
			String line = scan.nextLine();
			String[] tokens = line.split("=");
			
			if(tokens.length >= 2) {
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
	
	/**
	 * Reads a file containing key-value pairs separated by a comma sign. The key, which
	 * is on the left side of the comma, is a quantity measured in seconds. The value, which
	 * is on the right side of the comma, is the amount of thrust a rocket engine produces
	 * at that time (measured in newtons).
	 * 
	 * @param filePath the filepath to the file containing the key-value pairs
	 * @return the {@link ThrustCurve} that was read from the file located at {@code filePath}
	 * @throws FileNotFoundException if a file is not found at {@code filePath}
	 */
	public static ThrustCurve readThrustCurve(String filePath) throws FileNotFoundException {
		File file = new File(filePath);
		Scanner scan = new Scanner(file);
		ThrustCurve curve = new ThrustCurve();
		
		while(scan.hasNext()) {
			String line = scan.nextLine();
			String[] tokens = line.split(",");
			
			if(tokens.length >= 2) {				
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
