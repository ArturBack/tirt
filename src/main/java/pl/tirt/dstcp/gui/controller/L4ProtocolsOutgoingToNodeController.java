package pl.tirt.dstcp.gui.controller;

import pl.tirt.dstcp.data.DataUtils;
import pl.tirt.dstcp.data.model.BitsInPacketInfo;

/**
 * Created by mac on 04.06.2017.
 */
public class L4ProtocolsOutgoingToNodeController extends ProtocolsPerNode {

    @Override
    String getProtocol(BitsInPacketInfo info) {
        return info.getProtocolIV();
    }

    @Override
    boolean isSentToOrFromProperAddress(BitsInPacketInfo info) {
        return info.getSourceIP().equals(DataUtils.HOME_ADDRESS);
    }

    @Override
    protected String getIpAddress(BitsInPacketInfo info) {
        return info.getDestinationIP();
    }

    @Override
    protected String getFirstPort(BitsInPacketInfo info) {
        return info.getDestinationPort();
    }

    @Override
    protected String getSecondPort(BitsInPacketInfo info) {
        return info.getSourcePort();
    }
}
