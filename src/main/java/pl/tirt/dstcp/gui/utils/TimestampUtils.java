package pl.tirt.dstcp.gui.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Created by AWALICZE on 22.04.2017.
 */
public class TimestampUtils {

    private static int LOWEST_MILISECOND_DIFFERENCE = 100;
    private static int LOWEST_SECOND_DIFFERENCE = 1;
    private static int LAST_MILISECOND_TIME_TO_ANALYSE = 5000;

    public static ObservableList<Integer> createTimeValues(TimestampType timestampType, List<String> data){
        switch (timestampType) {
            case MILISECOND:
                return createMilisecondValues(data);
            case SECOND:
                return createSecondValues(data);
            case MINUTE:
                return createMinuteValues(data);
            default:
                return null;
        }
    }

    private static ObservableList<Integer> createSecondValues(List<String> data){
        ObservableList<Integer> timeValues = FXCollections.observableArrayList();
        int highestSecond = getSecond(data.get(data.size()-1));
        timeValues.add(LOWEST_SECOND_DIFFERENCE);
        while(timeValues.get(timeValues.size()-1) < highestSecond){
            timeValues.add(timeValues.get(timeValues.size()-1) + LOWEST_SECOND_DIFFERENCE);
        }
        return timeValues;
    }

    private static ObservableList<Integer> createMilisecondValues(List<String> data){
        ObservableList<Integer> timeValues = FXCollections.observableArrayList();
        int highestMiliTime = getMiliseconds(data.get(data.size()-1));
        timeValues.add(LOWEST_MILISECOND_DIFFERENCE);
        while(timeValues.get(timeValues.size()-1) < highestMiliTime){
            timeValues.add(timeValues.get(timeValues.size()-1) + LOWEST_MILISECOND_DIFFERENCE);
        }
        return timeValues;
    }

    private static ObservableList<Integer> createMinuteValues(List<String> data){
        return null;
    }

    private static Integer getSecond(String timestamp){
        String[] splitedTimestamp = timestamp.split("\\.");
        return Integer.parseInt(splitedTimestamp[0])+1;
    }

    private static Integer getMiliseconds(String timestamp){
        Double militime = Double.parseDouble(timestamp) * 1000;
        return militime.intValue();
    }

    private static Integer getMinute(String timestamp){
        return null;
    }

    public static Integer getTime(TimestampType timestampType, String timestamp){
        switch (timestampType) {
            case MILISECOND:
                return getMiliseconds(timestamp);
            case SECOND:
                return getSecond(timestamp);
            case MINUTE:
                return getMinute(timestamp);
            default:
                return null;
        }
    }

    public static int getStartIndexOfMiliTimeValues(ObservableList<Integer> timeValues) {
        int highestTimeValue = timeValues.get(timeValues.size()-1);
        if(highestTimeValue > LAST_MILISECOND_TIME_TO_ANALYSE){
            return timeValues.indexOf(highestTimeValue  - LAST_MILISECOND_TIME_TO_ANALYSE);
        }
        return 0;
    }
}
