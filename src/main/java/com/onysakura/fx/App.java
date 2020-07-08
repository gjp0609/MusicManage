package com.onysakura.fx;

import com.onysakura.utils.CustomLogger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App extends Application {

    private static final CustomLogger.Log LOG = CustomLogger.getLogger(App.class);
    private static final boolean USE_JMETRO = false;
    private static final int BLUR_PIXEL = 200;
    private GridPane rootPane;
    private WritableImage snapshot;
    private Stage stage;
    private int screenWidth;
    private int screenHeight;

    @Override
    public void start(Stage stage) throws Exception {
        LOG.info("app start");
        Rectangle2D screenRectangle = Screen.getPrimary().getBounds();
        screenWidth = (int) screenRectangle.getWidth();
        screenHeight = (int) screenRectangle.getHeight();
        LOG.info("windows width: " + screenWidth + ", height: " + screenHeight);
        Path path = Paths.get("src", "main", "resources", "fx", "App.fxml");
        FXMLLoader loader = new FXMLLoader(path.toFile().toURI().toURL());
        Parent layout = loader.load();
        AppController appController = loader.getController();
        this.stage = stage;
        rootPane = appController.rootPane;
        Scene scene = new Scene(layout, 300, 500, Color.TRANSPARENT);
        // Platform.setImplicitExit(false);
        if (USE_JMETRO) {
            createScreenCapture();
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
        }
        stage.setTitle("Hello World!");
        stage.setScene(scene);
        stage.setMinWidth(300D);
        stage.setMinHeight(400D);
        stage.show();
        if (USE_JMETRO) {
            setBackground(false);
            handleStageMove(stage);
        }
    }

    public void handleStageMove(Stage stage) {
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> setBackground(false);
        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);
        stage.xProperty().addListener(stageSizeListener);
        stage.yProperty().addListener(stageSizeListener);
        stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                stage.setOpacity(0);
                setBackground(true);
                stage.setOpacity(1);
            }
        });
    }

    public void createScreenCapture() {
        try {
            Robot robot = new Robot();
            java.awt.image.BufferedImage image = robot.createScreenCapture(new java.awt.Rectangle(0, 0, screenWidth, screenHeight));
            WritableImage writableImage = SwingFXUtils.toFXImage(image, null);
            ImageView imageView = new ImageView();
            imageView.setImage(writableImage);
            BoxBlur boxBlur = new BoxBlur(BLUR_PIXEL, BLUR_PIXEL, 3);
            imageView.setEffect(boxBlur);
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(0.3);
            boxBlur.setInput(colorAdjust);
            SnapshotParameters parameters = new SnapshotParameters();
            snapshot = imageView.snapshot(parameters, writableImage);
//            LOG.debug("snapshot width:" + snapshot.getWidth() + ", height: " + snapshot.getHeight());
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void setBackground(boolean refreshBackground) {
        if (snapshot == null || refreshBackground) {
            createScreenCapture();
        }
        try {
            int x, y, w, h;
            x = (int) stage.getX() + BLUR_PIXEL;
            y = (int) stage.getY() + 20 + BLUR_PIXEL;
            w = (int) stage.getWidth();
            h = (int) stage.getHeight();

            if (x >= 0 && y >= 0 && x < screenWidth - w && y < screenHeight - h) {
//                LOG.debug("window position: x:" + x + ", y:" + y + ", w:" + w + ", h:" + h);
                WritableImage newImage = new WritableImage(snapshot.getPixelReader(), x, y, w, h);
                BackgroundImage backgroundImage = new BackgroundImage(newImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
                Background background = new Background(backgroundImage);
                rootPane.setBackground(background);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
