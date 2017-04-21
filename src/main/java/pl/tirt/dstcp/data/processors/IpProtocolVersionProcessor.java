package pl.tirt.dstcp.data.processors;

import pl.tirt.dstcp.data.DataHelper;
import pl.tirt.dstcp.data.model.IpProtocolVersionInPacketInfo;
import pl.tirt.dstcp.data.model.StringPacket;
import pl.tirt.dstcp.data.repositories.IpProtocolVersionRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AWALICZE on 21.04.2017.
 */
public class IpProtocolVersionProcessor implements DataProcessor {

    @Override
    public void process(ArrayList<StringPacket> data) {
        ArrayList<IpProtocolVersionInPacketInfo> ipProtocolVersionInPacketInfos = new ArrayList<>();
        for(StringPacket stringPacket : data){
            int id = DataHelper.getID(stringPacket);
            int version = getIpVersionInfo(stringPacket);
            String timestamp = DataHelper.getTimestamp(stringPacket);

            ipProtocolVersionInPacketInfos.add(new IpProtocolVersionInPacketInfo(id,version,timestamp));
        }
        IpProtocolVersionRepository.getInstance().saveData(ipProtocolVersionInPacketInfos);
    }

    private int getIpVersionInfo(StringPacket stringPacket) {
        int version = -1;
        List<String[]> packetData = stringPacket.getData();
        for(String[] packetInfo: packetData){
            if(isInfoAboutIpProtocol(packetInfo)){
                version = Integer.parseInt(String.valueOf(packetInfo[3].charAt(0)));
                break;
            }
        }
        return version;
    }

    private boolean isInfoAboutIpProtocol(String[] packetInfo) {
        return packetInfo.length > 3 && packetInfo[0].equals("Internet")
                && packetInfo[1].equals("Protocol");
    }
}
