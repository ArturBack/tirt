package pl.tirt.dstcp.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import pl.tirt.dstcp.gui.App;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MenuController {

    @FXML
    void switchToBitsOut(ActionEvent event) {
        initializeView("src/main/resources/view/TotalBytesOut.fxml");
    }

    @FXML
    void switchToBitsIn(ActionEvent event) {
        initializeView("src/main/resources/view/TotalBytesIn.fxml");
    }

    @FXML
    void switchToIpSourceOut(ActionEvent event) {
        initializeView("src/main/resources/view/IpSourcesOut.fxml");
    }

    @FXML
    void switchToIpSourceIn(ActionEvent event) {
        initializeView("src/main/resources/view/IpSourcesIn.fxml");
    }

    @FXML
    void switchToIpRatio(ActionEvent event) {
        initializeView("src/main/resources/view/IpVersionRatio.fxml");
    }

    @FXML
    public void switchToProtocolOutRatio(ActionEvent actionEvent) {
        initializeView("src/main/resources/view/L2ProtocolOutRatio.fxml");
    }

    @FXML
    public void switchToProtocolInRatio(ActionEvent actionEvent) {
        initializeView("src/main/resources/view/L2ProtocolInRatio.fxml");
    }

    private void initializeView(String filename) {

        BorderPane rootPane = App.getRootPane();

        FXMLLoader f = new FXMLLoader();
        try {
            AnchorPane anchorPane = f.load(new FileInputStream(new File(filename)));
            rootPane.setCenter(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
