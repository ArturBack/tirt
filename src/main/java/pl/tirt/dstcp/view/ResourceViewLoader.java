package pl.tirt.dstcp.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Created by Dawid on 10.03.2017.
 */
public abstract class ResourceViewLoader {

    public static final String VIEW_RESOURCE_PATH = "/view/";

    public static Parent loadViewFromFile(String filename) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ResourceViewLoader.class.getResource(VIEW_RESOURCE_PATH + filename));
        return loader.load();
    }
}
