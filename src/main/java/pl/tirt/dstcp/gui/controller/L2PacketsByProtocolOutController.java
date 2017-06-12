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
 * Created by AWALICZE on 22.04.2017.
 */
public class L2PacketsByProtocolOutController {

    @FXML
    private StackedBarChart<String, Integer> protocol_per_address;

    @FXML
    private ComboBox<String> scale_type_combo;

    @FXML
    private ComboBox<String> mac_source_combo;

    private HashMap<String, HashMap<String, ObservableList<Integer>>> macDestinationsMap = new HashMap<>();
    private ObservableList<Integer> timeValues;
    private List<BitsInPacketInfo> data;
    private TimestampType timestampType = TimestampType.SECOND;

    @FXML
    private void initialize() {
        data = BitsInPacketRepository.getInstance().getData();
        setData(data);
        initScaleTypes();
    }

    private void setData(List<BitsInPacketInfo> data){
        timeValues = TimestampUtils.createTimeValues(timestampType,getTimestamps(data));
        fillIpSourceMap(data);
        fillIpSourcesCombo();
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

    private void fillIpSourcesCombo() {
        mac_source_combo.getItems().addAll(macDestinationsMap.keySet());
        //default value
        mac_source_combo.getSelectionModel().select(0);

        mac_source_combo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                changeShownIp();
            }
        });
    }

    private void reloadData() {
        protocol_per_address.getData().clear();
        timeValues = TimestampUtils.createTimeValues(timestampType,getTimestamps(data));
        fillIpSourceMap(data);
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

    private void changeShownIp() {
        protocol_per_address.getData().clear();
        fillChartWithData();
    }

    public void setTimestampType(TimestampType timestampType) {
        this.timestampType = timestampType;
    }

    private void fillChartWithProperData(int startIndex) {
        String selectedMac = mac_source_combo.getSelectionModel().getSelectedItem();
        HashMap<String, ObservableList<Integer>> protocolsCount = macDestinationsMap.get(selectedMac);
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
        protocol_per_address.getData().clear();
        protocol_per_address.getData().addAll(protocolSeries);
    }

    private void fillIpSourceMap(List<BitsInPacketInfo> data) {
        macDestinationsMap.clear();
        for(BitsInPacketInfo info : data){
            if(info.getSourceMac().equals(DataUtils.HOME_MAC_ADDRESS)) {

                Integer time = TimestampUtils.getTime(timestampType,info.getTimestamp());
                String destinationMac = info.getDestinationMac();
                String protocol = info.getProtocolII();

                for (int i = 0; i < timeValues.size(); i++) {
                    Integer timeValue = timeValues.get(i);
                    if (time <= timeValue) {

                        if (destinationMac.isEmpty()) {
                            break;
                        }

                        if (!macDestinationsMap.containsKey(destinationMac)) {
                            macDestinationsMap.put(destinationMac, new HashMap<>());
                        }
                        HashMap<String, ObservableList<Integer>> ipProtocolMap = macDestinationsMap.get(destinationMac);

                        if(!ipProtocolMap.containsKey(protocol)) {
                            ipProtocolMap.put(protocol, getListWithInitValues());
                        }

                        ObservableList<Integer> values = ipProtocolMap.get(protocol);
                        Integer newCount = values.get(i) + 1;
                        values.remove(i);
                        values.add(i, newCount);
                        break;
                    }
                }
            }
        }
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
