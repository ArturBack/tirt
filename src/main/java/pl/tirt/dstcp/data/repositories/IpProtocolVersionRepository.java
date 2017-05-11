package pl.tirt.dstcp.data.repositories;

import pl.tirt.dstcp.data.DataUtils;
import pl.tirt.dstcp.data.model.IpProtocolVersionInPacketInfo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by AWALICZE on 21.04.2017.
 */
public class IpProtocolVersionRepository {

    private String FILENAME = "IpProtocolVersionInfo";

    private static IpProtocolVersionRepository instance;

    public static IpProtocolVersionRepository getInstance() {
        if(instance == null) {
            instance = new IpProtocolVersionRepository();
        }
        return instance;
    }

    public List<IpProtocolVersionInPacketInfo> getData(){
        List<IpProtocolVersionInPacketInfo> data = null;
        try {
            FileInputStream fin = new FileInputStream(DataUtils.DIRECTORY_PATH + FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fin);
            data = (List<IpProtocolVersionInPacketInfo>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public void saveData(List<IpProtocolVersionInPacketInfo> ipProtocolVersionInPacketInfos){
        List<IpProtocolVersionInPacketInfo> data = getData();
        if(data != null) {
            data.addAll(ipProtocolVersionInPacketInfos);
        }
        try {
            FileOutputStream fout = new FileOutputStream(DataUtils.DIRECTORY_PATH + FILENAME);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            if(data != null){
                oos.writeObject(data);
            } else {
                oos.writeObject(ipProtocolVersionInPacketInfos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
