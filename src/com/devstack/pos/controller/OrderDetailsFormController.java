package com.devstack.pos.controller;

import com.devstack.pos.bo.BOFactory;
import com.devstack.pos.bo.custom.CustomerBO;
import com.devstack.pos.bo.custom.OrderBO;
import com.devstack.pos.bo.custom.OrderDetailBO;
import com.devstack.pos.dto.response.ResponseCustomerDTO;
import com.devstack.pos.dto.response.ResponseOrderDTO;
import com.devstack.pos.dto.response.ResponseOrderDetailsDTO;
import com.devstack.pos.util.BoType;
import com.devstack.pos.view.tm.OrderDetailTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailsFormController {

    public AnchorPane context;
    public ListView<String> lstDetails;
    public TableView<OrderDetailTM> tblDetails;
    public TableColumn<OrderDetailTM, Integer> colId;
    public TableColumn<OrderDetailTM, String> colDescription;
    public TableColumn<OrderDetailTM, Double> colUnitPrice;
    public TableColumn<OrderDetailTM, Integer> colQty;
    public TableColumn<OrderDetailTM, Double> colTotal;

    private OrderBO orderBO= BOFactory.getInstance().getBo(BoType.ORDER);
    private CustomerBO customerBO= BOFactory.getInstance().getBo(BoType.CUSTOMER);
    private OrderDetailBO orderDetailBO= BOFactory.getInstance().getBo(BoType.ORDER_DETAIL);

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
    }

    public void setData(int orderId){
        try {
            ResponseOrderDTO selectedOrder = orderBO.findOrderById(orderId);
            if(selectedOrder==null){
                new Alert(Alert.AlertType.WARNING,"Order Not Found!..").show();
                return;
            }

            ResponseCustomerDTO selectedCustomer = customerBO.getCustomerById(selectedOrder.getCustomer());
            if(selectedCustomer==null){
                new Alert(Alert.AlertType.WARNING,"Customer Not Found!..").show();
                return;
            }

            List<ResponseOrderDetailsDTO> orderDetailByOrderId =
                    orderDetailBO.getOrderDetailByOrderId(orderId);

            ObservableList<OrderDetailTM> tms = FXCollections.observableArrayList();
            int id=0;
            for (ResponseOrderDetailsDTO orderDetailDTO : orderDetailByOrderId) {
                tms.add(new OrderDetailTM(
                        ++id, orderDetailDTO.getProductName(),
                        orderDetailDTO.getUnitPrice(),
                        orderDetailDTO.getQty(),
                        orderDetailDTO.getTotalCost()));
            }
            tblDetails.setItems(tms);

            String customer ="Customer Name : "+selectedCustomer.getName();
            String address ="Customer Address : "+selectedCustomer.getAddress();
            String date ="Date : "+selectedOrder.getDate();
            String cost ="Total Cost : "+selectedOrder.getTotalCost();
            String itemCount ="Item Count : "+orderDetailByOrderId.size()+" Items";

            lstDetails.getItems().addAll(customer,address,date,cost,itemCount);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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

    public void printOnAction(ActionEvent actionEvent) {
    }
}
