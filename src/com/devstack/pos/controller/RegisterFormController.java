package com.devstack.pos.controller;

import com.devstack.pos.bo.BOFactory;
import com.devstack.pos.bo.custom.UserBo;
import com.devstack.pos.dto.request.RequestUserDTO;
import com.devstack.pos.model.User;
import com.devstack.pos.util.BoType;
import com.devstack.pos.util.PasswordHash;
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
import java.util.UUID;

public class RegisterFormController {
    public AnchorPane context;

    public TextField txtContactNumber;
    public TextField txtDisplayName;
    public TextField txtEmail;
    public PasswordField txtPassword;

    private UserBo userBo = BOFactory.getInstance().getBo(BoType.USER);


    public void backToScreenOnAction(ActionEvent actionEvent) throws IOException {
        setUi("MainForm");
    }

    public void RegisterOnAction(ActionEvent actionEvent) throws IOException {
        RequestUserDTO user = new RequestUserDTO(
                txtEmail.getText().trim(),
                txtDisplayName.getText().trim(),
                txtContactNumber.getText().trim(),
                txtPassword.getText()
        );
        try{
            boolean isSaved = userBo.registerUser(user);
            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION,String.format("User Saved %s",user.getDisplayName())).show();
                setUi("LoginForm");
            }else{
                new Alert(Alert.AlertType.WARNING,"Try Again!").show();
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
