package file;

import object.Cost;
import object.UsageSorter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Writer {

    public boolean validFile = false;

    public String saveInfo(File file, UsageSorter usageSorter, Cost cost){
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))){
            writer.printf(cost.getCostPerKwH() + "," + cost.getCostPerGJ());
            writer.println("month,electricity used,natural gas used");
            usageSorter.sortInfoAccordingToMonths();
            for (int i = 0; i < usageSorter.getSortedMonths().size(); i ++){
                writer.printf("%s,%f,%s\n", usageSorter.getSortedMonths().get(i), usageSorter.getSortedElectricityUsed().get(i), usageSorter.getSortedNaturalGasUsed().get(i));
            }
            validFile = true;
            return ("Data Successfully Saved to " + file.getName());
        }catch (IOException e){
            return ("ERROR: File was not saved. Please select a valid file location");
        }
    }

}
