package file;

import object.Cost;
import object.Usage;
import object.UsageSorter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Writer {

    public void saveInfo(File file, UsageSorter usageSorter, Cost cost){
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))){
            writer.printf(Cost.getCostPerKwH() + "," + Cost.getCostPerGJ());
            writer.println("month,electricity used,natural gas used");
            usageSorter.sortInfoAccordingToMonths();
            for (int i = 0; i < usageSorter.getSortedMonths().size(); i ++){
                writer.printf("%s,%f,%s\n", usageSorter.getSortedMonths().get(i), usageSorter.getSortedElectricityUsed().get(i), usageSorter.getSortedNaturalGasUsed().get(i));
            }
            System.out.println("\n* Data Successfully Saved to " + file + " *\n");
        }catch (IOException e){
            System.out.println("ERROR");
        }
    }

}
