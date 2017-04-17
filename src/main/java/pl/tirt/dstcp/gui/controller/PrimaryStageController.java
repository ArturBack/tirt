package pl.tirt.dstcp.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import pl.tirt.dstcp.gui.handler.ChangeColorButtonHandler;
import pl.tirt.dstcp.gui.handler.ContentListViewHandler;
import pl.tirt.dstcp.gui.service.ContentViewInitValuesProvider;

/**
 * Created by Dawid on 10.03.2017.
 */

public class PrimaryStageController {

    @FXML
    public Button turnRedButton;

    @FXML
    public Button turnGreenButton;

    @FXML
    public Button turnBlueButton;

    @FXML
    public ListView contentList;

    @FXML
    public Pane contentPane;

    @FXML
    public Label contentLabel;

    @FXML
    public void initialize() {
        turnRedButton.setOnMouseClicked(ChangeColorButtonHandler.getPaneColorChangeHandler(contentPane, Color.RED));
        turnGreenButton.setOnMouseClicked(ChangeColorButtonHandler.getPaneColorChangeHandler(contentPane, Color.GREEN));
        turnBlueButton.setOnMouseClicked(ChangeColorButtonHandler.getPaneColorChangeHandler(contentPane, Color.BLUE));
        contentList.setItems(ContentViewInitValuesProvider.getInitValues());
        contentList.setOnMouseClicked(ContentListViewHandler.getPaneLabelChangeHandler(contentList, contentLabel));
    }

}
