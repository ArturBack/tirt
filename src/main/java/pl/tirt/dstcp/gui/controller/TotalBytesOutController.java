package pl.tirt.dstcp.gui.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import pl.tirt.dstcp.data.DataUtils;
import pl.tirt.dstcp.data.model.BitsInPacketInfo;
import pl.tirt.dstcp.data.repositories.BitsInPacketRepository;
import pl.tirt.dstcp.gui.utils.TimestampType;
import pl.tirt.dstcp.gui.utils.TimestampUtils;

import java.util.ArrayList;
import java.util.List;

public class TotalBytesOutController {

    @FXML
    private LineChart<String, Integer> bitsChart;

    @FXML
    private ComboBox<String> scale_type_combo;

    private ObservableList<Integer> bitsValues;
    private ObservableList<Integer> timeValues;
    private List<BitsInPacketInfo>  data;
    private TimestampType timestampType = TimestampType.SECOND;


    @FXML
    private void initialize() {
        data = BitsInPacketRepository.getInstance().getData();
        setData(data);
        initScaleTypes();
    }

    private void setData(List<BitsInPacketInfo> data){
        timeValues = TimestampUtils.createTimeValues(timestampType,getTimestamps(data));
        bitsValues = createBitsValues(data);
        fillChartWithData();
    }

    private void initScaleTypes() {
        String[] options = {TimestampType.SECOND.name(), TimestampType.MILISECOND.name()};
        scale_type_combo.getItems().addAll(options);
        //default value
        scale_type_combo.getSelectionModel().select(0);

        scale_type_combo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.equals(TimestampType.SECOND.name())){
                    setTimestampType(TimestampType.SECOND);
                    reloadData();

                } else if(newValue.equals(TimestampType.MILISECOND.name())){
                    setTimestampType(TimestampType.MILISECOND);
                    reloadData();
                }
            }
        });
    }

    private void reloadData() {
        bitsChart.getData().clear();
        setData(data);
    }

    public void setTimestampType(TimestampType timestampType) {
        this.timestampType = timestampType;
    }

    private void fillChartWithData() {
        switch(timestampType){
            case SECOND:
                fillChartWithProperData(0);
                break;
            case MILISECOND:
                fillChartWithProperData(TimestampUtils.getStartIndexOfMiliTimeValues(timeValues));
                break;
            default:
        }
    }

    private void fillChartWithProperData(int startIndex) {
        XYChart.Series series = new XYChart.Series();
        for(int i = startIndex; i < timeValues.size(); i++){
            series.getData().add(new XYChart.Data(timeValues.get(i).toString(), bitsValues.get(i)));
        }
        bitsChart.getData().add(series);
    }

    private ObservableList<Integer> createBitsValues(List<BitsInPacketInfo> data) {
        ObservableList<Integer> bitsValues = getListWithInitBitsValues();
        for(BitsInPacketInfo info : data){
            Integer time = TimestampUtils.getTime(timestampType,info.getTimestamp());
            if(info.getSourceIP().equals(DataUtils.HOME_ADDRESS)) {
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
