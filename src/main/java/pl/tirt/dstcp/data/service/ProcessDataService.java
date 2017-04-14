package pl.tirt.dstcp.data.service;

import pl.tirt.dstcp.data.processors.CompositeDataProcessor;

import java.util.ArrayList;

/**
 * Created by AWALICZE on 14.04.2017.
 */
public class ProcessDataService {

    private CompositeDataProcessor compositeDataProcessor;
    private static ProcessDataService instance;

    public static ProcessDataService getInstance(){
        if(instance == null){
            instance = new ProcessDataService();
        }
        return instance;
    }

    public ProcessDataService(){
        this.compositeDataProcessor = new CompositeDataProcessor();
    }

    public void getAndProcessData() {
        //getData to process
        ArrayList<String[]> data = DataProvider.getInstance().getData();

        //process each item of data with processor
        for(String[] item : data){
            compositeDataProcessor.process(item);
        }
    }
}
