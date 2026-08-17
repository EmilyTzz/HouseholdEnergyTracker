package file;

import main.Menu;
import object.Cost;
import object.Usage;

import java.io.*;
import java.util.Scanner;

public class Reader {

    public void loadInfo(File file, Usage usage, Cost cost, String keepPriceOrNot){
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null){
                if (isFirstLine){
                    if (keepPriceOrNot.equals(Menu.DONT_KEEP_CURRENT_PRICES)){
                        String[] data = line.split(",");
                        double pricePerKwH = Double.parseDouble(data[0]);
                        double pricePerGJ = Double.parseDouble(data[1]);
                        cost.setCostPerKwH(pricePerKwH);
                        cost.setCostPerGJ(pricePerGJ);
                    }
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

        }catch (FileNotFoundException e) {
            System.out.println("* Error: File was not found *");
        }catch (IOException e){
            System.out.println("* Error: An issue occured while reading the file *");
        }
    }

}
