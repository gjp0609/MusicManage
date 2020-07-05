package com.onysakura.fx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.onysakura.model.MusicLocal;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setMusicLocal(MusicLocal musicLocal) {
        this.musicLocal = musicLocal;
        JSONObject info = JSON.parseObject(musicLocal.getInfo());
        nameLabel.setText(info.getString("name"));
        artLabel.setText(info.getString("art"));
        typeLabel.setText(musicLocal.getType().toString());

        Tooltip tooltip = new Tooltip(musicLocal.getName());
        Tooltip.install(rootPane, tooltip);
    }

    public void musicNameTextClickHandler(MouseEvent mouseEvent) throws Exception {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                Desktop.getDesktop().open(new File(musicLocal.getPath()));
            }
        }
    }
}
