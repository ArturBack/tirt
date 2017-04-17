package pl.tirt.dstcp.gui.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;

/**
 * Created by Dawid on 10.03.2017.
 */
public class ContentViewInitValuesProvider {

    public static ObservableList<String> getInitValues() {
        return FXCollections.observableList(Arrays.asList("Bardzo", "lubie", "kotlety", "i", "kebaby"));
    }
}
