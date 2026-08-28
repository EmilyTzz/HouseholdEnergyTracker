package file;

import main.Menu;
import object.Cost;
import object.Usage;

import java.io.*;

/**
 * This class handles loading usage data from a CSV file
 */
public class Reader {

    public boolean validFile = false;

    /**
     * This method helps to load all of the usage data from the CSV file into a new usage object
     * @param file CSV file the user chooses to load the data from
     * @param usage the new usage object to add the data to
     * @param cost the current cost object to set the new prices
     * @return a message of whether or not the file was loaded
     */
    public String loadInfo(File file, Usage usage, Cost cost){
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            validFile = false;
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null){ // keep reading file until a line is blank
                if (isFirstLine){ // if the line read is the first line, set the cost/Gj and kwH as the following values
                    String[] data = line.split(",");
                    double pricePerKwH = Double.parseDouble(data[0]);
                    double pricePerGJ = Double.parseDouble(data[1]);
                    // set the current costs to the ones from the file
                    cost.setCostPerKwH(pricePerKwH);
                    cost.setCostPerGJ(pricePerGJ);
                    isFirstLine = false;
                }
                // skip the line with the format indicator of the month, electricity usage, and natural gas usage
                if (line.trim().isEmpty() || line.toLowerCase().startsWith("month")){
                    continue;
                }
                // split the following lines and its values into a list
                String[] data = line.split(",");
                if (data.length < 3){
                    continue;
                }
                String month = data[0];
                double electricity = Double.parseDouble(data[1]);
                double naturalGas = Double.parseDouble(data[2]);
                usage.addMonth(month);
                usage.addElectricityUsage(electricity);
                usage.addNaturalGasUsage(naturalGas);
            }
            validFile = true; // set valid file to true
            return "Successfully load data from " + file.getName();
        }catch (FileNotFoundException e) {
            return "ERROR: Invalid File";
        }catch (IOException e){
            return "ERROR: Invalid File";
        }catch (RuntimeException e) {
            return "ERROR: Invalid File";
        }
    }

}
