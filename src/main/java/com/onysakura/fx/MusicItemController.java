package com.onysakura.fx;

import com.onysakura.model.MusicLocal;
import com.onysakura.model.MusicOnline;
import com.onysakura.utils.CustomLogger;
import javafx.animation.PauseTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
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
    public ListView<GridPane> musicLocalListView;
    @FXML
    public Separator separator;
    @FXML
    private Label nameLabel;
    private MusicLocal musicLocal;
    private MusicOnline musicOnline;
    private Tooltip tooltip;
    private final IntegerProperty sequentialClickCount = new SimpleIntegerProperty(0);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tooltip = new Tooltip();
        Tooltip.install(rootPane, tooltip);
        //双击等待间隔
        PauseTransition clickTimer = new PauseTransition(Duration.millis(200));
        clickTimer.setOnFinished(this::musicNameTextClickHandler);
        rootPane.setOnMouseClicked(event -> {
            sequentialClickCount.set(sequentialClickCount.get() + 1);
            clickTimer.playFromStart();
        });
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


    public void musicNameTextClickHandler(ActionEvent event) {
//        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
//        }
        int count = sequentialClickCount.get();
        if (count == 1) {
            boolean visible = musicLocalListView.isVisible();
            musicLocalListView.setVisible(!visible);
            Integer rowIndex = GridPane.getRowIndex(separator);
            GridPane.setRowIndex(musicLocalListView, rowIndex);
            double height = 40D;
            if (visible) {
                rootPane.setPrefHeight(rootPane.getPrefHeight() - height);
                musicLocalListView.setPrefHeight(0D);
                GridPane.setRowIndex(separator, rowIndex - 1);
            } else {
                rootPane.setPrefHeight(rootPane.getPrefHeight() + height);
                musicLocalListView.setPrefHeight(height);
                GridPane.setRowIndex(separator, rowIndex + 1);
            }
        }
        if (count == 2) {
            if (musicLocal != null) {
                try {
                    Desktop.getDesktop().open(new File(musicLocal.getPath()));
                } catch (IOException e) {
                    LOG.warn("open file fail: " + e.getMessage());
                }
            }
        }
        sequentialClickCount.set(0);
    }
}
