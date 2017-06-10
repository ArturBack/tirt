package pl.tirt.dstcp.gui.controller;

import pl.tirt.dstcp.data.DataUtils;
import pl.tirt.dstcp.data.model.BitsInPacketInfo;

/**
 * Created by mac on 30.05.2017.
 */
public class L4ProtocolsIncomingFromNodeController extends ProtocolsPerNode {

    @Override
    protected String getIpAddress(BitsInPacketInfo info) {
        return info.getSourceIP();
    }

    @Override
    protected String getFirstPort(BitsInPacketInfo info) {
        return info.getSourcePort();
    }

    @Override
    protected String getSecondPort(BitsInPacketInfo info) {
        return info.getDestinationPort();
    }

    String getProtocol(BitsInPacketInfo info){
        return info.getProtocolIV();
  }

    boolean isSentToOrFromProperAddress(BitsInPacketInfo info)
    {
        return info.getDestinationIP().equals(DataUtils.HOME_ADDRESS);
    }
}
