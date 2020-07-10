package com.onysakura.fx;

import com.onysakura.model.MusicLocal;
import com.onysakura.model.MusicOnline;
import com.onysakura.utils.CustomLogger;
import javafx.animation.PauseTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class MusicItemController extends GridPane implements Initializable {

    private static final CustomLogger.Log LOG = CustomLogger.getLogger(MusicItemController.class);
    public MusicItemController musicItem;
    public GridPane rootPane;
    @FXML
    public Label artLabel;
    @FXML
    public Label countLabel;
    public ListView<GridPane> musicLocalListView;
    @FXML
    public Separator separator;
    @FXML
    private Label nameLabel;
    private final Set<MusicLocal> musicLocalList = new HashSet<>();
    private MusicOnline musicOnline;
    private ObservableList<MusicItemController> observableList;
    // 悬浮提示
    private Tooltip tooltip;
    // 右键菜单
    private final ContextMenu contextMenu = new ContextMenu();
    // 区分单双击
    private final IntegerProperty sequentialClickCount = new SimpleIntegerProperty(0);
    // 双击等待间隔
    private final PauseTransition clickTimer = new PauseTransition(Duration.millis(200));

    public MusicItemController() {
        LOG.debug("MusicItem init");
        try {
            Path path = Paths.get("src", "main", "resources", "fx", "MusicItem.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(path.toFile().toURI().toURL());
            fxmlLoader.setControllerFactory(param -> this);
            Node view = fxmlLoader.load();
            getChildren().add(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LOG.debug("init");
        // 初始化悬浮提示
        tooltip = new Tooltip();
        Tooltip.install(rootPane, tooltip);
        // 单双击计时器完成动作
        clickTimer.setOnFinished(this::musicNameTextClickHandler);
        // 右键菜单
        MenuItem copyName = new MenuItem("copy name");
        copyName.setOnAction(actionEvent -> {
            Map<DataFormat, Object> content = new HashMap<>();
            content.put(DataFormat.PLAIN_TEXT, musicOnline.getOnlineName());
            Clipboard.getSystemClipboard().setContent(content);
        });
        contextMenu.getItems().add(copyName);
    }

    public void addMusicLocal(MusicLocal musicLocal) {
        musicLocalList.add(musicLocal);
        countLabel.setText(String.valueOf(musicLocalList.size()));
        List<String> collect = musicLocalList.stream().map(MusicLocal::getPath).collect(Collectors.toList());
        tooltip.setText(musicOnline.getOnlineName() + "\n" + String.join("\n", collect));
    }

    public void setMusicOnline(MusicOnline musicOnline) {
        this.musicOnline = musicOnline;
        nameLabel.setText(musicOnline.getName());
        artLabel.setText(musicOnline.getArt());
        countLabel.setText(String.valueOf(musicLocalList.size()));
        tooltip.setText(musicOnline.getOnlineName());
    }

    public MusicOnline getMusicOnline() {
        return musicOnline;
    }

    private void musicNameTextClickHandler(ActionEvent event) {
        int count = sequentialClickCount.get();
        if (count == 1) {
            boolean visible = musicLocalListView.isVisible();
            musicLocalListView.setVisible(!visible);
            Integer rowIndex = GridPane.getRowIndex(separator);
            GridPane.setRowIndex(musicLocalListView, rowIndex);
            double height = 40D;
            if (visible) {
                // 隐藏子列表
                rootPane.setPrefHeight(rootPane.getPrefHeight() - height);
                musicLocalListView.setPrefHeight(0D);
                GridPane.setRowIndex(separator, rowIndex - 1);
            } else {
                // 显示子列表
                rootPane.setPrefHeight(rootPane.getPrefHeight() + height);
                musicLocalListView.setPrefHeight(height);
                GridPane.setRowIndex(separator, rowIndex + 1);
                // 隐藏除自己外组件的子列表
                for (MusicItemController item : observableList) {
                    if (item != musicItem) {
                        item.closeSubList();
                    }
                }
            }
        }
        if (count == 2) {
//            if (musicLocal != null) {
//                try {
//                    Desktop.getDesktop().open(new File(musicLocal.getPath()));
//                } catch (IOException e) {
//                    LOG.warn("open file fail: " + e.getMessage());
//                }
//            }
        }
        sequentialClickCount.set(0);
    }

    public void onMouseClickedHandler(MouseEvent mouseEvent) {
        if (MouseButton.PRIMARY.equals(mouseEvent.getButton())) {
            contextMenu.hide();
            sequentialClickCount.set(sequentialClickCount.get() + 1);
            clickTimer.playFromStart();
        } else if (MouseButton.SECONDARY.equals(mouseEvent.getButton())) {
            contextMenu.show(rootPane, mouseEvent.getScreenX(), mouseEvent.getScreenY());
        }
    }

    public void closeSubList() {
        boolean visible = musicLocalListView.isVisible();
        Integer rowIndex = GridPane.getRowIndex(separator);
        double height = 40D;
        if (visible) {
            // 隐藏
            GridPane.setRowIndex(musicLocalListView, rowIndex);
            rootPane.setPrefHeight(rootPane.getPrefHeight() - height);
            musicLocalListView.setPrefHeight(0D);
            GridPane.setRowIndex(separator, rowIndex - 1);
            musicLocalListView.setVisible(false);
        }
    }

    public void setObservableList(ObservableList<MusicItemController> observableList) {
        this.observableList = observableList;
    }
}
