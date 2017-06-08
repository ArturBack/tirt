package pl.tirt.dstcp.gui.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
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
 * Created by mac on 04.06.2017.
 */
public abstract class ProtocolsPerNode {

    @FXML
    private StackedBarChart<String, Integer> protocol_chart;

    @FXML
    private ComboBox<String> ip_source_combo;

    @FXML
    private ComboBox<String> destination_port_combo;

    @FXML
    private ComboBox<String> source_port_combo;

    @FXML
    private ComboBox<String> scale_type_combo;



    private HashMap<String, HashMap<String, HashMap<String, HashMap<String, ObservableList<Integer>>>>> sourceIpCount = new HashMap<>();
//    private HashMap<String, ObservableList<Integer>> protocolsCount = new HashMap<>();

    private ObservableList<Integer> timeValues;
    private List<BitsInPacketInfo> data;

    private TimestampType timestampType = TimestampType.SECOND;

//    private boolean relodeOn = false;


    @FXML
    private void initialize() {
        data = BitsInPacketRepository.getInstance().getData();
//        setData(data);
        timeValues = TimestampUtils.createTimeValues(timestampType,getTimestamps(data));
        fillPacketValues(data);
        initCheckBoxes();
        initScaleTypes();
        fillChartWithData();
    }

    private void initCheckBoxes() {
        ip_source_combo.getItems().clear();
        ip_source_combo.getItems().addAll(sourceIpCount.keySet());
        ip_source_combo.getSelectionModel().select(0);
        ip_source_combo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("1");
                if (newValue != null) {
//                    relodeOn = true;
                    HashMap<String, HashMap<String, HashMap<String, ObservableList<Integer>>>> map = sourceIpCount.get(newValue);
                    updateSourcePortCombo(map);
//                    System.out.println("1");
                }
            }
        });
        String val = ip_source_combo.getSelectionModel().getSelectedItem();
        HashMap<String, HashMap<String, HashMap<String, ObservableList<Integer>>>> map = sourceIpCount.get(val);
        setSourcePortCombo(map);
    }


    private void setSourcePortCombo(HashMap<String, HashMap<String, HashMap<String, ObservableList<Integer>>>> sourcePortCount) {
        updateSourcePortCombo(sourcePortCount);
        source_port_combo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("2");
//                if (!relodeOn) {
                if (newValue != null) {
                    System.out.println("2.1");
                    updateDestinationPortCombo(sourceIpCount.get(ip_source_combo.getSelectionModel().getSelectedItem()).get(newValue));
//                    setDestinationPortCombo(sourcePortCount.get(newValue));
                }
            }
        });
        setDestinationPortCombo(sourcePortCount.get(source_port_combo.getSelectionModel().getSelectedItem()));
    }

    private void updateSourcePortCombo(HashMap<String, HashMap<String, HashMap<String, ObservableList<Integer>>>> sourcePortCount) {
        source_port_combo.getItems().clear();
        source_port_combo.getItems().addAll(sourcePortCount.keySet());
        source_port_combo.getSelectionModel().select(0);
    }

    private void setDestinationPortCombo(HashMap<String, HashMap<String, ObservableList<Integer>>> destinationPortCount) {
        updateDestinationPortCombo(destinationPortCount);
        destination_port_combo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    reloadData();
                }
            }
        });
    }

    private void updateDestinationPortCombo(HashMap<String, HashMap<String, ObservableList<Integer>>> destinationPortCount) {
        destination_port_combo.getItems().clear();
        destination_port_combo.getItems().addAll(destinationPortCount.keySet());
        destination_port_combo.getSelectionModel().select(0);
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
        sourceIpCount = new HashMap<>();
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

        String sourceIp = ip_source_combo.getSelectionModel().getSelectedItem();
        String destinationPort = destination_port_combo.getSelectionModel().getSelectedItem();
        String sourcePort = source_port_combo.getSelectionModel().getSelectedItem();
        HashMap<String, ObservableList<Integer>> protocolsCount = sourceIpCount.get(sourceIp).get(sourcePort).get(destinationPort);
        System.out.println("refil s");
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
                String sourceIP = info.getSourceIP();
                String destinationPort = info.getDestinationPort();
                String sourcePort = info.getSourcePort();
                String protocolName = getProtocol(info);

                for (int i = 0; i < timeValues.size(); i++) {
                    Integer timeValue = timeValues.get(i);
                    if (time <= timeValue) {

                        if(!sourceIpCount.containsKey(sourceIP)) {
                            sourceIpCount.put(sourceIP, new HashMap<>());
                        }
                        HashMap<String, HashMap<String, HashMap<String, ObservableList<Integer>>>> sourcePortCount = sourceIpCount.get(sourceIP);

                        if(!sourcePortCount.containsKey(destinationPort)) {
                            sourcePortCount.put(destinationPort, new HashMap<>());
                        }

                        HashMap<String, HashMap<String, ObservableList<Integer>>> destinationPortCount = sourcePortCount.get(destinationPort);

                        if(!destinationPortCount.containsKey(sourcePort)) {
                            destinationPortCount.put(sourcePort, new HashMap<>());
                        }

                        HashMap<String, ObservableList<Integer>> protocolsCount = destinationPortCount.get(sourcePort);
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

    private HashMap<String, HashMap<String, ObservableList<Integer>>> getListWithSourceIPInitValues() {
        return null;
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
}
