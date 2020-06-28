package com.onysakura;

import com.onysakura.utils.CustomLogger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.nio.file.Paths;

public class App extends Application {

    private static final CustomLogger.Log LOG = CustomLogger.getLogger(App.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        LOG.info("app start");
        Button btn = new Button();
        btn.setText("你好啊，世界");
        btn.setOnAction(event -> System.out.println("你好，世界!"));

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        ListView<String> list = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList(
                "Single", "Double", "Suite", "Family App");
        list.setItems(items);
        root.getChildren().add(list);

        Scene scene = new Scene(root, 300, 250);
        Path path = Paths.get("src", "main", "resources", "app.css");
        scene.getStylesheets().add(path.toUri().toURL().toExternalForm());
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
