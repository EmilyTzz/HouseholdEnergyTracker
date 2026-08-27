package main;

import file.Reader;
import file.Writer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import object.*;

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

    public static ArrayList<String> validMonths = new ArrayList<>(); // stores all the valid months

    private static final ArrayList<String> menuOptions = new ArrayList<>(); // stores all the main menu options

    private static final ArrayList<String> summaryOptions = new ArrayList<>(); // stores all the summary options

    private static final ArrayList<String> sortOptions = new ArrayList<>(); // stores all the sort options

    private static Usage usage = new Usage(); // create new usage object

    private final static Cost cost = new Cost(); // create new cost object

    private double currentAverageEmission;

    private final static CarbonConvertor carbonConvertor = new CarbonConvertor(); // create new carbon convertor object

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
    private Label monthChosenOnComboBox;

    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private HBox sortDisplayHbox;

    @FXML
    private VBox monthSummaryVbox;

    @FXML
    private HBox usageStatsHbox;

    @FXML
    private StackPane displayStackPane;

    @FXML
    private ImageView happyLogo;

    @FXML
    private ImageView sadLogo;

    @FXML
    private ImageView normalLogo;

    @FXML
    private StackPane logoStackPane;


    /**
     * Initializes the starting points of the nodes that are not static
     */
    @FXML
    public void initialize() {
        // Set All Tabs to be unclosable
        enterMonthlyUsageTab.setClosable(false); // Don't allow user to close the tab
        editPricesTab.setClosable(false);
        viewOverview.setClosable(false);
        UsageSorter usageSorter = new UsageSorter(usage.getMonths(), usage.getElectricityUsed(), usage.getNaturalGasUsed()); // create a usage sorter to add to the months combo box
        // Add Selections to the Combo boxes
        monthComboBox.getItems().addAll(validMonths);
        sortOptionsComboBox.getItems().addAll(sortOptions);
        monthlySummaryComboBox.getItems().addAll(usageSorter.getSortedMonths());
        // Set the display for the current kwH and Gj prices
        pricePerKwH.setText(Double.toString(cost.getCostPerKwH())); // Shows the starting prices
        pricePerGJ.setText(Double.toString(cost.getCostPerGJ()));
        // Calls the helper methods for combo box selections
        sortSelectionHelper();
        monthlySummarySelectionHelper();
        // Set visibility of different selections in the Overview Tabs
        displayStackPane.getChildren().clear();
        logoStackPane.getChildren().clear();
        logoStackPane.getChildren().add(normalLogo); // set default logo at beginning
    }

    /**
     * This method helps to detect the month selections made by the user from the monthly summary combo box
     */
    private void monthlySummarySelectionHelper(){
        monthlySummaryComboBox.setOnAction(event -> {
            String month = monthlySummaryComboBox.getSelectionModel().getSelectedItem(); // stores the user's selection
            monthChosenOnComboBox.setText(""); // clear the month selected previously
            monthlySummaryTextArea.setText("");
            displayStackPane.getChildren().clear();
            displayStackPane.getChildren().add(monthSummaryVbox); // have only the monthly summary GUI show on the overview tab
            monthChosenOnComboBox.setText(month);
            int indexOfCurrMonth = usage.getMonths().indexOf(month); // get the index of current month chosen to find its electricity and natural gas usage
            monthlySummaryTextArea.setText(estimations(usage.getElectricityUsed().get(indexOfCurrMonth), usage.getNaturalGasUsed().get(indexOfCurrMonth)));
            monthlySummaryTextArea.appendText(compareCarbonEmissionFromLastMonth(month));
        });
    }

    /**
     * This method helps to return the string with the carbon emission comparision between the current month and the previous month
     * @param monthEntered month chosen by the user to see its summary
     * @return String ith the carbon emission comparisons
     */
    private String compareCarbonEmissionFromLastMonth(String monthEntered){
        UsageSorter usageSorter = new UsageSorter(usage.getMonths(), usage.getElectricityUsed(), usage.getNaturalGasUsed());
        usageSorter.sortInfoAccordingToMonths(); // sort the months in the traditional monthly order
        StringBuilder sb = new StringBuilder();
        if (!usageSorter.getSortedMonths().getFirst().equals(monthEntered)){ // proceed if the month chosen is not the first month on the list
            int indexOfMonthBefore = usageSorter.getSortedMonths().indexOf(monthEntered)-1; // get index of the month chosen
            double carbonDiff = carbonConvertor.getPercentageDiffInCarbonFromLastMonth(monthEntered, usageSorter.getSortedMonths().get(indexOfMonthBefore), usageSorter.getSortedMonths(), usageSorter.getSortedElectricityUsed(), usageSorter.getSortedNaturalGasUsed());
            if (carbonDiff < 0){
                sb.append("\n✨ The carbon emission for this month decreased by " + carbonDiff*(-1) + "% compared to " + usageSorter.getSortedMonths().get(indexOfMonthBefore)+ "\n");
            }
            else if (carbonDiff > 0){
                sb.append("\n✨ The carbon emission for this month increased by " + carbonDiff + "% compared to " + usageSorter.getSortedMonths().get(indexOfMonthBefore)+ "\n");
            }
            else{
                sb.append("\n✨ The carbon emission for this month has not changed compared to " + usageSorter.getSortedMonths().get(indexOfMonthBefore) + "\n");
            }
        }
        return sb.toString();
    }

    /**
     * This method helps to detect any sort selections made by the user on the sort options combo box
     */
    private void sortSelectionHelper(){
        UsageSorter usageSorter = new UsageSorter(usage.getMonths(), usage.getElectricityUsed(), usage.getNaturalGasUsed());
        sortOptionsComboBox.setOnAction(event -> {
            displayStackPane.getChildren().clear();
            displayStackPane.getChildren().add(sortDisplayHbox); // have the sort display be the only GUI shown on the overview tab
            String sortOption = sortOptionsComboBox.getSelectionModel().getSelectedItem(); // store the user's selection
            switch (sortOption) {
                case "Highest to Lowest Total Cost":
                    usageSorter.sortFromHighestToLowest(TOTAL_COST, cost, null);
                    sortedDataDisplay.setText(sortedDataDisplayHelper(usageSorter)); // set the text area to display all the months' usage summary in the chosen sorting option
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

    /**
     * This method helps to add the sorted variables into a bar chart
     * @param sortOption sort option that the user chose
     * @param months list of all the months the user have entered
     * @param variablesBeingCompared variable that user is sorting the data by
     */
    private void barChartDisplayHelper(String sortOption, List<String> months, List<Double> variablesBeingCompared){
        dataBarChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>(); // create a category-value object
        series.setName(sortOption); // set name of bar chart to the sorting option
        for (int i = 0; i < months.size(); i ++){ // interate through all the months and the list of the variables being compared by
            series.getData().add(new XYChart.Data<>(months.get(i), variablesBeingCompared.get(i))); // add the month as the category and the variables as the values to the chart
        }
        dataBarChart.getData().add(series); // add the data to the bar chart
    }

    /**
     * This method helps to keep track of the month, electricity and natural gas usage entered by the user
     * and store them to their inidividual lists
     */
    @FXML
    void onEnterMonthlyUsageClicked(ActionEvent event) {
        double electricity;
        double naturalGas;
        if (cost.getCostPerKwH() == 0 && cost.getCostPerGJ() == 0){ // switch user to the edit prices tab if the prices are not entered yet
            tabPane.getSelectionModel().select(1); // switch to the edit prices tab
            rightStatusUpdate.setText("Error: Please fill in your Electricity and Natural Gas Prices");
            return;
        }
        String month = monthComboBox.getSelectionModel().getSelectedItem();
        if (month == null) {
            rightStatusUpdate.setText("Error: Please Select A Month");
            return;
        }
        if (usage.getMonths().contains(month)){ // makes sure there are no duplicate months
            rightStatusUpdate.setText("Error: You Have Already Entered this Month's bills");
            return;
        }

        try{
            electricity = Double.parseDouble(electricityEntered.getText());
            if (electricityEntered.getText().isBlank()) {
                rightStatusUpdate.setText("Error: Electricity Amount cannot be Empty");
                return;
            }
            if (electricity < 0){
                rightStatusUpdate.setText("Error: Electricity Amount cannot be Negative");
                return;
            }
        }catch (NumberFormatException e){
            rightStatusUpdate.setText("Error: Please enter a valid Electricity amount");
            return;
        }
        try{
            naturalGas = Double.parseDouble(naturalGasEntered.getText());
            if (naturalGasEntered.getText().isBlank()) {
                rightStatusUpdate.setText("Error: Natural Gas Amount cannot be Empty");
                return;
            }
            if (naturalGas< 0){
                rightStatusUpdate.setText("Error: Natural Gas Amount cannot be Negative");
                return;
            }
        }catch (NumberFormatException e){
            rightStatusUpdate.setText("Error: Please enter a valid Natural Gas amount");
            return;
        }
        if (!usage.getMonths().isEmpty()){
            currentAverageEmission = carbonConvertor.getAverageEmission(usage.getElectricityUsed(), usage.getNaturalGasUsed());
            changeLogo(carbonConvertor.getTotalCarbonEmission(electricity, naturalGas));
        }
        usage.addMonth(month);
        usage.addElectricityUsage(electricity);
        usage.addNaturalGasUsage(naturalGas);
        rightStatusUpdate.setText("");
        leftStatusUpdate.setText("Successfully Added Usage Data For " + month);
        monthComboBox.setValue(null); // clear selection
        // set text area
        monthlyUSageSummaryText.setText(estimations(electricity, naturalGas));
        double newAverageEmission = carbonConvertor.getAverageEmission(usage.getElectricityUsed(), usage.getNaturalGasUsed());
        monthlyUSageSummaryText.appendText(compareAverageEmission(electricity, naturalGas, newAverageEmission));
        monthlyUSageSummaryText.appendText(compareCarbonEmissionFromLastMonth(month));
        monthlyUSageSummaryText.selectRange(0,0); // Have scrollbar start at the top of the text area
        UsageSorter usageSorter = new UsageSorter(usage.getMonths(), usage.getElectricityUsed(), usage.getNaturalGasUsed());
        monthlySummaryComboBox.getSelectionModel().selectFirst();
        monthlySummaryComboBox.getItems().setAll(usageSorter.getSortedMonths());
    }


    private void changeLogo(double emission){
        if (emission > currentAverageEmission){
            logoStackPane.getChildren().clear();
            logoStackPane.getChildren().add(sadLogo); // set logo to a sad earth face if user used more natural gas than their current average
        }
        else if (emission == currentAverageEmission){
            logoStackPane.getChildren().clear();
            logoStackPane.getChildren().add(normalLogo); // set logo to a normal face if user used equal natural gas to their current average
        }
        else if (emission < currentAverageEmission){
            logoStackPane.getChildren().clear();
            logoStackPane.getChildren().add(happyLogo); // set logo to a happy face if user used less natural gas than their current average
        }
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
        double totalEmission = (carbonConvertor.getTotalCarbonEmission(electricity, naturalGas));
        sb.append("Total CO2 Emission: " + totalEmission + " kg\n");
        sb.append("\n------------Analysis------------\n");
        sb.append("\n💡 That is the same amount of energy required to power " + carbonConvertor.getEquivalentOfCO2Emission(totalEmission) + " homes for a day!");
        sb.append("\n...");
        return sb.toString();
    }

    private String compareAverageEmission(double electricity, double naturalGas, double newAverageEmission){
        StringBuilder sb = new StringBuilder();
        double emission = (carbonConvertor.getTotalCarbonEmission(electricity, naturalGas));
        if (!usage.getMonths().isEmpty()){
            if (emission > currentAverageEmission){
                sb.append("\n💡 Your total emission for this month exceeded your average emission by " + RoundingHelper.roundingHelper(emission-currentAverageEmission) + " kg");
                sb.append("\n❗ Your average emission has increased by " + RoundingHelper.roundingHelper(newAverageEmission-currentAverageEmission) + " kg");
            }
            else if (emission < currentAverageEmission){
                sb.append("\n💡 Your total emission for this month is less than your average emission by " + RoundingHelper.roundingHelper(currentAverageEmission-emission) + " kg");
                sb.append("\n😊 Your average emission has decreased by " + RoundingHelper.roundingHelper(currentAverageEmission-newAverageEmission) + " kg");
            }
        }
        sb.append("\n...");
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
            String msg = reader.loadInfo(file, usage, cost);
            if (reader.validFile){
                leftStatusUpdate.setText(msg);
                pricePerKwH.setText(Double.toString(cost.getCostPerKwH()));
                pricePerGJ.setText(Double.toString(cost.getCostPerGJ()));
                UsageSorter usageSorter = new UsageSorter(usage.getMonths(), usage.getElectricityUsed(), usage.getNaturalGasUsed());
                monthlySummaryComboBox.getSelectionModel().selectFirst();
                monthlySummaryComboBox.getItems().setAll(usageSorter.getSortedMonths());
                //monthlyUSageSummaryText.setText(sortedDataDisplayHelper(usageSorter));
            }
            else{
                showError(msg);
            }
        }
    }

    @FXML
    void onQuitClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit Application");
        alert.setHeaderText("Are you sure you want to Quit?");
        alert.setContentText("Unsaved Changes will be lost");
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.isPresent() && answer.get() == ButtonType.OK){
            System.exit(1);
        }
    }

    @FXML
    void onSaveClicked(ActionEvent event) {
        UsageSorter usageSorter = new UsageSorter(usage.getMonths(), usage.getElectricityUsed(), usage.getNaturalGasUsed());
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Usage Data");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File fileToSave = fileChooser.showSaveDialog(null);
        if (fileToSave != null){
            Writer writer = new Writer();
            if (writer.validFile){
                leftStatusUpdate.setText(writer.saveInfo(fileToSave, usageSorter, cost));
            }
            else{
                showError(writer.saveInfo(fileToSave, usageSorter, cost));
            }
        }
        else{
            showError("ERROR: File was not saved. Please select a valid file location");
        }
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
        displayStackPane.getChildren().clear();
        displayStackPane.getChildren().add(usageStatsHbox);
        if (usage.getMonths().isEmpty()){
            rightStatusUpdate.setText("No Data Here Yet...");
        }
        else{
            totalCostPieChart.setTitle("Energy Costs % of All The Months");
            totalEmissionPieChart.setTitle("Emission % of All The Months");
            addToPieChartHelper(totalCostPieChart, TOTAL_COST);
            addToPieChartHelper(totalEmissionPieChart, TOTAL_EMISSION);
            textAreaTotalCost.setText(costSummaryHelper());
            textAreaEmission.setText(emissionSummaryHelper());
        }
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Information alert window
        alert.setTitle("About My Household Tracker Application");
        alert.setHeaderText("About My Household Tracker Application");
        String content = "Author: Emily Trinh\n" + "Description: This is an application that allows users to enter their own " +
                "monthly electricity, natural gas usage, as well as the current prices they have to pay per kwH and GJ. The program would then " +
                "help users calculate their monthly usage costs, usage emission, and keep track of all their entered usage within the year. Users would" +
                " then get to see a full summary and statistics of all their monthly usage that helps them see which month they had the highest usage, emissions, costs, etc.";
        TextArea textArea = new TextArea(content);
        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.setPrefWidth(400);
        textArea.setPrefHeight(250);
        alert.getDialogPane().setContent(textArea);
        alert.show();
    }


}

