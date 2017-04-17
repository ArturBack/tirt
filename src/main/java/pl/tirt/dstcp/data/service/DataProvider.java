package pl.tirt.dstcp.data.service;

import pl.tirt.dstcp.data.DataUtils;
import pl.tirt.dstcp.data.model.StringPacket;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by AWALICZE on 14.04.2017.
 */
public class DataProvider {

    private String PACKET_BEGINING = "No.";
    public  String FILE_DELIMITER = "\\s+";

    private static DataProvider instance;

    public static DataProvider getInstance() {
        if(instance == null) {
            instance = new DataProvider();
        }
        return instance;
    }

    public ArrayList<StringPacket> getData() {
        File dataSource = getDataSource(DataUtils.DIRECTORY_PATH + DataUtils.FILE_NAME);

        Scanner scanner = null;
        try {
            scanner = new Scanner(dataSource);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(scanner != null){
            return processData(scanner);
        }
        return null;
    }

    private File getDataSource(String path){
        File file = new File(path);
        return file;
    }

    private ArrayList<StringPacket> processData(Scanner scanner){
        ArrayList<StringPacket> data = new ArrayList<>();
        StringPacket stringPacket = null;

        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] splitedLine = line.split(FILE_DELIMITER);

            if(isLinePacketBeginning(splitedLine)) {
                if(stringPacket != null){
                    //add processed packet to data
                    data.add(stringPacket);
                }
                //create new packet for processing
                stringPacket = new StringPacket();
            } else {
                //skip empty lines
                if(!isEmptyLine(splitedLine)) {
                    stringPacket.getData().add(splitedLine);
                }
            }
        }
        if(stringPacket != null){
            //add last processed packet
            data.add(stringPacket);
        }
        return data;
    }

    private boolean isLinePacketBeginning(String[] splitedLine){
        return splitedLine.length>0 && splitedLine[0].equals(PACKET_BEGINING);
    }

    private boolean isEmptyLine(String[] splitedLine){
        return splitedLine.length == 1 && splitedLine[0].equals("");
    }
}
