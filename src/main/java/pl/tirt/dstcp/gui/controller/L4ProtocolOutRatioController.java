package pl.tirt.dstcp.gui.controller;

import pl.tirt.dstcp.data.DataUtils;
import pl.tirt.dstcp.data.model.BitsInPacketInfo;

/**
 * Created by mac on 29.05.2017.
 */
public class L4ProtocolOutRatioController extends ProtocolRatioController {

    @Override
    String getProtocol(BitsInPacketInfo info) {
        return info.getProtocolIV();
    }

    @Override
    boolean isSentToOrFromProperAddress(BitsInPacketInfo info) {
        return info.getSourceIP().equals(DataUtils.HOME_ADDRESS);
    }
}
