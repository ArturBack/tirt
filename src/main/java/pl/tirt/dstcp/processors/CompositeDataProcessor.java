package pl.tirt.dstcp.processors;

import java.util.ArrayList;

/**
 * Created by AWALICZE on 14.04.2017.
 */
public class CompositeDataProcessor implements DataProcessor {

    private ArrayList<DataProcessor> dataProcessors = new ArrayList<>();

    public CompositeDataProcessor() {

    }

    @Override
    public void process(String[] data) {
        for(DataProcessor dataProcessor : dataProcessors){
            dataProcessor.process(data);
        }
    }
}
