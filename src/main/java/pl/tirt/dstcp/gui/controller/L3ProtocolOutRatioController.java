package pl.tirt.dstcp.gui.controller;

import pl.tirt.dstcp.data.DataUtils;
import pl.tirt.dstcp.data.model.BitsInPacketInfo;

/**
 * Created by mac on 29.05.2017.
 */
public class L3ProtocolOutRatioController extends ProtocolRatioController {

    @Override
    String getProtocol(BitsInPacketInfo info) {
        return info.getProtocolIII();
    }

    @Override
    boolean isSentToOrFromProperAddress(BitsInPacketInfo info) {
        return info.getSourceIP().equals(DataUtils.HOME_ADDRESS);
    }
}
