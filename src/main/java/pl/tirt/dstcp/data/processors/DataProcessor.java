package pl.tirt.dstcp.data.processors;

import pl.tirt.dstcp.data.model.StringPacket;

import java.util.ArrayList;

/**
 * Created by AWALICZE on 14.04.2017.
 */
public interface DataProcessor {
    void process(ArrayList<StringPacket> data);
}
