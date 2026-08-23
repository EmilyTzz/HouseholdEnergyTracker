package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import object.CarbonConvertor;
import object.Cost;
import object.Usage;

import java.util.*;

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
                "Lowest to Highest Emission Percentages"));
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
    private Text leftStatusUpdate2;

    @FXML
    private TextField monthEntered;

    @FXML
    private TextArea monthlyUSageSummaryText;

    @FXML
    private TextField naturalGasEntered;

    @FXML
    private TextField naturalGasPriceEntered;

    @FXML
    private Text pricePerGJ;

    @FXML
    private Text pricePerKwH;

    @FXML
    private Text rightStatusUpdate;

    @FXML
    private Text rightStatusUpdate2;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab viewOverview;

    @FXML
    private ComboBox<String> sortOptionsComboBox;


    @FXML
    public void initialize() {
        enterMonthlyUsageTab.setClosable(false); // Don't allow user to close the tab
        editPricesTab.setClosable(false);
        viewOverview.setClosable(false);
        pricePerKwH.setText(Double.toString(cost.getCostPerKwH())); // Shows the starting prices
        pricePerGJ.setText(Double.toString(cost.getCostPerGJ()));
        for (int i = 0; i < sortOptions.size(); i++){
            sortOptionsComboBox.getItems().add(sortOptions.get(i));
        }
    }

    @FXML
    void onEnterMonthlyUsageClicked(ActionEvent event) {
        if (cost.getCostPerKwH() == 0 && cost.getCostPerGJ() == 0){
            tabPane.getSelectionModel().select(1); // switch to the edit prices tab
            rightStatusUpdate2.setText("Error: Please fill in your Electricity and Natural Gas Prices");
            return;
        }
        String month = monthEntered.getText().toUpperCase();
        if (!validMonths.contains(month)) {
            rightStatusUpdate.setText("Error: This Month Does Not Exist");
            return;
        }
        if (usage.getMonths().contains(month)){ // makes sure there are no duplicate months
            rightStatusUpdate.setText("Error: You Have Already Entered this Month's bills");
            return;
        }

        try{
            if (electricityEntered.getText().isBlank()) {
                rightStatusUpdate.setText("Error: Electricity Amount cannot be Empty");
                return;
            }
            if (Double.parseDouble(electricityEntered.getText()) < 0){
                rightStatusUpdate.setText("Error: Electricity Amount cannot be Negative");
                return;
            }
        }catch (NumberFormatException e){
            rightStatusUpdate.setText("Error: Please enter a valid Electricity amount");
            return;
        }
        try{
            if (naturalGasEntered.getText().isBlank()) {
                rightStatusUpdate.setText("Error: Natural Gas Amount cannot be Empty");
                return;
            }
            if (Double.parseDouble(naturalGasEntered.getText()) < 0){
                rightStatusUpdate.setText("Error: Natural Gas Amount cannot be Negative");
                return;
            }
        }catch (NumberFormatException e){
            rightStatusUpdate.setText("Error: Please enter a valid Natural Gas amount");
            return;
        }
        usage.addMonth(monthEntered.getText());
        usage.addElectricityUsage(Double.parseDouble(electricityEntered.getText()));
        usage.addNaturalGasUsage(Double.parseDouble(naturalGasEntered.getText()));
        rightStatusUpdate.setText("");
        leftStatusUpdate.setText("Successfully Added Usage Data For " + monthEntered.getText());
        estimations(Double.parseDouble(electricityEntered.getText()), Double.parseDouble(naturalGasEntered.getText()));
    }

    private void estimations(double electricity, double naturalGas){
        StringBuilder sb = new StringBuilder();
        sb.append("\nEstimated Electricity Cost: $" + cost.getElectricityCost(electricity));
        sb.append("\nEstimated CO2 Emission from Electricity used: " + carbonConvertor.getElectricityCarbonFootprint(electricity) + "kg\n");
        sb.append("Estimated Natural Gas Cost: $" + cost.getNaturalGasCost(naturalGas));
        sb.append("\nEstimated CO2 Emission from Natural Gas used: " + carbonConvertor.getNaturalGasCarbonFootprint(naturalGas) + "kg\n");
        sb.append("Total cost: $" + (cost.getTotalCost(electricity, naturalGas)));
        double totalEmission = (carbonConvertor.getElectricityCarbonFootprint(electricity) + carbonConvertor.getNaturalGasCarbonFootprint(naturalGas));
        sb.append("\nTotal CO2 Emission: " + totalEmission + " kg");
        monthlyUSageSummaryText.setText(sb.toString());
    }

    @FXML
    void onEnterNewPricesClicked(ActionEvent event) {
        double electricityPrice;
        double naturalGasPrice;
        try{
            if (electrictyPriceEntered.getText().isBlank()){
                rightStatusUpdate2.setText("Error: Electricity Price cannot be Empty");
                return;
            }
            electricityPrice = Double.parseDouble(electrictyPriceEntered.getText());
            if (electricityPrice < 0) {
                rightStatusUpdate2.setText("Error: Electricity Price cannot be Negative");
                return;
            }
        }catch (NumberFormatException e){
            rightStatusUpdate2.setText("Error: Please enter a valid Electricity Price");
            return;
        }
        try{
            if (naturalGasPriceEntered.getText().isBlank()){
                rightStatusUpdate2.setText("Error: Natural Gas Price cannot be Empty");
                return;
            }
            naturalGasPrice = Double.parseDouble(naturalGasPriceEntered.getText());
            if (naturalGasPrice < 0) {
                rightStatusUpdate2.setText("Error: Natural Gas Price cannot be Negative");
                return;
            }
        }catch (NumberFormatException e){
            rightStatusUpdate2.setText("Error: Please enter a valid Natural Gas Price");
            return;
        }
        cost.setCostPerKwH(electricityPrice);
        cost.setCostPerGJ(naturalGasPrice);
        rightStatusUpdate2.setText("");
        leftStatusUpdate2.setText("Successfully Added Prices");
        pricePerKwH.setText(Double.toString(cost.getCostPerKwH()));
        pricePerGJ.setText(Double.toString(cost.getCostPerGJ()));

    }


    @FXML
    void onFileClicked(ActionEvent event) {

    }

    @FXML
    void onLoadClicked(ActionEvent event) {

    }

    @FXML
    void onQuitClicked(ActionEvent event) {

    }

    @FXML
    void onSaveAsClicked(ActionEvent event) {

    }

    @FXML
    void onSaveClicked(ActionEvent event) {

    }

    @FXML
    void onSortOptionsClicked(ActionEvent event) {

    }

    @FXML
    void onUsageOverviewClicked(ActionEvent event) {

    }


    @FXML
    void onAboutClicked(ActionEvent event) {

    }


}

