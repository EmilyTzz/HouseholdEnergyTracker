package main;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class MainController {

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


}

