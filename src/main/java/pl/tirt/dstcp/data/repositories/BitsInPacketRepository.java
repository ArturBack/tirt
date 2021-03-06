package pl.tirt.dstcp.data.repositories;

import pl.tirt.dstcp.data.DataUtils;
import pl.tirt.dstcp.data.model.BitsInPacketInfo;

import java.io.*;
import java.util.List;

/**
 * Created by AWALICZE on 18.04.2017.
 */
public class BitsInPacketRepository {

    private String FILENAME = "BitsInPacketInfo";

    private static BitsInPacketRepository instance;

    public static BitsInPacketRepository getInstance() {
        if(instance == null) {
            instance = new BitsInPacketRepository();
        }
        return instance;
    }

    public List<BitsInPacketInfo> getData(){
        List<BitsInPacketInfo> data = null;
        try {
            FileInputStream fin = new FileInputStream(DataUtils.DIRECTORY_PATH + FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fin);
            data = (List<BitsInPacketInfo>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public void saveData(List<BitsInPacketInfo> bitsInPacketInfos){
        List<BitsInPacketInfo> data = getData();
        if(data != null) {
            data.addAll(bitsInPacketInfos);
        }
        File file = new File(DataUtils.DIRECTORY_PATH + FILENAME);
        if(!file.exists()) {
            try {
                FileOutputStream fout = new FileOutputStream(DataUtils.DIRECTORY_PATH + FILENAME);
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                if(data != null){
                    oos.writeObject(data);
                } else {
                    oos.writeObject(bitsInPacketInfos);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
