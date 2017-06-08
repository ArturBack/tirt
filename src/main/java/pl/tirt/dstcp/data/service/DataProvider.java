package pl.tirt.dstcp.data.service;

import pl.tirt.dstcp.data.DataUtils;
import pl.tirt.dstcp.data.model.StringPacket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by AWALICZE on 14.04.2017.
 */
public class DataProvider {

    private String PACKET_BEGINING = "No.";
    private String FILE_DELIMITER = "\\s+";
    private int LAST_READ_LINE_INDEX = 0;

    private static DataProvider instance;

    public static DataProvider getInstance() {
        if(instance == null) {
            instance = new DataProvider();
        }
        return instance;
    }

    public ArrayList<StringPacket> getData() {

        try (Stream<String> lines = Files.lines(Paths.get(DataUtils.PACKETS_FILE_NAME))) {
            //we process only new lines
            Stream<String> notSkippedLines = lines.skip(LAST_READ_LINE_INDEX);
            return processData(notSkippedLines);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<StringPacket> processData(Stream<String> dataToProcess){
        ArrayList<StringPacket> data = new ArrayList<>();
        StringPacket stringPacket = null;

        Iterable<String> iterableLines = dataToProcess::iterator;
        for(String line : iterableLines){
            String[] splittedLine = line.split(FILE_DELIMITER);

            if(isLinePacketBeginning(splittedLine)) {
                if(stringPacket != null){
                    //add processed packet to data
                    data.add(stringPacket);
                }
                //create new packet for processing
                stringPacket = new StringPacket();
            } else {
                //skip empty lines
                if(!isEmptyLine(splittedLine)) {
                    stringPacket.getData().add(splittedLine);
                }
            }
            LAST_READ_LINE_INDEX++;
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
