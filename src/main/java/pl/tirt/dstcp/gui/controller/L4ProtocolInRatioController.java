package pl.tirt.dstcp.gui.controller;

import pl.tirt.dstcp.data.DataUtils;
import pl.tirt.dstcp.data.model.BitsInPacketInfo;

/**
 * Created by mac on 30.05.2017.
 */
public class L4ProtocolInRatioController extends ProtocolRatioController {
    @Override
    String getProtocol(BitsInPacketInfo info) {
        return info.getProtocolIV();
    }

    @Override
    boolean isSentToOrFromProperAddress(BitsInPacketInfo info) {
        return info.getDestinationIP().equals(DataUtils.HOME_IP_ADDRESS);
    }
}
