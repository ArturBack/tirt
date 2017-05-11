package pl.tirt.dstcp.data.service;

import pl.tirt.dstcp.data.DataUtils;
import pl.tirt.dstcp.data.model.StreamStringPacket;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Created by Artur on 2017-05-10.
 */
public class StreamDataService {

    private static Logger logger = Logger.getLogger(DataUtils.LOGGER_NAME);
    private static String PACKET_BEGINING = "No.";
    private static int NUMBER_OF_PACKETS_TO_STREAM = 100;
    private static int SLEEP_TIME = 1000;
    private static int LAST_SAVED_PACKET_INDEX = -1;


    public static void  startStreaming() throws InterruptedException {
        File dataSource = getDataSource(DataUtils.STREAM_PACKETS_FILE_NAME);

        Scanner scanner = null;
        try {
            scanner = new Scanner(dataSource);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(scanner != null){
            processData(scanner);
        }
    }

    private static void processData(Scanner scanner) throws InterruptedException {
        ArrayList<StreamStringPacket> data = new ArrayList<>();
        StreamStringPacket streamStringPacket = null;
        int numberOfIter = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if(isLinePacketBeginning(line)) {
               if(packetsShouldBeStreamed(numberOfIter)){
                   streamData(data);
                   Thread.sleep(SLEEP_TIME);
               }
               numberOfIter++;
               //next packet
                if(streamStringPacket != null){
                    //add previous processed packet to data
                    data.add(streamStringPacket);
                }
                //create new packet for processing
                streamStringPacket = new StreamStringPacket();
                streamStringPacket.getData().add(line);
            } else {
                //add next info about packet
                streamStringPacket.getData().add(line);
            }
        }
        streamData(data);
    }

    private static void streamData(ArrayList<StreamStringPacket> data) {
        PrintWriter fileWriter = null;
        try {
            fileWriter = new PrintWriter(new BufferedWriter(new FileWriter(DataUtils.PACKETS_FILE_NAME, true)));
            //saving last x packets

            for(int i = LAST_SAVED_PACKET_INDEX + 1; i<data.size()-1; i++){
                streamPacket(fileWriter,data.get(i));
                LAST_SAVED_PACKET_INDEX++;
            }
            logger.info("Next packtes has been streamed!");
         } catch (IOException e) {
            e.printStackTrace();
         } finally {
            if(fileWriter != null){
                fileWriter.close();
            }
        }
        ProcessDataService.getInstance().getAndProcessData();
    }

    private static void streamPacket(PrintWriter fileWriter, StreamStringPacket streamStringPacket) {
        for(String packetInfo: streamStringPacket.getData()){
            fileWriter.println(packetInfo);
        }
    }

    private static boolean packetsShouldBeStreamed(int numberOfIter) {
        return numberOfIter % (NUMBER_OF_PACKETS_TO_STREAM + 1) == 0 && numberOfIter > 0;
    }

    private static File getDataSource(String path){
        File file = new File(path);
        return file;
    }

    private static boolean isLinePacketBeginning(String line){
        return line.length()>0 && line.startsWith(PACKET_BEGINING);
    }

    private static boolean isEmptyLine(String line){
        return line.equals("");
    }

}
