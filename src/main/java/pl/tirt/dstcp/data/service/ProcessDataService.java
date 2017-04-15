package pl.tirt.dstcp.data.service;

import pl.tirt.dstcp.data.DataUtils;
import pl.tirt.dstcp.data.processors.CompositeDataProcessor;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by AWALICZE on 14.04.2017.
 */
public class ProcessDataService {

    private static Logger logger = Logger.getLogger(DataUtils.LOGGER_NAME);
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
        logger.info("Getting and processing data");
        //getData to process
        ArrayList<String[]> data = DataProvider.getInstance().getData();

        //process each item of data with processor
        for(String[] item : data){
            compositeDataProcessor.process(item);
        }
        logger.info("Data has been processed successfull");
    }
}
