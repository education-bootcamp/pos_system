package com.devstack.pos.controller;

import com.devstack.pos.env.StaticResource;
import com.devstack.pos.util.SystemVariables;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DashboardFormController {
    public Label lblCompany;
    public Label lblVersion;
    public Label lblDate;
    public Label lblTime;
    public Label lblUser;
    public AnchorPane context;

    public void initialize(){
        
        //=============
        if(SystemVariables.responseUserDTO==null){
            try{
                logout();
            }catch (Exception e){
                e.printStackTrace();
            }
           
        }
        //=============
        
        setStaticData();
        setDateAndTime();
        setUserDetails();
    }

    private void setUserDetails() {
        lblUser.setText("System User : "+SystemVariables.responseUserDTO.getDisplayName());
    }

    private void logout() throws IOException {
        setUi("LoginForm");
    }

    private void setUi(String location) throws IOException {
        URL resource = getClass().getResource("/com/devstack/pos/view/"+location+".fxml");
        Parent load = FXMLLoader.load(resource);
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(
                new Scene(load)
        );
    }

    private void setDateAndTime() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.ENGLISH); // Saturday, June 15 2025
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm:ss a", Locale.ENGLISH); // Saturday, June 15 2025

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event->{
            LocalDateTime now = LocalDateTime.now();
            lblDate.setText(now.format(dateFormatter));
            lblTime.setText(now.format(timeFormatter));
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();



    }

    private void setStaticData() {
        lblVersion.setText("Version : "+ StaticResource.getVersion());
        lblCompany.setText("From : "+ StaticResource.getCompany());
    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        SystemVariables.responseUserDTO=null;
        logout();
    }

    public void openCustomerFormOnAction(MouseEvent mouseEvent) throws IOException {
        setUi("CustomerForm");
    }

    public void openProductFormOnAction(MouseEvent mouseEvent) throws IOException {
        setUi("ProductForm");
    }
}
