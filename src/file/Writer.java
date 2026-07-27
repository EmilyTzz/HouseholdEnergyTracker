package file;

import object.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Writer {

    public void saveInfo(File file, User user){
        try{
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            writer.println("month,electricity used,natural gas used");
            for (int i = 0; i < user.getMonths().size(); i ++){
                writer.printf("%s,%f,%s\n", user.getMonths().get(i), user.getElectricityUsed().get(i), user.getNaturalGasUsed());
            }
            System.out.println("\n* Data Successfully Saved to " + file + " *");
        }catch (IOException e){
            System.out.println("ERROR");
        }
    }
}
