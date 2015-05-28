package andrew_huber.umbc.cmsc455.hw1.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Reader {
	
	public static VariableManager read(String filePath) throws FileNotFoundException {
		File file = new File(filePath);
		Scanner scan = new Scanner(file);
		VariableManager manager = new VariableManager();
		
		while(scan.hasNext()) {
			String line = scan.nextLine();
			String[] tokens = line.split("=");
			
			if(tokens.length < 2) {
				tokens[0] = tokens[0].trim();
				tokens[1] = tokens[1].trim();
				
				try {
					manager.addValue(tokens[0], Double.parseDouble(tokens[1]));
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
}
