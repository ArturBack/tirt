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
 * Created by mac on 04.06.2017.
 */
public abstract class ProtocolsPerNode {

    @FXML
    private StackedBarChart<String, Integer> protocol_chart;

    @FXML
    private ComboBox<String> ip_combo;

    @FXML
    private ComboBox<String> first_port_combo;

    @FXML
    private ComboBox<String> second_port_combo;

    @FXML
    private ComboBox<String> scale_type_combo;

    private HashMap<String, HashMap<String, HashMap<String, HashMap<String, ObservableList<Integer>>>>> ipCount = new HashMap<>();
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
        ip_combo.getItems().clear();
        ip_combo.getItems().addAll(ipCount.keySet());
        ip_combo.getSelectionModel().select(0);
        ip_combo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    HashMap<String, HashMap<String, HashMap<String, ObservableList<Integer>>>> map = ipCount.get(newValue);
                    updateSourcePortCombo(map);

                }
            }
        });
        String val = ip_combo.getSelectionModel().getSelectedItem();
        HashMap<String, HashMap<String, HashMap<String, ObservableList<Integer>>>> map = ipCount.get(val);
        setSourcePortCombo(map);
    }


    private void setSourcePortCombo(HashMap<String, HashMap<String, HashMap<String, ObservableList<Integer>>>> sourcePortCount) {
        updateSourcePortCombo(sourcePortCount);
        first_port_combo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    updateDestinationPortCombo(ipCount.get(ip_combo.getSelectionModel().getSelectedItem()).get(newValue));
                }
            }
        });
        setDestinationPortCombo(sourcePortCount.get(first_port_combo.getSelectionModel().getSelectedItem()));
    }

    private void updateSourcePortCombo(HashMap<String, HashMap<String, HashMap<String, ObservableList<Integer>>>> sourcePortCount) {
        first_port_combo.getItems().clear();
        first_port_combo.getItems().addAll(sourcePortCount.keySet());
        first_port_combo.getSelectionModel().select(0);
    }

    private void setDestinationPortCombo(HashMap<String, HashMap<String, ObservableList<Integer>>> destinationPortCount) {
        updateDestinationPortCombo(destinationPortCount);
        second_port_combo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    reloadData();
                }
            }
        });
    }

    private void updateDestinationPortCombo(HashMap<String, HashMap<String, ObservableList<Integer>>> destinationPortCount) {
        second_port_combo.getItems().clear();
        second_port_combo.getItems().addAll(destinationPortCount.keySet());
        second_port_combo.getSelectionModel().select(0);
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
                    clearAndReloadData();

                } else if(newValue.equals(TimestampType.MILISECOND.name())){
                    setTimestampType(TimestampType.MILISECOND);
                    clearAndReloadData();
                }
            }
        });
    }

    private void reloadData() {
        fillChartWithData();
    }

    private void clearAndReloadData() {
        ipCount = new HashMap<>();
        setData(data);
    }

    private void setData(List<BitsInPacketInfo> data){
        timeValues = TimestampUtils.createTimeValues(timestampType,getTimestamps(data));
        fillPacketValues(data);
        fillChartWithData();
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

        String sourceIp = ip_combo.getSelectionModel().getSelectedItem();
        String destinationPort = second_port_combo.getSelectionModel().getSelectedItem();
        String sourcePort = first_port_combo.getSelectionModel().getSelectedItem();
        HashMap<String, ObservableList<Integer>> protocolsCount = ipCount.get(sourceIp).get(sourcePort).get(destinationPort);
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

        protocol_chart.getData().clear();
        protocol_chart.getData().addAll(protocolSeries);
    }

    private void fillPacketValues(List<BitsInPacketInfo> data) {
        for(BitsInPacketInfo info : data) {

            if (isSentToOrFromProperAddress(info)) {
                Integer time = TimestampUtils.getTime(timestampType, info.getTimestamp());
                String ip = getIpAddress(info);
                String firstPort = getFirstPort(info);
                String secondPort = getSecondPort(info);
                String protocolName = getProtocol(info);

                for (int i = 0; i < timeValues.size(); i++) {
                    Integer timeValue = timeValues.get(i);
                    if (time <= timeValue) {

                        if(!ipCount.containsKey(ip)) {
                            ipCount.put(ip, new HashMap<>());
                        }
                        HashMap<String, HashMap<String, HashMap<String, ObservableList<Integer>>>> sourcePortCount = ipCount.get(ip);

                        if(!sourcePortCount.containsKey(firstPort)) {
                            sourcePortCount.put(firstPort, new HashMap<>());
                        }

                        HashMap<String, HashMap<String, ObservableList<Integer>>> destinationPortCount = sourcePortCount.get(firstPort);

                        if(!destinationPortCount.containsKey(secondPort)) {
                            destinationPortCount.put(secondPort, new HashMap<>());
                        }

                        HashMap<String, ObservableList<Integer>> protocolsCount = destinationPortCount.get(secondPort);
                        if (!protocolsCount.containsKey(protocolName)) {
                            protocolsCount.put(protocolName, getListWithInitValues());
                        }


                        ObservableList<Integer> protocolValues = protocolsCount.get(protocolName);
                        Integer newCount = protocolValues.get(i) + 1;
                        protocolValues.remove(i);
                        protocolValues.add(i, newCount);
                        break;
                    }
                }
            }
        }
    }

//    private HashMap<String, HashMap<String, ObservableList<Integer>>> getListWithSourceIPInitValues() {
//        return null;
//    }

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

    protected abstract String getIpAddress(BitsInPacketInfo info);

    protected abstract String getFirstPort(BitsInPacketInfo info);

    protected abstract String getSecondPort(BitsInPacketInfo info);
}
