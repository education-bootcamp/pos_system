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

        // Basic input validation
        String email = txtEmail.getText().trim();
        String name = txtDisplayName.getText().trim();
        String contact = txtContactNumber.getText().trim();
        String password = txtPassword.getText();

        // Regex patterns
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String contactRegex = "^[0-9]{10}$"; // Adjust if your contact number format differs

        if (email.isEmpty() || name.isEmpty() || contact.isEmpty() || password.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "All fields are required!").show();
            return;
        }

        if (!email.matches(emailRegex)) {
            new Alert(Alert.AlertType.WARNING, "Invalid email address!").show();
            return;
        }

        if (!contact.matches(contactRegex)) {
            new Alert(Alert.AlertType.WARNING, "Invalid contact number! Must be 10 digits.").show();
            return;
        }

        if (password.length() < 6) {
            new Alert(Alert.AlertType.WARNING, "Password must be at least 6 characters long!").show();
            return;
        }


        RequestUserDTO user = new RequestUserDTO(email, name, contact, password);
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
