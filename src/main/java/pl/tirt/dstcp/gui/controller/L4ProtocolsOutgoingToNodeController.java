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
}
