package pl.tirt.dstcp.data.model;

import java.io.Serializable;

/**
 * Created by AWALICZE on 18.04.2017.
 */
public class BitsInPacketInfo implements Serializable {

    private int id;
    private int bitsCount;
    private String timestamp;

    public BitsInPacketInfo(int id, int bitsCount, String timestamp) {
        this.id = id;
        this.bitsCount = bitsCount;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }
    public int getBitsCount() {
        return bitsCount;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
