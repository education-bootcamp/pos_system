package com.devstack.pos.controller;

import com.devstack.pos.bo.BOFactory;
import com.devstack.pos.bo.custom.OrderBO;
import com.devstack.pos.dto.response.ResponseOrderDTO;
import com.devstack.pos.util.BoType;
import com.devstack.pos.view.tm.OrderTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class OrderHistoryFormController {
    public AnchorPane context;
    public TableView<OrderTM> tblOrders;
    public TableColumn<OrderTM, Integer> colId;
    public TableColumn<OrderTM, String> colCustomer;
    public TableColumn<OrderTM, Double> colTotalCost;
    public TableColumn<OrderTM, PieChart.Data> colDate;
    public DatePicker datePicker;

    private OrderBO bo = BOFactory.getInstance().getBo(BoType.ORDER);

    public void initialize() throws SQLException, ClassNotFoundException {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("customer"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        setDate();

        tblOrders.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                URL resource = getClass().getResource("/com/devstack/pos/view/OrderDetailsForm.fxml");

                try {
                    FXMLLoader loader = new FXMLLoader(resource);
                    Parent parent = loader.load();

                    OrderDetailsFormController controller = loader.getController();
                    controller.setData(newValue.getId());
                    Stage stage = new Stage();
                    stage.setScene(new Scene(parent));
                    stage.show();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private void setDate() throws SQLException, ClassNotFoundException {
        datePicker.setValue(LocalDate.now());
        setData();
    }

    private void setData() throws SQLException, ClassNotFoundException {
        List<ResponseOrderDTO> orderHistory = bo.getOrderHistory(datePicker.getValue());
        ObservableList<OrderTM> orders = FXCollections.observableArrayList();

        for (ResponseOrderDTO dto : orderHistory) {
            OrderTM order = new OrderTM(
                    dto.getId(), dto.getCustomer(), dto.getTotalCost(), dto.getDate()
            );
            orders.add(order);
        }
        tblOrders.setItems(orders);
        tblOrders.refresh();

    }

    private void setUi(String location) throws IOException {
        URL resource = getClass().getResource("/com/devstack/pos/view/" + location + ".fxml");
        Parent load = FXMLLoader.load(resource);
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(
                new Scene(load)
        );
    }

    public void backToScreenOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void filterOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setData();
    }
}
