package com.devstack.pos.controller;

import com.devstack.pos.env.StaticResource;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainFormController {
    public Label lblCompany;
    public Label lblVersion;
    public AnchorPane context;

    public void initialize(){
        setStaticData();
    }

    private void setStaticData() {
        lblVersion.setText("Version : "+ StaticResource.getVersion());
        lblCompany.setText("From : "+ StaticResource.getCompany());
    }

    public void openLoginFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    public void openRegisterFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("RegisterForm");
    }

    private void setUi(String location) throws IOException {
        URL resource = getClass().getResource("/com/devstack/pos/view/"+location+".fxml");
        Parent load = FXMLLoader.load(resource);
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(
                new Scene(load)
        );
    }

}
