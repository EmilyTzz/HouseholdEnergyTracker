package file;

import object.User;

import java.io.*;

public class Reader {

    public void loadInfo(File file, User user){
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null){
                if (line.trim().isEmpty() || line.toLowerCase().startsWith("month")){
                    continue;
                }
                String[] data = line.split(",");
                if (data.length < 3){
                    continue;
                }
                String month = data[0];
                double electricity = Double.parseDouble(data[1]);
                double naturalGas = Double.parseDouble(data[2]);
                user.addMonth(month);
                user.addElectricityUsage(electricity);
                user.addNaturalGasUsage(naturalGas);
            }

        }catch (FileNotFoundException e) {
            System.out.println("* Error: File was not found *");
        }catch (IOException e){
            System.out.println("* Error: An issue occured while reading the file *");
        }
    }

}
