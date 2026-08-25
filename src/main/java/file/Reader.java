package file;

import main.Menu;
import object.Cost;
import object.Usage;

import java.io.*;

public class Reader {

    public boolean validFile = false;

    public void loadInfo(File file, Usage usage, Cost cost){
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            validFile = false;
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null){
                if (isFirstLine){
                    String[] data = line.split(",");
                    double pricePerKwH = Double.parseDouble(data[0]);
                    double pricePerGJ = Double.parseDouble(data[1]);
                    cost.setCostPerKwH(pricePerKwH);
                    cost.setCostPerGJ(pricePerGJ);
                    isFirstLine = false;
                }
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
                usage.addMonth(month);
                usage.addElectricityUsage(electricity);
                usage.addNaturalGasUsage(naturalGas);
            }
            validFile = true;
            System.out.println("\n* Successfully load data from " + file + "*\n");
        }catch (FileNotFoundException e) {
            System.exit(1);
        }catch (IOException e){
            System.exit(1);
        }
    }

}
