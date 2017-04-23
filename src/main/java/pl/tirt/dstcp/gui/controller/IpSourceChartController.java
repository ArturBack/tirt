package pl.tirt.dstcp.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
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
public class IpSourceChartController {

    @FXML
    private StackedBarChart<String, Integer> ipSourceChart;

    @FXML
    private CategoryAxis xAxis;

    private HashMap<String, ObservableList<Integer>> ipSourcesMap = new HashMap<>();
    private ObservableList<Integer> timeValues = FXCollections.observableArrayList();

    private TimestampType timestampType = TimestampType.SECOND;


    @FXML
    private void initialize() {
        List<IpProtocolVersionInPacketInfo> data = IpProtocolVersionRepository.getInstance().getData();
        timeValues = TimestampUtils.createTimeValues(timestampType,getTimestamps(data));
        fillIpSourceMap(data);
        fillChartWithData();
    }

    private void fillChartWithData() {
        for(String key: ipSourcesMap.keySet()) {
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.setName(key);
            for (int i = 0; i < timeValues.size(); i++) {
                XYChart.Data data = new XYChart.Data(timeValues.get(i).toString(), ipSourcesMap.get(key).get(i));
                series.getData().add(data);
            }
            ipSourceChart.getData().add(series);
        }
    }

    private void fillIpSourceMap(List<IpProtocolVersionInPacketInfo> data) {
        for(IpProtocolVersionInPacketInfo info : data){
            Integer time = TimestampUtils.getTime(timestampType,info.getTimestamp());

            for(int i = 0; i < timeValues.size(); i++){
                Integer timeValue = timeValues.get(i);
                if(time <= timeValue){
                    String sourceIp = info.getSource();
                    if(sourceIp.isEmpty()){
                        break;
                    }

                    if(!ipSourcesMap.containsKey(sourceIp)){
                        ipSourcesMap.put(sourceIp, getListWithInitValues());
                    }
                    ObservableList<Integer> values = ipSourcesMap.get(sourceIp);
                    Integer newCount = values.get(i) + 1;
                    values.remove(i);
                    values.add(i,newCount);
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
