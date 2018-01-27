package frame;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GameStart extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        Group frame = FXMLLoader.load(this.getClass().getResource("gameFrame.fxml"));
        Scene scene = new Scene(frame);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Rubik's cube");
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

}
