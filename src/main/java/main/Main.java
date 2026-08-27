package main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage stage) throws Exception{
        //Menu.mainMenu();

        FXMLLoader mainViewLoader = new FXMLLoader(Main.class.getResource("/MainView.fxml"));
        Scene scene = new Scene(mainViewLoader.load());
        stage.setTitle("Household Energy Tracker");
        stage.setScene(scene);
        stage.setResizable(false); // Don't allow user to resize screen
        stage.show();
    }

}
