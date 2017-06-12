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
import java.util.HashMap;
import java.util.List;

/**
 * Created by mac on 12.06.2017.
 */
public class L4BytesInPortController {

    @FXML
    private LineChart<String, Integer> bytes_per_port;

    @FXML
    private ComboBox<String> scale_type_combo;

    @FXML
    private ComboBox<String> receiving_port_combo;

    private HashMap<String, ObservableList<Integer>> bytesCount = new HashMap<>();

    private ObservableList<Integer> timeValues;
    private List<BitsInPacketInfo> data;

    private TimestampType timestampType = TimestampType.SECOND;


    @FXML
    private void initialize() {
        data = BitsInPacketRepository.getInstance().getData();
        timeValues = TimestampUtils.createTimeValues(timestampType,getTimestamps(data));
        fillPacketValues(data);
        initCheckBoxes();
        initScaleTypes();
        fillChartWithData();
    }

    private void initCheckBoxes() {
        receiving_port_combo.getItems().clear();
        receiving_port_combo.getItems().addAll(bytesCount.keySet());
        receiving_port_combo.getSelectionModel().select(0);
        receiving_port_combo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                fillChartWithData();
            }
        });
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
        bytesCount = new HashMap<>();
        bytes_per_port.getData().clear();
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
        bytes_per_port.getData().clear();
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
        String sourcePort = receiving_port_combo.getSelectionModel().getSelectedItem();
        XYChart.Series<String, Integer> protocolSerie = new XYChart.Series<>();
        protocolSerie.setName(sourcePort);
        ObservableList<Integer> bytesFromPort = bytesCount.get(sourcePort);
        for(int i = startIndex; i < timeValues.size(); i++){
            protocolSerie.getData().add(new XYChart.Data(timeValues.get(i).toString(), bytesFromPort.get(i)));
        }
        bytes_per_port.getData().add(protocolSerie);
    }

    private void fillPacketValues(List<BitsInPacketInfo> data) {
        for(BitsInPacketInfo info : data) {

            if (info.getDestinationIP().equals(DataUtils.HOME_IP_ADDRESS)) {
                Integer time = TimestampUtils.getTime(timestampType, info.getTimestamp());
                String sourcePort = info.getDestinationPort();

                for (int i = 0; i < timeValues.size(); i++) {
                    Integer timeValue = timeValues.get(i);
                    if (time <= timeValue) {

                        if (!bytesCount.containsKey(sourcePort)) {
                            bytesCount.put(sourcePort, getListWithInitValues());
                        }

                        ObservableList<Integer> protocolValues = bytesCount.get(sourcePort);
                        Integer newCount = protocolValues.get(i) + info.getBitsCount();
                        protocolValues.remove(i);
                        protocolValues.add(i, newCount);
                        break;
                    }
                }
            }
        }
    }

    private void updateProtocolCount(HashMap<String, ObservableList<Integer>> protocolsCount, int i, String protocolIV) {
        ObservableList<Integer> protocolValues = protocolsCount.get(protocolIV);
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
}
