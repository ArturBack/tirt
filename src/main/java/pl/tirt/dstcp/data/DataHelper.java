package pl.tirt.dstcp.data;

import pl.tirt.dstcp.data.model.StringPacket;

/**
 * Created by AWALICZE on 18.04.2017.
 */
public class DataHelper {

    public static int getID(StringPacket stringPacket){
        return Integer.parseInt(stringPacket.getData().get(0)[1]);
    }

    public static String getTimestamp(StringPacket stringPacket){
        return stringPacket.getData().get(0)[2];
    }
}
