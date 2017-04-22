package pl.tirt.dstcp.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
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
    private CategoryAxis xAxis;

    private ObservableList<Integer> packetValuesWithIPV4 = FXCollections.observableArrayList();
    private ObservableList<Integer> packetValuesWithIPV6 = FXCollections.observableArrayList();
    private ObservableList<Integer> timeValues = FXCollections.observableArrayList();

    private TimestampType timestampType = TimestampType.SECOND;


    @FXML
    private void initialize() {

        List<IpProtocolVersionInPacketInfo> data = IpProtocolVersionRepository.getInstance().getData();
        timeValues = TimestampUtils.createTimeValues(timestampType,getTimestamps(data));
        packetValuesWithIPV4 = getListWithInitValues();
        packetValuesWithIPV6 = getListWithInitValues();
        fillPacketValues(data);
        fillChartWithData();
    }

    private void fillChartWithData() {
        XYChart.Series<String, Integer> seriesIPV4 = new XYChart.Series<>();
        seriesIPV4.setName("IPV4");
        for(int i = 0; i < timeValues.size(); i++){
            seriesIPV4.getData().add(new XYChart.Data(timeValues.get(i).toString(), packetValuesWithIPV4.get(i)));
        }

        XYChart.Series<String, Integer> seriesIPV6 = new XYChart.Series<>();
        seriesIPV6.setName("IPV6");
        for(int i = 0; i < timeValues.size(); i++){
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
