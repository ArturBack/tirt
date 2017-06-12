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
        initializeView("src/main/resources/view/L2TotalBytesOut.fxml");
    }

    @FXML
    void switchToBitsIn(ActionEvent event) {
        initializeView("src/main/resources/view/L2TotalBytesIn.fxml");
    }

    @FXML
    void switchToPacketsByProtocolsOutL2(ActionEvent event) {
        initializeView("src/main/resources/view/L2PacketsByProtocolOut.fxml");
    }

    @FXML
    void switchToPacketsByProtocolsInL2(ActionEvent event) {
        initializeView("src/main/resources/view/L2PacketsPerProtocolIn.fxml");
    }

    @FXML
    public void switchToProtocolOutRatioL2(ActionEvent actionEvent) {
        initializeView("src/main/resources/view/L2ProtocolOutRatio.fxml");
    }

    @FXML
    public void switchToProtocolInRatioL2(ActionEvent actionEvent) {
        initializeView("src/main/resources/view/L2ProtocolInRatio.fxml");
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

    public void switchToProtocolsOutgoingToNodeLIV(ActionEvent actionEvent) {
        initializeView("src/main/resources/view/L4ProtocolsOutgoingToNode.fxml");
    }

    public void switchToProtocolsOutgoingToNodeLIII(ActionEvent actionEvent) {
        initializeView("src/main/resources/view/L3PacketsByProtocolOut.fxml");
    }

    public void switchToProtocolsIncomingFromNodeLIII(ActionEvent actionEvent) {
        initializeView("src/main/resources/view/L3PacketsPerProtocolIn.fxml");
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
