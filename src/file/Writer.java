package file;

import object.Usage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Writer {

    public void saveInfo(File file, Usage usage){
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))){
            writer.println("month,electricity used,natural gas used");
            usage.sortInfoAccordingToMonths();
            for (int i = 0; i < usage.getMonths().size(); i ++){
                writer.printf("%s,%f,%s\n", usage.getMonths().get(i), usage.getElectricityUsed().get(i), usage.getNaturalGasUsed().get(i));
            }
            System.out.println("\n* Data Successfully Saved to " + file + " *\n");
        }catch (IOException e){
            System.out.println("ERROR");
        }
    }

}
