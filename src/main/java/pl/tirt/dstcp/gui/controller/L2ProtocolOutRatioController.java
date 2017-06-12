package pl.tirt.dstcp.gui.controller;

import pl.tirt.dstcp.data.DataUtils;
import pl.tirt.dstcp.data.model.BitsInPacketInfo;

/**
 * Created by mac on 29.05.2017.
 */
public class L2ProtocolOutRatioController extends ProtocolRatioController {

    @Override
    String getProtocol(BitsInPacketInfo info) {
        return info.getProtocolII();
    }

    @Override
    boolean isSentToOrFromProperAddress(BitsInPacketInfo info) {
        return info.getSourceIP().equals(DataUtils.HOME_IP_ADDRESS);
    }
}
