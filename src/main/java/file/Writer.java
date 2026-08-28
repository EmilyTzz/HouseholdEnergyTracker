package file;

import object.Cost;
import object.UsageSorter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class handles saving usage data to a CSV file
 */
public class Writer {

    public boolean validFile = false;

    /**
     * This method helps to save the current data from the usage object into a CSV file
     * @param file file user wants to save the data to
     * @param usageSorter usage sorter object with sorted data from the usage object, according to the months
     * @param cost the current cost object
     * @return message to indicate if data was saved or not
     */
    public String saveInfo(File file, UsageSorter usageSorter, Cost cost){
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))){
            writer.printf(cost.getCostPerKwH() + "," + cost.getCostPerGJ()); // divide the 2 costs with a comma
            writer.println("month,electricity used,natural gas used");
            usageSorter.sortInfoAccordingToMonths(); // sort the data according to the traditional month order
            for (int i = 0; i < usageSorter.getSortedMonths().size(); i ++){ // seperate the data values with a comma
                writer.printf("%s,%f,%s\n", usageSorter.getSortedMonths().get(i), usageSorter.getSortedElectricityUsed().get(i), usageSorter.getSortedNaturalGasUsed().get(i));
            }
            validFile = true;
            return ("Data Successfully Saved to " + file.getName());
        }catch (IOException e){
            return ("ERROR: File was not saved. Please select a valid file location");
        }
    }

}
