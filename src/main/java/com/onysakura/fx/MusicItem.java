package com.onysakura.fx;

import com.onysakura.model.MusicLocal;
import com.onysakura.utils.CustomLogger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MusicItem extends GridPane {
    private static final CustomLogger.Log LOG = CustomLogger.getLogger(MusicItem.class);

    private final Node view;
    private MusicItemController musicItemController;

    private MusicLocal musicLocal;

    public MusicItem() throws Exception {
        LOG.debug("MusicItem init");
        Path path = Paths.get("src", "main", "resources", "fx", "MusicItem.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(path.toFile().toURI().toURL());
        fxmlLoader.setControllerFactory(param -> musicItemController = new MusicItemController());
        view = fxmlLoader.load();
        getChildren().add(view);
    }

    public void setMusicLocal(MusicLocal musicLocal) {
        this.musicLocal = musicLocal;
        musicItemController.setMusicLocal(musicLocal);
    }
}
