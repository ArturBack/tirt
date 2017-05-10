package pl.tirt.dstcp.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.tirt.dstcp.data.service.StreamDataService;
import pl.tirt.dstcp.data.watcher.DataChangeWatcher;
import pl.tirt.dstcp.data.DataUtils;
import pl.tirt.dstcp.data.service.ProcessDataService;
import pl.tirt.dstcp.gui.config.StageConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class App extends Application {

    private static BorderPane root = new BorderPane();

    public void start(Stage primaryStage) throws Exception {

        FXMLLoader f = new FXMLLoader();
        MenuBar menuBar = f.load(new FileInputStream(new File("src/main/resources/view/Menu.fxml")));

        FXMLLoader f1 = new FXMLLoader();
        AnchorPane anchorPane = f1.load(new FileInputStream(new File("src/main/resources/view/BitsCount.fxml")));

        root.setTop(menuBar);
        root.setCenter(anchorPane);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle(StageConfiguration.PRIMARY_STAGE_TITLE);
        primaryStage.setWidth(StageConfiguration.INITIAL_STAGE_WIDTH);
        primaryStage.setHeight(StageConfiguration.INITIAL_STAGE_HEIGHT);
        primaryStage.show();

        getInitialData();
       // startStreaming();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void startStreaming() {
        Thread thread = new Thread(() -> {
                try {
                    StreamDataService.startStreaming();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        });
        thread.start();
    }

    private void getInitialData() {
        Thread thread = new Thread(() -> { ProcessDataService.getInstance().getAndProcessData(); });
        thread.start();
    }

    public static BorderPane getRootPane() {
        return root;
    }
}
