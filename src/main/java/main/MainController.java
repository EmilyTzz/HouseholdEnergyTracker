package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import object.CarbonConvertor;
import object.Cost;
import object.Usage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainController {

    // Constants

    public static final String TOTAL_COST = "Total Cost";

    public static final String ELECTRICITY_COST = "Electricity Cost";

    public static final String NATURAL_GAS_COST = "Natural Gas Cost";

    public static final String TOTAL_EMISSION = "Total Emission";

    public static final String KEEP_CURRENT_PRICES = "Y";

    public static final String DONT_KEEP_CURRENT_PRICES = "N";

    public static ArrayList<String> validMonths = new ArrayList<>(); // stores all the valid months

    private static final ArrayList<String> menuOptions = new ArrayList<>(); // stores all the main menu options

    private static final ArrayList<String> summaryOptions = new ArrayList<>(); // stores all the summary options

    private static final ArrayList<String> sortOptions = new ArrayList<>(); // stores all the sort options

    private static Usage usage = new Usage();

    private final static Cost cost = new Cost();

    private final static CarbonConvertor carbonConvertor = new CarbonConvertor();


    static{
        validMonths.addAll(Arrays.asList("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY",
                "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"));
        menuOptions.addAll(Arrays.asList("Enter This Month's Usage", "View Usage Summary", "Edit Price/kwH or Price/GJ",
                "Save", "Load", "Quit"));
        summaryOptions.addAll(Arrays.asList("Monthly Overview", "View Statistics", "View Graphs", "Back"));
        sortOptions.addAll(Arrays.asList("Highest to Lowest Total Cost", "Lowest to Highest Total Cost", "Highest to Lowest Electricity Cost",
                "Lowest to Highest Electricity Cost", "Highest to Lowest Natural Gas Cost", "Lowest to Highest Natural Gas Cost", "Highest to Lowest Emission Percentage",
                "Lowest to Highest Emission Percentages" , "Quit"));
    }

    @FXML
    private Tab editPricesTab;

    @FXML
    private TextField electricityEntered;

    @FXML
    private TextField electrictyPriceEntered;

    @FXML
    private Tab enterMonthlyUsageTab;

    @FXML
    private Text leftStatusUpdate;

    @FXML
    private TextField monthEntered;

    @FXML
    private TextField naturalGasEntered;

    @FXML
    private TextField naturalGasPriceEntered;

    @FXML
    private Text rightStatusUpdate;

    @FXML
    private Tab viewOverview;

    @FXML
    public void initialize() {
        TabPane tabPane = new TabPane();
        enterMonthlyUsageTab.setClosable(false); // Don't allow user to close the tab
        tabPane.getTabs().add(enterMonthlyUsageTab); // Add tab to the tabPane
        editPricesTab.setClosable(false);
        tabPane.getTabs().add(editPricesTab);
        viewOverview.setClosable(false);
        tabPane.getTabs().add(viewOverview);
    }

    @FXML
    void onMonthEnteredClicked(ActionEvent event) {
        while (true){
            if (validMonths.contains(monthEntered.getText())) {
                if (usage.getMonths().contains(monthEntered.getText())){ // makes sure there are no duplicate months
                    rightStatusUpdate.setText("Error: You Have Already Entered this Month's bills");
                }
                else{
                    usage.addMonth(monthEntered.getText());
                    break;
                }
            }
        }
    }

    @FXML
    void onElectrictyUsedClicked(ActionEvent event) {
        while (true){
            try{
                if (Integer.parseInt(electricityEntered.getText()) >= 0){
                    usage.addElectricityUsage(Integer.parseInt(electricityEntered.getText()));
                    break;
                }
            }catch (InputMismatchException e){
                rightStatusUpdate.setText("Please enter a valid amount");
            }
        }
    }

    @FXML
    void onNaturalGasClicked(ActionEvent event) {
        while (true){
            try{
                if (Integer.parseInt(naturalGasEntered.getText()) >= 0){
                    usage.addNaturalGasUsage(Integer.parseInt(naturalGasEntered.getText()));
                    break;
                }
            }catch (InputMismatchException e){
                rightStatusUpdate.setText("Please enter a valid amount");
            }
        }
    }



}

