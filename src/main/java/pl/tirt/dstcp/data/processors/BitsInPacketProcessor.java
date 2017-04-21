package pl.tirt.dstcp.data.processors;

import pl.tirt.dstcp.data.DataHelper;
import pl.tirt.dstcp.data.model.BitsInPacketInfo;
import pl.tirt.dstcp.data.model.StringPacket;
import pl.tirt.dstcp.data.repositories.BitsInPacketRepository;

import java.util.ArrayList;

/**
 * Created by AWALICZE on 18.04.2017.
 */
public class BitsInPacketProcessor implements DataProcessor {

    @Override
    public void process(ArrayList<StringPacket> data) {
        ArrayList<BitsInPacketInfo> bitsInPacketInfos = new ArrayList<>();
        for(StringPacket stringPacket : data){
            int id = DataHelper.getID(stringPacket);
            int bits = getBitsCountInfo(stringPacket);
            String timestamp = DataHelper.getTimestamp(stringPacket);

            bitsInPacketInfos.add(new BitsInPacketInfo(id,bits,timestamp));
        }
        BitsInPacketRepository.getInstance().saveData(bitsInPacketInfos);
    }

    private int getBitsCountInfo(StringPacket stringPacket) {
        return Integer.parseInt(stringPacket.getData().get(1)[2]);
    }
}
