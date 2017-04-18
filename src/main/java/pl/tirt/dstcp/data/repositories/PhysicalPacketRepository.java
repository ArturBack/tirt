package pl.tirt.dstcp.data.repositories;

import pl.tirt.dstcp.data.DataUtils;
import pl.tirt.dstcp.data.model.PhysicalPacket;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AWALICZE on 18.04.2017.
 */
public class PhysicalPacketRepository {

    private String FILENAME = "physicalPackets";

    private static PhysicalPacketRepository instance;

    public static PhysicalPacketRepository getInstance() {
        if(instance == null) {
            instance = new PhysicalPacketRepository();
        }
        return instance;
    }

    public List<PhysicalPacket> getData(){
        List<PhysicalPacket> data = null;
        try {
            FileInputStream fin = new FileInputStream(DataUtils.DIRECTORY_PATH + FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fin);
            data = (List<PhysicalPacket>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public void saveData(List<PhysicalPacket> physicalPackets){
       try {
            FileOutputStream fout = new FileOutputStream(DataUtils.DIRECTORY_PATH + FILENAME);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(physicalPackets);
        } catch (Exception e) {
           e.printStackTrace();
       }
    }
}
