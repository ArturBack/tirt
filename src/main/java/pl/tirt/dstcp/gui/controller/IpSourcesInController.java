package pl.tirt.dstcp.gui.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import pl.tirt.dstcp.data.DataUtils;
import pl.tirt.dstcp.data.model.BitsInPacketInfo;
import pl.tirt.dstcp.data.model.IpProtocolVersionInPacketInfo;
import pl.tirt.dstcp.data.repositories.IpProtocolVersionRepository;
import pl.tirt.dstcp.gui.utils.TimestampType;
import pl.tirt.dstcp.gui.utils.TimestampUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by AWALICZE on 22.04.2017.
 */
public class IpSourcesInController {

    @FXML
    private LineChart<String, Integer> ipSourceChart;

    @FXML
    private ComboBox<String> scale_type_combo;

    @FXML
    private ComboBox<String> ip_source_combo;

    private HashMap<String, ObservableList<Integer>> ipSourcesMap = new HashMap<>();
    private ObservableList<Integer> timeValues;
    private List<IpProtocolVersionInPacketInfo> data;
    private TimestampType timestampType = TimestampType.SECOND;

    @FXML
    private void initialize() {
        data = IpProtocolVersionRepository.getInstance().getData();
        setData(data);
        initScaleTypes();
    }

    private void setData(List<IpProtocolVersionInPacketInfo> data){
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
        ip_source_combo.getItems().addAll(ipSourcesMap.keySet());
        //default value
        ip_source_combo.getSelectionModel().select(0);

        ip_source_combo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                changeShownIp();
            }
        });
    }

    private void reloadData() {
        ipSourceChart.getData().clear();
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
        ipSourceChart.getData().clear();
        fillChartWithData();
    }

    public void setTimestampType(TimestampType timestampType) {
        this.timestampType = timestampType;
    }

    private void fillChartWithProperData(int startIndex) {
        String selectedItem = ip_source_combo.getSelectionModel().getSelectedItem();
        XYChart.Series series = new XYChart.Series();
        for(int i = startIndex; i < timeValues.size(); i++){
            series.getData().add(new XYChart.Data(timeValues.get(i).toString(), ipSourcesMap.get(selectedItem).get(i)));
        }
        ipSourceChart.getData().add(series);
    }

    private void fillIpSourceMap(List<IpProtocolVersionInPacketInfo> data) {
        ipSourcesMap.clear();
        for(IpProtocolVersionInPacketInfo info : data){
            Integer time = TimestampUtils.getTime(timestampType,info.getTimestamp());

            if(info.getDestination().equals(DataUtils.HOME_ADDRESS)) {

                for (int i = 0; i < timeValues.size(); i++) {
                    Integer timeValue = timeValues.get(i);
                    if (time <= timeValue) {
                        String sourceIp = info.getSource();
                        if (sourceIp.isEmpty()) {
                            break;
                        }

                        if (!ipSourcesMap.containsKey(sourceIp)) {
                            ipSourcesMap.put(sourceIp, getListWithInitValues());
                        }
                        ObservableList<Integer> values = ipSourcesMap.get(sourceIp);
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

    private List<String> getTimestamps(List<IpProtocolVersionInPacketInfo> data){
        List<String> timestamps = new ArrayList<>();
        for(IpProtocolVersionInPacketInfo info : data){
            timestamps.add(info.getTimestamp());
        }
        return timestamps;
    }
}