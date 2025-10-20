package com.devstack.pos.controller;

import com.devstack.pos.bo.BOFactory;
import com.devstack.pos.bo.custom.CustomerBO;
import com.devstack.pos.bo.custom.OrderBO;
import com.devstack.pos.dto.response.ResponseCustomerDTO;
import com.devstack.pos.util.BoType;
import com.devstack.pos.view.tm.CartTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.List;

public class PlaceOrderFormController {
    public AnchorPane context;
    public Label lblHeader;
    public ComboBox<String> cmbCustomerIds;
    public TextField txtName;
    public TextField txtSalary;
    public TextField txtAddress;
    public TableView<CartTM> tblProducts;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    public TextField txtQty;
    public TableColumn<CartTM, Integer> colId;
    public TableColumn<CartTM, Integer> colDescription;
    public TableColumn<CartTM, Double> colUnitPrice;
    public TableColumn<CartTM, Integer> colQty;
    public TableColumn<CartTM, Double> colTotal;
    public TableColumn<CartTM, Button> colTools;
    public Label lblTotal;
    public TextField txtProductCodes;

    private final OrderBO orderBO = BOFactory.getInstance().getBo(BoType.ORDER);
    private final CustomerBO customerBO = BOFactory.getInstance().getBo(BoType.CUSTOMER);

    public void initialize() {
        disableFields();
        setOrderId();
        setCustomerIds();


        cmbCustomerIds.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                setCustomerDetails(newValue);
            }
        });

    }

    private void disableFields() {
        txtName.setDisable(true);
        txtAddress.setDisable(true);
        txtSalary.setDisable(true);

        txtDescription.setDisable(true);
        txtUnitPrice.setDisable(true);
        txtQtyOnHand.setDisable(true);
    }

    private void setCustomerDetails(String newValue) {
        try {
            ResponseCustomerDTO selectedCustomer = customerBO.getCustomerById(newValue);
            if(selectedCustomer == null) {
                new Alert(Alert.AlertType.WARNING, "Customer Not Found!", ButtonType.OK).show();
                return;
            }
            txtName.setText(selectedCustomer.getName());
            txtAddress.setText(selectedCustomer.getAddress());
            txtSalary.setText(String.valueOf(selectedCustomer.getSalary()));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCustomerIds() {
        try{
            List<String> ids = customerBO.loadAllIds();
            ObservableList<String> list = FXCollections.observableArrayList(ids);
            cmbCustomerIds.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setOrderId() {
        try{
            int lastOrderId = orderBO.findLastOrderId();
            lblHeader.setText("Place Order (Order Id: #"+ ++lastOrderId+")");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void backToWindowOnAction(ActionEvent actionEvent) {
    }

    public void newOrderOnAction(ActionEvent actionEvent) {
    }

    public void addToCartOnAction(ActionEvent actionEvent) {
    }

    public void placeOrderOnAction(ActionEvent actionEvent) {
    }
}
