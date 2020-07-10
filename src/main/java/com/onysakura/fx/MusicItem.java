//package com.onysakura.fx;
//
//import com.onysakura.model.MusicLocal;
//import com.onysakura.model.MusicOnline;
//import com.onysakura.utils.CustomLogger;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.layout.GridPane;
//
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//public class MusicItem extends GridPane {
//    private static final CustomLogger.Log LOG = CustomLogger.getLogger(MusicItem.class);
//
//    private MusicItemController controller;
//
//    public MusicItem() throws Exception {
//        LOG.debug("MusicItem init");
//        Path path = Paths.get("src", "main", "resources", "fx", "MusicItem.fxml");
//        FXMLLoader fxmlLoader = new FXMLLoader(path.toFile().toURI().toURL());
//        fxmlLoader.setControllerFactory(param -> controller = new MusicItemController());
//        Node view = fxmlLoader.load();
//        getChildren().add(view);
//    }
//
//    public void setMusicLocal(MusicLocal musicLocal) {
//        controller.setMusicLocal(musicLocal);
//    }
//
//    public MusicOnline getMusicOnline() {
//        return controller.getMusicOnline();
//    }
//
//    public void setMusicOnline(MusicOnline musicOnline) {
//        controller.musicItem = this;
//        controller.setMusicOnline(musicOnline);
//    }
//
//    public void setObservableList(ObservableList<MusicItem> observableList) {
//        controller.setObservableList(observableList);
//    }
//
//    public void closeSubList() {
//        boolean visible = controller.musicLocalListView.isVisible();
//        Integer rowIndex = GridPane.getRowIndex(controller.separator);
//        double height = 40D;
//        if (visible) {
//            // 隐藏
//            GridPane.setRowIndex(controller.musicLocalListView, rowIndex);
//            controller.rootPane.setPrefHeight(controller.rootPane.getPrefHeight() - height);
//            controller.musicLocalListView.setPrefHeight(0D);
//            GridPane.setRowIndex(controller.separator, rowIndex - 1);
//            controller.musicLocalListView.setVisible(false);
//        }
//    }
//}
