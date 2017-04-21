package pl.tirt.dstcp.data.model;

import java.io.Serializable;

/**
 * Created by AWALICZE on 21.04.2017.
 */
public class IpProtocolVersionInPacketInfo implements Serializable {

    private int id;
    private int ipProtocolVersion;

    public IpProtocolVersionInPacketInfo(int id, int ipProtocolVersion) {
        this.id = id;
        this.ipProtocolVersion = ipProtocolVersion;
    }

    public int getId() {
        return id;
    }

    public int getIpProtocolVersion() {
        return ipProtocolVersion;
    }
}
