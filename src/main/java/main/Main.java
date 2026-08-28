package main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class help launches the houshold energy tracker program
 */
public class Main extends Application{

    /**
     * This method creates the primary stage of the program and run JavaFX
     * @param stage the primary stage for the program
     * @throws Exception thrown if there were any error while loading or finding a fxml file
     */
    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader mainViewLoader = new FXMLLoader(Main.class.getResource("/MainView.fxml"));
        Scene scene = new Scene(mainViewLoader.load());
        stage.setTitle("Household Energy Tracker");
        stage.setScene(scene);
        stage.setResizable(false); // Don't allow user to resize screen
        stage.show();
    }

}
