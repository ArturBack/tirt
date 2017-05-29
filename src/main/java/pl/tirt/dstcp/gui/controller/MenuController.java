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
        BorderPane rootPane = App.getRootPane();

        FXMLLoader f = new FXMLLoader();
        try {
            AnchorPane anchorPane = f.load(new FileInputStream(new File("src/main/resources/view/TotalBytesOut.fxml")));
            rootPane.setCenter(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToBitsIn(ActionEvent event) {
        BorderPane rootPane = App.getRootPane();

        FXMLLoader f = new FXMLLoader();
        try {
            AnchorPane anchorPane = f.load(new FileInputStream(new File("src/main/resources/view/TotalBytesIn.fxml")));
            rootPane.setCenter(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToIpSourceOut(ActionEvent event) {
        BorderPane rootPane = App.getRootPane();

        FXMLLoader f = new FXMLLoader();
        try {
            AnchorPane anchorPane = f.load(new FileInputStream(new File("src/main/resources/view/IpSourcesOut.fxml")));
            rootPane.setCenter(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToIpSourceIn(ActionEvent event) {
        BorderPane rootPane = App.getRootPane();

        FXMLLoader f = new FXMLLoader();
        try {
            AnchorPane anchorPane = f.load(new FileInputStream(new File("src/main/resources/view/IpSourcesIn.fxml")));
            rootPane.setCenter(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToIpRatio(ActionEvent event) {
        BorderPane rootPane = App.getRootPane();

        FXMLLoader f = new FXMLLoader();
        try {
            AnchorPane anchorPane = f.load(new FileInputStream(new File("src/main/resources/view/IpVersionRatio.fxml")));
            rootPane.setCenter(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void switchToProtocolOutRatio(ActionEvent actionEvent) {
        BorderPane rootPane = App.getRootPane();

        FXMLLoader f = new FXMLLoader();
        try {
            AnchorPane anchorPane = f.load(new FileInputStream(new File("src/main/resources/view/ProtocolOutRatio.fxml")));
            rootPane.setCenter(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
