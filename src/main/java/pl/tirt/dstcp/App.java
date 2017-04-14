package pl.tirt.dstcp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.tirt.dstcp.config.StageConfiguration;
import pl.tirt.dstcp.data.service.ProcessDataService;
import pl.tirt.dstcp.view.ResourceViewLoader;

/**
 * Created by Dawid on 10.03.2017.
 */
public class App extends Application {

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(ResourceViewLoader.loadViewFromFile("PrimaryStage.fxml")));
        primaryStage.setTitle(StageConfiguration.PRIMARY_STAGE_TITLE);
        primaryStage.setWidth(StageConfiguration.INITIAL_STAGE_WIDTH);
        primaryStage.setHeight(StageConfiguration.INITIAL_STAGE_HEIGHT);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        ProcessDataService.getInstance().getAndProcessData();
    }
}
