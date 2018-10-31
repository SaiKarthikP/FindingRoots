package roots;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriteToFile {
	private static FileWriter fileWriter;
    private static BufferedWriter bufferedWriter;
    
    public void writeToFile(String fileName, String titleText, List<Double> approxErrData){
		try {
			fileWriter = new FileWriter(fileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(titleText);
			bufferedWriter.newLine();
			for(int j=0;j<approxErrData.size();j++){
				bufferedWriter.write(Double.toString(approxErrData.get(j)));
				bufferedWriter.newLine();
        	}
        	bufferedWriter.close();
    	}
    	catch(IOException ex) {
        	System.out.println(
        		"Error writing to file '"
            		+ fileName + "'");
		}
    }
	
}
