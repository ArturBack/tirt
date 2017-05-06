package pl.tirt.dstcp.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.tirt.dstcp.gui.config.StageConfiguration;
import pl.tirt.dstcp.data.watcher.DataChangeWatcher;
import pl.tirt.dstcp.data.DataUtils;
import pl.tirt.dstcp.data.service.ProcessDataService;
import pl.tirt.dstcp.gui.view.ResourceViewLoader;

import java.io.IOException;

public class App extends Application {

    public void start(Stage primaryStage) throws Exception {
        //Scene scene = new Scene(ResourceViewLoader.loadViewFromFile("BitsCount.fxml"));
        // Scene scene = new Scene(ResourceViewLoader.loadViewFromFile("IpVersionRatio.fxml"));
        Scene scene = new Scene(ResourceViewLoader.loadViewFromFile("IpSource.fxml"));
        primaryStage.setScene(scene);
        primaryStage.setTitle(StageConfiguration.PRIMARY_STAGE_TITLE);
        primaryStage.setWidth(StageConfiguration.INITIAL_STAGE_WIDTH);
        primaryStage.setHeight(StageConfiguration.INITIAL_STAGE_HEIGHT);
        primaryStage.show();

        getInitialData();
        registerDataWatcher();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void registerDataWatcher() {
        Thread thread = new Thread(() -> {
                try {
                    DataChangeWatcher.watchFile(DataUtils.DIRECTORY_PATH, DataUtils.FILE_NAME);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        thread.start();
    }

    private void getInitialData() {
        Thread thread = new Thread(() -> { ProcessDataService.getInstance().getAndProcessData(); });
        thread.start();
    }
}
