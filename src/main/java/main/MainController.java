package main;

import file.Reader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import object.CarbonConvertor;
import object.Cost;
import object.Usage;
import object.UsageSorter;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;

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
    private ComboBox<String> monthlySummaryComboBox;

    @FXML
    private Tab editPricesTab;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextArea textAreaEmission;

    @FXML
    private TextArea textAreaTotalCost;

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
    private TabPane tabPane;

    @FXML
    private Tab viewOverview;

    @FXML
    private ComboBox<String> sortOptionsComboBox;

    @FXML
    private PieChart totalCostPieChart;

    @FXML
    private PieChart totalEmissionPieChart;

    @FXML
    private TextArea sortedDataDisplay;

    @FXML
    private BarChart<String, Number> dataBarChart;

    @FXML
    private TextArea monthlySummaryTextArea;

    @FXML
    private Text monthChosenOnComboBox;


    @FXML
    public void initialize() {
        UsageSorter usageSorter = new UsageSorter(usage.getMonths(), usage.getElectricityUsed(), usage.getNaturalGasUsed());
        enterMonthlyUsageTab.setClosable(false); // Don't allow user to close the tab
        editPricesTab.setClosable(false);
        viewOverview.setClosable(false);
        pricePerKwH.setText(Double.toString(cost.getCostPerKwH())); // Shows the starting prices
        pricePerGJ.setText(Double.toString(cost.getCostPerGJ()));
        sortOptionsComboBox.getItems().addAll(sortOptions);
        monthlySummaryComboBox.getItems().addAll(usageSorter.getSortedMonths());
        sortSelectionHelper();
        makeOverviewDisplayInvisible();
        makeSortedDisplayInvisible();
        makeMonthlySummaryInvisible();
        monthlySummarySelectionHelper();
    }

    private void makeOverviewDisplayInvisible(){
        totalCostPieChart.setOpacity(0);
        totalEmissionPieChart.setOpacity(0);
        textAreaEmission.setOpacity(0);
        textAreaTotalCost.setOpacity(0);
    }

    private void makeOverviewDisplayVisible(){
        totalCostPieChart.setOpacity(1);
        totalEmissionPieChart.setOpacity(1);
        textAreaEmission.setOpacity(1);
        textAreaTotalCost.setOpacity(1);
    }

    private void makeSortedDisplayInvisible(){
        sortedDataDisplay.setOpacity(0);
        dataBarChart.setOpacity(0);
    }

    private void makeSortedDisplayVisible(){
        sortedDataDisplay.setOpacity(1);
        dataBarChart.setOpacity(1);
    }

    private void makeMonthlySummaryInvisible(){
        monthlySummaryTextArea.setOpacity(0);
        monthChosenOnComboBox.setOpacity(0);
    }

    private void makeMonthlySummaryVisible(){
        monthlySummaryTextArea.setOpacity(1);
        monthChosenOnComboBox.setOpacity(1);
    }

    private void monthlySummarySelectionHelper(){
        monthlySummaryComboBox.setOnAction(event -> {
            String month = monthlySummaryComboBox.getSelectionModel().getSelectedItem();
            monthChosenOnComboBox.setText("");
            monthlySummaryTextArea.setText("");
            makeOverviewDisplayInvisible();
            makeSortedDisplayInvisible();
            makeMonthlySummaryVisible();
            monthChosenOnComboBox.setText(month);
            int indexOfCurrMonth = usage.getMonths().indexOf(month);
            monthlySummaryTextArea.setText(estimations(usage.getElectricityUsed().get(indexOfCurrMonth), usage.getNaturalGasUsed().get(indexOfCurrMonth)));
            monthlySummaryTextArea.appendText(compareCarbonEmissionFromLastMonth(month));
        });
    }

    private String compareCarbonEmissionFromLastMonth(String monthEntered){
        UsageSorter usageSorter = new UsageSorter(usage.getMonths(), usage.getElectricityUsed(), usage.getNaturalGasUsed());
        usageSorter.sortInfoAccordingToMonths();
        StringBuilder sb = new StringBuilder();
        if (!usageSorter.getSortedMonths().getFirst().equals(monthEntered)){
            int indexOfMonthBefore = usageSorter.getSortedMonths().indexOf(monthEntered)-1;
            double carbonDiff = carbonConvertor.getPercentageDiffInCarbonFromLastMonth(monthEntered, usageSorter.getSortedMonths().get(indexOfMonthBefore), usageSorter.getSortedMonths(), usageSorter.getSortedElectricityUsed(), usageSorter.getSortedNaturalGasUsed());
            if (carbonDiff < 0){
                sb.append("\nThe carbon emission for this month decreased by " + carbonDiff*(-1) + "% compared to " + usageSorter.getSortedMonths().get(indexOfMonthBefore)+ "\n");
            }
            else if (carbonDiff > 0){
                sb.append("\nThe carbon emission for this month increased by " + carbonDiff + "% compared to " + usageSorter.getSortedMonths().get(indexOfMonthBefore)+ "\n");
            }
            else{
                sb.append("\nThe carbon emission for this month has not changed compared to " + usageSorter.getSortedMonths().get(indexOfMonthBefore) + "\n");
            }
        }
        return sb.toString();
    }

    private void sortSelectionHelper(){
        UsageSorter usageSorter = new UsageSorter(usage.getMonths(), usage.getElectricityUsed(), usage.getNaturalGasUsed());
        sortOptionsComboBox.setOnAction(event -> {
            makeOverviewDisplayInvisible();
            makeMonthlySummaryInvisible();
            makeSortedDisplayVisible();
            String sortOption = sortOptionsComboBox.getSelectionModel().getSelectedItem().toString();
            switch (sortOption) {
                case "Highest to Lowest Total Cost":
                    usageSorter.sortFromHighestToLowest(TOTAL_COST, cost, null);
                    sortedDataDisplay.setText(sortedDataDisplayHelper(usageSorter));
                    barChartDisplayHelper("Highest to Lowest Total Cost", usageSorter.getSortedMonths(), usageSorter.getSortedTotalCost());
                    break;
                case "Lowest to Highest Total Cost":
                    usageSorter.sortFromLowestToHighestTotalCost(cost);
                    sortedDataDisplay.setText(sortedDataDisplayHelper(usageSorter));
                    barChartDisplayHelper("Lowest to Highest Total Cost", usageSorter.getSortedMonths(), usageSorter.getSortedTotalCost());
                    break;
                case "Highest to Lowest Electricity Cost":
                    usageSorter.sortFromHighestToLowest(ELECTRICITY_COST, cost, null);
                    sortedDataDisplay.setText(sortedDataDisplayHelper(usageSorter));
                    barChartDisplayHelper("Highest to Lowest Electricity Cost", usageSorter.getSortedMonths(), usageSorter.getSortedElectricityCost());
                    break;
                case "Lowest to Highest Electricity Cost":
                    usageSorter.sortFromLowestToHighestElectricityCost(cost);
                    sortedDataDisplay.setText(sortedDataDisplayHelper(usageSorter));
                    barChartDisplayHelper("Lowest to Highest Electricity Cost", usageSorter.getSortedMonths(), usageSorter.getSortedElectricityCost());
                    break;
                case "Highest to Lowest Natural Gas Cost":
                    usageSorter.sortFromHighestToLowest(NATURAL_GAS_COST, cost, null);
                    sortedDataDisplay.setText(sortedDataDisplayHelper(usageSorter));
                    barChartDisplayHelper("Highest to Lowest Natural Gas Cost", usageSorter.getSortedMonths(), usageSorter.getSortedNaturalGasCost());
                    break;
                case "Lowest to Highest Natural Gas Cost":
                    usageSorter.sortFromLowestToHighestNaturalGasCost(cost);
                    sortedDataDisplay.setText(sortedDataDisplayHelper(usageSorter));
                    barChartDisplayHelper("Lowest to Highest Natural Gas Cost", usageSorter.getSortedMonths(), usageSorter.getSortedNaturalGasCost());
                    break;
                case "Highest to Lowest Emission Percentage":
                    usageSorter.sortFromHighestToLowest(TOTAL_EMISSION, null, carbonConvertor);
                    sortedDataDisplay.setText(sortedDataDisplayHelper(usageSorter));
                    barChartDisplayHelper("Highest to Lowest Emission Percentage", usageSorter.getSortedMonths(), usageSorter.getSortedEmission());
                    break;
                case "Lowest to Highest Emission Percentages":
                    usageSorter.sortFromLowestToHighestTotalEmission(carbonConvertor);
                    sortedDataDisplay.setText(sortedDataDisplayHelper(usageSorter));
                    barChartDisplayHelper("Lowest to Highest Emission Percentages", usageSorter.getSortedMonths(), usageSorter.getSortedEmission());
                    break;
            }
        });
    }

    private void barChartDisplayHelper(String sortOption, List<String> months, List<Double> variablesBeingCompared){
        dataBarChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(sortOption);
        for (int i = 0; i < months.size(); i ++){
            series.getData().add(new XYChart.Data<>(months.get(i), variablesBeingCompared.get(i)));
        }
        dataBarChart.getData().add(series);
    }

    @FXML
    void onEnterMonthlyUsageClicked(ActionEvent event) {
        if (cost.getCostPerKwH() == 0 && cost.getCostPerGJ() == 0){
            tabPane.getSelectionModel().select(1); // switch to the edit prices tab
            rightStatusUpdate.setText("Error: Please fill in your Electricity and Natural Gas Prices");
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
        usage.addMonth(monthEntered.getText().toUpperCase());
        usage.addElectricityUsage(Double.parseDouble(electricityEntered.getText()));
        usage.addNaturalGasUsage(Double.parseDouble(naturalGasEntered.getText()));
        rightStatusUpdate.setText("");
        leftStatusUpdate.setText("Successfully Added Usage Data For " + monthEntered.getText().toUpperCase());
        monthlyUSageSummaryText.setText(estimations(Double.parseDouble(electricityEntered.getText()), Double.parseDouble(naturalGasEntered.getText())));
        UsageSorter usageSorter = new UsageSorter(usage.getMonths(), usage.getElectricityUsed(), usage.getNaturalGasUsed());
        monthlySummaryComboBox.getSelectionModel().selectFirst();
        monthlySummaryComboBox.getItems().setAll(usageSorter.getSortedMonths());
    }

    private String sortedDataDisplayHelper(UsageSorter usageSorter){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < usageSorter.getSortedMonths().size(); i ++){
            sb.append("\n----------------------------\n");
            sb.append(usageSorter.getSortedMonths().get(i) + ":\n");
            sb.append(estimations(usageSorter.getSortedElectricityUsed().get(i), usageSorter.getSortedNaturalGasUsed().get(i)));
            sb.append("\n");
        }
        return sb.toString();
    }

    private String estimations(double electricity, double naturalGas){
        StringBuilder sb = new StringBuilder();
        sb.append("\nEstimated Electricity Cost: $" + cost.getElectricityCost(electricity) + "\n");
        sb.append("Estimated CO2 Emission from Electricity used: " + carbonConvertor.getElectricityCarbonFootprint(electricity) + "kg\n");
        sb.append("Estimated Natural Gas Cost: $" + cost.getNaturalGasCost(naturalGas) + "\n");
        sb.append("Estimated CO2 Emission from Natural Gas used: " + carbonConvertor.getNaturalGasCarbonFootprint(naturalGas) + "kg\n");
        sb.append("Total cost: $" + (cost.getTotalCost(electricity, naturalGas))+ "\n");
        double totalEmission = (carbonConvertor.getElectricityCarbonFootprint(electricity) + carbonConvertor.getNaturalGasCarbonFootprint(naturalGas));
        sb.append("Total CO2 Emission: " + totalEmission + " kg\n");
        return sb.toString();
    }


    @FXML
    void onEnterNewPricesClicked(ActionEvent event) {
        double electricityPrice;
        double naturalGasPrice;
        try{
            if (electrictyPriceEntered.getText().isBlank()){
                rightStatusUpdate.setText("Error: Electricity Price cannot be Empty");
                return;
            }
            electricityPrice = Double.parseDouble(electrictyPriceEntered.getText());
            if (electricityPrice < 0) {
                rightStatusUpdate.setText("Error: Electricity Price cannot be Negative");
                return;
            }
        }catch (NumberFormatException e){
            rightStatusUpdate.setText("Error: Please enter a valid Electricity Price");
            return;
        }
        try{
            if (naturalGasPriceEntered.getText().isBlank()){
                rightStatusUpdate.setText("Error: Natural Gas Price cannot be Empty");
                return;
            }
            naturalGasPrice = Double.parseDouble(naturalGasPriceEntered.getText());
            if (naturalGasPrice < 0) {
                rightStatusUpdate.setText("Error: Natural Gas Price cannot be Negative");
                return;
            }
        }catch (NumberFormatException e){
            rightStatusUpdate.setText("Error: Please enter a valid Natural Gas Price");
            return;
        }
        cost.setCostPerKwH(electricityPrice);
        cost.setCostPerGJ(naturalGasPrice);
        rightStatusUpdate.setText("");
        leftStatusUpdate.setText("Successfully Added Prices");
        pricePerKwH.setText(Double.toString(cost.getCostPerKwH()));
        pricePerGJ.setText(Double.toString(cost.getCostPerGJ()));

    }


    @FXML
    void onFileClicked(ActionEvent event) {

    }

    @FXML
    void onLoadClicked(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Load Usage Data");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fc.showOpenDialog(new Stage());
        if (file == null){
            showError("ERROR: No File Selected");
        }
        if (file != null){
            Reader reader = new Reader();
            usage = new Usage();
            reader.loadInfo(file, usage, cost, "N");
            if (reader.validFile){
                leftStatusUpdate.setText("Successfully Loaded " + file);
                pricePerKwH.setText(Double.toString(cost.getCostPerKwH()));
                pricePerGJ.setText(Double.toString(cost.getCostPerGJ()));
                UsageSorter usageSorter = new UsageSorter(usage.getMonths(), usage.getElectricityUsed(), usage.getNaturalGasUsed());
                monthlySummaryComboBox.getSelectionModel().selectFirst();
                monthlySummaryComboBox.getItems().setAll(usageSorter.getSortedMonths());
            }
            else{
                showError("ERROR: Invalid File");
            }
        }
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

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText("Invalid Input");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void onUsageStatisticsClicked(ActionEvent event) {
        makeSortedDisplayInvisible();
        makeOverviewDisplayVisible();
        makeMonthlySummaryInvisible();
        totalCostPieChart.setTitle("Energy Costs % of All The Months");
        totalEmissionPieChart.setTitle("Emission % of All The Months");
        addToPieChartHelper(totalCostPieChart, TOTAL_COST);
        addToPieChartHelper(totalEmissionPieChart, TOTAL_EMISSION);
        textAreaTotalCost.setText(costSummaryHelper());
        textAreaEmission.setText(emissionSummaryHelper());
    }

    private void addToPieChartHelper(PieChart pieChart, String type){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        if (type.equals(TOTAL_COST)){
            for (int i = 0; i < usage.getMonths().size(); i ++){ // Add data to the list
                pieChartData.add(new PieChart.Data(usage.getMonths().get(i), cost.getMonthlyCostPercentage(usage.getMonths().get(i), usage.getMonths(), usage.getElectricityUsed(), usage.getNaturalGasUsed())));
            }
        }
        else if (type.equals(TOTAL_EMISSION)) {
            for (int i = 0; i < usage.getMonths().size(); i++) { // Add data to the list
                pieChartData.add(new PieChart.Data(usage.getMonths().get(i), carbonConvertor.getMonthlyEmissionPercentage(usage.getMonths().get(i), usage.getMonths(), usage.getElectricityUsed(), usage.getNaturalGasUsed())));
            }
        }
        pieChart.setData(pieChartData);
    }

    private String costSummaryHelper(){
        UsageSorter usageSorter = new UsageSorter(usage.getMonths(), usage.getElectricityUsed(), usage.getNaturalGasUsed());
        StringBuilder sb = new StringBuilder();
        sb.append("Average Usage Cost : $" + cost.getAverageCost(usage.getElectricityUsed(), usage.getNaturalGasUsed()) + "\n");
        usageSorter.sortFromHighestToLowest(TOTAL_COST, cost, null); // months with highest to lowest costs
        for (int i = 0; i < usageSorter.getSortedMonths().size(); i ++){
            sb.append(usageSorter.getSortedMonths().get(i) + ": " + cost.getMonthlyCostPercentage(usageSorter.getSortedMonths().get(i), usageSorter.getSortedMonths(), usageSorter.getSortedElectricityUsed(), usageSorter.getSortedNaturalGasUsed()) + " %\n");
        }
        return sb.toString();
    }

    private String emissionSummaryHelper() {
        UsageSorter usageSorter = new UsageSorter(usage.getMonths(), usage.getElectricityUsed(), usage.getNaturalGasUsed());
        StringBuilder sb = new StringBuilder();
        sb.append("Average Usage Emissions : " + carbonConvertor.getAverageEmission(usage.getElectricityUsed(), usage.getNaturalGasUsed()) + " kg\n");
        usageSorter.sortFromHighestToLowest(TOTAL_EMISSION, cost, carbonConvertor); // months with highest to lowest emission
        for (int i = 0; i < usageSorter.getSortedMonths().size(); i ++){
            sb.append(usageSorter.getSortedMonths().get(i) + ": " + carbonConvertor.getMonthlyEmissionPercentage(usageSorter.getSortedMonths().get(i), usageSorter.getSortedMonths(), usageSorter.getSortedElectricityUsed(), usageSorter.getSortedNaturalGasUsed()) + " %\n");
        }
        return sb.toString();
    }


    @FXML
    void onAboutClicked(ActionEvent event) {

    }


}

