package main;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainController {

    @FXML
    private Tab editPricesTab;

    @FXML
    private Tab enterMonthlyUsageTab;

    @FXML
    private Tab viewOverview = new Tab("View Overview");;

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

