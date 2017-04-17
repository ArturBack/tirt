package pl.tirt.dstcp.data.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AWALICZE on 17.04.2017.
 */
public class StringPacket {

    private List<String[]> data;

    public StringPacket() {
        data = new ArrayList<>();
    }

    public List<String[]> getData() {
        return data;
    }
}
