package pl.tirt.dstcp.handler;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * Created by Dawid on 10.03.2017.
 */
public class ContentListViewHandler {

    public static EventHandler getPaneLabelChangeHandler(ListView listView, Label label) {
        return event -> label.setText(listView.getSelectionModel().getSelectedItem().toString());
    }
}
