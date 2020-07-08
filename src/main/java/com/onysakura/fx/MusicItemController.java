package com.onysakura.fx;

import com.onysakura.model.MusicLocal;
import com.onysakura.model.MusicOnline;
import com.onysakura.utils.CustomLogger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MusicItemController implements Initializable {

    private static final CustomLogger.Log LOG = CustomLogger.getLogger(MusicItemController.class);
    @FXML
    public GridPane rootPane;
    @FXML
    public Label artLabel;
    @FXML
    public Label typeLabel;
    @FXML
    private Label nameLabel;
    private MusicLocal musicLocal;
    private MusicOnline musicOnline;
    private Tooltip tooltip;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tooltip = new Tooltip();
        Tooltip.install(rootPane, tooltip);
    }

    public void setMusicLocal(MusicLocal musicLocal) {
        this.musicLocal = musicLocal;
        typeLabel.setText(musicLocal.getType().toString());
        tooltip.setText(musicOnline.getOnlineName() + "\n" + musicLocal.getPath());
    }

    public void setMusicOnline(MusicOnline musicOnline) {
        this.musicOnline = musicOnline;
        nameLabel.setText(musicOnline.getName());
        artLabel.setText(musicOnline.getArt());
        typeLabel.setText(musicOnline.getHasLocalFile());
        tooltip.setText(musicOnline.getOnlineName());
    }


    public void musicNameTextClickHandler(MouseEvent mouseEvent) throws Exception {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                if (musicLocal != null) {
                    Desktop.getDesktop().open(new File(musicLocal.getPath()));
                }
            }
        }
    }
}
