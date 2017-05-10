package pl.tirt.dstcp.data.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artur on 2017-05-10.
 */
public class StreamStringPacket {

    private List<String> data;

    public StreamStringPacket() {
        data = new ArrayList<>();
    }

    public List<String> getData() {
        return data;
    }
}

