package pl.tirt.dstcp.data.processors;

import com.sun.deploy.util.StringUtils;
import pl.tirt.dstcp.data.DataHelper;
import pl.tirt.dstcp.data.model.BitsInPacketInfo;
import pl.tirt.dstcp.data.model.StringPacket;
import pl.tirt.dstcp.data.repositories.BitsInPacketRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AWALICZE on 18.04.2017.
 */
public class BitsInPacketProcessor implements DataProcessor {

    @Override
    public void process(ArrayList<StringPacket> data) {
        ArrayList<BitsInPacketInfo> bitsInPacketInfos = new ArrayList<>();
        for(StringPacket stringPacket : data){
            int id = DataHelper.getID(stringPacket);
            String timestamp = DataHelper.getTimestamp(stringPacket);
            int bits = getBitsCountInfo(stringPacket);

            List<String[]> stringPacketData = stringPacket.getData();

            String sourceMac="";
            String destinationMac="";
            
            String sourceIP = stringPacketData.get(0)[3];
            String destinationIP = stringPacketData.get(0)[4];

            String sourcePort = null;
            String destinationPort = null;
            
            
            String protocolII = "";
            String protocolIII = stringPacketData.get(5)[2];
            String protocolIV = "";

            int size = stringPacketData.size();

            String[] layer2Line = stringPacketData.get(2);
            for(int i = 0; i < layer2Line.length; i++) {
                if(layer2Line[i].equals("Src:")) {
                    for(int j = 0; j < i; j++) {
                        protocolII += (layer2Line[j]) + " ";
                    }
                    sourceMac = layer2Line[i+2];
                    sourceMac = sourceMac.replaceAll("\\(", "");
                    sourceMac = sourceMac.replaceAll("\\),","");

                    destinationMac = layer2Line[layer2Line.length-1];
                    destinationMac = destinationMac.replaceAll("\\(", "");
                    destinationMac = destinationMac.replaceAll("\\)","");
                }
                protocolII = protocolII.replaceAll(", ", "");
            }



            if(size > 7) {
                String[] layer4Line = stringPacketData.get(7);
                for(int i = 0; i < layer4Line.length; i++) {
                    if(layer4Line[i].equals("Src")) {
                        for(int j = 0; j < i; j++) {
                            protocolIV += (layer4Line[j]) + " ";
                        }
                        protocolIV = protocolIV.replaceAll(", ", "");
                    }
                    if(layer4Line[i].equals("Dst")) {
                        sourcePort = layer4Line[i-1].replace(",","");
                        destinationPort = layer4Line[i+2].replace(",","");
                    }
                }

            }
            
            String protocol = stringPacket.getData().get(0)[5];
            System.out.println(protocol);
            

            BitsInPacketInfo bitsInPacketInfo = new BitsInPacketInfo(id,bits,timestamp, sourceMac, destinationMac, sourceIP, destinationIP, sourcePort, destinationPort, protocolII, protocolIII, protocolIV);

            bitsInPacketInfos.add(bitsInPacketInfo);
        }
        BitsInPacketRepository.getInstance().saveData(bitsInPacketInfos);
    }

    private int getBitsCountInfo(StringPacket stringPacket) {
        return Integer.parseInt(stringPacket.getData().get(1)[2]);
    }
}
