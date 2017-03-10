package pl.tirt.dstcp.handler;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Created by Dawid on 10.03.2017.
 */
public class ChangeColorButtonHandler {

    public static EventHandler getPaneColorChangeHandler(Pane pane, Color color) {
        return event -> pane.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
