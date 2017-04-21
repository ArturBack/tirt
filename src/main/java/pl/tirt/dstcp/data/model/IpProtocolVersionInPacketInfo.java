package pl.tirt.dstcp.data.model;

import java.io.Serializable;

/**
 * Created by AWALICZE on 21.04.2017.
 */
public class IpProtocolVersionInPacketInfo implements Serializable {

    private int id;
    private int ipProtocolVersion;
    private String source;
    private String destination;
    private String timestamp;

    public IpProtocolVersionInPacketInfo(int id, int ipProtocolVersion, String source, String destination, String timestamp) {
        this.id = id;
        this.ipProtocolVersion = ipProtocolVersion;
        this.source = source;
        this.destination = destination;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public int getIpProtocolVersion() {
        return ipProtocolVersion;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }
}
