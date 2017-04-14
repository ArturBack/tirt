package pl.tirt.dstcp.service;

import java.util.ArrayList;

/**
 * Created by AWALICZE on 14.04.2017.
 */
public class DataProvider {

    private static DataProvider instance;

    public static DataProvider getInstance() {
        if(instance == null){
            instance = new DataProvider();
            return instance;
        } else {
            return instance;
        }
    }

    public ArrayList<String[]> getData() {
        return null;
    }
}
