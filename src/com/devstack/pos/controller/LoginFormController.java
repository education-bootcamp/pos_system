package com.devstack.pos.controller;

import com.devstack.pos.bo.BOFactory;
import com.devstack.pos.bo.custom.UserBo;
import com.devstack.pos.dto.response.ResponseUserDTO;
import com.devstack.pos.util.BoType;
import com.devstack.pos.util.SystemVariables;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class LoginFormController {
    public AnchorPane context;


    public TextField txtEmail;
    public PasswordField txtPassword;

    private UserBo userBo = BOFactory.getInstance().getBo(BoType.USER);

    public void backToScreenOnAction(ActionEvent actionEvent) throws IOException {
        setUi("MainForm");
    }

    public void navigateToForgotPasswordOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ForgotPasswordForm");
    }

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        try{
            ResponseUserDTO loginData = userBo.loginUser(
                    txtEmail.getText().trim(), txtPassword.getText()
            );
            if(loginData.isStatus()){
                new Alert(Alert.AlertType.INFORMATION,loginData.getMsg()).show();
                SystemVariables.responseUserDTO=loginData;
                setUi("DashboardForm");
            }else{
                new Alert(Alert.AlertType.WARNING,loginData.getMsg()).show();
            }
        }catch (ClassNotFoundException | SQLException e){
            new Alert(Alert.AlertType.ERROR,"Error Occurred!...(" +e.getMessage()+")").show();
            e.printStackTrace();
        }

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
