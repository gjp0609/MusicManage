package com.onysakura.fx;

import com.onysakura.utils.CustomLogger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.nio.file.Paths;

public class App extends Application {

    private static final CustomLogger.Log LOG = CustomLogger.getLogger(App.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        LOG.info("app start");
        Path path = Paths.get("src", "main", "resources", "fx", "App.fxml");
        Parent root = FXMLLoader.load(path.toFile().toURI().toURL());
        Scene scene = new Scene(root);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
