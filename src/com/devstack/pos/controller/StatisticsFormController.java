package com.devstack.pos.controller;

import com.devstack.pos.bo.BOFactory;
import com.devstack.pos.bo.custom.OrderBO;
import com.devstack.pos.dto.response.StatisticsResponseDTO;
import com.devstack.pos.util.BoType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class StatisticsFormController {
    public AnchorPane context;
    public DatePicker from;
    public DatePicker to;
    public AreaChart chart;

    private final OrderBO orderBO = BOFactory.getInstance().getBo(BoType.ORDER);

    public void initialize() {
        setDate();
    }

    private void setDate() {
        LocalDate today = LocalDate.now();
        to.setValue(today);
        from.setValue(today.minusDays(7));

        loadAllData();
    }

    private void loadAllData() {
        try {

            List<StatisticsResponseDTO> statisticsResponseDTOS =
                    orderBO.loadStatistics(from.getValue(), to.getValue());

            chart.getData().clear();
            javafx.scene.chart.XYChart.Series<String, Number> series = new javafx.scene.chart.XYChart.Series<>();
            series.setName("Total Sales");

            for (StatisticsResponseDTO statisticsResponseDTO : statisticsResponseDTOS) {
                series.getData().add(new javafx.scene.chart.XYChart.Data<>(statisticsResponseDTO.getDate(), statisticsResponseDTO.getTotal()));
            }

            chart.getData().add(series);

        } catch (Exception e) {
        }
    }

    public void backToScreenOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void filterOnAction(ActionEvent actionEvent) {
        loadAllData();
    }

    private void setUi(String location) throws IOException {
        URL resource = getClass().getResource("/com/devstack/pos/view/" + location + ".fxml");
        Parent load = FXMLLoader.load(resource);
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(
                new Scene(load)
        );
    }
}
