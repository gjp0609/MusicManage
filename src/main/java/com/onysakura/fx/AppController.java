package com.onysakura.fx;

import com.onysakura.utils.CustomLogger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class AppController {
    private static final CustomLogger.Log LOG = CustomLogger.getLogger(AppController.class);
    @FXML
    public TextField filePath;
    public ListView<TextArea> list;

    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        List<TextArea> l = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TextArea textArea = new TextArea();
            l.add(textArea);
        }
        list.setItems(FXCollections.observableList(l));
    }
}
