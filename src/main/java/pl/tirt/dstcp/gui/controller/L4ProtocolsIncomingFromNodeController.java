package pl.tirt.dstcp.gui.controller;

import pl.tirt.dstcp.data.DataUtils;
import pl.tirt.dstcp.data.model.BitsInPacketInfo;

/**
 * Created by mac on 30.05.2017.
 */
public class L4ProtocolsIncomingFromNodeController extends ProtocolsPerNode {

    String getProtocol(BitsInPacketInfo info){
        return info.getProtocolIV();
  }

    boolean isSentToOrFromProperAddress(BitsInPacketInfo info)
    {
        return info.getDestinationIP().equals(DataUtils.HOME_ADDRESS);
    }
}
