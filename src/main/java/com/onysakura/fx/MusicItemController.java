package com.onysakura.fx;

import com.onysakura.model.MusicLocal;
import com.onysakura.utils.CustomLogger;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.File;

public class MusicItemController {

    private static final CustomLogger.Log LOG = CustomLogger.getLogger(MusicItemController.class);

    @FXML
    private Text musicNameText;
    private MusicLocal musicLocal;

    public void   setMusicLocal(MusicLocal musicLocal) {
        this.musicLocal = musicLocal;
        musicNameText.setText(musicLocal.getName());
    }

    public void musicNameTextClickHandler(MouseEvent mouseEvent) throws Exception {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                Desktop.getDesktop().open(new File(musicLocal.getPath()));
            }
        }
    }
}
