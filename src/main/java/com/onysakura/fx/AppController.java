package com.onysakura.fx;

import com.onysakura.constants.Constants;
import com.onysakura.model.MusicLocal;
import com.onysakura.model.MusicOnline;
import com.onysakura.repository.BaseRepository;
import com.onysakura.utils.CustomLogger;
import com.onysakura.utils.FileUtils;
import com.onysakura.utils.MusicUtils;
import com.onysakura.utils.StringUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.net.URL;
import java.text.Collator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class AppController implements Initializable {
    private static final CustomLogger.Log LOG = CustomLogger.getLogger(AppController.class);
    @FXML
    public TextField filePathTextField;
    @FXML
    public ListView<MusicItem> listView;
    @FXML
    public GridPane rootPane;
    @FXML
    public ProgressIndicator progressIndicator;
    private final ObservableList<MusicItem> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.setStyle("-fx-background-color: null");
        listView.setItems(observableList);
        progressIndicator.setVisible(true);
        listView.setDisable(true);
        Thread thread = new Thread(new Task<>() {
            @Override
            protected String call() throws Exception {
                BaseRepository<MusicOnline> musicOnlineRepository = new BaseRepository<>(MusicOnline.class);
                LinkedHashMap<String, Constants.Sort> sort = new LinkedHashMap<>();
                sort.put("art", Constants.Sort.ASC);
                sort.put("name", Constants.Sort.ASC);
                List<MusicOnline> musicOnlineList = musicOnlineRepository.selectAll(sort);
                Collator collator = Collator.getInstance(Locale.CHINA);
                musicOnlineList.sort((a, b) -> {
                    int i = collator.compare(a.getArt(), b.getArt());
                    if (i == 0) {
                        return collator.compare(a.getName(), b.getName());
                    } else {
                        return i;
                    }
                });
                observableList.clear();
                for (MusicOnline musicOnline : musicOnlineList) {
                    try {
                        MusicItem musicItem = new MusicItem();
                        musicItem.setMusicOnline(musicOnline);
                        observableList.add(musicItem);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                listView.setDisable(false);
                progressIndicator.setVisible(false);
                return null;
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void handleSubmitButtonAction(ActionEvent actionEvent) throws Exception {
        String filePath = filePathTextField.getText();
        if (FileUtils.exists(filePath)) {
            LOG.info("load local music from: " + filePath);
            List<File> fileList = FileUtils.getFileList(new File(filePath));
            LOG.info("find " + fileList.size() + " songs");
            Platform.runLater(()->{
                for (File file : fileList) {
                    MusicLocal musicLocal = MusicUtils.getMusicInfo(file);
                    if (musicLocal != null) {
                        for (MusicItem musicItem : observableList) {
                            MusicOnline musicOnline = musicItem.getMusicOnline();
                            int levenshtein = StringUtils.levenshtein(musicOnline.getOnlineName(), musicLocal.getOnlineName());
                            if (levenshtein > 80) {
                                musicItem.setMusicLocal(musicLocal);
                            }
                        }
                    }
                }
            });
        } else {
            LOG.warn("file not exists: " + filePath);
        }
    }
}
