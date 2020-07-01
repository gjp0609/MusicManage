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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.File;
import java.util.List;

public class AppController {
    private static final CustomLogger.Log LOG = CustomLogger.getLogger(AppController.class);
    @FXML
    public TextField filePathTextField;
    @FXML
    public ListView<MusicItem> list;
    ObservableList<MusicItem> observableList = FXCollections.observableArrayList();

//    Service<Integer> service = new Service<>() {
//        @Override
//        protected Task<Integer> createTask() {
//            return new Task<>() {
//                @Override
//                protected Integer call() throws Exception {
//
//                    return null;
//                }
//            };
//        }
//    };

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
