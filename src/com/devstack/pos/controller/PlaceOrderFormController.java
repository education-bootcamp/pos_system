package com.devstack.pos.controller;

import com.devstack.pos.bo.BOFactory;
import com.devstack.pos.bo.custom.CustomerBO;
import com.devstack.pos.bo.custom.OrderBO;
import com.devstack.pos.bo.custom.ProductBO;
import com.devstack.pos.dto.request.RequestOrderDTO;
import com.devstack.pos.dto.request.RequestOrderDetailDTO;
import com.devstack.pos.dto.response.ResponseCustomerDTO;
import com.devstack.pos.dto.response.ResponseProductDTO;
import com.devstack.pos.util.BoType;
import com.devstack.pos.view.tm.CartTM;
import com.google.zxing.WriterException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
    private int orderId;
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
            orderId = orderBO.findLastOrderId()+1;
            lblHeader.setText("Place Order (Order Id: #" + orderId + ")");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void backToWindowOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void newOrderOnAction(ActionEvent actionEvent) {
        clearAllFields();
    }

    public void addToCartOnAction(ActionEvent actionEvent) {
        String productId = txtProductCodes.getText().trim();
        String qtyText = txtQty.getText().trim();
        String qtyOnHandText = txtQtyOnHand.getText().trim();
        String unitPriceText = txtUnitPrice.getText().trim();
        String description = txtDescription.getText().trim();

        // Regex patterns
        String qtyRegex = "^[0-9]+$";
        String priceRegex = "^[0-9]+(\\.[0-9]{1,2})?$";

        // --- Field validations ---
        if (productId.isEmpty() || description.isEmpty() || qtyText.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all fields before adding to cart!").show();
            return;
        }

        if (!qtyText.matches(qtyRegex)) {
            new Alert(Alert.AlertType.WARNING, "Invalid quantity! Must be a positive whole number.").show();
            return;
        }

        if (!unitPriceText.matches(priceRegex)) {
            new Alert(Alert.AlertType.WARNING, "Invalid unit price format!").show();
            return;
        }

        if (!qtyOnHandText.matches(qtyRegex)) {
            new Alert(Alert.AlertType.WARNING, "Invalid stock quantity format!").show();
            return;
        }

        int qty = Integer.parseInt(qtyText);
        int qtyOnHand = Integer.parseInt(qtyOnHandText);
        double unitPrice = Double.parseDouble(unitPriceText);

        if (qty <= 0) {
            new Alert(Alert.AlertType.WARNING, "Quantity must be greater than 0!").show();
            return;
        }

        if (qty > qtyOnHand) {
            new Alert(Alert.AlertType.WARNING, "Insufficient stock available!").show();
            return;
        }

        // --- Create CartTM ---
        Button btn = new Button("Remove");
        CartTM tm = new CartTM(productId, description, unitPrice, qty, unitPrice * qty, btn);


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

    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (orderId == 0) {
            new Alert(Alert.AlertType.WARNING, "Order ID not initialized. Please try again!").show();
            return;
        }

        if (cmbCustomerIds.getValue() == null || cmbCustomerIds.getValue().trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select a customer before placing the order!").show();
            return;
        }

        if (tms.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Your cart is empty! Please add products first.").show();
            return;
        }

        // Build the order DTO
        RequestOrderDTO dto = new RequestOrderDTO();
        List<RequestOrderDetailDTO> orderDetailDTOList = new ArrayList<>();

        for (CartTM tm : tblProducts.getItems()) {
            orderDetailDTOList.add(
                    new RequestOrderDetailDTO(
                            tm.getId(), tm.getUnitPrice(),tm.getQty()
                    )
            );
        }
        dto.setOrderId(orderId);
        dto.setCustomerId(cmbCustomerIds.getValue());
        dto.setOrderDetailDTOList(orderDetailDTOList);
        dto.setDate(new Date());
        dto.setTotalCost(fullTotal);
        boolean isSavedOrder = orderBO.createOrder(dto);
        if (isSavedOrder) {
            setOrderId();
            clearAllFields();
            printBill();
            new Alert(Alert.AlertType.INFORMATION, "Order Completed!..").show();
        }
    }

    private void printBill() {
        // to be implemented...
    }

    private void clearAllFields() {
        cmbCustomerIds.setValue(null);
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();

        txtDescription.clear();
        txtQtyOnHand.clear();
        txtUnitPrice.clear();
        txtProductCodes.clear();

        fullTotal=0;

        txtProductCodes.requestFocus();
        tms.clear();
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
