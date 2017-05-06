package pl.tirt.dstcp.gui.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import pl.tirt.dstcp.data.model.IpProtocolVersionInPacketInfo;
import pl.tirt.dstcp.data.repositories.IpProtocolVersionRepository;
import pl.tirt.dstcp.gui.utils.TimestampType;
import pl.tirt.dstcp.gui.utils.TimestampUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AWALICZE on 22.04.2017.
 */
public class IpVersionChartController {

    @FXML
    private StackedBarChart<String, Integer> ipVersionChart;

    @FXML
    private ComboBox<String> scale_type_combo;

    private ObservableList<Integer> packetValuesWithIPV4;
    private ObservableList<Integer> packetValuesWithIPV6;
    private ObservableList<Integer> timeValues;
    private List<IpProtocolVersionInPacketInfo> data;

    private TimestampType timestampType = TimestampType.SECOND;


    @FXML
    private void initialize() {
        data = IpProtocolVersionRepository.getInstance().getData();
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
        ipVersionChart.getData().clear();
        setData(data);
    }

    public void setTimestampType(TimestampType timestampType) {
        this.timestampType = timestampType;
    }

    private void setData(List<IpProtocolVersionInPacketInfo> data){
        timeValues = TimestampUtils.createTimeValues(timestampType,getTimestamps(data));
        packetValuesWithIPV4 = getListWithInitValues();
        packetValuesWithIPV6 = getListWithInitValues();
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
        XYChart.Series<String, Integer> seriesIPV4 = new XYChart.Series<>();
        seriesIPV4.setName("IPV4");
        for(int i = startIndex; i < timeValues.size(); i++){
            seriesIPV4.getData().add(new XYChart.Data(timeValues.get(i).toString(), packetValuesWithIPV4.get(i)));
        }

        XYChart.Series<String, Integer> seriesIPV6 = new XYChart.Series<>();
        seriesIPV6.setName("IPV6");
        for(int i = startIndex; i < timeValues.size(); i++){
            seriesIPV6.getData().add(new XYChart.Data(timeValues.get(i).toString(), packetValuesWithIPV6.get(i)));
        }

        ipVersionChart.getData().addAll(seriesIPV4,seriesIPV6);
    }

    private void fillPacketValues(List<IpProtocolVersionInPacketInfo> data) {
        for(IpProtocolVersionInPacketInfo info : data){
            Integer time = TimestampUtils.getTime(timestampType,info.getTimestamp());
            for(int i = 0; i < timeValues.size(); i++){
                Integer timeValue = timeValues.get(i);
                if(time <= timeValue){
                    if(info.getIpProtocolVersion() == 4){
                        //packet with ipv4
                        Integer newCount = packetValuesWithIPV4.get(i) + 1;
                        packetValuesWithIPV4.remove(i);
                        packetValuesWithIPV4.add(i,newCount);
                    } else if(info.getIpProtocolVersion() == 6){
                        //packet with ipv6
                        Integer newCount = packetValuesWithIPV6.get(i) + 1;
                        packetValuesWithIPV6.remove(i);
                        packetValuesWithIPV6.add(i,newCount);
                    }
                    break;
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
