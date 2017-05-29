package pl.tirt.dstcp.gui.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import pl.tirt.dstcp.data.model.BitsInPacketInfo;
import pl.tirt.dstcp.data.repositories.BitsInPacketRepository;
import pl.tirt.dstcp.gui.utils.TimestampType;
import pl.tirt.dstcp.gui.utils.TimestampUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mac on 28.05.2017.
 */
public abstract class ProtocolRatioController {

    @FXML
    private StackedBarChart<String, Integer> protocolChart;

    @FXML
    private ComboBox<String> scale_type_combo;

    private HashMap<String, ObservableList<Integer>> protocolsCount = new HashMap<>();

    private ObservableList<Integer> timeValues;
    private List<BitsInPacketInfo> data;

    private TimestampType timestampType = TimestampType.SECOND;


    @FXML
    private void initialize() {
        data = BitsInPacketRepository.getInstance().getData();
        setData(data);
        initScaleTypes();
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
        protocolsCount = new HashMap<>();
        protocolChart.getData().clear();
        setData(data);
    }

    public void setTimestampType(TimestampType timestampType) {
        this.timestampType = timestampType;
    }

    private void setData(List<BitsInPacketInfo> data){
        timeValues = TimestampUtils.createTimeValues(timestampType,getTimestamps(data));
        fillPacketValues(data);
        fillChartWithData();
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
        ArrayList<XYChart.Series<String, Integer>> protocolSeries = new ArrayList<>();
        for (String protocolName : protocolsCount.keySet()) {
            XYChart.Series<String, Integer> protocolSerie = new XYChart.Series<>();
            protocolSerie.setName(protocolName);
            ObservableList<Integer> protocolValues = protocolsCount.get(protocolName);
            for(int i = startIndex; i < timeValues.size(); i++){
                protocolSerie.getData().add(new XYChart.Data(timeValues.get(i).toString(), protocolValues.get(i)));
            }
            protocolSeries.add(protocolSerie);
        }
        protocolChart.getData().addAll(protocolSeries);
    }

    private void fillPacketValues(List<BitsInPacketInfo> data) {
        for(BitsInPacketInfo info : data) {

            if (isSentToOrFromProperAddress(info)) {
                Integer time = TimestampUtils.getTime(timestampType, info.getTimestamp());
                for (int i = 0; i < timeValues.size(); i++) {
                    Integer timeValue = timeValues.get(i);
                    String protocolName = getProtocol(info);
                    if (time <= timeValue) {

                        if (!protocolsCount.containsKey(protocolName)) {
                            protocolsCount.put(protocolName, getListWithInitValues());
                        }
                        updateProtocolCount(protocolsCount, i, protocolName);
                        break;
                    }
                }
            }
        }
    }

    private void updateProtocolCount(HashMap<String, ObservableList<Integer>> protocolsCount, int i, String protocolII) {
        ObservableList<Integer> protocolValues = protocolsCount.get(protocolII);
        Integer newCount = protocolValues.get(i) + 1;
        protocolValues.remove(i);
        protocolValues.add(i, newCount);
    }

    private ObservableList<Integer>  getListWithInitValues() {
        ObservableList<Integer> values = FXCollections.observableArrayList();
        for(int i = 0; i < timeValues.size(); i++){
            //init with 0
            values.add(0);
        }
        return values;
    }

    private List<String> getTimestamps(List<BitsInPacketInfo> data){
        List<String> timestamps = new ArrayList<>();
        for(BitsInPacketInfo info : data){
            timestamps.add(info.getTimestamp());
        }
        return timestamps;
    }

    abstract String getProtocol(BitsInPacketInfo info);

    abstract boolean isSentToOrFromProperAddress(BitsInPacketInfo info);
//    {
//        return info.getSourceIP().equals(DataUtils.HOME_ADDRESS);
//    }
}
