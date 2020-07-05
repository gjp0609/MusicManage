package com.onysakura.fx;

import com.onysakura.model.MusicLocal;
import com.onysakura.utils.CustomLogger;
import com.onysakura.utils.FileUtils;
import com.onysakura.utils.MusicUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AppController implements Initializable {
    private static final CustomLogger.Log LOG = CustomLogger.getLogger(AppController.class);
    @FXML
    public TextField filePathTextField;
    @FXML
    public ListView<MusicItem> list;
    @FXML
    public GridPane rootPane;
    ObservableList<MusicItem> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        list.setStyle("-fx-background-color: null");
    }

    public void handleSubmitButtonAction(ActionEvent actionEvent) throws Exception {
        observableList.clear();
        list.setItems(observableList);
        String filePath = filePathTextField.getText();
        if (FileUtils.exists(filePath)) {
            LOG.info("load local music from: " + filePath);
            List<File> fileList = FileUtils.getFileList(new File(filePath));
            LOG.info("find " + fileList.size() + " songs");
            Platform.runLater(() -> {
                for (File file : fileList) {
                    MusicLocal musicLocal = MusicUtils.getMusicInfo(file);
                    if (musicLocal != null) {
                        try {
                            MusicItem musicItem = new MusicItem();
                            musicItem.setMusicLocal(musicLocal);
                            observableList.add(musicItem);
                        } catch (Exception ignored) {
                        }
                    }
                }
            });
        } else {
            LOG.warn("file not exists: " + filePath);
        }
    }
}
