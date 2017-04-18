package pl.tirt.dstcp.data.processors;

import pl.tirt.dstcp.data.model.StringPacket;

import java.util.ArrayList;

/**
 * Created by AWALICZE on 14.04.2017.
 */
public class CompositeDataProcessor implements DataProcessor {

    private ArrayList<DataProcessor> dataProcessors = new ArrayList<>();

    public CompositeDataProcessor() {
       dataProcessors.add(new BitsOnWireProcessor());
    }

    @Override
    public void process(ArrayList<StringPacket> data) {
        for(DataProcessor dataProcessor : dataProcessors){
            dataProcessor.process(data);
        }
    }
}
