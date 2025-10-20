package com.devstack.pos.controller;

import com.devstack.pos.bo.BOFactory;
import com.devstack.pos.bo.custom.ProductBO;
import com.devstack.pos.dto.request.RequestProductDTO;
import com.devstack.pos.dto.response.ResponseCustomerDTO;
import com.devstack.pos.dto.response.ResponseProductDTO;
import com.devstack.pos.util.BoType;
import com.devstack.pos.view.tm.CustomerTm;
import com.devstack.pos.view.tm.ProductTM;
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
import java.util.List;
import java.util.Optional;

public class ProductController {
    public AnchorPane context;
    public Label lblQty;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    public Button btnSaveUpdate;
    public TextField txtSearch;
    public TableView<ProductTM> tblProducts;
    public TableColumn<ProductTM, Long> colId;
    public TableColumn<ProductTM, String> colDescription;
    public TableColumn<ProductTM, Double> colUnitPrice;
    public TableColumn<ProductTM, Integer> colQtyOnHand;
    public TableColumn<ProductTM, Button> colQRAv;
    public TableColumn<ProductTM, ButtonBar> colTools;

    private final ProductBO bo = BOFactory.getInstance().getBo(BoType.PRODUCT);

    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colQRAv.setCellValueFactory(new PropertyValueFactory<>("qrAv"));
        colTools.setCellValueFactory(new PropertyValueFactory<>("tools"));

        searchAll();

        loadFillableCount();
    }

    private void loadFillableCount() {
        try {
            long l = bo.fillableCount();
            lblQty.setText(String.valueOf(l));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void newProductOnAction(ActionEvent actionEvent) {
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void saveUpdateOnAction(ActionEvent actionEvent) {
        if (btnSaveUpdate.getText().equalsIgnoreCase("Save Product")) {
            RequestProductDTO dto = new RequestProductDTO(
                    txtDescription.getText(),
                    Double.parseDouble(txtUnitPrice.getText()),
                    Integer.parseInt(txtQtyOnHand.getText())
            );
            try {
                boolean isSaved = bo.createProduct(dto);
                if (isSaved) {
                    clear();
                    new Alert(Alert.AlertType.INFORMATION, "Product Updated Successfully", ButtonType.OK).show();
                    searchAll();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Please Try Again").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(), ButtonType.OK).show();
            }
        } else {
            // update
        }
    }

    private void searchAll() {
        try {
            List<ResponseProductDTO> list = bo.loadAllProducts();
            ObservableList<ProductTM> items = FXCollections.observableArrayList();
            long id = 1;
            for (ResponseProductDTO rc : list) {

                ButtonBar bar = new ButtonBar();
                Button updateButton = new Button("Update");
                Button deleteButton = new Button("Delete");

                Button qrButton = new Button("Show QR");

                deleteButton.setStyle("-fx-background-color: red");

                bar.getButtons().addAll(updateButton, deleteButton);

                ProductTM item = new ProductTM(
                        id++,
                        rc.getDescription(),
                        rc.getUnitPrice(),
                        rc.getQtyOnHand(),
                        qrButton,
                        bar
                );

                qrButton.setOnAction(event -> {
                    URL resource = getClass().getResource("/com/devstack/pos/view/QRForm.fxml");

                    try {
                        FXMLLoader loader = new FXMLLoader(resource);
                        Parent parent = loader.load();

                        QRFormController controller = loader.getController();
                        controller.setQr(rc.getQr());
                        Stage stage = new Stage();
                        stage.setScene(new Scene(parent));
                        stage.show();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                });

                items.add(item);
            }

            tblProducts.setItems(items);

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(), ButtonType.OK).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }

    }

    private void clear() {
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        btnSaveUpdate.setText("Save Product");
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
