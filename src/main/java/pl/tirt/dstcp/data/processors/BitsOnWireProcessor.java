package pl.tirt.dstcp.data.processors;

import pl.tirt.dstcp.data.DataHelper;
import pl.tirt.dstcp.data.model.PhysicalPacket;
import pl.tirt.dstcp.data.model.StringPacket;
import pl.tirt.dstcp.data.repositories.PhysicalPacketRepository;

import java.util.ArrayList;

/**
 * Created by AWALICZE on 18.04.2017.
 */
public class BitsOnWireProcessor implements DataProcessor {

    @Override
    public void process(ArrayList<StringPacket> data) {
        ArrayList<PhysicalPacket> physicalPackets = new ArrayList<>();
        for(StringPacket stringPacket : data){
            int id = DataHelper.getID(stringPacket);
            int bits = getBitsCountInfo(stringPacket);
            String timestamp = DataHelper.getTimestamp(stringPacket);

            physicalPackets.add(new PhysicalPacket(id,bits,timestamp));
        }
        PhysicalPacketRepository.getInstance().saveData(physicalPackets);
    }

    private int getBitsCountInfo(StringPacket stringPacket) {
        return Integer.parseInt(stringPacket.getData().get(1)[2]);
    }
}
