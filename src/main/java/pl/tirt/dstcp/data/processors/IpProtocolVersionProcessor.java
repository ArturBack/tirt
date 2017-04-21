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
            String timestamp = DataHelper.getTimestamp(stringPacket);
            String[] ipInfo = getIpInfo(stringPacket);

            int version = -1;
            String source = "";
            String destination = "";
            if(ipInfo != null){
                version = Integer.parseInt(String.valueOf(ipInfo[3].charAt(0)));
                source = ipInfo[5].substring(0,ipInfo[5].length()-1);
                destination = ipInfo[7];
            }
            ipProtocolVersionInPacketInfos.add(new IpProtocolVersionInPacketInfo(id,version,source,destination,timestamp));
        }
        IpProtocolVersionRepository.getInstance().saveData(ipProtocolVersionInPacketInfos);
    }

    private String[] getIpInfo(StringPacket stringPacket) {
        List<String[]> packetData = stringPacket.getData();
        for(String[] packetInfo: packetData){
            if(isInfoAboutIpProtocol(packetInfo)){
                return packetInfo;
            }
        }
        return null;
    }

    private boolean isInfoAboutIpProtocol(String[] packetInfo) {
        return packetInfo.length > 3 && packetInfo[0].equals("Internet")
                && packetInfo[1].equals("Protocol");
    }
}
