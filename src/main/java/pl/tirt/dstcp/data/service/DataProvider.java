package pl.tirt.dstcp.data.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by AWALICZE on 14.04.2017.
 */
public class DataProvider {

    private String FILE_PATH = "C:\\tirt\\tcpdump.txt";
    private String FILE_REGEX = " ";

    private static DataProvider instance;

    public static DataProvider getInstance() {
        if(instance == null) {
            instance = new DataProvider();
        }
        return instance;
    }

    public ArrayList<String[]> getData() {
        File dataSource = getDataSource(FILE_PATH);

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

    private ArrayList<String[]> processData(Scanner scanner){
        ArrayList<String[]> data = new ArrayList<>();
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            data.add(line.split(FILE_REGEX));
        }
        return data;
    }
}
