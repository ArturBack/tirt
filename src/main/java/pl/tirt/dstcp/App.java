package pl.tirt.dstcp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.tirt.dstcp.config.StageConfiguration;
import pl.tirt.dstcp.data.DataChangeWatcher;
import pl.tirt.dstcp.data.DataUtils;
import pl.tirt.dstcp.data.service.ProcessDataService;
import pl.tirt.dstcp.view.ResourceViewLoader;

import java.io.IOException;

public class App extends Application {

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(ResourceViewLoader.loadViewFromFile("PrimaryStage.fxml")));
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
        ProcessDataService.getInstance().getAndProcessData();
    }
}
