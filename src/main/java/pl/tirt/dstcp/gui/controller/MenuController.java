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
    public void switchToProtocolOutRatioL2(ActionEvent actionEvent) {
        initializeView("src/main/resources/view/L2ProtocolOutRatio.fxml");
    }

    @FXML
    public void switchToProtocolInRatioL2(ActionEvent actionEvent) {
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

    public void switchToProtocolOutRatioL3(ActionEvent actionEvent) {
        initializeView("src/main/resources/view/L3ProtocolInRatio.fxml");
    }

    public void switchToProtocolInRatioL3(ActionEvent actionEvent) {
        initializeView("src/main/resources/view/L3ProtocolOutRatio.fxml");
    }

    public void switchToProtocolOutRatioL4(ActionEvent actionEvent) {
        initializeView("src/main/resources/view/L4ProtocolOutRatio.fxml");
    }

    public void switchToProtocolInRatioL4(ActionEvent actionEvent) {
        initializeView("src/main/resources/view/L4ProtocolInRatio.fxml");
    }

    public void switchToProtocolsIncomingFromNodeLIV(ActionEvent actionEvent) {
        initializeView("src/main/resources/view/L4ProtocolsIncomingFromNode.fxml");
    }
}
