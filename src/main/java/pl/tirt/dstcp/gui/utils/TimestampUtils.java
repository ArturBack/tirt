package pl.tirt.dstcp.gui.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Created by AWALICZE on 22.04.2017.
 */
public class TimestampUtils {

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
        for(String timestamp : data){
            Integer second = getSecond(timestamp);
            if(!timeValues.contains(second)) {
                timeValues.add(second);
            }
        }
        return timeValues;
    }

    private static ObservableList<Integer> createMilisecondValues(List<String> data){
        return null;
    }

    private static ObservableList<Integer> createMinuteValues(List<String> data){
        return null;
    }

    private static Integer getSecond(String timestamp){
        String[] splitedTimestamp = timestamp.split("\\.");
        return Integer.parseInt(splitedTimestamp[0])+1;
    }

    private static Integer getMiliseconds(String timestamp){
        return null;
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
}
