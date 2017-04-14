package pl.tirt.dstcp.service;

import pl.tirt.dstcp.processors.CompositeDataProcessor;

import java.util.ArrayList;

/**
 * Created by AWALICZE on 14.04.2017.
 */
public class DataService {

    private CompositeDataProcessor compositeDataProcessor;

    public DataService(CompositeDataProcessor compositeDataProcessor){
        this.compositeDataProcessor = compositeDataProcessor;
    }

    public void run() {
        //getData to process
        ArrayList<String[]> data = DataProvider.getInstance().getData();

        for(String[] item : data){
            compositeDataProcessor.process(item);
        }
    }
}
