package com.devstack.pos.controller;

import com.devstack.pos.bo.BOFactory;
import com.devstack.pos.bo.custom.CustomerBO;
import com.devstack.pos.bo.custom.OrderBO;
import com.devstack.pos.bo.custom.ProductBO;
import com.devstack.pos.dto.response.ResponseCustomerDTO;
import com.devstack.pos.dto.response.ResponseProductDTO;
import com.devstack.pos.util.BoType;
import com.devstack.pos.view.tm.CartTM;
import com.google.zxing.WriterException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
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
    private final ProductBO productBO = BOFactory.getInstance().getBo(BoType.PRODUCT);

    private double fullTotal = 0;

    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colTools.setCellValueFactory(new PropertyValueFactory<>("btn"));

        disableFields();
        setOrderId();
        setCustomerIds();


        cmbCustomerIds.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setCustomerDetails(newValue);
            }
        });

        txtProductCodes.textProperty().addListener((observable, oldValue, newValue) -> {
            setProductDetails(newValue);
        });

    }

    private void setProductDetails(String id) {
        try {
            ResponseProductDTO responseProductDTO = productBO.findById(id);
            System.out.println(responseProductDTO);
            if (responseProductDTO == null) {
                return;
            }
            txtDescription.setText(responseProductDTO.getDescription());
            txtQtyOnHand.setText(String.valueOf(responseProductDTO.getQtyOnHand()));
            txtUnitPrice.setText(String.valueOf(responseProductDTO.getUnitPrice()));
        } catch (SQLException | ClassNotFoundException | IOException | WriterException e) {
            throw new RuntimeException(e);
        }
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
            if (selectedCustomer == null) {
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
        try {
            List<String> ids = customerBO.loadAllIds();
            ObservableList<String> list = FXCollections.observableArrayList(ids);
            cmbCustomerIds.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setOrderId() {
        try {
            int lastOrderId = orderBO.findLastOrderId();
            lblHeader.setText("Place Order (Order Id: #" + ++lastOrderId + ")");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void backToWindowOnAction(ActionEvent actionEvent) {
    }

    public void newOrderOnAction(ActionEvent actionEvent) {
    }

    public void addToCartOnAction(ActionEvent actionEvent) {
        int qty = Integer.parseInt(txtQty.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());

        if (qtyOnHand < qty) {
            new Alert(Alert.AlertType.WARNING, "please fill the stock").show();
            return;
        }

        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        Button btn = new Button("Remove");

        CartTM tm = new CartTM(txtProductCodes.getText(),
                txtDescription.getText(),
                unitPrice, qty, unitPrice * qty, btn);

        btn.setOnAction(e -> {
            for (CartTM t : tms){
                if(t.getId().equals(tm.getId())){
                    tms.remove(t);
                    manageTotal();
                    return;
                }
            }
        });

        addToTable(tm);
        manageTotal();
    }

    private void manageTotal() {
        fullTotal = 0;
        for (CartTM tm : tblProducts.getItems()) {
            fullTotal += tm.getTotal();
        }
        lblTotal.setText(String.format("Total Cost : %.2f/=", fullTotal));
    }

    ObservableList<CartTM> tms = FXCollections.observableArrayList();

    private void addToTable(CartTM tm) {
        for (CartTM ctm : tms) {
            if (ctm.getId().equals(tm.getId())) {
                ctm.setQty(ctm.getQty() + tm.getQty());
                ctm.setTotal(ctm.getQty() * tm.getUnitPrice());
                tblProducts.refresh();
                return;
            }
        }

        tms.add(tm);
        tblProducts.setItems(tms);

    }

    public void placeOrderOnAction(ActionEvent actionEvent) {
    }
}
