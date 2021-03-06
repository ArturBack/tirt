package pl.tirt.dstcp.data.model;

import java.io.Serializable;

/**
 * Created by AWALICZE on 18.04.2017.
 */
public class BitsInPacketInfo implements Serializable {

    private int id;
    private int bitsCount;
    private String timestamp;

    private String sourceMac;
    private String destinationMac;

    private String sourceIP;
    private String destinationIP;

    private String sourcePort;
    private String destinationPort;

    private String protocolII;
    private String protocolIII;
    private String protocolIV;

    public BitsInPacketInfo(int id, int bitsCount, String timestamp, String sourceMac, String destinationMac, String sourceIP, String destinationIP, String sourcePort, String destinationPort, String protocolII, String protocolIII, String protocolIV) {
        this.id = id;
        this.bitsCount = bitsCount;
        this.timestamp = timestamp;
        this.sourceMac = sourceMac;
        this.destinationMac = destinationMac;
        this.sourceIP = sourceIP;
        this.destinationIP = destinationIP;
        this.sourcePort = sourcePort;
        this.destinationPort = destinationPort;
        this.protocolII = protocolII;
        this.protocolIII = protocolIII;
        this.protocolIV = protocolIV;
    }

    public BitsInPacketInfo(int id, int bitsCount, String timestamp, String sourceIP, String destinationIP, String sourcePort, String destinationPort, String protocolII, String protocolIII, String protocolIV) {
        this.id = id;
        this.bitsCount = bitsCount;
        this.timestamp = timestamp;
        this.sourceIP = sourceIP;
        this.destinationIP = destinationIP;
        this.sourcePort = sourcePort;
        this.destinationPort = destinationPort;
        this.protocolII = protocolII;
        this.protocolIII = protocolIII;
        this.protocolIV = protocolIV;
    }

    public BitsInPacketInfo(int id, int bitsCount, String timestamp, String source, String destination) {
        this.id = id;
        this.bitsCount = bitsCount;
        this.timestamp = timestamp;
        this.sourceIP = source;
        this.destinationIP = destination;
    }

    public BitsInPacketInfo(int id, int bitsCount, String timestamp) {
        this.id = id;
        this.bitsCount = bitsCount;
        this.timestamp = timestamp;
    }

    public String getSourceMac() {
        return sourceMac;
    }

    public String getDestinationMac() {
        return destinationMac;
    }

    public String getSourceIP() {
        return sourceIP;
    }

    public String getDestinationIP() {
        return destinationIP;
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

    public String getSourcePort() {
        return sourcePort;
    }

    public String getDestinationPort() {
        return destinationPort;
    }

    public String getProtocolII() {
        return protocolII;
    }

    public String getProtocolIII() {
        return protocolIII;
    }

    public String getProtocolIV() {
        return protocolIV;
    }
}
