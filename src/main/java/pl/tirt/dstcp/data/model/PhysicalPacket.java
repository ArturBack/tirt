package pl.tirt.dstcp.data.model;

import java.io.Serializable;

/**
 * Created by AWALICZE on 18.04.2017.
 */
public class PhysicalPacket implements Serializable {

    private int id;
    private int bits;
    private String timestamp;

    public PhysicalPacket(int id, int bits, String timestamp) {
        this.id = id;
        this.bits = bits;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }
    public int getBits() {
        return bits;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
