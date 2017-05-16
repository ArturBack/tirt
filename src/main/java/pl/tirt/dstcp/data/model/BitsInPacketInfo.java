package pl.tirt.dstcp.data.model;

import java.io.Serializable;

/**
 * Created by AWALICZE on 18.04.2017.
 */
public class BitsInPacketInfo implements Serializable {

    private int id;
    private int bitsCount;
    private String timestamp;

    private String source;
    private String destination;

    private String protocol;

    public BitsInPacketInfo(int id, int bitsCount, String timestamp, String source, String destination, String protocol) {
        this.id = id;
        this.bitsCount = bitsCount;
        this.timestamp = timestamp;
        this.source = source;
        this.destination = destination;
        this.protocol = protocol;
    }

    public BitsInPacketInfo(int id, int bitsCount, String timestamp, String source, String destination) {
        this.id = id;
        this.bitsCount = bitsCount;
        this.timestamp = timestamp;
        this.source = source;
        this.destination = destination;
    }

    public BitsInPacketInfo(int id, int bitsCount, String timestamp) {
        this.id = id;
        this.bitsCount = bitsCount;
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
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
