package pl.tirt.dstcp.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import pl.tirt.dstcp.data.model.BitsInPacketInfo;
import pl.tirt.dstcp.data.repositories.BitsInPacketRepository;
import pl.tirt.dstcp.gui.utils.TimestampType;
import pl.tirt.dstcp.gui.utils.TimestampUtils;

import java.util.ArrayList;
import java.util.List;

public class BitsChartController {

    @FXML
    private LineChart<Integer, Integer> bitsChart;

    @FXML
    private NumberAxis xAxis;

    private ObservableList<Integer> bitsValues = FXCollections.observableArrayList();
    private ObservableList<Integer> timeValues = FXCollections.observableArrayList();

    private TimestampType timestampType = TimestampType.SECOND;


    @FXML
    private void initialize() {
        List<BitsInPacketInfo> data = BitsInPacketRepository.getInstance().getData();
        timeValues = TimestampUtils.createTimeValues(timestampType,getTimestamps(data));
        bitsValues = createBitsValues(data);
        fillChartWithData();

    }

    private void fillChartWithData() {
        XYChart.Series series = new XYChart.Series();
        for(int i = 0; i < timeValues.size(); i++){
            series.getData().add(new XYChart.Data(timeValues.get(i), bitsValues.get(i)));
        }
        bitsChart.getData().add(series);
        xAxis.setTickUnit(2.0);
    }

    private ObservableList<Integer> createBitsValues(List<BitsInPacketInfo> data) {
        ObservableList<Integer> bitsValues = getListWithInitBitsValues();
        for(BitsInPacketInfo info : data){
            Integer time = TimestampUtils.getTime(timestampType,info.getTimestamp());
            for(int i = 0; i < timeValues.size(); i++){
                Integer timeValue = timeValues.get(i);
                if(time <= timeValue){
                    Integer newBitsCount = bitsValues.get(i) + info.getBitsCount();
                    bitsValues.remove(i);
                    bitsValues.add(i,newBitsCount);
                    break;
                }
            }
        }
        return bitsValues;
    }

    private ObservableList<Integer>  getListWithInitBitsValues() {
        ObservableList<Integer> bitsValues = FXCollections.observableArrayList();
        for(int i = 0; i < timeValues.size(); i++){
            //init with 0
            bitsValues.add(0);
        }
        return bitsValues;
    }

    private List<String> getTimestamps(List<BitsInPacketInfo> data){
        List<String> timestamps = new ArrayList<>();
        for(BitsInPacketInfo info : data){
            timestamps.add(info.getTimestamp());
        }
        return timestamps;
    }
    


}
